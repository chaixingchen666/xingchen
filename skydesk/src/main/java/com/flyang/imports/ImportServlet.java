package com.flyang.imports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;

/**
 * Servlet implementation class ImportServlet
 */
@WebServlet("/importservice")
public class ImportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImportServlet() {
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
		if (reqpar.equals("addfirstpaycurrxls")){ //导入期初应付
			action = "addfirstpaycurrxls";
		}else if(reqpar.equals("addfirstincomecurrxls")){//期初应收
			action = "addfirstincomecurrxls";
		}else if(reqpar.equals("addtempcheckmxls")){//临时盘点1
			action = "addtempcheckmxls";
		}else if(reqpar.equals("appendomuserxls")){//订货成员账户
			action = "appendomuserxls";//
		}
		if(!action.equals(""))
			ImportXls(action, request, response, appData);
	}
	private void ImportXls(String action, HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);// 通过工厂生成一个处理文件上传的servlet对象
//		PrintWriter out = response.getWriter();
		
		try {
			List<FileItem> items = servletFileUpload.parseRequest(request);// 解析request
			Iterator<FileItem> iterator = items.iterator();
			JSONObject everyObj = new JSONObject();
			while (iterator.hasNext()) {
				FileItem item = (FileItem) iterator.next();
				if (item.isFormField()) {// 表单的参数字段
					if(DataBase.isCS)System.out.println("表单的参数名称：" + item.getFieldName() + ",表单的参数值：" + item.getString("UTF-8"));
					if(!item.getFieldName().equals("")){
						if(!item.getFieldName().equals("id")&&!item.getFieldName().equals("name")
								&&!item.getFieldName().equals("type")
								&&!item.getFieldName().equals("lastModifiedDate")
								&&!item.getFieldName().equals("size"))
						everyObj.put(item.getFieldName(), item.getString("UTF-8"));
					}
				} else{
					if (item.getName() != null && !item.getName().equals("")) {// 一个上传的文件
						if(DataBase.isCS)System.out.println("文件的名称：" + item.getName());
						if(DataBase.isCS)System.out.println("文件的大小：" + item.getSize());
						if(DataBase.isCS)System.out.println("文件的类型：" + item.getContentType());
						int countFive = item.getName().lastIndexOf(".");
	                    String filetype = item.getName().substring(countFive + 1);// 扩展名
						ImportBase extendBase = new ImportBase();
						extendBase.setAction(action);
						List<String> fields = new ArrayList<>();
						List<String> fieldsname = new ArrayList<>();
						String channel = "";
						switch (action) {
						case "addfirstpaycurrxls":
							fields = Arrays.asList("provname","housename","curr","remark","handno");
							fieldsname = Arrays.asList("供应商","店铺","金额","摘要","自编号");
							channel = "firstpaycurr";
							break;
						case "addfirstincomecurrxls":
							fields = Arrays.asList("custname","housename","curr","remark","handno");
							fieldsname = Arrays.asList("客户","店铺","金额","摘要","自编号");
							channel = "firstincomecurr";
							break;
						case "addtempcheckmxls":
							fields = Arrays.asList("wareno","colorname","sizename","amount");
							fieldsname = Arrays.asList("货号","颜色","尺码","数量");
							channel = "tempcheckm";
							break;
						case "appendomuserxls":
							fields = Arrays.asList("custname","omepname","mobile","remark","omepid","omepno","password");
							fieldsname = Arrays.asList("客户名称","用户姓名","移动电话","备注","用户ID","用户账号","密码");
							channel = "omuser";
							break;
						default:
							break;
						}
						extendBase.setFields((String[])fields.toArray());
						extendBase.setFieldsname((String[])fieldsname.toArray());
						extendBase.setChannel(channel);
						if(filetype.contains("xls"))
							extendBase.importxlsread(filetype, everyObj,item.getInputStream(), appData);
						else if(filetype.contains("txt")&&action.equals("addtempcheckmxls"))
							extendBase.importtxtread(filetype, everyObj,item.getInputStream(), appData);
						if(DataBase.isCS)System.out.println("上传文件成功！");
					} else {
						if(DataBase.isCS)System.out.println("没有选择上传文件！");
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
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
