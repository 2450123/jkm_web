package com.example.jkm_web.service.impl;

import com.example.jkm_web.dao.TeacherDao;
import com.example.jkm_web.model.Teacher;
import com.example.jkm_web.service.TeacherService;
import com.example.jkm_web.util.VerificationCodeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service("TeacherService")
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private TeacherDao teacherDao;

    @Value("${register.email-delete-time}")
    private Integer deleteTime;
    @Value("${register.verification-code-length}")
    private Integer verificationCodeLength;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String Register(Teacher teacher) {

        //生成验证码
        String emailVerificationCode = VerificationCodeUtil.getVerificationCode(verificationCodeLength);
        //记录验证码，设置销毁时间
        stringRedisTemplate.opsForValue().set(teacher.getEmail(), emailVerificationCode, deleteTime, TimeUnit.MINUTES);
        //发送邮件


        return "";
    }

    @Override
    public boolean addTeacher(Teacher teacher) {
        teacherDao.insertTeacher(teacher);
        return false;
    }
}
