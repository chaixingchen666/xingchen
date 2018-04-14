package mains;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;

public class MainBase {
	public static String page = "1";

	public static String getqxpublic(AppData appData) {
		String url = DataBase.HostIP + "getallqxpublic" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isSyserror(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String writeqxpublic(String location, String value, AppData appData) {
		String url = DataBase.HostIP + "writeqxpublic&accid=" + appData.accid + "&locate=" + location + "&value=" + value
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return "t";
		} else {
			return jsonstr;
		}
	}

	public static String getfirstpage(AppData appData) {
		// TODO 获取首页
		String url = DataBase.HostIP + "listfirstpage" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}
	// 增加我的首页功能(pc版专用)
	// http://119.84.84.173:94/interface/ebdress.aspx?action=addfirstpage&progid=[程序id]
	//
	// 删除我的首页功能(pc版专用)
	// http://119.84.84.173:94/interface/ebdress.aspx?action=delfirstpage&progid=[程序id]

	public static String addfirstpage(String progid, AppData appData) {
		String url = DataBase.HostIP + "addfirstpage&progid=" + progid + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return "t";
		} else {
			return jsonstr;
		}
	}

	public static String delfirstpage(String progid, AppData appData) {
		String url = DataBase.HostIP + "delfirstpage&progid=" + progid + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return "t";
		} else {
			return jsonstr;
		}
	}

	public static String devicevalid(String progid, AppData appData)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String url = DataBase.HostIP + "devicevalid&epid=" + appData.epid + "&progid=" + progid + "&deviceno="
				+ appData.deviceno + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return "t";
		} else {
			return jsonstr;
		}
	}


	public static String getcominfo(AppData appData) {
		// TODO 获取账户信息
		String url = DataBase.HostIP + "getaccregbyid&accid=" + appData.accid
				+ "&fieldlist=accid,accname,regdate,mobile,company,linkman,address,email,tel,begindate"
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String updatecominfo(HashMap<String, String> params, AppData appData) {
		// TODO 更新账户信息
		String url = DataBase.HostIP + "updateaccregbyid&accid=" + appData.accid + DataBase.signature(appData);
		String jsonstr = DataBase.postJsonContent(url, params);
		if (appData.isResult(jsonstr)) {
			return "t";
		} else {
			return jsonstr;
		}
	}
	public static String addaccreg(String smspwd,HashMap<String, String> params, AppData appData) {
		// TODO 更新账户信息
		String url = DataBase.HostIP + "addaccreg&smspwd="+smspwd+"&md5=1";
		return DataBase.postJsonContent(url, params);
	}
	public static String accnamevalid(String accname, AppData appData) {
		// TODO 更新账户信息
		String url = DataBase.HostIP + "accnamevalid&accname="+accname + DataBase.signature(appData);
		return DataBase.getJsonContent(url);
	}

	public static String changebegindate(String date, AppData appData) {
		// TODO 更改建账日期
		String url = DataBase.HostIP + "changeaccbegindate&accid=" + appData.accid + "&begindate=" + date
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return "t";
		} else {
			return jsonstr;
		}
	}

	public static String sendsuggest(HashMap<String, String> params, AppData appData) {
		// TODO 发送反馈
		String url = DataBase.HostIP + "adduserback&epid=" + appData.epid + DataBase.signature(appData);
		String jsonstr = DataBase.postJsonContent(url, params);
		if (appData.isResult(jsonstr)) {
			return "t";
		} else {
			return jsonstr;
		}
	}

	public static String getsysnotice(AppData appData) {
		// TODO 获取系统公告
		String url = DataBase.HostIP + "sysnoticelist&page=" + page + "&rows=" + appData.pagecount + "&epid=" + appData.epid
				+ "&fieldlist=ggid,noticdate,topical,content" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String getmsg(AppData appData) {
		// TODO 获取系统公告
		String url = DataBase.HostIP + "messstate&accid=" + appData.accid + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String setsysnotice(String ggid, AppData appData) {
		// TODO 设置系统公告
		String url = DataBase.HostIP + "writesysnotic&ggid=" + ggid + "&epid=" + appData.epid + "&fieldlist=*"
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return "t";
		} else {
			return jsonstr;
		}
	}

	public static String setaccstg(String stg, AppData appData) {
		// TODO 设置系统公告
		String url = DataBase.HostIP + "changeaccregusetag&usetag=" + stg + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return "t";
		} else {
			return jsonstr;
		}
	}

	public static String getdefaultcust(AppData appData) {
		// TODO 获取账户默认客户
		String url = DataBase.HostIP + "getdefaultcust" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String setdefaultcust(String custid, AppData appData) {
		// TODO 设置默认客户
		String url = DataBase.HostIP + "writedefaultcust&custid=" + custid + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return "t";
		} else {
			return jsonstr;
		}
	}

	public static String setdefaultsize(String groupno, AppData appData) {
		// TODO 设置默认尺码组
		String url = DataBase.HostIP + "writedefaultsize&sizegroupno=" + groupno + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return "t";
		} else {
			return jsonstr;
		}
	}

	public static String setdefaultcolor(String colorid, AppData appData) {
		// TODO 设置默认尺码组
		String url = DataBase.HostIP + "writedefaultcolor&colorid=" + colorid + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return "t";
		} else {
			return jsonstr;
		}
	}

	public static String getsjwh(String date, AppData appData) {
		// TODO Auto-generated method stub
		String url = DataBase.HostIP + "totalaccsum&nowdate=" + date + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return "t";
		} else {
			return jsonstr;
		}
	}

	public static String accmoneylist(String fs, AppData appData) {
		// TODO Auto-generated method stub
		String url = DataBase.HostIP + "accmoneylist&fs=" + fs + "&rows=" + appData.pagecount + "&page=" + page
				+ "&fieldlist=*" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			JSONObject obj = JSONObject.fromObject(jsonstr);
			String[] total = obj.getString("total").split("\\^");
			String footer = "[{\"ROWNUMBER\":\"\",\"REMARK\":\"枫杨果余额\",\"CURR\":" + total[1]+ "}]";
			obj.put("footer", footer);
			return obj.toString();
		} else {
			return jsonstr;
		}
	}

	public static String moveaccmoney(String toaccname, String curr, AppData appData) {
		// TODO Auto-generated method stub
		String url = DataBase.HostIP + "moveaccmoney&toaccname=" + toaccname.toUpperCase() + "&curr=" + curr
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return "t";
		} else {
			return jsonstr;
		}
	}
	public static String listprognamebyuserid(String params, AppData appData) {
		// TODO 获取首页http://erp.sxyhome.com:62210/interface/erp.aspx?action=listprognamebyuserid
		String url = DataBase.HostIP + "listprognamebyuseridpc&" + params + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}
}
