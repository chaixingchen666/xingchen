package mains.receptionsales.changeclass;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.flyang.tools.ActionUtil;
import com.flyang.tools.FlyangHttp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;

public class ChangeClassBase {

	//http://119.84.84.173:94/interface/ebdress.aspx?action=houseworkexists&houseid=[店铺id] 店铺开班判断
	//http://119.84.84.173:94/interface/ebdress.aspx?action=houseworkopen&houseid=[店铺id]&lastop=[用户名] 店铺开班，下面是参数
	//post参数:要输入上班结存和开班时间
	//TXT_currqc=上班结存
	//TXT_begindate=开班时间（格式:yyyymmddhhmiss）
	//http://119.84.84.173:94/interface/ebdress.aspx?action=houseworkcancel&classid=[班次id] 取消店铺当前班次
	//http://119.84.84.173:94/interface/ebdress.aspx?action=houseworktotal&classid=[班次id]&lastop=[用户名]店铺收银员交班统计
	//http://119.84.84.173:94/interface/ebdress.aspx?action=houseworkcancel&classid=[班次id] 撤班
	//http://119.84.84.173:94/interface/ebdress.aspx?action=houseworkclose&classid=[班次id]&lastop=[用户名] 交班，下面是参数
//	post参数: 
//		TXT_currxj=现金收入（必传）
//		TXT_curryh=银行存款（必传）
//		TXT_currsjye=实际余额（必传）
//		TXT_remark=备注(可传)
		
		public static String getopclass(AppData appData, String houseid) throws IOException {// 店铺开班判断
			String url = DataBase.HostIP + "houseworkexists&epid="+appData.epid+"&accid="+appData.accid+"&houseid="+houseid
					+"&lastop=" +  DataBase.encode(appData.epname)+ DataBase.signature(appData);
			
			String jsonstr = DataBase.getJsonContent(url);
			if (appData.isSyserror(jsonstr)) {
				return jsonstr;
			}else{
				return jsonstr;
			}
		}
		public static String gethouseworkopen(AppData appData, String houseid, String begindate, String currqc) throws IOException {// 开班
			if(null==currqc){
				currqc="0";
			}else{
//				currqc=currqc;
			}
			if(null==currqc||""==currqc){
				currqc="0";
			}
			String url = DataBase.HostIP + "houseworkopen&epid="+appData.epid+"&lastop=" +  DataBase.encode(appData.epname)
			+"&houseid="+houseid+"&accid="+appData.accid+ DataBase.signature(appData);
			HashMap<String,String> params = new HashMap<String,String>();
			params.put("currqc", currqc);
			params.put("begindate", begindate);
			String jsonstr = DataBase.postJsonContent(url, params);
			if (appData.isResult(jsonstr)) {
				return jsonstr;
			}else{
				return jsonstr;
			}
		}
		
		public static String houseworkcancel(AppData appData, String classid) throws IOException {// 撤班
			String url = DataBase.HostIP + "houseworkcancel&page="+"&epid="+appData.epid+"&lastop=" +  DataBase.encode(appData.epname)
			+"&classid="+classid+ DataBase.signature(appData);			
			String jsonstr = DataBase.getJsonContent(url);
			if (appData.isTotal(jsonstr)) {
				return jsonstr;
			}else{
				return jsonstr;
			}
		}
		
		public static String houseworkclose(AppData appData, String currsjye, String currxj, String curryh, String remark, String CURRJC, String Currqc, String CURRYE, String CURRYK, String classid) throws IOException {// 交班
			String url = DataBase.HostIP + "houseworkclose&page="+"&epid="+appData.epid+"&lastop=" +  DataBase.encode(appData.epname)
			+"&accid="+appData.accid+"&classid="+classid+ DataBase.signature(appData);
			
			HashMap<String,String> params = new HashMap<String,String>();
			params.put("currxj", currxj);
			params.put("curryh", curryh);
			params.put("currsjye", currsjye);
			params.put("currqc", Currqc);
			params.put("currye", CURRYE);
			params.put("curryk", CURRYK);
			params.put("currjc", CURRJC);
			if(remark!=""&&remark!=null){
				params.put("remark", remark);				
			}
			String jsonstr = DataBase.postJsonContent(url, params);
			if (appData.isTotal(jsonstr)) {
				return jsonstr;
			}else{
				return jsonstr;
			}
		}
		
