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
//  @author:sunhong  @date:2017-02-19 21:03:31
//*************************************
public class Customer implements java.io.Serializable {
	private Long Custid;
	private String Custname;
	private String Shortname;
	private Long Accid;
	private Long Handmanid;
	private String Address;
	private String Postcode;
	private String Tel;
	private String Linkman;
	private String Mobile;
	private String Bankname;
	private String Taxno;
	private String Accountno;
	private String Remark;
	private Integer Noused;
	private Integer Statetag;
	private Integer Pricetype;
	private Float Discount;
	private Long Cust_accid;
	private Float Currbzj;
	private String Lastop;
	private Date Lastdate;
	private Float Creditcurr;
	private Integer Creditok;
	private Long Xsnum;
	private String Areaname;
	private Integer Usedbj;
	private Date Createdate;
	private String Wladd;
	private String Custno;
	private Integer Passkey;
	private String Password;
	private String errmess;
	private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	private Integer Qxbj; //
	private Integer Alluser = 0; //授权所有职员

	public void setQxbj(Integer qxbj) {
		this.Qxbj = qxbj;
	}

	private Long Userid;

	public void setUserid(Long userid) {
		this.Userid = userid;
	}

	public void setCustid(Long custid) {
		this.Custid = custid;
	}

	public Long getCustid() {
		return Custid;
	}

	public void setCustname(String custname) {
		this.Custname = custname;
	}

	public String getCustname() {
		return Custname;
	}

	public void setShortname(String shortname) {
		this.Shortname = shortname;
	}

