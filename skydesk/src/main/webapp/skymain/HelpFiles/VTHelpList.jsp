<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- 得到会员类型列表  -->
	<div id="dx_vtf" title="选择会员类型" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
	<!-- 搜索栏 -->
	<div id="dx_vtsbtn" class="help-qstoolbar">
		<input type="text" class="help-qsipt" id="dx_vtfindbox" data-enter="getdx_vtdata('1')"  placeholder="搜索会员类型<回车搜索>"  data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getdx_vtdata('1')">
		<input  type="hidden" id="dx_vtser" >
	</div>
		<table id="dx_vtt" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="dx_vtpp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="dx_vtselects()" />
			</div>
		</div>
	</div>
<script type="text/javascript">
 //加载会员类型列表窗
function selectdx_vt(dx_vtser){
	$("#dx_vtser").val(dx_vtser);
	var findbox='';
	$('#dx_vtfindbox').val(findbox);
	var $dx_dg = $('#dx_vtt');
	if(!$dx_dg .data().datagrid){
		$('#dx_vtpp').createPage({
	        current:1,
	        backFn:function(p){
	        	getdx_vtdata(p.toString());
	        }
		 });
		var empty = {};
		var options = {
				height:350,
				idField : 'VTID',
				fitColumns : true,
				striped : true, //隔行变色
				singleSelect : true,
				selectOnCheck: false,
				checkOnSelect: false,
				pageSize:20,
				columns : [ [ {
					field : 'VTID',
					title : 'ID',
					width : 200,
					hidden : true
				},{
					field: "CHECK",
					title: "选择",
					checkbox: true
				},{
					field : 'ROWNUMBER',title : '序号',width : 50,fixed:true,align:'center',halign:'center',
					formatter: rownumberfm
				}, {
					field : 'VTNAME',
					title : '会员类型',
					width : 200
				} ] ],
				toolbar: '#dx_vtsbtn'
			};
		$dx_dg.datagrid(options);
		getdx_vtdata('1');//加载数据
	}
	$dx_dg.datagrid('clearChecked');
	getdx_vtdata(1);
	$('#dx_vtf').dialog('open');
	$('#dx_vtfindbox').focus();
}
  //双击、确定赋值（会员类型）
function dx_vtselects(){
	var checkrows = $('#dx_vtt').datagrid('getChecked');
	var dx_vtser = $('#dx_vtser').val();
	var namearr = [];
	var valuearr = [];
	if(checkrows){
		for(var i in checkrows){
			var row = checkrows[i];
			valuearr.push(row.VTID); 
			namearr.push(row.VTNAME); 
		}
	}
	var name = namearr.join(",");
	var value = "^"+valuearr.join("^")+"^";
	if(value=="^^") value="";
	if(dx_vtser=='search'){
		$('#svtname').val(name);
		$('#svtid').val(value);
		$('#svtname').focus();
	}else if(dx_vtser=='load'){
		$('#lvtname').val(name);
		$('#lvtid').val(value);
		$('#lvtname').focus();
	}
	$('#dx_vtf').dialog('close');
}
//获取会员类型列表
function getdx_vtdata(page) { //定义回调函数#')
	var findbox = $('#dx_vtfindbox').val();
	findbox = findbox==undefined?"":findbox;
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fybuyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data :{
			erpser: "guesttypelist",
			findbox: findbox,
			rows: pagecount,
			fieldlist: "*",
			noused: 0,
			page: page
		}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
				$('#dx_vtpp').setPage({
		       		pageCount:Math.ceil(Number(data.total)/ pagecount),
		        	current:Number(page)
			 	});
				var $dx_dg  = $('#dx_vtt');
				$dx_dg .datagrid('loadData', data);
				var len = $dx_dg .datagrid('getRows').length;
				if(len>0){
					$dx_dg .datagrid("selectRow",0);
				}
				$('#dx_vtfindbox').focus();
	  			}
			}catch(err){
				console.error(err.message);
			}
		}
	});
	
} 
</script>
