package com.example.jkm_web.dao;

import com.example.jkm_web.model.Teacher;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeacherDao {
    void insertTeacher(Teacher teacher);
    void updatePassword(String id,String password);
    Teacher queryTeacherById(String id);
}
