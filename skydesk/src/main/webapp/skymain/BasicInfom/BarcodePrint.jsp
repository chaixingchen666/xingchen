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
<title>条码打印</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
</head>
<body class="easyui-layout layout panel-noscroll" >
<!-- 单据列表 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
	<div class="fl">
	<table border="0" cellspacing="5" class="fl-ml10">
		<tr><td class="header" style="font-size:14px;">输入货号</td>
			<td id="wquickuwaretd"><input type="text" id="wquickuwareno"  class="edithelpinput" data-end="yes" data-value="printaddwarem"  placeholder="<选择或输入>">
			<span onclick="selectware('print')"></span>
			</td>
						<td>
			<label onclick="getbarcodeprintdata('1')"><input type="checkbox" id="waremsort" checked>录入排序</label>
			</td>
		</tr>
		</table>
		<input type="hidden" id="wquickuwareid" >
		</div>
	<div class="fr" id="warem-toolbar">
				<span class="seprate1"></span>
				<span><input class="btn5" type="button" id="selprint" value="选择打印端口" onclick="selprint()"></span>
				<span class="seprate1 hide"></span>
				<span><input class="btn5 hide" type="button" id="expprint" value="导出条码文件" onclick="expdia()"></span>
				<span class="seprate1"></span>
				<span><input class="btn5" type="button" id="printnote" value="载入单据" onclick="openprintnote()"></span>
				<span class="seprate1"></span>
				<span><input class="btn5" type="button" id="loadck" value="载入库存" onclick="openloadck()"></span>
				<span class="seprate1"></span>
				<span><input class="btn5" type="button" id="updatebarsale" value="设置吊牌价" onclick="updatebarsalet()"></span>
				<span class="seprate1"></span>
				<span><input class="btn5" type="button" id="print" value="后台打印"></span>
				<span class="seprate1"></span>
				<span><input class="btn5" type="button" id="updateware" value="〼编辑" onclick="updatewd()"></span>
				<span class="seprate1"></span>
				<span><input class="btn5" type="button" id="delware" value="-删除行" onclick="delbarcodeprintx()"></span>
				<span class="seprate1"></span>
				<span><input class="btn5" type="button" id="delwareall" value="-删除所有" onclick="clearwarebarprintsum()"></span>
	</div>
</div>

</div>

<!-- 商品明细表 -->
<table id="gg" style="overflow: hidden;"></table>
	<div class="page-bar">
	<div class="page-total" id="notetotal">共有0条记录</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>
<!-- （无折扣）快速编辑窗体 -->
<div id="quickud" title="修改商品明细" style="text-align: center;max-width:100%;max-height:100%;"  class="easyui-dialog" closed="true" data-options="onClose:function(){$('#wquickuwareid').val('');$('#wquickuwareno').focus();}">
<input type="hidden" id="wquickuid" >
<div style="width: auto;overflow: auto;text-align: center;position: relative;left: 0;top: 0;">
<form id="quickuaddwaref" action="" method="get" class="quickkeymsg">
  输入数量后：Ctrl+A：所有数量一致；Ctrl+S：设置之后同颜色一致；Ctrl+D：设置之后同尺码数量一致；Ctrl+Z:取消所有数量输入。
</form>
<form action="" method="get" id="quickutable">
</form>
</div>
<form id="changedp" action="" method="get">
<div class="dialog-footer" style="text-align: center;display: block;position: relative;">
	<span><input type="button" class="btn1" onclick="quickaddwarem()" value="保存"></span>
</div>
</form>
</div>
<!-- 设置吊牌价弹出窗-->
	<div id="updated" title="设置吊牌价" style="width: 400px; height: 250px;text-align: center;" class="easyui-dialog" closed="true">
		<table width="280" cellspacing="10" class="table1 mt14">
			<tr>
				<td width="70" align="right" class="header">吊牌价</td>
				<td width="150" align="left"><select id="pricetype"
					style="width: 144px; height: 25px">
						<option value="0" selected="selected">零售价</option>
						<option value="1">售价一</option>
						<option value="2">售价二</option>
						<option value="3">售价三</option>
						<option value="4">批发价</option>
						<option value="5">打包价</option>
				</select></td>
			</tr>
			<tr>
				<td align="right" class="header">提价比例</td>
				<td align="left"><input style="width: 144px;" class="hig25" id="rate" type="text" data-options="required:true"
					 placeholder="0.00" ></td>
			</tr>
			<tr>
				<td align="right" width="70" class="header">尾数处理</td>
				<td width="150" align="left"><select id="wscl"
					style="width: 144px; height: 25px">
						<option value="0" selected="selected">四舍五入</option>
						<option value="1">尾数为8</option>
						<option value="2">尾数为6</option>
						<option value="3">尾数为0</option>
				</select></td>
			</tr>
			<tr>
				<td colspan="2" class="dialog-footer textcenter"><input class="btn1"
					type="button" onclick="updatebarsale();" value="确定" />
					<input class="btn1" type="button" value="取消" onclick="qxupdatebarsale()" /></td>
			</tr>
		</table>
	</div>
	
	<div id="loadkcds" title="载入库存" style="width: 500px; height: 410px;" data-options="modal:true" class="easyui-dialog" closed="true" data-qickey>
