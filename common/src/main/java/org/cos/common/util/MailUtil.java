package org.cos.common.util;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import org.cos.common.config.BaseConfiguration;
import org.cos.common.exception.GlobalException;
import org.cos.common.result.CodeMsg;
import org.cos.common.tool.LogTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.model.SesException;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.*;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
/**
 * @Description 邮件相关工具类
 * http://www.javaboy.org/2019/0717/springboot-mail.html
 * https://blog.csdn.net/Tracycater/article/details/73441010
 * @Author jie.zhao
 * @Date 2019/12/13 10:22
 */
@Component
@Slf4j
public class MailUtil {

    @Autowired
    BaseConfiguration baseConfiguration;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.myapp.aws.ses.accessKeyId}")
    private String awsAccessKeyId;

    @Value("${spring.myapp.aws.ses.secretKey}")
    private String awsSecretKey;

    @Value("${spring.myapp.aws.ses.region}")
    private String region;

    @Bean
    public SesV2Client sesV2Client() {
        return SesV2Client.builder()
                .region(Region.of(region.toLowerCase()))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(awsAccessKeyId, awsSecretKey)))
                .build();
    }

    /**
     * 发送简单的文件邮件
     *
     * @param subject 主题
     * @param from    发件人
     * @param to      收件人
     * @param text    邮件的正文
     */
    public void sendTextMail(String subject, String from, String to, String text) throws MessagingException, UnsupportedEncodingException {
        this.sendTextMail(subject, from, to, null, null, text);
    }

    /**
     * 发送简单的文件邮件
     *
     * @param subject 主题
     * @param from    发件人
     * @param to      收件人
     * @param cc      抄送人，可以有多个抄送人
     * @param bcc     隐秘抄送人，可以有多个
     * @param text    邮件的正文
     */
    public void sendTextMail(String subject, String from, String to, String[] cc, String[] bcc, String text) throws MessagingException, UnsupportedEncodingException {
        //1. 构建邮件对象，注意，这里要通过 javaMailSender 来获取一个复杂邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //2. MimeMessageHelper 是一个邮件配置的辅助工具类，true 表示构建一个 multipart message 类型的邮件
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //3. 针对工具类，配置邮件发送的基本信息
        helper.setSubject(subject);
        helper.setFrom(from, baseConfiguration.getMailNickName());
        helper.setTo(to);
        if (cc != null) {
            helper.setCc(cc);
        }
        if (bcc != null) {
            helper.setBcc(bcc);
        }
        helper.setSentDate(new Date());
        helper.setText(text);
        //5. 发送邮件
        javaMailSender.send(mimeMessage);
    }

    /**
     * 发送带附件的邮件
     *
     * @param subject  主题
     * @param from     发件人
     * @param to       收件人
     * @param text     邮件的正文
     * @param filePath 附件
     */
    public void sendAttachFileMail(String subject, String from, String to, String text, String filePath) throws MessagingException {
        this.sendAttachFileMail(subject, from, to, null, null, text, filePath);
    }

    /**
     * 发送带附件的邮件
     *
     * @param subject  主题
     * @param from     发件人
     * @param to       收件人
     * @param cc       抄送人，可以有多个抄送人
     * @param bcc      隐秘抄送人，可以有多个
     * @param text     邮件的正文
     * @param filePath 附件
     */
    public void sendAttachFileMail(String subject, String from, String to, String[] cc, String[] bcc, String text, String filePath) throws MessagingException {
        //1. 构建邮件对象，注意，这里要通过 javaMailSender 来获取一个复杂邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //2. MimeMessageHelper 是一个邮件配置的辅助工具类，true 表示构建一个 multipart message 类型的邮件
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //3. 针对工具类，配置邮件发送的基本信息
        helper.setSubject(subject);
        helper.setFrom(baseConfiguration.getMailNickName());
        helper.setTo(to);
        if (cc != null) {
            helper.setCc(cc);
        }
        if (bcc != null) {
            helper.setBcc(bcc);
        }
        helper.setSentDate(new Date());
        helper.setText(text);

        File file = new File(filePath);
        //4. 添加邮件附件
        helper.addAttachment(file.getName(), file);
        //5. 发送邮件
        javaMailSender.send(mimeMessage);
    }

    /**
     * 发送邮件使用Thymeleaf模板
     *
     * @param subject      主题
     * @param from         发件人
     * @param to           收件人
     * @param data         邮件模板需要替换的数据
     * @param templatePath 模板路径 路径在src/main/resources/templates/下
     * @throws MessagingException
     */
    public void sendThymeleafMail(String subject, String from, String to, Map<String, Object> data, String templatePath) throws MessagingException, UnsupportedEncodingException {
        this.sendThymeleafMail(subject, from, to, null, null, data, templatePath);
    }

    /**
     * 发送邮件使用Thymeleaf模板
     *
     * @param subject      主题
     * @param from         发件人
     * @param to           收件人
     * @param cc           抄送人，可以有多个抄送人
     * @param bcc          隐秘抄送人，可以有多个
     * @param data         邮件模板需要替换的数据
     * @param templatePath 模板路径 路径在src/main/resources/templates/下
     * @throws MessagingException
     */
    public void sendThymeleafMail(String subject, String from, String to, String[] cc, String[] bcc, Map<String, Object> data, String templatePath) throws MessagingException, UnsupportedEncodingException {
        //1. 构建邮件对象，注意，这里要通过 javaMailSender 来获取一个复杂邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //2. MimeMessageHelper 是一个邮件配置的辅助工具类，true 表示构建一个 multipart message 类型的邮件
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //3. 针对工具类，配置邮件发送的基本信息
        helper.setSubject(subject);
        helper.setFrom(new InternetAddress(from, "COSD"));
        helper.setTo(to);
        if (cc != null) {
            helper.setCc(cc);
        }
        if (bcc != null) {
            helper.setBcc(bcc);
        }
        helper.setSentDate(new Date());

        Context context = new Context();
        if (data != null) {
            data.forEach((k, v) -> {
                context.setVariable(k, v);
            });
        }
        String process = templateEngine.process(templatePath, context);
        helper.setText(process, true);
        javaMailSender.send(mimeMessage);
    }

    public static SendEmailResponse SendMessageTemplate(SesV2Client client, String sender, Template myTemplate,String recipient){

        Destination destination = Destination.builder()
                .toAddresses(recipient)
                .build();

        /*
         Specify both name and favorite animal (favoriteanimal) in your code when defining the Template object.
         If you don't specify all the variables in the template, Amazon SES doesn't send the email.
        */
//        Template myTemplate = Template.builder()
//                .templateName(templateName)
//                .templateData("{\n" +
//                        "  \"code\": \"666666\"\n" +
//                        "}")
//                .build();

        EmailContent emailContent = EmailContent.builder()
                .template(myTemplate)
                .build();

        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .destination(destination)
                .content(emailContent)
                .fromEmailAddress(sender)
                .build();

        try {
            log.info("Attempting to send an email based on a template using the AWS SDK for Java (v2)...");
            SendEmailResponse sendEmailResponse = client.sendEmail(emailRequest);
            log.info("email based on a template was sent");
            return sendEmailResponse;

        } catch (SesV2Exception e) {
           throw new GlobalException(CodeMsg.USER_SENDCODE_ERROR.fillArgs(e.awsErrorDetails().errorMessage()));
        }
    }

    // snippet-start:[ses.java2.list.templates.sesv2.main]
    public static void createTemplate(SesV2Client sesv2Client, CreateEmailTemplateRequest createTemplateRequest) {

        try {
            sesv2Client.createEmailTemplate(createTemplateRequest);
            System.out.println("Email template has been created.");
        } catch (SesException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }
    public static void deleteTemplate(SesV2Client sesv2Client, DeleteEmailTemplateRequest deleteEmailTemplateRequest) {

        try {
            sesv2Client.deleteEmailTemplate(deleteEmailTemplateRequest);
            System.out.println("Email template has been created.");
        } catch (SesException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }
}
