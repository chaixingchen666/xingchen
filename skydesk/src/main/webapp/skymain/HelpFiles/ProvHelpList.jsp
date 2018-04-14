<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- 得到供应商列表  -->
	<div id="dx_provf" title="选择供应商" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
	<!-- 搜索栏 -->
	<div id="dx_provsbtn" class="help-qstoolbar">
		<input type="text" class="help-qsipt" id="dx_provfindbox" data-enter="getdx_provdata('1')"  placeholder="搜索供应商<回车搜索>"  data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getdx_provdata('1')">
		<input  type="hidden" id="dx_provser" >
	</div>
		<table id="dx_provt" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="dx_provpp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="dx_provselects()" />
			</div>
		</div>
	</div>
<script type="text/javascript">
 //加载供应商列表窗
function selectdx_prov(dx_provser){
	$("#dx_provser").val(dx_provser);
	var findbox='';
	$('#dx_provfindbox').val(findbox);
	var $dx_dg = $('#dx_provt');
	if(!$dx_dg .data().datagrid){
		$('#dx_provpp').createPage({
	        current:1,
	        backFn:function(p){
	        	getdx_provdata(p.toString());
	        }
		 });
		var empty = {};
		var options = {
				height:350,
				idField : 'PROVID',
				fitColumns : true,
				striped : true, //隔行变色
				singleSelect : true,
				selectOnCheck: false,
				checkOnSelect: false,
				pageSize:20,
				columns : [ [ {
					field : 'PROVID',
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
					field : 'PROVNAME',
					title : '供应商',
					width : 200
				} ] ],
				toolbar: '#dx_provsbtn'
			};
		$dx_dg.datagrid(options);
		getdx_provdata('1');//加载数据
	}
	$dx_dg.datagrid('clearChecked');
	getdx_provdata(1);
	$('#dx_provf').dialog('open');
	$('#dx_provfindbox').focus();
}
  //双击、确定赋值（供应商）
function dx_provselects(){
	var checkrows = $('#dx_provt').datagrid('getChecked');
	var dx_provser = $('#dx_provser').val();
	var namearr = [];
	var valuearr = [];
	if(checkrows){
		for(var i in checkrows){
			var row = checkrows[i];
			valuearr.push(row.PROVID); 
			namearr.push(row.PROVNAME); 
		}
	}
	var name = namearr.join(",");
	var value = "^"+valuearr.join("^")+"^";
	if(value=="^^") value="";
	if(dx_provser=='search'){
		$('#sprovname').val(name);
		$('#sprovid').val(value);
		$('#sprovname').focus();
	}else if(dx_provser=='load'){
		$('#lprovname').val(name);
		$('#lprovid').val(value);
		$('#lprovname').focus();
	}
	$('#dx_provf').dialog('close');
}
//获取供应商列表
function getdx_provdata(page) { //定义回调函数#')
	var findbox = $('#dx_provfindbox').val();
	findbox = findbox==undefined?"":findbox;
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data:  {
			erpser : "providelisthelp",
			sort: "provname",
			order: "asc",
			noused: 0,
			fiellist: "*",
			rows: pagecount,
			page : page,
			findbox : findbox
		}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
				$('#dx_provpp').setPage({
		       		pageCount:Math.ceil(Number(data.total)/ pagecount),
		        	current:Number(page)
			 	});
				var $dx_dg  = $('#dx_provt');
				$dx_dg .datagrid('loadData', data);
				var len = $dx_dg .datagrid('getRows').length;
				if(len>0){
					$dx_dg .datagrid("selectRow",0);
				}
				$('#dx_provfindbox').focus();
	  			}
			}catch(err){
				console.error(err.message);
			}
		}
	});
	
} 
</script>
