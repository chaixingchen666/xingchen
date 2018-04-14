package mains.receptionsales.changeclassrecord;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import com.flyang.tools.ActionUtil;
import com.flyang.tools.FlyangHttp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;

public class ChangeClassRecordBase {

	public static String getchangeclassrecord(AppData appData, String houseid, String maxdate, String mindate,
                                              String page) throws IOException {// 查询交班记录
		FlyangHttp fh = new FlyangHttp();
		fh.setHostIP(DataBase.JERPIP+"api?action=");
		JSONObject dataobj = new JSONObject();
		dataobj.put("mindate", mindate);
		dataobj.put("maxdate", maxdate);
		if (!"0".equals(houseid) && null != houseid && !"".equals(houseid)) {
			dataobj.put("houseid", houseid);
		}
		dataobj.put("rows", 50);
		dataobj.put("page", page);
		return fh.FlyangdoPost("houseworklist", dataobj, appData).toString();
	}

	// 导出拼接
	public static String data2excel(AppData appData, String houseid, String maxdate, String mindate, String page)
			throws IOException {
		String jsonstrs = getchangeclassrecord(appData, houseid, maxdate, mindate, page);
		if (appData.isTotal(jsonstrs)) {
			JSONObject jsonobj = JSONObject.fromObject(jsonstrs);
			String total = jsonobj.getString("total");
			total = total.replace("^", "-");
			String[] ss = total.split("-");
			int pg = 50;
			double j = Math.ceil(new BigDecimal(ss[0]).divide(new BigDecimal(pg)).doubleValue());
			;
			JSONArray jsonarray = jsonobj.getJSONArray("rows");
			if (j > 1) {
				for (int i = 2; i <= j; i++) {
					page = String.valueOf(i);
					String data = getchangeclassrecord(appData, houseid, maxdate, mindate, page);
					JSONObject obj = JSONObject.fromObject(data);
					jsonarray = JSONArray
							.fromObject("[" + DataBase.joinJSONArray(jsonarray, obj.getJSONArray("rows")) + "]");
				}
			}
			String json = jsonarray.toString();
			page = "1";
			return json;
		}
		return jsonstrs;
	}

