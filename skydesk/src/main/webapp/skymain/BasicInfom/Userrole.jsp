<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0"> 
<meta name="renderer" content="webkit|ie-comp|ie-stand" /> 
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>角色功能</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/datagrid-groupview.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript">

var _levelid = getValuebyName("GLEVELID");
setqxpublic();
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize = function() {
	changeDivHeight();
}
function changeDivHeight() {
	var height = document.documentElement.clientHeight;//获取页面可见高度  
	$('#userrolet').datagrid('resize', {
		height : height - 50
	});
}
$(function(){
	$('#userrolepp').createPage({
        current:1,
        pageCount : 1,
        backFn:function(p){
        	getuserrolelist(p.toString());
        }
	});
	$('#userrolet').datagrid({
		idField : 'userrolename',
		height: $('body').height()-50,
		fitColumns : true,
		striped : true, //隔行变色
		singleSelect : true,
		pageSize:10,
		columns : [ [ {
			field : 'LEVELID',
			title : '角色ID',
			width : 200,
			hidden : true
		},{field :'CHECK',checkbox:true,hidden:true},
		{field : 'ROWNUMBER',title : '序号',width : 50,fixed:true,align:'center',halign:'center',
			formatter:function(value,row,index){
				var val = Math.ceil(Number(value)/ pagecount)-1;
				return isNaN(Number(value))||value==""?"":val*pagecount+Number(index)+1;
				}} , 
		{field : 'LEVELNAME', title : '角色名称',align:'center',halign:'center', fixed:true,width : 100} , 
        {field : 'XXSTR',title : '权限',width : 200,hidden : true},
        {field : 'ACTION',title : '操作',fixed:true,width : 150,align:'center',halign:'center',
			formatter:function(value,row,index){
				var html = '<input type="button" class="m-btn wid60" value="功能浏览" onclick="setprogd('+index+')">';
				if(row.LEVELID>100)
					html = '<input type="button" class="m-btn wid60" value="功能授权" onclick="setprogd('+index+')">'
					+ '<input type="button" class="m-btn wid60" value="删除" onclick="deluserrole('+index+')">';
					return html;
			}}
        ] ],
		onDblClickRow:function(rowIndex, rowData){
			updateuserd();
			},
		toolbar:'#usertollbar'
	}).datagrid('keyCtr','updateuserd()');
	getuserrolelist('1');
	setprogt();
	setjstype();
});
function adduserd(){
	$('#aduserd').dialog('open');
	$("#adlevelname,#adsearchday").val("");
	$("#adlevelname").focus();
}

function updateuserd(){
	var arr = $('#userrolet').datagrid('getSelections');
	if (arr.length != 1) {
		alerttext("请选择一行数据，进行编辑!");
	} else {
		var arrs = $('#userrolet').datagrid('getSelected');
		$('#uplevelname').val(arrs.LEVELNAME);
		$('#uplevelid').val(arrs.LEVELID);
		getuserrolebyid(arrs.LEVELID);
	}
}
//获取角色列表
function getuserrolelist(page){
	alertmsg(6);
	$.ajax({ 
       	type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
        data: {
        	erpser: "userrolelist",
        	fieldlist: "*",
        	rows: pagecount,
        	sort: "LEVELID",
        	order: "asc",
        	page: page
        	},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        dataType: 'json', 
        success: function(data) {     //回调函数，result，返回值 
        	if(valideData(data)){
    			$('#userrole_total').html(data.total);
        		$('#userrolepp').setPage({
			        pageCount:Math.ceil(data.total/ pagecount),
			        current:Number(page)
				 });
    			$('#userrolet').datagrid('loadData', data);
        	}
        }                 
        });
}

