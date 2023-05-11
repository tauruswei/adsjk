package org.cos.application.service;

import lombok.extern.slf4j.Slf4j;
import org.cos.common.entity.base.BaseResultEntity;
import org.cos.common.entity.data.req.CreateUserReq;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class UserService {
    @Resource
    private JavaMailSender mailSender;

    public BaseResultEntity sendCode(CreateUserReq req) throws Exception {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("abc@qq.com");
            helper.setTo(req.getEmail());
            helper.setSubject("test");
            helper.setText("content", true);
            mailSender.send(message);


        return BaseResultEntity.success();
    }
}
