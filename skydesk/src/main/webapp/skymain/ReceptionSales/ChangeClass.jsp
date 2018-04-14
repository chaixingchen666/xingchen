<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" --%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">
<meta name="renderer" content="webkit|ie-comp|ie-stand" /> 
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>收银交班表</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/easydropdown.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<style type="text/css">
body {
	font-family: 微软雅黑;
	font-size:14px;
	margin: auto;
	color:black;
	width:auto;
	background-color: white;
}
#start{height:40px;}
#main{overflow: auto;left:0;right:0;bottom: 0;top: 85px;position: absolute;}
h3{text-align:center;color: #0095a0;}
#benqi-l{
	text-align: center;
	width:98%;
	margin-bottom: 8px;
	border-top:1px solid #0095a0;
}
#benqi-l td{
	line-height:20px;
	border-bottom:1px solid #CCCCCC;
}
#beizhu{
	text-align: center;
	width:98%;
	margin-bottom: 8px;
	border-top:1px solid #0095a0;
}

</style>
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
</head>
<body class="easyui-layout layout panel-noscroll">
<div><h3>收银交班表</h3><input type="hidden" id="classid" />
<input type="hidden" id="shouseid"></div>
<hr style="border:1px solid #0195a1;margin-top:15px;"/>
<table id="start">
	<tr>
		<td style="padding-right:10px;" class="header">开始日期</td>
		<td style="padding-right:10px;"><input id="mindate" type="text"  style="width:164px;height:25px" readonly></td>
		<td style="padding-right:10px;" class="header">当前日期</td>
		<td style="padding-right:10px;" ondblclick="opendated()" title="双击可以修改当前日期"><input id="maxdate" type="text" style="width:164px;height:25px" readonly></td>
		<td style="padding-right:10px;" class="header">店铺<td>
		<td style="padding-right:10px;">
		<input  type="text" data-value="#shouseid" name="shousename" id="shousename" style="width:135px;height:24px;" readonly>
		<input class="btn1" type="button" onclick="opendated();" value="查询" />
		<input class="btn1" type="button" onclick="cancel();" value="撤班" />
		<input class="btn1" type="button" onclick="houseworkjiao();" value="交班" />
		<input class="btn1" type="button" onclick="selprint()" value="选择打印端口" />
		<input class="btn1" type="button" id="print" value="后台打印" />
</td>
	</tr>
</table>
<div id="main">
<div>
 <table id="xs"></table>
</div>
<div>
 <table id="fy"></table>
</div>
<div>
 <table id="sz"></table>
</div>
<div style="height:25px;"><div style="font-size:16px;color:#0195a1;padding-left:10px;">现金</div></div>
<div style="height:80px;margin-top:5px;">
	 <table style="width:98%;" id="benqi-l" cellspacing="10">
 	<tr>
 		<td ><span id="Currqc">0</span><br />上班结存</td>
 		<td ><span id="CURRYE">0</span><br />本班余额</td>
 		<td ><span id="CURRYK">0</span><br />损益金额</td>
 	</tr>
 	<tr>
 		<td><input type="text"  id="Currsjye" style="width:80px;text-align:center;" onchange="searchProductClassbyName()"/><br />实际金额</td>
 		<td><input type="text" id="Curryh" style="width:80px;text-align:center;" onchange="searchProductClassbyName()"/><br />银行存款</td>
 		<td><span id="CURRJC">0</span><br />本班结存</td>
 	</tr>
 </table>
</div>
<div style="height:25px;margin-top:40px;"><div style="font-size:16px;color:#0195a1;padding-left:10px;">备注</div></div>
<div style="height:40px;">
	 <table style="width:98%;border-top:1px solid #0095a0;" cellspacing="10" id="biezhu">
 	<tr>
 		<td ><input type="hidden" id="CURRXJ" /></td>
		<td> <textarea cols="100" id="remark" rows="3" maxlength="50"></textarea></td>
 	</tr>
 </table>
