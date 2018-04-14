package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

//*************************************
//  @author:sunhong  @date:2017-04-01 14:30:24
//*************************************
public class Bookemploye implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private Long Bookid;
	private Long Epid;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setBookid(Long bookid) {
		this.Bookid = bookid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getBookid() {
		return Bookid;
	}

	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public Long getEpid() {
		return Epid;
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
		// strSql.append(" update Bookemploye set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
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
	public int Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		if (Id != null) {
			strSql1.append("id,");
			strSql2.append("'" + Id + "',");
		}
		if (Bookid != null) {
			strSql1.append("bookid,");
			strSql2.append(Bookid + ",");
		}
		if (Epid != null) {
			strSql1.append("epid,");
			strSql2.append(Epid + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=Bookemploye_id.nextval; ");
		strSql.append("   insert into Bookemploye (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("  select v_id into :id from dual; ");
		strSql.append(" end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
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
		strSql.append("   update Bookemploye set ");
		// if (Id != null) {
		// strSql.append("id='" + Id + "',");
		// }
		if (Bookid != null) {
			strSql.append("bookid=" + Bookid + ",");
		}
		if (Epid != null) {
			strSql.append("epid=" + Epid + ",");
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
		strSql.append(" FROM Bookemploye ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Bookemploye ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp) {
		StringBuilder strSql = new StringBuilder();
		strSql.append(" select a.epid,b.epname,b.houseid,c.housename,d.levelname,'1' as selbj ");
		strSql.append("\n from bookemploye a");
		strSql.append("\n join employe b on a.epid=b.epid");
		strSql.append("\n left outer join warehouse c on b.houseid=c.houseid");
		strSql.append("\n left outer join userrole d on b.levelid=d.levelid");
		strSql.append("\n where a.bookid=" + Bookid + " and b.statetag=1  and b.noused=0 and b.passkey=1");

		strSql.append("\n union all");
		strSql.append("\n select a.epid,a.epname,a.houseid,c.housename,d.levelname,'0' as selbj ");
		strSql.append("\n from employe a ");
		strSql.append("\n left outer join warehouse c on a.houseid=c.houseid");
		strSql.append("\n left outer join userrole d on a.levelid=d.levelid");

		strSql.append("\n where a.accid=" + Accid + " and a.statetag=1 and a.noused=0 and a.passkey=1");
		strSql.append("\n and not exists (select 1 from bookemploye b where a.epid=b.epid and b.bookid=" + Bookid + ")");
		//System.out.println(strSql.toString());
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		// StringBuilder strSql = new StringBuilder();
		// strSql.append("select count(*) as num from Bookemploye");
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