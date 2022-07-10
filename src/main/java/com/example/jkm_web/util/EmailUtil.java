package com.example.jkm_web.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Component
public class EmailUtil {

    private final static Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    //读取html模板
    public static String readHtmlToString(String htmlPath) {
        InputStream is = null;
        Reader reader = null;
        try {
            is = new ClassPathResource(htmlPath).getInputStream();
            if (is == null) {
                throw new Exception("未找到html模板文件");
            }
            reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            StringBuilder sb = new StringBuilder();
            int bufferSize = 1024;
            char[] buffer = new char[bufferSize];
            int length;
            while ((length = reader.read(buffer, 0, bufferSize)) != -1) {
                sb.append(buffer, 0, length);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @Resource
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;

    public boolean sendEmailActivation(String email,String data) {
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            messageHelper.setFrom(from);
            messageHelper.setTo(email);
            messageHelper.setSubject("激活账号");
            messageHelper.setText(data,true);

            javaMailSender.send(messageHelper.getMimeMessage());
            logger.info("发送邮件成功:->" + email);
        } catch (Exception e) {
            logger.info("发送邮件失败:->" + email);
            return false;
        }
        return true;
    }
}
