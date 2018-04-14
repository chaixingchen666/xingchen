<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/skydesk/css/demo.min.css" type="text/css" />

</head>
<body>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js" ></script>
<script type="text/javascript" src="/skydesk/js/movelist.js" ></script>
<script>
var leftobj = {
		WARENO: "货号",
		WARENAME: "商品名称",
		PRICE: "零售价",
		CURR: "零售额"
};
var rightobj = {
		HOUSENAME: "店铺",
		AMOUT: "数量"
};
var hzfsopt = {
	ser: null,
	key: null,
	leftobj: {},
	rightobj: {}
}
$(document).ready(function(){
	$("#hzfsbox").movelist({
		boxl:"#hzfsbox .box_l",//左边大盒子
		boxr:"#hzfsbox .box_r",//右边大盒子
		boxlrX:"li",//移动小盒子
		boxon:"on",//点击添加属性
		idclass:true,//添加的属性是否为class//true=class; false=id;
		boxlan:"#hzfsbox #left",//单个向左移动按钮
		boxran:"#hzfsbox #right",//单个向右移动按钮
		boxtan:"#hzfsbox #top",//单个向上移动按钮
		boxban:"#hzfsbox #bottom",//单个向下移动按钮
		boxalllan:"#hzfsbox #allleft",//批量向左移动按钮
		boxallran:"#hzfsbox #allright",//批量向右移动按钮
		boxalltan:"#hzfsbox #alltop",//移动第一个按钮
		boxallban:"#hzfsbox #allbottom",//移动最后一个按钮,
		leftobj: leftobj,
		rightobj: rightobj
	});
});
var selecthzfsd = function selecthzfsd(obj){
	hzfsopt = $.extend(true,obj,hzfsopt);
	var ser = hzfsopt.ser;
	var key = hzfsopt.key;
	var name = hzfsopt.name;
	var leftobj = hzfsopt.leftobj;
	var rightobj = hzfsopt.rightobj;
	$('#hzfsbox').movelist("resetbox",leftobj,rightobj);
	$('#hzfs_dialog').dialog('open');
};
var selecthzfs = function(){
	var selobj = $('#hzfsbox').movelist('getrobj');
	var keystr = "";
	var valstr = "";
	for(var i in selobj){
		keystr += i + ",";
		valstr += selobj[i] + ",";
	}
	alert(keystr + "\r\n" + valstr);
};
</script>
<div id="hzfs_dialog" class="easyui-dialog" data-options="modal:true" style="width:700px;height:470px">
<div class="box" id="hzfsbox">
	<div class="box_l">
	<ul class="box_ul">
	</ul>
	</div>
	<div class="box_m">
		<input type="button" class="btn1" id="top" value="上移" />
		<input type="button" class="btn1" id="allleft" value="全部左移" />
		<input type="button" class="btn1" id="left" value="向左" />
		<input type="button" class="btn1" id="right" value="向右" />
		<input type="button" class="btn1" id="allright" value="全部右移" />
		<input type="button" class="btn1" id="bottom" value="下移" />
	</div>
	<div class="box_r">
		<ul class="box_ul">
		</ul>
	</div>
</div>
<div class="dialog-footer">
	<input class="btn1" type="button" type="button" value="确认" onclick="selecthzfs();">
<!-- 	<input class="btn1" type="button" type="button" value="取消" onclick="$('#hzzfs_dialog').dialog('close');"> -->
</div>
</div>
</body>
</html>