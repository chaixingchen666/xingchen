package mains.receptionsales.changeclassrecord;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

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
 * Servlet implementation class ChangeClassRecordServlet
 */
@WebServlet("/skymain/ReceptionSales/ChangeClassRecordServlet")
public class ChangeClassRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeClassRecordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO 对数据请求作出判断
				request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
				response.setContentType("text/html; charset=utf-8");
				response.setHeader("Pragma","No-cache"); 
				response.setHeader("Cache-Control","no-cache"); 
				response.setDateHeader("Expires", 0); 
                AppData appData=new AppData();
				HttpSession session = request.getSession(true);
				appData.setqxpublic(request, response, session);
				
				String reqpar = request.getParameter("changeclassrecordser");
				
				if(reqpar.equals("getchangeclassrecord")){//获得交班记录分页数据
					getchangeclassrecord(request, response,appData);
				}
				if(reqpar.equals("changeclassexport")){//导出
					changeclassexport(request, response,appData);
				}
	}
	
	private void getchangeclassrecord(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// 查询交班记录
		String mindate=request.getParameter("mindate");//开始日期
		String maxdate=request.getParameter("maxdate");//结束日期
		String houseid=request.getParameter("houseid");//店铺id
		String page=request.getParameter("page");//店铺id
		
		String res = ChangeClassRecordBase.getchangeclassrecord(appData,houseid,maxdate,mindate,page);
		JSONObject jsono= JSONObject.fromObject(res);
		String total=jsono.getString("total");//根据^分割要\\^
		total=total.replace("^", "-");
		String[] ss = total.split("-");
		//res=res.substring(0,res.length()-1)+",\"footer\":[{\"BEGINDATE\":\"合计\",\"CURRXS\":\""+ss[1]+"\",\"CURRYH\":\""+ss[3]+"\",\"CURRYK\":\""+ss[5]+"\"}]}";
		res=res.substring(0,res.length()-1)+",\"footer\":[{\"BEGINDATE\":\"您所查询时段内共产生\",\"CURRJC\":\""+ss[1]+"\",\"ENDDATE\":\"销售额总计\"},{\"ENDDATE\":\"存款总计\",\"CURRJC\":\""+ss[2]+"\",\"CURRFY\":\""+ss[3]+"笔\"},{\"ENDDATE\":\"损益合计\",\"CURRJC\":\""+ss[4]+"\",\"CURRFY\":\""+ss[5]+"笔\"}]}";
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
		
	}
	private void changeclassexport(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// 导出
		String mindate=request.getParameter("mindate");//开始日期
		String maxdate=request.getParameter("maxdate");//结束日期
		String houseid=request.getParameter("houseid");//店铺id
		String page=request.getParameter("page");//店铺id
		
		String res = ChangeClassRecordBase.data2excel(appData,houseid,maxdate,mindate,page);
		
		res="{\"total\":28,\"rows\":"+res+"}";//导出用的res
		response.reset();//可以加也可以不加  
		response.setContentType("multipart/form-data");
		String filename = "交班记录.xls";
		// 3.设置content-disposition响应头控制浏览器以下载的形式打开文件
		response.setHeader("Content-Disposition", "attachment;filename=\"" + DataBase.getStr(request, filename)+"\"");
		OutputStream out = response.getOutputStream();
		try{
			ChangeClassRecordBase.changeclassexport(out, appData,res);
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
