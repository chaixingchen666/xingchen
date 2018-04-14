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
//  @author:sunhong  @date:2017-04-10 17:11:13
//*************************************
public class Wareadjustm implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private String Noteno;
	private Long Wareid;
	private Float Entersale_0;
	private Float Retailsale_0;
	private Float Sale1_0;
	private Float Sale2_0;
	private Float Sale3_0;
	private Float Sale4_0;
	private Float Sale5_0;
	private Float Entersale;
	private Float Retailsale;
	private Float Sale1;
	private Float Sale2;
	private Float Sale3;
	private Float Sale4;
	private Float Sale5;
	private String Remark0;
	private String Wareno;

	private Date Lastdate;

	private Float Checksale;
	private Float Checksale_0;
	private String errmess;
	private String Calcdate = ""; // 账套开始使用日期

	public void setWareno(String wareno) {
		Wareno = wareno;
	}

	public void setCalcdate(String calcdate) {
		Calcdate = calcdate;
	}

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
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

	public void setWareid(Long wareid) {
		this.Wareid = wareid;
	}

	public Long getWareid() {
		return Wareid;
	}

	public void setEntersale_0(Float entersale_0) {
		this.Entersale_0 = entersale_0;
	}

	public Float getEntersale_0() {
		return Entersale_0;
	}

	public void setRetailsale_0(Float retailsale_0) {
		this.Retailsale_0 = retailsale_0;
	}

	public Float getRetailsale_0() {
		return Retailsale_0;
	}

	public void setSale1_0(Float sale1_0) {
		this.Sale1_0 = sale1_0;
	}

	public Float getSale1_0() {
		return Sale1_0;
	}

	public void setSale2_0(Float sale2_0) {
		this.Sale2_0 = sale2_0;
	}

	public Float getSale2_0() {
		return Sale2_0;
	}

	public void setSale3_0(Float sale3_0) {
		this.Sale3_0 = sale3_0;
	}

	public Float getSale3_0() {
		return Sale3_0;
	}

	public void setSale4_0(Float sale4_0) {
		this.Sale4_0 = sale4_0;
	}

	public Float getSale4_0() {
		return Sale4_0;
	}

	public void setSale5_0(Float sale5_0) {
		this.Sale5_0 = sale5_0;
	}

	public Float getSale5_0() {
		return Sale5_0;
	}

	public void setEntersale(Float entersale) {
		this.Entersale = entersale;
	}

	public Float getEntersale() {
		return Entersale;
	}

	public void setRetailsale(Float retailsale) {
		this.Retailsale = retailsale;
	}

	public Float getRetailsale() {
		return Retailsale;
	}

	public void setSale1(Float sale1) {
		this.Sale1 = sale1;
	}

	public Float getSale1() {
		return Sale1;
	}

	public void setSale2(Float sale2) {
		this.Sale2 = sale2;
	}

	public Float getSale2() {
		return Sale2;
	}

	public void setSale3(Float sale3) {
		this.Sale3 = sale3;
	}

	public Float getSale3() {
		return Sale3;
	}

	public void setSale4(Float sale4) {
		this.Sale4 = sale4;
	}

	public Float getSale4() {
		return Sale4;
	}

	public void setSale5(Float sale5) {
		this.Sale5 = sale5;
	}

	public Float getSale5() {
		return Sale5;
	}

	public void setRemark0(String remark0) {
		this.Remark0 = remark0;
	}

	public String getRemark0() {
		return Remark0;
	}

	public void setChecksale(Float checksale) {
		this.Checksale = checksale;
	}

	public Float getChecksale() {
		return Checksale;
	}

	public void setChecksale_0(Float checksale_0) {
		this.Checksale_0 = checksale_0;
	}

	public Float getChecksale_0() {
		return Checksale_0;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Noteno.length() <= 0 || Id <= 0) {
			errmess = "noteno或id不是一个有效的值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   delete from wareadjustm ");
		strSql.append("   where id=" + Id + " and accid= " + Accid + " and noteno='" + Noteno + "'; ");
		strSql.append("end;");
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
		if (Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		if (Wareid <= 0) {
			errmess = "wareid不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n   v_count number(10);";
		qry += "\n   v_houseid number(10);";
		qry += "\n   v_id number; ";
		qry += "\n   v_retailsale number; ";
		qry += "\n   v_entersale number; ";
		qry += "\n   v_retailsale0 number; ";
		qry += "\n   v_entersale0 number; ";
		qry += "\n   v_retcs varchar2(200); ";
		qry += "\n begin   ";
		qry += "\n   v_houseid:=0;";
		qry += "\n   select count(*) into v_count from warecode where accid=" + Accid + " and statetag=1 and wareid=" + Wareid + ";";
		qry += "\n   if v_count=0 then";
		qry += "\n      v_retcs:='0商品无效！';";
		qry += "\n      goto exit; ";
		qry += "\n   end if;";

		qry += "\n   select count(*) into v_count from wareadjustm where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + ";";
		qry += "\n   if v_count>0 then";
		qry += "\n      v_retcs:= '0当前商品已存在';";
		qry += "\n      goto exit; ";
		qry += "\n   end if;";
		// 查询第一个店铺
		qry += "\n   begin";
		qry += "\n     select houseid into v_houseid from (select houseid from wareadjustc where accid=" + Accid + " and noteno='" + Noteno + "' order by id) where rownum=1;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n     v_houseid:=0;";
		qry += "\n   end;";
		// 查看是否有商品店铺价格
		qry += "\n   if v_houseid>0 then";
		qry += "\n      select retailsale,entersale into v_retailsale0,v_entersale0 from warecode where wareid=" + Wareid + ";";

		qry += "\n      begin";
		qry += "\n        select nvl(retailsale,v_retailsale0),nvl(entersale,v_entersale0) into v_retailsale,v_entersale from housesaleprice where wareid=" + Wareid + " and houseid=v_houseid;";
		qry += "\n      EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n        v_retailsale:=v_retailsale0;  v_entersale:=v_entersale0;";
		// qry += "\n select retailsale,entersale into v_retailsale,v_entersale from warecode where wareid=" + wareid + ";";
		qry += "\n      end;";
		qry += "\n   else ";
		qry += "\n      select retailsale,entersale into v_retailsale,v_entersale from warecode where wareid=" + Wareid + ";";
		qry += "\n   end if; ";

		qry += "\n   v_id:=wareadjustm_id.nextval; ";
		qry += "\n   insert into wareadjustm (id,accid,noteno,wareid,entersale_0,retailsale_0,sale1_0,sale2_0,sale3_0,sale4_0,sale5_0,entersale,retailsale,sale1,sale2,sale3,sale4,sale5,remark0,lastdate)";
		qry += "\n   select v_id," + Accid + ",'" + Noteno
				+ "',wareid,nvl(v_entersale,0),nvl(v_retailsale,0),nvl(sale1,0),nvl(sale2,0),nvl(sale3,0),nvl(sale4,0),nvl(sale5,0),nvl(v_entersale,0),nvl(v_retailsale,0),nvl(sale1,0),nvl(sale2,0),nvl(sale3,0),nvl(sale4,0),nvl(sale5,0),'',sysdate from warecode where wareid="
				+ Wareid + " and accid=" + Accid + ";";
		qry += "\n   commit;";
		qry += "\n   v_retcs:='1'||v_id; ";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs,v_houseid into :retcs,:houseid from dual;";
		qry += "\n end;";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		param.put("houseid", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		if (Integer.parseInt(retcs.substring(0, 1)) == 0) {
			return 0;
		}
		Id = Float.parseFloat(errmess);
		long houseid = Long.parseLong(param.get("houseid").getParamvalue().toString());
		errmess = "\"msg\": \"1\",\"rows\":{";
		// + ",\"SIZENUM\": \"" + retss.Substring(1, retss.Length - 1) + "\"";
		qry = "select a.id,a.wareid,b.warename,b.wareno,b.units";
		qry += ",a.entersale_0,a.retailsale_0,a.sale1_0,a.sale2_0,a.sale3_0,a.sale4_0,a.sale5_0,a.entersale,a.retailsale,a.sale1,a.sale2,a.sale3,a.sale4,a.sale5,a.remark0";
		if (houseid > 0)
			qry += ",f_getwaresumx(to_char(sysdate,'yyyy-mm-dd'),a.accid,a.wareid," + houseid + ",'" + Calcdate + "') as amountkc";
		else
			qry += ",0 as amountkc";
		qry += " from wareadjustm a ";
		qry += " left outer join warecode b on a.wareid=b.wareid";
		qry += "  where a.accid=" + Accid + " and a.noteno='" + Noteno + "' and a.id=" + Id;
		// WriteLogTXT("debug", "1", qry);
		Table tb = DbHelperSQL.Query(qry).getTable(1);
		// responsestr += ",\"rows\":";
		if (tb.getRowCount() > 0) {

			for (int j = 0; j < tb.getColumnCount(); j++) {
				String strKey = tb.getRow(0).getColumn(j + 1).getName().toUpperCase();
				String strValue = tb.getRow(0).get(j).toString();
				if (j > 0)
					errmess += ",";
				errmess += "\"" + strKey + "\":\"" + strValue + "\"";
			}

		}
		errmess += "}";
		return 1;
	}

	// 增加记录
	public int AppendSXY() {
		if (Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		if (Wareid <= 0) {
			errmess = "wareid不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n   v_count number(10);";
		qry += "\n   v_houseid number(10);";
		qry += "\n   v_id number; ";
		qry += "\n   v_retailsale number; ";
		qry += "\n   v_entersale number; ";
		qry += "\n   v_checksale number; ";
		qry += "\n   v_retailsale0 number; ";
		qry += "\n   v_entersale0 number; ";
		qry += "\n   v_checksale0 number; ";
		qry += "\n   v_retcs varchar2(200); ";
		qry += "\n begin   ";
		qry += "\n   v_houseid:=0;";
		qry += "\n   select count(*) into v_count from warecode where accid=" + Accid + " and statetag=1 and wareid=" + Wareid + ";";
		qry += "\n   if v_count=0 then";
		qry += "\n      v_retcs:='0商品无效！';";
		qry += "\n      goto exit; ";
		qry += "\n   end if;";

		qry += "\n   select count(*) into v_count from wareadjustm where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + ";";
		qry += "\n   if v_count>0 then";
		qry += "\n      v_retcs:= '0当前商品已存在';";
		qry += "\n      goto exit; ";
		qry += "\n   end if;";
		// 查询第一个店铺
		qry += "\n   begin";
		qry += "\n     select houseid into v_houseid from (select houseid from wareadjustc where accid=" + Accid + " and noteno='" + Noteno + "' order by id) where rownum=1;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n     v_houseid:=0;";
		qry += "\n   end;";
		// 查看是否有商品店铺价格
		qry += "\n   if v_houseid>0 then";
		qry += "\n      begin";
		qry += "\n       select retailsale,entersale,checksale into v_retailsale0,v_entersale0,v_checksale0 from warecode where wareid=" + Wareid + ";";
		qry += "\n       select nvl(retailsale,v_retailsale0),nvl(entersale,v_entersale0),nvl(checksale,v_checksale0)";
		qry += "\n       into v_retailsale,v_entersale,v_checksale from housesaleprice where wareid=" + Wareid + " and houseid=v_houseid;";
		qry += "\n      EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n        v_retailsale:=v_retailsale0; v_entersale:=v_entersale0; v_checksale:=v_checksale0;";
		// qry += "\n select retailsale,entersale,checksale into v_retailsale,v_entersale,v_checksale from warecode where wareid=" + wareid + ";";
		qry += "\n      end;";
		qry += "\n   else ";
		qry += "\n      select retailsale,entersale,checksale into v_retailsale,v_entersale,v_checksale from warecode where wareid=" + Wareid + ";";
		qry += "\n   end if; ";

		qry += "\n   v_id:=wareadjustm_id.nextval; ";
		qry += "\n   insert into wareadjustm (id,accid,noteno,wareid,entersale_0,checksale_0,retailsale_0,sale1_0,sale2_0,sale3_0,sale4_0,sale5_0,entersale,checksale,retailsale,sale1,sale2,sale3,sale4,sale5,remark0,lastdate)";
		qry += "\n   select v_id," + Accid + ",'" + Noteno + "',wareid,nvl(v_entersale,0),nvl(v_checksale,0),nvl(v_retailsale,0),nvl(sale1,0),nvl(sale2,0),nvl(sale3,0),nvl(sale4,0)"//
				+ "\n ,nvl(sale5,0),nvl(v_entersale,0),nvl(v_checksale,0),nvl(v_retailsale,0),nvl(sale1,0),nvl(sale2,0),nvl(sale3,0),nvl(sale4,0),nvl(sale5,0),'',sysdate ";
		qry += "\n   from warecode where wareid=" + Wareid + " and accid=" + Accid + ";";
		qry += "\n   commit;";
		qry += "\n   v_retcs:='1'||v_id; ";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs,v_houseid into :retcs,:houseid from dual;";
		qry += "\n end;";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		param.put("houseid", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		if (Integer.parseInt(retcs.substring(0, 1)) == 0) {
			return 0;
		}
		Id = Float.parseFloat(errmess);
		long houseid = Long.parseLong(param.get("houseid").getParamvalue().toString());
		errmess = "\"msg\": \"1\",\"rows\":{";
		// + ",\"SIZENUM\": \"" + retss.Substring(1, retss.Length - 1) + "\"";
		qry = "select a.id,a.wareid,b.warename,b.wareno,b.units";
		qry += ",a.entersale_0,a.retailsale_0,a.sale1_0,a.sale2_0,a.sale3_0,a.sale4_0,a.sale5_0,a.entersale,a.retailsale,a.sale1,a.sale2,a.sale3,a.sale4,a.sale5,a.remark0";
		qry += ",a.checksale,a.checksale_0";
		if (houseid > 0)
			qry += ",f_getwaresumx(to_char(sysdate,'yyyy-mm-dd'),a.accid,a.wareid," + houseid + ",'" + Calcdate + "') as amountkc";
		else
			qry += ",0 as amountkc";
		qry += " from wareadjustm a ";
		qry += " left outer join warecode b on a.wareid=b.wareid";
		qry += "  where a.accid=" + Accid + " and a.noteno='" + Noteno + "' and a.id=" + Id;
		// WriteLogTXT("debug", "1", qry);
		Table tb = DbHelperSQL.Query(qry).getTable(1);
		// responsestr += ",\"rows\":";
		if (tb.getRowCount() > 0) {

			for (int j = 0; j < tb.getColumnCount(); j++) {
				String strKey = tb.getRow(0).getColumn(j + 1).getName().toUpperCase();
				String strValue = tb.getRow(0).get(j).toString();
				if (j > 0)
					errmess += ",";
				errmess += "\"" + strKey + "\":\"" + strValue + "\"";
			}

		}
		errmess += "}";
		return 1;
	}

	// 修改记录
	public int Update() {
		if (Func.isNull(Noteno) || Id == null || Id <= 0) {
			errmess = "noteno或id不是一个有效的值！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("  v_count number;");
		strSql.append("  v_retcs varchar2(200);");
		strSql.append("begin ");
		if (Wareid != null) {
			strSql.append("\n   select count(*) into v_count from warecode where accid=" + Accid + " and statetag=1 and wareid=" + Wareid + ";");
			strSql.append("\n   if v_count=0 then");
			strSql.append("\n      v_retcs:='0商品无效！';");
			strSql.append("\n      goto exit; ");
			strSql.append("\n   end if;");
		}
		strSql.append("\n   update Wareadjustm set ");

		// if (Id != null) {
		// strSql.append("id='" + Id + "',");
		// }
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		// if (Noteno != null) {
		// strSql.append("noteno='" + Noteno + "',");
		// }
		if (Wareid != null) {
			strSql.append("wareid=" + Wareid + ",");
		}
		if (Entersale_0 != null) {
			strSql.append("entersale_0='" + Entersale_0 + "',");
		}
		if (Retailsale_0 != null) {
			strSql.append("retailsale_0='" + Retailsale_0 + "',");
		}
		if (Sale1_0 != null) {
			strSql.append("sale1_0='" + Sale1_0 + "',");
		}
		if (Sale2_0 != null) {
			strSql.append("sale2_0='" + Sale2_0 + "',");
		}
		if (Sale3_0 != null) {
			strSql.append("sale3_0='" + Sale3_0 + "',");
		}
		if (Sale4_0 != null) {
			strSql.append("sale4_0='" + Sale4_0 + "',");
		}
		if (Sale5_0 != null) {
			strSql.append("sale5_0='" + Sale5_0 + "',");
		}
		if (Entersale != null) {
			strSql.append("entersale='" + Entersale + "',");
		}
		if (Retailsale != null) {
			strSql.append("retailsale='" + Retailsale + "',");
		}
		if (Sale1 != null) {
			strSql.append("sale1='" + Sale1 + "',");
		}
		if (Sale2 != null) {
			strSql.append("sale2='" + Sale2 + "',");
		}
		if (Sale3 != null) {
			strSql.append("sale3='" + Sale3 + "',");
		}
		if (Sale4 != null) {
			strSql.append("sale4='" + Sale4 + "',");
		}
		if (Sale5 != null) {
			strSql.append("sale5='" + Sale5 + "',");
		}
		if (Remark0 != null) {
			strSql.append("remark0='" + Remark0 + "',");
		}
		if (Checksale != null) {
			strSql.append("checksale='" + Checksale + "',");
		}
		if (Checksale_0 != null) {
			strSql.append("checksale_0='" + Checksale_0 + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=" + Id + " and accid=" + Accid + " and noteno='" + Noteno + "' ;");
		strSql.append("  v_retcs:= '1操作成功！' ; ");
		strSql.append("  <<exit>>");
		strSql.append("  select v_retcs into :retcs from dual;");
		strSql.append(" end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);
		if (Integer.parseInt(retcs.substring(0, 1)) == 0)
			return 0;

		errmess = "\"msg\":\"1\",\"rows\":{";
		// + ",\"SIZENUM\": \"" + retss.Substring(1, retss.Length - 1) + "\"";

		String qry = "select a.id,a.wareid,a.entersale,a.retailsale,a.sale1,a.sale2,a.sale3,a.sale4,a.sale5,a.remark0";
		qry += " from wareadjustm a ";
		// qry += " left outer join warecode b on a.wareid=b.wareid";
		qry += "  where a.accid=" + Accid + " and a.noteno='" + Noteno + "' and a.id=" + Id;
		// WriteLogTXT("debug", "1", qry);
		Table tb = DbHelperSQL.Query(qry).getTable(1);
		// responsestr += ",\"rows\":";
		if (tb.getRowCount() > 0) {

			for (int j = 0; j < tb.getColumnCount(); j++) {
				String strKey = tb.getRow(0).getColumn(j + 1).getName().toUpperCase();
				String strValue = tb.getRow(0).get(j).toString();
				if (j > 0)
					errmess += ",";
				errmess += "\"" + strKey + "\":\"" + strValue + "\"";
			}

		}
		errmess += "}";

		return 1;
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Wareadjustm a");
		strSql.append(" left outer join warecode b on a.wareid=b.wareid");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Wareadjustm a");
		strSql.append(" left outer join warecode b on a.wareid=b.wareid");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Wareadjustm");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	public int Load(JSONObject jsonObject) {
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		int loadbj = jsonObject.has("loadbj") ? Integer.parseInt(jsonObject.getString("loadbj")) : 0;
		// loadbj=0, 清空载入 loadbj=1 追加载入
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		long brandid = jsonObject.has("brandid") ? Long.parseLong(jsonObject.getString("brandid")) : -1;
		long typeid = jsonObject.has("typeid") ? Long.parseLong(jsonObject.getString("typeid")) : -1;
		long provid = jsonObject.has("provid") ? Long.parseLong(jsonObject.getString("provid")) : -1;
		long areaid = jsonObject.has("areaid") ? Long.parseLong(jsonObject.getString("areaid")) : -1;
		String wareno = jsonObject.has("wareno") ? jsonObject.getString("wareno") : "";
		String seasonname = jsonObject.has("seasonname") ? jsonObject.getString("seasonname") : "";
		String warename = jsonObject.has("warename") ? jsonObject.getString("warename") : "";
		String prodyear = jsonObject.has("prodyear") ? jsonObject.getString("prodyear") : "";
		String locale = jsonObject.has("locale") ? jsonObject.getString("locale") : "";
		float minsale = jsonObject.has("minsale") ? Float.parseFloat(jsonObject.getString("minsale")) : -1;
		float maxsale = jsonObject.has("maxsale") ? Float.parseFloat(jsonObject.getString("maxsale")) : -1;
		if (Func.isNull(noteno)) {
			errmess = "noteno不是一个有效的值！";
			return 0;
		}
		String qry = "declare ";

		qry += "\n   v_notedate date; ";
		qry += "\n   v_totalcurr number; ";
		qry += "\n   v_totalamt number; ";
		qry += "\n begin ";
		qry += "\n  v_notedate:=sysdate;";
		if (loadbj == 0) // loadbj:0清空原记录 1追加新记录
		{
			// qry += "\n delete from wareadjustm where accid=" + accid + " and noteno='" + noteno + "';";

			qry += "\n    declare  ";
			qry += "\n       cursor mycursor is ";
			qry += "         select ROWID from wareadjustm where accid=" + Accid + " and noteno='" + noteno + "'";
			qry += "\n       order by rowid; ";
			qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
			qry += "\n       v_rowid   rowid_table_type; ";
			qry += "\n    BEGIN ";
			qry += "\n       open mycursor; ";
			qry += "\n       loop ";
			qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
			qry += "\n          exit when v_rowid.count=0; ";
			qry += "\n          forall i in v_rowid.first..v_rowid.last ";
			qry += "\n          delete from wareadjustm where rowid=v_rowid(i); ";
			qry += "\n          commit; ";
			qry += "\n        end loop; ";
			qry += "\n       close mycursor; ";
			qry += "\n    END; ";

		}
		qry += "\n   insert into wareadjustm (id,accid,noteno,wareid,entersale_0,retailsale_0,sale1_0,sale2_0,sale3_0,sale4_0,sale5_0,entersale,retailsale,sale1,sale2,sale3,sale4,sale5,remark0)";
		qry += "\n   select wareadjustm_id.nextval," + Accid + ",'" + noteno + "',wareid,entersale,retailsale,sale1,sale2,sale3,sale4,sale5,entersale,retailsale,sale1,sale2,sale3,sale4,sale5,''";
		qry += "\n   from warecode a  where a.statetag=1 and a.noused=0 and a.accid=" + Accid;
		if (wareid > 0)
			qry += "  and a.wareid=" + wareid;
		else {
			if (!Func.isNull(wareno))
				qry += " and a.wareno like '%" + wareno + "%'";
			if (!Func.isNull(warename))
				qry += " and a.warename like '%" + warename + "%'";
		}
		if (!Func.isNull(seasonname))
			qry += " and a.seasonname = '" + seasonname + "'";
		if (!Func.isNull(prodyear))
			qry += " and a.prodyear = '" + prodyear + "'";
		if (!Func.isNull(locale))
			qry += " and a.locale like '%" + locale + "%'";
		if (minsale >= 0)
			qry += " and a.retailsale>=" + minsale;
		if (maxsale >= 0)
			qry += " and a.retailsale<=" + maxsale;
		if (provid > 0)
			qry += "  and a.provid=" + provid;
		if (areaid > 0)
			qry += "  and a.areaid=" + areaid;
		if (typeid >= 0)
			if (typeid > 0 && typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.typeid=a.typeid and t.p_typeid= " + typeid + ")";
			} else {
				qry += "    and a.typeid=" + typeid;
			}
		if (brandid >= 0)
			qry += "   and a.brandid=" + brandid;
		if (loadbj == 1) // loadbj:0清空原记录 1追加新记录
		{
			qry += "\n and not exists (select 1 from wareadjustm b where a.accid=b.accid and b.noteno='" + noteno + "'";
			qry += " and a.wareid=b.wareid)";
		}
		qry += "   ;";
		qry += "\n end;";
		int ret = DbHelperSQL.ExecuteSql(qry);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}

	// 成批更改商品调价单的价格
	public int ChangePrice(JSONObject jsonObject) {
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		int priceprec = jsonObject.has("priceprec") ? Integer.parseInt(jsonObject.getString("priceprec")) : 0;

		if (noteno.length() <= 0) {
			errmess = "noteno不是一个有效的值！";
			return 0;
		}
		String tjgs1 = jsonObject.has("tjgs1") ? jsonObject.getString("tjgs1") : "";
		String tjgs2 = jsonObject.has("tjgs2") ? jsonObject.getString("tjgs2") : "";
		String tjgs3 = jsonObject.has("tjgs3") ? jsonObject.getString("tjgs3") : "";
		String tjgs4 = jsonObject.has("tjgs4") ? jsonObject.getString("tjgs4") : "";
		String tjgs5 = jsonObject.has("tjgs5") ? jsonObject.getString("tjgs5") : "";
		String tjgs6 = jsonObject.has("tjgs6") ? jsonObject.getString("tjgs6") : "";
		String tjgs7 = jsonObject.has("tjgs7") ? jsonObject.getString("tjgs7") : "";

		String qry = "declare ";
		qry += "\n    v_count number(10); ";
		qry += "\n begin ";
		qry += "\n   update wareadjustm set lastdate=sysdate";
		if (tjgs1.length() > 0)
			qry += ",entersale=round(" + tjgs1 + "," + priceprec + ")"; // 进价
		if (tjgs2.length() > 0)
			qry += ",retailsale=round(" + tjgs2 + "," + priceprec + ")"; // 进价
		if (tjgs3.length() > 0)
			qry += ",sale1=round(" + tjgs3 + "," + priceprec + ")"; // 进价
		if (tjgs4.length() > 0)
			qry += ",sale2=round(" + tjgs4 + "," + priceprec + ")"; // 进价
		if (tjgs5.length() > 0)
			qry += ",sale3=round(" + tjgs5 + "," + priceprec + ")"; // 进价
		if (tjgs6.length() > 0)
			qry += ",sale4=round(" + tjgs6 + "," + priceprec + ")"; // 进价
		if (tjgs7.length() > 0)
			qry += ",sale5=round(" + tjgs7 + "," + priceprec + ")"; // 进价

		// priceprec:价格精度(小数位)
		qry += "\n    where accid=" + Accid + " and noteno='" + noteno + "';";
		qry += "\n   commit;";
		qry += "\n end;";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 从excel中导入
	public int LoadFromXLS() {
		if (Func.isNull(Noteno)) {
			errmess = "单据号无效！";
			return 0;
		}
		if (Func.isNull(Wareno)) {
			errmess = "货号无效！";
			return 0;
		}
		if (Entersale == null)
			Entersale = (float) 0;
		if (Retailsale == null)
			Retailsale = (float) 0;
		if (Sale1 == null)
			Sale1 = (float) 0;
		if (Sale2 == null)
			Sale2 = (float) 0;
		if (Sale3 == null)
			Sale3 = (float) 0;
		if (Sale4 == null)
			Sale4 = (float) 0;
		if (Sale5 == null)
			Sale5 = (float) 0;
		if (Checksale == null)
			Checksale = (float) 0;

		// Curr =Func.getRound(Amount * Price,2);
		String qry = "declare ";
		qry += "\n    v_wareid  number;";
		qry += "\n    v_price  number;";
		qry += "\n    v_entersale  number;";
		qry += "\n    v_retailsale  number;";
		qry += "\n    v_checksale  number;";
		qry += "\n    v_sale1  number;";
		qry += "\n    v_sale2  number;";
		qry += "\n    v_sale3  number;";
		qry += "\n    v_sale4  number;";
		qry += "\n    v_sale5  number;";
		qry += "\n    v_id  number;";
		qry += "\n    v_retcs  varchar2(100);";
		qry += "\n begin ";
		// qry += "\n v_sale1:=" + Sale1 + ";";
		// qry += "\n v_sale2:=" + Sale2 + ";";
		// qry += "\n v_sale3:=" + Sale3 + ";";
		// qry += "\n v_sale4:=" + Sale4 + ";";
		// qry += "\n v_sale5:=" + Sale5 + ";";
		// qry += "\n v_retailsale:=" + Retailsale + ";";
		// qry += "\n v_entersale:=" + Entersale + ";";
		// qry += "\n v_checksale:=" + Checksale + ";";
		qry += "\n   begin ";
		qry += "\n     select wareid,entersale,retailsale,sale1,sale2,sale3,sale4,sale5,checksale ";
		qry += "\n     into v_wareid,v_entersale,v_retailsale,v_sale1,v_sale2,v_sale3,v_sale4,v_sale5,v_checksale ";
		qry += "\n     from warecode where accid= " + Accid + " and wareno='" + Wareno + "' and statetag=1 and rownum=1;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_retcs:= '0货号" + Wareno + "未找到!' ; ";
		qry += "\n     goto exit; ";
		qry += "\n   end; ";

		qry += "\n    begin ";
		qry += "\n      select id into v_id from wareadjustm where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=v_wareid and rownum=1; ";
		qry += "\n      update wareadjustm set retailsale=" + Retailsale + ",sale1=" + Sale1 + ",sale2=" + Sale2//
				+ ",sale3=" + Sale3 + ",sale4=" + Sale4 + ",sale5=" + Sale5 + ",entersale=" + Entersale + ",checksale=" + Checksale + " where id=v_id;";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN"; // 未找到，挺插入新记录
		qry += "\n      insert into wareadjustm (id,accid,noteno,wareid,entersale_0,retailsale_0,sale1_0,sale2_0,sale3_0,sale4_0,sale5_0,checksale_0"//
				+ ",entersale,retailsale,sale1,sale2,sale3,sale4,sale5,checksale,remark0)";
		qry += "\n      values (wareadjustm_id.nextval," + Accid + ",'" + Noteno + "',v_wareid,v_entersale,v_retailsale,v_sale1,v_sale2,v_sale3,v_sale4,v_sale5,v_checksale"//
				+ "," + Entersale + "," + Retailsale + "," + Sale1 + "," + Sale2 + "," + Sale3 + "," + Sale4 + "," + Sale5 + "," + Checksale + ",'');";
		qry += "\n    end; ";
		qry += "\n    v_retcs:= '1操作成功!'; ";
		qry += "\n    <<exit>>";
		qry += "\n    select v_retcs into :retcs from dual;";
		qry += "\n end; ";
		System.out.println(qry);
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