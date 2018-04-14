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
//  @author:sunhong  @date:2017-03-08 21:46:59
//*************************************
public class Useronline implements java.io.Serializable {
	private Long Epid;
	private Date Logindate;
	private String Deviceno;
	private String Devicetype;
	private Float Lx;
	private Float Ly;
	private String Accesskey;
	private Long Accid;
	private String Ontime;
	private String Offtime;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public Long getEpid() {
		return Epid;
	}

	public void setLogindate(Date logindate) {
		this.Logindate = logindate;
	}

	public String getLogindate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Logindate);
	}

	public void setDeviceno(String deviceno) {
		this.Deviceno = deviceno;
	}

	public String getDeviceno() {
		return Deviceno;
	}

	public void setDevicetype(String devicetype) {
		this.Devicetype = devicetype;
	}

	public String getDevicetype() {
		return Devicetype;
	}

	public void setLx(Float lx) {
		this.Lx = lx;
	}

	public Float getLx() {
		return Lx;
	}

	public void setLy(Float ly) {
		this.Ly = ly;
	}

	public Float getLy() {
		return Ly;
	}

	public void setAccesskey(String accesskey) {
		this.Accesskey = accesskey;
	}

	public String getAccesskey() {
		return Accesskey;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setOntime(String ontime) {
		this.Ontime = ontime;
	}

	public String getOntime() {
		return Ontime;
	}

	public void setOfftime(String offtime) {
		this.Offtime = offtime;
	}

	public String getOfftime() {
		return Offtime;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }

	// 删除记录
	public int Delete() {
		// StringBuilder strSql = new StringBuilder();
		// strSql.append("select count(*) as num from (");
		// // strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		// strSql.append(" update useronline set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// // strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// }
		return 1;
	}

	// 增加记录
	public int Append() {
		// StringBuilder strSql = new StringBuilder();
		// StringBuilder strSql1 = new StringBuilder();
		// StringBuilder strSql2 = new StringBuilder();
		// strSql1.append("id,");
		// strSql2.append("v_id,");
		// if (Epid != null) {
		// strSql1.append("epid,");
		// strSql2.append(Epid + ",");
		// }
		// if (Logindate != null) {
		// strSql1.append("logindate,");
		// strSql2.append("'" + Logindate + "',");
		// }
		// if (Deviceno != null) {
		// strSql1.append("deviceno,");
		// strSql2.append("'" + Deviceno + "',");
		// }
		// if (Devicetype != null) {
		// strSql1.append("devicetype,");
		// strSql2.append("'" + Devicetype + "',");
		// }
		// if (Lx != null) {
		// strSql1.append("lx,");
		// strSql2.append("'" + Lx + "',");
		// }
		// if (Ly != null) {
		// strSql1.append("ly,");
		// strSql2.append("'" + Ly + "',");
		// }
		// if (Accesskey != null) {
		// strSql1.append("accesskey,");
		// strSql2.append("'" + Accesskey + "',");
		// }
		// if (Accid != null) {
		// strSql1.append("accid,");
		// strSql2.append(Accid + ",");
		// }
		// if (Ontime != null) {
		// strSql1.append("ontime,");
		// strSql2.append("'" + Ontime + "',");
		// }
		// if (Offtime != null) {
		// strSql1.append("offtime,");
		// strSql2.append("'" + Offtime + "',");
		// }
		// strSql1.append("lastdate");
		// strSql2.append("sysdate");
		// strSql.append("declare ");
		// strSql.append(" v_id number; ");
		// strSql.append(" begin ");
		// strSql.append(" v_id:=useronline_id.nextval; ");
		// strSql.append(" insert into useronline (");
		// strSql.append(" " + strSql1.toString());
		// strSql.append(")");
		// strSql.append(" values (");
		// strSql.append(" " + strSql2.toString());
		// strSql.append(");");
		// strSql.append(" select v_id into :id from dual; ");
		// strSql.append(" end;");
		// Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		// param.put("id", new ProdParam(Types.BIGINT));
		// int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		// // id = Long.parseLong(param.get("id").getParamvalue().toString());
		// if (ret < 0)
		// return 0;
		// else
		return 1;
	}

	// 修改记录
	public int Update() {
		// StringBuilder strSql = new StringBuilder();
		// strSql.append("begin ");
		// strSql.append(" update useronline set ");
		// if (Epid != null) {
		// strSql.append("epid=" + Epid + ",");
		// }
		// if (Logindate != null) {
		// strSql.append("logindate='" + Logindate + "',");
		// }
		// if (Deviceno != null) {
		// strSql.append("deviceno='" + Deviceno + "',");
		// }
		// if (Devicetype != null) {
		// strSql.append("devicetype='" + Devicetype + "',");
		// }
		// if (Lx != null) {
		// strSql.append("lx='" + Lx + "',");
		// }
		// if (Ly != null) {
		// strSql.append("ly='" + Ly + "',");
		// }
		// if (Accesskey != null) {
		// strSql.append("accesskey='" + Accesskey + "',");
		// }
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		// if (Ontime != null) {
		// strSql.append("ontime='" + Ontime + "',");
		// }
		// if (Offtime != null) {
		// strSql.append("offtime='" + Offtime + "',");
		// }
		// strSql.append("lastdate=sysdate");
		// strSql.append(" where id=id ;");
		// strSql.append(" commit;");
		// strSql.append(" end;");
		// int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		// if (ret < 0)
		// return 0;
		// else
		return 1;
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM useronline ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// // 获取分页数据
	// public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
	// StringBuilder strSql = new StringBuilder();
	// strSql.append("select " + fieldlist);
	// strSql.append("\n FROM useronline a join employe b on a.epid=b.epid");
	// strSql.append("\n join accreg c on b.accid=c.accid");
	// if (!strWhere.equals("")) {
	// strSql.append("\n where " + strWhere);
	// }
	//
	//
	// qp.setQueryString(strSql.toString());
	// return DbHelperSQL.GetTable(qp);
	// }

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String findbox) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select a.logindate,a.epid,b.epname,b.mobile,b.accid,c.accname,c.company,a.devicetype,c.balcurr");
		strSql.append("\n FROM useronline a join employe b on a.epid=b.epid");
		strSql.append("\n join accreg c on b.accid=c.accid");
		// if (!strWhere.equals("")) {
		strSql.append("\n where " + strWhere);
		// }
		if (findbox != null && !findbox.equals(""))
			strSql.append(" and (b.epname like '%" + findbox + "%' or b.mobile like '%" + findbox + "%' or c.accname like '%" + findbox.toUpperCase() + "%' or c.company like '%" + findbox + "%') ");

		strSql.append("\n union all");
		strSql.append("\n select a.logindate,a.omepid as epid,cast(b.omepname||'(订)' as nvarchar2(20))  as epname");
		strSql.append("\n ,cast(b.mobile as nvarchar2(20)) mobile,b.accid,c.accname,c.company,cast(a.devicetype as nvarchar2(20)) as devicetype,c.balcurr");
		strSql.append("\n FROM omuseronline a join omuser b on a.omepid=b.omepid");
		strSql.append("\n join accreg c on b.accid=c.accid");
		// if (!strWhere.equals("")) {
		strSql.append("\n where " + strWhere);
		// }

		if (findbox != null && !findbox.equals(""))
			strSql.append(" and (b.omepname like '%" + findbox + "%' or b.mobile like '%" + findbox + "%' or c.accname like '%" + findbox.toUpperCase() + "%' or c.company like '%" + findbox + "%') ");

		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from useronline");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}
}