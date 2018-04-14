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
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-05 21:59:43
//*************************************
public class Dealrecord implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private Long Vipid;
	private String Noteno;
	private Date Notedate;
	private Integer Payfs;
	private String Ywnoteno;
	private Integer Ywtag;
	private Float Curr;
	private String Remark;
	private String Failedremark;
	private Integer Statetag;
	private String Outtradeno;
	private String Payidstr;
	private String Payaccno;
	private Date Lastdate;
	private String Lastop;
	private String Checkman;
	private Float Totalcurr;
	private Integer Payfs1;
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

	public void setPayfs(Integer payfs) {
		this.Payfs = payfs;
	}

	public Integer getPayfs() {
		return Payfs;
	}

	public void setYwnoteno(String ywnoteno) {
		this.Ywnoteno = ywnoteno;
	}

	public String getYwnoteno() {
		return Ywnoteno;
	}

	public void setYwtag(Integer ywtag) {
		this.Ywtag = ywtag;
	}

	public Integer getYwtag() {
		return Ywtag;
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

	public void setFailedremark(String failedremark) {
		this.Failedremark = failedremark;
	}

	public String getFailedremark() {
		return Failedremark;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setOuttradeno(String outtradeno) {
		this.Outtradeno = outtradeno;
	}

	public String getOuttradeno() {
		return Outtradeno;
	}

	public void setPayidstr(String payidstr) {
		this.Payidstr = payidstr;
	}

	public String getPayidstr() {
		return Payidstr;
	}

	public void setPayaccno(String payaccno) {
		this.Payaccno = payaccno;
	}

	public String getPayaccno() {
		return Payaccno;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setCheckman(String checkman) {
		this.Checkman = checkman;
	}

	public String getCheckman() {
		return Checkman;
	}

	public void setTotalcurr(Float totalcurr) {
		this.Totalcurr = totalcurr;
	}

	public Float getTotalcurr() {
		return Totalcurr;
	}

	public void setPayfs1(Integer payfs1) {
		this.Payfs1 = payfs1;
	}

	public Integer getPayfs1() {
		return Payfs1;
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
		strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and
		// rownum=1 and statetag=1");
		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前记录已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		strSql.append(" update Dealrecord set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	// 增加记录
	// public int Append(long vipid) {
	public int Append() {
		if (Ywnoteno == null || Ywnoteno.equals("")) {
			errmess = "业务单号无效！";
			return 0;
		}

		if (Curr == 0) {
			errmess = "交易金额无效！";
			return 0;
		}

		if (Payfs == null)
			Payfs = 0;
		if (Payfs1 == null)
			Payfs1 = 0;
		if (Ywtag == null)
			Ywtag = 0;
		// ywtag:0=店铺零售，1=商场零售，2=线上，3=批发销售

		// payfs:0=支付宝，1=微信,2=拉卡拉,3=盛付通

		// 当payfs=2拉卡拉时有效,1=银行卡，2=扫码
		// 当payfs=2拉卡拉,payfs1:1=银行卡，2=扫码
		// 当payfs=3盛付通,payfs1:1=银行卡，2=微信，3=支付宝
		if (Remark == null)
			Remark = "";
		if (Outtradeno == null)
			Outtradeno = "";

		// String payfieldname = "";
		// if (Payfs == 0)
		// payfieldname = "paynoteno_zfb";
		// else if (Payfs == 1)
		// payfieldname = "paynoteno_wx";
		// else if (Payfs == 2)
		// payfieldname = "paynoteno_lkl";
		String qry = "declare";
		qry += "\n    v_noteno varchar2(20);";
		qry += "\n    v_paynoteno varchar2(20);";
		qry += "\n    v_retbj number(1);";
		qry += "\n begin";
		qry += "\n   begin";
		qry += "\n    select 'ZF'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from DEALRECORD";
		qry += "\n    where accid=" + Accid + "  and LENGTH(noteno)=15 and substr(noteno,1,10)= 'ZF'||To_Char(SYSDATE,'yyyymmdd');";
		qry += "\n    insert into DEALRECORD (id, accid,noteno,outtradeno,notedate,payfs,payfs1,curr,totalcurr,remark,ywtag,ywnoteno,statetag,checkman,lastop,lastdate)";
		qry += "\n    values (DEALRECORD_ID.NEXTVAL," + Accid + ",v_noteno,'" + Outtradeno + "',sysdate," + Payfs + "," + Payfs1 + "," + Curr + "," + Totalcurr //
				+ ",'" + Remark + "'," + Ywtag + ",'" + Ywnoteno + "',0,'','" + Lastop + "',sysdate);";
		// ywtag:0=店铺零售，1=商场零售，2=线上,3=批发销售
		// if (Ywtag == 0 || Ywtag == 3)
		// qry += "\n update wareouth set " + payfieldname + "=v_noteno where
		// accid=" + Accid + " and noteno='" + Ywnoteno + "';";
		// else if (Ywtag == 1)
		// qry += "\n update shopsaleh set " + payfieldname + "=v_noteno where
		// accid=" + Accid + " and noteno='" + Ywnoteno + "';";
		// else if (Ywtag == 2) {
		// if (Vipid == null)
		// Vipid = (long) 0;
		// qry += "\n update viporderh set " + payfieldname + "=v_noteno where
		// vipid=" + Vipid + " and noteno='" + Ywnoteno + "';";
		// }

		qry += "\n    v_retbj:=1; ";
		qry += "\n    commit;";
		qry += "\n    goto exit;";
		qry += "\n   exception";
		qry += "\n   when others then ";
		qry += "\n   begin";
		qry += "\n     rollback;";
		qry += "\n     v_retbj:=0;v_noteno:='';";
		qry += "\n   end;";

		qry += "\n  end;";
		qry += "\n  <<exit>>";
		qry += "\n  select v_retbj,v_noteno into :retbj,:noteno from dual;";
		qry += "\n end;";

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retbj", new ProdParam(Types.INTEGER));
		param.put("noteno", new ProdParam(Types.VARCHAR));

		// int ret = ;
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		Noteno = param.get("noteno").getParamvalue().toString();
		errmess = "\"msg\":\"操作成功！\",\"noteno\":\"" + Noteno + "\"";
		// 取支付key
		// payfs:0=支付宝，1=微信
		String payfsname = "";
		// payfs:0=支付宝，1=微信,2=拉卡拉,3=盛付通
		if (Payfs == 0)
			payfsname = "pay_zfb";
		else if (Payfs == 1)
			payfsname = "pay_wx";
		else if (Payfs == 2)
			payfsname = "pay_lkl";
		else if (Payfs == 3)
			payfsname = "pay_sft";
		qry = "select usymbol,uvalue from UParameter where accid=" + Accid + " and usection='" + payfsname + "' order by id";
		Table tb = DbHelperSQL.Query(qry).getTable(1);
		// string fh = "";
		// int rs = dt.Rows.Count;
		errmess += ",\"" + payfsname + "\":[{";
		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				errmess += ",";
			errmess += "\"" + tb.getRow(i).get("USYMBOL").toString() + "\":\"" + tb.getRow(i).get("UVALUE").toString() + "\"";
			// fh = ",";
		}
		errmess += "}]";

		return 1;
	}

	// 修改记录
	public int Update(long vipid) {

		if (Noteno == null || Noteno.equals("")) {
			errmess = "交易流水号无效！";
			return 0;
		}
		if (Statetag == null || Statetag < 1 || Statetag > 2) {
			errmess = "状态参数无效:statetag";
			return 0;
		}
		if (Statetag == 1 && (Payaccno == null || Payaccno.equals("") || Payidstr == null || Payidstr.equals(""))) {
			errmess = "支付流水号或支付账号无效！";
			return 0;
		}

		String qry = "declare";
		qry += "\n   v_count number;";
		qry += "\n   v_ywnoteno varchar2(20);";
		qry += "\n   v_ywtag number(1);";
		qry += "\n   v_payfs number(1);";
		qry += "\n begin";
		qry += "\n    select ywnoteno,ywtag,payfs into v_ywnoteno,v_ywtag,v_payfs from DEALRECORD where accid=" + Accid + " and  noteno='" + Noteno + "' and statetag=0;";
		qry += "\n    update DEALRECORD set lastdate=sysdate,statetag=" + Statetag;
		if (Statetag == 1) // 交易成功
		{
			qry += "\n   ,payaccno='" + Payaccno + "',payidstr='" + Payidstr + "',payfs1=" + Payfs1;
			qry += "\n    where accid=" + Accid + " and noteno='" + Noteno + "';";
		} else// 交易失效
		{
			if (Failedremark == null) {
				// errmess = "交易失败原因不能为空！";
				Failedremark = "";
				// return 0;
			}
			qry += "\n   ,failedremark='" + Failedremark + "'";
			qry += "\n    where accid=" + Accid + " and noteno='" + Noteno + "';";

			// payfs:0=支付宝，1=微信,2=拉卡拉
			// ywtag:0=店铺零售，1=商场零售，2=线上,3=批发销售
			// qry += "\n if v_payfs=0 then";
			// qry += "\n if v_ywtag=0 or v_ywtag=3 then ";
			// qry += "\n update wareouth set paynoteno_zfb='' where accid=" +
			// Accid + " and noteno=v_ywnoteno;";
			// qry += "\n elsif v_ywtag=1 then ";
			// qry += "\n update shopsaleh set paynoteno_zfb='' where accid=" +
			// Accid + " and noteno=v_ywnoteno;";
			// qry += "\n else";
			// qry += "\n update viporderh set paynoteno_zfb='' where vipid=" +
			// vipid + " and noteno=v_ywnoteno;";
			// qry += "\n end if;";
			// qry += "\n elsif v_payfs=1 then";
			// qry += "\n if v_ywtag=0 or v_ywtag=3 then ";
			// qry += "\n update wareouth set paynoteno_wx='' where accid=" +
			// Accid + " and noteno=v_ywnoteno;";
			// qry += "\n elsif v_ywtag=1 then ";
			// qry += "\n update shopsaleh set paynoteno_wx='' where accid=" +
			// Accid + " and noteno=v_ywnoteno;";
			// qry += "\n else";
			// qry += "\n update viporderh set paynoteno_wx='' where vipid=" +
			// vipid + " and noteno=v_ywnoteno;";
			// qry += "\n end if;";
			// qry += "\n elsif v_payfs=2 then";
			// qry += "\n if v_ywtag=0 or v_ywtag=3 then ";
			// qry += "\n update wareouth set paynoteno_lkl='' where accid=" +
			// Accid + " and noteno=v_ywnoteno;";
			// qry += "\n elsif v_ywtag=1 then ";
			// qry += "\n update shopsaleh set paynoteno_lkl='' where accid=" +
			// Accid + " and noteno=v_ywnoteno;";
			// qry += "\n end if;";
			// qry += "\n end if;";
		}
		qry += " end;";
		int ret = DbHelperSQL.ExecuteSql(qry);
		if (ret < 0) {
			errmess = "操作失败！";
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
		strSql.append(" FROM Dealrecord ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM Dealrecord a");
		strSql.append("\n left outer join accreg b on a.accid=b.accid");
		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Dealrecord");
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

	public int doGetDealrecordbyno(long vipid) {
		if (Func.isNull(Noteno)) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}

		// String paynofilename = "";
		// if (Payfs == 0)
		// paynofilename = "paynoteno_zfb";
		// else if (Payfs == 1)
		// paynofilename = "paynoteno_wx";
		// else if (Payfs == 2)
		// paynofilename = "paynoteno_lkl";
		//
		// String qry = "";
		// if (Ywtag == 0 || Ywtag == 3) // 0=店铺零售
		// {
		// qry = "select
		// b.noteno,b.ywnoteno,b.statetag,b.curr,b.outtradeno,b.PAYIDSTR,b.payaccno,b.payfs,b.payfs1";
		// qry += " from wareouth a join DEALRECORD b on a.accid=b.accid and a."
		// + paynofilename + "=b.noteno";
		// qry += " where a.accid=" + Accid + " and a.noteno='" + Noteno + "'
		// and b.statetag<2";
		// } else if (Ywtag == 1) // 1=商场零售
		// {
		// qry = "select
		// b.noteno,b.ywnoteno,b.statetag,b.curr,b.outtradeno,b.PAYIDSTR,b.payaccno,b.payfs,b.payfs1";
		// qry += " from shopsaleh a join DEALRECORD b on a.accid=b.accid and
		// a." + paynofilename + "=b.noteno";
		// qry += " where a.accid=" + Accid + " and a.noteno='" + Noteno + "'
		// and b.statetag<2";
		// } else if (Ywtag == 2)// 2=线上
		// {
		// // long vipid = dataObj["vipid"] == null ? 0 :
		// long.Parse(dataObj["vipid").toString()()());
		// qry = "select
		// b.noteno,b.ywnoteno,b.statetag,b.curr,b.outtradeno,b.PAYIDSTR,b.payaccno,b.payfs,b.payfs1";
		// qry += " from viporderh a join DEALRECORD b on a.accid=b.accid and
		// a." + paynofilename + "=b.noteno";
		// qry += " where a.vipid=" + vipid + " and a.noteno='" + Noteno + "'
		// and b.statetag<2";
		// }
		String qry = "select a.noteno,a.ywnoteno,a.ywtag,a.statetag,a.curr,a.outtradeno,a.PAYIDSTR,a.payaccno,a.payfs,a.payfs1";
		qry += " from DEALRECORD a ";
		qry += " where a.accid=" + Accid + " and a.ywnoteno='" + Noteno + "' and a.ywtag=" + Ywtag + " and a.statetag<2";
		System.out.println(qry);
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		int rs = tb.getRowCount();
		// if (rs <= 0) {
		// errmess = "未找到第三方交易记录！";
		// return 0;
		// }
		String responsestr = "\"msg\":\"操作成功！\",\"total\":\"" + rs + "\"";
		// if (rs > 0) {
		responsestr += ",\"rows\":[";
		for (int i = 0; i < rs; i++) {
			if (i > 0)
				responsestr += ",";
			responsestr += "{\"noteno\":\"" + tb.getRow(i).get("NOTENO").toString() + "\"" //
					+ ",\"ywnoteno\":\"" + tb.getRow(i).get("YWNOTENO").toString() + "\""//
					+ ",\"ywtag\":\"" + tb.getRow(i).get("YWTAG").toString() + "\""//
					+ ",\"statetag\":\"" + tb.getRow(i).get("STATETAG").toString() + "\"" //
					+ ",\"curr\":\"" + tb.getRow(i).get("CURR").toString() + "\"" //
					+ ",\"payidstr\":\"" + tb.getRow(i).get("PAYIDSTR").toString() + "\""//
					+ ",\"payaccno\":\"" + tb.getRow(i).get("PAYACCNO").toString() + "\"" //
					+ ",\"outtradeno\":\"" + tb.getRow(i).get("OUTTRADENO").toString() + "\"" //
					+ ",\"payfs\":\"" + tb.getRow(i).get("PAYFS").toString() + "\"" //
					+ ",\"payfs1\":\"" + tb.getRow(i).get("PAYFS1").toString() + "\"}";
		}
		responsestr += "]";
		// }

		// Write(YTJR.COMMON.JsonHelper.DataTable2Json(dt, rs));

		// 取支付key
		// payfs:0=支付宝，1=微信,2=拉卡拉，3=盛付通
		String payfsname = "";
		if (Payfs == 0)
			payfsname = "pay_zfb";
		else if (Payfs == 1)
			payfsname = "pay_wx";
		else if (Payfs == 2)
			payfsname = "pay_lkl";
		else if (Payfs == 3)
			payfsname = "pay_sft";
		qry = "select usymbol,uvalue from UParameter where accid=" + Accid + " and usection='" + payfsname + "' order by id";
		tb = DbHelperSQL.Query(qry).getTable(1);
		String fh = "";
		rs = tb.getRowCount();
		responsestr += ",\"" + payfsname + "\":[{";
		for (int i = 0; i < rs; i++) {
			responsestr += fh + "\"" + tb.getRow(i).get("USYMBOL").toString() + "\":\"" + tb.getRow(i).get("UVALUE").toString() + "\"";
			fh = ",";
		}
		responsestr += "}]";
		errmess = responsestr;
		return 1;
	}
}