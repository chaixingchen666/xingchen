<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- （有折扣）快速编辑窗体 -->
<div id="quickud" title="修改商品明细" style="text-align: center;max-width:100%;max-height:100%;min-width:600px;" class="easyui-dialog" closed="true">
<input type="hidden" id="wquickuid" >
<div style="width: auto;overflow: auto;text-align: center;position: relative;left: 0;top: 0;">
<form onsubmit="return false;" id="quickuaddwaref" action="" method="get" class="quickkeymsg">
  <span>输入数量后：Ctrl+A：所有数量一致；Ctrl+S：设置之后同颜色一致；Ctrl+D：设置之后同尺码数量一致；Ctrl+Z:取消所有数量输入</span><span id="wareoutonlymsg">；Ctrl+K:查询此款其他店铺库存</span>。
</form>
<form onsubmit="return false;" action="" method="get" id="quickutable">
</form>
</div>
<form onsubmit="return false;" id="changedp" action="" method="get">
<div class="dialog-footer" style="text-align: center;display: block;position: relative;">
<div class="ml10 s-box" id="span_sale" style="display: inline;width:80px;">
	<div class="text" style="width:80px;">
	<input type="text" id="wquickusalename"  class="edithelpinput wid60 sale_help" placeholder="选择销售类型"  data-end="yes" data-value="qaddwarem" value="首单" ><span onclick="selectsaletype('qaddwarem')"></span>
	</div>
	</div>
	<span class="ml10 span_wlhistory"><label><input id="showcusthistory" type="checkbox">显示销售历史</label></span>
	<span class="ml10">同款折扣<input id="alldisc" class="ml10 wid40 hig25 onlyMoney" type="text" placeholder="折扣" maxlength="4" onkeyup="this.value=Number(this.value)>=10?10:this.value"></span>
	<span class="ml10">同款单价<input id="allpri" class="ml10 wid60 hig25 onlyMoney" type="text" placeholder="折后价" maxlength="7"></span>
<span><input id="quickadd_btn" type="button" class="btn1" value="保存"></span>
<!-- 	<span><input type="button" class="btn1" onclick="opencustwarehistoryd()" value="查看销售历史"></span> -->
</div>
</form>
<div class="hide" id="custwarehistorydiv" style="clear:both">
<table id="custwarehistoryt"></table>
<div style="margin: 5px">
<div id="custwarehistorypp"  class="tcdPageCode fl"></div>
<div class="fl ml10"><label><input id="showdpkh" type="checkbox">只显示本店铺和本客户</label></div>
</div>
</div>
</div>
	<!-- 客户拿货历史 -->
<div id="custwarehistoryd" title="商品拿货历史" style="width: 800px; height:400px" class="easyui-dialog" closed="true">

</div>
	<!-- 其他店铺库存 -->
<div id="otherhousekcd" title="其他店铺库存" style="width: 700px;" class="easyui-dialog" closed="true">
<input type="hidden" id="othercolorid" value="">
<table id="otherhousekct"></table>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total">共有<span id="otherhousekcpp_total">0</span>条记录</div>
	<div id="otherhousekcpp" class="tcdPageCode page-code">
	</div>
</div>
</div>
<script type="text/javascript">

//打开历史窗
function opencustwarehistoryd(){
	if($('#custwarehistoryt').data().datagrid==undefined){
		setcustwarehistt();
	}
	$('#custwarehistoryd').dialog('open');
	listsalehistory(1);
}

$('#showcusthistory').change(function(){
	if($('#custwarehistoryt').data().datagrid==undefined){
		setcustwarehistt();
	}
	var $this = $(this);
	var showcusthistory = 0;
	if($this.prop('checked'))
		showcusthistory = 1;
	var $div = $('#custwarehistorydiv');
	if(showcusthistory==1){
		$div.show();
		$('#custwarehistoryt').datagrid('resize');
		$('#quickud').window('center');
		listsalehistory(1);
	}else{
		$div.hide();
		$('#quickud').window('center');
	}
	setparam({
		pgid: pgid.replace("pg",""),
		usymbol: "showcusthistory",
		uvalue: showcusthistory
	});
	$('#u1-1').focus();
});
$("#showdpkh").change(function(){
	listsalehistory(1);
});
$(function(){
	var showcusthistory = Number(getparam({pgid: pgid.replace("pg",""),
		usymbol: "showcusthistory"}));
	if(showcusthistory==1){
		$('#showcusthistory').prop('checked',true);
	}
	if(pgid=="pg1302"||pgid=="pg1303"||pgid=="pg1309")
		$("#wareoutonlymsg").show();
	else
		$("#wareoutonlymsg").hide();
});
//初始化拿货历史表；
function setcustwarehistt(){
	$('#custwarehistorypp').createPage({
		pageCount:1,
        current:1,
        backFn:function(p){
        	listsalehistory(p);
        }
	 });
	$('#custwarehistoryt').datagrid(
		{
			idField : 'ID',
			title: "客户销售历史(双击查看明细)",
			height: 240,
			fitColumns: true,
			nowrap: false,
			striped : true, //隔行变色
			singleSelect: true,
			frozenColumns:[[
			{
				field : 'NTID',title : 'NTID',width : 200,hidden:true
			},{
				field : 'ID',title : 'ID',width : 200,hidden:true
			},{
				field : 'ROWNUMBER',title: '序号',width: 50,fixed:true,align:'center',halign:'center',
				formatter:function(value,row,index){
					var val = Math.ceil(Number(value)/ pagecount)-1;
					return isNaN(Number(value))||value==""?"":val*pagecount+Number(index)+1;
				}
			},{
				field : 'NOTENO',	title : '单据号',width : 115,fixed:true,align:'center',halign:'center'
			},{
				field : 'NOTEDATE',	title : '单据日期',width : 100,fixed:true,align:'center',halign:'center'
			}]],
			columns: [ [
			{
				field : 'COLORNAME',title : '颜色',width : 80,fixed:true,align:'center',halign:'center'
			}, {
				field : 'AMOUNT',title : '数量',width : 60,fixed:true,align:'center',halign:'center',formatter: amtfm
			}, {
				field : 'PRICE0',title : '原价',width : 80,fixed:true,align:'right',halign:'center',hidden: (hideyj==1?true:false),formatter: currfm
			}, {
				field : 'DISCOUNT',title : '折扣',width : 50,fixed:true,align:'center',halign:'center',hidden: (hideyj==1?true:false),formatter: currfm
			}, {
				field : 'PRICE',title : '单价',width : 80,fixed:true,align:'right',halign:'center',formatter: currfm
			}, {
				field : 'CURR',title : '金额',width : 80,fixed:true,align:'right',halign:'center',formatter: currfm
			}, {
				field : 'REMARK',title : '备注',width : 150,fixed:true,align:'center',halign:'center'
			}]],
			onDblClickRow : function(index, row){
		   		var noteno = row.NOTENO;
		   		var ntid = Number(row.NTID);
		   		var pgno = "XS";
		   		if(ntid==2)
		   		 	pgno = "XT";
		   		if(noteno)
		   			opendetaild(pgno,noteno);
			}
		});
}

