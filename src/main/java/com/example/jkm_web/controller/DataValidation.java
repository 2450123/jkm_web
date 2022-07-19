package com.example.jkm_web.controller;

import com.example.jkm_web.model.RestMessage;
import com.example.jkm_web.service.AccountService;
import com.example.jkm_web.service.StudentService;
import com.example.jkm_web.service.TeacherService;
import com.example.jkm_web.util.EmailUtil;
import com.example.jkm_web.util.VerificationCodeUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 数据校验
 */
@Api(tags = {"数据验证校验", "验证码获取"})
@Controller
public class DataValidation {

    private final static Logger logger = LoggerFactory.getLogger(DataValidation.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private EmailUtil emailUtil;
    @Resource
    private TeacherService teacherService;
    @Resource
    private StudentService studentService;
    @Resource
    private AccountService accountService;

    @Value("${ip}")
    private String ip;
    @Value("${email-max-life-time}")
    private Integer emailLifeTime;
    @Value("${image-code-length}")
    private Integer imageCodeLength;

    /**
     * 生成图片验证码
     * 返回图片字节流
     */
    @ApiOperation(value = "获取图片验证码字节流")
    @RequestMapping(value = "/imageCode", method = RequestMethod.GET)
    @ResponseBody
    public void imageCode(HttpServletRequest request, HttpServletResponse response) {
        String imageCode = VerificationCodeUtil.getVerificationCode(imageCodeLength);
        //验证码保存在session中
        HttpSession session = request.getSession();
        session.setAttribute("imageCode", imageCode);
        //设置返回的内容
        response.setContentType("img/jpeg");
        //浏览器不缓存响应内容--验证码图片，点一次就要刷新一次，所以不能有缓存出现
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        //设置验证码失效时间
        response.setDateHeader("Expires", 0);
        //以字节流发过去，交给img的src属性去解析即可
        try {
            ImageIO.write(VerificationCodeUtil.createImage(imageCode), "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送邮件验证码
     * @param email 发往的邮箱地址
     */
    @ApiOperation(value = "发送验证码邮件")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 500, message = "出现异常"),
            @ApiResponse(code = 501, message = "参数错误"),
    })
    @RequestMapping(value = "/sendEmailCode", method = RequestMethod.POST)
    @ResponseBody
    public String sendEmailCode(HttpServletRequest request, @ApiParam(name = "email", value = "发往的邮箱地址") @RequestParam("email") String email) {
        //数据校验


        //获取session存取数据
        HttpSession session = request.getSession();
        //发送邮件
        try {
            String emailCode = accountService.sendEmailCode(email);
            logger.info("发送邮件成功:->" + email);
            //记录验证码到session
            session.setAttribute("emailCode", emailCode);
            //设置过期时间
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    session.removeAttribute("emailCode");
                }
            };
            timer.schedule(task,emailLifeTime*60*1000);
        } catch (Exception e) {
            logger.info("发送邮件失败:->" + email);
            return new RestMessage(false, 500, "邮件发送异常", "").toString();
        }
        return new RestMessage(true, 200, "请求成功", "").toString();
    }

    /*
    不出所料的重构了，整个废弃。233
     */

    /*
    emailActivationCode这个函数写的并不好，中间很大一段都应该创建一个Service来处理。
    Controller层和Service层还是应该分清楚一些，方便以后排查。
    这段后期可能重构
    越写越乱了，焯
     */
//    @RequestMapping("/emailActivationCode")
//    public String emailActivationCode(String code, String email, HttpServletRequest request, HttpServletResponse response) {
//        Object msg = "激活失败,请稍后重试";
//        String selectCode = (String) stringRedisTemplate.opsForHash().get(email, "code");
//        if (code.equals(selectCode)) {
//            //读取数据写入数据库
//            Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(email);
//            if (map.get("classId") == null) {
//                Teacher teacher = new Teacher((String) map.get("id"), (String) map.get("name"), email, (String) map.get("password"));
//                try{
//                    teacherService.addTeacher(teacher);
//                    msg = "激活成功";
//                    //激活成功，注册状态改为在线状态。重新设置过期时间，以及状态修改。
//                    stringRedisTemplate.expire(email, onlineTime, TimeUnit.MINUTES);
//                    stringRedisTemplate.opsForHash().delete(email, "code");
//                    stringRedisTemplate.opsForHash().put(email, "online", "1");
//                    //设置cookie，存活时间同上。用于自动登录。
//                    Cookie cookie = new Cookie("email", email);
//                    cookie.setPath("/");
//                    cookie.setMaxAge(onlineTime * 60);
//                    response.addCookie(cookie);
//                    //发送注册成功邮件
//                    //读取模板文件
//                    String filePath = "templates/registerSuccessHtml.html";
//                    String html = EmailUtil.readHtmlToString(filePath);
//                    //写入参数
//                    Document document = Jsoup.parse(html);
//                    document.getElementById("userId").html((String) stringRedisTemplate.opsForHash().get(email, "name"));
//                    emailUtil.sendEmailActivation(email, document.toString());
//                }catch (Exception e){
//                    logger.info("注册过程中，添加Teacher失败：\n"+ e);
//                }
//            }else{
//                Student student = new Student((String) map.get("id"), (String) map.get("name"), email, (String) map.get("password"));
//                try{
//                    studentService.addStudent(student);
//                    msg = "激活成功";
//                    //激活成功，注册状态改为在线状态。重新设置过期时间，以及状态修改。
//                    stringRedisTemplate.expire(email, onlineTime, TimeUnit.MINUTES);
//                    stringRedisTemplate.opsForHash().delete(email, "code");
//                    stringRedisTemplate.opsForHash().put(email, "online", "1");
//                    //设置cookie，存活时间同上。用于自动登录。
//                    Cookie cookie = new Cookie("email", email);
//                    cookie.setPath("/");
//                    cookie.setMaxAge(onlineTime * 60);
//                    response.addCookie(cookie);
//                    //发送注册成功邮件
//                    //读取模板文件
//                    String filePath = "templates/registerSuccessHtml.html";
//                    String html = EmailUtil.readHtmlToString(filePath);
//                    //写入参数
//                    Document document = Jsoup.parse(html);
//                    document.getElementById("userId").html((String) stringRedisTemplate.opsForHash().get(email, "name"));
//                    emailUtil.sendEmailActivation(email, document.toString());
//                }catch (Exception e){
//                    logger.info("注册过程中，添加Student失败：\n"+ e);
//                }
//            }
//        }
//        request.setAttribute("msg", msg);
//        return "index";
//    }


}
