<%@page import="tools.DataBase"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试支付</title>
</head>
<body>
<div>
<div id="zfbpayd" title="输入付款码" data-options="modal:true" style="width: 300px; height: 160px" class="easyui-dialog" closed="true">
<div style="width:280px;margin:18px auto">
<input type="hidden" id="skypayfs" value="0">
<input type="hidden" id="skypaydata" value="0">
<input type="text" id="zfbpaytext" class="bigipt" value="" maxlength="50"  data-enter="skypay()" placeholder="<请扫码>">
</div>
</div>
<div id="zfbrefundd" title="销售退款" data-options="modal:true" style="width: 500px; height: 250px" class="easyui-dialog" closed="true">
<table cellspacing="10" class="table1">
<tr>
<td width="80" align="right" class="header">
退款原因：
</td>
<td align="left">
<textarea rows="1" cols="1" style="width:280px;height:60px;"id="refundreseaon" maxlength="50" placeholder="<输入>">""</textarea>
</td>
</tr>
<tr>
<td align="right" class="header">
退款流水号：
</td>
<td align="left">
<input type="text" id="zfbrefundtext" style="width:280px;height:30px;" value="" maxlength="50"  data-enter="skypay()" placeholder="商户流水号以在线支付查询的结果为准">
</td>
</tr>
</table>
<div class="dialog-footer">
<!-- <label><input class="printtype" type="checkbox" id="isqtprint">前台打印</label> -->
	<input type="button" class="btn1" style="width:70px;margin-right: 10px" value="确定" onclick="skypay()">
<!-- 	<input type="button" class="btn2" name="close" value="取消" onclick="$('#zfbrefundd').dialog('close')"> -->
</div>
</div>
</div>
<div id="qtzfbarcoded" title="请使用拉卡拉终端扫码支付" data-options="modal:true,onClose: closeqtzfpayd" style="width: 300px; height: 350px" class="easyui-dialog" closed="true">
<div style="width:270px;height:270px;margin:18px auto">
<img src="" alt="加载中……" id="qtzfpaycodeimg" width="270" height="270">
</div>
</div>
<script type="text/javascript">
function openskypay(payfs){
	var payOpt = $('#skypaydata').data('payoption');
	payOpt = $.extend(skypayDefaultOpt,payOpt);
	if(payOpt.onBeforePay(payfs)&&checkskypayinfo(payfs)){
		var checkcurr = 0;
		if(typeof(payOpt.curr)=='function'){
			checkcurr = payOpt.curr(payfs);
		}
		else
			checkcurr = Number($(payOpt.curr).val());
		if(checkcurr==0){
			alerttext("付款金额不能为0！");
			return;
		}
		$('#skypayfs').val(payfs);
		if(payfs<2){
			if(checkcurr>0){
				$('#zfbpayd').dialog('open');
				$('#zfbpaytext').focus();
				setTimeout(function(){
					$('#zfbpaytext').val('');
				}, 200);
			}else{
				$('#zfbrefundd').dialog('open');
				$('#refundreseaon').focus();
				setTimeout(function(){
					$('#zfbrefundtext,#refundreseaon').val('');
				}, 200);
			}
		}else if(payfs>=2){
			openlaklpay();
		}
	}
}
/**
 * payoption = {noteno:'',amt:'',curr:'',paycode:''}
 * 
 */
var haspayrequest = false;
var qtzfpayname = "";
var skypayedbj = {zfbpay:0,paynoteno_zfb:"",wxpay:0,paynoteno_wx:"",qtzfpay:0,paynoteno_qtzf:""};
var setskypay = function(payOption){
	$('#skypaydata').data('payoption',payOption);
}
var skypayDefaultOpt = {
		ywtag: 0,//0=店铺零售，1=商场零售，2=线上，3=批发销售
		noteno: "",
		amt: "",
		totalcurr: '',
		curr: "",
		onBeforePay: function(payfs){
		
		},
		onSuccess: function(payfs,curr){
		
		}
	}
