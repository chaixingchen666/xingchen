/**
 * 
 */
package com.oracle.bean;

import java.util.Date;

import com.common.tool.Func;

/**
 * @author Administrator 最小查询日期校验
 */
public class MinDateValid {

	private String Mindate;
	// private String Accbegindate; // 账套启用日期

	// private String Currdate;
	private String errmess;

	// private Integer Maxday = 0; // 最大允许查询天数
	public MinDateValid(String mindate, String currdate, String accbegindate, int maxday) {// mindate:开始查询日期，currdate:当前日期，accbegindate：启用日期，maxday:最大允许查询天数
		errmess = "";
//		mindate = Func.strLeft(mindate, 10);

		if (mindate.compareTo(currdate) < 0 && maxday > 0) {
			// if (maxday > 0) {
			Date nowdate = Func.StrToDate(currdate, "yyyy-MM-dd");
			if (Func.DateToStr(Func.getDataAdd(nowdate, -maxday), "yyyy-MM-dd").compareTo(mindate) >= 0) // 要判断允许查询天数
			{
				mindate = Func.DateToStr(Func.getDataAdd(nowdate, -maxday + 1), "yyyy-MM-dd");
				errmess = "您只允许查询" + mindate + "以后的数据！";
				//	System.out.println("MinDateValid  "+errmess);
			}
		}
		if (mindate.compareTo(accbegindate) < 0)
			mindate = accbegindate; // 只能查询账套开始使用日期之后的数据

		Mindate = mindate;
	}

	// public void setAccbegindate(String ccbegindate) {
	// Accbegindate = ccbegindate;
	// }
	//
	// public String getAccbegindate() {
	// return Accbegindate;
	// }

	public String getMindate() {
		return Mindate;
	}

	// public void setMindate(String mindate) {
	// Mindate = mindate;
	// }

	// public String getCurrdate() {
	// return Currdate;
	// }
	//
	// public void setCurrdate(String currdate) {
	// Currdate = currdate;
	// }
	//
	public String getErrmess() {
		return errmess;
	}

	// public void setErrmess(String errmess) {
	// this.errmess = errmess;
	// }
	//
	// public Integer getMaxday() {
	// return maxday;
	// }
	//
	// public void setMaxday(Integer maxday) {
	// this.maxday = maxday;
	// }

}
