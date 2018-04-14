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
//  @author:sunhong  @date:2017-02-19 12:52:14
//*************************************
public class Provide implements java.io.Serializable {
	private Long Provid;
	private String Provname;
	private String Shortname;
	private Long Accid;
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
	private Long Prov_accid;
	private String Lastop;
	private Date Lastdate;
	private Integer Usedbj;
	private Long Kdisc;
	private String errmess;
	private Integer Qxbj; //
	private Integer Alluser = 0; // 授权所有职员

	public void setQxbj(Integer qxbj) {
		this.Qxbj = qxbj;
	}

	private Long Userid;

	public void setUserid(Long userid) {
		this.Userid = userid;
	}

	public String getErrmess() {
		return errmess;
	}

	private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setAccbegindate(String accbegindate) {
		this.Accbegindate = accbegindate;
	}

	public void setProvid(Long provid) {
		this.Provid = provid;
	}

	public Long getProvid() {
		return Provid;
	}

	public void setProvname(String provname) {
		this.Provname = provname;
	}

	public String getProvname() {
		return Provname;
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

	public void setProv_accid(Long prov_accid) {
		this.Prov_accid = prov_accid;
	}

	public Long getProv_accid() {
		return Prov_accid;
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

	public void setUsedbj(Integer usedbj) {
		this.Usedbj = usedbj;
	}

	public Integer getUsedbj() {
		return Usedbj;
	}

	public void setKdisc(Long kdisc) {
		this.Kdisc = kdisc;
	}

	public Long getKdisc() {
		return Kdisc;
	}

	// 删除记录
	public int Delete() {
		if (Provid == 0) {
			errmess = "供应商id参数无效";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();

		strSql.append("select count(*) as num from (");
		strSql.append("select id from wareinh where accid=" + Accid + " and provid=" + Provid + " and rownum=1 and statetag<2");
		strSql.append("  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("  union all");
		strSql.append("  select id from provorderh where accid=" + Accid + " and provid=" + Provid + " and rownum=1 and statetag<2");
		strSql.append("  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("  union all");
		strSql.append("  select id from paycurr where accid=" + Accid + " and provid=" + Provid + " and rownum=1 and statetag<2");
		strSql.append("  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("  union all");
		strSql.append("  select  id from firstpaycurr where accid=" + Accid + " and provid=" + Provid + " and statetag<2 and rownum=1");
		strSql.append("  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("  union all");
		strSql.append("  select id from paycost where accid=" + Accid + " and provid=" + Provid + " and rownum=1 and statetag<2");
		strSql.append("  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");

		strSql.append("  union all");
		strSql.append("  select wareid from warecode where provid=" + Provid + " and rownum=1 and statetag<>2 and accid=" + Accid);

		strSql.append(")");
		// System.out.println(strSql.toString());
		int num = DbHelperSQL.GetSingleCount(strSql.toString());

		if (num > 0) {
			errmess = "当前供应商已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		// strSql.append("delete from provide where accid=" + Accid + " and provid=" + Provid);
		strSql.append("declare ");
		strSql.append("   v_provname varchar2(200);");
		strSql.append("begin ");
		strSql.append("   select provname into v_provname from provide where  provid=" + Provid + " and accid=" + Accid + " and statetag=1;");
		strSql.append("   update provide set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append("   where provid=" + Provid + " and accid=" + Accid + "; ");
		strSql.append("   update warecode set provid=0 where accid=" + Accid + " and provid=" + Provid + "; ");
		strSql.append("   insert into LOGRECORD (id,accid,progname,remark,lastop)");
		strSql.append("   values (LOGRECORD_id.nextval," + Accid + ",'供应商档案','【删除记录】供应商id:" + Provid + "; 供应商名称:'||v_provname,'" + Lastop + "');");
		strSql.append("   commit;");
		strSql.append("  EXCEPTION WHEN NO_DATA_FOUND THEN ");
		strSql.append("  v_provname:=''; ");
		strSql.append("end; ");
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	// 增加记录
	public int Append() {

		if (Provname == null || Provname.equals("")) {
			errmess = "供应商名称参数无效！";
			return 0;
		}

		if (Exists())
			return 0;// 判断供应商名称是否已存在

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("provid,");
		strSql2.append("v_id,");
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		// if (Provid != null) {
		// strSql1.append("provid,");
		// strSql2.append(Provid + ",");
		// }
		// if (Provname != null) {
		strSql1.append("provname,");
		strSql2.append("'" + Provname + "',");
		Shortname = PinYin.getPinYinHeadChar(Provname);
		// }
		if (Shortname != null) {
			strSql1.append("shortname,");
			strSql2.append("'" + Shortname + "',");
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
		if (Discount != null) {
			strSql1.append("discount,");
			strSql2.append(Discount + ",");
		}
		if (Prov_accid != null) {
			strSql1.append("prov_accid,");
			strSql2.append(Prov_accid + ",");
		}
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		// if (Lastdate != null) {
		// strSql1.append("lastdate,");
		// strSql2.append("" + Lastdate + ",");
		// }
		if (Usedbj != null) {
			strSql1.append("usedbj,");
			strSql2.append(Usedbj + ",");
		}
		if (Kdisc != null) {
			strSql1.append("kdisc,");
			strSql2.append(Kdisc + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("  v_id:=provide_provid.nextval; ");
		strSql.append("  insert into provide (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		if (Qxbj == 1)// 如果启用了权限控制，增加的供应商自动加入权限表
		{
			if (Alluser == 1) {// 自动授权所有用户
				strSql.append("\n   insert into employeprov (epid,provid)");
				strSql.append("\n   select epid,v_id from employe b where b.statetag=1 and b.accid= " + Accid + ";");
			} else {
				strSql.append("\n   insert into employeprov (epid,provid)");
				strSql.append("\n   values (" + Userid + ",v_id); ");
			}
		}
		strSql.append("  select v_id into :id from dual; ");
		strSql.append(" end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret >= 0) {
			Provid = Long.parseLong(param.get("id").getParamvalue().toString());
			errmess = Provid.toString();
			return 1;
		} else {
			errmess = "操作异常！";
			return 0;
		}
	}

	// 修改记录
	public int Update() {
		if (Provid == null || Provid == 0) {
			errmess = "供应商id参数无效！";
			return 0;
		}
		if (Exists())
			return 0;// 判断供应商名称是否已存在

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("  v_count number; ");
		strSql.append("\n begin ");
		strSql.append("\n   update provide set ");
		// if (Provid != null) {
		// strSql.append("provid=" + Provid + ",");
		// }
		if (Provname != null) {
			strSql.append("provname='" + Provname + "',");
			Shortname = PinYin.getPinYinHeadChar(Provname);
		}
		if (Shortname != null) {
			strSql.append("shortname='" + Shortname + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
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
		if (Prov_accid != null) {
			strSql.append("prov_accid=" + Prov_accid + ",");
		}
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
		// if (Lastdate != null) {
		// strSql.append("lastdate='" + Lastdate + "',");
		// }
		if (Usedbj != null) {
			strSql.append("usedbj=" + Usedbj + ",");
		}
		if (Kdisc != null) {
			strSql.append("kdisc=" + Kdisc + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where provid=" + Provid + " and accid=" + Accid + ";");
		strSql.append("\n  commit;");

		if (Qxbj != null && Qxbj == 1)// 如果启用了权限控制，增加的品牌自动加入权限表
		{
			if (Alluser == 1) {// 自动授权所有用户
				strSql.append("\n   insert into employeprov (epid,Provid)");
				strSql.append("\n   select epid," + Provid + " from employe b where b.statetag=1 and b.accid= " + Accid);
				strSql.append("\n   and not exists (select 1 from employeprov c where b.epid=c.epid and c.Provid=" + Provid + ");");
			} else {
				strSql.append("\n   select count(*) into v_count from employeprov where epid=" + Userid + " and Provid=" + Provid + ";");
				strSql.append("\n   if v_count=0 then ");
				strSql.append("\n      insert into employeprov (epid,Provid)");
				strSql.append("\n      values (" + Userid + "," + Provid + ") ;");
				strSql.append("\n   end if;");
			}
		}
		strSql.append("\n end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功!";
			return 1;
		}

	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM provide ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM provide a");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	/// <summary>
	/// 是否存在该记录
	/// </summary>
	public boolean Exists() {
		// if (Provname == "") {
		// errmess = "供应商名称必须输入！";
		// return false;
		// }
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from provide");
		strSql.append(" where Provname='" + Provname + "' and statetag=1  and rownum=1 and accid=" + Accid);
		if (Provid != null)
			strSql.append(" and provid<>" + Provid);
		// System.out.println(strSql.toString());
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "供应商:" + Provname + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}

	// 从excel导入
	public int LoadFormXLS() {
		if (Provid == null)
			Provid = (long) 0;
		String qry = "declare";// "select wareid from warecode where accid=" + accid + " and wareno='" + wareno + "' and rownum=1";
		qry += "   v_retcs varchar2(200);";
		qry += "   v_count number;";
		qry += "\n begin";
		if (Provid > 0) // 货号已存在,更新商品资料
		{
			// qry = "select provid from provide where provid=" + provid + " and accid=" + accid;
			// dt = DbHelperSQL.Query(qry).Tables[0];
			//
			// if (dt.Rows.Count == 0) {
			// WriteResult("0", "供应商id:" + provid + " 未找到！");
			// return;
			// }

			qry += "\n  select count(*) into v_count from provide where provid=" + Provid + " and statetag=1 and accid=" + Accid + ";";

			qry += "\n  if v_count=0 then";
			qry += "\n     v_retcs:='0供应商id未找到！';";
			qry += "\n     goto exit;";
			qry += "\n  end if;";
			if (Provname != null) {
				if (Provname.equals("")) {
					errmess = "供应商名称不允许为空";
					return 0;
				}
				qry += "\n  select count(*) into v_count from provide where provname='" + Provname + "' and provid<>" + Provid + " and statetag=1 and accid=" + Accid + ";";

				qry += "\n  if v_count>0 then";
				qry += "\n     v_retcs:='0供应商已存在！';";
				qry += "\n     goto exit;";
				qry += "\n  end if;";

			}
			qry += "\n  begin";
			qry += "\n   update provide set lastdate=sysdate,lastop='" + Lastop + "'";
			if (Provname != null) {
				qry += ",provname='" + Provname + "'";
				Shortname = PinYin.getPinYinHeadChar(Provname);
				qry += ",shortname='" + Shortname + "'";
			}
			// }
			if (Address != null && !Address.equals("")) {
				qry += ",address='" + Address + "'";
			}
			if (Tel != null && !Tel.equals("")) {
				qry += ",tel='" + Tel + "'";
			}
			if (Linkman != null && !Linkman.equals("")) {
				qry += ",linkman='" + Linkman + "'";
			}
			if (Postcode != null && !Postcode.equals("")) {
				qry += ",postcode='" + Postcode + "'";
			}
			if (Mobile != null && !Mobile.equals("")) {
				qry += ",mobile='" + Mobile + "'";
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

			if (Pricetype != null && Pricetype >= 0 && Pricetype <= 1) {
				qry += ",pricetype=" + Pricetype;
			}
			// 面料useritem1，里料useritem2，毛料useritem3，配料useritem4，填充物useritem5
			if (Discount != null && Discount >= 0 && Discount <= 1) {
				qry += ",discount=" + Discount;
			}
			qry += " where provid=" + Provid + " and accid=" + Accid + ";";

		} else {
			if (Provname == null || Provname.equals("")) {
				errmess = "供应商名称不允许为空";
				return 0;
			}

			Shortname = PinYin.getPinYinHeadChar(Provname);
			qry += "\n  select count(*) into v_count from provide where provname='" + Provname + "'  and statetag=1 and accid=" + Accid + ";";

			qry += "\n  if v_count>0 then";
			qry += "\n     v_retcs:='0供应商已存在！';";
			qry += "\n     goto exit;";
			qry += "\n  end if;";

			String sqlinsert1 = "lastdate,statetag,accid,lastop";
			String sqlinsert2 = "sysdate,1," + Accid + ",'" + Lastop + "'";
			sqlinsert1 += ",provid";
			sqlinsert2 += ",provide_provid.nextval";
			sqlinsert1 += ",provname";
			sqlinsert2 += ",'" + Provname + "'";
			Shortname = PinYin.getPinYinHeadChar(Provname);
			sqlinsert1 += ",shortname";
			sqlinsert2 += ",'" + Shortname + "'";
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
				sqlinsert2 += ",'" + Tel + "'";
			}
			if (Linkman != null && !Linkman.equals("")) {
				sqlinsert1 += ",linkman";
				sqlinsert2 += ",'" + Linkman + "'";
			}
			if (Mobile != null && !Mobile.equals("")) {
				sqlinsert1 += ",mobile";
				sqlinsert2 += ",'" + Mobile + "'";
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
			if (Pricetype != null && Pricetype >= 0 && Pricetype <= 1) {
				sqlinsert1 += ",pricetype";
				sqlinsert2 += "," + Pricetype;
			}
			if (Noused != null) {
				sqlinsert1 += ",Noused";
				sqlinsert2 += "," + Noused;
			}
		if (Discount != null && Discount >= 0 && Discount <= 1) {
				sqlinsert1 += ",discount";
				sqlinsert2 += "," + Discount;
			}
			// {
		qry += "\n  begin";
			qry += "\n  insert into provide (" + sqlinsert1 + ") values (" + sqlinsert2 + ") ;";
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

		errmess = retcs.substring(1).replace("\"","'");
		return Integer.parseInt(retcs.substring(0, 1));

	}

	// 下载
	public int doDown(String downdate)// downdate="",同步所有数据，只同步更新的数据
	{
		StringBuilder sql = new StringBuilder();
		String headsql = "";
		boolean isupdate = false;
		String qry = "select provid,provname,shortname,address,postcode,tel,linkman,mobile,bankname,taxno,accountno,remark,pricetype,discount,noused,statetag,lastop,accid";
		qry += " from provide where (accid=" + Accid + " or accid=0)";
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

			sql.append("\n delete from provide where accid=" + Accid + " or accid=0;");

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
			sql.append("\n " + headsql + " into provide (" + sql1 + " lastdate) values (" + sql2 + " datetime('now','localtime'));");
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