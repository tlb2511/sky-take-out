package com.sky.common;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/admin/common")
@Api(tags = "公共接口")
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    @RequestMapping("/upload")
    @ApiOperation(value = "文件上传")
    public Result<String> upload(MultipartFile file){

        log.info("文件上传：{}",file);
        try {
            //原始文件名
            String originalFilename = file.getOriginalFilename();

            //获取后缀名
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            //生成UUID+后缀名
            String newName = UUID.randomUUID().toString() + extension;


            String fileName = aliOssUtil.upload(file.getBytes(), newName);
            return Result.success(fileName);
        } catch (IOException e) {
            log.error("文件上传失败：{}",e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }

}
