package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

//*************************************
//  @author:sunhong  @date:2017-04-07 11:24:54
//*************************************
public class Sysprogsys implements java.io.Serializable {
	private Long Sysid;
	private String Sysname;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setSysid(Long sysid) {
		this.Sysid = sysid;
	}

	public Long getSysid() {
		return Sysid;
	}

	public void setSysname(String sysname) {
		this.Sysname = sysname;
	}

	public String getSysname() {
		return Sysname;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		StringBuilder strSql = new StringBuilder();
		// strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		// strSql.append(" update Sysprogsys set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// } else {
		// errmess = "删除成功！";
		return 1;
		// }
	}

	// 增加记录
	public int Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		if (Sysid != null) {
			strSql1.append("sysid,");
			strSql2.append(Sysid + ",");
		}
		if (Sysname != null) {
			strSql1.append("sysname,");
			strSql2.append("'" + Sysname + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=Sysprogsys_id.nextval; ");
		strSql.append("   insert into Sysprogsys (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("  select v_id into :id from dual; ");
		strSql.append(" end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		// id = Long.parseLong(param.get("id").getParamvalue().toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Sysprogsys set ");
		if (Sysid != null) {
			strSql.append("sysid=" + Sysid + ",");
		}
		if (Sysname != null) {
			strSql.append("sysname='" + Sysname + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Sysprogsys ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Sysprogsys ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(long userid) {
		String qry = " select a.sysid ,a.sysname";
		qry += " from sysprogsys a";
		qry += " where  exists (select 1 from sysprog b ";
		qry += " join roleprog c on b.progid=c.progid";
		qry += " join employe d on c.levelid=d.levelid";
		qry += " where d.epid=" + userid + " and c.fs=0 and a.sysid=b.sysid) and a.sysid<10";
		qry += " order by a.sysid";
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);

		if (tb.getRowCount() == 0) // 如果当前用户未授权，默认取所属角色的权限
		{
			qry = "select a.sysid ,a.sysname";
			qry += " from sysprogsys a ";
			qry += " where  exists ( ";
			qry += "  select 1 from sysprog b  ";
			qry += "  join roleprog c on b.progid=c.progid ";
			qry += "  join userrole d on d.glevelid=c.levelid ";
			qry += "  join employe e on d.levelid=e.levelid ";
			qry += "  where e.epid=" + userid + " and c.fs=0 and a.sysid=b.sysid) and a.sysid<10";
			qry += "  order by a.sysid ";
			tb = DbHelperSQL.Query(qry).getTable(1);
		}

		return tb;
	}

	// 获取所有据
	public Table GetTable() {
		String qry = " select a.sysid ,a.sysname";
		qry += " from sysprogsys a";
		qry += " order by a.sysid";
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);

		return tb;
	}

	// 获取分页数据
	public Table GetTableIPD(long userid, int vertag) {

		String qry = " select a.sysid ,a.sysname";
		qry += "\n from sysprogsys a";
		qry += "\n where a.tag=0 and exists (select 1 from sysprog b ";
		qry += "\n join roleprog c on b.progid=c.progid";
		qry += "\n join employe d on c.levelid=d.levelid";
		qry += "\n where d.epid=" + userid + " and b.noused=0 and a.tag=0 and c.fs=0 and a.sysid=b.sysid";

		//sysprog->csxxx
		//XXXXXXXXXXX
		//||||    |||__10迷你
		//||||    ||___9批发
		//||||    |____8零售
		//||||_________4PC
		//|||__________3IPAD
		//||___________2苹果
		//|____________1安卓
		qry += "\n and substr(b.csxxx,3,1)='1' ";

		if (vertag == 0)
			qry += " and substr(b.csxxx,8,1)='1' ";
		else if (vertag == 1)
			qry += " and substr(b.csxxx,9,1)='1' ";
		else if (vertag == 2)
			qry += " and (substr(b.csxxx,8,1)='1' or  substr(b.csxxx,9,1)='1')";
		else if (vertag == 3)
			qry += " and substr(b.csxxx,10,1)='1' ";

		qry += "\n )";   // and a.sysid<>10
		qry += "\n order by a.sortid";
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		//		System.out.println(qry);
		if (tb.getRowCount() == 0) // 如果当前用户未授权，默认取所属角色的权限
		{
			qry = "select a.sysid ,a.sysname";
			qry += "\n from sysprogsys a ";
			qry += "\n where  a.tag=0 and exists ( ";
			qry += "\n  select 1 from sysprog b  ";
			qry += "\n  join roleprog c on b.progid=c.progid ";
			qry += "\n  join userrole d on d.glevelid=c.levelid ";
			qry += "\n  join employe e on d.levelid=e.levelid ";
			qry += "\n  where e.epid=" + userid + " and a.tag=0 and b.noused=0 and c.fs=0 and a.sysid=b.sysid";
			qry += "\n and substr(b.csxxx,3,1)='1' ";

			if (vertag == 0)
				qry += " and substr(b.csxxx,8,1)='1' ";
			else if (vertag == 1)
				qry += " and substr(b.csxxx,9,1)='1' ";
			else if (vertag == 2)
				qry += " and (substr(b.csxxx,8,1)='1' or  substr(b.csxxx,9,1)='1')";
			else if (vertag == 3)
				qry += " and substr(b.csxxx,10,1)='1' ";
			qry += "\n ) ";
			qry += "\n  order by a.sortid ";
			tb = DbHelperSQL.Query(qry).getTable(1);
		}

		return tb;
	}

	// 返回功能菜单，pc专用
	public int Menu(long userid, int vertag) {
		if (userid == 0) {
			errmess = "userid不是一个有效值！";
			return 0;
		}

		String qry = " select a.sysid ,a.sysname";
		qry += "\n from sysprogsys a";
		qry += "\n where a.tag=0 and exists (select 1 from sysprog b ";
		qry += "\n join roleprog c on b.progid=c.progid";
		qry += "\n join employe d on c.levelid=d.levelid";
		qry += "\n where d.epid=" + userid + " and b.noused=0 and c.fs=0 and a.sysid=b.sysid";
		
		//sysprog->csxxx
		//XXXXXXXXXXX
		//||||    |||__10迷你
		//||||    ||___9批发
		//||||    |____8零售
		//||||_________4PC
		//|||__________3IPAD
		//||___________2苹果
		//|____________1安卓
		qry += "\n and  substr(b.csxxx,4,1)='1' ";

		if (vertag == 0)
			qry += " and substr(b.csxxx,8,1)='1' ";
		else if (vertag == 1)
			qry += " and substr(b.csxxx,9,1)='1' ";
		else if (vertag == 2)
			qry += " and (substr(b.csxxx,8,1)='1' or  substr(b.csxxx,9,1)='1')";
		else if (vertag == 3)
			qry += " and substr(b.csxxx,10,1)='1' ";

		qry += "\n ) ";
		qry += "\n order by a.sortid";
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) // 如果当前用户未授权，默认取所属角色的权限
		{
			qry = "select a.sysid,a.sysname";
			qry += "\n from sysprogsys a ";
			qry += "\n where a.tag=0 and a.sysid<>11 and a.sysid<>99  and exists ( ";
			qry += "\n  select 1 from sysprog b  ";
			qry += "\n  join roleprog c on b.progid=c.progid ";
			qry += "\n  join userrole d on d.glevelid=c.levelid ";
			qry += "\n  join employe e on d.levelid=e.levelid ";
			qry += "\n  where e.epid=" + userid + "  and b.noused=0 and c.fs=0 and a.sysid=b.sysid";
			qry += "\n  and  substr(b.csxxx,4,1)='1' ";

			if (vertag == 0)
				qry += " and substr(b.csxxx,8,1)='1' ";
			else if (vertag == 1)
				qry += " and substr(b.csxxx,9,1)='1' ";
			else if (vertag == 2)
				qry += " and (substr(b.csxxx,8,1)='1' or  substr(b.csxxx,9,1)='1')";
			else if (vertag == 3)
				qry += " and substr(b.csxxx,10,1)='1' ";
			qry += "\n )  ";
			qry += "\n  order by a.sysid ";
			tb = DbHelperSQL.Query(qry).getTable(1);
		}
//		System.out.println("111:"+qry);
		int count = tb.getRowCount();
		String retcs = "{\"result\":1,\"msg\":\"操作成功！\",\"total\":" + count+ ",\"rows\":[";
		for (int i = 0; i < count; i++) {
			Sysid = Long.parseLong(tb.getRow(i).get("SYSID").toString());
			if (i > 0)
				retcs += ",";
			retcs += "{\"SYSID\":\"" + Sysid + "\""//
					+ ",\"SYSNAME\":\"" + tb.getRow(i).get("SYSNAME").toString() + "\"";
			String qry1 = "select a.progid,a.progname";
			qry1 += " from sysprog a ";
			qry1 += "\n join roleprog c on a.progid=c.progid ";
			qry1 += "\n join employe d on c.levelid=d.levelid ";
			qry1 += "\n where d.epid=" + userid + " and a.noused=0 and c.fs=0 ";
//			qry1 += " and a.tag=0";
			qry1 += " and a.sysid=" + Sysid;
			// sysprog->ptag:运行设备
			// 0=手机+pc端+ipad，1=pc端专用,2=手机，3=ipad,4=手机+pc,5=手机+ipad，6=pc+ipad
			// --sysprog->htag:程序属性0=通用(蓝窗),1=云壹，10=上下衣,11=上下衣供应商

			//sysprog->csxxx
			//XXXXXXXXXXX
			//||||    |||__10迷你
			//||||    ||___9批发
			//||||    |____8零售
			//||||_________4PC
			//|||__________3IPAD
			//||___________2苹果
			//|____________1安卓

			qry1 += " and substr(a.csxxx,4,1)='1' ";

			if (vertag == 0)
				qry1 += " and substr(a.csxxx,8,1)='1' ";
			else if (vertag == 1)
				qry1 += " and substr(a.csxxx,9,1)='1' ";
			else if (vertag == 2)
				qry1 += " and (substr(a.csxxx,8,1)='1' or substr(a.csxxx,9,1)='1' )";

			else if (vertag == 3) //迷你
				qry1 += " and substr(a.csxxx,10,1)='1' ";

			// qry += " and a.progid<>1708 and a.progid<>1709 and a.progid<>1509 ";
			qry1 += "\n order by a.ordid,a.progid";
			Table tb1 = DbHelperSQL.Query(qry1).getTable(1);
//			System.out.println("222:"+qry1);

			retcs += ",\"proglist\":[";
			for (int j = 0; j < tb1.getRowCount(); j++) {
				if (j > 0)
					retcs += ",";
				retcs += "{\"PROGID\":\"" + tb1.getRow(j).get("PROGID").toString() + "\""//
						+ ",\"PROGNAME\":\"" + tb1.getRow(j).get("PROGNAME").toString() + "\"}";
			}
			retcs += "]}";

		}
		retcs += "]}";
		errmess = retcs;
		return 1;
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Sysprogsys");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}
}