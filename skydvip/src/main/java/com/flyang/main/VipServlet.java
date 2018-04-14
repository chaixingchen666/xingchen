package com.flyang.main;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.comdot.data.Table;
import com.common.tool.*;
import com.oracle.bean.*;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.QueryParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
  * 蓝窗会员app接口
 */
@WebServlet("/api")
public class VipServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String filePath = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VipServlet() {
		super();
		// TODO Auto-generated constructor stub
		filePath = Func.initRootPath();// +"skydvip.config/";
		System.out.println("VipServlet is ready ok! " + filePath);

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");

		String url = request.getRequestURL() + "?" + request.getQueryString();

		// LogUtils.LogWrite("VIP调用", "", url + " post:" + request.getParameterMap().toString(), "vip");

		String action = request.getParameter("action");
		// 测试接口
		if (action.equals("path")) {
			response.getWriter().write(filePath + "   ---vip");
			return;
		} else

		if (action.equals("serverdate")) {
			ServerDateTime(response);
			// response.getWriter().write(filePath + " test.... ");
			return;
		}

		HttpInfo htp1 = new HttpInfo();
		htp1.setAction(action);// 设action

		String signjson = request.getParameter("sign"); // 取sign参数

		//System.out.println("1111");
		//System.out.println(signjson);
		if (signjson == null || signjson.equals("")) {
			WriteResult(response, 0, "sign参数无效！");
			return;
		}

		signjson = Func.Base64Decode(signjson);// sign base64解码
		htp1.setUrl(url);//
		String datajson = request.getParameter("data");
		if (datajson != null)
			htp1.setDatajson(datajson);// 传入data参数
		// htp1.setPictjson(request.getParameter("pict"));// 传入pict参数
		// =================
		String pictjson = request.getParameter("pict");
		// LogUtils.LogDebugWrite("VIPServlet pict=", pictjson);

		if (pictjson != null)// pictjson="";
			htp1.setPictjson(pictjson);// 传入pict参数
		// ===================

		if (htp1.setSignjson(signjson) == 0)// 校验http参数是否有效
		{
			Write(response, htp1.getErrmess());
			return;

		}
		String onlogaction = "^serverdate^serverdatetime^path^test^"; // 不记录日志的接口名
		if (!onlogaction.contains("^" + action + "^")) {
			// 接口调用记录
			// LogUtils.LogWrite(htp1.getUsername(), "", htp1.getUrl());
			LogUtils.LogWrite(htp1.getUsername() + Func.getIpAddress(request), htp1.getUrl());
		}
		String ss = Func.HttpIsDanger(htp1.getUrl());
		if (!ss.equals("")) {

			// 有危险操作的请求不允许通过
			LogUtils.LogWrite(htp1.getUsername() + "【 " + ss.trim() + "】" + Func.getIpAddress(request), htp1.getUrl(), "warn");
			WriteResult(response, 0, "警告：请求含中有危险操作命令！");
			return;

		}
		//System.out.println("action=" + action);
		try {
			switch (action) {

			case "viplogin": // VIP验证登录
				VipLogin(response, htp1);
				break;

			case "addvipgroup": // 新增会员账户记录
				AddVipgroupRec(response, htp1);
				break;
			case "getdeviceno": // 获取当前用户最近登录的设备号
				GetDeviceno(response, htp1);
				break;
			case "getvipnamebymobile": // 通过手机号找VIP注册账号
				GetVipnamebymobile(response, htp1);
				break;

			case "listpublicbanner": // 获取共公标题横幅
				ListPublicbanner(response, htp1);
				break;
			case "listhousebanner": // 获取店铺标题横幅
				ListHousebanner(response, htp1);
				break;
			case "listwaretype1": // 获取商品一级类型
				GetWaretype1List(response, htp1);
				break;
			// 以上接口不需要签名校验
			case "addguestvip": // 增加会员申请
				AddGuestvipRec(response, htp1);
				break;
			case "getonhouseattention": // 取入会须知
				GetOnhouseAttention(response, htp1);
				break;
			case "updatevipgroup": // 修改会员信息记录
				UpdateVipgroupRec(response, htp1);
				break;
			case "changevipmobile": // 更改会员手机
				ChangeVipmobile(response, htp1);
				break;
			// ===============================
			case "uploadviplogo": // 上传会员头像logo
				UploadViplogo(response, htp1);
				break;
			case "addvipcoupon": // 添加会员优惠券（会员领取优惠券）
				AddVipcoupon(response, htp1);
				break;
			case "listvipcoupon": // 显示会员优惠券（未用、已用、过期）
				ListVipcoupon(response, htp1);
				break;
			case "listhousecoupon": // 显示店铺未领优惠券
				ListHousecoupon(response, htp1);
				break;

			case "listmyhouse": // 显示我的店铺信息，可领优惠券，商品陈列
				ListMyHouse(response, htp1);
				break;
			case "uploadlogmess": // 上传日志信息
				UploadLogmess(response, htp1);
				break;
			// ===================================
			case "getmyhouse": // 显示我的店铺信息
				GetMyHouse(response, htp1);
				break;
			case "getwareonline": // 获取线上商品信息
				GetWareonline(response, htp1);
				break;
			case "setwareonlinetoagree": // 点赞
				SetWareonlinetoagree(response, htp1);
				break;

			case "getvipgroupbyid": // 通过VIPID查询会员信息
				GetVipgroupbyid(response, htp1);
				break;

			case "resetvipnamepwd": // 重置VIP账户密码
				ResetVipnamepwd(response, htp1);
				break;
			case "listwaredisplay": // 显示商品陈列
				ListWaredisplay(response, htp1);
				break;

			case "messstate":
				GetMessstate(response, htp1); // 取新消息记录数
				break;
			case "getonlinehouse": // 获取线上店铺信息
				GetOnlinehouse(response, htp1);
				break;
			case "listvipmessage": // 获取会员通知消息
				ListVipmessage(response, htp1);
				break;
			case "delvipmessage": // 删除会员通知消息
				DelVipmessage(response, htp1);
				break;
			case "listnearhouse": // 获取附近的店铺列表
				ListNearhouse(response, htp1);
				break;
			case "listhothouse": // 获取热门店铺列表
				ListHothouse(response, htp1);
				break;
			case "listviphouse": // 获取会员店铺列表
				ListViphouse(response, htp1);
				break;
			case "updateviphouse": // 更改会员店铺名称
				UpdateViphouse(response, htp1);
				break;
			case "listvipcard": // 获取我的会员卡列表
				ListVipcard(response, htp1);
				break;
			case "listonhouseseruser": // 获取客服列表（最多返回5个）
				ListOnhouseSeruser(response, htp1);
				break;
			case "listviphousenotice": // 获取会员店铺活动通知
				ListViphousenotice(response, htp1);
				break;
			case "writeviphousenotice": // 写阅读会员店铺活动通知标志
				WriteViphousenotice(response, htp1);
				break;
			case "writevipmessage": // 写阅读会员店铺消费通知标志
				WriteVipmessage(response, htp1);
				break;
			case "listvipsalenote": // 获取会员消费记录
				ListVipsalenote(response, htp1);
				break;
			// ===================================
			case "listvippairs": // 显示潮流搭配资料
				ListVippairs(response, htp1);
				break;
			case "getvippairs": // 获取潮流搭配资料
				GetVippairs(response, htp1);
				break;
			case "setvippairstoagree": // 搭配点赞
				SetVippairstoagree(response, htp1);
				break;
			// ===================================
			case "receaddlist": // 分页获取收货地址列表
				GetReceaddList(response, htp1);
				break;
			case "getreceaddbyid": // 获取指定收货地址id信息
				GetReceaddByID(response, htp1);
				break;
			case "delreceaddbyid": // 删除指定收货地址id信息
				DelReceaddByID(response, htp1);
				break;
			case "setdefreceadd": // 设置默认收货地址
				SetDefreceadd(response, htp1);
				break;
			case "getdefreceadd": // 取默认收货地址
				GetDefreceadd(response, htp1);
				break;
			case "updatereceaddbyid": // 更新指定收货地址id信息
				UpdateReceaddByID(response, htp1);
				break;
			case "addreceadd": // 新增收货地址记录
				AddReceaddRec(response, htp1);
				break;
			// ===========================================================================================
			case "addvipcollect": // 增加商品到收藏夹
				AddVipcollectRec(response, htp1);
				break;
			case "delvipcollect": // 删除收藏夹商品
				DelVipcollect(response, htp1);
				break;
			case "listvipcollect": // 显示商品收藏夹车记录
				ListVipcollect(response, htp1);
				break;

			// ===========================================================================================
			case "addbuycar": // 增加商品到购物车
				AddBuycarRec(response, htp1);
				break;
			case "delbuycar": // 删除购物车商品
				DelBuycar(response, htp1);
				break;
			case "listbuycar": // 显示商品购物车记录
				ListBuycar(response, htp1);
				break;
			case "updatebuycar": // 更改商品购物车数量
				UpdateBuycar(response, htp1);
				break;
			// ===========================================================================================
			case "listviporder": // 订单列表
				ListViporder(response, htp1);
				break;
			case "getviporder": // 获取指定订单详情
				GetViporder(response, htp1);
				break;
			case "delviporder": // 删除指定订单详情
				DelViporder(response, htp1);
				break;

			case "closeviporder": // 关闭订单列表
				CloseViporder(response, htp1);
				break;

			case "payviporder": // 订单付款
				PayViporder(response, htp1);
				break;

			case "changeviporder": // 确认,延迟收货
				ChangeViporder(response, htp1);
				break;

			case "addviporder": // 增加vip订单
				AddViporderRec(response, htp1);
				break;
			case "vipcar2order": // vip购物车转订单
				Vipcar2order(response, htp1);
				break;

			// ===========================================================================================
			case "addvipreservation": // 增加预约试衣记录
				AddVipreservationRec(response, htp1);
				break;
			case "listvipreservation": // 显示预约试衣记录(未提交)
				ListVipreservation(response, htp1, 0);
				break;
			case "listvipreservation1": // 显示预约试衣记录(已提交)
				ListVipreservation(response, htp1, 1);
				break;
			case "delvipreservation": // 删除预约试衣记录
				DelVipreservation(response, htp1);
				break;
			case "updatevipreservation": // 更改预约试衣主录
				UpdateVipreservation(response, htp1);
				break;
			case "cancelvipreservation": // 取消预约试衣主录
				CancelVipreservation(response, htp1);
				break;
			case "selectvipreservationhouse": // 选择预约门店
				SelectVipreservationhouse(response, htp1);
				break;
			case "getviptwobarcode": // 生成vip二维码
				VipTwobarcode(response, htp1);
				break;

			// ===========================================================================================
			case "serverdate":
				ServerDate(response);
				break;
			case "serverdatetime":
				ServerDateTime(response);
				break;
			case "testsaveimage": // 测试保存图片
				TestSaveimage(response, htp1);
				break;

			default:
				WriteResult(response, 0, "VIP接口未定义:" + action + "!");
				// response.getWriter().append("接口未定义:"+action+" !");
				// String jsonstr="{\"result\":0,\"msg\":\"接口 "+action+" 未定义!\"}";
				// response.getWriter().write(jsonstr);
				break;
			}
		} catch (IOException e) {
			//			e.printStackTrace();
			System.out.println(e.getMessage());
			WriteResult(response, 0, "网络异常，稍候请重试!");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	// 会员登录
	// protected void EmployeLogin()
	protected void VipLogin(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		//System.out.println("EmployeLogin begin...");

		Vipgroup dal = new Vipgroup();

		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		String mobile = jsonObject.has("mobile") ? jsonObject.getString("mobile") : "";
		String deviceno = jsonObject.has("deviceno") ? jsonObject.getString("deviceno") : "";
		String devicetype = jsonObject.has("devicetype") ? jsonObject.getString("devicetype") : "???";
		String password = jsonObject.has("password") ? jsonObject.getString("password") : "";
		dal.setMobile(mobile);
		dal.setPassword(password.toUpperCase());
		int ret = dal.Login(deviceno, devicetype);
		if (ret == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());

		}

	}

