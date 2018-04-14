/**
 * 
 */
package com.oracle.bean;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

import com.comdot.data.Table;
import com.common.tool.Func;
import com.netdata.db.DbHelperSQL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
public class vShareunit {
	private String Noteno;
	private Long Accid;
	private String errmess;
	private Integer Housezkbj = 0; // 1=销售帐款分店铺核对

	public String getNoteno() {
		return Noteno;
	}

	public void setNoteno(String noteno) {
		Noteno = noteno;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	public void setHousezkbj(int housezkbj) {
		Housezkbj = housezkbj;
	}

	public String getErrmess() {
		return errmess;
	}

	public void setErrmess(String errmess) {
		this.errmess = errmess;
	}

	// 分享销售单
	public int doShareWareout(int fs) {// fs:0=零售，1=批发，2=退货
		if (Noteno.equals("")) {
			errmess = "单据号无效!";
			return 0;
		}
		// if (Noteno.length() > 0) {
		// errmess = "正在评测，即将推出，敬请期待!";
		// return 0;
		// }

		//System.out.println(" begin " + Noteno);
		Table tb = new Table();
		String company = "";
		String tel = "";
		String address = "";
		String qry = "select company,tel,address from accreg where accid=" + Accid;
		tb = DbHelperSQL.Query(qry).getTable(1);// DbHelperSQL.GetTable(qry);
		if (tb.getRowCount() > 0) {
			company = tb.getRow(0).get("COMPANY").toString();
			// tel = tb.getRow(0).get("TEL").toString();
			// address = tb.getRow(0).get("ADDRESS").toString();
		}
		//System.out.println("111 ");

		String notetitle = "";
		if (fs == 0) {// 零售
			qry = "select a.noteno,a.notedate,a.operant,a.totalamt,a.totalcurr,a.remark,a.handno,a.changecurr,a.freecurr,a.checkcurr";
			qry += " ,a.houseid,b.housename,c.guestname,c.sex,c.vipno,a.cent,a.jfcurr,a.usecent,a.yhjcurr,a.zpaycurr";
			qry += ",f_getwareoutsalemanname(a.accid,a.noteno) as salemanlist";
			qry += ",b.address as haddress,b.tel as htel";
			qry += " from wareouth a ";
			qry += " left outer join warehouse b on a.houseid=b.houseid ";
			qry += " left outer join guestvip c on a.guestid=c.guestid ";
			qry += " where a.accid=" + Accid + " and a.noteno='" + Noteno + "' and a.ntid=0";
			qry += " and a.statetag=1";
			notetitle = "零售出库单";
		} else if (fs == 1) {// 批发
			qry = "select a.noteno,a.notedate,a.operant,a.totalamt,a.totalcurr,a.remark,a.handno,a.freecurr,a.checkcurr,a.zpaycurr";
			qry += " ,a.houseid,b.housename,c.custname,c.address,c.tel,a.orderno";
			qry += ",f_getwareoutsalemanname(a.accid,a.noteno) as salemanlist";
			qry += ",b.address as haddress,b.tel as htel";
			qry += " from wareouth a ";
			qry += " left outer join warehouse b on a.houseid=b.houseid ";
			qry += " left outer join customer c on a.custid=c.custid ";
			qry += " where a.accid=" + Accid + " and a.noteno='" + Noteno + "' and a.ntid=1";
			qry += " and a.statetag=1";
			notetitle = "批发出库单";

		} else if (fs == 2) {// 批发退货
			qry = "select a.noteno,a.notedate,a.operant,a.totalamt,a.totalcurr,a.remark,a.handno,a.freecurr,a.checkcurr,a.zpaycurr";
			qry += " ,a.houseid,b.housename,c.custname,c.address,c.tel,a.orderno";
			qry += ",f_getwareoutsalemanname(a.accid,a.noteno) as salemanlist";
			qry += ",b.address as haddress,b.tel as htel";
			qry += " from wareouth a ";
			qry += " left outer join warehouse b on a.houseid=b.houseid ";
			qry += " left outer join customer c on a.custid=c.custid ";
			qry += " where a.accid=" + Accid + " and a.noteno='" + Noteno + "' and a.ntid=2";
			qry += " and a.statetag=1";
			notetitle = "批发退货单";

		}
		tb = DbHelperSQL.Query(qry).getTable(1);// DbHelperSQL.GetTable(qry);
		if (tb.getRowCount() == 0) {
			errmess = "未找到单据！";
			return 0;
		}
		long houseid = 0;
		if (Housezkbj == 1) // 销售帐款分店铺核对
			houseid = Long.parseLong(tb.getRow(0).get("HOUSEID").toString());

		//System.out.println(qry);
		String house_address = tb.getRow(0).get("HADDRESS").toString();
		String house_tel = tb.getRow(0).get("HTEL").toString();

		String operant = tb.getRow(0).get("OPERANT").toString();
		String salemanlist = tb.getRow(0).get("SALEMANLIST").toString();
		Float totalcurr = Float.parseFloat(tb.getRow(0).get("TOTALCURR").toString());
		Float totalamt = Float.parseFloat(tb.getRow(0).get("TOTALAMT").toString());
		Float freecurr = Float.parseFloat(tb.getRow(0).get("FREECURR").toString());
		Float checkcurr = Float.parseFloat(tb.getRow(0).get("CHECKCURR").toString());
		Float balcurr = (float) 0;
		Float balcurr0 = (float) 0;
		Float qkcurr = (float) 0;
		JSONObject jsonObject = null;
		if (fs > 0) {// 销售出库
			Wareouth dal = new Wareouth();
			dal.setAccid(Accid);
			dal.setNoteno(Noteno);
			dal.setHouseid(houseid);
			//System.out.println("111 aaaa");
			if (dal.doGetpaye(fs - 1) > 0) {

				String jsonstr = "{" + dal.getErrmess() + "}";
				//System.out.println("111 bbbb");
				// {"TOTALCURR":"4339.72","TOTALAMT": "42.0","TOTALCOST": "0.0","BALCURR": "339.72","BALCURR0": "0.0","QKCURR": "339.72","FREECURR": "0.0","CHECKCURR": "4339.72"
				// ,"PAYLIST":[{"PAYID":"5679","PAYNAME":"现金","PAYNO":"0","CURR":"1000"},{"PAYID":"14463","PAYNAME":"刷卡","PAYNO":"1","CURR":"2000"},{"PAYID":"14464","PAYNAME":"转账","PAYNO":"2","CURR":"1000"}]}
				jsonObject = JSONObject.fromObject(jsonstr);

				totalcurr = Float.parseFloat(jsonObject.getString("TOTALCURR"));
				totalamt = Float.parseFloat(jsonObject.getString("TOTALAMT"));
				balcurr = Float.parseFloat(jsonObject.getString("BALCURR"));
				balcurr0 = Float.parseFloat(jsonObject.getString("BALCURR0"));
				qkcurr = Float.parseFloat(jsonObject.getString("QKCURR"));
				freecurr = Float.parseFloat(jsonObject.getString("FREECURR"));
				checkcurr = Float.parseFloat(jsonObject.getString("CHECKCURR"));
			}
		}

		String path = Func.getRootPath();
		String filename = "xs" + Func.desEncode(Accid + "_" + Noteno+"_"+Func.getRandomNumA(5)).toLowerCase() + ".html";
		String htmlfile = path + "/temp/" + filename;
		StringBuilder sb = new StringBuilder();
		try {
			PrintStream printStream = new PrintStream(new FileOutputStream(htmlfile));
			//System.out.println("1111");

			sb.append("<!DOCTYPE html>");
			sb.append("<html lang=\"zh-CN\">");
			sb.append("<head>");
			sb.append("  <meta charset=\"UTF-8\">");
			sb.append("  <meta http-equiv=\"x-ua-compatible\">");
			sb.append("  <link rel=\"shortcut icon\" href=\"../share/note/favicon.ico\">");
			sb.append("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, user-scalable=no\">");
			sb.append("  <title>蓝窗店管家</title>");
			sb.append("  <link rel=\"stylesheet\" href=\"../share/note/lib/bootstrap/css/bootstrap.css\">");
			sb.append("  <link rel=\"stylesheet\" href=\"../share/note/css/documents.css\">");
			sb.append("</head>");
			sb.append("<body>");
			sb.append("        <!--顶部提示-->");
			sb.append("  <div class=\"top_info tick-bgc\">");
			if (!company.equals("")) // 公司抬头名称
				sb.append("      <div>" + company + "</div>");
			sb.append("      <div class=\"document_type\">");
			sb.append("        <span>" + notetitle + "</span>");
			sb.append("      </div>");
			sb.append("  </div>");
			// <!--/顶部提示-->
			// <!--顶部单据信息-->
			sb.append("<div class=\"container top_note_info\">");
			sb.append("    <div>");
			sb.append("        <span class=\"text-align-justify float-left  tick-label-width light-text-color\" >单据号</span><span>:</span>");
			sb.append("        <span class=\"note_no\">" + Noteno + "</span>");
			sb.append("    </div>");
			sb.append("    <div>");
			sb.append("        <span class=\"text-align-justify tick-label-width float-left light-text-color\" >日期</span><span>:</span>");
			sb.append("        <span class=\"note_date\">" + tb.getRow(0).get("NOTEDATE").toString() + "</span>"); // 单据日期
			sb.append("    </div>");
			sb.append("    <div>");
			sb.append("         <span class=\"text-align-justify float-left  tick-label-width light-text-color\">店铺</span><span>:</span>");
			sb.append("         <span class=\"house_name\">" + tb.getRow(0).get("HOUSENAME").toString() + "</span>"); // 店铺
			sb.append("    </div>");
			if (fs == 0) // 会员
			{
				sb.append("     <div>");
				sb.append("         <span class=\"text-align-justify float-left  tick-label-width light-text-color\">会员</span><span>:</span>");
				sb.append("         <span class=\"house_name\">" + tb.getRow(0).get("GUESTNAME").toString() + " " + tb.getRow(0).get("VIPNO").toString() + "</span>"); // 店铺
				sb.append("     </div>");
			} else // 客户
			{
				sb.append("     <div>");
				sb.append("         <span class=\"text-align-justify float-left  tick-label-width light-text-color\">客户</span><span>:</span>");
				sb.append("         <span class=\"house_name\">" + tb.getRow(0).get("CUSTNAME").toString() + "</span>"); // 店铺
				sb.append("     </div>");
			}
			sb.append("     <div class=\"top_note_info_lastline\">");
			sb.append("        <div class=\"hand_no_content\">");
			sb.append("            <span class=\"text-align-justify float-left  tick-label-width light-text-color\">自编号</span><span>:</span>");
			sb.append("            <span class=\"hand_no\">" + tb.getRow(0).get("HANDNO").toString() + "</span>");
			sb.append("        </div>");
			// sb.append(" <div class=\"operant_content\">");
			// sb.append(" <span class=\"light-text-color\">制单人：</span>");
			// sb.append(" <span class=\"operant\">" + tb.getRow(0).get("OPERANT").toString() + "</span>"); // 制单人
			// sb.append(" </div>");
			sb.append("    </div>");
			sb.append(" </div>");
			// <!--/顶部单据信息-->

			// <!--/单据详情-->

			sb.append("<div class=\"container note_detail\">");
			sb.append("  <div class=\"row note_detail_title\">");
			sb.append("    <div class=\"col-xs-4 text-left\">");
			sb.append("         <div class=\"commodity_tick_title\">");
			sb.append("         <span class=\"light-text-color\">商品_颜色_尺码</span>");
			sb.append("        </div>");
			sb.append("    </div>");
			sb.append("    <div class=\"col-xs-2 text-center\">");
			sb.append("         <div class=\"commodity_amount_title\">");
			sb.append("         <span class=\"light-text-color\">数量</span>");
			sb.append("         </div>");
			sb.append("    </div>");
			sb.append("    <div class=\"col-xs-2 text-center\">");
			sb.append("         <div class=\"commodity_unit_price_title\">");
			sb.append("         <span class=\"light-text-color\">单价</span>");
			sb.append("    </div>");
			sb.append("    </div>");
			sb.append("    <div class=\"col-xs-2 text-center\">");
			sb.append("         <div class=\"commodity_discount_title\">");
			sb.append("         <span class=\"light-text-color\">折扣</span>");
			sb.append("        </div>");
			sb.append("    </div>");
			sb.append("    <div class=\"col-xs-2 text-right\">");
			sb.append("      <div class=\"commodity_total_curr_title\">");
			sb.append("          <span class=\"light-text-color\">金额</span>");
			sb.append("      </div>");
			sb.append("    </div>");
			sb.append("  </div>");

			qry = "select a.wareid,a.colorid,a.sizeid,b.warename,b.units,b.wareno,c.colorname,d.sizename,d.sizeno";
			qry += ",a.amount,a.price0,a.discount,a.price,a.curr,a.remark0,a.id";
			qry += " from wareoutm a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join colorcode c on a.colorid=c.colorid";
			qry += " left outer join sizecode d on a.sizeid=d.sizeid";
			qry += " where a.accid=" + Accid + " and a.noteno='" + Noteno + "'";
			qry += " order by b.wareno,c.colorname,d.sizeno,a.id";
			//System.out.println("111111-a");
			Table tb1 = DbHelperSQL.Query(qry).getTable(1);
			int count = tb1.getRowCount();
			//System.out.println("222222");
			// 商品记录
			// for (int i = 0; i < tb1.getRowCount(); i++) {
			int i = 0;
			String wareno = "";
			String wareno0 = "";
			// totalcurr=0;
			while (i < count) {
				wareno = tb1.getRow(i).get("WARENO").toString();
				wareno0 = wareno;
				sb.append(" <div class=\"row note_detail_content\">");
				sb.append("    <div class=\"col-xs-10 text-left\">");
				sb.append("        <div class=\"commodity_tick\">");
				sb.append("            <span>" + tb1.getRow(i).get("WARENAME").toString() + "(" + wareno + ")" + "</span>");
				sb.append("        </div>");
				sb.append("    </div>");
				sb.append("    <div class=\"col-xs-2 text-right\">");
				sb.append("        <div class=\"commodity_tick\">");
				sb.append("            <span>" + tb1.getRow(i).get("UNITS").toString() + "</span>");
				sb.append("        </div>");
				sb.append("    </div>");
				sb.append("</div>");
				Float xjamount = (float) 0;
				Float xjcurr = (float) 0;
				// sb.append(" <div class=\"col-xs-2 text-center\">");
				while (wareno0.equals(wareno) && i < count) {

					Float amount = Float.parseFloat(tb1.getRow(i).get("AMOUNT").toString());
					// Float price = Float.parseFloat(tb1.getRow(i).get("PRICE").toString());
					Float price0 = Float.parseFloat(tb1.getRow(i).get("PRICE0").toString());
					Float discount = Float.parseFloat(tb1.getRow(i).get("DISCOUNT").toString());
					Float curr = Float.parseFloat(tb1.getRow(i).get("CURR").toString());
					sb.append(" <div class=\"row note_detail_content\">");
					sb.append("    <div class=\"col-xs-3 text-center\">");
					sb.append("       <div class=\"commodity_tick\">");
					// sb.append(" <span> </span>");
					sb.append("       <span>" + tb1.getRow(i).get("COLORNAME").toString() + "</span>");

					sb.append("       </div>");
					sb.append("    </div>");
					sb.append("    <div class=\"col-xs-1 text-center\">");
					sb.append("       <div class=\"commodity_tick\">");
					// sb.append(" <span> </span>");
					sb.append("       <span>" + tb1.getRow(i).get("SIZENAME").toString() + "</span>");

					sb.append("       </div>");
					sb.append("    </div>");
					sb.append("    <div class=\"col-xs-2 text-center\">");
					sb.append("        <div class=\"commodity_amount\">");
					sb.append("            <span>" + Func.FormatNumber(amount) + "</span>"); // 数量
					sb.append("        </div>");
					sb.append("    </div>");
					sb.append("    <div class=\"col-xs-2 text-center\">");
					sb.append("        <div class=\"commodity_unit_price\">");
					sb.append("            <span>" + Func.FormatNumber(price0, 2) + "</span>");// 单价
					sb.append("        </div>");
					sb.append("    </div>");
					sb.append("    <div class=\"col-xs-2 text-center\">");
					sb.append("        <div class=\"commodity_discount\">");
					sb.append("            <span>" + Func.FormatNumber(discount, 2) + "</span>"); // 折扣
					sb.append("        </div>");
					sb.append("    </div>");
					sb.append("    <div class=\"col-xs-2 text-right\">");
					sb.append("        <div class=\"commodity_total_curr\">");
					sb.append("            <span>" + Func.FormatNumber(curr, 2) + "</span>");// 金额
					sb.append("        </div>");
					sb.append("    </div>");
					sb.append("</div>");

					i++;
					if (i < count)
						wareno0 = tb1.getRow(i).get("WARENO").toString();
					else
						wareno0 = "******";

					xjamount += amount;
					xjcurr += curr;
					// totalcurr+= curr;
				}
				sb.append(" <!--小计-->");
				sb.append(" <div class=\"row note_detail_content note_detail_content_dashline\">");
				sb.append("    <div class=\"col-xs-3 text-center\">");
				sb.append("        <div class=\"commodity_tick\">");
				sb.append("            <span  class=\"light-text-color\">小计</span>");
				sb.append("        </div>");
				sb.append("    </div>");

				// sb.append(" <div class=\"row note_detail_content\">");
				sb.append("    <div class=\"col-xs-1 text-center\">");
				sb.append("        <div class=\"commodity_tick\">");
				sb.append("            <span  class=\"light-text-color\"></span>");
				sb.append("        </div>");
				sb.append("    </div>");

				sb.append("    <div class=\"col-xs-2 text-center\">");
				sb.append("        <div class=\"commodity_amount\">");
				sb.append("            <span  class=\"light-text-color\">" + Func.FormatNumber(xjamount) + "</span>"); // 数量
				sb.append("        </div>");
				sb.append("    </div>");
				// sb.append(" <div class=\"col-xs-2 text-center\">");
				// sb.append(" <div class=\"commodity_unit_price\">");
				// sb.append(" <span> </span>");// 单价
				// sb.append(" </div>");
				// sb.append(" </div>");
				// sb.append(" <div class=\"col-xs-2 text-center\">");
				// sb.append(" <div class=\"commodity_discount\">");
				// sb.append(" <span></span>"); // 折扣
				// sb.append(" </div>");
				// sb.append(" </div>");
				sb.append("    <div class=\"col-xs-6 text-right\">");
				sb.append("        <div class=\"commodity_total_curr\">");
				sb.append("            <span  class=\"light-text-color\">" + Func.FormatNumber(xjcurr, 2) + "</span>");// 金额
				sb.append("        </div>");
				sb.append("    </div>");
				sb.append("</div>");
			}

			// for (int i = 0; i < count; i++) {
			// Float amount = Float.parseFloat(tb1.getRow(i).get("AMOUNT").toString());
			// // Float price = Float.parseFloat(tb1.getRow(i).get("PRICE").toString());
			// Float price0 = Float.parseFloat(tb1.getRow(i).get("PRICE0").toString());
			// Float discount = Float.parseFloat(tb1.getRow(i).get("DISCOUNT").toString());
			// Float curr = Float.parseFloat(tb1.getRow(i).get("CURR").toString());
			// sb.append(" <div class=\"row note_detail_content\">");
			// sb.append(" <div class=\"col-xs-4 text-left\">");
			// sb.append(" <div class=\"commodity_tick\">");
			// sb.append(" <span>" + tb1.getRow(i).get("WARENAME").toString() + " " + tb1.getRow(i).get("COLORNAME").toString() + " " + tb1.getRow(i).get("SIZENAME").toString() + "</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <div class=\"col-xs-2 text-center\">");

			// sb.append(" <div class=\"commodity_amount\">");
			// sb.append(" <span>" + Func.FormatNumber(amount) + "</span>"); // 数量
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <div class=\"col-xs-2 text-center\">");
			// sb.append(" <div class=\"commodity_unit_price\">");
			// sb.append(" <span>" + Func.FormatNumber(price0, 2) + "</span>");// 单价
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <div class=\"col-xs-2 text-center\">");
			// sb.append(" <div class=\"commodity_discount\">");
			// sb.append(" <span>" + Func.FormatNumber(discount, 2) + "</span>"); // 折扣
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <div class=\"col-xs-2 text-right\">");
			// sb.append(" <div class=\"commodity_total_curr\">");
			// sb.append(" <span>" + Func.FormatNumber(curr, 2) + "</span>");// 金额
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append("</div>");
			//
			// }
			//System.out.println("333333");

