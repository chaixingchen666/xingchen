<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Modal -->
<div id="chcodemodal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
   <h3 id="myModalLabel">
   修改密码
<span class="msgbox"></span>
   </h3>
  </div>
  <div class="modal-body">
<div class="form-horizontal">
 <div class="control-group">
    <label class="control-label">原始密码</label>
   <div class="controls">
    <input type="password" id="oldpsw" name="oldpsw" placeholder="--输入--" maxlength="10">
   </div></div>
    <div class="control-group">
       <label class="control-label" style="letter-spacing: 1px;">新&nbsp;密&nbsp;码</label>
   <div class="controls">
    <input type="password" id="newpsw" name="newpsw"  placeholder="--输入--" maxlength="10">
   </div></div>
    <div class="control-group">
       <label class="control-label">确认密码</label>
   <div class="controls">
    <input type="password" id="conpsw" name="conpsw" placeholder="--输入--" maxlength="10">
   </div></div>
   <div style="padding-left: 103px;">温馨提示：密码位数不低于6位（包含数字和字母）！</div>
</div>  
  </div>
 <div class="modal-footer" >
   <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
   <input type="button" class="btn btn-primary" value="保&nbsp;&nbsp;存" onclick="changecode()">
 </div>
</div>
<script type="text/javascript">
//更改密码
function changecode(){
	var oldpsw = $('#oldpsw').val();
	var newpsw = $('#newpsw').val();
	var conpsw = $('#conpsw').val();
	if(oldpsw==""){
		alerttext("密码不能为空！");
		$('#oldpsw').focus();
	}else if(!(/^\w{6,10}$/.test(newpsw))){
		alerttext("新密码必须为6-10位字母与数字组成！")
		$('#newpsw').val("");
		$('#newpsw').focus();
	}else if(newpsw=="123456"){
		alerttext("密码过于简单，请重新输入!");
		$('#newpsw').val("");
		$('#newpsw').focus();
	}else if(newpsw!=conpsw){
		alerttext("两次输入密码不一致，请重新输入！");
		$('#newpsw').val("");
		$('#conpsw').val("");
		$('#newpsw').focus();
	}else{
		alertmsg(2);
		  $.ajax({ 
	           	type: "POST",   //访问WebService使用Post方式请求 
	            url: "updateepcode", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
	            data: {oldpsw:oldpsw,newpsw:newpsw},         //这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
	            dataType: 'json', 
	            success: function(data) {     //回调函数，result，返回值
	            	if(valideData(data)){
	            		window.location.href="loginservlet?doservlet=logoff";
	            		alert("密码修改成功，请重新登录！");
	            	}else{
	            		$('#oldpsw').val("");
	            		$('#newpsw').val("");
	        			$('#conpsw').val("");
	            		$('#oldpsw').focus();
	            	}
	            }                 
	            });
	}
}
</script>