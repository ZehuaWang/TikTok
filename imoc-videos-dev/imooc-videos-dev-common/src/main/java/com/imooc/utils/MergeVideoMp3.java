package com.imooc.utils;

import org.apache.tomcat.jni.Proc;

import java.util.ArrayList;
import java.util.List;

public class MergeVideoMp3 {

    private String ffmpeg;

    public MergeVideoMp3(String ffmpeg) {
        super();
        this.ffmpeg = ffmpeg;
    }

    public void convertor(String videoInputPath, String mp3InputPath, double seconds, String videoOutputPath) throws Exception {
        List<String> command = new ArrayList<>();
        command.add(ffmpeg);
        command.add("-i");
        command.add(mp3InputPath);
        command.add("-i");
        command.add(videoInputPath);
        command.add("-t");
        command.add(String.valueOf(seconds));
        command.add("-y");
        command.add(videoOutputPath);

        command.stream().forEach(c -> System.out.print(c));

        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();
    }

    public static void main(String[] args) throws Exception {
        MergeVideoMp3 mergeVideoMp3 = new MergeVideoMp3("/Users/apple/Desktop/scala/ffmpeg/ffmpeg/bin/ffmpeg");
        mergeVideoMp3.convertor("/Users/apple/Desktop/scala/ffmpeg/ffmpeg/bin/cat.mp4","/Users/apple/Desktop/scala/ffmpeg/ffmpeg/bin/pokerface.mp3",7,"/Users/apple/Desktop/scala/ffmpeg/ffmpeg/bin/catwithbgm.mp4");
    }

}
