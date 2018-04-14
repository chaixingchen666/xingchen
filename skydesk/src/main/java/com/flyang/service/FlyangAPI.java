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

import com.flyang.tools.FlyangHttp;

import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;
import tools.LogUtils;

/**
 * Servlet implementation class FlayService
 * 没有用，以前调用.net
 */
@WebServlet("/flyangerp")
public class FlyangAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FlyangAPI() {
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
		String action = request.getParameter("erpser");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		HttpSession session = request.getSession(true);
		AppData appData = new AppData();
		appData.setqxpublic(request, response, session);
		HashMap<String, String> map = DataBase.getParamsMap(request.getParameterMap());
		map.remove("erpser");
		JSONObject dataobj = DataBase.getObjFrom(map);
		JSONObject resobj = new JSONObject();
		try {
			FlyangHttp fHttp = new FlyangHttp();
			fHttp.setHostIP(DataBase.HostIP2);
			resobj = fHttp.FlyangdoPost(action,dataobj,appData);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
