<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">
<meta name="renderer" content="webkit|ie-comp|ie-stand" /> 
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>账户明细</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
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
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
	<div class="fl">
	<input class="btn1" type="button" id="addnotebtn" value="转账" onclick="opentransfer()">
	</div>
</div>
</div>
<table id="balcurrdg"></table>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total" id="notetotal">共有0条记录</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>
<!-- 转账框 -->
<div id="transd" title="枫杨果转账" style="width: 350px; height: 280px" class="easyui-dialog" closed="true" data-options="modal:true">
	<div style="margin-left: 40px;margin-top: 30px">
	<table class="table2" border="0" cellspacing="0" cellpadding="0" width="300">
  <tr>
    <td width="67" align="right" class="header">收款账号</td>
    <td colspan="3" align="right" width="200"><input class="hig25 wid160" type="text" id="toaccname"></td>
  </tr>
  <tr>
    <td align="right" class="header">转账数量</td>
    <td colspan="3" align="right"><input class="hig25 wid160" type="text" id="curr" class="onlyMoney"></td>
  </tr>
</table>
</div>
<div class="dialog-footer">
		<input type="button" class="btn1" style="width:70px;margin-right: 10px" name="" value="转账" onclick="transfer()">
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#transd').dialog('close')">
</div>
	</div>

<script type="text/javascript">

	var levelid = getValuebyName("GLEVELID");
	setqxpublic();
//当浏览器窗口大小改变时，设置显示内容的高度  
	window.onresize=function(){  
	     changeDivHeight();  
	}  
function changeDivHeight(){               
	var height = document.documentElement.clientHeight;//获取页面可见高度 
	$('#gg').datagrid('resize',{
		height:height-45
	});
}
function opentransfer(){
	if(levelid!="0")
		alerttext("您没有权限操作！");
	
	else
		$('#transd').dialog('open');
		
}
$(function(){
	$('#balcurrdg').datagrid({
		idField : 'ID',
		height : $('body').height() - 50,
		fitColumns : true,
		striped : true, //隔行变色
		singleSelect : true,
		showFooter : true,
		columns : [ [
				{
					field : 'ID',
					title : 'ID',
					width : 200,
					hidden : true
				},
				{
					field : 'ROWNUMBER',
					title : '序号',
					width : 50,
					fixed : true,
					align : 'center',
					halign : 'center',
					formatter : function(value, row,index) {
						var val = Math.ceil(Number(value)/ pagecount)-1;
						return value==""?"":val*pagecount+Number(index)+1;
					}
				},
				{
					field : 'NOTEDATE',
					title : '日期',
					width : 160,
					halign : 'center',
					align : 'center',
					fixed : true				
				},
				{
					field : 'REMARK',
					title : '摘要',
					width : 160,
					fixed : true,
					halign : 'center',
					align : 'center'
				},
				{
					field : 'CURR',
					title : '枫杨果',
					width : 100,
					fixed : true,
					halign : 'center',
					align : 'center',
					formatter:function(value,row,index){
						return value!=undefined?Number(value):"";
					}	
				}] ],
		onClickRow :function(index,row){
			
		},
		onDblClickRow : function(rowIndex, rowData) {
			
		},
		toolbar : "#toolbars",
		pageSize : 20
	}).datagrid("keyCtr","");
	$("#pp").createPage({
		pageCount : 1,
		current : 1,
		backFn: function(p){
			getbalcurrlist(p.toString());
		}
	});
	getbalcurrlist("1");
	$('input[name]').change(function(e){
		getbalcurrlist("1");
	});
	var qxcs = getValuebyName("QXCS");
	var qx = Number(qxcs.charAt(0));
	if(qx==1)
		$("#toolbars").show();
	else
		$("#toolbars").hide();
});
function getbalcurrlist(page){
	opser = "get";
	var fs = "2";
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "accmoneylist",
			fs : fs,
			rows: pagecount,
			page : page
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			if(valideData(data)){
				$("#pp").setPage({
					pageCount : Math.ceil(data.total / pagecount),
					current : Number(page)
				});
				$('#balcurrdg').datagrid('loadData', data);
				$('#notetotal').html('共有'+data.total+'条记录');
			}
		}
	});
}
function transfer(){
	var toaccname = $('#toaccname').val();
	var curr = Number($('#curr').val());
	var oldcurr = Number(getValuebyName("BALCURR"));
	
	if(toaccname=="")
		alerttext("收款账户不能为空！");
	else if(curr>oldcurr)
		alerttext("转账金额不能大于您的账户余额！");
	else{
		$.messager.confirm(dialog_title,'您确认要转账给'+toaccname.toUpperCase()+'枫杨果'+curr+'吗？',function(r){    
			if (r){ 
				alertmsg(2);
				$.ajax({
					type : "POST", //访问WebService使用Post方式请求 
					url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data : {
						erpser : "moveaccmoney",
						toaccname : toaccname.toUpperCase(),
						curr : curr
					}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType : 'json',
					success : function(data) { //回调函数，result，返回值 
						if(valideData(data)){
							alert("枫杨果转账成功！");
							setCookie("BALCURR",oldcurr-curr,10);
							top.$("#menu-balcurr").html(oldcurr-curr);
							$('#transd').dialog('close');
							getbalcurrlist("1");
						}
					}
				});
			}
		});
	}
}

</script>
</body>
</html>