//添加角色
function adduserrole(){
	var levelname = $('#adlevelname').val();
	var glevelid = $('#adjstype').val();
	var xxstr1 = $('#ainsalekey').prop('checked')?"1":"0";
	var xxstr2 = $('#asharekey').prop('checked')?"1":"0";
	var xxstr3 = $('#aprintkey').prop('checked')?"1":"0";
	var xxstr4 = $('#adsearchday').val()==""?0:Number($('#adsearchday').val());
	var xxstr5 = $('#aallowcd').prop('checked')?"1":"0";
	var xxstr6 = $('#aallowclear').prop('checked')?"1":"0";
	var xxstr7 = $('#aalloweditware').prop('checked')?"1":"0";
	xxstr4 = "000"+xxstr4;
	xxstr4 = xxstr4.substr(-3);
	var xxstr="";
	xxstr += xxstr1;
	xxstr += xxstr2;
	xxstr += xxstr3;
	xxstr += xxstr4;
	xxstr += xxstr5;
	xxstr += xxstr6;
	xxstr += xxstr7;
	if(levelname==""){
		alerttext("角色名不能为空，请重新输入！");
	}else{
		alertmsg(2);
		$.ajax({ 
	       	type: "POST",   //访问WebService使用Post方式请求 
	        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
	        data: {
	        	erpser: "adduserrole",
	        	levelname: levelname,
	        	glevelid: glevelid,
	        	xxstr: xxstr
	        	},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
	        dataType: 'json', 
	        success: function(data) {     //回调函数，result，返回值 
	        	if(valideData(data)){
					getuserrolelist('1');
				}
	        	
	        }                 
	        });
	}
}
//修改角色
function updateuserrole(){
	var levelname = $('#uplevelname').val();
	var levelid = $('#uplevelid').val();
	var glevelid = $('#upjstype').val();
	var xxstr1 = $('#uinsalekey').prop('checked')?"1":"0";
	var xxstr2 = $('#usharekey').prop('checked')?"1":"0";
	var xxstr3 = $('#uprintkey').prop('checked')?"1":"0";
	var xxstr4 = $('#usearchday').val()==""?0:Number($('#usearchday').val());
	var xxstr5 = $('#uallowcd').prop('checked')?"1":"0";
	var xxstr6 = $('#uallowclear').prop('checked')?"1":"0";
	var xxstr7 = $('#ualloweditware').prop('checked')?"1":"0";
	xxstr4 = "000"+xxstr4;
	xxstr4 = xxstr4.substr(-3);
	var xxstr="";
	xxstr += xxstr1;
	xxstr += xxstr2;
	xxstr += xxstr3;
	xxstr += xxstr4;
	xxstr += xxstr5;
	xxstr += xxstr6;
	xxstr += xxstr7;
	if(levelname==""){
		alerttext("角色名不能为空，请重新输入！");
	}else{
		alertmsg(2);
		$.ajax({ 
	       	type: "POST",   //访问WebService使用Post方式请求 
	        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
	        data: {
	        	erpser: "updateuserrolebyid",
	        	levelid: levelid,
	        	glevelid: glevelid,
	        	levelname: levelname,
	        	xxstr: xxstr
	        	},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
	        dataType: 'json', 
	        success: function(data) {     //回调函数，result，返回值 
	        	if(valideData(data)){
					$('#upuserd').dialog('close');
					getuserrolelist('1');
				}
	        }                 
	        });
	}
}

