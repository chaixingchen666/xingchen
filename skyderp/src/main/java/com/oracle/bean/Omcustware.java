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
import com.common.tool.PinYin;
////import com.common.tool.SizeParam;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-02-22 08:53:34
//*************************************
public class Omcustware implements java.io.Serializable {
	private Long Id;
	private Long Omid;
	private Long Omepid;
	private Long Wareid;
	private Long Colorid;
	private Long Sizeid;
	private Long Amount;
	private Date Lastdate;
	private String Lastop;
	private String errmess;
	private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	private String Findbox;
	private Long Accid;
	//	private Long Userid;

	private String Custidlist = "";
	private String Poleidlist = "";

	public void setId(Long id) {
		this.Id = id;
	}

	public void setCustidlist(String custidlist) {
		Custidlist = custidlist;
	}

	public void setPoleidlist(String poleidlist) {
		Poleidlist = poleidlist;
	}

	public Long getId() {
		return Id;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	//	public void setUserid(Long userid) {
	//		this.Userid = userid;
	//	}

	public void setOmid(Long omid) {
		this.Omid = omid;
	}

	public Long getOmid() {
		return Omid;
	}

	public void setOmepid(Long omepid) {
		this.Omepid = omepid;
	}

	public Long getOmepid() {
		return Omepid;
	}

	public void setWareid(Long wareid) {
		this.Wareid = wareid;
	}

	public Long getWareid() {
		return Wareid;
	}

	public void setColorid(Long colorid) {
		this.Colorid = colorid;
	}

	public Long getColorid() {
		return Colorid;
	}

	public void setSizeid(Long sizeid) {
		this.Sizeid = sizeid;
	}

	public Long getSizeid() {
		return Sizeid;
	}

	public void setAmount(Long amount) {
		this.Amount = amount;
	}

	public Long getAmount() {
		return Amount;
	}

	public void setLastdate(Date lastdate) {
		this.Lastdate = lastdate;
	}

	public String getLastdate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Lastdate);
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public void setFindbox(String findbox) {
		this.Findbox = findbox;
	}

