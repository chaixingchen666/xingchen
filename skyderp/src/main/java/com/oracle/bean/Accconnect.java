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
//import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-16 22:43:01
//*************************************
public class Accconnect implements java.io.Serializable {
	private Float Id;
	private Long Sellaccid;
	private Long Sellcustid;
	private Long Buyaccid;
	private Long Buyprovid;
	private Date Lastdate;
	private Date Firstdate;
	private String errmess;

	public void setRefuse(String refuse) {
		Refuse = refuse;
	}

	public void setLastop(String lastop) {
		Lastop = lastop;
	}

	private String Refuse;
	private String Lastop;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setSellaccid(Long sellaccid) {
		this.Sellaccid = sellaccid;
	}

	public Long getSellaccid() {
		return Sellaccid;
	}

	public void setSellcustid(Long sellcustid) {
		this.Sellcustid = sellcustid;
	}

	public Long getSellcustid() {
		return Sellcustid;
	}

	public void setBuyaccid(Long buyaccid) {
		this.Buyaccid = buyaccid;
	}

	public Long getBuyaccid() {
		return Buyaccid;
	}

	public void setBuyprovid(Long buyprovid) {
		this.Buyprovid = buyprovid;
	}

	public Long getBuyprovid() {
		return Buyprovid;
	}

	public void setFirstdate(Date firstdate) {
		this.Firstdate = firstdate;
	}

	public String getFirstdate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Firstdate);
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Id == 0) {
			errmess = "id参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();

		strSql.append("delete from Accconnect where id=" + Id);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append(int tag) {
		if (Sellaccid == null || Buyaccid == null || Sellaccid == 0 || Buyaccid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		if (tag < 1 || tag > 2) {// tag:1=通过，2=拒绝
			tag = 2;
		}
		if (Refuse == null)
			Refuse = "";
		StringBuilder strSql = new StringBuilder();

		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n   v_count number(10); ");
		strSql.append("\n begin ");
		if (tag == 1) {
			strSql.append("\n  select count(*) into v_count from accconnect where sellaccid=" + Buyaccid + " and buyaccid=" + Sellaccid + ";");
			strSql.append("\n  if v_count>0 then ");
			strSql.append("\n     v_retcs:='0当前增加账户不能同时作为买家和卖家账户！';");
			strSql.append("\n     goto exit;");
			strSql.append("\n  end if;");
			strSql.append("\n   update acclink set tag=" + tag + ",refuse='" + Refuse + "',lastop='" + Lastop + "',lastdate=sysdate ");
			strSql.append("\n      where sellaccid=" + Sellaccid + " and buyaccid=" + Buyaccid + "; ");
			// qry += " if v_tag=1 then ";//通过
			strSql.append("\n    begin ");
			strSql.append("\n       select id into v_id from accconnect where sellaccid=" + Sellaccid + " and buyaccid=" + Buyaccid + ";");
			strSql.append("\n       update accconnect set lastdate=sysdate where id=v_id;");
			strSql.append("\n    EXCEPTION WHEN NO_DATA_FOUND THEN");
			strSql.append("\n       v_id:=accconnect_id.nextval;");
			strSql.append("\n       insert into accconnect (id,sellaccid,buyaccid,sellcustid,buyprovid,lastdate,firstdate)");
			strSql.append("\n       values (v_id," + Sellaccid + "," + Buyaccid + ",0,0,sysdate,trunc(sysdate) );");
			strSql.append("\n    end; ");
			// qry += " end if; ";
			strSql.append("\n    select id into v_id from accconnect where sellaccid=" + Sellaccid + " and buyaccid=" + Buyaccid + ";");
			strSql.append("\n    v_retcs:='1'||v_id;");
			strSql.append("\n    <<exit>> ");
		} else {
			strSql.append("\n   update acclink set tag=" + tag + ",refuse='" + Refuse + "',lastop='" + Lastop + "',lastdate=sysdate ");
			strSql.append("\n   where sellaccid=" + Sellaccid + " and buyaccid=" + Buyaccid + "; ");
			strSql.append("\n   v_retcs:='1操作成功!';");
		}
		strSql.append("\n   select v_retcs into :retcs from dual;");

		strSql.append("\n end; ");
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
			errmess = "id参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("\n   update Accconnect set ");
		// if (Id != null) {
		// strSql.append("id='" + Id + "',");
		// }
		if (Sellaccid != null) {
			strSql.append("sellaccid=" + Sellaccid + ",");
		}
		if (Sellcustid != null) {
			strSql.append("sellcustid=" + Sellcustid + ",");
		}
		if (Buyaccid != null) {
			strSql.append("buyaccid=" + Buyaccid + ",");
		}
		if (Buyprovid != null) {
			strSql.append("buyprovid=" + Buyprovid + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=" + Id + " ;");
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

	// 修改记录
	public int Update1() {
		if (Sellaccid == null || Sellaccid == 0 || Buyaccid == null || Buyaccid == 0) {
			errmess = "id参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Accconnect set ");
		// if (Id != null) {
		// strSql.append("id='" + Id + "',");
		// }
		// if (Sellaccid != null) {
		// strSql.append("sellaccid=" + Sellaccid + ",");
		// }
		if (Sellcustid != null) {
			strSql.append("sellcustid=" + Sellcustid + ",");
		}
		// if (Buyaccid != null) {
		// strSql.append("buyaccid=" + Buyaccid + ",");
		// }
		if (Buyprovid != null) {
			strSql.append("buyprovid=" + Buyprovid + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where Buyaccid=" + Buyaccid + " and Sellaccid=" + Sellaccid + " ;");
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
		strSql.append(" FROM Accconnect ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获得数据列表
	public DataSet GetListBuyer(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Accconnect a");
		strSql.append(" left outer join accreg b on a.buyaccid=b.accid");
		strSql.append(" left outer join customer c on a.sellcustid=c.custid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获得数据列表
	public DataSet GetListSeller(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		// strSql.append(" FROM Accconnect a");
		// strSql.append(" left outer join accreg b on a.sellaccid=b.accid");
		// strSql.append(" left outer join provide c on a.buyprovid=c.provid");

		strSql.append(" FROM accconnect a");
		strSql.append(" left outer join accreg b on a.sellaccid=b.accid");
		strSql.append(" left outer join provide c on a.buyprovid=c.provid");

		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Accconnect ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTableBuyer(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM Accconnect a");
		strSql.append("\n left outer join accreg b on a.buyaccid=b.accid");
		strSql.append("\n left outer join customer c on a.sellcustid=c.custid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTableSeller(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM Accconnect a");
		strSql.append(" left outer join accreg b on a.sellaccid=b.accid");
		strSql.append(" left outer join provide c on a.buyprovid=c.provid");
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
		// strSql.append(" FROM Accconnect ");
		// if (!strWhere.equals("")) {
		// strSql.append(" where " + strWhere);
		// }
		// qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Accconnect");
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