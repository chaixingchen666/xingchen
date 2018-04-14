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
	width: 500px;
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
<div id="hzdialog" title="查询方式" style="width: 700px;max-height: 100%" class="easyui-dialog" closed=true>
<div class="skylistbox" id="hzdivbox">
<table class="table1">
<tr>
<td align="left">
<div class="skylistbox_toolbar">
<label class="label_hzcheck label_mx" ><input type="radio" name="skycheckbj" class="skyhzcheckbj" id="skymxcheckbj" checked>明细</label>
<label class="label_hzcheck label_hz"><input type="radio" name="skycheckbj" class="skyhzcheckbj" id="skyhzcheckbj">汇总</label>
</div>
</td>
</tr>
<tr>
<td class="list">
<div class="skydlistbox_box">
<ul class="skylistbox_ul" id="hzlistbox" data-ishz="false">
</ul>
</div>
</td>
</tr>
</table>
<div class="dialog-footer" style="text-align: center;display: block;position: relative;" id="hzdialogfooter">
<label class="label_showhxcm hide"><input type="checkbox" id="showhxcm">尺码横向展示</label>
<label class="label_showprice hide"><input type="checkbox" id="showprice">显示均价</label>
<input type="button" class="btn2 selectAll" value="全选" onclick="$('#hzdivbox').skyListBox('selectAllhz');">
<input type="button" class="btn2 clearAll" value="取消全选" onclick="$('#hzdivbox').skyListBox('unSelectAllhz');">
<span><input type="button" class="btn1" onclick="selectzh()" value="确定"></span>
</div>


