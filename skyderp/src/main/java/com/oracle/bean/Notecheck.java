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
////import com.common.tool.SizeParam;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

//*************************************
//  @author:sunhong  @date:2017-04-13 08:52:50
//*************************************
public class Notecheck implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private Long Wareid;
	private Long Colorid;
	private Long Sizeid;
	private Float Amount;
	private Date Lastdate;
	private String Noteno;
	private String Tablename;
	private String Lastop;
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

	public void setNoteno(String noteno) {
		this.Noteno = noteno;
	}

	public String getNoteno() {
		return Noteno;
	}

	public void setTablename(String tablename) {
		this.Tablename = tablename;
	}

	public String getTablename() {
		return Tablename;
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
		// strSql.append(" update notecheck set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
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
		if (Id != null) {
			strSql1.append("id,");
			strSql2.append("'" + Id + "',");
		}
		if (Accid != null) {
			strSql1.append("accid,");
			strSql2.append(Accid + ",");
		}
		if (Wareid != null) {
			strSql1.append("wareid,");
			strSql2.append(Wareid + ",");
		}
		if (Colorid != null) {
			strSql1.append("colorid,");
			strSql2.append(Colorid + ",");
		}
		if (Sizeid != null) {
			strSql1.append("sizeid,");
			strSql2.append(Sizeid + ",");
		}
		if (Amount != null) {
			strSql1.append("amount,");
			strSql2.append("'" + Amount + "',");
		}
		if (Noteno != null) {
			strSql1.append("noteno,");
			strSql2.append("'" + Noteno + "',");
		}
		if (Tablename != null) {
			strSql1.append("Tablename,");
			strSql2.append("'" + Tablename + "',");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=notecheck_id.nextval; ");
		strSql.append("   insert into notecheck (");
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
		strSql.append("   update notecheck set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
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
		if (Noteno != null) {
			strSql.append("noteno='" + Noteno + "',");
		}
		if (Tablename != null) {
			strSql.append("Tablename='" + Tablename + "',");
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
		strSql.append(" FROM notecheck ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM notecheck ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取差异分页数据
	public Table GetTable(QueryParam qp, int xs) {// xs=1,只显示有差异的商品
		StringBuilder strSql = new StringBuilder();

		strSql.append(" select a.wareid,a.colorid,a.sizeid,b.warename,b.units,b.wareno,c.colorname,d.sizename");
		strSql.append(",sum(a.amount) as amount,sum(a.factamt) as factamt from (");
		strSql.append("\n select wareid,colorid,sizeid,amount as amount,0 as factamt");
		strSql.append("\n FROM " + Tablename + " where accid= " + Accid);
		strSql.append("\n and noteno='" + Noteno + "'");
		strSql.append("\n union all");
		strSql.append("\n select wareid,colorid,sizeid,0 as amount,amount as factamt");
		strSql.append("\n FROM notecheck where accid= " + Accid);
		strSql.append("\n and tablename='" + Tablename + "' and noteno='" + Noteno + "'");
		strSql.append(") a \n left outer join warecode b on a.wareid=b.wareid");
		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
		strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");

		strSql.append("\n group by a.wareid,a.colorid,a.sizeid,b.warename,b.units,b.wareno,c.colorname,d.sizename");
		if (xs == 1)
			strSql.append("\n having sum(a.amount)<>sum(a.factamt)");

		qp.setQueryString(strSql.toString());
		qp.setSumString("nvl(sum(amount),0) as totalamt,nvl(sum(factamt),0) as totalfactamt,nvl(sum(amount-factamt),0) as totalcyamt");
		qp.setCalcfield("amount-factamt as cyamt");
		qp.setSortString("wareid,colorid,sizeid");
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from notecheck");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 条码扫入
	public int doBarcodein(String barcode) {

		if (Func.isNull(Noteno) || Func.isNull(Tablename) || Func.isNull(barcode)) {
			errmess = "noteno或tablename或barcode无效！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n   v_accid  number;"; // --账户id
		qry += "\n   v_noteno  varchar2(20);"; // --单据号
		qry += "\n   v_barcode  varchar2(30); "; // --条码
		qry += "\n   v_amount  number; "; // --个数
		qry += "\n   v_amount0  number; "; // --个数
		// qry += "\n v_maxamt number; "; // --个数
		qry += "\n   v_wareid number(10); "; //
		qry += "\n   v_colorid number(10); "; //
		qry += "\n   v_sizeid number(10); "; //
		qry += "\n   v_barcode0  varchar2(30); "; // --条码
		qry += "\n   v_id  number(20); ";
		qry += "\n   v_id0  number(20); ";
		qry += "\n   v_count number; ";
		qry += "\n   v_totalamt number; ";
		qry += "\n   v_wareno varchar2(40); ";
		qry += "\n   v_warename varchar2(80); ";
		qry += "\n   v_colorname varchar2(40); ";
		qry += "\n   v_sizename varchar2(40); ";
		qry += "\n   v_sizegroupno  varchar2(60); "; // --条码
		qry += "\n   v_groupno  varchar2(60); "; // --条码
		qry += "\n   v_retcs  varchar2(100); "; // --条码
		qry += "\n begin ";
		// qry += "\nv_accid:=" + accid + "; ";
		// qry += "\nv_noteno:='" + noteno + "'; ";
		// qry += "\nv_barcode:='" + barcode + "'; ";

		qry += "\n   v_amount:=" + Amount + "; ";

		qry += "\n    v_count:=0;";
		if (barcode.length() >= 4) {
			qry += "\n   select nvl(min(id),0),nvl(min(barcode),''),count(*) into v_id,v_barcode0,v_count";
			qry += "\n   from (select id,barcode from warebarcode where barcode like '%" + barcode + "%' and accid=" + Accid + " and rownum<=2 ); ";//
		}
		qry += "\n   if v_count<>1 then";
		qry += "\n      begin";
		qry += "\n        select id,barcode into v_id,v_barcode0 from (select id,barcode from warebarcode where barcode=substr('" + barcode + "',1,length(barcode)) and accid=" + Accid
				+ " order by barcode desc )  where rownum=1; ";//
		qry += "\n      EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n        if v_count=0 then";
		qry += "\n           v_retcs:= '0 未找到条码:'||'" + barcode + "！' ;  ";
		qry += "\n        else ";
		qry += "\n           v_retcs:= '0 条码:'||'" + barcode + "不唯一！' ;  ";
		qry += "\n        end if;";
		// qry += "\n v_retcs:= '0 未找到条码:'||'" + barcode + "' ; ";
		qry += "\n        goto exit;";
		qry += "\n      end;";
		qry += "\n   end if;";

		qry += "\n   select a.wareid,a.colorid,a.sizeid,b.wareno,b.warename,c.colorname,d.sizename,b.sizegroupno,d.groupno  ";//
		qry += "\n     into v_wareid,v_colorid,v_sizeid,v_wareno,v_warename,v_colorname,v_sizename,v_sizegroupno,v_groupno  ";
		qry += "\n     from warebarcode a  ";
		qry += "\n     left outer join warecode b on a.wareid=b.wareid ";
		qry += "\n     left outer join colorcode c on a.colorid=c.colorid ";
		qry += "\n     left outer join sizecode d on a.sizeid=d.sizeid ";
		qry += "\n     where a.id=v_id  ";//
		// if (qxbj == 1) // 1 启用权限控制
		// qry += "\n and (b.brandid=0 or exists (select 1 from employebrand x where b.brandid=x.brandid and x.epid=" + Userid + "))";
		qry += "\n    ;";
		qry += "\n     if v_sizegroupno<>v_groupno then";
		qry += "\n        v_retcs:= '0商品尺码组异常，请检查！';  ";
		qry += "\n        goto exit;";
		qry += "\n     end if;";

		if (Tablename.equals("ALLOTINM")) {// 如果是调拨入库，不判断商品是否在单据中存在
//			qry += "\n     select nvl(sum(amount),0) into v_totalamt from " + Tablename + " where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=v_wareid and colorid=v_colorid and sizeid=v_sizeid;";
//			qry += "\n     if v_totalamt=0 then ";
//			qry += "\n        v_retcs:= '0该商品不能输入！';  ";
//			qry += "\n        goto exit;";
//			qry += "\n     end if;";
			qry += "\n     begin";
			qry += "\n       select id,amount into v_id,v_amount0 from notecheck where accid=" + Accid + " and noteno='" + Noteno + "' and Tablename='" + Tablename + "'";//
			qry += "\n       and wareid=v_wareid and colorid=v_colorid and sizeid=v_sizeid; ";//
//			qry += "\n       if v_amount0+v_amount>v_totalamt then";
//			qry += "\n          v_retcs:= '0商品数量已完成！';  ";
//			qry += "\n          goto exit;";
//			qry += "\n       end if;";
			qry += "\n       update notecheck set amount=amount+v_amount,lastdate=sysdate,lastop='" + Lastop + "' where id=v_id; ";
			qry += "\n     EXCEPTION WHEN NO_DATA_FOUND THEN ";//
//			qry += "\n       v_amount0:=0;";
//			qry += "\n       if v_amount0+v_amount>v_totalamt then";
//			qry += "\n          v_retcs:= '0商品数量已完成！';  ";
//			qry += "\n          goto exit;";
//			qry += "\n       end if;";
			qry += "\n       insert into notecheck (id,accid,noteno,tablename,wareid,colorid,sizeid,amount,lastdate,lastop) ";//
			qry += "\n       values (wareinm_id.nextval," + Accid + ",'" + Noteno + "','" + Tablename + "',v_wareid,v_colorid,v_sizeid,v_amount,sysdate,'" + Lastop + "'); ";//
			qry += "\n     end;  ";//

		} else {
			qry += "\n     select nvl(sum(amount),0) into v_totalamt from " + Tablename + " where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=v_wareid and colorid=v_colorid and sizeid=v_sizeid;";
			qry += "\n     if v_totalamt=0 then ";
			qry += "\n        v_retcs:= '0该商品不能输入！';  ";
			qry += "\n        goto exit;";
			qry += "\n     end if;";
			qry += "\n     begin";
			qry += "\n       select id,amount into v_id,v_amount0 from notecheck where accid=" + Accid + " and noteno='" + Noteno + "' and Tablename='" + Tablename + "'";//
			qry += "\n       and wareid=v_wareid and colorid=v_colorid and sizeid=v_sizeid; ";//
			qry += "\n       if v_amount0+v_amount>v_totalamt then";
			qry += "\n          v_retcs:= '0商品数量已完成！';  ";
			qry += "\n          goto exit;";
			qry += "\n       end if;";
			qry += "\n       update notecheck set amount=amount+v_amount,lastdate=sysdate,lastop='" + Lastop + "' where id=v_id; ";
			qry += "\n     EXCEPTION WHEN NO_DATA_FOUND THEN ";//
			qry += "\n       v_amount0:=0;";
			qry += "\n       if v_amount0+v_amount>v_totalamt then";
			qry += "\n          v_retcs:= '0商品数量已完成！';  ";
			qry += "\n          goto exit;";
			qry += "\n       end if;";
			qry += "\n       insert into notecheck (id,accid,noteno,tablename,wareid,colorid,sizeid,amount,lastdate,lastop) ";//
			qry += "\n       values (wareinm_id.nextval," + Accid + ",'" + Noteno + "','" + Tablename + "',v_wareid,v_colorid,v_sizeid,v_amount,sysdate,'" + Lastop + "'); ";//
			qry += "\n     end;  ";//
		}
		qry += "\n     select nvl(sum(amount),0) into v_totalamt from notecheck where accid=" + Accid + " and noteno='" + Noteno + "' and Tablename='" + Tablename + "'; ";

		qry += "\n     v_retcs:= '1 '||v_warename||':'||v_wareno||','||v_colorname||','||v_sizename||'  条码:'||v_barcode0 ; ";
		// 填 入条码跟踪表
		// qry += "\n if '" + barcode + "'>v_barcode0 then";
		// qry += "\n insert into warebarrecord (id,accid,barcode,noteid,noteno,amount,lastdate)";
		// qry += "\n values (warebarrecord_id.nextval," + Accid + ",'" + barcode + "',0,'" + Noteno + "',v_amount,sysdate); ";
		// qry += "\n end if;";
		qry += "\n     commit; ";
		qry += "\n     <<exit>> ";
		qry += "\n     select v_retcs into :retcs from dual; ";
		qry += "\n end;";
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

	public int doGetColorsumList(QueryParam qp, int sizenum) {

		if (Func.isNull(Noteno) || Func.isNull(Tablename)) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		String qry = "select a.wareid,b.wareno,b.warename,b.units,a.colorid,c.colorname";
		qry += ",sum(a.amount) as amount,min(a.id) as id";
		qry += "\n from notecheck a ";
		qry += "\n left outer join warecode b on a.wareid=b.wareid";
		qry += "\n left outer join colorcode c on a.colorid=c.colorid";
		qry += "\n where a.accid=" + Accid + " and a.noteno='" + Noteno + "' and a.tablename='" + Tablename + "'";
		qry += "\n group by a.wareid,b.wareno,b.warename,b.units,a.colorid,c.colorname";

		Table tb = new Table();
		qp.setQueryString(qry);
		qp.setSumString("nvl(sum(amount),0) as totalamt");
		tb = DbHelperSQL.GetTable(qp);
		int count = tb.getRowCount();
		String sqlsize = "";
		String sqlsum = "";
		String qry1;
		// long wareid;
		long wareid0 = 0;
		// String fh1="";
		String fh = "";
		// long colorid;
		String retcs = "{\"result\":1,\"msg\":\"操作成功！\"," + qp.getTotalString() + ",\"rows\":[";
		for (int i = 0; i < count; i++) {
			Wareid = Long.parseLong(tb.getRow(i).get("WAREID").toString());
			Colorid = Long.parseLong(tb.getRow(i).get("COLORID").toString());
			if (Wareid != wareid0) {
				SizeParam sp = new SizeParam(Wareid, sizenum);
				sqlsize = sp.getSqlSize();
				sqlsum = sp.getSqlSum();
				wareid0 = Wareid;
			}
			retcs += fh + "{";
			String fh1 = "";
			for (int j = 0; j < tb.getColumnCount(); j++) {
				String strKey = tb.getRow(i).getColumn(j + 1).getName().toUpperCase();
				String strValue = tb.getRow(i).get(j).toString();
				retcs += fh1 + "\"" + strKey + "\":\"" + strValue + "\"";
				// jsonString.append(fh1 + "\"" + strKey + "\":\"" + strValue + "\"");
				fh1 = ",";
			}

			// 取尺码数据
			// qry1 = "select " + sqlsum + "," + sqlsize;
			// qry1 += " from wareoutm a where accid=" + Accid + " and noteno='" + Noteno + "'";
			// qry1 += " and wareid=" + Wareid + " and colorid=" + Colorid;
			qry1 = "select " + sqlsum + "," + sqlsize;
			qry1 += " from notecheck a where accid=" + Accid + " and noteno='" + Noteno + "' and tablename='" + Tablename + "'";
			qry1 += " and wareid=" + Wareid + " and colorid=" + Colorid;

			Table tb1 = DbHelperSQL.Query(qry1).getTable(1);
			for (int j = 1; j <= sizenum; j++) {
				retcs += ",\"AMOUNT" + j + "\":\"" + tb1.getRow(0).get("AMOUNT" + j).toString() + "\"" //
						+ ",\"SIZEID" + j + "\":\"" + tb1.getRow(0).get("SIZEID" + j).toString() + "\"" //
						+ ",\"SIZENAME" + j + "\":\"" + tb1.getRow(0).get("SIZENAME" + j).toString() + "\"";
			}

			retcs += "}";
			fh = ",";

		}
		retcs += "]}";
		errmess = retcs;
		return 1;
	}

	// 清空捡货记录
	public int Clear() {
		if (Func.isNull(Noteno) || Func.isNull(Tablename)) {
			errmess = "noteno或tablename不是一个有效值！";
			return 0;
		}
		String Tablenameh = Func.subString(Tablename, 1, Tablename.length() - 1) + "h";
		String qry = "begin";
		qry += "\n    update " + Tablenameh + " set pickman='',lastdate=sysdate where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n    delete from notecheck where accid=" + Accid + " and noteno='" + Noteno + "' and tablename='" + Tablename + "';";
		qry += "\n    insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n    values (LOGRECORD_id.nextval," + Accid + ",'捡货管理','【清空捡货记录】单据号:" + Noteno + ";表名" + Tablename + "','" + Lastop + "');";
		qry += "\n end;";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	public int Over(int bj) {// bj=1,要替换
		if (Func.isNull(Noteno) || Func.isNull(Tablename)) {
			errmess = "noteno或tablename不是一个有效值！";
			return 0;
		}
		String qry = "declare";
		qry += "\n  v_totalamt number;";
		qry += "\n  v_totalcurr number;";
		qry += "\n  v_count number;";
		qry += "\n  v_amount number;";
		qry += "\n  v_id number;";
		qry += "\n begin";

		String Tablenameh = Func.subString(Tablename, 1, Tablename.length() - 1) + "h";
		if (bj == 0) {
			qry += "\n   update " + Tablenameh + " set pickman='" + Lastop + "' where accid=" + Accid + " and noteno='" + Noteno + "';";
		} else {// 1=要替换
			qry += "\n   update notecheck set amount0=0 where accid=" + Accid + " and noteno='" + Noteno + "' and tablename='" + Tablename + "';";
			qry += "\n   declare cursor cur_data is";
			qry += "\n      select wareid,colorid,sizeid,id,amount from " + Tablename + " where accid=" + Accid + " and noteno='" + Noteno + "'";
			qry += "\n      order by id;";
			qry += "\n      v_row cur_data%rowtype;";
			qry += "\n   begin";
			qry += "\n      v_count:=0;";
			qry += "\n      for v_row in cur_data loop";
			qry += "\n         begin";
			qry += "\n           select amount-amount0,id into v_amount,v_id from notecheck where accid=" + Accid + " and noteno='" + Noteno + "' and tablename='" + Tablename + "'";
			qry += "\n           and wareid=v_row.wareid and colorid=v_row.colorid and sizeid=v_row.sizeid; ";
			qry += "\n         EXCEPTION WHEN NO_DATA_FOUND THEN";
			qry += "\n           v_amount:=0;v_id:=0;";
			qry += "\n         end;";
			qry += "\n         if v_row.amount<=v_amount then";// 出库数小于捡货数
			qry += "\n            update notecheck set amount0=amount0+v_row.amount where id=v_id;";
			qry += "\n         else ";
			qry += "\n            update " + Tablename + " set amount=v_amount,curr=round(v_amount*price,2) where id=v_row.id;";
			qry += "\n            update notecheck set amount0=amount0+v_amount where id=v_id;";

			qry += "\n         end if;";
			qry += "\n         v_count:=v_count+1;";
			qry += "\n         if v_count>1000 then ";
			qry += "\n            commit;";
			qry += "\n            v_count:=0;";
			qry += "\n         end if;";
			qry += "\n      end loop;";
			qry += "\n      commit;";
			qry += "\n   end;";

			qry += "\n   delete from " + Tablename + " where accid=" + Accid + " and noteno='" + Noteno + "' and amount<=0;";
			qry += "\n   select nvl(sum(curr),0),nvl(sum(amount),0)  into v_totalcurr,v_totalamt from " + Tablename + "  where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   update " + Tablenameh + " set totalcurr=v_totalcurr,totalamt=v_totalamt,pickman='" + Lastop + "',lastdate=sysdate where accid=" + Accid + " and noteno='" + Noteno + "';";

		}
		qry += "\n end;";

		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}
}