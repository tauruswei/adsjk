package org.cos.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Slf4j
public class ZipUtils {
    /**
     * 压缩文件到指定路径---单个文件、多个文件适用
     *
     * @param path    源文件路径
     * @param zipPath 目标路径
     */
    public static void pathFileTOZipFile(String path, String zipPath, Set<String> excludeFileNames) {
        File sourceFile = new File(path);
        File targetZipFile = new File(zipPath);
        if (!targetZipFile.getParentFile().exists()) {
            targetZipFile.getParentFile().mkdir();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(targetZipFile);
            ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
            if (sourceFile.isDirectory()) {
                File[] files = sourceFile.listFiles();
                boolean isExclude = excludeFileNames!=null;
                if (files.length != 0) {
                    for (File file : files) {
                        if (isExclude&&excludeFileNames.contains(file.getName()))
                            continue;
                        executeToZip(zipOutputStream, file);
                    }
                }
            } else {
                executeToZip(zipOutputStream, sourceFile);
            }
            zipOutputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行单个文件压缩---可以作为压缩方法单独使用
     *
     * @param zipOutputStream 目标Zip输出流
     * @param sourceFile      源文件
     */
    public static void executeToZip(ZipOutputStream zipOutputStream, File sourceFile) throws IOException {
        FileInputStream inputStream = null;
        try {
            ZipEntry zipEntry = new ZipEntry(sourceFile.getName());
            zipOutputStream.putNextEntry(zipEntry);
            inputStream = new FileInputStream(sourceFile);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) >= 0) {
                zipOutputStream.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
            log.info("文件：{}压缩成功啦！",sourceFile.getAbsolutePath());
        }
    }
    public static String sendMain(String to) {
        String from = "wbt@chessofstars.io";// 发件人电子邮箱
        //String to = "×××××@qq.com";// 收件人电子邮箱
        //获取系统属性，主要用于设置邮件相关的参数。
        Properties properties = System.getProperties();
        //设置邮件传输服务器，由于本次是发送邮件操作，所需我们需要配置smtp协议，按outlook官方同步邮件的要求，依次配置协议地址，端口号和加密方法
        properties.setProperty("mail.smtp.host", "smtp.office365.com");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        //用户验证并返回Session，开启用户验证，设置发送邮箱的账号密码。
        properties.setProperty("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("wbt@chessofstars.io", "Xiao120315!");//账号密码
            }
        });

        //创建MimeMessage消息对象，消息头配置了收发邮箱的地址，消息体包含了邮件标题和邮件内容。接收者类型：TO代表直接发送，CC代表抄送，BCC代表秘密抄送。
        try {
            MimeMessage message = new MimeMessage(session);
            message.addHeader("X-Mailer", "Microsoft Outlook Express 6.00.2900.2869");
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("入学要求及申读信息");
            message.setText("具体内容请见附件!");
//		    Transport.send(message);
//		    System.out.println("发送成功！");

            // 1.创建复合消息体
            Multipart multipart = new MimeMultipart();
            // 2.添加附件
            BodyPart filePart = new MimeBodyPart();
            String filePath = "/data/ISC2019130.xlsx";
            DataSource source = new FileDataSource(filePath);
            filePart.setDataHandler(new DataHandler(source));
            filePart.setFileName(source.getName());
            multipart.addBodyPart(filePart);
            // 3.添加文本内容
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("测试包含文本和附件的邮件！");
            multipart.addBodyPart(textPart);
            // 4.绑定消息对象
            message.setContent(multipart);
            // 5.发送邮件
            Transport.send(message);
            return "success";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "failed";
        }
    }

}
