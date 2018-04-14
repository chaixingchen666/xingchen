package tools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

public class AppData {
	public String accid, epid, accesskey, qxpublic = "", rolepublic = "", accpublic = "", epname, comid, epno, psw,
			houseid, housename, msg = "", levelid, balcurr, sizenum, levelname, deviceno, msgid, locked, accdate,autowareno,
			pagecount = "20", qxcs;
	public StringBuffer uploadres = new StringBuffer();
	public static String versionname;
	public String showfooter, sqltext;
	public String foot = "";// 存储
	public Map<String, String> mapjson = new HashMap<String, String>();;// 存储houseid和
																		// housename
	public Map<String, String> mapfoot = new HashMap<String, String>();;// 存储
	public static Map<String, String> mapjsfs = new HashMap<String, String>();// 存储结算方式
	public static Map<String, String> mapjsje = new HashMap<String, String>();;// 存储结算金额
	// 权限参数设置
	public String qxkz;// 启用权限控制
	public String allowfsck;// 允许负数出库
	public String allownearsale;// 启用最近售价
	public String points;// 单价小数位数（0,1,2）
	public String centbl;// 积分抵扣比例
	public String allowinsale;// 允许查看进价
	public String allowshare;// 允许分析
	public String allowprymyj;// 允许打印页眉页脚
	public String allowchedan;// 允许撤单
	public int xsamt = 0;// 销量的合计
	public int xscurr = 0;// 销额的合计
	public int kcamt = 0;// 库存的合计
	public int kchj = 0;// 小计的合计

	public void setqxpublic(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String skystr = "{}";
		Cookie cookie[] = request.getCookies();
		for (Cookie ck : cookie) {
			String name = ck.getName();
			String value = ck.getValue();
			if (name.equals("SKYSTR")) {
				skystr = DataBase.getStringfromBase64(value);
			}
			if (name.equals("PAGECOUNT")) {
				pagecount = value.equals("") || value == null ? "20" : value;
				pagecount = Integer.valueOf(pagecount) <= 10 ? "10" : pagecount;
				pagecount = Integer.valueOf(pagecount) >= 50 ? "50" : pagecount;
			}
		}
		JSONObject skydserobj = JSONObject.fromObject(skystr);
		@SuppressWarnings("unchecked")
		Iterator<String> it = skydserobj.keys();
		while (it.hasNext()) {
			String key = it.next().toString();
			String value = skydserobj.getString(key);
			switch (key) {
			case "ACCID":
				accid = value;
				break;
			case "EPID":
				epid = value;
				break;
			case "COMID":
				comid = value;
				break;
			case "BALCURR":
				balcurr = value;
				break;
			case "ACCESSKEY":
				accesskey = value;
				break;
			case "QXPUBLIC":
				qxpublic = value;
				break;
			case "ROLEPUBLIC":
				rolepublic = value;
				break;
			case "ACCPUBLIC":
				accpublic = value;
				break;
			case "QXCS":
				qxcs = value;
				break;
			case "EPNAME":
				epname = DataBase.decode(value);
				break;
			case "ACCDATE":
				accdate = DataBase.decode(value);
				break;
			case "EPNO":
				epno = value;
				break;
			case "DEVICENO":
				deviceno = value;
				break;
			case "GLEVELID":
				levelid = value;
				break;
			case "LEVELNAME":
				levelname = DataBase.decode(value);
				break;
			case "PSW":
				psw = value;
				break;
			case "HOUSEID":
				houseid = value;
				break;
			case "HOUSENAME":
				housename = DataBase.decode(value);
				break;
			case "LOCKED":
				locked = value;
				break;
			case "SIZENUM":
				sizenum = value.equals("") ? "0" : value;
				sizenum = Integer.valueOf(sizenum) <= 5 ? "5" : sizenum;
				sizenum = Integer.valueOf(sizenum) >= 15 ? "15" : sizenum;
				break;
			// case "PAGECOUNT":
			// pagecount = value.equals("")||value==null?"20":value;
			// pagecount = Integer.valueOf(pagecount)<=10?"10":pagecount;
			// pagecount = Integer.valueOf(pagecount)>=50?"50":pagecount;
			// break;

			default:
				break;
			}
		}
		getqxpublic();
	}

