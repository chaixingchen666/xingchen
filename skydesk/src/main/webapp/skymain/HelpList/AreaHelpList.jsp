<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- 得到区位列表  -->
	<div id="areaf" title="Dialog" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
	<!-- 搜索栏 -->
	<div id="areasbtn" class="help-qstoolbar">
		<input class="btn2" type="button" value="添加" onclick="addaread()">
		<input type="text" class="help-qsipt" id="areafindbox" data-enter="getareadata('1')"  placeholder="搜索区位<回车搜索>"  data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getareadata('1')">
		<input  type="hidden" id="areaser" >
	</div>
		<table id="areat" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="areapp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="areafuzhi()" />
<!-- 				<input type="button" class="btn2" value="取消" onclick="$('#areaf').dialog('close')" />  -->
			</div>
		</div>
	</div>
	<!-- 添加区位窗 -->
	<div id="ad_aread" title="添加区位" style="width: 350px; height: 200px;"
		class="easyui-dialog" closed=true>
		<div style="margin-top: 40px; text-align: center;">
			<label class="theader skyrequied">区位名称&nbsp;&nbsp;&nbsp;</label> <input title="addarea();"
				style="height: 25px" type="text" placeholder="<输入>"
				id="adareaname" name="adareaname" width="175px" maxlength="20" />
		</div>
		<div class="dialog-footer" style="text-align: center;">
			<input id="add" class="btn1" type="button" name="save" style="width: 150px;" type="button" value="保存并继续添加" onclick="addarea();">
		</div>
	</div>
<script type="text/javascript">
var areadatagrid=true;
  //加载区位列表窗
function selectarea(areaser){
	  $("#areaser").val(areaser);
	  var findbox='';
// 	  if(areaser=='add'){								
// 			findbox = $('#adareaname').val();
// 		}else if(areaser=='update'){
// 			findbox = $('#uareaname').val();
// 		}else if(areaser=='analysis'){
// 			findbox = $('#adareaname').val();
// 		}else if(areaser=='autobarcode'){
// 			findbox = $('#iareaname').val();
// 		}else if(areaser=='load'){
// 			findbox = $('#iareaname').val();
// 		}
// 		else{
// 			findbox = $('#sareaname').val();
// 		}
		$('#areafindbox').val(findbox);
		if(!$('#areat').data().datagrid){
			$('#areapp').createPage({
		        current:1,
		        backFn:function(p){
		        	getareadata(p.toString());
		        }
			 });
			$('#areat').datagrid(
					{
						height:350,
						idField : 'areaname',
						fitColumns : true,
						striped : true, //隔行变色
						singleSelect : true,
						pageSize:20,
						columns : [ [ {
							field : 'AREAID',
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
							field : 'AREANAME',
							title : '区位',
							width : 200
						} ] ],
						onDblClickRow:function(rowIndex, rowData){
							areafuzhi();
						},
						
					toolbar:'#areasbtn'
					}).datagrid("keyCtr","areafuzhi()");
			$('#areaf').dialog({
				modal:true,
				title:'区位帮助列表'
			});
	}
		getareadata('1');//加载数据
		$('#areaf').dialog('open');
		$('#areafindbox').focus();
  }
  //双击、确定赋值（区位）
   function areafuzhi(){
	  var rowData = $('#areat').datagrid('getSelected');
	  var areaser= $('#areaser').val();
	  if(rowData){
	   if(areaser=='add'){								
			$('#aareaname').val(rowData.AREANAME);
			$('#aareaid').val(rowData.AREAID);
			$('#aareaname').focus();
		}else if(areaser=='update'){
			$('#uareaname').val(rowData.AREANAME);
			$('#uareaid').val(rowData.AREAID);
			$('#uareaname').focus();
		}else if(areaser=='analysis'){
			$('#adareaname').val(rowData.AREANAME);
			$('#adareaid').val(rowData.AREAID);
			$('#adareaname').focus();
		}else if(areaser=='autobarcode'){
			$('#iareaname').val(rowData.AREANAME);
			$('#iareaid').val(rowData.AREAID);
			$('#iareaname').focus();
		}else if(areaser=='load'){
			$('#lareaname').val(rowData.AREANAME);
			$('#lareaid').val(rowData.AREAID);
			$('#lareaname').focus();
		}
		else{
			$('#sareaname').val(rowData.AREANAME);
			$('#sareaid').val(rowData.AREAID);
			$('#sareaname').focus();
		}
		$('#areaf').dialog('close');
	  }else{
		  alert('请选择');
	  }
  }
	//获取区位列表
	function getareadata(page) { //定义回调函数#')
	var findbox = $('#areafindbox').val();
	findbox = findbox==undefined?"":findbox;
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "arealist",
			findbox: findbox,
			noused: "0",
			rows: pagecount,
			fieldlist: "*",
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			try{
				if(valideData(data)){
				$('#areat').datagrid('loadData', data);
				$('#areapp').setPage({
			        pageCount:Math.ceil(Number(data.total)/ pagecount),
			        current:Number(page)
				 });
				var len = $('#areat').datagrid('getRows').length;
				if(len>0){
					$('#areat').datagrid("selectRow",0);
				}
					$('#areafindbox').focus();
		  }
			}catch(err){
				console.error(err.message);
			}
		}
	});
		
	} 
	//添加框
	function addaread() {
		$('#ad_aread').dialog({
			modal : true
		});
		$('#ad_aread').dialog('open');
		$('#adareaname').focus();
	}
	//添加区位
	function addarea() {
		var areaname = $('#adareaname').val();
		if (areaname == "" || areaname == "null" || areaname == "undefined") {
			alerttext("区位名不能为空");
		} else {
			alertmsg(2);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser : "addarea",
					areaname : areaname
				}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType : 'json',
				success : function(data) { //回调函数，result，返回值
					try{
						if(valideData(data)){
						getareadata('1'); 
						}
					}catch(err){
						console.error(err.message);
					}
				}
			});
		}
	}
    </script>
