<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0"> 
<meta name="renderer" content="webkit|ie-comp|ie-stand" /> 
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1"><meta name="renderer" content="webkit|ie-comp|ie-stand" />
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>商场调班管理</title>
</head>
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
	<input class="btn1" type="button" value="添加" onclick="addnoted()">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatenoted()">
	<input type="text" placeholder="搜索店铺、销售人<回车搜索>" data-end="yes" data-enter="getnotedata()" class="ipt1" id="qsnotevalue" >
	<input type="button" id="highsearch" class="btn2" value="高级搜索▼" onclick="toggle()">
	<span id="serbtn" class="fr hide">
	<input type="button" class="btn2" type="button" value="搜索" id="odser" onclick="searchnote();toggle();"> 
	<input type="button" id="resetser" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()">
	</span>
</div>
<div class="fr hide">
	<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
<!-- 	<input type="button" class="btn3" value="打印端口设置" onclick="selprint()"> -->
<!-- 	<input type="button" class="btn3" id="print" value="后台打印"> -->
</div>
</div>
<!-- 高级搜索 -->
<div class="searchbar" style="display: none" data-enter="searchnote();toggle();">
<div class="search-box">
<div class="s-box"><div class="title">开始日期</div>
<div class="text"><input class="easyui-datebox" name="smindate" id="smindate"
						style="width: 163px; height: 28px; margin-left: 10px"/>
</div>
</div>
<div class="s-box"><div class="title">结束日期</div>
<div class="text"><input class="easyui-datebox" name="smaxdate" id="smaxdate"
					style="width: 163px; height: 28px; margin-left: 10px" />
</div>
</div>
<div class="s-box"><div class="title">销售人</div>
<div class="text"><input class="edithelpinput emp_help" data-value="#ssalemanid" maxlength="20" placeholder="<选择或输入>"
					   type="text" id="ssalemanname" name="ssalemanname"><span  onclick="selectemploye('search')"></span> 
					   <input type="hidden" id="ssalemanid" name="ssalemanid">
</div>
</div>
<div class="s-box"><div class="title">店铺</div>
<div class="text"><input class="edithelpinput house_help" data-value="#shouseid" maxlength="20" placeholder="<选择或输入>"
					   type="text" id="shousename" name="shousename"><span  onclick="selecthouse('search')"></span> 
					   <input type="hidden" id="shouseid" name="shouseid">
</div>
</div>
<div class="s-box"><div class="title">制单人</div>
<div class="text"><input type="text" class="hig25 wid160" id="soperant" name="soperant" placeholder="<输入>" />
</div>
</div>
<div class="s-box"><div class="title">备注</div>
<div class="text"><input type="text" class="hig25 wid160" id="sremark" name="sremark" placeholder="<输入>"/>
</div>
</div>
</div>
</div>
</div>
	<table id="gg" style="overflow: hidden; height:9000000px;"></table>
	<!-- 分页 -->
	<div class="page-bar">
		<div class="page-total">
			共有<span id="pp_total"></span>条记录
		</div>
		<div id="pp" class="tcdPageCode page-code"></div>
	</div>
	<!-- 修改 --------------------------------------------------->
	<div id="ud" title="编辑" style="width: 340px; height: 300px;" class="easyui-dialog"  closed="true">
		<table width="300px" border="0" cellspacing="10">
			<tr>
				<td width="70px" align="right">日期</td>
				<td width="180px" align="left">
				<input id="uworkdate" type="text" style="width: 162px;height:29px" class="easyui-datetimebox" data-options="showSeconds:true,readonly:true,hasDownArrow:false">
					</td>
			</tr>
			<tr>
				<td align="right">销售员</td>
				<td align="left"><input type="text" 
					class="edithelpinput wid145 hig25 emp_help" maxlength="20" data-value="usalemanid" placeholder="<选择或输入>" id="usalemanname"
					name="usalemanname"/><span onclick="selectemploye('updatesaleman')"></span> 
					<input type="hidden" id="usalemanid" name="usalemanid"></td>
			</tr>
			<tr>
				<td align="right" color="#ff7900">*&nbsp;店铺</td>
	            <td align="right">
	            <input class="edithelpinput wid145 hig25 house_help" type="text" id="uhousename" name="uhousename" maxlength="20" placeholder="<选择或输入>" data-value="#uhouseid"
	            value=""><span onclick="selecthouse('updatehouse')"></span><input type="hidden" id="uhouseid" name="uhouseid"> </td>
			
			</tr>
			<tr>
				<td align="right">备注</td>
				<td align="left"><input type="text" placeholder="<输入>"
					id="uremark" name="uremark" style="width: 160px; height: 25px" />
				</td>
			</tr>
		</table>
		<div class="dialog-footer" style="text-align: center;" id="div_updatebtn">
		<input type="button" class="btn1" value="保存" id="btn_update0" name="update" onclick="updatenote(0)">
		<input type="button" class="btn1" value="保存并提交" id="btn_update1" name="update1" onclick="updatenote(1)">
		</div>
	</div>
