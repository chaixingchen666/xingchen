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
//  @author:sunhong  @date:2017-03-08 20:55:34
//*************************************
public class Allotinh implements java.io.Serializable {
	private Float Id;
	private String Noteno;
	private Long Accid;
	private String Notedatestr = "";
	private Long Houseid;
	private Long Fromhouseid;
	private String Noteno1;
	private Integer Ntid;
	private String Remark;
	private String Handno;
	private Float Totalamt;
	private Float Totalcurr;
	private String Operant;
	private String Checkman;
	private Integer Statetag;
	private Date Lastdate;
	private Integer Ywly;
	private String errmess;
	private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	private String Calcdate = "";

	public void setCalcdate(String calcdate) {
		this.Calcdate = calcdate;
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

	// public void setNotedate(Date notedate) {
	// this.Notedate = notedate;
	// }
	//
	// public String getNotedate() {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// return df.format(Notedate);
	// }

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public void setFromhouseid(Long fromhouseid) {
		this.Fromhouseid = fromhouseid;
	}

	public Long getFromhouseid() {
		return Fromhouseid;
	}

	public void setNoteno1(String noteno1) {
		this.Noteno1 = noteno1;
	}

	public String getNoteno1() {
		return Noteno1;
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

	public void setYwly(Integer ywly) {
		this.Ywly = ywly;
	}

	public Integer getYwly() {
		return Ywly;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	public void setAccbegindate(String accbegindate) {
		this.Accbegindate = accbegindate;
	}

	// 删除记录
	public int Delete() {
		if (Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n  v_operant varchar2(20); ");
		strSql.append("\n  v_noteno1 varchar2(20); ");
		strSql.append("\n  v_statetag number(1); ");
		strSql.append("\n begin ");
		strSql.append(
				"\n   select noteno1,statetag,operant into v_noteno1,v_statetag,v_operant from allotinh where accid="
						+ Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n   if v_statetag>0 then ");
		strSql.append("\n      v_retcs:= '0当前单据已提交，不允许删除！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if;");
		strSql.append("\n   if v_operant<>'" + Operant + "' then ");
		strSql.append("\n      v_retcs:= '0不允许删除其他用户的单据！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if;");

//		strSql.append("\n   delete from allotinm ");
//		strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "'");
//		strSql.append(
//				"\n   and exists (select 1 from allotinh a where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
//		strSql.append("\n   and a.accid=allotinm.accid and a.noteno=allotinm.noteno and a.statetag=0);");
//		strSql.append("\n   delete from allotinh ");
//		strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0; ");
		strSql.append("\n   update allotinh set statetag=2,lastdate=sysdate");
		strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0; ");
		// 将对应的调拨出库单的调入单置为空
		strSql.append("\n   if length(v_noteno1)>0 then");
		strSql.append("\n      update allotouth set noteno1='' where accid=" + Accid + " and noteno=v_noteno1; ");
		strSql.append("\n   end if;");
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
	public int Append(String fromnoteno, int fs) {
		if (fromnoteno.length() <= 0) {
			errmess = "\"msg\":\"调出单不是一个有效值！\"";
			return 0;
		}
		String qry = "declare ";
		qry += "\n   v_accid  number;";
		qry += "\n   v_operant nvarchar2(10);";
		qry += "\n   v_noteno nvarchar2(20);";
		qry += "\n   v_noteno1 nvarchar2(20);";
		qry += "\n   v_fromnoteno nvarchar2(20);";
		qry += "\n   v_houseid  number(10);";
		qry += "\n   v_housename nvarchar2(40);";
		qry += "\n   v_fromhousename nvarchar2(40);";
		qry += "\n   v_fromhouseid number(10);";
		qry += "\n   v_totalamt number(16,2);";
		qry += "\n   v_totalcurr number(16,2);";
		qry += "\n   v_allotouthid number;";
		qry += "\n   v_id  number;";
		qry += "\n   v_count  number;";
		qry += "\n   v_retcs nvarchar2(200);";
		qry += "\n   v_retbj  number(1);";
		qry += "\n begin ";
		// qry += " v_accid:=" + accid + ";";
		// qry += " v_fromnoteno:='" + fromnoteno + "';";
		// qry += " v_operant:='" + lastop + "';";
		qry += "\n   begin ";
		qry += "\n     select a.houseid,a.tohouseid,a.totalamt,a.totalcurr,b.housename,c.housename,a.id,a.noteno1 ";
		qry += "\n     into v_fromhouseid,v_houseid,v_totalamt,v_totalcurr,v_fromhousename,v_housename,v_allotouthid,v_noteno1 ";
		qry += "\n     from allotouth a ";
		qry += "\n     left outer join warehouse b on a.houseid=b.houseid";
		qry += "\n     left outer join warehouse c on a.tohouseid=c.houseid";
		qry += "\n     where a.accid=" + Accid + " and a.noteno='" + fromnoteno + "' and a.statetag=1";
		// qry += "\n and not exists (select 1 from allotinh a1 where
		// a.accid=a1.accid and a.noteno=a1.noteno1 and a1.statetag<=1);";
		qry += "\n     ;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN  ";
		qry += "\n     v_retbj:=0;  v_retcs:='\"msg\":\"未找到调出单 '||'" + fromnoteno + "！'||'\"';";
		qry += "\n     goto exit;";
		qry += "\n   end;";
		qry += "\n   if length(v_noteno1)>0 then";
		qry += "\n      v_retbj:=0; v_retcs:='\"msg\":\"调出单 '||'" + fromnoteno + "已入库！'||'\"';";
		qry += "\n      goto exit; ";
		qry += "\n   end if;";
		qry += "\n   begin ";
		qry += "\n     select 'DR'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from ALLOTINH";
		qry += "\n     where accid=" + Accid
				+ "  and LENGTH(noteno)=15 and substr(noteno,1,10)= 'DR'||To_Char(SYSDATE,'yyyymmdd');";

		qry += "\n     v_id:=ALLOTINH_ID.NEXTVAL;";

		qry += "\n     insert into ALLOTINH (id, accid,noteno,notedate,noteno1,houseid,fromhouseid,ntid,totalamt,totalcurr,remark,handno,operant,checkman,lastdate,statetag)";
		qry += "\n     values (v_id," + Accid + ",v_noteno,sysdate,'" + fromnoteno
				+ "',v_houseid,v_fromhouseid,0,v_totalamt,v_totalcurr,'','','" + Operant + "','',sysdate";
		if (fs == 1) // 调拨出库单转调拨入库单直接提交
			qry += "\n     ,1);";
		else
			qry += "\n     ,0);";

		// qry += "\n insert into allotinm
		// (id,accid,noteno,wareid,colorid,sizeid,amount,price,curr,remark0,lastdate)";
		// qry += "\n select allotinm_id.nextval," + Accid +
		// ",v_noteno,wareid,colorid,sizeid,amount,price,curr,remark0,sysdate
		// from allotoutm";
		// qry += "\n where accid=" + Accid + " and noteno='" + fromnoteno +
		// "';";
		qry += "\n   update allotouth set noteno1=v_noteno,lastdate=sysdate where id=v_allotouthid and accid=" + Accid
				+ ";";
		// qry += "\n commit;";
		qry += "\n   v_count:=0;";
		qry += "\n   declare cursor cur_data is";
		qry += "\n     select wareid,colorid,sizeid,amount,price,curr,remark0,sysdate from allotoutm";
		qry += "\n       where accid=" + Accid + " and noteno='" + fromnoteno + "';";
		qry += "\n       v_row cur_data%rowtype; ";
		qry += "\n    begin ";
		qry += "\n      for v_row in cur_data loop ";
		qry += "\n         insert into allotinm (id,accid,noteno,wareid,colorid,sizeid,amount,price,curr,remark0,lastdate)  ";
		qry += "\n         values (allotinm_id.nextval," + Accid
				+ ",v_noteno,v_row.wareid,v_row.colorid,v_row.sizeid,v_row.amount,v_row.price,v_row.curr,v_row.remark0,sysdate);  ";
		qry += "\n         v_count:=v_count+1;";
		qry += "\n         if v_count>500 then";
		qry += "\n            commit;";
		qry += "\n            v_count:=0;";
		qry += "\n         end if;";
		qry += "\n      end loop; ";
		qry += "\n    end; ";
		qry += "\n    commit;";

		qry += "\n     v_retbj:=1;  select '\"msg\":\"操作成功\",\"ID\":\"'||id||'\",\"NOTENO\":\"'||noteno||'\",\"NOTEDATE\":\"'||to_char(notedate,'yyyy-mm-dd hh24:mi:ss')||'\"'";
		qry += "||',\"HOUSEID\":\"'||v_houseid||'\",\"HOUSENAME\":\"'||v_housename||'\",\"FROMHOUSEID\":\"'||v_fromhouseid||'\",\"FROMHOUSENAME\":\"'||v_fromhousename||'\"'";
		qry += "      into v_retcs from allotinh where id=v_id;  ";
		qry += "\n    insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n    values (LOGRECORD_id.nextval," + Accid + ",'调拨入库','【新增单据】单据号:'||v_noteno||',调出单号:" + fromnoteno
				+ "','" + Operant + "');";
		qry += "\n    exception";
		qry += "\n    when others then ";
		qry += "\n    begin";
		qry += "\n      rollback;";
		qry += "\n      v_retbj:=0;  v_retcs:='\"msg\":\"操作失败，请重试！\"';";
		qry += "\n    end;";
		qry += "\n  end;";

		qry += "\n  <<exit>>";

		qry += "\n  select v_retbj,v_retcs into :retbj,:retcs from dual;";
		qry += "\n end;";
		// LogUtils.LogDebugWrite("addallotinh", qry);
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
	public int Update(int changedatebj) {
		if (Noteno == null || Noteno.length() <= 0) {
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
		strSql.append("declare ");
		strSql.append("\n     v_totalcurr number;");
		strSql.append("\n     v_totalamt number;");
		strSql.append("\n     v_notedate date; ");
		strSql.append("\n begin ");

		if (!Func.isNull(Notedatestr)) {
			strSql.append("\n   v_notedate:=to_date('" + Notedatestr + "','yyyy-mm-dd hh24:mi:ss');");
			strSql.append("\n   if to_char(v_notedate,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')  or v_notedate>sysdate then ");
			strSql.append("\n      v_notedate:=sysdate;");
			strSql.append("\n   end if;");
		} else {
			strSql.append("\n   v_notedate:=sysdate;");
		}

		strSql.append("   update allotinh set ");

		strSql.append("notedate=v_notedate,");

		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
		}
		if (Fromhouseid != null) {
			strSql.append("fromhouseid=" + Fromhouseid + ",");
		}
		if (Noteno1 != null) {
			strSql.append("noteno1='" + Noteno1 + "',");
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
		// strSql.append("operant='" + Operant + "',");
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
		strSql.append("lastdate=sysdate");
		strSql.append("\n  where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n  commit;");

		if (Statetag != null && Statetag == 1)// 提交时刷新当前单据的总数量与总金额
		{
			strSql.append("\n  select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from allotinm");
			strSql.append("\n  where accid=" + Accid + " and noteno='" + Noteno + "';");

			strSql.append("\n  update allotinh set totalcurr=v_totalcurr,totalamt=v_totalamt");
			strSql.append("\n  where accid=" + Accid + " and noteno='" + Noteno + "' ;");
			// if (changedatebj == 1) {// 如果提交且允许更改单据日期，要判断是否更改账套结账日期
			// strSql.append("\n update accreg set calcdate=trunc(v_notedate-1)
			// where accid=" + Accid + " and calcdate>=trunc(v_notedate);");
			// }
		}

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

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist + ",C.HOUSENAME AS FROMHOUSENAME");
		// strSql.append(",case To_Char(a.notedate,'yyyy-mm-dd') when
		// To_Char(SYSDATE,'yyyy-mm-dd') then '1' else '0' end as istoday");
		strSql.append(" FROM allotinh a");
		strSql.append(" left outer join warehouse b on a.houseid=b.houseid");
		strSql.append(" left outer join warehouse c on a.fromhouseid=c.houseid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist + ",C.HOUSENAME AS FROMHOUSENAME");
		strSql.append(" FROM allotinh a");
		strSql.append(" left outer join warehouse b on a.houseid=b.houseid");
		strSql.append(" left outer join warehouse c on a.fromhouseid=c.houseid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from allotinh");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1
		// and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return true;
		}
		return false;
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
		qry += "\n     select to_char(notedate,'yyyy-mm-dd'),noteno1,checkman ";
		qry += "\n     into v_notedate,v_noteno1,v_checkman";
		qry += "\n     from allotinh where accid=" + Accid + "  and noteno='" + Noteno + "' and statetag=1; ";
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

		qry += "\n  update allotinh set statetag=0 where accid=" + Accid + "  and noteno='" + Noteno + "'; ";
		// qry += "\n if length(v_noteno1)>0 then "; // 将对应的调拨出库单的调入单号清空
		// qry += "\n update allotouth set noteno1='' where accid=" + Accid + "
		// and noteno=v_noteno1; ";
		// qry += "\n end if; ";

		qry += "\n   v_retcs:= '1操作成功！';  ";

		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'调拨入库','【撤单】单据号:" + Noteno + "','" + Operant + "');";
		qry += "\n   commit;";
		qry += "\n   if length(v_orderno)>0 then";// 如果出库单关联了订单，计算订单完成情况
		qry += "\n      p_calcallotorderover(" + Accid + ",v_orderno,0);"; // 计算订单完成情况
		qry += "\n   end if;";
		// if (changedatebj == 1) // 如果允许更改单据日期， 撤单要更改账套登账日期
		// qry += "\n update accreg set
		// calcdate=trunc(to_date(v_notedate,'yyyy-mm-dd')-1) where accid=" +
		// Accid + " and calcdate>=to_date(v_notedate,'yyyy-mm-dd');";
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

	// 审核/反审
	public int Check(int bj) {
		String qry = "declare";
		qry += "\n   v_count number;";
		qry += "\n   v_checkman varchar2(20);";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_mess varchar2(200);";
		qry += "\n begin";
		qry += "\n   select checkman into v_checkman from allotinh where accid=" + Accid + " and noteno='" + Noteno
				+ "';";
		if (bj == 1) {// 审核
			qry += "\n   if length(v_checkman)>0 then ";
			qry += "\n      v_retcs:='0单据已审核！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update allotinh set checkman='" + Operant + "' where accid=" + Accid + " and noteno='" + Noteno
					+ "';";
			qry += "\n   v_mess:='【取消审核】';";

		} else {// 取消审核
			qry += "\n   if v_checkman<>'" + Operant + "' then ";
			qry += "\n      v_retcs:='0系统不允许取消其他用户审核的单据！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update allotinh set checkman='' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【审核】';";
		}
		qry += "\n   v_retcs:='1操作成功！';";
		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'调拨入库',v_mess||'单据号:" + Noteno + "','" + Operant
				+ "');";
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
		if (Fromhouseid != null) {
			strSql1.append("Fromhouseid,");
			strSql2.append(Fromhouseid + ",");
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

		strSql.append("\n     select 'DR'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from allotinh");
		strSql.append("\n     where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'DR'||To_Char(SYSDATE,'yyyymmdd');");

		strSql.append("\n     v_id:=allotinh_id.nextval; ");
		strSql.append("\n     insert into allotinh (" + strSql1.toString() + ")");
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
			strSql.append("\n  insert into allotinm (id,accid,noteno,wareid,colorid,sizeid,amount,price,curr,remark0)");
			strSql.append("\n  values (allotinm_id.nextval," + Accid + ",v_noteno," + wareid + "," + colorid + "," + sizeid + "," + amount //
					+ "," + price + "," + curr + ",'" + remark0 + "');");
		}

		strSql.append("\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from allotinm where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   update allotinh set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno=v_noteno;");
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