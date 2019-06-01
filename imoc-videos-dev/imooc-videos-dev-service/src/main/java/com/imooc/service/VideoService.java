package com.imooc.service;

import com.imooc.pojo.Videos;
import com.imooc.utils.PageResult;

public interface VideoService {

    /**
     * 保存视频
     * @param videos
     */
    public String saveVideo(Videos videos);

    /**
     * 上传视频的封面截图
     * @param videoId
     * @param coverPath
     * @return
     */
    public void updateVideo(String videoId, String coverPath);


    /**
     * 分页查询视频列表
     * @param page
     * @param pageSize
     * @return
     */
    public PageResult getAllVideos(Integer page, Integer pageSize);

}
