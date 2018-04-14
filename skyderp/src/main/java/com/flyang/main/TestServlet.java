package com.flyang.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/test")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");

//		String url = request.getRequestURL() + "?" + request.getQueryString();

		String action = request.getParameter("action");
		String datajson = request.getParameter("data"); // 取sign参数
	
		if (action.equals("dbconn")) {
			DbConntest(response,datajson);
			return;
		} else 
		{
			WriteResult(response, 0, "test接口未定义:" + action + "!");
			
		}
	}


	// 数据库连接测试
	protected void DbConntest(HttpServletResponse response,String datajson) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(datajson);
		String driver = jsonObject.has("driver") ? jsonObject.getString("driver") : "";
		String url = jsonObject.has("url") ? jsonObject.getString("url") : "";
		String user = jsonObject.has("user") ? jsonObject.getString("user") : "";
		String password = jsonObject.has("password") ? jsonObject.getString("password") : "";

		Connection con = null;// 创建一个数据库连接
		ResultSet result = null;// 创建一个结果集对象
		try {
			Class.forName(driver);// 加载Oracle驱动程序
			con = DriverManager.getConnection(url, user, password);// 获取连接
			WriteResult(response, 1, "ok！");
		} catch (Exception e) {
			e.printStackTrace();
			WriteResult(response, 0, "err！");
			// return '0连接失败！';
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (result != null)
					result.close();
				if (con != null)
					con.close();
				// System.out.println("数据库连接已关闭！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void WriteResult(HttpServletResponse response, int result, String mess) throws ServletException, IOException {
		String jsonstr = "{\"result\":" + result + ",\"msg\":\"" + mess + "\"}";
		response.getWriter().write(jsonstr);
	}

}