<div class="textcenter">
<input type="hidden" id="lwareid">
<input type="hidden" id="lbrandid">
<input type="hidden" id="ltypeid">
<table class="table1" border="0" cellspacing="5" cellpadding="5">
  <tr>
    <td width="67" class="header" align="right">货号</td>
    <td width="170" align="right"><input class="edithelpinput wareno_help" data-value="#lwareid" type="text" id="lwareno" placeholder="<选择或输入>"><span onclick="selectware('load')"></span></td>
  </tr>
  <tr>
    <td width="67" class="header" align="right">货位</td>
    <td width="170" align="right"><input class="hig25 wid160" type="text" id="locale" placeholder="<输入>"></td>
  </tr>
    <tr>
    <td width="67" class="header" align="right">品牌</td>
    <td width="170" align="right"><input class="edithelpinput brand_help" data-value="#lbrandid" type="text" id="lbrandname" placeholder="<选择或输入>"><span onclick="selectbrand('load')"></span></td>
  </tr>
  <tr>
 	 <td width="67" class="header" align="right">店铺</td>
	 <td  width="170" align="right"><input type="text" class="edithelpinput house_help" data-value="#shouseid"
					name="shousename" id="shousename" data-options="required:true" style="width:125px;height:24px;"
					placeholder="<选择或输入>" ><span onclick="selecthouse('analysis')" ></span><input type="hidden" id="shouseid" value=""></td>
  </tr>
  <tr>
 	 <td  width="67" class="header" align="right">生产商</td>
	 <td  width="170" align="right" ><input class="edithelpinput provd_help" maxlength="20" style="width:125px;height:24px;" data-value="#sprovid" placeholder="<选择或输入>"
					 type="text" id="sprovname" name="sprovname"><span onclick="selectprov('search')"></span>
						<input type="hidden" id="sprovid" name="sprovid"> </td>
  </tr>
  <tr>
    <td class="header" align="right">类型</td>
    <td align="right"><input class="edithelpinput" type="text" id="lwaretype" readonly><span onclick="selectwaretype('load')" placeholder="<输入>"></span></td>
  </tr>
  <tr id="centdivm">
    <td class="header" align="right">季节</td>
    <td align="right">
<input  type="text" class="edithelpinput season_help" name="lseasonname" id="lseasonname" maxlength="20" placeholder="<选择或输入>">
<span onclick="opencombox(this)"></span>
</td>
  </tr>
  <tr>
    <td class="header" align="right">生产年份</td>
    <td align="right"><input class="hig25 wid160" type="text" id="prodyear" placeholder="<输入>"></td>
  </tr>
  <tr>
  <td colspan="2">
  <label>
		<input type="checkbox" name="loadbj" id="loadbj" value="1">清空原数据
		</label>
  </td>
  </tr>
</table>
</div>
<div class="dialog-footer">
<input type="button" class="btn1" value="确定" onclick="loadkc();" />
<input type="button" class="btn2" value="取消" onclick="$('#loadkcds').dialog('close')" />
</div>
</div>

<div id="printnotecd" title="载入单据" style="width: 1000px; height: 470px;padding-left:5px;" data-options="modal:true" class="easyui-dialog" closed="true" data-qickey>
	<div  class="toolbar" id="printnote_tolbars">
		<table cellspacing="5">
			<tr>
				<td><input class="hig25" style="width:220px"  type="text" id="findbox" data-enter="printnotelist('1')" placeholder="搜索单据号,店铺<回车搜索>"></td>
			<td>
			<input type="text" name="mindate" id="mindate" placeholder="<输入>" style="width: 100px;height:29px" class="easyui-datebox">
			-<input type="text" name="maxdate" id="maxdate" placeholder="<输入>" style="width: 100px;height:29px" class="easyui-datebox">
			</td>
				<td>单据类别</td>
				<td><select  id="notetype" style="width:160px;height:25px;">
		  			<option value="0">采购入库</option>
		  			<option value="1">期初入库</option>
		  			<option value="2">临时盘点</option>
		  			<option value="3">调出</option>
		          	<option value="4">调入</option>
		  			<option value="5">批发开票</option>
		  			<option value="6">批发退货</option>
		  			<option value="7">客户订单</option>
		  			<option value="8">采购订单</option>
		            <option selected="selected" value="9">所有</option>
  				</select></td>
  				<td ><input class="btn2" type="button" value="搜索" onclick="printnotelist('1')"></td>
			</tr>
		</table>
	</div>
	<!-- 单据列表 -->
		<table id="printnotegg" style="overflow: hidden;"></table>
		
		<div class="page-bar">
		<div class="page-total" id="printnotetotal">共有0条记录</div>
		<div id="printnotepp" class="tcdPageCode page-code" style="float: right;">
		</div>
			<div class="dialog-footer" style="padding-left:10px;float: right;padding-right:10px;width: 930px;">
				<span style="float:left;"><input type="checkbox" id="clearall"  checked>清除原数据</span>
				<input type="button" class="btn1" value="确定" onclick="printnotelistd()"/>
				<input type="button" class="btn2" value="取消" onclick="$('#printnotecd').dialog('close')"/> 
			</div>
		</div>
