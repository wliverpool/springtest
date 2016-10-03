package com.test;

import com.service.ConvertVideoService;

public class ConvertVideoTest {
	
public static void main(String args[]) {  
        
        try {  
            // 转换并截图  
            String filePath = "E:/[李香兰].[SUBPIG]Rikouran_SP-Part.2.rmvb";  
            String videofolder = "D:/convertvideo/other/"; // 别的格式视频的目录  
            String flvfolder = "D:/convertvideo/flv/"; // flv视频的目录  
            String ffmpegpath = "D:/convertvideo/ffmpeg.exe"; // ffmpeg.exe的目录  
            String mencoderpath = "D:/convertvideo/mencoder.exe"; // mencoder的目录  
            String videoRealPath = "D:/convertvideo/flv/"; // 截图的视频目录;  
            String imageRealPath = "D:/convertvideo/img/"; // 截图的存放目录  
            ConvertVideoService cv = new ConvertVideoService(videofolder,flvfolder,ffmpegpath,mencoderpath,videoRealPath,imageRealPath,filePath);  
            cv.beginConver();  
   
            // 仅截图  
            //cv.processImg();  
   
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

}
