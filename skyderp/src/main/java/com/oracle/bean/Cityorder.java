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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-17 20:32:09
//*************************************
public class Cityorder implements java.io.Serializable {
	private Float Id;
	private Long Cthouseid;
	private Long Ordaccid;
	private Long Wareid;
	private Long Colorid;
	private Long Sizeid;
	private Float Amount;
	private Float Price;
	private Float Curr;
	private Date Lastdate;
	private String Lastop;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setCthouseid(Long cthouseid) {
		this.Cthouseid = cthouseid;
	}

	public Long getCthouseid() {
		return Cthouseid;
	}

	public void setOrdaccid(Long ordaccid) {
		this.Ordaccid = ordaccid;
	}

	public Long getOrdaccid() {
		return Ordaccid;
	}

	public void setWareid(Long wareid) {
		this.Wareid = wareid;
	}

	public Long getWareid() {
		return Wareid;
	}

	public void setColorid(Long colorid) {
		this.Colorid = colorid;
	}

	public Long getColorid() {
		return Colorid;
	}

	public void setSizeid(Long sizeid) {
		this.Sizeid = sizeid;
	}

	public Long getSizeid() {
		return Sizeid;
	}

	public void setAmount(Float amount) {
		this.Amount = amount;
	}

	public Float getAmount() {
		return Amount;
	}

	public void setPrice(Float price) {
		this.Price = price;
	}

	public Float getPrice() {
		return Price;
	}

	public void setCurr(Float curr) {
		this.Curr = curr;
	}

