<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 无检验库存商品帮助列表 -->
<div id="wared" title="商品帮助列表" style="width: 590px;text-align:center; height: 440px" class="easyui-dialog" closed=true>
	<!-- 快速搜索栏 -->
	<div id="waresbtn"  class="help-qstoolbar">
	<input type="button" class="btn2" id="#add_ware" value="添加" onclick="openawd()"><input id="warefindbox" type="text" placeholder="搜索货号、商品名称<回车搜索>" class="help-qsipt" data-enter="getwaredata('1')" data-end="yes"></input>
	<input class="btn2" type="button" value="搜索" onclick="getwaredata('1')">
	</div>
	<table id="wareht" style="overflow: hidden;"></table>
	<input type="hidden" id="wareser" >
	<div class="dialog-footer">
	<div id="warehpp" class="tcdPageCode fl"></div> 
	<div class="fr">
	<label class="label_provware hide"><input type="checkbox" id="showprovware">仅显示此供应商商品</label>
	<input type="button" class="btn1" value="确定" onclick="selectwares();">
	</div>
</div>
</div>
<div id="awd" title="添加商品" style="width: 600px;text-align:center; height: 400px" class="easyui-dialog" closed=true>
<div style="overflow: auto;text-align:center">
   <datalist id="awyear_list"></datalist>
<form action="" id="addform" >
<table border="0" cellspacing="10" class="table1">
  <tr>
    <td width="60" align="right" class="header skyrequied">货号</td>
    <td width="150" align="left"><input maxlength="30" type="text" name="awwareno" style="width:160px;height:25px;" id="awwareno" data-options="required:true" placeholder="<输入>" onchange="getwarenoyz('add');"></td>
    <td align="right" class="header skyrequied">商品类型</td>
    <td align="left"><input  type="text" class="helpinput" name="awfullname" id="awfullname" style="width:145px;height:24px;" data-options="required:true" placeholder="<选择>" readonly>
    <span onclick="selectwaretype('aw');" ></span>
				 <input type="hidden" name="awtypeid" id="awtypeid" value=""></td>
  </tr>
  <tr>
    <td align="right" class="header">商品名称</td>
    <td align="left"><input type="text" style="width:160px;height:25px" name="awwarename" id="awwarename" maxlength="30" placeholder="<输入>"></td>
    <td align="right" class="header skyrequied">颜色</td>
    <td align="left"><input  type="text" class="helpinput wid143" name="awcolorname" id="awcolorname" placeholder="<选择或输入>" readonly><span onclick="selectcolor('add')"></span>
				 <input type="hidden" name="awcolorid" id="awcolorid" value=""></td>
  </tr>
    <tr>
    <td align="right" class="header skyrequied">尺码组</td>
    <td align="left">
    <select name="awsizetypegroup" id="awsizetypegroup" style="width: 164px;height:27px">
		<option value="">---请选择---</option>
	</select>
    <td align="right" class="header">品牌</td>
    <td align="left"><input  type="text" class="edithelpinput brand_help" data-value="awbrandid" name="awbrandname" id="awbrandname" placeholder="<选择或输入>"><span onclick="selectbdd('add')"></span>
				 <input type="hidden" name="awbrandid" id="awbrandid" value=""></td>
  </tr>
    <tr>
    <td align="right" class="header">生产商</td>
    <td align="left"><input  type="text" class="edithelpinput provd_help" data-value="awprovid" name="awprovname" id="awprovname" placeholder="<选择或输入>"><span onclick="selectpdd('add')"></span>
				 <input type="hidden" name="awprovid" id="awprovid" value=""></td>
	<td align="right" class="header">生产年份</td>
    <td align="left"><input class="wid160 hig25" type="text" id="awprodyear" name="awprodyear" list="awyear_list" placeholder="输入或点击选择"></td>
	 </tr>
  <tr>
	<td align="right" class="header">季节</td>
    <td align="left">
	<input  type="text" class="edithelpinput season_help" name="awseasonname" id="awseasonname" maxlength="20" placeholder="<选择或输入>">
	<span onclick="opencombox(this)"></span>
	</td>
	<td align="right" class="header">国标码</td>
    <td align="left"><input type="text" style="width:160px;height:25px"
				name="awgbm" id="awgbm" placeholder="<输入>" maxlength="13"></td>
  </tr>
  <tr id="tr_entersale" class="hide">
    <td align="right" class="header">进价</td>
    <td align="left"><input type="text" style="width:160px;height:25px" name="awentersale" id="awentersale" placeholder="<输入>" class="onlyMoney" maxlength="6"></td>
   </tr>
  <tr>
    <td align="right" class="header">零售价</td>
    <td align="left"><input type="text" style="width:160px;height:25px"
				name="awretailsale" id="awretailsale" placeholder="<输入>" class="onlyMoney" maxlength="6"></td>
	<td align="right" class="header">批发价</td>
    <td align="left"><input type="text" style="width:160px;height:25px"
				name="awsale4" id="awsale4" placeholder="<输入>" class="onlyMoney" maxlength="6"></td>
  </tr>
