package com.example.jkm_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class Test {

    @RequestMapping("/test")
    @ResponseBody
    public String test(HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("imageCode"));
        System.out.println(session.getAttribute("emailCode"));
        return "test";
    }
}
