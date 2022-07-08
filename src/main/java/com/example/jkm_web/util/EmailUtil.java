package com.example.jkm_web.util;

import com.example.jkm_web.model.Teacher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Component
public class EmailUtil {
    //读取html模板
    private static String readHtmlToString(String htmlPath) {
        InputStream is = null;
        Reader reader = null;
        try {
            is = EmailUtil.class.getClassLoader().getResourceAsStream(htmlPath);
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

    public String sendEmailActivation(Teacher teacher) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(teacher.getEmail());
        simpleMailMessage.setSubject("Test");
        simpleMailMessage.setText("Test");
        javaMailSender.send(simpleMailMessage);
        return "";
    }
}
