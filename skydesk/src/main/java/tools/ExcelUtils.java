package tools;

import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExcelUtils {
	public void noteExportxls(String sheetname, OutputStream output, String json, String jsonmx, String properties[],
			String columnsNames[], String mxproperties[], String mxcolumnsNames[], int sizenum) {
		HSSFWorkbook libro = new HSSFWorkbook();
		HSSFSheet hoja = (HSSFSheet) libro.createSheet(sheetname);
		HSSFCellStyle cellStyle = libro.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		// 背景色
		HSSFCellStyle headcellStyle = libro.createCellStyle();
		headcellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
		headcellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headcellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
		// 自动换行
		// headcellStyle.setWrapText(true);
		// 生成一个字体
		
		HSSFFont headfont = libro.createFont();
		headfont.setFontHeightInPoints((short) 10);
		headfont.setColor(HSSFColor.WHITE.index);
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headfont.setFontName("微软雅黑");
		headcellStyle.setFont(headfont);

		HSSFRow header = hoja.createRow(0);
		// 生成第一表行
		for (int i = 0; i < columnsNames.length; i++) {
			String string = columnsNames[i];
			HSSFCell cell = header.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(string);
			cell.setCellValue(text);
			cell.setCellStyle(headcellStyle);
		}
		// 单据byid信息
		JSONArray array = JSONArray.fromObject(json);
		JSONObject object = array.getJSONObject(0);
		HSSFRow row = hoja.createRow(1);
		for (int j = 0; j < properties.length; j++) {
			String string = properties[j];
			HSSFCell cell = row.createCell(j);
			HSSFRichTextString text = new HSSFRichTextString(object.getString(string));
			cell.setCellValue(text.toString());
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		}
		// 生成第三表行
		// 单据明细信息
		JSONArray mxarray = JSONArray.fromObject(jsonmx);
		HSSFCellStyle mxcellStyle = libro.createCellStyle();
		mxcellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		String sizeno = "";
		int r = 2;
		int sp = mxproperties.length;
		int amtsp = 0;
		for (int i = 0; i < mxarray.size(); i++) {
			JSONObject mxobject = mxarray.getJSONObject(i);
			String _sizeno = mxobject.getString("SIZEGROUPNO");
			if (!sizeno.equals(_sizeno)) {
				sizeno = _sizeno;
				HSSFRow mxrow = hoja.createRow(r++);
				for (int cn = 0; cn < mxcolumnsNames.length; cn++) {
					if (mxcolumnsNames[cn] == "小计") {
						sp = cn;
						for (int s = 1; s <= sizenum; s++) {
							HSSFCell cell = mxrow.createCell(cn + s - 1);
							HSSFRichTextString text = new HSSFRichTextString(mxobject.getString("SIZENAME" + s));
							cell.setCellValue(text);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(headcellStyle);
						}
					}
					int k = 0;
					if (cn >= sp)
						k = sizenum;
					String string = mxcolumnsNames[cn];
					HSSFCell cell = mxrow.createCell(cn + k);
					HSSFRichTextString text = new HSSFRichTextString(string);
					cell.setCellValue(text);
					cell.setCellStyle(headcellStyle);
				}
			}

			HSSFRow mxrow = hoja.createRow(r++);
			for (int j = 0; j < mxproperties.length; j++) {
				if (mxproperties[j] == "AMOUNT") {
					if(amtsp==0)
						amtsp = j;
					for (int s = 1; s <= sizenum; s++) {
						HSSFCell cell = mxrow.createCell(j + s - 1);
						double amt = Double.parseDouble(mxobject.getString("AMOUNT" + s));
						if (amt != 0) {
							HSSFRichTextString text = new HSSFRichTextString(String.valueOf(amt));
							cell.setCellValue(Float.parseFloat(text.toString().trim()));
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						}
					}
				}

				int k = 0;
				if (j >= sp)
					k = sizenum;
				String string = mxproperties[j];
				HSSFCell cell = mxrow.createCell(j + k);
				HSSFRichTextString text = new HSSFRichTextString(mxobject.getString(string));
				if (string.contains("AMOUNT")||string.contains("PRICE")||string.contains("DISCOUNT")||string.contains("CURR")||string.contains("RETAIL")) {
					cell.setCellValue(
							Float.parseFloat(text.toString().trim().equals(null) || text.toString().trim().equals("")
									? "0" : text.toString().trim()));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					if (j + k > 5 + sizenum)
						cell.setCellStyle(cellStyle);
				} else {
					cell.setCellValue(text.toString());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}

			}
		}
		try {
			libro.write(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param sheetname
	 *            表名
	 * @param hassize
	 *            是否有尺码明细
	 * @param output
	 *            输出流对象
	 * @param jsonmx
	 *            json数据流
	 * @param mxproperties
	 *            字段名
	 * @param mxcolumnsNames
	 *            列名字
	 * @param sizenum
	 *            尺码数
	 */
	public void kcmxExportxls(String sheetname, boolean hassize, OutputStream output, String jsonmx,
			String mxproperties[], String mxcolumnsNames[], int sizenum) {
		HSSFWorkbook libro = new HSSFWorkbook();
		HSSFSheet hoja = (HSSFSheet) libro.createSheet(sheetname);

		HSSFCellStyle cellStyle = libro.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		// 背景色
		HSSFCellStyle headcellStyle = libro.createCellStyle();
		headcellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
		headcellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headcellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
		// 自动换行
		// headcellStyle.setWrapText(true);
		// 生成一个字体
		HSSFFont headfont = libro.createFont();
		headfont.setFontHeightInPoints((short) 10);
		headfont.setColor(HSSFColor.WHITE.index);
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headfont.setFontName("微软雅黑");
		headcellStyle.setFont(headfont);

		HSSFRow sheetnamerow = hoja.createRow(0);// 信息行
		HSSFCell sheetnamecell = sheetnamerow.createCell(0);
		HSSFRichTextString sheetnametext = new HSSFRichTextString(sheetname);
		sheetnamecell.setCellValue(sheetnametext);
		// 生成第三表行
		// 单据明细信息
		JSONArray mxarray = JSONArray.fromObject(jsonmx);
		HSSFCellStyle mxcellStyle = libro.createCellStyle();
		mxcellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		String sizeno = "";
		int r = 2;
		int sp = mxproperties.length;
		for (int i = 0; i < mxarray.size(); i++) {
			JSONObject mxobject = mxarray.getJSONObject(i);
			String _sizeno = "no";
			if (hassize)
				_sizeno = mxobject.getString("SIZEGROUPNO");
			if (!sizeno.equals(_sizeno)) {
				sizeno = _sizeno;
				HSSFRow mxrow = hoja.createRow(r++);
				for (int cn = 0; cn < mxcolumnsNames.length; cn++) {
					if (mxcolumnsNames[cn] == "小计") {
						sp = cn;
						if (hassize) {
							for (int s = 1; s <= sizenum; s++) {
								HSSFCell cell = mxrow.createCell(cn + s - 1);
								HSSFRichTextString text = new HSSFRichTextString(mxobject.getString("SIZENAME" + s));
								cell.setCellValue(text);
								cell.setCellType(HSSFCell.CELL_TYPE_STRING);
								cell.setCellStyle(headcellStyle);
							}
						}
					}
					int k = 0;
					if (cn >= sp && hassize)
						k = sizenum;
					String string = mxcolumnsNames[cn];
					HSSFCell cell = mxrow.createCell(cn + k);
					HSSFRichTextString text = new HSSFRichTextString(string);
					cell.setCellValue(text);
					cell.setCellStyle(headcellStyle);
				}
			}

			HSSFRow mxrow = hoja.createRow(r++);
			for (int j = 0; j < mxproperties.length; j++) {
				if (mxproperties[j] == "AMOUNT") {
					if (hassize) {
						for (int s = 1; s <= sizenum; s++) {
							HSSFCell cell = mxrow.createCell(j + s - 1);
							double amt = Double.parseDouble(mxobject.getString("AMOUNT" + s));
							if (amt != 0) {
								HSSFRichTextString text = new HSSFRichTextString(String.valueOf(amt));
								cell.setCellValue(Float.parseFloat(text.toString().trim()));
								cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							}
						}
					}
				}
				int k = 0;
				if (j >= sp && hassize) {
					k = sizenum;
				}
				String string = mxproperties[j];
				HSSFCell cell = mxrow.createCell(j + k);
				HSSFRichTextString text = new HSSFRichTextString(mxobject.getString(string));

				if (j >= sp && j <= sp + 4) {
					cell.setCellValue(
							Float.parseFloat(text.toString().trim().equals(null) || text.toString().trim().equals("")
									? "0" : text.toString().trim()));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					if (j > sp)
						cell.setCellStyle(cellStyle);
				} else {
					cell.setCellValue(text.toString());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}

			}
		}
		try {
			libro.write(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取合并单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static String getMergedRegionValue(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();

		for (int i = 0; i < sheetMergeCount; i++) { CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();

			if (row >= firstRow && row <= lastRow) {

				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return getCellValue(fCell);
				}
			}
		}

		return null;
	}

	/**
	 * 判断合并了行
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static boolean isMergedRow(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row == firstRow && row == lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row
	 *            行下标
	 * @param column
	 *            列下标
	 * @return
	 */
	public static boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断sheet页中是否含有合并单元格
	 * 
	 * @param sheet
	 * @return
	 */
	public static boolean hasMerged(Sheet sheet) {
		return sheet.getNumMergedRegions() > 0 ? true : false;
	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 * @param firstRow
	 *            开始行
	 * @param lastRow
	 *            结束行
	 * @param firstCol
	 *            开始列
	 * @param lastCol
	 *            结束列
	 */
	public static void mergeRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {

		if (cell == null)
			return "";

		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

			return cell.getStringCellValue();

		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {

			return String.valueOf(cell.getBooleanCellValue());

		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

			return cell.getCellFormula();

		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

			return String.valueOf(cell.getNumericCellValue());

		}
		return "";
	}
}
