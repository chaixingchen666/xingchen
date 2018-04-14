<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 供应商帮助列表 -->
<div id="provhelpd" title="供应商帮助列表" style="width: 530px; text-align: center; height: 440px"
	class="easyui-dialog" closed=true><input type="hidden" id="provser" name="provser">
	<div id="provsbtn" class="help-qstoolbar">
			<input class="btn2" type="button" value="添加" onclick="$('#prov_ad').dialog('open');">
			<input type="text" placeholder="搜索供应商名称<回车搜索>" class="help-qsipt" id="provfindbox" data-enter="getsupinfodata('1')" data-end="yes">
			<input class="btn2" type="button" value="搜索" onclick="getsupinfodata('1')">
	</div>
	<table id="provht" style="overflow: hidden;"></table>
	<div class="dialog-footer">
		<div id="provhpp" class="tcdPageCode fl"></div>
		<div style="float: right;">
			 <input type="button" class="btn1" value="确定" onclick="selectprovd()"/>
<!-- 			<input type="button" class="btn2" value="取消" onclick="$('#provhelpd').dialog('close')"/> -->
		</div>
	</div>
</div>
<!-- 增加-------------------------- -->
	<div id="prov_ad" title="添加供应商"
		style="width: 600px; height: 300px; text-align: center;"
		class="easyui-dialog" closed=true>
		<table width="550" border="0" cellspacing="10" class="table1">
			<tr>
				<td width="100" align="right" class="header skyrequied">供应商名称</td>
				<td width="160" align="left"><input class="hig25" type="text" maxlength="20"
					placeholder="<输入>"  id="prov_aprovname" name="aprovname" /></td>
				<td width="130" align="right" class="header">联系人</td>
				<td width="160" align="left"><input class="hig25" type="text" maxlength="20"
					placeholder="<输入>"  id="prov_alinkman" name="alinkman" /></td>
			</tr>
			<tr>
				<td align="right" class="header">联系电话</td>
				<td align="left"><input type="text" placeholder="<输入>"  class="hig25"
					id="prov_atel" name="atel" maxlength="20" /></td>
				<td align="right" class="header">移动电话</td>
				<td align="left"><input class="hig25" type="text"
					placeholder="<输入>"  id="prov_amobile" name="amobile" maxlength="11" /></td>
			</tr>
			<tr>
				<td colspan="4" class="tdsplit"></td>
			</tr>
			<tr align="right">
				<td align="right" class="header">地址</td>
				<td align="left">
				<input class="hig25" type="text" placeholder="<输入>"  maxlength="100" id="prov_aaddress" name="aaddress" /></td>
				<td align="right" class="header skyrequied">折扣</td>
				<td align="left"><input class="hig25" type="text" maxlength="4" value="1.00" onchange="diskz(this.value,'add')"
					placeholder="<输入>"  id="prov_adiscount" name="adiscount" /></td>
			</tr>
			<tr align="right">
				<td align="right" class="header skyrequied">价格方式</td>
				<td align="left"><select id="prov_apricetype" name="apricetype"
					style="width: 152px; height: 27px">
						<option value="0">进价</option>
						<option value="1">零价折扣</option>
				</select></td>
				<td align="right" class="header">备注</td>
				<td align="left"><input class="hig25" type="text" maxlength="30"
					placeholder="<输入>"  id="prov_aremark" name="aremark" title="addsup();"/></td>
			</tr>
		</table>
		<div class="dialog-footer" style="text-align: center;">
			<input id="prov_add" class="btn1" type="button" name="save"
				style="width: 150px;" type="button" value="保存并继续添加"
				onclick="addsup();">
		</div>
	</div>
