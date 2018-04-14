package com.flyang.pay.query;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flyang.pay.config.PayService;
import com.flyang.tools.FlyangHttp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;

/**
 * Servlet implementation class DoQuery
 */
@WebServlet("/doquery")
public class DoQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoQuery() {
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
		String reqpar = request.getParameter("fyaction");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		AppData appData = new AppData();
		appData.setqxpublic(request, response);
		if (reqpar.equals("checkpaybyno")){
			checkpaybyno(request, response,appData);
		}
	}

	/**		
	 * 传noteno,ywtag（看要不要传）,payfs 获取该单据是否有支付记录：
	 *	1、有支付记录并stataetag=1 返回{total:1; rows:[]} row里面要有：curr zfnoteno；
	 *	2、有支付记录但是statetag=0 返回 {total:1; rows:[]} row里面要有： outtradeno curr;
	 *	3、有支付记录statag=2或者没有支付及记录的一律返回 {total: 0;rows:[]} ;
	 *{"total":1,
	 *"rows":[{"NOTENO":"ZF2016120300001","YWNOTENO":"XC2016120300001","STATETAG":"1","CURR":"90","OUTTRADENO":"XC20161203000011612031457487240713",
	 *"PAYIDSTR":"20880073967635360476112831616548","PAYACCNO":"pua***@sandbox.com","PAYFS":"0"}]}
	 * @param request
	 * @param response
	 * @param appData
	 * @throws IOException 
	 */
	private void checkpaybyno(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 检查支付记录
		JSONObject dataobj = DataBase.getObjFrom(request.getParameterMap());
		FlyangHttp fh = new FlyangHttp();
		String action = "getdealrecordbyno";
		fh.setHostIP(DataBase.JERPIP+"api?action=");
		JSONObject resobj1 = fh.FlyangdoPost(action, dataobj, appData);
		String payfs = request.getParameter("payfs");
		JSONObject resobj = JSONObject.fromObject("{}");
		int queryres = 0;
		String querymsg = "";
		if(appData.isTotal(resobj1.toString())){
			if(resobj1.getInt("total")>0){
				JSONObject row = resobj1.getJSONArray("rows").getJSONObject(0);
				if(row.getString("statetag").equals("0")){
					if(payfs.equals("0")){
						String outtradeno = row.getString("outtradeno");
						HashMap<String,String> queryMap = new HashMap<>();
						queryMap.put("outTradeNo", outtradeno);
						
						PayService payser = new PayService();
						JSONArray payKeyArr = resobj1.getJSONArray("pay_zfb");
						JSONObject payKeyObj = payKeyArr.getJSONObject(0);
						boolean conf =  payser.init(payKeyObj);
						if(conf){
							QueryPay queryPay = new QueryPay();
							queryPay.setQuerymap(queryMap);
							String queryRes = queryPay.queryPay(payser.getTradeService());
							JSONObject queryResObj = JSONObject.fromObject(queryRes);
							JSONObject updatedataObj = JSONObject.fromObject("{}");
							updatedataObj.put("noteno", row.getString("noteno"));
							if(queryResObj.getInt("result")>0){
								String payidstr = queryResObj.getString("payidstr");
								String payaccno = queryResObj.getString("payaccno");
								updatedataObj.put("statetag", 1);
								updatedataObj.put("payidstr", payidstr);
								updatedataObj.put("payaccno", payaccno);
								queryres = 2;
								resobj.put("curr", row.getString("curr"));
								resobj.put("zfnoteno", row.getString("noteno"));
							}else{
								String msg = queryResObj.getString("msg");
								updatedataObj.put("failedremark", msg);
								updatedataObj.put("statetag", 2);
								queryres = 1;
							}
							querymsg += queryResObj.getString("msg");
							JSONObject updateres = fh.FlyangdoPost("updatedealrecord", updatedataObj, appData);
							if(appData.isResult(updateres.toString())){
								//querymsg += ",记账成功！" ;
							}else{
								queryres = 0;
								querymsg += ",记账失败！" + appData.msg ;
							}
						}else{
							queryres = 0;
							querymsg += "该账户未被授权使用支付！需要开通，请联系官方客户QQ办理：3329713977";
						}
					}else if(payfs.equals("2")){
						queryres=1;
						querymsg="未完成支付，请使用终端扫码验证！";
					}
				}else{
					queryres = 2;
					resobj.put("curr", row.getString("curr"));
					resobj.put("zfnoteno", row.getString("noteno"));
				}
			}else{
				queryres = 1;
			}
			
		}
		resobj.put("result", queryres);
		resobj.put("msg", querymsg);
		PrintWriter out = response.getWriter();
		out.print(resobj.toString());
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
