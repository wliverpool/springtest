package com.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dao.UserFileDao;
import com.pojo.UserFile;
import com.util.DateTime;
import com.util.NumUtil;

@Service
public class UploadService {
	
	@Autowired
	private UserFileDao userFileDao;
	
	public String uploadImage(CommonsMultipartFile file,String uploadPath,String realUploadPath){
		InputStream is = null;
		OutputStream os =null;
		
		try {
			
			is = file.getInputStream();
			String des = realUploadPath + "/" + file.getOriginalFilename();
			os = new FileOutputStream(des);
			
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = is.read(buffer))>0){
				os.write(buffer);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(is!=null){
				try {
					is.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(os!=null){
				try {
					os.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
		return uploadPath+"/"+file.getOriginalFilename();
	}
	
	/**
	 * 保存上传文件信息,返回上传文件的保存路径
	 * @param file  上传文件
	 * @param clientIp  客户端ip
	 * @param realUploadPath  上传文件保存的目录
	 * @return  上传文件的保存路径
	 * @throws IOException
	 */
	public String uploadFile(MultipartFile file,String clientIp,String realUploadPath) throws IOException{
		Date baseDate = new Date();
		String fileName = DateTime.toDate("yyyyMMddHHmmss", baseDate);
		String orgFileName = file.getOriginalFilename();
		orgFileName = (orgFileName == null) ? "" : orgFileName;
		Pattern p = Pattern.compile("\\s|\t|\r|\n");
        Matcher m = p.matcher(orgFileName);
        orgFileName = m.replaceAll("_");
		String realFilePath = realUploadPath  + File.separator + "admin" + File.separator;
		if(!(new File(realFilePath).exists())){
			new File(realFilePath).mkdirs();
		}
		String bigRealFilePath = realFilePath  + File.separator + FilenameUtils.getBaseName(orgFileName).concat(".") + fileName.concat(".").concat(FilenameUtils.getExtension(orgFileName).toLowerCase());
		if (file.getSize() > 0) {
			File targetFile = new File(bigRealFilePath);
			file.transferTo(targetFile);//写入目标文件
		}
		//保存user file
		UserFile fileDTO = new UserFile(1, new Date(),clientIp,orgFileName, bigRealFilePath, 1);
		fileDTO.setFileSize(String.valueOf(NumUtil.divideNumber(file.getSize(), 1024*1024)));
		userFileDao.save(fileDTO);
		return bigRealFilePath;
	}
}