</div>


<!-- 导出文件弹窗 -->
	<div id="expdia" title="导出确认"
		style="width: 260px; height: 160px; text-align: center;"
		class="easyui-dialog" closed="true">
		<table cellspacing="10" class="table1" width="200px";>
			<tr align="center">
				<td align="center" colspan="2">确认导出:条码打印文件.xls?</td>
			</tr>
			<tr align="center">
				<td align="center"><input type="checkbox" id="idbj">产生唯一条码</td>
			</tr>
			<tr>
				<td align="center"><input class="btn1" value="确定"
					type="button" onclick="exp();" />
					<input class="btn1" type="button"
					value="取消" onclick="$('#expdia').dialog('close')" /></td>
			</tr>
		</table>
	</div>
<script type="text/javascript">

var levelid = Number(getValuebyName("GLEVELID"));
//店铺id 
var houseid=getValuebyName('HOUSEID');
var pgid = 'pg1015';//标记选择端口功能
var exptotal=0;//导出专用
setqxpublic();
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize=function(){  
     changeDivHeight();  
}  
function changeDivHeight(){               
  var height = document.documentElement.clientHeight;//获取页面可见高度 
	$('#gg').datagrid('resize',{
  	height:height-80
  });
}
var sizenum = getValuebyName("SIZENUM");
var cols = Number(sizenum)<=5?5: Number(sizenum);
	function setColums() {
		var colums = [];	
		colums[0] = {
				field : 'ROWNUMBER',
				title : '序号',
				fixed : true,
				width : 30,
				halign : 'center',
				align :'center',
				formatter : function(value,row,index){
					return value==""?"":index+1;
				}
			};
		colums[1] = {
			field : 'WAREID',
			title : 'WAREID',
			hidden : true,
			width : 10
		};
		colums[2] = {
			field : 'WARENO',
			title : '货号',
			width : 7,
			halign : 'center',
			styler: function(value,row,index){
				if(value=="合计"){
					return 'text-align:center';
				}else{
					return 'text-align:left';
				}
			}
		};
		colums[3] = {
				field : 'WARENAME',
				title : '商品名称',
				width : 12,
				halign : 'center',
				align :'left'
			};
		colums[4] = {
			field : 'UNITS',
			title : '单位',
			width : 40,
			fixed : true,
			halign : 'center',
			align :'center'
		};
		colums[5] = {
				field : 'COLORID',
				title : '颜色id',
				width : 20,
				hidden : true
				
			};
		colums[6] = {
			field : 'COLORNAME',
			title : '颜色',
			width : 6,
			halign : 'center',
			align :'center'
		};
		for (var i = 1; i <= cols; i++) {
			colums[6 + i] = {
				field : 'AMOUNT' + i,
				title : '<span id="title'+i+'"><span>',
				width : 35,
				fixed:true,
				halign : 'center',
				align :'center',
				formatter : function(value,row,index){
					return value==''?'':Number(value)==0?'':value;
				}
			};
		}
		colums[cols + 7] = {
			field : 'AMOUNT',
			title : '小计',
			width : 50,
			fixed:true,
			halign : 'center',
			align :'center'
		};
		colums[cols + 8] = {
			field : 'BARSALE',
			title : '吊牌价',
			width : 7,
			halign : 'center',
			align :'right',
			formatter : function(value,row,index){
				return value==undefined?'':Number(value).toFixed(2);
			}
		};
		colums[cols + 9] = {
			field : 'RETAILSALE',
			title : '零售价',
			width : 10,
			halign : 'center',
			align : 'center',
			formatter : function(value,row,index){
				return value==undefined?'':(Number(value).toFixed(2));
			}
		};
		for (var i = 1; i <= cols; i++) {
			colums[cols + 9 + i] = {
				field : 'SIZEID' + i,
				title : 'sizeid',
				hidden : true,
				width : 10
			};
		}
		for (var i = 1; i <= cols; i++) {
			colums[cols + cols + 9 + i] = {
				field : 'SIZENAME' + i,
				title : 'sizename',
				hidden : true,
				width : 10
			};
		}
		return colums;
		}
