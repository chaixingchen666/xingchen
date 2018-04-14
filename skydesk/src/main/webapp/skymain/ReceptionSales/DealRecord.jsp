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
<title>线上支付查询</title>
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
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
	<div class="fl">
	<input type="text" data-end="yes" placeholder="输入<回车搜索>" class="ipt1" id="qsfindbox" maxlength="20" data-enter="getdata(1)">
	<input type="button" id="highsearch" class="btn2" value="高级搜索▼" onclick="toggle()">
	</div>
	<div class="fl hide" id="searchbtn">
			<input type="button" class="btn2" type="button" value="搜索" onclick="searchdata(1);toggle();"> 
			<input type="button" class="btn2" value="清空搜索条件" onclick="resetsearch()">
	</div>
	<div class="fr">
	<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
	</div>
	</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchdata(1);toggle();">
<div class="search-box">
<div class="s-box"><div class="title">开始日期</div>
<div class="text">
<input type="text" name="smindate" id="smindate" placeholder="<输入>" style="width: 162px;height:29px" class="easyui-datebox">
</div>
</div>
<div class="s-box"><div class="title">结束日期</div>
<div class="text">
<input type="text" name="smaxdate" id="smaxdate" placeholder="<输入>" style="width: 162px;height:29px" class="easyui-datebox">
</div>
</div>
<div class="s-box"><div class="title">交易号</div>
<div class="text">
<input class="hig25 wid160" type="text" name="snoteno" id="snoteno" placeholder="<输入>" >
</div>
</div>
<div class="s-box"><div class="title">业务单号</div>
<div class="text">
<input class="hig25 wid160" type="text" name="sywnoteno" id="sywnoteno" placeholder="<输入>" >
</div>
</div>
<div class="s-box"><div class="title">支付方式</div>
<div class="text">
<select id="spayfs" class="sele1">
<option value="9" selected>所有</option>
<option value="0">支付宝</option>
<option value="1">微信</option>
<!-- <option value="2">拉卡拉</option> -->
<option value="3">盛付通</option>
</select>
</div>
</div>
<div class="s-box"><div class="title">业务来源</div>
<div class="text">
<select id="sywtag" class="sele1">
<option value="9" selected>所有</option>
<option value="0">店铺零售</option>
<option value="1">商场零售</option>
<option value="2">线上销售</option>
<option value="3">批发销售</option>
</select>
</div>
</div>
<div class="s-box"><div class="title">交易状态</div>
<div class="text">
<select id="sstatetag" class="sele1">
<option value="3" selected>所有</option>
<option value="0">待付</option>
<option value="1">已付/已退</option>
<option value="2">取消</option>
</select>
</div>
</div>
<div class="s-box"><div class="title">摘要</div>
<div class="text">
<input class="hig25 wid160" type="text" name="sremark" id="sremark" placeholder="<输入>" >
</div>
</div>
<div class="s-box"><div class="title">支付流水</div>
<div class="text">
<input class="hig25 wid160" type="text" name="spayidstr" id="spayidstr" placeholder="<输入>" >
</div>
</div>
</div>
</div>
	</div>
	<table id="gg" style="overflow: hidden;"></table>
	<!-- 分页 -->
	<div
		style="width: 100%; height: 30px; border: 1px #d7d7d7 solid; background-brand: white; margin-top: 10px;">
		<div
			class="page-total">
			共有<span id="pp_total">0</span>条记录
		</div>
		<div id="pp" class="tcdPageCode page-code"></div>
	</div>
<script type="text/javascript">

