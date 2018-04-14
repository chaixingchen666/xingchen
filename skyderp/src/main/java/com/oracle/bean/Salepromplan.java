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
//  @author:sunhong  @date:2017-11-15 21:31:37
//*************************************
public class Salepromplan implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private Date Notedate;
	private String Noteno;
	private String Actionname;
	private String Kzxx;
	//	kzxx
	//	5:1=适用于特价商品
	//	4:储值卡支付控制:0=比例，1=金额
	//	3:0=全场商品1=促销商品
	//	2:要计算积分
	//	1:启用会员折中折
	private String Handno;
	private Integer Ntid; //ntid:0=买满X元 打Y折 送Z商品,1=买满X元 让利Y元 送Z商品,	2=买满X件 打Y折 送Z商品,3=买满X件 让利Y元 送Z商品  
	private String Begindate;
	private String Enddate;
	private String Begintime;
	private String Endtime;
	private Integer Statetag;
	private Float Totalamt;
	private Float Totalcurr;
	private Float Cutcurr;
	private Float Zdiscount;
	private Float Vrate;
	private Float Vcurr;
	private String Remark;
	private String Operant;
	private String Saledaylist;
	//	XXXXXXX
	//	1-6=周一到周六
	//	7=周日
	private String Checkman;
	private Date Lastdate;
	private String errmess;

	//private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setNotedate(Date notedate) {
		this.Notedate = notedate;
	}

	public String getNotedate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Notedate);
	}

	public void setNoteno(String noteno) {
		this.Noteno = noteno;
	}

	public String getNoteno() {
		return Noteno;
	}

	public void setActionname(String actionname) {
		this.Actionname = actionname;
	}

	public String getActionname() {
		return Actionname;
	}

	public void setKzxx(String kzxx) {
		this.Kzxx = kzxx;
	}

	public String getKzxx() {
		return Kzxx;
	}

	public void setHandno(String handno) {
		this.Handno = handno;
	}

	public String getHandno() {
		return Handno;
	}

	public void setNtid(Integer ntid) {
		this.Ntid = ntid;
	}

	public Integer getNtid() {
		return Ntid;
	}

	public void setBegindate(String begindate) {
		this.Begindate = begindate;
	}

	//	public String getBegindate() {
	//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//		return df.format(Begindate);
	//	}

	public void setEnddate(String enddate) {
		this.Enddate = enddate;
	}

	//	public String getEnddate() {
	//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//		return df.format(Enddate);
	//	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setTotalamt(Float totalamt) {
		this.Totalamt = totalamt;
	}

	public Float getTotalamt() {
		return Totalamt;
	}

	public void setTotalcurr(Float totalcurr) {
		this.Totalcurr = totalcurr;
	}

	public Float getTotalcurr() {
		return Totalcurr;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setOperant(String operant) {
		this.Operant = operant;
	}

	public String getOperant() {
		return Operant;
	}

	public void setSaledaylist(String saledaylist) {
		this.Saledaylist = saledaylist;
	}

	public String getSaledaylist() {
		return Saledaylist;
	}

	public void setCheckman(String checkman) {
		this.Checkman = checkman;
	}

	public String getCheckman() {
		return Checkman;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	//public void setAccbegindate(String accbegindate) {
	//	  this.Accbegindate = accbegindate;
	//}
	//删除记录 
	public int Delete() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n  v_operant varchar2(20); ");
		strSql.append("\n  v_retcs varchar2(200); ");
		strSql.append("\n  v_statetag number(1); ");
		strSql.append("\n begin ");
		strSql.append("\n  begin");
		strSql.append("\n   select statetag,operant into v_statetag,v_operant from salepromplan where accid=" + Accid + " and noteno='" + Noteno + "' and statetag<2;");
		strSql.append("\n  EXCEPTION WHEN NO_DATA_FOUND THEN ");
		strSql.append("\n    v_retcs:='0未找到促销计划单!'; ");
		strSql.append("\n    goto exit; ");
		strSql.append("\n  end;");

		strSql.append("\n   if v_statetag>0 then ");
		strSql.append("\n      v_retcs:= '0当前单据已提交，不允许删除！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if;");
		strSql.append("\n   if v_operant<>'" + Operant + "' then ");
		strSql.append("\n      v_retcs:= '0不允许删除其他用户的单据！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if;");

		strSql.append("\n   update salepromplan set statetag=2,lastdate=sysdate");
		strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0; ");

		strSql.append("\n   v_retcs:='1操作成功！';");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		strSql.append("\n end; ");
		// System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

	//增加记录 
	public int Append() {
		if (Ntid < 0 || Ntid > 3) {
			errmess = "ntid不是一个有效值！";
			return 0;
		}

		String qry = "declare ";
		qry += "\n   v_accid  number;";
		qry += "\n   v_operant nvarchar2(10);";
		qry += "\n   v_noteno nvarchar2(20);";
		qry += "\n   v_id  number;";
		qry += "\n   v_ntid  number(1);";
		qry += "\n   v_retcs nvarchar2(400);";
		qry += "\n   v_progname nvarchar2(20);";
		qry += "\n   v_retbj number(1); ";
		qry += "\n begin ";
		qry += "\n  begin ";
		//ntid:0=买满X元 打Y折 送Z商品,1=买满X元 让利Y元 送Z商品,	2=买满X件 打Y折 送Z商品,3=买满X件 让利Y元 送Z商品  
		qry += "\n    v_ntid:=" + Ntid + ";";
		qry += "\n    select 'CJ'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from salepromplan";
		qry += "\n    where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'CJ'||To_Char(SYSDATE,'yyyymmdd');";
		qry += "\n    v_id:=salepromplan_ID.NEXTVAL;";
		qry += "\n    insert into salepromplan (id, accid,noteno,notedate,ntid,statetag,totalamt,totalcurr,saledaylist,remark,handno,operant,checkman,lastdate,begindate,enddate)";
		qry += "\n    values (v_id," + Accid + ",v_noteno,sysdate,v_ntid,0,0,0,'1111111','','','" + Operant
				+ "','',sysdate,trunc(sysdate+1),to_date(to_char(trunc(sysdate+31),'yyyy-mm-dd')||' 23:59:59','yyyy-mm-dd hh24:mi:ss'));";
		qry += "\n    v_retbj:=1;";
		qry += "\n    select '\"msg\":\"操作成功\",\"ID\":\"'||id||'\",\"NOTENO\":\"'||noteno||'\",\"NOTEDATE\":\"'||to_char(notedate,'yyyy-mm-dd hh24:mi:ss')||'\"'";
		qry += "\n    ||',\"BEGINDATE\":\"'||to_char(begindate,'yyyy-mm-dd')||'\",\"BEGINTIME\":\"'||to_char(begindate,'hh24:mi')||'\"'";
		qry += "\n    ||',\"ENDDATE\":\"'||to_char(enddate,'yyyy-mm-dd')||'\",\"ENDTIME\":\"'||to_char(enddate,'hh24:mi')||'\",\"SALEDAYLIST\":\"'||saledaylist||'\"'";
		qry += "\n    into v_retcs from salepromplan where id=v_id;  ";
		qry += "\n    insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n    values (LOGRECORD_id.nextval," + Accid + ",'促销计划单','【新增单据】单据号:'||v_noteno,'" + Operant + "');";
		qry += "\n   exception";
		qry += "\n   when others then ";
		qry += "\n   begin";
		qry += "\n     rollback;";
		qry += "\n     v_retbj:=0;  v_retcs:='\"msg\":\"操作失败，请重试！\"';";
		qry += "\n   end;";
		qry += "\n  end; ";
		qry += "\n  select v_retbj,v_retcs into :retbj,:retcs from dual;";
		qry += "\n end;";
		//				 System.out.println(qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retbj", new ProdParam(Types.INTEGER));
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常1！";
			return 0;
		}
		// int retbj =
		// Integer.parseInt(param.get("retbj").getParamvalue().toString());
		errmess = param.get("retcs").getParamvalue().toString();

		// errmess = retcs.substring(1);
		return Integer.parseInt(param.get("retbj").getParamvalue().toString());
	}

	//修改记录 
	public int Update() {
		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "单据号无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("\n   update SALEPROMPLAN set ");

		strSql.append(" notedate=sysdate,");
		//		if (Notedate != null) {
		//			strSql.append("notedate='" + Notedate + "',");
		//		}
		//		if (Noteno != null) {
		//			strSql.append("noteno='" + Noteno + "',");
		//		}
		if (Actionname != null) {
			if (Actionname.length() <= 0) {
				errmess = "活动名称不允许为空！";
				return 0;
			}
			strSql.append("actionname='" + Actionname + "',");
		}
		if (Kzxx != null) {
			strSql.append("kzxx='" + Kzxx + "',");
		}
		if (Handno != null) {
			strSql.append("handno='" + Handno + "',");
		}
		if (Ntid != null) {
			strSql.append("ntid=" + Ntid + ",");
		}
		if (Begindate != null) {
			//			Begindate += Begindate + Begintime + ":00";
			if (!Func.isValidDate(Begindate)) {
				errmess = "活动开始日期格式无效！";
				return 0;
			}
			strSql.append("begindate=to_date('" + Begindate + " " + Begintime + ":00" + "','yyyy-mm-dd hh24:mi:ss'),");
		}
		if (Enddate != null) {
			if (!Func.isValidDate(Begindate)) {
				errmess = "活动结束日期格式无效！";
				return 0;
			}
			strSql.append("enddate=to_date('" + Enddate + " " + Endtime + ":00" + "','yyyy-mm-dd  hh24:mi:ss'),");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Totalamt != null) {
			strSql.append("totalamt=" + Totalamt + ",");
		}
		if (Totalcurr != null) {
			strSql.append("totalcurr=" + Totalcurr + ",");
		}
		if (Cutcurr != null) {
			strSql.append("Cutcurr=" + Cutcurr + ",");
		}
		if (Zdiscount != null) {
			strSql.append("Zdiscount=" + Zdiscount + ",");
		}
		if (Vrate != null) {
			strSql.append("Vrate=" + Vrate + ",");
		}
		if (Vcurr != null) {
			strSql.append("Vcurr=" + Vcurr + ",");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		//		if (Operant != null) {
		//		}
		if (Saledaylist != null) {
			strSql.append("saledaylist='" + Saledaylist + "',");
			//XXXXXXX
			//1-6=周一到周六
			//7=周日
		}
		//		if (Checkman != null) {
		//			strSql.append("checkman='" + Checkman + "',");
		//		}
		strSql.append("operant='" + Operant + "',");
		strSql.append("lastdate=sysdate");
		strSql.append("  where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n  commit;");
		strSql.append("\n end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	//撤单 
	public int Cancel() {
		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "单据号无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n   v_count number;");
		strSql.append("\n   v_statetag number(1);");
		strSql.append("\n   v_checkman varchar2(20);");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n   v_mess varchar2(200);");
		strSql.append("\n begin");
		strSql.append("\n   select checkman,statetag into v_checkman,v_statetag from salepromplan where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n   if length(trim(v_checkman))>0 then ");
		strSql.append("\n      v_retcs:='0单据已审核！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		strSql.append("\n   update SALEPROMPLAN set statetag=0,lastdate=sysdate");
		strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n   insert into LOGRECORD (id,accid,progname,remark,lastop)");
		strSql.append("\n   values (LOGRECORD_id.nextval," + Accid + ",'促销计划','【撤单】单据号:" + Noteno + "','" + Operant + "');");
		strSql.append("\n   v_retcs:='1操作成功！';");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		strSql.append("\n end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

	// 审核/反审
	public int Check(int bj) {
		String qry = "declare";
		qry += "\n   v_count number;";
		qry += "\n   v_statetag number(1);";
		qry += "\n   v_checkman varchar2(20);";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_mess varchar2(200);";
		qry += "\n begin";
		qry += "\n   select checkman,statetag into v_checkman,v_statetag from salepromplan where accid=" + Accid + " and noteno='" + Noteno + "';";
		if (bj == 1) {// 审核
			qry += "\n   if length(trim(v_checkman))>0 then ";
			qry += "\n      v_retcs:='0单据已审核！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   if v_statetag=0 then ";
			qry += "\n      v_retcs:='0单据未提交！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update salepromplan set checkman='" + Operant + "' where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=1;";
			qry += "\n   v_mess:='【审核】';";
		} else {// 取消审核
			qry += "\n   if v_checkman<>'" + Operant + "' then ";
			qry += "\n      v_retcs:='0系统不允许取消其他用户审核的单据！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update salepromplan set checkman='' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【取消审核】';";
		}
		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'促销计划单' ,v_mess||'单据号:" + Noteno + "','" + Operant + "');";
		qry += "\n   v_retcs:='1操作成功！';";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";
//		System.out.println(qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM SALEPROMPLAN a");
		if (strWhere.length() > 0) {
			strSql.append("\n where " + strWhere);
		}
		System.out.println(strSql.toString());
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM SALEPROMPLAN a");
		if (strWhere.length() > 0) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from SALEPROMPLAN");

		return false;
	}
}