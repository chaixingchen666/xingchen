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
import com.common.tool.PinYin;
import com.flyang.main.pFunc;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

//*************************************
//  @author:sunhong  @date:2017-11-15 21:31:48
//*************************************
public class Salepromware implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private String Noteno;
	private Long Wareid;
	private Float Amount;
	private Float Discount;
	private Float Price;
	private Integer Iszp;
	private Date Lastdate;
	private String errmess;
	private String Wareno;

	//private String Accbegindate = "2000-01-01"; // 账套开始使用日期
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

	public void setAmount(Float amount) {
		this.Amount = amount;
	}

	public Float getAmount() {
		return Amount;
	}

	public void setPrice(Float price) {
		this.Price = price;
	}

	public Float getPrice() {
		return Price;
	}

	public void setIszp(Integer iszp) {
		this.Iszp = iszp;
	}

	public Integer getIszp() {
		return Iszp;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	//public void setAccbegindate(String accbegindate) {
	//	  this.Accbegindate = accbegindate;
	//}
	//删除记录 
	public int Delete() {
		StringBuilder strSql = new StringBuilder();
		if (Func.isNull(Noteno) || Wareid == 0) {
			errmess = "noteno或wareid不是一个有效值！";
			return 0;
		}
		strSql.append(" delete SALEPROMware ");
		strSql.append(" where Wareid=" + Wareid + " and iszp=" + Iszp + " and noteno='" + Noteno + "' and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	//增加记录 
	public int Append() {

		if (Func.isNull(Noteno) || Wareid == null || Wareid == 0) {
			errmess = "noteno或wareid不是一个有效值！";
			return 0;
		}
		if (Iszp == null)
			Iszp = 0;

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		//		if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		//		}
		//		if (Noteno != null) {
		strSql1.append("noteno,");
		strSql2.append("'" + Noteno + "',");
		//		}
		//		if (Wareid != null) {
		strSql1.append("wareid,");
		strSql2.append(Wareid + ",");
		//		}
		if (Amount != null) {
			strSql1.append("amount,");
			strSql2.append(Amount + ",");
		}
		if (Price != null) {
			strSql1.append("price,");
			strSql2.append(Price + ",");
		}
		if (Discount != null) {
			strSql1.append("Discount,");
			strSql2.append(Discount + ",");
		}
		//		if (Iszp != null) {
		strSql1.append("iszp,");
		strSql2.append(Iszp + ",");
		//		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");

		strSql.append("\n   select count(*) into v_count from warecode where accid=" + Accid + " and wareid=" + Wareid + " and statetag=1;");
		strSql.append("\n   if v_count=0 then ");
		strSql.append("\n      v_retcs:='0商品无效！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		strSql.append("\n   select count(*) into v_count from SALEPROMware where accid=" + Accid + " and noteno='" + Noteno + "' and Wareid=" + Wareid + " ;");
		strSql.append("\n   if v_count>0 then ");
		strSql.append("\n      v_retcs:='0商品已存在！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		strSql.append("\n   v_id:=SALEPROMware_id.nextval; ");
		strSql.append("\n   insert into SALEPROMware (" + strSql1.toString() + ")");
		strSql.append("\n   values (" + strSql2.toString() + ");");
		strSql.append("\n   v_retcs:='1'||v_id;");
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

		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

	//修改记录 
	public int Update() {
		if (Func.isNull(Noteno) || Wareid == null || Wareid == 0 || Iszp == null) {
			errmess = "noteno或wareid或iszp不是一个有效值！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();

		strSql.append("begin ");
		strSql.append("   update SALEPROMware set ");
		if (Amount != null) {
			strSql.append("amount=" + Amount + ",");
		}
		if (Price != null) {
			strSql.append("price=" + Price + ",");
		}
		if (Iszp != null) {
			strSql.append("iszp=" + Iszp + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where accid=" + Accid + " and wareid=" + Wareid + " and noteno='" + Noteno + "';");
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
		strSql.append("\n FROM SALEPROMware a");
		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
		if (strWhere.length() > 0) {
			strSql.append("\n where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM SALEPROMware a");
		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
		if (strWhere.length() > 0) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from SALEPROMware");
		return false;
	}

	//从excel导入促销商品
	public int LoadfromExcel() {
		if (Func.isNull(Noteno)) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}

		if (Wareno != null && !Wareno.equals(""))
			if (!pFunc.isWarecode(Wareno)) {
				errmess = "货号不是一个有效值！ 货号只允许输入字母、数字及 +/-.#*()";
				return 0;
			}
		if (Iszp == null)
			Iszp = 0;
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		//		strSql.append("wareid=v_wareid,");
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		strSql1.append("noteno,");
		strSql2.append("'" + Noteno + "',");
		strSql1.append("wareid,");
		strSql2.append("v_wareid,");

		if (Amount != null) {
			strSql.append("Amount=" + Amount + ",");
			strSql1.append("Amount,");
			strSql2.append(Amount + ",");
		}

		if (Price != null) {
			strSql.append("Price=" + Price + ",");
			strSql1.append("Price,");
			strSql2.append(Price + ",");
		}
		if (Discount != null) {
			strSql.append("Discount=" + Discount + ",");
			strSql1.append("Discount,");
			strSql2.append(Discount + ",");
		}
		if (Iszp != null) {
			//			strSql.append("Iszp=" + Iszp + ",");
			strSql1.append("Iszp,");
			strSql2.append(Iszp + ",");
		}
		strSql1.append("id,lastdate");
		strSql2.append("salepromware_id.nextval,sysdate");
		strSql.append("lastdate=sysdate");

		String qry = "declare ";
		qry += "\n    v_wareid number(10); ";
		qry += "\n    v_count number(10); ";
		qry += "\n    v_retcs varchar2(200); ";
		qry += "\n    v_id number(20); ";
		qry += "\n begin ";
		qry += "\n    begin";
		qry += "\n      select wareid into v_wareid from warecode where accid=" + Accid + " and statetag=1 and wareno='" + Wareno + "' and noused=0 and rownum=1;";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n      v_retcs:= '0商品" + Wareno + " 未找到！' ;";
		qry += "\n      goto exit;";
		qry += "\n    end;";

		qry += "\n  begin";
		qry += "\n    begin";
		qry += "\n      select id into v_id from salepromware where wareid=v_wareid and noteno='" + Noteno + "' and accid=" + Accid + ";";
		qry += "\n      update salepromware set " + strSql.toString() + " where id=v_id;";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n      insert into salepromware (" + strSql1.toString() + ") values (" + strSql2.toString() + "); ";
		qry += "\n    end;";
		qry += "\n    v_retcs:= '1导入成功！' ;";
		qry += "\n  exception";
		qry += "\n  when others then";
		qry += "\n    v_retcs:='2'||substr(sqlerrm,1,190);";
		qry += "\n  end;";
		qry += "\n  <<exit>>";
		qry += "\n  select v_retcs into :retcs from dual;";
		qry += "\n end ; ";
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
		if (retcs.substring(0, 1).equals("1")) {
			return 1;
		} else {
			if (retcs.substring(0, 1).equals("2"))
				LogUtils.LogErrWrite("loadsalepromwarexls", qry);
			return 0;
		}

	}
}