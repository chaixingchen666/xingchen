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

import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-08-29 18:38:46
//*************************************
public class Accregpay implements java.io.Serializable {
	private Long Accid;
	private String Fullname;
	private String Shortname;
	private String Servicephone;
	private String Businesslicense;
	private String Businesslicensetype;
	private String Contactname;
	private String Contactphone;
	private String Contactpersontype;
	private String Contactidcard;
	private String Contactemail;
	private String Merchantno;
	private String Merchantkey;
	private Integer Bizcategory;
	private String Kzbj;   //XXXX   1=支付宝，2=微信

	private String Imageyyzz;
	private String Imagezzjg;
	private String Imagefrsfza;
	private String Imagefrsfzb;

	private String Imagejskcyrsfza;//*结算卡持有人身份证正面
	private String Imagejskcyrsfzb;//*结算卡持有人身份证反面
	private String Imagedpzpmt;//*店铺照片（门头）
	private String Imagedpzpsyt;//*店铺照片（收银台）
	private String Imagedpzpjycs;//*店铺照片（经营场所）
	private String Imagejskzp;
	private Integer Remitt1;
	private Integer Remitd0;
	private String Province;
	private String City;
	private String District;
	private String Address;
	private String Bankaccountno;
	private String Bankaccountname;
	private String Idcard;
	private String Bankaccounttype;
	private String Bankaccountlineno;
	private String Bankaccountaddress;
	private String Lastop;
	private String Checkman;
	private Integer Statetag;
	private Long Levelid;

	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setLevelid(Long levelid) {
		Levelid = levelid;
	}

	public void setFullname(String fullname) {
		this.Fullname = fullname;
	}

	public void setKzbj(String kzbj) {
		this.Kzbj = kzbj;
	}

	public String getFullname() {
		return Fullname;
	}

	public void setShortname(String shortname) {
		this.Shortname = shortname;
	}

	public String getShortname() {
		return Shortname;
	}

	public void setServicephone(String servicephone) {
		this.Servicephone = servicephone;
	}

	public String getServicephone() {
		return Servicephone;
	}

	public void setBusinesslicense(String businesslicense) {
		this.Businesslicense = businesslicense;
	}

	public String getBusinesslicense() {
		return Businesslicense;
	}

	public void setBusinesslicensetype(String businesslicensetype) {
		this.Businesslicensetype = businesslicensetype;
	}

	public String getBusinesslicensetype() {
		return Businesslicensetype;
	}

	public void setContactname(String contactname) {
		this.Contactname = contactname;
	}

	public String getContactname() {
		return Contactname;
	}

	public void setContactphone(String contactphone) {
		this.Contactphone = contactphone;
	}

	public String getContactphone() {
		return Contactphone;
	}

	public void setContactpersontype(String contactpersontype) {
		this.Contactpersontype = contactpersontype;
	}

	public String getContactpersontype() {
		return Contactpersontype;
	}

	public void setContactidcard(String contactidcard) {
		this.Contactidcard = contactidcard;
	}

	public String getContactidcard() {
		return Contactidcard;
	}

	public void setContactemail(String contactemail) {
		this.Contactemail = contactemail;
	}

	public String getContactemail() {
		return Contactemail;
	}

	public void setRemitt1(Integer remitt1) {
		this.Remitt1 = remitt1;
	}

	public Integer getRemitt1() {
		return Remitt1;
	}

	public void setRemitd0(Integer remitd0) {
		this.Remitd0 = remitd0;
	}

	public Integer getRemitd0() {
		return Remitd0;
	}

	public void setProvince(String province) {
		this.Province = province;
	}

	public String getProvince() {
		return Province;
	}

	public void setCity(String city) {
		this.City = city;
	}

	public String getCity() {
		return City;
	}

	public void setDistrict(String district) {
		this.District = district;
	}

	public String getDistrict() {
		return District;
	}

	public void setAddress(String address) {
		this.Address = address;
	}

	public String getAddress() {
		return Address;
	}

