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
import com.flyang.main.pFunc;
//import com.common.tool.SizeParam;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-27 15:47:30
//*************************************
public class Warepeim implements java.io.Serializable {
	// private static final long serialVersionUID = -5555622244543189074L;

	private Float Id;
	private Long Accid;
	private Long Userid;
	private String Noteno;
	private Long Wareid;
	private Long Colorid;
	private Long Sizeid;
	private Long Saleid;
	private Float Amount;
	private Float Price0;
	private Float Discount;
	private Float Price;
	private Float Curr;
	private String Remark0;
	private Date Lastdate;
	private Float Factamt;
	private String errmess;
	private String Calcdate = ""; // 账套开始使用日期

	public void setCalcdate(String calcdate) {
		Calcdate = calcdate;
	}

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

	public void setUserid(Long userid) {
		this.Userid = userid;
	}

	public void setNoteno(String noteno) {
		this.Noteno = noteno;
	}

	public String getNoteno() {
		return Noteno;
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

	public void setSaleid(Long saleid) {
		this.Saleid = saleid;
	}

	public Long getSaleid() {
		return Saleid;
	}

	public void setAmount(Float amount) {
		this.Amount = amount;
	}

	public Float getAmount() {
		return Amount;
	}

	public void setPrice0(Float price0) {
		this.Price0 = price0;
	}

	public Float getPrice0() {
		return Price0;
	}

	public void setDiscount(Float discount) {
		this.Discount = discount;
	}

	public Float getDiscount() {
		return Discount;
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

	public void setRemark0(String remark0) {
		this.Remark0 = remark0;
	}

	public String getRemark0() {
		return Remark0;
	}

	public void setFactamt(Float factamt) {
		this.Factamt = factamt;
	}

	public Float getFactamt() {
		return Factamt;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Id == 0 || Noteno.length() <= 0) {
			errmess = "id或noteno不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("   v_totalcurr number; ");
		strSql.append("   v_totalamt number; ");
		strSql.append("begin ");
		strSql.append("  delete from warepeim ");
		strSql.append("  where id=" + Id + " and accid= " + Accid + " and noteno='" + Noteno + "';");
		// 汇总数量及金额
		strSql.append("  select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from warepeim where  accid=" + Accid + " and noteno='" + Noteno + "'; ");
		strSql.append("  update warepeih set totalcurr=v_totalcurr,totalamt=v_totalamt");
		strSql.append("  where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("  commit;");
		strSql.append(" end;");
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 删除记录
	public int Delete1() {
		if (Wareid == 0 || Colorid == 0 || Noteno == "") {
			errmess = "wareid或colorid或noteno不是一个有效值！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();

		strSql.append("declare ");
		strSql.append("\n   v_totalcurr number; ");
		strSql.append("\n   v_totalcheckcurr number; ");
		strSql.append("\n   v_totalamt number; ");
		strSql.append("\n begin ");
		strSql.append("\n   delete from warepeim where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + " and colorid=" + Colorid + "; ");
		strSql.append("\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from warepeim where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n   update warepeih set totalcurr=v_totalcurr,totalamt=v_totalamt");
		strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n   commit;");
		strSql.append("\n end;");

		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append(int priceprec, int statetag) {
		if (Func.isNull(Noteno)) {
			errmess = "noteno不是一个有效值！";
			return 0;

		}
		if (Wareid == null || Colorid == null || Sizeid == null || Wareid == 0 || Colorid == 0 || Sizeid == 0) {
			errmess = "wareid或colorid或sizeid不是一个有效值！";
			return 0;
		}
		if (Func.isNull(Amount)) {
			errmess = "amount不是一个有效值！";
			return 0;
		}
		Noteno = Noteno.toUpperCase();
		// 以下代码要注释===========================================
		if (Price != null) // 如果传入了折扣价，按折后价算折扣
		{
			Price = Func.getRound(Price, priceprec);
			// model.curr = model.price * model.amount;
			Curr = Func.getRound(Price * Amount, 2);

			if (Price0 == 0)
				Discount = (float) 1;
			// else model.discount = Math.Round((decimal)(model.price /
			// model.price0), 2, MidpointRounding.AwayFromZero);
		} else {
			Price = Func.getRound(Price0 * Discount, priceprec);
			// model.curr = model.price * model.amount;
			Curr = Func.getRound(Price * Amount, 2);

		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		// if (Noteno != null) {
		strSql1.append("noteno,");
		strSql2.append("'" + Noteno + "',");
		// }
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
		if (Saleid != null) {
			strSql1.append("saleid,");
			strSql2.append(Saleid + ",");
		}
		if (Amount != null) {
			strSql1.append("amount,");
			strSql2.append("'" + Amount + "',");
		}
		if (Price0 != null) {
			strSql1.append("price0,");
			strSql2.append("'" + Price0 + "',");
		}
		if (Discount != null) {
			strSql1.append("discount,");
			strSql2.append("'" + Discount + "',");
		}
		if (Price != null) {
			strSql1.append("price,");
			strSql2.append("'" + Price + "',");
		}
		if (Curr != null) {
			strSql1.append("curr,");
			strSql2.append("'" + Curr + "',");
		}
		if (Remark0 != null) {
			strSql1.append("remark0,");
			strSql2.append("'" + Remark0 + "',");
		}
		if (Factamt != null) {
			strSql1.append("factamt,");
			strSql2.append("'" + Factamt + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_totalcurr number; ");
		strSql.append("\n   v_totalamt number; ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		strSql.append("\n    select count(*) into v_count from warecode where accid=" + Accid + " and wareid=" + Wareid + " and statetag=1;");
		strSql.append("\n    if v_count=0 then");
		strSql.append("\n       v_retcs:='0商品无效！';");
		strSql.append("\n       goto exit;");
		strSql.append("\n    end if;");
		strSql.append("\n    select count(*) into v_count from colorcode where accid=" + Accid + " and colorid=" + Colorid + " and statetag=1;");
		strSql.append("\n    if v_count=0 then");
		strSql.append("\n       v_retcs:='0颜色无效！';");
		strSql.append("\n       goto exit;");
		strSql.append("\n    end if;");
		strSql.append("\n    select count(*) into v_count from sizecode where accid=" + Accid + " and sizeid=" + Sizeid + " and statetag=1;");
		strSql.append("\n    if v_count=0 then");
		strSql.append("\n       v_retcs:='0尺码无效！';");
		strSql.append("\n       goto exit;");
		strSql.append("\n    end if;");
		strSql.append("\n    begin");
		strSql.append("\n      select id into v_id from warepeim where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + " and colorid=" + Colorid + " and sizeid=" + Sizeid + " and rownum=1;");
		strSql.append("\n      update warepeim set amount=amount+" + Amount + ",curr=(amount+" + Amount + ")*price where id=v_id;");
		strSql.append("\n    EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n      v_id:=warepeim_id.nextval; ");
		strSql.append("\n      insert into warepeim (" + strSql1.toString() + ")");
		strSql.append("\n      values (" + strSql2.toString() + ");");
		strSql.append("\n    end;");

		strSql.append("\n    select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from warepeim where  accid=" + Accid + " and noteno='" + Noteno + "'; ");
		strSql.append("\n    update warepeih set totalcurr=v_totalcurr,totalamt=v_totalamt");

		if (statetag == 1)
			strSql.append(" ,statetag=1,lastdate=sysdate,notedate=sysdate");
		strSql.append("\n    where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n    commit;");
		strSql.append("\n    v_retcs:='1操作成功！'; ");
		strSql.append("\n    <<exit>>");
		strSql.append("\n    select v_retcs into :retcs from dual;");
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

	// 修改记录
	public int Update(int priceprec, int allpricechange, int statetag) {
		if (Id == null || Id == 0) {
			errmess = "id不是一个有效值！";
			return 0;
		}
		if (Func.isNull(Noteno)) {
			errmess = "noteno不是一个有效值！";
			return 0;

		}
		if (Wareid == null || Colorid == null || Sizeid == null || Wareid == 0 || Colorid == 0 || Sizeid == 0) {
			errmess = "wareid或colorid或sizeid不是一个有效值！";
			return 0;
		}
		if (Func.isNull(Amount)) {
			errmess = "amount不是一个有效值！";
			return 0;
		}
		Noteno = Noteno.toUpperCase();
		// 以下代码要注释===========================================
		if (Price != null) // 如果传入了折扣价，按折后价算折扣
		{
			Price = Func.getRound(Price, priceprec);
			// model.curr = model.price * model.amount;
			Curr = Func.getRound(Price * Amount, 2);

			if (Price0 == 0)
				Discount = (float) 1;
			// else model.discount = Math.Round((decimal)(model.price /
			// model.price0), 2, MidpointRounding.AwayFromZero);
		} else {
			Price = Func.getRound(Price0 * Discount, priceprec);
			// model.curr = model.price * model.amount;
			Curr = Func.getRound(Price * Amount, 2);

		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n    v_totalcurr number; ");
		strSql.append("\n    v_totalamt number; ");
		strSql.append("\n    v_count number; ");
		strSql.append("\n    v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		strSql.append("\n    select count(*) into v_count from warecode where accid=" + Accid + " and wareid=" + Wareid + " and statetag=1;");
		strSql.append("\n    if v_count=0 then");
		strSql.append("\n       v_retcs:='0商品无效！';");
		strSql.append("\n       goto exit;");
		strSql.append("\n    end if;");
		strSql.append("\n    select count(*) into v_count from colorcode where accid=" + Accid + " and colorid=" + Colorid + " and statetag=1;");
		strSql.append("\n    if v_count=0 then");
		strSql.append("\n       v_retcs:='0颜色无效！';");
		strSql.append("\n       goto exit;");
		strSql.append("\n    end if;");
		strSql.append("\n    select count(*) into v_count from sizecode where accid=" + Accid + " and sizeid=" + Sizeid + " and statetag=1;");
		strSql.append("\n    if v_count=0 then");
		strSql.append("\n       v_retcs:='0尺码无效！';");
		strSql.append("\n       goto exit;");
		strSql.append("\n    end if;");

		strSql.append("\n    update warepeim set ");
		if (Wareid != null) {
			strSql.append("wareid=" + Wareid + ",");
		}
		if (Colorid != null) {
			strSql.append("colorid=" + Colorid + ",");
		}
		if (Sizeid != null) {
			strSql.append("sizeid=" + Sizeid + ",");
		}
		if (Saleid != null) {
			strSql.append("saleid=" + Saleid + ",");
		}
		if (Amount != null) {
			strSql.append("amount='" + Amount + "',");
		}
		if (Price0 != null) {
			strSql.append("price0='" + Price0 + "',");
		}
		if (Discount != null) {
			strSql.append("discount='" + Discount + "',");
		}
		if (Price != null) {
			strSql.append("price='" + Price + "',");
		}
		if (Curr != null) {
			strSql.append("curr='" + Curr + "',");
		}
		if (Remark0 != null) {
			strSql.append("remark0='" + Remark0 + "',");
		}
		if (Factamt != null) {
			strSql.append("factamt='" + Factamt + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("\n  where id=" + Id + " and accid=" + Accid + " and noteno='" + Noteno + "';");
		if (allpricechange == 1) // allpricechange=1 要更改所有同款价格)
		{
			strSql.append("\n  update warepeim set  lastdate=sysdate");
			strSql.append("  ,price0=" + Price0);
			strSql.append("  ,discount=" + Discount);
			strSql.append("  ,price=" + Price);
			strSql.append("  ,curr=" + Price + "*amount");
			strSql.append("\n  where id<>" + Id + " and wareid=" + Wareid + " and accid=" + Accid + " and noteno='" + Noteno + "'; ");
		}

		// 汇总数量及金额
		strSql.append("\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from warepeim where  accid=" + Accid + " and noteno='" + Noteno + "'; ");
		strSql.append("\n   update warepeih set totalcurr=v_totalcurr,totalamt=v_totalamt");
		if (statetag == 1)
			strSql.append(",statetag=1,notedate=sysdate,lastdate=sysdate");
		strSql.append(" where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n   v_retcs:='1操作成功！'; ");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		strSql.append(" end;");
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

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM warepeim a");
		strSql.append(" left outer join warecode b on a.wareid=b.wareid");
		strSql.append(" left outer join colorcode c on a.colorid=c.colorid");
		strSql.append(" left outer join sizecode d on a.sizeid=d.sizeid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		if (qp.getPageIndex() < 0) {// 如果是导出，要显示条码
			strSql.append(", f_getwarebarcode(A.WAREID,A.COLORID,A.SIZEID) as barcode");
		}
		strSql.append(" FROM warepeim a");
		strSql.append(" left outer join warecode b on a.wareid=b.wareid");
		strSql.append(" left outer join colorcode c on a.colorid=c.colorid");
		strSql.append(" left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append(" left outer join salecode e on a.saleid=e.saleid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable1(QueryParam qp, String qry) {
		String qry1 = "select nvl(sum(a.amount),0) as totalamount,nvl(sum(a.curr),0) as totalcurr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
		qry1 += " FROM warepeim a";
		qry1 += " where a.ACCID=" + Accid + " and a.NOTENO='" + Noteno + "'";
		Float totalamt = (float) 0;
		Float totalcurr = (float) 0;
		Integer skc = 0;
		Table tb = DbHelperSQL.Query(qry1).getTable(1);
		if (tb.getRowCount() > 0) {
			totalamt = Float.parseFloat(tb.getRow(0).get("TOTALAMOUNT").toString());
			totalcurr = Float.parseFloat(tb.getRow(0).get("TOTALCURR").toString());
			skc = Integer.parseInt(tb.getRow(0).get("SKC").toString());
		}
		qp.setTotalString("\"totalamt\":" + totalamt + ",\"totalcurr\":" + totalcurr + ",\"skc\":" + skc);
		qp.setQueryString(qry);
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String qry) {
		qp.setQueryString(qry);
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from warepeim");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1
		// and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 条码扫入
	public int doBarcodein(String barcode, int pricetype, int priceprec, int qxbj) {
		if (barcode.equals("")) {
			errmess = "条码未传入！";
			return 0;
		}
		if (Noteno.equals("")) {
			errmess = "单据号参数无效！";
			return 0;
		}
		if (Amount < -99999 || Amount > 99999) {
			errmess = "数量不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n    v_accid  number;"; // --账户id
		qry += "\n    v_noteno  varchar2(20);"; // --单据号
		qry += "\n    v_barcode  varchar2(60); "; // --条码
		qry += "\n    v_discount  number; "; // --折扣
		qry += "\n    v_pricetype  number; "; // --价格方式 0=进价1=零价
		qry += "\n    v_amount  number; "; // --个数
		qry += "\n    v_custid number(10); "; //
		qry += "\n    v_wareid number(10); "; //
		qry += "\n    v_colorid number(10); "; //
		qry += "\n    v_saleid number(10); "; //
		qry += "\n    v_sizeid number(10); "; //
		qry += "\n    v_barcode0  varchar2(60); "; // --条码
		qry += "\n    v_id  number(20); ";
		qry += "\n    v_id0  number(20); ";
		qry += "\n    v_price number;";
		qry += "\n    v_price0 number; ";
		qry += "\n    v_count number; ";
		qry += "\n    v_curr number; ";
		qry += "\n    v_totalcurr number; ";
		qry += "\n    v_totalamt number; ";
		qry += "\n    v_wareno varchar2(40); ";
		qry += "\n    v_warename varchar2(80); ";
		qry += "\n    v_colorname varchar2(40); ";
		qry += "\n    v_sizename varchar2(40); ";
		qry += "\n    v_retcs varchar2(200); ";
		qry += "\n begin ";
		qry += "\n    v_amount:=" + Amount + "; ";
		qry += "\n    v_discount:=" + Discount + "; ";
		qry += "\n    v_pricetype:=" + pricetype + "; ";
		qry += "\n    v_saleid:=" + Saleid + "; ";

		// qry += "\n select id,barcode into v_id,v_barcode0 from (select
		// id,barcode from warebarcode where barcode=substr('" + barcode +
		// "',1,length(barcode)) and accid=" + accid + " order by barcode desc )
		// where rownum=1; ";//
		// =======================
		qry += "\n    v_count:=0;";
		if (barcode.length() >= 4) {
			qry += "\n   select nvl(min(id),0),nvl(min(barcode),''),count(*) into v_id,v_barcode0,v_count ";
			qry += "\n   from (select id,barcode from warebarcode where barcode like '%" + barcode + "%' and accid=" + Accid + " and rownum<=2 ); ";//
		}
		qry += "\n   if v_count<>1 then";
		qry += "\n      begin";
		qry += "\n        select id,barcode into v_id,v_barcode0 from (select id,barcode from warebarcode";
		qry += "\n        where barcode=substr('" + barcode + "',1,length(barcode)) and accid=" + Accid + " order by barcode desc )  where rownum=1; ";//
		qry += "\n      EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n        v_retcs:= '0 未找到条码:'||'" + barcode + "' ;  ";
		qry += "\n        goto exit;";
		qry += "\n      end;";
		qry += "\n   end if;";

		qry += "\n   begin";
		qry += "\n     select a.wareid,a.colorid,a.sizeid,";

		qry += "\n     case v_pricetype when 0 then b.retailsale  when 1 then b.sale1 when 2 then b.sale2 when 3 then b.sale3 when 4 then b.sale4 when 5 then b.sale5 else b.retailsale end";
		qry += "\n       ,b.wareno,b.warename,c.colorname,d.sizename  ";//
		qry += "\n     into v_wareid,v_colorid,v_sizeid,v_price0 ,v_wareno,v_warename,v_colorname,v_sizename  ";
		qry += "\n     from warebarcode a  ";
		qry += "\n     left outer join warecode b on a.wareid=b.wareid ";
		qry += "\n     left outer join colorcode c on a.colorid=c.colorid ";
		qry += "\n     left outer join sizecode d on a.sizeid=d.sizeid ";
		qry += "\n     where a.id=v_id  and b.sizegroupno=d.groupno  and b.statetag=1";//
		if (qxbj == 1) // 1 启用权限控制
			qry += "\n and (b.brandid=0 or exists (select 1 from employebrand x where b.brandid=x.brandid and x.epid=" + Userid + "))";
		qry += "\n     ;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_retcs:= '0 商品未授权或条码尺码异常！' ;  ";
		qry += "\n     goto exit;";
		qry += "\n   end;";
		qry += "\n   if v_saleid=0 then v_saleid:=2; end if; ";
		qry += "\n   select custid into v_custid from warepeih where accid=" + Accid + "  and noteno='" + Noteno + "';";
		qry += "\n   select  count(*) into v_count from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n   where a.statetag=1 and rownum=1 and a.ntid=1 and a.accid=" + Accid + "";
		qry += "\n   and  b.wareid=v_wareid  and a.custid=v_custid;"; // and
																		// b.colorid=v_colorid
		qry += "\n   if v_count=0 then v_saleid:=2; else v_saleid:=3; end if; ";

		qry += "\n     select count(*) into v_count from warepeim where accid=" + Accid + " and noteno='" + Noteno + "' ";//
		qry += "\n        and wareid=v_wareid and colorid=v_colorid and sizeid=v_sizeid and saleid=v_saleid ; ";//
		qry += "\n     if v_count=0 then ";//
		qry += "\n        v_price:=round(v_price0*v_discount," + priceprec + "); ";
		qry += "\n        v_curr:=v_price*v_amount; ";//
		qry += "\n        insert into warepeim (id,accid,noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,curr,remark0) ";//
		qry += "\n        values (warepeim_id.nextval," + Accid + ",'" + Noteno + "',v_wareid,v_colorid,v_sizeid,v_saleid,v_amount,v_price0,v_discount,v_price,v_curr,''); ";//
		qry += "\n     else  ";//
		qry += "\n        select id into v_id from warepeim where accid=" + Accid + " and noteno='" + Noteno
				+ "' and wareid=v_wareid and colorid=v_colorid and sizeid=v_sizeid  and saleid=v_saleid and rownum=1 order by id ; ";//
		qry += "\n        update warepeim set amount=amount+v_amount,curr=(amount+v_amount)*price where id=v_id and noteno='" + Noteno + "' and accid=" + Accid + "; ";
		qry += "\n     end if; ";

		qry += "\n     select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from warepeim where accid=" + Accid + " and noteno='" + Noteno + "';  ";
		qry += "\n     update warepeih set totalcurr=v_totalcurr,totalamt=v_totalamt  ";
		qry += "\n     where accid=" + Accid + " and noteno='" + Noteno + "'; ";

		qry += "\n    v_retcs:= '1 '||v_warename||':'||v_wareno||','||v_colorname||','||v_sizename||'  条码:'||v_barcode0 ; ";
		// 填 入条码跟踪表
		qry += "\n     if '" + barcode + "'>v_barcode0  then";
		// noteid:0采购入库1采购退库2期初入库3调拨入库4零售出库5批发出库6经销退库7调拨出库8盘点9临时盘点10采购订单11客户订单12调拨订单13配货单
		qry += "\n        insert into warebarrecord (id,accid,barcode,noteid,noteno,amount,lastdate)";
		qry += "\n        values (warebarrecord_id.nextval," + Accid + ",'" + barcode + "',13,'" + Noteno + "',v_amount,sysdate); ";
		qry += "\n     end if;";
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

	// 捡货扫码
	public int doBarcodeincheck(String barcode, int qxbj) {
		if (barcode.equals("")) {
			errmess = "条码未传入！";
			return 0;
		}
		if (Noteno.equals("")) {
			errmess = "单据号参数无效！";
			return 0;
		}
		if (Amount < -99999 || Amount > 99999) {
			errmess = "数量不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n    v_accid  number;"; // --账户id
		qry += "\n    v_noteno  varchar2(20);"; // --单据号
		qry += "\n    v_barcode  varchar2(30); "; // --条码
		// qry += "\n v_discount number; "; // --折扣
		// qry += "\n v_pricetype number; "; // --价格方式 0=进价1=零价
		qry += "\n    v_amount  number; "; // --个数
		qry += "\n    v_custid number(10); "; //
		qry += "\n    v_wareid number(10); "; //
		qry += "\n    v_colorid number(10); "; //
		// qry += "\n v_saleid number(10); "; //
		qry += "\n    v_sizeid number(10); "; //
		qry += "\n    v_barcode0  varchar2(30); "; // --条码
		qry += "\n    v_id  number(20); ";
		qry += "\n    v_id0  number(20); ";
		// qry += "\n v_price number;";
		// qry += "\n v_price0 number; ";
		qry += "\n    v_count number; ";
		// qry += "\n v_curr number; ";
		// qry += "\n v_totalcurr number; ";
		qry += "\n    v_totalamt number; ";
		qry += "\n    v_wareno varchar2(40); ";
		qry += "\n    v_warename varchar2(80); ";
		qry += "\n    v_colorname varchar2(40); ";
		qry += "\n    v_sizename varchar2(40); ";
		qry += "\n    v_retcs varchar2(200); ";
		qry += "\n begin ";
		/*
		 * qry += "\n    v_accid:=" + accid + "; "; qry += "\n    v_noteno:='" +
		 * noteno + "'; "; qry += "\n    v_barcode:='" + barcode + "'; ";
		 */
		qry += "\n    v_amount:=" + Amount + "; ";

		// qry += "\n select id,barcode into v_id,v_barcode0 from (select
		// id,barcode from warebarcode where barcode=substr('" + barcode +
		// "',1,length(barcode)) and accid=" + accid + " order by barcode desc )
		// where rownum=1; ";//
		// =======================
		qry += "\n    v_count:=0;";
		if (barcode.length() >= 4) {
			qry += "\n   select nvl(min(id),0),nvl(min(barcode),''),count(*) into v_id,v_barcode0,v_count from (select id,barcode from warebarcode where barcode like '%" + barcode + "%' and accid=" + Accid
					+ " and rownum<=2 ); ";//
		}
		qry += "\n   if v_count<>1 then";
		qry += "\n      begin";
		qry += "\n        select id,barcode into v_id,v_barcode0 from (select id,barcode from warebarcode where barcode=substr('" + barcode + "',1,length(barcode)) and accid=" + Accid
				+ " order by barcode desc )  where rownum=1; ";//
		qry += "\n      EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n        v_retcs:= '0 未找到条码:'||'" + barcode + "' ;  ";
		qry += "\n        goto exit;";
		qry += "\n      end;";
		qry += "\n   end if;";

		qry += "\n     select a.wareid,a.colorid,a.sizeid";

		// qry += "\n case v_pricetype when 0 then b.retailsale when 1 then
		// b.sale1 when 2 then b.sale2 when 3 then b.sale3 when 4 then b.sale4
		// when 5 then b.sale5 else b.retailsale end";
		qry += "\n       ,b.wareno,b.warename,c.colorname,d.sizename  ";//
		qry += "\n     into v_wareid,v_colorid,v_sizeid,v_wareno,v_warename,v_colorname,v_sizename  ";
		qry += "\n     from warebarcode a  ";
		qry += "\n     left outer join warecode b on a.wareid=b.wareid ";
		qry += "\n     left outer join colorcode c on a.colorid=c.colorid ";
		qry += "\n     left outer join sizecode d on a.sizeid=d.sizeid ";
		qry += "\n     where a.id=v_id and b.sizegroupno=d.groupno and b.statetag=1";//
		if (qxbj == 1) // 1 启用权限控制
			qry += "\n and (b.brandid=0 or exists (select 1 from employebrand x where b.brandid=x.brandid and x.epid=" + Userid + "))";
		qry += "\n     ;";
		qry += "\n     begin";
		qry += "\n        select id into v_id from warepeim where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=v_wareid and colorid=v_colorid and sizeid=v_sizeid and rownum=1 ; ";//
		qry += "\n     EXCEPTION WHEN NO_DATA_FOUND THEN ";//
		qry += "\n        v_retcs:= '0该款商品没有配货记录！' ;  ";
		qry += "\n        goto exit;";
		qry += "\n     end;";

		qry += "\n     begin";
		qry += "\n       select id into v_id from warepeim where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=v_wareid and colorid=v_colorid and sizeid=v_sizeid and rownum=1 and amount>factamt ; ";//
		qry += "\n       update warepeim set factamt=factamt+v_amount where id=v_id and noteno='" + Noteno + "' and accid=" + Accid + "; ";
		qry += "\n     EXCEPTION WHEN NO_DATA_FOUND THEN ";//
		qry += "\n        v_retcs:= '0该款商品已捡货完成！' ;  ";
		qry += "\n        goto exit;";
		qry += "\n     end;";

		qry += "\n     select nvl(sum(factamt),0) into v_totalamt from warepeim where accid=" + Accid + " and noteno='" + Noteno + "';  ";
		qry += "\n     update warepeih set totalfactamt=v_totalamt where accid=" + Accid + " and noteno='" + Noteno + "'; ";

		qry += "\n    v_retcs:= '1 '||v_warename||':'||v_wareno||','||v_colorname||','||v_sizename||'  条码:'||v_barcode0 ; ";
		// 填 入条码跟踪表
		qry += "\n     if '" + barcode + "'>v_barcode0  then";
		// noteid:0采购入库1采购退库2期初入库3调拨入库4零售出库5批发出库6经销退库7调拨出库8盘点9临时盘点10采购订单11客户订单12调拨订单13配货单
		qry += "\n        insert into warebarrecord (id,accid,barcode,noteid,noteno,amount,lastdate)";
		qry += "\n        values (warebarrecord_id.nextval," + Accid + ",'" + barcode + "',13,'" + Noteno + "',v_amount,sysdate); ";
		qry += "\n     end if;";
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

	// 整手输入
	public int AppendHand(JSONObject jsonObject) {
		if (Wareid == null || Wareid == 0) {
			errmess = "wareid不是一个有效值1！";
			return 0;
		}
		if (!pFunc.isWarecodeOk(Accid, Wareid)) {
			errmess = "wareid不是一个有效值2！";
			return 0;
		}
		if (Amount == 0 || Noteno.length() <= 0) {
			errmess = "amount或noteno不是一个有效值！";
			return 0;
		}
		if (!jsonObject.has("coloridlist")) {
			errmess = "coloridlist未传入！";
			return 0;
		}
		if (!jsonObject.has("sizeidlist")) {
			errmess = "sizeidlist未传入！";
			return 0;
		}
		JSONArray colorArray = jsonObject.getJSONArray("coloridlist");
		JSONArray sizeArray = jsonObject.getJSONArray("sizeidlist");
		String qry = "declare ";
		qry += "\n    v_totalcurr number; ";
		qry += "\n    v_totalcheckcurr number; ";
		qry += "\n    v_totalamt number; ";
		qry += "\n    v_id  number;";
		qry += "\n begin ";
		String sizelist = "^";
		for (int i = 0; i < colorArray.size(); i++) {

			JSONObject colorObject = colorArray.getJSONObject(i);
			Colorid = Long.parseLong(colorObject.getString("colorid"));

			for (int j = 0; j < sizeArray.size(); j++) {

				JSONObject sizeObject = sizeArray.getJSONObject(j);
				Sizeid = Long.parseLong(sizeObject.getString("sizeid"));
				if (!sizelist.contains("^" + Sizeid + "^")) {
					if (!pFunc.isWaresizeOk(Accid, Wareid, Sizeid)) {
						errmess = "sizeid不是一个有效值！";
						return 0;
					}
					sizelist += Sizeid + "^";
				}

				Curr = Func.getRound(Amount * Price, 2);
				qry += "\n   begin ";
				qry += "\n     select id into v_id from warepeim where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid;
				qry += "\n     and colorid=" + Colorid + " and sizeid=" + Sizeid + " and saleid=" + Saleid + " and rownum=1; ";
				qry += "\n     update warepeim set amount=amount+" + Amount + ",curr=(amount+" + Amount + ")*price where id=v_id;";
				qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN"; // 未找到，插入新记录
				qry += "\n     insert into warepeim (id,accid,noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,curr,remark0)";
				qry += "\n     values (warepeim_id.nextval," + Accid + ",'" + Noteno + "'," + Wareid + "," + Colorid + "," + Sizeid + "," + Saleid + "," + Amount + "," + Price0 + "," + Discount + "," + Price + "," + Curr
						+ ",'');";
				qry += "\n  end; ";
			}
		}
		qry += "\n    select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from warepeim where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n    update warepeih set totalcurr=v_totalcurr,totalamt=v_totalamt";

		qry += "\n    where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n    commit; ";
		qry += "\n end; ";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 获取配货明细汇总列表
	public int List(QueryParam qp, int sizenum, int priceprec) {
		if (Noteno.equals("")) {
			errmess = "noteno不是一个有效的值！";
			return 0;
		}
		String qry = "select a.wareid,b.wareno,b.warename,b.units,b.sizegroupno,b.retailsale,a.saleid,c.salename,a.colorid,d.colorname,a.price,a.discount,a.price0,b.imagename0";
		qry += ",sum(a.amount) as amount,sum(a.curr) as curr,min(a.remark0) as remark0,min(a.id) as id";
		qry += "\n from warepeim a ";
		qry += "\n left outer join warecode b on a.wareid=b.wareid";
		qry += "\n left outer join salecode c on a.saleid=c.saleid";
		qry += "\n left outer join colorcode d on a.colorid=d.colorid";
		qry += "\n where a.ACCID=" + Accid + " and a.NOTENO='" + Noteno + "'";
		// if (findbox != null && findbox != "") qry += " and (b.wareno like '%"
		// + findbox.ToUpper() + "%' or b.shortname like '%" + findbox.ToUpper()
		// + "%' or b.warename like '%" + findbox.ToUpper() + "%')";
		qry += "\n group by a.wareid,b.wareno,b.warename,b.units,b.sizegroupno,b.retailsale,a.saleid,c.salename,a.colorid,d.colorname,a.price,a.discount,a.price0,b.imagename0";
		Table tb = new Table();
		qp.setQueryString(qry);
		qp.setSumString("nvl(sum(amount),0) as totalamt,nvl(sum(curr),0) as totalcurr");
		String retcs;
		tb = DbHelperSQL.GetTable(qp);
		int count = tb.getRowCount();
		String fh = "";
		String fh1 = "";
		if (sizenum <= 0) // 不显示尺码
		{
			retcs = "{\"result\":1,\"msg\":\"操作成功！\"," + qp.getTotalString() + ",\"rows\":[";
			for (int i = 0; i < count; i++) {
				fh1 = "";
				retcs += fh + "{";
				for (int j = 0; j < tb.getColumnCount(); j++) {
					String strKey = tb.getRow(i).getColumn(j + 1).getName().toUpperCase();
					String strValue = tb.getRow(i).get(j).toString();
					retcs += fh1 + "\"" + strKey + "\":\"" + strValue + "\"";
					// jsonString.append(fh1 + "\"" + strKey + "\":\"" +
					// strValue + "\"");
					fh1 = ",";
				}
				retcs += "}";
				fh = ",";
			}
			retcs += "]}";
			errmess = retcs;
			return 1;
		}
		// System.out.println("doGetWareinmcolorsumList 1.6");
		String sqlsize = "";
		String sqlsum = "";
		String qry1;
		// long wareid;
		long wareid0 = 0;
		// long colorid;
		retcs = "{\"result\":1,\"msg\":\"操作成功！\"," + qp.getTotalString() + ",\"rows\":[";
		for (int i = 0; i < count; i++) {
			Wareid = Long.parseLong(tb.getRow(i).get("WAREID").toString());
			Colorid = Long.parseLong(tb.getRow(i).get("COLORID").toString());
			Saleid = Long.parseLong(tb.getRow(i).get("SALEID").toString());
			if (Wareid != wareid0) {
				SizeParam sp = new SizeParam(Wareid, sizenum);
				sqlsize = sp.getSqlSize();
				sqlsum = sp.getSqlSum();
				wareid0 = Wareid;
			}
			retcs += fh + "{";
			fh1 = "";
			for (int j = 0; j < tb.getColumnCount(); j++) {
				String strKey = tb.getRow(i).getColumn(j + 1).getName().toUpperCase();
				String strValue = tb.getRow(i).get(j).toString();
				retcs += fh1 + "\"" + strKey + "\":\"" + strValue + "\"";
				// jsonString.append(fh1 + "\"" + strKey + "\":\"" + strValue +
				// "\"");
				fh1 = ",";
			}

			// 取尺码数据
			qry1 = "select " + sqlsum + "," + sqlsize;
			qry1 += " from warepeim a where accid=" + Accid + " and noteno='" + Noteno + "'";
			qry1 += " and wareid=" + Wareid + " and colorid=" + Colorid + " and saleid=" + Saleid;

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

	// 显示配货商品明细(捡货)
	public int Load() {
		if (Wareid == 0 || Colorid == 0 || Noteno.equals("")) {
			errmess = "wareid或colorid或noteno不是一个有效的值！";
			return 0;
		}

		Table tb = new Table();
		String qry = "select wareid,wareno,warename,retailsale,units,locale,imagename0 from warecode where wareid=" + Wareid;

		tb = DbHelperSQL.Query(qry).getTable(1);
		String retcs = "\"msg\":\"操作成功\"";
		for (int j = 0; j < tb.getColumnCount(); j++) {
			String strKey = tb.getRow(0).getColumn(j + 1).getName().toUpperCase();
			String strValue = tb.getRow(0).get(j).toString();
//			if (j > 0)
//				retcs += ",";
			retcs += ",\"" + strKey + "\":\"" + strValue + "\"";
		}

		// 取颜色

		qry = "select colorid,colorname from colorcode where colorid=" + Colorid + " and accid=" + Accid;

		retcs += ",\"COLORID\":\"" + tb.getRow(0).get("COLORID").toString() + "\""//
				+ ",\"COLORNAME\":\"" + tb.getRow(0).get("COLORNAME").toString() + "\"";

		// 取配货单数
		// qry = " select
		// colorid,sizeid,amount,price,discount,price0,curr,remark0";
		// qry += " from warepeim a ";
		// qry += " where accid=" + Accid + " and noteno='" + Noteno + "' and
		// wareid=" + Wareid + " and saleid=" + Saleid;

		qry = "  select a.id,a.sizeid,b.sizename,a.amount,a.factamt,a.factamt-a.amount as balamt"; // 已捡
																									// -应捡=
																									// 差额
		qry += " from warepeim a ";
		qry += " join sizecode b on a.sizeid=b.sizeid";

		qry += " where a.accid=" + Accid + " and a.noteno='" + Noteno + "' and a.wareid=" + Wareid + " and a.colorid=" + Colorid + " and a.saleid=" + Saleid;
		qry += " order by b.sizeno,a.sizeid";

		// WriteLogTXT("debug", "2", qry);
		// qry += " group by b.sizeno,a.id";
		tb = DbHelperSQL.Query(qry).getTable(1);

		retcs += ",\"amountlist\":[";
		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				retcs += ",";

			// retcs += "{";
			// for (int j = 0; j < tb.getColumnCount(); j++) {
			// String strKey = tb.getRow(i).getColumn(j +
			// 1).getName().toUpperCase();
			// String strValue = tb.getRow(i).get(j).toString();
			// if (j > 0)
			// retcs += ",";
			// retcs += "\"" + strKey + "\":\"" + strValue + "\"";
			// }
			// retcs += "}";

			retcs += "{\"ID\":\"" + tb.getRow(i).get("ID").toString() + "\"" //
					+ ",\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"" //
					+ ",\"SIZENAME\":\"" + tb.getRow(i).get("SIZENAME").toString() + "\"" //
					+ ",\"AMOUNT\":\"" + tb.getRow(i).get("AMOUNT").toString() + "\"" //
					+ ",\"FACTAMT\":\"" + tb.getRow(i).get("FACTAMT").toString() + "\"" //
					+ ",\"BALAMT\":\"" + tb.getRow(i).get("BALAMT").toString() + "\"}";
		}
		retcs += "]";
		errmess = retcs;
		return 1;
	}

	// 显示配货商品明细
	public int Load(long custid, long houseid, int priceprec, int nearsaleok, String wareno) {
		if (Noteno.equals("")) {
			errmess = "wareid或noteno不是一个有效的值！";
			return 0;
		}
		if (custid == 0 || houseid == 0) {
			errmess = "custid或houseid不是一个有效的值！";
			return 0;
		}

		Table tb = new Table();
		String qry = "";
		if (Wareid != null && Wareid > 0)
			qry = "select wareid,wareno,warename,retailsale,units,locale,imagename0 from warecode where accid=" + Accid + " and  statetag=1 and wareid=" + Wareid;
		else if (wareno.length() > 0) {
			qry = "select wareid,wareno,warename,retailsale,units,locale,imagename0 from warecode where accid=" + Accid + " and wareno='" + wareno + "' and statetag=1 and rownum=1";
		} else {
			errmess = "商品id参数无效！";
			return 0;
		}

		tb = DbHelperSQL.Query(qry).getTable(1);
		int rs = tb.getRowCount();
		if (rs == 0) {
			errmess = "未找到商品！";
			return 0;
		}

		Wareid = Long.parseLong(tb.getRow(0).get("WAREID").toString());
		Price0 = Float.parseFloat(tb.getRow(0).get("RETAILSALE").toString());
		Price = Price0;
		String retcs = "\"msg\":\"操作成功\"";
		String retcs1 = "";
		for (int j = 0; j < tb.getColumnCount(); j++) {
			String strKey = tb.getRow(0).getColumn(j + 1).getName().toUpperCase();
			String strValue = tb.getRow(0).get(j).toString();
//			if (j > 0)
//				retcs += ",";
			retcs += ",\"" + strKey + "\":\"" + strValue + "\"";
		}

		// 取颜色

		// 取颜色
		qry = "select a.colorid,b.colorname";
		qry += "\n  FROM warecolor a";
		qry += "\n  left outer join colorcode b on a.colorid=b.colorid";
		qry += "\n  where a.wareid=" + Wareid + " and b.statetag=1 and b.noused=0 ";
		qry += "\n  and (a.noused=0 or a.noused=1 and exists (select 1 from warepeim c ";
		qry += "\n  where c.accid=" + Accid + " and c.noteno='" + Noteno + "' and a.wareid=c.wareid and a.colorid=c.colorid) )";

		qry += "\n  order by b.colorno";

		tb = DbHelperSQL.Query(qry).getTable(1);
		retcs1 += ",\"colorlist\":[";

		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				retcs1 += ",";
			retcs1 += "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\""//
					+ ",\"COLORNAME\":\"" + tb.getRow(i).get("COLORNAME").toString() + "\"}";

		}
		retcs1 += "]";

		// 取尺码
		qry = "select a.sizeid,a.sizename,a.sizeno  FROM sizecode a";
		qry += "\n  left outer join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno";
		qry += "\n  where a.statetag=1 and a.noused=0 and a.accid=" + Accid + " and b.wareid=" + Wareid;
		qry += "\n  and not exists (select 1 from warenosize a1 where b.wareid=a1.wareid and a.sizeid=a1.sizeid)";
		qry += "\n  order by a.sizeno,a.sizename";

		tb = DbHelperSQL.Query(qry).getTable(1);

		retcs1 += ",\"sizelist\":[";

		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				retcs1 += ",";
			retcs1 += "{\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"" //
					+ ",\"SIZENAME\":\"" + tb.getRow(i).get("SIZENAME").toString() + "\"}";

		}
		retcs1 += "]";

		// 取配货单数
		qry = " select colorid,sizeid,amount,price,discount,price0,curr,remark0";
		qry += " from warepeim a ";
		qry += " where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + " and saleid=" + Saleid;

		// WriteLogTXT("debug", "2", qry);
		// qry += " group by b.sizeno,a.id";
		tb = DbHelperSQL.Query(qry).getTable(1);

		retcs1 += ",\"amountlist\":[";
		Float totalcurr = (float) 0;
		Float totalamt = (float) 0;
		Amount = (float) 0;
		Curr = (float) 0;
		// Price = (float) 0;
		// Price0 = (float) 0;
		Discount = (float) 1;
		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				retcs1 += ",";
			Amount = Float.parseFloat(tb.getRow(i).get("AMOUNT").toString());
			Curr = Float.parseFloat(tb.getRow(i).get("CURR").toString());
			Price0 = Float.parseFloat(tb.getRow(i).get("PRICE0").toString());
			Remark0 = tb.getRow(i).get("REMARK0").toString();
			retcs1 += "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"" //
					+ ",\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"" //
					+ ",\"AMOUNT\":\"" + Amount + "\"" //
					+ ",\"PRICE0\":\"" + Price0 + "\""//
					+ ",\"DISCOUNT\":\"" + tb.getRow(i).get("DISCOUNT").toString() + "\"" //
					+ ",\"PRICE\":\"" + tb.getRow(i).get("PRICE").toString() + "\"" //
					+ ",\"CURR\":\"" + Curr + "\""//
					+ ",\"REMARK0\":\"" + Remark0 + "\"}";

			totalcurr += Curr;
			totalamt += Amount;
		}
		retcs1 += "]";

		if (Saleid == 0) // 新增配货商品,如果销售类型=0，自动查询销售 售类型
		{
			qry = "  select f_saleofwareout(" + Accid + "," + custid + "," + Wareid + ",0,0,1) as saleid from dual";
			Saleid = Long.parseLong(DbHelperSQL.RunSql(qry).toString());
		}

		qry = "select saleid,salename from salecode where saleid=" + Saleid;
		// string qry = "select
		// wareid,wareno,warename,entersale,retailsale,units from warecode where
		// wareid=" + wareid;
		tb = DbHelperSQL.Query(qry).getTable(1);
		retcs += ",\"SALEID\":\"" + tb.getRow(0).get("SALEID").toString() + "\"" //
				+ ",\"SALENAME\":\"" + tb.getRow(0).get("SALENAME").toString() + "\"";

		String jstr = Funcpackge.myWareoutprice(Accid, Wareid, custid, 0, houseid, Saleid, priceprec, nearsaleok, 0);
		retcs += "," + jstr;

		// 取库存
		if (houseid > 0) // 如果传入houseid,表示要返回库存
		{
			vTotalCxkczy kc = new vTotalCxkczy();
			kc.setAccid(Accid);
			kc.setUserid(Userid);
			kc.setHouseid(houseid);
			kc.setWareid(Wareid);
			kc.setBj(2);
			kc.setCalcdate(Calcdate);
			kc.doCxkczy();
			// private int myTotalCxkczy(string nowdate, long epid, long accid,
			// long houseid, long wareid, long colorid, long sizeid, long
			// typeid, long brandid, string wareno, string warename,string
			// seasonname, string prodyear, int qxbj,int bj=0)
			// myTotalCxkczy("", userid, accid, houseid, wareid, 0, 0, -1, -1,
			// -1, -1, "", "", "", "", "", "", 0, 2); //bj:2=可用库存
			qry = "   select colorid,sizeid,amount";
			qry += "   from v_cxkczy_data a where epid=" + Userid + " and wareid=" + Wareid + " and houseid=" + houseid;
			qry += "   and amount<>0 ";
			tb = DbHelperSQL.Query(qry).getTable(1);

			retcs1 += ",\"kclist\":[";

			for (int i = 0; i < tb.getRowCount(); i++) {
				if (i > 0)
					retcs1 += ",";
				retcs1 += "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"," //
						+ "\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\","//
						+ "\"AMOUNT\":\"" + tb.getRow(i).get("AMOUNT").toString() + "\"}";
			}
			retcs1 += "]";
		}

		retcs += retcs1;

		if (totalamt != 0)
			Price = Func.getRound(totalcurr / totalamt, priceprec);
		// if (Price0 == null)
		// Price0 = (float) 0;
		if (Price0 != 0) {
			Discount = Func.getRound(Price / Price0, 2);
		}
		retcs += ",\"ZAMOUNT\":\"" + totalamt + "\"" //
				+ ",\"ZPRICE0\":\"" + Price0 + "\"" //
				+ ",\"ZDISCOUNT\":\"" + Discount + "\"" //
				+ ",\"ZPRICE\":\"" + Price + "\"" //
				+ ",\"ZCURR\":\"" + totalcurr + "\"";

		errmess = retcs;
		return 1;
	}

	// 保存配货商品捡货颜色尺码明细
	public int Write(JSONObject jsonObject) {
		if (Wareid == 0 || Colorid == 0 || Noteno.equals("")) {
			errmess = "wareid或colorid或noteno不是一个有效的值！";
			return 0;
		}
		if (!jsonObject.has("rows")) {
			errmess = "rows不是一个有效的值！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("rows");
		String qry = "declare ";
		// qry += "\n v_id number;";
		qry += "\n    v_totalamt number;";
		qry += "\n begin ";
		// else qry += " delete from warepeim where accid=" + accid + " and
		// noteno='" + noteno + "' and wareid=" + wareid + " and saleid=" +
		// saleid0 + " ; ";
		String sizelist = "^";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			Sizeid = Long.parseLong(jsonObject2.getString("sizeid"));
			if (!sizelist.contains("^" + Sizeid + "^")) {
				if (!pFunc.isWaresizeOk(Accid, Wareid, Sizeid)) {
					errmess = "sizeid不是一个有效值！";
					return 0;
				}
				sizelist += Sizeid + "^";
			}

			Amount = Float.parseFloat(jsonObject2.getString("factamt"));
			Id = Float.parseFloat(jsonObject2.getString("id"));
			// qry += "\n v_id:=" + decimal.Parse(ht["id" + i].ToString()) +
			// ";";
			qry += "\n   update warepeim set factamt=" + Amount + " where id=" + Id + "  and  accid=" + Accid + " and noteno='" + Noteno + "' ";
			qry += "\n   and wareid=" + Wareid + " and colorid=" + Colorid + " and sizeid=" + Sizeid + " and saleid=" + Saleid + "; ";
		}
		qry += "\n   select nvl(sum(factamt),0) into v_totalamt from warepeim where accid=" + Accid + " and noteno='" + Noteno + "';  ";
		qry += "\n   update warepeih set totalfactamt=v_totalamt where accid=" + Accid + " and noteno='" + Noteno + "';  ";
		qry += "\n   commit; ";
		qry += "\n end; ";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}

	// 保存配货单商品
	public int Write1(JSONObject jsonObject) {
		if (Wareid == 0 || Saleid == 0 || Noteno.equals("")) {
			errmess = "wareid或saleid或noteno不是一个有效的值！";
			return 0;
		}

		if (!jsonObject.has("rows")) {
			errmess = "rows不是一个有效的值！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("rows");
		String qry = "declare ";
		qry += "\n    v_saleid  number;";
		qry += "\n    v_id  number;";
		qry += "\n    v_totalamt number;";
		qry += "\n    v_totalcurr number;";
		qry += "\n begin ";
		qry += "\n     v_saleid:=" + Saleid + ";";
		qry += "\n     update warepeim set amount=0 where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + " and saleid=v_saleid ; ";
		// else qry += " delete from warepeim where accid=" + accid + " and
		// noteno='" + noteno + "' and wareid=" + wareid + " and saleid=" +
		// saleid0 + " ; ";
		// else qry += " delete from warepeim where accid=" + accid + " and
		// noteno='" + noteno + "' and wareid=" + wareid + " and saleid=" +
		// saleid0 + " ; ";

		// System.out.println(qry);
		String sizelist = "^";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			Sizeid = Long.parseLong(jsonObject2.getString("sizeid"));
			if (!sizelist.contains("^" + Sizeid + "^")) {
				if (!pFunc.isWaresizeOk(Accid, Wareid, Sizeid)) {
					errmess = "sizeid不是一个有效值！";
					return 0;
				}
				sizelist += Sizeid + "^";
			}
			Colorid = Long.parseLong(jsonObject2.getString("colorid"));
			Amount = Float.parseFloat(jsonObject2.getString("amount"));
			Price0 = Float.parseFloat(jsonObject2.getString("price0"));

			Discount = Float.parseFloat(jsonObject2.getString("discount"));

			Price = Float.parseFloat(jsonObject2.getString("price"));
			Curr = Func.getRound(Amount * Price, 2);
			Remark0 = jsonObject2.getString("remark0");
			qry += "\n  begin ";
			qry += "\n    select id into v_id from warepeim where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + " and colorid=" + Colorid + " and sizeid=" + Sizeid
					+ " and saleid=v_saleid and rownum=1; ";
			qry += "\n    update warepeim set amount=" + Amount + ",discount=" + Discount + ",price=" + Price + ",curr=" + Curr + ",remark0='" + Remark0 + "' where id=v_id and  accid=" + Accid + " and noteno='" + Noteno
					+ "';";
			qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN"; // 未找到，插入新记录
			qry += "\n    insert into warepeim (id,accid,noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,curr,remark0,lastdate)";
			qry += "\n    values (warepeim_id.nextval," + Accid + ",'" + Noteno + "'," + Wareid + " ," + Colorid + "," + Sizeid + ",v_saleid," + Amount + "," + Price0 + "," + Discount + "," + Price + "," + Curr + ",'"
					+ Remark0 + "',sysdate);";
			qry += "\n end; ";
		}

		qry += "\n   delete from warepeim where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + " and saleid=v_saleid and amount=0; ";

		qry += "\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from warepeim where accid=" + Accid + " and noteno='" + Noteno + "';  ";
		qry += "\n   update warepeih set totalcurr=v_totalcurr,totalamt=v_totalamt  ";
		qry += "\n   where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   commit; ";
		qry += "\n end; ";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}

	// 成批增加商品配货数据
	public int Addbatch(JSONObject jsonObject) {
		if (Saleid == 0) {
			errmess = "saleid不是一个有效值！";
			return 0;
		}
		if (!jsonObject.has("rows")) {

			errmess = "rows无效！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("rows");
		if (jsonArray.size() <= 0) {
			errmess = "rows记录数无效！";
			return 0;
		}

		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		int statetag = jsonObject.has("statetag") ? Integer.parseInt(jsonObject.getString("statetag")) : 0;
		String handno = jsonObject.has("handno") ? jsonObject.getString("handno") : null;
		String remark = jsonObject.has("remark") ? jsonObject.getString("remark") : null;

		String qry = "declare ";
		qry += "\n    v_totalcurr  number;";
		qry += "\n    v_totalamt  number;";
		qry += "\n    v_accid  number;";
		qry += "\n    v_wareid  number;";
		qry += "\n    v_id  number;";
		qry += "\n    v_noteno  varchar2(20);";
		qry += "\n    v_retcs  varchar2(100);";
		qry += "\n begin ";
		String sizelist = "^";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			Sizeid = Long.parseLong(jsonObject2.getString("sizeid"));
			if (!sizelist.contains("^" + Sizeid + "^")) {
				if (!pFunc.isWaresizeOk(Accid, Wareid, Sizeid)) {
					errmess = "sizeid不是一个有效值！";
					return 0;
				}
				sizelist += Sizeid + "^";
			}
			Colorid = Long.parseLong(jsonObject2.getString("colorid"));
			Amount = Float.parseFloat(jsonObject2.getString("amount"));
			Curr = Func.getRound(Amount * Price, 2);
			qry += "\n  begin ";
			qry += "\n    select id into v_id from warepeim where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + " and colorid=" + Colorid + " and sizeid=" + Sizeid + "  and saleid=" + Saleid
					+ " and rownum=1; ";
			qry += "\n    update warepeim set amount=amount+" + Amount + ",curr=(amount+" + Amount + ")*price where id=v_id;";
			qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN"; // 未找到，插入新记录
			qry += "\n    insert into warepeim (id,accid,noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,curr,remark0)";
			qry += "\n    values (warepeim_id.nextval," + Accid + ",'" + Noteno + "'," + Wareid + "," + Colorid + "," + Sizeid + "," + Saleid + "," + Amount + "," + Price0 + "," + Discount + "," + Price + "," + Curr
					+ ",'');";
			qry += "\n end; ";
		}

		qry += "\n  select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from warepeim where accid=" + Accid + " and noteno='" + Noteno + "';";

		qry += "\n  update warepeih set totalcurr=v_totalcurr,totalamt=v_totalamt,lastdate=sysdate ";
		if (remark != null)
			qry += ",remark='" + remark + "'";
		if (handno != null)
			qry += ",handno='" + handno + "'";
		qry += "     where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n  commit; ";

		if (statetag == 1 && houseid > 0) // 要判断可用库存
		{
			// 要判断负库存
			qry += " begin";
			qry += "   select b.wareno||','||c.colorname||','||d.sizename into v_retcs from ";
			qry += "   (select wareid,colorid,sizeid,sum(amount) as amount from warepeim where accid=" + Accid + " and noteno='" + Noteno + "'";
			qry += "   group by wareid,colorid,sizeid) a";
			qry += "   left outer join warecode b on a.wareid=b.wareid";
			qry += "   left outer join colorcode c on a.colorid=c.colorid";
			qry += "   left outer join sizecode d on a.sizeid=d.sizeid";
			qry += "   where a.amount>f_getwareamountx(to_char(sysdate,'yyyy-mm-dd')," + Accid + ",a.wareid,a.colorid,a.sizeid," + houseid + ",'" + Calcdate + "') and rownum=1;";
			qry += "   v_retcs:=  '0'||v_retcs||'可用库存不足，不允许配货！' ; ";
			qry += "   goto exit; ";

			qry += " EXCEPTION WHEN NO_DATA_FOUND THEN ";
			qry += "   v_retcs:=''; ";
			qry += " end;";

		}
		qry += "\n  commit; ";
		qry += "\n  v_retcs:='1操作成功！' ; ";
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

	// 成批更改采购入库折扣
	public int ChangeDisc(int priceprec) {
		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "   v_discount number;";
		qry += "   v_accid number;";
		qry += "   v_noteno varchar2(20);";
		qry += "   v_totalcurr number;";
		qry += "begin ";
		qry += "   v_discount:=" + Discount + ";";
		qry += "   update warepeim set discount=v_discount,price=round(price0*v_discount," + priceprec + "),curr=amount*round(price0*v_discount," + priceprec + ")";
		qry += "     where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "   select nvl(sum(curr),0) into v_totalcurr from warepeim where  accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "   update warepeih set totalcurr=v_totalcurr where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "   commit; ";
		qry += "end; ";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}

	// 按尺码自动配货
	public int doSizetoWarepeim(JSONObject jsonObject, int priceprec) {
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		long custid = jsonObject.has("custid") ? Long.parseLong(jsonObject.getString("custid")) : 0;
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		long colorid = jsonObject.has("colorid") ? Long.parseLong(jsonObject.getString("colorid")) : 0;
		long typeid = jsonObject.has("typeid") ? Long.parseLong(jsonObject.getString("typeid")) : -1;
		long brandid = jsonObject.has("brandid") ? Long.parseLong(jsonObject.getString("brandid")) : -1;

		String sizegroupno = jsonObject.has("sizegroupno") ? jsonObject.getString("sizegroupno") : "";
		String wareno = jsonObject.has("wareno") ? jsonObject.getString("wareno") : "";
		String warename = jsonObject.has("warename") ? jsonObject.getString("warename") : "";
		String seasonname = jsonObject.has("seasonname") ? jsonObject.getString("seasonname") : "";
		String prodyear = jsonObject.has("prodyear") ? jsonObject.getString("prodyear") : "";
		if (Func.isNull(noteno)) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		if (custid == 0 || houseid == 0) {
			errmess = "custid或houseid不是一个有效值！";
			return 0;
		}

		if (!jsonObject.has("sizeordlist")) {
			errmess = "尺码订货sizeordlist参数无效！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("sizeordlist");
		if (jsonArray.size() <= 0) {
			errmess = "尺码订货sizeordlist参数个数无效！";
			return 0;
		}

		long saleid = 2; // 1=定单 2=首单
		String qry1 = "";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			qry1 += "\n           v_sizeid:=" + jsonObject2.getString("sizeid") + ";";
			qry1 += "\n           v_balamt:=" + jsonObject2.getString("amount") + ";"; // 配货数
			qry1 += "\n           select nvl(sum(amount),0) into v_kcamt from v_cxkczy_data where epid= " + Userid + " and wareid=v_wareid and colorid=v_colorid and sizeid=v_sizeid;";
			qry1 += "\n           if v_kcamt>0 and v_balamt>0 then ";
			qry1 += "\n              if v_kcamt<v_balamt then  "; // 可用库存小于配货数
			qry1 += "\n                 v_balamt:=v_kcamt; ";
			qry1 += "\n              end if; ";
			qry1 += "\n              v_curr:=v_price*v_balamt; ";
			qry1 += "\n              begin";
			qry1 += "\n                select id into v_id from warepeim where accid=" + Accid + " and noteno='" + noteno + "' and wareid=v_wareid and colorid=v_colorid and sizeid=v_sizeid and saleid=" + Saleid + "; ";
			qry1 += "\n                update warepeim set amount=amount+v_balamt,curr=(amount+v_balamt)*price where id=v_id and accid=" + Accid + " and noteno='" + noteno + "';";
			qry1 += "\n              EXCEPTION WHEN NO_DATA_FOUND THEN";
			qry1 += "\n                insert into warepeim (id,accid,noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,curr,remark0) ";
			qry1 += "\n                values (warepeim_id.nextval," + Accid + ",'" + noteno + "',v_wareid,v_colorid,v_sizeid," + Saleid + ",v_balamt,v_price0,v_discount,v_price,v_curr,'自动配货'); ";
			qry1 += "\n              end;";
			qry1 += "\n              v_count:=v_count+1; ";
			qry1 += "\n              if v_count>1000 then  ";
			qry1 += "\n                 v_count:=0; ";
			qry1 += "\n                 commit; ";
			qry1 += "\n              end if; ";
			qry1 += "\n           end if; ";
		}
		vTotalCxkczy kc = new vTotalCxkczy();
		kc.setUserid(Userid);
		kc.setAccid(Accid);
		kc.setHouseid(houseid);
		kc.setWareid(wareid);
		kc.setColorid(colorid);
		kc.setBrandid(brandid);
		kc.setTypeid(typeid);
		kc.setWareno(wareno);
		kc.setWarename(warename);
		kc.setSeasonname(seasonname);
		kc.setProdyear(prodyear);
		kc.setSizegroupno(sizegroupno);
		kc.setBj(2); // bj:1=只查询v_xskczy_data 中的商品的库存,2=减配货占用(返回可用库存)
					// ,3=只查询v_salewhere_data中的商品的库存
		kc.doCxkczy();
		// myTotalCxkczy("", userid, accid, houseid, wareid, colorid, 0, typeid,
		// brandid, -1, -1, wareno, warename, seasonname, prodyear, sizegroupno,
		// "", 0, 2);
		String qry = "declare";
		qry += "\n   v_totalcurr number; ";
		qry += "\n   v_totalamt number; ";
		qry += "\n   v_price number; ";
		qry += "\n   v_price0 number; ";
		qry += "\n   v_discount number; ";
		qry += "\n   v_curr number; ";
		qry += "\n   v_balamt number; ";
		qry += "\n   v_kcamt number; ";
		qry += "\n   v_amount number; ";
		qry += "\n   v_count number; ";
		qry += "\n   v_pricetype number(1); ";
		qry += "\n   v_id number; ";
		qry += "\n   v_num number; ";
		qry += "\n   v_wareid number(10); ";
		qry += "\n   v_wareid1 number(10); ";
		qry += "\n   v_colorid number(10); ";
		qry += "\n   v_sizeid number(10); ";
		qry += "\n begin ";
		qry += "\n   select pricetype,discount into v_pricetype,v_discount from customer where custid=" + custid + "; ";

		qry += "\n   declare cursor v_tempdata is";
		qry += "\n     select wareid,colorid,count(*) as num ";
		qry += "\n     from v_cxkczy_data where epid= " + Userid;
		qry += "\n     and f_getwareoutsum(to_char(sysdate,'yyyy-mm-dd')," + Accid + ",wareid," + custid + ")=0"; // 未发过货的商品
		qry += "\n     group by wareid,colorid ";
		// qry += "\n having sum(amount)>0 ";
		qry += "\n     order by wareid; ";
		// qry += "\n v_row v_tempdata%rowtype; ";
		qry += "\n  begin ";
		qry += "\n    v_count:=0; ";
		qry += "\n    open v_tempdata; ";
		qry += "\n    fetch v_tempdata  into v_wareid,v_colorid,v_num;  ";
		qry += "\n    while v_tempdata%found loop  ";
		qry += "\n       v_wareid1:=v_wareid; ";
		qry += "\n       select case v_pricetype when 0 then retailsale  when 1 then sale1 when 2 then sale2 when 3 then sale3 when 4 then sale4 when 5 then sale5 else retailsale end ";
		qry += "\n       into v_price0 from warecode where wareid=v_wareid1; ";
		qry += "\n       v_price:=round(v_price0*v_discount," + priceprec + "); ";
		qry += "\n       while v_tempdata%found and v_wareid=v_wareid1 loop  ";
		qry += qry1;
		qry += "\n          fetch v_tempdata  into v_wareid,v_colorid,v_num;  ";
		qry += "\n       end loop;    ";
		qry += "\n    end loop; ";
		qry += "\n    commit; ";
		qry += "\n    close v_tempdata; ";
		qry += "\n  end; ";
		qry += "\n  select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from warepeim where accid=" + Accid + " and noteno='" + noteno + "';  ";
		qry += "\n  update warepeih set totalcurr=v_totalcurr,totalamt=v_totalamt  where accid=" + Accid + " and noteno='" + noteno + "'; ";
		qry += "\n end; ";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 按未完定单自动配货
	public int doOrdertoWarepeim(JSONObject jsonObject, int priceprec) {
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		long custid = jsonObject.has("custid") ? Long.parseLong(jsonObject.getString("custid")) : 0;
		String noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : "";
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		long colorid = jsonObject.has("colorid") ? Long.parseLong(jsonObject.getString("colorid")) : 0;
		long typeid = jsonObject.has("typeid") ? Long.parseLong(jsonObject.getString("typeid")) : -1;
		long brandid = jsonObject.has("brandid") ? Long.parseLong(jsonObject.getString("brandid")) : -1;

		String wareno = jsonObject.has("wareno") ? jsonObject.getString("wareno") : "";
		String warename = jsonObject.has("warename") ? jsonObject.getString("warename") : "";
		String seasonname = jsonObject.has("seasonname") ? jsonObject.getString("seasonname") : "";
		String prodyear = jsonObject.has("prodyear") ? jsonObject.getString("prodyear") : "";
		if (Func.isNull(noteno)) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		if (custid == 0 || houseid == 0) {
			errmess = "custid或houseid不是一个有效值！";
			return 0;
		}
		long saleid = 1; // 1=定单 2=首单
		String qry = "declare";
		qry += "\n   v_nowdate varchar2(10);";
		qry += "\n   v_nowdate0 varchar2(10); ";
		qry += "\n   v_nowdate1 varchar2(10); ";
		qry += "\n   v_totalcurr number; ";
		qry += "\n   v_totalamt number; ";
		qry += "\n   v_price number; ";
		qry += "\n   v_price0 number; ";
		qry += "\n   v_discount number; ";
		qry += "\n   v_curr number; ";
		qry += "\n   v_balamt number; ";
		qry += "\n   v_kcamt number; ";
		qry += "\n   v_amount number; ";
		qry += "\n   v_count number; ";
		qry += "\n   v_pricetype number(1); ";
		qry += "\n   v_wareid number(10); ";
		qry += "\n   v_wareid1 number(10); ";
		qry += "\n   v_colorid number(10); ";
		qry += "\n   v_sizeid number(10); ";
		qry += "\n begin ";
		qry += "\n   v_nowdate:=to_char(sysdate,'yyyy-mm-dd'); ";
		qry += "\n   v_nowdate0:=to_char(to_date(v_nowdate,'yyyy-mm-dd')-1,'yyyy-mm-dd'); ";
		qry += "\n   v_nowdate1:=to_char(to_date(v_nowdate,'yyyy-mm-dd')+1,'yyyy-mm-dd'); ";
		qry += "\n   select pricetype,discount into v_pricetype,v_discount from customer where custid=" + custid + "; ";
		// --删除当前配货单据明细记录
		qry += "\n   delete from warepeim where accid=" + Accid + " and noteno='" + noteno + "';  ";

		qry += "\n   declare cursor v_tempdata is ";
		// --查找订货余额
		qry += "\n   select wareid,colorid,sizeid,sum(amount) as amount from ( ";
		// --昨日未完订货余额
		qry += "\n   select a.wareid,a.colorid,a.sizeid,a.amount  ";
		qry += "\n   from custorderbal a  ";
		qry += "\n   left outer join warecode c on a.wareid=c.wareid";
		qry += "\n   where a.daystr=v_nowdate0 and a.custid=" + custid;
		if (wareid > 0)
			qry += "      and a.wareid=" + wareid;
		if (colorid > 0)
			qry += "      and a.colorid=" + colorid;
		// if (sizeid > 0) qry += " and b.sizeid=" + sizeid;
		if (wareno.length() > 0)
			qry += "      and c.wareno like '%" + wareno + "%'";
		if (warename.length() > 0)
			qry += "      and c.warename like '%" + warename + "%'";
		if (seasonname.length() > 0)
			qry += "      and c.seasonname='" + seasonname + "'";
		if (prodyear.length() > 0)
			qry += "      and c.prodyear='" + prodyear + "'";
		if (typeid >= 0)
			if (typeid > 0 && typeid < 1000) {
				qry += "  and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + typeid + ") ";
			} else {
				qry += "    and c.typeid=" + typeid;
			}

		if (brandid >= 0)
			qry += "      and c.brandid=" + brandid;

		qry += "\n   union all ";
		// --今日新增订货
		qry += "\n   select b.wareid,b.colorid,b.sizeid,sum(b.amount) as amount ";
		qry += "\n   from custorderh a join custorderm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n   left outer join warecode c on b.wareid=c.wareid";
		qry += "\n   where a.accid=" + Accid + " and a.custid=" + custid + " and a.statetag=1 ";
		qry += "\n   and a.notedate>=to_date(v_nowdate,'yyyy-mm-dd') ";
		qry += "\n   and a.notedate<to_date(v_nowdate1,'yyyy-mm-dd') ";
		if (wareid > 0)
			qry += "      and b.wareid=" + wareid;
		if (colorid > 0)
			qry += "      and b.colorid=" + colorid;

		if (wareno.length() > 0)
			qry += "      and c.wareno like '%" + wareno + "%'";
		if (warename.length() > 0)
			qry += "      and c.warename like '%" + warename + "%'";
		if (seasonname.length() > 0)
			qry += "      and c.seasonname='" + seasonname + "'";
		if (prodyear.length() > 0)
			qry += "      and c.prodyear='" + prodyear + "'";
		if (typeid >= 0)
			if (typeid > 0 && typeid < 1000) {
				qry += "  and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + typeid + ") ";
			} else {
				qry += "    and c.typeid=" + typeid;
			}

		if (brandid >= 0)
			qry += "      and c.brandid=" + brandid;

		qry += "\n   group by b.wareid,b.colorid,b.sizeid ";
		qry += "\n   union all ";
		// --今日发货
		qry += "\n   select b.wareid,b.colorid,b.sizeid,-sum(b.amount) as amount  ";
		qry += "\n   from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n   left outer join warecode c on b.wareid=c.wareid";
		qry += "\n   where a.accid=" + Accid + " and a.custid=" + custid + " and a.statetag=1 and a.ntid=1 and b.saleid=1 ";
		qry += "\n   and a.notedate>=to_date(v_nowdate,'yyyy-mm-dd') and a.notedate<to_date(v_nowdate1,'yyyy-mm-dd') ";

		if (wareid > 0)
			qry += "      and b.wareid=" + wareid;
		if (colorid > 0)
			qry += "      and b.colorid=" + colorid;

		if (wareno.length() > 0)
			qry += "      and c.wareno like '%" + wareno + "%'";
		if (warename.length() > 0)
			qry += "      and c.warename like '%" + warename + "%'";
		if (seasonname.length() > 0)
			qry += "      and c.seasonname='" + seasonname + "'";
		if (prodyear.length() > 0)
			qry += "      and c.prodyear='" + prodyear + "'";
		if (typeid >= 0)
			if (typeid > 0 && typeid < 1000) {
				qry += "  and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + typeid + ") ";
			} else {
				qry += "    and c.typeid=" + typeid;
			}

		if (brandid >= 0)
			qry += "      and c.brandid=" + brandid;

		qry += "\n   and exists (select 1 from custorderh a1 join custorderm b1 on a1.accid=b1.accid and a1.noteno=b1.noteno ";
		qry += "\n   where a1.statetag=1 and a1.accid=" + Accid + " and a1.custid=" + custid + " and a.notedate<to_date(v_nowdate1,'yyyy-mm-dd') ";
		qry += "\n    and b.wareid=b1.wareid and b.colorid=b1.colorid and b.sizeid=b1.sizeid ) ";

		qry += "\n    group by b.wareid,b.colorid,b.sizeid ";

		qry += "\n    ) group by wareid,colorid,sizeid  having sum(amount)>0  ";
		qry += "\n    order by wareid; ";
		qry += "\n      v_row v_tempdata%rowtype; ";
		qry += "\n   begin ";
		qry += "\n     v_count:=0; ";
		qry += "\n     open v_tempdata; ";
		qry += "\n     fetch v_tempdata  into v_wareid,v_colorid,v_sizeid,v_amount;  ";
		qry += "\n     while v_tempdata%found loop  ";
		qry += "\n        v_wareid1:=v_wareid; ";
		qry += "\n        select case v_pricetype when 0 then retailsale  when 1 then sale1 when 2 then sale2 when 3 then sale3 when 4 then sale4 when 5 then sale5 else retailsale end ";
		qry += "\n          into v_price0 from warecode where wareid=v_wareid1; ";
		qry += "\n        v_price:=round(v_price0*v_discount," + priceprec + "); ";
		qry += "\n        while v_tempdata%found and v_wareid=v_wareid1 loop  ";
		qry += "\n           v_balamt:=v_amount; ";// --订货余额
		qry += "\n           v_kcamt:= f_getwareuseamount(to_char(sysdate,'yyyy-mm-dd')," + Accid + ",v_wareid,v_colorid,v_sizeid," + houseid + "); ";// --取可用库存数
		qry += "\n           if v_kcamt>0 and v_balamt>0 then ";
		qry += "\n             if v_kcamt<v_balamt then  ";
		qry += "\n                v_balamt:=v_kcamt; ";
		qry += "\n             end if; ";
		qry += "\n             v_curr:=v_price*v_balamt; ";
		qry += "\n             insert into warepeim (id,accid,noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,curr,remark0) ";
		qry += "\n             values (warepeim_id.nextval," + Accid + ",'" + noteno + "',v_wareid,v_colorid,v_sizeid," + saleid + ",v_balamt,v_price0,v_discount,v_price,v_curr,'自动配货'); ";
		qry += "\n             v_count:=v_count+1; ";
		qry += "\n             if v_count>1000 then  ";
		qry += "\n                 v_count:=0; ";
		qry += "\n                commit; ";
		qry += "\n             end if; ";
		qry += "\n           end if; ";

		qry += "\n           fetch v_tempdata  into v_wareid,v_colorid,v_sizeid,v_amount;  ";
		qry += "\n        end loop;   ";
		qry += "\n     end loop; ";
		qry += "\n     close v_tempdata; ";
		qry += "\n     commit; ";
		qry += "\n   end;";
		qry += "\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from warepeim where accid=" + Accid + " and noteno='" + noteno + "';  ";
		qry += "\n   update warepeih set totalcurr=v_totalcurr,totalamt=v_totalamt  ";
		qry += "\n   where accid=" + Accid + " and noteno='" + noteno + "';";

		qry += "\n end;";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 获取商品+颜色汇总列表
	public int doGetColorsumList(QueryParam qp, int sizenum, int priceprec) {
		if (Noteno.equals("")) {
			errmess = "wareid或noteno不是一个有效的值！";
			return 0;
		}

		String qry = "select a.wareid,b.wareno,b.warename,b.units,b.sizegroupno,b.imagename0,a.colorid,c.colorname,min(id) as id,sum(a.amount) as amount";
		qry += "\n ,case sum(a.amount) when 0 then 0 else round(sum(a.amount*a.price0)/sum(a.amount)," + priceprec + ") end as price0";
		qry += "\n ,case sum(a.amount*a.price0) when 0 then 1 else round(sum(a.curr)/sum(a.amount*a.price0),2) end as discount";
		qry += "\n ,case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount)," + priceprec + ") end as price";
		qry += "\n ,sum(a.curr) as curr";
		qry += "\n from custorderm a ";
		qry += "\n left outer join warecode b on a.wareid=b.wareid";
		qry += "\n left outer join colorcode c on a.colorid=c.colorid";
		// qry += "\n left outer join sizecode d on a.sizeid=d.sizeid";
		qry += "\n where a.ACCID=" + Accid + " and a.NOTENO='" + Noteno + "'";
		qry += "\n group by a.wareid,b.wareno,b.warename,b.units,b.sizegroupno,b.imagename0,a.colorid,c.colorname";

		Table tb = new Table();
		qp.setQueryString(qry);
		qp.setSumString("nvl(sum(amount),0) as totalamt,nvl(sum(curr),0) as totalcurr");
		String retcs;
		tb = DbHelperSQL.GetTable(qp);
		int count = tb.getRowCount();
		String fh = "";
		String fh1 = "";
		if (sizenum <= 0) // 不显示尺码
		{
			retcs = "{\"result\":1,\"msg\":\"�����ɹ���\"," + qp.getTotalString() + ",\"rows\":[";
			for (int i = 0; i < count; i++) {
				fh1 = "";
				retcs += fh + "{";
				for (int j = 0; j < tb.getColumnCount(); j++) {
					String strKey = tb.getRow(i).getColumn(j + 1).getName().toUpperCase();
					String strValue = tb.getRow(i).get(j).toString();
					retcs += fh1 + "\"" + strKey + "\":\"" + strValue + "\"";
					// jsonString.append(fh1 + "\"" + strKey + "\":\"" +
					// strValue + "\"");
					fh1 = ",";
				}
				retcs += "}";
				fh = ",";
			}
			retcs += "]}";
			errmess = retcs;
			return 1;
		}
		// System.out.println("doGetWareinmcolorsumList 1.6");
		String sqlsize = "";
		String sqlsum = "";
		String qry1;
		// long wareid;
		long wareid0 = 0;
		// long colorid;
		retcs = "{\"result\":1,\"msg\":\"�����ɹ���\"," + qp.getTotalString() + ",\"rows\":[";
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
			fh1 = "";
			for (int j = 0; j < tb.getColumnCount(); j++) {
				String strKey = tb.getRow(i).getColumn(j + 1).getName().toUpperCase();
				String strValue = tb.getRow(i).get(j).toString();
				retcs += fh1 + "\"" + strKey + "\":\"" + strValue + "\"";
				// jsonString.append(fh1 + "\"" + strKey + "\":\"" + strValue +
				// "\"");
				fh1 = ",";
			}

			// 取尺码数据
			qry1 = "select " + sqlsum + "," + sqlsize;
			qry1 += " from warepeim a where accid=" + Accid + " and noteno='" + Noteno + "'";
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

}