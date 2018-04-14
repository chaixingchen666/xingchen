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
import com.common.tool.JGPush;
import com.common.tool.LogUtils;
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

// *************************************
// @author:sunhong @date:2017-03-06 10:18:48
// *************************************
public class Wareouth implements java.io.Serializable {
	private Float Id;
	private String Noteno;
	private Long Accid;
	private Long Userid;
	private String Notedate;
	private Long Custid;
	private Long Guestid;
	private Long Houseid;
	private Integer Ntid;
	private String Handno;
	private String Remark;
	private Float Totalamt;
	private Double Totalcurr;
	private String Operant;
	private String Checkman;
	private String Waybillno;
	private Integer Statetag;
	private String Cashier;
	private String Orderno;
	private Long Rs;
	private Double Checkcurr;
	private Double Changecurr;
	private Double Freecurr;
	private Double Zpaycurr;
	private Double Gdcurr;
	private Integer Ywly;
	private Integer Bj;
	private Long Cent;
	private Double Jfcurr;
	private Long Usecent;
	private Long Dptid;
	private String Paylist;
	private Long Onhouseid;
	private Long Carryid;
	private String Newdatetime;
	private String Wladd;
	private Integer Cleartag;
	private Double Totalcost;
	private Double Paycurr0;

	private Long Tohouseid;
	private Float Messid;
	private Double Yhjcurr;
	private Long Vcpid;
	private Long Buy_accid;
	private String Buy_noteno;
	private Integer Fhtag;
	private String Ywnoteno;
	// private String Fhadd;
	private String Paynoteno_lkl;// 拉卡拉
	private String Paynoteno_sft;// 盛付通
	private String Paynoteno_zfb;// 支付宝
	private String Paynoteno_wx; // 微信
	private String errmess;
	private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	private String Notedatestr = "";
	private Long Sxyaccid;
	private String Calcdate = "";
	private String Smsuid;
	private String Smsuser;
	private String Smspwd;
	private Integer Xtbj = 0;
	private Integer Sumtag = 0;
	private Integer Tjspjf = 0;  //1=要积分

	public void setSmsuid(String smsuid) {
		Smsuid = smsuid;
	}

	public void setSmsuser(String smsuser) {
		Smsuser = smsuser;
	}

	public void setSmspwd(String smspwd) {
		Smspwd = smspwd;
	}

	public void setCalcdate(String calcdate) {
		this.Calcdate = calcdate;
	}

	public void setTjspjf(Integer tjspjf) {
		Tjspjf = tjspjf;
	}

	public void setCarryid(Long carryid) {
		Carryid = carryid;
	}

	public void setXtbj(Integer xtbj) {
		Xtbj = xtbj;
	}

	public void setSumtag(Integer sumtag) {
		Sumtag = sumtag;
	}

