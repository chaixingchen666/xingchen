package com.flyang.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flyang.tools.FlyangHttp;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import tools.AppData;
import tools.DataBase;
import tools.LogUtils;

/**
 * Servlet implementation class SkyService
 * 没有用，以前.net
 */
@WebServlet("/skyservice")
public class SkyService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SkyService() {
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
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		AppData appData = new AppData();
		appData.setqxpublic(request, response);
		String action = request.getParameter("skyser");
		String getjsonstr = request.getParameter("getjsonstr");
		String postjsonstr = request.getParameter("postjsonstr");
		JSONObject getobj = new JSONObject();
		JSONObject postobj = new JSONObject();
		JSONObject resobj = new JSONObject();
		try {
			if(getjsonstr!=null){
				if(JSONUtils.mayBeJSON(getjsonstr)&&!getjsonstr.equals("")){
					getobj = JSONObject.fromObject(getjsonstr);
				}
			}
			if(postjsonstr!=null){
				if(JSONUtils.mayBeJSON(postjsonstr)&&!postjsonstr.equals("")){
					postobj = JSONObject.fromObject(postjsonstr);
				}
			}
			FlyangHttp fHttp = new FlyangHttp();
			fHttp.setHostIP(DataBase.HostIP2);
			fHttp.setHasSign(true);
			if(action.equals("updatewarecheckh"))
				fHttp.setReadtimeout(30*60*1000);
			resobj = fHttp.FlyangdoPost(action,getobj,postobj,appData);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.LogWrite("ErorRequestData", getjsonstr+"\n"+postjsonstr);
			resobj.put("result", 0);
			resobj.put("msg", "请求数据格式不规范！(E005)");
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
