package com.flyang.uploadimg;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.HashMap;
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

import com.flyang.tools.FlyangHttp;

import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;

/**
 * Servlet implementation class ImportServlet
 */
@WebServlet("/uploadimgservice")
public class UploadImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadImgServlet() {
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
		String action = request.getParameter("flyangser");
		String server = request.getParameter("server");
		if(server==null){
			server = "ords";
		}
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		HttpSession session = request.getSession(true);
		AppData appData = new AppData();
		appData.setqxpublic(request, response, session);
		if(action.equals("addomsubject")||action.equals("updateomsubject")){
//			ImportOmImg(action, request, response, appData);
			SaveOmsubject(action,request, response, appData);
		}else if(action.equals("getfilebase64string")){
			getFileBase64String(action, request, response, appData);
		}else if(!action.equals(""))
			ImportImg(server,action, request, response, appData);
	}
	private void ImportImg(String server, String action, HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
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
	                    @SuppressWarnings("unused")
						String filetype = item.getName().substring(countFive + 1);// 扩展名
	                    InputStream in = item.getInputStream();
						byte[] buf = new byte[in.available()];
						in.read(buf);
						in.close();
						String ss = Base64.getEncoder().encodeToString(buf);
						String b64str = ss.trim().replaceAll("\r\n","").replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", "");
						JSONObject imgobj = new JSONObject();
						imgobj.put("base64string", DataBase.encode(b64str));
//						System.out.println(imgobj.toString());
						FlyangHttp FY = new FlyangHttp();
						String exparams = "&pict="+ imgobj.toString();
						PrintWriter out = response.getWriter();
						FY.setHostIP(DataBase.JERPIP+server+"?action=");
						JSONObject res = FY.FlyangdoPost(action, everyObj, exparams, appData);
						out.print(res);
						out.flush();
						out.close();
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
	private void SaveOmsubject(String action, HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		HashMap<String, String> map = DataBase.getParamsMap(request.getParameterMap());
		map.remove("flyangser");
		JSONObject dataobj = DataBase.getObjFrom(map);
		JSONObject imgobj = new JSONObject();
		if(!dataobj.getString("omimagename").equals(""))
			imgobj.put("omimagename", DataBase.encode(dataobj.getString("omimagename")));
		if(!dataobj.getString("logoimagename").equals(""))
			imgobj.put("logoimagename", DataBase.encode(dataobj.getString("logoimagename")));
		dataobj.remove("omimagename");
		dataobj.remove("logoimagename");
		FlyangHttp FY = new FlyangHttp();
		String exparams = "&pict="+ imgobj.toString();
		FY.setHostIP(DataBase.JERPIP+"ords?action=");
		JSONObject res = FY.FlyangdoPost(action, dataobj, exparams, appData);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.flush();
		out.close();
	}
	private void getFileBase64String(String action, HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);// 通过工厂生成一个处理文件上传的servlet对象
		try {
			List<FileItem> items = servletFileUpload.parseRequest(request);// 解析request
			Iterator<FileItem> iterator = items.iterator();
			JSONObject everyObj = new JSONObject();
			@SuppressWarnings("unused")
            JSONObject imgobj = new JSONObject();
			JSONObject res = new JSONObject();
			res.put("result", "1");
			while (iterator.hasNext()) {
				FileItem item = (FileItem) iterator.next();
				if (item.isFormField()){// 表单的参数字段
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
	                    InputStream in = item.getInputStream();
						byte[] buf = new byte[in.available()];
						in.read(buf);
						in.close();
						String ss = Base64.getEncoder().encodeToString(buf);
						String b64str = ss.trim().replaceAll("\r\n","").replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", "");
						res.put("msg",b64str);
						/*if(item.getName().equals("omimagename")){
							imgobj.put("omimagename", DataBase.encode(b64str));
						}else if(item.getName().equals("logoimagename")){
							imgobj.put("logoimagename", DataBase.encode(b64str));
						}*/
						if(DataBase.isCS)System.out.println("上传文件成功！");
					} else {
						if(DataBase.isCS)System.out.println("没有选择上传文件！");
					}
				}
			}
//			FlyangHttp FY = new FlyangHttp();
//			String exparams = "&pict="+ imgobj.toString();
//			JSONObject res = FY.FlyangdoPost(action, everyObj, exparams, appData);
			PrintWriter out = response.getWriter();
			out.print(res);
			out.flush();
			out.close();
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
