<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js" ></script>
<script type="text/javascript" src="/skydesk/js/movelist.js" ></script>
<title>Insert title here</title>
<style type="text/css">
.skylistbox{
	font-family: "微软雅黑";
	font-size: 14px;
	color: #00959a;
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
    width: 230px;
    height: 245px;
    overflow: auto;
}
.skydlistbox_box {
    float: left;
    border: 1px solid #00959a;
    padding: 5px;
    margin: 5px;
    background: #f7f8fa;
}
.skydlistbox_header{
	background: #0091a2;
	width: 100%;
	overflow: hidden;
}
.skydlistbox_header .skydlistbox_title{
	float:left;
	color: #fff;
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
    height: 40px;
    line-height: 40px;
    border-bottom: solid 1px #00959a;
}
.skylistbox_ul li .actived,.skylistbox_ul li:hover{
    color: #fff;
    background: #ff7900;
}
/* 新建规则 */
.skylistbox_ul li:last-child {
    border-bottom: none;
}

</style>
<script type="text/javascript">
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
$(function(){
	var resjson = gethzjson("pg1105");
// 	console.error(resjson);
	var hzlist = resjson["columns"];
// 	console.error(hzlist);
	$('#hzdivbox').skyListBox({
		hzlistbox: '#hzlistbox',
		showlistbox: '#showlistbox',
		checkbox: true,
		orderRadio: true,
		hzlist: hzlist,
		showlist: [{field: "WARENO",title: "货号"},{field: "WARENAME",title: "商品名称"},{field: "AOUNT",title: "数量"}],
	});
});
</script>
</head>
<body>
<div class="skylistbox" id="hzdivbox">
<div class="skydlistbox_box">
<div class="skydlistbox_header">
<div class="skydlistbox_title">组合查询</div>
</div>
<div class="skylistbox_toolbar">
<input type="button" class="btn2 selectAll" value="全选">
<input type="button" class="btn2 clearAll" value="取消全选">
</div>
<ul class="skylistbox_ul" id="hzlistbox">

</ul>
<div>
<label><input type="checkbox" class="skyhzcheckbj">数据汇总</label>
</div>
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
</body>
</html>