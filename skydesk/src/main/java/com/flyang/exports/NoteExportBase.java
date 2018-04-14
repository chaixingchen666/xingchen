package com.flyang.exports;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import com.flyang.tools.FlyangHttp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;

public class NoteExportBase {

	private String server = "api";
	// h-单据 m-单据明细 str-字段 name-表头
	private int sizenum = 5;
	private String sizeno = "";
	private String haction;
	private String maction;
	private JSONObject hdataobj;
	private JSONObject mdataobj;
	private String[] hfieldstr;
	private String[] hfieldname;
	private String[] mfieldstr;
	private String[] mfieldname;
	private String[] mfooterstr;
	private String[] mfooterfield;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getHaction() {
		return haction;
	}

	public void setHaction(String haction) {
		this.haction = haction;
	}

	public String getMaction() {
		return maction;
	}

	public void setMaction(String maction) {
		this.maction = maction;
	}

	public JSONObject getHdataobj() {
		return hdataobj;
	}

	public void setHdataobj(JSONObject hdataobj) {
		this.hdataobj = hdataobj;
	}

	public JSONObject getMdataobj() {
		return mdataobj;
	}

	public void setMdataobj(JSONObject mdataobj) {
		this.mdataobj = mdataobj;
	}

	public String[] getHfieldstr() {
		return hfieldstr;
	}

	public void setHfieldstr(String[] hfieldstr) {
		this.hfieldstr = hfieldstr;
	}

	public String[] getHfieldname() {
		return hfieldname;
	}

	public void setHfieldname(String[] hfieldname) {
		this.hfieldname = hfieldname;
	}

	public String[] getMfieldstr() {
		return mfieldstr;
	}

	public void setMfieldstr(String[] mfieldstr) {
		this.mfieldstr = mfieldstr;
	}

	public String[] getMfieldname() {
		return mfieldname;
	}

	public void setMfieldname(String[] mfieldname) {
		this.mfieldname = mfieldname;
	}

	public String[] getMfooterstr() {
		return mfooterstr;
	}

	public void setMfooterstr(String[] mfooterstr) {
		this.mfooterstr = mfooterstr;
	}

	public String[] getMfooterfield() {
		return mfooterfield;
	}

	public void setMfooterfield(String[] mfooterfield) {
		this.mfooterfield = mfooterfield;
	}

	public HSSFCellStyle currcellStyle;
	public HSSFCellStyle headcellStyle;

