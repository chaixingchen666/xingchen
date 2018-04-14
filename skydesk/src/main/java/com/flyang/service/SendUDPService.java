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

/**
 * Servlet implementation class SendUDPService
 * 调用：后台打印
 */
@WebServlet("/sendudp")
public class SendUDPService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendUDPService() {
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
		String prtserip = request.getParameter("prtserip");
		String fyprtstr = request.getParameter("fyprtstr");
		JSONObject resobj = new JSONObject();
		try {
			FlyangHttp fh = new FlyangHttp();
			resobj = fh.sendUDP(prtserip,fyprtstr);
			if(resobj.size()==0) {
				resobj.put("result", 0);
				resobj.put("msg", "发送失败！服务返回失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
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
