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

import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-27 23:01:17
//*************************************
public class Refundouth implements java.io.Serializable {
	private Float Id;
	private String Noteno;
	private Long Accid;
	private Date Notedate;
	private Long Custid;
	private String Remark;
	private String Handno;
	private String Notedatestr;
	private Float Totalamt;
	private Float Totalcurr;
	private String Operant;
	private String Checkman;
	private Integer Statetag;
	private Integer Thtag;
	private Date Lastdate;
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

	public void setCustid(Long custid) {
		this.Custid = custid;
	}

	public Long getCustid() {
		return Custid;
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

	public void setThtag(Integer thtag) {
		this.Thtag = thtag;
	}

	public Integer getThtag() {
		return Thtag;
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
		strSql.append("\n   v_operant varchar2(20); ");
		strSql.append("\n   v_statetag number(1); ");
		strSql.append("\n   v_retcs varchar2(200); ");

		strSql.append("\n begin ");
		strSql.append("\n   begin ");
		strSql.append("\n     select statetag,operant into v_statetag,v_operant from refundouth where accid=" + Accid + " and noteno='" + Noteno + "' and statetag<>2 ;");
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN ");
		strSql.append("\n     v_retcs:='0未找到单据！'; ");
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
		strSql.append("\n   update refundouth set statetag=2 where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0;");
		// strSql.append("\n delete from refundoutm ");
		// strSql.append("\n where accid=" + Accid + " and noteno='" + Noteno + "'");
		// strSql.append("\n and exists (select 1 from refundouth a where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
		// strSql.append("\n and a.accid=refundoutm.accid and a.noteno=refundoutm.noteno and a.statetag=0);");
		// strSql.append("\n delete from refundouth ");
		// strSql.append("\n where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0; ");
		strSql.append("\n    v_retcs:='1操作成功！';");
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
		qry += "\n   v_accid  number;";
		qry += "\n   v_operant nvarchar2(10);";
		qry += "\n   v_noteno nvarchar2(20);";
		qry += "\n   v_custid  number;";
		qry += "\n   v_id  number;";
		qry += "\n   v_retcs nvarchar2(100);";
		qry += "\n   v_retbj number(1); ";
		qry += "\n begin ";
		// qry += " v_accid:=" + accid + ";";
		// qry += " v_custid:=" + custid + ";";
		// qry += " v_operant:='" + lastop + "';";
		qry += "\n  begin ";
		qry += "\n    select 'TS'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from refundouth";
		qry += "\n       where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'TS'||To_Char(SYSDATE,'yyyymmdd');";
		qry += "\n    v_id:=refundouth_ID.NEXTVAL;";
		qry += "\n    insert into refundouth (id, accid,noteno,notedate,custid,statetag,totalamt,totalcurr,remark,handno,operant,checkman,lastdate)";
		qry += "\n    values (v_id," + Accid + ",v_noteno,sysdate," + Custid + ",0,0,0,'','','" + Operant + "','',sysdate);";
		qry += "\n    commit;";
		qry += "\n    v_retbj:=1; select '\"msg\":\"操作成功\",\"ID\":\"'||id||'\",\"NOTENO\":\"'||noteno||'\",\"NOTEDATE\":\"'||to_char(notedate,'yyyy-mm-dd hh24:mi:ss')||'\"' into v_retcs from refundouth where id=v_id;  ";
		qry += "\n    exception";
		qry += "\n    when others then ";
		qry += "\n    begin";
		qry += "\n      rollback;";
		qry += "\n      v_retbj:=0; v_retcs:= '\"msg\":\"操作失败，请重试！\"' ;";
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
		errmess = param.get("retcs").getParamvalue().toString();
		return Integer.parseInt(param.get("retbj").getParamvalue().toString());
	}

	// 修改记录
	public int Update(JSONObject jsonObject, int changedatebj) {
		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "单据号无效！";
			return 0;
		}

		if (Statetag != null && Statetag == 1) // 如果是提交，必须转入供应商及店铺，要判断供应商及店铺
		{
			if (Custid == null || Custid == 0) {
				errmess = "custid不是一个有效值！";
				return 0;
			}
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_count number(10);");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n   v_totalcurr number(16,2); ");
		strSql.append("\n   v_totalamt number; ");
		strSql.append("\n   v_noteno varchar2(20); ");
		strSql.append("\n   v_orderno varchar2(20); ");
		strSql.append("\n   v_notedate date; ");
		strSql.append("\n begin ");

		if (Custid != null) {
			strSql.append("\n   select count(*) into v_count from customer where accid=" + Accid + " and custid=" + Custid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0客户无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}

		if (!Func.isNull(Notedatestr)) {
			strSql.append("\n   v_notedate:=to_date('" + Notedatestr + "','yyyy-mm-ddhh24:mi:ss');");
			strSql.append("\n   if to_char(v_notedate,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') then ");
			strSql.append("\n      v_notedate:=sysdate;");
			strSql.append("\n   end if;");
		} else {
			strSql.append("\n   v_notedate:=sysdate;");
		}

		strSql.append("\n   update Refundouth set ");
		strSql.append("notedate=v_notedate,");
		if (Custid != null) {
			strSql.append("custid=" + Custid + ",");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Handno != null) {
			strSql.append("handno='" + Handno + "',");
		}
		if (Totalamt != null) {
			strSql.append("totalamt='" + Totalamt + "',");
		}
		if (Totalcurr != null) {
			strSql.append("totalcurr='" + Totalcurr + "',");
		}
		// if (Operant != null) {
		strSql.append("operant='" + Operant + "',");
		// }
		if (Checkman != null) {
			strSql.append("checkman='" + Checkman + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Thtag != null) {
			strSql.append("thtag=" + Thtag + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where accid=" + Accid + " and noteno='" + Noteno + "';");
		// strSql.append("\n commit;");
		if (Statetag != null && Statetag == 1) // 提交时刷新当前单据的总数量与总金额
		{

			strSql.append("\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from refundoutm ");
			strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "';");

			strSql.append("\n   update refundouth set totalcurr=v_totalcurr,totalamt=v_totalamt");
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
		strSql.append(", 0 as istoday");
		strSql.append("\n FROM refundouth a");
		strSql.append("\n left outer join customer b on a.custid=b.custid");
		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM Refundouth a");
		strSql.append("\n left outer join customer b on a.custid=b.custid");
		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Refundouth");
		return false;
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
		qry += "\n   v_count number(10); ";
		qry += "\n   v_checkman varchar2(20); ";
		qry += "\n begin ";
		qry += "\n   begin";
		qry += "\n     select to_char(notedate,'yyyy-mm-dd'),checkman ";
		qry += "\n     into v_notedate,v_checkman";
		qry += "\n     from refundouth where accid=" + Accid + "  and noteno='" + Noteno + "' and statetag=1; ";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_retcs:='0未找到单据!';";
		qry += "\n     goto exit;";
		qry += "\n   end;";
		qry += "\n   if length(v_checkman)>0 then ";// 如果收款单关联了销售单，重新刷新销售的的结算方式
		qry += "\n      v_retcs:= '0已审核的单据不允许撤单！'; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if; ";
		qry += "\n   select count(*) into v_count from wareouth where accid=" + Accid + " and ntid=2 and orderno='" + Noteno + "'; ";
		qry += "\n   if v_count>0 then ";
		qry += "\n      v_retcs:='0当前退货申请转入退货单，不允许撤单！' ; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if; ";
		qry += "\n   update refundouth set statetag=0 where accid=" + Accid + "  and noteno='" + Noteno + "' ; ";
		qry += "\n   v_retcs:= '1操作成功！';  ";
		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'退货申请货单','【撤单】单据号:" + Noteno + "','" + Operant + "');";
		qry += "\n   commit;";
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

}