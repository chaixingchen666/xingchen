<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="panel-fit">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0"> 
<meta name="renderer" content="webkit|ie-comp|ie-stand" /> 
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>账本管理</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<style type="text/css">
.accbooklist {
/*     position: absolute; */
/*     bottom: 0; */
/*     top: 0; */
/*      min-width: 300px; */
}

.listbookdetail {
/*     position: absolute; */
/*     top: 0; */
/*     bottom: 0; */
/*     left: 310px; */
/*     right: 0; */
}
.toolbar{
	padding:10px;
}

div.bookemplist {
	max-height: 250px;
	overflow: auto;
	margin: 0 13px 10px 13px;
}
.fr.bookhousecheck {
    margin-right: 4px;
}
.bookhouse {border-bottom: 1px dashed;overflow: hidden;zoom: 1;padding: 5px;margin-right: 1px;}
.bookhousename{
	font-weight: 600;
}
ul.bookempul {
    overflow: hidden;
    zoom: 1;
    background: #ddd;
}

li.bookempli label{
	display: flow-root;
}
li.bookempli,.bookhousecheck{
 	cursor: pointer;
}
li.bookempli {
    float: left;
    width: 165px;
    padding: 5px;
}

li.bookempli:hover,.bookhousecheck:hover{
	color:#ff7900;
}
span.bookempname,span.bookempcheck {
    float: left;
}
.datagrid-view.nodata{background:url(/skydesk/images/accbook/zb_emptyTick.png) no-repeat center center}
a.roundback {
   	padding: 10px;
    display: inline-block;
    border-radius: 10px;
    margin: 0 3px;
    vertical-align: middle;
}
li.bookcolorli {
    float: left;
    border-width: 1px;
    padding: 2px;
    border-radius: 12px;
    text-align: center;
    cursor: pointer;
}
#ubookcolor li.selected{
	border-style: solid;
}
li.bookcolorli a.roundback {
	margin: 0;
}
label#listbooktitle {
    font-size: 20px;
    color: #23b9cb;
    font-weight: 600;
    margin-right: 10px;
}
</style>
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
</head>
<body>
<div id="allpanel" class="easyui-layout" style="width:100%;height:100%;">   
<div data-options="region:'west',split:true" style="min-width:300px;max-width:420px;">
<!-- 账本列表 -->
<div class="accbooklist">
<div class="toolbar" id="accbooktoolbar">
<input type="button" class="btn1" value="增加" onclick="addbookd();">
<input type="button" class="btn1" value="修改" onclick="updatebookd();">
<input type="button" class="btn1" value="刷新" onclick="listaccbook();">
</div>
<table id="accbookt">
</table>
</div>

</div>   
<div data-options="region:'center'">
<!-- 账本登账 -->
<div class="listbookdetail">
<div class="toolbar" id="listbooktoolbar" style="padding:8px 10px;">
<label id="listbooktitle">请选择账本</label>
日期范围：
<input id="smindate" type="text" class="easyui-datebox" style="width:120px;" />
至
<input id="smaxdate" type="text" class="easyui-datebox" style="width:120px;" />
<input type="button" class="btn1" value="查询" onclick="listbookdetail(1)">
<input type="button" class="btn1" value="记账" onclick="addbookdetaild()">
<input type="button" class="btn1" value="转账" onclick="addtobookdetaild()">
<input type="button" class="btn1" value="修改" onclick="updatebookdetaild()">
<input type="button" class="btn1" value="计算" onclick="calcaccbook()">
 <input type="button" class="btn1" value="导出" onclick="fyexportxls('#listbookpp_total','#listbookt',currentdata,'buy',0,exceltitle);">
</div>
<table id="listbookt">
</table>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total">
	共有<span id="listbookpp_total">0</span>条记录
</div>
<div id="listbookpp" class="tcdPageCode page-code"></div>
</div>
</div>

</div>   
</div>  

