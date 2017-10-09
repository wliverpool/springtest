package com.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.rpc.service.GenericService;

import wfm.dubboserver.service.TestService;

@Controller
@RequestMapping("/dubboTest/")
public class DubboController {
	
	@Autowired
	private TestService dubboTestService;
	@Autowired
	private GenericService genericService;
	
	
	@RequestMapping("add")
	@ResponseBody
	public String addInDubbo(HttpServletRequest request){
		String paramTest = request.getParameter("paramTest");
		return dubboTestService.add(paramTest);
	}
	
	
	@RequestMapping("/helloWorld")
	@ResponseBody
	public String helloWorldInDubbo(){
		return dubboTestService.helloWorld(new Date());
	}
	
	@RequestMapping("/genericService")
	@ResponseBody
	public String doGenericService(){
		Object result = genericService.$invoke("sayHello", new String[] { "java.lang.String" }, new Object[] { "World" });
		return result.toString();
	}

}
