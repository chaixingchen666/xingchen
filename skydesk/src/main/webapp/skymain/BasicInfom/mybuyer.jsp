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
<title>我的经销商</title>
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
	<input class="btn1" type="button" id="addnotebtn" value="添加" onclick="addjxsd()">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatejxsd()">
	<a href="javascript:void(0)" class="btn1" id="showsqmsg" onclick="showjxssqd()">查看申请</a>
	<input type="text" placeholder="搜索账号、客户名称<回车搜索>" data-end="yes" class="ipt1" id="qsvalue" maxlength="20" >
	</div>
</div>
</div>
<table id="jxsdg"></table>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total" id="notetotal">共有0条记录</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>
<!-- 申请框 -->
<div id="ad" title="增加经销商" data-options="modal:true" style="width: 400px; height: 300px;" class="easyui-dialog" closed="true">
<div class="textcenter" style="margin-top: 30px;">
<table cellspacing="5" class="table1" border="0" class="table1">
<tr><td>企业账号</td><td><input type="text" class="wid160 hig25 uppercase" id="jxsaccno" placeholder="<输入>"></td></tr>
<tr><td>申请内容</td><td><textarea class="wid160" rows="5" id="sqtext" placeholder="<输入>"></textarea></td></tr>
</table>
</div>
<div class="dialog-footer textcenter">
<input type="button" class="btn1" value="确定" onclick="sendsq()">
</div>
</div>
<!-- 编辑框 -->
<div id="ud" title="单位信息" data-options="modal:true" style="width: 350px; height: 300px;" class="easyui-dialog" closed="true">
<div class="textcenter">
<input type="hidden" id="ubuyaccid">
<input type="hidden" id="ucustid" >
<input type="hidden" id="uid" >
<table width="350" border="0" cellspacing="10" cellpadding="10" class="table1">
  <tr>
    <td width="110" align="right">账户名称</td>
    <td width="264" align="left"><input type="text" class="wid160 hig25" id="ujxsno" readonly></td>
  </tr>
  <tr>
    <td align="right">单位名称</td>
    <td align="left"><input type="text" class="wid160 hig25" id="ujxsname" readonly></td>
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
    <td align="right">客户</td>
    <td align="left"><input type="text" class="edithelpinput" id="ucustname" readonly><span onclick=" selectcust('updatebuy')"></span></td>
  </tr>
  <tr>
    <td align="right">代理品牌</td>
    <td align="left"><input type="text" class="edithelpinput" id="ubrandnamelist" placeholder="<请选择>" readonly><span onclick="setbrandt()"></span></td>
  </tr>
</table>
</div>
</div>
<!-- 消息框 -->
<div id="jxsmsgd" title="经销商申请" data-options="modal:true" style="width: 650px; height: 400px;" class="easyui-dialog" closed="true">
<table id="jxsmsgdg"></table>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total" id="msgtotal">共有0条记录</div>
	<div id="msgpp" class="tcdPageCode page-code">
	</div>
</div>
</div>
<!-- 申请信息框 -->
<div id="umsgd" title="申请信息" data-options="modal:true" style="width: 550px; height: 400px;" class="easyui-dialog" closed="true">
<div class="textcenter">
<input type="hidden" id="umbuyaccid">
<table width="394" border="0" cellspacing="10" cellpadding="10" class="tebel1">
  <tr>
    <td width="110" align="right">账户名称</td>
    <td width="264" align="left"><input type="text" class="wid160 hig25" id="umjxsno" readonly></td>
  </tr>
  <tr>
    <td align="right">单位名称</td>
    <td align="left"><input type="text" class="wid160 hig25" id="umjxsname" readonly></td>
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
<input type="button" class="btn1" value="保存并继续" onclick="addmybuyer()">
</div>
</div>
<!-- 品牌授权-->
<div id="brandd" title="品牌授权" data-option="modal:true" style="width: 400px;text-align:center; height: 300px" class="easyui-dialog" closed=true>			
	<table id="brandt" style="overflow:hidden"></table>
	<div class="dialog-footer textcenter">
	<div id="brandpp" class="tcdPageCode fl"></div> 
	</div>
