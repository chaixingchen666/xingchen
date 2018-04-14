package com.flyang.entity;

/**
 * 交易查询提交Post数据给到API之后，API会返回JSON格式的数据，这个类用来装这些数据
 */
public class QueryMerchantResData {

	private String respCode = "";
	private String respMsg = "";
	private String merchantNo = "";
	private String status = "";
	private String auditStatus="";
	private String email = "";
	private String shortName = "";
	private String bizCategory = "";
	private String legalPerson = "";
	private String cardNo = "";
	private String mobileNo = "";
	private String province = "";
	private String city = "";
	private String address = "";
	private String bankName = "";
	private String bankAccountName = "";
	private String bankAccountNo = "";
	private String bankAccountType="";
	private String bankCardNo="";
	private String bankChannelNo="";
	private String aliPayTradeRate = "";
	private String aliD0Rate = "";
	private String wxPayTradeRate = "";
	private String wxD0Rate = "";
	private String remitFeeD0 = "";
	private String remitFeeT1 = "";
	private String remark = "";
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

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getBizCategory() {
		return bizCategory;
	}

	public void setBizCategory(String bizCategory) {
		this.bizCategory = bizCategory;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getAliPayTradeRate() {
		return aliPayTradeRate;
	}

	public void setAliPayTradeRate(String aliPayTradeRate) {
		this.aliPayTradeRate = aliPayTradeRate;
	}

	public String getAliD0Rate() {
		return aliD0Rate;
	}

	public void setAliD0Rate(String aliD0Rate) {
		this.aliD0Rate = aliD0Rate;
	}

	public String getWxPayTradeRate() {
		return wxPayTradeRate;
	}

	public void setWxPayTradeRate(String wxPayTradeRate) {
		this.wxPayTradeRate = wxPayTradeRate;
	}

	public String getWxD0Rate() {
		return wxD0Rate;
	}

	public void setWxD0Rate(String wxD0Rate) {
		this.wxD0Rate = wxD0Rate;
	}

	public String getRemitFeeD0() {
		return remitFeeD0;
	}

	public void setRemitFeeD0(String remitFeeD0) {
		this.remitFeeD0 = remitFeeD0;
	}

	public String getRemitFeeT1() {
		return remitFeeT1;
	}

	public void setRemitFeeT1(String remitFeeT1) {
		this.remitFeeT1 = remitFeeT1;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBankChannelNo() {
		return bankChannelNo;
	}

	public void setBankChannelNo(String bankChannelNo) {
		this.bankChannelNo = bankChannelNo;
	}

	@Override
	public String toString() {
		return "QueryOrderResData [respCode=" + respCode + ", respMsg=" + respMsg + ", merchantNo=" + merchantNo
				+ ", status=" + status + ", auditStatus=" + auditStatus + ", email=" + email + ", shortName="
				+ shortName + ", bizCategory=" + bizCategory + ", legalPerson=" + legalPerson + ", cardNo=" + cardNo
				+ ", mobileNo=" + mobileNo + ", province=" + province + ", city=" + city 
				+ ", address=" + address
				+ ", bankName=" + bankName
				+ ", bankAccountName=" + bankAccountName
				+ ", bankAccountNo=" + bankAccountNo
				+ ", bankAccountType=" + bankAccountType
				+ ", bankChannelNo=" + bankChannelNo
				+ ", bankCardNo=" + bankCardNo
				+ ", aliPayTradeRate=" + aliPayTradeRate
				+ ", aliD0Rate=" + aliD0Rate
				+ ", wxPayTradeRate=" + wxPayTradeRate
				+ ", wxD0Rate=" + wxD0Rate
				+ ", remitFeeD0=" + remitFeeD0
				+ ", remitFeeT1=" + remitFeeT1
				+ ", remark=" + remark
				+ ", randomStr=" + randomStr + ", signType=" + signType + ", sign=" + sign + "]";
	}
}
