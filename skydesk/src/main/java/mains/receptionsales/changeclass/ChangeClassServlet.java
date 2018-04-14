package mains.receptionsales.changeclass;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;

/**
 * Servlet implementation class ChangeClassServlet
 */
@WebServlet("/skymain/ReceptionSales/ChangeClassServlet")
public class ChangeClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeClassServlet() {
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
				appData.setqxpublic(request,response, session);
				
				String reqpar = request.getParameter("changeclassser");
				if(reqpar.equals("houseworktotal")){//店铺收银员交班统计
					gethouseworktotal(request,response,appData);
				}
				
				if(reqpar.equals("houseworkopen")){//开班
					gethouseworkopen(request,response,appData);
				}
				
				if(reqpar.equals("opclass")){//判断是否开班
					getopclass(request,response,appData);
				}
				if(reqpar.equals("houseworkcancel")){//撤班
					houseworkcancel(request,response,appData);
				}
				if(reqpar.equals("houseworkclose")){//交班
					houseworkclose(request,response,appData);
				}
				if(reqpar.equals("houseworktotalexp")){//导出
					houseworktotalexp(request, response,appData);
				}
	}
	private void houseworktotalexp(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// 导出交班记录
		String classid=request.getParameter("classid");//班次id		
		String houseid=request.getParameter("houseid");//店铺id
		appData.showfooter=new String(request.getParameter("housename").getBytes("iso-8859-1"),"UTF-8");//店铺id
		if(DataBase.isCS)System.out.println(appData.showfooter);
		String res = ChangeClassBase.gethouseworktotal(appData,classid,houseid);
		
		response.reset();//可以加也可以不加  
		response.setContentType("multipart/form-data");
		String filename = "详细交班记录.xls";
		// 3.设置content-disposition响应头控制浏览器以下载的形式打开文件
		response.setHeader("Content-Disposition", "attachment;filename=\"" + DataBase.getStr(request, filename)+"\"");
		OutputStream out = response.getOutputStream();
		try{
			ChangeClassBase.houseworktotalexp(out, appData,res);
		}finally{
			out.flush();
 			out.close();
		}
		
	}


	private void getopclass(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// 是否开班
		String houseid=request.getParameter("houseids");//店铺id
		String res = ChangeClassBase.getopclass(appData,houseid);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
		
	}
	private void gethouseworktotal(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// 获取店铺收银员交班统计
		String classid=request.getParameter("classid");//班次id		
		String houseid=request.getParameter("houseids");//店铺id
		String res = ChangeClassBase.gethouseworktotal(appData,classid,houseid);
		int AMOUNT=0;
		Double CURR=0.0;
		Double CURR0=0.0;
		Double CURR1=0.0;
		Double CURR2=0.0;
		Double PAYCURR0=0.0;
		Double PAYCURR1=0.0;
		Double PAYCURR2=0.0;
		JSONObject jsonObj= JSONObject.fromObject(res);
		JSONArray jsonArray0=jsonObj.getJSONArray("XSLIST");
		JSONArray jsonArray1=jsonObj.getJSONArray("FYLIST");
		JSONArray jsonArray2=jsonObj.getJSONArray("PAYCURRLIST");
		String XSLIST= jsonObj.getString("XSLIST");
		String FYLIST= jsonObj.getString("FYLIST");
		String PAYCURRLIST= jsonObj.getString("PAYCURRLIST");
		
		String MINDATE=jsonObj.getString("MINDATE");
		String MAXDATE=jsonObj.getString("MAXDATE");
		String LASTOP=jsonObj.getString("LASTOP");
		String CURRQC=jsonObj.getString("CURRQC");
		String CURRYE=jsonObj.getString("CURRYE");
		String CURRSJYE=jsonObj.getString("CURRSJYE");
		String CURRYK=jsonObj.getString("CURRYK");
		String CURRYH=jsonObj.getString("CURRYH");
		String CURRJC=jsonObj.getString("CURRJC");
		String CURRXJ=jsonObj.getString("CURRXJ");
		String REMARK=jsonObj.getString("REMARK");
		for(int i=0;i<jsonArray0.size();i++){
			JSONObject s=jsonArray0.getJSONObject(i);
			AMOUNT+=Double.parseDouble(s.getString("AMOUNT"));
			CURR+=Double.parseDouble(s.getString("CURR"));
		}
		for(int i=0;i<jsonArray1.size();i++){
			JSONObject s=jsonArray1.getJSONObject(i);
			CURR0+=Double.parseDouble(s.getString("CURR0"));
			CURR1+=Double.parseDouble(s.getString("CURR1"));
			CURR2+=Double.parseDouble(s.getString("CURR2"));
		}
		for(int i=0;i<jsonArray2.size();i++){
			JSONObject s=jsonArray2.getJSONObject(i);
			PAYCURR0+=Double.parseDouble(s.getString("PAYCURR0"));
			PAYCURR1+=Double.parseDouble(s.getString("PAYCURR1"));
			PAYCURR2+=Double.parseDouble(s.getString("PAYCURR2"));
		}
		appData.showfooter="{\"MINDATE\":\""+MINDATE+"\",\"MAXDATE\":\""+MAXDATE+"\",\"LASTOP\":\""+LASTOP+"\",\"CURRQC\":\""+CURRQC+"\",\"REMARK\":\""+REMARK+"\",\"CURRYE\":"
				+"\""+CURRYE+"\",\"CURRSJYE\":"+"\""+CURRSJYE+"\",\"CURRYH\":"+"\""+CURRYH+"\",\"CURRJC\":"+"\""+CURRJC
				+"\",\"CURRYK\":"+"\""+CURRYK+"\""+",\"CURRXJ\":"+"\""+CURRXJ+"\"";
		FYLIST=",\"FYLIST\":"+FYLIST+",\""+"FYLIST1"+"\""+":["+"{\"CURR0\":\""+CURR0+"\",\"CURR1\":"+"\""+CURR1+"\",\"CURR2\":"+"\""+CURR2+"\",\"CGNAME\":"+"\"小计\"}]";
		PAYCURRLIST=",\"PAYCURRLIST\":"+PAYCURRLIST+",\""+"PAYCURRLIST1"+"\""+":["+"{\"PAYCURR0\":\""+PAYCURR0+"\",\"PAYCURR1\":"+"\""+PAYCURR1+"\",\"PAYCURR2\":"+"\""+PAYCURR2+"\",\"PAYNAME\":"+"\"小计\"}]";
		XSLIST=",\"XSLIST\":"+XSLIST+",\""+"XSLIST1"+"\""+":["+"{\"CURR\":\""+CURR+"\",\"AMOUNT\":"+"\""+AMOUNT+"\",\"WARENO\":"+"\"小计\"}]";
		appData.showfooter=appData.showfooter+XSLIST+FYLIST+PAYCURRLIST+"}";
		//当没有数据时测试用
		//appData.showfooter=appData.showfooter.replace("[]", "[{\"WAREID\":\"2\",\"WARENAME\":\"大衣\",\"WARENO\":\"3002\",\"UNITS\":\"件\",\"AMOUNT\":\"8\",\"CURR\":\"6799.90\"}]");
		PrintWriter out = response.getWriter();
		out.print(appData.showfooter);
		out.close();
		
	}
	
	private void gethouseworkopen(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// 开班currqc:
		String begindate=request.getParameter("begindate");//开班时间
		String houseid=request.getParameter("houseid");//店铺id
		String currqc=request.getParameter("currqcs");//
		begindate=begindate.replace(" ", "");
		begindate=begindate.replace("-", "");
		begindate=begindate.replace(":", "");
		
		
		
		String res = ChangeClassBase.gethouseworkopen(appData,houseid,begindate,currqc);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
		
	}
	private void houseworkcancel(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// 撤班
		String classid=request.getParameter("classid");//撤班
		
		
		String res = ChangeClassBase.houseworkcancel(appData,classid);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
		
	}
	private void houseworkclose(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// 交班
		String currxj=request.getParameter("currxj");//现金收入
		String Currqc=request.getParameter("Currqc");//上班结存
		String CURRYE=request.getParameter("CURRYE");//本班余额
		String CURRYK=request.getParameter("CURRYK");//损益金额
		String curryh=request.getParameter("curryh");//银行存款
		String currsjye=request.getParameter("currsjye");//实际余额
		String classid=request.getParameter("classid");//classid
		String remark=request.getParameter("remark");//备注
		String CURRJC=request.getParameter("CURRJC");//本班结存
		
		String res = ChangeClassBase.houseworkclose(appData,currsjye,currxj,curryh,remark,CURRJC,Currqc,CURRYE,CURRYK,classid);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.close();
		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