</div>
<!-- 客户帮助列表 -->
<jsp:include page="../HelpList/CustHelpList.jsp"></jsp:include>
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
	if(top.buyernum>0)
		$('#showsqmsg').addClass('newmsg');
	$("#sqtext").text("我是"+getValuebyName("COMID"));
	$('#jxsdg').datagrid({
		idField : 'jxsdg',
		height : $('body').height() - 50,
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
					field : 'ACCID',
					title : '经销商ID',
					width : 200,
					hidden : true					
				},
				{
					field : 'ACCNAME',
					title : '经销商账号',
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
					halign : 'center'
				},
				{
					field : 'LINKMAN',
					title : '联系人',
					width : 100,
					fixed : true,
					halign : 'center',
					align : 'center'
				},
				{
					field : 'MOBILE',
					title : '移动电话',
					width : 100,
					fixed : true,
					halign : 'center',
					align : 'center'
				},
				{
					field : 'TEL',
					title : '联系电话',
					width : 100,
					fixed : true,
					halign : 'center',
					align : 'center'		
				},{
					field : 'CUSTNAME',
					title : '客户名称',
					fixed : true,
					width : 150,
					halign : 'center',
					align : 'center'
				} ,
				{
					field : 'ACTION',
					title : '操作',
					width : 80,
					fixed: true,
					halign : 'center',
					align: 'center',
					formatter : function(value, row,index) {
						return '<input type="button" class="m-btn" value="删除" onclick="delmybuyerbyid('+index+')"';
					}
				}] ],
		onClickRow :function(index,row){
			
		},
		onDblClickRow : function(rowIndex, rowData) {
			updatejxsd();
		},
		pageSize : 20,
		toolbar : '#toolbars'
	}).datagrid("keyCtr","updatejxsd()");
	$('#jxsmsgdg').datagrid({
		idField : 'jxsmsgdg',
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
					field : 'BUYACCID',
					title : '经销商ID',
					width : 200,
					hidden : true					
				},
				{
					field : 'BUYACCNAME',
					title : '经销商账号',
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
			updatejxssqd();
		},
		pageSize : 20,
		toolbar : ''
	}).datagrid("keyCtr","updatejxssqd()");
	$("#msgpp").createPage({
		pageCount : 1,
		current : 1,
		backFn: function(p){
			getmybuyersqlist(p);
		}
	});
	$("#pp").createPage({
		pageCount : 1,
		current : 1,
		backFn: function(p){
			getbuyerlist(p);
		}
	});
	getbuyerlist("1");
});
function getbuyerlist(page){
	opser = "get";
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "mybuyerlist",
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
				$('#jxsdg').datagrid('loadData', data);
				$('#notetotal').html('共有'+data.total+'条记录');
			}
		}
	});
}
function addjxsd(){
	$('#ad :text').val("");
	$('#ad').dialog('open');
	$("#jxsaccno").val("").focus();
}
function sendsq(){
	var buyaccname = $('#jxsaccno').val();
	var request = $('#sqtext').val();
	request==""?("我是"+getValuebyName('COMID')):request;
	if(buyaccname==""){
		alerttext("企业账号不能为空！");
		$('#jxsaccno').focus();
	}else{
		$.ajax({
			type: "POST", //访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "addmybuyeracclink",
				buyaccname : buyaccname,
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
function updatejxsd(){
	var data = $('#jxsdg').datagrid("getSelected");
	$('#ud :text').val("");
	if(data){
		$('#uid').val(data.ID);
		$('#ubuyaccid').val(data.ACCID);
		$('#ujxsno').val(data.ACCNAME);
		$('#ujxsname').val(data.COMPANY);
		$('#ulinkman').val(data.LINKMAN);
		$('#umobile').val(data.MOBILE);
		$('#utel').val(data.TEL);
		$('#ucustname').val(data.CUSTNAME);
		$('#ucustid').val(data.CUSTID);
		 getbuyerbrandname();
		$('#ud').dialog('open');
	}else{
		alerttext("请选择一条数据编辑！");
	}
}
function changebuyer(custid){
	alertmsg(2);
	var id = $('#uid').val();
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "updatemybuyerbyid",
			id : id,
			sellcustid : custid
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			if(valideData(data)){
				alerttext("操作完成！");
				getbuyerlist("1");
			}
		}
	});
}
function getbuyerbrandname(){
	var buyaccid = $('#ubuyaccid').val();
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			buyeraccid : buyaccid,
			erpser : "getbuyerbrandname"
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			if(valideData(data)){
				if(data.total>0){
					$('#ubrandnamelist').val(data.rows[0].BRANDNAMELIST);
				}
			}
		}
	});
}
function delmybuyerbyid(index){
	var msg= "是否确定删除该经销商";
	var row = $("#jxsdg").datagrid("getRows")[index];
	$.messager.confirm(dialog_title,msg,function(r){ 
		if(r){
			alertmsg(2);
			var id = $('#uid').val();
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser : "delmybuyerbyid",
					id : row.ID
				}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType : 'json',
				success : function(data) { //回调函数，result，返回值 
					if(valideData(data)){
						alerttext("操作完成！");
						getbuyerlist("1");
					}
				}
			});
		}
	});
}
var check = false;
var branddg = true;
function setbrandt(){
	$('#brandpp').createPage({
		pageCount :1,
		current:1,
        backFn:function(p){
			getbuybrand(p.toString());
        }
	});
	if(branddg){
	$('#brandt').datagrid({
		idField : 'buybrand',
		height : 200,
		fitColumns : true,
		striped : true, //隔行变色
		singleSelect : true,
		columns : [[
		          {field : 'ROWNUMBER',title : '序号',width : 50,fixed:true,align:'center',halign:'center',
								formatter:function(value,row,index){
									var val = Math.ceil(Number(value)/ pagecount)-1;
									return val*pagecount+Number(index)+1;
								}
							},
				  {field : 'BRANDID',title : '品牌ID',hidden : true,width : 80},
			      {field : 'BRANDNAME',title : '品牌',width:100},
			      {field : 'SELBJ',title : 'SELBJ',hidden : true,width : 80},
		          {field :'CHECK',checkbox:true,fixed:true,width:50}
		          ]],
		selectOnCheck : false,
		checkOnSelect :false,
		pageSize : 10,
		onCheck : function(index,row){
			if(check){
				setbuybrand(row.BRANDID,'1');
			}
		},
		onUncheck : function(index,row){
			if(check){
				setbuybrand(row.BRANDID,'0');
			}
		},
		onCheckAll : function(index,row){
			if(check){
				setallbrand('1');
			}
		},
		onUncheckAll : function(index,row){
			if(check){
				setallbrand('0');
			}
		},
		onLoadSuccess : function(data) {
			check = false;
			if (data) {
				$.each(data.rows, function(index, item) {
					if (item.SELBJ == '1') {
						$('#brandt').datagrid('checkRow', index);
					}
				});
				check = true;
			}
		}
	});
	branddg = false;
	}
	$('#brandd').dialog('open');
	getbuybrand("1");
}
function getbuybrand(page){
	var buyaccid = $('#ubuyaccid').val();
	$.ajax({
		type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
        data: {
        	erpser:"buyerbrandlist",
        	buyaccid:buyaccid,
        	rows: pagecount,
        	page:page
        	},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        dataType: 'json', 
        success: function(data) {     //回调函数，result，返回值 
        	if(valideData(data)){
        		var total = data.total;
        		$('#brandpp').setPage({
        			pageCount : Math.ceil(total/pagecount),
        			current : Number(page)
        		});
        		check = false;
        		$('#brandt').datagrid('clearChecked');
        		$('#brandt').datagrid('loadData',data);
        		check = true;
        	}
        }
	});
}
function setbuybrand(brandid,value){
	alertmsg(2);
	var buyaccid = $('#ubuyaccid').val();
	$.ajax({
		type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
        data: {
        	erpser:"writebuyerbrand",
        	buyaccid : buyaccid,
        	brandid : brandid,
        	value : value
        	},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        dataType: 'json', 
        success: function(data) {
        	if(valideData(data)){
        		getbuyerbrandname();
        	}
        }
	});
}
function setallbrand(value){
	var buyaccid = $('#ubuyaccid').val();
	alertmsg(2);
	$.ajax({
		type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
        data: {
        	erpser:"writeallbuyerbrand",
        	buyaccid : buyaccid,
        	value : value
        	},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        dataType: 'json', 
        success: function(data) {
        	if(valideData(data)){
        		getbuyerbrandname();
        	}
        }
	});
}

