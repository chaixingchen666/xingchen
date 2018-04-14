<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- 得到波次列表  -->
	<div id="wavef" title="波次帮助列表" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
	<!-- 搜索栏 -->
	<div id="wavesbtn" class="help-qstoolbar">
		<input class="btn2 hide" type="button" value="添加" onclick="addwaved()">
		<input type="text" class="help-qsipt" id="wavefindbox" data-enter="getwavedata('1')"  placeholder="搜索波次<回车搜索>"  data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getwavedata('1')">
		<input  type="hidden" id="waveser" >
	</div>
		<table id="wavet" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="wavepp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="wavefuzhi()" />
<!-- 				<input type="button" class="btn2" value="取消" onclick="$('#wavef').dialog('close')" />  -->
			</div>
		</div>
	</div>
	<!-- 添加波次窗 -->
	<div id="ad_waved" title="添加波次" style="width: 350px; height: 200px;"
		class="easyui-dialog" closed=true>
		<table class="table1" cellspacing="10" width="280">
		<tr>
		<td class="header" align="right" width="100">
		波次名称
		</td>
		<td align="left">
		<input class="wid160 hig25" type="text" placeholder="<输入>" name="ad_wave_wavename" id="ad_wave_wavename" width="175px" maxlength="20" /> 
		</td>
		</tr>
		<tr>
		<td class="header" align="right">
		开始日期
		</td>
		<td align="left">
		<input type="text" id="ad_wave_begindate" placeholder="<输入>" style="width: 162px;height:29px" class="easyui-datebox">
		</td>
		</tr>
		<tr>
		<td class="header" align="right">
		结束日期
		</td>
		<td align="left">
		<input type="text" id="ad_wave_enddate" placeholder="<输入>" style="width: 162px;height:29px" class="easyui-datebox">
		</td>
		</tr>
		</table>
		<div class="dialog-footer">
			<input type="button" class="btn1" value="保存" onclick="savewave()">
<!-- 			<input type="button" class="btn2" name="close" value="取消" onclick="$('#ad_waved').dialog('close')">  -->
		</div>
	</div>
<script type="text/javascript">
var wavedatagrid=true;
  //加载波次列表窗
