package com.common.tool;

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

//import net.sf.json.JSONObject;
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
//import com.netdata.db.ProdParam;

//import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.WriterException;
//import com.google.zxing.common.BitMatrix;
import java.security.Key;
import java.sql.Timestamp;
//import java.util.Collections;
//import java.util.Map.Entry;

//import java.security.spec.AlgorithmParameterSpec;
//import org.apache.commons.io.IOUtils; 

public class Func {
	private static String url1 = "http://sms.10690221.com:9011/hy/";
	// http://ip:port/hy/m?uid=1234&auth=fcea920f7412b5da7be0cf42b8c93759

	private static String KEY_64 = "SkydiApp"; // VavicApp";//注意了，是8个字符，64位
	private static String IV_64 = "SkydiApp"; // VavicApp";
	private static String fast_conf_filename = getRootPath() + "fast_client.conf";// fastdfs配置文件
	private static String errmess;
	//	public static int MAX_SIZE = 15; // 最大尺码数

	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

	// 银货通常量
	//	public static final String FLYANGHOSTID = "118.178.229.131"; // 服务器id（测试）

	//	public static final String YHT_APP_KEY = "123e51b3eecf458a9ba4591b9542ee53";// 银货通接口key商户号
	//	public static final String YHT_APP_SECRET = "0a186c6c085640fea38d42ad23420b9701be2476ffe9ce348399b184a829591e";// 银货通接口secret商户密钥

	//	public static final String YHT_URL = "http://61.164.59.232:9093/yht-api-uzmd/";

	//	public static final String KEY_SALT_KEY = "_salt_key";

	//	public static final String KEY_TIMESTAMP = "_timestamp";

	/**
	 * base64编码
	 * 
	 * @param String
	 * @return String
	 */

	public static String Base64Encode(String str) {
		byte[] bstr = str.getBytes();
		return new sun.misc.BASE64Encoder().encode(bstr);
	}

	/**
	 * base64解码
	 * 
	 * @param string
	 * @return string
	 */

	public static String Base64Decode(String str) {
		byte[] bt = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
			//			WriteErrTxt("ERR1004:", e.getMessage());
			//			LogUtils.LogErrWrite("Base64UpFastDFS", errmess);
		}

