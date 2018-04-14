package com.flyang.main;

import java.io.BufferedWriter;

//import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
//import org.apache.commons.io.IOUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.sql.Types;
//import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//import sun.misc.*;

import java.io.BufferedReader;
//import java.io.IOException;  
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
//import java.net.InetSocketAddress;
//import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.FileNotFoundException;

import org.apache.commons.io.IOUtils;
//import org.apache.commons.io.IOUtils;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.apache.tomcat.util.http.fileupload.IOUtils;
//import org.csource.common.MyException;
import org.csource.common.NameValuePair;
//import org.apache.commons.httpclient.NameValuePair;
//import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;

//import org.csource.fastdfs.FileInfo;

import org.csource.fastdfs.StorageClient;

import org.csource.fastdfs.StorageServer;

import org.csource.fastdfs.TrackerClient;

import org.csource.fastdfs.TrackerServer;

import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;

import com.common.tool.Func;
import com.common.tool.LogUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.WriterException;
//import com.google.zxing.common.BitMatrix;
import java.security.Key;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Map.Entry;

//import java.security.spec.AlgorithmParameterSpec;
//import org.apache.commons.io.IOUtils; 

public class pFunc {
	private static String url1 = "http://sms.10690221.com:9011/hy/";
	// http://ip:port/hy/m?uid=1234&auth=fcea920f7412b5da7be0cf42b8c93759

	private static String fast_conf_filename = getRootPath() + "fast_client.conf";// fastdfs配置文件
	private static String errmess;
	public static int MAX_SIZE = 15; // 最大尺码数

	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

	// 银货通常量
	public static final String FLYANGHOSTID = "118.178.229.131"; // 服务器id（测试）

	public static final String YHT_APP_KEY = "123e51b3eecf458a9ba4591b9542ee53";// 银货通接口key商户号
	public static final String YHT_APP_SECRET = "0a186c6c085640fea38d42ad23420b9701be2476ffe9ce348399b184a829591e";// 银货通接口secret商户密钥

	public static final String YHT_URL = "http://61.164.59.232:9093/yht-api-uzmd/";

	public static final String KEY_SALT_KEY = "_salt_key";

	public static final String KEY_TIMESTAMP = "_timestamp";

	

	// String s = "fs123fdsa";//String变量
	//
	// byte b[] = s.getBytes();//String转换为byte[]
	//
	// String t = new String(b);//bytep[]转换为String

	

	// private static byte[] ZeroPadding(byte[] in, Integer blockSize) {
	// Integer copyLen = in.length;
	// if (copyLen > blockSize) {
	// copyLen = blockSize;
	// }
	// byte[] out = new byte[blockSize];
	// System.arraycopy(in, 0, out, 0, copyLen);
	// return out;
	// }

	// public static byte[] aesEncrypt1(String str, String key) {
	// // public static String aesEncrypt1(String str, String key) {
	// //System.out.println("aesEncrypt1.3 " + str + " " + key);
	//
	// if (str == null || key == null)
	// return null;
	// try {
	// SecretKeySpec key0 = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	//
	// Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	// cipher.init(Cipher.ENCRYPT_MODE, key0);
	// byte[] bytes = cipher.doFinal(str.getBytes("UTF-8"));
	// return bytes;
	// // return new BASE64Encoder().encode(bytes).trim().replaceAll("\r\n",
	// "").replaceAll("\n", "");
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// WriteErrTxt("ERR1002:", e.getMessage());
	// }
	// return null;
	//
	// }

	

	//	public String aesDecrypt1(String str, String key) {
	//		if (str == null || key == null)
	//			return null;
	//		try {
	//			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	//			//cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("UTF-8"), "AES")); //原方法
	//
	//			//解决linux 环境aes加密随机数读取不一致问题
	//			KeyGenerator kgen = KeyGenerator.getInstance("AES");
	//			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
	//			secureRandom.setSeed(key.getBytes("utf-8"));
	//			kgen.init(128, secureRandom);
	//			SecretKey secretKey = kgen.generateKey();
	//			byte[] enCodeFormat = secretKey.getEncoded();
	//			SecretKeySpec skey = new SecretKeySpec(enCodeFormat, "AES");
	//			cipher.init(Cipher.ENCRYPT_MODE, skey);
	//
	//			byte[] bytes = new BASE64Decoder().decodeBuffer(str);
	//			bytes = cipher.doFinal(bytes);
	//			return new String(bytes, "UTF-8");
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			// e.printStackTrace();
	//			WriteErrTxt("ERR1001:", e.getMessage() + " str=" + str + " key=" + key);
	//			//			WriteLogTxt("ERR1001:", "", e.getMessage() + " str=" + str + " key=" + key);
	//
	//		}
	//		return null;
	//	}