$(function(){
	var housename = getValuebyName("HOUSENAME");
	var houseid = Number(getValuebyName("HOUSEID"));
	if(levelid==1||levelid==2||levelid==7){
		$('#ln_housename').next().hide();
		$('#ln_housename').attr('readonly',true);
	}
	$('#ln_housename').val(housename);
	$('#ln_houseid').val(houseid);
	$("#pp").createPage({
		pageCount:1,//总页码
		current:1,//当前页 
        backFn:function(p){
        	rownumbers=p;
        	getbarcodeprintdata(p.toString());
        }
	 });
	$("#printnotepp").createPage({
		pageCount:1,//总页码
		current:1,//当前页 
        backFn:function(p){
        	printnotelist(p.toString());
        }
	 });
	setaddbackprint("1015", {
		id: "top.skyepid",
		noteno: "top.skyepid"
	});
	setbarcodeprint();
	getbarcodeprintdata("1");
	//默认3天
	var myDate = new Date(top.getservertime());
	$('#mindate').datebox('setValue', datetrans(myDate, 3, 0)); // 设置日期输入框的值
	$('#maxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
});
function openloadck(){//载入库存弹窗
	$('#loadkcds').dialog('open');
}
function openprintnote(){//载入单据弹窗
	$('#printnotecd').dialog({
		modal : true,
		title : '单据列表'
	});
	if($('#clearall').prop('checked')){
		$('#clearall').prop('checked',false);
	}
	
	$('#printnotecd').dialog('open');
	printnotelist("1");
	
}
//查询单据列表 
function printnotelist(page){
	var mindate = $('#mindate').datebox('getValue');
	var maxdate = $('#maxdate').datebox('getValue');
	var notetype = $('#notetype').val();
	var houseid =  $('#ln_houseid').val();
	var findbox=$('#findbox').val();
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "listwarebarprintnote",
			mindate: mindate,
			maxdate: maxdate,
			notetype : notetype,
			houseid: houseid,
			findbox : findbox,
			rows: pagecount,
			page : page
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			try{if (valideData(data)) {
				var totals = data.total;
				$("#printnotepp").setPage({
			        pageCount:Math.ceil(Number(totals)/ pagecount),
			        current:Number(page)
				 });
				$('#printnotegg').datagrid('loadData', data);
				$('#printnotetotal').html('共有'+totals+'条记录');
				$('#printnotegg').datagrid('scrollTo',0);
			}
			}catch(err){
				console.error(err.message);
			}
		}
	});
}
//载入指定单据
function printnotelistd(){
	var loadbj;
	var msg = "确定追加载入单据？";
	if($('#clearall').prop('checked')){
		loadbj = '0';
		msg = "确定载入单据并且清除原有数据吗？"
	}
	else{
		loadbj = '1';
	}
	var rowdata= $('#printnotegg').datagrid('getSelected');
	if(!rowdata){
		alerttext("请选择有效单据");
		return;
	}
	$.messager.confirm(dialog_title,msg,function(r){    
		if (r){ 
			alertmsg(2);
			var notetype=rowdata.NOTETYPE;
			var noteno=rowdata.NOTENO;
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser : "loadwarebarprintnote",
					notetype : notetype,
					loadbj: loadbj,
					noteno : noteno
				}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType : 'json',
				success : function(data) { //回调函数，result，返回值 
					try{
						if(valideData(data)) {
							getbarcodeprintdata("1");
							$('#printnotecd').dialog('close');
						}
					}catch(err){
						console.error(err.message);
					}
				}
			});
		}
	});
}
//载入库存
function loadkc(){
	$('#loadkcds').dialog('close');
	var wareid = $('#lwareid').val();
	var typeid = $('#ltypeid').val();
	var brandid = $('#lbrandid').val();
	var seasonname = $('#lseasonname').val();
	var prodyear = $('#prodyear').val();
	var provid = $('#sprovid').val();//生产商
	var houseid = $('#shouseid').val();//店铺
	var locale = $('#locale').val();//货位
	brandid = brandid==""?"-1":brandid;
	typeid = typeid==""?"-1":typeid;
	provid = provid==""?"-1":provid;
	var loadbj;
	if($('#loadbj').prop('checked'))
		loadbj = '0';
	else 
		loadbj = '1';
	alertmsg(6);
	$.ajax({ 
       	type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
        data: {
        	erpser: "warebarprintloadkc",
        	houseid : Number(houseid),
        	wareid : Number(wareid),
        	typeid : typeid,
        	brandid : brandid,
        	provid: provid,
        	loadbj: loadbj,
        	locale:locale,
        	seasonname : seasonname,
        	prodyear : prodyear
        	//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        },
        dataType: 'json', 
        success: function(data) {     //回调函数，result，返回值 
        	try{if (valideData(data)) {
        		getbarcodeprintdata("1");
        	}
        	}catch(err){
				console.error(err.message);
			}
        }                 
        });
}
//查询列表 
function getbarcodeprintdata(page){
	alertmsg(6);
	var sortid = $('#waremsort').prop("checked")?1:0;
	$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			async:false,
			data : {
				erpser : "warebarprintsumlist",
				sortid: sortid,
				page:page,
				rows: pagecount,
				sizenum:sizenum
			}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType : 'json',
			success : function(data) { //回调函数，result，返回值 	
				try{if(valideData(data)) {
					var totals = data.total;
					var totalamt = data.totalamt;
					exptotal=totals;
					$("#pp").setPage({
				        pageCount:Math.ceil(Number(totals)/ pagecount),
				        current:Number(page)
					 });
					dqzamt = totalamt;
					var footer = [{ROWNUMBER:"",WARENO:"合计",AMOUNT:dqzamt}];
					$('#gg').datagrid('loadData', data);
					$('#gg').datagrid('reloadFooter',footer);
					$('#notetotal').html('共有'+totals+'条记录');
				}
				}catch(err){
					console.error(err.message);
				}
			}
		});
}
var pg=1;
var sumpg=1;
//加载商品明细表
function setbarcodeprint() {
	$('#gg').datagrid({
			idField : 'ID',
			width:'100%',
			height :$('body').height()-45,
			fitColumns : true,
			striped : true, //隔行变色
			showFooter:true,
			nowrap :false,//允许自动换行
			singleSelect : true,
			onSelect : function(rowIndex, rowData) {
					for(var i=1;i<=cols;i++){
						$('#title'+i).html(eval('rowData.SIZENAME'+i));
				}
					dqindex = rowIndex;
					dqamt = rowData.AMOUNT;
			},
			onLoadSuccess: function(data){
				var sizelength = 0;
				var rows = data.rows;
				var $dg = $('#gg');
				for(var i=0;i<rows.length;i++){
					var item= rows[i]; 
					while(sizelength<=sizenum){
						var s = sizelength+1;
						if(item["SIZENAME"+s]!=""&&item["SIZENAME"+s]!=undefined)
							sizelength++;
						else
							break;
					}
				}
				for(var i=1;i<=sizenum;i++){
					if(i<=sizelength)
						$dg.datagrid("showColumn","AMOUNT"+i);
					else
						$dg.datagrid("hideColumn","AMOUNT"+i);
				}
				$dg.datagrid('resize');
				},
			onDblClickRow : function(rowIndex, rowData) {
				updatewd();
				},
			columns : [setColums()],
			pageSize : 20,
			toolbar : '#toolbars'
			}).datagrid("keyCtr","");
	
	$('#printnotegg').datagrid({
		idField : 'NOTENO',
		height :330,
		fitColumns : true,
		striped : true, //隔行变色
		showFooter:true,
		nowrap :false,//允许自动换行
		singleSelect : true,
		onSelect : function(rowIndex, rowData) {
				
		},
		onDblClickRow : function(rowIndex, rowData) {
			printnotelistd();
			},
		columns : [[
			{
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
			},{
					field : 'NOTETYPE',
					title : '单据type',
					hidden : true,
					halign : 'center',
					align :'center',
					width : 10
			},{
					field : 'NOTETYPENAME',
					title : '单据类型',
					halign : 'center',
					align :'center',
					width : 80,
					fixed: true
				},{
					field : 'NOTENO',
					title : '单据号',
					halign : 'center',
					align :'center',
					width : 120,
					fixed: true
				},
				{
					field : 'NOTEDATE',
					title : '日期', 
					halign : 'center',
					align :'center',
					width : 100,
					fixed: true
				},
				{
					field : 'WLDW',
					title : '往来单位',
					halign : 'center',
					align :'left',
					width : 150,
					fixed: true
				},
				{
					field : 'HOUSENAME',
					title : '店铺',
					halign : 'center',
					align :'center',
					width : 120,
					fixed: true
				},
				{
					field : 'TOTALAMT',
					title : '数量',
					width : 80,
					fixed: true,
					halign : 'center',
					align :'center',
					formatter : function(value,row,index){
						return value=='0'||value=='0'||value==undefined?'':Number(value).toFixed(0);
					}
				
				},
				{
					field : 'REMARK',
					title : '摘要',
					halign : 'center',
					align :'left',
					width : 150,
					fixed: true
				},
				{
					field : 'HANDNO',
					title : '自编号',
					halign : 'center',
					align :'center',
					width : 50,
					fixed: true
				},
				{
					field : 'OPERANT',
					title : '制单人',
					halign : 'center',
					align :'center',
					width : 80,
					fixed: true
				}
			]],
		pageSize : 20,
		toolbar: "#printnote_tolbars"
		}).datagrid("keyCtr", "");
	}
	
