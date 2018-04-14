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
<meta http-equiv="X-UA-Compatible" content="IE=9;IE=8;IE=7;IE=EDGE" />
<title>店铺档案</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<style type="text/css">
.up_imgdiv {
    border: 1px solid #d7d7d7;
}
#bookhousetoolbar li.checked {
    background: #ff7900;
    color: #fff;
    border
}
#bookhousetoolbar li {
    float: left;
    padding: 0 5px;
    cursor: pointer;
}
#bookhousetoolbar li:not(.checked):hover {
    color: #00959a;
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
<body class="easyui-layout layout panel-noscroll" >
<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
<div class="fl">
	<input class="btn1" type="button" value="添加" onclick="addhoused()">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatehoused()">
	<input type="text" data-end="yes" placeholder="输入店铺名称或简称<回车搜索>" class="ipt1" id="qshousevalue" maxlength="20" data-enter="gethousedata()">
<!-- 	<input type="button" id="highsearch" class="btn2" value="高级搜索▼" onclick="toggle()"> -->
</div>
<div class="fl hide" id="searchbtn">
	<input type="button" class="btn2" type="button" value="搜索" onclick="searchhouse();toggle();"> 
	<input type="button" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()">
</div>
<div class="fr hide">
	<input type="button" class="btn3" style="width: 45px" value="导入" onclick="openimportd()"> 
	<span class="sepreate"></span>
	<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
</div>
</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchhouse();toggle();">
	<input type="hidden" id="shouseid">
	<div class="search-box">
	<div class="s-box">
		<div class="title">店铺名称</div>
		<div class="text"><input class="wid160 hig25" id="shousename" maxlength="20" type="text" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title">状态</div>
		<div class="text">
		<label>
		<input type="radio" name="snouse" id="st1" value="0">在用
		</label>
		<label>
		<input type="radio" name="snouse" id="st2" value="1">禁用
		</label>
		<label>
		<input type="radio" name="snouse" id="st3" value="2" checked>所有
		</label>
		</div>
	</div>
	</div>
</div>
</div>
<table id="gg" style="overflow: hidden;"></table>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total">
	共有<span id="pp_total">0</span>条记录
