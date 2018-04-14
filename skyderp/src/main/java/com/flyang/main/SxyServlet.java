package com.flyang.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.comdot.data.Table;
import com.common.tool.Func;
import com.common.tool.LogUtils;
import com.oracle.bean.*;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.QueryParam;

import net.sf.json.JSONObject;

/**
 * 上下衣专用接口 SxyServlet
 */
@WebServlet("/sxy")
public class SxyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String filePath = "";
	private static long sxy_accid = 0;

	static {
		File file = new File(Func.getRootPath() + "options.properties");// 极光推送的配置文件

		FileInputStream is = null;
		Properties prop = new Properties();
		try {
			// System.out.println(Func.getRootPath() + " ---read sxy_accid");
			is = new FileInputStream(file);
			prop.load(is);
			sxy_accid = Long.parseLong(prop.getProperty("sxyaccid"));
			// System.out.println("sxy_accid="+sxy_accid);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			String errmess = "Err1047:" + e.getMessage();
			LogUtils.LogErrWrite("SxyServlet", errmess);
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SxyServlet() {
		super();
		// TODO Auto-generated constructor stub
		// filePath = getServletContext().getRealPath("/");
		filePath = Func.initRootPath();// +"skyderp.config/";
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
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");

		String url = request.getRequestURL() + "?" + request.getQueryString();
		String action = request.getParameter("action").toLowerCase();
		// filePath=Func.getRootPath();
		if (action.equals("path")) {
			response.getWriter().write(filePath + "   ");
			return;
		}

		// filePath = getServletContext().getRealPath("/");
		// System.out.println(System.getProperty("user.dir"));//
		// filePath=getServletContext().getRealPath("/");
		// filePath =FyFunc// System.getProperty("user.dir")+"/";

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

		// =================
		String pictjson = request.getParameter("pict");

		if (pictjson != null)// pictjson="";
			htp1.setPictjson(pictjson);// 传入pict参数
		// ===================
		int ret = htp1.setSignjsonSxy(signjson);
		String onlogaction = "^serverdate^serverdatetime^path^test^"; // 不记录日志的接口名
		if (!onlogaction.contains("^" + action + "^")) {
			// 接口调用记录
			LogUtils.LogWrite(htp1.getUsername() +" "+ htp1.getIpaddress(), htp1.getUrl());
		}
		if (ret == 0)// 校验参数是否有效
		{
			Write(response, htp1.getErrmess());
			return;

		}
		switch (action) {
		case "getaccregbyname":// 通过注册名称找注册账号
			GetAccregbyname(response, htp1);
			break;
		case "employelogin": // 职员验证登录
			EmployeLogin(response, htp1, 0);
			break;

		case "addaccreg": // 新增账户记录
			AddAccregRec(response, htp1);
			break;
		case "totalwareloca": // 汇总货位数据
			TotalWareloca(response, htp1);
			break;

		case "totalcxkczy": // 汇总当前成本
			TotalCxkczy(response, htp1);
			break;
		case "repcxkczy": // 分页显示库存资源
			repCxkczy(response, htp1);
			break;

		case "repcxcgrk": // 查询采购入库
			repCxcgrk(response, htp1);
			break;
		case "repcxlsck": // 查询零售出库
			repCxlsck(response, htp1);
			break;
		case "repcxlsck0": // 查询零售出库(新)
			repCxlsck0(response, htp1);
			break;

		case "selectroleprog": // 选择角色功能
			SelectRoleprog(response, htp1);
			break;

		case "listprogpage": // 显示页面程序功能(v2版功能页) ok
			ListProgPage(response, htp1);
			break;
		case "selectprogpage": // 选择功能页程序(v2版功能页) ok
			SelectProgPage(response, htp1);
			break;

		case "addwareadjustm": // 新增商品调价明细
			AddWareadjustmRec(response, htp1);
			break;
		case "wareadjustmlist": // 获取商品调价明细列表
			GetWareadjustmList(response, htp1);
			break;
		case "sxywareinmlist": // 获取采购入库明细列表
			SxyWareinmList(response, htp1);
			break;
		case "sxyallotinmlist": // 获取调拨入库明细列表
			SxyAllotinmList(response, htp1);
			break;
		// ===========================================================================================
		case "emplarealist": // 获取职员区位授权列表
			GetEmplareaList(response, htp1);
			break;
		case "writeemplarea": // 写职员区位授权记录
			WriteEmplarea(response, htp1);
			break;
		case "writeallemplarea": // 成批写职员区位授权记录
			WriteAllemplarea(response, htp1);
			break;
		// ===========================================================================================
		case "provarealist": // 获取供应商区位授权列表
			GetProvareaList(response, htp1);
			break;
		case "writeprovarea": // 写供应商区位授权记录
			WriteProvarea(response, htp1);
			break;
		case "writeallprovarea": // 成批写供应商区位授权记录
			WriteAllprovarea(response, htp1);
			break;
		case "arealist": // 分页获取区位列表
			GetAreaList(response, htp1);
			break;

		// ===========================================================================================
		case "selectlocahelp": // 货位选择帮助
			Selectlocahelp(response, htp1);
			break;
		case "provlocalist": // 获取供应商货位授权列表
			GetProvlocaList(response, htp1);
			break;
		case "writeprovloca": // 写供应商货位授权记录
			WriteProvloca(response, htp1);
			break;
		// case "writealllocaarea": // 成批写供应商货位授权记录 未用
		// WriteAllprovloca(response, htp1);
		// break;
		// ===========================================================================================
		case "paychecklist": // 上下衣查看结算申请记录
			PaycheckList(response, htp1);
			break;
		case "provpaychecklist": // 供应商查看结算申请记录
			ProvPaycheckList(response, htp1);
			break;
		case "addpaycheck": // 供应商新增结算申请记录
			AddPaycheckRec(response, htp1);
			break;
		case "getpaycheck": // 供应商取结算申请记录
			GetPaycheck(response, htp1);
			break;
		// case "updatepaycheck": //供应商更改结算申请记录
		// UpdatePaycheck(response, htp1);
		// break;
		// case "provchangepaycheck": //供应商更改结算申请记录状态
		// ProvChangePaycheck(response, htp1);
		// break;
		// case "changepaycheck": //上下衣更改结算申请记录状态
		// ChangePaycheck(response, htp1);
		// break;
		// ===========================================================================================
		case "provwaresonglist": // 供应商查看上货，补货，返货,清仓申请记录
			ProvWaresongList(response, htp1);
			break;
		case "provwaretiaolist": // 供应商查看调货位申请记录
			ProvWaretiaoList(response, htp1);
			break;
		//
		// case "checktotallist": // 上下衣查看上货，返货，调位审核汇总
		// CheckTotalList(response, htp1);
		// break;

		case "updatewareincheck": // 按指定价格更新采购记录价格(上下衣)
			UpdateWareincheck(response, htp1);
			break;
		case "serverdate":
			ServerDate(response);
			break;
		case "serverdatetime":
			ServerDateTime(response);
			break;
		default:
			WriteResult(response, 0, "接口未定义1:" + action + "!");
			break;
		}
	}

	// 获取供应商货位授权列表 (上下衣用)
	protected void GetProvlocaList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long provid = jsonObject.has("provid") ? Long.parseLong(jsonObject.getString("provid")) : 0;
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		long areaid = jsonObject.has("areaid") ? Long.parseLong(jsonObject.getString("areaid")) : 0;
		// String findbox = jsonObject.has("findbox") ?
		// jsonObject.getString("findbox").trim().replace("'", "''") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = " a.locaid,a.locano,a.houseid,b.housename,a.areaid,c.areaname";
		String strwhere = " a.provid=" + provid;
		if (houseid > 0)
			strwhere += " and a.houseid=" + houseid;
		if (areaid > 0)
			strwhere += " and a.areaid=" + areaid;

		QueryParam qp = new QueryParam(page, pagesize, "locaid");

		Table tb = new Table();
		Wareloca dal = new Wareloca();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 货位选择帮助
	protected void Selectlocahelp(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long provid = jsonObject.has("provid") ? Long.parseLong(jsonObject.getString("provid")) : 0;
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		long areaid = jsonObject.has("areaid") ? Long.parseLong(jsonObject.getString("areaid")) : 0;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = " a.locaid,a.locano,a.houseid,b.housename,a.areaid,c.areaname";
		String strwhere = " a.provid=0";
		if (findbox.length() > 0)
			strwhere += " and (a.locano like '%" + findbox.toUpperCase() + "%'  or c.areaname like '%" + findbox //
					+ "%' or b.housename like '%" + findbox + "%' or b.shortname like '%" + findbox.toUpperCase() + "%' )";
		// if (houseid > 0)
		strwhere += " and a.houseid=" + houseid;
		if (areaid > 0)
			strwhere += " and a.areaid=" + areaid;

		strwhere += " and (a.provid=0 and a.entertag=0 and exists (select 1 from provarea a1 where a.areaid=a1.areaid and a1.provid=" + provid + ")";
		strwhere += " or a.provid=" + provid + " or a.provid0=" + provid + ")";

		QueryParam qp = new QueryParam(page, pagesize, "locaid");

		Table tb = new Table();
		Wareloca dal = new Wareloca();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// ==============================================
	// 获取供应商区位授权列表
	protected void GetProvareaList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long provid = jsonObject.has("provid") ? Long.parseLong(jsonObject.getString("provid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		Provarea dal = new Provarea();
		dal.setAccid(htp.getMaccid());
		dal.setProvid(provid);
		QueryParam qp = new QueryParam(page, pagesize, "provid");
		Table tb = new Table();
		tb = dal.GetTable(qp);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 获取职员区位授权列表
	protected void GetEmplareaList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long epid = jsonObject.has("epid") ? Long.parseLong(jsonObject.getString("epid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		Emplarea dal = new Emplarea();
		dal.setAccid(htp.getMaccid());
		dal.setEpid(epid);
		QueryParam qp = new QueryParam(page, pagesize, "areaid");
		Table tb = new Table();
		tb = dal.GetTable(qp);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 成批写供应商区位授权记录
	protected void WriteAllprovarea(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long provid = jsonObject.has("provid") ? Long.parseLong(jsonObject.getString("provid")) : 0;
		// long areaid = jsonObject.has("areaid") ?
		// Long.parseLong(jsonObject.getString("areaid")) : 0;
		int value = jsonObject.has("value") ? Integer.parseInt(jsonObject.getString("value")) : 0;
		Provarea dal = new Provarea();
		dal.setProvid(provid);
		// dal.setAreaid(areaid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.AppendAll(value), dal.getErrmess());

	}

	// 品牌分页列表
	protected void GetAreaList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		// System.out.println("BrandList 1 maccid=" + htp.maccid);
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// {"flyang":"20150107","rows":10,"page":1,"fieldlist":"brandid,brandname","sort":"brandid","order":"asc"}
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		int noused = jsonObject.has("noused") ? Integer.parseInt(jsonObject.getString("noused")) : 0;
		int hasware = jsonObject.has("hasware") ? Integer.parseInt(jsonObject.getString("hasware")) : 0;
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		int cxbj = jsonObject.has("cxbj") ? Integer.parseInt(jsonObject.getString("cxbj")) : 0;

		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " areaname ";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : " asc ";
		// String brandname = jsonObject.has("brandname") ?
		// jsonObject.getString("brandname") : "";
		// String lastdate = jsonObject.has("lastdate") ?
		// jsonObject.getString("lastdate") : "";
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		sort += " " + order;
		// String sort = " BRANDNAME,BRANDID ";
		// System.out.println("brandid=" + brandid);
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "A.AREAID,A.ACCID,A.AREANAME,A.NOUSED,A.STATETAG,A.LASTOP,A.LASTDATE";
		long accid = htp.getMaccid();
		long userid = htp.getUserid();
		String strwhere = " a.accid=" + accid + " and a.statetag=1";
		if (noused < 2)
			strwhere += " and a.NOUSED=" + noused;
		if (houseid > 0)
			strwhere += " and exists (select 1 from wareloca b where a.areaid=b.areaid and a.houseid=" + houseid + " and b.accid=" + accid + ")";

		if (hasware == 1)
			strwhere += " and exists (select 1 from warecode c where a.areaid=c.areaid and c.accid=" + accid + ")";
		// if (lastdate != null && lastdate != "") qry += " and
		// a.lastdate>to_date('" + lastdate + "','yyyy-mm-dd hh24:mi:ss')";

		// if (ywly < 2) qry += " and ywly=" + ywly;//ywly=0
		// 只查询手机端增加的单据，用于向erp传数据

		if (!Func.isNull(findbox))
			strwhere += " and a.AREANAME like '%" + findbox.toUpperCase() + "%' ";
		// if (cxbj == 1) // 只查权限允许的区位
		// {
		strwhere += " and exists (select 1 from emplarea d where a.areaid=d.areaid and d.epid=" + userid + ")";
		// }

		Table tb = new Table();
		Area dal = new Area();
		QueryParam qp = new QueryParam(page, pagesize, sort);

		tb = dal.GetTable(qp, strwhere, fieldlist);

		if (page < 0) {// 导出excel
			String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;
		}

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 成批写职员区位授权记录
	protected void WriteAllemplarea(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long epid = jsonObject.has("epid") ? Long.parseLong(jsonObject.getString("epid")) : 0;
		// long areaid = jsonObject.has("areaid") ?
		// Long.parseLong(jsonObject.getString("areaid")) : 0;
		int value = jsonObject.has("value") ? Integer.parseInt(jsonObject.getString("value")) : 0;
		Emplarea dal = new Emplarea();
		dal.setEpid(epid);
		// dal.setAreaid(areaid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.AppendAll(value), dal.getErrmess());

	}

	// 写供应商货位授权记录
	protected void WriteProvloca(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long provid = jsonObject.has("provid") ? Long.parseLong(jsonObject.getString("provid")) : 0;
		long locaid = jsonObject.has("locaid") ? Long.parseLong(jsonObject.getString("locaid")) : 0;
		int value = jsonObject.has("value") ? Integer.parseInt(jsonObject.getString("value")) : 0;
		Wareloca dal = new Wareloca();
		dal.setProvid(provid);
		dal.setLocaid(locaid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Write(value), dal.getErrmess());

	}

	// 写供应商区位授权记录
	protected void WriteProvarea(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long provid = jsonObject.has("provid") ? Long.parseLong(jsonObject.getString("provid")) : 0;
		long areaid = jsonObject.has("areaid") ? Long.parseLong(jsonObject.getString("areaid")) : 0;
		int value = jsonObject.has("value") ? Integer.parseInt(jsonObject.getString("value")) : 0;
		Provarea dal = new Provarea();
		dal.setProvid(provid);
		dal.setAreaid(areaid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Append(value), dal.getErrmess());

	}

	// 写职员区位授权记录
	protected void WriteEmplarea(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long epid = jsonObject.has("epid") ? Long.parseLong(jsonObject.getString("epid")) : 0;
		long areaid = jsonObject.has("areaid") ? Long.parseLong(jsonObject.getString("areaid")) : 0;
		int value = jsonObject.has("value") ? Integer.parseInt(jsonObject.getString("value")) : 0;
		Emplarea dal = new Emplarea();
		dal.setEpid(epid);
		dal.setAreaid(areaid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Append(value), dal.getErrmess());

	}

	// 获取调拨入库明细列表
	protected void SxyAllotinmList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		int sortid = jsonObject.has("sortid") ? Integer.parseInt(jsonObject.getString("sortid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String sort = "";
		if (sortid == 1)
			sort = " id ";
		else
			sort = "  wareid,colorid,sizeid,id ";// + order;
		String strwhere = " a.accid=" + htp.getMaccid() + " and a.noteno='" + noteno + "'";

		if (fieldlist.equals("*") || fieldlist.length() <= 0)
			fieldlist = " A.ID,a.NOTENO,a.ACCID,a.WAREID,a.COLORID,a.SIZEID,a.AMOUNT,a.PRICE,a.CURR,a.REMARK0,B.WARENO,B.WARENAME,B.UNITS,C.COLORNAME,D.SIZENAME";
		if (houseid == 0)
			fieldlist += ",b.retailsale ";
		else
			fieldlist += ",case when e.retailsale is null then b.retailsale else e.retailsale end  as retailsale";
		fieldlist += ", f.areaname";

		Allotinm dal = new Allotinm();
		// dal.setXtbj(0);
		QueryParam qp = new QueryParam(page, pagesize, sort);
		// System.out.println("1111");
		Table tb = new Table();

		tb = dal.GetTable(qp, strwhere, fieldlist, houseid);
		if (page < 0) {
			// System.out.println("11111");
			String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
			// System.out.println("22222");

			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;

		}
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 获取采购入库明细列表
	protected void SxyWareinmList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		int sortid = jsonObject.has("sortid") ? Integer.parseInt(jsonObject.getString("sortid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String sort = "";
		if (sortid == 1)
			sort = " id ";
		else
			sort = "  wareid,colorid,sizeid,id ";// + order;
		String strwhere = " a.accid=" + htp.getMaccid() + " and a.noteno='" + noteno + "'";

		if (fieldlist.equals("*") || fieldlist.length() <= 0)
			fieldlist = "  A.ID,a.NOTENO,a.ACCID,a.WAREID,a.COLORID,a.SIZEID,a.AMOUNT,a.PRICE0,a.DISCOUNT,a.PRICE,a.CURR,a.price1,a.REMARK0,B.WARENO,B.WARENAME,B.UNITS,C.COLORNAME,D.SIZENAME";
		if (houseid == 0)
			fieldlist += ",b.retailsale ";
		else
			fieldlist += ",case when e.retailsale is null then b.retailsale else e.retailsale end  as retailsale";
		fieldlist += ", f.areaname";

		Wareinm dal = new Wareinm();
		// dal.setXtbj(0);
		QueryParam qp = new QueryParam(page, pagesize, sort);
		// System.out.println("1111");
		Table tb = new Table();

		tb = dal.GetTable(qp, strwhere, fieldlist, houseid);
		if (page < 0) {
			// System.out.println("11111");
			String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
			// System.out.println("22222");

			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;

		}
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// // 取零售商品售价
	// protected void GetSaleoutprice(HttpServletResponse response, HttpInfo
	// htp) throws ServletException, IOException {
	// JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
	// int priceprec = htp.getPriceprec();
	// Wareoutm dal = new Wareoutm();
	// dal.setAccid(htp.getMaccid());
	// if (dal.doGetSaleoutpriceSXY(jsonObject, priceprec) == 0)
	// WriteResult(response, 0, dal.getErrmess());
	// else
	// WriteResultJson(response, 0, dal.getErrmess());
	//
	// }

	// 新增商品调价单明细
	protected void AddWareadjustmRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		// int statetag = jsonObject.has("statetag") ?
		// Integer.parseInt(jsonObject.getString("statetag")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		Wareadjustm dal = new Wareadjustm();
		dal.setAccid(htp.getMaccid());
		dal.setNoteno(noteno);
		dal.setWareid(wareid);
		dal.setCalcdate(htp.getCalcdate());
		if (dal.AppendSXY() == 0)
			WriteResult(response, 0, dal.getErrmess());
		else
			WriteResultJson(response, 1, dal.getErrmess());

	}

	// 获取商品调价明细列表
	protected void GetWareadjustmList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		int sortid = jsonObject.has("sortid") ? Integer.parseInt(jsonObject.getString("sortid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String sort = " ";
		if (sortid == 1)
			sort = " id ";
		else
			sort = "  wareno,wareid,id ";// + order;
		// sort += " " + order;
		String strwhere = " a.ACCID=" + htp.getMaccid() + " and a.NOTENO='" + noteno + "'";
		if (findbox.length() > 0)
			strwhere += " and (b.wareno like '%" + findbox.toUpperCase() + "%' or b.shortname like '%" + findbox.toUpperCase() + "%' or b.warename like '%" + findbox + "%')";

		if (fieldlist.equals("*") || fieldlist.length() <= 0) // fieldlist =
																// "a.id,a.accid,a.noteno,a.wareid,b.warename,b.units,a.colorid,c.colorname,a.sizeid,d.sizename,a.amount,a.price,a.discount,a.curr,a.remark0";
			fieldlist = "A.ID,A.NOTENO,A.ACCID,A.WAREID,B.WARENO,B.WARENAME,B.UNITS,B.SSDATE,A.ENTERSALE,A.RETAILSALE,A.SALE1,A.SALE2,A.SALE3,A.SALE4,A.SALE5,A.ENTERSALE_0,A.RETAILSALE_0,A.SALE1_0,A.SALE2_0,A.SALE3_0,A.SALE4_0,A.SALE5_0,a.REMARK0";

		QueryParam qp = new QueryParam(page, pagesize, sort);

		String calclist = "f_getchecksale(entersale) as checksale,f_getchecksale(ENTERSALE_0) as checksale_0";
		if (houseid > 0)
			calclist += ",f_getwaresumx(to_char(sysdate,'yyyy-mm-dd'),accid,wareid," + houseid + ",'" + htp.getCalcdate() + "') as amountkc";
		else
			calclist += ",0 as amountkc";

		qp.setCalcfield(calclist);

		Wareadjustm dal = new Wareadjustm();

		Table tb = new Table();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 选择角色功能
	protected void SelectRoleprog(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long levelid = jsonObject.has("levelid") ? Long.parseLong(jsonObject.getString("levelid")) : 0;
		String qry = " select a.progid,b.progname,c.sysname,b.sysid,b.ordid,'1' as selbj from roleprog a";
		qry += " left outer join sysprog b on a.progid=b.progid";
		qry += " join sysprogsys c on b.sysid=c.sysid";
		qry += " where a.fs=0 and a.levelid=" + levelid;
		qry += " and b.htag<=10";
		qry += " union all";
		qry += " select b.progid,b.progname,c.sysname,b.sysid,b.ordid,'0' as selbj from sysprog b ";
		qry += " join sysprogsys c on b.sysid=c.sysid";
		qry += " where not exists (select 1 from roleprog a where a.progid=b.progid and a.fs=0 and a.levelid=" + levelid + ")";
		qry += " and b.htag<=10";
		Sysprog dal = new Sysprog();
		Table tb = new Table();

		tb = dal.GetTable(qry);

		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// v2版显示各页 fs=1云壹
	protected void ListProgPage(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int pageid = jsonObject.has("pageid") ? Integer.parseInt(jsonObject.getString("pageid")) : 0;
		int hytag = jsonObject.has("hytag") ? Integer.parseInt(jsonObject.getString("hytag")) : 2;
		int gysbj = jsonObject.has("gysbj") ? Integer.parseInt(jsonObject.getString("gysbj")) : 2;
		// gysbj:0=上下衣公司，1=上下衣供应商
		long userid = htp.getUserid();
		String fieldlist = "a.progid,b.progname,b.progno,'' as sysname ,substr(b.grpname,2,20) as grpname ";

		String strwhere = " b.noused=0 and a.epid=" + userid + " and a.pageid=" + pageid;
		strwhere += " and exists (select 1 from employe a1 join roleprog b1 on a1.levelid=b1.levelid where a1.epid=" + userid + " and b1.fs=0 and a.progid=b1.progid) ";
		// sysprog->htag:程序属性0=通用(蓝窗),1=云壹，10=上下衣
		// sysprog->ptag:运行设备
		// 0=手机+pc端+ipad，1=pc端专用,2=手机，3=ipad,4=手机+pc,5=手机+ipad，6=pc+ipad
		strwhere += " and (b.ptag=0 or b.ptag=2 or b.ptag=4 or b.ptag=5) ";

		// gysbj:0=上下衣公司，1=上下衣供应商
		if (gysbj == 0)
			strwhere += " and (b.htag<10 or b.htag=10)";
		else if (gysbj == 1)
			strwhere += " and (b.htag<10 or b.htag=11)";

		if (hytag == 0)
			strwhere += " and (b.tag=0 or b.tag=2)";
		else if (hytag == 1)
			strwhere += " and (b.tag=1 or b.tag=2)";
		// if (pageid > 0)
		// qry += " and b.groupid=" + pageid;
		strwhere += " and b.grpid=" + pageid;

		String order = "  b.grpname,b.ordid,a.progid "; // and rownum<=23

		Sysfirstprog1 dal = new Sysfirstprog1();
		dal.setPageid(pageid);
		dal.setEpid(htp.getUserid());
		Table tb = new Table();
		tb = dal.GetTable(strwhere, fieldlist, order, hytag, gysbj);
		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 选择功能页的程序
	protected void SelectProgPage(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int pageid = jsonObject.has("pageid") ? Integer.parseInt(jsonObject.getString("pageid")) : 0;
		int hytag = jsonObject.has("hytag") ? Integer.parseInt(jsonObject.getString("hytag")) : 2;
		int gysbj = jsonObject.has("gysbj") ? Integer.parseInt(jsonObject.getString("gysbj")) : 2;
		// gysbj:0=上下衣公司，1=上下衣供应商
		long userid = htp.getUserid();
		Sysfirstprog1 dal = new Sysfirstprog1();
		Table tb = new Table();
		dal.setPageid(pageid);
		dal.setEpid(userid);
		tb = dal.GetTable(hytag, gysbj);// DbHelperSQL.GetTable(qry);
		Write(response, DbHelperSQL.DataTable2Json(tb));
	}

	// 通过账号名称获取账号id accid
	protected void GetAccregbyname(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {

		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "";
		String accname = jsonObject.has("accname") ? jsonObject.getString("accname") : "";
		// int all = jsonObject.has("all") ?
		// Integer.parseInt(jsonObject.getString("all")) : 0;

		accname = accname.toUpperCase().trim();

		// if (!Func.isNumAndEnCh(accname)) {
		// WriteResult(response, 0, "企业账号异常！");
		// return;
		// }
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "ACCID,ACCNAME,REGDATE,MOBILE,COMPANY,LINKMAN,ADDRESS,EMAIL,TEL,PASSKEY,PASSWORD,NOUSED,TAG,LASTDATE,BALCURR,EPID ";
		// String strwhere = " accname= '" + accname + "' and lytag=2";
		String strwhere = " accname= '" + accname + "' and qybj=2";

		Table tb = new Table();
		Accreg dal = new Accreg();
		tb = dal.GetList1(strwhere, fieldlist).getTable(1);
		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 新增账户记录
	protected void AddAccregRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Accreg dal = new Accreg();

		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		// dal.setLytag(2);
		if (dal.Append(1) == 0) {
			WriteResult(response, 0, dal.getErrmess());
			return;
		}
		WriteResult(response, 1, dal.getAccid().toString());
	}

	// 职员登录
	// protected void EmployeLogin()
	protected void EmployeLogin(HttpServletResponse response, HttpInfo htp, int fs) throws ServletException, IOException {
		// System.out.println("EmployeLogin begin...");

		Employe dal = new Employe();

		// Table tb = new Table();

		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		String epno = jsonObject.has("epno") ? jsonObject.getString("epno") : "";
		String deviceno = jsonObject.has("deviceno") ? jsonObject.getString("deviceno") : "";
		String devicetype = jsonObject.has("devicetype") ? jsonObject.getString("devicetype") : "???";
		String password = jsonObject.has("password") ? jsonObject.getString("password") : "";
		// int all = jsonObject.has("all") ?
		// Integer.parseInt(jsonObject.getString("all")) : 0;
		long accid = jsonObject.has("accid") ? Long.parseLong(jsonObject.getString("accid")) : 0;

		dal.setAccid(accid);
		dal.setEpno(epno.toUpperCase().trim());
		dal.setPassword(password.toUpperCase());
		int ret = dal.Login_sxy(deviceno, devicetype, fs, sxy_accid);
		if (ret == 0)
			WriteResult(response, 0, dal.getErrmess());
		else
			WriteResultJson(response, 1, dal.getErrmess());
	}

	// 汇总货位数据
	protected void TotalWareloca(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		String nowdate = jsonObject.has("nowdate") ? jsonObject.getString("nowdate") : "";
		// System.out.println("TotalWareSum=" + nowdate);

		String qry = "begin  p_sxytotalpay(" + sxy_accid + ",'" + nowdate + "'); end;";

		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			WriteResult(response, 0, "操作异常！");
		} else {
			WriteResult(response, 1, "操作成功！");

		}
		// Funcpackge dal = new Funcpackge();

		// WriteResult(response, dal.myTotalWaresum(nowdate, 0),
		// dal.getErrmess());
	}

	// 汇总库存资源
	protected void TotalCxkczy(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String nowdate = jsonObject.has("nowdate") ? jsonObject.getString("nowdate") : "";

		// String houseidlist = jsonObject.has("houseidlist") ?
		// jsonObject.getString("houseidlist") : "";
		if (nowdate.equals(""))
			nowdate = htp.getNowdatetime();
		int qxbj = htp.getQxbj();
		vTotalCxkczy dal = new vTotalCxkczy();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setAccid(htp.getMaccid());
		dal.setUserid(htp.getUserid());
		dal.setCxdatetime(nowdate);
		dal.setQxbj(qxbj);
		dal.setServerdatetime(htp.getNowdatetime());
		dal.setIssxy(1);
		WriteResult(response, dal.doCxkczy(), dal.getErrmess());

	}

	// 查询库存资源
	protected void repCxkczy(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// int xls = jsonObject.has("xls") ?
		// Integer.parseInt(jsonObject.getString("xls")) : 0;
		int sizenum = jsonObject.has("sizenum") ? Integer.parseInt(jsonObject.getString("sizenum")) : 0;
		// 如果sizenum>0,表示按尺码横向展开显示
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;

		int housecostbj = htp.getHousecostbj(); // 分店铺计算成本

		vTotalCxkczy dal = new vTotalCxkczy();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(htp.getMaccid());
		dal.setUserid(htp.getUserid());

		QueryParam qp = new QueryParam();
		qp.setPageIndex(page);
		qp.setPageSize(pagesize);
		Table tb = new Table();

		if (sizenum > 0) {

			String jsonstr = dal.GetTable2Json(qp, housecostbj, sizenum);

			if (page < 0) {// 导出excel

				String retcs = DbHelperSQL.Json2Excel(jsonstr, jsonObject);
				WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
				return;
			}
			Write(response, jsonstr);
			return;
		}

		tb = dal.GetTableSxy(qp, housecostbj);

		if (page < 0) {// 导出excel
			String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;
		}

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 查询采购入库
	protected void repCxcgrk(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int sizenum = jsonObject.has("sizenum") ? Integer.parseInt(jsonObject.getString("sizenum")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;

		int qxbj = htp.getQxbj();
		int maxday = htp.getMaxday();

		vCxcgrk dal = new vCxcgrk();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setIssxy(1);
		dal.setAccid(htp.getMaccid());
		dal.setUserid(htp.getUserid());
		dal.setMaxday(maxday);
		dal.setQxbj(qxbj);
		dal.setNowdate(htp.getNowdate());
		dal.setAccbegindate(htp.getAccdate());
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

	// 查询零售出库
	protected void repCxlsck0(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int sizenum = jsonObject.has("sizenum") ? Integer.parseInt(jsonObject.getString("sizenum")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 50;
		int qxbj = htp.getQxbj();
		int maxday = htp.getMaxday();
		vCxxsck dal = new vCxxsck();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setIssxy(1);
		dal.setAccid(htp.getMaccid());
		dal.setQxbj(qxbj);
		dal.setMaxday(maxday);
		dal.setNowdate(htp.getNowdate());
		dal.setAccbegindate(htp.getAccdate());
		dal.setUserid(htp.getUserid());
		Table tb = new Table();
		QueryParam qp = new QueryParam();

		qp.setPageIndex(page);
		qp.setPageSize(pagesize);
		// System.out.println("sizenum="+sizenum);

		if (sizenum > 0) // 如果尺码大于0，表示尺码横向展开方式
		{

			// System.out.println("00000");
			String jsonstr = dal.GetTable2Json00(qp, sizenum);
			// System.out.println("11111");
			if (page < 0) {// 导出excel
				String retcs = DbHelperSQL.Json2Excel(jsonstr, jsonObject);
				WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
				return;
			}
			Write(response, jsonstr);
			return;
		}

		tb = dal.GetTable00(qp);

		// 导出excel
		if (page < 1) {
			// "columns":[{"brandid":"品牌id"},{"brandname":"品牌名称"}]
			// qp.setPageIndex(-1);
			// tb = dal.GetTable0(qp); // 所有数据
			String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;
		}

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 查询零售出库
	protected void repCxlsck(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int sizenum = jsonObject.has("sizenum") ? Integer.parseInt(jsonObject.getString("sizenum")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		int qxbj = htp.getQxbj();
		int maxday = htp.getMaxday();
		vCxxsck dal = new vCxxsck();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setIssxy(1);
		dal.setAccid(htp.getMaccid());
		dal.setQxbj(qxbj);
		dal.setMaxday(maxday);
		dal.setNowdate(htp.getNowdate());
		dal.setAccbegindate(htp.getAccdate());
		dal.setUserid(htp.getUserid());
		Table tb = new Table();
		QueryParam qp = new QueryParam();

		qp.setPageIndex(page);
		qp.setPageSize(pagesize);
		// System.out.println("sizenum="+sizenum);

		if (sizenum > 0) // 如果尺码大于0，表示尺码横向展开方式
		{

			// System.out.println("00000");
			String jsonstr = dal.GetTable2Json0(qp, sizenum);
			// System.out.println("11111");
			if (page < 0) {// 导出excel
				String retcs = DbHelperSQL.Json2Excel(jsonstr, jsonObject);
				WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
				return;
			}
			Write(response, jsonstr);
			return;
		}

		tb = dal.GetTable0(qp);

		// 导出excel
		if (page < 1) {
			// "columns":[{"brandid":"品牌id"},{"brandname":"品牌名称"}]
			// qp.setPageIndex(-1);
			// tb = dal.GetTable0(qp); // 所有数据
			String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;
		}

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 供应商查看结算申请记录
	protected void ProvPaycheckList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String currdate = htp.getNowdate();// Func.DateToStr(pFunc.getServerdatetime(),
											// "yyyy-MM-dd");
		String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : currdate;
		String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : currdate;
		// String findbox = jsonObject.has("findbox") ?
		// jsonObject.getString("findbox").trim().replace("'", "''") : "";
		long provid = jsonObject.has("provid") ? Long.parseLong(jsonObject.getString("provid")) : 0;
		// String fieldlist = jsonObject.has("fieldlist") ?
		// jsonObject.getString("fieldlist") : "*";
		// String handno = jsonObject.has("handno") ?
		// jsonObject.getString("handno") : "";
		// String remark = jsonObject.has("remark") ?
		// jsonObject.getString("remark") : "";
		// String noteno = jsonObject.has("noteno") ?
		// jsonObject.getString("noteno") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		int cxfs = jsonObject.has("cxfs") ? Integer.parseInt(jsonObject.getString("cxfs")) : 0;
		// cxfs:0=当前，1=历史，
		String mess = "";
		if (cxfs == 1) {
			String accbegindate = htp.getAccdate();// 账套开始使用日期
			int maxday = htp.getMaxday();
			// 校验开始查询日期
			MinDateValid mdv = new MinDateValid(mindate, currdate, accbegindate, maxday);
			mindate = mdv.getMindate();
			mess = mdv.getErrmess();
			if (mindate.compareTo(maxdate) > 0)
				maxdate = mindate; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期

		}
		// String fieldlist = "
		// a.id,a.accid,a.noteno,a.notedate,a.curr,a.curr0,b.provid,b.provname,a.remark";
		// fieldlist +=
		// ",a.lastop,a.checkman,a.cwcheck,a.operant,a.statetag,a.bankname,a.bankgroup,a.bankman,a.bankno";

		String fieldlist = " a.id,a.accid,a.noteno,a.notedate,a.curr,a.curr0,a.remark";
		fieldlist += ",a.lastop,a.checkman,a.cwcheck,a.operant,a.statetag,a.bankname,a.bankman,a.bankno,a.bankgroup,a.refuse";

		// qry += " from paycheck a ";
		// qry += " left outer join provide b on a.provid1=b.provid";
		String strwhere = "  a.accid=" + htp.getMaccid() + " and a.accid1=" + sxy_accid;// =sxy_accid;//
																						// accid;
		if (provid > 0)
			strwhere += " and a.provid1=" + provid;
		// if (statetag >= 1 && statetag <= 3) qry += " and a.statetag=" +
		// statetag;
		// statetag:0=草稿，1=提交，2=主管审核通过，3=财务审核通过，4=已转款，5=到账
		if (cxfs == 0)
			strwhere += " and (a.statetag=0 and a.operant='" + htp.getUsername() + "' or a.statetag>0 and a.statetag<5)";
		else {
			strwhere += " and a.statetag=5";
			strwhere += " and a.NOTEDATE >= to_date('" + mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
			strwhere += " and a.NOTEDATE <= to_date('" + maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		}

		Paycheck dal = new Paycheck();

		QueryParam qp = new QueryParam(page, pagesize, " notedate desc,id ");
		qp.setSumString("nvl(sum(curr),0) as totalcurr,nvl(sum(curr0),0) as totalcurr0");
		Table tb = new Table();
		if (cxfs == 1)
			qp.setTotalString("'" + mess + "' as warning");

		tb = dal.GetTable(qp, strwhere, fieldlist);
		// if (page < 0) {// 导出excel
		// String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
		// WriteResult(response, Integer.parseInt(retcs.substring(0, 1)),
		// retcs.substring(1));
		// return;
		// }

		// System.out.println(qp.getTotalString());
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 供应商取结算申请记录
	protected void GetPaycheck(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// float id = jsonObject.has("id") ?
		// Float.parseFloat(jsonObject.getString("id")) : 0;
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		// int showbalcurr = jsonObject.has("showbalcurr") ?
		// Integer.parseInt(jsonObject.getString("showbalcurr")) : 0;
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";

		String strwhere = " noteno='" + noteno + "' and ACCID=" + htp.getMaccid();

		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "id,noteno,notedate,accid,curr0,bankname,bankman,bankno,bankgroup,remark";

		Table tb = new Table();
		Paycheck dal = new Paycheck();

		tb = dal.GetList(strwhere, fieldlist).getTable(1);

		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 供应商新增结算申请记录
	protected void AddPaycheckRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Paycheck dal = new Paycheck();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		// dal.setStatetag(0);
		if (dal.Append() == 0)
			WriteResult(response, 0, dal.getErrmess());
		else
			WriteResult(response, 1, dal.getErrmess());
	}

	// 上下衣公司查看结算申请记录
	protected void PaycheckList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String currdate = htp.getNowdate();// Func.DateToStr(pFunc.getServerdatetime(),
											// "yyyy-MM-dd");
		String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : currdate;
		String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : currdate;
		// String findbox = jsonObject.has("findbox") ?
		// jsonObject.getString("findbox").trim().replace("'", "''") : "";
		long provid = jsonObject.has("provid") ? Long.parseLong(jsonObject.getString("provid")) : 0;
		// String fieldlist = jsonObject.has("fieldlist") ?
		// jsonObject.getString("fieldlist") : "*";
		// String handno = jsonObject.has("handno") ?
		// jsonObject.getString("handno") : "";
		// String remark = jsonObject.has("remark") ?
		// jsonObject.getString("remark") : "";
		// String noteno = jsonObject.has("noteno") ?
		// jsonObject.getString("noteno") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		int cxfs = jsonObject.has("cxfs") ? Integer.parseInt(jsonObject.getString("cxfs")) : 0;
		// cxfs:0=当前，1=历史，
		String mess = "";
		if (cxfs == 1) {
			String accbegindate = htp.getAccdate();// 账套开始使用日期
			int maxday = htp.getMaxday();
			// 校验开始查询日期
			MinDateValid mdv = new MinDateValid(mindate, currdate, accbegindate, maxday);
			mindate = mdv.getMindate();
			mess = mdv.getErrmess();
			if (mindate.compareTo(maxdate) > 0)
				maxdate = mindate; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期

		}
		String fieldlist = "  a.id,a.accid,a.noteno,a.notedate,a.curr,a.curr0,b.provid,b.provname,a.remark";
		fieldlist += ",a.lastop,a.checkman,a.cwcheck,a.operant,a.statetag,a.bankname,a.bankgroup,a.bankman,a.bankno";
		// qry += " from paycheck a ";
		// qry += " left outer join provide b on a.provid1=b.provid";
		String strwhere = "  a.accid1=" + htp.getMaccid();// =sxy_accid;//
															// accid;
		if (provid > 0)
			strwhere += " and b.provid=" + provid;
		// if (statetag >= 1 && statetag <= 3) qry += " and a.statetag=" +
		// statetag;
		// statetag:0=草稿，1=提交，2=主管审核通过，3=财务审核通过，4=已转款，5=到账
		if (cxfs == 0)
			strwhere += " and a.statetag>=1 and a.statetag<=3";
		else {
			strwhere += " and a.statetag>=4";
			strwhere += " and a.NOTEDATE >= to_date('" + mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
			strwhere += " and a.NOTEDATE <= to_date('" + maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		}

		Paycheck dal = new Paycheck();

		QueryParam qp = new QueryParam(page, pagesize, " notedate desc,id ");
		qp.setSumString("nvl(sum(curr),0) as totalcurr,nvl(sum(curr0),0) as totalcurr0");
		Table tb = new Table();
		if (cxfs == 1)
			qp.setTotalString("'" + mess + "' as warning");

		tb = dal.GetTable(qp, strwhere, fieldlist);
		// if (page < 0) {// 导出excel
		// String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
		// WriteResult(response, Integer.parseInt(retcs.substring(0, 1)),
		// retcs.substring(1));
		// return;
		// }

		// System.out.println(qp.getTotalString());
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 供应商公司查看调换货位记录
	protected void ProvWaretiaoList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		int cxfs = jsonObject.has("cxfs") ? Integer.parseInt(jsonObject.getString("cxfs")) : 0;
		// cxfs:0=当前的,1=历史
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		if (fieldlist.equals("*") || fieldlist.equals("")) {
			fieldlist = " select  a.id,a.accid,a.noteno,a.notedate,b.provid,b.provname,a.remark,a.checkman";
			fieldlist += ",a.olocaid,a.nlocaid,c.locano as olocano,d.locano as nlocano,c1.areaname as oareaname,d1.areaname as nareaname";
			fieldlist += ",c.rent as orent,d.rent as nrent,a.statetag";
			fieldlist += ",a1.warename,a1.wareno,a1.units,a2.colorname,a1.imagename0,a.lastop,a.operant";

		}
		String lastop = htp.getUsername();
		String strwhere = " a.accid1=" + sxy_accid + " and a.accid=" + htp.getMaccid();
		if (houseid > 0)
			strwhere += " and c.houseid=" + houseid + " and d.houseid=" + houseid;
		// if (statetag >= 1 && statetag <= 3) qry += " and a.statetag=" +
		// statetag;
		if (cxfs == 0)
			strwhere += " and (a.statetag=1 or a.statetag=2 or a.statetag=3 or a.statetag=0 and a.operant='" + lastop + "')";
		else {// statetag:0=草稿，1=提交，2=审核通过，3=审核未通过,4=调位完成
				// cxfs:0=上货,补货，1=返货,清仓,2=上货,补货历史 ,3=返货,清仓历史
			String currdate = htp.getNowdate();// Func.DateToStr(pFunc.getServerdatetime(),
												// "yyyy-MM-dd");
			String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : currdate;
			String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : currdate;
			strwhere += " and a.statetag>=4";
			strwhere += " and a.notedate>=to_date('" + mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		}
		QueryParam qp = new QueryParam(page, pagesize, " notedate desc,id");
		// qp.setSumString("nvl(sum(curr),0) as totalcurr,nvl(sum(amount),0) as
		// totalamt,nvl(sum(amount0),0) as totalamt0");
		Table tb = new Table();
		Waretiao dal = new Waretiao();

		tb = dal.GetTable(qp, strwhere, fieldlist);
		// if (page < 0) {// 导出excel
		// String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
		// WriteResult(response, Integer.parseInt(retcs.substring(0, 1)),
		// retcs.substring(1));
		// return;
		// }
		// System.out.println(qp.getTotalString());
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 供应商查看上货补货返货清仓记录
	protected void ProvWaresongList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		// long wareid = jsonObject.has("wareid") ?
		// Long.parseLong(jsonObject.getString("wareid")) : 0;
		// int statetag = jsonObject.has("statetag") ?
		// Integer.parseInt(jsonObject.getString("statetag")) : 1;
		int cxfs = jsonObject.has("cxfs") ? Integer.parseInt(jsonObject.getString("cxfs")) : 0;
		// cxfs:0=上货,补货，1=返货,清仓,2=上货,补货历史 ,3=返货,清仓历史
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		if (fieldlist.equals("*") || fieldlist.equals("")) {
			fieldlist = " a.id,a.accid,a.noteno,a.notedate,a.ntid,a.remark,a.statetag";
			fieldlist += ",a.wareid,c.warename,c.units,c.wareno,a.colorid,d.colorname,a.amount0,a.price0,a.curr0,a.lastop,a.operant,a.checkman";
			fieldlist += ",a.locaid,b.locano,f.areaname,b.rent,c.imagename0,a.ywnoteno"; // ,e.housename
		}
		String lastop = htp.getUsername();
		String strwhere = " a.accid1=" + sxy_accid + " and a.accid=" + htp.getMaccid();
		// if (houseid > 0)
		strwhere += " and b.houseid=" + houseid;
		// if (statetag >= 1 && statetag <= 3) qry += " and a.statetag=" +
		// statetag;
		if (cxfs == 0)
			strwhere += " and (a.ntid=0 or a.ntid=1) and (a.statetag=1 or a.statetag=2 or a.statetag=3 or a.statetag=0 and a.operant='" + lastop + "')";
		else if (cxfs == 1)
			strwhere += " and (a.ntid=2 or a.ntid=3) and (a.statetag=1 or a.statetag=2 or a.statetag=3 or a.statetag=0 and a.operant='" + lastop + "')";
		else if (cxfs == 2 || cxfs == 3) { // cxfs:0=上货,补货，1=返货,清仓,2=上货,补货历史
											// ,3=返货,清仓历史
			String currdate = htp.getNowdate();// Func.DateToStr(pFunc.getServerdatetime(),
												// "yyyy-MM-dd");
			String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : currdate;
			String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : currdate;
			if (cxfs == 2)
				strwhere += " and (a.ntid=0 or a.ntid=1)";
			else
				strwhere += " and (a.ntid=2 or a.ntid=3)";
			strwhere += "  and a.notedate>=to_date('" + mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
			strwhere += " and a.statetag=4";
		}
		QueryParam qp = new QueryParam(page, pagesize, " notedate desc,id");
		qp.setSumString("nvl(sum(curr),0) as totalcurr,nvl(sum(amount),0) as totalamt,nvl(sum(amount0),0) as totalamt0");
		Table tb = new Table();
		Waresong dal = new Waresong();

		tb = dal.GetTable(qp, strwhere, fieldlist);
		// if (page < 0) {// 导出excel
		// String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
		// WriteResult(response, Integer.parseInt(retcs.substring(0, 1)),
		// retcs.substring(1));
		// return;
		// }

		// System.out.println(qp.getTotalString());
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 、、刷新采购明细价格(核价)
	protected void UpdateWareincheck(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String nowdatestr = Func.DateToStr(pFunc.getServerdatetime(), "yyyy-MM-dd");
		String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : nowdatestr;
		String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : nowdatestr;

		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		String remark0 = jsonObject.has("remark0") ? jsonObject.getString("remark0") : "";
		int tkbj = jsonObject.has("tkbj") ? Integer.parseInt(jsonObject.getString("tkbj")) : 0;
		int zlbj = jsonObject.has("zlbj") ? Integer.parseInt(jsonObject.getString("zlbj")) : 0;

		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		float id = jsonObject.has("id") ? Float.parseFloat(jsonObject.getString("id")) : 0;
		float price = jsonObject.has("price") ? Float.parseFloat(jsonObject.getString("price")) : 0;
		float price0 = jsonObject.has("price0") ? Float.parseFloat(jsonObject.getString("price0")) : 0;
		float price1 = jsonObject.has("price1") ? Float.parseFloat(jsonObject.getString("price1")) : 0;
		if (mindate.compareTo(htp.getAccdate()) < 0)
			mindate = htp.getAccdate();
		int priceprec = htp.getPriceprec();

		Wareinm dal = new Wareinm();
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		dal.setNoteno(noteno);
		dal.setWareid(wareid);
		dal.setRemark0(remark0);
		dal.setId(id);
		dal.setPrice(price);
		dal.setPrice0(price0);
		dal.setPrice1(price1);
		WriteResult(response, dal.RefushPrice_sxy(mindate, maxdate, tkbj, zlbj, priceprec), dal.getErrmess());

	}

	// ********************************************************
	protected void ServerDate(HttpServletResponse response) throws ServletException, IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String jsonstr = "{\"result\":1,\"msg\":\"操作成功！\",\"SERVERDATE\":\"" + df.format(pFunc.getServerdatetime()) + "\"}";
		response.getWriter().write(jsonstr);
	}

	protected void ServerDateTime(HttpServletResponse response) throws ServletException, IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// String jsonstr = "{\"SERVERDATETIME\":\"" + df.format(new Date()) +
		// "\"}";

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
