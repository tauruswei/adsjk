package org.cos.application.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;
import org.cos.application.service.GameService;
import org.cos.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Game controller",tags = "Game 模块", description = "Game 模块 Rest API")
@RequestMapping("game")
@RestController
@Slf4j
public class GameController {

    @Autowired
    GameService gameService;
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="query")
    @PostMapping("increaseUserChesserPts")
    public Result increaseUserChesserPts(@RequestParam(name = "chesserId") Integer chesserId,
                                         @RequestParam(name = "ptsInc") Integer ptsInc){

        return Result.success();
    }

    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="query")
    @PostMapping("increaseUserMoney")
    public Result increaseUserMoney(@RequestParam(name = "moneyId") Integer moneyId,
                                    @RequestParam(name = "moneyAmount") double moneyAmount){

        return Result.success();
    }

}
