<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 添加颜色窗 -->
	<div id="acolord" title="增加颜色" style="width: 260px; height: 180px;"
		class="easyui-dialog" closed=true>
		<br>
		<div>
			<label>&nbsp;&nbsp;颜色名称&nbsp;&nbsp;&nbsp;</label>
			<input type="text" placeholder="---输入---" id="addcolorname" name="addcolorname" width="175px" /> 
		</div>
		<br>
		<div>
			<label>&nbsp;&nbsp;色&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码&nbsp;&nbsp;&nbsp;</label>
			<input type="text" placeholder="---输入---" id="addcolorno" name="addcolorno" width="175px" /> 
		</div>
		<br>
		<div style="text-align: center;">
			<input type="button" value="保存并继续添加" width="200px" onclick="addcolor()" >
		</div>
	</div>
<!-- 得到全部颜色列表 -->
<div id="colord" title="颜色列表 " style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
	<div id="colorsbtn" class="help-qstoolbar">
		<span><input id="addcolorbtn" type="button" class="btn2 hide" value="添加" onclick="$('#acolord').dialog('open')"></span>
		<span><input id="colorfindbox" class="help-qsipt" data-enter="getwarecolor(1)" ></input></span>
		<input class="btn2" type="button" value="搜索" onclick="getwarecolor(1)">
	</div>
	<input type="hidden" id="color_wareid" value="">
	<input type="hidden" id="colorser" value="">
	<table id="colort" style="overflow: hidden;"></table>
	<div class="dialog-footer">
			<div id="colorpp" class="tcdPageCode" style="float: left; padding-top: 0px;"></div>
			<div style="float: right;">
				 <input type="button" class="btn1" value="确定"  onclick="warecolorfuzhi()"/>
			</div>
		</div> 
</div>
<script type="text/javascript">
//加载颜色框和数据
var warecolort = true;
var check = false;
function selectwarecolor(wareid,colorser){
	$('#colorser').val(colorser);
	$('#color_wareid').val(wareid);
	if (pgid == "pg1101" || pgid == "pg1102" || pgid == "pg1103")
		$('#addcolorbtn').show();
	if(!$('#colort').data().datagrid){
		$('#colorpp').createPage({
			pageCount : 1,
			current: 1,
			backFn : function(p) {
				getwarecolor(p);
			}
		});
		$('#colort').datagrid(
				{
					height:350,
					idField : 'COLORID',
					fitColumns : true,
					striped : 	true, //隔行变色
					singleSelect :true,
					pageSize:10,
					checkOnSelect:true,
					columns : [ [ {
						field : 'COLORID',
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
					},{
						field : 'COLORNAME',
						title : '颜色',
						width : 100,
						fixed:true,
						align:'center',
						halign:'center'
					},{
						field : 'COLORNO',
						title : '色码',
						width : 80,
						fixed:true,
						align:'center',
						halign:'center'
					}
					]],
					onDblClickRow:function(rowIndex, rowData){
						warecolorfuzhi();
					},
					toolbar:'#colorsbtn'
				}).datagrid('keyCtr','warecolorfuzhi()');
		warecolort = false;
	}
	getwarecolor(1);
	$('#colord').dialog('open');
	$('#colorfindbox').focus();
	
}
  //双击、确定赋值
  function warecolorfuzhi(){
	 var colorser=$('#colorser').val();
	 var rowData = $('#colort').datagrid('getSelected');
	 if(rowData){
		if(colorser=='add'){
			$('#acolorname').val(rowData.COLORNAME);
			$('#acolorid').val(rowData.COLORID);
			$('#acolorname').focus();
		}else if(colorser=='analysis'||colorser=='search'){
			$('#acolorname,#scolorname').val(rowData.COLORNAME);
			$('#acolorid,#scolorid').val(rowData.COLORID);
			$('#acolorname').focus();
		}else if(colorser=='update'){
			$('#ucolorname').val(rowData.COLORNAME);
			$('#ucolorid').val(rowData.COLORID);
			$('#ucolorname').focus();
		}else if(colorser=='shopsale'){
			$('#colorname').val(rowData.COLORNAME);
			$('#colorid').val(rowData.COLORID);
			if($('#sizeid').val()=="")
				selsize('shopsale');
		}else{
			$('#wucolorname').val(rowData.COLORNAME);
			$('#wucolorid').val(rowData.COLORID);
			$('#wucolorname').focus();
		}
		//getwaresum(colorser);
		$('#colord').dialog('close');
	 }else{
		  alert('请选择');
	  }
  }
//获取商品id的颜色名
function getwarecolor(page) {
	check=false;
	$('#colort').datagrid('loadData', {total:0,rows:[]});
	var wareid = Number($('#color_wareid').val());
	var findbox = $('#colorfindbox').val();
	var erpser = "warecolorexistslist";
	if(wareid<=0)
		erpser = "colorlist";
	$.ajax({ 
       	type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
        data: {
        	erpser:erpser,
			findbox:findbox,
        	wareid:wareid,
        	fieldlist: "*",
        	sort: "colorname",
			order: "asc",
			noused: 0,
			rows: pagecount,
			page:page
		},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        dataType: 'json', 
        success: function(data) {     //回调函数，result，返回值 
        	try{if(valideData(data)){
	    			$('#colorpp').setPage({
						pageCount:Math.ceil(data.total/ pagecount),
						current:Number(page)
					});
				  $('#colort').datagrid('loadData', data);
	    			var len = $('#colort').datagrid('getRows').length;
					if(len>0){
						$('#colort').datagrid("selectRow",0);
					}
					$('#colorfindbox').focus();
			  }
        	}catch(err){
				console.error(err.message);
			}
        }                 
        });						
}
//双击、确定赋值(全部颜色)
function colorfuzhi(){
	var colorser=$('#colorser').val();
	if(colorser=='addware'){
		var arrs=$('#colort').datagrid('getSelected');
		var l = $('#colort').datagrid('getSelections').length;
		var colorname = new Array(l);
		var colorid = new Array(l);
		if(l!=0){
			for(var i=0;i<l;i++){
				colorname[i]=arrs[i].COLORNAME;
				colorid[i]=arrs[i].COLORID;
			}
			if(colorser=='addware'){
				$('#adcolorname').val(colorname.toString());
				$('#adcolorid').val(colorid.toString());
				$('#adcolorname').focus();
			}else if(colorser=='search'){
				$('#scolorname').val(colorname.toString());
				$('#scolorid').val(colorid.toString());
				$('#scolorname').focus();
			}else{
				$('#ucolorname').val(colorname.toString());
				$('#ucolorid').val(colorid.toString());
				$('#ucolorname').focus();
			}
		}else{
			if(colorser=='addware'){
				$('#acolorname').val("");
			}else{
				$('#ucolorname').val("");
			}
		}
		}
}
//添加颜色
function addcolor(){
	var colorname = $('#addcolorname').val();
	var colorno = $('#addcolorno').val();
	if(colorname==""||colorname=="null"||colorname=="undefined"){
		alert("颜色名不能为空");
	}else{
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "addcolor",
				colorname: colorname,
				colorno: colorno
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值
				if (valideData(data)) {
					 getwarecolor(1);
					 $('#addcolorname').val("");
            		 $('#addcolorno').val("");
            	}
            }                 
        });
	}
}

</script>
