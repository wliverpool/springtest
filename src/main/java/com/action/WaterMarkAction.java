package com.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.service.AWTService;
import com.service.UploadService;
import com.util.WaterMarkPicInfo;

@Controller
@RequestMapping("/")
public class WaterMarkAction {
	
	private UploadService uploadService;
	private AWTService awtService;

	@RequestMapping(value="/waterTextMap",method=RequestMethod.POST)
	public ModelAndView waterTextMap(@RequestParam("image")CommonsMultipartFile[] files,HttpSession session) throws Exception{
		String uploadPath = "/images";
		String realUploadPath = session.getServletContext().getRealPath(uploadPath);
		List<WaterMarkPicInfo> infos = new ArrayList<WaterMarkPicInfo>();
		if(null!=files&&files.length>0){
			for(int i=0;i<files.length;i++){
				if(null!=files[i]&&!files[i].isEmpty()){
					String imageUrl = uploadService.uploadImage(files[i], uploadPath, realUploadPath);
					String waterMarkImageUrl = awtService.waterTextMark(files[i], uploadPath, realUploadPath);
					WaterMarkPicInfo info = new WaterMarkPicInfo();
					info.setImageOrginalUrl(imageUrl);
					info.setWaterMarkImageUrl(waterMarkImageUrl);
					infos.add(info);
				}
			}
		}
		
		ModelAndView ret = new ModelAndView();
		ret.addObject("infos",infos);
		ret.setViewName("showWaterMark");
		return ret;
	}
	
	@RequestMapping(value="/waterPicMap",method=RequestMethod.POST)
	public ModelAndView waterPicMap(@RequestParam("image")CommonsMultipartFile[] files,HttpSession session) throws Exception{
		String uploadPath = "/images";
		String realUploadPath = session.getServletContext().getRealPath(uploadPath);
		
		List<WaterMarkPicInfo> infos = new ArrayList<WaterMarkPicInfo>();
		if(null!=files&&files.length>0){
			for(int i=0;i<files.length;i++){
				if(null!=files[i]&&!files[i].isEmpty()){
					String imageUrl = uploadService.uploadImage(files[i], uploadPath, realUploadPath);
					String waterMarkImageUrl = awtService.waterPicMark(files[i], uploadPath, realUploadPath);
					WaterMarkPicInfo info = new WaterMarkPicInfo();
					info.setImageOrginalUrl(imageUrl);
					info.setWaterMarkImageUrl(waterMarkImageUrl);
					infos.add(info);
				}
			}
		}
		
		ModelAndView ret = new ModelAndView();
		ret.addObject("infos",infos);
		ret.setViewName("showWaterMark");
		return ret;
	}

	@Autowired
	public void setUploadService(UploadService uploadService) {
		this.uploadService = uploadService;
	}
	
	public UploadService getUploadService() {
		return uploadService;
	}
	
	public AWTService getAwtService() {
		return awtService;
	}

	@Autowired
	public void setAwtService(AWTService awtService) {
		this.awtService = awtService;
	}

}