//删除角色
function deluserrole(index){
	var $dg = $("#userrolet");
	var rows = $dg.datagrid("getRows");
	var row = rows[index];
	$.messager.confirm(dialog_title, '您确认要删除角色' + row.LEVELNAME + '？',
			function(r) {
				if (r) {
					var levelid = row.LEVELID;
					alertmsg(2);
					$.ajax({ 
				       	type: "POST",   //访问WebService使用Post方式请求 
				        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				        data: {erpser:"deluserrolebyid",levelid:levelid},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				        dataType: 'json', 
				        success: function(data) {     //回调函数，result，返回值 	
				        	if(valideData(data)){
								alerttext('删除成功！');
								getuserrolelist('1');
				        	}
				        }                 
				        });
				}
	});
}
function getepprog(page){
	var levelid = $('#uplevelid').val();
	alertmsg(6);
	$.ajax({ 
       	type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
        data: {erpser:"selectroleprog",levelid:levelid,rows:200,page:page},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        dataType: 'json', 
        success: function(data) {     //回调函数，result，返回值 
        	if(valideData(data)){
        		var total = data.total;
        		$('#progpp').setPage({
        			pageCount : Math.ceil(total/100),
        			current : Number(page)
        		});
        		check = false;
        		$('#progt').datagrid('loadData',data);
        		//$('#progt').datagrid('collapseGroup');
        		check = true;
        	}
        }
	});
}
function setprogd(index){
	$('#userrolet').datagrid('selectRow',index);
	var row = $('#userrolet').datagrid('getSelected');
	var levelids;
	var levelname;
	if(row){
		levelids = row.LEVELID;
		levelname = row.LEVELNAME;
		$('#uplevelid').val(levelids);
		var title;
		if(Number(levelids)>100)
			title = levelname + "_功能授权";
		else
			title = levelname + "_浏览授权";
		$('#progd').dialog({
			modal :true,
			title : title
		});
		$('#progd').dialog('open');
		getepprog('1');
	}else{
		alerttext('请选择！');
	}
}
function setepprog(progid,value){
	alertmsg(2);
	var levelid = $('#uplevelid').val();
	$.ajax({
		type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
        data: {
        	erpser:"writeroleprog",
        	levelid : levelid,
        	progid : progid,
        	value : value
        	},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        dataType: 'json', 
        success: function(data) {
        	if(valideData(data)){
        		
        	}
        }
	});
}
function setallprog(value){
	var levelid = $('#uplevelid').val();
	alertmsg(2);
	$.ajax({
		type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
        data: {
        	erpser:"writeallroleprog",
        	levelid : levelid,
        	value : value
        	},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        dataType: 'json', 
        success: function(data) {
        	if(valideData(data)){
        		
        	}
        		
        }
	});
}
var check = false;
function setprogt(){
	$('#progpp').setPage({
		current:1,
        backFn:function(p){
			getepprog(p.toString());
        }
	});
	$('#progt').datagrid({
		idField : 'epprog',
		height : 440,
		fitColumns : true,
		striped : true, //隔行变色
		singleSelect : true,
		view : groupview,
		groupField : 'SYSNAME',
		groupFormatter:function(value,rows){
	                  return value;
		       },
		columns : [[
		          {field : 'ROWNUMBER',title : '序号',width : 50,fixed:true,align:'center',halign:'center',
								formatter:function(value,row,index){
								return index+1;
								}
							},
				  {field : 'PROGID',title : '功能ID',hidden : true,width : 80},
			      {field : 'PROGNAME',title : '功能',width:100},
			      {field : 'SELBJ',title : 'SELBJ',hidden : true,width : 80},
		          {field :'CHECK',checkbox:true,fixed:true,width:50}
		          ]],
		selectOnCheck : false,
		checkOnSelect :false,
		pageSize : 100,
		onCheck : function(index,row){
			if(check){
				var levelid = $('#uplevelid').val();
				if(Number(levelid)>100)
					setepprog(row.PROGID,'1');
				else{
					alerttext('系统内定角色，不允许更改！');
					$('#progt').datagrid('refresh');
				}
			}
		},
		onUncheck : function(index,row){
			if(check){
				var levelid = $('#uplevelid').val();
				if(Number(levelid)>100)
					setepprog(row.PROGID,'0');
				else{
					alerttext('系统内定角色，不允许更改！');
					$('#progt').datagrid('refresh');
				}
			}
		},
		onCheckAll : function(index,row){
			if(check){
				var levelid = $('#uplevelid').val();
				if(Number(levelid)>100)
				setallprog('1');
				else{
					alerttext('系统内定角色，不允许更改！');
					$('#progt').datagrid('refresh');
				}
			}
		},
		onUncheckAll : function(index,row){
			if(check){
				var levelid = $('#uplevelid').val();
				if(Number(levelid)>100)
					setallprog('0');
				else{
					alerttext('系统内定角色，不允许更改！');
					$('#progt').datagrid('refresh');
				}
			}
		},
		onLoadSuccess : function(data) {
			check = false;
			if (data) {
				$.each(data.rows, function(index, item) {
					if (item.SELBJ == '1') {
						$('#progt').datagrid('checkRow', index);
					}else $('#progt').datagrid('uncheckRow', index);
				});
				check = true;
			}
			if(_levelid==0){
				$('.datagrid-cell-check input[type=checkbox],.datagrid-header-check input[type=checkbox]').removeAttr('disabled');
			}else{
				$('.datagrid-cell-check input[type=checkbox],.datagrid-header-check input[type=checkbox]').prop('disabled',true);
			}
		}
	});
}
function setjstype(){
	$.ajax({
		type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
        data: {
        	erpser:"listsysrole"
        	},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        dataType: 'json', 
        success: function(data) {
        	if(valideData(data)){
        		var rolelist = data.rows;
        		var html= '';
        		if(data.total>0){
        			for(var i in rolelist){
        				html += '<option value="'+rolelist[i].LEVELID+'">'+rolelist[i].LEVELNAME+'</option>';
        			}
        		}else{
        			html += '<option value="">网络出错，请刷新重试</option>';
        		}
        		$('#adjstype').append(html);
        		$('#upjstype').append(html);
        	}
        		
        }
	});
}
function getuserrolebyid(levelid){
	$.ajax({
		type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
        data: {
        	erpser:"getuserrolebyid",
        	levelid : levelid
        	},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        dataType: 'json', 
        success: function(data) {
        	if(valideData(data)){
        		var total = data.total;
        		var rows = data.rows;
        		if(total>0){
        			$('#uplevelname').val(rows[0].LEVELNAME);
        			$('#uplevelid').val(rows[0].LEVELID);
        			$('#upjstype').val(rows[0].GLEVELID);
        			if(rows[0].LEVELID>=0&&rows[0].LEVELID<=100)
        				$('#sysrole').hide();
        			else
        				$('#sysrole').show();
        			var xxstr = rows[0].XXSTR+"000000000";
        			var xxstr1 = xxstr.charAt(0);
        			var xxstr2 = xxstr.charAt(1);
        			var xxstr3 = xxstr.charAt(2);
        			if(xxstr.length>3){
        				$('#usearchday').val(Number(xxstr.substring(3,6)));
        			}else{
        				$('#usearchday').val(0);
        			}
        			var xxstr4 = xxstr.charAt(6);
        			var xxstr5 = xxstr.charAt(7);
        			var xxstr6 = xxstr.charAt(8);
        			xxstr1=='0'?$('#uinsalekey').removeAttr('checked'):$('#uinsalekey').prop('checked','true');
        			xxstr2=='0'?$('#usharekey').removeAttr('checked'):$('#usharekey').prop('checked','true');
        			xxstr3=='0'?$('#uprintkey').removeAttr('checked'):$('#uprintkey').prop('checked','true');
        			xxstr4=='0'?$('#uallowcd').removeAttr('checked'):$('#uallowcd').prop('checked','true');
        			xxstr5=='0'?$('#uallowclear').removeAttr('checked'):$('#uallowclear').prop('checked','true');
        			xxstr6=='0'?$('#ualloweditware').removeAttr('checked'):$('#ualloweditware').prop('checked','true');
        			if(_levelid==0){
	        			if(Number(rows[0].LEVELID)<=100){
	        				$('#upuserd input').attr("disabled","disabled");
	        				$('#upuserd .dialog-footer').hide(); 
	        				alerttext("系统内置角色不允许修改");
	        			}else{
		        			$('#upuserd').dialog({
		        				title : '编辑角色'
		        			});
	        				$('#upuserd .dialog-footer').show(); 
	        				$('#upuserd input').removeAttr("disabled");
	        			}
        			}else{
		        		$('#upuserd').dialog({
		        			title : '浏览角色'
		        		});
        				$('#upuserd :input').prop('disabled',true);
        			}
        			$('#upuserd').dialog('open');
        		}else{
        			alerttext('网络开小差，请重试！');
        		}
        	}
        		
        }
	});
}
</script>
</head>
<body class="easyui-layout layout panel-noscroll">
<div class="toolbar" id="usertollbar">
<div class="toolbar_box1">
<input class="btn1" type="button" value="添加" onclick="adduserd()">
<input type="button" class="btn1" value="编辑" onclick="updateuserd()">
</div>
</div>
<table id="userrolet" style="overflow: hidden;"></table>
	<!-- 分页 -->
	<div class="page-bar">
		<div  class="page-total">共有<span id="userrole_total">0</span>条记录
		</div>
		<div id="userrolepp" class="tcdPageCode page-code"></div>
	</div>
