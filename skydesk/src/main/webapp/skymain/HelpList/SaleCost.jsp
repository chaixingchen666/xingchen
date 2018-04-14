<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="/skydesk/js/easyui/extends/datagrid-cellediting.js?ver=<%=tools.DataBase.VERSION%>"></script>
<!-- 费用项目表 -->
<div id="salecostd" title="费用项目"  data-options="modal:true" style="width: 350px;" class="easyui-dialog" closed="true">
<input type="hidden" id="salecostindex" value="-1">
<table id="salecostt"></table>
<div class="dialog-normal-footer" style="text-align: center;" id="costfooter">
<input class="btn1" type="button" type="button" value="保存" onclick="savecost()">
</div>
</div>
<script type="text/javascript">
var hascostdg = true;	
function selectsalecost(){
	if(!$('#salecostt').data().datagrid){
		$('#salecostt').datagrid({
			height: 350,
			striped : true, //隔行变色
			singleSelect : true,
			showFooter : true,
			fitColumns:true,
			pageSize:20,
			columns : [ [ {
				field : 'ROWNUMBER',
				title : '序号',
				width : 50,
				fixed : true,
				align : 'center',
				halign : 'center',
				formatter : function(value, row, index) {
					if(value!=''&&value!=undefined&&value!='合计'){
						return index + 1;
					} else {
						return value;
					}
				}
			},{
				field : 'ID',
				title : 'ID',
				width : 200,
				hidden : true
			},{
				field : 'CGID',
				title : 'CGID',
				width : 200,
				hidden : true
			}, {
				field : 'CGNAME',
				title : '费用名称',
				halign : 'center',
				align : 'center',
				width : 200
			}, {
				field : 'CURR',
				title : '金额',
				width : 200,
				halign : 'center',
				align : 'right',
				editor: 'text',
				formatter : function(value, row, index) {
					if(value!=''&&value!=undefined)
						return Number(value).toFixed(2);
				}
			} ] ],
			onBeforeEdit: function(index,row){
				$("#salecostindex").val(index);
			},
			onEndEdit: function(index,row,changes){
				var rows = $('#salecostt').datagrid('getRows');
				var totalcurr = new Decimal(0);
				for(var i in rows){
					var row = rows[i];
					totalcurr = totalcurr.add(row.CURR);
				}
				$('#salecostt').datagrid('updateFooter', {
					CURR: totalcurr.valueOf()
				});
				$("#salecostindex").val(-1);
			}
		}).datagrid('enableCellEditing');
		hascostdg = false;
	}
	var editable = $("#delete").is(":hidden");
	var currcol = $('#salecostt').datagrid("getColumnOption","CURR");
	var title = "费用项目";
	if(editable){
		$('#costfooter').hide();
		currcol.editor = undefined;
		title = "浏览费用项目";
	}else{
		$('#costfooter').show();
		currcol.editor = "text";
		title = "费用项目(点击单元格编辑)";
	}
	$('#salecostt').datagrid();
	getsalecostlist('1');
	$('#salecostd').dialog("setTitle",title).dialog('open');
}
function getsalecostlist(page){
	var noteno = $('#unoteno').val();
	var _totalcost = Number($('#usaletotalcost').val());
	alertmsg(6);
	var erpser = "";
	if(pgid=="pg1302"||pgid=="pg1303") erpser="listwareoutcost";
	else if(pgid=="pg1102"||pgid=="pg1103") erpser="listwareincost";
	else{
		alerttext("未知程序，请检查！");
	}
	var all = 0;
	var stg = $("#ustatetag").val();
	if(stg==0) all = 1;
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : erpser,
			all: all,
			rows: pagecount,
			page : page,
			noteno : noteno
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			try{
				if (valideData(data)) {
					var totals = data.total;
					var salecurr = data.totalcurr;
					$('#usaletotalcost').val(Number(salecurr).toFixed(2));
					data.footer = [{
						ROWNUMBER: "合计",
						CURR: data.totalcurr
					}];
					$('#salecostt').datagrid('loadData', data);
					var len = $('#salecostt').datagrid('getRows').length;
					if(len>0){
						$('#salecostt').datagrid("selectRow",0);	
					}
					var totalcost = Number(salecurr);
					if(pgid=="pg1302" || pgid=="pg1103"|| pgid=="pg1102"){//批发出库|采购入库|采购退库
						dqcurr = Number(dqcurr) - _totalcost+ totalcost;	
						dqzcurr= Number(dqzcurr) - _totalcost + totalcost;
					}else if(pgid=="pg1303" ){//批发退货
						dqcurr = Number(dqcurr) + _totalcost - totalcost;	
						dqzcurr= Number(dqzcurr) + _totalcost - totalcost;
					}
					dqindex = $('#gg').datagrid('getRowIndex',noteno);
					$('#gg').datagrid('updateRow',{
						index : dqindex,
						row : {
							TOTALCURR : dqcurr
						}
					});
					$('#gg').datagrid('updateFooter',{
						TOTALCURR : dqzcurr
					});
					$('#gg').datagrid('refresh');
				}
			}catch(err){
				console.error(err.message);
			}
		}
	});
}
function savecost(){
	alertmsg(2);
	var $dg = $('#salecostt');
	var index = $("#salecostindex").val();
	if(index>=0) $dg.datagrid("endEdit",index);
	var changerows = $('#salecostt').datagrid('getRows');
	var costlist = [];
	var noteno = $('#unoteno').val();
	for(var i in changerows){
		var row = changerows[i];
		var cgid = row.CGID;
		var curr = Number(row.CURR);
		if(isNaN(curr)){
			alerttext(row.CGNAME+"金额不是数字！");
			return;
		}else{
			costlist.push({
				cgid: cgid,
				curr: curr
			});
		}
	}
	if(costlist.length>0){
		if(pgid=="pg1302"||pgid=="pg1303") erpser="savewareoutcost";
		else if(pgid=="pg1102"||pgid=="pg1103") erpser="savewareincost";
		else{
			alerttext("未知程序，请检查！");
			return;
		}
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data : {
				erpser : erpser,
				noteno : noteno,
				costlist : JSON.stringify(costlist)
			}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType : 'json',
			success : function(data) { //回调函数，result，返回值 
				try{
					if (valideData(data)) {
						getsalecostlist(1);
						$("#salecostd").dialog("close");
					}
				}catch(err){
					console.error(err.message);
				}
			}
		});
	}
}

</script>