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
//  @author:sunhong  @date:2017-03-14 22:08:03
//*************************************
public class Wareonline1 implements java.io.Serializable {
	private Float Id;
	private Long Onhouseid;
	private Long Wareid;
	private Long Wareid1;
	private Date Lastdate;
	private String Lastop;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setOnhouseid(Long onhouseid) {
		this.Onhouseid = onhouseid;
	}

	public Long getOnhouseid() {
		return Onhouseid;
	}

	public void setWareid(Long wareid) {
		this.Wareid = wareid;
	}

	public Long getWareid() {
		return Wareid;
	}

	public void setWareid1(Long wareid1) {
		this.Wareid1 = wareid1;
	}

	public Long getWareid1() {
		return Wareid1;
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
		if (Onhouseid == 0) {
			errmess = "缺少线上店铺参数！";
			return 0;
		}
		if (Wareid == 0 || Wareid1 == 0) {
			errmess = "缺少商品参数！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append(" delete wareonline1 ");
		strSql.append(" where onhouseid=" + Onhouseid + " and wareid=" + Wareid + " and wareid1=" + Wareid1);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录 ok
	public int Append() {
		if (Onhouseid == 0) {
			errmess = "缺少线上店铺参数！";
			return 0;
		}
		if (Wareid == 0 || Wareid1 == 0) {
			errmess = "缺少商品参数！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_count number(10); ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		strSql.append("\n   select count(*) into v_count from wareonline1 ");
		strSql.append("\n   where onhouseid=" + Onhouseid + " and wareid=" + Wareid + " and wareid1=" + Wareid1 + ";");
		strSql.append("\n   if v_count>0 then ");
		strSql.append("\n      v_retcs:= '1商品已经搭配！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if; ");

		strSql.append("\n   select count(*) into v_count from wareonline1 where onhouseid=" + Onhouseid + " and wareid=" + Wareid + ";");
		strSql.append("\n   if v_count>=3 then ");
		strSql.append("\n      v_retcs:= '1最多允许搭配3款商品！' ; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if; ");
		strSql.append("\n   insert into wareonline1 (id,onhouseid,wareid,wareid1,lastop)");
		strSql.append("\n   values (wareonline1_id.nextval," + Onhouseid + "," + Wareid + "," + Wareid1 + ",'" + Lastop + "');");
		strSql.append("\n   v_retcs:= '1操作成功！' ; ");
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
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Wareonline1 set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Onhouseid != null) {
			strSql.append("onhouseid=" + Onhouseid + ",");
		}
		if (Wareid != null) {
			strSql.append("wareid=" + Wareid + ",");
		}
		if (Wareid1 != null) {
			strSql.append("wareid1=" + Wareid1 + ",");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Wareonline1 ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Wareonline1 ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Wareonline1");
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