package com.flyang.main;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.comdot.data.Table;
import com.common.tool.*;

import com.oracle.bean.*;
import com.netdata.db.*;

import net.sf.json.JSONObject;

/**
 * 订货会后台接口
 */
@WebServlet("/ords")
public class OrdbackServlet extends HttpServlet {
	// private static final long serialVersionUID = 1L;
	// private static String filePath = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrdbackServlet() {
		super();
		// TODO Auto-generated constructor stub
		// filePath = Func.getRootPath();// +"skyderp.config/";
		System.out.println("OrdbackServlet is ready ok! ");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		// LogUtils.LogWrite("aaa","111", "1111");

		String url = request.getRequestURL() + "?" + request.getQueryString();
		// LogUtils.LogWrite("ERP调用", "", url + " post:" + request.getParameterMap().toString());

		String action = request.getParameter("action").toLowerCase();
		// filePath=Func.getRootPath();
		// if (action.equals("path")) {
		// response.getWriter().write(filePath + " ---city ");
		// return;
		// } else

		if (action.equals("serverdate")) {
			ServerDateTime(response);
			return;
		}

		HttpInfo htp1 = new HttpInfo();
		htp1.setAction(action);// 设action
		htp1.setIpaddress(Func.getIpAddress(request));

		String signjson = request.getParameter("sign"); // 取sign参数

		if (signjson == null || signjson.equals("")) {
			WriteResult(response, 0, "sign参数无效！");
			return;
		}
		// base64解码
		signjson = Func.Base64Decode(signjson);// sign base64解码
		htp1.setUrl(url);//

		String datajson = request.getParameter("data");
		if (datajson != null)
			htp1.setDatajson(datajson);// 传入data参数

		// =================
		String pictjson = request.getParameter("pict");

		if (pictjson != null)// pictjson="";
			htp1.setPictjson(pictjson);// 传入pict参数
		// ===================
		int ret=htp1.setSignjson(signjson,0);
		// String onlogaction = "^serverdate^serverdatetime^path^test^"; // 不记录日志的接口名
		// if (!onlogaction.contains("^" + action + "^")) {
		// 接口调用记录
		LogUtils.LogWrite(htp1.getUsername()+" "+htp1.getIpaddress(), htp1.getUrl());
		if (ret == 0)// 校验参数是否有效
		{
			Write(response, htp1.getErrmess());
			return;

		}

