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
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-04 16:27:24
//*************************************
public class Omwarecode implements java.io.Serializable {

	private Long Accid;
	private Long Userid;

	private Long Omid;
	private Long Wareid;
	private Date Lastdate;
	private String Lastop;
	private Float Amount;
	private Integer Mustbj;
	private Integer Noused;
	private Long Agreenum;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public void setUserid(Long userid) {
		this.Userid = userid;
	}

	public void setOmid(Long omid) {
		this.Omid = omid;
	}

	public Long getOmid() {
		return Omid;
	}

	public void setWareid(Long wareid) {
		this.Wareid = wareid;
	}

	public Long getWareid() {
		return Wareid;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setPermitamt(Float amount) {
		this.Amount = amount;
	}

	public Float getPermitamt() {
		return Amount;
	}

	public void setMustbj(Integer mustbj) {
		this.Mustbj = mustbj;
	}

	public Integer getMustbj() {
		return Mustbj;
	}

	public void setAgreenum(Long agreenum) {
		this.Agreenum = agreenum;
	}

	public Long getAgreenum() {
		return Agreenum;
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
		// // strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);

		strSql.append("declare ");
		strSql.append("\n    v_count number;");
		strSql.append("\n    v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		strSql.append("\n   select count(*) into v_count from omcustware where wareid=" + Wareid + " and omid=" + Omid + ";");
		strSql.append("\n   if v_count>0 then");
		strSql.append("\n      v_retcs:='0当前商品已有订货，不允许删除！' ;");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   delete from omwarecode where wareid=" + Wareid + " and omid=" + Omid + ";");
		strSql.append("\n   delete from omwarecode1 where (wareid=" + Wareid + " or wareid1=" + Wareid + ") and omid=" + Omid + ";");
		strSql.append("\n   delete from omdetail where wareid=" + Wareid + " and omid=" + Omid + ";");
		strSql.append("\n   delete from ompoleware a where wareid=" + Wareid + " and exists (select 1 from ompole b where a.poleid=b.poleid and b.omid=" + Omid + ");");
		strSql.append("\n   delete from omcollect where wareid=" + Wareid + " and omid=" + Omid + ";");
		strSql.append("\n   delete from omappraise where wareid=" + Wareid + " and omid=" + Omid + ";");
		strSql.append("\n   v_retcs:= '1删除成功！';");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		strSql.append("\n  end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作失败！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

	// 增加记录 ok
	public int Append(JSONObject jsonObject) {

		if (Omid == 0) {
			errmess = "订货会id无效！";
			return 0;
		}
		if (!jsonObject.has("warereclist")) {
			errmess = "没有加入商品记录！";
			return 0;

		}
		JSONArray jsonArray = jsonObject.getJSONArray("warereclist");
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n begin ");

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			long wareid = Long.parseLong(jsonObject2.getString("wareid"));
			int mustbj = Integer.parseInt(jsonObject2.getString("mustbj"));
			strSql.append("\n   select count(*) into v_count from omwarecode where wareid=" + wareid + " and omid=" + Omid + "; ");
			strSql.append("\n   if v_count=0 then");
			strSql.append("\n      insert into omwarecode (omid,wareid,permitamt,mustbj,noused,lastdate,lastop)");
			strSql.append("\n      values (" + Omid + "," + wareid + ",0," + mustbj + ",0,sysdate,'" + Lastop + "');");
			strSql.append("\n   else ");
			strSql.append("\n      update omwarecode set mustbj=" + mustbj + " where  wareid=" + wareid + " and omid=" + Omid + "; ");
			strSql.append("\n   end if; ");
		}
		strSql.append("\n end; ");
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 修改记录 ok
	public int Update() {
		if (Omid == null || Wareid == null || Omid == 0 || Wareid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Omwarecode set ");
		// if (Omid != null) {
		// strSql.append("omid=" + Omid + ",");
		// }
		// if (Wareid != null) {
		// strSql.append("wareid=" + Wareid + ",");
		// }
		// if (Lastop != null) {
		// }
		if (Amount != null) {
			strSql.append("permitamt='" + Amount + "',");
		}
		if (Mustbj != null) {
			strSql.append("mustbj=" + Mustbj + ",");
		}
		if (Noused != null) {
			strSql.append("Noused=" + Noused + ",");
		}
		if (Agreenum != null) {
			strSql.append("agreenum=" + Agreenum + ",");
		}
		strSql.append("lastop='" + Lastop + "',");
		strSql.append("lastdate=sysdate");
		strSql.append("  where wareid=" + Wareid + " and omid=" + Omid + " ;");
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
		strSql.append(" FROM Omwarecode ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM Omwarecode a  join warecode b on a.wareid=b.wareid");
		strSql.append("\n left outer join waretype c on b.typeid=c.typeid");
		strSql.append("\n left outer join warewave d on b.waveid=d.waveid");
		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Omwarecode");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}

	// 返回商品的资料，尺码，颜色及相关搭配（订货会用）
	public int getOmwareinfo(long wareid, String omepidlist) {

		if (wareid == 0) {
			errmess = "商品id无效！";
			return 0;
		}
		String qry = "select a.wareid,a.wareno,a.warename,a.units,a.retailsale,a.prodyear,a.seasonname,a.useritem1,a.useritem2,a.remark,a.sjman,a.tjtag,a.ssdate,d.mustbj ";
		qry += ",f_getwarecolorname(a.wareid) as colornamelist,f_getwaresizename(a.wareid) as sizenamelist,b.wavename,a.salerange,c.typename,e.provname,f.brandname ";
		qry += ", nvl((select 1 from omcollect where omid=" + Omid + " and omepid=" + Userid + " and wareid=" + wareid + " and rownum=1),0) as iscollect";
		qry += ", nvl((select 1 from omappraise where omid=" + Omid + " and omepid=" + Userid + " and wareid=" + wareid + " and rownum=1),0) as isappraise";
		// qry += ", nvl((select sum(amount) from omcustware a1 where a1.omid=" + omid + " and a1.omepid=" + userid + " and a1.wareid=" + wareid + "),0) as ordamt";
		qry += ", nvl((select sum(amount) from omcustware a1 where a1.omid=" + Omid + " and " + Func.getCondition("a1.omepid", omepidlist) + " and a1.wareid=" + wareid + "),0) as ordamt";
		qry += ", d.PERMITAMT";
		// qry += ",nvl( (select 1 from vipcollect where onhouseid=" + onhouseid + " and wareid=" + wareid + " and vipid=" + vipid + "),0) as iscollect"; //收藏

		qry += "\n from warecode a ";
		qry += "\n left outer join warewave b on a.waveid=b.waveid";
		qry += "\n left outer join waretype c on a.typeid=c.typeid";
		qry += "\n left outer join omwarecode d on a.wareid=d.wareid and d.omid=" + Omid;
		qry += "\n left outer join provide e on a.provid=e.provid";
		qry += "\n left outer join brand f on a.brandid=f.brandid";
		qry += "\n where a.accid=" + Accid + " and a.wareid=" + wareid;
		//System.out.println("getOmwareinfo 1:"+qry);
		// WriteDebugTXT("getomwareinfo1", qry);
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "未找到该商品！";
			return 0;
		}
		//System.out.println("getOmwareinfo 1.1");

		String retcs = "\"msg\":\"操作成功！\"";
		for (int j = 0; j < tb.getColumnCount(); j++) {//
			String strKey = tb.getRow(0).getColumn(j + 1).getName().toUpperCase();//
			String strValue = tb.getRow(0).get(j).toString();//
			retcs += ",\"" + strKey + "\":\"" + strValue + "\"";
		}
		//System.out.println(retcs);
		// String retcs = "\"WAREID\": \"" + wareid + "\"" //
		retcs += ",\"wareinfolist\":["

				+ "{\"商品名称\": \"" + tb.getRow(0).get("WARENAME").toString() + "\"}"//
				+ ",{\"货号\": \"" + tb.getRow(0).get("WARENO").toString() + "\"}" //
				+ ",{\"颜色\": \"" + tb.getRow(0).get("COLORNAMELIST").toString() + "\"}"//
				+ ",{\"尺码\": \"" + tb.getRow(0).get("SIZENAMELIST").toString() + "\"}" //
				+ ",{\"类型\":\"" + tb.getRow(0).get("TYPENAME").toString() + "\"}" //
				+ ",{\"季节\": \"" + tb.getRow(0).get("SEASONNAME").toString() + "\"}"//
		;
		if (tb.getRow(0).get("BRANDNAME").toString().length() > 0)
			retcs += ",{\"品牌\": \"" + tb.getRow(0).get("BRANDNAME").toString() + "\"}";

		if (tb.getRow(0).get("SSDATE").toString().length() > 0)
			retcs += ",{\"上市日期\": \"" + tb.getRow(0).get("SSDATE").toString() + "\"}";
		if (tb.getRow(0).get("USERITEM1").toString().length() > 0)
			retcs += ",{\"面料\": \"" + tb.getRow(0).get("USERITEM1").toString() + "\"}";

		if (tb.getRow(0).get("USERITEM2").toString().length() > 0)
			retcs += ",{\"里料\": \"" + tb.getRow(0).get("USERITEM2").toString() + "\"}";
		if (tb.getRow(0).get("WAVENAME").toString().length() > 0)
			retcs += ",{\"波段\": \"" + tb.getRow(0).get("WAVENAME").toString() + "\"}";//
		if (tb.getRow(0).get("PROVNAME").toString().length() > 0) // 要显示供应商
			retcs += ",{\"生产商\": \"" + tb.getRow(0).get("PROVNAME").toString() + "\"}"; //
		Float maxordamt = Float.parseFloat(tb.getRow(0).get("PERMITAMT").toString());
		if (maxordamt > 0) // 要显示供应商
			retcs += ",{\"备货数\": \"" + Func.FormatNumber(maxordamt) + "\"}"; //

		// + ",\"RETAILSALE\": \"" + tb.getRow(0).get("RETAILSALE").toString() + "\"" //
		// + ",\"PRODYEAR\": \"" + tb.getRow(0).get("PRODYEAR").toString() + "\"" //
		// + ",\"SJMAN\": \"" + tb.getRow(0).get("SJMAN").toString() + "\"" //
		// + ",\"SALERANGE\":\"" + tb.getRow(0).get("SALERANGE").toString() + "\""//
		// + ",\"REMARK\":\"" + tb.getRow(0).get("REMARK").toString() + "\"" //
		// + ",\"ISCOLLECT\":" + tb.getRow(0).get("ISCOLLECT").toString() //
		// + ",\"ISAPPRAISE\":" + tb.getRow(0).get("ISAPPRAISE").toString() //
		// + ",\"TJTAG\":" + tb.getRow(0).get("TJTAG").toString() //
		// + ",\"ORDAMT\":" + tb.getRow(0).get("ORDAMT").toString();
		;
		retcs += "]";
		//System.out.println("getOmwareinfo 1.2");

		// 商品图片
		qry = "select id,imagename from (select id,imagename from orderpicture where wareid=" + wareid + " order by orderid,id ) where rownum<=10";
		//System.out.println("getOmwareinfo 2:"+qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		String fh = "";
		int rs = tb.getRowCount();
		retcs += ",\"warepicturelist\":[";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"IMGID\":\"" + tb.getRow(i).get("ID").toString() + "\"," //
					+ "\"IMAGENAME\":\"" + tb.getRow(i).get("IMAGENAME").toString() + "\"}";
			fh = ",";
		}
		retcs += "]";
		// 取搭配商品
		qry = "select wareid,wareno,warename,units,retailsale,imagename ";
		qry += " from warecode a where a.accid=" + Accid;
		qry += " and exists (select 1 from omwarecode1 b where a.wareid=b.wareid1 and b.wareid=" + wareid + " and b.omid=" + Omid + ")";
		//System.out.println("getOmwareinfo 3:"+qry);

		tb = DbHelperSQL.Query(qry).getTable(1);
		fh = "";
		rs = tb.getRowCount();
		// {"colorlist":[{"COLORID":"1","COLORNAME":"红色"},{"COLORID":"2","COLORNAME":"黄色"}]
		// ,"sizelist":[{"SIZEID":"1","SIZENAME":"XS","SIZENO":"01"},{"SIZEID":"2","SIZENAME":"S","SIZENO":"02"},{"SIZEID":"3","SIZENAME":"M","SIZENO":"03"},{"SIZEID":"4","SIZENAME":"L","SIZENO":"04"},{"SIZEID":"5","SIZENAME":"XL","SIZENO":"05"},{"SIZEID":"6","SIZENAME":"XXL","SIZENO":"06"},{"SIZEID":"7","SIZENAME":"3XL","SIZENO":"07"}]}

		retcs += ",\"wareotherlist\":[";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"WAREID\":\"" + tb.getRow(i).get("WAREID").toString() + "\"" //
					+ ",\"WARENAME\":\"" + tb.getRow(i).get("WARENAME").toString() + "\"" //
					+ ",\"UNITS\":\"" + tb.getRow(i).get("UNITS").toString() + "\"" //
					+ ",\"WARENO\":\"" + tb.getRow(i).get("WARENO").toString() + "\""//
					+ ",\"RETAILSALE\":\"" + tb.getRow(i).get("RETAILSALE").toString() + "\"" //
					+ ",\"IMAGENAME0\":\"" + tb.getRow(i).get("IMAGENAME").toString() + "\"}";
			fh = ",";

		}
		retcs += "]";
		errmess = retcs;
		return 1;

	}

	// 显示订货商品汇总(订货会app用)我的订单
	// ordbj:0=未订货 1=已订货 2=所有
	public Table listOmcustwaresum(QueryParam qp, long poleid, int ordbj, int mustbj, int tjtag, Warecode wareinfo, String salerange, String typelist, String findbox) {
		//System.out.println("listOmcustwaresum begin ok");

		String qry = "";
		Long waveid = wareinfo.getWaveid();
		Float minsale = wareinfo.getMinsale();
		Float maxsale = wareinfo.getMaxsale();
		String wareno = wareinfo.getWareno();
		Long provid = wareinfo.getProvid();
		Long brandid = wareinfo.getBrandid();
		//System.out.println("listOmcustwaresum 111 ");

		//System.out.println("listOmcustwaresum 2222");

		if (ordbj == 0)// 未订货
		{
			qry = "select a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.imagename,b.tjtag,a.mustbj,0 as amount,0 as retailcurr";
			qry += "\n from omwarecode a join warecode b on a.wareid=b.wareid";
			qry += "\n where a.omid=" + Omid + " and a.noused=0";
			if (tjtag == 1)
				qry += " and b.tjtag=1";
			if (mustbj == 1)
				qry += " and a.mustbj=1";
			if (waveid > 0)
				qry += " and b.waveid=" + waveid;
			if (provid >= 0)
				qry += " and b.provid=" + provid;
			if (brandid >= 0)
				qry += " and b.brandid=" + brandid;
			if (!salerange.equals(""))
				qry += " and b.salerange='" + salerange + "'";
			if (minsale >= 0)
				qry += " and b.retailsale>=" + minsale;
			if (maxsale >= 0)
				qry += " and b.retailsale<=" + maxsale;
			if (!typelist.equals(""))
				qry += " and (" + Func.getCondition("b.typeid", typelist) + ") ";
			qry += "\n and not exists (select 1 from omcustware c where a.omid=c.omid and a.wareid=c.wareid and c.omepid=" + Userid + " and c.amount>0)";
			if (!wareno.equals(""))
				qry += "\n  and b.wareno='" + wareno + "'";
			if (!findbox.equals(""))
				qry += "\n  and (b.warename like '%" + findbox + "%' or b.wareno like '%" + findbox.toUpperCase() + "%')";
			if (poleid > 0)
				qry += "\n and exists (select 1 from ompoleware x where a.wareid=x.wareid and x.poleid=" + poleid + " )";
		} else if (ordbj == 1) // 已订货
		{
			qry = "select a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.imagename,b.tjtag,c.mustbj,sum(a.amount) as amount,sum(a.amount*b.retailsale) as retailcurr";
			qry += "\n from omcustware a join warecode b on a.wareid=b.wareid";
			qry += "\n join omwarecode c on a.omid=c.omid and a.wareid=c.wareid";
			qry += "\n where a.omid=" + Omid + " and a.omepid=" + Userid + " and a.amount>0";
			qry += "\n and c.noused=0";
			if (tjtag == 1)
				qry += " and b.tjtag=1";
			if (mustbj == 1)
				qry += " and c.mustbj=1";

			if (waveid > 0)
				qry += " and b.waveid=" + waveid;
			if (provid >= 0)
				qry += " and b.provid=" + provid;
			if (brandid >= 0)
				qry += " and b.brandid=" + brandid;
			if (!salerange.equals(""))
				qry += " and b.salerange='" + salerange + "'";
			if (minsale >= 0)
				qry += " and b.retailsale>=" + minsale;
			if (maxsale >= 0)
				qry += " and b.retailsale<=" + maxsale;
			if (!typelist.equals(""))
				qry += " and (" + Func.getCondition("b.typeid", typelist) + ") ";
			if (!wareno.equals(""))
				qry += "\n  and b.wareno='" + wareno + "'";
			if (!findbox.equals(""))
				qry += "\n  and (b.warename like '%" + findbox + "%' or b.wareno like '%" + findbox.toUpperCase() + "%')";
			if (poleid > 0)
				qry += "\n and exists (select 1 from ompoleware x where a.wareid=x.wareid and x.poleid=" + poleid + " )";
			qry += "\n group by a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.imagename,b.tjtag,c.mustbj";
		} else {
			qry = "select a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.imagename,b.tjtag,a.mustbj,0 as amount,0 as retailcurr";
			qry += "\n from omwarecode a join warecode b on a.wareid=b.wareid";
			qry += "\n where a.omid=" + Omid + " and a.noused=0";
			if (tjtag == 1)
				qry += " and b.tjtag=1";
			if (mustbj == 1)
				qry += " and a.mustbj=1";

			if (waveid > 0)
				qry += " and b.waveid=" + waveid;
			if (provid >= 0)
				qry += " and b.provid=" + provid;
			if (brandid >= 0)
				qry += " and b.brandid=" + brandid;
			if (!salerange.equals(""))
				qry += " and b.salerange='" + salerange + "'";
			if (minsale >= 0)
				qry += " and b.retailsale>=" + minsale;
			if (maxsale >= 0)
				qry += " and b.retailsale<=" + maxsale;
			if (!typelist.equals(""))
				qry += " and (" + Func.getCondition("b.typeid", typelist) + ") ";
			qry += "\n and not exists (select 1 from omcustware c where a.omid=c.omid and a.wareid=c.wareid and c.omepid=" + Userid + " and c.amount>0)";
			if (!wareno.equals(""))
				qry += "\n  and b.wareno='" + wareno + "'";
			if (!findbox.equals(""))
				qry += "  and (b.warename like '%" + findbox + "%' or b.wareno like '%" + findbox.toUpperCase() + "%')";
			if (poleid > 0)
				qry += "\n and exists (select 1 from ompoleware x where a.wareid=x.wareid and x.poleid=" + poleid + " )";
			qry += "\n union all ";
			qry += "\n select a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.imagename,b.tjtag,c.mustbj,sum(a.amount) as amount,sum(a.amount*b.retailsale) as retailcurr";
			qry += "\n from omcustware a join warecode b on a.wareid=b.wareid";
			qry += "\n join omwarecode c on a.omid=c.omid and a.wareid=c.wareid";
			qry += "\n where a.omid=" + Omid + " and a.omepid=" + Userid + " and a.amount>0";
			qry += "\n and c.noused=0";
			if (tjtag == 1)
				qry += " and b.tjtag=1";
			if (mustbj == 1)
				qry += " and c.mustbj=1";
			if (waveid > 0)
				qry += " and b.waveid=" + waveid;
			if (provid >= 0)
				qry += " and b.provid=" + provid;
			if (brandid >= 0)
				qry += " and b.brandid=" + brandid;
			if (!wareno.equals(""))
				qry += "\n  and b.wareno='" + wareno + "'";
			if (!salerange.equals(""))
				qry += " and b.salerange='" + salerange + "'";

			if (minsale >= 0)
				qry += " and b.retailsale>=" + minsale;
			if (maxsale >= 0)
				qry += " and b.retailsale<=" + maxsale;
			if (!typelist.equals(""))
				qry += " and (" + Func.getCondition("b.typeid", typelist) + ") ";
			if (!findbox.equals(""))
				qry += "  and (b.warename like '%" + findbox + "%' or b.wareno like '%" + findbox.toUpperCase() + "%')";
			if (poleid > 0)
				qry += "\n and exists (select 1 from ompoleware x where a.wareid=x.wareid and x.poleid=" + poleid + " )";
			qry += "\n group by a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.imagename,b.tjtag,c.mustbj";
		}
		System.out.println("listOmcustwaresum 3333 " + qry);

		qp.setQueryString(qry);

		return DbHelperSQL.GetTable(qp);

	}
}