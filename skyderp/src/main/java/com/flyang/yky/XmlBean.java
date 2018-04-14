package com.flyang.yky;

import java.util.ArrayList;
import java.util.Map;


public class XmlBean {
	
	//视图控制器
	private String controler;
	
	//需要的参数列表
	private ArrayList<Map<String, Object>> datas;
	
	public String getControler() {
		return controler;
	}
	public void setControler(String controler) {
		this.controler = controler;
	}
	
	//取消之前的 对xml里面的参数的判断
	public ArrayList<Map<String, Object>> getDatas() {
		return datas;
	}

	public void setDatas(ArrayList<Map<String, Object>> datas) {
		this.datas = datas;
	}

}
