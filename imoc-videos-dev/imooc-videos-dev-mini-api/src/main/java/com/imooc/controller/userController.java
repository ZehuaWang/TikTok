package com.imooc.controller;

import com.imooc.service.UserService;
import com.imooc.utils.IMoocJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 用户相关业务controller
 */
@RestController
@Api(value = "User Related Business Logic Interface", tags = {"User Related Business Logic Interface"})
@RequestMapping("/user")
public class userController {

    @Autowired
    private UserService userService;

    /**
     * Interface for user face uploading
     * @param userId
     * @param files
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "User upload face", notes = "User upload fgce")
    @PostMapping("/uploadFace")
    public IMoocJSONResult uploadFace(String userId,
                                      @RequestParam("file") MultipartFile[] files) throws Exception {

        if(StringUtils.isEmpty(userId)) {return IMoocJSONResult.errorMsg("UserID is empty"); }

        //文件保存的命名空间
        String fileSpace = "/Users/apple/Desktop/scala/TikTok/imoc-user-file";

        //保存到数据库中的相对路径
        String uploadPathDB = "/" + userId + "/face";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;

        try {
            if (files != null && files.length > 0) {

                String fileName = files[0].getOriginalFilename();
                if (!StringUtils.isEmpty(fileName)) {

                    //文件上传的绝对路径
                    String finalFacePath = fileSpace + uploadPathDB + "/" + fileName;

                    //设置数据库保存的路径
                    uploadPathDB += ("/" + fileName);

                    File outFile = new File(finalFacePath);

                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    //文件输出
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files[0].getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);

                }
            } else {return IMoocJSONResult.errorMsg("Uploading error...");}
        } catch (Exception e) {
            e.printStackTrace();
            return IMoocJSONResult.errorMsg("Uploading error...");
        } finally {
            if(fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }


        return IMoocJSONResult.ok();
    }


}