</div>
</div>
	<!-- 页面加载弹出窗pg1208收银交班表 -->
	<div id="selectd" title="收银交班表" style="width: 430px; height: 280px;text-align: center;" class="easyui-dialog" closed="true"><input type="hidden" id="shoueid1" >
		<table width="280" cellspacing="10" class="table1 mt14">
			<tr>
				<td width="80" align="left" class="header">店铺</td>
				<td width="170" align="left"><input type="text" data-value="#shouseid1"
					name="housename" id="housename" readonly></td>
			</tr>
			<tr>
				<td align="left" class="header">上班结存</td>
				<td align="left"><input class="wid160 hig25" id="currqcs" type="text" data-options="required:true"
					 placeholder="请输入上期结存金额" ></td>
			</tr>
			<tr>
				<td align="left" width="100" class="header">开班时间</td>
				<td align="left" width="200"><input id="begindate" type="text"  editable="true"
					class="easyui-datebox" required="required" currentText="today"
					style="width: 164px; height: 25px"></input></td>
			</tr>
			<tr>
				<td colspan="2" class="dialog-footer textcenter"><input class="btn1"
					type="button" onclick="gethouseworkopen();" value="确定" />
					<input class="btn1" type="button" value="取消" onclick="closeAll()" /></td>
			</tr>
		</table>
	</div>
	<!-- 页面加载弹出窗撤班 -->
	<div id="ched" title="撤班"
		style="width: 260px; height: 130px; padding-left: 40px; text-align: center;"
		class="easyui-dialog" closed="true">
		<table cellspacing="10"  class="table1">
			<tr>
				<td align="center" colspan="2">确定要撤班？</td>
			</tr>
			<tr>
				<td><input class="btn1" value="确定"
					type="button" onclick="houseworkcancel();" />
					<input class="btn1" type="button"
					value="取消" onclick="closeAll1()" /></td>
			</tr>
		</table>
	</div>
	<!-- 页面加载弹出窗交班 -->
	<div id="jiaod" title="交班"
		style="width: 260px; height: 180px; padding-left: 20px; text-align: center;"
		class="easyui-dialog" closed="true">
		<table cellspacing="10" class="table1" width="200px">
			<tr align="center">
				<td align="left">确定交班?</td>
			</tr>
			<tr class="hide">
				<td align="center">
				<input type="text" name="jbnotedate" id="jbnotedate" placeholder="<输入>" style="width: 162px;height:29px" class="easyui-datetimebox">
				</td>
			</tr>
		</table>
	<div class="dialog-footer">
	<input class="btn1" value="确定" type="button" onclick="houseworkclose();" />
	<input class="btn1" type="button" value="取消" onclick="closeAll2()" />
	</div>
	</div>
	<!-- 修改时间框 -->
<div id="changenotedated" title="修改当前时间" style="width: 350px; height: 200px" class="easyui-dialog" closed="true">
	<div style="margin-left: 40px;margin-top: 30px">
	<p>请输入时间</p>
	<p><input type="text" name="notedate" id="notedate" placeholder="<输入>" style="width: 162px;height:29px" class="easyui-datetimebox"></p>
	</div>
	<div class="dialog-footer" style="text-align: center;">
			<input type="button" class="btn1" style="width:70px;margin-right: 10px" name="updateware" value="确定" onclick="changemaxdate()">
			<input type="button" class="btn2" name="close" value="取消" onclick="$('#changenotedated').dialog('close')">
	</div>
</div>
<script type="text/javascript"> 
var epno = getValuebyName("EPNO");
//店铺id 
var houseid = getValuebyName('HOUSEID');
var housename = getValuebyName('HOUSENAME');
var pgid = "pg1208";
var classid; //班次 
var closedia = true; //判断dialog关闭方式
setqxpublic();
var data;
//设置弹出框的属性
$(function() {
	var myDate = new Date(top.getservertime());
	var hour = myDate.getHours();
	var mi = myDate.getMinutes();
	var second = myDate.getSeconds();
	$('#shousename').val(housename);
	$('#shouseid').val(houseid);
	setaddbackprint(1208,{
		id: '$("#classid").val()',
		houseid: '$("#shouseid").val()',
		noteno: "null"
	});
	datagridtab();
	opclass();
});

//取消开班 
function closeAll() {
	closedia = true;
	$('#selectd').dialog('close');
	setTimeout(function(e) {
		top.closeTab('tab_pg1208');
	},
	500);
}
//撤班弹窗
function cancel() {
	$('#ched').dialog("setTitle", '撤班确认');
	$('#ched').dialog('open');
}
function houseworkjiao() {
	$("#jbnotedate").datetimebox("setValue",new Date(top.getservertime()).Format("yyyy-MM-dd hh:mm")+":59");
	$('#jiaod').dialog("setTitle", '交班确认');
	$('#jiaod').dialog('open');
}
//取消撤班
function closeAll1() {
	$('#ched').dialog('close');
}
//取消交班 
function closeAll2() {
	$('#jiaod').dialog('close');
}
//店铺开班判断
function opclass() {
	var houseid = $('#shouseid').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data: {
			erpser: "houseworkexists",
			houseid: houseid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$('#classid').val(data.msg);
					gethouseworktotal();
				} else {
					$("#selectd").dialog({
						onClose: function() {
							if (closedia) {
								setTimeout(function(e) {
									top.closeTab('tab_pg1208');
								},
								500);
							}
						}
					});
					//设置日期框默认值
					var myDate = new Date(top.getservertime());
					$('#housename').val(housename);
					$('#begindate').datetimebox({
						value: myDate.Format('yyyy-MM-dd hh:mm:ss'),
						required: true,
						showSeconds: true
					});
					$('#selectd').dialog('open');
				}
			} catch(err) {
				console.error(err.message);
			}

		}
	});
}

