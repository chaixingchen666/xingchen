<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 选择xls窗 -->
<div id="downxlsd" title="请选择下载类型" data-options="modal:true"  style="width: 400px; height: 300px" class="easyui-dialog" closed="true">
<div style="margin: 10px;text-align: center;">
<table class="table1" cellspacing="10">
<tr>
<td>
<input type="button" class="btn1" value="下载明细（竖向尺码）" onclick="exportmxxls(0)"> 
</td>
</tr>
<tr>
<td>
<input type="button" class="btn1" value="下载明细（横向尺码）" onclick="exportmxxls(1)"> 
</td>
</tr>
<tr>
<td>
<input type="button" class="btn1" value="下载单据+明细（横向尺码）" onclick="exportxlsmx()"> 
</td>
</tr>
</table>
</div>
<div class="dialog-footer">
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#downxlsd').dialog('close')">
</div>
</div>
<script type="text/javascript">
var pgnameobj = {
		pg1101: "采购订单",
		pg1102: "采购入库",
		pg1103: "采购退货",
		pg1301: "客户订单",
		pg1302: "批发开票",
		pg1303: "批发退库",
		pg1309: "批发配货",
		pg1402: "调拨出库",
		pg1403: "调拨入库",
		pg1501: "临时盘点",
		pg1508: "期初入库"
};
var erpserobjc = {
		pg1101: "provordermcolorsumlist",
		pg1102: "wareinmcolorsumlist",
		pg1103: "wareinmcolorsumlist",
		pg1301: "custordermcolorsumlist",
		pg1302: "wareoutmcolorsumlist",
		pg1303: "wareoutmcolorsumlist",
		pg1309: "warepeimsumlist",
		pg1402: "allotoutmcolorsumlist",
		pg1403: "allotinmcolorsumlist",
		pg1501: "tempcheckmcolorsumlist",
		pg1508: "firsthousemcolorsumlist"
};
var erpserobj = {
		pg1101: "provordermlist",
		pg1102: "wareinmlist",
		pg1103: "wareinmlist",
		pg1301: "custordermlist",
		pg1302: "wareoutmlist",
		pg1303: "wareoutmlist",
		pg1309: "warepeimlist",
		pg1402: "allotoutmlist",
		pg1403: "allotinmlist",
		pg1501: "tempcheckmlist",
		pg1508: "firsthousemlist"
};
var fieldlist = {
		pg1101: "a.id,a.accid,a.price0,a.discount,b.wareid,b.units,b.imagename0,b.retailsale"
			+",c.colorid,d.sizeid,a.noteno,a.amount,a.price,a.curr,b.wareno,b.warename,c.colorname,d.sizename",
		pg1102: "a.id,a.accid,a.price0,a.discount,b.wareid,b.units,b.imagename0,b.retailsale"
			+",c.colorid,d.sizeid,a.noteno,a.amount,a.price,a.curr,b.wareno,b.warename,c.colorname,d.sizename",
		pg1103: "a.id,a.accid,a.price0,a.discount,b.wareid,b.units,b.imagename0,b.retailsale"
			+",c.colorid,d.sizeid,a.noteno,a.amount,a.price,a.curr,b.wareno,b.warename,c.colorname,d.sizename",
		pg1302: "a.id,a.accid,a.price0,a.discount,b.wareid,b.units,b.imagename0,b.retailsale"
			+",c.colorid,d.sizeid,e.salename,a.noteno,a.amount,a.price,a.curr,b.wareno,b.warename,c.colorname,d.sizename",
		pg1303: "a.id,a.accid,a.price0,a.discount,b.wareid,b.units,b.imagename0,b.retailsale"
			+",c.colorid,d.sizeid,e.salename,a.noteno,a.amount,a.price,a.curr,b.wareno,b.warename,c.colorname,d.sizename",
		pg1309: "a.id,a.accid,a.price0,a.discount,b.wareid,b.units,b.imagename0,b.retailsale"
			+",c.colorid,d.sizeid,e.salename,a.noteno,a.amount,a.price,a.curr,b.wareno,b.warename,c.colorname,d.sizename",
		pg1402: "a.id,a.accid,b.wareid,b.units,b.imagename0,b.retailsale"
			+",c.colorid,d.sizeid,a.noteno,a.amount,a.price,a.curr,b.wareno,b.warename,c.colorname,d.sizename",
		pg1403: "a.id,a.accid,b.wareid,b.units,b.imagename0,b.retailsale"
			+",c.colorid,d.sizeid,a.noteno,a.amount,a.price,a.curr,b.wareno,b.warename,c.colorname,d.sizename",
		pg1501: "a.id,a.accid,b.wareid,b.units,b.imagename0,b.retailsale"
			+",c.colorid,d.sizeid,a.noteno,a.amount,a.price,a.curr,b.wareno,b.warename,c.colorname,d.sizename",
		pg1508: "a.id,a.accid,b.wareid,b.units,b.imagename0,b.retailsale"
			+",c.colorid,d.sizeid,a.noteno,a.amount,a.price,a.curr,b.wareno,b.warename,c.colorname,d.sizename"
};
function openexportdialog(){
	var total = $('#waret').datagrid('getRows').length;
	if (total == 0) {
		alert('数据为空，不能导出！');
	} else {
		$("#downxlsd").dialog("open");
	}
}
//导出明细
function exportmxxls(xlstype) {
		var noteno = $("#unoteno").val();
		var sortid = $('#waremsort').prop('checked') ? 1 : 0;
		var order = $('div.div_sort .label_sort').hasClass('icon_jiantou_up') ? "asc": "desc";
		var mxdata = {
				erpser: erpserobjc[pgid],
				sizenum: sizenum,
				noteno: noteno,
				sortid: sortid,
				order: order,
				rows: pagecount
			};
		var filelname = pgnameobj[pgid]+noteno+"明细";
		var sizecol = $("#waret").datagrid("getColumnOption","SIZENAME");
		var barcode = $("#waret").datagrid("getColumnOption","BARCODE");
		var amountcol = $("#waret").datagrid("getColumnOption","AMOUNT");
		if(xlstype==0){
			sizecol.expable = true;
			if(barcode)
				barcode.expable = true;
			amountcol.title = "数量";
			for(var i =1;i<=sizenum;i++){
				var amtcol = $("#waret").datagrid("getColumnOption","AMOUNT"+i);
				amtcol.expable = false;
			}
			mxdata = {
					erpser: erpserobj[pgid],
					noteno: noteno,
					sortid: sortid,
					order: order,
					rows: pagecount,
					fieldlist: fieldlist[pgid]
				};
			fyexportxls(1,'#waret',mxdata,'api',-1,filelname+"(竖向)");
		}else{
			sizecol.expable = false;
			if(barcode)
				barcode.expable = false;
			amountcol.title = "小计";
			for(var i =1;i<=sizenum;i++){
				var amtcol = $("#waret").datagrid("getColumnOption","AMOUNT"+i);
				amtcol.expable = true;
			}
			fyexportxls(1,'#waret',mxdata,'api',1,filelname+"(横向)");
		}
		$("#downxlsd").dialog("close");
}
//导出单据+明细
function exportxlsmx(){
	var url = "/skydesk/noteexportservice";
	var noteno = $("#unoteno").val();
	var sortid = $('#waremsort').prop('checked') ? 1 : 0;
	var order = $('div.div_sort .label_sort').hasClass('icon_jiantou_up') ? "asc": "desc";
	var expdata = {
		exportid: pgid,
		noteno: noteno,
		sortid: sortid,
		order: order,
		sizenum: sizenum,
		rows: 50
	};
	DownLoadFile({
		//请求的url
		url: url,
		method: 'POST',
		data: expdata
	});
}
</script>