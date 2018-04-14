package tools;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogUtils {
	
	/**
	 * * 读取log或者txt信息 * * @param filePath * @return
	 */

	public List<String> readLog(String filePath) {
		List<String> list = new ArrayList<String>();
		try {
			FileInputStream is = new FileInputStream(filePath);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			try {
				while ((line = br.readLine()) != null) {
					if (line.equals(""))
						continue;
					else
						list.add(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("读取一行数据时出错");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("文件读取路径错误FileNotFoundException");
		}
		return list;
	}

	/**
	 * 
	 * * 新建log或者txt文件并写入内容
	 * 
	 * *
	 * 
	 * * @param filePath
	 * 
	 * * @param fileName
	 * 
	 * * @param msg
	 * 
	 */

	public static void createLog(String filePath, String fileName, String msg) {
		PrintWriter logPrint = null;
		try {
			logPrint = new PrintWriter(new FileWriter(filePath + fileName, true), true);
		} catch (IOException e) {
			
			(new File(filePath)).mkdirs();
			try {
				logPrint = new PrintWriter(new FileWriter(filePath + fileName, true), true);
			} catch (IOException ex) {
				logPrint = new PrintWriter(System.err);
				writerErrorInfo(logPrint, ex, "无法打开日志文件：" + filePath + fileName);
			}
		}
		writerLogInfo(logPrint, msg);
	}

	/**
	 * 
	 * * 将文本信息写入日志文件
	 * 
	 * *
	 * 
	 * * @param msg
	 * 
	 * * 日志内容
	 * 
	 */

	private synchronized static void writerLogInfo(PrintWriter logPrint, String msg) {
		logPrint.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + "\r\n" + msg);
	}

	/**
	 * 将文本信息与异常写入日志文件
	 * 
	 * @param e
	 * @param msg
	 */

	private synchronized static void writerErrorInfo(PrintWriter logPrint, Throwable e, String msg) {
		logPrint.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + "\r\n" + msg);
		e.printStackTrace(logPrint);
	}
	
	public static String getWebPath() {
		String rootPath = "";
		String classPath = LogUtils.class.getClassLoader().getResource("/").getPath();
		File rootfile = new File(classPath).getParentFile().getParentFile().getParentFile();
		File projFile = new File(classPath).getParentFile().getParentFile();
		rootPath = rootfile.getPath();
		String projname = projFile.getName();
		return rootPath +"/loginfo/" + projname +"log/";
	}
	public static void LogWrite(String action,String msg){
		String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".log";
		createLog(getWebPath(),filename,action+"\r\n"+msg);
	}
	public static void LogExportWrite(String action,String msg){
		String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "ExportXls.log";
		createLog(getWebPath(),filename,action+"\r\n"+msg);
	}
	public static void jsErrorLogWrite(String epid,String msg){
		String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "JSError.log";
		createLog(getWebPath(),filename,epid+"js报错："+"\r\n"+msg+"\r\n");
	}
	public static void main(String[] args) {
		LogWrite("20170112.log", "text");
	}

}