			sb.append(" </div>");

			sb.append(" <!--底部单据信息-->");
			sb.append(" <div class=\"container bottom_note_info\">");
			sb.append(" <div class=\"bottom_note_info_content\">");
			// <!--总计-->
			sb.append(" <div class=\"row\">");
			sb.append(" <div class=\"col-xs-4 text-left\">");
			sb.append(" <div>");
			sb.append(" <span class=\"text-align-justify float-left tick-label-width light-text-color\">总计</span><span>:</span>");
			sb.append(" </div>");
			sb.append(" </div>");
			sb.append(" <div class=\"col-xs-2 text-center\">");
			sb.append(" <div class=\"combined_amount\">");
			sb.append(" <span>" + Func.FormatNumber(totalamt) + "</span>");
			sb.append(" </div>");
			sb.append(" </div>");
			sb.append(" <div class=\"col-xs-6 text-right\">");
			sb.append(" <div class=\"combined_curr\">");
			sb.append(" <span>" + Func.FormatNumber(totalcurr, 2) + "</span>");
			sb.append(" </div>");
			sb.append(" </div>");
			sb.append(" </div>");
			sb.append(" <!--优惠金额-->");
			sb.append(" <div class=\"row\">");
			sb.append("    <div class=\"col-xs-5 text-left\">");
			sb.append("      <div>");
			sb.append("      <span class=\"text-align-justify float-left tick-label-width light-text-color\">免单金额</span><span>:</span>");
			sb.append("      </div>");
			sb.append("    </div>");
			sb.append("   <div class=\"col-xs-7 text-right\">");
			sb.append("      <div class=\"combined_curr\">");
			sb.append("         <span>" + Func.FormatNumber(freecurr, 2) + "</span>");
			sb.append("      </div>");
			sb.append("   </div>");
			sb.append(" </div>");

