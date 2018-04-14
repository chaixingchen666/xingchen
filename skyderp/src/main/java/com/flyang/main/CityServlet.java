package com.flyang.main;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.tool.Func;
import com.comdot.data.Table;
import com.common.tool.*;
import com.common.tool.TwoBarCode;
import com.oracle.bean.*;
import com.netdata.db.*;

/**
 * 标准接口模板文件
 */
@WebServlet("/city")
public class CityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String filePath = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CityServlet() {
		super();
		// TODO Auto-generated constructor stub
		filePath = Func.getRootPath();// +"skyderp.config/";
		System.out.println("CityServlet is ready ok! " + filePath);
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

		String action = request.getParameter("action");
		// filePath=Func.getRootPath();
		if (action.equals("path")) {
			response.getWriter().write(filePath + "  ---city  ");
			return;
		} else

		if (action.equals("serverdate")) {
			ServerDateTime(response);
			return;
		}

		HttpInfo htp1 = new HttpInfo();
		htp1.setAction(action);// 设action

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
		if (htp1.setSignjson(signjson, 0) == 0)// 校验参数是否有效
		{
			Write(response, htp1.getErrmess());
			return;

		}

		String onlogaction = "^serverdate^serverdatetime^path^test^"; // 不记录日志的接口名
		if (!onlogaction.contains("^" + action + "^")) {
			// 接口调用记录
			LogUtils.LogWrite(htp1.getUsername() + Func.getIpAddress(request), htp1.getUrl());
		}
		String ss = Func.HttpIsDanger(htp1.getUrl());
		if (!ss.equals("")) {

			// 有危险操作的请求不允许通过
			LogUtils.LogWrite(htp1.getUsername() + "【 " + ss.trim() + "】" + Func.getIpAddress(request), htp1.getUrl(), "warn");
			WriteResult(response, 0, "警告：请求中含有危险操作命令！");
			return;

		}
		//System.out.println("action=" + action);
		try {
			switch (action) {
			case "test":
				// Test(response, htp1);
				break;

			default:
				WriteResult(response, 0, "ERP接口未定义:" + action + "!");
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
