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
<title>销售年度计划</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<style type="text/css">
input.editcurr{
	width:70px;
	line-height: 24px;
	text-align: right;
	border: none;
	margin-right: 3px;
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
<body class="easyui-layout layout panel-noscroll">
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
<table>
<tr>
<td>
<select class="sele1" id="syearnum" onchange="loadsaleplan()" style="width:80px">
</select>
年度销售目标总计划（单位：万元）
</td>
<td>
</td>
</tr>
</table>
</div>
</div>
<table id="gg">
</table>
<div class="page-bar">
		<div class="page-total">
		已显示<span id="show_total">0</span>个店铺/共有<span id="pp_total">0</span>个店铺
		</div>
	</div>
<script type="text/javascript">
var pgid="pg1615";
var levelid = getValuebyName("GLEVELID"); 
setqxpublic();
function setyearnum(){
	var date = new Date;
	var year = Number(date.Format("yyyy"))-5;
	if(!isNaN(year)){
		for(var i=0;i<10;i++){
			if(i==5)
			$("#syearnum").append('<option value="'+year+'" selected>'+year+'</option>');
			else 
			$("#syearnum").append('<option value="'+year+'">'+year+'</option>');
			year++;
		}
	}
}
var monthcols = [ [{
	field : 'SEASON1',
	title : '第一季度',
	width : 240,
	colspan: 3,
	align : 'center',
	halign : 'center'
}, {
	field : 'SEASON2',
	title : '第二季度',
	width : 240,
	colspan: 3,
	align : 'center',
	halign : 'center'
}, {
	field : 'SEASON3',
	title : '第三季度',
	width : 240,
	colspan: 3,
	align : 'center',
	halign : 'center'
}, {
	field : 'SEASON4',
	title : '第四季度',
	width : 240,
	colspan: 3,
	align : 'center',
	halign : 'center'
},  {
	field : 'ACTION',
	title : '操作',
	rowspan: 2,
	fixed: true,
	width : 80,
	align : 'center',
	halign : 'center',
	formatter: function(value,row,index){
		if(index==0||index>1){
			return '<input type="button" value="保存" class="m-btn action-'+index+'-save hide" onclick="saveplan('+index+')">';
		}else return "";
	}
}],[]];
function editcurrfm(value,row,index){
	var field = this.field;
	if(index!=1)
		return '<input type="text" class="editcurr editindex-'+index+'" id="editcurr-'+index+'-'+field+'" placeholder="0" data-o_val="'+Number(value).toFixed(2)+'" value="'+Number(value).toFixed(2)+'">';
	return Number(value).toFixed(2);
}
function edittotalcurrfm(value,row,index){
	if(index!=1)
		return '<input type="text" class="editcurr editindex-'+index+'" id="editcurr-'+index+'-CURR" placeholder="0" data-o_val="'+Number(value).toFixed(2)+'" value="'+Number(value).toFixed(2)+'">';
	return Number(value).toFixed(2);
}
function setmonthcol(){
	var monthcol1 = monthcols[1];
	var titlearr = ["一","二","三","四","五","六","七","八","九","十","十一","十二"];
	for(var i=1;i<=12;i++){
		monthcol1.push({
			field : 'CURR'+i,
			title : titlearr[i-1]+"月",
			fixed: true,
			width : 80,
			align : 'right',
			halign : 'center',
			formatter: editcurrfm
		});
	}
	return monthcols;
}
$(function(){
	setyearnum();
	$("#gg").datagrid({   
		height: $('body').height() - 50,
		fitColumns : true,
		striped : true, //隔行变色
		singleSelect : true,
		frozenColumns:  [[ {
			field : 'SEASONNAME',
			title : '季节',
			fixed: true,
			width : 160,
			align : 'center',
			halign : 'center'
		},{
			field : 'CURR',
			title : '合计',
			width : 100,
			fixed: true,
			rowspan: 2,
			align : 'right',
			halign : 'center',
			formatter: edittotalcurrfm
		}],[{
			field : 'HOUSENAME',
			title : '月份',
			width : 100,
			fixed: true,
			align : 'center',
			halign : 'center'
		}]],
		columns: setmonthcol(),
		rowStyler: function(index,row){
			if(index==1)
				return "background: yellow !important;font-weight:600"
		},
		onSelect: function(index,row){
			if(index==1){
				$("#gg").datagrid("unselectRow",index);
			}
		},
		toolbar: "#toolbars"
	});
	loadsaleplan();
	$("#gg").parent().delegate("input.editcurr","focus",function(e){
		$(e.target).select();
	}).delegate("input.editcurr","keyup",function(e){
		var $this = $(e.target);
		e.target.value = e.target.value.replace(/[^\d.]/g,"");
		var id = $this.attr("id");
		var idarr = id.split("-");
		var index = Number(idarr[1]);
		var currid = idarr[2];
		var $dg = $("#gg");
		var val = e.target.value;
		var rows = $dg.datagrid("getRows");
		var row = rows[index];
		var rowhj = rows[1];
		var o_val = Number($this.data("o_val"));
		$this.data("o_val",val);
		if(index>1){
			var yfphj = rowhj[currid];//最初列表是的合计
			yfphj = Number(yfphj) - Number(o_val) + Number(val);
			rowhj[currid] = yfphj;
		}
		if(currid=="CURR"){
			var valpj = (val/12).toFixed(2);
			for(var i=1;i<=12;i++){
				var o_val1 = $("#editcurr-"+index+"-CURR"+i).val();
				var rval = 0;
				if(i==12)
					rval = Number(val - Number(valpj)*11).toFixed(2);
				else
					rval = valpj;
				$("#editcurr-"+index+"-CURR"+i).val(rval);
				$("#editcurr-"+index+"-CURR"+i).data("0_val",rval);
				if(index>1){
					var yfphj = rowhj["CURR"+i];
					yfphj = Number(yfphj)  - Number(o_val1) + Number(rval);
					rowhj["CURR"+i] = yfphj;
				}
			}
		}else{
			var hjcurr = Number($("#editcurr-"+index+"-CURR").val()) - Number(o_val) + Number(val);
			$("#editcurr-"+index+"-CURR").val(hjcurr.toFixed(2));
			$("#editcurr-"+index+"-CURR").data("0_val",hjcurr);
			if(index>1){
				var yfphj = rowhj["CURR"];
				yfphj = Number(yfphj)  - Number(o_val) + Number(val);
				rowhj["CURR"] = yfphj;
			}
		}
		$dg.datagrid("updateRow",{
			index: 1,
			row: rowhj
		});
		$("input.m-btn.action-"+index+"-save").show();
	}).delegate("input.editcurr","change",function(e){
		var val = Number($(e.target).val());
		$(e.target).val(val.toFixed(2));
	});
	$("#gg").prev().children('.datagrid-body').scroll(function(e) {
		var $this = $(this);
		var viewH = $(this).height(); //可见高度
		var contentH = $(this).get(0).scrollHeight; //内容高度
		var scrollTop = $(this).scrollTop(); //滚动高度
		if (contentH > (viewH + 1) && contentH - viewH - scrollTop <= 3 && $("#gg").datagrid("getRows").length > 0) { //到达底部100px时,加载新内容
			if (sumpg > pg && nextpg) {
				houseplanlist(pg + 1);
			}
		}
	});
});
function loadsaleplan(){
	var yearnum = $("#syearnum").val();
	alertmsg(6);
	var $dg = $("#gg");
	$dg.datagrid("loadData",{total:0,rows:[]});
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fybuyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		dataType : 'json',
		data: {
			erpser: "loadsaleplan",
			yearnum: yearnum
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		success : function(data) { //回调函数，result，返回值 
			try{
				if (valideData(data)) {
					data.HOUSENAME = "计划任务";
					$dg.datagrid("insertRow",{
						index: 0,
						row: data
					});
					houseplanlist(1);
				}
			}catch(e){
				console.log(e);
			}
		}
	});
}
var sumpg = 1;
var pg = 1;
var nextpg = true;
var footerrow = {};
function houseplanlist(page){
	alertmsg(6);
	nextpg = false;
	var $dg = $("#gg");
	var yearnum = $("#syearnum").val();
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fybuyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		dataType : 'json',
		data: {
			erpser: "houseplanlist",
			yearnum: yearnum,
			rows: pagecount,
			page: page
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		success : function(data) { //回调函数，result，返回值 
			try{
				if (valideData(data)) {
					if(data.total==0) return;
					footerrow = {HOUSENAME:"已分配"};
					for(var i=0;i<=12;i++){
						if(i==0)
							footerrow["CURR"] = data["totalcurr"];
						else
							footerrow["CURR"+i] = data["totalcurr"+i];
					}
					if(page==1)
						$dg.datagrid("appendRow",$.extend({},footerrow));
					sumpg = Math.ceil(data.total/pagecount);
					pg = page;
					var rows = data.rows;
					for(var i=0;i<rows.length;i++){
						var row = rows[i];
						$dg.datagrid("appendRow",row);
					}
					nextpg = true;
					$("#show_total").html($dg.datagrid("getRows").length-2);
					$("#pp_total").html(data.total);
				}
			}catch(e){
				console.log(e);
				nextpg = true;
			}
		}
	});
}
function saveplan(index){
	var yearnum = $("#syearnum").val();
	var $dg = $("#gg");
	var row = $dg.datagrid("getRows")[index];
	var houseid = row.HOUSEID;
	var ajaxdata = {
 			yearnum: yearnum
 	};
	if(index==0){
		ajaxdata.erpser = "savesaleplan";
	}else{
		ajaxdata.erpser = "savehouseplan";
		ajaxdata.houseid = houseid;
	}
 	for(var i = 0;i<=12;i++){
 		if(i==0) ajaxdata["curr"] = $("#editcurr-"+index+"-CURR").val();
 		else ajaxdata["curr"+i] = $("#editcurr-"+index+"-CURR"+i).val();
 	}
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fybuyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		dataType : 'json',
		data: ajaxdata, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		success : function(data) { //回调函数，result，返回值 
			try{
				if (valideData(data)) {
					$("input.m-btn.action-"+index+"-save").hide();
				}
			}catch(e){
				console.log(e);
			}
		}
	});
}
</script>
</body>
</html>