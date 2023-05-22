package org.cos.application.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cos.application.service.GameService;
import org.cos.application.service.UserService;
import org.cos.common.entity.data.vo.UserGameVo;
import org.cos.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Api(value = "Game controller", tags = "Game 模块", description = "Game 模块 Rest API")
@RequestMapping("game")
@RestController
@Slf4j
public class GameController {

    @Autowired
    GameService gameService;
    @Autowired
    UserService userService;

    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "query")
    @PostMapping("increaseUserChesserPts")
    public Result increaseUserChesserPts(@RequestParam(name = "userId") Long userId,
                                         @RequestParam(name = "chesserId") Integer chesserId,
                                         @RequestParam(name = "ptsInc") Integer ptsInc) {

        return gameService.increaseUserChesserPts(userId,chesserId,ptsInc);
    }

    @ApiOperation("用户钱币的加减")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "query")
    @PostMapping("increaseUserMoney")
    public Result increaseUserMoney(@RequestParam(name = "userId") Long userId,
                                    @RequestParam(name = "moneyId") Integer moneyId,
                                    @RequestParam(name = "moneyAmount") double moneyAmount) {

        return gameService.increaseUserMoney(userId, moneyId, moneyAmount);
    }

    @ApiOperation("用户在游戏端登录")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "query")
    @PostMapping("getUserProfile")
    public Result getUserProfile(@RequestParam(name = "userName") @NotBlank(message = "user name cannot be blank") String userName,
                                 @RequestParam(name = "userPassword") @NotBlank(message = "passwd cannot be blank") String passwd) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.getUserProfile(userName, passwd);
    }


}
