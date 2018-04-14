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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-08 20:58:07
//*************************************
public class Custorderh implements java.io.Serializable {
	private Float Id;
	private String Noteno;
	private Long Accid;
	private Long Custid;
	private Date Notedate;
	private String Remark;
	private String Handno;
	private Float Totalamt;
	private String Notedatestr = "";
	private Float Totalcurr;
	private String Operant;
	private String Checkman;
	private Integer Statetag;
	private Date Lastdate;
	private String Orderno;
	private Integer Ywly;
	private Integer Overbj;
	private Float Totalfactamt;
	private Long Buy_accid;
	private String Buy_cgorderno;
	private String errmess;
	private Long Epid;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setNoteno(String noteno) {
		this.Noteno = noteno;
	}

	public String getNoteno() {
		return Noteno;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setCustid(Long custid) {
		this.Custid = custid;
	}

	public Long getCustid() {
		return Custid;
	}

	public void setNotedate(Date notedate) {
		this.Notedate = notedate;
	}

	public String getNotedate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Notedate);
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setHandno(String handno) {
		this.Handno = handno;
	}

	public String getHandno() {
		return Handno;
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

	public void setOrderno(String orderno) {
		this.Orderno = orderno;
	}

	public String getOrderno() {
		return Orderno;
	}

	public void setYwly(Integer ywly) {
		this.Ywly = ywly;
	}

	public Integer getYwly() {
		return Ywly;
	}

	public void setOverbj(Integer overbj) {
		this.Overbj = overbj;
	}

	public Integer getOverbj() {
		return Overbj;
	}

	public void setTotalfactamt(Float totalfactamt) {
		this.Totalfactamt = totalfactamt;
	}

	public Float getTotalfactamt() {
		return Totalfactamt;
	}

	public void setBuy_accid(Long buy_accid) {
		this.Buy_accid = buy_accid;
	}

	public Long getBuy_accid() {
		return Buy_accid;
	}

	public void setBuy_cgorderno(String buy_cgorderno) {
		this.Buy_cgorderno = buy_cgorderno;
	}

	public String getBuy_cgorderno() {
		return Buy_cgorderno;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }

	// 删除记录
	public int Delete() {
		if (Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_ywly number(1);");
		strSql.append("\n   v_buyaccid number(10);");
		strSql.append("\n   v_cgorderno varchar2(20); ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n   v_operant varchar2(20); ");
		strSql.append("\n   v_statetag number(1); ");
		strSql.append("\n begin ");
		strSql.append("\n   begin ");
		strSql.append("\n     select ywly,buy_accid,buy_cgorderno,statetag,operant into v_ywly,v_buyaccid,v_cgorderno,v_statetag,v_operant from custorderh where accid=" + Accid + " and noteno='" + Noteno + "' ;");
		strSql.append("\n     if v_statetag>0 then ");
		strSql.append("\n        v_retcs:= '0当前单据已提交，不允许删除！'; ");
		strSql.append("\n        goto exit; ");
		strSql.append("\n     end if;");

		strSql.append("\n   if v_operant<>'" + Operant + "' then ");
		strSql.append("\n      v_retcs:= '0不允许删除其他用户的单据！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if;");

//		strSql.append("\n   delete from custorderm ");
//		strSql.append("\n       where accid=" + Accid + " and noteno='" + Noteno + "'");
//		strSql.append("\n       and exists (select 1 from custorderh a where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
//		strSql.append("\n       and a.accid=custorderm.accid and a.noteno=custorderm.noteno and a.statetag=0);");
//		strSql.append("\n   delete from custorderh ");
//		strSql.append("\n     where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0; ");
		strSql.append("\n   update custorderh set statetag=2,lastdate=sysdate");
		strSql.append("\n     where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0; ");

		strSql.append("\n     if v_ywly=3 and v_buyaccid>0 then "); // 如果入库单 是载入卖家的销售单 ，把销售标志置0
		strSql.append("\n        update provorderh set bj=0 where accid=v_buyaccid and noteno=v_cgorderno; ");
		strSql.append("\n     end if;");
		strSql.append("\n     delete from omorder where accid=" + Accid + " and noteno='" + Noteno + "';"); // 删除订货会产生的订单记录
		strSql.append("\n     v_retcs:='1操作成功！';");
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN ");
		strSql.append("\n     v_retcs:='0操作异常！'; ");
		strSql.append("\n   end; ");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		strSql.append("\n end; ");
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

	// 增加记录
	public int Append() {
		String qry = "declare ";
		// qry += " v_accid number;";
		// qry += " v_operant nvarchar2(10);";
		qry += "   v_noteno nvarchar2(20);";
		// qry += " v_custid number;";
		qry += "   v_id  number;";
		qry += "   v_retcs nvarchar2(100);";
		qry += "   v_retbj number(1); ";
		qry += "\n begin ";
		qry += "\n  begin ";
		qry += "     select 'KO'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from custorderh";
		qry += "        where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'KO'||To_Char(SYSDATE,'yyyymmdd');";
		qry += "     v_id:=custorderH_ID.NEXTVAL;";
		qry += "     insert into custorderh (id, accid,noteno,notedate,custid,statetag,totalamt,totalcurr,remark,handno,operant,checkman,lastdate)";
		qry += "     values (v_id," + Accid + ",v_noteno,sysdate," + Custid + ",0,0,0,'','','" + Operant + "','',sysdate);";
		qry += "     commit;";
		qry += "     v_retbj:=1; select '\"msg\":\"操作成功\",\"ID\":\"'||id||'\",\"NOTENO\":\"'||noteno||'\",\"NOTEDATE\":\"'||to_char(notedate,'yyyy-mm-dd hh24:mi:ss')||'\"' into v_retcs from custorderh where id=v_id;  ";
		qry += "\n    insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n    values (LOGRECORD_id.nextval," + Accid + ",'客户订单','【新增单据】单据号:'||v_noteno,'" + Operant + "');";
		qry += "    exception";
		qry += "    when others then ";
		qry += "    begin";
		qry += "     rollback;";
		qry += "     v_retbj:=0; v_retcs:='\"msg\":\"操作失败，请重试！\"' ;";
		qry += "    end;";
		qry += "\n  end;";
		qry += "\n  select v_retbj,v_retcs into :retbj,:retcs from dual;";
		qry += "\n end;";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retbj", new ProdParam(Types.INTEGER));
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// int retbj = Integer.parseInt(param.get("retbj").getParamvalue().toString());
		errmess = param.get("retcs").getParamvalue().toString();

		// errmess = retcs.substring(1);
		return Integer.parseInt(param.get("retbj").getParamvalue().toString());
	}

	// 修改记录
	public int Update() {
		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "单据号无效！";
			return 0;
		}
		Noteno = Noteno.toUpperCase();

		if (Statetag != null && Statetag == 1) // 如果是提交，必须转入店铺，要判断客户及店铺
		{
			if (Custid == null || Custid == 0) {
				errmess = "custid不是一个有效值！";
				return 0;
			}
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_totalcurr number;");
		strSql.append("\n   v_totalamt number; ");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n   v_count number;");
		strSql.append("\n   v_notedate date; ");
		strSql.append("\n begin ");

		// if (changedatebj == 1 && Notedatestr.length() > 0) {
		if (!Func.isNull(Notedatestr)) {
			strSql.append("\n   v_notedate:=to_date('" + Notedatestr + "','yyyy-mm-dd hh24:mi:ss');");
			strSql.append("\n   if to_char(v_notedate,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')  or v_notedate>sysdate then ");
			strSql.append("\n      v_notedate:=sysdate;");
			strSql.append("\n   end if;");
		} else {
			strSql.append("\n   v_notedate:=sysdate;");
		}

		if (Custid != null) {
			strSql.append("\n   select count(*) into v_count from customer where accid=" + Accid + " and custid=" + Custid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0客户无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}

		strSql.append("\n   update custorderh set ");

		if (Custid != null) {
			strSql.append("custid=" + Custid + ",");
		}
		// if (Notedate != null) {
		strSql.append("notedate=v_notedate,");
		// }
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Handno != null) {
			strSql.append("handno='" + Handno + "',");
		}
		// if (Totalamt != null) {
		// strSql.append("totalamt='" + Totalamt + "',");
		// }
		// if (Totalcurr != null) {
		// strSql.append("totalcurr='" + Totalcurr + "',");
		// }
		// if (Operant != null) {
		// strSql.append("operant='" + Operant + "',");
		// }
		if (Checkman != null) {
			strSql.append("checkman='" + Checkman + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Orderno != null) {
			strSql.append("orderno='" + Orderno + "',");
		}
		// if (Ywly != null) {
		// strSql.append("ywly=" + Ywly + ",");
		// }
		// if (Overbj != null) {
		// strSql.append("overbj=" + Overbj + ",");
		// }
		// if (Totalfactamt != null) {
		// strSql.append("totalfactamt='" + Totalfactamt + "',");
		// }
		if (Buy_accid != null) {
			strSql.append("buy_accid=" + Buy_accid + ",");
		}
		if (Buy_cgorderno != null) {
			strSql.append("buy_cgorderno='" + Buy_cgorderno + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where accid=" + Accid + " and noteno='" + Noteno + "';");
		if (Statetag != null && Statetag == 1)// 提交时刷新当前单据的总数量与总金额
		{
			strSql.append("\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from custorderm ");
			strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "';");

			strSql.append("\n   update custorderh set totalcurr=v_totalcurr,totalamt=v_totalamt");
			strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "';");
		}
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

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(",0 as istoday");
		strSql.append("\n FROM custorderh a");
		strSql.append("\n left outer join customer b on a.custid=b.custid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM custorderh a");
		strSql.append(" left outer join customer b on a.custid=b.custid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		System.out.println(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from custorderh");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}

	// 设置完工标志
	public int doSetOver() {
		if (Noteno.equals("")) {
			errmess = "单据号参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n  v_retcs varchar2(200);");
		strSql.append("\n  v_count number(10);");
		strSql.append("\n begin ");
		strSql.append("\n   select count(*) into v_count from custorderh where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=1;");
		strSql.append("\n   if v_count=0 then ");
		strSql.append("\n      v_retcs:='0单据状态异常，请重新进入！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		if (Overbj == 1) // 自动计算完成数量
			strSql.append("\n  p_calccustorderover(" + Accid + ",'" + Noteno + "',0);");
		strSql.append("\n   update custorderh set overbj=" + Overbj + " where accid=" + Accid + " and noteno='" + Noteno + "';");
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

	// 撤单
	public int Cancel(int changedatebj) {
		if (Noteno.equals("")) {
			errmess = "单据号无效！";
			return 0;
		}
		String qry = " declare";
		// qry += "\n v_ntid number(1); ";
		qry += "\n   v_notedate varchar2(10); ";
		qry += "\n   v_retcs varchar2(200); ";
		qry += "\n   v_checkman varchar2(20); ";
		qry += "\n   v_count number(10); ";
		qry += "\n begin ";

		// qry += "\n select count(*) into v_count from custorderh where accid=" + Accid ;
		// qry += " and noteno='" + Noteno + "'";
		// qry += "\n and statetag>=1; ";
		// qry += "\n if v_count=0 then ";
		// qry += "\n v_retcs:= '0当前单据已撤单！'; ";
		// qry += "\n goto exit; ";
		// qry += "\n end if;";

		qry += "\n   begin";
		qry += "\n     select to_char(notedate,'yyyy-mm-dd'),checkman ";
		qry += "\n     into v_notedate,v_checkman";
		qry += "\n     from custorderh where accid=" + Accid + "  and noteno='" + Noteno + "' and statetag=1; ";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_retcs:='0未找到单据!';";
		qry += "\n     goto exit;";
		qry += "\n   end;";
		qry += "\n   if length(v_checkman)>0 then ";// 如果收款单关联了销售单，重新刷新销售的的结算方式
		qry += "\n      v_retcs:= '0已审核的单据不允许撤单！'; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if; ";
		// if (changedatebj == 0) // 不允许更改单据日期
		// {
		// qry += "\n if v_notedate<to_char(sysdate,'yyyy-mm-dd') then ";
		// qry += "\n v_retcs:= '0今日之前的单据不允许撤单！'; ";
		// qry += "\n goto exit; ";
		// qry += "\n end if; ";
		// }
		qry += "\n   select count(*) into v_count from wareouth where accid=" + Accid + " and statetag<=1 and ntid=1 and rownum=1 and orderno='" + Noteno + "';";
		qry += "\n   if v_count>0 then ";
		qry += "\n      v_retcs:= '0当前订单已有出库记录，不允许撤单！' ; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if;";
		// qry += "\n if v_cleartag=1 then ";
		// qry += "\n v_retcs:= '0当前单据已被冲单，不允许撤单！' ; ";
		// qry += "\n goto exit; ";
		// qry += "\n elsif v_cleartag=2 then ";
		// qry += "\n v_retcs:='0当前单据是系统生成的冲账单据，不允许撤单！' ; ";
		// qry += "\n goto exit; ";
		// qry += "\n end if; ";

		// qry += "\n if length(v_noteno1)>0 then ";
		// qry += "\n v_retcs:= '0当前调出单已有调入单记录，不允许撤单！'; ";
		// qry += "\n goto exit; ";
		// qry += "\n end if; ";
		// qry += "\n v_progname:='调拨出库'; ";

		qry += "\n   update custorderh set statetag=0 where accid=" + Accid + "  and noteno='" + Noteno + "' ; ";

		qry += "\n   v_retcs:= '1操作成功！';  ";

		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'客户订货单','【撤单】单据号:" + Noteno + "','" + Operant + "');";
		qry += "\n   commit;";
		// qry += "\n if length(v_orderno)>0 then";// 如果出库单关联了订单，计算订单完成情况
		// qry += "\n p_calcallotorderover(" + Accid + ",v_orderno);"; // 计算订单完成情况
		// qry += "\n end if;";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";
		// System.out.println(qry);
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

	// 微信小程序撤单，自动删除订单，同时把订单数据返回购物车
	public int Cancel1() {
		if (Noteno.equals("")) {
			errmess = "单据号无效！";
			return 0;
		}
		String qry = " declare";
		// qry += "\n v_ntid number(1); ";
		qry += "\n   v_notedate varchar2(10); ";
		qry += "\n   v_retcs varchar2(200); ";
		// qry += "\n v_noteno1 varchar2(20); ";
		// qry += "\n v_orderno varchar2(20); ";
		// qry += "\n v_progname varchar2(40); ";
		qry += "\n   v_id number(20);";
		// qry += "\n v_paycurrv number(12,2); ";
		qry += "\n   v_count number(10); ";
		qry += "\n begin ";

		qry += "\n   begin";
		qry += "\n     select to_char(notedate,'yyyy-mm-dd') ";
		qry += "\n     into v_notedate";
		qry += "\n     from custorderh where accid=" + Accid + "  and noteno='" + Noteno + "'; ";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_retcs:='0未找到单据!';";
		qry += "\n     goto exit;";
		qry += "\n   end;";

		qry += "\n   select count(*) into v_count from wareouth where accid=" + Accid + " and statetag<=1 and ntid=1 and rownum=1 and orderno='" + Noteno + "';";
		qry += "\n   if v_count>0 then ";
		qry += "\n      v_retcs:= '0当前订单已有出库记录，不允许撤单！' ; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if;";

		qry += "\n   update custorderh set statetag=2 where accid=" + Accid + "  and noteno='" + Noteno + "' ; ";

		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'客户订货单','【微信小程序删除单据】单据号:" + Noteno + "','" + Operant + "');";
		// qry += "\n insert into omcustware
		// 把订单数据恢复到购物车
		qry += "\n   declare cursor cur_data is ";
		qry += "\n      select wareid,colorid,sizeid,amount from custorderm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n   begin";
		qry += "\n      for v_row in cur_data loop";

		qry += "\n         begin ";
		qry += "\n           select id into v_id from omcustware where omid=0 and omepid=" + Epid + " and omid=0 and wareid=v_row.wareid and colorid=v_row.colorid and sizeid=v_row.sizeid;";
		qry += "\n           update omcustware set amount=amount+v_row.amount where id=v_id;";
		qry += "\n         EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n           insert into omcustware (id,omid,omepid,wareid,colorid,sizeid,amount,lastop)";
		qry += "\n           values (omcustware_id.nextval,0," + Epid + ",v_row.wareid,v_row.colorid,v_row.sizeid,v_row.amount,'" + Operant + "');";
		qry += "\n         end; ";

		qry += "\n         v_count:=v_count+1;";
		qry += "\n         if v_count>500 then ";
		qry += "\n            v_count:=1;";
		qry += "\n            commit;";
		qry += "\n         end if;";
		qry += "\n      end loop;";
		qry += "\n   end;";
		qry += "\n   commit;";
		qry += "\n   v_retcs:= '1操作成功！';  ";

		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";
		// System.out.println(qry);
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

	/// 载入所有经销商的采购订单(我的买家订单) 采购订单转客户订单
	/// 载入我的经销商的采购订单（转为当前的客户订单）
	public int doLoadBuyerOrder(int priceprec, int nearsale) {
		String qry = "declare ";

		qry += "\n    v_accid number(10);";
		qry += "\n    v_accid1 number(10);";
		qry += "\n    v_custid number(10);";
		qry += "\n    v_orderno varchar2(20);";
		qry += "\n    v_noteno varchar2(20);";
		qry += "\n    v_operant varchar2(20);";
		qry += "\n    v_amount number;";
		qry += "\n    v_curr number;";
		qry += "\n    v_retailsale number;";
		qry += "\n    v_sale1 number;";
		qry += "\n    v_sale2 number;";
		qry += "\n    v_sale3 number;";
		qry += "\n    v_sale4 number;";
		qry += "\n    v_sale5 number;";
		qry += "\n    v_discount number;";
		qry += "\n    v_pricetype number(1);";
		qry += "\n begin  ";
		qry += "\n    declare cursor cur_provorderh is  ";
		qry += "\n       select a.id,a.accid,a.noteno,b.sellcustid from provorderh a ";
		qry += "\n         join accconnect b on a.accid=b.buyaccid and a.provid=b.buyprovid where a.bj=0 and a.statetag=1 and b.sellaccid=" + Accid;
		qry += "\n         and a.notedate>b.firstdate and b.sellcustid>0 ;";
		qry += "\n       v_row cur_provorderh%rowtype; ";
		qry += "\n    begin ";
		qry += "\n       for v_row in cur_provorderh loop ";
		qry += "\n          v_orderno:=v_row.noteno; ";
		qry += "\n          v_accid1:=v_row.accid; ";
		qry += "\n          v_custid:=v_row.sellcustid; ";
		qry += "\n          begin ";
		qry += "\n            select pricetype,discount into v_pricetype,v_discount from customer where custid=v_custid;";
		// --生成客户订单
		qry += "\n            select 'KO'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from CUSTORDERH ";
		qry += "\n            where accid=" + Accid + "  and LENGTH(noteno)=15 and substr(noteno,1,10)= 'KO'||To_Char(SYSDATE,'yyyymmdd');   ";
		qry += "\n            insert into CUSTORDERH (id, accid,noteno,notedate,custid,statetag,totalamt,totalcurr,remark,handno,operant,checkman,ywly,buy_accid,buy_cgorderno) ";
		qry += "\n            values (CUSTORDERH_id.nextval," + Accid + ",v_noteno,sysdate,v_custid,0,0,0,'经销商采购订单'||v_orderno||'转入','','" + Operant + "','',3,v_accid1,v_orderno); ";
		qry += "\n            insert into custorderm (id,accid,noteno,wareid,colorid,sizeid,amount,discount,price0,price,curr,remark0,lastdate) ";
		qry += "\n            select custorderm_id.nextval," + Accid + ",v_noteno,wareid,colorid,sizeid,amount,v_discount,0,0,0,remark0,sysdate from ";
		qry += "\n            ( ";
		qry += "\n              select b.wareid1 as wareid,c.colorid1 as colorid,d.sizeid1 as sizeid,a.amount,a.remark0  ";
		qry += "\n              from provorderm a  ";
		qry += "\n              join warecode b on a.wareid=b.wareid ";
		qry += "\n              join colorcode c on a.colorid=c.colorid ";
		qry += "\n              join sizecode d on a.sizeid=d.sizeid ";
		qry += "\n              where a.accid=v_accid1 and a.noteno=v_orderno ";
		qry += "\n              and b.wareid1>0 and c.colorid1>0 and d.sizeid1>0";
		qry += "\n           );    ";
		// 更新折扣及单价
		// qry += " update custorderm set discount=v_discount where accid="+accid+" and noteno=v_noteno;";
		qry += "\n       update custorderm set price0=(select ";
		qry += "\n           case v_pricetype ";
		qry += "\n              when 1 then sale1 ";
		qry += "\n              when 2 then sale2 ";
		qry += "\n              when 3 then sale3 ";
		qry += "\n              when 4 then sale4 ";
		qry += "\n              when 5 then sale5 ";
		qry += "\n              else retailsale ";
		qry += "\n           end ";
		qry += "\n           from warecode  where custorderm.wareid=warecode.wareid)";
		qry += "\n           where accid=" + Accid + " and noteno=v_noteno; ";
		// 计算折后价
		qry += "\n          update custorderm set price=round(price0*discount," + priceprec + ") where accid=" + Accid + " and noteno=v_noteno; ";
		// 取最近售价
		if (nearsale == 1) // 1 启用最近售价
		{
			// string qry = "select price0,discount,price from nearsale where wareid=" + wareid + " and custid=" + custid + " and saleid=" + saleid;
			// qry += "\n update custorderm set price=nvl((select price from nearsale where nearsale.custid=v_custid and nearsale.wareid=custorderm.wareid and nearsale.saleid=1),price)";
			// qry += " where accid=" + Accid + " and noteno=v_noteno; ";

		}

		qry += "\n          update custorderm set curr=round(amount*price,2) where accid=" + Accid + " and noteno=v_noteno; ";

		qry += "\n          select nvl(sum(amount),0),nvl(sum(curr),0) into v_amount,v_curr from custorderm where accid=" + Accid + " and noteno=v_noteno; ";
		qry += "\n          update custorderh set totalcurr=v_curr,totalamt=v_amount where  accid=" + Accid + " and noteno=v_noteno; ";
		qry += "\n          update provorderh set bj=1 where id=v_row.id; ";
		qry += "\n          EXCEPTION WHEN NO_DATA_FOUND THEN "; // 未指定经销商对应的客户
		qry += "\n            v_custid:=0; ";
		qry += "\n          end; ";
		qry += "\n       end loop; ";
		qry += "\n    end; ";
		qry += "\n    commit; ";
		qry += "\n end;   ";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 合并订单
	public int Merge(JSONObject jsonObject, int priceprec) {
		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		Noteno = Noteno.toUpperCase();
		if (!jsonObject.has("notelist")) {
			errmess = "未传入要合并的客户订单1！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("notelist");
		if (jsonArray.size() == 0) {
			errmess = "未传入要合并的客户订单2！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n   v_id number;  ";
		qry += "\n   v_count number;  ";
		qry += "\n   v_totalamt number;  ";
		qry += "\n   v_totalcurr number;  ";
		qry += "\n   v_retcs varchar2(200);  ";
		qry += "\n begin";
		qry += "\n   select count(*) into v_count from custorderh where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=1 and overbj=0;";
		qry += "\n   if v_count=0 then";
		qry += "\n      v_retcs:='0未找到有效的订单！';";
		qry += "\n      goto exit;";
		qry += "\n   end if;";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			String noteno1 = jsonObject2.getString("noteno").toUpperCase();
			if (Noteno.equals(noteno1))
				continue;
			qry += "\n   declare cursor cur_data is  ";
			qry += "\n     select wareid,colorid,sizeid,amount,price0,discount,price,curr,remark0 from custorderm where accid=" + Accid + " and noteno='" + noteno1 + "';";
			qry += "\n     v_row cur_data%rowtype; ";
			qry += "\n   begin";
			qry += "\n     for v_row in cur_data loop";
			qry += "\n     begin";
			qry += "\n        select id into v_id from custorderm where accid=" + Accid + " and noteno='" + Noteno + "' ";
			qry += "\n           and wareid=v_row.wareid and colorid=v_row.colorid and sizeid=v_row.sizeid;";
			qry += "\n        update custorderm set amount=amount+v_row.amount,curr=(amount+v_row.amount)*price where id=v_id;";
			qry += "\n     EXCEPTION WHEN NO_DATA_FOUND THEN";
			qry += "\n        v_id:=custorderm_id.nextval;";
			qry += "\n        insert into custorderm (id,accid,noteno,wareid,colorid,sizeid,amount,price0,discount,price,curr,remark0)";
			qry += "\n        values (v_id," + Accid + ",'" + Noteno + "',v_row.wareid,v_row.colorid,v_row.sizeid,v_row.amount,v_row.price0,v_row.discount,v_row.price,v_row.curr,v_row.remark0);";
			qry += "\n     end;  ";
			qry += "\n     end loop;";
			qry += "\n   end;  ";
			qry += "\n   update wareouth set orderno='" + Noteno + "' where accid=" + Accid + " and orderno='" + noteno1 + "' and ntid=1;";
			qry += "\n   update custorderh set statetag=2 where accid=" + Accid + " and noteno='" + noteno1 + "';";

			qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
			qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'客户订单','【合并订单】单据号:" + Noteno + ",被合并订单:" + noteno1 + "','" + Operant + "');";

			qry += "\n   commit;";
		}
		qry += "\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from custorderm where accid=" + Accid + " and noteno='" + Noteno + "';  ";
		qry += "\n   update custorderh set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno='" + Noteno + "'; ";
		qry += "\n   p_calccustorderover(" + Accid + ",'" + Noteno + "',0);";//
		qry += "\n   <<exit>>";
		qry += "\n   v_retcs:='1操作成功！';";
		qry += "\n   select v_retcs into :retcs  from dual;";
		qry += "\n end;";
		// System.out.println(qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param, 600);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

	// 审核/反审
	public int Check(int checkid) {
		//		checkid:0=取消业务审核，1=业务审核，2=财务审核，3=取消财务审核
		String qry = "declare";
		//		qry += "\n   v_count number;";
		//		qry += "\n   v_checkman varchar2(20);";
		//		qry += "\n   v_retcs varchar2(200);";
		//		qry += "\n   v_mess varchar2(200);";
		//		qry += "\n begin";
		//		qry += "\n   select checkman into v_checkman from custorderh where accid=" + Accid + " and noteno='" + Noteno + "';";
		//		if (bj == 1) {// 审核
		//			qry += "\n   if length(v_checkman)>0 then ";
		//			qry += "\n      v_retcs:='0单据已审核！';";
		//			qry += "\n      goto exit;";
		//			qry += "\n   end if;";
		//			qry += "\n   update custorderh set checkman='" + Operant + "' where accid=" + Accid + " and noteno='" + Noteno + "';";
		//			qry += "\n   v_mess:='【取消审核】';";
		//
		//		} else {// 取消审核
		//			qry += "\n   if v_checkman<>'" + Operant + "' then ";
		//			qry += "\n      v_retcs:='0系统不允许取消其他用户审核的单据！';";
		//			qry += "\n      goto exit;";
		//			qry += "\n   end if;";
		//			qry += "\n   update custorderh set checkman='' where accid=" + Accid + " and noteno='" + Noteno + "';";
		//			qry += "\n   v_mess:='【审核】';";
		//		}

		qry += "\n   v_count number;";
		qry += "\n   v_ntid number(1);";
		qry += "\n   v_checkman varchar2(20);";
		qry += "\n   v_cwcheck varchar2(20);";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_mess varchar2(200);";
		qry += "\n begin";
		qry += "\n   select checkman,cwcheck into v_checkman,v_cwcheck from custorderh where accid=" + Accid + " and noteno='" + Noteno + "';";
		if (checkid == 2) {// 财务审核
			qry += "\n   if length(v_checkman)=0 then ";
			qry += "\n      v_retcs:='0未经过业务审核的单据，不允许财务审核！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   if length(v_cwcheck)>0 then ";
			qry += "\n      v_retcs:='0单据已进行财务审核！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update custorderh set cwcheck='" + Operant + "' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【财务审核】';";

		} else if (checkid == 3) {// 财务取消审核
			qry += "\n   if v_cwcheck<>'" + Operant + "' then ";
			qry += "\n      v_retcs:='0系统不允许取消其他财务审核的单据！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update custorderh set cwcheck='' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【取消财务审核】';";
		} else if (checkid == 1) {// 业务审核
			qry += "\n   if length(v_checkman)>0 then ";
			qry += "\n      v_retcs:='0单据业务已审核！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update custorderh set checkman='" + Operant + "' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【业务审核】';";

		} else if (checkid == 0) {// 业务取消审核
			qry += "\n   if length(v_cwcheck)>0 then ";
			qry += "\n      v_retcs:='0系统不允许取消财务已审核的单据！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";

			qry += "\n   if v_checkman<>'" + Operant + "' then ";
			qry += "\n      v_retcs:='0系统不允许取消其他业务审核的单据！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update custorderh set checkman='' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【取消业务审核】';";
		}

		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'客户订单',v_mess||'单据号:" + Noteno + "','" + Operant + "');";
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

	//json转单据
	public int json2note(JSONObject jsonObject) {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();

		//		if (!Func.isNull(Notedatestr)) {
		//			if (Func.subString(Notedatestr, 1, 10).compareTo(Calcdate) <= 0) {
		//				errmess = "单据日期只能在系统登账日 " + Calcdate + " 之后！";
		//				return 0;
		//			}
		//		}

		strSql1.append("id,");
		strSql2.append("v_id,");
		// if (Noteno != null) {
		strSql1.append("noteno,");
		strSql2.append("v_noteno ,");
		strSql1.append("accid,");
		strSql2.append(Accid + ",");

		if (!Func.isNull(Notedatestr)) {
			strSql1.append("notedate,");
			strSql2.append("to_date('" + Notedatestr + "','yyyy-mm-dd hh24:mi:ss'),");

		} else {
			strSql1.append("notedate,");
			strSql2.append("sysdate,");
		}

		//		if (Houseid != null) {
		//			strSql1.append("houseid,");
		//			strSql2.append(Houseid + ",");
		//		}
		if (Custid != null) {
			strSql1.append("Custid,");
			strSql2.append(Custid + ",");
		}

		if (Handno != null) {
			strSql1.append("handno,");
			strSql2.append("'" + Handno + "',");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		// }
		if (Checkman != null) {
			strSql1.append("checkman,");
			strSql2.append("'" + Checkman + "',");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Ywly != null) {
			strSql1.append("Ywly,");
			strSql2.append(Ywly + ",");
		}
		strSql1.append("operant,");
		strSql2.append("'" + Operant + "',");

		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_rs number; ");
		strSql.append("\n   v_totalamt number; ");
		strSql.append("\n   v_totalcurr number; ");
		//		strSql.append("\n   v_totalcost number; ");
		strSql.append("\n   v_noteno varchar2(20);");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n   v_notedate date;");
		strSql.append("\n begin ");

		strSql.append("\n   begin ");

		strSql.append("\n     select 'KO'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from custorderh");
		strSql.append("\n     where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'KO'||To_Char(SYSDATE,'yyyymmdd');");

		strSql.append("\n     v_id:=custorderh_id.nextval; ");
		strSql.append("\n     insert into custorderh (" + strSql1.toString() + ")");
		strSql.append("\n     values (" + strSql2.toString() + ");");
		JSONArray jsonArray = jsonObject.getJSONArray("warereclist");

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			long wareid = jsonObject2.has("wareid") ? Long.parseLong(jsonObject2.getString("wareid")) : 0;
			long colorid = jsonObject2.has("colorid") ? Long.parseLong(jsonObject2.getString("colorid")) : 0;
			long sizeid = jsonObject2.has("sizeid") ? Long.parseLong(jsonObject2.getString("sizeid")) : 0;
			Float amount = jsonObject2.has("amount") ? Float.parseFloat(jsonObject2.getString("amount")) : 0;
			Float price0 = jsonObject2.has("price0") ? Float.parseFloat(jsonObject2.getString("price0")) : 0;
			Float discount = jsonObject2.has("discount") ? Float.parseFloat(jsonObject2.getString("discount")) : 0;
			Float price = jsonObject2.has("price") ? Float.parseFloat(jsonObject2.getString("price")) : 0;
			Float curr = jsonObject2.has("curr") ? Float.parseFloat(jsonObject2.getString("curr")) : 0;
			String remark0 = jsonObject2.has("remark0") ? jsonObject2.getString("remark0") : "";
			strSql.append("\n  insert into custorderm (id,accid,noteno,wareid,colorid,sizeid,amount,price0,discount,price,curr,remark0)");
			strSql.append("\n  values (custorderm_id.nextval," + Accid + ",v_noteno," + wareid + "," + colorid + "," + sizeid + "," + amount //
					+ "," + price0 + "," + discount + "," + price + "," + curr + ",'" + remark0 + "');");
		}

		strSql.append("\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from custorderm where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   update custorderh set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   v_retcs:='1'||'\"msg\":\"操作成功\",\"NOTENO\":\"'||v_noteno||'\"';");
		strSql.append("\n   commit;");
		strSql.append("\n   exception");
		strSql.append("\n   when others then ");
		strSql.append("\n   begin");
		strSql.append("\n     rollback;");
//		strSql.append("\n     v_retcs:='0操作失败!';");
		strSql.append("\n     v_retcs:='0操作失败：'||substr(sqlerrm,1,160);");
		strSql.append("\n   end;");
		strSql.append("\n  end; ");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs into :retcs from dual;");
		strSql.append("\n end;");
		//		System.out.print(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常1！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}
}