function getmybuyersqlist(page){
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "mybuyeracclinklist",
			fieldlist: "a.id,a.applydate,a.buyaccid,a.tag,a.request,b.company,b.linkman,b.mobile",
			sort: "company",
			order: "asc",
			rows: pagecount,
			page: page
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			if(valideData(data)){
				$("#msgpp").setPage({
					pageCount : Math.ceil(data.total / pagecount),
					current : Number(page)
				});
				$('#jxsmsgdg').datagrid('loadData', data);
				$('#msgtotal').html('共有'+data.total+'条记录');
				if(data.total==0){
					$('#showsqmsg').removeClass('newmsg');
				}
				$(top.$("#iframe_home"))[0].contentWindow.getskymsg();
			}
		}
	});
}
function showjxssqd(){
	getmybuyersqlist('1');
	$('#jxsmsgd').dialog('open');
}
function updatejxssqd(){
	var data = $('#jxsmsgdg').datagrid("getSelected");
	if(data){
		$('#umbuyaccid').val(data.BUYACCID);
		$('#umjxsno').val(data.BUYACCNAME);
		$('#umlinkman').val(data.LINKMAN);
		$('#ummobile').val(data.MOBILE);
		$('#umrequest').val(data.REQUEST);
		$('#umsgd').dialog('open');
	}else{
		alerttext("请选择一条数据编辑！");
	}
}
function addmybuyer(){
	alertmsg(2);
	var buyaccid = $('#umbuyaccid').val();
	var refuse = $('#umrejtext').val();
	var tag = 1;
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
        	erpser: "addmybuyer",
        	buyaccid: buyaccid,
        	sellaccid: getValuebyName("ACCID"),
        	refuse: refuse,
        	tag: tag
        	},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        dataType: 'json', 
        success: function(data) {
        	if(valideData(data)){
        		if($('#umreject').prop("checked")){
        			alerttext("已拒绝！");
        		}else if($('#umpass').prop("checked")){
        			alerttext("已添加！");
        		}
        		getmybuyersqlist("1");
        		getbuyerlist("1");
        		$('#umsgd').dialog('close');
        		$('#jxsmsgd').dialog('close');
        	}
        }
	});
	
}

</script>
</body>
</html>