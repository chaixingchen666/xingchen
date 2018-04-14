package mains;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;
import tools.ReaderServer;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
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
		String reqpar = request.getParameter("mainser");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		HttpSession session = request.getSession(true);
		AppData appData = new AppData();
		appData.setqxpublic(request, response, session);
		if (reqpar.equals("getqxpublic")) {
			getqxpublic(request, response, appData);
		} else if (reqpar.equals("writeqxpublic")) {
			writeqxpublic(request, response, appData);
		} else if (reqpar.equals("getfirstpage")) {
			getfirstpage(request, response, appData);
		} else if (reqpar.equals("addfirstpage")) {
			addfirstpage(request, response, appData);
		} else if (reqpar.equals("delfirstpage")) {
			delfirstpage(request, response, appData);
		} else if (reqpar.equals("getcominfo")) {
			getcominfo(request, response, appData);
		} else if (reqpar.equals("updatecominfo")) {
			updatecominfo(request, response, appData);
		} else if (reqpar.equals("changebegindate")) {
			changebegindate(request, response, appData);
		} else if (reqpar.equals("getsysnotice")) {
			getsysnotice(request, response, appData);
		} else if (reqpar.equals("setsysnotice")) {
			setsysnotice(request, response, appData);
		} else if (reqpar.equals("getmsg")) {
			getmsg(request, response, appData);
		} else if (reqpar.equals("devicevalid")) {
			try {
				devicevalid(request, response, appData);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (reqpar.equals("sendsuggest")) {
			sendsuggest(request, response, appData);
		} else if (reqpar.equals("changeaccstg")) {
			changeaccstg(request, response, appData);
		} else if (reqpar.equals("getdefaultcust")) {
			getdefaultcust(request, response, appData);
		} else if (reqpar.equals("setdefaultcust")) {
			setdefaultcust(request, response, appData);
		} else if (reqpar.equals("writedefaultsize")) {
			writedefaultsize(request, response, appData);
		} else if (reqpar.equals("writedefaultcolor")) {
			writedefaultcolor(request, response, appData);
		} else if (reqpar.equals("getsjwh")) {
			getsjwh(request, response, appData);
		} else if (reqpar.equals("getmoban")) {
			getmoban(request, response, appData);
		} else if (reqpar.equals("accmoneylist")) {
			accmoneylist(request, response, appData);
		} else if (reqpar.equals("moveaccmoney")) {
			moveaccmoney(request, response, appData);
		} else if (reqpar.equals("listprognamebyuserid")) {
			listprognamebyuserid(request, response, appData);
		} else if (reqpar.equals("setpaydevice")) {
			setpaydevice(request, response, appData);
		} else if (reqpar.equals("accnamevalid")) {
			accnamevalid(request, response, appData);
		} else if (reqpar.equals("addaccreg")) {
			addaccreg(request, response, appData);
		} else if (reqpar.equals("getip")) {
			getip(request, response, appData);
		} else if (reqpar.equals("getversion")) {
			PrintWriter out = response.getWriter();
			out.print("版本号："+DataBase.VERSION);
			out.close();
		}
	}
	private void listprognamebyuserid(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		String params  = DataBase.getParamsData(request.getParameterMap());
		String res = MainBase.listprognamebyuserid(params,appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}
	private void moveaccmoney(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		String toaccname = request.getParameter("toaccname");
		String curr = request.getParameter("curr");
		String res = MainBase.moveaccmoney(toaccname,curr, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void accmoneylist(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		String fs = request.getParameter("fs");
		String page = request.getParameter("page");
		MainBase.page = page;
		String res = MainBase.accmoneylist(fs, appData);
		MainBase.page = "1";
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void changebegindate(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		String date = request.getParameter("date");
		String res = MainBase.changebegindate(date, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}
	private void accnamevalid(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		String accname = request.getParameter("accname");
		String res = MainBase.accnamevalid(accname, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void addaccreg(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		String accname = request.getParameter("accname");
		String smspwd = request.getParameter("smspwd");
		String mobile = request.getParameter("mobile");
		String password = request.getParameter("password");
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("accname", accname);
		params.put("mobile", mobile);
		params.put("password", password.toUpperCase());
		String res = MainBase.addaccreg(smspwd,params, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
		
	}
	private void updatecominfo(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		String company = request.getParameter("company");
		String linkman = request.getParameter("linkman");
		String tel = request.getParameter("tel");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("company", company);
		params.put("linkman", linkman);
		params.put("tel", tel);
		params.put("address", address);
		params.put("email", email);
		String res = MainBase.updatecominfo(params, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
				
	}

	private void getmoban(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		// 这是一个下载的servlet
		// 先得到你要下的文件路径，从页面传递过来的
		String moban = request.getParameter("moban");
		String pa = "skydesk/xlsmoban/";
		String path = ReaderServer.getWebPath() + DataBase.getfilestr(pa);
		switch (moban) {
		case "warecode":
			path += "商品编码.xls";
			break;
		case "warecolor":
			path += "商品颜色.xls";
			break;
		case "firsthouse":
			path += "期初入库.xls";
			break;
		case "warein":
			path += "采购入库.xls";
			break;
		case "allotout":
			path += "调拨出库.xls";
			break;
		case "custcode":
			path += "客户档案.xls";
			break;
		case "employe":
			path += "职员档案.xls";
			break;
		case "provide":
			path += "供应商档案.xls";
			break;
		case "colorcode":
			path += "颜色编码.xls";
			break;
		case "charges":
			path += "费用项目.xls";
			break;
        case "vipinfo":
			path += "会员档案.xls";
			break;
         case "warebarcode":
        	path += "条码管理.xls";
        	break;
         case "brandcode":
         	path += "品牌编码.xls";
         	break;
         case "vipcurr":
        	 path += "会员积分和余额.xls";
        	 break;
         case "firstpaycurr":
        	 path += "期初应付.xls";
        	 break;
         case "firstincomecurr":
        	 path += "期初应收.xls";
        	 break;
         case "tempcheckmxls":
        	 path += "临时盘点单.xls";
        	 break;
         case "tempcheckmtxt":
        	 path += "临时盘点单.txt";
        	 break;
         case "omuser":
        	 path += "订货成员账号.xls";
        	 break;
         case "warewarn":
        	 path += "商品库存预警设置.xls";
        	 break;
         case "custorder":
        	 path += "客户订单.xls";
        	 break;
         case "provorder":
        	 path += "采购订单.xls";
        	 break;
         case "wareprice":
        	 path += "商品调价.xls";
        	 break;
         case "custsale":
        	 path += "客户销售.xls";
        	 break;
         case "waresaleh":
        	 path += "店铺零售.xls";
        	 break;
         case "areacode":
        	 path += "店铺区位.xls";
        	 break;
         case "salepromware":
        	 path += "促销商品.xls";
        	 break;
		default:
			break;
		}
		if (!path.equals("")) {
			File file = new File(path);// 得到要下载的文件
			InputStream is = new FileInputStream(file);// 创建一个输入流
			String fileName = file.getName();
			int fileSize = (int) file.length();
			// 下面就是一些固定步骤，完成页面弹框下载的功能
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("utf-8");
			if(fileName.contains(".xls"))
				response.setContentType("application/vnd.ms-excel");  
			else if(fileName.contains(".txt"))
				response.setContentType("text/plain");  
			String str = null;
			try {
				str = "attachment;filename=\"" + DataBase.getStr(request, fileName)+"\"";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setHeader("Content-Disposition", str);
			response.setContentLength(fileSize);
			ServletOutputStream sos = response.getOutputStream();
			byte[] data = new byte[2048];
			int len = 0;
			while ((len = is.read(data)) > 0) {
				sos.write(data, 0, len);
			}
			is.close();
			sos.close();
		}
	}

	

	private void setpaydevice(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		JSONObject dataobj = DataBase.getObjFrom(request.getParameterMap());
		String action = "setdevice";
		String res = DataBase.postRequest(action,dataobj,appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}
	private void getsjwh(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		String date = request.getParameter("date");
		String res = MainBase.getsjwh(date, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void writedefaultcolor(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 设置默认颜色
		String colorid = request.getParameter("colorid");
		String res = MainBase.setdefaultcolor(colorid, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void writedefaultsize(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 设置默认尺码组
		String groupno = request.getParameter("groupno");
		String res = MainBase.setdefaultsize(groupno, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void getmsg(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 获取消息状态
		String data = MainBase.getmsg(appData);
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	private void setdefaultcust(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 设置默认客户
		String custid = request.getParameter("custid");
		String res = MainBase.setdefaultcust(custid, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void getdefaultcust(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 获取默认客户
		String res = MainBase.getdefaultcust(appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void changeaccstg(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 修改账户禁用
		String stg = request.getParameter("stg");
		String res = MainBase.setaccstg(stg, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void setsysnotice(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 设置系统公告
		String ggid = request.getParameter("ggid");
		String res = MainBase.setsysnotice(ggid, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void getsysnotice(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 获取系统公告
		String page = request.getParameter("page");
		MainBase.page = page;
		String data = MainBase.getsysnotice(appData);
		MainBase.page = "1";
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	private void sendsuggest(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 用户反馈
		String suggest = request.getParameter("suggest");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("content", suggest);
		String res = MainBase.sendsuggest(params, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void getcominfo(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 获取账户信息
		String data = MainBase.getcominfo(appData);
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	private void writeqxpublic(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		if (appData.levelid.equals("0")) {
			String location = request.getParameter("location");
			String value = request.getParameter("value");
			out.print(MainBase.writeqxpublic(location, value, appData));
		} else {
			out.print("f");
		}
		out.close();
	}

	private void getqxpublic(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 获取权限参数
		String data = MainBase.getqxpublic(appData);
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	private void getfirstpage(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 获取首页
		String data = MainBase.getfirstpage(appData);
		PrintWriter out = response.getWriter();
		out.print(data);
		out.close();
	}

	private void addfirstpage(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 获取权限首页
		String progid = request.getParameter("progid");
		String res = MainBase.addfirstpage(progid, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void delfirstpage(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 获取权限首页
		String progid = request.getParameter("progid");
		String res = MainBase.delfirstpage(progid, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	private void devicevalid(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException, NoSuchAlgorithmException {
		// TODO 检验设备
		String progid = request.getParameter("progid");
		//String winip = request.getRemoteAddr();// 获取客户端的ip
		String res = MainBase.devicevalid(progid,appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}
	private void getip(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException{
		// TODO 检验设备
		String winip = request.getRemoteAddr();// 获取客户端的ip
//		JSONObject json = new JSONObject();
//		json.put("IP", value)
		PrintWriter out = response.getWriter();
		out.print(winip);
		out.close();
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response, AppData appData)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