	public void setBankaccountno(String bankaccountno) {
		this.Bankaccountno = bankaccountno;
	}

	public String getBankaccountno() {
		return Bankaccountno;
	}

	public void setBankaccountname(String bankaccountname) {
		this.Bankaccountname = bankaccountname;
	}

	public String getBankaccountname() {
		return Bankaccountname;
	}

	public void setIdcard(String idcard) {
		this.Idcard = idcard;
	}

	public String getIdcard() {
		return Idcard;
	}

	public void setBankaccounttype(String bankaccounttype) {
		this.Bankaccounttype = bankaccounttype;
	}

	public String getBankaccounttype() {
		return Bankaccounttype;
	}

	public void setBankaccountlineno(String bankaccountlineno) {
		this.Bankaccountlineno = bankaccountlineno;
	}

	public String getBankaccountlineno() {
		return Bankaccountlineno;
	}

	public void setBankaccountaddress(String bankaccountaddress) {
		this.Bankaccountaddress = bankaccountaddress;
	}

	public String getBankaccountaddress() {
		return Bankaccountaddress;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
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

		strSql.append(" begin");
		strSql.append("\n   delete from  accregpay where  accid=" + Accid + ";");
		strSql.append("\n   update payway set noused=1 where accid=" + Accid + " and (payno='Z' or payno='W');");
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
		return 0;
		// if (Accid == null || Accid == 0) {
		// errmess = "accid不是一个有效值！";
		// return 0;
		// }
		// StringBuilder strSql = new StringBuilder();
		// StringBuilder strSql1 = new StringBuilder();
		// StringBuilder strSql2 = new StringBuilder();
		//
		// // if (Accid != null) {
		// strSql1.append("accid,");
		// strSql2.append(Accid + ",");
		// // }
		// if (Fullname != null) {
		// strSql1.append("fullname,");
		// strSql2.append("'" + Fullname + "',");
		// }
		// if (Shortname != null) {
		// strSql1.append("shortname,");
		// strSql2.append("'" + Shortname + "',");
		// }
		// if (Servicephone != null) {
		// strSql1.append("servicephone,");
		// strSql2.append("'" + Servicephone + "',");
		// }
		// if (Businesslicense != null) {
		// strSql1.append("businesslicense,");
		// strSql2.append("'" + Businesslicense + "',");
		// }
		// if (Businesslicensetype != null) {
		// strSql1.append("businesslicensetype,");
		// strSql2.append("'" + Businesslicensetype + "',");
		// }
		// if (Contactname != null) {
		// strSql1.append("contactname,");
		// strSql2.append("'" + Contactname + "',");
		// }
		// if (Contactphone != null) {
		// strSql1.append("contactphone,");
		// strSql2.append("'" + Contactphone + "',");
		// }
		// if (Contactpersontype != null) {
		// strSql1.append("contactpersontype,");
		// strSql2.append("'" + Contactpersontype + "',");
		// }
		// if (Contactidcard != null) {
		// strSql1.append("contactidcard,");
		// strSql2.append("'" + Contactidcard + "',");
		// }
		// if (Contactemail != null) {
		// strSql1.append("contactemail,");
		// strSql2.append("'" + Contactemail + "',");
		// }
		// if (Remitt1 != null) {
		// strSql1.append("remitt1,");
		// strSql2.append(Remitt1 + ",");
		// }
		// if (Remitd0 != null) {
		// strSql1.append("remitd0,");
		// strSql2.append(Remitd0 + ",");
		// }
		// if (Province != null) {
		// strSql1.append("province,");
		// strSql2.append("'" + Province + "',");
		// }
		// if (City != null) {
		// strSql1.append("city,");
		// strSql2.append("'" + City + "',");
		// }
		// if (District != null) {
		// strSql1.append("district,");
		// strSql2.append("'" + District + "',");
		// }
		// if (Address != null) {
		// strSql1.append("address,");
		// strSql2.append("'" + Address + "',");
		// }
		// if (Bankaccountno != null) {
		// strSql1.append("bankaccountno,");
		// strSql2.append("'" + Bankaccountno + "',");
		// }
		// if (Bankaccountname != null) {
		// strSql1.append("bankaccountname,");
		// strSql2.append("'" + Bankaccountname + "',");
		// }
		// if (Idcard != null) {
		// strSql1.append("idcard,");
		// strSql2.append("'" + Idcard + "',");
		// }
		// if (Bankaccounttype != null) {
		// strSql1.append("bankaccounttype,");
		// strSql2.append("'" + Bankaccounttype + "',");
		// }
		// if (Bankaccountlineno != null) {
		// strSql1.append("bankaccountlineno,");
		// strSql2.append("'" + Bankaccountlineno + "',");
		// }
		// if (Bankaccountaddress != null) {
		// strSql1.append("bankaccountaddress,");
		// strSql2.append("'" + Bankaccountaddress + "',");
		// }
		// // if (Lastop != null) {
		// strSql1.append("lastop,");
		// strSql2.append("'" + Lastop + "',");
		// // }
		// strSql1.append("lastdate");
		// strSql2.append("sysdate");
		// strSql.append("declare ");
		// strSql.append(" v_count number; ");
		// strSql.append(" begin ");
		// strSql.append(" select count(*) into v_count from accregpay where
		// accid=" + Accid + "; ");
		// strSql.append(" if v_count=0 then");
		// strSql.append(" insert into accregpay (" + strSql1.toString() + ")");
		// strSql.append(" values (" + strSql2.toString() + ");");
		// strSql.append(" else ");
		//
		// strSql.append(" end;");
		// // Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		// // param.put("id", new ProdParam(Types.BIGINT));
		// int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		// // id = Long.parseLong(param.get("id").getParamvalue().toString());
		// if (ret < 0) {
		// errmess = "操作异常！";
		// return 0;
		// } else {
		// errmess = "操作成功！";
		// return 1;
		// }
	}

