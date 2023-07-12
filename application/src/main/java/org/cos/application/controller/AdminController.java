package org.cos.application.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cos.application.service.AdminService;
import org.cos.common.entity.data.req.UserListReq;
import org.cos.common.entity.data.req.UserUpdateReq;
import org.cos.common.repository.TransWebsiteRepository;
import org.cos.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "Web controller", tags = "web 模块", description = "web 模块 Rest API")
@RequestMapping("admin")
@RestController
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private TransWebsiteRepository transWebsiteRepository;

    @ApiOperation("统计 evic 的总销售量")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("sumEvic")
    public Result sumEvic(@RequestParam(name = "transType",required = true) int transType,@RequestParam(name = "days",required = false) int days,@RequestParam(name = "userId",required = false,defaultValue = "0") Long userId){

        return adminService.sumEvicSales(transType,days,userId);
    }
    @ApiOperation("统计 evic 某一天的销售量")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("sumEvicDay")
    public Result sumEvicDay(@RequestParam(name = "transType",required = true) int transType,@RequestParam(name = "days",required = false) int days,@RequestParam(name = "userId",required = false,defaultValue = "0") Long userId){

        return adminService.sumEvicSalesDay(transType,days,userId);
    }


    @ApiOperation("图表数据")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("graphData")
    public Result graphData(@RequestParam(name = "transType",required = true) int transType,@RequestParam(name = "dataNum",required = false) int dataNum,@RequestParam(name = "interval",required = false) int interval,@RequestParam(name = "userId",required = false,defaultValue = "0") Long userId){
        return adminService.graphData(transType,dataNum,interval,userId);
    }



    @ApiOperation("统计 星光玩家的数量")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("statisticalSL")
    public Result statisticalSL(@RequestParam(name = "userId",required = false,defaultValue = "0") Long userId){

        return adminService.statisticalSL(userId);
    }

    @ApiOperation("统计 NFT 销售和使用情况")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("statisticalNFT")
    public Result statisticalNFT(@RequestParam(name = "userId",required = false,defaultValue = "0") Long userId){

        return adminService.statisticalNFT(userId);
    }

    @ApiOperation("获取渠道商和俱乐部老板的列表")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("userList")
    public Result userList(@RequestBody UserListReq userListReq){

        return adminService.userList(userListReq);
    }


    @ApiOperation("更改俱乐部老板和渠道商的身份")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("userUpdate")
    public Result userUpdate(@RequestBody UserUpdateReq req){

        return adminService.userUpdate(req);
    }
    @ApiOperation("统计 NFT 销售利润")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @GetMapping("getNftProfile")
    public Result getNftProfile(){

        return adminService.getNftProfile();
    }








}
