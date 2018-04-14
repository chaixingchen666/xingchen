<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">
<meta name="renderer" content="webkit|ie-comp|ie-stand" /> 
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>经营汇总统计</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/easydropdown.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<style type="text/css">
body {
	font-family: 微软雅黑;
	font-size:14px;
	margin: auto;
	color:black;
	background-color:white;
}
#main{min-height:350px;overflow: auto;}
#start{height:40px;}
#mindate{border:1px solid blue;}
#nowdate{height:10px;}
select {
	border:1px solid #d7d7d7;
}
#benqi-l{
	border-right:1px solid #EBEBEB;
	border-bottom:1px solid #EBEBEB;
	float:left;
	text-align: center;
	width:49%;
	margin-bottom: 8px;
	border-top:10px solid #44b5af;
}
#benqi-l td{
	border-left:1px solid #CCCCCC;
	line-height:20px;
	padding-top:10px;
}
#jiesuan{
	float:clear;
	background-color:#EBEBEB;
	width:100%;
	height:auto;
	margin-top: 10px;
	margin-bottom: 8px;
	padding-top:10px;
}
#jsfs{width:100%;height:auto;}

#chu{
	width:33%;
	height:30px;
	float:left;	
}
#chu1{
	position:absolute;
	height:30px;
	width:33%;
	left:35%;
	margin-left:-5px;
	
}
#chuzhi{
	border-right:1px solid #EBEBEB;
	border-bottom:1px solid #EBEBEB;
	float:left;
	text-align: center;
	margin-bottom: 8px;
}
#chuzhi td{
	border-left:1px solid #CCCCCC;
	line-height:20px;
	padding-top:10px;
}
#jingying{
	border-right:1px solid #EBEBEB;
	border-bottom:1px solid #EBEBEB;
	float:left;
	width:49%;
	text-align: center;
	margin-bottom: 8px;
	border-top:10px solid #65c3df;
}
#jingying td{
	border-left:1px solid #CCCCCC;
	line-height:20px;
	padding-top:10px;
}
#fang{width:15px;height:15px;background-color:#44b5af; float:left; margin-top:3px; margin-right:7px;}
#fangj{width:15px;height:15px;background-color:#65c3df; float:left; margin-top:3px; margin-right:7px;}
#chul{width:15px;height:15px;background-color:#f8cb00; float:left; margin-top:3px; margin-right:7px;}
#chur{width:15px;height:15px;background-color:#ff6b6b; float:left; margin-top:3px; margin-right:7px;}

#housed{overflow:hidden;}
</style>
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript">
//当浏览器窗口大小改变时，设置显示内容的高度  
	window.onresize=function(){  
	     changeDivHeight();  
	}  
	function changeDivHeight(){               
	  var height = document.documentElement.clientHeight;//获取页面可见高度 
		$('#main').height(height-60);
	}