			sb.append(" <!--实结金额-->");
			sb.append(" <div class=\"row\">");
			sb.append("    <div class=\"col-xs-5 text-left\">");
			sb.append("      <div>");
			sb.append("        <span class=\"text-align-justify float-left tick-label-big-width font-size19 light-text-color\">实结金额</span><span>:</span>");
			sb.append("      </div>");
			sb.append("    </div>");
			sb.append("    <div class=\"col-xs-7 text-right\">");
			sb.append("       <div class=\"combined_curr\">");
			sb.append("          <span class=\"font-size19\">" + Func.FormatNumber(checkcurr, 2) + "</span>");
			sb.append("       </div>");
			sb.append("    </div>");
			sb.append(" </div>");

			Float totalpaycurr = (float) 0;
			if (jsonObject.has("PAYLIST")) {
				sb.append(" <!--结算方式-->");
				JSONArray jsonArray = jsonObject.getJSONArray("PAYLIST");
				// ,"PAYLIST":[{"PAYID":"5679","PAYNAME":"现金","PAYNO":"0","CURR":"1000"},{"PAYID":"14463","PAYNAME":"刷卡","PAYNO":"1","CURR":"2000"},{"PAYID":"14464","PAYNAME":"转账","PAYNO":"2","CURR":"1000"}]}

				for (int j = 0; j < jsonArray.size(); j++) {
					JSONObject jsonObject2 = jsonArray.getJSONObject(j);
					String payname = jsonObject2.getString("PAYNAME");
					Float paycurr = Float.parseFloat(jsonObject2.getString("CURR"));
					sb.append(" <div class=\"row\">");
					sb.append("    <div class=\"col-xs-7 text-left\">");
					sb.append("      <div>");
					sb.append("         <span class=\"text-align-justify float-left tick-label-width font-size19 light-text-color\">" + payname + "</span><span>:</span>");
					sb.append("      </div>");
					sb.append("    </div>");
					sb.append("    <div class=\"col-xs-5 text-right\">");
					sb.append("       <div class=\"combined_curr\">");
					sb.append("          <span class=\"font-size19\">" + Func.FormatNumber(paycurr, 2) + "</span>");
					sb.append("       </div>");
					sb.append("    </div>");
					sb.append(" </div>");
					totalpaycurr += paycurr;
				}
			}

			sb.append(" <!--收款合计-->");
			sb.append(" <div class=\"row\">");
			sb.append("    <div class=\"col-xs-6 text-left\">");
			sb.append("      <div>");
			sb.append("        <span class=\"text-align-justify float-left tick-label-big-width font-size19 light-text-color\">收款合计</span><span>:</span>");
			sb.append("      </div>");
			sb.append("    </div>");
			sb.append("    <div class=\"col-xs-6 text-right\">");
			sb.append("       <div class=\"combined_curr\">");
			sb.append("          <span class=\"font-size19\">" + Func.FormatNumber(totalpaycurr, 2) + "</span>");
			sb.append("       </div>");
			sb.append("    </div>");
			sb.append(" </div>");
			sb.append(" <!--上次欠款-->");
			sb.append(" <div class=\"row\">");
			sb.append("    <div class=\"col-xs-6 text-left\">");
			sb.append("      <div>");
			sb.append("        <span class=\"text-align-justify float-left tick-label-big-width font-size19 light-text-color\">上次欠款</span><span>:</span>");
			sb.append("      </div>");
			sb.append("    </div>");
			sb.append("    <div class=\"col-xs-6 text-right\">");
			sb.append("       <div class=\"combined_curr\">");
			sb.append("          <span class=\"font-size19\">" + Func.FormatNumber(balcurr0, 2) + "</span>");
			sb.append("       </div>");
			sb.append("    </div>");
			sb.append(" </div>");

			sb.append(" <!--本单欠款-->");
			sb.append(" <div class=\"row\">");
			sb.append("    <div class=\"col-xs-6 text-left\">");
			sb.append("      <div>");
			sb.append("        <span class=\"text-align-justify float-left tick-label-big-width font-size19 light-text-color\">本单欠款</span><span>:</span>");
			sb.append("      </div>");
			sb.append("    </div>");
			sb.append("    <div class=\"col-xs-6 text-right\">");
			sb.append("       <div class=\"combined_curr\">");
			sb.append("          <span class=\"font-size19\">" + Func.FormatNumber(qkcurr, 2) + "</span>");
			sb.append("       </div>");
			sb.append("    </div>");
			sb.append(" </div>");
			sb.append(" <!--欠款余额-->");
			sb.append(" <div class=\"row\">");
			sb.append("    <div class=\"col-xs-6 text-left\">");
			sb.append("      <div>");
			sb.append("        <span class=\"text-align-justify float-left tick-label-big-width font-size19 light-text-color\">累计欠款</span><span>:</span>");
			sb.append("      </div>");
			sb.append("    </div>");
			sb.append("    <div class=\"col-xs-6 text-right\">");
			sb.append("       <div class=\"combined_curr\">");
			sb.append("          <span class=\"font-size19\">" + Func.FormatNumber(balcurr, 2) + "</span>");
			sb.append("       </div>");
			sb.append("    </div>");
			sb.append(" </div>");

