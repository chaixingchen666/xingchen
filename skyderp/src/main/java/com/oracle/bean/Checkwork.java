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
//  @author:sunhong  @date:2017-03-25 11:32:37
//*************************************
public class Checkwork implements java.io.Serializable {
	private Float Id;
	private Long Epid;
	private String Classday;
	private Integer Classid;
	private Integer Checkid;
	private Date Checktime0;
	private String Remark0;
	private Integer Uptag0;
	private Date Checktime1;
	private String Remark1;
	private Integer Uptag1;
	private Long Days;
	private Integer Cdztbj0;
	private Integer Cdztbj1;
	private String Imagename0;
	private String Imagename1;
	private String Lastop;
	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public Long getEpid() {
		return Epid;
	}

	public void setClassday(String classday) {
		this.Classday = classday;
	}

	public String getClassday() {
		return Classday;
	}

	public void setClassid(Integer classid) {
		this.Classid = classid;
	}

	public Integer getClassid() {
		return Classid;
	}

	public void setCheckid(Integer checkid) {
		this.Checkid = checkid;
	}

	public Integer getCheckid() {
		return Checkid;
	}

	public void setChecktime0(Date checktime0) {
		this.Checktime0 = checktime0;
	}

	public String getChecktime0() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Checktime0);
	}

	public void setRemark0(String remark0) {
		this.Remark0 = remark0;
	}

	public String getRemark0() {
		return Remark0;
	}

	public void setUptag0(Integer uptag0) {
		this.Uptag0 = uptag0;
	}

	public Integer getUptag0() {
		return Uptag0;
	}

	public void setChecktime1(Date checktime1) {
		this.Checktime1 = checktime1;
	}

	public String getChecktime1() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Checktime1);
	}

	public void setRemark1(String remark1) {
		this.Remark1 = remark1;
	}

	public String getRemark1() {
		return Remark1;
	}

	public void setUptag1(Integer uptag1) {
		this.Uptag1 = uptag1;
	}

	public Integer getUptag1() {
		return Uptag1;
	}

	public void setDays(Long days) {
		this.Days = days;
	}

	public Long getDays() {
		return Days;
	}

	public void setCdztbj0(Integer cdztbj0) {
		this.Cdztbj0 = cdztbj0;
	}

	public Integer getCdztbj0() {
		return Cdztbj0;
	}

	public void setCdztbj1(Integer cdztbj1) {
		this.Cdztbj1 = cdztbj1;
	}

	public Integer getCdztbj1() {
		return Cdztbj1;
	}

	public void setImagename0(String imagename0) {
		this.Imagename0 = imagename0;
	}

	public String getImagename0() {
		return Imagename0;
	}

	public void setImagename1(String imagename1) {
		this.Imagename1 = imagename1;
	}

	public String getImagename1() {
		return Imagename1;
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
		// strSql.append(" update Checkwork set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// } else {
		// errmess = "删除成功！";
		return 1;
		// }
	}

	// 增加记录 店长考勤
	public int Append(int workid) {
		if (Epid == null || Epid == 0) {
			errmess = "职员id无效！";
			return 0;
		}
		if (workid == 0) // 上班
		{
			if (Remark0 == null || Remark0.equals("")) {
				errmess = "请输入备注0！";
				return 0;

			}
			// model.imagename0 = retcs.Substring(1, retcs.Length - 1);
			// model.imagename1 = "";
		} else {
			if (Remark1 == null || Remark1.equals("")) {
				errmess = "请输入备注1！";
				return 0;

			}
			// model.imagename0 = "";
			// model.imagename1 = retcs.Substring(1, retcs.Length - 1);
		}
		if (Classid == null) {
			errmess = "classid无效！";
			return 0;
		}
		// checkid:0=正常考勤 1=旷工2=病假 3=事假 4=公休
		if (Checkid == null || Checkid > 4) {
			errmess = "Checkid无效！";
			return 0;

		}
		if (Classday == null || Classday.equals("")) {
			errmess = "Classday无效！";
			return 0;

		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		StringBuilder strSql3 = new StringBuilder();
		strSql3.append(" lastop='" + Lastop + "',lastdate=sysdate");
		strSql1.append("id,");
		strSql2.append("v_id,");
		// if (Checkid != null) {
		strSql1.append("checkid,");
		strSql2.append(Checkid + ",");
		strSql3.append(",checkid=" + Checkid);
		// }

		if (Epid != null) {
			strSql1.append("epid,");
			strSql2.append(Epid + ",");
		}
		if (Classday != null) {
			strSql1.append("classday,");
			strSql2.append("'" + Classday + "',");
			strSql3.append(",classday=" + Classday);
		}
		if (Classid != null) {
			strSql1.append("classid,");
			strSql2.append(Classid + ",");
			strSql3.append(",Classid=" + Classid);
		}
		if (Remark0 != null) {
			strSql1.append("remark0,");
			strSql2.append("'" + Remark0 + "',");
			strSql3.append(",Remark0=" + Remark0);
		}
		if (Remark1 != null) {
			strSql1.append("remark1,");
			strSql2.append("'" + Remark1 + "',");
			strSql3.append(",Remark1=" + Remark1);
		}
		if (Days != null) {
			strSql1.append("days,");
			strSql2.append(Days + ",");
			strSql3.append(",Days=" + Days);
		}
		// if (Cdztbj0 != null) {
		// strSql1.append("cdztbj0,");
		// strSql2.append(Cdztbj0 + ",");
		// }
		// if (Cdztbj1 != null) {
		// strSql1.append("cdztbj1,");
		// strSql2.append(Cdztbj1 + ",");
		// }

		if (workid == 0) // 上班
		{
			strSql1.append("uptag0,");
			strSql2.append("1,");
			strSql1.append("uptag1,");
			strSql2.append("0,");
		} else {// 下班
			strSql1.append("uptag0,");
			strSql2.append("0,");
			strSql1.append("uptag1,");
			strSql2.append("1,");

		}
		// if (Checktime1 != null) {
		strSql1.append("checktime1,");
		strSql2.append("sysdate,");
		// }
		// if (Checktime0 != null) {
		strSql1.append("checktime0,");
		strSql2.append("sysdate,");
		// }
		// if (Imagename0 != null) {
		// strSql1.append("imagename0,");
		// strSql2.append("'" + Imagename0 + "',");
		// }
		// if (Imagename1 != null) {
		// strSql1.append("imagename1,");
		// strSql2.append("'" + Imagename1 + "',");
		// }
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n begin ");
		strSql.append("\n     select id into v_id from checkwork where epid=" + Epid + " and classday='" + Classday + "' and classid=" + Classid + "; ");
		if (workid == 0) // 上班
		{
			// qry += "\n update checkwork set checktime0=sysdate,uptag0=1,cdztbj0=v_cdztbj0,imagename0='"+model.imagename0+"',remark0='"+model.remark0+"',lastop='"+model.lastop+"' where id=v_id; ";
			strSql.append("\n     update checkwork set checktime0=sysdate,uptag0=1, " + strSql3.toString() + " where id=v_id; ");
		} else {
			// qry += "\n update checkwork set checktime1=sysdate,uptag1=1,cdztbj1=v_cdztbj1,imagename1='"+model.imagename1+"',remark1='"+model.remark1+"',lastop='"+model.lastop+"' where id=v_id; ";
			strSql.append("\n     update checkwork set checktime1=sysdate,uptag1=1, " + strSql3.toString() + "  where id=v_id; ");
		}
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN ");

		strSql.append("   v_id:=Checkwork_id.nextval; ");
		strSql.append("   insert into Checkwork (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("  select v_id into :id from dual; ");
		strSql.append(" end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.FLOAT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			Id = Float.parseFloat(param.get("id").getParamvalue().toString());
			errmess = Id.toString();
			return 1;
		}
	}

	// 增加记录
	public int Append(int workid, String pictjson) {
		if (Epid == null || Epid == 0) {
			errmess = "职员id无效！";
			return 0;
		}

		if (workid == 0) // 上班
		{
			if (Remark0 == null || Remark0.equals("")) {
				errmess = "请输入备注0！";
				return 0;

			}
			// model.imagename0 = retcs.Substring(1, retcs.Length - 1);
			// model.imagename1 = "";
		} else {
			if (Remark1 == null || Remark1.equals("")) {
				errmess = "请输入备注1！";
				return 0;

			}
			// model.imagename0 = "";
			// model.imagename1 = retcs.Substring(1, retcs.Length - 1);
		}
		if (Classid == null) {
			errmess = "classid无效！";
			return 0;
		}
		// checkid:0=正常考勤 1=旷工2=病假 3=事假 4=公休
		if (Checkid == null || Checkid > 4) {
			errmess = "Checkid无效！";
			return 0;

		}
		if (Classday == null || Classday.equals("")) {
			errmess = "Classday无效！";
			return 0;

		}
		if (Exists(workid)) // 判断是否已有上，下班记录
			return 0;

		if (pictjson == null || pictjson.equals("")) {
			errmess = "图像文件无效！";
			return 0;
		}
		JSONObject jsonObject = JSONObject.fromObject(pictjson);
		if (!jsonObject.has("base64string")) {
			errmess = "未传入图片值参数:base64string！";
			return 0;
		}
		String base64string = jsonObject.getString("base64string");
		String retcs = Func.Base64UpFastDFS(base64string);
		if (retcs.substring(0, 1).equals("1")) {
			if (workid == 0)
				Imagename0 = retcs.substring(1);
			else
				Imagename1 = retcs.substring(1);
		} else {
			errmess = "头像图片上传失败！" + retcs.substring(1);
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		StringBuilder strSql3 = new StringBuilder();
		strSql3.append(" lastop='" + Lastop + "',lastdate=sysdate");
		strSql1.append("id,");
		strSql2.append("v_id,");
		// if (Checkid != null) {
		strSql1.append("checkid,");
		strSql2.append(Checkid + ",");
		// }

		if (Epid != null) {
			strSql1.append("epid,");
			strSql2.append(Epid + ",");
		}
		if (Classday != null) {
			strSql1.append("classday,");
			strSql2.append("'" + Classday + "',");
		}
		if (Classid != null) {
			strSql1.append("classid,");
			strSql2.append(Classid + ",");
		}
		if (Remark0 != null) {
			strSql1.append("remark0,");
			strSql2.append("'" + Remark0 + "',");
			strSql3.append(",remark0=" + Remark0 + "'");
		}
		if (Remark1 != null) {
			strSql1.append("remark1,");
			strSql2.append("'" + Remark1 + "',");
			strSql3.append(",remark1=" + Remark1 + "'");
		}
		if (Days != null) {
			strSql1.append("days,");
			strSql2.append(Days + ",");
		}
		// if (Cdztbj0 != null) {
		strSql1.append("cdztbj0,");
		strSql2.append("v_cdztbj0,");
		// }
		// if (Cdztbj1 != null) {
		strSql1.append("cdztbj1,");
		strSql2.append("v_cdztbj1,");
		// }

		if (workid == 0) // 上班
		{
			strSql1.append("uptag0,");
			strSql2.append("1,");
			strSql1.append("uptag1,");
			strSql2.append("0,");
			strSql3.append(",uptag0=1,uptag1=0,cdztbj0=v_cdztbj0");
			strSql3.append(",checktime0=sysdate");
		} else {// 下班
			strSql1.append("uptag0,");
			strSql2.append("0,");
			strSql1.append("uptag1,");
			strSql2.append("1,");
			strSql3.append(",uptag0=0,uptag1=1,cdztbj1=v_cdztbj1");
			strSql3.append(",checktime1=sysdate");

		}
		if (Imagename0 != null) {
			strSql1.append("imagename0,");
			strSql2.append("'" + Imagename0 + "',");
			strSql3.append(",imagename0='" + Imagename0 + "'");
		}
		if (Imagename1 != null) {
			strSql1.append("imagename1,");
			strSql2.append("'" + Imagename1 + "',");
			strSql3.append(",imagename1='" + Imagename1 + "'");
		}

		// if (Checktime1 != null) {
		strSql1.append("checktime1,");
		strSql2.append("sysdate,");
		// }
		// if (Checktime0 != null) {
		strSql1.append("checktime0,");
		strSql2.append("sysdate,");

		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		strSql1.append("lastdate");
		strSql2.append("sysdate");

		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n begin ");

		strSql.append("\n   v_cdztbj0:=0;  v_cdztbj1:=0;");

		if (workid == 0) // 上班
		{
			// 取上班时间
			strSql.append("\n     select case " + Classid + " when 0 then a.ontime when 1 then a.ontime1 when 2 then a.ontime2 end  into v_time from warehouse a join employe b on a.houseid=b.houseid where b.epid=" + Epid
					+ ";");
			strSql.append("\n     if  to_char(sysdate,'hh24:mi')>v_time then v_cdztbj0:=1; end if;"); // 考勤时间大于上班时间，迟到
		} else {
			// 取下班时间
			strSql.append("\n     select case " + Classid + " when 0 then a.offtime when 1 then a.offtime1 when 2 then a.offtime2 end  into v_time from warehouse a join employe b on a.houseid=b.houseid where b.epid="
					+ Epid + ";");
			strSql.append("\n     if  to_char(sysdate,'hh24:mi')<v_time then v_cdztbj1:=1; end if;"); // 考勤时间小于班班时间，早退
		}
		strSql.append("\n   begin ");

		strSql.append("\n     select id into v_id from checkwork where epid=" + Epid + " and classday='" + Classday + "' and classid=" + Classid + "; ");
		strSql.append("\n     update checkwork set " + strSql3.toString() + " where id=v_id; ");

		// if (workid == 0) // 上班
		// {
		// strSql.append("\n update checkwork set checktime0=sysdate,uptag0=1,cdztbj0=v_cdztbj0,imagename0='" + Imagename0 + "',remark0='" + Remark0 + "',lastop='" + Lastop + "' where id=v_id; ");
		// } else {
		// strSql.append("\n update checkwork set checktime1=sysdate,uptag1=1,cdztbj1=v_cdztbj1,imagename1='" + Imagename1 + "',remark1='" + Remark1 + "',lastop='" + Lastop + "' where id=v_id; ");
		// }
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN ");
		strSql.append("\n     v_id:=checkwork_id.nextval;"); // checkid:0=正常考勤 1=旷工 2=病假 3=事假 4=公休
		strSql.append("\n   insert into Checkwork (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");

		// if (workid == 0) //上班
		// {
		// strSql.append( "\n INSERT INTO CHECKWORK (ID,EPID,CLASSDAY,CLASSID,CHECKID,CHECKTIME0,CHECKTIME1,UPTAG0,UPTAG1,cdztbj0,cdztbj1");
		// strSql.append( "\n ,imagename0,imagename1,REMARK0,REMARK1,DAYS,LASTOP)");
		// strSql.append( "\n values (v_id,"+Epid+",'"+Classday+"' ,"+Classid+",0,sysdate,sysdate,1,0,v_cdztbj0,v_cdztbj1");
		// strSql.append("\n ,'"+Imagename0+"','"+Imagename1+"','"+Remark0+"','"+Remark1+"',0,'"+Lastop+"'); ");
		//
		// }
		// else
		// {
		// strSql.append("\n INSERT INTO CHECKWORK (ID,EPID,CLASSDAY,CLASSID,CHECKID,CHECKTIME0,CHECKTIME1,UPTAG0,UPTAG1,cdztbj0,cdztbj1");
		// strSql.append( "\n ,imagename0,imagename1,REMARK0,REMARK1,DAYS,LASTOP)");
		// strSql.append( "\n values (v_id,"+Epid+",'"+Classday+"',"+Classid+",0,sysdate,sysdate,0,1,v_cdztbj0,v_cdztbj1");
		// strSql.append( "\n ,'"+Imagename0+"','"+Imagename1+"','"+Remark0+"','"+Remark1+"',0,'"+Lastop+"'); ";
		// }

		strSql.append("\n  select v_id into :id from dual; ");
		strSql.append("\n end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.FLOAT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			Id = Float.parseFloat(param.get("id").getParamvalue().toString());
			errmess = Id.toString();
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Checkwork set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Epid != null) {
			strSql.append("epid=" + Epid + ",");
		}
		if (Classday != null) {
			strSql.append("classday='" + Classday + "',");
		}
		if (Classid != null) {
			strSql.append("classid=" + Classid + ",");
		}
		if (Checkid != null) {
			strSql.append("checkid=" + Checkid + ",");
		}
		if (Checktime0 != null) {
			strSql.append("checktime0='" + Checktime0 + "',");
		}
		if (Remark0 != null) {
			strSql.append("remark0='" + Remark0 + "',");
		}
		if (Uptag0 != null) {
			strSql.append("uptag0=" + Uptag0 + ",");
		}
		if (Checktime1 != null) {
			strSql.append("checktime1='" + Checktime1 + "',");
		}
		if (Remark1 != null) {
			strSql.append("remark1='" + Remark1 + "',");
		}
		if (Uptag1 != null) {
			strSql.append("uptag1=" + Uptag1 + ",");
		}
		if (Days != null) {
			strSql.append("days=" + Days + ",");
		}
		if (Cdztbj0 != null) {
			strSql.append("cdztbj0=" + Cdztbj0 + ",");
		}
		if (Cdztbj1 != null) {
			strSql.append("cdztbj1=" + Cdztbj1 + ",");
		}
		if (Imagename0 != null) {
			strSql.append("imagename0='" + Imagename0 + "',");
		}
		if (Imagename1 != null) {
			strSql.append("imagename1='" + Imagename1 + "',");
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
		strSql.append(" FROM Checkwork a ");
		strSql.append(" left outer join employe b on a.epid=b.epid ");

		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据 分页获取考勤本店铺记录列表
	public Table GetTable1(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		// strSql.append(" FROM Checkwork ");
		strSql.append(" from employe a ");
		strSql.append(" left outer join checkwork b on a.epid=b.epid and b.classday='" + Classday + "' and b.classid=" + Classid);
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据 分页获取考勤本店铺记录列表
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		// strSql.append(" FROM Checkwork ");
		// strSql.append(" from employe a ");
		// strSql.append(" left outer join checkwork b on a.epid=b.epid and b.classday='" + Classday + "' and b.classid=" + Classid);

		strSql.append(" from checkwork a join employe b on a.epid=b.epid");
		strSql.append(" join warehouse c on b.houseid=c.houseid");

		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists(int workid) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Checkwork");
		if (workid == 0) // 上班
		{
			strSql.append("select id from checkwork");
			strSql.append(" where epid=" + Epid + " and classday='" + Classday + "' and classid=" + Classid + " and uptag0=1");
			if (DbHelperSQL.Exists(strSql.toString())) {
				errmess = "职员已有上班考勤记录！";
				return true;
			}
		} else // 下班
		{
			strSql.append("select id from checkwork");
			strSql.append(" where epid=" + Epid + " and classday='" + Classday + "' and classid=" + Classid + " and uptag1=1");
			if (DbHelperSQL.Exists(strSql.toString())) {
				errmess = "职员已有下班考勤记录！";
				return true;
			}
		}
		return false;
	}

	// 获取考勤时间
	public int doGetcheckwork() {
		String qry = "declare ";
		qry += "\n   v_epid number; ";
		// qry += "\n v_classday varchar2(10);";// --考勤日
		// qry += "\n v_weekday varchar2(10);";// --星期几
		// qry += "\n v_classtime varchar2(10);"; // --考勤时间
		// qry += "\n v_workid number;";// -- 2=未到考勤时间 0=上班 1=下班
		// qry += "\n v_checktime varchar2(5); ";// --上班时间
		qry += "\n begin ";
		qry += "\n   v_epid:=" + Epid + "; ";

		qry += "\n   select to_char(sysdate,'yyyy-mm-dd'),to_char(sysdate,'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''')";
		qry += "\n   ,nvl(a.ontime,'00:00'),nvl(a.offtime,'00:00'),nvl(a.ontime1,'00:00'),nvl(a.offtime1,'00:00'),nvl(a.ontime2,'00:00'),nvl(a.offtime2,'00:00') ";
		qry += "\n   into :classday,:weekday,:ontime0,:offtime0,:ontime1,:offtime1,:ontime2,:offtime2";
		qry += "\n   from warehouse a join employe b on a.houseid=b.houseid where b.epid=v_epid; ";
		qry += "\n end;";

		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("classday", new ProdParam(Types.VARCHAR));
		param.put("weekday", new ProdParam(Types.VARCHAR));

		param.put("ontime0", new ProdParam(Types.VARCHAR));
		param.put("offtime0", new ProdParam(Types.VARCHAR));
		param.put("ontime1", new ProdParam(Types.VARCHAR));
		param.put("offtime1", new ProdParam(Types.VARCHAR));
		param.put("ontime2", new ProdParam(Types.VARCHAR));
		param.put("offtime2", new ProdParam(Types.VARCHAR));
		// 调用
		int ret = DbHelperSQL.ExecuteProc(qry, param);

		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "\"msg\":\"操作成功！\",\"CLASSDAY\":\"" + param.get("classday").getParamvalue().toString() + "\""//
					+ ",\"WEEKDAY\":\"" + param.get("weekday").getParamvalue().toString() + "\""//
					+ ",\"ONTIME0\":\"" + param.get("ontime0").getParamvalue().toString() + "\"" // 班次 0=早班 1=中班 2=晚班
					+ ",\"OFFTIME0\":\"" + param.get("offtime0").getParamvalue().toString() + "\"" // 班次 0=早班 1=中班 2=晚班
					+ ",\"ONTIME1\":\"" + param.get("ontime1").getParamvalue().toString() + "\"" // 班次 0=早班 1=中班 2=晚班
					+ ",\"OFFTIME1\":\"" + param.get("offtime1").getParamvalue().toString() + "\"" // 班次 0=早班 1=中班 2=晚班
					+ ",\"ONTIME2\":\"" + param.get("ontime2").getParamvalue().toString() + "\"" // 班次 0=早班 1=中班 2=晚班
					+ ",\"OFFTIME2\":\"" + param.get("offtime2").getParamvalue().toString() + "\""; // 班次 0=早班 1=中班 2=晚班

			return 1;
		}
	}

}