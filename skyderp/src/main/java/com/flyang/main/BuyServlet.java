package com.flyang.main;

//import java.io.File;
import java.io.IOException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.comdot.data.Table;
import com.common.tool.Func;
import com.common.tool.LogUtils;
import com.flyang.yky.FYApiClientUtils;
//import com.common.tool.TwoBarCode;
import com.oracle.bean.*;
import com.netdata.db.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 会员,会员商城，采购商城，采购后台接口，销售计划，账本,促销方案
 * 
 */
@WebServlet("/buy")
public class BuyServlet extends HttpServlet {

	private static final long serialVersionUID = 5460440847955057519L;

	// private static final long serialVersionUID = 1L;
	// private static String filePath = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BuyServlet() {
		super();
		// filePath = Func.getRootPath();// +"skyderp.config/";
		// System.out.println("BuyServlet is ready ok! ");
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
		// LogUtils.LogWrite("aaa","111", "1111");

		String url = request.getRequestURL() + "?" + request.getQueryString();
		// LogUtils.LogWrite("buy调用", "", url + " post:" +
		// request.getParameterMap().toString());

		String action = request.getParameter("action").toLowerCase();

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
		int ret = htp1.setSignjson(signjson, 0);

		String onlogaction = "^serverdate^serverdatetime^path^test^"; // 不记录日志的接口名
		if (!onlogaction.contains("^" + action + "^")) {
			// 接口调用记录
			LogUtils.LogWrite(htp1.getUsername() + " " + htp1.getIpaddress(), htp1.getUrl());
		}

		if (ret == 0)// 校验参数是否有效
		{
			Write(response, htp1.getErrmess());
			return;
		}