<!-- 角色添加 -->
	<div id="aduserd" title="添加角色" style="width: 400px;text-align:center; height: 360px" class="easyui-dialog" closed=true>
	<br><input type="hidden" id="adjslevelid">
	 <table width="300" border="0" cellspacing="5" cellpadding="5" class="table1">
    <tr>
      <td width="80" align="right" class="header skyrequied">角色名称</td>
      <td width="200" align="left"><input type="text" class="wid160 hig25" maxlength="10" name="adlevelname" id="adlevelname" placeholder="<输入>" ></td>
    </tr>
    <tr>
      <td width="80" align="right" class="header skyrequied">查询天数</td>
      <td width="200" align="left"><input type="text" class="wid162 hig25 onlyNum" maxlength="3" name="adsearchday" id="adsearchday" placeholder="<输入>" ></td>
    </tr>
    <tr>
      <td width="80" align="right" class="header skyrequied">角色类型</td>
      <td width="200" align="left">
      <select name="adjstype" id="adjstype"  class="wid160 hig25">
      </select>
      </td>
    </tr>
    <tr>
      <td align="right">&nbsp;</td>
      <td align="left"><label><input type="checkbox" id="ainsalekey">允许查看进价</label></td>
    </tr>
    <tr>
      <td align="right">&nbsp;</td>
      <td align="left"><label><input type="checkbox" id="asharekey">允许分享</label></td>
    </tr>
    <tr>
      <td align="right">&nbsp;</td>
      <td align="left"><label><input type="checkbox" id="aprintkey">允许设置打印页眉页脚</label></td>
    </tr>
    <tr>
      <td align="right">&nbsp;</td>
      <td align="left"><label><input type="checkbox" id="aallowcd">允许撤单</label></td>
    </tr>
    <tr>
      <td align="right">&nbsp;</td>
      <td align="left"><label><input type="checkbox" id="aallowclear">允许冲单</label></td>
    </tr>
    <tr>
      <td align="right">&nbsp;</td>
      <td align="left"><label><input type="checkbox" id="aalloweditware">仅允许浏览商品编码</label></td>
    </tr>
  </table>
		<div class="dialog-footer textcenter">
		<input type="button" class="btn1"  id="adduser" onclick="adduserrole();" value="保存并继续添加" />
		</div>
	</div>
	<!-- 角色修改 -->
	<div id="upuserd" title="修改角色" style="width: 400px;text-align:center; height: 360px" class="easyui-dialog" closed=true>
	<br><input type="hidden" id="uplevelid"><input type="hidden" id="ujslevelid">
	 <table width="300" border="0" cellspacing="5" cellpadding="5" class="table1">
    <tr>
      <td width="80" align="right" class="header skyrequied">角色名称</td>
      <td width="200" align="left"><input type="text" class="wid160 hig25" maxlength="10" name="uplevelname" id="uplevelname" placeholder="<输入>" ></td>
    </tr>
    <tr>
      <td width="80" align="right" class="header skyrequied">查询天数</td>
      <td width="200" align="left"><input type="text" class="wid160 hig25 onlyNum" maxlength="3" name="usearchday" id="usearchday" placeholder="<输入>" ></td>
    </tr>
     <tr id="sysrole">
      <td width="80" align="right" class="header skyrequied">角色类型</td>
      <td width="200" align="left">
 	<select name="upjstype" id="upjstype" class="wid162 hig25">
      </select>
	</td>
    </tr>
    <tr>
      <td align="right">&nbsp;</td>
      <td align="left"><label><input type="checkbox" id="uinsalekey">允许查看进价</label></td>
    </tr>
    <tr>
      <td align="right">&nbsp;</td>
      <td align="left"><label><input type="checkbox" id="usharekey">允许分享</label></td>
    </tr>
     <tr>
      <td align="right">&nbsp;</td>
      <td align="left"><label><input type="checkbox" id="uprintkey">允许打印页眉页脚</label></td>
    </tr>
     <tr>
      <td align="right">&nbsp;</td>
      <td align="left"><label><input type="checkbox" id="uallowcd">允许撤单</label></td>
    </tr>
     <tr>
      <td align="right">&nbsp;</td>
      <td align="left"><label><input type="checkbox" id="uallowclear">允许冲单</label></td>
    </tr>
      <tr>
      <td align="right">&nbsp;</td>
      <td align="left"><label><input type="checkbox" id="ualloweditware">仅允许浏览商品编码</label></td>
    </tr>
  </table>
 	 <div class="dialog-footer textcenter">
		<input type="button" class="btn1"  id="updateuser" onclick="updateuserrole();" value="保存" />
	</div>
	</div>
	<!-- 角色删除 -->
	<input	type="hidden" name="relevelid" id="relevelid">
	<!-- 角色功能权限-->
	<div id="progd" title="功能授权" style="width: 400px;text-align:center; height: 500px" class="easyui-dialog" closed=true>			
		<table id="progt" style="overflow:hidden"></table>
		<input type="hidden" id="uplevelid">
<!-- 		<div class="dialog-footer textcenter"> -->
<!-- 		<div id="progpp" class="tcdPageCode fl hide"></div>  -->
<!-- 		</div> -->
	</div>
</body>
</html>