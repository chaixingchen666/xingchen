<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 费用项目表 -->
<div id="salecostd" title="费用项目"  data-options="modal:true" style="width: 350px; height: 400px;" class="easyui-dialog" closed="true">
<div id="salecost_toolbar" class="toolbar" style="margin: 5px 0;">
<input type="button" class="btn1" value="增加" onclick="$('#addsalecostd').dialog('open');">
<input type="button" class="btn1" value="删除" onclick="delsalecost();">
</div>
<table id="salecostt"></table>
</div>
<!-- 添加费用项目表 -->
<div id="addsalecostd" title="添加费用项目" data-options="modal:true" style="width: 300px; height: 200px;" class="easyui-dialog" closed="true">
<div class="textcenter" style="overflow: hidden;zoom:1;">
<input type="hidden" id="salecgid" value="0">
<table width="260" border="0" cellspacing="10" class="table1">
			<tr align="right">
				<td class="header skyrequied">费用名称</td>
				<td>
				<select name="salecgname" id="salecgname" class="sele1">
				<option value="0">--请选择--</option>
				</select>
				</td>
			</tr>
			<tr align="right">
				<td width="100" class="header skyrequied">费用金额</td>
				<td width="160"> 
					<input class="wid160 hig25 onlyMoney" type="text" maxlength="7" placeholder="<输入>" id="salecgcurr" data-enter="addsalecost();"/>
				</td> 
			</tr>
		</table>
</div>
<div class="dialog-footer" style="text-align: center;">
	<input class="btn1" type="button" id="add" type="button" value="保存" onclick="addsalecost(true);">
	<input class="btn1" type="button" id="addcontinue" type="button" value="保存并继续" onclick="addsalecost(false);">
</div>
</div>
<script type="text/javascript">
var hascostdg = true;	
function selectsalecost(){
	if(!$('#salecostt').data().datagrid){
		$('#salecostt').datagrid({
			height: $('#salecostd').height(),
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
			},{
				field : 'NOTENO',
				title : 'NOTENO',
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
				formatter : function(value, row, index) {
					if(value!=''&&value!=undefined)
						return Number(value).toFixed(2);
				}
			} ] ],
		toolbar:'#salecost_toolbar'
		}).datagrid("keyCtr","");
		getcglist();
		hascostdg = false;
	}
	var stg= Number($('#ustatetag').val());
	if(stg>=1){
		$('#salecost_toolbar').hide();
	}else{
		$('#salecost_toolbar').show();
	}
	$('#salecostt').datagrid("resize");
	getsalecostlist('1');
	$('#salecostd').dialog('open');
	
}
function getsalecostlist(page){
	var noteno = $('#unoteno').val();
	var _totalcost = Number($('#usaletotalcost').val());
	alertmsg(6);
	var erpser = "";
	if(pgid=="pg1302"||pgid=="pg1303") erpser="wareoutcostlist";
	else if(pgid=="pg1102"||pgid=="pg1103") erpser="wareincostlist";
	else{
		alerttext("未知程序，请检查！");
	}
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : erpser,
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
function addsalecost(bol){
	alertmsg(2);
	var cgid = Number($('#salecgname').val());
	var curr = Number($('#salecgcurr').val());
	var noteno = $('#unoteno').val();
	if(cgid<=0)
		alerttext('请选择费用项目');
	else if(curr==0)
		alerttext('费用不能为0');
	else{
		if(pgid=="pg1302"||pgid=="pg1303") erpser="addwareoutcost";
		else if(pgid=="pg1102"||pgid=="pg1103") erpser="addwareincost";
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
				cgid : cgid,
				curr : curr
			}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType : 'json',
			success : function(data) { //回调函数，result，返回值 
				try{
					if (valideData(data)) {
						$('#salecgname').focus();
						getsalecostlist('1');
						$('#salecgcurr').val('');
						if(bol) $('#addsalecostd').dialog('close');
					}
				}catch(err){
					console.error(err.message);
				}
			}
		});
	}
}
function delsalecost(){
	var rowdata = $('#salecostt').datagrid('getSelected');
	if(rowdata){
		var id = rowdata.ID;
		var noteno = rowdata.NOTENO;
		if(id<=0)
			alerttext('请选择费用项目记录');
		else{
			$.messager.confirm(dialog_title,'是否删除'+rowdata.CGNAME+'费用？',function(r){    
				if (r){ 
					if(pgid=="pg1302"||pgid=="pg1303") erpser="delwareoutcostbyid";
					else if(pgid=="pg1102"||pgid=="pg1103") erpser="delwareincostbyid";
					else{
						alerttext("未知程序，请检查！");
						return;
					}
					alertmsg(2);
					$.ajax({
						type : "POST", //访问WebService使用Post方式请求 
						url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
						data : {
							erpser : erpser,
							id : id,
							noteno : noteno
						}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
						dataType : 'json',
						success : function(data) { //回调函数，result，返回值 
							try{
								if (valideData(data)) {
									getsalecostlist('1');
								}
							}catch(err){
								console.error(err.message);
							}
						}
					});
			}	
			});
		}
	}else
		alerttext('请选择费用记录！');
}

function getcglist(){
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "chargeslist",
			rows: 50,
			page : 1,
			noused: 0,
			findbox : ""
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			try{
				if (valideData(data)) {
					var rows = data.rows;
					for(var row in rows){
						$('#salecgname').append("<option value="+rows[row].CGID+">"+rows[row].CGNAME+"</option>");
					}
				}
			}catch(err){
				console.error(err.message);
			}
		}
	});
}
</script>