function selectwave(waveser){
	  $("#waveser").val(waveser);
	  var findbox='';
// 	  if(waveser=='add'){					
// 			findbox = $('#adwavename').val();
// 		}else if(waveser=='update'){
// 			findbox = $('#uwavename').val();
// 		}else if(waveser=='analysis'){
// 			findbox = $('#adwavename').val();
// 		}else if(waveser=='autobarcode'){
// 			findbox = $('#iwavename').val();
// 		}else if(waveser=='load'){
// 			findbox = $('#iwavename').val();
// 		}
// 		else{
// 			findbox = $('#swavename').val();
// 		}
		$('#wavefindbox').val(findbox);
		if(wavedatagrid){
			$('#wavepp').createPage({
		        current:1,
		        backFn:function(p){
		        	getwavedata(p.toString());
		        }
			 });
			$('#wavet').datagrid({
				idField: 'WAVEID',
				height: 350,
				fitColumns: true,
				striped: true, //隔行变色
				singleSelect: true,
				sort: "WAVEID",
				nowrap: false,
				order: "asc",
				columns: [
					[{
						field: 'WAVEID',
						title: 'ID',
						width: 200,
						hidden: true
					}, {
						field: 'CHECK',
						checkbox: true,
						hidden: true
					}, {
						field: 'ROWNUMBER',
						title: '序号',
						width: 50,
						fixed: true,
						align: 'center',
						halign: 'center',
						formatter: function(value, row, index) {
							var val = Math.ceil(Number(value) / pagecount) - 1;
							return val * pagecount + Number(index) + 1;
						}
					}, {
						field: 'WAVENAME',
						title: '波次',
						width: 100,
						sortable: true,
						fixed: true,
						align: 'center',
						halign: 'center'
					}, {
						field: 'BEGINDATE',
						title: '开始日期',
						width: 80,
						sortable: true,
						fixed: true,
						hidden: true,
						align: 'center',
						halign: 'center'
					}, {
						field: 'ENDDATE',
						title: '结束日期',
						width: 100,
						sortable: true,
						fixed: true,
						hidden: true,
						align: 'center',
						halign: 'center'
					}, {
						field: 'BEGINDATESTR',
						title: '开始日期',
						width: 80,
						sortable: true,
						fixed: true,
						align: 'center',
						halign: 'center'
					}, {
						field: 'ENDDATESTR',
						title: '结束日期',
						width: 100,
						sortable: true,
						fixed: true,
						align: 'center',
						halign: 'center'
					}]
				],
				onClickRow: function(index, row) {
					$('#uindex').val(index);
				},
				onSortColumn: function(sort, order) {
					if (sort == "BEGINDATESTR")
						sort = "BEGINDATE";
					if (sort == "ENDDATESTR")
						sort = "ENDDATE";
					$('#gg').datagrid('options').sort = sort;
					$('#gg').datagrid('options').order = order;
					$('#pp').refreshPage();
				},
				onDblClickRow: function(rowIndex, rowData) {
					wavefuzhi();
				},
				pageSize: 20,
				toolbar: '#wavesbtn'
			}).datagrid("keyCtr","wavefuzhi()");
	}
		getwavedata('1');//加载数据
		$('#wavef').dialog('open');
		$('#wavefindbox').focus();
  }
  //双击、确定赋值（波次）
   function wavefuzhi(){
	  var rowData = $('#wavet').datagrid('getSelected');
	  var waveser= $('#waveser').val();
	  if(rowData){
	   if(waveser=='add'){								
			$('#awavename').val(rowData.WAVENAME);
			$('#awaveid').val(rowData.WAVEID);
			$('#awavename').focus();
		}else if(waveser=='update'){
			$('#uwavename').val(rowData.WAVENAME);
			$('#uwaveid').val(rowData.WAVEID);
			$('#uwavename').focus();
		}else if(waveser=='analysis'){
			$('#adwavename').val(rowData.WAVENAME);
			$('#adwaveid').val(rowData.WAVEID);
			$('#adwavename').focus();
		}else if(waveser=='autobarcode'){
			$('#iwavename').val(rowData.WAVENAME);
			$('#iwaveid').val(rowData.WAVEID);
			$('#iwavename').focus();
		}else if(waveser=='load'){
			$('#lwavename').val(rowData.WAVENAME);
			$('#lwaveid').val(rowData.WAVEID);
			$('#lwavename').focus();
		}
		else{
			$('#swavename').val(rowData.WAVENAME);
			$('#swaveid').val(rowData.WAVEID);
			$('#swavename').focus();
		}
		$('#wavef').dialog('close');
	  }else{
		  alert('请选择');
	  }
  }
	//获取波次列表
	//获取波次数据
function getwavedata(page) {
	var findbox = $('#wavefindbox').val();
	var sort = $('#wavet').datagrid('options').sort;
	var order = $('#wavet').datagrid('options').order;
	alertmsg(6);
	$.ajax({
		type: "POST", //访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "warewavelist",
			rows: pagecount,
			findbox: findbox,
			order: order,
			sort: sort,
			fieldlist: "wavename,waveid,begindate,enddate",
			page: page
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				$("#wavepp").setPage({
					pageCount: Math.ceil(data.total / pagecount),
					current: Number(page)
				});
				$('#wavet').datagrid('loadData', data);
				$('body').focus();
			}
		}
	});
}
	//添加框
	function addwaved() {
		$('#ad_waved').dialog('open');
		$('#ad_wave_wavename').focus();
	}
	//添加波次
	function savewave() {
	var wavename = $('#ad_wave_wavename').val();
	if (wavename == "" || wavename == "null" || wavename == "undefined") {
		alerttext("波次名不能为空");
	} else {
		alertmsg(2);
		var begindate = $('#ad_wave_begindate').datebox('getValue');
		var enddate = $('#ad_wave_enddate').datebox('getValue');
		if (begindate.length == 0) {
			alerttext('请选择开始日期');
			return;
		}
		if (enddate.length == 0) {
			alerttext('请选择结束日期');
			return;
		}
		var flyangser = "addwarewave";
		$.ajax({
			type: "POST", //访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: flyangser,
				begindate: begindate,
				enddate: enddate,
				wavename: wavename
			}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值
				if (valideData(data)) {
					$('#wavet').datagrid('insertRow', {
						index: 0,
						row: {
							ROWNUMBER: 1,
							WAVEID: data.msg,
							BEGINDATE: begindate,
							ENDDATE: enddate,
							WAVENAME: wavename
						}
					});
					$('#ad_wave_wavename').val("");
					//getwavedata('1');
					$('#ad_wave_wavename').focus();
					$('#ad_waved').dialog('close');
					$('#wavet').datagrid('refresh');
				}
			}
		});
	}
}
    </script>
