package org.cos.application.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.cos.common.config.BaseConfiguration;
import org.cos.common.constant.CommonConstant;
import org.cos.common.convert.GameVoConvert;
import org.cos.common.entity.data.dto.UserRelationDTO;
import org.cos.common.entity.data.po.*;
import org.cos.common.entity.data.req.*;
import org.cos.common.entity.data.vo.UserLoginVo;
import org.cos.common.entity.data.vo.UserRelationAddressVo;
import org.cos.common.exception.GlobalException;
import org.cos.common.redis.RedisService;
import org.cos.common.redis.UserKey;
import org.cos.common.repository.PoolUserRepository;
import org.cos.common.repository.UserRelationRepository;
import org.cos.common.repository.UserRepository;
import org.cos.common.result.CodeMsg;
import org.cos.common.result.Result;
import org.cos.common.token.TokenManager;
import org.cos.common.util.MailUtil;
import org.cos.common.util.crypt.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
@Transactional
public class UserService {
    @Resource
    private JavaMailSender mailSender;
    @Autowired
    private BaseConfiguration baseConfiguration;
    @Value("${spring.mail.username}")
    private String mail;
    @Value("${spring.mail.subject}")
    private String subject;
    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRelationRepository userRelationRepository;
    @Autowired
    private AssetService assetService;
    @Autowired
    private NFTService nftService;

    @Autowired
    private PoolUserRepository poolUserRepository;

    public Result sendCode(UserSendCodeReq req) {
        if (StringUtils.isNotBlank( redisService.get(UserKey.getEmail, req.getEmail(), String.class))) {
            throw new GlobalException(CodeMsg.USER_SENDCODE_ERROR.fillArgs("验证码还在有效期内"));
        }
//        User user;
//        try {
//            user = userRepository.queryUserByEmail(req.getEmail());
//        } catch (Exception e) {
//            throw new GlobalException(CodeMsg.USER_QUERY_ERROR.fillArgs(e.getMessage()));
//        }
//        if (ObjectUtils.isNotEmpty(user)) {
//            throw new GlobalException(CodeMsg.USER_EXIST_ERROR);
//        }
        // 生成验证码
        int max = (int) Math.pow(10, 6) - 1;
        int min = (int) Math.pow(10, 6 - 1);
        Random random = new Random();
        int code = random.nextInt(max - min + 1) + min;
        System.out.println(code);
        // 发送邮件
        try {
//            mailUtil.sendTextMail(subject, mail, req.getEmail(), code + "");
            Map<String, Object> myMap = new HashMap<String, Object>() {{
                put("code", String.valueOf(code).chars().map(Character::getNumericValue).toArray());
            }};
            mailUtil.sendThymeleafMail(subject,mail,req.getEmail(),myMap,"email1");
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.USER_SENDCODE_ERROR.fillArgs(e.getMessage()));
        }
        // 验证码 存 redis
        boolean success = redisService.set(UserKey.getEmail, req.getEmail(), code);