		String ss = Func.HttpIsDanger(htp1.getUrl());
		if (!ss.equals("")) {
			// 有危险操作的请求不允许通过

			LogUtils.LogWrite(htp1.getUsername() + "【 " + ss.trim() + "】 "+ htp1.getIpaddress(), htp1.getUrl(), "warn");
			WriteResult(response, 0, "警告：请求中含有危险操作命令！");
			return;
		}
		// }
		//System.out.println("action=" + action);
		switch (action) {
		// 订货会后台begin

		case "addomuser": // 增加订货会用户
			AddOmuser(response, htp1);
			break;

		case "appendomuserxls": // 从excel表导入订货会用户
			AppendOmuserxls(response, htp1);
			break;
		case "listomcustwaretotalsize": // 显示订货的商品汇总列表(按尺码横向展开) 订货商品实时监控 手机 未用
			ListOmcustwaretotalsize(response, htp1);
			break;

		case "listomtotal": // 显示订货的商品汇总列表 订货商品实时监控 手机 new
			ListOmtotal(response, htp1);
			break;
		case "listomtotalcolorandsize": // 返回订货表商品颜色尺码二维表格式 手机 new
			ListOmtotalcolorandsize(response, htp1);
			break;

		case "listomtotalpole": // 显示订货的商品陈列汇总列表
			ListOmtotalpole(response, htp1);
			break;
		case "repcxomorder": // 显示订货的商品汇总列表(按尺码横向展开) 订货商品实时汇总监控pc
			repCxomorder(response, htp1);
			break;

		case "updateomuser": // 修改订货会用户
			UpdateOmuser(response, htp1);
			break;
		case "delomuser": // 删除订货会用户
			DelOmuser(response, htp1);
			break;
		case "listomuser": // 显示订货会用户列表
			ListOmuser(response, htp1);
			break;
		case "listallomuser": // 显示订货会所有用户列表，按客户排序，在订货会监控里调用
			ListAllOmuser(response, htp1);
			break;
		case "setomuserpwd": // 设置订货会用户随机密码
			SetOmuserpwd(response, htp1);
			break;
		case "autotoomuserno": // 自动生成订货会用户登录账号，如果客户没有账号，自动生成一个
			AutotoOmuserno(response, htp1);
			break;
		// ========================================

		case "addomsubject": // 增加订货会项目
			AddOmsubject(response, htp1);
			break;
		case "delomsubject": // 删除订货会项目
			DelOmsubject(response, htp1);
			break;
		case "getomsubject": // 获取指定的订货会
			GetOmsubject(response, htp1);
			break;

		case "getomsubject1": // 获取当前召开的订货会
			GetOmsubject1(response, htp1);
			break;

		case "updateomsubject": // 修改订货会项目
			UpdateOmsubject(response, htp1);
			break;
		case "listomsubject": // 显示订货会项目
			ListOmsubject(response, htp1);
			break;

		case "listompole": // 查询订货商品陈列杆记录列表
			ListOmpole(response, htp1);
			break;

		case "addompole": // 新增订货商品陈列杆记录
			AddOmpole(response, htp1);
			break;
		case "updateompole": // 修改订货商品陈列杆记录
			UpdateOmpole(response, htp1);
			break;
		case "delompole": // 删除订货商品陈列杆记录
			DelOmpole(response, htp1);
			break;
		case "listompoleware": // 显示陈列杆商品信息
			ListOmpoleware(response, htp1);
			break;
		case "selectompoleware": // 选择陈列杆商品列表
			SelectOmpoleware(response, htp1);
			break;

		case "saveompoleware": // 保存订货商品陈列杆记录
			SaveOmpoleware(response, htp1);
			break;

		case "delompoleware": // 保存订货商品陈列杆记录
			DelOmpoleware(response, htp1);
			break;

		case "listomwarecode": // 显示订货会商品
			ListOmwarecode(response, htp1);
			break;
		case "addomwarecode": // 增加订货会商品
			AddOmwarecode(response, htp1);
			break;
		case "delomwarecode": // 删除订货会商品
			DelOmwarecode(response, htp1);
			break;
		case "updateomwarecode": // 更改订货会商品数量
			UpdateOmwarecode(response, htp1);
			break;

		case "saveomwarecode1": // 保存订货商品相关搭配
			SaveOmwarecode1(response, htp1);
			break;
		case "listomwarecode1": // 显示商品搭配信息
			ListOmwarecode1(response, htp1);
			break;
		case "delomwarecode1": // 删除商品搭配信息
			DelOmwarecode1(response, htp1);
			break;
		case "selectomwarecode1": // 选择商品搭配列表
			SelectOmwarecode1(response, htp1);
			break;

		case "writeomwarenocolor": // 写指定商品可用颜色 (可用，禁用)
			WriteOmWarenocolor(response, htp1);
			break;

		case "omwarecolorlist": // 获取订货商品颜色列表(可用，禁用)
			GetOmWarecolorList(response, htp1);
			break;

		case "listomwave": // 查询订货商品波次
			ListOmwave(response, htp1);
			break;
		case "changeomsubject": // 更改订货会状态
			ChangeOmsubject(response, htp1);
			break;
		case "listomcustomer": // 显示订货会客户
			ListOmcustomer(response, htp1);
			break;
		case "listomcustomerin": // 显示参数订货会的客户
			ListOmcustomerIn(response, htp1);
			break;

		case "addomcustomer": // 增加订货会客户 未用
			AddOmcustomer(response, htp1);
			break;
		case "writeomcustomer": // 增减订货会客户
			WriteOmcustomer(response, htp1);
			break;
		case "allomcustomer": // 全选或取消订货会客户
			AllOmcustomer(response, htp1);
			break;
		case "orderunlock": // 订货会解锁
			OrderUnLock(response, htp1);
			break;

		// 订货会后台end;

		default:
			WriteResult(response, 0, "ERP接口未定义:" + action + "!");
			// response.getWriter().append("接口未定义:"+action+" !");
			// String jsonstr="{\"result\":0,\"msg\":\"接口 "+action+" 未定义!\"}";
			// response.getWriter().write(jsonstr);
			break;
		}
	}

	// 订货会后台接口begin
	// 增加订货会用户
	protected void AddOmuser(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		Omuser dal = new Omuser();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);

		Omplan omplan = new Omplan();
		DbHelperSQL.JsonConvertObject1(omplan, jsonObject);
		omplan.setOmid(omid);
		// omplan.setLastop(htp.getUsername());

		dal.setAccid(htp.getMaccid());
		omplan.setLastop(htp.getUsername());
		if (dal.Append(omid, omplan) == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResult(response, 1, dal.getErrmess());
		}

	}

	// 从excel导入订货会用户
	protected void AppendOmuserxls(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omepid = jsonObject.has("omepid") ? Long.parseLong(jsonObject.getString("omepid")) : 0;
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		// String custname = jsonObject.has("custname") ? jsonObject.getString("custname") : "";
		Omuser dal = new Omuser();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		Omplan omplan = new Omplan();
		DbHelperSQL.JsonConvertObject1(omplan, jsonObject);
		omplan.setOmid(omid);
		omplan.setOmepid(omepid);
		omplan.setLastop(htp.getUsername());
		dal.setOmepid(omepid);
		dal.setAccid(htp.getMaccid());
		WriteResult(response, dal.AppendFromXLS(omid, omplan), dal.getErrmess());
	}

	// // 显示订货商品汇总记录(按尺码横向展开)订货商品实时监控
	// protected void ListOmcustwaretotalsize(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
	// JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
	// long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
	// // long custid = jsonObject.has("custid") ? Long.parseLong(jsonObject.getString("custid")) : 0;
	// String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
	// String omepidlist = jsonObject.has("omepidlist") ? jsonObject.getString("omepidlist") : "";
	// String custidlist = jsonObject.has("custidlist") ? jsonObject.getString("custidlist") : "";
	// int sizenum = jsonObject.has("sizenum") ? Integer.parseInt(jsonObject.getString("sizenum")) : 10;
	// int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
	// int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
	// if (pagesize > 50)
	// pagesize = 10;
	// String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " wareno ";
	// String order = jsonObject.has("order") ? jsonObject.getString("order") : " asc ";
	// if (sizenum < 5)
	// sizenum = 5;
	// sort += " " + order + ",wareid";
	//
	// String qry = "select a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.sizegroupno,b.imagename";
	// // if (hzfs == 1)
	// qry += ",a.colorid,c.colorname";
	// qry += ",sum(a.amount) as amount,sum(a.amount*b.retailsale) as retailcurr";
	// qry += "\n from omcustware a ";
	// qry += "\n left outer join warecode b on a.wareid=b.wareid";
	// qry += "\n left outer join colorcode c on a.colorid=c.colorid";
	// qry += "\n where a.omid=" + omid;
	// if (!omepidlist.equals(""))
	// qry += " and " + Func.getCondition("a.omepid", omepidlist);
	// if (!custidlist.equals(""))
	// // qry += " and a.custid=" + custid;
	// qry += " and exists (select 1 from omuser c where a.omepid=c.omepid and " + Func.getCondition("c.custid", custidlist) + ")";
	//
	// if (findbox != null && !findbox.equals(""))
	// qry += " and (b.warename like '%" + findbox + "%' or b.wareno like '%" + findbox.toUpperCase() + "%')";
	// qry += "\n group by a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.sizegroupno,b.imagename";
	// qry += "\n ,a.colorid,c.colorname";
	// //System.out.println(qry);
	// // QueryParam qp = new QueryParam();
	// // qp.setPageIndex(page);
	// // qp.setPageSize(pagesize);
	// // qp.setSortString(sort);
	// // qp.setQueryString(qry);
	// QueryParam qp = new QueryParam(page, pagesize, sort, qry);
	//
	// qp.setSumString("nvl(sum(amount),0) as totalamt,nvl(sum(retailcurr),0) as totalcurr,count(distinct wareid) as totalnum");
	//
	// Table tb = new Table();
	// Omcustware dal = new Omcustware();
	// dal.setOmid(omid);
	// String jsonstr = dal.GetTable2Json(qp, custidlist, sizenum);
	//
	// Write(response, jsonstr);
	// }

	// 显示订货商品汇总记录(按尺码横向展开)订货商品实时监控 汇总
	protected void repCxomorder(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		int sizenum = jsonObject.has("sizenum") ? Integer.parseInt(jsonObject.getString("sizenum")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		vTotalomorder dal = new vTotalomorder();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(htp.getMaccid());
		dal.setUserid(htp.getUserid());

		QueryParam qp = new QueryParam();
		Table tb = new Table();

		qp.setPageIndex(page);
		qp.setPageSize(pagesize);

		if (sizenum > 0) // 如果尺码大于0，表示尺码横向展开方式
		{
			String jsonstr = dal.GetTable2Json(qp, sizenum);
			if (page < 0) {// 导出excel
				String retcs = DbHelperSQL.Json2Excel(jsonstr, jsonObject);
				WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
				return;
			}
			Write(response, jsonstr);
			return;
		}
		tb = dal.GetTable(qp);
		if (page < 0) {// 导出excel
			String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;
		}
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 返回订货表商品颜色尺码二维表格式 手机 new
	protected void ListOmtotalcolorandsize(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		// int fs = jsonObject.has("fs") ? Integer.parseInt(jsonObject.getString("fs")) : 0;
		Omcustware dal = new Omcustware();
		dal.setAccid(htp.getMaccid());
		dal.setWareid(wareid);
		dal.setOmid(omid);
		if (dal.listOmcustwaremx("") == 0)
			WriteResult(response, 0, dal.getErrmess());
		else
			WriteResultJson(response, 1, dal.getErrmess());

	}

	// 订货会商品陈列汇总
	protected void ListOmtotalpole(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		String custidlist = jsonObject.has("custidlist") ? jsonObject.getString("custidlist") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		// String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " wareno ";
		// String order = jsonObject.has("order") ? jsonObject.getString("order") : " asc ";
		// if (sizenum < 5)
		// sizenum = 5;
		String sort = " poleid ";

		QueryParam qp = new QueryParam(page, pagesize, sort);

		Omcustware dal = new Omcustware();
		dal.setOmid(omid);
		dal.setCustidlist(custidlist);
		Table tb = new Table();
		tb = dal.GetTable1(qp);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 订货会商品实时监控，手机app调用
	protected void ListOmtotal(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		// long poleid = jsonObject.has("custid") ? Long.parseLong(jsonObject.getString("custid")) : 0;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String poleidlist = jsonObject.has("poleidlist") ? jsonObject.getString("poleidlist") : "";
		String custidlist = jsonObject.has("custidlist") ? jsonObject.getString("custidlist") : "";
		// int sizenum = jsonObject.has("sizenum") ? Integer.parseInt(jsonObject.getString("sizenum")) : 10;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " wareno ";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : " asc ";
		// if (sizenum < 5)
		// sizenum = 5;
		sort += " " + order + ",wareid";

		QueryParam qp = new QueryParam(page, pagesize, sort);
		// QueryParam qp = new QueryParam();
		// qp.setPageIndex(page);
		// qp.setPageSize(pagesize);
		// qp.setSortString(sort);

		Omcustware dal = new Omcustware();
		dal.setOmid(omid);
		dal.setFindbox(findbox);
		dal.setCustidlist(custidlist);
		dal.setPoleidlist(poleidlist);
		Table tb = new Table();
		tb = dal.GetTable(qp);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
		// if (page < 0) {// 导出excel
		// // String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
		// String retcs = DbHelperSQL.Json2Excel(jsonstr, jsonObject);
		// WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
		//
		// return;
		// }
		// dal.setOmepid(htp.getUserid());
		// Write(response, jsonstr);

		// Write(response, jsonstr);
	}

	// 显示订货商品汇总记录(按尺码横向展开)订货商品实时监控
	protected void ListOmcustwaretotalsize(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		// long custid = jsonObject.has("custid") ? Long.parseLong(jsonObject.getString("custid")) : 0;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		// String omepidlist = jsonObject.has("omepidlist") ? jsonObject.getString("omepidlist") : "";
		String custidlist = jsonObject.has("custidlist") ? jsonObject.getString("custidlist") : "";
		int sizenum = jsonObject.has("sizenum") ? Integer.parseInt(jsonObject.getString("sizenum")) : 10;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " wareno ";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : " asc ";
		if (sizenum < 5)
			sizenum = 5;
		sort += " " + order + ",wareid";

		QueryParam qp = new QueryParam(page, pagesize, sort);
		// QueryParam qp = new QueryParam();
		// qp.setPageIndex(page);
		// qp.setPageSize(pagesize);
		// qp.setSortString(sort);

		Omcustware dal = new Omcustware();
		dal.setOmid(omid);
		dal.setFindbox(findbox);
		String jsonstr = dal.doListOmcustwaretotalsize(qp, sizenum, custidlist);
		// if (page < 0) {// 导出excel
		// // String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
		// String retcs = DbHelperSQL.Json2Excel(jsonstr, jsonObject);
		// WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
		//
		// return;
		// }

		// dal.setOmepid(htp.getUserid());
		Write(response, jsonstr);

		// Write(response, jsonstr);
	}

	// 修改订货会用户
	protected void UpdateOmuser(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		Omuser dal = new Omuser();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);

		Omplan omplan = new Omplan();
		DbHelperSQL.JsonConvertObject1(omplan, jsonObject);

		dal.setAccid(htp.getMaccid());
		omplan.setLastop(htp.getUsername());
		WriteResult(response, dal.Update(omid, omplan), dal.getErrmess());

	}

	// 删除订货会用户
	protected void DelOmuser(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omepid = jsonObject.has("omepid") ? Long.parseLong(jsonObject.getString("omepid")) : 0;
		Omuser dal = new Omuser();
		dal.setOmepid(omepid);
		dal.setAccid(htp.getMaccid());
		WriteResult(response, dal.Delete(), dal.getErrmess());
	}

	// 显示订货会客户用户列表
	protected void ListOmuser(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long custid = jsonObject.has("custid") ? Long.parseLong(jsonObject.getString("custid")) : 0;
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : "asc";
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " omepid ";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = "a.omepid,a.omepno,a.omepname,a.remark,a.js,a.mobile,a.password,a.statetag,c.custname";
		fieldlist += ",nvl(b.jhamt,0) as jhamt,nvl(b.jhcurr,0) as jhcurr,nvl(b.jhskc,0) as jhskc,nvl(b.jhnum,0) as jhnum";
		String strwhere = " a.accid=" + htp.getMaccid();

		strwhere += " and c.statetag=1 ";
		// strwhere += " and exists (select 1 from omcustomer x where a.custid=x.custid and b.omid=x.omid and x.statetag=1) ";
		if (custid > 0)
			strwhere += " and a.custid=" + custid;
		else strwhere += " and c.noused=0";
		if (!findbox.equals(""))
			strwhere += " and (a.omepname like '%" + findbox + "%' or a.omepno like '%" + findbox.toUpperCase() + "%')";
		sort += " " + order;
		Omuser dal = new Omuser();
		// QueryParam qp = new QueryParam();
		// qp.setPageIndex(page);
		// qp.setPageSize(pagesize);
		// qp.setSortString(sort);
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere, fieldlist, omid);
		if (page < 0) {// 导出excel
			String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;
		}

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 显示订货会所有客户用户列表
	protected void ListAllOmuser(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long custid = jsonObject.has("custid") ? Long.parseLong(jsonObject.getString("custid")) : 0;
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		// String order = jsonObject.has("order") ? jsonObject.getString("order") : "asc";
		// String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " omepid ";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = "a.omepid,a.omepno,a.omepname,a.remark,a.js,a.mobile,a.password,a.statetag,a.custid,b.custname";
		// fieldlist += ",nvl(b.jhamt,0) as jhamt,nvl(b.jhcurr,0) as jhcurr,nvl(b.jhskc,0) as jhskc,nvl(b.jhnum,0) as jhnum";
		String sort = "custid,omepname";
		String strwhere = "a.accid=" + htp.getMaccid();
		strwhere += " and exists (select 1 from omcustomer c where c.omid=" + omid + " and c.custid=a.custid)";
		// if (custid>0)
		// strwhere += " a.custid=" + custid;
		if (!findbox.equals(""))
			strwhere += " and (a.omepname like '%" + findbox + "%' or a.omepno like '%" + findbox.toUpperCase() + "%')";
		// sort += " " + order;
		Omuser dal = new Omuser();
		// QueryParam qp = new QueryParam();
		// qp.setPageIndex(page);
		// qp.setPageSize(pagesize);
		// qp.setSortString(sort);
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere, fieldlist);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 设置订货会用户6位随机密码
	protected void SetOmuserpwd(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		Omuser dal = new Omuser();
		dal.setAccid(htp.getMaccid());
		WriteResult(response, dal.doRandompwd(omid), dal.getErrmess());
	}

	// 自动生成订货会用户登账账号
	protected void AutotoOmuserno(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		Omuser dal = new Omuser();
		dal.setAccid(htp.getMaccid());
		WriteResult(response, dal.doAutotoOmuserno(omid), dal.getErrmess());
	}

	// 增加订货会项目
	protected void AddOmsubject(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		Omsubject dal = new Omsubject();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		dal.setStatetag(0);
		WriteResult(response, dal.Append(htp.getPictjson()), dal.getErrmess());

	}

	// 当获指定的订货会
	protected void GetOmsubject(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;

		if (fieldlist.equals("*") || fieldlist.length() <= 0)
			fieldlist = "a.omid,a.omname,a.begindate,a.enddate,a.remark,a.address,a.linkman,a.linktel,a.wxts ";
		String strwhere = " a.accid=" + htp.getMaccid() + " and a.omid=" + omid;
		Omsubject dal = new Omsubject();

		Table tb = new Table();
		tb = dal.GetList(strwhere, fieldlist).getTable(1);
		Write(response, DbHelperSQL.DataTable2Json(tb));
	}
	// 当获当前召开的订货会
	protected void GetOmsubject1(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
//		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;

		if (fieldlist.equals("*") || fieldlist.length() <= 0)
			fieldlist = "a.omid,a.omname,a.begindate,a.enddate,a.remark,a.address,a.linkman,a.linktel,a.wxts ";
		String strwhere = " a.accid=" + htp.getMaccid() + " and a.statetag>=1 and a.statetag<=3 and rownum=1";
		Omsubject dal = new Omsubject();

		Table tb = new Table();
		tb = dal.GetList(strwhere, fieldlist).getTable(1);
		Write(response, DbHelperSQL.DataTable2Json(tb));
	}

	// 删除订货会项目
	protected void DelOmsubject(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;

		Omsubject dal = new Omsubject();
		dal.setOmid(omid);
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Delete(), dal.getErrmess());
	}

	// 修改订货会项目
	protected void UpdateOmsubject(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		Omsubject dal = new Omsubject();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		dal.setStatetag(0);
		WriteResult(response, dal.Update(htp.getPictjson()), dal.getErrmess());
	}

	// 显示订货会项目列表
	protected void ListOmsubject(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int statetag = jsonObject.has("statetag") ? Integer.parseInt(jsonObject.getString("statetag")) : 3;
		// statetag:0=未开 1=已开 2=结束

		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		// String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " poleid ";
		// String order = jsonObject.has("order") ? jsonObject.getString("order") : " asc ";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = " a.omid,a.omname,a.begindate,a.enddate,a.statetag,a.remark,a.address,a.omimagename ,a.logoimagename";
		String strwhere = " a.accid=" + htp.getMaccid();
		if (statetag < 3)
			strwhere += " and a.statetag=" + statetag;
		if (findbox.length() > 0)
			strwhere += " and a.omname like '%" + findbox + "%'";

		String sort = " omid  desc";
		Omsubject dal = new Omsubject();

		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 查询订货商品陈列杆记录列表
	protected void ListOmpole(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " poleid ";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : " asc ";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = "poleid,polename,imagename,warenum,lastdate";
		String strwhere = " omid=" + omid;
		sort += " " + order + ",rowid ";
		// QueryParam qp = new QueryParam();
		// qp.setPageIndex(page);
		// qp.setPageSize(pagesize);
		// qp.setSortString(sort);
		// qp.setQueryString(qry);

		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		Ompole dal = new Ompole();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 增加订货会商品陈列杆
	protected void AddOmpole(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		Ompole dal = new Ompole();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setOmid(omid);
		dal.setLastop(htp.getUsername());

		WriteResult(response, dal.Append(htp.getPictjson()), dal.getErrmess());

	}

	// 修改订货会商品陈列杆
	protected void UpdateOmpole(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		long poleid = jsonObject.has("poleid") ? Long.parseLong(jsonObject.getString("poleid")) : 0;
		Ompole dal = new Ompole();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		// 、、dal.setOmid(omid);
		// dal.setPoleid(poleid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Update(htp.getPictjson()), dal.getErrmess());
	}

	// 删除订货会陈列商品
	protected void DelOmpole(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		long poleid = jsonObject.has("poleid") ? Long.parseLong(jsonObject.getString("poleid")) : 0;
		Ompole dal = new Ompole();
		// dal.setOmid(omid);
		dal.setPoleid(poleid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Delete(), dal.getErrmess());
	}

	// 显示陈列杆商品信息
	protected void ListOmpoleware(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		long poleid = jsonObject.has("poleid") ? Long.parseLong(jsonObject.getString("poleid")) : 0;
		if (poleid == 0) {
			WriteResult(response, 0, "参数无效！");
			return;
		}
		Ompoleware dal = new Ompoleware();
		String fieldlist = "a.wareid,b.warename,b.units,b.wareno,b.imagename,b.retailsale";
		String strwhere = " poleid=" + poleid + " and b.statetag=1 and b.noused=0  and b.accid=" + htp.getMaccid();
		String sort = " b.wareno,a.wareid";
		Table tb = new Table();
		// 不分页
		tb = dal.GetTable(strwhere, fieldlist, sort);
		Write(response, DbHelperSQL.DataTable2Json(tb));
	}

	// 选择陈列标商品列表
	protected void SelectOmpoleware(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";

		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		long poleid = jsonObject.has("poleid") ? Long.parseLong(jsonObject.getString("poleid")) : 0;

		// long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		long brandid = jsonObject.has("brandid") ? Long.parseLong(jsonObject.getString("brandid")) : -1;
		long typeid = jsonObject.has("typeid") ? Long.parseLong(jsonObject.getString("typeid")) : -1;
		String seasonname = jsonObject.has("seasonname") ? jsonObject.getString("seasonname") : "";
		String prodyear = jsonObject.has("prodyear") ? jsonObject.getString("prodyear") : "";
		String remark = jsonObject.has("remark") ? jsonObject.getString("remark") : "";
		int havepicture = jsonObject.has("havepicture") ? Integer.parseInt(jsonObject.getString("havepicture")) : 2;

		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = "a.omid,a.wareid,b.wareno,b.warename,b.retailsale,b.units,b.imagename,a.permitamt,a.mustbj";
		fieldlist += " ,c.typename,b.prodyear,b.salerange,b.seasonname,d.wavename,b.ssdate,b.remark";
		String strwhere = " a.omid=" + omid;
		if (findbox.length() > 0)
			strwhere += " and (b.warename like '%" + findbox + "%' or b.wareno like '%" + findbox.toUpperCase() + "%')";

		strwhere += "\n  and not exists (select 1 from Ompoleware a1 where a.wareid=a1.wareid and a1.poleid=" + poleid + ")";

		if (havepicture == 0) // 只返回无图片的商品
		{
			strwhere += " and (length(b.imagename)=0 or b.imagename is null ) ";
		} else if (havepicture == 1) // 只返回有图片的商品
		{
			// qry += " and (length(a.imagename)>0 or length(a.imagename0)>0) and not a.imagename is null and not a.imagename0 is null ";
			strwhere += " and length(b.imagename)>0 and not b.imagename is null ";
		}

		if (typeid >= 0)
			if (typeid > 0 && typeid < 1000) {
				strwhere += "    and exists (select 1 from waretype t where t.typeid=b.typeid and t.p_typeid= " + typeid + ")";
			} else {
				strwhere += "    and b.typeid=" + typeid;
			}
		if (brandid >= 0)
			strwhere += " and b.brandid=" + brandid;
		if (findbox.length() > 0)
			strwhere += " and (b.warename like '%" + findbox + "%' or b.wareno like '%" + findbox.toUpperCase() + "%')";
		if (remark.length() > 0)
			strwhere += " and  b.remark like '%" + remark + "%'";
		if (prodyear.length() > 0)
			strwhere += " and  b.prodyear = '" + prodyear + "'";
		if (seasonname.length() > 0)
			strwhere += " and  b.seasonname = '" + seasonname + "'";

		// QueryParam qp = new QueryParam();
		// qp.setPageIndex(page);
		// qp.setPageSize(pagesize);
		// qp.setSortString("wareno,wareid");
		QueryParam qp = new QueryParam(page, pagesize, "wareno,wareid");
		// qp.setQueryString(qry);

		Table tb = new Table();
		Omwarecode dal = new Omwarecode();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 保存订货会陈列杆商品
	protected void SaveOmpoleware(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long poleid = jsonObject.has("poleid") ? Long.parseLong(jsonObject.getString("poleid")) : 0;
		Ompoleware dal = new Ompoleware();
		dal.setPoleid(poleid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Append(jsonObject), dal.getErrmess());

	}

	// 删除订货会陈列商品
	protected void DelOmpoleware(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long poleid = jsonObject.has("poleid") ? Long.parseLong(jsonObject.getString("poleid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		Ompoleware dal = new Ompoleware();
		dal.setPoleid(poleid);
		dal.setWareid(wareid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Delete(), dal.getErrmess());

	}

	// 显示订货会商品
	protected void ListOmwarecode(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		long brandid = jsonObject.has("brandid") ? Long.parseLong(jsonObject.getString("brandid")) : -1;
		long typeid = jsonObject.has("typeid") ? Long.parseLong(jsonObject.getString("typeid")) : -1;
		// String wareno = jsonObject.has("wareno") ? jsonObject.getString("wareno") : "";
		String seasonname = jsonObject.has("seasonname") ? jsonObject.getString("seasonname") : "";
		String prodyear = jsonObject.has("prodyear") ? jsonObject.getString("prodyear") : "";
		String remark = jsonObject.has("remark") ? jsonObject.getString("remark") : "";
		int havepicture = jsonObject.has("havepicture") ? Integer.parseInt(jsonObject.getString("havepicture")) : 2;

		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = "a.omid,a.wareid,b.wareno,b.warename,b.retailsale,b.units,b.imagename,a.permitamt,a.mustbj,a.noused ";
		fieldlist += " ,c.typename,c.fullname,b.prodyear,b.salerange,b.seasonname,d.wavename,b.ssdate,b.remark";
		String strwhere = " a.omid=" + omid;
		if (!findbox.equals(""))
			strwhere += " and (b.warename like '%" + findbox + "%' or b.wareno like '%" + findbox.toUpperCase() + "%')";
		if (brandid > 0)
			strwhere += " and b.brandid=" + brandid;
		if (typeid > 0)
			strwhere += " and b.typeid=" + typeid;
		if (!seasonname.equals(""))
			strwhere += " and b.seasonname='" + seasonname + "'";
		// if (!wareno.equals(""))
		// strwhere += " and b.wareno like '%" + wareno + "%'";

		if (!prodyear.equals(""))
			strwhere += " and b.prodyear='" + prodyear + "'";
		if (!remark.equals(""))
			strwhere += " and b.remark like '%" + remark + "%'";
		if (havepicture == 0) // 只返回无图片的商品
		{
			strwhere += " and (length(b.imagename)=0 or b.imagename is null ) ";
		} else if (havepicture == 1) // 只返回有图片的商品
		{
			// qry += " and (length(a.imagename)>0 or length(a.imagename0)>0) and not a.imagename is null and not a.imagename0 is null ";
			strwhere += " and length(b.imagename)>0 and not b.imagename is null ";
		}

		String sort = " wareno,rowid";
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		Omwarecode dal = new Omwarecode();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 增加订货会商品资料
	protected void AddOmwarecode(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		Omwarecode dal = new Omwarecode();
		// DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setOmid(omid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Append(jsonObject), dal.getErrmess());

	}

	// 删除订货会商品资料
	protected void DelOmwarecode(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;

		Omwarecode dal = new Omwarecode();

		dal.setOmid(omid);
		dal.setLastop(htp.getUsername());
		dal.setWareid(wareid);
		WriteResult(response, dal.Delete(), dal.getErrmess());
	}

	// 更改订货会商品数量
	protected void UpdateOmwarecode(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		// long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		Omwarecode dal = new Omwarecode();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		// dal.setOmid(omid);
		dal.setLastop(htp.getUsername());
		// dal.setWareid(wareid);
		WriteResult(response, dal.Update(), dal.getErrmess());
	}

	// 保存订货会商品相关搭配
	protected void SaveOmwarecode1(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		Omwarecode1 dal = new Omwarecode1();
		dal.setOmid(omid);
		dal.setWareid(wareid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Append(jsonObject), dal.getErrmess());

	}

	// 显示商品搭配信息
	protected void ListOmwarecode1(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		String fieldlist = " b.wareid,b.warename,b.units,b.wareno,b.imagename,b.retailsale";
		String strwhere = " a.omid=" + omid + " and a.wareid=" + wareid;
		String order = " b.wareno,b.wareid";
		Omwarecode1 dal = new Omwarecode1();
		Table tb = new Table();
		tb = dal.GetTable(strwhere, fieldlist, order);

		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 保存订货商品可用颜色
	protected void WriteOmWarenocolor(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		Omwarenocolor dal = new Omwarenocolor();
		dal.setWareid(wareid);
		dal.setOmid(omid);

		WriteResult(response, dal.Append(jsonObject), dal.getErrmess());
	}

	// 获取订货商品颜色列表(可用，禁用)
	protected void GetOmWarecolorList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		//System.out.println("GetOmWarecolorList");
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		Omwarenocolor dal = new Omwarenocolor();
		dal.setWareid(wareid);
		dal.setOmid(omid);
		Table tb = new Table();
		tb = dal.GetTable();
		Write(response, DbHelperSQL.DataTable2Json(tb));
	}

	// 删除搭配商品
	protected void DelOmwarecode1(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		long wareid1 = jsonObject.has("wareid1") ? Long.parseLong(jsonObject.getString("wareid1")) : 0;
		Omwarecode1 dal = new Omwarecode1();
		dal.setOmid(omid);
		dal.setWareid(wareid);
		dal.setWareid1(wareid1);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Delete(), dal.getErrmess());

	}

	// 选择商品搭配列表
	protected void SelectOmwarecode1(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		long brandid = jsonObject.has("brandid") ? Long.parseLong(jsonObject.getString("brandid")) : -1;
		long typeid = jsonObject.has("typeid") ? Long.parseLong(jsonObject.getString("typeid")) : -1;
		String seasonname = jsonObject.has("seasonname") ? jsonObject.getString("seasonname") : "";
		String prodyear = jsonObject.has("prodyear") ? jsonObject.getString("prodyear") : "";
		String remark = jsonObject.has("remark") ? jsonObject.getString("remark") : "";
		int havepicture = jsonObject.has("havepicture") ? Integer.parseInt(jsonObject.getString("havepicture")) : 2;

		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";

		String fieldlist = " a.omid,a.wareid,b.wareno,b.warename,b.retailsale,b.units,b.imagename,a.permitamt,a.mustbj ";
		fieldlist += " ,c.typename,b.prodyear,b.salerange,b.seasonname,d.wavename,b.ssdate,b.remark";

		String strwhere = " a.omid=" + omid + "  and a.wareid<>" + wareid;

		if (havepicture == 0) // 只返回无图片的商品
		{
			strwhere += " and (length(b.imagename)=0 or b.imagename is null ) ";
		} else if (havepicture == 1) // 只返回有图片的商品
		{
			// qry += " and (length(a.imagename)>0 or length(a.imagename0)>0) and not a.imagename is null and not a.imagename0 is null ";
			strwhere += " and length(b.imagename)>0 and not b.imagename is null ";
		}

		if (typeid >= 0)
			if (typeid > 0 && typeid < 1000) {
				strwhere += "    and exists (select 1 from waretype t where t.typeid=b.typeid and t.p_typeid= " + typeid + ")";
			} else {
				strwhere += "    and b.typeid=" + typeid;
			}
		if (brandid >= 0)
			strwhere += " and b.brandid=" + brandid;
		if (findbox.length() > 0)
			strwhere += " and (b.warename like '%" + findbox + "%' or b.wareno like '%" + findbox.toUpperCase() + "%')";
		if (remark.length() > 0)
			strwhere += " and  b.remark like '%" + remark + "%'";
		if (prodyear.length() > 0)
			strwhere += " and  b.prodyear = '" + prodyear + "'";
		if (seasonname.length() > 0)
			strwhere += " and  b.seasonname = '" + seasonname + "'";

		strwhere += "\n  and not exists (select 1 from omwarecode1 a1 where a1.omid=" + omid + " and a.wareid=a1.wareid1 and a1.wareid=" + wareid + ")";
		String sort = " wareno,wareid";
		Omwarecode dal = new Omwarecode();
		QueryParam qp = new QueryParam(page, pagesize, sort);
		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere, fieldlist);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 显示订货会商品波次
	protected void ListOmwave(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;

		String fieldlist = " a.waveid,a.wavename,to_char(a.begindate,'yyyy-mm-dd') as begdate,to_char(a.enddate,'yyyy-mm-dd') as enddate";
		String strwhere = " a.statetag=1 and a.accid=" + htp.getMaccid();
		strwhere += " and exists (select 1 from omwarecode b join warecode c on b.wareid=c.wareid where b.omid=" + omid;
		strwhere += " and a.waveid=c.waveid)";
		String sort = " waveid";
		Warewave dal = new Warewave();
		Table tb = new Table();
		tb = dal.GetTable(strwhere, fieldlist, sort);
		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 更改订货会状态
	protected void ChangeOmsubject(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		int opid = jsonObject.has("opid") ? Integer.parseInt(jsonObject.getString("opid")) : 0;
		/*
		 * statetag:0=等待 1=筹备2=开始3=暂停4=结束 opid=0 返回等待 1->0 opid=1 筹备中 0->1 opid=2 开始 1->2 opid=3 开始返筹备 2->1 opid=4 暂停 2->3 opid=5 暂停转开始 3->2 opid=6 结束 2->4 opid=7 结束返回 4->2
		 */
		Omsubject dal = new Omsubject();
		dal.setOmid(omid);
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.doChangeState(opid), dal.getErrmess());

	}

	// 显示参加订货会的客户
	protected void ListOmcustomerIn(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;

		// 已经加入订货会的下级商家
		String qry = "  select a.custid,a.custname,a.address,a.linkman,a.mobile";
		qry += "  from customer a";
		qry += "  join omcustomer b on b.omid=" + omid + " and a.custid=b.custid";
		qry += "  where  a.statetag=1 and a.noused=0 and a.accid=" + htp.getMaccid();
		if (!findbox.equals(""))
			qry += " and (a.custname like '%" + findbox + "%' or a.linkman like '%" + findbox + "%' or a.mobile like '%" + findbox + "%')";
		// WriteDebugTXT("listomcustomer", qry);
		String sort = " custname ,custid";
		QueryParam qp = new QueryParam(page, pagesize, sort, qry);
		Table tb = new Table();
		Omcustomer dal = new Omcustomer();
		tb = dal.GetTable(qp);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 显示订货会客户
	protected void ListOmcustomer(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		// 未加入订货会的下级商家
		String qry = " select a.custid,a.custname,a.address,a.linkman,a.mobile,0 as selbj";
		qry += "  from customer a";
		qry += "  where a.statetag=1 and a.noused=0 and a.accid=" + htp.getMaccid();
		if (!findbox.equals(""))
			qry += " and (a.custname like '%" + findbox + "%' or a.linkman like '%" + findbox + "%' or a.mobile like '%" + findbox + "%')";
		qry += "  and not exists (select 1 from omcustomer c where c.omid=" + omid + " and a.custid=c.custid)";
		qry += "  union all";
		// 已经加入订货会的下级商家
		qry += "  select a.custid,a.custname,a.address,a.linkman,a.mobile,1 as selbj";
		qry += "  from customer a";
		qry += "  join omcustomer b on b.omid=" + omid + " and a.custid=b.custid";
		qry += "  where  a.statetag=1 and a.noused=0 and a.accid=" + htp.getMaccid();
		if (!findbox.equals(""))
			qry += " and (a.custname like '%" + findbox + "%' or a.linkman like '%" + findbox + "%' or a.mobile like '%" + findbox + "%')";
		// WriteDebugTXT("listomcustomer", qry);
		String sort = " custname ,custid";
		QueryParam qp = new QueryParam(page, pagesize, sort, qry);
		Table tb = new Table();
		Omcustomer dal = new Omcustomer();
		tb = dal.GetTable(qp);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 增加订货会客户 未用
	protected void AddOmcustomer(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
	}

	// 增减订货会客户(代理商、经销商)
	protected void WriteOmcustomer(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		long custid = jsonObject.has("custid") ? Long.parseLong(jsonObject.getString("custid")) : 0;
		int value = jsonObject.has("value") ? Integer.parseInt(jsonObject.getString("value")) : 0;
		Omcustomer dal = new Omcustomer();
		dal.setOmid(omid);
		dal.setCustid(custid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.doWrite(value), dal.getErrmess());

	}

	// 全选或清除订货会客户
	protected void AllOmcustomer(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		int value = jsonObject.has("value") ? Integer.parseInt(jsonObject.getString("value")) : 0;
		Omcustomer dal = new Omcustomer();
		dal.setOmid(omid);
		// dal.setCustid(custid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.doWriteAll(htp.getMaccid(), value), dal.getErrmess());
	}

	// 订货会截图解锁
	protected void OrderUnLock(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omepid = jsonObject.has("omepid") ? Long.parseLong(jsonObject.getString("omepid")) : 0;
		Omuser dal = new Omuser();
		dal.setOmepid(omepid);
		dal.setAccid(htp.getMaccid());
		WriteResult(response, dal.UnLock(), dal.getErrmess());

	}

	// 订货会后台接口end

	// ********************************************************
	protected void ServerDate(HttpServletResponse response) throws ServletException, IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("ServerDate ");
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
