/**
 * 
 */
package com.oracle.bean;

import com.comdot.data.Table;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.QueryParam;

/**
 * @author Administrator 查询模板
 *
 */
public class vTotalModel {

	private Long Accid;// 账户id
	private Long Userid;// 用户id
	private String Serverdatetime = "";// 服务器当前时间,-------------必传
	private String Accbegindate = "";// 账套启用日期
	private String Mindate;
	private String Maxdate;

	private String Fieldlist = ""; // 要显示的项目列表
	private String Grouplist = ""; // 分组的项目列表
	private String Sortlist = ""; // 排序的项目列表
	private String Sumlist = ""; // 要求和的项目列表
	private String Calcfield = ""; // 要求和的项目列表

	private String errmess;

	public String getErrmess() {
		return errmess;
	}

	public void setServerdatetime(String serverdatetime) {
		Serverdatetime = serverdatetime;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	public void setUserid(Long userid) {
		Userid = userid;
	}

	// 汇总
	public int Total() {
		return 1;
	}

	// 分页显示
	public Table GetTable(QueryParam qp) {

		StringBuilder strSql = new StringBuilder();

		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}
}