function quickupdatesum(){
	var list = $('#quickutables tr:eq(1) input[type=text]').length;//横向文本框数
	var line = $('#quickutables tr').length-2;//竖向文本框数
	var totalamount = new Decimal(0);
	var totalcurr = new Decimal(0);
	var count=0;
	var amount1 = new Array(line);
	var amount2 = new Array(list+3);
	var quickjsonstr =new Array();
	if(line>=1&&list>=1){
		for(i=1;i<=line;i++){
			amount1[i]=new Decimal(0);
			for(j=1;j<=list+1;j++){
				if( j!=list+1 ){
					var value = Number($('#u'+i.toString() + "-" + j.toString()).val()==undefined?0:$('#u'+i.toString() + "-" + j.toString()).val()==''?0:$('#u'+i.toString() + "-" + j.toString()).val());
					value = isNaN(value)?0:value;
					if(value!=0){
						quickjsonstr.push({
								colorid:$('#uacolor'+i).val(),
								sizeid:$('#uasize'+j).val(),
								amount:value,
								barsale:$('#upri'+i).val(),
								curr:Number($('#upri'+i).val())*Number(value)
						});
						count=count+1;
						amount1[i] = amount1[i].add(value);
						totalamount = totalamount.add(value);	
					}
				}else{
					$('#usum'+i).html(amount1[i].valueOf());
					var curr = Number((amount1[i]*Number($('#upri'+i).val())).toFixed(points));
					$('#uacurr'+i).html(allowinsale=='1'?curr.toFixed(2):"***");
					totalcurr = totalcurr.add(curr);
				}
			}	
		}
		for(i=1;i<=list;i++){
			amount2[i]=new Decimal(0);
			for(j=1;j<=line+1;j++){
				if( j!=line+1 ){
					var value = Number($('#u'+j.toString() + "-" + i.toString()).val()==undefined?0:$('#u'+j.toString() + "-" + i.toString()).val()==''?0:$('#u'+j.toString() + "-" + i.toString()).val());
					if(value!=0){
						amount2[i] = amount2[i].add(value);		
					}
				}else if(j==line+1){
					var a = amount2[i].valueOf();
					$('#asum'+i).html(a==0?"":a);
				}
			}	
		}
	}
	$('#asum'+(list+1).toString()).html(totalamount.valueOf());
	$('#asum'+(list+3).toString()).html(totalcurr.toFixed(2));
	jsondata = quickjsonstr;
}

