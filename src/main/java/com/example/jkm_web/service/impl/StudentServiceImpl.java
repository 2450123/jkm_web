package com.example.jkm_web.service.impl;

import com.example.jkm_web.dao.StudentDao;
import com.example.jkm_web.model.Student;
import com.example.jkm_web.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("StudentService")
public class StudentServiceImpl implements StudentService {

    @Resource
    StudentDao studentDao;

    @Override
    public void addStudent(Student student) {
        studentDao.insertStudent(student);
    }
}
