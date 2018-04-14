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
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-02-28 14:24:30
//*************************************
public class Omsubject implements java.io.Serializable {
	private Long Omid;
	private String Omname;
	private Long Accid;
	private String Begindate;
	private Date Lastdate;
	private String Enddate;
	private String Remark;
	private String Address;
	private String Lastop;
	private String Checkman;
	private Integer Statetag;
	private String Omimagename;
	private String Linkman;
	private String Linktel;
	private String Wxts;
	private String Logoimagename;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setOmid(Long omid) {
		this.Omid = omid;
	}

	public Long getOmid() {
		return Omid;
	}

	public void setOmname(String omname) {
		this.Omname = omname;
	}

	public String getOmname() {
		return Omname;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setBegindate(String begindate) {
		this.Begindate = begindate;
	}

	public String getBegindate() {
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// return df.format(Begindate);
		return Begindate;
	}

	public void setEnddate(String enddate) {
		this.Enddate = enddate;
	}

	public String getEnddate() {
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return Enddate;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setAddress(String address) {
		this.Address = address;
	}

	public String getAddress() {
		return Address;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setCheckman(String checkman) {
		this.Checkman = checkman;
	}

	public String getCheckman() {
		return Checkman;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setOmimagename(String omimagename) {
		this.Omimagename = omimagename;
	}

	public String getOmimagename() {
		return Omimagename;
	}

	public void setLinkman(String linkman) {
		this.Linkman = linkman;
	}

	public String getLinkman() {
		return Linkman;
	}

	public void setLinktel(String linktel) {
		this.Linktel = linktel;
	}

	public String getLinktel() {
		return Linktel;
	}

	public void setWxts(String wxts) {
		this.Wxts = wxts;
	}

	public String getWxts() {
		return Wxts;
	}

	public void setLogoimagename(String logoimagename) {
		this.Logoimagename = logoimagename;
	}

	public String getLogoimagename() {
		return Logoimagename;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }

	// 删除记录
	public int Delete() {
		String qry = "declare";
		qry += "    v_count number(10);";
		qry += "    v_retcs varchar2(200);";
		qry += "\n begin";
		qry += "\n   select count(*) into v_count from ( ";
		qry += "\n   select omid from omwarecode where omid=" + Omid + " and rownum=1";
		qry += "\n   union all";
		qry += "\n   select omid from ompole where omid=" + Omid + " and rownum=1";
		qry += "\n   union all";
		qry += "\n   select omid from omcustomer where omid=" + Omid + " and rownum=1";
		qry += "\n   );";
		qry += "\n   if v_count>0 then ";
		qry += "\n      v_retcs:= '0当前订货会已有内容，不允许删除！' ;";
		qry += "\n      goto exit;";
		qry += "\n   end if;";
		qry += "\n   delete from omsubject where statetag=0 and omid=" + Omid + ";";
		qry += "\n   delete from omcustware where omid=" + Omid + ";";
		qry += "\n   v_retcs:= '1删除成功！';";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";
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

	// 增加订货会记录 ok
	public int Append(String pictjson) {

		if (Omname == null || Begindate == null || Enddate == null || Omname.equals("") || Begindate.equals("") || Enddate.equals("")) {
			errmess = "订货会名称、开始日期、结束日期不允许为空！";
			return 0;
		}
		// if (Begindate != null && Enddate != null) {
		if (Begindate.compareTo(Enddate) > 0) {
			errmess = "订货会结束日期不允许小于开始日期！";
			return 0;
		}
		// LogUtils.LogDebugWrite("append0", pictjson);
		// }
		if (pictjson != null && !pictjson.equals("")) {
			JSONObject jsonObject = JSONObject.fromObject(pictjson);
			String base64string = "";
			// String omimagename = "";
			if (jsonObject.has("omimagename")) {
				base64string = jsonObject.getString("omimagename");
				String retcs = Func.Base64UpFastDFS(base64string);
				if (retcs.substring(0, 1).equals("1"))
					Omimagename = retcs.substring(1);

			}

			// String omimagename = "";
			if (jsonObject.has("logoimagename")) {
				base64string = jsonObject.getString("logoimagename");
				String retcs = Func.Base64UpFastDFS(base64string);
				if (retcs.substring(0, 1).equals("1"))
					Logoimagename = retcs.substring(1);

			}
		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("omid,");
		strSql2.append("v_id,");
		// if (Omid != null) {
		// strSql1.append("omid,");
		// strSql2.append(Omid + ",");
		// }
		// if (Omname != null) {
		strSql1.append("omname,");
		strSql2.append("'" + Omname + "',");
		// }
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		// if (Begindate != null) {
		strSql1.append("begindate,");
		strSql2.append("to_date('" + Begindate + "','yyyy-mm-dd'),");
		// }
		// if (Enddate != null) {
		strSql1.append("enddate,");
		strSql2.append("to_date('" + Enddate + "','yyyy-mm-dd'),");
		// }
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Address != null) {
			strSql1.append("address,");
			strSql2.append("'" + Address + "',");
		}
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		if (Checkman != null) {
			strSql1.append("checkman,");
			strSql2.append("'" + Checkman + "',");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Omimagename != null) {
			strSql1.append("omimagename,");
			strSql2.append("'" + Omimagename + "',");
		}
		if (Linkman != null) {
			strSql1.append("linkman,");
			strSql2.append("'" + Linkman + "',");
		}
		if (Linktel != null) {
			strSql1.append("linktel,");
			strSql2.append("'" + Linktel + "',");
		}
		if (Wxts != null) {
			strSql1.append("wxts,");
			strSql2.append("'" + Wxts + "',");
		}
		if (Logoimagename != null) {
			strSql1.append("logoimagename,");
			strSql2.append("'" + Logoimagename + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=omsubject_omid.nextval; ");
		strSql.append("   insert into omsubject (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("  select v_id into :id from dual; ");
		strSql.append(" end;");
		// LogUtils.LogDebugWrite("append1", strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		LogUtils.LogDebugWrite("append2", "end " + ret);
		// id = Long.parseLong(param.get("id").getParamvalue().toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功!";
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update omsubject set ");
		if (Omid != null) {
			strSql.append("omid=" + Omid + ",");
		}
		if (Omname != null) {
			strSql.append("omname='" + Omname + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Begindate != null) {
			strSql.append("begindate=to_date('" + Begindate + "','yyyy-mm-dd'),");
		}
		if (Enddate != null) {
			strSql.append("enddate=to_date('" + Enddate + "','yyyy-mm-dd'),");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Address != null) {
			strSql.append("address='" + Address + "',");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Checkman != null) {
			strSql.append("checkman='" + Checkman + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Omimagename != null) {
			strSql.append("omimagename='" + Omimagename + "',");
		}
		if (Linkman != null) {
			strSql.append("linkman='" + Linkman + "',");
		}
		if (Linktel != null) {
			strSql.append("linktel='" + Linktel + "',");
		}
		if (Wxts != null) {
			strSql.append("wxts='" + Wxts + "',");
		}
		if (Logoimagename != null) {
			strSql.append("logoimagename='" + Logoimagename + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
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

	// 修改记录 ok
	public int Update(String pictjson) {
		if (Begindate != null && Enddate != null) {
			if (Begindate.compareTo(Enddate) > 0) {
				errmess = "订货会结束日期不允许小于开始日期！";
				return 0;
			}
		}
		if (!pictjson.equals("")) {
			// LogUtils.LogDebugWrite("Update", pictjson);

			String base64string = "";
			JSONObject jsonObject = JSONObject.fromObject(pictjson);

			// LogUtils.LogDebugWrite("updateomsubject0", jsonObject.toString());

			if (jsonObject.has("omimagename")) {
				base64string = jsonObject.getString("omimagename");
				String retcs = Func.Base64UpFastDFS(base64string);
				if (retcs.substring(0, 1).equals("1"))
					Omimagename = retcs.substring(1);
				// LogUtils.LogDebugWrite("Update1", Omimagename);
				//
			}

			// String omimagename = "";
			if (jsonObject.has("logoimagename")) {
				base64string = jsonObject.getString("logoimagename");
				String retcs = Func.Base64UpFastDFS(base64string);
				if (retcs.substring(0, 1).equals("1"))
					Logoimagename = retcs.substring(1);
				// LogUtils.LogDebugWrite("Update2", Logoimagename);

			}
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("\n   update omsubject set ");
		// if (Omid != null) {
		// strSql.append("omid=" + Omid + ",");
		// }
		if (Omname != null) {
			strSql.append("omname='" + Omname + "',");
		}
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		if (Begindate != null) {
			strSql.append("begindate=to_date('" + Begindate + "','yyyy-mm-dd'),");
		}
		if (Enddate != null) {
			strSql.append("enddate=to_date('" + Enddate + "','yyyy-mm-dd'),");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Address != null) {
			strSql.append("address='" + Address + "',");
		}
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
		if (Checkman != null) {
			strSql.append("checkman='" + Checkman + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Linkman != null) {
			strSql.append("linkman='" + Linkman + "',");
		}
		if (Linktel != null) {
			strSql.append("linktel='" + Linktel + "',");
		}
		if (Wxts != null) {
			strSql.append("wxts='" + Wxts + "',");
		}
		if (Logoimagename != null) {
			strSql.append("logoimagename='" + Logoimagename + "',");
		}
		if (Omimagename != null) {
			strSql.append("omimagename='" + Omimagename + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("\n  where omid=" + Omid + " and accid=" + Accid + " ;");
		strSql.append("\n  commit;");
		strSql.append("\n end;");

		// LogUtils.LogDebugWrite("updateomsubject1", strSql.toString());

		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		// LogUtils.LogDebugWrite("updateomsubject2", "返回:" + ret);
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
		strSql.append("\n FROM omsubject a");
		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		System.out.println(strSql.toString());
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM omsubject a");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from omsubject");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}

	// 更改订货会状态ok
	public int doChangeState(int opid) {
		/*
		 * statetag:0=等待 1=筹备2=开始3=暂停4=结束 opid=0 返回等待 1->0 opid=1 筹备中 0->1
		 * opid=2 开始 1->2 opid=3 开始返筹备 2->1 opid=4 暂停 2->3 opid=5 暂停转开始 3->2
		 * opid=6 结束 2->4 opid=7 结束返回 4->2
		 */

		String qry = "declare";
		qry += "\n   v_count number(10);";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n begin";
		if (opid == 0) // 返回等待 1->0
		{
			qry += "\n  update omsubject set statetag=0,lastdate=sysdate,lastop='" + Lastop + "' where accid=" + Accid + " and omid=" + Omid + " and statetag=1;";
		} else if (opid == 1) // 开始筹备 0->1
		{
			qry += "\n  select count(*) into v_count from omsubject where accid=" + Accid + " and statetag>=1 and statetag<=3;";
			qry += "\n  if v_count>0 then";
			qry += "\n     v_retcs:='0当前已有未完成的订货会，不允许开始筹备新订货会！';";
			qry += "\n     goto exit;";
			qry += "\n  end if;";
			qry += "\n  update omsubject set statetag=1,lastdate=sysdate,lastop='" + Lastop + "' where accid=" + Accid + " and omid=" + Omid + " and statetag=0;";
		} else if (opid == 2) // 开始 1->2
		{
			qry += "\n  update omsubject set statetag=2,lastdate=sysdate,lastop='" + Lastop + "' where accid=" + Accid + " and omid=" + Omid + " and statetag=1;";
		} else if (opid == 3) // 开始返筹备 2->1
		{
			qry += "\n  update omsubject set statetag=1,lastdate=sysdate,lastop='" + Lastop + "' where accid=" + Accid + " and omid=" + Omid + " and statetag=2;";
		} else if (opid == 4) // 暂停 2->3
		{
			qry += "\n  update omsubject set statetag=3,lastdate=sysdate,lastop='" + Lastop + "' where accid=" + Accid + " and omid=" + Omid + " and statetag=2;";
		} else if (opid == 5) // 暂停转开始 3->2
		{
			qry += "\n  update omsubject set statetag=2,lastdate=sysdate,lastop='" + Lastop + "' where accid=" + Accid + " and omid=" + Omid + " and statetag=3;";
		} else if (opid == 6) // 结束 2->4
		{
			qry += "\n  update omsubject set statetag=4,lastdate=sysdate,lastop='" + Lastop + "' where accid=" + Accid + " and omid=" + Omid + " and statetag=2;";
		} else if (opid == 7) // 结束返回 4->2
		{
			qry += "\n  select count(*) into v_count from omsubject where accid=" + Accid + " and statetag>=1 and statetag<=3;";
			qry += "\n  if v_count>0 then";
			qry += "\n     v_retcs:= '0当前已有未完成的订货会，不允许将当前订货会返回召开状态！' ;";
			qry += "\n     goto exit;";
			qry += "\n  end if;";
			qry += "\n  update omsubject set statetag=2,lastdate=sysdate,lastop='" + Lastop + "' where accid=" + Accid + " and omid=" + Omid + " and statetag=4;";
		}
		qry += "\n   v_retcs:= '1操作成功！' ; ";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual; ";
		qry += "\n end;";
		//System.out.println(qry);
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

	public String getOminfo() {

		// 取订货商品的波次
		String qry = " select waveid,wavename,to_char(begindate,'mm.dd')||'-'||to_char(enddate,'mm.dd') as daterange from warewave a where a.statetag=1 and a.accid=" + Accid;
		qry += " and exists (select 1 from warecode b join omwarecode c on b.wareid=c.wareid and c.omid=" + Omid;
		qry += " where a.waveid=b.waveid and b.accid=" + Accid + "  and b.statetag=1 and b.noused=0)";
		// WriteDebugTXT("getominfo1", qry);
		String jsonstr = "\"wavelist\":[";
		String fh = "";
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		int rs = tb.getRowCount();

		jsonstr += "{\"msg\":\"操作成功！\",\"WAVEID\":\"0\"" + ",\"WAVENAME\":\"所有\"}";

		for (int i = 0; i < rs; i++) {
			jsonstr += ",{\"WAVEID\":\"" + tb.getRow(i).get("WAVEID").toString() + "\"" //
					+ ",\"WAVENAME\":\"" + tb.getRow(i).get("WAVENAME").toString() + "\"" //
					+ ",\"DATERANGE\":\"" + tb.getRow(i).get("DATERANGE").toString() + "\"}";

			// fh = ",";
		}
		jsonstr += "]";
		// 取订货商品的价格范围
		qry = " select distinct salerange from warecode a";
		qry += " where a.accid=" + Accid + " and a.statetag=1 and a.noused=0";
		qry += " and exists (select 1 from omwarecode b where a.wareid=b.wareid and b.omid=" + Omid + ")";
		// WriteDebugTXT("getominfo2", qry);
		jsonstr += ",\"salerangelist\":[";
		fh = "";
		tb = DbHelperSQL.Query(qry).getTable(1);
		rs = tb.getRowCount();
		jsonstr += "{\"SALERANGE\":\"所有\"}";
		for (int i = 0; i < rs; i++) {
			jsonstr += ",{\"SALERANGE\":\"" + tb.getRow(i).get("SALERANGE").toString() + "\"}";
			// fh = ",";
		}
		jsonstr += "]";
		// 取商品类型
		qry = " select a.typeid,a.typename,a.p_typeid,a1.typename as p_typename from waretype a left outer join waretype a1 on a.p_typeid=a1.typeid and a1.p_typeid=0";
		qry += " where a.statetag=1 and a.noused=0 and (a.accid=0 or a.accid=" + Accid + ")";
		qry += " and exists (select 1 from warecode b join omwarecode c on b.wareid=c.wareid and c.omid=" + Omid;
		qry += " where a.typeid=b.typeid and b.accid=" + Accid + "  and b.statetag=1 and b.noused=0)";
		qry += " order by a.p_typeid,a.typeid";
		// WriteDebugTXT("getominfo3", qry);
		jsonstr += ",\"waretypelist\":[";
		fh = "";
		tb = DbHelperSQL.Query(qry).getTable(1);
		rs = tb.getRowCount();
		for (int i = 0; i < rs; i++) {
			jsonstr += fh + "{\"TYPEID\":\"" + tb.getRow(i).get("TYPEID").toString() + "\"" + ",\"TYPENAME\":\"" + tb.getRow(i).get("TYPENAME").toString() + "\"" + ",\"P_TYPENAME\":\""
					+ tb.getRow(i).get("P_TYPENAME").toString() + "\"}";
			fh = ",";
		}
		jsonstr += "]";

		return jsonstr;

	}

	// 获取当前召开的订货会
	public int doGetOmsubject(String fieldlist, String strWhere) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM omsubject ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		//System.out.println(strSql.toString());
		Table tb = new Table();
		tb = DbHelperSQL.Query(strSql.toString()).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "没有找到正在进行的订货会！";
			return 0;
		}
		errmess = "\"OMID\":" + tb.getRow(0).get("OMID").toString() + ",\"OMNAME\":\"" + tb.getRow(0).get("OMNAME").toString() + "\"";
		return 1;
	}
}