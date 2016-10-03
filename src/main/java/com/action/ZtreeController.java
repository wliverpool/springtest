package com.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pojo.Ztree;
import com.util.JsonUtil;


@Controller
@RequestMapping("/ztree/")
public class ZtreeController extends AjaxAction{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value = "getNodes")
	public String getNodes(HttpServletResponse response) throws Exception {
		List<Ztree> list = new ArrayList<Ztree>();
		for(int i=1;i<=4;i++){
			Ztree ztree = new Ztree();
			ztree.setId("0"+i);
			ztree.setName("n"+i);
			if(i%2!=0){
				ztree.setIsParent(true);
			}else{
				ztree.setIsParent(false);
			}
			list.add(ztree);
		}
		return ajaxJson(JsonUtil.getJsonString4JavaArray(list.toArray()), response);
	}
	
	@RequestMapping(value = "getBigNodes")
	public String getBigNodes(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String count = request.getParameter("count");
		int c = Integer.parseInt(count);
		List<Ztree> list = new ArrayList<Ztree>();
		for(int i=1;i<=c;i++){
			Ztree ztree = new Ztree();
			ztree.setId(id+"_"+i);
			ztree.setName("tree"+id+"_"+i);
			list.add(ztree);
		}
		return ajaxJson(JsonUtil.getJsonString4JavaArray(list.toArray()), response);
	}

}
