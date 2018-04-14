<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 端口帮助列表 -->
<div id="selprintdia" title="选择端口"
		style="width: 300px; height: 140px; text-align: center;"
		class="easyui-dialog" closed="true">
		<table cellspacing="10" class="table1" width="260px";>
			<tr align="center">
				 <td width="67" class="header" align="right">打印端口</td>
	    		 <td width="100" align="right"><select id="prtid"  style="width:164px;height:25px">
                    </select></td>
			</tr>
			<tr>
				<td align="center" colspan="2"><input class="btn1" value="确定"
					type="button" onclick="selprints();" />
<!-- 					<input class="btn1" type="button" value="取消" onclick="$('#selprintdia').dialog('close')" /> -->
					</td>
			</tr>
		</table>
	</div>
<script type="text/javascript">
var printpgid = 0;
//弹出端口选择窗口
function selprint(setpgid){
	if(setpgid) printpgid = setpgid;
	getprintcs();
	$('#selprintdia').dialog('open');
}
//确定
function selprints(){
	var progid=pgid.replace("pg", "");
	if(printpgid!=0) progid = printpgid;
	var prtid=$("#prtid").val();//端口id
	if(prtid=='0'||prtid==''||prtid==null){
		alerttext("请选择端口号！")
	}else{
		$('#selprintdia').dialog('close');
		
		$.ajax({ 
	       	type: "POST",   //访问WebService使用Post方式请求 
	        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方 法名 
	        data: {
	        	erpser:"writeprintcs",
	        	prtid : prtid,
	        	progid : progid
	        	//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
	        },
	        dataType: 'json', 
	        success: function(data) {     //回调函数，result，返回值 
	        	try{if(valideData(data)){
	        		
	        	}
	        	}catch(err){
					console.error(err.message);
				}
	        }                 
	        });
	}
}
//获取端口
function getprintcs(){
	var progid=pgid.replace("pg", "");
	if(printpgid!=0) progid = printpgid;
	$.ajax({ 
       	type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
        data: {
        	erpser: "getprintcs1",
        	progid : progid
        	//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        },
        dataType: 'json', 
        success: function(data) {     //回调函数，result，返回值 
        	try{if(valideData(data)){
        		$("#prtid").empty();
        		 $("#prtid").append("<option value="+data.PRTID+" selected=\"selected\">"+data.PRTNAME+"</option>");
        		 var datalist=data.printslist;
        		 for(var i=0;i<datalist.length;i++){
        			 $("#prtid").append("<option value="+datalist[i].PRTID+">"+datalist[i].PRTNAME+"</option>");
        		 }
        	}
        	}catch(err){
				console.error(err.message);
			}
        }                 
        });
}
</script>
