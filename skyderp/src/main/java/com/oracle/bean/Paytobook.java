package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

//*************************************
//  @author:sunhong  @date:2017-05-17 17:08:05
//*************************************
public class Paytobook implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private Long Houseid;
	private Long Payid;
	private Long Bookid;
	private Long Subid;
	private String Lastop;
	private Date Lastdate;
	private String errmess;
	private Long Progid;
	// --progid:1102,'采购入库' ,1103,'cgth','采购退货',1201,'lskp','零售开票',1302,'xsck','批发开票',1303,'xstk','批发退库',1210,'shopsale','商场零售'

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

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public void setProgid(Long progid) {
		this.Progid = progid;
	}

	public Long getProgid() {
		return Progid;
	}

	public void setPayid(Long payid) {
		this.Payid = payid;
	}

	public Long getPayid() {
		return Payid;
	}

	public void setBookid(Long bookid) {
		this.Bookid = bookid;
	}

	public Long getBookid() {
		return Bookid;
	}

	public void setSubid(Long subid) {
		this.Subid = subid;
	}

	public Long getSubid() {
		return Subid;
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
		if (Id == null || Id == 0) {
			errmess = "id不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("delete PAYTOBOOK ");
		strSql.append(" where id=" + Id + " and accid=" + Accid);
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
		if (Houseid == null || Payid == null || Subid == null || Bookid == null) {
			errmess = "houseid或payid或subid或bookid不是一个有效值！";
			return 0;
		}
		// --progid:1102,'采购入库' ,1103,'cgth','采购退货',1201,'lskp','零售开票',1302,'xsck','批发开票',1303,'xstk','批发退库',1210,'shopsale','商场零售'
		if (Progid == null && (Progid != 1102 || Progid != 1103 || Progid != 1201 || Progid != 1302 || Progid != 1303 || Progid != 1210)) {
			errmess = "progid不是一个有效值！";
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
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Progid != null) {
			strSql1.append("progid,");
			strSql2.append(Progid + ",");
		}
		if (Houseid != null) {
			strSql1.append("houseid,");
			strSql2.append(Houseid + ",");
		}
		if (Payid != null) {
			strSql1.append("payid,");
			strSql2.append(Payid + ",");
		}
		if (Bookid != null) {
			strSql1.append("bookid,");
			strSql2.append(Bookid + ",");
		}
		if (Subid != null) {
			strSql1.append("subid,");
			strSql2.append(Subid + ",");
		}
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n begin ");
		strSql.append("\n   select count(*) into v_count from warehouse where accid=" + Accid + " and houseid=" + Houseid + " and statetag=1 and rownum=1;");
		strSql.append("\n   if v_count=0 then ");
		strSql.append("\n      v_retcs:='0店铺无效！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if; ");
		strSql.append("\n   select count(*) into v_count from payway where accid=" + Accid + " and payid=" + Payid + " and statetag=1 and rownum=1;");
		strSql.append("\n   if v_count=0 then ");
		strSql.append("\n      v_retcs:='0结算方式无效！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if; ");
		strSql.append("\n   select count(*) into v_count from accbook where accid=" + Accid + " and bookid=" + Bookid + " and statetag=1 and rownum=1;");
		strSql.append("\n   if v_count=0 then ");
		strSql.append("\n      v_retcs:='0账本无效！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if; ");

		strSql.append("\n   select count(*) into v_count from subitem where accid=" + Accid + " and subid=" + Subid + " and statetag=1 and rownum=1;");
		strSql.append("\n   if v_count=0 then ");
		strSql.append("\n      v_retcs:='0记账类别无效！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if; ");

		strSql.append("\n   select count(*) into v_count from PAYTOBOOK where accid=" + Accid + " and progid=" + Progid + " and houseid=" + Houseid + "  and payid=" + Payid //
				+ " and bookid=" + Bookid + " and subid=" + Subid + " and rownum=1;");
		strSql.append("\n   if v_count>0 then ");
		strSql.append("\n      v_retcs:='0结算记账关联记录已存在！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if; ");

		strSql.append("\n   v_id:=PAYTOBOOK_id.nextval; ");
		strSql.append("\n   insert into PAYTOBOOK (" + strSql1.toString() + ")");
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

	// 修改记录
	public int Update() {
		if (Id == null || Id == 0) {
			errmess = "id不是一个有效值！";
			return 0;
		}
//		if (Progid != null) {
//			if (Progid != 1102 || Progid != 1103 || Progid != 1201 || Progid != 1302 || Progid != 1303 || Progid != 1210) {
//				errmess = "progid不是一个有效值！";
//				return 0;
//			}
//		}

		if (Houseid == null || Payid == null || Subid == null || Bookid == null) {
			errmess = "houseid或payid或subid或bookid不是一个有效值！";
			return 0;
		}
		// --progid:1102,'采购入库' ,1103,'cgth','采购退货',1201,'lskp','零售开票',1302,'xsck','批发开票',1303,'xstk','批发退库',1210,'shopsale','商场零售'
		if (Progid == null && (Progid != 1102 || Progid != 1103 || Progid != 1201 || Progid != 1302 || Progid != 1303 || Progid != 1210)) {
			errmess = "progid不是一个有效值！";
			return 0;
		}

		
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n begin ");
		if (Houseid != null) {
			strSql.append("\n   select count(*) into v_count from warehouse where accid=" + Accid + " and houseid=" + Houseid + " and statetag=1 and rownum=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0店铺无效！'; ");
			strSql.append("\n      goto exit; ");
			strSql.append("\n   end if; ");
		}
		if (Payid != null) {
			strSql.append("\n   select count(*) into v_count from payway where accid=" + Accid + " and payid=" + Payid + " and statetag=1 and rownum=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0结算方式无效！'; ");
			strSql.append("\n      goto exit; ");
			strSql.append("\n   end if; ");
		}
		if (Bookid != null) {

			strSql.append("\n   select count(*) into v_count from accbook where accid=" + Accid + " and bookid=" + Bookid + " and statetag=1 and rownum=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0账本无效！'; ");
			strSql.append("\n      goto exit; ");
			strSql.append("\n   end if; ");
		}
		if (Subid != null) {

			strSql.append("\n   select count(*) into v_count from subitem where accid=" + Accid + " and subid=" + Subid + " and statetag=1 and rownum=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0记账类别无效！'; ");
			strSql.append("\n      goto exit; ");
			strSql.append("\n   end if; ");
		}
		strSql.append("\n   select count(*) into v_count from PAYTOBOOK where accid=" + Accid + " and houseid=" + Houseid + "  and payid=" + Payid //
				+ " and progid=" +Progid + " and bookid=" + Bookid + " and subid=" + Subid + " and id<>" + Id + " and rownum=1;");
		strSql.append("\n   if v_count>0 then ");
		strSql.append("\n      v_retcs:='0结算记账关联记录已存在！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if; ");

		strSql.append("\n   update PAYTOBOOK set ");
		if (Progid != null) {
			strSql.append("Progid=" + Progid + ",");
		}

		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
		}
		if (Payid != null) {
			strSql.append("payid=" + Payid + ",");
		}
		if (Bookid != null) {
			strSql.append("bookid=" + Bookid + ",");
		}
		if (Subid != null) {
			strSql.append("subid=" + Subid + ",");
		}
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=" + Id + " and accid=" + Accid + " ;");
		strSql.append("\n   v_retcs:='1操作成功！';");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual; ");
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
		return Integer.parseInt(retcs.substring(0, 1));
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM PAYTOBOOK a");
		strSql.append("\n left outer join warehouse b on a.houseid=b.houseid");
		strSql.append("\n left outer join payway c on a.payid=c.payid");
		strSql.append("\n left outer join accbook d on a.bookid=d.bookid");
		strSql.append("\n left outer join subitem e on a.subid=e.subid");
		strSql.append("\n left outer join SYSPROG f on a.progid=f.progid");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM PAYTOBOOK a");
		strSql.append("\n left outer join warehouse b on a.houseid=b.houseid");
		strSql.append("\n left outer join payway c on a.payid=c.payid");
		strSql.append("\n left outer join accbook d on a.bookid=d.bookid");
		strSql.append("\n left outer join subitem e on a.subid=e.subid");
		strSql.append("\n left outer join SYSPROG f on a.progid=f.progid");

		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from PAYTOBOOK");
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