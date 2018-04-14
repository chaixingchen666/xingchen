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
//import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
//import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-25 10:11:48
//*************************************
public class Callback implements java.io.Serializable {
	private Float Callid;
	private Date Notedate;
	private String Content;
	private Long Accid;
	private String Linkman;
	private String Tel;
	private String Sex;
	private Integer Succtag;
	private String Remark;
	private String Nextdate;
	private String Operant;
	private String Checkman;
	private Float Pcallid;
	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setCallid(Float callid) {
		this.Callid = callid;
	}

	public Float getCallid() {
		return Callid;
	}

	public void setNotedate(Date notedate) {
		this.Notedate = notedate;
	}

	public String getNotedate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Notedate);
	}

	public void setContent(String content) {
		this.Content = content;
	}

	public String getContent() {
		return Content;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setLinkman(String linkman) {
		this.Linkman = linkman;
	}

	public String getLinkman() {
		return Linkman;
	}

	public void setTel(String tel) {
		this.Tel = tel;
	}

	public String getTel() {
		return Tel;
	}

	public void setSex(String sex) {
		this.Sex = sex;
	}

	public String getSex() {
		return Sex;
	}

	public void setSucctag(Integer succtag) {
		this.Succtag = succtag;
	}

	public Integer getSucctag() {
		return Succtag;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setNextdate(String nextdate) {
		this.Nextdate = nextdate;
	}

//	public String getNextdate() {
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		return df.format(Nextdate);
//	}

	public void setOperant(String operant) {
		this.Operant = operant;
	}

	public String getOperant() {
		return Operant;
	}

	public void setCheckman(String checkman) {
		this.Checkman = checkman;
	}

	public String getCheckman() {
		return Checkman;
	}

	public void setPcallid(Float pcallid) {
		this.Pcallid = pcallid;
	}

	public Float getPcallid() {
		return Pcallid;
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
		// strSql.append(" update Callback set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
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

		if (Accid == null || Accid == 0) {
			errmess = "账户id无效！";
			return 0;
		}
		if (Func.isNull(Content)) {
			errmess = "回访内容不允许为空！";
			return 0;
		}
		if (Func.isNull(Linkman)) {
			errmess = "联系人不允许为空！";
			return 0;
		}
		if (Func.isNull(Tel)) {
			errmess = "联系电话不允许为空！";
			return 0;
		}
		if (Pcallid == null) {
			errmess = "上次回访记录id无效！";
			return 0;

		}
		if (Succtag < 1 || Succtag > 3) {
			errmess = "回访结果参数无效！";
			return 0;
		}
		// succtag=标志 (1=转下次回访，2=成功(已充值)，3=战败，4=无效)
		if (Succtag == 1 && (Nextdate == null || Nextdate.equals(""))) {
			errmess = "请输入下次回访日期！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("callid,");
		strSql2.append("v_callid,");
		// if (Callid != null) {
		// strSql1.append("callid,");
		// strSql2.append("'" + Callid + "',");
		// }
		// if (Notedate != null) {
		strSql1.append("notedate,");
		strSql2.append("sysdate,");
		// }
		if (Content != null) {
			strSql1.append("content,");
			strSql2.append("'" + Content + "',");
		}
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Linkman != null) {
			strSql1.append("linkman,");
			strSql2.append("'" + Linkman + "',");
		}
		if (Tel != null) {
			strSql1.append("tel,");
			strSql2.append("'" + Tel + "',");
		}
		if (Sex != null) {
			strSql1.append("sex,");
			strSql2.append("'" + Sex + "',");
		}
		if (Succtag != null) {
			strSql1.append("succtag,");
			strSql2.append(Succtag + ",");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Nextdate != null) {
			strSql1.append("nextdate,");
			strSql2.append("to_date('" + Nextdate + "','yyyy-mm-dd'),");
		}
		// if (Operant != null) {
		strSql1.append("operant,");
		strSql2.append("'" + Operant + "',");
		// }
		if (Checkman != null) {
			strSql1.append("checkman,");
			strSql2.append("'" + Checkman + "',");
		}
		if (Pcallid != null) {
			strSql1.append("pcallid,");
			strSql2.append("'" + Pcallid + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_callid number; ");
		strSql.append(" begin ");
		strSql.append("   v_callid:=Callback_callid.nextval; ");
		strSql.append("   insert into Callback (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		
        if (Succtag >= 1) strSql.append("\n   update accreg set succtag=" + Succtag + " where accid=" + Accid + "; ");        //战败
        //succtag=标志 (1=继续回访，2=成功(已充值)，3=战败)
        if (Pcallid != null && Pcallid > 0) strSql.append("\n   update callback set succtag=1 where callid=" + Pcallid + ";");


		strSql.append("  select v_callid into :id from dual; ");
		strSql.append(" end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.FLOAT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			Callid = Float.parseFloat(param.get("id").getParamvalue().toString());
			errmess = Callid.toString();
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Callback set ");
		if (Callid != null) {
			strSql.append("callid='" + Callid + "',");
		}
		if (Notedate != null) {
			strSql.append("notedate='" + Notedate + "',");
		}
		if (Content != null) {
			strSql.append("content='" + Content + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Linkman != null) {
			strSql.append("linkman='" + Linkman + "',");
		}
		if (Tel != null) {
			strSql.append("tel='" + Tel + "',");
		}
		if (Sex != null) {
			strSql.append("sex='" + Sex + "',");
		}
		if (Succtag != null) {
			strSql.append("succtag=" + Succtag + ",");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Nextdate != null) {
			strSql.append("nextdate='" + Nextdate + "',");
		}
		if (Operant != null) {
			strSql.append("operant='" + Operant + "',");
		}
		if (Checkman != null) {
			strSql.append("checkman='" + Checkman + "',");
		}
		if (Pcallid != null) {
			strSql.append("pcallid='" + Pcallid + "',");
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
		strSql.append(" FROM Callback ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Callback a");
		strSql.append(" left outer join accreg b on a.accid=b.accid");
		strSql.append(" left outer join mobilearea d on substr(b.mobile,1,7)=d.mobile");

		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Callback");
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