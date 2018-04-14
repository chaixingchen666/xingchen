package tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.flyang.tools.FlyangHttp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
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
		// response.setHeader("Pragma","No-cache");
		// response.setHeader("Cache-Control","no-cache");
		// response.setDateHeader("Expires", 0);
		HttpSession session = request.getSession(true);
		AppData appData = new AppData();
		appData.setqxpublic(request, response, session);
		String reqpar = request.getParameter("downser");
		if (reqpar.equals("downloadxls")) {
			downloadxls(request, response, appData);
		}else if (reqpar.equals("getallwareimg")) {
			getallwareimg(request, response, appData);
		}else if(reqpar.equals("getaccpayzip")) {
			getaccpayzip(request, response, appData);
			
		}
	}

	private void downloadxls(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO Auto-generated method stub
		response.setContentType("multipart/form-data");
		String _params = DataBase.getParamsData(request.getParameterMap());
		String tableid = request.getParameter("tableid");
		String strwhere = request.getParameter("strwhere");
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("strwhere", strwhere);
		String filename = "";
		switch (tableid) {
		case "0":
			filename = "商品编码.xls";
			break;
		case "1":
			filename = "会员档案.xls";
			break;
		case "2":
			filename = "商品条码.xls";
			break;
		case "3":
			filename = "采购入库汇总.xls";
			break;
		case "4":
			filename = "零售汇总统计.xls";
			break;
		case "5":
			filename = "库存盘点汇总.xls";
			break;
		case "6":
			filename = "库存资源明细.xls";
			break;

		default:
			break;
		}
		response.setHeader("Content-Disposition", "attachment;filename=\"" + DataBase.getStr(request, filename)+"\""); // filename参数指定下载的文件名
		
		String url = DataBase.HostIP + "table2excel"
				+ "&"+ _params
				+ DataBase.signature(appData);
		// 4.获取要下载的文件输入流
		InputStream in = null;
		if(strwhere!=null&&strwhere.equals(""))
			in = new DownloadUtils().postUrlFileStream(url,params);
		else 
			in = new DownloadUtils().getUrlFileStream(url);
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
	private void getallwareimg(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment;filename=" + appData.comid + ".zip"); // filename参数指定下载的文件名
		String imgt = request.getParameter("imgt");
		String prodyear = request.getParameter("prodyear");
		String img = imgt.equals("1") ? "IMAGENAME0" : "IMAGENAME";
		
		FlyangHttp fh = new FlyangHttp();
		fh.setHostIP(DataBase.JERPIP+"api?action=");
		JSONObject dataobj = new JSONObject();
		dataobj.put("sort", "wareid");
		dataobj.put("order", "asc");
		dataobj.put("prodyear", prodyear);
		dataobj.put("havepicture", 1);
		dataobj.put("noused", 0);
		dataobj.put("page", 1);
		dataobj.put("rows", 50);
		dataobj.put("fieldlist", "a.wareid,a.wareno,a.warename,a.imagename,imagename0");
		
		JSONObject resJson = new JSONObject();
		
		resJson = fh.FlyangdoPost("warecodelist", dataobj, appData);
		
		ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
		if(appData.valideResData(resJson)) {
			int total = resJson.getInt("total");
			double totalp = Math.ceil(new BigDecimal(total).divide(new BigDecimal(50)).doubleValue());
			JSONArray arr = resJson.getJSONArray("rows");
			for(int i=0;i<arr.size();i++) {
				JSONObject json = arr.getJSONObject(i);
				String wareno = "";
				String imagename = "";
				if(json.has("WARENO"))
					wareno = json.getString("WARENO");
				if(json.has(img))
					imagename = json.getString(img);
				if(wareno.length()>0) {
					try {
						zos.putNextEntry(new ZipEntry(wareno + ".jpg"));
					} catch (Exception e) {
						// TODO: handle exception
						zos.putNextEntry(new ZipEntry(wareno +"(货号重复！)"+ DataBase.getRandomNo()+".jpg"));
					}
					InputStream is = DataBase.getimgIS(DataBase.ImageshowIP + imagename);
					try {
						byte b[] = new byte[1024];
						int n = 0;
						while ((n = is.read(b)) != -1) {
							zos.write(b, 0, n);
						}
					} finally {
						zos.flush();
						is.close();
					}
				}
			}
			if(totalp>1) {
				for(int j =2;j<=totalp;j++) {
					dataobj.put("page", j);
					resJson = fh.FlyangdoPost("warecodelist", dataobj, appData);
					arr = resJson.getJSONArray("rows");
					for(int i=0;i<arr.size();i++) {
						JSONObject json = arr.getJSONObject(i);
						String wareno = "";
						String imagename = "";
						if(json.has("WARENO"))
							wareno = json.getString("WARENO");
						if(json.has(img))
							imagename = json.getString(img);
						if(wareno.length()>0) {
							InputStream is = DataBase.getimgIS(DataBase.ImageshowIP + imagename);
							if(is==null) continue;
							try {
								try {
									zos.putNextEntry(new ZipEntry(wareno + ".jpg"));
								} catch (Exception e) {
									// TODO: handle exception
									zos.putNextEntry(new ZipEntry(wareno +"(货号重复！)"+ DataBase.getRandomNo()+".jpg"));
								}
								byte b[] = new byte[1024];
								int n = 0;
								while ((n = is.read(b)) != -1) {
									zos.write(b, 0, n);
								}
							}catch (Exception e) {
								// TODO: handle exception
							} finally {
								zos.flush();
								is.close();
							}
						}
					}
				}
			}
		}
		zos.flush();
		zos.close();
	}
	private void getaccpayzip(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/zip");
		String zipfilename = request.getParameter("filename");
		response.setHeader("Content-Disposition", "attachment;filename=" + DataBase.encode(zipfilename) + ".zip"); // filename参数指定下载的文件名
		String imageyyzz = request.getParameter("imageyyzz");//*营业执照
		String imagezzjg = request.getParameter("imagezzjg");//*组织机构
		String imagefrsfza = request.getParameter("imagefrsfza");//*法人身份证正面
		String imagefrsfzb = request.getParameter("imagefrsfzb");//*法人身份证反面
		String imagejskzp = request.getParameter("imagejskzp");//*结算卡
		String imagejskcyrsfza = request.getParameter("imagejskcyrsfza");//*结算卡持有人身份证正面
		String imagejskcyrsfzb = request.getParameter("imagejskcyrsfzb");//*结算卡持有人身份证反面
		String imagedpzpmt = request.getParameter("imagedpzpmt");//*店铺照片（门头）
		String imagedpzpsyt = request.getParameter("imagedpzpsyt");//*店铺照片（收银台）
		String imagedpzpjycs = request.getParameter("imagedpzpjycs");//*店铺照片（经营场所）
		ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
		String[] fileurlstr = {imageyyzz,imagezzjg,imagefrsfza,imagefrsfzb,imagejskzp,imagejskcyrsfza,imagejskcyrsfzb,imagedpzpmt,imagedpzpsyt,imagedpzpjycs};
		String[] filenamelist = {"营业执照","组织机构","法人身份证正面","法人身份证反面","结算卡照片","结算卡持有人身份证正面","结算卡持有人身份证反面","店铺门头照片","店铺收银台照片","店铺经营场所照片"};
		for(int i=0;i<fileurlstr.length;i++) {
			String urlstr = fileurlstr[i];
			if(null==urlstr||urlstr.length()==0) continue;
			String filename = filenamelist[i];
			zos.putNextEntry(new ZipEntry(zipfilename+"/"+filename+".jpg"));
			InputStream is = DataBase.getimgIS(DataBase.ImageshowIP + urlstr);
			try {
				byte b[] = new byte[1024];
				int n = 0;
				while ((n = is.read(b)) != -1) {
					zos.write(b, 0, n);
				}
			} finally {
				zos.flush();
				is.close();
			}
		}
		zos.flush();
		zos.close();
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
