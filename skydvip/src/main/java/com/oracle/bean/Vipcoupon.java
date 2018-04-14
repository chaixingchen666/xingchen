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
//  @author:sunhong  @date:2017-02-25 17:10:03
//*************************************
public class Vipcoupon implements java.io.Serializable {
	private Long Vcpid;
	private Long Cpid;
	private Long Vipid;
	private Date Lastdate;
	private Integer Usetag;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setVcpid(Long vcpid) {
		this.Vcpid = vcpid;
	}

	public Long getVcpid() {
		return Vcpid;
	}

	public void setCpid(Long cpid) {
		this.Cpid = cpid;
	}

	public Long getCpid() {
		return Cpid;
	}

	public void setVipid(Long vipid) {
		this.Vipid = vipid;
	}

	public Long getVipid() {
		return Vipid;
	}

	// public void setLastdate(Date lastdate) {
	// this.Lastdate = lastdate;
	// }
	//
	// public String getLastdate() {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// return df.format(Lastdate);
	// }

	public void setUsetag(Integer usetag) {
		this.Usetag = usetag;
	}

	public Integer getUsetag() {
		return Usetag;
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
		// strSql.append(" update Vipcoupon set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) == 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	// 增加记录
	public int Append(long userid) {

		StringBuilder strSql = new StringBuilder();

		strSql.append("declare ");
		strSql.append("     v_vcpid number; ");
		strSql.append("     v_count number; ");
		strSql.append("     v_statetag number(1);");
		strSql.append("     v_retcs varchar2(200);");
		strSql.append("\n begin ");
		// 查看会员是否已申请加入
		strSql.append("\n   select count(*) into v_count from viphouse a where vipid=" + userid + " and statetag=1");
		strSql.append("\n   and exists (select 1 from housecoupon b where a.onhouseid=b.onhouseid and b.cpid=" + Cpid + ") ;");
		strSql.append("\n   if v_count=0 then");
		strSql.append("\n      v_retcs:= '0您加入该店会员后才能领取优惠券！' ; ");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   begin ");
		strSql.append("\n     select statetag into v_statetag from housecoupon where cpid=" + Cpid + ";"); // statetag:0=未发布 1=已发布 2=过期或取消
		strSql.append("\n     if v_statetag<>1 then ");
		strSql.append("\n        v_retcs:= '0当前优惠券已失效！' ; ");
		strSql.append("\n        goto exit;");
		strSql.append("\n     end if;");
		strSql.append("\n     select count(*) into v_count from Vipcoupon where vipid=" + userid + " and cpid=" + Cpid + "; ");
		strSql.append("\n     if v_count=0 then ");
		strSql.append("\n        v_vcpid:=Vipcoupon_vcpid.nextval;");
		strSql.append("\n        insert into Vipcoupon (vcpid,vipid,cpid,usetag,lastdate) ");
		strSql.append("\n        values (v_vcpid," + userid + "," + Cpid + ",0,sysdate);");
		strSql.append("\n     end if; ");
		strSql.append("\n     v_retcs:= '1'||v_vcpid ; ");
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n     v_retcs:= '0已领过该优惠券！' ; ");
		strSql.append("\n   end; ");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		strSql.append("\n end; ");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
//		DbHelperSQL dh=new DbHelperSQL();
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret == 0) {
			errmess = "操作失败！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		
		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
		// if (retcs.substring(0, 1).equals("1"))
		// return 1;
		// else
		// return 0;
		// // StringBuilder strSql1 = new StringBuilder();
		// StringBuilder strSql2 = new StringBuilder();
		// strSql1.append("id,");
		// strSql2.append("v_id,");
		// if (Vcpid != null) {
		// strSql1.append("vcpid,");
		// strSql2.append(Vcpid + ",");
		// }
		// if (Cpid != null) {
		// strSql1.append("cpid,");
		// strSql2.append(Cpid + ",");
		// }
		// if (Vipid != null) {
		// strSql1.append("vipid,");
		// strSql2.append(Vipid + ",");
		// }
		// if (Usetag != null) {
		// strSql1.append("usetag,");
		// strSql2.append(Usetag + ",");
		// }
		// strSql1.append("lastdate");
		// strSql2.append("sysdate");
		// strSql.append("declare ");
		// strSql.append(" v_id number; ");
		// strSql.append(" begin ");
		// strSql.append(" v_id:=Vipcoupon_id.nextval; ");
		// strSql.append(" insert into Vipcoupon (");
		// strSql.append(" " + strSql1.toString());
		// strSql.append(")");
		// strSql.append(" values (");
		// strSql.append(" " + strSql2.toString());
		// strSql.append(");");
		// strSql.append(" select v_id into :id from dual; ");
		// strSql.append(" end;");
		// Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		// param.put("id", new ProdParam(Types.BIGINT));
		// int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		// // id = Long.parseLong(param.get("id").getParamvalue().toString());
		// return ret;
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Vipcoupon set ");
		if (Vcpid != null) {
			strSql.append("vcpid=" + Vcpid + ",");
		}
		if (Cpid != null) {
			strSql.append("cpid=" + Cpid + ",");
		}
		if (Vipid != null) {
			strSql.append("vipid=" + Vipid + ",");
		}
		if (Usetag != null) {
			strSql.append("usetag=" + Usetag + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		return DbHelperSQL.ExecuteSql(strSql.toString());
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Vipcoupon ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Vipcoupon ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}
	// 获取分页数据
	public Table GetTable(QueryParam qp) {
//		StringBuilder strSql = new StringBuilder();
//		strSql.append("select " + fieldlist);
//		strSql.append(" FROM Vipcoupon ");
//		if (!strWhere.equals("")) {
//			strSql.append(" where " + strWhere);
//		}
//		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Vipcoupon");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}
}