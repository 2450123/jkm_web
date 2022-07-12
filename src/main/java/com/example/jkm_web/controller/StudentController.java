package com.example.jkm_web.controller;

import com.example.jkm_web.service.AccountService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class StudentController {
    @Resource
    private AccountService registerService;

//    @RequestMapping("/sRegister")
//    @ResponseBody
//    public String sRegister(String id, String email, String name, String password, String classId) {
//        Map<String, String> result = new HashMap<>();
//        result.put("result", "false");
//        result.put("msg", "");
//        //数据校验(暂空)
//
//
//        if (!result.get("msg").equals("")) {
//            return JSON.toJSONString(result);
//        }
//        //封装为Student，交给Service
//        Student student = new Student(id, name, email, password);
//        String serviceResult = registerService.Register(student);
//        if (serviceResult.equals("")) {
//            result.put("result", "true");
//        } else {
//            result.put("msg", serviceResult);
//        }
//        return JSON.toJSONString(result);
//    }
}
