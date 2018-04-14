package mains.help;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;

public class HelpBase {
	public static String nouse = "0", page = "1";

	public static String getdepartmentlist(String findbox, AppData appData) throws IOException {
		// TODO 获取销售部门列表
		String url = DataBase.HostIP + "departmenthelplist&page=" + page + "&rows=" + appData.pagecount
				+ "&sort=dptid&order=asc&noused=0" + "&findbox=" + DataBase.encode(findbox) + "&fieldlist=dptid,dptname"
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String getprintcs(String progid, AppData appData) throws IOException {
		// TODO 获取端口列表
		String url = DataBase.HostIP + "getprintcs1" + "&progid=" + progid + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String selprints(String progid, String prtid, AppData appData) throws IOException {
		// TODO 选择端口
		String url = DataBase.HostIP + "writeprintcs&prtid=" + prtid + "&progid=" + progid + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String getemployelist(String params, AppData appData) throws IOException {
		// TODO 获取销售员列表（职员）
		String url = DataBase.HostIP + "employelist&rows=" + appData.pagecount
				+ "&" + params
				+ "&sort=epid&fieldlist=a.epid,a.epno,a.epname" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String gethouselist(String findbox, AppData appData) throws IOException {
		// TODO 获取店铺列表
		String url = DataBase.HostIP + "warehouselisthelp&page=" + page + "&rows=" + appData.pagecount
				+ "&sort=houseid&order=asc&noused=0&findbox=" + DataBase.encode(findbox) + "&accid=" + appData.accid
				+ "&fieldlist=houseid,housename,pricetype1" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	// 费用项目帮助列表
	public static String getChargeslist(String findbox, AppData appData) throws IOException {
		// chargeslist&page=1&rows="+appData.pagecount+"&sort=cgid&order=asc&accid=1&fieldlist=cgid,cgname
		String url = DataBase.HostIP + "chargeslist&page=" + page + "&rows=" + appData.pagecount
				+ "&sort=cgid&order=asc&noused=0&accid=" + appData.accid + "&fieldlist=cgid,cgname" + "&findbox="
				+ DataBase.encode(findbox) + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	// 结算方式
	public static String getPaywaylist(String findbox, AppData appData) throws IOException {
		String url = DataBase.HostIP + "paywaylist&page=" + page + "&rows=" + appData.pagecount
				+ "&sort=payno&sort=asc&noused=0&accid=" + appData.accid + "&findbox=" + DataBase.encode(findbox)
				+ "&fieldlist=payid,payno,payname" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String gethouselist1(String findbox, AppData appData) throws IOException {
		// TODO 获取店铺列表
		String url = DataBase.HostIP + "warehouselisthelp&page=" + page + "&rows=" + appData.pagecount
				+ "&sort=houseid&order=asc&cxdr=1&noused=0&findbox=" + DataBase.encode(findbox) + "&accid=" + appData.accid
				+ "&fieldlist=houseid,housename,pricetype1" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String addhouse(HashMap<String, String> params, AppData appData) throws Exception {
		// TODO 添加店铺
		String url = DataBase.HostIP + "addwarehouse&accid=" + appData.accid + "&lastop=" + DataBase.encode(appData.epname)
				+ DataBase.signature(appData);
		String jsonstr = DataBase.postJsonContent(url, params);
		if (appData.isResult(jsonstr)) {
			return "t";
		}
		return jsonstr;
	}

	public static String getbrandhelp(String findbox, AppData appData) throws IOException {
		String url = DataBase.HostIP + "brandhelplist&page=" + page + "&rows=" + appData.pagecount
				+ "&sort=brandid&order=asc&noused=0&findbox=" + DataBase.encode(findbox) + "&accid=" + appData.accid
				+ "&fieldlist=brandid,brandname,noused" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String getwarelist(String findbox, AppData appData) throws IOException {
		// TODO 获取商品列表
		String url = DataBase.HostIP + "warecodelisthelp&page=" + page + "&rows=" + appData.pagecount
				+ "&sort=wareid&order=asc&noused=0&findbox=" + DataBase.encode(findbox) + "&accid=" + appData.accid
				+ "&fieldlist=A.WAREID,A.WARENO,A.WARENAME,A.UNITS,A.SHORTNAME,"
				+ "A.BRANDID,A.SEASONNAME,A.TYPEID,A.PRODYEAR,A.PRODNO,A.ENTERSALE,"
				+ "A.RETAILSALE,A.SALE1,A.SALE2,A.SALE3,A.SALE4,A.SALE5,A.REMARK,A.SIZEGROUPNO,"
				+ "A.NOUSED,A.DOWNENABLED,C.BRANDNAME,B.TYPENAME,B.FULLNAME,a.IMAGENAME0" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String getwarelist(String findbox, String provid, AppData appData) throws IOException {
		// TODO 获取商品列表
		String url = DataBase.HostIP + "warecodelisthelp&page=" + page + "&rows=" + appData.pagecount
				+ "&sort=wareid&order=asc&noused=0&findbox=" + DataBase.encode(findbox) + "&provid=" + provid
				+ "&fieldlist=A.WAREID,A.WARENO,A.WARENAME,A.UNITS,A.SHORTNAME,"
				+ "A.BRANDID,A.SEASONNAME,A.TYPEID,A.PRODYEAR,A.PRODNO,A.ENTERSALE,"
				+ "A.RETAILSALE,A.SALE1,A.SALE2,A.SALE3,A.SALE4,A.SALE5,A.REMARK,A.SIZEGROUPNO,"
				+ "A.NOUSED,A.DOWNENABLED,C.BRANDNAME,B.TYPENAME,B.FULLNAME,a.IMAGENAME0" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String addware(HashMap<String, String> params, AppData appData) throws Exception {
		// TODO 添加商品
		String url = DataBase.HostIP + "addwarecode&accid=" + appData.accid + "&lastop=" + DataBase.encode(appData.epname)
				+ DataBase.signature(appData);
		String jsonstr = DataBase.postJsonContent(url, params);
		if (appData.isResult(jsonstr)) {
			return jsonstr;
		}
		return jsonstr;
	}

	public static String getwarecolor(String wareid, String findbox, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		// http://119.84.84.173:94/interface/ebdress.aspx?action=waresizeandcolor&accid=[账号id]&wareid=[商品id]
		String url = DataBase.HostIP + "warecolorexistslist&noused=0&rows=" + appData.pagecount + "&page=" + page + "&accid="
				+ appData.accid + "&wareid=" + wareid + "&findbox=" + DataBase.encode(findbox) + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		}
		return jsonstr;
	}

	public static String getwaresize(String wareid, String findbox, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		// http://119.84.84.173:94/interface/ebdress.aspx?action=waresizeandcolor&accid=[账号id]&wareid=[商品id]
		String url = DataBase.HostIP + "waresizecodelist&noused=0&rows=" + appData.pagecount + "&page=" + page + "&accid="
				+ appData.accid + "&wareid=" + wareid + "&findbox=" + DataBase.encode(findbox) + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		}
		return jsonstr;
	}

	// 所有尺码列表
	public static String quicksearch(String findbox, AppData appData, String pag) throws IOException {
		String url = DataBase.HostIP + "sizecodelist&noused=0&page=" + pag + "&rows=" + appData.pagecount + "&findbox="
				+ DataBase.encode(findbox) + "&accid=" + appData.accid + "&fieldlist=sizeid,sizename,groupno,sizeno,noused"
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;

		} else {
			return jsonstr;
		}
	}

	public static String getwaretype(String p_typeid, AppData appData) {
		// 获取商品类型
		// http://119.84.84.173:94/interface/ebdress.aspx?action=accwaretypelist&page=1&rows="+appData.pagecount+"&accid=[账号id]&p_typeid=[类型id]&fieldlist=[
		// a.TYPEID,a.TYPENAME,a.FULLNAME,a.P_TYPEID,a.LASTNODE]
		String url = DataBase.HostIP + "accwaretypelist&page=" + page + "&rows=" + appData.pagecount + "&accid=" + appData.accid
				+ "&p_typeid=" + p_typeid + "&fieldlist=a.TYPEID,a.TYPENAME,a.FULLNAME" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		}
		return jsonstr;
	}

	public static String getaccwaretype(String i, AppData appData) {
		// TODO 获取账户商品类型
		String url = DataBase.HostIP + "accwaretypelist&page=1&rows=" + appData.pagecount + "&accid=" + appData.accid
				+ "&p_typeid=" + i + "&fieldlist=a.TYPEID,a.TYPENAME,a.FULLNAME,a.P_TYPEID,a.LASTNODE"
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		JSONObject obj = JSONObject.fromObject(jsonstr);
		StringBuffer str = new StringBuffer();
		if (appData.isTotal(jsonstr)) {
			JSONArray arr = obj.getJSONArray("rows");
			for (int j = 0; j < arr.size(); j++) {
				JSONObject s = arr.getJSONObject(j);
				str.append("<option value=\"" + s.getString("TYPEID") + "\">" + s.getString("TYPENAME") + "</option>");
			}
			return str.toString();
		}
		return jsonstr;
	}

	public static List<String> getsizegroup(AppData appData) throws IOException {
		// 获取尺码组
		String url = DataBase.HostIP + "sizegrouplist&accid=" + appData.accid + "&lastop=" + DataBase.encode(appData.epname)
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			JSONObject jsonObj = JSONObject.fromObject(jsonstr);
			JSONArray jsonArray = jsonObj.getJSONArray("rows");
			List<String> sizegroup = new ArrayList<String>();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject s = jsonArray.getJSONObject(i);
				sizegroup.add(s.getString("GROUPNO") + ":" + s.getString("SIZELIST"));
			}
			return sizegroup;
		}
		return null;
	}

	public static List<String> getsizeno(AppData appData) throws IOException {
		String url = DataBase.HostIP + "sizegrouplist&accid=" + appData.accid + "&lastop=" + DataBase.encode(appData.epname)
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			JSONObject jsonObj = JSONObject.fromObject(jsonstr);
			JSONArray jsonArray = jsonObj.getJSONArray("rows");
			List<String> sizeno = new ArrayList<String>();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject s = jsonArray.getJSONObject(i);
				sizeno.add(s.getString("GROUPNO"));
			}
			return sizeno;
		}
		return null;
	}

	public static List<String> seasonname(AppData appData) throws IOException {
		// http://119.84.84.173:94/interface/ebdress.aspx?action=seasonlist&accid=1
		String url = DataBase.HostIP + "seasonlist&accid=" + appData.accid + "&lastop=" + DataBase.encode(appData.epname)
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			JSONObject jsonObj = JSONObject.fromObject(jsonstr);
			JSONArray jsonArray = jsonObj.getJSONArray("rows");
			List<String> seasonname = new ArrayList<String>();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject s = jsonArray.getJSONObject(i);
				seasonname.add(s.getString("SEASONNAME"));
			}
			return seasonname;
		}
		return null;

	}

	// 获取商品颜色列表 (warecolor)
	public static String warecolorlist(String wareid, String findbox, AppData appData) throws IOException {
		// http://119.84.84.173:94/interface/ebdress.aspx?action=warecolorlist&page=1&rows="+appData.pagecount+"&accid=1&wareid=1
		String url = DataBase.HostIP + "warecolorlist&noused=0&page=" + page + "&rows=" + appData.pagecount + "&accid="
				+ appData.accid + "&wareid=" + wareid + "&findbox=" + DataBase.encode(findbox) + "&lastop="
				+ DataBase.encode(appData.epname) + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		}
		return jsonstr;
	}
	public static String warecolorlist(String params, AppData appData) throws IOException {
		// http://119.84.84.173:94/interface/ebdress.aspx?action=warecolorlist&page=1&rows="+appData.pagecount+"&accid=1&wareid=1
		String url = DataBase.HostIP + "warecolorlist&noused=0&" + params + "&rows=" + appData.pagecount + "&lastop="
				+ DataBase.encode(appData.epname) + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		}
		return jsonstr;
	}
	// 修改添加商品的已有颜色列表
	public static String updatewarecolor(String wareid, HashMap<String, String> params, AppData appData) throws Exception {
		String url = DataBase.HostIP + "writewarecolor&accid=" + appData.accid + "&wareid=" + wareid + "&lastop="
				+ DataBase.encode(appData.epname) + DataBase.signature(appData);
		String jsonstr = DataBase.postJsonContent(url, params);
		if (appData.isResult(jsonstr)) {
			return "t";
		} else {
			return jsonstr;
		}
	}

	public static String quicksearchwarecolor(String wareid, String findbox, AppData appData) throws IOException {
		// TODO 快速搜索商品颜色
		String url = DataBase.HostIP + "warecolorexistslist&rows=" + appData.pagecount + "&page=" + page + "&accid="
				+ appData.accid + "&wareid=" + wareid + "&findbox=" + DataBase.encode(findbox) + "&lastop="
				+ DataBase.encode(appData.epname) + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		}
		return jsonstr;
	}

	public static String quicksearchallcolor(String wareid, String findbox, AppData appData) throws IOException {
		// http://119.84.84.173:94/interface/ebdress.aspx?action=warecolorlist&page=1&rows="+appData.pagecount+"&accid=1&wareid=1
		String url = DataBase.HostIP + "warecolorlist&page=" + page + "&rows=" + appData.pagecount + "&accid=" + appData.accid
				+ "&wareid=" + wareid + "&findbox=" + DataBase.encode(findbox) + "&lastop=" + DataBase.encode(appData.epname)
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		}
		return jsonstr;
	}

	public static String getwaresum(String houseid, String wareid, String sizeid, String colorid, AppData appData)
			throws IOException {
		// TODO 检验库存
		String url = DataBase.HostIP + "getwaresum&accid=" + appData.accid + "&wareid=" + wareid + "&sizeid=" + sizeid
				+ "&colorid=" + colorid + "&lastop=" + DataBase.encode(appData.epname) + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (jsonstr.contains("AMOUNT")) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String getwaresizeandcolor(String wareid, AppData appData) {
		// TODO 快速增加
		String url = DataBase.HostIP + "waresizeandcolor&accid=" + appData.accid + "&wareid=" + wareid
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return jsonstr;
		}
		return jsonstr;
	}

	public static String getwaresizeandcolor(String wareid, String houseid, AppData appData) {
		// TODO 快速增加得到库存明细
		String url = DataBase.HostIP + "waresizeandcolor&accid=" + appData.accid + "&wareid=" + wareid + "&houseid="
				+ houseid + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return jsonstr;
		}
		return jsonstr;
	}

	public static String getsupinfodata(String findbox, AppData appData) throws IOException {
		// TODO获取供应商数据
		String url = DataBase.HostIP + "providelisthelp&noused=0&page=" + page + "&rows=" + appData.pagecount
				+ "&sort=provid&order=asc&accid=" + appData.accid + "&findbox=" + DataBase.encode(findbox)
				+ "&fieldlist=provid,provname,discount,pricetype" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String getsupinfodata(String findbox, String showall, AppData appData) throws IOException {
		// TODO获取供应商数据
		String url = DataBase.HostIP + "providelisthelp&noused=0&page=" + page + "&rows=" + appData.pagecount
				+ "&sort=provid&order=asc&accid=" + appData.accid + "&showall=" + showall + "&findbox="
				+ DataBase.encode(findbox) + "&fieldlist=provid,provname,discount,pricetype" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String getcustlist(String findbox, AppData appData) throws IOException {
		// TODO获取客户数据
		String url = DataBase.HostIP + "customerlisthelp&noused=0&page=" + page + "&rows=" + appData.pagecount
				+ "&sort=custid&order=asc&accid=" + appData.accid + "&findbox=" + DataBase.encode(findbox)
				+ "&fieldlist=custid,custname,discount,pricetype" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String getsaletype(String findbox, AppData appData) throws IOException {
		// TODO获取销售类型
		String url = DataBase.HostIP + "salecodelist&noused=0&page=" + page + "&rows=" + appData.pagecount
				+ "&sort=custid&order=asc&accid=" + appData.accid + "&findbox=" + DataBase.encode(findbox)
				+ "&fieldlist=saleid,salename" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String getguestviplist(String findbox, AppData appData) throws IOException {
		// 获取vip列表
		String url = DataBase.HostIP + "guestviplist&noused=0&findbox=" + DataBase.encode(findbox) + "&page=" + page
				+ "&rows=" + appData.pagecount + "&sort=guestid&order=asc"
				+ "&fieldlist=A.GUESTID,A.GUESTNAME,A.VIPNO,A.VTID,A.MOBILE,A.BALCENT,A.BALCURR,B.VTNAME,B.DISCOUNT"
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}
	public static String getguestvipbyno(String params, AppData appData) throws IOException {
		// 获取vip列表
		String url = DataBase.HostIP + "getguestvipbyno&" + params 
				+ "&fieldlist=A.GUESTID,A.GUESTNAME,A.VIPNO,A.VTID,A.MOBILE,A.BALCENT,A.BALCURR,B.VTNAME,B.DISCOUNT"
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String getPaywaylist(AppData appData) {
		// TODO 获取支付方式
		String url = DataBase.HostIP + "paywaylist&noused=0&page=" + page + "&rows=50&sort=payno&sort=asc&accid="
				+ appData.accid + "&fieldlist=payid,payno,payname" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	// 取服务器日期时间
	public static String getSerdatatime(AppData appData) throws Exception {
		// http://119.84.84.173:94/interface/ebdress.aspx?action=serverdate
		String url = DataBase.HostIP + "serverdatetime" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		return jsonstr;
	}

	// 取服务器日期
	public static String getSerdata(AppData appData) throws Exception {
		// http://119.84.84.173:94/interface/ebdress.aspx?action=serverdate
		String url = DataBase.HostIP + "serverdate" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		return jsonstr;
	}

	public static String addbackprint(String progid, String houseid, String noteid, String noteno, AppData appData)
			throws IOException {
		// TODO 提交后台打印
		String url = DataBase.HostIP + "addbackprint&accid=" + appData.accid + "&progid=" + progid + "&houseid=" + houseid
				+ "&noteid=" + noteid + "&noteno=" + noteno + "&lastop=" + DataBase.encode(appData.epname)
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String barcodeadd(String params, AppData appData) throws IOException {
		String url = DataBase._HostIP + params + "&accid=" + appData.accid + "&lastop=" + DataBase.encode(appData.epname)
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	// 会员类型帮助列表
	public static String getVipTypelist(String findbox, AppData appData) throws IOException {
		String url = DataBase.HostIP + "guesttypelist&noused=0&page=" + page + "&rows=" + appData.pagecount
				+ "&sort=vtid&order=asc" + "&findbox=" + DataBase.encode(findbox) + "&fieldlist=vtid,vtname"
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String removesubmit(String id, String noteno, String tablename, AppData appData) {
		// TODO 撤单
		String url = DataBase.HostIP + "removesubmit&tablename=" + tablename + "&accid=" + appData.accid + "&id=" + id
				+ "&noteno=" + noteno + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return "t";
		} else {
			return jsonstr;
		}
	}

	public static String listomsubject(String findbox, AppData appData) throws IOException {
		String url = DataBase.HostIP + "listomsubject&page=" + page + "&rows=" + appData.pagecount + "&findbox="
				+ DataBase.encode(findbox) + "&accid=" + appData.accid + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String getsaleoutprice(String params, AppData appData) throws IOException {
		// TODO 调价价格（商场零售）
		String url = DataBase.HostIP + "getsaleoutprice&" + params + "&lastop=" + DataBase.encode(appData.epname)
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String getwareoutprice(String params, AppData appData) throws IOException {
		// TODO 调价价格（商场零售）
		String url = DataBase.HostIP + "getwareoutprice&" + params + "&lastop=" + DataBase.encode(appData.epname)
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String findguestvip(String findbox, AppData appData) throws IOException {
		// TODO 取会员（商场零售）
		String url = DataBase.HostIP + "findguestvip&findbox=" + DataBase.encode(findbox) + "&lastop="
				+ DataBase.encode(appData.epname) + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isSyserror(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String copynote(String noteid, String fromnoteno, String tonoteno, AppData appData) throws IOException {
		// TODO 复制单据
		String url = DataBase.HostIP + "copynote" + "&noteid=" + noteid + "&fromnoteno=" + fromnoteno + "&tonoteno="
				+ tonoteno + "&lastop=" + DataBase.encode(appData.epname) + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return "t";
		} else {
			return jsonstr;
		}
	}

	public static String clearnote(String noteid, String noteno, AppData appData) throws IOException {
		// TODO 冲单
		String url = DataBase.HostIP + "clearnote" + "&noteid=" + noteid + "&noteno=" + noteno + "&lastop="
				+ DataBase.encode(appData.epname) + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isResult(jsonstr)) {
			return "t";
		} else {
			return jsonstr;
		}
	}

	/**
	 * 取销售费用列表（批发开票）
	 * 
	 * @param noteno
	 * @param appData
	 * @return
	 * @throws IOException
	 */
	public static String wareoutcostlist(String noteno, AppData appData) throws IOException {
		// TODO 取销售费用列表（批发开票）
		String url = DataBase.HostIP + "wareoutcostlist&noteno=" + noteno +"&page="+page+"&rows="+appData.pagecount
				+ "&fieldlist=A.ID,A.NOTENO,A.ACCID,A.CGID,A.CURR,B.CGNAME" + "&lastop=" + DataBase.encode(appData.epname)
				+ DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isSyserror(jsonstr)) {
			JSONObject obj = JSONObject.fromObject(jsonstr);
			String total = obj.getString("total");
			String[] totals = total.split("\\^");
			String footer = "[{\"CURR\":" + totals[1]+ ",\"ROWNUMBER\":\"合计\"}]";
			obj.put("footer", footer);
			return obj.toString();
		} else {
			return jsonstr;
		}
	}

	public static String addwareoutcost(HashMap<String, String> params, AppData appData) throws IOException {
		// TODO 增加销售费用
		String url = DataBase.HostIP + "addwareoutcost" + "&lastop=" + DataBase.encode(appData.epname)
				+ DataBase.signature(appData);
		String jsonstr = DataBase.postJsonContent(url, params);
		if (appData.isSyserror(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String delwareoutcostbyid(String id, String noteno, AppData appData) throws IOException {
		// TODO 删除销售费用
		String url = DataBase.HostIP + "delwareoutcostbyid&id=" + id + "&noteno=" + noteno + "&lastop="
				+ DataBase.encode(appData.epname) + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isSyserror(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}
	public static String mybuyerlist(String params, AppData appData) throws IOException {
		// TODO获取客户数据
		String url = DataBase.HostIP + "mybuyerlist&rows=" + appData.pagecount
				+ "&" + params
				+ "&fieldlist=*" + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	// 修改添加商品的已有颜色列表
	public static String changenotedate(String param, HashMap<String, String> params, AppData appData) throws IOException {
		String url = DataBase.HostIP + param + "&lastop=" + DataBase.encode(appData.epname) + DataBase.signature(appData);
		return DataBase.postJsonContent(url, params);
	}
//保存参数 
	public String setparam(String params, AppData appData) throws IOException {
		String url = DataBase.HostIP + "writeparameter&" + params
				+ "&lastop=" + DataBase.encode(appData.epname) + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		return jsonstr;
	}
	
//获取参数  
	public String getparam(String params, AppData appData) throws IOException {
		String url = DataBase.HostIP + "getparameter&" + params
				+ "&lastop=" + DataBase.encode(appData.epname) + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		return jsonstr;
	}
//获取客户累计欠款
	public String getincomebal(String params, AppData appData) throws IOException {
		String url = DataBase.HostIP + "getincomebal&" + params
				+ "&lastop=" + DataBase.encode(appData.epname) + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		return jsonstr;
	}
//获取供应商累计欠款
	public String getpaybal(String params, AppData appData) throws IOException {
		String url = DataBase.HostIP + "getpaybal&" + params
				+ "&lastop=" + DataBase.encode(appData.epname) + DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		return jsonstr;
	}
}