	// 修改记录
	public int Check(int checkid) {
		if (Accid == null || Accid == 0) {
			errmess = "accid不是一个有效值！";
			return 0;
		}
		if (checkid < 0 || checkid > 1) {
			errmess = "checkid不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();

		strSql.append("declare ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_checkman varchar2(20); ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n   v_statetag number(1);");
		strSql.append("\n begin ");
		strSql.append("\n   begin ");
		strSql.append("\n     select checkman,statetag into v_checkman,v_statetag from accregpay where accid=" + Accid + "; ");
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n     v_retcs:='0未找到企业账号';");
		strSql.append("\n     goto exit;");
		strSql.append("\n   end;");
		if (checkid == 1) {// 1=审核
			strSql.append("\n   if v_statetag=1 then ");
			strSql.append("\n      v_retcs:='0当前记录已审核！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
			strSql.append("\n   update accregpay set statetag=1,checkman='" + Lastop + "'");
			if (Kzbj != null)
				strSql.append("\n   ,kzbj='" + Kzbj + "'");
			strSql.append("\n   where accid=" + Accid + ";");
			if (Kzbj != null) {
				Kzbj += "0000000";//第1位=支付宝，第2位=微信
				if (Func.subString(Kzbj, 1, 1).equals("1")) {  //开通支付宝
					strSql.append("\n  select count(*) into v_count from payway where accid=" + Accid + " and payno='Z';");
					strSql.append("\n  if v_count=0 then");
					strSql.append("\n     insert into payway (payid,payno,payname,accid,statetag,noused,lastop,payaccount) values  (payway_payid.nextval,'Z','*支付宝*'," + Accid + ",2,1,'sysdba','');");
					strSql.append("\n  end if;");
					strSql.append("\n  update payway set statetag=1,noused=0 where accid=" + Accid + " and payno='Z';");
				} else {
					strSql.append("\n  update payway set statetag=2,noused=1 where accid=" + Accid + " and payno='Z';");
				}

				if (Func.subString(Kzbj, 2, 1).equals("1")) {  //开通微信
					strSql.append("\n  select count(*) into v_count from payway where accid=" + Accid + " and payno='W';");
					strSql.append("\n  if v_count=0 then");
					strSql.append("\n     insert into payway (payid,payno,payname,accid,statetag,noused,lastop,payaccount) values  (payway_payid.nextval,'W','*微信*'," + Accid + ",2,1,'sysdba','');");
					strSql.append("\n  end if;");
					strSql.append("\n  update payway set statetag=1,noused=0 where accid=" + Accid + " and payno='W';");
				} else {
					strSql.append("\n  update payway set statetag=2,noused=1 where accid=" + Accid + " and payno='W';");
				}

				// strSql.append("\n insert into payway	 (payid,payno,payname,accid,statetag,noused,lastop,payaccount) values (payway_payid.nextval,'W','*微信*',v_accid,2,1,'sysdba','');");
			}
		} else {// 0=取消审核
			if (Levelid != 0) {//系统管理员可以取消审核其他用户审核的记录
				strSql.append("\n   if v_checkman<>'" + Lastop + "' then ");
				strSql.append("\n      v_retcs:='0系统不允许取消别人审核的记录！';");
				strSql.append("\n      goto exit;");
				strSql.append("\n   end if;");
			}
			strSql.append("\n   update accregpay set statetag=0,checkman=''");
			if (Kzbj != null)
				strSql.append("\n   ,kzbj='" + Kzbj + "'");
			strSql.append("\n   where accid=" + Accid + ";");
			strSql.append("\n   update payway set statetag=2,noused=1 where accid=" + Accid + " and (payno='W' or payno='Z');");

		}
		strSql.append("\n   v_retcs:='1操作成功！';");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
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
	}

	// 修改记录
	public int Update(String pictjson) {
		if (Accid == null || Accid == 0) {
			errmess = "accid不是一个有效值！";
			return 0;
		}
		StringBuilder strSql0 = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		// strSql0.append(" update accregpay set ");
		strSql1.append("accid,");
		strSql2.append(Accid + ",");

		if (Fullname != null) {
			strSql0.append("fullname='" + Fullname + "',");
			strSql1.append("fullname,");
			strSql2.append("'" + Fullname + "',");
		}
		if (Shortname != null) {
			strSql0.append("shortname='" + Shortname + "',");
			strSql1.append("Shortname,");
			strSql2.append("'" + Shortname + "',");
		}
		if (Servicephone != null) {
			strSql0.append("servicephone='" + Servicephone + "',");
			strSql1.append("servicephone,");
			strSql2.append("'" + Servicephone + "',");
		}
		if (Businesslicense != null) {
			strSql0.append("businesslicense='" + Businesslicense + "',");
			strSql1.append("Businesslicense,");
			strSql2.append("'" + Businesslicense + "',");
		}
		if (Businesslicensetype != null) {
			strSql0.append("businesslicensetype='" + Businesslicensetype + "',");
			strSql1.append("Businesslicensetype,");
			strSql2.append("'" + Businesslicensetype + "',");
		}
		if (Contactname != null) {
			strSql0.append("contactname='" + Contactname + "',");
			strSql1.append("Contactname,");
			strSql2.append("'" + Contactname + "',");
		}
		if (Contactphone != null) {
			strSql0.append("contactphone='" + Contactphone + "',");
			strSql1.append("Contactphone,");
			strSql2.append("'" + Contactphone + "',");
		}
		if (Contactpersontype != null) {
			strSql0.append("contactpersontype='" + Contactpersontype + "',");
			strSql1.append("Contactpersontype,");
			strSql2.append("'" + Contactpersontype + "',");
		}
		if (Contactidcard != null) {
			strSql0.append("contactidcard='" + Contactidcard + "',");
			strSql1.append("Contactidcard,");
			strSql2.append("'" + Contactidcard + "',");
		}
		if (Contactemail != null) {
			strSql0.append("contactemail='" + Contactemail + "',");
			strSql1.append("contactemail,");
			strSql2.append("'" + Contactemail + "',");
		}
		if (Remitt1 != null) {
			strSql0.append("remitt1=" + Remitt1 + ",");
			strSql1.append("Remitt1,");
			strSql2.append(Remitt1 + ",");
		}
		if (Remitd0 != null) {
			strSql0.append("remitd0=" + Remitd0 + ",");
			strSql1.append("Remitd0,");
			strSql2.append(Remitd0 + ",");
		}
		if (Province != null) {
			strSql0.append("province='" + Province + "',");
			strSql1.append("Province,");
			strSql2.append("'" + Province + "',");
		}
		if (City != null) {
			strSql0.append("city='" + City + "',");
			strSql1.append("City,");
			strSql2.append("'" + City + "',");
		}
		if (District != null) {
			strSql0.append("district='" + District + "',");
			strSql1.append("District,");
			strSql2.append("'" + District + "',");
		}
		if (Address != null) {
			strSql0.append("address='" + Address + "',");
			strSql1.append("Address,");
			strSql2.append("'" + Address + "',");
		}
		if (Bankaccountno != null) {
			strSql0.append("bankaccountno='" + Bankaccountno + "',");
			strSql1.append("Bankaccountno,");
			strSql2.append("'" + Bankaccountno + "',");
		}
		if (Bankaccountname != null) {
			strSql0.append("bankaccountname='" + Bankaccountname + "',");
			strSql1.append("Bankaccountname,");
			strSql2.append("'" + Bankaccountname + "',");
		}
		if (Idcard != null) {
			strSql0.append("idcard='" + Idcard + "',");
			strSql1.append("Idcard,");
			strSql2.append("'" + Idcard + "',");
		}
		if (Bankaccounttype != null) {
			strSql0.append("bankaccounttype='" + Bankaccounttype + "',");
			strSql1.append("Bankaccounttype,");
			strSql2.append("'" + Bankaccounttype + "',");
		}
		if (Bankaccountlineno != null) {
			strSql0.append("BANKACCOUNTLINENO='" + Bankaccountlineno + "',");
			strSql1.append("BANKACCOUNTLINENO,");
			strSql2.append("'" + Bankaccountlineno + "',");
		}
		if (Bankaccountaddress != null) {
			strSql0.append("BANKACCOUNTADDRESS='" + Bankaccountaddress + "',");
			strSql1.append("BANKACCOUNTADDRESS,");
			strSql2.append("'" + Bankaccountaddress + "',");
		}
		if (Merchantno != null) {
			strSql0.append("Merchantno='" + Merchantno + "',");
			strSql1.append("Merchantno,");
			strSql2.append("'" + Merchantno + "',");
		}
		if (Merchantkey != null) {
			strSql0.append("Merchantkey='" + Merchantkey + "',");
			strSql1.append("Merchantkey,");
			strSql2.append("'" + Merchantkey + "',");
		}
		if (Bizcategory != null) {
			strSql0.append("Bizcategory=" + Bizcategory + ",");
			strSql1.append("Bizcategory,");
			strSql2.append(Bizcategory + ",");
		}
		if (Kzbj != null) {  //--1=微信,2=支付宝
			strSql0.append("Kzbj='" + Kzbj + "',");
			strSql1.append("Kzbj,");
			strSql2.append("'" + Kzbj + "',");
		}

		if (!Func.isNull(pictjson)) {

			JSONObject jsonObject = JSONObject.fromObject(pictjson);
			if (jsonObject.has("imageyyzz")) {
				String retcs = Func.Base64UpFastDFS(jsonObject.getString("imageyyzz"));
				if (retcs.substring(0, 1).equals("1")) {
					Imageyyzz = retcs.substring(1);
					//					strSql0.append("imageyyzz='" + Imageyyzz + "',");
					//					strSql1.append("imageyyzz,");
					//					strSql2.append("'" + Imageyyzz + "',");
				}
			}
			if (jsonObject.has("imagezzjg")) {

				String retcs = Func.Base64UpFastDFS(jsonObject.getString("imagezzjg"));
				if (retcs.substring(0, 1).equals("1")) {
					Imagezzjg = retcs.substring(1);
					//					strSql0.append("imagezzjg='" + Imagezzjg + "',");
					//					strSql1.append("imagezzjg,");
					//					strSql2.append("'" + Imagezzjg + "',");
				}
			}
			if (jsonObject.has("imagefrsfza")) {
				String retcs = Func.Base64UpFastDFS(jsonObject.getString("imagefrsfza"));
				if (retcs.substring(0, 1).equals("1")) {
					Imagefrsfza = retcs.substring(1);
					//					strSql0.append("imagefrsfza='" + Imagefrsfza + "',");
					//					strSql1.append("imagefrsfza,");
					//					strSql2.append("'" + Imagefrsfza + "',");
				}
			}
			if (jsonObject.has("imagefrsfzb")) {
				String retcs = Func.Base64UpFastDFS(jsonObject.getString("imagefrsfzb"));
				if (retcs.substring(0, 1).equals("1")) {
					Imagefrsfzb = retcs.substring(1);
					//					strSql0.append("imagefrsfzb='" + Imagefrsfzb + "',");
					//					strSql1.append("imagefrsfzb,");
					//					strSql2.append("'" + Imagefrsfzb + "',");
				}
			}

			//			private String imagejskcyrsfza;//*结算卡持有人身份证正面
			if (jsonObject.has("imagejskcyrsfza")) {
				String retcs = Func.Base64UpFastDFS(jsonObject.getString("imagejskcyrsfza"));
				if (retcs.substring(0, 1).equals("1")) {
					Imagejskcyrsfza = retcs.substring(1);
					//					strSql0.append("imagejskcyrsfza='" + Imagejskcyrsfza + "',");
					//					strSql1.append("imagejskcyrsfza,");
					//					strSql2.append("'" + Imagejskcyrsfza + "',");
				}
			}

			//			private String imagejskcyrsfzb;//*结算卡持有人身份证反面
			if (jsonObject.has("imagejskcyrsfzb")) {
				String retcs = Func.Base64UpFastDFS(jsonObject.getString("imagejskcyrsfzb"));
				if (retcs.substring(0, 1).equals("1")) {
					Imagejskcyrsfzb = retcs.substring(1);
					//					strSql0.append("imagejskcyrsfzb='" + Imagejskcyrsfzb + "',");
					//					strSql1.append("imagejskcyrsfzb,");
					//					strSql2.append("'" + Imagejskcyrsfzb + "',");
				}
			}

			//			private String imagedpzpmt;//*店铺照片（门头）
			if (jsonObject.has("imagedpzpmt")) {
				String retcs = Func.Base64UpFastDFS(jsonObject.getString("imagedpzpmt"));
				if (retcs.substring(0, 1).equals("1")) {
					Imagedpzpmt = retcs.substring(1);
					//					strSql0.append("imagedpzpmt='" + Imagedpzpmt + "',");
					//					strSql1.append("imagedpzpmt,");
					//					strSql2.append("'" + Imagedpzpmt + "',");
				}
			}
			//			private String imagedpzpsyt;//*店铺照片（收银台）
			if (jsonObject.has("imagedpzpsyt")) {
				String retcs = Func.Base64UpFastDFS(jsonObject.getString("imagedpzpsyt"));
				if (retcs.substring(0, 1).equals("1")) {
					Imagedpzpsyt = retcs.substring(1);
					//					strSql0.append("imagedpzpsyt='" + Imagedpzpsyt + "',");
					//					strSql1.append("imagedpzpsyt,");
					//					strSql2.append("'" + Imagedpzpsyt + "',");
				}
			}
			//			private String imagedpzpjycs;//*店铺照片（经营场所）
			if (jsonObject.has("imagedpzpjycs")) {
				String retcs = Func.Base64UpFastDFS(jsonObject.getString("imagedpzpjycs"));
				if (retcs.substring(0, 1).equals("1")) {
					Imagedpzpjycs = retcs.substring(1);
					//					strSql0.append("imagedpzpjycs='" + Imagedpzpjycs + "',");
					//					strSql1.append("imagedpzpjycs,");
					//					strSql2.append("'" + Imagedpzpjycs + "',");
				}
			}
			if (jsonObject.has("imagejskzp")) {
				String retcs = Func.Base64UpFastDFS(jsonObject.getString("imagejskzp"));
				if (retcs.substring(0, 1).equals("1")) {
					Imagejskzp = retcs.substring(1);
					//					strSql0.append("Imagejskzp='" + Imagejskzp + "',");
					//					strSql1.append("Imagejskzp,");
					//					strSql2.append("'" + Imagejskzp + "',");
				}
			}
		}
		if (Imageyyzz != null) {
			strSql0.append("imageyyzz='" + Imageyyzz + "',");
			strSql1.append("imageyyzz,");
			strSql2.append("'" + Imageyyzz + "',");
		}

		if (Imagezzjg != null) {
			strSql0.append("imagezzjg='" + Imagezzjg + "',");
			strSql1.append("imagezzjg,");
			strSql2.append("'" + Imagezzjg + "',");
		}
		if (Imagejskcyrsfza != null) {
			strSql0.append("imagejskcyrsfza='" + Imagejskcyrsfza + "',");
			strSql1.append("imagejskcyrsfza,");
			strSql2.append("'" + Imagejskcyrsfza + "',");
		}
		if (Imagefrsfza != null) {
			strSql0.append("imagefrsfza='" + Imagefrsfza + "',");
			strSql1.append("imagefrsfza,");
			strSql2.append("'" + Imagefrsfza + "',");
		}
		if (Imagefrsfzb != null) {
			strSql0.append("imagefrsfzb='" + Imagefrsfzb + "',");
			strSql1.append("imagefrsfzb,");
			strSql2.append("'" + Imagefrsfzb + "',");
		}
		if (Imagejskcyrsfzb != null) {
			strSql0.append("imagejskcyrsfzb='" + Imagejskcyrsfzb + "',");
			strSql1.append("imagejskcyrsfzb,");
			strSql2.append("'" + Imagejskcyrsfzb + "',");
		}
		if (Imagedpzpsyt != null) {
			strSql0.append("imagedpzpsyt='" + Imagedpzpsyt + "',");
			strSql1.append("imagedpzpsyt,");
			strSql2.append("'" + Imagedpzpsyt + "',");
		}
		if (Imagedpzpjycs != null) {
			strSql0.append("imagedpzpjycs='" + Imagedpzpjycs + "',");
			strSql1.append("imagedpzpjycs,");
			strSql2.append("'" + Imagedpzpjycs + "',");
		}
		if (Imagejskzp != null) {
			strSql0.append("Imagejskzp='" + Imagejskzp + "',");
			strSql1.append("Imagejskzp,");
			strSql2.append("'" + Imagejskzp + "',");
		}
		if (Imagedpzpmt != null) {
			strSql0.append("imagedpzpmt='" + Imagedpzpmt + "',");
			strSql1.append("imagedpzpmt,");
			strSql2.append("'" + Imagedpzpmt + "',");
		}
		strSql0.append("lastop='" + Lastop + "',lastdate=sysdate,createdate=sysdate");
		strSql1.append("lastop,lastdate");
		strSql2.append("'" + Lastop + "',sysdate");

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n begin ");
		strSql.append("\n    select count(*) into v_count from accregpay where accid=" + Accid + "; ");
		strSql.append("\n    if v_count=0 then");
		strSql.append("\n       insert into accregpay (" + strSql1.toString() + ")");
		strSql.append("\n       values (" + strSql2.toString() + ");");
		strSql.append("\n    else ");
		strSql.append("\n       update accregpay set " + strSql0.toString());
		strSql.append("\n       where accid=" + Accid + ";");
		strSql.append("\n    end if; ");
		strSql.append("\n end;");

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
		strSql.append(" FROM accregpay a");
		strSql.append(" join accreg b on a.accid=b.accid");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM accregpay a");
		strSql.append(" join accreg b on a.accid=b.accid");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from accregpay");
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
}