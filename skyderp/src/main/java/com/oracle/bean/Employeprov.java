package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.Func;
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-07 20:29:13
//*************************************
public class Employeprov implements java.io.Serializable {
	private Long Accid;
	private Long Epid;
	private Long Provid;
	private String errmess;
	private String Findbox;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public Long getEpid() {
		return Epid;
	}

	public void setProvid(Long provid) {
		this.Provid = provid;
	}

	public Long getProvid() {
		return Provid;
	}
	public void setFindbox(String findbox) {
		Findbox = findbox;
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
		strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前记录已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		// strSql.append(" update employeprov set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	public int Append(int value) {
		if (Epid == 0 || Provid == 0) {
			errmess = "职员id或客户id无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		if (value == 0) {
			strSql.append("delete from employeprov where epid=" + Epid + " and Provid=" + Provid);
		} else {
			strSql.append(" declare");
			strSql.append("   v_count number;");
			strSql.append(" begin");
			strSql.append("    select count(*) into v_count from employeprov where epid=" + Epid + " and Provid=" + Provid + " ;");
			strSql.append("    if v_count=0 then");
			strSql.append("       insert into employeprov (epid,Provid) ");
			strSql.append("       values (" + Epid + ", " + Provid + ");");
			strSql.append("    end if;");
			strSql.append("    commit;");
			strSql.append(" end ;");
		}
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 成批增加记录 ok
	public int AppendAll(int value) {
		if (Epid == 0) {
			errmess = "职员id无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		if (value == 0) {
			strSql.append("delete from employeprov where epid=" + Epid);
		} else {

			strSql.append("insert into employeprov (epid,Provid)");
			strSql.append(" select " + Epid + ",Provid from provide a where  a.statetag=1 and a.noused=0 and a.accid=" + Accid);
			strSql.append(" and not exists (select 1 from employeprov b where a.Provid=b.Provid and b.epid=" + Epid + ") ");
		}
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
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
		strSql.append("   update employeprov set ");
		if (Epid != null) {
			strSql.append("epid=" + Epid + ",");
		}
		if (Provid != null) {
			strSql.append("provid=" + Provid + ",");
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
		strSql.append(" FROM employeprov ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, int fs) {
		StringBuilder strSql = new StringBuilder();
		strSql.append(" select a.provid,b.provname,'1' as selbj from employeprov a");
		strSql.append(" join provide b on a.provid=b.provid");
		strSql.append(" where b.accid=" + Accid + " and a.epid=" + Epid + " and b.statetag=1  and b.noused=0");
		if (!Func.isNull(Findbox))
			strSql.append(" and (b.provname like '%" + Findbox + "%' or b.shortname like '%" + Findbox.toUpperCase() + "%' )");

		if (fs == 0) { // 获取职员客户授权表
			strSql.append(" union all");
			strSql.append(" select a.provid,a.provname,'0' as selbj from provide a where a.accid=" + Accid + " and a.statetag=1 and a.noused=0");
			strSql.append(" and not exists (select 1 from employeprov b where a.provid=b.provid and b.epid=" + Epid + ")");
			if (!Func.isNull(Findbox))
				strSql.append(" and (a.provname like '%" + Findbox + "%' or a.shortname like '%" + Findbox.toUpperCase() + "%' )");
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据  通过供应商查找职员
	public Table GetTableX(QueryParam qp) {
		StringBuilder strSql = new StringBuilder();
		strSql.append(" select a.epid,b.epname,b.houseid,c.housename,'1' as selbj from employeprov a");
		strSql.append("\n join employe b on a.epid=b.epid");
		strSql.append("\n left outer join warehouse c on b.houseid=c.houseid");
		strSql.append("\n where b.accid=" + Accid + " and a.Provid=" + Provid + " and b.statetag=1  and b.noused=0");
		if (!Func.isNull(Findbox))
			strSql.append("\n and (b.epname like '%" + Findbox + "%' or b.shortname like '%" + Findbox.toUpperCase() + "%' )");
		strSql.append("\n union all");
		strSql.append("\n select a.epid,a.epname,a.houseid,c.housename,'0' as selbj from employe a ");
		strSql.append("\n left outer join warehouse c on a.houseid=c.houseid");
		strSql.append("\n where a.accid=" + Accid + " and a.statetag=1 and a.noused=0");
		if (!Func.isNull(Findbox))
			strSql.append("\n and (a.epname like '%" + Findbox + "%' or a.shortname like '%" + Findbox.toUpperCase() + "%' )");

		strSql.append("\n and not exists (select 1 from employeprov b where a.epid=b.epid and b.Provid=" + Provid + ")");

		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from employeprov");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}

	// 成批写供应商职员授权记录
	public int AppendAll1(int value) {
		if ( Provid== 0) {
			errmess = "参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		if (value == 0) {
			strSql.append("delete from employeprov where Provid=" + Provid);
		} else {
			strSql.append("insert into employeprov (Provid,epid)");
			strSql.append(" select " + Provid + ",epid from employe a where  a.statetag=1 and a.noused=0 and a.accid=" + Accid);
			strSql.append(" and not exists (select 1 from employeprov b where a.epid=b.epid and b.Provid=" + Provid + ") ");
		}
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}


}