			if (fs == 0) {// 零售单有找零金额
				Float changecurr = Float.parseFloat(tb.getRow(0).get("CHANGECURR").toString());
				sb.append(" <!--找零-->");
				sb.append(" <div class=\"row\">");
				sb.append("   <div class=\"col-xs-6 text-left\">");
				sb.append("     <div>");
				sb.append("       <span class=\"text-align-justify float-left tick-label-width light-text-color\">找零</span><span>:</span>");
				sb.append("     </div>");
				sb.append("   </div>");
				sb.append("   <div class=\"col-xs-6 text-right\">");
				sb.append("      <div class=\"combined_curr\">");
				sb.append("      <span>" + Func.FormatNumber(changecurr, 2) + "</span>");
				sb.append("      </div>");
				sb.append("   </div>");
				sb.append(" </div>");
			}
			sb.append(" </div>");
			sb.append(" </div>");
			sb.append(" <!--/底部单据信息-->");
			sb.append(" <!--脚部-->");
			sb.append(" <div class=\"container note_footer\">");
			sb.append(" <!--销售人-->");
			sb.append(" <div class=\"row\">");
			sb.append("    <div class=\"col-xs-12 text-left\">");
			sb.append("      <div>");
			sb.append("        <span class=\"text-align-justify float-left tick-label-width light-text-color\">销售人</span><span>:</span>");
			sb.append("        <span>" + salemanlist + "</span>");
			sb.append("      </div>");
			sb.append("    </div>");
			sb.append(" </div>");
			sb.append(" <!--地址-->");
			sb.append(" <div class=\"row\">");
			sb.append("    <div class=\"col-xs-12 text-left\">");
			sb.append("      <div>");
			sb.append("        <span class=\"text-align-justify float-left tick-label-width light-text-color\">地址</span><span>:</span>");
			sb.append("        <span>" + house_address + "</span>");
			sb.append("      </div>");
			sb.append("    </div>");
			sb.append(" </div>");
			sb.append(" <!--电话-->");
			sb.append(" <div class=\"row\">");
			sb.append("    <div class=\"col-xs-12 text-left\">");
			sb.append("      <div>");
			sb.append("        <span class=\"text-align-justify float-left tick-label-width light-text-color\">电话</span><span>:</span>");
			sb.append("        <span>" + house_tel + "</span>");
			sb.append("      </div>");
			sb.append("    </div>");
			sb.append(" </div>");
			sb.append(" <!--备注-->");
			sb.append(" <div class=\"row\">");
			sb.append("    <div class=\"col-xs-12 text-left\">");
			sb.append("       <div>");
			sb.append("         <span class=\"text-align-justify float-left tick-label-width light-text-color\">备注</span><span>:</span>");
			sb.append("       </div>");
			sb.append("    </div>");
			sb.append(" </div>");

