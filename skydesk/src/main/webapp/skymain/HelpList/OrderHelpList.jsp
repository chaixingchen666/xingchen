<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- 得到订货会列表  -->
	<div id="orderf" title="Dialog" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
	<!-- 搜索栏 -->
	<div id="ordersbtn" class="help-qstoolbar">
		<input type="text" class="help-qsipt" id="orderfindbox" data-enter="getorderdata('1')"  placeholder="搜索订货会<回车搜索>"  data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getorderdata('1')">
		<input  type="hidden" id="orderser" >
	</div>
		<table id="ordert" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="orderpp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="orderfuzhi()" />
<!-- 				<input type="button" class="btn2" value="取消" onclick="$('#orderf').dialog('close')" />  -->
			</div>
		</div>
	</div>

<script type="text/javascript">
var orderdatagrid=true;
  //加载订货会列表窗
function selectorder(orderser){
	  $("#orderser").val(orderser);
	  var findbox='';
// 	  if(orderser=='add'){								
// 			findbox = $('#adordername').val();
// 		}else if(orderser=='update'){
// 			findbox = $('#uordername').val();
// 		}else if(orderser=='analysis'){
// 			findbox = $('#adordername').val();
// 		}else if(orderser=='autobarcode'){
// 			findbox = $('#iordername').val();
// 		}else if(orderser=='load'){
// 			findbox = $('#iordername').val();
// 		}
// 		else{
// 			findbox = $('#sordername').val();
// 		}
		$('#orderfindbox').val(findbox);
		if(orderdatagrid){
			$('#orderpp').createPage({
		        current:1,
		        backFn:function(p){
		        	getorderdata(p.toString());
		        }
			 });
			$('#ordert').datagrid(
					{
						height:350,
						idField : 'ordername',
						fitColumns : true,
						striped : true, //隔行变色
						singleSelect : true,
						pageSize:20,
						columns : [ [ {
							field : 'BRANDID',
							title : 'ID',
							width : 200,
							hidden : true
						},{
							field : 'ROWNUMBER',title : '序号',width : 50,fixed:true,align:'center',halign:'center',
							formatter:function(value,row,index){
								var val = Math.ceil(Number(value)/ pagecount)-1;
								return isNaN(Number(value))||value==""?"":val*pagecount+Number(index)+1;
							}
						}, {
							field : 'OMNAME',
							title : '订货会',
							width : 200
						} ] ],
						onDblClickRow:function(rowIndex, rowData){
							orderfuzhi();
						},
						
					toolbar:'#ordersbtn'
					}).datagrid("keyCtr","orderfuzhi()");
			$('#orderf').dialog({
				modal:true,
				title:'订货会帮助列表'
			});
	}
		getorderdata('1');//加载数据
		$('#orderf').dialog('open');
		$('#orderfindbox').focus();
  }
  //双击、确定赋值（订货会）
   function orderfuzhi(){
	  var rowData = $('#ordert').datagrid('getSelected');
	  var orderser= $('#orderser').val();
	  if(rowData){
	   if(orderser=='analysis'){								
			$('#aordername').val(rowData.OMNAME);
			$('#aorderid').val(rowData.OMID);
			$('#aordername').focus();
		}
		$('#orderf').dialog('close');
	  }else{
		  alert('请选择');
	  }
  }
 //获取订货会列表
	function getorderdata(page) { //定义回调函数#')
	var findbox = $('#orderfindbox').val();
	findbox = findbox==undefined?"":findbox;
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "listomsubject",
			fiellist: "*",
			rows: pagecount,
			page : page,
			findbox : findbox
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			try{
				if (valideData(data)) {
					$('#ordert').datagrid('loadData', data);
					$('#orderpp').setPage({
				        pageCount:Math.ceil(Number(data.total)/ pagecount),
				        current:Number(page)
					 });
					var len = $('#ordert').datagrid('getRows').length;
					if(len>0){
						$('#ordert').datagrid("selectRow",0);
					}
					$('#orderfindbox').focus();
		  		}
			}catch(err){
				console.error(err.message);
			}
		}
	});
		
	} 

    </script>
