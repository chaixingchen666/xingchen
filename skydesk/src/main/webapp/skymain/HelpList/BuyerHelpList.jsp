<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 客户弹框 -->
<div id="buyd" title="经销商帮助列表"
	style="width: 530px; text-align: center; height: 440px"
	class="easyui-dialog" closed=true><input type="hidden" id="buyser" name="buyser">
	<!-- 搜索栏 -->
	<div id="buysbtn" class="help-qstoolbar">
				<input type="text" placeholder="搜索客户名称" class="help-qsipt" id="buyfindbox" data-enter="getbuylist('1')"  data-end="yes">
				<input type="button" class="btn2" value="搜索" onclick="getbuylist('1')">
	</div>
	<table id="buyt" style="overflow: hidden;"></table>
			<div class="dialog-footer">
			<div id="buypp" class="tcdPageCode"
				style="float: left;"></div>
			<div style="float: right;">
<!-- 				<input type="button" class="btn2" value="取消" onclick="$('#buyd').dialog('close')" />  -->
				<input type="button" class="btn1" value="确定" onclick="buyfuzhi()"/>
			</div>
		</div>
</div>
<script type="text/javascript">
	var custdatat = true;
	//获取经销商列表,买家
	function selectbuyer(ser){
		$('#buyser').val(ser);
		var findbox ='';
// 		if (ser == 'update') {
// 			findbox = d$('#ucompany').val();
// 		} else if (ser == 'search') {
// 			findbox = $('#scompany').val();
// 		}else if(ser=='analysis'){
// 			findbox = $('#scompany').val();
// 		}
		$('#buyfindbox').val(findbox);
		if(custdatat){
			 $('#buypp').createPage({
					pageCount : 1,
					current : 1,
					backFn : function(p){
						getbuylist(p);
					}
				});
			$('#buyt').datagrid({
				idField : 'ACCID',
				height :350,
				fitColumns : true,
				striped : true, //隔行变色
				singleSelect : true,
				pageSize : 10,
				columns : [ [ {
					field : 'ROWNUMBER',title : '序号',width : 50,fixed:true,align:'center',halign:'center',
					formatter:function(value,row,index){
						var val = Math.ceil(Number(value)/ pagecount)-1;
						return isNaN(Number(value))||value==""?"":val*pagecount+Number(index)+1;
					}
				},{
					field : 'ACCID',
					title : '经销商ID',
					width : 200,
					hidden : true
				}, {
					field : 'CUSTNAME',
					title : '客户名称',
					width : 200
				}, {
					field : 'COMPANY',
					title : '经销商名称',
					width : 200
				}] ],
				onDblClickRow : function(rowIndex, rowData) {
					buyfuzhi();
				},
				toolbar:"#buysbtn"
			}).datagrid('keyCtr','buyfuzhi();');
			custdatat = false;
			$('#buyd').dialog({
				modal:true
			});
		}
		getbuylist('1');
		$('#buyd').dialog('open');
		$('#buyfindbox').focus();
	}
	//双击、确定赋值（客户）
	function buyfuzhi(){
		var ser=$('#buyser').val();
		var rowData=$('#buyt').datagrid('getSelected');
		if(rowData){
			if (ser == 'update') {
				/*if(rowData.COMPANY!="")
					$('#ucompany').val(rowData.CUSTNAME+"("+rowData.COMPANY+")");
				else*/
					$('#ucompany').val(rowData.CUSTNAME);
				$('#uaccid').val(rowData.ACCID);
				$('#ucompany').focus();
			} else if (ser == 'search') {
				/*if(rowData.COMPANY!="")
					$('#scompany').val(rowData.CUSTNAME+"("+rowData.COMPANY+")");
				else*/
					$('#scompany').val(rowData.CUSTNAME);
				$('#saccid').val(rowData.ACCID);
				$('#scompany').focus();
			}else if(ser=='analysis'){
			/*	if(rowData.COMPANY!="")
					$('#scompany').val(rowData.CUSTNAME+"("+rowData.COMPANY+")");
				else*/
					$('#scompany').val(rowData.CUSTNAME);
				$('#saccid').val(rowData.ACCID);
				$('#scompany').focus();
			}
			$('#buyd').dialog('close');
		}else{
			alert('请选择一条数据！');
		}
	}

	//获取经销商数据
	function getbuylist(page){
		var findbox = $('#buyfindbox').val();
		alertmsg(6);
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data : {
				erpser : "mybuyerlist",
				findbox : findbox,
				rows: pagecount,
				page : page
			}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType : 'json',
			success : function(data) { //回调函数，result，返回值 
				try{if (valideData(data)){
					$('#buypp').setPage({
				        pageCount:Math.ceil(Number(data.total)/ pagecount),
				        current:Number(page)
					});
					$('#buyt').datagrid('loadData', data);
					var len = $('#buyt').datagrid('getRows').length;
					if(len>0){
						$('#buyt').datagrid("selectRow",0);
					}
					$('#buyfindbox').focus();
				}
				}catch(err){
					console.error(err.message);
				}
			}
		});
	}
</script>