<script type="text/javascript" charset="UTF-8">
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize = function() {
	changeDivHeight();
}
function changeDivHeight() {
	var height = document.documentElement.clientHeight; //获取页面可见高度 
	var width = document.documentElement.clientWidth; //获取页面可见高度 
	$('#gg').datagrid('resize', {
		width: width,
		height: height - 50
	});
}
var pgid = 'pg1609';
var todaydate = "";
var s = false;
var levelid = getValuebyName("GLEVELID");
var epname = getValuebyName("EPNAME");
setqxpublic();
$(function() {
	setaddbackprint("1609");
	$("#pp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			getnotelist(p);
		}
	});
	getserdatetime();
	$('#gg').datagrid({
		idField: "ID",
		width: '100%',
		height: $('body').height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		onDblClickRow: function(rowIndex, rowData) {
			updatenoted();
		},
		onSelect: function(index,row){
			if(row){
				$("#uid").val(row.ID);
				$("#uhouseid").val(row.HOUSEID);
			}
		},
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if (isNaN(Number(value)) && value!=undefined && value.length > 0) return value;
				var p = $("#pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
		},
		{
			field: 'WORKDATE',
			title: '日期',
			align: 'center',
			halign: 'center',
			fixed: true,
			width: 100
		},
		{
			field: 'SALENAMENAME',
			title: '销售员',
			align: 'left',
			halign: 'center',
			fixed: true,
			width: 160
		},
		{
			field: 'HOUSENAME',
			title: '店铺',
			align: 'center',
			halign: 'center',
			fixed: true,
			width: 160
		},{
			field: 'REMARK',
			title: '备注',
			width: 200,
			fixed: true,
			align: 'center',
			halign: 'center',
		},{
			field: 'OPERANT',
			title: '制单人',
			align: 'center',
			halign: 'center',
			fixed: true,
			width: 100
		},{
			field: 'ACTION',
			title: '操作',
			align: 'center',
			halign: 'center',
			width: 160,
			fixed: true,
			formatter: function(value, row, index) {
				var allowcheck = levelid == 0 || levelid==3 || levelid ==4 || levelid == 5;
				var html = "";
				var checkman = row.CHECKMAN==undefined?"":row.CHECKMAN;
				var stg = row.STATETAG;
				if (row.ACTION == '1') {
					return "";
				} else if (stg == 1&& checkman.length==0) {
					var workdate = row.WORKDATE;
					var date = workdate.substring(0, 10);
					 if (allowchedan == 1 && Number(row.ISTODAY)==1)
						html += '<input type="button" class="m-btn" value="撤单" onclick="notecancel(' + index + ')">';
				} else if (stg == 0 && row.OPERANT == epname) {
					html += '<input type="button" class="m-btn" value="删除" onclick="delnote(' + index + ')">' 
					+ '<input type="button" class="m-btn" id="' + index + '" value="提交" onclick="updatestatetag(' + index + ')">';
				}
				return html;
			}
		}]],
		toolbar: "#toolbars",
		pageSize: 20

	}).datagrid("keyCtr", "updatenoted();");
	getnotedata();
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
});
//添加框
function addnoted() {
	$('#usalemanname,#uhousename,#uremark').removeProp("readonly");
	$('#usalemanname,#uhousename').next().show();
	$('#div_updatebtn').show();
	$("#ud table :input").val("");
	$("#ud input#btn_update0").val("保存并继续");
	$('#uhousename').val(getValuebyName('HOUSENAME'));
	$('#uhouseid').val(getValuebyName('HOUSEID'));
	getserdatetime();
	$('#ud').dialog("setTitle","添加").dialog('open');
}
//修改框
function updatenoted() {
	var arrs = $('#gg').datagrid('getSelected');
	if (!arrs) {
		alerttext('请选择一行数据，进行编辑');
	} else {
		if (arrs.STATETAG == 0 && arrs.OPERANT == epname) {
			$("#ud input#btn_update0").val("保存");
			$('#ud').dialog("setTitle", "编辑");
			$('#ud').dialog('open');
			if (arrs) {
				$('#usalemanname,#uhousename,#uremark').removeProp("readonly");
				$('#usalemanname,#uhousename').next().show();
				$('#div_updatebtn').show();
			}
			$('#uworkdate').datetimebox({
				readonly: false,
				hasDownArrow: true
			});
		}else{
			$('#ud').dialog("setTitle", "浏览");
			$('#ud').dialog('open');
			$('#usalemanname,#uhousename,#uremark').prop("readonly",true);
			$('#usalemanname,#uhousename').next().hide();
			$('#div_updatebtn').hide();
			$('#uworkdate').datetimebox({
				readonly: true,
				hasDownArrow: false
			});
		}
		getnotebyid(arrs.ID);
	};
}

