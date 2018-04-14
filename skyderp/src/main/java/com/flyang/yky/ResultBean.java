package com.flyang.yky;

//错误的参数格式
public class ResultBean {
	 private int status;  //状态码
	 private String message; //当参数错误时 返回错误原因
	 private String errParam;  //当参数错误是 返回错误参数
	 private int httpCode;
	 
	public int getHttpCode() {
		return httpCode;
	}
	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrParam() {
		return errParam;
	}
	public void setErrParam(String errParam) {
		this.errParam = errParam;
	}
	 
	 
}