</div>
<div id="pp" class="tcdPageCode page-code"></div>
</div>
<!-- 修改店铺窗 -->
<div id="ud" title="<span id='udtitle'>编辑店铺</span>" style="width: 600px; max-height: 100%;" class="easyui-dialog webuploader-element-invisible" closed=true>
	<div style="margin-top: 10px; text-align: center;">
	<input type="hidden" id="uindex" value="0">
	<input type="hidden" id="uhouseid" value="0">
	<table width="550" border="0" cellspacing="10" class="table1">
  <tr>
    <td width="70" align="right" class="header skyrequied">店铺名称</td>
    <td width="160" align="left"><input class="easyui-validatebox" maxlength="20" type="text" name="uhousename" id="uhousename" data-options="required:true" style="width:160px;height:25px"></td>
 	<td colspan="2" rowspan="5">
	  <div class="up_imgdiv" style="width:200px;height:200px" id="uimg_div">
	  <img id="uimgview" class="hide" width="200" height="200"/>
	  </div>
 		<div id="utwobarimage">上传二维码</div><span style="color:#ff7900;margin-top: 15px;display: block;">二维码请选择长宽1:1</span>
 	</td>
  </tr>
  <tr>
    <td width="120" align="right" class="header">联系电话</td>
    <td width="160" align="left"><input class="hig25" type="text" name="hosuetel" id="utel" style="width:160px" maxlength="12" placeholder="<输入>"></td>
  </tr>
  <tr>
    <td align="right" class="header">早班时间</td>
    <td align="left"><input id="uontime" class="easyui-timespinner" 
    data-options="showSeconds:false" value="08:30" style="width:73px;height:25px"/>---<input id="uofftime"
	class="easyui-timespinner" data-options="showSeconds:false" value="11:30"  style="width:73px;height:25px" /></td>
  </tr>
  <tr>
    <td align="right" class="header">中班时间</td>
    <td align="left"><input id="uontime1" class="easyui-timespinner"   style="width:73px;height:25px"
    data-options="showSeconds:false" value="12:30" />---<input id="uofftime1"
	class="easyui-timespinner" data-options="showSeconds:false" value="17:30"  style="width:73px;height:25px" /></td>
  </tr>
  <tr>
    <td align="right" class="header">晚班时间</td>
    <td align="left"><input id="uontime2" class="easyui-timespinner" style="width:73px;height:25px"
    data-options="showSeconds:false" value="18:30" />---<input id="uofftime2"
	class="easyui-timespinner" data-options="showSeconds:false" value="22:30"  style="width:73px;height:25px" /></td>
  </tr>
   <tr>
     <td align="right" class="header">零售价格</td>
    <td align="left"><select id="upricetype" name="upricetype" class="sele1">
						<option value="0" selected>零售价</option>
						<option value="4">批发价</option>
						<option value="5">打包价</option>
						<option value="1">售价一</option>
						<option value="2">售价二</option>
						<option value="3">售价三</option>
				</select></td>
     <td align="right" class="header">调拨价格</td>
    <td align="left"><select id="upricetype1" name="upricetype1" class="sele1">
						<option value="0" selected>零售价</option>
						<option value="4">批发价</option>
						<option value="5">打包价</option>
						<option value="1">售价一</option>
						<option value="2">售价二</option>
						<option value="3">售价三</option>
				</select></td>
  </tr>
  <tr>
    <td align="right" class="header">地址</td>
    <td colspan="3" align="left"><input class="hig25" type="text" id="uaddress" style="width:100%" maxlength="100" placeholder="<输入>"></td>
  </tr>
  <tr>
	<td align="right" class="header">分类</td>
	<td align="left"><input type="text" class="hig25" placeholder="<输入>" id="ulocano" name="ulocano" list="locano_list">
	            <datalist id="locano_list">
				</datalist>
  </tr>
  <tr id="uishouseinfo">
    <td align="right" class="header">面积</td>
    <td align="left"><input class="hig25" maxlength="7" type="text" name="uarea" id="uarea" style="width:100px" placeholder="<输入>"> 平方米(㎡)</td>
    <td align="right" class="header">租金</td>
    <td align="left"><input class="hig25" type="text" maxlength="7" name="urent" id="urent"  style="width:143px" placeholder="<输入>"> 元</td>
  </tr>
  <tr>
    <td align="right" class="header skyrequied">经营描述</td>
    <td colspan="3" align="left"><textarea style="width:100%;" maxlength="500" name="uremark" id="uremark" cols="45" rows="2" placeholder="例如：男装。女装、箱包等"></textarea></td>
  </tr>
</table>
	</div>
	<div class="dialog-normal-footer textcenter">
		<label class="updateshow"><input type="checkbox" name="unoused" id="unoused">禁用</label>
		<input type="button" id="savebtn" class="btn1"  name="updatehouse" value="保存" onclick="savehouse()">
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#ud').dialog('close')"> 
	</div>
	</div>
<div id="bookhoused" title="设置登账" class="easyui-dialog" style="width:400px;height:400px;" closed="true">
<div class="toolbar" id="bookhousetoolbar" style="height: 30px;line-height: 30px;">
<ul>
<li class="checked" id="bookhouselb0">收款</li>
<li id="bookhouselb1">付款</li>
</ul>
<input class="btn1 ml10" type="button" value="添加" onclick="addbookhoused()">
<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatebookhoused()">
</div>
<table id="bookhouset"></table>
<!-- 分页 -->
<div class="page-bar">
<div id="bookhousepp" class="tcdPageCode page-code"></div>
</div>
</div>

