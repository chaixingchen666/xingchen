package com.flyang.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flyang.exports.NoteExportBase;

import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;

/**
 * Servlet implementation class NoteExportService
 * 单据导出
 */
@WebServlet("/noteexportservice")
public class NoteExportService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NoteExportService() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String pgid = request.getParameter("exportid");
		String server = request.getParameter("server");
		// 下面没设置IP
		if (server == null)
			server = "api";
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		AppData appData = new AppData();
		appData.setqxpublic(request, response);
		if (pgid != null && pgid.length() > 0) {
			ExportXls(server, pgid, request, response, appData);
		}

	}

	private void ExportXls(String server, String pgid, HttpServletRequest request, HttpServletResponse response,
                           AppData appData) throws IOException {
		// TODO Auto-generated method stub
		response.reset();// 可以加也可以不加
//		response.setContentType("multipart/form-data");
		response.setContentType("application/vnd.ms-excel");  
		HashMap<String, String> map = DataBase.getParamsMap(request.getParameterMap());
		map.remove("exportid");
		String noteno = map.get("noteno");
		String filename = "";
		String haction = "";
		String maction = "";
		JSONObject hdataobj = new JSONObject();
		JSONObject mdataobj = DataBase.getObjFrom(map);
		List<String> hfieldstr = new ArrayList<>();
		List<String> hfieldname = new ArrayList<>();
		List<String> mfieldstr = new ArrayList<>();
		List<String> mfieldname = new ArrayList<>();
		List<String> mfooterstr = new ArrayList<>();
		List<String> mfooterfield = new ArrayList<>();
		switch (pgid) {
		case "pg1101":
			filename = "采购订单单据详情+明细";
			haction = "getprovorderhbyid";
			maction = "provordermcolorsumlist";
			hdataobj.put("noteno", noteno);
			hdataobj.put("fieldlist",
					"a.id,a.noteno,a.accid,a.notedate,a.provid,a.handno,a.remark,a.operant,a.checkman,a.statetag,a.lastdate,a.totalamt,a.totalcurr,b.provname,b.discount,b.pricetype,a.overbj");
			hfieldstr = Arrays.asList("NOTENO", "NOTEDATE", "HANDNO", "REMARK", "OPERANT", "PROVNAME", "DISCOUNT",
					"PRICETYPE");
			hfieldname = Arrays.asList("单据号", "单据日期", "自编号", "摘要", "操作人", "供应商", "折扣", "价格方式");
			mfieldstr = Arrays.asList("WARENO", "WARENAME", "UNITS","COLORNAME","AMOUNT", "RETAILSALE",
					"PRICE0", "DISCOUNT", "PRICE", "CURR");
			mfieldname = Arrays.asList("货号", "商品名称", "单位", "颜色", "小计", "零售价", "单价", "折扣", "折后价", "金额");
			mfooterstr = Arrays.asList("AMOUNT", "CURR");
			mfooterfield = Arrays.asList("totalamt", "totalcurr");
			break;
		case "pg1102":
			filename = "采购入库单据详情+明细";
			haction = "getwareinhbyid";
			maction = "wareinmcolorsumlist";
			hdataobj.put("noteno", noteno);
			hdataobj.put("fieldlist",
					"a.id,a.noteno,a.accid,a.notedate,a.provid,a.handno,a.houseid,c.housename,a.remark,a.operant,a.checkman,a.statetag,a.lastdate,a.totalamt,a.totalcurr,b.provname,b.discount,b.pricetype");
			hfieldstr = Arrays.asList("NOTENO", "NOTEDATE", "HANDNO", "REMARK", "OPERANT", "PROVNAME", "DISCOUNT",
					"PRICETYPE");
			hfieldname = Arrays.asList("单据号", "单据日期", "自编号", "摘要", "操作人", "供应商", "折扣", "价格方式");
			mfieldstr = Arrays.asList("WARENO", "WARENAME", "UNITS","COLORNAME","AMOUNT", "RETAILSALE",
					"PRICE0", "DISCOUNT", "PRICE", "CURR");
			mfieldname = Arrays.asList("货号", "商品名称", "单位", "颜色", "小计", "零售价", "单价", "折扣", "折后价", "金额");
			mfooterstr = Arrays.asList("AMOUNT", "CURR");
			mfooterfield = Arrays.asList("totalamt", "totalcurr");
			break;
		case "pg1103":
			filename = "采购退货单据详情+明细";
			haction = "getwareinhbyid";
			maction = "wareinmcolorsumlist";
			hdataobj.put("noteno", noteno);
			hdataobj.put("fieldlist",
					"a.id,a.noteno,a.accid,a.notedate,a.provid,a.houseid,c.housename,a.handno,a.remark,a.operant,a.checkman,a.paylist,a.statetag,a.lastdate,a.totalamt,a.totalcurr,b.provname,b.discount,b.pricetype");
			hfieldstr = Arrays.asList("NOTENO", "NOTEDATE", "HANDNO", "REMARK", "OPERANT", "PROVNAME", "DISCOUNT",
					"PRICETYPE");
			hfieldname = Arrays.asList("单据号", "单据日期", "自编号", "摘要", "操作人", "供应商", "折扣", "价格方式","店铺","付款方式");
			mfieldstr = Arrays.asList("WARENO", "WARENAME", "UNITS","COLORNAME","AMOUNT", "RETAILSALE",
					"PRICE0", "DISCOUNT", "PRICE", "CURR","HOUSENAME","PAYLIST");
			mfieldname = Arrays.asList("货号", "商品名称", "单位", "颜色", "小计", "零售价", "单价", "折扣", "折后价", "金额");
			mfooterstr = Arrays.asList("AMOUNT", "CURR");
			mfooterfield = Arrays.asList("totalamt", "totalcurr");
			break;
		case "pg1301":
			filename = "客户订单单据详情+明细";
			haction = "getcustorderhbyid";
			maction = "custordermcolorsumlist";
			hdataobj.put("noteno", noteno);
			hdataobj.put("fieldlist",
					"a.id,a.noteno,a.accid,a.notedate,a.custid,a.handno,a.remark,a.operant,a.checkman,a.statetag,a.lastdate,a.totalamt,a.totalcurr,b.custname,b.discount,b.pricetype,a.overbj");
			hfieldstr = Arrays.asList("NOTENO", "NOTEDATE", "HANDNO", "REMARK", "OPERANT", "CUSTNAME", "DISCOUNT",
					"PRICETYPE");
			hfieldname = Arrays.asList("单据号", "单据日期", "自编号", "摘要", "操作人", "客户名称", "折扣", "价格方式");
			mfieldstr = Arrays.asList("WARENO", "WARENAME", "UNITS","COLORNAME","AMOUNT", "RETAILSALE",
					"PRICE0", "DISCOUNT", "PRICE", "CURR");
			mfieldname = Arrays.asList("货号", "商品名称", "单位", "颜色", "小计", "零售价", "单价", "折扣", "折后价", "金额");
			mfooterstr = Arrays.asList("AMOUNT", "CURR");
			mfooterfield = Arrays.asList("totalamt", "totalcurr");
			break;
		case "pg1302":
			filename = "批发开票单据详情+明细";
			haction = "getwareouthbyid";
			maction = "wareoutmcolorsumlist";
			hdataobj.put("noteno", noteno);
			hdataobj.put("fieldlist",
					"a.id,a.noteno,a.accid,a.notedate,a.custid,a.houseid,a.ntid,a.handno,a.remark,a.operant,a.checkman,a.statetag,a.lastdate,a.totalamt,a.totalcurr,b.custname,b.discount,b.pricetype,c.housename,a.dptid,e.dptname,a.paylist,a.cleartag");
			hfieldstr = Arrays.asList("NOTENO", "NOTEDATE", "HANDNO", "REMARK", "OPERANT", "CUSTNAME", "DISCOUNT",
					"PRICETYPE", "HOUSENAME", "PAYLIST");
			hfieldname = Arrays.asList("单据号", "单据日期", "自编号", "摘要", "操作人", "客户名称", "折扣", "价格方式", "店铺", "收款方式");
			mfieldstr = Arrays.asList("WARENO", "WARENAME", "UNITS","BRANDNAME", "COLORNAME",  "AMOUNT", "RETAILSALE",
					"PRICE0", "DISCOUNT", "PRICE", "CURR", "SALENAME", "GBBAR");
			mfieldname = Arrays.asList("货号", "商品名称", "单位","品牌","颜色",  "小计", "零售价", "单价", "折扣", "折后价", "金额", "销售类型",
					"国标码");
			mfooterstr = Arrays.asList("AMOUNT", "CURR");
			mfooterfield = Arrays.asList("totalamt", "totalcurr");
			break;
		case "pg1303":
			filename = "批发退库单据详情+明细";
			haction = "getwareouthbyid";
			maction = "wareoutmcolorsumlist";
			hdataobj.put("noteno", noteno);
			hdataobj.put("fieldlist",
					"a.id,a.noteno,a.accid,a.notedate,a.custid,a.houseid,a.ntid,a.handno,a.remark,a.operant,a.checkman,a.statetag,a.lastdate,a.totalamt,a.totalcurr,b.custname,b.discount,b.pricetype,c.housename,a.dptid,e.dptname,a.paylist,a.cleartag");
			hfieldstr = Arrays.asList("NOTENO", "NOTEDATE","HANDNO","REMARK","OPERANT", "CUSTNAME", "DISCOUNT",
					"PRICETYPE", "HOUSENAME", "PAYLIST");
			hfieldname = Arrays.asList("单据号", "单据日期", "自编号", "摘要", "操作人", "客户名称", "折扣", "价格方式", "店铺", "收款方式");
			mfieldstr = Arrays.asList("WARENO", "WARENAME", "UNITS","BRANDNAME", "COLORNAME",  "AMOUNT", "RETAILSALE",
					"PRICE0", "DISCOUNT", "PRICE", "CURR", "SALENAME", "GBBAR");
			mfieldname = Arrays.asList("货号", "商品名称", "单位", "品牌","颜色",  "小计", "零售价", "单价", "折扣", "折后价", "金额", "销售类型",
					"国标码");
			mfooterstr = Arrays.asList("AMOUNT","CURR");
			mfooterfield = Arrays.asList("totalamt","totalcurr");
			break;
		case "pg1309":
			filename = "批发配货单据详情+明细";
			haction = "getwarepeihbyid";
			maction = "warepeimsumlist";
			hdataobj.put("noteno", noteno);
			hdataobj.put("fieldlist",
					"a.id,a.noteno,a.accid,a.notedate,a.custid,a.houseid,a.handno,a.dptid,d.dptname,a.xsnoteno,a.remark,a.operant,a.checkman,a.statetag,a.lastdate,a.totalamt,a.salemanid,e.epname as salemanname,a.totalfactamt,a.totalcurr,b.custname,b.discount,b.pricetype,b.address,b.tel,c.housename,a.orderno,a.prtnum");
			hfieldstr = Arrays.asList("NOTENO", "NOTEDATE", "HANDNO", "REMARK", "OPERANT", "CUSTNAME", "DISCOUNT",
					"PRICETYPE", "HOUSENAME");
			hfieldname = Arrays.asList("单据号", "单据日期", "自编号", "摘要", "操作人", "客户名称", "折扣", "价格方式", "店铺");
			mfieldstr = Arrays.asList("WARENO", "WARENAME", "UNITS", "COLORNAME", "AMOUNT", "RETAILSALE", "PRICE0",
					"DISCOUNT", "PRICE", "CURR", "SALENAME");
			mfieldname = Arrays.asList("货号", "商品名称", "单位", "颜色", "小计", "零售价", "单价", "折扣", "折后价", "金额", "销售类型");
			mfooterstr = Arrays.asList("AMOUNT", "CURR");
			mfooterfield = Arrays.asList("totalamt", "totalcurr");
			break;
		case "pg1402":
			filename = "调拨出库单据详情+明细";
			haction = "getallotouthbyid";
			maction = "allotoutmcolorsumlist";
			hdataobj.put("noteno", noteno);
			hdataobj.put("fieldlist",
					"a.id,a.noteno,a.notedate,a.houseid,a.tohouseid,a.ntid,a.noteno1,a.remark,a.handno,a.operant,a.checkman,a.statetag,b.housename");
			hfieldstr = Arrays.asList("NOTENO", "NOTEDATE", "HANDNO", "REMARK", "OPERANT", "HOUSENAME","TOHOUSENAME");
			hfieldname = Arrays.asList("单据号", "单据日期", "自编号", "摘要", "操作人", "调出店铺", "调入店铺");
			mfieldstr = Arrays.asList("WARENO", "WARENAME", "UNITS", "COLORNAME", "AMOUNT", "RETAILSALE", "PRICE", "CURR");
			mfieldname = Arrays.asList("货号", "商品名称", "单位", "颜色", "小计", "零售价", "单价", "金额");
			mfooterstr = Arrays.asList("AMOUNT", "CURR");
			mfooterfield = Arrays.asList("totalamt", "totalcurr");
			break;
		case "pg1403":
			filename = "调拨入库单据详情+明细";
			haction = "getallotinhbyid";
			maction = "allotinmcolorsumlist";
			hdataobj.put("noteno", noteno);
			hdataobj.put("fieldlist",
					"a.id,a.noteno,a.notedate,a.houseid,a.fromhouseid,a.ntid,a.noteno1,a.remark,a.handno,a.operant,a.checkman,a.statetag,b.housename");
			hfieldstr = Arrays.asList("NOTENO", "NOTEDATE", "HANDNO", "REMARK", "OPERANT", "FROMHOUSENAME","HOUSENAME");
			hfieldname = Arrays.asList("单据号", "单据日期", "自编号", "摘要", "操作人", "调出店铺", "调入店铺");
			mfieldstr = Arrays.asList("WARENO", "WARENAME", "UNITS", "COLORNAME", "AMOUNT", "RETAILSALE", "PRICE", "CURR");
			mfieldname = Arrays.asList("货号", "商品名称", "单位", "颜色", "小计", "零售价", "单价", "金额");
			mfooterstr = Arrays.asList("AMOUNT", "CURR");
			mfooterfield = Arrays.asList("totalamt", "totalcurr");
			break;
		case "pg1501":
			filename = "临时盘点单据详情+明细";
			haction = "gettempcheckhbyid";
			maction = "tempcheckmcolorsumlist";
			hdataobj.put("noteno", noteno);
			hdataobj.put("fieldlist",
					"a.id,a.noteno,a.notedate,a.houseid,a.remark,a.handno,a.operant,a.checkman,a.statetag,b.housename");
			hfieldstr = Arrays.asList("NOTENO", "NOTEDATE", "HANDNO", "REMARK", "OPERANT","HOUSENAME");
			hfieldname = Arrays.asList("单据号", "单据日期", "自编号", "摘要", "操作人", "店铺");
			mfieldstr = Arrays.asList("WARENO", "WARENAME", "UNITS", "COLORNAME", "AMOUNT", "RETAILSALE", "PRICE", "CURR");
			mfieldname = Arrays.asList("货号", "商品名称", "单位", "颜色", "小计", "零售价", "单价", "金额");
			mfooterstr = Arrays.asList("AMOUNT", "CURR");
			mfooterfield = Arrays.asList("totalamt", "totalcurr");
			break;
		case "pg1508":
			filename = "期初入库单据详情+明细";
			haction = "getfirsthousehbyid";
			maction = "firsthousemcolorsumlist";
			hdataobj.put("noteno", noteno);
			hdataobj.put("fieldlist",
					"a.id,a.noteno,a.notedate,a.houseid,a.remark,a.handno,a.operant,a.checkman,a.statetag,b.housename");
			hfieldstr = Arrays.asList("NOTENO", "NOTEDATE", "HANDNO", "REMARK", "OPERANT", "HOUSENAME");
			hfieldname = Arrays.asList("单据号", "单据日期", "自编号", "摘要", "操作人", "店铺");
			mfieldstr = Arrays.asList("WARENO", "WARENAME", "UNITS", "COLORNAME", "AMOUNT", "RETAILSALE", "PRICE", "CURR");
			mfieldname = Arrays.asList("货号", "商品名称", "单位", "颜色", "小计", "零售价", "单价", "金额");
			mfooterstr = Arrays.asList("AMOUNT", "CURR");
			mfooterfield = Arrays.asList("totalamt", "totalcurr");
			break;
		default:
			break;
		}
		response.setHeader("Content-Disposition",
				"attachment;filename=\"" + DataBase.getStr(request, filename) + ".xls\"");
		OutputStream out = response.getOutputStream();
		JSONObject resobj = new JSONObject();
		try {
			NoteExportBase noteExB = new NoteExportBase();
			noteExB.setHaction(haction);
			noteExB.setMaction(maction);
			noteExB.setHdataobj(hdataobj);
			noteExB.setMdataobj(mdataobj);
			noteExB.setHfieldstr((String[]) hfieldstr.toArray());
			noteExB.setMfieldstr((String[]) mfieldstr.toArray());
			noteExB.setHfieldname((String[]) hfieldname.toArray());
			noteExB.setMfieldname((String[]) mfieldname.toArray());
			noteExB.setMfooterstr((String[]) mfooterstr.toArray());
			noteExB.setMfooterfield((String[]) mfooterfield.toArray());
			resobj = noteExB.noteExportxls(filename, out, appData);
			if (!appData.valideResData(resobj)) {
				PrintWriter outjson = response.getWriter();
				outjson.print(resobj);
				outjson.close();
			}
		} finally {
			out.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
