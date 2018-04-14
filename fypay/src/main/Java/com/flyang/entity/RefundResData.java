package com.flyang.entity;

/**
 * 退款提交Post数据给到API之后，API会返回JSON格式的数据，这个类用来装这些数据
 */
public class RefundResData {

	private String respCode = "";
	private String respMsg = "";
	private String refundTime = "";
	private String randomStr = "";
	private String signType = "";
	private String sign = "";

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	


	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

	public String getRandomStr() {
		return randomStr;
	}

	public void setRandomStr(String randomStr) {
		this.randomStr = randomStr;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "RefundResData [respCode=" + respCode + ", respMsg=" + respMsg + ", refundTime=" + refundTime
				+ ", randomStr=" + randomStr + ", signType=" + signType + ", sign=" + sign + "]";
	}
}
