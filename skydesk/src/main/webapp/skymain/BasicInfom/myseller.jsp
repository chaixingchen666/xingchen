<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">
<meta name="renderer" content="webkit|ie-comp|ie-stand" /> 
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>我的供应商</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
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
	<input class="btn1" type="button" id="addnotebtn" value="添加" onclick="addgysd()">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updategysd()">
	<a class="btn1" href="javascript:void(0)" id="showsqmsg" onclick="showgyssqd()">查看申请</a>
	<input type="text" placeholder="搜索账号、供应商名称<回车搜索>" data-end="yes" class="ipt1" id="qsvalue" maxlength="20" data-enter="getsellerlist('1')">
	</div>
</div>
</div>
<table id="gysdg"></table>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total" id="notetotal">共有0条记录</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>
<!-- 申请框 -->
<div id="ad" title="增加供应商" data-options="modal:true" style="width: 400px; height: 300px;" class="easyui-dialog" closed="true">
<div class="textcenter" style="margin-top: 30px;">
<table cellspacing="5" class="table1" border="0" class="table1">
<tr><td>企业账号</td><td><input type="text" class="wid160 hig25 uppercase" id="gysaccno" placeholder="<输入>"></td></tr>
<tr><td>申请内容</td><td><textarea class="wid160" rows="5" id="sqtext" placeholder="<输入>"></textarea></td></tr>
</table>
</div>
<div class="dialog-footer textcenter">
<input type="button" class="btn1" value="确定" onclick="sendsq()">
</div>
</div>
<!-- 编辑框 -->
<div id="ud" title="单位信息" data-options="modal:true" style="width: 550px; height: 400px;" class="easyui-dialog" closed="true">
<div class="textcenter">
<input type="hidden" id="usellaccid">
<input type="hidden" id="uprovid" >
<input type="hidden" id="uid" >
<table width="394" border="0" cellspacing="10" cellpadding="10" class="table1">
  <tr>
    <td width="110" align="right">账户名称</td>
    <td width="264" align="left"><input type="text" class="wid160 hig25" id="ugysno" readonly></td>
  </tr>
  <tr>
    <td align="right">单位名称</td>
    <td align="left"><input type="text" class="wid160 hig25" id="ugysname" readonly></td>
  </tr>
  <tr>
    <td align="right">联系人</td>
    <td align="left"><input type="text" class="wid160 hig25" id="ulinkman" readonly></td>
  </tr>
  <tr>
    <td align="right">移动电话</td>
    <td align="left"><input type="text" class="wid160 hig25" id="umobile" readonly></td>
  </tr>
  <tr>
    <td align="right">供应商</td>
    <td align="left"><input type="text" class="edithelpinput" id="uprovname" readonly><span onclick="selectprov('updatesell')"></span></td>
  </tr>
</table>
</div>
<div class="dialog-footer textcenter">
<input type="button" class="btn1" value="数据同步" onclick="$('#tbd').dialog('open');">
</div>
</div>
<!-- 消息框 -->
<div id="tbd" title="选择同步" data-options="modal:true" style="width: 200px; height: 200px;" class="easyui-dialog" closed="true">
<div style="margin: 30px auto;width:165px">
<select id="fs" class="sele1">
<option value="0">同步商品资料</option> 
<option value="1">同步价格</option> 
<option value="2">同步商品图片</option> 
<!-- <option value="3">处理默认颜色及尺码</option>  -->
<option value="4">所有</option> 
</select>
</div>
<div class="dialog-footer">
<input type="button" class="btn1" value="开始同步" onclick="downsellerparams()" >
</div>
</div>
<!-- 消息框 -->
<div id="gysmsgd" title="供应商申请" data-options="modal:true" style="width: 750px; height: 400px;" class="easyui-dialog" closed="true">
<table id="gysmsgdg"></table>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total" id="msgtotal">已显示0条记录/共有0条记录</div>
	<div id="msgpp" class="tcdPageCode page-code">
	</div>
