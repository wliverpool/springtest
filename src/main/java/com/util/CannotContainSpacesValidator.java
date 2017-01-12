package com.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义验证器类,验证String属性中不能有空格
 * 
 * @author 吴福明
 *
 */
public class CannotContainSpacesValidator implements ConstraintValidator<CannotContainSpaces, String>{
	
	private int len;

	/**
	 * 初始参数,获取注解中length的值
	 */
	@Override
	public void initialize(CannotContainSpaces cannotContainSpaces) {
		this.len = cannotContainSpaces.length();
	}

	@Override
	public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
		if (str != null) {
			if (str.indexOf(" ") < 0) {
				return true;
			}
		} else {
			constraintValidatorContext.disableDefaultConstraintViolation();// 禁用默认的message的值
			// 重新添加错误提示语句
			constraintValidatorContext.buildConstraintViolationWithTemplate("字符串不能为空").addConstraintViolation();
		}
		return false;
	}
}