$(function(){
	var myDate=new Date(top.getservertime());
	$('#nowdate').text(myDate.Format("yyyy-MM-dd"));
	$("#smindate").datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$("#smaxdate").datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$("#shouseid").val(getValuebyName("HOUSEID"));
	$("#shousename").val(getValuebyName("HOUSENAME"));
	pp();
	changeDivHeight();
});
function resetsearch(){
	var myDate=new Date(top.getservertime());
	$("#smindate,#smaxdate").datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$('#shousename,#shouseid').val('');
}
var paycurrlist;
var payname;
var paycurr0;
var paycurr1;
var paycurr2;
var PAYCURR0hj='0';
var PAYCURR1hj='0';
var PAYCURR2hj='0';
function pp(){
	var mindate=$('#smindate').datebox('getValue');
	var maxdate=$('#smaxdate').datebox('getValue');
	var houseid=$('#shouseid').val();
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "totaljyhztj",
			mindate:mindate,
			maxdate:maxdate,
			houseid: Number(houseid)
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			try{
				if (valideData(data)) {
 				paycurrlist = data.PAYCURRLIST;
 				PAYCURR0hj=new Decimal(0);
				PAYCURR1hj=new Decimal(0);
				PAYCURR2hj=new Decimal(0);
 				for(var i in paycurrlist){
 					var row = paycurrlist[i];
 					PAYCURR0hj = PAYCURR0hj.add(row.PAYCURR0);
 					PAYCURR1hj = PAYCURR1hj.add(row.PAYCURR1);
 					PAYCURR2hj = PAYCURR2hj.add(row.PAYCURR2);
 				}
 				$("#jiesuan").html("");
 				var jiesuan='<table id="jsfs">'; 
 				jiesuan+='<tr><td>结算方式</td><td align="right">收入</td><td align="right">支出</td><td align="right">收支差</td></tr>';
				for(var i in paycurrlist){
					jiesuan+='<tr><td>'+paycurrlist[i].PAYNAME+'</td><td align="right">'+Number(paycurrlist[i].PAYCURR0).toFixed(2)+'</td><td align="right">'+Number(paycurrlist[i].PAYCURR1).toFixed(2)+'</td><td align="right">'+Number(paycurrlist[i].PAYCURR2).toFixed(2)+'</td></tr>';
				}
				jiesuan+='<tr><td style="color:#ffc85f;">合计</td><td align="right">'+PAYCURR0hj.toFixed(2)+'</td><td align="right">'+PAYCURR1hj.toFixed(2)+'</td><td align="right">'+PAYCURR2hj.toFixed(2)+'</td></tr>';
				jiesuan+='</table>';
				 $("#jiesuan").html(jiesuan);
				$('#BRKAMT').html(data.BRKAMT);$('#BRKCURR').html(data.BRKCURR);
				$('#BRTAMT').html(data.BRTAMT);$('#BRTCURR').html(data.BRTCURR);
				$('#BDRAMT').html(data.BDRAMT);$('#BYYAMT').html(data.BYYAMT);
				$('#BYFFYCURR').html(data.BYFFYCURR);$('#BXSAMT').html(data.BXSAMT);
				$('#BXSCURR').html(data.BXSCURR);$('#BXTAMT').html(data.BXTAMT);
				$('#BXTCURR').html(data.BXTCURR);$('#BDCAMT').html(data.BDCAMT);
				$('#BYKAMT').html(data.BYKAMT);$('#BYSFYCURR').html(data.BYSFYCURR);
				$('#KQCCURR').html(data.KQCCURR);$('#KZJCURR').html(data.KZJCURR);
				$('#KJSCURR').html(data.KJSCURR);$('#KQMCURR').html(data.KQMCURR);
				
				$('#ZRKAMT').html(data.ZRKAMT);$('#ZYKAMT').html(data.ZYKAMT);
				$('#ZZTAMT').html(data.ZZTAMT);$('#RETAILCURR').html(data.RETAILCURR);
				
				$('#ZXSAMT').html(data.ZXSAMT);$('#ZKCAMT').html(data.ZKCAMT);
				$('#ZYEAMT').html(data.ZYEAMT);$('#FIXEDCURR').html(data.FIXEDCURR);
				$('#ZSKCURR').html(data.ZSKCURR);$('#YSKCURR').html(data.YSKCURR);
				$('#ZFYCURR').html(data.ZFYCURR);$('#ZFKCURR').html(data.ZFKCURR);
				$('#YFKCURR').html(data.YFKCURR);$('#ZSYCURR').html(data.ZSYCURR); 
				jschange();
			} else {
				alert(text);
			}
			}catch(err){
				console.error(err.message);
			}

		}
	});
}
function jschange(){
	var jsfs=$("#jsfs").val();
	$("#PAYCURR0 option[value='"+jsfs+"']").attr("selected","selected");
	$("#PAYCURR1 option[value='"+jsfs+"']").attr("selected","selected");
	$("#PAYCURR2 option[value='"+jsfs+"']").attr("selected","selected");
	var PAYCURR0=$("#PAYCURR0 option:selected").text();
	var PAYCURR1=$("#PAYCURR1 option:selected").text();
	var PAYCURR2=$("#PAYCURR2 option:selected").text();
	 $("#pay0").html(PAYCURR0);
	 $("#pay1").html(PAYCURR1);
	 $("#pay2").html(PAYCURR2);
}

</script>
</head>
<body>
<table id="start">
	<tr>
		<td class="header">开始日期</td>
		<td><input id="smindate" type="text" class="easyui-datebox" editable="true" required="required" currentText="today"  style="width:164px;height:25px"></td>
		<td class="header">结束日期</td>
		<td><input id="smaxdate" type="text" class="easyui-datebox"  editable="true" required="required" currentText="today"  style="width:164px;height:25px"></td>
		<td class="header">店铺<td>
		<td width="180"><input  type="text" class="edithelpinput house_help" data-value="#shouseid"
			name="shousename" id="shousename" style="width:135px;height:24px;" data-options="required:true" placeholder="<选择或输入>"  ><span  onclick="selecthouse('analysis')"></span>
			<input type="hidden" id="shouseid" value=""></td>
		<td><input class="btn1" type="button" onclick="pp();" value="查询" /><input class="btn1" type="button" onclick="resetsearch();" value="清除条件" /></td>
	</tr>