<div id="ubookhoused" title="增加登账记录" class="easyui-dialog" style="width:400px;height:300px;" closed="true">
<div style="margin-top: 10px;text-align: center;">
	<input type="hidden" id="upayid">
	<input type="hidden" id="ubookid">
	<input type="hidden" id="usubid">
	<input type="hidden" id="ubookhouseid">
	<input type="hidden" id="ubookhouseindex">
	<table class="table1" cellspacing="10">
	<tr>
	<td class="header skyrequied" align="right">
	结算方式
	</td>
	<td>
	<input type="text" class="edithelpinput pw_help" data-value="#upayid" placeholder="<选择或输入>" id="upayname"/> 
	<span onclick="Paywaydata('update')"></span>
	</td>
	</tr>
	<tr>
	<td class="header skyrequied" align="right">
	账本
	</td>
	<td>
	<input type="text" class="edithelpinput book_help" data-value="#ubookid" placeholder="<选择或输入>" id="ubookname"/> 
	<span onclick="selectbook('update')"></span>
	</td>
	</tr>
	<tr>
	<td class="header skyrequied" align="right">
	记账项目
	</td>
	<td>
	<input type="text" class="edithelpinput sub_help" data-value="#usubid" placeholder="<选择或输入>" id="usubname"/> 
	<span onclick="selectsubd('update')"></span>
	</td>
	</tr>
	</table>
	</div>
<div class="dialog-footer">
	<input type="button" class="btn1" value="确定" onclick="savebookhousepay()"/>
	<input type="button" class="btn2" value="取消" onclick="$('#ubookhoused').dialog('close')"/> 
