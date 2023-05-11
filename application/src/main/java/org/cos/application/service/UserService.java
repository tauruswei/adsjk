package org.cos.application.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.cos.common.config.BaseConfiguration;
import org.cos.common.entity.User;
import org.cos.common.entity.data.req.UserCreateReq;
import org.cos.common.entity.data.req.UserSendCodeReq;
import org.cos.common.exception.GlobalException;
import org.cos.common.redis.RedisService;
import org.cos.common.redis.UserKey;
import org.cos.common.repository.UserRepository;
import org.cos.common.result.CodeMsg;
import org.cos.common.result.Result;
import org.cos.common.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
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

    public Result sendCode(UserSendCodeReq req) {
        User user = new User();
        try {
            user = userRepository.queryUserByEmail(req.getEmail());
        }catch(Exception e){
            throw  new GlobalException(CodeMsg.USER_QUERY_ERROR.fillArgs(e.getMessage()));
        }

        if (null!=user){
            throw  new GlobalException(CodeMsg.USER_EXIST_ERROR);
        }


        // 生成验证码
        int max = (int)Math.pow(10, 6) - 1;
        int min = (int)Math.pow(10, 6 - 1);
        Random random = new Random();
        int code = random.nextInt(max - min + 1) + min;
        System.out.println(code);
        // 发送邮件
        try {
            mailUtil.sendTextMail(subject,mail,req.getEmail(),code+"");
        }catch (Exception e){
            throw  new GlobalException(CodeMsg.USER_SENDCODE_ERROR.fillArgs(e.getMessage()));
        }
        // 验证码 存 redis
        boolean success = redisService.set(UserKey.getEmail, req.getEmail(), code);

        if(!success){
            throw  new GlobalException(CodeMsg.USER_SENDCODE_ERROR);
        }
        return Result.success();
    }

    public Result createUser(UserCreateReq req){
        // 判断 redis 中的邮箱验证码是否存在
        String code = redisService.get(UserKey.getEmail, req.getUserSendCodeReq().getEmail(), String.class);
        if (!StringUtils.endsWithIgnoreCase(req.getCode(),code)){
            throw new GlobalException(CodeMsg.USER_ADD_ERROR.fillArgs("验证码失效，请重新获取"));
        }
        User user = new User();
        user.setName(req.getName());
        user.setPasswd(req.getPasswd());
        user.setEmail(req.getUserSendCodeReq().getEmail());
        user.setWalletAddress(req.getWalletAddress());
        user.setInviterId(req.getInviterId());
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        try {
            userRepository.insertUser(user);
        }catch (Exception e){
            throw new GlobalException(CodeMsg.USER_ADD_ERROR.fillArgs(e.getMessage()));
        }
        return Result.success();
    }

    public Result login(UserCreateReq req){
        return Result.success();
    }


}
