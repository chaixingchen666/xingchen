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
import com.common.tool.JGPush;
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-18 16:03:25
//*************************************
public class Acclink implements java.io.Serializable {
	private Float Id;
	private Long Buyaccid;
	private Long Sellaccid;
	private Integer Tag;
	private Date Applydate;
	private String Request;
	private String Refuse;
	private String Applyop;
	private String Lastop;
	private Date Lastdate;
	private Long Accid;
	private String Tel;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setBuyaccid(Long buyaccid) {
		this.Buyaccid = buyaccid;
	}

	public Long getBuyaccid() {
		return Buyaccid;
	}

	public void setSellaccid(Long sellaccid) {
		this.Sellaccid = sellaccid;
	}

	public Long getSellaccid() {
		return Sellaccid;
	}

	public void setTag(Integer tag) {
		this.Tag = tag;
	}

	public Integer getTag() {
		return Tag;
	}

	public void setApplydate(Date applydate) {
		this.Applydate = applydate;
	}

	public String getApplydate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Applydate);
	}

	public void setRequest(String request) {
		this.Request = request;
	}

	public String getRequest() {
		return Request;
	}

	public void setRefuse(String refuse) {
		this.Refuse = refuse;
	}

	public String getRefuse() {
		return Refuse;
	}

	public void setApplyop(String applyop) {
		this.Applyop = applyop;
	}

	public String getApplyop() {
		return Applyop;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setTel(String tel) {
		this.Tel = tel;
	}

	public String getTel() {
		return Tel;
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
		// strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		// strSql.append(" update acclink set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// } else {
		// errmess = "删除成功！";
		return 1;
		// }
	}

	// 增加记录买家
	public int AppendBuyer(String buyaccname) {
		if (Sellaccid == null || Sellaccid == 0) {
			errmess = "Sellaccid无效";
			return 0;

		}
		if (buyaccname == null || buyaccname.equals("")) {
			errmess = "买家账户无效";
			return 0;
		}
		buyaccname = buyaccname.toUpperCase();

		if (Request == null || Request.equals("")) {
			errmess = "请输入申请信息";
			return 0;
		}

		Table tb = new Table();
		String qry = "select accid from accreg where accname='" + buyaccname + "'";
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "未找到买家账户" + buyaccname;
			return 0;
		}
		Buyaccid = Long.parseLong(tb.getRow(0).get("ACCID").toString());
		if (Sellaccid == Buyaccid) {
			errmess = "不能申请自已的账户作为买家！";
			return 0;
		}

		qry = "select id from acclink where sellaccid=" + Sellaccid + " and buyaccid=" + Buyaccid + " and tag=0";
		if (DbHelperSQL.Exists(qry)) {
			errmess = "申请已发送，请等候回复！";
			return 0;
		}

		qry = "select id from accconnect where sellaccid=" + Sellaccid + " and buyaccid=" + Buyaccid;
		if (DbHelperSQL.Exists(qry)) {
			errmess = "买家账户" + buyaccname + "已存在！";
			return 0;
		}

		qry = "select id from accconnect where buyaccid=" + Sellaccid + " and sellaccid=" + Buyaccid;
		if (DbHelperSQL.Exists(qry)) {
			errmess = buyaccname + "已是我的卖家账户，不允许同时成为买家账户！";
			return 0;
		}

		Applyop = Lastop;
		Accid = Sellaccid;
		Id = Append(); // 返回id
		//System.out.println("id="+Id);
		qry = " select f_getaccmanagerlist(" + Buyaccid + ") as userlist from dual ";
		String userlist = DbHelperSQL.RunSql(qry).toString();
		if (userlist != "") {
			String usertag = "erptag" + Buyaccid;
			String strmess = "您有新的供应商申请信息！";
			JGPush.PushToErp(userlist, usertag, strmess);
		}
		errmess = Id.toString();
		return 1;
	}

	// 增加记录卖家
	public int AppendSeller(String sellaccname) {
		// if (Buyaccid == null || Buyaccid == 0) {
		// errmess = "Buyaccid无效";
		// return 0;
		//
		// }
		if (sellaccname == null || sellaccname.equals("")) {
			errmess = "卖家账户无效";
			return 0;
		}
		sellaccname = sellaccname.toUpperCase();

		if (Request == null || Request.equals("")) {
			errmess = "请输入申请信息";
			return 0;
		}

		Table tb = new Table();
		String qry = "select accid from accreg where accname='" + sellaccname + "'";
		// if (!DbHelperSQL.Exists(qry))
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "未找到卖家账户" + sellaccname;
			return 0;
		}
		Sellaccid = Long.parseLong(tb.getRow(0).get("ACCID").toString());
		// mess = "0";
		if (Sellaccid == Buyaccid) {
			errmess = "不能申请自已的账户作为卖家！";
			return 0;
		}
		qry = "select id from acclink where sellaccid=" + Sellaccid + " and buyaccid=" + Buyaccid + " and tag=0";
		if (DbHelperSQL.Exists(qry)) {
			errmess = "申请已发送，请等候回复！";
			return 0;
		}
		// mess = "1";

		qry = "select id from accconnect where sellaccid=" + Sellaccid + " and buyaccid=" + Buyaccid;
		if (DbHelperSQL.Exists(qry)) {
			errmess = "卖家账户" + sellaccname + "已存在！";
			return 0;
		}
		qry = "select id from accconnect where buyaccid=" + Sellaccid + " and sellaccid=" + Buyaccid;
		if (DbHelperSQL.Exists(qry)) {
			errmess = sellaccname + "已是我的买家账户，不允许同时成为卖家账户！";
			return 0;
		}

		Accid = Buyaccid;
		Applyop = Lastop;

		Id = Append();
		qry = " select f_getaccmanagerlist(" + Sellaccid + ") as userlist from dual ";
		String userlist = DbHelperSQL.RunSql(qry).toString();
		if (userlist != "") {
			String usertag = "erptag" + Sellaccid;
			String strmess = "您有新的加盟商申请信息！";
			JGPush.PushToErp(userlist, usertag, strmess);
		}
		errmess = Id.toString();
		return 1;
	}

	// 增加记录买家
	public float Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		strSql1.append("buyaccid,");
		strSql2.append(Buyaccid + ",");
		strSql1.append("sellaccid,");
		strSql2.append(Sellaccid + ",");
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Tag != null) {
			strSql1.append("tag,");
			strSql2.append(Tag + ",");
		}
		if (Request != null) {
			strSql1.append("request,");
			strSql2.append("'" + Request + "',");
		}
		if (Refuse != null) {
			strSql1.append("refuse,");
			strSql2.append("'" + Refuse + "',");
		}
		if (Applyop != null) {
			strSql1.append("applyop,");
			strSql2.append("'" + Applyop + "',");
		}
		if (Tel != null) {
			strSql1.append("tel,");
			strSql2.append("'" + Tel + "',");
		}
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		strSql1.append("applydate,");
		strSql2.append("sysdate,");
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare");
		strSql.append("\n   v_id number;");
		strSql.append("\n begin");
		strSql.append("\n   v_id:=acclink_id.nextval; ");
		strSql.append("\n   insert into acclink (" + strSql1.toString() + ")");
		strSql.append("\n   values (" + strSql2.toString() + ");");
		strSql.append("\n   select v_id into :id from dual; ");
		strSql.append("\n end;");
		//System.out.println(strSql.toString());

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.FLOAT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			return 0;
		} else {
			return Float.parseFloat(param.get("id").getParamvalue().toString());
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update acclink set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Buyaccid != null) {
			strSql.append("buyaccid=" + Buyaccid + ",");
		}
		if (Sellaccid != null) {
			strSql.append("sellaccid=" + Sellaccid + ",");
		}
		if (Tag != null) {
			strSql.append("tag=" + Tag + ",");
		}
		if (Applydate != null) {
			strSql.append("applydate='" + Applydate + "',");
		}
		if (Request != null) {
			strSql.append("request='" + Request + "',");
		}
		if (Refuse != null) {
			strSql.append("refuse='" + Refuse + "',");
		}
		if (Applyop != null) {
			strSql.append("applyop='" + Applyop + "',");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Tel != null) {
			strSql.append("tel='" + Tel + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
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
	public DataSet GetList(String strWhere, String fieldlist, int fs) {
		StringBuilder strSql = new StringBuilder();
		// strSql.append("select " + fieldlist);
		// strSql.append(" FROM acclink ");
		if (fs == 0) // 我的买家
		{
			strSql.append("select  " + fieldlist + ",b.accname as buyaccname");
			strSql.append(" FROM acclink a");
			strSql.append(" left outer join accreg b on a.buyaccid=b.accid");
		} else // 我的卖家
		{
			strSql.append("select  " + fieldlist + ",b.accname as sellaccname");
			strSql.append(" FROM acclink a");
			strSql.append(" left outer join accreg b on a.sellaccid=b.accid");
		}

		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist, int fs) {
		StringBuilder strSql = new StringBuilder();
		if (fs == 0) {// 我的买家
			strSql.append("select  " + fieldlist + ",b.accname as buyaccname");
			strSql.append(" FROM acclink a");
			strSql.append(" left outer join accreg b on a.buyaccid=b.accid");

		} else {// 我的卖家
			strSql.append("select  " + fieldlist + ",b.accname as sellaccname");
			strSql.append(" FROM acclink a");
			strSql.append(" left outer join accreg b on a.sellaccid=b.accid");

		}
		// strSql.append("select " + fieldlist);
		// strSql.append(" FROM acclink ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from acclink");
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