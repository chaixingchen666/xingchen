package com.flyang.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.flyang.exports.ExportBase;

import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;

/**
 * Servlet implementation class ExportServlet
 * 导出
 */
@WebServlet("/exportservice")
public class ExportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportServlet() {
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
		}else if(reqpar.equals("listlogrecord")){//获取操作日志
			action = "listlogrecord";
		}else if(reqpar.equals("listdealrecord")){//获取线上交易记录
			action = "listdealrecord";
		}
		if(!action.equals(""))
			ExportXls(action, request, response, appData);
	}
	private void ExportXls(String action, HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 获取权限参数
		String colstr = request.getParameter("colsobjs");
		String footerstr = request.getParameter("footer");
		HashMap<String, String> map = DataBase.getParamsMap(request.getParameterMap());
		map.remove("flyangser");
		map.remove("colsobjs");
		map.remove("footer");
		JSONObject dataobj = DataBase.getObjFrom(map);
		response.reset();//可以加也可以不加  
		response.setContentType("multipart/form-data");
		String filename = ".xls";
		switch (action) {
		case "listdealrecord":
			filename = "交易记录查询" + filename;
			break;
		default:
			break;
		}
		// 3.设置content-disposition响应头控制浏览器以下载的形式打开文件
		response.setHeader("Content-Disposition", "attachment;filename=\"" + DataBase.getStr(request, filename)+"\"");
		OutputStream out = response.getOutputStream();
		try{
			ExportBase eBase = new ExportBase();
			eBase.setAction(action);
			eBase.json2excel(out,dataobj,filename,colstr,footerstr,appData);
		}finally{
			out.flush();
 			out.close();
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
