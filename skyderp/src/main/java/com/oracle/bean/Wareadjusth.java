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
//  @author:sunhong  @date:2017-04-10 17:11:17
//*************************************
public class Wareadjusth implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private String Noteno;
	private Date Notedate;
	private String Handno;
	private Integer Statetag;
	private String Begindate;
	private String Enddate;
	private String Remark;
	private String Operant;
	private String Checkman;
	private Date Lastdate;
	private String Checkremark;
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

	public void setHandno(String handno) {
		this.Handno = handno;
	}

	public String getHandno() {
		return Handno;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setBegindate(String begindate) {
		this.Begindate = begindate;
	}

	public String getBegindate() {
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// return df.format(Begindate);
		return Begindate;
	}

	public void setEnddate(String enddate) {
		this.Enddate = enddate;
	}

	public String getEnddate() {
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// return df.format(Enddate);
		return Enddate;
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

	public void setCheckremark(String checkremark) {
		this.Checkremark = checkremark;
	}

	public String getCheckremark() {
		return Checkremark;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Func.isNull(Noteno)) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		// strSql.append(" delete from wareadjustm ");
		// strSql.append(" where accid=" + accid + " and noteno='" + noteno + "'");
		// strSql.append(" and exists (select 1 from wareadjusth a where a.id=" + id);
		// strSql.append(" and a.accid=wareadjustm.accid and a.noteno=wareadjustm.noteno);");
		//
		// strSql.append(" delete from wareadjustc ");
		// strSql.append(" where accid=" + accid + " and noteno='" + noteno + "'");
		// strSql.append(" and exists (select 1 from wareadjusth a where a.id=" + id);
		// strSql.append(" and a.accid=wareadjustc.accid and a.noteno=wareadjustc.noteno);");
		//
		// strSql.append(" delete from wareadjusth ");
		// strSql.Append(" where id=" + id + " and accid=" + accid + " and noteno='" + noteno + "'; ");
		strSql.append("  update wareadjusth set statetag=4 ");
		strSql.append("  where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0; ");

		strSql.append("end; ");
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append(long houseid) {
		String qry = "declare ";

		qry += "\n    v_accid  number;";
		qry += "\n    v_operant nvarchar2(10);";
		qry += "\n    v_noteno nvarchar2(20);";
		// qry += "\n v_houseid number;";
		// qry += "\n v_provid number;";
		qry += "\n   v_id  number;";
		qry += "\n   v_retcs nvarchar2(200);";
		qry += "\n   v_retbj number(1); ";
		qry += "\n begin ";
		qry += "\n   begin ";
		qry += "\n     select 'TJ'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareadjusth";
		qry += "\n     where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'TJ'||To_Char(SYSDATE,'yyyymmdd');";
		qry += "\n     v_id:=wareadjusth_ID.NEXTVAL;";
		qry += "\n     insert into wareadjusth (id, accid,noteno,notedate,begindate,enddate,statetag,remark,handno,operant,checkman,lastdate)";
		qry += "\n     values (v_id," + Accid + ",v_noteno,sysdate,trunc(sysdate),trunc(sysdate)+7,0,'','','" + Operant + "','',sysdate);";
		if (houseid > 0) {
			qry += "\n   insert into wareadjustc (id,accid,noteno,houseid) values (wareadjustc_id.nextval," + Accid + ",v_noteno," + houseid + ");";
		}
		qry += "\n     commit;";
		qry += "\n     v_retbj:=1; select '\"msg\":\"操作成功\",\"ID\":\"'||id||'\",\"NOTENO\":\"'||noteno||'\",\"NOTEDATE\":\"'||to_char(notedate,'yyyy-mm-dd hh24:mi:ss')||'\"' into v_retcs from wareadjusth where id=v_id;  ";
		qry += "\n    exception";
		qry += "\n    when others then ";
		qry += "\n    begin";
		qry += "\n      rollback;";
		qry += "\n      v_retbj:=0;  v_retcs:='\"msg\":\"操作失败，请重试！\"' ;";
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
	public int Update() {
		if (Func.isNull(Noteno)) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		Noteno = Noteno.toUpperCase();
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Wareadjusth set ");
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
		if (Statetag != null && Statetag != 2 && Statetag!=3) {

			strSql.append("notedate=sysdate,");
		}
		if (Handno != null) {
			strSql.append("handno='" + Handno.toUpperCase() + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Begindate != null) {
			strSql.append("begindate=to_date('" + Begindate + "','yyyy-mm-dd'),");
		}
		if (Enddate != null) {
			strSql.append("enddate=to_date('" + Enddate + "','yyyy-mm-dd'),");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		// if (Operant != null) {
		strSql.append("operant='" + Operant + "',");
		// }
		if (Checkman != null) {
			strSql.append("checkman='" + Checkman + "',");
		}
		if (Checkremark != null) {
			strSql.append("checkremark='" + Checkremark + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where accid=" + Accid + " and noteno='" + Noteno + "';");
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
		strSql.append(",0 as istoday");
		strSql.append(",f_getwareadjusthouselist(a.accid,a.noteno) as housenamelist");
		strSql.append(" FROM Wareadjusth a");

		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Wareadjusth a");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		qp.setCalcfield("f_getwareadjusthouselist(accid,noteno) as housenamelist");
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp) {
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Wareadjusth");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 调价单审核及取消审核
	public int Check(int opid) {
		if (Func.isNull(Noteno)) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		// Noteno = Noteno.toUpperCase();
		String qry = "declare ";
		qry += "\n   v_count number;";
		qry += "\n   v_statetag number(1);";
		qry += "\n   v_operant varchar2(20);";
		qry += "\n   v_checkman varchar2(20);";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n begin";
		qry += "\n   begin";
		qry += "\n     select statetag,operant,checkman into v_statetag,v_operant,v_checkman  from wareadjusth where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_retcs:= '0未找到调价单！' ; ";
		qry += "\n     goto exit;";
		qry += "\n   end;";

		if (opid == 1) // 审核
		{
			qry += "\n   if v_statetag=0 then";
			qry += "\n      v_retcs:= '0当前调价单未提交！' ; ";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			// 更新商品店铺价格
			if (Accid == 1310 || Accid == 1282) // 上下衣
			{
				qry += "\n   declare cursor cur_data is";
				qry += "\n      select b.houseid,a.wareid,a.retailsale";
				qry += "\n      from wareadjustm a join wareadjustc b on a.accid=b.accid and a.noteno=b.noteno";
				qry += "\n      where a.accid=" + Accid + " and a.noteno='" + Noteno + "';";
				qry += "\n      v_row cur_data%rowtype; ";
				qry += "\n   begin";
				qry += "\n     for v_row in cur_data loop ";
				qry += "\n         select count(*) into v_count from  housesaleprice where  houseid=v_row.houseid and wareid=v_row.wareid;";
				qry += "\n         if v_count=0 then";
				qry += "\n            insert into housesaleprice (houseid,wareid,retailsale,retailsale0,lastop)";
				qry += "\n            values (v_row.houseid,v_row.wareid,v_row.retailsale,v_row.retailsale,'" + Operant + "');";
				qry += "\n         else ";
				qry += "\n            update housesaleprice set retailsale=v_row.retailsale where houseid=v_row.houseid and wareid=v_row.wareid;";
				qry += "\n         end if;";
				qry += "\n     end loop; ";
				qry += "\n   end;";
			}

			qry += "\n   update wareadjusth set statetag=2,checkman='" + Operant + "' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
			qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'调价单','【审核】单据号:" + Noteno + "' ,'" + Operant + "');";
		} else // 取消审核
		if (opid == 0) {

			qry += "\n   if v_statetag<>2 then";
			qry += "\n      v_retcs:= '0当前单据没有审核！' ; ";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   if v_checkman<>'" + Operant + "' then";
			qry += "\n      v_retcs:= '0不允许取消其他用户审核的单据！'; ";
			qry += "\n      goto exit;";
			qry += "\n   end if;";

			qry += "\n   update wareadjusth set statetag=1,checkman='' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
			qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'调价单','【审核取消】单据号:" + Noteno + "' ,'" + Operant + "');";
		} else {
			if (Func.isNull(Checkremark)) {
				errmess = "请输入审核驳回原因！";
				return 0;
			}

			qry += "\n   if v_statetag<>1 then";
			qry += "\n      v_retcs:= '0当前单据没有提交！' ; ";
			qry += "\n      goto exit;";
			qry += "\n   end if;";

			qry += "\n   update wareadjusth set statetag=0,checkman='',checkremark='" + Checkremark + "' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
			qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'调价单','【审核驳回】单据号:" + Noteno + "' ,'" + Operant + "');";

		}
		qry += "\n   v_retcs:= '1操作成功！'; ";
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
		qry += "\n   v_count number(10); ";
		qry += "\n begin ";
		qry += "\n   begin";
		qry += "\n     select to_char(notedate,'yyyy-mm-dd') ";
		qry += "\n     into v_notedate";
		qry += "\n     from wareadjusth where accid=" + Accid + "  and noteno='" + Noteno + "' and statetag=1; ";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_retcs:='0未找到单据!';";
		qry += "\n     goto exit;";
		qry += "\n   end;";

//		if (changedatebj == 0) // 不允许更改单据日期
//		{
//			qry += "\n   if v_notedate<to_char(sysdate,'yyyy-mm-dd') then ";
//			qry += "\n      v_retcs:= '0今日之前的单据不允许撤单！'; ";
//			qry += "\n      goto exit; ";
//			qry += "\n   end if; ";
//		}
		qry += "\n   update wareadjusth set statetag=0 where accid=" + Accid + "  and noteno='" + Noteno + "' ; ";

		qry += "\n   v_retcs:= '1操作成功！';  ";

		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'商品调价','【撤单】单据号:" + Noteno + "','" + Operant + "');";
		qry += "\n   commit;";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";
		//System.out.println(qry);

//		LogUtils.LogDebugWrite("调拨订单撤单", qry);
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