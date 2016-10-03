package com.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ThumbnailService {

	public static final int WIDTH = 100;
	public static final int HEIGHT = 100;
	
	public String thumbnail(CommonsMultipartFile orignal,String uploadPath,String realUploadPath){
		
		try {
			String des = realUploadPath + "/thum_" + orignal.getOriginalFilename();
			Thumbnails.of(orignal.getInputStream()).size(WIDTH, HEIGHT).toFile(des);;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return uploadPath + "/thum_" + orignal.getOriginalFilename();
	}
	
}
