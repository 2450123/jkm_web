package com.example.jkm_web.controller;

import com.example.jkm_web.service.AccountService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class TeacherController {
    @Resource
    private AccountService registerService;
//
//    @RequestMapping("/tRegister")
//    @ResponseBody
//    public String tRegister(String id, String email, String name, String password) {
//        Map<String, String> result = new HashMap<>();
//        result.put("result", "false");
//        result.put("msg", "");
//        //数据校验(暂空)
//
//
//        if (!result.get("msg").equals("")) {
//            return JSON.toJSONString(result);
//        }
//        //封装为Teacher，交给Service
//        Teacher teacher = new Teacher(id, name, email, password);
//        String serviceResult = registerService.Register(teacher);
//        if (serviceResult.equals("")) {
//            result.put("result", "true");
//        } else {
//            result.put("msg", serviceResult);
//        }
//        return JSON.toJSONString(result);
//    }
}
