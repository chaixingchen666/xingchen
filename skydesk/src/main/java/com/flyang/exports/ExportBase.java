package com.flyang.exports;


import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;
import tools.ExtendBase;


public class ExportBase extends ExtendBase{
	private String action = "";
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	@Override
	public String getexportdata(String page, JSONObject dataobj, AppData appData) throws IOException {
		// TODO 导出数据 order sort fieldlist
		dataobj.put("page", page);
		dataobj.put("rows", "50");
		return DataBase.postRequest(action, dataobj, appData);
	}
	
	@Override
	public void setCellText(HSSFWorkbook workbook, HSSFCell cell, JSONObject object, String field) {
		if (object.containsKey(field)) {
			String valuestr = object.getString(field);
			switch (action) {
			case "listdealrecord":
				if(field.equals("PAYFS")){
					int payfs = Integer.parseInt(valuestr);
					if(payfs==0)
						valuestr = "支付宝";
					else if(payfs==1)
						valuestr = "微信";
					else if(payfs==2)
						valuestr = "拉卡拉";
				}else if(field.equals("STATETAG")){
					int stg = Integer.parseInt(valuestr);
					double curr = Integer.parseInt(valuestr);
					if(stg==0)
					valuestr=curr>0?"待付":"待退";
					else if(stg==1)
						valuestr=curr>0?"已付":"已退";
					else if(stg==2)
						valuestr="取消";
				}else if(field.equals("YWTAG")){
					int ywtg = Integer.parseInt(valuestr);
					if(ywtg==0)
						valuestr = "零售开票";
					else if(ywtg==1)
						valuestr = "商场零售";
					else if(ywtg==2)
						valuestr = "线上支付";
					else if(ywtg==3)
						valuestr = "批发销售";
				}
				
				break;

			default:
				break;
			}
			HSSFRichTextString text = new HSSFRichTextString(valuestr);
			if (field.contains("AMOUNT") || field.contains("AMT")) {
				Float value = Float.parseFloat(text.toString().trim().equals(null) || text.toString().trim().equals("")
						? "0" : text.toString().trim());
				cell.setCellValue(value);
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			} else if (field.contains("CURR") || field.contains("PRICE") || field.contains("SALE") || field.contains("RATE")) {
				Float value = Float.parseFloat(text.toString().trim().equals(null) || text.toString().trim().equals("")
						? "0" : text.toString().trim());
				cell.setCellValue(value);
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellStyle(cellStyle);
			} else {
				cell.setCellValue(text.toString());
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			}
		}
	}
}
