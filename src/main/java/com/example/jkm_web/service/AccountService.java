package com.example.jkm_web.service;

import com.example.jkm_web.model.User;

public interface AccountService {
    void Register(User user) throws Exception;
    String sendEmailCode(String email) throws Exception;
    void Reset(String id,String password,String role) throws Exception;
    User queryUserById(String id,String role) throws Exception;
    //boolean addTeacher(Teacher teacher);
}