setqxpublic();
var searchb = false;
var pgid = "pg1906";
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
function resertsearch(){
	var date = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue',date.Format('yyyy-MM-dd'));
	$('#progname,#sremark,#slatop').val('');
}
$(function(){
	resertsearch();
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
						sort: "NOTENO",
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
									field : 'NOTENO',
									title : '交易号',
									width : 120,
									fixed : true,
									sortable: true,
									halign : 'center',
									align : 'center'
								},{
									field : 'NOTEDATE',
									title : '操作日期',
									width : 120,
									fixed : true,
									sortable: true,
									halign : 'center',
									align : 'center'
								},{
									field : 'NOTETYPE',
									title : 'NOTETYPE',
									width : 100,
									fixed : true,
									sortable: true,
									hidden: true,
									halign : 'center',
									align : 'center'
								},{
									field : 'YWTAG',
									title : 'YWTAG',
									width : 100,
									fixed : true,
									sortable: true,
									hidden: true,
									halign : 'center',
									align : 'center'
								},{
									field : 'YWNAME',
									title : '业务来源',
									width : 100,
									fixed : true,
									sortable: true,
									halign : 'center',
									align : 'center',
									formatter: function(value, row,index){
										var ywtag = Number(row.YWTAG);
										var ywname = "";
										switch (ywtag) {
										case 0:
											ywname = "店铺零售";
											break;
										case 1:
											ywname = "商场零售";
											break;
										case 2:
											ywname = "线上销售";
											break;
										case 3:
											ywname = "批发销售";
											break;
										default:
											break;
										}
										return ywname;
									}
								},{
									field : 'YWNOTENO',
									title : '业务单据号',
									width : 120,
									fixed : true,
									sortable: true,
									halign : 'center',
									align : 'center'
								},{
									field : 'TOTALCURR',
									title : '应结金额',
									fieldtype: "N",
									width : 80,
									fixed : true,
									sortable: true,
									halign : 'center',
									align : 'right',
									styler: zeroCellStyle,
									formatter: currfm
								},{
									field : 'CURR',
									title : '支付金额',
									fieldtype: "N",
									width : 80,
									fixed : true,
									sortable: true,
									halign : 'center',
									align : 'right',
									styler: zeroCellStyle,
									formatter: currfm
								},{
									field : 'PAYFS',
									title : 'PAYFS',
									width : 80,
									fixed : true,
									sortable: true,
									hidden: true,
									halign : 'center',
									align : 'center'
								},{
									field : 'PAYNAME',
									title : '支付方式',
									width : 80,
									fixed : true,
									sortable: true,
									halign : 'center',
									align : 'center',
									formatter: function(value,row,index){
										var payfs = Number(row.PAYFS);
										var payname = "";
										
										switch (payfs) {
										case 0:
											payname = "支付宝";
											break;
										case 1:
											payname = "微信";
											break;
										case 2:
											payname = "拉卡拉";
											break;
										case 3:
											payname = "盛付通";
											break;

										default:
											break;
										}
										return payname;
									}
								},{
									field : 'STATETAG',
									title : 'STATETAG',
									width : 80,
									fixed : true,
									sortable: true,
									hidden: true,
									halign : 'center',
									align : 'center'
								},{
									field : 'STGNAME',
									title : '支付状态',
									width : 80,
									fixed : true,
									sortable: true,
									halign : 'center',
									align : 'center',
									formatter: function(value,row,index){
										var stg = Number(row.STATETAG);
										var stgname = "";
										var curr = Number(row.CURR);
										switch (stg) {
										case 0:
											stgname = curr>0?"待付":"待退";
											break;
										case 1:
											stgname = curr>0?"已付":"已退";
											break;
										case 2:
											stgname = "取消";
											break;

										default:
											break;
										}
										return stgname;
									}
								},{
									field : 'REMARK',
									title : '摘要',	
									width : 250,
									fixed : true,
									halign : 'center',
									align : 'left'
								},{
									field : 'FAILEDREMARK',
									title : '失败原因',	
									width : 100,
									fixed : true,
									halign : 'center',
									align : 'center'
								},{
									field : 'PAYACCNO',
									title : '支付账号',	
									width : 100,
									fixed : true,
									halign : 'center',
									align : 'center'
								},{
									field : 'OUTTRADENO',
									title : '商户流水',
									width : 250,
									fixed : true,
									halign : 'center',
									align : 'center'
								},{
									field : 'PAYIDSTR',
									title : '支付流水号',
									width : 250,
									fixed : true,
									halign : 'center',
									align : 'center'
								},{
									field : 'LASTOP',
									title : '制单人',
									width : 80,
									fixed : true,
									halign : 'center',
									align : 'center'
								}] ],
						onDblClickRow:function(rowIndex, rowData){
				   			var pgno = rowData.NOTETYPE;
				   			if(pgno!=''&&pgno!=undefined){
					   			var noteno = rowData.YWNOTENO;
					   			opendetaild(pgno,noteno);
				   			}
						},
						onSortColumn: function(sort, order){
							if(sort=="STGNAME")
								sort = "STATETAG";
							if(sort=="PAYNAME")
								sort = "PAYFS";
							if(sort=="YWNAME")
								sort = "YWTAG";
							$('#gg').datagrid('options').sort= sort;
							$('#gg').datagrid('options').order = order;
							$('#pp').refreshPage();
						},
						pageSize : 20,
						toolbar : '#toolbars'
					}).datagrid("keyCtr");
}
var currentdata = {};
function getdata(page){
	opser = "get";
	var findbox = $('#qsfindbox').val();
	var sort = $('#gg').datagrid('options').sort;
	var order = $('#gg').datagrid('options').order;
	alertmsg(6);
	currentdata = {
			erpser : "listdealrecord",
			findbox : findbox,
			sort : sort,
			order : order,
			rows: pagecount,
			accid: getValuebyName('ACCID'),
			fieldlist: "a.id,a.noteno,a.notedate,a.ywnoteno,a.outtradeno,a.ywtag,a.payfs,a.curr,a.statetag,a.remark,a.failedremark,a.payidstr,a.payaccno,a.accid,b.accname,a.totalcurr,a.lastop",
			page : page
		};
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : currentdata, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					$('#pp_total').html(data.total);
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

function searchdata(page){
	opser = "search";
	var sort = $('#gg').datagrid('options').sort;
	var order = $('#gg').datagrid('options').order;
	var mindate = $('#smindate').datebox('getValue');
	var maxdate = $('#smaxdate').datebox('getValue');
	var noteno = $('#snoteno').val();
	var ywnoteno = $('#sywnoteno').val();
	var payfs = $('#spayfs').val();
	var ywtag = $('#sywtag').val();
	var remark = $('#sremark').val();
	var statetag = $('#sstatetag').val();
	var payidstr = $('#spayidstr').val();
	alertmsg(6);
	currentdata = {
			erpser : "listdealrecord",
			sort : sort,
			order : order,
			mindate: mindate,
			maxdate: maxdate,
			noteno: noteno,
			ywnoteno: ywnoteno,
			payfs: payfs,
			ywtag: ywtag,
			statetag: statetag,
			remark: remark,
			payidstr: payidstr,
			rows: pagecount,
			accid: getValuebyName('ACCID'),
			fieldlist: "a.id,a.noteno,a.notedate,a.ywnoteno,a.outtradeno,a.ywtag,a.payfs,a.curr,a.statetag,a.remark,a.failedremark,a.payidstr,a.payaccno,a.accid,b.accname,a.totalcurr,a.lastop",
			page : page
		};
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : currentdata, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					$('#pp_total').html(data.total);
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
<jsp:include page="../HelpList/NoteDetail.jsp"></jsp:include>
</body>
</html>