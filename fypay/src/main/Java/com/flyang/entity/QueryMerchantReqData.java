package com.flyang.entity;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.flyang.utils.ApiUtil;
import com.flyang.utils.MD5Util;

/**
 * 请求商户查询API需要提交的数据
 */
public class QueryMerchantReqData {

    //每个字段具体的意思请查看API文档
	private String agentNo = "";
	private String merchantNo = "";
	private String randomStr = "";
	private String signType = "";
	private String sign = "";
	
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
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
	public QueryMerchantReqData(String agentNo,String merchantNo, String md5Key) throws IOException {
		this.agentNo = agentNo;
		this.merchantNo = merchantNo;
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
        System.out.println(map);
        return map;
    }
	
	@Override
	public String toString() {
		return "QueryOrderReqData [merchantNo=" + merchantNo  + ", randomStr=" + randomStr + ", signType="
				+ signType + ", sign=" + sign + "]";
	}

}
