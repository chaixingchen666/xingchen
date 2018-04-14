package tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class DownloadImage {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		 download("https://gg.385gg.com/gif/2017.07/07/6.gif", "1.gif","d:");
//		String url = "https://gg.385gg.com/gif/2017.07";
//		for(int i=1;i<=8;i++) {
//			for(int j=1;j<=8;j++) {
//				try {
//					download(url+"/0"+i+"/"+j+".gif",j+".gif","d:\\gif\\");
//				} catch (Exception e) {
//					// TODO: handle exception
//					System.out.println(e.getMessage());
//				}
//			}
//		}
		
	}
	
	public static void download(String urlString, String filename,String savePath) throws Exception {
	    // 构造URL
	    URL url = new URL(urlString);
	    // 打开连接
	    URLConnection con = url.openConnection();
	    //设置请求超时为5s
	    con.setConnectTimeout(5*1000);
	    // 输入流
	    InputStream is = con.getInputStream();
	
	    // 1K的数据缓冲
	    byte[] bs = new byte[1024];
	    // 读取到的数据长度
	    int len;
	    // 输出的文件流
	   File sf=new File(savePath);
	   if(!sf.exists()){
		   sf.mkdirs();
	   }
	   OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);
	    // 开始读取
	    while ((len = is.read(bs)) != -1) {
	      os.write(bs, 0, len);
	    }
	    // 完毕，关闭所有链接
	    os.close();
	    is.close();
	} 
	public static InputStream getimgIS(String urlString) throws Exception {
	    // 构造URL
	    URL url = new URL(urlString);
	    // 打开连接
	    URLConnection con = url.openConnection();
	    //设置请求超时为5s
	    con.setConnectTimeout(5*1000);
	    // 输入流
	    InputStream is = con.getInputStream();
	    try{
	    	return is;
	    }finally{
	    	is.close();
	    }
	}
	public static String getimgdata(String imgt, String prodyear, AppData appData) {
		// TODO Auto-generated method stub
		String url = DataBase.HostIP + "warecodelist&page=1&rows=" + appData.pagecount
				+ "&sort=wareid&order=asc&noused=2&havepicture=1&prodyear=" + prodyear
				+ "&fieldlist=A.WAREID,A.WARENO,A.WARENAME,a.imagename,imagename0" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}
}
