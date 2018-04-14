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
<title>用户设备授权</title>
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
	<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
<div class="fl">
	<input type="text" data-end="yes" placeholder="输入用户名称或简称<回车搜索>" class="ipt1" id="qsdptvalue" maxlength="20" data-enter="getdptdata()">
<!-- 	<input type="button" id="highsearch" class="btn2" value="高级搜索▼" onclick="toggle()"> -->
</div>
</div>
<!-- 高级搜索栏 -->
</div>
<table id="gg" style="overflow: hidden;"></table>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total">
	共有<span id="pp_total">0</span>条记录
</div>
<div id="pp" class="tcdPageCode page-code"></div>
</div>
<script type="text/javascript" charset="UTF-8">
//权限设置
setqxpublic();
var pgid= "pg1019";
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize = function() {
	changeDivHeight();
}
function changeDivHeight() {
	var height = document.documentElement.clientHeight; //获取页面可见高度  
	$('#gg').datagrid('resize', {
		height: height - 50
	});
}
$(function() {
	$("#pp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			getuserdevicelist(p);
		}
	});
	//加载数据
	$('#gg').datagrid({
		idField: 'DPTID',
		height: $("body").height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
// 		sortName: "DPTNAME",
// 		sortOrder: "asc",
		columns: [[{
			field: 'EPID',
			title: '用户ID',
			fieldtype: "G",
			width: 200,
			expable: true,
			hidden: true
		},{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var p = $("#pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
		},
		{
			field: 'EPNAME',
			title: '用户名',
			width: 100,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'center'
		},
		{
			field: 'LX',
			title: '设备类型',
			width: 100,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'center'
		},
		{
			field: 'DEVICENO',
			title: '设备号',
			width: 300,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'center'
		},{
			field: 'LOGINENABLED',
			title: '允许登录',
			width: 80,
			fixed: true,
// 			sortable: true,
			fieldtype: "G",
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var nouse = Number(value);
				var checked = "";
				if (nouse == 1) checked = "checked";
				if (isNaN(nouse)) return "";
				return '<input type="checkbox" id="disabledcheckin_' + index + '" class="m-btn" onchange="disabledservice(' + index + ')" ' + checked + '>';
			}
		},{
			field: 'ACTION',
			title: '操作',
			width: 80,
			fixed: true,
// 			sortable: true,
			fieldtype: "G",
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				return '<input type="button" class="m-btn" onclick="deluserDevice('+index+')" value="删除">';
			}
		}]],
		pageSize: 20,
		toolbar: '#toolbars'
	}).datagrid("keyCtr");
	getuserdevicedata();
});

var currentdata = {};
var getuserdevicelist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
// 	var sort = options.sortName;
// 	var order = options.sortOrder;
	currentdata["erpser"] = "listuserdevice";
	currentdata["noused"] = 2;
	currentdata["fieldlist"] = "*";
	currentdata["rows"] = pagecount;
	currentdata["page"] = page;
	$dg.datagrid('loadData', nulldata);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: currentdata,
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$("#pp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$dg.datagrid('loadData', data);
					$('#pp_total').html(data.total);
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
//搜索
function getuserdevicedata() {
	var value = $('#qsdptvalue').val();
	alertmsg(6);
	currentdata = {
		findbox: value
	};
	getuserdevicelist(1);
}
function disabledservice(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var epid = row.EPID;
		var deviceno = row.DEVICENO;
		var $check = $('#disabledcheckin_' + index);
		var noused = 0;
		if ($check.prop('checked')) noused = 1;
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "updateuserdevice",
				deviceno: deviceno,
				epid: epid,
				loginenabled: noused
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				try {
					if (valideData(data)) {
						$dg.datagrid('updateRow', {
							index: index,
							row: {
								LOGINENABLED: loginenabled
							}
						});
					}
					$dg.datagrid('refresh');
				} catch(e) {
					// TODO: handle exception
					console.error(e);top.wrtiejsErrorLog(e);
				}
			}
		});
	}
}
function deluserDevice(index){
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var epid = row.EPID;
		var deviceno = row.DEVICENO;
		$.messager.confirm(dialog_title, '您确认要删除设备？',
				function(r) {
					if (r) {
						alertmsg(2);
						$.ajax({
							type: "POST",
							//访问WebService使用Post方式请求 
							url: "/skydesk/fyjerp",
							//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
							data: {
								erpser: "deleteuserdevice",
								deviceno: deviceno,
								epid: epid
							},
							//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
							dataType: 'json',
							success: function(data) { //回调函数，result，返回值 
								try {
									if (valideData(data)) {
										$dg.datagrid('deleteRow',index);
									}
									$dg.datagrid('refresh');
								} catch(e) {
									// TODO: handle exception
									console.error(e);top.wrtiejsErrorLog(e);
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