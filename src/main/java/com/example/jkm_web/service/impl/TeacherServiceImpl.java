package com.example.jkm_web.service.impl;

import com.example.jkm_web.dao.TeacherDao;
import com.example.jkm_web.model.Teacher;
import com.example.jkm_web.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("TeacherService")
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private TeacherDao teacherDao;

    @Override
    public void addTeacher(Teacher teacher) {
        teacherDao.insertTeacher(teacher);
    }

}
