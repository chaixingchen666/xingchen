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
<title>波次编码</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/webuploader/webuploader.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
</head>
<body class="easyui-layout layout panel-noscroll">
<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
<div class="fl">
	<input class="btn1" type="button" value="添加" onclick="addwaved()">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatewaved()">
	<input type="text" data-end="yes" placeholder="输入波次名称或简称<回车搜索>" class="ipt1" id="qswavevalue" maxlength="20" data-enter="getwavedata()">
<!-- 	<input type="button" id="highsearch" class="btn2" value="高级搜索▼" onclick="toggle()"> -->
</div>
<div class="fl hide" id="searchbtn">
	<input type="button" class="btn2" type="button" value="搜索" onclick="searchwave();toggle();"> 
	<input type="button" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()">
</div>
<div class="fr hide">
	<input type="button" class="btn3" style="width: 45px" value="导入" onclick="openimportd()"> 
	<span class="sepreate"></span>
	<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
</div>
</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchwave();toggle();">
	<input type="hidden" id="swaveid">
	<div class="search-box">
	<div class="s-box">
		<div class="title">波次名称</div>
		<div class="text"><input class="wid160 hig25" id="swavename" maxlength="20" type="text" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title">状态</div>
		<div class="text">
		<label>
		<input type="radio" name="snouse" id="st1" value="0">在用
		</label>
		<label>
		<input type="radio" name="snouse" id="st2" value="1">禁用
		</label>
		<label>
		<input type="radio" name="snouse" id="st3" value="2" checked>所有
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
<!-- 修改波次窗 -->
<div id="ud" title="<span id='udtitle'>编辑波次</span>" style="width: 350px; height: 250px;" class="easyui-dialog" closed=true>
	<div style="margin-top: 10px; text-align: center;">
	<table class="table1" cellspacing="10">
	<tr>
	<td class="header skyrequied" align="right">
	波次名称
	</td>
	<td>
	<input type="hidden" id="uwaveid">
	<input type="hidden" id="uindex">
	<input class="hig25 wid160" type="text" placeholder="<输入>" name="uwavename" id="uwavename" maxlength="20" /> 
	</td>
	</tr>
	<tr>
		<td class="header" align="right">
		开始日期
		</td>
		<td align="left">
		<input type="text" id="ubegindate" placeholder="<输入>" style="width: 162px;height:29px" class="easyui-datebox">
		</td>
		</tr>
		<tr>
		<td class="header" align="right">
		结束日期
		</td>
		<td align="left">
		<input type="text" id="uenddate" placeholder="<输入>" style="width: 162px;height:29px" class="easyui-datebox">
		</td>
		</tr>
	</table>
	</div>
	<div class="dialog-footer textcenter">
		<input type="button" id="savebtn" class="btn1"  name="updatewave" value="保存" onclick="savewave()">
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#ud').dialog('close')"> 
	</div>
	</div>
<script type="text/javascript" charset="UTF-8">
//权限设置

