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
//  @author:sunhong  @date:2017-03-07 20:03:15
//*************************************
public class Employebrand implements java.io.Serializable {
	private Float Id;
	private Long Epid;
	private Long Accid;
	private Long Brandid;
	private String errmess;
	private String Findbox;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setFindbox(String findbox) {
		Findbox = findbox;
	}

	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getEpid() {
		return Epid;
	}

	public void setBrandid(Long brandid) {
		this.Brandid = brandid;
	}

	public Long getBrandid() {
		return Brandid;
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
		// strSql.append(" update employebrand set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	// 增加记录
	public int Append(int value) {
		if (Epid == 0 || Brandid == 0) {
			errmess = "epid或brandid参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		if (value == 0) {
			strSql.append("delete from employebrand where epid=" + Epid + " and brandid=" + Brandid);
		} else {
			strSql.append("declare");
			strSql.append("   v_count number;");
			strSql.append(" begin");
			strSql.append("    select count(*) into v_count from employebrand where epid=" + Epid + " and brandid=" + Brandid + " ;");
			strSql.append("    if v_count=0 then");
			strSql.append("       insert into employebrand (id,epid,brandid) ");
			strSql.append("       values (employebrand_id.nextval," + Epid + ", " + Brandid + ");");
			strSql.append("    end if;");
			strSql.append("    commit;");
			strSql.append(" end ;");
		}
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 成批写职员品牌授权记录
	public int AppendAll(int value) {
		if (Epid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		if (value == 0) {
			strSql.append("delete from employebrand where epid=" + Epid);
		} else {
			strSql.append("insert into employebrand (id,epid,brandid)");
			strSql.append(" select employebrand_id.nextval," + Epid + ",brandid from brand a where  a.statetag=1 and a.noused=0 and a.accid=" + Accid);
			strSql.append(" and not exists (select 1 from employebrand b where a.brandid=b.brandid and b.epid=" + Epid + ") ");
		}
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	

	// 成批写品牌职员授权记录
	public int AppendAll1(int value) {
		if ( Brandid== 0) {
			errmess = "参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		if (value == 0) {
			strSql.append("delete from employebrand where Brandid=" + Brandid);
		} else {
			strSql.append("insert into employebrand (id,brandid,epid)");
			strSql.append(" select employebrand_id.nextval," + Brandid + ",epid from employe a where  a.statetag=1 and a.noused=0 and a.accid=" + Accid);
			strSql.append(" and not exists (select 1 from employebrand b where a.epid=b.epid and b.brandid=" + Brandid + ") ");
		}
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		// StringBuilder strSql = new StringBuilder();
		// strSql.append("begin ");
		// strSql.append(" update employebrand set ");
		// if (Id != null) {
		// strSql.append("id='" + Id + "',");
		// }
		// if (Epid != null) {
		// strSql.append("epid=" + Epid + ",");
		// }
		// if (Brandid != null) {
		// strSql.append("brandid=" + Brandid + ",");
		// }
		// strSql.append("lastdate=sysdate");
		// strSql.append(" where id=id ;");
		// strSql.append(" commit;");
		// strSql.append(" end;");
		// int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		// if (ret < 0)
		// return 0;
		// else
		return 1;
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM employebrand ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM employebrand ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	
	
	// 获取分页数据  通过品牌查找职员
	public Table GetTableX(QueryParam qp) {
		StringBuilder strSql = new StringBuilder();
		strSql.append(" select a.epid,b.epname,b.houseid,c.housename,'1' as selbj from employebrand a");
		strSql.append("\n join employe b on a.epid=b.epid");
		strSql.append("\n left outer join warehouse c on b.houseid=c.houseid");
		strSql.append("\n where b.accid=" + Accid + " and a.brandid=" + Brandid + " and b.statetag=1  and b.noused=0");
		if (!Func.isNull(Findbox))
			strSql.append("\n and (b.epname like '%" + Findbox + "%' or b.shortname like '%" + Findbox.toUpperCase() + "%' )");
		strSql.append("\n union all");
		strSql.append("\n select a.epid,a.epname,a.houseid,c.housename,'0' as selbj from employe a ");
		strSql.append("\n left outer join warehouse c on a.houseid=c.houseid");
		strSql.append("\n where a.accid=" + Accid + " and a.statetag=1 and a.noused=0");
		if (!Func.isNull(Findbox))
			strSql.append("\n and (a.epname like '%" + Findbox + "%' or a.shortname like '%" + Findbox.toUpperCase() + "%' )");

		strSql.append("\n and not exists (select 1 from employebrand b where a.epid=b.epid and b.brandid=" + Brandid + ")");

		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}
	// 获取分页数据
	public Table GetTable(QueryParam qp) {
		StringBuilder strSql = new StringBuilder();
		strSql.append(" select a.brandid,b.brandname,'1' as selbj from employebrand a");
		strSql.append(" join brand b on a.brandid=b.brandid");
		strSql.append(" where b.accid=" + Accid + " and a.epid=" + Epid + " and b.statetag=1  and b.noused=0");
		if (!Func.isNull(Findbox))
			strSql.append(" and (b.brandname like '%" + Findbox + "%' or b.shortname like '%" + Findbox.toUpperCase() + "%' )");
		strSql.append(" union all");
		strSql.append(" select a.brandid,a.brandname,'0' as selbj from brand a where a.accid=" + Accid + " and a.statetag=1 and a.noused=0");
		if (!Func.isNull(Findbox))
			strSql.append(" and (a.brandname like '%" + Findbox + "%' or a.shortname like '%" + Findbox.toUpperCase() + "%' )");

		strSql.append(" and not exists (select 1 from employebrand b where a.brandid=b.brandid and b.epid=" + Epid + ")");

		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable1(QueryParam qp) {
		StringBuilder strSql = new StringBuilder();
		strSql.append(" select a.brandid,b.brandname from employebrand a");
		strSql.append(" left outer join brand b on a.brandid=b.brandid");
		strSql.append(" where b.accid=" + Accid + " and a.epid=" + Epid + " and b.noused=0");
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		// StringBuilder strSql = new StringBuilder();
		// strSql.append("select count(*) as num from employebrand");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// return true;
		// }
		return false;
	}
}