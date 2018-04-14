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
//  @author:sunhong  @date:2017-03-12 19:49:00
//*************************************
public class Vipbanner implements java.io.Serializable {
	private Long Bannerid;
	private String Bannername;
	private Date Lastdate;
	private String Lastop;
	private Integer Statetag;
	private Long Onhouseid;
	private Integer Lx;
	private String Imagename;
	private String Refuse;
	private String Webadd;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setBannerid(Long bannerid) {
		this.Bannerid = bannerid;
	}

	public Long getBannerid() {
		return Bannerid;
	}

	public void setBannername(String bannername) {
		this.Bannername = bannername;
	}

	public String getBannername() {
		return Bannername;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setOnhouseid(Long onhouseid) {
		this.Onhouseid = onhouseid;
	}

	public Long getOnhouseid() {
		return Onhouseid;
	}

	public void setLx(Integer lx) {
		this.Lx = lx;
	}

	public Integer getLx() {
		return Lx;
	}

	public void setImagename(String imagename) {
		this.Imagename = imagename;
	}

	public String getImagename() {
		return Imagename;
	}

	public void setRefuse(String refuse) {
		this.Refuse = refuse;
	}

	public String getRefuse() {
		return Refuse;
	}

	public void setWebadd(String webadd) {
		this.Webadd = webadd;
	}

	public String getWebadd() {
		return Webadd;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Bannerid == 0) {
			errmess = "参数Bannerid无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n   v_count number(10);");
		strSql.append("\n begin");
		// strSql.append(" begin");
		// strSql.append(" select imagename into v_retcs from vipbanner where statetag=0 and bannerid=" + Bannerid + ";");
		// strSql.append(" EXCEPTION WHEN NO_DATA_FOUND THEN");
		// strSql.append(" v_retcs:='';");
		// strSql.append(" end;");
		strSql.append("\n   select count(*) into v_count from vipbanner where statetag=0 and bannerid=" + Bannerid + ";");
		strSql.append("\n   if v_count=0 then");
		strSql.append("\n      v_retcs:='0未找到记录！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		strSql.append("\n   delete from vipbanner where statetag=0 and bannerid=" + Bannerid + ";");
		strSql.append("\n   v_retcs:='1操作成功！';");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		strSql.append("\n end;");
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
	public int Append(String pictjson) {

		if (Lx == 1 && Onhouseid == 0) // 上传店铺广告图片
		{
			errmess = "线上店铺id参数无效！";
			return 0;
		}
		if (pictjson == null || pictjson.equals("")) {
			errmess = "未传入图片组参数:pictjson！";
			return 0;
		}
		JSONObject jsonObject = JSONObject.fromObject(pictjson);
		if (!jsonObject.has("base64string")) {
			errmess = "未传入图片值参数:base64string！";
			return 0;
		}
		String base64string = jsonObject.getString("base64string");
		String retcs = Func.Base64UpFastDFS(base64string);
		if (retcs.substring(0, 1).equals("1")) {
			Imagename = retcs.substring(1);
		} else {
			errmess = "广告图片上传失败！" + retcs.substring(1);
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("bannerid,");
		strSql2.append("v_id,");
		// if (Bannerid != null) {
		// strSql1.append("bannerid,");
		// strSql2.append(Bannerid + ",");
		// }
		if (Bannername != null) {
			strSql1.append("bannername,");
			strSql2.append("'" + Bannername + "',");
		}
		// if (Lastop != null) {
		// }
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Onhouseid != null) {
			strSql1.append("onhouseid,");
			strSql2.append(Onhouseid + ",");
		}
		if (Lx != null) {
			strSql1.append("lx,");
			strSql2.append(Lx + ",");
		}
		if (Imagename != null) {
			strSql1.append("imagename,");
			strSql2.append("'" + Imagename + "',");
		}
		if (Refuse != null) {
			strSql1.append("refuse,");
			strSql2.append("'" + Refuse + "',");
		}
		if (Webadd != null) {
			strSql1.append("webadd,");
			strSql2.append("'" + Webadd + "',");
		}
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=VIPbanner_bannerid.nextval; ");
		strSql.append("   insert into VIPbanner (");
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
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		Bannerid = Long.parseLong(param.get("id").getParamvalue().toString());
		errmess = Bannerid.toString();
		return 1;
	}

	// 修改记录
	public int Update(String pictjson) {
		if (Bannerid == 0) {
			errmess = "Bannerid参数无效！";
			return 0;
		}

		if (pictjson != null && !pictjson.equals("")) {
			JSONObject jsonObject = JSONObject.fromObject(pictjson);
			if (jsonObject.has("base64string")) {
				String base64string = jsonObject.getString("base64string");
				String retcs = Func.Base64UpFastDFS(base64string);
				if (retcs.substring(0, 1).equals("1")) {
					Imagename = retcs.substring(1);
				}
			}
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update VIPbanner set ");
		if (Bannername != null) {
			strSql.append("bannername='" + Bannername + "',");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Onhouseid != null) {
			strSql.append("onhouseid=" + Onhouseid + ",");
		}
		if (Lx != null) {
			strSql.append("lx=" + Lx + ",");
		}
		if (Imagename != null) {
			strSql.append("imagename='" + Imagename + "',");
		}
		if (Refuse != null) {
			strSql.append("refuse='" + Refuse + "',");
		}
		if (Webadd != null) {
			strSql.append("webadd='" + Webadd + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where Bannerid=" + Bannerid + " ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "修改失败!";
			return 0;
		} else {
			errmess = "修改成功!";
			return 1;
		}
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM VIPbanner ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM VIPbanner a left outer join onlinehouse b on A.ONHOUSEID=B.ONHOUSEID");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from VIPbanner");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 更改公共标题横幅状态
	public int ChangePublicBanner(int zt) {
		if (Bannerid == 0) {
			errmess = "bannerid参数无效！";
			return 0;
		}

		String qry = "";
		if (zt == 1) // 发布图片要判断店铺图片允许张数
		{
			qry = "declare ";
			qry += "  v_numview number(10); ";
			qry += "  v_bannerid number(10); ";
			qry += "  v_houseid number(10); ";
			qry += "  v_count number(10); ";
			qry += "  v_retcs varchar2(200); ";
			qry += " begin ";
			qry += "    v_bannerid:=" + Bannerid + "; v_numview:=5; ";
			// qry += " select a.houseid,b.numview into v_houseid,v_numview from vipbanner a join warehouse b on a.houseid=b.houseid where a.bannerid=v_bannerid;";
			qry += "    select count(*) into v_count from vipbanner where statetag=3 and lx=0; ";
			qry += "    if v_count>=v_numview then";
			qry += "       v_retcs:= '0公共区只允许发布'||v_numview||'张横幅广告！' ; ";
			qry += "       goto exit; ";
			qry += "    end if;";
			qry += "    update vipbanner set statetag=3,lastdate=sysdate,lastop='" + Lastop + "' where statetag=0 and bannerid=" + Bannerid + "; ";
			qry += "    v_retcs:= '1发布成功！' ; ";
			qry += "    <<exit>>";
			qry += "    select v_retcs into :retcs from dual; ";
			qry += " end;";

			Map<String, ProdParam> param = new HashMap<String, ProdParam>();
			param.put("retcs", new ProdParam(Types.VARCHAR));
			int ret = DbHelperSQL.ExecuteProc(qry, param);
			if (ret == 0) {
				errmess = "操作异常！";
				return 0;
			}
			String retcs = param.get("retcs").getParamvalue().toString();

			errmess = retcs.substring(1);
			return Integer.parseInt(retcs.substring(0, 1));
			// return;
		} else

		if (zt == 0) {
			qry = "update vipbanner set statetag=0,refuse='',lastdate=sysdate,lastop='" + Lastop + "' where statetag=3 and bannerid=" + Bannerid;
			if (DbHelperSQL.ExecuteSql(qry) < 0) {
				errmess = "操作失败！";
				return 0;
			} else {
				errmess = "操作成功！";
				return 1;
			}

		} else {
			errmess = "参数:zt无效！";
			return 0;
		}
	}

	// 更改店铺标题横幅状态
	public int ChangeHouseBanner(int zt) {
		if (Bannerid == 0) {
			errmess = "bannerid参数无效！";
			return 0;
		}
		if (zt == 6 && Refuse.equals("")) {
			errmess = "请输入未通过审核原因！";
			return 0;
		}
		String qry = "";
		// if (zt == 0) // 提交
		// {
		// qry = "declare ";
		// // qry += " v_numview number(10); ";
		// // y += " v_bannerid number(10); ";
		// qry += " v_bannername varchar2(200);";
		// qry += " v_retcs varchar2(200); ";
		// qry += " begin ";
		// // y += " v_bannerid:=" + bannerid + "; ";
		// qry += " begin ";
		// qry += " select bannername into v_bannername from vipbanner where bannerid=" + Bannerid + ";";
		// qry += " if nvl(length(v_bannername),0)=0 then ";
		// qry += " v_retcs:= '0请输入横幅广告词！' ; ";
		// qry += " goto exit; ";
		// qry += " end if;";
		// qry += " update vipbanner set statetag=1,lastdate=sysdate,lastop='" + Lastop + "' where statetag=0 and bannerid=" + Bannerid + "; ";
		// qry += " v_retcs:= '1提交成功！'; ";
		// qry += " EXCEPTION WHEN NO_DATA_FOUND THEN";
		// qry += " v_retcs:= '0未找到店铺横幅记录！' ; ";
		// qry += " end;";
		// qry += " <<exit>>";
		// qry += " select v_retcs into :retcs from dual; ";
		// qry += " end;";
		//
		// Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		// param.put("retcs", new ProdParam(Types.VARCHAR));
		// int ret = DbHelperSQL.ExecuteProc(qry, param);
		// if (ret == 0) {
		// errmess = "操作异常！";
		// return 0;
		// }
		// String retcs = param.get("retcs").getParamvalue().toString();
		//
		// errmess = retcs.substring(1);
		// return Integer.parseInt(retcs.substring(0, 1));
		// // return;
		// } else
		// zt=0:提交 statetag 0->1
		// zt=1:撤消待审 statetag 1->0
		// zt=2:发布 statetag 2->3
		// zt=3:撤消审核 statetag 2->1
		// zt=4:撤消发布 statetag 3->2
		// zt=5:审核通过 statetag 1->2
		// zt=6:拒绝 statetag 1->0 要传入refuse
		if (zt == 2) {// 发布
			qry = "declare ";
			qry += "  v_numview number(10); ";
			// qry += " v_bannerid number(10); ";
			qry += "  v_houseid number(10); ";
			qry += "  v_count number(10); ";
			qry += "  v_retcs varchar2(200); ";
			qry += " begin ";
			qry += "   begin ";
			// qry += " v_bannerid:=" + bannerid + "; ";
			qry += "    select a.onhouseid,b.numview into v_houseid,v_numview from vipbanner a join onlinehouse b on a.onhouseid=b.onhouseid where a.bannerid=" + Bannerid + ";";
			qry += "    select count(*) into v_count from vipbanner where onhouseid=v_houseid and statetag=3 and lx=1; ";
			qry += "    if v_count>=v_numview then";
			qry += "       v_retcs:= '0当前店铺只允许发布'||v_numview||'张横幅广告！' ; ";
			qry += "       goto exit; ";
			qry += "    end if;";
			qry += "    update vipbanner set statetag=3,lastdate=sysdate,lastop='" + Lastop + "' where statetag=2 and bannerid=" + Bannerid + "; ";
			qry += "    update onlinehouse set lastdate=sysdate where onhouseid=v_houseid; ";
			qry += "    v_retcs:= '1发布成功！' ; ";
			qry += "  EXCEPTION WHEN NO_DATA_FOUND THEN";
			qry += "    v_retcs:= '0未找到店铺横幅记录' ; ";
			qry += "  end;";
			qry += "  <<exit>>";
			qry += "  select v_retcs into :retcs from dual; ";
			qry += " end;";

			Map<String, ProdParam> param = new HashMap<String, ProdParam>();
			param.put("retcs", new ProdParam(Types.VARCHAR));
			int ret = DbHelperSQL.ExecuteProc(qry, param);
			if (ret == 0) {
				errmess = "操作异常！";
				return 0;
			}
			String retcs = param.get("retcs").getParamvalue().toString();

			errmess = retcs.substring(1);
			return Integer.parseInt(retcs.substring(0, 1));

		}
		if (zt == 0)
			qry = "update vipbanner set statetag=1,refuse='',lastdate=sysdate,lastop='" + Lastop + "' where statetag=0 and bannerid=" + Bannerid;
		else

		if (zt == 1)// 撤消待审 statetag 1->0
			qry = "update vipbanner set statetag=0,refuse='',lastdate=sysdate,lastop='" + Lastop + "' where statetag=1 and bannerid=" + Bannerid;
		else if (zt == 3)
			qry = "update vipbanner set statetag=1,lastdate=sysdate,lastop='" + Lastop + "' where statetag=2 and bannerid=" + Bannerid;
		else if (zt == 4)
			qry = "update vipbanner set statetag=2,lastdate=sysdate,lastop='" + Lastop + "' where statetag=3 and bannerid=" + Bannerid;
		else if (zt == 5)
			qry = "update vipbanner set statetag=2,lastdate=sysdate,lastop='" + Lastop + "' where statetag=1 and bannerid=" + Bannerid;
		else if (zt == 6)
			qry = "update vipbanner set statetag=0,lastdate=sysdate,refuse='" + Refuse + "',lastop='" + Lastop + "' where statetag=1 and bannerid=" + Bannerid;
		else {
			errmess = "操作参数错误:" + zt;
			return 0;
		}

		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}

}