<!-- 账本管理 -->
<div id="ubookd" class="easyui-dialog" style="width:600px;max-width: 100%;max-height: 100%;padding:10px;" closed="true">
<input type="hidden" id="ubookid" value="0" />
<input type="hidden" id="uindex" value="0" />
<table class="table1" style="width:580px" cellspacing="10">
<tr>
<td class="skyrequied" align="right" width="60">
账本名称
</td>
<td align="left" width="200">
<input type="text" class="wid160 hig25" placeholder="<输入>" id="ubookname"/>
</td>
<td class="skyrequied" align="right" width="60">
建账日期
</td>
<td align="left">
<input type="text" class="easyui-datebox" style="width:162px;height:26px" id="unotedate"/>
</td>
</tr>
<tr>
<td class="skyrequied" align="right">
账本颜色
</td>
<td align="left" colspan="3">
<ul class="bookcolor" id="ubookcolor" >
</ul>
</td>
</tr>
<tr>
<td class="skyrequied" align="right">
期初余额
</td>
<td align="left">
<input type="text" class="wid160 hig25" placeholder="<输入>" id="ufirstcurr"/>
</td>
<td class="skyrequied" align="right">
账本状态
</td>
<td align="left">
<select class="sele1" id="ustatetag">
<option value="0">关闭</option>
<option value="1">开启</option>
</select>
</td>
</tr>
</table>
<div style="margin-left: 13px;" class="skyrequied">
授权用户
</div>
<div class="bookemplist" id="bookemplist">
<div class="bookhouseemp">
<div class="bookhouse">
<div class="fl bookhousename">
杭州雨贝尔店
</div>
<div class="fr">
<input type="checkbox" class="bookempallcheck" />
</div>
</div>
<div class="bookemplist">
<ul class="bookempul">
<li class="bookempli">
<span class="bookempname">
张三（老板）
</span>
<span class="bookempcheck">
<input type="checkbox" />
</span>
</li>
</ul>
</div>
</div>
</div>
<div class="dialog-normal-footer">
		<input type="button" class="btn1"  value="保存" onclick="saveaccbook()">
		<input type="button" class="btn2" value="取消" onclick="$('#ubookd').dialog('close')"> 
</div>
</div>

<div id="ubookdetaild" class="easyui-dialog" style="width:400px;max-height:100%; text-align: center;" closed="true">
<input type="hidden" id="udetailindex" value="">
<input type="hidden" id="udetailbookid" value="">
<input type="hidden" id="udetailid" value="">
<table class="table1" style="width:250px" cellspacing="10">
<tbody>
<tr>
<td class="header skyrequied" align="right" width="80">
日期
</td>
<td align="left">
<input type="text" class="easyui-datetimebox" style="width:162px;height:26px" id="unotedate1"/>
</td>
</tr>
<tr>
<td class="header skyrequied" align="right">
记账项目
</td>
<td align="left">
<input type="text" class="edithelpinput sub_help" id="usubname" placeholder="<输入或选择>"/>
<span onclick="selectsub('update')"></span>
<input type="hidden" id="usubid" value="">
<input type="hidden" id="usublx" value="">
</td>
</tr>
<tr>
<td class="header skyrequied" align="right">
方向
</td>
<td align="left">
<input type="text" class="wid160 hig25" id="usublxname" readonly/>
</td>
</tr>
<tr>
<td class="header skyrequied" align="right">
金额
</td>
<td align="left">
<input type="text" class="wid160 hig25" id="ucurr"  placeholder="<输入>"/>
</td>
</tr>
<tr>
<td class="header skyrequied" align="right">
摘要
</td>
<td align="left">
<input type="text" class="wid160 hig25" id="uremark"  placeholder="<输入>"/>
</td>
</tr>

</tbody>
</table>
<div class="dialog-normal-footer">
		<input type="button" class="btn1"  value="保存" onclick="savebookdetail()">
		<input type="button" class="btn2" value="取消" onclick="$('#ubookdetaild').dialog('close')"> 
</div>
</div>

<div id="utobookd" title="增加" class="easyui-dialog" style="width:400px;max-height:100%; text-align: center;" closed="true">
<table class="table1" style="width:250px" cellspacing="10">
<tbody>
<tr>
<td class="header skyrequied" align="right" width="80">
日期
</td>
<td align="left">
<input type="text" class="easyui-datetimebox" style="width:162px;height:26px" id="unotedate2"/>
</td>
</tr>
<tr>
<td class="header skyrequied" align="right">
转入账本
</td>
<td align="left">
<input type="text" class="edithelpinput book_help" id="utobookname" placeholder="<输入或选择>"/>
<span onclick="selectbook('tobook')"></span>
<input type="hidden" id="utobookid" value="">
</td>
</tr>
<tr>
<td class="header skyrequied" align="right">
金额
</td>
<td align="left">
<input type="text" class="wid160 hig25" id="utocurr"  placeholder="<输入>"/>
</td>
</tr>
<tr>
<td class="header skyrequied" align="right">
摘要
</td>
<td align="left">
<input type="text" class="wid160 hig25" id="utoremark"  placeholder="<输入>"/>
</td>
</tr>
</tbody>
</table>
<div class="dialog-normal-footer">
		<input type="button" class="btn1"  value="保存" onclick="addtobookdetail()">
		<input type="button" class="btn2" value="取消" onclick="$('#utobookd').dialog('close')"> 