		String ss = Func.HttpIsDanger(htp1.getUrl());
		if (!ss.equals("")) {

			// 有危险操作的请求不允许通过
			LogUtils.LogWrite(htp1.getUsername() + "【 " + ss.trim() + "】" + htp1.getIpaddress(), htp1.getUrl(), "warn");
			WriteResult(response, 0, "警告：请求中含有危险操作命令！");
			return;

		}
		try {		// System.out.println("action=" + action);
			switch (action) {

			case "yky_syncvip": //同步一卡易会员
				yky_syncvip(response, htp1, 0);
				break;
			case "yky_syncvip1": //同步一卡易会员（不要签名）
				yky_syncvip(response, htp1, 1);
				break;

			case "test":
				// Test(response, htp1);
				break;
			case "getmessagebal": // 取短信余额
				GetMessagebal(response, htp1);
				break;

			case "sendmessage": // 发送短信
				SendMessage(response, htp1);
				break;
			//=========================
			case "addsalepromplan": // 新增促销计划记录
				AddSalepromplan(response, htp1);
				break;
			case "delsalepromplan": // 删除促销计划记录
				DelSalepromplan(response, htp1);
				break;
			case "selectsalepromplan": // 选择满足条件的促销计划
				SelectSalepromplan(response, htp1);
				break;
			case "execsalepromplan": // 执行促销计划单
				ExecSalepromplan(response, htp1);
				break;

			case "updatesalepromplan": //修改促销计划记录
				UpdateSalepromplan(response, htp1);
				break;

			case "checksalepromplan": // 审核/取消审核促销计划单
				CheckSalepromplan(response, htp1);
				break;
			case "listsalepromplan": // 分页显示促销计划单
				ListSalepromplan(response, htp1);
				break;
			case "getsalepromplan": //获取促销计划单
				GetSalepromplan(response, htp1);
				break;
			case "cancelsalepromplan": //撤消促销计划单
				CancelSalepromplan(response, htp1);
				break;

			case "listsalepromhouse": // 分页显示促销店铺
				ListSalepromhouse(response, htp1);
				break;
			case "delsalepromhouse": // 删除促销店铺
				DelSalepromhouse(response, htp1);
				break;
			case "addsalepromhouse": // 增加促销店铺
				AddSalepromhouse(response, htp1);
				break;
			case "writesalepromhouse": //保存促销店铺
				WriteSalepromhouse(response, htp1);
				break;
			case "writeallsalepromhouse": //全选保存促销店铺
				WriteallSalepromhouse(response, htp1);
				break;

			case "listsalepromware": // 分页显示促销商品
				ListSalepromware(response, htp1);
				break;
			case "delsalepromware": // 删除促销商品
				DelSalepromware(response, htp1);
				break;
			case "addsalepromware": // 增加促销商品
				AddSalepromware(response, htp1);
				break;
			case "updatesalepromware": // 修改促销商品
				UpdateSalepromware(response, htp1);
				break;

			case "loadsalepromwarexls": // 从excel导入促销商品
				LoadSalepromwarexls(response, htp1);
				break;

			//=========================

			// 销售计划接口begin
			case "loadsaleplan": // 载入公司销售计划
				LoadSaleplan(response, htp1);
				break;
			case "savesaleplan": // 保存公司销售计划
				SaveSaleplan(response, htp1);
				break;

			case "houseplanlist": // 分页显示店铺年度销售计划
				Houseplanlist(response, htp1);
				break;
			case "savehouseplan": // 保存店铺销售计划
				SaveHouseplan(response, htp1);
				break;

			case "getsalemanplan": // 获取店铺店员销售日计划
				GetSalemanplan(response, htp1);
				break;
			case "autosalemanplan": // 店铺月计划自动均分店员销售日计划
				AutoSalemanplan(response, htp1);
				break;
			case "listsalemanplan": // 店员每日销售计划明细
				ListSalemanplan(response, htp1);
				break;
			case "writesalemanplan": // 保存店员每日销售计划明细
				WriteSalemanplan(response, htp1);
				break;

			// 销售计划接口end // =================================

			case "getbookhousepay": // 获取指定id结算记账关联记录
				GetBookhousepay(response, htp1);
				break;

			case "listbookhousepay": // 分页显示账本登账设置
				ListBookhousepay(response, htp1);
				break;

			case "addbookhousepay": //增加账本登账设置
				AddBookhousepay(response, htp1);
				break;
			case "updatebookhousepay": //修改账本登账设置
				UpdateBookhousepay(response, htp1);
				break;

			case "delbookhousepay": // 删除账本登账设置
				DelBookhousepay(response, htp1);
				break;

			case "addsubitem": // 增加记账项目
				AddSubitem(response, htp1);
				break;
			case "updatesubitem": // 修改记账项目
				UpdateSubitem(response, htp1);
				break;
			case "delsubitem": // 删除记账项目
				DelSubitem(response, htp1);
				break;
			case "listsubitem": // 显示记账项目
				ListSubitem(response, htp1);
				break;
			// =======================

			case "bookemployelist": // 获取职员品牌授权列表
				BookEmployeList(response, htp1);
				break;

			case "listaccbook": // 显示账本
				ListAccbook(response, htp1);
				break;

			case "addaccbook": // 增加账本
				AddAccbook(response, htp1);
				break;

			case "getaccbook": // 显示指定账本
				GetAccbook(response, htp1);
				break;

			case "updateaccbook": // 修改账本
				UpdateAccbook(response, htp1);
				break;
			case "delaccbook": // 删除账本
				DelAccbook(response, htp1);
				break;
			case "calcaccbook": // 刷新计算账本余额
				CalcAccbook(response, htp1);
				break;
			case "listbookdetail": // 显示账本明细流水
				ListBookdetail(response, htp1);
				break;
			case "findbookdetail": // 查询账本明细记录
				FindBookdetail(response, htp1);
				break;

			case "setaccbook": // 设置账本标志
				SetAccbook(response, htp1);
				break;

			case "addbookdetail": // 增加账本记账记录
				AddBookdetail(response, htp1);
				break;
			case "addtobookdetail": // 增加账本转账记录
				AddtoBookdetail(response, htp1);
				break;

			case "delbookdetail": // 删除账本记账记录
				DelBookdetail(response, htp1);
				break;

			case "checkbookdetail": // 审核账本记账记录
				CheckBookdetail(response, htp1);
				break;

			case "updatebookdetail": // 修改账本记账记录
				UpdateBookdetail(response, htp1);
				break;

			case "listbookdetailsum": // 显示账本记账汇总记录
				ListBookdetailsum(response, htp1);
				break;

			case "listbookdetailmx": // 显示账本记账明细记录
				ListBookdetailmx(response, htp1);
				break;
			case "listbookbalcurr": // 显示账本余额
				ListBookbalcurr(response, htp1);
				break;

			case "getbookoption": // 显示账本记账汇总记录
				GetBookoption(response, htp1);
				break;
			// ==================================

			case "listpublicbanner": // 显示公共标题横幅
				ListPublicbanner(response, htp1);
				break;
			case "addpublicbanner": // 增加公共标题横幅
				AddPublicbanner(response, htp1);
				break;
			case "delpublicbanner": // 删除公共标题横幅
				DelHousebanner(response, htp1);
				break;
			case "changepublicbanner": // 更改公共标题状态
				ChangePublicbanner(response, htp1);
				break;
			case "addhousebanner": // 增加店铺标题横幅
				AddHousebanner(response, htp1);
				break;
			case "updatehousebanner": // 更改店铺标题横幅说明
				UpdateHousebanner(response, htp1);
				break;
			case "changehousebanner": // 更改店铺标题状态
				ChangeHousebanner(response, htp1);
				break;
			case "listhousebanner": // 显示店铺标题横幅
				ListHousebanner(response, htp1, 0);
				break;

			case "listhousebannercheck": // 显示所有店铺待审广告标题横幅
				ListHousebanner(response, htp1, 1);
				break;
			case "delhousebanner": // 删除店铺标题横幅
				DelHousebanner(response, htp1);
				break;

			// ========================================
			// C端后台功能接口begin
			case "listonlinefirst": // 显示线上店铺首页信息，可发放优惠券，设置商品陈列类别
				ListOnlineFirst(response, htp1);
				break;
			case "getonlinehouse": // 获取最近登录的线上店铺
				GetOnlinehouse(response, htp1);
				break;

			case "setonlinehouse": // 保存最近登录的线上店铺
				SetOnlinehouse(response, htp1);
				break;

			case "getonlinepage": // 获取线上店铺首页信息
				GetOnlinepage(response, htp1);
				break;

			case "uploadhouselogo": // 上传线上店铺logo
				UploadHouselogo(response, htp1);
				break;

			case "listonlinehouse": // 线上店铺列表
				ListOnlinehouse(response, htp1);
				break;
			case "changeonlinehouse": // 更换线上店铺列表
				ChangeOnlinehouse(response, htp1);
				break;
			case "uponlinehouse": // 发布线上店铺
				UpOnlinehouse(response, htp1);
				break;

			case "getlinehouseinfo": // 获取线上店铺信息
				GetLinehouseInfo(response, htp1);

				break;
			case "getonlineinfo": // 获取指定线上店铺对应的实体店，商品类型，管理员
				GetOnlineinfo(response, htp1);
				break;

			case "updateonlinehouse": // 更改线上店铺信息
				UpdateOnlinehouse(response, htp1);
				break;
			case "createonlinehouse": // 创建线上店铺
				CreateOnlinehouse(response, htp1);
				break;

			case "selectonline2type": // 选择线上店铺经营类型
				SelectOnline2type(response, htp1);
				break;
			case "writeonline2type": // 保存线上店铺经营类型
				WriteOnline2type(response, htp1);
				break;

			case "selectonline2user": // 选择线上店铺职员
				SelectOnline2user(response, htp1);
				break;
			case "writeonline2user": // 保存线上店铺职员
				WriteOnline2user(response, htp1);
				break;

			case "selectonline2house": // 选择线上店铺对应的实体店铺
				SelectOnline2house(response, htp1);
				break;
			case "writeonline2house": // 保存线上店铺对应的实体店铺
				WriteOnline2house(response, htp1);
				break;
			// ====================================
			case "listdisplaycode": // 显示陈列类别
				ListDisplaycode(response, htp1);
				break;
			case "listdisplaycode1": // 显示陈列类别及款数
				ListDisplaycode1(response, htp1);
				break;
			case "adddisplaycode": // 增加陈列类别
				AddDisplaycode(response, htp1);
				break;
			case "updatedisplaycode": // 修改陈列类别
				UpdateDisplaycode(response, htp1);
				break;
			case "deldisplaycode": // 删除陈列类别
				DelDisplaycode(response, htp1);
				break;
			case "writeorderdisplaycode": // 更改陈列类别顺序
				WriteorderDisplaycode(response, htp1);
				break;
			// =========================

			case "warecodedisplayhelp": // 获取商品列表 --商品陈列专用
				GetWareCodeDisplayHelp(response, htp1);
				break;

			case "getwaredisplay": // 显示陈列商品信息(图片，颜色，尺码) ok
				GetWaredisplay(response, htp1);
				break;

			case "writewaredisplay": // 保存陈列商品信息(图片，颜色，尺码) ok
				WriteWaredisplay(response, htp1);
				break;

			case "listwaredisplay": // 显示陈列商品列表 ok
				ListWaredisplay(response, htp1);
				break;
			case "changewaredisplay": // 更改陈列商品状态:上下架
				ChangeWaredisplay(response, htp1);
				break;
			case "changeallwaredisplay": // 成批更改陈列商品状态:上下架
				ChangeAllWaredisplay(response, htp1);
				break;
			case "delwaredisplay": // 删除陈列商品
				DelWaredisplay(response, htp1);
				break;

			case "addwareonline1": // 增加相关搭配商品
				AddWareonline1(response, htp1);
				break;
			case "delwareonline1": // 删除相关搭配商品
				DelWareonline1(response, htp1);
				break;

			case "listwareonline1": // 选择相关搭配商品
				ListWareonline1(response, htp1);
				break;
			// ====================================================================
			case "addomwarepicture": // 增加订货会商品图片 可能未用
				AddOmWarepicture(response, htp1);
				break;
			case "addwarecodepicture": // 增加商品编码主图 可能未用
				AddWarecodepicture(response, htp1);
				break;
			// ==============================

			//
			case "addvippairs": // 增加潮流搭配资料
				// LogUtils.LogDebugWrite("addvippairs", url + " post:" +
				// request.getParameterMap().toString());
				AddVippairs(response, htp1);
				break;
			case "updatevippairs": // 更改潮流搭配资料
				UpdateVippairs(response, htp1);
				break;
			// case "updatevippairs1": // 更改潮流搭配资料
			// UpdateVippairs(response, htp1, 1);
			// break;
			//
			case "listvippairs": // 显示潮流搭配资料
				ListVippairs(response, htp1, 0);
				break;
			case "listvippairscheck": // 显示待审潮流搭配资料
				ListVippairs(response, htp1, 1);
				break;

			case "listwarepairs": // 销售助手显示商品搭配效果
				ListWarepairs(response, htp1);
				break;

			case "getvippairs": // 获取潮流搭配资料
				GetVippairs(response, htp1);
				break;

			case "delvippairs": // 删除潮流搭配资料
				DelVippairs(response, htp1);
				break;
			case "changevippairs": // 更改潮流搭配状态
				ChangeVippairs(response, htp1);
				break;
			// //
			// ====================================================================
			//
			case "addhousecoupon": // 增加店铺优惠券
				AddHousecoupon(response, htp1);
				break;
			case "listhousecoupon": // 显示店铺优惠券
				ListHousecoupon(response, htp1);
				break;
			case "gethousecoupon": // 获取店铺优惠券
				GetHousecoupon(response, htp1);
				break;

			case "listgethousecoupon": // 显示已领用的店铺优惠券
				ListGetHousecoupon(response, htp1);
				break;

			case "changehousecoupon": // 更改店铺优惠券状态
				ChangeHousecoupon(response, htp1);
				break;

			case "listvipcoupon": // 显示会员可用的优惠券
				ListVipcoupon(response, htp1);
				break;

			// 、、=============================
			// case "pushmessage": //推送消息
			// PushMessage(response, htp1);
			// break;
			case "sendvipmessage": // 店铺零售给会员发送消费消息
				SendVipmessage(response, htp1, 0);
				break;
			case "sendvipmessage1": // 商场零售给会员发送消费消息
				SendVipmessage(response, htp1, 1);
				break;
			case "addvipmessage": // 增加会员消息通知记录
				AddVipmessage(response, htp1);
				break;
			case "listvipmessage": // 显示会员消息通知记录
				ListVipmessage(response, htp1);
				break;
			case "delvipmessage": // 删除会员消息通知记录
				DelVipmessage(response, htp1);
				break;
			case "listvipgroup": // 显示店铺会员记录
				ListVipgroup(response, htp1);
				break;

			case "addviphousenotice": // 增加店铺公告记录
				AddViphousenotice(response, htp1);
				break;
			case "listviphousenotice": // 显示店铺公告记录
				ListViphousenotice(response, htp1);
				break;
			case "delviphousenotice": // 删除店铺公告记录
				DelViphousenotice(response, htp1);
				break;
			// case "upimage": // 上传图片
			// UpImage(response, htp1);
			// break;
			case "listvipsalenote": // 会员消费记录
				ListVipsalenote(response, htp1);
				break;
			case "listviporder": // 会员订单列表
				ListViporder(response, htp1);
				break;
			case "getviporderh": // 获取会员订单主记录
				GetViporderh(response, htp1);
				break;
			case "getviporder": // 获取会员订单详情
				GetViporder(response, htp1);
				break;
			case "updateviporder": // 更改会员订单实结货款及运费
				UpdateViporder(response, htp1);
				break;
			case "sendviporder": // 会员订单发货
				SendViporder(response, htp1);
				break;
			case "delviporder": // 删除会员订单
				DelViporder(response, htp1);
				break;
			case "closeviporder": // 关闭会员订单
				CloseViporder(response, htp1);
				break;
			// case "addwarenotopicture": // 增加商品图片1(用于从指定目录)
			// AddWarenotopicture(response, htp1);
			// break;
			//
			case "uploadwarepicture": // 载入多张商品图片1(用于从指定目录)
				UploadWarepicture(response, htp1);
				break;

			case "addwarepicture": // 增加商品陈列图片
				AddWarepicture(response, htp1);
				break;
			case "delwarepicture": // 删除商品陈列图片
				DelWarepicture(response, htp1);
				break;
			case "updatewarepicture": // 更改商品陈列图片
				UpdateWarepicture(response, htp1);
				break;
			case "sortwarepicture": // 更改商品陈列图片顺序
				SortWarepicture(response, htp1);
				break;
			case "listwarepicture": // 显示商品陈列图片
				ListWarepicture(response, htp1);
				break;

			// 采购商城接口begin
			case "addcityhouse": // 新增商城店铺记录
				AddCityhouseRec(response, htp1);
				break;
			case "delcityhousebyid": // 删除指定商城店铺id信息
				DelCityhouseByID(response, htp1);
				break;
			case "updatecityhousebyid": // 更新指定商城店铺id信息
				UpdateCityhouseByID(response, htp1, 0);
				break;
			case "updatecityhousebyid1": // 更新指定商城店铺id信息
				UpdateCityhouseByID(response, htp1, 1);
				break;
			case "getcityhousebyid": // 获取指定商城店铺id信息
				GetCityhouseByID(response, htp1);
				break;
			case "getprovcityhousebyid": // 加盟商获取供应商商城信息
				GetProvCityhouseByID(response, htp1);
				break;
			case "cityhouselist": // 分页获取我的商城店铺列表
				GetCityHouseList(response, htp1);
				break;
			case "cityhousewarelist": // 获取商城店铺商品列表
				GetCityHousewareList(response, htp1);
				break;
			case "cityhousebuyerlist": // 获取商城店铺授权买家(经销商)
				GetCityHousebuyerList(response, htp1);
				break;
			case "writecityhousebuyer": // 写商城店铺授权买家(经销商
				Writecityhousebuyer(response, htp1);
				break;
			case "writeallcityhousebuyer": // 写全部商城店铺授权买家(经销商
				WriteAllCityhousebuyer(response, htp1);
				break;
			case "cityhouseselectwarecode": // 商城选择商品列表
				CityhouseSelectWarecode(response, htp1);
				break;
			case "cityhousebuylist": // 买家分页获取访问采购商城店铺列表
				GetCityHousebuyList(response, htp1);
				break;
			case "cityhousebuywarelist": // 分页获取经销商采购商城店铺商品列表
				GetCityHousebuywareList(response, htp1);
				break;

			case "addcityhouseware": // 新增商城店铺商品记录
				AddCityhousewareRec(response, htp1);
				break;
			case "updatecityhousebywareid": // 更改商城店铺商品记录
				UpdateCityhousebywareid(response, htp1);
				break;
			case "delcityhouseware": // 删除商城店铺商品记录
				DelCityhousewareRec(response, htp1);
				break;
			case "clearcityhouseware": // 清除商城店铺商品记录
				ClearCityhousewareRec(response, htp1);
				break;

			case "changecityhousewareopendate": // 更改所有商城店铺商品的开放日期
				ChangeCityhouseWareopendate(response, htp1);
				break;

			case "getcityhousewarebyid": // 获取商城店铺商品记录信息
				GetCityhousewareById(response, htp1);
				break;
			case "upcityhousewareonline": // 商城店铺商品全部上线
				UpCityhousewareOnline(response, htp1);
				break;
			case "listcityorderwaresort": // 商城店铺商品排行列表
				ListCityorderwaresort(response, htp1);
				break;
			case "getcityhouseprovorderhlist": // 商城店铺采购订单历史记录列表
				GetCityHouseProvorderhList(response, htp1);
				break;
			// 采购商城
			case "addcityorderware": // 增加商城店铺订货商品记录
				AddCityorderware(response, htp1);
				break;

			case "listcityorderware": // 商城店铺订货商品记录列表 购物车列表
				ListCityorderware(response, htp1);
				break;
			case "delcityorderware": // 删除商城店铺订货商品记录
				DelCityorderware(response, htp1);
				break;
			case "getcityorderbywareid": // 获取商城订货商品颜色及尺码数量
				GetCityorderbywareid(response, htp1);
				break;
			case "writecityorderware": // 保存商城订货商品颜色及尺码数量
				WriteCityorderware(response, htp1);
				break;

			case "citywaretoorder": // 商城订货商品生成采购订单
				CityWareToOrder(response, htp1);
				break;
			// // ==================================== //
			// =================================
			case "gethousetwobarcode": // 生成店铺二维码，供VIP会员扫码注册
				HouseTwobarcode(response, htp1);
				break;

			// ===========================================================================================
			case "guesttypelist": // 分页获取会员类型列表
				GetGuesttypeList(response, htp1);
				break;
			case "getguesttypebyid": // 获取指定会员类型id信息
				GetGuesttypeByID(response, htp1);
				break;
			case "delguesttypebyid": // 删除指定会员类型id信息
				DelGuesttypeByID(response, htp1);
				break;
			case "updateguesttypebyid": // 更新指定会员类型id信息
				UpdateGuesttypeByID(response, htp1);
				break;

			case "addguesttype": // 新增会员类型记录
				AddGuesttypeRec(response, htp1);
				break;

			// ===========================================================================================
			case "guestviplist": // 分页获取会员列表
				GetGuestvipList(response, htp1);
				break;
			case "getguestvipbyid": // 获取指定会员id信息
				GetGuestvipByID(response, htp1);
				break;
			case "getguestvipbyno": // 根据会员卡号、手机号查找会员id信息
				GetGuestvipByNO(response, htp1);
				break;
			case "findguestvip": // 查找会员
				FindGuestvip(response, htp1);
				break;
			case "getguestvipinfo": // 销售助手--获取会员信息
				GetGuestvipinfo(response, htp1);
				break;
			case "listguestvipinfo": // 销售助手--会员消费历史
				ListGuestvipinfo(response, htp1);
				break;

			case "listwareinwhere": // 销售助手-- 指定商品的库存分布
				Listwareinwhere(response, htp1);
				break;

			case "delguestvipbyid": // 删除指定会员id信息
				DelGuestvipByID(response, htp1);
				break;
			case "updateguestvipbyid": // 更新指定会员id信息
				UpdateGuestvipByID(response, htp1);
				break;
			case "addguestvip": // 新增会员档案记录
				AddGuestvipRec(response, htp1);
				break;
			case "appendguestvip": // 根据会员卡增加或更新会员档案资料 ， 主要用于从excel中导入会员资料
				AppendGuestvip(response, htp1);
				break;
			case "appendguestcentorcurr": // 载入会员初始积分和储值余额
				AppendGuestcentorcurr(response, htp1);
				break;
			case "totalguestbal": // 汇总会员积分及储值
				TotalGuestbal(response, htp1);
				break;
			case "listguestbal": // 分页显示会员积分及储值
				ListGuestbal(response, htp1);
				break;
			// ===========================================================================================

			case "guestcentlist": // 获取会员积分记录
				GetGuestcentList(response, htp1);
				break;
			case "addguestcent": // 新增会员积分记录
				AddGuestcentRec(response, htp1);
				break;
			case "guestcurrlist": // 获取会员储值记录
				GetGuestcurrList(response, htp1);
				break;
			case "addguestcurr": // 新增会员储值记录
				AddGuestcurrRec(response, htp1);
				break;
			case "getguestcurrbyid": // 获取指定id会员储值记录
				GetGuestcurrbyid(response, htp1);
				break;
			case "getguestcurrbyidx": // 获取指定id会员储值记录(后台打印调用)
				GetGuestcurrbyid(response, htp1);
				break;

			// ==================================================================
			case "carrylinelist": // 分页获取物流单位列表
				GetCarrylineList(response, htp1);
				break;
			case "addcarryline": // 新增物流单位记录
				AddCarrylineRec(response, htp1);
				break;
			case "delcarrylinebyid": // 删除指定物流单位id信息
				DelCarrylineByID(response, htp1);
				break;
			case "updatecarrylinebyid": // 更新指定物流单位id信息
				UpdateCarrylineByID(response, htp1);
				break;
			case "getcarrylinebyid": // 获取指定物流单位id信息
				GetCarrylineByID(response, htp1);
				break;

			default:
				WriteResult(response, 0, "buy接口未定义:" + action + "!");
				break;
			}
		} catch (IOException e) {
			//			e.printStackTrace();
			System.out.println(e.getMessage());
			WriteResult(response, 0, "网络异常，稍候请重试!");
		}
	}

	// ==============================

	// 新增促销方案单记录
	protected void AddSalepromplan(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int ntid = jsonObject.has("ntid") ? Integer.parseInt(jsonObject.getString("ntid")) : 0;
		//ntid:0=买满X元 打Y折 送Z商品,1=买满X元 让利Y元 送Z商品,	2=买满X件 打Y折 送Z商品,3=买满X件 让利Y元 送Z商品  
		Salepromplan dal = new Salepromplan();
		dal.setAccid(htp.getMaccid());
		dal.setOperant(htp.getUsername());
		dal.setNtid(ntid);

		WriteResultJson(response, dal.Append(), dal.getErrmess());
	}

	//撤消促销计划单
	protected void CancelSalepromplan(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno").replace("'", "''") : "";
		Salepromplan dal = new Salepromplan();
		dal.setAccid(htp.getMaccid());
		dal.setOperant(htp.getUsername());
		dal.setNoteno(noteno);
		dal.setStatetag(0);
		WriteResult(response, dal.Cancel(), dal.getErrmess());
	}

	//获取促销计划单
	protected void GetSalepromplan(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno").replace("'", "''") : "";
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		//		int statetag = jsonObject.has("statetag") ? Integer.parseInt(jsonObject.getString("statetag")) : 2;
		//		int checktag = jsonObject.has("checktag") ? Integer.parseInt(jsonObject.getString("checktag")) : 2;
		//checktag:0=未审，1=已审，2=所有
		long accid = htp.getMaccid();
		//		String lastop = htp.getUsername();
		String strwhere = " a.ACCID=" + accid + " and a.NOTENO='" + noteno.toUpperCase() + "'";
		//		if (checktag == 0)  //未审
		//			strwhere += " and a.checkman is null";
		//		else if (checktag == 1) //已审
		//			strwhere += " and length(a.checkman)>0";
		//		if (htp.getLevelid() == 0) // 系统管理员允许看所有用户的单据
		//		{
		//			if (statetag == 0)
		//				strwhere += " and a.statetag=0 "; // 未提 交
		//			else if (statetag == 1)
		//				strwhere += " and a.statetag=1"; // 已提 交
		//			else
		//				strwhere += " and (a.STATETAG=1 or a.STATETAG=0)"; // 所有
		//		} else {
		//			if (statetag == 0)
		//				strwhere += " and a.statetag=0 and a.operant='" + lastop + "'"; // 未提 交
		//			else if (statetag == 1)
		//				strwhere += " and a.statetag=1"; // 已提 交
		//			else
		//				strwhere += " and (a.STATETAG=1 or a.STATETAG=0 and a.operant='" + lastop + "')"; // 所有
		//		}

		if (fieldlist.equals("*") || fieldlist.length() <= 0)
			fieldlist = "A.ID,A.NOTENO,A.ACCID,A.NOTEDATE,A.NTID,A.HANDNO,A.REMARK,A.OPERANT,A.STATETAG,A.TOTALAMT,A.TOTALCURR,A.KZXX,A.saledayLIST,A.ZDISCOUNT,A.CUTCURR,A.VRATE,A.VCURR";

		fieldlist += ",decode(a.ntid,0,'买满X元打Y折送Z商品',1,'买满X元让利Y元送Z商品',2,'买满X件打Y折送Z商品',3,'买满X件让利Y元送Z商品','无效方案') as ntidname";
		fieldlist += ",to_char(a.begindate,'yyyy-mm-dd') as begindate,to_char(a.enddate,'yyyy-mm-dd') as enddate,to_char(a.begindate,'hh24:mi') as begintime,to_char(a.enddate,'hh24:mi') as endtime";
		Salepromplan dal = new Salepromplan();

		Table tb = new Table();
		tb = dal.GetList(strwhere, fieldlist).getTable(1);

		Write(response, DbHelperSQL.DataTable2Json(tb));
	}

	// 审核/反审促销计划单
	protected void CheckSalepromplan(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno").replace("'", "''") : "";
		Integer checkid = jsonObject.has("checkid") ? Integer.parseInt(jsonObject.getString("checkid")) : 0;
		// checkid:1=审核，0=取消审核
		Salepromplan dal = new Salepromplan();
		dal.setAccid(htp.getMaccid());
		dal.setOperant(htp.getUsername());
		dal.setNoteno(noteno);
		WriteResult(response, dal.Check(checkid), dal.getErrmess());
	}

	//执行促销计划
	protected void ExecSalepromplan(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String xsnoteno = jsonObject.has("xsnoteno") ? jsonObject.getString("xsnoteno").replace("'", "''") : "";
		String cxno = jsonObject.has("cxno") ? jsonObject.getString("cxno").replace("'", "''") : "";
		//		 p_dosaleprom(v_accid in number,v_cxnoteno in varchar2,v_noteno in varchar2,v_changed out number )

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("v_accid", new ProdParam(Types.BIGINT, htp.getMaccid(), 1));// 输入参数
		param.put("v_cxnoteno", new ProdParam(Types.VARCHAR, cxno, 2));// 输入参数
		param.put("v_noteno", new ProdParam(Types.VARCHAR, xsnoteno, 3));// 输入参数
		param.put("v_changed", new ProdParam(Types.INTEGER, 4));// 输出参数
		String qry = "{call p_dosaleprom(?,?,?,?)}";

		int ret = DbHelperSQL.ExecuteProcIn(qry, param); // 带输入参数
		if (ret >= 0) {
			// return 0;
			//			int changed =Integer.parseInt(param.get("v_changed").getParamvalue().toString());
			if (Integer.parseInt(param.get("v_changed").getParamvalue().toString()) == 0)
				WriteResult(response, 2, "促销计划不能执行！");
			else
				WriteResult(response, 1, "促销计划执行成功！");
		} else
			WriteResult(response, 0, "操作异常2！");
	}

	//选择满足条件的促销计划
	protected void SelectSalepromplan(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		String xsnoteno = jsonObject.has("xsnoteno") ? jsonObject.getString("xsnoteno").replace("'", "''") : "";
		String notedatetime = jsonObject.has("notedatetime") ? jsonObject.getString("notedatetime") : "";
		//yyyy-mm-dd hh:mi:ss
		//12345678901234567890
		String notedatestr = Func.subString(notedatetime, 1, 10);
		String notetimestr = Func.subString(notedatetime, 12, 5);

		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 50;
		long accid = htp.getMaccid();
		String fieldlist = "a.NOTENO,a.ACCID,a.NOTEDATE,a.begindate,a.enddate,a.NTID,a.REMARK,a.id";
		String strwhere = " a.ACCID=" + accid;
		strwhere += "\n and a.statetag=1 and length(a.checkman)>0";
		strwhere += "\n and a.begindate<=to_date('" + notedatestr + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		strwhere += "\n and a.enddate>=to_date('" + notedatestr + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		strwhere += "\n and to_char(a.begindate,'hh24:mi')<='" + notetimestr + "'";
		strwhere += "\n and to_char(a.enddate,'hh24:mi')>='" + notetimestr + "'";
		if (houseid > 0)
			strwhere += "\n and exists (select 1 from salepromhouse c where c.houseid=" + houseid + " and a.accid=c.accid and a.noteno=c.noteno)";
		//ntid:0=买满X元 打Y折 送Z商品,1=买满X元 让利Y元 送Z商品,	2=买满X件 打Y折 送Z商品,3=买满X件 让利Y元 送Z商品
		//	kzxx
		//	4:储值卡支付控制:0=比例，1=金额
		//	3:0=全场商品1=促销商品
		//	2:要计算积分
		//	1:启用会员折中折
		strwhere += "\n and ( substr(a.kzxx,3,1)<>'1'";
		strwhere += " or substr(a.kzxx,3,1)='1'";  //如果是促销商品，要判断销售单里有没有该商品
		if (xsnoteno.length() > 0) {
			//			  Formsaleout.Query1.sql.add('and exists (select 1 from salemodem b join (select wareno from '+tempname+') c on b.wareno=c.wareno where a.noteno=b.noteno and b.iszp<>''1'') )');
			//			strwhere += "\n and exists (select 1 from salepromware b join (select wareid from shopsalem where accid=" + accid //
			//					+ "\n and noteno='" + xsnoteno + "' ) c on b.wareid=c.wareid where a.accid=b.accid and a.noteno=b.noteno and b.iszp=0 )";
			strwhere += "\n and exists (select 1 from salepromware b  where a.accid=b.accid and a.noteno=b.noteno and b.iszp=0";
			strwhere += "\n	and exists (select wareid from shopsalem c where c.accid=" + accid + " and c.noteno='" + xsnoteno + "' and b.wareid=c.wareid))";
		}
		strwhere += "\n )";
		Salepromplan dal = new Salepromplan();

		QueryParam qp = new QueryParam(page, pagesize, "notedate desc,id");
		qp.setCalcfield("decode(ntid,0,'买满X元打Y折送Z商品',1,'买满X元让利Y元送Z商品',2,'买满X件打Y折送Z商品',3,'买满X件让利Y元送Z商品','无效方案') as ntidname");
		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere, fieldlist);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	//分页显示促销计划单
	protected void ListSalepromplan(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		//		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno").replace("'", "''") : "";
		String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : htp.getNowdate();
		String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : htp.getNowdate();
		String order = jsonObject.has("order") ? jsonObject.getString("order") : "desc";
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : "notedate";
		sort += " " + order + ",id ";// +order;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		int ntid = jsonObject.has("ntid") ? Integer.parseInt(jsonObject.getString("ntid")) : -1;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 50;
		int statetag = jsonObject.has("statetag") ? Integer.parseInt(jsonObject.getString("statetag")) : 2;
		//statetag:0=未提交，1=已提交，2=所有
		int checktag = jsonObject.has("checktag") ? Integer.parseInt(jsonObject.getString("checktag")) : 2;
		//checktag:0=未审，1=已审，2=所有
		String lastop = htp.getUsername();
		long accid = htp.getMaccid();

		String strwhere = " a.ACCID=" + accid;
		if (checktag == 0)
			strwhere += " and a.checkman is null  "; // 未审
		else if (checktag == 1)
			strwhere += " and length(a.checkman)>0 "; // 已审

		if (ntid >= 0)
			strwhere += "  and a.ntid=" + ntid;
		if (htp.getLevelid() == 0) // 系统管理员允许看所有用户的单据
		{
			if (statetag == 0)
				strwhere += " and a.statetag=0 "; // 未提 交
			else if (statetag == 1)
				strwhere += " and a.statetag=1"; // 已提 交
			else
				strwhere += " and (a.STATETAG=1 or a.STATETAG=0)"; // 所有
		} else {

			if (statetag == 0)
				strwhere += " and a.statetag=0 and a.operant='" + lastop + "'"; // 未提交
			else if (statetag == 1)
				strwhere += " and a.statetag=1"; // 已提 交
			else
				strwhere += " and (a.STATETAG=1 or a.STATETAG=0 and a.operant='" + lastop + "')"; // 所有
		}
		if (findbox.length() > 0)
			strwhere += " and (a.noteno like '%" + findbox.toUpperCase() + "%' or a.ACTIONNAME like '%" + findbox + "%')";

		strwhere += " and a.NOTEDATE >= to_date('" + mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		strwhere += " and a.NOTEDATE <= to_date('" + maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		if (fieldlist.equals("*") || fieldlist.length() <= 0)
			fieldlist = "a.ID,a.NOTENO,a.ACCID,a.NOTEDATE,a.NTID,a.HANDNO,a.REMARK,a.OPERANT,a.CHECKMAN,a.STATETAG,a.TOTALAMT,a.TOTALCURR";

		fieldlist += ",to_char(a.begindate,'yyyy-mm-dd') as begindate,to_char(a.enddate,'yyyy-mm-dd') as enddate,to_char(a.begindate,'hh24:mi') as begintime,to_char(a.enddate,'hh24:mi') as endtime";

		Salepromplan dal = new Salepromplan();

		QueryParam qp = new QueryParam(page, pagesize, sort);
		qp.setCalcfield("decode(ntid,0,'买满X元打Y折送Z商品',1,'买满X元让利Y元送Z商品',2,'买满X件打Y折送Z商品',3,'买满X件让利Y元送Z商品','无效方案') as ntidname");
		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere, fieldlist);
		if (page < 0) {// 导出excel
			String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;
		}
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 修改促销计划单
	protected void UpdateSalepromplan(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno").replace("'", "''") : "";
		Salepromplan dal = new Salepromplan();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(htp.getMaccid());
		dal.setOperant(htp.getUsername());
		dal.setNoteno(noteno);
		WriteResult(response, dal.Update(), dal.getErrmess());
	}

	// 删除促销计划单
	protected void DelSalepromplan(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno").replace("'", "''") : "";
		Salepromplan dal = new Salepromplan();
		long accid = htp.getMaccid();
		String lastop = htp.getUsername();
		dal.setOperant(lastop);
		dal.setNoteno(noteno);
		dal.setAccid(accid);
		if (dal.Delete() == 0)
			WriteResult(response, 0, dal.getErrmess());
		else {
			pFunc.myWriteLog(accid, "促销计划单", "【删除单据】" + noteno, lastop);

			WriteResult(response, 1, dal.getErrmess());
		}
	}

	//增加促销计划店铺
	protected void AddSalepromhouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno").replace("'", "''") : "";
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		Salepromhouse dal = new Salepromhouse();
		long accid = htp.getMaccid();

		dal.setNoteno(noteno);
		dal.setHouseid(houseid);
		dal.setAccid(accid);
		if (dal.Append() == 0)
			WriteResult(response, 0, dal.getErrmess());
		else {
			WriteResult(response, 1, dal.getErrmess());
		}
	}

	//增加促销计划商品
	protected void AddSalepromware(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Salepromware dal = new Salepromware();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(htp.getMaccid());
		if (dal.Append() == 0)
			WriteResult(response, 0, dal.getErrmess());
		else {
			WriteResult(response, 1, dal.getErrmess());
		}
	}

	//修改促销计划商品
	protected void UpdateSalepromware(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Salepromware dal = new Salepromware();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(htp.getMaccid());
		if (dal.Update() == 0)
			WriteResult(response, 0, dal.getErrmess());
		else {
			WriteResult(response, 1, dal.getErrmess());
		}
	}

	// 保存促销店铺选择
	protected void WriteSalepromhouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno").replace("'", "''") : "";
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		int value = jsonObject.has("value") ? Integer.parseInt(jsonObject.getString("value")) : 0;
		Salepromhouse dal = new Salepromhouse();
		dal.setNoteno(noteno);
		dal.setHouseid(houseid);
		dal.setAccid(htp.getMaccid());
		if (dal.Write(value) == 0)
			WriteResult(response, 0, dal.getErrmess());
		else {

			WriteResult(response, 1, dal.getErrmess());
		}
	}

	// 保存促销店铺选择
	protected void WriteallSalepromhouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno").replace("'", "''") : "";
		//		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		int value = jsonObject.has("value") ? Integer.parseInt(jsonObject.getString("value")) : 0;
		Salepromhouse dal = new Salepromhouse();
		dal.setNoteno(noteno);
		//		dal.setHouseid(houseid);
		dal.setUserid(htp.getUserid());
		dal.setAccid(htp.getMaccid());
		if (dal.WriteAll(value, htp.getQxbj()) == 0)
			WriteResult(response, 0, dal.getErrmess());
		else {
			WriteResult(response, 1, dal.getErrmess());
		}
	}

	// 删除促销店铺
	protected void DelSalepromhouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno").replace("'", "''") : "";
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		Salepromhouse dal = new Salepromhouse();
		long accid = htp.getMaccid();
		//		String lastop = htp.getUsername();
		//		dal.setOperant(lastop);
		dal.setNoteno(noteno);
		dal.setHouseid(houseid);
		dal.setAccid(accid);
		if (dal.Delete() == 0)
			WriteResult(response, 0, dal.getErrmess());
		else {

			WriteResult(response, 1, dal.getErrmess());
		}
	}

	//从excel导入促销商品
	protected void LoadSalepromwarexls(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Salepromware dal = new Salepromware();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(htp.getMaccid());
		if (dal.LoadfromExcel() == 0)
			WriteResult(response, 0, dal.getErrmess());
		else {

			WriteResult(response, 1, dal.getErrmess());
		}
	}

	// 删除促销商品
	protected void DelSalepromware(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno").replace("'", "''") : "";
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		int iszp = jsonObject.has("iszp") ? Integer.parseInt(jsonObject.getString("iszp")) : 0;
		Salepromware dal = new Salepromware();
		long accid = htp.getMaccid();
		//		String lastop = htp.getUsername();
		//		dal.setOperant(lastop);
		dal.setNoteno(noteno);
		dal.setWareid(wareid);
		dal.setIszp(iszp);
		dal.setAccid(accid);
		if (dal.Delete() == 0)
			WriteResult(response, 0, dal.getErrmess());
		else {

			WriteResult(response, 1, dal.getErrmess());
		}
	}

	//分页显示促销计划店铺
	protected void ListSalepromhouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno").replace("'", "''") : "";
		String sort = " housename ,houseid ";// +order;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 50;

		//		String strwhere = " a.ACCID=" + htp.getMaccid();
		//		strwhere += "  and a.noteno='" + noteno + "'";
		//		String fieldlist = "a.houseid,b.housename";

		Salepromhouse dal = new Salepromhouse();
		dal.setAccid(htp.getMaccid());
		dal.setNoteno(noteno);
		dal.setUserid(htp.getUserid());
		QueryParam qp = new QueryParam(page, pagesize, sort);
		//		qp.setSumString("nvl(sum(totalamt),0) as totalamount,nvl(sum(totalcurr),0) as totalcurr");
		Table tb = new Table();
		tb = dal.GetTable(qp, htp.getQxbj());
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	//分页显示促销计划商品
	protected void ListSalepromware(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno").replace("'", "''") : "";
		String sort = " wareno ,wareid ";// +order;
		int iszp = jsonObject.has("iszp") ? Integer.parseInt(jsonObject.getString("iszp")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 50;

		String strwhere = " a.ACCID=" + htp.getMaccid();
		strwhere += "  and a.noteno='" + noteno + "' and a.iszp=" + iszp;
		String fieldlist = "a.wareid,b.wareno,b.warename,b.units,b.retailsale,b.model,a.price,a.amount,a.discount";

		Salepromware dal = new Salepromware();

		QueryParam qp = new QueryParam(page, pagesize, sort);
		//		qp.setSumString("nvl(sum(totalamt),0) as totalamount,nvl(sum(totalcurr),0) as totalcurr");
		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere, fieldlist);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 获取年度销售计划
	protected void LoadSaleplan(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int yearnum = jsonObject.has("yearnum") ? Integer.parseInt(jsonObject.getString("yearnum")) : 0;
		Saleplan dal = new Saleplan();
		dal.setAccid(htp.getMaccid());
		dal.setYearnum(yearnum);
		if (dal.Load() == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());
		}
	}

	// 保存年度销售计划
	protected void SaveSaleplan(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// int yearnum = jsonObject.has("yearnum") ?
		// Integer.parseInt(jsonObject.getString("yearnum")) : 0;
		String lastop = htp.getUsername();
		long accid = htp.getMaccid();
		if (accid == 1000 && (lastop.equals("演示") || lastop.equals("枫杨"))) {
			WriteResult(response, 0, "这是演示套账，不允许更改记录！");
			return;
		}
		Saleplan dal = new Saleplan();
		dal.setAccid(htp.getMaccid());
		dal.setLastop(lastop);
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类

		WriteResult(response, dal.Save(), dal.getErrmess());

	}

	// 保存店铺销售计划
	protected void SaveHouseplan(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// int yearnum = jsonObject.has("yearnum") ?
		// Integer.parseInt(jsonObject.getString("yearnum")) : 0;
		String lastop = htp.getUsername();
		long accid = htp.getMaccid();
		if (accid == 1000 && (lastop.equals("演示") || lastop.equals("枫杨"))) {
			WriteResult(response, 0, "这是演示套账，不允许更改记录！");
			return;
		}
		Houseplan dal = new Houseplan();
		dal.setAccid(htp.getMaccid());
		dal.setLastop(lastop);
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类

		WriteResult(response, dal.Save(), dal.getErrmess());

	}

	// 分页显示店铺年度销售计划
	protected void Houseplanlist(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int yearnum = jsonObject.has("yearnum") ? Integer.parseInt(jsonObject.getString("yearnum")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;

		if (yearnum <= 2000 || yearnum >= 3000) {

			WriteResult(response, 0, "年份参数无效");
			return;
		}
		String qry = " insert into houseplan (houseid,yearnum,curr,curr1,curr2,curr3,curr4,curr5,curr6,curr7,curr8,curr9,curr10,curr11,curr12,lastop,lastdate)";
		qry += "  select houseid," + yearnum + ",0,0,0,0,0,0,0,0,0,0,0,0,0,'" + htp.getUsername() + "',sysdate from warehouse a ";
		qry += "  where a.accid=" + htp.getMaccid() + " and a.statetag=1 and a.noused=0 ";
		qry += "  and not exists (select 1 from houseplan b where a.houseid=b.houseid and b.yearnum=" + yearnum + ")";
		DbHelperSQL.ExecuteSql(qry);

		String fieldlist = "a.houseid,b.housename,a.curr,a.curr1,a.curr2,a.curr3,a.curr4,a.curr5,a.curr6,a.curr7,a.curr8,a.curr9,a.curr10,a.curr11,a.curr12";
		String strwhere = "  b.statetag=1 and b.noused=0 and b.accid=" + htp.getMaccid() + " and a.yearnum=" + yearnum;
		//		if (htp.getQxbj() == 1) {
		//			strwhere += "\n     and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + htp.getUserid() + " ) ";
		//		}
		String sort = "housename,houseid";
		Houseplan dal = new Houseplan();
		QueryParam qp = new QueryParam(page, pagesize, sort);

		String sumstr = " nvl(sum(curr),0) as totalcurr,nvl(sum(curr1),0) as totalcurr1,nvl(sum(curr2),0) as totalcurr2,nvl(sum(curr3),0) as totalcurr3,nvl(sum(curr4),0) as totalcurr4,nvl(sum(curr5),0) as totalcurr5,nvl(sum(curr6),0) as totalcurr6";
		sumstr += ",nvl(sum(curr7),0) as totalcurr7,nvl(sum(curr8),0) as totalcurr8,nvl(sum(curr9),0) as totalcurr9,nvl(sum(curr10),0) as totalcurr10,nvl(sum(curr11),0) as totalcurr11,nvl(sum(curr12),0) as totalcurr12";

		qp.setSumString(sumstr);

		Table tb = new Table();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// ==============================
	// VIP后台接口begin
	// 显示公共标题横幅
	protected void ListPublicbanner(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = "A.BANNERID,A.BANNERNAME,A.IMAGENAME,A.LASTDATE,A.ONHOUSEID,B.ONHOUSENAME,A.STATETAG,a.webadd ";
		String strwhere = "a.lx=0";
		String sort = "LASTDATE DESC,BANNERID";
		Vipbanner dal = new Vipbanner();
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 增加公共标题横幅
	protected void AddPublicbanner(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String bannername = jsonObject.has("bannername") ? jsonObject.getString("bannername") : "";
		Vipbanner dal = new Vipbanner();
		// DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setStatetag(0);
		dal.setBannername(bannername);
		dal.setLastop(htp.getUsername());
		dal.setLx(0);
		WriteResult(response, dal.Append(htp.getPictjson()), dal.getErrmess());
	}

	// 删除店铺标题横幅/公共标题横幅
	protected void DelHousebanner(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long bannerid = jsonObject.has("bannerid") ? Long.parseLong(jsonObject.getString("bannerid")) : 0;
		Vipbanner dal = new Vipbanner();
		dal.setBannerid(bannerid);
		WriteResult(response, dal.Delete(), dal.getErrmess());
	}

	// 更改公共标题横幅状态
	protected void ChangePublicbanner(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long bannerid = jsonObject.has("bannerid") ? Long.parseLong(jsonObject.getString("bannerid")) : 0;
		String refuse = jsonObject.has("refuse") ? jsonObject.getString("refuse") : "";
		int zt = jsonObject.has("zt") ? Integer.parseInt(jsonObject.getString("zt")) : 0;
		// /*statetag:0=草稿 1=待审 2=审核 3=发布
		// zt=0:撤消 3->0
		// zt=1:发布 0->3
		// */
		// /* zt=0:提交 statetag 0->1
		// zt=1:撤消待审 statetag 1->0
		// zt=2:发布 statetag 2->3
		// zt=3:撤消审核 statetag 2->1
		// zt=4:撤消发布 statetag 3->2
		// zt=5:审核通过 statetag 1->2
		// zt=6:拒绝 statetag 1->0 要传入refuse
		// */
		Vipbanner dal = new Vipbanner();
		dal.setBannerid(bannerid);
		dal.setRefuse(refuse);
		WriteResult(response, dal.ChangePublicBanner(zt), dal.getErrmess());
	}

	// 增加店铺标题横幅
	protected void AddHousebanner(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String bannername = jsonObject.has("bannername") ? jsonObject.getString("bannername") : "";
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Vipbanner dal = new Vipbanner();
		// DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setStatetag(0);
		dal.setBannername(bannername);
		dal.setOnhouseid(onhouseid);
		dal.setLastop(htp.getUsername());
		dal.setLx(1);
		WriteResult(response, dal.Append(htp.getPictjson()), dal.getErrmess());
	}

	// 更改店铺标题横幅
	protected void UpdateHousebanner(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long bannerid = jsonObject.has("bannerid") ?
		// Long.parseLong(jsonObject.getString("bannerid")) : 0;
		Vipbanner dal = new Vipbanner();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Update(htp.getPictjson()), dal.getErrmess());
	}

	// 更改店铺标题横幅状态
	protected void ChangeHousebanner(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long bannerid = jsonObject.has("bannerid") ? Long.parseLong(jsonObject.getString("bannerid")) : 0;
		// String refuse = jsonObject.has("refuse") ?
		// jsonObject.getString("refuse") : "";
		int zt = jsonObject.has("zt") ? Integer.parseInt(jsonObject.getString("zt")) : 0;
		// statetag:0=草稿 1=待审 2=审核 3=发布
		// zt=0:提交 statetag 0->1
		// zt=1:撤消待审 statetag 1->0
		// zt=2:发布 statetag 2->3
		// zt=3:撤消审核 statetag 2->1
		// zt=4:撤消发布 statetag 3->2
		// zt=5:审核通过 statetag 1->2
		// zt=6:拒绝 statetag 1->0 要传入refuse
		Vipbanner dal = new Vipbanner();
		dal.setBannerid(bannerid);
		// dal.setRefuse(refuse);
		WriteResult(response, dal.ChangeHouseBanner(zt), dal.getErrmess());
	}

	// 获取店铺标题横幅
	protected void ListHousebanner(HttpServletResponse response, HttpInfo htp, int fs) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = "";
		String strwhere = "";
		if (fs == 0) {
			long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
			fieldlist = " a.bannerid,a.bannername,a.imagename,a.lastdate,a.statetag,a.refuse ";
			strwhere = " a.lx=1 and a.onhouseid=" + onhouseid;
			// qry += " from VIPbanner a where a.lx=1 and a.onhouseid=" +
			// onhouseid;
		} else {
			fieldlist = " a.bannerid,a.bannername,a.imagename,a.lastdate,a.onhouseid,b.onhousename,a.statetag,a.refuse ";
			strwhere = " a.lx=1 and a.statetag=1 ";
			// qry += " from VIPbanner a left outer join onlinehouse b on
			// a.onhouseid=b.onhouseid where a.lx=1 and a.statetag=1";
		}
		String sort = "lastdate desc,bannerid";

		Vipbanner dal = new Vipbanner();
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 删除店铺标题横幅/公共标题横幅
	protected void DelHousebanner(HttpServletResponse response, HttpInfo htp, int fs) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long bannerid = jsonObject.has("bannerid") ? Long.parseLong(jsonObject.getString("bannerid")) : 0;
		Vipbanner dal = new Vipbanner();
		dal.setBannerid(bannerid);
		// dal.setRefuse(refuse);
		WriteResult(response, dal.Delete(), dal.getErrmess());

	}

	// 生成线上店铺二维码，供VIP扫码注册,扫码后自动成为该店铺的会员
	protected void HouseTwobarcode(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Onlinehouse dal = new Onlinehouse();
		// dal.setEpid(htp.getUserid());type name = new type();
		dal.setOnhouseid(onhouseid);
		// dal.doHouseTwobarcode();
		WriteResult(response, dal.doHouseTwobarcode(), dal.getErrmess());
	}

	// 显示线上店铺首页信息，可发放优惠券，设置商品陈列类别
	protected void ListOnlineFirst(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Onlinehouse dal = new Onlinehouse();
		dal.setOnhouseid(onhouseid);
		if (dal.doListOnlineFirst() == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());
		}
	}

	// 获取最近登录的线上店铺
	protected void GetOnlinehouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		// JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Onlinehouse dal = new Onlinehouse();
		dal.setAccid(htp.getMaccid());

		int ret = dal.doGetOnlinehouse(htp.getUserid());
		if (ret == 1) {
			WriteResultJson(response, 1, dal.getErrmess());
		} else {
			WriteResult(response, ret, dal.getErrmess());
		}
	}

	// 保存最近登录的线上店铺
	protected void SetOnlinehouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Employe dal = new Employe();
		dal.setEpid(htp.getUserid());
		dal.setOnhouseid(onhouseid);
		WriteResult(response, dal.UpdateOnhouseid(), dal.getErrmess());

	}

	// 获取线上店铺首页信息
	protected void GetOnlinepage(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		int dateid = jsonObject.has("dateid") ? Integer.parseInt(jsonObject.getString("dateid")) : 0;
		// dateid:0=本日，1=昨日，2=本周，3=本月
		vPagetotal dal = new vPagetotal();
		dal.setUserid(htp.getUserid());
		dal.setAccid(htp.getMaccid());
		dal.setHouseid(onhouseid);
		dal.setDateid(dateid);
		dal.setCurrdatetimestr(htp.getNowdatetime());
		dal.setNowday(Func.StrToDate(htp.getNowdate()));
		dal.doGetOnlinepage();
		WriteResultJson(response, 1, dal.getErrmess());

	}

	// 上传线上店铺logo
	protected void UploadHouselogo(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Onlinehouse dal = new Onlinehouse();
		dal.setOnhouseid(onhouseid);
		dal.setAccid(htp.getMaccid());
		WriteResult(response, dal.doUploadHouselogo(htp.getPictjson()), dal.getErrmess());
	}

	// 线上店铺列表
	protected void ListOnlinehouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		int statetag = jsonObject.has("statetag") ? Integer.parseInt(jsonObject.getString("statetag")) : 2;
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " ONHOUSENAME ";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : " asc ";
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String strwhere = " ACCID=" + htp.getMaccid();
		sort += " " + order + ",rowid ";
		if (statetag < 2)
			strwhere += " and STATETAG=" + statetag;
		if (findbox.equals(""))
			strwhere += " and (ONHOUSENAME like '%" + findbox + "%' or SHORTNAME like '%" + findbox.toUpperCase() + "%')";
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "ONHOUSEID,ONHOUSENAME,ACCID,NOUSED,STATETAG,LASTOP,LASTDATE,IMAGENAME";

		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		Onlinehouse dal = new Onlinehouse();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 更换线上店铺列表
	protected void ChangeOnlinehouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String sort = " onhouseid ";
		// String fieldlist = "";
		// String strwhere = "";
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		Onlinehouse dal = new Onlinehouse();

		if (htp.getLevelid() == 0) {
			String fieldlist = " a.ONHOUSEID,a.ONHOUSENAME,a.STATETAG,a.IMAGENAME,'1111111111' as XXX";
			// qry += " from onlinehouse a ";
			String strwhere = "  a.accid=" + htp.getMaccid();
			tb = dal.GetTable(qp, strwhere, fieldlist);
		} else {
			String fieldlist = " a.ONHOUSEID,a.ONHOUSENAME,a.STATETAG,a.IMAGENAME,B.XXX";
			// qry += " from onlinehouse a join online2user b on
			// a.onhouseid=b.onhouseid";
			String strwhere = "  a.accid=" + htp.getMaccid() + " and b.epid=" + htp.getUserid();
			tb = dal.GetTable1(qp, strwhere, fieldlist);
		}

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 发布线上店铺
	protected void UpOnlinehouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		int fbbj = jsonObject.has("fbbj") ? Integer.parseInt(jsonObject.getString("fbbj")) : 1;
		// fbbj:1=发布，0=取消发布
		Onlinehouse dal = new Onlinehouse();
		dal.setAccid(htp.getMaccid());
		dal.setOnhouseid(onhouseid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.doUpOnlinehouse(fbbj), dal.getErrmess());

	}

	// 获取显示线上店铺信息
	protected void GetLinehouseInfo(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		String fieldlist = "a.onhouseid,a.onhousename,a.imagename,a.accid,a.remark,a.tel,a.address,a.notice,a.attention,a.statetag,a.viewtag";
		fieldlist += " ,nvl(b.vtid,0) as vtid,nvl(b.vtname,'未设置默认类型') as vtname";
		String strwhere = "a.onhouseid=" + onhouseid + " and a.accid=" + htp.getMaccid();

		Table tb = new Table();
		Onlinehouse dal = new Onlinehouse();
		dal.setAccid(htp.getMaccid());
		tb = dal.GetList(strwhere, fieldlist).getTable(1);

		Write(response, DbHelperSQL.DataTable2Json(tb));
	}

	// 获取线上店铺的实体店，经营类目，管理员
	protected void GetOnlineinfo(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		// long accid = htp.getMaccid();
		String fieldlist = "f_getonlinetohouselist(a.accid,a.onhouseid ) as houselist";
		fieldlist += ",f_getonlinetotypelist(a.accid,a.onhouseid) as typelist";
		fieldlist += ",f_getonlinetomanagerlist(a.accid,a.onhouseid) as managerlist,viewtag";
		String strwhere = " a.onhouseid=" + onhouseid + " and a.accid=" + htp.getMaccid();
		Table tb = new Table();
		Onlinehouse dal = new Onlinehouse();
		tb = dal.GetList(strwhere, fieldlist).getTable(1);
		Write(response, DbHelperSQL.DataTable2Json(tb));
	}

	// 更改线上店铺资料
	protected void UpdateOnlinehouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long onhouseid = jsonObject.has("onhouseid") ?
		// Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Onlinehouse dal = new Onlinehouse();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Update(), dal.getErrmess());
	}

	// 新建线上店铺
	protected void CreateOnlinehouse(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Onlinehouse dal = new Onlinehouse();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		dal.setUserid(htp.getUserid());

		WriteResult(response, dal.Append(jsonObject), dal.getErrmess());
	}

	// 选择线上店铺的操作人员
	protected void SelectOnline2user(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		Employe dal = new Employe();
		long accid = htp.getMaccid();
		String qry = " select a.epid,a.epname,'0000000000' as xxx,0 as selbj ";
		qry += " from employe a where a.statetag=1 and a.levelid>0 and a.noused=0 and a.accid=" + accid;
		if (onhouseid > 0) {
			qry += " and not exists (select 1 from online2user b where a.epid=b.epid and b.onhouseid=" + onhouseid + ")";
			qry += " union all ";

			qry += " select a.epid,b.epname,a.xxx,1 as selbj";
			qry += " from online2user a join employe b on a.epid=b.epid";
			qry += " where a.onhouseid=" + onhouseid + "  and b.accid=" + accid;
			qry += " and b.levelid>0";
		}
		// WriteLogTXT("debug", "selectonline2user", qry);
		String sort = " selbj desc,epid ";

		QueryParam qp = new QueryParam(page, pagesize, sort, qry);
		Table tb = new Table();

		tb = dal.GetTable(qp);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 选择线上店铺的经营类型
	protected void SelectOnline2type(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		Waretype dal = new Waretype();
		String qry = " select a.typeid,a.typename,0 as selbj ";
		qry += "\n from waretype a where a.p_typeid=0 and a.typeid>0";
		if (onhouseid > 0) {
			qry += "\n  and not exists (select 1 from online2type b where a.typeid=b.typeid and b.onhouseid=" + onhouseid + ")";
			qry += "\n  union all";
			qry += "\n  select a.typeid,a.typename,1 as selbj ";
			qry += "\n  from waretype a where a.p_typeid=0 and a.typeid>0";
			qry += "\n  and exists (select 1 from online2type b where a.typeid=b.typeid and b.onhouseid=" + onhouseid + ")";
		}

		String sort = " selbj desc,typeid ";
		QueryParam qp = new QueryParam(page, pagesize, sort, qry);
		Table tb = new Table();

		tb = dal.GetTable(qp);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 选择线上店铺对应的实体店铺
	protected void SelectOnline2house(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		Warehouse dal = new Warehouse();
		String qry = " select a.houseid,a.housename,0 as selbj ";
		qry += " from warehouse a where a.accid=" + htp.getMaccid() + " and a.statetag=1 and a.noused=0 and a.onhouseid=0";

		if (onhouseid > 0) {
			qry += "  union all";
			qry += "  select a.houseid,a.housename,1 as selbj ";
			qry += "  from warehouse a where a.accid=" + htp.getMaccid() + " and a.statetag=1 and a.noused=0 and a.onhouseid=" + onhouseid;
		}

		String sort = " selbj desc,houseid ";
		QueryParam qp = new QueryParam(page, pagesize, sort, qry);
		Table tb = new Table();

		tb = dal.GetTable(qp);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 保存线上店铺经营类型
	protected void WriteOnline2type(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Online2type dal = new Online2type();
		// System.out.println("WriteOnline2type");
		dal.setLastop(htp.getUsername());
		dal.setOnhouseid(onhouseid);
		WriteResult(response, dal.Append(jsonObject), dal.getErrmess());
	}

	// 保存线上店铺经营类型
	protected void WriteOnline2user(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Online2user dal = new Online2user();
		dal.setLastop(htp.getUsername());
		dal.setOnhouseid(onhouseid);
		WriteResult(response, dal.Append(jsonObject), dal.getErrmess());
	}

	// 保存线上店铺对应的实体店铺
	protected void WriteOnline2house(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Warehouse dal = new Warehouse();

		dal.setLastop(htp.getUsername());
		dal.setOnhouseid(onhouseid);
		dal.setAccid(htp.getMaccid());
		WriteResult(response, dal.Append(jsonObject), dal.getErrmess());
	}

	// 增加订货会商品图片
	protected void AddOmWarepicture(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		// String remark = jsonObject.has("remark") ?
		// jsonObject.getString("remark") : "*";
		Warecode dal = new Warecode();
		dal.setWareid(wareid);
		// dal.setRemark(remark);
		WriteResult(response, dal.doUploadOmpicture(htp.getPictjson()), dal.getErrmess());

	}

	// 增加商品编码主图
	protected void AddWarecodepicture(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		// String remark = jsonObject.has("remark") ?
		// jsonObject.getString("remark") : "*";
		Warecode dal = new Warecode();
		dal.setWareid(wareid);
		// dal.setRemark(remark);
		WriteResult(response, dal.doUploadWarepicture(htp.getPictjson()), dal.getErrmess());

	}

	// 增加潮流搭配资料
	protected void AddVippairs(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Vippairs dal = new Vippairs();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setLastop(htp.getUsername());
		dal.setStatetag(0);
		WriteResult(response, dal.Append(jsonObject, htp.getPictjson()), dal.getErrmess());
	}

	// 更改潮流搭配资料
	protected void UpdateVippairs(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Vippairs dal = new Vippairs();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setLastop(htp.getUsername());
		dal.setStatetag(0);
		WriteResult(response, dal.Update(jsonObject, htp.getPictjson()), dal.getErrmess());
	}

	// 显示潮流搭配
	protected void ListVippairs(HttpServletResponse response, HttpInfo htp, int fs) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = "";
		String strwhere = "";
		if (fs == 0) {
			long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
			fieldlist = "a.paid,a.patitle,a.content,a.remark,a.styletag,a.statetag,a.lastdate,a.onhouseid,a.imagename as pairimagename,b.onhousename,b.imagename,a.imgheight";
			strwhere = " a.onhouseid=" + onhouseid;
		} else {
			fieldlist = "a.paid,a.patitle,a.content,a.remark,a.styletag,a.statetag,a.lastdate,a.onhouseid,a.imagename as pairimagename,b.onhousename ,b.imagename,a.imgheight";
			strwhere = " a.statetag=1";
		}

		String sort = "lastdate  desc,paid";
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		Vippairs dal = new Vippairs();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 销售助手显示商品搭配列表
	protected void ListWarepairs(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = "a.paid,a.patitle,a.content,a.remark,a.styletag,a.statetag,a.lastdate,a.onhouseid,a.imagename as pairimagename,b.onhousename,b.imagename ";
		String strwhere = " a.statetag=3 and a.onhouseid=" + onhouseid;
		if (wareid > 0)
			strwhere += "  and exists (select 1 from VIPpairs1 c where a.paid=c.paid and c.wareid=" + wareid + ")";

		String sort = "lastdate  desc,paid";
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		Vippairs dal = new Vippairs();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 获取潮流搭配
	protected void GetVippairs(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long onhouseid = jsonObject.has("onhouseid") ?
		// Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		float paid = jsonObject.has("paid") ? Float.parseFloat(jsonObject.getString("paid")) : 0;
		Vippairs dal = new Vippairs();
		dal.setPaid(paid);
		// dal.setOnhouseid(onhouseid);
		if (dal.doGetVippairs() == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());
		}
	}

	// 删除潮流搭配
	protected void DelVippairs(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long onhouseid = jsonObject.has("onhouseid") ?
		// Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		float paid = jsonObject.has("paid") ? Float.parseFloat(jsonObject.getString("paid")) : 0;
		Vippairs dal = new Vippairs();
		dal.setPaid(paid);
		WriteResult(response, dal.Delete(), dal.getErrmess());
	}

	// 更改潮流搭配状态
	protected void ChangeVippairs(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long onhouseid = jsonObject.has("onhouseid") ?
		// Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		float paid = jsonObject.has("paid") ? Float.parseFloat(jsonObject.getString("paid")) : 0;
		int zt = jsonObject.has("zt") ? Integer.parseInt(jsonObject.getString("zt")) : 0;
		// zt=0:提交 statetag 0->1
		// zt=1:撤消待审 statetag 1->0
		// zt=2:发布 statetag 2->3
		// zt=3:撤消审核 statetag 2->1
		// zt=4:撤消发布 statetag 3->2
		// zt=5:审核通过 statetag 1->2
		// zt=6:审核驳回 statetag 1->0 要传入refuse
		Vippairs dal = new Vippairs();
		dal.setPaid(paid);
		dal.setLastop(htp.getUsername());

		WriteResult(response, dal.doChangeVippairs(zt), dal.getErrmess());
	}

	// 增加店铺优惠券
	protected void AddHousecoupon(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Housecoupon dal = new Housecoupon();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		// dal.setStatetag(0);
		dal.setLastop(htp.getUsername());
		if (dal.Append() == 0)

			WriteResult(response, 0, dal.getErrmess());
		else
			Write(response, dal.getErrmess());

	}

	// 显示店铺优惠券记录
	protected void ListHousecoupon(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		int zt = jsonObject.has("zt") ? Integer.parseInt(jsonObject.getString("zt")) : 0;
		// zt: 0=在用 1=历史
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = " a.cpid,a.curr,a.xfcurr,a.remark,to_char(a.begindate,'yyyy-mm-dd') as begindate,to_char(a.overdate,'yyyy-mm-dd') as overdate";
		fieldlist += ",'满'||a.xfcurr||'元减'||a.curr||'元' as remark0,a.statetag,a.totalnum,to_char(a.lastdate,'yyyy-mm-dd') as lastdate";
		String strwhere = "  a.onhouseid=" + onhouseid + " ";
		// if (zt == 1) qry += " and
		// to_char(sysdate,'yyyy-mm-dd')>to_char(a.overdate,'yyyy-mm-dd')";
		// else qry += " and
		// to_char(sysdate,'yyyy-mm-dd')<=to_char(a.overdate,'yyyy-mm-dd')";
		if (zt == 1)
			strwhere += " and (to_char(sysdate,'yyyy-mm-dd')>to_char(a.overdate,'yyyy-mm-dd') or a.statetag>1) and a.statetag<3";
		else
			strwhere += " and a.statetag<=1 and to_char(sysdate,'yyyy-mm-dd')<=to_char(a.overdate,'yyyy-mm-dd') ";

		if (!findbox.equals(""))
			strwhere += " and a.remark like '%" + findbox + "%'";
		String sort = " lastdate desc,cpid";
		Housecoupon dal = new Housecoupon();
		QueryParam qp = new QueryParam(page, pagesize, sort);
		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere, fieldlist);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 获取店铺优惠券记录
	protected void GetHousecoupon(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		long cpid = jsonObject.has("cpid") ? Long.parseLong(jsonObject.getString("cpid")) : 0;
		String fieldlist = "cpid,curr,xfcurr,begindate,overdate";
		String strwhere = " cpid=" + cpid + " and onhouseid=" + onhouseid;
		Housecoupon dal = new Housecoupon();
		Table tb = new Table();
		tb = dal.GetList(strwhere, fieldlist).getTable(1);
		Write(response, DbHelperSQL.DataTable2Json(tb));
	}

	// 显示已领用店铺优惠券记录
	protected void ListGetHousecoupon(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;

		String fieldlist = " a.lastdate,a.vcpid,c.vipname,c.mobile,b.curr,b.xfcurr,b.OVERDATE,b.remark";
		fieldlist += ",'满'||b.xfcurr||'元减'||b.curr||'元' as remark0, to_char(a.lastdate,'yyyy-mm-dd') as daystr";

		String strwhere = " b.onhouseid=" + onhouseid;
		String sort = " daystr desc,lastdate desc,vcpid";
		Vipcoupon dal = new Vipcoupon();
		QueryParam qp = new QueryParam(page, pagesize, sort);
		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere, fieldlist);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 更改店铺优惠券状态
	protected void ChangeHousecoupon(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		long cpid = jsonObject.has("cpid") ? Long.parseLong(jsonObject.getString("cpid")) : 0;
		int statetag = jsonObject.has("statetag") ? Integer.parseInt(jsonObject.getString("statetag")) : 0;
		// statetag: 0=取消发布 1=发布 2=过期 3=删除

		Housecoupon dal = new Housecoupon();
		dal.setOnhouseid(onhouseid);
		dal.setCpid(cpid);
		dal.setStatetag(statetag);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Change(), dal.getErrmess());

	}

	// 显示会员可用的优惠券
	protected void ListVipcoupon(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		String houseidstr = jsonObject.has("houseidstr") ? jsonObject.getString("houseidstr") : "";
		long guestid = jsonObject.has("guestid") ? Long.parseLong(jsonObject.getString("guestid")) : 0;
		float xfcurr = jsonObject.has("xfcurr") ? Float.parseFloat(jsonObject.getString("xfcurr")) : 0;
		String fieldlist = "  A.LASTDATE,A.VCPID,B.CURR,B.XFCURR,B.OVERDATE";
		fieldlist += ",'满'||b.xfcurr||'元减'||b.curr||'元('||b.remark||')' as remark";

		String strwhere = " a.usetag=0 and b.statetag=1 and c.guestid=" + guestid + " and c.accid=" + htp.getMaccid();
		strwhere += " and exists (select 1 from warehouse b1 where b.onhouseid=b1.onhouseid ";
		if (houseid > 0)
			strwhere += "  and b1.houseid=" + houseid;
		else {
			strwhere += " and " + Func.getCondition("b1.houseid", houseidstr);
		}
		strwhere += " )";
		strwhere += " and to_char(sysdate,'yyyy-mm-dd')<=to_char(b.overdate,'yyyy-mm-dd')";
		strwhere += " and b.xfcurr<=" + xfcurr;
		String sort = " lastdate desc,vcpid";

		Vipcoupon dal = new Vipcoupon();

		Table tb = new Table();
		tb = dal.GetTable(strwhere, fieldlist, sort);
		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 零售前台给会员发送消费通知
	protected void SendVipmessage(HttpServletResponse response, HttpInfo htp, int bj) throws ServletException, IOException {
		// bj:0=店铺零售，1=商场零售
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		int isvipvalid = htp.getVipvalid();
		// if (Func.subString(qxpublic, 18, 1).equals("1"))
		// isvipvalid = 1;
		if (isvipvalid == 0) {
			WriteResult(response, 5, "系统尚未启用会员C端验证，默认用户同意请求！");
			return;
		}
		int newask = jsonObject.has("newask") ? Integer.parseInt(jsonObject.getString("newask")) : 0;
		// newask=1,如果以前已发了通知，将原通知设为无效
		long vcpid = jsonObject.has("vcpid") ? Long.parseLong(jsonObject.getString("vcpid")) : 0;
		float totalcurr = jsonObject.has("totalcurr") ? Float.parseFloat(jsonObject.getString("totalcurr")) : 0;
		float yhjcurr = jsonObject.has("yhjcurr") ? Float.parseFloat(jsonObject.getString("yhjcurr")) : 0;
		float xfcurr = jsonObject.has("xfcurr") ? Float.parseFloat(jsonObject.getString("xfcurr")) : 0;
		float usecent = jsonObject.has("usecent") ? Float.parseFloat(jsonObject.getString("usecent")) : 0;
		float jfcurr = jsonObject.has("jfcurr") ? Float.parseFloat(jsonObject.getString("jfcurr")) : 0;
		float payvcurr = jsonObject.has("payvcurr") ? Float.parseFloat(jsonObject.getString("payvcurr")) : 0;
		if (yhjcurr == 0 && usecent == 0 && payvcurr == 0) {
			WriteResult(response, 0, "消息内容不允许为空！");
			return;
		}

		String remark = "尊敬的VIP会员:您的购物单 " + noteno + " 将";
		String fs = "";
		if (yhjcurr > 0) {
			remark += "使用满" + Func.FormatNumber(xfcurr) + "元减" + Func.FormatNumber(yhjcurr) + "元优惠券一张";
			fs = ";";
		}
		if (usecent > 0) {
			remark += fs + "使用" + Func.FormatNumber(usecent) + "积分抵扣" + Func.FormatNumber(jfcurr, 2) + "元消费金额";
			fs = ";";
		}
		if (payvcurr > 0) {
			remark += fs + "使用储值卡结账" + Func.FormatNumber(payvcurr, 2) + "元";
		}
		remark += ",本单总金额" + Func.FormatNumber(totalcurr, 2) + "元,请确认是否同意？";
		Vipmessage dal = new Vipmessage();
		dal.setOnhouseid(onhouseid);
		dal.setLastop(htp.getUsername());
		dal.setRemark(remark);
		dal.setAccid(htp.getMaccid());
		dal.setLx(1); // lx:0=通知，1=消费请求
		WriteResult(response, dal.doSendmess(noteno, newask, vcpid, bj), dal.getErrmess());

	}

	// 增加会员消息通知记录
	protected void AddVipmessage(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		String onhousename = jsonObject.has("onhousename") ? jsonObject.getString("onhousename") : "";
		String remark = jsonObject.has("remark") ? jsonObject.getString("remark") : "";
		Vipmessage dal = new Vipmessage();
		dal.setOnhouseid(onhouseid);
		dal.setRemark(remark);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Append(jsonObject, onhousename), dal.getErrmess());
	}

	// 店铺会员列表
	protected void ListVipgroup(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = "a.vipid,a.vipname,a.nickname,a.sex,a.mobile,nvl(b.guestid,0) as guestid ";
		fieldlist += ",nvl(b.vipno,'') as vipno,nvl(b.balcurr,0) as balcurr,nvl(b.balcent,0) as balcent";
		fieldlist += ",nvl(c.vtname,'') as vtname,a.imagename";
		// qry += " from vipgroup a ";
		// qry += " left outer join guestvip b on a.vipid=b.vipid and
		// b.accid="+accid;
		// qry += " join guesttype c on b.vtid=c.vtid ";
		String strwhere = " a.statetag=1 and exists (select 1 from viphouse b where a.vipid=b.vipid and b.onhouseid=" + onhouseid + " and b.statetag=1)";
		if (!findbox.equals(""))
			strwhere += " and (a.vipname like '%" + findbox + "%'  or a.nickname like '%" + findbox + "%'  or a.mobile like '%" + findbox + "%' )";

		String sort = " vipname,vipid";
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		Vipgroup dal = new Vipgroup();

		tb = dal.GetTable(qp, strwhere, fieldlist, htp.getMaccid());

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 显示会员通知记录
	protected void ListVipmessage(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		Vipmessage dal = new Vipmessage();

		String fieldlist = "a.messid,a.lastdate,a.remark,a.vipid,b.vipname,b.nickname,b.mobile,a.rdbj,to_char(a.lastdate,'yyyy-mm-dd') as daystr";
		// qry += " from Vipmessage a join vipgroup b on a.vipid=b.vipid ";

		String strwhere = " a.statetag=1 and a.onhouseid=" + onhouseid;
		if (!findbox.equals(""))
			strwhere += " and (b.vipname like '%" + findbox + "%' or b.nickname like '%" + findbox + "%' or b.mobile like '%" + findbox + "%')";
		String sort = " daystr desc,lastdate desc,messid";
		QueryParam qp = new QueryParam(page, pagesize, sort);
		Table tb = new Table();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 删除会员通知消息
	protected void DelVipmessage(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		float messid = jsonObject.has("messid") ? Float.parseFloat(jsonObject.getString("messid")) : 0;
		Vipmessage dal = new Vipmessage();
		dal.setMessid(messid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Delete(), dal.getErrmess());

	}

	// 增加店铺公告
	protected void AddViphousenotice(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long onhouseid = jsonObject.has("onhouseid") ?
		// Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Viphousenotice dal = new Viphousenotice();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Append(), dal.getErrmess());
	}

	// 显示店铺公告记录
	protected void ListViphousenotice(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = " a.hnid,a.lastdate,a.topical,a.content,a.onhouseid,a.lastop ";
		String strwhere = " a.statetag=1 and a.onhouseid=" + onhouseid + "";
		if (!findbox.equals(""))
			strwhere += " and a.topical like '%" + findbox + "%'";
		String sort = " lastdate desc,hnid";

		Viphousenotice dal = new Viphousenotice();

		QueryParam qp = new QueryParam(page, pagesize, sort);
		Table tb = new Table();

		tb = dal.GetTable(qp, strwhere, fieldlist);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 删除店铺活动公告
	protected void DelViphousenotice(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long hnid = jsonObject.has("hnid") ? Long.parseLong(jsonObject.getString("hnid")) : 0;
		Viphousenotice dal = new Viphousenotice();
		dal.setHnid(hnid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Delete(), dal.getErrmess());
	}

	// 显示会员消费记录
	protected void ListVipsalenote(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long guestid = jsonObject.has("guestid") ? Long.parseLong(jsonObject.getString("guestid")) : 0;
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		Guestvip dal = new Guestvip();
		dal.setAccid(htp.getMaccid());
		dal.setGuestid(guestid);
		dal.setHouseid(onhouseid);
		QueryParam qp = new QueryParam();
		qp.setPageIndex(page);
		qp.setPageSize(pagesize);
		Write(response, dal.doListVipsalenote(qp));

	}

	// 显示VIP订单列表
	protected void ListViporder(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		int cxzt = jsonObject.has("cxzt") ? Integer.parseInt(jsonObject.getString("cxzt")) : 0;
		// statetag:0=草稿 1=提交 2=付款，3=发货，4=签收，5=完成， 6=关闭
		// cxzt:0=待付款，1=待发货，2=已发货，3=完成, 4=退款，5=已关闭
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = "a.id,a.vipid,a.noteno,to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss') as notedate,a.onhouseid,a.statetag,a.totalamt,a.totalcurr,a.curryf,a.remark";
		// 0=未付，1=现金，2=银联卡，3=支付宝，4=微信
		fieldlist += ",a.paytag,case a.paytag when 1 then '现金' when 2 then '银联卡' when 3 then '支付宝' when 4 then '微信' else '' end as paytagname";
		fieldlist += ",a.tktag,a.tkcurr,a.tkremark,a.tkyy,a.delayremark,to_char(a.notedate,'yyyy-mm-dd') as daystr";
		String strwhere = " a.onhouseid=" + onhouseid + " and (a.totalamt<>0 or a.totalcurr<>0)";
		if (!findbox.equals(""))
			strwhere += " and (a.noteno like '%" + findbox.toUpperCase() + "%'  or b.vipname like '%" + findbox + "%' or b.mobile like '%" + findbox + "%')";

		if (cxzt == 0)
			strwhere += " and a.statetag<=1"; // 0=待付款
		else if (cxzt == 1)
			strwhere += " and a.statetag=2"; // 1=待发货
		else if (cxzt == 2)
			strwhere += " and a.statetag=3"; // 2=已发货
		else if (cxzt == 3)
			strwhere += " and a.statetag=4"; // 3=完成
		else if (cxzt == 4)
			strwhere += " and a.statetag=5 "; // 4=退款
		else if (cxzt == 5)
			strwhere += " and a.statetag=6"; // 6=已关闭
		String sort = " daystr desc,notedate desc,id ";
		Viporderh dal = new Viporderh();
		QueryParam qp = new QueryParam(page, pagesize, sort);
		// Table tb = new Table();
		// Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
		// tb = dal.GetTable(qp, strwhere, fieldlist);
		String jsonstr = dal.doListViporder(qp, strwhere, fieldlist);
		Write(response, jsonstr);
	}

	// 获取订单主记录
	protected void GetViporderh(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long vipid = jsonObject.has("vipid") ? Long.parseLong(jsonObject.getString("vipid")) : 0;
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "A.NOTENO,A.NOTEDATE,A.LINKMAN,A.TEL,A.ADDRESS,A.CARRYID,A.REMARK,B.CARRYNAME";
		String strwhere = "  a.vipid=" + vipid + " and a.noteno='" + noteno + "'";
		Viporderh dal = new Viporderh();
		Table tb = new Table();
		tb = dal.GetList(strwhere, fieldlist).getTable(1);
		Write(response, DbHelperSQL.DataTable2Json(tb));
	}

	// 获取订单详情
	protected void GetViporder(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long vipid = jsonObject.has("vipid") ? Long.parseLong(jsonObject.getString("vipid")) : 0;
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		Viporderh dal = new Viporderh();
		dal.setVipid(vipid);
		dal.setNoteno(noteno);
		if (dal.doGetViporder() == 0) {
			WriteResult(response, 0, dal.getErrmess());

		} else {

			WriteResultJson(response, 1, dal.getErrmess());
		}
	}

	// 更改订单实结金额及运费
	protected void UpdateViporder(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long vipid = jsonObject.has("vipid") ? Long.parseLong(jsonObject.getString("vipid")) : 0;
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		float checkcurr = jsonObject.has("checkcurr") ? Float.parseFloat(jsonObject.getString("checkcurr")) : 0;
		float curryf = jsonObject.has("curryf") ? Float.parseFloat(jsonObject.getString("curryf")) : 0;
		Viporderh dal = new Viporderh();
		dal.setVipid(vipid);
		dal.setNoteno(noteno);
		dal.setCheckcurr(checkcurr);
		dal.setCurryf(curryf);
		WriteResult(response, dal.Update(), dal.getErrmess());
	}

	// 会员订单发货
	protected void SendViporder(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long vipid = jsonObject.has("vipid") ? Long.parseLong(jsonObject.getString("vipid")) : 0;
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		String ydnoteno = jsonObject.has("ydnoteno") ? jsonObject.getString("ydnoteno") : "";
		String address = jsonObject.has("address") ? jsonObject.getString("address") : "";
		long carryid = jsonObject.has("carryid") ? Long.parseLong(jsonObject.getString("carryid")) : 0;
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		Viporderh dal = new Viporderh();
		dal.setVipid(vipid);
		dal.setNoteno(noteno);
		dal.setYdnoteno(ydnoteno);
		dal.setCarryid(carryid);
		dal.setAddress(address);
		WriteResult(response, dal.doSendViporder(htp.getMaccid(), houseid, htp.getUsername()), dal.getErrmess());

	}

	// 删除VIP订单
	protected void DelViporder(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long vipid = jsonObject.has("vipid") ? Long.parseLong(jsonObject.getString("vipid")) : 0;
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		Viporderh dal = new Viporderh();
		dal.setNoteno(noteno);
		dal.setVipid(vipid);
		WriteResult(response, dal.Delete(), dal.getErrmess());
	}

	// 关闭VIP订单
	protected void CloseViporder(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long vipid = jsonObject.has("vipid") ? Long.parseLong(jsonObject.getString("vipid")) : 0;
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		Viporderh dal = new Viporderh();
		dal.setNoteno(noteno);
		dal.setVipid(vipid);
		WriteResult(response, dal.Close(), dal.getErrmess());
	}

	// // 上传商品图片 增加更改warepicture中的图片
	protected void UploadWarepicture(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		String lastop = htp.getUsername();
		long accid = htp.getMaccid();
		if (accid == 1000 && (lastop.equals("演示") || lastop.equals("枫杨"))) {
			WriteResult(response, 0, "这是演示套账，不允许增加图片！");
			return;
		}

		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		String wareno = jsonObject.has("wareno") ? jsonObject.getString("wareno").replace("'", "''") : "";
		int dhhbj = jsonObject.has("dhhbj") ? Integer.parseInt(jsonObject.getString("dhhbj")) : 0;
		// dhhbj:1=订货会图片
		int ordid = jsonObject.has("ordid") ? Integer.parseInt(jsonObject.getString("ordid")) : 1;
		float recid = jsonObject.has("recid") ? Float.parseFloat(jsonObject.getString("recid")) : 0;

		JSONObject jsonObject1 = JSONObject.fromObject(htp.getPictjson());
		String base64string = jsonObject1.has("base64string") ? jsonObject1.getString("base64string") : "";
		if (base64string.equals("")) {
			WriteResult(response, 0, "图片参数无效！");
			return;
		}
		String retcs = Func.Base64UpFastDFS(base64string);
		if (retcs.substring(0, 1) == "0") // 图片文件上传不成功
		{
			WriteResult(response, 0, retcs.substring(1));
			return;
		}
		String imagename = retcs.substring(1);

		Warecode dal = new Warecode();
		dal.setAccid(accid);
		dal.setLastop(lastop);
		dal.setWareid(wareid);
		dal.setWareno(wareno.toUpperCase());
		dal.setImagename(imagename);
		WriteResult(response, dal.doUpdateWarePicture(recid, dhhbj, ordid), dal.getErrmess());

	}

	// 增加商品陈列图片
	protected void AddWarepicture(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		String lastop = htp.getUsername();
		long accid = htp.getMaccid();
		if (accid == 1000 && (lastop.equals("演示") || lastop.equals("枫杨"))) {
			WriteResult(response, 0, "这是演示套账，不允许增加图片！");
			return;
		}

		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		int dhhbj = jsonObject.has("dhhbj") ? Integer.parseInt(jsonObject.getString("dhhbj")) : 0;
		if (dhhbj == 1) {
			Orderpicture dal = new Orderpicture();
			dal.setWareid(wareid);
			dal.setLastop(lastop);
			if (dal.Append(accid, htp.getPictjson()) == 0)

				WriteResult(response, 0, dal.getErrmess());
			else
				WriteResultJson(response, 1, dal.getErrmess());
		} else {
			Warepicture dal = new Warepicture();
			dal.setWareid(wareid);
			dal.setLastop(lastop);
			if (dal.Append(accid, htp.getPictjson()) == 0)

				WriteResult(response, 0, dal.getErrmess());
			else
				WriteResultJson(response, 1, dal.getErrmess());
		}

	}

	// 删除商品图片
	protected void DelWarepicture(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		float imgid = jsonObject.has("imgid") ? Float.parseFloat(jsonObject.getString("imgid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		int dhhbj = jsonObject.has("dhhbj") ? Integer.parseInt(jsonObject.getString("dhhbj")) : 0;
		if (dhhbj == 1) {
			Orderpicture dal = new Orderpicture();
			dal.setWareid(wareid);
			dal.setLastop(htp.getUsername());
			dal.setId(imgid);
			WriteResult(response, dal.Delete(), dal.getErrmess());
		} else {
			Warepicture dal = new Warepicture();
			dal.setWareid(wareid);
			dal.setLastop(htp.getUsername());
			dal.setId(imgid);
			WriteResult(response, dal.Delete(), dal.getErrmess());
		}

	}

	// 更改商品图片
	protected void UpdateWarepicture(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		float imgid = jsonObject.has("imgid") ? Float.parseFloat(jsonObject.getString("imgid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		int dhhbj = jsonObject.has("dhhbj") ? Integer.parseInt(jsonObject.getString("dhhbj")) : 0;
		if (dhhbj == 1) {// 订货会图片
			Orderpicture dal = new Orderpicture();
			dal.setWareid(wareid);
			dal.setLastop(htp.getUsername());
			dal.setId(imgid);
			WriteResult(response, dal.Update(htp.getPictjson()), dal.getErrmess());
		} else {
			Warepicture dal = new Warepicture();
			dal.setWareid(wareid);
			dal.setLastop(htp.getUsername());
			dal.setId(imgid);

			WriteResult(response, dal.Update(htp.getPictjson()), dal.getErrmess());
		}

	}

	// 更改商品图片顺序
	protected void SortWarepicture(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// float imgid = jsonObject.has("imgid") ?
		// Float.parseFloat(jsonObject.getString("imgid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		int dhhbj = jsonObject.has("dhhbj") ? Integer.parseInt(jsonObject.getString("dhhbj")) : 0;
		Warecode dal = new Warecode();
		dal.setWareid(wareid);
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Sort(jsonObject, dhhbj), dal.getErrmess());

	}

	// 显示商品图片
	protected void ListWarepicture(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// float imgid = jsonObject.has("imgid") ?
		// Float.parseFloat(jsonObject.getString("imgid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		int dhhbj = jsonObject.has("dhhbj") ? Integer.parseInt(jsonObject.getString("dhhbj")) : 0;

		String qry = " select id,imagename from (";

		Table tb = new Table();
		if (dhhbj == 1) {// 订货会
			qry += "select id,imagename,orderid from orderpicture where wareid=" + wareid + " and orderid>0 order by orderid,id";
			qry += ") where rownum<=10";
			Orderpicture dal = new Orderpicture();

			tb = dal.GetTable(qry);

			Write(response, DbHelperSQL.DataTable2Json(tb));
		} else {
			qry += "select id,imagename,orderid from warepicture where wareid=" + wareid + " and orderid>0 order by orderid,id";
			qry += ") where rownum<=10";
			Warepicture dal = new Warepicture();
			tb = dal.GetTable(qry);

			Write(response, DbHelperSQL.DataTable2Json(tb));
		}

	}

	// 显示陈列类别
	protected void ListDisplaycode(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int usetag = jsonObject.has("usetag") ? Integer.parseInt(jsonObject.getString("usetag")) : 0;
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Displaycode dal = new Displaycode();
		String fieldlist = " dspid,dspname,orderid,usetag";
		String strwhere = " onhouseid=" + onhouseid;
		if (usetag < 2)
			strwhere += " and usetag=" + usetag;
		String sort = " orderid,dspid";

		Table tb = new Table();

		tb = dal.GetTable(strwhere, fieldlist, sort);

		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 显示陈列类别及款数
	protected void ListDisplaycode1(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Displaycode dal = new Displaycode();
		String qry = " select a.dspid,a.dspname,a.orderid,count(*) as num from DISPLAYCODE a";
		qry += " left outer join waredisplay b on a.dspid=b.dspid ";
		qry += " where a.onhouseid=" + onhouseid + " and a.usetag=1 and a.statetag=1";
		qry += " group by a.dspid,a.dspname,a.orderid";
		qry += " order by a.orderid,a.dspid";
		Table tb = new Table();

		tb = dal.GetTable(qry);

		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 增加陈列类别
	protected void AddDisplaycode(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long onhouseid = jsonObject.has("onhouseid") ?
		// Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Displaycode dal = new Displaycode();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		// dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		dal.setStatetag(1);
		dal.setUsetag(1);
		dal.setOrderid((long) 99);
		WriteResult(response, dal.Append(), dal.getErrmess());

	}

	// 修改陈列类别
	protected void UpdateDisplaycode(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long onhouseid = jsonObject.has("onhouseid") ?
		// Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Displaycode dal = new Displaycode();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		// dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		// dal.setStatetag(1);
		// dal.setUsetag(1);
		// dal.setOrderid((long) 99);
		WriteResult(response, dal.Update(), dal.getErrmess());
	}

	// 删除陈列类别
	protected void DelDisplaycode(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		long dspid = jsonObject.has("dspid") ? Long.parseLong(jsonObject.getString("dspid")) : 0;
		Displaycode dal = new Displaycode();
		dal.setOnhouseid(onhouseid);
		dal.setDspid(dspid);
		WriteResult(response, dal.Delete(), dal.getErrmess());

	}

	// 调整陈列类别顺序
	protected void WriteorderDisplaycode(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Displaycode dal = new Displaycode();
		dal.setOnhouseid(onhouseid);
		WriteResult(response, dal.Sort(jsonObject), dal.getErrmess());
	}

	// 显示陈列商品信息
	protected void GetWaredisplay(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		Wareonline dal = new Wareonline();
		dal.setOnhouseid(onhouseid);
		dal.setWareid(wareid);
		int ret = dal.doGetWaredisplay();
		if (ret == 0) {
			WriteResult(response, 0, dal.getErrmess());

		} else {
			WriteResultJson(response, 1, dal.getErrmess());
		}
	}

	// 商品陈列选商品帮助
	protected void GetWareCodeDisplayHelp(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		int qxbj = htp.getQxbj();

		Warecode dal = new Warecode();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		// dal.setHouseid(houseid);
		// Long houseid = dal.getHouseid();
		// Long areaid = dal.getAreaid();
		// System.out.println("1111");
		Long brandid = dal.getBrandid();
		Long typeid = dal.getTypeid();
		Long provid = dal.getProvid();
		// Integer noused = dal.getNoused();
		String warename = dal.getWarename();
		String wareno = dal.getWareno();
		String seasonname = dal.getSeasonname();
		// String locale = dal.getLocale();
		// String prodno = dal.getProdno();
		String prodyear = dal.getProdyear();
		// String lastop = dal.getLastop();
		String sort = "  wareno,wareid";// +order;

		// System.out.println("2222");

		String strwhere = " a.ACCID=" + htp.getMaccid() + " and a.STATETAG=1";
		strwhere += " and a.NOUSED=0";// +noused;

		if (warename != null && !warename.equals(""))
			strwhere += " and (a.warename like '%" + warename + "%' or a.shortname like '%" + warename.toUpperCase() + "%')";
		if (wareno != null && !wareno.equals(""))
			strwhere += " and a.wareno like '%" + wareno.toUpperCase() + "%'";
		if (seasonname != null && !seasonname.equals(""))
			strwhere += " and a.seasonname = '" + seasonname + "'";
		if (prodyear != null && !prodyear.equals(""))
			strwhere += " and a.prodyear = '" + prodyear + "'";
		if (brandid != null && brandid >= 0)
			strwhere += " and a.brandid = " + brandid;

		if (typeid != null && typeid >= 0)
			if (typeid > 0 && typeid < 1000) {
				strwhere += "    and exists (select 1 from waretype t where t.typeid=a.typeid and t.p_typeid= " + typeid + ")";
			} else {
				strwhere += "    and a.typeid=" + typeid;
			}

		if (findbox != null && !findbox.equals("")) {
			strwhere += " and (a.wareno like '%" + findbox.toUpperCase() + "%' or a.prodno like '%" + findbox.toUpperCase() + "%' or a.shortname like '%" + findbox.toUpperCase() + "%' or a.warename like '%" + findbox
					+ "%' or c.brandname = '" + findbox + "' or a.SEASONNAME like '%" + findbox + "%' or a.prodyear='" + findbox + "'";
			if (Func.isNumber(findbox))
				strwhere += " or a.retailsale=" + findbox;
			strwhere += ")";
		}

		if (qxbj == 1) // 1 启用权限控制
			strwhere += " and (a.brandid=0 or exists (select 1 from employebrand x where a.brandid=x.brandid and x.epid=" + htp.getUserid() + "))";
		if (provid != null && provid > 0) {
			strwhere += " and (a.provid=0 or a.provid=" + provid + ")";
		}
		if (onhouseid > 0) {
			strwhere += " and not exists (select 1 from wareonline x where a.wareid=x.wareid and x.onhouseid=" + onhouseid + ")";

		}
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = " A.WAREID,A.WARENO,A.WARENAME,A.UNITS,A.SHORTNAME,A.BRANDID,A.SEASONNAME,A.ACCID,A.TYPEID"
					+ ",A.PRODYEAR,A.PRODNO,A.ENTERSALE,A.RETAILSALE,A.SALE1,A.SALE2,A.SALE3,A.SALE4,A.SALE5,A.REMARK,A.SIZEGROUPNO"
					+ ",A.LYFS,A.ACCID1,A.WAREID1,A.NOUSED,A.STATETAG,A.LASTOP,C.BRANDNAME,B.TYPENAME,B.FULLNAME,A.IMAGENAME0";
		fieldlist += ",a.ssdate,a.xsamount,a.lastdate";
		// if (houseid > 0)
		// fieldlist += ",case when h.retailsale is null then 0 else
		// h.retailsale end as retailsale0";
		// else
		// fieldlist += ",a.retailsale as retailsale0";
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere, fieldlist);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 保存陈列商品信息
	protected void WriteWaredisplay(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long onhouseid = jsonObject.has("onhouseid") ?
		// Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		// long wareid = jsonObject.has("wareid") ?
		// Long.parseLong(jsonObject.getString("wareid")) : 0;
		Wareonline dal = new Wareonline();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		// dal.setOnhouseid(onhouseid);
		// dal.setWareid(wareid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.doWriteWaredisplay(jsonObject), dal.getErrmess());

	}

	// 显示陈列商品列表
	protected void ListWaredisplay(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		long dspid = jsonObject.has("dspid") ? Long.parseLong(jsonObject.getString("dspid")) : 0;
		// int statetag = jsonObject.has("statetag") ?
		// Integer.parseInt(jsonObject.getString("statetag")) : 2;
		int uptag = jsonObject.has("uptag") ? Integer.parseInt(jsonObject.getString("uptag")) : 2;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";

		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = " A.WAREID,A.REMARK as warename,b.wareremark,A.SALEPRICE,b.wareno,B.UNITS,A.LASTDATE,b.IMAGENAME0,a.statetag,a.uptag";
		String strwhere = " A.onhouseid=" + onhouseid;
		if (!findbox.equals(""))
			strwhere += " and (a.remark like '%" + findbox + "%' or b.wareno like '%" + findbox.toUpperCase() + "%')";
		// if (statetag < 2) qry += " and a.statetag=" + statetag;
		if (uptag < 2)
			strwhere += " and a.uptag=" + uptag;
		if (dspid > 0) {
			strwhere += " AND exists (select 1 from WAREDISPLAY c where a.wareid=c.wareid ";
			strwhere += " and c.DSPID=" + dspid;
			strwhere += " )";
		}
		String sort = "lastdate desc";
		QueryParam qp = new QueryParam(page, pagesize, sort);
		Wareonline dal = new Wareonline();
		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere, fieldlist);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 更改陈列商品状态(上下架)
	protected void ChangeWaredisplay(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		int optag = jsonObject.has("optag") ? Integer.parseInt(jsonObject.getString("optag")) : -1;
		// optag:0=下架，1=上架，2=发布，3=取消发布
		// statetag:0=下架 ，1=上架
		Wareonline dal = new Wareonline();
		dal.setOnhouseid(onhouseid);
		dal.setWareid(wareid);
		dal.setLastop(htp.getUsername());
		dal.setUptag(optag);
		WriteResult(response, dal.doUpAndDown(), dal.getErrmess());

	}

	// 成批更改陈列商品状态(上下架)
	protected void ChangeAllWaredisplay(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		int optag = jsonObject.has("optag") ? Integer.parseInt(jsonObject.getString("optag")) : -1;
		// optag:0=下架，1=上架，2=发布，3=取消发布 ,4=删除,5=分组到
		Wareonline dal = new Wareonline();
		dal.setOnhouseid(onhouseid);
		dal.setLastop(htp.getUsername());
		dal.setUptag(optag);
		WriteResult(response, dal.doAllUpAndDown(jsonObject), dal.getErrmess());

	}

	// 删除陈列商品
	protected void DelWaredisplay(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Waredisplay dal = new Waredisplay();
		dal.setWareid(wareid);

		WriteResult(response, dal.Delete(onhouseid), dal.getErrmess());
	}

	// 增加相关搭配商品
	protected void AddWareonline1(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		long wareid1 = jsonObject.has("wareid1") ? Long.parseLong(jsonObject.getString("wareid1")) : 0;
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Wareonline1 dal = new Wareonline1();
		dal.setOnhouseid(onhouseid);
		dal.setWareid(wareid);
		dal.setWareid1(wareid1);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Append(), dal.getErrmess());
	}

	// 删除相关搭配商品
	protected void DelWareonline1(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		long wareid1 = jsonObject.has("wareid1") ? Long.parseLong(jsonObject.getString("wareid1")) : 0;
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		Wareonline1 dal = new Wareonline1();
		dal.setOnhouseid(onhouseid);
		dal.setWareid(wareid);
		dal.setWareid1(wareid1);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Delete(), dal.getErrmess());
	}

	// 选择相关搭配商品列表
	protected void ListWareonline1(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long onhouseid = jsonObject.has("onhouseid") ? Long.parseLong(jsonObject.getString("onhouseid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		// int uptag = jsonObject.has("uptag") ?
		// Integer.parseInt(jsonObject.getString("uptag")) : 2;

		String fieldlist = "a.wareid,b.imagename0";
		String strwhere = " a.onhouseid=" + onhouseid + " and a.wareid<>" + wareid;
		strwhere += " and a.uptag=1";
		strwhere += " and not exists (select 1 from wareonline1 c where a.onhouseid=c.onhouseid and c.wareid=" + wareid + " and a.wareid=c.wareid1)";
		String sort = "wareid ";

		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		Wareonline dal = new Wareonline();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// VIP后台接口end
	// =================================
	// 采购商城begin
	// 新增商城店铺记录
	protected void AddCityhouseRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		Cityhouse dal = new Cityhouse();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Append(jsonObject), dal.getErrmess());

	}

	// 删除指定商城id信息
	protected void DelCityhouseByID(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String lastop = htp.getUsername();
		long accid = htp.getMaccid();
		if (accid == 1000 && (lastop.equals("演示") || lastop.equals("枫杨"))) {
			WriteResult(response, 0, "这是演示套账，不允许删除记录！");
			return;
		}
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;

		Cityhouse dal = new Cityhouse();
		dal.setCthouseid(cthouseid);
		dal.setAccid(accid);
		dal.setLastop(lastop);

		WriteResult(response, dal.Delete(), dal.getErrmess());
	}

	// 更新指定商城店铺id信息
	protected void UpdateCityhouseByID(HttpServletResponse response, HttpInfo htp, int fs) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String lastop = htp.getUsername();
		long accid = htp.getMaccid();
		if (accid == 1000 && (lastop.equals("演示") || lastop.equals("枫杨"))) {
			WriteResult(response, 0, "这是演示套账，不允许修改记录！");
			return;
		}
		Cityhouse dal = new Cityhouse();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Update(jsonObject, fs), dal.getErrmess());
	}

	// 获取指定商城店铺id信息
	protected void GetCityhouseByID(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		Cityhouse dal = new Cityhouse();
		dal.setCthouseid(cthouseid);
		dal.setAccid(htp.getMaccid());
		if (dal.doGetCityhouse() == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());

		}
	}

	// 加盟商获取供应商指定商城店铺id信息
	protected void GetProvCityhouseByID(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		Cityhouse dal = new Cityhouse();
		dal.setCthouseid(cthouseid);
		dal.setAccid(htp.getMaccid());
		if (dal.doGetProvCityhouse() == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());

		}
	}

	// 获取我的商城店铺列表
	protected void GetCityHouseList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : "CTHOUSEID";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		sort += " " + order + ",rowid";
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "CTHOUSEID,CTHOUSENAME,ACCID,ADDRESS,TEL,REMARK,OPENTAG,STATETAG,LASTOP,LASTDATE,QXCS";

		String strwhere = " a.STATETAG<=1 and a.ACCID=" + htp.getMaccid();
		if (!findbox.equals(""))
			strwhere += " and (CTHOUSENAME like '%" + findbox + "%' or shortname like '%" + findbox.toUpperCase() + "%')";

		Cityhouse dal = new Cityhouse();
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 获取商城店铺商品列表
	protected void GetCityHousewareList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		int newtag = jsonObject.has("newtag") ? Integer.parseInt(jsonObject.getString("newtag")) : 0;
		// newtag:0=商品，1=新品,2=所有
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		// String fieldlist = jsonObject.has("fieldlist") ?
		// jsonObject.getString("fieldlist") : "*";
		// String sort = jsonObject.has("sort") ? jsonObject.getString("sort") :
		// "CTHOUSEID";
		// String order = jsonObject.has("order") ?
		// jsonObject.getString("order") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;

		String fieldlist = " a.id,a.wareid,b.wareno,a.warename0,a.haveamt,a.opendate,a.citysale,b.retailsale,b.imagename0";
		fieldlist += ",case when a.newtag=1 or floor(a.opendate-sysdate+1)>0 then floor(a.opendate-sysdate+1) else 0 end as openday"; // 如果是新品，计算允许游客查看天数

		String strwhere = " a.cthouseid=" + cthouseid + " and b.statetag=1";
		if (!findbox.equals("")) {
			strwhere += " and (e.brandname like '%" + findbox + "%' or c.typename like '%" + findbox + "%' ";
			strwhere += " or b.warename like '%" + findbox + "%' or b.shortname like '%" + findbox.toUpperCase() + "%' or b.wareno like '%" + findbox.toUpperCase() + "%')";
		}
		if (newtag < 2)
			strwhere += " and a.newtag=" + newtag;
		Cityhouseware dal = new Cityhouseware();
		QueryParam qp = new QueryParam(page, pagesize, "id");

		Table tb = new Table();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 获取商城店铺授权买家列表
	protected void GetCityHousebuyerList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		// String findbox = jsonObject.has("findbox") ?
		// jsonObject.getString("findbox").trim().replace("'", "''") : "";
		// String fieldlist = jsonObject.has("fieldlist") ?
		// jsonObject.getString("fieldlist") : "*";
		// String sort = jsonObject.has("sort") ? jsonObject.getString("sort") :
		// "CTHOUSEID";
		// String order = jsonObject.has("order") ?
		// jsonObject.getString("order") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		long accid = htp.getMaccid();
		String qry = "select a.buyaccid,c.accname,b.custname,b.address,b.tel,b.linkman,b.mobile,a.id ,1 as selbj";
		qry += "\n from accconnect a join customer b on a.sellcustid=b.custid";
		qry += "\n join accreg c on a.buyaccid=c.accid";
		qry += "\n where a.sellaccid=" + accid;
		qry += "\n and exists (select 1 from cityhousebuyer a1 where a1.buyaccid=a.buyaccid and a1.cthouseid=" + cthouseid + ")";
		qry += "\n union all";
		qry += "\n select a.buyaccid,c.accname,b.custname,b.address,b.tel,b.linkman,b.mobile,a.id ,0 as selbj";
		qry += "\n from accconnect a join customer b on a.sellcustid=b.custid";
		qry += "\n join accreg c on a.buyaccid=c.accid";
		qry += "\n where a.sellaccid=" + accid;
		qry += "\n and not exists (select 1 from cityhousebuyer a1 where a1.buyaccid=a.buyaccid and a1.cthouseid=" + cthouseid + ")";
		String sort = " buyaccid,id ";
		Accconnect dal = new Accconnect();
		QueryParam qp = new QueryParam(page, pagesize, sort, qry);

		Table tb = new Table();

		tb = dal.GetTable(qp);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 写商城店铺授权供应商(卖家)
	protected void Writecityhousebuyer(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		long buyaccid = jsonObject.has("buyaccid") ? Long.parseLong(jsonObject.getString("buyaccid")) : 0;
		int value = jsonObject.has("value") ? Integer.parseInt(jsonObject.getString("value")) : 0;
		Cityhousebuyer dal = new Cityhousebuyer();
		dal.setCthouseid(cthouseid);
		dal.setBuyaccid(buyaccid);
		dal.setLastop(htp.getUsername());

		WriteResult(response, dal.Append(value), dal.getErrmess());
	}

	// 写全部商城店铺授权供应商(卖家)
	protected void WriteAllCityhousebuyer(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		int value = jsonObject.has("value") ? Integer.parseInt(jsonObject.getString("value")) : 0;
		Cityhousebuyer dal = new Cityhousebuyer();
		dal.setCthouseid(cthouseid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Append(value, htp.getMaccid()), dal.getErrmess());
	}

	// 商城选择商品
	protected void CityhouseSelectWarecode(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		long userid = htp.getUserid();
		int qxbj = htp.getQxbj();

		String sort = "  ssdate desc,wareno,wareid";// +order;
		String strwhere = " a.ACCID=" + htp.getMaccid() + " and a.STATETAG=1";
		strwhere += " and a.NOUSED=0";// +noused;
		if (!findbox.equals("")) {
			strwhere += " and (a.wareno like '%" + findbox.toUpperCase() + "%' or a.shortname like '%" + findbox.toUpperCase() + "%' or a.warename like '%" + findbox + "%' or c.brandname = '" + findbox
					+ "' or a.SEASONNAME like '%" + findbox + "%' or a.prodyear='" + findbox + "'";
			if (Func.isNumber(findbox))
				strwhere += " or a.retailsale=" + findbox;
			strwhere += ")";
		}
		if (qxbj == 1) // 1 启用权限控制
			strwhere += " and (a.brandid=0 or exists (select 1 from employebrand x where a.brandid=x.brandid and x.epid=" + userid + "))";

		if (cthouseid > 0) // 转入订货员编码，不显示已订货的商品,表示订货会程序选商品， 要取所有商品
		{
			strwhere += " and not exists (select 1 from cityhouseware x where a.wareid=x.wareid and x.cthouseid=" + cthouseid + ")";
			sort = " wareno,wareid ";
		}
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = " A.WAREID,A.WARENO,A.WARENAME,A.UNITS,A.SHORTNAME,A.BRANDID,A.SEASONNAME,A.ACCID,A.TYPEID" //
					+ ",A.PRODYEAR,A.PRODNO,A.ENTERSALE,A.RETAILSALE,A.REMARK,A.SIZEGROUPNO" //
					+ ",A.NOUSED,A.STATETAG,C.BRANDNAME,B.TYPENAME,B.FULLNAME,A.IMAGENAME0";
		fieldlist += ",a.ssdate,a.lastdate";
		Warecode dal = new Warecode();
		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();

		tb = dal.GetTable(qp, strwhere, fieldlist);

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
		int jmbj = jsonObject.has("jmbj") ? Integer.parseInt(jsonObject.getString("jmbj")) : 2;
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
		if (jmbj == 3) // 查看自已的商城
		{
			strwhere += " and a.accid=" + accid;
		} else {
			strwhere += " and a.accid<>" + accid;
			if (jmbj == 0) // 未加盟的商家:对非加盟商开放且未加盟
			{
				strwhere += " and a.opentag=1 and not exists (select 1 from accconnect b where a.accid=b.sellaccid and b.buyaccid=" + accid + ") ";
			} else if (jmbj == 1) // 已加盟商家
			{
				strwhere += " and exists (select 1 from accconnect b where a.accid=b.sellaccid and b.buyaccid=" + accid + ") ";
			} else {
				strwhere += " and (a.opentag=1 or a.opentag=0 and exists (select 1 from accconnect b where a.accid=b.sellaccid and b.buyaccid=" + accid + ") )";
			}
		}

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

	// 成批新增商城店铺商品记录
	protected void AddCityhousewareRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		int newtag = jsonObject.has("newtag") ? Integer.parseInt(jsonObject.getString("newtag")) : 0;
		// newtag:0=商品，1=新品
		Cityhouseware dal = new Cityhouseware();
		dal.setCthouseid(cthouseid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Append(jsonObject, newtag, htp.getMaccid()), dal.getErrmess());
	}

	// 更改商城店铺商品记录
	protected void UpdateCityhousebywareid(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long cthouseid = jsonObject.has("cthouseid") ?
		// Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		// long wareid = jsonObject.has("wareid") ?
		// Long.parseLong(jsonObject.getString("wareid")) : 0;
		// int newtag = jsonObject.has("newtag") ?
		// Integer.parseInt(jsonObject.getString("newtag")) : 0;
		// // newtag:0=商品，1=新品
		Cityhouseware dal = new Cityhouseware();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		// dal.setCthouseid(cthouseid);
		// dal.setWareid(wareid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Update(), dal.getErrmess());
	}

	// 删除商城店铺商品记录
	protected void DelCityhousewareRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		Cityhouseware dal = new Cityhouseware();
		dal.setCthouseid(cthouseid);
		dal.setWareid(wareid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Delete(), dal.getErrmess());
	}

	// 清空商城店铺商品记录
	protected void ClearCityhousewareRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		int newtag = jsonObject.has("newtag") ? Integer.parseInt(jsonObject.getString("newtag")) : 0;
		// newtag:0=商品，1=新品
		Cityhouseware dal = new Cityhouseware();
		dal.setCthouseid(cthouseid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Delete(newtag), dal.getErrmess());
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

	// 显示商城店铺订货商品排行榜
	protected void ListCityorderwaresort(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		long buyaccid = htp.getMaccid();
		String qry = " select a.wareid,b.wareno,b.warename,b.units,a.amount,a.curr,a.tamount,b.imagename0 from (";
		qry += "\n select wareid,sum(amount) as amount,sum(curr) as curr,sum(tamount) as tamount from (";
		qry += "\n select a.wareid,sum(a.amount) as amount,sum(a.curr) as curr,0 as tamount ";
		qry += "\n from cityorder a  ";
		qry += "\n join warecode b on a.wareid=b.wareid  ";
		qry += "\n where a.cthouseid=" + cthouseid + " and a.ordaccid=" + buyaccid + " and a.amount>0";
		if (!findbox.equals(""))
			qry += "\n and (b.warename like '%" + findbox + "%' or b.wareno like  '%" + findbox.toUpperCase() + "%')";
		qry += "\n group by a.wareid ";
		qry += "\n union all ";
		// 所有订货
		qry += "\n select b.wareid,0 as amount,0 as curr,sum(b.amount) as tamount  ";
		qry += "\n from custorderh a join custorderm b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n join warecode c on b.wareid=c.wareid  ";
		qry += "\n where a.statetag=1 and b.amount>0";
		qry += "\n and a.NOTEDATE >= to_date('" + htp.getAccdate() + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		if (!findbox.equals(""))
			qry += "\n and (c.warename like '%" + findbox + "%' or c.wareno like  '%" + findbox.toUpperCase() + "%')";
		// qry += " and exists (select 1 from cityorder a1 where
		// a1.wareid=b.wareid and a1.cthouseid=" + cthouseid + " and
		// a1.ordaccid=" + accid + ")";
		qry += "\n and exists (select 1 from CITYHOUSEWARE a1 where a1.wareid=b.wareid and a1.cthouseid=" + cthouseid + " and a1.onlinetag=1)";
		qry += "\n group by b.wareid ) ";
		qry += "\n group by wareid  having sum(amount)>0 ";
		qry += "\n )  a join warecode b on a.wareid=b.wareid ";

		// System.out.println(qry);
		String sort = " tamount desc,wareid";
		QueryParam qp = new QueryParam(page, pagesize, sort, qry);

		qp.setSumString("nvl(sum(amount),0) as totalamt,nvl(sum(curr),0) as totalcurr");

		Cityorder dal = new Cityorder();

		Table tb = new Table();
		tb = dal.GetTable(qp);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 获取商场采购订单历史订单
	protected void GetCityHouseProvorderhList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		// String remark = jsonObject.has("remark") ?
		// jsonObject.getString("remark") : "";
		// String nowdatestr = htp.getNowdate();//
		// Func.DateToStr(pFunc.getServerdatetime(), "yyyy-MM-dd");
		String currdate = htp.getNowdate();
		String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : currdate;
		String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : currdate;
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		long provid = jsonObject.has("provid") ? Long.parseLong(jsonObject.getString("provid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		// ==========================
		String accbegindate = htp.getAccdate();// 账套开始使用日期
		int maxday = htp.getMaxday();

		// maxday = 10;
		// 校验开始查询日期
		MinDateValid mdv = new MinDateValid(mindate, currdate, accbegindate, maxday);
		mindate = mdv.getMindate();
		// String mess = mdv.getErrmess();
		if (mindate.compareTo(maxdate) > 0)
			maxdate = mindate; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期
		// ==========================

		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = " a.noteno,a.notedate,a.provid,b.provname,a.cthouseid,c.cthousename,a.totalamt,a.totalcurr,a.operant,a.id";
		String strwhere = "  a.ACCID=" + htp.getMaccid() + " and a.statetag=1 and a.cthouseid>0";
		strwhere += " and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		if (!findbox.equals(""))
			strwhere += " and (a.noteno like '%" + findbox.toUpperCase() + "%' or c.cthousename like '%" + findbox + "%' or b.provname like '%" + findbox + "%' or b.shortname like '%" + findbox.toUpperCase() + "%')";
		if (provid > 0)
			strwhere += " and a.PROVID = " + provid;
		if (cthouseid > 0)
			strwhere += " and a.cthouseid = " + cthouseid;
		String sort = " notedate desc,rowid ";

		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		Provorderh dal = new Provorderh();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 商城店铺订货商品列表
	protected void ListCityorderware(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : "";
		int sortid = jsonObject.has("sortid") ? Integer.parseInt(jsonObject.getString("sortid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		// String fieldlist = "
		// a.wareid,b.wareno,b.warename,b.units,b.imagename0,sum(a.amount) as
		// amount,sum(a.curr) as curr";

		String sort = "";
		// if (sortid == 1) sort = " id ";
		// else
		if (sortid == 1)
			sort = " amount ";
		else if (sortid == 2)
			sort = " curr ";
		else
			sort = " wareno ";

		sort += order + ",wareid";

		// // string qry = "select sum(a.amount) as totalamount,sum(a.curr) as
		// totalcurr";
		// // qry += " FROM cityorder a";
		// String strwhere = " a.ordaccid=" + htp.getMaccid() + " and
		// a.cthouseid=" + cthouseid;
		// if (!findbox.equals(""))
		// strwhere += " and (b.warename like '%" + findbox + "%' or b.wareno
		// like '%" + findbox.toUpperCase() + "%')";
		String qry = "select a.wareid,b.wareno,b.warename,b.units,b.imagename0,sum(a.amount) as amount,sum(a.curr) as curr,min(a.id) as id";
		qry += "\n from cityorder a ";
		qry += "\n left outer join warecode b on a.wareid=b.wareid";
		qry += "\n where a.ordaccid=" + htp.getMaccid() + " and a.cthouseid=" + cthouseid;
		if (!findbox.equals(""))
			qry += " and (b.warename like '%" + findbox + "%' or b.wareno like '%" + findbox.toUpperCase() + "%')";
		qry += "\n group by a.wareid,b.wareno,b.warename,b.units,b.imagename0";
		QueryParam qp = new QueryParam(page, pagesize, sort, qry);

		qp.setSumString("nvl(sum(amount),0) as totalamt,nvl(sum(curr),0) as totalcurr");
		Table tb = new Table();
		Cityorder dal = new Cityorder();

		tb = dal.GetTable(qp);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 删除商城店铺订货商品记录
	protected void DelCityorderware(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		Cityorder dal = new Cityorder();
		dal.setCthouseid(cthouseid);
		dal.setWareid(wareid);
		dal.setOrdaccid(htp.getMaccid());
		WriteResult(response, dal.Delete(), dal.getErrmess());
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

	// 增加商城店铺订货商品
	protected void AddCityorderware(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		float price = jsonObject.has("price") ? Float.parseFloat(jsonObject.getString("price")) : 0;
		Cityorder dal = new Cityorder();
		dal.setCthouseid(cthouseid);
		dal.setWareid(wareid);
		dal.setPrice(price);
		dal.setLastop(htp.getUsername());

		if (dal.Append(jsonObject, htp.getMaccid()) == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());

		}

	}

	// 保存商城订货商品颜色尺码明细
	protected void WriteCityorderware(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		float price = jsonObject.has("price") ? Float.parseFloat(jsonObject.getString("price")) : 0;

		Cityorder dal = new Cityorder();
		dal.setCthouseid(cthouseid);
		dal.setWareid(wareid);
		dal.setPrice(price);
		dal.setLastop(htp.getUsername());
		if (dal.Append1(jsonObject, htp.getMaccid()) == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());

		}
	}

	// 商城订货商品生成采购订单
	protected void CityWareToOrder(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;

		int priceprec = htp.getPriceprec();
		Cityorder dal = new Cityorder();
		dal.setCthouseid(cthouseid);
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.doCityWareToOrder(htp.getMaccid(), priceprec), dal.getErrmess());

	}

	// 商城店铺商品全部上线
	protected void UpCityhousewareOnline(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		Cityhouseware dal = new Cityhouseware();
		dal.setCthouseid(cthouseid);
		dal.setLastop(htp.getUsername());
		dal.setOnlinetag(1);
		WriteResult(response, dal.Updateall(), dal.getErrmess());
	}

	// 更改所有商城店铺商品的开放日期
	protected void ChangeCityhouseWareopendate(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long cthouseid = jsonObject.has("cthouseid") ? Long.parseLong(jsonObject.getString("cthouseid")) : 0;
		String opendate = jsonObject.has("opendate") ? jsonObject.getString("opendate") : "";
		Cityhouseware dal = new Cityhouseware();
		dal.setCthouseid(cthouseid);
		dal.setLastop(htp.getUsername());
		dal.setOpendate(opendate);
		WriteResult(response, dal.Updateall(), dal.getErrmess());
	}
	// 采购商城end

	// =============================
	// 新增会员档案记录
	protected void AddGuestvipRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Guestvip dal = new Guestvip();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		dal.setStatetag(1);
		WriteResult(response, dal.Append(), dal.getErrmess());
	}

	// 新增会员积分记录
	protected void AddGuestcentRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Guestcent dal = new Guestcent();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setLy(0); // ly:0=手工输入,1=商场零售,2=店铺零售
		dal.setAccid(htp.getMaccid());
		dal.setOperant(htp.getUsername());
		dal.setSmsuid(htp.getSmsuid());
		dal.setSmsuser(htp.getSmsuser());
		dal.setSmspwd(htp.getSmspwd());
		if (dal.Append() == 0)
			WriteResult(response, 0, dal.getErrmess());
		else
			WriteResultJson(response, 1, dal.getErrmess());
	}

	// 新增会员储值记录
	protected void AddGuestcurrRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Guestcurr dal = new Guestcurr();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setLy(0); // ly:0=手工输入,1=商场零售,2=店铺零售
		dal.setAccid(htp.getMaccid());
		dal.setOperant(htp.getUsername());
		dal.setSmsuid(htp.getSmsuid());
		dal.setSmsuser(htp.getSmsuser());
		dal.setSmspwd(htp.getSmspwd());
		if (dal.Append() == 0)
			WriteResult(response, 0, dal.getErrmess());
		else
			WriteResultJson(response, 1, dal.getErrmess());
	}

	// 新增会员类型记录
	protected void AddGuesttypeRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Guesttype dal = new Guesttype();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		dal.setNoused(0);
		dal.setStatetag(1);
		WriteResult(response, dal.Append(), dal.getErrmess());
	}

	// 更新指定会员id信息
	protected void UpdateGuestvipByID(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String lastop = htp.getUsername();
		long accid = htp.getMaccid();
		if (accid == 1000 && (lastop.equals("演示") || lastop.equals("枫杨"))) {
			WriteResult(response, 0, "这是演示套账，不允许更改记录！");
			return;
		}
		Guestvip dal = new Guestvip();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setAccid(accid);
		dal.setLastop(lastop);
		WriteResult(response, dal.Update(), dal.getErrmess());

	}

	// 根据会员卡号增加或更新会员信息 用于从excel中导入会员资料
	protected void AppendGuestvip(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Guestvip dal = new Guestvip();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.LoadFromExcel(), dal.getErrmess());
	}

	// 载入会员的初始积分及储值余额
	protected void AppendGuestcentorcurr(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String vipno = jsonObject.has("vipno") ? jsonObject.getString("vipno") : "";
		long guestid = jsonObject.has("guestid") ? Long.parseLong(jsonObject.getString("guestid")) : 0;
		float balcurr = jsonObject.has("balcurr") ? Float.parseFloat(jsonObject.getString("balcurr")) : 0;
		float balcent = jsonObject.has("balcent") ? Float.parseFloat(jsonObject.getString("balcent")) : 0;
		Guestvip dal = new Guestvip();
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		dal.setGuestid(guestid);
		dal.setVipno(vipno);
		dal.setBalcent(balcent);
		dal.setBalcurr(balcurr);
		// WriteResult(response, dal.LoadCentorCurr(), dal.getErrmess());
		WriteResult(response, dal.doAppendGuestcentorcurr(), dal.getErrmess());
		// doAppendGuestcentorcurr
	}

	// 汇总会员积分及储值
	protected void TotalGuestbal(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		vTotalGuestbal dal = new vTotalGuestbal();

		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(htp.getMaccid());
		dal.setUserid(htp.getUserid());
		dal.setAccbegindate(htp.getAccdate());
		dal.setMaxday(htp.getMaxday());
		if (dal.Total() < 0)
			WriteResult(response, 0, dal.getErrmess());
		else
			WriteResult(response, 1, dal.getErrmess());
	}

	// 分页显示会员积分及储值
	protected void ListGuestbal(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 50;
		vTotalGuestbal dal = new vTotalGuestbal();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(htp.getMaccid());
		dal.setUserid(htp.getUserid());
		QueryParam qp = new QueryParam();
		qp.setPageIndex(page);
		qp.setPageSize(pagesize);
		Table tb = new Table();

		tb = dal.GetTable(qp);

		if (page < 0) {// 导出excel
			String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;
		}

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 更新指定会员类型id信息
	protected void UpdateGuesttypeByID(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String lastop = htp.getUsername();
		long accid = htp.getMaccid();
		if (accid == 1000 && (lastop.equals("演示") || lastop.equals("枫杨"))) {
			WriteResult(response, 0, "这是演示套账，不允许更改记录！");
			return;
		}
		// long vtid = jsonObject.has("vtid") ?
		// Long.parseLong(jsonObject.getString("vtid")) : 0;
		Guesttype dal = new Guesttype();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setAccid(accid);
		dal.setLastop(lastop);
		WriteResult(response, dal.Update(), dal.getErrmess());

	}

	// 销售助手返回会员信息
	protected void GetGuestvipinfo(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long guestid = jsonObject.has("guestid") ? Long.parseLong(jsonObject.getString("guestid")) : 0;
		String vipno = jsonObject.has("vipno") ? jsonObject.getString("vipno") : "";

		Guestvip dal = new Guestvip();
		dal.setAccid(htp.getMaccid());
		dal.setGuestid(guestid);
		dal.setVipno(vipno);

		if (dal.doGuestvipinfo(htp.getAccdate()) == 0)
			WriteResult(response, 0, dal.getErrmess());
		else
			WriteResultJson(response, 1, dal.getErrmess());

	}

	// 查询商品分布
	protected void Listwareinwhere(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long guestid = jsonObject.has("guestid") ?
		// Long.parseLong(jsonObject.getString("guestid")) : 0;
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		long colorid = jsonObject.has("colorid") ? Long.parseLong(jsonObject.getString("colorid")) : 0;
		long sizeid = jsonObject.has("sizeid") ? Long.parseLong(jsonObject.getString("sizeid")) : 0;
		// int page = jsonObject.has("page") ?
		// Integer.parseInt(jsonObject.getString("page")) : 1;
		// int pagesize = jsonObject.has("rows") ?
		// Integer.parseInt(jsonObject.getString("rows")) : 10;
		// if (pagesize > 50)
		// pagesize = 10;
		if (wareid == 0 || colorid == 0 || sizeid == 0) {
			WriteResult(response, 0, "参数无效！");
			return;
		}
		long userid = htp.getUserid();
		vTotalCxkczy dal = new vTotalCxkczy();
		dal.setUserid(userid);
		dal.setAccid(htp.getMaccid());
		dal.setWareid(wareid);
		dal.setColorid(colorid);
		dal.setSizeid(sizeid);
		if (dal.doCxkczy() == 0) {
			WriteResult(response, 0, dal.getErrmess());
			return;
		}
		String qry = "select a.houseid,b.housename,a.amount,a.id from v_cxkczy_data a";
		qry += " left outer join warehouse b on a.houseid=b.houseid";
		qry += " where a.epid=" + userid + " and a.wareid=" + wareid + " and a.colorid=" + colorid + " and a.sizeid=" + sizeid;
		qry += " and rownum<100 order by a.houseid ,a.id";
		Table tb = new Table();

		tb = dal.GetTable(qry);
		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 销售助手查询会员消费历史
	protected void ListGuestvipinfo(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long guestid = jsonObject.has("guestid") ? Long.parseLong(jsonObject.getString("guestid")) : 0;
		// String vipno = jsonObject.has("vipno") ?
		// jsonObject.getString("vipno") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		long accid = htp.getMaccid();
		String accdate = htp.getAccdate();
		// 最近消费时间
		String qry = "  select a.id,a.notedate,a.noteno,a.remark,b.wareid,c.wareno,c.warename,c.units,c.imagename0,b.amount,b.price0,b.discount,b.price,b.curr,0 as fs";
		qry += " ,d.colorname,e.sizename,f.housename";
		qry += " from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += " join warecode c on b.wareid=c.wareid";
		qry += " join colorcode d on b.colorid=d.colorid";
		qry += " join sizecode e on b.sizeid=e.sizeid";
		qry += " join warehouse f on a.houseid=f.houseid";
		qry += " where a.statetag=1 and a.ntid=0 and a.guestid=" + guestid;
		qry += " and a.accid=" + accid;
		qry += " and a.notedate>=to_date('" + accdate + "','yyyy-mm-dd') ";
		qry += " union all";
		qry += "  select a.id,a.notedate,a.noteno,a.remark,b.wareid,c.wareno,c.warename,c.units,c.imagename0,b.amount,b.price0,b.discount,b.price,b.curr,1 as fs";
		qry += " ,d.colorname,e.sizename,f.housename";
		qry += " from shopsaleh a join shopsalem b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += " join warecode c on b.wareid=c.wareid";
		qry += " join colorcode d on b.colorid=d.colorid";
		qry += " join sizecode e on b.sizeid=e.sizeid";
		qry += " join warehouse f on b.houseid=f.houseid";
		qry += " where a.statetag=1 and a.guestid=" + guestid;
		qry += " and a.accid=" + accid;
		qry += " and a.notedate>=to_date('" + accdate + "','yyyy-mm-dd') ";

		String sort = "notedate desc,id,fs";

		QueryParam qp = new QueryParam(page, pagesize, sort, qry);

		Table tb = new Table();
		Wareouth dal = new Wareouth();

		tb = dal.GetTable(qp);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 查找会员
	protected void FindGuestvip(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "A.GUESTID,A.GUESTNAME,A.SEX,A.SHORTNAME,A.VIPNO,A.ACCID,A.VTID,A.MOBILE,A.BIRTHDAY,A.STATETAG,A.USETAG,A.STATURE"
					+ ",A.FITSIZE,A.LIKESOME,A.BALCENT,A.BALCURR,A.HOUSEID,A.V_ACCID,A.CREATEDATE,A.LASTOP,A.LASTDATE ,B.VTNAME,C.HOUSENAME";

		Guestvip dal = new Guestvip();
		dal.setAccid(htp.getMaccid());
		dal.setVipno(findbox.toUpperCase());
		dal.setMobile(findbox);

		if (dal.doFind(htp.getNowdate()) == 0)
			WriteResult(response, 0, dal.getErrmess());
		else
			WriteResultJson(response, 1, dal.getErrmess());
	}

	// 根据会员卡号、手机号获取会员信息
	protected void GetGuestvipByNO(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long guestid = jsonObject.has("guestid") ?
		// Long.parseLong(jsonObject.getString("guestid")) : 0;
		String vipidstr = jsonObject.has("vipidstr") ? jsonObject.getString("vipidstr") : "";
		String vipno = jsonObject.has("vipno") ? jsonObject.getString("vipno") : "";
		String mobile = jsonObject.has("mobile") ? jsonObject.getString("mobile") : "";
		String guestname = jsonObject.has("guestname") ? jsonObject.getString("guestname") : "";

		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";

		if (vipidstr.equals("")) {
			vipidstr = "0";
		} else {
			if (!Func.isNumber(vipidstr))
				vipidstr = Func.desDecode(vipidstr);
		}
		long vipid = Long.parseLong(vipidstr);

		String strwhere = " a.accid=" + htp.getMaccid() + " and a.statetag=1 ";
		if (!vipno.equals(""))
			strwhere += " and a.vipno='" + vipno + "'";
		if (!guestname.equals(""))
			strwhere += " and a.guestname='" + guestname + "'";
		if (!mobile.equals(""))
			strwhere += " and a.mobile='" + mobile + "'";
		if (vipid > 0)
			strwhere += " and vipid=" + vipid;

		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "A.GUESTID,A.GUESTNAME,A.SEX,A.SHORTNAME,A.VIPNO,A.ACCID,A.VTID,A.MOBILE,A.BIRTHDAY,A.STATETAG,A.USETAG,A.STATURE"
					+ ",A.FITSIZE,A.LIKESOME,A.BALCENT,A.BALCURR,A.HOUSEID,A.V_ACCID,A.CREATEDATE,A.LASTOP,A.LASTDATE ,B.VTNAME,C.HOUSENAME";
		Guestvip dal = new Guestvip();
		Table tb = new Table();
		tb = dal.GetList(strwhere, fieldlist).getTable(1);

		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 获取指定会员id信息
	protected void GetGuestvipByID(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long guestid = jsonObject.has("guestid") ? Long.parseLong(jsonObject.getString("guestid")) : 0;
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		String strwhere = " a.GUESTID=" + guestid;// +" and a.statetag=1";
		// if (accid != 0)
		strwhere += " and a.ACCID=" + htp.getMaccid();
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "A.GUESTID,A.GUESTNAME,A.SEX,A.SHORTNAME,A.VIPNO,A.ACCID,A.VTID,A.MOBILE,A.BIRTHDAY,A.STATETAG,A.USETAG,A.STATURE"
					+ ",A.FITSIZE,A.LIKESOME,A.BALCENT,A.BALCURR,A.HOUSEID,A.V_ACCID,A.CREATEDATE,A.LASTOP,A.LASTDATE ,B.VTNAME,C.HOUSENAME,a.remark";
		Guestvip dal = new Guestvip();
		Table tb = new Table();

		tb = dal.GetList(strwhere, fieldlist).getTable(1);

		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 获取指定id会员储值记录
	protected void GetGuestcurrbyid(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		float id = jsonObject.has("id") ? Float.parseFloat(jsonObject.getString("id")) : 0;
		// String noteno = jsonObject.has("noteno") ?
		// jsonObject.getString("noteno").replace("'", "''") : "";
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";

		String strwhere = " a.id='" + id + "' and b.ACCID=" + htp.getMaccid();
		// if (!noteno.equals(""))
		// strwhere += " and a.noteno='" + noteno + "'";
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "A.ID,A.GUESTID,A.NOTEDATE,A.CURR,A.PAYCURR,A.FS,A.LY,A.XSNOTENO,A.OPERANT,A.REMARK,B.HOUSENAME,C.PAYNAME,d.guestname,d.vipno,d.mobile,e.vtname,d.balcurr";
		// fieldlist += ",case when To_Char(a.notedate,'yyyy-mm-dd')>'" +
		// htp.getCalcdate() + "' then '1' else '0' end as istoday";
		Table tb = new Table();
		Guestcurr dal = new Guestcurr();

		tb = dal.GetList(strwhere, fieldlist).getTable(1);

		Write(response, DbHelperSQL.DataTable2Json(tb));
	}

	// 分页获取会员储值记录
	protected void GetGuestcurrList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : "notedate";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : "desc";

		int cxfs = jsonObject.has("cxfs") ? Integer.parseInt(jsonObject.getString("cxfs")) : 0;
		long guestid = jsonObject.has("guestid") ? Long.parseLong(jsonObject.getString("guestid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		// String sort = jsonObject.has("sort") ? jsonObject.getString("sort") :
		// "";
		// String order = jsonObject.has("order") ?
		// jsonObject.getString("order") : "";
		// String findbox = jsonObject.has("findbox") ?
		// jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String strwhere = "";
		// String sort = "notedate desc,id desc ";
		sort += " " + order + ",id desc";
		if (cxfs == 0) {
			strwhere += " a.GUESTID=" + guestid;
		} else {
			int fs = jsonObject.has("fs") ? Integer.parseInt(jsonObject.getString("fs")) : 2;
			// 类型:0=储值，1=消费，2=所有

			String vipno = jsonObject.has("vipno") ? jsonObject.getString("vipno") : "";
			String guestname = jsonObject.has("guestname") ? jsonObject.getString("guestname") : "";
			String vtidlist = jsonObject.has("vtidlist") ? jsonObject.getString("vtidlist") : "";
			String houseidlist = jsonObject.has("houseidlist") ? jsonObject.getString("houseidlist") : "";
			String fkhouseidlist = jsonObject.has("fkhouseidlist") ? jsonObject.getString("fkhouseidlist") : "";
			String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : htp.getNowdate();
			String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : htp.getNowdate();
			String operant = jsonObject.has("operant") ? jsonObject.getString("operant") : "";
			long payid = jsonObject.has("payid") ? Long.parseLong(jsonObject.getString("payid")) : 0;
			// if (mindate.length() > 0)
			strwhere = " d.statetag=1 and d.accid=" + htp.getMaccid();
			strwhere += " and a.notedate>=to_date('" + mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
			// if (maxdate.length() > 0)
			strwhere += " and a.notedate<=to_date('" + maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
			if (fs == 0 && payid > 0)
				strwhere += " and a.payid=" + payid;
			if (operant.length() > 0)
				strwhere += "\n and a.operant like '%" + operant + "%'";
			if (fs < 2)
				strwhere += " and a.fs=" + fs;
			if (guestid > 0)
				strwhere += " and a.GUESTID=" + guestid;
			if (vipno.length() > 0)
				strwhere += "\n and d.vipno like '%" + vipno + "%'";
			if (guestname.length() > 0)
				strwhere += "\n and d.Guestname like '%" + guestname + "%'";
			if (!Func.isNull(vtidlist))
				strwhere += "  and " + Func.getCondition("d.vtid", vtidlist);

			if (!Func.isNull(houseidlist))
				strwhere += "  and " + Func.getCondition("a.houseid", houseidlist);

			if (!Func.isNull(fkhouseidlist))
				strwhere += "  and " + Func.getCondition("d.houseid", fkhouseidlist);

		}

		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "A.ID,A.GUESTID,A.NOTEDATE,A.HOUSEID,A.CURR,A.PAYCURR,A.PAYID,A.FS,A.LY,A.XSNOTENO,A.OPERANT,A.REMARK,B.HOUSENAME,C.PAYNAME";
		fieldlist += ",decode(a.fs,0,a.curr,-a.curr) as curr0,decode(a.fs,0,'储值','消费') as zy";
		if (!fieldlist.toUpperCase().contains("A.PAYCURR"))
			fieldlist += ",A.PAYCURR";
		QueryParam qp = new QueryParam(page, pagesize, sort);
		Guestcurr dal = new Guestcurr();
		if (page < 0) {// 导出excel
			// String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
			String retcs = dal.GetTable2Excel(qp, strwhere, fieldlist, cxfs, jsonObject);
			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;
		}

		Table tb = new Table();

		tb = dal.GetTable(qp, strwhere, fieldlist, cxfs);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 分页获取会员积分记录
	protected void GetGuestcentList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int cxfs = jsonObject.has("cxfs") ? Integer.parseInt(jsonObject.getString("cxfs")) : 0;
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : "notedate";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : "desc";

		long guestid = jsonObject.has("guestid") ? Long.parseLong(jsonObject.getString("guestid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		// String sort = jsonObject.has("sort") ? jsonObject.getString("sort") :
		// "";
		// String order = jsonObject.has("order") ?
		// jsonObject.getString("order") : "";
		// String findbox = jsonObject.has("findbox") ?
		// jsonObject.getString("findbox").trim().replace("'", "''") : "";
		sort += " " + order + ",id desc";
		// String sort = "notedate desc,id desc ";
		String strwhere = "";
		if (cxfs == 0) {
			strwhere = " a.GUESTID=" + guestid;
		} else {
			int fs = jsonObject.has("fs") ? Integer.parseInt(jsonObject.getString("fs")) : 2;
			// 类型:0=积分，1=使用积分，2=所有
			String vipno = jsonObject.has("vipno") ? jsonObject.getString("vipno") : "";
			String guestname = jsonObject.has("guestname") ? jsonObject.getString("guestname") : "";
			String vtidlist = jsonObject.has("vtidlist") ? jsonObject.getString("vtidlist") : "";
			String houseidlist = jsonObject.has("houseidlist") ? jsonObject.getString("houseidlist") : "";
			String fkhouseidlist = jsonObject.has("fkhouseidlist") ? jsonObject.getString("fkhouseidlist") : "";
			String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : htp.getNowdate();
			String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : htp.getNowdate();
			String operant = jsonObject.has("operant") ? jsonObject.getString("operant") : "";
			strwhere = " d.statetag=1 and d.accid=" + htp.getMaccid();
			// if (mindate.length() > 0)
			strwhere += " and a.notedate>=to_date('" + mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
			// if (maxdate.length() > 0)
			strwhere += " and a.notedate<=to_date('" + maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
			if (fs < 2)
				strwhere += " and a.fs=" + fs;
			if (guestid > 0)
				strwhere += " and a.GUESTID=" + guestid;
			if (vipno.length() > 0)
				strwhere += "\n and d.vipno like '%" + vipno + "%'";
			if (guestname.length() > 0)
				strwhere += "\n and d.Guestname like '%" + guestname + "%'";
			if (!Func.isNull(vtidlist))
				strwhere += "  and " + Func.getCondition("d.vtid", vtidlist);
			if (operant.length() > 0)
				strwhere += "\n and a.operant like '%" + operant + "%'";

			if (!Func.isNull(houseidlist))
				strwhere += "  and " + Func.getCondition("a.houseid", houseidlist);

			if (!Func.isNull(fkhouseidlist))
				strwhere += "  and " + Func.getCondition("d.houseid", fkhouseidlist);

		}
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "A.ID,A.GUESTID,A.NOTEDATE,A.HOUSEID,A.CENT,A.FS,A.LY,A.XSNOTENO,A.OPERANT,A.REMARK,B.HOUSENAME";
		fieldlist += ",decode(a.fs,0,a.cent,-a.cent) as cent0,decode(a.fs,0,'积分','减分') as zy";
		Guestcent dal = new Guestcent();
		QueryParam qp = new QueryParam(page, pagesize, sort);
		if (page < 0) {// 导出excel
			// String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
			String retcs = dal.GetTable2Excel(qp, strwhere, fieldlist, cxfs, jsonObject);
			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;
		}

		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere, fieldlist, cxfs);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 获取会员列表
	protected void GetGuestvipList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int downbj = jsonObject.has("downbj") ? Integer.parseInt(jsonObject.getString("downbj")) : 0;
		// int noused = jsonObject.has("noused") ?
		// Integer.parseInt(jsonObject.getString("noused")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : "guestid";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : "";
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String lastdate = jsonObject.has("lastdate") ? jsonObject.getString("lastdate") : "";
		String vipno = jsonObject.has("vipno") ? jsonObject.getString("vipno") : "";
		String mobile = jsonObject.has("mobile") ? jsonObject.getString("mobile") : "";
		String guestname = jsonObject.has("guestname") ? jsonObject.getString("guestname") : "";
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : -1;
		long vtid = jsonObject.has("vtid") ? Long.parseLong(jsonObject.getString("vtid")) : -1;
		int cvip = jsonObject.has("cvip") ? Integer.parseInt(jsonObject.getString("cvip")) : 0;
		int srdays = jsonObject.has("srdays") ? Integer.parseInt(jsonObject.getString("srdays")) : -1;
		// cvip=1 表示查询已是C端app的会员,注意fieldlist要加a.vipid
		int usetag = jsonObject.has("usetag") ? Integer.parseInt(jsonObject.getString("usetag")) : 0;
		// usetag:0=在用，1=禁用，2=挂失，3=所有
		sort += " " + order;
		String strwhere = " a.ACCID=" + htp.getMaccid();
		if (downbj == 0)
			strwhere += " and a.STATETAG=1";
		// if (ywly < 2) qry += " and a.ywly=" + ywly;//ywly=0
		// 只查询手机端增加的单据，用于向erp传数据
		if (!findbox.equals(""))
			strwhere += " and (a.guestname like '%" + findbox + "%' or a.vipno like '%" + findbox.toUpperCase() + "%' or a.mobile like '%" + findbox + "%' or b.vtname like '%" + findbox + "%')";
		if (!lastdate.equals(""))
			strwhere += " and a.lastdate>to_date('" + lastdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (cvip == 1)
			strwhere += " and a.vipid>0";
		if (!guestname.equals(""))
			strwhere += " and a.GUESTNAME like '%" + guestname + "%'";
		if (!mobile.equals(""))
			strwhere += " and a.mobile like '%" + mobile + "%'";
		if (!vipno.equals(""))
			strwhere += " and a.vipno like '%" + vipno + "%'";

		if (houseid >= 0)
			strwhere += " and a.HOUSEID = " + houseid;
		if (vtid >= 0)
			strwhere += " and a.VTID = " + vtid;
		if (usetag < 3)
			strwhere += " and a.usetag=" + usetag;
		// if (newvip > 0) qry += " and a.VTID = 0" ;
		if (srdays >= 0)// 近日过生的会员
			strwhere += " and f_isdate(a.birthday)=1 and SUBSTR(a.birthday,6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(a.birthday, 6,5) <= to_char(sysdate + " + srdays + ", 'mm-dd')";

		if (fieldlist.equals("") || fieldlist.equals("*"))
			fieldlist = "A.GUESTID,A.GUESTNAME,A.SEX,A.SHORTNAME,A.VIPNO,A.ACCID,A.VTID,A.MOBILE,A.BIRTHDAY,A.STATETAG,A.USETAG,A.STATURE"
					+ ",A.FITSIZE,A.LIKESOME,A.BALCENT,A.BALCURR,A.HOUSEID,A.VIPID,A.CREATEDATE,A.LASTOP,A.LASTDATE,B.VTNAME,C.HOUSENAME,A.REMARK,B.DISCOUNT";
		if (!fieldlist.toUpperCase().contains("A.BALCENT"))
			fieldlist += ",A.BALCENT";
		if (!fieldlist.toUpperCase().contains("A.BALCURR"))
			fieldlist += ",A.BALCURR";

		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		Guestvip dal = new Guestvip();

		if (page < 0) {// 导出excel
			// String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
			String retcs = dal.GetTable2Excel(qp, strwhere, fieldlist, jsonObject);
			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;
		}

		tb = dal.GetTable(qp, strwhere, fieldlist);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 获取指定会员类型id信息
	protected void GetGuesttypeByID(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		long vtid = jsonObject.has("vtid") ? Long.parseLong(jsonObject.getString("vtid")) : 0;
		String strwhere = " vtid=" + vtid;// +" and statetag=1";
		strwhere += " and accid=" + htp.getMaccid();
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "VTID,VTNAME,ACCID,DISCOUNT,CENTRATE,STATETAG,NOUSED,LASTOP,LASTDATE";
		Table tb = new Table();
		Guesttype dal = new Guesttype();

		tb = dal.GetList(strwhere, fieldlist).getTable(1);

		Write(response, DbHelperSQL.DataTable2Json(tb));
	}

	// 删除指定会员id信息
	protected void DelGuestvipByID(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		String lastop = htp.getUsername();
		long accid = htp.getMaccid();
		if (accid == 1000 && (lastop.equals("演示") || lastop.equals("枫杨"))) {
			WriteResult(response, 0, "这是演示套账，不允许删除记录！");
			return;
		}

		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		long guestid = jsonObject.has("guestid") ? Long.parseLong(jsonObject.getString("guestid")) : 0;
		Guestvip dal = new Guestvip();
		dal.setGuestid(guestid);
		dal.setAccid(accid);
		dal.setLastop(lastop);
		WriteResult(response, dal.Delete(), dal.getErrmess());

	}

	// 删除指定会员类型id信息
	protected void DelGuesttypeByID(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		String lastop = htp.getUsername();
		long accid = htp.getMaccid();
		if (accid == 1000 && (lastop.equals("演示") || lastop.equals("枫杨"))) {
			WriteResult(response, 0, "这是演示套账，不允许删除记录！");
			return;
		}

		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		long vtid = jsonObject.has("vtid") ? Long.parseLong(jsonObject.getString("vtid")) : 0;
		// Table tb = new Table();
		Guesttype dal = new Guesttype();
		dal.setVtid(vtid);
		dal.setAccid(accid);
		dal.setLastop(lastop);
		WriteResult(response, dal.Delete(), dal.getErrmess());

	}

	// 获取会员类型列表
	protected void GetGuesttypeList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : "vtid";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : "";
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String lastdate = jsonObject.has("lastdate") ? jsonObject.getString("lastdate") : "";
		String vtname = jsonObject.has("vtname") ? jsonObject.getString("vtname") : "";
		int downbj = jsonObject.has("downbj") ? Integer.parseInt(jsonObject.getString("downbj")) : 0;
		int noused = jsonObject.has("noused") ? Integer.parseInt(jsonObject.getString("noused")) : 0;
		sort += " " + order;
		String strwhere = " accid=" + htp.getMaccid();
		if (downbj == 0)
			strwhere += " and statetag=1";
		if (noused < 2)
			strwhere += " and NOUSED=" + noused;
		if (!lastdate.equals(""))
			strwhere += " and lastdate>to_date('" + lastdate + "','yyyymmddhh24miss')";

		if (!findbox.equals(""))
			strwhere += " and vtname like '%" + findbox + "%'";
		if (!vtname.equals(""))
			strwhere += " and vtname like '%" + vtname + "%'";
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "VTID,VTNAME,ACCID,DISCOUNT,CENTRATE,STATETAG,NOUSED,LASTOP,LASTDATE";
		QueryParam qp = new QueryParam(page, pagesize, sort);
		// qp.setPageIndex(page);
		// qp.setPageSize(pagesize);
		// qp.setSortString(sort);

		Table tb = new Table();
		Guesttype dal = new Guesttype();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// ===========================================
	// 获取指定物流单位id信息
	protected void GetCarrylineByID(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long carryid = jsonObject.has("carryid") ? Long.parseLong(jsonObject.getString("carryid")) : 0;
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		String strwhere = " carryid=" + carryid;// +" and statetag=1";
		strwhere += " and ACCID=" + htp.getMaccid();
		if (fieldlist.equals("*") || fieldlist.length() <= 0)
			fieldlist = "CARRYID,CARRYTNAME,SHORTNAME,ACCID,ADDRESS,POSTCODE,TEL,LINKMAN,MOBILE,BANKNAME,TAXNO,ACCOUNTNO,REMARK,URLADD,NOUSED,STATETAG,LASTOP,LASTDATE";
		Carryline dal = new Carryline();
		Table tb = new Table();
		tb = dal.GetList(strwhere, fieldlist).getTable(1);

		Write(response, DbHelperSQL.DataTable2Json(tb));
	}

	// 更新指定物流单位信息
	protected void UpdateCarrylineByID(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String lastop = htp.getUsername();
		long accid = htp.getMaccid();
		if (accid == 1000 && (lastop.equals("演示") || lastop.equals("枫杨"))) {
			WriteResult(response, 0, "这是演示套账，不允许更改记录！");
			return;
		}
		// long carryid = jsonObject.has("carryid") ?
		// Long.parseLong(jsonObject.getString("carryid")) : 0;
		Carryline dal = new Carryline();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		// dal.setCarryid(carryid);
		dal.setAccid(accid);
		dal.setLastop(lastop);
		WriteResult(response, dal.Update(), dal.getErrmess());
	}

	// 删除物流单位
	protected void DelCarrylineByID(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String lastop = htp.getUsername();
		long accid = htp.getMaccid();
		if (accid == 1000 && (lastop.equals("演示") || lastop.equals("枫杨"))) {
			WriteResult(response, 0, "这是演示套账，不允许删除记录！");
			return;
		}
		long carryid = jsonObject.has("carryid") ? Long.parseLong(jsonObject.getString("carryid")) : 0;
		Carryline dal = new Carryline();
		dal.setCarryid(carryid);
		dal.setAccid(accid);
		dal.setLastop(lastop);
		WriteResult(response, dal.Delete(), dal.getErrmess());
	}

	// 新增物流单位记录
	protected void AddCarrylineRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Carryline dal = new Carryline();
		// 读取json数据到表类
		// DbHelperSQL.JsonConvertObject1(dal, jsonObject);
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		// dal.setUserid(htp.getUserid());
		dal.setStatetag(1);
		dal.setNoused(0);
		WriteResult(response, dal.Append(), dal.getErrmess());

	}

	// 获取物流单位列表
	protected void GetCarrylineList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " CARRYID";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : "";
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String carryname = jsonObject.has("carryname") ? jsonObject.getString("carryname").replace("'", "''") : "";
		String mobile = jsonObject.has("mobile") ? jsonObject.getString("mobile").replace("'", "''") : "";
		String remark = jsonObject.has("remark") ? jsonObject.getString("remark").replace("'", "''") : "";
		String linkman = jsonObject.has("linkman") ? jsonObject.getString("linkman").replace("'", "''") : "";
		// String lastdate = jsonObject.has("lastdate") ?
		// jsonObject.getString("lastdate") : "";
		int noused = jsonObject.has("noused") ? Integer.parseInt(jsonObject.getString("noused")) : 0;

		sort += " " + order + ",rowid";
		String strwhere = " ACCID=" + htp.getMaccid() + " and STATETAG=1";
		// if (ywly < 2) qry += " and ywly=" + ywly;//ywly=0
		// 只查询手机端增加的单据，用于向erp传数据
		if (noused < 2)
			strwhere += " and NOUSED=" + noused;
		if (!findbox.equals(""))
			strwhere += " and (carryname like '%" + findbox + "%' or shortname like '%" + findbox.toUpperCase() + "%' or mobile like '%" + findbox + "%')";
		// if (lastdate != null && lastdate != "") qry += " and
		// lastdate>to_date('" + lastdate + "','yyyymmddhh24miss')";

		if (carryname.length() > 0)
			strwhere += " and carryname like '%" + carryname + "%'";
		// if (linkman != null && linkman != "") qry += " and linkman like '%" +
		// linkman + "%'";
		if (mobile.length() > 0)
			strwhere += " and mobile like '%" + mobile + "%'";
		if (remark.length() > 0)
			strwhere += " and remark like '%" + remark + "%'";
		if (linkman.length() > 0)
			strwhere += " and linkman like '%" + linkman + "%'";
		// if (areaname != null && areaname != "") qry += " and areaname like
		// '%" + areaname + "%'";
		if (fieldlist.equals("*") || fieldlist.length() <= 0)
			fieldlist = "CARRYID,CARRYNAME,SHORTNAME,ACCID,ADDRESS,POSTCODE,TEL,LINKMAN,MOBILE,BANKNAME,TAXNO,ACCOUNTNO,REMARK,URLADD,NOUSED,STATETAG,LASTOP,LASTDATE";

		QueryParam qp = new QueryParam(page, pagesize, sort);

		Table tb = new Table();
		Carryline dal = new Carryline();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// ===========================================
	// 增加结算记账关联记录
	/*
	 * protected void AddPaytobook(HttpServletResponse response, HttpInfo htp)
	 * throws ServletException, IOException {
	 * JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
	 * Paytobook dal = new Paytobook();
	 * // 读取json数据到表类
	 * DbHelperSQL.JsonConvertObject(dal, jsonObject);
	 * dal.setAccid(htp.getMaccid());
	 * dal.setLastop(htp.getUsername());
	 * WriteResult(response, dal.Append(), dal.getErrmess());
	 * 
	 * }
	 * 
	 * // 修改结算记账关联记录
	 * protected void UpdatePaytobook(HttpServletResponse response, HttpInfo
	 * htp) throws ServletException, IOException {
	 * JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
	 * Paytobook dal = new Paytobook();
	 * // 读取json数据到表类
	 * DbHelperSQL.JsonConvertObject(dal, jsonObject);
	 * dal.setAccid(htp.getMaccid());
	 * dal.setLastop(htp.getUsername());
	 * WriteResult(response, dal.Update(), dal.getErrmess());
	 * 
	 * }
	 * 
	 * // 删除结算记账关联记录
	 * protected void DelPaytobook(HttpServletResponse response, HttpInfo htp)
	 * throws ServletException, IOException {
	 * JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
	 * Float id = jsonObject.has("id") ?
	 * Float.parseFloat(jsonObject.getString("id")) : 0;
	 * 
	 * Paytobook dal = new Paytobook();
	 * dal.setId(id);
	 * dal.setAccid(htp.getMaccid());
	 * dal.setLastop(htp.getUsername());
	 * WriteResult(response, dal.Delete(), dal.getErrmess());
	 * }
	 */
	// 增加记账项目
	protected void AddSubitem(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Subitem dal = new Subitem();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		// dal.setUserid(htp.getUserid());

		dal.setStatetag(1);

		WriteResult(response, dal.Append(), dal.getErrmess());

	}

	// 更改记账项目
	protected void UpdateSubitem(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Subitem dal = new Subitem();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Update(), dal.getErrmess());

	}

	// 增加账本
	protected void AddAccbook(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Accbook dal = new Accbook();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		// dal.setUserid(htp.getUserid());

		// dal.setStatetag(1);

		WriteResult(response, dal.Append(jsonObject), dal.getErrmess());

	}

	// 修改账本
	protected void UpdateAccbook(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Accbook dal = new Accbook();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Update(jsonObject), dal.getErrmess());

	}

	// 删除记账项目
	protected void DelSubitem(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long subid = jsonObject.has("subid") ? Long.parseLong(jsonObject.getString("subid")) : 0;
		Subitem dal = new Subitem();
		dal.setAccid(htp.getMaccid());
		dal.setSubid(subid);
		WriteResult(response, dal.Delete(), dal.getErrmess());

	}

	//计算刷新账本余额
	protected void CalcAccbook(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long bookid = jsonObject.has("bookid") ? Long.parseLong(jsonObject.getString("bookid")) : 0;
		String notedate = jsonObject.has("notedate") ? jsonObject.getString("notedate") : "";
		if (notedate.length() <= 0)
			notedate = htp.getNowdate();
		Accbook dal = new Accbook();
		dal.setAccid(htp.getMaccid());
		dal.setNotedate(notedate);
		dal.setBookid(bookid);
		WriteResult(response, dal.Calc(), dal.getErrmess());

	}

	// 删除账本
	protected void DelAccbook(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long bookid = jsonObject.has("bookid") ? Long.parseLong(jsonObject.getString("bookid")) : 0;
		Accbook dal = new Accbook();
		dal.setAccid(htp.getMaccid());
		dal.setBookid(bookid);
		WriteResult(response, dal.Delete(), dal.getErrmess());

	}

	// 获取指定id结算记账关联记录
	protected void GetBookhousepay(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Float id = jsonObject.has("id") ? Float.parseFloat(jsonObject.getString("id")) : 0;
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		//		long payid = jsonObject.has("payid") ? Long.parseLong(jsonObject.getString("payid")) : 0;
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "a.payid,a.bookid,a.subid,a.id,b.payname,c.bookname,d.subname";
		String strwhere = " a.accid=" + htp.getMaccid() + " and a.id=" + id + " and a.houseid=" + houseid;
		Bookhousepay dal = new Bookhousepay();
		Table tb = new Table();
		tb = dal.GetList(strwhere, fieldlist).getTable(1);
		Write(response, DbHelperSQL.DataTable2Json(tb));
	}

	//删除账本自动登账记录
	protected void DelBookhousepay(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Float id = jsonObject.has("id") ? Float.parseFloat(jsonObject.getString("id")) : 0;
		Bookhousepay dal = new Bookhousepay();
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		dal.setId(id);
		WriteResult(response, dal.Delete(), dal.getErrmess());
	}

	//增加账本自动登账记录
	protected void AddBookhousepay(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Bookhousepay dal = new Bookhousepay();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Append(), dal.getErrmess());
	}

	//修改账本自动登账记录
	protected void UpdateBookhousepay(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Bookhousepay dal = new Bookhousepay();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Update(), dal.getErrmess());
	}

	//分页显示账本自动登账记录
	protected void ListBookhousepay(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		long lbid = jsonObject.has("lbid") ? Long.parseLong(jsonObject.getString("lbid")) : 0;
		//1210=商场零售
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "a.bookid,a.payid,a.subid,a.id,c.bookname,b.payname,d.subname";
		String strwhere = " a.accid=" + htp.getMaccid() + " and a.houseid=" + houseid;
		strwhere += " and a.lbid=" + lbid;

		QueryParam qp = new QueryParam(page, pagesize, "payid,bookid,subid,id");

		Table tb = new Table();
		Bookhousepay dal = new Bookhousepay();
		tb = dal.GetTable(qp, strwhere, fieldlist);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	/*
	 * // 分页显示结算记账关联记录
	 * protected void ListPaytobook(HttpServletResponse response, HttpInfo htp)
	 * throws ServletException, IOException {
	 * JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
	 * long houseid = jsonObject.has("houseid") ?
	 * Long.parseLong(jsonObject.getString("houseid")) : 0;
	 * long payid = jsonObject.has("payid") ?
	 * Long.parseLong(jsonObject.getString("payid")) : 0;
	 * int page = jsonObject.has("page") ?
	 * Integer.parseInt(jsonObject.getString("page")) : 1;
	 * int pagesize = jsonObject.has("rows") ?
	 * Integer.parseInt(jsonObject.getString("rows")) : 10;
	 * if (pagesize > 50)
	 * pagesize = 10;
	 * String fieldlist = jsonObject.has("fieldlist") ?
	 * jsonObject.getString("fieldlist") : "*";
	 * if (fieldlist.equals("*") || fieldlist.equals(""))
	 * fieldlist =
	 * "a.houseid,a.payid,a.bookid,a.subid,a.id,f.progname,b.housename,c.payname,d.bookname,e.subname";
	 * String strwhere = " a.accid=" + htp.getMaccid();
	 * if (houseid > 0)
	 * strwhere += " and a.houseid=" + houseid;
	 * if (payid > 0)
	 * strwhere += " and a.payid=" + payid;
	 * 
	 * QueryParam qp = new QueryParam(page, pagesize,
	 * "houseid,payid,bookid,id");
	 * 
	 * Table tb = new Table();
	 * Paytobook dal = new Paytobook();
	 * tb = dal.GetTable(qp, strwhere, fieldlist);
	 * Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	 * }
	 */
	// 显示记账项目明细
	protected void ListSubitem(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		int lx = jsonObject.has("lx") ? Integer.parseInt(jsonObject.getString("lx")) : 1;
		// lx:1=收入，2=支出，0=转账
		// int page = jsonObject.has("page") ?
		// Integer.parseInt(jsonObject.getString("page")) : 1;
		// int pagesize = jsonObject.has("rows") ?
		// Integer.parseInt(jsonObject.getString("rows")) : 10;
		// if (pagesize > 50)
		// pagesize = 10;
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = " SUBID,SUBNAME,STATETAG,IMGINDEX,NOUSED,LASTOP,ACCID";
		// dal.setBookid(bookid);
		String strwhere = "  statetag=1 and (accid=0 or accid=" + htp.getMaccid() + ")";
		strwhere += " and lx=" + lx;
		String sort = " subname,subid  ";
		QueryParam qp = new QueryParam(-1, 10, sort);
		Subitem dal = new Subitem();
		Table tb = new Table();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 显示账本职员授权列表
	protected void BookEmployeList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long bookid = jsonObject.has("bookid") ? Long.parseLong(jsonObject.getString("bookid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		Bookemploye dal = new Bookemploye();
		dal.setAccid(htp.getMaccid());
		dal.setBookid(bookid);

		QueryParam qp = new QueryParam(page, pagesize, " housename,houseid,epname,epid");

		Table tb = new Table();

		tb = dal.GetTable(qp);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 显示指定账本
	protected void GetAccbook(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long bookid = jsonObject.has("bookid") ? Long.parseLong(jsonObject.getString("bookid")) : 0;
		String fieldlist = " BOOKID,BOOKNAME,NOTEDATE,FIRSTCURR,BALCURR,BOOKCOLOR,REMARK,LASTOP,STATETAG";
		fieldlist += ",case statetag when 1 then 1 else (select count(*) from BOOKDETAIL  where bookid=" + bookid + " and rownum=1 ) end as modibj";
		// dal.setBookid(bookid);
		String strwhere = " accid=" + htp.getMaccid() + " and bookid=" + bookid;
		Accbook dal = new Accbook();
		Table tb = new Table();
		tb = dal.GetList(strwhere, fieldlist).getTable(1);

		Write(response, DbHelperSQL.DataTable2Json(tb));
	}

	// 显示账本
	protected void ListAccbook(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long bookid = jsonObject.has("bookid") ? Long.parseLong(jsonObject.getString("bookid")) : 0;
		int statetag = jsonObject.has("statetag") ? Integer.parseInt(jsonObject.getString("statetag")) : 0;

		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = " BOOKID,BOOKNAME,NOTEDATE,FIRSTCURR,BALCURR,BOOKCOLOR,REMARK,LASTOP,STATETAG";
		String strwhere = " a.accid=" + htp.getMaccid() + " and a.statetag<>2";
		if (statetag == 1) {
			strwhere += " and a.statetag=1";
		} else {
			if (bookid > 0) {
				page = -1;// 显示所有数据
				strwhere += " and a.bookid<>" + bookid + " and a.statetag=1";
			} else {
				if (htp.getLevelid() > 0)// 系统管理员可以看所有账本，非系统管理员只能看已启用的账本
					strwhere += " and exists (select 1 from bookemploye b where a.bookid=b.bookid and b.epid=" + htp.getUserid() + ")  and a.statetag=1";
			}
		}
		//		if (!fieldlist.toUpperCase().contains("FIRSTCURR"))
		//		fieldlist += ",FIRSTCURR";
		//		if (!fieldlist.toUpperCase().contains("BALCURR"))
		//		fieldlist += ",BALCURR";

		String sort = " bookid ";
		QueryParam qp = new QueryParam(page, pagesize, sort);
		qp.setSumString("nvl(sum(balcurr),0) as totalbalcurr");
		Accbook dal = new Accbook();
		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 获取账本条件
	protected void GetBookoption(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int lx = jsonObject.has("lx") ? Integer.parseInt(jsonObject.getString("lx")) : 1;
		// lx:1=收入，2=支出

		Accbook dal = new Accbook();
		dal.setAccid(htp.getMaccid());
		if (dal.doOption(htp.getUserid(), lx, htp.getLevelid()) == 0)
			WriteResult(response, 0, dal.getErrmess());
		else
			WriteResultJson(response, 1, dal.getErrmess());
	}

	// 显示账本余额文件
	protected void ListBookbalcurr(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String nowdate = jsonObject.has("nowdate") ? jsonObject.getString("nowdate") : htp.getNowdate();
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		// String fieldlist = "a.bookid,a.bookname ,nvl(b.curr2,0) as balcurr";
		String strwhere = " a.accid=" + htp.getMaccid();
		strwhere += "\n and a.notedate>=to_date('" + nowdate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		strwhere += "\n and a.notedate<=to_date('" + nowdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		if (htp.getLevelid() > 0) // 如果不是系统管理员
			strwhere += "\n and exists (select 1 from bookemploye d where a.bookid=d.bookid and d.epid=" + htp.getUserid() + ")";

		QueryParam qp = new QueryParam(page, pagesize, "bookid");
		qp.setSumString("nvl(sum(balcurr),0) as totalcurr");
		Accbook dal = new Accbook();
		dal.setAccid(htp.getMaccid());
		if (htp.getLevelid() > 0)
			dal.setUserid(htp.getUserid());
		else
			dal.setUserid((long) 0);
		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 显示账本明细记录
	protected void ListBookdetailmx(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String nowdatestr = htp.getNowdate();// Func.DateToStr(pFunc.getServerdatetime(),
												// "yyyy-MM-dd");
		String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : nowdatestr;
		String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : nowdatestr;
		String currdatetime = jsonObject.has("currdatetime") ? jsonObject.getString("currdatetime") : htp.getNowdatetime();
		// int fs = jsonObject.has("fs") ?
		// Integer.parseInt(jsonObject.getString("fs")) : 0;
		// fs:0=记账类别，1=记账人
		int lx = jsonObject.has("lx") ? Integer.parseInt(jsonObject.getString("lx")) : 0;
		// lx:1=收入，2=支出
		String bookidlist = jsonObject.has("bookidlist") ? jsonObject.getString("bookidlist") : "";
		String lastoplist = jsonObject.has("lastoplist") ? jsonObject.getString("lastoplist") : "";
		String subidlist = jsonObject.has("subidlist") ? jsonObject.getString("subidlist") : "";
		String remark = jsonObject.has("remark") ? jsonObject.getString("remark") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = "a.id,a.notedate,a.subid,a.remark,b.subname,b.imgindex,a.lastop,a.checkman";
		if (lx == 1) // 收入
			fieldlist += ",a.curr0 as curr";
		else if (lx == 2)// 支出
			fieldlist += ",a.curr1 as curr";
		fieldlist += ",to_char(a.notedate,'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''') as weekstr";
		if (maxdate.length() <= 10)
			maxdate += " 23:59:59";
		if (maxdate.compareTo(currdatetime) > 0)
			maxdate = currdatetime;
		mindate += " 00:00:00";

		String strwhere = " a.accid=" + htp.getMaccid();
		strwhere += "\n and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')";
		strwhere += "\n and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		strwhere += "\n and a.subid>0  and b.lx=" + lx;
		if (htp.getLevelid() > 0)
			strwhere += "\n and exists (select 1 from bookemploye d where a.bookid=d.bookid and d.epid=" + htp.getUserid() + ")";

		if (lastoplist.length() > 0)
			strwhere += "\n and " + Func.getCondition("a.lastop", lastoplist, "'");
		if (bookidlist.length() > 0)
			strwhere += "\n and " + Func.getCondition("a.bookid", bookidlist);
		if (subidlist.length() > 0)
			strwhere += "\n and " + Func.getCondition("a.subid", subidlist);
		if (remark.length() > 0)
			strwhere += "\n and a.remark like '%" + remark + "%'";
		// System.out.println(strwhere);
		String sort = " notedate desc, id";
		QueryParam qp = new QueryParam(page, pagesize, sort);
		qp.setSumString("nvl(sum(curr),0) as totalcurr");
		// qp.setTotalString("'" + maxdate + "' as currdatetime");
		Bookdetail dal = new Bookdetail();
		dal.setDayhj(0);
		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere, fieldlist);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 显示账本汇总记录
	protected void ListBookdetailsum(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String nowdatestr = htp.getNowdate();// Func.DateToStr(pFunc.getServerdatetime(),
												// "yyyy-MM-dd");
		String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : nowdatestr;
		String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : nowdatestr;
		int fs = jsonObject.has("fs") ? Integer.parseInt(jsonObject.getString("fs")) : 0;
		// fs:0=记账类别，1=记账人
		int lx = jsonObject.has("lx") ? Integer.parseInt(jsonObject.getString("lx")) : 0;
		// lx:1=收入，2=支出
		String bookidlist = jsonObject.has("bookidlist") ? jsonObject.getString("bookidlist") : "";
		String lastoplist = jsonObject.has("lastoplist") ? jsonObject.getString("lastoplist") : "";
		String subidlist = jsonObject.has("subidlist") ? jsonObject.getString("subidlist") : "";
		String remark = jsonObject.has("remark") ? jsonObject.getString("remark") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		// String currdatetime = htp.getNowdatetime();
		if (maxdate.length() <= 10)
			maxdate += " 23:59:59";
		// if (maxdate.compareTo(currdatetime) > 0)
		// maxdate = currdatetime;
		mindate += " 00:00:00";

		String fieldlist = "";
		if (lx == 1) // 收入
			fieldlist += "sum(a.curr0) as curr";
		else if (lx == 2)// 支出
			fieldlist += "sum(a.curr1) as curr";

		String strwhere = " a.accid=" + htp.getMaccid();
		strwhere += " and b.lx=" + lx;
		strwhere += "\n and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')";
		strwhere += "\n and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		strwhere += "\n and a.subid>0";
		if (htp.getLevelid() > 0)
			// strwhere += "\n and exists (select 1 from accbook c where
			// a.bookid=c.bookid and c.accid=" + htp.getMaccid() + ")";
			strwhere += "\n and exists (select 1 from bookemploye d where a.bookid=d.bookid and d.epid=" + htp.getUserid() + ")";

		if (lastoplist.length() > 0)
			strwhere += "\n and " + Func.getCondition("a.lastop", lastoplist, "'");
		if (bookidlist.length() > 0)
			strwhere += "\n and " + Func.getCondition("a.bookid", bookidlist);
		if (subidlist.length() > 0)
			strwhere += "\n and " + Func.getCondition("a.subid", subidlist);
		if (remark.length() > 0)
			strwhere += "\n and a.remark like '%" + remark + "%'";
		String sort = " curr desc,keyid";
		QueryParam qp = new QueryParam(page, pagesize, sort);
		qp.setSumString("nvl(sum(curr),0) as totalcurr");
		qp.setTotalString("\"currdatetime\":\"" + maxdate + "\"");
		Bookdetail dal = new Bookdetail();
		Table tb = new Table();
		tb = dal.GetTableSum(qp, strwhere, fieldlist, fs);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 显示账本明细流水记录
	protected void ListBookdetail(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String nowdatestr = htp.getNowdate();// Func.DateToStr(pFunc.getServerdatetime(),

		int dayhj = jsonObject.has("dayhj") ? Integer.parseInt(jsonObject.getString("dayhj")) : 0;
		//dayhj:1=每日合计（手机用），2=期初余额（电脑用）
		// "yyyy-MM-dd");
		String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : nowdatestr;
		String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : nowdatestr;
		if (dayhj == 1) {
			int yearid = jsonObject.has("yearid") ? Integer.parseInt(jsonObject.getString("yearid")) : 2000;
			int monthid = jsonObject.has("monthid") ? Integer.parseInt(jsonObject.getString("monthid")) : 0;
			if (yearid > 2000 && yearid < 3000 && monthid >= 1 && monthid <= 12) {
				mindate = Func.DateToStr(Func.getFirstDayOfMonth(yearid, monthid));
				maxdate = Func.DateToStr(Func.getLastDayOfMonth(yearid, monthid));
			}
		}
		long bookid = jsonObject.has("bookid") ? Long.parseLong(jsonObject.getString("bookid")) : 0;
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = "A.ID,A.NOTEDATE,A.CURR0,A.CURR1,A.CURR2,A.REMARK,A.YWNOTENO,A.LASTOP,A.CHECKMAN,A.SUBID,B.SUBNAME,b.IMGINDEX";
		fieldlist += ",to_char(a.notedate,'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''') as weekstr";
		// dal.setBookid(bookid);
		String strwhere = " a.bookid=" + bookid + " and a.accid=" + htp.getMaccid();
		strwhere += " and a.notedate>=to_date('" + mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		strwhere += " and a.notedate<=to_date('" + maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		String sort = " notedate desc,id desc ";
		if (dayhj != 1)
			sort = "notedate ,id ";

		QueryParam qp = new QueryParam(page, pagesize, sort);
		qp.setSumString("nvl(sum(curr0),0) as totalcurr0,nvl(sum(curr1),0) as totalcurr1");
		Bookdetail dal = new Bookdetail();
		dal.setAccid(htp.getMaccid());
		dal.setBookid(bookid);
		dal.setMaxdate(maxdate);
		dal.setMindate(mindate);
		dal.setDayhj(dayhj);

		if (page < 0) {// 导出excel
			// String retcs = DbHelperSQL.Table2Excel(tb, jsonObject);
			String retcs = dal.GetTable2Excel(qp, strwhere, fieldlist, jsonObject);
			WriteResult(response, Integer.parseInt(retcs.substring(0, 1)), retcs.substring(1));
			return;
		}

		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere, fieldlist);
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
		// String strjson = dal.GetTable2Json(qp, strwhere, fieldlist);
		// Write(response,strjson);
	}

	// 查询账本明细记录
	protected void FindBookdetail(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String nowdatestr = htp.getNowdate();// Func.DateToStr(pFunc.getServerdatetime(),
												// "yyyy-MM-dd");
		String mindate = jsonObject.has("mindate") ? jsonObject.getString("mindate") : nowdatestr;
		String maxdate = jsonObject.has("maxdate") ? jsonObject.getString("maxdate") : nowdatestr;
		String subidlist = jsonObject.has("subidlist") ? jsonObject.getString("subidlist") : "";
		String bookidlist = jsonObject.has("bookidlist") ? jsonObject.getString("bookidlist") : "";
		String remark = jsonObject.has("remark") ? jsonObject.getString("remark") : "";
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		String fieldlist = " A.ID,A.NOTEDATE,A.CURR0,A.CURR1,A.CURR2,A.REMARK,A.YWNOTENO,A.LASTOP,A.CHECKMAN,A.SUBID,B.SUBNAME ";

		String strwhere = " a.accid=" + htp.getMaccid();
		strwhere += " and a.notedate>=to_date('" + mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		strwhere += " and a.notedate<=to_date('" + maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		if (!bookidlist.equals(""))
			strwhere += " and (" + Func.getCondition("a.bookid", bookidlist) + ") ";

		if (!subidlist.equals(""))
			strwhere += " and (" + Func.getCondition("a.subid", subidlist) + ") ";

		if (!remark.equals(""))
			strwhere += " and a.remark like '%" + remark + "%'";
		String sort = " notedate desc,id desc ";
		QueryParam qp = new QueryParam(page, pagesize, sort);
		Bookdetail dal = new Bookdetail();
		Table tb = new Table();
		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 设置账本标志
	protected void SetAccbook(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long bookid = jsonObject.has("bookid") ? Long.parseLong(jsonObject.getString("bookid")) : 0;
		int statetag = jsonObject.has("statetag") ? Integer.parseInt(jsonObject.getString("statetag")) : 0;
		// statetag:1=启用，2=禁用
		Accbook dal = new Accbook();
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		dal.setBookid(bookid);
		dal.setStatetag(statetag);
		WriteResult(response, dal.Enabled(), dal.getErrmess());

	}

	// 删除记账本记账记录
	protected void DelBookdetail(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// long bookid = jsonObject.has("bookid") ?
		// Long.parseLong(jsonObject.getString("bookid")) : 0;
		// lx:1=收入，2=支出
		// float id = jsonObject.has("id") ?
		// Float.parseFloat(jsonObject.getString("id")) : 0;
		// String nowdatestr = Func.DateToStr(pFunc.getServerdatetime(),
		// "yyyy-MM-dd HH:mm:ss");
		// String notedate = jsonObject.has("notedate") ?
		// jsonObject.getString("notedate") : nowdatestr;
		Bookdetail dal = new Bookdetail();
		// System.out.println("a1");
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		// System.out.println("a2");
		dal.setLastop(htp.getUsername());
		if (dal.Delete() == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());
		}

	}

	// 审核记账本记账记录
	protected void CheckBookdetail(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		float id = jsonObject.has("id") ? Float.parseFloat(jsonObject.getString("id")) : 0;
		long bookid = jsonObject.has("bookid") ? Long.parseLong(jsonObject.getString("bookid")) : 0;
		Bookdetail dal = new Bookdetail();
		dal.setId(id);
		dal.setBookid(bookid);
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		// if (dal.Check() == 0) {
		WriteResult(response, dal.Check(), dal.getErrmess());
		// } else {
		// WriteResultJson(response, 1, dal.getErrmess());
		// }

	}

	// 修改记账本记账记录
	protected void UpdateBookdetail(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Double curr = jsonObject.has("curr") ? Double.parseDouble(jsonObject.getString("curr")) : null;
		// int lx = jsonObject.has("lx") ?
		// Integer.parseInt(jsonObject.getString("lx")) : 1;
		// lx:1=收入，2=支出
		Bookdetail dal = new Bookdetail();
		// System.out.println("a1");
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setLastop(htp.getUsername());
		dal.setAccid(htp.getMaccid());
		if (dal.Update(curr) == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());
		}

	}

	// 增加账本记账记录
	protected void AddBookdetail(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// int lx = jsonObject.has("lx") ?
		// Integer.parseInt(jsonObject.getString("lx")) : 1;
		// lx:1=收入，2=支出
		Double curr = jsonObject.has("curr") ? Double.parseDouble(jsonObject.getString("curr")) : 0;
		// String nowdatestr = Func.DateToStr(pFunc.getServerdatetime(),
		// "yyyy-MM-dd HH:mm:ss");
		// String notedate = jsonObject.has("notedate") ?
		// jsonObject.getString("notedate") : nowdatestr;
		Bookdetail dal = new Bookdetail();
		// System.out.println("a1");
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		// System.out.println("a2");
		dal.setLastop(htp.getUsername());
		dal.setAccid(htp.getMaccid());
		if (dal.Append(curr) == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());
		}

	}

	// 增加账本转账记录
	protected void AddtoBookdetail(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// int lx = jsonObject.has("lx") ?
		// Integer.parseInt(jsonObject.getString("lx")) : 1;
		// lx:1=收入，2=支出
		Double curr = jsonObject.has("curr") ? Double.parseDouble(jsonObject.getString("curr")) : 0;
		// String nowdatestr = Func.DateToStr(pFunc.getServerdatetime(),
		// "yyyy-MM-dd HH:mm:ss");
		// String notedate = jsonObject.has("notedate") ?
		// jsonObject.getString("notedate") : nowdatestr;
		Bookdetail dal = new Bookdetail();
		// System.out.println("a1");
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		// System.out.println("a2");
		dal.setLastop(htp.getUsername());
		dal.setAccid(htp.getMaccid());
		if (dal.Appendto(curr) == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());
		}

	}

	// 取店员销售计划
	protected void GetSalemanplan(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		int yearnum = jsonObject.has("yearnum") ? Integer.parseInt(jsonObject.getString("yearnum")) : 0;
		int monthnum = jsonObject.has("monthnum") ? Integer.parseInt(jsonObject.getString("monthnum")) : 0;
		Salemanplan dal = new Salemanplan();
		dal.setEpid(htp.getUserid());
		dal.setAccid(htp.getMaccid());
		if (dal.doGetSalemanplan(houseid, yearnum, monthnum) == 0) {
			WriteResult(response, 0, dal.getErrmess());
		} else {
			WriteResultJson(response, 1, dal.getErrmess());
		}
	}

	// 自动均分店员销售计划
	protected void AutoSalemanplan(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		int yearnum = jsonObject.has("yearnum") ? Integer.parseInt(jsonObject.getString("yearnum")) : 0;
		int monthnum = jsonObject.has("monthnum") ? Integer.parseInt(jsonObject.getString("monthnum")) : 0;
		Salemanplan dal = new Salemanplan();
		dal.setEpid(htp.getUserid());
		dal.setAccid(htp.getMaccid());
		WriteResult(response, dal.doAutoSalemanplan(houseid, yearnum, monthnum), dal.getErrmess());

	}

	// 每日店员销售日计划明细
	protected void WriteSalemanplan(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		String currdate = jsonObject.has("currdate") ? jsonObject.getString("currdate") : htp.getNowdate();
		Salemanplan dal = new Salemanplan();
		dal.setAccid(htp.getMaccid());
		dal.setEpid(htp.getUserid());
		dal.setLastop(htp.getUsername());
		WriteResult(response, dal.Write(houseid, currdate, jsonObject), dal.getErrmess());
	}

	// 每日店员销售日计划明细
	protected void ListSalemanplan(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		String currdate = jsonObject.has("currdate") ? jsonObject.getString("currdate") : htp.getNowdate();
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 10;
		long accid = htp.getMaccid();
		String qry = "";
		if (page == 1) {
			qry = "   insert into salemanplan (id,epid,saledate,curr) ";
			qry += "   select salemanplan_id.nextval,a.epid,to_date('" + currdate + "','yyyy-mm-dd'),0 from employe a";
			qry += "   where a.accid=" + accid + " and a.statetag=1 and a.noused=0 and a.houseid=" + houseid;
			qry += "   and not exists (select 1 from salemanplan b,employe c where b.epid=c.epid and c.accid=" + accid + " and a.epid=b.epid ";
			qry += "   and b.saledate >= to_date('" + currdate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
			qry += "   and b.saledate <= to_date('" + currdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss') )";
			DbHelperSQL.ExecuteSql(qry);
		}
		String fieldlist = " a.id,a.epid,b.epname,a.curr ";
		String strwhere = "  b.accid=" + accid + " and b.statetag=1 and b.noused=0 and b.houseid=" + houseid;
		strwhere += " and a.saledate >= to_date('" + currdate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		strwhere += " and a.saledate <= to_date('" + currdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		String sort = " id ";

		Salemanplan dal = new Salemanplan();
		QueryParam qp = new QueryParam(page, pagesize, sort);
		Table tb = new Table();

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	//同步一卡易会员
	protected void yky_syncvip(HttpServletResponse response, HttpInfo htp, int fs) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long accid = htp.getMaccid();
		if (fs == 1)
			accid = 17873;
		if (accid != 17873) {//胡燃商户 :accname=CQHR666  accid=17873
			WriteResult(response, 0, "企业账号无效！");
			return;
		}
		int page = 0;// jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = 100;// jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 100;
		//		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : "Guid";
		//		String order = jsonObject.has("order") ? jsonObject.getString("order") : "";
		String sort = " ModifiedTime,guid";
		Uparameter dal = new Uparameter();

		dal.setAccid(accid);
		dal.setUsection("options");
		dal.setUsymbol("syncdatetime");
		String syncdatetime = dal.GetList();//获取上次同步时间
		String strwhere = "1=1";
		if (!Func.isNull(syncdatetime))
			strwhere += " and ModifiedTime>'" + syncdatetime + "'";
		//		System.out.println(strwhere);
		Guestvip vip = new Guestvip();
		vip.setAccid(accid);
		vip.setLastop(htp.getUsername());

		int total = -1;
		int total0 = 0;
		do {

			Map<String, Object> data = new HashMap<>();
			data.put("userAccount", "10000");
			data.put("where", strwhere);
			data.put("pageIndex", page);
			data.put("pageSize", pagesize);
			data.put("orderBy", sort);
			//			System.out.println("11111");
			String responseOut = FYApiClientUtils.httpPost("Get_MembersPagedV2", data);
//			System.out.println("page=" + page + ", 解析的数据:" + responseOut);
			jsonObject = JSONObject.fromObject(responseOut);
			int status = Integer.parseInt(jsonObject.getString("status"));
			if (status < 0) {
				WriteResult(response, 0, jsonObject.getString("message"));
				return;
			}
			if (total < 0) {
				total = Integer.parseInt(jsonObject.getString("total"));
				if (total == 0)
					break;
			}
			String modifiedTime = "";
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				modifiedTime = jsonObject2.getString("ModifiedTime");
				vip.setIdstr(jsonObject2.getString("Guid"));
				//				System.out.println(vip.getIdstr());
				vip.setGuestname(jsonObject2.getString("TrueName")); //会员姓名
				vip.setMobile(jsonObject2.getString("Mobile"));//手机号
				String sex = jsonObject2.getString("sex");
				if (sex.equals("先生"))
					vip.setSex("男"); //性别
				else if (sex.equals("女士"))
					vip.setSex("女"); //性别
				String vipno = jsonObject2.getString("CardId");//会员卡号
				if (vipno.equals("null"))
					vipno = "";
				vip.setVipno(vipno);
				String idcard = jsonObject2.getString("IdCard");
				if (idcard.equals("null"))
					idcard = "";
				vip.setIdcard(idcard);  //身份证号
				vip.setBirthday(jsonObject2.getString("Birthday"));  //生日
				vip.setRemark(jsonObject2.getString("Meno"));  //备注
				vip.setEmail(jsonObject2.getString("Email"));  //电邮
				vip.setAddress(jsonObject2.getString("Address"));
				vip.setCreatedate(jsonObject2.getString("RegisterTime")); //注册时间
				String DurationTime = jsonObject2.getString("DurationTime");
				if (!Func.isValidDate(DurationTime))
					DurationTime = "2100-12-31";  //永久有效"

				vip.setValiddate(DurationTime);//有效期
				vip.setVtname(jsonObject2.getString("MemberGroupName"));//卡类型
				vip.setHousename(jsonObject2.getString("ChainStoreName"));//注册所在店面 

				vip.setLytag(2);//1=APP申请，0=输入,2=一卡易

				if (vip.ykyUpdate() == 0) {//错误记录日志保存到警告文件中
					LogUtils.LogWrite("ykyUpdate", vip.getErrmess(), "warn");
				}
				total0++;
			}
			if (modifiedTime.length() > 0) {
				dal.setUvalue(modifiedTime);
				dal.Update();
			}
			page++;
		} while (total0 < total);

		//		System.out.println(total);
		//		Write(response, responseOut);
		//		System.out.println("解析的数据" + responseOut);

		//保存本次同步时间
		WriteResult(response, 1, "本次更新" + total + "条会员记录！");
	}

	// 取短信余额
	protected void GetMessagebal(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {

		String retcs = Func.getMessagebal(htp.getMaccid(), htp.getSmsuid(), htp.getSmsuser(), htp.getSmspwd());
		if (retcs.substring(0, 1).equals("1"))
			WriteResult(response, 1, retcs.substring(1));
		else
			WriteResult(response, 0, retcs.substring(1));
	}

	// 发送短信
	protected void SendMessage(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String mobile = jsonObject.has("mobile") ? jsonObject.getString("mobile") : "";
		String content = jsonObject.has("content") ? jsonObject.getString("content") : "";

		String retcs = Func.sendMessage(htp.getSmsuid(), htp.getSmsuser(), htp.getSmspwd(), mobile, content, htp.getUsername(), htp.getMaccid());
		if (retcs.substring(0, 1).equals("1"))
			WriteResult(response, 1, retcs.substring(1));
		else
			WriteResult(response, 0, retcs.substring(1));
	}

	// ********************************************************
	protected void ServerDate(HttpServletResponse response) throws ServletException, IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// System.out.println("ServerDate ");
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
