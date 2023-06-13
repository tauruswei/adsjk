package org.cos.application.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cos.common.entity.data.vo.WebStatisticalDataVo;
import org.cos.common.redis.DownloadKey;
import org.cos.common.redis.RedisService;
import org.cos.common.repository.TransWebsiteRepository;
import org.cos.common.repository.UserRepository;
import org.cos.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Web controller", tags = "web 模块", description = "web 模块 Rest API")
@RequestMapping("admin")
@RestController
@Slf4j
public class AdminController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransWebsiteRepository transWebsiteRepository;

    @ApiOperation("统计 evic 的总销售量")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("sumEvicSales")
    public Result sumEvicSales(){
        long l = transWebsiteRepository.sumEvicSales();

        return Result.success();
    }



}
