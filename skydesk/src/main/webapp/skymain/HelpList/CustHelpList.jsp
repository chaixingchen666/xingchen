<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 客户弹框 -->
<div id="custd" title="客户帮助列表"
	style="width: 530px; height: 440px"
	class="easyui-dialog" closed=true>
	<div id="custsbtn" class="help-qstoolbar">
				<input type="button" class="btn2" value="添加" onclick="$('#cust_ad').dialog('open');">
				<input type="text" placeholder="客户名称搜索<回车搜索>" class="help-qsipt" style="width:160px" id="custfindbox" data-enter="getcustlist('1')"  data-end="yes">
				<input type="text" placeholder="客户区域名称搜索<回车搜索>" class="help-qsipt" style="width:160px" id="custareafindbox" data-enter="getcustlist('1')"  data-end="yes">
				<input type="button" class="btn2" value="搜索" onclick="getcustlist('1')">
			</div>
			<input type="hidden" id="custser">
	<table id="custt" style="overflow: hidden;"></table>
	<div class="dialog-footer">
			<div id="custpp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="custfuzhi()" />
<!-- 				<input type="button" class="btn2" value="取消" onclick="$('#custd').dialog('close')"/>  -->
			</div>
		</div>
</div>

<!-- 增加客户档案-->
	<div id="cust_ad" data-options="modal:true" title="添加客户" style="width: 600px; height: 410px; text-align: center;" class="easyui-dialog" closed=true>
		<table width="550" border="0" cellspacing="5" class="table1">
			<tr>
				<td width="70" align="right" class="header skyrequied">客户名称</td>
				<td width="160" align="left"><input class="hig25" type="text" maxlength="20"
					placeholder="<输入>" id="custadd_custname" name="acustname" /></td>
				<td width="130" align="right" class="header">联系人</td>
				<td width="160" align="left"><input class="hig25" type="text" maxlength="20"
					placeholder="<输入>" id="custadd_linkman" name="alinkman" /></td>
			</tr>
			<tr>
				<td align="right" class="header">联系电话</td>
				<td align="left"><input class="hig25" type="text"
					placeholder="<输入>" id="custadd_tel" name="atel" maxlength="12" /></td>
				<td align="right" class="header">移动电话</td>
				<td align="left"><input class="hig25" type="text"
					placeholder="<输入>" id="custadd_mobile" name="amobile" maxlength="11" /></td>
			</tr>
			<tr>
				<td colspan="4" class="tdsplit"></td>
			</tr>
			<tr><td align="right" class="header skyrequied">价格方式</td>
				<td align="left"><select id='custadd_pricetype' name='apricetype'
					style="width: 152px; height: 27px">
					    <option value='' selected="selected">---请选择---</option>
						<option value='0'>零价折扣</option>
						<option value='4'>批发价</option>
						<option value='5'>打包价</option>
						<option value='1'>售价一</option>
						<option value='2'>售价二</option>
						<option value='3'>售价三</option>
				</select></td>
			<td align="right" class="header skyrequied">折扣</td>
				<td align="left"><input class="hig25" type="text" maxlength="4" value="1.00" placeholder="<输入>" id="custadd_discount" name="adiscount" /></td>
					</tr>
			<tr>
				<td align="right" class="header">信用额度</td>
				<td align="left"><input class="hig25" type="text" maxlength="8"
					placeholder="<输入>" id="custadd_creditcurr" name="acreditcurr" /></td>
				<td align="right" class="header">区域</td>
				<td align="left"><input class="hig25" type="text" maxlength="30"
					placeholder="<输入>" id="custadd_areaname" /></td>
			</tr>
			<tr>
				<td align="right" class="header">备注</td>
				<td align="left" colspan="3"><input class="hig25" type="text" style="width:440px;"
					placeholder="<输入>" maxlength="100" id="custadd_remark" /></td></tr>
			<tr>
				<td align="right" class="header">地址</td>
				<td align="left" colspan="3"><input class="hig25" type="text" style="width:440px;"
					placeholder="<输入>" maxlength="100" id="custadd_address"
					name="aaddress" /></td></tr>
		</table>
		<div class="dialog-footer">
			<label class="fl-ml30"><input type="checkbox"
				name="acreditoks" id="custadd_creditoks">启用信用控制</label> <input class="btn1"
				type="button" id="custadd_dd" style="width: 150px; margin-right: 20px"
				type="button" value="保存并继续添加" onclick="addcustomer();">
		</div>
	</div>

