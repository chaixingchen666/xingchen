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
//  @author:sunhong  @date:2017-03-10 20:19:00
//*************************************
public class Allotorderh implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private String Noteno;
	private Date Notedate;
	private Long Houseid;
	private String Remark;
	private String Handno;
	private Float Totalamt;
	private Float Totalcurr;
	private String Operant;
	private String Checkman;
	private Integer Statetag;
	private String Notedatestr;
	private Date Lastdate;
	private Integer Ywly;
	private Integer Overbj;
	private Float Totalfactamt;
	private String errmess;

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

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
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
		strSql.append("\n begin ");
		strSql.append("\n   select statetag into v_statetag from allotorderh where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n   if v_statetag>0 then ");
		strSql.append("\n      v_retcs:= '0当前单据已提交，不允许删除！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if;");
		//		strSql.append("\n   delete from allotorderm ");
		//		strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "'");
		//		strSql.append("\n   and exists (select 1 from allotorderh a where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
		//		strSql.append("\n   and a.accid=allotorderm.accid and a.noteno=allotorderm.noteno and a.statetag=0);");
		//		strSql.append("\n   delete from allotorderh ");
		//		strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0; ");

		strSql.append("\n   update allotorderh set statetag=2,lastdate=sysdate");
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
		qry += "\n   v_operant nvarchar2(10);";
		qry += "\n   v_noteno nvarchar2(20);";
		qry += "\n   v_houseid  number;";
		qry += "\n   v_id  number;";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_retbj number(1); ";
		qry += "\n begin ";
		qry += "\n   begin ";
		qry += "\n     select 'DO'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from allotorderh";
		qry += "\n     where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'DO'||To_Char(SYSDATE,'yyyymmdd');";
		qry += "\n     v_id:=ALLOTORDERH_ID.NEXTVAL;";
		qry += "\n     insert into ALLOTORDERH (id, accid,noteno,notedate,houseid,statetag,totalamt,totalcurr,remark,handno,operant,checkman,lastdate)";
		qry += "\n     values (v_id," + Accid + ",v_noteno,sysdate," + Houseid + ",0,0,0,'','','" + Operant + "','',sysdate);";
		qry += "\n     commit;";
		qry += "\n     v_retbj:=1;  select '\"msg\":\"操作成功\",\"ID\":\"'||id||'\",\"NOTENO\":\"'||noteno||'\",\"NOTEDATE\":\"'||to_char(notedate,'yyyy-mm-dd hh24:mi:ss')||'\"' into v_retcs from allotorderh where id=v_id;  ";
		qry += "\n    insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n    values (LOGRECORD_id.nextval," + Accid + ",'调拨订单','【新增单据】单据号:'||v_noteno,'" + Operant + "');";
		qry += "\n     exception";
		qry += "\n     when others then ";
		qry += "\n     begin";
		qry += "\n        rollback;";
		qry += "\n        v_retbj:=0;  v_retcs:='\"msg\":\"操作失败，请重试！\"';";
		qry += "\n     end;";
		qry += "\n   end;";
		qry += "\n   select v_retbj,v_retcs into :retbj,:retcs from dual;";
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
			if (Houseid == null || Houseid == 0) {
				errmess = "houseid不是一个有效值！";
				return 0;
			}
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n     v_retcs varchar2(200);");
		strSql.append("\n     v_count number;");
		strSql.append("\n     v_totalcurr number;");
		strSql.append("\n     v_totalamt number;");
		strSql.append("\n   v_notedate date; ");
		strSql.append("\n begin ");
		if (!Func.isNull(Notedatestr)) {
			strSql.append("\n   v_notedate:=to_date('" + Notedatestr + "','yyyy-mm-dd hh24:mi:ss');");
			strSql.append("\n   if to_char(v_notedate,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')  or v_notedate>sysdate then ");
			strSql.append("\n      v_notedate:=sysdate;");
			strSql.append("\n   end if;");
		} else {
			strSql.append("\n   v_notedate:=sysdate;");
		}

		if (Houseid != null) {
			strSql.append("\n   select count(*) into v_count from warehouse where accid=" + Accid + " and houseid=" + Houseid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0店铺无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}

		strSql.append("\n   update Allotorderh set ");
		// }
		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
		}
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
		// }
		if (Checkman != null) {
			strSql.append("checkman='" + Checkman + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Ywly != null) {
			strSql.append("ywly=" + Ywly + ",");
		}
		if (Overbj != null) {
			strSql.append("overbj=" + Overbj + ",");
		}
		// if (Totalfactamt != null) {
		// strSql.append("totalfactamt='" + Totalfactamt + "',");
		// }
		strSql.append("operant='" + Operant + "',");
		strSql.append("notedate=v_notedate,");
		strSql.append("lastdate=sysdate");
		strSql.append("  where accid=" + Accid + " and noteno='" + Noteno + "';");
		if (Statetag != null && Statetag == 1)// 提交时刷新当前单据的总数量与总金额
		{
			strSql.append("\n  select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from allotorderm");
			strSql.append("\n  where accid=" + Accid + " and noteno='" + Noteno + "';");

			strSql.append("\n  update allotorderh set totalcurr=v_totalcurr,totalamt=v_totalamt");
			strSql.append("\n  where accid=" + Accid + " and noteno='" + Noteno + "';");
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
		strSql.append(" FROM allotorderh a");
		strSql.append(" left outer join warehouse b on a.houseid=b.houseid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Allotorderh a");
		strSql.append(" left outer join warehouse b on a.houseid=b.houseid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Allotorderh");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
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
		strSql.append("\n   select count(*) into v_count from allotorderh where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=1;");
		strSql.append("\n   if v_count=0 then ");
		strSql.append("\n      v_retcs:='0单据状态异常，请重新进入！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		if (Overbj == 1) // 自动计算完成数量
			strSql.append("\n  p_calcallotorderover(" + Accid + ",'" + Noteno + "',0);");
		strSql.append("\n   update allotorderh set overbj=" + Overbj + " where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n   v_retcs:='1操作成功！';");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		strSql.append("\n end;");
		//System.out.println(strSql.toString());
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
		qry += "\n   v_notedate varchar2(10); ";
		qry += "\n   v_retcs varchar2(200); ";
		qry += "\n   v_checkman varchar2(20); ";
		qry += "\n   v_count number(10); ";
		qry += "\n begin ";
		qry += "\n   begin";
		qry += "\n     select to_char(notedate,'yyyy-mm-dd'),checkman ";
		qry += "\n     into v_notedate,v_checkman";
		qry += "\n     from allotorderh where accid=" + Accid + "  and noteno='" + Noteno + "' and statetag=1; ";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_retcs:='0未找到单据!';";
		qry += "\n     goto exit;";
		qry += "\n   end;";
		qry += "\n   if length(v_checkman)>0 then ";// 如果收款单关联了销售单，重新刷新销售的的结算方式
		qry += "\n      v_retcs:= '0已审核的单据不允许撤单！'; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if; ";

		qry += "\n   select count(*) into v_count from allotouth where accid=" + Accid + " and orderno='" + Noteno + "' and statetag<=1 and rownum=1;";
		qry += "\n   if v_count>0 then ";
		qry += "\n      v_retcs:= '0当前订单已有调出记录，不允许撤单！' ; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if;";

		//		if (changedatebj == 0) // 不允许更改单据日期
		//		{
		//			qry += "\n   if v_notedate<to_char(sysdate,'yyyy-mm-dd') then ";
		//			qry += "\n      v_retcs:= '0今日之前的单据不允许撤单！'; ";
		//			qry += "\n      goto exit; ";
		//			qry += "\n   end if; ";
		//		}
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

		qry += "\n   update allotorderh set statetag=0 where accid=" + Accid + "  and noteno='" + Noteno + "' ; ";

		qry += "\n   v_retcs:= '1操作成功！';  ";

		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'调拨订货','【撤单】单据号:" + Noteno + "','" + Operant + "');";
		qry += "\n   commit;";
		// qry += "\n if length(v_orderno)>0 then";// 如果出库单关联了订单，计算订单完成情况
		// qry += "\n p_calcallotorderover(" + Accid + ",v_orderno);"; // 计算订单完成情况
		// qry += "\n end if;";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";
		//System.out.println(qry);

		// LogUtils.LogDebugWrite("调拨订单撤单", qry);
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

	// 合并订单
	public int Merge(JSONObject jsonObject, int priceprec) {
		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		Noteno = Noteno.toUpperCase();
		if (!jsonObject.has("notelist")) {
			errmess = "未传入要合并的调拨订单1！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("notelist");
		if (jsonArray.size() == 0) {
			errmess = "未传入要合并的调拨订单2！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n   v_id number;  ";
		qry += "\n   v_totalamt number;  ";
		qry += "\n   v_totalcurr number;  ";
		qry += "\n   v_count number;  ";
		qry += "\n   v_retcs varchar2(200);  ";
		qry += "\n begin";
		qry += "\n   select count(*) into v_count from allotorderh where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=1 and overbj=0;";
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
			qry += "\n   select wareid,colorid,sizeid,amount,price,curr,remark0 from allotorderm where accid=" + Accid + " and noteno='" + noteno1 + "';";
			qry += "\n     v_row cur_data%rowtype; ";
			qry += "\n   begin";
			qry += "\n     for v_row in cur_data loop";
			qry += "\n     begin";
			qry += "\n        select id into v_id from allotorderm where accid=" + Accid + " and noteno='" + Noteno + "' ";
			qry += "\n           and wareid=v_row.wareid and colorid=v_row.colorid and sizeid=v_row.sizeid;";
			qry += "\n        update allotorderm set amount=amount+v_row.amount,curr=(amount+v_row.amount)*price where id=v_id;";
			qry += "\n     EXCEPTION WHEN NO_DATA_FOUND THEN";
			qry += "\n        v_id:=allotorderm_id.nextval;";
			qry += "\n        insert into allotorderm (id,accid,noteno,wareid,colorid,sizeid,amount,price,curr,remark0)";
			qry += "\n        values (v_id," + Accid + ",'" + Noteno + "',v_row.wareid,v_row.colorid,v_row.sizeid,v_row.amount,v_row.price,v_row.curr,v_row.remark0);";
			qry += "\n     end;  ";
			qry += "\n     end loop;";
			qry += "\n   end;  ";
			qry += "\n   update allotouth set orderno='" + Noteno + "' where accid=" + Accid + " and orderno='" + noteno1 + "' and statetag<>2;";
			qry += "\n   update allotorderh set statetag=2 where accid=" + Accid + " and noteno='" + noteno1 + "';";
			qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
			qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'调拨订单','【合并订单】单据号:" + Noteno + ",被合并订单:" + noteno1 + "','" + Operant + "');";

		}
		qry += "\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from allotorderm where accid=" + Accid + " and noteno='" + Noteno + "';  ";
		qry += "\n   update allotorderh set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno='" + Noteno + "'; ";
		qry += "\n   commit;";
		qry += "\n   p_calcallotorderover(" + Accid + ",'" + Noteno + "',0);";//
		qry += "\n   <<exit>>";
		qry += "\n   v_retcs:='1操作成功！';";
		qry += "\n   select v_retcs into :retcs  from dual;";

		qry += "\n   exception";
		qry += "\n   when others then ";
		qry += "\n   begin";
		qry += "\n     rollback;";
		qry += "\n     v_retcs:='0操作失败，请重试！';";
		qry += "\n   end;";
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
		qry += "\n   select checkman into v_checkman from allotorderh where accid=" + Accid + " and noteno='" + Noteno + "';";
		if (bj == 1) {// 审核
			qry += "\n   if length(v_checkman)>0 then ";
			qry += "\n      v_retcs:='0单据已审核！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update allotorderh set checkman='" + Operant + "' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【取消审核】';";

		} else {// 取消审核
			qry += "\n   if v_checkman<>'" + Operant + "' then ";
			qry += "\n      v_retcs:='0系统不允许取消其他用户审核的单据！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update allotorderh set checkman='' where accid=" + Accid + " and noteno='" + Noteno + "';";
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

		if (Houseid != null) {
			strSql1.append("houseid,");
			strSql2.append(Houseid + ",");
		}
		//		if (Custid != null) {
		//			strSql1.append("Custid,");
		//			strSql2.append(Custid + ",");
		//		}

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

		strSql.append("\n     select 'DO'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from allotorderh");
		strSql.append("\n     where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'DO'||To_Char(SYSDATE,'yyyymmdd');");

		strSql.append("\n     v_id:=allotorderh_id.nextval; ");
		strSql.append("\n     insert into allotorderh (" + strSql1.toString() + ")");
		strSql.append("\n     values (" + strSql2.toString() + ");");
		JSONArray jsonArray = jsonObject.getJSONArray("warereclist");

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			long wareid = jsonObject2.has("wareid") ? Long.parseLong(jsonObject2.getString("wareid")) : 0;
			long colorid = jsonObject2.has("colorid") ? Long.parseLong(jsonObject2.getString("colorid")) : 0;
			long sizeid = jsonObject2.has("sizeid") ? Long.parseLong(jsonObject2.getString("sizeid")) : 0;
			Float amount = jsonObject2.has("amount") ? Float.parseFloat(jsonObject2.getString("amount")) : 0;
			//			Float price0 = jsonObject2.has("price0") ? Float.parseFloat(jsonObject2.getString("price0")) : 0;
			//			Float discount = jsonObject2.has("discount") ? Float.parseFloat(jsonObject2.getString("discount")) : 0;
			Float price = jsonObject2.has("price") ? Float.parseFloat(jsonObject2.getString("price")) : 0;
			Float curr = jsonObject2.has("curr") ? Float.parseFloat(jsonObject2.getString("curr")) : 0;
			String remark0 = jsonObject2.has("remark0") ? jsonObject2.getString("remark0") : "";
			strSql.append("\n  insert into allotorderm (id,accid,noteno,wareid,colorid,sizeid,amount,price,curr,remark0)");
			strSql.append("\n  values (allotorderm_id.nextval," + Accid + ",v_noteno," + wareid + "," + colorid + "," + sizeid + "," + amount //
					+ "," + price + "," + curr + ",'" + remark0 + "');");
		}

		strSql.append("\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from allotorderm where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   update allotorderh set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   v_retcs:='1'||'\"msg\":\"操作成功\",\"NOTENO\":\"'||v_noteno||'\"';");
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