package com.flyang.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * Servlet implementation class ImportServlet
 */
@WebServlet("/uploadimgservice")
public class UploadImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log = LoggerFactory.getLogger(UploadImgServlet.class);
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
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		getFileBase64String(request, response);
	}
	private void getFileBase64String(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
					if(!item.getFieldName().equals("")){
						if(!item.getFieldName().equals("id")&&!item.getFieldName().equals("name")
								&&!item.getFieldName().equals("type")
								&&!item.getFieldName().equals("lastModifiedDate")
								&&!item.getFieldName().equals("size"))
						everyObj.put(item.getFieldName(), item.getString("UTF-8"));
					}
				} else{
					if (item.getName() != null && !item.getName().equals("")) {// 一个上传的文件
						log.info("文件的名称：" + item.getName());
						log.info("文件的大小：" + item.getSize());
						log.info("文件的类型：" + item.getContentType());
	                    InputStream in = item.getInputStream();
						byte[] buf = new byte[in.available()];
						in.read(buf);
						in.close();
						String ss = Base64.getEncoder().encodeToString(buf);
						String b64str = ss.trim().replaceAll("\r\n","").replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", "");
						res.put("msg",b64str);
						log.info("上传文件成功！");
					} else {
						log.info("没有选择上传文件！");
					}
				}
			}
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
