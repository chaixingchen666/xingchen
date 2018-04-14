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
<title>颜色编码</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/webuploader/webuploader.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
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
	<input class="btn1" type="button" value="添加" onclick="addcolord()">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatecolord()">
	<input type="text" data-end="yes" placeholder="输入颜色名称或简称<回车搜索>" class="ipt1" id="qscolorvalue" maxlength="20" data-enter="getcolordata()">
	<input type="button" id="highsearch" class="btn2" value="高级搜索▼" onclick="toggle()">
</div>
<div class="fl hide" id="searchbtn">
	<input type="button" class="btn2" type="button" value="搜索" onclick="searchcolor();toggle();"> 
	<input type="button" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()">
</div>
<div class="fr">
	<input type="button" class="btn3" style="width: 45px" value="导入" onclick="openimportd()"> 
	<span class="sepreate"></span>
	<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
</div>
</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchcolor();toggle();">
	<div class="search-box">
	<div class="s-box">
	<div class="title">颜色名称</div>
	<div class="text"><input class="wid160 hig25" id="scolorname" type="text" maxlength="20" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title">颜色色码</div>
	<div class="text"><input class="wid160 hig25" id="scolorno" type="text" maxlength="6" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title">品牌</div>
	<div class="text"><input class="edithelpinput brand_help" id="sbrandname" type="text" data-value="#sbrandid" placeholder="<输入>">
	<span onclick="selectbrand('search')"></span>
	<input type="hidden" id="sbrandid">
	</div>
	</div>
	<div class="s-box">
	<div class="title">状态</div>
	<div class="text">
	<label>
	<input type="radio" name="snouse" id="st1" value="0" checked>在用
	</label>
	<label>
	<input type="radio" name="snouse" id="st2" value="1">禁用
	</label>
	<label>
	<input type="radio" name="snouse" id="st3" value="2">所有
	</label>
	</div>
	</div>
	</div>
</div>
</div>
<table id="gg" style="overflow: hidden;"></table>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total">
	共有<span id="pp_total">0</span>条记录
</div>
<div id="pp" class="tcdPageCode page-code"></div>
</div>
<!-- 修改颜色窗 -->
<div id="ud" title="<span id='udtitle'>编辑颜色</span>" style="width: 350px; height: 300px;" class="easyui-dialog" closed=true>
	<div style="text-align: center;">
	<table class="table1" cellspacing="10">
	<tr>
	<td class="header skyrequied" align="right">
	颜色
	</td>
	<td align="left">
	<input type="hidden" id="ucolorid">
	<input type="hidden" id="uindex">
	<input class="hig25 wid160" type="text" placeholder="<输入>" name="ucolorname" id="ucolorname" maxlength="20" /> 
	</td>
	</tr>
	<tr>
	<td class="header skyrequied" align="right">
	色码
	</td>
	<td align="left">
	<input class="hig25 wid160" type="text" placeholder="<输入>" name="ucolorno" id="ucolorno" maxlength="20" /> 
	</td>
	</tr>
	<tr>
	<td class="header" align="right">
	品牌
	</td>
	<td align="left">
	<input  type="text" class="edithelpinput brand_help" name="ubrandname" id="ubrandname" data-value="#ubrandid" maxlength="20" placeholder="<选择或输入>">
	<span onclick="selectbrand('update')"></span>
				<input type="hidden" name="ubrandid" id="ubrandid" value="">
	</td>
	</tr>
	<tr align="left">
    <td colspan="2"  class="tishi">
    	<p>1.颜色处输入产品的颜色名称。（如：黑色，红色等）</p>
    	<p>2.色码主要用于产生条码时使用，色码不允许使用汉字，同一商品多个颜色时，这几个颜色的色码不能填同一个值。如不用条码，色码处可不填内容，（如：01，A等）</p>
    </td>
  </tr>
	</table>
	</div>
	<div class="dialog-footer textcenter">
		<label class="updateshow"><input type="checkbox" name="unoused" id="unoused">禁用</label>
		<input type="button" id="savebtn" class="btn1"  name="updatecolor" value="保存" onclick="savecolor()">
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#ud').dialog('close')"> 
	</div>
	</div>
