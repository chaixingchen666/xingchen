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
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-02-21 19:05:32
//*************************************
public class Smsrecord implements java.io.Serializable {
	private Long Id;
	private String Mobile;
	private Date Senddate;
	private String Messstr;
	private String Operant;
	private Integer Tag;
	private String Smspwd;
	private String errmess;


	public void setMobile(String mobile) {
		this.Mobile = mobile;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setSenddate(Date senddate) {
		this.Senddate = senddate;
	}

	public String getSenddate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Senddate);
	}

	public void setMessstr(String messstr) {
		this.Messstr = messstr;
	}

	public String getMessstr() {
		return Messstr;
	}

	public void setOperant(String operant) {
		this.Operant = operant;
	}

	public String getOperant() {
		return Operant;
	}

	public void setTag(Integer tag) {
		this.Tag = tag;
	}

	public Integer getTag() {
		return Tag;
	}

	public void setSmspwd(String smspwd) {
		this.Smspwd = smspwd;
	}

	public String getSmspwd() {
		return Smspwd;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }

	// 删除记录
	// public int Delete() {
	// StringBuilder strSql = new StringBuilder();
	// strSql.append("select count(*) as num from (");
	// // strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
	// strSql.append(")");
	// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
	// errmess = "当前记录已使用，不允许删除";
	// return 0;
	// }
	// strSql.setLength(0);
	// strSql.append(" update smsrecord set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
	// // strSql.append(" where id=" + id + " and accid=" + Accid);
	// if (DbHelperSQL.ExecuteSql(strSql.toString()) == 0) {
	// errmess = "删除失败！";
	// return 0;
	// }
	// return 1;
	// }

	// 增加记录
	public int Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		// if (Id != null) {
		// strSql1.append("id,");
		// strSql2.append(Id + ",");
		// }
		if (Mobile != null) {
			strSql1.append("mobile,");
			strSql2.append("'" + Mobile + "',");
		}
		if (Senddate != null) {
			strSql1.append("senddate,");
			strSql2.append("'" + Senddate + "',");
		}
		if (Messstr != null) {
			strSql1.append("messstr,");
			strSql2.append("'" + Messstr + "',");
		}
		if (Operant != null) {
			strSql1.append("operant,");
			strSql2.append("'" + Operant + "',");
		}
		if (Tag != null) {
			strSql1.append("tag,");
			strSql2.append(Tag + ",");
		}
		if (Smspwd != null) {
			strSql1.append("smspwd,");
			strSql2.append("'" + Smspwd + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=smsrecord_id.nextval; ");
		strSql.append("   insert into smsrecord (");
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
		if (ret >= 0) {
			Id = Long.parseLong(param.get("id").getParamvalue().toString());
			return 1;
		} else
			return 0;
	}

	// 修改记录
	// public int Update() {
	// StringBuilder strSql = new StringBuilder();
	// strSql.append("begin ");
	// strSql.append(" update smsrecord set ");
	// if (Id != null) {
	// strSql.append("id=" + Id + ",");
	// }
	// if (Mobile != null) {
	// strSql.append("mobile='" + Mobile + "',");
	// }
	// if (Senddate != null) {
	// strSql.append("senddate='" + Senddate + "',");
	// }
	// if (Messstr != null) {
	// strSql.append("messstr='" + Messstr + "',");
	// }
	// if (Operant != null) {
	// strSql.append("operant='" + Operant + "',");
	// }
	// if (Tag != null) {
	// strSql.append("tag=" + Tag + ",");
	// }
	// if (Smspwd != null) {
	// strSql.append("smspwd='" + Smspwd + "',");
	// }
	// strSql.append("lastdate=sysdate");
	// strSql.append(" where id=id ;");
	// strSql.append(" commit;");
	// strSql.append(" end;");
	// return DbHelperSQL.ExecuteSql(strSql.toString());
	// }

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM smsrecord ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM smsrecord ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int doSendsms(long accid, long vipid, int fs) {
	
//		System.out.println("doSendsms 0 ");
		if (!Func.isMobile(Mobile)) {
			errmess = "不是有效的移动电话号码！";
			return 0;
		}
		String qry;
		if (fs == 2) // VIP注册返回校验 码
		{
			qry = "select count(*) as num  from vipgroup where mobile='" + Mobile + "' and rownum=1 ";
			if (vipid > 0)
				qry += " and vipid<>" + vipid;
		} else {
			qry = "select count(*) as num  from accreg where mobile='" + Mobile + "' and rownum=1 ";
			if (accid > 0)
				qry += " and accid<>" + accid;

		}
		if (DbHelperSQL.GetSingleCount(qry) > 0) {
			errmess = "移动电话:" + Mobile + " 已绑定账户，请换个号码重新输入！";
			return 0;
		}
		// ==============================================
		// 统计最近3分钟内移动电话发送短信次数
		qry = "select count(*) as num from smsrecord where mobile='" + Mobile + "' and floor(to_number(sysdate-senddate)*24*60)<=5";
		int num = DbHelperSQL.GetSingleCount(qry);
		if (num >= 3) {
			errmess = "您在5分钟内发送短信超过3次，请过段时间再发送！";
			return 0;
		}
		return 1;
	}

	public int doSendsms4(long accid) {

		if (!Func.isMobile(Mobile)) {
			errmess = "不是有效的移动电话号码！";
			return 0;
		}

		String qry;
		qry = "select count(*) as num  from accreg where mobile='" + Mobile + "' and rownum=1 ";
		if (accid > 0)
			qry += " and accid<>" + accid;

		if (DbHelperSQL.GetSingleCount(qry) > 0) {
			errmess = "移动电话:" + Mobile + " 已绑定账户，请换个号码重新输入！";
			return 0;
		}
		// ==============================================
		// 统计最近3分钟内移动电话发送短信次数
		qry = "select count(*) as num from smsrecord where mobile='" + Mobile + "' and floor(to_number(sysdate-senddate)*24*60)<=5";
		int num = DbHelperSQL.GetSingleCount(qry);
		if (num >= 3) {
			errmess = "您在5分钟内发送短信超过3次，请过段时间再发送！";
			return 0;
		}
		return 1;
	}

	public int doGetms(int fs) {
		String qry;
		if (fs == 0) {
			qry = "select count(*) as num  from accreg where mobile='" + Mobile + "' and rownum=1 ";
		} else {
			qry = "select count(*) as num   from vipgroup where mobile='" + Mobile + "' and rownum=1 ";

		}
		if (DbHelperSQL.GetSingleCount(qry) == 0) {
			errmess = "移动电话:" + Mobile + " 未注册！";
			return 0;
		}
		return 1;

	}
	// public int Exists() {
	// StringBuilder strSql = new StringBuilder();
	// strSql.append("select count(*) as num from smsrecord");
	// // strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
	// // if (Brandid != null)
	// // strSql.append(" and brandid<>" + Brandid);
	// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
	// // errmess = "???:" + Brandname + " 已存在，请重新输入！";
	// return 0;
	// }
	// return 1;
	// }
}