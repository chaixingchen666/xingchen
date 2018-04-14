package com.flyang.main;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.comdot.data.Table;
import com.common.tool.LogUtils;
import com.common.tool.Func;
import com.oracle.bean.*;
import com.netdata.db.*;
import net.sf.json.JSONObject;

/**
 * 
 * 订货会前端App接口
 */
@WebServlet("/ord")
public class OrdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String filePath = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrdServlet() {
		super();
		// TODO Auto-generated constructor stub
		filePath = Func.getRootPath();// +"skyderp.config/";

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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		// response.getWriter().append("ord ").append(request.getContextPath());
		String url = request.getRequestURL() + "?" + request.getQueryString();
		String action = request.getParameter("action").toLowerCase();
		// filePath = getServletContext().getRealPath("/");
		HttpInfo htp1 = new HttpInfo();
		htp1.setAction(action);// 设action
		htp1.setIpaddress(Func.getIpAddress(request));

		String signjson = request.getParameter("sign"); // 取sign参数

		// System.out.println("1111");
		// System.out.println(signjson);
		if (signjson == null || signjson.equals("")) {
			WriteResult(response, 0, "sign参数无效！");
			return;
		}
		// base64解码
		signjson = Func.Base64Decode(signjson);// sign base64解码
		htp1.setUrl(url);//

		// htp1.setDatajson(request.getParameter("data"));// 传入data参数
		String datajson = request.getParameter("data");
		if (datajson != null)
			htp1.setDatajson(datajson);// 传入data参数
		// htp1.setPictjson(request.getParameter("pict"));// 传入pict参数
		// =================
		String pictjson = request.getParameter("pict");

		if (pictjson != null)// pictjson="";
			htp1.setPictjson(pictjson);// 传入pict参数
		// ===================
		int ret = htp1.setSignjsonOrd(signjson);

		LogUtils.LogWrite(htp1.getUsername() +" "+ htp1.getIpaddress(), htp1.getUrl());
		if (ret == 0)// 校验参数是否有效
		{
			Write(response, htp1.getErrmess());
			return;
		}

		// LogUtils.LogWrite("1111", "2222","3333");

		String ss = Func.HttpIsDanger(htp1.getUrl());
		if (!ss.equals("")) {
			// 有危险操作的请求不允许通过
			LogUtils.LogWrite(htp1.getUsername() + "【 " + ss.trim() + "】" +htp1.getIpaddress(), htp1.getUrl(), "warn");
			WriteResult(response, 0, "警告：请求中含有危险操作命令！");
			return;
		}

