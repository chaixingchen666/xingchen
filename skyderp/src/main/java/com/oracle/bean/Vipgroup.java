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
//  @author:sunhong  @date:2017-03-20 20:56:03
//*************************************
public class Vipgroup implements java.io.Serializable {
	private Long Vipid;
	private String Vipname;
	private String Nickname;
	private String Sex;
	private String Mobile;
	private String Password;
	private Integer Statetag;
	private Date Regdate;
	private Date Lastdate;
	private String Imagename;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setVipid(Long vipid) {
		this.Vipid = vipid;
	}

	public Long getVipid() {
		return Vipid;
	}

	public void setVipname(String vipname) {
		this.Vipname = vipname;
	}

	public String getVipname() {
		return Vipname;
	}

	public void setNickname(String nickname) {
		this.Nickname = nickname;
	}

	public String getNickname() {
		return Nickname;
	}

	public void setSex(String sex) {
		this.Sex = sex;
	}

	public String getSex() {
		return Sex;
	}

	public void setMobile(String mobile) {
		this.Mobile = mobile;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setPassword(String password) {
		this.Password = password;
	}

	public String getPassword() {
		return Password;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setRegdate(Date regdate) {
		this.Regdate = regdate;
	}

	public String getRegdate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Regdate);
	}

	public void setImagename(String imagename) {
		this.Imagename = imagename;
	}

	public String getImagename() {
		return Imagename;
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
		// strSql.append(" update Vipgroup set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
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
		if (Vipid != null) {
			strSql1.append("vipid,");
			strSql2.append(Vipid + ",");
		}
		if (Vipname != null) {
			strSql1.append("vipname,");
			strSql2.append("'" + Vipname + "',");
		}
		if (Nickname != null) {
			strSql1.append("nickname,");
			strSql2.append("'" + Nickname + "',");
		}
		if (Sex != null) {
			strSql1.append("sex,");
			strSql2.append("'" + Sex + "',");
		}
		if (Mobile != null) {
			strSql1.append("mobile,");
			strSql2.append("'" + Mobile + "',");
		}
		if (Password != null) {
			strSql1.append("password,");
			strSql2.append("'" + Password + "',");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Regdate != null) {
			strSql1.append("regdate,");
			strSql2.append("'" + Regdate + "',");
		}
		if (Imagename != null) {
			strSql1.append("imagename,");
			strSql2.append("'" + Imagename + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=Vipgroup_id.nextval; ");
		strSql.append("   insert into Vipgroup (");
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
		strSql.append("   update Vipgroup set ");
		if (Vipid != null) {
			strSql.append("vipid=" + Vipid + ",");
		}
		if (Vipname != null) {
			strSql.append("vipname='" + Vipname + "',");
		}
		if (Nickname != null) {
			strSql.append("nickname='" + Nickname + "',");
		}
		if (Sex != null) {
			strSql.append("sex='" + Sex + "',");
		}
		if (Mobile != null) {
			strSql.append("mobile='" + Mobile + "',");
		}
		if (Password != null) {
			strSql.append("password='" + Password + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Regdate != null) {
			strSql.append("regdate='" + Regdate + "',");
		}
		if (Imagename != null) {
			strSql.append("imagename='" + Imagename + "',");
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
		strSql.append(" FROM Vipgroup ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist,long accid) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Vipgroup a ");
		strSql.append(" left outer join guestvip b on a.vipid=b.vipid and b.accid=" + accid);
		strSql.append(" join guesttype c on b.vtid=c.vtid ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Vipgroup");
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