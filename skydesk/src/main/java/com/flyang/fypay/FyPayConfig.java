package com.flyang.fypay;

import tools.DataBase;

public class FyPayConfig {
	public static final String FYPAY_URL = DataBase.CONFIGOBJ.getString("LocalIP")+"/fypay/fypayapi";
//	public static final String FYPAY_URL = "http://localhost:8080/fypay/fypayapi";
//	public static final String FYPAY_URL = "http://win.skydispark.com/fypay/fypayapi";
}