	public void setqxpublic(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		String skystr = "{}";
		Cookie cookie[] = request.getCookies();
		for (Cookie ck : cookie) {
			String name = ck.getName();
			String value = ck.getValue();
			if (name.equals("SKYSTR")) {
				skystr = DataBase.getStringfromBase64(value);
			}
			if (name.equals("PAGECOUNT")) {
				pagecount = value.equals("") || value == null ? "20" : value;
				pagecount = Integer.valueOf(pagecount) <= 10 ? "10" : pagecount;
				pagecount = Integer.valueOf(pagecount) >= 50 ? "50" : pagecount;
			}
		}
		JSONObject skydserobj = JSONObject.fromObject(skystr);
		@SuppressWarnings("unchecked")
		Iterator<String> it = skydserobj.keys();
		while (it.hasNext()) {
			String key = it.next().toString();
			String value = skydserobj.getString(key);
			switch (key) {
			case "ACCID":
				accid = value;
				break;
			case "EPID":
				epid = value;
				break;
			case "COMID":
				comid = value;
				break;
			case "BALCURR":
				balcurr = value;
				break;
			case "ACCESSKEY":
				accesskey = value;
				break;
			case "QXPUBLIC":
				qxpublic = value;
				break;
			case "ROLEPUBLIC":
				rolepublic = value;
				break;
			case "ACCPUBLIC":
				accpublic = value;
				break;
			case "QXCS":
				qxcs = value;
				break;
			case "EPNAME":
				epname = DataBase.decode(value);
				break;
			case "ACCDATE":
				accdate = DataBase.decode(value);
				break;
			case "EPNO":
				epno = value;
				break;
			case "DEVICENO":
				deviceno = value;
				break;
			case "GLEVELID":
				levelid = value;
				break;
			case "LEVELNAME":
				levelname = DataBase.decode(value);
				break;
			case "PSW":
				psw = value;
				break;
			case "HOUSEID":
				houseid = value;
				break;
			case "HOUSENAME":
				housename = DataBase.decode(value);
				break;
			case "LOCKED":
				locked = value;
				break;
			case "SIZENUM":
				sizenum = value.equals("") ? "0" : value;
				sizenum = Integer.valueOf(sizenum) <= 5 ? "5" : sizenum;
				sizenum = Integer.valueOf(sizenum) >= 15 ? "15" : sizenum;
				break;
			case "PAGECOUNT":
				pagecount = value.equals("") || value == null ? "20" : value;
				pagecount = Integer.valueOf(pagecount) <= 10 ? "10" : pagecount;
				pagecount = Integer.valueOf(pagecount) >= 50 ? "50" : pagecount;
				break;
			default:
				break;
			}
		}
		getqxpublic();
	}

	public void clearCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			cookie.setValue(null);
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
	}

	public boolean isResult(String jsonStr) {// 是否有result返回
		try {
			if (!jsonStr.equals(null)) {
				if (jsonStr.contains("result")) {
					try {
						JSONObject jsonObj = JSONObject.fromObject(jsonStr);
						if (jsonObj.getString("result").equals("0")) {
							msg = jsonObj.getString("msg");
						}
						return !jsonObj.getString("result").equals("0");
					} catch (Exception e) {
						msg = "数据异常！";
						return false;
					}
				} else if (jsonStr.contains("syserror")) {
					msg = "sys";
					return false;
				} else if (jsonStr.contains("fwq")) {
					msg = "fwq";
					return false;
				} else {
					return true;
				}
			} else {
				msg = "网络异常！";
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean valideResData(JSONObject resobj) {// 是否有result返回
		try {
			if (resobj.has("syserror")) {
				msg = resobj.getString("syserror");
				return false;
			} else if (resobj.has("result")) {
				if (resobj.has("msg"))
					msg = resobj.getString("msg");
				if (Integer.parseInt(resobj.getString("result")) > 0) {
					return true;
				} else {
					return false;
				}
			} else if (resobj.has("total")) {
				return true;
			} else
				return true;
		} catch (Exception e) {
			msg = "valideResData处理数据异常！";
			e.printStackTrace();
			return false;
		}
	}

	public boolean isSyserror(String jsonStr) {
		// TODO 是否返回签名异常
		try {
			if (!jsonStr.equals(null)) {
				if (jsonStr.contains("syserror")) {
					msg = "sys";
					return false;
				} else if (jsonStr.contains("fwq")) {
					msg = "fwq";
					return false;
				} else {
					return true;
				}
			} else {
				msg = "网络异常！";
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isTotal(String jsonStr) {// 是否有total返回
		try {
			if (!jsonStr.equals(null)) {
				if (jsonStr.contains("total")) {
					return true;
				} else if (jsonStr.contains("syserror")) {
					msg = "sys";
					return false;
				} else if (jsonStr.contains("fwq")) {
					msg = "fwq";
					return false;
				} else {
					return isResult(jsonStr);
				}
			} else {
				msg = "网络异常！";
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void getqxpublic() {
		if (qxpublic.length() > 0) {
			qxkz = String.valueOf(qxpublic.charAt(0));
			allowfsck = String.valueOf(qxpublic.charAt(1));
			allownearsale = String.valueOf(qxpublic.charAt(2));
			points = String.valueOf(qxpublic.charAt(3));
			autowareno = String.valueOf(qxpublic.charAt(11));
			centbl = qxpublic.substring(4, 8);
			allowinsale = String.valueOf(rolepublic.charAt(0));
			allowshare = String.valueOf(rolepublic.charAt(1));
			allowprymyj = String.valueOf(rolepublic.charAt(3));
			allowchedan = String.valueOf(rolepublic.charAt(6));
		}
	}

	public boolean isResult(JSONObject dataObj) {
		// TODO Auto-generated method stub
		try {
			if (!dataObj.equals(null)) {
				if (dataObj.has("result")) {
					if("1".equals(dataObj.getString("result")))
					return true;
					return false;
				} else
					return false;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
