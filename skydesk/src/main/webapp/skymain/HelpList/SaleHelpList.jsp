<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- 得到销售类型列表  -->
	<div id="salef" title="售类型帮助列表" style="width: 530px; height:440px;" class="easyui-dialog" closed=true>
		<!-- 搜索栏 -->
		<div id="salesbtn" class="help-qstoolbar"><input type="hidden" id="saleser">
				<input type="text" placeholder="搜索销售方式<回车搜索>" class="help-qsipt" id="salefindbox" data-enter="getsaledata('1')" data-end="yes">
				<input class="btn2" type="button" value="搜索" onclick="getsaledata('1')">
		</div>
		<table id="salet" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="salepp" class="tcdPageCode fl" ></div>
			<div class="fr">
				<input type="button" class="btn1" value="确定" onclick="salefuzhi()"/>
<!-- 				<input type="button" class="btn2" value="取消" onclick="$('#salef').dialog('close')"/>  -->
			</div>
		</div>
	</div>
<script type="text/javascript">
var saledatagrid=true;
  //加载销售类型列表窗
function selectsaletype(saleser){
	  $('#saleser').val(saleser);
	  var findbox="";
// 		if(saleser=='add'){
// 			findbox = $('#asalename').val();
// 		}else if (saleser=='update'){
// 			findbox = $('#usalename').val();
// 		}else if (saleser=='search'){
// 			findbox = $('#ssalenmae').val();
// 		}else if (saleser=='qaddwarem'){
// 			findbox = $('#usalename').val();
// 		}
		$('#salefindbox').val(findbox);
		if(!$('#salet').data().datagrid){
			$('#salepp').createPage({
				pageCount :1,
		        current:1,
		        backFn:function(p){
		        	getsaledata(p.toString());
		        }
			 });
			$('#salet').datagrid(
					{
						height:350,
						striped : true, //隔行变色
						singleSelect : true,
						showFooter : true,
						fitColumns:true,
						pageSize:20,
						columns : [ [ {
							field : 'ROWNUMBER',
							title : '序号',
							width : 50,
							fixed : true,
							align : 'center',
							halign : 'center',
							formatter : function(value, row, index) {
								if (value != null) {
									return index + 1;
								} else {
									return value;
								}
							}
						},{
							field : 'SALEID',
							title : 'ID',
							width : 200,
							hidden : true
						}, {
							field : 'SALENAME',
							title : '销售类型',
							width : 200
						} ] ],
						onDblClickRow:function(rowIndex, rowData){
							salefuzhi();
						},
					toolbar:'#salesbtn'
					}).datagrid("keyCtr","salefuzhi()");
			saledatagrid = false;
		}
		getsaledata("1");//加载数据
		$('#salef').dialog('open');
		$('#salefindbox').focus();
	}  
  //双击、确定赋值（销售类型）
   function salefuzhi(){
	  var saleser = $('#saleser').val();
	  var rowData = $('#salet').datagrid('getSelected');
	  if(rowData){
	   if(saleser=='add'){								
			$('#asalename').val(rowData.SALENAME);
			$('#asaleid').val(rowData.SALEID);
			$('#asalename').focus();
		}else if(saleser=='update'){
			$('#wusalename').val(rowData.SALENAME);
			$('#wusaleid').val(rowData.SALEID);
			$('#wusalename').focus();
		}else if(saleser=='quickadd'){
			$('#wquicksalename').val(rowData.SALENAME);
			$('#usaleid').val(rowData.SALEID);
			$('#wquicksalename').focus();
		}else if(saleser=='quickupdatew'){
			$('#wquickusalename').val(rowData.SALENAME);
			$('#usaleid').val(rowData.SALEID);
			$('#wquickusalename').focus();
		}else if(saleser=='search'){
			$('#hsalename').val(rowData.SALENAME);
			$('#hsaleid').val(rowData.SALEID);
			$('#hsalename').focus();
		}else if(saleser=='qaddwarem'){
			$('#wquickusalename').val(rowData.SALENAME);
			$('#usaleid').val(rowData.SALEID);
			var wareid = $('#wquickuwareid').val();
			if(isAddWarem()&&wareid!=''){
				var noteno = $('#unoteno').val();
				getwaremsum(rowData.SALEID,wareid,noteno,true);
			}else{
				alerttext('请输入货号！');
				$('#wquickuwareno').focus();
			}
			$('#wquicksalename').focus();
		}
		//getnearsale(wareid,rowData.SALEID,saleser);
		$('#salef').dialog('close');
	  }else{
		  alert('请选择');
	  }
  }
	//获取销售类型列表
	function getsaledata(page) { //定义回调函数
		//url="wareservlet?wareser=getsaledata";
	var findbox = $("#salefindbox").val();
	$.ajax({
		type: "POST",
		url: "/skydesk/fyjerp",
	//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "salecodelist",
			findbox: findbox,
			rows: pagecount,
			fieldlist: "*",
			page: page
		},
		dataType: 'json',
		success: function(data) {
		try{
		if (valideData(data)) {
				$('#salepp').setPage({
			        pageCount:Math.ceil(Number(data.total)/ pagecount),
			        current:Number(page)
				 });
				$('#salet').datagrid('loadData', data);
				var len = $('#salet').datagrid('getRows').length;
				if(len>0){
					$('#salet').datagrid("selectRow",0);
				}
					$('#salefindbox').focus();
		    }
			}catch(err){
				console.error(err.message);
			}
		}	
	});
	} 
	
    </script>
