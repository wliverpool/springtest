package com.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.apache.commons.collections.CollectionUtils;

public class ValidationUtils {
	
	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	public static <T> ValidateResult validateEntity(T obj){
		ValidateResult result = new ValidateResult();
		Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
		if(!CollectionUtils.isEmpty(set)){
			result.setHasErrors(true);
			Map<String,String> errorMsgMap = new HashMap<>();
			for(ConstraintViolation<T> cv : set){
				errorMsgMap.put(cv.getPropertyPath().toString(), cv.getMessage());
			}
			result.setErrorMsgMap(errorMsgMap);
		}
		return result;
	}
	
	public static <T> ValidateResult validateEntity(T obj,String propertyName){
		ValidateResult result = new ValidateResult();
		Set<ConstraintViolation<T>> set = validator.validateProperty(obj, propertyName, Default.class);
		if(!CollectionUtils.isEmpty(set)){
			result.setHasErrors(true);
			Map<String,String> errorMsgMap = new HashMap<>();
			for(ConstraintViolation<T> cv : set){
				errorMsgMap.put(cv.getPropertyPath().toString(), cv.getMessage());
			}
			result.setErrorMsgMap(errorMsgMap);
		}
		return result;
	}

}
