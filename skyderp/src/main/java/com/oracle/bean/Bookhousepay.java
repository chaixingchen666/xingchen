package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-02 17:55:31
//*************************************
public class Bookhousepay implements java.io.Serializable {

	private Float Id;//1210=商场零售

	private Long Lbid;//0=收款，1=付款
	private Long Houseid;
	private Long Payid;
	private Long Bookid;
	private Long Subid;
	private Long Accid;
	//	private Date Lastdate;
	private String errmess;
	private String Lastop;
	//	private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setId(Float id) {
		Id = id;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
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

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	public void setLastop(String lastop) {
		Lastop = lastop;
	}

	// 删除记录
	public int Delete() {
		StringBuilder strSql = new StringBuilder();

		strSql.append(" delete bookhousepay ");
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
		if (Bookid == null || Lbid == null || Houseid == null || Payid == null || Subid == null) {
			errmess = "参数无效！";
			return 0;
		}
//		BOOKHOUSEPAY->lbid:0=收款，1=付款
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");

		strSql.append("\n    select count(*) into v_count from payway where payid=" + Payid + " and accid=" + Accid + " and statetag=1;");
		strSql.append("\n    if v_count=0 then ");
		strSql.append("\n       v_retcs:='0结算方式无效！';");
		strSql.append("\n       goto exit;");
		strSql.append("\n    end if;");

		//		strSql.append("\n  begin");  唯一索引:LBID, HOUSEID, PAYID
		strSql.append("\n    select count(*) into v_count from bookhousepay where lbid=" + Lbid + " and houseid=" + Houseid + " and payid=" + Payid + ";");
		strSql.append("\n    if v_count>0 then ");
		strSql.append("\n       v_retcs:='0结算方式已绑定其它账本！';");
		strSql.append("\n       goto exit;");
		strSql.append("\n    end if;");

		strSql.append("\n    select count(*) into v_count from accbook where Bookid=" + Bookid + " and accid=" + Accid + " and statetag=1;");
		strSql.append("\n    if v_count=0 then ");
		strSql.append("\n       v_retcs:='0账本无效！';");
		strSql.append("\n       goto exit;");
		strSql.append("\n    end if;");

		strSql.append("\n    select count(*) into v_count from subitem where Subid=" + Subid + " and (accid=" + Accid + " or accid=0) and statetag=1;");
		strSql.append("\n    if v_count=0 then ");
		strSql.append("\n       v_retcs:='0记帐项目无效！';");
		strSql.append("\n       goto exit;");
		strSql.append("\n    end if;");

		//		
		//		strSql.append("\n    update bookhousepay set houseid=" + Houseid + ",payid=" + Payid + ",subid=" + Subid);
		//		strSql.append("\n    ,bookid=" + Bookid + ",accid=" + Accid + ",lastop='" + Lastop + "',lastdate=sysdate");
		//		strSql.append("\n    where id=v_id;");
		//		strSql.append("\n  EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n    v_id:=bookhousepay_id.nextval; ");
		strSql.append("\n    insert into bookhousepay (id,accid,lbid,bookid,houseid,payid,subid,lastop,lastdate)");
		strSql.append("\n    values (v_id," + Accid + "," + Lbid + "," + Bookid + "," + Houseid + "," + Payid + "," + Subid + ",'" + Lastop + "',sysdate);");
		//		strSql.append("\n  end;");
		strSql.append("\n    v_retcs:='1'||v_id;");
		strSql.append("\n    <<exit>>");
		strSql.append("\n    select v_retcs into :retcs from dual; ");
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
		if (Lbid == null || Houseid == null || Payid == null) {
			errmess = "参数无效！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");

		if (Bookid != null) {
			strSql.append("\n    select count(*) into v_count from accbook where Bookid=" + Bookid + " and accid=" + Accid + " and statetag=1;");
			strSql.append("\n    if v_count=0 then ");
			strSql.append("\n       v_retcs:='0账本无效！';");
			strSql.append("\n       goto exit;");
			strSql.append("\n    end if;");
		}
		if (Subid != null) {
			strSql.append("\n    select count(*) into v_count from subitem where Subid=" + Subid + " and (accid=" + Accid + " or accid=0) and statetag=1;");
			strSql.append("\n    if v_count=0 then ");
			strSql.append("\n       v_retcs:='0记帐项目无效！';");
			strSql.append("\n       goto exit;");
			strSql.append("\n    end if;");
		}
		strSql.append("\n   update bookhousepay set ");

		if (Bookid != null) {
			strSql.append("bookid=" + Bookid + ",");
		}
		if (Subid != null) {
			strSql.append("subid=" + Subid + ",");
		}
		strSql.append("lastdate=sysdate,lastop='" + Lastop + "'");
		strSql.append("\n  where Accid=" + Accid + " and lbid=" + Lbid);
		if (Id != null)
			strSql.append("\n and id=" + Id);
		strSql.append("\n  and Houseid=" + Houseid + " and payid=" + Payid + " ;");
		strSql.append("\n  commit;");
		strSql.append("\n    v_retcs:='1操作成功！';");
		strSql.append("\n    <<exit>>");
		strSql.append("\n    select v_retcs into :retcs from dual; ");
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

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM bookhousepay a");
		strSql.append("\n left outer join payway b on a.payid=b.payid");
		strSql.append("\n left outer join accbook c on a.bookid=c.bookid");
		strSql.append("\n left outer join SUBITEM d on a.subid=d.subid");
		//		strSql.append("\n left outer join SYSPROG f on a.progid=f.progid");

		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM bookhousepay a");
		strSql.append("\n left outer join payway b on a.payid=b.payid");
		strSql.append("\n left outer join accbook c on a.bookid=c.bookid");
		strSql.append("\n left outer join SUBITEM d on a.subid=d.subid");
		//		strSql.append("\n left outer join SYSPROG f on a.progid=f.progid");
		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from bookhousepay");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}
}