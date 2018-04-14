<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- 得到订货成员列表  -->
	<div id="dx_omepf" title="选择订货成员" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
	<!-- 搜索栏 -->
	<div id="dx_omepsbtn" class="help-qstoolbar">
		<input type="text" class="help-qsipt" id="dx_omepfindbox" data-enter="getdx_omepdata('1')"  placeholder="搜索订货成员<回车搜索>"  data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getdx_omepdata('1')">
		<input  type="hidden" id="dx_omepser" >
	</div>
		<table id="dx_omept" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="dx_omeppp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="dx_omepselects()" />
			</div>
		</div>
	</div>
<script type="text/javascript">
 //加载订货成员列表窗
function selectdx_omep(dx_omepser){
	$("#dx_omepser").val(dx_omepser);
	var findbox='';
	$('#dx_omepfindbox').val(findbox);
	var $dx_dg = $('#dx_omept');
	if(!$dx_dg .data().datagrid){
		$('#dx_omeppp').createPage({
	        current:1,
	        backFn:function(p){
	        	getdx_omepdata(p.toString());
	        }
		 });
		var empty = {};
		var options = {
				height:350,
				idField : 'OMEPID',
				fitColumns : true,
				striped : true, //隔行变色
				singleSelect : true,
				selectOnCheck: false,
				checkOnSelect: false,
				pageSize:20,
				columns : [ [ {
					field : 'OMEPID',
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
					field : 'OMEPNAME',
					title : '订货成员',
					width : 200
				} ] ],
				toolbar: '#dx_omepsbtn'
			};
		$dx_dg.datagrid(options);
		getdx_omepdata('1');//加载数据
	}
	$dx_dg.datagrid('clearChecked');
	getdx_omepdata(1);
	$('#dx_omepf').dialog('open');
	$('#dx_omepfindbox').focus();
}
  //双击、确定赋值（订货成员）
function dx_omepselects(){
	var checkrows = $('#dx_omept').datagrid('getChecked');
	var dx_omepser = $('#dx_omepser').val();
	var namearr = [];
	var valuearr = [];
	if(checkrows){
		for(var i in checkrows){
			var row = checkrows[i];
			valuearr.push(row.OMEPID); 
			namearr.push(row.OMEPNAME); 
		}
	}
	var name = namearr.join(",");
	var value = "^"+valuearr.join("^")+"^";
	if(value=="^^") value="";
	if(dx_omepser=='search'){
		$('#somepname').val(name);
		$('#somepid').val(value);
		$('#somepname').focus();
	}else if(dx_omepser=='load'){
		$('#lomepname').val(name);
		$('#lomepid').val(value);
		$('#lomepname').focus();
	}
	$('#dx_omepf').dialog('close');
}
//获取订货成员列表
function getdx_omepdata(page) { //定义回调函数#')
	var findbox = $('#dx_omepfindbox').val();
	findbox = findbox==undefined?"":findbox;
	var omid = Number(top.omid);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "listallomuser",
			findbox : findbox,
			omid: omid,
			page : page
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                     
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
				$('#dx_omeppp').setPage({
		       		pageCount:Math.ceil(Number(data.total)/ pagecount),
		        	current:Number(page)
			 	});
				var $dx_dg  = $('#dx_omept');
				$dx_dg .datagrid('loadData', data);
				var len = $dx_dg .datagrid('getRows').length;
				if(len>0){
					$dx_dg .datagrid("selectRow",0);
				}
				$('#dx_omepfindbox').focus();
	  			}
			}catch(err){
				console.error(err.message);
			}
		}
	});
	
} 
</script>