</table>
<div id="main">
<div id="nowdate"></div>
<hr style="border:1px solid #0195a1;margin-top:15px;"/>
<div style="height:25px;padding-top:10px;"><div id="fang"></div><div style="font-size:16px;">本期发生</div></div>
<div style="height:120px;">
 <table id="benqi-l" style="margin-right:20px;">
 	<tr>
 		<td >进货数量<br /><span id="BRKAMT"></span></td>
 		<td>进货金额<br /><span id="BRKCURR"></span></td>
 		<td>进退数量<br /><span id="BRTAMT"></span></td>
 		<td>进退金额<br /><span id="BRTCURR"></span></td>
 	</tr>
 	<tr>
 		<td>调入数量<br /><span id="BDRAMT"></span></td>
 		<td>盘盈数量<br /><span id="BYYAMT"></span></td>
 		<td>应付费用<br /><span id="BYFFYCURR"></span></td>
 	</tr>
 </table>
 <table id="benqi-l">
 	<tr>
 		<td>销售数量<br /><span id="BXSAMT"></span></td>
 		<td>销售金额<br /><span id="BXSCURR"></span></td>
 		<td>消退数量<br /><span id="BXTAMT"></span></td>
 		<td>消退金额<br /><span id="BXTCURR"></span></td>
 	</tr>
 	<tr>
 		<td>调出数量<br /><span id="BDCAMT"></span></td>
 		<td>盘亏数量<br /><span id="BYKAMT"></span></td>
 		<td>应收费用<br /><span id="BYSFYCURR"></span></td>
 	</tr>
 </table>
</div>
<div id="jiesuan" style="width:100%;overflow:auto;">
	
</div>
<div style="height:30px;padding-top:10px;position:relative;"><span id="chu"><div id="chul"></div><div style="font-size:16px;">储值卡</div></span><span id="chu1"><div id="chur"></div><div style="font-size:16px;">库存汇总</div></span></div>
<div style="width:100%;height:120px;">
	<table id="chuzhi" style="width:33%;margin-right:10px;border-top:10px solid #f8cb00;">
		<tr>
			<td>期初<br /><span id="KQCCURR"></span></td>
			<td>增加<br /><span id="KZJCURR"></span></td>
		</tr>
		<tr>
			<td>减少<br /><span id="KJSCURR"></span></td>
			<td>期末<br /><span id="KQMCURR"></span></td>
		</tr>
	</table>
	<table id="chuzhi" style="width:33%;margin-right:10px;border-top:10px solid #ff6b6b;">
		<tr>
			<td>累计进货<br /><span id="ZRKAMT"></span></td>
			<td>盈亏数量<br /><span id="ZYKAMT"></span></td>
		</tr>
		<tr>
			<td>在途数量<br /><span id="ZZTAMT"></span></td>
			<td>零售总额<br /><span id="RETAILCURR"></span></td>
		</tr>
	</table>
	<table id="chuzhi" style="width:32%;border-top:10px solid #ff6b6b;">
		<tr>
			<td>累计销售<br /><span id="ZXSAMT"></span></td>
			<td>库存数量<br /><span id="ZKCAMT"></span></td>
		</tr>
		<tr>
			<td>结存数量<br /><span id="ZYEAMT"></span></td>
			<td>成本总额<br /><span id="FIXEDCURR"></span></td>
		</tr>
	</table>
</div>
<div style="float:clear;width:100%; height:30px;padding-top:15px;"><div id="fangj"></div><div style="font-size:16px;">经营汇总<span style="font-size:12px;">收益合计=累计收款+应收款-累计付款-应付款-店铺费用</span></div></div>
<div style="height:80px;width:100%;">
 <table id="jingying" style="margin-right:20px;">
 	<tr>
 		<td>累计收款<br /><span id="ZSKCURR"></span></td>
 		<td>应收账款<br /><span id="YSKCURR"></span></td>
 		<td>店铺费用<br /><span id="ZFYCURR"></span></td>
 	</tr>
 </table>
 <table id="jingying">
 	<tr>
 		<td>累计付款<br /><span id="ZFKCURR"></span></td>
 		<td>应付账款<br /><span id="YFKCURR"></span></td>
 		<td>收益合计<br /><span id="ZSYCURR"></span></td>
 	</tr>
 </table>
</div>
</div>
<!-- 店铺帮助列表 -->
<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
</body>
</html>