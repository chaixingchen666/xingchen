<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>404</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="images/icon.ico" rel="icon" type="image/x-icon">
<style>
html,body{
margin:0px;
height: 100%;
width: 100%
}
*{
margin: 0;padding: 0}
#error_404{text-align:center;background:url('images/404.jpg') no-repeat center center;margin:0 auto;width:100%;height:100%;font-family:"Microsoft YaHei","微软雅黑"}
.backbtn{border:none;background:#01b1aa;color:#fff;padding:10px 15px;font-size:24px;cursor:pointer}
.backbtn:hover{background:#ff7900}
#error_404 table{position:absolute;top:50%;left:50%;margin:-10px -196px}
td{font-size:24px}
</style>
<script type="text/javascript">
function backmain(){
	top.window.location.href = '/skydesk/main.jsp';
}
</script>
</head>
<body>
<div id="error_404">
   <table>
   <tr>
   <td>
   您要找的页面不存在……
   </td>
   <td>
   <input type="button" class="backbtn" value="返回主页" onclick="backmain()"/>
   </td>
   </tr>
   </table>
</div>
</body>
</html>
