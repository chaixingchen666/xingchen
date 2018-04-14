<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">
.skylistbox{
	font-family: "微软雅黑";
	font-size: 14px;
	color: #323232;
	text-align: center;
}
/* 新建规则 */
.skylistbox_left {
    float: left;
    margin: 0 5px 0 0;
}

/* 新建规则 */
.skylistbox_right {
    float: right;
    width: 55px;
    line-height: 10px;
    text-align: center;
}
/* 新建规则 */
.skylistbox_right label {
    font-size: 12px;
}
/* 新建规则 */
.skylistbox_ul {
    list-style: none;
    padding: 0;
    margin: 0 25px;
	overflow: hidden;
	zoom: 1;
}
.skydlistbox_box {
    float: left; 
/*     border: 1px solid #00959a; */
    padding: 5px;
    margin: 5px; 
/*     min-height: 350px; */
/*     max-height: 450px; */
    overflow: auto;
}
.skylistbox .table1 td.list{
	border: 1px solid #ddd;
	vertical-align: top;
	background: #f7f8fa;
}
.skydlistbox_header{
/* 	background: #0091a2; */
	width: 100%;
	overflow: hidden;
}
.skydlistbox_header .skydlistbox_title{
	float:left;
/* 	color: #fff; */
	font-size: 18px;
	padding:10px;
}
.skydlistbox_box .skylistbox_toolbar{
	background: #fff;
	margin: 5px;
}

/* 新建规则 */
.skylistbox_ul li {
    overflow: hidden;
    width: 125px;
    float: left;
    height: 40px;
    line-height: 40px;
    border-bottom: solid 1px #ddd;
}
.skylistbox_ul li .actived,.skylistbox_ul li:hover{
    color: #fff;
    background: #ff7900;
}
/* 新建规则 */
.skylistbox_ul li:last-child {
    border-bottom: none;
}
.skydlistbox_box .label_hzcheck {
    padding: 5px;
    color: #ff7900;
}
</style>
<div id="coldialog" title="设置" style="width: 700px;max-height: 100%" class="easyui-dialog" closed=true>
<div class="skylistbox" id="coldivbox">
<div class="skydlistbox_box">
<ul class="skylistbox_ul" id="collistbox">
</ul>
</div>
<div class="dialog-normal-footer" style="text-align: center;display: block;position: relative;" id="coldialogfooter">
<input type="button" class="btn2 reset" value="恢复默认" onclick="$('#coldivbox').skyColumnBox('reset');">
<input type="button" class="btn2 selectAll" value="全选" onclick="$('#coldivbox').skyColumnBox('selectAll');">
<input type="button" class="btn2 clearAll" value="取消全选" onclick="$('#coldivbox').skyColumnBox('unSelectAll');">
<span><input type="button" class="btn1" onclick="selectcol()" value="确定"></span>
</div>
</div>
</div>
<script type="text/javascript" src="/skydesk/js/columnset.js" ></script>
<script type="text/javascript">
//获取列数据json
var defaultcolumnjson = [];
var DEFAULTCOLSET = {
		showcolarray: null,
		fieldlist: ""
};
var showcolset = {};
var getcoljson = function(pgidtable){
	var resjson = null;
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		async: false,
		url : "/skydesk/jsonfiles/pgcolumns.json", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) {
			resjson = data[pgidtable];
		}
	});
	return resjson;
}
var tableid = "";
function getpgcolumnarray(column){
	if($("#settingdiv").length>0){
		$("#coldivbox").before($("#settingdiv"));
	}
	tableid = "#gg";
	defaultcolumnjson = getcoljson(pgid+tableid);
	DEFAULTCOLSET.showcolarray = [].concat(defaultcolumnjson.defaultcolumn);
	DEFAULTCOLSET.fieldlist = defaultcolumnjson.mustfieldli+defaultcolumnjson.defaultfieldlist;
	$("#coldivbox").skyColumnBox({
		columnbox: "#collistbox",
		collist: defaultcolumnjson.columns
	});
	var _defcolset = getparamx({
		pgid: pgid + tableid,
		usymbol: "showcolset"
	});
	if(_defcolset.length!=0&&_defcolset.indexOf(defaultcolumnjson.mustfieldli)>=0){
		showcolset = $.extend({},DEFAULTCOLSET,$.parseJSON(decodeURI(_defcolset)));
	}else{
		showcolset = $.extend({},DEFAULTCOLSET,{});
	}
	var array = showcolset.showcolarray;
	for(var i in column){
		var col = column[i];
		var field = col.field;
		if(field.indexOf("ACTION")>=0) continue;
		if($.inArray(field,array)>=0){
			col.hidden = false;
		}else{
			col.hidden = true;
		}
	}
	return column;
}
function opensetcolumnd(){
	var $columnbox = $('#collistbox');
	$columnbox.find("li").each(function(){
		var $li = $(this);
		var field = $li.data("field");
		if($.inArray(field,showcolset.showcolarray)>-1){
			$li.addClass('checked');
			$li.find('input[type=checkbox]').prop("checked",true);
		}
	});
	$("#coldialog").dialog("open");
}
function selectcol(){
	var $box = $('#coldivbox');
	var $dg = $(tableid);
	showcolset = {
		showcolarray: $box.skyColumnBox("getcheckfields"),
		fieldlist: defaultcolumnjson.mustfieldli+$box.skyColumnBox("getcheckfieldlist")
	};
	if(showcolset.showcolarray.length==0){
		alerttext("选择项目不能为空！");
		return;
	}
	setparamx({
		pgid: pgid + tableid,
		usymbol: "showcolset",
		uvalue: encodeURI(JSON.stringify(showcolset))
	});
	var array = showcolset.showcolarray;
	var option = $dg.datagrid("options");
	var column = option.columns[0];
	for(var i in column){
		var col = column[i];
		var field = col.field;
		if(field.indexOf("ACTION")>=0) continue;
		if($.inArray(field,array)>=0){
			col.hidden= false;
		}else{
			col.hidden= true;
		}
	}
// 	option.sortName = array[0];
    $dg.datagrid();
    if (pgid === 'pg1511') {
        //对进销存分析进行特殊处理（因店铺是否区分与数据生成对sql有关）
	    searchdata();
    } else {
        $("#pp").refreshPage();
    }
    //$dg.datagrid();
    //$("#pp").refreshPage();

	$("#coldialog").dialog("close");
}
</script>