</div>
</div>
<script type="text/javascript" charset="UTF-8">
//权限设置
setqxpublic();
var pgid= "pg1007";
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize = function() {
	changeDivHeight();
}
function changeDivHeight() {
	var height = document.documentElement.clientHeight; //获取页面可见高度  
	$('#gg').datagrid('resize', {
		height: height - 50
	});
}
var searchb = false;
function toggle() {
	if (searchb) {
		$('#highsearch').val('高级搜索▼');
		searchb = false;
		$('#searchbtn').hide();
	} else {
		$('#highsearch').val('高级搜索▲');
		searchb = true;
		$('#searchbtn').show();
	}
	$('.searchbar').slideToggle('fast');
	$('#shousename').focus();

}
//清空搜索条件
function resetsearch() {
	$('#st1').prop('checked', true);
	var idstr = "#shousename";
	$(idstr).val('');
}
//添加框
function addhoused() {
	$("#udtitle").html("添加店铺");
	$("#savebtn").val("保存并继续");
	$('#ud').dialog('open');
	$("#uhouseid,#uhousename,#uaddress,#utel,#uremark,#ulocano").val("");
	$("#uontime").val("08:30");
	$("#uofftime").val("11:30");
	$("#uontime1").val("12:30");
	$("#uofftime1").val("17:30");
	$("#uontime2").val("18:30");
	$("#uofftime2").val("22:30");
	$('#unoused').prop('checked','checked');
	$(".updateshow").hide();
	$("#utwobarimage").removeClass("changed");
	$("#uimgview").hide();
	$('#uhousename').focus();
}
//编辑框
function updatehoused() {
	var row = $('#gg').datagrid('getSelected');
	if (!row) {
		alerttext("请选择一行数据，进行编辑!");
	} else {
		var noused = row.NOUSED;
		$("#udtitle").html("编辑店铺");
		$("#savebtn").val("保存");
		$(".updateshow").show();
		$('#ud').dialog('open');
		$('#uhousename').focus();
		$('#uhousename').val(row.HOUSENAME);
		$('#uhouseid').val(row.HOUSEID);
		$('#uaddress').val(row.ADDRESS);
		$('#utel').val(row.TEL);
		$('#upricetype').val(row.PRICETYPE);
		$('#upricetype1').val(row.PRICETYPE1);
		$('#uontime').timespinner('setValue', row.ONTIME);
		$('#uofftime').timespinner('setValue', row.OFFTIME);
		$('#uontime1').timespinner('setValue', row.ONTIME1);
		$('#uofftime1').timespinner('setValue', row.OFFTIME1);
		$('#uontime2').timespinner('setValue', row.ONTIME2);
		$('#uofftime2').timespinner('setValue', row.OFFTIME2);
		$("#utwobarimage").removeClass("changed");
		if(row.TWOBARIMAGE&&row.TWOBARIMAGE.length>0)
			$("#uimgview").attr("src",imageurl + row.TWOBARIMAGE).show();
		else
			$("#uimgview").hide();
		$('#uremark').val(row.REMARK);
		$('#ulocano').val(row.LOCANO);
		$('#urent').val(row.RENT);
		$('#uarea').val(row.AREAM2);
		$('#uhouseid').val(row.HOUSEID);
		if (noused != '0') {
			$('#unoused').prop('checked', 'true');
		} else {
			$('#unoused').removeProp('checked');
		}
	}
}
$(function() {
	$("#pp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			gethouselist(p);
		}
	});
	//加载数据
	$('#gg').datagrid({
		idField: 'HOUSEID',
		height: $("body").height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
// 		sortName: "HOUSENAME",
// 		sortOrder: "asc",
		columns: [[{
			field: 'HOUSEID',
			title: '店铺ID',
			fieldtype: "G",
			width: 200,
			expable: true,
			hidden: true
		},{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var p = $("#pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
		},
		{
			title: '店铺二维码',
			field: 'TWOBARIMAGE',
			width: 70,
			align: 'center',
			fixed: true,
			formatter: function(value, row, index) {
				var val = row.TWOBARIMAGE;
				if (val != '' && val != null && val != undefined) {
					return '<img bigimg="true"  style=\"height:60px; width:60px;\" src="' + imageurl + '' + row.TWOBARIMAGE + '_60x60-m3.png" />';
				}
			}
		},{
			field: 'HOUSENAME',
			title: '店铺',
			width: 200,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'center'
		}, {
			field : 'ADDRESS',
			title : '地址',
			halign : 'center',
			width : 200,
			fixed: true
		}, {
			field : 'TEL',
			title : '联系电话',
			halign : 'center',
			align : 'center',
			width : 90,
			fixed : true
		}, {
			field : 'PRICETYPE',
			title : '零售价方式',
			align : 'center',
			halign : 'center',
			width : 80,
			fixed: true,
			formatter : function(value, row,index) {
				var pt = value=='0'?'零售价':
					value=='1'?'售价一':
						value=='2'?'售价二':
							value=='3'?'售价三':
								value=='4'?'批发价':'打包价';
				return pt;
			}
		}, {
			field : 'PRICETYPE1',
			title : '调拨价方式',
			align : 'center',
			halign : 'center',
			width : 80,
			fixed: true,
			formatter : function(value, row,index) {
				var pt = value=='0'?'零售价':
					value=='1'?'售价一':
						value=='2'?'售价二':
							value=='3'?'售价三':
								value=='4'?'批发价':'打包价';
				return pt;
			}
		}, {
			field : 'ONTIME',
			title : '早班时间',
			halign : 'center',
			width : 200,
			hidden:true
		}, {
			field : 'OFFTIME',
			title : '早班时间',
			halign : 'center',
			width : 200,
			hidden:true
		},{
			field : 'AMTINE',title : '早班时间',align:'center',halign:'center',width : 80,fixed : true,
			formatter:function(value,row,index){
				return row.ONTIME+'-'+row.OFFTIME;
			}
		}, {
			field : 'ONTIME1',
			title : '中班时间',
			width : 200,
			hidden:true
		}, {
			field : 'OFFTIME1',
			title : '中班时间',
			width : 200,
			hidden:true
		},{
			field : 'PMTINE',title : '中班时间',align:'center',halign:'center',width :80,fixed : true,
			formatter:function(value,row,index){
				return row.ONTIME1+'-'+row.OFFTIME1;
			}
		}, {
			field : 'ONTIME2',
			title : '上晚班时间',
			width : 200,
			hidden:true
		}, {
			field : 'OFFTIME2',
			title : '晚班时间',
			width : 200,
			hidden:true
		},{
			field : 'EMTINE',title : '晚班时间',align:'center',halign:'center',width : 80,fixed : true,
			formatter:function(value,row,index){
				return row.ONTIME2+'-'+row.OFFTIME2;
			}
		}, {
			field : 'AREAM2',
			title : '店铺面积',
			width : 200,
			hidden : true
		}, {
			field : 'REMARK',
			title : '经营描述',
			width : 200,
			fixed: true,
		}, {
			field : 'RENT',
			title : '租金',
			width : 200,
			hidden : true
		},{
			field: 'NOUSED',
			title: '禁用',
			width: 80,
			fixed: true,
// 			sortable: true,
			fieldtype: "G",
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var nouse = Number(value);
				var checked = "";
				if (nouse == 1) checked = "checked";
				if (isNaN(nouse)) return "";
				return '<input type="checkbox" id="disabledcheckin_' + index + '" class="m-btn" onchange="disabledhouse(' + index + ')" ' + checked + '>';
			}
		},{
			field: 'ACTION',
			title: '操作',
			width: 150,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				return '<input type="button" class="m-btn" value="设置登账" onclick="setbookhoused(' + index + ')">'
				+'<input type="button" class="m-btn" value="删除" onclick="delhouse(' + index + ')">';
			}
		}]],
		onSortColumn: function(sort, order) {
			$('#pp').refreshPage();
		},
		onClickRow: function(index, row) {
			$('#uindex').val(index);
		},
		onDblClickRow: function(rowIndex, rowData) {
			updatehoused();
		},
		pageSize: 20,
		toolbar: '#toolbars'
	}).datagrid("keyCtr", "updatehoused()");
	gethousedata();