	public Float getCurr() {
		return Curr;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// 删除记录
	public int Delete() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("delete from cityorder where ordaccid=" + Ordaccid + " and cthouseid=" + Cthouseid + " and wareid=" + Wareid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append(JSONObject jsonObject, long accid) {
		if (Cthouseid == 0 || Wareid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		if (!jsonObject.has("amountlist")) {
			errmess = "订货商品信息无效！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n    v_id  number;";
		qry += "\n    v_cityaccid  number;";
		qry += "\n    v_retcs  varchar2(100);";
		qry += "\n    v_retbj  number(1);";
		qry += "\n    v_totalamt number;";
		qry += "\n    v_totalcurr number;";
		qry += "\n begin ";
		qry += "\n    select accid into v_cityaccid from cityhouse where cthouseid=" + Cthouseid + ";";
		qry += "\n    if v_cityaccid=" + accid + " then ";
		qry += "\n       v_retbj:=0; v_retcs:='\"msg\":\"您不能在自已的商城订货！\"';";
		qry += "\n       goto exit;";
		qry += "\n    end if;";
		JSONArray jsonArray = jsonObject.getJSONArray("amountlist");

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			Sizeid = Long.parseLong(jsonObject2.getString("sizeid"));
			Colorid = Long.parseLong(jsonObject2.getString("colorid"));
			Amount = Float.parseFloat(jsonObject2.getString("amount"));
			Curr =Func.getRound(Amount * Price,2);
			qry += "\n  begin ";
			qry += "\n    select id into v_id from cityorder where ordaccid=" + accid + " and cthouseid=" + Cthouseid + " and wareid=" + Wareid + " and colorid=" + Colorid + " and sizeid=" + Sizeid + " and rownum=1; ";
			qry += "\n    update cityorder set amount=amount+" + Amount + ",curr=(amount+" + Amount + ")*price,lastdate=sysdate,lastop='" + Lastop + "' where id=v_id and cthouseid=" + Cthouseid + ";";// +" and wareid=" + wareid + " and colorid=" + colorid + " and sizeid=" + sizeid + ";";
			qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN"; // 未找到，插入新记录
			qry += "\n    insert into cityorder (id,ordaccid,cthouseid,wareid,colorid,sizeid,amount,price,curr,lastdate,lastop)";
			qry += "\n    values (cityorder_id.nextval," + accid + "," + Cthouseid + "," + Wareid + "," + Colorid + "," + Sizeid + "," + Amount + "," + Price + "," + Curr + ",sysdate,'" + Lastop + "');";
			qry += "\n end; ";
		}
		qry += "\n  commit; ";
		qry += "\n  select  nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from cityorder where ordaccid=" + accid + " and cthouseid=" + Cthouseid + " and wareid=" + Wareid + "; ";
		// qry += "\n v_retbj:=1; v_retcs:= '\"msg\":\"'||v_totalamt||'\",\"TOTALAMT\":\"'||v_totalamt||'\",\"TOTALCURR\":\"'||v_totalcurr||'\"'; ";
		qry += "\n  v_retbj:=1; v_retcs:= '\"msg\":\"操作完成！\",\"TOTALAMT\":\"'||v_totalamt||'\",\"TOTALCURR\":\"'||v_totalcurr||'\"';  ";
		qry += "\n  <<exit>>";
		qry += "\n  select v_retbj,v_retcs into :retbj,:retcs from dual;";
		qry += "\n end;";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retbj", new ProdParam(Types.INTEGER));
		param.put("retcs", new ProdParam(Types.VARCHAR));

		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {

			errmess = param.get("retcs").getParamvalue().toString();
			return Integer.parseInt(param.get("retbj").getParamvalue().toString());
		}

		// StringBuilder strSql = new StringBuilder();
		// StringBuilder strSql1 = new StringBuilder();
		// StringBuilder strSql2 = new StringBuilder();
		// strSql1.append("id,");
		// strSql2.append("v_id,");
		// if (Id != null) {
		// strSql1.append("id,");
		// strSql2.append("'" + Id + "',");
		// }
		// if (Cthouseid != null) {
		// strSql1.append("cthouseid,");
		// strSql2.append(Cthouseid + ",");
		// }
		// if (Ordaccid != null) {
		// strSql1.append("ordaccid,");
		// strSql2.append(Ordaccid + ",");
		// }
		// if (Wareid != null) {
		// strSql1.append("wareid,");
		// strSql2.append(Wareid + ",");
		// }
		// if (Colorid != null) {
		// strSql1.append("colorid,");
		// strSql2.append(Colorid + ",");
		// }
		// if (Sizeid != null) {
		// strSql1.append("sizeid,");
		// strSql2.append(Sizeid + ",");
		// }
		// if (Amount != null) {
		// strSql1.append("amount,");
		// strSql2.append("'" + Amount + "',");
		// }
		// if (Price != null) {
		// strSql1.append("price,");
		// strSql2.append("'" + Price + "',");
		// }
		// if (Curr != null) {
		// strSql1.append("curr,");
		// strSql2.append("'" + Curr + "',");
		// }
		// if (Lastop != null) {
		// strSql1.append("lastop,");
		// strSql2.append("'" + Lastop + "',");
		// }
		// strSql1.append("lastdate");
		// strSql2.append("sysdate");
		// strSql.append("declare ");
		// strSql.append(" v_id number; ");
		// strSql.append(" begin ");
		// strSql.append(" v_id:=Cityorder_id.nextval; ");
		// strSql.append(" insert into Cityorder (");
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
		// if (ret < 0) {
		// errmess = "操作异常！";
		// return 0;
		// } else {
		// errmess = "操作成功！";
		// return 1;
		// }
	}

	// 增加记录
	public int Append1(JSONObject jsonObject, long accid) {
		if (Cthouseid == 0 || Wareid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		if (!jsonObject.has("amountlist")) {
			errmess = "订货商品信息无效！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n    v_id  number;";
		qry += "\n    v_cityaccid  number;";
		qry += "\n    v_retcs  varchar2(100);";
		qry += "\n    v_retbj  number(1);";
		qry += "\n    v_totalamt number;";
		qry += "\n    v_totalcurr number;";
		qry += "\n begin ";
		qry += "\n    select accid into v_cityaccid from cityhouse where cthouseid=" + Cthouseid + ";";
		qry += "\n    if v_cityaccid=" + accid + " then ";
		qry += "\n       v_retbj:=0;  v_retcs:='\"msg\":\"您不能在自已的商城订货！\"' ;";
		qry += "\n       return;";
		qry += "\n    end if;";

		qry += "\n    delete from cityorder where ordaccid=" + accid + " and cthouseid=" + Cthouseid + " and wareid=" + Wareid + "; ";
		JSONArray jsonArray = jsonObject.getJSONArray("amountlist");

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			Sizeid = Long.parseLong(jsonObject2.getString("sizeid"));
			Colorid = Long.parseLong(jsonObject2.getString("colorid"));
			Amount = Float.parseFloat(jsonObject2.getString("amount"));
			Curr =Func.getRound(Amount * Price,2);
			qry += "\n    insert into cityorder (id,ordaccid,cthouseid,wareid,colorid,sizeid,amount,price,curr,lastdate,lastop)";
			qry += "\n    values (cityorder_id.nextval," + accid + "," + Cthouseid + "," + Wareid + "," + Colorid + "," + Sizeid + "," + Amount + "," + Price + "," + Curr + ",sysdate,'" + Lastop + "');";
		}
		// qry += "\n commit; ";
		qry += "\n  select  nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from cityorder where ordaccid=" + accid + " and cthouseid=" + Cthouseid + " and wareid=" + Wareid + "; ";
		// qry += "\n v_retbj:=1; v_retcs:= '\"msg\":\"'||v_totalamt||'\",\"TOTALAMT\":\"'||v_totalamt||'\",\"TOTALCURR\":\"'||v_totalcurr||'\"'; ";
		qry += "\n  v_retbj:=1; v_retcs:= '\"msg\":\"操作完成！\",\"TOTALAMT\":\"'||v_totalamt||'\",\"TOTALCURR\":\"'||v_totalcurr||'\"';  ";

		qry += "\n  <<exit>>";
		qry += "\n  select v_retbj,v_retcs into :retbj,:retcs from dual;";
		qry += "\n end;";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retbj", new ProdParam(Types.INTEGER));
		param.put("retcs", new ProdParam(Types.VARCHAR));

		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {

			errmess = param.get("retcs").getParamvalue().toString();
			return Integer.parseInt(param.get("retbj").getParamvalue().toString());
		}

	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Cityorder set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Cthouseid != null) {
			strSql.append("cthouseid=" + Cthouseid + ",");
		}
		if (Ordaccid != null) {
			strSql.append("ordaccid=" + Ordaccid + ",");
		}
		if (Wareid != null) {
			strSql.append("wareid=" + Wareid + ",");
		}
		if (Colorid != null) {
			strSql.append("colorid=" + Colorid + ",");
		}
		if (Sizeid != null) {
			strSql.append("sizeid=" + Sizeid + ",");
		}
		if (Amount != null) {
			strSql.append("amount='" + Amount + "',");
		}
		if (Price != null) {
			strSql.append("price='" + Price + "',");
		}
		if (Curr != null) {
			strSql.append("curr='" + Curr + "',");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
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
		strSql.append(" FROM Cityorder ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Cityorder ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp) {
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Cityorder");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	public int doCityWareToOrder(long accid, int priceprec) {

		String qry = "declare  ";
		qry += "\n    v_paccid number; ";
		qry += "\n    v_provid number; ";
		qry += "\n    v_totalcurr number; ";
		qry += "\n    v_totalamt number; ";
		qry += "\n    v_id number; ";
		qry += "\n    v_noteno varchar2(20); ";
		qry += "\n    v_discount number; ";
		qry += "\n    v_price0 number; ";
		qry += "\n    v_count number; ";
		qry += "\n    v_wareid number(10); ";
		qry += "\n    v_colorid number(10); ";
		qry += "\n    v_sizeid number(10); ";
		qry += "\n    v_wareid1 number(10); ";
		qry += "\n    v_wareid0 number(10); ";
		qry += "\n    v_colorid0 number(10); ";
		qry += "\n    v_sizeid0 number(10); ";

		qry += "\n    v_amount number; ";
		qry += "\n    v_price number; ";
		qry += "\n    v_curr number; ";
		qry += "\n    v_retailsale number; ";
		// qry += "\n v_price0 number; ";
		// qry += "\n v_discount number; ";
		qry += "\n    v_pricetype number(1); ";
		qry += "\n    v_cthousename varchar2(100); ";
		qry += "\n    v_retcs varchar2(200); ";
		qry += "\n  begin ";
		// qry += "\n select accid into v_paccid from cityhouse where cthouseid=" + cthouseid + ";";
		// qry += "\n v_paccid:=" + paccid + ";";

		qry += "\n    begin ";
		qry += "\n      select accid,cthousename into v_paccid,v_cthousename from cityhouse where cthouseid=" + Cthouseid + ";"; // 商城的accid
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN  ";
		qry += "\n      v_retcs:= '0未找到商城账户！' ; ";
		qry += "\n      goto exit; ";
		qry += "\n    end; ";
		// --查询商城账户在经销商账户中对应的供应商id
		qry += "\n    begin ";
		qry += "\n      select a.buyprovid,b.pricetype,b.discount into v_provid,v_pricetype,v_discount ";
		qry += "\n      from accconnect a join provide b on a.buyprovid=b.provid where a.sellaccid=v_paccid and a.buyaccid=" + accid + " ; ";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN  ";
		qry += "\n      v_retcs:= '0您在商城账户的加盟信息未建立，请联系商城客服！' ; ";
		qry += "\n      goto exit; ";
		qry += "\n    end; ";
		qry += "\n    select count(*) into v_count from cityorder where cthouseid=" + Cthouseid + " and ordaccid=" + accid + " and amount>0;";
		qry += "\n    if v_count=0 then";
		qry += "\n       v_retcs:= '0购物车商品不存在！'; ";
		qry += "\n       goto exit; ";
		qry += "\n    end if;";
		// --产生采购订单
		qry += "\n  begin ";
		qry += "\n    select 'CO'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from provorderh ";
		qry += "\n    where accid=" + accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'CO'||To_Char(SYSDATE,'yyyymmdd'); ";
		qry += "\n    v_id:=provorderH_ID.NEXTVAL; ";
		qry += "\n    insert into provorderh (id, accid,noteno,notedate,provid,cthouseid,statetag,totalamt,totalcurr,remark,handno,operant,checkman,lastdate) ";
		qry += "\n    values (v_id," + accid + ",v_noteno,sysdate,v_provid," + Cthouseid + ",1,0,0,v_cthousename,'','" + Lastop + "','',sysdate); ";
		// --插入订货会商品明细记录

		qry += "\n      declare cursor cur_data is ";
		qry += "\n         select a.wareid,a.colorid,a.sizeid,a.amount,a.price,a.curr,b.retailsale";
		qry += "\n         from cityorder a join warecode b on a.wareid=b.wareid where a.cthouseid=" + Cthouseid + " and a.ordaccid=" + accid;
		qry += "\n         and a.amount>0 order by a.wareid; ";
		qry += "\n      begin";

		qry += "\n         open cur_data;";
		qry += "\n         fetch cur_data into v_wareid,v_colorid,v_sizeid,v_amount,v_price,v_curr,v_retailsale;";
		qry += "\n         while cur_data%found loop";
		qry += "\n            v_wareid1:=v_wareid;";
		qry += "\n            p_downsellerware0(" + accid + ",v_wareid," + priceprec + ");"; // 同步商品编码
		qry += "\n            begin"; // 查询商品id
		qry += "\n              select wareid into v_wareid0 from warecode where accid=" + accid + " and accid1=v_paccid and wareid1=v_wareid;";
		qry += "\n            EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n              v_wareid0:=0;";
		qry += "\n            end;";
		qry += "\n            if v_retailsale=0 then "; // 计算原价
		qry += "\n               v_price0:=v_price;";
		qry += "\n               v_discount:=1;";
		qry += "\n            else ";
		qry += "\n               v_price0:=v_retailsale;";
		qry += "\n               v_discount:=round(v_price/v_price0,2);";
		qry += "\n            end if;";

		qry += "\n            while cur_data%found and v_wareid=v_wareid1 loop";
		qry += "\n               begin"; // 查询颜色id
		qry += "\n                 select colorid into v_colorid0 from colorcode where accid=" + accid + " and accid1=v_paccid and colorid1=v_colorid;";
		qry += "\n               EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n                 v_colorid0:=0;";
		qry += "\n               end;";
		qry += "\n               begin"; // 查询尺码id
		qry += "\n                 select sizeid into v_sizeid0 from sizecode where accid=" + accid + " and accid1=v_paccid and sizeid1=v_sizeid;";
		qry += "\n               EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n                 v_sizeid0:=0;";
		qry += "\n               end;";
		qry += "\n               if v_wareid0>0 and v_colorid0>0 and v_sizeid0>0 then ";
		qry += "\n                  insert into provorderm (id,accid,noteno,wareid,colorid,sizeid,amount,price0,discount,price,curr,remark0) ";
		qry += "\n                  values (provorderm_id.nextval," + accid + ",v_noteno,v_wareid0,v_colorid0,v_sizeid0,v_amount,v_price0,v_discount,v_price,v_curr,'');";
		qry += "\n               end if;";
		qry += "\n               fetch cur_data into v_wareid,v_colorid,v_sizeid,v_amount,v_price,v_curr,v_retailsale;";
		qry += "\n            end loop;";
		qry += "\n         end loop;";
		qry += "\n         close cur_data;";
		qry += "\n      end;";

		qry += "\n      select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from provorderm where accid=" + accid + " and noteno=v_noteno;  ";
		qry += "\n      update provorderh set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + accid + " and noteno=v_noteno; ";
		qry += "\n      delete from cityorder where cthouseid=" + Cthouseid + " and ordaccid=" + accid + ";";
		// qry += "\n select '1'||noteno into :retcs from provorderh where id=v_id; ";
		qry += "\n      v_retcs:='1'||v_noteno; ";
		qry += "\n    exception ";
		qry += "\n    when others then  ";
		qry += "\n    begin ";
		qry += "\n      rollback; "; // 回滚
		qry += "\n      v_retcs:= '0生成采购订单失败' ; ";
		qry += "\n    end; ";
		qry += "\n  end; ";
		qry += "\n  <<exit>>";
		qry += "\n  select v_retcs into :retcs from dual;";
		qry += "\n end; ";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

}