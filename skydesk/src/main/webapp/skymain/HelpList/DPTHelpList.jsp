<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="departmentd" title="销售部门列表" style="width: 530px;height: 440px;" class="easyui-dialog" closed=true>
<input type="hidden" id="hpid" name="name"> <input type="hidden" id="departmentdser" name="departmentdser">
	<div id="dptsbtn" class="help-qstoolbar">
				<input type="text" placeholder="搜索销售部门名称<回车搜索>" class="help-qsipt" id="departmentfindbox" data-enter="getdepartmentlist('1')"  data-end="yes">
				<input type="button" class="btn2" value="搜索" onclick="getdepartmentlist('1')"> 
		</div>
	<table id="departmentt" style="overflow: hidden;"></table>
	<div class="dialog-footer">
	<div id="departmentpp" class="tcdPageCode fl"></div> 
	<div class="fr">
		<input type="button" class="btn1" value="确定" onclick="departmentfuzhi()"/>
<!-- 		<input type="button" class="btn2" value="取消" onclick="$('#departmentd').dialog('close')"/>  -->
	</div>
	</div>
</div>
<script type="text/javascript">
var dptdatat = true;
function selectdepartment(ser){
	$('#departmentdser').val(ser);
	var findbox = '';
// 	if (ser == "analysis") {
// 		findbox = $('#departmentname').val();
// 	}else if(ser == "search"){
// 		findbox = $('#sdepartmentname').val();
// 	}else if(ser = 'update'){
// 		findbox = $('#udptname').val();
// 	}
	$('#departmentfindbox').val(findbox);
	if(!$('#departmentt').data().datagrid){
		$('#departmentd').dialog({
			modal:true
		});
		$('#departmentpp').createPage({
			pageCount : 1,
			current : 1,
			backFn : function(p){
				getdepartmentlist(p.toString());
			}
		});
		$('#departmentt').datagrid(
				{
					height:350,
					idField : 'departmentname',
					fitColumns : true,
					striped : 	true, //隔行变色
					pageSize:10,
					singleSelect : true,
					checkOnSelect:true,
					columns : [ [ {
						field : 'DPTID',
						title : 'ID',
						width : 200,
						hidden : true
					},{
						field : 'ROWNUMBER',title : '序号',width : 50,fixed:true,align:'center',halign:'center',
						formatter:function(value,row,index){
							var val = Math.ceil(Number(value)/ pagecount)-1;
							return isNaN(Number(value))||value==""?"":val*pagecount+Number(index)+1;
						}
					},{
						field : 'DPTNAME',
						title : '销售部门',
						width : 200,
						align : 'center',
						halign :'center'
					}
					] ],
					onClickRow:function(rowIndex, rowData){
						
					},
					onDblClickRow : function(rowIndex, rowData) {
						departmentfuzhi();
					},
					toolbar :"#dptsbtn"
	         	 
				}).datagrid("keyCtr","departmentfuzhi()");
		dptdatat = false;
	}
	getdepartmentlist('1');
	$('#departmentd').dialog('open');
	$('#departmentfindbox').focus();
	}
//双击、确定赋值（销售员）a
function departmentfuzhi(){
	 var ser = $('#departmentdser').val();
	var rowData = $('#departmentt').datagrid('getSelected');
	if(rowData){
		if (ser == "analysis") {
			$('#departmentid').val(rowData.DPTID);
			$('#departmentname').val(rowData.DPTNAME);
			$('#departmentname').focus();
		}else if(ser == "search"){
			$('#sdptid').val(rowData.DPTID);
			$('#sdptname').val(rowData.DPTNAME);
			$('#sdptname').focus();
		}else if(ser == "updatedpt"){
			$('#udptid').val(rowData.DPTID);
			$('#udptname').val(rowData.DPTNAME);
			$('#udptname').focus();
		}else if(ser == 'update'){
			$('#udptid').val(rowData.DPTID);
			$('#udptname').val(rowData.DPTNAME);
			changedpt(rowData.DPTID);
			$('#udptname').focus();
		}
		$('#departmentd').dialog('close');
	}else{
		alert('请选择');
	}
}
//获取销售员数据
function getdepartmentlist(page){
	var findbox = $('#departmentfindbox').val();
	$.ajax({ 
		type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
        data: {
        	erpser:"departmentlist",
			findbox:findbox,
        	noused: 0,
        	sort: "dptname",
        	order: "asc",
        	fieldlist: "*",
        	rows: pagecount,
        	page:page
			},        //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        dataType: 'json', 
        success: function(data) {     //回调函数，result，返回值 
        	try{if(valideData(data)){
				$('#departmentpp').setPage({
			        pageCount:Math.ceil(Number(data.total) / pagecount),
			        current:Number(page)
				 });
				$('#departmentt').datagrid('loadData', data);
				var len = $('#departmentt').datagrid('getRows').length;
				if(len>0){
					$('#departmentt').datagrid("selectRow",0);
				}
					$('#departmentfindbox').focus();
        	}
        	}catch(err){
				console.error(err.message);
			}
        }                 
        });
}
</script>