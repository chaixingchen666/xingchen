package com.flyang.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tools.AppData;

public class ActionUtil {
	
	/**
	 * 文件下载
	 * @param inStream
	 * @param fileName
	 */
	/*public static void downLoadFile(InputStream inStream, String fileName) {
		if (StringUtils.isBlank(fileName)) {
			fileName = UUID.randomUUID().toString().replaceAll("-", "");
		}

		String agent = getRequest().getHeader("User-Agent");
		boolean isMSIE = (agent != null) && (agent.indexOf("MSIE") != -1);
		try {
			if (isMSIE) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
				fileName = fileName.replace("+", "%20");
			} else {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		HttpServletResponse response = getResponse();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");

		response.setHeader("content-disposition", "attachment;filename=\""
				+ fileName + "\"");
		response.setContentType("application/octet-stream");

		OutputStream outStream = null;
		try {
			outStream = response.getOutputStream();
			byte[] cache = new byte[1024];
			int length = inStream.read(cache);
			while (length != -1) {
				outStream.write(cache, 0, length);
				length = inStream.read(cache);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void sendData(String data) {
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	public static void sendSuccessInfo() {
		String success = "{\"success\":true}";
		sendData(success);
	}

	public static void sendFailueInfo() {
		String failue = "{\"success\":false}";
		sendData(failue);
	}
	
	public static void outputJsonData(String msg,String data){
		if(StringUtils.isEmpty(data)){
			data="{}";
		}
		JSONObject jo = JSONObject.fromObject("{}");
		jo.put("msg", msg);
		jo.put("data", data);
		sendData(jo.toString());
	}*/
	/**
	 * 对象值拷贝
     * 当browserData的属性值为 null,的就不拷贝到serveData
     * @param dest
     * @param orig
     */
    public static void copyPropertiesWithoutNull(Object serveData, Object browserData) {
        try {
            // 得到两个Class 的所有成员变量
            Field[] destFields = serveData.getClass().getDeclaredFields();
            Field[] origFields = browserData.getClass().getDeclaredFields();

            // 设置访问权限
            AccessibleObject.setAccessible(destFields, true);
            AccessibleObject.setAccessible(origFields, true);

            Object value = null;
            String name = null;
            String returnType = null;

            for (int i = 0; i < origFields.length; i++) {
                name = origFields[i].getName();
                returnType = origFields[i].getType().getName();
                for (int j = 0; j < destFields.length; j++) {
                    if (name.equals(destFields[j].getName())&& returnType.equals(destFields[j].getType().getName())) {
                        value = origFields[i].get(browserData);
                        if (value != null) {
                            destFields[j].set(serveData, value);
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
	 * 声明excel,并设置样式
	 * 
	 * @return
	 */
	public static HSSFWorkbook exportExcel(String title, List<String> headers) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为25个字节
		sheet.setDefaultColumnWidth(15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.WHITE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
//		// 声明一个画图的顶级管理器
//		 HSSFPatriarch patriarch =
//		 sheet.createDrawingPatriarch(); 
//		 // 定义注释的大小和位置,详见文档
//		 HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0,
//		 (short) 4, 2, (short) 6, 5)); // 设置注释内容 
//		 comment.setString(new HSSFRichTextString("可以在POI中添加注释！")); //
//		 //设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容. 
//		 comment.setAuthor("leno");

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.size(); i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString((String)headers.get(i));
			cell.setCellValue(text);
		}
		return workbook;
	}
	
	/**
	 * workbook流转换成xls文件，并返回文件路径及文件名
	 * @param workbook
	 * @return
	 * @throws IOException
	 */
	public static String bulidExcelData(String path , HSSFWorkbook workbook) throws IOException{
		
		String expPath = path + "/tempFilePath/";
		File f = new File(expPath);
		if(!f.exists()){
			f.mkdir();
		}
		String excelPath = expPath+UUID.randomUUID().toString().replace("-", "")+".xls";
		FileOutputStream fos = new FileOutputStream(excelPath);
		workbook.write(fos);
		fos.close();
		return excelPath;
		
	}
	//库存资源明细账
	public static void getexpot(OutputStream out, AppData appData, String res, String totalcurr, String totalamt, String fixedcurr, String wholecurr){
		JSONObject jsono= JSONObject.fromObject(res);
		    JSONArray list=jsono.getJSONArray("rows");
		    int sizenum = Integer.parseInt(appData.sizenum);//尺码个数
		    String title =  "库存资源明细账.xls";
		    // excel表头
			//String[] headers = {"货号","商品名称","单位"};
			List<String> headers=new ArrayList<>();
			/*headers.add("货号");标题行
			headers.add("商品名称");
			headers.add("单位");
			for(int i=1;i<sizenum;i++){
				headers.add("尺码"+i);
				headers.add("数量"+i);
				headers.add("尺码编号"+i);
			}
			headers.add("小计");
			headers.add("零售价");
			headers.add("零售额");
			headers.add("成本价");
			headers.add("成本额");*/
			// 声明一个工作薄
			HSSFWorkbook workbook = ActionUtil.exportExcel(title, headers);
			// excel工作薄
			HSSFSheet sheet = workbook.getSheetAt(0);
			int hejirow=0;
			 try{
			 if (list != null && list.size() > 0) {
		        	int size=0;
		        	String SIZEGROUPNO="";
		        	String sizeg="";
		        	for (int n = 0; n < list.size(); n++) {
		              HSSFRow row;// 得到行
		              JSONObject s=list.getJSONObject(n);
		              SIZEGROUPNO=s.getString("SIZEGROUPNO");
		            if(!SIZEGROUPNO.equals(sizeg)){
		            	sizeg=SIZEGROUPNO;
		            	 for(int li=0;li<2;li++){
		                	 if (s!= null) {
		                         if(li==0){
		                        	 row = sheet.createRow(size);
		                        	//CellStyle style = workbook.createCellStyle();
		                        	//style = workbook.createCellStyle();
		                     		//style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		                        	//style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		                        	 
		                        	
		                     		//style.setFillBackgroundColor(HSSFColor.YELLOW.index);
		                        	 HSSFCellStyle style = workbook.createCellStyle();
		                        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格
		                        	 style.setFillForegroundColor(HSSFColor.YELLOW.index);
		                        	 int c=3;
		                        	 HSSFCell cell0 = row.createCell(0);
		                             HSSFRichTextString text0=new HSSFRichTextString("货号");
		                             cell0.setCellStyle(style);
		                             cell0.setCellValue(text0);
		                             HSSFCell cell1 = row.createCell(1);
		                             HSSFRichTextString text1=new HSSFRichTextString("商品名称");
		                             cell1.setCellStyle(style);
		                             cell1.setCellValue(text1);
		                             HSSFCell cell2 = row.createCell(2);
		                             HSSFRichTextString text2=new HSSFRichTextString("单位");
		                             cell2.setCellStyle(style);
		                             cell2.setCellValue(text2);
		                             if("1".equals(appData.showfooter)){
		                            	 HSSFCell cell4 = row.createCell(3);
			                             HSSFRichTextString text4=new HSSFRichTextString("颜色");
			                             cell4.setCellStyle(style);
			                             cell4.setCellValue(text4);
			                             c++;
		                             }
		                            for(int i=1;i<=sizenum;i++){
		                         	   HSSFCell cell3 = row.createCell(c);
		                                HSSFRichTextString text3=new HSSFRichTextString(s.getString("SIZENAME"+i));
		                                cell3.setCellStyle(style);
		                                cell3.setCellValue(text3);
		                                c++;
		                            }
		                            //c+=1;
		                         	HSSFCell cell8 = row.createCell(c);
		                          HSSFRichTextString a=new HSSFRichTextString("小计");//小计
		                          cell8.setCellStyle(style);
		                          cell8.setCellValue(a);
		                          c++;
		                          cell8 = row.createCell(c);
		                          HSSFRichTextString b=new HSSFRichTextString("零售价");//零售价
		                          cell8.setCellStyle(style);
		                          cell8.setCellValue(b);
		                          c++;
		                          cell8 = row.createCell(c);
		                          HSSFRichTextString d=new HSSFRichTextString("零售额");//零售额
		                          cell8.setCellStyle(style);
		                          cell8.setCellValue(d);
		                          c++;
		                          cell8 = row.createCell(c);
		                          HSSFRichTextString e=new HSSFRichTextString("成本价");//成本价
		                          cell8.setCellStyle(style);
		                          cell8.setCellValue(e);
		                          c++;
		                          cell8 = row.createCell(c);
		                          HSSFRichTextString f=new HSSFRichTextString("成本额");//成本额
		                          cell8.setCellStyle(style);
		                          cell8.setCellValue(f);
		                          size++;
		                          hejirow++;
		                         }else{
		                        	 row = sheet.createRow(size);
		                        	 int c=3;
		                        	 HSSFCell cell0 = row.createCell(0);
		                             HSSFRichTextString text0=new HSSFRichTextString(s.getString("WARENO"));
		                             cell0.setCellValue(text0);
		                             HSSFCell cell1 = row.createCell(1);
		                             HSSFRichTextString text1=new HSSFRichTextString(s.getString("WARENAME"));
		                             cell1.setCellValue(text1);
		                             HSSFCell cell2 = row.createCell(2);
		                             HSSFRichTextString text2=new HSSFRichTextString(s.getString("UNITS"));
		                             cell2.setCellValue(text2);
		                             if("1".equals(appData.showfooter)){
		                            	 HSSFCell cell4 = row.createCell(3);
			                             HSSFRichTextString text4=new HSSFRichTextString(s.getString("COLORNAME"));
			                             cell4.setCellValue(text4);
			                             c++;
		                             }
		                            for(int i=1;i<=sizenum;i++){
		                                HSSFCell cell4 = row.createCell(c);
		                                HSSFRichTextString text4=new HSSFRichTextString(s.getString("AMOUNT"+i));
		                                cell4.setCellValue(Float.parseFloat(text4.toString().trim().equals("0")||text4.toString().trim().equals("")?"0":text4.toString().trim()));
		                                cell4.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		                                c++;
		                            
		                            }
		                            //c+=1;
		                         	HSSFCell cell8 = row.createCell(c);
		                          HSSFRichTextString a=new HSSFRichTextString(s.getString("AMOUNT"));//小计
		                          cell8.setCellValue(Float.parseFloat(a.toString().trim().equals("0")||a.toString().trim().equals("")?"0":a.toString().trim()));
		                          cell8.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		                          c++;
		                          cell8 = row.createCell(c);
		                          HSSFRichTextString b=new HSSFRichTextString(s.getString("RETAILSALE"));//零售价
		                          cell8.setCellValue(Float.parseFloat(b.toString().trim().equals("")||b.toString().trim().equals("0")?"0.00":b.toString().trim()));
		                          cell8.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		                          c++;
		                          cell8 = row.createCell(c);
		                          HSSFRichTextString d=new HSSFRichTextString(s.getString("RETAILCURR"));//零售额
		                          cell8.setCellValue(Float.parseFloat(d.toString().trim().equals("")||d.toString().trim().equals("0")?"0.00":d.toString().trim()));
		                          cell8.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		                          c++;
		                          cell8 = row.createCell(c);
		                          HSSFRichTextString e=new HSSFRichTextString(s.getString("COSTPRICE"));//成本价
		                          cell8.setCellValue(Float.parseFloat(e.toString().trim().equals("0")||e.toString().trim().equals("")?"0.00":e.toString().trim()));
		                          cell8.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		                          c++;
		                          cell8 = row.createCell(c);
		                          HSSFRichTextString f=new HSSFRichTextString(s.getString("FIXEDCURR"));//成本额
		                          cell8.setCellValue(Float.parseFloat(f.toString().trim().equals("")||f.toString().trim().equals("0")?"0.00":f.toString().trim()));
		                          cell8.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		                          size++;
		                          hejirow++;
		                         }
		                         
		                      }
		                 }
		            }else{
		            	row = sheet.createRow(size);
		           	 int c=3;
		           	 HSSFCell cell0 = row.createCell(0);
		                HSSFRichTextString text0=new HSSFRichTextString(s.getString("WARENO"));
		                cell0.setCellValue(text0);
		                HSSFCell cell1 = row.createCell(1);
		                HSSFRichTextString text1=new HSSFRichTextString(s.getString("WARENAME"));
		                cell1.setCellValue(text1);
		                HSSFCell cell2 = row.createCell(2);
		                HSSFRichTextString text2=new HSSFRichTextString(s.getString("UNITS"));
		                cell2.setCellValue(text2);
		                if("1".equals(appData.showfooter)){
                       	 HSSFCell cell4 = row.createCell(3);
                            HSSFRichTextString text4=new HSSFRichTextString(s.getString("COLORNAME"));
                            cell4.setCellValue(text4);
                            c++;
                        }
		               for(int i=1;i<=sizenum;i++){
		                   HSSFCell cell4 = row.createCell(c);
		                   HSSFRichTextString text4=new HSSFRichTextString(s.getString("AMOUNT"+i));
		                   cell4.setCellValue(Float.parseFloat(text4.toString().trim().equals("0")||text4.toString().trim().equals("")?"0":text4.toString().trim()));
		                   cell4.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		                   c++;
		               
		               }
		               //c+=1;
		            	HSSFCell cell8 = row.createCell(c);
		             HSSFRichTextString a=new HSSFRichTextString(s.getString("AMOUNT"));//小计
		             cell8.setCellValue(Float.parseFloat(a.toString().trim().equals("0")||a.toString().trim().equals("")?"0":a.toString().trim()));
		             cell8.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		             c++;
		             cell8 = row.createCell(c);
		             HSSFRichTextString b=new HSSFRichTextString(s.getString("RETAILSALE"));//零售价
		             cell8.setCellValue(Float.parseFloat(b.toString().trim().equals("")||b.toString().trim().equals("0")?"0.00":b.toString().trim()));
		             cell8.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		             c++;
		             cell8 = row.createCell(c);
		             HSSFRichTextString d=new HSSFRichTextString(s.getString("RETAILCURR"));//零售额
		             cell8.setCellValue(Float.parseFloat(d.toString().trim().equals("")||d.toString().trim().equals("0")?"0.00":d.toString().trim()));
		             cell8.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		             c++;
		             cell8 = row.createCell(c);
		             HSSFRichTextString e=new HSSFRichTextString(s.getString("COSTPRICE"));//成本价
		             cell8.setCellValue(Float.parseFloat(e.toString().trim().equals("")||e.toString().trim().equals("0")?"0.00":e.toString().trim()));
		             cell8.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		             c++;
		             cell8 = row.createCell(c);
		             HSSFRichTextString f=new HSSFRichTextString(s.getString("FIXEDCURR"));//成本额
		             cell8.setCellValue(Float.parseFloat(f.toString().trim().equals("")||f.toString().trim().equals("0")?"0.00":f.toString().trim()));
		             cell8.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		             size++;
		             hejirow++;
		            }
		           }
		        	HSSFRow row = sheet.createRow(hejirow);
		        	HSSFCell cell8 = row.createCell(1);
		        	cell8.setCellValue("合计");
		        	cell8 = row.createCell(sizenum+3);
		        	cell8.setCellValue(Float.parseFloat(totalamt));
		        	cell8.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		        	
		        	cell8 = row.createCell(sizenum+5);
		        	cell8.setCellValue(Float.parseFloat(totalcurr));
		        	cell8.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		        	
//		        	cell8 = row.createCell(sizenum+7);
//		        	cell8.setCellValue(Float.parseFloat(wholecurr));
//		        	cell8.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		        	
		        	cell8 = row.createCell(sizenum+7);
		        	cell8.setCellValue(Float.parseFloat(fixedcurr));
		        	cell8.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		        }
			 
	        	workbook.write(out);
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	}
	
	//导出库存盘点汇总表
			public static void getexpotchecktable(OutputStream out, AppData appData, String res){
				 JSONObject jsono= JSONObject.fromObject(res);
				    JSONArray list=jsono.getJSONArray("rows");
				    String title =  "库存盘点汇总表.xls";
				    // excel表头
					//String[] headers = {"货号","商品名称","单位"};
					List<String> headers=new ArrayList<>();
					
					// 声明一个工作薄
					HSSFWorkbook workbook = ActionUtil.exportExcel(title, headers);
					// excel工作薄
					HSSFSheet sheet = workbook.getSheetAt(0);
					 try{
					 if (list != null && list.size() > 0) {
				        	int size=0;
				        	for (int n = 0; n <= list.size(); n++) {
				              HSSFRow row;// 得到行
				              JSONObject s=list.getJSONObject(size);
				                	 if (s!= null) {
				                         if(n==0){
				                        	 row = sheet.createRow(0);
				                        	 HSSFCellStyle style = workbook.createCellStyle();
				                        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格
				                        	 style.setFillForegroundColor(HSSFColor.YELLOW.index);
				                        	 int c=1;
				                        	 
				                        	 if("3".equals(appData.showfooter)){
				                            	 HSSFCell cell4 = row.createCell(0);
					                             HSSFRichTextString text4=new HSSFRichTextString("店铺");
					                            // cell4.setCellStyle(style);
					                             cell4.setCellValue(text4);
				                             }else{
					                        	 HSSFCell cell0 = row.createCell(0);
					                             HSSFRichTextString text0=new HSSFRichTextString("货号");
					                            // cell0.setCellStyle(style);
					                             cell0.setCellValue(text0);
					                             c++;
					                             HSSFCell cell1 = row.createCell(1);
					                             HSSFRichTextString text1=new HSSFRichTextString("商品");
					                            // cell1.setCellStyle(style);
					                             cell1.setCellValue(text1);
					                             if("1".equals(appData.showfooter)){
					                            	 c++;
					                            	 HSSFCell cell2 = row.createCell(2);
						                             HSSFRichTextString text2=new HSSFRichTextString("颜色");
						                             //cell2.setCellStyle(style);
						                             cell2.setCellValue(text2);
					                             }else  if("2".equals(appData.showfooter)){
					                            	 c++;
					                            	 HSSFCell cell2 = row.createCell(2);
						                             HSSFRichTextString text2=new HSSFRichTextString("颜色");
						                             //cell2.setCellStyle(style);
						                             cell2.setCellValue(text2);
						                             c++;
					                            	 HSSFCell cell3 = row.createCell(3);
						                             HSSFRichTextString text3=new HSSFRichTextString("尺码");
						                            // cell3.setCellStyle(style);
						                             cell3.setCellValue(text3);
					                             }
					                             
				                             }
				                        	
				                         HSSFCell cell8 = row.createCell(c);
				                          HSSFRichTextString a=new HSSFRichTextString("账面数");//账面数
				                          //cell8.setCellStyle(style);
				                          cell8.setCellValue(a);
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString b=new HSSFRichTextString("实盘数");//实盘数
				                          //cell8.setCellStyle(style);
				                          cell8.setCellValue(b);
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString d=new HSSFRichTextString("盈亏数");//盈亏数
				                         // cell8.setCellStyle(style);
				                          cell8.setCellValue(d);
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString e=new HSSFRichTextString("盈亏金额");//盈亏金额
				                         // cell8.setCellStyle(style);
				                          cell8.setCellValue(e);
				                         }else{
				                        	 row = sheet.createRow(n);
				                        
				                        	 int c=1;
				                        	 
				                        	 if("3".equals(appData.showfooter)){
				                            	 HSSFCell cell4 = row.createCell(0);
					                             HSSFRichTextString text4=new HSSFRichTextString(s.getString("HOUSENAME"));
					                             cell4.setCellValue(text4);
				                             }else{
					                        	 HSSFCell cell0 = row.createCell(0);
					                             HSSFRichTextString text0=new HSSFRichTextString(s.getString("WARENO"));
					                             cell0.setCellValue(text0);
					                             c++;
					                             HSSFCell cell1 = row.createCell(1);
					                             HSSFRichTextString text1=new HSSFRichTextString(s.getString("WARENAME"));
					                             cell1.setCellValue(text1);
					                             if("1".equals(appData.showfooter)){
					                            	 c++;
					                            	 HSSFCell cell2 = row.createCell(2);
						                             HSSFRichTextString text2=new HSSFRichTextString(s.getString("COLORNAME"));
						                             cell2.setCellValue(text2);
					                             }else  if("2".equals(appData.showfooter)){
					                            	 c++;
					                            	 HSSFCell cell2 = row.createCell(2);
						                             HSSFRichTextString text2=new HSSFRichTextString(s.getString("COLORNAME"));
						                             cell2.setCellValue(text2);
						                             c++;
					                            	 HSSFCell cell3 = row.createCell(3);
						                             HSSFRichTextString text3=new HSSFRichTextString(s.getString("SIZENAME"));
						                             cell3.setCellValue(text3);
					                             }
					                             
				                             }
				                       
				                         HSSFCell cell8 = row.createCell(c);
				                          HSSFRichTextString a=new HSSFRichTextString(s.getString("BOOKAMT"));//账面数
				                          cell8.setCellValue(a.toString().replace(".00", "").equals("0")?"":a.toString().replace(".00", ""));
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString b=new HSSFRichTextString(s.getString("CHECKAMT"));//实盘数
				                          cell8.setCellValue(b.toString().replace(".00", "").equals("0")?"":b.toString().replace(".00", ""));
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString d=new HSSFRichTextString(s.getString("AMOUNT"));//盈亏数
				                          
				                          cell8.setCellValue(d.toString().replace(".00", "").equals("0")?"":d.toString().replace(".00", ""));
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString e=new HSSFRichTextString(s.getString("CURR"));//盈亏金额
				                          cell8.setCellValue(e.toString().trim().equals("0.00")?"":e.toString().trim());
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
			
			//导出订货会商品统计
			public static void exportinqorder(OutputStream out, AppData appData, String res){
				 JSONObject jsono= JSONObject.fromObject(res);
				    JSONArray list=jsono.getJSONArray("rows");
				    int sizenum = Integer.parseInt(appData.sizenum);//尺码个数
				    String title =  "订货会商品统计.xls";
				    // excel表头
					List<String> headers=new ArrayList<>();
					
					// 声明一个工作薄
					HSSFWorkbook workbook = ActionUtil.exportExcel(title, headers);
					// excel工作薄
					HSSFSheet sheet = workbook.getSheetAt(0);
					 try{
					 if (list != null && list.size() > 0) {
				        	int size=0;
				        	String SIZEGROUPNO="";
				        	String sizeg="";
				        	for (int n = 0; n < list.size(); n++) {
				              HSSFRow row;// 得到行
				              JSONObject s=list.getJSONObject(n);
				              SIZEGROUPNO=s.getString("SIZEGROUPNO");
				            if(!SIZEGROUPNO.equals(sizeg)){
				            	sizeg=SIZEGROUPNO;
				            	 for(int li=0;li<2;li++){
				                	 if (s!= null) {
				                         if(li==0){
				                        	 row = sheet.createRow(size);
				                        	 HSSFCellStyle style = workbook.createCellStyle();
				                        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格
				                        	 style.setFillForegroundColor(HSSFColor.YELLOW.index);
				                        	 int c=3;
				                        	 HSSFCell cell0 = row.createCell(0);
				                             HSSFRichTextString text0=new HSSFRichTextString("货号");
				                             cell0.setCellStyle(style);
				                             cell0.setCellValue(text0);
				                             HSSFCell cell1 = row.createCell(1);
				                             HSSFRichTextString text1=new HSSFRichTextString("商品名称");
				                             cell1.setCellStyle(style);
				                             cell1.setCellValue(text1);
				                             HSSFCell cell2 = row.createCell(2);
				                             HSSFRichTextString text2=new HSSFRichTextString("零售价");
				                             cell2.setCellStyle(style);
				                             cell2.setCellValue(text2);
			                            	 HSSFCell cell4 = row.createCell(3);
				                             HSSFRichTextString text4=new HSSFRichTextString("颜色");
				                             cell4.setCellStyle(style);
				                             cell4.setCellValue(text4);
				                             c++;
				                            for(int i=1;i<=sizenum;i++){
				                         	   HSSFCell cell3 = row.createCell(c);
				                                HSSFRichTextString text3=new HSSFRichTextString(s.getString("SIZENAME"+i));
				                                cell3.setCellStyle(style);
				                                cell3.setCellValue(text3);
				                                c++;
				                            }
				                            //c+=1;
				                         	HSSFCell cell8 = row.createCell(c);
				                          HSSFRichTextString a=new HSSFRichTextString("订货数量");//订货数量
				                          cell8.setCellStyle(style);
				                          cell8.setCellValue(a);
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString b=new HSSFRichTextString("订货金额");//订货金额
				                          cell8.setCellStyle(style);
				                          cell8.setCellValue(b);
				                         
				                          size++;
				                         }else{
				                        	 row = sheet.createRow(size);
				                        	 int c=3;
				                        	 HSSFCell cell0 = row.createCell(0);
				                             HSSFRichTextString text0=new HSSFRichTextString(s.getString("WARENO"));
				                             cell0.setCellValue(text0);
				                             HSSFCell cell1 = row.createCell(1);
				                             HSSFRichTextString text1=new HSSFRichTextString(s.getString("WARENAME"));
				                             cell1.setCellValue(text1);
				                             HSSFCell cell2 = row.createCell(2);
				                             HSSFRichTextString text2=new HSSFRichTextString(s.getString("RETAILSALE"));
				                             cell2.setCellValue(text2);
			                            	 HSSFCell cell4 = row.createCell(3);
				                             HSSFRichTextString text4=new HSSFRichTextString(s.getString("COLORNAME"));
				                             cell4.setCellValue(text4);
				                             c++;
				                            for(int i=1;i<=sizenum;i++){
				                                HSSFCell cell5 = row.createCell(c);
				                                HSSFRichTextString text5=new HSSFRichTextString(s.getString("AMOUNT"+i));
				                                cell5.setCellValue(text5.toString().trim().equals("0")||text5.toString().trim().equals("0.00")?"":text5.toString().trim());
				                                c++;
				                            
				                            }
				                            //c+=1;
				                         	HSSFCell cell8 = row.createCell(c);
				                          HSSFRichTextString a=new HSSFRichTextString(s.getString("AMOUNT"));//订货数量
				                          cell8.setCellValue(a.toString().trim().equals("0")||a.toString().trim().equals("0.00")?"":a.toString().trim());
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString b=new HSSFRichTextString(s.getString("RETAILCURR"));//订货金额
				                          cell8.setCellValue(b.toString().trim().equals("0.00")?"":b.toString().trim());
				                         
				                          size++;
				                         }
				                         
				                      }
				                 }
				            }else{
				            	row = sheet.createRow(size);
				           	 int c=3;
				           	 HSSFCell cell0 = row.createCell(0);
				                HSSFRichTextString text0=new HSSFRichTextString(s.getString("WARENO"));
				                cell0.setCellValue(text0);
				                HSSFCell cell1 = row.createCell(1);
				                HSSFRichTextString text1=new HSSFRichTextString(s.getString("WARENAME"));
				                cell1.setCellValue(text1);
				                HSSFCell cell2 = row.createCell(2);
				                HSSFRichTextString text2=new HSSFRichTextString(s.getString("RETAILSALE"));
				                cell2.setCellValue(text2);
		                       	 HSSFCell cell4 = row.createCell(3);
		                            HSSFRichTextString text4=new HSSFRichTextString(s.getString("COLORNAME"));
		                            cell4.setCellValue(text4);
		                            c++;
				               for(int i=1;i<=sizenum;i++){
				                   HSSFCell cell5 = row.createCell(c);
				                   HSSFRichTextString text5=new HSSFRichTextString(s.getString("AMOUNT"+i));
				                   cell5.setCellValue(text5.toString().trim().equals("0")||text5.toString().trim().equals("0.00")?"":text5.toString().trim());
				                   c++;
				               
				               }
				               //c+=1;
				            	HSSFCell cell8 = row.createCell(c);
				             HSSFRichTextString a=new HSSFRichTextString(s.getString("AMOUNT"));//订货数量
				             cell8.setCellValue(a.toString().trim().equals("0")?"":a.toString().trim());
				             c++;
				             cell8 = row.createCell(c);
				             HSSFRichTextString b=new HSSFRichTextString(s.getString("RETAILCURR"));//订货金额
				             cell8.setCellValue(b.toString().trim().equals("0.00")?"":b.toString().trim());
				            
				             size++;
				            }
				           }
				        }
			        	workbook.write(out);
			        } catch (IOException e) {
			        	e.printStackTrace();
			        }
			}
			//导出进销存汇总账
			public static void getinstableexport(OutputStream out, AppData appData, String res){
				 JSONObject jsono= JSONObject.fromObject(res);
				    JSONArray list=jsono.getJSONArray("rows");
				    String title =  "进销存汇总账.xls";
				    // excel表头
					List<String> headers=new ArrayList<>();
					
					// 声明一个工作薄
					HSSFWorkbook workbook = ActionUtil.exportExcel(title, headers);
					// excel工作薄
					HSSFSheet sheet = workbook.getSheetAt(0);
					 try{
					 if (list != null && list.size() > 0) {
				        	int size=0;
				        	for (int n = 0; n <= list.size(); n++) {
				              HSSFRow row;// 得到行
				              JSONObject s=list.getJSONObject(size);
				                	 if (s!= null) {
				                         if(n==0){
				                        	 row = sheet.createRow(0);
				                        	 HSSFCellStyle style = workbook.createCellStyle();
				                        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格
				                        	 style.setFillForegroundColor(HSSFColor.YELLOW.index);
				                        	 int c=1;
				                        	 
				                        	 HSSFCell cell0 = row.createCell(0);
				                             HSSFRichTextString text0=new HSSFRichTextString("货号");
				                            // cell0.setCellStyle(style);
				                             cell0.setCellValue(text0);
				                             c++;
				                             HSSFCell cell1 = row.createCell(1);
				                             HSSFRichTextString text1=new HSSFRichTextString("商品");
				                            // cell1.setCellStyle(style);
				                             cell1.setCellValue(text1);
				                             if("1".equals(appData.showfooter)){
				                            	 c++;
				                            	 HSSFCell cell2 = row.createCell(2);
					                             HSSFRichTextString text2=new HSSFRichTextString("颜色");
					                             //cell2.setCellStyle(style);
					                             cell2.setCellValue(text2);
				                             }else  if("2".equals(appData.showfooter)){
				                            	 c++;
				                            	 HSSFCell cell2 = row.createCell(2);
					                             HSSFRichTextString text2=new HSSFRichTextString("颜色");
					                             //cell2.setCellStyle(style);
					                             cell2.setCellValue(text2);
					                             c++;
				                            	 HSSFCell cell3 = row.createCell(3);
					                             HSSFRichTextString text3=new HSSFRichTextString("尺码");
					                            // cell3.setCellStyle(style);
					                             cell3.setCellValue(text3);
				                             }
					                             
				                        	
				                         HSSFCell cell8 = row.createCell(c);
				                          HSSFRichTextString a=new HSSFRichTextString("零售价");//零售价
				                          cell8.setCellValue(a);
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString b=new HSSFRichTextString("期初");//期初
				                          cell8.setCellValue(b);
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString d=new HSSFRichTextString("采购");//采购
				                          cell8.setCellValue(d);
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString e=new HSSFRichTextString("采购退货");//采购退货
				                          cell8.setCellValue(e);
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString f=new HSSFRichTextString("初始");//初始
				                          cell8.setCellValue(f);
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString g=new HSSFRichTextString("调入");//调入
				                          cell8.setCellValue(g);
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString h=new HSSFRichTextString("盘盈");//盘盈
				                          cell8.setCellValue(h);
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString i=new HSSFRichTextString("零售");//零售
				                          cell8.setCellValue(i);
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString j=new HSSFRichTextString("批发销售");//批发销售
				                          cell8.setCellValue(j);
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString k=new HSSFRichTextString("批发退库");//批发退库
				                          cell8.setCellValue(k);
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString l=new HSSFRichTextString("调出");//调出
				                          cell8.setCellValue(l);
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString m=new HSSFRichTextString("盘亏");//盘亏
				                          cell8.setCellValue(m);
				                          c++;
				                          cell8 = row.createCell(c);
				                          HSSFRichTextString o=new HSSFRichTextString("期末");//期末
				                          cell8.setCellValue(o);
				                         }else{
				                        	 row = sheet.createRow(n);
				                        
				                        	 int c=1;
				                        	 
				                       
				                        	 HSSFCell cell0 = row.createCell(0);
				                             HSSFRichTextString text0=new HSSFRichTextString(s.getString("WARENO"));
				                             cell0.setCellValue(text0);
				                             c++;
				                             HSSFCell cell1 = row.createCell(1);
				                             HSSFRichTextString text1=new HSSFRichTextString(s.getString("WARENAME"));
				                             cell1.setCellValue(text1);
				                             if("1".equals(appData.showfooter)){
				                            	 c++;
				                            	 HSSFCell cell2 = row.createCell(2);
					                             HSSFRichTextString text2=new HSSFRichTextString(s.getString("COLORNAME"));
					                             cell2.setCellValue(text2);
				                             }else  if("2".equals(appData.showfooter)){
				                            	 c++;
				                            	 HSSFCell cell2 = row.createCell(2);
					                             HSSFRichTextString text2=new HSSFRichTextString(s.getString("COLORNAME"));
					                             cell2.setCellValue(text2);
					                             c++;
				                            	 HSSFCell cell3 = row.createCell(3);
					                             HSSFRichTextString text3=new HSSFRichTextString(s.getString("SIZENAME"));
					                             cell3.setCellValue(text3);
				                             }
					                             
				                             
				                       
				                             HSSFCell cell8 = row.createCell(c);
					                          HSSFRichTextString a=new HSSFRichTextString(s.getString("RETAILSALE"));//零售价
					                          cell8.setCellValue(a.toString().trim().equals("0.00")||a.toString().trim().equals("0")?"":a.toString().trim());
					                          c++;
					                          cell8 = row.createCell(c);
					                          HSSFRichTextString b=new HSSFRichTextString(s.getString("AMOUNTQC"));//期初
					                          cell8.setCellValue(b.toString().replace(".00", "").equals("0")?"":b.toString().replace(".00", ""));
					                          c++;
					                          cell8 = row.createCell(c);
					                          HSSFRichTextString d=new HSSFRichTextString(s.getString("AMOUNTCG"));//采购
					                          cell8.setCellValue(d.toString().replace(".00", "").equals("0")?"":d.toString().replace(".00", ""));
					                          c++;
					                          cell8 = row.createCell(c);
					                          HSSFRichTextString e=new HSSFRichTextString(s.getString("AMOUNTCT"));//采购退货
					                          cell8.setCellValue(e.toString().replace(".00", "").equals("0")?"":e.toString().replace(".00", ""));
					                          c++;
					                          cell8 = row.createCell(c);
					                          HSSFRichTextString f=new HSSFRichTextString(s.getString("AMOUNTCS"));//初始
					                          cell8.setCellValue(f.toString().replace(".00", "").equals("0")?"":f.toString().replace(".00", ""));
					                          c++;
					                          cell8 = row.createCell(c);
					                          HSSFRichTextString g=new HSSFRichTextString(s.getString("AMOUNTDR"));//调入
					                          cell8.setCellValue(g.toString().replace(".00", "").equals("0")?"":g.toString().replace(".00", ""));
					                          c++;
					                          cell8 = row.createCell(c);
					                          HSSFRichTextString h=new HSSFRichTextString(s.getString("AMOUNTPY"));//盘盈
					                          cell8.setCellValue(h.toString().replace(".00", "").equals("0")?"":h.toString().replace(".00", ""));
					                          c++;
					                          cell8 = row.createCell(c);
					                          HSSFRichTextString i=new HSSFRichTextString(s.getString("AMOUNTLS"));//零售
					                          cell8.setCellValue(i.toString().replace(".00", "").equals("0")?"":i.toString().replace(".00", ""));
					                          c++;
					                          cell8 = row.createCell(c);
					                          HSSFRichTextString j=new HSSFRichTextString(s.getString("AMOUNTXS"));//批发销售
					                          cell8.setCellValue(j.toString().replace(".00", "").equals("0")?"":j.toString().replace(".00", ""));
					                          c++;
					                          cell8 = row.createCell(c);
					                          HSSFRichTextString k=new HSSFRichTextString(s.getString("AMOUNTXT"));//批发退库
					                          cell8.setCellValue(k.toString().replace(".00", "").equals("0")?"":k.toString().replace(".00", ""));
					                          c++;
					                          cell8 = row.createCell(c);
					                          HSSFRichTextString l=new HSSFRichTextString(s.getString("AMOUNTDC"));//调出
					                          cell8.setCellValue(l.toString().replace(".00", "").equals("0")?"":l.toString().replace(".00", ""));
					                          c++;
					                          cell8 = row.createCell(c);
					                          HSSFRichTextString m=new HSSFRichTextString(s.getString("AMOUNTPK"));//盘亏
					                          cell8.setCellValue(m.toString().replace(".00", "").equals("0")?"":m.toString().replace(".00", ""));
					                          c++;
					                          cell8 = row.createCell(c);
					                          HSSFRichTextString o=new HSSFRichTextString(s.getString("AMOUNTQM"));//期末
					                          cell8.setCellValue(o.toString().replace(".00", "").equals("0")?"":o.toString().replace(".00", ""));
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