var skypay = function(){
	var payOpt = $('#skypaydata').data('payoption');
	payOpt = $.extend(skypayDefaultOpt,payOpt);
	var payfs = Number($('#skypayfs').val()); //0-支付宝 1-微信 2-拉卡拉 3-盛付通
	if(checkskypayinfo(payfs)){
		haspayrequest = false;
		var ywtag = 1; //0=店铺零售，1=商场零售，2=线上
		var noteno = '';
		if(typeof(payOpt.noteno) == 'function')
			noteno = payOpt.noteno();
		else
			noteno = $(payOpt.noteno).val();
		if(typeof(payOpt.ywtag) == 'function')
			ywtag = payOpt.ywtag();
		else
			ywtag = payOpt.ywtag;
		var totalamt = 0;
		if(typeof(payOpt.amt) == 'function')
			totalamt = payOpt.amt();
		else
			totalamt = Number($(payOpt.amt).val());
		var checkcurr = 0;
		if(typeof(payOpt.curr)=='function'){
			checkcurr = payOpt.curr(payfs);
		}
		else
			checkcurr = Number($(payOpt.curr).val());
		var totalcurr = 0;
		if(typeof(payOpt.totalcurr)=='function'){
			totalcurr = payOpt.totalcurr(payfs);
		}
		else
			totalcurr = Number($(payOpt.totalcurr).val());
		if(noteno.length==0){
			alerttext('结账失败，销售单据号为空！');
			return;
		}
		/*if(totalamt==0){
			alerttext('结账失败，商品明细为空！');
			return;
		}*/
		if(checkcurr==0){
			alerttext('结账失败，结账金额为0！');
			return;
		}
		if(haspayrequest==true){
			alerttext('存在未处理的结账请求，但处理完成，请稍后重试！');
			return;
		}
		if(checkcurr>0){
			var paycode = $('#zfbpaytext').val();
			if(paycode.length!=18){
				//alerttext('结账失败，付款码不等于18位');
				return;
			}
			$('#zfbpaytext').val('');
			alerttext("正在支付请稍等……",5*60*1000);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
// 				url : "/skydesk/dopay", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				url : "/skydesk/fypay", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
// 				async: false,
				data : {
					payser: "dopay",
					noteno : noteno,
					totalamt : totalamt,
					totalcurr: totalcurr,//应付总金额
					ywtag : ywtag,
					payfs : payfs,
					paycurr : checkcurr,//结账金额
					paycode : paycode
				}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType : 'json',
				success : function(data) {
					//alert(JSON.stringify(data));
					try{
						alerttext(data.msg);
						if(data.result==1){
							if(payfs==0){
								skypayedbj.zfbpay=1;
								skypayedbj.paynoteno_zfb=data.zfnoteno;
							}
							else if(payfs==1){
								skypayedbj.wxpay=1;
								skypayedbj.paynoteno_wx=data.zfnoteno;
							}
							payOpt.onSuccess(Number(payfs),Number(checkcurr));
							$('#zfbpayd').dialog('close');
						}
						haspayrequest = true;
					}catch (e) {
						// TODO: handle exception
						console.error(e);top.wrtiejsErrorLog(e);
					}
					
				},
				error: function(){
					alerttext('支付异常，订单状态未知！');
					haspayrequest = true;
				}
			});
		}else{
			var refundreseaon = $('#refundreseaon').val().replace(/\n/g,"");
			var outtradeno = $('#zfbrefundtext').val();
			if(refundreseaon.length==0){
				alerttext('请输入退款原因！');
				return;
			}
			if(outtradeno.length==0){
				//alerttext('结账失败，付款码不等于18位');
				return;
			}
			$.messager.confirm(dialog_title,'是否确定退款！',function(r){    
				if (r){ 
					alertmsg(2);
					$.ajax({
						type : "POST", //访问WebService使用Post方式请求 
// 						url : "/skydesk/dorefund", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
						url : "/skydesk/fypay", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
// 						async: false,
						data : {
							payser: "dorefund",
							outtradeno : outtradeno,
							noteno: noteno,
							totalamt : totalamt,
							totalcurr: totalcurr,//应退总金额
							ywtag : ywtag,
							payfs : payfs,
							refundreseaon : refundreseaon,
							refundcurr : checkcurr//结账金额
						}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
						dataType : 'json',
						success : function(data) {
							try{
								alerttext(data.msg);
								if(data.result==1){
									if(payfs==0){
										skypayedbj.zfbpay=1;
										skypayedbj.paynoteno_zfb=data.zfnoteno;
									}
									else{
										skypayedbj.wxpay=1;
										skypayedbj.paynoteno_wx=data.zfnoteno;
									}
									payOpt.onSuccess(Number(payfs),Number(checkcurr));
									$('#zfbrefundd').dialog('close');
								}else{
									$('#zfbrefundtext').val('').focus();
								}
								haspayrequest = true;
							}catch (e) {
								// TODO: handle exception
								console.error(e);top.wrtiejsErrorLog(e);
							}
							
						},
						error: function(){
							alerttext('操作异常，订单状态未知！');
							haspayrequest = true;
						}
					});
				}
			});
		}
	}
}
function checkskypayinfo(payfs){
	var noteno = '';
	var ywtag = 1; //0=店铺零售，1=商场零售，2=线上
	var payOpt = {};
	if(payfs<2){
		payOpt = $('#skypaydata').data('payoption');
		payOpt = $.extend(skypayDefaultOpt,payOpt);
		if(typeof(payOpt.noteno) == 'function')
			noteno = payOpt.noteno();
		else
			noteno = $(payOpt.noteno).val();
		if(typeof(payOpt.ywtag) == 'function')
			ywtag = payOpt.ywtag();
		else
			ywtag = payOpt.ywtag;
		if(typeof(payOpt.curr)=='function'){
			checkcurr = payOpt.curr(payfs);
		}
		else
			checkcurr = Number($(payOpt.curr).val());
		if(checkcurr==0){
// 			if(clickbol)alerttext("付款金额不能为0！");
			return false;
		}
// 		var payfs = Number($('#skypayfs').val()); //0-支付宝 1-微信
	}else if(payfs>=2){
		payOpt = $('#skypaydata').data('payoption');
		if(typeof(payOpt.noteno) == 'function')
			noteno = payOpt.noteno();
		else
			noteno = $(payOpt.noteno).val();
		if(typeof(payOpt.ywtag) == 'function')
			ywtag = payOpt.ywtag();
		else
			ywtag = payOpt.ywtag;
	}
// 	alertmsg(2);
	var result = true;
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
// 		url : "/skydesk/doquery", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		url : "/skydesk/fypay", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data : {
// 			fyaction: "checkpaybyno",
			payser: "doquery",
			noteno : noteno,
			ywtag : ywtag,
			payfs : payfs
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) {
			//alert(JSON.stringify(data));{"total":1,"rows":[{"NOTENO":"XC2016120300001","STATETAG":"1","CURR":"90","OUTTRADENO":"XC20161203000011612031457487240713","PAYIDSTR":"20880073967635360476112831616548","PAYACCNO":"pua***@sandbox.com","PAYFS":"0"}]}
			try{
				 if(data.result==2){
					alerttext("该单据已经存在一次支付记录，请跟顾客核对！");
					var curr = Number(data.curr);
					var _payfs = data.payfs;
					if(_payfs==0){
						skypayedbj.zfbpay=1;
						skypayedbj.paynoteno_zfb=data.zfnoteno;
					}else if(_payfs==1){
						skypayedbj.wxpay=1;
						skypayedbj.paynoteno_wx=data.zfnoteno;
					}else if(_payfs>=2){
						skypayedbj.qtzfpay=1;
						$("input.paycurr.payfs"+payfs).data("sucstg",1);
						skypayedbj.paynoteno_qtzf=data.zfnoteno;
						$('#qtzfbarcoded').dialog('close');
					}
					payOpt.onSuccess(_payfs,curr);
					result = false;
				}else if(data.result==1){
					if(payfs>=2&&data.msg.indexOf('未完成支付')>-1){
						$('#qtzfbarcoded').dialog("setTitle",'正在支付中,请稍候……');
					}
					result = true;
				}else{
					if(payfs>=2){
						$('#qtzfbarcoded').dialog("setTitle",'请使用'+qtzfpayname+'终端扫码支付');
					}
					result = false;
				} 
			}catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
				result = false;
			}
		},
		error: function(){	
			alerttext('查询异常，订单状态未知！');
			result = false;
		}
	});
	return result;
}
function quick_zf(payfs){
	if(payfs==0){
		if($("label:contains('*支付宝*')").length==1){
			$("label:contains('*支付宝*')").click();
			openskypay(payfs);
		}
	}
}
function setqtzfpaydata(payOpt){
	$('#skypaydata').data('payoption',payOpt);
}
function openlaklpay(){
	var payOpt = $('#skypaydata').data('payoption');
	if(payOpt){
		notetwobarcode(payOpt);
	}
}
//根据单据号生成二维码 
var qtzfpaytimer = null;
var closeqtzfpayd = function(){
	clearInterval(qtzfpaytimer);
}
function notetwobarcode(payOpt){
	var ywtag = payOpt.ywtag;
	var ywnoteno = '';
	if(typeof(payOpt.noteno)=='function')
		ywnoteno = payOpt.noteno();
	else
		ywnoteno = $(payOpt.noteno).val();
	var curr = 0;
	if(typeof(payOpt.curr)=='function')
		curr = payOpt.curr();
	else
		curr = $(payOpt.curr).val();
	var totalcurr = 0;
	if(typeof(payOpt.totalcurr)=='function')
		totalcurr = payOpt.totalcurr();
	else
		totalcurr = $(payOpt.totalcurr).val();
	if(curr==0){
		alerttext('金额不能等于0!');
		return;
	}
	$('#qtzfpaycodeimg').attr('src',"");
	$('#qtzfbarcoded').dialog("setTitle",'请使用'+qtzfpayname+'终端扫码支付');
	$('#qtzfbarcoded').dialog('open');
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser: "notetwobarcode",
			ywnoteno: ywnoteno,
			curr: curr,
			totalcurr: totalcurr,
			ywtag: ywtag  //0=店铺零售，1=商场零售，2=线上，3=批发销售
		}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					$('#qtzfpaycodeimg').attr('src','<%=tools.DataBase.OUTSIP%>/'+data.msg);
					qtzfpaytimer = setInterval(function(){
						checkskypayinfo(qtpayfs);
					},2000);
				}
			}catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}

</script>
</body>
</html>