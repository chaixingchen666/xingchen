<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- 得到品牌列表  -->
	<div id="dx_brandf" title="选择品牌" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
	<!-- 搜索栏 -->
	<div id="dx_brandsbtn" class="help-qstoolbar">
		<input type="text" class="help-qsipt" id="dx_brandfindbox" data-enter="getdx_branddata('1')"  placeholder="搜索品牌<回车搜索>"  data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getdx_branddata('1')">
		<input  type="hidden" id="dx_brandser" >
	</div>
		<table id="dx_brandt" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="dx_brandpp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="dx_brandselects()" />
			</div>
		</div>
	</div>
<script type="text/javascript">
 //加载品牌列表窗
function selectdx_brand(dx_brandser){
	$("#dx_brandser").val(dx_brandser);
	var findbox='';
	$('#dx_brandfindbox').val(findbox);
	var $dx_dg = $('#dx_brandt');
	if(!$dx_dg .data().datagrid){
		$('#dx_brandpp').createPage({
	        current:1,
	        backFn:function(p){
	        	getdx_branddata(p.toString());
	        }
		 });
		var empty = {};
		var options = {
				height:350,
				idField : 'BRANDID',
				fitColumns : true,
				striped : true, //隔行变色
				singleSelect : true,
				selectOnCheck: false,
				checkOnSelect: false,
				pageSize:20,
				columns : [ [ {
					field : 'BRANDID',
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
					field : 'BRANDNAME',
					title : '品牌',
					width : 200
				} ] ],
				toolbar: '#dx_brandsbtn'
			};
		$dx_dg.datagrid(options);
		getdx_branddata('1');//加载数据
	}
	$dx_dg.datagrid('clearChecked');
	getdx_branddata(1);
	$('#dx_brandf').dialog('open');
	$('#dx_brandfindbox').focus();
}
  //双击、确定赋值（品牌）
function dx_brandselects(){
	var checkrows = $('#dx_brandt').datagrid('getChecked');
	var dx_brandser = $('#dx_brandser').val();
	var namearr = [];
	var valuearr = [];
	if(checkrows){
		for(var i in checkrows){
			var row = checkrows[i];
			valuearr.push(row.BRANDID); 
			namearr.push(row.BRANDNAME); 
		}
	}
	var name = namearr.join(",");
	var value = "^"+valuearr.join("^")+"^";
	if(value=="^^") value="";
	if(dx_brandser=='search'){
		$('#sbrandname').val(name);
		$('#sbrandid').val(value);
		$('#sbrandname').focus();
	}else if(dx_brandser=='load'){
		$('#lbrandname').val(name);
		$('#lbrandid').val(value);
		$('#lbrandname').focus();
	}
	$('#dx_brandf').dialog('close');
}
//获取品牌列表
function getdx_branddata(page) { //定义回调函数#')
	var findbox = $('#dx_brandfindbox').val();
	findbox = findbox==undefined?"":findbox;
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data :{erpser:"brandhelplist",findbox:findbox,rows:pagecount,filedlist:"*",page:page}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
				$('#dx_brandpp').setPage({
		       		pageCount:Math.ceil(Number(data.total)/ pagecount),
		        	current:Number(page)
			 	});
				var $dx_dg  = $('#dx_brandt');
				$dx_dg .datagrid('loadData', data);
				var len = $dx_dg .datagrid('getRows').length;
				if(len>0){
					$dx_dg .datagrid("selectRow",0);
				}
				$('#dx_brandfindbox').focus();
	  			}
			}catch(err){
				console.error(err.message);
			}
		}
	});
	
} 
</script>
