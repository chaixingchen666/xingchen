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
//  @author:sunhong  @date:2017-03-08 20:57:54
//*************************************
public class Provorderh implements java.io.Serializable {
	private Float Id;
	private String Noteno;
	private Long Accid;
	private Date Notedate;
	private Long Provid;
	private String Handno;
	private String Remark;
	private Float Totalamt;
	private Float Totalcurr;
	private String Operant;
	private String Checkman;
	private String Notedatestr;
	private Integer Statetag;
	private Date Lastdate;
	private String Orderno;
	private Integer Bj;
	private Integer Ywly;
	private Integer Overbj;
	private Float Totalfactamt;
	private Long Cthouseid;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

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

	public void setNotedate(Date notedate) {
		this.Notedate = notedate;
	}

	public String getNotedate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Notedate);
	}

	public void setProvid(Long provid) {
		this.Provid = provid;
	}

	public Long getProvid() {
		return Provid;
	}

	public void setHandno(String handno) {
		this.Handno = handno;
	}

	public String getHandno() {
		return Handno;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
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

	public void setBj(Integer bj) {
		this.Bj = bj;
	}

	public Integer getBj() {
		return Bj;
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

	public void setCthouseid(Long cthouseid) {
		this.Cthouseid = cthouseid;
	}

	public Long getCthouseid() {
		return Cthouseid;
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
		strSql.append("declare");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n   v_statetag number(1); ");
		strSql.append("\n   v_operant varchar2(20); ");
		strSql.append("\n begin ");
		strSql.append("\n   begin ");
		strSql.append("\n     select statetag,operant into v_statetag,v_operant from provorderh where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n     v_retcs:= '0未找到单据！'; ");
		strSql.append("\n     goto exit; ");
		strSql.append("\n   end; ");
		strSql.append("\n   if v_statetag>0 then ");
		strSql.append("\n      v_retcs:= '0当前单据已提交，不允许删除！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if;");
		strSql.append("\n   if v_operant<>'" + Operant + "' then ");
		strSql.append("\n      v_retcs:= '0不允许删除其他用户的单据！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if;");

		// strSql.append("\n delete from provorderm ");
		// strSql.append("\n where accid=" + Accid + " and noteno='" + Noteno + "'");
		// strSql.append("\n and exists (select 1 from provorderh a where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
		// strSql.append("\n and a.accid=provorderm.accid and a.noteno=provorderm.noteno and a.statetag=0);");
		// strSql.append("\n delete from provorderh ");
		// strSql.append("\n where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0; ");

		strSql.append("\n   update provorderh set statetag=2,lastdate=sysdate ");
		strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0; ");

		strSql.append("\n   v_retcs:='1操作成功！';");
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
		// qry += "\n v_accid number;";
		// qry += "\n v_operant nvarchar2(10);";
		qry += "\n   v_noteno nvarchar2(20);";
		// qry += "\n v_provid number;";
		qry += "\n   v_id  number;";
		qry += "\n   v_retcs nvarchar2(100);";
		qry += "\n   v_retbj number(1); ";
		qry += "\n begin ";
		// qry += " v_accid:=" + accid + ";";
		// qry += " v_provid:=" + provid + ";";
		// qry += " v_operant:='" + lastop + "';";
		qry += "\n  begin ";
		qry += "\n    select 'CO'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from provorderh";
		qry += "\n    where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'CO'||To_Char(SYSDATE,'yyyymmdd');";
		qry += "\n    v_id:=provorderH_ID.NEXTVAL;";
		qry += "\n    insert into provorderh (id, accid,noteno,notedate,provid,statetag,totalamt,totalcurr,remark,handno,operant,checkman,lastdate)";
		qry += "\n    values (v_id," + Accid + ",v_noteno,sysdate," + Provid + ",0,0,0,'','','" + Operant + "','',sysdate);";
		qry += "\n    commit;";
		qry += "\n    v_retbj:=1;\n  select '\"msg\":\"操作成功\",\"ID\":\"'||id||'\",\"NOTENO\":\"'||noteno||'\",\"NOTEDATE\":\"'||to_char(notedate,'yyyy-mm-dd hh24:mi:ss')||'\"' into v_retcs from provorderh where id=v_id;  ";
		qry += "\n    insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n    values (LOGRECORD_id.nextval," + Accid + ",'采购订单','【新增单据】单据号:'||v_noteno,'" + Operant + "');";
		qry += "\n    exception";
		qry += "\n    when others then ";
		qry += "\n    begin";
		qry += "\n      rollback;";
		qry += "\n      v_retbj:=0;\n   v_retcs:='\"msg\":\"操作失败，请重试！\"' ;";
		qry += "\n    end;";
		qry += "\n  end;";
		qry += "\n  select v_retbj,v_retcs into :retbj,:retcs from dual;";
		qry += "\n end;";
		// System.out.print(qry);
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

		if (Statetag != null && Statetag == 1) // 如果是提交，必须转入店铺，要判断客户及店铺
		{
			if (Provid == null || Provid == 0) {
				errmess = "provid不是一个有效值！";
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

		if (Provid != null) {
			strSql.append("\n   select count(*) into v_count from provide where accid=" + Accid + " and provid=" + Provid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0供应商无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		strSql.append("\n   update provorderh set ");
		// if (Id != null) {
		// strSql.append("id='" + Id + "',");
		// }
		// if (Noteno != null) {
		// strSql.append("noteno='" + Noteno + "',");
		// }
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		// if (Notedate != null) {
		strSql.append("notedate=v_notedate,");
		// }
		if (Provid != null) {
			strSql.append("provid=" + Provid + ",");
		}
		if (Handno != null) {
			strSql.append("handno='" + Handno + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		// if (Totalamt != null) {
		// strSql.append("totalamt='" + Totalamt + "',");
		// }
		// if (Totalcurr != null) {
		// strSql.append("totalcurr='" + Totalcurr + "',");
		// }
		// if (Operant != null) {
		strSql.append("operant='" + Operant + "',");
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
		// if (Bj != null) {
		// strSql.append("bj=" + Bj + ",");
		// }
		// if (Ywly != null) {
		// strSql.append("ywly=" + Ywly + ",");
		// }
		// if (Overbj != null) {
		// strSql.append("overbj=" + Overbj + ",");
		// }
		// if (Totalfactamt != null) {
		// strSql.append("totalfactamt='" + Totalfactamt + "',");
		// }
		if (Cthouseid != null) {// 商城店铺id
			strSql.append("cthouseid=" + Cthouseid + ",");
		}
		strSql.append("lastdate=sysdate,bj=0");
		strSql.append("  where accid=" + Accid + " and noteno='" + Noteno + "' ;");
		if (Statetag != null && Statetag == 1)// 提交时刷新当前单据的总数量与总金额
		{
			strSql.append("\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from provorderm ");
			strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "';");

			strSql.append("\n   update provorderh set totalcurr=v_totalcurr,totalamt=v_totalamt");
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
		strSql.append(" FROM provorderh a");
		strSql.append(" left outer join provide b on a.provid=b.provid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据 ok
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM provorderh a");
		strSql.append("\n left outer join provide b on a.provid=b.provid");
		strSql.append("\n left outer join cityhouse c on a.cthouseid=c.cthouseid");

		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from provorderh");
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
		strSql.append("\n   select count(*) into v_count from provorderh where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=1;");
		strSql.append("\n   if v_count=0 then ");
		strSql.append("\n      v_retcs:='0单据状态异常，请重新进入！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		if (Overbj == 1) // 自动计算完成数量
			strSql.append("\n  p_calcprovorderover(" + Accid + ",'" + Noteno + "',0);");
		strSql.append("\n   update provorderh set overbj=" + Overbj + " where accid=" + Accid + " and noteno='" + Noteno + "';");
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
		qry += "\n     from provorderh where accid=" + Accid + "  and noteno='" + Noteno + "' and statetag=1; ";
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
		qry += "\n   select count(*) into v_count from wareinh where accid=" + Accid + " and statetag<=1 and ntid=0 and rownum=1 and orderno='" + Noteno + "';";
		qry += "\n   if v_count>0 then ";
		qry += "\n      v_retcs:= '0当前订单已有入库记录，不允许撤单！' ; ";
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

		qry += "\n   update provorderh set statetag=0 where accid=" + Accid + "  and noteno='" + Noteno + "' ; ";

		qry += "\n   v_retcs:= '1操作成功！';  ";

		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'采购订货单','【撤单】单据号:" + Noteno + "','" + Operant + "');";
		qry += "\n   commit;";
		// qry += "\n if length(v_orderno)>0 then";// 如果出库单关联了订单，计算订单完成情况
		// qry += "\n p_calcallotorderover(" + Accid + ",v_orderno);"; // 计算订单完成情况
		// qry += "\n end if;";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";
		System.out.println(qry);
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

	// 合并客户订单到采购订单中
	public int MergeFromCust(JSONObject jsonObject, int priceprec) {
		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		String wherestr = "";
		if (!jsonObject.has("custorderlist")) {
			errmess = "未传入要合并的客户订单1！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("custorderlist");
		if (jsonArray.size() == 0) {
			errmess = "未传入要合并的客户订单2！";
			return 0;
		}

		String fh = "";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			wherestr += fh + " a.noteno ='" + jsonObject2.getString("noteno") + "'";
			fh = " or ";
		}
		String qry = "declare ";
		qry += "\n   v_id number;  ";
		qry += "\n   v_accid number;  ";
		qry += "\n   v_fs number(1);  ";
		qry += "\n   v_noteno varchar2(20);  ";
		qry += "\n   v_curr number;  ";
		qry += "\n   v_price number;  ";
		qry += "\n   v_price0 number;  ";
		qry += "\n   v_discount number;  ";
		qry += "\n   v_pricetype number;  ";
		qry += "\n   v_totalcurr number;  ";
		qry += "\n   v_totalamt number;  ";
		qry += "\n begin ";
		qry += "\n   select b.pricetype,b.discount into v_pricetype,v_discount from provorderh a join provide b on a.provid=b.provid";
		qry += "\n   where a.accid=" + Accid + " and a.noteno='" + Noteno + "';";

		qry += "\n  declare cursor cur_data is ";
		qry += "\n     select a.wareid,a.colorid,a.sizeid,b.retailsale,b.entersale,sum(a.amount) as amount";
		qry += "\n     from custorderm a ";
		qry += "\n     left outer join warecode b on a.wareid=b.wareid ";
		qry += "\n     where a.accid=" + Accid + " and (" + wherestr + ")";
		qry += "\n     group by a.wareid,a.colorid,a.sizeid,b.retailsale,b.entersale;";
		qry += "\n     v_row cur_data%rowtype;";
		qry += "\n  begin";
		qry += "\n     for v_row in cur_data loop";
		qry += "\n     begin";
		qry += "\n        select id into v_id from provorderm where accid=" + Accid + " and noteno='" + Noteno + "' ";
		qry += "\n           and wareid=v_row.wareid and colorid=v_row.colorid and sizeid=v_row.sizeid;";
		qry += "\n        update provorderm set amount=amount+v_row.amount,curr=(amount+v_row.amount)*price where id=v_id;";
		qry += "\n     EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n        if v_pricetype=0 then v_price0:=v_row.entersale; else v_price0:=v_row.retailsale; end if;";
		qry += "\n        v_price:=round(v_price0*v_discount," + priceprec + ");";
		qry += "\n        v_curr:=v_price*v_row.amount;";
		qry += "\n        v_id:=provorderm_id.nextval;";
		qry += "\n        insert into provorderm (id,accid,noteno,wareid,colorid,sizeid,amount,price0,discount,price,curr,remark0)";
		qry += "\n        values (v_id," + Accid + ",'" + Noteno + "',v_row.wareid,v_row.colorid,v_row.sizeid,v_row.amount,v_price0,v_discount,v_price,v_curr,'');";
		qry += "\n     end;  ";
		qry += "\n     end loop;";

		// qry += "\n update provorderh set totalcurr=(select nvl(sum(curr),0) from provorderm where provorderh.accid=provorderm.accid and provorderh.noteno=provorderm.noteno) ";
		// qry += "\n ,totalamt=(select nvl(sum(amount),0) from provorderm where provorderh.accid=provorderm.accid and provorderh.noteno=provorderm.noteno )";
		qry += "\n     select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from provorderm where accid=" + Accid + " and noteno='" + Noteno + "';  ";
		qry += "\n     update provorderh set totalcurr=v_totalcurr,totalamt=v_totalamt  ";
		qry += "\n     where accid=" + Accid + " and noteno='" + Noteno + "';";

		qry += "\n  end; ";
		qry += "\n  commit;";
		qry += "\n end; ";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
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
			errmess = "未传入要合并的采购订单1！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("notelist");
		if (jsonArray.size() == 0) {
			errmess = "未传入要合并的采购订单2！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n   v_id number;  ";
		qry += "\n   v_totalamt number;  ";
		qry += "\n   v_totalcurr number;  ";
		qry += "\n   v_count number;  ";
		qry += "\n   v_retcs varchar2(200);  ";
		qry += "\n begin";
		qry += "\n   select count(*) into v_count from provorderh where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=1 and overbj=0;";
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
			qry += "\n   select wareid,colorid,sizeid,amount,price0,discount,price,curr,remark0 from provorderm where accid=" + Accid + " and noteno='" + noteno1 + "';";
			qry += "\n     v_row cur_data%rowtype; ";
			qry += "\n   begin";
			qry += "\n     for v_row in cur_data loop";
			qry += "\n     begin";
			qry += "\n        select id into v_id from provorderm where accid=" + Accid + " and noteno='" + Noteno + "' ";
			qry += "\n           and wareid=v_row.wareid and colorid=v_row.colorid and sizeid=v_row.sizeid;";
			qry += "\n        update provorderm set amount=amount+v_row.amount,curr=(amount+v_row.amount)*price where id=v_id;";
			qry += "\n     EXCEPTION WHEN NO_DATA_FOUND THEN";
			qry += "\n        v_id:=provorderm_id.nextval;";
			qry += "\n        insert into provorderm (id,accid,noteno,wareid,colorid,sizeid,amount,price0,discount,price,curr,remark0)";
			qry += "\n        values (v_id," + Accid + ",'" + Noteno + "',v_row.wareid,v_row.colorid,v_row.sizeid,v_row.amount,v_row.price0,v_row.discount,v_row.price,v_row.curr,v_row.remark0);";
			qry += "\n     end;  ";
			qry += "\n     end loop;";
			qry += "\n   end;  ";
			qry += "\n   update wareinh set orderno='" + Noteno + "' where accid=" + Accid + " and orderno='" + noteno1 + "' and ntid=0;";
			qry += "\n   update provorderh set statetag=2 where accid=" + Accid + " and noteno='" + noteno1 + "';";
			qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
			qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'采购订单','【合并订单】单据号:" + Noteno + ",被合并订单:" + noteno1 + "','" + Operant + "');";

			qry += "\n   commit;";
		}
		qry += "\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from provorderm where accid=" + Accid + " and noteno='" + Noteno + "';  ";
		qry += "\n   update provorderh set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno='" + Noteno + "'; ";
		qry += "\n   p_calcprovorderover(" + Accid + ",'" + Noteno + "',0);";//
		qry += "\n   <<exit>>";
		qry += "\n   v_retcs:='1操作成功！';";
		qry += "\n   select v_retcs into :retcs  from dual;";
		qry += "\n end;";

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
	public int Check(int bj) {
		String qry = "declare";
		qry += "\n   v_count number;";
		qry += "\n   v_checkman varchar2(20);";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_mess varchar2(200);";
		qry += "\n begin";
		qry += "\n   select checkman into v_checkman from provorderh where accid=" + Accid + " and noteno='" + Noteno + "';";
		if (bj == 1) {// 审核
			qry += "\n   if length(v_checkman)>0 then ";
			qry += "\n      v_retcs:='0单据已审核！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update provorderh set checkman='" + Operant + "' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【取消审核】';";

		} else {// 取消审核
			qry += "\n   if v_checkman<>'" + Operant + "' then ";
			qry += "\n      v_retcs:='0系统不允许取消其他用户审核的单据！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update provorderh set checkman='' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【审核】';";
		}
		qry += "\n   v_retcs:='1操作成功！';";
		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'客户订货',v_mess||'单据号:" + Noteno + "','" + Operant + "');";
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
		if (Provid != null) {
			strSql1.append("Provid,");
			strSql2.append(Provid + ",");
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

		strSql.append("\n     select 'CO'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from provorderh");
		strSql.append("\n     where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'CO'||To_Char(SYSDATE,'yyyymmdd');");

		strSql.append("\n     v_id:=provorderh_id.nextval; ");
		strSql.append("\n     insert into provorderh (" + strSql1.toString() + ")");
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
			strSql.append("\n  insert into provorderm (id,accid,noteno,wareid,colorid,sizeid,amount,price0,discount,price,curr,remark0)");
			strSql.append("\n  values (provorderm_id.nextval," + Accid + ",v_noteno," + wareid + "," + colorid + "," + sizeid + "," + amount //
					+ "," + price0 + "," + discount + "," + price + "," + curr + ",'" + remark0 + "');");
		}

		strSql.append("\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from provorderm where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   update provorderh set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   v_retcs:='1\"msg\":\"操作成功\",'||'\"NOTENO\":\"'||v_noteno||'\"';");
		//		strSql.append("\n   select '\"ID\":\"'||id||'\",\"NOTENO\":\"'||noteno||'\",\"NOTEDATE\":\"'||to_char(notedate,'yyyy-mm-dd hh24:mi:ss')||'\"' into v_retcs from firstpaycurr where id=v_id;");

		strSql.append("\n   commit;");
		strSql.append("\n   exception");
		strSql.append("\n   when others then ");
		strSql.append("\n   begin");
		strSql.append("\n     rollback;");
		strSql.append("\n     v_retcs:='0操作失败：'||substr(sqlerrm,1,160);");
//		strSql.append("\n     v_retcs:='0操作失败!';");
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