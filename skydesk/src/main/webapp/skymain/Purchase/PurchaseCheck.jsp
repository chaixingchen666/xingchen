<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">
<meta name="renderer" content="webkit|ie-comp|ie-stand" /> 
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>采购核价</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/easydropdown.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
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
<body class="easyui-layout layout panel-noscroll">
	<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
<div class="fl">
				<input type="text" placeholder="搜索货号<回车搜索>" data-enter="getpurchasecheck('1')" data-end="yes" class="ipt1" id="qsnotetvalue" maxlength="20" >
				<input type="button" id="highsearch" class="btn2" value="条件搜索" onclick="toggle()">
				<input id="csearch" class="btn1 hide" type="button"  onclick="pp();toggle();" value="查找" />
				<input id="qclear" class="btn1 hide" type="button" value="清除内容"  onclick="clearAll()"/>
</div>
<!-- 				<input class="btn1" type="button" value="刷新" /> -->
</div>
	<!-- 隐藏查询条件 -->
<div id="hsearch"  style="background-color: white;display:none; font-size:12px;clear:both;width:100%;"  data-enter="pp();toggle();">
<div class="search-box">
<div class="s-box"><div class="title">开始日期</div>
<div class="text"><input id="smindate" editable="true" type="text" class="easyui-datebox" required="required" currentText="today"  style="width:164px;height:25px"></input>
</div>
</div>
				
<div class="s-box"><div class="title">结束日期</div>
<div class="text"><input id="smaxdate" editable="true" type="text" class="easyui-datebox" required="required" currentText="today"  style="width:164px;height:25px"></input>
</div>
</div>
<div class="s-box"><div class="title" color="#ff7900">*&nbsp;供应商</div>
<div class="text"><input class="edithelpinput provd_help" type="text" id="sprovname" name="sprovname" placeholder="<选择或输入>"
						data-value="#sprovid" maxlength="20"><span onclick="selectprov('search')"></span>
						<input type="hidden" id="sprovid" name="sprovid">
</div>
</div>
<div class="s-box"><div class="title" color="#ff7900">*&nbsp;店铺</div>
<div class="text"><input type="text"
					 class="edithelpinput house_help" data-value="#shouseid"
					name="shousename" id="shousename"
					 data-options="required:true"
					placeholder="<选择或输入>" ><span onclick="selecthouse('analysis')"></span> <input type="hidden" id="shouseid" value="">
</div>
</div>
<div class="s-box"><div class="title">货号</div>
<div class="text"><input  type="text"  class="edithelpinput wareno_help" data-value="#swareid"
				name="swareno" id="swareno" style="width:145px;height:24px;" data-options="required:true" placeholder="<选择或输入>" >
				<span onclick="selectware('search')"></span>
				 <input type="hidden" id="swareid" value="">
</div>
</div>
				 
<div class="s-box"><div class="title">类型</div>
<div class="text"><input  type="text"  class="edithelpinput" data-value="#stypeid"
				name="sfullname" id="sfullname" placeholder="<选择或输入>"><span onclick="selectwaretype('search')"></span>
				 <input type="hidden" id="stypeid" value="">
</div>
</div>
				 
<div class="s-box"><div class="title">品牌</div>
<div class="text"><input  type="text"  class="edithelpinput brand_help" data-value="#sbrandid"
				name="sbrandname" id="sbrandname" style="width:145px;height:24px;" data-options="required:true" placeholder="<选择或输入>" >
				<span onclick="selectbrand('search')"></span>
				 <input type="hidden" id="sbrandid" value="">
</div>
</div>
									
	
<div class="s-box"><div class="title">季节</div>
<div class="text">
<input  type="text" class="edithelpinput season_help" name="sseasonname" id="sseasonname" maxlength="20" placeholder="<选择或输入>">
<span onclick="opencombox(this)"></span>
</div>
</div>
<div class="s-box"><div class="title">生产年份</div>
<div class="text"><select id="sprodyear"
					style="width: 164px; height: 25px">
						<option selected="selected" value="">--请选择--</option>						
				</select><input id="iszero" name="iszero" type="checkbox">只查询单价=0的商品
