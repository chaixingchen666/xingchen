package com.oracle.bean;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
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
//  @author:sunhong  @date:2017-03-14 09:32:12
//*************************************
public class Guestvip implements java.io.Serializable {
	private Long Guestid;
	private String Guestname;
	private String Shortname;
	private String Sex;
	private String Vipno;
	private Long Accid;
	private Long Vtid;

	private String Mobile;
	private String Idcard;

	private String Birthday;
	private Integer Statetag;
	private Integer Usetag;
	private String Stature;
	private String Fitsize;
	private String Likesome;
	private Float Balcent;
	private Float Balcurr;
	private Long Houseid;
	private String Lastop;
	private Long Vipid;
	private String Remark;

	private String Createdate;
	private Date Lastdate;
	private String Validdate;
	private String Address;
	private String Email;
	private Long Epid;
	private Integer Lytag;
	private String errmess;
	private String Vtname;
	private String Housename;

	private String Idstr;

	public String getVtname() {
		return Vtname;
	}

	public void setVtname(String vtname) {
		Vtname = vtname;
	}

	public String getIdstr() {
		return Idstr;
	}

	public void setIdstr(String idstr) {
		Idstr = idstr;
	}

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setGuestid(Long guestid) {
		this.Guestid = guestid;
	}

	public Long getGuestid() {
		return Guestid;
	}

	public String getIdcard() {
		return Idcard;
	}

	public void setIdcard(String idcard) {
		Idcard = idcard;
	}
	public String getHousename() {
		return Housename;
	}

	public void setHousename(String housename) {
		Housename = housename;
	}

	public void setGuestname(String guestname) {
		this.Guestname = guestname;
	}

	public String getGuestname() {
		return Guestname;
	}

	public void setShortname(String shortname) {
		this.Shortname = shortname;
	}

	public String getShortname() {
		return Shortname;
	}

	public void setSex(String sex) {
		this.Sex = sex;
	}

	public String getSex() {
		return Sex;
	}

	public void setVipno(String vipno) {
		this.Vipno = vipno;
	}