var hasTable=false;
function getbarcodeprintsum(wareid){
	$('#wquickuwareid').val(wareid);
	$('#quickutable').html('');
	if($('#quickutables').length==0&&hasTable==false){
		hasTable=true;
		alertmsg(2);
		$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "getwarebarprintsum",
			wareid:wareid
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			try{if(valideData(data)) {
				var wareno = data.WARENO;
				var warename = data.WARENAME;
				var retailsale = data.RETAILSALE;
				var price = retailsale;
				var defaultdisc = $('#udiscount').val();
				var colorlist = data.colorlist;
				var sizelist = data.sizelist;
				var amountlist = data.amountlist;
				var colorid;
        		var sizeid;
        	     //获取hang数值
        	    var line= getJsonLength(colorlist);
        	     //获取lie数值
        	    var list= getJsonLength(sizelist);
        	    if(line==0){
        	    	alerttext("该商品未选择任何可用商品颜色，请在商品档案中选择该商品颜色");
        	    	hasTable=false;
        	    	return;
        	    }
        	    if(list==0){
        	    	alerttext("该商品未选择尺码组，请在商品档案中选择该商品尺码组");
        	    	hasTable=false;
        	    	return;
        	    }
        	    var table=document.createElement("table");
        		table.id = "quickutables";
        		table.cellSpacing=0;
        	     for(var i=0;i<=line+1;i++){
        	         //alert(line);
        	         //创建tr
        	         var tr=document.createElement("tr");
        	         var curr=0;
        	         var amt=0;
        	         var price0 = price;
        	         for(var j=0;j<=list+2;j++){
        	             //alert(list);
        	             //创建td
        	              var td=document.createElement("td");
        	             if(i==0){
        	            	 tr.className="table-header",
        	            	 td.style.border="none";
        	            	 if(j==0){
        	            		 td.style.width='80px';
        	            		 td.innerHTML='颜色';
        	            	 }else if(j==list+1){
        	            		 td.style.width='60px';
        	            		 td.align="center";
            	               	 td.innerHTML = "小计"; 
        	            	 }
        	            	 else if(j==list+2){
        	            		 td.style.width='80px';
            	               	 td.innerHTML = "吊牌价"; 
        	            	 }else{
            	            	 td.style.width='50px';
        	            		 var input = document.createElement("input");
        	            		 input.type = "hidden";
        	            		 input.id="uasize"+j;
        	            		 input.value = sizelist[j-1].SIZEID; 
        	            		 td.innerHTML = sizelist[j-1].SIZENAME;
        	            		 td.appendChild(input);
        	            	 }
        	             }else if(i==line+1){
        	            	tr.style.fontWeight=600;
        	              	if(j==0){
            	               	td.innerHTML = "合计"; 
        	            	}else if(j==(list+3)){
        	            		td.align="right";
            	               	td.id="asum"+j;
                	            td.innerHTML = ""; 
        	            	}else{
        	            		td.align="center";
            	               	td.id="asum"+j;
                	            td.innerHTML = ""; 
        	            	}
    	            	 }else{
        	            	 if(j==0){
        	            		 var input = document.createElement("input");
        	            		 input.type = "hidden";
        	            		 input.id="uacolor"+i;
        	            		 input.value = colorlist[i-1].COLORID; 
        	                	 td.innerHTML = colorlist[i-1].COLORNAME; 
        	                	 td.appendChild(input);
        	            	 }else if(j==list+1){
        	            		 td.id="usum"+i;
        	            		 td.align="center";
        	            		 td.innerHTML = amt;
        	            	 }else if(j==list+2){
        	            		 var input = document.createElement("input");
        	            		 input.id="upri"+i;
        	            		 input.style.textAlign="right";
        	            		 input.style.border="0";
        	            		 input.value = Number(price0).toFixed(2);
        	            		 td.appendChild(input);
        	            	 }else{
        	            		 var input = document.createElement("input");
        	                     input.type="text";
        	                     input.className="reg-amt";
        	                     input.style.width="95%";
        	                     input.style.textAlign="center";
        	                     input.maxLength=5;
        	                     input.style.border="none";
        	                     input.style.imeMode = 'disabled';
        	                     input.autocomplete = 'off';
        	                     sizeid = sizelist[j-1].SIZEID;
        	                     colorid = colorlist[i-1].COLORID;
        	                     if(getJsonLength(amountlist)==0){
        	                    	 input.placeholder='';
        	                     }else{
            	                     for(r in amountlist){
            	                    	 var colorids = amountlist[r].COLORID;
            	                    	 var sizeids = amountlist[r].SIZEID;
            	                    	 if(colorids==colorid&&sizeid==sizeids){
            	                    		 var val = input.value==''?0:Number(input.value);
            	                    		 input.value = val + Number(amountlist[r].AMOUNT);
            	                    		 price0 = Number(amountlist[r].BARSALE);
            	                    		 amt = Number(amountlist[r].AMOUNT) + amt;
                	                    	 curr = curr + Number(amountlist[r].CURR);
            	                    		 break;
            	                    	 }else{
            	                    		 input.placeholder='';
            	                    	 }
            	                     } 
        	                     }
        	                 	input.id= 'u' + i.toString() + "-" + j.toString();
	        	               	input.setAttribute("data-colnum",j);
           	            		input.setAttribute("data-rownum",i);
        	                     	td.appendChild(input);
        	                    }
        	             }
        	        	 	tr.appendChild(td);
        	             }
        	        	 table.appendChild(tr);
        	         }
        	     if($('#quickutables').length==0){
        	    	document.getElementById("quickutable").appendChild(table);
        	     }   
        		//autocreate(colorlist,sizelist);
        	quickupdatesum();
        	var title="修改商品明细："+wareno+"，"+warename;
        	 $('#quickud').dialog({
        		title : title,
        		width : list*51+360,
 				height : line*35+175,
 				modal : true
 			})
 			$('#quickud').window('center');
            $('#quickud').dialog('open');
     		var out_wid = $('#quickud').width();
     		$('#quickutables').width(out_wid-40);
 			$('#u1-1').focus();
			}
			}catch(err){
				console.error(err.message);
			}
			hasTable=false;
		}
	});
	}
}