        if (!success) {
            throw new GlobalException(CodeMsg.USER_SENDCODE_ERROR);
        }
        return Result.success(UserKey.getEmail.expireSeconds());
    }

    public Result queryUserById(Long userId) {

        User user = userRepository.queryUserById(userId);

        if (Objects.isNull(user)) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST_ERROR);
        }

        return Result.success(user);
    }

    public Result queryUserByName(String name) {

        User user = userRepository.queryUserByName(name);


        return Result.success(user);
    }

    public Result queryUserByEmail(String email) {

        User user = userRepository.queryUserByEmail(email);

        if(ObjectUtils.isEmpty(user)){
            throw new GlobalException(CodeMsg.USER_NOT_EXIST_ERROR);
        }
        return Result.success(user);
    }

    public Result validateEmailIsAvaliable(String email) {

        User user = userRepository.queryUserByEmail(email);

        if(!ObjectUtils.isEmpty(user)){
            return Result.success(false);
        }
        return Result.success(true);
    }

    public Result createUser(UserCreateReq req) {
        // 判断 redis 中的邮箱验证码是否存在
        String code = redisService.get(UserKey.getEmail, req.getEmail(), String.class);
        if(StringUtils.isBlank(req.getCode())){
            throw new GlobalException(CodeMsg.PARAMETER_VALID_ERROR.fillArgs("验证码不能为空"));
        }
        if ((!StringUtils.equalsIgnoreCase(req.getCode(), code))||StringUtils.isBlank(code)) {
            throw new GlobalException(CodeMsg.USER_ADD_ERROR.fillArgs("验证码无效，请重新输入"));
        }
        // 判断用户名的唯一性
        if(ObjectUtils.isNotEmpty(userRepository.queryUserByName(req.getName()))){
            throw new GlobalException(CodeMsg.USER_EXIST_ERROR);
        }
        // 判断用户邮箱的唯一性
        if(ObjectUtils.isNotEmpty(userRepository.queryUserByEmail(req.getEmail()))){
            throw new GlobalException(CodeMsg.USER_EXIST_ERROR);
        }

        // 先创建用户，直接存库
        User user = new User();
        user.setName(req.getName());
        user.setPasswd(SignUtil.getMD5ValueLowerCaseByDefaultEncode(req.getPasswd()));
        user.setEmail(req.getEmail());
        user.setWalletAddress(req.getWalletAddress());
        // 默认都是普通用户，质押一定量的 cosd 后，会更新 user_type 变成 1
        user.setUserType(CommonConstant.USER_PLAYER);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setUserRelationId(UUID.randomUUID().toString());

        try {
            userRepository.insertUser(user);
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.USER_ADD_ERROR.fillArgs(e.getMessage()));
        }
        //创建用户关系，level2 一律设置为 当前用户的id，俱乐部老板 质押成功后，后修改 usertype，同时修改 level1
        User inviter;
        UserRelation userRelation;
        // 存在邀请人
        if (req.getInviterId() != null && req.getInviterId() > 0) {
            // 查询邀请人的 基本信息
            inviter = userRepository.queryUserById(req.getInviterId());
            if (null == inviter) {
                throw new GlobalException(CodeMsg.USER_NOT_EXIST_ERROR);
            }
            // 查询邀请人的用户关系，
            userRelation = userRelationRepository.queryUserRelationById(inviter.getUserRelationId());
            // 说明这是渠道商邀请
            if(ObjectUtils.isEmpty(userRelation)){
                userRelation = new UserRelation();
                userRelation.setLevel0(inviter.getId());
//            俱乐部老板/普通用户的邀请
            }else{
                // 俱乐部老板邀请
                if (CommonConstant.USER_CLUB == inviter.getUserType()) {
                    userRelation.setLevel1(inviter.getId());
                    userRelation.setLevel0(userRelation.getLevel0());
//                // 普通用户邀请
//                } else {
//                    userRelation.setLevel0(userRelation.getLevel0());
                }

            }
            userRelation.setLevel2(user.getId());
            // 不存在邀请人
        } else {
            userRelation = new UserRelation();
            userRelation.setLevel2(user.getId());
        }
        userRelation.setId(user.getUserRelationId());
        userRelation.setCreateTime(new Date());
        userRelation.setUpdateTime(new Date());
        try {
            userRelationRepository.insertUserRelation(userRelation);
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.USER_RELATION_ADD_ERROR.fillArgs(e.getMessage()));
        }
        return Result.success();
    }

    public Result updateUser(UserUpdateReq req) {
        if(StringUtils.isNotBlank(req.getWalletAddress())){
            User user = userRepository.queryUserByWalletAddress(req.getWalletAddress());
            if(ObjectUtils.isNotEmpty(user)&&user.getId()!=req.getUserId()){
                throw new GlobalException(CodeMsg.USER_EXIST_ERROR);
            }
        }
        if (StringUtils.isNotBlank(req.getName())){
            User user = userRepository.queryUserByName(req.getName());
            if(ObjectUtils.isNotEmpty(user)&&user.getId()!=req.getUserId()){
                throw new GlobalException(CodeMsg.USER_EXIST_ERROR);
            }
        }

        User user = userRepository.queryUserById(req.getUserId());
        if (null == user) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST_ERROR);
        }
        user.setName(req.getName());
        user.setWalletAddress(req.getWalletAddress());
        user.setUpdateTime(new Date());

        try {
            userRepository.updateUser(user);
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.USER_ADD_ERROR.fillArgs(e.getMessage()));
        }
        return Result.success();
    }

    public Result resetPasswd(UserUpdateReq req) {
        User user = userRepository.queryUserByEmail(req.getEmail());
        if (null == user) {
            throw new GlobalException(CodeMsg.USER_EMAIL_NOT_EXIST_ERROR);
        }
        // 判断 redis 中的邮箱验证码是否存在
        if (StringUtils.isNotBlank(req.getCode())) {
            if (StringUtils.isBlank(req.getEmail())) {
                throw new GlobalException(CodeMsg.PARAMETER_VALID_ERROR.fillArgs("用户邮箱不能为空"));
            }
            String code = redisService.get(UserKey.getEmail, req.getEmail(), String.class);
            if ((!StringUtils.equalsIgnoreCase(req.getCode(), code))||StringUtils.isBlank(code)) {
                throw new GlobalException(CodeMsg.USER_UPDATE_ERROR.fillArgs("验证码无效，请重新输入"));
            }
            user.setEmail(req.getEmail());
        }else{
            throw new GlobalException(CodeMsg.PARAMETER_VALID_ERROR.fillArgs("验证码不能为空"));
        }

//        if (StringUtils.isNotBlank(req.getOldPasswd())) {
//            if (!StringUtils.equals(SignUtil.getMD5ValueLowerCaseByDefaultEncode(req.getOldPasswd()), user.getPasswd())) {
//                throw new GlobalException(CodeMsg.USER_UPDATE_ERROR.fillArgs("用户输入的密码和原来的密码不一致"));
//            }
            user.setPasswd(SignUtil.getMD5ValueLowerCaseByDefaultEncode(req.getNewPasswd()));
//        }

        user.setUpdateTime(new Date());

        try {
            userRepository.updateUser(user);
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.USER_ADD_ERROR.fillArgs(e.getMessage()));
        }
        return Result.success();
    }

    public Result modifyEmail(UserUpdateReq req) {
        User user = userRepository.queryUserById(req.getUserId());
        if (null == user) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST_ERROR);
        }
        // 判断 redis 中的邮箱验证码是否存在
        if (StringUtils.isNotBlank(req.getCode())) {
            if (StringUtils.isBlank(req.getEmail())) {
                throw new GlobalException(CodeMsg.PARAMETER_VALID_ERROR.fillArgs("用户邮箱不能为空"));
            }
            String code = redisService.get(UserKey.getEmail, req.getEmail(), String.class);
            if ((!StringUtils.equalsIgnoreCase(req.getCode(), code))||StringUtils.isBlank(code)) {
                throw new GlobalException(CodeMsg.USER_UPDATE_ERROR.fillArgs("验证码无效，请重新输入"));
            }
            user.setEmail(req.getEmail());
        }else{
            throw new GlobalException(CodeMsg.PARAMETER_VALID_ERROR.fillArgs("验证码不能为空"));
        }

        user.setUpdateTime(new Date());

        try {
            userRepository.updateUser(user);
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.USER_ADD_ERROR.fillArgs(e.getMessage()));
        }
        return Result.success();
    }

    public Result modifyPasswd(UserUpdateReq req) {
        User user = userRepository.queryUserById(req.getUserId());
        if (null == user) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST_ERROR);
        }

        if(StringUtils.isBlank(req.getOldPasswd())){
            throw new GlobalException(CodeMsg.PARAMETER_VALID_ERROR.fillArgs("用户旧密码不能为空"));
        }
        if(StringUtils.isBlank(req.getNewPasswd())){
            throw new GlobalException(CodeMsg.PARAMETER_VALID_ERROR.fillArgs("用户新密码不能为空"));
        }

        if (StringUtils.isNotBlank(req.getOldPasswd())) {
            if (!StringUtils.equals(SignUtil.getMD5ValueLowerCaseByDefaultEncode(req.getOldPasswd()), user.getPasswd())) {
                throw new GlobalException(CodeMsg.USER_UPDATE_ERROR.fillArgs("用户输入的密码和原来的密码不一致"));
            }
            user.setPasswd(SignUtil.getMD5ValueLowerCaseByDefaultEncode(req.getNewPasswd()));

        }

        user.setUpdateTime(new Date());

        try {
            userRepository.updateUser(user);
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.USER_ADD_ERROR.fillArgs(e.getMessage()));
        }
        return Result.success();
    }


    public Result login(UserLoginReq req) {
        UserLoginVo userLoginVo = new UserLoginVo();
        User user;
        if (StringUtils.isNotBlank(req.getEmail())) {
            user = userRepository.queryUserByEmail(req.getEmail());
            if (null == user) {
                throw new GlobalException(CodeMsg.USER_QUERY_ERROR);
            }
            if (!StringUtils.equals(SignUtil.getMD5ValueLowerCaseByDefaultEncode(req.getPasswd()), user.getPasswd())) {
                throw new GlobalException(CodeMsg.USER_LOGIN_ERROR.fillArgs("密码不正确"));
            }
            String jwt = TokenManager.createJWT(user.getName(), Integer.parseInt(baseConfiguration.getTokenTimeOut()), "", new HashMap<>());
            userLoginVo.setToken(jwt);
            userLoginVo.setWalletAddress(user.getWalletAddress());
            userLoginVo.setUserName(user.getName());
            userLoginVo.setUserId(user.getId());
            userLoginVo.setUserType(user.getUserType());
            return Result.success(userLoginVo);
        }

        user = userRepository.queryUserByName(req.getName());
        if (null == user) {
            throw new GlobalException(CodeMsg.USER_QUERY_ERROR);
        }
        if (!StringUtils.equals(SignUtil.getMD5ValueLowerCaseByDefaultEncode(req.getPasswd()), user.getPasswd())) {
            throw new GlobalException(CodeMsg.USER_LOGIN_ERROR.fillArgs("密码不正确"));
        }
        String jwt = TokenManager.createJWT(user.getName(), Integer.parseInt(baseConfiguration.getTokenTimeOut()), "", new HashMap<>());

        userLoginVo.setToken(jwt);
        userLoginVo.setWalletAddress(user.getWalletAddress());
        userLoginVo.setUserName(user.getName());
        userLoginVo.setUserId(user.getId());
        userLoginVo.setUserType(user.getUserType());
        return Result.success(userLoginVo);
    }

    public Result queryUserByInviterId(UserQueryByInviterIdReq req) {

        if ((null != req.getPage()) && (null != req.getLimit())) {
            PageHelper.startPage(req.getPage(), req.getLimit());
        } else {
            PageHelper.startPage(0, 0);
        }
        List<User> users = userRepository.queryUserByInviterId(req.getInviterId());

        PageInfo<User> pageInfo = new PageInfo<>(users);
//        return ResultLayui.success(applications,pageInfo.getTotal());

        return Result.success(pageInfo);
    }

    public Result getUserProfile(String userName, String passwd) {
        User user = userRepository.queryUserByName(userName);
        if (null == user) {
            throw new GlobalException(CodeMsg.USER_QUERY_ERROR);
        }
        if (!StringUtils.equals(SignUtil.getMD5ValueLowerCaseByDefaultEncode(passwd), user.getPasswd())) {
            throw new GlobalException(CodeMsg.USER_LOGIN_ERROR.fillArgs("密码不正确"));
        }
        String jwt = TokenManager.createJWT(user.getName(), Integer.parseInt(baseConfiguration.getTokenTimeOut()), "", new HashMap<>());

        // 获取用户资产
        AssetQueryReq assetQueryReq = new AssetQueryReq();
        assetQueryReq.setUserId(user.getId());
//        assetQueryReq.setAssetType(CommonConstant.EVIC);
        Result<List<Asset>> assets = assetService.queryUserAssets(assetQueryReq);

        // 获取用户是否具有玩星光的资格
        PoolUser poolUser = poolUserRepository.queryPoolUserByUserIdAndPoolId((long) CommonConstant.POOL_SL, user.getId());

        // 获取用户的NFT
        NFTListReq nftListReq = new NFTListReq();
        nftListReq.setUserId(user.getId());
        // 查找已使用的 NFT
        nftListReq.setStatus(CommonConstant.NFT_USED);

        Result<PageInfo<NFT>> nfts = nftService.queryNFTsByUserIdAndStatusGame(nftListReq);

//        List<NFT> list = nfts.getData().getList();

        return Result.success( GameVoConvert.UserGameVoConvert(jwt,user,assets.getData(),nfts.getData().getList(),poolUser.getAmount()>=baseConfiguration.getSlAmount()?true:false));
    }

    public Result createChannelLeader(String walletAddress) {
        User user = new User();
//        user = userRepository.queryUserByWalletAddressAndUserType(walletAddress, 0);
        if (null != userRepository.queryUserByWalletAddressAndUserType(walletAddress, 0)) {
            throw new GlobalException(CodeMsg.USER_EXIST_ERROR.fillArgs("该钱包地址已经绑定了一个渠道商"));
        }
        //先创建用户，存库
        user.setWalletAddress(walletAddress);
        user.setUserType(0);
//        user.setUserRelationId(UUID.randomUUID().toString());
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());

        try {
            userRepository.insertUser(user);
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.USER_ADD_ERROR.fillArgs(e.getMessage()));
        }