setqxpublic();
var pgid= "pg1018";
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
	$('#swavename').focus();

}
//清空搜索条件
function resetsearch() {
	$('#st1').prop('checked', true);
	var idstr = "#swavename";
	$(idstr).val('');
}
//添加框
function addwaved() {
	$("#udtitle").html("添加波次");
	$("#savebtn").val("保存并继续");
	$('#ud').dialog('open');
	$('#uwavename,#uwaveid,#uindex').val("");
	$(".updateshow").hide();
	$('#uwavename').focus();
}
//编辑框
function updatewaved() {
	var row = $('#gg').datagrid('getSelected');
	if (!row) {
		alerttext("请选择一行数据，进行编辑!");
	} else {
		$("#udtitle").html("编辑波次");
		$("#savebtn").val("保存");
		$(".updateshow").show();
		$('#ud').dialog('open');
		$('#uwavename').focus();
		$('#uwavename').val(row.WAVENAME);
		$('#ubegindate').datebox("setValue",row.BEGINDATE);
		$('#uenddate').datebox("setValue",row.ENDDATE);
		$('#uwaveid').val(row.WAVEID);
	}
}
$(function() {
	$("#pp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			getwavelist(p);
		}
	});
	//加载数据
	$('#gg').datagrid({
		idField: 'WAVEID',
		height: $("body").height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
// 		sortName: "WAVENAME",
// 		sortOrder: "asc",
		columns: [[{
			field: 'WAVEID',
			title: '波次ID',
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
			field: 'WAVENAME',
			title: '波次',
			width: 200,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'center'
		}, {
			field: 'BEGINDATE',
			title: '开始日期',
			width: 80,
			sortable: true,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: datefm
		}, {
			field: 'ENDDATE',
			title: '结束日期',
			width: 100,
			sortable: true,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: datefm
		},{
			field: 'ACTION',
			title: '操作',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				return '<input type="button" class="m-btn" value="删除" onclick="delwave(' + index + ')">';
			}
		}]],
		onSortColumn: function(sort, order) {
			$('#pp').refreshPage();
		},
		onClickRow: function(index, row) {
			$('#uindex').val(index);
		},
		onDblClickRow: function(rowIndex, rowData) {
			updatewaved();
		},
		pageSize: 20,
		toolbar: '#toolbars'
	}).datagrid("keyCtr", "updatewaved()");
	getwavedata();
	uploader_xls_options.uploadComplete = function(file){
		$('#pp').refreshPage();
	}
	uploader_xls = SkyUploadFiles(uploader_xls_options);
	setuploadserver({
		server: "/skydesk/fyimportservice?importser=appendwave",
		xlsmobanname: "wavecode",
		channel: "wavecode"
	});
});

var currentdata = {};
var getwavelist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
// 	var sort = options.sortName;
// 	var order = options.sortOrder;
	currentdata["erpser"] = "warewavelist";
// 	currentdata["sort"] = sort;
// 	currentdata["order"] = order;
	currentdata["rows"] = pagecount;
	currentdata["page"] = page;
	currentdata["fieldlist"] = "wavename,waveid,begindate,enddate";
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
function getwavedata() {
	var value = $('#qswavevalue').val();
	alertmsg(6);
	currentdata = {
		findbox: value
	};
	getwavelist(1);
}
//添加波次
function savewave() {
	var index = $("#uindex").val();
	var waveid = Number($("#uwaveid").val());
	var wavename = $('#uwavename').val();
	if (wavename == "") {
		alerttext("波次名不能为空");
		return;
	}
	var begindate = $('#ubegindate').datebox('getValue');
	var enddate = $('#uenddate').datebox('getValue');
	if (begindate.length == 0) {
		alerttext('请选择开始日期');
		return;
	}
	if (enddate.length == 0) {
		alerttext('请选择结束日期');
		return;
	}
	var erpser = "addwarewave";
	if(waveid>0)
		erpser = "updatewarewavebyid";
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: erpser,
			waveid: waveid,
			begindate: begindate,
			enddate: enddate,
			wavename: wavename
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			try {
				if (valideData(data)) {
					var $dg = $("#gg");
					if (waveid == 0) {
						waveid = data.msg;
						$dg.datagrid('insertRow', {
							index: 0,
							row: {
								ROWNUMBER: 1,
								WAVEID: waveid,
								WAVENAME: wavename,
								BEGINDATE: begindate,
								ENDDATE: enddate
							}
						}).datagrid('refresh');
						var total = Number($("#pp_total").html());
						if (!isNaN(total)) $("#pp_total").html(total + 1);
						$('#uwavename').val("").focus();
					} else {
						$dg.datagrid('updateRow', {
							index: index,
							row: {
								WAVEID: waveid,
								WAVENAME: wavename,
								BEGINDATE: begindate,
								ENDDATE: enddate
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
//删除
function delwave(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var waveid = row.WAVEID;
		$.messager.confirm(dialog_title, '您确认要删除波次' + row.WAVENAME + '？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delwarewavebyid",
						waveid: waveid
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
</body>
</html>