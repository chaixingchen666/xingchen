package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-16 23:01:23
//*************************************
public class Cityhousebuyer implements java.io.Serializable {
	private Float Id;
	private Long Cthouseid;
	private Long Buyaccid;
	private Date Lastdate;
	private String Lastop;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setCthouseid(Long cthouseid) {
		this.Cthouseid = cthouseid;
	}

	public Long getCthouseid() {
		return Cthouseid;
	}

	public void setBuyaccid(Long buyaccid) {
		this.Buyaccid = buyaccid;
	}

	public Long getBuyaccid() {
		return Buyaccid;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

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
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		// strSql.append(" update cityhousebuyer set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// } else {
		// errmess = "删除成功！";
		return 1;
		// }
	}

	// 增加记录
	public int Append(int value) {
		if (Cthouseid == 0) {
			errmess = "商城id无效！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();

		if (value == 0) {
			strSql.append("begin  delete from cityhousebuyer where cthouseid=" + Cthouseid + " and buyaccid=" + Buyaccid + "; end ;");
		} else {
			strSql.append(" declare");
			strSql.append("   v_count number;");
			strSql.append(" begin");
			strSql.append("    select count(*) into v_count from cityhousebuyer where cthouseid=" + Cthouseid + " and buyaccid=" + Buyaccid + " ;");
			strSql.append("    if v_count=0 then");
			strSql.append("       insert into cityhousebuyer (id,cthouseid,buyaccid,lastdate,lastop) ");
			strSql.append("       values (cityhousebuyer_id.nextval," + Cthouseid + ", " + Buyaccid + ",sysdate,'" + Lastop + "');");
			strSql.append("    end if;");
			strSql.append("    commit;");
			strSql.append(" end ;");
		}

		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append(int value, long accid) {
		if (Cthouseid == 0) {
			errmess = "商城id无效！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();

		strSql.append("begin ");
		strSql.append("\n delete from cityhousebuyer where cthouseid=" + Cthouseid + "; ");
		if (value == 1) {
			strSql.append("\n   insert into cityhousebuyer (id,cthouseid,buyaccid,lastdate,lastop) ");
			strSql.append("\n   select cityhousebuyer_id.nextval," + Cthouseid + ",buyaccid,sysdate,'" + Lastop + "' from accconnect where sellaccid=" + accid + ";");
		}
		strSql.append("\n    commit;");
		strSql.append("\n end ;");

		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
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
		strSql.append("   update cityhousebuyer set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Cthouseid != null) {
			strSql.append("cthouseid=" + Cthouseid + ",");
		}
		if (Buyaccid != null) {
			strSql.append("buyaccid=" + Buyaccid + ",");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
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
		strSql.append(" FROM cityhousebuyer ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM cityhousebuyer ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from cityhousebuyer");
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