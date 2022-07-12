package com.example.jkm_web.service.impl;

import com.example.jkm_web.controller.AccountController;
import com.example.jkm_web.dao.StudentDao;
import com.example.jkm_web.dao.TeacherDao;
import com.example.jkm_web.model.Student;
import com.example.jkm_web.model.Teacher;
import com.example.jkm_web.model.User;
import com.example.jkm_web.service.AccountService;
import com.example.jkm_web.util.EmailUtil;
import com.example.jkm_web.util.VerificationCodeUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("RegisterService")
public class AccountServiceImpl implements AccountService {

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Resource
    private TeacherDao teacherDao;
    @Resource
    private StudentDao studentDao;
    @Autowired
    private EmailUtil emailUtil;

    @Value("${email-code-length}")
    private Integer emailCodeLength;
    @Value("${email-max-life-time}")
    private Integer emailLifeTime;
    @Value("${ip}")
    private String ip;

    @Override
    public void Register(User user) throws Exception {
        if (user instanceof Student) {
            studentDao.insertStudent((Student) user);
        } else if (user instanceof Teacher) {
            teacherDao.insertTeacher((Teacher) user);
        }
    }

    @Override
    public String sendEmailCode(String email) throws Exception {
        //生成验证码
        String emailCode = VerificationCodeUtil.getVerificationCode(emailCodeLength);
        //读取模板文件
        String filePath = "templates/sendEmailCode.html";
        String html = EmailUtil.readHtmlToString(filePath);
        //写入参数
        Document document = Jsoup.parse(html);
        document.getElementById("emailCode").html(emailCode);
        document.getElementById("time").html(String.valueOf(emailLifeTime));
        //发送邮件
        emailUtil.sendEmailActivation(email, document.toString());
        return emailCode;
    }


//    @Override
//    public String Register(User user) {
//        System.out.println(user);
//        String result = "邮件发送失败，邮箱可能存在问题";
//        //生成验证码
//        String emailVerificationCode = VerificationCodeUtil.getVerificationCode(verificationCodeLength);
//
//        //读取模板文件
//        String filePath = "templates/sendEmailActivationHtml.html";
//        String html = EmailUtil.readHtmlToString(filePath);
//        //写入参数
//        Document document = Jsoup.parse(html);
//        document.getElementById("userId").html(user.getName());
//        document.getElementById("servletIP").attr("href", "http://" + ip + "/emailActivationCode?code=" + emailVerificationCode + "&email=" + user.getEmail());
//        //发送邮件
//        if (emailUtil.sendEmailActivation(user.getEmail(), document.toString())) {
//            //记录user类和验证码，设置销毁时间
//            Map<String, String> map = new HashMap<>();
//            map.put("id", user.getId());
//            map.put("name", user.getName());
//            map.put("password", user.getPassword());
//            map.put("code", emailVerificationCode);
//            if (user instanceof Student){
//                map.put("classId",((Student) user).getClassId());
//            }
//            //设置在线状态，0为注册状态，1为登陆状态。
//            map.put("online","0");
//            stringRedisTemplate.opsForHash().putAll(user.getEmail(), map);
//            stringRedisTemplate.expire(user.getEmail(), deleteTime, TimeUnit.MINUTES);
//            result = "";
//        }
//        return result;
//    }
}