<script type="text/javascript">
	var custdatat = true;
	//获取客户列表
	function selectcust(ser){
		$('#custser').val(ser);
		var findbox ='';
// 		if (ser == 'update'||ser == 'updatesk') {
// 			findbox =  $('#ucustname').val();
// 		} else if (ser == 'add'||ser == 'addsk') {
// 			findbox = $('#acustname').val();
// 		}else if (ser == 'updatep') {
// 			findbox = $('#ucustname').val();
// 		}else if (ser == 'updatebuy') {
// 			findbox = $('#ucustname').val();
// 		}else if (ser == 'search') {
// 			findbox = $('#scustname').val();
// 		}else if(ser=='analysis'){
// 			findbox = $('#ucustname').val();
// 		}
		$('#custfindbox').val(findbox);
		if(!$('#custt').data().datagrid){
			$('#custpp').createPage({
				current : 1,
				backFn : function(p){
					getcustlist(p.toString());
				}
			});
			$('#custt').datagrid({
				idField : 'CUSTID',
				height : 350,
				fitColumns : true,
				striped : true, //隔行变色
				singleSelect : true,
				pageSize : 20,
				columns : [ [ {
					field : 'ROWNUMBER',title : '序号',width: 32,fixed:true,align:'center',halign:'center',
					formatter:function(value,row,index){
						var val = Math.ceil(Number(value)/ pagecount)-1;
						return isNaN(Number(value))||value==""?"":val*pagecount+Number(index)+1;
					}
				}, {
					field: 'CUSTNAME',
					title: '客户名称',
					fixed:true,
					width: 100,
					align:'center',
					halign:'center'
				}, {
					field: 'LINKMAN',
					title: '联系人',
					fixed: true,
					width: 60,
					align:'center',
					halign:'center'
				}, {
					field: 'MOBILE',
					title: '手机',
					fixed: true,
					width: 100,
					align:'center',
					halign:'center'
				}, {
					field: 'TEL',
					title: '联系电话',
					fixed: true,
					width: 100,
					align:'center',
					halign:'center'
				}, {
					field: 'AREANAME',
					title: '区域',
					fixed: true,
					width: 100,
					align:'center',
					halign:'center'
				}, {
					field: 'ADDRESS',
					title: '地址',
					fixed: true,
					width: 100,
					align:'center',
					halign:'center'
				} ] ],
				onDblClickRow: function(rowIndex, rowData) {
					custfuzhi();
				},
				toolbar : "#custsbtn"
			}).datagrid('keyCtr',"custfuzhi()");
			custdatat=false;
			$('#custd').dialog({
				modal:true
			});
		}
		getcustlist('1');
		$('#custd').dialog('open');
		$('#custfindbox').focus();
	}

	//获取客户数据
	function getcustlist(page){
		var findbox = $('#custfindbox').val();
		var areaname = $('#custareafindbox').val();
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data : {
				erpser : "customerlisthelp",
				areaname: areaname,
				noused : 0,
				rows: pagecount,
				sort: "custname",
				order: "asc",
				findbox: findbox,
				fieldlist: "a.custid,a.custname,a.linkman,a.mobile,a.tel,a.address,a.discount,a.pricetype,a.areaname",
				page : page
			}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType : 'json',
			success : function(data) { //回调函数，result，返回值 
				try{if (valideData(data)){
					$('#custpp').setPage({
				        pageCount:Math.ceil(Number(data.total)/ pagecount),
				        current:Number(page)
					 });
					$('#custt').datagrid('loadData', data);
					var len = $('#custt').datagrid('getRows').length;
					if(len>0){
						$('#custt').datagrid("selectRow",0);
					}
						$('#custfindbox').focus();
				} 
				}catch(err){
					console.error(err.message);
				}
			}
		});
	}
	function custfuzhi(){
		var ser = $('#custser').val();
		var rowData = $('#custt').datagrid('getSelected');
		if(rowData){
			if (ser == 'update') {
				$('#ucustname').val(rowData.CUSTNAME);
				$('#ucustid').val(rowData.CUSTID);
				$('#udiscount').val(rowData.DISCOUNT);
				$('#upricetype').val(rowData.PRICETYPE);
				var id = $('#uid').val();
				var noteno = $('#unoteno').val();
				changecust(id, noteno, rowData.CUSTID,rowData.DISCOUNT,rowData.PRICETYPE);
				$('#ucustname').focus();
			} else if (ser == 'add') {
				$('#acustname').val(rowData.CUSTNAME);
				$('#acustid').val(rowData.CUSTID);
				$('#acustname').focus();
			} else if (ser == 'addsk') {
				$('#acustname').val(rowData.CUSTNAME);
				$('#acustid').val(rowData.CUSTID);
				var houseid = $('#ahouseid').val();
				getincomebal(rowData.CUSTID,houseid);
				$('#acustname').focus();
			} else if (ser == 'updatesk') {
				$('#ucustname').val(rowData.CUSTNAME);
				$('#ucustid').val(rowData.CUSTID);
				var houseid = $('#uhouseid').val();
				getincomebal(rowData.CUSTID,houseid);
				$('#ucustname').focus();
			}else if (ser == 'updatep'||ser=="updatecust") {
				$('#ucustname').val(rowData.CUSTNAME);
				$('#ucustid').val(rowData.CUSTID);
				$('#ucustname').focus();
			}else if (ser == 'updatebuy') {
				$('#ucustname').val(rowData.CUSTNAME);
				$('#ucustid').val(rowData.CUSTID);
				changebuyer(rowData.CUSTID);
				$('#ucustname').focus();
			}else if (ser == 'search') {
				$('#scustname').val(rowData.CUSTNAME);
				$('#scustid').val(rowData.CUSTID);
				$('#scustname').focus();
			}else if(ser=='analysis'){
				$('#ucustname').val(rowData.CUSTNAME);
				$('#ucustid').val(rowData.CUSTID);
				$('#ucustname').focus();
			}
			$('#custd').dialog('close');
	 	}else{
		 	 alert('请选择一条数据');
	 	 }
	}
	//添加客户数据
	function addcustomer() {
		var custname = $('#custadd_custname').val();
		var linkman = $('#custadd_linkman').val();
		var remark = $('#custadd_remark').val();
		var address = $('#custadd_address').val();
		var mobile = $('#custadd_mobile').val();
		var tel = $('#custadd_tel').val();
		var discount = $('#custadd_discount').val();
		var creditcurr = $('#custadd_creditcurr').val();
		var currbzj = $('#custadd_currbzj').val();
		var pricetype = $('#custadd_pricetype').val();
		var areaname = $('#custadd_areaname').val();
		var creditok;
		if ($('#custadd_creditoks').prop('checked')) {
			creditok = '1';
		} else {
			creditok = '0';
		}
		if(pricetype==''){
			alerttext('请选择价格方式！');
		}else{
		alertmsg(2);
		$.ajax({
			type : 'POST', //访问WebService使用Post方式请求 
			url : '/skydesk/fyjerp', //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data : {
				erpser : 'addcustomer',
				custname : custname,
				linkman : linkman,
				remark : remark,
				bankname : "",
				address : address,
				mobile : mobile,
				tel : tel,
				areaname: areaname,
				discount : discount,
				postcode : "",
				taxno : "",
				creditcurr : creditcurr,
				accountno : "",
				pricetype : pricetype,
				creditok : creditok,
				currbzj : currbzj
			}, //这里是要传递的参数，格式为 data: '{paraName:paraValue}',下面将会看到                          
			dataType : 'json',
			success : function(data) { //回调函数，text，返回值 
				if (valideData(data)) {
					getcustlist("1");
					$('#custadd_custname').val('');
					$('#custadd_linkman').val('');
					$('#custadd_remark').val('');
					$('#custadd_bankname').val('');
					$('#custadd_address').val('');
					$('#custadd_mobile').val('');
					$('#custadd_areaname').val('');
					$('#custadd_tel').val('');
					$('#custadd_discount').val('1.00');
					$('#custadd_postcode').val('');
					$('#custadd_taxno').val('');
					$('#custadd_creditcurr').val('');
					$('#custadd_currbzj').val('');
					$('#custadd_accountno').val('');
					$('#pricetype').val('0');
					$('#custadd_custname').focus();
				}
			}
		});
	}}
</script>
