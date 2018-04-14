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
//  @author:sunhong  @date:2017-03-04 19:52:18
//*************************************
public class Warewave implements java.io.Serializable {
	private Long Waveid;
	private String Wavename;
	private Long Accid;
	private String Begindate;
	private String Enddate;
	private Integer Statetag;
	private String Lastop;
	private Date Lastdate;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setWaveid(Long waveid) {
		this.Waveid = waveid;
	}

	public Long getWaveid() {
		return Waveid;
	}

	public void setWavename(String wavename) {
		this.Wavename = wavename;
	}

	public String getWavename() {
		return Wavename;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setBegindate(String begindate) {
		this.Begindate = begindate;
	}

	public String getBegindate() {
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// return df.format(Begindate);
		return Begindate;
	}

	public void setEnddate(String enddate) {
		this.Enddate = enddate;
	}

	public String getEnddate() {
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// return df.format(Enddate);
		return Enddate;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
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
		strSql.append("select count(*) as num from (");
		strSql.append("select wareid from warecode where waveid=" + Waveid + " and rownum=1 and statetag=1");
		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前波次已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		strSql.append(" update warewave set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append(" where waveid=" + Waveid + " and accid=" + Accid);
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
		if (Wavename == null || Wavename.equals("")) {
			errmess = "波次名称不允许为空！";
			return 0;
		}
		if (Exists() ) {
			errmess = "波次名称已存在！";
			return 0;
		}
		if (Begindate == null || Enddate == null || Begindate.equals("") || Enddate.equals("")) {
			errmess = "日期无效！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("waveid,");
		strSql2.append("v_id,");
		// if (Waveid != null) {
		// strSql1.append("waveid,");
		// strSql2.append(Waveid + ",");
		// }
		// if (Wavename != null) {
		strSql1.append("wavename,");
		strSql2.append("'" + Wavename + "',");
		// }
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		// if (Begindate != null) {
		strSql1.append("begindate,");
		strSql2.append("to_date('" + Begindate + "','yyyy-mm-dd'),");
		// }
		// if (Enddate != null) {
		strSql1.append("enddate,");
		strSql2.append("to_date('" + Enddate + "','yyyy-mm-dd'),");
		// }
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=warewave_waveid.nextval; ");
		strSql.append("   insert into warewave (");
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
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		Waveid = Long.parseLong(param.get("id").getParamvalue().toString());
		errmess = "" + Waveid;
		return 1;
	}

	// 修改记录
	public int Update() {
		if (Exists()) {
			errmess = "波次名称已存在！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update warewave set ");
		// if (Waveid != null) {
		// strSql.append("waveid=" + Waveid + ",");
		// }
		if (Wavename != null) {
			strSql.append("wavename='" + Wavename + "',");
		}
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		if (Begindate != null) {
			strSql.append("begindate=to_date('" + Begindate + "','yyyy-mm-dd'),");
		}
		if (Enddate != null) {
			strSql.append("enddate=to_date('" + Enddate + "','yyyy-mm-dd'),");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
		strSql.append("lastdate=sysdate");
		strSql.append("  where waveid=" + Waveid + " and accid=" + Accid + " ;");
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
		strSql.append(" FROM warewave ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM warewave ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取所有数据
	public Table GetTable(String strWhere, String fieldlist, String sort) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM warewave a");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		if (!sort.equals("")) {
			strSql.append(" order by " + sort);
		}
		return DbHelperSQL.GetTable(strSql.toString());
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from warewave");
		strSql.append(" where wavename='" + Wavename + "' and statetag=1 and rownum=1 and accid=" + Accid);
		if (Waveid != null)
			strSql.append(" and Waveid<>" + Waveid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}
}