			sb.append(" <!--制单人-->");
			sb.append(" <div class=\"row\">");
			sb.append("    <div class=\"col-xs-6 text-left\">");
			sb.append("      <div>");
			sb.append("        <span class=\"text-align-justify float-left tick-label-width light-text-color\">制单人</span><span>:</span>");
			sb.append("        <span>" + operant + "</span>");
			sb.append("      </div>");
			sb.append("    </div>");
			// 分享时间
			sb.append("    <div class=\"col-xs-6 text-right\">");
			sb.append("        <div class=\"commodity_time\">");
			sb.append("            <span class=\"light-text-color\">" + Func.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss") + "</span>");// 金额
			sb.append("        </div>");
			sb.append("    </div>");

			sb.append(" </div>");

			sb.append(" </div>");

			sb.append(" <!--/脚部-->");
			sb.append(" <div class=\"advertising container\">");
			sb.append(" <a class=\"light-text-color\" href=\"http://www.skydispark.com\" >");
			sb.append(" <img src=\"../share/note/icon.png\"  style=\"width: 15px;height: 15px;\" >");
			sb.append(" <span>我在用</span><span class=\"tick-text-color\">蓝窗店管家</span><span>管理店铺，推荐你也使用</span>");
			sb.append(" </a>");
			sb.append(" </div>");
			sb.append(" </body>");
			sb.append(" </html>");

			printStream.println(sb.toString());
			printStream.close();
			errmess = "/temp/" + filename;
			return 1;
			// printStream.
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			errmess = "操作异常！" + e.getMessage();
			return 0;
		}

	}

	// 分享客户订单
	public int doShareCustorder() {
		if (Noteno.equals("")) {
			errmess = "单据号无效!";
			return 0;
		}
		// if (Noteno.length() > 0) {
		// errmess = "正在评测，即将推出，敬请期待!";
		// return 0;
		// }

		//System.out.println(" begin " + Noteno);
		Table tb = new Table();
		String company = "";
		String tel = "";
		String address = "";
		String qry = "select company,tel,address from accreg where accid=" + Accid;
		tb = DbHelperSQL.Query(qry).getTable(1);// DbHelperSQL.GetTable(qry);
		if (tb.getRowCount() > 0) {
			company = tb.getRow(0).get("COMPANY").toString();
			// tel = tb.getRow(0).get("TEL").toString();
			// address = tb.getRow(0).get("ADDRESS").toString();
		}
		//System.out.println("111 ");

		String notetitle = "";
		qry = "select a.noteno,a.notedate,a.operant,a.totalamt,a.totalcurr,a.remark,a.handno";
		qry += " ,c.custname,c.address,c.tel,a.orderno";
		qry += " from custorderh a ";
		qry += " left outer join customer c on a.custid=c.custid ";
		qry += " where a.accid=" + Accid + " and a.noteno='" + Noteno + "'";
		qry += " and a.statetag=1";
		notetitle = "客户订单";

		tb = DbHelperSQL.Query(qry).getTable(1);// DbHelperSQL.GetTable(qry);
		if (tb.getRowCount() == 0) {
			errmess = "未找到单据！";
			return 0;
		}

		// String cust_address = tb.getRow(0).get("ADDRESS").toString();
		// String cust_tel = tb.getRow(0).get("TEL").toString();

		String operant = tb.getRow(0).get("OPERANT").toString();
		Float totalcurr = Float.parseFloat(tb.getRow(0).get("TOTALCURR").toString());
		Float totalamt = Float.parseFloat(tb.getRow(0).get("TOTALAMT").toString());
		// JSONObject jsonObject = null;

		String path = Func.getRootPath();
		String filename = "ko" + Func.desEncode(Accid + "_" + Noteno+"_"+Func.getRandomNumA(5)).toLowerCase() + ".html";
		String htmlfile = path + "/temp/" + filename;
		StringBuilder sb = new StringBuilder();
		try {
			PrintStream printStream = new PrintStream(new FileOutputStream(htmlfile));
			//System.out.println("1111");

			sb.append("<!DOCTYPE html>");
			sb.append("<html lang=\"zh-CN\">");
			sb.append("<head>");
			sb.append("  <meta charset=\"UTF-8\">");
			sb.append("  <meta http-equiv=\"x-ua-compatible\">");
			sb.append("  <link rel=\"shortcut icon\" href=\"../share/note/favicon.ico\">");
			sb.append("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, user-scalable=no\">");
			sb.append("  <title>蓝窗店管家</title>");
			sb.append("  <link rel=\"stylesheet\" href=\"../share/note/lib/bootstrap/css/bootstrap.css\">");
			sb.append("  <link rel=\"stylesheet\" href=\"../share/note/css/documents.css\">");
			sb.append("</head>");
			sb.append("<body>");
			sb.append("        <!--顶部提示-->");
			sb.append("  <div class=\"top_info tick-bgc\">");
			if (!company.equals("")) // 公司抬头名称
				sb.append("      <div>" + company + "</div>");
			sb.append("      <div class=\"document_type\">");
			sb.append("        <span>" + notetitle + "</span>");
			sb.append("      </div>");
			sb.append("  </div>");
			// <!--/顶部提示-->
			// <!--顶部单据信息-->
			sb.append("<div class=\"container top_note_info\">");
			sb.append("    <div>");
			sb.append("        <span class=\"text-align-justify float-left  tick-label-width light-text-color\" >单据号</span><span>:</span>");
			sb.append("        <span class=\"note_no\">" + Noteno + "</span>");
			sb.append("    </div>");
			sb.append("    <div>");
			sb.append("        <span class=\"text-align-justify tick-label-width float-left light-text-color\" >日期</span><span>:</span>");
			sb.append("        <span class=\"note_date\">" + tb.getRow(0).get("NOTEDATE").toString() + "</span>"); // 单据日期
			sb.append("    </div>");
			sb.append("    <div>");
			sb.append("         <span class=\"text-align-justify float-left  tick-label-width light-text-color\">店铺</span><span>:</span>");
			sb.append("         <span class=\"house_name\">" + tb.getRow(0).get("HOUSENAME").toString() + "</span>"); // 店铺
			sb.append("    </div>");
			sb.append("     <div>");
			sb.append("         <span class=\"text-align-justify float-left  tick-label-width light-text-color\">客户</span><span>:</span>");
			sb.append("         <span class=\"house_name\">" + tb.getRow(0).get("CUSTNAME").toString() + "</span>"); // 店铺
			sb.append("     </div>");
			sb.append("     <div class=\"top_note_info_lastline\">");
			sb.append("        <div class=\"hand_no_content\">");
			sb.append("            <span class=\"text-align-justify float-left  tick-label-width light-text-color\">自编号</span><span>:</span>");
			sb.append("            <span class=\"hand_no\">" + tb.getRow(0).get("HANDNO").toString() + "</span>");
			sb.append("        </div>");
			// sb.append(" <div class=\"operant_content\">");
			// sb.append(" <span class=\"light-text-color\">制单人：</span>");
			// sb.append(" <span class=\"operant\">" + tb.getRow(0).get("OPERANT").toString() + "</span>"); // 制单人
			// sb.append(" </div>");
			sb.append("    </div>");
			sb.append(" </div>");
			// <!--/顶部单据信息-->

			// <!--/单据详情-->

			sb.append("<div class=\"container note_detail\">");
			sb.append("  <div class=\"row note_detail_title\">");
			sb.append("    <div class=\"col-xs-4 text-left\">");
			sb.append("         <div class=\"commodity_tick_title\">");
			sb.append("         <span class=\"light-text-color\">商品_颜色_尺码</span>");
			sb.append("        </div>");
			sb.append("    </div>");
			sb.append("    <div class=\"col-xs-2 text-center\">");
			sb.append("         <div class=\"commodity_amount_title\">");
			sb.append("         <span class=\"light-text-color\">数量</span>");
			sb.append("         </div>");
			sb.append("    </div>");
			sb.append("    <div class=\"col-xs-2 text-center\">");
			sb.append("         <div class=\"commodity_unit_price_title\">");
			sb.append("         <span class=\"light-text-color\">单价</span>");
			sb.append("    </div>");
			sb.append("    </div>");
			sb.append("    <div class=\"col-xs-2 text-center\">");
			sb.append("         <div class=\"commodity_discount_title\">");
			sb.append("         <span class=\"light-text-color\">折扣</span>");
			sb.append("        </div>");
			sb.append("    </div>");
			sb.append("    <div class=\"col-xs-2 text-right\">");
			sb.append("      <div class=\"commodity_total_curr_title\">");
			sb.append("          <span class=\"light-text-color\">金额</span>");
			sb.append("      </div>");
			sb.append("    </div>");
			sb.append("  </div>");

			qry = "select a.wareid,a.colorid,a.sizeid,b.warename,b.units,b.wareno,c.colorname,d.sizename,d.sizeno";
			qry += ",a.amount,a.price0,a.discount,a.price,a.curr,a.remark0,a.id";
			qry += " from custorderm a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join colorcode c on a.colorid=c.colorid";
			qry += " left outer join sizecode d on a.sizeid=d.sizeid";
			qry += " where a.accid=" + Accid + " and a.noteno='" + Noteno + "'";
			qry += " order by b.wareno,c.colorname,d.sizeno,a.id";
			//System.out.println("111111");
			Table tb1 = DbHelperSQL.Query(qry).getTable(1);
			int count = tb1.getRowCount();
			//System.out.println("222222");
			// 商品记录
			// for (int i = 0; i < tb1.getRowCount(); i++) {
			int i = 0;
			String wareno = "";
			String wareno0 = "";
			// totalcurr=0;
			while (i < count) {
				wareno = tb1.getRow(i).get("WARENO").toString();
				wareno0 = wareno;
				sb.append(" <div class=\"row note_detail_content\">");
				sb.append("    <div class=\"col-xs-10 text-left\">");
				sb.append("        <div class=\"commodity_tick\">");
				sb.append("            <span>" + tb1.getRow(i).get("WARENAME").toString() + "(" + wareno + ")" + "</span>");
				sb.append("        </div>");
				sb.append("    </div>");
				sb.append("    <div class=\"col-xs-2 text-right\">");
				sb.append("        <div class=\"commodity_tick\">");
				sb.append("            <span>" + tb1.getRow(i).get("UNITS").toString() + "</span>");
				sb.append("        </div>");
				sb.append("    </div>");
				sb.append("</div>");
				Float xjamount = (float) 0;
				Float xjcurr = (float) 0;
				// sb.append(" <div class=\"col-xs-2 text-center\">");
				while (wareno0.equals(wareno) && i < count) {
					Float amount = Float.parseFloat(tb1.getRow(i).get("AMOUNT").toString());
					// Float price = Float.parseFloat(tb1.getRow(i).get("PRICE").toString());
					Float price0 = Float.parseFloat(tb1.getRow(i).get("PRICE0").toString());
					Float discount = Float.parseFloat(tb1.getRow(i).get("DISCOUNT").toString());
					Float curr = Float.parseFloat(tb1.getRow(i).get("CURR").toString());
					sb.append(" <div class=\"row note_detail_content\">");
					sb.append("    <div class=\"col-xs-3 text-center\">");
					sb.append("       <div class=\"commodity_tick\">");
					// sb.append(" <span> </span>");
					sb.append("       <span>" + tb1.getRow(i).get("COLORNAME").toString() + "</span>");

					sb.append("       </div>");
					sb.append("    </div>");
					sb.append("    <div class=\"col-xs-1 text-center\">");
					sb.append("       <div class=\"commodity_tick\">");
					// sb.append(" <span> </span>");
					sb.append("       <span>" + tb1.getRow(i).get("SIZENAME").toString() + "</span>");

					sb.append("       </div>");
					sb.append("    </div>");
					sb.append("    <div class=\"col-xs-2 text-center\">");
					sb.append("        <div class=\"commodity_amount\">");
					sb.append("            <span>" + Func.FormatNumber(amount) + "</span>"); // 数量
					sb.append("        </div>");
					sb.append("    </div>");
					sb.append("    <div class=\"col-xs-2 text-center\">");
					sb.append("        <div class=\"commodity_unit_price\">");
					sb.append("            <span>" + Func.FormatNumber(price0, 2) + "</span>");// 单价
					sb.append("        </div>");
					sb.append("    </div>");
					sb.append("    <div class=\"col-xs-2 text-center\">");
					sb.append("        <div class=\"commodity_discount\">");
					sb.append("            <span>" + Func.FormatNumber(discount, 2) + "</span>"); // 折扣
					sb.append("        </div>");
					sb.append("    </div>");
					sb.append("    <div class=\"col-xs-2 text-right\">");
					sb.append("        <div class=\"commodity_total_curr\">");
					sb.append("            <span>" + Func.FormatNumber(curr, 2) + "</span>");// 金额
					sb.append("        </div>");
					sb.append("    </div>");
					sb.append("</div>");

					i++;
					if (i < count)
						wareno0 = tb1.getRow(i).get("WARENO").toString();
					else
						wareno0 = "******";
					xjamount += amount;
					xjcurr += curr;
					// totalcurr+= curr;
				}
				sb.append(" <!--小计-->");
				sb.append(" <div class=\"row note_detail_content note_detail_content_dashline\">");
				sb.append("    <div class=\"col-xs-3 text-center\">");
				sb.append("        <div class=\"commodity_tick\">");
				sb.append("            <span  class=\"light-text-color\">小计</span>");
				sb.append("        </div>");
				sb.append("    </div>");

				// sb.append(" <div class=\"row note_detail_content\">");
				sb.append("    <div class=\"col-xs-1 text-center\">");
				sb.append("        <div class=\"commodity_tick\">");
				sb.append("            <span  class=\"light-text-color\"></span>");
				sb.append("        </div>");
				sb.append("    </div>");

				sb.append("    <div class=\"col-xs-2 text-center\">");
				sb.append("        <div class=\"commodity_amount\">");
				sb.append("            <span  class=\"light-text-color\">" + Func.FormatNumber(xjamount) + "</span>"); // 数量
				sb.append("        </div>");
				sb.append("    </div>");
				// sb.append(" <div class=\"col-xs-2 text-center\">");
				// sb.append(" <div class=\"commodity_unit_price\">");
				// sb.append(" <span> </span>");// 单价
				// sb.append(" </div>");
				// sb.append(" </div>");
				// sb.append(" <div class=\"col-xs-2 text-center\">");
				// sb.append(" <div class=\"commodity_discount\">");
				// sb.append(" <span></span>"); // 折扣
				// sb.append(" </div>");
				// sb.append(" </div>");
				sb.append("    <div class=\"col-xs-6 text-right\">");
				sb.append("        <div class=\"commodity_total_curr\">");
				sb.append("            <span  class=\"light-text-color\">" + Func.FormatNumber(xjcurr, 2) + "</span>");// 金额
				sb.append("        </div>");
				sb.append("    </div>");
				sb.append("</div>");
			}

			sb.append(" </div>");

			sb.append(" <!--底部单据信息-->");
			sb.append(" <div class=\"container bottom_note_info\">");
			sb.append(" <div class=\"bottom_note_info_content\">");
			// <!--总计-->
			sb.append(" <div class=\"row\">");
			sb.append(" <div class=\"col-xs-4 text-left\">");
			sb.append(" <div>");
			sb.append(" <span class=\"text-align-justify float-left tick-label-width light-text-color\">总计</span><span>:</span>");
			sb.append(" </div>");
			sb.append(" </div>");
			sb.append(" <div class=\"col-xs-2 text-center\">");
			sb.append(" <div class=\"combined_amount\">");
			sb.append(" <span>" + Func.FormatNumber(totalamt) + "</span>");
			sb.append(" </div>");
			sb.append(" </div>");
			sb.append(" <div class=\"col-xs-6 text-right\">");
			sb.append(" <div class=\"combined_curr\">");
			sb.append(" <span>" + Func.FormatNumber(totalcurr, 2) + "</span>");
			sb.append(" </div>");
			sb.append(" </div>");
			sb.append(" </div>");

			// sb.append(" <!--优惠金额-->");
			// sb.append(" <div class=\"row\">");
			// sb.append(" <div class=\"col-xs-5 text-left\">");
			// sb.append(" <div>");
			// sb.append(" <span class=\"text-align-justify float-left tick-label-width light-text-color\">免单金额</span><span>:</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <div class=\"col-xs-7 text-right\">");
			// sb.append(" <div class=\"combined_curr\">");
			// sb.append(" <span>" + Func.FormatNumber(freecurr, 2) + "</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" </div>");

			// sb.append(" <!--实结金额-->");
			// sb.append(" <div class=\"row\">");
			// sb.append(" <div class=\"col-xs-5 text-left\">");
			// sb.append(" <div>");
			// sb.append(" <span class=\"text-align-justify float-left tick-label-big-width font-size19 light-text-color\">实结金额</span><span>:</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <div class=\"col-xs-7 text-right\">");
			// sb.append(" <div class=\"combined_curr\">");
			// sb.append(" <span class=\"font-size19\">" + Func.FormatNumber(checkcurr, 2) + "</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" </div>");

			// Float totalpaycurr = (float) 0;
			// if (jsonObject.has("PAYLIST")) {
			// sb.append(" <!--结算方式-->");
			// JSONArray jsonArray = jsonObject.getJSONArray("PAYLIST");
			// // ,"PAYLIST":[{"PAYID":"5679","PAYNAME":"现金","PAYNO":"0","CURR":"1000"},{"PAYID":"14463","PAYNAME":"刷卡","PAYNO":"1","CURR":"2000"},{"PAYID":"14464","PAYNAME":"转账","PAYNO":"2","CURR":"1000"}]}
			//
			// for (int j = 0; j < jsonArray.size(); j++) {
			// JSONObject jsonObject2 = jsonArray.getJSONObject(j);
			// String payname = jsonObject2.getString("PAYNAME");
			// Float paycurr = Float.parseFloat(jsonObject2.getString("CURR"));
			// sb.append(" <div class=\"row\">");
			// sb.append(" <div class=\"col-xs-7 text-left\">");
			// sb.append(" <div>");
			// sb.append(" <span class=\"text-align-justify float-left tick-label-width font-size19 light-text-color\">" + payname + "</span><span>:</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <div class=\"col-xs-5 text-right\">");
			// sb.append(" <div class=\"combined_curr\">");
			// sb.append(" <span class=\"font-size19\">" + Func.FormatNumber(paycurr, 2) + "</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// totalpaycurr += paycurr;
			// }
			// }

			// sb.append(" <!--收款合计-->");
			// sb.append(" <div class=\"row\">");
			// sb.append(" <div class=\"col-xs-6 text-left\">");
			// sb.append(" <div>");
			// sb.append(" <span class=\"text-align-justify float-left tick-label-big-width font-size19 light-text-color\">收款合计</span><span>:</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <div class=\"col-xs-6 text-right\">");
			// sb.append(" <div class=\"combined_curr\">");
			// sb.append(" <span class=\"font-size19\">" + Func.FormatNumber(totalpaycurr, 2) + "</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <!--上次欠款-->");
			// sb.append(" <div class=\"row\">");
			// sb.append(" <div class=\"col-xs-6 text-left\">");
			// sb.append(" <div>");
			// sb.append(" <span class=\"text-align-justify float-left tick-label-big-width font-size19 light-text-color\">上次欠款</span><span>:</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <div class=\"col-xs-6 text-right\">");
			// sb.append(" <div class=\"combined_curr\">");
			// sb.append(" <span class=\"font-size19\">" + Func.FormatNumber(balcurr0, 2) + "</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" </div>");

			// sb.append(" <!--本单欠款-->");
			// sb.append(" <div class=\"row\">");
			// sb.append(" <div class=\"col-xs-6 text-left\">");
			// sb.append(" <div>");
			// sb.append(" <span class=\"text-align-justify float-left tick-label-big-width font-size19 light-text-color\">本单欠款</span><span>:</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <div class=\"col-xs-6 text-right\">");
			// sb.append(" <div class=\"combined_curr\">");
			// sb.append(" <span class=\"font-size19\">" + Func.FormatNumber(qkcurr, 2) + "</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <!--欠款余额-->");
			// sb.append(" <div class=\"row\">");
			// sb.append(" <div class=\"col-xs-6 text-left\">");
			// sb.append(" <div>");
			// sb.append(" <span class=\"text-align-justify float-left tick-label-big-width font-size19 light-text-color\">欠款余额</span><span>:</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <div class=\"col-xs-6 text-right\">");
			// sb.append(" <div class=\"combined_curr\">");
			// sb.append(" <span class=\"font-size19\">" + Func.FormatNumber(balcurr, 2) + "</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" </div>");

			sb.append(" </div>");
			sb.append(" </div>");
			sb.append(" <!--/底部单据信息-->");
			sb.append(" <!--脚部-->");
			sb.append(" <div class=\"container note_footer\">");
			// sb.append(" <!--销售人-->");
			// sb.append(" <div class=\"row\">");
			// sb.append(" <div class=\"col-xs-12 text-left\">");
			// sb.append(" <div>");
			// sb.append(" <span class=\"text-align-justify float-left tick-label-width light-text-color\">销售人</span><span>:</span>");
			// sb.append(" <span>" + salemanlist + "</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			sb.append(" <!--地址-->");
			sb.append(" <div class=\"row\">");
			sb.append("    <div class=\"col-xs-12 text-left\">");
			sb.append("      <div>");
			sb.append("        <span class=\"text-align-justify float-left tick-label-width light-text-color\">地址</span><span>:</span>");
			sb.append("        <span>" + address + "</span>");
			sb.append("      </div>");
			sb.append("    </div>");
			sb.append(" </div>");
			sb.append(" <!--电话-->");
			sb.append(" <div class=\"row\">");
			sb.append("    <div class=\"col-xs-12 text-left\">");
			sb.append("      <div>");
			sb.append("        <span class=\"text-align-justify float-left tick-label-width light-text-color\">电话</span><span>:</span>");
			sb.append("        <span>" + tel + "</span>");
			sb.append("      </div>");
			sb.append("    </div>");
			sb.append(" </div>");
			sb.append(" <!--备注-->");
			sb.append(" <div class=\"row\">");
			sb.append("    <div class=\"col-xs-12 text-left\">");
			sb.append("       <div>");
			sb.append("         <span class=\"text-align-justify float-left tick-label-width light-text-color\">备注</span><span>:</span>");
			sb.append("       </div>");
			sb.append("    </div>");
			sb.append(" </div>");

			sb.append(" <!--制单人-->");
			sb.append(" <div class=\"row\">");
			sb.append("    <div class=\"col-xs-6 text-left\">");
			sb.append("      <div>");
			sb.append("        <span class=\"text-align-justify float-left tick-label-width light-text-color\">制单人</span><span>:</span>");
			sb.append("        <span>" + operant + "</span>");
			sb.append("      </div>");
			sb.append("    </div>");
			// 分享时间
			sb.append("    <div class=\"col-xs-6 text-right\">");
			sb.append("        <div class=\"commodity_time\">");
			sb.append("            <span  class=\"light-text-color\">" + Func.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss") + "</span>");// 金额
			sb.append("        </div>");
			sb.append("    </div>");

			sb.append(" </div>");

			sb.append(" </div>");

			sb.append(" <!--/脚部-->");
			sb.append(" <div class=\"advertising container\">");
			sb.append(" <a class=\"light-text-color\" href=\"http://www.skydispark.com\" >");
			sb.append(" <img src=\"../share/note/icon.png\"  style=\"width: 15px;height: 15px;\" >");
			sb.append(" <span>我在用</span><span class=\"tick-text-color\">蓝窗店管家</span><span>管理店铺，推荐你也使用</span>");
			sb.append(" </a>");
			sb.append(" </div>");
			sb.append(" </body>");
			sb.append(" </html>");

			printStream.println(sb.toString());
			printStream.close();
			errmess = "/temp/" + filename;
			return 1;
			// printStream.
		} catch (

		FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			errmess = "操作异常！" + e.getMessage();
			return 0;
		}

	}

	// 分享采购订单
	public int doShareProvorder() {
		if (Noteno.equals("")) {
			errmess = "单据号无效!";
			return 0;
		}
		// if (Noteno.length() > 0) {
		// errmess = "正在评测，即将推出，敬请期待!";
		// return 0;
		// }

		//System.out.println(" begin " + Noteno);
		Table tb = new Table();
		String company = "";
		String tel = "";
		String address = "";
		String qry = "select company,tel,address from accreg where accid=" + Accid;
		tb = DbHelperSQL.Query(qry).getTable(1);// DbHelperSQL.GetTable(qry);
		if (tb.getRowCount() > 0) {
			company = tb.getRow(0).get("COMPANY").toString();
			// tel = tb.getRow(0).get("TEL").toString();
			// address = tb.getRow(0).get("ADDRESS").toString();
		}
		//System.out.println("111 ");

		String notetitle = "";
		qry = "select a.noteno,a.notedate,a.operant,a.totalamt,a.totalcurr,a.remark,a.handno";
		qry += " ,c.provname,c.address,c.tel,a.orderno";
		qry += " from provorderh a ";
		qry += " left outer join provide c on a.provid=c.provid ";
		qry += " where a.accid=" + Accid + " and a.noteno='" + Noteno + "'";
		qry += " and a.statetag=1";
		notetitle = "采购订单";

		tb = DbHelperSQL.Query(qry).getTable(1);// DbHelperSQL.GetTable(qry);
		if (tb.getRowCount() == 0) {
			errmess = "未找到单据！";
			return 0;
		}

		// String prov_address = tb.getRow(0).get("ADDRESS").toString();
		// String prov_tel = tb.getRow(0).get("TEL").toString();

		String operant = tb.getRow(0).get("OPERANT").toString();
		Float totalcurr = Float.parseFloat(tb.getRow(0).get("TOTALCURR").toString());
		Float totalamt = Float.parseFloat(tb.getRow(0).get("TOTALAMT").toString());
		// JSONObject jsonObject = null;

		String path = Func.getRootPath();
		String filename = "co" + Func.desEncode(Accid + "_" + Noteno+"_"+Func.getRandomNumA(5)).toLowerCase() + ".html";
		String htmlfile = path + "/temp/" + filename;
		StringBuilder sb = new StringBuilder();
		try {
			PrintStream printStream = new PrintStream(new FileOutputStream(htmlfile));
			//System.out.println("1111");

			sb.append("<!DOCTYPE html>");
			sb.append("<html lang=\"zh-CN\">");
			sb.append("<head>");
			sb.append("  <meta charset=\"UTF-8\">");
			sb.append("  <meta http-equiv=\"x-ua-compatible\">");
			sb.append("  <link rel=\"shortcut icon\" href=\"../share/note/favicon.ico\">");
			sb.append("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, user-scalable=no\">");
			sb.append("  <title>蓝窗店管家</title>");
			sb.append("  <link rel=\"stylesheet\" href=\"../share/note/lib/bootstrap/css/bootstrap.css\">");
			sb.append("  <link rel=\"stylesheet\" href=\"../share/note/css/documents.css\">");
			sb.append("</head>");
			sb.append("<body>");
			sb.append("        <!--顶部提示-->");
			sb.append("  <div class=\"top_info tick-bgc\">");
			if (!company.equals("")) // 公司抬头名称
				sb.append("      <div>" + company + "</div>");
			sb.append("      <div class=\"document_type\">");
			sb.append("        <span>" + notetitle + "</span>");
			sb.append("      </div>");
			sb.append("  </div>");
			// <!--/顶部提示-->
			// <!--顶部单据信息-->
			sb.append("<div class=\"container top_note_info\">");
			sb.append("    <div>");
			sb.append("        <span class=\"text-align-justify float-left  tick-label-width light-text-color\" >单据号</span><span>:</span>");
			sb.append("        <span class=\"note_no\">" + Noteno + "</span>");
			sb.append("    </div>");
			sb.append("    <div>");
			sb.append("        <span class=\"text-align-justify tick-label-width float-left light-text-color\" >日期</span><span>:</span>");
			sb.append("        <span class=\"note_date\">" + tb.getRow(0).get("NOTEDATE").toString() + "</span>"); // 单据日期
			sb.append("    </div>");
			sb.append("    <div>");
			sb.append("         <span class=\"text-align-justify float-left  tick-label-width light-text-color\">店铺</span><span>:</span>");
			sb.append("         <span class=\"house_name\">" + tb.getRow(0).get("HOUSENAME").toString() + "</span>"); // 店铺
			sb.append("    </div>");
			sb.append("     <div>");
			sb.append("         <span class=\"text-align-justify float-left  tick-label-width light-text-color\">客户</span><span>:</span>");
			sb.append("         <span class=\"house_name\">" + tb.getRow(0).get("CUSTNAME").toString() + "</span>"); // 店铺
			sb.append("     </div>");
			sb.append("     <div class=\"top_note_info_lastline\">");
			sb.append("        <div class=\"hand_no_content\">");
			sb.append("            <span class=\"text-align-justify float-left  tick-label-width light-text-color\">自编号</span><span>:</span>");
			sb.append("            <span class=\"hand_no\">" + tb.getRow(0).get("HANDNO").toString() + "</span>");
			sb.append("        </div>");
			// sb.append(" <div class=\"operant_content\">");
			// sb.append(" <span class=\"light-text-color\">制单人：</span>");
			// sb.append(" <span class=\"operant\">" + tb.getRow(0).get("OPERANT").toString() + "</span>"); // 制单人
			// sb.append(" </div>");
			sb.append("    </div>");
			sb.append(" </div>");
			// <!--/顶部单据信息-->

			// <!--/单据详情-->

			sb.append("<div class=\"container note_detail\">");
			sb.append("  <div class=\"row note_detail_title\">");
			sb.append("    <div class=\"col-xs-4 text-left\">");
			sb.append("         <div class=\"commodity_tick_title\">");
			sb.append("         <span class=\"light-text-color\">商品_颜色_尺码</span>");
			sb.append("        </div>");
			sb.append("    </div>");
			sb.append("    <div class=\"col-xs-2 text-center\">");
			sb.append("         <div class=\"commodity_amount_title\">");
			sb.append("         <span class=\"light-text-color\">数量</span>");
			sb.append("         </div>");
			sb.append("    </div>");
			sb.append("    <div class=\"col-xs-2 text-center\">");
			sb.append("         <div class=\"commodity_unit_price_title\">");
			sb.append("         <span class=\"light-text-color\">单价</span>");
			sb.append("    </div>");
			sb.append("    </div>");
			sb.append("    <div class=\"col-xs-2 text-center\">");
			sb.append("         <div class=\"commodity_discount_title\">");
			sb.append("         <span class=\"light-text-color\">折扣</span>");
			sb.append("        </div>");
			sb.append("    </div>");
			sb.append("    <div class=\"col-xs-2 text-right\">");
			sb.append("      <div class=\"commodity_total_curr_title\">");
			sb.append("          <span class=\"light-text-color\">金额</span>");
			sb.append("      </div>");
			sb.append("    </div>");
			sb.append("  </div>");

			qry = "select a.wareid,a.colorid,a.sizeid,b.warename,b.units,b.wareno,c.colorname,d.sizename,d.sizeno";
			qry += ",a.amount,a.price0,a.discount,a.price,a.curr,a.remark0,a.id";
			qry += " from provorderm a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join colorcode c on a.colorid=c.colorid";
			qry += " left outer join sizecode d on a.sizeid=d.sizeid";
			qry += " where a.accid=" + Accid + " and a.noteno='" + Noteno + "'";
			qry += " order by b.wareno,c.colorname,d.sizeno,a.id";
			//System.out.println("111111");
			Table tb1 = DbHelperSQL.Query(qry).getTable(1);
			int count = tb1.getRowCount();
			//System.out.println("222222");
			// 商品记录
			// for (int i = 0; i < tb1.getRowCount(); i++) {
			int i = 0;
			String wareno = "";
			String wareno0 = "";
			// totalcurr=0;
			while (i < count) {
				wareno = tb1.getRow(i).get("WARENO").toString();
				wareno0 = wareno;
				sb.append(" <div class=\"row note_detail_content\">");
				sb.append("    <div class=\"col-xs-10 text-left\">");
				sb.append("        <div class=\"commodity_tick\">");
				sb.append("            <span>" + tb1.getRow(i).get("WARENAME").toString() + "(" + wareno + ")" + "</span>");
				sb.append("        </div>");
				sb.append("    </div>");
				sb.append("    <div class=\"col-xs-2 text-right\">");
				sb.append("        <div class=\"commodity_tick\">");
				sb.append("            <span>" + tb1.getRow(i).get("UNITS").toString() + "</span>");
				sb.append("        </div>");
				sb.append("    </div>");
				sb.append("</div>");
				Float xjamount = (float) 0;
				Float xjcurr = (float) 0;
				// sb.append(" <div class=\"col-xs-2 text-center\">");
				while (wareno0.equals(wareno) && i < count) {
					Float amount = Float.parseFloat(tb1.getRow(i).get("AMOUNT").toString());
					// Float price = Float.parseFloat(tb1.getRow(i).get("PRICE").toString());
					Float price0 = Float.parseFloat(tb1.getRow(i).get("PRICE0").toString());
					Float discount = Float.parseFloat(tb1.getRow(i).get("DISCOUNT").toString());
					Float curr = Float.parseFloat(tb1.getRow(i).get("CURR").toString());
					sb.append(" <div class=\"row note_detail_content\">");
					sb.append("    <div class=\"col-xs-3 text-center\">");
					sb.append("       <div class=\"commodity_tick\">");
					// sb.append(" <span> </span>");
					sb.append("       <span>" + tb1.getRow(i).get("COLORNAME").toString() + "</span>");

					sb.append("       </div>");
					sb.append("    </div>");
					sb.append("    <div class=\"col-xs-1 text-center\">");
					sb.append("       <div class=\"commodity_tick\">");
					// sb.append(" <span> </span>");
					sb.append("       <span>" + tb1.getRow(i).get("SIZENAME").toString() + "</span>");

					sb.append("       </div>");
					sb.append("    </div>");
					sb.append("    <div class=\"col-xs-2 text-center\">");
					sb.append("        <div class=\"commodity_amount\">");
					sb.append("            <span>" + Func.FormatNumber(amount) + "</span>"); // 数量
					sb.append("        </div>");
					sb.append("    </div>");
					sb.append("    <div class=\"col-xs-2 text-center\">");
					sb.append("        <div class=\"commodity_unit_price\">");
					sb.append("            <span>" + Func.FormatNumber(price0, 2) + "</span>");// 单价
					sb.append("        </div>");
					sb.append("    </div>");
					sb.append("    <div class=\"col-xs-2 text-center\">");
					sb.append("        <div class=\"commodity_discount\">");
					sb.append("            <span>" + Func.FormatNumber(discount, 2) + "</span>"); // 折扣
					sb.append("        </div>");
					sb.append("    </div>");
					sb.append("    <div class=\"col-xs-2 text-right\">");
					sb.append("        <div class=\"commodity_total_curr\">");
					sb.append("            <span>" + Func.FormatNumber(curr, 2) + "</span>");// 金额
					sb.append("        </div>");
					sb.append("    </div>");
					sb.append("</div>");

					i++;
					if (i < count)
						wareno0 = tb1.getRow(i).get("WARENO").toString();
					else
						wareno0 = "******";
					xjamount += amount;
					xjcurr += curr;
					// totalcurr+= curr;
				}
				sb.append(" <!--小计-->");
				sb.append(" <div class=\"row note_detail_content note_detail_content_dashline\">");
				sb.append("    <div class=\"col-xs-3 text-center\">");
				sb.append("        <div class=\"commodity_tick\">");
				sb.append("            <span  class=\"light-text-color\">小计</span>");
				sb.append("        </div>");
				sb.append("    </div>");

				// sb.append(" <div class=\"row note_detail_content\">");
				sb.append("    <div class=\"col-xs-1 text-center\">");
				sb.append("        <div class=\"commodity_tick\">");
				sb.append("            <span  class=\"light-text-color\"></span>");
				sb.append("        </div>");
				sb.append("    </div>");

				sb.append("    <div class=\"col-xs-2 text-center\">");
				sb.append("        <div class=\"commodity_amount\">");
				sb.append("            <span  class=\"light-text-color\">" + Func.FormatNumber(xjamount) + "</span>"); // 数量
				sb.append("        </div>");
				sb.append("    </div>");
				// sb.append(" <div class=\"col-xs-2 text-center\">");
				// sb.append(" <div class=\"commodity_unit_price\">");
				// sb.append(" <span> </span>");// 单价
				// sb.append(" </div>");
				// sb.append(" </div>");
				// sb.append(" <div class=\"col-xs-2 text-center\">");
				// sb.append(" <div class=\"commodity_discount\">");
				// sb.append(" <span></span>"); // 折扣
				// sb.append(" </div>");
				// sb.append(" </div>");
				sb.append("    <div class=\"col-xs-6 text-right\">");
				sb.append("        <div class=\"commodity_total_curr\">");
				sb.append("            <span  class=\"light-text-color\">" + Func.FormatNumber(xjcurr, 2) + "</span>");// 金额
				sb.append("        </div>");
				sb.append("    </div>");
				sb.append("</div>");
			}

			sb.append(" </div>");

			sb.append(" <!--底部单据信息-->");
			sb.append(" <div class=\"container bottom_note_info\">");
			sb.append(" <div class=\"bottom_note_info_content\">");
			// <!--总计-->
			sb.append(" <div class=\"row\">");
			sb.append(" <div class=\"col-xs-4 text-left\">");
			sb.append(" <div>");
			sb.append(" <span class=\"text-align-justify float-left tick-label-width light-text-color\">总计</span><span>:</span>");
			sb.append(" </div>");
			sb.append(" </div>");
			sb.append(" <div class=\"col-xs-2 text-center\">");
			sb.append(" <div class=\"combined_amount\">");
			sb.append(" <span>" + Func.FormatNumber(totalamt) + "</span>");
			sb.append(" </div>");
			sb.append(" </div>");
			sb.append(" <div class=\"col-xs-6 text-right\">");
			sb.append(" <div class=\"combined_curr\">");
			sb.append(" <span>" + Func.FormatNumber(totalcurr, 2) + "</span>");
			sb.append(" </div>");
			sb.append(" </div>");
			sb.append(" </div>");

			// sb.append(" <!--优惠金额-->");
			// sb.append(" <div class=\"row\">");
			// sb.append(" <div class=\"col-xs-5 text-left\">");
			// sb.append(" <div>");
			// sb.append(" <span class=\"text-align-justify float-left tick-label-width light-text-color\">免单金额</span><span>:</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <div class=\"col-xs-7 text-right\">");
			// sb.append(" <div class=\"combined_curr\">");
			// sb.append(" <span>" + Func.FormatNumber(freecurr, 2) + "</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" </div>");

			// sb.append(" <!--实结金额-->");
			// sb.append(" <div class=\"row\">");
			// sb.append(" <div class=\"col-xs-5 text-left\">");
			// sb.append(" <div>");
			// sb.append(" <span class=\"text-align-justify float-left tick-label-big-width font-size19 light-text-color\">实结金额</span><span>:</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <div class=\"col-xs-7 text-right\">");
			// sb.append(" <div class=\"combined_curr\">");
			// sb.append(" <span class=\"font-size19\">" + Func.FormatNumber(checkcurr, 2) + "</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" </div>");

			// Float totalpaycurr = (float) 0;
			// if (jsonObject.has("PAYLIST")) {
			// sb.append(" <!--结算方式-->");
			// JSONArray jsonArray = jsonObject.getJSONArray("PAYLIST");
			// // ,"PAYLIST":[{"PAYID":"5679","PAYNAME":"现金","PAYNO":"0","CURR":"1000"},{"PAYID":"14463","PAYNAME":"刷卡","PAYNO":"1","CURR":"2000"},{"PAYID":"14464","PAYNAME":"转账","PAYNO":"2","CURR":"1000"}]}
			//
			// for (int j = 0; j < jsonArray.size(); j++) {
			// JSONObject jsonObject2 = jsonArray.getJSONObject(j);
			// String payname = jsonObject2.getString("PAYNAME");
			// Float paycurr = Float.parseFloat(jsonObject2.getString("CURR"));
			// sb.append(" <div class=\"row\">");
			// sb.append(" <div class=\"col-xs-7 text-left\">");
			// sb.append(" <div>");
			// sb.append(" <span class=\"text-align-justify float-left tick-label-width font-size19 light-text-color\">" + payname + "</span><span>:</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <div class=\"col-xs-5 text-right\">");
			// sb.append(" <div class=\"combined_curr\">");
			// sb.append(" <span class=\"font-size19\">" + Func.FormatNumber(paycurr, 2) + "</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// totalpaycurr += paycurr;
			// }
			// }

			// sb.append(" <!--收款合计-->");
			// sb.append(" <div class=\"row\">");
			// sb.append(" <div class=\"col-xs-6 text-left\">");
			// sb.append(" <div>");
			// sb.append(" <span class=\"text-align-justify float-left tick-label-big-width font-size19 light-text-color\">收款合计</span><span>:</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <div class=\"col-xs-6 text-right\">");
			// sb.append(" <div class=\"combined_curr\">");
			// sb.append(" <span class=\"font-size19\">" + Func.FormatNumber(totalpaycurr, 2) + "</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <!--上次欠款-->");
			// sb.append(" <div class=\"row\">");
			// sb.append(" <div class=\"col-xs-6 text-left\">");
			// sb.append(" <div>");
			// sb.append(" <span class=\"text-align-justify float-left tick-label-big-width font-size19 light-text-color\">上次欠款</span><span>:</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <div class=\"col-xs-6 text-right\">");
			// sb.append(" <div class=\"combined_curr\">");
			// sb.append(" <span class=\"font-size19\">" + Func.FormatNumber(balcurr0, 2) + "</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" </div>");

			// sb.append(" <!--本单欠款-->");
			// sb.append(" <div class=\"row\">");
			// sb.append(" <div class=\"col-xs-6 text-left\">");
			// sb.append(" <div>");
			// sb.append(" <span class=\"text-align-justify float-left tick-label-big-width font-size19 light-text-color\">本单欠款</span><span>:</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <div class=\"col-xs-6 text-right\">");
			// sb.append(" <div class=\"combined_curr\">");
			// sb.append(" <span class=\"font-size19\">" + Func.FormatNumber(qkcurr, 2) + "</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <!--欠款余额-->");
			// sb.append(" <div class=\"row\">");
			// sb.append(" <div class=\"col-xs-6 text-left\">");
			// sb.append(" <div>");
			// sb.append(" <span class=\"text-align-justify float-left tick-label-big-width font-size19 light-text-color\">欠款余额</span><span>:</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" <div class=\"col-xs-6 text-right\">");
			// sb.append(" <div class=\"combined_curr\">");
			// sb.append(" <span class=\"font-size19\">" + Func.FormatNumber(balcurr, 2) + "</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" </div>");

			sb.append(" </div>");
			sb.append(" </div>");
			sb.append(" <!--/底部单据信息-->");
			sb.append(" <!--脚部-->");
			sb.append(" <div class=\"container note_footer\">");
			// sb.append(" <!--销售人-->");
			// sb.append(" <div class=\"row\">");
			// sb.append(" <div class=\"col-xs-12 text-left\">");
			// sb.append(" <div>");
			// sb.append(" <span class=\"text-align-justify float-left tick-label-width light-text-color\">销售人</span><span>:</span>");
			// sb.append(" <span>" + salemanlist + "</span>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			// sb.append(" </div>");
			sb.append(" <!--地址-->");
			sb.append(" <div class=\"row\">");
			sb.append("    <div class=\"col-xs-12 text-left\">");
			sb.append("      <div>");
			sb.append("        <span class=\"text-align-justify float-left tick-label-width light-text-color\">地址</span><span>:</span>");
			sb.append("        <span>" + address + "</span>");
			sb.append("      </div>");
			sb.append("    </div>");
			sb.append(" </div>");
			sb.append(" <!--电话-->");
			sb.append(" <div class=\"row\">");
			sb.append("    <div class=\"col-xs-12 text-left\">");
			sb.append("      <div>");
			sb.append("        <span class=\"text-align-justify float-left tick-label-width light-text-color\">电话</span><span>:</span>");
			sb.append("        <span>" + tel + "</span>");
			sb.append("      </div>");
			sb.append("    </div>");
			sb.append(" </div>");
			sb.append(" <!--备注-->");
			sb.append(" <div class=\"row\">");
			sb.append("    <div class=\"col-xs-12 text-left\">");
			sb.append("       <div>");
			sb.append("         <span class=\"text-align-justify float-left tick-label-width light-text-color\">备注</span><span>:</span>");
			sb.append("       </div>");
			sb.append("    </div>");
			sb.append(" </div>");

			sb.append(" <!--制单人-->");
			sb.append(" <div class=\"row\">");
			sb.append("    <div class=\"col-xs-6 text-left\">");
			sb.append("      <div>");
			sb.append("        <span class=\"text-align-justify float-left tick-label-width light-text-color\">制单人</span><span>:</span>");
			sb.append("        <span>" + operant + "</span>");
			sb.append("      </div>");
			sb.append("    </div>");
			// 分享时间
			sb.append("    <div class=\"col-xs-6 text-right\">");
			sb.append("        <div class=\"commodity_time\">");
			sb.append("            <span  class=\"light-text-color\">" + Func.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss") + "</span>");// 金额
			sb.append("        </div>");
			sb.append("    </div>");

			sb.append(" </div>");

			sb.append(" </div>");

			sb.append(" <!--/脚部-->");
			sb.append(" <div class=\"advertising container\">");
			sb.append("   <a class=\"light-text-color\" href=\"http://www.skydispark.com\" >");
			sb.append("   <img src=\"../share/note/icon.png\"  style=\"width: 15px;height: 15px;\" >");
			sb.append("   <span>我在用</span><span class=\"tick-text-color\">蓝窗店管家</span><span>管理店铺，推荐你也使用</span>");
			sb.append("   </a>");
			sb.append(" </div>");
			sb.append(" </body>");
			sb.append(" </html>");

			printStream.println(sb.toString());
			printStream.close();
			errmess = "/temp/" + filename;
			return 1;
			// printStream.
		} catch (

		FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			errmess = "操作异常！" + e.getMessage();
			return 0;
		}

	}

}
