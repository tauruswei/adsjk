package org.cos.application.controller.abe;

import org.cos.common.entity.base.BaseResultEntity;
import org.cos.common.entity.base.BaseResultEnum;
import org.cos.common.entity.data.req.*;
//import com.primihub.biz.service.abe.AbeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("abe")
@RestController
@Slf4j
public class AbeController {

    // 创建模版
    @PostMapping("test")
    public BaseResultEntity createProject(
                                           @RequestBody ScdCreateTemplateReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return BaseResultEntity.success("hello world");
    }



}
