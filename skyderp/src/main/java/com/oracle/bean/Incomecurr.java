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
import com.flyang.main.pFunc;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-26 17:30:38
//*************************************
public class Incomecurr implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private String Noteno;
	private Date Notedate;
	private Long Custid;
	private String Handno;
	private String Phno;
	private Float Curr;
	private String Remark;
	private String Operant;
	private String Checkman;
	private Integer Statetag;
	private String Notedatestr;
	private String Lastop;
	private Date Lastdate;
	private Long Payid;
	private String Ynoteno;
	private Integer Ywly;
	private Long Houseid;
	private Long Handmanid;
	private String errmess;
	private String Calcdate = "";
	private Integer Fs = 0;// 0=收款 1=折让

	public void setCalcdate(String calcdate) {
		this.Calcdate = calcdate;
	}

	public void setFs(Integer fs) {
		this.Fs = fs;
	}

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
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

	public void setNoteno(String noteno) {
		this.Noteno = noteno;
	}

	public String getNoteno() {
		return Noteno;
	}

	public void setNotedate(Date notedate) {
		this.Notedate = notedate;
	}

	public String getNotedate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Notedate);
	}

	public void setCustid(Long custid) {
		this.Custid = custid;
	}

	public Long getCustid() {
		return Custid;
	}

	public void setHandno(String handno) {
		this.Handno = handno;
	}

	public String getHandno() {
		return Handno;
	}

	public void setCurr(Float curr) {
		this.Curr = curr;
	}

	public Float getCurr() {
		return Curr;
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

	public void setCheckman(String checkman) {
		this.Checkman = checkman;
	}

	public String getCheckman() {
		return Checkman;
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

	public void setPayid(Long payid) {
		this.Payid = payid;
	}

	public Long getPayid() {
		return Payid;
	}

	public void setYnoteno(String ynoteno) {
		this.Ynoteno = ynoteno;
	}

	public String getYnoteno() {
		return Ynoteno;
	}

	public void setYwly(Integer ywly) {
		this.Ywly = ywly;
	}

	public Integer getYwly() {
		return Ywly;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Noteno.equals("")) {
			errmess = "单据号无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n  v_retcs varchar2(200);");
		strSql.append("\n  v_checkman varchar2(20);");
		strSql.append("\n  v_count number;");
		strSql.append("\n begin");
		//		if (delbj == 1) {
		//			strSql.append("\n  update incomecurr set statetag=2 ");
		//			strSql.append("\n  where accid=" + Accid + " and noteno='" + Noteno + "';");
		////			strSql.append("\n  delete from incomelink where accid=" + Accid + " and pnoteno='" + Noteno + "';");
		//		} else {
		// notetype:0销售(退库) 1物料销售（退库）
		// fs:0销售收款勾销售单1销售收款勾退货单2销售退款勾退货单
		strSql.append("\n  select checkman into v_checkman from incomecurr where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n  if v_checkman<>'' then");
		strSql.append("\n     v_retcs:='0收款单已审核，不允许删除！';");
		strSql.append("\n     goto exit;");
		strSql.append("\n  end if;");

		strSql.append("\n  select count(*) into v_count from incomelink where accid=" + Accid + " and pnoteno='" + Noteno + "';");
		strSql.append("\n  if v_count>0 then ");
		strSql.append("\n     v_retcs:='0收款单有勾单记录，不允许删除！';");
		strSql.append("\n     goto exit;");
		strSql.append("\n  end if;");

		strSql.append("\n  update incomecurr set statetag=2 ");
		// strSql.Append(" where id=" + id + " and accid=" + accid + " and noteno='" + noteno + "'");
		strSql.append("\n  where accid=" + Accid + " and statetag=0 and noteno='" + Noteno + "';");
		// 删除勾单标志
		//			strSql.append("\n delete from incomelink where accid=" + Accid + " and pnoteno='" + Noteno + "';");
		//		}
		strSql.append("\n  v_retcs:='1删除成功！';");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs into :retcs from dual;");
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
		if (retcs.substring(0, 1).equals("1")) {
			if (Fs == 0)
				pFunc.myWriteLog(Accid, "销售收款", "【删除单据】" + Noteno + ",", Lastop);// 写日志
			else
				pFunc.myWriteLog(Accid, "销售折让", "【删除单据】" + Noteno + ",", Lastop);// 写日志
			return 1;
		} else
			return 0;

		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// } else {
		// if (Fs == 0)
		// pFunc.myWriteLog(Accid, "销售收款", "【删除单据】" + Noteno + ",", Lastop);// 写日志
		// else
		// pFunc.myWriteLog(Accid, "销售折让", "【删除单据】" + Noteno + ",", Lastop);// 写日志
		// errmess = "删除成功！";
		// return 1;
		// }
	}

	// 增加记录
	public int Append() {
		if (Custid == null || Custid == 0) {
			errmess = "custid无效！";
			return 0;
		}
		if (Houseid == null || Houseid == 0) {
			errmess = "Houseid无效！";
			return 0;
		}
		if (Fs == 0) {// 收款
			if (Payid == null || Payid == 0) {
				errmess = "Payid无效！";
				return 0;
			}
		} else {
			Payid = (long) 0; // 折让
		}
		if (Curr == null || Curr == 0) {
			errmess = "金额无效！";
			return 0;
		}
		if (!Func.isNull(Notedatestr)) {
			if (Func.subString(Notedatestr, 1, 10).compareTo(Calcdate) <= 0) {
				errmess = "单据日期只能在系统登账日 " + Calcdate + " 之后！";
				return 0;
			}
		}

		if (Statetag == null)
			Statetag = 0;
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// if (Noteno != null) {
		strSql1.append("noteno,");
		strSql2.append("v_noteno,");
		if (!Func.isNull(Notedatestr)) {
			strSql1.append("notedate,");
			strSql2.append("to_date('" + Notedatestr + "','yyyy-mm-dd hh24:mi:ss'),");
		} else {
			strSql1.append("notedate,");
			strSql2.append("sysdate,");
		}
		strSql1.append("custid,");
		strSql2.append(Custid + ",");
		// if (Houseid != null) {
		strSql1.append("houseid,");
		strSql2.append(Houseid + ",");
		strSql1.append("payid,");
		strSql2.append(Payid + ",");
		strSql1.append("curr,");
		strSql2.append(Curr + ",");
		if (Handno != null) {
			strSql1.append("handno,");
			strSql2.append("'" + Handno + "',");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Checkman != null) {
			strSql1.append("checkman,");
			strSql2.append("'" + Checkman + "',");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Handmanid != null) {
			strSql1.append("Handmanid,");
			strSql2.append(Handmanid + ",");
		}
		if (Ynoteno != null) {
			strSql1.append("ynoteno,");
			strSql2.append("'" + Ynoteno + "',");
		}
		if (Phno != null) {
			strSql1.append("Phno,");
			strSql2.append("'" + Phno + "',");
		}
		if (Ywly != null) {
			strSql1.append("ywly,");
			strSql2.append(Ywly + ",");
		}
		if (!Func.isNull(Operant))
			Lastop = Operant;

		strSql1.append("operant,");
		strSql2.append("'" + Lastop + "',");
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");

		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_operant varchar2(20);");
		strSql.append("\n   v_noteno varchar2(20);");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n   v_retbj number(1); ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_progname varchar2(100);");

		strSql.append("\n begin ");
		strSql.append("\n   v_retbj:=0;");
		strSql.append("\n     select count(*) into v_count from customer where accid=" + Accid + " and custid=" + Custid + " and statetag=1;");
		strSql.append("\n     if v_count=0 then");
		strSql.append("\n        v_retcs:='客户无效！';");
		strSql.append("\n        goto exit;");
		strSql.append("\n     end if;");
		strSql.append("\n     select count(*) into v_count from warehouse where accid=" + Accid + " and Houseid=" + Houseid + " and statetag=1;");
		strSql.append("\n     if v_count=0 then");
		strSql.append("\n        v_retcs:='店铺无效！';");
		strSql.append("\n        goto exit;");
		strSql.append("\n     end if;");
		if (Handmanid != null && Handmanid > 0) {
			strSql.append("\n   select count(*) into v_count from employe where accid=" + Accid + " and epid=" + Handmanid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then");
			strSql.append("\n      v_retcs:='0业务员无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Fs == 0) {
			strSql.append("\n     select count(*) into v_count from payway where accid=" + Accid + " and Payid=" + Payid + " and statetag=1;");
			strSql.append("\n     if v_count=0 then");
			strSql.append("\n        v_retcs:='结算方式无效！';");
			strSql.append("\n        goto exit;");
			strSql.append("\n     end if;");
		}

		strSql.append("\n begin ");
		if (Fs == 0) {
			strSql.append("\n     select 'SK'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from incomecurr");
			strSql.append("\n     where accid=" + Accid + "  and LENGTH(noteno)=15 and substr(noteno,1,10)= 'SK'||To_Char(SYSDATE,'yyyymmdd');");
			strSql.append("\n     v_progname:='销售收款';");
		} else {
			strSql.append("\n     select 'SZ'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from incomecurr");
			strSql.append("\n     where accid=" + Accid + "  and LENGTH(noteno)=15 and substr(noteno,1,10)= 'SZ'||To_Char(SYSDATE,'yyyymmdd');");
			strSql.append("\n     v_progname:='销售折让';");
		}
		strSql.append("\n     v_id:=incomecurr_ID.NEXTVAL;");

		strSql.append("\n     insert into incomecurr (" + strSql1.toString() + ")");
		strSql.append("\n     values (" + strSql2.toString() + ");");
		if (!Func.isNull(Ynoteno)) {
			// notetype:0销售(退库) 1物料销售（退库）
			// fs:0销售收款勾销售单1销售收款勾退货单2销售退款勾退货单
			strSql.append("\n  update incomecurr set gdcurr=curr where accid=" + Accid + " and id=v_id;");
			strSql.append("\n  update wareouth set gdcurr=gdcurr+" + Curr + " where accid=" + Accid + " and noteno='" + Ynoteno + "';");
			strSql.append("\n  insert into incomelink (id,accid,notetype,noteno,pnoteno,fs,curr,lastop)");
			strSql.append("\n  values (incomelink_id.nextval," + Accid + ",0,'" + Ynoteno + "',v_noteno,0," + Curr + ",'" + Lastop + "');");
		}
		strSql.append("\n     v_retbj:=1;  ");
		strSql.append("\n     select '\"msg\":\"操作成功\",\"ID\":\"'||id||'\",\"NOTENO\":\"'||noteno||'\",\"NOTEDATE\":\"'||to_char(notedate,'yyyy-mm-dd hh24:mi:ss')||'\"' into v_retcs from incomecurr where id=v_id;");
		strSql.append("\n     insert into LOGRECORD (id,accid,progname,remark,lastop)");
		strSql.append("\n     values (LOGRECORD_id.nextval," + Accid + ",v_progname,'【增加记录】单据号:'||v_noteno||';金额:" + Curr + "','" + Lastop + "');");
//		strSql.append("\n     commit;");
//		if (Statetag != null && Statetag == 1 && Fs == 0) {//自动登账
//			strSql.append("\n  p_paytobook(" + Accid + ",v_noteno,2);"); //结算自动登入账本 fs:0=商场零售，1=店铺零售,2=销售收款，3=采购付款
//		}

		strSql.append("\n    exception");
		strSql.append("\n    when others then ");
		strSql.append("\n    begin");
		strSql.append("\n      rollback;");
		strSql.append("\n      v_retbj:=0;v_retcs:='操作失败，请重试！';");// into :v_retbj,:v_notenolist from dual;");
		strSql.append("\n    end;");
		strSql.append("\n  end;");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retbj,v_retcs into :retbj,:retcs from dual;");
		strSql.append("\n end;");
		// System.out.println(strSql.toString());

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retbj", new ProdParam(Types.INTEGER));
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		// id = Long.parseLong(param.get("id").getParamvalue().toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = param.get("retcs").getParamvalue().toString();
			return Integer.parseInt(param.get("retbj").getParamvalue().toString());
		}
	}

	// 修改记录
	public int Update() {
		if (Noteno == null || Noteno.equals("")) {
			errmess = "单据号无效！";
			return 0;
		}
		if (!Func.isNull(Notedatestr)) {
			if (Func.subString(Notedatestr, 1, 10).compareTo(Calcdate) <= 0) {
				errmess = "单据日期只能在系统登账日 " + Calcdate + " 之后！";
				return 0;
			}
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n   v_count number;");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n   v_ynoteno varchar2(20);");

		strSql.append("\n begin ");
		if (Custid != null) {
			strSql.append("\n   select count(*) into v_count from customer where accid=" + Accid + " and custid=" + Custid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then");
			strSql.append("\n      v_retcs:='0客户无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Houseid != null) {
			strSql.append("\n   select count(*) into v_count from warehouse where accid=" + Accid + " and Houseid=" + Houseid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then");
			strSql.append("\n      v_retcs:='0店铺无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Handmanid != null && Handmanid > 0) {
			strSql.append("\n   select count(*) into v_count from employe where accid=" + Accid + " and epid=" + Handmanid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then");
			strSql.append("\n      v_retcs:='0业务员无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Payid != null && Fs == 0) {
			strSql.append("\n   select count(*) into v_count from payway where accid=" + Accid + " and Payid=" + Payid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then");
			strSql.append("\n      v_retcs:='0结算方式无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		strSql.append("\n   update incomecurr set ");
		if (!Func.isNull(Notedatestr)) {
			strSql.append("notedate=to_date('" + Notedatestr + "','yyyy-mm-dd hh24:mi:ss'),");
		} else {
			strSql.append("notedate=sysdate,");
		}
		// }
		if (Custid != null) {
			strSql.append("custid=" + Custid + ",");
		}
		if (Handmanid != null) {
			strSql.append("Handmanid=" + Handmanid + ",");
		}
		if (Handno != null) {
			strSql.append("handno='" + Handno + "',");
		}
		if (Curr != null) {
			strSql.append("curr='" + Curr + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Checkman != null) {
			strSql.append("checkman='" + Checkman + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		// if (Lastop != null) {
		// }
		if (Payid != null) {
			strSql.append("payid=" + Payid + ",");
		}
		if (Ynoteno != null) {
			strSql.append("ynoteno='" + Ynoteno + "',");
		}
		if (Phno != null) {
			strSql.append("Phno='" + Phno + "',");
		}
		if (Ywly != null) {
			strSql.append("ywly=" + Ywly + ",");
		}
		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
		}
		strSql.append("operant='" + Lastop + "',");
		strSql.append("lastop='" + Lastop + "',");
		strSql.append("lastdate=sysdate");
		strSql.append("  where  noteno='" + Noteno + "' and accid=" + Accid + " ;");
//		if (Statetag != null && Statetag == 1) {//自动登账
//			strSql.append("\n  commit;");
//			strSql.append("\n  p_paytobook(" + Accid + ",'" + Noteno + "',2);"); //结算自动登入账本 fs:0=商场零售，1=店铺零售,2=销售收款，3=采购付款，,4=客户缴押金
//		}
		strSql.append("\n   v_retcs:='1操作成功';");
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

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		// strSql.append(",case To_Char(a.notedate,'yyyy-mm-dd') when To_Char(SYSDATE,'yyyy-mm-dd') then '1' else '0' end as istoday");
		strSql.append(" FROM incomecurr a");
		strSql.append(" left outer join customer b on a.custid=b.custid");
		strSql.append(" left outer join warehouse d on a.houseid=d.houseid");
		strSql.append(" left outer join employe e on a.handmanid=e.epid");
		//		if (Fs == 0)
		strSql.append(" left outer join payway c on a.payid=c.payid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		// strSql.append(",case To_Char(a.notedate,'yyyy-mm-dd') when To_Char(SYSDATE,'yyyy-mm-dd') then '1' else '0' end as istoday");
		strSql.append("\n FROM incomecurr a");
		strSql.append("\n left outer join customer b on a.custid=b.custid");
		strSql.append("\n left outer join warehouse d on a.houseid=d.houseid");
		strSql.append("\n left outer join employe e on a.handmanid=e.epid");
		//		if (Fs == 0)
		strSql.append("\n left outer join payway c on a.payid=c.payid");

		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from incomecurr");
		return false;
	}

	// 撤单
	public int Cancel(int changedatebj) {
		if (Noteno.equals("")) {
			errmess = "单据号无效！";
			return 0;
		}
		String qry = " declare";
		qry += "\n   v_ynoteno varchar2(20); ";
		qry += "\n   v_notedate varchar2(10); ";
		qry += "\n   v_retcs varchar2(200); ";
		qry += "\n   v_payid number(10); ";
		qry += "\n   v_count number; ";
		qry += "\n   v_checkman varchar2(20); ";
		qry += "\n   v_bdid number;";
		qry += "\n   v_cwcheck varchar2(20); ";
		qry += "\n   v_jzdate date;";
		qry += "\n   v_bookid number(10); ";
		qry += "\n   v_curr number(16,2); ";
		qry += "\n begin ";

		qry += "\n   begin";
		qry += "\n     select to_char(notedate,'yyyy-mm-dd'),payid,ynoteno,checkman,bd_id ";
		qry += "\n     into v_notedate,v_payid,v_ynoteno,v_checkman,v_bdid";
		qry += "\n     from incomecurr where accid=" + Accid + "  and noteno='" + Noteno + "' and statetag=1; ";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_retcs:='0未找到单据!';";
		qry += "\n     goto exit;";
		qry += "\n   end;";
		qry += "\n   if length(v_checkman)>0 then ";// 如果收款单关联了销售单，重新刷新销售的的结算方式
		qry += "\n      v_retcs:= '0已审核的单据不允许撤单！'; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if; ";

		qry += "\n   if v_notedate<='" + Calcdate + "' then ";
		qry += "\n      v_retcs:= '0系统登账日" + Calcdate + "及之前的单据不允许撤单！'; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if; ";
		qry += "\n   if length(v_ynoteno)>0 then ";// 如果收款单关联了销售单，重新刷新销售的的结算方式
		qry += "\n      v_retcs:= '0当前收款是销售单结账时的收款记录，不允许撤单！'; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if; ";
		qry += "\n   if v_bdid>0 then";//已登账本，判断账本记录是否已审核，如果已审核，不允许撤单，否则删除账本记录
		qry += "\n      begin";
		qry += "\n        select notedate,checkman,bookid into v_jzdate,v_cwcheck,v_bookid from bookdetail where id=v_bdid and accid=" + Accid + ";";
		qry += "\n        if length(v_cwcheck)>0 then ";
		qry += "\n           v_retcs:= '0当前收款记录已登账审核，不允许撤单！'; ";
		qry += "\n           goto exit; ";
		qry += "\n        end if;";
		qry += "\n      EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n        v_bdid:=0;";
		qry += "\n      end;";
		qry += "\n   end if;";

		// if (changedatebj == 0) // 不允许更改单据日期
		// {
		// qry += "\n if v_notedate<to_char(sysdate,'yyyy-mm-dd') then ";
		// qry += "\n v_retcs:= '0今日之前的单据不允许撤单！'; ";
		// qry += "\n goto exit; ";
		// qry += "\n end if; ";
		// }
		// notetype:0售(退库) 1物料销售（退库）
		// fs:0销售收款勾销售单1销售收款勾退货单2销售退款勾退货单

		qry += "\n   select count(*) into v_count from incomelink where accid=" + Accid + " and pnoteno='" + Noteno + "';";
		qry += "\n   if v_count>0 then ";
		qry += "\n      v_retcs:='0收款单有勾单记录，不允许撤单！';";
		qry += "\n      goto exit;";
		qry += "\n   end if;";

		// qry += "\n if length(v_ynoteno)>0 then ";// 如果收款单关联了销售单，重新刷新销售的的结算方式
		// qry += "\n p_wareoutpaylist(" + Accid + ",v_ynoteno);";
		// qry += "\n end if;";
		qry += "\n   update incomecurr set statetag=0 where accid=" + Accid + "  and noteno='" + Noteno + "' ; ";
		qry += "\n   if v_bdid>0 then";//已登账本，判断账本记录是否已审核，如果已审核，不允许撤单，否则删除账本记录
		qry += "\n      delete from bookdetail where id=v_bdid and accid=" + Accid + ";";  //删除账本记录
		qry += "\n      commit;";
		qry += "\n      p_calcbookbalcurr(v_bookid,v_jzdate);";
		qry += "\n   end if;";//已登账本，判断账本记录是否已审核，如果已审核，不允许撤单，否则删除账本记录
		qry += "\n   commit;";
		qry += "\n   v_retcs:= '1操作成功！';  ";
		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",decode(v_payid,0,'销售折让','销售收款'),'【撤单】单据号:" + Noteno + "','" + Operant + "');";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret == 0) {
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
		qry += "\n   v_payid number(10);";
		qry += "\n   v_checkman varchar2(20);";
		qry += "\n   v_statetag number(1);";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_mess varchar2(200);";
		qry += "\n begin";
		qry += "\n   select checkman,payid,statetag into v_checkman,v_payid,v_statetag from incomecurr where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   if v_statetag<>1 then ";
		qry += "\n      v_retcs:='0单据状态无效核！';";
		qry += "\n      goto exit;";
		qry += "\n   end if;";
		if (bj == 1) {// 审核
			qry += "\n   if length(v_checkman)>0 then ";
			qry += "\n      v_retcs:='0单据已审核！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update incomecurr set checkman='" + Operant + "' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【取消审核】';";

		} else {// 取消审核
			qry += "\n   if length(v_checkman)=0 then ";
			qry += "\n      v_retcs:='0当前单据未审核！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   if v_checkman<>'" + Operant + "' then ";
			qry += "\n      v_retcs:='0系统不允许取消其他用户审核的单据！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update incomecurr set checkman='' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【审核】';";
		}
		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",case v_payid when 0 then '销售折让' else '销售收款' end,v_mess||'单据号:" + Noteno + "','" + Operant + "');";
		qry += "\n   v_retcs:='1操作成功！';";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";
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

}