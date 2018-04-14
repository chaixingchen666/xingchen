package com.flyang.main;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.tool.Func;
import com.common.tool.LogUtils;
import com.comdot.data.Table;

import com.oracle.bean.*;
import com.netdata.db.*;

import net.sf.json.JSONObject;

/**
 * 不加密调用的接口，不判断签名
 */
@WebServlet("/pub")
public class PubServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String filePath = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PubServlet() {
		super();
		// TODO Auto-generated constructor stub
		filePath = Func.getRootPath();// +"skyderp.config/";
		System.out.println("PubServlet is ready ok! " + filePath);
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
		// LogUtils.LogWrite("ERP调用", "", url + " post:" +
		// request.getParameterMap().toString());

		String action = request.getParameter("action").toLowerCase();
		// filePath=Func.getRootPath();
		if (action.equals("path")) {
			response.getWriter().write(filePath + "  ---pub  ");
			return;
		} else

		if (action.equals("serverdate")) {
			ServerDateTime(response);
			return;
		}

		HttpInfo htp1 = new HttpInfo();
		htp1.setAction(action);// 设action
		htp1.setIpaddress(Func.getIpAddress(request));
		String signjson = request.getParameter("sign"); // 取sign参数
		// System.out.println(signjson);
		if (signjson == null || signjson.equals("")) {
			WriteResult(response, 0, "sign参数无效！");
			return;
		}
		// base64解码
		// signjson = Func.Base64Decode(signjson);// sign base64解码
		htp1.setUrl(url);//

		String datajson = request.getParameter("data");
		if (datajson != null)
			htp1.setDatajson(datajson);// 传入data参数

		// =================
		String pictjson = request.getParameter("pict");

