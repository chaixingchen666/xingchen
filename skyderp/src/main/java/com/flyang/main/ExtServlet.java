package com.flyang.main;

import java.io.IOException;
import java.sql.Types;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.io.RandomAccessFile;
//import java.io.File;
//import java.io.UnsupportedEncodingException;
//import java.sql.SQLException;
//import java.sql.Types;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.HashMap;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.*;
import com.oracle.bean.*;
import com.netdata.db.*;

//import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 标准外部接口
 */
@WebServlet("/ext")
public class ExtServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// private static final long serialVersionUID = -8250428508633548879L;
	// private static String aes_key = "sun81xjh34wmg91zhb16ccy35lk82xjw"; //
	// 32位
	// private static final long serialVersionUID = 1L;
	private static String filePath = "";

	// static {
	// filePath = getServletContext().getRealPath("/");
	// }
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public void destroy() {
		// TODO Auto-generated constructor stub
		super.destroy();
	}

	public void Init() throws ServletException {
		// super();
		// TODO Auto-generated constructor stub
		// filePath = getServletContext().getRealPath("/");
		// filePath = getServletContext().getRealPath("/");
		// System.out.println("Init ");
		// filePath = Func.getRootPath();
	}

	public ExtServlet() {
		// TODO Auto-generated constructor stub
		super();
		// filePath = System.getProperty("user.dir");
		// filePath = getServletContext().getRealPath("/");
		filePath = Func.initRootPath();// +"skyderp.config/";

		System.out.println("ExtServlet is ready ok! " + filePath);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// System.out.println("0000000");

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		// LogUtils.LogWrite("aaa","111", "1111");

		String url = request.getRequestURL() + "?" + request.getQueryString();
		// LogUtils.LogWrite("ERP调用", "", url + " post:" +
		// request.getParameterMap().toString());
		// System.out.println(url);

		String action = request.getParameter("action").toLowerCase();
		// 测试接口
		if (action.equals("path")) {
			response.getWriter().write(filePath + "  ---ext  ");
			return;
		} else

		if (action.equals("serverdate")) {
			ServerDate(response);
			return;
		}

		HttpInfo htp1 = new HttpInfo();
		htp1.setAction(action);// 设action
		htp1.setIpaddress(Func.getIpAddress(request));
		// htp1.setRootfilepath(filePath);
		String signjson = request.getParameter("sign"); // 取sign参数
		// LogUtils.LogDebugWrite(action+"-00000", signjson);

		if (signjson == null || signjson.length() <= 0) {
			WriteResult(response, 0, "sign参数无效！");
			return;
		}
		// base64解码 暂时不编码
		signjson = Func.Base64Decode(signjson);// sign base64解码
		// System.out.println(signjson);
		htp1.setUrl(url);//

		String datajson = request.getParameter("data");
		// LogUtils.LogDebugWrite(action+"-22222", datajson);
		if (datajson != null)
			htp1.setDatajson(datajson);// 传入data参数

		// =================
		String pictjson = request.getParameter("pict");

		if (pictjson != null)// pictjson="";
			htp1.setPictjson(pictjson);// 传入pict参数
		// ===================
		// LogUtils.LogDebugWrite(action+"-3333", "begin");
		int ret = htp1.setSignjsonExt(signjson);
		String nologaction = "^serverdate^serverdatetime^getdeviceno^getaccesskey^";
		// 不记录日志的接口名
		if (!nologaction.contains("^" + action + "^")) {
			// 接口调用记录
			LogUtils.LogWrite(htp1.getUsername() + " " + htp1.getIpaddress(), htp1.getUrl());
		}
		if (ret == 0)// 校验参数是否有效
		{
			Write(response, htp1.getErrmess());
			return;
		}
		// if (action != "serverdatetime" && action != "serverdatetime1" &&
		// action != "serverdate" && action != "decodexstr1" && action !=
		// "decodexstr"
		// && action != "devicevalid" && action != "getworkday" && action !=
		// "getdeviceno" && action != "getaccesskey" && action !=
		// "backprintlist"
		// && action != "overbackprint")

		String ss = Func.HttpIsDanger(htp1.getUrl());
		if (!ss.equals("")) {
			// 有危险操作的请求不允许通过
			LogUtils.LogWrite(htp1.getUsername() + "【 " + ss.trim() + "】" + htp1.getIpaddress(), htp1.getUrl(), "warn");

		}
		try {
			// System.out.println("action=" + action);
			switch (action) {
			case "getwarehousesum": // 查询指定商品库存
				GetWarehousesum(response, htp1);
				break;

			case "repcxxsck": // 查询销售出库
				repCxxsck(response, htp1);
				break;

			case "repcxcgrk": // 查询采购入库
				repCxcgrk(response, htp1);
				break;

			case "totalcxkczy": // 汇总当前库存
				TotalCxkczy(response, htp1);
				break;
			case "repcxkczy": // 分页显示库存资源
				repCxkczy(response, htp1);
				break;

			case "totalcustcheck": // 汇总客户销售对账数据
				TotalCustcheck(response, htp1);
				break;
			case "listcustcheckhz": // 查询客户销售对账汇总数据
				ListCustcheckhz(response, htp1);
				break;
			case "totalprovcheck": // 汇总供应商采购对账数据
				TotalProvcheck(response, htp1);
				break;
			case "listprovcheckhz": // 查询供应商采购对账汇总数据
				ListProvcheckhz(response, htp1);
				break;
			case "totaljxchz": // 汇总进销存汇总数据
				TotalJxchz(response, htp1);
				break;
			case "listjxchz": // 查询进销存汇总数据
				ListJxchz(response, htp1);
				break;

			case "getwarecodebybarcode": // 根据商品条码获取指定商品信息(原创)
				GetWarecodeByBarcode(response, htp1);
				break;
			case "warecodehouselist": // 分页获取商品库存列表（原创）
				GetWareCodeHouseList(response, htp1);
				break;

			case "serverdate":
				ServerDate(response);
				break;
			case "serverdatetime":
				ServerDateTime(response);
				break;
			default:
				WriteResult(response, 0, "Ext接口未定义:" + action + "!");
				break;
			}
		} catch (IOException e) {
			//			e.printStackTrace();
			System.out.println(e.getMessage());
			WriteResult(response, 0, "网络异常，稍候请重试!");
		}
	}

	// 查询采购入库
	protected void repCxcgrk(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long accid = jsonObject.has("accid") ? Integer.parseInt(jsonObject.getString("accid")) : 0;
		String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : htp.getNowdate();
		String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : htp.getNowdate();
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 50;
		vCxcgrk dal = new vCxcgrk();
		dal.setMindate(mindate);
		dal.setMaxdate(maxdate);
		// DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setFieldlist("a.noteno,a.notedate,i.provname,c.wareno,c.warename,c.units,d.colorname,"
				+ "e.sizename,e.sizeno,case a.ntid when 0 then b.amount else -b.amount end as amount,b.price,case a.ntid when 0 then b.curr else -b.curr end as curr");
		dal.setSumlist("nvl(sum(amount),0) as totalamt,nvl(sum(curr),0) as totalcurr");
		dal.setSortlist("notedate");
		dal.setAccid(accid);
		dal.setUserid(htp.getUserid());
		// dal.setQxbj(qxbj);
		dal.setNowdate(htp.getNowdate());
		dal.setAccbegindate(htp.getAccdate());
		QueryParam qp = new QueryParam();
		Table tb = new Table();

		qp.setPageIndex(page);
		qp.setPageSize(pagesize);

		// tb = dal.GetTable(qp);
		if (page < 0) {// 导出excel
			jsonObject.put("reptitle", "商品采购明细表");
			// System.out.println("00:" + jsonObject.toString());
			jsonObject.put("columns",
					"[{\"fieldno\":\"NOTENO\",\"fieldname\":\"单据号\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"NOTEDATE\",\"fieldname\":\"单据日期\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"PROVNAME\",\"fieldname\":\"供应商\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"WARENO\",\"fieldname\":\"货号\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"WARENAME\",\"fieldname\":\"商品名称\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"UNITS\",\"fieldname\":\"单位\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"COLORNAME\",\"fieldname\":\"颜色\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"SIZENAME\",\"fieldname\":\"尺码\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"AMOUNT\",\"fieldname\":\"数量\",\"fieldtype\":\"G\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"PRICE\",\"fieldname\":\"单价\",\"fieldtype\":\"N\",\"level\":0}"//
							+ ",{\"fieldno\":\"CURR\",\"fieldname\":\"金额\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}]");
			String retcs = dal.GetTable2Excel(qp, jsonObject);
			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;
		}
		tb = dal.GetTable(qp);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 查询指定商品库存
	protected void GetWarehousesum(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String wareno = jsonObject.has("wareno") ? jsonObject.getString("wareno") : "";
		String colorname = jsonObject.has("colorname") ? jsonObject.getString("colorname") : "";
		String sizename = jsonObject.has("sizename") ? jsonObject.getString("sizename") : "";
		vTotalCxkczy dal = new vTotalCxkczy();
		dal.setUserid(htp.getUserid());
		dal.setAccid(htp.getMaccid());
		dal.setNohouse(1);// 不分店铺
		dal.setWareno1(wareno);
		dal.setColorname(colorname);
		dal.setSizename(sizename);
		dal.setCalcdate(htp.getCalcdate());
		dal.setCxdatetime(htp.getNowdate());
		if (dal.doGetwarehousesum() == 0)
			WriteResult(response, 0, dal.getErrmess());
		else
			WriteResultJson(response, 1, dal.getErrmess());
	}

	// 查询销售库
	protected void repCxxsck(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long accid = jsonObject.has("accid") ? Integer.parseInt(jsonObject.getString("accid")) : 0;
		String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : htp.getNowdate();
		String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : htp.getNowdate();
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 50;

		// int qxbj = htp.getQxbj();
		// int maxday = htp.getMaxday();

		vCxxsck dal = new vCxxsck();
		dal.setMindate(mindate);
		dal.setMaxdate(maxdate);
		// DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		// dal.setFieldlist("a.noteno,a.notedate,i.custname,c.wareno,c.warename,c.units,d.colorname,"
		// + "e.sizename,e.sizeno,case a.ntid when 0 then b.amount else
		// -b.amount end as amount,b.price,case a.ntid when 0 then b.curr else
		// -b.curr end as curr");
		dal.setAccid(accid);
		dal.setUserid(htp.getUserid());
		// dal.setQxbj(qxbj);
		dal.setNowdate(htp.getNowdate());
		dal.setAccbegindate(htp.getAccdate());
		QueryParam qp = new QueryParam();
		Table tb = new Table();

		qp.setPageIndex(page);
		qp.setPageSize(pagesize);

		// tb = dal.GetTable(qp);
		if (page < 0) {// 导出excel
			jsonObject.put("reptitle", "商品销售明细表");

			// System.out.println("00:" + jsonObject.toString());
			jsonObject.put("columns",
					"[{\"fieldno\":\"NOTENO\",\"fieldname\":\"单据号\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"NOTEDATE\",\"fieldname\":\"单据日期\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"PROVNAME\",\"fieldname\":\"供应商\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"WARENO\",\"fieldname\":\"货号\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"WARENAME\",\"fieldname\":\"商品名称\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"UNITS\",\"fieldname\":\"单位\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"COLORNAME\",\"fieldname\":\"颜色\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"SIZENAME\",\"fieldname\":\"尺码\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"AMOUNT\",\"fieldname\":\"数量\",\"fieldtype\":\"G\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"PRICE\",\"fieldname\":\"单价\",\"fieldtype\":\"N\",\"level\":0}"//
							+ ",{\"fieldno\":\"CURR\",\"fieldname\":\"金额\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}]");
			String retcs = "";// dal.GetTable2Excel(qp, jsonObject);
			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;
		}
		tb = dal.GetTable2(qp);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 汇总库存资源
	protected void TotalCxkczy(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long accid = jsonObject.has("accid") ? Integer.parseInt(jsonObject.getString("accid")) : 0;
		String nowdate = jsonObject.has("nowdate") ? jsonObject.getString("nowdate") : "";
		// System.out.println(nowdate);
		// String houseidlist = jsonObject.has("houseidlist") ?
		// jsonObject.getString("houseidlist") : "";
		if (nowdate.equals(""))
			nowdate = htp.getNowdatetime();
		vTotalCxkczy dal = new vTotalCxkczy();
		// 读取json数据到表类
		// DbHelperSQL.JsonConvertObject(dal, jsonObject);
		// System.out.println("epid="+htp.getUserid());
		dal.setAccid(accid);
		dal.setUserid(htp.getUserid());
		dal.setCxdatetime(nowdate);
		dal.setNohouse(1);
		// dal.setQxbj(0);
		dal.setServerdatetime(htp.getNowdatetime());
		dal.setAccbegindate(htp.getAccdate());
		dal.setCalcdate(htp.getCalcdate());

		WriteResult(response, dal.doCxkczy(), dal.getErrmess());

	}

	// 查询库存资源
	protected void repCxkczy(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long accid = jsonObject.has("accid") ? Integer.parseInt(jsonObject.getString("accid")) : 0;
		// int xls = jsonObject.has("xls") ?
		// Integer.parseInt(jsonObject.getString("xls")) : 0;
		// int sizenum = jsonObject.has("sizenum") ?
		// Integer.parseInt(jsonObject.getString("sizenum")) : 0;
		// 如果sizenum>0,表示按尺码横向展开显示
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 50;
		// int housecostbj = htp.getHousecostbj(); // 分店铺计算成本
		vTotalCxkczy dal = new vTotalCxkczy();
		// DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(accid);
		dal.setUserid(htp.getUserid());

		QueryParam qp = new QueryParam();
		qp.setPageIndex(page);
		qp.setPageSize(pagesize);

		dal.setFieldlist("sum(a.amount) as amount,sum(a.amount*e.costprice) as curr,sum(a.amount*b.retailsale) as retailcurr");
		dal.setSumlist("nvl(sum(amount),0) as totalamt,nvl(sum(curr),0) as totalcurr,nvl(sum(retailcurr),0) as totalretailcurr");
		dal.setSortlist("WARENO asc");
		// dal.setGrouplist("a.wareid,b.wareno,b.warename,b.units,a.colorid,c.colorname,a.sizeid,d.sizename,d.sizeno");
		dal.setGrouplist("b.wareno,b.warename,b.units,c.colorname,d.sizename,d.sizeno");

		Table tb = new Table();

		// tb = dal.GetTable(qp, 0);

		if (page < 0) {// 导出excel
			jsonObject.put("reptitle", "库存商品汇总表");
			jsonObject.put("columns",
					"[{\"fieldno\":\"WARENO\",\"fieldname\":\"货号\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"WARENAME\",\"fieldname\":\"商品名称\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"UNITS\",\"fieldname\":\"单位\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"COLORNAME\",\"fieldname\":\"颜色\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"SIZENAME\",\"fieldname\":\"尺码\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"AMOUNT\",\"fieldname\":\"数量\",\"fieldtype\":\"G\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"PRICE\",\"fieldname\":\"成本价\",\"fieldtype\":\"N\",\"level\":0}"//
							+ ",{\"fieldno\":\"CURR\",\"fieldname\":\"成本额\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"RETAILSALE\",\"fieldname\":\"零售价\",\"fieldtype\":\"N\",\"level\":0}"//
							+ ",{\"fieldno\":\"RETAILCURR\",\"fieldname\":\"零售额\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}]");

			// "columns":[{"fieldno":"WARENO","fieldname":"货号","fieldtype":"C","level":0},{"fieldno":"WARENAME","fieldname":"商品名称","fieldtype":"C","level":0},{"fieldno":"UNITS","fieldname":"单位","fieldtype":"C","level":0},{"fieldno":"COLORNAME","fieldname":"颜色","fieldtype":"C","level":0},{"fieldno":"SIZENAME","fieldname":"尺码","fieldtype":"C","level":0},{"fieldno":"AMOUNT","fieldname":"数量","fieldtype":"G","level":0,"fieldfooter":"1047"},{"fieldno":"PRICE","fieldname":"成本价","fieldtype":"N","level":0},{"fieldno":"CURR","fieldname":"成本额","fieldtype":"N","level":0,"fieldfooter":"147609.7"},{"fieldno":"RETAILSALE","fieldname":"零售价","fieldtype":"N","level":0},{"fieldno":"RETAILCURR","fieldname":"零售额","fieldtype":"N","level":0,"fieldfooter":"391436"}]

			// String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
			String retcs = dal.GetTable2Excel(qp, 0, jsonObject);

			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;
		}
		tb = dal.GetTable(qp, 0);

		// {"sortlist":"WARENO
		// asc","grouplist":"b.imagename0,a.wareid,b.wareno,b.warename,b.units,a.colorid,c.colorname,a.sizeid,d.sizename,d.sizeno"
		// ,"maxamt":"","page":"1","fieldlist":"sum(a.amount) as
		// amount,sum(a.amount*e.costprice) as curr,sum(a.amount*b.retailsale)
		// as retailcurr"
		// ,"rows":"20","calcfield":"case amount when 0 then 0 else
		// round(curr/amount,2) end as price,case amount when 0 then 0 else
		// round(retailcurr/amount,2) end as retailsale"
		// ,"minamt":"","xsid":"3","sumlist":"nvl(sum(amount),0) as
		// totalamt,nvl(sum(curr),0) as totalcurr,nvl(sum(retailcurr),0) as
		// totalretailcurr","flyang":"20150107"}

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 汇总客户销售对账数据
	protected void TotalCustcheck(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long accid = jsonObject.has("accid") ? Integer.parseInt(jsonObject.getString("accid")) : 0;
		String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : htp.getNowdate();
		String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : htp.getNowdate();
		vTotalcustcheck dal = new vTotalcustcheck();
		// DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setMindate(mindate);
		dal.setMaxdate(maxdate);
		dal.setAccid(accid);
		dal.setUserid(htp.getUserid());
		dal.setMaxday(htp.getMaxday());
		dal.setNowdate(htp.getNowdate());
		dal.setAccbegindate(htp.getAccdate());
		dal.setCalcdate(htp.getCalcdate());
		// dateid:0=本日，1=昨日，2=本周，3=本月 ,4=指定日期
		if (dal.doTotal(4) == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());
		}
	}

	// 汇总供应商采购对账数据
	protected void TotalProvcheck(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long accid = jsonObject.has("accid") ? Integer.parseInt(jsonObject.getString("accid")) : 0;
		String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : htp.getNowdate();
		String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : htp.getNowdate();
		vTotalprovcheck dal = new vTotalprovcheck();
		// DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(accid);
		dal.setUserid(htp.getUserid());
		dal.setMindate(mindate);
		dal.setMaxdate(maxdate);
		// dal.setMaxday(htp.getMaxday());
		dal.setNowdate(htp.getNowdate());
		dal.setAccbegindate(htp.getAccdate());
		dal.setCalcdate(htp.getCalcdate());
		// dateid:0=本日，1=昨日，2=本周，3=本月 ,4=指定日期
		if (dal.doTotal(4) == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());
		}
	}

	// 分页查询应收款对账表
	protected void ListCustcheckhz(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long accid = jsonObject.has("accid") ?
		// Integer.parseInt(jsonObject.getString("accid")) : 0;
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : "custid";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : "";
		int qkid = 2;// jsonObject.has("qkid") ?
						// Integer.parseInt(jsonObject.getString("qkid")) : 2;
						// qkid:0=有欠款 1=有余额 2=所有
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 50;
		vTotalcustcheck dal = new vTotalcustcheck();
		sort += " " + order + ",keyid";
		dal.setUserid(htp.getUserid());
		QueryParam qp = new QueryParam(page, pagesize, sort);
		Table tb = new Table();
		tb = dal.GetTable(qp, qkid);
		if (page < 0) {// 导出excel
			jsonObject.put("reptitle", "应收款汇总表");
			jsonObject.put("columns",
					"[{\"fieldno\":\"CUSTNAME\",\"fieldname\":\"客户\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"AREANAME\",\"fieldname\":\"区域\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"CURR0\",\"fieldname\":\"期初欠款\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"XSAMT\",\"fieldname\":\"销售数量\",\"fieldtype\":\"G\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"XSCURR\",\"fieldname\":\"销售金额\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"XTAMT\",\"fieldname\":\"退货数量\",\"fieldtype\":\"G\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"XTCURR\",\"fieldname\":\"退货金额\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"FYCURR\",\"fieldname\":\"应收费用\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"ZKCURR\",\"fieldname\":\"销售折让\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CURR1\",\"fieldname\":\"应收金额\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CURR2\",\"fieldname\":\"已收金额\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CURR3\",\"fieldname\":\"期末欠款\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}]");

			String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;
		}
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 分页查询应付款对账表
	protected void ListProvcheckhz(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " provid ";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : " ";
		int qkid = 2;// jsonObject.has("qkid") ?
						// Integer.parseInt(jsonObject.getString("qkid")) : 2;
						// qkid:0=有欠款 1=有余额 2=所有
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 50;
		vTotalprovcheck dal = new vTotalprovcheck();
		sort += " " + order + ",keyid";
		dal.setUserid(htp.getUserid());
		QueryParam qp = new QueryParam(page, pagesize, sort);
		Table tb = new Table();
		tb = dal.GetTable(qp, qkid);
		if (page < 0) {// 导出excel
			jsonObject.put("reptitle", "应付款汇总表");
			jsonObject.put("columns",
					"[{\"fieldno\":\"PROVNAME\",\"fieldname\":\"供应商\",\"fieldtype\":\"C\",\"level\":0}"//
							+ ",{\"fieldno\":\"CURR0\",\"fieldname\":\"期初欠款\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CGAMT\",\"fieldname\":\"采购数量\",\"fieldtype\":\"G\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CGCURR\",\"fieldname\":\"采购金额\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CTAMT\",\"fieldname\":\"退货数量\",\"fieldtype\":\"G\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CTCURR\",\"fieldname\":\"退货金额\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"FYCURR\",\"fieldname\":\"应付费用\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"ZKCURR\",\"fieldname\":\"采购折让\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CURR1\",\"fieldname\":\"应付金额\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CURR2\",\"fieldname\":\"已付金额\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CURR3\",\"fieldname\":\"期末欠款\",\"fieldtype\":\"N\",\"level\":0,\"fieldfooter\":\"0\"}]");
			String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;
		}
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 汇总进销存汇总
	protected void TotalJxchz(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : htp.getNowdate();
		String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : htp.getNowdate();
		long accid = jsonObject.has("accid") ? Integer.parseInt(jsonObject.getString("accid")) : 0;
		// int cxfs = jsonObject.has("cxfs") ?
		// Integer.parseInt(jsonObject.getString("cxfs")) : 0;
		// cxfs 0=单据，1=商品，2=商品+颜色，3=商品+颜色+尺码
		vTotalJxchz dal = new vTotalJxchz();
		dal.setMindate(mindate);
		dal.setMaxdate(maxdate);

		// int housecostbj = htp.getHousecostbj();
		// int qxbj = htp.getQxbj();
		// int priceprec = htp.getPriceprec();
		// // String accbegindate = htp.getAccdate();// 账套开始使用日期
		// int maxday = htp.getMaxday();
		// ==========================
		// DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(accid);
		dal.setUserid(htp.getUserid());
		dal.setAccbegindate(htp.getAccdate());
		dal.setServerdatetime(htp.getNowdatetime());
		dal.setPriceprec(2);
		dal.setCalcdate(htp.getCalcdate());
		if (dal.Total(0, 0, 0) < 0)
			WriteResult(response, 0, dal.getErrmess());
		else
			WriteResultJson(response, 1, dal.getErrmess());
	}

	// 分页查询进销存汇总数据
	protected void ListJxchz(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long accid = jsonObject.has("accid") ? Integer.parseInt(jsonObject.getString("accid")) : 0;
		// int cxfs = jsonObject.has("cxfs") ?
		// Integer.parseInt(jsonObject.getString("cxfs")) : 0;
		// hzfs:0=商品，1=商品+颜色 , 2=商品+颜色+尺码,3=区位
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 50;
		vTotalJxchz dal = new vTotalJxchz();
		// DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setSortlist("WARENO asc");
		dal.setGrouplist("a.wareid,b.wareno,b.warename,b.units");
		dal.setFieldlist("sum(a.amountqc) as amountqc,sum(a.currqc) as currqc,sum(a.amountcg) as amountcg,sum(a.currcg) as currcg"//
				+ ",sum(a.amountct) as amountct,sum(a.currct) as currct,sum(a.amountcs) as amountcs,sum(a.currcs) as currcs"//
				+ ",sum(a.amountdr) as amountdr,sum(a.currdr) as currdr,sum(a.amountpy) as amountpy,sum(a.currpy) as currpy"//
				+ ",sum(a.amountls) as amountls,sum(a.currls) as currls,sum(a.amountxs) as amountxs,sum(a.currxs) as currxs"//
				+ ",sum(a.amountxt) as amountxt,sum(a.currxt) as currxt,sum(a.amountdc) as amountdc,sum(a.currdc) as currdc"//
				+ ",sum(a.amountpk) as amountpk,sum(a.currpk) as currpk,sum(a.amountqm) as amountqm,sum(a.currqm) as currqm");
		dal.setSumlist("nvl(sum(amountqc),0) as totalamtqc,nvl(sum(currqc),0) as totalcurrqc,nvl(sum(amountcg),0) as totalamtcg"//
				+ ",nvl(sum(currcg),0) as totalcurrcg,nvl(sum(amountct),0) as totalamtct,nvl(sum(currct),0) as totalcurrct"//
				+ ",nvl(sum(amountcs),0) as totalamtcs,nvl(sum(currcs),0) as totalcurrcs,nvl(sum(amountdr),0) as totalamtdr"//
				+ ",nvl(sum(currdr),0) as totalcurrdr,nvl(sum(amountpy),0) as totalamtpy,nvl(sum(currpy),0) as totalcurrpy"//
				+ ",nvl(sum(amountls),0) as totalamtls,nvl(sum(currls),0) as totalcurrls,nvl(sum(amountxs),0) as totalamtxs"//
				+ ",nvl(sum(currxs),0) as totalcurrxs,nvl(sum(amountxt),0) as totalamtxt,nvl(sum(currxt),0) as totalcurrxt"//
				+ ",nvl(sum(amountdc),0) as totalamtdc,nvl(sum(currdc),0) as totalcurrdc,nvl(sum(amountpk),0) as totalamtpk"//
				+ ",nvl(sum(currpk),0) as totalcurrpk,nvl(sum(amountqm),0) as totalamtqm,nvl(sum(currqm),0) as totalcurrqm");
		dal.setAccid(accid);
		dal.setUserid(htp.getUserid());
		QueryParam qp = new QueryParam();
		qp.setPageIndex(page);
		qp.setPageSize(pagesize);
		Table tb = new Table();

		tb = dal.GetTable(qp);

		if (page < 0) {// 导出excel
			jsonObject.put("reptitle", "进销存汇总表");
			jsonObject.put("headrows", "2");
			jsonObject.put("columns",
					"[{\"fieldno\":\"WARENO\",\"fieldname\":\"货号\",\"fieldtype\":\"C\",\"m_title0\":\"货号\",\"m_col0\":0,\"m_row0\":1}"//
							+ ",{\"fieldno\":\"WARENAME\",\"fieldname\":\"商品名称\",\"fieldtype\":\"C\",\"m_title0\":\"商品名称\",\"m_col0\":0,\"m_row0\":1}"//
							+ ",{\"fieldno\":\"UNITS\",\"fieldname\":\"单位\",\"fieldtype\":\"C\",\"m_title0\":\"单位\",\"m_col0\":0,\"m_row0\":1}"//
							+ ",{\"fieldno\":\"AMOUNTQC\",\"fieldname\":\"数量\",\"fieldtype\":\"G\",\"level\":1,\"m_title0\":\"期初\",\"m_col0\":1,\"m_row0\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CURRQC\",\"fieldname\":\"成本额\",\"fieldtype\":\"N\",\"level\":1,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"AMOUNTCG\",\"fieldname\":\"数量\",\"fieldtype\":\"G\",\"level\":1,\"m_title0\":\"采购入库\",\"m_col0\":1,\"m_row0\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CURRCG\",\"fieldname\":\"成本额\",\"fieldtype\":\"N\",\"level\":1,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"AMOUNTCT\",\"fieldname\":\"数量\",\"fieldtype\":\"G\",\"level\":1,\"m_title0\":\"采购退货\",\"m_col0\":1,\"m_row0\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CURRCT\",\"fieldname\":\"金额\",\"fieldtype\":\"N\",\"level\":1,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"AMOUNTCS\",\"fieldname\":\"数量\",\"fieldtype\":\"G\",\"level\":1,\"m_title0\":\"初始\",\"m_col0\":1,\"m_row0\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CURRCS\",\"fieldname\":\"成本额\",\"fieldtype\":\"N\",\"level\":1,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"AMOUNTDR\",\"fieldname\":\"数量\",\"fieldtype\":\"G\",\"level\":1,\"m_title0\":\"调入\",\"m_col0\":1,\"m_row0\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CURRDR\",\"fieldname\":\"金额\",\"fieldtype\":\"N\",\"level\":1,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"AMOUNTPY\",\"fieldname\":\"数量\",\"fieldtype\":\"G\",\"level\":1,\"m_title0\":\"盘盈\",\"m_col0\":1,\"m_row0\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CURRPY\",\"fieldname\":\"金额\",\"fieldtype\":\"N\",\"level\":1,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"AMOUNTLS\",\"fieldname\":\"数量\",\"fieldtype\":\"G\",\"level\":1,\"m_title0\":\"零售\",\"m_col0\":1,\"m_row0\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CURRLS\",\"fieldname\":\"金额\",\"fieldtype\":\"N\",\"level\":1,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"AMOUNTXS\",\"fieldname\":\"数量\",\"fieldtype\":\"G\",\"level\":1,\"m_title0\":\"批发销售\",\"m_col0\":1,\"m_row0\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CURRXS\",\"fieldname\":\"金额\",\"fieldtype\":\"N\",\"level\":1,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"AMOUNTXT\",\"fieldname\":\"数量\",\"fieldtype\":\"G\",\"level\":1,\"m_title0\":\"批发退库\",\"m_col0\":1,\"m_row0\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CURRXT\",\"fieldname\":\"金额\",\"fieldtype\":\"N\",\"level\":1,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"AMOUNTDC\",\"fieldname\":\"数量\",\"fieldtype\":\"G\",\"level\":1,\"m_title0\":\"调出\",\"m_col0\":1,\"m_row0\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CURRDC\",\"fieldname\":\"金额\",\"fieldtype\":\"N\",\"level\":1,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"AMOUNTPK\",\"fieldname\":\"数量\",\"fieldtype\":\"G\",\"level\":1,\"m_title0\":\"盘亏\",\"m_col0\":1,\"m_row0\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CURRPK\",\"fieldname\":\"金额\",\"fieldtype\":\"N\",\"level\":1,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"AMOUNTQM\",\"fieldname\":\"数量\",\"fieldtype\":\"G\",\"level\":1,\"m_title0\":\"期末\",\"m_col0\":1,\"m_row0\":0,\"fieldfooter\":\"0\"}"//
							+ ",{\"fieldno\":\"CURRQM\",\"fieldname\":\"成本额\",\"fieldtype\":\"N\",\"level\":1,\"fieldfooter\":\"0\"}]");
			// System.out.println(jsonObject);
			String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;
		}

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 根据商品条码获取商品信息（原创）
	protected void GetWarecodeByBarcode(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		//		Long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		String barcode = jsonObject.has("barcode") ? jsonObject.getString("barcode") : "";
		String strwhere = " a.barcode='" + barcode + "' and a.STATETAG=1 and a.ACCID=" + htp.getMaccid();
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "A.BARCODE,A.WAREID,B.WARENO,B.WARENAME,B.UNITS,A.COLORID,C.COLORNAME,A.SIZEID,D.SIZENAME,d.sizeno,B.BRANDID,E.BRANDNAME,B.RETAILSALE";
		// fieldlist += ",f_wareexistsofhouse(a.wareid) as bj";
		fieldlist += ",f_getwaresumkc(a.accid,'','" + htp.getCalcdate() + "',a.wareid,0,0,0) as amount";
		Warebarcode dal = new Warebarcode();
		Table tb = new Table();

		tb = dal.GetList(strwhere, fieldlist).getTable(1);

		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 分页获取商品库存记录表
	protected void GetWareCodeHouseList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		//		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : "wareno";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 50;
		//		int qxbj = htp.getQxbj();

		sort += " " + order + ",wareid";
		// System.out.println("000");
		Warecode dal = new Warecode();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		// dal.setHouseid(houseid);
		Long houseid = dal.getHouseid();
		Long areaid = dal.getAreaid();
		Long brandid = dal.getBrandid();
		Long typeid = dal.getTypeid();
		Long provid = dal.getProvid();
		Integer noused = dal.getNoused();
		String warename = dal.getWarename();
		String wareno = dal.getWareno();
		String seasonname = dal.getSeasonname();
		String locale = dal.getLocale();
		String prodno = dal.getProdno();
		String prodyear = dal.getProdyear();
		//		String lastop = dal.getLastop();
		//		String lastdate = dal.getLastdate();
		//		String barcode = dal.getBarcode();
		// if (noused == null)
		// noused = 0;
		// System.out.println("111");
		String strwhere = " a.ACCID=" + htp.getMaccid();
		//		if (downbj == 0)
		strwhere += " and a.STATETAG=1";

		//		if (!Func.isNull(mindate))
		//			strwhere += " and a.lastdate >= to_date('" + mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		//
		//		if (!Func.isNull(maxdate))
		//			strwhere += " and a.lastdate <= to_date('" + maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		//
		//		if (!Func.isNull(lastdate))
		//			strwhere += " and a.lastdate>to_date('" + lastdate + "','yyyy-mm-dd hh24:mi:ss')";

		if (noused != null && noused < 2)
			strwhere += " and a.noused=" + noused;
		//		if (tjtag < 2)
		//			strwhere += " and a.tjtag=" + tjtag;

		//		if (!Func.isNull(findbox))
		//			strwhere += " and (a.warename like '%" + findbox + "%' or a.shortname like '%" + findbox.toUpperCase() + "%' or a.wareno like '%" + findbox.toUpperCase() + "%')";
		// System.out.println("222");
		if (!Func.isNull(warename))
			strwhere += " and (a.warename like '%" + warename + "%' or a.shortname like '%" + warename.toUpperCase() + "%')";
		if (!Func.isNull(wareno))
			strwhere += " and a.wareno like '%" + wareno.toUpperCase() + "%'";
		if (!Func.isNull(seasonname))
			strwhere += " and a.seasonname = '" + seasonname + "'";
		if (!Func.isNull(prodyear))
			strwhere += " and a.prodyear = '" + prodyear + "'";
		if (locale != null && !locale.equals(""))
			strwhere += "  and a.locale like '%" + locale + "%'";
		if (prodno != null && !prodno.equals(""))
			strwhere += " and a.prodno like '%" + prodno + "%'";
		if (areaid != null && areaid >= 0)
			strwhere += " and a.areaid = " + areaid;
		if (brandid != null && brandid >= 0)
			strwhere += " and a.brandid = " + brandid;
		if (provid != null && provid >= 0)
			strwhere += " and a.provid = " + provid;
		if (typeid != null && typeid >= 0)
			if (typeid > 0 && typeid < 1000) {
				strwhere += "    and exists (select 1 from waretype t where t.typeid=a.typeid and t.p_typeid= " + typeid + ")";
			} else {
				strwhere += "    and a.typeid=" + typeid;
			}
		//		if (!Func.isNull(lastop))
		//			strwhere += " and a.lastop like '%" + lastop + "%'";
		// if (typeid>0) qry += " and a.typeid = " + typeid;
		//		if (havepicture == 0) // 只返回无图片的商品
		//		{
		//			strwhere += " and (length(a.imagename0)=0 or a.imagename0 is null ) ";
		//		} else if (havepicture == 1) // 只返回有图片的商品
		//		{
		//			// qry += " and (length(a.imagename)>0 or length(a.imagename0)>0)
		//			// and not a.imagename is null and not a.imagename0 is null ";
		//			strwhere += " and length(a.imagename0)>0 and not a.imagename0 is null ";
		//		}
		// System.out.println("333");
		//		if (qxbj == 1 && downbj == 0) // 1 启用权限控制
		//		{
		//			strwhere += " and (a.brandid=0 or exists (select 1 from employebrand x where a.brandid=x.brandid and x.epid=" + htp.getUserid() + "))";
		//		}
		//		if (barcode.length() > 0) {
		//			strwhere += " and exists (select 1 from warebarcode y where a.accid=y.accid and a.wareid=y.wareid and y.statetag=1 and y.barcode like '%" + barcode + "%')";
		//		}
		if (fieldlist.equals("*") || fieldlist.length() <= 0)
			fieldlist = " A.WAREID,A.WARENO,A.WARENAME,A.UNITS,A.SHORTNAME,A.BRANDID,A.SEASONNAME,A.ACCID,A.TYPEID"//
					+ ",A.PRODYEAR,A.PRODNO,A.ENTERSALE,A.RETAILSALE,A.REMARK,A.SIZEGROUPNO" //
					+ ",A.NOUSED,A.STATETAG,C.BRANDNAME,B.TYPENAME,B.FULLNAME,F.PROVNAME";
		//		if (houseid != null && houseid > 0)
		//			fieldlist += ",case when h.retailsale is null then 0 else h.retailsale end as retailsale0";
		//		else
		//			fieldlist += ",a.retailsale as retailsale0";
		// System.out.println("444");

		QueryParam qp = new QueryParam(page, pagesize, sort);
		qp.setCalcfield("f_getwaresumkc(accid,'','" + htp.getCalcdate() + "',wareid,0,0," + houseid + ") as amount");

		//		if (page < 0) {// 导出excel
		//			// String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
		//			String retcs = dal.GetTable2Excel(qp, strwhere, fieldlist, jsonObject);
		//			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
		//			return;
		//		}
		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere, fieldlist);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// ********************************************************
	protected void ServerDate(HttpServletResponse response) throws ServletException, IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String jsonstr = "{\"result\":1,\"msg\":\"操作成功！\",\"SERVERDATE\":\"" + df.format(pFunc.getServerdatetime()) + "\"}";
		response.getWriter().write(jsonstr);
	}

	protected void ServerDateTime(HttpServletResponse response) throws ServletException, IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String jsonstr = "{\"result\":1,\"msg\":\"操作成功！\",\"SERVERDATETIME\":\"" + df.format(pFunc.getServerdatetime()) + "\"}";

		response.getWriter().write(jsonstr);
	}

	protected void WriteResult(HttpServletResponse response, int result, String mess) throws ServletException, IOException {
		String jsonstr = "{\"result\":" + result + ",\"msg\":\"" + mess + "\"}";
		response.getWriter().write(jsonstr);
	}

	protected void WriteResultJson(HttpServletResponse response, int result, String mess) throws ServletException, IOException {
		String jsonstr = "{\"result\":" + result + "," + mess + "}";
		response.getWriter().write(jsonstr);
	}

	protected void Write(HttpServletResponse response, String outstr) throws ServletException, IOException {
		response.getWriter().write(outstr);
		// return;
	}

}