<script type="text/javascript" charset="UTF-8">
//权限设置

setqxpublic();
var pgid= "pg1002";
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
var searchb = false;
function toggle() {
	if (searchb) {
		$('#highsearch').val('高级搜索▼');
		searchb = false;
		$('#searchbtn').hide();
	} else {
		$('#highsearch').val('高级搜索▲');
		searchb = true;
		$('#searchbtn').show();
	}
	$('.searchbar').slideToggle('fast');
	$('#scolorname').focus();

}
//清空搜索条件
function resetsearch() {
	$('#st1').prop('checked', true);
	var idstr = "#scolorname,#scolorno,#sbrandname,#sbrandid";
	$(idstr).val('');
}
//添加框
function addcolord() {
	$("#udtitle").html("添加颜色");
	$("#savebtn").val("保存并继续");
	$('#ud').dialog('open');
	$('#ucolorname,#ucolorid,#ucolorno,#ubrandname,#ubrandid,#uindex').val("");
	$(".updateshow").hide();
	$('#ucolorname').focus();
}
//编辑框
function updatecolord() {
	var row = $('#gg').datagrid('getSelected');
	if (!row) {	
		alerttext("请选择一行数据，进行编辑!");
	} else {
		var noused = row.NOUSED;
		$("#udtitle").html("编辑颜色");
		$("#savebtn").val("保存");
		$(".updateshow").show();
		$('#ud').dialog('open');
		$('#ucolorname').focus();
		$('#ucolorname').val(row.COLORNAME);
		$('#ucolorno').val(row.COLORNO);
		$('#ucolorid').val(row.COLORID);
		$('#ubrandname').val(row.BRANDNAME);
		$('#ubrnadid').val(row.BRANDID);
		if (noused != 0) {
			$('#unoused').prop('checked', true);
		} else {
			$('#unoused').removeProp('checked');
		}
	}
}
$(function() {
	$("#pp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			getcolorlist(p);
		}
	});
	//加载数据
	$('#gg').datagrid({
		idField: 'COLORID',
		height: $("body").height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
// 		sortName: "COLORNAME",
// 		sortOrder: "asc",
		columns: [[{
			field: 'COLORID',
			title: '颜色ID',
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
		},{
			field: 'COLORNAME',
			title: '颜色',
			width: 200,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'center'
		},{
			field: 'COLORNO',
			title: '色号',
			width: 100,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'center'
		},{
			field: 'BRANDNAME',
			title: '品牌',
			width: 100,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'center',
			formatter: function(value,row,index){
				if(value==""||value=="无")
					return  "通用颜色";
				else return value;
			}
		},{
			field: 'NOUSED',
			title: '禁用',
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
				return '<input type="checkbox" id="disabledcheckin_' + index + '" class="m-btn" onchange="disabledcolor(' + index + ')" ' + checked + '>';
			}
		},
		{
			field: 'ACTION',
			title: '操作',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				return '<input type="button" class="m-btn" value="删除" onclick="delcolor(' + index + ')">';
			}
		}]],
		onSortColumn: function(sort, order) {
			$('#pp').refreshPage();
		},
		onClickRow: function(index, row) {
			$('#uindex').val(index);
		},
		onDblClickRow: function(rowIndex, rowData) {
			updatecolord();
		},
		pageSize: 20,
		toolbar: '#toolbars'
	}).datagrid("keyCtr", "updatecolord()");
	getcolordata();
	uploader_xls_options.uploadComplete = function(file){
		$('#pp').refreshPage();
	}
	uploader_xls = SkyUploadFiles(uploader_xls_options);
	setuploadserver({
		server: "/skydesk/fyimportservice?importser=appendcolor",
		xlsmobanname: "colorcode",
		channel: "colorcode"
	});
});

