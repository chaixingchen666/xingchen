package com.flyang.yky;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class CheckParams {
	public static CheckParams getInstance = new CheckParams();
	
	public  ResultBean checkParams(XmlBean bean, Map<String, Object> data){
		if(bean == null || bean.getControler() == null){
			return null;
		}
		 
		for(int i =0; i< bean.getDatas().size(); i++){
			boolean isIsNecessary = false;
			int dateType = -1;
			String paraName = "";
	
			
			//获取当前Map里面对应的value
			for(Entry<String, Object> xmlEntry : bean.getDatas().get(i).entrySet()){
				String xmlKey = xmlEntry.getKey();
				if(xmlKey.equals("IsNecessary")){
					System.out.println("xml value ------>> " + xmlEntry.getValue());
					isIsNecessary = Boolean.valueOf(xmlEntry.getValue().toString());
				}else if(xmlKey.equals("DataType")){
					dateType = Integer.valueOf(xmlEntry.getValue().toString());;
				}else if(xmlKey.equals("ParaName")){
					paraName = xmlEntry.getValue().toString();
				}
			}
		
			//如果该参数是isIsNecessary 则去判断是否有该参数
			if(isIsNecessary){
				//是否有传必须传过来的参数标识
				boolean isExistNecessary = false;
				for (Entry<String, Object> entry : data.entrySet()) {  
					   // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
						String key = entry.getKey();
						Object value = entry.getValue();
						if(key.equals(paraName)){
							//检验数据格式是否正确
							if(!checkDateFormat(value, dateType)){
								return initCheckResult(3, paraName);
							}
							isExistNecessary = true;
							break;
						}
				}
				if(!isExistNecessary){
					return initCheckResult(2, paraName);
				}
			}else{
				//如果不是必须的 只判断数据格式是否正确
				for (Entry<String, Object> entry : data.entrySet()) {  
					   // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
						String key = entry.getKey();
						Object value = entry.getValue();
						if(key.equals(paraName)){
							//检验数据格式是否正确
							if(!checkDateFormat(value, dateType)){
								return initCheckResult(3, paraName);
							}
							break;
						}
				}
				
			}
		}
		
		//检测
		return checkParamIsExist(bean, data);
	}
	
	//判断本地xml是否有传过来的这个参数
	private ResultBean checkParamIsExist(XmlBean bean, Map<String, Object> data){
		for (Entry<String, Object> entry : data.entrySet()) {  
			   // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				String key = entry.getKey();
				boolean paramIsExist = false;
				for(int i =0; i< bean.getDatas().size(); i++){
					for(Entry<String, Object> xmlEntry : bean.getDatas().get(i).entrySet()){
						String xmlKey = xmlEntry.getKey();
						Object xmlValue = xmlEntry.getValue();
						System.out.println("xmlvalue ---->> " + xmlValue + " xmlKey =  " + xmlKey + " key = " + key);
						if(xmlValue.equals(key)){
							paramIsExist = true;
							break;
						}
					}
				}
				if(!paramIsExist){
					//return false;
					return initCheckResult(4, key);
				}
			  
			}
		
		return initCheckResult(0);
	}
	
	//检验传过来的参数数据格式是否正确
	private boolean checkDateFormat(Object object, int dataType){
		switch (dataType) {
		case 1:
			if(object instanceof String)
				return true;
			break;
		case 2:
			if(object instanceof Integer)
				return true;
			break;
		case 3:
			if(object instanceof BigDecimal)
				return true;
			break;
		case 4:
			if(object instanceof Boolean)
				return true;
			break;
		case 5:
			if(object instanceof UUID)
				return true;
			break;
		case 6:
			if(object instanceof Date)
				return true;
			break;
		default:
			break;
		}
		
		return false;
	}
	public ResultBean initCheckResult(int flag){
		return initCheckResult(flag, "");
	}
	
	//初始化参数检验结果的返回类   1，正确； 2，缺少参数， 3，数据格式错误 ,4 参数不存在, 5,action 不存在
	public ResultBean initCheckResult(int flag, String param){
		ResultBean result = new ResultBean();
		if(flag == 1){
			result.setStatus(1);
		}else if(flag == 2){
			result.setStatus(-1);
			result.setMessage("缺少参数");
			result.setErrParam(param);
		} else if(flag == 3){
			result.setStatus(-1);
			result.setMessage("参数格式错误");
			result.setErrParam(param);
		}else if(flag == 4){
			result.setStatus(-1);
			result.setMessage("参数不存在");
			result.setErrParam(param);
		}else if(flag == 5){
			result.setStatus(-1);
			result.setMessage("action不存在");
			result.setErrParam(param);
		}
		return result;
	}
}
