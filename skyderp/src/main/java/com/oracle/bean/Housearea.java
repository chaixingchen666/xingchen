package com.oracle.bean;

//import java.sql.Types;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.netdata.db.DbHelperSQL;
//import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

//*************************************
//  @author:sunhong  @date:2017-04-09 12:21:36
//*************************************
public class Housearea implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6035611861298648323L;
	/**
	 * 
	 */
	
	private Long Accid;
	private Long Areaid;
	private Long Houseid;
//	private Date Lastdate;
	private String Aimagename;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setAreaid(Long areaid) {
		this.Areaid = areaid;
	}

	public void setAcciid(Long accid) {
		this.Accid = accid;
	}

	public Long getAreaid() {
		return Areaid;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public void setAimagename(String aimagename) {
		this.Aimagename = aimagename;
	}

	public String getAimagename() {
		return Aimagename;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Houseid == null || Areaid == null || Houseid == 0 || Areaid == 0) {
			errmess = "houseid或areaid无效！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();

		strSql.append(" delete from housearea where houseid=" + Houseid + " and areaid=" + Areaid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append() {
		if (Aimagename == null || Aimagename.length() <= 0) {
			errmess = "图片名称不能为空！";
			return 0;
		}
		if (Houseid == null || Areaid == null || Houseid == 0 || Areaid == 0) {
			errmess = "houseid或areaid无效！";
			return 0;
		}
		String qry = "declare";
		qry += "   v_count number;";
		qry += " begin";
		qry += "   select count(*) into v_count from housearea where houseid=" + Houseid + " and areaid=" + Areaid + ";";
		qry += "   if v_count=0 then";
		qry += "      insert into housearea (houseid,areaid,aimagename) values (" + Houseid + "," + Areaid + ",'" + Aimagename + "');";
		qry += "   else";
		qry += "      update housearea set aimagename='" + Aimagename + "',lastdate=sysdate where houseid=" + Houseid + " and areaid=" + Areaid + ";";
		qry += "   end if;";
		qry += " end;";

		int ret = DbHelperSQL.ExecuteSql(qry);
		// id = Long.parseLong(param.get("id").getParamvalue().toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update housearea set ");
		if (Areaid != null) {
			strSql.append("areaid=" + Areaid + ",");
		}
		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
		}
		if (Aimagename != null) {
			strSql.append("aimagename='" + Aimagename + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM housearea ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM housearea ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp) {
		String qry = "select a.areaid,b.areaname,a.aimagename ";
		qry += " from housearea a join area b on a.areaid=b.areaid";
		qry += " where b.accid=" + Accid + " and a.houseid=" + Houseid;
		qry += " union all";
		qry += " select b.areaid,b.areaname,'' as aimagename";
		qry += " from area b where b.accid=" + Accid;
		qry += " and not exists (select 1 from housearea a where a.areaid=b.areaid and a.houseid=" + Houseid + ")";
		qp.setQueryString(qry);
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
//		StringBuilder strSql = new StringBuilder();
//		strSql.append("select count(*) as num  from housearea");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}
}