</table>
<p style="cursor: pointer;color:#ff7900;" onclick="gotowarrcode()">填写更多资料</p>
</form>
</div>
<div class="dialog-footer">
	<input class="btn1" type="button" style="width:150px;margin-right: 30px" type="button" value="保存并继续添加" onclick="addWareCode(false);">
	<input class="btn1 hide" type="button" id="addandusebtn" style="width:150px;margin-right: 30px" type="button" value="保存并使用" onclick="addWareCode(true);">
</div>
</div>
<!-- 得到品牌列表  -->
	<div id="whp_brandd" title="品牌帮助列表" style="width: 530px; height: 440px;" class="easyui-dialog" closed="true">
		<input type="hidden" id="hbrandname">
		<input type="hidden" id="hbrandid">
		<input type="hidden" id="hbrandser">
		<div id="adbrandsbtn" class="help-qstoolbar">
		<input class="btn2" type="button" value="添加" onclick="$('#brand_add_dialog').dialog('open')">
		<input type="text" class="help-qsipt" id="whp_brandfindbox" data-enter="getbddata('1')" data-end="yes"  placeholder="搜索品牌<回车搜索>" >
		<input class="btn2" type="button" value="搜索" onclick="getbddata('1')">
		</div>
		<table id="whp_brandt" style="overflow: hidden;"></table>
		<div class="dialog-footer">
		<div id="whp_brandpp" class="tcdPageCode fl"></div>
		<input type="button" class="btn1" value="确定" onclick="selectbd();" />
<!-- 		<input type="button" class="btn2" value="取消" onclick="$('#whp_brandd').dialog('close')" /> -->
		</div>
	</div>
	<!-- 得到生产商列表  -->
	<div id="whp_provd" title="生产商帮助列表" style="width: 530px; height: 440px;" class="easyui-dialog" closed="true">
		<input type="hidden" id="hprovname">
		<input type="hidden" id="hprovid">
		<input type="hidden" id="hprovser">
		<div id="adprovsbtn" class="help-qstoolbar">
		<input type="text" class="help-qsipt" id="whp_provfindbox" data-enter="getbddata('1')" data-end="yes"  placeholder="搜索生产商<回车搜索>" >
		<input class="btn2" type="button" value="搜索" onclick="getbddata('1')">
		</div>
		<table id="whp_provt" style="overflow: hidden;"></table>
		<div class="dialog-footer">
		<div id="whp_provpp" class="tcdPageCode fl"></div>
		<input type="button" class="btn1" value="确定" onclick="selectpd();" />
<!-- 		<input type="button" class="btn2" value="取消" onclick="$('#whp_provd').dialog('close')" /> -->
		</div>
	</div>
	<!-- 得到颜色列表 -->
<div id="adcolorf" title="颜色帮助列表" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
		<input type="hidden" id="hcolorname">
		<input type="hidden" id="hcolorid">
		<input type="hidden" id="hcolorser">	
		<div id="addcolorsbtn" class="help-qstoolbar">
		<input class="btn2" id="color_add_btn" type="button" value="增加" onclick="openaddcolord();">
		<input type="text" class="help-qsipt" id="addcolorfindbox" data-enter="getcolordata('1')" data-end="yes" placeholder="搜索颜色<回车搜索>" >
		<input class="btn2" type="button" value="搜索" onclick="getcolordata('1')">
		</div>
		<table id="addcolort" style="overflow: hidden;"></table>
		<div class="dialog-footer">
		<div id="addcolorpp" class="tcdPageCode fl"></div> 
<!-- 		<input type="button" class="btn2" value="取消" onclick="$('#adcolorf').dialog('close')" /> -->
		<input type="button" class="btn1" value="确定" onclick="selectcl();" />
		</div>
</div>
	<!-- 添加颜色窗 -->
<div id="color_add_dialog" title="添加颜色" style="width: 400px; height: 320px;text-align: center;"  data-options="onClose:refreshcolorcode"
		class="easyui-dialog" closed=true>
<table width="400" border="0" cellspacing="10" class="table1" data-qickey="F4:'addcolor();'">
  <tr align="right">
    <td width="100" class="header skyrequied">颜色名称</td>
    <td width="160" align="left"><input class="hig25 wid160 "type="text" maxlength="20" placeholder="<输入>" id="color_add_colorname" data-enter="addcolor()" /> </td>
  </tr>
  <tr align="right">
    <td class="header">色码</td>
    <td align="left"><input class="onlyNumAlpha hig25 wid160 "  maxlength="6" type="text" placeholder="<输入>" id="color_add_colorno"/> </td>
  </tr>
</table>
<div class="dialog-footer" style="text-align: center;">
				<input id="add" class="btn1" type="button" name="save" style="width:150px;" type="button" value="保存并继续添加" onclick="addcolor();">
</div>
	</div>
		<!-- 添加品牌窗 -->
	<div id="brand_add_dialog" title="添加品牌" style="width: 350px; height: 200px;"
		class="easyui-dialog" closed=true>
		<div style="margin-top: 40px; text-align: center;">
			<label class="theader skyrequied">品牌名称&nbsp;&nbsp;&nbsp;</label> <input data-enter="addbd();"
				style="height: 25px" type="text" placeholder="<输入>"
				id="brand_add_brandname" name="brand_add_brandname" width="175px" maxlength="20" />
		</div>
		<div class="dialog-footer" style="text-align: center;">
			<input id="brand_add_add" class="btn1" type="button" name="save" style="width: 150px;" type="button" value="保存并继续添加" onclick="addbd();">
		</div>
	</div>
