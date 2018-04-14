package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-10 09:16:52
//*************************************
public class Sysfirstprog1 implements java.io.Serializable {
	private Long Progid;
	private Long Epid;
	private Long Orderid;
	private Integer Pageid;
	private Date Lastdate;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setProgid(Long progid) {
		this.Progid = progid;
	}

	public Long getProgid() {
		return Progid;
	}

	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public Long getEpid() {
		return Epid;
	}

	public void setOrderid(Long orderid) {
		this.Orderid = orderid;
	}

	public Long getOrderid() {
		return Orderid;
	}

	public void setPageid(Integer pageid) {
		this.Pageid = pageid;
	}

	public Integer getPageid() {
		return Pageid;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }

	// 删除记录
	public int Delete() {
		// StringBuilder strSql = new StringBuilder();
		// strSql.append("select count(*) as num from (");
		// // strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		// strSql.append(" update sysfirstprog1 set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// // strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// }
		return 1;
	}

	// 增加记录 ok
	public int Append(JSONObject jsonObject) {
		if (!jsonObject.has("proglist")) {
			errmess = "程序参数组无效！";
			return 0;
		}
		// "proglist":[{"progid":10,"value":1},{"progid":12,"value":0},{"progid":13,"value":1}]
		JSONArray jsonArray = jsonObject.getJSONArray("proglist");

		String qry;
		/// long progid;
		int value;
		int orderid;
		qry = " declare";
		qry += "\n   v_count number;";
		qry += "\n   v_levelid number;";
		qry += "\n   v_epno varchar2(20);";
		qry += "\n   v_epid number;";
		qry += "\n   v_menudate date;";
		qry += "\n begin";
		qry += "\n   v_epid:=" + Epid + "; ";
		qry += "\n   v_menudate:=sysdate; ";
		// if (sort == 1)
		qry += "\n   delete from sysfirstprog1 where epid=" + Epid + " and pageid=" + Pageid + " ;";
		orderid = 1;
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			Progid = Long.parseLong(jsonObject2.getString("progid"));
			value = Integer.parseInt(jsonObject2.getString("value"));
			qry += "\n   delete from sysfirstprog1 where epid=" + Epid + " and progid=" + Progid + " ;";
			if (value == 1) {
				qry += "\n   insert into sysfirstprog1 (epid,progid,orderid,lastdate,pageid) ";
				qry += "\n       values (" + Epid + "," + Progid + "," + orderid + ",sysdate," + Pageid + ");";
				orderid += 1;
			}
		}
		if (Pageid > 0) // 如果不是首页，且没有选定功能，则自动载入默认功能
		{
			qry += "\n   select count(*) into v_count from sysfirstprog1 where epid=" + Epid + " and pageid=" + Pageid + " and rownum=1; ";
			qry += "\n   if v_count=0 then";
			qry += "\n      select levelid,epno into v_levelid,v_epno from employe where epid=" + Epid + "; ";
			qry += "\n      insert into sysfirstprog1 (epid,progid,orderid,lastdate,pageid) ";
			qry += "\n      select " + Epid + ",a.progid,rownum,sysdate," + Pageid + " from progrole a where a.fs=1 and a.levelid=v_levelid  ";
			// 如果是功能页，只载入该功能的默认程序
			qry += "\n      and exists (select 1 from sysprog a1 where a1.progid=a.progid and a1.noused=1 and a1.groupid=" + Pageid + "); ";
			qry += "\n   end if;";

		}
		qry += "\n   update employe set menudate=v_menudate where epid=" + Epid + ";";
		qry += "\n   select to_char(v_menudate,'yyyy-mm-dd hh24:mi:ss') into :menudate from dual; ";
		qry += "\n end;";

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("menudate", new ProdParam(Types.VARCHAR));
		// int ret = DbHelperSQL.ExecuteProc(qry, param);
		// id = Long.parseLong(param.get("id").getParamvalue().toString());
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = param.get("menudate").getParamvalue().toString();
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update sysfirstprog1 set ");
		if (Progid != null) {
			strSql.append("progid=" + Progid + ",");
		}
		if (Epid != null) {
			strSql.append("epid=" + Epid + ",");
		}
		if (Orderid != null) {
			strSql.append("orderid=" + Orderid + ",");
		}
		if (Pageid != null) {
			strSql.append("pageid=" + Pageid + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM sysfirstprog1 ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM sysfirstprog1 ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public Table GetTable(int hytag, boolean syt,int vertag,String devicetype) {

		//sysprog->csxxx
		//XXXXXXXXXXX
		//||||    |||__10迷你
		//||||    ||___9批发
		//||||    |____8零售
		//||||_________4PC
		//|||__________3IPAD
		//||___________2苹果
		//|____________1安卓

		String qry = " select a.progid,b.progname,b.grpid as groupid,b.indexid,b.ordid,substr(b.grpname,2,20) as grpname,b.grpname as grpname0,'1' as selbj ";
		// qry += " ,case b.groupid when 1 then '销售' when 2 then '仓库' when 3 then '账务' when 4 then '分析' when 5 then '基础' else '***' end groupname";
		qry += "\n ,case b.grpid when 1 then '采购' when 2 then '销售' when 3 then '仓库' when 4 then '基础' else '***' end groupname";
		qry += "\n from sysfirstprog1 a";
		qry += "\n left outer join sysprog b on a.progid=b.progid";
		qry += "\n where b.noused=0 and a.epid=" + Epid + " and a.pageid=" + Pageid;
		if (!syt)// 未启用收银台1207=收银台
			qry += " and a.progid<>1207";
		qry += "\n and exists (select 1 from employe a1 join roleprog b1 on a1.levelid=b1.levelid where a1.epid=" + Epid + " and b1.fs=0 and a.progid=b1.progid) ";
		// sysprog->htag:程序属性0=通用(蓝窗),1=云壹，10=上下衣
		// sysprog->ptag:运行设备 0=手机+pc端+ipad，1=pc端专用,2=手机，3=ipad,4=手机+pc,5=手机+ipad，6=pc+ipad
//		qry += " and (b.ptag=0 or b.ptag=2 or b.ptag=4 or b.ptag=5) and b.htag<10 ";
//
//		// qry += " and b.grpid=" + pageid;
//		if (hytag == 0)
//			qry += " and (b.tag=0 or b.tag=2)";
//		else if (hytag == 1)
//			qry += " and (b.tag=1 or b.tag=2)";
		qry += "\n and b.htag<10 ";
		qry += "\n and b.grpid=" + Pageid;
		if (vertag == 0)
			qry += " and substr(b.csxxx,8,1)='1' ";
		else if (vertag == 1)
			qry += " and substr(b.csxxx,9,1)='1' ";
		else if (vertag == 2)
			qry += " and (substr(b.csxxx,8,1)='1' or  substr(b.csxxx,9,1)='1')";
		else if (vertag ==3)
			qry += " and substr(b.csxxx,10,1)='1' ";

		if (devicetype.equals("APH"))
			qry += " and substr(b.csxxx,1,1)='1' ";
		else if (devicetype.equals("IPH"))
			qry += " and substr(b.csxxx,2,1)='1' ";
		else if (devicetype.equals("IPD"))
			qry += " and substr(b.csxxx,3,1)='1' ";
		else if (devicetype.equals("WIN"))
			qry += " and substr(b.csxxx,4,1)='1' ";
		
		// if (pageid > 0)
		// qry += " and b.groupid=" + pageid;
		qry += "\n union all";
		qry += "\n select b.progid,b.progname,b.grpid as groupid,b.indexid,b.ordid,substr(b.grpname,2,20) as grpname,b.grpname as grpname0,'0' as selbj";
		// qry += " ,case b.groupid when 1 then '销售' when 2 then '仓库' when 3 then '账务' when 4 then '分析' when 5 then '基础' else '***' end groupname";
		qry += "\n ,case b.grpid when 1 then '采购' when 2 then '销售' when 3 then '仓库' when 4 then '基础' else '***' end groupname";
		qry += "\n from sysprog b ";
		qry += "\n where b.noused=0 and not exists (select 1 from sysfirstprog1 a where a.progid=b.progid and a.epid=" + Epid + " and a.pageid=" + Pageid + " )";
		if (!syt)// 未启用收银台1207=收银台
			qry += " and b.progid<>1207";
		// sysprog->htag:程序属性0=通用(蓝窗),1=云壹，10=上下衣,11=上下衣供应商
		// sysprog->ptag:运行设备 0=手机+pc端+ipad，1=pc端专用,2=手机，3=ipad,4=手机+pc,5=手机+ipad，6=pc+ipad
		qry += "\n and b.htag<10 ";

		//		qry += " and (b.ptag=0 or b.ptag=2 or b.ptag=4 or b.ptag=5) and b.htag<10 ";
//		if (hytag == 0)
//			qry += " and (b.tag=0 or b.tag=2)";
//		else if (hytag == 1)
//			qry += " and (b.tag=1 or b.tag=2)";
		// qry += " and b.groupid=" + pageid;
		qry += "\n and b.grpid=" + Pageid;
		
		if (vertag == 0)
			qry += " and substr(b.csxxx,8,1)='1' ";
		else if (vertag == 1)
			qry += " and substr(b.csxxx,9,1)='1' ";
		else if (vertag == 2)
			qry += " and (substr(b.csxxx,8,1)='1' or  substr(b.csxxx,9,1)='1')";
		else if (vertag ==3)
			qry += " and substr(b.csxxx,10,1)='1' ";

		if (devicetype.equals("APH"))
			qry += " and substr(b.csxxx,1,1)='1' ";
		else if (devicetype.equals("IPH"))
			qry += " and substr(b.csxxx,2,1)='1' ";
		else if (devicetype.equals("IPD"))
			qry += " and substr(b.csxxx,3,1)='1' ";
		else if (devicetype.equals("WIN"))
			qry += " and substr(b.csxxx,4,1)='1' ";
		

		qry += "\n and exists (select 1 from employe a1 join roleprog b1 on a1.levelid=b1.levelid where a1.epid=" + Epid + " and b1.fs=0 and b.progid=b1.progid) ";
		// qry += " order by grpname0,groupid ,indexid,progid";
		qry += "\n order by grpname0,ordid,progid";
		System.out.println(qry);
		return DbHelperSQL.GetTable(qry);
	}

	// 上下衣
	public Table GetTable(int hytag, int gysbj) {

		String qry = " select a.progid,b.progname,b.grpid as groupid,b.indexid,b.ordid,substr(b.grpname,2,20) as grpname,b.grpname as grpname0,'1' as selbj ";
		// qry += " ,case b.groupid when 1 then '销售' when 2 then '仓库' when 3 then '账务' when 4 then '分析' when 5 then '基础' else '***' end groupname";
		qry += "\n ,case b.grpid when 1 then '采购' when 2 then '销售' when 3 then '仓库' when 4 then '基础' else '***' end groupname";
		qry += "\n from sysfirstprog1 a";
		qry += "\n left outer join sysprog b on a.progid=b.progid";
		qry += "\n where b.noused=0 and a.epid=" + Epid + " and a.pageid=" + Pageid;
		qry += "\n and exists (select 1 from employe a1 join roleprog b1 on a1.levelid=b1.levelid where a1.epid=" + Epid + " and b1.fs=0 and a.progid=b1.progid) ";
		// sysprog->htag:程序属性0=通用(蓝窗),1=云壹，10=上下衣
		// sysprog->ptag:运行设备 0=手机+pc端+ipad，1=pc端专用,2=手机，3=ipad,4=手机+pc,5=手机+ipad，6=pc+ipad
		qry += "\n and (b.ptag=0 or b.ptag=2 or b.ptag=4 or b.ptag=5) ";// and b.htag<10 ";
		if (gysbj == 0)
			qry += "\n and (b.htag<10 or b.htag=10)";
		else if (gysbj == 1)
			qry += "\n and (b.htag<10 or b.htag=11)";

		// qry += " and b.grpid=" + pageid;
		if (hytag == 0)
			qry += "\n and (b.tag=0 or b.tag=2)";
		else if (hytag == 1)
			qry += "\n and (b.tag=1 or b.tag=2)";
		// if (pageid > 0)
		// qry += " and b.groupid=" + pageid;
		qry += "\n and b.grpid=" + Pageid;
		qry += "\n union all";
		qry += "\n select b.progid,b.progname,b.grpid as groupid,b.indexid,b.ordid,substr(b.grpname,2,20) as grpname,b.grpname as grpname0,'0' as selbj";
		// qry += " ,case b.groupid when 1 then '销售' when 2 then '仓库' when 3 then '账务' when 4 then '分析' when 5 then '基础' else '***' end groupname";
		qry += "\n ,case b.grpid when 1 then '采购' when 2 then '销售' when 3 then '仓库' when 4 then '基础' else '***' end groupname";
		qry += "\n from sysprog b ";
		qry += "\n where b.noused=0 and not exists (select 1 from sysfirstprog1 a where a.progid=b.progid and a.epid=" + Epid + " and a.pageid=" + Pageid + " )";
		// sysprog->htag:程序属性0=通用(蓝窗),1=云壹，10=上下衣,11=上下衣供应商
		// sysprog->ptag:运行设备 0=所有通用(手机，pc端)，1=pc端专用,2=手机专用，3=ipad专用
		// sysprog->ptag:运行设备 0=手机+pc端+ipad，1=pc端专用,2=手机，3=ipad,4=手机+pc,5=手机+ipad，6=pc+ipad
		// qry += " and (b.ptag=0 or b.ptag=2)";// and b.htag<10 ";
		qry += "\n and (b.ptag=0 or b.ptag=2 or b.ptag=4 or b.ptag=5) ";
		if (gysbj == 0)
			qry += "\n and (b.htag<10 or b.htag=10)";
		else if (gysbj == 1)
			qry += "\n and (b.htag<10 or b.htag=11)";

		if (hytag == 0)
			qry += "\n and (b.tag=0 or b.tag=2)";
		else if (hytag == 1)
			qry += " and (b.tag=1 or b.tag=2)";
		// qry += " and b.groupid=" + pageid;
		qry += "\n and b.grpid=" + Pageid;

		qry += "\n and exists (select 1 from employe a1 join roleprog b1 on a1.levelid=b1.levelid where a1.epid=" + Epid + " and b1.fs=0 and b.progid=b1.progid) ";
		// qry += " order by grpname0,groupid ,indexid,progid";
		qry += "\n order by grpname0,ordid,progid";

		//		System.out.println(qry);
		return DbHelperSQL.GetTable(qry);
	}

	// 获取所有数据
	public Table GetTable(String strWhere, String fieldlist, String order, int hytag,int vertag,String devicetype) {

		StringBuilder strSql = new StringBuilder();

		if (Pageid > 0) // 不是首页要判断默认值
		{
			strSql.append("declare ");
			strSql.append("   v_count number(10);  ");
			strSql.append("   v_epid number(10);  ");
			strSql.append("   v_epno varchar2(20);  ");
			strSql.append("   v_levelid number(10);  ");
			strSql.append("   v_glevelid number(10);  ");
			strSql.append(" begin ");
			// qry += " "+userid+":=" + userid + "; ";\
			// 如果默认程序为空，显示默认的内容
			strSql.append("   select count(*) into v_count from sysfirstprog1 a join sysprog b  on a.progid=b.progid where a.epid=" + Epid + " and b.grpid=" + Pageid + " and a.pageid=" + Pageid + " and rownum=1; ");
			strSql.append("   if v_count=0 then");
			strSql.append("      select a.levelid,a.epno,b.glevelid into v_levelid,v_epno,v_glevelid from employe  a join userrole b on a.levelid=b.levelid where a.epid=" + Epid + "; ");
			strSql.append("      insert into sysfirstprog1 (epid,progid,orderid,lastdate,pageid) ");
			strSql.append("      select " + Epid + ",a.progid,rownum,sysdate," + Pageid + " from roleprog a where a.fs=0 and a.defbj=1 and a.levelid=v_glevelid");
			strSql.append("      and exists (select 1 from sysprog a1 where a1.progid=a.progid and a1.noused=0 and a1.grpid=" + Pageid);
			strSql.append("      and exists (select 1 from employe a1 join roleprog b1 on a1.levelid=b1.levelid where a1.epid=" + Epid + " and b1.fs=0 and a.progid=b1.progid) ");

			// sysprog->htag:程序属性0=通用(蓝窗),1=云壹，10=上下衣
			// sysprog->ptag:运行设备 0=手机+pc端+ipad，1=pc端专用,2=手机，3=ipad,4=手机+pc,5=手机+ipad，6=pc+ipad
			strSql.append("      and (a1.ptag=0 or a1.ptag=2 or a1.ptag=4 or a1.ptag=5) and a1.htag<10 ");

			if (hytag == 0)
				strSql.append(" and (a1.tag=0 or a1.tag=2)");
			else if (hytag == 1)
				strSql.append(" and (a1.tag=1 or a1.tag=2)");
			
			if (vertag == 0)
				strSql.append( " and substr(a1.csxxx,8,1)='1' ");
			else if (vertag == 1)
				strSql.append( " and substr(a1.csxxx,9,1)='1' ");
			else if (vertag == 2)
				strSql.append( " and substr(a1.csxxx,10,1)='1' ");

			if (devicetype.equals("APH"))
				strSql.append(" and substr(a1.csxxx,1,1)='1' ");
			else if (devicetype.equals("IPH"))
				strSql.append( " and substr(a1.csxxx,2,1)='1' ");
			else if (devicetype.equals("IPD"))
				strSql.append(" and substr(a1.csxxx,3,1)='1' ");
			else if (devicetype.equals("WIN"))
				strSql.append( " and substr(a1.csxxx,4,1)='1' ");
			
			strSql.append(" );");
			// qry += " select " + userid + ",a.progid,rownum,sysdate," + pageid + " from progrole a where a.fs=1 and a.levelid=v_levelid ";
			// 如果是功能页，只载入该功能的默认程序
			// qry += " and exists (select 1 from sysprog a1 where a1.progid=a.progid and a1.noused=1 and a1.groupid=" + pageid + "); ";
			strSql.append("   end if;");
			strSql.append("   commit;");
			strSql.append(" end ;");
			// WriteLogTXT("debug", "listprogpage1", qry);
			DbHelperSQL.ExecuteSql(strSql.toString());
		}
		strSql.setLength(0);
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM sysfirstprog1 a join sysprog b on a.progid=b.progid");
		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		if (!order.equals("")) {
			strSql.append("\n order by " + order);
		}
		// qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(strSql.toString());
	}

	// 获取所有数据
	public Table GetTable(String strWhere, String fieldlist, String order, int hytag, int gysbj) {

		StringBuilder strSql = new StringBuilder();

		if (Pageid > 0) // 不是首页要判断默认值
		{
			strSql.append("declare ");
			strSql.append("   v_count number(10);  ");
			strSql.append("   v_epid number(10);  ");
			strSql.append("   v_epno varchar2(20);  ");
			strSql.append("   v_levelid number(10);  ");
			strSql.append("   v_glevelid number(10);  ");
			strSql.append(" begin ");
			// qry += " "+userid+":=" + userid + "; ";\
			// 如果默认程序为空，显示默认的内容
			strSql.append("   select count(*) into v_count from sysfirstprog1 a join sysprog b  on a.progid=b.progid where a.epid=" + Epid + " and b.grpid=" + Pageid + " and a.pageid=" + Pageid + " and rownum=1; ");
			strSql.append("   if v_count=0 then");
			strSql.append("      select a.levelid,a.epno,b.glevelid into v_levelid,v_epno,v_glevelid from employe  a join userrole b on a.levelid=b.levelid where a.epid=" + Epid + "; ");
			strSql.append("      insert into sysfirstprog1 (epid,progid,orderid,lastdate,pageid) ");
			strSql.append("      select " + Epid + ",a.progid,rownum,sysdate," + Pageid + " from roleprog a where a.fs=0 and a.defbj=1 and a.levelid=v_glevelid");
			strSql.append("      and exists (select 1 from sysprog a1 where a1.progid=a.progid and a1.noused=0 and a1.grpid=" + Pageid);
			strSql.append("      and exists (select 1 from employe a1 join roleprog b1 on a1.levelid=b1.levelid where a1.epid=" + Epid + " and b1.fs=0 and a.progid=b1.progid) ");

			// sysprog->htag:程序属性0=通用(蓝窗),1=云壹，10=上下衣
			// sysprog->ptag:运行设备 0=手机+pc端+ipad，1=pc端专用,2=手机，3=ipad,4=手机+pc,5=手机+ipad，6=pc+ipad
			strSql.append("      and (a1.ptag=0 or a1.ptag=2 or a1.ptag=4 or a1.ptag=5) ");
			// gysbj:0=上下衣公司，1=上下衣供应商
			if (gysbj == 0)
				strSql.append(" and (a1.htag<10 or a1.htag=10)");
			else if (gysbj == 1)
				strSql.append(" and (a1.htag<10 or a1.htag=11)");

			if (hytag == 0)
				strSql.append(" and (a1.tag=0 or a1.tag=2)");
			else if (hytag == 1)
				strSql.append(" and (a1.tag=1 or a1.tag=2)");
			strSql.append(" );");
			// qry += " select " + userid + ",a.progid,rownum,sysdate," + pageid + " from progrole a where a.fs=1 and a.levelid=v_levelid ";
			// 如果是功能页，只载入该功能的默认程序
			// qry += " and exists (select 1 from sysprog a1 where a1.progid=a.progid and a1.noused=1 and a1.groupid=" + pageid + "); ";
			strSql.append("   end if;");
			strSql.append("   commit;");
			strSql.append(" end ;");
			// WriteLogTXT("debug", "listprogpage1", qry);
			DbHelperSQL.ExecuteSql(strSql.toString());
		}
		strSql.setLength(0);
		strSql.append("select " + fieldlist);
		strSql.append(" FROM sysfirstprog1 a join sysprog b on a.progid=b.progid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		if (!order.equals("")) {
			strSql.append(" order by " + order);
		}
		// qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(strSql.toString());
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from sysfirstprog1");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}
}