	public String getShortname() {
		return Shortname;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public Long getHandmanid() {
		return Handmanid;
	}

	public void setHandmanid(Long handmanid) {
		Handmanid = handmanid;
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

	public void setLinkman(String linkman) {
		this.Linkman = linkman;
	}

	public String getLinkman() {
		return Linkman;
	}

	public void setMobile(String mobile) {
		this.Mobile = mobile;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setBankname(String bankname) {
		this.Bankname = bankname;
	}

	public String getBankname() {
		return Bankname;
	}

	public void setTaxno(String taxno) {
		this.Taxno = taxno;
	}

	public String getTaxno() {
		return Taxno;
	}

	public void setAccountno(String accountno) {
		this.Accountno = accountno;
	}

	public String getAccountno() {
		return Accountno;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setNoused(Integer noused) {
		this.Noused = noused;
	}

	public Integer getNoused() {
		return Noused;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setPricetype(Integer pricetype) {
		this.Pricetype = pricetype;
	}

	public Integer getPricetype() {
		return Pricetype;
	}

	public void setDiscount(Float discount) {
		this.Discount = discount;
	}

	public Float getDiscount() {
		return Discount;
	}

	public void setCust_accid(Long cust_accid) {
		this.Cust_accid = cust_accid;
	}

	public Long getCust_accid() {
		return Cust_accid;
	}

	public void setCurrbzj(Float currbzj) {
		this.Currbzj = currbzj;
	}

	public Float getCurrbzj() {
		return Currbzj;
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

	public void setCreditcurr(Float creditcurr) {
		this.Creditcurr = creditcurr;
	}

	public Float getCreditcurr() {
		return Creditcurr;
	}

	public void setCreditok(Integer creditok) {
		this.Creditok = creditok;
	}

	public Integer getCreditok() {
		return Creditok;
	}

	public void setXsnum(Long xsnum) {
		this.Xsnum = xsnum;
	}

	public Long getXsnum() {
		return Xsnum;
	}

	public void setAreaname(String areaname) {
		this.Areaname = areaname;
	}

	public String getAreaname() {
		return Areaname;
	}

	public void setUsedbj(Integer usedbj) {
		this.Usedbj = usedbj;
	}

	public Integer getUsedbj() {
		return Usedbj;
	}

	public void setCreatedate(Date createdate) {
		this.Createdate = createdate;
	}

	public String getCreatedate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Createdate);
	}

	public void setWladd(String wladd) {
		this.Wladd = wladd;
	}

	public String getWladd() {
		return Wladd;
	}

	public void setCustno(String custno) {
		this.Custno = custno;
	}

	public String getCustno() {
		return Custno;
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

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	public void setAccbegindate(String accbegindate) {
		this.Accbegindate = accbegindate;
	}

	// 删除记录
	public int Delete() {
		if (Custid == 0) {
			errmess = "客户id参数无效";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) from (");
		strSql.append("\n  select  id from wareouth where accid=" + Accid + " and custid=" + Custid + " and statetag<>2 and rownum=1");
		strSql.append("\n   and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n   union all");
		strSql.append("\n   select  id from custorderh where accid=" + Accid + " and custid=" + Custid + " and statetag<>2 and rownum=1");
		strSql.append("\n   and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		// strSql.append("\n union all");
		// strSql.append("\n select id from warepeih where custid=" + Custid + " and statetag<>2 and rownum=1");
		// strSql.append("\n and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n   union all");
		strSql.append("\n   select  id from incomecurr where accid=" + Accid + " and custid=" + Custid + " and statetag<>2 and rownum=1");
		strSql.append("\n   and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n   union all");
		strSql.append("\n   select  id from firstincomecurr where accid=" + Accid + " and custid=" + Custid + " and statetag<>2 and rownum=1");
		strSql.append("\n   and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n   union all");
		strSql.append("\n   select  id from incomecost where accid=" + Accid + " and custid=" + Custid + " and statetag<>2 and rownum=1");
		strSql.append("\n   and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append(")");// System.out.println(strSql.toString());
		int num = DbHelperSQL.GetSingleCount(strSql.toString());

		if (num > 0) {
			errmess = "当前客户已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);

		// strSql.append("delete from customer where accid=" + Accid + " and custid=" +Custid);
		strSql.append("declare ");
		strSql.append("\n    v_custname varchar2(200);");
		strSql.append("\n begin ");
		strSql.append("\n    select custname into v_custname from customer where  custid=" + Custid + " and accid=" + Accid + " and statetag=1;");
		strSql.append("\n    update customer set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append("\n    where custid=" + Custid + " and accid=" + Accid + ";");
		strSql.append("\n    insert into LOGRECORD (id,accid,progname,remark,lastop)");
		strSql.append("\n    values (LOGRECORD_id.nextval," + Accid + ",'客户档案','【删除记录】客户id:" + Custid + "; 客户名称:'||v_custname,'" + Lastop + "');");
		strSql.append("\n    commit;");

		strSql.append("\n    EXCEPTION WHEN NO_DATA_FOUND THEN ");
		strSql.append("\n    v_custname:=''; ");
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
	public int Append() {
		if (Custname == null || Custname.equals("")) {
			errmess = "客户名称不允许为空！";
			return 0;
		}
		// if (Exists()) // 判断客户名称是否已存在
		// {
		// // WriteResult(response, 0, dal.getErrmess());
		// return 0;
		//
		// }

		// if (Handmanid != null && Handmanid > 0) {
		// if (Func.isEmployeOk(Accid, Handmanid)) {
		// errmess = "业务员无效！";
		// return 0;
		// }
		// }

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("custid,");
		strSql2.append("v_id,");
		// if (Custid != null) {
		// strSql1.append("custid,");
		// strSql2.append(Custid + ",");
		// }
		// if (Custname != null) {
		strSql1.append("custname,");
		strSql2.append("'" + Custname + "',");
		Shortname = PinYin.getPinYinHeadChar(Custname);
		// }
		// if (Shortname != null) {
		strSql1.append("shortname,");
		strSql2.append("'" + Shortname + "',");
		// }
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
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
		if (Linkman != null) {
			strSql1.append("linkman,");
			strSql2.append("'" + Linkman + "',");
		}
		if (Mobile != null) {
			strSql1.append("mobile,");
			strSql2.append("'" + Mobile + "',");
		}
		if (Bankname != null) {
			strSql1.append("bankname,");
			strSql2.append("'" + Bankname + "',");
		}
		if (Taxno != null) {
			strSql1.append("taxno,");
			strSql2.append("'" + Taxno + "',");
		}
		if (Accountno != null) {
			strSql1.append("accountno,");
			strSql2.append("'" + Accountno + "',");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Noused != null) {
			strSql1.append("noused,");
			strSql2.append(Noused + ",");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Pricetype != null) {
			strSql1.append("pricetype,");
			strSql2.append(Pricetype + ",");
		}
		if (Handmanid != null) {
			strSql1.append("Handmanid,");
			strSql2.append(Handmanid + ",");
		}

		if (Discount != null) {
			strSql1.append("discount,");
			strSql2.append(Discount + ",");
		}
		if (Cust_accid != null) {
			strSql1.append("cust_accid,");
			strSql2.append(Cust_accid + ",");
		}
		if (Currbzj != null) {
			strSql1.append("currbzj,");
			strSql2.append(Currbzj + ",");
		}
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		// if (Lastdate != null) {
		// strSql1.append("lastdate,");
		// strSql2.append("" + Lastdate + ",");
		// }
		if (Creditcurr != null) {
			strSql1.append("creditcurr,");
			strSql2.append(Creditcurr + ",");
		}
		if (Creditok != null) {
			strSql1.append("creditok,");
			strSql2.append(Creditok + ",");
		}
		if (Xsnum != null) {
			strSql1.append("xsnum,");
			strSql2.append(Xsnum + ",");
		}
		if (Areaname != null) {
			strSql1.append("areaname,");
			strSql2.append("'" + Areaname + "',");
		}
		if (Usedbj != null) {
			strSql1.append("usedbj,");
			strSql2.append(Usedbj + ",");
		}
		// if (Createdate != null) {
		strSql1.append("createdate,");
		strSql2.append("sysdate,");
		// }
		if (Wladd != null) {
			strSql1.append("wladd,");
			strSql2.append("'" + Wladd + "',");
		}
		if (Custno != null) {
			strSql1.append("custno,");
			strSql2.append("'" + Custno + "',");
		}
		if (Passkey != null) {
			strSql1.append("passkey,");
			strSql2.append(Passkey + ",");
		}
		if (Password != null) {
			strSql1.append("password,");
			strSql2.append("" + Password + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n    v_id number; ");
		strSql.append("\n    v_count number; ");
		strSql.append("\n    v_retcs varchar2(200); ");
		strSql.append("\n  begin ");
		strSql.append("\n    select count(*) into v_count  from customer");
		strSql.append("\n    where custname='" + Custname + "' and statetag=1 and rownum=1 and accid=" + Accid + ";");
		strSql.append("\n    if v_count>0 then ");
		strSql.append("\n       v_retcs:='0客户已存在！';");
		strSql.append("\n       goto exit;");
		strSql.append("\n    end if;");
		if (Handmanid != null && Handmanid > 0) {
			strSql.append("\n    select count(*) into v_count  from employe");
			strSql.append("\n    where epid=" + Handmanid + " and statetag=1 and accid=" + Accid + ";");
			strSql.append("\n    if v_count=0 then ");
			strSql.append("\n       v_retcs:='0业务主管无效！';");
			strSql.append("\n       goto exit;");
			strSql.append("\n    end if;");
		}
		strSql.append("\n   v_id:=customer_custid.nextval; ");
		strSql.append("\n   insert into customer ( " + strSql1.toString() + ")");
		strSql.append("\n   values ( " + strSql2.toString() + ");");
		strSql.append("\n   v_retcs:='1'||v_id ; ");
		if (Qxbj == 1)// 如果启用了权限控制，增加的客户自动加入权限表
		{
			if (Alluser == 1) {// 自动授权所有用户
				strSql.append("\n   insert into employecust (epid,custid)");
				strSql.append("\n   select epid,v_id from employe b where b.statetag=1 and b.accid= " + Accid + ";");
			} else {
				strSql.append("\n   insert into employecust (epid,custid)");
				strSql.append("\n   values (" + Userid + ",v_id); ");
			}
		}

		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual; ");
		strSql.append("\n  end;");
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

	// 修改记录
	public int Update() {
		if (Custid == null || Custid == 0) {
			errmess = "客户id无效！";
			return 0;
		}
		if (Custname != null && Custname.equals("")) {
			errmess = "客户名称不允许为空！";
			return 0;
		}
		if (Exists()) // 判断客户名称是否已存在
		{
			// WriteResult(response, 0, dal.getErrmess());
			return 0;

		}
		// if (Handmanid != null || Handmanid == 0) {
		// if (!Func.isEmployeOk(Accid, Handmanid)) {
		// errmess = "业务员无效！";
		// return 0;
		// }
		// }
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("  v_count number;");
		strSql.append("  v_retcs varchar2(200);");
		strSql.append("begin ");

		if (Handmanid != null && Handmanid > 0) {
			strSql.append("\n    select count(*) into v_count  from employe");
			strSql.append("\n    where epid=" + Handmanid + " and statetag=1 and accid=" + Accid + ";");
			strSql.append("\n    if v_count=0 then ");
			strSql.append("\n       v_retcs:='0业务主管无效！';");
			strSql.append("\n       goto exit;");
			strSql.append("\n    end if;");
		}

		strSql.append("\n    update customer set ");

		if (Custname != null) {
			strSql.append("custname='" + Custname + "',");
			Shortname = PinYin.getPinYinHeadChar(Custname);
		}
		if (Shortname != null) {
			strSql.append("shortname='" + Shortname + "',");
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
		if (Linkman != null) {
			strSql.append("linkman='" + Linkman + "',");
		}
		if (Mobile != null) {
			strSql.append("mobile='" + Mobile + "',");
		}
		if (Bankname != null) {
			strSql.append("bankname='" + Bankname + "',");
		}
		if (Taxno != null) {
			strSql.append("taxno='" + Taxno + "',");
		}
		if (Accountno != null) {
			strSql.append("accountno='" + Accountno + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Noused != null) {
			strSql.append("noused=" + Noused + ",");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Pricetype != null) {
			strSql.append("pricetype=" + Pricetype + ",");
		}
		if (Discount != null) {
			strSql.append("discount=" + Discount + ",");
		}
		if (Handmanid != null) {
			strSql.append("Handmanid=" + Handmanid + ",");
		}

		if (Cust_accid != null) {
			strSql.append("cust_accid=" + Cust_accid + ",");
		}
		if (Currbzj != null) {
			strSql.append("currbzj=" + Currbzj + ",");
		}
		// if (Lastop != null) {
		// }
		// if (Lastdate != null) {
		// strSql.append("lastdate='" + Lastdate + "',");
		// }
		if (Creditcurr != null) {
			strSql.append("creditcurr=" + Creditcurr + ",");
		}
		if (Creditok != null) {
			strSql.append("creditok=" + Creditok + ",");
		}
		if (Xsnum != null) {
			strSql.append("xsnum=" + Xsnum + ",");
		}
		if (Areaname != null) {
			strSql.append("areaname='" + Areaname + "',");
		}
		if (Usedbj != null) {
			strSql.append("usedbj=" + Usedbj + ",");
		}
		if (Createdate != null) {
			strSql.append("createdate='" + Createdate + "',");
		}
		if (Wladd != null) {
			strSql.append("wladd='" + Wladd + "',");
		}
		if (Custno != null) {
			strSql.append("custno='" + Custno + "',");
		}
		if (Passkey != null) {
			strSql.append("passkey=" + Passkey + ",");
		}
		if (Password != null) {
			strSql.append("password='" + Password + "',");
		}
		strSql.append("lastop='" + Lastop + "',");
		strSql.append("lastdate=sysdate");
		strSql.append("\n   where custid=" + Custid + " and accid=" + Accid + " ;");
		strSql.append("\n   v_retcs:='1操作成功！' ; ");

		if (Qxbj != null && Qxbj == 1)// 如果启用了权限控制，增加的品牌自动加入权限表
		{
			if (Alluser == 1) {// 自动授权所有用户
				strSql.append("\n   insert into employecust (epid,custid)");
				strSql.append("\n   select epid," + Custid + " from employe b where b.statetag=1 and b.accid= " + Accid);
				strSql.append("\n   and not exists (select 1 from employecust c where b.epid=c.epid and c.custid=" + Custid + ");");
			} else {
				strSql.append("\n   select count(*) into v_count from employecust where epid=" + Userid + " and custid=" + Custid + ";");
				strSql.append("\n   if v_count=0 then ");
				strSql.append("\n      insert into employecust (epid,custid)");
				strSql.append("\n      values (" + Userid + "," + Custid + ") ;");
				strSql.append("\n   end if;");
			}
		}

		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual; ");
		strSql.append("\n  end;");
		System.out.println(strSql.toString());
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
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(",b.epname as handmanname");
		strSql.append("\n  FROM customer a");
		strSql.append("\n  left outer join employe b on a.handmanid=b.epid");
		if (!strWhere.equals("")) {
			strSql.append("\n  where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(",b.epname as handmanname");
		strSql.append("\n  FROM customer a");
		strSql.append("\n  left outer join employe b on a.handmanid=b.epid");
		if (!strWhere.equals("")) {
			strSql.append("\n  where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取所有数据 userd
	public Table GetTable(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n  FROM customer");
		if (!strWhere.equals("")) {
			strSql.append("\n  where " + strWhere);
		}

		return DbHelperSQL.GetTable(strSql.toString());
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from customer");
		strSql.append("\n  where custname='" + Custname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		if (Custid != null)
			strSql.append("\n  and Custid<>" + Custid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "品牌:" + Custname + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}

	// 从excel导入
	public int LoadFormXLS() {
		if (Custid == null)
			Custid = (long) 0;
		String qry = "declare";// "select wareid from warecode where accid=" + accid + " and wareno='" + wareno + "' and rownum=1";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_count number;";
		qry += "\n begin";
		if (Custid > 0) // 货号已存在,更新商品资料
		{
			qry += "\n  select count(*) into v_count from customer where custid=" + Custid + " and statetag=1 and accid=" + Accid + ";";
			qry += "\n  if v_count=0 then";
			qry += "\n     v_retcs:='0客户id未找到！';";
			qry += "\n     goto exit;";
			qry += "\n  end if;";
			if (Custname != null) {
				if (Custname.equals("")) {
					errmess = "客户名称不允许为空";
					return 0;
				}
				qry += "\n  select count(*) into v_count from customer where Custname='" + Custname + "' and custid<>" + Custid + " and statetag=1 and accid=" + Accid + ";";

				qry += "\n  if v_count>0 then";
				qry += "\n     v_retcs:='0客户已存在！';";
				qry += "\n     goto exit;";
				qry += "\n  end if;";

			}
			qry += "\n  begin";
			qry += "\n   update customer set lastdate=sysdate,lastop='" + Lastop + "'";
			if (Custname != null) {
				qry += ",Custname='" + Custname + "'";
				Shortname = PinYin.getPinYinHeadChar(Custname);
				qry += ",shortname='" + Func.subString(Shortname, 1, 30) + "'";
			}
			// }
			if (Address != null && !Address.equals("")) {
				qry += ",address='" + Address + "'";
			}
			if (Tel != null && !Tel.equals("")) {
				qry += ",tel='" + Func.subString(Tel, 1, 30) + "'";
			}
			if (Linkman != null && !Linkman.equals("")) {
				qry += ",linkman='" + Linkman + "'";
			}
			if (Postcode != null && !Postcode.equals("")) {
				qry += ",postcode='" + Postcode + "'";
			}
			if (Mobile != null && !Mobile.equals("")) {
				qry += ",mobile='" + Func.subString(Mobile, 1, 11)+ "'";
			}
			if (Remark != null && !Remark.equals("")) {
				qry += ",remark='" + Remark + "'";
			}
			if (Bankname != null && !Bankname.equals("")) {
				qry += ",bankname='" + Bankname + "'";
			}
			if (Taxno != null && !Taxno.equals("")) {
				qry += ",taxno='" + Taxno + "'";
			}
			if (Areaname != null && !Areaname.equals("")) {
				qry += ",areaname='" + Areaname + "'";
			}
			if (Currbzj != null) {
				qry += ",currbzj=" + Currbzj;
			}
			if (Creditcurr != null) {
				qry += ",creditcurr=" + Creditcurr;
			}
			if (Noused != null) {
				qry += ",Noused=" + Noused;
			}
			if (Creditok != null && Creditok >= 0 && Creditok <= 1) {
				qry += ",creditok=" + Creditok;
			}

			if (Pricetype != null && Pricetype >= 0 && Pricetype <= 6) {
				qry += ",pricetype=" + Pricetype;
			}
			// 面料useritem1，里料useritem2，毛料useritem3，配料useritem4，填充物useritem5
			if (Discount != null && Discount >= 0 && Discount <= 1) {
				qry += ",discount=" + Discount;
			}
			qry += "\n where custid=" + Custid + " and accid=" + Accid + ";";

		} else {
			if (Custname == null || Custname.equals("")) {
				errmess = "客户名称不允许为空";
				return 0;
			}

			Shortname = PinYin.getPinYinHeadChar(Custname);
			qry += "\n  select count(*) into v_count from customer where Custname='" + Custname + "'  and statetag=1 and accid=" + Accid + ";";

			qry += "\n  if v_count>0 then";
			qry += "\n     v_retcs:='0客户已存在！';";
			qry += "\n     goto exit;";
			qry += "\n  end if;";

			String sqlinsert1 = "lastdate,statetag,accid,lastop";
			String sqlinsert2 = "sysdate,1," + Accid + ",'" + Lastop + "'";
			sqlinsert1 += ",custid";
			sqlinsert2 += ",customer_custid.nextval";
			sqlinsert1 += ",Custname";
			sqlinsert2 += ",'" + Custname + "'";
			sqlinsert1 += ",shortname";
			sqlinsert2 += ",'" + Func.subString(Shortname, 1, 30) + "'";
			if (Address != null && !Address.equals("")) {
				sqlinsert1 += ",address";
				sqlinsert2 += ",'" + Address + "'";
			}
			if (Postcode != null && !Postcode.equals("")) {
				sqlinsert1 += ",postcode";
				sqlinsert2 += ",'" + Postcode + "'";
			}
			if (Tel != null && !Tel.equals("")) {
				sqlinsert1 += ",tel";
				sqlinsert2 += ",'" + Func.subString(Tel, 1, 30) + "'";
			}
			if (Linkman != null && !Linkman.equals("")) {
				sqlinsert1 += ",linkman";
				sqlinsert2 += ",'" + Linkman + "'";
			}
			if (Mobile != null && !Mobile.equals("")) {
				sqlinsert1 += ",mobile";
				sqlinsert2 += ",'" +  Func.subString(Mobile, 1, 11) + "'";
			}
			if (Remark != null && !Remark.equals("")) {
				sqlinsert1 += ",remark";
				sqlinsert2 += ",'" + Remark + "'";
			}
			if (Bankname != null && !Bankname.equals("")) {
				sqlinsert1 += ",bankname";
				sqlinsert2 += ",'" + Bankname + "'";
			}
			if (Taxno != null && !Taxno.equals("")) {
				sqlinsert1 += ",taxno";
				sqlinsert2 += ",'" + Taxno + "'";
			}
			if (Accountno != null && !Accountno.equals("")) {
				sqlinsert1 += ",accountno";
				sqlinsert2 += ",'" + Accountno + "'";
			}
			if (Pricetype != null && Pricetype >= 0 && Pricetype <= 6) {
				sqlinsert1 += ",pricetype";
				sqlinsert2 += "," + Pricetype;
			}
			if (Discount != null && Discount >= 0 && Discount <= 1) {
				sqlinsert1 += ",discount";
				sqlinsert2 += "," + Discount;
			}
			if (Areaname != null && !Areaname.equals("")) {
				sqlinsert1 += ",areaname";
				sqlinsert2 += ",'" + Areaname + "'";
			}
			if (Currbzj != null) {
				sqlinsert1 += ",currbzj";
				sqlinsert2 += "," + Currbzj;
			}
			if (Creditcurr != null) {
				sqlinsert1 += ",creditcurr";
				sqlinsert2 += "," + Creditcurr;
			}
			if (Noused != null) {
				sqlinsert1 += ",Noused";
				sqlinsert2 += "," + Noused;
			}
			if (Creditok != null && Creditok >= 0 && Creditok <= 1) {
				sqlinsert1 += ",creditok";
				sqlinsert2 += "," + Creditok;
			}
			// {
			qry += "\n  begin";
			qry += "\n  insert into customer (" + sqlinsert1 + ") values (" + sqlinsert2 + ") ;";
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

	// 下载
	public int doDown(String downdate)// downdate="",同步所有数据，只同步更新的数据
	{
		StringBuilder sql = new StringBuilder();
		String headsql = "";
		boolean isupdate = false;
		String qry = "select custid,custname,shortname,address,postcode,tel,linkman,mobile,bankname,taxno,accountno,remark,pricetype"//
				+ ",discount,currbzj,creditcurr,creditok,areaname,xsnum,createdate,wladd,custno,lastop,noused,statetag,accid";
		qry += " from customer where accid=" + Accid;
		if (downdate.length() <= 0) {
			qry += " and statetag=1 and noused=0";
			headsql = " insert ";
		} else {
			qry += " and lastdate>=to_date('" + downdate + "','yyyy-mm-dd hh24:mi:ss')";
			isupdate = true; // 更改
			headsql = " replace ";
		}
		Table tb = new Table();
		// System.out.println(qry);
		tb = DbHelperSQL.GetTable(qry);

		if (!isupdate)

			sql.append("\n delete from customer where accid=" + Accid + ";");

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
			sql.append("\n " + headsql + " into customer (" + sql1 + " lastdate) values (" + sql2 + " datetime('now','localtime'));");
		}

		String path = Func.getRootPath();
		String filename = "down" + Func.desEncode(Accid + "_" + Func.DateToStr(new Date(), "yyyyMMddHHmmss")).toLowerCase() + Func.getRandomNumA(5) + ".sql";// "brand.sql";
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