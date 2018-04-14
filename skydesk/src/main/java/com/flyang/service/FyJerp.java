package com.flyang.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flyang.tools.FlyangHttp;

import net.sf.json.JSONObject;
import tools.*;

/**
 * Servlet implementation class FlayService
 * 调用：所有进销存
 */
@WebServlet("/fyjerp")
public class FyJerp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FyJerp() {
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
		String timeout = request.getParameter("timeout");
		String hasSign = request.getParameter("hassign");
		String pictparam =  request.getParameter("pictparam");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		AppData appData = new AppData();
		appData.setqxpublic(request, response);
		HashMap<String, String> map = DataBase.getParamsMap(request.getParameterMap());
		map.remove("erpser");
		map.remove("timeout");
		map.remove("hassign");
		map.remove("pictparam");
		JSONObject dataobj = DataBase.getObjFrom(map);
		JSONObject resobj = new JSONObject();
		try {
			FlyangHttp fHttp = new FlyangHttp();
			fHttp.setHostIP(DataBase.JERPIP+"api?action=");
			if(timeout!=null){
				fHttp.setReadtimeout(Integer.parseInt(timeout));
			}
			if(hasSign!=null){
				fHttp.setHasSign(Boolean.parseBoolean(hasSign));
			}

			//获取Cookie
			String cookie = CookiesUtil.getCookieValue(request,"SKYSTR");
			if(null != cookie && !"".equals(cookie)){
				cookie = MD5Utils.getMD5(cookie);
			}

			if(pictparam!=null){
				JSONObject pictobj = JSONObject.fromObject(pictparam);
				@SuppressWarnings("rawtypes")
				Iterator it = pictobj.keys();
				while (it.hasNext()) {
					String key = it.next().toString();
					String value = DataBase.encode(pictobj.getString(key));
					pictobj.put(key, value);
				}
				resobj = fHttp.FlyangdoPost(action, dataobj, "&pict="+pictobj.toString(), appData, cookie);
			}else{
				resobj = fHttp.FlyangdoPost(action,dataobj,appData, cookie);
			}
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
