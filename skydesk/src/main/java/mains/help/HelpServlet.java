package mains.help;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.flyang.tools.FlyangHttp;

import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;
import tools.LogUtils;

/**
 * Servlet implementation class HelpServlet
 */
@WebServlet("/skymain/HelpList/HelpServlet")
public class HelpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HelpServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		AppData appData = new AppData();
		HttpSession session = request.getSession(true);
		appData.setqxpublic(request, response, session);

		String reqpar = request.getParameter("helpser");
		if (reqpar.equals("getprintcs")) {// 获取端口
			getprintcs(request, response, appData);
		}
		if (reqpar.equals("selprints")) {// 选择端口
			selprints(request, response, appData);
		}
		if (reqpar.equals("getdepartmentlist")) {// 销售部门
			getdepartmentlist(request, response, appData);
		}
		if (reqpar.equals("getemployelist")) {// 销售员
			getemployelist(request, response, appData);
		}
		if (reqpar.equals("gethouselist")) {
			gethouselist(request, response, appData);
		}
		if (reqpar.equals("mybuyerlist")) {
			mybuyerlist(request, response, appData);
		}
		if (reqpar.equals("addhouse")) {
			try {
				addhouse(request, response, appData);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (reqpar.equals("getwaredata")) {
			getwaredata(request, response, appData);
		}
		if (reqpar.equals("quicksearchware")) {
			quicksearchware(request, response, appData);
		}
		if (reqpar.equals("getsizelist")) {
			getsizelist(request, response, appData);
		}
		if (reqpar.equals("getwarecolor")) {
			getwarecolor(request, response, appData);
		}
		if (reqpar.equals("quicksearchwarecolor")) {
			quicksearchwarecolor(request, response, appData);
		}
		if (reqpar.equals("quicksearchallcolor")) {
			quicksearchallcolor(request, response, appData);
		}
		if (reqpar.equals("getwaresize")) {
			getwaresize(request, response, appData);
		}
		if (reqpar.equals("getwaresizepage")) {
			getwaresizepage(request, response, appData);
		}
		if (reqpar.equals("addwaresize")) {
			try {
				addwaresize(request, response, appData);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (reqpar.equals("getcolor")) {
			getcolor(request, response, appData);
		}
		if (reqpar.equals("writewarecolor")) {
			try {
				writewarecolor(request, response, appData);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (reqpar.equals("getwaresizeandcolor")) {
			getwaresizeandcolor(request, response, appData);
		}
		if (reqpar.equals("getwaretype")) {
			getwaretype(request, response, appData);
		}
		if (reqpar.equals("getwaretypelist")) {
			getwaretypelist(request, response, appData);
		}
		if (reqpar.equals("warewavelist")) {
			warewavelist(request, response, appData);
		}
		if (reqpar.equals("getseasonlist")) {
			getseasonlist(request, response, appData);
		}
		if (reqpar.equals("getbranddata")) {
			getbranddata(request, response, appData);
		}
		if (reqpar.equals("quicksearchbrand")) {
			try {
				quicksearchbrand(request, response, appData);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (reqpar.equals("getsupinfodata")) {
			getsupinfodata(request, response, appData);
		}
		if (reqpar.equals("getcustlist")) {
			getcustlist(request, response, appData);
		}
		if (reqpar.equals("getsaletype")) {
			getsaletype(request, response, appData);
		}
		if (reqpar.equals("getguestviplist")) {
			getguestviplist(request, response, appData);
		}
		if (reqpar.equals("getguestvipbyno")) {
			getguestvipbyno(request, response, appData);
		}
		if (reqpar.equals("getwaresum")) {
			getwaresum(request, response, appData);
		}
		if (reqpar.equals("addbackprint")) {
			addbackprint(request, response, appData);
		}
		if (reqpar.equals("getchargeslist")) {
			getchargeslist(request, response, appData);
		}
		if (reqpar.equals("getpaywaylist")) {
			getpaywaylist(request, response, appData);
		}
		if (reqpar.equals("barcodeadd")) {
			barcodeadd(request, response, appData);
		}
		if (reqpar.equals("getviptypedata")) {
			getviptypedata(request, response, appData);
		}
		if (reqpar.equals("removesubmit")) {
			withdraw(request, response, appData);
		}
		if (reqpar.equals("listomsubject")) {// 订货会
			listomsubject(request, response, appData);
		}
		if (reqpar.equals("getsaleoutprice")) {// 商场零售调价
			getsaleoutprice(request, response, appData);
		}
		if (reqpar.equals("getwareoutprice")) {// 调价
			getwareoutprice(request, response, appData);
		}
		if (reqpar.equals("findguestvip")) {// 会员
			findguestvip(request, response, appData);
		}
		if (reqpar.equals("copynote")) {// 复制单据
			copynote(request, response, appData);
		}
		if (reqpar.equals("clearnote")) {// 冲单单据
			clearnote(request, response, appData);
		}
		if (reqpar.equals("wareoutcostlist")) {// 获取安居销售费用表
			wareoutcostlist(request, response, appData);
		}
		if (reqpar.equals("addwareoutcost")) {// 增加销售费用
			addwareoutcost(request, response, appData);
		}
		if (reqpar.equals("delwareoutcostbyid")) {// 删除销售费用
			delwareoutcostbyid(request, response, appData);
		}
		if (reqpar.equals("changenotedate")) {// 改单据日期
			changenotedate(request, response, appData);
		}
		if (reqpar.equals("setparam")) {// 设置参数
			setparam(request, response, appData);
		}
		if (reqpar.equals("getparam")) {// 获取参数
			getparam(request, response, appData);
		}
		if (reqpar.equals("getincomebal")) {// 获取客户欠款
			getincomebal(request, response, appData);
		}
		if (reqpar.equals("getpaybal")) {// 获取供应商欠款
			getpaybal(request, response, appData);
		}
	}

	private void delwareoutcostbyid(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 删除销售费用
		String id = request.getParameter("id");
		String noteno = request.getParameter("noteno");
		String res = HelpBase.delwareoutcostbyid(id, noteno, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void addwareoutcost(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 增加销售费用
		String noteno = request.getParameter("noteno");
		String cgid = request.getParameter("cgid");
		String curr = request.getParameter("curr");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("noteno", noteno);
		params.put("cgid", cgid);
		params.put("curr", curr);
		String res = HelpBase.addwareoutcost(params, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void wareoutcostlist(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 获取单据销售费用表
		String noteno = request.getParameter("noteno");
		String page = request.getParameter("page");
		HelpBase.page = page;
		String data = HelpBase.wareoutcostlist(noteno, appData);
		HelpBase.page = "1";
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void addhouse(HttpServletRequest request, HttpServletResponse response, AppData appData) throws Exception {
		// TODO 添加店铺
		String housename = request.getParameter("housename");
		String address = request.getParameter("address");
		String tel = request.getParameter("tel");
		String ontime = request.getParameter("ontime");
		String offtime = request.getParameter("offtime");
		String remark = request.getParameter("remark");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("housename", housename);
		params.put("address", address);
		params.put("tel", tel);
		params.put("ontime", ontime);
		params.put("offtime", offtime);
		params.put("remark", remark);
		String res = HelpBase.addhouse(params, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();

	}

	private void getprintcs(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 获取端口
		String progid = request.getParameter("progid");
		String data = HelpBase.getprintcs(progid, appData);
		// JSONObject jsonObj=JSONObject.fromObject(data);
		// StringBuffer text=new StringBuffer();
		// text.append("<option value=\"" +jsonObj.getString("PRTID") +"\">"
		// +jsonObj.getString("PRTID")+"</option>");
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	private void selprints(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 选择端口
		String progid = request.getParameter("progid");
		String prtid = request.getParameter("prtid");
		String data = HelpBase.selprints(progid, prtid, appData);
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	private void getdepartmentlist(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 获取销售员帮助列表
		String page = request.getParameter("page");
		String findbox = request.getParameter("findbox");
		HelpBase.page = page;
		String data = HelpBase.getdepartmentlist(findbox, appData);
		PrintWriter out = response.getWriter();
		HelpBase.page = "1";
		out.print(data);
		out.close();
	}

	private void getemployelist(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 获取销售员帮助列表
		String params = DataBase.getParamsData(request.getParameterMap());
		String data = HelpBase.getemployelist(params, appData);
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	private void gethouselist(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 获取店铺帮助列表
		String page = request.getParameter("page");
		String flag = request.getParameter("flag");
		String findbox = request.getParameter("findbox");
		HelpBase.page = page;
		String data = "";
		if (DataBase.isCS)
			System.out.println(flag);
		if (flag.equals("0")) {
			data = HelpBase.gethouselist(findbox, appData);
		} else {

			data = HelpBase.gethouselist1(findbox, appData);
		}
		PrintWriter out = response.getWriter();
		HelpBase.page = "1";
		out.print(data);
		out.close();
	}

	private void getwaredata(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 获取商品帮助列表
		String findbox = request.getParameter("findbox");
		String provid = request.getParameter("provid");
		String page = request.getParameter("page");
		HelpBase.page = page;
		String data = "";
		if (provid == null)
			data = HelpBase.getwarelist(findbox, appData);
		else if (provid.equals("no"))
			data = HelpBase.getwarelist(findbox, appData);
		else
			data = HelpBase.getwarelist(findbox, provid, appData);
		HelpBase.page = "1";
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	private void quicksearchware(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 快速搜索商品帮助列表
		String findbox = request.getParameter("findbox");
		String page = request.getParameter("page");
		HelpBase.page = page;
		String data = HelpBase.getwarelist(findbox, appData);
		HelpBase.page = "1";
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	private void getwarecolor(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 获取制定id商品的颜色
		String wareid = request.getParameter("wareid");
		String findbox = request.getParameter("findbox");
		String page = request.getParameter("page");
		HelpBase.page = page;
		if (wareid.equals("0")) {
			String data = HelpBase.quicksearchallcolor(wareid, findbox, appData);
			PrintWriter out = response.getWriter();
			out.print(data);
			out.close();
		} else {
			String res = HelpBase.getwarecolor(wareid, findbox, appData);
			HelpBase.page = "1";
			PrintWriter out = response.getWriter();
			out.print(res);
			out.close();
		}
	}

	private void quicksearchwarecolor(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 快速搜索商品颜色
		String findbox = request.getParameter("findbox");
		String wareid = request.getParameter("wareid");
		String data = HelpBase.quicksearchwarecolor(wareid, findbox, appData);
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	private void quicksearchallcolor(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 快速搜索商品颜色
		String findbox = request.getParameter("findbox");
		String wareid = request.getParameter("wareid");
		String data = HelpBase.quicksearchallcolor(wareid, findbox, appData);
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}


	private void mybuyerlist(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 获取客户数据
		String params = DataBase.getParamsData(request.getParameterMap());
		String data = HelpBase.mybuyerlist(params, appData);
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	private void getwaresize(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 获取制定id商品的尺码
		String wareid = request.getParameter("wareid");
		String page = request.getParameter("page");
		String findbox = request.getParameter("findbox");// 快速搜索
		String res;
		if ("0".equals(wareid)) {
			res = HelpBase.quicksearch(findbox, appData, page);
		} else {
			res = HelpBase.getwaresize(wareid, findbox, appData);
		}
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void addwaresize(HttpServletRequest request, HttpServletResponse response, AppData appData) {
		// TODO 添加尺码颜色

	}

	private void getwaresizepage(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 商品尺码分页
		String page = request.getParameter("page");
		String wareid = (String) request.getParameter("wareid");
		String findbox = (String) request.getParameter("findbox");
		HelpBase.page = page;
		String data = HelpBase.getwaresize(wareid, findbox, appData);
		PrintWriter out = response.getWriter();
		HelpBase.page = "1";
		out.print(data);
		out.close();
	}


	private void getcolor(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		String wareid = (String) request.getParameter("wareid");
		String findbox = request.getParameter("findbox");
		String page = request.getParameter("page");
		HelpBase.page = page;
		String data = HelpBase.warecolorlist(wareid, findbox, appData);
		HelpBase.page = "1";
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	private void getwaresizeandcolor(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 获取商品的颜色和尺码
		String wareid = (String) request.getParameter("wareid");
		String houseid = request.getParameter("houseid");
		String res;
		if (houseid == null) {
			res = HelpBase.getwaresizeandcolor(wareid, appData);
		} else {
			res = HelpBase.getwaresizeandcolor(wareid, houseid, appData);
		}
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();

	}

	private void getwaretype(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		String p_typeid = request.getParameter("p_typeid");
		String page = request.getParameter("page");
		HelpBase.page = page;
		String data = HelpBase.getwaretype(p_typeid, appData);
		HelpBase.page = "1";
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();

	}

	private void getwaresum(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		String houseid = request.getParameter("houseid");
		String wareid = request.getParameter("wareid");
		String sizeid = request.getParameter("sizeid");
		String colorid = request.getParameter("colorid");
		String res = HelpBase.getwaresum(houseid, wareid, sizeid, colorid, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void addbackprint(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		String houseid = request.getParameter("houseid").trim();
		String noteid = request.getParameter("noteid");
		String noteno = request.getParameter("noteno");
		String progid = request.getParameter("progid");
		if (houseid.equals("nohouse"))
			houseid = appData.houseid;
		String res = HelpBase.addbackprint(progid, houseid, noteid, noteno, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void getwaretypelist(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO Auto-generated method stub
		String typelist = HelpBase.getaccwaretype("0", appData);
		PrintWriter out = response.getWriter();
		out.print(typelist);
		out.close();
	}

	private void getsizelist(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		List<String> sizelist = HelpBase.getsizegroup(appData);
		List<String> sizeno = HelpBase.getsizeno(appData);
		StringBuffer text = new StringBuffer();
		if (sizelist.size() != 0) {
			for (int i = 0; i < sizelist.size(); i++) {
				text.append("<option value=\"" + sizeno.get(i) + "\">" + sizelist.get(i) + "</option>");
			}
			PrintWriter out = response.getWriter();
			out.print(text);
			out.close();
		}
	}

	private void getseasonlist(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO Auto-generated method stub
		List<String> seasonlist = HelpBase.seasonname(appData);
		StringBuffer text = new StringBuffer();
		if (seasonlist.size() != 0) {
			for (int i = 0; i < seasonlist.size(); i++) {
				text.append("<option value=\"" + seasonlist.get(i) + "\">" + seasonlist.get(i) + "</option>");
			}
			PrintWriter out = response.getWriter();
			out.print(text);
			out.close();
		}
	}
	
	private void warewavelist(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		HashMap<String, String> map = DataBase.getParamsMap(request.getParameterMap());
		map.remove("helpser");
		JSONObject dataobj = DataBase.getObjFrom(map);
		dataobj.put("rows", appData.pagecount);
		dataobj.put("sort", "waveid");
		dataobj.put("order", "asc");
		dataobj.put("page", "1");
		JSONObject resobj = new JSONObject();
		try {
			FlyangHttp fHttp = new FlyangHttp();
			fHttp.setHostIP(DataBase.HostIP2);
			resobj = fHttp.FlyangdoPost("warewavelist",dataobj,appData);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.LogWrite("ErorRequestData2", dataobj.toString());
			resobj.put("result", 0);
			resobj.put("msg", "请求返回数据格式异常！(E006)");
		}
		PrintWriter out = response.getWriter();
		out.print(resobj);
		out.close();
	}
	private void getbranddata(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 获取品牌数据
		String page = request.getParameter("page");
		String findbox = request.getParameter("findbox");
		HelpBase.page = page;
		String res = HelpBase.getbrandhelp(findbox, appData);
		HelpBase.page = "1";
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void writewarecolor(HttpServletRequest request, HttpServletResponse response, AppData appData) throws Exception {
		// TODO写商品颜色列表
		String wareid = (String) request.getParameter("wareid");
		String colorid = (String) request.getParameter("colorid");
		String value = (String) request.getParameter("value");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("colorid", colorid);
		params.put("value", value);
		String res = HelpBase.updatewarecolor(wareid, params, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void quicksearchbrand(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws Exception {
		// TODO 快速搜索品牌
		String page = request.getParameter("page");
		String findbox = request.getParameter("findbox");
		HelpBase.page = page;
		String data = HelpBase.getbrandhelp(findbox, appData);
		HelpBase.page = "1";
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	private void getsupinfodata(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 获取供应商数据
		String page = request.getParameter("page");
		String showall = request.getParameter("showall");
		String findbox = request.getParameter("findbox");
		HelpBase.page = page;
		String data = "";
		if (showall.equals("1"))
			data = HelpBase.getsupinfodata(findbox, showall, appData);
		else
			data = HelpBase.getsupinfodata(findbox, appData);
		HelpBase.page = "1";
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	private void getcustlist(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 获取客户数据
		String page = request.getParameter("page");
		String findbox = request.getParameter("findbox");
		HelpBase.page = page;
		String data = HelpBase.getcustlist(findbox, appData);
		HelpBase.page = "1";
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	private void getsaletype(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 获取客户数据
		String page = request.getParameter("page");
		String findbox = request.getParameter("findbox");
		HelpBase.page = page;
		String data = HelpBase.getsaletype(findbox, appData);
		HelpBase.page = "1";
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	private void getguestviplist(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 获取vip数据
		String page = request.getParameter("page");
		String findbox = request.getParameter("findbox");
		HelpBase.page = page;
		String data = HelpBase.getguestviplist(findbox, appData);
		HelpBase.page = "1";
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}
	private void getguestvipbyno(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 获取vip数据
		String params = DataBase.getParamsData(request.getParameterMap());
		String data = HelpBase.getguestvipbyno(params, appData);
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	// 获取费用项目列表
	private void getchargeslist(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO Auto-generated method stub
		String page = request.getParameter("page");
		String findbox = request.getParameter("findbox");
		HelpBase.page = page;
		String data = HelpBase.getChargeslist(findbox, appData);
		HelpBase.page = "1";
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	// 获取结算方式
	private void getpaywaylist(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO Auto-generated method stub
		String page = request.getParameter("page");
		String findbox = request.getParameter("findbox");
		HelpBase.page = page;
		String data = HelpBase.getPaywaylist(findbox, appData);
		HelpBase.page = "1";
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	private void barcodeadd(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		Map<String, String[]> params = request.getParameterMap();
		String get_params = DataBase.getParamsData(params);
		String res = HelpBase.barcodeadd(get_params, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	// 获取会员类型
	private void getviptypedata(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO Auto-generated method stub
		String page = request.getParameter("page");
		String findbox = request.getParameter("findbox");
		HelpBase.page = page;
		String data = HelpBase.getVipTypelist(findbox, appData);
		HelpBase.page = "1";
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	private void withdraw(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		String noteno = (String) request.getParameter("noteno");
		String id = (String) request.getParameter("id");
		String tablename = (String) request.getParameter("tablename");
		String res = "";
		if (appData.levelid.equals("0") || appData.allowchedan.equals("1")) {
			res = HelpBase.removesubmit(id, noteno, tablename, appData);
		} else {
			res = "您没有此权限！";
		}
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void listomsubject(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 获取订货会数据
		String page = request.getParameter("page");
		String findbox = request.getParameter("findbox");
		HelpBase.page = page;
		String res = HelpBase.listomsubject(findbox, appData);
		HelpBase.page = "1";
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void getsaleoutprice(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO Auto-generated method stub
		Map<String, String[]> params = request.getParameterMap();
		String get_params = DataBase.getParamsData(params);
		String res = HelpBase.getsaleoutprice(get_params, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void getwareoutprice(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO Auto-generated method stub
		Map<String, String[]> params = request.getParameterMap();
		String get_params = DataBase.getParamsData(params);
		String res = HelpBase.getwareoutprice(get_params, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void findguestvip(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		String findbox = request.getParameter("findbox");
		String res = HelpBase.findguestvip(findbox, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void copynote(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		String noteid = request.getParameter("noteid");
		String fromnoteno = request.getParameter("fromnoteno");
		String tonoteno = request.getParameter("tonoteno");
		String res = HelpBase.copynote(noteid, fromnoteno, tonoteno, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void clearnote(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		String noteid = request.getParameter("noteid");
		String noteno = request.getParameter("noteno");
		String res = HelpBase.clearnote(noteid, noteno, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void changenotedate(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		String id = request.getParameter("id");
		String noteno = request.getParameter("noteno");
		String notedatestr = request.getParameter("notedatestr");
		String param = action + "&id=" + id + "&noteno=" + noteno + "&notedatestr=" + notedatestr;
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("notedatestr", notedatestr);
		String res = HelpBase.changenotedate(param, params, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}
	
	private void setparam(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		Map<String, String[]> params = request.getParameterMap();
		String get_params = DataBase.getParamsData(params);
		HelpBase HB = new HelpBase();
		String res = HB.setparam(get_params, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}
	private void getparam(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		Map<String, String[]> params = request.getParameterMap();
		String get_params = DataBase.getParamsData(params);
		HelpBase HB = new HelpBase();
		String res = HB.getparam(get_params, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}
	private void getincomebal(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		Map<String, String[]> params = request.getParameterMap();
		String get_params = DataBase.getParamsData(params);
		HelpBase HB = new HelpBase();
		String res = HB.getincomebal(get_params, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}
	private void getpaybal(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		Map<String, String[]> params = request.getParameterMap();
		String get_params = DataBase.getParamsData(params);
		HelpBase HB = new HelpBase();
		String res = HB.getpaybal(get_params, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}
}