		switch (action) {
		case "omuserlogin":
			OmuserLogin(response, htp1, 0);
			break;
		case "omuserloginx":
			OmuserLogin(response, htp1, 1); // 微信小程序登录
			break;

		case "updateomuser1": // 修改订货会用户信息
			UpdateOmuser1(response, htp1);
			break;

		case "writeomcustware": // 保存订货的商品明细
			WriteOmcustware(response, htp1);
			break;

		case "clearomcustware": // 清空订货的商品明细
			ClearOmcustware(response, htp1);
			break;

		case "listomcustwaremx": // 显示订货的商品明细(颜色尺码以二维表方式展开)
			ListOmcustwaremx(response, htp1);
			break;
		case "listomcustwaresum": // 显示订货的商品汇总（单品订货时用）
			ListOmcustwaresum(response, htp1);
			break;
		case "listomcustwaretotal": // 显示订货的商品汇总列表 (我的订单)
			ListOmcustwaretotal(response, htp1);
			break;
		case "listomcustwaretotal1": // 显示订货的商品汇总列表 (我的订单) 微信小程序
			ListOmcustwaretotal1(response, htp1);
			break;
		case "listomcustwaresort": // 显示订货的商品排行榜
			ListOmcustwaresort(response, htp1);
			break;

		case "providelist": // 供应商分页列表
			ProvideList(response, htp1);
			break;
		case "totalomcustware": // 订单汇总分析
			TotalOmcustware(response, htp1);
			break;

		case "omcustwaretoorder": // 订货会生成客户订单
			OmcustwareToorder(response, htp1);
			break;

		case "wxcustwaretoorder": // 微信小程序生成客户订单
			WxcustwareToorder(response, htp1);
			break;

		case "delomorder": // 删除订货会订单,并恢复订单数据到订货车
			DelOmorder(response, htp1);
			break;
		case "omdetailcancelorder": // 订货会取消采购订单，重新订货
			OmcustwareCancelorder(response, htp1);
			break;
		case "listcustompole": // 查询订货商品陈列杆记录列表
			ListCustOmpole(response, htp1);
			break;
		// case "citywaretocustorder": // 商城订货商品生成客户订单
		// OmCustWareToCustOrder(response, htp1);
		// break;

		case "getomwareinfo": // 返回商品的资料，尺码，颜色及相关搭配（订货会用）
			GetOmwareinfo(response, htp1);
			break;
		case "brandlist": // 品牌分页列表
			BrandList(response, htp1, 0);
			break;

		case "orderlock": // 订货会截屏加锁(订货会APP调用)
			OrderLock(response, htp1);
			break;
		case "custorderhlist": // 分页获取客户订单记录列表
			GetCustorderhList(response, htp1);
			break;
		case "getcustorderhbyid": // 获取指定客户订单记录id信息
			GetCustorderhByID(response, htp1);
			break;
		case "custordermcolorsumlist": // 获取客户订货明细汇总列表，按货号+颜色汇总 ok
			GetCustordermcolorsumList(response, htp1);
			break;
		case "custorderhcancel": // 客户订单撤单
			CustorderhCancel(response, htp1);
			break;

		case "cityhousebuylist": // 买家分页获取访问采购商城店铺列表
			GetCityHousebuyList(response, htp1);
			break;
		case "getcityorderbywareid": // 获取商城订货商品颜色及尺码数量
			GetCityorderbywareid(response, htp1);
			break;

		case "cityhousebuywarelist": // 分页获取经销商采购商城店铺商品列表
			GetCityHousebuywareList(response, htp1);
			break;
		case "getcityhousewarebyid": // 获取商城店铺商品记录信息
			GetCityhousewareById(response, htp1);
			break;

		case "getomcustwarebyid": // 获取微信订单商品颜色及尺码数量 微信小程序
			GetOmcustwarebyid(response, htp1);
			break;
		case "getcustordermsum": // 获取客户订货商品颜色尺码明细 ok微信小程序
			GetCustordermsum(response, htp1);
			break;

		case "writeomappraise": // 写商品评价
			WriteOmappraise(response, htp1);
			break;
		case "delomappraise": // 删除商品评价
			DelOmappraise(response, htp1);
			break;
		case "getomappraise": // 获取商品评价
			GetOmappraise(response, htp1);
			break;

		case "addomcollect": // 增加商品收藏
			AddOmcollectRec(response, htp1);
			break;
		case "delomcollect": // 删除商品收藏
			DelOmcollect(response, htp1);
			break;
		case "listomcollect": // 显示商品收藏
			ListOmcollect(response, htp1);
			break;
		case "listcustomuser": // 显示订货会客户用户列表
			ListCustOmuser(response, htp1);
			break;

		case "listomuserordersum": // 显示订货会用户订货汇总
			ListOmuserOrderSum(response, htp1);
			break;
		//
		case "omorderlist": // 分页客户订单记录列表
			OmOrderList(response, htp1);
			break;
		case "listomcustwarehz": // 显示订货会用户订货的商品汇总(未生成订单)
			ListOmcustwarehz(response, htp1);
			break;
		case "listomcustorderhz": // 显示订货会用户订货的商品汇总(已生成订单)
			ListOmcustorderhz(response, htp1);
			break;

		case "addbackprint": // 将单据转入后台打印服务
			AddBackprint(response, htp1);
			break;
		case "getwarebarcodebyno1": // 获取指定商品条码信息(订货会专用)
			GetWarebarcodeByNo1(response, htp1);
			break;
		case "getominfo": // 获取订货会商品类型，波段，价格范围等信息
			GetOminfo(response, htp1);
			break;

		// case "serverdatetime":
		// ServerDateTime(response);
		// break;
		default:
			WriteResult(response, 0, "ORD 接口未定义1:" + action + "!");
			break;
		}
	}

	// 订货会用户登录
	// protected void EmployeLogin()
	protected void OmuserLogin(HttpServletResponse response, HttpInfo htp, int fs) throws ServletException, IOException {
		// System.out.println("EmployeLogin begin...");

		Omuser dal = new Omuser();

		// Table tb = new Table();

		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		String omepno = jsonObject.has("omepno") ? jsonObject.getString("omepno") : "";
		String deviceno = jsonObject.has("deviceno") ? jsonObject.getString("deviceno") : "";
		String devicetype = jsonObject.has("devicetype") ? jsonObject.getString("devicetype") : "???";
		String password = jsonObject.has("password") ? jsonObject.getString("password") : "";
		// int all = jsonObject.has("all") ?
		// Integer.parseInt(jsonObject.getString("all")) : 0;
		long accid = jsonObject.has("accid") ? Long.parseLong(jsonObject.getString("accid")) : 0;

		dal.setAccid(accid);
		dal.setOmepno(omepno.toUpperCase().trim());
		dal.setPassword(password.toUpperCase());
		int ret = dal.Login(deviceno, devicetype, fs);
		// System.out.println("EmployeLogin " + ret);

		// System.out.println("EmployeLogin "+dal.getErrmess());
		// Write(response);
		if (ret == 0)
			WriteResult(response, 0, dal.getErrmess());
		else
			WriteResultJson(response, 1, dal.getErrmess());

	}

	// 删除商品收藏
	protected void DelOmcollect(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Omcollect dal = new Omcollect();
		// long omid = jsonObject.has("omid") ?
		// Long.parseLong(jsonObject.getString("omid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		// if (omid == 0 || wareid == 0) {
		// WriteResult(response, 0, "订货会/商品参数无效！");
		// return;
		// }
		dal.setOmid(htp.getOmid());
		dal.setWareid(wareid);
		dal.setOmepid(htp.getUserid());

		if (dal.Delete() == 0) {
			WriteResult(response, 0, "删除失败！");
		} else {
			WriteResult(response, 1, "删除成功！");
		}

	}

	// 删除商品评价
	protected void DelOmappraise(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		Omappraise dal = new Omappraise();
		dal.setOmid(htp.getOmid());
		dal.setOmepid(htp.getUserid());
		dal.setWareid(wareid);
		if (dal.Delete() == 0) {
			WriteResult(response, 0, "删除失败！");
		} else {
			WriteResult(response, 1, "删除成功！");
		}

	}

	// 获取商品评价
	protected void GetOmappraise(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		Omappraise dal = new Omappraise();
		// long omid = jsonObject.has("omid") ?
		// Long.parseLong(jsonObject.getString("omid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;

		String strwhere = " omid=" + htp.getOmid() + " and omepid=" + htp.getUserid() + " and wareid=" + wareid;

		String fieldlist = " score,remark ";

		Table tb = new Table();
		tb = dal.GetList(strwhere, fieldlist).getTable(1);

		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 订货会app,写商品评价
	protected void WriteOmappraise(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Omappraise dal = new Omappraise();
		dal.setOmid(htp.getOmid());
		dal.setOmepid(htp.getUserid());
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);

		// if (dal.Append(htp.getUserid()) == 0) {
		WriteResult(response, dal.Append(htp.getUserid()), dal.getErrmess());
		// } else {
		// WriteResult(response, 1, "操作成功！");
		// }
	}

	// 增加商品收藏
	protected void AddOmcollectRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Omcollect dal = new Omcollect();
		// 读取json数据到表类
		// DbHelperSQL.JsonConvertObject(dal, jsonObject);
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;

		if (omid == 0 || wareid == 0) {
			WriteResult(response, 0, "订货会/商品参数无效！");
			return;
		}
		dal.setOmid(omid);
		dal.setWareid(wareid);
		dal.setOmepid(htp.getUserid());

		// dal.setLastop(htp.getUsername());
		// dal.setStatetag(1);
		// System.out.println("addsalecode 3");
		WriteResult(response, dal.Append(), dal.getErrmess());
		// if (dal.Append() == 0) {
		// WriteResult(response, 0, "增加失败！");
		// } else {
		// WriteResult(response, 1, "增加成功！");
		// }
	}

	/// <summary>
	/// 显示商品收藏列表
	/// </summary>
	protected void ListOmcollect(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Omcollect dal = new Omcollect();
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		int mustbj = jsonObject.has("mustbj") ? Integer.parseInt(jsonObject.getString("mustbj")) : 0;
		int tjtag = jsonObject.has("tjtag") ? Integer.parseInt(jsonObject.getString("tjtag")) : 0;

		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "a.wareid,a.wareno,a.warename,a.units,a.retailsale,a.imagename,a.tjtag,c.mustbj";
		fieldlist += ",f_getsumcustomorder(" + htp.getOmid() + ",a.wareid," + htp.getUserid() + ") as ordamt";
		String sort = " wareno ,wareid ";

		String strwhere = " b.omid=" + htp.getOmid() + " and b.omepid=" + htp.getUserid();
		strwhere += " and a.accid=" + htp.getMaccid() + " and a.statetag=1 and c.noused=0";
		if (mustbj == 1)
			strwhere += " and c.mustbj=1";
		if (tjtag == 1)
			strwhere += " and a.tjtag=1";
		// strwhere += " and exists (select 1 from omwarecode c where
		// b.omid=c.omid and a.wareid=c.wareid and c.noused=0) ";
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 显示订货会客户用户列表(app调用)
	protected void ListCustOmuser(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// Omuser dal = new Omuser();
		// String fieldlist = jsonObject.has("fieldlist") ?
		// jsonObject.getString("fieldlist") : "*";

		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : "omepid";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : " asc ";
		int fs = jsonObject.has("fs") ? Integer.parseInt(jsonObject.getString("fs")) : 2;
		// fs:0=未产生订单用户，1=已产生订单用户，2=所有用户

		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		// if (fieldlist.equals("*") || fieldlist.equals(""))
		String qry = "select a.omepid,a.omepno,a.omepname,a.remark,a.js"; // ,a.js,a.mobile,a.password,a.statetag
		// qry += ",nvl(b.jhamt,0) as jhamt,nvl(b.jhcurr,0) as
		// jhcurr,nvl(b.jhskc,0) as jhskc";
		qry += "\n from omuser a join omcustomer b on a.custid=b.custid and b.omid=" + htp.getOmid();
		// qry += "\n left outer join omplan b on a.omepid=b.omepid and b.omid="
		// + htp.omid;
		qry += "\n where a.custid=" + htp.getCustid() + " and a.statetag=1"; // statetag:0=禁止登录1=允许登录2=删除,3=截屏锁定
		qry += "\n and a.accid=" + htp.getMaccid();
		if (fs == 0)
			qry += " and not exists (select 1 from omorder c where a.omepid=c.omepid and b.omid=c.omid)";
		else if (fs == 1)
			qry += " and exists (select 1 from omorder c where a.omepid=c.omepid and b.omid=c.omid)";

		// qry += "\n and exists (select 1 from omcustomer b where
		// a.custid=b.custid and b.omid=" + htp.omid + ")";
		if (!findbox.equals(""))
			qry += " and a.omepname like '%" + findbox + "%'";
		sort += " " + order + " ";

		QueryParam qp = new QueryParam(page, pagesize, sort, qry);
		Table tb = new Table();

		tb = DbHelperSQL.GetTable(qp);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	/// 修改订货会用户（订货会app调用）
	/// </summary>
	protected void UpdateOmuser1(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Omuser dal = new Omuser();

		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(htp.getMaccid());
		if (dal.Update() == 0) {
			WriteResult(response, 0, "修改失败！");
		} else {
			WriteResult(response, 1, "修改成功！");
		}

	}

	// 清空订货会商品
	protected void ClearOmcustware(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : 0;
		Omcustware dal = new Omcustware();
		// dal.setUserid(htp.getUserid());
		dal.setOmepid(htp.getUserid());
		dal.setOmid(omid);
		WriteResult(response, dal.Clearall(), dal.getErrmess());
	}

	/// <summary>
	/// 保存订货会的订货商品
	/// </summary>
	protected void WriteOmcustware(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Omcustware dal = new Omcustware();
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		// long omid = jsonObject.has("omid") ?
		// Long.parseLong(jsonObject.getString("omid")) : -1;

		dal.setWareid(wareid);
		dal.setOmid(htp.getOmid());
		// if (omid == 0)
		// dal.setOmid(omid);
		dal.setLastop(htp.getUsername());
		if (dal.Update(htp.getUserid(), jsonObject) == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResult(response, 1, "修改成功！");
		}

	}

	/// 将单据传入后台打印服务
	/// </summary>
	protected void AddBackprint(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Backprint dal = new Backprint();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		int ret = dal.Append(htp.getUserid());

		WriteResult(response, ret, dal.getErrmess());
		// if (dal.Append() == 0) {
		// WriteResult(response, 0, "失败！");
		// } else {
		// WriteResult(response, 1, "修改成功！");
		// }

	}

	/// <summary>
	/// 获取指定商品条码信息(订货会)
	/// </summary>

	protected void GetWarebarcodeByNo1(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String barcode = jsonObject.has("barcode") ? jsonObject.getString("barcode") : "";
		if (barcode.equals("")) {
			WriteResult(response, 0, "请输入条码！");
			return;

		}
		// System.out.println("GetWarebarcodeByNo1 1 " + barcode);
		Warebarcode dal = new Warebarcode();
		dal.setBarcode(barcode);

		String strwhere = "  b.accid=" + htp.getMaccid() + " and a.barcode='" + barcode + "'";

		String fieldlist = "A.BARCODE,B.WAREID,B.WARENO,B.WARENAME,B.UNITS,A.COLORID,C.COLORNAME,A.SIZEID,D.SIZENAME,B.ENTERSALE,B.RETAILSALE";

		Table tb = new Table();
		tb = dal.GetList(strwhere, fieldlist, htp.getOmid()).getTable(1);
		// System.out.println("GetWarebarcodeByNo1 2");
		if (tb.getRowCount() == 0) {

			WriteResult(response, 0, "未找到条码或非本次订货商品！");
			return;
		}

		String retcs = "\"msg\":\"操作成功！\",\"WAREID\":\"" + tb.getRow(0).get("WAREID").toString() + "\"" //
				+ ",\"WARENO\":\"" + tb.getRow(0).get("WARENO").toString() + "\"" //
				+ ",\"WARENAME\":\"" + tb.getRow(0).get("WARENAME").toString() + "\"" //
				+ ",\"UNITS\":\"" + tb.getRow(0).get("UNITS").toString() + "\""//
				+ ",\"COLORID\":\"" + tb.getRow(0).get("COLORID").toString() + "\"" //
				+ ",\"COLORNAME\":\"" + tb.getRow(0).get("COLORNAME").toString() + "\"" //
				+ ",\"SIZEID\":\"" + tb.getRow(0).get("SIZEID").toString() + "\"" //
				+ ",\"SIZENAME\":\"" + tb.getRow(0).get("SIZENAME").toString() + "\"" //
				+ ",\"RETAILSALE\":\"" + tb.getRow(0).get("RETAILSALE").toString() + "\"";
		WriteResultJson(response, 1, retcs);

	}

	// 获取订货会商品类型，波段，价格范围等信息
	protected void GetOminfo(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		// JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Omsubject dal = new Omsubject();
		dal.setOmid(htp.getOmid());
		dal.setAccid(htp.getMaccid());
		WriteResultJson(response, 1, dal.getOminfo());

	}

	// 生成订单
	protected void OmcustwareToorder(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Omorder dal = new Omorder();
		String omepidlist = jsonObject.has("omepidlist") ? jsonObject.getString("omepidlist") : "";
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		dal.setOmid(htp.getOmid());
		if (dal.Append(omepidlist, htp.getCustid()) == 1) {
			WriteResult(response, 1, dal.getErrmess());
		} else {
			WriteResult(response, 0, dal.getErrmess());
		}
	}

	// 微信小程序生成客户订单
	protected void WxcustwareToorder(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		// JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Omorder dal = new Omorder();
		// String omepidlist = jsonObject.has("omepidlist") ?
		// jsonObject.getString("omepidlist") : "";
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		dal.setOmepid(htp.getUserid());
		dal.setOmid((long) 0);
		if (dal.Append(htp.getCustid()) == 1) {
			WriteResult(response, 1, dal.getErrmess());
		} else {
			WriteResult(response, 0, dal.getErrmess());
		}
	}

	/// <summary>
	/// 删除订货会订单
	/// </summary>
	protected void DelOmorder(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Omorder dal = new Omorder();
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";

		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		dal.setNoteno(noteno);
		if (dal.Delete() == 1) {
			WriteResult(response, 1, "删除成功！");
		} else {
			WriteResult(response, 0, dal.getErrmess());
		}

	}

	// 订货会商品取消客户订单
	protected void OmcustwareCancelorder(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Omcustomer dal = new Omcustomer();
		dal.setCustid(htp.getCustid());
		dal.setOmid(htp.getOmid());
		dal.setLastop(htp.getUsername());
		dal.CancelOrder(htp.getMaccid());

	}

	// 返回商品的资料，尺码，颜色及相关搭配（订货会用）
	protected void GetOmwareinfo(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		String omepidlist = jsonObject.has("omepidlist") ? jsonObject.getString("omepidlist") : "";
		if (omepidlist.equals(""))
			omepidlist = "" + htp.getUserid();

		Omwarecode dal = new Omwarecode();
		dal.setAccid(htp.getMaccid());
		dal.setUserid(htp.getUserid());
		dal.setOmid(htp.getOmid());
		if (dal.getOmwareinfo(wareid, omepidlist) == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());
		}
	}
	// 微信小程序 显示订货的商品明细(颜色尺码以二维表方式展开)

	protected void GetOmcustwarebyid(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;

		Omcustware dal = new Omcustware();

		dal.setAccid(htp.getMaccid());
		dal.setOmepid(htp.getUserid());

		dal.setOmid((long) 0);
		dal.setWareid(wareid);
		if (dal.listOmcustwaremx() == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());
		}

	}

	// 显示订货的商品明细(颜色尺码以二维表方式展开)
	protected void ListOmcustwaremx(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		// long omepid = jsonObject.has("omepid") ?
		// Long.parseLong(jsonObject.getString("omepid")) : 0;
		String omepidlist = jsonObject.has("omepidlist") ? jsonObject.getString("omepidlist") : "";

		Omcustware dal = new Omcustware();

		// System.out.println("omepidlist1=" + omepidlist);
		// if (omepid == 0)
		// omepid = htp.getUserid();
		if (omepidlist == null || omepidlist.equals(""))
			omepidlist = "^" + htp.getUserid() + "^";
		// System.out.println("omepidlist2=" + omepidlist);
		dal.setAccid(htp.getMaccid());
		dal.setOmepid(htp.getUserid());
		dal.setOmid(htp.getOmid());
		dal.setWareid(wareid);
		if (dal.listOmcustwaremx(omepidlist) == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());
		}

	}

	// 显示订货会用户订货汇总
	protected void ListOmuserOrderSum(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		int fs = jsonObject.has("fs") ? Integer.parseInt(jsonObject.getString("fs")) : 1;
		// fs:0=未产生订单用户，1=已产生订单用户，2=所有用户
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;

		// int rs = 0;
		String sort = " omepname,omepid";
		Omuser dal = new Omuser();
		dal.setAccid(htp.getMaccid());

		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		tb = dal.listOmuserOrderSum(qp, htp.getOmid(), htp.getCustid(), fs, findbox);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 显示指定用户的订货商品汇总(订货会app用) 未生成订单
	protected void ListOmcustwarehz(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		// String fieldlist = jsonObject.has("fieldlist") ?
		// jsonObject.getString("fieldlist") : "*";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : "asc";
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " wareno,wareid";
		long omepid = jsonObject.has("omepid") ? Long.parseLong(jsonObject.getString("omepid")) : 0;

		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		sort += " " + order + ",wareid ";
		// Omcustware dal = new Omcustware();
		// QueryParam qp = new QueryParam();
		// String strwhere=" a.omid=" + htp.getOmid() + " and a.omepid=" +
		// htp.getUserid();
		String qry = "select a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.imagename,b.tjtag,c.mustbj";
		qry += ",sum(a.amount) as amount,sum(a.amount*b.retailsale) as retailcurr";
		qry += "\n from omcustware a join warecode b on a.wareid=b.wareid";
		qry += "\n join omwarecode c on a.omid=c.omid and a.wareid=c.wareid";

		qry += "\n where a.omid=" + htp.getOmid() + " and a.omepid=" + omepid + " and c.noused=0";
		// 禁用的商品不显示
		// qry += "\n and exists (select 1 from omwarecode c where
		// a.wareid=c.wareid and a.omid=c.omid and c.noused=0)";

		if (!findbox.equals(""))
			qry += "\n  and (b.warename like '%" + findbox + "%' or b.wareno like '%" + findbox.toUpperCase() + "%')";
		qry += "\n group by a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.imagename,b.tjtag,c.mustbj";
		// System.out.println(qry);
		QueryParam qp = new QueryParam(page, pagesize, sort, qry);
		// string s1 = rs + "^" + totalamt + "^" + totalcurr + "^" + totalnum +
		// "^";
		qp.setSumString("nvl(sum(amount),0) as totalamt,nvl(sum(retailcurr),0) as totalcurr,count(distinct wareid) as totalnum");
		// System.out.println("1111");

		Table tb = new Table();
		tb = DbHelperSQL.GetTable(qp);// dal.GetTable(qp);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 显示订货会用户订货的商品汇总(已生成订单)
	protected void ListOmcustorderhz(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		long omid = htp.getOmid();
		String sort = "wareno,wareid";
		String qry = "select a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.imagename,b.tjtag,c.mustbj";
		qry += ",sum(a.amount) as amount,sum(a.amount*b.retailsale) as retailcurr,sum(a.curr) as curr";
		qry += ",case sum(a.amount*b.retailsale) when 0 then 0 else round(sum(a.curr)/sum(a.amount*b.retailsale),2) end as discount";
		qry += "\n from custorderm a ";
		qry += "\n join warecode b on a.wareid=b.wareid";
		qry += "\n join omwarecode c on c.omid=" + omid + " and a.wareid=c.wareid";
		qry += "\n where a.accid=" + htp.getMaccid() + " and a.noteno='" + noteno + "'";
		// 禁用的商品不显示
		qry += "\n and exists (select 1 from omorder d where a.accid=d.accid and a.noteno=d.noteno and d.omid=" + omid + ")";

		if (!findbox.equals(""))
			qry += " and (b.wareno like '%" + findbox.toUpperCase() + "%' or b.warename like '%" + findbox + "%')";
		qry += "\n group by a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.imagename,b.tjtag,c.mustbj";
		Custorderm dal = new Custorderm();
		QueryParam qp = new QueryParam(page, pagesize, sort, qry);
		// string s1 = rs + "^" + totalamt + "^" + totalcurr + "^" +
		// totalretailcurr + "^" + totalnum + "^";

		qp.setSumString("nvl(sum(amount),0) as totalamt,nvl(sum(retailcurr),0) as totalretailcurr,nvl(sum(curr),0) as totalcurr,count(distinct wareid) as totalnum");
		Table tb = new Table();
		tb = dal.GetTable(qp);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 显示订货会订单记录表
	protected void OmOrderList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : "asc";
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : "notedate";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		sort += " " + order + ",id ";

		if (fieldlist.equals("") || fieldlist.equals("*"))
			fieldlist = "a.id,a.noteno,a.notedate,b.custname,a.totalamt,a.totalcurr,c.totalnum,a.operant,a.remark,a.statetag,c.omepid";

		Omorder dal = new Omorder();
		dal.setAccid(htp.getMaccid());
		dal.setOmid(htp.getOmid());
		// dal.setUserid(htp.getUserid());
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		// System.out.println(htp.getCustid());

		tb = dal.listOmOrderList(qp, htp.getCustid(), fieldlist, findbox);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 显示订货商品排行榜(订货会app用)
	protected void ListOmcustwaresort(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// String omepidlist = jsonObject.has("omepidlist") ?
		// jsonObject.getString("omepidlist") : "";
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String sort = " tamount desc,wareid";

		Omcustware dal = new Omcustware();
		dal.setAccid(htp.getMaccid());
		dal.setOmid(htp.getOmid());
		dal.setOmepid(htp.getUserid());

		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = dal.listOmcustwaresort(qp, findbox);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 供应商分页列表
	protected void ProvideList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		// System.out.println("ProvideList 1 maccid=" + htp.maccid);
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// {"flyang":"20150107","rows":10,"page":1,"fieldlist":"Provideid,Providename","sort":"Provideid","order":"asc"}
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		int noused = jsonObject.has("noused") ? Integer.parseInt(jsonObject.getString("noused")) : 0;
		String linkman = jsonObject.has("linkman") ? jsonObject.getString("linkman") : "";
		String mobile = jsonObject.has("mobile") ? jsonObject.getString("mobile") : "";
		String remark = jsonObject.has("remark") ? jsonObject.getString("remark") : "";

		String provname = jsonObject.has("provname") ? jsonObject.getString("provname") : "";
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String sort = " provname,provid ";
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "PROVID,PROVNAME,SHORTNAME,ACCID,ADDRESS,POSTCODE,TEL,LINKMAN,MOBILE,BANKNAME,TAXNO,ACCOUNTNO,REMARK,NOUSED,STATETAG,PRICETYPE,DISCOUNT,LASTOP,LASTDATE";
		String strwhere = "accid=" + htp.getMaccid() + " and statetag=1";
		if (noused < 2)
			strwhere += " and NOUSED=" + noused;

		if (findbox.length() > 0)
			strwhere += " and (provname like '%" + findbox + "%' or shortname like '%" + findbox.toUpperCase() + "%')";
		if (provname.length() > 0)
			strwhere += " and provname like '%" + provname + "%'";

		if (linkman.length() > 0)
			strwhere += " and linkman like '%" + linkman + "%'";
		if (mobile.length() > 0)
			strwhere += " and mobile like '%" + mobile + "%'";
		if (remark.length() > 0)
			strwhere += " and remark like '%" + remark + "%'";

		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		Provide dal = new Provide();

		tb = dal.GetTable(qp, strwhere, fieldlist);
		// if (page < 0) {// 导出excel
		// String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
		// WriteResult(response, Integer.parseInt(retcs.substring(0, 1)),
		// retcs.substring(1));
		// return;
		// }

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 订货会汇总分析
	protected void TotalOmcustware(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String omepidlist = jsonObject.has("omepidlist") ? jsonObject.getString("omepidlist") : "";
		int hzfs = jsonObject.has("hzfs") ? Integer.parseInt(jsonObject.getString("hzfs")) : 0;
		// System.out.println("TotalOmcustware");
		// if (omepidlist==null)//System.out.println(" opemidlist is null");
		// hzfs:0=类型，1=颜色，2=尺码，3=波段，4=价格
		// if (omepidlist==null || omepidlist.equals(""))
		// omepidlist="^"+htp.getUserid()+"^";
		Omcustware dal = new Omcustware();
		dal.setOmepid(htp.getUserid());
		dal.setAccid(htp.getMaccid());
		dal.setOmid(htp.getOmid());

		String jsonstr = dal.totalOmcustware(htp.getCustid(), omepidlist, hzfs, htp.getLevelid());
		WriteResultJson(response, 1, jsonstr);
	}

	// 显示客户订货的商品汇总列表
	protected void ListOmcustwaretotal(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = jsonObject.has("omid") ? Integer.parseInt(jsonObject.getString("omid")) : -1;

		String omepidlist = jsonObject.has("omepidlist") ? jsonObject.getString("omepidlist") : "";

		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " wareno ";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : " asc ";
		sort += " " + order;

		Omcustware dal = new Omcustware();
		dal.setOmepid(htp.getUserid());
		dal.setAccid(htp.getMaccid());
		if (omid < 0)
			omid = htp.getOmid();
		dal.setOmid(omid);

		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		// tb = dal.listOmcustwaretotal(qp, htp.getOmid(),
		// htp.getUserid(),omepidlist, findbox);
		tb = dal.listOmcustwaretotal(qp, htp.getLevelid(), htp.getCustid(), omepidlist, findbox);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 显示客户订货的商品汇总列表(微信小程序)
	protected void ListOmcustwaretotal1(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long omid = 0;// jsonObject.has("omid") ?
						// Integer.parseInt(jsonObject.getString("omid")) : -1;

		String omepidlist = jsonObject.has("omepidlist") ? jsonObject.getString("omepidlist") : "";

		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " wareno ";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : " asc ";
		sort += " " + order;

		Omcustware dal = new Omcustware();
		dal.setOmepid(htp.getUserid());
		dal.setAccid(htp.getMaccid());
		// if (omid < 0)
		// omid = htp.getOmid();
		dal.setOmid(omid);

		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		// tb = dal.listOmcustwaretotal(qp, htp.getOmid(),
		// htp.getUserid(),omepidlist, findbox);
		tb = dal.listOmcustwaretotal1(qp, htp.getLevelid(), htp.getCustid(), omepidlist, findbox);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 显示订货商品汇总(订货会app用)我的订单
	protected void ListOmcustwaresum(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long waveid = jsonObject.has("waveid") ? Long.parseLong(jsonObject.getString("waveid")) : 0;
		int mustbj = jsonObject.has("mustbj") ? Integer.parseInt(jsonObject.getString("mustbj")) : 0;
		int tjtag = jsonObject.has("tjtag") ? Integer.parseInt(jsonObject.getString("tjtag")) : 0;

		int ordbj = jsonObject.has("ordbj") ? Integer.parseInt(jsonObject.getString("ordbj")) : 2;
		// ordbj:0=未订货 1=已订货 2=所有
		float maxsale = jsonObject.has("maxsale") ? Float.parseFloat(jsonObject.getString("maxsale")) : -1;
		float minsale = jsonObject.has("minsale") ? Float.parseFloat(jsonObject.getString("minsale")) : -1;
		long poleid = jsonObject.has("poleid") ? Long.parseLong(jsonObject.getString("poleid")) : 0;
		long provid = jsonObject.has("provid") ? Long.parseLong(jsonObject.getString("provid")) : -1;
		long brandid = jsonObject.has("brandid") ? Long.parseLong(jsonObject.getString("brandid")) : -1;
		String wareno = jsonObject.has("wareno") ? jsonObject.getString("wareno") : "";
		String typelist = jsonObject.has("typelist") ? jsonObject.getString("typelist") : "";
		String salerange = jsonObject.has("salerange") ? jsonObject.getString("salerange") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " wareno ";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : " asc ";
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";

		sort += " " + order + ",wareid";

		Omwarecode dal = new Omwarecode();

		dal.setAccid(htp.getMaccid());
		dal.setUserid(htp.getUserid());
		dal.setOmid(htp.getOmid());
		Warecode wareinfo = new Warecode();
		wareinfo.setWaveid(waveid);
		wareinfo.setMaxsale(maxsale);
		wareinfo.setMinsale(minsale);
		wareinfo.setWareno(wareno);
		wareinfo.setProvid(provid);
		wareinfo.setBrandid(brandid);

		QueryParam qp = new QueryParam(page, pagesize, sort);
		Table tb = new Table();
		tb = dal.listOmcustwaresum(qp, poleid, ordbj, mustbj, tjtag, wareinfo, salerange, typelist, findbox);
		// System.out.println("aaa" + qp.getTotalString());

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 品牌分页列表
	protected void BrandList(HttpServletResponse response, HttpInfo htp, int fs) throws ServletException, IOException {
		// System.out.println("BrandList 1 maccid=" + htp.maccid);
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// {"flyang":"20150107","rows":10,"page":1,"fieldlist":"brandid,brandname","sort":"brandid","order":"asc"}
		// int xls = jsonObject.has("xls") ?
		// Integer.parseInt(jsonObject.getString("xls")) : 0;

		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		// int noused = jsonObject.has("noused") ?
		// Integer.parseInt(jsonObject.getString("noused")) : 0;
		// int downbj = jsonObject.has("downbj") ?
		// Integer.parseInt(jsonObject.getString("downbj")) : 0;
		// long brandid = jsonObject.getString("brandid") == null ? 0 :
		// Long.parseLong(jsonObject.getString("brandid"));
		// String sort = jsonObject.has("sort") ? jsonObject.getString("sort") :
		// " brandid ";
		// String order = jsonObject.has("order") ?
		// jsonObject.getString("order") : " asc ";
		String brandname = jsonObject.has("brandname") ? jsonObject.getString("brandname") : "";
		// String lastdate = jsonObject.has("lastdate") ?
		// jsonObject.getString("lastdate") : "";
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String sort = " BRANDNAME,BRANDID ";
		// System.out.println("brandid=" + brandid);
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "BRANDID,BRANDNAME,ACCID,LYFS,ACCID1,BRANDID1,NOUSED,STATETAG,LASTOP,LASTDATE";
		long accid = htp.getMaccid();
		// long userid = htp.getUserid();
		// String qxpublic = htp.getQxpublic();
		String strwhere = "";
		// if (downbj == 0) {
		// if (fs == 1)
		// strwhere = " (accid=" + accid + " or brandid=0)";
		// else
		// strwhere = " accid=" + accid;
		// strwhere += " and STATETAG=1";
		// } else // 同步
		// {
		strwhere = " (accid=" + accid + " or brandid=0)";
		strwhere += " and STATETAG=1 and noused=0";

		// }
		// if (noused < 2)
		// strwhere += " and NOUSED=" + noused;
		// if (!lastdate.equals(""))
		// strwhere += " and lastdate>to_date('" + lastdate +
		// "','yyyymmddhh24miss')";

		// if (ywly < 2) qry += " and ywly=" + ywly;//ywly=0
		// 只查询手机端增加的单据，用于向erp传数据

		if (!findbox.equals(""))
			strwhere += " and (BRANDNAME like '%" + findbox + "%' or shortname like '%" + findbox.toUpperCase() + "%')";
		if (!brandname.equals(""))
			strwhere += " and (BRANDNAME like '%" + brandname + "%' or shortname like '%" + brandname.toUpperCase() + "%')";

		// if (htp.getQxbj() == 1 ) // 1 启用权限控制
		// if (fs == 1 && userid != 0)
		// strwhere += " and exists (select 1 from employebrand where
		// brand.brandid=employebrand.brandid and employebrand.epid=" + userid +
		// ") ";

		Table tb = new Table();
		Brand dal = new Brand();
		QueryParam qp = new QueryParam(page, pagesize, sort);

		tb = dal.GetTable(qp, strwhere, fieldlist);

		// if (page < 0) {// 导出excel
		// String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
		// WriteResult(response, Integer.parseInt(retcs.substring(0, 1)),
		// retcs.substring(1));
		// return;
		// }

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 订货会截图加锁
	protected void OrderLock(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		// JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		Omuser dal = new Omuser();
		dal.setAccid(htp.getMaccid());
		dal.setOmepid(htp.getUserid());
		dal.setStatetag(3); // statetag:0=禁止登录1=允许登录2=删除,3=截屏锁定
		if (dal.Update() == 1) {
			WriteResult(response, 1, "加锁成功！");
		} else {
			WriteResult(response, 0, dal.getErrmess());
		}
	}

	// // 显示商城店铺订货商品排行榜
	// protected void ListCityorderwaresort(HttpServletResponse response,
	// HttpInfo htp) throws ServletException, IOException {
	// JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
	// long cthouseid = jsonObject.has("cthouseid") ?
	// Long.parseLong(jsonObject.getString("cthouseid")) : 0;
	// String findbox = jsonObject.has("findbox") ?
	// jsonObject.getString("findbox").trim().replace("'", "''") : "";
	// int page = jsonObject.has("page") ?
	// Integer.parseInt(jsonObject.getString("page")) : 1;
	// int pagesize = jsonObject.has("rows") ?
	// Integer.parseInt(jsonObject.getString("rows")) : 10;
	// if (pagesize > 50)
	// pagesize = 10;
	// long buyaccid = htp.getMaccid();
	// String qry = " select
	// a.wareid,b.wareno,b.warename,b.units,a.amount,a.curr,a.tamount,b.imagename0
	// from (";
	// qry += "\n select wareid,sum(amount) as amount,sum(curr) as
	// curr,sum(tamount) as tamount from (";
	// qry += "\n select a.wareid,sum(a.amount) as amount,sum(a.curr) as curr,0
	// as tamount ";
	// qry += "\n from cityorder a ";
	// qry += "\n join warecode b on a.wareid=b.wareid ";
	// qry += "\n where a.cthouseid=" + cthouseid + " and a.ordaccid=" +
	// buyaccid + " and a.amount>0";
	// if (!findbox.equals(""))
	// qry += "\n and (b.warename like '%" + findbox + "%' or b.wareno like '%"
	// + findbox.toUpperCase() + "%')";
	// qry += "\n group by a.wareid ";
	// qry += "\n union all ";
	// // 所有订货
	// qry += "\n select b.wareid,0 as amount,0 as curr,sum(b.amount) as tamount
	// ";
	// qry += "\n from custorderh a join custorderm b on a.accid=b.accid and
	// a.noteno=b.noteno";
	// qry += "\n join warecode c on b.wareid=c.wareid ";
	// qry += "\n where a.statetag=1 and b.amount>0";
	// qry += "\n and a.NOTEDATE >= to_date('" + htp.getAccdate() + "
	// 00:00:00','yyyy-mm-dd hh24:mi:ss')";
	// if (!findbox.equals(""))
	// qry += "\n and (c.warename like '%" + findbox + "%' or c.wareno like '%"
	// + findbox.toUpperCase() + "%')";
	// // qry += " and exists (select 1 from cityorder a1 where
	// a1.wareid=b.wareid and a1.cthouseid=" + cthouseid + " and a1.ordaccid=" +
	// accid + ")";
	// qry += "\n and exists (select 1 from CITYHOUSEWARE a1 where
	// a1.wareid=b.wareid and a1.cthouseid=" + cthouseid + " and
	// a1.onlinetag=1)";
	// qry += "\n group by b.wareid ) ";
	// qry += "\n group by wareid having sum(amount)>0 ";
	// qry += "\n ) a join warecode b on a.wareid=b.wareid ";
	//
	// //System.out.println(qry);
	// String sort = " tamount desc,wareid";
	// QueryParam qp = new QueryParam(page, pagesize, sort, qry);
	//
	// qp.setSumString("nvl(sum(amount),0) as totalamt,nvl(sum(curr),0) as
	// totalcurr");
	//
	// Cityorder dal = new Cityorder();
	//
	// Table tb = new Table();
	// tb = dal.GetTable(qp);
	//
	// Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	// }

	// 获取指定客户订单记录id信息
	protected void GetCustorderhByID(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";// 调拨出库单号
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";// 调拨出库单号
		String strwhere = " a.NOTENO='" + noteno + "' and a.ACCID=" + htp.getMaccid();
		if (fieldlist.equals("*") || fieldlist.length() <= 0)
			fieldlist = "a.ID,a.NOTENO,a.ACCID,a.NOTEDATE,a.CUSTID,a.REMARK,a.HANDNO,a.OPERANT,a.CHECKMAN,a.STATETAG,a.LASTDATE,b.CUSTNAME";
		Table tb = new Table();
		Custorderh dal = new Custorderh();
		tb = dal.GetList(strwhere, fieldlist).getTable(1);

		Write(response, DbHelperSQL.DataTable2Json(tb));
	}

	// 客户订单撤单（微信小程序）
	protected void CustorderhCancel(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";

		// int changedatebj = htp.getChangedatebj();

		Custorderh dal = new Custorderh();
		dal.setAccid(htp.getMaccid());
		dal.setOperant(htp.getUsername());
		dal.setNoteno(noteno);
		dal.setEpid(htp.getUserid());

		WriteResult(response, dal.Cancel1(), dal.getErrmess());
	}

	// 获取客户订货商品+颜色汇总列表
	protected void GetCustordermcolorsumList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		int sortid = jsonObject.has("sortid") ? Integer.parseInt(jsonObject.getString("sortid")) : 0;
		int sizenum = jsonObject.has("sizenum") ? Integer.parseInt(jsonObject.getString("sizenum")) : 0;
		// if (sizenum == 0)
		// sizenum = 8;
		long accid0 = jsonObject.has("accid") ? Long.parseLong(jsonObject.getString("accid")) : 0;
		long accid = htp.getMaccid();
		if (accid0 > 0)
			accid = accid0;
		int priceprec = htp.getPriceprec();// Integer.parseInt(Func.subString(qxpublic,
											// 4, 1));
		String sort = "";
		if (sortid == 1)
			sort = " id " + order;
		else
			sort = "  wareno " + order + ",wareid,colorid,id ";// + order;
		QueryParam qp = new QueryParam(page, pagesize, sort);
		Custorderm dal = new Custorderm();
		dal.setAccid(accid);
		dal.setNoteno(noteno);
		// dal.setLastop(htp.getUsername());
		if (dal.doGetColorsumList(qp, sizenum, priceprec) == 0)
			WriteResult(response, 0, dal.getErrmess());
		else
			Write(response, dal.getErrmess());
	}

	// 获取客户订单记录列表
	protected void GetCustorderhList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long custid = jsonObject.has("custid") ? Long.parseLong(jsonObject.getString("custid")) : 0;
		// long wareid = jsonObject.has("wareid") ?
		// Long.parseLong(jsonObject.getString("wareid")) : 0;
		// String wareno = jsonObject.has("wareno") ?
		// jsonObject.getString("wareno") : "";
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		String handno = jsonObject.has("handno") ? jsonObject.getString("handno") : "";
		// String custname = jsonObject.has("custname") ?
		// jsonObject.getString("custname") : "";

		// String currdate = htp.getNowdate();//
		// Func.DateToStr(pFunc.getServerdatetime(), "yyyy-MM-dd");
		String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : "";
		String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : "";
		String operant = jsonObject.has("operant") ? jsonObject.getString("operant") : "";

		String remark = jsonObject.has("remark") ? jsonObject.getString("remark") : "";
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : "desc";
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : "notedate";
		sort += " " + order + ",id ";// +order;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		// int ywly = jsonObject.has("ywly") ?
		// Integer.parseInt(jsonObject.getString("ywly")) : 2;
		int statetag = jsonObject.has("statetag") ? Integer.parseInt(jsonObject.getString("statetag")) : 2;
		// int ywly = jsonObject.has("ywly") ?
		// Integer.parseInt(jsonObject.getString("ywly")) : 2;
		String lastop = htp.getUsername();
		String begindate = htp.getAccdate();
		// int maxday = htp.getMaxday();
		// // 校验开始查询日期
		// MinDateValid mdv = new MinDateValid(mindate, currdate,
		// htp.getAccdate(), maxday);
		// mindate = mdv.getMindate();
		// // String mess = mdv.getErrmess();
		// if (mindate.compareTo(maxdate) > 0)
		// maxdate = mindate; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期
		// ==========================
		// String sort = " notedate desc,id ";// +order;
		String strwhere = " a.ACCID=" + htp.getMaccid();

		if (!Func.isNull(findbox))
			strwhere += " and (a.noteno like '%" + findbox.toUpperCase() + "%' or a.remark like '%" + findbox + "%' or a.operant like '%" //
					+ findbox + "%' or b.custname like '%" + findbox + "%' or b.shortname like '%" + findbox + "%' )";

		// if (fs == 1) // 调拨出库载入调拨订单
		// {
		// strwhere += " and a.STATETAG=1 ";
		// strwhere += " and not exists (select 1 from allotouth a1 where
		// a.accid=a1.accid and a.noteno=a1.orderno and a1.statetag<=1) "; //
		// 已经对应调入单的单据不再显示
		// } else {
		// if (htp.getLevelid() == 0) // 系统管理员允许看所有用户的单据
		// {
		// if (statetag == 0)
		// strwhere += " and a.statetag=0 "; // 未提 交
		// else if (statetag == 1)
		// strwhere += " and a.statetag=1"; // 已提 交
		// else
		// strwhere += " and (a.STATETAG=1 or a.STATETAG=0)"; // 所有
		// } else {
		if (statetag == 0)
			strwhere += " and a.statetag=0 and a.operant='" + lastop + "'"; // 未提
		// 交
		else if (statetag == 1)
			strwhere += " and a.statetag=1"; // 已提 交
		else
			strwhere += " and (a.STATETAG=1 or a.STATETAG=0 and a.operant='" + lastop + "')"; // 所有
		// }
		// if (ywly < 2)
		// strwhere += " and a.ywly=" + ywly;// ywly=0 只查询手机端增加的单据，用于向erp传数据
		if (begindate.length() > 0)
			strwhere += " and a.NOTEDATE >= to_date('" + begindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";

		if (mindate.length() > 0)
			strwhere += " and a.NOTEDATE >= to_date('" + mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		if (maxdate.length() > 0)
			strwhere += " and a.NOTEDATE <= to_date('" + maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		// }

		if (custid > 0)
			strwhere += " and a.custid = " + custid;
		// else if (custname.length() > 0)
		// strwhere += " and (b.custname like '%" + custname + "%' or
		// b.shortname like '%" + custname.toUpperCase() + "%' )";
		// if (fromhouseid != "") qry += " and a.fromhouseid = " + fromhouseid;
		if (handno.length() > 0)
			strwhere += " and a.HANDNO like '%" + handno + "%'";
		if (operant.length() > 0)
			strwhere += " and a.operant like '%" + operant + "%'";
		if (noteno.length() > 0)
			strwhere += " and a.NOTENO like '%" + noteno.toUpperCase() + "%'";
		if (remark != null && remark.length() > 0)
			strwhere += " and a.remark like '%" + remark + "%'";

		// if (wareid != 0) {
		// strwhere += " and exists (select 1 from custorderm x where
		// a.accid=x.accid and a.noteno=x.noteno and x.wareid=" + wareid + ")";
		// } else if (wareno.length() > 0) {
		// strwhere += " and exists (select 1 from custorderm x,warecode y where
		// x.wareid=y.wareid and a.accid=x.accid and a.noteno=x.noteno and
		// y.wareno like '%" + wareno + "%')";
		// }

		// WriteResult("0", qry);
		if (fieldlist.equals("*") || fieldlist.length() <= 0)
			fieldlist = "a.ID,a.NOTENO,a.ACCID,a.NOTEDATE,a.CUSTID,a.REMARK,a.HANDNO,a.OPERANT,a.CHECKMAN,a.STATETAG,a.LASTDATE,b.CUSTNAME,a.totalamt,a.totalcurr";

		Custorderh dal = new Custorderh();

		QueryParam qp = new QueryParam(page, pagesize, sort);
		qp.setSumString("nvl(sum(totalamt),0) as totalamount,nvl(sum(totalcurr),0) as totalcurr");
		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere, fieldlist);
		if (page < 0) {// 导出excel
			String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;
		}
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 获取采购商城店铺列表
	protected void GetCityHousebuyList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long cthouseid = jsonObject.has("cthouseid") ?
		// Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		// String fieldlist = jsonObject.has("fieldlist") ?
		// jsonObject.getString("fieldlist") : "*";
		// int jmbj = jsonObject.has("jmbj") ?
		// Integer.parseInt(jsonObject.getString("jmbj")) : 2;
		// jmbj:0=未加盟，1=已加盟，2=所有 ,3=我的商城
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		long accid = htp.getMaccid();

		String fieldlist = " a.accid,a.cthouseid,a.cthousename,a.address,a.tel,a.remark,a.lastdate,f_accidisbuyer(" + accid + ",accid) as jxstag ";
		String strwhere = " a.statetag=1";
		// opentag:1=允许非加盟商看查
		// jmbj:0=未加盟，1=已加盟，2=所有 ,3=我的商城
		// if (jmbj == 3) // 查看自已的商城
		// {
		strwhere += " and a.accid=" + accid;
		// } else {
		// strwhere += " and a.accid<>" + accid;
		// if (jmbj == 0) // 未加盟的商家:对非加盟商开放且未加盟
		// {
		// strwhere += " and a.opentag=1 and not exists (select 1 from
		// accconnect b where a.accid=b.sellaccid and b.buyaccid=" + accid + ")
		// ";
		// } else if (jmbj == 1) // 已加盟商家
		// {
		// strwhere += " and exists (select 1 from accconnect b where
		// a.accid=b.sellaccid and b.buyaccid=" + accid + ") ";
		// } else {
		// strwhere += " and (a.opentag=1 or a.opentag=0 and exists (select 1
		// from accconnect b where a.accid=b.sellaccid and b.buyaccid=" + accid
		// + ") )";
		// }
		// }

		if (!findbox.equals(""))

			strwhere += " and a.cthousename like '%" + findbox + "%'";

		strwhere += " and exists (select 1 from cityhouseware b ";
		strwhere += "  join warecode c on b.wareid=c.wareid  ";
		strwhere += "  where a.cthouseid=b.cthouseid and b.onlinetag=1 and length(c.imagename0)>0 "; // 要有商品的商城店铺才显示
		strwhere += ") ";

		String sort = " lastdate  desc";

		Cityhouse dal = new Cityhouse();
		// dal.setAccid(accid);

		QueryParam qp = new QueryParam(page, pagesize, sort);

		// Table tb = new Table();

		String jsonstr = dal.GetTable2json(qp, strwhere, fieldlist);
		Write(response, jsonstr);
		// Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 获取商城订货商品颜色及尺码数量明细
	protected void GetCityorderbywareid(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		Cityhouseware dal = new Cityhouseware();
		dal.setCthouseid(cthouseid);
		dal.setWareid(wareid);
		if (dal.doGetCityorderware(htp.getMaccid()) == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());
		}
	}

	// 经销商获取采购商城店铺商品列表
	protected void GetCityHousebuywareList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		int newtag = jsonObject.has("newtag") ? Integer.parseInt(jsonObject.getString("newtag")) : 0;
		// newtag:0=商品，1=新品
		int jxstag = jsonObject.has("jxstag") ? Integer.parseInt(jsonObject.getString("jxstag")) : 0;
		// jxstag:0=未申请（申请加盟），1=已审核 （等待审核） ，2=已审但未建对应供应商（建供应商），3=已建对应的供应商(生成订单)
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;

		String fieldlist = " a.id,a.wareid,a.warename0,b.warename,b.wareno,a.newtag,a.citysale,b.retailsale,a.haveamt,a.lastdate,b.imagename0,d.tel,d.mobile,d.linkman ,a.ordid";// ";
		if (jxstag >= 2)
			fieldlist += ",0 as openday";
		else // qry += ",case a.newtag when 1 then floor(a.opendate-sysdate+1)
				// else 0 end as openday"; //如果是游客且是新品，计算允许天数
			fieldlist += ",case when a.newtag=1 or floor(a.opendate-sysdate+1)>0 then floor(a.opendate-sysdate+1) else 0 end as openday"; // 如果是新品，计算允许游客查看天数
		fieldlist += ",f_getcityhouseordamt(a.cthouseid," + htp.getMaccid() + ",a.wareid) as ordamt";

		String strwhere = " a.cthouseid=" + cthouseid + " and a.onlinetag=1 and a.newtag=" + newtag;
		if (!findbox.equals(""))
			strwhere += " and (a.warename0 like '%" + findbox + "%' or b.warename like '%" + findbox + "%' or b.shortname like '%" + findbox.toUpperCase() + "%' or b.wareno like '%" + findbox.toUpperCase() + "%')";

		String sort = " ordid,lastdate desc,id ";

		Cityhouseware dal = new Cityhouseware();
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();

		tb = dal.GetTable1(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 商城订货商品生成客户订单，微信小程序
	// protected void OmCustWareToOrder(HttpServletResponse response, HttpInfo
	// htp) throws ServletException, IOException {
	// JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
	// long custid = jsonObject.has("custid") ?
	// Long.parseLong(jsonObject.getString("custid")) : 0;
	//
	// int priceprec = htp.getPriceprec();
	// Omcustware dal = new Omcustware();
	// dal.setOmepid(htp.getUserid());
	// dal.setAccid(htp.getMaccid());
	// dal.setOmid((long) 0);
	//
	// dal.setLastop(htp.getUsername());
	// WriteResult(response, dal.doOmCustWareToOrder(custid, priceprec),
	// dal.getErrmess());
	//
	// }

	// 获取客户订单商品颜色尺码明细
	protected void GetCustordermsum(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		// long houseid = jsonObject.has("houseid") ?
		// Long.parseLong(jsonObject.getString("houseid")) : 0;
		long custid = jsonObject.has("custid") ? Long.parseLong(jsonObject.getString("custid")) : 0;
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		String wareno = jsonObject.has("wareno") ? jsonObject.getString("wareno") : "";
		int priceprec = htp.getPriceprec();// Integer.parseInt(Func.subString(qxpublic,
											// 4, 1));
		int nearsaleok = htp.getNearsale();// Integer.parseInt(Func.subString(qxpublic,
											// 3, 1)); // 未启用最近售价

		Custorderm dal = new Custorderm();

		dal.setUserid(htp.getUserid());
		dal.setNoteno(noteno);
		dal.setWareid(wareid);
		dal.setAccid(htp.getMaccid());
		if (dal.Load(custid, priceprec, nearsaleok, wareno) == 0)
			WriteResult(response, 0, dal.getErrmess());
		else
			WriteResultJson(response, 1, dal.getErrmess());
	}

	// 获取商城店铺的商品信息
	protected void GetCityhousewareById(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		Cityhouseware dal = new Cityhouseware();
		dal.setCthouseid(cthouseid);
		dal.setWareid(wareid);
		dal.setLastop(htp.getUsername());
		if (dal.doGetCityhouseware() == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());
		}
	}

	// 显示订货会商品陈列杆 (订货会app)
	protected void ListCustOmpole(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		// String findbox = jsonObject.has("findbox") ?
		// jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " poleid ";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : " asc ";
		sort += " " + order;
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "poleid,polename,imagename,warenum,lastdate";
		fieldlist += ",f_getsumcustompole(" + htp.getOmid() + " ,poleid ," + htp.getUserid() + "  ) as totalamt";
		String strwhere = " omid=" + htp.getOmid();

		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		Ompole dal = new Ompole();
		tb = dal.GetTable(qp, strwhere, fieldlist);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
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
