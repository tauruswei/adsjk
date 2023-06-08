package org.cos.application.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Web controller", tags = "web 模块", description = "web 模块 Rest API")
@RequestMapping("web")
@RestController
@Slf4j
public class WebController {

}
