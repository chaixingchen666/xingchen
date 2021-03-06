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
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-14 09:32:27
//*************************************
public class Guestcurr implements java.io.Serializable {
	private Float Id;
	private Long Guestid;
	private Long Accid;
	private Date Notedate;
	private Long Houseid;
	private Long Curr;
	private Long Paycurr;
	private Integer Fs;
	private Integer Ly; // ly:0=手工输入,1=商场零售,2=店铺零售
	private String Xsnoteno;
	private String Operant;
	private String Remark;
	private Long Payid;

	private String errmess;

	private String Payname;
	private String Housename;
	private String Smsuid;
	private String Smsuser;
	private String Smspwd;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setSmsuid(String smsuid) {
		Smsuid = smsuid;
	}

	public void setSmsuser(String smsuser) {
		Smsuser = smsuser;
	}

	public void setSmspwd(String smspwd) {
		Smspwd = smspwd;
	}

	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setGuestid(Long guestid) {
		this.Guestid = guestid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getGuestid() {
		return Guestid;
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

	public void setCurr(Long curr) {
		this.Curr = curr;
	}

	public Long getCurr() {
		return Curr;
	}

	public void setPaycurr(Long paycurr) {
		this.Paycurr = paycurr;
	}

	public Long getPaycurr() {
		return Paycurr;
	}

	public void setFs(Integer fs) {
		this.Fs = fs;
	}

	public Integer getFs() {
		return Fs;
	}

	public void setLy(Integer ly) {
		this.Ly = ly;
	}

	public Integer getLy() {
		return Ly;
	}

	public void setXsnoteno(String xsnoteno) {
		this.Xsnoteno = xsnoteno;
	}

	public String getXsnoteno() {
		return Xsnoteno;
	}

	public void setOperant(String operant) {
		this.Operant = operant;
	}

	public String getOperant() {
		return Operant;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setPayid(Long payid) {
		this.Payid = payid;
	}

	public Long getPayid() {
		return Payid;
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
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前记录已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		// strSql.append(" update Guestcurr set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// }
		return 1;
	}

	// 增加记录
	public int Append() {

		if (Guestid == null || Guestid == 0) {
			errmess = "会员id无效！";
			return 0;
		}
		if (Houseid == null || Houseid == 0) {
			errmess = "店铺id无效！";
			return 0;
		}
		String qry = "select housename from warehouse where houseid=" + Houseid + " and accid=" + Accid;
		Housename = DbHelperSQL.RunSql(qry);
		if (Func.isNull(Housename)) {
			errmess = "店铺无效！";
			return 0;
		}

		if (Fs == 0) // 储值增加
		{
			if (Payid == null || Payid == 0) {
				errmess = "结算方式无效！";
				return 0;
			}
			qry = "select payname from payway where payid=" + Payid + " and accid=" + Accid;
			Payname = DbHelperSQL.RunSql(qry);
			if (Func.isNull(Payname)) {
				errmess = "结算方式无效！";
				return 0;
			}
			// if (!Func.isPaywayOk(Accid, Payid)) {
			// errmess = "结算方式无效！";
			// return 0;
			// }

			if (Curr == null || Curr == 0) {
				errmess = "储值金额无效！";
				return 0;
			}

		} else {// 储值消费

			Payid = (long) 0;
			Paycurr = (long) 0;
			if (Curr == null || Curr == 0) {
				errmess = "消费金额无效！";
				return 0;
			}
		}
		if (Remark == null || Remark.equals("")) {
			errmess = "请输入备注！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		// if (Id != null) {
		// strSql1.append("id,");
		// strSql2.append("'" + Id + "',");
		// }
		// if (Guestid != null) {
		strSql1.append("guestid,");
		strSql2.append(Guestid + ",");
		// }
		// if (Notedate != null) {
		// }
		if (Houseid != null) {
			strSql1.append("houseid,");
			strSql2.append(Houseid + ",");
		}
		if (Curr != null) {
			strSql1.append("curr,");
			strSql2.append(Curr + ",");
		}
		if (Paycurr != null) {
			strSql1.append("paycurr,");
			strSql2.append(Paycurr + ",");
		}
		if (Fs != null) {
			strSql1.append("fs,");
			strSql2.append(Fs + ",");
		}
		if (Ly != null) {
			strSql1.append("ly,");
			strSql2.append(Ly + ",");
		}
		if (Xsnoteno != null) {
			strSql1.append("xsnoteno,");
			strSql2.append("'" + Xsnoteno + "',");
		}
		// if (Operant != null) {
		strSql1.append("operant,");
		strSql2.append("'" + Operant + "',");
		// }
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Payid != null) {
			strSql1.append("payid,");
			strSql2.append(Payid + ",");
		}
		strSql1.append("notedate");
		strSql2.append("sysdate");
		// strSql1.append("lastdate");
		// strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_onhouseid number; ");
		strSql.append("\n   v_smsstr varchar2(500); ");
		strSql.append("\n   v_balcurr number; ");
		// strSql.append("\n v_payname varchar2(20);");
		strSql.append("\n begin ");
		// if (Payid > 0) {
		//
		// strSql.append("\n select payname into v_payname from payway where payid=" + Payid);
		// }
		strSql.append("\n   v_onhouseid:=0;");
		// strSql.append(" select count(*) into v_count from payway where accid="+Accid+" and statetag=1 and payid="+Payid+";");
		// strSql.append(" if v_count=0 then ");
		// strSql.append(" v_retcs:='0结算方式无效！';");
		// strSql.append(" goto exit;");
		// strSql.append(" end if;");
		strSql.append("\n     v_id:=Guestcurr_id.nextval; ");

		strSql.append("\n     insert into Guestcurr (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append("\n values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("\n   select sum(decode(fs,0,curr,-curr)) into v_balcurr from guestcurr where guestid=" + Guestid + ";");
		strSql.append("\n   update guestvip set lastdate=sysdate,lastop='" + Operant + "',balcurr=v_balcurr where guestid=" + Guestid + ";");
		// strSql.append("\n select '尊敬的'||guestname||decode(sex,'男','先生','女士'),mobile into v_guestname,:mobile from guestvip where guestid=" + Guestid + "; "); // 不加这2句要出错

		strSql.append("\n   select v_id,nvl(guestname,' '),nvl(sex,' '),nvl(vipno,' '),nvl(balcurr,0),nvl(mobile,' ') into :id,:guestname,:sex,:vipno,:balcurr,:mobile from guestvip  where guestid=" + Guestid + "; "); // 不加这2句要出错
		strSql.append("\n end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.FLOAT));
		param.put("guestname", new ProdParam(Types.VARCHAR));
		param.put("sex", new ProdParam(Types.VARCHAR));
		param.put("vipno", new ProdParam(Types.VARCHAR));
		param.put("mobile", new ProdParam(Types.VARCHAR));
		param.put("balcurr", new ProdParam(Types.FLOAT));
		// param.put("houseid", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			Id = Float.parseFloat(param.get("id").getParamvalue().toString());
			// long vipid = Long.parseLong(param.get("vipid").getParamvalue().toString());

			float balcurr = Float.parseFloat(param.get("balcurr").getParamvalue().toString());

			errmess = "\"msg\":\"操作成功！\",\"id\":" + Id + ",\"balcurr\":" + balcurr;
			if (Fs == 0)
				try {
					String mobile = param.get("mobile").getParamvalue().toString();

					String guestname = param.get("guestname").getParamvalue().toString();
					String sex = param.get("sex").getParamvalue().toString();
					String vipno = param.get("vipno").getParamvalue().toString();

					String strmess1 = "尊敬的" + guestname;
					if (sex.equals("女"))
						strmess1 += "女士";
					else if (sex.equals("男"))
						strmess1 += "先生";

					strmess1 += ":您尾号为" + Func.strRight(vipno, 4) + "的会员卡在『" + Housename + "』" + Payname + "充值" + Paycurr + "元,储值增加" + Curr + "元,储值余额" + balcurr + "元 。谢谢您的惠顾！";
					// System.out.println(Smsuid + " " + Smsuser + " " + Smspwd+" "+mobile);
					Func.sendMessage(Smsuid, Smsuser, Smspwd, mobile, strmess1, Operant, Accid);

					if (Houseid != null && Houseid > 0) {
						// ret = JGPush.PushToVip(s1, tag1, strmess1, onhouseid, 1, 0);
						Vipmessage dal = new Vipmessage();
						dal.setLastop(Operant);
						dal.setRemark(strmess1);
						dal.setAccid(Accid);
						// dal.setLx(1); // lx:0=通知，1=消费请求
						dal.doSendmess1(Houseid, Guestid);
					}

				} catch (Exception e) {
					LogUtils.LogErrWrite("addguestcurr", e.getMessage());
				}
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Guestcurr set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Guestid != null) {
			strSql.append("guestid=" + Guestid + ",");
		}
		if (Notedate != null) {
			strSql.append("notedate='" + Notedate + "',");
		}
		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
		}
		if (Curr != null) {
			strSql.append("curr=" + Curr + ",");
		}
		if (Paycurr != null) {
			strSql.append("paycurr=" + Paycurr + ",");
		}
		if (Fs != null) {
			strSql.append("fs=" + Fs + ",");
		}
		if (Ly != null) {
			strSql.append("ly=" + Ly + ",");
		}
		if (Xsnoteno != null) {
			strSql.append("xsnoteno='" + Xsnoteno + "',");
		}
		if (Operant != null) {
			strSql.append("operant='" + Operant + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Payid != null) {
			strSql.append("payid=" + Payid + ",");
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
		strSql.append("\n FROM Guestcurr a");
		strSql.append("\n left outer join  warehouse b on a.houseid=b.houseid");
		strSql.append("\n join guestvip d on a.guestid=d.guestid");
		strSql.append("\n left outer join guesttype e on d.vtid=e.vtid");
		strSql.append("\n left outer join  payway c on a.payid=c.payid ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist, int cxfs) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM Guestcurr a");
		strSql.append("\n left outer join  warehouse b on a.houseid=b.houseid");
		strSql.append("\n left outer join  payway c on a.payid=c.payid ");
		if (cxfs == 1) {
			strSql.append("\n join guestvip d on a.guestid=d.guestid");
			strSql.append("\n left outer join  guesttype f on d.vtid=f.vtid");
			strSql.append("\n left outer join  warehouse e on d.houseid=e.houseid");
			qp.setSumString("nvl(sum(curr0),0) as totalcurr0,nvl(sum(paycurr),0) as totalpaycurr");
		}

		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public String GetTable2Excel(QueryParam qp, String strWhere, String fieldlist, int cxfs, JSONObject jsonObject) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM Guestcurr a");
		strSql.append("\n left outer join  warehouse b on a.houseid=b.houseid");
		strSql.append("\n left outer join  payway c on a.payid=c.payid ");
		if (cxfs == 1) {
			strSql.append("\n join guestvip d on a.guestid=d.guestid");
			strSql.append("\n left outer join  guesttype f on d.vtid=f.vtid");
			strSql.append("\n left outer join  warehouse e on d.houseid=e.houseid");
			qp.setSumString("nvl(sum(curr0),0) as totalcurr0");
		}

		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		qp.setPageIndex(1);
		qp.setPageSize(100);
		return DbHelperSQL.Table2Excel(qp, jsonObject);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Guestcurr");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}
}