</div>
</div>
<script type="text/javascript">
//权限设置
setqxpublic();
var pgid= "pg1907";
var levelid= getValuebyName("GLEVELID");
var exceltitle = "";
var bookcolorjson = ["#3C444E",
    "#8B572A",
    "#2BABEE",
    "#B8BD00",
    "#0091A2",
    "#E44856",
    "#D85146",
    "#FF885A",
    "#FF9934",
    "#F9C051",
    "#065381",
    "#0174B8",
    "#3E5EA1",
    "#00605E",
    "#52A642"];
$(function(){
	for(var i in bookcolorjson){
    	var color = bookcolorjson[i];
		$("#ubookcolor").append('<li class="bookcolorli '+(i==0?'selected':'')
				+'" data-value="'+color+'" style="border-color:'+color+'"><a class="roundback" style="background-color:'+color+'" /></li>');
    }
	$("#ubookcolor li").click(function(){
		$("#ubookcolor li.selected").removeClass("selected");
		$(this).addClass("selected");
	});
	$("#accbookt").datagrid({
		idField: 'BOOKID',
		height: $("#allpanel").height()-5,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		frozenColumns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 40,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if(value!="合计")
					return index+1;
				return value;
			}
		}]],
		columns: [[{
			field: 'BOOKID',
			title: '账本ID',
			fieldtype: "G",
			width: 200,
			hidden: true
		},{
			field: 'BOOKNAME',
			title: '账本名称',
			width: 110,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'left',
			formatter: function(value, row, index) {
				if(value){
					var stgtext = row.STATETAG==0?"(关闭)":"";
					return value+stgtext;
				}else return "";
			}
		},{
			field: 'BALCURR',
			title: '余额',
			width: 80,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'right',
			formatter: currfm
		},{
			field: 'NOTEDATE',
			title: '建账日期',
			width: 90,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'center',
			formatter: datefm
		},{
			field: 'ACTION',
			title: '操作',
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if(row.ROWNUMBER!="合计"){
					var html = '<input type="button" class="m-btn" value="删除" onclick="delaccbook(' + index + ')">';
					return html;
				}else return "";
			}
		}]],
		onDblClickRow: function(index, row) {
			var stg = Number(row.STATETAG);
			if(stg==0){
				alerttext("该账本已关闭,请去开启!");
				return;
			}
			var bookid = row.BOOKID;
			if(!row){
				alerttext("请选择有效账本");
				return;
			}
			$("#udetailbookid").val(bookid);
			exceltitle = "【"+row.BOOKNAME+"】明细账";
			$("#listbooktitle").html(row.BOOKNAME);
			$("#uindex").val(index);
			$("#smindate,#smaxdate").datebox("setValue",top.getservertime());
			listbookdetail(1);
		},
		onSelect: function(index,row){
			$("#uindex").val(index);
		},
		pageSize: 20,
		toolbar: '#accbooktoolbar'
	});
	$("#listbookpp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			listbookdetail(p);
		}
	});
	$("#listbookt").datagrid({
		idField: 'BOOKID',
		height: $("#allpanel").height()-50,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		frozenColumns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 40,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if(isNaN(Number(value))&&value!=undefined&&value.length>0)
					return value;
				var p = $("#listbookpp").getPage() - 1;
				if(row.SUBNAME!="期末")
					return p * pagecount + index + 1;
				return "";
			}
		}]],
		columns: [[{
			field: 'ID',
			title: '记账ID',
			fieldtype: "G",
			width: 200,
			hidden: true
		},{
			field: 'NOTEDATE',
			title: '日期',
			width: 90,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'center',
			formatter: datetimefm
		},{
			field: 'SUBNAME',
			title: '记账项目',
			width: 110,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'center'
		},{
			field: 'REMARK',
			title: '摘要',
			width: 150,
// 			sortable: true,
// 			fixed: true,
			halign: "center",
			align: 'left'
		},{
			field: 'YWNOTENO',
			title: '业务单号',
			width: 120,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'center'
		},{
			field: 'CURR0',
			title: '收入',
			width: 90,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'right',
			formatter: currfm2
		},{
			field: 'CURR1',
			title: '支出',
			width: 90,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'right',
			formatter: currfm2
		},{
			field: 'CURR2',
			title: '余额',
			width: 100,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'right',
			formatter: currfm2
		},{
			field: 'LASTOP',
			title: '记账人',
			width: 80,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'center'
		},{
			field: 'CHECKMAN',
			title: '审核人',
			width: 80,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'center'
		},{
			field: 'ACTION',
			title: '操作',
			width: 120,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var lastop = row.LASTOP;
				var subid = row.SUBID;
				var checkman = row.CHECKMAN;
				var stg = checkman==""?1:2;
				var epname = getValuebyName("EPNAME");
				var html = "";
				// 获取删除权限 只允许删除自己的 和 未审核的
				if(lastop==epname&&stg==1)
				html += '<input type="button" class="m-btn" value="删除" onclick="delbookdetail(' + index + ')">';
				// 获取审核权限   审核：1不允许审核自已的记录2系统管理员，财务，经理，老板允许审核
				if((lastop==epname||levelid==0||levelid==3||levelid==4||levelid==5)&&stg==1&&subid>0)
				html += '<input type="button" class="m-btn" value="审核" onclick="checkbookdetail(' + index + ',1)">';
				// 获取编辑权限 修改：自已的记录和未审核的  转账记录不允许编辑
				if(stg==2&&checkman==epname&&subid>0)
				html += '<input type="button" class="m-btn" value="取消审核" onclick="checkbookdetail(' + index + ',0)">';
				if(row.SUBNAME!="期末")
					return html;
				return "";
			}
		}]],
		rowStyler:function(index,row){
			if(row.SUBNAME=="期初"){
				return "color:#ff7900;font-weight:600";
			}
		},
		onDblClickRow: function(index, row) {
			if(row.CHECKMAN.length>0){
				alerttext("已审核的记账不允许修改");
				return;
			}
			updatebookdetaild();
		},
		onSelect: function(index,row){
			$("#udetailindex").val(index);
		},
		pageSize: 20,
		toolbar: '#listbooktoolbar'
	});
	listaccbook();
	$("#bookemplist").delegate(".bookempcheck :checkbox","change",function(){
		checkAllEmpCheck();
		$(this).addClass("changed");
	}).delegate(".bookhousecheck :checkbox","change",function(){
		var checked = $(this).prop("checked");
		var houseid = $(this).data("houseid");
		var $allcheck = $(".bookhouseemp.house"+houseid).find(".bookempcheck :checkbox");
		$allcheck.prop("checked",checked);
		$allcheck.addClass("changed");
	});
	/*$('#yearmonth').datebox({
		//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
		onShowPanel: function() {
			//触发click事件弹出月份层
			span.trigger('click');
			if (!tds)
			//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
			setTimeout(function() {
				tds = p.find('div.calendar-menu-month-inner td');
				tds.click(function(e) {
					//禁止冒泡执行easyui给月份绑定的事件
					e.stopPropagation();
					//得到年份
					var year = /\d{4}/.exec(span.html())[0],
					//月份
					//之前是这样的month = parseInt($(this).attr('abbr'), 10) + 1; 
					month = parseInt($(this).attr('abbr'), 10);

					//隐藏日期对象                     
					$('#yearmonth').datebox('hidePanel')
					//设置日期的值
					.datebox('setValue', year + '-' + month);
				});
			},
			0);
		},
		//配置parser，返回选择的日期
		parser: function(s) {
			if (!s) return new Date();
			var arr = s.split('-');
			return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
		},
		//配置formatter，只返回年月 之前是这样的d.getFullYear() + '-' +(d.getMonth()); 
		formatter: function(d) {
			var currentMonth = (d.getMonth() + 1);
			var currentMonthStr = currentMonth < 10 ? ('0' + currentMonth) : (currentMonth + '');
			return d.getFullYear() + '-' + currentMonthStr;
		}
	});

	//日期选择对象
	var p = $('#yearmonth').datebox('panel'),
	//日期选择对象中月份
	tds = false,
	//显示月份层的触发控件
	span = p.find('span.calendar-text');
	var curr_time = new Date();
	//设置前当月
	$("#yearmonth").datebox("setValue", getyearmonth(curr_time));
	*/
	$("div.accbooklist,div.listbookdetail").resize(function(e){
		$("#accbookt,#listbookt").datagrid("resize");
	});
	$("div.listbookdetail div.datagrid-view").addClass("nodata");
});
//格式化日期
function getyearmonth(date) {
	//获取年份
	var y = date.getFullYear();
	//获取月份
	var m = date.getMonth() + 1;
	return y + '-' + m;
}
function addbookd(){
	$("#ubookname,#ubookid,#uindex,#ufirstcurr").val("");
	$("#unotedate").datebox("setValue",top.getservertime());
	$("#ustatetag").val(0);
	$("#ubookcolor li.selected").removeClass("selected");
	$("#ubookcolor li").eq(0).addClass("selected");
	$("#ufirstcurr").removeProp("readonly");
	$('#unotedate').datebox({
		readonly: false,
		hasDownArrow: true
	});
	bookemployelist(1);
	$("#ubookd").dialog("setTitle","新建账本");
}
function updatebookd(){
	$("#ubookd").dialog("setTitle","编辑账本");
	getbookbyid();
}
function listaccbook(){
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "listaccbook",
			fieldlist: "bookid,bookname,balcurr,firstcurr,bookcolor,statetag,notedate",
			rows: 50,
			page: 1
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					data.footer = [{
						ROWNUMBER: "合计",
						BALCURR: data.totalbalcurr
					}];
					$("#accbookt").datagrid("loadData",data);
					
// 					$("#listbooktitle").html("请选择账本");
// 					listbookdetail(1);
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}
function bookemployelist(page){
	var bookid = Number($("#ubookid").val());
	if(page==1) $("#bookemplist").html("");
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "bookemployelist",
			bookid: bookid,
			rows: pagecount,
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var rows = data.rows;
					for(var i in rows){
						var row = rows[i];
						var housename = row.HOUSENAME;
						var houseid = Number(row.HOUSEID);
						var levelname = row.LEVELNAME;
						var selbj = row.SELBJ;
						var epid = Number(row.EPID);
						var epname = row.EPNAME;
						var $housediv;
						if($(".bookhouseemp.house"+houseid).length==0){
							$housediv = $('<div class="bookhouseemp house'+houseid+'"></div>')
							$housediv.append('<div class="bookhouse">'
									+'<div class="fl bookhousename">'
									+housename
									+'</div>'
									+'<div class="fr bookhousecheck">'
									+'<label><input type="checkbox" class="bookempallcheck"  data-houseid="'+houseid+'">全选</label>'
									+'</div>'
									+'</div>');
							$housediv.append('<div>'
									+'<ul class="bookempul">'
									+'<li class="bookempli">'
									+'<label>'
									+'<span class="bookempcheck">'
									+'<input type="checkbox" data-epid="'+epid+'" '+(selbj==1?'checked':'') +'>'
									+'</span>'
									+'<span class="bookempname">'
									+epname+'('+levelname+')'
									+'</span>'
									+'</label>'
									+'</li>'
									+'</ul>'
									+'</div>');
							$("#bookemplist").append($housediv);
						}else{
							$housediv = $(".bookhouseemp.house"+houseid);
							var $ul = $housediv.find("ul.bookempul");
							$ul.append('<li class="bookempli">'
									+'<label>'
									+'<span class="bookempcheck">'
									+'<input type="checkbox" data-epid="'+epid+'" '+(selbj==1?'checked':'') +'>'
									+'</span>'
									+'<span class="bookempname">'
									+epname+'('+levelname+')'
									+'</span>'
									+'</label>'
									+'</li>');
						}
					}
					checkAllEmpCheck();
					$("#ubookd").dialog("open");
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}
//检测是否全选。
function checkAllEmpCheck(){
	$("#bookemplist div.bookhouseemp").each(function(){
		var $this = $(this);
		var $allcheckbox = $this.find(".bookhousecheck :checkbox");
		var $bookempcheck = $this.find(".bookempcheck :checkbox");
		var $bookempchecked = $this.find(".bookempcheck :checkbox:checked");
		var checkboxlength = $bookempcheck.length;
		var checkedboxlength = $bookempchecked.length;
		if(checkboxlength==checkedboxlength&&checkboxlength>0) $allcheckbox.prop("checked",true);
		else $allcheckbox.removeProp("checked");
	});
}

