package com.flyang.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;

/**
 * Servlet implementation class FlayService
 * 没有用，调用.net
 */
@WebServlet("/flyangservice")
public class FlyService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FlyService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String reqpar = request.getParameter("flyangser");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		HttpSession session = request.getSession(true);
		AppData appData = new AppData();
		appData.setqxpublic(request, response, session);
		String action = "";
		if (reqpar.equals("getdevicelist")){ //获取设备列表
			action = "listdevice";
		}else if(reqpar.equals("powerdevice")){//授权设备
			action = "powerdevice";
		}else if(reqpar.equals("loadpayparam")){//获取支付参数
			action = "loadpayparam";
		}else if(reqpar.equals("savepayparam")){//保存支付参数
			action = "savepayparam";
		}else if(reqpar.equals("listlogrecord")){//获取操作日志
			action = "listlogrecord";
		}else if(reqpar.equals("listdealrecord")){//获取线上交易记录
			action = "listdealrecord";
		}else if(reqpar.equals("orderlock")){//订货会锁定
			action = "orderlock";
		}else if(reqpar.equals("warewavelist")){//商品波次
			action = "warewavelist";
		}else if(reqpar.equals("addwarewave")){//添加波次
			action = "addwarewave";
		}else if(reqpar.equals("updatewarewavebyid")){//编辑波次
			action = "updatewarewavebyid";
		}else if(reqpar.equals("getwarewavebyid")){//获取ID波次
			action = "getwarewavebyid";
		}else if(reqpar.equals("sendsms4")){//删除ID波次
			action = "sendsms4";
			String verificationCode = (String)session.getAttribute("verificationCode");
			String checkcode = request.getParameter("code");
			if(checkcode.toUpperCase().equals(verificationCode.toUpperCase())){
				postSmSRequest(action,request, response, appData);
			}else{
				PrintWriter out = response.getWriter();
				out.print("{\"result\":\"0\",\"msg\":\"识别码不正确！\"}");
				out.close();
			}
			return;
		}else if(reqpar.equals("changeomsubject")){//修改订货会状态
			action = "changeomsubject";
		}
		postRequest(action,request, response, appData);
	}

	private void postRequest(String action, HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 获取权限参数
		HashMap<String, String> map = DataBase.getParamsMap(request.getParameterMap());
		map.remove("flyangser");
		JSONObject dataobj = DataBase.getObjFrom(map);
		String res = "";
		if(action.equals("")){
			res = "{\"result\":\"0\",\"msg\":\"获取ServerAction为空！\"}";
		}else{
			res = DataBase.postRequest(action,dataobj,appData);
		}
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}
	private void postSmSRequest(String action, HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 获取权限参数
		HashMap<String, String> map = DataBase.getParamsMap(request.getParameterMap());
		map.remove("flyangser");
		JSONObject dataobj = DataBase.getObjFrom(map);
		String res = "";
		if(action.equals("")){
			res = "{\"result\":\"0\",\"msg\":\"获取ServerAction为空！\"}";
		}else{
			res = DataBase.postSmSRequest(action,dataobj,appData);
		}
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
