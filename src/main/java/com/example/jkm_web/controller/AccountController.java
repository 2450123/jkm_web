package com.example.jkm_web.controller;

import com.alibaba.fastjson.JSON;
import com.example.jkm_web.model.RestMessage;
import com.example.jkm_web.model.Student;
import com.example.jkm_web.model.Teacher;
import com.example.jkm_web.model.User;
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
    private AccountService accountService;

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
                accountService.Register(student);
                session.setAttribute("role", student);
                session.setAttribute("role", "student");
            } else if (role.equals("teacher")) {
                Teacher teacher = new Teacher(id, name, email, password);
                accountService.Register(teacher);
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

    @ApiOperation(value = "重置密码",notes = "返回的data数据为用户信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 500, message = "出现异常"),
            @ApiResponse(code = 501, message = "参数错误"),
    })
    @RequestMapping(value = "/Reset", method = RequestMethod.POST)
    @ResponseBody
    public String Reset(HttpServletRequest request, @ApiParam(name = "id", value = "学号/工号") @RequestParam("id") String id, @ApiParam(name = "email", value = "邮箱") @RequestParam("email") String email, @ApiParam(name = "password", value = "密码") @RequestParam("password") String password, @ApiParam(name = "emailCode", value = "邮箱验证码") @RequestParam("emailCode") String emailCode, @ApiParam(name = "role", value = "student/teacher") @RequestParam("role") String role) {
        //获取session存取数据
        HttpSession session = request.getSession();
        //数据校验(暂空)


        //验证码
        if (!emailCode.equals(session.getAttribute("emailCode"))) {
            logger.info(emailCode + ";" + session.getAttribute("emailCode"));
            logger.error(role + "重置：{" + email + "}->邮件验证码错误");
            return new RestMessage(false, 501, "邮件验证码错误", "").toString();
        }
        //判断后，交给Service
        try {
            if (role.equals("student")) {
                accountService.Reset(id, password, role);
            } else if (role.equals("teacher")) {
                accountService.Reset(id, password, role);
            }
        } catch (Exception e) {
            logger.error(role + "重置：{" + email + "}->数据库更新异常" + e);
            return new RestMessage(false, 500, "数据库更新异常", "").toString();
        }
        logger.info(role + "重置：{" + email + "}->请求成功");
        return new RestMessage(true, 200, "请求成功", "").toString();
    }

    @ApiOperation(value = "登录")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 500, message = "出现异常"),
            @ApiResponse(code = 501, message = "参数错误"),
            @ApiResponse(code = 502, message = "请求失败"),
    })
    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    @ResponseBody
    public String Register(HttpServletRequest request, @ApiParam(name = "id", value = "学号/工号") @RequestParam("id") String id, @ApiParam(name = "password", value = "密码") @RequestParam("password") String password, @ApiParam(name = "role", value = "student/teacher") @RequestParam("role") String role) {
        //获取session存取数据
        HttpSession session = request.getSession();
        //数据校验(暂空)


        //登录
        try {
            User user = accountService.queryUserById(id,role);
            if (password.equals(user.getPassword())){
                session.setAttribute("role", user);
                session.setAttribute("role", role);
                logger.info(role + "登录：{" + id + "}->请求成功");
                return new RestMessage(true, 200, "请求成功", JSON.toJSON(user)).toString();
            }else {
                logger.info(role + "登录：{" + id + "}->请求失败");
                return new RestMessage(false, 502, "请求失败", "").toString();
            }
        } catch (Exception e) {
            logger.error(role + "登录：{" + id + "}->数据库查询异常" + e);
            return new RestMessage(false, 500, "数据库查询异常", "").toString();
        }
    }

}