// 	uploader_xls_options.uploadComplete = function(file){
// 		$('#pp').refreshPage();
// 	}
// 	uploader_xls = SkyUploadFiles(uploader_xls_options);
// 	setuploadserver({
// 		server: "/skydesk/fyimportservice?importser=appendhouse",
// 		xlsmobanname: "housecode",
// 		channel: "housecode"
// 	});
	setuploadimg({
		pick: {
			id: "#utwobarimage",
			multiple : false
		},
		viewimg: "#uimgview",
		thumb: {
			width: 200,
			height: 200,
			quality: 70,
			allowMagnify: true,
			crop: true,
			type: 'image/jpeg'
		},
		compress: {
			width: 300,
			height: 300,
			quality: 90,
			allowMagnify: true,
			crop: false,
			preserveHeaders: true,
			noCompressIfLarger: true,
			compressSize: 0
		}
	});
});

var currentdata = {};
var gethouselist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
// 	var sort = options.sortName;
// 	var order = options.sortOrder;
	currentdata["erpser"] = "warehouselist";
// 	currentdata["sort"] = sort;
// 	currentdata["order"] = order;
	currentdata["fieldlist"] = "houseid,housename,address,tel,noused"
		+",ontime,offtime,ontime1,offtime1,pricetype,pricetype1,twobarimage"
		+ ",ontime2,offtime2,aream2,rent,remark,locano";
	currentdata["noused"] = 2;
	currentdata["rows"] = pagecount;
	currentdata["page"] = page;
	$dg.datagrid('loadData', nulldata);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: currentdata,
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$("#pp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$dg.datagrid('loadData', data);
					$('#pp_total').html(data.total);
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
//搜索
function gethousedata() {
	var value = $('#qshousevalue').val();
	alertmsg(6);
	currentdata = {
		findbox: value
	};
	gethouselist(1);
}
//搜索店铺
function searchhouse() {
	var housename = $('#shousename').val();
	var noused = 0;
	if ($('#st1').prop('checked')) noused = "0";
	else if ($('#st2').prop('checked')) noused = "1";
	else noused = "2";
	alertmsg(6);
	currentdata = {
		housename: housename,
		noused: noused
	};
	gethouselist(1);
}
//添加店铺
function savehouse() {
	var index = $("#uindex").val();
	var houseid = Number($("#uhouseid").val());
	var housename = $('#uhousename').val();
	if (housename == "") {
		alerttext("店铺名不能为空");
		return;
	}
	var erpser = "addwarehouse";
	var noused = 0;
	if (houseid > 0){
		erpser = "updatewarehousebyid";
		noused = $("#unoused").prop("checked") ? 1 : 0;
	}
	var address = $("#uaddress").val();
	var tel = $("#utel").val();
	var aream2 = $("#uarea").val();
	var rent = $("#urent").val();
	var pricetype = $("#upricetype").val();
	var pricetype1 = $("#upricetype1").val();
	var ontime = $("#uontime").timespinner('getValue'); 
	var offtime = $("#uofftime").timespinner('getValue'); 
	var ontime1 = $("#uontime1").timespinner('getValue'); 
	var offtime1 = $("#uofftime1").timespinner('getValue'); 
	var ontime2 = $("#uontime2").timespinner('getValue'); 
	var offtime2 = $("#uofftime2").timespinner('getValue'); 
	var remark = $("#uremark").val();
// 	var imagename1 = $("#uimagename1").val();
	var locano = $("#ulocano").val();
	if(Number(ontime.substring(0,2))>Number(offtime.substring(0,2))){
		alerttext("上班时间大于下班时间！");
		$('#uontime').focus();
		return;
	}else if(Number(ontime1.substring(0,2))>Number(offtime1.substring(0,2))){
		alerttext("上班时间大于下班时间！");
		$('#uontime1').focus();
		return;
	}else if(Number(ontime2.substring(0,2))>Number(offtime2.substring(0,2))){
		alerttext("上班时间大于下班时间！");
		$('#aontime2').focus();
		return;
	}
	var currentdata = {
			erpser: erpser,
			houseid: houseid,
			housename: housename,
        	address: address,
        	pricetype: pricetype,
        	pricetype1: pricetype1,
        	tel: tel,
        	aream2: aream2,
        	rent: rent,
        	ontime: ontime,
        	offtime: offtime,
        	ontime1: ontime1,
        	offtime1: offtime1,
        	ontime2: ontime2,
        	offtime2: offtime2,
        	remark: remark,
        	noused: noused,
        	locano: locano
		};
	if($("#utwobarimage").hasClass("changed")){
		var base64str = $("#utwobarimage").data("base64str");
		var pict = {
				twobarimage: base64str
		};
		currentdata["pictparam"] = JSON.stringify(pict);
	}
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: currentdata,
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			try {
				if (valideData(data)) {
					var $dg = $("#gg");
					if (houseid == 0) {
						houseid = data.msg;
						$dg.datagrid('insertRow', {
							index: 0,
							row:{
            					ROWNUMBER : 1,
            					HOUSEID: houseid,
            					HOUSENAME: housename,
            					ADDRESS: address,
            					TEL: tel,
            					PRICETYPE: pricetype,
            					PRICETYPE1: pricetype1,
            					ONTIME : ontime,
            					ONTIME1 : ontime1,
            					ONTIME2 : ontime2,
            					OFFTIME :offtime,
            					OFFTIME1 :offtime1,
            					OFFTIME2 :offtime2,
            					REMARK : remark,
            					AREAM2: aream2,
            		        	RENT: rent,
            					LOCANO: locano,
            					NOUSED : '0'
            				}
						}).datagrid('refresh');
						var total = Number($("#pp_total").html());
						if (!isNaN(total)) $("#pp_total").html(total + 1);
						$("#uhousename").val("");
	            		$("#uaddress").val("");
	            		$("#utel").val("");
	            		$("#uontime").val("08:30");
	            		$("#uofftime").val("11:30");
	            		$("#uontime1").val("12:30");
	            		$("#uofftime1").val("17:30");
	            		$("#uontime2").val("18:30");
	            		$("#uofftime2").val("22:30");
	            		$("#uremark").val("");
	            		$("#ulocano").val("");
	            		$('#unoused').prop('checked','checked')
	            		$("#uhousename").focus();
					} else {
						$dg.datagrid('updateRow', {
							index: index,
							row: {
            					ROWNUMBER : 1,
            					HOUSEID :houseid,
            					HOUSENAME :housename,
            					ADDRESS :address,
            					TEL: tel,
            					PRICETYPE: pricetype,
            					PRICETYPE1: pricetype1,
            					ONTIME : ontime,
            					ONTIME1 : ontime1,
            					ONTIME2 : ontime2,
            					OFFTIME :offtime,
            					OFFTIME1 :offtime1,
            					OFFTIME2:offtime2,
            					REMARK: remark,
            					LOCANO: locano,
            					AREAM2: aream2,
            		        	RENT: rent,
            					NOUSED : noused
            				}
						}).datagrid('refresh');
						$("#ud").dialog("close");
					}
					if($("#utwobarimage").hasClass("changed")){
						$("#utwobarimage").removeClass("changed");
						$("#uimgview").hide();
						$("#pp").refreshPage();
					}
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
function disabledhouse(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var houseid = row.HOUSEID;
		var $check = $('#disabledcheckin_' + index);
		var noused = 0;
		if ($check.prop('checked')) noused = 1;
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "updatewarehousebyid",
				houseid: houseid,
				noused: noused
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				try {
					if (valideData(data)) {
						$dg.datagrid('updateRow', {
							index: index,
							row: {
								NOUSED: noused
							}
						});
					}
					$dg.datagrid('refresh');
				} catch(e) {
					// TODO: handle exception
					console.error(e);top.wrtiejsErrorLog(e);
				}
			}
		});
	}
}
//删除
function delhouse(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var houseid = row.HOUSEID;
		$.messager.confirm(dialog_title, '您确认要删除店铺' + row.HOUSENAME + '？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delwarehousebyid",
						houseid: houseid
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						try {
							if (valideData(data)) {
								$dg.datagrid("deleteRow", index).datagrid("refresh");
								var total = Number($("#pp_total").html());
								if (!isNaN(total)) $("#pp_total").html(total - 1);
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
function locanosearch(){
	$.ajax({
		type : 'POST', //访问WebService使用Post方式请求 
		url : '/skydesk/fyjerp', //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : 'houselocanolist'
		},
	dataType : 'json',
	success : function(data) {
		try {
			if (valideData(data)) {
				var rows = data.rows;
				var locanolist='';
				for(var i in rows){
					locanolist += '<option label="'+rows[i].LOCANO+'" value="'+rows[i].LOCANO+'"/>';
				}
				$('#locano_list').html(locanolist);
			}
		} catch (e) {
			// TODO: handle exception
			console.error(e);top.wrtiejsErrorLog(e);
		}
	}
	});
}
//设置登账
function setbookhoused(index){
	var rows = $("#gg").datagrid("getRows");
	var row = rows[index];
	var houseid = $("#uhouseid").val(row.HOUSEID);
	if (!$('#bookhouset').data().datagrid) {
		$('#bookhousepp').createPage({
			pageCount: 1,
			current: 1,
			backFn: function(p) {
				listbookhousepay(p.toString());
			}
		});
		$('#bookhouset').datagrid({
			idField: 'ID',
			height: 300,
			fitColumns: true,
			striped: true,
			//隔行变色
			singleSelect: true,
			pageSize: 20,
			columns: [[
			{
				field: 'ROWNUMBER',
				title: '序号',
				width: 40,
				fixed: true,
				align: 'center',
				halign: 'center',
				formatter: function(value, row, index) {
					var val = Math.ceil(Number(value) / pagecount) - 1;
					return isNaN(Number(value)) || value == "" ? "": val * pagecount + Number(index) + 1;
				}
			},{
				field: 'PAYNAME',
				title: '结算方式',
				width: 80,
				fixed: true,
				align: 'center',
				halign: 'center'
			},{
				field: 'BOOKNAME',
				title: '账本',
				width: 120,
				fixed: true,
				align: 'center',
				halign: 'center'
			},{
				field: 'SUBNAME',
				title: '记账项目',
				width: 80,
				fixed: true,
				align: 'center',
				halign: 'center'
			},{
				field: 'ACTION',
				title: '操作',
				width: 60,
				fixed: true,
				align: 'center',
				halign: 'center',
				formatter: function(value, row, index) {
					return '<input type="button" class="m-btn" value="删除" onclick="delbookhousepay(' + index + ')">';
				}
			}]],
			onSelect: function(index, row) {
				$("#ubookhouseindex").val(index);
			},
			onDblClickRow: function(rowIndex, rowData) {
				updatebookhoused();
			},
			toolbar: "#bookhousetoolbar"
		}).datagrid("keyCtr");
		$("#bookhousetoolbar ul li").click(function(){
			$this = $(this);
			if(!$this.hasClass("checked")){
				$("#bookhousetoolbar ul li").removeClass("checked");
				$this.addClass("checked");
				$("#bookhousepp").refreshPage();
			}
		});
	}
	$("#bookhouselb0").addClass("checked");
	$("#bookhouselb1").removeClass("checked");
	listbookhousepay(1);
	$("#bookhoused").dialog("open");
}
function listbookhousepay(page){
	var lbid = $("#bookhouselb0").hasClass("checked")?0:1;
	var houseid = $("#uhouseid").val();
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "listbookhousepay",
			houseid: houseid,
			lbid: lbid,
			rows: pagecount,
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$('#bookhousepp').setPage({
						pageCount: Math.ceil(Number(data.total) / pagecount),
						current: Number(page)
					});
					$('#bookhouset').datagrid('loadData', data);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
function addbookhoused(){
	$("#ubookhoused :input:not([type=button])").val("");
	$("#ubookhoused").dialog("setTitle","增加登账记录").dialog("open");
}
function updatebookhoused(){
	var index = $("#ubookhouseindex").val();
	var row = $("#bookhouset").datagrid("getRows")[index];
	if(row){
		$("#ubookhouseid").val(row.ID);		
		$("#ubookid").val(row.BOOKID);		
		$("#upayid").val(row.PAYID);		
		$("#usubid").val(row.SUBID);		
		$("#upayname").val(row.PAYNAME);		
		$("#ubookname").val(row.BOOKNAME);		
		$("#usubname").val(row.SUBNAME);
		$("#ubookhoused").dialog("setTitle","编辑登账记录").dialog("open");
	}else alerttext("选择无效！");
}
function selectsubd(ser){
	var lx = $("#bookhouselb0").hasClass("checked")?1:2;
	selectsub(ser,lx);
}
function savebookhousepay(){
	var bookhouseid = Number($("#ubookhouseid").val());
	var lbid = $("#bookhouselb0").hasClass("checked")?0:1;
	var payid = Number($("#upayid").val());
	var bookid = Number($("#ubookid").val());
	var subid = Number($("#usubid").val());
	var houseid = Number($("#uhouseid").val());
	var erpser = "addbookhousepay";
	if(bookhouseid>0) erpser = "updatebookhousepay";
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: erpser,
			lbid: lbid,
			payid: payid,
			bookid: bookid,
			subid: subid,
			houseid: houseid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			try {
				if (valideData(data)) {
					$("#bookhousepp").refreshPage();
					$("#ubookhoused").dialog("close");
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
function delbookhousepay(index) {
	var $dg = $("#bookhouset");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var id = row.ID;
		$.messager.confirm(dialog_title, '您确认要删除此条登账记录？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fybuyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delbookhousepay",
						id: id
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
							console.error(e);
						top.wrtiejsErrorLog(e);
						}
					}
				});
			}
		});
	}
}
</script>
<jsp:include page="../HelpList/ImportHelp.jsp"></jsp:include>
<jsp:include page="../HelpList/UploadImgHelp.jsp"></jsp:include>
<jsp:include page="../HelpList/PayWayHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/BookHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/SubItemHelpList.jsp"></jsp:include>
</body>
</html>