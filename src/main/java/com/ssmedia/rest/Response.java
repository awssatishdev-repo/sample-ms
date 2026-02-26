package com.ssmedia.rest;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Response<T> {
	
	public T response;
	public Integer code;
	public String message;
	
	
	
	public T getResponse() {
		return response;
	}
	public void setResponse(T response) {
		this.response = response;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Response(T response, Integer code, String message) {
		super();
		this.response = response;
		this.code = code;
		this.message = message;
	}
	public Response() {
		// TODO Auto-generated constructor stub
	}
	
}