		public static String gethouseworktotal(AppData appData, String classid, String houseid) throws IOException {// 店铺收银员交班统计
			FlyangHttp fh = new FlyangHttp();
			fh.setHostIP(DataBase.JERPIP+"api?action=");
			JSONObject dataobj = new JSONObject();
			dataobj.put("classid", classid);
			if (!"0".equals(houseid) && null != houseid && !"".equals(houseid)) {
				dataobj.put("houseid", houseid);
			}
			return fh.FlyangdoPost("houseworktotal", dataobj, appData).toString();
		}
		//导出详细交班记录
		public static void houseworktotalexp(OutputStream out, AppData appData, String res){
			    int AMOUNT=0;
				Double CURR=0.0;
				Double CURR0=0.0;
				Double CURR1=0.0;
				Double CURR2=0.0;
				Double PAYCURR0=0.0;
				Double PAYCURR1=0.0;
				Double PAYCURR2=0.0;
				JSONObject jsonObj= JSONObject.fromObject(res);
				JSONArray jsonArray0=jsonObj.getJSONArray("XSLIST");
				JSONArray jsonArray1=jsonObj.getJSONArray("FYLIST");
				JSONArray jsonArray2=jsonObj.getJSONArray("PAYCURRLIST");
//				String XSLIST= jsonObj.getString("XSLIST");
//				String FYLIST= jsonObj.getString("FYLIST");
//				String PAYCURRLIST= jsonObj.getString("PAYCURRLIST");
				
				String MINDATE=jsonObj.getString("MINDATE");
				String MAXDATE=jsonObj.getString("MAXDATE");
				String LASTOP=jsonObj.getString("LASTOP");
				String CURRQC=jsonObj.getString("CURRQC");
				String CURRYE=jsonObj.getString("CURRYE");
				String CURRSJYE=jsonObj.getString("CURRSJYE");
				String CURRYK=jsonObj.getString("CURRYK");
				String CURRYH=jsonObj.getString("CURRYH");
				String CURRJC=jsonObj.getString("CURRJC");
//				String CURRXJ=jsonObj.getString("CURRXJ");
				String REMARK=jsonObj.getString("REMARK");
				for(int i=0;i<jsonArray0.size();i++){
					JSONObject s=jsonArray0.getJSONObject(i);
					AMOUNT+=Double.parseDouble(s.getString("AMOUNT"));
					CURR+=Double.parseDouble(s.getString("CURR"));
				}
				for(int i=0;i<jsonArray1.size();i++){
					JSONObject s=jsonArray1.getJSONObject(i);
					CURR0+=Double.parseDouble(s.getString("CURR0"));
					CURR1+=Double.parseDouble(s.getString("CURR1"));
					CURR2+=Double.parseDouble(s.getString("CURR2"));
				}
				for(int i=0;i<jsonArray2.size();i++){
					JSONObject s=jsonArray2.getJSONObject(i);
					PAYCURR0+=Double.parseDouble(s.getString("PAYCURR0"));
					PAYCURR1+=Double.parseDouble(s.getString("PAYCURR1"));
					PAYCURR2+=Double.parseDouble(s.getString("PAYCURR2"));
				}
				
				
			    String title =  "详细交班记录.xls";
			    // excel表头
				List<String> headers=new ArrayList<>();
				
				// 声明一个工作薄
				HSSFWorkbook workbook = ActionUtil.exportExcel(title, headers);
				// excel工作薄
				try {
				HSSFSheet sheet = workbook.getSheetAt(0);
				 HSSFRow row=sheet.createRow(0);// 得到行
				 HSSFCell cell2 = row.createCell(0);
				 cell2.setCellValue("开始日期"+MINDATE);
				 cell2 = row.createCell(1);
				 cell2.setCellValue("当前日期"+MAXDATE);
				 cell2 = row.createCell(2);
				 cell2.setCellValue("店铺"+appData.showfooter);
				 cell2 = row.createCell(3);
				 cell2.setCellValue("销售人"+LASTOP);
				 
				 row=sheet.createRow(1);
				 cell2 = row.createCell(0);
				 cell2.setCellValue("销售汇总");
				
				 row=sheet.createRow(2);
				 cell2 = row.createCell(0);
				 cell2.setCellValue("货号");
				 cell2 = row.createCell(1);
				 cell2.setCellValue("商品");
				 cell2 = row.createCell(2);
				 cell2.setCellValue("数量");
				 cell2 = row.createCell(3);
				 cell2.setCellValue("金额");
				 int c=3;
				 for (int i = 0; i < jsonArray0.size(); i++) {
					 JSONObject s=jsonArray0.getJSONObject(i);
					 row=sheet.createRow(c);
					 
					 cell2 = row.createCell(0);
					 cell2.setCellValue(s.getString("WAREID"));
					 cell2 = row.createCell(1);
					 cell2.setCellValue(s.getString("WARENAME"));
					 cell2 = row.createCell(2);
					 cell2.setCellValue(s.getString("AMOUNT"));
					 cell2 = row.createCell(3);
					 cell2.setCellValue(s.getString("CURR"));
					 c++;
					 
				 }
				 
				 row=sheet.createRow(c);
				 cell2 = row.createCell(0);
				 cell2.setCellValue("小计");
				 cell2 = row.createCell(1);
				 cell2.setCellValue(" ");
				 cell2 = row.createCell(2);
				 cell2.setCellValue(AMOUNT);
				 cell2 = row.createCell(3);
				 cell2.setCellValue(CURR);
				 c++;
				 c++;
				 row=sheet.createRow(c);
				 cell2 = row.createCell(0);
				 cell2.setCellValue("费用汇总");
				 c++;
				 row=sheet.createRow(c);
				 cell2 = row.createCell(0);
				 cell2.setCellValue("项目名称");
				 cell2 = row.createCell(1);
				 cell2.setCellValue("支出");
				 cell2 = row.createCell(2);
				 cell2.setCellValue("收入");
				 cell2 = row.createCell(3);
				 cell2.setCellValue("金额");
				 for (int i = 0; i < jsonArray1.size(); i++) {
					 c++;
					 row=sheet.createRow(c);
					 JSONObject s=jsonArray1.getJSONObject(i);
					 cell2 = row.createCell(0);
					 cell2.setCellValue(s.getString("CGNAME"));
					 cell2 = row.createCell(1);
					 cell2.setCellValue(s.getString("CURR0"));
					 cell2 = row.createCell(2);
					 cell2.setCellValue(s.getString("CURR1"));
					 cell2 = row.createCell(3);
					 cell2.setCellValue(s.getString("CURR2"));
					 
					 
				 }
				 c++;
				 row=sheet.createRow(c);
				 cell2 = row.createCell(0);
				 cell2.setCellValue("小计");
				 cell2 = row.createCell(1);
				 cell2.setCellValue(CURR0);
				 cell2 = row.createCell(2);
				 cell2.setCellValue(CURR1);
				 cell2 = row.createCell(3);
				 cell2.setCellValue(CURR2);
				 
				 c++;
				 c++;
				 row=sheet.createRow(c);
				 cell2 = row.createCell(0);
				 cell2.setCellValue("收支汇总");
				 c++;
				 row=sheet.createRow(c);
				 cell2 = row.createCell(0);
				 cell2.setCellValue("结算方式");
				 cell2 = row.createCell(1);
				 cell2.setCellValue("销售收入");
				 cell2 = row.createCell(2);
				 cell2.setCellValue("费用支出");
				 cell2 = row.createCell(3);
				 cell2.setCellValue("小计");
				 for (int i = 0; i < jsonArray2.size(); i++) {
					 c++;
					 row=sheet.createRow(c);
					 JSONObject s=jsonArray2.getJSONObject(i);
					 cell2 = row.createCell(0);
					 cell2.setCellValue(s.getString("PAYNAME"));
					 cell2 = row.createCell(1);
					 cell2.setCellValue(s.getString("PAYCURR0"));
					 cell2 = row.createCell(2);
					 cell2.setCellValue(s.getString("PAYCURR1"));
					 cell2 = row.createCell(3);
					 cell2.setCellValue(s.getString("PAYCURR2"));
					 
				 }
				 c++;
				 row=sheet.createRow(c);
				 cell2 = row.createCell(0);
				 cell2.setCellValue("小计");
				 cell2 = row.createCell(1);
				 cell2.setCellValue(PAYCURR0);
				 cell2 = row.createCell(2);
				 cell2.setCellValue(PAYCURR1);
				 cell2 = row.createCell(3);
				 cell2.setCellValue(PAYCURR2);
				 
				 c++;
				 c++;
				 row=sheet.createRow(c);
				 cell2 = row.createCell(0);
				 cell2.setCellValue("现金");
				 
				 c++;
				 row=sheet.createRow(c);
				 cell2 = row.createCell(0);
				 cell2.setCellValue("上班结存"+CURRQC);
				 cell2 = row.createCell(1);
				 cell2.setCellValue("本班余额"+CURRYE);
				 cell2 = row.createCell(2);
				 cell2.setCellValue("损益金额"+CURRYK);
				 c++;
				 row=sheet.createRow(c);
				 cell2 = row.createCell(0);
				 cell2.setCellValue("实际金额"+CURRSJYE);
				 cell2 = row.createCell(1);
				 cell2.setCellValue("银行存款"+CURRYH);
				 cell2 = row.createCell(2);
				 cell2.setCellValue("本班结存"+CURRJC);
				 c++;
				 c++;
				 row=sheet.createRow(c);
				 cell2 = row.createCell(0);
				 cell2.setCellValue("备注");
				 c++;
				 row=sheet.createRow(c);
				 cell2 = row.createCell(0);
				 cell2.setCellValue(REMARK);
				 
					workbook.write(out);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
}
		
