<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 	勾对 -->
<div id="currcheckd" title="应付款勾对" style="width: 800px; height: 400px;"  class="easyui-dialog" closed=true>
<div id="currchcek_toolbar" class="toolbar">
<div class="toolbar_box1">
<input type="hidden" id="currcheck_pnoteno">
<input type="hidden" id="currcheck_provid">
<input type="hidden" id="currcheck_houseid">
<table width="800" style="display: table;font-weight: 600;font-size: 14px;">
<tr>
<td align="right" width="80">
收款金额：
</td>
<td id="currcheck_curr" align="left" width="100">
&nbsp;
</td>
<td align="right" width="80">
勾对金额：
</td>
<td id="currcheck_gdcurr0" align="left" width="100">
&nbsp;
</td>
<td align="right" width="80">
未勾金额：
</td>
<td id="currcheck_balcurr" align="left" width="100">
&nbsp;
</td>
</tr>
</table>
</div>
</div>
<table id="currcheckt"></table>
<div class="page-bar">
	<div class="page-total">
	<span>共有<span id="currcheck_total">0</span>条记录</span>
	<span style="color:#ff7900">回车：勾对或取消  双击：查看单据</span>
	</div>
	<div id="currcheckpp" class="tcdPageCode page-code">
	</div>
</div>
</div>
<script type="text/javascript">
function setcurrcheckt(){
	$('#currcheckpp').createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			paycurrlink(p);
		}
	});
	$('#currcheckt').datagrid({
		idField: 'NOTENO',
		width: '100%',
		toolbar: "#currchcek_toolbar",
		height: 310,
		fitColumns: true,
		striped: true, //隔行变色
		singleSelect: true,
		showFooter: true,
		columns: [
			[{
				field: 'ROWNUMBER',
				title: '序号',
				width: 30,
				fixed: true,
				halign: 'center',
				align: 'center',
				formatter: function(value, row, index) {
					var rn = Number(value);
					if (!isNaN(rn))
						return rn;
					else return "";
				}
			}, {
				field: 'NOTEDATE',
				title: '单据日期',
				width: 110,
				fixed: true,
				halign: 'center',
				align: 'center',
				formatter: function(value, row, index) {
					return row.NOTEDATE == undefined ? '' : row.NOTEDATE.substring(0, 16);
				}
			}, {
				field: 'NOTENO',
				title: '单据号',
				width: 120,
				fixed: true,
				halign: 'center',
				align: 'center'
			}, {
				field: 'TOTALCURR',
				title: '总金额',
				width: 100,
				fixed: true,
				halign: 'center',
				align: 'right',
				formatter: currfm2
			}, {
				field: 'GDCURR',
				title: '已勾合计',
				width: 80,
				fixed: true,
				halign: 'center',
				align: 'right',
				formatter: currfm2
			}, {
				field: 'BALCURR',
				title: '未勾合计',
				width: 80,
				fixed: true,
				halign: 'center',
				align: 'right',
				formatter: currfm2
			}, {
				field: 'GDCURR0',
				title: '本单勾对',
				width: 80,
				fixed: true,
				halign: 'center',
				align: 'right',
				formatter: currfm2
			}, {
				field: 'ACTION',
				title: '操作',
				width: 80,
				fixed: true,
				halign: 'center',
				align: 'center',
				formatter: function(value,row,index){
					if(row.NOTEDATE=="合计") return "";
					var gdcurr0 = Number(row.GDCURR0);
					if(gdcurr0!=0)
						return '<input type="button" class="m-btn" value="取消勾对" onclick="paycurrcheck(' + index + ')">';
					return '<input type="button" class="m-btn" value="勾对" onclick="paycurrcheck(' + index + ')">';
				}
			}]
		],
		onDblClickRow: function(index, row) {
			var noteno = row.NOTENO;
			if (noteno.length > 0 && detail_bol == true) {
				var pgno = noteno.substring(0, 2);
				opendetaild(pgno, noteno);
			}
		},
		onDblClickCell: function(index, field, value) {
			if (field == "ACTION") {
				detail_bol = false;
			} else detail_bol = true;
		},
		pageSize: 20
	}).datagrid("keyCtr", "paycurrcheck()");
}
//打开勾对窗
function opencurrchcek(index){
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	var row = rows[index];
	var pnoteno = row.NOTENO;
	var curr = row.CURR;
	var provid = row.PROVID;
	var houseid = row.HOUSEID;
	var curr = Number(row.CURR);
	$("#currcheck_pnoteno").val(pnoteno);
	$("#currcheck_provid").val(provid);
	$("#currcheck_houseid").val(houseid);
	$("#currcheck_curr").html(curr.toFixed(2));
	if($("#currcheckt").data().datagrid==undefined){
		setcurrcheckt();
	}
	paycurrlink(1);
	var checkman = row.CHECKMAN;
	var title = "应付款勾对";
	if(checkman.length>0){
		 title = "浏览应付款勾对";
	}
	$("#currcheckd").dialog("setTitle",title).dialog("open");
}
function paycurrlink(page){
	var provid = $('#currcheck_provid').val();
	var houseid = $('#currcheck_houseid').val();
	var pnoteno = $('#currcheck_pnoteno').val();
	if(provid.length==0&&houseid.length===0&&pnoteno.length==0){
		alerttext("无效付款单据！");
		return;
	}
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "paycurrlink",
			houseid: Number(houseid),
			provid: Number(provid),
			pnoteno: pnoteno,
			rows: pagecount,
			page: page
		},
		//这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			if (valideData(data)) {
				$("#currcheck_total").html(data.total);
				$("#currcheckpp").setPage({
					pageCount: Math.ceil(data.total / pagecount),
					current: Number(page)
				});
				data.footer = [{
					NOTEDATE: "合计",
					TOTALCURR: data.totalcurr,
					GDCURR: data.gdcurr,
					BALCURR: data.balcurr,
					GDCURR0: data.gdcurr0
				}];
				var curr = new Decimal($('#currcheck_curr').html());
				$("#currcheck_gdcurr0").html(Number(data.gdcurr0).toFixed(2));
				$("#currcheck_balcurr").html(curr.sub(Number(data.gdcurr0)).toFixed(2));
				$("#currcheckt").datagrid("loadData",data);
			}
		}
	});
}
//勾对金额
function paycurrcheck(index){
	var $dg = $("#currcheckt");
	if(index>=0)
		$dg.datagrid("selectRow",index);
	var checkman = $("#gg").datagrid("getSelected").CHECKMAN;
	if(checkman.length>0){
		return;
	}
	var row = $dg.datagrid("getSelected");
	var pnoteno = $("#currcheck_pnoteno").val();
	var curr = new Decimal($("#currcheck_balcurr").html());
	var noteno = row.NOTENO;
	var totalcurr = new Decimal(row.TOTALCURR);
	var gdcurr = new Decimal(row.GDCURR);
	var balcurr = new Decimal(row.BALCURR);
	var gdcurr0 = new Decimal(row.GDCURR0);
	if(curr>balcurr&&balcurr>0)
		curr = balcurr;
	var opid = 1;
	if(gdcurr0!=0){
		opid=0;
		curr = gdcurr0;
	}
	if(opid==1&&curr==0){
		alerttext("当前付款单金额已全部勾对！");
		return;
	}
	var fs = row.FS;
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "paycurrcheck",
			noteno: noteno,
			curr: curr.valueOf(),
			fs: fs,
			opid: opid,
			pnoteno: pnoteno
		},
		//这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			try {
				if (valideData(data)) {
					var index = $dg.datagrid("getRowIndex",noteno);
					curr = new Decimal(data.msg);
					var footers = $dg.datagrid("getFooterRows");
					var footer = footers[0];
					var t_gdcurr = new Decimal(footer.GDCURR);
					var t_balcurr = new Decimal(footer.BALCURR);
					var t_gdcurr0 = new Decimal(footer.GDCURR0);
					var t_curr = new Decimal($("#currcheck_curr").html());
					if(opid==0){
						row.GDCURR = gdcurr.sub(curr).valueOf();
						row.BALCURR = balcurr.add(curr).valueOf();
						row.GDCURR0 = gdcurr0.sub(curr).valueOf();
						footer.GDCURR = t_gdcurr.sub(curr).valueOf();
						footer.BALCURR = t_balcurr.add(curr).valueOf();
						footer.GDCURR0 = t_gdcurr0.sub(curr).valueOf();
						$("#currcheck_gdcurr0").html(Number(footer.GDCURR0).toFixed(2));
						$("#currcheck_balcurr").html(t_curr.add(footer.GDCURR0).toFixed(2));
					}else{
						row.GDCURR = gdcurr.add(curr).valueOf();
						row.BALCURR = balcurr.sub(curr).valueOf();
						row.GDCURR0 = gdcurr0.add(curr).valueOf();
						footer.GDCURR = t_gdcurr.add(curr).valueOf();
						footer.BALCURR = t_balcurr.sub(curr).valueOf();
						footer.GDCURR0 =  t_gdcurr0.add(curr).valueOf();
						$("#currcheck_gdcurr0").html(Number(footer.GDCURR0).toFixed(2));
						$("#currcheck_balcurr").html(t_curr.sub(footer.GDCURR0).toFixed(2));
					}
					$dg.datagrid('reloadFooter').datagrid("refreshRow",index);
					var gindex = $("#gg").datagrid("getRowIndex",pnoteno);
					$("#gg").datagrid("updateRow",{
						index: gindex,
						row: {
							GDCURR: footer.GDCURR0
						}
					});
				}
			} catch (e) {
				// TODO: handle exception
				console.log(e.message);
				top.wrtiejsErrorLog(e.message);
			}
		}
	});
}
</script>