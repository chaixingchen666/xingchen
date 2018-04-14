package tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class DownloadUtils {
	/**
	 * 通过链接获取文件流
	 * @param urlString
	 * @return
	 * @throws Exception
	 */
	public InputStream getUrlFileStream(String url) throws IOException {
		// 构造URL
		if(DataBase.isCS)
			System.out.println(url);
		URL urls = new URL(url);
		// 打开连接
		URLConnection con = urls.openConnection();
		//设置请求超时为5s
		con.setConnectTimeout(5*60*1000);
		// 输入流
		InputStream is = con.getInputStream();
		return is;
	}
	
	/**
	 * 通过链接获取文件流
	 * @param urlString
	 * @return
	 * @throws Exception
	 */
	public InputStream postUrlFileStream(String apiUrl,HashMap<String,String> params) throws IOException {
		// 构造URL
		if(DataBase.isCS)
			System.out.println(apiUrl);
		try {
			String str = null;
			InputStream is = null;
			URL url = new URL(DataBase.removekg(apiUrl));// 根据参数创建URL对象
			if(DataBase.isCS)System.out.println(url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();// 得到HttpURLConnection对象
			con.setRequestMethod("POST");
			con.setUseCaches(false);  
			con.setReadTimeout(5*60*1000);
			con.setDoInput(true);
			con.setDoOutput(true);// 指示应用程序要将数据写入 URL 连接。
			String content = DataBase.getContent(params);// 解析参数（请求的内容）
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 设置内容
			if(content!=null){
				con.setRequestProperty("Content-Length", content.length() + "");// 设置内容长度
				OutputStream os = con.getOutputStream();
				os.write(content.getBytes("utf-8"));// 发送参数内容
				if (con.getResponseCode() == 200) {
					is = con.getInputStream();
					con.getInputStream().close();
				}
				os.flush();
				os.close();
			}
			if(DataBase.isCS)
				System.out.println(str);
			con.disconnect();
			return is;
		}catch (IOException e) {
			// TODO Auto-generated catch block
			if(DataBase.isCS)
				System.out.println("-------连接超时2-----");
			e.printStackTrace();
			return null;
		}
	}
}