function searchProductClassbyName() {
	var cursj = $('#Currsjye').val(); //实际余额
	var curyh = $('#Curryh').val(); //银行存款
	var curye = $('#CURRYE').text(); //本班余额
	var curyk = Number(cursj) - Number(curye);
	var currjc = Number(cursj) - Number(curyh);
	$('#CURRYK').html(curyk); //损益金额
	$('#CURRJC').html(currjc) //本班结存
}
//店铺收银交班统计
function gethouseworktotal() {
	classid = Number($('#classid').val());
	var houseid = Number($('#shouseid').val());
	var enddate = $("#maxdate").val().substr(0,16);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "houseworktotal",
			enddate: enddate,
			classid: classid,
			houseid: houseid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var XSLIST = {
						total: data.XSLIST.length,
						rows: data.XSLIST
					};
					var PAYCURRLIST = {
						total: data.PAYCURRLIST.length,
						rows: data.PAYCURRLIST
					};
					var FYLIST = {
						total: 1,
						rows: data.FYLIST
					};
					$('#mindate').val(data.MINDATE);
					$('#maxdate').val(data.MAXDATE);
					$('#Currqc').html(Number(data.CURRQC).toFixed(2));
					$('#CURRYE').html(Number(data.CURRYE).toFixed(2));
					$('#CURRYK').html(Number(data.CURRYK).toFixed(2));
					$('#Currsjye').val(Number(data.CURRSJYE).toFixed(2));
					$('#Curryh').val(Number(data.CURRYH).toFixed(2));
					$('#CURRJC').html(Number(data.CURRJC).toFixed(2));
					$('#CURRXJ').val(Number(data.CURRXJ).toFixed(2));
					$('#xs').datagrid('loadData', XSLIST);
					$('#fy').datagrid('loadData', FYLIST);
					$('#sz').datagrid('loadData', PAYCURRLIST);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}