</div>
</div>
<!-- 申请信息框 -->
<div id="umsgd" title="申请信息" data-options="modal:true" style="width: 550px; height: 400px;" class="easyui-dialog" closed="true">
<div class="textcenter">
<input type="hidden" id="umsellaccid">
<table width="394" border="0" cellspacing="10" cellpadding="10" class="tebel1">
  <tr>
    <td width="110" align="right">账户名称</td>
    <td width="264" align="left"><input type="text" class="wid160 hig25" id="umgysno" readonly></td>
  </tr>
  <tr>
    <td align="right">单位名称</td>
    <td align="left"><input type="text" class="wid160 hig25" id="umgysname" readonly></td>
  </tr>
  <tr>
    <td align="right">联系人</td>
    <td align="left"><input type="text" class="wid160 hig25" id="umlinkman" readonly></td>
  </tr>
  <tr>
    <td align="right">移动电话</td>
    <td align="left"><input type="text" class="wid160 hig25" id="ummobile" readonly></td>
  </tr>
  <tr>
    <td align="right">申请信息</td>
    <td align="left"><input type="text" class="wid160 hig25" id="umrequest" readonly></td>
  </tr>
  <tr>
    <td align="right">是否拒绝</td>
    <td align="left"><label><input type="radio" id="umagree" name="umpass" checked>同意</label>
    <label><input type="radio" id="umreject" name="umpass">拒绝</label></td>
  </tr>
  <tr>
    <td align="right">拒绝原因</td>
    <td align="left"><textarea id="umrejtext" rows="3" class="wid160" placeholder="<输入>"></textarea></td>
  </tr>
</table>
</div>
<div class="dialog-footer textcenter">
<input type="button" class="btn1" value="保存并继续" onclick="addmyseller()" >
</div>
</div>
<!-- 供应商帮助列表 -->
<jsp:include page="../HelpList/ProvHelpList.jsp"></jsp:include>
<script type="text/javascript">
	var levelid = getValuebyName("GLEVELID");
	setqxpublic();
//当浏览器窗口大小改变时，设置显示内容的高度  
	window.onresize=function(){  
	     changeDivHeight();  
	}  
