package com.example.jkm_web.controller;

import com.example.jkm_web.model.RestMessage;
import com.example.jkm_web.model.Student;
import com.example.jkm_web.model.Teacher;
import com.example.jkm_web.service.AccountService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(tags = {"账号相关操作（登陆注册等）"})
@Controller
public class AccountController {

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Resource
    private AccountService registerService;

    @ApiOperation(value = "注册")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 500, message = "出现异常"),
            @ApiResponse(code = 501, message = "参数错误"),
    })
    @RequestMapping(value = "/Register", method = RequestMethod.POST)
    @ResponseBody
    public String Register(HttpServletRequest request, @ApiParam(name = "id", value = "学号/工号") @RequestParam("id") String id, @ApiParam(name = "email", value = "邮箱") @RequestParam("email") String email, @ApiParam(name = "name", value = "姓名") @RequestParam("name") String name, @ApiParam(name = "password", value = "密码") @RequestParam("password") String password, @ApiParam(name = "emailCode", value = "邮箱验证码") @RequestParam("emailCode") String emailCode, @ApiParam(name = "role", value = "student/teacher") @RequestParam("role") String role) {
        //获取session存取数据
        HttpSession session = request.getSession();
        //数据校验(暂空)


        //验证码
        if (!emailCode.equals(session.getAttribute("emailCode"))) {
            logger.error(role + "注册：{" + email + "}->邮件验证码错误");
            return new RestMessage(false, 501, "邮件验证码错误", "").toString();
        }
        //判断后，交给Service
        try {
            if (role.equals("student")) {
                Student student = new Student(id, name, email, password);
                registerService.Register(student);
                session.setAttribute("role", student);
                session.setAttribute("role", "student");
            } else if (role.equals("teacher")) {
                Teacher teacher = new Teacher(id, name, email, password);
                registerService.Register(teacher);
                session.setAttribute("role", teacher);
                session.setAttribute("role", "teacher");
            }
        } catch (Exception e) {
            logger.error(role + "注册：{" + email + "}->数据库写入异常" + e);
            return new RestMessage(false, 500, "数据库写入异常", "").toString();
        }
        logger.info(role + "注册：{" + email + "}->请求成功");
        return new RestMessage(true, 200, "请求成功", "").toString();
    }
}
