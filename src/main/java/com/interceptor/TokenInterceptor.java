package com.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.util.Token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * springmvc的拦截器用于拦截controller中的方法判断是否被@Token注解
 * 被@Token注解则在session生产token变量,在提交之后的controller方法删除对应token变量
 * @author 吴福明
 *
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Token annotation = method.getAnnotation(Token.class);
			if (annotation != null) {
				boolean needSaveSession = annotation.save();
				//是否需要生成token
				if (needSaveSession) {
					request.getSession(false).setAttribute("token", UUID.randomUUID().toString());
				}
				boolean needRemoveSession = annotation.remove();
				//是否需要验证token
				if (needRemoveSession) {
					if (isRepeatSubmit(request)) {
						throw new Exception("重复提交");
					}
					request.getSession(false).removeAttribute("token");
				}
			}
			return true;
		} else {
			return super.preHandle(request, response, handler);
		}
	}

	private boolean isRepeatSubmit(HttpServletRequest request) {
		String serverToken = (String) request.getSession(false).getAttribute("token");
		if (serverToken == null) {
			return true;
		}
		String clinetToken = request.getParameter("token");
		if (clinetToken == null) {
			return true;
		}
		if (!serverToken.equals(clinetToken)) {
			return true;
		}
		return false;
	}
}