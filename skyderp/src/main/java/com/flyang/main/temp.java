/**
 * 
 */
package com.flyang.main;

import java.io.IOException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.comdot.data.Table;
import com.common.tool.Func;
import com.oracle.bean.Employeprov;
import com.oracle.bean.Payway;
//import com.oracle.bean.Buycar;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Administrator // Err1047
 * 颜色方案 monokai
 */
public class temp {

//	  begin
//	  exception
//	  when others then 
//	  v_errmess:=substr(sqlerrm,1,200);
//	  end;
	
	//	if (DbHelperSQL.ExecuteSql(qry) < 0) {
	//		errmess = "操作失败！";
	//		return 0;
	//	} else {
	//		errmess = "操作成功！";
	//		return 1;
	//	}

	// String value = jsonObject.getString(name.toLowerCase()).trim().replace("'", "''");
	//	if (!fieldlist.toUpperCase().contains("A.PAYCURR"))
	//		fieldlist += ",A.PAYCURR";

	// long accid0 = jsonObject.has("accid") ? Long.parseLong(jsonObject.getString("accid")) : 0;
	// long accid = htp.getMaccid();
	// if (accid0 > 0)
	// accid = accid0;

	// try {
	// } catch (Exception e) {
	// }
	// finally
	// {
	// }

	// String lastop = htp.getUsername();
	// long accid = htp.getMaccid();
	// if (accid == 1000 && (lastop.equals("演示") || lastop.equals("枫杨"))) {
	// WriteResult(response, 0, "这是演示套账，不允许更改记录！");
	// return;
	// }

	// EXCEPTION WHEN NO_DATA_FOUND THEN
	// -------------------------------------------------------

	// String currdate = htp.getNowdate();// Func.DateToStr(pFunc.getServerdatetime(), "yyyy-MM-dd");
	// String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : currdate;
	// String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : currdate;
	// -------------------------------------------------------

	// DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
	// -------------------------------------------------------
	// Map<String, ProdParam> param = new HashMap<String, ProdParam>();
	// param.put("retcs", new ProdParam(Types.VARCHAR));
	// int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
	// if (ret < 0) {
	// errmess = "操作异常！";
	// return 0;
	// }
	// String retcs = param.get("retcs").getParamvalue().toString();
	//
	// errmess = retcs.substring(1);
	// return Integer.parseInt(retcs.substring(0, 1));
	// -------------------------------------------------------

	// {"warereclist":[{"recid":343},{"recid":654},{"recid":865}]}

	// if (jsonObject.has("warereclist")) {
	// JSONArray jsonArray = jsonObject.getJSONArray("warereclist");
	//
	// for (int i = 0; i < jsonArray.size(); i++) {
	// JSONObject jsonObject2 = jsonArray.getJSONObject(i);
	// }
	// }
	// -------------------------------------------------------
	// 取最近售价
	// qry += "\n select nvl(price,v_price0) into v_price1 from (";
	// qry += "\n select b.price from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
	// qry += "\n where a.accid=" + accid + " and a.statetag=1 and a.ntid=1 and b.wareid=" + wareid + " and a.custid=" + custid + " order by a.notedate desc,b.id desc";
	// qry += "\n ) where rownum=1;";

	// System.out.println("随机数: "+Func.getRandomNum(6)+" "+Func.getRandomNumA(6));
	// -------------------------------------------------------

	// String qry = "declare";
	// qry += " v_amount number;";
	// qry += " v_curr number;";
	// qry += " begin";
	// qry += " select sum(amount),sum(curr) into v_amount, v_curr from wareoutm where Accid=1000 and Noteno='XS2015081000001';";
	// qry += " select v_amount,v_curr,'XS2015081000001' into :amount,:curr,:Noteno from dual;";
	// qry += " end;";
	//
	// // 申请返回值
	// Map<String, ProdParam> param = new HashMap<String, ProdParam>();
	// param.put("amount", new ProdParam(Types.FLOAT));
	// param.put("curr", new ProdParam(Types.FLOAT));
	// param.put("Noteno", new ProdParam(Types.VARCHAR));
	// // 调用
	// DbHelperSQL.ExecuteProc(qry, param);
	// // 取返回值
	// float amount = Float.parseFloat(param.get("amount").getParamvalue().toString());
	// float curr = Float.parseFloat(param.get("curr").getParamvalue().toString());
	// String Noteno = param.get("Noteno").getParamvalue().toString();
	//
	// System.out.println(" amount=" + amount + " curr=" + curr + " Noteno=" + Noteno);
	// -------------------------------------------------------

	// String qry = "select id,Accid,Noteno,wareid,amount,curr from
	// wareoutm where Accid=11990 and Noteno='XS2017021600035' ";
	//
	// QueryParam qp = new QueryParam();
	// // WriteResult("0", "111111111111");
	// qp.setPageIndex(1);
	// qp.setPageSize(10);
	// qp.setSortString("id");
	// qp.setQueryString(qry);
	// qp.setSumString("nvl(sum(amount),0) as totalamt,nvl(sum(curr),0) as totalcurr");
	// Table tb = new Table();
	//
	// tb = DbHelperSQL.GetTable(qp);
	//
	// System.out.println("GetTable ok!");
	// // qp.SumString=""+tb.getRowCount();
	// Write(response, DbHelperSQL.DataTable2Json(tb,
	// qp.getTotalString()));