//设置表格列名
function datagridtab() {
	$('#xs').datagrid({
		idField: 'WAREID',
		title: "销售汇总",
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		scrollbarSize: 0,
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if(value) return value;
				return Number(index+1);
			}
		},{
			field: 'WARENO',
			title: '货号',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'WARENAME',
			title: '商品',
			width: 150,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'AMOUNT',
			title: '数量',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: amtfm
		},
		{
			field: 'CURR',
			title: '金额',
			width: 150,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		}]],
		 onLoadSuccess: function(data){  
			 var totalamt = new Decimal(0);
			 var totalcurr = new Decimal(0);
			 if(data.total>0){
				 var rows = data.rows;
				 for(var i=0;i<rows.length;i++){
					 var row = rows[i];
					 totalamt = totalamt.add(row.AMOUNT);
					 totalcurr = totalcurr.add(row.CURR);
				 }
			 }
			 var footer = [{ROWNUMBER: "合计",AMOUNT:totalamt.valueOf(),CURR:totalcurr.valueOf()}];
			 $(this).datagrid("reloadFooter",footer);
		 }
	});
	$('#fy').datagrid({
		idField: 'CGID',
		title: "费用汇总",
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		scrollbarSize: 0,
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if(value) return value;
				return Number(index+1);
			}
		},{
			field: 'CGNAME',
			title: '项目名称',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'CURR0',
			title: '支出',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'CURR1',
			title: '收入',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'CURR2',
			title: '金额',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		}]],
		 onLoadSuccess: function(data){  
			 var totalcurr0 = new Decimal(0);
			 var totalcurr1 = new Decimal(0);
			 var totalcurr2 = new Decimal(0);
			 if(data.total>0){
				 var rows = data.rows;
				 for(var i=0;i<rows.length;i++){
					 var row = rows[i];
					 totalcurr0 = totalcurr0.add(row.CURR0);
					 totalcurr1 = totalcurr1.add(row.CURR1);
					 totalcurr2 = totalcurr2.add(row.CURR2);
				 }
			 }
			 var footer = [{ROWNUMBER: "合计",CURR0:totalcurr0.valueOf(),CURR1:totalcurr1.valueOf(),CURR2:totalcurr2.valueOf()}];
			 $(this).datagrid("reloadFooter",footer);
		 }
	});
	$('#sz').datagrid({
		idField: 'PAYID',
		title: "收支汇总",
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		scrollbarSize: 0,
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if(value) return value;
				return Number(index+1);
			}
		},{
			field: 'PAYNAME',
			title: '结算方式',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'PAYCURR0',
			title: '销售收入',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'PAYCURR1',
			title: '费用支出',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'PAYCURR2',
			title: '小计',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		}]],
		 onLoadSuccess: function(data){  
			 var totalcurr0 = new Decimal(0);
			 var totalcurr1 = new Decimal(0);
			 var totalcurr2 = new Decimal(0);
			 if(data.total>0){
				 var rows = data.rows;
				 for(var i=0;i<rows.length;i++){
					 var row = rows[i];
					 totalcurr0 = totalcurr0.add(Number(row.PAYCURR0));
					 totalcurr1 = totalcurr1.add(Number(row.PAYCURR1));
					 totalcurr2 = totalcurr2.add(Number(row.PAYCURR2));
				 }
			 }
			 var footer = [{
				 ROWNUMBER: "合计",
				 PAYCURR0:totalcurr0.valueOf(),
				 PAYCURR1:totalcurr1.valueOf(),
				 PAYCURR2:totalcurr2.valueOf()
				 }];
			$(this).datagrid("reloadFooter",footer);
		 }
	});
}
//店铺开班 houseworkopen
function gethouseworkopen(){
	closedia=false;
	$('#selectd').dialog('close');
	var begindate =$('#begindate').datebox('getValue');//当前时间
	var currqc=$("#currqcs").val();//上班结存
	var hosueid = $('#shoueid1').val();
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			async:false,
			data : {
				erpser : "houseworkopen",
				houseid:houseid,
				currqc:currqc,
				begindate:begindate
			}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType : 'json',
			success : function(data) { //回调函数，result，返回值 
				try{if (valideData(data)) {
					$('#classid').val(data.msg);
					gethouseworktotal();
				} 
				}catch(err){
					console.error(err.message);
				}

			}
		});
}
//撤班 
 function houseworkcancel(){
	 $('#ched').dialog('close');
	 classid=$('#classid').val();//班次 
	 $.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			async:false,
			data : {
				erpser : "houseworkcancel",
				classid:classid
			}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType : 'json',
			success : function(data) { //回调函数，result，返回值 
				try{if (valideData(data)) {
					alert("撤班成功！");
					setTimeout(function(e){
						top.closeTab('tab_pg1208');//关闭当前窗口 
					},500);
				} 
				}catch(err){
					console.error(err.message);
				}
			}
		});
 }
 
//交班 
 function houseworkclose(){
	// $('#jiaod').dialog('close');
	 classid=$('#classid').val();//班次 
	var currxj=$('#CURRXJ').val();//现金收入
	var Currqc=$('#Currqc').text();//上班结存
	var CURRYE=$('#CURRYE').text();//本班余额
	var CURRYK=$('#CURRYK').text();//损益金额
	var curryh=$('#Curryh').val();//银行存款
	var CURRJC=$('#CURRJC').text();//本班结存 
	var currsjye=$('#Currsjye').val();//实际余额
	var remark=$('#remark').val();//备注
	var enddate = $("#jbnotedate").val().substr(0,16);
	 $.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			async:false,
			data : {
				erpser : "houseworkclose",
				classid:classid,
				enddate: enddate,
				currsjye:currsjye,
				curryh:curryh,
				currxj:currxj,
				remark:remark,
				Currqc:Currqc,
				CURRYE:CURRYE,
				CURRYK:CURRYK,
				CURRJC:CURRJC
			}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType : 'json',
			success : function(data) { //回调函数，result，返回值  
				try{if (valideData(data)) {
					alert("交班成功！");
					setTimeout(function(e){
						top.closeTab('tab_pg1208');
					},500);
				} 
				}catch(err){
					console.error(err.message);
				}

			}
		});
 }
function opendated(){
	$("#changenotedated").dialog("open");
	$("#notedate").datetimebox("setValue",new Date(top.getservertime()).Format("yyyy-MM-dd hh:mm")+":59");
}
function changemaxdate(){
	var maxdate = $("#notedate").datetimebox("getValue");
	$("#maxdate").val(maxdate);
	$("#changenotedated").dialog("close");
	gethouseworktotal();
}
</script>
<!-- 端口帮助列表 -->
<jsp:include page="../HelpList/PrintcsHelp.jsp"></jsp:include>
<!-- 店铺帮助列表 -->
<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
</body>
</html>