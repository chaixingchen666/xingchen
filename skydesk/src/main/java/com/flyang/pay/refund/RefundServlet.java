package com.flyang.pay.refund;

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
 * Servlet implementation class RefundServlet
 */
@WebServlet("/dorefund")
public class RefundServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefundServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		RefundPay skyrefund = new RefundPay();
		String ywtag = request.getParameter("ywtag");
		String payfs = request.getParameter("payfs");
		String noteno = request.getParameter("noteno");
		String outtradeno = request.getParameter("outtradeno").toUpperCase();
		String totalamt = request.getParameter("totalamt");
		String totalcurr = request.getParameter("totalcurr");
		String refundcurr = request.getParameter("refundcurr");
		String refundreseason = request.getParameter("refundreseaon");
		String subject = appData.housename + "购物退款";
		String body = "销售退货" + String.valueOf(Math.abs(Double.parseDouble(totalamt)))
		+ "件商品 " + String.valueOf(Math.abs(Double.parseDouble(refundcurr)))  
		+ "元;退货单号："+noteno;
		HashMap<String, String> paymap = new HashMap<>();
		String randomNo =  DataBase.getRandomNo();//12位随机数
		String out_request_no  = noteno+randomNo;
		paymap.put("outTradeNo",outtradeno);
		paymap.put("refundAmount", String.valueOf(Math.abs(Double.parseDouble(refundcurr))));
		paymap.put("outRequestNo", out_request_no);
		paymap.put("refundReason",subject+";"+ body+";"+refundreseason);
		paymap.put("storeId", appData.houseid);
		PrintWriter out = response.getWriter();
		try {
			int payres = 0;
			String paymsg = "";
			String zfnoteno = "";
			skyrefund.setRefundmap(paymap);
			JSONObject adddataObj = JSONObject.fromObject("{}");
			adddataObj.put("ywtag", ywtag);
			adddataObj.put("ywnoteno", noteno);
			adddataObj.put("payfs", payfs);
			adddataObj.put("curr", refundcurr);
			adddataObj.put("totalcurr", totalcurr);
			adddataObj.put("outtradeno", out_request_no);
			adddataObj.put("remark", subject+";"+body);
			
			String addres = DataBase.postRequest("adddealrecord", adddataObj, appData);
			JSONObject resObj = JSONObject.fromObject(addres);
			if(appData.isResult(addres)){
				PayService payser = new PayService();
				zfnoteno = resObj.getString("noteno");
				JSONArray payKeyArr = resObj.getJSONArray("pay_zfb");
				JSONObject payKeyObj = payKeyArr.getJSONObject(0);
				JSONObject updatedataObj = JSONObject.fromObject("{}");
				updatedataObj.put("noteno", zfnoteno);
				boolean conf =  payser.init(payKeyObj);
				if(conf){
					String res = skyrefund.refundPay(payser.getTradeService());
					JSONObject payresObj = JSONObject.fromObject(res);
					if(payresObj.getInt("result")>0){
						String payidstr = payresObj.getString("openid");
						String payaccno = payresObj.getString("logid");
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
					String updateres = DataBase.postRequest("updatedealrecord", updatedataObj, appData);
					
					if(appData.isResult(updateres)){
						paymsg += ",记账成功！" ;
					}else{
						paymsg += ",记账失败！" + appData.msg ;
					}
				}else{
					updatedataObj.put("statetag", 2);
					payres = 0;
					paymsg += "该账户未被授权使用支付！需要开通，请联系官方客户QQ办理：3329713977";
				}
			}else{
				payres = 0;
				paymsg += "未生成记账记录，无法退款！";
			}
			JSONObject payresObj = JSONObject.fromObject("{}");
			payresObj.put("result", payres);
			payresObj.put("msg", paymsg);
			payresObj.put("zfnoteno", zfnoteno);
			out.println(payresObj.toString());
		} catch (Exception e) {
			// TODO: handle exception
			out.println( "{\"result\":\"0\",\"msg\":\"退款异常！\"}");
			e.printStackTrace();
		}
		out.close();
	}

}