	public JSONObject noteExportxls(String sheetname, OutputStream output, AppData appData) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = (HSSFSheet) workbook.createSheet(sheetname);
		currcellStyle = workbook.createCellStyle();
		currcellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		// 背景色
		headcellStyle = workbook.createCellStyle();
		headcellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
		headcellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headcellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
		// 自动换行
		// headcellStyle.setWrapText(true);
		// 生成一个字体
		HSSFFont headfont = workbook.createFont();
		headfont.setFontHeightInPoints((short) 10);
		headfont.setColor(HSSFColor.WHITE.index);
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headfont.setFontName("微软雅黑");
		headcellStyle.setFont(headfont);
		int rownum = 0;
		HSSFRow header = sheet.createRow(rownum++);
		// 生成第一表行
		for (int i = 0; i < hfieldname.length; i++) {
			String string = hfieldname[i];
			HSSFCell cell = header.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(string);
			cell.setCellValue(text);
			cell.setCellStyle(headcellStyle);
		}
		FlyangHttp fh = new FlyangHttp();
		fh.setHostIP(DataBase.JERPIP + server + "?action=");
		// 单据byid信息
		JSONObject hresobj = fh.FlyangdoPost(haction, hdataobj, appData);
		if (!appData.valideResData(hresobj)) {
			return hresobj;
		}
		JSONObject object = hresobj.getJSONArray("rows").getJSONObject(0);
		HSSFRow row = sheet.createRow(rownum++);
		for (int j = 0; j < hfieldstr.length; j++) {
			String string = hfieldstr[j];
			HSSFCell cell = row.createCell(j);
			HSSFRichTextString text = new HSSFRichTextString(object.getString(string));
			cell.setCellValue(text.toString());
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		}
		// 生成第三表行
		// 单据明细信息
		mdataobj.put("page", 1);
		JSONObject mresobj = fh.FlyangdoPost(maction, mdataobj, appData);
		if (!appData.valideResData(mresobj)) {
			return mresobj;
		}
		int total = 0;
		JSONArray footerrows = new JSONArray();
		if (mresobj.has("total")) {
			total = mresobj.getInt("total");
			JSONObject footer = new JSONObject();
			footer.put(mfieldstr[0], "合计");
			for (int i = 0; i < mfooterstr.length; i++) {
				String field = mfooterstr[i];
				String tfield = mfooterfield[i];
				String value = "0";
				if (mresobj.has(tfield))
					value = mresobj.getString(tfield);
				footer.put(field, value);
			}
			footerrows.add(footer);
		}
		JSONArray mrows = mresobj.getJSONArray("rows");
		sizenum = Integer.parseInt(appData.sizenum);
		System.out.println(sizenum);
		double pagetotal = Math.ceil(new BigDecimal(total).divide(new BigDecimal(50)).doubleValue());
		rownum = json2sheet(sheet, workbook, rownum, mrows);
		if (pagetotal > 1) {
			for (int i = 2; i <= pagetotal; i++) {
				String page = String.valueOf(i);
				mdataobj.put("page", page);
				mresobj = fh.FlyangdoPost(maction, mdataobj, appData);
				if (appData.valideResData(mresobj)) {
					mrows = mresobj.getJSONArray("rows");
					rownum = json2sheet(sheet, workbook, rownum, mrows);
				}
			}
		}
		System.out.println(footerrows.toString());
		rownum = json2sheet(sheet, workbook, rownum, footerrows);
		try {
			workbook.write(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject resobj = new JSONObject();
		resobj.put("result", 1);
		return resobj;
	}

	/**
	 * 
	 * @param sheet
	 *            表
	 * @param rownum
	 *            开始行
	 * @param arrData
	 *            数据
	 * @param arrayHeader
	 *            表头字段
	 */
	public int json2sheet(HSSFSheet sheet, HSSFWorkbook workbook, int rownum, JSONArray arrData) {
		for (int i = 0; i < arrData.size(); i++) {
			JSONObject object = arrData.getJSONObject(i);
			String _sizeno = "";
			if (object.has("SIZEGROUPNO"))
				_sizeno = object.getString("SIZEGROUPNO");
			int cellindex = 0;
			if (!sizeno.equals(_sizeno)) {
				sizeno = _sizeno;
				HSSFRow row = sheet.createRow(rownum++);
				for (int cn = 0; cn < mfieldname.length; cn++) {
					if (mfieldname[cn] == "小计") {
						for (int s = 1; s <= sizenum; s++) {
							HSSFCell cell = row.createCell(cellindex++);
							String field = "SIZENAME" + s;
							String value = "";
							if (object.has(field))
								value = object.getString(field);
							cell.setCellValue(value);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(headcellStyle);
						}
					}
					String value = mfieldname[cn];
					HSSFCell cell = row.createCell(cellindex++);
					cell.setCellValue(value);
					cell.setCellStyle(headcellStyle);
				}
				cellindex = 0;
			}
			HSSFRow row = sheet.createRow(rownum++);
			for (int j = 0; j < mfieldstr.length; j++) {
				String field = mfieldstr[j];
				String value = "";
				if (object.has(field))
					value = object.getString(field);
				if (field == "AMOUNT") {
					for (int s = 1; s <= sizenum; s++) {
						HSSFCell cell = row.createCell(cellindex++);
						double amt = 0;
						String amtfield = "AMOUNT" + s;
						String amtvalue = "0";
						if (object.has(amtfield))
							amtvalue = object.getString(amtfield);
						if(!amtvalue.equals(""))
							amt = Double.parseDouble(amtvalue);
						if (amt != 0)
							cell.setCellValue(amt);

					}
				}
				HSSFCell cell = row.createCell(cellindex++);
				if (field.contains("AMOUNT") || field.contains("PRICE") || field.contains("DISCOUNT")
						|| field.contains("CURR") || field.contains("RETAIL")) {
					if(!value.equals(""))
						cell.setCellValue(Double.parseDouble(value));
					if (field.contains("PRICE") || field.contains("DISCOUNT") || field.contains("CURR")
							|| field.contains("RETAIL"))
						cell.setCellStyle(currcellStyle);
				} else {
					cell.setCellValue(value);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}
			}
		}
		return rownum;
	}
}