	// String fh="";
	// for (int j = 0; j < tb.getColumnCount(); j++) {//
	// String strKey = tb.getRow(0).getColumn(j + 1).getName().toUpperCase();//
	// String strValue = tb.getRow(0).get(j).toString();//
	// errmess += fh + "\"" + strKey + "\":\"" + strValue + "\"";
	// fh = ",";//
	// }
	
	//	cast('零售' as nvarchar2(100)) as custname
	
	// -------------------------------------------------------
	// String qxpublic = htp.getQxpublic();
	// // QXPUBLIC:用户自定系统参数
	// // 1234567890123456789012345678901234567890
	// // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// // |||_______________________3 启用最近售价
	// // ||________________________2 允许负数出库
	// // |_________________________1 启用权限控制
	// int qxbj = 0;
	// if (Func.subString(qxpublic, 1, 1).equals("1"))
	// qxbj = 1;// 启用权限管理

	// app接口
	// 原参数
	// QXPUBLIC:共40位，1-20:用户系统参数，21-30:用户角色参数(在角色中定义) 31-40为账户控制参数
	// 1234567890123456789012345678901234567890
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// ||||| |||||||||| |||| || |||||_35 1=开通订货会功能
	// ||||| |||||||||| |||| || ||||__34 1=订货会截屏锁住
	// ||||| |||||||||| |||| || |||___33 1=允许更改单据日期
	// ||||| |||||||||| |||| || ||____32 1=表示分店铺算成本
	// ||||| |||||||||| |||| || |_____31 1=表示允许转枫杨果
	// ||||| |||||||||| |||| ||_28 允许冲单
	// ||||| |||||||||| |||| |__27 允许撤单
	// ||||| |||||||||| ||||____24-26 允许查询天数（0表示不限止）
	// ||||| |||||||||| |||_____23 允许设置打印页眉页脚
	// ||||| |||||||||| ||______22 允许分享
	// ||||| |||||||||| |_______21 允许查看进价
	// ||||| ||||||||||__________19 批发启用收银台
	// ||||| |||||||||___________18 收银启用C端验证
	// ||||| ||||||||____________17 零售启用收银台
	// ||||| |||||||_____________16 零售抹零方式：0=取整，1=四舍五入,2=保留原数
	// ||||| |||||||_____________15 0=按店铺交班 1=按收银员交班
	// ||||| ||||||______________14 往来帐款分店铺核对
	// ||||| |||||_______________13 条码产生方式(0-8)
	// ||||| ||||________________12 自动生成货号
	// ||||| |||_________________11 配货单允许直接出库
	// ||||| ||__________________10 配货单审核后才允许拣货
	// ||||| |___________________9 采购入库自动更新进价
	// |||||_____________________5-8 积分抵扣比例（4位）
	// ||||______________________4 单价小数位数(0,1,2)
	// |||_______________________3 启用最近售价
	// ||________________________2 允许负数出库
	// |_________________________1 启用权限控制

	// 现参数
	// QXPUBLIC:用户自定系统参数 accreg->uqxcs
	// 1234567890123456789012345678901234567890
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// ||||| |||||||||||_______19 批发启用收银台
	// ||||| ||||||||||________18 收银启用C端验证
	// ||||| |||||||||_________17 零售启用收银台
	// ||||| ||||||||__________16 零售抹零方式：0=取整，1=四舍五入,2=保留原数
	// ||||| |||||||___________15 0=按店铺交班 1=按收银员交班
	// ||||| ||||||____________14 往来帐款分店铺核对
	// ||||| |||||_____________13 条码产生方式(0-9)
	// ||||| ||||______________12 自动生成货号
	// ||||| |||_______________11 配货单允许直接出库
	// ||||| ||________________10 配货单审核后才允许拣货
	// ||||| |_________________9 采购入库自动更新进价
	// |||||_____________________5-8 积分抵扣比例（4位）
	// ||||______________________4 单价小数位数(0,1,2)
	// |||_______________________3 启用最近售价
	// ||________________________2 允许负数出库
	// |_________________________1 启用权限控制
	// String qxpublic = htp.getQxpublic();
	//
	// int priceprec = 0;
	// try {
	// priceprec = Integer.parseInt(Func.subString(qxpublic, 4, 1));
	// } catch (Exception e) {
	// priceprec = 0;
	// }

	// ROLEPUBLIC角色控制参数 原qxpublic 21-30位参数对应 rolepublic 1-10位参数 userrole->xxstr
	// 1234567890123456789012345678901234567890
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// |||| ||_8 允许冲单
	// |||| |__7 允许撤单
	// ||||____4-6 允许查询天数（0表示不限止）
	// |||_____3 允许设置打印页眉页脚
	// ||______2 允许分享
	// |_______1 允许查看进价

	// ACCPUBLIC账套后台控制参数 原qxpublic 31-40位参数对应 accpublic 1-10位参数 accreg->qxcs
	// 1234567890123456789012345678901234567890
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// |||||_5 1=开通订货会功能
	// ||||__4 1=订货会截屏锁住
	// |||___3 1=允许更改单据日期
	// ||____2 1=表示分店铺算成本
	// |_____1 1=表示允许转枫杨果

	// eclipse复制粘贴很卡
	// Eclipse -- Windows->Preferences->General->Editors->Text Editors->Hyperlinking:
	// 去掉这个选项的勾：Enable on demand hyperlinks style navigation
}
