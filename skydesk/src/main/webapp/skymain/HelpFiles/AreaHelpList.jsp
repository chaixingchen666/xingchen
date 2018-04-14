<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- 得到区位列表  -->
	<div id="dx_areaf" title="选择区位" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
	<!-- 搜索栏 -->
	<div id="dx_areasbtn" class="help-qstoolbar">
		<input type="text" class="help-qsipt" id="dx_areafindbox" data-enter="getdx_areadata('1')"  placeholder="搜索区位<回车搜索>"  data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getdx_areadata('1')">
		<input  type="hidden" id="dx_areaser" >
	</div>
		<table id="dx_areat" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="dx_areapp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="dx_areaselects()" />
			</div>
		</div>
	</div>
<script type="text/javascript">
 //加载区位列表窗
function selectdx_area(dx_areaser){
	$("#dx_areaser").val(dx_areaser);
	var findbox='';
	$('#dx_areafindbox').val(findbox);
	var $dx_dg = $('#dx_areat');
	if(!$dx_dg .data().datagrid){
		$('#dx_areapp').createPage({
	        current:1,
	        backFn:function(p){
	        	getdx_areadata(p.toString());
	        }
		 });
		var empty = {};
		var options = {
				height:350,
				idField : 'AREAID',
				fitColumns : true,
				striped : true, //隔行变色
				singleSelect : true,
				selectOnCheck: false,
				checkOnSelect: false,
				pageSize:20,
				columns : [ [ {
					field : 'AREAID',
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
					field : 'AREANAME',
					title : '区位',
					width : 200
				} ] ],
				toolbar: '#dx_areasbtn'
			};
		$dx_dg.datagrid(options);
		getdx_areadata('1');//加载数据
	}
	$dx_dg.datagrid('clearChecked');
	getdx_areadata(1);
	$('#dx_areaf').dialog('open');
	$('#dx_areafindbox').focus();
}
  //双击、确定赋值（区位）
function dx_areaselects(){
	var checkrows = $('#dx_areat').datagrid('getChecked');
	var dx_areaser = $('#dx_areaser').val();
	var namearr = [];
	var valuearr = [];
	if(checkrows){
		for(var i in checkrows){
			var row = checkrows[i];
			valuearr.push(row.AREAID); 
			namearr.push(row.AREANAME); 
		}
	}
	var name = namearr.join(",");
	var value = "^"+valuearr.join("^")+"^";
	if(value=="^^") value="";
	if(dx_areaser=='search'){
		$('#sareaname').val(name);
		$('#sareaid').val(value);
		$('#sareaname').focus();
	}else if(dx_areaser=='load'){
		$('#lareaname').val(name);
		$('#lareaid').val(value);
		$('#lareaname').focus();
	}
	$('#dx_areaf').dialog('close');
}
//获取区位列表
function getdx_areadata(page) { //定义回调函数#')
	var findbox = $('#dx_areafindbox').val();
	findbox = findbox==undefined?"":findbox;
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data :{erpser:"arealist",findbox:findbox,rows:pagecount,filedlist:"*",page:page}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
				$('#dx_areapp').setPage({
		       		pageCount:Math.ceil(Number(data.total)/ pagecount),
		        	current:Number(page)
			 	});
				var $dx_dg  = $('#dx_areat');
				$dx_dg .datagrid('loadData', data);
				var len = $dx_dg .datagrid('getRows').length;
				if(len>0){
					$dx_dg .datagrid("selectRow",0);
				}
				$('#dx_areafindbox').focus();
	  			}
			}catch(err){
				console.error(err.message);
			}
		}
	});
	
} 
</script>