//-----------------------------------------------ajax(数据)
//获取服务器当前日期时间
function getserdatetime() {
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "serverdatetime"
		},
		//这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			var datetime = data.SERVERDATETIME;
			$('#uworkdate').datetimebox({
				readonly: false,
				hasDownArrow: true
			});
			changedatebj = false;
			$('#uworkdate').datetimebox('setValue', datetime);
			changedatebj = true;
			todaydate = datetime.substring(0, 10);
		}
	});
}
var currentdata = {};
var getnotelist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	//		 	var sort = options.sortName;
	//		 	var order = options.sortOrder;
	currentdata["erpser"] = "shopsalemanlist";
	//		 	currentdata["sort"] = sort;
	//		 	currentdata["order"] = order;
	currentdata["mindate"] = $("#smindate").datebox("getValue");
	currentdata["maxdate"] = $("#smaxdate").datebox("getValue");
	currentdata["fieldlist"] = "a.id,a.id,a.workdate,b.salemanname,b.salemanid,a.remark,a.statetag,a.lastop,c.houseid,c.housename,a.operant";
	currentdata["rows"] = pagecount;
	currentdata["page"] = page;
	$dg.datagrid('loadData', nulldata);
	alertmsg(6);
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
					dqzjl = data.total;
					dqzcurr = data.totalcurr;
					$("#pp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					data.footer = [{
						ROWNUMBER: "合计",
						CURR: data.totalcurr
					}];
					$dg.datagrid('loadData', data);
					if (data.total > 0) $('#gg').datagrid('scrollTo', 0);
					$('#pp_total').html(data.total);
					$('#qsnotevalue').focus();
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}
//搜索
function getnotedata() {
	var value = $('#qsnotevalue').val();
	currentdata = {
		findbox: value
	};
	getnotelist(1);
}

