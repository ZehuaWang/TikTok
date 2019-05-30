package com.imooc.pojo.vo;

import java.util.Date;

public class VideosVO {
    private String id;
    private String userId;
    private String audioId;
    private String videoDesc;
    private String videoPath;
    private Float videoSeconds;
    private Integer videoWidth;
    private Integer videoHeight;
    private String coverPath;
    private Long likeCounts;
    private Integer status;
    private Date createTime;

    private String faceImage;
    private String nickname;

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String face_image) {
        this.faceImage = face_image;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


}