var currentdata = {};
var getcolorlist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
// 	var sort = options.sortName;
// 	var order = options.sortOrder;
	currentdata["erpser"] = "colorlist";
	currentdata["fieldlist"] = "a.colorid,a.colorname,a.colorno,a.noused,a.brandid,b.brandname";
// 	currentdata["sort"] = sort;
// 	currentdata["order"] = order;
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
function getcolordata() {
	var value = $('#qscolorvalue').val();
	alertmsg(6);
	currentdata = {
		findbox: value
	};
	getcolorlist(1);
}
//搜索颜色
function searchcolor() {
	var colorname = $('#scolorname').val();
	var colorno = $('#scolorno').val();
	var brandid = $('#sbrandid').val();
	var noused = 0;
	if ($('#st1').prop('checked')) noused = "0";
	else if ($('#st2').prop('checked')) noused = "1";
	else noused = "2";
	alertmsg(6);
	currentdata = {
		colorname: colorname,
		colorno: colorno,
		brandid: brandid,
		noused: noused
	};
	getcolorlist(1);
}
//添加颜色
function savecolor() {
	var index = $("#uindex").val();
	var colorid = Number($("#ucolorid").val());
	var brandid = Number($("#ubrandid").val());
	var colorname = $('#ucolorname').val();
	var brandname = $('#ubrandname').val();
	var colorno = $('#ucolorno').val();
	if (colorname == "") {
		alerttext("颜色名不能为空");
		return;
	}
	var erpser = "addcolor";
	var noused = 0;
	if (colorid > 0){
		erpser = "updatecolorbyid";
		noused = $("#unoused").prop("checked") ? 1 : 0;
	}
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: erpser,
			colorid: colorid,
			brandid: brandid,
			colorname: colorname,
			colorno: colorno,
			noused: noused
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			try {
				if (valideData(data)) {
					var $dg = $("#gg");
					if (colorid == 0) {
						colorid = data.msg;
						$dg.datagrid('insertRow', {
							index: 0,
							row: {
								ROWNUMBER: 1,
								COLORID: colorid,
								BRANDID: brandid,
								BRANDNAME: brandname,
								COLORNAME: colorname,
								COLORNO: colorno,
								NOUSED: noused
							}
						}).datagrid('refresh');
						var total = Number($("#pp_total").html());
						if (!isNaN(total)) $("#pp_total").html(total + 1);
						if (isNaN(colorno)){
							$('#ucolorno').val("");
						}else
							$('#ucolorno').val(autogetno(colorno));
						$('#ucolorname').val("").focus();
					} else {
						$dg.datagrid('updateRow', {
							index: index,
							row: {
								COLORID: colorid,
								COLORNAME: colorname,
								BRANDID: brandid,
								BRANDNAME: brandname,
								COLORNO: colorno,
								NOUSED: noused
							}
						}).datagrid('refresh');
						$("#ud").dialog("close");
					}
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
function disabledcolor(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var colorid = row.COLORID;
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
				erpser: "updatecolorbyid",
				colorid: colorid,
				noused: noused
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				try {
					if (valideData(data)) {
						$dg.datagrid('updateRow', {
							index: index,
							row: {
								NOUSED: noused
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
//删除
function delcolor(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var colorid = row.COLORID;
		$.messager.confirm(dialog_title, '您确认要删除颜色' + row.COLORNAME + '？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delcolorbyid",
						colorid: colorid
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						try {
							if (valideData(data)) {
								$dg.datagrid("deleteRow", index).datagrid("refresh");
								var total = Number($("#pp_total").html());
								if (!isNaN(total)) $("#pp_total").html(total - 1);
							}
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
<jsp:include page="../HelpList/ImportHelp.jsp"></jsp:include>
<jsp:include page="../HelpList/BrandHelpList.jsp"></jsp:include>
</body>
</html>