//        // 创建用户关系
//        UserRelation userRelation = new UserRelation();
//        userRelation.setId(user.getUserRelationId());
//        userRelation.setLevel0(user.getId());
//        userRelation.setCreateTime(new Date());
//        userRelation.setUpdateTime(new Date());
//        try {
//            userRelationRepository.insertUserRelation(userRelation);
//        } catch (Exception e) {
//            throw new GlobalException(CodeMsg.USER_RELATION_ADD_ERROR.fillArgs(e.getMessage()));
//        }


        return Result.success();
    }

    public Result queryChannelLeaderByWalletAddress (String walletAddress) {

        User user = userRepository.queryUserByWalletAddress(walletAddress);
        if (ObjectUtils.isEmpty(user)){
            createChannelLeader(walletAddress);
            User user1 = userRepository.queryUserByWalletAddress(walletAddress);
            return Result.success(user1);
        }

        return Result.success(user.getId());
    }

    public Result queryClubAndChannelAddress(Long userId){
        UserRelationAddressVo userRelationAddressVo = new UserRelationAddressVo();
        User user = userRepository.queryUserById(userId);
        if(ObjectUtils.isEmpty(user)){
            throw new GlobalException(CodeMsg.USER_NOT_EXIST_ERROR);
        }
        userRelationAddressVo.setUserAddress(user.getWalletAddress());

        UserRelationDTO userRelationDTO = userRelationRepository.queryUserByRelationId(user.getUserRelationId());
        if(ObjectUtils.isEmpty(userRelationDTO)){
            return Result.success(userRelationAddressVo);
        }
        User level0= userRelationDTO.getLevel0();
        User level1 = userRelationDTO.getLevel1();
        if((!ObjectUtils.isEmpty(level0))&&(!ObjectUtils.isEmpty(level1))){
            userRelationAddressVo.setChannelAddress(level0.getWalletAddress());
            userRelationAddressVo.setClubAddress(level1.getWalletAddress());
        }else if((ObjectUtils.isEmpty(level0))&&(!ObjectUtils.isEmpty(level1))){
            userRelationAddressVo.setClubAddress(level1.getWalletAddress());
        }else if((!ObjectUtils.isEmpty(level0))&&(ObjectUtils.isEmpty(level1))) {
            userRelationAddressVo.setClubAddress(level0.getWalletAddress());
        }
        userRelationAddressVo.setUserAddress(user.getWalletAddress());
        return Result.success(userRelationAddressVo);
    }


}
