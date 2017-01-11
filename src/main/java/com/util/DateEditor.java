package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.propertyeditors.PropertiesEditor;


/**
 * springmvc日期类型转换器
 * @author 吴福明
 *
 */
public class DateEditor extends PropertiesEditor {
	
	@Override
	public void setAsText(String source) throws IllegalArgumentException{
		//设置数据如何从文本格式转换成需要的格式
		SimpleDateFormat sdf = getDate(source);
		try {
			//调用setValue方法设置文本数据最后转换成的内容
			setValue(sdf.parse(source));
		} catch (ParseException e) {
			// TODO: handle exception
		}
	}
	
	private SimpleDateFormat getDate(String source){
		SimpleDateFormat sdf = null;
		//根据正则表达式匹配转换不同格式的日期数据
		if(Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", source)){
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}else if(Pattern.matches("^\\d{4}/\\d{2}/\\d{2}$", source)){
			sdf = new SimpleDateFormat("yyyy/MM/dd");
		}else if(Pattern.matches("^\\d{4}\\d{2}\\d{2}$", source)){
			sdf = new SimpleDateFormat("yyyyMMdd");
		}else{
			throw new TypeMismatchException("", Date.class);
		}
		return sdf;
	}

}
