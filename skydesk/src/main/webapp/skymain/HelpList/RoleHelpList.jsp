<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- 得到角色列表  -->
	<div id="rolef" title="Dialog" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
	<!-- 搜索栏 -->
	<div id="rolesbtn" class="help-qstoolbar">
		<input type="text" class="help-qsipt" id="rolefindbox" data-enter="getroledata('1')"  placeholder="搜索角色<回车搜索>"  data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getroledata('1')">
		<input  type="hidden" id="roleser" >
	</div>
		<table id="rolet" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="rolepp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="rolefuzhi()" />
<!-- 				<input type="button" class="btn2" value="取消" onclick="$('#rolef').dialog('close')" />  -->
			</div>
		</div>
	</div>

<script type="text/javascript">
var roledatagrid=true;
  //加载角色列表窗
function selectuserrole(roleser){
	  $("#roleser").val(roleser);
	  var findbox='';
// 	  if(roleser=='add'){								
// 			findbox = $('#adlevelname').val();
// 		}else if(roleser=='update'){
// 			findbox = $('#ulevelname').val();
// 		}
		$('#rolefindbox').val(findbox);
		if(roledatagrid){
			$('#rolepp').createPage({
		        current:1,
		        backFn:function(p){
		        	getroledata(p.toString());
		        }
			 });
			$('#rolet').datagrid(
					{
						height:350,
						idField : 'rolename',
						fitColumns : true,
						striped : true, //隔行变色
						singleSelect : true,
						pageSize:20,
						columns : [ [ {
							field : 'LEVELID',
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
							field : 'LEVELNAME',
							title : '角色',
							width : 200
						} ] ],
						onDblClickRow:function(rowIndex, rowData){
							rolefuzhi();
						},
						
					toolbar:'#rolesbtn',
					}).datagrid("keyCtr","rolefuzhi()");
			$('#rolef').dialog({
				modal:true,
				title:'角色帮助列表'
			});
			roledatagrid = false;
		}
		getroledata('1');//加载数据
		$('#rolef').dialog('open');
		$('#rolefindbox').focus();
  }
  //双击、确定赋值（角色）
   function rolefuzhi(){
	  var rowData = $('#rolet').datagrid('getSelected');
	  var roleser= $('#roleser').val();
	  if(rowData){
	   if(roleser=='add'){								
			$('#alevelname').val(rowData.LEVELNAME);
			$('#alevelid').val(rowData.LEVELID);
			$('#alevelname').focus();
		}else if(roleser=='update'){
			$('#ulevelname').val(rowData.LEVELNAME);
			$('#ulevelid').val(rowData.LEVELID);
			$('#ulevelname').focus();
		}
		$('#rolef').dialog('close');
	  }else{
		  alert('请选择');
	  }
  }
	//获取角色列表
	function getroledata(page) { //定义回调函数#')
	var findbox = $('#rolefindbox').val();
	findbox = findbox==undefined?"":findbox;
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "userrolelist",
			findbox: findbox,
			rows: pagecount,
			fieldlist: "*",
			page: page
		},
		dataType: 'json',
		success: function(data) {
			try{
			if (valideData(data)) {
				$('#rolepp').setPage({
			        pageCount:Math.ceil(Number(data.total)/ pagecount),
			        current:Number(page),
				 });
				$('#rolet').datagrid('loadData', data);
				var len = $('#rolet').datagrid('getRows').length;
				if(len>0){
					$('#rolet').datagrid("selectRow",0);
				}
					$('#rolefindbox').focus();
		  }
			}catch(err){
				console.error(err.message);
			}
		}
		});
	} 
    </script>

</html>