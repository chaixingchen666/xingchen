package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-15 23:13:53
//*************************************
public class Viporderh implements java.io.Serializable {
	private Float Id;
	private Long Vipid;
	private String Noteno;
	private Date Notedate;
	private Long Onhouseid;
	private Integer Statetag;
	private String Xsnoteno;
	private String Address;
	private String Tel;
	private String Linkman;
	private String Remark;
	private Long Totalamt;
	private Float Totalcurr;
	private Float Curryf;
	private Float Paycurr;
	private Integer Paytag;
	private Date Arrivedate;
	private Date Receivedate;
	private Date Yddate;
	private String Ydnoteno;
	private Long Carryid;
	private Date Lastdate;
	private Float Checkcurr;
	private String Paynoteno_zfb;
	private String Paynoteno_wx;
	private String Delayremark;
	private Float Tkcurr;
	private Integer Tkyy;
	private String Tkremark;
	private Integer Tktag;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setVipid(Long vipid) {
		this.Vipid = vipid;
	}

	public Long getVipid() {
		return Vipid;
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

	public void setOnhouseid(Long onhouseid) {
		this.Onhouseid = onhouseid;
	}

	public Long getOnhouseid() {
		return Onhouseid;
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

	public void setAddress(String address) {
		this.Address = address;
	}

	public String getAddress() {
		return Address;
	}

	public void setTel(String tel) {
		this.Tel = tel;
	}

	public String getTel() {
		return Tel;
	}

	public void setLinkman(String linkman) {
		this.Linkman = linkman;
	}

	public String getLinkman() {
		return Linkman;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setTotalamt(Long totalamt) {
		this.Totalamt = totalamt;
	}

	public Long getTotalamt() {
		return Totalamt;
	}

	public void setTotalcurr(Float totalcurr) {
		this.Totalcurr = totalcurr;
	}

	public Float getTotalcurr() {
		return Totalcurr;
	}

	public void setCurryf(Float curryf) {
		this.Curryf = curryf;
	}

	public Float getCurryf() {
		return Curryf;
	}

	public void setPaycurr(Float paycurr) {
		this.Paycurr = paycurr;
	}

	public Float getPaycurr() {
		return Paycurr;
	}

	public void setPaytag(Integer paytag) {
		this.Paytag = paytag;
	}

	public Integer getPaytag() {
		return Paytag;
	}

	public void setArrivedate(Date arrivedate) {
		this.Arrivedate = arrivedate;
	}

	public String getArrivedate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Arrivedate);
	}

	public void setReceivedate(Date receivedate) {
		this.Receivedate = receivedate;
	}

	public String getReceivedate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Receivedate);
	}

	public void setYddate(Date yddate) {
		this.Yddate = yddate;
	}

	public String getYddate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Yddate);
	}

	public void setYdnoteno(String ydnoteno) {
		this.Ydnoteno = ydnoteno;
	}

	public String getYdnoteno() {
		return Ydnoteno;
	}

	public void setCarryid(Long carryid) {
		this.Carryid = carryid;
	}

	public Long getCarryid() {
		return Carryid;
	}

	public void setCheckcurr(Float checkcurr) {
		this.Checkcurr = checkcurr;
	}

	public Float getCheckcurr() {
		return Checkcurr;
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

	public void setDelayremark(String delayremark) {
		this.Delayremark = delayremark;
	}

	public String getDelayremark() {
		return Delayremark;
	}

	public void setTkcurr(Float tkcurr) {
		this.Tkcurr = tkcurr;
	}

	public Float getTkcurr() {
		return Tkcurr;
	}

	public void setTkyy(Integer tkyy) {
		this.Tkyy = tkyy;
	}

	public Integer getTkyy() {
		return Tkyy;
	}

	public void setTkremark(String tkremark) {
		this.Tkremark = tkremark;
	}

	public String getTkremark() {
		return Tkremark;
	}

	public void setTktag(Integer tktag) {
		this.Tktag = tktag;
	}

	public Integer getTktag() {
		return Tktag;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Noteno == null || Noteno.equals("") || Vipid == 0) {
			errmess = "参数无效&noteno=?&vipid=?！";
			return 0;

		}

		StringBuilder strSql = new StringBuilder();

		// 只能删除关闭状态的订单
		// statetag:0=草稿 1=提交 2=付款，3=发货，4=签收，5=退款，6=关闭
		strSql.append("declare ");
		strSql.append("\n    v_count number;");
		strSql.append("\n    v_retcs varchar2(200);");
		strSql.append("\n begin ");
		strSql.append("\n   select count(*) into v_count from viporderh where vipid=" + Vipid + " and noteno='" + Noteno + "' and statetag=6; ");
		strSql.append("\n   if v_count=0 then ");
		strSql.append("\n      v_retcs:= '0订单未找到或未关闭，不允许删除！' ;");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   delete from viporderh where vipid=" + Vipid + " and noteno='" + Noteno + "';");
		strSql.append("\n   delete from viporderm where vipid=" + Vipid + " and noteno='" + Noteno + "';");
		strSql.append("\n   v_retcs:= '1删除成功！' ;");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		strSql.append("\n end ;");
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
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		if (Id != null) {
			strSql1.append("id,");
			strSql2.append("'" + Id + "',");
		}
		if (Vipid != null) {
			strSql1.append("vipid,");
			strSql2.append(Vipid + ",");
		}
		if (Noteno != null) {
			strSql1.append("noteno,");
			strSql2.append("'" + Noteno + "',");
		}
		if (Notedate != null) {
			strSql1.append("notedate,");
			strSql2.append("'" + Notedate + "',");
		}
		if (Onhouseid != null) {
			strSql1.append("onhouseid,");
			strSql2.append(Onhouseid + ",");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Xsnoteno != null) {
			strSql1.append("xsnoteno,");
			strSql2.append("'" + Xsnoteno + "',");
		}
		if (Address != null) {
			strSql1.append("address,");
			strSql2.append("'" + Address + "',");
		}
		if (Tel != null) {
			strSql1.append("tel,");
			strSql2.append("'" + Tel + "',");
		}
		if (Linkman != null) {
			strSql1.append("linkman,");
			strSql2.append("'" + Linkman + "',");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Totalamt != null) {
			strSql1.append("totalamt,");
			strSql2.append(Totalamt + ",");
		}
		if (Totalcurr != null) {
			strSql1.append("totalcurr,");
			strSql2.append("'" + Totalcurr + "',");
		}
		if (Curryf != null) {
			strSql1.append("curryf,");
			strSql2.append("'" + Curryf + "',");
		}
		if (Paycurr != null) {
			strSql1.append("paycurr,");
			strSql2.append("'" + Paycurr + "',");
		}
		if (Paytag != null) {
			strSql1.append("paytag,");
			strSql2.append(Paytag + ",");
		}
		if (Arrivedate != null) {
			strSql1.append("arrivedate,");
			strSql2.append("'" + Arrivedate + "',");
		}
		if (Receivedate != null) {
			strSql1.append("receivedate,");
			strSql2.append("'" + Receivedate + "',");
		}
		if (Yddate != null) {
			strSql1.append("yddate,");
			strSql2.append("'" + Yddate + "',");
		}
		if (Ydnoteno != null) {
			strSql1.append("ydnoteno,");
			strSql2.append("'" + Ydnoteno + "',");
		}
		if (Carryid != null) {
			strSql1.append("carryid,");
			strSql2.append(Carryid + ",");
		}
		if (Checkcurr != null) {
			strSql1.append("checkcurr,");
			strSql2.append("'" + Checkcurr + "',");
		}
		if (Paynoteno_zfb != null) {
			strSql1.append("paynoteno_zfb,");
			strSql2.append("'" + Paynoteno_zfb + "',");
		}
		if (Paynoteno_wx != null) {
			strSql1.append("paynoteno_wx,");
			strSql2.append("'" + Paynoteno_wx + "',");
		}
		if (Delayremark != null) {
			strSql1.append("delayremark,");
			strSql2.append("'" + Delayremark + "',");
		}
		if (Tkcurr != null) {
			strSql1.append("tkcurr,");
			strSql2.append("'" + Tkcurr + "',");
		}
		if (Tkyy != null) {
			strSql1.append("tkyy,");
			strSql2.append(Tkyy + ",");
		}
		if (Tkremark != null) {
			strSql1.append("tkremark,");
			strSql2.append("'" + Tkremark + "',");
		}
		if (Tktag != null) {
			strSql1.append("tktag,");
			strSql2.append(Tktag + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=Viporderh_id.nextval; ");
		strSql.append("   insert into Viporderh (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("  select v_id into :id from dual; ");
		strSql.append(" end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		// id = Long.parseLong(param.get("id").getParamvalue().toString()+());
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 修改记录
	public int Update() {
		if (Noteno.equals("") || Vipid == 0) {
			errmess = "单据号参数无效&noteno=?&vipid=?";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Viporderh set ");
		// if (Id != null) {
		// strSql.append("id='" + Id + "',");
		// }
		if (Vipid != null) {
			strSql.append("vipid=" + Vipid + ",");
		}
		if (Noteno != null) {
			strSql.append("noteno='" + Noteno + "',");
		}
		if (Notedate != null) {
			strSql.append("notedate='" + Notedate + "',");
		}
		if (Onhouseid != null) {
			strSql.append("onhouseid=" + Onhouseid + ",");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Xsnoteno != null) {
			strSql.append("xsnoteno='" + Xsnoteno + "',");
		}
		if (Address != null) {
			strSql.append("address='" + Address + "',");
		}
		if (Tel != null) {
			strSql.append("tel='" + Tel + "',");
		}
		if (Linkman != null) {
			strSql.append("linkman='" + Linkman + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Totalamt != null) {
			strSql.append("totalamt=" + Totalamt + ",");
		}
		if (Totalcurr != null) {
			strSql.append("totalcurr='" + Totalcurr + "',");
		}
		if (Curryf != null) {
			strSql.append("curryf='" + Curryf + "',");
		}
		if (Paycurr != null) {
			strSql.append("paycurr='" + Paycurr + "',");
		}
		if (Paytag != null) {
			strSql.append("paytag=" + Paytag + ",");
		}
		if (Arrivedate != null) {
			strSql.append("arrivedate='" + Arrivedate + "',");
		}
		if (Receivedate != null) {
			strSql.append("receivedate='" + Receivedate + "',");
		}
		if (Yddate != null) {
			strSql.append("yddate='" + Yddate + "',");
		}
		if (Ydnoteno != null) {
			strSql.append("ydnoteno='" + Ydnoteno + "',");
		}
		if (Carryid != null) {
			strSql.append("carryid=" + Carryid + ",");
		}
		if (Checkcurr != null) {
			strSql.append("checkcurr='" + Checkcurr + "',");
		}
		if (Paynoteno_zfb != null) {
			strSql.append("paynoteno_zfb='" + Paynoteno_zfb + "',");
		}
		if (Paynoteno_wx != null) {
			strSql.append("paynoteno_wx='" + Paynoteno_wx + "',");
		}
		if (Delayremark != null) {
			strSql.append("delayremark='" + Delayremark + "',");
		}
		if (Tkcurr != null) {
			strSql.append("tkcurr='" + Tkcurr + "',");
		}
		if (Tkyy != null) {
			strSql.append("tkyy=" + Tkyy + ",");
		}
		if (Tkremark != null) {
			strSql.append("tkremark='" + Tkremark + "',");
		}
		if (Tktag != null) {
			strSql.append("tktag=" + Tktag + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where vipid=" + Vipid + " and noteno='" + Noteno + "';");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0)
		{
			errmess="操作异常！";
			return 0;
		}
		else
		{
			errmess="操作成功！";
			return 1;
		}
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Viporderh a");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据 ok
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Viporderh a left outer join vipgroup b on a.vipid=b.vipid ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Viporderh");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()+()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	public int doSendViporder(long accid, long houseid, String lastop) {
		if (Noteno == null || Noteno.equals("") || Vipid == 0) {
			errmess = "单据号或会员id参数无效!";
			return 0;
		}
		if (houseid == 0) {
			errmess = "请选择发货店铺！";
			return 0;
		}
		if (Ydnoteno == null || Ydnoteno.equals("")) {
			errmess = "请输入快递单号！";
			return 0;
		}
		if (Carryid == null || Carryid == 0) {
			errmess = "请选择快递公司！";
			return 0;
		}

		// 0=草稿 1=提交 2=付款，3=发货，4=签收，5=退款，6=关闭
		String qry = "declare ";
		qry += "    v_count number;";
		qry += "    v_id number;";
		// qry += " v_guestid number(10);";
		qry += "    v_totalamt number;";
		qry += "    v_totalcurr number;";
		qry += "    v_statetag number(1);";
		qry += "    v_guestid number(10);";
		qry += "    v_onhouseid number(10);";
		qry += "    v_onhousename varchar2(100);";
		qry += "    v_retcs varchar2(200);";
		qry += "    v_noteno varchar2(20);";
		qry += "    v_sysdate date;";
		qry += "\n begin";
		qry += "\n  begin";
		qry += "\n    select a.onhouseid,b.onhousename,a.statetag into v_onhouseid,v_onhousename,v_statetag ";
		qry += "\n    from viporderh a join onlinehouse b on a.onhouseid=b.onhouseid";
		qry += "\n    where a.vipid=" + Vipid + " and a.noteno='" + Noteno + "';"; // 生成零售出库单
		// statetag:0=草稿 1=提交 2=付款，3=发货，4=签收，5=退款，6=关闭
		qry += "\n    if v_statetag<>2 then ";
		qry += "\n       v_retcs:= '0订单状态异常，不允许发货！' ; ";
		qry += "\n       goto exit;";
		qry += "\n    end if;";
		qry += "\n    begin";
		qry += "\n      select guestid into v_guestid from guestvip where accid=" + accid + " and vipid=" + Vipid + " and rownum=1;";// c端会员对应的b端卡号
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n      v_retcs:= '0未找到会员信息！'; ";
		qry += "\n      goto exit;";
		qry += "\n    end;";
		qry += "\n    v_sysdate:=sysdate;";
		// 取销售单号
		qry += "\n    select 'XX'||To_Char(v_sysdate,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareouth";
		qry += "\n    where accid=" + accid + " and ntid=0 and LENGTH(noteno)=15 and substr(noteno,1,10)= 'XX'||To_Char(v_sysdate,'yyyymmdd');";
		qry += "\n    v_id:=WAREOUTH_ID.NEXTVAL;";
		// 生成销售主记录
		qry += "\n    insert into wareouth (id, accid,noteno,notedate,custid,houseid,guestid,dptid,ntid,statetag,totalamt,totalcurr,remark,handno,operant,checkman,lastdate,rs,checkcurr,changecurr,zpaycurr,freecurr,onhouseid,ywly)";
		qry += "\n    values (v_id," + accid + ",v_noteno,v_sysdate,0," + houseid + ",v_guestid,0,0,1,0,0,v_onhousename||'(会员商城销售)','" + Noteno + "','" + lastop + "','',sysdate,1,0,0,0,0,v_onhouseid,3);";
		// 生成销售明细记录
		qry += "\n    declare cursor cur_data is ";
		qry += "\n       select a.wareid,a.colorid,a.sizeid,a.amount,case b.retailsale when 0 then a.price else b.retailsale end as price0";
		qry += "\n       ,case b.retailsale when 0 then 1 else round(a.price/b.retailsale,2) end as discount,a.price,a.curr,remark0";
		qry += "\n       from viporderm a join warecode b on a.wareid=b.wareid";
		qry += "\n       where a.vipid=" + Vipid + " and a.noteno='" + Noteno + "';";
		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n    begin   ";
		qry += "\n      for v_row in cur_data loop";
		qry += "\n         insert into wareoutm (id,accid,noteno,wareid,colorid,sizeid,amount,price0,discount,price,curr,remark0)";
		qry += "\n         values (wareoutm_id.nextval," + accid + ",v_noteno,v_row.wareid,v_row.colorid,v_row.sizeid,v_row.amount,v_row.price0,v_row.discount,v_row.price,v_row.curr,v_row.remark0);";
		qry += "\n      end loop;";
		qry += "\n    end;";

		// 单据数量，金额合计
		qry += "\n    select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from wareoutm where accid=" + accid + " and noteno=v_noteno;";
		qry += "\n    update wareouth set totalcurr=v_totalcurr,totalamt=v_totalamt where id=v_id and accid=" + accid + " and noteno=v_noteno;";

		qry += "\n    update viporderh set ydnoteno='" + Ydnoteno + "',carryid=" + Carryid + ",xsnoteno=v_noteno,yddate=v_sysdate,arrivedate=v_sysdate+10,receivedate=v_sysdate+10,statetag=3";

		qry += "      ,address='" + Address + "'";
		qry += "\n    where vipid=" + Vipid + " and noteno='" + Noteno + "' and statetag=2;";
		qry += "\n    v_retcs:= '1操作成功!' ;";
		// =======================================================
		qry += "\n    exception ";
		qry += "\n    when others then  ";
		qry += "\n    begin";
		qry += "\n      rollback; ";
		qry += "\n      v_retcs:= '0操作失败，请重试！' ; ";
		qry += "\n    end;";
		qry += "\n    <<exit>>";
		qry += "\n    select v_retcs into :retcs from dual;";
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

	// 关闭VIP订单
	public int Close() {
		if (Noteno == null || Noteno.equals("") || Vipid == 0) {
			errmess = "参数无效&noteno！";
			return 0;
		}
		// statetag:0=草稿 1=提交 2=付款，3=发货，4=签收，5=完成， 6=关闭
		String qry = "begin ";
		qry += "  update viporderh set statetag=6  where vipid=" + Vipid + " and noteno='" + Noteno + "' and statetag<=1; ";
		qry += "end ;";
		int ret = DbHelperSQL.ExecuteSql(qry);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 获取订单详情
	public int doGetViporder() {
		if (Noteno.equals("") || Vipid == 0) {
			errmess = "单据号参数无效&noteno=?&vipid=?";
			return 0;
		}
		String qry = "select a.noteno,a.notedate,a.linkman,a.tel,a.address,a.remark,a.totalamt,a.totalcurr,a.checkcurr,a.curryf,a.paytag,a.paycurr";
		qry += " ,a.yddate,a.ydnoteno,a.carryid,a.xsnoteno ,a.arrivedate,a.receivedate,a.statetag";
		qry += " from viporderh a";
		// qry += " left outer join ";
		qry += " where a.vipid=" + Vipid + " and a.noteno='" + Noteno + "'";// and a.onhouseid=" + onhouseid;
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "未找到订单记录！";
			return 0;
		}
		// statetag:0=草稿 1=提交 2=付款，3=发货，4=签收，5=退款，6=关闭
		int statetag = Integer.parseInt(tb.getRow(0).get("STATETAG").toString());
		String retcs = "\"msg\":\"操作成功！\",\"NOTENO\":\"" + tb.getRow(0).get("NOTENO").toString() + "\"" + ",\"NOTEDATE\":\"" + tb.getRow(0).get("NOTEDATE").toString() + "\"" + ",\"LINKMAN\":\"" + tb.getRow(0).get("LINKMAN").toString()
				+ "\"" + ",\"TEL\":\"" + tb.getRow(0).get("TEL").toString()+ "\"" + ",\"ADDRESS\":\"" + tb.getRow(0).get("ADDRESS").toString()+ "\"" + ",\"REMARK\":\"" + tb.getRow(0).get("REMARK").toString() + "\""
				+ ",\"TOTALAMT\":\"" + tb.getRow(0).get("TOTALAMT").toString() + "\"" + ",\"TOTALCURR\":\"" + tb.getRow(0).get("TOTALCURR").toString() + "\"" + ",\"CHECKCURR\":\""
				+ tb.getRow(0).get("CHECKCURR").toString() + "\"" + ",\"CURRYF\":\"" + tb.getRow(0).get("CURRYF").toString() + "\"" + ",\"PAYTAG\":\"" + tb.getRow(0).get("PAYTAG").toString() + "\"" + ",\"PAYCURR\":\""
				+ tb.getRow(0).get("PAYCURR").toString()+ "\"" + ",\"ARRIVEDATE\":\"" + tb.getRow(0).get("ARRIVEDATE").toString() + "\"" + ",\"STATETAG\":\"" + statetag + "\""

		;
		if (statetag < 3)
			retcs += ",\"YDDATE\":\"" + "" + "\"" // 运单日期
					+ ",\"YDNOTENO\":\"" + "" + "\"" // 运单号
					+ ",\"XSNOTENO\":\"" + "" + "\""; // 发货单号
		else
			retcs += ",\"YDDATE\":\"" + tb.getRow(0).get("YDDATE").toString()+ "\"" // 运单日期
					+ ",\"YDNOTENO\":\"" + tb.getRow(0).get("YDNOTENO").toString() + "\"" // 运单号
					+ ",\"XSNOTENO\":\"" + tb.getRow(0).get("XSNOTENO").toString()+ "\"";

		// + ",\"IMAGENAME0\":\"" + tb.getRow(0).get("imagename0").toString()+() + "\""
		if (statetag < 4)
			retcs += ",\"RECEIVEDATE\":\"\""; // 实际收货日
		else
			retcs += ",\"RECEIVEDATE\":\"" + tb.getRow(0).get("YDNOTENO").toString() + "\"";

		// 取订单明细商品
		qry = "select a.id,a.wareid,b.warename,b.wareremark,b.wareno,b.units,a.colorid,c.colorname,a.sizeid,d.sizename,a.amount,a.price,a.curr,a.remark0,b.imagename0 ";
		qry += " from viporderm a ";
		qry += " left outer join warecode b on a.wareid=b.wareid";
		qry += " left outer join colorcode c on a.colorid=c.colorid";
		qry += " left outer join sizecode d on a.sizeid=d.sizeid";
		qry += " where a.vipid=" + Vipid + " and a.noteno='" + Noteno + "'";
		qry += " order by a.id";
		tb = DbHelperSQL.Query(qry).getTable(1);
		String fh = "";
		int rs = tb.getRowCount();
		retcs += ",\"warelist\":[";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"RECID\":\"" + tb.getRow(i).get("ID").toString()+ "\"" + ",\"WAREID\":\"" + tb.getRow(i).get("WAREID").toString() + "\"" + ",\"WARENAME\":\"" + tb.getRow(i).get("WARENAME").toString() + "\""
					+ ",\"WAREREMARK\":\"" + tb.getRow(i).get("WAREREMARK").toString()+ "\"" + ",\"WARENO\":\"" + tb.getRow(i).get("WARENO").toString() + "\"" + ",\"UNITS\":\"" + tb.getRow(i).get("UNITS").toString()
					+ "\"" + ",\"COLORNAME\":\"" + tb.getRow(i).get("COLORNAME").toString() + "\"" + ",\"SIZENAME\":\"" + tb.getRow(i).get("SIZENAME").toString()+ "\"" + ",\"AMOUNT\":\""
					+ tb.getRow(i).get("AMOUNT").toString() + "\"" + ",\"PRICE\":\"" + tb.getRow(i).get("PRICE").toString() + "\"" + ",\"CURR\":\"" + tb.getRow(i).get("CURR").toString() + "\"" + ",\"IMAGENAME0\":\""
					+ tb.getRow(i).get("IMAGENAME0").toString()+ "\"" + ",\"REMARK0\":\"" + tb.getRow(i).get("REMARK0").toString() + "\"}";
			fh = ",";
		}
		retcs += "]";
		errmess = retcs;
		return 1;
	}

	public String doListViporder(QueryParam qp, String strWhere, String fieldlist) {

		Table tb = new Table();
		tb = GetTable(qp, strWhere, fieldlist);
        String qry1;
        String fh = "";
        String noteno = "";
//        long vipid = 0;
        String retcs = "{\"result\":1,\"msg\":\"操作成功！\","+ qp.getTotalString() +",\"rows\":[";//"{\"total\":\"" + rs + "\",\"rows\":[";
        int count = tb.getRowCount();
        for (int i = 0; i < count; i++)
        {
            noteno = tb.getRow(i).get("NOTENO").toString();
            long vipid = Long.parseLong(tb.getRow(i).get("VIPID").toString());
            retcs += fh + "{\"NOTENO\":\"" + noteno + "\""
                 + ",\"VIPID\":\"" + vipid + "\""
                 + ",\"NOTEDATE\":\"" + tb.getRow(i).get("NOTEDATE").toString()+ "\""
                 + ",\"DAYSTR\":\"" + tb.getRow(i).get("DAYSTR").toString()+ "\""
                 + ",\"STATETAG\":\"" + tb.getRow(i).get("STATETAG").toString()+ "\""
                 + ",\"TOTALAMT\":\"" + tb.getRow(i).get("TOTALAMT").toString()+"\""
                 + ",\"TOTALCURR\":\"" + tb.getRow(i).get("TOTALCURR").toString() +"\""
                 + ",\"REMARK\":\"" + tb.getRow(i).get("REMARK").toString()+ "\""
                 + ",\"PAYTAGNAME\":\"" + tb.getRow(i).get("PAYTAGNAME").toString()+ "\""
                 ;
            //取订货明细    
            qry1 = "select a.id,a.wareid,a.colorid,a.sizeid,b.warename,b.units,b.wareno,b.retailsale,c.colorname,d.sizename,a.amount,a.price,a.curr,a.remark0,b.imagename0 ";
            qry1 += ",b.wareremark";
            qry1 += " from viporderm a ";
            qry1 += " left outer join warecode b on a.wareid=b.wareid ";
            qry1 += " left outer join colorcode c on a.colorid=c.colorid";
            qry1 += " left outer join sizecode d on a.sizeid=d.sizeid";
            //qry1 += " left outer join wareonline e on a.wareid=e.wareid and e.onhouseid=" + onhouseid;
            qry1 += " where a.vipid= " + vipid + " and a.noteno='" + noteno + "'";
            qry1 += " order by a.wareid,id";
            Table tb1 = DbHelperSQL.Query(qry1).getTable(1);
            int count1 = tb1.getRowCount();
            retcs += ",\"imagelist\":[";
            String fh1 = "";
            for (int j = 0; j < count1; j++)
            {
                retcs += fh1 + "{\"IMGID\":\"" + tb1.getRow(j).get("ID").toString()+ "\""
                    + ",\"WAREID\":\"" + tb1.getRow(j).get("WAREID").toString()+ "\""
                    + ",\"WARENAME\":\"" + tb1.getRow(j).get("WARENAME").toString()+ "\""
                    + ",\"WAREREMARK\":\"" + tb1.getRow(j).get("WAREREMARK").toString()+"\""
                    + ",\"UNITS\":\"" + tb1.getRow(j).get("UNITS").toString()+ "\""
                    + ",\"WARENO\":\"" + tb1.getRow(j).get("WARENO").toString()+ "\""
                    + ",\"COLORNAME\":\"" + tb1.getRow(j).get("COLORNAME").toString()+ "\""
                    + ",\"SIZENAME\":\"" + tb1.getRow(j).get("SIZENAME").toString()+ "\""
                    + ",\"AMOUNT\":\"" + tb1.getRow(j).get("AMOUNT").toString()+ "\""
                    + ",\"PRICE\":\"" + tb1.getRow(j).get("PRICE").toString()+ "\""
                    + ",\"CURR\":\"" + tb1.getRow(j).get("CURR").toString()+ "\""
                    + ",\"IMAGENAME0\":\"" + tb1.getRow(j).get("IMAGENAME0").toString()+ "\""
                    + ",\"RETAILSALE\":\"" + tb1.getRow(j).get("RETAILSALE").toString()+ "\""
                    + ",\"REMARK0\":\"" + tb1.getRow(j).get("REMARK0").toString()+ "\"}";
                fh1 = ",";
            }
            retcs += "]}";
            fh = ",";
        }
        retcs += "]}";
		return retcs;
	}
}