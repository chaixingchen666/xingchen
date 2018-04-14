<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 得到费用项目列表  -->
	<div id="cgf" title="选择费用项目" class="easyui-dialog" closed=true style="width: 530px; height: 440px;" >
		<input type="hidden" id="chargesser" name="chargesser">
			<div id="cgsbtn" class="help-qstoolbar">
				<input type="text" class="help-qsipt" id="cghp" data-enter="getChargesData('1')" placeholder="搜索费用名称名称<回车搜索>" data-end="yes">
				<input type="button" class="btn2" value="搜索" onclick="getChargesData('1')">
		</div>
		<table id="cgt" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="cgpp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="costfuzhi();" />
<!-- 				<input type="button" class="btn2" value="取消" onclick="$('#cgf').dialog('close')" />  -->
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//获取费用项目列表
		var chargesdatat = true;
		function Chargesdata(chargersser){
			$('#chargesser').val(chargersser);
			var findbox ='';
// 			if (chargersser == 'add') {
// 				findbox = $('#acgname').val();
// 			} else if (chargersser == 'update'){
// 				findbox = $('#ucgname').val();
// 			} else if (chargersser == 'search'){
// 				findbox = $('#hcgname').val();
// 			}
			$('#cghp').val(findbox);
			if(chargesdatat){
				$('#cgt').datagrid(
						{
							fitColumns : true,
							striped : true, //隔行变色
							height : 350,
							singleSelect : true,
							pageSize : 20,
							columns : [ [
									{
										field : 'ROWNUMBER',
										title : '序号',
										width : 50,
										fixed : true,
										align : 'center',
										halign : 'center',
										formatter : function(value, row, index) {
											return Number(value) % 20 == 0 ? 20
													: Number(value) % 20;
										}
									}, {
										field : 'CGNAME',
										title : '费用项目',
										width : 200
									}, {
										field : 'CGID',
										title : 'CGID',
										width : 200,
										hidden : true
									} ] ],
							onClickRow : function(rowIndex, rowData) {
							},
							onDblClickRow : function(rowIndex, rowData) {
								costfuzhi();
							},
							toolbar:'#cgsbtn'
						}).datagrid("keyCtr","costfuzhi()");
						$('#cgpp').createPage({
							pageCount : 1,
							current : 1,
							backFn : function(p){
								getChargesData(p.toString());
							}
						});
						chargesdatat= false;
			}
			getChargesData('1');
			$('#cgf').dialog('open');
			$('#cghp').focus();
		}
		//双击、确定赋值（费用名称）
		function costfuzhi() {
		var ser=$('#chargesser').val();
			var arrs = $('#cgt').datagrid('getSelected');
			if(arrs){
				if (ser == 'add') {
					$('#acgname').val(arrs.CGNAME);
					$('#acgid').val(arrs.CGID);
					$('#acgname').focus();
				} else if (ser == 'update') {
					$('#ucgname').val(arrs.CGNAME);
					$('#ucgid').val(arrs.CGID);
					$('#ucgname').focus();
				} else if (ser == 'search') {
					$('#scgname').val(arrs.CGNAME);
					$('#scgid').val(arrs.CGID);
					$('#scgname').focus();
				}
				$('#cgf').dialog('close');
			}else{
				alerttext("请选择一条数据！")
			}
		}
		//获取费用项目数据
		function getChargesData(page) {
			var findbox =  $('#cghp').val();
			alertmsg(6);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser : "chargeslist",
					sort: "cgname",
					order: "asc",
					fieldlist: "*",
					rows: pagecount,
					noused: 0,
					page : page,
					findbox : findbox
				}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType : 'json',
				success : function(data) { //回调函数，result，返回值 
					try{if (valideData(data)) {
						$('#cgpp').setPage({
							pageCount : Math.ceil(data.total / pagecount),
							current : Number(page)
						});
						$('#cgt').datagrid('loadData', data);
						var len = $('#cgt').datagrid('getRows').length;
						if(len>0){
							$('#cgt').datagrid("selectRow",0);
						}
							$('#cghp').focus();
					}
					}catch(err){
						console.error(err.message);
					}
				}
			});
		}
	</script>