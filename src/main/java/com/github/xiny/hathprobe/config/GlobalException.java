package com.github.xiny.hathprobe.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.github.xiny.hathprobe.domain.AjaxJson;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常拦截
 * <p> @ControllerAdvice 可指定包前缀，例如：(basePackages = "com.pj.controller.admin")
 * @author kong
 *
 */
@RestControllerAdvice
public class GlobalException {


	// 捕捉Springboot参数异常
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public Object handleRequestException(MissingServletRequestParameterException e) {
		return AjaxJson.getError( "参数" + e.getParameterName() + "缺失");
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Object handleRequestException(String method ) {
		return AjaxJson.getError("不支持"+ method+"方法");
	}

	/** 全局异常拦截  */
	@ResponseBody
	@ExceptionHandler(value =Exception.class)
	public AjaxJson handlerException(Exception e) {

    	// 记录日志信息
    	AjaxJson aj = null;

		e.printStackTrace();

		// ------------- 判断异常类型，提供个性化提示信息

    	// 如果是未登录异常
		if(e instanceof NotLoginException){
			aj = AjaxJson.getNotLogin();
		}
		// 如果是权限异常
		else if(e instanceof NotPermissionException) {
			NotPermissionException ee = (NotPermissionException) e;
			aj = AjaxJson.getNotJur(ee.getMessage());
		}
		// 普通异常输出：500 + 异常信息
		else {
			aj = AjaxJson.getError(e.getMessage());
		}
		SaHolder.getResponse().setStatus(aj.getCode());
		return aj;
	}

}
