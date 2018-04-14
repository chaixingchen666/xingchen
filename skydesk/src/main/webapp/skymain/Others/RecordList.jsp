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
<title>操作日志</title>
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
	<input type="text" data-end="yes" placeholder="输入<回车搜索>" class="ipt1" id="qsfindbox" maxlength="20" data-enter="getdata(1)">
	<input type="button" id="highsearch" class="btn2" value="高级搜索▼" onclick="toggle()">
	</div>
	<div class="fl hide" id="searchbtn">
			<input type="button" class="btn2" type="button" value="搜索" onclick="searchdata(1);toggle();"> 
			<input type="button" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()">
	</div>
	<div class="fr">
		<input type="button" class="btn3" style="width: 45px" value="导出" onclick="exportxls()">
	</div>
	</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchdata(1);toggle();">
<div class="search-box">
<div class="s-box"><div class="title">开始日期</div>
<div class="text">
<input type="text" name="smindate" id="smindate" placeholder="<输入>"  style="width: 162px;height:29px" class="easyui-datebox">
</div>
</div>
<div class="s-box"><div class="title">结束日期</div>
<div class="text">
<input type="text" name="smaxdate" id="smaxdate" placeholder="<输入>"  style="width: 162px;height:29px" class="easyui-datebox">
</div>
</div>
<div class="s-box"><div class="title">程序名称</div>
<div class="text">
<input class="hig25 wid160" type="text" name="sprogname" id="sprogname" placeholder="<输入>" >
</div>
</div>
<div class="s-box"><div class="title">操作摘要</div>
<div class="text">
<input class="hig25 wid160" type="text" name="sremark" id="sremark" placeholder="<输入>" >
</div>
</div>
<div class="s-box"><div class="title">操作人</div>
<div class="text">
<input class="hig25 wid160" type="text" name="slatop" id="slatop" placeholder="<输入>" >
</div>
</div>

</div>
</div>
	</div>
	<table id="gg" style="overflow: hidden;"></table>
	<!-- 分页 -->
	<div class="page-bar">
		<div class="page-total">
			共有<span id="total">0</span>条记录
		</div>
		<div id="pp" class="tcdPageCode page-code"></div>
	</div>
<script type="text/javascript">

setqxpublic();
var searchb = false;
var opser = "get";
function toggle() {
	if (searchb) {
		$('#highsearch').val('高级搜索▼');
		searchb = false;
		$('#searchbtn,.searchbar').hide();
	} else {
		$('#highsearch').val('高级搜索▲');
		searchb = true;
		$('#searchbtn ,.searchbar').show();
	}
	setTimeout(function(){
		$('#gg').datagrid('resize',{
			height: $('body').height()-50
		});
		$('#smindate').focus();			
	}, 200);

}
function resetsearch(){
	var date = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue',date.Format('yyyy-MM-dd'));
	$('#progname,#sremark,#slatop').val('');
}
$(function(){
	setrecordt();
	getdata(1);
});

function setrecordt(){
	$("#pp").createPage(
			{
				pageCount : 1,
				current : 1,
				backFn : function(p) {
					if(opser=="get")
						getdata(p);
					else
						searchdata(p);
				}
			});

	//加载数据
	$('#gg')
			.datagrid(
					{
						idField : 'ID',
						height : $('body').height() - 50,
						fitColumns : true,
						striped : true, //隔行变色
						nowrap: false,
						singleSelect : true,
						sort: "LASTDATE",
						order: "asc",
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
									sortable: true,
									formatter : function(value, row,index) {
										var val = Math.ceil(Number(value)/pagecount)-1;
										return val*pagecount+Number(index)+1;
									}
								},{
									field : 'LASTDATE',
									title : '操作日期',
									width : 120,
									fixed : true,
									sortable: true,
									halign : 'center',
									align : 'center'
								},{
									field : 'PROGNAME',
									title : '程序名称',
									width : 120,
									fixed : true,
									sortable: true,
									halign : 'center',
									align : 'center'
								},{
									field : 'REMARK',
									title : '操作摘要',
									width : 400,
									fixed : true,
									sortable: true,
									halign : 'center',
									align : 'left'
								},{
									field : 'LASTOP',
									title : '操作人',
									width : 80,
									fixed : true,
									sortable: true,
									halign : 'center',
									align : 'center'
								}] ],
								
						onClickRow :function(index,row){
							$('#uindex').val(index);
						},
						onSortColumn: function(sort, order){
							$('#gg').datagrid('options').sort= sort;
							$('#gg').datagrid('options').order = order;
							$('#pp').refreshPage();
						},
						pageSize : 20,
						toolbar : '#toolbars'
					}).datagrid("keyCtr");
}

function getdata(page){
	opser = "get";
	var findbox = $('#qsfindbox').val();
	var sort = $('#gg').datagrid('options').sort;
	var order = $('#gg').datagrid('options').order;
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "listlogrecord",
			findbox : findbox,
			sort : sort,
			order : order,
			rows: pagecount,
			accid: getValuebyName('ACCID'),
			page : page
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					$("#pp").setPage({
						pageCount : Math.ceil(data.total / pagecount),
						current : Number(page)
					});
					$('#gg').datagrid('loadData', data);
					$('#total').html(data.total);
					$('body').focus();
				}
			}catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}

function searchdata(page){
	opser = "search";
	var sort = $('#gg').datagrid('options').sort;
	var order = $('#gg').datagrid('options').order;
	var mindate = $('#smindate').datebox('getValue');
	var maxdate = $('#smaxdate').datebox('getValue');
	var progname = $('#sprogname').val();
	var remark = $('#sremark').val();
	var lastop = $('#slatop').val();
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "listlogrecord",
			sort : sort,
			order : order,
			mindate: mindate,
			maxdate: maxdate,
			remark: remark,
			progname: progname,
			lastop: lastop,
			rows: pagecount,
			accid: getValuebyName('ACCID'),
			page : page
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					$('#total').html(data.total);
					$("#pp").setPage({
						pageCount : Math.ceil(data.total / pagecount),
						current : Number(page)
					});
					$('#gg').datagrid('loadData', data);
					$('body').focus();
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