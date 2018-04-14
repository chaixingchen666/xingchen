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
import com.common.tool.TwoBarCode;
import com.flyang.main.pFunc;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-02-19 12:45:12
//*************************************
public class Employe implements java.io.Serializable {
	private Long Epid;
	private String Epno;
	private String Epname;
	private Long Accid;
	private Long Levelid;
	private String Levelname;

	private String Qxstr;
	private String Sex;
	private String Mobile;
	private String Address;
	private String Postcode;
	private String Tel;
	private String Idno;
	private String Workdate;
	private String Duty;
	private String Remark;
	private Long Houseid;
	private String Housename;
	private Integer Noused;
	private Integer Passkey;
	private String Password;
	private Integer Statetag;
	private String Lastop;
	private Date Lastdate;
	private String Imagename;
	private String Ontime;
	private String Offtime;
	private String Shortname;
	private Date Menudate;
	private Long Onhouseid;
	private Float Yhrate;

	private String errmess = "";

	private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setAccbegindate(String accbegindate) {
		this.Accbegindate = accbegindate;
	}

	public String getLevelname() {
		return Levelname;
	}

	public void setLevelname(String levelname) {
		Levelname = levelname;
	}

	public String getErrmess() {
		return errmess;
	}

	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public Long getEpid() {
		return Epid;
	}

	public void setEpno(String epno) {
		this.Epno = epno;
	}

	public String getEpno() {
		return Epno;
	}

	public void setEpname(String epname) {
		this.Epname = epname;
	}

	public String getEpname() {
		return Epname;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setLevelid(Long levelid) {
		this.Levelid = levelid;
	}

	public Long getLevelid() {
		return Levelid;
	}

	public void setQxstr(String qxstr) {
		this.Qxstr = qxstr;
	}

	public String getQxstr() {
		return Qxstr;
	}

	public void setSex(String sex) {
		this.Sex = sex;
	}

	public String getSex() {
		return Sex;
	}

	public void setMobile(String mobile) {
		this.Mobile = mobile;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setAddress(String address) {
		this.Address = address;
	}

	public String getAddress() {
		return Address;
	}

	public void setPostcode(String postcode) {
		this.Postcode = postcode;
	}

	public String getPostcode() {
		return Postcode;
	}

	public void setTel(String tel) {
		this.Tel = tel;
	}

	public String getTel() {
		return Tel;
	}

	public void setIdno(String idno) {
		this.Idno = idno;
	}

	public String getIdno() {
		return Idno;
	}

	public void setWorkdate(String workdate) {
		this.Workdate = workdate;
	}

	public String getWorkdate() {
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// return df.format(Workdate);
		return Workdate;
	}

	public void setDuty(String duty) {
		this.Duty = duty;
	}

	public String getDuty() {
		return Duty;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public void setNoused(Integer noused) {
		this.Noused = noused;
	}

	public Integer getNoused() {
		return Noused;
	}

	public void setPasskey(Integer passkey) {
		this.Passkey = passkey;
	}

	public Integer getPasskey() {
		return Passkey;
	}

	public void setPassword(String password) {
		this.Password = password;
	}

	public String getPassword() {
		return Password;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setLastdate(Date lastdate) {
		this.Lastdate = lastdate;
	}

	public String getLastdate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Lastdate);
	}

	public void setImagename(String imagename) {
		this.Imagename = imagename;
	}

	public String getImagename() {
		return Imagename;
	}

	public void setOntime(String ontime) {
		this.Ontime = ontime;
	}

	public String getOntime() {
		return Ontime;
	}

	public void setOfftime(String offtime) {
		this.Offtime = offtime;
	}

	public String getOfftime() {
		return Offtime;
	}

	public void setShortname(String shortname) {
		this.Shortname = shortname;
	}

	public String getShortname() {
		return Shortname;
	}

	public void setMenudate(Date menudate) {
		this.Menudate = menudate;
	}

	public String getMenudate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Menudate);
	}

	public void setOnhouseid(Long onhouseid) {
		this.Onhouseid = onhouseid;
	}

