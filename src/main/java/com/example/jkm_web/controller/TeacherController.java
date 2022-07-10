package com.example.jkm_web.controller;

import com.alibaba.fastjson.JSON;
import com.example.jkm_web.model.Teacher;
import com.example.jkm_web.service.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TeacherController {
    @Resource
    private RegisterService registerService;

    @RequestMapping("/tRegister")
    @ResponseBody
    public String tRegister(String id, String email, String name, String password) {
        Map<String, String> result = new HashMap<>();
        result.put("result", "false");
        result.put("msg", "");
        //数据校验(暂空)


        if (!result.get("msg").equals("")) {
            return JSON.toJSONString(result);
        }
        //封装为Teacher，交给Service
        Teacher teacher = new Teacher(id, name, email, password);
        String serviceResult = registerService.Register(teacher);
        if (serviceResult.equals("")) {
            result.put("result", "true");
        } else {
            result.put("msg", serviceResult);
        }
        return JSON.toJSONString(result);
    }
}
