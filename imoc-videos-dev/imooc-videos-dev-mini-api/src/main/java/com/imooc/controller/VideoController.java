package com.imooc.controller;

import com.imooc.pojo.Bgm;
import com.imooc.utils.IMoocJSONResult;
import io.swagger.annotations.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@RestController
@Api(value = "Video Related Business Logic Interface", tags = {"Video Related Business Logic Controller"})
@RequestMapping("/video")
public class VideoController {

    @ApiOperation(value = "upload video", notes = "interface for video upload")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", required = true),
            @ApiImplicitParam(name = "bgmId", required = true),
            @ApiImplicitParam(name = "videoSeconds", required = true),
            @ApiImplicitParam(name = "videoWidth", required = true),
            @ApiImplicitParam(name = "videoHeight", required = true),
            @ApiImplicitParam(name = "description", required = false)
    })
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    public IMoocJSONResult upload(String userId, String bgmId, double videoSeconds, int videoWidth,
                                  int videoHeight, String desc, @ApiParam(value = "Short Video", required = true) MultipartFile files) throws Exception {

        if(StringUtils.isEmpty(userId)) {return IMoocJSONResult.errorMsg("User Id can not be empty");}

        //文件保存的命名空间
        String fileSpace = "/Users/apple/Desktop/scala/TikTok/imoc-user-file";

        //保存到数据库中的相对路径
        String uploadPathDB = "/" + userId + "/video";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;

        try{

            if(files != null) {
                String fileName = files.getOriginalFilename();
                if(!StringUtils.isEmpty(fileName)) {
                    //文件上传的最终保存的路径
                    String finalVideoPath = fileSpace+uploadPathDB+"/"+fileName;

                    //设置数据库的保存的路径
                    uploadPathDB += ("/" + fileName);

                    File outFile = new File(finalVideoPath);

                    if(outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files.getInputStream();
                    org.apache.commons.io.IOUtils.copy(inputStream,fileOutputStream);

                }

            } else {
                return IMoocJSONResult.errorMsg("Error when upload...");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return IMoocJSONResult.errorMsg("Error when upload");
        } finally {
            if(fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        //判断bgm的id是否为空 如果不为空 需要进行音频与视频的合并 查询bgm的信息 并且合并视频 生成新的视频
        if(!StringUtils.isEmpty(bgmId)) {
            //从数据库中取出对应的bgm
        }

        return IMoocJSONResult.ok();
    }
}
