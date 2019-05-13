package com.imooc.controller;

import com.imooc.service.BgmService;
import com.imooc.utils.IMoocJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Back Ground Music Business Logic Interface", tags = {"Bgm Controller"})
@RequestMapping("/bgm")
public class BgmController {

    @Autowired
    private BgmService bgmService;

    @ApiOperation(value = "Get the list of background music", notes = "Interface for get the back ground music")
    @PostMapping("/list")
    public IMoocJSONResult list() {
        return IMoocJSONResult.ok(bgmService.queryBgmList());
    }

}
