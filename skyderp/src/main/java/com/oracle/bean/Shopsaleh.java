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
import com.common.tool.LogUtils;
import com.flyang.yky.FYApiClientUtils;
//import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
//import net.sf.json.JSONObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-27 15:07:39
//*************************************
public class Shopsaleh implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private String Noteno;
	private String Notedate;
	private Long Guestid;
	private Long Houseid;
	private Long Dptid;
	private Integer Ntid;
	private String Handno;
	private String Remark;
	private Integer Statetag;
	private Long Cent;
	private String Thnoteno;
	private Float Totalamt;
	private Float Totalcurr;
	private Float Freecurr;
	private Long Usecent;
	private Float Jfcurr;
	private Float Checkcurr;
	private Float Zpaycurr;
	private Float Changecurr;
	private Integer Bj;
	private String Operant;
	private String Checkman;
	private String Cashier;

	private Date Lastdate;
	private String Paylist;
	private Long Custid;
	private Float Messid;
	private String Paynoteno_zfb;
	private String Paynoteno_wx;
	private Long Vcpid;
	private Long Yhjcurr;
	private String Paynoteno_lkl;
	private String errmess;
	private Integer Sumtag = 0;
	private String Calcdate = "";
	private String Notedatestr = "";
	private String Smsuid;
	private String Smsuser;
	private String Smspwd;
	private Integer Tjspjf = 0;  //1=特价商品要积分
	private Integer Srsbjf = 0;  //1=会员生日双倍积分

	public void setTjspjf(Integer tjspjf) {
		Tjspjf = tjspjf;
	}

	public void setSrsbjf(Integer srsbjf) {
		Srsbjf = srsbjf;
	}

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

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public void setSumtag(Integer sumtag) {
		Sumtag = sumtag;
	}

	public Float getId() {
		return Id;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
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

	public void setNotedate(String notedate) {
		this.Notedate = notedate;
	}

	public String getNotedate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Notedate);
	}

	public void setGuestid(Long guestid) {
		this.Guestid = guestid;
	}

	public Long getGuestid() {
		return Guestid;
	}

	public void setDptid(Long dptid) {
		this.Dptid = dptid;
	}

	public Long getDptid() {
		return Dptid;
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

	public void setCent(Long cent) {
		this.Cent = cent;
	}

	public Long getCent() {
		return Cent;
	}

	public void setThnoteno(String thnoteno) {
		this.Thnoteno = thnoteno;
	}

	public String getThnoteno() {
		return Thnoteno;
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

	public void setFreecurr(Float freecurr) {
		this.Freecurr = freecurr;
	}

	public Float getFreecurr() {
		return Freecurr;
	}

	public void setUsecent(Long usecent) {
		this.Usecent = usecent;
	}

	public Long getUsecent() {
		return Usecent;
	}

	public void setJfcurr(Float jfcurr) {
		this.Jfcurr = jfcurr;
	}

	public Float getJfcurr() {
		return Jfcurr;
	}

	public void setCheckcurr(Float checkcurr) {
		this.Checkcurr = checkcurr;
	}

	public Float getCheckcurr() {
		return Checkcurr;
	}

	public void setZpaycurr(Float zpaycurr) {
		this.Zpaycurr = zpaycurr;
	}

	public Float getZpaycurr() {
		return Zpaycurr;
	}

	public void setChangecurr(Float changecurr) {
		this.Changecurr = changecurr;
	}

	public Float getChangecurr() {
		return Changecurr;
	}

	public void setBj(Integer bj) {
		this.Bj = bj;
	}

	public Integer getBj() {
		return Bj;
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

	public void setPaylist(String paylist) {
		this.Paylist = paylist;
	}

	public String getPaylist() {
		return Paylist;
	}

	public void setCustid(Long custid) {
		this.Custid = custid;
	}

	public Long getCustid() {
		return Custid;
	}

	public void setMessid(Float messid) {
		this.Messid = messid;
	}

	public Float getMessid() {
		return Messid;
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

	public void setVcpid(Long vcpid) {
		this.Vcpid = vcpid;
	}

	public Long getVcpid() {
		return Vcpid;
	}

	public void setYhjcurr(Long yhjcurr) {
		this.Yhjcurr = yhjcurr;
	}

	public Long getYhjcurr() {
		return Yhjcurr;
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

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n  v_retcs varchar2(200);");
		strSql.append("\n  v_statetag number(1);");
		strSql.append("\n  v_count number;");
		strSql.append("\n  v_paynoteno_zfb varchar2(30);v_paynoteno_wx varchar2(30);v_paynoteno_lkl varchar2(30); ");
		strSql.append("\n  v_operant varchar2(20); ");

		strSql.append("\n begin ");

		strSql.append("\n  begin ");
		strSql.append("\n    select paynoteno_zfb,paynoteno_wx,paynoteno_lkl,statetag,operant ");
		strSql.append("\n    into v_paynoteno_zfb,v_paynoteno_wx,v_paynoteno_lkl,v_statetag,v_operant");
		strSql.append("\n    from shopsaleh where accid=" + Accid + " and noteno='" + Noteno + "';");

		strSql.append("\n    if v_statetag>0 then ");
		strSql.append("\n       v_retcs:= '0当前单据已提交，不允许删除！'; ");
		strSql.append("\n       goto exit; ");
		strSql.append("\n    end if;");
		strSql.append("\n   if v_operant<>'" + Operant + "' then ");
		strSql.append("\n      v_retcs:= '0不允许删除其他用户的单据！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if;");
		// ywtag:0=店铺零售，1=商场零售，2=线上，3=批发销售
		strSql.append("\n   select count(*) into v_count from DEALRECORD where accid=" + Accid + " and ywnoteno='" + Noteno + "' and ywtag=1 and rownum=1;");
		strSql.append("\n   if v_count>0 then ");

		// strSql.append("\n if length(v_paynoteno_zfb)>0 or
		// length(v_paynoteno_wx)>0 or length(v_paynoteno_lkl)>0 then ");
		strSql.append("\n       v_retcs:= '0当前单据有第三方支付记录，不允许删除！' ; ");
		strSql.append("\n       goto exit; ");
		strSql.append("\n    end if;");

		//		strSql.append("\n    delete from shopsalem ");
		//		strSql.append("\n    where accid=" + Accid + " and noteno='" + Noteno + "'");
		//		strSql.append("\n    and exists (select 1 from shopsaleh a where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
		//		strSql.append("\n    and a.accid=shopsalem.accid and a.noteno=shopsalem.noteno and a.statetag=0);");

		//		strSql.append("\n    delete from shopsalepay ");
		//		strSql.append("\n    where accid=" + Accid + " and noteno='" + Noteno + "'");
		//		strSql.append("\n    and exists (select 1 from shopsaleh a where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
		//		strSql.append("\n    and a.accid=shopsalem.accid and a.noteno=shopsalem.noteno and a.statetag=0);");

		//		strSql.append("\n    delete from shopsaleh ");
		//		strSql.append("\n    where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0; ");
		strSql.append("\n    update shopsaleh set statetag=2,lastdate=sysdate");
		strSql.append("\n    where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0; ");

		strSql.append("\n    commit; ");
		strSql.append("\n    v_retcs:='1删除成功' ; ");
		strSql.append("\n    exception  ");
		strSql.append("\n    when others then  ");
		strSql.append("\n    begin ");
		strSql.append("\n      rollback;  ");
		strSql.append("\n      v_retcs:='0删除失败！' ; ");
		strSql.append("\n    end; ");
		strSql.append("\n  end; ");
		strSql.append("\n  <<exit>> ");

		strSql.append("\n  select v_retcs into :retcs from dual;");
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
		// qry += " v_houseid number;";
		// qry += " v_custid number;";
		qry += "   v_id  number;";
		qry += "   v_epid  number;";
		qry += "   v_ntid  number(1);";
		qry += "   v_retcs nvarchar2(100);";
		qry += "   v_retbj number(1); ";
		qry += "\n begin ";
		qry += "\n   begin ";

		qry += "\n     select 'XC'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from shopsaleh";
		qry += "\n     where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'XC'||To_Char(SYSDATE,'yyyymmdd');";
		qry += "\n     v_id:=shopsaleh_ID.NEXTVAL;";
		qry += "\n     insert into shopsaleh (id, accid,noteno,notedate,houseid,dptid,ntid,statetag,totalamt,totalcurr,remark,handno,operant,checkman,lastdate,checkcurr,changecurr,zpaycurr,freecurr)";
		qry += "\n     values (v_id," + Accid + ",v_noteno,sysdate," + Houseid + "," + Dptid + ",0,0,0,0,'','','" + Operant + "','',sysdate,0,0,0,0);";
		// if ( (ntid == 0 || ntid==1) && epid > 0) //前台零售自动增加销售人
		qry += "\n     commit;";
		qry += "\n     v_retbj:=1; select '\"msg\":\"操作成功\",\"ID\":\"'||id||'\",\"NOTENO\":\"'||noteno||'\",\"NOTEDATE\":\"'||to_char(notedate,'yyyy-mm-dd hh24:mi:ss')||'\"' into v_retcs from shopsaleh where id=v_id;  ";
		qry += "\n     insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n     values (LOGRECORD_id.nextval," + Accid + ",'商场开票','【增加单据】单据号:'||v_noteno,'" + Operant + "');";
		qry += "\n     exception";
		qry += "\n     when others then ";
		qry += "\n     begin";
		qry += "\n       rollback;";
		qry += "\n       v_retbj:=0;  v_retcs:='\"msg\":\"操作失败，请重试！\"';";
		qry += "\n     end;";
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
		// int retbj =
		// Integer.parseInt(param.get("retbj").getParamvalue().toString());
		errmess = param.get("retcs").getParamvalue().toString();

		// errmess = retcs.substring(1);
		return Integer.parseInt(param.get("retbj").getParamvalue().toString());
	}

	// 修改记录
	public int Update(JSONObject jsonObject, float jfrate, int fs) {
		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		if (Zpaycurr == null)
			Zpaycurr = (float) 0;
		// if (Checkcurr == null) Checkcurr = 0;
		if (Freecurr == null)
			Freecurr = (float) 0;
		if (Totalcurr == null)
			Totalcurr = (float) 0;
		if (Usecent == null)
			Usecent = (long) 0;
		if (Jfcurr == null)
			Jfcurr = (float) 0;
		if (Yhjcurr == null)
			Yhjcurr = (long) 0;
		if (Jfcurr > 0) {
			if (jfrate == 0) {
				errmess = "积分抵扣比例无效，不允许积分抵扣消费！";
				return 0;
			}
			Float jfcurr = Func.getRound(Usecent / jfrate, 2);
			if (!Func.isEqual(jfcurr, Jfcurr)) {
				// if (jfcurr != Jfcurr) {
				errmess = "积分抵扣的金额异常，请检查！ 使用积分:" + Usecent + "  比例: " + jfrate + " <> 金额:" + Jfcurr + "  " + jfcurr;
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
				// if (Freecurr > model.totalcurr - Jfcurr || Freecurr < 0)
				if (Freecurr > Func.getRound(Totalcurr - Jfcurr - Yhjcurr, 2)) {
					errmess = "免单金额不允许大于合计金额！";
					return 0;
				}
				if (Zpaycurr < Checkcurr) {
					errmess = "收款合计小于实结金额，不允许提交！";
					return 0;
				}
			} else {
				if (Freecurr < Totalcurr || Freecurr > 0) {
					errmess = "当前单据需要退款，免单金额不允许小于应退合计金额或大于零！";
					return 0;
				}
				// if (Zpaycurr != Checkcurr) {
				if (!Func.isEqual(Zpaycurr, Checkcurr)) {
					errmess = "退款合计与实结金额不等，不允许提交！";
					return 0;
				}
			}
		}
		if (!Func.isNull(Notedatestr)) {
			if (Func.subString(Notedatestr, 1, 10).compareTo(Calcdate) <= 0) {
				errmess = "单据日期只能在系统登账日 " + Calcdate + " 之后！";
				return 0;
			}
		}

		Changecurr = Func.getRound(Zpaycurr - Checkcurr, 2);
		if (Changecurr < 0)
			Changecurr = (float) 0;

		String qry = "declare ";
		qry += "\n   v_count number(10);";
		qry += "\n   v_retcs varchar2(200); ";
		qry += "\n   v_centrate number; ";
		qry += "\n   v_cent number(10); ";
		qry += "\n   v_totalcurr number; ";
		qry += "\n   v_totalamt number; ";
		qry += "\n   v_balcent number; ";
		qry += "\n   v_usecent number; ";
		qry += "\n   v_balcurr number(16,2); ";
		qry += "\n   v_paycurrv number(16,2); ";
		qry += "\n   v_paycurrvid number(10); ";
		qry += "\n   v_mobile varchar2(20); ";
		qry += "\n   v_smspwd varchar2(6); ";
		qry += "\n   v_houseid number(10); ";
		qry += "\n   v_vcpid number(10); ";
		qry += "\n   v_vipid number(10); ";
		qry += "\n    v_usetag number(1);";
		qry += "\n    v_xfcurr number;";
		qry += "\n    v_yhjcurr number;";
		qry += "\n    v_kyjfcurr number;";
		qry += "\n    v_tjcurr number;";
		qry += "\n    v_notedate date; ";
		qry += "\n    v_birthday varchar2(10);";
		qry += "\n begin ";
		if (Notedatestr.length() > 0) {
			qry += "\n   v_notedate:=to_date('" + Notedatestr + "','yyyy-mm-dd hh24:mi:ss');";
			qry += "\n   if to_char(v_notedate,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')  or v_notedate>sysdate then ";
			qry += "\n      v_notedate:=sysdate;";
			qry += "\n   end if;";
		} else {
			qry += "\n   v_notedate:=sysdate;";
		}

		if (Dptid != null && Dptid > 0) {
			qry += "\n   select count(*) into v_count from department where accid=" + Accid + " and dptid=" + Dptid + " and statetag=1;";
			qry += "\n   if v_count=0 then ";
			qry += "\n      v_retcs:='0部门无效！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
		}
		if (Houseid != null) {
			qry += "\n   select count(*) into v_count from warehouse where accid=" + Accid + " and houseid=" + Houseid + " and statetag=1;";
			qry += "\n   if v_count=0 then ";
			qry += "\n      v_retcs:='0店铺无效1！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   v_houseid:=" + Houseid + ";";
		} else {
			qry += "\n   select houseid into v_houseid from shopsaleh where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   if v_houseid=0 then";
			qry += "\n      v_retcs:='0店铺无效2！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
		}

		if (Statetag != null && Statetag == 1) {
			// ywtag:0=店铺零售，1=商场零售，2=线上，3=批发销售
			qry += "\n  select count(*) into v_count from DEALRECORD where accid=" + Accid + " and ywnoteno='" + Noteno + "' and ywtag=1 and statetag=0 and rownum=1;";
			qry += "\n  if v_count>0 then ";
			qry += "\n     v_retcs:='0第三方交易记录异常，请检查！'; ";
			qry += "\n     goto exit; ";
			qry += "\n  end if;";

			// if (Paynoteno_zfb != null && Paynoteno_zfb.length() > 0) //
			// 支付宝交易记录
			// {
			// qry += "\n begin ";
			// qry += "\n select curr into v_zfbcurr from DEALRECORD where
			// accid=" + Accid + " and noteno='" + Paynoteno_zfb + " and
			// statetag=1;";
			// qry += "\n EXCEPTION WHEN NO_DATA_FOUND THEN ";
			// qry += "\n v_retcs:='0支付宝交易订单记录异常！'; ";
			// qry += "\n goto exit; ";
			// qry += "\n end; ";
			// }
			// if (Paynoteno_wx != null && Paynoteno_wx.length() > 0) // 微信交易记录
			// {
			// qry += "\n begin ";
			// qry += "\n select curr into v_zfbcurr from DEALRECORD where
			// accid=" + Accid + " and noteno='" + Paynoteno_wx + " and
			// statetag=1;";
			// qry += "\n EXCEPTION WHEN NO_DATA_FOUND THEN ";
			// qry += "\n v_retcs:= '0微信交易订单记录异常！'; ";
			// qry += "\n goto exit; ";
			// qry += "\n end; ";
			// }
			// if (Paynoteno_lkl != null && Paynoteno_lkl.length() > 0) //
			// 拉卡拉交易记录
			// {
			// qry += "\n begin ";
			// qry += "\n select curr into v_zfbcurr from DEALRECORD where
			// accid=" + Accid + " and noteno='" + Paynoteno_lkl + " and
			// statetag=1;";
			// qry += "\n EXCEPTION WHEN NO_DATA_FOUND THEN ";
			// qry += "\n v_retcs:= '0拉卡拉交易订单记录异常！' ; ";
			// qry += "\n goto exit; ";
			// qry += "\n end; ";
			// }
		}
		qry += "\n   v_count:=0; v_cent:=0;  v_paycurrv:=0; ";
		qry += "\n   v_usecent:=" + Usecent + "; ";
		if (Guestid != null && Guestid > 0) // 是会员消费
		{
			qry += "\n   begin ";
			qry += "\n     select b.centrate,a.balcent,a.balcurr,a.mobile,a.vipid,a.birthday into v_centrate,v_balcent,v_balcurr,v_mobile,v_vipid,v_birthday ";
			qry += "\n     from guestvip a join guesttype b on a.vtid=b.vtid where a.guestid= " + Guestid + "; ";
			qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
			qry += "\n     v_centrate:=0;  v_balcent:=0;  v_balcurr:=0;  v_mobile:='';  v_vipid:=0; ";
			qry += "\n   end; ";

			if (Vcpid != null && Vcpid > 0) // 使用优惠券
			{
				qry += "\n   v_vcpid:=" + Vcpid + "; ";
				qry += "\n   begin";
				qry += "\n     select a.usetag,b.curr,b.xfcurr into v_usetag,v_yhjcurr,v_xfcurr from VIPCOUPON a join housecoupon b on a.cpid=b.cpid ";
				qry += "\n     where a.vcpid=v_vcpid and a.vipid=v_vipid;";
				qry += "\n     if v_usetag>0 then ";
				qry += "\n        v_retcs:=  '0优惠券已使用！' ; ";
				qry += "\n        goto exit; ";
				qry += "\n     end if;";
				qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
				qry += "\n    v_retcs:= '0未找到优惠券！' ; ";
				qry += "\n     goto exit; ";
				qry += "\n   end; ";
				qry += "\n   if " + Yhjcurr + ">v_yhjcurr then ";
				qry += "\n      v_retcs:=  '0优惠券抵扣金额无效！'; ";
				qry += "\n      goto exit; ";
				qry += "\n   end if;";

				qry += "\n   if " + (Totalcurr - Freecurr) + "<v_xfcurr then ";
				qry += "\n      v_retcs:='0优惠券未达到消费金额条件！'; ";
				qry += "\n      goto exit; ";
				qry += "\n   end if;";
			}
		}
		qry += "\n  select nvl(sum(curr),0),nvl(sum(amount),0)  into v_totalcurr,v_totalamt from shopsalem  where accid=" + Accid + " and noteno='" + Noteno + "';";
		if (Statetag != null && Statetag == 1) // 前台零售提交要填入结算方式
		{
			qry += "\n  if v_totalcurr<>" + Totalcurr + " then ";
			qry += "\n     update shopsaleh set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n     v_retcs:='0合计金额" + Totalcurr + "异常,请重新结算！'; ";
			qry += "\n     goto exit; ";
			qry += "\n  end if; ";
			qry += "\n  delete from shopsalepay where accid=" + Accid + " and noteno='" + Noteno + "';";
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
						qry += "\n   v_paycurrv:=" + paycurr + "; ";
						qry += "\n   v_paycurrvid:=" + payid + "; ";
					}
					qry += "\n  insert into shopsalepay (id,accid,noteno,payid,curr,lastdate)";
					qry += "\n  values (shopsalepay_id.nextval," + Accid + ",'" + Noteno + "'," + payid + "," + paycurr + ",sysdate); ";
				}
			}
			if (Guestid != null && Guestid > 0) // 生成积分记录
			{
				//				qry += "\n   begin ";
				//				qry += "\n     select b.centrate,a.balcent,a.balcurr,a.mobile into v_centrate,v_balcent,v_balcurr,v_mobile from guestvip a join guesttype b on a.vtid=b.vtid where a.guestid= " + Guestid + "; ";
				//				qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
				//				qry += "\n     v_centrate:=0; v_balcent:=0; v_balcurr:=0;  v_mobile:=''; ";
				//				qry += "\n   end; ";
				qry += "\n   if v_paycurrv<>0 then"; // 有储值消费，校验短信
				qry += "\n      if v_paycurrv>v_balcurr then";
				qry += "\n         v_retcs:='0本次储值刷卡金额已超出卡余额！'; ";
				qry += "\n         goto exit; ";
				qry += "\n      end if; ";

				qry += "\n      begin";
				qry += "\n        select houseid into v_houseid from shopsaleh where accid=" + Accid + " and noteno='" + Noteno + "';";
				qry += "\n      EXCEPTION WHEN NO_DATA_FOUND THEN";
				qry += "\n        v_houseid:=0;";
				qry += "\n      end;";

				qry += "\n      insert into guestcurr (id,guestid,notedate,houseid,curr,payid,paycurr,remark,fs,ly,xsnoteno,operant)";// ly:0=手工输入,1=商场零售,2=店铺零售
				qry += "\n      values (guestcurr_id.nextval," + Guestid + ",sysdate,v_houseid,v_paycurrv,v_paycurrvid,0,'刷卡消费 №" + Noteno + "',1,1,'" + Noteno + "','" + Operant + "'); ";

				qry += "\n      update guestvip set balcurr=nvl((select sum(case fs when 0 then curr else -curr end) from guestcurr where guestvip.guestid=guestcurr.guestid),0)";
				qry += "\n      where guestid=" + Guestid + "; ";

				qry += "\n   end if; ";

				qry += "\n   if v_usecent>v_balcent then";
				qry += "\n      v_retcs:='0本次使用积分已超出积分余额！' ; ";
				qry += "\n      goto exit; ";
				qry += "\n   end if; ";
				qry += "\n   if v_usecent>0 then";
				qry += "\n      insert into guestcent (id,guestid,notedate,houseid,cent,remark,fs,ly,xsnoteno,operant)"; // ly:0=手工输入,1=商场零售,2=店铺零售
				qry += "\n      values (guestcent_id.nextval," + Guestid + ",sysdate,v_houseid,v_usecent,'积分抵扣" + Jfcurr + "元金额 №" + Noteno + "',1,1,'" + Noteno + "','" + Operant + "'); ";
				qry += "\n   end if; ";
				qry += "\n   v_cent:=0;";
				qry += "\n   if v_centrate>0 then ";
				if (Checkcurr > 0) {

					if (Tjspjf == 0) {//0=特价商品不积分
						qry += "\n      select nvl(sum(a.curr),0) into v_tjcurr from shopsalem a where a.accid=" + Accid + " and a.noteno='" + Noteno + "'";
						qry += "\n      and a.tjtag=1;";//and exists (select 1 from warecode b where a.wareid=b.wareid and b.tjtag=1);";
					} else {
						qry += "\n      v_tjcurr:=0;";
					}
					// 可积分金额=应结金额-特价商品金额
					qry += "\n      v_kyjfcurr:=" + Checkcurr + "-v_tjcurr;";
					qry += "\n      if v_kyjfcurr<0 then v_kyjfcurr:=0; end if;";
				} else {
					qry += "\n      v_kyjfcurr:=" + Checkcurr + ";";
				}
				qry += "\n      v_cent:=trunc(v_kyjfcurr/v_centrate); ";
				qry += "\n   end if;";
				qry += "\n   if v_cent<>0 then ";
				if (Srsbjf == 1) {//1=会员生日双倍积分
					qry += "\n  if to_char(v_notedate,'mm-dd')=substr(v_birthday,6,5) then";
					qry += "\n     v_cent:=v_cent*2;";
					qry += "\n  end if;";
				}
				// fs:0=增加积分 1=减少积分 ly:0=电脑产生 1=手工输入
				qry += "\n      insert into guestcent (id,guestid,notedate,houseid,cent,remark,fs,ly,xsnoteno,operant)";// ly:0=手工输入,1=商场零售,2=店铺零售
				qry += "\n      values (guestcent_id.nextval," + Guestid + ",sysdate,v_houseid,v_cent,'消费增加积分 №" + Noteno + "',0,1,'" + Noteno + "','" + Operant + "'); ";
				qry += "\n   end if; ";
				qry += "\n   if v_cent<>0 or v_usecent>0 then ";
				qry += "\n      update guestvip set balcent=nvl((select sum(case fs when 0 then cent else -cent end) from guestcent where guestvip.guestid=guestcent.guestid),0)";
				qry += "\n      where guestid=" + Guestid + "; ";
				qry += "\n   end if; ";
			}
		}
		qry += "\n update shopsaleh set ";
		if (Guestid != null) {
			qry += "guestid=" + Guestid + ",";
		}
		if (Dptid != null) {
			qry += "dptid=" + Dptid + ",";
		}
		if (Houseid != null) {
			qry += "Houseid=" + Houseid + ",";
		}
		if (Handno != null) {
			qry += "handno='" + Handno.toUpperCase() + "',";
		}
		if (Remark != null) {
			qry += "remark='" + Remark + "',";
		}
		// if (Operant != null) {
		qry += "operant='" + Operant + "',";
		// }
		if (Statetag != null && Statetag == 1) {
			//			qry += "checkman='" + Operant + "',"; // 要删除！！！！
			qry += "Cashier='" + Operant + "',"; // 收银员
		}
		if (Statetag != null) {
			qry += "statetag=" + Statetag + ",";
		}
		if (Zpaycurr != null) {
			qry += "zpaycurr=" + Zpaycurr + ",";
		}
		if (Changecurr != null) {
			qry += "changecurr=" + Changecurr + ",";
		}
		if (Freecurr != null) {
			qry += "freecurr=" + Freecurr + ",";
		}
		if (Checkcurr != null) {
			qry += "checkcurr=" + Checkcurr + ",";
		}
		if (Jfcurr != null) {
			qry += "jfcurr=" + Jfcurr + ",";
		}
		if (Usecent != null) {
			qry += "usecent=" + Usecent + ",";
		}
		if (Yhjcurr != null) {
			qry += "yhjcurr=" + Yhjcurr + ",";
		}
		//		if (!Func.isNull(Notedatestr)) {
		//			qry += "notedate=to_date('" + Notedatestr + "','yyyy-mm-dd hh24:mi:ss'),";
		//		} else {
		qry += " notedate=v_notedate,";
		//		}
		qry += "\n  totalcurr=v_totalcurr,totalamt=v_totalamt,cent=v_cent,lastdate=sysdate";
		qry += "\n  where  accid=" + Accid + " and noteno='" + Noteno + "'; ";
		qry += "\n  commit;";
		// 以后要屏蔽
		qry += "\n  update shopsalem a set houseid=v_houseid where a.accid=" + Accid + " and a.noteno='" + Noteno + "'; ";

		qry += "\n  p_shopsalepaylist(" + Accid + ",'" + Noteno + "');";
		if (Statetag != null && Statetag == 1) {
			qry += "\n  p_shopsalesetarea(" + Accid + ",'" + Noteno + "');";
			qry += "\n  insert into LOGRECORD (id,accid,progname,remark,lastop)";
			qry += "\n  values (LOGRECORD_id.nextval," + Accid + ",'商场开票','【结账】单据号:" + Noteno + "','" + Operant + "');";
			// 如果提交，优惠券已用标志=1
			qry += "\n  if v_vcpid>0 then ";
			qry += "\n     update vipcoupon set usetag=1 where vcpid=v_vcpid;";
			qry += "\n  end if;";
			qry += "\n  p_paytobook(" + Accid + ",'" + Noteno + "',0);"; //结算自动登入账本
		}
		qry += "\n  v_retcs:='1'||v_cent ;";
		qry += "\n  <<exit>>";
		qry += "\n  select v_retcs into :retcs from dual;";
		qry += "\n end;";
		//		 LogUtils.LogDebugWrite("shopsaleh.update", qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
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
					qry = "select b.balcurr,b.balcent,b.mobile,b.guestname,b.sex,b.vipno,c.housename,b.idstr";
					qry += " from shopsaleh a";
					qry += " join guestvip b on a.guestid=b.guestid";
					qry += " join warehouse c on a.houseid=c.houseid";
					qry += " where a.accid=" + Accid + " and noteno='" + Noteno + "'";
					Table tb = DbHelperSQL.Query(qry).getTable(1);
					if (tb.getRowCount() == 1) {
						// if (Cent == null)
						String guestname = tb.getRow(0).get("GUESTNAME").toString();
						String sex = tb.getRow(0).get("SEX").toString();
						String vipno = tb.getRow(0).get("VIPNO").toString();
						String mobile = tb.getRow(0).get("MOBILE").toString();
						String housename = tb.getRow(0).get("HOUSENAME").toString();
						String idstr = tb.getRow(0).get("IDSTR").toString();
						Cent = Long.parseLong(errmess);

						String strmess1 = "尊敬的" + guestname;
						if (sex.equals("女"))
							strmess1 += "女士";
						else if (sex.equals("男"))
							strmess1 += "先生";

						strmess1 += ":您尾号为" + Func.strRight(vipno, 4) + "的会员卡在『" + housename + "』的购物单：" + Noteno //
								+ " 消费总金额:" + Totalcurr + "元,本次积分" + Cent//
								+ ",积分余额" + tb.getRow(0).get("BALCENT").toString()//
								+ ",储值余额" + tb.getRow(0).get("BALCURR").toString() + "元。谢谢惠顾！";

						Func.sendMessage(Smsuid, Smsuser, Smspwd, mobile, strmess1, Operant, Accid);
						if (Accid == 17873 && idstr.length() > 0 && Cent != 0 && vipno.length() > 0) { //如果是胡燃商行，要向一卡易写积分
							String retcs1 = yky_addcent(vipno, "消费增加积分", Cent);
							if (retcs1.substring(0, 1).equals("0"))
								LogUtils.LogErrWrite("胡燃增加会员积分", retcs1.substring(1));
						}

					}
				} catch (Exception e) {
					LogUtils.LogErrWrite("updateshopsaleh", e.getMessage());

				}

		}
		return ret;
	}

	//一卡易增加积分
	public String yky_addcent(String vipno, String remark, Long cent) {
		try {
			Map<String, Object> data = new HashMap<>();
			data.put("userAccount", "10000");
			data.put("cardId", vipno);
			data.put("point", cent);
			data.put("meno", remark);
			//			System.out.println("11111");
			String responseOut = FYApiClientUtils.httpPost("Update_MemberPoint", data);
			JSONObject jsonObject = JSONObject.fromObject(responseOut);
			int status = Integer.parseInt(jsonObject.getString("status"));//状态(0,成功;-1，失败)
			//		if (status < 0)
			String msg = jsonObject.getString("message");
			if (status < 0)
				return "0" + msg;
			else
				return "1" + msg;

		} catch (Exception e) {
			return "0"+e.getMessage();
		}
		//		return status;
		//		if (status < 0) {
		//			WriteResult(response, 0, jsonObject.getString("message"));
		//			return;
		//		}
		//		return 1;
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(",case To_Char(a.notedate,'yyyy-mm-dd') when To_Char(SYSDATE,'yyyy-mm-dd') then '1' else '0' end as istoday");
		strSql.append(",f_getshopsalemanname(a.accid,a.noteno) as salemanlist");
		strSql.append("\n FROM shopsaleh a ");
		strSql.append("\n left outer join guestvip b on a.guestid=b.guestid ");
		strSql.append("\n left outer join warehouse c on a.houseid=c.houseid");
		strSql.append("\n left outer join guesttype d on b.vtid=d.vtid ");
		strSql.append("\n left outer join department e on a.dptid=e.dptid ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		System.out.println(strSql.toString());
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();

		if (Sumtag == 1) // 取批发结算方式合计
		{
			strSql.append("  select a1.payid,b1.payname,sum(curr) as curr from shopsalepay a1");
			strSql.append("  join payway b1 on a1.accid=b1.accid and a1.payid=b1.payid");
			strSql.append("  where exists (select 1 FROM shopsaleh a");
			// strSql.append(" left outer join warehouse c on
			// a.houseid=c.houseid ");
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
			String sumlist = "";
			String fh = "";
			for (int i = 0; i < count; i++) {
				sumlist += fh + tb.getRow(i).get("PAYNAME").toString() + ":" + tb.getRow(i).get("CURR").toString();
				fh = ",";
			}
			qp.setTotalString(sumlist);
			// sumlist = strSql.ToString();
		}

		strSql.setLength(0);
		strSql.append("select " + fieldlist);
		// strSql.append(",case To_Char(a.notedate,'yyyy-mm-dd') when
		// To_Char(SYSDATE,'yyyy-mm-dd') then '1' else '0' end as istoday");
		strSql.append("\n FROM shopsaleh a ");
		strSql.append("\n left outer join guestvip b on a.guestid=b.guestid ");
		strSql.append("\n left outer join warehouse c on a.houseid=c.houseid ");
		strSql.append("\n left outer join guesttype d on b.vtid=d.vtid ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from shopsaleh");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1
		// and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
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
		// qry += "\n v_noteno1 varchar2(20); ";
		qry += "\n   v_orderno varchar2(20); ";
		qry += "\n   v_progname varchar2(40); ";
		qry += "\n   v_cleartag number(1);";
		qry += "\n   v_guestid number(10); ";
		qry += "\n   v_cent number; ";
		qry += "\n   v_count number(10); ";
		qry += "\n   v_paycurrv number; ";
		qry += "\n   v_checkman varchar2(20); ";
		qry += "\n   v_dosql clob; ";
		qry += "\n begin ";
		qry += "\n   v_dosql:='';";
		qry += "\n   begin";
		qry += "\n     select to_char(notedate,'yyyy-mm-dd'),guestid,cent,checkman into v_notedate,v_guestid,v_cent,v_checkman from shopsaleh where accid=" + Accid + " and noteno='" + Noteno + "'; ";
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

		// if (changedatebj == 0) // 不允许更改单据日期
		// {
		// qry += "\n if v_notedate<to_char(sysdate,'yyyy-mm-dd') then ";
		// qry += "\n v_retcs:= '0今日之前的单据不允许撤单！'; ";
		// qry += "\n goto exit; ";
		// qry += "\n end if; ";
		// }
		// qry += "\n if length(v_paynoteno_zfb)>0 or length(v_paynoteno_wx)>0
		// or length(v_paynoteno_lkl)>0 then ";
		qry += "\n   select count(*) into v_count from DEALRECORD where accid=" + Accid + " and ywnoteno='" + Noteno + "' and ywtag=1  and rownum=1;";
		qry += "\n   if v_count>0 then ";
		qry += "\n      v_retcs:='0当前单据有第三方支付记录，不允许撤单！'; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if;";

		qry += "\n     v_progname:='商场零售'; ";
		qry += "\n     begin";
		qry += "\n       select a.curr into v_paycurrv from shopsalepay a join payway b on a.payid=b.payid where a.accid=" + Accid + " and a.noteno='" + Noteno + "' and b.payno='V';";
		qry += "\n     EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n       v_paycurrv:=0;";
		qry += "\n     end;";

		//判断有没有登账记录
		qry += "\n     select count(*) into v_count  from shopsalepay a join bookdetail b on a.bd_id=b.id";
		qry += "\n     where a.accid=" + Accid + " and a.noteno='" + Noteno + "'";
		qry += "\n     and a.accid=b.accid and a.bd_id>0 and length(b.checkman)>0;";
		qry += "\n     if v_count>0 then ";
		qry += "\n        v_retcs:= '0当前单据收款记录已登账审核，不允许撤单！' ; ";
		qry += "\n        goto exit; ";
		qry += "\n     end if;";
		//处理登账记录begin
		qry += "\n     declare cursor cur_data is";
		qry += "\n       select b.notedate,b.bookid,b.id from shopsalepay a join bookdetail b on a.bd_id=b.id";
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

		qry += "\n     delete from shopsalepay where accid=" + Accid + " and noteno='" + Noteno + "'; ";
		qry += "\n     if v_guestid>0 and ( v_cent<>0 or v_paycurrv>0) then"; // 有储值
		qry += "\n        delete from guestcent where guestid=v_guestid and xsnoteno='" + Noteno + "'; "; // 删除消费积分
		qry += "\n        delete from guestcurr where guestid=v_guestid and xsnoteno='" + Noteno + "'; "; // 删除消费积分
		qry += "\n        update guestvip set balcent=nvl((select sum(case fs when 0 then cent else -cent end) from guestcent where guestvip.guestid=guestcent.guestid),0）";
		qry += "\n          ,balcurr=nvl((select sum(case fs when 0 then curr else -curr end) from guestcurr where guestvip.guestid=guestcurr.guestid),0）";
		qry += "\n        where guestid=v_guestid; ";
		qry += "\n     end if; ";

		qry += "\n  update shopsaleh set statetag=0,cent=0,checkman='',cashier='',paylist='' where accid=" + Accid + "  and noteno='" + Noteno + "'; ";

		qry += "\n   v_retcs:= '1操作成功！';  ";
		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",v_progname,'【撤单】单据号:" + Noteno + "','" + Operant + "');";

		qry += "\n   commit;";

		qry += "\n   if length(v_dosql)>0 then"; //重新计算账本
		qry += "\n      execute immediate v_dosql;";
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

	// 离线开票转销售出库
	public int doOffline2shopsale(JSONObject jsonObject) {
		if (Houseid == null || Houseid == 0) {
			errmess = "houseid或ntid不是一个有效值！";
			return 0;
		}
		if (!jsonObject.has("warereclist")) {
			errmess = "缺少商品明细记录！";
			return 0;
		}
		// if (Ntid > 0 || Custid == null || Custid == 0) {
		// errmess = "custid不是一个有效值！";
		// return 0;
		//
		// }
		if (Func.isNull(Operant)) {
			errmess = "operant不是一个有效值！";
			return 0;
		}
		if (Func.subString(Notedate, 1, 10).compareTo(Calcdate) <= 0) {
			errmess = "不允许上传登账日" + Calcdate + "及之前的单据！";
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
		// }
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Notedate != null) {
			strSql1.append("notedate,");
			strSql2.append("to_date('" + Notedate + "','yyyy-mm-dd hh24:mi:ss'),");
		}
		// if (Custid != null) {
		// strSql1.append("custid,");
		// strSql2.append(Custid + ",");
		// }
		if (Guestid != null) {
			strSql1.append("guestid,");
			strSql2.append(Guestid + ",");
		}
		if (Houseid != null) {
			strSql1.append("houseid,");
			strSql2.append(Houseid + ",");
		}
		// if (Ntid != null) {
		// strSql1.append("ntid,");
		// strSql2.append(Ntid + ",");
		// }
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
			strSql2.append("'" + Checkcurr + "',");
		}
		if (Changecurr != null) {
			strSql1.append("changecurr,");
			strSql2.append("'" + Changecurr + "',");
		}
		if (Freecurr != null) {
			strSql1.append("freecurr,");
			strSql2.append("'" + Freecurr + "',");
		}
		if (Zpaycurr != null) {
			strSql1.append("zpaycurr,");
			strSql2.append("'" + Zpaycurr + "',");
		}
		if (Cent != null) {
			strSql1.append("cent,");
			strSql2.append(Cent + ",");
		}
		if (Jfcurr != null) {
			strSql1.append("jfcurr,");
			strSql2.append("'" + Jfcurr + "',");
		}
		if (Usecent != null) {
			strSql1.append("usecent,");
			strSql2.append(Usecent + ",");
		}
		if (Dptid != null) {
			strSql1.append("dptid,");
			strSql2.append(Dptid + ",");
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

		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_rs number; ");
		strSql.append("\n   v_noteno varchar2(20);");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n   v_totalamt number; ");
		strSql.append("\n   v_totalcurr number; ");
		strSql.append("\n begin ");

		strSql.append("\n   begin ");
		strSql.append("\n   select 'XC'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from shopsaleh");
		strSql.append("\n   where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'XC'||To_Char(SYSDATE,'yyyymmdd');");

		strSql.append("\n   v_id:=shopsaleh_id.nextval; ");
		strSql.append("\n   insert into shopsaleh (" + strSql1.toString() + ")");
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
			long salemanid = jsonObject2.has("salemanid") ? Long.parseLong(jsonObject2.getString("salemanid")) : 0;
			if (wareid == 0 || colorid == 0 || sizeid == 0) {
				errmess = "wareid或colorid或sizeid不是一个有效值！";
				return 0;
			}
			strSql.append("\n  insert into shopsalem (id,accid,noteno,wareid,colorid,sizeid,amount,price0,discount,price,curr,salemanid,remark0,houseid)");
			strSql.append("\n  values (shopsalem_id.nextval," + Accid + ",v_noteno," + wareid + "," + colorid + "," + sizeid + "," + amount + "," + price0//
					+ "," + discount + "," + price + "," + curr + "," + salemanid + ",'" + remark0 + "'," + Houseid + ");");
		}

		if (jsonObject.has("paylist")) {// 结算方式
			jsonArray = jsonObject.getJSONArray("paylist");
			strSql.append("\n   delete from shopsalepay where accid=" + Accid + " and noteno=v_noteno;");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				long payid = jsonObject2.has("payid") ? Long.parseLong(jsonObject2.getString("payid")) : 0;
				float curr = jsonObject2.has("curr") ? Float.parseFloat(jsonObject2.getString("curr")) : 0;
				strSql.append("\n  insert into shopsalepay (id,accid,noteno,payid,curr)");
				strSql.append("\n  values (shopsalepay_id.nextval," + Accid + ",v_noteno," + payid + "," + curr + ");");
			}
		}

		strSql.append("\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from shopsalem where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   update shopsaleh set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   p_shopsalepaylist(" + Accid + ",v_noteno);");

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

		// System.out.print(qry);
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

}