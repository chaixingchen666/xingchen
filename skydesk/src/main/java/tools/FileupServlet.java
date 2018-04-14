package tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Base64;
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

/**
 * Servlet implementation class FileupServlet
 */
@WebServlet(name = "fileup", urlPatterns = { "/fileup" })
public class FileupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileupServlet() {
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
		HttpSession session = request.getSession(true);
		AppData appData = new AppData();
		appData.setqxpublic(request, response, session);
		PrintWriter out = response.getWriter();
		String filename = "";
		// String userName =
		// request.getSession().getAttribute("myname").toString();
		String extendName = "";

		String msg = "";
		StringBuffer isres = new StringBuffer();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 存储文件路径，分别是原图片、缓存文件、缩略图
		factory.setSizeThreshold(1024 * 1024);// 文件大小
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			List<FileItem> list = (List<FileItem>) upload.parseRequest(request);
			String dhhbj = "0";// 订货会标记
			String ordid = "1";// 主图标记
			for (FileItem item : list) {
				String name = item.getFieldName();
				// if(DataBase.isCS)
				// System.out.println("name:"+name);
				if (item.isFormField()) {
					String value = item.getString();
					// if(DataBase.isCS)
					// System.out.println("value:"+value);
					if (name.equals("dhhbj"))
						dhhbj = value;
					// request.setAttribute(name, value);
				} else {
					String value = item.getName();
					int start;
					if ("\\".equals(File.separator)) {
						start = value.lastIndexOf("\\");
					} else if ("/".equals(File.separator)) {
						start = value.lastIndexOf("/");
					} else {
						start = value.lastIndexOf("\\");
					}
					filename = value.substring(start + 1);// 带扩展名
					int countFive = filename.lastIndexOf(".");
					extendName = filename.substring(countFive + 1);// 扩展名
					String warenostr = filename.replace("." + extendName, "");
					int sorid = warenostr.lastIndexOf(" (");
					int eorid = warenostr.lastIndexOf(")");
					if (sorid > 0&&sorid<eorid) {
						try{
							ordid = warenostr.substring(sorid + 2,eorid); // 主图标记1-10
						}catch (Exception e) {
							// TODO: handle exception
							ordid = "";
						}
					}

					String wareno = warenostr;
					if (sorid > 0&&sorid<eorid)
						wareno = warenostr.substring(0, sorid);
					if(ordid.equals("")){
						isres.append("0");
						msg="货号编号错误";
						continue;
					}
					InputStream in = item.getInputStream();
					byte[] buf = new byte[in.available()];
					// if(DataBase.isCS)System.out.println("文件大小:" +
					// item.getSize());
					in.read(buf);
					in.close();
					
					String ss = Base64.getEncoder().encodeToString(buf);
					JSONObject dataobj = JSONObject.fromObject("{}");
					JSONObject imgobj = JSONObject.fromObject("{}");
					dataobj.put("wareno", wareno);
					dataobj.put("dhhbj", dhhbj);
					dataobj.put("ordid", ordid);
					imgobj.put("base64string", ss.trim().replaceAll("\r\n", "").replaceAll("\n", "").replaceAll(" ", ""));
					String exparams = "&pict=" + DataBase.encode(imgobj.toString());
					FlyangHttp fh = new FlyangHttp();
					fh.setHostIP(DataBase.JERPIP + "buy?action=");
					JSONObject resobj = fh.FlyangdoPost("uploadwarepicture", dataobj, exparams, appData);
					if (appData.isResult(resobj.toString())){
						isres.append("1");
					} else {
						isres.append("0");
						msg = appData.msg;
					}
					in.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String tmp = isres.toString();
		tmp = "{\"stg\":" + tmp + ",\"msg\":\"" + msg + "\"}";
		out.print(tmp);
		out.flush();
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
