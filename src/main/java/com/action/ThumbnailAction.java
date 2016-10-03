package com.action;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.service.AWTService;
import com.service.ThumbnailService;
import com.service.UploadService;

@Controller
@RequestMapping("/")
public class ThumbnailAction {
	
	private UploadService uploadService;
	private ThumbnailService thumbnailService;
	private AWTService awtService;
	
	@RequestMapping(value="/thumbnail",method=RequestMethod.POST)
	public ModelAndView thumbnail(@RequestParam("image")CommonsMultipartFile file,HttpSession session) throws Exception{
		String uploadPath = "/images";
		String realUploadPath = session.getServletContext().getRealPath(uploadPath);
		
		String imageUrl = uploadService.uploadImage(file, uploadPath, realUploadPath);
		String thumImageUrl = thumbnailService.thumbnail(file, uploadPath, realUploadPath);
		
		ModelAndView ret = new ModelAndView();
		ret.addObject("imageUrl",imageUrl);
		ret.addObject("thumbImageUrl",thumImageUrl);
		ret.setViewName("thumbnail");
		
		return ret;
	}

	public UploadService getUploadService() {
		return uploadService;
	}

	@Autowired
	public void setUploadService(UploadService uploadService) {
		this.uploadService = uploadService;
	}

	public ThumbnailService getThumbnailService() {
		return thumbnailService;
	}

	@Autowired
	public void setThumbnailService(ThumbnailService thumbnailService) {
		this.thumbnailService = thumbnailService;
	}

	public AWTService getAwtService() {
		return awtService;
	}

	@Autowired
	public void setAwtService(AWTService awtService) {
		this.awtService = awtService;
	}
	
}