	public String getLastop() {
		return Lastop;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	public void setAccbegindate(String accbegindate) {
		this.Accbegindate = accbegindate;
	}

	// 删除记录
	public int Delete() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前记录已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		strSql.append(" update Omcustware set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	// 增加记录
	public int Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		if (Id != null) {
			strSql1.append("id,");
			strSql2.append(Id + ",");
		}
		if (Omid != null) {
			strSql1.append("omid,");
			strSql2.append(Omid + ",");
		}
		if (Omepid != null) {
			strSql1.append("omepid,");
			strSql2.append(Omepid + ",");
		}
		if (Wareid != null) {
			strSql1.append("wareid,");
			strSql2.append(Wareid + ",");
		}
		if (Colorid != null) {
			strSql1.append("colorid,");
			strSql2.append(Colorid + ",");
		}
		if (Sizeid != null) {
			strSql1.append("sizeid,");
			strSql2.append(Sizeid + ",");
		}
		if (Amount != null) {
			strSql1.append("amount,");
			strSql2.append(Amount + ",");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=Omcustware_id.nextval; ");
		strSql.append("   insert into Omcustware (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("  select v_id into :id from dual; ");
		strSql.append(" end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		// id = Long.parseLong(param.get("id").getParamvalue().toString());
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 修改记录
	public int Update(long userid, JSONObject jsonObject) {
		StringBuilder strSql = new StringBuilder();
		long sizeid;
		long colorid;
		float amount;

		strSql.append("declare ");
		strSql.append("\n    v_id  number;  ");
		strSql.append("\n    v_retcs  varchar2(100);  ");
		strSql.append("\n    v_maxordamt  number;  ");  //最大订货数
		strSql.append("\n    v_totalordamt  number;  ");  //已订货数
		strSql.append("\n begin ");
		strSql.append("\n    select PERMITAMT into v_maxordamt from omwarecode where omid=" + Omid + " and wareid=" + Wareid + ";");

		if (Omid > 0) {
			strSql.append("\n   select count(*) into v_id from omorder where omepid=" + userid + " and omid=" + Omid + ";");
			strSql.append("\n   if v_id>0 then ");
			strSql.append("\n      v_retcs:='0您的订货已生成订单！' ; ");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		strSql.append("\n   delete from omcustware where omepid=" + userid + " and omid=" + Omid + " and wareid=" + Wareid + "; ");
		JSONArray jsonArray = jsonObject.getJSONArray("orderlist");

		for (int i = 0; i < jsonArray.size(); i++)

		{
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);

			// WriteDebugTXT("writeomcustware1.0", i.toString());

			sizeid = jsonObject2.has("sizeid") ? Long.parseLong(jsonObject2.getString("sizeid")) : 0;
			colorid = jsonObject2.has("colorid") ? Long.parseLong(jsonObject2.getString("colorid")) : 0;
			amount = jsonObject2.has("amount") ? Float.parseFloat(jsonObject2.getString("amount")) : 0;
			if (amount > 0) {
				strSql.append("\n  begin ");
				strSql.append("\n    select id into v_id from omcustware where omepid=" + userid + " and omid=" + Omid + " and wareid=" + Wareid + " and colorid=" + colorid + " and sizeid=" + sizeid + " and rownum=1; ");
				strSql.append("\n    update omcustware set amount=amount+" + amount + ",lastop='" + Lastop + "',lastdate=sysdate where id=v_id;");
				strSql.append("\n  EXCEPTION WHEN NO_DATA_FOUND THEN"); // 未找到，插入新记录
				strSql.append("\n    insert into omcustware (id,omepid,omid,wareid,colorid,sizeid,amount,lastdate,lastop)");
				strSql.append("\n    values (omcustware_id.nextval," + userid + "," + Omid + "," + Wareid + " ," + colorid + "," + sizeid + "," + amount + ",sysdate,'" + Lastop + "');");
				strSql.append("\n  end; ");
			}
			// WriteDebugTXT("writeomcustware1.2", qry);
		}
		strSql.append("\n   delete from omcustware where omepid=" + userid + " and omid=" + Omid + " and wareid=" + Wareid + " and amount<=0; ");
		strSql.append("\n   if v_maxordamt>0 then"); //如果有最大订货数，要判断订货数是否超过最大订货数
		strSql.append("\n      select nvl(sum(amount),0) into v_totalordamt from omcustware where omid=" + Omid + " and wareid=" + Wareid + ";");
		strSql.append("\n      if v_totalordamt>v_maxordamt then");
		strSql.append("\n         v_totalordamt:=v_totalordamt-v_maxordamt;");
		strSql.append("\n         v_retcs:= '0订货数已超出允许订货数'||v_totalordamt||'件!' ; ");
		strSql.append("\n         rollback;");
		strSql.append("\n         goto exit;");
		strSql.append("\n      end if;");
		strSql.append("\n   end if;");
		strSql.append("\n   commit; ");
		strSql.append("\n   v_retcs:= '1操作完成' ; ");

		strSql.append("\n   <<exit>> ");
		strSql.append("\n   select v_retcs into :retcs from dual; ");

		strSql.append("\n end; ");
		// LogUtils.LogDebugWrite("WriteOmcustware", strSql.toString());

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作失败！";
			return 0;
		}

		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);
		if (retcs.substring(0, 1).equals("0"))
			return 0;
		else
			return 1;
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Omcustware ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Omcustware ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	// public Table GetTable(QueryParam qp) {
	//
	// return DbHelperSQL.GetTable(qp);
	// }

	public String GetTable2Json(QueryParam qp, String custidlist, int sizenum) {
		Table tb = new Table();
		//System.out.println("GetTable2Json 1 " + qp.getSumString());
		tb = DbHelperSQL.GetTable(qp);
		//System.out.println("GetTable2Json 2 " + qp.getTotalString());
		String sqlsize = "";
		String sqlsum = "";
		String qry1;
		String fh = "";
		long wareid;
		long wareid0 = 0;
		long colorid = 0;

		String jsonstr = "{\"result\":1,\"msg\":\"�����ɹ���\"," + qp.getTotalString() + ",\"rows\":[";
		//System.out.println("GetTable2Json 3 " + jsonstr);

		int count = tb.getRowCount();
		for (int i = 0; i < count; i++) {
			wareid = Long.parseLong(tb.getRow(i).get("WAREID").toString());
			//System.out.println("GetTable2Json 4 wareid=" + wareid);
			// if (hzfs == 1) colorid = long.Parse(tb.getRows(i).get("colorid"].toString());
			if (wareid != wareid0) {
				// sqlsize = "";
				SizeParam sp = new SizeParam(wareid, sizenum, 0);
				// sp.setWareid(wareid);
				// sp.setSizenum(sizenum);
				//System.out.println("qrySizesum begin");
				// Func.qrySizesum(sp);
				//System.out.println("qrySizesum end");

				sqlsize = sp.getSqlSize();
				sqlsum = sp.getSqlSum();

				//System.out.println("sqlsize=" + sqlsize + "\n sqlsum=" + sqlsum);

				wareid0 = wareid;
			}
			jsonstr += fh + "{\"WAREID\":\"" + wareid + "\"" //
					+ ",\"WARENAME\":\"" + tb.getRow(i).get("WARENAME").toString() + "\""//
					+ ",\"WARENO\":\"" + tb.getRow(i).get("WARENO").toString() + "\"" //
					+ ",\"UNITS\":\"" + tb.getRow(i).get("UNITS").toString() + "\"" //
					+ ",\"IMAGENAME\":\"" + tb.getRow(i).get("IMAGENAME").toString() + "\""//
					+ ",\"SIZEGROUPNO\":\"" + tb.getRow(i).get("SIZEGROUPNO").toString() + "\"" //
					+ ",\"AMOUNT\":\"" + tb.getRow(i).get("AMOUNT").toString() + "\"" //
					+ ",\"RETAILSALE\":\"" + tb.getRow(i).get("RETAILSALE").toString() + "\""//
					+ ",\"RETAILCURR\":\"" + tb.getRow(i).get("RETAILCURR").toString() + "\""//
					+ ",\"ROWNUMBER\":\"" + tb.getRow(i).get("ROWNUMBER").toString() + "\"";

			jsonstr += ",\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"" //
					+ ",\"COLORNAME\":\"" + tb.getRow(i).get("COLORNAME").toString() + "\"";

			// 取尺码数据
			qry1 = "select " + sqlsum + "," + sqlsize;
			qry1 += " from omcustware a where a.omid=" + Omid;

			// if (custid > 0)
			// qry1 += " and a.custid=" + custid;
			qry1 += " and a.wareid=" + wareid;
			// qry += " and a.omepid="+htp.userid;
			// if (custid > 0)
			// qry1 += " and exists (select 1 from omuser c where a.omepid=b.omepid and c.custid=" + custid + ")";
			if (!custidlist.equals(""))
				// qry += " and a.custid=" + custid;
				qry1 += " and exists (select 1 from omuser c where a.omepid=c.omepid and " + Func.getCondition("c.custid", custidlist) + ")";

			if (colorid > 0)
				qry1 += " and a.colorid=" + colorid;

			//System.out.println(qry1);

			Table tb1 = DbHelperSQL.Query(qry1).getTable(1);
			for (int j = 1; j <= sizenum; j++) {
				jsonstr += ",\"AMOUNT" + j + "\":\"" + tb1.getRow(0).get("AMOUNT" + j).toString() + "\"" + ",\"SIZEID" + j + "\":\"" + tb1.getRow(0).get("SIZEID" + j).toString() + "\"" + ",\"SIZENAME" + j + "\":\""
						+ tb1.getRow(0).get("SIZENAME" + j).toString() + "\"";
			}
			jsonstr += "}";
			fh = ",";

		}
		jsonstr += "]}";

		return jsonstr;
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Omcustware");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}

	// 实时查询 手机new
	public Table GetTable(QueryParam qp) {
		String qry = "select a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.sizegroupno,b.imagename";
		qry += ",sum(a.amount) as amount,sum(a.amount*b.retailsale) as retailcurr";
		qry += "\n from omcustware a ";
		qry += "\n left outer join warecode b on a.wareid=b.wareid";
		qry += "\n left outer join colorcode c on a.colorid=c.colorid";
		qry += " where a.omid=" + Omid;
		if (Custidlist.length() > 0) {
			qry += " and exists (select 1 from omuser d where a.omepid=d.omepid ";
			qry += " and " + Func.getCondition("d.custid", Custidlist);
			qry += ")";
		}
		if (Poleidlist.length() > 0) {
			qry += " and exists (select 1 from ompoleware e where a.wareid=e.wareid ";
			qry += " and " + Func.getCondition("e.poleid", Poleidlist);
			qry += ")";
		}
		if (!Findbox.equals(""))
			qry += " and (b.warename like '%" + Findbox + "%' or b.wareno like '%" + Findbox.toUpperCase() + "%')";
		qry += "\n group by a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.sizegroupno,b.imagename";
		// qry += "\n ,a.colorid,c.colorname";
		qp.setQueryString(qry);
		qp.setSumString(" nvl(sum(amount),0) as amount,nvl(sum(retailcurr),0) as retailcurr,count(distinct wareid) as num");

		return DbHelperSQL.GetTable(qp);
	}

	// 按陈列查询订货会商品
	public Table GetTable1(QueryParam qp) {

		String qry = "select a.poleid,a.polename,sum(c.amount) as amount";
		qry += " from ompole a";
		qry += " join ompoleware b on a.poleid=b.poleid";
		qry += " join OMCUSTWARE c on a.omid=c.omid and b.wareid=c.wareid";
		qry += " where a.omid=" + Omid;
		if (Custidlist.length() > 0) {
			qry += " and exists (select 1 from omuser d where b.omepid=d.omepid ";
			qry += " and " + Func.getCondition("d.custid", Custidlist);
			qry += ")";
		}
		qry += " group by a.poleid,a.polename";

		qp.setQueryString(qry);
		// qp.setSumString(" nvl(sum(amount),0) as totalamt");
		// qp.setCalcfield("round(amount/[totalamt],2) as zbrate");
		return DbHelperSQL.GetTable(qp);
	}

	// 实时查询 手机
	public String doListOmcustwaretotalsize(QueryParam qp, int sizenum, String custidlist) {
		String qry = "select a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.sizegroupno,b.imagename";
		qry += ",a.colorid,c.colorname";
		qry += ",sum(a.amount) as amount,sum(a.amount*b.retailsale) as retailcurr";
		qry += "\n from omcustware a ";
		qry += "\n left outer join warecode b on a.wareid=b.wareid";
		qry += "\n left outer join colorcode c on a.colorid=c.colorid";
		qry += " where a.omid=" + Omid;
		if (!custidlist.equals("")) {
			qry += " and exists (select 1 from omuser d where a.omepid=d.omepid ";
			qry += " and " + Func.getCondition("d.custid", custidlist);
			qry += ")";
		}
		if (!Findbox.equals(""))
			qry += " and (b.warename like '%" + Findbox + "%' or b.wareno like '%" + Findbox.toUpperCase() + "%')";
		qry += "\n group by a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.sizegroupno,b.imagename";
		qry += "\n ,a.colorid,c.colorname";
		qp.setQueryString(qry);
		qp.setSumString(" nvl(sum(amount),0) as amount,nvl(sum(retailcurr),0) as retailcurr,count(distinct wareid) as num");
		Table tb = new Table();
		tb = DbHelperSQL.GetTable(qp);
		String sqlsize = "";
		String sqlsum = "";
		String qry1;
		String fh = "";
		long wareid;
		long wareid0 = 0;
		long colorid = 0;
		String retcs = "{\"result\":1,\"msg\":\"操作成功！\"," + qp.getTotalString() + ",\"rows\":[";
		int count = tb.getRowCount();
		for (int i = 0; i < count; i++) {
			wareid = Long.parseLong(tb.getRow(i).get("WAREID").toString());
			colorid = Long.parseLong(tb.getRow(i).get("COLORID").toString());
			if (wareid != wareid0) {
				sqlsize = "";

				SizeParam sp = new SizeParam(wareid, sizenum, 0);
				sqlsum = sp.getSqlSum();
				sqlsize = sp.getSqlSize();
				wareid0 = wareid;
			}
			retcs += fh + "{\"WAREID\":\"" + wareid + "\"" + ",\"WARENAME\":\"" + tb.getRow(i).get("WARENAME").toString() + "\"" + ",\"WARENO\":\"" + tb.getRow(i).get("WARENO").toString() + "\"" + ",\"UNITS\":\""
					+ tb.getRow(i).get("UNITS").toString() + "\"" + ",\"SIZEGROUPNO\":\"" + tb.getRow(i).get("SIZEGROUPNO").toString() + "\"" + ",\"AMOUNT\":\"" + tb.getRow(i).get("AMOUNT").toString() + "\""
					// + ",\"NUM\":\"" + tb.getRow(i).get("NUM").toString() + "\""
					+ ",\"RETAILSALE\":\"" + tb.getRow(i).get("RETAILSALE").toString() + "\"" + ",\"RETAILCURR\":\"" + tb.getRow(i).get("RETAILCURR").toString() + "\"" + ",\"IMAGENAME\":\""
					+ tb.getRow(i).get("IMAGENAME").toString() + "\"" + ",\"ROWNUMBER\":\"" + tb.getRow(i).get("ROWNUMBER").toString() + "\"";
			retcs += ",\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"" + ",\"COLORNAME\":\"" + tb.getRow(i).get("COLORNAME").toString() + "\"";
			// 取尺码数据
			qry1 = "select " + sqlsum + "," + sqlsize;
			qry1 += " from omcustware a where a.omid=" + Omid;

			qry1 += " and a.wareid=" + wareid;
			// if (colorid > 0)
			qry1 += " and a.colorid=" + colorid;
			if (!custidlist.equals("")) {
				qry1 += " and exists (select 1 from omuser c where a.omepid=c.omepid";
				qry1 += " and " + Func.getCondition("c.custid", custidlist);
				qry1 += ")";
			}
			//System.out.println(qry1);

			Table tb1 = DbHelperSQL.Query(qry1).getTable(1);
			for (int j = 1; j <= sizenum; j++) {
				retcs += ",\"AMOUNT" + j + "\":\"" + tb1.getRow(0).get("AMOUNT" + j).toString() + "\"" + ",\"SIZEID" + j + "\":\"" + tb1.getRow(0).get("SIZEID" + j).toString() + "\"" + ",\"SIZENAME" + j + "\":\""
						+ tb1.getRow(0).get("SIZENAME" + j).toString() + "\"";
			}
			retcs += "}";
			fh = ",";

		}
		retcs += "]}";

		return retcs;
	}

	// 显示订货商品排行榜(订货会app用)
	public Table listOmcustwaresort(QueryParam qp, String findbox) {
		String qry = " select a.wareid,b.wareno,b.warename,b.units,b.tjtag,c.mustbj,a.amount,a.tamount,b.retailsale,a.amount*b.retailsale as retailcurr,b.imagename from (";
		qry += "\n select wareid,sum(amount) as amount,sum(tamount) as tamount from (";
		qry += "\n select a.wareid,sum(a.amount) as amount,0 as tamount ";
		qry += "\n from omcustware a  join warecode b on a.wareid=b.wareid ";

		qry += "\n where a.omid=" + Omid;
		qry += "\n and a.omepid=" + Omepid;
		qry += "\n and exists (select 1 from omwarecode d where a.omid=d.omid and a.wareid=d.wareid and d.noused=0)";
		qry += "\n group by a.wareid ";
		qry += "\n union all ";
		qry += "\n select a.wareid,0 as amount,sum(amount) as tamount  ";
		qry += "\n from omcustware a join warecode b on a.wareid=b.wareid where a.omid=" + Omid;
		// if (findbox != "") qry += " and (b.wareno like '%" + findbox.ToUpper() + "%' or b.warename like '%" + findbox + "%')";
		qry += "\n and exists (select 1 from omwarecode d where a.omid=d.omid and a.wareid=d.wareid and d.noused=0)";

		qry += "\n group by a.wareid ) ";
		qry += "\n group by wareid  ";
		qry += " )  a join warecode b on a.wareid=b.wareid ";
		qry += "\n join omwarecode c on c.omid=" + Omid + " and a.wareid=c.wareid";

		if (!findbox.equals(""))
			qry += " where (b.wareno like '%" + findbox.toUpperCase() + "%' or b.warename like '%" + findbox + "%')";
		qp.setQueryString(qry);

		return DbHelperSQL.GetTable(qp);

	}

	// 订货会汇总分析

	public String totalOmcustware(long custid, String omepidlist, int hzfs, long levelid) {
		//System.out.println("000: omepidlist=" + omepidlist);
		Table tb = new Table();
		// 总计划数
		String qry = "select  nvl(sum(jhcurr),0) as jhcurr,nvl(sum(jhamt),0) as jhamt,nvl(sum(jhskc),0) as jhskc,nvl(sum(jhnum),0) as jhnum from omplan a ";
		qry += " where a.omid=" + Omid;
		// qry+=" and a.omepid=" + htp.userid;
		if (levelid == 0) // 公司本部
		{
			//System.out.println("omepidlist 111");
			if (!omepidlist.equals(""))
				qry += " and " + Func.getCondition("a.omepid", omepidlist);
			qry += " and exists (select 1 from omuser c where a.omepid=c.omepid and c.custid=" + custid + ")";
		} else {
			qry += " and a.omepid=" + Omepid;
		}
		//System.out.println("TotalOmcustware1:" + qry);

		double totaljhamt = 0;
		double totaljhcurr = 0;
		int totaljhskc = 0;
		int totaljhnum = 0;
		tb = DbHelperSQL.Query(qry).getTable(1);
		// WriteDebugTXT("TotalOmcustware1.1", "ok");
		if (tb.getRowCount() > 0) {
			totaljhamt = Double.parseDouble(tb.getRow(0).get("JHAMT").toString());
			totaljhcurr = Double.parseDouble(tb.getRow(0).get("JHCURR").toString());
			totaljhnum = Integer.parseInt(tb.getRow(0).get("JHNUM").toString());
			totaljhskc = Integer.parseInt(tb.getRow(0).get("JHSKC").toString());
		}
		// WriteDebugTXT("TotalOmcustware1.2", "ok");
		String responsestr = "\"msg\":\"操作成功！\",\"TOTALJHAMT\":\"" + totaljhamt + "\"" //
				+ ",\"TOTALJHCURR\":\"" + Func.FormatNumber(totaljhcurr, 2) + "\""//
				+ ",\"TOTALJHNUM\":\"" + totaljhnum + "\""//
				+ ",\"TOTALJHSKC\":\"" + totaljhskc + "\"";
		// 总完成数

		// 当前用户订货数
		qry = "select nvl(sum(a.amount),0) as amount,nvl(sum(a.amount*c.retailsale),0) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc,count(distinct a.wareid) as num";
		qry += "\n from omcustware a";
		qry += "\n join omuser b on a.omepid=b.omepid";
		qry += "\n left outer join warecode c on a.wareid=c.wareid";

		qry += "\n where a.omid=" + Omid + " and b.accid=" + Accid;
		qry += "\n and exists (select 1 from omwarecode x where a.omid=x.omid and a.wareid=x.wareid and x.noused=0)";
		if (levelid == 0) // 公司本部
		{
			if (!omepidlist.equals(""))
				qry += " and " + Func.getCondition("a.omepid", omepidlist);
			qry += " and exists (select 1 from omuser b1 where a.omepid=b1.omepid and b1.custid=" + custid + ")";
		} else {
			qry += " and a.omepid=" + Omepid;
		}

		// WriteDebugTXT("TotalOmcustware2", qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		// WriteDebugTXT("TotalOmcustware2.1", "ok");
		double totalwcamt = 0;
		double totalwccurr = 0;
		int totalwcskc = 0;
		int totalwcnum = 0;
		if (tb.getRowCount() > 0) {
			totalwcamt = Double.parseDouble(tb.getRow(0).get("AMOUNT").toString());
			totalwccurr = Double.parseDouble(tb.getRow(0).get("CURR").toString());
			totalwcnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			totalwcskc = Integer.parseInt(tb.getRow(0).get("SKC").toString());
		}
		//System.out.println(tb.getRow(0).get("CURR").toString()+" "+Func.FormatNumber(totalwccurr, 2));
		// WriteDebugTXT("TotalOmcustware2.2", "ok");
		responsestr += ",\"TOTALWCAMT\":\"" + totalwcamt + "\"" //
				+ ",\"TOTALWCCURR\":\"" + Func.FormatNumber(totalwccurr, 2) + "\"" //
				+ ",\"TOTALWCNUM\":\"" + totalwcnum + "\"" //
				+ ",\"TOTALWCSKC\":\"" + totalwcskc + "\"";
		float rateamt = 1;
		float ratecurr = 1;

		if (totaljhamt != 0)
			rateamt = (float) Func.getRound(totalwcamt / totaljhamt * 100, 2);
		if (totaljhcurr != 0)
			ratecurr = (float) Func.getRound(totalwccurr / totaljhcurr * 100, 2);

		responsestr += ",\"RATEAMT\":\"" + rateamt + "\"" + ",\"RATECURR\":\"" + ratecurr + "\"";

		// WriteDebugTXT("TotalOmcustware2.3", "ok");

		// 当前用户订货数
		qry = "select ";
		if (hzfs == 0)
			qry += " c.typeid as code,d.fullname as name";
		else if (hzfs == 1)
			qry += " a.colorid as code,d.colorname as name";
		else if (hzfs == 2)
			qry += " a.sizeid as code,d.sizename as name";
		else if (hzfs == 3)
			qry += " c.waveid as code,d.wavename as name";
		else if (hzfs == 4)
			qry += " c.salerange as code,c.salerange as name";
		qry += ",nvl(sum(a.amount),0) as amount,nvl(sum(a.amount*c.retailsale),0) as retailcurr";
		qry += "\n from omcustware a";
		qry += "\n join omuser b on a.omepid=b.omepid";
		qry += "\n left outer join warecode c on a.wareid=c.wareid";
		if (hzfs == 0)
			qry += "\n left outer join waretype d on c.typeid=d.typeid";
		else if (hzfs == 1)
			qry += "\n left outer join colorcode d on a.colorid=d.colorid";
		else if (hzfs == 2)
			qry += "\n left outer join sizecode d on a.sizeid=d.sizeid";
		else if (hzfs == 3)
			qry += "\n left outer join warewave d on c.waveid=d.waveid";
		qry += "\n where a.omid=" + Omid + " and b.accid=" + Accid;
		qry += "\n  and a.amount>0";
		qry += "\n and exists (select 1 from omwarecode x where a.omid=x.omid and a.wareid=x.wareid and x.noused=0)";
		if (levelid == 0) // 公司本部
		{
			if (!omepidlist.equals(""))
				qry += " and " + Func.getCondition("a.omepid", omepidlist);
			qry += " and exists (select 1 from omuser b1 where a.omepid=b1.omepid and b1.custid=" + custid + ")";
		} else {
			qry += " and a.omepid=" + Omepid;
		}
		qry += "\n group by ";
		if (hzfs == 0)
			qry += " c.typeid,d.fullname";
		else if (hzfs == 1)
			qry += " a.colorid,d.colorname";
		else if (hzfs == 2)
			qry += " a.sizeid,d.sizename,d.sizeno";
		else if (hzfs == 3)
			qry += " c.waveid,d.wavename";
		else if (hzfs == 4)
			qry += " c.salerange";
		qry += " order by amount desc";
		// WriteDebugTXT("TotalOmcustware3", qry);

		tb = DbHelperSQL.Query(qry).getTable(1);
		// WriteDebugTXT("TotalOmcustware4", qry);

		String fh = "";
		int rs = tb.getRowCount();
		float amount = 0; // totalwcamt
		responsestr += ",\"orderlist\":[";
		for (int i = 0; i < rs; i++) {
			amount = Float.parseFloat(tb.getRow(i).get("AMOUNT").toString());
			rateamt = 0;
			if (totalwcamt != 0)
				rateamt = (float) Func.getRound(amount / totalwcamt * 100, 2);
			int rownum = i + 1;
			String ss = tb.getRow(i).get("NAME").toString().trim();
			if (ss.length() <= 0)
				ss = "<无>";
			responsestr += fh + "{\"CODE\":\"" + tb.getRow(i).get("CODE").toString() + "\""//
					+ ",\"NAME\":\"" + ss + "\""//
					+ ",\"AMOUNT\":\"" + amount + "\""//
					+ ",\"RATEAMT\":\"" + rateamt + "\"" //
					+ ",\"RETAILCURR\":\"" + tb.getRow(i).get("RETAILCURR").toString() + "\""//
					+ ",\"ROWNUMBER\":" + rownum + "}";
			fh = ",";

		}
		responsestr += "]";
		return responsestr;

	}

	// 显示客户订货的商品汇总列表
	public Table listOmcustwaretotal(QueryParam qp, long levelid, long custid, String omepidlist, String findbox) {
		String qry = "select a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.imagename,b.tjtag,c.mustbj,sum(a.amount) as amount,sum(a.amount*b.retailsale) as retailcurr";
		qry += "\n from omcustware a join warecode b on a.wareid=b.wareid";
		qry += "\n join omwarecode c on a.omid=c.omid and a.wareid=c.wareid";
		qry += "\n where a.omid=" + Omid + " and c.noused=0";
		// qry += " and exists (select 1 from omwarecode d where a.omid=d.omid and a.wareid=d.wareid and d.noused=0)";
		if (levelid == 0) {// 本部可以查询所 有订货
			if (!omepidlist.equals(""))
				qry += " and (" + Func.getCondition("a.omepid", omepidlist) + ")";
			qry += " and exists (select 1 from omuser d where a.omepid=d.omepid and d.custid=" + custid + ")";
		} else {
			qry += " and a.omepid=" + Omepid;
		}
		if (!findbox.equals(""))
			qry += "  and (b.warename like '%" + findbox + "%' or b.wareno like '%" + findbox.toUpperCase() + "%')";
		qry += " group by a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.imagename,b.tjtag,c.mustbj";
		qp.setQueryString(qry);
		qp.setSumString("nvl(sum(amount),0) as totalamt,nvl(sum(retailcurr),0) as totalcurr,count(distinct wareid) as totalnum");

		return DbHelperSQL.GetTable(qp);

	}

	// 显示客户订货的商品汇总列表 微信小程序订货用
	public Table listOmcustwaretotal1(QueryParam qp, long levelid, long custid, String omepidlist, String findbox) {
		String qry = "select a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.imagename0,sum(a.amount) as amount,sum(a.amount*b.retailsale) as retailcurr";
		qry += "\n from omcustware a join warecode b on a.wareid=b.wareid";
		// qry += "\n join omwarecode c on a.omid=c.omid and a.wareid=c.wareid";
		qry += "\n where a.omid=" + Omid;
		qry += " and a.omepid=" + Omepid;
		if (!findbox.equals(""))
			qry += "  and (b.warename like '%" + findbox + "%' or b.wareno like '%" + findbox.toUpperCase() + "%')";
		qry += " group by a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.imagename0";
		qp.setQueryString(qry);
		qp.setSumString("nvl(sum(amount),0) as totalamt,nvl(sum(retailcurr),0) as totalcurr,count(distinct wareid) as totalnum");
		//System.out.println(qry);
		return DbHelperSQL.GetTable(qp);

	}

	// 显示订货的商品明细(颜色尺码以二维表方式展开)
	public int listOmcustwaremx(String omepidlist) {

		String qry = "select wareid,wareno,warename,retailsale,units,imagename ";
		if (omepidlist.equals("^" + Omepid + "^"))
			qry += ",(select count(*) from omorder where omepid=" + Omepid + " and omid=" + Omid + " and rownum>0) as ordbj";
		else
			qry += ",1 as ordbj";
		// ordbj>=0,表示不允许改订货数
		qry += " from warecode  where wareid=" + Wareid;
		//System.out.println("listOmcustwaremx: " + omepidlist + " " + userid + " " + qry);
		Table tb = new Table();

		tb = DbHelperSQL.Query(qry).getTable(1);
		int rs = tb.getRowCount();
		if (rs == 0) {
			errmess = "订货商品未找到！";
			return 0;
		}
		String retcs = "\"msg\":\"操作成功！\",\"WAREID\":\"" + tb.getRow(0).get("WAREID").toString() + "\"" //
				+ ",\"WARENO\":\"" + tb.getRow(0).get("WARENO").toString() + "\""//
				+ ",\"WARENAME\":\"" + tb.getRow(0).get("WARENAME").toString() + "\"" //
				+ ",\"UNITS\":\"" + tb.getRow(0).get("UNITS").toString() + "\"" //
				+ ",\"RETAILSALE\":\"" + tb.getRow(0).get("RETAILSALE").toString() + "\""//
				+ ",\"IMAGENAME\":\"" + tb.getRow(0).get("IMAGENAME").toString() + "\"" //
				+ ",\"ORDBJ\":\"" + tb.getRow(0).get("ORDBJ").toString() + "\"";

		// 取颜色
		qry = "select a.colorid,b.colorname";
		qry += " FROM warecolor a";
		qry += " left outer join colorcode b on a.colorid=b.colorid";
		qry += " where a.wareid=" + Wareid + " and b.statetag=1 and b.noused=0 ";
		qry += " and not exists (select 1 from omwarenocolor a1 where a.wareid=a1.wareid and a.colorid=a1.colorid and a1.omid=" + Omid + ")";
		qry += " order by b.colorno";

		tb = DbHelperSQL.Query(qry).getTable(1);
		// String fh = "";
		rs = tb.getRowCount();
		if (rs == 0) {
			errmess = "商品没有可以订货的颜色！";
			return 0;
		}
		// {"colorlist":[{"COLORID":"1","COLORNAME":"红色"},{"COLORID":"2","COLORNAME":"黄色"}]
		// ,"sizelist":[{"SIZEID":"1","SIZENAME":"XS","SIZENO":"01"},{"SIZEID":"2","SIZENAME":"S","SIZENO":"02"},{"SIZEID":"3","SIZENAME":"M","SIZENO":"03"},{"SIZEID":"4","SIZENAME":"L","SIZENO":"04"},{"SIZEID":"5","SIZENAME":"XL","SIZENO":"05"},{"SIZEID":"6","SIZENAME":"XXL","SIZENO":"06"},{"SIZEID":"7","SIZENAME":"3XL","SIZENO":"07"}]}

		retcs += ",\"colorlist\":[";

		for (int i = 0; i < rs; i++) {
			if (i > 0)
				retcs += ",";
			retcs += "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"," //
					+ "\"COLORNAME\":\"" + tb.getRow(i).get("COLORNAME").toString() + "\"}";
			// fh = ",";

		}
		retcs += "]";

		// 取尺码
		qry = "select a.sizeid,a.sizename,a.sizeno  FROM sizecode a";
		qry += " left outer join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno";
		// qry += " left outer join omsubject c on b.accid=c.accid and c.omid=" + omid;
		qry += " where a.statetag=1 and a.noused=0  and b.wareid=" + Wareid;
		qry += " and not exists (select 1 from warenosize a1 where b.wareid=a1.wareid and a.sizeid=a1.sizeid)";
		qry += " order by a.sizeno,a.sizename";
		// qry += " where a.statetag=1 and a.noused=0 and b.wareid=" + wareid + " order by a.sizeno,a.sizename";
		tb = DbHelperSQL.Query(qry).getTable(1);
		// fh = "";
		rs = tb.getRowCount();
		if (rs == 0) {
			errmess = "商品没有可以订货的尺码！";
			return 0;
		}

		retcs += ",\"sizelist\":[";

		for (int i = 0; i < rs; i++) {
			if (i > 0)
				retcs += ",";
			retcs += "{\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\","//
					+ "\"SIZENAME\":\"" + tb.getRow(i).get("SIZENAME").toString() + "\"}";
			// fh = ",";

		}
		retcs += "]";
		// 取定货数
		qry = "select colorid,sizeid,sum(amount) as amount";
		qry += " from omcustware a ";
		qry += " where omid=" + Omid + " and wareid=" + Wareid;
		if (omepidlist.length() > 0)
			qry += " and " + Func.getCondition("a.omepid", omepidlist);
		// if (userid > 0)
		// qry += " and a.omepid=" + userid;
		// else
		// qry += " and a.omepid=" + userid;
		// if (custid > 0)
		// {
		// qry += " and exists (select 1 from omuser b where a.omepid=b.omepid and b.custid= " + custid + ")";
		// }
		qry += " and amount>0 ";
		qry += " group by colorid,sizeid";

		tb = DbHelperSQL.Query(qry).getTable(1);
		// fh = "";
		rs = tb.getRowCount();
		retcs += ",\"orderlist\":[";

		for (int i = 0; i < rs; i++) {
			if (i > 0)
				retcs += ",";
			retcs += "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"," //
					+ "\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"," //
					+ "\"AMOUNT\":\"" + tb.getRow(i).get("AMOUNT").toString() + "\"}";
			// fh = ",";
		}
		retcs += "]";

		errmess = retcs;
		return 1;
	}

	// 显示订货的商品明细(颜色尺码以二维表方式展开) 微信小程序
	public int listOmcustwaremx() { // omid==0

		String qry = "select wareid,wareno,warename,retailsale,units,imagename ";
		// if (omepidlist.equals("^" + Userid + "^"))
		qry += ",(select count(*) from omorder where omepid=" + Omepid + " and omid=" + Omid + " and rownum>0) as ordbj";
		// else
		// qry += ",1 as ordbj";
		// ordbj>=0,表示不允许改订货数
		qry += " from warecode  where wareid=" + Wareid;
		//System.out.println("listOmcustwaremx: " + omepidlist + " " + userid + " " + qry);
		Table tb = new Table();

		tb = DbHelperSQL.Query(qry).getTable(1);
		int rs = tb.getRowCount();
		if (rs == 0) {
			errmess = "订货商品未找到！";
			return 0;
		}
		String retcs = "\"msg\":\"操作成功！\",\"WAREID\":\"" + tb.getRow(0).get("WAREID").toString() + "\"" //
				+ ",\"WARENO\":\"" + tb.getRow(0).get("WARENO").toString() + "\""//
				+ ",\"WARENAME\":\"" + tb.getRow(0).get("WARENAME").toString() + "\"" //
				+ ",\"UNITS\":\"" + tb.getRow(0).get("UNITS").toString() + "\"" //
				+ ",\"RETAILSALE\":\"" + tb.getRow(0).get("RETAILSALE").toString() + "\""//
				+ ",\"IMAGENAME\":\"" + tb.getRow(0).get("IMAGENAME").toString() + "\"" //
				+ ",\"ORDBJ\":\"" + tb.getRow(0).get("ORDBJ").toString() + "\"";

		// 取颜色
		qry = "select a.colorid,b.colorname";
		qry += " FROM warecolor a";
		qry += " left outer join colorcode b on a.colorid=b.colorid";
		qry += " where a.wareid=" + Wareid + " and b.statetag=1 and b.noused=0 ";
		qry += " and not exists (select 1 from omwarenocolor a1 where a.wareid=a1.wareid and a.colorid=a1.colorid and a1.omid=" + Omid + ")";
		qry += " order by b.colorno";

		tb = DbHelperSQL.Query(qry).getTable(1);
		// String fh = "";
		rs = tb.getRowCount();
		if (rs == 0) {
			errmess = "商品没有可以订货的颜色！";
			return 0;
		}
		// {"colorlist":[{"COLORID":"1","COLORNAME":"红色"},{"COLORID":"2","COLORNAME":"黄色"}]
		// ,"sizelist":[{"SIZEID":"1","SIZENAME":"XS","SIZENO":"01"},{"SIZEID":"2","SIZENAME":"S","SIZENO":"02"},{"SIZEID":"3","SIZENAME":"M","SIZENO":"03"},{"SIZEID":"4","SIZENAME":"L","SIZENO":"04"},{"SIZEID":"5","SIZENAME":"XL","SIZENO":"05"},{"SIZEID":"6","SIZENAME":"XXL","SIZENO":"06"},{"SIZEID":"7","SIZENAME":"3XL","SIZENO":"07"}]}

		retcs += ",\"colorlist\":[";

		for (int i = 0; i < rs; i++) {
			if (i > 0)
				retcs += ",";
			retcs += "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"," //
					+ "\"COLORNAME\":\"" + tb.getRow(i).get("COLORNAME").toString() + "\"}";
			// fh = ",";

		}
		retcs += "]";

		// 取尺码
		qry = "select a.sizeid,a.sizename,a.sizeno  FROM sizecode a";
		qry += " left outer join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno";
		// qry += " left outer join omsubject c on b.accid=c.accid and c.omid=" + omid;
		qry += " where a.statetag=1 and a.noused=0  and b.wareid=" + Wareid;
		qry += " and not exists (select 1 from warenosize a1 where b.wareid=a1.wareid and a.sizeid=a1.sizeid)";
		qry += " order by a.sizeno,a.sizename";
		// qry += " where a.statetag=1 and a.noused=0 and b.wareid=" + wareid + " order by a.sizeno,a.sizename";
		tb = DbHelperSQL.Query(qry).getTable(1);
		// fh = "";
		rs = tb.getRowCount();
		if (rs == 0) {
			errmess = "商品没有可以订货的尺码！";
			return 0;
		}

		retcs += ",\"sizelist\":[";

		for (int i = 0; i < rs; i++) {
			if (i > 0)
				retcs += ",";
			retcs += "{\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\","//
					+ "\"SIZENAME\":\"" + tb.getRow(i).get("SIZENAME").toString() + "\"}";
			// fh = ",";

		}
		retcs += "]";
		// 取定货数
		qry = "select colorid,sizeid,sum(amount) as amount";
		qry += " from omcustware a ";
		qry += " where omid=" + Omid + " and wareid=" + Wareid;
		// if (omepidlist.length() > 0)
		// qry += " and " + Func.getCondition("a.omepid", omepidlist);
		// if (userid > 0)
		// qry += " and a.omepid=" + userid;
		// else
		qry += " and a.omepid=" + Omepid;
		// if (custid > 0)
		// {
		// qry += " and exists (select 1 from omuser b where a.omepid=b.omepid and b.custid= " + custid + ")";
		// }
		qry += " and amount>0 ";
		qry += " group by colorid,sizeid";

		tb = DbHelperSQL.Query(qry).getTable(1);
		// fh = "";
		rs = tb.getRowCount();
		retcs += ",\"orderlist\":[";

		for (int i = 0; i < rs; i++) {
			if (i > 0)
				retcs += ",";
			retcs += "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"," //
					+ "\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"," //
					+ "\"AMOUNT\":\"" + tb.getRow(i).get("AMOUNT").toString() + "\"}";
			// fh = ",";
		}
		retcs += "]";

		errmess = retcs;
		return 1;
	}

	// 生成客户订单
	// public int doOmCustWareToOrder(long custid,int priceprec) {
	//
	// String qry = "declare ";
	// qry += "\n v_totalcurr number; ";
	// qry += "\n v_totalamt number; ";
	// qry += "\n v_id number; ";
	// qry += "\n v_noteno varchar2(20); ";
	// qry += "\n v_discount number; ";
	// qry += "\n v_price0 number; ";
	// qry += "\n v_count number; ";
	// qry += "\n v_wareid number(10); ";
	// qry += "\n v_colorid number(10); ";
	// qry += "\n v_sizeid number(10); ";
	// qry += "\n v_wareid1 number(10); ";
	// qry += "\n v_wareid0 number(10); ";
	// qry += "\n v_colorid0 number(10); ";
	// qry += "\n v_sizeid0 number(10); ";
	//
	// qry += "\n v_amount number; ";
	// qry += "\n v_price number; ";
	// qry += "\n v_curr number; ";
	// qry += "\n v_retailsale number; ";
	// // qry += "\n v_price0 number; ";
	// // qry += "\n v_discount number; ";
	// qry += "\n v_pricetype number(1); ";
	// qry += "\n v_retcs varchar2(200); ";
	// qry += "\n begin ";
	// // qry += "\n select accid into v_paccid from cityhouse where cthouseid=" + cthouseid + ";";
	// // qry += "\n v_paccid:=" + paccid + ";";
	//
	// // --产生客户订单
	// qry += "\n begin ";
	// qry += "\n select 'CO'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from custorderh ";
	// qry += "\n where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'CO'||To_Char(SYSDATE,'yyyymmdd'); ";
	// qry += "\n v_id:=custorderH_ID.NEXTVAL; ";
	// qry += "\n insert into custorderh (id, accid,noteno,notedate,custid,statetag,totalamt,totalcurr,remark,handno,operant,checkman,lastdate) ";
	// qry += "\n values (v_id," + Accid + ",v_noteno,sysdate," + custid + ",1,0,0,'','','" + Lastop + "','',sysdate); ";
	// // --插入订货会商品明细记录
	//
	// qry += "\n declare cursor cur_data is ";
	// qry += "\n select a.wareid,a.colorid,a.sizeid,a.amount,a.price,a.curr,b.retailsale";
	// qry += "\n from omcustware a join warecode b on a.wareid=b.wareid where a.omid=" + Omid + " and a.omepid=" + Omepid;
	// qry += "\n and a.amount>0 order by a.wareid; ";
	// qry += "\n begin";
	//
	// qry += "\n open cur_data;";
	// qry += "\n fetch cur_data into v_wareid,v_colorid,v_sizeid,v_amount,v_price,v_curr,v_retailsale;";
	// qry += "\n while cur_data%found loop";
	// qry += "\n if v_retailsale=0 then "; // 计算原价
	// qry += "\n v_price0:=v_price;";
	// qry += "\n v_discount:=1;";
	// qry += "\n else ";
	// qry += "\n v_price0:=v_retailsale;";
	// qry += "\n v_discount:=round(v_price/v_price0,2);";
	// qry += "\n end if;";
	//
	// qry += "\n insert into provorderm (id,accid,noteno,wareid,colorid,sizeid,amount,price0,discount,price,curr,remark0) ";
	// qry += "\n values (provorderm_id.nextval," + accid + ",v_noteno,v_wareid0,v_colorid0,v_sizeid0,v_amount,v_price0,v_discount,v_price,v_curr,'');";
	// qry += "\n fetch cur_data into v_wareid,v_colorid,v_sizeid,v_amount,v_price,v_curr,v_retailsale;";
	// qry += "\n end loop;";
	// qry += "\n end loop;";
	// qry += "\n close cur_data;";
	// qry += "\n end;";
	//
	// qry += "\n select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from provorderm where accid=" + accid + " and noteno=v_noteno; ";
	// qry += "\n update provorderh set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + accid + " and noteno=v_noteno; ";
	// qry += "\n delete from cityorder where cthouseid=" + Cthouseid + " and ordaccid=" + accid + ";";
	// // qry += "\n select '1'||noteno into :retcs from provorderh where id=v_id; ";
	// qry += "\n v_retcs:='1'||v_noteno; ";
	// qry += "\n exception ";
	// qry += "\n when others then ";
	// qry += "\n begin ";
	// qry += "\n rollback; "; // 回滚
	// qry += "\n v_retcs:= '0生成采购订单失败' ; ";
	// qry += "\n end; ";
	// qry += "\n <<exit>>";
	// qry += "\n select v_retcs into :retcs from dual;";
	// qry += "\n end; ";
	// Map<String, ProdParam> param = new HashMap<String, ProdParam>();
	// param.put("retcs", new ProdParam(Types.VARCHAR));
	// int ret = DbHelperSQL.ExecuteProc(qry, param);
	// if (ret < 0) {
	// errmess = "操作异常！";
	// return 0;
	// }
	// String retcs = param.get("retcs").getParamvalue().toString();
	//
	// errmess = retcs.substring(1);
	// return Integer.parseInt(retcs.substring(0, 1));
	// }
	// 清空订货商品

	public int Clearall() {
		String qry = " delete from omcustware where omid=" + Omid + " and omepid=" + Omepid;
		//	System.out.println(qry);
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作异常!";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;

		}
	}

}