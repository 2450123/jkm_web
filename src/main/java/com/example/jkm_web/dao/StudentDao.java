package com.example.jkm_web.dao;

import com.example.jkm_web.model.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentDao {
    void insertStudent(Student student);
}