</div>
</div>
</div>
</div>
</div>
	<input type="hidden" id="noteid">
	<input type="hidden" id="noteno">
	<table id="gg" style="overflow: hidden;"></table>
	<div class="page-bar">
	<div class="page-total" id="notetotal">共有0条记录</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
	</div>
	<!-- 修改采购价格 -->
	<div id="updateprice" title="修改价格" style="width: 430px; height: 490px;text-align: center;" class="easyui-dialog" closed="true"><input type="hidden" id="shoueid1" >
		<table width="280" cellspacing="7" class="table1 mt14">
			<tr>
				<td width="80" align="left" class="header">单据号</td>
				<td width="170" align="left"><input type="text" class="wid160 hig25"
					name="notenoltj" id="notenoltj" readonly>
					<input id="id" type="hidden"  readonly>
					</td>
			</tr>
			<tr>
				<td align="left" class="header">货号</td>
				<td align="left"><input class="wid160 hig25" id="wareno" type="text" readonly>
				</td>
			</tr>
			<tr>
				<td align="left" class="header">名称</td>
				<td align="left"><input class="wid160 hig25" id="warename" type="text" readonly>
				<input id="wareid" type="hidden"  readonly>
				</td>
			</tr>
			<tr>
				<td align="left" class="header">数量</td>
				<td align="left"><input class="wid160 hig25" id="amount" type="text" readonly></td>
			</tr>
			<tr>
				<td align="left" class="header">进价</td>
				<td align="left"><input class="wid160 hig25" id="entersale" type="text" readonly></td>
			</tr>
			<tr>
				<td align="left" class="header">原价</td>
				<td align="left"><input class="wid160 hig25" id="price0" type="text" readonly></td>
			</tr>
			<tr>
				<td align="left" class="header">折扣</td>
				<td align="left"><input class="wid160 hig25" id="discount" type="text"></td>
			</tr>
			<tr>
				<td align="left" class="header">单价</td>
				<td align="left"><input class="wid160 hig25" id="price" type="text"></td>
			</tr>
			<tr>
				<td align="left" class="header">金额</td>
				<td align="left"><input class="wid160 hig25" id="curr" type="text" readonly></td>
			</tr>
			<tr>
				<td align="left" colspan="2"><input id="tong" name="tong" type="checkbox">更改同款商品价格</td>
			</tr>
			<tr>
				<td align="left" colspan="2"><input id="ziliao" name="ziliao" type="checkbox">更改商品资料价格</td>
			</tr>
			<tr>
				<td colspan="2" class="dialog-footer textcenter"><input class="btn1"
					type="button" onclick="closeAll();updatewareincheck();" value="确定" />
					<input class="btn1" type="button" value="取消" onclick="closeAll()" /></td>
			</tr>
		</table>
	</div>
<script type="text/javascript"> 
var dan = false;
var searchb = false;
var dheight = 80;
var rownumbers = 1;
var rownumbers2 = 1;
var epno = getValuebyName("EPNO");
setqxpublic();
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

