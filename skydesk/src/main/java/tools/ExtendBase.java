package tools;

import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.flyang.tools.FlyangHttp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import websocket.SessionUtils;

public class ExtendBase {
	String sizegroupno = "";
	ArrayList<String> headerlist = new ArrayList<>();
	protected HSSFCellStyle cellStyle = null;
	boolean isHeader = false;// 是否得到字段列表
	private String server = "";
	private String action = "";
	private String[] fields = null;
	private String[] msgFields = null;
	private String[] fieldsname = null;
	private String[] validefields = null;
	private boolean valideMsgBool = true;// 是否得到字段列表
	private String channel = "";// 频道

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public String[] getMsgFields() {
		return msgFields;
	}

	public void setMsgFields(String[] msgFields) {
		this.msgFields = msgFields;
	}

	public String[] getFieldsname() {
		return fieldsname;
	}

	public void setFieldsname(String[] fieldsname) {
		this.fieldsname = fieldsname;
	}

	public String[] getValidefields() {
		return validefields;
	}

	public void setValidefields(String[] validefields) {
		this.validefields = validefields;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = DataBase.JERPIP + server + "?action=";
	}

	public void setValideMsgBool(boolean valideMsgBool) {
		this.valideMsgBool = valideMsgBool;
	}

	public String getexportdata(String page, String params, AppData appData) throws IOException {
		return null;
	}

	// 返回竖向尺码的单据
	public String getexportSingledata(String page, String params, AppData appData) throws IOException {
		return null;
	}

	public String getexportdata(String page, JSONObject dataobj, AppData appData) throws IOException {
		return null;
	}

	public boolean valideData(JSONObject dataobj) {// 验证导入有效数据
		return true;
	}

	public boolean j_valideData(JSONObject dataobj) {// 验证导入有效数据 有其中一个就好
		if (validefields[0].length() > 0) {
			for (String str : validefields) {
				if (dataobj.has(str)&&valideMsgBool) {
					if (dataobj.getString(str) == null || dataobj.getString(str).length() == 0) {
						return false; //必要性全部，因此有一个值假，那么久返回假
					}
				} else if(dataobj.has(str)){
					if (dataobj.getString(str) != null && dataobj.getString(str).length() > 0) {
						return true; //必要性其中一个就好，因此有一个值真，那么久返回真
					}
				}
			}
		}
		return valideMsgBool;//遍历完中途没有得到结果，那么返回是否全必要的bool值
	}

	public String getReqMsg(JSONObject dataobj) {// 获取需要发送前台的信息
		return null;
	}