	public void setNewdatetime(String newdatetime) {
		Newdatetime = newdatetime;
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

	public void setUserid(Long userid) {
		this.Userid = userid;
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

	public void setCustid(Long custid) {
		this.Custid = custid;
	}

	public Long getCustid() {
		return Custid;
	}

	public void setGuestid(Long guestid) {
		this.Guestid = guestid;
	}

	public Long getGuestid() {
		return Guestid;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public void setNtid(Integer ntid) {
		this.Ntid = ntid;
	}

	public Integer getNtid() {
		return Ntid;
	}

	public void setHandno(String handno) {
		this.Handno = handno;
	}

	public void setWaybillno(String waybillno) {
		this.Waybillno = waybillno;
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

	public void setTotalcurr(Double totalcurr) {
		this.Totalcurr = totalcurr;
	}

	public Double getTotalcurr() {
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

	public void setRs(Long rs) {
		this.Rs = rs;
	}

	public Long getRs() {
		return Rs;
	}

	public void setCheckcurr(Double checkcurr) {
		this.Checkcurr = checkcurr;
	}

	public Double getCheckcurr() {
		return Checkcurr;
	}

	public void setChangecurr(Double changecurr) {
		this.Changecurr = changecurr;
	}

	public Double getChangecurr() {
		return Changecurr;
	}

	public void setFreecurr(Double freecurr) {
		this.Freecurr = freecurr;
	}

	public Double getFreecurr() {
		return Freecurr;
	}

	public void setZpaycurr(Double zpaycurr) {
		this.Zpaycurr = zpaycurr;
	}

	public Double getZpaycurr() {
		return Zpaycurr;
	}

	public void setYwly(Integer ywly) {
		this.Ywly = ywly;
	}

	public Integer getYwly() {
		return Ywly;
	}

	public void setBj(Integer bj) {
		this.Bj = bj;
	}

	public Integer getBj() {
		return Bj;
	}

	public void setCent(Long cent) {
		this.Cent = cent;
	}

	public Long getCent() {
		return Cent;
	}

	public void setJfcurr(Double jfcurr) {
		this.Jfcurr = jfcurr;
	}

	public Double getJfcurr() {
		return Jfcurr;
	}

	public void setUsecent(Long usecent) {
		this.Usecent = usecent;
	}

	public Long getUsecent() {
		return Usecent;
	}

	public void setDptid(Long dptid) {
		this.Dptid = dptid;
	}

	public Long getDptid() {
		return Dptid;
	}

	public void setPaylist(String paylist) {
		this.Paylist = paylist;
	}

	public String getPaylist() {
		return Paylist;
	}

	public void setOnhouseid(Long onhouseid) {
		this.Onhouseid = onhouseid;
	}

	public Long getOnhouseid() {
		return Onhouseid;
	}

	public void setWladd(String wladd) {
		this.Wladd = wladd;
	}

	public String getWladd() {
		return Wladd;
	}

	public void setCleartag(Integer cleartag) {
		this.Cleartag = cleartag;
	}

	public Integer getCleartag() {
		return Cleartag;
	}

	public void setTotalcost(Double totalcost) {
		this.Totalcost = totalcost;
	}

	public Double getTotalcost() {
		return Totalcost;
	}

	public void setTohouseid(Long tohouseid) {
		this.Tohouseid = tohouseid;
	}

	public Long getTohouseid() {
		return Tohouseid;
	}

	public void setMessid(Float messid) {
		this.Messid = messid;
	}

	public Float getMessid() {
		return Messid;
	}

	public void setYhjcurr(Double yhjcurr) {
		this.Yhjcurr = yhjcurr;
	}

	public Double getYhjcurr() {
		return Yhjcurr;
	}

	public void setVcpid(Long vcpid) {
		this.Vcpid = vcpid;
	}

	public Long getVcpid() {
		return Vcpid;
	}

	public void setBuy_accid(Long buy_accid) {
		this.Buy_accid = buy_accid;
	}

	public Long getBuy_accid() {
		return Buy_accid;
	}

	public void setBuy_noteno(String buy_noteno) {
		this.Buy_noteno = buy_noteno;
	}

	public String getBuy_noteno() {
		return Buy_noteno;
	}

	public void setPaynoteno_zfb(String paynoteno_zfb) {
		this.Paynoteno_zfb = paynoteno_zfb;
	}

	public String getPaynoteno_zfb() {
		return Paynoteno_zfb;
	}

	public void setPaynoteno_wx(String paynoteno_wx) {
		this.Paynoteno_wx = paynoteno_wx;
	}

	public String getPaynoteno_wx() {
		return Paynoteno_wx;
	}

	public void setFhtag(Integer fhtag) {
		this.Fhtag = fhtag;
	}

	public Integer getFhtag() {
		return Fhtag;
	}

	public void setYwnoteno(String ywnoteno) {
		this.Ywnoteno = ywnoteno;
	}

	public String getYwnoteno() {
		return Ywnoteno;
	}

	public void setPaynoteno_lkl(String paynoteno_lkl) {
		this.Paynoteno_lkl = paynoteno_lkl;
	}

	public String getPaynoteno_lkl() {
		return Paynoteno_lkl;
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
			errmess = "单据号无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n    v_orderno varchar2(20);");
		strSql.append("\n    v_ywnoteno varchar2(20);");
		strSql.append("\n    v_ntid number(1);");
		strSql.append("\n    v_ywly number(1);");
		strSql.append("\n    v_count number;");
		strSql.append("\n    v_statetag number(1);");
		strSql.append("\n    v_operant varchar2(20); ");
		strSql.append("\n    v_checkman varchar2(20); ");
		strSql.append("\n    v_paycurrv number(12,2);");
		strSql.append("\n    v_buyaccid number(10);");
		strSql.append("\n    v_buynoteno varchar2(20); ");
		strSql.append("\n    v_paynoteno_zfb varchar2(30);v_paynoteno_wx varchar2(30);v_paynoteno_lkl varchar2(30); ");
		strSql.append("\n    v_retcs varchar2(200);");
		strSql.append("\n    v_guestid number(10);");
		strSql.append("\n    v_cent number(10);");
		strSql.append("\n begin ");
		strSql.append("\n  v_ntid:=0;");

		strSql.append("\n  begin ");
		strSql.append("\n   select ntid,orderno,ywnoteno,ywly,buy_accid,buy_noteno,paynoteno_zfb,paynoteno_wx,paynoteno_lkl,statetag,operant,checkman,guestid,cent ");
		strSql.append("\n   into v_ntid,v_orderno,v_ywnoteno,v_ywly,v_buyaccid,v_buynoteno,v_paynoteno_zfb,v_paynoteno_wx,v_paynoteno_lkl,v_statetag,v_operant,v_checkman,v_guestid,v_cent ");
		strSql.append("\n   from wareouth where accid=" + Accid + " and noteno='" + Noteno + "';");

		strSql.append("\n   if v_operant<>'" + Operant + "' then ");
		strSql.append("\n      v_retcs:= '0不允许删除其他用户的单据！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if;");

		strSql.append("\n   if v_statetag>0 then ");
		strSql.append("\n      v_retcs:= '0当前单据已提交，不允许删除！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if;");
		strSql.append("\n  EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n    v_retcs:= '0未找到单据！'; ");
		strSql.append("\n    goto exit; ");
		strSql.append("\n  end; ");
		strSql.append("\n  begin ");

		// ywtag:0=店铺零售，1=商场零售，2=线上，3=批发销售
		strSql.append("\n   select count(*) into v_count from DEALRECORD where accid=" + Accid + " and ywnoteno='" + Noteno + "' and (ywtag=0 or ywtag=3)  and rownum=1;");
		strSql.append("\n   if v_count>0 then ");
		// strSql.append("\n if length(v_paynoteno_zfb)>0 or
		// length(v_paynoteno_wx)>0 or length(v_paynoteno_lkl)>0 then ");
		strSql.append("\n      v_retcs:= '0当前单据有第三方支付记录，不允许删除！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if;");

		//		strSql.append("\n  delete from wareoutm ");
		//		strSql.append("\n  where accid=" + Accid + " and noteno='" + Noteno + "'");
		//		strSql.append("\n  and exists (select 1 from wareouth a where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
		//		strSql.append("\n  and a.accid=wareoutm.accid and a.noteno=wareoutm.noteno and a.statetag=0);");

		//		strSql.append("\n  delete from waresaleman ");
		//		strSql.append("\n  where  accid=" + Accid + " and noteno='" + Noteno + "' ");
		//		strSql.append("\n  and exists (select 1 from wareouth a where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
		//		strSql.append("\n  and a.accid=waresaleman.accid and a.noteno=waresaleman.noteno and a.statetag=0);");

		//		strSql.append("\n  delete from wareoutcost ");
		//		strSql.append("\n  where  accid=" + Accid + " and noteno='" + Noteno + "' ");
		//		strSql.append("\n  and exists (select 1 from wareouth a where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
		//		strSql.append("\n  and a.accid=wareoutcost.accid and a.noteno=wareoutcost.noteno and a.statetag=0);");

		//		strSql.append("\n  delete from wareouth ");
		//		strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "'");
		//		strSql.append("\n   and statetag=0;");

		strSql.append("\n   update wareouth set statetag=2,lastdate=sysdate");
		strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0; ");

		strSql.append("\n  if length(v_orderno)>0 and v_ntid=2 then ");
		strSql.append("\n     update refundouth set thtag=0 where accid=" + Accid + " and noteno=v_orderno;");
		strSql.append("\n  end if;");
		strSql.append("\n  if length(v_ywnoteno)>0 and v_ntid=1 then ");// 删除批发开票，要把对应的配货单释放
		strSql.append("\n     update warepeih set xsnoteno='',statetag=1 where accid=" + Accid + " and noteno=v_ywnoteno;");
		// statetag:0=草稿,1=提交,2=审核,3=配货中，4=配货完，5=已转出库单
		strSql.append("\n  end if;");

		strSql.append("\n   if v_ywly=2 then "); // 如果是上下衣货位上货产生的出库单 ，要处理上货记录
		// waresong->statetag:0=草稿，1=提交，2=审核通过，3=审核未通过,4=收货/退货
		strSql.append("\n      update waresong set ywnoteno='',statetag=2 where accid=" + Accid + " and ywnoteno='" + Noteno + "' and statetag=4; ");
		strSql.append("\n   end if;");

		strSql.append("\n   if v_ywly=3 and v_buyaccid>0 and v_ntid=2 then "); // 如果是批发退货且载入经销商的采购退货单
		// waresong->statetag:0=草稿，1=提交，2=审核通过，3=审核未通过,4=收货/退货
		strSql.append("\n      update wareinh set bj=0 where accid=v_buyaccid and noteno=v_buynoteno; ");
		strSql.append("\n   end if;");

		strSql.append("\n   commit; ");
		strSql.append("\n   v_retcs:= '1删除成功'; ");
		strSql.append("\n    exception  ");
		strSql.append("\n    when others then  ");
		strSql.append("\n    begin ");
		strSql.append("\n      rollback;  ");
		strSql.append("\n      v_retcs:= '0删除失败！' ; ");
		strSql.append("\n    end; ");

		strSql.append("\n  end; ");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs,v_ntid into :retcs,:ntid from dual;");
		strSql.append("\n end; ");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		param.put("ntid", new ProdParam(Types.INTEGER));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		Ntid = Integer.parseInt(param.get("ntid").getParamvalue().toString());
		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

	// 删除记录 离线开票调用
	public int Delete1() {
		if (Noteno.length() <= 0) {
			errmess = "单据号无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n    v_orderno varchar2(20);");
		strSql.append("\n    v_ywnoteno varchar2(20);");
		strSql.append("\n    v_ntid number(1);");
		strSql.append("\n    v_ywly number(1);");
		strSql.append("\n    v_count number;");
		strSql.append("\n    v_statetag number(1);");
		strSql.append("\n    v_operant varchar2(20); ");
		strSql.append("\n    v_checkman varchar2(20); ");
		strSql.append("\n    v_paycurrv number(12,2);");
		strSql.append("\n    v_buyaccid number(10);");
		strSql.append("\n    v_buynoteno varchar2(20); ");
		// strSql.append("\n v_paynoteno_zfb varchar2(30);v_paynoteno_wx
		// varchar2(30);v_paynoteno_lkl varchar2(30); ");
		strSql.append("\n    v_retcs varchar2(200);");
		strSql.append("\n    v_guestid number(10);");
		strSql.append("\n    v_cent number(10);");
		strSql.append("\n begin ");
		strSql.append("\n  v_ntid:=0;");

		strSql.append("\n  begin ");
		strSql.append("\n   select ntid,orderno,ywnoteno,ywly,buy_accid,buy_noteno,statetag,operant,checkman,guestid,cent ");
		strSql.append("\n   into v_ntid,v_orderno,v_ywnoteno,v_ywly,v_buyaccid,v_buynoteno,v_statetag,v_operant,v_checkman,v_guestid,v_cent ");
		strSql.append("\n   from wareouth where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n   if v_checkman<>'' then ");
		strSql.append("\n      v_retcs:= '0单据已审核，不允许撤单！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if;");
		strSql.append("\n  EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n      v_retcs:= '1未找到单据！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n  end; ");

		strSql.append("\n  begin ");

		// ywtag:0=店铺零售，1=商场零售，2=线上，3=批发销售
		strSql.append("\n   select count(*) into v_count from DEALRECORD where accid=" + Accid + " and ywnoteno='" + Noteno + "' and (ywtag=0 or ywtag=3)  and rownum=1;");
		strSql.append("\n   if v_count>0 then ");
		// strSql.append("\n if length(v_paynoteno_zfb)>0 or
		// length(v_paynoteno_wx)>0 or length(v_paynoteno_lkl)>0 then ");
		strSql.append("\n      v_retcs:= '0当前单据有第三方支付记录，不允许删除！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if;");

		strSql.append("\n  delete from wareoutm ");
		strSql.append("\n  where accid=" + Accid + " and noteno='" + Noteno + "'");
		strSql.append("\n  and exists (select 1 from wareouth a where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
		strSql.append("\n  and a.accid=wareoutm.accid and a.noteno=wareoutm.noteno);");

		strSql.append("\n  delete from waresaleman ");
		strSql.append("\n  where  accid=" + Accid + " and noteno='" + Noteno + "' ");
		strSql.append("\n  and exists (select 1 from wareouth a where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
		strSql.append("\n  and a.accid=waresaleman.accid and a.noteno=waresaleman.noteno );");

		strSql.append("\n  delete from wareoutcost ");
		strSql.append("\n  where  accid=" + Accid + " and noteno='" + Noteno + "' ");
		strSql.append("\n  and exists (select 1 from wareouth a where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
		strSql.append("\n  and a.accid=wareoutcost.accid and a.noteno=wareoutcost.noteno);");

		strSql.append("\n  delete from wareouth ");
		strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "';");

		// strSql.append("\n if length(v_orderno)>0 and v_ntid=2 then ");
		// strSql.append("\n update refundouth set thtag=0 where accid=" + Accid
		// + " and noteno=v_orderno;");
		//
		// strSql.append("\n end if;");
		// strSql.append("\n if length(v_ywnoteno)>0 and v_ntid=1 then ");//
		// 删除批发开票，要把对应的配货单释放
		// strSql.append("\n update warepeih set xsnoteno='',statetag=1 where
		// accid=" + Accid + " and noteno=v_ywnoteno;");
		// // statetag:0=草稿,1=提交,2=审核,3=配货中，4=配货完，5=已转出库单
		// strSql.append("\n end if;");
		//
		// strSql.append("\n if v_ywly=2 then "); // 如果是上下衣货位上货产生的出库单 ，要处理上货记录
		// // waresong->statetag:0=草稿，1=提交，2=审核通过，3=审核未通过,4=收货/退货
		// strSql.append("\n update waresong set ywnoteno='',statetag=2 where
		// accid=" + Accid + " and ywnoteno='" + Noteno + "' and statetag=4; ");
		// strSql.append("\n end if;");
		//
		// strSql.append("\n if v_ywly=3 and v_buyaccid>0 and v_ntid=2 then ");
		// // 如果是批发退货且载入经销商的采购退货单
		// // waresong->statetag:0=草稿，1=提交，2=审核通过，3=审核未通过,4=收货/退货
		// strSql.append("\n update wareinh set bj=0 where accid=v_buyaccid and
		// noteno=v_buynoteno; ");
		// strSql.append("\n end if;");

		strSql.append("\n   commit; ");
		strSql.append("\n   v_retcs:= '1删除成功'; ");

		strSql.append("\n  if v_ntid=0 then "); // 零售
		strSql.append("\n     begin");
		strSql.append("\n       select a.curr into v_paycurrv from waresalepay a join payway b on a.payid=b.payid where a.accid=" + Accid + " and a.noteno='" + Noteno + "' and b.payno='V';");
		strSql.append("\n     EXCEPTION WHEN NO_DATA_FOUND THEN ");
		strSql.append("\n       v_paycurrv:=0;");
		strSql.append("\n     end;");
		strSql.append("\n     delete from waresalepay where accid=" + Accid + " and noteno='" + Noteno + "'; ");
		strSql.append("\n     if v_guestid>0 and ( v_cent<>0 or v_paycurrv>0) then");
		strSql.append("\n        delete from guestcent where guestid=v_guestid and xsnoteno='" + Noteno + "' and ly=0; "); // 删除消费积分
		strSql.append("\n        delete from guestcurr where guestid=v_guestid and xsnoteno='" + Noteno + "' and ly=0; "); // 删除消费积分
		strSql.append("\n        update guestvip set balcent=nvl((select sum(case fs when 0 then cent else -cent end) from guestcent where guestvip.guestid=guestcent.guestid),0)");
		strSql.append("\n          ,balcurr=nvl((select sum(case fs when 0 then curr else -curr end) from guestcurr where guestvip.guestid=guestcurr.guestid),0)");
		strSql.append("\n        where guestid=v_guestid; ");
		strSql.append("\n     end if; ");

		strSql.append("\n  elsif v_ntid=1 then "); // 批发出库
		strSql.append("\n     update incomecurr set statetag=2 where accid=" + Accid + " and ynoteno='" + Noteno + "'; ");
		strSql.append("\n  elsif v_ntid=2 then "); // 批发退库要把申请单退单标志清除
		strSql.append("\n     update incomecurr set statetag=2 where accid=" + Accid + " and ynoteno='" + Noteno + "'; ");
		strSql.append("\n  end if; ");
		strSql.append("\n    exception  ");
		strSql.append("\n    when others then  ");
		strSql.append("\n    begin ");
		strSql.append("\n      rollback;  ");
		strSql.append("\n      v_retcs:= '0删除失败！' ; ");
		strSql.append("\n    end; ");

		strSql.append("\n  end; ");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs,v_ntid into :retcs,:ntid from dual;");
		strSql.append("\n end; ");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		param.put("ntid", new ProdParam(Types.INTEGER));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		Ntid = Integer.parseInt(param.get("ntid").getParamvalue().toString());
		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

	// 增加记录
	public int Append(long epid) {
		String qry = "declare ";

		qry += "\n    v_accid  number;";
		qry += "\n    v_operant nvarchar2(10);";
		qry += "\n    v_noteno nvarchar2(20);";
		qry += "\n    v_custid  number;";
		qry += "\n    v_id  number;";
		qry += "\n    v_epid  number;";
		qry += "\n    v_ntid  number(1);";
		qry += "\n    v_houseid  number(10);";
		qry += "\n    v_retcs nvarchar2(100);";
		qry += "\n    v_progname varchar2(20);";
		qry += "\n    v_retbj number(1); ";
		qry += "\n begin ";
		qry += "\n   v_houseid:=" + Houseid + ";";
		//		qry += "\n   begin";
		//		qry += "\n     select houseid into v_houseid from employehouseout where houseid=" + Houseid + " and epid=" + Userid + ";";
		//		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
		//		qry += "\n     v_houseid:=0;";
		//		qry += "\n   end;";

		qry += "\n   begin ";
		if (Ntid == 2) {
			qry += "\n      select 'XT'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareouth";
			qry += "\n        where accid=" + Accid + " and ntid=" + Ntid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'XT'||To_Char(SYSDATE,'yyyymmdd');";
			qry += "\n    v_progname:='批发退货';";
		} else
		// qry += "\n elsif "+ntid+"=1 then ";
		if (Ntid == 1) {
			qry += "\n      select 'XS'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareouth";
			qry += "\n        where accid=" + Accid + " and ntid=" + Ntid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'XS'||To_Char(SYSDATE,'yyyymmdd');";
			qry += "\n    v_progname:='批发开票';";
		} else {
			qry += "\n      select 'XX'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareouth";
			qry += "\n        where accid=" + Accid + " and ntid=" + Ntid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'XX'||To_Char(SYSDATE,'yyyymmdd');";
			qry += "\n    v_progname:='店铺零售';";
		}
		// qry += "\n end if;";
		qry += "\n    v_id:=WAREOUTH_ID.NEXTVAL;";
		qry += "\n    insert into wareouth (id, accid,noteno,notedate,custid,houseid,dptid,ntid,statetag,totalamt,totalcurr,remark,handno,operant,checkman,lastdate,rs,checkcurr,changecurr,zpaycurr,freecurr)";
		qry += "\n    values (v_id," + Accid + ",v_noteno,sysdate," + Custid + ",v_houseid," + Dptid + "," + Ntid + ",0,0,0,'','','" + Operant + "','',sysdate,0,0,0,0,0);";
		// if ( (ntid == 0 || ntid==1) && epid > 0) //前台零售自动增加销售人
		if (epid > 0) // 前台零售，批发开票 自动增加销售人
		{
			qry += "\n   delete from waresaleman where accid=" + Accid + " and noteno=v_noteno and epid=" + epid + ";";
			qry += "\n   insert into waresaleman (id,accid,noteno,epid,lastdate)";
			qry += "\n   values (waresaleman_id.nextval," + Accid + ",v_noteno," + epid + ",sysdate); ";
		}
		qry += "\n    commit;";
		qry += "\n    v_retbj:=1;";
		qry += "\n    select '\"msg\":\"操作成功\",\"ID\":\"'||id||'\",\"NOTENO\":\"'||noteno||'\",\"NOTEDATE\":\"'||to_char(notedate,'yyyy-mm-dd hh24:mi:ss')||'\"' into v_retcs from wareouth where id=v_id;  ";

		qry += "\n    insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n    values (LOGRECORD_id.nextval," + Accid + ",v_progname,'【新增单据】单据号:'||v_noteno,'" + Operant + "');";

		qry += "\n  exception";
		qry += "\n   when others then ";
		qry += "\n   begin";
		qry += "\n     rollback;";
		qry += "\n     v_retbj:=0;  v_retcs:='\"msg\":\"操作失败，请重试！\"' ;";
		qry += "\n   end;";
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
		// int retbj =
		// Integer.parseInt(param.get("retbj").getParamvalue().toString());
		errmess = param.get("retcs").getParamvalue().toString();

		// errmess = retcs.substring(1);
		return Integer.parseInt(param.get("retbj").getParamvalue().toString());
	}

	// 修改零售记录
	public int Update(JSONObject jsonObject, int changedatebj, int fs, float jfrate, int priceprec, int openvipcity) {
		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "单据号无效！";
			return 0;
		}
		if (Ntid == null) {
			errmess = "ntid不是一个有效值！";
			return 0;
		}
		if (Statetag != null && Statetag == 1) // 如果是提交，必须转入客户及店铺，要判断客户及店铺
		{

			if (Houseid == null || Houseid == 0) {
				errmess = "houseid不是一个有效值！";
				return 0;
			}
		}
		if (!Func.isNull(Notedatestr)) {
			if (Func.subString(Notedatestr, 1, 10).compareTo(Calcdate) <= 0) {
				errmess = "单据日期只能在系统登账日 " + Calcdate + " 之后！";
				return 0;
			}
		}

		if (Zpaycurr == null)
			Zpaycurr = (double) 0;
		if (Freecurr == null)
			Freecurr = (double) 0;
		if (Totalcurr == null)
			Totalcurr = (double) 0;
		if (Usecent == null)
			Usecent = (long) 0;
		if (Jfcurr == null)
			Jfcurr = (double) 0;
		if (Yhjcurr == null)
			Yhjcurr = (double) 0;
		if (Jfcurr > 0) {
			if (jfrate == 0) {
				errmess = "积分抵扣比例无效，不允许积分抵扣消费！";
				return 0;
			}
			Float jfcurr = Func.getRound(Usecent / jfrate, 2);
			// if (Func.getRound(jfcurr, 2)!=Func.getRound(Jfcurr, 2)) {

			if (!Func.isEqual(jfcurr, Jfcurr)) {
				errmess = "积分抵扣的金额异常，请检查！ 金额:" + Jfcurr + " 比例: " + jfrate + " 使用积分:" + Usecent + " = " + jfcurr;
				return 0;
			}
			if (Jfcurr > Totalcurr) {
				errmess = "积分抵扣金额不允大于合计应收金额！";
				return 0;
			}
		}

		if ((Guestid == null || Guestid == 0) && (Usecent > 0 || Jfcurr > 0)) // 会员id未传入，不允许使用积分和抵扣消费

		{
			errmess = "会员卡无效，不允许积分抵扣消费！";
			return 0;
		}
		if (Guestid == null || Guestid == 0) {
			Yhjcurr = (double) 0;
			Vcpid = (long) 0;
		}
		Checkcurr = Func.getRound(Totalcurr - Freecurr - Jfcurr - Yhjcurr, 2);

		if (Vcpid == null)
			Vcpid = (long) 0;
		if (Yhjcurr > 0 && Vcpid == 0) {
			errmess = "优惠券id无效！";
			return 0;
		}
		if (Statetag != null && Statetag == 1) {
			// 判断免单金额
			if (Totalcurr >= 0) {
				// if (model.freecurr > model.totalcurr - model.jfcurr ||
				// model.freecurr < 0)
				// if (Func.isEqual(a, b))
				if (Freecurr > Func.getRound(Totalcurr - Jfcurr - Yhjcurr, 2)) {
					errmess = "免单金额不允许大于合计金额！";
					return 0;
				}
				if (Zpaycurr < Checkcurr) {
					errmess = "收款合计" + Zpaycurr + "小于实结金额" + Checkcurr + "，不允许提交！";
					return 0;
				}
			} else {
				// i//f (model.freecurr < model.totalcurr || model.freecurr > 0)
				if (Freecurr < Totalcurr) {
					errmess = "当前单据需要退款，免单金额不允许小于应退合计金额！";
					return 0;
				}
				if (!Func.isEqual(Zpaycurr, Checkcurr)) {
					// if (Zpaycurr != Checkcurr) {
					errmess = "退款合计与实结金额不等，不允许提交！";
					return 0;
				}
			}
		}
		Changecurr = Func.getRound(Zpaycurr - Checkcurr, 2);
		if (Changecurr < 0)
			Changecurr = (double) 0;

		Noteno = Noteno.toUpperCase();
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		// strSql.append("\n v_noteno varchar2(20); ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_centrate number; ");
		strSql.append("\n   v_cent number(10); ");
		strSql.append("\n   v_totalcurr number; ");
		strSql.append("\n   v_totalamt number; ");
		strSql.append("\n   v_balcent number; ");
		strSql.append("\n   v_usecent number; ");
		strSql.append("\n   v_balcurr number(16,2); ");
		strSql.append("\n   v_paycurrv number(16,2); ");
		strSql.append("\n   v_paycurrvid number(10); ");
		strSql.append("\n   v_mobile varchar2(20); ");
		strSql.append("\n   v_smspwd varchar2(6); ");
		strSql.append("\n   v_vcpid number(10); ");
		strSql.append("\n   v_vipid number(10); ");
		strSql.append("\n   v_usetag number(1); ");
		strSql.append("\n   v_yhjcurr number; ");
		strSql.append("\n   v_xfcurr number; ");
		strSql.append("\n   v_zfbcurr number; ");
		strSql.append("\n   v_tjcurr number; ");
		strSql.append("\n   v_kyjfcurr number; ");
		strSql.append("\n   v_houseid number(10); ");
		strSql.append("\n   v_notedate date; ");

		strSql.append("\n begin ");

		if (Notedatestr.length() > 0) {
			strSql.append("\n   v_notedate:=to_date('" + Notedatestr + "','yyyy-mm-dd hh24:mi:ss');");
			strSql.append("\n   if to_char(v_notedate,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')  or v_notedate>sysdate then ");
			strSql.append("\n      v_notedate:=sysdate;");
			strSql.append("\n   end if;");
		} else {
			strSql.append("\n   v_notedate:=sysdate;");
		}

		if (Guestid != null && Guestid > 0) {
			strSql.append("\n   select count(*) into v_count from guestvip where accid=" + Accid + " and guestid=" + Guestid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0会员无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Houseid != null) {
			strSql.append("\n   select count(*) into v_count from warehouse where accid=" + Accid + " and houseid=" + Houseid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0店铺无效1！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
			strSql.append("\n   v_houseid:=" + Houseid + ";");
		} else {
			strSql.append("\n   select houseid into v_houseid from wareouth where accid=" + Accid + " and noteno='" + Noteno + "';");
			strSql.append("\n   if v_houseid=0 then");
			strSql.append("\n      v_retcs:='0店铺无效2！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Dptid != null && Dptid > 0) {
			strSql.append("\n   select count(*) into v_count from department where accid=" + Accid + " and dptid=" + Dptid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0部门无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}

		if (Statetag != null && Statetag == 1) {
			// ywtag:0=店铺零售，1=商场零售，2=线上，3=批发销售
			strSql.append("\n  select count(*) into v_count from DEALRECORD where accid=" + Accid + " and ywnoteno='" + Noteno + "' and ywtag=0 and statetag=0 and rownum=1;");
			strSql.append("\n  if v_count>0 then ");
			strSql.append("\n     v_retcs:='0第三方交易记录异常，请检查！'; ");
			strSql.append("\n     goto exit; ");
			strSql.append("\n  end if;");

			// if (Paynoteno_zfb != null && Paynoteno_zfb.length() > 0) {
			// strSql.append("\n begin "); // DEALRECORD->payfs:
			// 0=支付宝，1=微信,2=拉卡拉,3=盛付通
			// strSql.append("\n select curr into v_zfbcurr from DEALRECORD
			// where accid=" + Accid + " and noteno='" + Paynoteno_zfb + " and
			// payfs=0 and statetag=1;");
			// strSql.append("\n EXCEPTION WHEN NO_DATA_FOUND THEN ");
			// strSql.append("\n v_retcs:='0支付宝交易订单记录异常！'; ");
			// strSql.append("\n goto exit; ");
			// strSql.append("\n end; ");
			//
			// }
			// if (Paynoteno_wx != null && Paynoteno_wx.length() > 0) // 微信交易记录
			// {
			// strSql.append("\n begin "); // DEALRECORD->payfs:
			// 0=支付宝，1=微信,2=拉卡拉,3=盛付通
			// strSql.append("\n select curr into v_zfbcurr from DEALRECORD
			// where accid=" + Accid + " and noteno='" + Paynoteno_wx + " and
			// payfs=1 and statetag=1;");
			// strSql.append("\n EXCEPTION WHEN NO_DATA_FOUND THEN ");
			// strSql.append("\n v_retcs:= '0微信交易订单记录异常！'; ");
			// strSql.append("\n goto exit; ");
			// strSql.append("\n end; ");
			// }
			// if (Paynoteno_lkl != null && Paynoteno_lkl.length() > 0) //
			// 拉卡拉交易记录
			// {
			// strSql.append("\n begin ");// DEALRECORD->payfs:
			// 0=支付宝，1=微信,2=拉卡拉,3=盛付通
			// strSql.append("\n select curr into v_zfbcurr from DEALRECORD
			// where accid=" + Accid + " and noteno='" + Paynoteno_lkl + " and
			// payfs=2 and statetag=1;");
			// strSql.append("\n EXCEPTION WHEN NO_DATA_FOUND THEN ");
			// strSql.append("\n v_retcs:='0拉卡拉交易订单记录异常！'; ");
			// strSql.append("\n goto exit; ");
			// strSql.append("\n end; ");
			// }
			// if (Paynoteno_sft != null && Paynoteno_sft.length() > 0) //
			// 拉卡拉交易记录
			// {
			// strSql.append("\n begin ");// DEALRECORD->payfs:
			// 0=支付宝，1=微信,2=拉卡拉,3=盛付通
			// strSql.append("\n select curr into v_zfbcurr from DEALRECORD
			// where accid=" + Accid + " and noteno='" + Paynoteno_sft + " and
			// payfs=3 and statetag=1;");
			// strSql.append("\n EXCEPTION WHEN NO_DATA_FOUND THEN ");
			// strSql.append("\n v_retcs:='0盛付通交易订单记录异常！'; ");
			// strSql.append("\n goto exit; ");
			// strSql.append("\n end; ");
			// }
		}
		strSql.append("\n   v_count:=0; v_cent:=0; v_paycurrv:=0; v_vcpid:=0; v_vipid:=0;");
		strSql.append("\n   v_yhjcurr:=0; v_xfcurr:=0; ");
		strSql.append("\n   v_usecent:=" + Usecent + "; ");
		if (Guestid != null && Guestid > 0) // 是会员消费
		{
			strSql.append("\n   begin ");
			strSql.append("\n     select b.centrate,a.balcent,a.balcurr,a.mobile,a.vipid into v_centrate,v_balcent,v_balcurr,v_mobile,v_vipid ");
			strSql.append("\n     from guestvip a join guesttype b on a.vtid=b.vtid where a.guestid= " + Guestid + "; ");
			strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN ");
			strSql.append("\n     v_centrate:=0;  v_balcent:=0;  v_balcurr:=0;  v_mobile:='';  v_vipid:=0; ");
			strSql.append("\n   end; ");

			if (Vcpid != null && Vcpid > 0) // 使用优惠券
			{
				strSql.append("\n   v_vcpid:=" + Vcpid + "; ");
				strSql.append("\n   begin");
				strSql.append("\n     select a.usetag,b.curr,b.xfcurr into v_usetag,v_yhjcurr,v_xfcurr from VIPCOUPON a join housecoupon b on a.cpid=b.cpid where a.vcpid=v_vcpid and a.vipid=v_vipid;");
				strSql.append("\n     if v_usetag>0 then ");
				strSql.append("\n        v_retcs:='0优惠券已使用！'; ");
				strSql.append("\n        goto exit; ");
				strSql.append("\n     end if;");
				strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN ");
				strSql.append("\n     v_retcs:='0未找到优惠券！' ; ");
				strSql.append("\n     return; ");
				strSql.append("\n   end; ");
				strSql.append("\n   if " + Yhjcurr + ">v_yhjcurr then ");
				strSql.append("\n      v_retcs:='0优惠券抵扣金额无效！'; ");
				strSql.append("\n      goto exit; ");
				strSql.append("\n   end if;");

				strSql.append("\n   if " + (Totalcurr - Freecurr) + "<v_xfcurr then ");
				strSql.append("\n      v_retcs:='0优惠券未达到消费金额条件！' ; ");
				strSql.append("\n      goto exit; ");
				strSql.append("\n   end if;");
			}
		}
		if (Statetag != null && Statetag == 1 && fs == 0)// fs=1允许负出库 fs=0不允许负出库
		{
			// 要判断负库存
			strSql.append("\n begin");
			strSql.append("\n    select b.wareno||','||c.colorname||','||d.sizename into v_retcs from ");
			strSql.append("\n    (select wareid,colorid,sizeid,sum(amount) as amount from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "'");
			strSql.append("\n    group by wareid,colorid,sizeid) a");
			strSql.append("\n    left outer join warecode b on a.wareid=b.wareid");
			strSql.append("\n    left outer join colorcode c on a.colorid=c.colorid");
			strSql.append("\n    left outer join sizecode d on a.sizeid=d.sizeid");
			strSql.append("\n    where a.amount>0 and a.amount>f_getwareamountx(to_char(sysdate,'yyyy-mm-dd')," + Accid + ",a.wareid,a.colorid,a.sizeid," + Houseid + ",'" + Calcdate + "') and rownum=1;");
			strSql.append("\n    v_retcs:='0'||v_retcs||'库存不足，不允许出库！'; ");
			strSql.append("\n    goto exit; ");

			strSql.append("\n EXCEPTION WHEN NO_DATA_FOUND THEN ");
			strSql.append("\n    v_retcs:=''; ");
			strSql.append("\n end; ");

		}
		strSql.append("\n  select count(*) into v_count from waresaleman where accid=" + Accid + " and noteno='" + Noteno + "'; "); // 销售人员数
		strSql.append("\n  select nvl(sum(curr),0),nvl(sum(amount),0)  into v_totalcurr,v_totalamt from wareoutm  where accid=" + Accid + " and noteno='" + Noteno + "';");

		if (Statetag != null && Statetag == 1) // 前台零售提交要填入结算方式
		{
			strSql.append("\n  if v_totalcurr<>" + Totalcurr + " then ");
			strSql.append("\n     update wareouth set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno='" + Noteno + "';");
			strSql.append("\n     v_retcs:='0合计金额(" + Totalcurr + ")异常,请重新结算！' ; ");
			strSql.append("\n     goto exit; ");
			strSql.append("\n  end if; ");
			strSql.append("\n  delete from waresalepay where accid=" + Accid + " and noteno='" + Noteno + "';");
			if (jsonObject.has("paylist")) {
				JSONArray jsonArray = jsonObject.getJSONArray("paylist");

				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject2 = jsonArray.getJSONObject(i);
					Float paycurr = Float.parseFloat(jsonObject2.getString("paycurr"));
					Long payid = Long.parseLong(jsonObject2.getString("payid"));
					String payno = "*";
					if (jsonObject2.has("payno"))
						payno = jsonObject2.getString("payno");
					if (payno.equals("V")) // 有储值卡消费，要校验短信码
					{
						strSql.append("\n   v_paycurrv:=" + paycurr + "; ");
						strSql.append("\n   v_paycurrvid:=" + payid + "; ");
					}

					strSql.append("\n  insert into waresalepay (id,accid,noteno,payid,curr,lastdate)");
					strSql.append("\n  values (waresalepay_id.nextval," + Accid + ",'" + Noteno + "'," + payid + "," + paycurr + ",sysdate); ");
				}
			}
			if (Guestid != null && Guestid > 0) // 生成积分记录
			{
				strSql.append("\n   if v_paycurrv<>0 then"); // 有储值消费，校验短信
				strSql.append("\n      if v_paycurrv>0 then ");
				strSql.append("\n        if v_paycurrv>v_balcurr then");
				strSql.append("\n          v_retcs:='0本次储值刷卡金额已超出卡余额！'; ");
				strSql.append("\n          goto exit; ");
				strSql.append("\n        end if; ");
				strSql.append("\n      end if; ");
				strSql.append("\n      insert into guestcurr (id,guestid,notedate,houseid,curr,payid,paycurr,remark,fs,ly,xsnoteno,operant)"); // ly:0=手工输入,1=商场零售,2=店铺零售
				strSql.append("\n      values (guestcurr_id.nextval," + Guestid + ",sysdate,v_houseid,v_paycurrv,v_paycurrvid,0,'刷卡消费 №" + Noteno + "',1,2,'" + Noteno + "','" + Operant + "'); ");

				strSql.append("\n      update guestvip set balcurr=nvl((select sum(case fs when 0 then curr else -curr end) from guestcurr where guestvip.guestid=guestcurr.guestid),0)");
				strSql.append("\n         where guestid=" + Guestid + "; ");

				strSql.append("\n   end if; ");

				strSql.append("\n   if v_usecent>v_balcent then");
				strSql.append("\n      v_retcs:='0本次使用积分已超出积分余额！'; ");
				strSql.append("\n      goto exit; ");
				strSql.append("\n   end if; ");
				strSql.append("\n   if v_usecent>0 then");
				strSql.append("\n      insert into guestcent (id,guestid,notedate,houseid,cent,remark,fs,ly,xsnoteno,operant)"); // ly:0=手工输入,1=商场零售,2=店铺零售
				strSql.append("\n      values (guestcent_id.nextval," + Guestid + ",sysdate,v_houseid,v_usecent,'积分抵扣" + Jfcurr + "元金额 №" + Noteno + "',1,2,'" + Noteno + "','" + Operant + "'); ");
				strSql.append("\n   end if; ");
				strSql.append("\n   v_cent:=0;");
				strSql.append("\n   if v_centrate>0 then ");
				if (Checkcurr > 0) {
					// 计算特价商品金额
					if (Tjspjf == 0) {//0=特价商品不积分
						strSql.append("\n      select nvl(sum(a.curr),0) into v_tjcurr from wareoutm a where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
						strSql.append("\n      and a.tjtag=1;");//and exists (select 1 from warecode b where a.wareid=b.wareid and b.tjtag=1);");
					} else {
						strSql.append("\n   v_tjcurr:=0;");
					}
					// 可积分金额=应结金额-特价商品金额
					strSql.append("\n      v_kyjfcurr:=" + Checkcurr + "-v_tjcurr;");
					strSql.append("\n      if v_kyjfcurr<0 then v_kyjfcurr:=0; end if;");
				} else {
					strSql.append("\n      v_kyjfcurr:=" + Checkcurr + ";");
				}
				strSql.append("\n      v_cent:=trunc(v_kyjfcurr/v_centrate); ");
				strSql.append("\n   end if;");
				strSql.append("\n   if v_cent<>0 then ");
				strSql.append("\n      insert into guestcent (id,guestid,notedate,houseid,cent,remark,fs,ly,xsnoteno,operant)"); // ly:0=手工输入,1=商场零售,2=店铺零售
				strSql.append("\n      values (guestcent_id.nextval," + Guestid + ",sysdate,v_houseid,v_cent,'消费增加积分 №" + Noteno + "',0,2,'" + Noteno + "','" + Operant + "'); ");
				strSql.append("\n   end if; ");
				strSql.append("\n   if v_cent<>0 or v_usecent>0 then ");
				strSql.append("\n      update guestvip set balcent=nvl((select sum(case fs when 0 then cent else -cent end) from guestcent where guestvip.guestid=guestcent.guestid),0)");
				strSql.append("\n      where guestid=" + Guestid + "; ");
				strSql.append("\n   end if; ");
			}
		}

		strSql.append("\n   update wareouth set ");

		strSql.append("notedate=v_notedate,");
		if (Custid != null) {
			strSql.append("custid=" + Custid + ",");
		}
		if (Guestid != null) {
			strSql.append("guestid=" + Guestid + ",");
		}
		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
		}
		if (Handno != null) {
			strSql.append("handno='" + Handno.toUpperCase() + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		// if (Fhadd != null) {
		// strSql.append("Fhadd='" + Fhadd + "',");
		// }
		if (Waybillno != null) {
			strSql.append("Waybillno='" + Waybillno + "',");
		}

		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Orderno != null) {
			strSql.append("orderno='" + Orderno + "',");
		}
		if (Checkcurr != null) {
			strSql.append("checkcurr=" + Checkcurr + ",");
		}
		if (Changecurr != null) {
			strSql.append("changecurr=" + Changecurr + ",");
		}
		if (Freecurr != null) {
			strSql.append("freecurr=" + Freecurr + ",");
		}
		if (Zpaycurr != null) {
			strSql.append("zpaycurr=" + Zpaycurr + ",");
		}
		if (Ywly != null) {
			strSql.append("ywly=" + Ywly + ",");
		}
		if (Bj != null) {
			strSql.append("bj=" + Bj + ",");
		}
		// if (Cent != null) {
		// strSql.append("cent=" + Cent + ",");
		// }
		if (Jfcurr != null) {
			strSql.append("jfcurr=" + Jfcurr + ",");
		}
		if (Usecent != null) {
			strSql.append("usecent=" + Usecent + ",");
		}
		if (Dptid != null) {
			strSql.append("dptid=" + Dptid + ",");
		}
		// if (Paylist != null) {
		// strSql.append("paylist='" + Paylist + "',");
		// }
		if (Onhouseid != null) {
			strSql.append("onhouseid=" + Onhouseid + ",");
		}
		if (Wladd != null) {
			strSql.append("wladd='" + Wladd + "',");
		}
		// if (Cleartag != null) {
		// strSql.append("cleartag=" + Cleartag + ",");
		// }
		// if (Totalcost != null) {
		// strSql.append("totalcost='" + Totalcost + "',");
		// }
		if (Tohouseid != null) {
			strSql.append("tohouseid=" + Tohouseid + ",");
		}
		if (Messid != null) {
			strSql.append("messid='" + Messid + "',");
		}
		if (Yhjcurr != null) {
			strSql.append("yhjcurr=" + Yhjcurr + ",");
		}
		if (Vcpid != null) {
			strSql.append("vcpid=" + Vcpid + ",");
		}
		if (Buy_accid != null) {
			strSql.append("buy_accid=" + Buy_accid + ",");
		}
		if (Buy_noteno != null) {
			strSql.append("buy_noteno='" + Buy_noteno + "',");
		}
		if (Paynoteno_zfb != null) {
			strSql.append("paynoteno_zfb='" + Paynoteno_zfb + "',");
		}
		if (Paynoteno_wx != null) {
			strSql.append("paynoteno_wx='" + Paynoteno_wx + "',");
		}
		// if (Fhtag != null) {
		// strSql.append("fhtag=" + Fhtag + ",");
		// }
		if (Ywnoteno != null) {
			strSql.append("ywnoteno='" + Ywnoteno + "',");
		}
		if (Paynoteno_lkl != null) {
			strSql.append("paynoteno_lkl='" + Paynoteno_lkl + "',");
		}
		// if (!Func.isNull(Notedatestr)) {
		// strSql.append("notedate=to_date('" + Notedatestr + "','yyyy-mm-dd
		// hh24:mi:ss'),");
		// } else {
		// strSql.append("notedate=sysdate,");
		// }
		if (Statetag != null && Statetag == 1) // 如果提交，优惠券已用标志=1
		{
			strSql.append("checkman='" + Operant + "',");
			strSql.append("cashier='" + Operant + "',");
			// aaaa
		}
		// strSql.append("\n
		// rs=v_count,totalcurr=v_totalcurr,totalamt=v_totalamt,lastdate=sysdate,bj=0");
		strSql.append("\n  rs=v_count,totalcurr=v_totalcurr,totalamt=v_totalamt,cent=v_cent,lastdate=sysdate");

		strSql.append("\n  where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n  p_wareoutpaylist(" + Accid + ",'" + Noteno + "'); ");
		if (Statetag != null && Statetag == 1) // 如果提交，优惠券已用标志=1
		{
			strSql.append("\n  if v_vcpid>0 then ");
			strSql.append("\n     update vipcoupon set usetag=1 where vcpid=v_vcpid;");
			strSql.append("\n  end if;");
			if (Ntid == 0)//店铺零售
				strSql.append("\n  p_paytobook(" + Accid + ",'" + Noteno + "',1);"); //结算自动登入账本 fs:0=商场零售，1=店铺零售,2=销售收款，3=采购付款
		}
		strSql.append("\n   commit;");
		// qry += "\n select '1操作成功！' into :retcs from dual;";
		strSql.append("\n   v_retcs:='1'||v_cent ;");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		strSql.append("\n end;");
		//		System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		ret = Integer.parseInt(retcs.substring(0, 1));
		if (ret == 1 && Statetag != null && Statetag == 1) // 如果提交且成功，如果是会员，向会员发送消息
		{
			if (Guestid != null && Guestid > 0)
				try {
					// String qry = "select
					// balcurr,balcent,mobile,guestname,sex,vipno from guestvip
					// where guestid=" + Guestid;
					String qry = "select b.balcurr,b.balcent,b.mobile,b.guestname,b.sex,b.vipno,c.housename";
					qry += " from wareouth a";
					qry += " join guestvip b on a.guestid=b.guestid";
					qry += " join warehouse c on a.houseid=c.houseid";
					qry += " where a.accid=" + Accid + " and noteno='" + Noteno + "'";
					Table tb = DbHelperSQL.Query(qry).getTable(1);
					if (tb.getRowCount() == 1) {
						// if (Cent == null)
						// LogUtils.LogDebugWrite("updatewaresaleh","1111");
						String guestname = tb.getRow(0).get("GUESTNAME").toString();
						String sex = tb.getRow(0).get("SEX").toString();
						String vipno = tb.getRow(0).get("VIPNO").toString();
						String mobile = tb.getRow(0).get("MOBILE").toString();
						String housename = tb.getRow(0).get("HOUSENAME").toString();

						Cent = Long.parseLong(errmess);
						// LogUtils.LogDebugWrite("updatewaresaleh","2222");

						String strmess1 = "尊敬的" + guestname;
						if (sex.equals("女"))
							strmess1 += "女士";
						else if (sex.equals("男"))
							strmess1 += "先生";

						strmess1 += ":您尾号为" + Func.strRight(vipno, 4) + "的会员卡在『" + housename + "』的购物单：" + Noteno //
								+ " 消费总金额:" + Totalcurr + "元,本次积分" + Cent//
								+ ",积分余额" + tb.getRow(0).get("BALCENT").toString()//
								+ ",储值余额" + tb.getRow(0).get("BALCURR").toString() + "元。谢谢惠顾！";

						// LogUtils.LogDebugWrite("updatewaresaleh","3333:"+strmess1);
						Func.sendMessage(Smsuid, Smsuser, Smspwd, mobile, strmess1, Operant, Accid);
						// LogUtils.LogDebugWrite("updatewaresaleh","4444");

						Vipmessage dal = new Vipmessage();
						dal.setLastop(Operant);
						dal.setRemark(strmess1);
						dal.setAccid(Accid);
						// dal.setLx(1); // lx:0=通知，1=消费请求
						dal.doSendmess1(Houseid, Guestid);
						// LogUtils.LogDebugWrite("updatewaresaleh", "5555");
					}
				} catch (Exception e) {

					LogUtils.LogErrWrite("updatewaresaleh", e.getMessage());
				}

		}
		return ret;

	}

	// 修改批发记录 主要用于发货
	public int Update() {
		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "单据号无效！";
			return 0;
		}
		if (Fhtag < 0 || Fhtag > 1) {
			errmess = "发货标志无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n   v_count number;");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n   v_custid number(10);");

		strSql.append("begin");

		if (Custid != null) {
			strSql.append("\n   select count(*) into v_count from customer where accid=" + Accid + " and custid=" + Custid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0客户无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Guestid != null) {
			strSql.append("\n   select count(*) into v_count from guestvip where accid=" + Accid + " and custid=" + Guestid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0会员无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Carryid != null && Carryid > 0) {
			strSql.append("\n   select count(*) into v_count from carryline where accid=" + Accid + " and carryid=" + Carryid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0物流单位无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Dptid != null) {
			strSql.append("\n   select count(*) into v_count from department where accid=" + Accid + " and Dptid=" + Dptid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0销售部门无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}

		strSql.append("\n   update wareouth set ");
		if (Custid != null) {
			strSql.append("custid=" + Custid + ",");
		}
		if (Guestid != null) {
			strSql.append("guestid=" + Guestid + ",");
		}
		if (Carryid != null) {
			strSql.append("Carryid=" + Carryid + ",");
		}
		if (Handno != null) {
			strSql.append("handno='" + Handno.toUpperCase() + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Orderno != null) {
			strSql.append("orderno='" + Orderno.toUpperCase() + "',");
		}
		if (Ywly != null) {
			strSql.append("ywly=" + Ywly + ",");
		}
		if (Bj != null) {
			strSql.append("bj=" + Bj + ",");
		}
		if (Dptid != null) {
			strSql.append("dptid=" + Dptid + ",");
		}
		if (Wladd != null) {
			strSql.append("wladd='" + Wladd + "',");
		}
		if (Fhtag != null) {
			strSql.append("fhtag=" + Fhtag + ",");
		}
		if (Ywnoteno != null) {
			strSql.append("ywnoteno='" + Ywnoteno.toUpperCase() + "',");
		}
		if (Waybillno != null) {
			strSql.append("Waybillno='" + Waybillno.toUpperCase() + "',");
		}
		// if (Fhadd != null) {
		// strSql.append("Fhadd='" + Fhadd + "',");
		// }

		strSql.append("\n  lastdate=sysdate");
		strSql.append("\n  where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n  v_retcs:='1操作成功！';");
		strSql.append("\n  commit;");
		if (Carryid != null && Carryid > 0) { // 更改默认物流单位
			strSql.append("\n    select custid into v_custid from wareouth where accid=" + Accid + " and noteno='" + Noteno + "';");
			strSql.append("\n    update customer set carryid=" + Carryid + " where custid=v_custid;");
		}
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

		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "操作异常！";
		// return 0;
		// } else {
		// errmess = "操作成功！";
		// return 1;
		// }
	}

	// 修改批发记录
	public int Update1(JSONObject jsonObject, int changedatebj, int fs, int autogd, int housecostbj) {
		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "单据号无效！";
			return 0;
		}
		if (Ntid == null) {
			errmess = "ntid不是一个有效值！";
			return 0;
		}
		long houseid = 0;
		if (Statetag != null && Statetag == 1) // 如果是提交，必须转入客户及店铺，要判断客户及店铺
		{
			if (Custid == null || Custid == 0) {
				errmess = "custid不是一个有效值！";
				return 0;
			}
			if (Houseid == null || Houseid == 0) {
				errmess = "houseid不是一个有效值！";
				return 0;
			}
			if (housecostbj == 1)
				houseid = Houseid;
		}

		if (Sxyaccid != null && Sxyaccid > 0) {
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

		if (Zpaycurr == null)
			Zpaycurr = (double) 0;
		if (Totalcurr == null)
			Totalcurr = (double) 0;
		// if (model.freecurr == null)
		Freecurr = (double) 0;
		if (Totalcurr == null)
			Totalcurr = (double) 0;
		Checkcurr = Totalcurr;// -model.freecurr;
		Gdcurr = (double) 0;
		Noteno = Noteno.toUpperCase();
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_rs number; ");
		strSql.append("\n   v_saleid number(10);");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n   v_totalcurr number(16,2); ");
		strSql.append("\n   v_totalcost number; ");
		strSql.append("\n   v_totalamt number; ");
		strSql.append("\n   v_noteno varchar2(20); ");
		strSql.append("\n   v_orderno varchar2(20); ");
		strSql.append("\n   v_balcurr number(16,2); ");
		strSql.append("\n   v_creditcurr number(16,2);  ");
		strSql.append("\n   v_creditok number(1);  ");
		strSql.append("\n   v_freecurr number(16,2); ");
		strSql.append("\n   v_zpaycurr number(16,2); ");
		strSql.append("\n   v_zfbcurr number(16,2); ");
		strSql.append("\n   v_notedate date; ");
		strSql.append("\n   v_handmanid number(10);");
		strSql.append("\n   v_ywnoteno varchar2(20); ");
		strSql.append("\n begin ");
		strSql.append("\n    v_handmanid:=0; ");
		if (Notedatestr.length() > 0) {
			strSql.append("\n   v_notedate:=to_date('" + Notedatestr + "','yyyy-mm-dd hh24:mi:ss');");
			strSql.append("\n   if to_char(v_notedate,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') or v_notedate>sysdate then ");
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
		if (Houseid != null) {
			strSql.append("\n   select count(*) into v_count from warehouse where accid=" + Accid + " and houseid=" + Houseid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0店铺无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Dptid != null && Dptid > 0) {
			strSql.append("\n   select count(*) into v_count from department where accid=" + Accid + " and dptid=" + Dptid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0部门无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Tohouseid != null && Tohouseid > 0) {
			strSql.append("\n   select count(*) into v_count from warehouse where accid=" + Accid + " and houseid=" + Tohouseid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0收货店铺无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		strSql.append("\n   v_count:=0;");
		if (Statetag != null && Statetag == 1 && fs == 0 && Ntid != 2)// fs=1允许负出库
																		// fs=0不允许负出库
		{
			// 要判断负库存
			strSql.append("\n begin");
			strSql.append("\n   select b.wareno||','||c.colorname||','||d.sizename into v_retcs from ");
			strSql.append("\n   (select wareid,colorid,sizeid,sum(amount) as amount from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "'");
			strSql.append("\n   group by wareid,colorid,sizeid) a");
			strSql.append("\n   left outer join warecode b on a.wareid=b.wareid");
			strSql.append("\n   left outer join colorcode c on a.colorid=c.colorid");
			strSql.append("\n   left outer join sizecode d on a.sizeid=d.sizeid");
			strSql.append("\n   where a.amount>0 and a.amount>f_getwareamountx(to_char(sysdate,'yyyy-mm-dd')," + Accid + ",a.wareid,a.colorid,a.sizeid," + Houseid + ",'" + Calcdate + "') and rownum=1;");
			strSql.append("\n   v_retcs:='0'||v_retcs||'库存不足，不允许出库！'; ");
			strSql.append("\n   goto exit; ");

			strSql.append("\n EXCEPTION WHEN NO_DATA_FOUND THEN ");
			strSql.append("\n   v_retcs:=''; ");
			strSql.append("\n end; ");

		}
		strSql.append("\n  select count(*) into v_rs from waresaleman where accid=" + Accid + " and noteno='" + Noteno + "'; "); // 销售人员数
		// 找第一个销售人
		strSql.append("\n  begin");
		strSql.append("\n    select epid into v_handmanid from waresaleman where accid=" + Accid + " and noteno='" + Noteno + "' and rownum=1;");
		strSql.append("\n  EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n    v_handmanid:=0;");
		strSql.append("\n  end;");

		strSql.append("\n  select nvl(sum(curr),0),nvl(sum(amount),0) into v_totalcurr,v_totalamt from wareoutm  where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n  select nvl(sum(curr),0) into v_totalcost from wareoutcost  where accid=" + Accid + " and noteno='" + Noteno + "';");
		if (Ntid == 2)
			strSql.append("\n  v_totalcurr:=v_totalcurr- v_totalcost;");
		else
			strSql.append("\n  v_totalcurr:=v_totalcurr+ v_totalcost;");
		if (Statetag != null && Statetag == 1) {
			// ywtag:0=店铺零售，1=商场零售，2=线上，3=批发销售
			strSql.append("\n  select count(*) into v_count from DEALRECORD where accid=" + Accid + " and ywnoteno='" + Noteno + "' and ywtag=3 and statetag=0 and rownum=1;");
			strSql.append("\n  if v_count>0 then ");
			strSql.append("\n     v_retcs:='0第三方交易记录异常，请检查！'; ");
			strSql.append("\n     goto exit; ");
			strSql.append("\n  end if;");
			// if (Paynoteno_zfb != null && Paynoteno_zfb.length() > 0) {//
			// payfs:0=支付宝，1=微信,2=拉卡拉,3=盛付通
			// strSql.append("\n begin ");
			// strSql.append("\n select curr into v_zfbcurr from DEALRECORD
			// where accid=" + Accid + " and noteno='" + Paynoteno_zfb + " and
			// payfs=0 and statetag=1;");
			// strSql.append("\n EXCEPTION WHEN NO_DATA_FOUND THEN ");
			// strSql.append("\n v_retcs:='0支付宝交易订单记录异常！'; ");
			// strSql.append("\n goto exit; ");
			// strSql.append("\n end; ");
			//
			// }
			// if (Paynoteno_wx != null && Paynoteno_wx.length() > 0) // 微信交易记录
			// //payfs:0=支付宝，1=微信,2=拉卡拉,3=盛付通
			// {
			// strSql.append("\n begin ");
			// strSql.append("\n select curr into v_zfbcurr from DEALRECORD
			// where accid=" + Accid + " and noteno='" + Paynoteno_wx + " and
			// payfs=1 and statetag=1;");
			// strSql.append("\n EXCEPTION WHEN NO_DATA_FOUND THEN ");
			// strSql.append("\n v_retcs:= '0微信交易订单记录异常！'; ");
			// strSql.append("\n goto exit; ");
			// strSql.append("\n end; ");
			// }
			// if (Paynoteno_lkl != null && Paynoteno_lkl.length() > 0) //
			// 拉卡拉交易记录//payfs:0=支付宝，1=微信,2=拉卡拉,3=盛付通
			// {
			// strSql.append("\n begin ");
			// strSql.append("\n select curr into v_zfbcurr from DEALRECORD
			// where accid=" + Accid + " and noteno='" + Paynoteno_lkl + " and
			// payfs=2 and statetag=1;");
			// strSql.append("\n EXCEPTION WHEN NO_DATA_FOUND THEN ");
			// strSql.append("\n v_retcs:='0拉卡拉交易订单记录异常！'; ");
			// strSql.append("\n goto exit; ");
			// strSql.append("\n end; ");
			// }
			// if (Paynoteno_sft != null && Paynoteno_sft.length() > 0) //
			// 盛付通交易记录//payfs:0=支付宝，1=微信,2=拉卡拉,3=盛付通
			// {
			// strSql.append("\n begin ");
			// strSql.append("\n select curr into v_zfbcurr from DEALRECORD
			// where accid=" + Accid + " and noteno='" + Paynoteno_sft + " and
			// payfs=3 and statetag=1;");
			// strSql.append("\n EXCEPTION WHEN NO_DATA_FOUND THEN ");
			// strSql.append("\n v_retcs:='0盛付通交易订单记录异常！'; ");
			// strSql.append("\n goto exit; ");
			// strSql.append("\n end; ");
			// }

			Freecurr = (double) 0;
			strSql.append("\n  if v_totalcurr<>" + Totalcurr + " then ");
			strSql.append("\n     update wareouth set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno='" + Noteno + "';");
			// qry += "\n select '0合计金额'||to_char(v_totalcurr)||'异常,请重新结算！' into
			// :retcs from dual; ";
			strSql.append("\n     v_retcs:='0合计金额'||to_char(v_totalcurr)||'异常,请重新结算！" + Totalcurr + "'; ");
			strSql.append("\n     goto exit; ");
			strSql.append("\n  end if; ");
			if (Ntid == 1) // 批发收款 //批发出库提交要填入收款方式
			{
				if (Paycurr0 != null && Paycurr0 != 0)// 有折让
				{
					Freecurr = Paycurr0;
					if (Totalcurr >= 0) {
						// 折让金额不能大于结算金额
						if (Freecurr > Totalcurr || Freecurr < 0) {
							errmess = "折让金额不允许大于应收金额或小于零！";
							return 0;
						}
					} else // 如果批发单应收金额为负，折扣金额不允许大于0，小于应收金额
					{
						if (Freecurr < Totalcurr) {
							errmess = "当前单据需要退款，折让金额不允许小于应退金额！";
							return 0;
						}
					}
				}
				strSql.append("\n  v_freecurr:=" + Freecurr + ";");
				strSql.append("\n  v_zpaycurr:=" + Zpaycurr + ";");
				// 判断欠款情况
				strSql.append("\n  select creditcurr,creditok into v_creditcurr,v_creditok from customer where custid=" + Custid + "; ");
				strSql.append("\n  if v_creditok=1 then ");// 启用信用控制
				// strSql.append("\n select
				// f_getincomebal(to_char(sysdate,'yyyy-mm-dd')," + Accid + ","
				// + Custid + ") into v_balcurr from dual;");
				strSql.append("\n    select f_getincomebal2x(to_char(sysdate,'yyyy-mm-dd')," + Accid + "," + Custid + "," + houseid + ",'" + Calcdate + "') into v_balcurr from dual;");
				strSql.append("\n    if v_totalcurr+v_balcurr-v_zpaycurr-v_freecurr>v_creditcurr and " + Zpaycurr + "<v_totalcurr+v_balcurr then ");
				strSql.append("\n       v_retcs:=  '0客户欠款已超信用额度，不允许发货！'; ");
				strSql.append("\n       goto exit; ");
				strSql.append("\n    end if; ");
				strSql.append("\n  end if; ");

				if (jsonObject.has("paylist")) {
					JSONArray jsonArray = jsonObject.getJSONArray("paylist");

					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObject2 = jsonArray.getJSONObject(i);
						Float paycurr = Float.parseFloat(jsonObject2.getString("paycurr"));
						Long payid = Long.parseLong(jsonObject2.getString("payid"));
						strSql.append("\n   select 'SK'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from incomecurr");
						strSql.append("\n   where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'SK'||To_Char(SYSDATE,'yyyymmdd');");

						strSql.append("\n   insert into incomecurr (id, accid,custid,houseid,noteno,notedate,handmanid,payid,curr,remark,handno,statetag,operant,checkman,lastop,ynoteno,lastdate)");
						strSql.append("\n   values (INCOMECURR_ID.NEXTVAL," + Accid + "," + Custid + "," + Houseid + ",v_noteno,v_notedate,v_handmanid," + payid);
						strSql.append("\n   ," + paycurr + ",'','',1,'" + Operant + "','','" + Operant + "','" + Noteno + "',sysdate); ");
						// notetype:0销售(退库) 1物料销售（退库）
						// fs:0销售收款勾销售单,1销售收款勾退货单,2销售退款勾退货单
						if (autogd == 1) {
							strSql.append("\n insert into incomelink (id,accid,notetype,noteno,pnoteno,fs,curr,lastop)");
							strSql.append("\n values (incomelink_id.nextval," + Accid + ",0,'" + Noteno + "',v_noteno,0," + paycurr + ",'" + Operant + "');");
						}
						strSql.append("\n   insert into LOGRECORD (id,accid,progname,remark,lastop)");
						strSql.append("\n   values (LOGRECORD_id.nextval," + Accid + ",'批发销售收款','【增加记录】销售单号:" + Noteno + ";单据号:'||v_noteno||';金额:" + paycurr + "','" + Operant + "');");
						Gdcurr += paycurr;
					}
				}

				if (Freecurr > 0) {
					strSql.append("\n  select 'SZ'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from INCOMECURR ");
					strSql.append("   where accid=" + Accid + "  and LENGTH(noteno)=15 and substr(noteno,1,10)= 'SZ'||To_Char(SYSDATE,'yyyymmdd');");

					strSql.append("\n  insert into incomecurr (id, accid,custid,houseid,noteno,notedate,handmanid,payid,curr,remark,handno,statetag,operant,checkman,lastop,ynoteno,lastdate)");
					strSql.append("\n  values (INCOMECURR_ID.NEXTVAL," + Accid + "," + Custid + "," + Houseid + ",v_noteno,v_notedate,v_handmanid,0");
					strSql.append("\n ," + Freecurr + ",'','',1,'" + Operant + "','','" + Operant + "','" + Noteno + "',sysdate); ");
					// 生成勾单记录
					// notetype:0销售(退库) 1物料销售（退库）
					// fs:0销售收款勾销售单1售收款勾退货单2销售退款勾退货单
					if (autogd == 1) {

						strSql.append("\n  insert into incomelink (id,accid,notetype,noteno,pnoteno,fs,curr,lastop)");
						strSql.append("\n  values (incomelink_id.nextval," + Accid + ",0,'" + Noteno + "',v_noteno,0," + Freecurr + ",'" + Operant + "');");
					}
					strSql.append("\n    insert into LOGRECORD (id,accid,progname,remark,lastop)");
					strSql.append("\n    values (LOGRECORD_id.nextval," + Accid + ",'批发销售折让','【增加记录】销售单号:" + Noteno + ";单据号:'||v_noteno||';金额:" + Freecurr + "','" + Operant + "');");

					Gdcurr += Freecurr;
				}
				/*
				 * if (model.totalcurr < 0 && (model.zpaycurr < model.totalcurr
				 * - freecurr || model.zpaycurr > 0)) { WriteResult("0",
				 * "当前单据需要退款，退款金额请输负数，且不能大于应退金额！"); return; }
				 */
			} else if (Ntid == 2) // 批发退款 //批发出库提交要填入收款方式
			{

				if (jsonObject.has("paylist")) {
					JSONArray jsonArray = jsonObject.getJSONArray("paylist");

					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObject2 = jsonArray.getJSONObject(i);
						Float paycurr = Float.parseFloat(jsonObject2.getString("paycurr"));
						Long payid = Long.parseLong(jsonObject2.getString("payid"));

						strSql.append(" select 'SK'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from incomecurr");
						strSql.append(" where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'SK'||To_Char(SYSDATE,'yyyymmdd');");

						strSql.append("\n insert into incomecurr (id, accid,custid,houseid,noteno,notedate,handmanid,payid,curr,remark,handno,statetag,operant,checkman,lastop,ynoteno,lastdate)");
						strSql.append("\n values (INCOMECURR_ID.NEXTVAL," + Accid + "," + Custid + "," + Houseid + ",v_noteno,v_notedate,v_handmanid," + payid);
						strSql.append("\n ,-" + paycurr + ",'','',1,'" + Operant + "','','" + Operant + "','" + Noteno + "',sysdate); ");
						// notetype:0销售(退库) 1物料销售（退库）
						// fs:0销售收款勾销售单1销售收款勾退货单2销售退款勾退货单
						// 生成勾单记录
						if (autogd == 1) {
							strSql.append("\n insert into incomelink (id,accid,notetype,noteno,pnoteno,fs,curr,lastop)");
							strSql.append("\n values (incomelink_id.nextval," + Accid + ",0,'" + Noteno + "',v_noteno,1," + paycurr + ",'" + Operant + "');");
						}
						strSql.append(" insert into LOGRECORD (id,accid,progname,remark,lastop)");
						strSql.append(" values (LOGRECORD_id.nextval," + Accid + ",'批发销售退款','【增加记录】销售退货单号:" + Noteno + ";单据号:'||v_noteno||';金额:" + paycurr + "','" + Operant + "');");

					}
				}
				if (Paycurr0 != null && Paycurr0 != 0)// 有折让
				{
					Freecurr = Paycurr0;
					// 折让金额不能大于结算金额
					if (Freecurr > Totalcurr || Freecurr < 0) {
						errmess = "折让金额不允许大于应退金额或小于零！";
						return 0;

					}

					strSql.append("\n  select 'SZ'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from INCOMECURR ");
					strSql.append("   where accid=" + Accid + "  and LENGTH(noteno)=15 and substr(noteno,1,10)= 'SZ'||To_Char(SYSDATE,'yyyymmdd');");

					strSql.append("\n  insert into incomecurr (id, accid,custid,houseid,noteno,notedate,handmanid,payid,curr,remark,handno,statetag,operant,checkman,lastop,ynoteno,lastdate)");
					strSql.append("\n  values (INCOMECURR_ID.NEXTVAL," + Accid + "," + Custid + "," + Houseid + ",v_noteno,v_notedate,v_handmanid,0");
					strSql.append("\n ,-" + Freecurr + ",'','',1,'" + Operant + "','','" + Operant + "','" + Noteno + "',sysdate); ");
					// notetype:0销售(退库) 1物料销售（退库）
					// fs:0销售收款勾销售单1销售收款勾退货单2销售退款勾退货单
					if (autogd == 1) {
						strSql.append("\n insert into incomelink (id,accid,notetype,noteno,pnoteno,fs,curr,lastop)");
						strSql.append("\n values (incomelink_id.nextval," + Accid + ",0,'" + Noteno + "',v_noteno,1," + (-Freecurr) + ",'" + Operant + "');");
					}
					strSql.append("\n    insert into LOGRECORD (id,accid,progname,remark,lastop)");
					strSql.append("\n    values (LOGRECORD_id.nextval," + Accid + ",'批发退款折让','【增加记录】销售退货单号:" + Noteno + ";单据号:'||v_noteno||';金额:" + Freecurr + "','" + Operant + "');");
				}
				if (Zpaycurr < 0) {
					errmess = "退款金额必须大于零！";
					return 0;

				}
				// 只能少退
				/*
				 * if (model.zpaycurr > model.totalcurr - freecurr ||
				 * model.zpaycurr < 0) { WriteResult("0",
				 * "当前单据需要退款，退款金额不允许大于应退金额！"); return; }
				 */
			}
		}
		strSql.append("\n   update wareouth set ");
		if (Custid != null) {
			strSql.append("custid=" + Custid + ",");
		}
		if (Guestid != null) {
			strSql.append("guestid=" + Guestid + ",");
		}
		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
		}
		if (Handno != null) {
			strSql.append("handno='" + Handno.toUpperCase() + "',");
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
		// strSql.append("operant='" + Operant + "',");
		// }
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Orderno != null) {
			strSql.append("orderno='" + Orderno + "',");
			//			if (!Func.subString(Orderno, 1, 2).equals("KO")) {
			//				LogUtils.LogErrWrite("updatewareouth123", Orderno);
			//			}
		}
		// if (Rs != null) {
		// strSql.append("rs=" + Rs + ",");
		// }
		if (Checkcurr != null) {
			strSql.append("checkcurr=" + Checkcurr + ",");

		}
		if (Changecurr != null) {
			strSql.append("changecurr=" + Changecurr + ",");
		}
		if (Freecurr != null) {
			strSql.append("freecurr=" + Freecurr + ",");
		}
		if (Zpaycurr != null) {
			strSql.append("zpaycurr=" + Zpaycurr + ",");
		}
		if (Ywly != null) {
			strSql.append("ywly=" + Ywly + ",");
		}
		if (Dptid != null) {
			strSql.append("dptid=" + Dptid + ",");
		}
		// if (Paylist != null) {
		// strSql.append("paylist='" + Paylist + "',");
		// }
		// if (Onhouseid != null) {
		// strSql.append("onhouseid=" + Onhouseid + ",");
		// }
		if (Wladd != null) {
			strSql.append("wladd='" + Wladd + "',");
		}
		// if (Cleartag != null) {
		// strSql.append("cleartag=" + Cleartag + ",");
		// }
		// if (Totalcost != null) {
		// strSql.append("totalcost='" + Totalcost + "',");
		// }
		if (Tohouseid != null) {
			strSql.append("tohouseid=" + Tohouseid + ",");
		}
		// if (Messid != null) {
		// strSql.append("messid='" + Messid + "',");
		// }
		if (Yhjcurr != null) {
			strSql.append("yhjcurr=" + Yhjcurr + ",");
		}
		if (Buy_accid != null) {
			strSql.append("buy_accid=" + Buy_accid + ",");
		}
		if (Buy_noteno != null) {
			strSql.append("buy_noteno='" + Buy_noteno + "',");
		}
		if (Paynoteno_zfb != null) {
			strSql.append("paynoteno_zfb='" + Paynoteno_zfb + "',");
		}
		if (Paynoteno_wx != null) {
			strSql.append("paynoteno_wx='" + Paynoteno_wx + "',");
		}
		if (Fhtag != null) {
			strSql.append("fhtag=" + Fhtag + ",");
		}
		if (Ywnoteno != null) {
			strSql.append("ywnoteno='" + Ywnoteno + "',");
		}
		if (Paynoteno_lkl != null) {
			strSql.append("paynoteno_lkl='" + Paynoteno_lkl + "',");
		}
		// if (changedatebj == 1 && Notedatestr.length() > 0) {
		// strSql.append("notedate=to_date('" + Notedatestr +
		// "','yyyy-mm-ddhh24:mi:ss'),");
		// } else {
		strSql.append("notedate=v_notedate,");
		// }
		if (Statetag != null && Statetag == 1)// 如果是提交，记录勾单金额
		{
			strSql.append("gdcurr=" + Gdcurr + ",");
			// strSql.append("checkman='" + Operant + "',");
			strSql.append("cashier='" + Operant + "',");
			// bbbb
		}

		strSql.append("\n  rs=v_rs,totalcurr=v_totalcurr,totalamt=v_totalamt,lastdate=sysdate,bj=0");
		strSql.append("\n  where accid=" + Accid + " and noteno='" + Noteno + "';");
		if (Statetag != null && Statetag == 1)// 提交时刷新当前单据的总数量与总金额
		{
			strSql.append("\n      commit;");
			if (Ntid == 1) // 批发出库要//记录最近售价
			{
				// 计算订单完成情况
				strSql.append("\n   select orderno,ywnoteno into v_orderno,v_ywnoteno from wareouth where accid=" + Accid + " and noteno='" + Noteno + "';");
				strSql.append("\n   if length(v_orderno)>0 then ");
				strSql.append("\n      p_calccustorderover(" + Accid + ",v_orderno,1);");
				strSql.append("\n   end if ; ");

				strSql.append("\n  if length(v_ywnoteno)>0 then ");// 配货单标志改为出库状态
				strSql.append("\n     update warepeih set statetag=5 where accid=" + Accid + " and noteno=v_ywnoteno;");
				// statetag:0=草稿,1=提交,2=审核,3=配货中，4=配货完，5=已转出库单
				strSql.append("\n  end if;");

			}
			if (Ntid == 2) // 批发退库处理退货申请单标志
			{
				strSql.append("\n   select orderno into v_orderno from wareouth where accid=" + Accid + " and noteno='" + Noteno + "';");
				strSql.append("\n   if length(v_orderno)>0 then ");
				strSql.append("\n      update refundouth set thtag=1 where accid=" + Accid + " and noteno=v_orderno;");
				strSql.append("\n   end if ; ");
			}
			strSql.append("\n  p_wareoutpaylist(" + Accid + ",'" + Noteno + "'); ");
		}
		strSql.append("\n   commit;");
		strSql.append("\n   p_wareoutsetarea(" + Accid + ",'" + Noteno + "'); "); // 填入区位
		strSql.append("\n   v_retcs:='1操作成功！';");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		strSql.append("\n end;");
		// System.out.println(strSql.toString());
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
	public DataSet GetList1(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(",f_getwareoutsalemanname(a.accid,a.noteno) as salemanlist");
		strSql.append(",f_wareoutcostlist(a.accid,a.noteno) as xsfylist");
		strSql.append("\n FROM wareouth a ");
		strSql.append("\n left outer join customer b on a.custid=b.custid ");
		strSql.append("\n left outer join warehouse c on a.houseid=c.houseid ");
		strSql.append("\n left outer join department e on a.dptid=e.dptid ");
		strSql.append("\n left outer join carryline f on a.carryid=f.carryid ");
		strSql.append("\n left outer join carryline g on b.carryid=g.carryid ");
		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		// System.out.println(strSql.toString());
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获得数据列表
	public DataSet GetList0(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		// strSql.append(",case To_Char(a.notedate,'yyyy-mm-dd') when
		// To_Char(SYSDATE,'yyyy-mm-dd') then '1' else '0' end as istoday");
		strSql.append(",f_getwareoutsalemanname(a.accid,a.noteno) as salemanlist");
		// strSql.append(",f_wareoutcostlist(a.accid,a.noteno) as xsfylist");
		strSql.append(" FROM wareouth a ");
		// strSql.append(" left outer join customer b on a.custid=b.custid ");
		strSql.append(" left outer join warehouse c on a.houseid=c.houseid ");
		strSql.append(" left outer join guestvip b on a.guestid=b.guestid ");
		strSql.append(" left outer join guesttype d on b.vtid=d.vtid ");
		strSql.append(" left outer join department e on a.dptid=e.dptid ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取批发分页数据
	public Table GetTable1(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		if (Sumtag == 1) // 取批发结算方式合计
		{
			strSql.append("  select a1.payid,b1.payno,b1.payname,sum(a1.curr) as curr from incomecurr a1");
			strSql.append("  join payway b1 on a1.accid=b1.accid and a1.payid=b1.payid");
			strSql.append("  where exists (select 1 FROM wareouth a");
			strSql.append("  left outer join warehouse c on a.houseid=c.houseid ");
			strSql.append("  left outer join customer b on a.custid=b.custid ");
			strSql.append("  where a1.accid=a.accid and a1.ynoteno=a.noteno");
			if (strWhere.length() > 0) {
				strSql.append(" and " + strWhere);
			}
			strSql.append(" ) ");
			strSql.append("  group by a1.payid,b1.payno,b1.payname order by b1.payno,a1.payid");
			Table tb = DbHelperSQL.Query(strSql.toString()).getTable(1);
			int count = tb.getRowCount();
			String sumlist = "";
			String fh = "";
			for (int i = 0; i < count; i++) {
				sumlist += fh + tb.getRow(i).get("PAYNAME").toString() + ":" + tb.getRow(i).get("CURR").toString();
				fh = ",";
			}
			qp.setTotalString(sumlist);
			// sumlist = strSql.ToString();
		}

		qp.setSumString(" nvl(sum(totalamt),0) as totalamount,nvl(sum(totalcurr),0) as totalcurr");
		qp.setCalcfield("f_getwareoutsalemanname(accid,noteno) as salemanlist");

		strSql.setLength(0);
		strSql.append("select " + fieldlist);
		// strSql.append(",f_getwareoutsalemanname(a.accid,a.noteno) as
		// salemanlist");
		if (Xtbj == 1)
			strSql.append(",d.housename as tohousename ");// 上下衣供应商
		strSql.append("\n FROM wareouth a ");
		strSql.append("\n left outer join customer b on a.custid=b.custid ");
		strSql.append("\n left outer join warehouse c on a.houseid=c.houseid ");
		strSql.append("\n left outer join department e on a.dptid=e.dptid ");
		// strSql.append("\n left outer join carryline f on a.carryid=f.carryid
		// ");
		strSql.append("\n left outer join carryline f on a.carryid=f.carryid ");
		strSql.append("\n left outer join carryline g on b.carryid=g.carryid ");
		if (Xtbj == 1)
			strSql.append("\n left outer join warehouse d on a.tohouseid=d.houseid "); // 上下衣供应商

		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		// qp.setSumString("nvl(sum(totalamt),0) as
		// totalamt,nvl(sum(totalcurr),0) as totalcurr");

		return DbHelperSQL.GetTable(qp);
	}

	// 获取零售分页数据
	public Table GetTable0(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();

		if (Sumtag == 1) // 取零售结算方式合计
		{
			strSql.append("  select a1.payid,b1.payname,sum(curr) as curr from waresalepay a1");
			strSql.append("  join payway b1 on a1.accid=b1.accid and a1.payid=b1.payid");
			strSql.append("  where exists (select 1 FROM wareouth a");
			strSql.append("  left outer join warehouse c on a.houseid=c.houseid ");
			strSql.append("  left outer join guestvip b on a.guestid=b.guestid ");
			strSql.append("  left outer join guesttype d on b.vtid=d.vtid ");
			strSql.append("  where a1.noteno=a.noteno");
			if (strWhere.length() > 0) {
				strSql.append(" and " + strWhere);
			}
			strSql.append(" ) ");
			strSql.append("  group by a1.payid,b1.payname");
			Table tb = DbHelperSQL.Query(strSql.toString()).getTable(1);
			int count = tb.getRowCount();
			String fh = "";
			String sumlist = "";
			for (int i = 0; i < count; i++) {
				sumlist += fh + tb.getRow(i).get("PAYNAME").toString() + ":" + tb.getRow(i).get("CURR").toString();
				fh = ",";
			}
			qp.setTotalString(sumlist);
		}

		qp.setSumString("nvl(sum(totalamt),0) as totalamount,nvl(sum(totalcurr),0) as totalcurr");
		qp.setCalcfield("f_getwareoutsalemanname(accid,noteno) as salemanlist");

		strSql.setLength(0);
		strSql.append("select " + fieldlist);
		// strSql.append(",f_getwareoutsalemanname(a.accid,a.noteno) as
		// salemanlist");
		strSql.append(" FROM wareouth a ");
		// strSql.append(" left outer join customer b on a.custid=b.custid ");
		strSql.append(" left outer join warehouse c on a.houseid=c.houseid ");
		strSql.append(" left outer join guestvip b on a.guestid=b.guestid ");
		strSql.append(" left outer join guesttype d on b.vtid=d.vtid ");
		strSql.append(" left outer join department e on a.dptid=e.dptid ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取批发分页数据
	public Table GetTable(QueryParam qp) {

		return DbHelperSQL.GetTable(qp);
	}

	// 获取批发分页数据 勾单记录
	public Table GetTableLink(QueryParam qp) {
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

		String qry = " select a.noteno,a.notedate,a.remark,a.curr,c.curr as curr0,a.payid,b.payname,a.operant ";
		qry += "\n from incomecurr a";
		qry += "\n left outer join payway b on a.payid=b.payid";
		qry += "\n join incomelink c on a.accid=c.accid and a.noteno=c.pnoteno";
		qry += "\n where a.statetag=1 and a.accid=" + Accid;
		// incomelink->notetype:0销售(退库) 1物料销售（退库）
		qry += "\n and c.noteno='" + Noteno + "'";
		qry += "\n and c.notetype=0 ";

		qp.setQueryString(qry);
		qp.setSumString("nvl(sum(curr),0) as totalcurr,nvl(sum(curr0),0) as totalcurr0");
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页商品数据
	// public Table GetTablemx(QueryParam qp, String strWhere, String fieldlist)
	// {
	// public Table GetTableHistory(QueryParam qp) {
	// return DbHelperSQL.GetTable(qp);
	// }

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from wareouth");
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

	// 载入指定客户订单明细记录到批发出库
	public int doOrdertoOut() {
		if (Orderno.length() <= 0) {
			errmess = "orderno不是一个有效值！";
			return 0;
		}
		if (!Func.subString(Orderno, 1, 2).equals("KO")) {
			errmess = Orderno + "不是一个有效的客户订单！";
			LogUtils.LogErrWrite("doOrdertoOut", Orderno);
			return 0;
		}

		String qry = "declare ";
		qry += "\n   v_saleid number(10); ";
		qry += "\n   v_amount number; ";
		qry += "\n   v_totalamt number; ";
		qry += "\n   v_totalcurr number; ";
		qry += "\n   v_overbj number(1); ";
		qry += "\n   v_retcs varchar2(200); ";
		qry += "\n   v_remark varchar2(200); ";
		qry += "\n begin ";

		qry += "\n   begin";
		qry += "\n     select overbj,remark into v_overbj,v_remark from custorderh where accid=" + Accid + " and noteno='" + Orderno + "' and statetag=1;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n     v_retcs:='0订单未找到！';";
		qry += "\n     goto exit;";
		qry += "\n   end;";
		qry += "\n   if v_overbj=1 then";
		qry += "\n      v_retcs:='0订单已完成！';";
		qry += "\n      goto exit;";
		qry += "\n   end if;";

		qry += "\n   v_saleid:=1;";
		qry += "\n   delete from wareoutm where  accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   declare cursor cur_data is ";
		qry += "\n   select a.wareid,a.colorid,a.sizeid,a.amount-a.factamt as amount,a.price0,a.discount,a.price from custorderm a";
		qry += "\n   where a.accid=" + Accid + " and a.noteno='" + Orderno + "' and a.amount>a.factamt;";
		// if (ordernolist != null && ordernolist != "") qry += "\n and " +
		// myCondition("a.noteno", orderno, 1);
		// if (orderno != null && orderno != "") qry += "\n and a.noteno='" +
		// orderno + "'";
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
		qry += "\n        insert into wareoutm (id,accid,noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,curr,remark0,lastdate)";
		qry += "\n        values ( wareoutm_id.nextval," + Accid + ",'" + Noteno + "',v_row.wareid,v_row.colorid,v_row.sizeid,v_saleid,v_amount,v_row.price0,v_row.discount,v_row.price,v_amount*v_row.price,'',sysdate); ";
		qry += "\n     end if;";
		qry += "\n   end loop;";
		qry += "\n end;";

		qry += "\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   update wareouth set orderno='" + Orderno + "',totalcurr=v_totalcurr,totalamt=v_totalamt,remark=v_remark";
		qry += "\n   where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   commit;";
		qry += "\n   v_retcs:='1操作完成！';";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		// qry += "\n p_calcallotorderover(" + Accid + ",'" + Orderno + "');";
		// // 计算订单完成情况
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

	// 零售出库，批发出库，批发退货撤单
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
		qry += "\n   v_guestid number(10); ";
		qry += "\n   v_progname varchar2(40); ";
		qry += "\n   v_cleartag number(1);";
		qry += "\n   v_cent number(10); ";
		qry += "\n   v_paycurrv number(12,2); ";
		//		qry += "\n   v_curr number(16,2); ";
		qry += "\n   v_count number(10); ";
		qry += "\n   v_paynoteno_zfb varchar2(30);v_paynoteno_wx varchar2(30);v_paynoteno_lkl varchar2(30);";
		qry += "\n   v_checkman varchar2(20); ";
		qry += "\n   v_dosql clob; ";
		qry += "\n begin ";
		qry += "\n   v_dosql:='';";

		qry += "\n   begin";
		qry += "\n     select to_char(notedate,'yyyy-mm-dd'),ntid,guestid,cent,orderno,cleartag,paynoteno_zfb,paynoteno_wx,paynoteno_lkl,checkman";
		qry += "\n     into v_notedate,v_ntid,v_guestid,v_cent,v_orderno,v_cleartag,v_paynoteno_zfb,v_paynoteno_wx,v_paynoteno_lkl,v_checkman";
		qry += "\n     from wareouth where accid=" + Accid + " and noteno='" + Noteno + "'; ";
		// qry += "\n select to_char(notedate,'yyyy-mm-dd') ,ntid,cleartag ";
		// qry += "\n into v_notedate,v_ntid,v_cleartag";
		// qry += "\n from wareinh where accid=" + Accid + " and noteno='" +
		// Noteno + "' and statetag=1; ";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_retcs:='0未找到单据!';";
		qry += "\n     goto exit;";
		qry += "\n   end;";
		// qry += "\n if length(v_checkman)>0 then ";// 如果收款单关联了销售单，重新刷新销售的的结算方式
		// qry += "\n v_retcs:= '0已审核的单据不允许撤单！'; ";
		// qry += "\n goto exit; ";
		// qry += "\n end if; ";

		qry += "\n   if v_notedate<='" + Calcdate + "' then ";
		qry += "\n      v_retcs:= '0系统登账日" + Calcdate + "及之前的单据不允许撤单！'; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if; ";

		// qry += "\n select
		// to_char(notedate,'yyyy-mm-dd'),ntid,guestid,cent,orderno,cleartag,paynoteno_zfb,paynoteno_wx,paynoteno_lkl";
		// qry += "\n into
		// v_notedate,v_ntid,v_guestid,v_cent,v_orderno,v_cleartag,v_paynoteno_zfb,v_paynoteno_wx,v_paynoteno_lkl
		// from wareouth where accid=" +Accid + " and noteno='" + Noteno + "';
		// ";
		// if (changedatebj == 0) // 不允许更改单据日期
		// {
		// qry += "\n if v_notedate<to_char(sysdate,'yyyy-mm-dd') then ";
		// qry += "\n v_retcs:= '0今日之前的单据不允许撤单！' ; ";
		// qry += "\n goto exit; ";
		// qry += "\n end if; ";
		// }

		qry += "\n   select count(*) into v_count from incomelink where accid=" + Accid + " and notetype=0 and noteno='" + Noteno + "';";
		qry += "\n   if v_count>0 then ";
		qry += "\n      v_retcs:='0销售单有收款勾单记录，不允许撤单！';";
		qry += "\n      goto exit;";
		qry += "\n   end if;";

		qry += "\n   if v_cleartag=1  then ";
		qry += "\n      v_retcs:= '0当前单据已被冲单，不允许撤单！'; ";
		qry += "\n      goto exit; ";
		qry += "\n   elsif v_cleartag=2  then ";
		qry += "\n      v_retcs:= '0当前单据是系统生成的冲账单据，不允许撤单！' ; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if; ";
		// ywtag:0=店铺零售，1=商场零售，2=线上，3=批发销售
		qry += "\n   select count(*) into v_count from DEALRECORD where accid=" + Accid + " and ywnoteno='" + Noteno + "' and (ywtag=0 or ywtag=3)  and rownum=1;";
		qry += "\n   if v_count>0 then ";

		// qry += "\n if length(v_paynoteno_zfb)>0 or length(v_paynoteno_wx)>0
		// or length(v_paynoteno_lkl)>0 then ";
		qry += "\n      v_retcs:= '0当前单据有第三方支付记录，不允许撤单！' ; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if;";

		// qry += "\n select ntid,guestid,cent,orderno into
		// v_ntid,v_guestid,v_cent,v_orderno from wareouth where accid=" + accid
		// + " and noteno='" + noteno + "' and id=" + id + "; ";
		qry += "\n   if v_ntid=0 then "; // 零售

		qry += "\n     v_progname:='店铺零售'; ";
		qry += "\n     begin";
		qry += "\n       select a.curr into v_paycurrv from waresalepay a join payway b on a.payid=b.payid where a.accid=" + Accid + " and a.noteno='" + Noteno + "' and b.payno='V';";
		qry += "\n     EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n       v_paycurrv:=0;";
		qry += "\n     end;";
		//判断有没有登账记录
		qry += "\n     select count(*) into v_count  from waresalepay a join bookdetail b on a.bd_id=b.id";
		qry += "\n     where a.accid=" + Accid + " and a.noteno='" + Noteno + "'";
		qry += "\n     and a.accid=b.accid and a.bd_id>0 and length(b.checkman)>0;";
		qry += "\n     if v_count>0 then ";
		qry += "\n        v_retcs:= '0当前单据收款记录已登账审核，不允许撤单！' ; ";
		qry += "\n        goto exit; ";
		qry += "\n     end if;";
		//处理登账记录begin
		qry += "\n     declare cursor cur_data is";
		qry += "\n       select b.notedate,b.bookid,b.id from waresalepay a join bookdetail b on a.bd_id=b.id";
		qry += "\n       where a.accid=" + Accid + " and a.noteno='" + Noteno + "'";
		qry += "\n       and a.accid=b.accid and a.bd_id>0 ;";
		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n     begin";
		qry += "\n       v_dosql:='';";
		qry += "\n       for v_row in cur_data loop";
		qry += "\n           delete from bookdetail where accid=" + Accid + " and id=v_row.id;";
		qry += "\n           v_dosql:=v_dosql||'  p_calcbookbalcurr('||v_row.bookid||','''||v_row.notedate||''');';";
		qry += "\n       end loop;";
		qry += "\n     end;";
		qry += "\n     if length(v_dosql)>0 then";
		qry += "\n        v_dosql:='begin '||v_dosql||' end;';";
		qry += "\n     end if;";
		//处理登账记录end
		qry += "\n     delete from waresalepay where accid=" + Accid + " and noteno='" + Noteno + "'; ";

		qry += "\n     if v_guestid>0 and ( v_cent<>0 or v_paycurrv>0) then";
		qry += "\n        delete from guestcent where guestid=v_guestid and xsnoteno='" + Noteno + "'; "; // 删除消费积分
		qry += "\n        delete from guestcurr where guestid=v_guestid and xsnoteno='" + Noteno + "'; "; // 删除消费积分
		qry += "\n        update guestvip set balcent=nvl((select sum(case fs when 0 then cent else -cent end) from guestcent where guestvip.guestid=guestcent.guestid),0)";
		qry += "\n          ,balcurr=nvl((select sum(case fs when 0 then curr else -curr end) from guestcurr where guestvip.guestid=guestcurr.guestid),0)";
		qry += "\n        where guestid=v_guestid; ";
		qry += "\n     end if; ";
		qry += "\n  elsif v_ntid=1 then "; // 批发出库
		qry += "\n     v_progname:='批发开票'; ";
		qry += "\n     update incomecurr set statetag=2 where accid=" + Accid + " and ynoteno='" + Noteno + "' and statetag=1 and (checkman is null or length(checkman)<=0); ";
		qry += "\n  elsif v_ntid=2 then "; // 批发退库要把申请单退单标志清除
		qry += "\n     v_progname:='批发退货'; ";
		qry += "\n     update incomecurr set statetag=2 where accid=" + Accid + " and ynoteno='" + Noteno + "' and statetag=1 and (checkman is null or length(checkman)<=0) ; ";
		qry += "\n  end if; ";
		qry += "\n  update wareouth set statetag=0,cent=0,checkman='',cashier='',paylist='',lastdate=sysdate where accid=" + Accid + "  and noteno='" + Noteno + "'; ";

		qry += "\n   v_retcs:= '1操作成功！';  ";

		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",v_progname,'【撤单】单据号:" + Noteno + "','" + Operant + "');";

		qry += "\n   commit;";

		qry += "\n   if length(v_dosql)>0 then"; //重新计算账本
		qry += "\n      execute immediate v_dosql;";
		qry += "\n   end if;";
		qry += "\n   if length(v_orderno)>0 and v_ntid=1 then";// 如果采购入库单关联了订单，计算订单完成情况
		qry += "\n      p_calccustorderover(" + Accid + ",v_orderno,0);"; // 计算订单完成情况
		qry += "\n   end if;";
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

	// 取零售、批发、退货单结算信息
	public int doGetcheck() {
		if (Func.isNull(Noteno)) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n     v_nowdate varchar2(20);";
		qry += "\n     v_noteno varchar2(20);";
		qry += "\n     v_accid number;";
		qry += "\n     v_totalcurr number(16,2);";
		qry += "\n     v_totalcost number(16,2);";
		qry += "\n     v_creditcurr number(16,2);";
		qry += "\n     v_creditok number(1);";
		qry += "\n     v_balcurr number(16,2);";
		qry += "\n     v_custid number;";
		qry += "\n     v_ntid number(1);";
		qry += "\n     v_statetag number(1);";
		qry += "\n     v_nowdate0 varchar2(10);";
		qry += "\n     v_curr  number(16,2); ";
		// qry += "\n v_creditcurr number(16,2); v_creditok number(1);";
		qry += "\n begin";
		qry += "\n   v_balcurr:=0;v_creditcurr:=0;v_creditok:=0;v_ntid:=0;";

		qry += "\n   begin";
		qry += "\n    select nvl(sum(curr),0) into v_totalcurr from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "'; ";
		qry += "\n    select to_char(notedate,'yyyy-mm-dd hh24:mi:ss'),custid,ntid,statetag into v_nowdate,v_custid,v_ntid,v_statetag from wareouth where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n    if v_statetag=0 then";
		qry += "\n       v_nowdate:=to_char(sysdate,'yyyy-mm-dd hh24:mi:ss');";
		qry += "\n    end if;";
		qry += "\n    v_nowdate0:=to_char(to_date(substr(v_nowdate,1,10),'yyyy-mm-dd')-1,'yyyy-mm-dd'); ";
		qry += "\n    select nvl(sum(curr),0) into  v_totalcost from wareoutcost where accid=" + Accid + " and noteno='" + Noteno + "'; ";
		qry += "\n    if v_ntid=2 then ";
		qry += "\n       v_totalcurr:=v_totalcurr-v_totalcost; ";
		qry += "\n    else";
		qry += "\n       v_totalcurr:=v_totalcurr+v_totalcost; ";
		qry += "\n    end if;";

		qry += "\n    v_curr:=f_getincomebaltimex(v_nowdate," + Accid + ",v_custid," + Houseid + ",'" + Calcdate + "');";

		qry += "\n    if v_custid>0 then";
		qry += "\n       select creditcurr,creditok into v_creditcurr,v_creditok from customer where custid=v_custid and accid=" + Accid + ";";
		qry += "\n    end if; ";

		// qry += "\n select v_totalcurr,v_curr,creditcurr,creditok,v_ntid into
		// :totalcurr,:balcurr,:creditcurr,:creditok,:ntid from customer where
		// custid=v_custid and accid=" + Accid + ";";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n    v_balcurr:=0;v_creditcurr:=0;v_creditok:=0;v_ntid:=0;";// where
																				// custid=v_custid
																				// and
																				// accid="+accid+";";
		qry += "\n    end;";

		qry += "\n    select v_totalcurr,v_curr,v_creditcurr,v_creditok,v_ntid into :totalcurr,:balcurr,:creditcurr,:creditok,:ntid from dual;";
		qry += "\n end;";
		//		System.out.println(qry);

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalcurr", new ProdParam(Types.DOUBLE));
		param.put("balcurr", new ProdParam(Types.DOUBLE));
		param.put("creditcurr", new ProdParam(Types.DOUBLE));
		param.put("creditok", new ProdParam(Types.INTEGER));
		param.put("ntid", new ProdParam(Types.INTEGER));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		int ntid = Integer.parseInt(param.get("ntid").getParamvalue().toString());
		// 取返回值
		errmess = "\"msg\":\"操作成功！\",\"TOTALCURR\": \"" + Func.FormatNumber(Double.parseDouble(param.get("totalcurr").getParamvalue().toString()), 2) + "\""//
				+ ",\"BALCURR\": \"" + Func.FormatNumber(Double.parseDouble(param.get("balcurr").getParamvalue().toString()), 2) + "\""//
				+ ",\"CREDITCURR\": \"" + Func.FormatNumber(Double.parseDouble(param.get("creditcurr").getParamvalue().toString()), 2) + "\""//
				+ ",\"CREDITOK\": \"" + param.get("creditok").getParamvalue().toString() + "\""//
		// + ",\"NTID\": \"" + param.get("ntid").getParamvalue().toString() +
		// "\""//
		;
		qry = "select payno,payid,payname from payway where accid=" + Accid + " and statetag=1 and noused=0";
		if (ntid > 0)
			qry += " and payno<>'V'";
		qry += " order by payno,payid";
		Table tb = DbHelperSQL.Query(qry).getTable(1);

		// string fh = "";
		// int count = dt.Rows.Count;

		errmess += ",\"PAYLIST\":[";

		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				errmess += ",";
			errmess += "{\"PAYID\":\"" + tb.getRow(i).get("PAYID").toString() + "\"" //
					+ ",\"PAYNAME\":\"" + tb.getRow(i).get("PAYNAME").toString() + "\""//
					+ ",\"PAYNO\":\"" + tb.getRow(i).get("PAYNO").toString() + "\"}";
		}
		errmess += "]";

		return 1;
	}

	// 取批发销售单收款记录及应收余额
	public int doGetpaye(int fs) { // fs:0=销售，1=退货
		if (Func.isNull(Noteno)) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n     v_nowdate varchar2(20);";
		qry += "\n     v_noteno varchar2(20);";
		qry += "\n     v_accid number;";
		qry += "\n     v_custid number;";
		qry += "\n     v_ntid number(1);";
		qry += "\n     v_nowdate0 varchar2(10);";
		qry += "\n     v_totalamt number(16,2);";
		qry += "\n     v_totalcurr number(16,2);";
		qry += "\n     v_totalcost number(16,2);";
		qry += "\n     v_balcurr  number(16,2); ";
		qry += "\n     v_freecurr  number(16,2); ";
		qry += "\n     v_zpaycurr  number(16,2); ";
		qry += "\n     v_balcurr0  number(16,2); ";
		qry += "\n     v_qkcurr  number(16,2); ";
		qry += "\n     v_statetag number(1);";
		qry += "\n begin";
		// qry += "\n select nvl(sum(curr),0) into v_totalcurr from wareoutm
		// where accid=" + accid + " and noteno='" + noteno + "'; ";
		qry += "\n   begin";
		qry += "\n    select to_char(notedate,'yyyy-mm-dd hh24:mi:ss'),custid,nvl(totalcurr,0),nvl(totalamt,0),nvl(totalcost,0),statetag,ntid ";
		qry += "\n    into v_nowdate,v_custid,v_totalcurr,v_totalamt,v_totalcost,v_statetag,v_ntid from wareouth where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n    if v_statetag=0 then "; // 如果当前单据未提交，取最近时间的欠款
		qry += "\n       v_nowdate:=to_char(sysdate,'yyyy-mm-ddhh24:mi:ss');";
		qry += "\n    end if;";
		qry += "\n    v_nowdate0:=to_char(to_date(substr(v_nowdate,1,10),'yyyy-mm-dd')-1,'yyyy-mm-dd'); ";
		// 免单金额
		if (fs == 0) // 销售

			qry += "\n    select nvl(sum(curr),0) into v_freecurr from incomecurr where accid=" + Accid + " and ynoteno='" + Noteno + "' and payid=0 and statetag=1;";
		else
			qry += "\n    select nvl(-sum(curr),0) into v_freecurr from incomecurr where accid=" + Accid + " and ynoteno='" + Noteno + "' and payid=0 and statetag=1;";
		// 收款金额
		qry += "\n    select nvl(sum(curr),0) into v_zpaycurr from incomecurr where accid=" + Accid + " and ynoteno='" + Noteno + "' and payid>0 and statetag=1;";

		qry += "\n    v_balcurr:=f_getincomebaltimex(v_nowdate," + Accid + ",v_custid," + Houseid + ",'" + Calcdate + "');";

		// v_nowdate in varchar2,
		// v_accid in number,
		// v_provid in number,
		// v_houseid in number,
		// v_calcdate in varchar

		// qry += "\n select nvl(sum(curr),0) into v_balcurr from (";
		// // --上日余额
		// qry += "\n select curr from incomebal where custid=v_custid and
		// daystr=v_nowdate0";
		// if (Houseid > 0)
		// qry += "\n and houseid=" + Houseid;
		// qry += "\n union all";
		// // --本日初始应收款
		// qry += "\n select sum(a.curr) from firstincomecurr a";
		// qry += "\n where a.accid=" + Accid + " and a.custid=v_custid ";
		// qry += "\n and a.statetag=1 and
		// a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and
		// a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		// if (Houseid > 0)
		// qry += "\n and a.houseid=" + Houseid;
		// // --本日应收货款
		// qry += "\n union all";
		// qry += "\n select sum(case a.ntid when 1 then b.curr else -b.curr
		// end) from wareouth a left outer join wareoutm b on a.accid=b.accid
		// and a.noteno=b.noteno ";
		// qry += "\n where a.accid=" + Accid + " and a.custid=v_custid and
		// a.ntid>0";
		// qry += "\n and a.statetag=1 and
		// a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and
		// a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		// if (Houseid > 0)
		// qry += "\n and a.houseid=" + Houseid;
		//
		// // --本日批发应收费用
		// qry += "\n union all";
		// qry += "\n select sum(b.curr ) from wareouth a left outer join
		// wareoutcost b on a.accid=b.accid and a.noteno=b.noteno ";
		// qry += "\n where a.accid=" + Accid + " and a.custid=v_custid and
		// (a.ntid=1 or a.ntid=2)";
		// qry += "\n and a.statetag=1 and
		// a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and
		// a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		// if (Houseid > 0)
		// qry += "\n and a.houseid=" + Houseid;
		//
		// // --应收费用
		// qry += "\n union all";
		// qry += "\n select sum(a.curr) from incomecost a ";
		// qry += "\n where a.accid=" + Accid + " and a.custid=v_custid ";
		// qry += "\n and a.statetag=1 and
		// a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and
		// a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		// if (Houseid > 0)
		// qry += "\n and a.houseid=" + Houseid;
		// // --已收及折扣
		// qry += "\n union all";
		// qry += "\n select -sum(a.curr) from incomecurr a ";
		// qry += "\n where a.accid=" + Accid + " and a.custid=v_custid ";
		// qry += "\n and a.statetag=1 and
		// a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and
		// a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		// if (Houseid > 0)
		// qry += "\n and a.houseid=" + Houseid;
		// qry += "\n ) ;";

		if (fs == 0) // 销售
		{
			qry += "\n    v_qkcurr:=v_totalcurr-v_freecurr-v_zpaycurr;"; // 本次欠款
			qry += "\n    if v_statetag=1 then "; // 已结账的单据
			qry += "\n       v_balcurr0:=v_balcurr-v_qkcurr;"; // 累计欠款=上次欠款+本次欠款
			qry += "\n    else ";
			qry += "\n       v_balcurr0:=v_balcurr;"; // 累计欠款=上次欠款+本次欠款
			qry += "\n       v_balcurr:=v_balcurr+v_qkcurr;"; // 累计欠款=上次欠款+本次欠款
			qry += "\n    end if;";
		} else // 退货
		{
			// qry += "\n v_totalcurr:=v_totalcurr-v_totalcost;";
			// qry += "\n v_qkcurr:=v_totalcurr+v_freecurr+v_zpaycurr;"; //本次欠款
			qry += "\n    v_qkcurr:=v_totalcurr-v_freecurr+v_zpaycurr;"; // 本次欠款
			qry += "\n    if v_statetag=1 then "; // 已结账的单据
			qry += "\n       v_balcurr0:=v_balcurr+v_qkcurr;"; // 累计欠款=上次欠款+本次欠款
			qry += "\n    else ";
			qry += "\n       v_balcurr0:=v_balcurr;"; // 累计欠款=上次欠款+本次欠款
			qry += "\n       v_balcurr:=v_balcurr-v_qkcurr;"; // 累计欠款=上次欠款+本次欠款
			qry += "\n    end if;";
		}

		// qry += "\n select
		// v_totalamt,v_totalcurr,v_totalcost,v_balcurr,v_balcurr0,v_qkcurr,v_freecurr,v_totalcurr-v_freecurr
		// ";
		// qry += "\n into
		// :totalamt,:totalcurr,:totalcost,:balcurr,:balcurr0,:qkcurr,:freecurr,:checkcurr
		// from dual;";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN ";
		// qry += "\n select 0,0,0,0,0,0,0,0 into
		// :totalamt,:totalcurr,:totalcost,:balcurr,:balcurr0,:qkcurr,:freecurr,:checkcurr
		// from dual;";// where custid=v_custid and accid="+accid+";";
		qry += "\n      v_totalamt:=0;v_totalcurr:=0;v_totalcost:=0;v_balcurr:=0;v_balcurr0:=0;v_qkcurr:=0;v_freecurr:=0;";
		qry += "\n   end;";
		qry += "\n   select v_totalamt,v_totalcurr,v_totalcost,v_balcurr,v_balcurr0,v_qkcurr,v_freecurr,v_totalcurr-v_freecurr ";
		qry += "\n       into :totalamt,:totalcurr,:totalcost,:balcurr,:balcurr0,:qkcurr,:freecurr,:checkcurr  from dual;";
		qry += "\n end;";
		//		 System.out.println("doGetWareoutpaye:"+qry);
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalamt", new ProdParam(Types.DOUBLE));
		param.put("totalcurr", new ProdParam(Types.DOUBLE));
		param.put("totalcost", new ProdParam(Types.DOUBLE));
		param.put("balcurr", new ProdParam(Types.DOUBLE));
		param.put("balcurr0", new ProdParam(Types.DOUBLE));
		param.put("qkcurr", new ProdParam(Types.DOUBLE));
		param.put("freecurr", new ProdParam(Types.DOUBLE));
		param.put("checkcurr", new ProdParam(Types.DOUBLE));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常!";
			return 0;
		}
		// 取返回值
		// float amount =
		// Float.parseFloat(param.get("amount").getParamvalue().toString());
		// float curr =
		// Float.parseFloat(param.get("curr").getParamvalue().toString());

		// String or =
		// ConfigurationManager.ConnectionStrings["conn").toString();
		// OracleConnection oc = new OracleConnection(or);
		// OracleCommand om = oc.CreateCommand();
		//System.out.println(param.get("balcurr").getParamvalue().toString());

		String responsestr = "\"msg\":\"操作成功！\",\"TOTALCURR\":\"" + param.get("totalcurr").getParamvalue().toString() + "\"" //
				+ ",\"TOTALAMT\": \"" + param.get("totalamt").getParamvalue().toString() + "\"" //
				+ ",\"TOTALCOST\": \"" + param.get("totalcost").getParamvalue().toString() + "\"" //
				+ ",\"BALCURR\": \"" + param.get("balcurr").getParamvalue().toString() + "\"" //
				+ ",\"BALCURR0\": \"" + param.get("balcurr0").getParamvalue().toString() + "\"" //
				+ ",\"QKCURR\": \"" + param.get("qkcurr").getParamvalue().toString() + "\"" //

				//				+ ",\"BALCURR\": \"" + Func.FormatNumber(Double.parseDouble(param.get("balcurr").getParamvalue().toString()), 2) + "\"" //
				//				+ ",\"BALCURR0\": \"" + Func.FormatNumber(Double.parseDouble(param.get("balcurr0").getParamvalue().toString()), 2) + "\"" //
				//				+ ",\"QKCURR\": \"" + Func.FormatNumber(Double.parseDouble(param.get("qkcurr").getParamvalue().toString()), 2) + "\"" //
				+ ",\"FREECURR\": \"" + param.get("freecurr").getParamvalue().toString() + "\"" //
				+ ",\"CHECKCURR\": \"" + param.get("checkcurr").getParamvalue().toString() + "\"";

		qry = "";
		if (fs == 0) // 批发开票单
		{
			qry = "select a.payid,cast('*' as nvarchar2(10)) as payno,cast('折让' as nvarchar2(10)) as payname,a.curr from incomecurr a";
			qry += "\n  where a.accid=" + Accid + " and a.ynoteno='" + Noteno + "' and a.payid=0 and a.curr<>0 and a.statetag=1";
			qry += "\n  union all";
			qry += "\n  select a.payid,b.payno,b.payname,a.curr from incomecurr a";
			qry += "\n  left outer join payway b  on a.payid=b.payid";
			qry += "\n  where a.accid=" + Accid + " and a.ynoteno='" + Noteno + "' and a.payid>0 and a.statetag=1";
			qry += "\n  order by payno";
		} else // 批发退货
		{
			qry = "select a.payid,cast('*' as nvarchar2(10)) as payno,cast('折让' as nvarchar2(10)) as payname,-a.curr as curr from incomecurr a";
			qry += "\n  where a.accid=" + Accid + " and a.ynoteno='" + Noteno + "' and a.payid=0 and a.curr<>0 and a.statetag=1";
			qry += "\n  union all";
			qry += "\n  select a.payid,b.payno,b.payname,-a.curr as curr from incomecurr a";
			qry += "\n  left outer join payway b  on a.payid=b.payid";
			qry += "\n  where a.accid=" + Accid + " and a.ynoteno='" + Noteno + "' and a.payid>0 and a.statetag=1";
			qry += "\n  order by payno";
		}
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		String fh = "";
		int count = tb.getRowCount();

		responsestr += ",\"PAYLIST\":[";

		for (int i = 0; i < count; i++) {
			responsestr += fh + "{\"PAYID\":\"" + tb.getRow(i).get("PAYID").toString() + "\"" //
					+ ",\"PAYNAME\":\"" + tb.getRow(i).get("PAYNAME").toString() + "\"" //
					+ ",\"PAYNO\":\"" + tb.getRow(i).get("PAYNO").toString() + "\"" //
					+ ",\"CURR\":\"" + tb.getRow(i).get("CURR").toString() + "\"" + "}";
			fh = ",";
		}
		responsestr += "]";
		errmess = responsestr;
		return 1;
	}

	// 商品选择列表(看图开单)
	public int doSelectWarecode(QueryParam qp, int noteid, int sortid, int kdbj, int qxbj, long userid) {
		if (Houseid == 0) {
			errmess = "未指定店铺！";
			return 0;
		}
		if (Noteno.equals("")) {
			errmess = "单据号无效！";
			return 0;
		}
		int pricetype = 0;
		String qry = "";
		if (noteid == 0)// 如果是零售开票，要取价格方式
		{
			qry = "select pricetype from warehouse where houseid=" + Houseid;
			try {
				pricetype = Integer.parseInt(DbHelperSQL.RunSql(qry).toString());
			} catch (Exception e) {
				pricetype = 0;
			}
		} else if (noteid == 1) {//批发
			qry = "select b.pricetype from wareouth a left outer join customer b on a.custid=b.custid where a.accid=" + Accid + " and a.noteno='" + Noteno + "'";
			try {
				pricetype = Integer.parseInt(DbHelperSQL.RunSql(qry).toString());
			} catch (Exception e) {
				pricetype = 0;
			}
		}
		// 合计
		Float totalamt = (float) 0;
		Float totalcurr = (float) 0;
		Table tb = new Table();
		qry = "select nvl(sum(amount),0) as totalamt,nvl(sum(curr),0) as totalcurr from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "'";
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			totalamt = Float.parseFloat(tb.getRow(0).get("TOTALAMT").toString());
			totalcurr = Float.parseFloat(tb.getRow(0).get("TOTALCURR").toString());
		}

		qry = "select wareid,warename,wareno,units,retailsale,typeid,typename,imagename0,ssdate,xsamount,xsamt ";
		if (sortid == 2)
			qry += "\n  ,f_getwaresumx(to_char(sysdate,'yyyy-mm-dd')," + Accid + ",wareid," + Houseid + ",'" + Calcdate + "') as kcamount ";
		qry += "\n   from (";

		if (kdbj == 0 || kdbj == 2) {
			qry += "\n  select a.wareid,a.warename,a.wareno,a.units,a.typeid,b.typename,a.imagename0,a.ssdate,a.xsamount";
			if (noteid == 0 || noteid == 1)//零售
				qry += ",case " + pricetype + " when 1 then a.sale1 when 2 then a.sale2 when 3 then a.sale3 when 4 then a.sale4 when 5 then a.sale5 else a.retailsale end  as retailsale ";
			else
				qry += ",a.retailsale";
			qry += "\n ,0 as xsamt";
			qry += "\n  FROM warecode a";
			qry += "\n  left outer join waretype b on a.typeid=b.typeid";
			qry += "\n  where a.statetag=1 and a.noused=0 and a.accid= " + Accid;
			qry += "\n   and not exists (select 1 from wareoutm c where c.accid=" + Accid + " and c.noteno='" + Noteno + "' and a.wareid=c.wareid)";
			if (!Remark.equals(""))
				qry += "\n  and (a.warename like '%" + Remark + "%' or a.shortname like '%" + Remark.toUpperCase() + "%' or a.wareno like '%" + Remark.toUpperCase() + "%')";
			if (qxbj == 1) // 1 启用权限控制
			{
				qry += "\n  and (a.brandid=0 or exists (select 1 from employebrand x where a.brandid=x.brandid and x.epid=" + userid + "))";
			}

		}

		if (kdbj == 1 || kdbj == 2) // 1=已开单
		{
			if (kdbj != 1)
				qry += "\n  union all";
			qry += "\n  select a.wareid,a.warename,a.wareno,a.units,a.typeid,b.typename,a.imagename0,a.ssdate,a.xsamount";
			if (noteid == 0 || noteid == 1)
				qry += ",case " + pricetype + " when 1 then a.sale1 when 2 then a.sale2 when 3 then a.sale3 when 4 then a.sale4 when 5 then a.sale5 else a.retailsale end  as retailsale ";
			else
				qry += ",a.retailsale";
			qry += "\n ,f_getamtofnoteno(a.accid,'" + Noteno + "'," + noteid + ",a.wareid) as xsamt";
			qry += "\n  FROM warecode a";
			qry += "\n  left outer join waretype b on a.typeid=b.typeid";
			qry += "\n  where a.statetag=1 and a.noused=0 and a.accid= " + Accid;
			qry += "\n  and exists (select 1 from wareoutm c where c.accid=" + Accid + " and c.noteno='" + Noteno + "' and a.wareid=c.wareid)";
			if (!Remark.equals(""))
				qry += "\n  and (a.warename like '%" + Remark + "%' or a.shortname like '%" + Remark.toUpperCase() + "%' or a.wareno like '%" + Remark.toUpperCase() + "%')";
			if (qxbj == 1) // 1 启用权限控制
			{
				qry += "\n  and (a.brandid=0 or exists (select 1 from employebrand x where a.brandid=x.brandid and x.epid=" + userid + "))";
			}
		}
		qry += "\n  ) where rownum<100";
		qp.setQueryString(qry);
		qp.setSumString(totalamt + " as totalamt," + totalcurr + " as totalcurr");
		// Table tb = new Table();
		tb = DbHelperSQL.GetTable(qp);

		String responsestr = "{\"result\":1,\"msg1\":\"操作成功！\"," + qp.getTotalString() + ",\"rows\":[";
		String fh = "";
		// int rs1 = ;
		for (int i = 0; i < tb.getRowCount(); i++) {
			long wareid = Long.parseLong(tb.getRow(i).get("WAREID").toString());
			Float kcamount = (float) 0;
			if (sortid != 2) {
				String qry1 = "select f_getwaresumx(to_char(sysdate,'yyyy-mm-dd')," + Accid + "," + wareid + "," + Houseid + ",'" + Calcdate + "') as kcamt from dual";
				kcamount = Float.parseFloat(DbHelperSQL.RunSql(qry1).toString());
			} else {
				kcamount = Float.parseFloat(tb.getRow(i).get("KCAMOUNT").toString());
			}
			responsestr += fh + "{\"WAREID\":\"" + wareid + "\"" //
					+ ",\"WARENAME\":\"" + tb.getRow(i).get("WARENAME").toString() + "\"" //
					+ ",\"WARENO\":\"" + tb.getRow(i).get("WARENO").toString() + "\"" //
					+ ",\"UNITS\":\"" + tb.getRow(i).get("UNITS").toString() + "\"" //
					+ ",\"RETAILSALE\":\"" + tb.getRow(i).get("RETAILSALE").toString() + "\"" //
					+ ",\"TYPEID\":\"" + tb.getRow(i).get("TYPEID").toString() + "\""//
					+ ",\"TYPENAME\":\"" + tb.getRow(i).get("TYPENAME").toString() + "\"" //
					+ ",\"IMAGENAME0\":\"" + tb.getRow(i).get("IMAGENAME0").toString() + "\"" //
					+ ",\"XSAMOUNT\":\"" + tb.getRow(i).get("XSAMOUNT").toString() + "\"" //
					+ ",\"XSAMT\":\"" + tb.getRow(i).get("XSAMT").toString() + "\"" //
					+ ",\"KCAMOUNT\":\"" + kcamount + "\"" //
					+ ",\"SSDATE\":\"" + tb.getRow(i).get("SSDATE").toString() + "\"}";
			fh = ",";

		}
		responsestr += "]}";
		errmess = responsestr;
		return 1;
	}

	// 提交收银台
	public int Tocash(int zt, int fs) {

		String qry = "declare ";
		qry += "\n     v_retcs varchar2(200); ";
		qry += "\n     v_count number; ";
		qry += "\n     v_progname varchar2(40);";
		qry += "\n   begin ";

		if (zt == 0) {

			qry += "\n   select count(*) into v_count from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   if v_count=0 then";
			qry += "\n      v_retcs:=  '0没有商品明细记录，不允许提交！' ; ";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			if (Ntid == 1)
				qry += "\n   v_progname:='批发开票';";
			else if (Ntid == 2)
				qry += "\n   v_progname:='批发退货';";
			else
				qry += "\n   v_progname:='零售开票';";

			qry += "\n   update wareouth set lastdate=sysdate";
			if (Remark != null && Remark.length() > 0)
				qry += ",remark='" + Remark + "'";
			if (Handno != null && Handno.length() > 0)
				qry += ",handno='" + Handno + "'";
			qry += " where accid=" + Accid + " and noteno='" + Noteno + "' ;";

			if ((Ntid == 0 || Ntid == 1) && fs == 0 && Houseid > 0) // 要判断负库存
			{
				// 要判断负库存
				qry += "\n   begin";
				qry += "\n     select b.wareno||','||c.colorname||','||d.sizename into v_retcs from ";
				qry += "\n     (select wareid,colorid,sizeid,sum(amount) as amount from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "'";
				qry += "\n     group by wareid,colorid,sizeid) a";
				qry += "\n     left outer join warecode b on a.wareid=b.wareid";
				qry += "\n     left outer join colorcode c on a.colorid=c.colorid";
				qry += "\n     left outer join sizecode d on a.sizeid=d.sizeid";
				qry += "\n     where a.amount>0 and a.amount>f_getwareamountx(to_char(sysdate,'yyyy-mm-dd')," + Accid + ",a.wareid,a.colorid,a.sizeid," + Houseid + ",'" + Calcdate + "') and rownum=1;";
				qry += "\n     v_retcs:= '0'||v_retcs||'库存不足，不允许出库！'; ";
				qry += "\n     goto exit; ";

				qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
				qry += "\n     v_retcs:=''; ";
				qry += "\n   end;";

			}
			qry += "\n   update wareouth set statetag=3,lastdate=sysdate";
			qry += "\n   where statetag=0 and accid=" + Accid + " and noteno='" + Noteno + "';";// and
																								// id="
																								// +
																								// id
																								// +
																								// ";";
			qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
			qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",v_progname,'【提交收银台】销售单号:" + Noteno + "','" + Operant + "');";

		} else {
			qry += "\n   update wareouth set statetag=0,lastdate=sysdate";
			qry += "\n   where statetag=3 and accid=" + Accid + " and noteno='" + Noteno + "';";// and
																								// id="
																								// +
																								// id
																								// +
																								// ";";
			qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
			qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'收银台','【返单】销售单号:" + Noteno + "','" + Operant + "');";
		}
		qry += "\n   commit; ";
		qry += "\n   v_retcs:='1操作成功！'; ";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end; ";
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

	// 冲单
	public int doClear(int noteid) {
		if (Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n    v_noteno varchar2(20);";
		qry += "\n    v_znoteno varchar2(20);";
		qry += "\n    v_count number;";
		qry += "\n    v_custid number;";
		qry += "\n    v_houseid number;";
		qry += "\n    v_dptid number;";
		qry += "\n    v_totalcurr number;";
		qry += "\n    v_totalcost number;";
		qry += "\n    v_totalamt number;";
		qry += "\n    v_checkcurr number;";
		qry += "\n    v_zpaycurr number;";
		qry += "\n    v_id number;";
		qry += "\n    v_rs number;";
		qry += "\n    v_ntid number(1);";
		qry += "\n    v_cleartag number(1);";
		qry += "\n    v_retcs varchar2(200);";
		qry += "\n begin";
		qry += "\n    select custid,houseid,dptid,cleartag,checkcurr,zpaycurr,totalcurr,totalcost,totalamt,rs ";
		qry += "      into v_custid,v_houseid,v_dptid,v_cleartag,v_checkcurr,v_zpaycurr,v_totalcurr,v_totalcost,v_totalamt,v_rs from wareouth where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n    if v_cleartag>0 then ";
		qry += "\n       v_retcs:= '0当前单据已有冲单记录！'; ";
		qry += "\n       goto exit; ";
		qry += "\n    end if;";

		qry += "\n   begin";
		// 生成单据号
		if (noteid == 3) {
			qry += "\n    select 'XS'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareouth ";
			qry += "\n    where accid=" + Accid + " and ntid=1 and LENGTH(noteno)=15 and substr(noteno,1,10)= 'XS'||To_Char(SYSDATE,'yyyymmdd'); ";
			qry += "\n    v_ntid:=1;";
		} else {
			qry += "\n    select 'XT'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareouth ";
			qry += "\n    where accid=" + Accid + " and ntid=2 and LENGTH(noteno)=15 and substr(noteno,1,10)= 'XT'||To_Char(SYSDATE,'yyyymmdd'); ";
			qry += "\n    v_ntid:=2;";
		}
		qry += "\n    insert into wareouth (id, accid,noteno,notedate,custid,houseid,dptid,ntid,statetag,totalamt,totalcurr,remark,handno,orderno,operant,checkman,cleartag,lastdate,rs) ";
		qry += "\n    values (WAREOUTH_ID.NEXTVAL," + Accid + ",v_noteno,sysdate,v_custid,v_houseid,v_dptid,v_ntid,1,0,0,'冲单：" + Noteno + "','" + Noteno + "','','" + Operant + "','',2,sysdate,v_rs); ";
		// 冲商品明细
		qry += "\n    v_count:=1;";
		qry += "\n    declare cursor cur_data is ";
		qry += "\n      select a.wareid,a.colorid,a.sizeid,a.saleid,a.amount,a.price0,a.discount,a.price,a.curr,a.remark0";
		qry += "\n      from wareoutm a ";// join warecode b on
											// a.wareid=b.wareid";
		qry += "\n      where a.accid=" + Accid + " and a.noteno='" + Noteno + "';";
		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n    begin";
		qry += "\n      for v_row in cur_data loop";
		qry += "\n         insert into wareoutm (id,accid,noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,curr,remark0)";
		qry += "\n         values (wareoutm_id.nextval," + Accid + ",v_noteno,v_row.wareid,v_row.colorid,v_row.sizeid,v_row.saleid,-v_row.amount,v_row.price0,v_row.discount,v_row.price,-v_row.curr,v_row.remark0);";
		qry += "\n         v_count:=v_count+1;";
		qry += "\n         if v_count>500 then ";
		qry += "\n            v_count:=1;";
		qry += "\n            commit;";
		qry += "\n         end if;";
		qry += "\n      end loop;";
		qry += "\n    end;";

		// 冲销售费用
		qry += "\n    v_count:=1;";
		qry += "\n    declare cursor cur_data is ";
		qry += "\n      select a.cgid,a.curr";
		qry += "\n      from wareoutcost a ";// join warecode b on
												// a.wareid=b.wareid";
		qry += "\n      where a.accid=" + Accid + " and a.noteno='" + Noteno + "';";
		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n    begin";
		qry += "\n      for v_row in cur_data loop";
		qry += "\n         insert into wareoutcost (id,accid,noteno,cgid,curr)";
		qry += "\n         values (wareoutcost_id.nextval," + Accid + ",v_noteno,v_row.cgid,-v_row.curr);";
		qry += "\n         v_count:=v_count+1;";
		qry += "\n         if v_count>500 then ";
		qry += "\n            v_count:=1;";
		qry += "\n            commit;";
		qry += "\n         end if;";
		qry += "\n      end loop;";
		qry += "\n    end;";
		if (noteid == 3) // 批发出库-冲折让优惠
		{
			qry += "\n    v_count:=1;";
			qry += "\n    declare cursor cur_data is ";
			qry += "\n      select a.noteno,a.custid,a.houseid,a.curr";
			qry += "\n      from incomecurr a ";// join warecode b on
												// a.wareid=b.wareid";
			qry += "\n      where a.accid=" + Accid + " and a.ynoteno='" + Noteno + "' and a.payid=0;";
			qry += "\n      v_row cur_data%rowtype;";
			qry += "\n    begin";
			qry += "\n      for v_row in cur_data loop";
			qry += "\n         select 'SZ'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_znoteno from incomecurr";
			qry += "\n         where accid=" + Accid + "  and LENGTH(noteno)=15 and substr(noteno,1,10)= 'SZ'||To_Char(SYSDATE,'yyyymmdd');";
			// qry += "\n v_id:=incomecurr_ID.NEXTVAL;";
			qry += "\n         insert into incomecurr (id, accid,noteno,ynoteno,notedate,custid,houseid,payid,curr,remark,handno,statetag,operant,checkman,lastop,lastdate)";
			qry += "\n         values (incomecurr_ID.NEXTVAL," + Accid + ",v_znoteno,v_noteno,sysdate,v_row.custid,v_row.houseid,0,-v_row.curr,'[" + Noteno + "]冲单折让优惠','',1,'"//
					+ Operant + "','','" + Operant + "',sysdate);";
			qry += "\n         v_count:=v_count+1;";
			qry += "\n         if v_count>500 then ";
			qry += "\n            v_count:=1;";
			qry += "\n            commit;";
			qry += "\n         end if;";
			qry += "\n      end loop;";
			qry += "\n    end;";

		}
		// 如果原单据有销售人，则冲单的单据也要填入销售人
		qry += "\n  if v_rs>0 then";
		qry += "\n     insert into waresaleman (id,accid,noteno,epid) ";
		qry += "\n     select waresaleman_id.nextval," + Accid + ",v_noteno,epid from waresaleman";
		qry += "\n     where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n     commit;";
		// qry += "\n values (waresaleman_id.nextval,"+Accid+",v_noteno,6671);";
		qry += "\n  end if;";
		// qry += "\n select nvl(sum(amount),0),nvl(sum(curr),0) into
		// v_totalamt,v_totalcurr from wareoutm where accid=" + accid + " and
		// noteno=v_noteno ;";
		// qry += "\n select nvl(sum(curr),0) into v_totalcost from wareoutcost
		// where accid=" + accid + " and noteno=v_noteno ;";
		qry += "\n    update wareouth set totalcurr=-v_totalcurr,totalcost=-v_totalcost,totalamt=-v_totalamt,checkcurr=-v_checkcurr,zpaycurr=-v_zpaycurr  where accid=" + Accid + " and noteno=v_noteno;";

		qry += "\n    update wareouth set cleartag=1,remark=remark||'(已被'||v_noteno||'冲单)'  where accid=" + Accid + " and noteno='" + Noteno + "';"; // 将原单据cleartag=1，表示已冲单
		qry += "\n    commit;";
		qry += "\n    insert into LOGRECORD (id,accid,progname,remark,lastop)";
		if (noteid == 3) {
			qry += "\n    values (LOGRECORD_id.nextval," + Accid + ",'批发冲单','【冲单】原单据号:" + Noteno + ";新单据号:'||v_noteno,'" + Operant + "');";
		} else {
			qry += "\n    values (LOGRECORD_id.nextval," + Accid + ",'批发退货冲单','【冲单】原单据号:" + Noteno + ";新单据号:'||v_noteno,'" + Operant + "');";
		}
		qry += "\n    v_retcs:= '1冲单成功！'; ";
		qry += "\n    exception  ";
		qry += "\n    when others then  ";
		qry += "\n    begin ";
		qry += "\n      rollback; ";
		qry += "\n      v_retcs:='0冲单失败！'; ";
		qry += "\n    end; ";
		qry += "\n  end;";
		qry += "\n  <<exit>>";
		qry += "\n  select v_retcs into :retcs from dual;";
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

	// 配货单转出库单
	public int PeiToOut(String phnoteno, int tocash) {
		if (phnoteno.length() <= 0) {
			errmess = "phnoteno不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		// qry += "\n v_phnoteno varchar2(20); ";
		qry += "\n    v_noteno varchar2(20); ";
		qry += "\n    v_custid number(10); ";
		qry += "\n    v_dptid number(10); ";
		qry += "\n    v_salemanid number(10); ";
		qry += "\n    v_id number; ";
		qry += "\n    v_statetag number(1); ";
		qry += "\n    v_houseid number(10); ";
		qry += "\n    v_totalamt number; ";
		qry += "\n    v_totalcurr number(10); ";
		qry += "\n    v_statetag1 number(1); ";

		qry += "\n    v_retbj number(1); ";
		qry += "\n    v_retcs varchar2(200);";
		qry += "\n begin ";
		qry += "\n   begin ";

		qry += "\n    v_retbj:=0; ";
		// qry += "\n v_phnoteno:='KO2016052900001'; ";
		if (tocash == 1)
			qry += "\n  v_statetag:=3;";
		else
			qry += "\n  v_statetag:=1;";
		qry += "\n    select custid,houseid,dptid,salemanid,statetag into v_custid,v_houseid,v_dptid,v_salemanid,v_statetag1 from warepeih where accid=" + Accid + " and noteno='" + phnoteno + "'; ";
		// statetag:0=草稿,1=提交,2=审核,3=配货中，4=配货完，5=已转出库单

		qry += "\n    if v_statetag1<>4 and v_statetag1<>0 and v_statetag1<>1  then"; // 提交和配货完成都可以转出库单
		qry += "\n       v_retcs:='\"msg\":\"单据状态异常，请重试！\"' ;";
		qry += "\n       goto exit;";
		qry += "\n    end if;";
		// --生成批发出库单号
		qry += "\n    select 'XS'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareouth ";
		qry += "\n    where accid=" + Accid + " and ntid=1 and LENGTH(noteno)=15 and substr(noteno,1,10)= 'XS'||To_Char(SYSDATE,'yyyymmdd'); ";
		qry += "\n    v_id:=WAREOUTH_ID.NEXTVAL;";
		qry += "\n    insert into wareouth (id, accid,noteno,notedate,custid,houseid,dptid,ntid,statetag,totalamt,totalcurr,remark,handno,orderno,operant,checkman,lastdate) ";
		qry += "\n    values (v_id," + Accid + ",v_noteno,sysdate,v_custid,v_houseid,v_dptid,1,v_statetag,0,0,'','" + phnoteno + "','" + phnoteno + "','" + Operant + "','',sysdate); ";
		qry += "\n    if v_salemanid>0 then ";
		qry += "\n       insert into waresaleman (id,accid,noteno,epid)";
		qry += "\n       values (waresaleman_id.nextval," + Accid + ",v_noteno,v_salemanid);";
		qry += "\n    end if ;";
		qry += "\n    if v_statetag1<=1 then "; // 配货单直接出库
		qry += "\n       insert into wareoutm (id,accid,noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,curr,remark0) ";
		qry += "\n       select wareoutm_id.nextval," + Accid + ",v_noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,factamt*price,remark0  ";
		qry += "\n       from warepeim where accid=" + Accid + " and noteno='" + phnoteno + "' and amount>0; ";
		qry += "\n    else "; // 配货单捡货后出库
		qry += "\n       insert into wareoutm (id,accid,noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,curr,remark0) ";
		qry += "\n       select wareoutm_id.nextval," + Accid + ",v_noteno,wareid,colorid,sizeid,saleid,factamt,price0,discount,price,factamt*price,remark0  ";
		qry += "\n       from warepeim where accid=" + Accid + " and noteno='" + phnoteno + "' and factamt>0; ";
		qry += "\n    end if;";

		qry += "\n    select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from wareoutm where accid=" + Accid + " and noteno=v_noteno; ";
		qry += "\n    update wareouth set totalamt=v_totalamt,totalcurr=v_totalcurr where accid=" + Accid + " and noteno=v_noteno; ";
		// --配货单填入出库单号 statetag:0=草稿,1=提交,2=审核,3=配货中，4=配货完，5=已转出库单
		qry += "\n    update warepeih set xsnoteno=v_noteno,statetag=decode(v_statetag,1,5,4) where accid=" + Accid + " and noteno='" + phnoteno + "'; ";
		qry += "\n    commit; ";
		qry += "\n    v_retbj:=1; select '\"msg\":\"操作成功\",\"ID\":\"'||id||'\",\"NOTENO\":\"'||noteno||'\",\"NOTEDATE\":\"'||to_char(notedate,'yyyy-mm-dd hh24:mi:ss')||'\"' into v_retcs from wareouth where id=v_id;  ";
		qry += "\n    exception  ";
		qry += "\n    when others then  ";
		qry += "\n    begin ";
		qry += "\n      rollback;  ";
		qry += "\n      v_retbj:=0;  v_retcs:= '\"msg\":\"操作失败，请重试！\"';";
		qry += "\n    end; ";
		qry += "\n  end; ";
		qry += "\n  <<exit>>";

		qry += "\n  select v_retbj,v_retcs into :retbj,:retcs from dual;";
		qry += "\n end; ";
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

	// 载入退货申请到批发退货单
	public int doLoadRefundaskm() {
		if (Func.isNull(Noteno) || Func.isNull(Orderno)) {
			errmess = "noteno或orderno不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n  v_saleid number(10); ";
		qry += "\n  v_custid number(10); ";
		qry += "\n  v_amount number; ";
		qry += "\n  v_totalamt number; ";
		qry += "\n  v_totalcurr number; ";
		qry += "\n begin ";
		qry += "\n   begin";
		qry += "\n     select custid into v_custid from refundouth where accid=" + Accid + " and noteno='" + Orderno + "';";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n     return;";
		qry += "\n   end;";
		qry += "\n   delete from wareoutm where  accid=" + Accid + " and noteno='" + Noteno + "';";

		qry += "\n   declare CURSOR cur_tempdata is ";
		qry += "\n        select accid,wareid,colorid,sizeid,amount,price0,discount,price,curr,remark0  ";
		qry += "\n        from refundoutm  ";
		qry += "\n        where accid=" + Accid + "  and noteno='" + Orderno + "'; ";
		qry += "\n        v_row cur_tempdata%rowtype; ";
		qry += "\n      begin ";
		qry += "\n         for v_row in cur_tempdata loop ";
		// qry += "\n
		// v_saleid:=f_saleofcustware(v_row.accid,v_custid,v_row.wareid,v_row.colorid);
		// ";

		qry += "\n             v_saleid:=f_saleofwareout(v_row.accid,v_custid,v_row.wareid,v_row.colorid,2,2); ";
		// qry += "\n v_saleid:=f_saleofwareout(" + accid +
		// ",v_custid,v_wareid,v_colorid,v_saleid," + ntid + "); ";

		qry += "\n            insert into wareoutm (id,accid,noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,curr,remark0,lastdate) ";
		qry += "\n            values ( wareoutm_id.nextval," + Accid + ",'" + Noteno
				+ "',v_row.wareid,v_row.colorid,v_row.sizeid,v_saleid,v_row.amount,v_row.price0,v_row.discount,v_row.price,v_row.curr,v_row.remark0,sysdate);  ";
		qry += "\n          end loop; ";
		qry += "\n      end; ";

		qry += "\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   update wareouth set orderno='" + Orderno + "',ywnoteno='" + Orderno + "',totalcurr=v_totalcurr,totalamt=v_totalamt";
		qry += "\n   where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n end;";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作导常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	public int doLoadWarepeim(String phnoteno, int priceprec) {

		if (Func.isNull(Noteno) || Func.isNull(phnoteno)) {
			errmess = "noteno或phnoteno不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		// qry += "\n v_saleid number(10); ";
		qry += "\n  v_amount number; ";
		qry += "\n  v_totalamt number; ";
		qry += "\n  v_totalcurr number; ";
		qry += "\n  v_remark varchar2(200); ";
		qry += "\n  v_orderno varchar2(20); ";
		qry += "\n  v_phnoteno varchar2(20); "; // 原配货单号
		qry += "\n begin ";
		qry += "\n   select ywnoteno into v_phnoteno from wareouth where accid=" + Accid + " and noteno='" + Noteno + "';";
		// 删除原出库单上的数据
		qry += "\n   delete from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   commit;";

		qry += "\n   select remark,orderno into v_remark,v_orderno from warepeih where accid=" + Accid + " and noteno='" + phnoteno + "';";
		// qry += "\n v_saleid:=1;";
		qry += "\n   declare cursor cur_data is ";
		qry += "\n   select a.wareid,a.colorid,a.sizeid,a.saleid,a.amount,a.price0,a.discount,a.price,a.curr,a.remark0 from warepeim a";
		qry += "\n      where a.accid=" + Accid;
		// if (ordernolist != null && ordernolist != "") qry += " and " +
		// myCondition("a.noteno", orderno, 1);
		qry += "\n    and a.noteno='" + phnoteno + "'";
		qry += "  ;";
		qry += "\n   v_row cur_data%rowtype;";
		qry += "\n  begin";
		qry += "\n    for v_row in cur_data loop";
		qry += "\n       v_amount:=v_row.amount;";

		qry += "\n      if v_amount>0 then ";
		qry += "\n         insert into wareoutm (id,accid,noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,curr,remark0,lastdate)";
		qry += "\n         values ( wareoutm_id.nextval," + Accid + ",'" + Noteno
				+ "',v_row.wareid,v_row.colorid,v_row.sizeid,v_row.saleid,v_row.amount,v_row.price0,v_row.discount,v_row.price,v_row.curr,v_row.remark0,sysdate); ";
		qry += "\n      end if;";
		qry += "\n    end loop;";
		qry += "\n  end;";
		qry += "\n  select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n  update wareouth set orderno=v_orderno,handno='" + phnoteno + "',ywnoteno='" + phnoteno //
				+ "',remark=v_remark,totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno='" + Noteno + "';";
		// statetag:0=草稿,1=提交,2=审核,3=配货中，4=配货完，5=已转出库单
		// qry += "\n update warepeih set statetag=5 where accid=" + accid + "
		// and noteno='" + phnoteno + "';";
		qry += "\n   update warepeih set xsnoteno='" + Noteno + "',statetag=4 where accid=" + Accid + " and noteno='" + phnoteno + "'; ";
		qry += "\n   if length(v_phnoteno)>0 then ";// 如果销售单已关联配货，把原配货单释放
		qry += "\n      update warepeih set xsnoteno='',statetag=1 where accid=" + Accid + " and noteno=v_phnoteno;";
		qry += "\n   end if;";
		qry += "\n   commit;";
		qry += "\n end;";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作导常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	//excel转销售出库
	public int doExcel2wareout(JSONObject jsonObject) {
		String handno = jsonObject.has("handno") ? jsonObject.getString("handno").replace("'", "''") : "";
		String notedate = jsonObject.has("notedate") ? jsonObject.getString("notedate").replace("'", "''") : "";
		String housename = jsonObject.has("housename") ? jsonObject.getString("housename").replace("'", "''") : "";
		String vipno = jsonObject.has("vipno") ? jsonObject.getString("vipno").replace("'", "''") : "";
		String saleman = jsonObject.has("saleman") ? jsonObject.getString("saleman").replace("'", "''") : "";
		String remark = jsonObject.has("remark") ? jsonObject.getString("remark").replace("'", "''") : "";
		//System.out.println("1111");
		//12345678901234567890
		//yyyy-mm-dd hh:mm:ss

		//		2017-10-16 15:24:00
		if (notedate.length() == 10)
			notedate += " " + Func.subString(Newdatetime, 12, 8);
		else if (notedate.length() == 16)
			notedate += Func.subString(Newdatetime, 18, 2);
		else if (notedate.length() != 19) {
			errmess = "日期notedate不是一个有效值！";
			return 0;
		}

		if (Func.subString(notedate, 1, 10).compareTo(Calcdate) <= 0) {
			errmess = "单据日期不能在登账日:" + Calcdate + "之前！";
			return 0;
		}

		if (housename.length() <= 0) {
			errmess = "店铺housename不是一个有效值！";
			return 0;
		}
		if (saleman.length() <= 0) {
			errmess = "销售人saleman不是一个有效值！";
			return 0;
		}
		//		System.out.println("2222");
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_wareid number(10); ");
		strSql.append("\n   v_sizeid number(10); ");
		strSql.append("\n   v_colorid number(10); ");
		strSql.append("\n   v_brandid number(10); ");
		strSql.append("\n   v_payid number(10); ");

		strSql.append("\n   v_houseid number(10); ");
		strSql.append("\n   v_guestid number(10); ");
		strSql.append("\n   v_salemanid number(10); ");
		strSql.append("\n   v_totalamt number; ");
		strSql.append("\n   v_totalcurr number; ");
		strSql.append("\n   v_rs number; ");
		strSql.append("\n   v_noteno varchar2(20);");
		strSql.append("\n   v_sizegroupno  varchar2(60);");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n   v_notedate date;");
		strSql.append("\n begin ");
		strSql.append("\n   begin");
		strSql.append("\n     select payid into v_payid from payway where accid=" + Accid + " and payno='0' and rownum=1;");
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n     v_payid:=0;");
		strSql.append("\n   end;");
		strSql.append("\n   v_guestid:=0;v_salemanid:=0;v_rs:=0;");
		strSql.append("\n   begin");
		strSql.append("\n     select houseid into v_houseid from warehouse where accid=" + Accid + " and rownum=1 and noused=0 and statetag=1 and housename='" + housename + "';");
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n     v_retcs:='0店铺:" + housename + " 未找到！';");
		strSql.append("\n     goto exit;");
		strSql.append("\n   end;");
		if (vipno.length() > 0) {
			strSql.append("\n   begin");
			strSql.append("\n     select guestid into v_guestid from guestvip where accid=" + Accid + " and rownum=1 and statetag=1 and vipno='" + vipno + "';");
			strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
			strSql.append("\n     v_retcs:='0会员:" + vipno + " 未找到！';");
			strSql.append("\n     goto exit;");
			strSql.append("\n   end;");
		}
		if (saleman.length() > 0) {
			strSql.append("\n   begin");
			strSql.append("\n     select epid into v_salemanid from employe where accid=" + Accid + " and rownum=1 and noused=0 and statetag=1 and epname='" + saleman + "';");
			strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
			strSql.append("\n     v_retcs:='0销售人:" + saleman + " 未找到！';");
			strSql.append("\n     goto exit;");
			strSql.append("\n   end;");
		}
		strSql.append("\n   select 'XX'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareouth");
		strSql.append("\n   where accid=" + Accid + " and ntid=0 and LENGTH(noteno)=15 and substr(noteno,1,10)= 'XX'||To_Char(SYSDATE,'yyyymmdd');");

		//		strSql.append("\n   v_id:=wareouth_id.nextval; ");
		strSql.append("\n   insert into wareouth (id,accid,noteno,notedate,houseid,guestid,remark,handno,ntid,operant)");
		strSql.append("\n   values (wareouth_id.nextval," + Accid + ",v_noteno,to_date('" + notedate + "','yyyy-mm-dd hh24:mi:ss'),v_houseid,v_guestid,'" + remark + "','" + handno + "',0,'" + Operant + "');");
		JSONArray jsonArray = jsonObject.getJSONArray("warereclist");
		//		System.out.println("3333");

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			String wareno = jsonObject2.has("wareno") ? jsonObject2.getString("wareno") : "";
			String colorname = jsonObject2.has("colorname") ? jsonObject2.getString("colorname") : "";
			String sizename = jsonObject2.has("sizename") ? jsonObject2.getString("sizename") : "";
			if (wareno.length() == 0 || colorname.length() == 0 || sizename.length() == 0) {
				errmess = "商品/颜色/尺码不是一个有效值！";
				return 0;
			}
			Float amount = jsonObject2.has("amount") ? Float.parseFloat(jsonObject2.getString("amount")) : 0;
			Float price = jsonObject2.has("price") ? Float.parseFloat(jsonObject2.getString("price")) : 0;
			Float curr = jsonObject2.has("curr") ? Float.parseFloat(jsonObject2.getString("curr")) : 0;
			String remark0 = jsonObject2.has("remark0") ? jsonObject2.getString("remark0") : "";
			if (amount == 0) {
				errmess = "数量不是一个有效值！";
				return 0;
			}

			strSql.append("\n   begin");
			strSql.append("\n     select wareid,brandid,sizegroupno into v_wareid,v_brandid,v_sizegroupno from warecode where accid=" + Accid + " and rownum=1 and noused=0 and statetag=1 and wareno='" + wareno + "';");
			strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
			strSql.append("\n     v_retcs:='0商品:" + wareno + " 未找到！';");
			strSql.append("\n     rollback;");
			strSql.append("\n     goto exit;");
			strSql.append("\n   end;");

			strSql.append("\n   begin");
			strSql.append("\n     select colorid into v_colorid from (");
			strSql.append("\n     select colorid,brandid from colorcode where accid=" + Accid + " and noused=0 and statetag=1 and (brandid=v_brandid or brandid=0) and colorname='" //
					+ colorname + "' order by brandid desc");
			strSql.append("\n     ) where rownum=1;");
			strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
			strSql.append("\n     v_retcs:='0颜色:" + colorname + " 未找到！';");
			strSql.append("\n     rollback;");
			strSql.append("\n     goto exit;");
			strSql.append("\n   end;");

			strSql.append("\n   begin");
			strSql.append("\n     select sizeid into v_sizeid from sizecode where accid=" + Accid + " and rownum=1 and noused=0 and statetag=1 and groupno=v_sizegroupno and sizename='" + sizename + "';");
			strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
			strSql.append("\n     v_retcs:='0尺码:" + sizename + " 未找到！';");
			strSql.append("\n     rollback;");
			strSql.append("\n     goto exit;");
			strSql.append("\n   end;");

			strSql.append("\n   insert into wareoutm (id,accid,noteno,wareid,colorid,sizeid,amount,price0,discount,price,curr,saleid,remark0)");
			strSql.append("\n   values (wareoutm_id.nextval," + Accid + ",v_noteno,v_wareid,v_colorid,v_sizeid," + amount + "," + price + ",1," + price + ",round(" + curr + ",2) ,0,'" + remark0 + "');");
		}
		strSql.append("\n   if v_salemanid>0 then");
		strSql.append("\n      delete from waresaleman where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n      insert into waresaleman (id,accid,noteno,epid)");
		strSql.append("\n      values (waresaleman_id.nextval," + Accid + ",v_noteno,v_salemanid);");
		strSql.append("\n      v_rs:=1;");
		strSql.append("\n   end if;");

		strSql.append("\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from wareoutm where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   update wareouth set totalcurr=v_totalcurr,totalamt=v_totalamt,rs=v_rs,statetag=1 where accid=" + Accid + " and noteno=v_noteno;");

		strSql.append("\n   if v_payid>0 then");  //默认现金
		strSql.append("\n      delete from waresalepay where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n      insert into waresalepay (id,accid,noteno,payid,curr)");
		strSql.append("\n      values (waresalepay_id.nextval," + Accid + ",v_noteno,v_payid,v_totalcurr);");
		strSql.append("\n   end if;");

		strSql.append("\n   commit;");
		strSql.append("\n   p_wareoutpaylist(" + Accid + ",v_noteno); ");
		strSql.append("\n   v_retcs:='1'||v_noteno;");
		strSql.append("\n   <<exit>> ");
		strSql.append("\n   select v_retcs into :retcs from dual; ");
		strSql.append("\n end; ");
		//		System.out.println(strSql.toString());
		//		LogUtils.LogDebugWrite("execl2wareout", strSql.toString());
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

	// 离线开票转销售出库
	public int doOffline2wareout(JSONObject jsonObject) {
		// System.out.println("11111");
		if (Houseid == null || Houseid == 0 || Ntid == null || Ntid > 2) {
			errmess = "houseid或ntid不是一个有效值！";
			return 0;
		}
		if (!jsonObject.has("warereclist")) {
			errmess = "缺少商品明细记录！";
			return 0;
		}
		if (Ntid > 0 && (Custid == null || Custid == 0)) {
			errmess = "custid不是一个有效值！";
			return 0;
		}
		if (Func.isNull(Operant)) {
			errmess = "operant不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		// if (Noteno != null) {
		strSql1.append("noteno,");
		strSql2.append("v_noteno ,");

		strSql1.append("notedate,");
		strSql2.append("v_notedate ,");
		// }
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		// if (Notedate != null) {
		// strSql1.append("notedate,");
		// strSql2.append("to_date('" + Notedate + "','yyyy-mm-dd
		// hh24:mi:ss'),");
		// }
		if (Custid != null) {
			strSql1.append("custid,");
			strSql2.append(Custid + ",");
		}
		if (Guestid != null) {
			strSql1.append("guestid,");
			strSql2.append(Guestid + ",");
		}
		if (Houseid != null) {
			strSql1.append("houseid,");
			strSql2.append(Houseid + ",");
		}
		if (Ntid != null) {
			strSql1.append("ntid,");
			strSql2.append(Ntid + ",");
		}
		if (Handno != null) {
			strSql1.append("handno,");
			strSql2.append("'" + Handno + "',");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		strSql1.append("operant,");
		strSql2.append("'" + Operant + "',");
		// }
		if (Checkman != null) {
			strSql1.append("checkman,");
			strSql2.append("'" + Checkman + "',");
		}
		// if (Statetag != null) {
		strSql1.append("statetag,");
		strSql2.append("1,");
		// if (Rs != null) {
		// strSql1.append("rs,");
		// strSql2.append(Rs + ",");
		// }
		if (Checkcurr != null) {
			strSql1.append("checkcurr,");
			strSql2.append(Checkcurr + ",");
		}
		if (Changecurr != null) {
			strSql1.append("changecurr,");
			strSql2.append(Changecurr + ",");
		}
		if (Freecurr != null) {
			strSql1.append("freecurr,");
			strSql2.append(Freecurr + ",");
		}
		if (Zpaycurr != null) {
			strSql1.append("zpaycurr,");
			strSql2.append(Zpaycurr + ",");
		}
		if (Cent != null) {
			strSql1.append("cent,");
			strSql2.append(Cent + ",");
		}
		if (Jfcurr != null) {
			strSql1.append("jfcurr,");
			strSql2.append(Jfcurr + ",");
		}
		if (Usecent != null) {
			strSql1.append("usecent,");
			strSql2.append(Usecent + ",");
		}
		if (Dptid != null) {
			strSql1.append("dptid,");
			strSql2.append(Dptid + ",");
		}

		if (Totalcost != null) {
			strSql1.append("totalcost,");
			strSql2.append(Totalcost + ",");
		}
		// if (Tohouseid != null) {
		// strSql1.append("tohouseid,");
		// strSql2.append(Tohouseid + ",");
		// }
		// if (Messid != null) {
		// strSql1.append("messid,");
		// strSql2.append("'" + Messid + "',");
		// }
		if (Yhjcurr != null) {
			strSql1.append("yhjcurr,");
			strSql2.append(Yhjcurr + ",");
		}
		// if (Vcpid != null) {
		// strSql1.append("vcpid,");
		// strSql2.append(Vcpid + ",");
		// }
		// if (Buy_accid != null) {
		// strSql1.append("buy_accid,");
		// strSql2.append(Buy_accid + ",");
		// }
		// if (Buy_noteno != null) {
		// strSql1.append("buy_noteno,");
		// strSql2.append("'" + Buy_noteno + "',");
		// }
		// if (Paynoteno_zfb != null) {
		// strSql1.append("paynoteno_zfb,");
		// strSql2.append("'" + Paynoteno_zfb + "',");
		// }
		// if (Paynoteno_wx != null) {
		// strSql1.append("paynoteno_wx,");
		// strSql2.append("'" + Paynoteno_wx + "',");
		// }
		// if (Fhtag != null) {
		// strSql1.append("fhtag,");
		// strSql2.append(Fhtag + ",");
		// }
		if (Ywnoteno != null) {
			strSql1.append("ywnoteno,");
			strSql2.append("'" + Ywnoteno + "',");
		}
		// if (Paynoteno_lkl != null) {
		// strSql1.append("paynoteno_lkl,");
		// strSql2.append("'" + Paynoteno_lkl + "',");
		// }

		if (Func.subString(Notedate, 1, 10).compareTo(Calcdate) <= 0) {
			errmess = "不允许上传登账日" + Calcdate + "及之前的单据！";
			return 0;
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_rs number; ");
		strSql.append("\n   v_totalamt number; ");
		strSql.append("\n   v_totalcurr number; ");
		strSql.append("\n   v_totalcost number; ");
		strSql.append("\n   v_noteno varchar2(20);");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n   v_sknoteno varchar2(20);");
		strSql.append("\n   v_notedate date;");
		strSql.append("\n begin ");
		strSql.append("\n   v_totalcost:=0;");

		if (Totalcost != null) {
			if (Ntid == 2)
				strSql.append("\n   v_totalcost:=-" + Totalcost + ";");
			else
				strSql.append("\n   v_totalcost:=" + Totalcost + ";");
		}
		strSql.append("\n   v_notedate:=to_date('" + Notedate + "','yyyy-mm-dd hh24:mi:ss');");
		strSql.append("\n   begin ");
		if (Ntid == 2) {// 退货
			strSql.append("\n   select 'XT'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareouth");
			strSql.append("\n   where accid=" + Accid + " and ntid=" + Ntid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'XT'||To_Char(SYSDATE,'yyyymmdd');");
		} else if (Ntid == 1) {// 批发
			strSql.append("\n   select 'XS'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareouth");
			strSql.append("\n   where accid=" + Accid + " and ntid=" + Ntid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'XS'||To_Char(SYSDATE,'yyyymmdd');");
		} else {// 零售
			strSql.append("\n   select 'XX'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareouth");
			strSql.append("\n   where accid=" + Accid + " and ntid=" + Ntid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'XX'||To_Char(SYSDATE,'yyyymmdd');");
		}

		strSql.append("\n   v_id:=wareouth_id.nextval; ");
		strSql.append("\n   insert into wareouth (" + strSql1.toString() + ")");
		strSql.append("\n   values (" + strSql2.toString() + ");");
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
			long saleid = jsonObject2.has("saleid") ? Long.parseLong(jsonObject2.getString("saleid")) : 0;
			if (wareid == 0 || colorid == 0 || sizeid == 0) {
				errmess = "wareid或colorid或sizeid不是一个有效值！";
				return 0;
			}
			strSql.append("\n  insert into wareoutm (id,accid,noteno,wareid,colorid,sizeid,amount,price0,discount,price,curr,saleid,remark0)");
			strSql.append("\n  values (wareoutm_id.nextval," + Accid + ",v_noteno," + wareid + "," + colorid + "," + sizeid + "," + amount + "," + price0//
					+ "," + discount + "," + price + "," + curr + "," + saleid + ",'" + remark0 + "');");
		}
		strSql.append("\n   delete from waresaleman where accid=" + Accid + " and noteno=v_noteno;");
		// 销售人
		if (jsonObject.has("salemanlist")) {
			jsonArray = jsonObject.getJSONArray("salemanlist");
			strSql.append("\n  v_rs:=0;");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				long epid = jsonObject2.has("epid") ? Long.parseLong(jsonObject2.getString("epid")) : 0;
				strSql.append("\n  insert into waresaleman (id,accid,noteno,epid)");
				strSql.append("\n  values (waresaleman_id.nextval," + Accid + ",v_noteno," + epid + ");");
				strSql.append("\n  v_rs:=v_rs+1;");
			}
		}
		if (Ntid > 0)// 批发有销售费用
		{
			if (jsonObject.has("costlist")) {
				strSql.append("\n   delete from wareoutcost where accid=" + Accid + " and noteno=v_noteno;");

				jsonArray = jsonObject.getJSONArray("costlist");
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject2 = jsonArray.getJSONObject(i);
					long cgid = jsonObject2.has("cgid") ? Long.parseLong(jsonObject2.getString("cgid")) : 0;
					float curr = jsonObject2.has("curr") ? Float.parseFloat(jsonObject2.getString("curr")) : 0;
					strSql.append("\n  insert into wareoutcost (id,accid,noteno,cgid,curr)");
					strSql.append("\n  values wareoutcost_id.nextval," + Accid + ",v_noteno," + cgid + "," + curr + ");");
				}
			}
		}
		strSql.append("\n   delete from incomecurr where accid=" + Accid + " and ynoteno=v_noteno ;");
		if (jsonObject.has("paylist")) {// 结算方式
			jsonArray = jsonObject.getJSONArray("paylist");
			strSql.append("\n   delete from waresalepay where accid=" + Accid + " and noteno=v_noteno;");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				long payid = jsonObject2.has("payid") ? Long.parseLong(jsonObject2.getString("payid")) : 0;
				float curr = jsonObject2.has("curr") ? Float.parseFloat(jsonObject2.getString("curr")) : 0;
				if (Ntid == 0) {// 零售
					strSql.append("\n  insert into waresalepay (id,accid,noteno,payid,curr)");
					strSql.append("\n  values (waresalepay_id.nextval," + Accid + ",v_noteno," + payid + "," + curr + ");");

				} else if (Ntid == 1) {// 批发
					strSql.append("\n   select 'SK'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_sknoteno from incomecurr");
					strSql.append("\n   where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'SK'||To_Char(SYSDATE,'yyyymmdd');");

					strSql.append("\n   insert into incomecurr (id, accid,custid,houseid,noteno,notedate,payid,curr,remark,handno,statetag,operant,checkman,lastop,ynoteno,lastdate)");
					strSql.append("\n   values (INCOMECURR_ID.NEXTVAL," + Accid + "," + Custid + "," + Houseid + ",v_sknoteno,v_notedate," + payid);
					strSql.append("\n   ," + curr + ",'','',1,'" + Operant + "','','" + Operant + "',v_noteno,sysdate); ");
				} else// 退货
				{
					strSql.append("\n   select 'SK'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_sknoteno from incomecurr");
					strSql.append("\n   where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'SK'||To_Char(SYSDATE,'yyyymmdd');");

					strSql.append("\n   insert into incomecurr (id, accid,custid,houseid,noteno,notedate,payid,curr,remark,handno,statetag,operant,checkman,lastop,ynoteno,lastdate)");
					strSql.append("\n   values (INCOMECURR_ID.NEXTVAL," + Accid + "," + Custid + "," + Houseid + ",v_sknoteno,v_notedate," + payid);
					strSql.append("\n   ,-" + curr + ",'','',1,'" + Operant + "','','" + Operant + "',v_noteno,sysdate); ");

				}
			}
		}
		if (Freecurr > 0) {// 有折让
			if (Ntid == 1) {

				// strSql.append("\n delete from incomecurr where accid=" +
				// Accid + " and ynoteno=v_noteno and payid=0;");

				strSql.append("\n  select 'SZ'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_sknoteno from INCOMECURR ");
				strSql.append("\n  where accid=" + Accid + "  and LENGTH(noteno)=15 and substr(noteno,1,10)= 'SZ'||To_Char(SYSDATE,'yyyymmdd');");

				strSql.append("\n  insert into incomecurr (id, accid,custid,houseid,noteno,notedate,payid,curr,remark,handno,statetag,operant,checkman,lastop,ynoteno,lastdate)");
				strSql.append("\n  values (INCOMECURR_ID.NEXTVAL," + Accid + "," + Custid + "," + Houseid + ",v_sknoteno,v_notedate,0");
				strSql.append("\n ," + Freecurr + ",'','',1,'" + Operant + "','','" + Operant + "',v_noteno,sysdate); ");
				// 生成勾单记录
				// notetype:0采购(退货) 1物料采购(退货) 2销售(退库) 3物料销售（退库）
				// fs:0采购付款勾采购单1采购付款勾退货单5采购退款勾退货单2销售收款勾销售单3销售收款勾退货单4销售退款勾退货单

				// strSql.append("\n insert into currlink
				// (id,accid,notetype,noteno,pnoteno,fs,curr,lastop)");
				// strSql.append("\n values (currlink_id.nextval," + Accid +
				// ",'2',v_noteno,v_sknoteno,'2'," + Freecurr + ",'" + Operant +
				// "');");

				// strSql.append("\n insert into LOGRECORD
				// (id,accid,progname,remark,lastop)");
				// strSql.append("\n values (LOGRECORD_id.nextval," + Accid +
				// ",'批发销售折让','【增加记录】销售单号:" + Noteno + ";单据号:'||v_noteno||';金额:"
				// + Freecurr + "','" + Operant + "');");

				// Gdcurr += Freecurr;
			} else if (Ntid == 2) {// 退货
				strSql.append("\n  select 'SZ'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_sknoteno from INCOMECURR ");
				strSql.append("\n  where accid=" + Accid + "  and LENGTH(noteno)=15 and substr(noteno,1,10)= 'SZ'||To_Char(SYSDATE,'yyyymmdd');");

				strSql.append("\n  insert into incomecurr (id, accid,custid,houseid,noteno,notedate,payid,curr,remark,handno,statetag,operant,checkman,lastop,ynoteno,lastdate)");
				strSql.append("\n  values (INCOMECURR_ID.NEXTVAL," + Accid + "," + Custid + "," + Houseid + ",v_sknoteno,v_notedate,0");
				strSql.append("\n ,-" + Freecurr + ",'','',1,'" + Operant + "','','" + Operant + "',v_noteno,sysdate); ");

			}
		}

		strSql.append("\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from wareoutm where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   update wareouth set totalcurr=v_totalcurr+v_totalcost,totalamt=v_totalamt,rs=v_rs where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   p_wareoutpaylist(" + Accid + ",v_noteno); ");
		if (Ntid == 0)// 零售
			if (Guestid != null && Guestid > 0 && Cent != null && Cent > 0) { // 有会员积分
				strSql.append("\n   ");
				// aaaa
				strSql.append("\n      insert into guestcent (id,guestid,notedate,houseid,cent,remark,fs,ly,xsnoteno,operant)");// ly:0=手工输入,1=商场零售,2=店铺零售
				strSql.append("\n      values (guestcent_id.nextval," + Guestid + ",sysdate," + Houseid + "," + Cent + ",'消费增加积分 №'||v_noteno,0,2,v_noteno,'" + Operant + "'); ");
				// strSql.append("\n update guestvip set balcent=nvl((select
				// sum(case fs when 0 then cent else -cent end) from guestcent
				// where guestvip.guestid=guestcent.guestid),0)");
				strSql.append("\n      update guestvip set balcent=nvl((select sum(decode(fs,0,cent,-cent)) from guestcent where guestvip.guestid=guestcent.guestid),0)");
				strSql.append("\n      where guestid=" + Guestid + "; ");
			}

		strSql.append("\n   v_retcs:='1'||v_noteno;");
		strSql.append("\n   commit;");
		strSql.append("\n   exception");
		strSql.append("\n   when others then ");
		strSql.append("\n   begin");
		strSql.append("\n     rollback;");
		strSql.append("\n     v_retcs:='0操作失败!';");
		strSql.append("\n   end;");
		strSql.append("\n  end; ");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs into :retcs from dual;");
		strSql.append("\n end;");
		// LogUtils.LogDebugWrite("doOffline2wareout", strSql.toString());
		// System.out.print(strSql.toString());
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
		qry += "\n   select checkman,cwcheck,ntid into v_checkman,v_cwcheck,v_ntid from wareouth where accid=" + Accid + " and noteno='" + Noteno + "';";
		if (checkid == 2) {// 财务审核
			qry += "\n   if length(v_checkman)=0 then ";
			qry += "\n      v_retcs:='0未经过业务审核的单据，不允许财务审核！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   if length(v_cwcheck)>0 then ";
			qry += "\n      v_retcs:='0单据已进行财务审核！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update wareouth set cwcheck='" + Operant + "' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【财务审核】';";

		} else if (checkid == 3) {// 财务取消审核
			qry += "\n   if v_cwcheck<>'" + Operant + "' then ";
			qry += "\n      v_retcs:='0系统不允许取消其他财务审核的单据！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update wareouth set cwcheck='' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【取消财务审核】';";
		} else if (checkid == 1) {// 业务审核
			qry += "\n   if length(v_checkman)>0 then ";
			qry += "\n      v_retcs:='0单据业务已审核！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update wareouth set checkman='" + Operant + "' where accid=" + Accid + " and noteno='" + Noteno + "';";
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
			qry += "\n   update wareouth set checkman='' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【取消业务审核】';";
		}
		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",case v_ntid when 0 then '店铺零售' when 1 then '批发销售' else '批发退货' end,v_mess||'单据号:" + Noteno + "','" + Operant + "');";
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
		if (Custid != null) {
			strSql1.append("Custid,");
			strSql2.append(Custid + ",");
		}
		if (Guestid != null) {
			strSql1.append("Guestid,");
			strSql2.append(Guestid + ",");
		}
		if (Dptid != null) {
			strSql1.append("Dptid,");
			strSql2.append(Dptid + ",");
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
		if (Ntid != null) {
			strSql1.append("Ntid,");
			strSql2.append(Ntid + ",");
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

		if (Ntid == 2) {// 退货
			strSql.append("\n   select 'XT'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareouth");
			strSql.append("\n   where accid=" + Accid + " and ntid=" + Ntid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'XT'||To_Char(SYSDATE,'yyyymmdd');");
		} else if (Ntid == 1) {// 批发
			strSql.append("\n   select 'XS'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareouth");
			strSql.append("\n   where accid=" + Accid + " and ntid=" + Ntid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'XS'||To_Char(SYSDATE,'yyyymmdd');");
		} else {// 零售
			strSql.append("\n   select 'XX'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareouth");
			strSql.append("\n   where accid=" + Accid + " and ntid=" + Ntid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'XX'||To_Char(SYSDATE,'yyyymmdd');");
		}
		strSql.append("\n     v_id:=wareouth_id.nextval; ");
		strSql.append("\n     insert into wareouth (" + strSql1.toString() + ")");
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
			strSql.append("\n  insert into wareoutm (id,accid,noteno,wareid,colorid,sizeid,amount,price0,discount,price,curr,remark0)");
			strSql.append("\n  values (wareoutm_id.nextval," + Accid + ",v_noteno," + wareid + "," + colorid + "," + sizeid + "," + amount //
					+ "," + price0 + "," + discount + "," + price + "," + curr + ",'" + remark0 + "');");
		}

		strSql.append("\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from wareoutm where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   update wareouth set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   v_retcs:='1'||'\"msg\":\"操作成功\",\"NOTENO\":\"'||v_noteno||'\"';");
		//		strSql.append("\n   select '\"ID\":\"'||id||'\",\"NOTENO\":\"'||noteno||'\",\"NOTEDATE\":\"'||to_char(notedate,'yyyy-mm-dd hh24:mi:ss')||'\"' into v_retcs from firstpaycurr where id=v_id;");

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
		qry += "\n   v_totalamt number;";
		qry += "\n   v_totalcurr number;";
		qry += "\n   v_custid number(10);";
		qry += "\n begin";
		qry += "\n    begin";
		qry += "\n      select a.custid,b.discount,b.pricetype into v_custid,v_discount,v_pricetype from wareouth a join customer b on a.custid=b.custid";
		qry += "\n      where a.accid=" + Accid + " and a.noteno='" + Noteno + "';";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n      v_retcs:='0未找到销售单！';";
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
		qry += "\n   begin";
		qry += "\n     v_count:=0;";
		qry += "\n     for v_row in cur_data loop";
		qry += "\n         v_price:=round(v_row.price0*v_discount,2);";
		qry += "\n         v_curr:=round(v_row.amount*v_price,2);";
		qry += "\n         insert into wareoutm (id,accid,noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,curr)";
		qry += "\n         values (wareoutm_id.nextval," + Accid + ",'" + Noteno + "',v_row.wareid,v_row.colorid,v_row.sizeid,2,v_row.amount,v_row.price0,v_discount,v_price,v_curr);";
		qry += "\n         v_count:=v_count+1;";
		qry += "\n         if v_count>1000 then";
		qry += "\n            commit;";
		qry += "\n            v_count:=0;";
		qry += "\n         end if;";
		qry += "\n     end loop;";
		qry += "\n   end;";
		qry += "\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   update wareouth set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno='" + Noteno + "';";
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