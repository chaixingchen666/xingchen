package tools;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/logservice")
public class LogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		String reqpar = request.getParameter("logser");
		if (reqpar.equals("getfiles")) {
			getlogfiles(request, response);
		}
		if (reqpar.equals("delallfiles")) {
			delallfiles(request, response);
		}
		if (reqpar.equals("writelog")) {
			writelog(request, response);
		}
	}

	private void writelog(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String epid = request.getParameter("epid");
		String msg = request.getParameter("msg");
		LogUtils.jsErrorLogWrite(epid, msg);
		PrintWriter out = response.getWriter();
		out.println("{\"msg\":\"日志已上报！\"}");
		out.close();
	}

	private void getlogfiles(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//servlet中获得项目绝对路径
		String projname = request.getContextPath();
		StringBuffer url = request.getRequestURL();  
		String domain = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
		String filePath = this.getServletConfig().getServletContext().getRealPath("/");
		String logPath = new File(filePath).getParent() + "/loginfo"+ projname +"log"; 
		File[] logfiles = new File(logPath).listFiles();
		String logurl = domain + "loginfo"+ projname +"log/"; //域名目录
		PrintWriter out = response.getWriter();
		out.println("<div>异常日志文件：</div>");
		out.println("<ul>");
		for(int i=0;i<logfiles.length;i++){
			if(logfiles[i].isFile()){//只寻找log的文件
				String filename = logfiles[i].getName();
				if(filename.contains(".log")){
					out.println("<li><a target=\"_blank\" href=\""+logurl+filename+"\">"+filename+"</a></li>");
				}
			}
		}
		out.println("</ul>");
		out.close();
	}
	private void delallfiles(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//servlet中获得项目绝对路径
		String projname = request.getContextPath();
		StringBuffer url = request.getRequestURL();  
		String domain = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
		System.out.println(domain);
		String filePath = this.getServletConfig().getServletContext().getRealPath("/");
		String logPath = new File(filePath).getParent() + "/loginfo"+ projname +"log"; 
		File[] logfiles = new File(logPath).listFiles();
		PrintWriter out = response.getWriter();
		for(int i=0;i<logfiles.length;i++){
			if(logfiles[i].isFile()){//只寻找log的文件
				String filename = logfiles[i].getName();
				if(filename.contains(".log")){
					logfiles[i].delete();
					out.println(filename+"</br>");
				}
			}
		}
		out.println("删除成功！");
		out.close();
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
