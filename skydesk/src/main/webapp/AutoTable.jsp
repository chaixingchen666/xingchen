<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="http://win.skydispark.com/skyasset/css/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/bootstrap/bootstrap.js"></script>
<script type="text/javascript">
	function loaddata(data) {
		var data = {
			"total" : "2^20^5450",
			"rows" : [ {
				"ID" : "2423.00",
				"NOTENO" : "CG2015103000001",
				"ACCID" : "1",
				"NOTEDATE" : "2015-11-20 00:01:03",
				"PROVID" : "3",
				"HOUSEID" : "3",
				"NTID" : "0",
				"HANDNO" : "",
				"REMARK" : "",
				"OPERANT" : "吴明格",
				"CHECKMAN" : "",
				"STATETAG" : "0",
				"LASTDATE" : "2015-10-30 11:19:12",
				"TOTALAMT" : "7.00",
				"TOTALCURR" : "1550.00",
				"PROVNAME" : "杭州兰贝尔服装有限公司",
				"DISCOUNT" : "0.75",
				"PRICETYPE" : "0",
				"HOUSENAME" : "凯瑞商都",
				"ROWNUMBER" : "1.00"
			}, {
				"ID" : "2424.00",
				"NOTENO" : "CG2015103000002",
				"ACCID" : "1",
				"NOTEDATE" : "2015-11-20 00:01:03",
				"PROVID" : "1",
				"HOUSEID" : "3",
				"NTID" : "0",
				"HANDNO" : "",
				"REMARK" : "",
				"OPERANT" : "吴明格",
				"CHECKMAN" : "",
				"STATETAG" : "0",
				"LASTDATE" : "2015-10-30 12:09:52",
				"TOTALAMT" : "13.00",
				"TOTALCURR" : "3900.00",
				"PROVNAME" : "重庆树王服装厂",
				"DISCOUNT" : "0.8",
				"PRICETYPE" : "0",
				"HOUSENAME" : "凯瑞商都",
				"ROWNUMBER" : "2.00"
			} ]
		};
		var head = new Array("ID",
				"NOTENO",
				"ACCID",
				"NOTEDATE",
				"PROVID",
				"HOUSEID",
				"NTID",
				"HANDNO",
				"REMARK",
				"OPERANT",
				"CHECKMAN",
				"STATETAG",
				"LASTDATE",
				"TOTALAMT",
				"TOTALCURR",
				"PROVNAME" ,
				"DISCOUNT" ,
				"PRICETYPE" ,
				"HOUSENAME" ,
				"ROWNUMBER");
		var rows = data.rows;
		var html;
		var thead = document.createElement('thead');
		var thr = document.createElement('tr');
		for(var i in head){
			var th = document.createElement("th");
			th.innerHTML = head[i];
			thr.appendChild(th);
		}
		thead.appendChild(thr);
		var tbody = document.createElement("tbody");
		for(var i in rows){
			var tbr = document.createElement("tr");
			for(var j in rows[i]){
				var td = document.createElement("td");
				td.innerHTML = rows[i][j];
				tbr.appendChild(td);
			}
			tbody.appendChild(tbr);
		}
		$('#dg').append(thead).append(tbody);
	}
	$(function(){
		loaddata();
	})
	
</script>
</head>
<body>
<table class="table" id="dg"></table>
</body>
</html>