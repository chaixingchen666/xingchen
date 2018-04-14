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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-14 18:36:35
//*************************************
public class Displaycode implements java.io.Serializable {
	private Long Dspid;
	private String Dspname;
	private Long Onhouseid;
	private Integer Statetag;
	private Date Lastdate;
	private String Lastop;
	private Long Orderid;
	private Integer Usetag;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setDspid(Long dspid) {
		this.Dspid = dspid;
	}

	public Long getDspid() {
		return Dspid;
	}

	public void setDspname(String dspname) {
		this.Dspname = dspname;
	}

	public String getDspname() {
		return Dspname;
	}

	public void setOnhouseid(Long onhouseid) {
		this.Onhouseid = onhouseid;
	}

	public Long getOnhouseid() {
		return Onhouseid;
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

	public void setOrderid(Long orderid) {
		this.Orderid = orderid;
	}

	public Long getOrderid() {
		return Orderid;
	}

	public void setUsetag(Integer usetag) {
		this.Usetag = usetag;
	}

	public Integer getUsetag() {
		return Usetag;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Onhouseid == 0 || Dspid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n    v_count number(10);");
		strSql.append("\n    v_retcs varchar2(200);");

		strSql.append("\n begin ");
		strSql.append("\n    select count(*) into v_count from DISPLAYCODE where statetag=1 and onhouseid=" + Onhouseid + ";");
		strSql.append("\n    if v_count<=1 then");
		strSql.append("\n      v_retcs:= '0当前店铺至少要有一种陈列类别！ ';");
		strSql.append("\n      goto exit;");
		strSql.append("\n    end if;");
		strSql.append("\n    delete  from DISPLAYCODE where dspid=" + Dspid + ";");
		strSql.append("\n    delete  from WAREDISPLAY where dspid=" + Dspid + ";");
		strSql.append("\n    v_retcs:= '1删除成功！' ; ");
		strSql.append("\n    <<exit>>");
		strSql.append("\n    select v_retcs into :retcs from dual;");
		strSql.append("\n end; ");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret == 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

	// 增加记录
	public int Append() {

		if (Onhouseid == null || Onhouseid == 0) {
			errmess = "缺少线上店铺参数！";
			return 0;
		}

		if (Dspname == null || Dspname.equals("")) {
			errmess = "请输入陈列名称！";
			return 0;

		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("dspid,");
		strSql2.append("v_id,");

		// if (Dspname != null) {
		strSql1.append("dspname,");
		strSql2.append("'" + Dspname + "',");
		// }
		// if (Onhouseid != null) {
		strSql1.append("onhouseid,");
		strSql2.append(Onhouseid + ",");
		// }
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		if (Orderid != null) {
			strSql1.append("orderid,");
			strSql2.append(Orderid + ",");
		}
		if (Usetag != null) {
			strSql1.append("usetag,");
			strSql2.append(Usetag + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		strSql.append("\n   select count(*) into v_count from DISPLAYCODE where onhouseid=" + Onhouseid + ";");
		strSql.append("\n   if v_count>=20 then ");
		strSql.append("\n      v_retcs:='0每个店铺陈列类别最多允许20个！'; ");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   v_id:=DISPLAYCODE_dspid.nextval; ");
		strSql.append("\n   insert into DISPLAYCODE (");
		strSql.append("  " + strSql1.toString());
		strSql.append(" )");
		strSql.append("\n values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(" );");
		strSql.append("\n   v_retcs:='1'||v_id ; ");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		strSql.append("\n end;");
		System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret == 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));

	}

	// 修改记录
	public int Update() {
		if (Dspid == 0) {
			errmess = "陈列类别无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update DISPLAYCODE set ");
		// if (Dspid != null) {
		// strSql.append("dspid=" + Dspid + ",");
		// }
		if (Dspname != null) {
			strSql.append("dspname='" + Dspname + "',");
		}
		// if (Onhouseid != null) {
		// strSql.append("onhouseid=" + Onhouseid + ",");
		// }
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
		if (Orderid != null) {
			strSql.append("orderid=" + Orderid + ",");
		}
		if (Usetag != null) {
			strSql.append("usetag=" + Usetag + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where Dspid=" + Dspid + " ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "修改失败！";
			return 0;
		} else {
			errmess = "修改成功！";
			return 1;
		}
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM DISPLAYCODE ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM DISPLAYCODE ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取所有数据
	public Table GetTable(String strWhere, String fieldlist, String sort) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM DISPLAYCODE ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		if (!sort.equals("")) {
			strSql.append(" order by " + sort);
		}

		return DbHelperSQL.GetTable(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(String qry) {
		// StringBuilder strSql = new StringBuilder();
		// strSql.append("select " + fieldlist);
		// strSql.append(" FROM DISPLAYCODE ");
		// if (!strWhere.equals("")) {
		// strSql.append(" where " + strWhere);
		// }
		// qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qry);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from DISPLAYCODE");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 调整陈列类别顺序
	public int Sort(JSONObject jsonObject) {
		if (Onhouseid == 0) {
			errmess = "线上店铺id无效！";
			return 0;
		}
		// int orderid;
		String qry = " declare";
		qry += "\n   v_count number;";
		qry += "\n begin";
		qry += "\n   update Displaycode set orderid=99 where onhouseid=" + Onhouseid + " ;";
		if (jsonObject.has("dspidlist")) {
			JSONArray jsonArray = jsonObject.getJSONArray("dspidlist");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				long dspid = Long.parseLong(jsonObject2.getString("dspid"));
				qry += "\n   update Displaycode set orderid=" + (i + 1) + " where dspid=" + dspid + ";";
			}
		}
		qry += "\n end ;";
		int ret = DbHelperSQL.ExecuteSql(qry);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

}