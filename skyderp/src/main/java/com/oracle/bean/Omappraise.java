package com.oracle.bean;

import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.QueryParam;

//*************************************
//  @author:sunhong  @date:2017-02-22 20:04:13
//*************************************
public class Omappraise implements java.io.Serializable {
	private Long Omid;
	private Long Omepid;
	private Long Wareid;
	private String Remark = "";
	private Long Score = (long) 0;
	// private Date Lastdate;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setOmid(Long omid) {
		this.Omid = omid;
	}

	public Long getOmid() {
		return Omid;
	}

	public void setOmepid(Long omepid) {
		this.Omepid = omepid;
	}

	public Long getOmepid() {
		return Omepid;
	}

	public void setWareid(Long wareid) {
		this.Wareid = wareid;
	}

	public Long getWareid() {
		return Wareid;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setScore(Long score) {
		this.Score = score;
	}

	public Long getScore() {
		return Score;
	}

	// public void setLastdate(Date lastdate) {
	// this.Lastdate = lastdate;
	// }
	//
	// public String getLastdate() {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// return df.format(Lastdate);
	// }

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }

	// 删除记录
	public int Delete() {
		StringBuilder strSql = new StringBuilder();
		// strSql.append("select count(*) as num from (");
		// // strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		strSql.append("declare");
		strSql.append("\n   v_count number;");
		strSql.append("\n begin");
		strSql.append("\n   delete from omappraise ");
		strSql.append("\n   where omid=" + Omid + " and omepid=" + Omepid + " and wareid=" + Wareid + ";");
		strSql.append("\n   select nvl(sum(score),0) into v_count from Omappraise where omid=" + Omid + " and wareid=" + Wareid + ";");
		strSql.append("\n   update omwarecode set totalscore=v_count where omid=" + Omid + " and wareid=" + Wareid + ";");
		strSql.append("\n end;");

		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	// 增加记录
	public int Append(long userid) {
		StringBuilder strSql = new StringBuilder();

		if (Wareid == null || Wareid == 0) {
			errmess = "商品参数无效！";
			return 0;
		}
		if (Score == 0 && Remark == "") {
			errmess = "评分或评价内容至少要输入一项！";
			return 0;
		}
		strSql.append("declare");
		strSql.append("\n   v_count number;");
		strSql.append("\n begin");
		strSql.append("\n   select count(*) into v_count from Omappraise where omid=" + Omid + " and omepid=" + userid + " and wareid=" + Wareid + ";");
		strSql.append("\n   if v_count=0 then ");
		strSql.append("\n      insert into Omappraise (omid,omepid,wareid,score,remark)");
		strSql.append("\n      values (" + Omid + "," + userid + "," + Wareid + "," + Score + ",'" + Remark + "');");
		strSql.append("\n   else");
		strSql.append("\n      update Omappraise set score=" + Score + ",remark='" + Remark + "',lastdate=sysdate");
		strSql.append("\n      where omid=" + Omid + " and omepid=" + userid + " and wareid=" + Wareid + ";");
		strSql.append("\n   end if;");
		strSql.append("\n   select nvl(sum(score),0) into v_count from Omappraise where omid=" + Omid + " and wareid=" + Wareid + ";");
		strSql.append("\n   update omwarecode set totalscore=v_count where omid=" + Omid + " and wareid=" + Wareid + ";");
		strSql.append("\n end;");
		//System.out.println(strSql.toString());
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			// Id = Long.parseLong(param.get("id").getParamvalue().toString());
			return 1;
		}

	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update omappraise set ");
		if (Omid != null) {
			strSql.append("omid=" + Omid + ",");
		}
		if (Omepid != null) {
			strSql.append("omepid=" + Omepid + ",");
		}
		if (Wareid != null) {
			strSql.append("wareid=" + Wareid + ",");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Score != null) {
			strSql.append("score=" + Score + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM omappraise ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM omappraise ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from omappraise");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}
}