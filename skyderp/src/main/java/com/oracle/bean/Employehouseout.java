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
//  @author:sunhong  @date:2017-03-07 20:03:23
//*************************************
public class Employehouseout implements java.io.Serializable {
	private Float Id;
	private Long Epid;
	private Long Accid;
	private Long Houseid;
	private String errmess;
	private String Findbox;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setAccid(Long accid) {
		this.Accid = accid;
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

	public Long getEpid() {
		return Epid;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	public void setFindbox(String findbox) {
		Findbox = findbox;
	}

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
		// strSql.append(" update employehouseout set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	// 增加记录
	public int Append(int value) {
		if (Epid == 0 || Houseid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append(" declare");
		strSql.append("\n   v_count number;");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n begin");
		if (value == 0) {

//			strSql.append("\n    select count(*) into v_count from employe where epid=" + Epid + " and statetag=1 and houseid=" + Houseid + ";");
//			strSql.append("\n    if v_count>0 then ");
//			strSql.append("\n       v_retcs:='0职员所属店铺不允许取消授权！';");
//			strSql.append("\n       goto exit;");
//			strSql.append("\n    end if;");
			strSql.append("\n    delete from employehouseout a where epid=" + Epid + " and houseid=" + Houseid+";");
//			strSql.append("\n    and not exists (select 1 from employe b where a.epid=b.epid and a.houseid=b.houseid);");
		} else {
			strSql.append("\n    select count(*) into v_count from employehouseout where epid=" + Epid + " and houseid=" + Houseid + " ;");
			strSql.append("\n    if v_count=0 then");
			strSql.append("\n       insert into employehouseout (id,epid,houseid) ");
			strSql.append("\n       values (employehouseout_id.nextval," + Epid + ", " + Houseid + ");");
			strSql.append("\n    end if;");

//			strSql.append("\n    select count(*) into v_count from employehouse where epid=" + Epid + " and houseid=" + Houseid + " ;");
//			strSql.append("\n    if v_count=0 then");
//			strSql.append("\n       insert into employehouse (id,epid,houseid) ");
//			strSql.append("\n       values (employehouse_id.nextval," + Epid + ", " + Houseid + ");");
//			strSql.append("\n    end if;");

			//			strSql.append("\n    commit;");
		}
		strSql.append("\n       v_retcs:='1操作成功！';");

		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual ;");

		strSql.append("\n end ;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

	// 增加记录
	public int AppendAll(int value) {
		if (Epid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append(" begin");
		if (value == 0) {
			strSql.append("\n   delete from employehouseout a where epid=" + Epid+";");
//			strSql.append("\n   and not exists (select 1 from employe b where a.epid=b.epid and a.houseid=b.houseid);");
		} else {
			strSql.append("\n  insert into employehouseout (id,epid,houseid)");
			strSql.append("\n  select employehouseout_id.nextval," + Epid + ",houseid from warehouse a where a.statetag=1 and a.noused=0 and a.accid=" + Accid);
			strSql.append("\n  and not exists (select 1 from employehouseout b where a.houseid=b.houseid and b.epid=" + Epid + ") ;");

			strSql.append("\n  insert into employehouse (id,epid,houseid)");
			strSql.append("\n  select employehouse_id.nextval," + Epid + ",houseid from warehouse a where a.statetag=1 and a.noused=0 and a.accid=" + Accid);
			strSql.append("\n  and not exists (select 1 from employehouse b where a.houseid=b.houseid and b.epid=" + Epid + ") ;");
		}
		strSql.append("\n end;");
		//System.out.println(strSql.toString());
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
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("\n   update employehouseout set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Epid != null) {
			strSql.append("epid=" + Epid + ",");
		}
		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
		strSql.append("\n  commit;");
		strSql.append("\n end;");
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
		strSql.append(" FROM employehouseout ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp) {
		StringBuilder strSql = new StringBuilder();
		strSql.append(" select a.houseid,b.housename,'1' as selbj from employehouseout a");
		strSql.append("\n join warehouse b on a.houseid=b.houseid");
		strSql.append("\n where b.accid=" + Accid + " and a.epid=" + Epid + " and b.statetag=1 and b.noused=0");
		if (!Func.isNull(Findbox))
			strSql.append(" and (b.housename like '%" + Findbox + "%' or b.shortname like '%" + Findbox.toUpperCase() + "%' )");
		strSql.append("\n union all");
		strSql.append("\n select a.houseid,a.housename,'0' as selbj from warehouse a where a.accid=" + Accid + " and a.statetag=1 and a.noused=0");
		if (!Func.isNull(Findbox))
			strSql.append("\n and (a.housename like '%" + Findbox + "%' or a.shortname like '%" + Findbox.toUpperCase() + "%' )");

		strSql.append("\n and not exists (select 1 from employehouseout b where a.houseid=b.houseid and b.epid=" + Epid + ")");
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable1(QueryParam qp) {
		StringBuilder strSql = new StringBuilder();
		strSql.append(" select a.houseid,b.housename from employehouseout a");
		strSql.append("\n left outer join warehouse b on a.houseid=b.houseid");
		strSql.append("\n where b.accid=" + Accid + " and a.epid=" + Epid + "  and b.noused=0");
		if (!Func.isNull(Findbox))
			strSql.append("\n and (b.housename like '%" + Findbox + "%' or b.shortname like '%" + Findbox.toUpperCase() + "%' )");
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from employehouseout");
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