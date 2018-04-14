<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 得到会员列表 -->
<div id="vipd" title="会员帮助列表" style="width: 530px; height:440px;" class="easyui-dialog" closed=true>
   <input type="hidden" id="vipser" name="vipser">
<div id="vipsbtn" class="help-qstoolbar">
		<input type="text" placeholder="搜索会员名称<回车搜索>" class="help-qsipt" id="vipfindbox" data-enter="getguestviplist('1')" data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getguestviplist('1')">
		</div>
	<table id="vipt" style="overflow: hidden;"></table>
	<div class="dialog-footer">
			<div id="vippp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				 <input type="button" class="btn1" value="确定" onclick="vipfuzhi()"/>
<!-- 				<input type="button" class="btn2" value="取消" onclick="$('#vipd').dialog('close')"/> -->
			</div>
		</div>
</div>
<script type="text/javascript">
 var vipdatat = true;
 function selectvip(ser){
	 $('#vipser').val(ser);
	 var findbox='';
// 	 if(ser=='search'){
// 		 findbox=$('#sguestname').val();
// 		}else if(ser=='update'){
// 			 findbox=$('#uguestname').val();
// 		}else if(ser=='sear'){
// 			 findbox=$('#svtname').val();
// 		}else if(ser=='add'){
// 			 findbox=$('#avtname').val();
// 		}else if(ser=='shopsale'){
// 			 findbox=$('#guestfindbox').val();
// 		}
		$('#vipfindbox').val(findbox);
		if(vipdatat){
			$('#vipd').dialog({
					modal:true
			});
			$('#vippp').createPage({
			pageCount:1,
			current:1,
			backFn: function(p) {
					getguestviplist(p.toString());
				}
			});
			$('#vipt').datagrid({
				height:350,
				idField : 'vipname',
				fitColumns : true,
				striped : 	true, //隔行变色
				singleSelect :true,
				pageSize:20,
				checkOnSelect:true,
				columns : [ [ {
					field : 'GUESTID',
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
					field : 'GUESTNAME',
					title : '会员名',
					width : 200
				},{
					field : 'VIPNO',
					title : '会员卡号',
					width : 200
				},{
					field : 'MOBILE',
					title : '手机号',
					width : 200
				},{
					field : 'VTNAME',
					title : '会员类型',
					width : 200
				},{
					field : 'VTID',
					title : '会员类型id',
					width : 200,
					hidden : true
				},{
					field : 'BALCURR',
					title : '会员类型id',
					width : 200,
					hidden : true
				},{
					field : 'BALCENT',
					title : '会员类型id',
					width : 200,
					hidden : true
				},{
					field : 'DISCOUNT',
					title : '会员折扣',
					width : 200,
					hidden : true
				}
				] ],
				onDblClickRow:function(rowIndex, rowData){
					vipfuzhi();
					},
				toolbar:"#vipsbtn"
			}).datagrid("keyCtr","vipfuzhi()");
			vipdatat = false;
		}
	getguestviplist("1");	
	$('#vipd').dialog('open');
	$('#vipfindbox').focus();
 }
 //双击、确定赋值（会员）
 function vipfuzhi(){
	 var ser=$('#vipser').val();
	 var rowData = $('#vipt').datagrid('getSelected');
	 if(rowData){
	 if(ser=='search'){
			$('#sguestname').val(rowData.GUESTNAME);
			$('#sguestid').val(rowData.GUESTID);
			$('#sguestname').focus();
		}else if(ser=='update'){
			$('#uguestid').val(rowData.GUESTID);
			$('#udiscount').val(Number(rowData.DISCOUNT).toFixed(2));
			$('#alldiscount').val(Number(rowData.DISCOUNT).toFixed(2));
			$('#uguestname').val(rowData.GUESTNAME);
			$('#vipno').html(rowData.VIPNO);
			$('#mobile').html(rowData.MOBILE);
			$('#balcent').html(rowData.BALCENT);
			$('#vtid').val(rowData.VTID);
			$('#vtname').html(rowData.VTNAME);
			$('#balcurr').html(rowData.BALCURR);
			$('#guestinfo').show();
			changeguest(rowData.GUESTID);
			alldisc();
			$('#uguestname').focus();
		}else if(ser=='sear'){
			$('#svtid').val(rowData.VTID);
			$('#svtname').val(rowData.VTNAME);
			$('#svtname').focus();
		}else if(ser=='add'){
			$('#avtid').val(rowData.VTID);
			$('#avtname').val(rowData.VTNAME);
			$('#avtname').focus();
		}else if(ser=='shopsalevip'){
			$('#guestid').val(rowData.GUESTID);
			$('#discount').val(Number(rowData.DISCOUNT).toFixed(2));
			$('#guestfindbox').val(rowData.GUESTNAME);
			$('#guestname').html(rowData.GUESTNAME);
			$('#vipno').html(rowData.VIPNO);
			$('#mobile').html(rowData.MOBILE);
			$('#balcent').html(rowData.BALCENT);
			$('#vtid').val(rowData.VTID);
			$('#vtname').html(rowData.VTNAME);
			$('#balcurr').html(rowData.BALCURR);
			changeguest(rowData.GUESTID);
			changediscount();
		}
		$('#vipd').dialog('close');
	 }else{
		 alert('请选择');
	 }
 }
 function getguestviplist(page){
	var findbox = $('#vipfindbox').val();
	 $.ajax({ 
	       	type: "POST",   //访问WebService使用Post方式请求 
	        url: "/skydesk/fybuyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
	        data: {
	        	erpser:"guestviplist",
	        	page:page,
	        	rows:pagecount,
	        	sort:"vipno",
	        	order:"asc",
	        	findbox:findbox,
	        	usetag: 0,
	        	fieldlist: "A.GUESTID,A.GUESTNAME,A.VIPNO,A.VTID,A.MOBILE,A.BALCENT,A.BALCURR,B.VTNAME,B.DISCOUNT"
	        	},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
	        dataType: 'json', 
	        success: function(data) {     //回调函数，result，返回值 
	        	try{if (valideData(data)){
					  $('#vipt').datagrid('loadData', data);
		    		$('#vippp').setPage({
		    				pageCount:Math.ceil(data.total/ pagecount),
		    				current:Number(page)
		    		});
				  }
	        	
	        	var len = $('#vipt').datagrid('getRows').length;
				if(len>0){
					$('#vipt').datagrid("selectRow",0);
				}
					$('#vipfindbox').focus();
	        	}catch(err){
					console.error(err.message);
				}
	        }                 
	        });		
 }
</script>
