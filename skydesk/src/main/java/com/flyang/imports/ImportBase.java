package com.flyang.imports;

import net.sf.json.JSONObject;
import tools.ExtendBase;

public class ImportBase extends ExtendBase {
	@Override
	public boolean valideData(JSONObject dataobj) {
		String action = getAction();
		boolean res = false;
		if (dataobj.size() == 0)
			return res;
		System.out.println(dataobj.toString());
		switch (action) {
		case "addfirstpaycurrxls":// 期初应付
			if (dataobj.getString("provname") != null && dataobj.getString("housename") != null) {
				if (!dataobj.getString("provname").equals("") && !dataobj.getString("housename").equals(""))
					res = true;
			}
			break;
		case "addfirstincomecurrxls":// 期初应收
			if (dataobj.getString("custname") != null && dataobj.getString("housename") != null) {
				if (!dataobj.getString("custname").equals("") && !dataobj.getString("housename").equals(""))
					res = true;
			}
			break;
		case "addtempcheckmxls":// 临时盘点单
			if (dataobj.getString("wareno") != null && dataobj.getString("colorname") != null
					&& dataobj.getString("sizename") != null) {
				if (!dataobj.getString("wareno").equals("") && !dataobj.getString("colorname").equals("")
						&& !dataobj.getString("sizename").equals(""))
					res = true;
			}
			break;
		case "appendomuserxls":// 订货会用户
			if (dataobj.getString("custname") != null && dataobj.getString("omepname") != null) {
				if (!dataobj.getString("custname").equals("") && !dataobj.getString("omepname").equals(""))
					res = true;
			}
			break;
		default:
			break;
		}
		return res;
	}

	@Override
	public String getReqMsg(JSONObject dataobj) {
		String action = getAction();
		JSONObject resobj = JSONObject.fromObject("{}");
		resobj.put("msg", dataobj.getString("msg"));
		switch (action) {
		case "addfirstpaycurrxls":// 期初应付
			resobj.put("provname", dataobj.getString("provname"));
			resobj.put("housename", dataobj.getString("housename"));
			resobj.put("curr", dataobj.getString("curr"));
			break;
		case "addfirstincomecurrxls":// 期初应收
			resobj.put("custname", dataobj.getString("custname"));
			resobj.put("housename", dataobj.getString("housename"));
			resobj.put("curr", dataobj.getString("curr"));
			break;
		case "addtempcheckmxls":// 临时盘点单
			resobj.put("wareno", dataobj.getString("wareno"));
			resobj.put("colorname", dataobj.getString("colorname"));
			resobj.put("sizename", dataobj.getString("sizename"));
			resobj.put("amount", dataobj.getString("amount"));
			break;
		case "appendomuserxls":// 订货会用户
			resobj.put("custname", dataobj.getString("custname"));
			resobj.put("omepname", dataobj.getString("omepname"));
			break;
		default:
			resobj = dataobj;
			break;
		}
		return resobj.toString();
	}

	
}