		if (pictjson != null)// pictjson="";
			htp1.setPictjson(pictjson);// 传入pict参数
		// ===================
		int ret = htp1.setSignjson(signjson, 1);
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
		// System.out.println("action=" + action);
		switch (action) {
		//		case "accregcount": // 取注册账户数及当前登录用户数
		//			GetAccregcount(response);
		//			break;
		case "accregnearlist": // 取最近注册用户
			GetAccregnearlist(response, htp1);
			break;
		case "banklist":                 // 银行分页列表
			GetBankList(response, htp1);
			break;
		case "banklistx":                // 银行分页列表
			GetBankList(response, htp1);
			break;
		case "prtserverstart":          //启动后台打印服务器
			PrtServerStart(response, htp1);
			break;
		case "updateprintset":       // 更新指定打印机id信息
			UpdatePrintsetByID(response, htp1);
			break;
		case "getprintset": // 获取指定打印机id信息
			GetPrintsetByID(response, htp1);
			break;
		case "uploadimagex"://上传图片
			UploadImage(response, htp1);
			break;
		case "updateaccregpay":          // 更新企业账户支付信息表
			UpdateAccregpay(response, htp1);
			break;
		case "yht_custaccess":           // 贷款客户准入
			YHT_Custaccess(response, htp1);
			break;
		case "yht_loaninformation":      // 客户贷款信息列表
			YHT_Loaninformation(response, htp1);
			break;
		case "yht_getaccregbyid":        // 获取指定账户id信息
			GetAccregByID(response, htp1);
			break;
		case "addcreditrecord":          // 新增贷款申请
			AddCreditrecordRec(response, htp1);
			break;
		case "listcreditrecord":         // 分页显示贷款申请
			ListCreditrecordRec(response, htp1);
			break;
		case "delcreditrecord":          // 删除贷款申请
			DelCreditrecordRec(response, htp1);
			break;
		case "getcreditrecord":          // 查询贷款信息
			GetCreditrecordRec(response, htp1);
			break;
		default:
			WriteResult(response, 0, "PUB接口未定义:" + action + "!");
			break;
		}
	}

	// 获取最近注册用户
	protected void GetAccregnearlist(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// int page = jsonObject.has("page") ?
		// Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 50;
		Accreg dal = new Accreg();
		Table tb = new Table();
		QueryParam qp = new QueryParam(-1, pagesize, "regdate desc");
		tb = dal.GetTable(qp);
		//		Write(response, DbHelperSQL.DataTable2Json(tb));
		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));
	}

	// 取注册账户数和当前用户数
	//	protected void GetAccregcount(HttpServletResponse response) throws ServletException, IOException {
	////		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
	//        String qry = "select  (select count(*) from accreg)+3000 as acccount";
	//        qry += ",(select count(*) from useronline)+3000 as usercount ";
	//        qry += " from dual ";
	//		Accreg dal = new Accreg();
	//
	//		Table tb = new Table();
	//		tb = dal.GetTable(qry);
	//		Write(response, DbHelperSQL.DataTable2Json(tb));
	//	}

	// 银行分页列表
	protected void GetBankList(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		// System.out.println("BrandList 1 maccid=" + htp.maccid);
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// {"flyang":"20150107","rows":10,"page":1,"fieldlist":"brandid,brandname","sort":"brandid","order":"asc"}
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 50;
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		String sort = jsonObject.has("sort") ? jsonObject.getString("sort") : " bank_name ";
		String order = jsonObject.has("order") ? jsonObject.getString("order") : " asc ";
		String findbox = jsonObject.has("findbox") ? jsonObject.getString("findbox").trim().replace("'", "''") : "";
		String city = jsonObject.has("city") ? jsonObject.getString("city").replace("'", "''") : "";
		String province = jsonObject.has("province") ? jsonObject.getString("province").replace("'", "''") : "";
		String type_code = jsonObject.has("type_code") ? jsonObject.getString("type_code").replace("'", "''") : "";
		sort += " " + order;
		// String sort = " BRANDNAME,BRANDID ";
		// System.out.println("brandid=" + brandid);
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "TYPE_CODE,BANK_CHANNEL_NO,BANK_NAME,PROVINCE,CITY,CLEAR_BANK_CHANNEL_NO,ID";
		if (!fieldlist.toUpperCase().contains("ID"))
			fieldlist += ",ID";

		// long accid = htp.getMaccid();
		// long userid = htp.getUserid();
		String strwhere = " 1=1";

		page = province.length() <= 0 || city.length() <= 0 || type_code.length() <= 0 ? 1 : page;

		if (!Func.isNull(findbox))
			strwhere += " and BANK_NAME like '%" + findbox + "%' ";
		if (province.length() > 0)
			strwhere += " and province='" + province + "'";
		if (city.length() > 0)
			strwhere += " and city='" + city + "'";
		if (type_code.length() > 0)
			strwhere += " and type_code='" + type_code + "'";
		Table tb = new Table();
		Bankinfo dal = new Bankinfo();
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

	protected void GetPrintsetByID(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		long accid = jsonObject.has("accid") ? Long.parseLong(jsonObject.getString("accid")) : 0;
		long prtid = jsonObject.has("prtid") ? Long.parseLong(jsonObject.getString("prtid")) : 0;
		Printset dal = new Printset();
		dal.setPrtid(prtid);
		dal.setAccid(accid);
		if (dal.Get() == 0)
			WriteResult(response, 0, dal.getErrmess());
		else
			WriteResultJson(response, 1, dal.getErrmess());
	}

	// 更新指定打印端口id信息
	protected void UpdatePrintsetByID(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		long accid = jsonObject.has("accid") ? Long.parseLong(jsonObject.getString("accid")) : 0;
		long prtid = jsonObject.has("prtid") ? Long.parseLong(jsonObject.getString("prtid")) : 0;
		int port = jsonObject.has("port") ? Integer.parseInt(jsonObject.getString("port")) : 0;
		String ipstr = jsonObject.has("ipstr") ? jsonObject.getString("ipstr") : "";

		Printset dal = new Printset();
		dal.setPort(port);
		dal.setPrtid(prtid);
		dal.setIpstr(ipstr);
		dal.setAccid(accid);
		//		dal.setLastop(htp.getUsername());
		//		dal.setAccid(htp.getMaccid());
		WriteResult(response, dal.Update(), dal.getErrmess());
	}

	//启动后台打印服务
	protected void PrtServerStart(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String serverip = jsonObject.has("serverip") ? jsonObject.getString("serverip") : "";
		int port = jsonObject.has("port") ? Integer.parseInt(jsonObject.getString("port")) : 0;
		if (port <= 0) {
			WriteResult(response, 0, "端口无效！");
			return;
		}
		Uparameter dal = new Uparameter();
		dal.setAccid((long) 0);
		dal.setUsection("OPTIONS");
		dal.setUsymbol("IPADDRESS");
		if (serverip.length() <= 0)
			serverip = Func.getInternetIp();
		dal.setUvalue(serverip + ":" + port);
		if (dal.Update() == 0)
			WriteResult(response, 0, dal.getErrmess());
		else
			WriteResult(response, 1, serverip);

	}

	// 更改企业账户支付信息表
	protected void UpdateAccregpay(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Accregpay dal = new Accregpay();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		// dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());

		WriteResult(response, dal.Update(htp.getPictjson()), dal.getErrmess());

	}

	// 分页显示贷款申请记录
	protected void ListCreditrecordRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		int page = jsonObject.has("page") ? Integer.parseInt(jsonObject.getString("page")) : 1;
		int pagesize = jsonObject.has("rows") ? Integer.parseInt(jsonObject.getString("rows")) : 10;
		if (pagesize > 50)
			pagesize = 50;
		String fieldlist = jsonObject.has("fieldlist") ? jsonObject.getString("fieldlist") : "*";
		// String fieldlist = jsonObject.has("fieldlist") ?
		// jsonObject.getString("fieldlist") : "*";
		// System.out.println("brandid=" + brandid);
		if (fieldlist.equals("*") || fieldlist.equals(""))
			fieldlist = "ID,NOTEDATE,SURNAME,IDNO,MOBILE,LASTOP,DKCURR";
		long accid = htp.getMaccid();
		// long userid = htp.getUserid();
		// String qxpublic = htp.getQxpublic();
		String strwhere = " accid=" + accid + " and statetag<9";

		// if (!findbox.equals(""))
		// strwhere += " and (BRANDNAME like '%" + findbox + "%' or shortname
		// like '%" + findbox.toUpperCase() + "%')";

		Table tb = new Table();
		Creditrecord dal = new Creditrecord();
		String sort = " notedate desc,id ";
		QueryParam qp = new QueryParam(page, pagesize, sort);

		tb = dal.GetTable(qp, strwhere, fieldlist);

		Write(response, DbHelperSQL.DataTable2Json(tb, qp.getTotalString()));

	}

	// 查询贷款信息
	protected void GetCreditrecordRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());

		Creditrecord dal = new Creditrecord();
		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());

		dal.setAccid(htp.getMaccid());
		WriteResult(response, dal.GetInfo(), dal.getErrmess());

	}

	// 删除贷款申请记录
	protected void DelCreditrecordRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		float id = jsonObject.has("id") ? Float.parseFloat(jsonObject.getString("id")) : 0;
		Creditrecord dal = new Creditrecord();
		dal.setAccid(htp.getMaccid());
		dal.setId(id);
		WriteResult(response, dal.Delete(), dal.getErrmess());
	}

	// 客户贷款信息列表
	protected void YHT_Loaninformation(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		// {"flyang":"20150107","surname":"朱道真","mobile":"17826871520","idno":"330127199302122736"}
		// 朱道真 17826871520 101 330127199302122736
		Creditrecord dal = new Creditrecord();

		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		dal.setStatetag(0);
		dal.doLoaninformation();
		//		if (dal.doLoanlist() == 0)
		//			WriteResult(response, 0, dal.getErrmess());
		//		else
		response.getWriter().write(dal.getErrmess());
	}

	// 贷款客户准入
	protected void YHT_Custaccess(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		WriteResult(response, 0, "因为系统内测，所以贷款功能暂时关闭，敬请期待！");
		return;
		//暂时关闭2017.12.29
		//		if (htp.getMaccid() == 1000 && htp.getUsername().equals("演示")) {
		//			WriteResult(response, 0, "这是演示套账，不允许准入！");
		//			return;
		//		}
		//		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		//		String surname = jsonObject.has("surname") ? jsonObject.getString("surname") : "";
		//		String idno = jsonObject.has("idno") ? jsonObject.getString("idno") : "";
		//		String mobile = jsonObject.has("mobile") ? jsonObject.getString("mobile") : "";
		//		int ok = jsonObject.has("ok") ? Integer.parseInt(jsonObject.getString("ok")) : 1;
		//
		//		Accreg dal = new Accreg();
		//		dal.setDksurname(surname);
		//		dal.setDkidno(idno);
		//		dal.setDkmobile(mobile);
		//		dal.setDkok(ok);
		//		dal.setAccid(htp.getMaccid());
		//		dal.setStatetag(0);
		//		WriteResultJson(response, dal.doCustaccess(), dal.getErrmess());

	}

	// 获取指定账号id信息
	protected void GetAccregByID(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {

		WriteResult(response, 0, "因为系统内测，所以贷款功能暂时关闭，敬请期待！");
		return;

		//暂时关闭  2017.12.29

		//		long accid = htp.getMaccid();// jsonObject.has("accid") ? Long.parseLong(jsonObject.getString("accid")) : 0;
		//		if (accid == 1000 && htp.getUsername().equals("演示")) {
		//			WriteResult(response, 0, "这是演示套账，不允许贷款！");
		//			return;
		//		} 
		//		String strwhere = " accid= " + accid;
		//		String fieldlist = " accid,accname,dksurname,dkidno,dkmobile,dkok";
		//		Table tb = new Table();
		//		Accreg dal = new Accreg();
		//		tb = dal.GetList1(strwhere, fieldlist).getTable(1);
		//		Write(response, DbHelperSQL.DataTable2Json(tb));

	}

	// 增加贷款申请记录
	protected void AddCreditrecordRec(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		Creditrecord dal = new Creditrecord();
		// dal.setUserid(htp.getUserid());

		// 读取json数据到表类
		DbHelperSQL.JsonConvertObject(dal, jsonObject);
		dal.setAccid(htp.getMaccid());
		dal.setLastop(htp.getUsername());
		dal.setStatetag(0);
		if (dal.Append() == 0)
			WriteResult(response, 0, dal.getErrmess());
		else
			WriteResultJson(response, 1, dal.getErrmess());
	}

	/// 上传图片
	protected void UploadImage(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {

		String pictjson = htp.getPictjson();
		if (Func.isNull(pictjson)) {
			WriteResult(response, 0, "pictjson参数无效！");
			return;
		}
		JSONObject jsonObject = JSONObject.fromObject(pictjson);
		if (jsonObject.has("image")) {
			String retcs = Func.Base64UpFastDFS(jsonObject.getString("image"));
			if (retcs.substring(0, 1).equals("1")) {
				WriteResult(response, 1, retcs.substring(1));
			} else {
				WriteResult(response, 0, retcs.substring(1));
			}
		} else
			WriteResult(response, 0, "image参数无效！");

	} // ==============================

	// ********************************************************
	protected void ServerDate(HttpServletResponse response) throws ServletException, IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		//		System.out.println("ServerDate ");
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