function changeDivHeight(){               
	var height = document.documentElement.clientHeight;//获取页面可见高度 
	$('#gg').datagrid('resize',{
		height:height-45
	});
}
$(function(){
	if(top.sellernum>0)
		$('#showsqmsg').addClass('newmsg');
	$("#sqtext").text("我是"+getValuebyName("COMID"));
	$("#pp").createPage({
		pageCount : 1,
		current : 1,
		backFn: function(p){
			getsellerlist(p);
		}
	});
	$('#gysdg').datagrid({
		idField : 'gysdg',
		height : $('body').height() - 50,
		fitColumns : true,
		nowrap : false,
		striped : true, //隔行变色
		singleSelect : true,
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
					formatter : function(value, row,index) {
						var val = Math.ceil(Number(value)/ pagecount)-1;
						return val*pagecount+Number(index)+1;
					}
				},
				{
					field : 'ACCNAME',
					title : '供应商账号',
					width : 100,
					fixed : true,
					halign : 'center',
					align : 'center'
				},
				{
					field : 'COMPANY',
					title : '单位名称',
					width : 150,
					fixed : true,
					halign : 'center',
					align : 'center'
				},
				{
					field : 'LINKMAN',
					title : '联系人',
					width : 110,
					fixed : true,
					halign : 'center',
					align : 'center'
				},
				{
					field : 'MOBILE',
					title : '移动电话',
					width : 110,
					fixed : true,
					halign : 'center',
					align : 'center'
				},
				{
					field : 'TEL',
					title : '联系电话',
					width : 110,
					fixed : true,
					halign : 'center',
					align : 'center'		
				},
				{
					field : 'PROVNAME',
					title : '供应商名称',
					width : 150,
					fixed : true,
					halign : 'center',
					align : 'center'
				},
				{
					field : 'ACTION',
					title : '操作',
					width : 80,
					fixed: true,
					halign : 'center',
					align: 'center',
					formatter : function(value, row,index) {
						return '<input type="button" class="m-btn" value="删除" onclick="delmysellerbyid('+index+')"';
					}
				} ] ],
		onClickRow :function(index,row){
			
		},
		onDblClickRow : function(rowIndex, rowData) {
			updategysd();
		},
		pageSize : 20,
		toolbar : '#toolbars'
	}).datagrid("keyCtr","updategysd()");
	$('#gysmsgdg').datagrid({
		idField : 'gysmsgdg',
		height : 300,
		nowrap : false,
		fitColumns : true,
		striped : true, //隔行变色
		singleSelect : true,
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
					formatter : function(value, row,index) {
						var val = Math.ceil(Number(value)/ pagecount)-1;
						return val*pagecount+Number(index)+1;
					}
				},
				{
					field : 'SELLACCID',
					title : '供应商ID',
					width : 200,
					hidden : true					
				},
				{
					field : 'SELLACCNAME',
					title : '供应商账号',
					width : 200,
					halign : 'center',
					align : 'center'
				},
				{
					field : 'APPLYDATE',
					title : '申请日期',
					width : 200,
					halign : 'center',
					formatter:function(value,row,index){
						return value.substring(0,16);
					}
				},
				{
					field : 'COMPANY',
					title : '单位信息',
					width : 200,
					halign : 'center'
				},
				{
					field : 'LINKMAN',
					title : '联系人',
					width : 200,
					halign : 'center'
				},
				{
					field : 'MOBILE',
					title : '联系电话',
					width : 200,
					halign : 'center'
				},
				{
					field : 'REQUEST',
					title : '申请理由',
					width : 200,
					halign : 'center'
				},
				{
					field : 'TAG',
					title : '状态',
					width : 200,
					hidden : true					
				} ] ],
		onClickRow :function(index,row){
			
		},
		onDblClickRow : function(rowIndex, rowData) {
			updategyssqd();
		},
		pageSize : 20,
		toolbar : ''
	}).datagrid("keyCtr","updategyssqd()");
	getsellerlist("1");
});
function getsellerlist(page){
	alertmsg(6);
	var findbox = $('#qsvalue').val();
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "mysellerlist",
			findbox: findbox,
			rows: pagecount,
			page : page
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			if(valideData(data)){
				$("#pp").setPage({
					pageCount : Math.ceil(data.total / pagecount),
					current : Number(page)
				});
				$('#gysdg').datagrid('loadData', data);
				$('#notetotal').html('共有'+data.total+'条记录');
			}
		}
	});
}
function addgysd(){
	$('#ad :text').val("");
	$('#ad').dialog('open');
}
function sendsq(){
	var sellaccname = $('#gysaccno').val();
	var request = $('#sqtext').val();
	request==""?("我是"+getValuebyName("COMID")):request;
	if(sellaccname==""){
		alerttext("企业账号不能为空！");
		$('#gysaccno').focus();
	}else{
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data : {
				erpser : "addmyselleracclink",
				sellaccname : sellaccname,
				request : request
			}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType : 'json',
			success : function(data) { //回调函数，result，返回值 
				if(valideData(data)){
					$('#ad').dialog('close');
					alerttext('发送申请成功！');
				}
			}
		});
	}
}
function updategysd(){
	var data = $('#gysdg').datagrid("getSelected");
	$('#ud :text').val("");
	if(data){
		$('#uid').val(data.ID);
		$('#usellaccid').val(data.ACCID);
		$('#ugysno').val(data.ACCNAME);
		$('#ugysname').val(data.COMPANY);
		$('#ulinkman').val(data.LINKMAN);
		$('#umobile').val(data.MOBILE);
		$('#utel').val(data.TEL);
		$('#uprovname').val(data.PROVNAME);
		$('#uprovid').val(data.PROVID);
		$('#ud').dialog('open');
	}else{
		alerttext("请选择一条数据编辑！");
	}
}
function changeseller(provid){
	alertmsg(2);
	var id = $('#uid').val();
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "updatemysellerbyid",
			id : id,
			buyprovid : provid
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			if(valideData(data)){
				alerttext("操作完成！");
				getsellerlist("1");
			}
		}
	});
}
function delmysellerbyid(index){
	var msg= "是否确定删除该供应商";
	var row = $("#gysdg").datagrid("getRows")[index];
	$.messager.confirm(dialog_title,msg,function(r){ 
		if(r){
			alertmsg(2);
			var id = $('#uid').val();
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser : "delmysellerbyid",
					id : row.ID
				}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType : 'json',
				success : function(data) { //回调函数，result，返回值 
					if(valideData(data)){
						alerttext("操作完成！");
						getsellerlist("1");
						$('#ud').dialog('close');
					}
				}
			});
		}
	});
}

