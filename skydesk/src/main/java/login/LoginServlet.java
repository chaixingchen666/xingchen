package login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tools.AppData;
import tools.DataBase;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/loginservlet")
public class LoginServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
	    response.setContentType("text/html; charset=utf-8");
	    response.setHeader("Pragma","No-cache"); 
		response.setHeader("Cache-Control","no-cache"); 
		response.setDateHeader("Expires", 0); 
		AppData appData = new AppData();
		if (request.getParameter("doservlet").equals("login")) {
				login(request, response,appData);
		}
		if (request.getParameter("doservlet").equals("logindemo")) {
			logindemo(request, response,appData);
		}
		if (request.getParameter("doservlet").equals("idconfirm")) {
			idconfirm(request, response,appData);
		}
		if (request.getParameter("doservlet").equals("cleardata")) {
			clearcookie(request, response,appData);
		}
		
		if (request.getParameter("doservlet").equals("logoff")) {
			logoff(request, response,appData);
		}

	}
	
	private void clearcookie(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 清除Cookie
		System.out.println("2");
		HttpSession session = request.getSession();
		session.invalidate();
		appData.clearCookie(request, response);
		response.sendRedirect("login.jsp");
	}
	private void logoff(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 注销账户
		HttpSession session = request.getSession();
		session.invalidate();
		DataBase.resetCoolkie(response);
		response.sendRedirect("login.jsp");
	}

	private void idconfirm(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO Auto-generated method stub
		String comid = request.getParameter("comid");
		String res = DataBase.idconfirm(comid,request,response,appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}

	/**
	 * @throws Exception 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	private void login(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		String userid = request.getParameter("userid");
		String comid = request.getParameter("comid");
		String userpsw = request.getParameter("userpsw");
		String winip = request.getRemoteAddr();//获取客户端的ip
		HttpSession session = request.getSession(true);
		String accid=request.getParameter("accid");
		String password = userpsw;
		String res = DataBase.login(accid, userid, password,comid,winip,request,response,session,appData);// 请求验证登录的用户代号，并且返回是否正确和失败
		PrintWriter out = response.getWriter();
		if(accid.equals("")){
			out.print("idconfirm");
		}else{
				out.print(res);
		}
		out.close();
//		Locale languageType=request.getLocale();//获取用户语言
//		String localIp=request.getLocalAddr();//获取本地ip
//		int localPort=request.getLocalPort();//获取本地的端口
//		String localName=request.getLocalName();//获取本地计算机的名字
//		String remoteIp=request.getRemoteAddr();//获取客户端的ip
//		int remotePort=request.getRemotePort();//获取客户端的端口号
//		String serverName=request.getRemoteHost();//获取远程计算机的名字
//		if(DataBase.isCS)System.out.println("语言类型->"+languageType);
//		if(DataBase.isCS)System.out.println(localName+" "+serverName);
//		if(DataBase.isCS)System.out.println(localIp+":"+localPort+" "+remoteIp+":"+remotePort);
//		String requestURL=request.getRequestURL().toString();//获取除参数之外的地址信息
//		String requestURI=request.getRequestURI();
//		String queryString=request.getQueryString();
//		if(DataBase.isCS)System.out.println("请求的地址->"+requestURL);
//		if(DataBase.isCS)System.out.println("请求的地址1->"+requestURI);
//		if(DataBase.isCS)System.out.println("请求的地址后的信息->"+queryString);
	}
	private void logindemo(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		String userid = "DEMO";
		String comid = "SYSDEMO";
		String userpsw = "2015301";
		String winip = request.getRemoteAddr();//获取客户端的ip
		HttpSession session = request.getSession(true);
		String accid= "1000";
		String password = userpsw;
		String res = DataBase.login(accid, userid, password,comid,winip,request,response,session,appData);// 请求验证登录的用户代号，并且返回是否正确和失败
		PrintWriter out = response.getWriter();
		if(accid.equals("")){
			out.print("idconfirm");
		}else{
			out.print(res);
		}
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
