package com.flyang.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flyang.tools.FlyangHttp;

import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;
import tools.DownloadUtils;
import tools.LogUtils;

/**
 * Servlet implementation class ExportXlsServlet
 * 导出xls，调用
 */
@WebServlet("/fyexprotxls")
public class ExportXlsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportXlsServlet() {
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
//		response.setContentType("multipart/form-data");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");  
		AppData appData = new AppData();
		appData.setqxpublic(request, response);
		String action = request.getParameter("erpser");
		String serverid = request.getParameter("serverid");
		String filename = request.getParameter("filename");
		response.setHeader("Content-Disposition", "attachment;filename=\"" + DataBase.getStr(request, filename)+"\""); // filename参数指定下载的文件名
		HashMap<String, String> map = DataBase.getParamsMap(request.getParameterMap());
		map.remove("erpser");
		map.remove("filename");
		map.remove("serverid");
		map.put("page", "-1");
		JSONObject dataobj = DataBase.getObjFrom(map);
		JSONObject resobj = new JSONObject();
		try {
			FlyangHttp fHttp = new FlyangHttp();
			fHttp.setReadtimeout(10*60*1000);//设置读取时长
			if(serverid.equals("api")) fHttp.setHostIP(DataBase.JERPIP+"api?action=");
			else if(serverid.equals("ords")) fHttp.setHostIP(DataBase.JERPIP+"ords?action=");
			else if(serverid.equals("buy")) fHttp.setHostIP(DataBase.JERPIP+"buy?action=");
			else if(serverid.equals("sxy")) fHttp.setHostIP(DataBase.JERPIP+"sxy?action=");
			else return;
			LogUtils.LogExportWrite(appData.comid+"在导"+action+"数据:", dataobj.toString());
			resobj = fHttp.FlyangdoPost(action,dataobj,appData);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.LogWrite("ErorRequestData2", dataobj.toString());
			resobj.put("result", 0);
			resobj.put("msg", "请求返回数据格式异常！(E006)");
		}
		if(appData.isResult(resobj.toString())){
			String url = DataBase.JERPIP.replace("/skyderp","")+resobj.getString("msg");
			InputStream in = new DownloadUtils().getUrlFileStream(url);
			// 6.通过response对象获取OutputStream流
			OutputStream out = response.getOutputStream();
			try {
				int len = 0;
				// 5.创建数据缓冲区
				byte[] buffer = new byte[1024];
				// 7.将FileInputStream流写入到buffer缓冲区
				while ((len = in.read(buffer)) > 0) {
					// 8.使用OutputStream将缓冲区的数据输出到客户端浏览器
					out.write(buffer, 0, len);
				}
				out.flush();
				// 要加以下两句话，否则会报错
				// java.lang.IllegalStateException: getOutputStream() has already
				// been called for //this response
			} finally {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			}
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
