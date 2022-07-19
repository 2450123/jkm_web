package com.example.jkm_web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Api(tags = "上传相关操作")
@Controller
public class UploadController {

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);


    @ApiOperation(value = "上传健康码图片")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 500, message = "出现异常"),
            @ApiResponse(code = 501, message = "参数错误"),
    })
    @RequestMapping(value = "/UploadImage", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(MultipartFile image){
        //test
        try {
            final String imagePathRoot = "E:\\图片\\test-image\\";
            File file = new File(imagePathRoot);
            if (!file.exists()) {
                file.mkdirs();
            }
            String fileName = image.getOriginalFilename();
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String imageFilePath = imagePathRoot + uuid + fileType;
            image.transferTo(new File(imageFilePath));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "ok";
    }
}
