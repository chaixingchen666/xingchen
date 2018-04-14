package com.flyang.pay;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.flyang.pay.config.PayService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;

/**
 * Servlet implementation class PayServlet
 */
@WebServlet(description = "支付宝支付", urlPatterns = { "/dopay" })
public class PayServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PayServlet() {
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
		response.getWriter().append("Request only by POST!");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		HttpSession session = request.getSession(true);
		AppData appData = new AppData();
		appData.setqxpublic(request, response, session);
		Pay skypay = new Pay();
		String ywtag = request.getParameter("ywtag");
		String payfs = request.getParameter("payfs");
		String noteno = request.getParameter("noteno");
		String totalamt = request.getParameter("totalamt");
		String totalcurr = request.getParameter("totalcurr");
		String paycurr = request.getParameter("paycurr");
		String paycode = request.getParameter("paycode");
		String subject = appData.housename + "购物消费";
		String body = "购买" + totalamt + "件商品支付" + paycurr  + "元;销售单号："+noteno;
		HashMap<String, String> paymap = new HashMap<>();
		String randomNo =  DataBase.getRandomNo();//19位随机数
		String outtradeno = noteno+randomNo;
		paymap.put("outTradeNo",outtradeno);
		paymap.put("subject", subject);
		paymap.put("totalAmount", paycurr);
		paymap.put("authCode", paycode);
		paymap.put("body", body);
		paymap.put("operatorId", appData.epid);
		paymap.put("storeId", appData.houseid);
		PrintWriter out = response.getWriter();
		try {
			int payres = 0;
			String paymsg = "";
			String zfnoteno = "";
			skypay.setPaymap(paymap);
			JSONObject adddataObj = JSONObject.fromObject("{}");
			adddataObj.put("ywtag", ywtag); //0=店铺零售，1=商场零售，2=线上,3=批发
			adddataObj.put("ywnoteno", noteno); //业务单据号
			adddataObj.put("payfs", payfs); //
			adddataObj.put("curr", paycurr);
			adddataObj.put("totalcurr", totalcurr);
			adddataObj.put("outtradeno", outtradeno);
			adddataObj.put("remark", subject+";"+body);
			//生成对应ed支付流水
			String addres = DataBase.postRequest("adddealrecord", adddataObj, appData);
			JSONObject resObj = JSONObject.fromObject(addres);
			if(appData.isResult(addres)){
				PayService payser = new PayService();
				zfnoteno = resObj.getString("noteno");
				JSONArray payKeyArr = resObj.getJSONArray("pay_zfb");
				JSONObject payKeyObj = payKeyArr.getJSONObject(0);
				JSONObject updatedataObj = JSONObject.fromObject("{}");
				updatedataObj.put("noteno", zfnoteno);
				//初始化支付对象
				boolean conf =  payser.init(payKeyObj);
				if(conf){
					//支付请求
					String res = skypay.pay(payser.getTradeService());
					JSONObject payresObj = JSONObject.fromObject(res);
					if(payresObj.getInt("result")>0){
						String payidstr = payresObj.getString("PAYIDSTR");
						String payaccno = payresObj.getString("PAYACCNO");
						updatedataObj.put("statetag", 1);
						updatedataObj.put("payidstr", payidstr);
						updatedataObj.put("payaccno", payaccno);
						payres = 1;
					}else{
						String msg = payresObj.getString("msg");
						updatedataObj.put("failedremark", msg);
						updatedataObj.put("statetag", 2);
						payres = 0;
					}
					paymsg += payresObj.getString("msg");
					//更新的支付记录
					String updateres = DataBase.postRequest("updatedealrecord", updatedataObj, appData);
					
					if(appData.isResult(updateres)){
						paymsg += ",记账成功！" ;
					}else{
						paymsg += ",记账失败！" + appData.msg;
					}
				}else{
					updatedataObj.put("statetag", 2);
					payres = 0;
					paymsg += "该账户未被授权使用支付！需要开通，请联系官方客户QQ办理：3329713977";
				}
			}else{
				payres = 0;
				paymsg += "未生成记账记录，无法支付！";
			}
			JSONObject payresObj = JSONObject.fromObject("{}");
			payresObj.put("result", payres);
			payresObj.put("msg", paymsg);
			payresObj.put("zfnoteno", zfnoteno);
			out.println(payresObj.toString());
		} catch (Exception e) {
			// TODO: handle exception
			out.println( "{\"result\":\"0\",\"msg\":\"支付异常！\"}");
			e.printStackTrace();
		}
		out.close();
	}

}