	// 新增会员账户记录
	protected void AddVipgroupRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String houseidstr = jsonObject.has("houseidstr") ? jsonObject.getString("houseidstr") : "";
		String smspwd = jsonObject.has("smspwd") ? jsonObject.getString("smspwd") : "";
		int md5 = jsonObject.has("md5") ? Integer.parseInt(jsonObject.getString("md5")) : 0;

		if (houseidstr == null || houseidstr == "") {
			houseidstr = "0";
		} else {
			houseidstr = Func.Base64Decode(houseidstr);
		}
		long onhouseid = Long.parseLong(houseidstr);

		Vipgroup dal = new Vipgroup();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setStatetag(1);

		//System.out.println("addsalecode 3");
		// if (dal.Append(onhouseid, smspwd, md5) == 0) {
		WriteResult(response, dal.Append(onhouseid, smspwd, md5), dal.getErrmess());
		// } else {
		// WriteResult(response, 1, dal.getVipid().toString());
		// }

	}

	// 获取当前用户最近登录的设备号
	protected void GetDeviceno(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {

		Table tb = new Table();
		String qry = "select DEVICENO from useronline where EPID=" + htp.getUserid();
		tb = DbHelperSQL.Query(qry).getTable(1);
		String jsonstr = "\"result\":1,\"msg\":\"操作成功！\"";

		if (tb.getRowCount() == 1) {
			jsonstr += ",\"DEVICENO\": \"" + tb.getRow(0).get("DEVICENO").toString() + "\"}";
		} else {
			jsonstr += ",\"DEVICENO\": \"\"}";
		}
		Write(response, jsonstr);
	}

	// 通过手机号查找会员id及密码
	protected void GetVipnamebymobile(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String mobile = jsonObject.has("mobile") ? jsonObject.getString("mobile") : "";
		String smspwd = jsonObject.has("smspwd") ? jsonObject.getString("smspwd") : "";
		Vipgroup dal = new Vipgroup();
		dal.setMobile(mobile);
		if (dal.Find(smspwd) == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());
		}

	}

	// 获取店铺标题横幅
	protected void ListHousebanner(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int groupid = jsonObject.has("groupid") ? Integer.parseInt(jsonObject.getString("groupid")) : 0;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		// string findbox = YTJR.COMMON.PubObj.sink("findbox", MethodType.Get);
		// int groupid = YTJR.COMMON.PubObj.sink("groupid", MethodType.Get) == "" ? 0 : int.Parse(YTJR.COMMON.PubObj.sink("groupid", MethodType.Get));

		String qry = "select a.bannerid,a.bannername,a.imagename,b.lastdate,a.onhouseid,b.onhousename ";
		qry += " from VIPbanner a join onlinehouse b on a.onhouseid=b.onhouseid ";
		qry += " where a.lx=1  and a.statetag=3 and b.statetag=1";
		qry += " and ( b.viewtag=0 or b.viewtag=1 and exists (select 1 from viphouse c where c.statetag=1 and b.onhouseid=c.onhouseid and c.vipid=" + htp.getUserid() + "))";

		if (findbox != null && !findbox.equals(""))
			qry += " and ( b.onhousename like '%" + findbox + "%' or b.shortname like '%" + findbox.toUpperCase() + "%')";
		if (groupid > 0) {
			// * groupid: 1、女装：（1） 2、男装：（2） 3、女鞋：（7） 4、男鞋：（8） 5、内衣（5、13） 6、婴童用品（3、4、9、14） 7、箱包（11） 8、配饰（6、10） 9、家纺（12）
			// *
			// * typeid:1 女装 2 男装 3 男婴童装 4 女婴童装 5 内衣裤 6 配饰 7 女鞋/靴 8 男鞋/靴 9 婴童鞋 10 珠宝首饰 11 箱包 12 家纺 13 袜子 14 婴童用品
			qry += " and exists (select 1 from online2type c where a.onhouseid=c.onhouseid ";
			if (groupid == 1)
				qry += " and  c.typeid=1 ";
			else if (groupid == 2)
				qry += " and  c.typeid=2 ";
			else if (groupid == 3)
				qry += " and  c.typeid=7";
			else if (groupid == 4)
				qry += " and  c.typeid=8";
			else if (groupid == 5)
				qry += " and  (c.typeid=5 or c.typeid=13)";
			else if (groupid == 6)
				qry += " and  (c.typeid=3 or c.typeid=4 or c.typeid=9 or c.typeid=14)";
			else if (groupid == 7)
				qry += " and  c.typeid=11";
			else if (groupid == 8)
				qry += " and  (c.typeid=6 or c.typeid=10)";
			else if (groupid == 9)
				qry += " and  c.typeid=12";
			qry += "  )";
		}

		// QueryParam qp = new QueryParam();
		// qp.setPageIndex(page);
		// qp.setPageSize(pagesize);
		// qp.setSortString("lastdate,bannerid");
		// qp.setQueryString(qry);
		QueryParam qp = new QueryParam(page, pagesize, "lastdate,bannerid", qry);

		Table tb = new Table();
		Vipbanner dal = new Vipbanner();

		tb = dal.GetTable(qp);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 获取公共标题横幅
	protected void ListPublicbanner(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;

		String qry = "select bannerid,bannername,imagename,lastdate,onhouseid,webadd from VIPbanner a where a.lx=0  and a.statetag=3";

		// QueryParam qp = new QueryParam();
		// qp.setPageIndex(page);
		// qp.setPageSize(pagesize);
		// qp.setSortString("lastdate,bannerid");
		// qp.setQueryString(qry);
		QueryParam qp = new QueryParam(page, pagesize, "lastdate,bannerid", qry);

		Table tb = new Table();
		Vipbanner dal = new Vipbanner();

		tb = dal.GetTable(qp);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	/// <summary>
	/// 获取商品一级类型 未用
	/// 参数：账号id,页，行数，排序项目，排序方式，需返回的字段列表
	/// 返回：warecode->a,waretype->b,brand->c
	/// </summary>
	protected void GetWaretype1List(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {

		String qry = "select typeid,typename from waretype where p_typeid=0 and statetag=1 and noused=0 order by typeid";
		Table tb = new Table();
		// Waretype dal = new Waretype();

		// tb = dal.GetTable(qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		Write(response, DbHelperSQL.DataTable2Json(tb));

		// int rs = dt.Rows.Count;
		// Write(YTJR.COMMON.JsonHelper.DataTable2Json(dt, rs));

	}

	// 新增会员档案记录 增加会员申请
	protected void AddGuestvipRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		if (onhouseid == 0) {
			// String onhouseidstr = YTJR.COMMON.PubObj.sink("onhouseidstr", MethodType.Get);
			String onhouseidstr = jsonObject.has("onhouseidstr") ? jsonObject.getString("onhouseidstr") : "0";
			onhouseid = Long.parseLong(Func.desDecode(onhouseidstr));
		}
		Guestvip dal = new Guestvip();

		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setVipid(htp.getUserid());
		int ret = dal.doAddGuestvip(onhouseid);

		if (ret == 1)
			WriteResultJson(response, ret, dal.getErrmess());
		else
			WriteResult(response, ret, dal.getErrmess());
	}

	// 取入会须知
	protected void GetOnhouseAttention(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		if (onhouseid == 0) {
			String onhouseidstr = jsonObject.has("onhouseidstr") ? jsonObject.getString("onhouseidstr") : "";
			try {
				onhouseid = Long.parseLong(Func.desDecode(onhouseidstr));
			} catch (Exception e2) {
				WriteResult(response, 0, "店铺id参数无效!");
				return;
			}
		}
		String qry = "select attention from onlinehouse where onhouseid=" + onhouseid;

		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 修改会员信息
	protected void UpdateVipgroupRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Vipgroup dal = new Vipgroup();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setVipid(htp.getUserid());
		if (dal.Update() < 0) {
			WriteResult(response, 0, "修改失败！");
		} else {
			WriteResult(response, 1, "修改成功！");
		}
	}

	// 更改会员注册手机
	protected void ChangeVipmobile(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String mobile = jsonObject.has("mobile") ? jsonObject.getString("mobile") : "";
		String smspwd = jsonObject.has("smspwd") ? jsonObject.getString("smspwd") : "";
		Vipgroup dal = new Vipgroup();
		dal.setVipid(htp.getUserid());
		dal.setMobile(mobile);
		WriteResult(response, dal.ChangeMob(smspwd), dal.getErrmess());

	}

	// 增加会员优惠券
	protected void AddVipcoupon(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cpid = jsonObject.has("cpid") ? Long.parseLong(jsonObject.getString("cpid")) : 0;
		Vipcoupon dal = new Vipcoupon();
		dal.setCpid(cpid);
		WriteResult(response, dal.Append(htp.getUserid()), dal.getErrmess());
	}

	// 上传会员头像logo
	protected void UploadViplogo(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		// JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Vipgroup dal = new Vipgroup();
		dal.setVipid(htp.getUserid());
		WriteResult(response, dal.Uploadlogo(htp.getPictjson()), dal.getErrmess());
	}

	// 显示会员优惠券（未用，已用，过期
	protected void ListVipcoupon(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int zt = jsonObject.has("zt") ? Integer.parseInt(jsonObject.getString("zt")) : 0;
		// zt:0=未用 1=已用 2=过期
		// String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " brandid ";
		// String order = jsonObject.has("order") ? jsonObject.getString("order") : " asc ";
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String sort = " onhouseid,overdate,cpid";

		String qry = "select a.cpid,b.curr,b.xfcurr,b.remark,to_char(b.begindate,'yyyy-mm-dd') as begindate,to_char(b.overdate,'yyyy-mm-dd') as overdate,to_char(b.lastdate,'yyyy-mm-dd') as lastdate,b.statetag,b.onhouseid,c.onhousename";
		qry += ",'满'||b.xfcurr||'元减'||b.curr||'元' as remark0";

		qry += " from vipcoupon a  ";
		qry += " join housecoupon b on a.cpid=b.cpid";
		qry += " join onlinehouse c on b.onhouseid=c.onhouseid";
		qry += " where a.vipid=" + htp.getUserid();
		if (findbox != null && !findbox.equals(""))
			qry += " and c.onhousename like '%" + findbox + "%'";

		if (zt == 0)
			qry += " and a.usetag=0 and b.statetag=1";
		else if (zt == 1)
			qry += " and a.usetag=1";
		else
			qry += " and a.usetag=0 and b.statetag=2";

		QueryParam qp = new QueryParam(page, pagesize, sort, qry);

		Table tb = new Table();
		Vipcoupon dal = new Vipcoupon();

		tb = dal.GetTable(qp);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 显示店铺未领优惠券
	protected void ListHousecoupon(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String qry = "select a.vcpid,a.cpid,a.curr,a.xfcurr,a.remark,to_char(a.begindate,'yyyy-mm-dd') as begindate,to_char(a.overdate,'yyyy-mm-dd') as overdate,to_char(a.lastdate,'yyyy-mm-dd') as lastdate,a.statetag";
		qry += ",'满'||a.xfcurr||'元减'||a.curr||'元' as remark0";
		qry += " from Housecoupon a  ";

		qry += " where a.onhouseid=" + onhouseid + " and a.statetag=1";
		qry += " and not exists (select 1 from vipcoupon b where a.cpid=b.cpid and b.vipid=" + htp.getUserid() + " )";

		String sort = " lastdate desc,cpid";
		// WriteResult("0", qry);
		QueryParam qp = new QueryParam(page, pagesize, sort, qry);
		Table tb = new Table();
		Housecoupon dal = new Housecoupon();

		tb = dal.GetTable(qp);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 显示我的店铺信息，可领优惠券，商品陈列类别
	protected void ListMyHouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Onlinehouse dal = new Onlinehouse();
		dal.setOnhouseid(onhouseid);

		int ret = dal.listMyHouse(htp.getUserid());
		if (ret == 0) {
			WriteResult(response, 0, dal.getErrmess());
			return;
		}
		WriteResultJson(response, 1, dal.getErrmess());
	}

	// 显示指定店铺信息
	protected void GetMyHouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Onlinehouse dal = new Onlinehouse();
		dal.setOnhouseid(onhouseid);

		String strwhere = "  onhouseid=" + onhouseid;// +" and statetag=1";
		String fieldlist = "onhouseid,onhousename,imagename,accid,remark,tel,address";

		Table tb = new Table();
		tb = dal.GetList(strwhere, fieldlist).getTable(1);

		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 获取线上商店商品信息
	protected void GetWareonline(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		Wareonline dal = new Wareonline();

		dal.setWareid(wareid);
		dal.setOnhouseid(onhouseid);
		int ret = dal.getWareonline(htp.getUserid());
		if (ret == 0) {
			WriteResult(response, 0, dal.getErrmess());
			return;
		}
		WriteResultJson(response, 1, dal.getErrmess());

	}

	// 商品点赞
	protected void SetWareonlinetoagree(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		int agreeid = jsonObject.has("agreeid") ? Integer.parseInt(jsonObject.getString("agreeid")) : 0;
		// 0=取消赞，1=点赞
		Wareonlineagree dal = new Wareonlineagree();
		dal.setOnhouseid(onhouseid);
		dal.setWareid(wareid);
		dal.setVipid(htp.getUserid());

		// if (dal.Append(agreeid) == 0) {
		// WriteResult(response, 0, "增加失败！");
		// } else {
		WriteResult(response, dal.Append(agreeid), dal.getErrmess()); // 返回点赞数
		// }

	}

	// 通过VIPID查找会员信息
	protected void GetVipgroupbyid(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		// JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Vipgroup dal = new Vipgroup();

		String qry = "select vipid,vipname,nickname,sex,mobile,imagename,password ";
		qry += ",(select address from receiptaddress b where a.vipid=b.vipid and b.bj=1 and rownum=1) as  address";
		qry += " from vipgroup a where vipid='" + htp.getUserid() + "'";

		Table tb = new Table();
		tb = dal.GetList(qry).getTable(1);

		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 重设VIP账户密码
	protected void ResetVipnamepwd(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long vipid = jsonObject.has("vipid") ? Long.parseLong(jsonObject.getString("vipid")) : 0;
		String pwd = jsonObject.has("pwd") ? jsonObject.getString("pwd") : "";
		String oldpwd = jsonObject.has("oldpwd") ? jsonObject.getString("oldpwd") : "";
		Vipgroup dal = new Vipgroup();
		dal.setVipid(vipid);
		dal.setPassword(pwd.toUpperCase()); // 新密码
		if (dal.Changepwd(oldpwd.toUpperCase()) < 0) {
			WriteResult(response, 0, "操作失败!");
		} else {
			WriteResult(response, 1, "操作成功!");
		}
	}

	// 显示陈列商品列表
	protected void ListWaredisplay(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		long dspid = jsonObject.has("dspid") ? Long.parseLong(jsonObject.getString("dspid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		Wareonline dal = new Wareonline();
		String strwhere = " A.onhouseid=" + onhouseid + " and a.uptag=1";
		if (dspid > 0)
			strwhere += " AND exists (select 1 from WAREDISPLAY c where a.wareid=c.wareid and c.DSPID=" + dspid + ")";
		String fieldlist = " A.WAREID,A.REMARK AS WARENAME,A.SALEPRICE,B.RETAILSALE,B.WARENO,B.UNITS ,A.LASTDATE,B.IMAGENAME0";
		String sort = "LASTDATE DESC,ROWID";
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 获取消息记录数
	protected void GetMessstate(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		long userid = htp.getUserid();
		String qry = "select ";
		qry += " (select count(*) from vipmessage where statetag=1 and jg<3 and rdbj=0 and vipid=" + userid + ") as tznum"; // --新通知
		qry += "  ,( select count(*) from viphousenotice a where a.statetag=1 and not exists (select 1 from vipreadhousenotice b where  a.hnid=b.hnid and b.vipid=" + userid + ")";
		qry += "  and exists (select 1 from viphouse c where a.onhouseid=c.onhouseid and c.vipid=" + userid + ")  ) as ggnum"; // --新公告
		qry += " from dual";
		// WriteLogTXT("debug", "GetMessstate", qry);
		// WriteResult("0", qry);
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 取线上店铺信息
	protected void GetOnlinehouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = " onhouseid,onhousename,attention";
		String strwhere = "   onhouseid=" + onhouseid;// +" and statetag=1";
		Onlinehouse dal = new Onlinehouse();
		Table tb = new Table();
		tb = dal.GetList(strwhere, fieldlist).getTable(1);

		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 获取会员通知消息列表
	protected void ListVipmessage(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int rdbj = jsonObject.has("rdbj") ? Integer.parseInt(jsonObject.getString("rdbj")) : 2;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String strwhere = " a.statetag=1 and a.vipid=" + htp.getUserid() + " and a.jg<3";
		if (rdbj < 2)
			strwhere += " and a.rdbj=" + rdbj;

		String fieldlist = "a.messid,a.lastdate,a.remark,a.rdbj,a.onhouseid,b.onhousename,a.lastop,b.imagename,a.lx,a.jg";

		String sort = "lastdate desc,messid";
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		Vipmessage dal = new Vipmessage();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 删除通知消息
	protected void DelVipmessage(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Float messid = jsonObject.has("messid") ? Float.parseFloat(jsonObject.getString("messid")) : 0;
		Vipmessage dal = new Vipmessage();
		dal.setMessid(messid);
		dal.setLastop(htp.getUsername());
		if (dal.Delete() == 0) {
			WriteResult(response, 0, "删除失败!");
		} else {
			WriteResult(response, 1, "删除成功!");
		}
	}

	protected void ListNearhouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Float px = jsonObject.has("px") ? Float.parseFloat(jsonObject.getString("px")) : 0;
		Float py = jsonObject.has("py") ? Float.parseFloat(jsonObject.getString("py")) : 0;
		Float distance = jsonObject.has("distance") ? Float.parseFloat(jsonObject.getString("distance")) : 0;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String qry = "select b.onhouseid,b.onhousename,b.imagename,a.housename,a.address,a.tel";
		// String qry = "select b.onhouseid,b.imagename,a.housename";
		qry += ",F_Distance0(" + px + "," + py + ",lx,ly) as distance,to_char(lx) as lx,to_char(ly) as ly ";
		qry += " from warehouse a join onlinehouse b on a.onhouseid=b.onhouseid";
		qry += " where a.lx>0 and a.ly>0 and length(b.imagename)>0 and b.statetag=1 ";

		// String qry=" select houseid,housename from warehouse where stateag=1";
		if (!findbox.equals(""))
			qry += " and b.onhousename like '%" + findbox + "%'";
		if (distance > 0)
			qry += " and F_Distance0(" + px + "," + py + ",lx,ly)<" + distance;
		qry += " order by F_Distance0(" + px + "," + py + ",lx,ly)";
		//System.out.println("aaa: "+qry);
		String sort = " distance,onhouseid ";
		// String sort = " houseid ";
		QueryParam qp = new QueryParam(page, pagesize, sort, qry);
		Table tb = new Table();
		//System.out.println("ListNearhouse1");
		tb = DbHelperSQL.GetTable(qp);
		//System.out.println("ListNearhouse2。3");
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 搜索热门铺
	protected void ListHothouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = "a.onhouseid,a.onhousename,a.imagename,a.agreeview,a.pageview";
		String strwhere = "a.statetag=1 and length(a.imagename)>0";
		strwhere += " and not exists (select 1 from viphouse b where b.vipid=" + htp.getUserid() + " and a.onhouseid=b.onhouseid and b.statetag=1)";
		if (!findbox.equals(""))
			strwhere += " and a.onhousename like '%" + findbox + "%'";
		String sort = " pageview desc,onhouseid ";
		// QueryParam qp = new QueryParam();
		Onlinehouse dal = new Onlinehouse();
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere, fieldlist);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 获取会员店铺记录
	protected void ListViphouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = "a.onhouseid,a.housename0,b.onhousename,b.imagename,b.statetag";
		String strwhere = " a.statetag=1 and a.vipid=" + htp.getUserid();
		String sort = "onhouseid ";

		// QueryParam qp = new QueryParam();
		Viphouse dal = new Viphouse();
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere, fieldlist);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 更改会员店铺名称
	protected void UpdateViphouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		String onhousename = jsonObject.has("onhousename") ? jsonObject.getString("onhousename") : "";
		Viphouse dal = new Viphouse();
		dal.setOnhouseid(onhouseid);
		dal.setHousename0(onhousename);
		if (dal.Update(htp.getUserid()) == 0) {
			WriteResult(response, 0, "操作失败！");

		} else {
			WriteResult(response, 0, "操作成功！");

		}
	}

	// 获取我的会员卡列表
	protected void ListVipcard(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String qry = "select c.guestid,c.vipno,a.onhouseid,b.onhousename,b.address,b.tel,b.imagename,c.validdate,c.balcent,c.balcurr,c.lastdate,d.vtname";
		qry += " from viphouse a";
		qry += " join onlinehouse b on a.onhouseid=b.onhouseid";
		qry += " left outer join guestvip c on a.vipid=c.vipid and c.accid=b.accid";
		qry += " join guesttype d on c.vtid=d.vtid";
		qry += " where a.statetag=1 and a.vipid=" + htp.getUserid();

		String sort = " lastdate desc,guestid ";
		QueryParam qp = new QueryParam(page, pagesize, sort, qry);
		Table tb = new Table();
		tb = DbHelperSQL.GetTable(qp);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 获取线上店铺客服人员列表
	protected void ListOnhouseSeruser(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		String qry = "select epid,epname,epno,imagename,bj from (";
		qry += "\n select a.epid,a.epname,a.epno,a.imagename,case when b.epid is null then 1 else 0 end as bj";
		qry += "\n from employe a left outer join useronline b on a.epid=b.epid";
		qry += "\n where a.statetag=1 and a.levelid>0";
		qry += "\n and exists (select 1 from online2user b where a.epid=b.epid and b.onhouseid=" + onhouseid + " and substr(b.xxx,7,1)='1')";
		qry += "\n order by bj ) ";
		qry += "\n where rownum<=5";

		Table tb = new Table();

		tb = DbHelperSQL.Query(qry).getTable(1);
		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 获取会员店铺活动通知记录
	protected void ListViphousenotice(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int rdbj = jsonObject.has("rdbj") ? Integer.parseInt(jsonObject.getString("rdbj")) : 0;
		// rdbj:0=未读 1=已读
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		long userid = htp.getUserid();
		String qry = "";
		if (rdbj == 0) {
			qry = " select a.hnid,a.notedate,a.onhouseid,b.onhousename,a.topical,a.content,b.imagename,0 as rdbj ";
			qry += " from VIPHOUSEnotice a ";
			qry += " left outer join onlinehouse b on a.onhouseid=b.onhouseid";
			qry += " where a.statetag=1 and exists (select 1 from viphouse c where a.onhouseid=c.onhouseid and c.vipid=" + userid + ")";
			qry += " and not exists (select 1 from VIPreadHOUSEnotice d where a.hnid=d.hnid and d.vipid=" + userid + ")";
		} else if (rdbj == 1) {
			qry = "select a.hnid,a.notedate,a.onhouseid,b.onhousename,a.topical,a.content,b.imagename,1 as rdbj ";
			qry += " from VIPHOUSEnotice a ";
			qry += " left outer join onlinehouse b on a.onhouseid=b.onhouseid";
			qry += " where a.statetag=1 and exists (select 1 from viphouse c where a.onhouseid=c.onhouseid and c.vipid=" + userid + ")";
			qry += " and exists (select 1 from VIPreadHOUSEnotice d where a.hnid=d.hnid and d.vipid=" + userid + ")";
		} else {
			qry = "select a.hnid,a.notedate,a.onhouseid,b.onhousename,a.topical,a.content,b.imagename,1 as rdbj ";
			qry += " from VIPHOUSEnotice a ";
			qry += " left outer join onlinehouse b on a.onhouseid=b.onhouseid";
			qry += " where a.statetag=1 and exists (select 1 from viphouse c where a.onhouseid=c.onhouseid and c.vipid=" + userid + ")";
			qry += " and exists (select 1 from VIPreadHOUSEnotice d where a.hnid=d.hnid and d.vipid=" + userid + ")";
			qry += " union all";
			qry += " select a.hnid,a.notedate,a.onhouseid,b.onhousename,a.topical,a.content,b.imagename,0 as rdbj ";
			qry += " from VIPHOUSEnotice a ";
			qry += " left outer join onlinehouse b on a.onhouseid=b.onhouseid";
			qry += " where a.statetag=1 and exists (select 1 from viphouse c where a.onhouseid=c.onhouseid and c.vipid=" + userid + ")";
			qry += " and not exists (select 1 from VIPreadHOUSEnotice d where a.hnid=d.hnid and d.vipid=" + userid + ")";
		}
		String sort = "notedate desc";
		QueryParam qp = new QueryParam(page, pagesize, sort, qry);
		Table tb = new Table();
		tb = DbHelperSQL.GetTable(qp);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 写阅读公告标志
	protected void WriteViphousenotice(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		float hnid = jsonObject.has("hnid") ? Float.parseFloat(jsonObject.getString("hnid")) : 0;
		Vipreadhousenotice dal = new Vipreadhousenotice();
		dal.setHnid(hnid);
		if (dal.Append(htp.getUserid()) == 0) {
			WriteResult(response, 0, "操作失败！");
		} else {
			WriteResult(response, 1, "操作成功！");
		}
	}

	// 写会员消息标志
	protected void WriteVipmessage(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		float messid = jsonObject.has("messid") ? Float.parseFloat(jsonObject.getString("messid")) : 0;
		int jg = jsonObject.has("jg") ? Integer.parseInt(jsonObject.getString("jg")) : 0;
		// jg:1=同意，2=拒绝
		Vipmessage dal = new Vipmessage();
		dal.setMessid(messid);

		WriteResult(response, dal.Update(jg), dal.getErrmess());

	}

	// 取会员消费明细
	protected void ListVipsalenote(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		long vipid = htp.getUserid();

		// qry += " )";

		QueryParam qp = new QueryParam(page, pagesize);
		// Table tb = new Table();
		Vipgroup dal = new Vipgroup();
		dal.setVipid(vipid);

		// tb = DbHelperSQL.GetTable(qp);
		if (dal.doListVipsalenote(qp, onhouseid) == 0)
			WriteResult(response, 0, dal.getErrmess());
		else
			Write(response, dal.getErrmess());

	}

	// 显示潮流搭配
	protected void ListVippairs(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		long vipid = htp.getUserid();
		String qry = "select a.paid,a.patitle,a.content,a.remark,a.styletag,a.statetag,b.lastdate,a.onhouseid,a.imagename as pairimagename,b.onhousename,b.imagename,a.imgheight0 ";
		qry += ",a.pageview,a.agreeview";
		if (vipid == 0)
			qry += ",0 as isagree";
		else
			qry += ",nvl( (select 1 from vippairsagree c where c.paid=a.paid and c.vipid=" + vipid + "),0) as isagree"; // 收藏

		qry += " from VIPpairs a join onlinehouse b on a.onhouseid=b.onhouseid ";
		qry += " where a.statetag=1 and length(a.imagename)>0 and b.statetag=1";
		if (!findbox.equals(""))
			qry += " and (a.patitle like '%" + findbox + "%' or b.onhousename like  '%" + findbox + "%')";

		Vippairs dal = new Vippairs();
		String sort = "lastdate  desc,rowid";
		QueryParam qp = new QueryParam(page, pagesize, sort, qry);
		Table tb = new Table();
		tb = dal.GetTable(qp);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 获取潮流搭配
	protected void GetVippairs(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long paid = jsonObject.has("paid") ? Long.parseLong(jsonObject.getString("paid")) : 0;
		Vippairs dal = new Vippairs();
		String strwhere = " paid=" + paid;
		long vipid = htp.getUserid();
		String fieldlist = " paid,patitle,content,remark,styletag,statetag,onhouseid,agreeview,pageview ";
		if (vipid == 0)
			fieldlist += ",0 as isagree";
		else
			fieldlist += ",nvl( (select 1 from vippairsagree c where c.paid=a.paid and c.vipid=" + vipid + "),0) as isagree"; // 收藏
		Table tb = new Table();
		tb = dal.GetList(strwhere, fieldlist).getTable(1);
		if (tb.getRowCount() == 0) {
			// WriteResult("0", "未找到搭配！");
			WriteResult(response, 0, "未找到搭配！");
			return;
		}
		String retcs = "\"msg\":\"操作成功！\",\"PAID\":\"" + tb.getRow(0).get("PAID").toString() + "\"" + ",\"PATITLE\":\"" + tb.getRow(0).get("PATITLE").toString() + "\"" + ",\"CONTENT\":\""
				+ tb.getRow(0).get("CONTENT").toString() + "\"" + ",\"REMARK\":\"" + tb.getRow(0).get("REMARK").toString() + "\"" + ",\"STATETAG\":\"" + tb.getRow(0).get("STATETAG").toString() + "\"" + ",\"STYLETAG\":\""
				+ tb.getRow(0).get("STYLETAG").toString() + "\"" + ",\"ONHOUSEID\":\"" + tb.getRow(0).get("ONHOUSEID").toString() + "\"" + ",\"ISAGREE\":\"" + tb.getRow(0).get("ISAGREE").toString() + "\"";

		String qry1 = "select a.ID,a.WAREID,b.IMAGENAME0,b.retailsale,a.ORDID,a.REMARK0 from VIPPAIRS1 a join warecode b on a.wareid=b.wareid where a.PAID= " + paid + " order by a.ORDID,a.ID";
		Table tb1 = DbHelperSQL.Query(qry1).getTable(1);
		int count1 = tb1.getRowCount();
		retcs += ",\"imagelist\":[";
		String fh1 = "";
		for (int j = 0; j < count1; j++) {
			retcs += fh1 + "{\"IMGID\":\"" + tb1.getRow(j).get("ID").toString() + "\"" + ",\"WAREID\":\"" + tb1.getRow(j).get("WAREID").toString() + "\"" + ",\"IMAGENAME0\":\""
					+ tb1.getRow(j).get("IMAGENAME0").toString() + "\"" + ",\"RETAILSALE\":\"" + tb1.getRow(j).get("RETAILSALE").toString() + "\"" + ",\"IMGREMARK\":\"" + tb1.getRow(j).get("REMARK0").toString() + "\"}";
			fh1 = ",";
		}
		// fh = ",";
		retcs += "]";
		WriteResultJson(response, 1, retcs);
	}

	// 搭配点赞
	protected void SetVippairstoagree(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long paid = jsonObject.has("paid") ? Long.parseLong(jsonObject.getString("paid")) : 0;
		int agreeid = jsonObject.has("agreeid") ? Integer.parseInt(jsonObject.getString("agreeid")) : 0;
		Vippairsagree dal = new Vippairsagree();
		dal.setPaid(paid);
		dal.setVipid(htp.getUserid());

		WriteResult(response, dal.Update(agreeid), dal.getErrmess());

	}

	// 获取收货人列表
	protected void GetReceaddList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		// String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " id ";
		// String order = jsonObject.has("order") ? jsonObject.getString("order") : " asc ";
		String sort = " bj desc,id ";
		if (fieldlist.equals("*"))
			fieldlist = "ID,VIPID,LINKMAN,TEL,ADDRESS,STATETAG,LASTDATE,BJ";
		String strwhere = " statetag=1 and vipid=" + htp.getUserid();

		if (fieldlist.equals("*") || fieldlist.equals(""))
			strwhere += " and (linkman like '%" + findbox + "%' or tel like '%" + findbox + "%' or address like '%" + findbox + "%')";

		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		Receiptaddress dal = new Receiptaddress();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 获取指定收货地址id信息
	protected void GetReceaddByID(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		long id = jsonObject.has("id") ? Long.parseLong(jsonObject.getString("id")) : 0;
		// if (id == 0) {
		// WriteResult(response, 0, "品牌id参数无效");
		// return;
		// }

		String strwhere = "  id=" + id;// +" and statetag=1";
		strwhere += " and vipid=" + htp.getUserid();
		if (fieldlist.equals("*"))
			fieldlist = "ID,VIPID,LINKMAN,TEL,ADDRESS,STATETAG,LASTDATE,BJ";

		Receiptaddress dal = new Receiptaddress();
		Table tb = new Table();
		tb = dal.GetList(strwhere, fieldlist).getTable(1);

		Write(response, DbHelperSQL.DataTable2Json(tb));
	}

	// 删除指定收货地址id信息
	protected void DelReceaddByID(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		float id = jsonObject.has("id") ? Float.parseFloat(jsonObject.getString("id")) : 0;
		if (id == 0) {
			WriteResult(response, 0, "id参数无效");
			return;
		}
		Receiptaddress dal = new Receiptaddress();
		dal.setId(id);
		// dal.setLastop(htp.getUsername());
		dal.setVipid(htp.getUserid());
		// dal.setAccbegindate(htp.getAccdate());
		if (dal.Delete() == 1) {
			WriteResult(response, 1, "删除成功！");
		} else {
			WriteResult(response, 0, dal.getErrmess());
		}
	}

	// 设置默认收货地址
	protected void SetDefreceadd(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		float id = jsonObject.has("id") ? Float.parseFloat(jsonObject.getString("id")) : 0;
		if (id == 0) {
			WriteResult(response, 0, "id参数无效");
			return;
		}
		Receiptaddress dal = new Receiptaddress();
		dal.setId(id);
		dal.setVipid(htp.getUserid());
		if (dal.DefaultAdd() == 1) {
			WriteResult(response, 1, "操作成功！");
		} else {
			WriteResult(response, 0, "操作失败！");
		}
	}

	// 取默认收货地址
	protected void GetDefreceadd(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		// JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String strwhere = " vipid=" + htp.getUserid() + " and bj=1 ";
		String fieldlist = "ID,VIPID,LINKMAN,TEL,ADDRESS";

		Receiptaddress dal = new Receiptaddress();
		Table tb = new Table();
		tb = dal.GetList(strwhere, fieldlist).getTable(1);

		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 更新指定收货id信息
	protected void UpdateReceaddByID(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Receiptaddress dal = new Receiptaddress();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setVipid(htp.getUserid());

		if (dal.Update() == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResult(response, 1, "修改成功！");
		}

	}

	// 新增收货地址记录
	protected void AddReceaddRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Receiptaddress dal = new Receiptaddress();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setVipid(htp.getUserid());
		dal.setStatetag(1);
		if (dal.Append() == 0) {
			WriteResult(response, 0, "增加失败！");
		} else {
			WriteResult(response, 1, dal.getErrmess());
		}
	}

	// 增加商品到收藏夹
	protected void AddVipcollectRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		Vipcollect dal = new Vipcollect();
		dal.setVipid(htp.getUserid());
		dal.setOnhouseid(onhouseid);
		dal.setWareid(wareid);

		// if (dal.Append() == 0) {
		// WriteResult(response, 0, "增加失败！");
		// } else {
		WriteResult(response, dal.Append(), dal.getErrmess());
		// }
	}

	// 删除收藏夹商品
	protected void DelVipcollect(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Vipcollect dal = new Vipcollect();
		dal.setVipid(htp.getUserid());
		dal.setOnhouseid(onhouseid);
		// data={"flyang":"20150107","onhouseid":2434,"warelist":[{"wareid":3243},{"wareid":3561},{"wareid":445}]}
		JSONArray jsonArray = jsonObject.getJSONArray("warelist");
		// if ( == 0) {
		// WriteResult(response, 0, "增加失败！");
		// } else {
		WriteResult(response, dal.Delete(jsonArray), dal.getErrmess());
		// }
	}

	// 显示我的收藏夹商品
	protected void ListVipcollect(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String strwhere = " a.vipid=" + htp.getUserid();
		String fieldlist = "a.onhouseid,b.onhousename,a.wareid,c.warename,c.wareno,c.units,c.retailsale,c.imagename0,b.imagename,a.id,a.lastdate";
		String sort = " onhouseid,id desc ";
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		Vipcollect dal = new Vipcollect();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 增加商品到购物车
	protected void AddBuycarRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Buycar dal = new Buycar();
		dal.setVipid(htp.getUserid());
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);

		// if (dal.Append() == 0) {
		// WriteResult(response, 0, "增加失败！");
		// } else {
		WriteResult(response, dal.Append(), dal.getErrmess());
		// }
	}

	// 删除购物车商品
	protected void DelBuycar(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		float id = jsonObject.has("id") ? Float.parseFloat(jsonObject.getString("id")) : 0;
		Buycar dal = new Buycar();
		dal.setVipid(htp.getUserid());
		dal.setId(id);
		// JSONArray jsonArray = jsonObject.getJSONArray("warelist");

		WriteResult(response, dal.Delete(), dal.getErrmess());
	}

	// 显示我的购物车商品
	protected void ListBuycar(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String strwhere = " a.vipid=" + htp.getUserid();
		// String fieldlist = "a.onhouseid,b.onhousename,a.wareid,c.warename,c.units,c.retailsale";

		String fieldlist = "a.onhouseid,b.onhousename,a.wareid,c.warename,c.units,c.retailsale,a.colorid,d.colorname,a.sizeid,e.sizename";
		fieldlist += ",a.amount,a.price,a.curr,a.remark0,c.imagename0,b.imagename,a.id,a.lastdate,c.wareremark";
		fieldlist += ",f_cvipisbvip(a.vipid,a.onhouseid) as isvip";

		String sort = " onhouseid,id ";
		QueryParam qp = new QueryParam(page, pagesize, sort);
		qp.setSumString("nvl(sum(amount),0) as totalamt,nvl(sum(curr),0) as totalcurr");
		Table tb = new Table();
		Vipbuycar dal = new Vipbuycar();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 更改我的购物车商品数量
	protected void UpdateBuycar(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		float id = jsonObject.has("id") ? Float.parseFloat(jsonObject.getString("id")) : 0;
		Float amount = jsonObject.has("amount") ? Float.parseFloat(jsonObject.getString("amount")) : 0;
		Vipbuycar dal = new Vipbuycar();
		dal.setVipid(htp.getUserid());
		dal.setId(id);
		dal.setAmount(amount);
		if (dal.Update() == 0) {
			WriteResult(response, 0, "操作失败！");
		} else {
			WriteResult(response, 1, "操作成功！");
		}

	}

	// 显示VIP订单列表
	protected void ListViporder(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		int cxzt = jsonObject.has("cxzt") ? Integer.parseInt(jsonObject.getString("cxzt")) : 0;
		// cxzt:0=待付款，1=待发货，2=待收货，3=签收（已完成）
		// statetag:0=草稿 1=提交 2=付款，3=发货，4=签收
		String strwhere = " a.vipid=" + htp.getUserid();
		// 0=草稿 1=提交 2=付款，3=发货，4=签收，5=退款，6=关闭,7=删除
		if (cxzt == 0)
			strwhere += " and a.statetag<=1";
		else if (cxzt == 1)
			strwhere += " and a.statetag=2";
		else if (cxzt == 2)
			strwhere += " and a.statetag=3";
		else if (cxzt == 3)
			strwhere += " and a.statetag=4";

		String fieldlist = "a.id,a.noteno,a.onhouseid,b.onhousename,a.statetag,a.totalamt,a.totalcurr,a.curryf,a.remark";
		fieldlist += " ,to_char(a.notedate,'yyyy-mm-dd') as notedate,to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss') as notedatetime";

		String sort = " notedate desc,id ";
		Viporderh dal = new Viporderh();
		dal.setVipid(htp.getUserid());
		QueryParam qp = new QueryParam(page, pagesize, sort);

		// Table tb = new Table();
		String jsonstr = dal.GetTableJson(qp, strwhere, fieldlist);
		Write(response, jsonstr);

	}

	// 、、获取订单详情
	protected void GetViporder(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		if (noteno.equals("")) {
			WriteResult(response, 0, "订单号参数为空");
			return;
		}
		Viporderh dal = new Viporderh();
		dal.setVipid(htp.getUserid());
		dal.setNoteno(noteno);

		int ret = dal.GetOrderInfo();
		if (ret == 0) {
			WriteResult(response, 0, dal.getErrmess());
			return;

		}
		WriteResultJson(response, 1, dal.getErrmess());

	}

	// 删除订单
	protected void DelViporder(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		if (noteno.equals("")) {
			WriteResult(response, 0, "订单号参数为空");
			return;
		}
		Viporderh dal = new Viporderh();
		dal.setVipid(htp.getUserid());
		dal.setNoteno(noteno);
		WriteResult(response, dal.Delete(), dal.getErrmess());
	}

	// 关闭VIP订单
	protected void CloseViporder(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		Viporderh dal = new Viporderh();
		dal.setVipid(htp.getUserid());
		dal.setNoteno(noteno);
		WriteResult(response, dal.Close(), dal.getErrmess());
	}

	// VIP订单付款
	protected void PayViporder(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int paytag = jsonObject.has("paytag") ? Integer.parseInt(jsonObject.getString("paytag")) : 0;
		float paycurr = jsonObject.has("paycurr") ? Float.parseFloat(jsonObject.getString("paycurr")) : 0;

		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		if (noteno.equals("")) {
			WriteResult(response, 0, "订单号参数为空");
			return;
		}
		Viporderh dal = new Viporderh();
		dal.setVipid(htp.getUserid());
		dal.setPaycurr(paycurr);
		dal.setNoteno(noteno);
		WriteResult(response, dal.Pay(paytag), dal.getErrmess());
	}

	// VIP订单确认及延迟收货
	protected void ChangeViporder(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		// if (noteno.equals("")) {
		// WriteResult(response, 0, "订单号参数为空");
		// return;
		// }
		int opid = jsonObject.has("opid") ? Integer.parseInt(jsonObject.getString("opid")) : 0;
		// String arrivedate = jsonObject.has("arrivedate") ? jsonObject.getString("arrivedate") : "";
		// opid:0=延迟收货，1=确认收货,2=退款
		// 付款方式paytag:1=现金，2=银联卡，3=支付宝，4=微信
		// statetag:0=草稿 1=提交 2=付款，3=发货，4=签收，5=申请退款，6=退款成功，7=退款拒绝，9=关闭
		Viporderh dal = new Viporderh();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setVipid(htp.getUserid());
		// dal.setArrivedate(arrivedate);
		// dal.setPaycurr(paycurr);
		// dal.setNoteno(noteno);
		WriteResult(response, dal.Take(opid), dal.getErrmess());

	}

	// 增加vip订单
	protected void AddViporderRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		Viporderh dal = new Viporderh();
		Viporderm dal1 = new Viporderm();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		DbHelperSQL.JsonConvertObject(dal1, jsonObject);
		dal.setVipid(htp.getUserid());

		// if (dal.Append(dal1) == 0) {
		// WriteResult(response, 0, dal.getErrmess());
		//
		// return;
		// }

		WriteResult(response, dal.Append(dal1), dal.getErrmess());

	}

	// vip购物车转订单
	protected void Vipcar2order(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Viporderh dal = new Viporderh();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);

		dal.setVipid(htp.getUserid());
		// if (!jsonObject.has("warereclist")) {
		// WriteResult(response, 0, "记录数组warereclist为空");
		// return;
		// }
		// JSONArray jsonArray = jsonObject.getJSONArray("warereclist");
		// "warereclist":[{"recid":343},{"recid":654},{"recid":865}]

		WriteResult(response, dal.Vipcar2order(jsonObject), dal.getErrmess());

	}

	// 增加预约试衣记录
	protected void AddVipreservationRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		Vipreservation dal = new Vipreservation();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);

		dal.setVipid(htp.getUserid());

		WriteResult(response, dal.Append(), dal.getErrmess());
	}

	// 显示预约试衣记录
	protected void ListVipreservation(HttpServletResponse response, HttpInfo htp, int cxfs) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String qry = "select a.onhouseid,b.onhousename,a.wareid,c.warename,c.units,c.retailsale,a.colorid,d.colorname,a.sizeid,e.sizename";
		qry += ",a.remark0,a.id,a.lastdate,b.imagename,c.imagename0,f.remark as wareremark";
		if (cxfs == 1)
			qry += ",a.arrdate,f.housename";
		qry += " from Vipreservation a";
		qry += " left outer join onlinehouse b on a.onhouseid=b.onhouseid";
		qry += " join warecode c on a.wareid=c.wareid";
		qry += " join colorcode d on a.colorid=d.colorid";
		qry += " join sizecode e on a.sizeid=e.sizeid";
		qry += " join wareonline f on a.wareid=f.wareid and a.onhouseid=f.onhouseid";
		if (cxfs == 1)
			qry += " left outer join warehouse f on a.houseid=f.houseid";
		qry += " where a.vipid=" + htp.getUserid();
		if (cxfs == 0)
			qry += " and a.statetag=0";
		else
			qry += " and a.statetag=1";
		String sort = " onhouseid,id ";

		QueryParam qp = new QueryParam(page, pagesize, sort, qry);
		Table tb = new Table();
		Vipreservation dal = new Vipreservation();
		tb = dal.GetTable(qp);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 删除预约试衣记录
	protected void DelVipreservation(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		float id = jsonObject.has("id") ? Float.parseFloat(jsonObject.getString("id")) : 0;
		Vipreservation dal = new Vipreservation();
		dal.setId(id);
		dal.setVipid(htp.getUserid());

		WriteResult(response, dal.Delete(), dal.getErrmess());

	}

	// 更改预约试衣
	protected void UpdateVipreservation(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Vipreservation dal = new Vipreservation();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setVipid(htp.getUserid());

		WriteResult(response, dal.Update(), dal.getErrmess());

	}

	// 取消预约试衣记录
	protected void CancelVipreservation(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		float id = jsonObject.has("id") ? Float.parseFloat(jsonObject.getString("id")) : 0;
		Vipreservation dal = new Vipreservation();
		dal.setVipid(htp.getUserid());
		dal.setId(id);
		WriteResult(response, dal.Cancel(), dal.getErrmess());

	}

	// 选择预约试衣门店
	protected void SelectVipreservationhouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		String fieldlist = " houseid,housename,address,tel";
		String strwhere = "statetag=1 and noused=0 and onhouseid=" + onhouseid;
		String sort = " houseid ";

		QueryParam qp = new QueryParam(page, pagesize, sort);
		Table tb = new Table();
		Warehouse dal = new Warehouse();
		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 上传操作日志
	protected void UploadLogmess(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String messstr = jsonObject.has("messstr") ? jsonObject.getString("messstr") : "";
		// String title = jsonObject.has("title") ? jsonObject.getString("title") : "";
		if (messstr.length() > 0) {
			LogUtils.LogWrite(htp.getUsername() + "userid=" + htp.getUserid(), messstr, "run");
			WriteResult(response, 1, "操作成功！");
		}
		//			if (LogUtils.LogWrite(htp.getUsername()+ "userid=" + htp.getUserid(), messstr, "run") == 0)
		//				WriteResult(response, 0, "操作异常！");
		//			else
		//				WriteResult(response, 1, "操作成功！");
	}

	// 生成VIP二维码
	protected void VipTwobarcode(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		// JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long userid = htp.getUserid();

		Vipgroup dal = new Vipgroup();
		dal.setVipid(htp.getUserid());
		WriteResult(response, dal.doVipTwobarcode(), dal.getErrmess());

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

	/// <summary>
	/// 测试保存图片test.jpg
	/// </summary>
	protected void TestSaveimage(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {

		// String ss = ;
		//System.out.println("0:" + ss);
		String ss = Func.Base64UpFastDFS(Func.testImagetobase64());
		//System.out.println("1:" + ss);
		if (ss.substring(0, 1).equals("1")) {
			String fileName = ss.substring(1);
			WriteResult(response, 1, fileName);

			if (Func.fastDFSDelete(fileName) == 1)
				WriteResult(response, 1, "删除文件成功!");
			else
				WriteResult(response, 0, "删除文件失败!");

		} else
			WriteResult(response, 0, ss.substring(1));

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