<!-- <div class="skydlistbox_box" > -->
<!-- <div class="skydlistbox_header"> -->
<!-- <div class="skydlistbox_title">显示排序</div> -->
<!-- </div> -->
<!-- <div class="skylistbox_toolbar"> -->
<!-- <input type="button" class="btn2 selectAll" value="全选"> -->
<!-- <input type="button" class="btn2 clearAll" value="取消全选"> -->
<!-- </div> -->
<!-- <ul class="skylistbox_ul" id="showlistbox"> -->
<!-- </ul> -->
<!-- </div> -->
</div>
</div>
<script type="text/javascript" src="/skydesk/js/movelist.js" ></script>
<script type="text/javascript">
var defaulthzfs = {};
var DEFHZFS = {};
$(function(){
	var resjson = gethzjson(pgid);
	DEFHZFS = {
		showhxcm: resjson.showhxcm==undefined?-1:resjson.showhxcm,
		showprice: resjson.showprice==undefined?-1:resjson.showprice,
		onlyhz: resjson.onlyhz==undefined?false:resjson.onlyhz,
	};
	defaulthzfs = {
			ishz: resjson.ishz,
			showhxcm: resjson.showhxcm==undefined?-1:resjson.showhxcm,
			showprice: resjson.showprice==undefined?-1:resjson.showprice,
			onlyhz: resjson.onlyhz==undefined?false:resjson.onlyhz,
			hzfs: resjson.hzfs,
			hzsj: resjson.hzsj
	}
	var hzlist = resjson["columns"];
	$('#hzdivbox').skyListBox({
		hzlistbox: '#hzlistbox',
		showlistbox: '#showlistbox',
		checkbox: true,
		orderRadio: true,
		hzlist: hzlist,
		showlist: [{field: "WARENO",title: "货号"},{field: "WARENAME",title: "商品名称"},{field: "AOUNT",title: "数量"}]
	});
	var _defhzfs = getparamx({
		pgid: pgid.replace("pg",""),
		usymbol: "defaulthzfs"
	}).replace(/\'/g,"\"");
	if(_defhzfs!=""){
		defaulthzfs = $.extend({},defaulthzfs,$.parseJSON(decodeURI(_defhzfs)));
	}
	if(resjson.showhxcm==-1) defaulthzfs.showhxcm=-1;
	var $hzbox = $('#hzlistbox');
	if(defaulthzfs.ishz==true){
		$('#skymxcheckbj').removeProp('checked');		
		$('#skyhzcheckbj').prop('checked',true);
		$hzbox.data('ishz',true);
		$hzbox.find('li.only_hz').show();
		$hzbox.find('li.no_hz').removeClass('checked').hide();
		$hzbox.find('li.no_hz input[type=checkbox]').removeProp('checked');
		if(defaulthzfs.showhxcm>-1){
			$('.label_showhxcm').show();
			$('#showhxcm').prop('checked',defaulthzfs.showhxcm==1?true:false);
			if(defaulthzfs.showhxcm==1){
				var $hzbox = $('#hzlistbox');
				$hzbox.find('li:contains("尺码")').addClass('checked');
				$hzbox.find('li:contains("尺码") input[type=checkbox]').prop('checked',true);
				$hzbox.find('li:contains("尺码") input[type=checkbox]').prop('disabled',true);
				$hzbox.find('li:contains("尺码")').addClass('disabled_hz');
			}
		}
		if(defaulthzfs.showprice>-1 && allowinsale==1){
			$('.label_showprice').show();
			$('#showprice').prop('checked',defaulthzfs.showprice==1?true:false);
		}
		if(defaulthzfs.onlyhz==true){
			$(".skylistbox_toolbar label.label_mx").hide();
		}
	}else{
		$hzbox.find('li.only_hz').hide();
	}
	if(defaulthzfs.hzfs.length>0){
		var hzfs = defaulthzfs.hzfs;
		for(var i=0;i<hzfs.length;i++){
			var li = $hzbox.find("li:contains('"+hzfs[i]+"')");
			$(li).each(function(){
				var $li = $(this);
				var hzlitext = $li.find("div.skylistbox_left").text();
				if(hzfs[i]==hzlitext){
					$li.addClass('checked');
					$li.find('input[type=checkbox]').prop("checked",true);
				}
			});
		}
	}
	if(defaulthzfs.hzsj.length>0){
		var hzsj = defaulthzfs.hzsj;
		for(var i=0;i<hzsj.length;i++){
			var li = $hzbox.find("li:contains('"+hzsj[i]+"')");
			$(li).each(function(){
				var $li = $(this);
				var hzlitext = $li.find("div.skylistbox_left").text();
				if(hzsj[i]==hzlitext){
					$li.addClass('checked');
					$li.find('input[type=checkbox]').prop("checked",true);
				}
			});
		}
	}
	selectzh();
	$('input.skyhzcheckbj').change(function(){
		var $this = $(this);
		var checked = $this.prop('checked');
		var id = $this.attr('id');
		if(id=="skyhzcheckbj"&&checked){
			$hzbox.data('ishz',true);
			$hzbox.find('li.only_hz').show();
			$hzbox.find('li.no_hz').removeClass('checked').hide();
			$hzbox.find('li.no_hz input[type=checkbox]').removeProp('checked');
			if(resjson.showhxcm>-1) $('.label_showhxcm').show();
				if($('#showhxcm').prop("checked")){
					$hzbox.find('li:contains("尺码")').addClass('checked');
					$hzbox.find('li:contains("尺码") input[type=checkbox]').prop('checked',true);
					$hzbox.find('li:contains("尺码") input[type=checkbox]').prop('disabled',true);
					$hzbox.find('li:contains("尺码")').addClass('disabled_hz');
				}
		}else{
			$hzbox.data('ishz',false);
			$hzbox.find('li.no_hz').show();
			$hzbox.find('li.only_hz').removeClass('checked').hide();
			$hzbox.find('li.only_hz input[type=checkbox]').removeProp('checked');
			if(resjson.showhxcm>-1) $('.label_showhxcm').hide();
			//明细时候不禁止选择
			$hzbox.find('li:contains("尺码") input[type=checkbox]').removeProp('disabled');
			$hzbox.find('li:contains("尺码")').removeClass('disabled_hz');
		}
	});
	$('#showhxcm').change(function(){
		var $this = $(this);
		var checked = $this.prop('checked');
		if(resjson.showhxcm>-1&&$("#skyhzcheckbj").prop("checked")){
			if(checked){
				$hzbox.find('li:contains("尺码")').addClass('checked');
				$hzbox.find('li:contains("尺码") input[type=checkbox]').prop('checked',true);
				$hzbox.find('li:contains("尺码") input[type=checkbox]').prop('disabled',true);
				$hzbox.find('li:contains("尺码")').addClass('disabled_hz');
			}else{
				$hzbox.find('li:contains("尺码") input[type=checkbox]').removeProp('disabled');
				$hzbox.find('li:contains("尺码")').removeClass('disabled_hz');
			}
		}
	});
});
var selectzhfs = function(){
	$('#hzdialog').dialog('open');
}
//获取汇总方式
var gethzjson = function(progid){
	var resjson = null;
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		async: false,
		url : "/skydesk/jsonfiles/collection.json", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) {
			resjson = data[progid];
		}
	});
	return resjson;
}
var rcolumns = [{
	field : 'ROWNUMBER',
	title : '序号',
	width : 40,
	fixed:true,
	align:'center',
	halign:'center',
	formatter:function(value,row,index){
		var val = Number(value);
		if(value==undefined)
			return "";
		else if(isNaN(val))
			return value;
		return val;
	}
}];
var sizenum = getValuebyName('SIZENUM');
var cols = Number(sizenum)<=5?5: Number(sizenum);
function getsizecolum(){
// 	var sizenum = getValuebyName('SIZENUM');
// 	var cols = Number(sizenum)<=5?5: Number(sizenum);
	var colums = [];
	for (var i = 1; i <= cols; i++) {
		colums.push({
			field : 'AMOUNT' + i,
			title : '<span id="title'+i+'"><span>',
			width : 40,
			fixed: true,
			expable: true,
			fieldtype: "G",
			halign : 'center',
			align :'center',
			formatter : function(value,row,index){
				var val = Number(value);
				var j = Number(this.field.replace("AMOUNT", ""));
				var sizename = row["SIZENAME"+j];
				var r = row["ROWNUMBER"];
				if(sizename==""||r=="合计")
					return "";
				if(val==0||isNaN(val))
					return '0';
				return val;
			},
			styler: function(value,row,index){
				var val = Number(value);
				var j = Number(this.field.replace("AMOUNT", ""));
				var sizename = row["SIZENAME"+j];
				var r = row["ROWNUMBER"];
				if(sizename==""||r=="合计")
					return "";
				if(val==0||isNaN(val))
					return "color:#aaa;"
			}
		});
	}
	return colums;
}
function selectzh(){
	var $box = $('#hzdivbox');
	var $dg = $('#gg');
	var showhxcm = $('#showhxcm').prop("checked")?1:0;
	var showprice = $('#showprice').prop("checked")?1:0;
	var ishz = true;
	if($('#skymxcheckbj').prop('checked'))
		ishz = false;
	var hzname = $box.skyListBox('gethzname');
	var hzsjname = $box.skyListBox('gethzsjname');
	if(ishz==false&&defaulthzfs.showhxcm>=0) showhxcm=0;
	var columns = $box.skyListBox('gethzcolumns',showhxcm);
	var datalist = $box.skyListBox('gethzlist',ishz,showhxcm);
	if(DEFHZFS.showhxcm==-1)
		showhxcm = -1;
	if(DEFHZFS.showprice==-1)
		showprice = -1;
	defaulthzfs =  {
			ishz: ishz,
			showhxcm: showhxcm,
			showprice: showprice,
			hzfs: hzname.split("+"),
			hzsj: hzsjname.split("+")
		}
	setparamx({
		pgid: pgid.replace("pg",""),
		usymbol: "defaulthzfs",
		uvalue: encodeURI(JSON.stringify(defaulthzfs))
	});
	if(hzname==""){
		alerttext("请选择汇总项目");
		$('#span_hzlist').val("点击选择");
		return;
	}else if(hzsjname==""&&DEFHZFS.onlyhz!=true){
		alerttext("请选择汇总数据");
		$('#span_hzlist').val("点击选择");
		return;
	}else if(validehzsj(ishz,hzname,hzsjname)){
		if(ishz){
			$('#span_hzlist').val("汇总查询");
		}else{
			$('#span_hzlist').val("明细查询");
		}
		$('#shzlist').data("hzlist",datalist);
		var sortName = columns[0].field;
		if(columns[0].field=="IMAGENAME0")
			sortName = columns[1].field;
		if(DEFHZFS.onlyhz==true&&(pgid!="pg1504"&&pgid!="pg1717")){
			columns.splice(0, 0, rcolumns[0]);  
			setdatagrid($dg,sortName,columns);
		}else{
			sortlist = sortName +" asc";
			$dg.datagrid({
// 				sortName: sortName,
// 				sortOrder: "asc",
				sortName: "",
				sortOrder: "",
				multiSort: true,//允许多列排序
				frozenColumns: [rcolumns],
				columns: [columns]
			}).datagrid('resize');
		}
		$dg.datagrid('loadData',nulldata);
	}else{
		return;
	}
	$('#hzdialog').dialog('close');
// 	searchdatas(); 
}
</script>