		return new String(bt);
	}

	// String s = "fs123fdsa";//String变量
	//
	// byte b[] = s.getBytes();//String转换为byte[]
	//
	// String t = new String(b);//bytep[]转换为String

	// MD5加密
	public static String StrtoMD5(String value) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// e.printStackTrace();
			//			WriteErrTxt("ERR1005:", e.getMessage());
		}
		try {
			md5.update(value.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			//			WriteErrTxt("ERR1003:", e.getMessage());
		}
		byte[] m = md5.digest();
		return Byte2String(m).toUpperCase();// 要转成大写！
	}

	public static String Byte2String(byte[] b) {// 转16进制
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			int val = ((int) b[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

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

	public String aesEncrypt(String str, String key) {
		// System.out.println("aesEncrypt1.3 " + str + " " + key);
		if (str == null || key == null)
			return null;
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("UTF-8"), "AES"));
			byte[] bytes = cipher.doFinal(str.getBytes("UTF-8"));
			return new BASE64Encoder().encode(bytes).trim().replaceAll("\r\n", "").replaceAll("\n", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			//			WriteErrTxt("ERR1002:", e.getMessage());
		}
		return null;
	}

	public String aesDecrypt(String str, String key) {
		if (str == null || key == null)
			return null;
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("UTF-8"), "AES"));

			byte[] bytes = new BASE64Decoder().decodeBuffer(str);
			bytes = cipher.doFinal(bytes);
			return new String(bytes, "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//			WriteErrTxt("ERR1001:", e.getMessage() + " str=" + str + " key=" + key);
		}
		return null;
	}

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

	//	public static Date getServerdatetime() {
	//
	//		String qry = "Select To_Char(SYSDATE,'yyyy-mm-dd hh24:mi:ss') as serverdate from dual";
	//		Date date0 = new Date();
	//		String datestr = DbHelperSQL.RunSql(qry);
	//
	//		try {
	//			DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//			date0 = f.parse("1899-12-30 00:00:00");
	//			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datestr);
	//		} catch (Exception e) {
	//			// e.printStackTrace();
	//			WriteErrTxt("Err1006:", e.getMessage());
	//			return date0;
	//		}
	//		// return new Date();
	//	}

	public static String desDecode(String data) {
		if (data == null)
			return null;
		try {
			DESKeySpec dks = new DESKeySpec(KEY_64.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec(IV_64.getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			return new String(cipher.doFinal(hex2byte(data.getBytes())));
		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}

	} /// <summary>

	public static String desEncode(String data) {
		if (data == null)
			return null;
		try {
			DESKeySpec dks = new DESKeySpec(KEY_64.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec(IV_64.getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
			byte[] bytes = cipher.doFinal(data.getBytes());
			return byte2hex(bytes);
		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}
	}

	/**
	 * 二行制转字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp;
		for (int n = 0; b != null && n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1)
				hs.append('0');
			hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}

	private static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException();
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	public static String getCondition(String fieldname, String valuelist) {
		String sqltext = "";
		String orstr = "";
		String fh = "";
		// if (fs == 1)
		// fh = "'";
		if (valuelist.equals(""))
			return " (1=1)";

		if (!valuelist.substring(0, 1).equals("^")) // 只有一个值参数
		{
			sqltext = fieldname + " = " + fh + valuelist + fh;
			return sqltext;
		}
		int p = 0;
		// valuelist=valuelist.remove(0, 1);
		valuelist = valuelist.substring(1);

		while (valuelist.toString().length() > 0) {
			p = valuelist.indexOf("^");
			sqltext += orstr + fieldname + "=" + fh + valuelist.substring(0, p) + fh;
			orstr = " or ";
			// valuelist=valuelist.remove(0, p+1);
			valuelist = valuelist.substring(p + 1);
		}
		return "( " + sqltext + " )";
	}

	public static String getCondition(String fieldname, String valuelist, String fh) {
		String sqltext = "";
		String orstr = "";
		// String fh = "";
		// if (fs == 1)
		// fh = "'";
		if (valuelist.equals(""))
			return " (1=1)";

		if (!valuelist.substring(0, 1).equals("^")) // 只有一个值参数
		{
			sqltext = fieldname + " = " + fh + valuelist + fh;
			return sqltext;
		}
		int p = 0;
		// valuelist=valuelist.remove(0, 1);
		valuelist = valuelist.substring(1);

		while (valuelist.toString().length() > 0) {
			p = valuelist.indexOf("^");
			sqltext += orstr + fieldname + "=" + fh + valuelist.substring(0, p) + fh;
			orstr = " or ";
			// valuelist=valuelist.remove(0, p+1);
			valuelist = valuelist.substring(p + 1);
		}
		return "( " + sqltext + " )";
	}

	// 数字
	public static boolean isNumber(String input) {
		if (input.length() <= 0)
			return true;
		String regex = "^[0-9]+$";
		return match(regex, input);

		// if (input == "") return true;
		//
		// return System.Text.RegularExpressions.Regex.IsMatch(input,
		// @"^[0-9]+$");

	}

	// 数字和字母
	public static boolean isNumAndEnCh(String input) {

		if (input.length() <= 0)
			return true;
		String regex = "^[A-Za-z0-9]+$";
		return match(regex, input);

		// return false;

		// return System.Text.RegularExpressions.Regex.IsMatch(input,
		// @"^[A-Za-z0-9]+$");

	}

	/// 允许输入汉字，字母和数字

	public static boolean isNumAndEnHZCh(String input) {
		if (input.length() <= 0)
			return true;
		String regex = "^[A-Za-z0-9\\u4e00-\\u9fa5]+$";
		return match(regex, input);

		// if (input == "") return true;
		//
		// return System.Text.RegularExpressions.Regex.IsMatch(input,
		// @"^[A-Za-z0-9\u4e00-\u9fa5]+$");
		//
	}

	// 判断是否存在空格
	public static boolean isExistsSpace(String input) {
		if (input.length() <= 0)
			return true;
		String regex = "^[^ ]+$";
		return match(regex, input);

		// string reg = @"^[^ ]+$";
		// return System.Text.RegularExpressions.Regex.IsMatch(input, reg);
	}

	// /**
	// * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
	// * 此方法中前三位格式有：
	// * 13+任意数
	// * 15+除4的任意数
	// * 18+除1和4的任意数
	// * 17+除9的任意数
	// * 147
	// */

	// 判断是否有效的手机号码
	public static boolean isMobile(String input) {

		if (input.length() <= 0)
			return true;
		String regex = "^[1]+[3,4,5,7,8]+\\d{9}";
		// String regex =
		// "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
		return match(regex, input);

		// return System.Text.RegularExpressions.Regex.IsMatch(input,
		// @"^[1]+[3,4,5,7,8]+\d{9}");
	}

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

	// 判断是否有效的邮箱地址
	public static boolean isEmail(String input) {
		if (input.length() <= 0)
			return true;
		String regex = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
		return match(regex, input);
	}

	public static boolean isMatchCode(String input, String regex) {
		if (input.length() <= 0)
			return true;
		return match(regex, input);
	}

	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	//
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
	//
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

	/**
	 * 获取tomcat下webapp下项目的class路径
	 */
	public static String getRootPath() {
		String rootPath = "";
		String classPath = Func.class.getClassLoader().getResource("/").getPath();
		File rootfile = new File(classPath);
		rootPath = rootfile.getPath();
		try {
			rootPath = URLDecoder.decode(rootPath, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rootPath + "/";

	}

	/**
	 * 获取tomcat下temp路径
	 */
	public static String getWebPath() {
		String rootPath = "";
		String classPath = Func.class.getClassLoader().getResource("/").getPath();
		File rootfile = new File(classPath).getParentFile().getParentFile().getParentFile();

		rootPath = rootfile.getPath();
		try {
			rootPath = URLDecoder.decode(rootPath, "utf-8");
		} catch (UnsupportedEncodingException e) {
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

	// 产生指定位数的随机数字
	public static String getRandomNum(int len) {
		String[] baseStr = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
		// "a","b","c","d","e","A","B"};
		String str = "";
		for (int i = 0; i < len; i++) {
			int index = ((int) (100 * Math.random())) % baseStr.length;
			// System.out.println(index);
			str += baseStr[index];
		}
		return str;
	}

	// 产生指定位数的随机数字及字母
	public static String getRandomNumA(int len) {
		String[] baseStr = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
		// "a","b","c","d","e","A","B"};
		String str = "";
		for (int i = 0; i < len; i++) {
			int index = ((int) (100 * Math.random())) % baseStr.length;
			// System.out.println(index);
			str += baseStr[index];
		}
		return str;
	}

	// 产生指定位数的随机数字及字母及符号
	public static String getRandomNumC(int len) {
		String[] baseStr = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B",
				"C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "_", "-" };
		// "a","b","c","d","e","A","B"};
		String str = "";
		for (int i = 0; i < len; i++) {
			int index = ((int) (100 * Math.random())) % baseStr.length;
			// System.out.println(index);
			str += baseStr[index];
		}
		return str;
	}

	public static boolean delFile(String fileName) {
		File file = new File(fileName);
		// System.out.println("delfile:"+fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {

				// System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				// System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			// System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}

	// / <summary>
	// / 将流文件转换为图像文件并上传
	// / </summary>
	// / <returns></returns>
	public static String Base64UpFastDFS(String base64string) {
		// if (extname.equals("")) = "jpg";
		// String string = strBase64;
		// String fileName = "d:/gril2.gif"; // 生成的新文件
		// String pathFile = getRootPath() + "/temp/temp_" + getRandomNumC(10) +
		// ".jpg";// "banner0.png";
		// LogUtils.LogDebugWrite("Base64UpFastDFS", "begin");
		String pathFile = getRootPath() + "/temp/temp_" + DateToStr(new Date(), "yyMMddHHmmss") + "_" + getRandomNumC(5) + ".jpg";// "banner0.png";

		String retcs = "";
		byte[] bytes;
		try {
			bytes = new BASE64Decoder().decodeBuffer(base64string);
			FileOutputStream write = new FileOutputStream(new File(pathFile));
			write.write(bytes);
			write.close();
			// LogUtils.LogDebugWrite("Base64UpFastDFS", "1111");

			retcs = fastDFSUpload(pathFile);
			// LogUtils.LogDebugWrite("Base64UpFastDFS", "ok");
			// retcs="1"+fastFilename;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			errmess = "Err1032:" + e.getMessage();
			//			LogErrWrite("Base64UpFastDFS", errmess);
			LogUtils.LogErrWrite("Base64UpFastDFS", errmess);
			retcs = "0" + errmess;
			// pathFile = "";
		}

		delFile(pathFile);
		return retcs;
		// return UpFastDFS(pathfileName); //将图片上传到FastDFS
	}

	// fastdfs上传文件
	public static String fastDFSUpload(String local_filename) {
		// LogUtils.LogDebugWrite("fastDFSUpload ", local_filename);
		String retcs = "";
		int errid = 0;
		File f = new File(local_filename);
		String fileName = f.getName();
		String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
		fileName = fileName.substring(0, fileName.lastIndexOf("."));// 获取除后缀1位的名
		// LogUtils.LogDebugWrite("fastDFSUpload ", fast_conf_filename + "
		// fileName=" + fileName + " fileExtName=" + fileExtName);
		TrackerServer trackerServer = null;
		StorageServer storageServer = null;
		try {
			errid = 1;
			ClientGlobal.init(fast_conf_filename);
			errid = 2;
			TrackerClient tracker = new TrackerClient();
			errid = 3;
			trackerServer = tracker.getConnection();
			errid = 4;
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
			errid = 5;
			NameValuePair nvp[] = new NameValuePair[] { new NameValuePair("fileName", fileName), new NameValuePair("fileExtName", fileExtName) };
			errid = 6;
			// 设置元信息
			// NameValuePair nvp[] = new NameValuePair[] {};
			// NameValuePair[] nvp = new NameValuePair[3];
			// nvp[0] = new NameValuePair("fileName", tempFileName);
			// nvp[1] = new NameValuePair("fileExtName", fileExtName);
			// nvp[2] = new NameValuePair("fileLength",
			// String.valueOf(file.getSize()));
			// String fileIds[] = storageClient.upload_file(local_filename,
			// "png", nvp);
			String fileIds[] = storageClient.upload_file(local_filename, fileExtName, nvp);
			fileIds[0] = "image";
			errid = 7;
			retcs = "1" + fileIds[1];// fileIds[0]：组名 fileIds[1]：路径

		} catch (Exception e) {
			// e.printStackTrace();
			errmess = "Err1031:" + e.getMessage() + "  :" + errid;
			//			LogErrWrite("fastDFSUpload", errmess + "  " + local_filename);
			LogUtils.LogErrWrite("fastDFSUpload", errmess);
			retcs = "0" + errmess;// Err201:" + errid + "_" + e.getMessage();
		} finally {
			if (trackerServer != null)
				try {
					trackerServer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (storageServer != null)
				try {
					storageServer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return retcs;

	}

	// fastdfs下载文件
	// public static String fastDFSDownload(String fileName) {
	public static String fastDFSDownload(String local_filename) {
		// System.out.println("fastDFSDownload 0:" + local_filename);
		// return filename;
		TrackerServer trackerServer = null;
		StorageServer storageServer = null;
		try {
			ClientGlobal.init(fast_conf_filename);
			TrackerClient tracker = new TrackerClient();

			trackerServer = tracker.getConnection();
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
			byte[] b = storageClient.download_file("image", local_filename);
			// System.out.println("fastDFSDownload 1:" + local_filename);
			// System.out.println(b);
			// M00/00/E3/ooYBAFearLSAPQ6RAABp8miJoz4461.jpg
			// String filepath="temp/"+fileName.lastIndexOf("/");
			String filename = "temp/" + local_filename.substring(local_filename.lastIndexOf("/") + 1);// 获取除后缀1位的名
			// System.out.println("fastDFSDownload 2:" + filename);

			// IOUtils.write(b, new
			// FileOutputStream("D:/"+UUID.randomUUID().toString()+".conf"));
			// IOUtils.write(b, new FileOutputStream(getRootPath() + filename));
			FileOutputStream fs = new FileOutputStream(getRootPath() + filename);
			IOUtils.write(b, fs);
			fs.close();
			// if (!saveFile(b, getRootPath() + filename))
			// filename = "";

			return filename;
		} catch (Exception e) {
			// e.printStackTrace();
			errmess = "Err1030:" + e.getMessage();
			//			LogErrWrite("fastDFSDownload", errmess);
			LogUtils.LogErrWrite("fastDFSDownload", errmess);

			return "";
		} finally {
			if (trackerServer != null)
				try {
					trackerServer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (storageServer != null)
				try {
					storageServer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
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

	// fastdfs删除文件
	public static int fastDFSDelete(String fileName) {
		int ret = 0;
		StorageServer storageServer = null;
		TrackerServer trackerServer = null;
		try {
			// ClientGlobal.init(conf_filename);
			ClientGlobal.init(fast_conf_filename);

			TrackerClient tracker = new TrackerClient();

			trackerServer = tracker.getConnection();

			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
			if (storageClient.delete_file("image", fileName) == 0)
				ret = 1;
			// System.out.println(i == 0 ? "删除成功" : "删除失败:" + i);
		} catch (Exception e) {
			// e.printStackTrace();
			errmess = "Err1029:" + e.getMessage();
			//			LogErrWrite("fastDFSDelete", errmess);
			LogUtils.LogErrWrite("fastDFSDelete", errmess);
		} finally

		{
			if (trackerServer != null)
				try {
					trackerServer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (storageServer != null)
				try {
					storageServer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return ret;
	}

	/// <summary>
	/// //测试图片转base64字符串
	/// </summary>
	public static String testImagetobase64() {
		//		LogUtils.LogDebugWrite("testImagetobase64", "begin");
		String b64str = "";

		String imgFile = getRootPath() + "/img/test.jpg";// "banner0.png";
		File file = new File(imgFile);
		if (!file.exists()) { // 文件不存在
			// System.out.println("文件未找到:"+imgFile);
			return "";
		}

		//
		InputStream in = null;
		byte[] buf = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			buf = new byte[in.available()];
			in.read(buf);
			in.close();
			String ss = new BASE64Encoder().encode(buf);
			b64str = ss.trim().replaceAll("\r\n", "").replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", "");
			//			LogUtils.LogDebugWrite("testImagetobase64", "ok");
		} catch (IOException e) {
			e.printStackTrace();
			//			LogUtils.LogDebugWrite("testImagetobase64", "err");
		}
		return b64str;

	}

	// 第一个字母大写
	public static String getUpperOne(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	// 判断网络是否连接正常：
	public static boolean NetIsConn() {
		return true;
		// boolean connect = false;
		// Runtime runtime = Runtime.getRuntime();
		// Process process;
		// try {
		// process = runtime.exec("ping " + "www.baidu.com");
		// InputStream is = process.getInputStream();
		// InputStreamReader isr = new InputStreamReader(is);
		// BufferedReader br = new BufferedReader(isr);
		// String line = null;
		// StringBuffer sb = new StringBuffer();
		// while ((line = br.readLine()) != null) {
		// sb.append(line);
		// }
		// //System.out.println("返回值为:" + sb);
		// is.close();
		// isr.close();
		// br.close();
		//
		// if (null != sb && !sb.toString().equals("")) {
		// String logString = "";
		// if (sb.toString().indexOf("TTL") > 0) {
		// // 网络畅通
		// connect = true;
		// } else {
		// // 网络不畅通
		// connect = false;
		// }
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// return connect;
	}

	// 取指四舍五入
	public static double getRound(double f, int prec) {

		BigDecimal d = new BigDecimal(f);

		double e = d.setScale(2, RoundingMode.HALF_UP).doubleValue();
		return e;
		// if (prec > 4)
		// prec = 4;
		// else if (prec < 0)
		// prec = 0;
		// float jd = prec * 100;// (这里的100就是2位小数点,如果要其它位,如4位,这里两个100改成10000)
		// return (float) (Math.round(value * jd)) / jd;
	}

	// 取指页数
	public static Integer getPagecount(int rows, int total) {
		int pagecount = 0;
		while (total > 0) {
			pagecount += 1;
			total -= rows;
		}
		return pagecount;
	}

	// 取指四舍五入
	public static float getRound(float f, int prec) {

		BigDecimal d = new BigDecimal(f);

		double e = d.setScale(2, RoundingMode.HALF_UP).doubleValue();
		return (float) e;
		// if (prec > 4)
		// prec = 4;
		// else if (prec < 0)
		// prec = 0;
		// float jd = prec * 100;// (这里的100就是2位小数点,如果要其它位,如4位,这里两个100改成10000)
		// return (float) (Math.round(value * jd)) / jd;
	}

	// 日期转字符
	public static String DateToStr(Date date) {
		return DateToStr(date, "yyyy-MM-dd");
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// String str = df.format(date);
		// return str;
	}

	// 日期转字符
	public static String DateToStr(Date date, String pict) {

		SimpleDateFormat df = new SimpleDateFormat(pict);
		String str = df.format(date);
		return str;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {
		return StrToDate(str, "yyyy-MM-dd");
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// Date date = null;
		// try {
		// date = df.parse(str);
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		// return date;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str, String pict) {

		SimpleDateFormat df = new SimpleDateFormat(pict);
		Date date = null;
		try {
			date = df.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	// 取指定日期后一天
	public static Date getNextDay(Date date) {

		// System.out.println("getNextDay begin");
		// System.out.println(DateToStr(date));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.add(Calendar.DATE, 1); // 得到后一天
		// date = calendar.getTime();
		return calendar.getTime();
	}

	// 取指定日期后一天
	public static Date getNextDay(String datestr) {
		Date date = StrToDate(subString(datestr, 1, 10));
		return getNextDay(date);
	}

	// 取指定日期前一天
	public static Date getPrevDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(Calendar.DATE, -1); // 得到前一天
		// Date date = calendar.getTime();
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// System.out.println(df.format(date));
		return calendar.getTime();
	}

	// 取指定日期前一天
	public static Date getPrevDay(String datestr) {
		Date date = StrToDate(subString(datestr, 1, 10));
		return getPrevDay(date);
	}

	// 取指定日期后n天
	public static Date getDataAdd(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(Calendar.DATE, days); // 得到前一天
		// Date date = calendar.getTime();
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// System.out.println(df.format(date));
		return calendar.getTime();
	}

	// 取指定日期后n天
	public static Date getDataAdd(String datestr, int days) {
		Date date = StrToDate(subString(datestr, 1, 10));
		return getDataAdd(date, days);
	}

	// 需要注意的是：月份是从0开始的，比如说如果输入5的话，实际上显示的是4月份的最后一天，千万不要搞错了哦
	// 指定年月 的最后一天
	public static Date getLastDayOfMonth(int year, int month) {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		return cal.getTime();
	}

	// 指定日期所在月 的最后一天
	public static Date getLastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR); // 获取年
		int month = cal.get(Calendar.MONTH) + 1; // 获取月份，0表示1月份
		return getLastDayOfMonth(year, month);
	}

	// 指定年月 的第一天
	public static Date getFirstDayOfMonth(int year, int month) {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
		return cal.getTime();
	}

	// 指定日期所在月 的第一天
	public static Date getFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR); // 获取年
		int month = cal.get(Calendar.MONTH) + 1; // 获取月份，0表示1月份
		return getFirstDayOfMonth(year, month);
	}

	// 指定日期所在年 的第一天
	public static Date getFirstDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR); // 获取年
		int month = 0;// cal.get(Calendar.MONTH) + 1; // 获取月份，0表示1月份
		return getFirstDayOfMonth(year, month);
	}

	// 指定日期所在周 的第一天
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		// int m = c.get(Calendar.MONTH);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday

		// //下面的代码作用是如果第一天的日期是上一个月的话，就把这周的第一天设成本月一号
		// if(c.get(Calendar.MONTH) < m ){
		// // 如果月份小于最初给定的月份了的话
		// c.add(Calendar.MONTH, 1);//月份加1
		// c.set(Calendar.DAY_OF_MONTH, 1);//日期改为1号
		return c.getTime();
	}

	// * 取得当前日期所在周的最后一天

	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();

		c.setFirstDayOfWeek(Calendar.MONDAY);

		// int m = c.get(Calendar.MONTH);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday

		// //下面的代码作用是如果最后一天的日期是下一个月的话，就把这周的最后一天设成本月一号最后一天
		// while(c.get(Calendar.MONTH) > m){
		// // 如果月份大于了最初给定的月份了的话
		// c.add(Calendar.DAY_OF_MONTH, -1);//日期循环减少一天，直到月份是本月为止
		// }
		return c.getTime();
	}

	// 两个日期相差的天数
	public static int getBetweenDays(Date mindate, Date maxdate) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(mindate);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(maxdate);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) // 不同年
		{
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
				{
					timeDistance += 366;
				} else // 不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else // 同年
		{
			// System.out.println("判断day2 - day1 : " + (day2 - day1));
			return day2 - day1;
		}
	}

	// 两个日期相差的天数
	public static int getBetweenDays(String mindatestr, String maxdatestr) {
		Date mindate = StrToDate(subString(mindatestr, 1, 10));
		Date maxdate = StrToDate(subString(maxdatestr, 1, 10));
		return getBetweenDays(mindate, maxdate);
	}
	// /**
	// * * 获得指定日期的前一天 *
	// *
	// * @param specifiedDay
	// *
	// * @return
	// *
	// * @throws Exception
	// */
	//
	// public static String getSpecifiedDayBefore(String specifiedDay) {
	//
	// SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//
	// Calendar c = Calendar.getInstance();
	//
	// Date date = null;
	//
	// try {
	//
	// date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
	//
	// } catch (ParseException e) {
	//
	// e.printStackTrace();
	//
	// }
	//
	// c.setTime(date);
	// int day = c.get(Calendar.DATE);
	//
	// c.set(Calendar.DATE, day - 1);
	//
	// String dayBefore = new
	// SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	//
	// return dayBefore;
	//
	// }
	//
	// /**
	// *
	// * 获得指定日期的后一天
	// *
	// * @param specifiedDay
	// *
	// * @return
	// *
	// */
	//
	// public static String getNextDay(String specifiedDay) {
	//
	// Calendar c = Calendar.getInstance();
	//
	// Date date = null;
	//
	// try {
	//
	// date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
	//
	// } catch (ParseException e) {
	//
	// e.printStackTrace();
	//
	// }
	//
	// c.setTime(date);
	//
	// int day = c.get(Calendar.DATE);
	//
	// c.set(Calendar.DATE, day + 1);
	//
	// String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	//
	// return dayAfter;
	//
	// }

	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getMACAddress(String ip) {
		String str = "";
		String macAddress = "";
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return macAddress;
	}

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
	//	public static void myWriteLog(long accid, String progname, String remark, String lastop) {
	//		if (remark == "")
	//			return;
	//		String qry = "begin ";
	//		qry += "    insert into LOGRECORD (id,accid,progname,remark,lastop)";
	//		qry += "    values (LOGRECORD_id.nextval," + accid + ",'" + progname + "','" + remark + "','" + lastop + "');";
	//		qry += " end;";
	//
	//		DbHelperSQL.ExecuteSql(qry);
	//
	//	}

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

	// 指定位置开始截取指定长度的字符串
	public static String subString(String input, int index, int count) {
		if (input.equals("")) {
			return "";
		}
		int len = input.length();
		count = (count > len - index + 1) ? len - index + 1 : count;
		return input.substring(index - 1, index + count - 1);
	}

	// 从右边取指定长度字符串
	public static String strRight(String input, int count) {
		if (input.equals("")) {
			return "";
		}
		int len = input.length();
		count = (count <= len) ? count : len;
		return input.substring(len - count, len);
	}

	// 从左边取指定长度字符串
	public static String strLeft(String input, int count) {
		if (input.equals("")) {
			return "";
		}
		return subString(input, 1, count);
	}

	// 去除group by 中的as
	// group by a.noteno,to_char(a.notedate,'yyyy-MM-dd') as date ,a.houseid
	// group by a.noteno,to_char(a.notedate,'yyyy-MM-dd') as date
	// ,to_char(a.notedate,'hh4:mi:ss') as time
	public static String delGroupOfAs(String grouplist) {
		String grouplist0 = "";
		int p = grouplist.toUpperCase().indexOf(" AS ");

		while (p > 0) {

			grouplist0 += subString(grouplist, 1, p);
			// System.out.println("1111:" + grouplist0);
			grouplist = grouplist.substring(p + 1);
			// System.out.println("2222:" + grouplist);
			int i = grouplist.toUpperCase().indexOf(",");
			if (i > 0) {
				grouplist = grouplist.substring(i);
				// System.out.println("3333:" + grouplist);
			} else {
				grouplist = "";
			}
			p = grouplist.toUpperCase().indexOf(" AS ");
		}
		grouplist0 += grouplist;
		// System.out.println("ok:" + grouplist0);

		return grouplist0;
	}

	public static String FormatNumber(Float value) {

		return FormatNumber(value, "###");
	}

	public static String FormatNumber(Double value) {

		return FormatNumber(value, "###");
	}

	public static String FormatNumber(Float value, int d) {

		String pict = "###0"; // ("#,##0.00");//
		if (d > 0) {
			if (d > 4)
				d = 4;
			pict += "." + subString("000000", 1, d);
		}
		// System.out.println("FormatNumber: " + pict);
		return FormatNumber(value, pict);
	}

	public static String FormatNumber(Double value, int d) {

		String pict = "###0"; // ("#,##0.00");//
		if (d > 0) {
			if (d > 4)
				d = 4;
			pict += "." + subString("000000", 1, d);
		}
		// System.out.println("FormatNumber: " + pict);
		return FormatNumber(value, pict);
	}

	public static String FormatNumber(Float value, String pict) {
		DecimalFormat df = new DecimalFormat(pict);
		return df.format(value);
	}

	public static String FormatNumber(Double value, String pict) {
		DecimalFormat df = new DecimalFormat(pict);
		return df.format(value);
	}

	// 判断pos传入的数据是否有危险操作
	public static String HttpIsDanger(String hpptstr) {
		String[] keylist = { "update", "delete", "drop", "truncate", "alter", "rename", "insert" };
		hpptstr = hpptstr.toLowerCase();
		for (int i = 0; i < keylist.length; i++) {
			String keystr = keylist[i];
			if (hpptstr.contains(" " + keystr + " ") || hpptstr.contains(";" + keystr + " ")) {
				return keylist[i];
			}
		}
		return "";
	}

	// 判断是否为空
	public static boolean isNull(String Value) {
		if (Value == null)
			return true;
		if (Value.length() <= 0)
			return true;
		else
			return false;
	}

	// 判断是否为空或为零
	public static boolean isNull(Float Value) {
		if (Value == null)
			return true;
		if (Value == 0)
			return true;
		else
			return false;
	}

	// 判断是否为空或为零
	public static boolean isNull(Long Value) {
		if (Value == null)
			return true;
		if (Value == 0)
			return true;
		else
			return false;
	}

	// 判断是否为空或为零
	public static boolean isNull(Integer Value) {
		if (Value == null)
			return true;
		if (Value == 0)
			return true;
		else
			return false;
	}

	// 判断字符是否有效日期
	public static boolean isValidDate(String str) {
		boolean convertSuccess = true;// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
					//			format.setLenient(false);
			format.parse(str);
			//             * 判断日期格式和范围
			String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
			Pattern pat = Pattern.compile(rexp);
			Matcher mat = pat.matcher(str);
			convertSuccess = mat.matches();
		} catch (ParseException e) {
			//		} catch (Exception e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}

	// 判断两个double是否相等
	public static boolean isEqual(double a, double b) {
		if (Math.abs(a - b) < 0.00000001)
			return true;
		else
			return false;
	}

	// 判断两个float是否相等
	public static boolean isEqual(float a, float b) {
		if (Math.abs(a - b) < 0.00000001)
			return true;
		else
			return false;
	}

	/**
	 * 获取字符串的长度，如果有中文，则每个中文字符计为2位
	 * 
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 */
	public static int getLength(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}

	public static String getHtmlFromUrl(String url, String method, String param) {

		// WriteDebugTXT("", url1);
		method = method.toUpperCase();
		// String strResult = "";
		String result = "";
		if (method.equals("POST")) {
			// OutputStreamWriter out = null;
			OutputStream out = null;
			BufferedReader in = null;
			try {
				URL realUrl = new URL(url);
				HttpURLConnection conn = null;
				conn = (HttpURLConnection) realUrl.openConnection();
				// 打开和URL之间的连接

				// 发送POST请求必须设置如下两行
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setRequestMethod("POST"); // POST方法

				// 设置通用的请求属性

				conn.setRequestProperty("accept", "*/*");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				// conn.setRequestProperty("Content-Type",
				// "application/x-www-form-urlencoded");
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
				// 希奥信息&陈泉霖

				conn.connect();

				out = conn.getOutputStream();
				out.write(param.getBytes("UTF-8"));// 发送参数内容

				out.flush();
				// 定义BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			} catch (Exception e) {
				System.out.println("发送 POST 请求出现异常！" + e);
				e.printStackTrace();
			}
			// 使用finally块来关闭输出流、输入流
			finally {
				try {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

		} else if (method.equals("GET")) {
			// String result = "";
			BufferedReader in = null;
			try {
				String urlNameString = url + "?" + param;
				URL realUrl = new URL(urlNameString);
				// System.out.println(urlNameString);
				// 打开和URL之间的连接
				URLConnection connection = realUrl.openConnection();
				// 设置通用的请求属性
				connection.setRequestProperty("accept", "*/*");
				connection.setRequestProperty("connection", "Keep-Alive");
				connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				// 建立实际的连接
				connection.connect();
				// 获取所有响应头字段
				// Map<String, List<String>> map = connection.getHeaderFields();
				// 遍历所有的响应头字段
				// for (String key : map.keySet()) {
				// System.out.println(key + "--->" + map.get(key));
				// }
				// 定义 BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			} catch (Exception e) {
				System.out.println("发送GET请求出现异常！" + e);
				e.printStackTrace();
			}
			// 使用finally块来关闭输入流
			finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return result;
	}

	// 取短信余额
	public static String getMessagebal(long accid, String smsuid, String smsuser, String smspwd) {
		if (smsuid.length() <= 0 || smsuser.length() <= 0 || smspwd.length() <= 0) {
			return "0短信账号无效！";
		}
		// http://ip:port/hy/m?uid=1234&auth=fcea920f7412b5da7be0cf42b8c93759
		String auth = StrtoMD5(smsuser + smspwd).toLowerCase(); // MD5(企业代码+用户密码),32位加密小写
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

		if (isNull(smsuid) || isNull(smsuser) || isNull(smspwd)) {
			return "0短信账号无效！";
		}
		if (!isMobile(phone)) {
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
				String auth = StrtoMD5(smsuser + smspwd).toLowerCase(); // MD5(企业代码+用户密码),32位加密小写
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

	// 获取unix时间戳
	public static Long getUnixTime() {
		Timestamp appointTime = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = null;
		try {
			date = df.parse(String.valueOf(appointTime));

		} catch (Exception e) {
			e.printStackTrace();
		}
		long s = date.getTime();
		return s;
	}

	/*
	 * 获得内网IP
	 * 
	 * @return 内网IP
	 */
	public static String getLocaleIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获得外网IP
	 * @return 外网IP
	 */
	public static String getInternetIp() {
		try {
			Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			Enumeration<InetAddress> addrs;
			while (networks.hasMoreElements()) {
				addrs = networks.nextElement().getInetAddresses();
				while (addrs.hasMoreElements()) {
					ip = addrs.nextElement();
					if (ip != null && ip instanceof Inet4Address && ip.isSiteLocalAddress() && !ip.getHostAddress().equals(getLocaleIp())) {
						return ip.getHostAddress();
					}
				}
			}

			// 如果没有外网IP，就返回内网IP
			return getLocaleIp();
		} catch (Exception e) {
			throw new RuntimeException(e);
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
