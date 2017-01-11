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

}