function saveaccbook(){
	var bookid = Number($("#ubookid").val());
	var index = Number($("#uindex").val());
	var erpser = "addaccbook";
	if(bookid>0) erpser = "updateaccbook";
	var bookname = $("#ubookname").val();
	var firstcurr = $("#ufirstcurr").val();
	var notedate = $("#unotedate").datebox("getValue");
	var statetag = $("#ustatetag").val();
	var bookcolor = $("#ubookcolor li.selected").data("value").toUpperCase();
	var epidlist = [];
	$("#bookemplist .bookempcheck :checkbox.changed").each(function(){
		$this = $(this);
		var epid = $this.data("epid");
		var checked = $this.prop("checked");
		var epidobj = {epid:0,value:0};
		if(epid>0){
			epidobj.epid = epid;
			epidobj.value = checked?1:0;
			epidlist.push(epidobj);
		}
	});
	if(epidlist.length==0&&bookid==0){
		alerttext("请授权账户用户");
		return;
	}
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: erpser,
			bookid: bookid,
			bookname: bookname,
			firstcurr: firstcurr,
			notedate: notedate,
			statetag: statetag,
			bookcolor: bookcolor,
			epidlist: JSON.stringify(epidlist)
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				var $dg = $("#accbookt");
				if(bookid==0){
					$dg.datagrid("insertRow",{
						index: 0,
						row: {
						BOOKID: data.msg,
						BOOKNAME: bookname,
						FIRSTCURR: firstcurr,
						NOTEDATE: notedate,
						STATETAG: statetag,
						BOOKCOLOR: bookcolor
					}});
				}else{
					$dg.datagrid("updateRow",{
						index: index,
						row: {
						BOOKID: data.bookid,
						BOOKNAME: bookname,
						FIRSTCURR: firstcurr,
						NOTEDATE: notedate,
						STATETAG: statetag,
						BOOKCOLOR: bookcolor
					}});
				}
				$("#ubookd").dialog("close")
			}
		}
	});
}
function getbookbyid(){
	var index = $("#uindex").val();
	var row = $("#accbookt").datagrid("getRows")[index];
	if(row){
		var bookid = row.BOOKID;
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fybuyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "getaccbook",
				bookid: bookid,
				fieldlist: "bookid,bookname,balcurr,firstcurr,bookcolor,statetag,notedate",
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) {
				if (valideData(data)) {
					if(data.total>0){
						var row = data.rows[0];
						var bookname = row.BOOKNAME;
						var bookcolor = row.BOOKCOLOR.toUpperCase();
						var notedate = row.NOTEDATE;
						var firstcurr = Number(row.FIRSTCURR);
						var statetag = row.STATETAG;
						if(statetag==1){
							$("#ufirstcurr").prop("readonly",true);
							$('#unotedate').datebox({
								readonly: true,
								hasDownArrow: false
							});
						}else{
							$("#ufirstcurr").removeProp("readonly");
							$('#unotedate').datebox({
								readonly: false,
								hasDownArrow: true
							});
						}
						$("#ubookid").val(bookid);
						$("#ubookname").val(bookname);
						$("#unotedate").datebox("setValue",notedate);
						$("#ufirstcurr").val(firstcurr.toFixed(2));
						$("#ustatetag").val(statetag);
						$("#ubookcolor li.selected").removeClass("selected");
						$("#ubookcolor li[data-value="+bookcolor+"]").addClass("selected");
						bookemployelist(1);
					}
				}
			}
		});
	}
}
function delaccbook(index){
	var $dg = $("#accbookt");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var bookid = row.BOOKID;
		$.messager.confirm(dialog_title, '您确认要删除账本' + row.BOOKNAME + '？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fybuyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delaccbook",
						bookid: bookid
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						try {
							if (valideData(data)) {
								$dg.datagrid("deleteRow", index).datagrid("refresh");
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
var footerrow = {};
var currentdata = {};
//显示账本记账明细列表
function listbookdetail(page){
	var bookid = Number($("#udetailbookid").val());
	if(bookid==0||isNaN(bookid)){
		alerttext("请选择有效账本");
		return;
	}
	var mindate = $("#smindate").datebox("getValue");
	var maxdate = $("#smaxdate").datebox("getValue");
	currentdata =  {
			erpser: "listbookdetail",
			bookid: bookid,
			mindate: mindate,
			maxdate: maxdate,
			dayhj: page==1?2:0, //dayhj=1,返回每日收入currhj0，支出合计currhj1(手机)  （mindate,maxdate不传）	dayhj=2,返回期初firstcurr，期末lastcurr（电脑） 第一次查询时dayhj=2,翻页时dayhj=0（yearid,monthid不传）
			rows: pagecount,
			page: page
		};
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: currentdata,
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					if(data.total==0){
						$("div.listbookdetail div.datagrid-view").addClass("nodata");
					}else
						$("div.listbookdetail div.datagrid-view").removeClass("nodata");
					$("#listbookpp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					if(page==1){
						var firstrow = {
							ROWNUMBER:0,
							SUBNAME: "期初",
							CURR2: data.firstcurr
						};
						data.rows.unshift(firstrow);
					}
					footerrow = {
						SUBNAME: "期末",
						CURR0: data.totalcurr0,
						CURR1: data.totalcurr1,
						CURR2: data.lastcurr
					};
					data.footer = [footerrow];
					$("#listbookt").datagrid("loadData",data);
					$('#listbookpp_total').html(data.total);
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}
function addbookdetaild(){
	var bookid = Number($("#udetailbookid").val());
	if(bookid==0){
		alerttext("请选择需要记账账本");
		return;
	}
	$("#unotedate1").datetimebox("setValue",top.getservertime());
	$("#usubid,#udetailid,#usubname,#ucurr,#uremark,#sublx,#usublxname").val("");
	$("#ubookdetaild").dialog("setTitle","新建账目明细").dialog("open");
}
function addtobookdetaild(){
	var bookid = Number($("#udetailbookid").val());
	if(bookid==0){
		alerttext("请选择需要转账账本");
		return;
	}
	$("#unotedate2").datetimebox("setValue",top.getservertime());
	$("#udetailid,#utobookname,#utocurr,#utoremark").val("");
	$("#utobookd").dialog("setTitle","新建转账").dialog("open");
}
function updatebookdetaild(){
	var index = Number($("#udetailindex").val());
	var $dg = $("#listbookt");
	var editrow = $dg.datagrid("getRows")[index];
	if(!editrow){
		alerttext("请选择有效记账记录！");
		return;
	}
	if(getValuebyName("EPNAME")!=editrow.LASTOP){
		alerttext("不是自己的记录不允许操作！");
		return;
	}
	if(editrow.SUBID>0){
		$("#unotedate1").datetimebox("setValue",editrow.NOTEDATE);
		$("#udetailid").val(editrow.ID);
		$("#usubid").val(editrow.SUBID);
		$("#usubname").val(editrow.SUBNAME);
		var curr = editrow.CURR0>0?editrow.CURR0:editrow.CURR1;
		var sublx = editrow.CURR0>0?1:2;
		var usublxname = editrow.CURR0>0?"收入":"支出";
		$("#ucurr").val(Number(curr).toFixed(2));
		$("#uremark").val(editrow.REMARK);
		$("#usublx").val(sublx);
		$("#usublxname").val(usublxname);
		$("#ubookdetaild").dialog("setTitle","编辑账目明细").dialog("open");
	}else {
		alerttext("已转账本，不允许编辑！");
	}
}
function savebookdetail(){
	var bookid = Number($("#udetailbookid").val());
	var id = Number($("#udetailid").val());
	var subid = Number($("#usubid").val());
	var sublx = Number($("#usublx").val());
	var index = Number($("#udetailindex").val());
	var erpser = "addbookdetail";
	if(id>0) erpser = "updatebookdetail";
	var remark = $("#uremark").val();
	var curr = Number($("#ucurr").val());
	var notedate = $("#unotedate1").datetimebox("getValue");
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: erpser,
			bookid: bookid,
			id: id,
			subid: subid,
			curr: curr,
			notedate: notedate,
			remark: remark
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				/*var $dg = $("#listbookt");
				var editrow = $dg.datagrid("getRows")[index];
				var row = {
						SUBID: subid,
						SUBNAME: $("#usubname").val(),
						REMARK: remark,
						CURR2: data.balcurr,
						NOTEDATE: notedate
				};
				var curr0 = new Decimal(footerrow.CURR0);
				var curr1 = new Decimal(footerrow.CURR1);
				if(sublx==1){
					if(id==0){
						curr0 = curr0.add(curr);
					}else{
						curr0 = curr0.sub(editrow.CURR0).add(curr);
					}
					row["CURR0"] = curr;
				}
				if(sublx==2){
					if(id==0){
						curr1 = curr0.add(curr);
					}else{
						curr1 = curr1.sub(editrow.CURR1).add(curr);
					}
					row["CURR1"] = curr;
				}
				footerrow.CURR0 = curr0.valueOf();
				footerrow.CURR1 = curr1.valueOf();
				row["LASTOP"] = getValuebyName("EPNAME");
				if(id==0){
					row.ID = data.msg;
					row.ROWNUMBER = 1;
					$dg.datagrid("insertRow",{
						index: 0,
						row: row
						});
					$("#listbookpp_total").html(Number($("#listbookpp_total").html())+1);
				}else{
					$dg.datagrid("updateRow",{
						index: index,
						row: row
						});
					
				}
				$dg.datagrid("refresh");
				$dg.datagrid("reloadFooter");*/
				$("#listbookpp").refreshPage();
				$("#ubookdetaild").dialog("close")
			}
		}
	});
}