<script type="text/javascript">
	var s = false;
	var provdatat = true;
	var showall="0";
	function selectprov(ser) {
		$('#provser').val(ser);
		var findbox="";
		showall="0";
		if(ser=="add"||ser=="addfk"){
// 			findbox = $('#aprovname').val();
		}else if(ser=='update'||ser=='updatefk'||ser=="updatesell"){
// 			findbox = $('#uprovname').val();
		}else
			showall='1';
		$('#provfindbox').val(findbox);
		if ($('#uhouseid').val == '') {
			alert('请先选择店铺！');
		} else {
			if(provdatat){
				
				$('#provhpp').createPage({
					pageCount : 1,
			        current:1,
			        backFn : function(p){
			        	getsupinfodata(p.toString());
			        }
				 });
				$('#provht').datagrid({
					height : 350,
					fitColumns : true,
					striped : true, //隔行变色
					singleSelect : true,
					columns : [ [ 
					{
						field : 'ROWNUMBER',title : '序号',width : 30,fixed:true,align:'center',halign:'center',
						formatter:function(value,row,index){
							var val = Math.ceil(Number(value)/ pagecount)-1;
							return isNaN(Number(value))||value==""?"":val*pagecount+Number(index)+1;
						}
					},{
						field : 'PROVNAME',
						title : '供应商名称',
						width : 100
					}, {
						field : 'PROVID',
						title : 'ID',
						width : 100,
						hidden : true
					}, {
						field : 'DISCOUNT',
						title : '折扣',
						width : 100,
						hidden : true
					}, {
						field : 'PRICETYPE',
						title : '价格方式',
						width : 100,
						hidden : true
					}, ] ],
					onClickRow : function(rowIndex, rowData) {
					
					},
					onDblClickRow : function(rowIndex, rowData) {
						selectprovd();
					},
					pageSize : 20,
					toolbar : '#provsbtn'
				}).datagrid("keyCtr","selectprovd()");
				$('#provhelpd').dialog({
					modal:true
				});
				provdatat = false;
			}
			getsupinfodata('1');
			$('#provhelpd').dialog('open');
			$('#provfindbox').focus();
		}
	}
	function selectprovd(){
		var ser = $('#provser').val();
		var arrs1 = $('#provht').datagrid('getSelected');
		if(arrs1){
			if (ser == 'add'||ser == 'awareprov') {
				$('#aprovname').val(arrs1.PROVNAME);
				$('#aprovid').val(arrs1.PROVID);
				$('#aprovname').focus();
			}else if (ser == 'addfk') {
				$('#aprovname').val(arrs1.PROVNAME);
				$('#aprovid').val(arrs1.PROVID);
				var houseid = $('#ahouseid').val();
				getpaybal(arrs1.PROVID,houseid);
				$('#aprovname').focus();
			}else if (ser == 'updatefk') {
				$('#uprovname').val(arrs1.PROVNAME);
				$('#uprovid').val(arrs1.PROVID);
				var houseid = $('#uhouseid').val();
				getpaybal(arrs1.PROVID,houseid);
				$('#uprovname').focus();
			}else if (ser == 'updatep' || ser == 'uwareprov' || ser=="updateprov") {
				$('#uprovname').val(arrs1.PROVNAME);
				$('#uprovid').val(arrs1.PROVID);
				$('#uprovname').focus();
			}else if (ser == 'update') {
				$('#uprovname').val(arrs1.PROVNAME);
				$('#uprovid').val(arrs1.PROVID);
				$('#udiscount').val(arrs1.DISCOUNT);
				$('#upricetype').val(arrs1.PRICETYPE);
				var id = $('#uid').val();
				var noteno = $('#unoteno').val();
				changeprov(id, noteno, arrs1.PROVID,arrs1.DISCOUNT,arrs1.PRICETYPE);
				$('#uprovname').focus();
			}else if (ser == 'updatesell') {
				$('#uprovname').val(arrs1.PROVNAME);
				$('#uprovid').val(arrs1.PROVID);
				changeseller(arrs1.PROVID);
				$('#uprovname').focus();
			} else if (ser == 'search' ||ser =='swareprov') {
				$('#sprovname').val(arrs1.PROVNAME);
				$('#sprovid').val(arrs1.PROVID);
				$('#sprovname').focus();
			} else if (ser == 'load') {
				$('#lprovname').val(arrs1.PROVNAME);
				$('#lprovid').val(arrs1.PROVID);
				$('#lprovname').focus();
			}  else if (ser == 'analysis') {
				$('#sprovname').val(arrs1.PROVNAME);
				$('#sprovid').val(arrs1.PROVID);
				$('#sprovname').focus();
			} 
			$('#provhelpd').dialog('close');
		}else{
			alert('请选择');
		}
	}
	//获取数据
	function getsupinfodata(page) {
		var findbox = $('#provfindbox').val();
		alertmsg(6);
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data : {
				erpser : "providelisthelp",
				sort: "PROVNAME",
				order: "asc",
				noused: 0,
				fieldlist: "*",
				rows: pagecount,
				page : page,
				showall : showall,
				findbox : findbox
			}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType : 'json',
			success : function(data) { //回调函数，result，返回值 
				try{if (valideData(data)) {
					$('#provhpp').setPage({
				        pageCount:Math.ceil(Number(data.total)/ pagecount),
				        current:Number(page)
					 });
					$('#provht').datagrid('loadData', data);
					var len = $('#provht').datagrid('getRows').length;
					if(len>0){
						$('#provht').datagrid("selectRow",0);
					}
						$('#provfindbox').focus();
					
				}
				}catch(err){
					console.error(err.message);
				}
			}
		});
	}
	function openprov() {
		$('#adsupd').dialog({
			modal : true
		});
		$('#adsupd').dialog('open');
	}
	//添加数据
	function addsup() {
		var provname = $("#prov_aprovname").val();
		var linkman = $("#prov_alinkman").val();
		var remark = $("#prov_aremark").val();
		var address = $("#prov_aaddress").val();
		var mobile = $("#prov_amobile").val();
		var tel = $("#prov_atel").val();
		var discount = $("#prov_adiscount").val();
		var pricetype = $("#prov_apricetype").val();
		if (provname == "") {
			alerttext("供应商名字不能为空！");
			$('#aprovname').focus();
		} else {
			alertmsg(2);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser : "addprovide",
					provname : provname,
					linkman : linkman,
					remark : remark,
					bankname : "",
					address : "",
					mobile : mobile,
					tel : tel,
					discount : discount,
					postcode : "",
					taxno : "",
					accountno : "",
					pricetype : pricetype
				}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType : 'json',
				success : function(data) { //回调函数，result，返回值 
					if (valideData(data)) {
						var provid  = data.msg;
						$('#provfindbox').val(provname);
						getsupinfodata('1');
						$("#prov_aprovname").val('');
						$("#prov_alinkman").val('');
						$("#prov_aremark").val('');
						$("#prov_abankname").val('');
						$("#prov_aaddress").val('');
						$("#prov_amobile").val('');
						$("#prov_atel").val('');
						$("#prov_adiscount").val('1.00');
						$("#prov_apostcode").val('');
						$("#prov_ataxno").val('');
						$("#prov_aaccountno").val('');
						$("#prov_apricetype").val('0');
						$("#prov_aprovname").focus();
					}
				}
			});
		}
	}
</script>