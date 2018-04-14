package com.flyang.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;
import tools.ExtendBase;

/**
 * Servlet implementation class ImportServlet
 * 调用：导入
 */
@WebServlet("/fyimportservice")
public class ImportService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImportService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String action = request.getParameter("importser");
		String server = request.getParameter("server");
		//下面没设置IP
		if(server==null)
			server = "api";
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		HttpSession session = request.getSession(true);
		AppData appData = new AppData();
		appData.setqxpublic(request, response, session);
		if (action!=null&&action.length()>0){
			ImportXls(server,action, request, response, appData);
		}
	}
	private void ImportXls(String server, String action, HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);// 通过工厂生成一个处理文件上传的servlet对象
//		PrintWriter out = response.getWriter();
		
		try {
			List<FileItem> items = servletFileUpload.parseRequest(request);// 解析request
			Iterator<FileItem> iterator = items.iterator();
			JSONObject everyObj = new JSONObject();
			while (iterator.hasNext()) {
				FileItem item = (FileItem) iterator.next();
				if (item.isFormField()) {// 表单的参数字段
					if(DataBase.isCS)System.out.println("表单的参数名称：" + item.getFieldName() + ",表单的参数值：" + item.getString("UTF-8"));
					if(!item.getFieldName().equals("")){
						if(!item.getFieldName().equals("id")&&!item.getFieldName().equals("name")
								&&!item.getFieldName().equals("type")
								&&!item.getFieldName().equals("lastModifiedDate")
								&&!item.getFieldName().equals("size"))
						everyObj.put(item.getFieldName(), item.getString("UTF-8"));
					}
				} else{
					if (item.getName() != null && !item.getName().equals("")) {// 一个上传的文件
						if(DataBase.isCS)System.out.println("文件的名称：" + item.getName());
						if(DataBase.isCS)System.out.println("文件的大小：" + item.getSize());
						if(DataBase.isCS)System.out.println("文件的类型：" + item.getContentType());
						int countFive = item.getName().lastIndexOf(".");
	                    String filetype = item.getName().substring(countFive + 1);// 扩展名
	                    ExtendBase extendBase = new ExtendBase();
						extendBase.setAction(action);
						List<String> fields = new ArrayList<>();
						List<String> fieldsname = new ArrayList<>();
						List<String> msgfields = new ArrayList<>();
						List<String> validefields = new ArrayList<>();
						String channel = "";
						boolean validemsgbool = true; //判断必要条件满足一个(false)就好还是都要满足(true)。
						switch (action) {
						case "appendarea":
							fields = Arrays.asList("areaid","areaname","noused");
							fieldsname = Arrays.asList("区位ID","区位","禁用");
							msgfields = Arrays.asList("areaid","areaname","noused");
							validefields = Arrays.asList("areaname");
							channel = "areacode";
							break;
						case "appendbrand":
							fields = Arrays.asList("brandid","brandname","noused");
							fieldsname = Arrays.asList("品牌ID","品牌","禁用");
							msgfields = Arrays.asList("brandid","brandname","noused");
							validefields = Arrays.asList("brandname");
							channel = "brandcode";
							break;
						case "appendcolor":
							fields = Arrays.asList("colorid","colorname","colorno","brandname","noused");
							fieldsname = Arrays.asList("颜色ID","颜色","色码","品牌","禁用");
							msgfields = Arrays.asList("colorname");
							validefields = Arrays.asList("colorname");
							channel = "colorcode";
							break;
						case "appendcharges":
							fields = Arrays.asList("cgid","cgname","noused");
							fieldsname = Arrays.asList("费用ID","费用","禁用");
							msgfields = Arrays.asList("charges");
							validefields = Arrays.asList("cgname");
							channel = "charges";
							break;
						case "appendprovide":
							fields = Arrays.asList("provid","provname","linkman","mobile","tel","discount","pricetype","address","postcode","bankname","accountno","taxno","remark","noused");
							fieldsname = Arrays.asList("供应商ID","供应商","联系人","移动电话","联系电话","折扣","价格方式","地址","邮编","开户银行","账号","税号","备注","禁用");
							msgfields = Arrays.asList("provname");
							validefields = Arrays.asList("provname");
							channel = "provide";
							break;
						case "addwarecolor":
							fields = Arrays.asList("wareno", "colorname");
							fieldsname = Arrays.asList("货号", "颜色");
							msgfields = Arrays.asList("wareno","colorname");
							validefields = Arrays.asList("wareno","colorname");
							channel = "warecolor";
							break;
						case "appendwarecode":
							fields = Arrays.asList("wareid", "wareno", "warename", "gbbar", "zxbz", "units","model", "brandname","provname", "seasonname",
									"typename0","typename", "prodyear", "prodno", "sizegroupno", "colorlist","entersale", "retailsale", "sale4", "sale5", "sale1",
									"sale2", "sale3", "wavename","areaname","locale", "remark", "useritem1", "useritem2", "useritem3", "useritem4", "useritem5","noused","tjtag");
							fieldsname = Arrays.asList("商品ID", "货号", "商品名称", "国标码", "执行标准", "单位", "规格","品牌","生产商", "季节", "商品大类", "商品小类", "生产年份", "厂家编码",
									"尺码组", "颜色","进价", "零售价", "批发价", "打包价", "售价1", "售价2", "售价3", "波次", "区位","货位","备注", "面料", "里料", "配料", "毛料", "填充物","禁用","特价");
//							if(appData.autowareno.equals("0")) {
//								msgfields = Arrays.asList("wareno");
//								validefields = Arrays.asList("wareno");
//							}else{
								msgfields = Arrays.asList("");
								validemsgbool = false;
								validefields = Arrays.asList("wareid","wareno");
//							}
							channel = "warecode";
							break;
						case "appendwarebarcode":
							fields = Arrays.asList("barcode", "wareno", "colorname", "sizegroupno", "sizename","prtok");
							fieldsname = Arrays.asList("条码", "货号", "颜色", "尺码组", "尺码","允许打印");
							msgfields = Arrays.asList("barcode");
							validefields = Arrays.asList("barcode");
							channel = "warebarcode";
							break;
						case "appendcustomer":
							fields = Arrays.asList("custid", "custname", "linkman", "mobile", "tel", "discount", "pricetype", "address",
									"postcode", "bankname", "accountno", "taxno", "currbzj", "creditcurr", "remark", "creditok", "areaname","noused");
							fieldsname = Arrays.asList("客户ID", "客户名称", "联系人", "移动电话", "联系电话", "折扣", "价格方式", "地址", "邮编", "开户银行", "账号", "税号",
									"保证金", "信用额度", "备注", "启用信用控制", "区域", "禁用");
							msgfields = Arrays.asList("custname");
							validefields = Arrays.asList("custname");
							channel = "custcode";
							break;
						case "appendemploye":
							fields = Arrays.asList("epid", "epname", "epno", "sex", "idno", "tel", "mobile", "address",
									"postcode", "duty", "workdate", "yhrate", "ontime", "offtime", "levelname", "housename", "remark","passkey","noused");
							fieldsname = Arrays.asList("用户ID", "姓名", "用户账号", "性别", "身份证号", "固定电话", "移动电话", "地址", "邮编", "职务", "入职日期", "优惠率",
									"起始时间", "截止时间", "角色", "所属店铺","备注", "允许登录", "禁用");
							msgfields = Arrays.asList("epname");
							validefields = Arrays.asList("epname");
							channel = "employe";
							break;
						case "appendguestvip":
							fields = Arrays.asList("guestid", "guestname","sex","vtname","mobile", "vipno", 
									"housename","validdate", "birthday", "likesome", "stature", "fitsize", "remark","usetag");
							fieldsname = Arrays.asList("会员ID", "会员姓名","性别", "会员类型","移动电话","卡号","店铺", "有效日期","出生日期", "爱好",
									"身高", "尺码", "备注","状态");
							msgfields = Arrays.asList("guestname","vipno");
							validemsgbool = false;
							validefields = Arrays.asList("guestid","guestname","vipno");
							channel = "vipinfo";
							break;
						case "appendguestcentorcurr":
							fields = Arrays.asList("vipno", "balcent", "balcurr");
							fieldsname = Arrays.asList("卡号", "初始积分", "初始余额");
							msgfields = Arrays.asList("vipno");
							validefields = Arrays.asList("vipno","vipno","balcurr");
							channel = "vipcurr";
							break;
						case "appendomuserxls":
							fields = Arrays.asList("omepid", "custname","omepname", "mobile", "remark", "omepno", "password", "js", "jhamt",
									"jhcurr", "jhnum");
							fieldsname = Arrays.asList("用户ID", "客户名称", "用户姓名", "移动电话",  "备注", "用户账号", "密码", "订货方式", "订货量", "订货额", "订货款");
							msgfields = Arrays.asList("custname","omepname","omepno");
							validefields = Arrays.asList("custname","omepname","omepno");
							channel = "omuser";
							break;
						case "addwarewarnxls":
							fields = Arrays.asList("housename", "wareno","colorname","sizename", "minamt", "maxamt");
							fieldsname = Arrays.asList("店铺", "货号", "颜色", "尺码",  "最小库存", "最大库存");
							msgfields = Arrays.asList("housename","wareno","colorname","sizename");
							validefields = Arrays.asList("housename","wareno","colorname","sizename");
							channel = "warewarn";
							break;
						case "addfirsthousemxls":
							fields = Arrays.asList("wareno","colorname","sizename","amount");
							fieldsname = Arrays.asList("货号", "颜色","尺码","数量");
							msgfields = Arrays.asList("wareno","colorname","sizename");
							validefields = Arrays.asList("wareno","colorname","sizename");
							channel = "firsthouse";
							break;
						case "addwareinmxls":
							fields = Arrays.asList("wareno","colorname","sizename","amount","price","curr");
							fieldsname = Arrays.asList("货号", "颜色","尺码","数量","单价","金额");
							msgfields = Arrays.asList("wareno","colorname","sizename");
							validefields = Arrays.asList("wareno","colorname","sizename");
							channel = "warein";
							break;
						case "addallotoutmxls":
							fields = Arrays.asList("wareno","colorname","sizename","amount","price","curr");
							fieldsname = Arrays.asList("货号", "颜色","尺码","数量","单价","金额");
							msgfields = Arrays.asList("wareno","colorname","sizename");
							validefields = Arrays.asList("wareno","colorname","sizename");
							channel = "allotout";
							break;
						case "addcustordermxls":
							fields = Arrays.asList("wareno","gbbar","colorname","sizename","amount","price","curr");
							fieldsname = Arrays.asList("货号","国标码", "颜色","尺码","数量","单价","金额");
							msgfields = Arrays.asList("wareno","gbbar");
							validemsgbool = false;
							validefields = Arrays.asList("wareno","gbbar");
							channel = "custorder";
							break;
						case "addprovordermxls":
							fields = Arrays.asList("wareno","gbbar","colorname","sizename","amount","price","curr");
							fieldsname = Arrays.asList("货号","国标码", "颜色","尺码","数量","单价","金额");
							msgfields = Arrays.asList("wareno","gbbar");
							validemsgbool = false;
							validefields = Arrays.asList("wareno","gbbar");
							channel = "provorder";
							break;
						case "addfirstpaycurrxls":
							fields = Arrays.asList("provname","housename","curr","remark","handno");
							fieldsname = Arrays.asList("供应商","店铺","金额","摘要","自编号");
							msgfields = Arrays.asList("provname","housename");
							validefields = Arrays.asList("provname","housename");
							channel = "firstpaycurr";
							break;
						case "addfirstincomecurrxls":
							fields = Arrays.asList("custname","housename","curr","remark","handno");
							fieldsname = Arrays.asList("客户","店铺","金额","摘要","自编号");
							msgfields = Arrays.asList("custname","housename");
							validefields = Arrays.asList("custname","housename");
							channel = "firstincomecurr";
							break;
						case "addwareadjustmxls":
							fields = Arrays.asList("wareno","retailsale", "sale1", "sale2", "sale3", "sale4", "sale5", "checksale");
							fieldsname = Arrays.asList("货号","零售价","售价一","售价二","售价三","批发价","打包价","配送价");
							msgfields = Arrays.asList("wareno");
							validefields = Arrays.asList("wareno");
							channel = "wareprice";
							break;
						case "addtempcheckmxls":
							fields = Arrays.asList("wareno","colorname","sizename","amount");
							fieldsname = Arrays.asList("货号","颜色","尺码","数量");
							msgfields = Arrays.asList("wareno");
							validefields = Arrays.asList("wareno");
							channel = "tempcheckm";
							break;
						case "addcustsalemxls":
							fields = Arrays.asList("wareno","colorname","sizename","amount","price","curr");
							fieldsname = Arrays.asList("货号", "颜色","尺码","数量","单价","金额");
							msgfields = Arrays.asList("wareno","colorname","sizename");
							validefields = Arrays.asList("wareno","colorname","sizename");
							channel = "custsale";
							break;
						case "excel2wareout":
							fields = Arrays.asList("handno","notedate","housename","vipno","saleman","remark","wareno","colorname","sizename","amount","price","curr","remark0");
							fieldsname = Arrays.asList("单据号", "日期","店铺","会员卡号","销售人","摘要","货号","颜色","尺码","数量","单价","金额","备注");
							msgfields = Arrays.asList("handno");
							validefields = Arrays.asList("handno");
							channel = "waresaleh";
							break;
						case "loadsalepromwarexls":
							fields = Arrays.asList("wareno","discount","price","iszp");
							fieldsname = Arrays.asList("货号", "折扣","单价","赠品");
							msgfields = Arrays.asList("wareno");
							validefields = Arrays.asList("wareno");
							channel = "salepromware";
							break;
						default:
							break;
						}
						extendBase.setFields((String[])fields.toArray());
						extendBase.setFieldsname((String[])fieldsname.toArray());
						extendBase.setMsgFields((String[])msgfields.toArray());
						extendBase.setValidefields((String[])validefields.toArray());
						extendBase.setValideMsgBool(validemsgbool);
						extendBase.setChannel(channel);
						extendBase.setServer(server);
						if(filetype.toLowerCase().contains("xls")) {
							if(action.equals("excel2wareout"))
								extendBase.j_importxlsreadnote(filetype, everyObj,item.getInputStream(), appData);
							else
								extendBase.j_importxlsread(filetype, everyObj,item.getInputStream(), appData);
						}
						else if(filetype.toLowerCase().contains("txt")&&action.equals("addtempcheckmxls"))
							extendBase.importtxtread(filetype, everyObj,item.getInputStream(), appData);
						if(DataBase.isCS)System.out.println("上传文件成功！");
					} else {
						if(DataBase.isCS)System.out.println("没有选择上传文件！");
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
