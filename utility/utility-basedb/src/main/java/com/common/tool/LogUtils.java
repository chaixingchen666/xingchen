package com.common.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.common.tool.Func;

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
			writerLogInfo(logPrint, msg);
		} catch (IOException e) {

			(new File(filePath)).mkdirs();
			try {
				logPrint = new PrintWriter(new FileWriter(filePath + fileName, true), true);
			} catch (IOException ex) {
				logPrint = new PrintWriter(System.err);
				writerErrorInfo(logPrint, ex, "无法打开日志文件：" + filePath + fileName);
			}
		} finally {
			if (logPrint != null)
				try {
					logPrint.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			//			if (logPrint != null)
			//				logPrint.close();
		}
	}

	/**
	 * 
	 * * 将文本信息写入日志文件
		 * * @param msg
	 * 
	 * * 日志内容
	 * 
	 */

	private synchronized static void writerLogInfo(PrintWriter logPrint, String msg) {
		logPrint.println("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]" + msg );
		logPrint.println("--------------------------------------------------------------------------");
	}

	/**
	 * 将文本信息与异常写入日志文件
	 * 
	 * @param e
	 * @param msg
	 */

	private synchronized static void writerErrorInfo(PrintWriter logPrint, Throwable e, String msg) {
		logPrint.println("\r\n[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]" + "\r\n" + msg );
		e.printStackTrace(logPrint);
	}

	public static String getWebPath() {
		String rootPath = "";
		String classPath = LogUtils.class.getClassLoader().getResource("/").getPath();
		File rootfile = new File(classPath).getParentFile().getParentFile().getParentFile();
		File projFile = new File(classPath).getParentFile().getParentFile();
		String projname = projFile.getName();

		rootPath = rootfile.getPath();

		try {
			rootPath = URLDecoder.decode(rootPath, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rootPath += "/log/" + projname + "/";

		//		System.out.println("getWebPath()=" + rootPath);
		return rootPath;

		//		String rootPath = "";
		//		String classPath = LogUtils.class.getClassLoader().getResource("/").getPath();
		//		File rootfile = new File(classPath).getParentFile().getParentFile().getParentFile();
		//		File projFile = new File(classPath).getParentFile().getParentFile();
		//		rootPath = rootfile.getPath();
		//		String projname = projFile.getName();
		//		System.out.println("getWebPath()="+rootPath + "/loginfo/" + projname + "log/");
		//		return rootPath + "/loginfo/" + projname + "log/";
	}

	public static void jsErrorLogWrite(String epid, String msg) {
		String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_JSError.log";
		createLog(getWebPath(), filename, epid + "js报错：" + "\r\n" + msg);
	}

	//	public static void main(String[] args) {
	//		LogWrite("20170112.log", "text");
	//	}
	public static void LogWrite(String action, String msg) {
		String filename = new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + ".log";
		createLog(getWebPath(), filename, action.trim() + "\r\n" + msg);
	}

	public static void LogWrite(String action, String msg, String filetag) {
		String filename = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
		//		if (filetag.length()>0)filename = new SimpleDateFormat("yyyyMMddHHmm").format(new Date())+ filetag+ ".log";
		if (filetag.length() > 0)
			filename += "_" + filetag;
		filename += ".log";
		createLog(getWebPath(), filename, action.trim() + "\r\n" + msg);
	}

	public static void LogErrWrite(String action, String msg) {
		String filename = new SimpleDateFormat("yyyyMMddHH").format(new Date()) + "_err.log";
		createLog(getWebPath(), filename, action.trim() + "\r\n" + msg);
	}

	public static void LogDebugWrite(String action, String msg) {
		String filename = new SimpleDateFormat("yyyyMMddHH").format(new Date()) + "_debug.log";
		createLog(getWebPath(), filename, action.trim() + "\r\n" + msg);
	}

}
