package com.oracle.bean;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
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
//  @author:sunhong  @date:2017-03-19 22:22:17
//*************************************
public class Warecolor implements java.io.Serializable {
	private Float Id;
	private Long Userid;
	private Long Accid;
	private Long Wareid;
	private Long Colorid;
	private String Lastop;
	private Date Lastdate;
	private Integer Ywly;
	private Integer Noused;
	private String errmess;
	private String Calcdate = ""; // 账套开始使用日期

	public void setCalcdate(String calcdate) {
		Calcdate = calcdate;
	}

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

	public void setUserid(Long userid) {
		this.Userid = userid;
	}

	public Long getAccid() {
		return Accid;
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

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setYwly(Integer ywly) {
		this.Ywly = ywly;
	}

	public void setNoused(Integer noused) {
		this.Noused = noused;
	}

	public Integer getYwly() {
		return Ywly;
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
		// strSql.append("select wareid from warecode where id=" + id + " and
		// rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		// strSql.append("\n update warecolor set statetag=2,lastop='" + Lastop
		// + "',lastdate=sysdate");
		// strSql.append("\n where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// } else {
		// errmess = "删除成功！";
		return 1;
		// }
	}

	// 成批增加记录
	public int Append(JSONObject jsonObject) {
		if (Wareid == 0) {
			errmess = "商品id无效！";
			return 0;
		}
		if (!jsonObject.has("colorreclist")) {
			errmess = "未传入颜色参数！";
			return 0;

		}
		JSONArray jsonArray = jsonObject.getJSONArray("colorreclist");

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n    v_count number;");
		strSql.append("\n begin");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			Colorid = Long.parseLong(jsonObject2.getString("colorid"));
			int value = Integer.parseInt(jsonObject2.getString("value"));
			if (value == 0) {
				strSql.append("\n    delete from warecolor where wareid=" + Wareid + " and colorid=" + Colorid);
				strSql.append("\n    and f_warecolorisused(wareid,colorid)=0;");
			} else {
				strSql.append("\n    select count(*) into v_count from warecolor where wareid=" + Wareid + "  and colorid=" + Colorid + " ;");
				strSql.append("\n    if v_count=0 then");
				strSql.append("\n       insert into warecolor (id,wareid,colorid,lastdate,lastop,ywly) ");
				strSql.append("\n       values (warecolor_id.nextval," + Wareid + "," + Colorid + ",sysdate,'" + Lastop + "'," + Ywly + ");");
				strSql.append("\n    end if;");
			}
		}
		strSql.append("\n    commit; ");
		strSql.append("\n    update warecode set colorlist=f_getwarecolorname(wareid) where wareid=" + Wareid + " and accid=" + Accid + ";");
		strSql.append("\n end ;");
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append(int value) {
		if (Wareid == 0 || Colorid == 0) {
			errmess = "商品id或颜色id无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append(" declare");
		strSql.append("\n   v_count number;");
		// strSql.append(" v_wareid number;");
		// strSql.append(" v_colorid number;");
		// strSql.append(" v_accid number;");
		strSql.append("\n begin");
		if (value == 0) {
			strSql.append("\n   delete from warecolor where wareid=" + Wareid + " and colorid=" + Colorid);
			strSql.append("\n   and  f_warecolorisused(wareid,colorid)=0;");

		} else {
			strSql.append("\n    select count(*) into v_count from warecolor where wareid=" + Wareid + "  and colorid=" + Colorid + " ;");
			strSql.append("\n    if v_count=0 then");
			strSql.append("\n       insert into warecolor (id,wareid,colorid,lastdate,lastop,ywly) ");
			strSql.append("\n       values (warecolor_id.nextval," + Wareid + "," + Colorid + ",sysdate,'" + Lastop + "'," + Ywly + ");");
			strSql.append("\n    end if;");
		}
		strSql.append("\n     update warecode set colorlist=f_getwarecolorname(wareid) where wareid=" + Wareid + " and accid=" + Accid + ";");
		strSql.append("\n  commit; ");
		strSql.append("\n end ;");

		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("\n    update warecolor set ");
		// if (Id != null) {
		// strSql.append("id='" + Id + "',");
		// }
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		// if (Wareid != null) {
		// strSql.append("wareid=" + Wareid + ",");
		// }
		// if (Colorid != null) {
		// strSql.append("colorid=" + Colorid + ",");
		// }
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
		// if (Ywly != null) {
		// strSql.append("ywly=" + Ywly + ",");
		// }
		if (Noused != null) {
			strSql.append("Noused=" + Noused + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("\n   where wareid=" + Wareid + " and colorid=" + Colorid + " ;");
		strSql.append("\n   commit;");
		strSql.append("\n  end;");
		// System.out.println(strSql.toString());
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
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
		strSql.append("\n  FROM warecolor ");
		if (!strWhere.equals("")) {
			strSql.append("\n  where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	// public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
	// StringBuilder strSql = new StringBuilder();
	// strSql.append("select " + fieldlist);
	// strSql.append("\n FROM warecolor ");
	// if (!strWhere.equals("")) {
	// strSql.append("\n where " + strWhere);
	// }
	// qp.setQueryString(strSql.toString());
	// return DbHelperSQL.GetTable(qp);
	// }

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append(" select  " + fieldlist);
		strSql.append("\n from warecolor a");
		strSql.append("\n left outer join colorcode b on a.colorid=b.colorid");
		strSql.append("\n where b.accid=" + Accid);
		if (Wareid != null)
			strSql.append("\n  and a.wareid=" + Wareid);
		if (!strWhere.equals("")) {
			strSql.append("\n  and " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String strWhere1, int rdbj) {
		StringBuilder strSql = new StringBuilder();

		strSql.append("select a.colorid,b.colorname,b.colorno,a.noused,'1' as selbj,f_warecolorisused(a.wareid,a.colorid) as usedbj ");
		strSql.append(",c.brandname");
		strSql.append("\n  from warecolor a");
		strSql.append("\n  left outer join colorcode b on a.colorid=b.colorid");
		strSql.append("\n  left outer join brand c on b.brandid=c.brandid");
		strSql.append("\n  where b.accid=" + Accid + " and a.wareid=" + Wareid);
		if (strWhere.length() > 0)
			strSql.append("\n  and " + strWhere);
		if (rdbj == 0) {
			strSql.append("\n  union all");
			strSql.append("\n  select b.colorid,b.colorname,b.colorno,b.noused,'0' as selbj,0 as usedbj");
			strSql.append(",c.brandname");
			strSql.append("\n  from colorcode b ");
			strSql.append("\n  left outer join brand c on b.brandid=c.brandid");
			strSql.append("\n  where b.accid=" + Accid);
			if (strWhere1.length() > 0)
				strSql.append("\n  and " + strWhere1);
			strSql.append("\n  and not exists (select 1 from warecolor a where a.colorid=b.colorid and a.wareid=" + Wareid + ")");
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable() {
		String qry = "SELECT wareid, TRANSLATE (LTRIM (text, '~'), '*~', '*,') colornamelist";
		qry += " FROM (SELECT ROW_NUMBER () OVER (PARTITION BY wareid ORDER BY wareid,lvl DESC) rn,wareid, text";
		qry += " FROM (SELECT  wareid, LEVEL lvl, SYS_CONNECT_BY_PATH (colorname,'~') text";
		qry += " FROM (SELECT  a.wareid, a.colorid,b.colorname as colorname, ROW_NUMBER () OVER (PARTITION BY a.wareid ORDER BY a.wareid,b.colorname) x";
		// qry += " FROM warecolor a join colorcode b on a.colorid=b.colorid
		// where a.wareid=" + wareid + " and b.noused=0 and b.statetag=1 ORDER
		// BY a.wareid,a.colorid fetch first 10 row only) a";
		qry += " FROM warecolor a join colorcode b on a.colorid=b.colorid where a.wareid=" + Wareid + " and b.noused=0 and b.statetag=1 and rownum<10 ORDER BY a.wareid,a.colorid ) a";
		qry += " CONNECT BY wareid = PRIOR wareid AND x - 1 = PRIOR x))";
		qry += " WHERE rn = 1";
		return DbHelperSQL.GetTable(qry);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from warecolor");
		// strSql.append("\n where brandname='" + Brandname + "' and statetag=1
		// and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append("\n and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 获取指定商品的颜色和尺码及库存(新)
	public int doGetWaresizeandcolor2(JSONObject jsonObject, int priceprec, int nearsaleok, int qxbj) {

		Wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		long custid = jsonObject.has("custid") ? Long.parseLong(jsonObject.getString("custid")) : 0;
		long guestid = jsonObject.has("guestid") ? Long.parseLong(jsonObject.getString("guestid")) : 0;
		long provid = jsonObject.has("provid") ? Long.parseLong(jsonObject.getString("provid")) : 0;
		long saleid = jsonObject.has("saleid") ? Long.parseLong(jsonObject.getString("saleid")) : 0;
		int isph = jsonObject.has("isph") ? Integer.parseInt(jsonObject.getString("isph")) : 0;
		int thbj = jsonObject.has("thbj") ? Integer.parseInt(jsonObject.getString("thbj")) : 0;
		int notetype = jsonObject.has("notetype") ? Integer.parseInt(jsonObject.getString("notetype")) : -1;
		String wareno = jsonObject.has("wareno") ? jsonObject.getString("wareno") : "";
		// notetype:0=零售，1=调出，2=调拨订单,3=客户销售

		String qry = "";
		int pricetype = 0;
		// notetype:0=零售，1=调出，2=调拨订单
		if (notetype == 3) {
			qry = "select wareid,wareno,warename,units,entersale,sale1,sale2,sale3,sale4,sale5,imagename0 ";
			qry += ",retailsale";
			qry += " from warecode where accid=" + Accid + " and wareno='" + wareno + "' and statetag=1 and rownum=1";

		} else if (notetype == 1) {
			long tohouseid = jsonObject.has("tohouseid") ? Long.parseLong(jsonObject.getString("tohouseid")) : 0;
			if (tohouseid > 0) {
				qry = "select pricetype1 from warehouse where houseid =" + tohouseid;
				pricetype = Integer.parseInt(DbHelperSQL.RunSql(qry).toString());
			}
		} else if (notetype == 2 && houseid > 0) {
			qry = "select pricetype1 from warehouse where houseid =" + houseid;
			pricetype = Integer.parseInt(DbHelperSQL.RunSql(qry).toString());
			// System.out.println(qry+" pricetype="+pricetype);
		} else if (notetype == 0 && houseid > 0) {
			qry = "select pricetype from warehouse where houseid =" + houseid;
			pricetype = Integer.parseInt(DbHelperSQL.RunSql(qry).toString());
		}
		if (Wareid > 0) {
			qry = "select wareid,wareno,warename,units,entersale,sale1,sale2,sale3,sale4,sale5,imagename0 ";
			qry += ",decode(tjtag,1,retailsale,case " + pricetype + " when 1 then sale1 when 2 then sale2 when 3 then sale3  when 4 then sale4  when 5 then sale5 else retailsale end) as retailsale";
			qry += " from warecode where accid=" + Accid + " and statetag=1 and wareid=" + Wareid;
		} else if (wareno.length() > 0) {
			qry = "select wareid,wareno,warename,units,entersale,sale1,sale2,sale3,sale4,sale5,imagename0 ";
			qry += ",decode(tjtag,1,retailsale,case " + pricetype + " when 1 then sale1 when 2 then sale2 when 3 then sale3  when 4 then sale4  when 5 then sale5 else retailsale end) as retailsale";
			qry += " from warecode where accid=" + Accid + " and wareno='" + wareno + "' and statetag=1 and rownum=1";
		} else {
			errmess = "商品id参数无效！";
			return 0;
		}
		// System.out.println(qry);
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "未找到该商品！";
			return 0;
		}
		Wareid = Long.parseLong(tb.getRow(0).get("WAREID").toString());
		Float entersale = Float.parseFloat(tb.getRow(0).get("ENTERSALE").toString());
		Float retailsale = Float.parseFloat(tb.getRow(0).get("RETAILSALE").toString());
		Float sale4 = Float.parseFloat(tb.getRow(0).get("SALE4").toString()); // 批发价
		Float sale5 = Float.parseFloat(tb.getRow(0).get("SALE5").toString()); // 打包价
		Float sale1 = Float.parseFloat(tb.getRow(0).get("SALE1").toString()); // 售价1
		Float sale2 = Float.parseFloat(tb.getRow(0).get("SALE2").toString()); // 售价2
		Float sale3 = Float.parseFloat(tb.getRow(0).get("SALE3").toString()); // 售价3
		if (notetype == 0) {//如果是零售，查看是否有调价记录

			qry = "     select retailsale from ( ";
			qry += "\n     select case " + pricetype + " when 0 then b.retailsale  when 1 then b.sale1 when 2 then b.sale2 when 3 then b.sale3 when 4 then b.sale4 when 5 then b.sale5 else b.retailsale end as retailsale";
			qry += "\n     from wareadjusth a join wareadjustm b on a.accid=b.accid and a.noteno=b.noteno";
			qry += "\n     where a.statetag=2";
			qry += "\n     and a.begindate<to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')+1";
			qry += "\n     and a.enddate>=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') ";
			if (houseid > 0)
				qry += "\n   and exists (select 1 from wareadjustc c where a.accid=c.accid and a.noteno=c.noteno and c.houseid=" + houseid + ")";
			qry += "\n     and b.wareid=" + Wareid + " and a.accid=" + Accid + " order by a.notedate desc ) where rownum=1";
			Table tb1 = new Table();
			tb1 = DbHelperSQL.Query(qry).getTable(1);
			if (tb1.getRowCount() == 1) {
				retailsale = Float.parseFloat(tb1.getRow(0).get("RETAILSALE").toString());
			}
		}
		// System.out.println("retailsale=" + retailsale);

		String retcs = "\"msg\":\"操作成功！\",\"WAREID\": \"" + Wareid + "\"" //
				+ ",\"WARENO\": \"" + tb.getRow(0).get("WARENO").toString() + "\"" //
				+ ",\"WARENAME\": \"" + tb.getRow(0).get("WARENAME").toString() + "\"" //
				+ ",\"UNITS\": \"" + tb.getRow(0).get("UNITS").toString() + "\"" //
				+ ",\"IMAGENAME0\": \"" + tb.getRow(0).get("IMAGENAME0").toString() + "\"" //
				+ ",\"ENTERSALE\": \"" + entersale + "\"" //
				+ ",\"RETAILSALE\": \"" + retailsale + "\"" //
				+ ",\"SALE1\": \"" + sale1 + "\"" //
				+ ",\"SALE2\": \"" + sale2 + "\""//
				+ ",\"SALE3\": \"" + sale3 + "\"" //
				+ ",\"SALE4\": \"" + sale4 + "\"" //
				+ ",\"SALE5\": \"" + sale5 + "\"";
		Float discount = (float) 1;
		Float price0 = retailsale;
		Float price = retailsale;
		// pricetype = 0;

		if (notetype == 3) {
			retcs += ",\"PRICE0\": \"" + price0 + "\"" //
					+ ",\"DISCOUNT\": \"" + discount + "\"" //
					+ ",\"PRICE\": \"" + price + "\"";
		} else {
			if (provid > 0) // 取供应商价格，采购
			{
				// System.out.println("111111");
				price0 = entersale;
				price = entersale;
				qry = "select pricetype,discount from provide where provid=" + provid + " and accid=" + Accid;
				tb = DbHelperSQL.Query(qry).getTable(1);
				if (tb.getRowCount() == 1) {
					pricetype = Integer.parseInt(tb.getRow(0).get("PRICETYPE").toString()); // 价格方式
																							// 0=进价,0=零售价
					discount = Float.parseFloat(tb.getRow(0).get("DISCOUNT").toString()); // 折扣

					switch (pricetype) {
					case 1: // 零售价
						price0 = retailsale;
						price = Func.getRound(price0 * discount, priceprec);// Math.Round((Float)(price0
																			// *
																			// discount),
																			// priceprec,
																			// MidpointRounding.AwayFromZero);
						break;
					default:
						price0 = entersale;
						price = Func.getRound(price0 * discount, priceprec);// Math.Round((Float)(price0
																			// *
																			// discount),
																			// priceprec,
																			// MidpointRounding.AwayFromZero);
						break;
					}

				}
				retcs += ",\"PRICE0\": \"" + price0 + "\"" //
						+ ",\"DISCOUNT\": \"" + discount + "\"" //
						+ ",\"PRICE\": \"" + price + "\"";
			} else if (custid > 0 || guestid > 0) // 取客户价格 ,销售
			{
				// System.out.println("22222");
				String jstr = Funcpackge.myWareoutprice(Accid, Wareid, custid, guestid, houseid, saleid, priceprec, nearsaleok, thbj);
				retcs += "," + jstr;
			} else {
				retcs += ",\"PRICE0\": \"" + price0 + "\"" //
						+ ",\"DISCOUNT\": \"" + discount + "\"" //
						+ ",\"PRICE\": \"" + price + "\"";

			}
		}

		int rs = 0;
		// 取颜色
		qry = "select a.colorid,b.colorname";
		qry += " FROM warecolor a";
		qry += " left outer join colorcode b on a.colorid=b.colorid";
		qry += " where a.wareid=" + Wareid + " and b.statetag=1 and b.noused=0 and a.noused=0 order by b.colorno";
		tb = DbHelperSQL.Query(qry).getTable(1);
		// string retcs;
		String fh = "";
		rs = tb.getRowCount();
		// {"colorlist":[{"COLORID":"1","COLORNAME":"红色"},{"COLORID":"2","COLORNAME":"黄色"}]
		// ,"sizelist":[{"SIZEID":"1","SIZENAME":"XS","SIZENO":"01"},{"SIZEID":"2","SIZENAME":"S","SIZENO":"02"},{"SIZEID":"3","SIZENAME":"M","SIZENO":"03"},{"SIZEID":"4","SIZENAME":"L","SIZENO":"04"},{"SIZEID":"5","SIZENAME":"XL","SIZENO":"05"},{"SIZEID":"6","SIZENAME":"XXL","SIZENO":"06"},{"SIZEID":"7","SIZENAME":"3XL","SIZENO":"07"}]}

		retcs += ",\"colorlist\":[";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"," //
					+ "\"COLORNAME\":\"" + tb.getRow(i).get("COLORNAME").toString() + "\"}";
			fh = ",";

		}
		retcs += "]";

		// 取尺码
		qry = " select a.sizeid,a.sizename,a.sizeno  FROM sizecode a";
		qry += " left outer join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno";
		qry += " where a.statetag=1 and a.noused=0 and b.wareid=" + Wareid;
		qry += " and not exists (select 1 from warenosize a1 where b.wareid=a1.wareid and a.sizeid=a1.sizeid)";
		qry += " order by a.sizeno,a.sizename";

		tb = DbHelperSQL.Query(qry).getTable(1);
		fh = "";
		rs = tb.getRowCount();
		// {"colorlist":[{"COLORID":"1","COLORNAME":"红色"},{"COLORID":"2","COLORNAME":"黄色"}]
		// ,"sizelist":[{"SIZEID":"1","SIZENAME":"XS","SIZENO":"01"},{"SIZEID":"2","SIZENAME":"S","SIZENO":"02"},{"SIZEID":"3","SIZENAME":"M","SIZENO":"03"},{"SIZEID":"4","SIZENAME":"L","SIZENO":"04"},{"SIZEID":"5","SIZENAME":"XL","SIZENO":"05"},{"SIZEID":"6","SIZENAME":"XXL","SIZENO":"06"},{"SIZEID":"7","SIZENAME":"3XL","SIZENO":"07"}]}

		retcs += ",\"sizelist\":[";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"," //
					+ "\"SIZENAME\":\"" + tb.getRow(i).get("SIZENAME").toString() + "\"}";
			fh = ",";

		}
		retcs += "]";
		if (houseid > 0) // 如果传入houseid,表示要返回库存
		{
			int bj = 0;
			if (isph == 1)
				bj = 2; // 如果是配货，返回可用库存

			vTotalCxkczy dal = new vTotalCxkczy();

			dal.setAccid(Accid);
			dal.setUserid(Userid);
			dal.setHouseid(houseid);
			dal.setWareid(Wareid);
			dal.setQxbj(qxbj);
			dal.setBj(bj);
			dal.setCalcdate(Calcdate);
			// dal.setCxdatetime(nowdate);
			// dal.setServerdatetime(htp.getNowdatetime());
			dal.doCxkczy();

			// myTotalCxkczy("", userid, accid, houseid, wareid, 0, 0, -1, -1,
			// -1, -1, "", "", "", "", "", "", 0, bj);

			qry = "   select colorid,sizeid,amount";
			qry += "   from v_cxkczy_data a where epid=" + Userid + " and wareid=" + Wareid + " and houseid=" + houseid;
			qry += "   and amount<>0 ";
			tb = DbHelperSQL.Query(qry).getTable(1);
			fh = "";
			rs = tb.getRowCount();

			retcs += ",\"kclist\":[";

			for (int i = 0; i < rs; i++) {
				retcs += fh + "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\","//
						+ "\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\","//
						+ "\"AMOUNT\":\"" + tb.getRow(i).get("AMOUNT").toString() + "\"}";
				fh = ",";

			}
			retcs += "]";
		}
		errmess = retcs;
		return 1;
	}

	public int Clear() {
		if (Wareid == 0) {
			errmess = "wareid不是一个有效值！";
			return 0;
		}
		String qry = "delete from warecolor where wareid=" + Wareid;
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}

	public int LoadFromXLS(String wareno, String colorname, int barfs) {
		if (wareno.equals("")) {
			errmess = "货号不是一个有效值！";
			return 0;
		}

		if (colorname.equals("")) {
			errmess = "颜色不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n    v_wareid number(10); ";
		qry += "\n    v_colorid number(10); ";
		qry += "\n    v_count number(10); ";
		qry += "\n    v_retcs varchar2(200); ";
		qry += "\n begin ";
		qry += "\n    begin";
		qry += "\n      select wareid into v_wareid from warecode where accid=" + Accid + " and statetag=1 and wareno='" + wareno + "' and rownum=1;";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n      v_retcs:= '0货号 " + wareno + " 未找到！' ;";
		qry += "\n      goto exit;";
		qry += "\n    end;";
		qry += "\n    begin";
		qry += "\n      select colorid into v_colorid from colorcode where accid=" + Accid + " and accid1=0 and statetag=1 and colorname='" + colorname + "' and rownum=1;";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n      v_colorid:=colorcode_colorid.nextval; ";
		qry += "\n      insert into colorcode (accid,colorid,colorname,colorno,shortname,statetag,lastop)";
		qry += "\n      values (" + Accid + ",v_colorid,'" + colorname + "','','" + PinYin.getPinYinHeadChar(colorname) + "',1,'" + Lastop + "');";
		qry += "\n    end;";
		qry += "\n    select count(*) into v_count from warecolor where wareid=v_wareid and colorid=v_colorid;";
		qry += "\n    if v_count=0  then";
		qry += "\n       insert into warecolor (id,wareid,colorid,lastop) values (warecolor_id.nextval,v_wareid,v_colorid,'" + Lastop + "'); ";
		qry += "\n    end if;";
		qry += "\n    update warecode set colorlist=f_getwarecolorname(wareid) where wareid=v_wareid and accid=" + Accid + ";";
		qry += "\n    commit;";
		qry += "\n    p_autobarcode(v_wareid, " + barfs + "); ";

		qry += "\n    v_retcs:= '1颜色增加成功！' ;";
		qry += "\n    <<exit>>";
		qry += "\n    select v_retcs into :retcs from dual;";
		qry += "\n end ; ";
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

	// 下载
	public int doDown(String downdate)// downdate="",同步所有数据，只同步更新的数据
	{
		StringBuilder sql = new StringBuilder();
		String headsql = "";
		boolean isupdate = false;
		String qry = "select id,wareid,colorid,noused,lastop";
		qry += " from warecolor a ";

		if (downdate.length() <= 0) {
			qry += " where exists (select 1 from warecode b where a.wareid=b.wareid and b.statetag=1 and b.noused=0 and b.accid=" + Accid + ")";
			headsql = " insert ";
		} else {
			qry += " where exists (select 1 from warecode b where a.wareid=b.wareid and b.statetag=1 and b.noused=0 and b.accid=" + Accid + ")";
			qry += " and a.lastdate>=to_date('" + downdate + "','yyyy-mm-dd hh24:mi:ss')";
			isupdate = true; // 更改
			headsql = " replace ";
		}
		Table tb = new Table();
		// System.out.println(qry);
		tb = DbHelperSQL.GetTable(qry);

		if (!isupdate) {
			sql.append("\n begin;");
			sql.append("\n delete from warecolor where accid=" + Accid + ";");
			sql.append("\n commit;");
		}
		sql.append("\n begin;");
		for (int i = 0; i < tb.getRowCount(); i++) {
			String sql1 = "accid";
			String sql2 = Accid.toString();
			for (int j = 0; j < tb.getColumnCount(); j++) {//
				// if (j > 0) {
				// sql1 += ",";
				// sql2 += ",";
				// }
				String fieldtype = tb.getMeta().get(j + 1).getType();

				String strKey = tb.getRow(i).getColumn(j + 1).getName();//
				String strValue = "";
				if (fieldtype.equals("NUMBER"))
					strValue = tb.getRow(i).get(j).toString();//
				else
					strValue = "'" + tb.getRow(i).get(j).toString() + "'";//
				sql1 += "," + strKey;
				sql2 += "," + strValue;

			}
			sql.append("\n " + headsql + " into warecolor (" + sql1 + ",lastdate) values (" + sql2 + ",datetime('now','localtime'));");
			if (i > 0 && i % 100 == 0) {
				sql.append("\n commit;");
				sql.append("\n begin;");
			}
		}
		sql.append("\n commit;");

		String path = Func.getRootPath();
		String filename = "down" + Func.desEncode(Accid + "_" + Func.DateToStr(new Date(), "yyyyMMddHHmmss")).toLowerCase() + ".sql";// "brand.sql";
		String savefile = path + "/temp/" + filename;
		PrintStream printStream;
		try {
			printStream = new PrintStream(new FileOutputStream(savefile));
			printStream.println(sql.toString());
			printStream.close();
			errmess = "/temp/" + filename;
			return 1;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errmess = "操作失败：" + e.getMessage();
			return 0;
		}

	}

}