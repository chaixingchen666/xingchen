package tools;

import java.io.*;
import java.net.URLDecoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class ReaderServer {
//	 public static File f = new File(getRootPath()+"skydeskzs.xml");
//	 public static File f = new File(getRootPath()+"skydeskjava.xml");
	public static File f = new File(getRootPath() + "skydesk.xml");

	public static String[] getIP() {
		Element element = null;
		// 可以使用绝对路劲
		// documentBuilder为抽象不能直接实例化(将XML文件转换为DOM文件)
		DocumentBuilder db = null;
		DocumentBuilderFactory dbf = null;
		try {
			// 返回documentBuilderFactory对象
			dbf = DocumentBuilderFactory.newInstance();
			// 返回db对象用documentBuilderFatory对象获得返回documentBuildr对象
			db = dbf.newDocumentBuilder();

			// 得到一个DOM并返回给document对象
			Document dt = db.parse(f);
			// 得到一个elment根元素
			element = dt.getDocumentElement();
			// 获得根节点
			// if(DataBase.isCS)System.out.println("根元素：" +
			// element.getNodeName());
			// 获得根元素下的子节点
			NodeList childNodes = element.getChildNodes();
			if (childNodes.getLength() >= 5) {
				Node node1 = childNodes.item(1);
				Node node2 = childNodes.item(3);
				Node node3 = childNodes.item(5);
				String[] IPAdre = { "", "", "" };
				if ("ServerIP".equals(node1.getNodeName())) {
					String sip = node1.getTextContent().trim();
					if (sip.equals("http://api.skydispark.com:62202")) {
						// AppData.versionname="（测试）";
						AppData.versionname = "";
					} else if (sip.equals("http://api.skydispark.com:62201")) {
						AppData.versionname = "";
					} else {
						AppData.versionname = "";
					}
					IPAdre[0] = sip;
				}
				if ("ImgIP".equals(node2.getNodeName())) {
					String sip = node2.getTextContent().trim();
					if (sip.equals("http://api.skydispark.com:62202")) {
						// AppData.versionname="（测试）";
						AppData.versionname = "";
					} else if (sip.equals("http://api.skydispark.com:62201")) {
						AppData.versionname = "";
					} else {
						AppData.versionname = "";
					}
					IPAdre[1] = sip;
				}
				if ("ImgShowIP".equals(node3.getNodeName())) {
					String sip = node3.getTextContent().trim();
					if (sip.equals("http://112.124.36.28")) {
						// AppData.versionname="（测试）";
						AppData.versionname = "";
					} else if (sip.equals("http://112.124.36.28")) {
						AppData.versionname = "";
					} else {
						AppData.versionname = "";
					}
					IPAdre[2] = sip;
				}
				getIPJson();
				return IPAdre;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static JSONObject getIPJson(){
		 //字符流输出
		try {
	        @SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
	        String string=null;
	        StringBuffer sb = new StringBuffer();
	        while((string = reader.readLine())!=null){
	            sb.append(string);
	        }
	        XMLSerializer xmlSerializer = new XMLSerializer();
	        JSON json=xmlSerializer.read(sb.toString());
	        return JSONObject.fromObject(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String getRootPath() {
		String classPath = ReaderServer.class.getClassLoader().getResource("/").getPath();
		File rootfile = new File(classPath);
		String rootPath = rootfile.getPath();
		try {
			rootPath = URLDecoder.decode(rootPath, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return rootPath +"/";
	}

	/**
	 * 获取项目路径
	 * @return String
	 */
	public static String getWebPath() {
		String classPath = ReaderServer.class.getClassLoader().getResource("/").getPath();
		File rootfile = new File(classPath).getParentFile().getParentFile().getParentFile();
		String rootPath = rootfile.getPath();
		try {
			rootPath = URLDecoder.decode(rootPath, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return rootPath +"/";
	}

}
