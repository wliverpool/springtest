package com.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pojo.Page;
import com.service.QaReaderService;

/*import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;*/

//@Api(produces="application/json",tags={"富表格api接口"})
@Controller
@RequestMapping("/")
public class QaReadController {
	
	@Autowired
	private QaReaderService qaReaderServie;
	//swagger注解用于生产接口api信息
	//@ApiOperation(value="分页请求表格数据",httpMethod="POST",notes="分页请求表格数据")
	//@ApiImplicitParams({@ApiImplicitParam(name = "request", value = "http请求内容", required = true, dataType = "string"), @ApiImplicitParam(name = "response", value = "http响应", required = true, dataType = "string")})
	@ResponseBody
	@RequestMapping(value = "listQaReader",produces = {"application/json"})
	public Map<String, Object> listQaReader(HttpServletRequest request,HttpServletResponse response)throws IOException{
		String paramString = getBody(request);
		Page page = new Page();
		transferParam(page, paramString);
		qaReaderServie.queryQaReaderByPage(page);
		Map<String, Object> result = new HashMap<>();
		result.put("data", page.getDataRows());
		result.put("totals", page.getRecords());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		return result;
	}
	
	/**
	 * 从请求参数字符串中转换出请求的分页信息以及其他查询参数
	 * @param pageInfo  分页查询信息
	 * @param paramString    请求参数字符串
	 */
	public void transferParam(Page pageInfo,String paramString){
		if(StringUtils.isNotBlank(paramString)){
			String[] params = paramString.split("&");
			Map<String, Object> queryCondition = new HashMap<>();
			for(String param : params){
				if(StringUtils.isNotBlank(param)){
					String[] paramArray = param.split("=");
					if(null!=paramArray&&paramArray.length>1){
						if(StringUtils.isNotBlank(paramArray[0])&&("pSize".equals(paramArray[0]))){
							try {
								pageInfo.setRows(Integer.parseInt(paramArray[1]));
							} catch (Exception e) {
								pageInfo.setRows(10);
							}
						}else if(StringUtils.isNotBlank(paramArray[0])&&("cPage".equals(paramArray[0]))){
							try {
								pageInfo.setPage(Integer.parseInt(paramArray[1]));
							} catch (Exception e) {
								pageInfo.setPage(1);
							}
						}else{
							queryCondition.put(paramArray[0], paramArray[1]);
						}
					}
				}
			}
			pageInfo.setUserdata(queryCondition);
		}
	}
	
	/**
	 * 从request payload中获取请求内容
	 * @param request   http请求
	 * @return     request payload的数据字符串
	 * @throws IOException
	 */
	public String getBody(HttpServletRequest request) throws IOException {

	    String body = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    BufferedReader bufferedReader = null;

	    try {
	        InputStream inputStream = request.getInputStream();
	        if (inputStream != null) {
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
	            char[] charBuffer = new char[128];
	            int bytesRead = -1;
	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
	            }
	        } else {
	            stringBuilder.append("");
	        }
	    } catch (IOException ex) {
	        throw ex;
	    } finally {
	        if (bufferedReader != null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException ex) {
	                throw ex;
	            }
	        }
	    }

	    body = stringBuilder.toString();
	    return body;
	}

}