//高级搜索
function searchnote() {
	var id = $('#sid').val();
	var remark = $('#sremark').val();
	var operant = $('#soperant').val();
	var salemanid = Number($('#ssalemanid').val());
	var houseid = Number($('#shouseid').val());
	currentdata = {
		operant: operant,
		id: id,
		remark: remark,
		salemanid: salemanid,
		houseid: houseid
	};
	getnotelist(1);
}
//高级搜索
var searchb = false;
function toggle() {
	ser = 'sear';
	if (searchb) {
		$('#highsearch').val('高级搜索▼');
		$('#serbtn').hide();
		searchb = false;
	} else {
		$('#highsearch').val('高级搜索▲');
		$('#serbtn').show();
		searchb = true;
	}
	$('.searchbar').slideToggle('fast');
}
//清除高级搜索数据
function resetsearch() {
	$("#smindate,#smaxdate").datebox("setValue",top.getservertime());
	$('#soperant').val("");
	$('#sid').val("");
	$('#ssalemanname').val("");
	$('#ssalemanid').val("");
	$('#sremark').val("");
	$('#shouseid').val("");
	$('#shousename').val("");
}
//根据号获取明细
function getnotebyid(id) {
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "getshopsalemanbyid",
			id: id,
			showbalcurr: 1,
			fieldlist: "a.id,a.id,a.workdate,b.salemanname,b.salemanid,a.remark,a.statetag,a.lastop,d.houseid,d.housename" 
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				total = data.total;
				rows = data.rows;
				if (total > 0) {
					var row = rows[0];
					$('#uid').val(row.ID);
					changedatebj = false;
					$('#uworkdate').datetimebox('setValue', row.WORKDATE);
					changedatebj = true;
					$('#usalemanname').val(row.SALEMANNAME);
					$('#uremark').val(row.REMARK);
					$('#uhousename').val(row.HOUSENAME);
					$('#uhouseid').val(row.HOUSEID);
					dqcurr = row.CURR;
					$('#ubalcurr').val(row.BALCURR);
				}
			}
		}
	});
}
//撤单
function notecancel(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	var row = rows[index];
	if (!row) {
		alerttext("记录无效！");
		return;
	}
	var id = row.ID;
	$.messager.confirm(dialog_title, '您确认要撤销' + id + '？',
	function(r) {
		if (r) {
			alertmsg(2);
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "incomecurrcancel",
					id: id
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值
					if (valideData(data)) {
						var index = $dg.datagrid("getRowIndex",id);
						$dg.datagrid('updateRow', {
							index: index,
							row: {
								STATETAG: "0"
							}
						}).datagrid('refresh');
					}
				}
			});
		}
	});
}
//直接提交
function updatestatetag(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	var row = rows[index];
	if (!row) {
		alerttext("记录无效！");
		return;
	}
	var id = row.ID;
	$.messager.confirm(dialog_title, '您确认要提交' + id + '？',
	function(r) {
		if (r) {
			alertmsg(2);
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "updateshopsalemanbyid",
					id: id,
					statetag: 1
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值
					if (valideData(data)) {
						var index = $dg.datagrid("getRowIndex",id);
						$('#gg').datagrid('updateRow', {
							index: index,
							row: {
								STATETAG: 1
							}
						});
					}
				}
			});
		}
	});
}
//保存或提交
function updatenote(statetag) {
	if ($('#usalemanname').val() == "") {
		alerttext('请选择客户！');
		return;
	}
	var id = $('#uid').val();
	var workdate = $('#uworkdate').datetimebox('getValue');
	var salemanid = $('#usalemanid').val();
	var curr = $('#ucurr').val();
	var remark = $('#uremark').val();
	var payno = $('#upayno').val();
	var houseid = $('#uhouseid').val();
	var erpser = "updateshopsalemanbyid";
	if(id.length==0) erpser="addshopsaleman";
	var dofn = function() {
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: erpser,
				workdatestr: workdate,
				salemanid: salemanid,
				remark: remark,
				statetag: statetag,
				id: id,
				houseid: houseid
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值
				if (valideData(data)) {
					var $dg = $('#gg');
					var index = $dg.datagrid("getRowIndex",id);
					if(id.length>0){
						$dg.datagrid('updateRow', {
							index: index,
							row: {
								SALEMANID: salemanid,
								SALEMANNAME: $('#usalemanname').val(),
								CURR: curr,
								REMARK: remark,
								STATETAG: statetag,
								HOUSEID: houseid,
								HOUSENAME: $('#uhousename').val()
							}
						});
						$("#ud").dialog("close");
					}else{
						id =  data.ID;
						workdate =  data.WORKDATE;
						id =  data.ID;
						$dg.datagrid('insertRow', {
							index: 0,
							row: {
								ID: id,
								WORKDATE: workdate,
								OPERANT: getValuebyName("EPNAME"),
								SALEMANID: salemanid,
								SALEMANNAME: $('#usalemanname').val(),
								REMARK: remark,
								STATETAG: statetag,
								HOUSEID: houseid,
								HOUSENAME: $('#uhousename').val()
							}
						});
						$("#qsnotevalue,#ud table :input").val("");
						$("#smaxdate").datebox("setValue", top.getservertime());
						getserdatetime();
						$('#uhousename').val(getValuebyName('HOUSENAME'));
						$('#uhouseid').val(getValuebyName('HOUSEID'));
						if (statetag == 1) $('#ud').dialog('close');
					}
				}
			}
		});
	};
	if (statetag == 1) {
		$.messager.confirm(dialog_title, '您确认要提交？',
		function(r) {
			if (r) {
				dofn();
			}
		});
	} else {
		dofn();
	}
}
//删除
function delnote(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	var row = rows[index];
	if (!row) {
		alerttext("记录无效！");
		return;
	}
	var id = row.ID;
	$.messager.confirm(dialog_title, '您确认要删除？',
	function(r) {
		if (r) {
			alertmsg(2);
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "delshopsalemanbyid",
					id: id
				},
				//这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值
					if (valideData(data)) {
						var index = $dg.datagrid("getRowIndex",id);
						var row = $dg.datagrid("getSelected");
						$('#pp_toal').html(dqzjl);
					}
				}
			});
		}
	});
}
</script>
	<!-- 店铺帮助列表 -->
	<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
	<!-- 销售人帮助列表 -->
	<jsp:include page="../HelpList/EmpHelpList.jsp"></jsp:include>
</body>
</html>