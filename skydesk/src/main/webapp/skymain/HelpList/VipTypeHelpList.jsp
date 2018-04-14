<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 得到会员类型列表  -->
	<div id="viptypef" title="会员类型帮助列表" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
	<!-- 搜索栏 -->
	<div id="viptypesbtn" class="help-qstoolbar">
		<input type="text" class="help-qsipt" id="viptypefindbox" data-enter="getviptypedata('1')"  placeholder="搜索会员类型<回车搜索>"  data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getviptypedata('1')">
		<input  type="hidden" id="viptypeser" >
	</div>
		<table id="viptypet" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="viptypepp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="viptypefuzhi()" />
<!-- 				<input type="button" class="btn2" value="取消" onclick="$('#viptypef').dialog('close')" />  -->
			</div>
		</div>
	</div>
	
<script type="text/javascript">
var viptypedatagrid=true;
//加载会员类型列表窗
function selectviptype(viptypeser){
	  $("#viptypeser").val(viptypeser);
	  var findbox='';
// 	  if(viptypeser=='add'){			
// 		  findbox = $('#avtname').val();
// 	  }else if(viptypeser=='update'){
// 		  findbox = $('#uvtname').val();
// 	  }else if(viptypeser=='search'){
// 		  findbox = $('#svtname').val();
// 	  }
	  $('#viptypefindbox').val(findbox);
	  if(viptypedatagrid){
		$('#viptypepp').createPage({
	        current:1,
	        backFn:function(p){
	        	getviptypedata(p.toString());
	        }
		 });
		$('#viptypet').datagrid(
				{
					height:350,
					idField : 'vtname',
					fitColumns : true,
					striped : true, //隔行变色
					singleSelect : true,
					pageSize:20,
					columns : [ [{
					         	field : 'VTID',
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
								field : 'VTNAME',
								title : '会员类型',
								width : 200
							} ] ],
							onDblClickRow:function(rowIndex, rowData){
								viptypefuzhi();
							},
						toolbar:'#viptypesbtn'
						}).datagrid("keyCtr","viptypefuzhi()");
                  viptypedatagrid = false; 
	  }
       getviptypedata('1');//加载数据
       $('#viptypef').dialog('open');
       $('#viptypefindbox').focus();
}
			
//双击、确定赋值（会员类型）
function viptypefuzhi(){
	  var rowData = $('#viptypet').datagrid('getSelected');
	  var viptypeser= $('#viptypeser').val();
	  if(rowData){
	   if(viptypeser=='add'){		
			$('#avtname').val(rowData.VTNAME);
			$('#avtid').val(rowData.VTID);
			$('#avtname').focus();
	   }else if(viptypeser=='update'){	
			$('#uvtname').val(rowData.VTNAME);
			$('#uvtid').val(rowData.VTID);
			$('#uvtname').focus();
	   }else if(viptypeser=='search'){								
			$('#svtname').val(rowData.VTNAME);
			$('#svtid').val(rowData.VTID);
			$('#svtname').focus();
	   }		
	   $('#viptypef').dialog('close');
	  }else{
		  alert('请选择');
	  }
}
	//获取会员类型列表
	function getviptypedata(page) { //定义回调函数#')
	var findbox = $('#viptypefindbox').val();
	findbox = findbox==undefined?"":findbox;
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "guesttypelist",
			findbox: findbox,
			rows: pagecount,
			fieldlist: "*",
			noused: 0,
			page: page
		},
		dataType: 'json',
		success: function(data) {
			try{
			if (valideData(data)) {
				$('#viptypepp').setPage({
			        pageCount:Math.ceil(Number(data.total)/ pagecount),
			        current:Number(page)
				 });
				$('#viptypet').datagrid('loadData', data);
				var len = $('#viptypet').datagrid('getRows').length;
				if(len>0){
					$('#viptypet').datagrid("selectRow",0);
				}
					$('#viptypefindbox').focus();
		  }
			}catch(err){
				console.error(err.message);
			}
		}
		});
		
	}              
                  
                  
                  
                  
                  
                  
                  
	</script>