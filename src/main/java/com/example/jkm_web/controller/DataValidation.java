package com.example.jkm_web.controller;

import com.example.jkm_web.model.Teacher;
import com.example.jkm_web.service.TeacherService;
import com.example.jkm_web.util.EmailUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 数据校验
 */
@Controller
public class DataValidation {

    private final static Logger logger = LoggerFactory.getLogger(DataValidation.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private EmailUtil emailUtil;
    @Resource
    private TeacherService teacherService;

    @Value("${online-time}")
    private Integer onlineTime;
    @Value("${ip}")
    private String ip;

    /*
    emailActivationCode这个函数写的并不好，中间很大一段都应该创建一个Service来处理。
    Controller层和Service层还是应该分清楚一些，方便以后排查。
    这段后期可能重构
     */
    @RequestMapping("/emailActivationCode")
    public String emailActivationCode(String code, String email, HttpServletRequest request, HttpServletResponse response) {
        Object msg = "激活失败,请稍后重试";
        String selectCode = (String) stringRedisTemplate.opsForHash().get(email, "code");
        if (code.equals(selectCode)) {
            //读取数据写入数据库
            Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(email);
            if (map.get("classId") == null) {
                Teacher teacher = new Teacher((String) map.get("id"), (String) map.get("name"), email, (String) map.get("password"));
                try{
                    teacherService.addTeacher(teacher);
                    msg = "激活成功";
                    //激活成功，注册状态改为在线状态。重新设置过期时间，以及状态修改。
                    stringRedisTemplate.expire(email, onlineTime, TimeUnit.MINUTES);
                    stringRedisTemplate.opsForHash().delete(email, "code");
                    stringRedisTemplate.opsForHash().put(email, "online", "1");
                    //设置cookie，存活时间同上。用于自动登录。
                    Cookie cookie = new Cookie("email", email);
                    cookie.setPath("/");
                    cookie.setMaxAge(onlineTime * 60);
                    response.addCookie(cookie);
                    //发送注册成功邮件
                    //读取模板文件
                    String filePath = "templates/registerSuccessHtml.html";
                    String html = EmailUtil.readHtmlToString(filePath);
                    //写入参数
                    Document document = Jsoup.parse(html);
                    document.getElementById("userId").html((String) stringRedisTemplate.opsForHash().get(email, "name"));
                    emailUtil.sendEmailActivation(email, document.toString());
                }catch (Exception e){
                    logger.info("注册过程中，添加Teacher失败：\n"+ e);
                }
            }
        }
        request.setAttribute("msg", msg);
        return "index";
    }
}
