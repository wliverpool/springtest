package com.action;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pojo.TestPojo;
import com.util.DateEditor;
import com.util.ValidateResult;
import com.util.ValidationUtils;

@Controller
@RequestMapping("/")
public class CustomDataTypeController {

	@InitBinder
	public void initData(WebDataBinder wdb) {
		// 设置当前controller中日期类型数据绑定时使用的editor
		wdb.registerCustomEditor(Date.class, new DateEditor());
	}

	@RequestMapping("dataTypeConvert")
	public ModelAndView convertDataType(@Valid TestPojo pojo,Date birthday, int age) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("showData");
		modelAndView.addObject("date", birthday);
		return modelAndView;
	}
	
	@RequestMapping("handworkValid")
	public ModelAndView handworkValid(TestPojo pojo){
		ModelAndView modelAndView = new ModelAndView();
		ValidateResult result = ValidationUtils.validateEntity(pojo);
		if(result.isHasErrors()){
			modelAndView.addObject("error", result.getErrorMsgMap());
		}
		modelAndView.setViewName("showData");
		modelAndView.addObject("date", pojo.getAddress());
		return modelAndView;
	}

}
