package org.cos.application.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.cos.common.config.BaseConfiguration;
import org.cos.common.entity.data.vo.WebStatisticalDataVo;
import org.cos.common.redis.DownloadKey;
import org.cos.common.redis.RedisService;
import org.cos.common.repository.UserRepository;
import org.cos.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Web controller", tags = "web 模块", description = "web 模块 Rest API")
@RequestMapping("web")
@RestController
@Slf4j
public class WebController {
    @Autowired
    private BaseConfiguration baseConfiguration;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserRepository userRepository;
    @ApiOperation("增加用户的下载数量")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("increaseDownload")
    public Result increaseDownload(){
        redisService.incr(DownloadKey.getDownload,"");
        return Result.success();
    }

    @ApiOperation("获取网站的统计数据")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @GetMapping("getStatisticalData")
    public Result getStatisticalData(){
        // 获取游戏的下载量
        Integer downloadCount = redisService.get(DownloadKey.getDownload, "", Integer.class);
        if(ObjectUtils.isEmpty(downloadCount)){
            downloadCount =0;
        }
        // 获取web3用户

        WebStatisticalDataVo webStatisticalDataVo = userRepository.countWeb2AndWeb3User();
        log.info("======>请求--{}--接口开始,参数:{}", webStatisticalDataVo);
        if(ObjectUtils.isEmpty(webStatisticalDataVo)){
            webStatisticalDataVo = new WebStatisticalDataVo();
        }
        webStatisticalDataVo.setDownloadCount(downloadCount);
        // todo 游戏数量
        webStatisticalDataVo.setGameCount(1);

        return Result.success(webStatisticalDataVo);
    }

    @ApiOperation("获取网站的统计数据")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @GetMapping("getStatisticalData1")
    public Result getStatisticalData1(){

        WebStatisticalDataVo webStatisticalDataVo = new WebStatisticalDataVo();
       webStatisticalDataVo.setDownloadCount(1000);
       webStatisticalDataVo.setWeb2Count(205);
       webStatisticalDataVo.setWeb3Count(86);
       webStatisticalDataVo.setGameCount(3216);

        return Result.success(webStatisticalDataVo);
    }

    @ApiOperation("获取网站配置")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("getWebConfig")
    public Result getWebConfig(){
        return Result.success(baseConfiguration.getWebConfig());
    }





}