	public static Date getServerdatetime() {

		String qry = "Select To_Char(SYSDATE,'yyyy-mm-dd hh24:mi:ss') as serverdate from dual";
		Date date0 = new Date();
		String datestr = DbHelperSQL.RunSql(qry);

		try {
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date0 = f.parse("1899-12-30 00:00:00");
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datestr);
		} catch (Exception e) {
			// e.printStackTrace();
			LogUtils.LogErrWrite("Err1006:", e.getMessage());
			return date0;
		}
		// return new Date();
	}

	

	

	/// 允许输入汉字，字母和数字

	

	// /**
	// * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
	// * 此方法中前三位格式有：
	// * 13+任意数
	// * 15+除4的任意数
	// * 18+除1和4的任意数
	// * 17+除9的任意数
	// * 147
	// */

	// 判断是否有效的ip地址
	public static boolean isIpaddress(String input) {
		if (input.length() <= 0)
			return true;
		// String regex =
		// "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." //
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." //
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."//
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

		return match(regex, input);
	}

	// 商品编码
	public static boolean isWarecode(String input) {
		if (input.length() <= 0)
			return true;
		String regex = "^[A-Za-z0-9\\u4e00-\\u9fa5\\+\\-\\.\\*\\#\\/\\(\\)]+$";
		// String regex =
		// "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
		return match(regex, input);
	}

	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

//	public static int WriteLogTxt(String username, String mess, String logstr) {
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
//		Date currdate = new Date();
//		// String filepath=;
//		// E:\sunjava\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\skyderp\
//		String filename = getRootPath() + "/log/" + df.format(currdate) + ".txt";
//		// String imgFile = getRootPath() + "/img/test.jpg";// "banner0.png";
//
//		// System.out.println(filename);
//
//		BufferedWriter out = null;
//		try {
//
//			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, true)));
//			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//			out.write("[" + df1.format(currdate) + "]" + username + " " + mess + "\r\n");
//			out.write(logstr + "\r\n");
//			out.write("------------------------------------------------\r\n");
//			return 1;
//		} catch (Exception e) {
//
//			e.printStackTrace();
//			return 0;
//		} finally {
//
//			try {
//
//				out.close();
//
//			} catch (IOException e) {
//
//				e.printStackTrace();
//			}
//		}
//
//	}
//
//	public static int WriteLogTxt(String username, String mess, String logstr, String filetag) {
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
//		Date currdate = new Date();
//		// filetag = "none";
//		String filename = getRootPath() + "/log/" + df.format(currdate);// + "_"
//																		// +
//																		// filetag
//																		// +
//																		// ".txt";
//		if (!filetag.equals(""))
//			filename += "_" + filetag;
//		filename += ".txt";
//
//		BufferedWriter out = null;
//		try {
//
//			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, true)));
//			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//			out.write("[" + df1.format(currdate) + "]" + username + " " + mess + "\r\n");
//			out.write(logstr + "\r\n");
//			out.write("------------------------------------------------\r\n");
//			return 1;
//		} catch (Exception e) {
//
//			e.printStackTrace();
//			return 0;
//		} finally {
//
//			try {
//
//				out.close();
//
//			} catch (IOException e) {
//
//				e.printStackTrace();
//			}
//		}
//
//	}

