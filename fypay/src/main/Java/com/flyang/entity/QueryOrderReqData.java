package com.flyang.entity;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.flyang.utils.ApiUtil;
import com.flyang.utils.MD5Util;

/**
 * 请求交易查询API需要提交的数据
 */
public class QueryOrderReqData {

    //每个字段具体的意思请查看API文档
	private String merchantNo = "";
	private String orderNo = "";
	private String randomStr = "";
	private String signType = "";
	private String sign = "";
	
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	public QueryOrderReqData(String merchantNo, String md5Key, String orderNo) throws IOException {
		this.merchantNo = merchantNo;
		this.orderNo = orderNo;
		this.randomStr = ApiUtil.genRandomStr();
		this.signType = "MD5";
		this.sign = MD5Util.getSign(toMap(), md5Key);
	}
	
	public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                    map.put(field.getName(), (String) obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
	
	@Override
	public String toString() {
		return "QueryOrderReqData [merchantNo=" + merchantNo + ", orderNo=" + orderNo + ", randomStr=" + randomStr + ", signType="
				+ signType + ", sign=" + sign + "]";
	}

}
