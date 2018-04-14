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
//  @author:sunhong  @date:2017-02-22 08:52:52
//*************************************
public class Backprint implements java.io.Serializable {
	private Float Id;
	private Long Userid;
	private Long Accid;
	private Long Progid;
	private Long Houseid;
	private String Noteno;
	private Date Lastdate;
	private String Lastop;
	private Integer Printok;
	private Long Noteid;
	private Long Prtid;
	private String errmess;
	private String Tablename;

	private Integer Sortfs;
	private Integer Hzfs;
	private Integer Priceprec;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setId(Float id) {
		this.Id = id;
	}

	public void setSortfs(Integer sortfs) {
		Sortfs = sortfs;
	}

	public void setHzfs(Integer hzfs) {
		Hzfs = hzfs;
	}

	public void setPriceprec(Integer priceprec) {
		Priceprec = priceprec;
	}

	//
	// public Float getId() {
	// return Id;
	// }
	//
	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public void setUserid(Long userid) {
		this.Userid = userid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setProgid(Long progid) {
		this.Progid = progid;
	}

	public Long getProgid() {
		return Progid;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public void setNoteno(String noteno) {
		this.Noteno = noteno;
	}

	public void setTablename(String tablename) {
		this.Tablename = tablename;
	}

	public String getNoteno() {
		return Noteno;
	}

	public void setLastdate(Date lastdate) {
		this.Lastdate = lastdate;
	}

	public String getLastdate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Lastdate);
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setPrintok(Integer printok) {
		this.Printok = printok;
	}

	public Integer getPrintok() {
		return Printok;
	}

	public void setNoteid(Long noteid) {
		this.Noteid = noteid;
	}

	public Long getNoteid() {
		return Noteid;
	}

	public void setPrtid(Long prtid) {
		this.Prtid = prtid;
	}

	public Long getPrtid() {
		return Prtid;
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
		// // strSql.append("select wareid from warecode where id=" + id + " and
		// rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		strSql.append(" delete from backprint ");
		strSql.append(" where id=" + Id);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append(long userid) {
		// StringBuilder strSql = new StringBuilder();
		// progid:1301客户订单, 1201零售开票, 1302 批发开票, 1303 批发退库
		// ,1101采购订单,1102采购入库,1103采购退货,1401 调拨订单
		// ,1402调拨出库,1403调拨入库,1501临时盘点,1502盘点管理,1508 期初入库建账 , 1210商场零售
		if (Houseid == null)
			Houseid = (long) 0;
		if (Noteid == null)
			Noteid = (long) 0;

		if (Progid == 1209)
			Progid = (long) 1208; // 1209查询收银交班表，改为1208收银交班表 1607 销售收款

		if (Progid == 1015) // 如果是条码打印，noteid取值用户id
		{
			Noteid = userid;
			Noteno = "USER" + userid;// lastop;
		} else if (Progid == 1208 || Progid == 9001) // 1208交班表 noteid=classid
		{
			// noteid = userid;
			Noteno = "USER" + userid;// lastop;
		}

		if (Progid == 0 || Noteid == 0) // 后台打印2.0后 不需要参数houseid == 0
		{
			errmess = "progid或noteid参数无效1！";
			return 0;
		}
		if (Noteno == null || Noteno.equals("")) {
			errmess = "参数无效2！";
			return 0;

		}
		String qry = "declare ";
		qry += "\n    v_count number(10); ";
		qry += "\n    v_prtid number(10); ";
		qry += "\n    v_retcs varchar2(200); ";
		qry += "\n  begin";
		qry += "\n    begin  ";
		qry += "\n      select prtid into v_prtid from PROGPRINT where epid=" + userid + " and progid=" + Progid + "; "; // 取默认打印机
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n      v_prtid:=0; ";
		qry += "\n    end;";
		qry += "\n    if v_prtid=0 then ";
		qry += "\n       v_retcs:='1未找到后台默认打印机，请先设置...' ;"; // 暂时注释
		qry += "\n       goto exit; ";
		qry += "\n    end if;";

		qry += "\n    select count(*) into v_count from backprint where accid=" + Accid + " and progid=" + Progid + " and noteno='" + Noteno + "' and prtid=v_prtid; ";
		qry += "\n    if v_count=0 then ";
		qry += "\n       insert into backprint(id,accid,progid,houseid,noteno,noteid,prtid,lastop)";
		qry += "\n       values (backprint_id.nextval," + Accid + "," + Progid + "," + Houseid + ",'" + Noteno + "'," + Noteid + ",v_prtid,'" + Lastop + "'); ";
		// 1201零售开票, 1302 批发开票, 1303 批发退库
		// if (Progid == 1201 || Progid == 1302 || Progid == 1303) {//打印次数
		// qry += "\n update wareouth set prtnum=prtnum+1 where accid=" + Accid
		// + " and noteno='" + Noteno + "';";
		qry += "\n       v_retcs:= '1单据已传送到后台打印队列，稍候开始打印...' ;";
		qry += "\n    else ";
		qry += "\n       v_retcs:= '1单据已存在于后台打印队列，不能重复发送。' ;";
		qry += "\n    end if;";
		qry += "\n    <<exit>>";
		qry += "\n    select v_retcs into :retcs from dual;";
		qry += "\n  end; ";
		// System.out.println(qry);
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

		// Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		// param.put("retcs", new ProdParam(Types.BIGINT));
		// int ret = DbHelperSQL.ExecuteProc(qry, param);
		// if (ret >= 0) {
		// String retcs = param.get("retcs").getParamvalue().toString();
		// errmess = retcs.substring(1);
		//
		// if (retcs.substring(0, 1).equals("0"))
		// return 0;
		// else
		// return 1;
		// } else {
		// errmess = "操作异常！";
		// return 0;
		// }
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update backprint set ");
		if (Id != null) {
			strSql.append("id=" + Id + ",");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Progid != null) {
			strSql.append("progid=" + Progid + ",");
		}
		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
		}
		if (Noteno != null) {
			strSql.append("noteno='" + Noteno + "',");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Printok != null) {
			strSql.append("printok=" + Printok + ",");
		}
		if (Noteid != null) {
			strSql.append("noteid=" + Noteid + ",");
		}
		if (Prtid != null) {
			strSql.append("prtid=" + Prtid + ",");
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
		strSql.append(" FROM backprint ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM backprint ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据 尺码横向展开
	public Table GetTable1(QueryParam qp) {
		String qry = "";
		if (qp.getPageIndex() == 1) {
			if (Tablename.equals("WAREOUTM")) {
				qry = " update wareouth set prtnum=prtnum+1 where  accid=" + Accid + " and noteno='" + Noteno + "'";
				DbHelperSQL.ExecuteSql(qry);
			} else if (Tablename.equals("WAREPEIM")) {
				qry = " update warepeih set prtnum=prtnum+1 where  accid=" + Accid + " and noteno='" + Noteno + "'";
				DbHelperSQL.ExecuteSql(qry);
			}
		}
		// return;
		qry = "select a.wareid,a.colorid,a.amount,a.price,a.curr,c.wareno,c.warename,c.tjtag,c.units,c.model,c.RETAILSALE,c.locale,c.sizegroupno";
		qry += ",c.ssdate,c.gbbar,c.prodno,d.colorname,e.brandname,g.typename,g1.typename as typename0";
		if (Tablename.equals("WAREINM") || Tablename.equals("WAREOUTM") || Tablename.equals("WAREPEIM") || Tablename.equals("SHOPSALEM")//
				|| Tablename.equals("PROVORDERM") || Tablename.equals("CUSTORDERM") || Tablename.equals("REFUNDOUTM"))
			qry += ",a.price0,a.discount";// ,a.curr0
		if (Tablename.equals("WAREOUTM") || Tablename.equals("WAREPEIM"))
			qry += " ,f.salename";
		if (Tablename.equals("WARETOUTM"))
			qry += ",";
		qry += ",a.amount1,a.amount2,a.amount3,a.amount4,a.amount5,a.amount6,a.amount7,a.amount8,a.amount9,a.amount10,a.amount11,a.amount12,a.amount13,a.amount14,a.amount15";
		qry += ",b.size1,b.size2,b.size3,b.size4,b.size5,b.size6,b.size7,b.size8,b.size9,b.size10,b.size11,b.size12,b.size13,b.size14,b.size15,a.id,a.remark0,a.minid,r.areaname";
		qry += "\n from v_notesize_data a ";
		qry += "\n join v_notesize1_data b on a.wareid=b.wareid and a.epid=b.epid";
		qry += "\n left outer join warecode c on a.wareid=c.wareid";
		qry += "\n left outer join area r on c.areaid=r.areaid";
		qry += "\n left outer join colorcode d on a.colorid=d.colorid";
		qry += "\n left outer join brand e on c.brandid=e.brandid";
		qry += "\n left outer join waretype g on c.typeid=g.typeid";
		qry += "\n left outer join waretype g1 on g.p_typeid=g1.typeid";
		if (Tablename.equals("WAREOUTM") || Tablename.equals("WAREPEIM"))
			qry += "\n left outer join salecode f on a.saleid=f.saleid";
		qry += "\n where a.epid=" + Userid;

		String sort = "";
		// sortfs:0=货号 1=输入，2=数量,3=区域,4=品牌
		if (Sortfs == 4)
			sort = " brandname,sizegroupno,wareno,wareid,colorid,id ";// 货号顺序
		else if (Sortfs == 3)
			sort = " areaname,sizegroupno,wareno,wareid,colorid,id ";// 货号顺序
		else if (Sortfs == 0)
			sort = " sizegroupno,wareno,wareid,colorid,id ";// 货号顺序
		else if (Sortfs == 1)
			sort = " minid,sizegroupno,wareno,wareid,colorid "; // 录入排序
		else
			sort = " amount,sizegroupno,wareno,wareid,colorid,id "; // 录入排序
		qp.setQueryString(qry);
		qp.setSortString(sort);
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp) {
		String qry = "";
		if (qp.getPageIndex() == 1) {
			if (Tablename.equals("WAREOUTM")) {
				qry = " update wareouth set prtnum=prtnum+1 where  accid=" + Accid + " and noteno='" + Noteno + "'";
				DbHelperSQL.ExecuteSql(qry);
			} else if (Tablename.equals("WAREPEIM")) {
				qry = " update warepeih set prtnum=prtnum+1 where  accid=" + Accid + " and noteno='" + Noteno + "'";
				DbHelperSQL.ExecuteSql(qry);
			}
		}
		String sort = "";
		// sortfs:0=货号 1=输入，2=数量,3=区域，4=品牌
		if (Sortfs == 4) {// 货号顺序
			if (Hzfs == 0) // hzfs: 0=货号+颜色+尺码 1=货号+颜色 2=货号
				sort = "brandname,wareno,wareid,colorid,sizeid,id";
			else if (Hzfs == 1)
				sort = "brandname,wareno,wareid,colorid,id";
			else
				sort = "brandname,wareno,wareid,id";
		} else if (Sortfs == 3) {// 货号顺序
			if (Hzfs == 0) // hzfs: 0=货号+颜色+尺码 1=货号+颜色 2=货号
				sort = "areaname,wareno,wareid,colorid,sizeid,id";
			else if (Hzfs == 1)
				sort = "areaname,wareno,wareid,colorid,id";
			else
				sort = "areaname,wareno,wareid,id";
		} else if (Sortfs == 0) {// 货号顺序
			if (Hzfs == 0) // hzfs: 0=货号+颜色+尺码 1=货号+颜色 2=货号
				sort = "wareno,wareid,colorid,sizeid,id";
			else if (Hzfs == 1)
				sort = "wareno,wareid,colorid,id";
			else
				sort = "wareno,wareid,id";
		} else if (Sortfs == 1) {// 输入顺序)
			if (Hzfs == 0)
				sort = "id,wareno,wareid,colorid,sizeid";
			else if (Hzfs == 1)
				sort = "id,wareno,wareid,colorid";
			else
				sort = "id,wareno,wareid";
		} else {// 数量
			if (Hzfs == 0)
				sort = "amount,wareno,wareid,colorid,sizeid,id";
			else if (Hzfs == 1)
				sort = "amount,wareno,wareid,colorid,id";
			else
				sort = "amount,wareno,wareid,id";

		}

		if (Hzfs == 0) {
			qry = "select A.ID,a.WAREID,a.COLORID,a.SIZEID,a.AMOUNT,a.PRICE,a.CURR,B.WARENO,B.WARENAME,B.UNITS,b.model,B.RETAILSALE,b.sale1";
			qry += ",B.LOCALE,C.COLORNAME,D.SIZENAME,B.GBBAR,b.ssdate,b.prodno,b.sizegroupno,e.brandname,t.typename,t1.typename as typename0,a.remark0,r.areaname";
			if (Tablename.equals("WAREINM") || Tablename.equals("WAREOUTM") || Tablename.equals("WAREPEIM")//
					|| Tablename.equals("PROVORDERM") || Tablename.equals("CUSTORDERM") || Tablename.equals("SHOPSALEM")//
					|| Tablename.equals("REFUNDOUTM"))
				qry += ",a.PRICE0,a.DISCOUNT";
			else if (Tablename.equals("WARECHECKM"))
				qry += ",A.CHECKAMT,A.BOOKAMT";
			if (Tablename.equals("WAREOUTM") || Tablename.equals("WAREPEIM"))
				qry += ",A.SALEID,F.SALENAME";
			else if (Tablename.equals("SHOPSALEM"))
				qry += ",A.HOUSEID,F.HOUSENAME,g.epname as salemanname";

			if (Tablename.equals("WAREOUTM") || Tablename.equals("SHOPSALEM"))
				qry += ",a.tjtag";
			else
				qry += ",0 as tjtag";
			qry += ",f_getwarebarcode(A.WAREID,A.COLORID,A.SIZEID) as barcode";
			qry += "\n FROM " + Tablename + " a";
			qry += "\n left outer join warecode b on a.wareid=b.wareid";
			qry += "\n left outer join area r on b.areaid=r.areaid";
			qry += "\n left outer join colorcode c on a.colorid=c.colorid";
			qry += "\n left outer join sizecode d on a.sizeid=d.sizeid";
			qry += "\n left outer join brand e on b.brandid=e.brandid";
			qry += "\n left outer join waretype t on b.typeid=t.typeid";
			qry += "\n left outer join waretype t1 on t.p_typeid=t1.typeid";
			if (Tablename.equals("WAREOUTM") || Tablename.equals("WAREPEIM"))
				qry += "\n left outer join salecode f on a.saleid=f.saleid";
			else if (Tablename.equals("SHOPSALEM")) {
				qry += "\n left outer join warehouse f on a.houseid=f.houseid";
				qry += "\n left outer join employe g on a.salemanid=g.epid";
			}
			qry += "\n where a.accid=" + Accid + " and a.noteno='" + Noteno + "'";
		} else {
			qry = "select A.WAREID,B.WARENAME,B.WARENO,B.UNITS,b.model,B.RETAILSALE,b.sale1,B.LOCALE,B.GBBAR,b.ssdate";
			qry += ",b.prodno,b.sizegroupno,E.BRANDNAME,t.typename,t1.typename as typename0,r.areaname,a.remark0";
			if (Tablename.equals("WAREOUTM") || Tablename.equals("WAREPEIM"))
				qry += ",A.SALEID,F.SALENAME";
			else if (Tablename.equals("SHOPSALEM"))
				qry += ",A.HOUSEID,F.HOUSENAME,g.epname as salemanname";

			if (Hzfs == 1)
				qry += ",A.COLORID,C.COLORNAME";
			if (Tablename.equals("WAREOUTM") || Tablename.equals("SHOPSALEM"))
				qry += ",a.tjtag";
			else
				qry += ",0 as tjtag";

			qry += ",'' as barcode";
			// qry += ",'' as remark0";
			qry += " ,min(a.id) as id,sum(a.amount) as amount,sum(a.curr) as curr,case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount)," + Priceprec + ") end  as price";
			if (Tablename.equals("WAREINM") || Tablename.equals("WAREOUTM") || Tablename.equals("WAREPEIM")//
					|| Tablename.equals("PROVORDERM") || Tablename.equals("CUSTORDERM") || Tablename.equals("SHOPSALEM")//
					|| Tablename.equals("REFUNDOUTM")) {
				qry += ",sum(A.AMOUNT*A.PRICE0) as CURR0";
				qry += ",case sum(A.AMOUNT) when 0 then 0 else round(sum(A.AMOUNT*A.PRICE0)/sum(a.amount)," + Priceprec + ") end as price0";
				qry += ",case sum(A.AMOUNT*A.PRICE0) when 0 then 0 else round(sum(a.curr)/sum(a.amount*a.PRICE0),2) end as DISCOUNT";
			}
			if (Tablename.equals("WARECHECKM"))
				qry += ",sum(a.checkamt) as checkamt,sum(a.bookamt) as bookamt";
			qry += "\n FROM " + Tablename + " a";
			qry += "\n left outer join warecode b on a.wareid=b.wareid";
			qry += "\n left outer join area r on b.areaid=r.areaid";
			if (Hzfs == 1)
				qry += " left outer join colorcode c on a.colorid=c.colorid";
			qry += "\n left outer join brand e on b.brandid=e.brandid";
			qry += "\n left outer join waretype t on b.typeid=t.typeid";
			qry += "\n left outer join waretype t1 on t.p_typeid=t1.typeid";
			if (Tablename.equals("WAREOUTM") || Tablename.equals("WAREPEIM"))
				qry += "\n left outer join salecode f on a.saleid=f.saleid";
			else if (Tablename.equals("SHOPSALEM")) {
				qry += "\n left outer join warehouse f on a.houseid=f.houseid";
				qry += "\n left outer join employe g on a.salemanid=g.epid";
			}
			qry += "\n where a.accid=" + Accid + " and a.noteno='" + Noteno + "'";
			qry += "\n group by A.WAREID,B.WARENAME,B.WARENO,B.UNITS,b.model,b.tjtag,B.RETAILSALE,b.sale1,B.LOCALE,B.GBBAR";
			qry += ",b.ssdate,b.prodno,b.sizegroupno,E.BRANDNAME,t.typename,t1.typename,r.areaname,a.remark0";
			if (Tablename.equals("WAREOUTM") || Tablename.equals("WAREPEIM"))
				qry += ",A.SALEID,F.SALENAME";
			else if (Tablename.equals("SHOPSALEM"))
				qry += ",A.HOUSEID,F.HOUSENAME,g.epname";
			if (Hzfs == 1)
				qry += ",A.COLORID,C.COLORNAME";
			if (Tablename.equals("WAREOUTM") || Tablename.equals("SHOPSALEM"))
				qry += ",a.tjtag";
		}
		qp.setQueryString(qry);
		qp.setSortString(sort);
		// System.out.println(qry);
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		return 1;
	}

	// 传送指定单据的商品条码到后台打印
	public int SendBartoBack() {
		if (Noteno.length() <= 0 || Progid == 0) {
			errmess = "noteno或progid不是一个有效值！";
			return 0;
		}
		long progid0 = 1015; // 条码打印
		String noteno0 = "USER" + Userid;
		String qry = "declare ";
		qry += "\n    v_id number; ";
		qry += "\n    v_count number; ";
		qry += "\n    v_prtid number(10); ";
		qry += "\n    v_retcs varchar2(200); ";
		qry += "\n begin ";

		qry += "\n   begin  ";
		qry += "\n     select prtid into v_prtid from PROGPRINT where epid=" + Userid + " and progid=" + progid0 + "; "; // 取默认打印机
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n     v_prtid:=0; ";
		qry += "\n   end;";
		qry += "\n   if v_prtid=0 then ";
		qry += "\n      v_retcs:= '0未找到后台默认打印机，请先设置...';"; // 暂时注释
		qry += "\n      goto exit; ";
		qry += "\n   end if;";
		qry += "\n   select count(*) into v_count from backprint where accid=" + Accid + " and progid=" + progid0 + " and noteno='" + noteno0 + "' and prtid=v_prtid; ";
		qry += "\n   if v_count>0 then ";
		qry += "\n      v_retcs:= '0单据已存在于后台打印队列，不能重复发送。' ;";
		qry += "\n      goto exit;";
		qry += "\n   end if;";

		qry += "\n   delete from warebarprint where epid=" + Userid + ";";
		qry += "\n   declare cursor cur_data is";

		if (Progid == 1102) // 采购入库
		{
			qry += "\n   select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount)  as amount";
			qry += "\n   from wareinm a left outer join warecode b on a.wareid=b.wareid";
			qry += "\n   where a.accid=" + Accid + " and a.noteno='" + Noteno + "'";
			qry += "\n   group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0 ";
		} else if (Progid == 1508) // 期初入库
		{
			qry += "\n   select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount) as amount ";
			qry += "\n   from firsthousem a left outer join warecode b on a.wareid=b.wareid";
			qry += "\n   where a.accid=" + Accid + " and a.noteno='" + Noteno + "'";
			qry += "\n   group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0 ";
		} else if (Progid == 1501) // 临时盘点
		{
			qry += "\n   select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount) as amount ";
			qry += "\n   from tempcheckm a left outer join warecode b on a.wareid=b.wareid";
			qry += "\n   where a.accid=" + Accid + " and a.noteno='" + Noteno + "'";
			qry += "\n   group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0 ";
		} else if (Progid == 1402) // 调拨出库
		{
			qry += "\n   select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount) as amount ";
			qry += "\n   from allotoutm a left outer join warecode b on a.wareid=b.wareid";
			qry += "\n   where a.accid=" + Accid + " and a.noteno='" + Noteno + "'";
			qry += "\n   group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0 ";
		} else if (Progid == 1403) // 调拨入库
		{
			qry += "\n   select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount) as amount ";
			qry += "\n   from allotinm a left outer join warecode b on a.wareid=b.wareid";
			qry += "\n   where a.accid=" + Accid + " and a.noteno='" + Noteno + "'";
			qry += "\n   group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0 ";
		}

		qry += ";";
		qry += "\n     v_row cur_data%rowtype;  ";
		qry += "\n  begin ";
		qry += "\n    v_count:=0;";
		qry += "\n    for v_row in cur_data loop  ";
		qry += "\n       begin ";
		qry += "\n         select id into v_id from warebarprint where epid=" + Userid + " and wareid=v_row.wareid and colorid=v_row.colorid  and sizeid=v_row.sizeid and rownum=1; ";
		qry += "\n         update warebarprint set amount=amount+v_row.amount where id=v_id; ";
		qry += "\n       EXCEPTION WHEN NO_DATA_FOUND THEN  ";
		qry += "\n         insert into warebarprint (id,epid,wareid,colorid,sizeid,amount,barsale,remark) ";
		qry += "\n         values (warebarprint_id.nextval," + Userid + ",v_row.wareid,v_row.colorid,v_row.sizeid,v_row.amount,v_row.retailsale,''); ";
		qry += "\n       end; ";
		qry += "\n       v_count:=v_count+1;";
		qry += "\n       if v_count>1000 then ";
		qry += "\n          commit;";
		qry += "\n          v_count:=0;";
		qry += "\n       end if;";
		qry += "\n    end loop; ";
		qry += "\n  end;";
		qry += "\n  insert into backprint(id,accid,progid,houseid,noteno,noteid,prtid,lastop)";
		qry += "\n  values (backprint_id.nextval," + Accid + "," + progid0 + ",0,'" + noteno0 + "'," + Userid + ",v_prtid,'" + Lastop + "'); ";
		qry += "\n  commit;";

		qry += "\n  v_retcs:= '1单据已传送到后台打印队列，稍候开始打印...';";
		qry += "\n  <<exit>>";
		qry += "\n  select v_retcs into :retcs from dual;";
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
}