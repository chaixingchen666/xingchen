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
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-24 23:43:13
//*************************************
public class Buyerbrand implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private Long Buyaccid;
	private Long Brandid;
	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setBuyaccid(Long buyaccid) {
		this.Buyaccid = buyaccid;
	}

	public Long getBuyaccid() {
		return Buyaccid;
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
		// strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		// strSql.append(" update Buyerbrand set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
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
	public int Append(int value) {
		if (Buyaccid == 0 || Brandid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		if (value == 0) {
			strSql.append(" delete from buyerbrand where accid=" + Accid + " and buyaccid=" + Buyaccid + " and brandid=" + Brandid);
		} else {
			strSql.append(" declare");
			strSql.append("   v_count number;");
			strSql.append(" begin");
			strSql.append("    select count(*) into v_count from buyerbrand where accid=" + Accid + " and buyaccid=" + Buyaccid + " and brandid=" + Brandid + " ;");
			strSql.append("    if v_count=0 then");
			strSql.append("       insert into buyerbrand (id,accid,buyaccid,brandid) ");
			strSql.append("        values (buyerbrand_id.nextval," + Accid + ", " + Buyaccid + "," + Brandid + ");");
			strSql.append("    end if;");
			strSql.append(" end ;");
			// qry = "if not exists(select 1 from employehouse where epid=" + epid + " and houseid=" + houseid + ")";
			// qry += "insert into employehouse (epid,houseid) values (" + epid + "," + houseid + ")";
		}

		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 增加记录
	public int AppendAll(int value) {
		if (Buyaccid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		if (value == 0) {
			strSql.append(" delete from buyerbrand where accid=" + Accid + " and buyaccid=" + Buyaccid);
		} else {
			strSql.append(" insert into buyerbrand (id,accid,buyaccid,brandid)");
			strSql.append("\n select buyerbrand_id.nextval," + Accid + "," + Buyaccid + ",brandid from brand a where a.accid=" + Accid);
			strSql.append("\n and not exists (select 1 from buyerbrand b where a.brandid=b.brandid and b.accid=" + Accid + " and b.buyaccid=" + Buyaccid + ") ");
		}

		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
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
		strSql.append("   update Buyerbrand set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Buyaccid != null) {
			strSql.append("buyaccid=" + Buyaccid + ",");
		}
		if (Brandid != null) {
			strSql.append("brandid=" + Brandid + ",");
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
		strSql.append(" FROM Buyerbrand ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获得数据列表
	public DataSet GetList() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("SELECT buyaccid, TRANSLATE (LTRIM (text, '~'), '*~', '*,') BRANDNAMELIST");
		strSql.append("\n FROM (SELECT ROW_NUMBER () OVER (PARTITION BY buyaccid ORDER BY buyaccid,lvl DESC) rn,buyaccid, text");
		strSql.append("\n FROM (SELECT  buyaccid, LEVEL lvl, SYS_CONNECT_BY_PATH (brandname,'~') text");
		strSql.append("\n FROM (SELECT  a.buyaccid, a.brandid,b.brandname, ROW_NUMBER () OVER (PARTITION BY a.buyaccid ORDER BY a.buyaccid,a.brandid) x");
		strSql.append("\n FROM buyerbrand a join brand b on a.brandid=b.brandid where a.accid=" + Accid + " and a.buyaccid=" + Buyaccid + " and b.statetag=1 and b.noused=0 ORDER BY a.brandid) a");
		strSql.append("\n CONNECT BY buyaccid = PRIOR buyaccid AND x - 1 = PRIOR x))");
		strSql.append("\n  WHERE rn = 1");
//		System.out.println(strSql.toString());
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp) {
		StringBuilder strSql = new StringBuilder();
		strSql.append(" select a.brandid,b.brandname,'1' as selbj from buyerbrand a");
		strSql.append(" join brand b on a.brandid=b.brandid");
		strSql.append(" where a.accid=" + Accid + " and a.buyaccid=" + Buyaccid + " and (b.statetag=1  and b.noused=0 or b.brandid=0)");
		strSql.append(" union all");
		strSql.append(" select a.brandid,a.brandname,'0' as selbj from brand a where (a.accid=" + Accid + " and a.statetag=1 and a.noused=0 or a.brandid=0)");
		strSql.append(" and not exists (select 1 from buyerbrand b where a.brandid=b.brandid and b.buyaccid=" + Buyaccid + " and b.accid=" + Accid + ")");
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Buyerbrand");
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