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
//  @author:sunhong  @date:2017-03-27 15:47:27
//*************************************
public class Warepeih implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6846494410042043640L;
	private Float Id;
	private Long Accid;
	private Long Userid;
	private String Noteno;
	private Date Notedate;
	private Long Houseid;
	private Long Custid;
	private Long Dptid;
	private String Handno;
	private String Remark;
	private Integer Statetag;
	private String Xsnoteno;
	private String Orderno;
	private Float Totalamt;
	private Float Totalcurr;
	private Integer Overbj;
	private String Operant;
	private String Checkman;
	private String Notedatestr = "";
	// private Date Lastdate;
	private Long Salemanid;
	private Float Totalfactamt;
	private Long Prtnum;
	private String Pickman;
	private String errmess;
	private String Calcdate = "";
	private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setAccbegindate(String accbegindate) {
		this.Accbegindate = accbegindate;
	}
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

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public void setUserid(Long userid) {
		this.Userid = userid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setNoteno(String noteno) {
		this.Noteno = noteno;
	}

	public void setOrderno(String orderno) {
		this.Orderno = orderno;
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

	public void setCustid(Long custid) {
		this.Custid = custid;
	}

	public Long getCustid() {
		return Custid;
	}

	public void setDptid(Long dptid) {
		this.Dptid = dptid;
	}

	public Long getDptid() {
		return Dptid;
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

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setXsnoteno(String xsnoteno) {
		this.Xsnoteno = xsnoteno;
	}

	public String getXsnoteno() {
		return Xsnoteno;
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

	public void setOverbj(Integer overbj) {
		this.Overbj = overbj;
	}

	public Integer getOverbj() {
		return Overbj;
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

	public void setSalemanid(Long salemanid) {
		this.Salemanid = salemanid;
	}

	public Long getSalemanid() {
		return Salemanid;
	}

	public void setTotalfactamt(Float totalfactamt) {
		this.Totalfactamt = totalfactamt;
	}

	public Float getTotalfactamt() {
		return Totalfactamt;
	}

	public void setPrtnum(Long prtnum) {
		this.Prtnum = prtnum;
	}

	public Long getPrtnum() {
		return Prtnum;
	}

	public void setPickman(String pickman) {
		this.Pickman = pickman;
	}

	public String getPickman() {
		return Pickman;
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
		strSql.append("declare ");
		strSql.append("\n  v_retcs varchar2(200); ");
		strSql.append("\n  v_statetag number(1); ");
		strSql.append("\n begin ");
		strSql.append("\n   begin");

		strSql.append("\n   select statetag into v_statetag from warepeih where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n   if v_statetag>0 then ");
		strSql.append("\n      v_retcs:= '0当前单据已提交，不允许删除！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if;");
//warepeih->statetag:0=草稿,1=提交,2=审核,3=配货中，4=配货完，5=已转出库单
		strSql.append("\n   delete from warepeim ");
		strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "'");
		strSql.append("\n   and exists (select 1 from warepeih a where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
		strSql.append("\n   and a.accid=warepeim.accid and a.noteno=warepeim.noteno and a.statetag=0);");
		strSql.append("\n   delete from warepeih ");
		strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0; ");
		//		strSql.append("\n   update warepeih set statetag=2,lastdate=sysdate");
		//		strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0; ");
		strSql.append("\n   v_retcs:='1操作成功！';");
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN ");
		strSql.append("\n    v_retcs:='0操作失败！';");
		strSql.append("\n   end;");
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
	public int Append(long epid) {

		String qry = "declare ";
		qry += "   v_accid  number;";
		qry += "   v_operant nvarchar2(10);";
		qry += "   v_noteno nvarchar2(20);";
		qry += "   v_custid  number;";
		qry += "   v_id  number;";
		qry += "   v_retcs nvarchar2(100);";
		qry += "   v_retbj number(1); ";
		qry += "\n begin ";
		qry += "\n   begin ";
		// qry += " v_accid:=" + accid + ";";
		// qry += " v_custid:=" + custid + ";";
		// qry += " v_operant:='" + lastop + "';";
		qry += "\n     select 'PH'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from warepeih";
		qry += "\n     where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'PH'||To_Char(SYSDATE,'yyyymmdd');";
		qry += "\n     v_id:=warepeiH_ID.NEXTVAL;";
		qry += "\n     insert into warepeih (id, accid,noteno,notedate,custid,houseid,dptid,overbj,statetag,totalamt,totalcurr,remark,handno,xsnoteno,operant,checkman,lastdate,salemanid)";
		qry += "\n     values (v_id," + Accid + ",v_noteno,sysdate," + Custid + "," + Houseid + ",0,0,0,0,0,'','','','" + Operant + "','',sysdate," + epid + ");";
		qry += "\n     commit;";
		qry += "\n     v_retbj:=1;  select '\"msg\":\"操作成功\",\"ID\":\"'||id||'\",\"NOTENO\":\"'||noteno||'\",\"NOTEDATE\":\"'||to_char(notedate,'yyyy-mm-dd hh24:mi:ss')||'\"' into v_retcs from warepeih where id=v_id;  ";
		qry += "\n    exception";
		qry += "\n    when others then ";
		qry += "\n    begin";
		qry += "\n      rollback;";
		qry += "\n      v_retbj:=0;  v_retcs:='\"msg\":\"操作失败，请重试！\"' ;";
		qry += "\n    end;";
		qry += "\n  end; ";
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
		if (Func.isNull(Noteno)) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("   v_totalcurr number;");
		strSql.append("   v_totalamt number;");
		strSql.append("   v_count number;");
		strSql.append("   v_retcs varchar2(200);");
		strSql.append("   v_orderno varchar2(20);");
		strSql.append("\n     v_notedate date; ");
		strSql.append("\n begin ");

		if (!Func.isNull(Notedatestr)) {
			strSql.append("\n   v_notedate:=to_date('" + Notedatestr + "','yyyy-mm-dd hh24:mi:ss');");
			strSql.append("\n   if to_char(v_notedate,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') then ");
			strSql.append("\n      v_notedate:=sysdate;");
			strSql.append("\n   end if;");
		} else {
			strSql.append("\n   v_notedate:=sysdate;");
		}
		if (Houseid != null) {
			strSql.append("\n   select count(*) into v_count from warehouse where accid=" + Accid + " and houseid=" + Houseid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then");
			strSql.append("\n      v_retcs:='0店铺无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Custid != null) {
			strSql.append("\n   select count(*) into v_count from customer where accid=" + Accid + " and custid=" + Custid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then");
			strSql.append("\n      v_retcs:='0客户无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Dptid != null && Dptid > 0) {
			strSql.append("\n   select count(*) into v_count from department where accid=" + Accid + " and dptid=" + Dptid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then");
			strSql.append("\n      v_retcs:='0部门无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		// if (Id != null) {
		// strSql.append("id='" + Id + "',");
		// }
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		// if (Noteno != null) {
		// strSql.append("noteno='" + Noteno + "',");
		// }
		// if (Notedate != null) {
		// strSql.append("notedate='" + Notedate + "',");
		// }
		strSql.append("\n   update warepeih set ");
		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
		}
		if (Custid != null) {
			strSql.append("custid=" + Custid + ",");
		}
		if (Dptid != null) {
			strSql.append("dptid=" + Dptid + ",");
		}
		if (Handno != null) {
			strSql.append("handno='" + Handno + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		// if (Statetag != null) {
		// strSql.append("statetag=" + Statetag + ",");
		// }
		if (Xsnoteno != null) {
			strSql.append("xsnoteno='" + Xsnoteno + "',");
		}
		// if (Totalamt != null) {
		// strSql.append("totalamt='" + Totalamt + "',");
		// }
		// if (Totalcurr != null) {
		// strSql.append("totalcurr='" + Totalcurr + "',");
		// }
		if (Overbj != null) {
			strSql.append("overbj=" + Overbj + ",");
		}
		// if (Operant != null) {
		// }
		if (Checkman != null) {
			strSql.append("checkman='" + Checkman + "',");
		}
		if (Salemanid != null) {
			strSql.append("salemanid=" + Salemanid + ",");
		}

		if (Pickman != null) {
			strSql.append("pickman='" + Pickman + "',");
		}
		strSql.append("operant='" + Operant + "',");
		strSql.append("notedate=v_notedate,lastdate=sysdate");
		strSql.append("\n  where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n  commit;");
		if (Statetag != null && Statetag == 1) // 提交时刷新当前单据的总数量与总金额
		{
			strSql.append("\n  select nvl(sum(curr),0),nvl(sum(amount),0) into v_totalcurr,v_totalamt from warepeim ");
			strSql.append("\n  where accid=" + Accid + " and noteno='" + Noteno + "';");
			strSql.append("\n  update warepeih set totalcurr=v_totalcurr,totalamt=v_totalamt,statetag=1");
			strSql.append("\n  where accid=" + Accid + " and noteno='" + Noteno + "';");
			strSql.append("\n   insert into LOGRECORD (id,accid,progname,remark,lastop)");
			strSql.append("\n   values (LOGRECORD_id.nextval," + Accid + ",'客户配货','【保存并提交】单据号:" + Noteno + "','" + Operant + "');");
			// 计算订单完成情况
			strSql.append("\n   select orderno into v_orderno from warepeih where accid=" + Accid + " and noteno='" + Noteno + "';");
			strSql.append("\n   if length(v_orderno)>0 then ");
			strSql.append("\n      p_calccustorderpei(" + Accid + ",v_orderno);");
			strSql.append("\n   end if ; ");
		}
		strSql.append("\n  v_retcs:='1操作成功！';");
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
		return Integer.parseInt(retcs.substring(0, 1));
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(",case To_Char(a.notedate,'yyyy-mm-dd') when To_Char(SYSDATE,'yyyy-mm-dd') then '1' else '0' end as istoday");
		strSql.append("\n FROM warepeih a");
		strSql.append("\n left outer join customer b on a.custid=b.custid");
		strSql.append("\n left outer join warehouse c on a.houseid=c.houseid");
		strSql.append("\n left outer join department d on a.dptid=d.dptid");
		strSql.append("\n left outer join employe e on a.salemanid=e.epid");
		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		// //System.out.println(strSql.toString());
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(", '1' as istoday");
		strSql.append("\n FROM warepeih a");
		strSql.append("\n left outer join customer b on a.custid=b.custid");
		strSql.append("\n left outer join warehouse c on a.houseid=c.houseid");
		strSql.append("\n left outer join department d on a.dptid=d.dptid");
		strSql.append("\n left outer join employe e on a.salemanid=e.epid");
		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		qp.setSumString("nvl(sum(totalamt),0) as totalamount,nvl(sum(totalfactamt),0) as totalfactamt,nvl(sum(TOTALCYAMT),0) as TOTALCYAMT,nvl(sum(totalcurr),0) as totalcurr");

		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from warepeih");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 更改商品配货单记录状态
	public int Change(int opid) {
		if (Func.isNull(Noteno)) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		String qry = "declare";
		qry += "\n   v_count number; ";
		qry += "\n   v_id number; ";
		qry += "\n   v_progid number(10); ";
		qry += "\n   v_checkman varchar2(20); ";
		qry += "\n   v_xsnoteno  varchar2(20); ";
		qry += "\n   v_prtid number; ";
		qry += "\n   v_houseid number(10); ";
		qry += "\n   v_retcs  varchar2(100); ";
		qry += "\n   v_mess  varchar2(100); ";
		qry += "\n begin";
		if (opid == 0) // 提交返单:statetag=1->0
		{ // 如果已审核，不允许返单
			qry += "\n   select count(*) into v_count from warepeih where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=1; ";
			qry += "\n   if v_count=0 then ";
			qry += "\n      v_retcs:= '0单据状态已变化，请刷新后重试！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update warepeih set statetag=0 where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=1;";
			qry += "\n   v_mess:='提交->草稿';";
			qry += "\n   v_retcs:='1操作完成！';";
		} else if (opid == 1) // 审核:statetag:1->2 提交转为审核
		{
			qry += "\n   select count(*) into v_count from warepeih where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=1; ";
			qry += "\n   if v_count=0 then ";
			qry += "\n      v_retcs:= '0单据状态已变化，请刷新后重试！' ;";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   v_mess:='提交->审核';";
			qry += "\n   update warepeih set statetag=2,checkman='" + Operant + "' where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=1;";
			qry += "\n   v_retcs:= '1操作完成！' ;";
		} else if (opid == 2) // 取消审核:statetag:2->1 审核 转提交
		{
			qry += "\n   select count(*) into v_count from warepeih where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=2; ";
			qry += "\n   if v_count=0 then ";
			qry += "\n      v_retcs:= '0单据状态已变化，请刷新后重试！' ;";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   select checkman into v_checkman from warepeih where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   if v_checkman<>'" + Operant + "' then ";
			qry += "\n      v_retcs:= '0不允许取消其他用户审核的单据！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update warepeih set statetag=1,checkman='' where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=2;";
			qry += "\n   v_mess:='审核->提交';";
			qry += "\n   v_retcs:= '1操作完成！' ;";
		} else if (opid == 3) // 开始捡货:statetag:2->3 审核转为捡货中
		{
			qry += "\n   select count(*) into v_count from warepeih where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=2; ";
			qry += "\n   if v_count=0 then ";
			qry += "\n      v_retcs:= '0单据状态已变化，请刷新后重试！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update warepeih set statetag=3,pickman='" + Operant + "' where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=2;";
			qry += "\n   v_mess:='审核->捡货';";
			qry += "\n   v_retcs:= '1操作完成！';";
		} else if (opid == 4) // 中止捡货:statetag:3->2
		{
			// opid:0=返单，1=审核，2=取消审核，3=开始配货，4=中止配货，5=配货完成，6=配货完成返回
			// statetag:0=草稿,1=提交,2=审核,3=配货中，4=配货完成，5=已转出库单

			qry += "\n   select count(*) into v_count from warepeih where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=3; ";
			qry += "\n   if v_count=0 then ";
			qry += "\n      v_retcs:= '0单据状态已变化，请刷新后重试！' ;";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   select pickman into v_checkman from warepeih where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   if v_checkman<>'" + Operant + "' then ";
			qry += "\n      v_retcs:= '0不允许中止其他用户正在捡货的单据！' ;";
			qry += "\n      goto exit;";
			qry += "\n   end if;";

			qry += "\n   update warepeih set statetag=2 where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=3;";
			qry += "\n   v_mess:='捡货->审核';";
			qry += "\n   v_retcs:= '1操作完成！' ;";
		} else

		if (opid == 5) // 配货完成:statetag:3->4
		{
			qry += "\n   select count(*) into v_count from warepeih where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=3; ";
			qry += "\n   if v_count=0 then ";
			qry += "\n      v_retcs:= '0单据状态已变化，请刷新后重试！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update warepeih set statetag=4 where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=3;";
			qry += "\n   v_mess:='捡货->完成';";
			qry += "\n   v_retcs:= '1操作完成！';";
		} else if (opid == 6) // 配货完成返回:statetag:4->3
		{
			qry += "\n   select count(*) into v_count from warepeih where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=4; ";
			qry += "\n   if v_count=0 then ";
			qry += "\n      v_retcs:= '0单据状态已变化，请刷新后重试！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update warepeih set statetag=3 where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=4;";
			qry += "\n   v_mess:='完成->捡货';";
			qry += "\n   v_retcs:= '1操作完成！' ;";
		} else if (opid == 7) // 发货标志(已出货的配货单才能许发货)
		{

			qry += "\n   select count(*) into v_count from warepeih where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=5 and overbj=0; ";
			qry += "\n   if v_count=0 then ";
			qry += "\n      v_retcs:= '0单据状态已变化，请刷新后重试！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update warepeih set overbj=1 where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=5;";
			qry += "\n   v_mess:='发货标志';";
			qry += "\n   v_retcs:= '1操作完成！';";
		} else if (opid == 8) // 传后台打印(已出货的配货单才能许传后打印)
		{
			qry += "\n   select count(*) into v_count from warepeih where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=5; ";
			qry += "\n   if v_count=0 then ";
			qry += "\n      v_retcs:= '0单据状态已变化，请刷新后重试！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   v_progid:=1302;"; // 批发开票
			// 找出库单号
			qry += "\n   select id,xsnoteno,houseid into v_id,v_xsnoteno,v_houseid from warepeih where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=5; ";
			qry += "\n   select count(*) into v_count from backprint where accid=" + Accid + " and progid=v_progid and noteno=v_xsnoteno; ";
			qry += "\n    if v_count=0 then ";
			qry += "\n       begin  ";
			qry += "\n         select prtid into v_prtid from PROGPRINT where epid=" + Userid + " and progid=v_progid; "; // 取默认打印机
			qry += "\n       EXCEPTION WHEN NO_DATA_FOUND THEN";
			qry += "\n         v_prtid:=0; ";
			qry += "\n         v_retcs:= '0未找到默认后台打印机，请先设置...' ;"; // 暂时注释
			qry += "\n         goto exit; ";
			qry += "\n       end;";
			qry += "\n       insert into backprint(id,accid,progid,houseid,noteno,noteid,prtid,lastop)";
			qry += "\n       values (backprint_id.nextval," + Accid + ",v_progid,v_houseid ,v_xsnoteno,v_id,v_prtid,'" + Operant + "'); ";
			qry += "\n       v_retcs:= '1单据已传送到后台打印队列，稍候开始打印...';";
			// qry += "\n update warepeih set prtnum=prtnum+1 where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=5;";
			qry += "\n    else ";
			qry += "\n       v_retcs:= '0单据已存在于后台打印队列，不能重复发送。';";
			qry += "\n    end if;";

		} else
		// statetag:0=草稿,1=提交,2=审核,3=捡货中，4=捡货完成，5=已转出库单
		if (opid == 9) // 出库返单:statetag:5->4
		{
			qry += "\n   select count(*) into v_count from wareouth where accid=" + Accid + " and ntid=1 and ywnoteno='" + Noteno + "' and statetag<>2; ";
			qry += "\n   if v_count>0 then ";
			qry += "\n      v_retcs:= '0当前配货已生成批发出库单，不允许更改状态！' ; ";
			qry += "\n      goto exit; ";
			qry += "\n   end if; ";

			qry += "\n   select count(*) into v_count from warepeih where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=5; ";
			qry += "\n   if v_count=0 then ";
			qry += "\n      v_retcs:= '0单据状态已变化，请刷新后重试！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update warepeih set statetag=4,xsnoteno='' where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=5;";
			qry += "\n   v_mess:='出库返单';";
			qry += "\n   v_retcs:= '1操作完成！';";
		}

		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'客户配货','【更改状态】单据号:" + Noteno + "'||v_mess,'" + Operant + "');";

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
		qry += "\n   v_orderno varchar2(100); ";
		qry += "\n   v_count number(10); ";
		qry += "\n begin ";
		qry += "\n   begin";
		qry += "\n     select to_char(notedate,'yyyy-mm-dd'),orderno ";
		qry += "\n     into v_notedate,v_orderno";
		qry += "\n     from warepeih where accid=" + Accid + " and noteno='" + Noteno + "' and statetag>=1; ";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_retcs:='0未找到单据!';";
		qry += "\n     goto exit;";
		qry += "\n   end;";

		// if (changedatebj == 0) // 不允许更改单据日期
		// {
		// qry += "\n if v_notedate<to_char(sysdate,'yyyy-mm-dd') then ";
		// qry += "\n v_retcs:= '0今日之前的单据不允许撤单！'; ";
		// qry += "\n goto exit; ";
		// qry += "\n end if; ";
		// }
		qry += "\n   select count(*) into v_count from wareouth where accid=" + Accid + " and ntid=1 and ywnoteno='" + Noteno + "' and statetag<>2; ";
		qry += "\n   if v_count>0 then ";
		qry += "\n      v_retcs:= '0当前配货已生成批发出库单，不允许撤单！' ; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if; ";

		qry += "\n   update warepeih set statetag=0,xsnoteno='' where accid=" + Accid + "  and noteno='" + Noteno + "' ; ";
		qry += "\n   v_retcs:= '1操作成功！';  ";

		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'客户配货','【撤单】单据号:" + Noteno + "','" + Operant + "');";
		qry += "\n   commit;";
		qry += "\n   if length(v_orderno)>0 then ";// 重算订单的配货情况
		qry += "\n      p_calccustorderpei(" + Accid + ",v_orderno);";
		qry += "\n   end if ; ";
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

	// 载入指定客户订单明细记录到配货单
	public int doOrdertoPei() {

		String qry = "declare ";
		qry += "\n  v_saleid number(10); ";
		qry += "\n  v_amount number; ";
		qry += "\n  v_totalamt number; ";
		qry += "\n  v_totalcurr number; ";

		qry += "\n   v_overbj number(1); ";
		qry += "\n   v_retcs varchar2(200); ";
		qry += "\n begin ";
		qry += "\n   begin";
		qry += "\n     select overbj into v_overbj from custorderh where accid=" + Accid + " and noteno='" + Orderno + "' and statetag=1;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n     v_retcs:='0订单未找到！';";
		qry += "\n     goto exit;";
		qry += "\n   end;";
		qry += "\n   if v_overbj=1 then";
		qry += "\n      v_retcs:='0订单已完成！';";
		qry += "\n      goto exit;";
		qry += "\n   end if;";

		qry += "\n   v_saleid:=1;";
		qry += "\n   delete from warepeim where  accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   declare cursor cur_data is ";
		qry += "\n   select a.wareid,a.colorid,a.sizeid,a.amount-a.peiamt as amount,a.price0,a.discount,a.price from custorderm a";
		qry += "\n   where a.accid=" + Accid + " and a.noteno='" + Orderno + "' and a.amount>a.peiamt;";
		// if (ordernolist != null && ordernolist != "") qry += "\n and " + myCondition("a.noteno", orderno, 1);
		// if (orderno != null && orderno != "") qry += "\n and a.noteno='" + orderno + "'";
		// qry += "\n ;";
		qry += "\n   v_row cur_data%rowtype;";
		qry += "\n begin";
		qry += "\n   for v_row in cur_data loop";
		if (Houseid > 0) // 只载入有库存的商品
		{
			qry += "\n     v_amount:=f_getwareamountx(to_char(sysdate,'yyyy-mm-dd')," + Accid + ",v_row.wareid,v_row.colorid,v_row.sizeid," + Houseid + ",'" + Calcdate + "') ; ";
			qry += "\n     if v_amount>=v_row.amount then";
			qry += "\n        v_amount:=v_row.amount; ";
			qry += "\n     end if;";
		} else {
			qry += "\n     v_amount:=v_row.amount; ";
		}
		qry += "\n     if v_amount>0 then ";
		qry += "\n        insert into warepeim (id,accid,noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,curr,remark0,lastdate)";
		qry += "\n        values ( warepeim_id.nextval," + Accid + ",'" + Noteno + "',v_row.wareid,v_row.colorid,v_row.sizeid,v_saleid,v_amount,v_row.price0,v_row.discount,v_row.price,v_amount*v_row.price,'',sysdate); ";
		qry += "\n     end if;";
		qry += "\n   end loop;";
		qry += "\n end;";

		qry += "\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from warepeim where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   update warepeih set orderno='" + Orderno + "',totalcurr=v_totalcurr,totalamt=v_totalamt";
		qry += "\n   where accid=" + Accid + " and noteno='" + Noteno + "';";
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

	// 审核/反审
	public int Check(int checkid) {
		// checkid:1=审核，0=取消审核,2=财务审核，3=取消财务
		String qry = "declare";
		qry += "\n   v_count number;";
		qry += "\n   v_ntid number(1);";
		qry += "\n   v_checkman varchar2(20);";
		qry += "\n   v_cwcheck varchar2(20);";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_mess varchar2(200);";
		qry += "\n begin";
		qry += "\n   select checkman,cwcheck into v_checkman,v_cwcheck from warepeih where accid=" + Accid + " and noteno='" + Noteno + "';";
		if (checkid == 2) {// 财务审核
			qry += "\n   if length(v_checkman)=0 then ";
			qry += "\n      v_retcs:='0未经过业务审核的单据，不允许财务审核！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   if length(v_cwcheck)>0 then ";
			qry += "\n      v_retcs:='0单据已进行财务审核！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update warepeih set cwcheck='" + Operant + "' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【财务审核】';";

		} else if (checkid == 3) {// 财务取消审核
			qry += "\n   if v_cwcheck<>'" + Operant + "' then ";
			qry += "\n      v_retcs:='0系统不允许取消其他财务审核的单据！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update warepeih set cwcheck='' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【取消财务审核】';";
		} else if (checkid == 1) {// 业务审核
			qry += "\n   if length(v_checkman)>0 then ";
			qry += "\n      v_retcs:='0单据业务已审核！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update warepeih set checkman='" + Operant + "' where accid=" + Accid + " and noteno='" + Noteno + "';";
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
			qry += "\n   update warepeih set checkman='' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【取消业务审核】';";
		}
		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'配货单',v_mess||'单据号:" + Noteno + "','" + Operant + "');";
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

	// 获取批发分页数据 勾单记录
	public Table GetTable(QueryParam qp) {
		// String qry = " select
		// a.noteno,a.notedate,a.remark,a.curr,a.payid,b.payname,a.operant ";
		// qry += "\n from incomecurr a";
		// qry += "\n left outer join payway b on a.payid=b.payid";
		// qry += "\n where a.statetag=1 and a.accid=" + Accid;
		// // incomelink->notetype:0销售(退库) 1物料销售（退库），2=配货
		// qry += "\n and exists (select 1 from incomelink c where c.noteno='" +
		// Noteno + "'";
		// qry += "\n and c.notetype=0 and a.accid=c.accid and
		// a.noteno=c.pnoteno)";

		String qry = " select a.noteno,a.notedate,a.remark,a.curr,a.payid,b.payname,a.operant ";
		qry += "\n from incomecurr a";
		qry += "\n left outer join payway b on a.payid=b.payid";
		qry += "\n where a.statetag=1 and a.accid=" + Accid;
		qry += "\n and a.phno='" + Noteno + "'";

		qp.setQueryString(qry);
		qp.setSumString("nvl(sum(curr),0) as totalcurr,nvl(sum(curr0),0) as totalcurr0");
		return DbHelperSQL.GetTable(qp);
	}

	//载入未发货商品库存
	public int Loadnosendkc() {
		if (Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		String qry = "declare";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_discount number(10,2);";
		qry += "\n   v_pricetype number(1);";
		qry += "\n   v_curr number(16,2);";
		qry += "\n   v_price number;";
		qry += "\n   v_count number;";
		qry += "\n   v_custid number(10);";
		qry += "\n   v_totalamt number;";
		qry += "\n   v_totalcurr number;";
		qry += "\n begin";
		qry += "\n    begin";
		qry += "\n      select a.custid,b.discount,b.pricetype into v_custid,v_discount,v_pricetype from warepeih a join customer b on a.custid=b.custid";
		qry += "\n      where a.accid=" + Accid + " and a.noteno='" + Noteno + "';";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n      v_retcs:='0未找到配货单！';";
		qry += "\n      goto exit;";
		qry += "\n    end;";
		qry += "\n    declare cursor cur_data is ";
		qry += "\n      select a.wareid,a.colorid,a.sizeid,a.amount,";
		qry += "\n      decode(v_pricetype,1,b.sale1,2,b.sale2,3,b.sale3,4,b.sale4,5,b.sale5,b.retailsale) as price0";
		qry += "\n      from v_cxkczy_data a ";
		qry += "\n      join warecode b on a.wareid=b.wareid";
		qry += "\n      where a.epid=" + Userid;
		qry += "\n      and not exists (select 1 from wareouth a1 join wareoutm b1 on a1.accid=b1.accid and a1.noteno=b1.noteno";
		qry += "\n      where a1.accid=" + Accid + " and a.wareid=b1.wareid and a.colorid=b1.colorid and a1.statetag=1";
		qry += "\n      and a1.custid=v_custid and a1.notedate>=to_date('" + Accbegindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss'));";
		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n    begin";
		qry += "\n     v_count:=0;";
		qry += "\n     for v_row in cur_data loop";
		qry += "\n         v_price:=round(v_row.price0*v_discount,2);";
		qry += "\n         v_curr:=round(v_row.amount*v_price,2);";  //saleid:2=首单
		qry += "\n         insert into warepeim (id,accid,noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,curr)";
		qry += "\n         values (warepeim_id.nextval," + Accid + ",'" + Noteno + "',v_row.wareid,v_row.colorid,v_row.sizeid,2,v_row.amount,v_row.price0,v_discount,v_price,v_curr);";
		qry += "\n         v_count:=v_count+1;";
		qry += "\n         if v_count>1000 then";
		qry += "\n            commit;";
		qry += "\n            v_count:=0;";
		qry += "\n         end if;";
		qry += "\n     end loop;";
		qry += "\n   end;";
		qry += "\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from warepeim where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   update warepeih set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   commit;";
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
