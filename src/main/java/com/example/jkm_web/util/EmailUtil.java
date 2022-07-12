package com.example.jkm_web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
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

    public void sendEmailActivation(String email, String data) throws MessagingException {

        MimeMessageHelper messageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
        messageHelper.setFrom(from);
        messageHelper.setTo(email);
        messageHelper.setSubject("jkm验证码");
        messageHelper.setText(data, true);

        javaMailSender.send(messageHelper.getMimeMessage());
    }
}