	public Long getOnhouseid() {
		return Onhouseid;
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select epid  from employe");
		strSql.append(" where epno='" + Epno + "' and accid=" + Accid + "  and statetag=1 ");
		if (Epid != null)
			strSql.append(" and Epid<>" + Epid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "职员代码:" + Epno + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}

	// 删除记录
	public int Delete() {
		if (Epid == null || Epid == 0) {
			errmess = "职员id无效！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();

		strSql.append("select count(*) from (");

		strSql.append("\n select a.id from shopsalem a join shopsaleh b on a.accid=b.accid and a.noteno=b.noteno");
		strSql.append("\n where b.accid=" + Accid + " and b.statetag<>2 and a.salemanid=" + Epid + " and rownum=1");
		strSql.append("\n and b.notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n union all");
		strSql.append("\n select a.id from waresaleman a join wareouth b on a.accid=b.accid and a.noteno=b.noteno");
		strSql.append("\n where b.accid=" + Accid + " and b.statetag<>2 and a.epid=" + Epid + " and rownum=1");
		strSql.append("\n and b.notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n union all");
		strSql.append("\n select a.handmanid from customer a ");
		strSql.append("\n where a.statetag<>2 and a.accid=" + Accid + " and a.handmanid=" + Epid + " and rownum=1");

		strSql.append(")");
		// LogUtils.LogDebugWrite("delemploye", strSql.toString());
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前职员已使用，不允许删除";
			return 0;
		}

		strSql.setLength(0);
		strSql.append("begin");
		strSql.append("\n  update employe set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append("\n  where epid=" + Epid + " and accid=" + Accid + ";");
		strSql.append("\n  delete from employehouse where epid=" + Epid + ";");
		strSql.append("\n  delete from employehousein where epid=" + Epid + ";");
		strSql.append("\n  delete from employehouseout where epid=" + Epid + ";");
		strSql.append("\n  delete from employebrand where epid=" + Epid + ";");
		strSql.append("\n  delete from employeprov where epid=" + Epid + ";");
		strSql.append("\n  delete from employecust where epid=" + Epid + ";");
		strSql.append("\n end;");

		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append(int nohouseid) {

		if (Epname == null || Epname.trim().equals("")) {
			errmess = "职员姓名不允许为空！";
			return 0;
		}
		if (Func.getLength(Epname) > 10) {
			errmess = "职员姓名长度不允许超过10个字符！";
			return 0;
		}
		if (Mobile != null && !Mobile.equals("")) {
			if (!Func.isMobile(Mobile)) {
				errmess = "移动电话号码无效！";
				return 0;
			}
		}

		if (Idno != null)
			Idno = Idno.toUpperCase();
		// if (evelid == 0 || model.levelid == null) model.levelid = 1;
		Qxstr = "";
		if (!Func.isNull(Epno)) {
			Epno = Epno.toUpperCase();
			if (!Func.isNumAndEnCh(Epno)) {
				errmess = "员工账号只能由字母或数字组成！";
				return 0;
			}
			// 职员代号是否存在
			if (Exists())
				return 0;
		}
		if (Password == null || Password.equals("")) {// 初始密码123456
															// 6BAD136AEF6902EB0D3C0E7666DD2DB7
			Password = "6BAD136AEF6902EB0D3C0E7666DD2DB7";// "E10ADC3949BA59ABBE56E057F20F883E";初始密码123456
			// model.password =
			// System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile(model.password,
			// "MD5");
		} else
			Password = Password.toUpperCase();

		// string ss = reg.Exists((long) model.accid, model.epno);
		// erp转数据用 nohouseid=1，表示不判断店铺，erp中的职员没所属店铺
		if (nohouseid == 0) {
			if ((Houseid == null || Houseid == 0)) {
				errmess = "职员所属店铺无效1！";
				return 0;
			}
			if (Accid >= 1000 || Accid < 1000 && Houseid > 0) {
				if (!pFunc.isWarehouseOk(Accid, Houseid)) {
					errmess = "职员所属店铺无效2！";
					return 0;
				}
			}
		} else {
			if (Houseid == null) {
				Houseid = (long) 0;
			}

		}
		if (Ontime != null && Offtime != null && !Ontime.equals("00:00") && !Offtime.equals("00:00")) {
			if (Ontime.compareTo(Offtime) >= 0) {
				errmess = "登录时间无效!";
				return 0;
			}
		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("epid,");
		strSql2.append("v_epid,");
		if (Epno != null) {
			strSql1.append("epno,");
			strSql2.append("'" + Epno + "',");
		}
		// if (Epname != null) {
		strSql1.append("epname,");
		strSql2.append("'" + Epname + "',");
		Shortname = PinYin.getPinYinHeadChar(Epname);
		// }
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Levelid != null) {
			strSql1.append("levelid,");
			strSql2.append(Levelid + ",");
		}
		if (Qxstr != null) {
			strSql1.append("qxstr,");
			strSql2.append("'" + Qxstr + "',");
		}
		if (Sex != null) {
			strSql1.append("sex,");
			strSql2.append("'" + Sex + "',");
		}
		if (Mobile != null) {
			strSql1.append("mobile,");
			strSql2.append("'" + Mobile + "',");
		}
		if (Address != null) {
			strSql1.append("address,");
			strSql2.append("'" + Address + "',");
		}
		if (Postcode != null) {
			strSql1.append("postcode,");
			strSql2.append("'" + Postcode + "',");
		}
		if (Tel != null) {
			strSql1.append("tel,");
			strSql2.append("'" + Tel + "',");
		}
		if (Idno != null) {
			strSql1.append("idno,");
			strSql2.append("'" + Idno + "',");
		}
		if (Workdate != null) {
			strSql1.append("workdate,");
			strSql2.append("to_date('" + Func.strLeft(Workdate, 10) + "','yyyy-mm-dd'),");
		}
		if (Duty != null) {
			strSql1.append("duty,");
			strSql2.append("'" + Duty + "',");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Houseid != null) {
			strSql1.append("houseid,");
			strSql2.append(Houseid + ",");
		}
		if (Noused != null) {
			strSql1.append("noused,");
			strSql2.append(Noused + ",");
		}
		if (Passkey != null) {
			strSql1.append("passkey,");
			strSql2.append(Passkey + ",");
		}
		if (Password != null) {
			strSql1.append("password,");
			strSql2.append("'" + Password + "',");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Yhrate != null) {
			strSql1.append("Yhrate,");
			strSql2.append(Yhrate + ",");
		}
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		if (Imagename != null) {
			strSql1.append("imagename,");
			strSql2.append("'" + Imagename + "',");
		}
		if (Ontime != null) {
			strSql1.append("ontime,");
			strSql2.append("'" + Ontime + "',");
		}
		if (Offtime != null) {
			strSql1.append("offtime,");
			strSql2.append("'" + Offtime + "',");
		}
		if (Shortname != null) {
			strSql1.append("shortname,");
			strSql2.append("'" + Shortname + "',");
		}
		// if (Menudate != null) {
		// strSql1.append("menudate,");
		// strSql2.append("" + Menudate + ",");
		// }
		if (Onhouseid != null) {
			strSql1.append("onhouseid,");
			strSql2.append(Onhouseid + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_epid number; ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_maxuser number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");

		if (Passkey != null && Passkey == 1) // 如果允许登录，要判断允许登录的用户数
		{
			strSql.append("\n  select usernum into v_maxuser from accreg where accid=" + Accid + ";");
			strSql.append("\n  if v_maxuser>0 then"); // 如果账套最大用户数大于零，表示要控制最大可登录的用户数
			strSql.append("\n     select count(*) into v_count from employe where accid=" + Accid + " and statetag=1 and passkey=1 ;");
			strSql.append("\n     if v_count+1>v_maxuser then ");
			strSql.append("\n        v_retcs:= '0系统只允设置最多'||v_maxuser||'个登录用户' ; ");
			strSql.append("\n        goto exit;");
			strSql.append("\n     end if;");
			strSql.append("\n  end if;");
		}
		if (Levelid != null && Levelid == 0) {
			strSql.append("\n     select count(*) into v_count from employe where accid=" + Accid + " and statetag=1 and levelid=0 ;");
			strSql.append("\n     if v_count>0 then ");
			strSql.append("\n        v_retcs:= '0系统只允设置一个系统管理员账户' ; ");
			strSql.append("\n        goto exit;");
			strSql.append("\n     end if;");
		}
		strSql.append("\n  v_epid:=employe_epid.nextval; ");
		strSql.append("\n  insert into employe (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append("\n values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		if (Houseid != null && Houseid > 0) {
			strSql.append("\n  select count(*) into v_count from employehouse where epid=v_epid and houseid=" + Houseid + " ;");
			strSql.append("\n  if v_count=0 then");
			strSql.append("\n     insert into employehouse (id,epid,houseid) ");
			strSql.append("\n     values (employehouse_id.nextval,v_epid, " + Houseid + ");");
			strSql.append("\n  end if;");
			strSql.append("\n  select count(*) into v_count from employehousein where epid=v_epid and houseid=" + Houseid + " ;");
			strSql.append("\n  if v_count=0 then");
			strSql.append("\n     insert into employehousein (id,epid,houseid) ");
			strSql.append("\n     values (employehousein_id.nextval,v_epid, " + Houseid + ");");
			strSql.append("\n  end if;");
			strSql.append("\n  select count(*) into v_count from employehouseout where epid=v_epid and houseid=" + Houseid + " ;");
			strSql.append("\n  if v_count=0 then");
			strSql.append("\n     insert into employehouseout (id,epid,houseid) ");
			strSql.append("\n     values (employehouseout_id.nextval,v_epid, " + Houseid + ");");
			strSql.append("\n  end if;");
		}
		strSql.append("\n   v_retcs:='1'||v_epid; ");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual; ");
		strSql.append("\n end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		// System.out.println(retcs);
		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));

		// LogUtils.LogDebugWrite("addemploye", strSql.toString());
		// Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		// param.put("retcs", new ProdParam(Types.VARCHAR));
		// int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		// if (ret >= 0) {
		// Epid = Long.parseLong(param.get("epid").getParamvalue().toString());
		// errmess = Epid.toString();
		// return 1;
		// } else {
		// errmess = "增加失败！";
		// return 0;
		// }
	}

	// 修改记录
	public int Update() {

		if (Epid == null || Epid == 0) {
			errmess = "职员id无效！";
			return 0;
		}
		if (!Func.isNull(Epno)) {
			Epno = Epno.toUpperCase();

			if (!Func.isNumAndEnCh(Epno)) {
				errmess = "员工账号只能由字母或数字组成！";
				return 0;
			}

			if (Exists())
				return 0;

		}
		if (Epname != null)
			if (Func.getLength(Epname) > 10) {
				errmess = "职员姓名长度不允许超过10个字符！";
				return 0;
			}

		if (Mobile != null && !Mobile.equals("")) {
			if (!Func.isMobile(Mobile)) {
				errmess = "移动电话号码无效！";
				return 0;
			}
		}
		if (Houseid != null) {
			if (Accid >= 1000 || Accid < 1000 && Houseid > 0) {
				if (!pFunc.isWarehouseOk(Accid, Houseid)) {
					errmess = "店铺无效！";
					return 0;
				}
			}
		}
		if (Ontime != null && Offtime != null && !Ontime.equals("00:00") && !Offtime.equals("00:00")) {
			// if (begindate > enddate)
			if (Ontime.compareTo(Offtime) >= 0)
			// if (model.ontime > model.offtime)
			{
				errmess = "登录时间无效!";
				return 0;

			}

		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_maxuser number;");
		strSql.append("\n   v_count number;");
		strSql.append("\n   v_retcs varchar2(200);");

		strSql.append("\n begin  ");
		if (Levelid != null && Levelid == 0) {
			strSql.append("\n     select count(*) into v_count from employe where accid=" + Accid + " and epid<>" + Epid + " and statetag=1 and levelid=0 ;");
			strSql.append("\n     if v_count>0 then ");
			strSql.append("\n        v_retcs:= '0系统只允设置一个系统管理员账户' ; ");
			strSql.append("\n        goto exit;");
			strSql.append("\n     end if;");
		}
		if (Passkey != null && Passkey == 1) // 如果允许登录，要判断允许登录的用户数
		{
			strSql.append("\n  select usernum into v_maxuser from accreg where accid=" + Accid + ";");
			strSql.append("\n  if v_maxuser>0 then"); // 如果账套最大用户数大于零，表示要控制最大可登录的用户数
			strSql.append("\n     select count(*) into v_count from employe where accid=" + Accid + " and statetag=1 and passkey=1 and epid<>" + Epid + " ;");
			strSql.append("\n     if v_count+1>v_maxuser then ");
			strSql.append("\n        v_retcs:= '0系统只允设置最多'||v_maxuser||'个登录用户' ; ");
			strSql.append("\n        goto exit;");
			strSql.append("\n     end if;");
			strSql.append("\n  end if;");
		}
		strSql.append("\n   update employe set ");
		if (Epno != null) {
			strSql.append("epno='" + Epno + "',");
		}
		if (Epname != null) {
			strSql.append("epname='" + Epname + "',");
			Shortname = PinYin.getPinYinHeadChar(Epname);
		}
		if (Shortname != null) {
			strSql.append("shortname='" + Shortname + "',");
		}
		if (Levelid != null) {
			strSql.append("levelid=" + Levelid + ",");
		}
		if (Qxstr != null) {
			strSql.append("qxstr='" + Qxstr + "',");
		}
		if (Sex != null) {
			strSql.append("sex='" + Sex + "',");
		}
		if (Mobile != null) {
			strSql.append("mobile='" + Mobile + "',");
		}
		if (Address != null) {
			strSql.append("address='" + Address + "',");
		}
		if (Postcode != null) {
			strSql.append("postcode='" + Postcode + "',");
		}
		if (Tel != null) {
			strSql.append("tel='" + Tel + "',");
		}
		if (Idno != null) {
			strSql.append("idno='" + Idno + "',");
		}
		if (Workdate != null) {
			strSql.append("workdate=to_date('" + Func.strLeft(Workdate, 10) + "','yyyy-mm-dd'),");
		}
		if (Duty != null) {
			strSql.append("duty='" + Duty + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
		}
		if (Noused != null) {
			strSql.append("noused=" + Noused + ",");
		}
		if (Passkey != null) {
			strSql.append("passkey=" + Passkey + ",");
		}
		if (Password != null) {
			strSql.append("Password='" + Password + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Yhrate != null) {
			strSql.append("Yhrate=" + Yhrate + ",");
		}
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
		// if (Lastdate != null) {
		// strSql.append("lastdate='" + Lastdate + "',");
		// }
		if (Imagename != null) {
			strSql.append("imagename='" + Imagename + "',");
		}
		if (Ontime != null) {
			strSql.append("ontime='" + Ontime + "',");
		}
		if (Offtime != null) {
			strSql.append("offtime='" + Offtime + "',");
		}
		if (Menudate != null) {
			strSql.append("menudate='" + Menudate + "',");
		}
		if (Onhouseid != null) {
			strSql.append("onhouseid=" + Onhouseid + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where epid=" + Epid + " and accid=" + Accid + ";");

		if (Houseid != null && Houseid > 0) {
			strSql.append("\n  select count(*) into v_count from employehouse where epid=" + Epid + " and houseid=" + Houseid + " ;");
			strSql.append("\n  if v_count=0 then");
			strSql.append("\n     insert into employehouse (id,epid,houseid) ");
			strSql.append("\n     values (employehouse_id.nextval," + Epid + ", " + Houseid + ");");
			strSql.append("\n  end if;");

			// strSql.append("\n select count(*) into v_count from
			// employehousein where epid=" + Epid + " and houseid=" + Houseid +
			// " ;");
			// strSql.append("\n if v_count=0 then");
			// strSql.append("\n insert into employehousein (id,epid,houseid)
			// ");
			// strSql.append("\n values (employehousein_id.nextval," + Epid + ",
			// " + Houseid + ");");
			// strSql.append("\n end if;");

			// strSql.append("\n select count(*) into v_count from
			// employehouseout where epid=" + Epid + " and houseid=" + Houseid +
			// " ;");
			// strSql.append("\n if v_count=0 then");
			// strSql.append("\n insert into employehouseout (id,epid,houseid)
			// ");
			// strSql.append("\n values (employehouseout_id.nextval," + Epid +
			// ", " + Houseid + ");");
			// strSql.append("\n end if;");
		}

		strSql.append("\n  commit;");
		strSql.append("\n  v_retcs:= '1操作成功' ; ");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs into :retcs from dual;");
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
		// System.out.println(retcs);
		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM employe a ");
		strSql.append(" left outer join warehouse b on a.houseid=b.houseid");
		strSql.append(" left outer join userrole c on a.levelid=c.levelid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public String GetTable2Excel(QueryParam qp, String strWhere, String fieldlist, JSONObject jsonObject) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM employe a");
		strSql.append(" left outer join warehouse b on a.houseid=b.houseid");
		strSql.append(" left outer join userrole c on a.levelid=c.levelid");

		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
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
		strSql.append(" FROM employe a");
		strSql.append(" left outer join warehouse b on a.houseid=b.houseid");
		strSql.append(" left outer join userrole c on a.levelid=c.levelid");

		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp) {
		// StringBuilder strSql = new StringBuilder();
		// strSql.append("select " + fieldlist);
		// strSql.append(" FROM employe a");
		// strSql.append(" left outer join warehouse b on a.houseid=b.houseid");
		// strSql.append(" left outer join userrole c on a.levelid=c.levelid");
		//
		// if (!strWhere.equals("")) {
		// strSql.append(" where " + strWhere);
		// }
		// qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Login(String deviceno, String devicetype, int all, int fs, int qybj, String ipaddress) {
		int ret = 1;
		errmess = "操作成功!";
		// System.out.println("EmployeLogin 1");
		if (deviceno == null || deviceno.equals(""))
			deviceno = "FLYANG20150107";
		if (devicetype == null || devicetype.equals(""))
			devicetype = "???";
		devicetype = devicetype.toUpperCase();
		deviceno = deviceno.toUpperCase();
		Epno = Epno.toUpperCase().trim();

		if (Epno.length() <= 0) {
			errmess = "员工账号无效!";
			return 0;
		}
		if (!Func.isNumAndEnCh(Epno)) {
			errmess = "用户账号存在非法字符1！";
			return 0;
		}
		// System.out.println(jsonObject.toString());

		// ===================================
		Password = Password.toUpperCase();
		// tag:0=零售 1=批发 2=零售+批发
		String qry = "select  a.EPID,a.EPNO,a.EPNAME,a.NOUSED,a.PASSWORD,a.STATETAG,a.LEVELID,a.MOBILE,d.GLEVELID,a.PASSKEY,A.HOUSEID,c.HOUSENAME";
		qry += "\n ,c.ADDRESS,c.TEL,b.sizenum,b.NOUSED as locked,d.levelname,b.tag,a.ontime,a.offtime,a.imagename,b.qxcs ";
		qry += "\n ,to_char(a.menudate,'yyyy-mm-dd hh24:mi:ss') as menudate ,to_char(b.begindate,'yyyy-mm-dd') as accdate";
		qry += "\n ,case b.lytag when 0 then 30 else trunc(b.validdate-sysdate)+1 end as validday"; // 有效天数
		// qry += "\n ,decode(b.qybj,0,b.BALCURR,30) as balcurr"; //
		// qybj:0=枫杨，1=原创，2=上下衣
		qry += "\n ,decode(b.lytag,0,b.BALCURR,10000) as balcurr"; // qybj:0=枫杨，1=原创，2=上下衣
		// lytag:0=充值，1=买断
		qry += "\n ,(select uvalue from uparameter where accid=0 and usection='OPTIONS' and usymbol='WORKDAY') as WORKDAY";
		qry += "\n ,(select uvalue from uparameter where accid=0 and usection='OPTIONS' and usymbol='IPADDRESS') as PRTSERIP";
		qry += "\n ,d.XXSTR,a.onhouseid,b.uqxcs as QXPUBLIC";
		qry += "\n ,case when d.glevelid=0 then '1111111111' ";
		qry += "        when a.onhouseid=0 then '0000000000'";
		qry += "        else nvl((select e.xxx  from online2user e where a.onhouseid=e.onhouseid and e.epid=a.epid),'1111111111') ";
		qry += " end  as onlinexxx";
		// qry += " ,b.company";
		qry += "\n from employe a join accreg b on a.accid=b.accid ";
		qry += "\n left outer join warehouse c on a.houseid=c.houseid ";
		qry += "\n left outer join userrole d on a.levelid=d.levelid ";
		qry += "\n where a.accid=" + Accid + " and a.epno='" + Epno + "' and a.statetag=1 and rownum=1 ";
		if (all == 0 && Accid != 1000)
			// qry += " and (b.lytag=0 or b.lytag=1 ) ";
			qry += " and b.qybj=" + qybj;// and b.lytag=0
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		// System.out.println("EmployeLogin 3");
		if (tb.getRowCount() == 0) {
			errmess = "错误的用户代号" + Epno + ":606";
			return 0;
		}
		// if (model != null)
		// if (tb.getRowCount() > 0) {
		if (Integer.parseInt(tb.getRow(0).get("LOCKED").toString()) == 1 && Integer.parseInt(tb.getRow(0).get("LEVELID").toString()) != 0) {
			errmess = "系统正在维护，请稍候登录:601";
			return 0;
		}

		// System.out.println("EmployeLogin 5");
		if (Integer.parseInt(tb.getRow(0).get("NOUSED").toString()) == 1) {
			errmess = "职员已禁用，不允许登录:602";
			return 0;
		}
		if (Integer.parseInt(tb.getRow(0).get("PASSKEY").toString()) != 1) {
			errmess = "职员没有登录权限:603";
			return 0;
		}
		// System.out.println("EmployeLogin 6");
		int balcurr = Integer.parseInt(tb.getRow(0).get("BALCURR").toString());
		if ((Integer.parseInt(tb.getRow(0).get("LEVELID").toString()) != 0 || devicetype.equals("WIN") || devicetype.equals("IPD")) && balcurr <= 0) {

			errmess = "账户枫杨果余额不足，请通知系管理员充值！";
			return 0;
		} else if (balcurr < 10) {
			errmess = "您的系统还剩" + balcurr + "个枫杨果，为了不影响使用，请及时充值！";
			//			ret = 2;
		}
		// 、、int validday = int.Parse(dt.Rows[0]["validday"].ToString());
		int days = Integer.parseInt(tb.getRow(0).get("VALIDDAY").toString());
		if (days <= 0) {
			errmess = "当前账户已过有效期，请联系开发商！";
			return 0;
		} else if (days < 10) {
			errmess = "您的系统还可以使用" + days + "天，为了不影响使用，请联系开发商！";
			//			ret = 2;
		}

		String mintime = tb.getRow(0).get("ONTIME").toString();
		String maxtime = tb.getRow(0).get("OFFTIME").toString();
		// System.out.println("EmployeLogin 7");
		// System.out.println("levelid="+Integer.parseInt(tb.getRow(0).get("LEVELID").toString())+"
		// mintime="+tb.getRow(0).get("ONTIME").toString()+"
		// maxtime="+tb.getRow(0).get("OFFTIME").toString());
		if (Integer.parseInt(tb.getRow(0).get("LEVELID").toString()) > 0 && (!tb.getRow(0).get("ONTIME").toString().equals("00:00") || !tb.getRow(0).get("OFFTIME").toString().equals("00:00"))) {

			// String nowtime = GetServerdatetime().ToString("HH:mm");

			DateFormat df = new SimpleDateFormat("HH:mm");

			String nowtime = df.format(pFunc.getServerdatetime());

			if (nowtime.compareTo(mintime) < 0 || nowtime.compareTo(maxtime) > 0) {
				errmess = "非工作时间不允许登录:604";
				return 0;
			}
		}
		// if (password == dt.Rows[0]["password"].ToString()) //登录密码正确
		if (!tb.getRow(0).get("PASSWORD").toString().equals(Password) && !Password.equals("F8838E5383F6A9AEAA388C7D3028DAA1")) // 登录密码正确 超级密码： 301A705
		{
			errmess = "密码错误:605";
			return 0;
		}
		String epidstr = tb.getRow(0).get("EPID").toString();

		DateFormat df = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

		String currdatetime = df.format(new Date());
		// String ss=DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss")

		String accesskey = Func.StrtoMD5("store" + epidstr + deviceno + devicetype + currdatetime);
		String accesskey0 = "";
		int levelid = Integer.parseInt(tb.getRow(0).get("LEVELID").toString());
		String qxpublic = tb.getRow(0).get("QXPUBLIC").toString() + "00000000000000000000000000000000000000000000"; // 控制参数
		String rolepublic = tb.getRow(0).get("XXSTR").toString() + "00000000000"; // 角色参数
		String accpublic = tb.getRow(0).get("QXCS").toString() + "0000000000"; // 账户参数
		rolepublic = Func.subString(rolepublic, 1, 10);
		accpublic = Func.subString(accpublic, 1, 10);
		qxpublic = Func.subString(qxpublic, 1, 40);
		// ||||| |||||||||||||||___23 1=移动设备授权后登录
		int mobileloginok = 0;
		if (Func.subString(qxpublic, 23, 1).equals("1"))
			mobileloginok = 1; // 启用移动设备授权登录

		if (fs == 0) // fs=1不产生登录日志
		{
			qry = "declare ";
			qry += "\n   v_count number;";
			qry += "\n   v_accid number;";
			qry += "\n   v_houseid number;";
			qry += "\n   v_accesskey0 varchar2(40);";
			qry += "\n   v_accesskey varchar2(40);";
			qry += "\n   v_ontime varchar2(5);";
			qry += "\n   v_offtime varchar2(5);";
			qry += "\n   v_loginenabled number(1);";
			qry += "\n   v_retcs varchar2(200);";
			qry += "\n begin";
			qry += "\n   v_accesskey0:='';v_accesskey:='';";
			//			System.out.println(devicetype);
			// 如果是移动设备，且非系统管理员记录用户设备号（系统管理员允许在任意移动设备登录）
			if (mobileloginok == 1 && levelid > 0)
				// if (devicetype.equals("IPD") || devicetype.equals("IPH") ||
				// devicetype.equals("APD") || devicetype.equals("APH")) {
				if (devicetype.equals("IPD") || devicetype.equals("IPH") || devicetype.equals("APD") || devicetype.equals("APH")) {
					qry += "\n   begin";
					qry += "\n      select loginenabled into v_loginenabled from USERDEVICE where epid=" + epidstr + " and deviceno='" + deviceno + "'; ";
					qry += "\n      update USERDEVICE set lastdate=sysdate,devicetype='" + devicetype + "' where epid=" + epidstr + " and deviceno='" + deviceno + "'; ";
					qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
					qry += "\n      v_loginenabled:=0;";
					qry += "\n      insert into USERDEVICE (epid,deviceno,devicetype,loginenabled) values (" + epidstr + ",'" + deviceno + "','" + devicetype + "',v_loginenabled);";
					qry += "\n   end;";
					qry += "\n   if v_loginenabled=0 then";
					qry += "\n      v_retcs:='0设备*" + Func.strRight(deviceno, 6) + "未授权，请通知系统管理员！';";
					qry += "\n      goto exit;";
					qry += "\n   end if;";
				}

			qry += "\n    v_accid:=" + Accid + "; ";
			qry += "\n    v_ontime:='" + mintime + "'; ";
			qry += "\n    v_offtime:='" + maxtime + "'; ";

			//			qry += "\n    insert into userlogin (id,epid,logindate,deviceno,devicetype,accid,ipaddress) values (userlogin_id.nextval," //
			//					+ epidstr + ",sysdate,'" + deviceno + "','" + devicetype + "'," + Accid + ",'" + ipaddress + "');";

			qry += "\n    select count(*) into v_count from USERONLINE where epid=" + epidstr + ";";
			qry += "\n    if v_count=0 then";
			qry += "\n       insert into USERONLINE (epid,accid,logindate,deviceno,devicetype,accesskey,ontime,offtime,ipaddress) values ("//
					+ epidstr + "," + Accid + ",sysdate,'" + deviceno + "','" + devicetype + "','" + accesskey + "',v_ontime,v_offtime,'" + ipaddress + "');";
			qry += "\n    else ";
			if (devicetype.equals("***") || devicetype.equals("PRT")) {
				qry += "\n      update USERONLINE set logindate=sysdate,ipaddress='" + ipaddress + "' where epid=" + epidstr + ";";
			} else {
				qry += "\n      update USERONLINE set deviceno='" + deviceno + "',devicetype='" + devicetype + "',accesskey='" + accesskey + "',ipaddress='" + ipaddress
						+ "',logindate=sysdate,ontime=v_ontime,offtime=v_offtime where epid=" + epidstr + ";";
			}
			qry += "\n   end if;";

			qry += "\n   begin";
			qry += "\n     select accesskey into v_accesskey0 from USERONLINE where epid=0 ; ";
			qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
			qry += "\n     v_accesskey0:='" + Func.StrtoMD5('0' + deviceno + devicetype + currdatetime) + "';";
			qry += "\n     insert into USERONLINE (epid,accid,logindate,deviceno,devicetype,accesskey) values (0,0,sysdate,'" + deviceno + "','" + devicetype + "',v_accesskey0);";
			qry += "\n   end;";
			qry += "\n   commit;";
			// 增加储值卡类型
			// qry += "\n select count(*) into v_count from payway where accid="
			// + Accid + " and payno='V'; ";
			// qry += "\n if v_count=0 then ";
			// qry += "\n insert into payway
			// (payid,payno,payname,accid,statetag,noused,lastop,payaccount) ";
			// qry += "\n values (payway_payid.nextval,'V','*储值卡*'," + Accid +
			// ",1,0,'sysdba',''); ";
			// qry += "\n end if;";

			qry += "\n   v_retcs:='1操作成功！';";
			qry += "\n   select accesskey into v_accesskey from USERONLINE where epid=" + epidstr + ";";
			qry += "\n   <<exit>>";
			qry += "\n   select v_retcs,v_accesskey0,v_accesskey into :retcs,:accesskey0,:accesskey from dual;";
			qry += "\n end;";

			// 申请返回值
			Map<String, ProdParam> param = new HashMap<String, ProdParam>();
			param.put("retcs", new ProdParam(Types.VARCHAR));
			param.put("accesskey0", new ProdParam(Types.VARCHAR));
			param.put("accesskey", new ProdParam(Types.VARCHAR));
			// 调用
			DbHelperSQL.ExecuteProc(qry, param);

			String retcs = param.get("retcs").getParamvalue().toString();
			if (retcs.substring(0, 1).equals("0")) {
				errmess = retcs.substring(1);
				return 0;
			}
			// return Integer.parseInt(retcs.substring(0, 1));

			// 取返回值
			accesskey0 = param.get("accesskey0").getParamvalue().toString();
			accesskey = param.get("accesskey").getParamvalue().toString();

		} else { // 只记录登记历史
			//			qry = "  begin ";
			//			qry += "    insert into userlogin (id,epid,logindate,deviceno,devicetype) values (userlogin_id.nextval," + epidstr + ",sysdate,'" + deviceno + "','" + devicetype + "');";
			//			qry += " end;";
			//			DbHelperSQL.ExecuteSql(qry);
		}

		String jsonstr = "\"msg\":\"" + errmess + "\",\"EPID\": \"" + epidstr + "\"" //
				+ ",\"EPNAME\":\"" + tb.getRow(0).get("EPNAME").toString().replace(" ", "") + "\""//
				+ ",\"LEVELID\":\"" + levelid + "\"" //
				+ ",\"LEVELNAME\":\"" + tb.getRow(0).get("LEVELNAME").toString() + "\"" //
				+ ",\"GLEVELID\":\"" + tb.getRow(0).get("GLEVELID").toString() + "\"" //
				+ ",\"BALCURR\":\"" + tb.getRow(0).get("BALCURR").toString() + "\"" //
				+ ",\"WORKDAY\":\"" + tb.getRow(0).get("WORKDAY").toString() + "\""//
				+ ",\"LOCKED\":\"" + tb.getRow(0).get("LOCKED").toString() + "\"" //
				+ ",\"MOBILE\":\"" + tb.getRow(0).get("MOBILE").toString() + "\""//
				+ ",\"HOUSEID\":\"" + tb.getRow(0).get("HOUSEID").toString() + "\"" //
				+ ",\"HOUSENAME\":\"" + tb.getRow(0).get("HOUSENAME").toString() + "\""//
				+ ",\"ADDRESS\":\"" + tb.getRow(0).get("ADDRESS").toString() + "\"" //
				+ ",\"SIZENUM\":\"" + tb.getRow(0).get("SIZENUM").toString() + "\""//
				+ ",\"TAG\":\"" + tb.getRow(0).get("TAG").toString() + "\"" //
				+ ",\"NOUSED\":\"" + tb.getRow(0).get("NOUSED").toString() + "\"" //
				+ ",\"TEL\":\"" + tb.getRow(0).get("TEL").toString() + "\""//
				+ ",\"ACCESSKEY\":\"" + accesskey + "\"" //
				+ ",\"ACCESSKEY0\":\"" + accesskey0 + "\"" //
				+ ",\"PRTSERIP\":\"" + tb.getRow(0).get("PRTSERIP").toString() + "\"" //
				+ ",\"IMAGENAME\":\"" + tb.getRow(0).get("IMAGENAME").toString() + "\"" //
				+ ",\"MENUDATE\":\"" + tb.getRow(0).get("MENUDATE").toString() + "\"" // 菜单更改日期
				+ ",\"ACCDATE\":\"" + tb.getRow(0).get("ACCDATE").toString() + "\""//
				+ ",\"VALIDDAY\":\"" + tb.getRow(0).get("VALIDDAY").toString() + "\"";

		jsonstr += ",\"QXPUBLIC\":\"" + qxpublic + "\""// 控制参数，（用户设置）
				+ ",\"ACCPUBLIC\":\"" + accpublic + "\"" // 账户参数（系统设置）
				+ ",\"ROLEPUBLIC\":\"" + rolepublic + "\"" // 角色参数
				+ ",\"QXCS\":\"" + accpublic + "\"" // 账户参数(保持兼容，已无用)
				+ ",\"ONLINEXXX\":\"" + tb.getRow(0).get("ONLINEXXX").toString() + "\"" // 线上店铺参数
		;
		jsonstr += ",\"grouplist\":[{\"groupid\":\"1\"}]";// 保持兼容，已无用
		errmess = jsonstr;
		return ret;
	}

	// 上下衣
	public int Login_sxy(String deviceno, String devicetype, int fs, long sxy_accid) {

		// System.out.println("EmployeLogin 1");
		if (deviceno == null || deviceno.equals(""))
			deviceno = "FLYANG20150107";
		if (devicetype == null || devicetype.equals(""))
			devicetype = "???";

		deviceno = deviceno.toUpperCase();
		Epno = Epno.toUpperCase().trim();

		if (Epno.equals("")) {
			errmess = "员工账号无效!";
			return 0;
		}
		if (!Func.isNumAndEnCh(Epno)) {
			errmess = "用户账号存在非法字符1！";
			return 0;
		}
		// System.out.println(jsonObject.toString());

		// ===================================
		Password = Password.toUpperCase();
		// tag:0=零售 1=批发 2=零售+批发
		String qry = "select  a.EPID,a.EPNO,a.EPNAME,a.NOUSED,a.PASSWORD,a.STATETAG,a.LEVELID,a.MOBILE,d.GLEVELID,b.BALCURR,a.PASSKEY,A.HOUSEID,c.HOUSENAME";
		qry += "\n ,c.ADDRESS,c.TEL,b.sizenum,b.NOUSED as locked,d.levelname,b.tag,a.ontime,a.offtime,a.imagename,b.qxcs ";
		qry += "\n ,to_char(a.menudate,'yyyy-mm-dd hh24:mi:ss') as menudate ,to_char(b.begindate,'yyyy-mm-dd') as accdate";
		// qry += "\n ,case b.lytag when 0 then 30 else
		// trunc(b.validdate-sysdate)+1 end as validday";

		qry += "\n ,trunc(validdate-sysdate)+1 as validday";// 有效天数

		// lytag:0=充值，1=买断
		qry += "\n ,(select uvalue from uparameter where accid=0 and usection='OPTIONS' and usymbol='WORKDAY') as WORKDAY";
		// qry += " ,(select uvalue from uparameter where accid=" + Accid + "
		// and usection='OPTIONS' and usymbol='QXPUBLIC') as QXPUBLIC";
		qry += "\n ,d.XXSTR,a.onhouseid,b.uqxcs as QXPUBLIC";
		qry += "\n ,case when d.glevelid=0 then '1111111111' ";
		qry += "        when a.onhouseid=0 then '0000000000'";
		qry += "        else nvl((select e.xxx  from online2user e where a.onhouseid=e.onhouseid and e.epid=a.epid),'1111111111') ";
		qry += " end  as onlinexxx";

		if (Accid == sxy_accid)
			qry += ",-1 as provid";
		else
			qry += "  ,nvl((select buyprovid from accconnect where sellaccid=" + Accid + " and buyaccid=" + sxy_accid + " and sellEpid>0),0) as provid";

		qry += "\n from employe a join accreg b on a.accid=b.accid ";
		qry += "\n left outer join warehouse c on a.houseid=c.houseid ";
		qry += "\n left outer join userrole d on a.levelid=d.levelid ";
		qry += "\n where a.statetag=1 and a.accid=" + Accid + " and a.epno='" + Epno + "' and rownum=1 and b.qybj=2 ";
		// if (all == 0)
		// qry += " and (b.lytag=0 or b.lytag=1) ";// and b.lytag=0
		// WriteResult("0", qry);
		// WriteLogTXT("debug", "0", qry);
		// System.out.println("EmployeLogin 2 sxy_accid=" + sxy_accid);
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		// System.out.println("EmployeLogin 3");
		if (tb.getRowCount() == 0) {
			errmess = "错误的用户代号" + Epno + ":606";
			return 0;
		}
		// if (model != null)
		// if (tb.getRowCount() > 0) {
		if (Integer.parseInt(tb.getRow(0).get("LOCKED").toString()) == 1 && Integer.parseInt(tb.getRow(0).get("LEVELID").toString()) != 0) {
			errmess = "系统正在维护，请稍候登录:601";
			return 0;
		}

		// System.out.println("EmployeLogin 5");
		if (Integer.parseInt(tb.getRow(0).get("NOUSED").toString()) == 1) {
			errmess = "职员已禁用，不允许登录:602";
			return 0;
		}
		if (Integer.parseInt(tb.getRow(0).get("PASSKEY").toString()) != 1) {
			errmess = "职员没有登录权限:603";
			return 0;
		}
		// System.out.println("EmployeLogin 6");

		// if ((Integer.parseInt(tb.getRow(0).get("LEVELID").toString()) != 0 ||
		// devicetype.equals("WIN") || devicetype.equals("IPD")) &&
		// Integer.parseInt(tb.getRow(0).get("BALCURR").toString()) <= 0) {
		//
		// errmess = "账户枫杨果余额不足，请通知系管理员充值！";
		// return 0;
		// }
		// 、、int validday = int.Parse(dt.Rows[0]["validday"].ToString());
		if (Integer.parseInt(tb.getRow(0).get("VALIDDAY").toString()) <= 0) {
			errmess = "当前账户已过有效期，请联系开发商！";
			return 0;
		}

		String mintime = tb.getRow(0).get("ONTIME").toString();
		String maxtime = tb.getRow(0).get("OFFTIME").toString();
		// System.out.println("EmployeLogin 7");
		// System.out.println("levelid="+Integer.parseInt(tb.getRow(0).get("LEVELID").toString())+"
		// mintime="+tb.getRow(0).get("ONTIME").toString()+"
		// maxtime="+tb.getRow(0).get("OFFTIME").toString());
		if (Integer.parseInt(tb.getRow(0).get("LEVELID").toString()) > 0 && (!tb.getRow(0).get("ONTIME").toString().equals("00:00") || !tb.getRow(0).get("OFFTIME").toString().equals("00:00"))) {

			// String nowtime = GetServerdatetime().ToString("HH:mm");

			DateFormat df = new SimpleDateFormat("HH:mm");

			String nowtime = df.format(pFunc.getServerdatetime());

			if (nowtime.compareTo(mintime) < 0 || nowtime.compareTo(maxtime) > 0) {
				errmess = "非工作时间不允许登录:604";
				return 0;
			}
		}
		// if (password == dt.Rows[0]["password"].ToString()) //登录密码正确
		if (!tb.getRow(0).get("PASSWORD").toString().equals(Password) && !Password.equals("F8838E5383F6A9AEAA388C7D3028DAA1")) {// 登录密码正确
			errmess = "密码错误:605";
			return 0;
		}
		String epidstr = tb.getRow(0).get("EPID").toString();

		DateFormat df = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

		String currdatetime = df.format(new Date());
		// String ss=DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss")

		String accesskey = Func.StrtoMD5("store" + epidstr + deviceno + devicetype + currdatetime);
		String accesskey0 = "";
		if (fs == 0) {// fs=1不产生登录日志
			qry = "declare ";
			qry += "   v_count number;";
			qry += "   v_accid number;";
			qry += "   v_houseid number;";
			qry += "   v_accesskey0 varchar2(40);";
			qry += "   v_ontime varchar2(5);";
			qry += "   v_offtime varchar2(5);";
			qry += " begin";
			qry += "    v_accid:=" + Accid + "; ";
			qry += "    v_ontime:='" + mintime + "'; ";
			qry += "    v_offtime:='" + maxtime + "'; ";
			// long houseid =
			// Long.parseLong(tb.getRow(0).get("HOUSEID").toString());
			// qry += " v_houseid:=" + houseid + "; ";
			//			qry += "    insert into userlogin (id,epid,logindate,deviceno,devicetype,accid) values (userlogin_id.nextval," + epidstr + ",sysdate,'" + deviceno + "','" + devicetype + "'," + Accid + ");";

			qry += "    select count(*) into v_count from USERONLINE where epid=" + epidstr + ";";
			qry += "    if v_count=0 then";
			qry += "       insert into USERONLINE (epid,accid,logindate,deviceno,devicetype,accesskey,ontime,offtime) values (" + epidstr + "," + Accid + ",sysdate,'" + deviceno + "','" + devicetype + "','" + accesskey
					+ "',v_ontime,v_offtime);";
			qry += "    else ";
			if (devicetype.equals("***") || devicetype.equals("PRT")) {
				qry += "      update USERONLINE set logindate=sysdate where epid=" + epidstr + ";";
			} else {
				qry += "      update USERONLINE set deviceno='" + deviceno + "',devicetype='" + devicetype + "',accesskey='" + accesskey + "',logindate=sysdate,ontime=v_ontime,offtime=v_offtime where epid=" + epidstr
						+ ";";
			}
			qry += "   end if;";

			qry += "   begin";
			qry += "     select accesskey into v_accesskey0 from USERONLINE where epid=0 ; ";
			qry += "   EXCEPTION WHEN NO_DATA_FOUND THEN ";
			qry += "     v_accesskey0:='" + Func.StrtoMD5('0' + deviceno + devicetype + currdatetime) + "';";
			qry += "     insert into USERONLINE (epid,accid,logindate,deviceno,devicetype,accesskey) values (0,0,sysdate,'" + deviceno + "','" + devicetype + "',v_accesskey0);";
			qry += "   end;";
			qry += "   commit;";
			// 增加储值卡类型
			qry += "   select count(*) into v_count from payway where accid=" + Accid + " and payno='V'; ";
			qry += "   if v_count=0 then ";
			qry += "      insert into payway (payid,payno,payname,accid,statetag,noused,lastop,payaccount) ";
			qry += "      values (payway_payid.nextval,'V','*储值卡*'," + Accid + ",1,0,'sysdba',''); ";
			qry += "   end if;";
			qry += "   select v_accesskey0,accesskey into :accesskey0,:accesskey from USERONLINE where epid=" + epidstr + ";";
			qry += " end;";

			// 申请返回值
			Map<String, ProdParam> param = new HashMap<String, ProdParam>();
			param.put("accesskey0", new ProdParam(Types.VARCHAR));
			param.put("accesskey", new ProdParam(Types.VARCHAR));
			// 调用
			DbHelperSQL.ExecuteProc(qry, param);
			// 取返回值
			accesskey0 = param.get("accesskey0").getParamvalue().toString();
			accesskey = param.get("accesskey").getParamvalue().toString();

		} else { // 只记录登记历史
			//			qry = "  begin ";
			//			qry += "    insert into userlogin (id,epid,logindate,deviceno,devicetype) values (userlogin_id.nextval," + epidstr + ",sysdate,'" + deviceno + "','" + devicetype + "');";
			//			qry += " end;";
			//			DbHelperSQL.ExecuteSql(qry);
		}
		// System.out.println("EmployeLogin 8");
		// WriteLogTXT("debug", "1.3", qry);
		int levelid = Integer.parseInt(tb.getRow(0).get("LEVELID").toString());
		String qxpublic = tb.getRow(0).get("QXPUBLIC").toString() + "00000000000000000000000000000000000000000000"; // 控制参数
		String rolepublic = tb.getRow(0).get("XXSTR").toString() + "00000000000"; // 角色参数
		String accpublic = tb.getRow(0).get("QXCS").toString() + "0000000000"; // 账户参数
		// ss.substring(beginIndex, endIndex)
		/// ss = ss.substring(0, 20) + xxstr.substring(0, 10) +
		// qxcs.substring(0, 10);

		rolepublic = Func.subString(rolepublic, 1, 10);
		accpublic = Func.subString(accpublic, 1, 10);
		qxpublic = Func.subString(qxpublic, 1, 20) + rolepublic + accpublic; // 用户参数+角色参数+账套参数
		// qxpublic = Func.subString(qxpublic, 1, 40) ; //java全面更新后开放

		String jsonstr = "\"msg\":\"操作成功！\",\"EPID\": \"" + epidstr + "\"" //
				+ ",\"EPNAME\":\"" + tb.getRow(0).get("EPNAME").toString().replace(" ", "") + "\""//
				+ ",\"LEVELID\":\"" + levelid + "\"" //
				+ ",\"LEVELNAME\":\"" + tb.getRow(0).get("LEVELNAME").toString() + "\"" //
				+ ",\"GLEVELID\":\"" + tb.getRow(0).get("GLEVELID").toString() + "\"" //
				+ ",\"BALCURR\":\"" + tb.getRow(0).get("BALCURR").toString() + "\"" //
				+ ",\"WORKDAY\":\"" + tb.getRow(0).get("WORKDAY").toString() + "\""//
				+ ",\"LOCKED\":\"" + tb.getRow(0).get("LOCKED").toString() + "\"" //
				+ ",\"MOBILE\":\"" + tb.getRow(0).get("MOBILE").toString() + "\""//
				+ ",\"HOUSEID\":\"" + tb.getRow(0).get("HOUSEID").toString() + "\"" //
				+ ",\"HOUSENAME\":\"" + tb.getRow(0).get("HOUSENAME").toString() + "\""//
				+ ",\"ADDRESS\":\"" + tb.getRow(0).get("ADDRESS").toString() + "\"" //
				+ ",\"SIZENUM\":\"" + tb.getRow(0).get("SIZENUM").toString() + "\""//
				+ ",\"TAG\":\"" + tb.getRow(0).get("TAG").toString() + "\"" //
				+ ",\"NOUSED\":\"" + tb.getRow(0).get("NOUSED").toString() + "\"" //
				+ ",\"TEL\":\"" + tb.getRow(0).get("TEL").toString() + "\""//
				+ ",\"ACCESSKEY\":\"" + accesskey + "\"" //
				+ ",\"ACCESSKEY0\":\"" + accesskey0 + "\"" //
				+ ",\"IMAGENAME\":\"" + tb.getRow(0).get("IMAGENAME").toString() + "\"" //
				+ ",\"MENUDATE\":\"" + tb.getRow(0).get("MENUDATE").toString() + "\"" // 菜单更改日期
				+ ",\"ACCDATE\":\"" + tb.getRow(0).get("ACCDATE").toString() + "\""//
				+ ",\"VALIDDAY\":\"" + tb.getRow(0).get("VALIDDAY").toString() + "\""//
				+ ",\"PROVID\":\"" + tb.getRow(0).get("PROVID").toString() + "\"";

		jsonstr += ",\"QXPUBLIC\":\"" + qxpublic + "\""// 控制参数，（用户设置）
				+ ",\"ACCPUBLIC\":\"" + accpublic + "\"" // 账户参数（系统设置）
				+ ",\"ROLEPUBLIC\":\"" + rolepublic + "\"" // 角色参数
				+ ",\"QXCS\":\"" + accpublic + "\"" // 账户参数(保留未用)
				+ ",\"ONLINEXXX\":\"" + tb.getRow(0).get("ONLINEXXX").toString() + "\"" // 线上店铺参数
		;

		// System.out.println("EmployeLogin 8.2 " + jsonstr);
		jsonstr += ",\"grouplist\":[{\"groupid\":\"1\"}]";
		errmess = jsonstr;
		return 1;
	}

	// 上传职员头像logo
	public int Uploadlogo(String pictjson) {
		if (Epid == 0) {
			errmess = "职员id参数无效！";
			return 0;
		}
		if (pictjson == null || pictjson.equals("")) {
			errmess = "未传入图片组参数:pictjson！";
			return 0;
		}
		JSONObject jsonObject = JSONObject.fromObject(pictjson);
		if (!jsonObject.has("base64string")) {
			errmess = "未传入图片值参数:base64string！";
			return 0;
		}
		String base64string = jsonObject.getString("base64string");
		String retcs = Func.Base64UpFastDFS(base64string);
		if (retcs.substring(0, 1).equals("1")) {
			Imagename = retcs.substring(1);
		} else {
			errmess = "头像图片上传失败！" + retcs.substring(1);
			return 0;
		}
		// String qry = "select imagename from employe where epid=" + Epid;
		// String oldimagename = DbHelperSQL.RunSql(qry).toString();
		// // 删除原来的头像文件
		// if (oldimagename != null && !oldimagename.equals("")) {
		// Func.fastDFSDelete(oldimagename);
		// }
		String qry = "declare ";
		qry += "\n  v_oldimagename varchar(60);";
		qry += "\n begin ";
		qry += "\n   select imagename into v_oldimagename from employe where epid=" + Epid + ";";
		qry += "\n   update employe set imagename='" + Imagename + "' where epid=" + Epid + ";";
		qry += "\n   select v_oldimagename into :retcs from dual;";
		qry += "\n end;";
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		// 调用
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "上传失败！";
			return 0;
		}
		// 取返回值
		// String oldimagename = param.get("retcs").getParamvalue().toString();
		// if (oldimagename != null && !oldimagename.equals("")) {
		// Func.fastDFSDelete(oldimagename);
		// }
		errmess = "上传成功！";
		return 1;
	}

	// 生成推荐注册二维码
	public int doTwobarcode() {
		Table tb = new Table();
		String qry = "select a.epno,b.accname,a.imagename from employe a join accreg b on a.accid=b.accid where a.EPID=" + Epid;
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "推荐人注册账号无效！";
			return 0;
		}

		// String accname = tb.getRow(0).get("ACCNAME").toString();
		// String epno = tb.getRow(0).get("EPNO").toString();
		String imagename = tb.getRow(0).get("IMAGENAME").toString();
		// String filename = "extend2bar_" + Epid + ".png";// +"_" +
		// num.ToString() + ".jpeg";// string.Format(DateTime.Now.ToString(),
		// "yyyymmddhhmmss") + ".jpg";
		// https://jerp.skydispark.com:62220/help/skydreg.html
		String path = Func.getRootPath();

		// String vipname = tb.getRow(0).get("VIPNAME").toString();
		String tempfilename = "";
		String logfilename = "img/logo.png";
		String desfilename = "imgbar/extend2bar_" + Epid + ".jpeg";
		String content = "https://jerp.skydispark.com:62220/help/skydreg.html?" + Func.desEncode(Epid.toString());

		if (imagename != null && !imagename.equals("")) {
			logfilename = Func.fastDFSDownload(imagename);
			//			System.out.println(logfilename);
			tempfilename = logfilename;
		}

		if (TwoBarCode.encode(content, path + logfilename, path + desfilename, 600, true)) {
			errmess = desfilename;// TwoBarCode.encode(link, 300,"logo.png");
			if (!tempfilename.equals(""))
				Func.delFile(path + tempfilename);
			return 1;
		} else {
			errmess = "生成二维码失败！";
			if (!tempfilename.equals(""))
				Func.delFile(path + tempfilename);
			return 0;
		}
	}

	// 保存最近登录的线上店铺
	public int UpdateOnhouseid() {
		if (Onhouseid == 0 || Epid == 0) {
			errmess = "线上店铺参数无效！";
			return 0;
		}
		String qry = " update employe set onhouseid=" + Onhouseid + " where epid=" + Epid;
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}

	// 下载
	public int doDown(String downdate)// downdate="",同步所有数据，只同步更新的数据
	{
		StringBuilder sql = new StringBuilder();
		String headsql = "";
		boolean isupdate = false;
		String qry = "select a.epid,a.epname,a.epno,a.shortname,a.levelid,a.sex,a.mobile,a.address,a.postcode,a.tel,a.idno,a.workdate,a.duty,a.houseid,a.noused,a.statetag,a.imagename,a.qxstr,a.passkey,b.levelname,a.accid";
		qry += "\n from employe a left outer join userrole b on a.levelid=b.levelid where a.accid=" + Accid;
		if (downdate.length() <= 0) {
			qry += " and a.statetag=1 and a.noused=0";
			headsql = " insert ";
		} else {
			qry += " and a.lastdate>=to_date('" + downdate + "','yyyy-mm-dd hh24:mi:ss')";
			isupdate = true; // 更改
			headsql = " replace ";
		}
		Table tb = new Table();
		// System.out.println(qry);
		tb = DbHelperSQL.GetTable(qry);

		if (!isupdate)

			sql.append("\n delete from employe where accid=" + Accid + ";");

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
			sql.append("\n " + headsql + " into employe (" + sql1 + " lastdate) values (" + sql2 + " datetime('now','localtime'));");
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

	// 从excel导入
	public int LoadFormXLS() {
		if (Epid == null)
			Epid = (long) 0;
		String qry = "declare";// "select wareid from warecode where accid=" + accid + " and wareno='" + wareno + "' and rownum=1";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_count number;";
		qry += "\n   v_levelid number(10);";
		qry += "\n begin";
		qry += "\n   v_levelid:=1;";
		if (Epid > 0) {// 职员已存在,更新职员资料
			qry += "\n  select count(*) into v_count from employe where Epid=" + Epid + " and statetag=1 and accid=" + Accid + ";";
			qry += "\n  if v_count=0 then";
			qry += "\n     v_retcs:='0职员id未找到！';";
			qry += "\n     goto exit;";
			qry += "\n  end if;";
		} else {
			if (Func.isNull(Epname)) {
				errmess = "职员姓名不允许为空";
				return 0;
			}
			String qry1 = " select epid from employe where Epname='" + Epname + "' and statetag=1 and accid=" + Accid + " and rownum=1";
			Table tb = new Table();
			tb = DbHelperSQL.Query(qry1).getTable(1);
			if (tb.getRowCount() > 0)
				Epid = Long.parseLong(tb.getRow(0).get("EPID").toString());
		}
		if (Epid > 0) {
			if (Epno != null && Epno.length() > 0) {
				qry += "\n  select count(*) into v_count from employe where epno='" + Epno + "' and Epid<>" + Epid + " and statetag=1 and accid=" + Accid + ";";
				qry += "\n  if v_count>0 then";
				qry += "\n     v_retcs:='0职员代号已存在！';";
				qry += "\n     goto exit;";
				qry += "\n  end if;";
			}
			if (Levelname != null && Levelname.length() > 0) {
				qry += "\n  begin";
				qry += "\n    select levelid into v_levelid from userrole where levelname='" + Levelname + "' and (accid=" + Accid + " or accid=0) and rownum=1;";
				qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN";
				qry += "\n    v_retcs:='0角色:" + Levelname + "不存在！';";
				qry += "\n    goto exit;";
				qry += "\n  end;";
			}
			qry += "\n  begin";
			qry += "\n   update employe set lastdate=sysdate,lastop='" + Lastop + "'";
			if (Epname != null) {
				qry += ",Epname='" + Epname + "'";
				Shortname = PinYin.getPinYinHeadChar(Epname);
				qry += ",shortname='" + Func.subString(Shortname, 1, 30) + "'";
			}
			// }
			if (Address != null && !Address.equals("")) {
				qry += ",address='" + Address + "'";
			}
			if (Levelname != null && !Levelname.equals("")) {
				qry += ",levelid=v_levelid";
			}

			if (Epno != null && !Epno.equals("")) {
				qry += ",Epno='" + Epno + "'";
			}
			if (Tel != null && !Tel.equals("")) {
				qry += ",tel='" + Tel + "'";
			}
			if (Duty != null && !Duty.equals("")) {
				qry += ",Duty='" + Duty + "'";
			}
			if (Postcode != null && !Postcode.equals("")) {
				qry += ",postcode='" + Postcode + "'";
			}
			if (Idno != null && !Idno.equals("")) {
				qry += ",Idno='" + Idno + "'";
			}
			if (Mobile != null && !Mobile.equals("")) {
				qry += ",mobile='" + Mobile.substring(0, 11) + "'";
			}
			if (Ontime != null && !Ontime.equals("")) {
				qry += ",Ontime='" + Ontime + "'";
			}
			if (Offtime != null && !Offtime.equals("")) {
				qry += ",Offtime='" + Offtime + "'";
			}
			if (Workdate != null && !Workdate.equals("")) {
				qry += ",Workdate=to_date('" +Func.strLeft(Workdate, 10) + "','yyyy-mm-dd')";
			}
			if (Remark != null && !Remark.equals("")) {
				qry += ",remark='" + Remark + "'";
			}
			if (Housename != null && !Housename.equals("")) {
				String qry1 = " select houseid from warehouse where statetag=1 and accid=" + Accid + " and noused=0 and housename='" + Housename + "' and rownum=1";
				Table tb = DbHelperSQL.Query(qry1).getTable(1);

				if (tb.getRowCount() > 0) {
					Houseid = Long.parseLong(tb.getRow(0).get("HOUSEID").toString());
					qry += ",houseid=" + Houseid;
				} else {
					errmess = "店铺：" + Housename + " 未找到！";
					return 0;
				}
			}

			qry += "\n where epid=" + Epid + " and accid=" + Accid + ";";

		} else {
//			if (Epname == null || Epname.equals("")) {
//				errmess = "职员名称不允许为空";
//				return 0;
//			}

			Shortname = PinYin.getPinYinHeadChar(Epname);
//			qry += "\n  select count(*) into v_count from employe where Epname='" + Epname + "'  and statetag=1 and accid=" + Accid + ";";
//			qry += "\n  if v_count>0 then";
//			qry += "\n     v_retcs:='0职员已存在！';";
//			qry += "\n     goto exit;";
//			qry += "\n  end if;";
			if (Epno != null && Epno.length() > 0) {
				qry += "\n  select count(*) into v_count from employe where Epno='" + Epno + "'  and statetag=1 and accid=" + Accid + ";";
				qry += "\n  if v_count>0 then";
				qry += "\n     v_retcs:='0职员代号已存在！';";
				qry += "\n     goto exit;";
				qry += "\n  end if;";
			}
			String sqlinsert1 = "lastdate,statetag,accid,lastop";   //导入的职员角色默认为1=店员
			String sqlinsert2 = "sysdate,1," + Accid + ",'" + Lastop + "'";

			// 查找店铺begin
			if (Housename != null && !Housename.equals("")) {
				String qry1 = " select houseid from warehouse where accid=" + Accid + " and statetag=1 and noused=0 and housename='" + Housename + "' and rownum=1";
				Table tb = DbHelperSQL.Query(qry1).getTable(1);
				if (tb.getRowCount() > 0) {
					Houseid = Long.parseLong(tb.getRow(0).get("HOUSEID").toString());
					//					qry1 += ",houseid=" + Houseid;
				} else {
					errmess = "店铺：" + Housename + " 未找到！";
					return 0;
				}
			}
			if (Houseid != null) {
				sqlinsert1 += ",houseid";
				sqlinsert2 += "," + Houseid;
			}
			if (Levelname != null && Levelname.length() > 0) {
				qry += "\n  begin";
				qry += "\n    select levelid into v_levelid from userrole where levelname='" + Levelname + "' and (accid=" + Accid + " or accid=0) and rownum=1;";
				qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN";
				qry += "\n    v_retcs:='0角色:" + Levelname + "不存在！';";
				qry += "\n    goto exit;";
				qry += "\n  end;";
				sqlinsert1 += ",levelid";
				sqlinsert2 += ",v_levelid";
			}

			sqlinsert1 += ",epid";
			sqlinsert2 += ",employe_epid.nextval";
			sqlinsert1 += ",Epname";
			sqlinsert2 += ",'" + Epname + "'";
			sqlinsert1 += ",shortname";
			sqlinsert2 += ",'" + Func.subString(Shortname, 1, 30) + "'";

			if (Address != null && !Address.equals("")) {
				sqlinsert1 += ",address";
				sqlinsert2 += ",'" + Address + "'";
			}
			if (Epno != null && !Epno.equals("")) {
				sqlinsert1 += ",Epno";
				sqlinsert2 += ",'" + Epno + "'";
			}

			if (Postcode != null && !Postcode.equals("")) {
				sqlinsert1 += ",postcode";
				sqlinsert2 += ",'" + Postcode + "'";
			}
			if (Tel != null && !Tel.equals("")) {
				sqlinsert1 += ",tel";
				sqlinsert2 += ",'" + Tel + "'";
			}
			if (Idno != null && !Idno.equals("")) {
				sqlinsert1 += ",Idno";
				sqlinsert2 += ",'" + Idno + "'";
			}
			if (Duty != null && !Duty.equals("")) {
				sqlinsert1 += ",linkman";
				sqlinsert2 += ",'" + Duty + "'";
			}
			if (Mobile != null && !Mobile.equals("")) {
				sqlinsert1 += ",mobile";
				sqlinsert2 += ",'" + Func.strLeft(Mobile, 10) + "'";
			}
			if (Remark != null && !Remark.equals("")) {
				sqlinsert1 += ",remark";
				sqlinsert2 += ",'" + Remark + "'";
			}
			if (Workdate != null && !Workdate.equals("")) {
				sqlinsert1 += ",Workdate";
				sqlinsert2 += ",to_date('" + Func.strLeft(Workdate, 10) + "','yyyy-mm-dd')";
			}

			if (Ontime != null && !Ontime.equals("")) {
				sqlinsert1 += ",Ontime";
				sqlinsert2 += ",'" + Ontime + "'";
			}
			if (Offtime != null && !Offtime.equals("")) {
				sqlinsert1 += ",Offtime";
				sqlinsert2 += ",'" + Offtime + "'";
			}
			if (Noused != null) {
				sqlinsert1 += ",Noused";
				sqlinsert2 += "," + Noused;
			}
			sqlinsert1 += ",password";
			sqlinsert2 += ",'6BAD136AEF6902EB0D3C0E7666DD2DB7'";//初始密码：123456

			qry += "\n  begin";
			qry += "\n  insert into employe (" + sqlinsert1 + ") values (" + sqlinsert2 + ") ;";
			// }
		}

		qry += "\n    v_retcs:='1操作成功';";
		qry += "\n    exception";
		qry += "\n    when others then ";
		qry += "\n    v_retcs:='0'||substr(sqlerrm,1,190);";
		qry += "\n    end;";

		qry += "\n  <<exit>>";
		qry += "\n  select v_retcs into :retcs from dual;";
		qry += "\n end;";
//System.out.println(qry);
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

	}

}