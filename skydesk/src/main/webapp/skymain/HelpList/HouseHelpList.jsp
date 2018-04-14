<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 店铺弹框 -->
<div id="housed" title="店铺帮助列表" style="width: 530px;height: 440px" class="easyui-dialog" closed=true>
  <input type="hidden" id="hpid" name="name"> <input type="hidden" id="housedser" name="housedser">
	<div id="housesbtn" class="help-qstoolbar">
		<input type="text" placeholder="搜索店铺名称<回车搜索>" class="help-qsipt" id="housefindbox" data-enter="gethouselist('1')">
	<input class="btn2" type="button" value="搜索" onclick="gethouselist('1')">
	</div>
	<table id="houset" style="overflow: hidden;"></table>
	<div class="dialog-footer">
		<div id="housepp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="housefuzhi()"/>
<!-- 				<input type="button" class="btn2" value="取消" onclick="$('#housed').dialog('close')"/>  -->
			</div>
		</div>
</div>
<script type="text/javascript">
	var housedatat = true;
	//获取店铺列表
	flags = "0";
	function selecthouse(ser){
		 $('#housedser').val(ser);
		 var findbox ='';
		 flags = "0";
		 if (ser == 'tohouse'){
			flags="1";
		 }
		 $('#housefindbox').val(findbox);
		 if(!$('#houset').data().datagrid){
			 $('#housepp').createPage({
					pageCount : 1,
					current : 1,
					backFn : function(p){
						gethouselist(p.toString());
					}
				});
			$('#houset').datagrid({
				idField : 'housename',
				height : 350,
				fitColumns : true,
				striped : true, //隔行变色
				singleSelect : true,
				pageSize : 20,
				columns : [ [ {
					field : 'HOUSEID',
					title : '店铺ID',
					width : 200,
					hidden : true
				},{
					field : 'PRICETYPE1',
					title : '调拨方式1',
					width : 200,
					hidden : true
				},{
					field : 'ROWNUMBER',title : '序号',width : 50,fixed:true,align:'center',halign:'center',
					formatter:function(value,row,index){
						var val = Math.ceil(Number(value)/ pagecount)-1;
						return isNaN(Number(value))||value==""?"":val*pagecount+Number(index)+1;
					}
				}, {
					field : 'HOUSENAME',
					title : '店铺名称',
					width : 200
				} ] ],
				onClickRow:function(rowIndex, rowData){
					
				},
				onDblClickRow : function(rowIndex, rowData) {
					housefuzhi();
				},
				toolbar :"#housesbtn"
			}).datagrid("keyCtr","housefuzhi()");
			$('#housed').dialog({
				modal:true
			});
			housedatat = false;
		 }
		gethouselist('1');
		$('#housed').dialog('open');
// 		$('#houset').prev().focus();
	$('#housefindbox').focus();
	}
    //双击、确定赋值（店铺）
     function housefuzhi(){
    	 var id = $('#uid').val();
    	 var ser = $('#housedser').val();
			var noteno = $('#unoteno').val();
			var rowData = $('#houset').datagrid('getSelected');
			if(rowData){
			if (ser == "update") {
				$('#uhouseid').val(rowData.HOUSEID);
				$('#uhousename').val(rowData.HOUSENAME);
				changehouse(id, noteno, rowData.HOUSEID);
				$('#uhousename').focus();
			} else if(ser == "search"){
				$('#shouseid').val(rowData.HOUSEID);
				$('#shousename').val(rowData.HOUSENAME);
				$('#shousename').focus();
			} else if(ser == "addfk"){
				$('#ahouseid').val(rowData.HOUSEID);
				$('#ahousename').val(rowData.HOUSENAME);
				if(xsdpdz==1){
					var provid = $('#aprovid').val();
					getpaybal(provid,rowData.HOUSEID);
				}
				$('#ahousename').focus();
			} else if(ser == "updatefk"){
				$('#uhouseid').val(rowData.HOUSEID);
				$('#uhousename').val(rowData.HOUSENAME);
				if(xsdpdz==1){
					var provid = $('#uprovid').val();
					getpaybal(provid,rowData.HOUSEID);
				}
				$('#uhousename').focus();
			} else if(ser == "addsk"){
				$('#ahouseid').val(rowData.HOUSEID);
				$('#ahousename').val(rowData.HOUSENAME);
				if(xsdpdz==1){
					var custid = $('#acustid').val();
					getincomebal(custid,rowData.HOUSEID);
				}
				$('#ahousename').focus();
			} else if(ser == "updatesk"){
				$('#uhouseid').val(rowData.HOUSEID);
				$('#uhousename').val(rowData.HOUSENAME);
				if(xsdpdz==1){
					var custid = $('#ucustid').val();
					getincomebal(custid,rowData.HOUSEID);
				}
				$('#uhousename').focus();
			}  else if(ser == "tosearch"){
				$('#stohouseid').val(rowData.HOUSEID);
				$('#stohousename').val(rowData.HOUSENAME);
				$('#stohousename').focus();
			}  else if(ser == "fromsearch"){
				$('#sfromhouseid').val(rowData.HOUSEID);
				$('#sfromhousename').val(rowData.HOUSENAME);
				$('#sfromhousename').focus();
			} else if (ser == 'tohouse'){
				$('#utohouseid').val(rowData.HOUSEID);
				$('#utohousename').val(rowData.HOUSENAME);
				$('#upricetype1').val(rowData.PRICETYPE1);
				changetohouse(id, noteno, rowData.HOUSEID);
				$('#utohousename').focus();
			}else if (ser == 'fromhouse'){
				$('#ufromhouseid').val(rowData.HOUSEID);
				$('#ufromhousename').val(rowData.HOUSENAME);
				changefromhouse(id, noteno, rowData.HOUSEID);
				$('#ufromhousename').focus();
			}else if (ser == "addep"||ser== "add") {
				$('#ahouseid').val(rowData.HOUSEID);
				$('#ahousename').val(rowData.HOUSENAME);
				$('#ahousename').focus();
			}else if(ser == "updateep"||ser=="updatehouse"){
				$('#uhouseid').val(rowData.HOUSEID);
				$('#uhousename').val(rowData.HOUSENAME);
				$('#uhousename').focus();
			}else if(ser == "hp"){
				$('#hpid').val(rowData.HOUSEID);
				$('#househp').val(rowData.HOUSENAME);
				$('#househp').focus();
			}else if(ser == "analysis"){
				$('#shouseid').val(rowData.HOUSEID);
				$('#shousename').val(rowData.HOUSENAME);
				$('#shousename').focus();
			}else if(ser == "allocation"){
				$('#tohouseid').val(rowData.HOUSEID);
				$('#tohousename').val(rowData.HOUSENAME);
				$('#tohousename').focus();
			}else if(ser == "vipjf"){
				$('#jfhouseid').val(rowData.HOUSEID);
				$('#jfhousename').val(rowData.HOUSENAME);
				$('#jfhousename').focus();
			}else if(ser == "vipcz"){
				$('#czhouseid').val(rowData.HOUSEID);
				$('#czhousename').val(rowData.HOUSENAME);
				$('#czhousename').focus();
			}
			$('#housed').dialog('close');
			}else{
				alert('请选择')
			}
    }
	//获取店铺数据
	function gethouselist(page) {
		var findbox = $('#housefindbox').val();
		var qxfs = 1;
		if(pgid&&(pgid=="pg1102"||pgid=="pg1103"||pgid=="pg1508"||pgid=="pg1613"||(pgid=="pg1403"&&flags!=1))) qxfs=2;
		else if(pgid&&(pgid=="pg1201"||pgid=="pg1210"||pgid=="pg1302"||pgid=="pg1303"||pgid=="pg1309"||(pgid=="pg1402"&&flags!=1)||pgid=="pg1501"||pgid=="pg1502"||pgid=="pg1602"||pgid=="pg1601"||pgid=="pg1607"||pgid=="pg1908"||pgid=="pg1608")) qxfs = 3;
		else if(pgid&&(pgid=="pg1402"||pgid=="pg1403")&&flags==1) qxfs = 0;
		else qxfs = 1;
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data : {
				erpser : "warehousehelplist",
				sort: "housename",
				order: "asc",
				fieldlist: "houseid,housename,pricetype1",
				noused: 0,
				cxdr: flags,
				qxfs: qxfs,
				rows: pagecount,
				page: page,
				findbox: findbox
			}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType : 'json',
			success : function(data) { //回调函数，result，返回值 
				try{if (valideData(data)){
					$('#housepp').setPage({
				        pageCount:Math.ceil(Number(data.total)/pagecount),
				        current:Number(page)
					 });
					$('#houset').datagrid('loadData', data);
					var len = $('#houset').datagrid('getRows').length;
					if(len>0){
						$('#houset').datagrid("selectRow",0);
					}
					$('#housefindbox').focus();
				}
				}catch(err){
					console.error(err.message);
				}
			}
		});
	}

</script>

