package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.Func;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

//*************************************
//  @author:sunhong  @date:2017-08-25 20:33:50
//*************************************
public class Userdevice implements java.io.Serializable {
	private Long Accid;
	private Long Epid;
	private String Deviceno;
	private String Devicetype;
	private Integer Loginenabled;
	private Date Lastdate;
	private String Remark;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // ���׿�ʼʹ������
	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getEpid() {
		return Epid;
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

	public void setLoginenabled(Integer loginenabled) {
		this.Loginenabled = loginenabled;
	}

	public Integer getLoginenabled() {
		return Loginenabled;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public String getErrmess() { // ȡ������ʾ
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// ɾ����¼
	public int Delete() {
		if (Epid == null || Epid == 0) {
			errmess = "epid不是一个有效值！";
			return 0;
		}
		if (Func.isNull(Deviceno)) {
			errmess = "deviceno不是一个有效值！";
			return 0;

		}
		StringBuilder strSql = new StringBuilder();

//		strSql.setLength(0);
		strSql.append(" delete from USERDEVICE ");
		strSql.append(" where epid=" + Epid + " and deviceno='" + Deviceno + "'");
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// ���Ӽ�¼
	public int Append() {
		// StringBuilder strSql = new StringBuilder();
		// StringBuilder strSql1 = new StringBuilder();
		// StringBuilder strSql2 = new StringBuilder();
		// if (Epid != null) {
		// strSql1.append("epid,");
		// strSql2.append(Epid + ",");
		// }
		// if (Deviceno != null) {
		// strSql1.append("deviceno,");
		// strSql2.append("'" + Deviceno + "',");
		// }
		// if (Devicetype != null) {
		// strSql1.append("devicetype,");
		// strSql2.append("'" + Devicetype + "',");
		// }
		// if (Loginenabled != null) {
		// strSql1.append("loginenabled,");
		// strSql2.append(Loginenabled + ",");
		// }
		// if (Remark != null) {
		// strSql1.append("remark,");
		// strSql2.append("'" + Remark + "',");
		// }
		// strSql1.append("lastdate");
		// strSql2.append("sysdate");
		// strSql.append("declare ");
		// strSql.append(" v_id number; ");
		// strSql.append(" begin ");
		// strSql.append(" v_id:=USERDEVICE_id.nextval; ");
		// strSql.append(" insert into USERDEVICE (");
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
		// if (ret < 0) {
		// errmess = "�����쳣��";
		// return 0;
		// } else {
		// errmess = "�����ɹ���";
		return 1;
		// }
	}

	// �޸ļ�¼
	public int Update() {
		if (Epid == null || Epid == 0) {
			errmess = "epid不是一个有效值！";
			return 0;
		}
		if (Func.isNull(Deviceno)) {
			errmess = "设备号无效！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("  update USERDEVICE set ");

		if (Loginenabled != null) {
			strSql.append("loginenabled=" + Loginenabled + ",");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("   where epid=" + Epid + " and deviceno='" + Deviceno + "' ;");
		strSql.append("   commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM USERDEVICE ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM USERDEVICE a ");
		strSql.append("\n join employe b on a.epid=b.epid ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from USERDEVICE");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1
		// and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " �Ѵ��ڣ����������룡";
		// return true;
		// }
		return false;
	}
}