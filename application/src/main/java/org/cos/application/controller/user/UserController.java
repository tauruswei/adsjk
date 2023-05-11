package org.cos.application.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.cos.application.service.UserService;
import org.cos.common.entity.base.BaseResultEntity;
import org.cos.common.entity.data.req.CreateUserReq;
import org.cos.common.entity.data.req.ScdCreateTemplateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("user")
@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    // 创建模版
    @PostMapping("send_code")
    public BaseResultEntity createProject(@RequestBody CreateUserReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return BaseResultEntity.success("hello world");
    }
}
