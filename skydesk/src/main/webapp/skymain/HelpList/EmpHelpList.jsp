<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="employed" title="销售员列表" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true> 
<input type="hidden" id="hpid" name="name"> <input type="hidden" id="employedser" name="employedser">
	<div id="epsbtn" class="help-qstoolbar">
		<input type="text" placeholder="搜索销售员名称<回车搜索>" class="help-qsipt" id="employefindbox" data-enter="getemployedata('1')"  data-end="yes">
		<input type="button" class="btn2" value="搜索" onclick="getemployedata('1')" > 
		</div>
	<table id="employet" style="overflow: hidden;"></table>
	<div class="dialog-footer">
	<div id="employepp" class="tcdPageCode fl"></div> 
	<div class="fr">
		<input type="button" class="btn1" value="确定" onclick="employefuzhi()"/>
<!-- 		<input type="button" class="btn2" value="取消" onclick="$('#employed').dialog('close')"/>  -->
	</div>
	</div>
</div>

<script type="text/javascript">
var employet = true;
function selectemploye(ser){
	$('#employedser').val(ser);
	var findbox ='';
// 	if (ser == "analysis") {
// 		findbox = $('#epname').val();
// 	}else if(ser == "search"){
// 		findbox = $('#ssalemanname').val()||$('#sepname').val();
// 	}else if(ser == "shopsale"){
// 		findbox =$('#saleman').val();
// 	}else if(ser == "add"){
// 		findbox =$('#aepname').val();
// 	}else if(ser == "update"){
// 		findbox =$('#uepname').val();
// 	}else if(ser == "updateep"){
// 		findbox =$('#usalemanname').val();
// 	}else if(ser == "lastop"){
// 		findbox =$('#lastop').val();
// 	}
	$('#employefindbox').val(findbox);
	$('#employepp').createPage({
		pageCount : 1,
		current : 1,
		backFn : function(p){
			getemployedata(p);
		}
	});
	if(employet){
		$('#employet').datagrid(
				{
					height:350,
					idField : 'EPID',
					fitColumns : true,
					striped : 	true, //隔行变色
					pageSize:10,
					singleSelect : true,
					checkOnSelect:true,
					columns : [ [ {
						field : 'EPID',
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
						field : 'EPNAME',
						title : '销售员',
						width : 200,
						align : 'center',
						halign :'center'
					},{
						field : 'EPNO',
						title : '用户代号',
						width : 200,
						align : 'center',
						halign :'center'
					}
					] ],
					onClickRow:function(rowIndex, rowData){
						
					},
					onDblClickRow : function(rowIndex, rowData) {
						employefuzhi();
					},
					toolbar :"#epsbtn"
	         	 
				}).datagrid("keyCtr","employefuzhi()");
		employet = false;
	}
	getemployedata('1');
	$('#employed').dialog('open');
// 	$('#emplyet').prev().focus();
	$('#employefindbox').focus();
	}
	function getemployedata(page){
		 var ser = $('#employedser').val();
		if(ser=="shopsale"){
			getemployelistbyhouseid(page);
		}else{
			getemployelist(page);
		}
	}
//双击、确定赋值（销售员）a
function employefuzhi(){
	 var ser = $('#employedser').val();
	var rowData = $('#employet').datagrid('getSelected');
	if(rowData){
		if (ser == "analysis") {
			$('#epid').val(rowData.EPID);
			$('#epname').val(rowData.EPNAME);
			$('#epname').focus();
		}else if(ser == "search"){
			$('#ssalemanid,#sepid,#shandmanid').val(rowData.EPID);
			$('#ssalemanname,#sepname,#shandmanname').val(rowData.EPNAME);
			$('#ssalemanname,#shandmanname').focus();
		}else if(ser == "shopsale"){
			$('#epid').val(rowData.EPID);
			$('#saleman').val(rowData.EPNAME);
			$('#saleman').focus();
		}else if(ser == "addcust"||ser == "addsk"){
			$('#ahandmanid').val(rowData.EPID);
			$('#ahandmanname').val(rowData.EPNAME);
			$('#ahandmanname').focus();
		}else if(ser == "updatecust"||ser == "updatesk"){
			$('#uhandmanid').val(rowData.EPID);
			$('#uhandmanname').val(rowData.EPNAME);
			$('#uhandmanname').focus();
		}else if(ser == "add"){
			$('#aepid').val(rowData.EPID);
			$('#aepname').val(rowData.EPNAME);
			$('#aepname').focus();
		}else if(ser == "update"){
			$('#uepid').val(rowData.EPID);
			$('#uepname').val(rowData.EPNAME);
			$('#uepname').focus();
		}else if(ser == "lastop"){
			$('#slastop').val(rowData.EPNAME);
			$('#slastop').focus();
		}else if(ser == "updateep"){
			$('#uepid').val(rowData.EPID);
			$('#usalemanname').val(rowData.EPNAME);
			changeemp(rowData);
			$('#usalemanname').focus();
		}
		$('#employed').dialog('close');
	}else{
		alert('请选择');
	}
}
//获取销售员数据
function getemployelist(page){
	var findbox = $('#employefindbox').val();
	$.ajax({ 
		type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
        data: {
        	erpser:"employelist",
        	nosued: 0,
        	fieldlist: "a.epid,a.epno,a.epname",
        	rows: pagecount,
        	page:page,
			findbox:findbox
			},        //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        dataType: 'json', 
        success: function(data) {     //回调函数，result，返回值 
        	if(valideData(data)){
				$('#employepp').setPage({
			        pageCount:Math.ceil(Number(data.total)/ pagecount),
			        current:Number(page)
				 });
				$('#employet').datagrid('loadData', data);
				var len = $('#employet').datagrid('getRows').length;
				if(len>0){
					$('#employet').datagrid("selectRow",0);
				}
					$('#employefindbox').focus();
        	}
        	
        }                 
        });
}
function getemployelistbyhouseid(page){
	var houseid = Number(getValuebyName("HOUSEID"));
	if(houseid>0){
		var findbox = $('#employefindbox').val();
		$.ajax({ 
			type: "POST",   //访问WebService使用Post方式请求 
	        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
	        data: {
	        	erpser:"employelist",
	        	nosued: 0,
	        	fieldlist: "a.epid,a.epno,a.epname",
	        	rows: pagecount,
	        	page: page,
				findbox:findbox
			},        //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
	        dataType: 'json', 
	        success: function(data) {     //回调函数，result，返回值 
	        	if(valideData(data)){
					$('#employepp').setPage({
				        pageCount:Math.ceil(Number(data.total)/ pagecount),
				        current:Number(page)
					 });
					$('#employet').datagrid('loadData', data);
					var len = $('#employet').datagrid('getRows').length;
					if(len>0){
						$('#employet').datagrid("selectRow",0);
					}
						$('#employefindbox').focus();
	        	}
        	
      	  }                 
        });
	}else
		alerttext("请授权所属店铺");
}
</script>