function quickaddbarcodeprint() {
	var wareid = $('#wquickuwareid').val();
	if(wareid==undefined||wareid==""){
		alerttext("请重新选择货号！");
	}else{
		alertmsg(2);
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyjerp", //调用WebService的地址和方  法名称组合 ---- WsURL/方法名 
			data : {
				erpser : "writewarebarprintsum",
				wareid : wareid,
				rows : JSON.stringify(jsondata)
			}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType : 'json',
			success : function(data) { //回调函数，result，返回值 
				try{if(valideData(data)) {
					$('#quickuaddwaref')[0].reset();
					$('#quickutable').html('');
					$('#wquickuwareno').val('');
					getbarcodeprintdata("1");
					$("#wquickuwareno").parent().children("ul").html("");
					$("#wquickuwareno").parent().children("ul").hide();
					$('#quickud').dialog('close');
					$('#wquickuwareno').focus();
				}
				}catch(err){
					console.error(err.message);
				}

			}
		});
	}
}
//编辑商品框
function updatewd(){
	$('#quickuaddwaref')[0].reset();
	$('#quickutable').html('');
	var arrs = $('#gg').datagrid('getSelected');
	if (arrs==null) {
		alerttext('请选择一行数据，进行编辑');
	} else {
		$('#wquickuwareid').val(arrs.WAREID);
		getbarcodeprintsum(arrs.WAREID);
	}
}
	//修改商品明细保存 F4
	function quickaddwarem() {
		var wareid = $('#wquickuwareid').val();
		if(wareid==undefined||wareid==""){
			alerttext("请重新选择货号！");
		}else{
			quickupdatesum();
			alertmsg(2);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyjerp", //调用WebService的地址和方  法名称组合 ---- WsURL/方法名 
				data : {
					erpser : "writewarebarprintsum",
					wareid : wareid,
					rows : JSON.stringify(jsondata)
				}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType : 'json',
				success : function(data) { //回调函数，result，返回值 
					try{if(valideData(data)) {
						$('#quickuaddwaref')[0].reset();
						$('#quickutable').html('');
						$('#wquickuwareno').val('');
						getbarcodeprintdata('1');
						$("#wquickuwareno").parent().children("ul").html("");
						$("#wquickuwareno").parent().children("ul").hide();
						$('#quickud').dialog('close');
						$('#wquickuwareno').focus();
					}
					}catch(err){
						console.error(err.message);
					}

				}
			});
		}
	};
	//删除
	function delbarcodeprintx(){
		var noteno = $('#unoteno').val();
		var rowData = $('#gg').datagrid("getSelected");
		var rows=Number($('#gg').datagrid('getRows').length);
		if(rows==0){
			alerttext("记录为空，请添加数据！"); 
		}else{
			if(rowData){
				var wareid = rowData.WAREID;
				var colorid = rowData.COLORID;
				$.messager.confirm(dialog_title,'您确认要删除'+rowData.WARENO+'，'+rowData.WARENAME+'，'+rowData.COLORNAME+'吗？',function(r){    
					if (r){ 
					alertmsg(2);
					$.ajax({
						type : "POST", //访问WebService使用Post方式请求 
						url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
						data : {
							erpser : "delwarebarprintsum",
							noteno : noteno,
							wareid:wareid,
							colorid:colorid
						}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
						dataType : 'json',
						success : function(data) { //回调函数，result，返回值 
							try{if(valideData(data)) {
								$('#gg').datagrid('deleteRow',dqindex);
								dqzamt = Number(dqzamt) -  Number(dqamt);
								$('#gg').datagrid('updateFooter',{
									AMOUNT : dqzamt
								});
							}
							}catch(err){
								console.error(err.message);
							}
						}
					});
				}
				});
			}else{
				alerttext('请选择一行数据，进行编辑');
			}
		}
	}
	//删除所有
	function clearwarebarprintsum(){
		var rows=Number($('#gg').datagrid('getRows').length);
		if(rows==0){
			alerttext("记录为空，请添加数据！"); 
		}else{
			$.messager.confirm(dialog_title,'您确认要删除当前所有条码吗？',function(r){    
				if (r){ 
				alertmsg(2);
				$.ajax({
					type : "POST", //访问WebService使用Post方式请求 
					url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data : {
						erpser : "clearwarebarprintsum"
					}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType : 'json',
					success : function(data) { //回调函数，result，返回值 
						try{if(valideData(data)) {
							getbarcodeprintdata('1');
						}
						}catch(err){
							console.error(err.message);
						}
					}
				});
			}
			});
		}
	}
	
	//批量修改吊牌价弹窗
	function updatebarsalet(){
		$('#updated').dialog({
			modal:true,
			title:'设置吊牌价'  
		});
		$('#updated').dialog('open');
	}
	//取消修改
	function qxupdatebarsale(){
		$('#updated').dialog('close');
	}
	//批量修改吊牌价
	function updatebarsale(){
		$('#updated').dialog('close');
		var pricetype=$("#pricetype").val();//吊牌价
		var rate=$("#rate").val();//提价比例
		var wscl=$("#wscl").val();//尾数处理方式
		$.ajax({
			type:"POST",
			url:"/skydesk/fyjerp",
			data:{
				erpser : "changewarebarprintprice",
				pricetype : pricetype,
				rate : rate,
				wscl : wscl
			},
			dataType:'json',
			success:function(data){
				try{if (valideData(data)) {
					var result=data.result;
					if(result=='1'){
						alert(data.msg);
						getbarcodeprintdata('1');
					}else{
						alert(data.msg);
					}
				}
				}catch(err){
					console.error(err.message);
				}
			}
		});
	}
	function expdia(){
		var rows=Number($('#gg').datagrid('getRows').length);
		if(rows==0){
			alert('数据为空，不能导出！');
		}else{
			$('#expdia').dialog('open');
		}
	}
	//导出 
	function exp(){
		var idbj="0";
		if($("#idbj").prop("checked")==true){
			idbj="1";
		}else{
			idbj="0";
		}
		$('#expdia').dialog('close');
			var url = 'BarcodePrintServlet?barcodeprintser=expprint&page=1&idbj='+idbj;
			top.window.location.href = encodeURI(url);
			alert('正在努力导出，请不要重复操作！关闭此弹窗，请等待'+Math.ceil(exptotal/pagecount)+'秒左右，等待下载框弹出！');
	}
</script>

<!-- 商品帮助列表 -->
<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
<!-- 店铺帮助列表 -->
<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
<!-- 类型帮助列表 -->
<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
<!-- 品牌帮助列表 -->
<jsp:include page="../HelpList/BrandHelpList.jsp"></jsp:include>

<!-- 生产商帮助列表 -->
<jsp:include page="../HelpList/ProvHelpList.jsp"></jsp:include>
<!-- 端口帮助列表 -->
<jsp:include page="../HelpList/PrintcsHelp.jsp"></jsp:include>
</body>
</html>