//获取销售历史数据
function listsalehistory(page){
	var custid = $('#ucustid').val();
	var wareid = $('#wquickuwareid').val();
	var erpser = "listsalehistory";
	var ajaxdata = {
			erpser: erpser,
			custid: custid,
			wareid: wareid,
			rows: pagecount,
			page: page
		};
	var showdpkh = $("#showdpkh").prop("checked");
	if(showdpkh){
		ajaxdata.houseid = $("#uhouseid").val();
	}
	$('#custwarehistoryt').datagrid('loadData', nulldata);
	alertmsg(2);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : ajaxdata, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					$("#custwarehistorypp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$('#custwarehistoryt').datagrid('loadData', data);
				}
			}catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
var $tagipt;
function openotherhousekct($ipt){
	if(!$("#otherhousekct").data().datagrid){
		$('#otherhousekcpp').createPage({
			pageCount:1,
	        current:1,
	        backFn:function(p){
	        	listwareandcolor(p);
	        }
		 });
		$("#otherhousekct").datagrid({
			idField : 'HOUSEID',
			height: 240,
			fitColumns: true,
			nowrap: false,
			striped : true, //隔行变色
			singleSelect: true,
			showFooter: true,
			frozenColumns:[[
			{
				field : 'ROWNUMBER',title: '序号',width: 50,fixed:true,align:'center',halign:'center',
				formatter: function(value, row, index) {
					if(isNaN(Number(value))&&value!=undefined&&value.length>0)
						return value;
					var p = $("#pp").getPage() - 1;
					return p * pagecount + index + 1;
				}
			},{
				field : 'HOUSENAME',title : '店铺',width : 115,fixed:true,align:'center',halign:'center'
			}]]
		});
		$("#otherhousekcd").dialog({
			onClose: function(){
				$tagipt.focus();
			}
		});
	}
	$tagipt = $ipt;
	totalwareandcolor();
}
var sumsql = "";
function totalwareandcolor(){
	var houseid = $('#uhouseid').val();
	var wareid = $('#wquickuwareid').val();
	var colorid = $('#othercolorid').val();
	var erpser = "totalwareandcolor";
	alertmsg(2);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser: erpser,
			nohouseid: houseid,
			wareid: wareid,
			colorid: colorid
		}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					var columns = [];
					var sizejson = data.sizelist;
					for(var i in sizejson){
						var row = sizejson[i];
						columns.push({
							field: "AMOUNT"+row.SIZEID,
							title: row.SIZENAME,
							width : 50,
							fixed: true,
							align: 'center',
							halign: 'center',
							formatter: amtfm
						});
					}
					columns.push({
						field: "AMOUNT0",
						title: "合计",
						width : 50,
						fixed: true,
						align: 'center',
						halign: 'center',
						formatter: amtfm
					});
					$("#otherhousekct").datagrid("options").columns = [columns];
					$("#otherhousekct").datagrid();
					sumsql = data.sumsql;
					$("#otherhousekcd").dialog("setTitle",data.WARENO+","+data.COLORNAME+"其他店铺库存");
					listwareandcolor(1);
				}
			}catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
function listwareandcolor(page){
	var erpser = "listwareandcolor";
	alertmsg(2);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser: erpser,
			sumsql: sumsql,
			rows: pagecount,
			page: page
		}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					if(data.total==0){
						alerttext("其他店铺不存在该颜色的库存！");
						return;
					}
					$("#otherhousekcpp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$("#otherhousekcpp_total").html(data.total);
					data.footer = [{
						ROWNUMBER: "合计",
						AMOUNT0: data.totalamt
					}];
					$("#otherhousekct").datagrid("loadData",data);
					$("#otherhousekcd").dialog("open");
					$("#otherhousekcd input").first().focus();
				}
			}catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
</script>
