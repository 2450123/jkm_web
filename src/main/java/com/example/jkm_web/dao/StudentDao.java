package com.example.jkm_web.dao;

import com.example.jkm_web.model.Student;
import com.example.jkm_web.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentDao {
    void insertStudent(Student student);
    void updatePassword(String id, String password);
    Student queryStudentById(String id);
}
