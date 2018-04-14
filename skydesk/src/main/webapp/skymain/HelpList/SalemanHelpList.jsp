<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <div id="salemand" title="前台销售员列表" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
		<!-- 搜索栏 -->
		<div id="salemansbtn" class="help-qstoolbar">
			<input type="hidden" id="salemanser">
			<input type="text" placeholder="搜索销售员名称<回车搜索>" class="help-qsipt" id="salemanfindbox" data-end="yes"
			data-enter="getsalemanlist('1',$('#uhouseid').val(),$('#unoteno').val())" style="width:310px">
			<input class="btn2" type="button" value="搜索" onclick="getsalemanlist('1',$('#uhouseid').val(),$('#unoteno').val())">
			<label><input type="checkbox" id="onlyhousesaleman" checked>仅显示本店铺销售人</label>
		</div>
	<table id="salemant" style="overflow: hidden;"></table>
	<div class="dialog-footer">
	<div id="salemanpp" class="tcdPageCode fl" ></div> 
	</div>	
</div>
<script type="text/javascript">
function getwaresalemanname(id,noteno){
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "getwaresalemanname",
			noteno : noteno,
			id : id
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			try{if(valideData(data)) {
				if(data.total==0){
					$('#usalemanname').val("");
				}else{
					var rows = data.rows;
					var salemanname = rows[0].SALEMANLIST;
					$('#usalemanname').val(salemanname);
					$("#gg").datagrid("updateRow",{
						index: dqindex,
						row: {
							SALEMANLIST: salemanname
						}
					});
				}
			}
			}catch(err){
				console.error(err.message);
			}
		}
	});
}
var salemandatat = true;
function selectsaleman(ser){
	var check = false;
	var saleman = "";
	var houseid = $('#uhouseid').val();
	var noteno = $('#unoteno').val();
	var onlyhouse = wareoutparams.charAt(3);
	if(onlyhouse==1) $("#onlyhousesaleman").prop("checked",true);
	else $("#onlyhousesaleman").removeProp("checked");
	if(!$('#salemant').data().datagrid){
		$("#onlyhousesaleman").change(function(){
			var checked = $(this).prop("checked")?1:0;
			var paramsarr = wareoutparams.split("");
			paramsarr.splice(3,1,checked);
			wareoutparams = paramsarr.join("");
			wareoutparams += "0000000000";
			setparam({
				pgid: "wareoutcs",
				usymbol: "wareoutparams",
				uvalue: wareoutparams.substring(0, 10)
			});
			$('#salemanpp').refreshPage();
		});
	$('#salemanpp').createPage({
		pageCount : 1,
		current : 1,
		backFn : function(p){
			var houseid = $('#uhouseid').val();
			var noteno = $('#unoteno').val();
			getsalemanlist(p.toString(),houseid,noteno);
		}
	});
	$('#salemant').datagrid(
			{
				height:350,
				idField : 'salemanname',
				fitColumns : true,
				striped : 	true, //隔行变色
				pageSize:20,
				singleSelect :true,
				checkOnSelect:false,
				selectOnCheck:false,
				columns : [ [{
					field : 'ROWNUMBER',title : '序号',width : 50,fixed:true,align:'center',halign:'center',
					formatter:function(value,row,index){
						var val = Math.ceil(Number(value)/ pagecount)-1;
						return isNaN(Number(value))||value==""?"":val*pagecount+Number(index)+1;
					}
				}, {
					field : 'EPID',
					title : 'ID',
					width : 200,
					hidden : true
				},{
					field : 'EPNAME',
					title : '销售员',
					width : 100,
					fixed: true,
					align : 'center',
					halign :'center'
				},{
					field : 'SELBJ',
					title : '选择',
					width : 200,
					hidden : true
				},{
					field:'SELECTSALE',
					checkbox:true
				}
				] ],
				onLoadSuccess:function(data){
					check = false;
					if(data){
		    	   			$.each(data.rows, function(index, item){
		    	  	 		if(item.SELBJ=='1'){
		    	   				$('#salemant').datagrid('checkRow', index);
		    	  	 		}else $('#salemant').datagrid('uncheckRow', index);
		    	   			});
		    	   			}
		    	   		check=true;
				},
				onCheck:function (rowIndex, rowData){
	   				if(check){
	   					writesaleman(rowData.EPID,'1');
	   				}
		     	   	},
		     	 	onUncheck:function (rowIndex, rowData){
		     	 		if(check){
		     	 			writesaleman(rowData.EPID,'0');
		     	 		}
		    	   	},
		    	   	onCheckAll:function (rows){//要改
	   					if(check){
	   						//var counts = $('#allcolort').datagrid('getSelections').length;
	   						//var colorid = new Array(counts);
	   						//var value = new Array(counts);
	   					alerttext('全选功能未启用');
	   					$('#salemant').datagrid('reload');
						}
		     	   },
		     	 toolbar:"#salemansbtn"
			});
	salemandatat = false;
	}
	getsalemanlist('1',houseid,noteno);
	$('#salemand').dialog('open');
}
	function getsalemanlist(page,houseid,noteno){
		alertmsg(6);
		var houseid = $("#onlyhousesaleman").prop("checked")?houseid: "-1";
		var findbox = $("#salemanfindbox").val().toUpperCase();
		$.ajax({ 
           	type: "POST",   //访问WebService使用Post方式请求 
            url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
            data: {
            	erpser: "waresalemanlist",
            	houseid: houseid,
            	noteno: noteno,
            	findbox: findbox,
            	rows: pagecount,
            	page: page
            },         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
            dataType: 'json', 
            success: function(data) {     //回调函数，result，返回值 
            	try{if(valideData(data)){
					$('#salemant').datagrid('loadData', data);
					$('#salemanpp').setPage({
				        pageCount:Math.ceil(Number(data.total)/ pagecount),
				        current:Number(page)
					 });
            	}
            	}catch(err){
					console.error(err.message);
				}
            }                 
            });
	}
function writesaleman(epid,value,clearbj){
var id = $('#uid').val();
var noteno = $('#unoteno').val();
alertmsg(2);
$.ajax({ 
   	type: "POST",   //访问WebService使用Post方式请求 
    url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
    data: {erpser: "writewaresaleman",id:id,noteno:noteno,epid:epid,value:value,clearall:(clearbj?clearbj:0)},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
    dataType: 'json', 
    success: function(data) {     //回调函数，result，返回值 
    	try{if(valideData(data)){
    		getwaresalemanname(id, noteno); 
    	}
    	}catch(err){
			console.error(err.message);
		}
    }                 
    });
}
</script>