	public String getVipno() {
		return Vipno;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setVtid(Long vtid) {
		this.Vtid = vtid;
	}

	public Long getVtid() {
		return Vtid;
	}

	public void setMobile(String mobile) {
		this.Mobile = mobile;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setBirthday(String birthday) {
		this.Birthday = birthday;
	}

	public String getBirthday() {
		return Birthday;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setUsetag(Integer usetag) {
		this.Usetag = usetag;
	}

	public Integer getUsetag() {
		return Usetag;
	}

	public void setStature(String stature) {
		this.Stature = stature;
	}

	public String getStature() {
		return Stature;
	}

	public void setFitsize(String fitsize) {
		this.Fitsize = fitsize;
	}

	public String getFitsize() {
		return Fitsize;
	}

	public void setLikesome(String likesome) {
		this.Likesome = likesome;
	}

	public String getLikesome() {
		return Likesome;
	}

	public void setBalcent(Float balcent) {
		this.Balcent = balcent;
	}

	public Float getBalcent() {
		return Balcent;
	}

	public void setBalcurr(Float balcurr) {
		this.Balcurr = balcurr;
	}

	public Float getBalcurr() {
		return Balcurr;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public void setCreatedate(String createdate) {
		this.Createdate = createdate;
	}

	public String getCreatedate() {
		//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//		return df.format(Createdate);
		return Createdate;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setVipid(Long vipid) {
		this.Vipid = vipid;
	}

	public Long getVipid() {
		return Vipid;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setValiddate(String validdate) {
		this.Validdate = Func.strLeft(validdate, 10);
	}

	// public String getValiddate() {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// return df.format(Validdate);
	// }

	public void setAddress(String address) {
		this.Address = address;
	}

	public String getAddress() {
		return Address;
	}

	public void setEmail(String email) {
		this.Email = email;
	}

	public String getEmail() {
		return Email;
	}

	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public Long getEpid() {
		return Epid;
	}

	public void setLytag(Integer lytag) {
		this.Lytag = lytag;
	}

	public Integer getLytag() {
		return Lytag;
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
		strSql.append("  select  id from wareouth where guestid=" + Guestid + " and rownum=1");
		strSql.append("  union all");
		strSql.append("  select  id from shopsaleh where guestid=" + Guestid + " and rownum=1");
		strSql.append("  union all");
		strSql.append("  select  id from guestcent where guestid=" + Guestid + " and rownum=1");
		strSql.append("  union all");
		strSql.append("  select  id from guestcurr where guestid=" + Guestid + " and rownum=1");

		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前会员已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		strSql.append("declare");
		strSql.append("    v_vtid number(10);");
		strSql.append("    v_vipid number(10);");
		strSql.append("    v_onhouseid number(10);");
		strSql.append(" begin");
		strSql.append("   select a.vtid,a.vipid,nvl(b.onhouseid,0) into v_vtid,v_vipid,v_onhouseid from guestvip a left outer join warehouse b on a.houseid=b.houseid");
		strSql.append("   where a.guestid=" + Guestid + " and a.accid=" + Accid + ";");
		strSql.append("   update guestvip set statetag=2 where guestid=" + Guestid + " and accid=" + Accid + ";");
		strSql.append("   if v_vipid>0 and v_onhouseid>0 then ");
		strSql.append("      delete from viphouse where onhouseid=v_onhouseid and vipid=v_vipid;");
		strSql.append("   end if;");
		strSql.append(" end;");
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append() {
		if (Guestname == null || Guestname.trim().equals("")) {
			errmess = "会员姓名不允许为空！";
			return 0;
		}
		if (Vtid == null) {
			errmess = "会员卡类型不允许为空！";
			return 0;
		}
		if (Mobile != null && !Mobile.equals("")) {
			if (!Func.isMobile(Mobile)) {
				errmess = "移动电话:" + Mobile + "无效！";
				return 0;
			}
		}
		if (Validdate != null) {
			if (!Func.isValidDate(Validdate)) {
				errmess = "会员有效期不是一个有效值！";
				return 0;
			}
		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("guestid,");
		strSql2.append("v_id,");
		// if (Guestid != null) {
		// strSql1.append("guestid,");
		// strSql2.append(Guestid + ",");
		// }
		if (Guestname != null) {
			strSql1.append("guestname,");
			strSql2.append("'" + Guestname + "',");
			Shortname = PinYin.getPinYinHeadChar(Guestname);
		}
		if (Shortname != null) {
			strSql1.append("shortname,");
			strSql2.append("'" + Shortname + "',");
		}
		if (Sex != null) {
			strSql1.append("sex,");
			strSql2.append("'" + Sex + "',");
		}
		if (Vipno != null) {
			strSql1.append("vipno,");
			strSql2.append("'" + Vipno.toUpperCase() + "',");
		}
		if (Idcard != null) {
			strSql1.append("Idcard,");
			strSql2.append("'" + Idcard.toUpperCase() + "',");
		}
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		// if (Vtid != null) {
		strSql1.append("vtid,");
		strSql2.append(Vtid + ",");
		// }
		if (Mobile != null) {
			strSql1.append("mobile,");
			strSql2.append("'" + Mobile + "',");
		}
		if (Birthday != null) {
			// strSql1.append("birthday,");
			// strSql2.append("'" + Birthday + "',");
			if (Func.isValidDate(Birthday)) {

				// strSql.append("birthday='" + Func.DateToStr(Func.StrToDate(Birthday)) + "',");
				strSql1.append("Birthday,");
				strSql2.append("'" + Func.DateToStr(Func.StrToDate(Birthday)) + "',");
			}
		}
		// if (Statetag != null) {
		strSql1.append("statetag,");
		strSql2.append(Statetag + ",");
		// }
		if (Usetag != null) {
			strSql1.append("usetag,");
			strSql2.append(Usetag + ",");
		}
		if (Stature != null) {
			strSql1.append("stature,");
			strSql2.append("'" + Stature + "',");
		}
		if (Fitsize != null) {
			strSql1.append("fitsize,");
			strSql2.append("'" + Fitsize + "',");
		}
		if (Likesome != null) {
			strSql1.append("likesome,");
			strSql2.append("'" + Likesome + "',");
		}
		if (Balcent != null) {
			strSql1.append("balcent,");
			strSql2.append("'" + Balcent + "',");
		}
		if (Balcurr != null) {
			strSql1.append("balcurr,");
			strSql2.append("'" + Balcurr + "',");
		}
		if (Houseid != null) {
			strSql1.append("houseid,");
			strSql2.append(Houseid + ",");
		}
		// if (Createdate != null) {
		// }
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		if (Vipid != null) {
			strSql1.append("vipid,");
			strSql2.append(Vipid + ",");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Validdate != null) {
			//			 if (!Func.isValidDate(Validdate)) {
			//			 
			//			 }

			// strSql.append("birthday='" + Func.DateToStr(Func.StrToDate(Birthday)) + "',");
			strSql1.append("validdate,");
			strSql2.append("to_date('" + Func.subString(Validdate, 1, 10) + "','yyyy-mm-dd'),");
			// }

		}
		if (Address != null) {
			strSql1.append("address,");
			strSql2.append("'" + Address + "',");
		}
		if (Email != null) {
			strSql1.append("email,");
			strSql2.append("'" + Email + "',");
		}
		if (Epid != null) {
			strSql1.append("epid,");
			strSql2.append(Epid + ",");
		}
		if (Lytag != null) {
			strSql1.append("lytag,");
			strSql2.append(Lytag + ",");
		}
		strSql1.append("createdate,");
		strSql2.append("sysdate,");
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		if (Vipno != null && !Vipno.equals("")) {
			strSql.append("\n   select count(*) into v_count from guestvip where accid=" + Accid + " and vipno='" + Vipno + "' and statetag<2;");
			strSql.append("\n   if v_count>0 then ");
			strSql.append("\n      v_retcs:='0会员卡号:" + Vipno + " 已存在，请重新输入！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Mobile != null && !Mobile.equals("")) {
			strSql.append("\n   select count(*) into v_count from guestvip where accid=" + Accid + " and Mobile='" + Mobile + "' and statetag<2;");
			strSql.append("\n   if v_count>0 then ");
			strSql.append("\n      v_retcs:='0移动电话:" + Mobile + " 已存在，请重新输入！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Vtid != null && Vtid > 0) {
			strSql.append("\n   select count(*) into v_count from guesttype where accid=" + Accid + " and vtid=" + Vtid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0会员类型无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		strSql.append("\n   v_id:=Guestvip_guestid.nextval; ");
		strSql.append("\n   insert into Guestvip (" + strSql1.toString() + ")");
		strSql.append("\n   values (" + strSql2.toString() + ");");
		strSql.append("\n    v_retcs:='1'||v_id; ");
		strSql.append("\n    <<exit>>");
		strSql.append("\n    select v_retcs into :retcs from dual;");
		strSql.append("\n end;");
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

		// Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		// param.put("id", new ProdParam(Types.BIGINT));
		// int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		// if (ret < 0) {
		// errmess = "操作异常！";
		// return 0;
		// } else {
		// Guestid = Long.parseLong(param.get("id").getParamvalue().toString());
		// errmess = Guestid.toString();
		// return 1;
		// }
	}

	//同步一卡易会员数据
	public int ykyUpdate() {
		if (Func.isNull(Idstr)) {
			errmess = "idstr参数无效！";
			return 0;
		}
		String qry = "declare";
		qry += "\n   v_count number;";
		qry += "\n   v_guestid number(10);";
		qry += "\n   v_vtid number(10);";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_houseid number(10);";
		qry += "\n begin";
		qry += "\n   begin";
		qry += "\n     select vtid into v_vtid from guesttype where accid=" + Accid + " and vtname='" + Vtname + "' and statetag=1 and rownum=1;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n     v_retcs:='0会员卡类型:" + Vtname + " 无效！';";
		qry += "\n     goto exit;";
		qry += "\n   end;";
		qry += "\n   begin";
		qry += "\n     select houseid into v_houseid from warehouse where accid=" + Accid + " and housename='" +Housename + "' and statetag=1 and rownum=1;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n     v_houseid:=0;";
		qry += "\n   end;";

		qry += "\n   begin";
		qry += "\n     select guestid into v_guestid from guestvip where accid=" + Accid + " and statetag=1 and idstr='" + Idstr + "' and rownum=1;";
		qry += "\n     update guestvip set lastdate=sysdate,lastop='" + Lastop + "',lytag=2,vtid=v_vtid,houseid=v_houseid";
		if (Guestname != null)
			qry += "\n     ,guestname='" + Guestname + "'";
		if (Vipno != null)
			qry += "\n     ,Vipno='" + Vipno + "'";
		if (Sex != null)
			qry += "\n     ,sex='" + Sex + "'";
		if (Mobile != null)
			qry += "\n     ,mobile='" + Mobile + "'";
		if (Idcard != null)
			qry += "\n     ,Idcard='" + Idcard + "'";
		if (Birthday != null && Func.isValidDate(Birthday))
			qry += "\n     ,Birthday='" + Birthday + "'";
		if (Email != null)
			qry += "\n     ,Email='" + Email + "'";
		if (Remark != null)
			qry += "\n     ,Remark='" + Remark + "'";
		if (Createdate != null)
			qry += "\n     ,Createdate=to_date('" + Createdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Validdate != null)
			qry += "\n     ,Validdate=to_date('" + Validdate + "','yyyy-mm-dd')";

		qry += "\n     where guestid=v_guestid;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n     insert into guestvip(accid,guestid,idstr,vtid,houseid,guestname,vipno,sex,mobile,idcard,birthday,email,remark,Createdate,validdate,statetag,lytag,lastdate,lastop)";
		qry += "\n     values ("+Accid+",guestvip_guestid.nextval,'" + Idstr + "',v_vtid,v_houseid";
		if (Guestname != null)
			qry += "\n     ,'" + Guestname + "'";
		else
			qry += "\n     ,''";
		if (Vipno != null)
			qry += "\n     ,'" + Vipno + "'";
		else
			qry += "\n     ,''";

		if (Sex != null)
			qry += "\n     ,'" + Sex + "'";
		else
			qry += "\n     ,''";
		if (Mobile != null)
			qry += "\n     ,'" + Mobile + "'";
		else
			qry += "\n     ,''";
		if (Idcard != null)
			qry += "\n     ,'" + Idcard + "'";
		else
			qry += "\n     ,''";
		if (Birthday != null && Func.isValidDate(Birthday))
			qry += "\n     ,'" + Birthday + "'";
		else
			qry += "\n     ,''";
		if (Email != null)
			qry += "\n     ,'" + Email + "'";
		else
			qry += "\n     ,''";
		if (Remark != null)
			qry += "\n     ,'" + Remark + "'";
		else
			qry += "\n     ,''";
		if (Createdate != null)
			qry += "\n     ,to_date('" + Createdate + "','yyyy-mm-dd hh24:mi:ss')";
		else
			qry += "\n     ,sysdate";
		if (Validdate != null)
			qry += "\n     ,to_date('" + Validdate + "','yyyy-mm-dd')";
		else
			qry += "\n     ,sysdate+30";

		qry += "\n  ,1,2,sysdate,'" + Lastop + "');";
		qry += "\n   end;";
		qry += "\n   v_retcs:='1操作成功！';";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";
//		System.out.println(qry);
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

	// 修改记录
	public int Update() {
		if (Guestid == null || Guestid == 0) {
			errmess = "guestid参数无效！";
			return 0;
		}
		if (Validdate != null) {
			if (!Func.isValidDate(Validdate)) {
				errmess = "会员有效期不是一个有效值！";
				return 0;
			}
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Guestvip set ");
		// if (Guestid != null) {
		// strSql.append("guestid=" + Guestid + ",");
		// }
		if (Guestname != null) {
			strSql.append("guestname='" + Guestname + "',");
			Shortname = PinYin.getPinYinHeadChar(Guestname);
		}
		if (Shortname != null) {
			strSql.append("shortname='" + Shortname + "',");
		}
		if (Sex != null) {
			strSql.append("sex='" + Sex + "',");
		}
		if (Vipno != null) {
			strSql.append("vipno='" + Vipno + "',");
		}
		if (Idcard != null) {
			strSql.append("Idcard='" + Idcard + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Vtid != null) {
			strSql.append("vtid=" + Vtid + ",");
		}
		if (Mobile != null) {
			strSql.append("mobile='" + Mobile + "',");
		}
		if (Birthday != null) {

			if (Func.isValidDate(Birthday)) {

				strSql.append("birthday='" + Func.DateToStr(Func.StrToDate(Birthday)) + "',");
			}
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Usetag != null) {
			strSql.append("usetag=" + Usetag + ",");
		}
		if (Stature != null) {
			strSql.append("stature='" + Stature + "',");
		}
		if (Fitsize != null) {
			strSql.append("fitsize='" + Fitsize + "',");
		}
		if (Likesome != null) {
			strSql.append("likesome='" + Likesome + "',");
		}
		if (Balcent != null) {
			strSql.append("balcent='" + Balcent + "',");
		}
		if (Balcurr != null) {
			strSql.append("balcurr='" + Balcurr + "',");
		}
		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
		}
		// if (Createdate != null) {
		// strSql.append("createdate=to_date('" + Createdate + "','yyyy-mm-dd'),");
		// }
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
		if (Vipid != null) {
			strSql.append("vipid=" + Vipid + ",");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Validdate != null) {
			strSql.append("validdate=to_date('" + Func.subString(Validdate, 1, 10) + "','yyyy-mm-dd'),");
		}
		if (Address != null) {
			strSql.append("address='" + Address + "',");
		}
		if (Email != null) {
			strSql.append("email='" + Email + "',");
		}
		if (Epid != null) {
			strSql.append("epid=" + Epid + ",");
		}
		if (Lytag != null) {
			strSql.append("lytag=" + Lytag + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where guestid=" + Guestid + " and accid=" + Accid + " ;");
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
		strSql.append("\n FROM guestvip a");
		strSql.append("\n left outer join guesttype b on a.vtid=b.vtid");
		strSql.append("\n left outer join warehouse c on a.houseid=c.houseid");
		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		System.out.print(strSql.toString());
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public String GetTable2Excel(QueryParam qp, String strWhere, String fieldlist, JSONObject jsonObject) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM Guestvip a");
		strSql.append("\n left outer join guesttype b on a.vtid=b.vtid");
		strSql.append("\n left outer join warehouse c on a.houseid=c.houseid");
		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		qp.setPageIndex(1);
		qp.setPageSize(100);
		return DbHelperSQL.Table2Excel(qp, jsonObject);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM Guestvip a");
		strSql.append("\n left outer join guesttype b on a.vtid=b.vtid");
		strSql.append("\n left outer join warehouse c on a.houseid=c.houseid");
		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		qp.setSumString("nvl(sum(balcent),0) as totalcent,nvl(sum(balcurr),0) as totalcurr");
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Guestvip");
		return false;
	}

	// 载入会员的初始积分及储值余额
	public int doAppendGuestcentorcurr() {
		if (Guestid == 0 && Vipno.equals("")) {
			errmess = "会员id或会员卡号无效！";
			return 0;
		}
		if (Balcent == 0 && Balcurr == 0) {
			errmess = "初始积分和储值余额不能都为0！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n      v_guestid number(20);";
		qry += "\n      v_retcs varchar2(200);";
		qry += "\n  begin";

		qry += "\n    begin";
		if (Guestid > 0) {
			qry += "\n    select guestid into v_guestid from guestvip where guestid=" + Guestid + " and statetag=1 and accid=" + Accid + ";";

		} else {
			qry += "\n    select guestid into v_guestid from guestvip where vipno='" + Vipno + "' and rownum=1 and statetag=1 and accid=" + Accid + ";";
		}
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n     v_retcs:= '0会员未找到！' ;";
		qry += "\n     goto exit;";
		qry += "\n   end;";

		if (Balcent != 0) {
			qry += "\n   insert into guestcent (id,guestid,notedate,houseid,cent,fs,ly,remark,operant)";
			qry += "\n   values (guestcent_id.nextval,v_guestid,sysdate,0," + Balcent + ",0,1,'初始增加积分余额','" + Lastop + "');";
		}
		if (Balcurr != 0) {
			qry += "\n   insert into guestcurr (id,guestid,notedate,houseid,curr,fs,ly,remark,operant)";
			qry += "\n   values (guestcurr_id.nextval,v_guestid,sysdate,0," + Balcurr + ",0,1,'初始增加储值余额','" + Lastop + "');";
		}
		qry += "\n   update guestvip set balcent =nvl((select sum(case fs when 0 then cent else -cent end) from guestcent where guestcent.guestid=guestvip.guestid),0)";
		qry += "\n   where guestid=v_guestid; ";
		qry += "\n   update guestvip set balcurr =nvl((select sum(case fs when 0 then curr else -curr end) from guestcurr where guestcurr.guestid=guestvip.guestid),0)";
		qry += "\n   where guestid=v_guestid; ";
		qry += "\n   v_retcs:= '1操作成功！'; ";
		qry += "\n   <<exit>> ";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";
		// System.out.println(qry);
		// LogUtils.LogDebugWrite("doAppendGuestcentorcurr", qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret == 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		// LogUtils.LogDebugWrite("doAppendGuestcentorcurrdo2", retcs);

		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

	public String doListVipsalenote(QueryParam qp) {
		String qry = " select a.id,a.noteno,to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss') as notedate,to_char(a.notedate,'yyyy-mm-dd') as daystr,a.remark,'XX' as notetype,0 as bj";
		qry += " from wareouth a";
		qry += " join warehouse b on a.houseid=b.houseid";
		qry += " where a.statetag=1 and a.ntid=0 and a.accid=" + Accid;
		qry += " and a.guestid=" + Guestid;
		qry += " and b.onhouseid=" + Houseid;
		qry += " union all";
		qry += " select a.id,a.noteno,to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss') as notedate,to_char(a.notedate,'yyyy-mm-dd') as daystr,a.remark,'XC' as notetype,1 as bj";
		qry += " from shopsaleh a";
		qry += " where a.statetag=1 and a.accid=" + Accid;
		qry += " and a.guestid=" + Guestid;
		qry += " and exists (select 1 from shopsalem b join warehouse c on b.houseid=c.houseid where a.accid=b.accid and a.noteno=b.noteno";
		qry += " and c.onhouseid=" + Houseid + ")";
		// qry += " )";
		String sort = " daystr desc,notedate desc,id,notetype ";
		// WriteLogTXT("debug", "listvipsalenote", qry);
		// int rs = 0;
		qp.setSortString(sort);
		qp.setQueryString(qry);
		Table tb = new Table();
		tb = DbHelperSQL.GetTable(qp);

		// Write(YTJR.COMMON.JsonHelper.DataTable2Json(dt, rs));
		String qry1;
		String fh = "";
		String noteno = "";
		int bj;
		// long vipid = 0;
		String retcs = "{\"result\":1,\"msg\":\"操作成功！\"," + qp.getTotalString() + ",\"rows\":[";
		int count = tb.getRowCount();
		for (int i = 0; i < count; i++) {
			noteno = tb.getRow(i).get("NOTENO").toString();
			bj = Integer.parseInt(tb.getRow(i).get("BJ").toString());
			retcs += fh + "{\"NOTENO\":\"" + noteno + "\"" + ",\"NOTEDATE\":\"" + tb.getRow(i).get("NOTEDATE").toString() + "\"" + ",\"DAYSTR\":\"" + tb.getRow(i).get("DAYSTR").toString() + "\""
			// + ",\"STATETAG\":\"" + tb.getRow(i).get("statetag").toString() + "\""
			// + ",\"ONHOUSEID\":\"" + tb.getRow(i).get("onhouseid").toString() + "\""
			// + ",\"ONHOUSENAME\":\"" + tb.getRow(i).get("onhousename").toString() + "\""
					+ ",\"NOTETYPE\":\"" + tb.getRow(i).get("NOTETYPE").toString() + "\""
					// + ",\"TOTALCURR\":\"" + tb.getRow(i).get("totalcurr").toString() + "\""
					+ ",\"REMARK\":\"" + tb.getRow(i).get("REMARK").toString() + "\""
			// + ",\"LASTDATE\":\"" + tb.getRow(i).get("lastdate").toString() + "\""
			;
			// 取购物明细
			qry1 = "select a.id,a.wareid,a.colorid,a.sizeid,b.warename,b.units,b.wareno,b.retailsale,c.colorname,d.sizename,a.amount,a.price0,a.discount,a.price,a.curr,a.remark0,b.imagename0 ";
			// qry1 += ",e.remark as wareremark";
			if (bj == 0)
				qry1 += " from wareoutm a";
			else
				qry1 += " from shopsalem a";
			qry1 += " left outer join warecode b on a.wareid=b.wareid ";
			qry1 += " left outer join colorcode c on a.colorid=c.colorid";
			qry1 += " left outer join sizecode d on a.sizeid=d.sizeid";
			qry1 += " where a.accid= " + Accid + " and a.noteno='" + noteno + "'";
			qry1 += " order by a.wareid,a.id";
			Table tb1 = DbHelperSQL.Query(qry1).getTable(1);
			int count1 = tb1.getRowCount();
			retcs += ",\"wareoutlist\":[";
			String fh1 = "";
			for (int j = 0; j < count1; j++) {
				retcs += fh1 + "{\"ID\":\"" + tb1.getRow(j).get("ID").toString() + "\"" + ",\"WAREID\":\"" + tb1.getRow(j).get("WAREID").toString() + "\"" + ",\"WARENAME\":\"" + tb1.getRow(j).get("WARENAME").toString()
						+ "\""
						// + ",\"WAREREMARK\":\"" + tb1.getRow(j).get("WAREREMARK").toString() + "\""
						+ ",\"UNITS\":\"" + tb1.getRow(j).get("UNITS").toString() + "\"" + ",\"WARENO\":\"" + tb1.getRow(j).get("WARENO").toString() + "\"" + ",\"COLORNAME\":\""
						+ tb1.getRow(j).get("COLORNAME").toString() + "\"" + ",\"SIZENAME\":\"" + tb1.getRow(j).get("SIZENAME").toString() + "\"" + ",\"AMOUNT\":\"" + tb1.getRow(j).get("AMOUNT").toString() + "\""
						+ ",\"PRICE0\":\"" + tb1.getRow(j).get("PRICE0").toString() + "\"" + ",\"DISCOUNT\":\"" + tb1.getRow(j).get("DISCOUNT").toString() + "\"" + ",\"PRICE\":\"" + tb1.getRow(j).get("PRICE").toString()
						+ "\"" + ",\"CURR\":\"" + tb1.getRow(j).get("CURR").toString() + "\"" + ",\"IMAGENAME0\":\"" + tb1.getRow(j).get("IMAGENAME0").toString() + "\"" + ",\"RETAILSALE\":\""
						+ tb1.getRow(j).get("RETAILSALE").toString() + "\"" + ",\"REMARK0\":\"" + tb1.getRow(j).get("REMARK0").toString() + "\"}";
				fh1 = ",";
			}
			retcs += "]}";

			fh = ",";

		}
		retcs += "]}";

		return retcs;
	}

	// 查找会员
	public int doFind(String nowdate) {
		if (Vipno.equals("") || Mobile.equals("")) {
			errmess = "请输入会员卡号或手机号！";
			return 0;

		}
		String qry = "select A.GUESTID,A.GUESTNAME,A.SEX,A.VIPNO,A.VTID,A.MOBILE,A.USETAG,B.VTNAME,B.DISCOUNT,A.BALCENT,A.BALCURR,A.VALIDDATE";
		qry += " FROM guestvip a";
		qry += " left outer join guesttype b on a.vtid=b.vtid";
		// qry += " left outer join warehouse c on a.houseid=c.houseid";
		qry += " where a.statetag=1 and a.accid= " + Accid;
		qry += " and (a.vipno='" + Vipno + "' or a.mobile='" + Mobile + "')";
		Table tb = new Table();
		// WriteLogTXT("debug", "findguestvip", qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		int rs = tb.getRowCount();
		if (rs == 0) {
			errmess = "未找到满足条件会员！";
			return 0;
		}
		if (rs > 1) {
			errmess = "找到多个会员，请打开会员帮助继续查找！";
			return 0;

		}
		String validdate = Func.subString(tb.getRow(0).get("VALIDDATE").toString(), 1, 10); // 会员有效期
		// if (begindate > enddate)
		if (validdate.compareTo(nowdate) < 0) {
			errmess = "当前会员卡已过有效期！";
			return 0;
		}

		int usetag = Integer.parseInt(tb.getRow(0).get("USETAG").toString());
		if (usetag == 1) {
			errmess = "当前会员卡已禁用！";
			return 0;
		} else if (usetag == 2) {
			errmess = "当前会员卡已挂失！";
			return 0;
		}

		// string qry = "select a.GUESTID,A.GUESTNAME,A.SEX,a.vipno,a.vtid,a.mobile,a.usetag,b.vtname,b.discount,A.BALCENT,A.BALCURR";

		errmess = "\"msg\": \"找到会员！\"";

		errmess += ",\"rows\":{\"guestid\":\"" + tb.getRow(0).get("GUESTID").toString() + "\"" + ",\"GUESTNAME\":\"" + tb.getRow(0).get("GUESTNAME").toString() + "\"" + ",\"SEX\":\"" + tb.getRow(0).get("SEX").toString()
				+ "\"" + ",\"VIPNO\":\"" + tb.getRow(0).get("VIPNO").toString() + "\"" + ",\"MOBILE\":\"" + tb.getRow(0).get("MOBILE").toString() + "\""
				// + ",\"USETAG\":\"" + tb.getRow(0).get("USETAG"].ToString() + "\""
				+ ",\"VTNAME\":\"" + tb.getRow(0).get("VTNAME").toString() + "\"" + ",\"DISCOUNT\":\"" + tb.getRow(0).get("DISCOUNT").toString() + "\"" + ",\"BALCENT\":\"" + tb.getRow(0).get("BALCENT").toString() + "\""
				+ ",\"BALCURR\":\"" + tb.getRow(0).get("BALCURR").toString() + "\"" + "}";

		return 1;
	}

	// 销售助手返回会员信息
	public int doGuestvipinfo(String accdate) {
		if (Guestid == 0 && (Vipno == null || Vipno.equals(""))) {
			errmess = "会员参数无效！";
			return 0;
		}
		String qry = "  select a.guestid,a.vipno,a.guestname,a.sex,a.balcent,a.balcurr,b.discount,b.vtname,a.birthday,a.mobile,a.usetag,a.fitsize,a.likesome,a.createdate,nvl(floor(sysdate - a.createdate),0) as days";
		qry += "  from guestvip a";
		qry += "  left outer join guesttype b on a.vtid=b.vtid";
		qry += "  where a.statetag=1 and a.accid=" + Accid;
		if (Guestid > 0)
			qry += " and a.guestid=" + Guestid;
		else if (!Vipno.equals(""))
			qry += " and a.vipno='" + Vipno + "'";

		// WriteLogTXT("debug", "getguestvip", qry);
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		int rs = tb.getRowCount();
		if (rs == 0) {
			errmess = "未找到会员！";
			return 0;
		}
		int days = Integer.parseInt(tb.getRow(0).get("DAYS").toString());
		Guestid = Long.parseLong(tb.getRow(0).get("GUESTID").toString());

		String mindate = Func.subString(tb.getRow(0).get("createdate").toString(), 1, 10);

		if (mindate.compareTo(accdate) < 0)
			mindate = accdate;

		// p_AccBegindatestr

		String responsestr = "\"msg\":\"操作成功！\",\"GUESTID\":\"" + Guestid + "\"" //
				+ ",\"GUESTNAME\":\"" + tb.getRow(0).get("GUESTNAME").toString() + "\"" //
				+ ",\"VIPNO\":\"" + tb.getRow(0).get("VIPNO").toString() + "\"" //
				+ ",\"SEX\":\"" + tb.getRow(0).get("SEX").toString() + "\"" //
				+ ",\"VTNAME\":\"" + tb.getRow(0).get("VTNAME").toString() + "\"" //
				+ ",\"MOBILE\":\"" + tb.getRow(0).get("MOBILE").toString() + "\"" //
				+ ",\"BIRTHDAY\":\"" + tb.getRow(0).get("BIRTHDAY").toString() + "\"" //
				+ ",\"DISCOUNT\":\"" + tb.getRow(0).get("DISCOUNT").toString() + "\""//
				+ ",\"BALCENT\":\"" + tb.getRow(0).get("BALCENT").toString() + "\""//
				+ ",\"BALCURR\":\"" + tb.getRow(0).get("BALCURR").toString() + "\"" //
				+ ",\"LIKESOME\":\"" + tb.getRow(0).get("LIKESOME").toString() + "\""//
				+ ",\"FITSIZE\":\"" + tb.getRow(0).get("FITSIZE").toString() + "\"";
		// 最近消费时间
		qry = "  select nvl(sum(amount),0) as amount,nvl(sum(curr),0) as curr,nvl(sum(linecurr),0) as linecurr,nvl(sum(retailcurr),0) as retailcurr,to_char(max(notedate),'yyyy-mm-dd hh24:mi') as lastdate ,count(*) as xsnum from (";
		qry += " select a.noteno,sum(b.amount) as amount,sum(b.curr) as curr,sum(b.amount*c.retailsale) as retailcurr, max(notedate) as notedate";
		qry += " ,sum(case a.onhouseid when 0 then 0 else b.curr end) as linecurr";
		qry += " from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += " join warecode c on b.wareid=c.wareid";
		qry += " where a.statetag=1 and a.ntid=0 and a.guestid=" + Guestid;
		qry += " and a.accid=" + Accid;
		qry += " and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') ";
		qry += " group by a.noteno";
		qry += " union all";
		qry += " select a.noteno,sum(b.amount) as amount,sum(b.curr) as curr,sum(b.amount*c.retailsale) as retailcurr,max(notedate) as notedate";
		qry += " ,0 as linecurr";
		qry += " from shopsaleh a join shopsalem b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += " join warecode c on b.wareid=c.wareid";
		qry += " where a.statetag=1 and a.guestid=" + Guestid;
		qry += " and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') ";
		qry += " and a.accid=" + Accid;
		qry += " group by a.noteno";
		qry += ")";
		// Table tb=new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		Float retailcurr = Float.parseFloat(tb.getRow(0).get("RETAILCURR").toString());
		Float curr = Float.parseFloat(tb.getRow(0).get("CURR").toString());
		Float linecurr = Float.parseFloat(tb.getRow(0).get("LINECURR").toString());
		Float amount = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
		int xsnum = Integer.parseInt(tb.getRow(0).get("XSNUM").toString());
		Float discount = (float) 0;
		Float price = (float) 0;
		Float xfrate = (float) 0;
		Float zbrate = (float) 0;
		// 平均折扣
		if (retailcurr > 0)
			discount = Func.getRound(curr / retailcurr, 2);// Math.Round((Float)curr / (Float)retailcurr, 2, MidpointRounding.AwayFromZero);

		// 平均单价
		if (amount > 0)
			price = Func.getRound(curr / amount, 2);// Math.Round((Float)curr / (Float)amount, 0, MidpointRounding.AwayFromZero);
		// 消费频次=天数/消费次数
		if (xsnum > 0)
			xfrate = Func.getRound(days / xsnum, 2);// Math.Round((Float)days / (Float)xsnum, 1, MidpointRounding.AwayFromZero);

		// 线上销售占比
		if (curr > 0)
			zbrate = Func.getRound(linecurr / curr, 2);// Math.Round((Float)linecurr / (Float)curr, 4, MidpointRounding.AwayFromZero);

		responsestr += ",\"AMOUNT\":\"" + amount + "\"" //
				+ ",\"CURR\":\"" + curr + "\"" //
				+ ",\"LASTDATE\":\"" + tb.getRow(0).get("LASTDATE").toString() + "\"" //
				+ ",\"XSNUM\":\"" + xsnum + "\"" + ",\"DISCOUNT\":\"" + discount + "\"" //
				+ ",\"PRICE\":\"" + price + "\"" + ",\"XFRATE\":\"" + xfrate + "\"" //
				+ ",\"ZBRATE\":\"" + Func.FormatNumber(zbrate, "##.##%") + "\"" // zbrate.ToString("p")
		;
		errmess = responsestr;
		return 1;
	}

	// 根据会员卡号增加或更新会员信息 用于从excel中导入会员资料
	public int LoadFromExcel() {
		// string wareno=ht["wareno"].ToString().ToUpper();
		Table tb = new Table();
		if (Validdate != null) {
			if (!Func.isValidDate(Validdate)) {
				errmess = "会员有效期不是一个有效值！";
				return 0;
			}
		}
		// long vtid = 0;
		// long houseid = 0;
		// String vipno = "";
		// String birthday = "";
		// String validdate = "";
		String qry1 = "";

		String qry = "declare";// "select wareid from warecode where accid=" + accid + " and wareno='" + wareno + "' and rownum=1";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_count number;";
		qry += "\n   v_vtid number(10);";
		qry += "\n   v_houseid number(10);";
		qry += "\n begin";

		qry += "\n   begin";
		if (Guestid != null && Guestid > 0) {// 货号已存在,更新商品资料

			//			qry1 = "select guestid from guestvip where guestid=" + Guestid + " and accid=" + Accid;
			//			tb = DbHelperSQL.Query(qry1).getTable(1);
			//
			//			if (tb.getRowCount() == 0) {
			//				errmess = "会员id:" + Guestid + " 未找到！";
			//				return 0;
			//			}
			qry += "\n   select guestid into v_count from guestvip where guestid=" + Guestid + " and accid=" + Accid + ";";
			qry += "\n   if v_count=0 then";
			qry += "\n      v_retcs:='0会员id:" + Guestid + "未找到！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";

			// long wareid = long.Parse(DbHelperSQL.RunSql(qry).ToString());
			qry += " update guestvip set lastdate=sysdate,lastop='" + Lastop + "'";
			if (Guestname != null && !Guestname.equals("")) {
				qry += ",guestname='" + Guestname + "'";
				Shortname = PinYin.getPinYinHeadChar(Guestname);
				qry += ",shortname='" + Shortname + "'";

			}
			if (Vipno != null && !Vipno.equals("")) {
				Vipno = Vipno.toUpperCase();

				qry1 = " select count(*) from guestvip where accid=" + Accid + " and statetag=1 and rownum=1 and vipno='" + Vipno + "' and guestid<>'" + Guestid;
				if (DbHelperSQL.GetSingleCount(qry1) > 0) {
					// if (DbHelperSQL.Exists(qry)) {
					errmess = "会员卡号：" + Vipno + " 已存在！";
					return 0;
				}

				qry += ",vipno='" + Vipno + "'";
			}
			if (Sex != null) {
				qry += ",sex='" + Sex + "'";
			}
			if (Mobile != null) {
				qry += ",mobile='" + Mobile + "'";
			}
			if (Idcard != null) {
				qry += ",Idcard='" + Idcard + "'";
			}
			if (Birthday != null && !Birthday.equals("")) {

				if (Func.isValidDate(Birthday)) {
					qry += ",birthday='" + Func.DateToStr(Func.StrToDate(Birthday)) + "'";
				}

			}
			if (Validdate != null) {

				qry += ",validdate=to_date('" + Func.subString(Validdate, 1, 10) + "','yyyy-mm-dd')";

			}
			if (Remark != null) {
				qry += ",remark='" + Remark + "'";
			}
			if (Stature != null) {
				qry += ",stature='" + Stature + "'";
			}
			if (Fitsize != null) {
				qry += ",fitsize='" + Fitsize + "'";
			}

			if (Likesome != null) {
				qry += ",likesome='" + Likesome + "'";
			}
			if (Address != null) {
				qry += ",address='" + Address + "'";
			}
			if (Email != null) {
				qry += ",email='" + Email + "'";
			}
			// 查找会员类型begin
			if (Vtname != null && !Vtname.equals("")) {

				qry1 = " select vtid from guesttype where accid=" + Accid + " and noused=0 and statetag=1 and vtname='" + Vtname + "' and rownum=1";
				tb = DbHelperSQL.Query(qry1).getTable(1);
				if (tb.getRowCount() > 0) {
					Vtid = Long.parseLong(tb.getRow(0).get("VTID").toString());
					qry += ",vtid=" + Vtid;
				} else {
					errmess = "会员类型：" + Vtname + " 未找到！";
					return 0;
				}
			}

			if (Housename != null && !Housename.equals("")) {
				qry1 = " select houseid from warehouse where statetag=1 and accid=" + Accid + " and noused=0 and housename='" + Housename + "' and rownum=1";
				tb = DbHelperSQL.Query(qry1).getTable(1);

				if (tb.getRowCount() > 0) {
					Houseid = Long.parseLong(tb.getRow(0).get("HOUSEID").toString());
					qry += ",houseid=" + Houseid;
				} else {

					errmess = "店铺：" + Housename + " 未找到！";
					return 0;
				}
			}

			qry += " where guestid=" + Guestid + " and accid=" + Accid + ";";

		} else {
			if (Func.isNull(Guestname)) {
				errmess = "会员姓名未输入！";
				return 0;
			}

			String sqlinsert1 = "lastdate,createdate,usetag,statetag,accid,lastop";
			String sqlinsert2 = "sysdate,sysdate,0,1," + Accid + ",'" + Lastop + "'";
			sqlinsert1 += ",guestid";
			sqlinsert2 += ",guestvip_guestid.nextval";
			// 查找会员类型begin
			if (Vtname != null && !Vtname.equals("")) {

				qry1 = " select vtid from guesttype where accid=" + Accid + " and noused=0 and statetag=1 and vtname='" + Vtname + "' and rownum=1";
				tb = DbHelperSQL.Query(qry1).getTable(1);

				if (tb.getRowCount() > 0) {
					Vtid = Long.parseLong(tb.getRow(0).get("VTID").toString());
				} else {

					errmess = "会员类型：" + Vtname + " 未找到！";
					return 0;
				}

			}

			sqlinsert1 += ",vtid";
			sqlinsert2 += "," + Vtid;

			// 查找会员类型end

			// 查找店铺begin
			if (Housename != null && !Housename.equals("")) {
				qry1 = " select houseid from warehouse where accid=" + Accid + " and statetag=1 and noused=0 and housename='" + Housename + "' and rownum=1";
				tb = DbHelperSQL.Query(qry1).getTable(1);

				if (tb.getRowCount() > 0) {
					Houseid = Long.parseLong(tb.getRow(0).get("HOUSEID").toString());
					//					qry1 += ",houseid=" + Houseid;
				} else {

					errmess = "店铺：" + Housename + " 未找到！";
					return 0;
				}
			}
			sqlinsert1 += ",houseid";
			sqlinsert2 += "," + Houseid;
			// 查找店铺end

			if (Guestname != null && !Guestname.equals("")) {
				sqlinsert1 += ",guestname";
				sqlinsert2 += ",'" + Guestname + "'";
				Shortname = PinYin.getPinYinHeadChar(Guestname);
				sqlinsert1 += ",shortname";
				sqlinsert2 += ",'" + Shortname + "'";
			}
			if (Vipno != null && !Vipno.equals("")) {
				Vipno = Vipno.toUpperCase();
				qry += "\n   select count(*) into v_count from guestvip where accid=" + Accid + " and statetag=1 and vipno='" + Vipno + "' and rownum=1;";
				qry += "\n   if v_count>0 then";
				qry += "\n      v_retcs:='0会员卡号：" + Vipno + " 已存在！';";
				qry += "\n      goto exit;";
				qry += "\n   end if;";
				//				qry1 = " select count(*)  from guestvip where accid=" + Accid + " and statetag=1 and vipno='" + Vipno + "' and rownum=1";
				//				if (DbHelperSQL.GetSingleCount(qry1) > 0) {
				//					errmess = "会员卡号：" + Vipno + " 已存在！";
				//					return 0;
				//				}
				sqlinsert1 += ",Vipno";
				sqlinsert2 += ",'" + Vipno + "'";
			}
			if (Sex != null) {
				sqlinsert1 += ",sex";
				sqlinsert2 += ",'" + Sex + "'";
			}
			if (Birthday != null) {

				if (Func.isValidDate(Birthday)) {
					// qry += ",birthday='" + Func.DateToStr(Func.StrToDate(Birthday)) + "'";
					sqlinsert1 += ",birthday";
					sqlinsert2 += ",'" + Func.DateToStr(Func.StrToDate(Birthday)) + "'";
				}

			}
			if (!Func.isNull(Validdate)) {
				sqlinsert1 += ",validdate";
				sqlinsert2 += ",to_date('" + Func.subString(Validdate, 1, 10) + "','yyyy-mm-dd')";
			} else {
				sqlinsert1 += ",validdate";
				sqlinsert2 += ",sysdate+730";
			}

			if (Mobile != null) {
				sqlinsert1 += ",mobile";
				sqlinsert2 += ",'" + Mobile + "'";
			}
			if (Idcard != null) {
				sqlinsert1 += ",Idcard";
				sqlinsert2 += ",'" + Idcard + "'";
			}
			if (Fitsize != null) {
				sqlinsert1 += ",fitsize";
				sqlinsert2 += ",'" + Fitsize + "'";
			}
			if (Remark != null) {
				sqlinsert1 += ",remark";
				sqlinsert2 += ",'" + Remark + "'";
			}
			if (Likesome != null) {
				sqlinsert1 += ",likesome";
				sqlinsert2 += ",'" + Likesome + "'";
			}
			if (Stature != null) {
				sqlinsert1 += ",stature";
				sqlinsert2 += ",'" + Stature + "'";
			}
			if (Address != null) {
				sqlinsert1 += ",address";
				sqlinsert2 += ",'" + Address + "'";
			}
			if (Email != null) {
				sqlinsert1 += ",email";
				sqlinsert2 += ",'" + Email + "'";
			}
			// {
			qry += "\n  insert into guestvip (" + sqlinsert1 + ") \n    values (" + sqlinsert2 + ") ;";
			// }
		}
		qry += "\n    v_retcs:='1操作成功';";
		qry += "\n  exception";
		qry += "\n    when others then ";
		qry += "\n    v_retcs:='0'||substr(sqlerrm,1,190);";
		qry += "\n  end;";
		qry += "\n  <<exit>>";
		qry += "\n  select v_retcs into :retcs from dual;";
		qry += "\n end;";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1).replace("\"", "'");
		return Integer.parseInt(retcs.substring(0, 1));

		//		int ret = DbHelperSQL.ExecuteSql(qry);
		//		if (ret < 0) {
		//			errmess = "操作异常！";//+ db.getErrmess();
		//			return 0;
		//		} else {
		//			errmess = "操作成功！";
		//			return 1;
		//		}
	}

	// 载入会员的初始积分及储值余额 未用
	public int LoadCentorCurr() {
		if (Vipno.length() <= 0) {
			errmess = "会员卡号vipno未传入！";
			return 0;
		}
		if (Balcurr == 0 && Balcent == 0) {
			errmess = "初始积分和储值余额不能都为0！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n      v_guestid number(20);";
		qry += "\n      v_retcs varchar2(200);";
		qry += "\n  begin";
		qry += "\n    begin";
		qry += "\n      select guestid into v_guestid from guestvip where vipno='" + Vipno + "' and rownum=1 and accid=" + Accid + ";";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n      v_retcs:='0会员未找到！' ;";
		qry += "\n      goto exit;";
		qry += "\n    end;";
		if (Balcent != 0) {
			qry += "\n   insert into guestcent (id,guestid,notedate,houseid,cent,fs,ly,remark,operant)";
			qry += "\n   values (guestcent_id.nextval,v_guestid,sysdate,0," + Balcent + ",0,1,'初始增加积分余额','" + Lastop + "');";
		}
		if (Balcurr != 0) {
			qry += "\n   insert into guestcurr (id,guestid,notedate,houseid,curr,fs,ly,remark,operant)";
			qry += "\n   values (guestcurr_id.nextval,v_guestid,sysdate,0," + Balcurr + ",0,1,'初始增加储值余额','" + Lastop + "');";
		}
		qry += "\n   update guestvip set balcent =(select sum(case fs when 0 then cent else -cent end) from guestcent where guestcent.guestid=guestvip.guestid)";
		qry += "\n   where guestid=v_guestid; ";
		qry += "\n   update guestvip set balcurr =(select sum(case fs when 0 then curr else -curr end) from guestcurr where guestcurr.guestid=guestvip.guestid)";
		qry += "\n   where guestid=v_guestid; ";
		qry += "\n   v_retcs:= '1操作成功！' ;";
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

	// 下载
	public int doDown(String downdate)// downdate="",同步所有数据，只同步更新的数据
	{
		StringBuilder sql = new StringBuilder();
		String headsql = "";
		boolean isupdate = false;
		String qry = "select guestid,guestname,vipno,sex,mobile,shortname,birthday,usetag,stature,fitsize,likesome,email"//
				+ ",address,balcent,balcurr,houseid,createdate,vtid,vipid,epid,validdate,statetag,lastop,accid";
		qry += "\n from guestvip where accid=" + Accid;

		if (downdate.length() <= 0) {
			qry += " and statetag=1 ";
			headsql = " insert ";
		} else {
			qry += " and lastdate>=to_date('" + downdate + "','yyyy-mm-dd hh24:mi:ss')";
			isupdate = true; // 更改
			headsql = " replace ";
		}
		Table tb = new Table();
		System.out.println(qry);
		tb = DbHelperSQL.GetTable(qry);

		if (!isupdate)

			sql.append("\n delete from guestvip where accid=" + Accid + ";");

		for (int i = 0; i < tb.getRowCount(); i++) {
			String sql1 = "";// "accid";
			String sql2 = "";// Accid.toString();
			for (int j = 0; j < tb.getColumnCount(); j++) {//
				// if (j > 0) {
				// sql1 += ",";
				// sql2 += ",";
				// }
				String fieldtype = tb.getMeta().get(j + 1).getType();

				String strKey = tb.getRow(i).getColumn(j + 1).getName();//
				String strValue = "";
				if (fieldtype.equals("NUMBER"))
					strValue = tb.getRow(i).get(j).toString();//
				else
					strValue = "'" + tb.getRow(i).get(j).toString() + "'";//
				sql1 += strKey + ",";
				sql2 += strValue + ",";

			}
			sql.append("\n " + headsql + " into guestvip (" + sql1 + " lastdate) values (" + sql2 + " datetime('now','localtime'));");
		}

		String path = Func.getRootPath();
		String filename = "down" + Func.desEncode(Accid + "_" + Func.DateToStr(new Date(), "yyyyMMddHHmmss")).toLowerCase() + ".sql";// "brand.sql";
		String savefile = path + "/temp/" + filename;
		PrintStream printStream;
		try {
			printStream = new PrintStream(new FileOutputStream(savefile));
			printStream.println(sql.toString());
			printStream.close();
			errmess = "/temp/" + filename;
			return 1;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errmess = "操作失败：" + e.getMessage();
			return 0;
		}

	}

}