//设置弹出框的属性
$(function() {
	//设置日期框默认值
	var myDate = new Date(top.getservertime());
	$("#smindate").datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$("#smaxdate").datebox('setValue', myDate.Format('yyyy-MM-dd'));
	//生成年份 
	var year = myDate.getFullYear();
	var on = year - 5;
	var end = year + 1;
	for (var i = on; i <= end; i++) {
		$("#sprodyear").append("<option value=" + i + ">" + i + "</option'>");
	}
	$("#discount").bind("keyup",
	function() {
		this.value = this.value.replace(/[^\d.]/g, "");
		this.value = this.value.replace(/^\./g, ""); //验证第一个字符是数字而不是. 
		this.value = this.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
		if (Number(this.value) > 1) {
			alerttext("折扣应小于1", 1500);
			this.value = "1";
		}
		var disValue = Number(this.value); //折扣
		var price0Value = Number($('#price0').val()); //原价
		var amountValue = Number($('#amount').val()); //数量
		if (price0Value != 0) {
			$('#price').val(Number(disValue * price0Value).toFixed(2));
			var priceValue = Number($('#price').val());
			$('#curr').val(Number(amountValue * priceValue).toFixed(2));
		}
	});
	$("#price").bind("keyup",
	function() {
		this.value = this.value.replace(/^-?\D/g, '');
		var priceValue = Number(this.value); //单价
		var price0Value = Number($('#price0').val()); //原价
		var disValue = Number($('#discount').val()); //原价
		var amountValue = Number($('#amount').val()); //数量 
		if (price0Value == 0 || price0Value == undefined) {
			$('#curr').val(Number(amountValue * priceValue).toFixed(2));
		} else {
			$('#discount').val(Number(priceValue / price0Value).toFixed(2));
			$('#curr').val(Number(amountValue * priceValue).toFixed(2));
		};
	});
	$("#pp").createPage({
		pageCount: 1,
		//总页码
		current: 1,
		//当前页 
		backFn: function(p) {
			rownumbers = p;
			getpurchasecheck(p.toString());
		}
	});
	pp();
});
function disChange() {
	var disValue = Number($('#discount').val()); //折扣
	var price0Value = Number($('#price0').val()); //原价
	var amountValue = Number($('#amount').val()); //数量
	if (price0Value == 0 || price0Value == undefined) {
		$('#discount').val(discount);
	} else {
		$('#price').val(Number(disValue * price0Value).toFixed(2));
		var priceValue = Number($('#price').val());
		$('#curr').val(Number(amountValue * priceValue).toFixed(2));
	}
}
function priChange() {
	var priceValue = Number($('#price').val()); //单价
	var price0Value = Number($('#price0').val()); //原价
	var disValue = Number($('#discount').val()); //原价
	var amountValue = Number($('#amount').val()); //数量 
	if (price0Value == 0 || price0Value == undefined) {
		$('#price').val(priceValue);
		$('#price0').val(Number(priceValue / disValue).toFixed(2));
		$('#curr').val(Number(amountValue * priceValue).toFixed(2));
	} else {
		$('#discount').val(Number(priceValue / price0Value).toFixed(2));
		$('#curr').val(Number(amountValue * priceValue).toFixed(2));
	}

}
function toggle() {
	$("#hsearch").slideToggle('fast');
	if (searchb) {
		searchb = false;
		dheight = 80;
		$('#highsearch').val('条件搜索');
		$("#csearch").hide();
		$("#qclear").hide();
	} else {
		searchb = true;
		dheight = 188;
		$('#highsearch').val('条件搜索▲');
		$("#csearch").show();
		$("#qclear").show();
	}
	setTimeout(function() {
		$('#gg').datagrid('resize', {
			height: $('body').height() - 50
		});
	},
	200);

}
//清空表单搜索条件 
function clearAll() {
	var myDate = new Date(top.getservertime());
	$('#smindate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$('#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$('#shousename').val('');
	$('#shouseid').val('');
	$('#swareno').val('');
	$('#swareid').val('');
	$('#sbrandname').val('');
	$('#sbrandid').val('');
	$('#sfullname').val('');
	$('#stypeid').val('');
	$('#sseasonname').val('');
	$('#sprodyear').val('');
	$('#sprovname').val('');
	$('#sprovid').val('');
}

function closeAll() {
	$('#updateprice').dialog('close');
}
var dqindex; //行
//分页设置
function pp() {

	//加载数据
	$('#gg').datagrid({
		idField: 'NOTEDATE',
		width: '100%',
		height: $('body').height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			//halign表示对齐头部
			formatter: function(value, row, index) {
				if (value != '' && value != null) {
					return index + 1 + (rownumbers - 1) * pagecount;
				}
			}
		},
		{
			field: 'NOTENO',
			title: '单据号',
			width: 150,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'NOTEDATE',
			title: '日期',
			width: 120,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				return row.NOTEDATE == undefined ? '': row.NOTEDATE.substring(0, 16);
			}
		},
		{
			field: 'PROVNAME',
			title: '供应商',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'HOUSENAME',
			title: '店铺',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'WARENO',
			title: '货号',
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'WAREID',
			title: 'WAREID',
			width: 20,
			align: 'center',
			halign: 'center',
			hidden: true
		},
		{
			field: 'ID',
			title: 'ID',
			width: 80,
			align: 'center',
			halign: 'center',
			hidden: true
		},
		{
			field: 'WARENAME',
			title: '商品名称',
			width: 120,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'COLORNAME',
			title: '颜色',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'SIZENAME',
			title: '尺码',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'AMOUNT',
			title: '数量',
			width: 50,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				if (value != '' && value != null) {
					return Number(value);
				} else {
					return "";
				}
			}
		},
		{
			field: 'ENTERSALE',
			title: '进价',
			width: 80,
			fixed: true,
			align: 'right',
			halign: 'center',
			hidden: true
		},
		{
			field: 'PRICE0',
			title: '原价',
			width: 80,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				if (value != '' && value != null) {
					var val = Number(value);
					var nval = val.toFixed(2);
					if (nval != 0) {
						return nval.toString();
					} else {
						return "";
					}
				}
			}
		},
		{
			field: 'DISCOUNT',
			title: '折扣',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if (value != '' && value != null) {
					var val = Number(value);
					var nval = val.toFixed(2);
					if (nval != 0) {
						return nval.toString();
					} else {
						return "";
					}
				}
			}
		},
		{
			field: 'PRICE',
			title: '单价',
			width: 80,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				if (value != '' && value != null) {
					var val = Number(value);
					var nval = val.toFixed(2);
					if (nval != 0) {
						return nval.toString();
					} else {
						return "";
					}
				}
			}
		},
		{
			field: 'CURR',
			title: '金额',
			width: 80,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				if (value != '' && value != null) {
					var val = Number(value);
					var nval = val.toFixed(2);
					if (nval != 0) {
						return nval.toString();
					} else {
						return "";
					}
				}
			}
		},
		{
			field: 'REMARK',
			title: '摘要',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		}]],
		onDblClickRow: function(rowIndex, rowData) {
			if (!dan) {
				$('#notenoltj').val(rowData.NOTENO);
				$('#warename').val(rowData.WARENAME);
				$('#wareid').val(rowData.WAREID);
				$('#wareno').val(rowData.WARENO);
				$('#id').val(rowData.ID);
				$('#amount').val(Number(rowData.AMOUNT));
				$('#price0').val(rowData.PRICE0 == '' ? 0 : rowData.PRICE0);
				$('#discount').val(Number(rowData.DISCOUNT).toFixed(2));
				$('#price').val(rowData.PRICE);
				$('#entersale').val(rowData.ENTERSALE == '' ? 0 : rowData.ENTERSALE);
				$('#curr').val(rowData.CURR);
				dqindex = rowIndex;
				$('#tong,#ziliao').removeProp("checked");
				$('#updateprice').dialog('open');
			}
		},
		onDblClickCell: function(index, field, value) {
			if (field == 'NOTENO') {
				dan = true;
				var noteno = value;
				if (noteno != '' && noteno != undefined) {
					opendetaild('CG', noteno);
				}
			} else {
				dan = false;
			}

		},
		toolbar: '#toolbars',
		pageSize: 20
	});
	getpurchasecheck('1');
}
//更改采购入库原价记录
function updatewareincheck() {
	var mindate = $('#smindate').datebox('getValue'); //开始时间
	var maxdate = $('#smaxdate').datebox('getValue'); //结束时间
	var wareid = $("#wareid").val(); //商品id
	var noteno = $("#notenoltj").val(); //单据号
	var discount = $("#discount").val(); //折扣
	var price = $("#price").val(); //单价
	var curr = $("#curr").val(); //金额 
	var id = $('#id').val(); //id
	var tkbj;
	var zlbj;
	if ($("#tong").prop("checked") == true) {
		tkbj = "1";
	} else {
		tkbj = "0";
	}
	if ($("#ziliao").prop("checked") == true) {
		zlbj = "1";
	} else {
		zlbj = "0";
	}
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data: {
			erpser: "updatewareincheck",
			mindate: mindate,
			maxdate: maxdate,
			wareid: wareid,
			noteno: noteno,
			discount: discount,
			price: price,
			id: id,
			tkbj: tkbj,
			zlbj: zlbj,
			curr: curr
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$('#gg').datagrid('updateRow', {
						index: dqindex,
						row: {
							PRICE: price,
							DISCOUNT: discount,
							CURR: curr
						}
					});
					$('#gg').datagrid('refresh');
					$('#gg').datagrid('selectRow', dqindex);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
//查询采购入库核价记录，快速搜索也是这个
function getpurchasecheck(page) {
	var findbox = $('#qsnotetvalue').val(); //快速搜索 
	var mindate = $('#smindate').datebox('getValue'); //开始时间
	var maxdate = $('#smaxdate').datebox('getValue'); //结束时间
	var shouseid = Number($("#shouseid").val()); //店铺id
	var wareno = $("#swareno").val(); //货号no
	var wareid = Number($("#swareid").val()); //货号id
	var brandid = $("#sbrandid").val() == "" ? "-1": Number($("#sbrandid").val()); //品牌
	var prodyear = $("#sprodyear").val(); //生产年份
	var typeid = $("#stypeid").val(); //类型
	var seasonname = $("#sseasonname").val(); //季节
	var provid = $("#sprovid").val() == "" ? "-1": Number($("#sprovid").val()); //供应商
	var iszero;
	if ($("#iszero").prop("checked") == true) {
		iszero = "1";
	} else {
		iszero = "0";
	}
	var dataparams = {
		erpser: "listwareincheck",
		mindate: mindate,
		maxdate: maxdate,
		houseid: shouseid,
		wareid: wareid,
		brandid: brandid,
		prodyear: prodyear,
		seasonname: seasonname,
		typeid: typeid,
		iszero: iszero,
		provid: provid,
		rows: pagecount,
		page: page
	};
	if (findbox.length > 0) {
		dataparams["findbox"] = findbox;
	}
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: dataparams,
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var totals = data.total;
					$("#pp").setPage({
						pageCount: Math.ceil(Number(totals) / pagecount),
						current: Number(page)
					});
					$('#gg').datagrid('loadData', data);
					$('#notetotal').html('共有' + totals + '条记录');
					$('#gg').datagrid('scrollTo', 0);
				}
			} catch(err) {
				console.error(err.message);
			}

		}
	});
}
</script>

<!-- 店铺帮助列表 -->
<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
<!-- 商品帮助列表 -->
<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
<!-- 商品类型帮助列表 -->
<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
<!-- 品牌帮助列表 -->
<jsp:include page="../HelpList/BrandHelpList.jsp"></jsp:include>
<!-- 单据明细帮助列表 -->
<jsp:include page="../HelpList/NoteDetail.jsp"></jsp:include>
<!-- 供应商帮助列表 -->
<jsp:include page="../HelpList/ProvHelpList.jsp"></jsp:include>

</body>
</html>