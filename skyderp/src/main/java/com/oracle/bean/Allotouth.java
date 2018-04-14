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
//import com.common.tool.Func;
//import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
//import net.sf.json.JSONObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-08 20:55:47
//*************************************
public class Allotouth implements java.io.Serializable {
	private Float Id;
	private String Noteno;
	private Long Accid;
	private Date Notedate;
	private Long Houseid;
	private Long Tohouseid;
	private Integer Ntid;
	private String Remark;
	private String Handno;
	private Float Totalamt;
	private Float Totalcurr;
	private String Operant;
	private String Checkman;
	private Integer Statetag;
	// private Date Lastdate;
	private String Noteno1;
	private String Orderno;
	private Integer Ywly;
	private Integer Cleartag;
	private String errmess;
	private String Notedatestr = "";
	private String Calcdate = "";

	public void setCalcdate(String calcdate) {
		this.Calcdate = calcdate;
	}
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

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public void setTohouseid(Long tohouseid) {
		this.Tohouseid = tohouseid;
	}

	public Long getTohouseid() {
		return Tohouseid;
	}

	public void setNtid(Integer ntid) {
		this.Ntid = ntid;
	}

	public Integer getNtid() {
		return Ntid;
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

	public void setNoteno1(String noteno1) {
		this.Noteno1 = noteno1;
	}

	public String getNoteno1() {
		return Noteno1;
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

	public void setCleartag(Integer cleartag) {
		this.Cleartag = cleartag;
	}

	public Integer getCleartag() {
		return Cleartag;
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
		strSql.append("\n  v_statetag number(1); ");
		strSql.append("\n  v_operant varchar2(20); ");
		strSql.append("\n begin ");
		strSql.append("\n   select statetag,operant into v_statetag,v_operant from allotouth where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n   if v_statetag>0 then ");
		strSql.append("\n      v_retcs:= '0当前单据已提交，不允许删除！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if;");
		strSql.append("\n   if v_operant<>'" + Operant + "' then ");
		strSql.append("\n      v_retcs:= '0不允许删除其他用户的单据！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if;");
		// strSql.append("\n delete from allotoutm ");
		// strSql.append("\n where accid=" + Accid + " and noteno='" + Noteno + "'");
		// strSql.append("\n and exists (select 1 from allotouth a where a.accid=" + Accid+" and a.noteno='"+Noteno+"'");
		// strSql.append("\n and a.accid=allotoutm.accid and a.noteno=allotoutm.noteno and a.statetag=0);");
		// strSql.append("\n delete from allotouth ");
		// strSql.append("\n where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0; ");

		strSql.append("\n   update allotouth set statetag=2 ");
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
		qry += "\n    v_accid  number;";
		qry += "\n    v_operant nvarchar2(10);";
		qry += "\n    v_noteno nvarchar2(20);";
		qry += "\n    v_houseid  number;";
		qry += "\n    v_id  number;";
		qry += "\n    v_retcs nvarchar2(200);";
		qry += "\n    v_retbj number(1); ";
		qry += "\n  begin ";
		qry += "\n    begin ";
		qry += "\n      select 'DC'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from allotouth";
		qry += "\n        where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'DC'||To_Char(SYSDATE,'yyyymmdd');";
		qry += "\n      v_id:=allotouth_ID.NEXTVAL;";
		qry += "\n      insert into allotouth (id, accid,noteno,notedate,houseid,tohouseid,statetag,totalamt,totalcurr,remark,handno,operant,checkman,lastdate,noteno1)";
		qry += "\n      values (v_id," + Accid + ",v_noteno,sysdate," + Houseid + ",0,0,0,0,'','','" + Operant + "','',sysdate,'');";
		qry += "\n      commit;";
		qry += "\n      v_retbj:=1; select '\"msg\":\"操作成功\",\"ID\":\"'||id||'\",\"NOTENO\":\"'||noteno||'\",\"NOTEDATE\":\"'||to_char(notedate,'yyyy-mm-dd hh24:mi:ss')||'\"' into v_retcs from allotouth where id=v_id;  ";
		qry += "\n    insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n    values (LOGRECORD_id.nextval," + Accid + ",'调拨出库','【新增单据】单据号:'||v_noteno,'" + Operant + "');";
		qry += "\n     exception";
		qry += "\n     when others then ";
		qry += "\n     begin";
		qry += "\n       rollback;";
		qry += "\n      v_retbj:=0; v_retcs:='\"msg\":\"操作失败，请重试！\"';";
		qry += "\n     end;";
		qry += "\n   end;";
		qry += "\n   select v_retbj,v_retcs into :retbj,:retcs from dual;";
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
	public int Update(int changedatebj, int fs) {
		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "单据号无效！";
			return 0;
		}

		if (Statetag != null && Statetag == 1) // 如果是提交，必须转入客户及店铺，要判断客户及店铺
		{
			if (Houseid == null || Houseid == 0) {
				errmess = "houseid不是一个有效值！";
				return 0;
			}
			if (Tohouseid == null || Tohouseid == 0) {
				errmess = "tohouseid不是一个有效值！";
				return 0;
			}
		}
		if (!Func.isNull(Notedatestr)) {
			if (Func.subString(Notedatestr, 1, 10).compareTo(Calcdate) <= 0) {
				errmess = "单据日期只能在系统登账日 " + Calcdate + " 之后！";
				return 0;
			}
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n     v_retcs varchar2(200);");
		strSql.append("\n     v_noteno1 varchar2(20);");
		strSql.append("\n     v_orderno varchar2(20);");
		strSql.append("\n     v_count number;");
		strSql.append("\n     v_totalcurr number;");
		strSql.append("\n     v_totalamt number;");
		strSql.append("\n     v_notedate date; ");
		strSql.append("\n begin ");

		if (Notedatestr.length() > 0) {
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
			strSql.append("\n      v_retcs:='0调出店铺无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Tohouseid != null) {
			strSql.append("\n   select count(*) into v_count from warehouse where accid=" + Accid + " and houseid=" + Tohouseid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0调入店铺无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Statetag != null && Statetag == 1 && fs == 0 && Houseid != null)// 提交时刷新当前单据的总数量与总金额
		{

			// strSql.append("\n select notedate into v_notedate from wareouth where accid=" + Accid + " and noteno='" + Noteno + "';");
			// 要判断负库存
			strSql.append("\n begin");
			strSql.append("\n   select b.wareno||','||c.colorname||','||d.sizename into v_retcs from ");
			strSql.append("\n   (select wareid,colorid,sizeid,sum(amount) as amount from allotoutm where accid=" + Accid + " and noteno='" + Noteno + "'");
			strSql.append("\n   group by wareid,colorid,sizeid) a");
			strSql.append("\n   left outer join warecode b on a.wareid=b.wareid");
			strSql.append("\n   left outer join colorcode c on a.colorid=c.colorid");
			strSql.append("\n   left outer join sizecode d on a.sizeid=d.sizeid");
			strSql.append("\n   where a.amount>0 and a.amount>f_getwaresumskux(to_char(v_notedate,'yyyy-mm-dd hh24:mi:ss')," + Accid + ",a.wareid,a.colorid,a.sizeid," + Houseid + ",'" + Calcdate + "') and rownum=1;");
			strSql.append("\n   v_retcs:='0'||v_retcs||'库存不足，不允许出库！'; ");
			strSql.append("\n   goto exit; ");

			strSql.append("\n EXCEPTION WHEN NO_DATA_FOUND THEN ");
			strSql.append("\n   v_retcs:=''; ");
			strSql.append("\n end;");
		}

		strSql.append("\n    update allotouth set ");
		// if (Id != null) {
		// strSql.append("id='" + Id + "',");
		// }
		// if (Noteno != null) {
		// strSql.append("noteno='" + Noteno + "',");
		// }
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		// if (changedatebj == 1 && !Func.isNull(Notedatestr)) {
		// strSql.append("notedate=to_date('" + Notedatestr + "','yyyy-mm-ddhh24:mi:ss'),");
		// } else {
		strSql.append("notedate=v_notedate,");
		// }
		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
		}
		if (Tohouseid != null) {
			strSql.append("tohouseid=" + Tohouseid + ",");
		}
		// if (Ntid != null) {
		// strSql.append("ntid=" + Ntid + ",");
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
		strSql.append("operant='" + Operant + "',");
		// }
		if (Checkman != null) {
			strSql.append("checkman='" + Checkman + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Noteno1 != null) {
			strSql.append("noteno1='" + Noteno1 + "',");
		}
		if (Orderno != null) {
			strSql.append("orderno='" + Orderno + "',");
		}
		if (Ywly != null) {
			strSql.append("ywly=" + Ywly + ",");
		}
		if (Cleartag != null) {
			strSql.append("cleartag=" + Cleartag + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where accid=" + Accid + " and noteno='" + Noteno + "';");
		// strSql.append(" commit;");
		if (Statetag != null && Statetag == 1)// 提交时刷新当前单据的总数量与总金额
		{

			strSql.append("  select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from allotoutm");
			strSql.append("  where accid=" + Accid + " and noteno='" + Noteno + "';");

			strSql.append("  update allotouth set totalcurr=v_totalcurr,totalamt=v_totalamt");
			strSql.append("  where accid=" + Accid + " and noteno='" + Noteno + "';");
			strSql.append("\n  select orderno into v_orderno from allotouth where accid=" + Accid + " and noteno='" + Noteno + "';");
			strSql.append("\n  if length(v_orderno)>0 then "); // 计算订单完成情况
			strSql.append("\n      p_calcallotorderover(" + Accid + ",v_orderno,1);");
			strSql.append("\n  end if;");
			// if (changedatebj == 1) {// 如果提交且允许更改单据日期，要判断是否更改账套结账日期
			// strSql.append("\n update accreg set calcdate=trunc(v_notedate-1) where accid=" + Accid + " and calcdate>=trunc(v_notedate);");
			// }
		}
		strSql.append("   v_retcs:='1操作成功！';");
		strSql.append("   <<exit>>");
		strSql.append("   select v_retcs into :retcs from dual;");
		strSql.append(" end;");
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
		strSql.append("select " + fieldlist + ",c.housename as tohousename");
		// strSql.append(",case To_Char(a.notedate,'yyyy-mm-dd') when To_Char(SYSDATE,'yyyy-mm-dd') then '1' else '0' end as istoday");
		strSql.append(" FROM allotouth a");
		strSql.append(" left outer join warehouse b on a.houseid=b.houseid");
		strSql.append(" left outer join warehouse c on a.tohouseid=c.houseid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist + ",c.housename as tohousename");
		strSql.append("\n FROM allotouth a");
		strSql.append("\n left outer join warehouse b on a.houseid=b.houseid");
		strSql.append("\n left outer join warehouse c on a.tohouseid=c.houseid");
		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from allotouth");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}

	// 载入指定调拨订单明细记录到调拨出库
	public int doOrdertoOut() {
		if (Noteno.length() <= 0) {
			errmess = "调出单号noteno不是一个有效值！";
			return 0;
		}
		if (Orderno.length() <= 0) {
			errmess = "调拨订单号orderno不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n   v_amount number; ";
		qry += "\n   v_totalamt number; ";
		qry += "\n   v_totalcurr number; ";
		qry += "\n   v_overbj number(1); ";
		qry += "\n   v_retcs varchar2(200); ";
		qry += "\n begin ";
		qry += "\n   begin";
		qry += "\n     select overbj into v_overbj from allotorderh where accid=" + Accid + " and noteno='" + Orderno + "' and statetag=1;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n     v_retcs:='0订单未找到！';";
		qry += "\n     goto exit;";
		qry += "\n   end;";
		qry += "\n   if v_overbj=1 then";
		qry += "\n      v_retcs:='0订单已完成！';";
		qry += "\n      goto exit;";
		qry += "\n   end if;";
		// if (Houseid > 0) // 只载入有库存的商品
		// {
		qry += "\n  delete from allotoutm where  accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n  declare cursor cur_data is ";
		qry += "\n  select a.wareid,a.colorid,a.sizeid,a.amount-a.factamt as amount,a.price from allotorderm a";
		qry += "\n     where a.accid=" + Accid + " and a.noteno='" + Orderno + "' and a.amount>a.factamt;";
		qry += "\n     v_row cur_data%rowtype;";
		qry += "\n  begin";
		qry += "\n     for v_row in cur_data loop";
		if (Houseid > 0) // 只载入有库存的商品
		{
			qry += "\n     v_amount:=f_getwareamountx(to_char(sysdate,'yyyy-mm-dd')," + Accid + ",v_row.wareid,v_row.colorid,v_row.sizeid," + Houseid + ",'" + Calcdate + "') ; ";
			qry += "\n     if v_amount>=v_row.amount then"; // 库存数大于订货数，按订货数发货，否则按库存发货
			qry += "\n        v_amount:=v_row.amount; ";
			qry += "\n     end if;";
		} else {
			qry += "\n     v_amount:=v_row.amount; ";

		}

		qry += "\n      if v_amount>0 then ";
		qry += "\n         insert into allotoutm (id,accid,noteno,wareid,colorid,sizeid,amount,price,curr,remark0,lastdate)";
		qry += "\n         values ( allotoutm_id.nextval," + Accid + ",'" + Noteno + "',v_row.wareid,v_row.colorid,v_row.sizeid,v_amount,v_row.price,v_amount*v_row.price,'',sysdate); ";
		qry += "\n      end if;";
		qry += "\n    end loop;";
		qry += "\n   end;";

		qry += "\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from allotoutm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   update allotouth set orderno='" + Orderno + "',totalcurr=v_totalcurr,totalamt=v_totalamt";
		qry += "\n   where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   commit;";
		qry += "\n   v_retcs:='1操作完成！';";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		// qry += "\n p_calcallotorderover(" + Accid + ",'" + Orderno + "');"; // 计算订单完成情况
		qry += "\n end;";
		// LogUtils.LogDebugWrite("doOrdertoOut", qry);
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

	// 撤单
	public int Cancel(int changedatebj) {
		if (Noteno.equals("")) {
			errmess = "单据号无效！";
			return 0;
		}
		String qry = " declare";
		qry += "\n   v_ntid number(1); ";
		qry += "\n   v_notedate varchar2(10); ";
		qry += "\n   v_retcs varchar2(200); ";
		qry += "\n   v_noteno1 varchar2(20); ";
		qry += "\n   v_orderno varchar2(20); ";
		// qry += "\n v_progname varchar2(40); ";
		qry += "\n   v_cleartag number(1);";
		qry += "\n   v_checkman varchar2(20); ";
		qry += "\n   v_count number(10); ";
		qry += "\n begin ";
		qry += "\n   begin";
		qry += "\n     select to_char(notedate,'yyyy-mm-dd'),ntid,cleartag,noteno1,orderno ,checkman";
		qry += "\n     into v_notedate,v_ntid,v_cleartag,v_noteno1,v_orderno,v_checkman";
		qry += "\n     from allotouth where accid=" + Accid + "  and noteno='" + Noteno + "' and statetag=1; ";
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
		// if (changedatebj == 0) // 不允许更改单据日期
		// {
		// qry += "\n if v_notedate<to_char(sysdate,'yyyy-mm-dd') then ";
		// qry += "\n v_retcs:= '0今日之前的单据不允许撤单！'; ";
		// qry += "\n goto exit; ";
		// qry += "\n end if; ";
		// }
		qry += "\n   if v_cleartag=1  then ";
		qry += "\n      v_retcs:= '0当前单据已被冲单，不允许撤单！' ; ";
		qry += "\n      goto exit; ";
		qry += "\n   elsif v_cleartag=2  then ";
		qry += "\n      v_retcs:='0当前单据是系统生成的冲账单据，不允许撤单！' ; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if; ";

		// qry += "\n select count(*) into v_count from allotinh where accid=" + accid + " and noteno1='" + noteno + "' and rownum=1; ";
		// qry += "\n if v_count>0 then ";
		qry += "\n  if length(v_noteno1)>0 then ";
		qry += "\n      v_retcs:= '0当前调出单已有调入单记录，不允许撤单！'; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if; ";
		// qry += "\n v_progname:='调拨出库'; ";

		qry += "\n   update allotouth set statetag=0 where accid=" + Accid + "  and noteno='" + Noteno + "' ; ";

		qry += "\n   v_retcs:= '1操作成功！';  ";

		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'调拨出库','【撤单】单据号:" + Noteno + "','" + Operant + "');";
		qry += "\n   commit;";
		qry += "\n   if length(v_orderno)>0 then";// 如果出库单关联了订单，计算订单完成情况
		qry += "\n      p_calcallotorderover(" + Accid + ",v_orderno,0);"; // 计算订单完成情况
		qry += "\n   end if;";
		// if (changedatebj == 1) // 如果允许更改单据日期， 撤单要更改账套登账日期
		// qry += "\n update accreg set calcdate=trunc(to_date(v_notedate,'yyyy-mm-dd')-1) where accid=" + Accid + " and calcdate>=to_date(v_notedate,'yyyy-mm-dd');";
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

	// 冲单
	public int doClear() {
		if (Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n    v_noteno varchar2(20);";
		qry += "\n    v_noteno1 varchar2(20);";
		qry += "\n    v_count number;";
		qry += "\n    v_tohouseid number;";
		qry += "\n    v_houseid number;";
		qry += "\n    v_retcs varchar2(200);";
		qry += "\n    v_totalcurr number;";
		qry += "\n    v_totalamt number;";
		qry += "\n    v_id number;";
		qry += "\n    v_cleartag number(1);";
		qry += "\n begin";
		qry += "\n    select houseid,tohouseid,noteno1,cleartag into v_houseid,v_tohouseid,v_noteno1,v_cleartag from allotouth where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n    if v_cleartag>0 then ";
		qry += "\n       v_retcs:= '0当前单据已有冲单记录！' ; ";
		qry += "\n       goto exit; ";
		qry += "\n    end if;";
		qry += "\n    if length(v_noteno1)>0 then";
		qry += "\n       v_retcs:= '0当前单据已被调入方收货，不允许冲单！'; ";
		qry += "\n       goto exit; ";
		qry += "\n    end if;";
		// qry += "\n EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n   begin";
		// 生成单据号
		qry += "\n    select 'DC'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from allotouth ";
		qry += "\n    where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'DC'||To_Char(SYSDATE,'yyyymmdd'); ";
		qry += "\n    insert into allotouth (id, accid,noteno,notedate,houseid,tohouseid,ntid,statetag,totalamt,totalcurr,remark,handno,orderno,operant,checkman,cleartag,lastdate) ";
		qry += "\n    values (allotouth_ID.NEXTVAL," + Accid + ",v_noteno,sysdate,v_houseid,v_tohouseid,0,1,0,0,'冲单：" + Noteno + "','" + Noteno + "','','" + Operant + "','',2,sysdate); ";
		// 冲商品明细
		qry += "\n    v_count:=1;";
		qry += "\n    declare cursor cur_data is ";
		qry += "\n      select a.wareid,a.colorid,a.sizeid,a.amount,a.price,a.curr,a.remark0";
		qry += "\n      from allotoutm a ";// join warecode b on a.wareid=b.wareid";
		qry += "\n      where a.accid=" + Accid + " and a.noteno='" + Noteno + "';";
		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n    begin";
		qry += "\n      for v_row in cur_data loop";
		qry += "\n         insert into allotoutm (id,accid,noteno,wareid,colorid,sizeid,amount,price,curr,remark0)";
		qry += "\n         values (allotoutm_id.nextval," + Accid + ",v_noteno,v_row.wareid,v_row.colorid,v_row.sizeid,-v_row.amount,v_row.price,-v_row.curr,v_row.remark0);";
		qry += "\n         v_count:=v_count+1;";
		qry += "\n         if v_count>500 then ";
		qry += "\n            v_count:=1;";
		qry += "\n            commit;";
		qry += "\n         end if;";
		qry += "\n      end loop;";
		qry += "\n    end;";

		qry += "\n    select  nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from allotoutm where accid=" + Accid + " and noteno=v_noteno ;";
		qry += "\n    update allotouth set totalcurr=v_totalcurr,totalamt=v_totalamt  where accid=" + Accid + " and noteno=v_noteno;";
		qry += "\n    update allotouth set cleartag=1,remark=remark||'(已被'||v_noteno||'冲单)'  where accid=" + Accid + " and noteno='" + Noteno + "';"; // 将原单据cleartag=1，表示已冲单
		qry += "\n    commit;";
		qry += "\n    insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n    values (LOGRECORD_id.nextval," + Accid + ",'调出冲单','【冲单】原单据号:" + Noteno + ";新单据号:'||v_noteno,'" + Operant + "');";
		qry += "\n    v_retcs:= '1冲单成功！' ; ";
		qry += "\n    exception  ";
		qry += "\n    when others then  ";
		qry += "\n    begin ";
		qry += "\n      rollback; ";
		qry += "\n      v_retcs:= '0冲单失败！'; ";
		qry += "\n    end; ";
		qry += "\n  end;";
		qry += "\n  <<exit>>";
		qry += "\n  select v_retcs into :retcs from dual;";
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
		qry += "\n   v_checkman varchar2(20);";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_mess varchar2(200);";
		qry += "\n begin";
		qry += "\n   select checkman into v_checkman from allotouth where accid=" + Accid + " and noteno='" + Noteno + "';";
		if (bj == 1) {// 审核
			qry += "\n   if length(v_checkman)>0 then ";
			qry += "\n      v_retcs:='0单据已审核！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update allotouth set checkman='" + Operant + "' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【取消审核】';";

		} else {// 取消审核
			qry += "\n   if v_checkman<>'" + Operant + "' then ";
			qry += "\n      v_retcs:='0系统不允许取消其他用户审核的单据！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update allotouth set checkman='' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【审核】';";
		}
		qry += "\n   v_retcs:='1操作成功！';";
		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'调拨出库',v_mess||'单据号:" + Noteno + "','" + Operant + "');";
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

		if (!Func.isNull(Notedatestr)) {
			if (Func.subString(Notedatestr, 1, 10).compareTo(Calcdate) <= 0) {
				errmess = "单据日期只能在系统登账日 " + Calcdate + " 之后！";
				return 0;
			}
		}

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
		if (Tohouseid != null) {
			strSql1.append("Tohouseid,");
			strSql2.append(Tohouseid + ",");
		}

		if (Handno != null) {
			strSql1.append("handno,");
			strSql2.append("'" + Handno + "',");
		}
		if (Noteno1 != null) {
			strSql1.append("Noteno1,");
			strSql2.append("'" + Noteno1 + "',");
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

		strSql.append("\n     select 'DC'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from allotouth");
		strSql.append("\n     where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'DC'||To_Char(SYSDATE,'yyyymmdd');");

		strSql.append("\n     v_id:=allotouth_id.nextval; ");
		strSql.append("\n     insert into allotouth (" + strSql1.toString() + ")");
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
			strSql.append("\n  insert into allotoutm (id,accid,noteno,wareid,colorid,sizeid,amount,price,curr,remark0)");
			strSql.append("\n  values (allotoutm_id.nextval," + Accid + ",v_noteno," + wareid + "," + colorid + "," + sizeid + "," + amount //
					+ "," + price + "," + curr + ",'" + remark0 + "');");
		}

		strSql.append("\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from allotoutm where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   update allotouth set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   v_retcs:='1'||'\"msg\":\"操作成功\",\"NOTENO\":\"'||v_noteno||'\"';");
		strSql.append("\n   commit;");
		strSql.append("\n   exception");
		strSql.append("\n   when others then ");
		strSql.append("\n   begin");
		strSql.append("\n     rollback;");
		strSql.append("\n     v_retcs:='0操作失败：'||substr(sqlerrm,1,160);");
		strSql.append("\n   end;");
		strSql.append("\n  end; ");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs into :retcs from dual;");
		strSql.append("\n end;");
				System.out.print(strSql.toString());
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