function getmysellersqlist(page){
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "myselleracclinklist",
			rows: pagecount,
			page : page
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			if(valideData(data)){
				$("#msgpp").setPage({
					pageCount : Math.ceil(data.total / pagecount),
					current : Number(page)
				});
				$('#gysmsgdg').datagrid('loadData', data);
				$('#msgtotal').html('共有'+data.total+'条记录');
				if(data.total==0){
					$('#showsqmsg').removeClass('newmsg');
				}
				$(top.$("#iframe_home"))[0].contentWindow.getskymsg();
			}
		}
	});
}
function showgyssqd(){
	getmysellersqlist('1');
	$('#gysmsgd').dialog('open');
}
function updategyssqd(){
	var data = $('#gysmsgdg').datagrid("getSelected");
	if(data){
		$('#umsellaccid').val(data.SELLACCID);
		$('#umgysno').val(data.SELLACCNAME);
		$('#umlinkman').val(data.LINKMAN);
		$('#ummobile').val(data.MOBILE);
		$('#umrequest').val(data.REQUEST);
		$('#umsgd').dialog('open');
	}else{
		alerttext("请选择一条数据编辑！");
	}
}

function addmyseller(){
	alertmsg(2);
	var sellaccid = $('#umsellaccid').val();
	var refuse = $('#umrejtext').val();
	var tag = "1";
	if($('#umreject').prop("checked")){
		tag=2;
		if(refuse.replace(/ /g,"").length==0){
			alerttext("拒绝原因不能为空！");
			return;
		}
	}else if($('#umagree').prop("checked")){
		tag=1;
	}
	$.ajax({
		type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
        data: {
        	erpser: "addmyseller",
        	sellaccid : sellaccid,
        	buyaccid: getValuebyName("ACCID"),
        	refuse : refuse,
        	tag : tag
        	},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        dataType: 'json', 
        success: function(data) {
        	if(valideData(data)){
        		if($('#umreject').prop("checked")){
        			alerttext("已拒绝！");
        		}else if($('#umagree').prop("checked")){
        			alerttext("已添加！");
        		}
        		getmysellersqlist("1");
        		getsellerlist("1");
        		$('#umsgd').dialog('close');
        		$('#gysmsgd').dialog('close');
        	}
        }
	});
	
}
function downsellerparams(){
	alertmsg(2);
	var sellaccid = $('#usellaccid').val();
	var fs = $('#fs').val();
	alerttext("同步中……",5*60*1000);
	$.ajax({
		type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
        data: {
        	erpser: "downsellerparams",
        	selleraccid: sellaccid ,
        	buyeraccid: getValuebyName("ACCID"),
        	fs : fs
        	},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        dataType: 'json', 
        success: function(data) {
        	if(valideData(data)){
        		alerttext("同步完成！");
        		$('#tbd').dialog('close');
        	}
        }
	});
}
</script>
</body>
</html>