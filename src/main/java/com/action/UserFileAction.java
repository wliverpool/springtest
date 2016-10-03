package com.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pojo.Page;
import com.pojo.Progress;
import com.pojo.UserFile;
import com.service.HSSFExcelAdapter;
import com.service.IExcelAdapter;
import com.service.UploadService;
import com.service.UserFileService;
import com.service.XSSFExcelAdapter;
import com.util.ExcelUtil;
import com.util.FileOperateUtil;
import com.util.IpTool;
import com.util.JsonUtil;
import com.util.StringUtil;

/**
 * 
 * 功能描述： 用户文件上传下载和文件列表<br>
 * @author 吴福明
 */
@Controller
@RequestMapping("/userFile")
public class UserFileAction extends AjaxAction{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserFileService userFileService;

    @Autowired
	private UploadService uploadService;
    private IExcelAdapter<UserFile> adapter;
	
	@RequestMapping(value = "/indexPage")
	public String indexPage(HttpServletRequest request) throws Exception {
		//文件列表
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", 1);
		String uploadType = request.getParameter("uploadType");
		String fileName = request.getParameter("fileName");
		String page = request.getParameter("page");
		if(StringUtil.isNotEmpty(uploadType)){
			map.put("upload_type", Integer.parseInt(uploadType));
			request.setAttribute("uploadType", uploadType);
		}
		if(StringUtil.isNotEmpty(fileName)){
			map.put("file_name", fileName);
			request.setAttribute("fileName", fileName);
		}
		int pageNo = 1;
		if(StringUtil.isNotEmpty(page)){
			try {
				pageNo = Integer.parseInt(page);
			} catch (NumberFormatException e) {
				
			}
		}
		Page pageInfo = userFileService.findPage(map,pageNo);
		request.setAttribute("lst_file", pageInfo);
		return "indexUploadFileList";
	}
	
	/**文件下载**/
    @RequestMapping("download")
    public String download(HttpServletRequest request, HttpServletResponse response) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
    		String fileId = request.getParameter("fileId");
    		if(StringUtil.isEmpty(fileId)){
    			map.put("status", "error");
    			map.put("message", "下载错误");
    			return ajaxJson(JsonUtil.getJsonString4JavaPOJO(map), response);
    		}
    		Long Id = 0L;
    		try {
    			Id = Long.parseLong(fileId);
			} catch (Exception e) {
				
			}
    		UserFile file = userFileService.findFileById(Id);
    		if(null!=file){
    			FileOperateUtil.download(request, response, "application/octet-stream; charset=utf-8", file.getFilePath(), file.getFileName());
    		}
			return null;
		} catch (IOException e) {
			logger.error("文件下载出错");
			map.put("status", "error");
			map.put("message", "下载错误");
		}
        return ajaxJson(JsonUtil.getJsonString4JavaPOJO(map), response);
    }

    /**获取文件大小**/
    @RequestMapping(value = "/getfilesize")
	@ResponseBody
	public String getFileSize(HttpServletRequest request) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	String fileId = request.getParameter("fileId");
		if(StringUtil.isEmpty(fileId)){
			map.put("status", "error");
			map.put("message", "下载错误");
			return (new Long(0L)).toString();
		}
		Long Id = 0L;
		try {
			Id = Long.parseLong(fileId);
		} catch (Exception e) {
			
		}
		UserFile file = userFileService.findFileById(Id);
    	if(null!=file){
        	Long fileLength = new File(file.getFilePath()).length();
        	return fileLength.toString();
    	}
    	return (new Long(0L)).toString();
	}
	
	@RequestMapping(value = "/upfile/progress", method = RequestMethod.POST )
	@ResponseBody
	public String initCreateInfo(HttpServletRequest request) {
		Progress status = (Progress) request.getSession().getAttribute("upload_ps");
		if(status==null){
			return "{}";
		}
		return status.toString();
	}
	
	/**
	 * 上传文件
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> result = new HashMap<String, Object>();
		String folder = "";
		try {
			folder = request.getSession().getServletContext().getRealPath("/uploads");
		} catch (Exception ex) {
			result.put("message", "获取folder失败");
			return ajaxHtml(JsonUtil.getJsonString4JavaPOJO(result), response);
		}
		if (StringUtil.isEmpty(folder)) {// step-1 获得文件夹
			response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
			if (!result.containsKey("message")) {
				result.put("message", "处理失败");
			}
			return ajaxJson(JsonUtil.getJsonString4JavaPOJO(result), response);
		}
		if (handler(request, response, result, folder)) {
			result.put("status", "success");
			result.put("message", "上传成功");
		}
		return ajaxHtml(JsonUtil.getJsonString4JavaPOJO(result), response);
	}
	
	/**
	 * 处理文件上传
	 */
	public boolean handler(MultipartHttpServletRequest request, HttpServletResponse response, Map<String, Object> result, String folder) throws IOException{
		MultipartFile file = request.getFile("file");
		if (file == null) {
			return getError("文件内容为空", HttpStatus.UNPROCESSABLE_ENTITY, result, response, null);
		}
		String clientIp = IpTool.getClientAddress(request);
		String filePath = uploadService.uploadFile(file,clientIp,folder);
		if(null!=filePath&&!"".equals(filePath)&&ExcelUtil.isExcel2003(filePath)){
			adapter = new HSSFExcelAdapter();
			List<UserFile> list = adapter.readFromExcel(filePath);
			if(null!=list&&list.size()>0){
				for(UserFile userFile : list){
					System.out.println(userFile.getFileName());
					System.out.println(userFile.getFileSize());
					System.out.println(userFile.getId());
					System.out.println(userFile.getUserId());
				}
			}
		}
		if(null!=filePath&&!"".equals(filePath)&&ExcelUtil.isExcel2007(filePath)){
			adapter = new XSSFExcelAdapter();
			List<UserFile> list = adapter.readFromExcel(filePath);
			if(null!=list&&list.size()>0){
				for(UserFile userFile : list){
					System.out.println(userFile.getFileName());
					System.out.println(userFile.getFileSize());
					System.out.println(userFile.getId());
					System.out.println(userFile.getUserId());
				}
			}
		}
		return true;
	}
	
	boolean getError(String message, HttpStatus status, Map<String, Object> result, HttpServletResponse response, Exception ex) {
		response.setStatus(status.value());
		result.put("message", message);
		LOG.warn(message, ex);
		return false;
	}

}