	public String getReqMsgStr(JSONObject dataobj) {// 获取需要发送前台的信息
		JSONObject resObj = new JSONObject();
		try {
			if (validefields[0].length() == 0||!valideMsgBool) {
				@SuppressWarnings("rawtypes")
				Iterator it = dataobj.keys();
				while (it.hasNext()) {
					String msg = dataobj.getString(it.next().toString());
					if (msg.length() > 0) {
						resObj.put("FirstString", msg);
						break;
					}
				}
			} else {
				for (String str : msgFields) {
					if (dataobj.has(str))
						resObj.put(str, dataobj.getString(str));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			resObj.put("ErrorString", "提示数据未知！");
		}
		resObj.put("msg", dataobj.getString("msg"));
		return resObj.toString();
	}

	/**
	 * 普通的根据表头导出
	 * 
	 * @param out
	 * @param params
	 * @param filename
	 * @param colstr
	 * @param footerstr
	 * @param appData
	 * @return
	 * @throws IOException
	 */
	public String json2excel(OutputStream out, String params, String filename, String colstr, String footerstr,
			AppData appData) throws IOException {
		// TODO Auto-generated method stub
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = (HSSFSheet) workbook.createSheet(filename);
		HSSFRow sheetnamerow = sheet.createRow(0);// 信息行
		int rownum = 0;// 数据行渐加
		HSSFCell sheetnamecell = sheetnamerow.createCell(rownum++);
		HSSFRichTextString sheetnametext = new HSSFRichTextString(filename);
		sheetnamecell.setCellValue(sheetnametext);
		// 设置表头
		// 背景色
		HSSFCellStyle headcellStyle = workbook.createCellStyle();
		// 自动换行
		// headcellStyle.setWrapText(true);
		// 生成一个字体
		HSSFFont headfont = workbook.createFont();
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headfont.setFontName("微软雅黑");
		headcellStyle.setFont(headfont);
		headcellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		// 设置单元格格式
		cellStyle = workbook.createCellStyle();
		HSSFDataFormat format = workbook.createDataFormat(); // --->单元格内容格式
		cellStyle.setDataFormat(format.getFormat("0.00"));
		cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);

		JSONArray array = JSONArray.fromObject(colstr);
		JSONArray footerarray = JSONArray.fromObject(footerstr);
		int lnum = 0;
		for (int i = 0; i < array.size(); i++) {
			HSSFRow header = sheet.createRow(rownum++);
			header.setHeight((short) (25 * 15.625));// n*15.625 n像素值
			JSONArray cols = array.getJSONArray(i);
			int cellnum = 0;
			for (int j = 0; j < cols.size(); j++) {
				String title = cols.getJSONObject(j).getString("title");
				String field = cols.getJSONObject(j).getString("field");
				int colspan = cols.getJSONObject(j).getInt("colspan");
				int rowspan = cols.getJSONObject(j).getInt("rowspan");
				while (ExcelUtils.isMergedRow(sheet, i + 1, cellnum)
						|| ExcelUtils.isMergedRegion(sheet, i + 1, cellnum)) {
					cellnum++;
				}
				HSSFCell cell = header.createCell(cellnum);
				HSSFRichTextString text = new HSSFRichTextString(title);
				cell.setCellValue(text);
				if (colspan != 1 || rowspan != 1)
					sheet.addMergedRegion(new CellRangeAddress(i + 1, rowspan + i, cellnum, cellnum + colspan - 1));
				if (i == 0) {
					if (colspan == 1)
						headerlist.add(field);
					else if (colspan > 1) {
						JSONArray cols2 = array.getJSONArray(1);
						for (int pi = 0; pi < colspan; pi++) {
							String field2 = cols2.getJSONObject(lnum).getString("field");
							headerlist.add(field2);
							lnum++;
						}
					}
				}
				cell.setCellStyle(headcellStyle);
				cellnum++;
			}
		}
		// 写数据
		try {
			String data = getexportdata("1", params, appData);
			if (appData.isTotal(data)) {
				JSONObject dataobj = JSONObject.fromObject(data);
				String _total = dataobj.getString("total");
				String[] totals = _total.split("\\^");
				// 运算精度
				double pagetotal = Math.ceil(new BigDecimal(totals[0]).divide(new BigDecimal(50)).doubleValue());
				JSONArray arrData = dataobj.getJSONArray("rows");
				rownum = json2sheet(sheet, workbook, rownum, arrData);
				if (pagetotal > 1) {
					for (int i = 2; i <= pagetotal; i++) {
						String page = String.valueOf(i);
						data = getexportdata(page, params, appData);
						if (appData.isTotal(data)) {
							dataobj = JSONObject.fromObject(data);
							arrData = dataobj.getJSONArray("rows");
							rownum = json2sheet(sheet, workbook, rownum, arrData);
						}
					}
				}
			}
			rownum = json2sheet(sheet, workbook, rownum, footerarray);
			for (int i = 0; i < headerlist.size(); i++) {
				sheet.autoSizeColumn(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		workbook.write(out);
		return "";
	}

	public String json2excel(OutputStream out, JSONObject params, String filename, String colstr, String footerstr,
                             AppData appData) throws IOException {
		// TODO Auto-generated method stub
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = (HSSFSheet) workbook.createSheet(filename);
		HSSFRow sheetnamerow = sheet.createRow(0);// 信息行
		int rownum = 0;// 数据行渐加
		HSSFCell sheetnamecell = sheetnamerow.createCell(rownum++);
		HSSFRichTextString sheetnametext = new HSSFRichTextString(filename);
		sheetnamecell.setCellValue(sheetnametext);
		// 设置表头
		// 背景色
		HSSFCellStyle headcellStyle = workbook.createCellStyle();
		// 自动换行
		// headcellStyle.setWrapText(true);
		// 生成一个字体
		HSSFFont headfont = workbook.createFont();
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headfont.setFontName("微软雅黑");
		headcellStyle.setFont(headfont);
		headcellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置单元格格式
		cellStyle = workbook.createCellStyle();
		HSSFDataFormat format = workbook.createDataFormat(); // --->单元格内容格式
		cellStyle.setDataFormat(format.getFormat("0.00"));
		cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);

		JSONArray array = JSONArray.fromObject(colstr);
		JSONArray footerarray = JSONArray.fromObject(footerstr);
		for (int i = 0; i < array.size(); i++) {
			HSSFRow header = sheet.createRow(rownum++);
			header.setHeight((short) (25 * 15.625));// n*15.625 n像素值
			JSONArray cols = array.getJSONArray(i);
			int cellnum = 0;
			for (int j = 0; j < cols.size(); j++) {
				String title = cols.getJSONObject(j).getString("title");
				String field = cols.getJSONObject(j).getString("field");
				int colspan = cols.getJSONObject(j).getInt("colspan");
				int rowspan = cols.getJSONObject(j).getInt("rowspan");
				while (ExcelUtils.isMergedRow(sheet, i + 1, cellnum)
						|| ExcelUtils.isMergedRegion(sheet, i + 1, cellnum)) {
					cellnum++;
				}
				HSSFCell cell = header.createCell(cellnum);
				HSSFRichTextString text = new HSSFRichTextString(title);
				cell.setCellValue(text);
				if (colspan != 1 || rowspan != 1)
					sheet.addMergedRegion(new CellRangeAddress(i + 1, rowspan + i, cellnum, cellnum + colspan - 1));
				if (colspan == 1)
					headerlist.add(field);
				cell.setCellStyle(headcellStyle);
				cellnum++;
			}
		}
		// 写数据
		try {
			String data = getexportdata("1", params, appData);
			if (appData.isTotal(data)) {
				JSONObject dataobj = JSONObject.fromObject(data);
				String _total = dataobj.getString("total");
				String[] totals = _total.split("\\^");
				double pagetotal = Math.ceil(Integer.parseInt(totals[0]) / 50);
				JSONArray arrData = dataobj.getJSONArray("rows");
				rownum = json2sheet(sheet, workbook, rownum, arrData);
				if (pagetotal > 1) {
					for (int i = 2; i <= pagetotal; i++) {
						String page = String.valueOf(i);
						data = getexportdata(page, params, appData);
						if (appData.isTotal(data)) {
							dataobj = JSONObject.fromObject(data);
							arrData = dataobj.getJSONArray("rows");
							rownum = json2sheet(sheet, workbook, rownum, arrData);
						}
					}
				}
			}
			rownum = json2sheet(sheet, workbook, rownum, footerarray);
			for (int i = 0; i < headerlist.size(); i++) {
				sheet.autoSizeColumn(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		workbook.write(out);
		return "";
	}

	/**
	 * 
	 * @param sheet
	 *            表
	 * @param cellStyle
	 *            数据格式
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
			HSSFRow row = sheet.createRow(rownum++);
			for (int j = 0; j < headerlist.size(); j++) {
				String field = headerlist.get(j);
				HSSFCell cell = row.createCell(j);
				setCellText(workbook, cell, object, field);
			}
		}
		return rownum;
	}

	/**
	 * 普通的导出单元格格式化
	 * 
	 * @param workbook
	 * @param cell
	 * @param object
	 * @param field
	 */
	public void setCellText(HSSFWorkbook workbook, HSSFCell cell, JSONObject object, String field) {
		if (object.containsKey(field)) {
			String valuestr = object.getString(field);
			HSSFRichTextString text = new HSSFRichTextString(valuestr);
			if (field.contains("AMOUNT") || field.contains("AMT")) {
				Float value = Float
						.parseFloat(text.toString().trim().equals(null) || text.toString().trim().equals("") ? "0"
								: text.toString().trim());
				cell.setCellValue(value);
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			} else if (field.contains("CURR") || field.contains("PRICE") || field.contains("SALE")
					|| field.contains("DISCOUNT") || field.contains("RATE")) {
				Float value = Float
						.parseFloat(text.toString().trim().equals(null) || text.toString().trim().equals("") ? "0"
								: text.toString().trim());
				cell.setCellValue(value);
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellStyle(cellStyle);
			} else {
				cell.setCellValue(text.toString());
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			}
		}
	}

	/**
	 * 导出横向尺码excel
	 * 
	 * @param out
	 * @param params
	 * @param filename
	 * @param colstr
	 * @param footerstr
	 * @param appData
	 * @return
	 * @throws IOException
	 */
	public String sizejson2excel(OutputStream out, String params, String filename, String colstr, String footerstr,
			AppData appData) throws IOException {
		// TODO Auto-generated method stub
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = (HSSFSheet) workbook.createSheet(filename);
		HSSFRow sheetnamerow = sheet.createRow(0);// 信息行
		int rownum = 0;// 数据行渐加
		HSSFCell sheetnamecell = sheetnamerow.createCell(rownum++);
		HSSFRichTextString sheetnametext = new HSSFRichTextString(filename);
		sheetnamecell.setCellValue(sheetnametext);
		// 设置表头
		// 背景色
		HSSFCellStyle headcellStyle = workbook.createCellStyle();
		// 自动换行
		// headcellStyle.setWrapText(true);
		// 生成一个字体
		HSSFFont headfont = workbook.createFont();
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headfont.setFontName("微软雅黑");
		headcellStyle.setFont(headfont);
		headcellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置单元格格式
		cellStyle = workbook.createCellStyle();
		HSSFDataFormat format = workbook.createDataFormat(); // --->单元格内容格式
		cellStyle.setDataFormat(format.getFormat("0.00"));
		cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);

		JSONArray array = JSONArray.fromObject(colstr);
		JSONArray footerarray = JSONArray.fromObject(footerstr);
		// 写数据
		try {
			String data = getexportdata("1", params, appData);
			if (appData.isTotal(data)) {
				JSONObject dataobj = JSONObject.fromObject(data);
				String _total = dataobj.getString("total");
				String[] totals = _total.split("\\^");
				double pagetotal = Math.ceil(Integer.parseInt(totals[0]) / 50);
				JSONArray arrData = dataobj.getJSONArray("rows");
				rownum = sizejson2sheet(sheet, workbook, rownum, arrData, array, Integer.parseInt(appData.sizenum));
				if (pagetotal > 1) {
					for (int i = 2; i <= pagetotal; i++) {
						String page = String.valueOf(i);
						data = getexportdata(page, params, appData);
						if (appData.isTotal(data)) {
							dataobj = JSONObject.fromObject(data);
							arrData = dataobj.getJSONArray("rows");
							rownum = sizejson2sheet(sheet, workbook, rownum, arrData, array,
									Integer.parseInt(appData.sizenum));
						}
					}
				}
			}
			rownum = sizejson2sheet(sheet, workbook, rownum, footerarray, array, Integer.parseInt(appData.sizenum));
		} catch (Exception e) {
			e.printStackTrace();
		}
		workbook.write(out);
		return "";
	}

	/**
	 * 普通的根据表头导出
	 * 
	 * @param out
	 * @param params
	 * @param filename
	 * @param colstr
	 * @param footerstr
	 * @param appData
	 * @return
	 * @throws IOException
	 */
	public String singleSizejson2excel(OutputStream out, String params, String filename, String colstr,
			String footerstr, AppData appData) throws IOException {
		// TODO Auto-generated method stub
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = (HSSFSheet) workbook.createSheet(filename);
		HSSFRow sheetnamerow = sheet.createRow(0);// 信息行
		int rownum = 0;// 数据行渐加
		HSSFCell sheetnamecell = sheetnamerow.createCell(rownum++);
		HSSFRichTextString sheetnametext = new HSSFRichTextString(filename);
		sheetnamecell.setCellValue(sheetnametext);
		// 设置表头
		// 背景色
		HSSFCellStyle headcellStyle = workbook.createCellStyle();
		// 自动换行
		// headcellStyle.setWrapText(true);
		// 生成一个字体
		HSSFFont headfont = workbook.createFont();
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headfont.setFontName("微软雅黑");
		headcellStyle.setFont(headfont);
		headcellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		// 设置单元格格式
		cellStyle = workbook.createCellStyle();
		HSSFDataFormat format = workbook.createDataFormat(); // --->单元格内容格式
		cellStyle.setDataFormat(format.getFormat("0.00"));
		cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);

		JSONArray array = JSONArray.fromObject(colstr);
		JSONArray footerarray = JSONArray.fromObject(footerstr);
		for (int i = 0; i < array.size(); i++) {
			HSSFRow header = sheet.createRow(rownum++);
			header.setHeight((short) (25 * 15.625));// n*15.625 n像素值
			JSONArray cols = array.getJSONArray(i);
			int cellnum = 0;
			for (int j = 0; j < cols.size(); j++) {
				String title = cols.getJSONObject(j).getString("title");
				String field = cols.getJSONObject(j).getString("field");
				int colspan = cols.getJSONObject(j).getInt("colspan");
				int rowspan = cols.getJSONObject(j).getInt("rowspan");
				while (ExcelUtils.isMergedRow(sheet, i + 1, cellnum)
						|| ExcelUtils.isMergedRegion(sheet, i + 1, cellnum)) {
					cellnum++;
				}
				HSSFCell cell = header.createCell(cellnum);
				HSSFRichTextString text = new HSSFRichTextString(title);
				cell.setCellValue(text);
				if (colspan != 1 || rowspan != 1)
					sheet.addMergedRegion(new CellRangeAddress(i + 1, rowspan + i, cellnum, cellnum + colspan - 1));
				if (colspan == 1)
					headerlist.add(field);
				cell.setCellStyle(headcellStyle);
				cellnum++;
				if (field.equals("COLORNAME")) {
					HSSFCell cell2 = header.createCell(cellnum);
					HSSFRichTextString text2 = new HSSFRichTextString("尺码");
					cell.setCellValue(text2);
					headerlist.add("SIZENAME");
					cell2.setCellStyle(headcellStyle);
					cellnum++;
				}
			}
		}
		// 写数据
		try {
			String data = getexportSingledata("1", params, appData);
			if (appData.isTotal(data)) {
				JSONObject dataobj = JSONObject.fromObject(data);
				String _total = dataobj.getString("total");
				String[] totals = _total.split("\\^");
				double pagetotal = Math.ceil(Integer.parseInt(totals[0]) / 50);
				JSONArray arrData = dataobj.getJSONArray("rows");
				rownum = json2sheet(sheet, workbook, rownum, arrData);
				if (pagetotal > 1) {
					for (int i = 2; i <= pagetotal; i++) {
						String page = String.valueOf(i);
						data = getexportSingledata(page, params, appData);
						if (appData.isTotal(data)) {
							dataobj = JSONObject.fromObject(data);
							arrData = dataobj.getJSONArray("rows");
							rownum = json2sheet(sheet, workbook, rownum, arrData);
						}
					}
				}
			}
			rownum = json2sheet(sheet, workbook, rownum, footerarray);
			for (int i = 0; i < headerlist.size(); i++) {
				sheet.autoSizeColumn(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		workbook.write(out);
		return "";
	}

	public String sizejson2excel(OutputStream out, JSONObject params, String filename, String colstr, String footerstr,
                                 AppData appData) throws IOException {
		// TODO Auto-generated method stub
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = (HSSFSheet) workbook.createSheet(filename);
		HSSFRow sheetnamerow = sheet.createRow(0);// 信息行
		int rownum = 0;// 数据行渐加
		HSSFCell sheetnamecell = sheetnamerow.createCell(rownum++);
		HSSFRichTextString sheetnametext = new HSSFRichTextString(filename);
		sheetnamecell.setCellValue(sheetnametext);
		// 设置表头
		// 背景色
		HSSFCellStyle headcellStyle = workbook.createCellStyle();
		// 自动换行
		// headcellStyle.setWrapText(true);
		// 生成一个字体
		HSSFFont headfont = workbook.createFont();
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headfont.setFontName("微软雅黑");
		headcellStyle.setFont(headfont);
		headcellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		JSONArray array = JSONArray.fromObject(colstr);
		JSONArray footerarray = JSONArray.fromObject(footerstr);
		// 写数据
		try {
			String data = getexportdata("1", params, appData);
			if (appData.isTotal(data)) {
				JSONObject dataobj = JSONObject.fromObject(data);
				String _total = dataobj.getString("total");
				String[] totals = _total.split("\\^");
				double pagetotal = Math.ceil(Integer.parseInt(totals[0]) / 50);
				JSONArray arrData = dataobj.getJSONArray("rows");
				rownum = sizejson2sheet(sheet, workbook, rownum, arrData, array, Integer.parseInt(appData.sizenum));
				if (pagetotal > 1) {
					for (int i = 2; i <= pagetotal; i++) {
						String page = String.valueOf(i);
						data = getexportdata(page, params, appData);
						if (appData.isTotal(data)) {
							dataobj = JSONObject.fromObject(data);
							arrData = dataobj.getJSONArray("rows");
							rownum = sizejson2sheet(sheet, workbook, rownum, arrData, array,
									Integer.parseInt(appData.sizenum));
						}
					}
				}
			}
			rownum = sizejson2sheet(sheet, workbook, rownum, footerarray, array, Integer.parseInt(appData.sizenum));
		} catch (Exception e) {
			e.printStackTrace();
		}
		workbook.write(out);
		return "";
	}

	/**
	 * 包含尺码同时写表头
	 * 
	 * @param sheet
	 *            表
	 * @param cellStyle
	 *            数据格式
	 * @param rownum
	 *            开始行
	 * @param arrData
	 *            数据
	 * @param arrayHeader
	 *            表头字段
	 */
	int sizeend = 0;// 此货号的尺码数量
	int amountstart = 0;// 合计数量
	String sizeno = "";

	public int sizejson2sheet(HSSFSheet sheet, HSSFWorkbook workbook, int rownum, JSONArray arrData, JSONArray array,
                              int sizenum) {
		// 设置表头
		// 背景色
		HSSFCellStyle headcellStyle = workbook.createCellStyle();
		// 自动换行
		// headcellStyle.setWrapText(true);
		// 生成一个字体
		HSSFFont headfont = workbook.createFont();
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headfont.setFontName("微软雅黑");
		headcellStyle.setFont(headfont);
		headcellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		for (int i = 0; i < arrData.size(); i++) {
			JSONObject object = arrData.getJSONObject(i);
			// 写表头
			if (object.containsKey("SIZEGROUPNO")) {
				sizeno = object.getString("SIZEGROUPNO");
			}
			if (!sizeno.equals(sizegroupno)) {
				sizeend = 0;
				if (sizenum > 0)
					sizegroupno = sizeno;
				for (int j = 0; j < array.size(); j++) {
					HSSFRow header = sheet.createRow(rownum++);
					header.setHeight((short) (25 * 15.625));// n*15.625 n像素值
					JSONArray cols = array.getJSONArray(j);
					int cellnum = 0;
					for (int k = 0; k < cols.size(); k++) {
						String title = cols.getJSONObject(k).getString("title");
						String field = cols.getJSONObject(k).getString("field");
						int colspan = cols.getJSONObject(k).getInt("colspan");
						int rowspan = cols.getJSONObject(k).getInt("rowspan");
						while (ExcelUtils.isMergedRow(sheet, j + 1, cellnum)
								|| ExcelUtils.isMergedRegion(sheet, j + 1, cellnum)) {
							cellnum++;
						}
						if (sizenum > 0 && field.equals("AMOUNT")) {
							for (int m = 0; m < sizenum; m++) {
								HSSFCell cell = header.createCell(cellnum);
								if (object.containsKey("SIZENAME" + (m + 1))) {
									String valuestr = object.getString("SIZENAME" + (m + 1));
									if (valuestr.equals("")) {
										String pov_valuestr = object.getString("SIZENAME" + m);
										if (!pov_valuestr.equals(""))
											sizeend = k + m;// 尺码长度
									}
									if (colspan == 1 && isHeader == false)
										headerlist.add("AMOUNT" + (m + 1));
									HSSFRichTextString text = new HSSFRichTextString(valuestr);
									cell.setCellValue(text);
								}
								cell.setCellStyle(headcellStyle);
								cellnum++;
							}
							if (amountstart == 0)
								amountstart = k + sizenum;// 合计位置
						}
						HSSFCell cell = header.createCell(cellnum);
						HSSFRichTextString text = new HSSFRichTextString(title);
						cell.setCellValue(text);
						if (colspan != 1 || rowspan != 1)
							sheet.addMergedRegion(
									new CellRangeAddress(j + 1, rowspan + j, cellnum, cellnum + colspan - 1));
						if (colspan == 1 && isHeader == false)
							headerlist.add(field);
						cell.setCellStyle(headcellStyle);
						cellnum++;
					}
				}
				isHeader = true;
			}
			HSSFRow row = sheet.createRow(rownum++);
			for (int j = 0; j < headerlist.size(); j++) {
				String field = headerlist.get(j);
				HSSFCell cell = row.createCell(j);
				if (j < sizeend || sizeend == 0 || j >= amountstart)
					setCellText(workbook, cell, object, field);
			}
		}
		return rownum;
	}

	// 导入
	public void importxlsread(String fileType, JSONObject everydataobj, InputStream stream, AppData appData)
			throws Exception {
		Workbook wb = null;
		if (fileType.equalsIgnoreCase("xls")) {
			wb = new HSSFWorkbook(stream);
		} else if (fileType.equalsIgnoreCase("xlsx")) {
			wb = new XSSFWorkbook(stream);
		}
		Sheet sheet1 = wb.getSheetAt(0);// 从工作区中取得页（Sheet）
		// List<String> wareid = new ArrayList<String>();
		Row header = sheet1.getRow(0);
		int j = 0;
		List<String> achead = new ArrayList<String>();
		int snum = 0;// 成功条数
		int fnum = 0;// 失败条数
		try {
			for (Row row : sheet1) {
				if (j == 0) {
					for (Cell cellh : header) {
						String cellvalue = cellh.getStringCellValue().trim().replaceAll(" ", "");
						for (int s = 0; s < fieldsname.length; s++) {
							if (fieldsname[s].equals(cellvalue)) {
								achead.add(fields[s]);
								break;
							}
						}
					}
				} else if (achead.size() > 0) {
					JSONObject dataobj = JSONObject.fromObject("{}");
					int i = 0;
					for (String str : achead) {
						Cell cells = row.getCell(i);
						if (null != cells) {
							String cellvale = "";
							switch (cells.getCellType()) {
							case HSSFCell.CELL_TYPE_NUMERIC: // 数字
								//判断是否是一个日期格式
								if(HSSFDateUtil.isCellDateFormatted(cells)) {
									Date d = cells.getDateCellValue();
									DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									cellvale = formater.format(d);
								}else {
									cellvale = DataBase.getNumricStr(cells.getNumericCellValue());
								}
								break;
							case HSSFCell.CELL_TYPE_STRING: // 字符串
								cellvale = cells.getStringCellValue();
								break;
							case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
								break;
							case HSSFCell.CELL_TYPE_FORMULA: // 公式
								try {
									cellvale = DataBase.getNumricStr(cells.getNumericCellValue());
								} catch (IllegalStateException e) {
									cellvale = String.valueOf(cells.getRichStringCellValue());
								}
								break;
							case HSSFCell.CELL_TYPE_BLANK: // 空值
							case HSSFCell.CELL_TYPE_ERROR: // 故障
							default:
								break;
							}
							dataobj.put(str, cellvale.trim().replaceAll(" ", ""));
						} else {
							break;
						}
						i = i + 1;
					}
					if (valideData(dataobj)) {
						if (everydataobj.size() > 0)
							dataobj.putAll(everydataobj);
						String re = DataBase.postRequest(action, dataobj, appData);
						if (appData.isResult(re)) {
							snum++;
							dataobj.put("msg", "成功");
						} else {
							fnum++;
							dataobj.put("msg", "失败:" + appData.msg);
						}
						try {
							SessionUtils.broadcast(channel, Integer.valueOf(appData.epid), getReqMsg(dataobj));
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						break;
					}
					if (appData.msg.equals("sys"))
						break;
				}
				// run();
				j = j + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				SessionUtils.broadcast(channel, Integer.valueOf(appData.epid), "{\"success\":\"成功条数:" + snum
						+ "\",\"fail\":\"失败条数:" + fnum + "\",\"sum\":\"上传总条数：" + (snum + fnum) + "\"}");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// run();
	}

	public void j_importxlsread(String fileType, JSONObject everydataobj, InputStream stream, AppData appData)
			throws Exception {
		Workbook wb = null;
		if (fileType.equalsIgnoreCase("xls")) {
			wb = new HSSFWorkbook(stream);
		} else if (fileType.equalsIgnoreCase("xlsx")) {
			wb = new XSSFWorkbook(stream);
		}
		Sheet sheet1 = wb.getSheetAt(0);// 从工作区中取得页（Sheet）
		// List<String> wareid = new ArrayList<String>();
		Row header = sheet1.getRow(0);
		int j = 0;
		List<String> achead = new ArrayList<String>();
		int snum = 0;// 成功条数
		int fnum = 0;// 失败条数
		String errormsg = "";
		try {
			for (Row row : sheet1) {
				if (j == 0) {
					for (Cell cellh : header) {
						String cellvalue = cellh.getStringCellValue().trim();
						boolean rightFieldName = false;
						for (int s = 0; s < fieldsname.length; s++) {
							if (fieldsname[s].equals(cellvalue)) {
								rightFieldName = true;
								achead.add(fields[s]);
								break;
							}
						}
						if (rightFieldName == false && cellvalue.length() > 0) {
							errormsg += cellvalue + "表头无效；";
						}
					}
					if (errormsg.length() > 0)
						break;
				} else if (achead.size() > 0) {
					JSONObject dataobj = JSONObject.fromObject("{}");
					int i = 0;
					for (String str : achead) {
						Cell cells = row.getCell(i);
						if (null != cells) {
							String cellvale = "";
							switch (cells.getCellType()) {
							case HSSFCell.CELL_TYPE_NUMERIC: // 数字
								//判断是否是一个日期格式
								if(HSSFDateUtil.isCellDateFormatted(cells)) {
									Date d = cells.getDateCellValue();
									DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									cellvale = formater.format(d);
								}else {
									cellvale = DataBase.getNumricStr(cells.getNumericCellValue());
								}
								break;
							case HSSFCell.CELL_TYPE_STRING: // 字符串
								cellvale = cells.getStringCellValue();
								break;
							case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
								break;
							case HSSFCell.CELL_TYPE_FORMULA: // 公式
								try {
									cellvale = DataBase.getNumricStr(cells.getNumericCellValue());
								} catch (IllegalStateException e) {
									cellvale = String.valueOf(cells.getRichStringCellValue());
								}
								break;
							case HSSFCell.CELL_TYPE_BLANK: // 空值
							case HSSFCell.CELL_TYPE_ERROR: // 故障
							default:
								break;
							}
							dataobj.put(str, cellvale.trim());
						}
						i = i + 1;
					}
					if (j_valideData(dataobj)) {
						if (everydataobj.size() > 0)
							dataobj.putAll(everydataobj);
						FlyangHttp fh = new FlyangHttp();
						fh.setHostIP(server);
						JSONObject re = fh.FlyangdoPost(action, dataobj, appData);
						if (appData.isResult(re.toString())) {
							snum++;
							dataobj.put("msg", "成功");
						} else {
							fnum++;
							dataobj.put("msg", "失败:" + appData.msg);
						}
					} else if (dataobj.size() == 0) {
						// 遇到空行就截止遍历
						break;
					} else {
						fnum++;
						dataobj.put("msg", "失败:第" + (snum + fnum) + "条数据缺少关键信息或者存在异常格式不正确，请检查！");
					}
					try {
						SessionUtils.broadcast(channel, Integer.valueOf(appData.epid), getReqMsgStr(dataobj));
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (appData.msg.equals("sys")) {
						errormsg += "签名异常，请重新登录；";
						break;
					}
				}
				j = j + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JSONObject totalobj = new JSONObject();
				totalobj.put("success", "成功条数:" + snum);
				totalobj.put("fail", "失败条数:" + fnum);
				totalobj.put("sum", "上传总条数：" + (snum + fnum));
				if (errormsg.length() > 0)
					totalobj.put("msg", errormsg);
				SessionUtils.broadcast(channel, Integer.valueOf(appData.epid), totalobj.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// run();
	}

	public void importtxtread(String filetype, JSONObject everyObj, InputStream inputStream, AppData appData) {
		// TODO Auto-generated method stub
		InputStreamReader inreader = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(inreader);
		String tempString = null;
		JSONObject dataobj = new JSONObject();
		JSONObject resobj = new JSONObject();
		String noteno = everyObj.getString("noteno");
		dataobj.put("noteno", noteno);
		int fnum = 0;
		int snum = 0;
		try {
			while ((tempString = reader.readLine()) != null) {
				String[] codestr = tempString.trim().replaceAll(" ", "").split(",");
				String barcode = "";
				String amount = "0";
				if (codestr.length == 0) {
					continue;
				} else if (codestr.length == 1) {
					barcode = codestr[0];
					amount = "1";
				} else if (codestr.length >= 2) {
					barcode = codestr[0];
					amount = codestr[1].equals("") ? "1" : codestr[1];
				}
				if (barcode.length() == 0)
					continue;
				try {
					if (Double.parseDouble(amount) == 0)
						amount = "0";
				} catch (Exception e) {
					LogUtils.LogWrite("tempcheckbarcode_error1", e.getMessage());
					amount = "0";
				}
				dataobj.put("barcode", barcode.toUpperCase());
				dataobj.put("amount", amount);
				if (Double.parseDouble(amount) <= 0 || Double.parseDouble(amount) >= 1000) {
					String errmsg = barcode + "，失败：数量格式异常，检查数量范围1-1000；";
					resobj.put("msg", errmsg);
					SessionUtils.broadcast(getChannel(), Integer.valueOf(appData.epid), resobj.toString());
					resobj.clear();
					fnum++;
					continue;
				}
				FlyangHttp fHttp = new FlyangHttp();
				fHttp.setHostIP(DataBase.HostIP2);
				fHttp.setHasSign(true);
				fHttp.setHostIP(DataBase.JERPIP + "api?action=");
				resobj = fHttp.FlyangdoPost("tempcheckbarcode", dataobj, appData);
				try {
					if (resobj.getInt("result") > 0) {
						resobj.remove("result");
						resobj.put("msg1", "数量：" + amount + "，成功");
						snum++;
					} else {
						resobj.remove("result");
						resobj.put("msg1", "数量：" + amount + "，失败");
						fnum++;
					}
					SessionUtils.broadcast(getChannel(), Integer.valueOf(appData.epid), resobj.toString());
				} catch (Exception e) {
					resobj.put("msg1", "异常：" + e.getMessage());
					SessionUtils.broadcast(getChannel(), Integer.valueOf(appData.epid), resobj.toString());
				} finally {
					resobj.clear();
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LogUtils.LogWrite("tempcheckbarcode_error2", e.getMessage());
			e.printStackTrace();
		} finally {
			SessionUtils.broadcast(getChannel(), Integer.valueOf(appData.epid), "{\"success\":\"成功条数:" + snum
					+ "\",\"fail\":\"失败条数:" + fnum + "\",\"sum\":\"上传总条数：" + (snum + fnum) + "\"}");
		}
	}

	// 导入店铺零售单
	public void j_importxlsreadnote(String fileType, JSONObject everydataobj, InputStream stream, AppData appData)
			throws IOException {
		// TODO Auto-generated method stub
		Workbook wb = null;
		if (fileType.equalsIgnoreCase("xls")) {
			wb = new HSSFWorkbook(stream);
		} else if (fileType.equalsIgnoreCase("xlsx")) {
			wb = new XSSFWorkbook(stream);
		}
		Sheet sheet1 = wb.getSheetAt(0);// 从工作区中取得页（Sheet）
		// List<String> wareid = new ArrayList<String>();
		Row header = sheet1.getRow(0);
		int j = 0;
		List<String> achead = new ArrayList<String>();
		int snum = 0;// 成功条数
		int fnum = 0;// 失败条数
		String errormsg = "";
		try {
			String handno = ""; // 请求单据号
			String rowhandno = ""; // 行单据号
			JSONObject actdataobj = new JSONObject();
			JSONArray warereclist = new JSONArray();
			for (Row row : sheet1) {
				if (j == 0) {
					for (Cell cellh : header) {
						String cellvalue = cellh.getStringCellValue().trim();
						boolean rightFieldName = false;
						for (int s = 0; s < fieldsname.length; s++) {
							if (fieldsname[s].equals(cellvalue)) {
								rightFieldName = true;
								achead.add(fields[s]);
								break;
							}
						}
						if (rightFieldName == false && cellvalue.length() > 0) {
							errormsg += cellvalue + "表头无效；";
						}
					}
					if (errormsg.length() > 0)
						break;
				} else if (achead.size() > 0) {
					JSONObject dataobj = new JSONObject();
					int i = 0;
					for (String str : achead) {
						Cell cells = row.getCell(i);
						if (null != cells) {
							String cellvale = "";
							switch (cells.getCellType()) {
							case HSSFCell.CELL_TYPE_NUMERIC: // 数字
								//判断是否是一个日期格式
								if(HSSFDateUtil.isCellDateFormatted(cells)) {
									Date d = cells.getDateCellValue();
									DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									cellvale = formater.format(d);
								}else {
									cellvale = DataBase.getNumricStr(cells.getNumericCellValue());
								}
								break;
							case HSSFCell.CELL_TYPE_STRING: // 字符串
								cellvale = cells.getStringCellValue();
								break;
							case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
								break;
							case HSSFCell.CELL_TYPE_FORMULA: // 公式
								try {
									cellvale = DataBase.getNumricStr(cells.getNumericCellValue());
								} catch (IllegalStateException e) {
									cellvale = String.valueOf(cells.getRichStringCellValue());
								}
								break;
							case HSSFCell.CELL_TYPE_BLANK: // 空值
							case HSSFCell.CELL_TYPE_ERROR: // 故障
							default:
								break;
							}
							dataobj.put(str, cellvale.trim());
						}
						i = i + 1;
					}
					if (dataobj.has("handno"))
						rowhandno = dataobj.getString("handno");
					if (!handno.equals(rowhandno)) {
						if (!"".equals(handno)) {
							if (warereclist.size() > 0)
								actdataobj.put("warereclist", warereclist);
							if (actdataobj.size() > 0) {
								if (everydataobj.size() > 0)
									actdataobj.putAll(everydataobj);
								FlyangHttp fh = new FlyangHttp();
								fh.setHostIP(server);
								JSONObject re = fh.FlyangdoPost(action, actdataobj, appData);
								if (appData.isResult(re.toString())) {
									snum++;
									actdataobj.put("msg", "成功，店铺零售单据号："+re.getString("msg"));
								} else {
									fnum++;
									actdataobj.put("msg", "失败:" + appData.msg);
								}
							} else if (actdataobj.size() == 0) {
								// 遇到空行就截止遍历
								break;
							} else {
								fnum++;
								actdataobj.put("msg", "失败:第" + (snum + fnum) + "条数据缺少关键信息或者存在异常格式不正确，请检查！");
							}
							try {
								SessionUtils.broadcast(channel, Integer.valueOf(appData.epid),
										getReqMsgStr(actdataobj));
							} catch (Exception e) {
								e.printStackTrace();
							}
							if (appData.msg.equals("sys")) {
								errormsg += "签名异常，请重新登录；";
								break;
							}
						}
						handno = rowhandno;
						actdataobj = new JSONObject();
						warereclist = new JSONArray();
						if (dataobj.has("handno"))
							actdataobj.put("handno", dataobj.getString("handno"));
						if (dataobj.has("notedate"))
							actdataobj.put("notedate", dataobj.getString("notedate"));
						if (dataobj.has("housename"))
							actdataobj.put("housename", dataobj.getString("housename"));
						if (dataobj.has("vipno"))
							actdataobj.put("vipno", dataobj.getString("vipno"));
						if (dataobj.has("saleman"))
							actdataobj.put("saleman", dataobj.getString("saleman"));
						if (dataobj.has("remark"))
							actdataobj.put("remark", dataobj.getString("remark"));
						JSONObject waremobj = new JSONObject();
						if (dataobj.has("wareno"))
							waremobj.put("wareno", dataobj.getString("wareno"));
						if (dataobj.has("colorname"))
							waremobj.put("colorname", dataobj.getString("colorname"));
						if (dataobj.has("sizename"))
							waremobj.put("sizename", dataobj.getString("sizename"));
						if (dataobj.has("amount"))
							waremobj.put("amount", dataobj.getString("amount"));
						if (dataobj.has("price"))
							waremobj.put("price", dataobj.getString("price"));
						if (dataobj.has("curr"))
							waremobj.put("curr", dataobj.getString("curr"));
						warereclist.add(waremobj);
					} else {
						JSONObject waremobj = new JSONObject();
						if (dataobj.has("wareno"))
							waremobj.put("wareno", dataobj.getString("wareno"));
						if (dataobj.has("colorname"))
							waremobj.put("colorname", dataobj.getString("colorname"));
						if (dataobj.has("sizename"))
							waremobj.put("sizename", dataobj.getString("sizename"));
						if (dataobj.has("amount"))
							waremobj.put("amount", dataobj.getString("amount"));
						if (dataobj.has("price"))
							waremobj.put("price", dataobj.getString("price"));
						if (dataobj.has("curr"))
							waremobj.put("curr", dataobj.getString("curr"));
						warereclist.add(waremobj);
					}
				}
				j = j + 1;
			}
			if (!"".equals(handno)) {
				if (warereclist.size() > 0)
					actdataobj.put("warereclist", warereclist);
				if (actdataobj.size() > 0) {
					if (everydataobj.size() > 0)
						actdataobj.putAll(everydataobj);
					FlyangHttp fh = new FlyangHttp();
					fh.setHostIP(server);
					JSONObject re = fh.FlyangdoPost(action, actdataobj, appData);
					if (appData.isResult(re.toString())) {
						snum++;
						actdataobj.put("msg", "成功，店铺零售单据号："+re.getString("msg"));
					} else {
						fnum++;
						actdataobj.put("msg", "失败:" + appData.msg);
					}
				} else {
					fnum++;
					actdataobj.put("msg", "失败:第" + (snum + fnum) + "条数据缺少关键信息或者存在异常格式不正确，请检查！");
				}
				try {
					SessionUtils.broadcast(channel, Integer.valueOf(appData.epid),
							getReqMsgStr(actdataobj));
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (appData.msg.equals("sys")) {
					errormsg += "签名异常，请重新登录；";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JSONObject totalobj = new JSONObject();
				totalobj.put("success", "成功条数:" + snum);
				totalobj.put("fail", "失败条数:" + fnum);
				totalobj.put("sum", "上传总条数：" + (snum + fnum));
				if (errormsg.length() > 0)
					totalobj.put("msg", errormsg);
				SessionUtils.broadcast(channel, Integer.valueOf(appData.epid), totalobj.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
