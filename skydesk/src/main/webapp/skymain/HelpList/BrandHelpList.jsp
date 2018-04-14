<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- 得到品牌列表  -->
	<div id="brandf" title="品牌帮助列表" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
	<!-- 搜索栏 -->
	<div id="brandsbtn" class="help-qstoolbar">
		<input class="btn2" type="button" value="添加" onclick="addbrandd()">
		<input type="text" class="help-qsipt" id="brandfindbox" data-enter="getbranddata('1')"  placeholder="搜索品牌<回车搜索>"  data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getbranddata('1')">
		<input  type="hidden" id="brandser" >
	</div>
		<table id="brandt" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="brandpp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="brandfuzhi()" />
<!-- 				<input type="button" class="btn2" value="取消" onclick="$('#brandf').dialog('close')" />  -->
			</div>
		</div>
	</div>
	<!-- 添加品牌窗 -->
	<div id="ad_brandd" title="添加品牌" style="width: 350px; height: 200px;"
		class="easyui-dialog" closed=true>
		<div style="margin-top: 40px; text-align: center;">
			<label class="theader skyrequied">品牌名称&nbsp;&nbsp;&nbsp;</label> <input title="addbrand();"
				style="height: 25px" type="text" placeholder="<输入>"
				id="abrandname" name="abrandname" width="175px" maxlength="20" />
		</div>
		<div class="dialog-footer" style="text-align: center;">
			<input id="add" class="btn1" type="button" name="save" style="width: 150px;" type="button" value="保存并继续添加" onclick="addbrand();">
		</div>
	</div>
<script type="text/javascript">
var branddatagrid=true;
  //加载品牌列表窗
function selectbrand(brandser){
	  $("#brandser").val(brandser);
	  var findbox='';
// 	  if(brandser=='add'){								
// 			findbox = $('#adbrandname').val();
// 		}else if(brandser=='update'){
// 			findbox = $('#ubrandname').val();
// 		}else if(brandser=='analysis'){
// 			findbox = $('#adbrandname').val();
// 		}else if(brandser=='autobarcode'){
// 			findbox = $('#ibrandname').val();
// 		}else if(brandser=='load'){
// 			findbox = $('#ibrandname').val();
// 		}
// 		else{
// 			findbox = $('#sbrandname').val();
// 		}
		$('#brandfindbox').val(findbox);
		if(!$('#brandt').data().datagrid){
			$('#brandpp').createPage({
		        current:1,
		        backFn:function(p){
		        	getbranddata(p.toString());
		        }
			 });
			$('#brandt').datagrid(
					{
						height:350,
						idField : 'brandname',
						fitColumns : true,
						striped : true, //隔行变色
						singleSelect : true,
						pageSize:20,
						columns : [ [ {
							field : 'BRANDID',
							title : 'ID',
							width : 200,
							hidden : true
						},{
							field : 'ROWNUMBER',
							title : '序号',
							width : 50,
							fixed:true,
							align:'center',
							halign:'center',
							formatter:function(value,row,index){
								var num = Number(value);
								if(!isNaN(num)) return num;
								return "";
							}
						}, {
							field : 'BRANDNAME',
							title : '品牌',
							width : 200
						} ] ],
						onDblClickRow:function(rowIndex, rowData){
							brandfuzhi();
						},
					toolbar:'#brandsbtn'
					}).datagrid("keyCtr","brandfuzhi()");
	}
		getbranddata('1');//加载数据
		$('#brandf').dialog('open');
		$('#brandfindbox').focus();
  }
  //双击、确定赋值（品牌）
   function brandfuzhi(){
	  var rowData = $('#brandt').datagrid('getSelected');
	  var brandser= $('#brandser').val();
	  if(rowData){
	   if(brandser=='add'){								
			$('#adbrandname').val(rowData.BRANDNAME);
			$('#adbrandid').val(rowData.BRANDID);
			$('#adbrandname').focus();
		}else if(brandser=='update'){
			$('#ubrandname').val(rowData.BRANDNAME);
			$('#ubrandid').val(rowData.BRANDID);
			$('#ubrandname').focus();
		}else if(brandser=='analysis'){
			$('#adbrandname').val(rowData.BRANDNAME);
			$('#adbrandid').val(rowData.BRANDID);
			$('#adbrandname').focus();
		}else if(brandser=='autobarcode'){
			$('#ibrandname').val(rowData.BRANDNAME);
			$('#ibrandid').val(rowData.BRANDID);
			$('#ibrandname').focus();
		}else if(brandser=='load'){
			$('#lbrandname').val(rowData.BRANDNAME);
			$('#lbrandid').val(rowData.BRANDID);
			$('#lbrandname').focus();
		}
		else{
			$('#sbrandname').val(rowData.BRANDNAME);
			$('#sbrandid').val(rowData.BRANDID);
			$('#sbrandname').focus();
		}
		$('#brandf').dialog('close');
	  }else{
		  alert('请选择');
	  }
  }
	//获取品牌列表
	function getbranddata(page) { //定义回调函数#')
	var findbox = $('#brandfindbox').val();
	findbox = findbox==undefined?"":findbox;
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "brandhelplist",
			findbox: findbox,
			rows: pagecount,
			fieldlist: "*",
			page: page
		},
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				$('#brandpp').setPage({
			        pageCount:Math.ceil(Number(data.total)/ pagecount),
			        current:Number(page)
				 });
				$('#brandt').datagrid('loadData', data);
				var len = $('#brandt').datagrid('getRows').length;
				if(len>0){
					$('#brandt').datagrid("selectRow",0);
				}
					$('#brandfindbox').focus();
		  	}
			}
		});
		
	} 
	//添加框
	function addbrandd() {
		$('#ad_brandd').dialog('open');
		$('#abrandname').focus();
	}
	//添加品牌
	function addbrand() {
		var brandname = $('#abrandname').val();
		if (brandname == "" || brandname == "null" || brandname == "undefined") {
			alerttext("品牌名不能为空");
		} else {
			alertmsg(2);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser : "addbrand",
					brandname : brandname
				}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType : 'json',
				success : function(data) { //回调函数，result，返回值
					try{
						if(valideData(data)){
							getbranddata('1');
						}
					}catch(err){
						console.error(err.message);
					}
				}
			});
		}
	}
    </script>
