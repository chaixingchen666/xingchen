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
//  @author:sunhong  @date:2017-02-28 20:10:08
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
	private String Arrivedate;
	private String Receivedate;
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

	public void setArrivedate(String arrivedate) {
		this.Arrivedate = arrivedate;
	}

	public String getArrivedate() {
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// return df.format(Arrivedate);
		return Arrivedate;
	}

	public void setReceivedate(String receivedate) {
		this.Receivedate = receivedate;
	}

	public String getReceivedate() {
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// return df.format(Receivedate);
		return Receivedate;
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
	// 关闭订单
	public int Close() {
		if (Noteno.equals("")) {
			errmess = "订单号参数为空";
			return 0;
		}
		// statetag：0=草稿 1=提交 2=付款，3=发货，4=签收，5=完成， 6=关闭
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("  update viporderh set statetag=6  where vipid=" + Vipid + " and noteno='" + Noteno + "' and statetag<=1; ");
		strSql.append("end ;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 支付
	public int Pay(int paytag) {
		// 付款方式paytag:1=现金，2=银联卡，3=支付宝，4=微信
		if (paytag == 0 || paytag > 4) {
			errmess = "付款方式参数无效！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n    v_totalcurr number;");
		strSql.append("\n    v_paycurr number;");
		strSql.append("\n    v_retcs varchar2(200);");
		strSql.append("\n begin ");
		strSql.append("\n   v_paycurr:=" + Paycurr + ";");
		strSql.append("\n   select checkcurr+curryf into v_totalcurr from viporderh where vipid=" + Vipid + " and noteno='" + Noteno + "';");
		strSql.append("\n   if v_totalcurr<>v_paycurr then ");
		strSql.append("\n      v_retcs:= '0付款金额与应付金额不等！' ; ");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   update viporderh set paycurr=v_paycurr,paytag=" + paytag);
		if (paytag == 1)
			strSql.append(" ,statetag=4"); // 如果是现金支付，订单自动完成 statetag:0=草稿 1=提交 2=付款，3=发货，4=签收，5=退款，6=关闭
		else
			strSql.append(" ,statetag=2");

		strSql.append("\n   where vipid=" + Vipid + " and noteno='" + Noteno + "';");
		strSql.append("\n   v_retcs:= '1付款成功！' ; ");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		strSql.append("\n end;");

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret == 0) {
			errmess = "操作失败!";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);
		if (retcs.substring(0, 1).equals("1"))
			return 1;
		else
			return 0;

	}

	// 收货
	public int Take(int opid) {
		// opid:0=延迟收货，1=确认收货,2=退款
		// statetag:0=草稿 1=提交 2=付款，3=发货，4=签收，5=申请退款，6=退款成功，7=退款拒绝，9=关闭
		if (Noteno == null || Noteno.equals("")) {
			errmess = "订单号参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n    v_count number;");
		strSql.append("\n    v_retcs varchar2(200);");
		strSql.append("\n    v_paycurr number;");
		strSql.append("\n    v_tktag number(1);");
		strSql.append("\n begin ");
		if (opid == 2) {// 2=退款
			// decimal tkcurr = 0;
			// if (ht["tkcurr"] != null) tkcurr = decimal.Parse(ht["tkcurr"].ToString());
			if (Tkcurr == null || Tkcurr <= 0) {
				errmess = "请输入退款金额！";
				return 0;
			}
			// int tkyy = 0;
			if (Tkyy == null)
			// if (tkyy == 0)
			{
				errmess = "请选择退款原因！";
				return 0;
			}
			if (Tkremark == null || Tkremark.equals("")) {
				errmess = "请输入退款说明！";
				return 0;
			}
			// statetag:0=草稿 1=提交 2=付款，3=发货，4=签收，5=申请退款，6=退款成功，7=退款拒绝，9=关闭
			strSql.append("\n   select paycurr,tktag into v_paycurr,v_tktag from viporderh where vipid=" + Vipid + " and noteno='" + Noteno + "';");
			strSql.append("\n   if v_tktag>0 then");
			strSql.append("\n      v_retcs:= '0已经申请退款，请耐心等候！'; ");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
			strSql.append("\n   if v_paycurr<" + Tkcurr + " then");
			strSql.append("\n      v_retcs:= '0退款金额不能大于付款金额！' ; ");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
			strSql.append("\n   update viporderh set tktag=1,tkcurr=" + Tkcurr + ",tkremark='" + Tkremark + "',tkyy=" + Tkyy + " where vipid=" + Vipid + " and noteno='" + Noteno + "';");

		} else if (opid == 1)// 1=确认收货
		{
			strSql.append("\n  update viporderh set statetag=4,receivedate=sysdate where vipid=" + Vipid + " and noteno='" + Noteno + "';"); // 如果是现金支付，订单自动完成 statetag:0=草稿 1=提交 2=付款，3=发货，4=签收，5=退款，6=关闭
		} else if (opid == 0) { // 0=延迟收货
			if (Arrivedate.equals("")) {
				errmess = "延迟收货日期参数无效！";
				return 0;
			}
			strSql.append("\n   update viporderh set arrivedate=to_date('" + Arrivedate + "','yyyy-mm-dd') where vipid=" + Vipid + " and noteno='" + Noteno + "';");
		} else {
			errmess = "opid参数无效！";
			return 0;
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
		//		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		//		if (ret < 0) {
		//			errmess = "操作失败！";
		//			return 0;
		//		} else {
		//			errmess = "操作成功！";
		//			return 1;
		//
		//		}

	}

	// 删除记录
	public int Delete() {
		StringBuilder strSql = new StringBuilder();

		strSql.append("declare");
		strSql.append("  v_statetag number(1);");
		strSql.append("  v_retcs varchar2(200);");
		strSql.append("begin ");

		strSql.append("   begin ");
		strSql.append("     select statetag into v_statetag from viporderh where vipid=" + Vipid + " and noteno='" + Noteno + "';");
		strSql.append("   EXCEPTION WHEN NO_DATA_FOUND THEN ");
		strSql.append("     v_retcs:='0订单未找到！';");
		strSql.append("     goto exit; ");

		strSql.append("   end; ");

		strSql.append("   if v_statetag=2 or v_statetag=3 or v_statetag=5 then "); // 0=草稿 1=提交 2=付款，3=发货，4=签收，5=退款，6=关闭,7=删除
		strSql.append("      v_retcs:= '0订单状态不允许删除！';");
		strSql.append("      goto exit;");
		strSql.append("   end if;");
		strSql.append("   if v_statetag<=1 then ");
		strSql.append("      delete from viporderh where vipid=" + Vipid + " and noteno='" + Noteno + "';");// and a.onhouseid=" + onhouseid;
		strSql.append("      delete from viporderm where vipid=" + Vipid + " and noteno='" + Noteno + "';");// and a.onhouseid=" + onhouseid;
		strSql.append("   else");
		strSql.append("      update viporderh set statetag=7 where vipid=" + Vipid + " and noteno='" + Noteno + "';");
		strSql.append("   end if;");
		strSql.append("   v_retcs:= '1删除成功！'; ");
		strSql.append("   <<exit>>");
		strSql.append("   select v_retcs into :retcs from dual;");
		strSql.append(" end; ");

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret == 0) {
			errmess = "操作失败!";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);
		if (retcs.substring(0, 1).equals("1"))
			return 1;
		else
			return 0;

	}

	// vip购物车转订单
	public int Vipcar2order(JSONObject jsonObject) {
		// if (jsonArray == null) {
		// errmess = "记录warereclist为空！";
		// return 0;
		// }
		// LogUtils.LogDebugWrite("vipcar2order 1", "begin");
		if (Onhouseid == null || Onhouseid == 0) {
			errmess = "线上店铺id无效！";
			return 0;
		}
		if (!jsonObject.has("warereclist")) {
			errmess = "记录数组warereclist为空";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("warereclist");
		if (jsonArray.size() > 9) {
			errmess = "同一订单商品记录不允许超过10条！";
			return 0;
		}
		if (Linkman == null)
			Linkman = "";
		if (Address == null)
			Address = "";
		if (Tel == null)
			Tel = "";
		// LogUtils.LogDebugWrite("vipcar2order 2", "2222");

		StringBuilder strSql = new StringBuilder();

		strSql.append("declare");
		strSql.append("\n    v_noteno varchar2(20);");
		strSql.append("\n    v_totalamt number;");
		strSql.append("\n    v_totalcurr number;");
		strSql.append("\n    v_retcs varchar2(200);");
		strSql.append("\n begin");
		// 生成订单号
		strSql.append("\n   begin");
		strSql.append("\n    select 'VO'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,6)),'999999'),0)+1,'000000')) into v_noteno");
		strSql.append("\n    from viporderh where vipid=" + Vipid + " and LENGTH(noteno)=16 and substr(noteno,1,10)= 'VO'||To_Char(SYSDATE,'yyyymmdd');");

		strSql.append("\n    insert into viporderh (id,vipid,noteno,notedate,onhouseid,totalamt,totalcurr,linkman,address,tel)");
		strSql.append("\n    values (viporderh_id.nextval," + Vipid + ",v_noteno,sysdate," + Onhouseid + ",0,0,'" + Linkman + "','" + Address + "','" + Tel + "' );");
		float id = 0;
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			// id =decimal.Parse( ht["recid"+i].ToString());
			id = Long.parseLong(jsonObject2.getString("recid"));

			strSql.append("\n    insert into viporderm (id,vipid,noteno,wareid,colorid,sizeid,amount,price,curr,remark0)");
			strSql.append("\n    select viporderm_id.nextval,vipid,v_noteno,wareid,colorid,sizeid,amount,price,curr,remark0 from vipbuycar ");
			strSql.append("\n    where vipid=" + Vipid + " and onhouseid=" + Onhouseid + " and id=" + id + ";");
			strSql.append("\n    delete from vipbuycar where id=" + id + ";");
		}
		strSql.append("\n    select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from viporderm where vipid=" + Vipid + " and noteno=v_noteno;");
		strSql.append("\n    update viporderh set totalamt=v_totalamt,totalcurr=v_totalcurr,checkcurr=v_totalcurr where  vipid=" + Vipid + " and noteno=v_noteno;");
		strSql.append("\n    v_retcs:= '1'||v_noteno ;");
		strSql.append("\n exception");
		strSql.append("\n  when others then ");
		strSql.append("\n  begin");
		strSql.append("\n    rollback;");
		strSql.append("\n    v_retcs:= '0操作失败，请重试！' ;");
		strSql.append("\n  end;");
		strSql.append("\n  end;");
		// strSql.append(" v_retcs:= '1删除成功！'; ");
		// strSql.append(" <<exit>>");
		strSql.append("   select v_retcs into :retcs from dual;");
		strSql.append(" end; ");
		// LogUtils.LogDebugWrite("vipcar2order 3", strSql.toString());

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		// LogUtils.LogDebugWrite("vipcar2order 4", "end");
		if (ret == 0) {
			errmess = "操作失败!";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);
		if (retcs.substring(0, 1).equals("1"))
			return 1;
		else
			return 0;
	}

	// 增加记录
	public int Append(Viporderm m) {
		//System.out.println("11111111111");
		Long wareid = m.getWareid();
		Long colorid = m.getColorid();
		Long sizeid = m.getSizeid();
		Float amount = m.getAmount();
		Float price = m.getPrice();

		if (Onhouseid == null || Onhouseid == 0 || wareid == null || colorid == null || sizeid == null || amount == null || wareid == 0 || colorid == 0 || sizeid == 0 || amount == 0) {
			errmess = "参数无效！";
			return 0;
		}
		float curr = price * amount;
		if (Linkman == null)
			Linkman = "";
		if (Address == null)
			Address = "";
		if (Tel == null)
			Tel = "";
		StringBuilder strSql = new StringBuilder();

		strSql.append("declare ");
		strSql.append("\n   v_noteno varchar2(20); ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		strSql.append("\n begin");
		strSql.append("\n    select 'VO'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,6)),'999999'),0)+1,'000000')) into v_noteno");
		strSql.append("\n    from viporderh where vipid=" + Vipid + "  and LENGTH(noteno)=16 and substr(noteno,1,10)= 'VO'||To_Char(SYSDATE,'yyyymmdd');");

		strSql.append("\n    insert into viporderh (id,vipid,noteno,notedate,onhouseid,linkman,address,tel,totalamt,totalcurr,checkcurr)");
		strSql.append("\n    values (viporderh_id.nextval," + Vipid + ",v_noteno,sysdate," + Onhouseid + ",'" + Linkman + "','" + Address + "','" + Tel + "'," + amount + "," + curr + "," + curr + "  );");

		strSql.append("\n    insert into viporderm (id,vipid,noteno,wareid,colorid,sizeid,amount,price,curr,remark0)");
		strSql.append("\n    values (viporderm_id.nextval," + Vipid + ",v_noteno," + wareid + "," + colorid + "," + sizeid + "," + amount + "," + price + "," + curr + ",'');");

		strSql.append("\n    v_retcs:='1'||v_noteno;");
		// strSql.append("\n goto exit;");
		strSql.append("\n  exception");
		strSql.append("\n  when others then ");
		strSql.append("\n  begin");
		strSql.append("\n    rollback;");
		strSql.append("\n    v_retcs:= '0操作失败，请重试！';");
		// strSql.append("\n goto exit;");
		strSql.append("\n  end;");
		strSql.append("\n  end;");
		// strSql.append("\n <<exit>>");
		strSql.append("\n  select v_retcs into :retcs from dual;");
		strSql.append("\n end;");
		//System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		//System.out.println("2222");
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		//System.out.println("3333");
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		//System.out.println(retcs);
		errmess = retcs.substring(1);
		if (retcs.substring(0, 1).equals("1"))
			return 1;
		else
			return 0;
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update viporderh set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
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
		strSql.append("  where id=id ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM viporderh ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM viporderh a");
		strSql.append(" join onlinehouse b on a.onhouseid=b.onhouseid ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取订单详情
	public int GetOrderInfo() {
		String qry = "select a.noteno,a.notedate,a.linkman,a.tel,a.address,a.remark,a.totalamt,a.totalcurr,a.checkcurr,a.curryf,a.paytag,a.paycurr";
		qry += " ,a.yddate,a.ydnoteno,a.carryid,a.xsnoteno ,a.arrivedate,a.receivedate,a.statetag,a.onhouseid";
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
		String retcs = "\"msg\":\"操作成功！\",\"NOTENO\":\"" + tb.getRow(0).get("NOTENO").toString() + "\""//
				+ ",\"NOTEDATE\":\"" + tb.getRow(0).get("NOTEDATE").toString() + "\"" //
				+ ",\"LINKMAN\":\"" + tb.getRow(0).get("LINKMAN").toString() + "\"" //
				+ ",\"TEL\":\"" + tb.getRow(0).get("TEL").toString() + "\"" //
				+ ",\"ADDRESS\":\"" + tb.getRow(0).get("ADDRESS").toString() + "\"" //
				+ ",\"REMARK\":\"" + tb.getRow(0).get("REMARK").toString() + "\"" //
				+ ",\"TOTALAMT\":\"" + tb.getRow(0).get("TOTALAMT").toString() + "\"" //
				+ ",\"TOTALCURR\":\"" + tb.getRow(0).get("TOTALCURR").toString() + "\""//
				+ ",\"CHECKCURR\":\"" + tb.getRow(0).get("CHECKCURR").toString() + "\"" //
				+ ",\"CURRYF\":\"" + tb.getRow(0).get("CURRYF").toString() + "\"" //
				+ ",\"PAYTAG\":\"" + tb.getRow(0).get("PAYTAG").toString() + "\"" //
				+ ",\"PAYCURR\":\"" + tb.getRow(0).get("PAYCURR").toString() + "\"" //
				+ ",\"ARRIVEDATE\":\"" + tb.getRow(0).get("ARRIVEDATE").toString() + "\"" //
				+ ",\"ONHOUSEID\":\"" + tb.getRow(0).get("ONHOUSEID").toString() + "\"" //
				+ ",\"STATETAG\":\"" + statetag + "\""

		;
		if (statetag < 3)
			retcs += ",\"YDDATE\":\"" + "" + "\"" // 运单日期
					+ ",\"YDNOTENO\":\"" + "" + "\"" // 运单号
					+ ",\"XSNOTENO\":\"" + "" + "\""; // 发货单号
		else
			retcs += ",\"YDDATE\":\"" + tb.getRow(0).get("YDDATE").toString() + "\"" // 运单日期
					+ ",\"YDNOTENO\":\"" + tb.getRow(0).get("YDNOTENO").toString() + "\"" // 运单号
					+ ",\"XSNOTENO\":\"" + tb.getRow(0).get("XSNOTENO").toString() + "\"";

		// + ",\"IMAGENAME0\":\"" + tb.getRow(0).get("imagename0").toString() + "\""
		if (statetag < 4)
			retcs += ",\"RECEIVEDATE\":\"\""; // 实际收货日
		else
			retcs += ",\"RECEIVEDATE\":\"" + tb.getRow(0).get("YDNOTENO").toString() + "\"";

		// 取订单明细商品
		qry = "select a.id,a.wareid,b.warename,b.wareno,b.units,a.colorid,c.colorname,a.sizeid,d.sizename,a.amount,a.price,a.curr,a.remark0,b.imagename0 ";
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
			retcs += fh + "{\"RECID\":\"" + tb.getRow(i).get("ID").toString() + "\"" + ",\"WAREID\":\"" + tb.getRow(i).get("WAREID").toString() + "\"" + ",\"WARENAME\":\"" + tb.getRow(i).get("WARENAME").toString() + "\""
					+ ",\"WARENO\":\"" + tb.getRow(i).get("WARENO").toString() + "\"" + ",\"UNITS\":\"" + tb.getRow(i).get("UNITS").toString() + "\"" + ",\"COLORNAME\":\"" + tb.getRow(i).get("COLORNAME").toString()
					+ "\"" + ",\"SIZENAME\":\"" + tb.getRow(i).get("SIZENAME").toString() + "\"" + ",\"AMOUNT\":\"" + tb.getRow(i).get("AMOUNT").toString() + "\"" + ",\"PRICE\":\"" + tb.getRow(i).get("PRICE").toString()
					+ "\"" + ",\"CURR\":\"" + tb.getRow(i).get("CURR").toString() + "\"" + ",\"IMAGENAME0\":\"" + tb.getRow(i).get("IMAGENAME0").toString() + "\"" + ",\"REMARK0\":\""
					+ tb.getRow(i).get("REMARK0").toString() + "\"}";
			fh = ",";
		}
		retcs += "]";
		errmess = retcs;
		return 1;
	}

	// 获取分页数据
	public String GetTableJson(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM viporderh a");
		strSql.append(" join onlinehouse b on a.onhouseid=b.onhouseid ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		Table tb = new Table();
		tb = DbHelperSQL.GetTable(qp);
		int count = tb.getRowCount();

		String qry1;
		String fh = "";
		String noteno = "";
		String retcs = "{\"result\":1,\"msg\":\"操作成功！\"," + qp.getTotalString() + ",\"rows\":[";
		long onhouseid = 0;
		for (int i = 0; i < count; i++) {
			noteno = tb.getRow(i).get("NOTENO").toString();
			onhouseid = Long.parseLong(tb.getRow(i).get("ONHOUSEID").toString());
			retcs += fh + "{\"NOTENO\":\"" + noteno + "\"" + ",\"NOTEDATE\":\"" + tb.getRow(i).get("NOTEDATE").toString() + "\"" + ",\"NOTEDATETIME\":\"" + tb.getRow(i).get("NOTEDATETIME").toString() + "\""
					+ ",\"STATETAG\":\"" + tb.getRow(i).get("STATETAG").toString() + "\"" + ",\"ONHOUSEID\":\"" + onhouseid + "\"" + ",\"ONHOUSENAME\":\"" + tb.getRow(i).get("ONHOUSENAME").toString() + "\""
					+ ",\"TOTALAMT\":\"" + tb.getRow(i).get("TOTALAMT").toString() + "\"" + ",\"TOTALCURR\":\"" + tb.getRow(i).get("TOTALCURR").toString() + "\"" + ",\"REMARK\":\"" + tb.getRow(i).get("REMARK").toString()
					+ "\""
			// + ",\"LASTDATE\":\"" +tb.getRow(i).get("lastdate").toString() + "\""
			;
			// 取订货明细
			qry1 = "select a.id,a.wareid,a.colorid,a.sizeid,b.warename,b.units,b.wareno,b.retailsale,c.colorname,d.sizename,a.amount,a.price,a.curr,a.remark0,b.imagename0 ";
			qry1 += ",e.remark as wareremark";
			qry1 += " from viporderm a ";
			qry1 += " left outer join warecode b on a.wareid=b.wareid ";
			qry1 += " left outer join colorcode c on a.colorid=c.colorid";
			qry1 += " left outer join sizecode d on a.sizeid=d.sizeid";
			qry1 += " left outer join wareonline e on a.wareid=e.wareid and e.onhouseid=" + onhouseid;

			qry1 += " where a.vipid= " + Vipid + " and a.noteno='" + noteno + "'";
			qry1 += " order by a.wareid,id";
			Table tb1 = DbHelperSQL.Query(qry1).getTable(1);
			int count1 = tb1.getRowCount();
			retcs += ",\"imagelist\":[";
			String fh1 = "";
			for (int j = 0; j < count1; j++) {
				retcs += fh1 + "{\"IMGID\":\"" + tb1.getRow(j).get("ID").toString() + "\"" + ",\"WAREID\":\"" + tb1.getRow(j).get("WAREID").toString() + "\"" + ",\"WARENAME\":\""
						+ tb1.getRow(j).get("WARENAME").toString() + "\"" + ",\"WAREREMARK\":\"" + tb1.getRow(j).get("WAREREMARK").toString() + "\"" + ",\"UNITS\":\"" + tb1.getRow(j).get("UNITS").toString() + "\""
						+ ",\"WARENO\":\"" + tb1.getRow(j).get("WARENO").toString() + "\"" + ",\"COLORNAME\":\"" + tb1.getRow(j).get("COLORNAME").toString() + "\"" + ",\"SIZENAME\":\""
						+ tb1.getRow(j).get("SIZENAME").toString() + "\"" + ",\"AMOUNT\":\"" + tb1.getRow(j).get("AMOUNT").toString() + "\"" + ",\"PRICE\":\"" + tb1.getRow(j).get("PRICE").toString() + "\""
						+ ",\"CURR\":\"" + tb1.getRow(j).get("CURR").toString() + "\"" + ",\"IMAGENAME0\":\"" + tb1.getRow(j).get("IMAGENAME0").toString() + "\"" + ",\"RETAILSALE\":\""
						+ tb1.getRow(j).get("RETAILSALE").toString() + "\"" + ",\"REMARK0\":\"" + tb1.getRow(j).get("REMARK0").toString() + "\"}";
				fh1 = ",";
			}
			retcs += "]}";

			fh = ",";

		}
		retcs += "]}";

		return retcs;
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from viporderh");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}
}