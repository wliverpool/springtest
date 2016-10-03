package com.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pojo.UserFile;
import com.service.HSSFExcelAdapter;
import com.service.IExcelAdapter;
import com.service.UserFileService;
import com.service.XSSFExcelAdapter;
import com.util.FileOperateUtil;
import com.util.ZipUtil;

/**
 * excel导出
 * @author 吴福明
 *
 */
@Controller
@RequestMapping("/export")
public class ExportExcelAction {
	
	@Autowired
	private UserFileService userFileService;
	private IExcelAdapter<UserFile> exportExcel;
	
	@RequestMapping(value = "/toXls")
	public String toXls(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserFile> list = userFileService.findList(map);
		exportExcel = new HSSFExcelAdapter();
		String downloadPath = request.getSession().getServletContext().getRealPath("download");
		String filePath = exportExcel.generateExcel(downloadPath,"上传文件列表", list);
		File file = new File(filePath);
		try {
			if(null!=file&&file.exists()){
				FileOperateUtil.download(request, response, "application/excel; charset=utf-8", file.getAbsolutePath(), file.getName());
			}
		} catch (IOException e) {
			// TODO: handle exception
		}
		return null;
	}
	
	@RequestMapping(value = "/toXlsx")
	public String toXlsx(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserFile> list = userFileService.findList(map);
		exportExcel = new XSSFExcelAdapter();
		String downloadPath = request.getSession().getServletContext().getRealPath("download");
		String filePath = exportExcel.generateExcel(downloadPath,"上传文件列表", list);
		File file = new File(filePath);
		try {
			if(null!=file&&file.exists()){
				FileOperateUtil.download(request, response, "application/excel; charset=utf-8", file.getAbsolutePath(), file.getName());
			}
		} catch (IOException e) {
			// TODO: handle exception
		}
		return null;
	}
	
	@RequestMapping(value = "/toZip")
	public String toZip(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserFile> list = userFileService.findList(map);
		exportExcel = new XSSFExcelAdapter();
		String downloadPath = request.getSession().getServletContext().getRealPath("download");
		String dir = downloadPath + File.separator+System.currentTimeMillis();
		File parentDir = new File(dir);
		if(!parentDir.exists()){
			parentDir.mkdirs();
		}
		exportExcel.generateExcel(dir,"上传文件列表", list);
		exportExcel = new HSSFExcelAdapter();
		exportExcel.generateExcel(dir,"上传文件列表", list);
		String zipFile = downloadPath + File.separator+System.currentTimeMillis()+".zip";
		ZipUtil zip = new ZipUtil(zipFile);
		zip.compress(dir);
		File downloadFile = new File(zipFile);
		try {
			if(null!=downloadFile&&downloadFile.exists()){
				FileOperateUtil.download(request, response, "application/zip; charset=utf-8", downloadFile.getAbsolutePath(), downloadFile.getName());
			}
		} catch (IOException e) {
			// TODO: handle exception
		}
		return null;
	}

}