	// 导出交班记录
	public static void changeclassexport(OutputStream out, AppData appData, String res) {
		JSONObject jsono = JSONObject.fromObject(res);
		JSONArray list = jsono.getJSONArray("rows");
		String title = "交班记录.xls";
		// excel表头
		List<String> headers = new ArrayList<>();
		// 声明一个工作薄
		HSSFWorkbook workbook = ActionUtil.exportExcel(title, headers);
		// excel工作薄
		HSSFSheet sheet = workbook.getSheetAt(0);
		try {
			if (list != null && list.size() > 0) {
				int size = 0;
				for (int n = 0; n <= list.size(); n++) {
					HSSFRow row;// 得到行
					JSONObject s = list.getJSONObject(size);
					if (s != null) {
						if (n == 0) {
							row = sheet.createRow(0);
							HSSFCellStyle style = workbook.createCellStyle();
							style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格
							style.setFillForegroundColor(HSSFColor.YELLOW.index);
							int c = 1;

							HSSFCell cell2 = row.createCell(0);
							HSSFRichTextString text2 = new HSSFRichTextString("开班时间");// 开班时间
							cell2.setCellValue(text2);

							HSSFCell cell8 = row.createCell(c);
							HSSFRichTextString a = new HSSFRichTextString("交班时间");// 交班时间
							cell8.setCellValue(a);
							c++;
							cell8 = row.createCell(c);
							HSSFRichTextString b = new HSSFRichTextString("店铺");// 店铺
							cell8.setCellValue(b);
							c++;
							cell8 = row.createCell(c);
							HSSFRichTextString d = new HSSFRichTextString("上班结存");// 上班结存
							cell8.setCellValue(d);
							c++;
							cell8 = row.createCell(c);
							HSSFRichTextString e = new HSSFRichTextString("占比");// 现金收支
							cell8.setCellValue(e);
							c++;
							cell8 = row.createCell(c);
							e = new HSSFRichTextString("费用开支");// 费用开支
							cell8.setCellValue(e);
							c++;
							cell8 = row.createCell(c);
							e = new HSSFRichTextString("本班余额");// 本班余额
							cell8.setCellValue(e);
							c++;
							cell8 = row.createCell(c);
							e = new HSSFRichTextString("销售金额");// 销售金额
							cell8.setCellValue(e);
							c++;
							cell8 = row.createCell(c);
							e = new HSSFRichTextString("银行存款");// 银行存款
							cell8.setCellValue(e);
							c++;
							cell8 = row.createCell(c);
							e = new HSSFRichTextString("损益金额");// 损益金额
							cell8.setCellValue(e);
							c++;
							cell8 = row.createCell(c);
							e = new HSSFRichTextString("本班结存");// 本班结存
							cell8.setCellValue(e);
							c++;
							cell8 = row.createCell(c);
							e = new HSSFRichTextString("销售人");// 销售人
							cell8.setCellValue(e);

						} else {
							row = sheet.createRow(n);
							int c = 1;

							HSSFCell cell0 = row.createCell(0);
							HSSFRichTextString text0 = new HSSFRichTextString(s.getString("BEGINDATE"));// 开班时间
							cell0.setCellValue(text0);
							HSSFCell cell8 = row.createCell(c);
							HSSFRichTextString b = new HSSFRichTextString(s.getString("ENDDATE"));// 交班时间
							cell8.setCellValue(b);
							c++;
							cell8 = row.createCell(c);
							b = new HSSFRichTextString(s.getString("HOUSENAME"));// 店铺
							cell8.setCellValue(b);
							c++;
							cell8 = row.createCell(c);
							HSSFRichTextString a = new HSSFRichTextString(s.getString("CURRQC"));// 上班结存
							cell8.setCellValue(a.toString().trim().equals("0.00") ? "" : a.toString().trim());
							c++;
							cell8 = row.createCell(c);
							HSSFRichTextString d = new HSSFRichTextString(s.getString("CURRXJ"));// 现金收支
							cell8.setCellValue(d.toString().trim().equals("0.00") ? "" : d.toString().trim());
							c++;
							cell8 = row.createCell(c);
							HSSFRichTextString e = new HSSFRichTextString(s.getString("CURRFY"));// 费用开支
							cell8.setCellValue(e.toString().trim().equals("0.00") ? "" : e.toString().trim());
							c++;
							cell8 = row.createCell(c);
							e = new HSSFRichTextString(s.getString("CURRYE"));// 本班余额
							cell8.setCellValue(e.toString().trim().equals("0.00") ? "" : e.toString().trim());
							c++;
							cell8 = row.createCell(c);
							e = new HSSFRichTextString(s.getString("CURRXS"));// 销售金额
							cell8.setCellValue(e.toString().trim().equals("0.00") ? "" : e.toString().trim());
							c++;
							cell8 = row.createCell(c);
							e = new HSSFRichTextString(s.getString("CURRYH"));// 银行存款
							cell8.setCellValue(e.toString().trim().equals("0.00") ? "" : e.toString().trim());
							c++;
							cell8 = row.createCell(c);
							e = new HSSFRichTextString(s.getString("CURRYK"));// 损益金额
							cell8.setCellValue(e.toString().trim().equals("0.00") ? "" : e.toString().trim());
							c++;
							cell8 = row.createCell(c);
							e = new HSSFRichTextString(s.getString("CURRJC"));// 本班结存
							cell8.setCellValue(e.toString().trim().equals("0.00") ? "" : e.toString().trim());
							c++;
							cell8 = row.createCell(c);
							b = new HSSFRichTextString(s.getString("LASTOP"));// 销售人
							cell8.setCellValue(b);
							size++;
						}

					}
				}
			}
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