function delbookdetail(index){
	var $dg = $("#listbookt");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var id = row.ID;
		var bookid = Number($("#udetailbookid").val());
		$.messager.confirm(dialog_title, '您确认要删除账目明细？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fybuyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delbookdetail",
						bookid: bookid,
						id: id
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						try {
							if (valideData(data)) {
								var curr0 = new Decimal(footerrow.CURR0);
								var curr1 = new Decimal(footerrow.CURR1);
								curr0 = curr0.sub(row.CURR0);
								curr1 = curr1.sub(row.CURR1);
								footerrow.CURR0 = curr0.valueOf();
								footerrow.CURR1 = curr1.valueOf();
								$dg.datagrid("deleteRow", index).datagrid("refresh").datagrid("reloadFooter");
								$("#listbookpp_total").html(Number($("#listbookpp_total").html())-1);
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
function checkbookdetail(index,opid){
	var $dg = $("#listbookt");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var checkman = row.CHECKMAN;
		var id = row.ID;
		var bookid = Number($("#udetailbookid").val());
// 		var msg = '确定进行审核？';
// 		if(opid==0)
// 			msg = "确定取消审核么？";
// 		$.messager.confirm(dialog_title, msg,
// 		function(r) {
// 			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fybuyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "checkbookdetail",
						bookid: bookid,
						id: id
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						try {
							if (valideData(data)) {
								$dg.datagrid("updateRow",{
									index: index,
									row: {
										CHECKMAN: checkman==""?getValuebyName("EPNAME"):""
									}
								}).datagrid("refreshRow",index);
							}
						} catch(e) {
							// TODO: handle exception
							console.error(e);
							top.wrtiejsErrorLog(e);
						}
					}
				});
// 			}
// 		});
	}
}
function addtobookdetail(){
	var bookid = Number($("#udetailbookid").val());
	var tobookid = Number($("#utobookid").val());
	var erpser = "addtobookdetail";
	var remark = $("#utoremark").val();
	var curr = Number($("#utocurr").val());
	var notedate = $("#unotedate2").datetimebox("getValue");
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: erpser,
			bookid: bookid,
			tobookid: tobookid,
			curr: curr,
			notedate: notedate,
			remark: remark
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				$("#listbookpp").refreshPage();
				$("#utobookd").dialog("close")
			}
		}
	});
}
function calcaccbook(){
	var notedate = $("#smindate").datebox("getValue");
	var bookid = Number($("#udetailbookid").val());
	if(bookid==0){
		alerttext("请选择需要计算账本");
		return;
	}
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "calcaccbook",
			bookid: bookid,
			notedate: notedate
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				$("#listbookpp").refreshPage();
			}
		}
	});
}
</script>
<jsp:include page="../HelpList/SubHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/BookHelpList.jsp"></jsp:include>
</body>
</html>