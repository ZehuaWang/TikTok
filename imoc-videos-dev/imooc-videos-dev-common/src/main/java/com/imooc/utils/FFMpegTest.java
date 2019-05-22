package com.imooc.utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FFMpegTest {

    //The path of ffmpeg tool
    private String ffmpeg;

    public FFMpegTest(String ffmpeg) {
        super();
        this.ffmpeg = ffmpeg;
    }

    public void convert(String videoInputPath, String videoOutputPath) throws IOException {
        List<String> command = new ArrayList<>();
        command.add(ffmpeg);
        command.add("-i");
        command.add(videoInputPath);
        command.add(videoOutputPath);
        for(String c : command) {System.out.print(c);}
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.start();
    }

    public static void main(String[] args) throws IOException {
        FFMpegTest ffmpeg = new FFMpegTest("/Users/apple/Desktop/scala/ffmpeg/ffmpeg/bin/ffmpeg");
        ffmpeg.convert("/Users/apple/Desktop/scala/ffmpeg/ffmpeg/bin/cat.mp4","/Users/apple/Desktop/scala/ffmpeg/ffmpeg/bin/cat.avi");
    }

}