//	public static int WriteDebugTxt(String username, String logstr) {
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHH");
//		Date currdate = new Date();
//		// String filepath=;
//		// E:\sunjava\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\skyderp\
//		String filename = getRootPath() + "/log/" + df.format(currdate) + "_debug.txt";
//
//		BufferedWriter out = null;
//		try {
//
//			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, true)));
//			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//			out.write("[" + df1.format(currdate) + "]" + username + "\r\n");
//			out.write(logstr + "\r\n");
//			out.write("------------------------------------------------\r\n");
//			return 1;
//		} catch (Exception e) {
//
//			e.printStackTrace();
//			return 0;
//		} finally {
//
//			try {
//
//				out.close();
//
//			} catch (IOException e) {
//
//				e.printStackTrace();
//			}
//		}
//
//	}
//
//	public static int WriteErrTxt(String username, String logstr) {
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHH");
//		Date currdate = new Date();
//		// String filepath=;
//		// E:\sunjava\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\skyderp\
//		String filename = getRootPath() + "/log/" + df.format(currdate) + "_err.txt";
//		// String imgFile = getRootPath() + "/img/test.jpg";// "banner0.png";
//
//		// System.out.println(filename);
//
//		BufferedWriter out = null;
//		try {
//
//			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, true)));
//			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//			out.write("[" + df1.format(currdate) + "]" + username + "\r\n");
//			out.write(logstr + "\r\n");
//			out.write("------------------------------------------------\r\n");
//			return 1;
//		} catch (Exception e) {
//
//			e.printStackTrace();
//			return 0;
//		} finally {
//
//			try {
//
//				out.close();
//
//			} catch (IOException e) {
//
//				e.printStackTrace();
//			}
//		}
//
//	}

	public static String sqlSum_size(long wareid) {
		String qry = "declare ";
		qry += "\n      v_wareid number(10);";
		qry += "\n      v_sqltext varchar2(1000);";
		qry += "\n      v_i number(10);";
		qry += "\n   begin";
		qry += "\n     v_wareid:=" + wareid + ";";
		qry += "\n     declare cursor cur_sizecode is ";
		qry += "\n       select a.sizeid ";
		qry += "\n       from sizecode a ";
		qry += "\n       join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno ";
		qry += "\n       where b.wareid=v_wareid  and a.statetag=1 and a.noused=0";
		qry += "\n       order by a.sizeno,a.sizeid; ";
		qry += "\n       v_row cur_sizecode%rowtype;";
		qry += "\n     begin";
		qry += "\n       v_sqltext:='';";
		qry += "\n       v_i:=0;";
		qry += "\n       for v_row in cur_sizecode loop";
		qry += "\n          v_sqltext:=v_sqltext||',sum(case sizeid when '||v_row.sizeid||' then amount else 0 end) as size'||v_i;";
		qry += "\n          v_i:=v_i+1;";
		qry += "\n       end loop;";
		qry += "\n      end;";
		qry += "\n      select v_sqltext into :sumsql from dual;";
		qry += "\n   end;";
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("sumsql", new ProdParam(Types.VARCHAR));
		// 调用
		DbHelperSQL.ExecuteProc(qry, param);
		// 取返回值
		return param.get("sumsql").getParamvalue().toString();
	}

	// 获取绝对路径
	public static String getRootPath() {
		String rootPath = "";
		String classPath = pFunc.class.getClassLoader().getResource("/").getPath();
		File rootfile = new File(classPath).getParentFile().getParentFile().getParentFile();

		rootPath = rootfile.getPath();
		try {
			rootPath = URLDecoder.decode(rootPath, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rootPath + "/";

	}

	// 获取绝对路径
	public static String initRootPath() {
		String filePath = getRootPath();
		File file = new File(filePath + "log/");
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
			// System.out.println(file.getPath() + " 创建成功！ ");
			// response.getWriter().write(file.getPath() + " 创建成功！ \n");
		}
		file = new File(filePath + "img/");
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
			// System.out.println(file.getPath() + " 创建成功！ ");
		}
		file = new File(filePath + "imgbar/");
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
			// System.out.println(file.getPath() + " 创建成功！ ");
			// response.getWriter().write(file.getPath() + " 创建成功！ \n");
		}
		file = new File(filePath + "temp/");
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
			// System.out.println(file.getPath() + " 创建成功！ ");
			// response.getWriter().write(file.getPath() + " 创建成功！ \n");
		}

		return filePath;
	}

	

	

	

	// private static boolean saveFile(byte[] bytes, String fileName) {
	//
	// try {
	// //System.out.println("saveFile 1:" + fileName);
	//
	// FileWriter out = new FileWriter(fileName);
	//
	// String contents = new String(bytes);
	//
	// // System.arraycopy(bytes, 0, contents, 0, bytes.length);
	//
	// out.write(contents);
	//
	// out.close();
	// return true;
	// } catch (IOException e) {
	//
	// // TODO Auto-generated catch block
	//
	// e.printStackTrace();
	// return false;
	// }
	// }

	

	

	

	

	// * 取得当前日期所在周的最后一天

	

	/// <summary>
	/// 返回指定商品的尺码横向汇总sql语句
	/// </summary>
	/// <param name="wareid"></param>
	/// <returns></returns>
	// public static int qrySizesum(SizeParam sp) {
	// //System.out.println("qrySizesum begin");
	//
	// Map<String, ProdParam> param = new HashMap<String, ProdParam>();
	// param.put("v_wareid", new ProdParam(Types.BIGINT, sp.getWareid(), 1));//
	/// 输入参数
	// param.put("v_sizenum", new ProdParam(Types.INTEGER, sp.getSizenum(),
	/// 2));// 输入参数
	// param.put("v_sqlsum", new ProdParam(Types.VARCHAR, 3)); // 输出参数
	// param.put("v_sqlsize", new ProdParam(Types.VARCHAR, 4));// 输出参数
	//
	// String qry = "{call p_qrysizesum(?,?,?,?)}";
	// int ret = DbHelperSQL.ExecuteProcIn(qry, param); // 带输入参数
	// if (ret < 0) {
	// return 0;
	// }
	// sp.setSqlSum(param.get("v_sqlsum").getParamvalue().toString());
	// sp.setSqlSize(param.get("v_sqlsize").getParamvalue().toString());
	// return 1;
	// }

	/// <summary>
	/// 返回指定商品的尺码横向汇总sql语句,盘点专用
	/// </summary>
	/// <param name="wareid"></param>
	/// <returns></returns>
	// public static int qrySizesum1(SizeParam sp) {
	// //System.out.println("qrySizesum begin");
	//
	// Map<String, ProdParam> param = new HashMap<String, ProdParam>();
	// param.put("v_wareid", new ProdParam(Types.BIGINT, sp.getWareid(), 1));//
	/// 输入参数
	// param.put("v_sizenum", new ProdParam(Types.INTEGER, sp.getSizenum(),
	/// 2));// 输入参数
	// param.put("v_sqlsum", new ProdParam(Types.VARCHAR, 3)); // 输出参数
	// param.put("v_sqlsize", new ProdParam(Types.VARCHAR, 4));// 输出参数
	//
	// String qry = "{call p_qrysizesum1(?,?,?,?)}";
	// int ret = DbHelperSQL.ExecuteProcIn(qry, param); // 带输入参数
	// if (ret < 0) {
	// return 0;
	// }
	// sp.setSqlSum(param.get("v_sqlsum").getParamvalue().toString());
	// sp.setSqlSize(param.get("v_sqlsize").getParamvalue().toString());
	// return 1;
	// }

	/// <summary>
	/// 返回指定商品的尺码横向汇总sql语句,临时盘点专用
	/// </summary>
	/// <param name="wareid"></param>
	/// <returns></returns>
	// public static int qrySizesum2(SizeParam sp) {
	// //System.out.println("qrySizesum begin");
	//
	// Map<String, ProdParam> param = new HashMap<String, ProdParam>();
	// param.put("v_wareid", new ProdParam(Types.BIGINT, sp.getWareid(), 1));//
	/// 输入参数
	// param.put("v_sizenum", new ProdParam(Types.INTEGER, sp.getSizenum(),
	/// 2));// 输入参数
	// param.put("v_sqlsum", new ProdParam(Types.VARCHAR, 3)); // 输出参数
	// param.put("v_sqlsize", new ProdParam(Types.VARCHAR, 4));// 输出参数
	//
	// String qry = "{call p_qrysizesum2(?,?,?,?)}";
	// int ret = DbHelperSQL.ExecuteProcIn(qry, param); // 带输入参数
	// if (ret < 0) {
	// return 0;
	// }
	// sp.setSqlSum(param.get("v_sqlsum").getParamvalue().toString());
	// sp.setSqlSize(param.get("v_sqlsize").getParamvalue().toString());
	// return 1;
	// }

	// 生成二维码
	// @SuppressWarnings("unchecked")
	// public static String TwoBarcode(String bartext, int barsize) {
	//
	// // String text = bartext;
	// int width = barsize;
	// int height = barsize;
	// String format = "png";
	//
	// String filename = "imgbar/bar2_" + DateToStr(new Date(), "yyMMddHHmmss")
	// + "_" + getRandomNumC(6) + "." + format;
	//
	// String pathFile = getRootPath() + filename;
	//
	// // Hashtable hints = new Hashtable();
	// // @SuppressWarnings("rawtypes")
	// Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType,
	// String>();
	// hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
	// try {
	//
	// BitMatrix bitMatrix = new MultiFormatWriter().encode(bartext,
	// BarcodeFormat.QR_CODE, width, height, hints);
	//
	// File outputFile = new File(pathFile);
	// MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
	// } catch (WriterException | IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// pathFile = "";
	// }
	// return filename;
	//
	// }

	// 写日志
	public static void myWriteLog(long accid, String progname, String remark, String lastop) {
		if (remark == "")
			return;
		String qry = "begin ";
		qry += "    insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "    values (LOGRECORD_id.nextval," + accid + ",'" + progname + "','" + remark + "','" + lastop + "');";
		qry += " end;";

		DbHelperSQL.ExecuteSql(qry);

	}

	// 判断店铺是否有效
	public static boolean isWarehouseOk(long accid, long houseid) {
		if (houseid == 0)
			return false;
		String qry = "select houseid from warehouse where accid=" + accid + " and statetag=1 and houseid=" + houseid;
		return DbHelperSQL.Exists(qry);
	}

	// 判断商品是否有效
	public static boolean isWarecodeOk(long accid, long wareid) {
		if (wareid == 0)
			return false;

		String qry = "select wareid from warecode where accid=" + accid + " and statetag=1 and wareid=" + wareid;
		return DbHelperSQL.Exists(qry);
	}

	// 判断商品尺码是否有效
	public static boolean isWaresizeOk(long accid, long wareid, long sizeid) {
		if (wareid == 0)
			return false;

		String qry = "select a.sizeid from sizecode a join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno";
		qry += " where a.accid=" + accid + " and a.sizeid=" + sizeid + " and b.wareid=" + wareid;
		qry += " and b.statetag=1 and a.statetag=1";
		return DbHelperSQL.Exists(qry);
	}

	// 判断客户是否有效
	public static boolean isCustomerOk(long accid, long custid) {
		if (custid == 0)
			return false;
		String qry = "select custid from customer where accid=" + accid + " and statetag=1 and custid=" + custid;
		return DbHelperSQL.Exists(qry);
	}

	// 判断部门是否有效
	public static boolean isDepartmentOk(long accid, long dptid) {
		if (dptid == 0)
			return false;
		String qry = "select dptid from Department where accid=" + accid + " and statetag=1 and dptid=" + dptid;
		return DbHelperSQL.Exists(qry);
	}

	//	public static String getHttparam(HttpServletRequest request) {
	//		// PrintWriter writer = response.getWriter();
	//		Map<String, String[]> params = request.getParameterMap();
	//		String queryString = "";
	//		for (String key : params.keySet()) {
	//			String[] values = params.get(key);
	//			for (int i = 0; i < values.length; i++) {
	//				String value = values[i];
	//				queryString += key + "=" + value + " ";
	//			}
	//		}
	//		// 去掉最后一个空格
	//		// queryString = queryString.substring(0, queryString.length() - 1);
	//		return queryString;
	//		// writer.println("POST " + request.getRequestURL() + " " +
	//		// queryString);
	//	}

	// 取短信余额
	public static String getMessagebal(long accid, String smsuid, String smsuser, String smspwd) {
		if (smsuid.length() <= 0 || smsuser.length() <= 0 || smspwd.length() <= 0) {
			return "0短信账号无效！";
		}
		// http://ip:port/hy/m?uid=1234&auth=fcea920f7412b5da7be0cf42b8c93759
		String auth = Func.StrtoMD5(smsuser + smspwd).toLowerCase(); // MD5(企业代码+用户密码),32位加密小写
		String param = "uid=" + smsuid + "&auth=" + auth;// +
															// "&expid=0&encode=utf-8";//
															// &time=" + ss;
															// if (NetIsConn()) {
		String msg = Func.getHtmlFromUrl(url1 + "m", "GET", param);

		if (Integer.parseInt(msg) < 0)
			return "0操作失败:" + msg;
		else
			return "1" + msg;
		// if (msg.substring(0, 1).equals("0")) // 成功 smsrecord->tag:0=验证短信1=其它
		// {
		// return "1发送成功！";
		// } else {
		// return "0发送失败:" + msg.substring(1);
		// }
	}

	// 发验 短信
	public static String sendMessage(String smsuid, String smsuser, String smspwd, String phone, String content, String operant, long accid) {
		// if (smsuid.length() <= 0 || smsuser.length() <= 0 || smspwd.length()
		// <= 0) {
		// return "0短信账号无效！";
		// }

		if (Func.isNull(smsuid) || Func.isNull(smsuser) || Func.isNull(smspwd)) {
			return "0短信账号无效！";
		}
		if (!Func.isMobile(phone)) {
			return "0移动电话号码无效！";
		}
		// 蓝窗店管家用户uid：805351
		// 企业代码：flyang
		// 密：flyang2015
		// private String uid = "805351";//
		// ConfigurationManager.AppSettings["uid"].ToString();
		// private String auth =
		// Func.StrtoMD5("flyangflyang2015").toLowerCase(); //
		// MD5(企业代码+用户密码),32位加密小写

		if (content.length() <= 0) {
			return "0短信内容为空！";
		} else

			try {
				String auth = Func.StrtoMD5(smsuser + smspwd).toLowerCase(); // MD5(企业代码+用户密码),32位加密小写
				// String param = "uid=" + smsuid + "&auth=" + auth + "&mobile="
				// + phone + "&msg=" + content + "&expid=0&encode=utf-8";//
				// &time=" + ss;
				// String msg = Func.getHtmlFromUrl(url1, "GET", param);

				String param = "uid=" + smsuid + "&auth=" + auth + "&mobile=" + phone + "&msg=" + content + "&expid=0&encode=utf-8";// &time="
																																	// +
																																	// ss;
				String msg = Func.getHtmlFromUrl(url1, "POST", param);

				// System.out.println("2222" + msg);

				if (msg.substring(0, 1).equals("0")) // 成功
													// smsrecord->tag:0=验证短信1=其它
				{
					String qry = "insert into smsrecord (id,senddate,messstr,mobile,smspwd,operant,tag,accid) values (smsrecord_id.nextval,sysdate,'" + content + "','" + phone + "','***','" + operant + "',1," + accid
							+ ")";
					DbHelperSQL.ExecuteSql(qry);
					return "1发送成功！";
				} else {
					return "0发送失败1:" + msg.substring(1);
				}
			} catch (Exception e) {
				return "0发送失败2:" + e.getMessage();
				// System.out.println("发送GET请求出现异常！" + e);
				// e.printStackTrace();
			}

	}

	//	/**
	//	 * 字典排序参数
	//	 *
	//	 * @return
	//	 */
	//	public static String getSignData(HashMap<String, String> params) {
	//
	//		StringBuffer sb = new StringBuffer();
	//		List<String> keys = new ArrayList<String>(params.keySet());
	//		Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
	//		String value;
	//		for (String key : keys) {
	//			value = String.valueOf(params.get(key));
	//			if (sb.length() > 0) {
	//				sb.append("&" + key + "=" + value);
	//			} else {
	//				sb.append(key + "=" + value);
	//
	//			}
	//		}
	//		return sb.toString();
	//	}
	//
	//	// map 转json
	//	public static JSONObject getObjFrom(HashMap<String, String> map) {
	//		// TODO Auto-generated method stub
	//		JSONObject dataObj = JSONObject.fromObject("{}");
	//		Set<Entry<String, String>> set = map.entrySet();// Map.entrySet
	//														// 方法返回映射的
	//														// collection
	//														// 视图，其中的元素属于此类
	//		for (Entry<String, String> i : set) {// 将参数解析为"name=tom&age=21"的模式
	//			if (i != null) {
	//				if (i.getValue() != null) {
	//					String val = i.getValue();
	//					if (!val.equals("null"))
	//						dataObj.put(i.getKey(), val);
	//					// 参数格式要求更改
	//				}
	//			}
	//		}
	//		return dataObj;
	//	}
	//
	//	public static HashMap<String, String> getParamsMap(Map<String, String[]> params) throws IOException {
	//		HashMap<String, String> paramMap = new HashMap<String, String>();
	//		Set<Entry<String, String[]>> set = params.entrySet();// Map.entrySet
	//																// 方法返回映射的
	//																// collection
	//																// 视图，其中的元素属于此类
	//		for (Entry<String, String[]> i : set) {// 将参数解析为"name=tom&age=21"的模式
	//			if (i != null) {
	//				if (i.getValue() != null) {
	//					String[] val = i.getValue();
	//					String s = val.length == 0 ? "" : val[0];
	//					paramMap.put(i.getKey(), s);
	//					// 参数格式要求更改
	//				}
	//			}
	//		}
	//
	//		// if (DataBase.isCS)
	//		// System.out.println(paramMap.toString());
	//		return paramMap;
	//	}
	//
}