<script type="text/javascript">
var jsondata;
var waredatagrid = true;
var colorcount = 0;
var checkcolor = '';
var showprovware = 0; //仅显示供应商商品帮助
if (pgid == "pg1101" || pgid == "pg1102" || pgid == "pg1103") {
	var cgbj = getparam({
		pgid: "cgbj",
		usymbol: "cgbj"
	});
	cgbj += "0000000000";
	showprovware = cgbj.charAt(0);
	if (showprovware == 1) $("#showprovware,#barcodeaddkzgys").prop("checked", true);
}
function setwaredatagrid(wareser) {
	var findbox;
	$('#addandusebtn,label.label_provware,tr#kzbarcodeaddgys').hide();
	if (wareser == 'add') {
		findbox = $('#awareno').val();
	} else if (wareser == 'update') {
		findbox = $('#wuwareno').val();
	} else if (wareser == 'search') {
		findbox = $('#swareno').val();
	} else if (wareser == 'load') {
		findbox = $('#lwareno').val();
	} else if (wareser == 'print') {
		findbox = $('#wquickuwareno').val();
	} else if (wareser == 'shopsale') {
		findbox = $('#wareno').val();
	} else if (wareser == 'analysis') {
		findbox = $('#awareno').val();
	} else if (wareser == 'tjwareadd') { //调价 
		findbox = $('#tjquickuwareno').val();
	} else {
		findbox = $('#wquickuwareno').val();
		$('#addandusebtn').show();
		if ((pgid == "pg1101" || pgid == "pg1102" || pgid == "pg1103") && allowscanware!=1) $('label.label_provware,tr#kzbarcodeaddgys').show();
	}
	$('#warefindbox').val(findbox);
	if (!$('#wareht').data().datagrid) {
		$("#showprovware,#barcodeaddkzgys").change(function() {
			var check = $(this).prop('checked');
			$("#showprovware,#barcodeaddkzgys").prop("checked",check);
			showprovware = check ? "1": "0"; //是否仅显示供应商
			//设置采购标记cgbj十位0000000000；
			//                |__________是否仅显示供应商商品；
			var cgbj = getparam({
				pgid: "cgbj",
				usymbol: "cgbj"
			});
			cgbj += "0000000000";
			var cgbjArr = cgbj.split("");
			cgbjArr.splice(0, 1, showprovware);
			cgbj = cgbjArr.join("");
			setparam({
				pgid: "cgbj",
				usymbol: "cgbj",
				uvalue: cgbj.substring(0, 10)
			});
			getwaredata(1);
		});
		var myDate = new Date(top.getservertime());
		var year = myDate.getFullYear();
		var start = year - 5;
		var end = year + 1;
		var arealist = '';
		for (var i = start; i <= end; i++) {
			arealist += "<option label=" + i + " value=" + i + "/>"
		}
		try {
			$("#awyear_list").html(arealist);
		} catch(e) {
}
		$('#warehpp').createPage({
			pageCount: 1,
			current: 1,
			backFn: function(p) {
				getwaredata(p.toString())
			}
		});
		$('#wareht').datagrid({
			idField: 'warecode',
			height: 350,
			fitColumns: true,
			nowrap: false,
			striped: true,
			//隔行变色
			singleSelect: true,
			onDblClickRow: function(rowIndex, rowData) {
				selectwares();
			},
			columns: [[

			{
				field: 'WAREID',
				title: 'ID',
				width: 200,
				hidden: true

			},
			{
				field: 'ROWNUMBER',
				title: '序号',
				width: 50,
				fixed: true,
				align: 'center',
				halign: 'center',
				formatter: function(value, row, index) {
					var val = Math.ceil(Number(value) / pagecount) - 1;
					return isNaN(Number(value)) || value == "" ? "": val * pagecount + Number(index) + 1;
				}
			},
			{
				title: '图片',
				field: 'IMAGENAME0',
				width: 70,
				align: 'center',
				fixed: true,
				formatter: function(value, row, index) {
					if (value != '' && value != undefined) {
						return '<img bigimg="true" style=\"height: 60px;width: 60px;\" src="' + imageurl + row.IMAGENAME0 + '_60x60-m3.png" />';
					}
				}
			},
			{
				field: 'WARENO',
				title: '货号',
				width: 200
			},
			{
				field: 'WARENAME',
				title: '商品名称',
				width: 200
			},
			{
				field: 'UNITS',
				title: '计量单位',
				width: 200,
				hidden: true
			},
			{
				field: 'BRANDID',
				title: '品牌 ID',
				width: 200,
				hidden: true
			},
			{
				field: 'BRANDNAME',
				title: '品牌',
				width: 200
			},
			{
				field: 'FULLNAME',
				title: '类型',
				width: 200
			},
			{
				field: 'SEASONNAME',
				title: '季节',
				width: 200,
				hidden: true
			},
			{
				field: 'SIZEGROUPNO',
				title: '尺码组',
				width: 200,
				hidden: true
			},
			{
				field: 'TYPEID',
				title: '类型ID',
				width: 200,
				hidden: true
			},
			{
				field: 'PRODYEAR',
				title: '生产年份',
				width: 200,
				hidden: true
			},
			{
				field: 'PRODNO',
				title: '厂家编码',
				width: 200,
				hidden: true
			},
			{
				field: 'ENTERSALE',
				title: '进价',
				width: 200,
				hidden: true,
				formatter: function(value, row, index) {
					return Number(value).toFixed(2);
				}
			},
			{
				field: 'RETAILSALE',
				title: '零售价',
				width: 200,
				formatter: function(value, row, index) {
					return Number(value).toFixed(2);
				}
			},
			{
				field: 'SALE1',
				title: '售价一',
				width: 200,
				hidden: true,
				formatter: function(value, row, index) {
					return Number(value).toFixed(2);
				}
			},
			{
				field: 'SALE2',
				title: '售价二',
				width: 200,
				hidden: true,
				formatter: function(value, row, index) {
					return Number(value).toFixed(2);
				}
			},
			{
				field: 'SALE3',
				title: '售价三',
				width: 200,
				hidden: true,
				formatter: function(value, row, index) {
					return Number(value).toFixed(2);
				}
			},
			{
				field: 'SALE4',
				title: '售价四',
				width: 200,
				hidden: true,
				formatter: function(value, row, index) {
					return Number(value).toFixed(2);
				}
			},
			{
				field: 'SALE5',
				title: '售价五',
				width: 200,
				hidden: true,
				formatter: function(value, row, index) {
					return Number(value).toFixed(2);
				}
			},
			{
				field: 'NOUSED',
				title: '禁用',
				width: 200,
				hidden: true
			}]],
			pageSize: 10,
			toolbar: '#waresbtn'
		}).datagrid("keyCtr", "selectwares()");
		waredatagrid = false;
	}
	getwaredata('1');
}
function selectware(wareser) {
	$('#wareser').val(wareser);
	setwaredatagrid(wareser);
	$('#wared').dialog({
		modal: true
	});
	$('#wared').dialog('open');
	// 	$('#wareht').prev().focus();
	$('#warefindbox').focus();
}
function selectwares() {
	var wareser = $('#wareser').val();
	var rowData = $('#wareht').datagrid('getSelected');
	if (rowData) {
		if (wareser == 'add') {
			$('#awareno').val(rowData.WARENO);
			$('#awareid').val(rowData.WAREID);
			$('#awarename').val(rowData.WARENAME);
			$('#aunits').val(rowData.UNITS);
			if ($('#upricetype')[0]) {
				if ($('#upricetype').val() == '0') {
					$('#aprice').val(rowData.RETAILSALE);
				} else {
					$('#aprice').val(rowData.ENTERSALE);
				}
			} else {
				$('#aprice').val(rowData.ENTERSALE);
			}
			$('#awarename').focus();
		} else if (wareser == 'update') {
			$('#wuwareno').val(rowData.WARENO);
			$('#wuwareid').val(rowData.WAREID);
			$('#wuwarename').val(rowData.WARENAME);
			$('#wuunits').val(rowData.UNITS);
			if ($('#upricetype')[0]) {
				if ($('#upricetype').val() == '0') {
					$('#wuprice').val(rowData.RETAILSALE);
				} else {
					$('#wuprice').val(rowData.ENTERSALE);
				}
			} else {
				$('#wuprice').val(rowData.ENTERSALE);
			}
			$('#wuwarename').focus();
		} else if (wareser == 'quickaddh') {
			$('#quicktable').html('');
			$('#wquickwareno').val(rowData.WARENO);
			$('#wquickwareid').val(rowData.WAREID);
			$('#wquickwarename').val(rowData.WARENAME);
			$('#wquickunits').val(rowData.UNITS);
			if ($('#upricetype')[0]) {
				if ($('#upricetype').val() == '0') {
					$('#wquickprice').val(Number(rowData.RETAILSALE).toFixed(2));
				} else {
					$('#wquickprice').val(Number(rowData.ENTERSALE).toFixed(2));
				}
			} else {
				$('#wquickprice').val(Number(rowData.ENTERSALE).toFixed(2));
			}
			sumcurr('qucikadd');
			var houseid = $('#uhouseid').val();
			quickaddtable(rowData.WAREID, houseid);
			$('#wquickwareno').focus();
		} else if (wareser == 'quickaddw') {
			$('#quicktable').html('');
			$('#wquickwareno').val(rowData.WARENO);
			$('#wquickwareid').val(rowData.WAREID);
			$('#wquickwarename').val(rowData.WARENAME);
			$('#wquickunits').val(rowData.UNITS);
			if ($('#upricetype')[0]) {
				if ($('#upricetype').val() == '0') {
					$('#wquickprice').val(Number(rowData.RETAILSALE).toFixed(2));
				} else {
					$('#wquickprice').val(Number(rowData.ENTERSALE).toFixed(2));
				}
			} else {
				$('#wquickprice').val(Number(rowData.ENTERSALE).toFixed(2));
			}
			sumcurr('qucikadd');
			var houseid = $('#uhouseid').val();
			quickaddtable(rowData.WAREID, "");
			$('#wquickwareno').focus();
		} else if (wareser == 'search') {
			$('#swareno').val(rowData.WARENO);
			$('#swareid').val(rowData.WAREID);
		} else if (wareser == 'quickupdatew') {
			$('#wquickuwareid').val(rowData.WAREID);
			$('#wquickuwareno').val(rowData.WARENO);
			$('#wquickuwarename').val(rowData.WARENAME);
			$('#wquickuunits').val(rowData.UNITS);
			getwaremsum(rowData.WAREID, $('#unoteno').val());
			//newquickaddtable(rowData.WAREID,"");
			$('#wquickuwareno').focus();
		} else if (wareser == 'quickupdatew1') {
			$('#wquickuwareid').val(rowData.WAREID);
			$('#wquickuwareno').val(rowData.WARENO);
			$('#wquickuwarename').val(rowData.WARENAME);
			$('#wquickuunits').val(rowData.UNITS);
			getwaremsum($('#usaleid').val(), rowData.WAREID, $('#unoteno').val());
			//newquickaddtable(rowData.WAREID,"");
			$('#wquickuwareno').focus();
		} else if (wareser == 'getwaresaleid') {
			$('#wquickuwareid').val(rowData.WAREID);
			$('#wquickuwareno').val(rowData.WARENO);
			getnearsaleid(rowData.WAREID);
			//newquickaddtable(rowData.WAREID,"");
			$('#wquickuwareno').focus();
		} else if (wareser == "analysis") {
			$('#awareno').val(rowData.WARENO);
			$('#awareno').sethelpval(rowData.WAREID);
			$('#awareid').val(rowData.WAREID);
			$('#awareno').focus();
		} else if (wareser == "addbarcode") {
			$('#awareno').val(rowData.WARENO);
			$('#awareid').val(rowData.WAREID);
			$('#awarename').val(rowData.WARENAME);
			$('#abrandname').val(rowData.BRANDNAME);
			$('#abrandid').val(rowData.BRANDID);
			$('#awareno').focus();
		} else if (wareser == "autobarcode") {
			$('#iwareno').val(rowData.WARENO);
			$('#iwareid').val(rowData.WAREID);
			$('#iwareno').focus();
		} else if (wareser == 'load') {
			$('#lwareno').val(rowData.WARENO);
			$('#lwareid').val(rowData.WAREID);
			$('#lwareno').focus();
		} else if (wareser == 'print') {
			$('#wquickuwareno').val(rowData.WARENO);
			$('#wquickuwareid').val(rowData.WAREid);
			getbarcodeprintsum(rowData.WAREID);
		} else if (wareser == 'shopsale') {
			var oldwareid = $('#wareid').val();
			$('#wareno').val(rowData.WARENO);
			$('#warename').val(rowData.WARENAME);
			$('#wareid').val(rowData.WAREID);
			//			$('#price0').val(rowData.RETAILSALE);
			getsaleoutprice(rowData.WAREID);
			//			var discount = $('#discount').val();
			//			diacount = isNaN(discount)?1:Number(discount);
			//			var price = Number(rowData.RETAILSALE)*discount;
			// 			var curr = price * Number($('#amount').val());
			// 			$('#price').val(price.toFixed(2));
			// 			$('#curr').val(curr.toFixed(2));
			if (oldwareid != rowData.WAREID) {
				$('#colorname').val('');
				$('#sizename').val('');
				$('#colorid').val('');
				$('#sizeid').val('');
			}
		} else if (wareser == 'tjwareadd') { //调价 
			$('#tjquickuwareid').val(rowData.WAREID);
			$('#tjquickuwareno').val(rowData.WARENO);
			addwareadjustm(rowData.WAREID, $('#unoteno').val());
			//newquickaddtable(rowData.WAREID,"");
			$('#tjquickuwareno').focus();
		} else if (wareser == 'warewarn') { //设置库存预警 
			$('#uwareid').val(rowData.WAREID);
			$('#uwareno').val(rowData.WARENO);
			openwarnd();
		} else if (wareser == 'addsaleprom0') { //增加促销商品
			addsalepromware(rowData,0)
		} else if (wareser == 'addsaleprom1') { //增加促销商品
			addsalepromware(rowData,1)
		}
		$('#wared').dialog('close');
	} else {
		alert('请选择商品！');
	}
}
//获取商品列表数据
function getwaredata(page) {
	var findbox = $('#warefindbox').val();
	var wareser = $('#wareser').val();
	var provid = "-1";
	if (wareser == "quickupdatew" && showprovware == 1) {
		provid = $('#uprovid').val();
	}
	$('#wareht').datagrid('loadData', nulldata);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "warecodelisthelp",
			findbox: findbox,
			provid: provid,
			rows: pagecount,
			fieldlist: "a.wareid,a.wareno,a.warename,a.units,a.shortname,a.brandid,a.seasonname,a.typeid,a.prodyear,a.prodno,a.entersale,a.retailsale,a.sale1,a.sale2,a.sale3,a.sale4,a.sale5,a.remark,a.sizegroupno,a.noused,a.downenabled,c.brandname,b.typename,b.fullname,a.imagename0",
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$('#warehpp').setPage({
						pageCount: Math.ceil(Number(data.total) / pagecount),
						current: Number(page)
					});
					$('#wareht').datagrid('loadData', data);
					var len = $('#wareht').datagrid('getRows').length;
					if (len > 0) {
						$('#wareht').datagrid("selectRow", 0);
					}
					$('#warefindbox').focus();
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
//添加框
function openawd() {
	$('#addform')[0].reset();
	$('#addform input[type=hidden]').val("");
	if (Number(allowinsale) == 1) $('#tr_entersale').show();
	else $('#tr_entersale').hide();

	var date = new Date(top.getservertime());
	var thisyear = date.getFullYear();
	getdefaultcs();
	$('#awd').dialog('open');
}
var hascolort = true;
//加载颜色框和数据
function selectcolor(colorser) {
	$('#addcolorfindbox').val('');
	$('#hcolorser').val(colorser);
	if (hascolort) {
		$("#addcolorpp").createPage({
			pageCount: 1,
			current: 1,
			backFn: function(p) {
				getcolordata(p.toString());
			}
		});
		$('#addcolort').datagrid({
			idField: 'colorname',
			height: 350,
			fitColumns: true,
			striped: true,
			//隔行变色
			singleSelect: true,
			pageSize: 20,
			checkOnSelect: false,
			selectOnCheck: false,
			columns: [[{
				field: 'COLORID',
				title: 'ID',
				width: 200,
				hidden: true
			},
			{
				field: 'SELECTCOLOR',
				checkbox: true
			},
			{
				field: 'ROWNUMBER',
				title: '序号',
				width: 40,
				fixed: true,
				align: 'center',
				halign: 'center',
				formatter: function(value, row, index) {
					var val = Math.ceil(Number(value) / pagecount) - 1;
					return val * pagecount + Number(index) + 1;
				}
			},
			{
				field: 'COLORNAME',
				title: '颜色',
				width: 200
			},
			{
				field: 'COLORNO',
				title: '色码',
				width: 200
			},
			{
				field: 'SELBJ',
				title: 'SELBJ',
				width: 200,
				hidden: true
			}]],
			onLoadSuccess: function(data) {
				if (data) {
					clcheck = false;
					$.each(data.rows,
					function(index, item) {
						var has = hasKey(item.COLORID);
						if (item.SELBJ == '1' || has) {
							if (!has) {
								var newjson = {
									colorid: item.COLORID,
									colorname: item.COLORNAME
								};
								cljson.push(newjson);
							}
							$('#addcolort').datagrid('checkRow', index);
						} else $('#addcolort').datagrid('uncheckRow', index);
					});
					clcheck = true;
				}
			},
			onCheck: function(rowIndex, rowData) {
				if (colorser == "add") {
					if (clcheck) {
						var newjson = {
							colorid: rowData.COLORID,
							colorname: rowData.COLORNAME
						};
						cljson.push(newjson);
					}
				}

			},
			onUncheck: function(index, row) {
				for (var i in cljson) {
					if (cljson[i].colorid == row.COLORID) {
						cljson.splice(i, 1);
						break;
					}
				}
			},
			onDblClickRow: function(index,row){
				var $dg = $('#addcolort');
				var rows = $dg.datagrid("getChecked");
				for(var i=0;i<rows.length;i++){
				    var r = rows[i];
					if(row.COLORID==r.COLORID){
				    	$dg.datagrid('uncheckRow', index);
				        return;
				    }
				};
				$dg.datagrid('checkRow', index);
			},
			toolbar: '#addcolorsbtn'
		}).datagrid("keyCtr", "");
		hascolort = false;
	}
	getcolordata('1');
	$('#adcolorf').dialog('open');
}

//获取颜色列表
function getcolordata(page) { //定义回调函数
	alertmsg(6);
	var findbox = $('#addcolorfindbox').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "colorlist",
			findbox: findbox,
			sort: "colorname",
			order: "asc",
			noused: 0,
			fieldlist: "*",
			page: page
		},
		dataType: 'json',
		success: function(data) {
			try {
				if (valideData(data)) {
					$("#addcolorpp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$('#addcolort').datagrid('loadData', data);
					clcheck = true;
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
var cljson = [];
function hasKey(key) {
	for (var i in cljson) {
		if (cljson[i].colorid == key) return true;
	}
	return false;
}
function selectcl() {
	var colorser = $('#hcolorser').val();
	var l = getJsonLength(cljson);
	colorcount = l;
	var colornames = new Array(l);
	var colorids = new Array(l);
	for (var i in cljson) {
		colornames[i] = cljson[i].colorname;
		colorids[i] = cljson[i].colorid;
	}
	var colorname = colornames.toString();
	var colorid = colorids.toString();
	if (colorid != '') {
		if (colorser == 'add') {
			$('#awcolorname').val(colorname);
			$('#awcolorid').val(colorid);
		}
	}
	$('#adcolorf').dialog('close');
}
//取默认颜色尺码
var warefs = true;
var dfcolorid = "";
var dfcolorname = "";
var dfsizegroupno = "";
var dfcolorcount = 0;
var dfcljson = [];
var cljson = [];
function getdefaultcs() {
	var fs = "1";
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		async: false,
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "getwareinfo",
			fs: 1
		},
		dataType: 'json',
		success: function(data) {
			try {
				if (valideData(data)) {
					var colorname = data.COLORNAME;
					var colorid = data.COLORID;
					var sizegroupno = data.sizegrouplist;
					var sg = data.SIZEGROUPNO;
					var seasonlist = data.seasonlist;
					var sizelist = '';
					dfcolorid = colorid;
					dfcolorname = colorname;
					dfsizegroupno = sg;
					$('#awcolorname').val(colorname);
					$('#awcolorid').val(colorid);
					cljson = [];
					colorcount = 0;
					if (Number(colorid) > 0) {
						colorcount = 1;
						dfcolorcount = 1;
						var newjson = {
							colorid: colorid,
							colorname: colorname
						};
						cljson.push(newjson);
						dfcljson = copyobjarry(cljson);
					}
					try {
						if (warefs) {
							for (var i in sizegroupno) {
								sizelist += '<option value="' + sizegroupno[i].GROUPNO + '">' + sizegroupno[i].SIZELIST + '</option>';
							}
							$('#awsizetypegroup').append(sizelist);
							warefs = false;
						}

					} finally {
						$('#awsizetypegroup').val(sg);
					}
					$('#awwareno').focus();
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
//验证货号
function getwarenoyz() {
	var wareno = $('#awwareno').val();
	var wareid = "";
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "warenoexists",
			wareid: Number(wareid),
			wareno: wareno.toUpperCase()
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
//增加商品编码
function addWareCode(isuse) {
	var wareno = $("#awwareno").val();
	var warename = $("#awwarename").val();
	var brandid = $("#awbrandid").val();
	var provid = $("#awprovid").val();
	var typeid = $("#awtypeid").val();
	var sizetypegroup = $("#awsizetypegroup").val();
	if (Number(colorcount) == 0) {
		alerttext("请选择颜色！")
	}
	if (sizetypegroup == '') {
		alerttext("请选择吃尺码组！")

	}
	var sale4 = $("#awsale4").val();
	var entersale = $("#awentersale").val();
	var retailsale = $("#awretailsale").val();
	var gbbar = $("#awgbm").val();
	var prodyear = $("#awprodyear").val();
	var seasonname = $("#awseasonname").val();
	sale4 = sale4 == '' ? '0': sale4;
	if (Number(colorcount) > 0 && sizetypegroup != '') {
		var colorids = $("#awcolorid").val().split(",");
		var colorlist = [];
		for (var i in colorids) {
			var colorid = Number(colorids[i]);
			if (!isNaN(colorid) && colorid > 0) colorlist.push({
				colorid: colorid
			});
		}
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "addwarecode",
				wareno: wareno,
				typeid: typeid,
				provid: provid,
				warename: warename,
				brandid: brandid,
				gbbar: gbbar,
				seasonname: seasonname,
				prodyear: prodyear,
				sizegroupno: sizetypegroup,
				entersale: entersale,
				retailsale: retailsale,
				sale4: sale4,
				colorlist: JSON.stringify(colorlist)
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				try {
					if (valideData(data)) {
						if (!isuse) {
							getwaredata('1');
							$("#awwareno").val(autogetno(wareno));
							$('#awcolorname').val(dfcolorname);
							$('#awcolorid').val(dfcolorid);
							colorcount = dfcolorcount;
							cljson = copyobjarry(dfcljson);
							/*
							cljson = [];
							colorcount = 0;
							$('#awcolorname').val('');
							$('#awcolorid').val('');*/
							$('#awwareno').focus();
						} else {
							$('#awd,#wared').dialog('close');
							var wareid = data.msg;
							$('#tjquickuwareid,#wquickuwareid').val(wareid);
							var noteno = $('#unoteno').val();
							if (pgid == "pg1302" || pgid == "pg1303" || pgid == "pg1309") getnearsaleid(wareid);
							else getwaremsum(wareid, noteno);
						}
					}
				} catch(err) {
					console.error(err.message);
				}

			}
		});

	}
}
function autogetno(text) {
	var start = new RegExp(/^\d+/);
	//alert(start.exec(test));
	var end = new RegExp(/\d+$/);
	//alert(end.exec(test));
	if (end.exec(text) != null) {
		var numstr = (end.exec(text))[0];
		var number = Number(numstr);
		var news = (number + 1).toString();
		var l1 = numstr.length;
		var l2 = news.length;
		if (l1 <= l2) return newtext = text.replace(numstr, news);
		else if (l1 > l2) {
			for (var i = 1; i <= l1 - l2; i++) news = '0' + news;
			return newtext = text.replace(numstr, news);
		}
	} else {
		return text + '0';
	}
}
function gotowarrcode() {
	top.addTabs({
		id: "pg1005",
		title: "商品编码",
		close: true
	});
}
//加载品牌列表窗
var hasbranddg = true;
function selectbdd(brandser) {
	$('#hbrandser').val(brandser);
	var findbox = "";
	if (brandser == 'add') {
		findbox = $('#awbrandname').val();
	} else if (brandser == 'update') {
		findbox = $('#ubrandname').val();
	} else {
		findbox = $('#sbrandname').val();
	}
	$('#brandfindbox').val(findbox);
	if (hasbranddg) {
		$("#whp_brandpp").createPage({
			pageCount: 1,
			current: 1,
			backFn: function(p) {
				getbddata(p.toString());
			}
		});
		$('#whp_brandd').dialog({
			modal: true,
			title: '选择品牌'
		});
		$('#whp_brandt').datagrid({
			idField: 'brandname',
			height: 350,
			fitColumns: true,
			striped: true,
			//隔行变色
			singleSelect: true,
			pageSize: 20,
			columns: [[{
				field: 'BRANDID',
				title: 'ID',
				width: 200,
				hidden: true
			},
			{
				field: 'ROWNUMBER',
				title: '序号',
				width: 40,
				fixed: true,
				align: 'center',
				halign: 'center',
				formatter: function(value, row, index) {
					var val = Math.ceil(Number(value) / pagecount) - 1;
					return val * pagecount + Number(index) + 1;
				}
			},
			{
				field: 'BRANDNAME',
				title: '品牌',
				width: 200
			}]],
			onDblClickRow: function(rowIndex, rowData) {
				selectbd();
			},
			toolbar: '#adbrandsbtn'
		}).datagrid("keyCtr", "selectbd()");
		hasbranddg = false;
	}
	$('#whp_brandd').dialog('open');
	getbddata('1'); //加载数据
}
function selectbd() {
	var brandser = $('#hbrandser').val();
	var rowData = $('#whp_brandt').datagrid("getSelected");
	if (rowData) {
		if (brandser == 'add') {
			$('#awbrandname').val(rowData.BRANDNAME);
			$('#awbrandid').val(rowData.BRANDID);
		}
		$('#whp_brandd').dialog('close');
	} else {
		alerttext("请选择！");
	}
}
//加载生产商列表窗
var hasprovdg = true;
function selectpdd(provser) {
	$('#hprovser').val(provser);
	var findbox = "";
	if (provser == 'add') {
		findbox = $('#awprovname').val();
	} else if (provser == 'update') {
		findbox = $('#uprovname').val();
	} else {
		findbox = $('#sprovname').val();
	}
	$('#provfindbox').val(findbox);
	if (hasprovdg) {
		$("#whp_provpp").createPage({
			pageCount: 1,
			current: 1,
			backFn: function(p) {
				getprovdata(p.toString());
			}
		});
		$('#whp_provd').dialog({
			modal: true,
			title: '选择生产商'
		});
		$('#whp_provt').datagrid({
			idField: 'PROVID',
			height: 350,
			fitColumns: true,
			striped: true,
			//隔行变色
			singleSelect: true,
			pageSize: 20,
			columns: [[{
				field: 'PROVID',
				title: 'ID',
				width: 200,
				hidden: true
			},
			{
				field: 'ROWNUMBER',
				title: '序号',
				width: 40,
				fixed: true,
				align: 'center',
				halign: 'center',
				formatter: function(value, row, index) {
					var val = Math.ceil(Number(value) / pagecount) - 1;
					return val * pagecount + Number(index) + 1;
				}
			},
			{
				field: 'PROVNAME',
				title: '生产商',
				width: 200
			}]],
			onDblClickRow: function(rowIndex, rowData) {
				selectpd();
			},
			toolbar: '#adprovsbtn'
		}).datagrid("keyCtr", "selectbd()");
		hasprovdg = false;
	}
	$('#whp_provd').dialog('open');
	getprovdata('1'); //加载数据
}
function selectpd() {
	var provser = $('#hprovser').val();
	var rowData = $('#whp_provt').datagrid("getSelected");
	if (rowData) {
		if (provser == 'add') {
			$('#awprovname').val(rowData.PROVNAME);
			$('#awprovid').val(rowData.PROVID);
		}
		$('#whp_provd').dialog('close');
	} else {
		alerttext("请选择！");
	}
}
function openaddcolord() {
	$('#color_add_dialog').dialog('open');
	$('#color_add_colorname').focus();
}
var refreshcolorcode = function() {
	selectcolor('add');
}

//添加颜色
function addcolor() {
	var colorname = $('#color_add_colorname').val();
	var colorno = $('#color_add_colorno').val();
	if (colorname == "" || colorname == "null" || colorname == "undefined") {
		alerttext("颜色名不能为空");
	} else {
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "addcolor",
				colorname: colorname,
				colorno: colorno
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值
				if (valideData(data)) {
					$('#color_add_colorname').focus();
				}
			}
		});
	}
}
//添加品牌
function addbd() {
	var brandname = $('#brand_add_brandname').val();
	if (brandname == "" || brandname == "null" || brandname == "undefined") {
		alerttext("品牌名不能为空");
	} else {
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "addbrand",
				brandname: brandname
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值
				if (valideData(data)) {
					getbddata('1');
					$('#brand_add_brandname').val("");
					$('#brand_add_brandname').focus();
				}
			}
		});
	}
}
//获取品牌列表
function getbddata(page) { //定义回调函数
	//url="wareservlet?wareser=getbranddata";
	var findbox = $('#whp_brandfindbox').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "brandhelplist",
			findbox: findbox,
			rows: pagecount,
			fieldlist: "*",
			page: page
		},
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				$('#whp_brandt').datagrid('loadData', data);
				$("#whp_brandpp").setPage({
					pageCount: Math.ceil(data.total / pagecount),
					current: Number(page)
				});
			}
		}
	});
}
//获取品牌列表
function getprovdata(page) { //定义回调函数
	//url="wareservlet?wareser=getbranddata";
	var findbox = $('#whp_provfindbox').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser : "providelisthelp",
			sort: "PROVNAME",
			order: "asc",
			noused: 0,
			fieldlist: "*",
			rows: pagecount,
			page : page,
			showall : 1,
			findbox : findbox
		},
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				$('#whp_provt').datagrid('loadData', data);
				$("#whp_provpp").setPage({
					pageCount: Math.ceil(data.total / pagecount),
					current: Number(page)
				});
			}
		}
	});
}
</script>