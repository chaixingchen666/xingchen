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
//  @author:sunhong  @date:2017-03-06 10:18:53
//*************************************
public class Wareoutm implements java.io.Serializable {
	private Float Id;
	private String Noteno;
	private Long Userid;
	private Long Accid;
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
	private String Boxno;
	private Date Lastdate;
	private Long Areaid;
	private String errmess;
	private String Lastop;
	private String Calcdate = ""; // 账套开始使用日期

	public void setCalcdate(String calcdate) {
		Calcdate = calcdate;
	}

	public void setBoxno(String boxno) {
		Boxno = boxno;
	}

	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public void setNoteno(String noteno) {
		this.Noteno = noteno;
	}

	public String getNoteno() {
		return Noteno;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public void setUserid(Long userid) {
		this.Userid = userid;
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

	public void setAreaid(Long areaid) {
		this.Areaid = areaid;
	}

	public Long getAreaid() {
		return Areaid;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }

	public int DeletebyId() {
		if (Id == 0 || Noteno.length() <= 0) {
			errmess = "id或noteno不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("   v_totalcurr number; ");
		strSql.append("   v_totalamt number; ");
		strSql.append("begin ");
		strSql.append("  delete from wareoutm ");
		strSql.append("  where id=" + Id + " and accid= " + Accid + " and noteno='" + Noteno + "';");
		// 汇总数量及金额
		strSql.append("\n  select  nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n  update wareouth set totalcurr=v_totalcurr,totalamt=v_totalamt ");
		strSql.append(" where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append(" commit;");
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
	public int Delete() {
		if (Wareid == 0 || Colorid == 0 || Noteno.length() <= 0) {
			errmess = "wareid或colorid或noteno不是一个有效值！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();

		strSql.append("declare ");
		strSql.append("\n   v_totalcurr number; ");
		// strSql.append("\n v_totalcheckcurr number; ");
		strSql.append("\n   v_totalamt number; ");
		strSql.append("\n begin ");
		strSql.append("\n   delete from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + " and colorid=" + Colorid + "; ");
		strSql.append("\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n   update wareouth set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno='" + Noteno + "';");
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
		if (Noteno != null) {
			strSql1.append("noteno,");
			strSql2.append("'" + Noteno + "',");
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
		if (Areaid != null) {
			strSql1.append("areaid,");
			strSql2.append(Areaid + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=wareoutm_id.nextval; ");
		strSql.append("   insert into wareoutm (");
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
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 修改记录
	public int Update(JSONObject jsonObject, int priceprec, int fs, int bj) {// bj=0零售，1=批发
		if (Func.isNull(Noteno) || Id == null || Id == 0) {
			errmess = "noteno或id不是一个有效值！";
			return 0;
		}
		if (Wareid == null || Wareid == 0 || Colorid == null || Colorid == 0 || Sizeid == null || Sizeid == 0) {
			errmess = "wareid或colorid或sizeid不是一个有效值！";
			return 0;
		}
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		// int statetag = jsonObject.has("statetag") ?
		// Integer.parseInt(jsonObject.getString("statetag")) : 0;
		int tocash = jsonObject.has("tocash") ? Integer.parseInt(jsonObject.getString("tocash")) : 0;
		int allpricechange = jsonObject.has("allpricechange") ? Integer.parseInt(jsonObject.getString("allpricechange")) : 0;

		int ntid = jsonObject.has("ntid") ? Integer.parseInt(jsonObject.getString("ntid")) : 0;

		if (Saleid == null || bj == 0)
			Saleid = (long) 0;
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
		if (Amount == null || Amount == 0) {
			errmess = "数量不允许为零！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("   v_totalcurr number; ");
		strSql.append("   v_totalamt number; ");
		strSql.append("   v_saleid number; ");
		strSql.append("   v_custid number; ");
		strSql.append("   v_count number; ");
		strSql.append("   v_retcs varchar2(200); ");
		strSql.append("\n begin ");

		strSql.append("\n   select count(*) into v_count from warecode where accid=" + Accid + " and statetag=1 and wareid=" + Wareid + ";");
		strSql.append("\n   if v_count=0 then");
		strSql.append("\n      v_retcs:='0商品无效';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   select count(*) into v_count from warecolor where wareid=" + Wareid + "  and colorid=" + Colorid + ";");
		strSql.append("\n   if v_count=0 then");
		strSql.append("\n      v_retcs:='0商品颜色无效';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		// strSql.append("\n select count(*) into v_count from sizecode where
		// accid="+Accid+" and statetag=1 and sizeid="+Sizeid+";");
		strSql.append("\n   select count(*) into v_count from sizecode a join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno ");
		strSql.append("\n   where a.accid=" + Accid + " and b.wareid=" + Wareid + " and a.sizeid=" + Sizeid + ";");

		strSql.append("\n   if v_count=0 then");
		strSql.append("\n      v_retcs:='0商品尺码无效';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		if (bj == 1) {
			strSql.append("\n   select count(*) into v_count from salecode where accid=" + Accid + " and statetag=1 and saleid>0 and saleid=" + Saleid + ";");
			strSql.append("\n   if v_count=0 then");
			strSql.append("\n      v_retcs:='0销售类型无效';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");

		}
		if (ntid == 1 && Saleid >= 2 && Saleid <= 3) // 如果是批发，且销售类型是首单及补货，要校验
		{

			strSql.append("\n   select custid into v_custid from wareouth where accid=" + Accid + " and noteno='" + Noteno + "';");
			strSql.append("\n   select  count(*) into v_count from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ");
			strSql.append("\n   where a.statetag=1 and rownum=1 and a.ntid=1 and a.accid=" + Accid);
			strSql.append("\n   and  b.wareid=" + Wareid + " and b.colorid=" + Colorid + " and a.custid=v_custid;");
			strSql.append("\n   if v_count=0 then v_saleid:=2; else v_saleid:=3; end if; ");
		}

		strSql.append("\n   update wareoutm set ");

		// if (Wareid != null) {
		strSql.append("wareid=" + Wareid + ",");
		// }
		// if (Colorid != null) {
		strSql.append("colorid=" + Colorid + ",");
		// }
		// if (Sizeid != null) {
		strSql.append("sizeid=" + Sizeid + ",");
		// }
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
		if (Areaid != null) {
			strSql.append("areaid=" + Areaid + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=" + Id + " and accid=" + Accid + " and noteno='" + Noteno + "';");
		if (allpricechange == 1) // allpricechange=1 要更改所有同款价格)
		{
			strSql.append("  update wareoutm set  lastdate=sysdate");
			strSql.append("  ,price0=" + Price0);
			strSql.append("  ,discount=" + Discount);
			strSql.append("  ,price=" + Price);
			strSql.append("  ,curr=round(" + Price + "*amount,2)");
			strSql.append("  where id<>" + Id + " and wareid=" + Wareid + " and accid=" + Accid + " and noteno='" + Noteno + "'; ");
		}

		// 汇总数量及金额
		strSql.append("\n  select  nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n  update wareouth set totalcurr=v_totalcurr,totalamt=v_totalamt ");
		// if (tocash == 1) strSql.Append(",statetag=3 ,lastdate=sysdate");
		strSql.append("\n where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n commit;");

		// if ((statetag == 1|| tocash==1) && (ntid == 1 || ntid == 0) && fs ==
		// 0 && houseid > 0) //判断库存是否负出库
		if (tocash == 1 && (ntid == 1 || ntid == 0) && fs == 0 && houseid > 0) // 判断库存是否负出库
		{
			strSql.append("\n begin");
			strSql.append("\n   select b.wareno||','||c.colorname||','||d.sizename into v_retcs from ");
			strSql.append("\n   (select wareid,colorid,sizeid,sum(amount) as amount from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "'");
			strSql.append("\n   group by wareid,colorid,sizeid) a");
			strSql.append("\n   left outer join warecode b on a.wareid=b.wareid");
			strSql.append("\n   left outer join colorcode c on a.colorid=c.colorid");
			strSql.append("\n   left outer join sizecode d on a.sizeid=d.sizeid");
			strSql.append("\n   where a.amount>f_getwareamountx(to_char(sysdate,'yyyy-mm-dd')," + Accid + ",a.wareid,a.colorid,a.sizeid," + houseid + ",'" + Calcdate + "') and rownum=1;");
			strSql.append("\n   v_retcs:='0'||v_retcs||'库存不足，不允许出库！' ; ");
			strSql.append("\n   goto exit; ");

			strSql.append("\n EXCEPTION WHEN NO_DATA_FOUND THEN ");
			strSql.append("\n   v_retcs:=''; ");
			strSql.append("\n end;");

		}
		if (tocash == 1) // 传收银台
		{
			strSql.append("\n   select count(*) into v_count from waresaleman where accid=" + Accid + " and noteno='" + Noteno + "'; "); // 销售人员数
			strSql.append("\n   update wareouth set statetag=3,notedate=sysdate,lastdate=sysdate,rs=v_count");
			strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "';");
			strSql.append("\n   p_wareoutsetarea(" + Accid + ",'" + Noteno + "');");
		}
		/*
		 * else if (statetag == 1) { strSql.
		 * Append(" select count(*) into v_count from waresaleman where accid="
		 * + model.accid + " and noteno='" + model.noteno + "'; "); //销售人员数
		 * strSql.
		 * Append(" update wareouth set statetag=1,notedate=sysdate,lastdate=sysdate,rs=v_count"
		 * ); strSql.Append(" where accid=" + model.accid + " and noteno='" +
		 * model.noteno + "';"); }
		 */
		strSql.append("\n  commit;");
		strSql.append("\n  v_retcs:= '1操作成功！';");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs into :retcs from dual;");
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

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM wareoutm ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获得数据列表
	public DataSet GetList0(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM wareoutm a");
		strSql.append(" left outer join warecode b on a.wareid=b.wareid");
		strSql.append(" left outer join colorcode c on a.colorid=c.colorid");
		strSql.append(" left outer join sizecode d on a.sizeid=d.sizeid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获得数据列表
	public DataSet GetList1(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM wareoutm a");
		strSql.append(" left outer join warecode b on a.wareid=b.wareid");
		strSql.append(" left outer join colorcode c on a.colorid=c.colorid");
		strSql.append(" left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append(" left outer join salecode e on a.saleid=e.saleid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM wareoutm ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable0(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		if (qp.getPageIndex() < 0) {// 如果是导出，要显示条码
			strSql.append(", f_getwarebarcode(A.WAREID,A.COLORID,A.SIZEID) as barcode");
		}
		strSql.append("\n FROM wareoutm a");
		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
		strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");
		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		qp.setSumString("sum(amount) as totalamount,sum(curr) as totalcurr,count(distinct '^'||wareid||'^'||colorid||'^') as skc");
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable1(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		if (qp.getPageIndex() < 0) {// 如果是导出，要显示条码
			strSql.append(", f_getwarebarcode(A.WAREID,A.COLORID,A.SIZEID) as barcode");
		}
		strSql.append("\n FROM wareoutm a");
		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
		strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append("\n left outer join salecode e on a.saleid=e.saleid");
		strSql.append("\n left outer join brand f on b.brandid=f.brandid");
		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		qp.setSumString("sum(amount) as totalamount,sum(curr) as totalcurr,count(distinct '^'||wareid||'^'||colorid||'^') as skc");
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from wareoutm");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1
		// and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}

	// 条码扫入
	public int doBarcodein(String barcode, long custid, long houseid, int pricetype, int priceprec, int currprec, int qxbj, int ntid, int nearsaleok) {
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
		qry += "\n    v_discount  number; "; // --折扣
		qry += "\n    v_pricetype  number(1); "; // --价格方式 0=进价1=零价
		qry += "\n    v_amount  number; "; // --个数
		qry += "\n    v_custid number(10); "; //
		qry += "\n    v_wareid number(10); "; //
		qry += "\n    v_colorid number(10); "; //
		qry += "\n    v_saleid number(10); "; //
		qry += "\n    v_saleid0 number(10); "; //
		qry += "\n    v_sizeid number(10); "; //
		qry += "\n    v_brandid number(10); "; //
		qry += "\n    v_barcode0  varchar2(30); "; // --条码
		qry += "\n    v_id  number(20); ";
		qry += "\n    v_id0  number(20); ";
		qry += "\n    v_price number;";
		qry += "\n    v_price0 number; ";
		qry += "\n    v_price1 number; ";
		qry += "\n    v_count number; ";
		// qry += "\n v_curr number; ";
		qry += "\n    v_curr number; ";
		qry += "\n    v_totalcurr number; ";
		qry += "\n    v_totalamt number; ";
		qry += "\n    v_wareno varchar2(40); ";
		qry += "\n    v_warename varchar2(80); ";
		qry += "\n    v_colorname varchar2(40); ";
		qry += "\n    v_sizename varchar2(40); ";
		qry += "\n    v_sizegroupno  varchar2(60); "; // --条码
		qry += "\n    v_groupno  varchar2(60); "; // --条码
		qry += "\n    v_tjtag number(1); ";
		qry += "\n    v_retcs varchar2(200); "; // --条码
		qry += "\n    vt_price1 number(14,2); vt_discount  number(14,2); vt_price  number(14,2); ";
		qry += "\n begin ";
		/*
		 * qry += "\n     v_accid:=" + accid + "; "; qry += "\n     v_noteno:='"
		 * + noteno + "'; "; qry += "\n     v_barcode:='" + barcode + "'; ";
		 */
		qry += "\n    v_amount:=" + Amount + "; ";
		qry += "\n    v_discount:=" + Discount + "; ";
		qry += "\n    v_pricetype:=" + pricetype + "; ";
		if (ntid == 0) { // 零售出库价格方式取店铺的
			qry += "\n   begin";
			qry += "\n      select pricetype into v_pricetype from warehouse where houseid=" + houseid + " and accid=" + Accid + ";";
			qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN    ";
			qry += "\n    v_pricetype:=" + pricetype + "; ";
			qry += "\n   end;";
		}
		qry += "\n    v_saleid:=" + Saleid + "; ";
		qry += "\n    v_custid:=" + custid + "; ";
		// =============================
		qry += "\n    v_count:=0;";
		if (barcode.length() >= 4) {
			qry += "\n   select nvl(min(id),0),nvl(min(barcode),''),count(*) into v_id,v_barcode0,v_count from (select id,barcode from warebarcode where barcode like '%" //
					+ barcode + "%' and accid=" + Accid + " and rownum<=2 ); ";//
		}
		qry += "\n   if v_count<>1 then";
		qry += "\n      begin";
		qry += "\n        select id,barcode into v_id,v_barcode0 from (select id,barcode from warebarcode where barcode=substr('" //
				+ barcode + "',1,length(barcode)) and accid=" + Accid + " order by barcode desc )  where rownum=1; ";
		qry += "\n      EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n        if v_count=0 then";
		qry += "\n           v_retcs:= '0 未找到条码:'||'" + barcode + "！';";
		qry += "\n        else ";
		qry += "\n           v_retcs:= '0 条码:'||'" + barcode + "不唯一！';";
		qry += "\n        end if;";
		qry += "\n        goto exit;";
		qry += "\n      end;";
		qry += "\n   end if;";

		qry += "\n   begin";
		qry += "\n     select a.wareid,a.colorid,a.sizeid,";
		qry += "\n     case v_pricetype when 0 then b.retailsale  when 1 then b.sale1 when 2 then b.sale2 when 3 then b.sale3 when 4 then b.sale4 when 5 then b.sale5 else b.retailsale end";
		qry += "\n       ,b.wareno,b.warename,c.colorname,d.sizename ,b.sizegroupno,d.groupno,b.brandid,b.tjtag ";//
		qry += "\n     into v_wareid,v_colorid,v_sizeid,v_price0 ,v_wareno,v_warename,v_colorname,v_sizename ,v_sizegroupno,v_groupno,v_brandid,v_tjtag ";
		qry += "\n     from warebarcode a  ";
		qry += "\n     left outer join warecode b on a.wareid=b.wareid ";
		qry += "\n     left outer join colorcode c on a.colorid=c.colorid ";
		qry += "\n     left outer join sizecode d on a.sizeid=d.sizeid ";
		qry += "\n     where a.id=v_id and b.sizegroupno=d.groupno and b.statetag=1  ";//
		if (qxbj == 1) // 1 启用权限控制
			qry += "\n and (b.brandid=0 or exists (select 1 from employebrand x where b.brandid=x.brandid and x.epid=" + Userid + "))";
		qry += "\n      ;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_retcs:= '0 商品未授权或条码尺码异常！' ;  ";
		qry += "\n     goto exit;";
		qry += "\n   end;";

		// qry += "\n if v_sizegroupno<>v_groupno then";
		// qry += "\n v_retcs:= '0商品尺码组异常，请检查！' ; ";
		// qry += "\n goto exit;";
		// qry += "\n end if;";
		qry += "\n     if v_tjtag=1 then";// 特价商品不打折
		qry += "\n        v_discount:=1;";
		qry += "\n        v_price:=v_price0;";
		qry += "\n        v_curr:=round(v_price*v_amount," + currprec + ");";
		qry += "\n        goto exit1;";
		qry += "\n     end if;";

		// pricetype:0=零价折扣,1=售价1,2=售价2,3=售价3,4=批发价,5=打包价,6=品牌价格
		if (ntid > 0) // 如果是批发，批发退库,找品牌价和最近售价
		{
			qry += "\n   v_saleid:=f_saleofwareout(" + Accid + ",v_custid,v_wareid,v_colorid,v_saleid," + ntid + "); ";
			// 如果有品牌折扣
			qry += "\n   select count(*) into v_count from custbrand where custid=v_custid and brandid=v_brandid; ";
			qry += "\n   if v_count>0 then ";
			qry += "\n      select pricetype,discount into v_pricetype,v_discount from custbrand where custid=v_custid and brandid=v_brandid; ";
			qry += "\n      select ";
			qry += "\n      case v_pricetype when 0 then b.retailsale  when 1 then b.sale1 when 2 then b.sale2 when 3 then b.sale3 when 4 then b.sale4 when 5 then b.sale5 else b.retailsale end";
			qry += "\n      into v_price0";
			qry += "\n      from warecode b where accid=" + Accid + " and wareid=v_wareid;";
			qry += "\n   end if;";
			qry += "\n   v_price:=round(v_price0*v_discount," + priceprec + "); ";
			if (ntid == 2) {
				// 0=零售 1=定单 2=首单 3=补货 4=换货 5=代销
				// long saleid0 = saleid;
				// if (saleid0 == 3)
				// saleid0 = 2; // 如果是补货，则按首单取最近售价
				// qry += "\n end if;";
				qry += "\n   begin";
				qry += "\n     if v_saleid=3 or v_saleid=2 then  ";
				qry += "\n        select price into v_price1 from (";
				qry += "\n        select b.price from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
				qry += "\n        where a.accid=" + Accid + " and a.statetag=1 and a.ntid=1 and b.wareid=v_wareid";// +
																													// "
																													// and
																													// b.saleid="
																													// +
																													// saleid0;
				qry += "\n        and (b.saleid=2 or b.saleid=3)";
				qry += "\n        and a.custid=" + custid + " order by a.notedate desc,b.id desc";
				qry += "\n        ) where rownum=1;";
				qry += "\n    else ";
				qry += "\n       select price into v_price1 from (";
				qry += "\n       select b.price from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
				qry += "\n       where a.accid=" + Accid + " and a.statetag=1 and a.ntid=1 and b.wareid=v_wareid";// +
																													// "
																													// and
																													// b.saleid="
																													// +
																													// saleid0;
				qry += "\n       and b.saleid=v_saleid";
				qry += "\n       and a.custid=" + custid + " order by a.notedate desc,b.id desc";
				qry += "\n       ) where rownum=1;";
				qry += "\n    end if;";
				qry += "\n     v_price:=v_price1;";
				qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN  ";
				qry += "\n     v_price1:=v_price;"; // 没有调价记录，单价=原价*折扣
				qry += "\n   end;";

			} else {

				// 2.查找是否有调价记录（裴晓要求批发单也要取调价单记录）
				qry += "\n   begin";
				qry += "\n     select price into v_price1 from ( ";
				qry += "\n     select case v_pricetype when 0 then b.retailsale  when 1 then b.sale1 when 2 then b.sale2 when 3 then b.sale3 when 4 then b.sale4 when 5 then b.sale5 else b.retailsale end as price";
				qry += "\n     from wareadjusth a join wareadjustm b on a.accid=b.accid and a.noteno=b.noteno";
				qry += "\n     where a.statetag=2";
				qry += "\n     and a.begindate<to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')+1";
				qry += "\n     and a.enddate>=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') ";
				if (houseid > 0)
					qry += "\n   and exists (select 1 from wareadjustc c where a.accid=c.accid and a.noteno=c.noteno and c.houseid=" + houseid + ")";
				qry += "\n     and b.wareid=v_wareid and a.accid=" + Accid + " order by a.notedate desc ) where rownum=1;";

				qry += "\n     if v_price1>=v_price0 then "; // 如果调后价高于调前价
				qry += "\n        v_price0:=v_price1;v_discount:=1; ";
				qry += "\n        v_price:=v_price1; ";
				qry += "\n     else";
				qry += "\n        v_price:=v_price1; v_discount:=0; "; // 查到有调价记录
				qry += "\n        if v_price0>0 then";
				qry += "\n           v_discount:=round(v_price/v_price0,2); "; // 有调价记录,折扣=单价/原价
				qry += "\n        end if;";
				qry += "\n     end if ;";
				qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN  ";
				qry += "\n     v_price:=round(v_price0*v_discount," + priceprec + ");"; // 没有调价记录，单价=原价*折扣
				qry += "\n   end;";

				if (nearsaleok == 1) // 启用最近售价
				{
					qry += "\n   begin";
					qry += "\n     if v_saleid=3 or v_saleid=2 then  ";
					qry += "\n        select price0,discount,price into vt_price1,vt_discount,vt_price from (";
					qry += "\n        select b.price0,b.discount,b.price from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
					qry += "\n        where a.accid=" + Accid + " and a.statetag=1 and a.ntid=1 and a.cleartag=0 and b.wareid=v_wareid and a.custid=v_custid";
					//					qry += "\n        and (b.saleid=2 or b.saleid=3)";
					qry += "\n        order by a.notedate desc,b.id desc";
					qry += "\n        ) where rownum=1;";
					qry += "\n     else ";
					qry += "\n        select price0,discount,price into vt_price1,vt_discount,vt_price from (";
					qry += "\n        select b.price0,b.discount,b.price from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
					qry += "\n        where a.accid=" + Accid + " and a.statetag=1 and a.ntid=1 and a.cleartag=0 and b.wareid=v_wareid and a.custid=v_custid";
					//					qry += "\n        and b.saleid=v_saleid";
					qry += "\n        order by a.notedate desc,b.id desc";
					qry += "\n        ) where rownum=1;";
					qry += "\n     end if;";
					qry += "\n     v_price1:=vt_price1;v_discount:=vt_discount;v_price:=vt_price;";
					qry += "\n     if v_price0<>v_price1  then";
					qry += "\n        v_discount:=case v_price0 when 0 then 1 else round(v_price/v_price0,2) end;";
					qry += "\n     end if;";
					// qry += "\n end if;";
					qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN  ";
					qry += "\n     vt_price1:=0;"; // 没有调价记录，单价=原价*折扣
					qry += "\n   end;";

					// qry += "\n if v_saleid=3 then ";
					// qry += "\n v_saleid:=2;";
					// qry += "\n end if;";
					// qry += "\n select count(*) into v_count from nearsale
					// where wareid=v_wareid and custid=v_custid and
					// saleid=v_saleid ;";
					// qry += "\n if v_count>0 then ";
					// qry += "\n select price0,discount,price into
					// v_price1,v_discount,v_price from nearsale where
					// wareid=v_wareid and custid=v_custid and
					// saleid=v_saleid;";
					// qry += "\n if v_price0<>v_price1 then";
					// qry += "\n v_discount:=case v_price0 when 0 then 1 else
					// round(v_price/v_price0,2) end;";
					// qry += "\n end if;";
					// qry += "\n end if;";
				}
			}
		} else {// 零售
			if (houseid > 0) {
				qry += "\n  select pricetype into v_pricetype from warehouse where houseid=" + houseid + ";";
				qry += "\n  if v_pricetype>0 then"; // 按店铺价格取售价
				qry += "\n     select case v_pricetype when 1 then b.sale1 when 2 then b.sale2 when 3 then b.sale3 when 4 then b.sale4 when 5 then b.sale5 else b.retailsale end into v_price0";
				qry += "\n     from warecode b where accid=" + Accid + " and wareid=v_wareid;";
				qry += "\n  end if;";
			}
			qry += "\n   v_saleid:=0;";
			qry += "\n   v_price:=round(v_price0*v_discount," + priceprec + "); ";

			// 2.查找是否有调价记录
			qry += "\n   begin";

			qry += "\n     select nvl(price,v_price0) into v_price1 from ( select ";
			// qry += "\n b.retailsale as price";
			qry += "\n     case v_pricetype when 1 then b.sale1 when 2 then b.sale2 when 3 then b.sale3 when 4 then b.sale4 when 5 then b.sale5 else b.retailsale end as price";

			qry += "\n     from wareadjusth a join wareadjustm b on a.accid=b.accid and a.noteno=b.noteno";
			qry += "\n     where a.statetag=2";
			qry += "\n     and a.begindate<to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')+1";
			qry += "\n     and a.enddate>=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') ";
			if (houseid > 0)
				qry += "\n   and exists (select 1 from wareadjustc c where a.accid=c.accid and a.noteno=c.noteno and c.houseid=" + houseid + ")";
			qry += "\n     and b.wareid=v_wareid and a.accid=" + Accid + " order by a.notedate desc ) where rownum=1;";

			qry += "\n     if v_price1>=v_price0 then "; // 如果调后价高于调前价
			qry += "\n        v_price0:=v_price1;v_discount:=1; ";
			qry += "\n        v_price:=v_price1; ";
			qry += "\n     else";
			qry += "\n        v_price:=v_price1; v_discount:=0; "; // 查到有调价记录
			qry += "\n        if v_price0>0 then";
			qry += "\n           v_discount:=round(v_price/v_price0,2); "; // 有调价记录,折扣=单价/原价
			qry += "\n        end if;";
			qry += "\n     end if ;";
			qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN  ";
			qry += "\n     v_price:=round(v_price0*v_discount," + priceprec + ");"; // 没有调价记录，单价=原价*折扣
			qry += "\n   end;";

		}
		qry += "\n     <<exit1>>";
		qry += "\n     select count(*) into v_count from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "' ";//
		qry += "\n        and wareid=v_wareid and colorid=v_colorid and sizeid=v_sizeid and saleid=v_saleid ; ";//
		qry += "\n     if v_count=0 then ";//
		// qry += "\n v_price:=round(v_price0*v_discount," + priceprec + "); ";
		qry += "\n        v_curr:=round(v_price*v_amount," + currprec + "); ";//

		qry += "\n        insert into wareoutm (id,accid,noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,curr,remark0,tjtag) ";//
		qry += "\n        values (wareoutm_id.nextval," + Accid + ",'" + Noteno + "',v_wareid,v_colorid,v_sizeid,v_saleid,v_amount,v_price0,v_discount,v_price,v_curr,'',v_tjtag); ";//
		qry += "\n     else  ";//
		qry += "\n        select id into v_id from wareoutm where accid=" + Accid + " and noteno='" + Noteno//
				+ "' and wareid=v_wareid and colorid=v_colorid and sizeid=v_sizeid and saleid=v_saleid and rownum=1 order by id ; ";//
		qry += "\n        update wareoutm set amount=amount+v_amount,curr=round((amount+v_amount)*price,2) where id=v_id and noteno='" + Noteno + "' and accid=" + Accid + "; ";
		qry += "\n     end if; ";

		qry += "\n     select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "';  ";
		qry += "\n     update wareouth set totalcurr=v_totalcurr,totalamt=v_totalamt  where accid=" + Accid + " and noteno='" + Noteno + "';  ";

		qry += "\n     v_retcs:= '1 '||v_warename||':'||v_wareno||','||v_colorname||','||v_sizename||',条码:'||v_barcode0||',单价:'||v_price; ";
		// 填 入条码跟踪表
		qry += "\n     if '" + barcode + "'>v_barcode0  then";
		// noteid:0采购入库1采购退库2期初入库3调拨入库4零售出库5批发出库6经销退库7调拨出库8盘点9临时盘点10采购订单11客户订单12调拨订单
		qry += "\n        insert into warebarrecord (id,accid,barcode,noteid,noteno,amount,lastdate)";
		qry += "\n        values (warebarrecord_id.nextval," + Accid + ",'" + barcode + "',5,'" + Noteno + "',v_amount,sysdate); ";
		qry += "\n     end if;";
		qry += "\n     commit; ";
		qry += "\n     <<exit>> ";
		qry += "\n     select v_retcs into :retcs from dual; ";
		qry += "\n end; ";
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
		// qry += "\n v_totalcheckcurr number; ";
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
				qry += "\n     select id into v_id from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid;
				qry += "\n     and colorid=" + Colorid + " and sizeid=" + Sizeid + " and saleid=" + Saleid + " and rownum=1; ";
				qry += "\n     update wareoutm set amount=amount+" + Amount + ",curr=(amount+" + Amount + ")*price where id=v_id;";
				qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN"; // 未找到，插入新记录
				qry += "\n     insert into wareoutm (id,accid,noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,curr,remark0)";
				qry += "\n     values (wareoutm_id.nextval," + Accid + ",'" + Noteno + "'," + Wareid + "," + Colorid + "," + Sizeid + "," + Saleid + "," + Amount + "," + Price0 + "," + Discount + "," + Price + "," + Curr
						+ ",'');";
				qry += "\n  end; ";
			}
		}
		qry += "\n    select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n    update wareouth set totalcurr=v_totalcurr,totalamt=v_totalamt";
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

	public int Load(long custid, long houseid, long guestid, int ntid, int priceprec, int nearsaleok, String wareno) {
		if (Noteno.equals("")) {
			errmess = "noteno不是一个有效的值！";
			return 0;
		}
		Table tb = new Table();
		String qry = "";
		// int pricetype = 0;
		// if (ntid == 0 && houseid > 0) {// v如果是零售，要取店铺里的零售价格方式
		// qry = "select pricetype from warehouse where houseid =" + houseid;
		// pricetype = Integer.parseInt(DbHelperSQL.RunSql(qry).toString());
		// }

		if (Wareid != null && Wareid > 0) {
			qry = "select wareid,wareno,warename,entersale,sale1,sale2,sale3,sale4,sale5,retailsale,units,imagename0 ";
			// qry += ",case " + pricetype + " when 1 then sale1 when 2 then
			// sale2 when 3 then sale3 when 4 then sale4 when 5 then sale5 else
			// retailsale end as retailsale";
			qry += " from warecode where accid=" + Accid + " and statetag=1 and wareid=" + Wareid;
		} else if (wareno.length() > 0) {
			qry = "select wareid,wareno,warename,entersale,sale1,sale2,sale3,sale4,sale5,retailsale,units,imagename0 ";
			// qry += ",case " + pricetype + " when 1 then sale1 when 2 then
			// sale2 when 3 then sale3 when 4 then sale4 when 5 then sale5 else
			// retailsale end as retailsale";
			qry += " from warecode where accid=" + Accid + " and wareno='" + wareno + "' and statetag=1 and rownum=1";
		} else {
			errmess = "商品id参数无效！";
			return 0;
		}
		// string qry = "select
		// wareid,wareno,warename,entersale,retailsale,units from warecode where
		// wareid=" + wareid;
		tb = DbHelperSQL.Query(qry).getTable(1);
		int rs = tb.getRowCount();
		if (rs == 0) {
			errmess = "未找到商品！";
			return 0;
		}
		Wareid = Long.parseLong(tb.getRow(0).get("WAREID").toString());

		Price0 = Float.parseFloat(tb.getRow(0).get("RETAILSALE").toString());
		Discount = (float) 1;
		String retcs = "\"msg\":\"操作成功\"";

		String fh = "";
		for (int j = 0; j < tb.getColumnCount(); j++) {
			String strKey = tb.getRow(0).getColumn(j + 1).getName().toUpperCase();
			String strValue = tb.getRow(0).get(j).toString();
			retcs += ",\"" + strKey + "\":\"" + strValue + "\"";
		}

		qry = "select saleid,salename from salecode where saleid=" + Saleid;
		tb = DbHelperSQL.Query(qry).getTable(1);
		retcs += ",\"SALEID\":\"" + tb.getRow(0).get("SALEID").toString() + "\"" //
				+ ",\"SALENAME\":\"" + tb.getRow(0).get("SALENAME").toString() + "\"";

		String jstr = "";
		if (ntid == 2)// 批发退货
			jstr = Funcpackge.myWareoutprice(Accid, Wareid, custid, guestid, houseid, Saleid, priceprec, nearsaleok, 1); // 取客户售价
		else if (ntid == 1)// 批发
			jstr = Funcpackge.myWareoutprice(Accid, Wareid, custid, 0, houseid, Saleid, priceprec, nearsaleok, 0); // 取客户售价
		else // 零售
			jstr = Funcpackge.myWareoutprice(Accid, Wareid, 0, guestid, houseid, 0, priceprec, nearsaleok, 0); // 取客户售价
		JSONObject jsonObject = JSONObject.fromObject("{" + jstr + "}");
		Price0 = Float.parseFloat(jsonObject.getString("PRICE0"));
		Discount = Float.parseFloat(jsonObject.getString("DISCOUNT"));
		Price = Float.parseFloat(jsonObject.getString("PRICE"));

		retcs += "," + jstr;
		//		 System.out.println("jstr=" + jstr);

		// 取颜色
		qry = "select a.colorid,b.colorname";
		qry += "\n  FROM warecolor a";
		qry += "\n  left outer join colorcode b on a.colorid=b.colorid";
		qry += "\n  where a.wareid=" + Wareid + " and b.statetag=1 and b.noused=0 ";
		qry += "\n  and (a.noused=0 or a.noused=1 and exists (select 1 from wareoutm c ";
		qry += "\n  where c.accid=" + Accid + " and c.noteno='" + Noteno + "' and a.wareid=c.wareid and a.colorid=c.colorid) )";

		qry += "\n  order by b.colorno";

		tb = DbHelperSQL.Query(qry).getTable(1);
		fh = "";
		rs = tb.getRowCount();

		retcs += ",\"colorlist\":[";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\""//
					+ ",\"COLORNAME\":\"" + tb.getRow(i).get("COLORNAME").toString() + "\"}";
			fh = ",";
		}
		retcs += "]";

		// 取尺码
		qry = "select a.sizeid,a.sizename,a.sizeno  FROM sizecode a";
		qry += "\n  left outer join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno";
		qry += "\n  where a.statetag=1 and a.noused=0 and A.ACCID=" + Accid + " and b.wareid=" + Wareid;
		qry += "\n  and not exists (select 1 from warenosize a1 where b.wareid=a1.wareid and a.sizeid=a1.sizeid)";
		qry += "\n  order by a.sizeno,a.sizename";
		// qry += "\n where a.statetag=1 and a.noused=0 and b.wareid=" + wareid
		// + " order by a.sizeno,a.sizename";
		tb = DbHelperSQL.Query(qry).getTable(1);
		fh = "";
		rs = tb.getRowCount();
		// {"colorlist":[{"COLORID":"1","COLORNAME":"红色"},{"COLORID":"2","COLORNAME":"黄色"}]
		// ,"sizelist":[{"SIZEID":"1","SIZENAME":"XS","SIZENO":"01"},{"SIZEID":"2","SIZENAME":"S","SIZENO":"02"},{"SIZEID":"3","SIZENAME":"M","SIZENO":"03"},{"SIZEID":"4","SIZENAME":"L","SIZENO":"04"},{"SIZEID":"5","SIZENAME":"XL","SIZENO":"05"},{"SIZEID":"6","SIZENAME":"XXL","SIZENO":"06"},{"SIZEID":"7","SIZENAME":"3XL","SIZENO":"07"}]}

		retcs += ",\"sizelist\":[";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\""//
					+ ",\"SIZENAME\":\"" + tb.getRow(i).get("SIZENAME").toString() + "\"}";
			fh = ",";

		}
		retcs += "]";
		// 取出库数

		qry = "  select colorid,sizeid,amount,price,discount,price0,curr,remark0";
		qry += "\n  from wareoutm a ";
		qry += "\n  where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + " and saleid=" + Saleid;
		if (!Func.isNull(Boxno))
			qry += "\n and boxno='" + Boxno + "'";
		qry += "\n  order by colorid";
		tb = DbHelperSQL.Query(qry).getTable(1);
		fh = "";
		rs = tb.getRowCount();
		retcs += ",\"amountlist\":[";
		Float totalcurr = (float) 0;
		Float totalamt = (float) 0;
		// Amount = 0;
		// curr = 0;
		// price = 0;

		for (int i = 0; i < rs; i++) {
			Amount = Float.parseFloat(tb.getRow(i).get("AMOUNT").toString());
			Curr = Float.parseFloat(tb.getRow(i).get("CURR").toString());
			Price0 = Float.parseFloat(tb.getRow(i).get("PRICE0").toString());
			retcs += fh + "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\""//
					+ ",\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"" //
					+ ",\"AMOUNT\":\"" + Amount + "\"" //
					+ ",\"PRICE0\":\"" + Price0 + "\""//
					+ ",\"DISCOUNT\":\"" + tb.getRow(i).get("DISCOUNT").toString() + "\"" //
					+ ",\"PRICE\":\"" + tb.getRow(i).get("PRICE").toString() + "\""//
					+ ",\"REMARK0\":\"" + tb.getRow(i).get("REMARK0").toString() + "\""//
					+ ",\"CURR\":\"" + Curr + "\"}";
			fh = ",";
			totalcurr += Curr;
			totalamt += Amount;
		}

		retcs += "]";
		// 取库存
		// if (houseid > 0 && (ntid == 0 || ntid == 1)) // 如果传入houseid,表示要返回库存
		if (houseid > 0) // 如果传入houseid,表示要返回库存
		{
			vTotalCxkczy dal = new vTotalCxkczy();
			dal.setAccid(Accid);
			dal.setUserid(Userid);
			dal.setWareid(Wareid);
			dal.setHouseid(houseid);
			dal.setCalcdate(Calcdate);
			dal.doCxkczy();
			// private int myTotalCxkczy(string nowdate, long epid, long accid,
			// long houseid, long wareid, long colorid, long sizeid, long
			// typeid, long brandid, string wareno, string warename,string
			// seasonname, string prodyear, int qxbj,int bj=0)
			// myTotalCxkczy("", userid, accid, houseid, wareid, 0, 0, -1, -1,
			// -1, -1, "", "", "", "", "", "", 0, 0);

			qry = "   select colorid,sizeid,amount";
			qry += "\n    from v_cxkczy_data a where epid=" + Userid + " and wareid=" + Wareid + " and houseid=" + houseid;
			qry += "\n    and amount<>0 ";
			tb = DbHelperSQL.Query(qry).getTable(1);
			fh = "";
			rs = tb.getRowCount();
			// {"colorlist":[{"COLORID":"1","COLORNAME":"红色"},{"COLORID":"2","COLORNAME":"黄色"}]
			// ,"sizelist":[{"SIZEID":"1","SIZENAME":"XS","SIZENO":"01"},{"SIZEID":"2","SIZENAME":"S","SIZENO":"02"},{"SIZEID":"3","SIZENAME":"M","SIZENO":"03"},{"SIZEID":"4","SIZENAME":"L","SIZENO":"04"},{"SIZEID":"5","SIZENAME":"XL","SIZENO":"05"},{"SIZEID":"6","SIZENAME":"XXL","SIZENO":"06"},{"SIZEID":"7","SIZENAME":"3XL","SIZENO":"07"}]}

			retcs += ",\"kclist\":[";

			for (int i = 0; i < rs; i++) {
				retcs += fh + "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\","//
						+ "\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"," //
						+ "\"AMOUNT\":\"" + tb.getRow(i).get("AMOUNT").toString() + "\"}";
				fh = ",";

			}
			retcs += "]";
		}
		if (totalamt != 0)
			Price = Func.getRound(totalcurr / totalamt, priceprec);// Math.Round(totalcurr
																// /
																	// totalamt,
																	// priceprec,
																	// MidpointRounding.AwayFromZero);

		if (Price0 != 0) {
			if (Price == null)
				Price = (float) 0;
			Discount = Func.getRound(Price / Price0, 2);// Math.Round(price /
														// price0, 2,
														// MidpointRounding.AwayFromZero);
		}
		if (Price == null)
			Price = (float) 0;
		retcs += ",\"ZAMOUNT\":\"" + totalamt + "\"" //
				+ ",\"ZPRICE0\":\"" + Price0 + "\"" //
				+ ",\"ZDISCOUNT\":\"" + Discount + "\""//
				+ ",\"ZPRICE\":\"" + Price + "\"" //
				+ ",\"ZCURR\":\"" + totalcurr + "\"";
		errmess = retcs;
		return 1;

	}

	public int Write(JSONObject jsonObject, long saleid0, int currprec) {
		if (Wareid == 0 || Noteno.equals("")) {
			errmess = "wareid或noteno不是一个有效的值！";
			return 0;
		}
		if (!pFunc.isWarecodeOk(Accid, Wareid)) {
			errmess = "wareid不是一个有效的值！";
			return 0;
		}
		if (!jsonObject.has("rows")) {
			errmess = "rows不是一个有效的值！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("rows");
		String qry = "declare ";
		qry += "\n    v_totalcurr number; ";
		qry += "\n    v_totalcheckcurr number; ";
		qry += "\n    v_totalamt number; ";
		qry += "\n    v_id  number;";
		qry += "\n    v_saleid  number(10);";
		qry += "\n begin ";
		qry += "\n     v_saleid:=" + Saleid + ";";
		if (saleid0 == Saleid)
			qry += "\n      update wareoutm set amount=0 where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + " and saleid=v_saleid ; ";
		else
			qry += "\n      delete from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + " and saleid=" + saleid0 + " ; ";
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
			Curr = Func.getRound(Float.parseFloat(jsonObject2.getString("curr")), currprec);
			Remark0 = jsonObject2.has("remark0") ? jsonObject2.getString("remark0") : "";
			qry += "\n   begin ";
			qry += "\n     select id into v_id from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid //
					+ " and colorid=" + Colorid + " and sizeid=" + Sizeid + " and saleid=v_saleid and rownum=1; ";
			qry += "\n     update wareoutm set amount=" + Amount + ",discount=" + Discount + ",price=" + Price + ",price0=" + Price0 + ",curr=" + Curr + ",remark0='" + Remark0 //
					+ "' where id=v_id and  accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN"; // 未找到，插入新记录
			qry += "\n     insert into wareoutm (id,accid,noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,curr,remark0,lastdate)";
			qry += "\n     values (wareoutm_id.nextval," + Accid + ",'" + Noteno + "'," + Wareid + " ," + Colorid + "," + Sizeid + ",v_saleid,"//
					+ Amount + "," + Price0 + "," + Discount + "," + Price + "," + Curr + ",'" + Remark0 + "',sysdate);";
			qry += "\n  end; ";

		}
		qry += "\n   delete from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + " and saleid=v_saleid and amount=0; ";

		qry += "\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "';  ";
		qry += "\n   update wareouth set totalcurr=v_totalcurr+decode(ntid,2,-totalcost,totalcost),totalamt=v_totalamt  ";
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

	// 获取采购入库\退货商品+颜色汇总列表
	public int doGetColorsumList(QueryParam qp, int sizenum, int priceprec) {
		if (Noteno.equals("")) {
			errmess = "noteno不是一个有效的值！";
			return 0;
		}

		String qry = "select a.wareid,b.wareno,b.warename,b.units,b.sizegroupno,b.imagename0,b.gbbar,e.brandname,a.colorid,c.colorname,a.saleid,d.salename";
		qry += "\n,b.retailsale,b.entersale ,min(a.id) as id,sum(a.amount) as amount";
		qry += "\n ,case sum(a.amount) when 0 then 0 else round(sum(a.amount*a.price0)/sum(a.amount)," + priceprec + ") end as price0";
		qry += "\n ,case sum(a.amount*a.price0) when 0 then 1 else round(sum(a.curr)/sum(a.amount*a.price0),2) end as discount";
		qry += "\n ,case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount)," + priceprec + ") end as price";
		qry += "\n ,sum(a.curr) as curr,min(remark0) as remark0,max(a.tjtag) as tjtag";
		qry += "\n from wareoutm a ";
		qry += "\n left outer join warecode b on a.wareid=b.wareid";
		qry += "\n left outer join colorcode c on a.colorid=c.colorid";
		qry += "\n left outer join salecode d on a.saleid=d.saleid";
		qry += "\n left outer join brand e on b.brandid=e.brandid";
		qry += "\n where a.ACCID=" + Accid + " and a.NOTENO='" + Noteno + "'";
		qry += "\n group by a.wareid,b.wareno,b.warename,b.units,b.sizegroupno,b.imagename0,b.gbbar,e.brandname,a.colorid,c.colorname,a.saleid,d.salename,b.retailsale,b.entersale";
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
			//			retcs = "{\"result\":1," + qp.getTotalString() + ",\"rows\":[";
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
		retcs = "{\"result\":1,\"msg\":\"操作成功\"," + qp.getTotalString() + ",\"rows\":[";
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
			qry1 += " from wareoutm a where accid=" + Accid + " and noteno='" + Noteno + "'";
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

	// 成批更改销售出库折扣
	public int ChangeDisc(long custid, int priceprec) {
		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n    v_price0 number;";
		qry += "\n    v_discount number;";
		qry += "\n    v_pricetype number(1);";
		qry += "\n    v_accid number;";
		qry += "\n    v_noteno varchar2(20);";
		qry += "\n    v_count number(10); ";
		qry += "\n    v_totalcurr number(16,2); ";
		qry += "\n    v_saleid number;";
		qry += "\n begin ";
		// aaaa
		if (custid > 0) {// 根据custid查询价格方式及折扣
			qry += "\n   begin";
			qry += "\n     select discount,pricetype into v_discount,v_pricetype from customer where accid=" + Accid + " and custid=" + custid + ";";
			qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
			qry += "\n     v_discount:=" + Discount + ";";
			qry += "\n     v_pricetype:=0;";
			qry += "\n   end;";
			qry += "\n   declare cursor cur_data is ";
			qry += "\n     select a.id,a.wareid,a.amount";
			qry += "\n     ,case v_pricetype when 0 then b.retailsale  when 1 then b.sale1 when 2 then b.sale2 when 3 then b.sale3 when 4 then b.sale4 when 5 then b.sale5 else b.retailsale end as price0";
			qry += "\n     from wareoutm a join warecode b on a.wareid=b.wareid";
			qry += "\n     where a.accid=" + Accid + " and a.noteno='" + Noteno + "' and b.tjtag=0; ";
			qry += "\n     v_row cur_data%rowtype;";
			qry += "\n   begin";
			qry += "\n     for v_row in cur_data loop";
			qry += "\n         update wareoutm set price0=v_row.price0,discount=v_discount,price=round(v_row.price0*v_discount," + priceprec + ")";
			qry += "\n         ,curr=round(amount*round(v_row.price0*v_discount," + priceprec + "),2)";
			qry += "\n         where id=v_row.id;";
			qry += "\n     end loop;";
			qry += "\n   end;";
		} else {

			qry += "\n   v_discount:=" + Discount + ";";
			qry += "\n   update wareoutm a set discount=v_discount,price=round(price0*v_discount," + priceprec + "),curr=round(amount*round(price0*v_discount," + priceprec + "),2)";
			qry += "\n   where accid=" + Accid + " and noteno='" + Noteno + "' ";
			qry += "\n   and exists (select 1 from warecode b where a.wareid=b.wareid and b.tjtag=0);";
		}
		// qry += " update wareoutm set
		// discount=v_discount,price=trunc(price0*v_discount+0.5),curr=amount*trunc(price0*v_discount+0.5)";
		qry += "\n    commit;";
		// qry += " update wareouth set totalcurr=(select nvl(sum(curr),0) from
		// wareoutm where wareouth.accid=wareoutm.accid and
		// wareouth.noteno=wareoutm.noteno) ";
		// qry += " where accid="+accid+" and noteno='"+noteno+"';";
		qry += "\n   select nvl(sum(curr),0) into v_totalcurr from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   update wareouth set totalcurr=v_totalcurr where accid=" + Accid + " and noteno='" + Noteno + "';";

		// 更新最近售价
		// qry += " declare cursor cur_wareout is ";
		// qry += " select
		// a.custid,b.wareid,b.saleid,b.price0,b.discount,b.price from wareouth
		// a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
		// qry += " where a.accid=" + Accid + " and a.noteno='" + Noteno + "'
		// and a.ntid=1 order by b.price desc; ";// for update nowait;";
		// qry += " v_row cur_wareout%rowtype;";
		// qry += " begin";
		// qry += " for v_row in cur_wareout loop";
		// qry += " v_saleid:=v_row.saleid;";
		// qry += " if v_saleid=3 then v_saleid:=2; end if; ";
		//
		// qry += " select count(*) into v_count from nearsale where
		// custid=v_row.custid and wareid=v_row.wareid and saleid=v_saleid;";
		// qry += " if v_count=0 then";
		// qry += " insert into nearsale
		// (custid,wareid,saleid,price0,discount,price,lastdate)";
		// qry += " values
		// (v_row.custid,v_row.wareid,v_saleid,v_row.price0,v_row.discount,v_row.price,sysdate);";
		// qry += " else";
		// qry += " update nearsale set
		// price=v_row.price,price0=v_row.price0,discount=v_row.discount,lastdate=sysdate
		// where custid=v_row.custid and wareid=v_row.wareid and
		// saleid=v_saleid;";
		// qry += " end if; ";
		// qry += " end loop;";
		// qry += " end;";

		qry += "   commit;";
		qry += "end; ";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}

	// 复制单据
	public int doCopy(String fromnoteno, int fromnoteid, String tonoteno, int tonoteid, int priceprec) {
		// fromnoteid:0=采购，1=采购退货，2=店铺零售，3=批发，4=批发退货，5=商场零售，6=调出，7=调入
		if (fromnoteno.length() <= 0 || tonoteno.length() <= 0) {
			errmess = "单据号参数无效！";
			return 0;
		}
		if (fromnoteno.equals(tonoteno) && fromnoteid == tonoteid) {
			errmess = "源单据与目标单据不允许一样！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n    v_custid number;";
		qry += "\n    v_count number;";
		qry += "\n    v_totalcurr number;";
		qry += "\n    v_totalamt number;";
		qry += "\n    v_id number;";
		qry += "\n    v_discount number(10,4);";
		qry += "\n    v_pricetype number(1);";

		qry += "\n begin";
		qry += "\n    select b.discount,b.pricetype into v_discount,v_pricetype from wareouth a join customer b on a.custid=b.custid  where a.accid=" + Accid + " and noteno='" + tonoteno + "';";

		qry += "\n    v_count:=1;";
		qry += "\n    declare cursor cur_data is ";
		qry += "\n      select a.wareid,a.colorid,a.sizeid,a.amount,a.remark0";
		qry += "\n      ,case v_pricetype when 1 then b.sale1 when 2 then b.sale2 when 3 then b.sale3 when 4 then b.sale4 when 5 then b.sale5 else b.retailsale end as price0";
		if (tonoteid == 3 || tonoteid == 4) {
			qry += "\n  ,2 as saleid";

		} else {
			qry += "\n  ,0 as saleid";
		}
		if (fromnoteid == 0 || fromnoteid == 1)
			qry += "\n      from wareinm a ";
		else if (fromnoteid == 2 || fromnoteid == 3 || fromnoteid == 4)// 2=店铺零售，3=批发，4=批发退货
			qry += "\n      from wareoutm a ";
		else if (fromnoteid == 5)// 5=商场零售
			qry += "\n      from shopsalem a ";
		else if (fromnoteid == 6) // 6=调出
			qry += "\n      from allotoutm a ";
		else if (fromnoteid == 7)// 7=调入
			qry += "\n      from allotinm a ";
		qry += "\n      join warecode b on a.wareid=b.wareid";
		qry += "\n      where a.accid=" + Accid + " and a.noteno='" + fromnoteno + "';";
		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n    begin";
		qry += "\n      for v_row in cur_data loop";
		qry += "\n         begin ";
		qry += "\n           select id into v_id from wareoutm where accid=" + Accid + " and noteno='" + tonoteno
				+ "' and wareid=v_row.wareid and colorid=v_row.colorid and sizeid=v_row.sizeid and saleid=v_row.saleid and rownum=1; ";
		qry += "\n           update wareoutm set amount=amount+v_row.amount,curr=(amount+v_row.amount)*price where id=v_id and accid=" + Accid + " and noteno='" + tonoteno + "';";
		qry += "\n         EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n           insert into wareoutm (id,accid,noteno,wareid,colorid,sizeid,saleid,amount,price0,discount,price,curr,remark0)";
		qry += "\n           values (wareoutm_id.nextval," + Accid + ",'" + tonoteno + "',v_row.wareid,v_row.colorid,v_row.sizeid,v_row.saleid,v_row.amount,v_row.price0,v_discount,round(v_row.price0*v_discount,"//
				+ priceprec + "),round(round(v_row.price0*v_discount,2)*v_row.amount,2),v_row.remark0);";
		qry += "\n         end; ";
		qry += "\n         v_count:=v_count+1;";
		qry += "\n         if v_count>500 then ";
		qry += "\n            v_count:=1;";
		qry += "\n            commit;";
		qry += "\n         end if;";
		qry += "\n      end loop;";
		qry += "\n    end;";
		qry += "\n    select  nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from wareoutm where accid=" + Accid + " and noteno='" + tonoteno + "';";
		qry += "\n    update wareouth set totalcurr=v_totalcurr,totalamt=v_totalamt  where accid=" + Accid + " and noteno='" + tonoteno + "';";
		qry += "\n    commit;";
		qry += "\n end;";

		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 从验货单里更新商品记录
	public int Updatefromcheck() {

		String qry = "declare ";
		qry += "\n   v_tablename varchar2(20);";
		qry += "\n   v_balamt number;";
		qry += "\n   v_id number;";
		qry += "\n   v_count number;";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n begin";
		qry += "\n    v_tablename:='WAREOUTM';";

		qry += "\n   select count(*) into v_count from (";
		qry += "\n     select wareid,colorid,sizeid from notecheck a where accid=" + Accid + " and tablename=v_tablename and noteno='" + Noteno + "' and rownum=1 ";
		qry += "\n     and not exists (select 1 from wareoutm b where b.accid=" + Accid + " and b.noteno='" + Noteno + "'";
		qry += "\n     and a.wareid=b.wareid and a.colorid=b.colorid and a.sizeid=c.sizeid));";
		qry += "\n   if v_count>0 then";
		qry += "\n      v_retcs:='0捡货单上有不存在的商品，请检查！'";
		qry += "\n      goto exit;";
		qry += "\n   end if;";

		qry += "\n    update notecheck set amount0=0 where accid=" + Accid + " and tablename=v_tablename and noteno='" + Noteno + "';";
		qry += "\n    commit;";
		// 根据出库单原数量更新捡货数量
		qry += "\n    declare cursor cur_data is";
		qry += "\n      select wareid,colorid,sizeid,id,amount from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n    begin";
		qry += "\n      v_count:=0;";
		qry += "\n      for v_row in cur_data loop";
		qry += "\n        begin";
		qry += "\n          select amount-amount0,id into v_balamt,v_id from notecheck where accid=" + Accid + " and tablename=v_tablename and noteno='" + Noteno + "'";
		qry += "\n          and wareid=v_row.wareid and colorid=v_row.colorid and sizeid=v_row.sizeid;";
		qry += "\n          if v_balamt<=v_row.amount then";
		qry += "\n             update wareoutm set amount=v_balamt,curr=round(v_balamt*price,2) where id=v_row.id;";
		qry += "\n             update notecheck set amount0=v_balamt where id=v_id;";
		qry += "\n          else ";
		qry += "\n             update notecheck set amount0=v_row.amount where id=v_id;";
		qry += "\n          end if;";
		qry += "\n        EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n          update wareoutm set amount=0 where id=v_row.id;";
		qry += "\n        end;";
		qry += "\n        v_count:=v_count+1;";
		qry += "\n        if v_count>1000 then ";
		qry += "\n           commit;";
		qry += "\n           v_count:=0;";
		qry += "\n        end if;";
		qry += "\n      end loop;";
		qry += "\n    end;";
		qry += "\n    commit;";
		// 如果有多余的捡货数量处理，正常情况捡货数是小于等于单据上的数量的
		qry += "\n    declare cursor cur_data is";
		qry += "\n      select wareid,colorid,sizeid,id,amount-amount0 as amount from notecheck where accid=" + Accid + " and tablename=v_tablename and noteno='" + Noteno + "' and amount-amount0>0;";
		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n    begin";
		qry += "\n      v_count:=0;";
		qry += "\n      for v_row in cur_data loop";
		qry += "\n        select id,amount into v_id,v_balamt from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "'";
		qry += "\n        and wareid=v_row.wareid and colorid=v_row.colorid and sizeid=v_row.sizeid;";
		qry += "\n        v_balamt:=v_balamt+v_row.amount;";
		qry += "\n        update wareoutm set amount=v_balamt,curr=round(v_balamt*price,2) where id=id;";
		qry += "\n        update notecheck set amount0=amount where id=_row.v_id;";

		qry += "\n        v_count:=v_count+1;";
		qry += "\n        if v_count>1000 then ";
		qry += "\n           commit;";
		qry += "\n           v_count:=0;";
		qry += "\n        end if;";
		qry += "\n      end loop;";
		qry += "\n    end;";
		qry += "\n    commit;";
		qry += "\n    v_retcs:='1操作成功！';";
		qry += "\n    <<exit>>";
		qry += "\n    select v_retcs into :retcs from dual;";
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

	// 取零售商品售价
	public int doGetSaleoutprice(JSONObject jsonObject, int priceprec, int vippricetype) {
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		long guestid = jsonObject.has("guestid") ? Long.parseLong(jsonObject.getString("guestid")) : 0;
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		long salemanid = jsonObject.has("salemanid") ? Long.parseLong(jsonObject.getString("salemanid")) : 0;
		String salemanno = jsonObject.has("salemanno") ? jsonObject.getString("salemanno") : "";

		String qry = "";
		if (houseid == 0)// 如果没有传入店铺id，则找salemanid对应的店铺id
		{
			if (salemanid > 0)
				qry = " select  f_gethouseidbysalemanid(" + Accid + "," + salemanid + ") as houseid from dual";
			else
				qry = " select  f_gethouseidbysalemanno(" + Accid + ",'" + salemanno.toUpperCase() + "') as houseid from dual";
			houseid = Long.parseLong(DbHelperSQL.RunSql(qry).toString());
			if (houseid == 0) {
				errmess = "销售人未对应店铺，请检查！";
				return 0;
			}
		}

		qry = "declare";
		// qry += "\n v_houseid number(10); ";
		qry += "\n   v_pricetype number(1); ";
		qry += "\n   v_brandid number(10); ";
		qry += "\n   v_price number(14,2); ";
		qry += "\n   v_price00 number(14,2); ";
		qry += "\n   v_price0 number(14,2); ";
		qry += "\n   v_price1 number(14,2); ";
		qry += "\n   v_price2 number(14,2); ";
		qry += "\n   v_discount number(14,2); ";
		qry += "\n   v_discount1 number(14,2); ";
		qry += "\n   v_count number(10); ";
		qry += "\n   v_isvip number(1); ";
		qry += "\n   v_tjtag number(1); ";
		qry += "\n begin ";

		// 1.取店铺价格，价格方式 pricetype: 0=零价折扣,1=售价1,2=售价2,3=售价3,4=批发价,5=打包价,6=品牌价格
		qry += "\n   v_discount:=1;  ";
		qry += "\n   v_pricetype:=0;";
		if (houseid > 0) {
			qry += "\n  select pricetype into v_pricetype from warehouse where houseid=" + houseid + ";";
		}
		qry += "\n   select nvl(case v_pricetype when 1 then sale1 when 2 then sale2 when 3 then sale3 when 4 then sale4 when 5 then sale5 else retailsale end,0),tjtag into v_price0,v_tjtag";
		qry += "\n   from warecode b where accid=" + Accid + " and wareid=" + wareid + ";";

		// qry += "\n select nvl(retailsale,0),tjtag into v_price0,v_tjtag from
		// warecode where accid=" + Accid + " and wareid=" + wareid + "; ";

		qry += "\n   if v_tjtag=1 then"; // 特价商品不允许打折
		qry += "\n      v_discount:=1; v_price:=v_price0; ";
		qry += "\n      goto exit;";
		qry += "\n   end if;";

		// 2.取商品商场价格
		qry += "\n   begin";
		qry += "\n     select nvl(retailsale,v_price0) into v_price2 from housesaleprice where wareid=" + wareid + " and houseid=" + houseid + ";";
		qry += "\n     v_price0:=v_price2;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_price2:=0;";
		qry += "\n   end;";
		qry += "\n   v_isvip:=0;";

		qry += "\n   v_price00:=v_price0;";

		// 取会员折扣
		// qry += "\n v_discount:=1;";
		if (guestid > 0) {
			qry += "\n   begin";
			qry += "\n     select nvl(b.discount,1) into v_discount1 from guestvip a join guesttype b on a.vtid=b.vtid where a.guestid=" + guestid + ";";
			qry += "\n     v_discount:=v_discount1;"; // 没有调价记录，单价=原价*折扣
			qry += "\n     v_isvip:=1;";
			//按会员价方式取价格  0=零售价，1=售价1，2=售价2，3=售价3
			if (vippricetype > 0) {
				qry += "\n   select decode(" + vippricetype + ",1,sale1,2,sale2,3,sale3,retailsale) into v_price0";
				qry += "\n   from warecode b where accid=" + Accid + " and wareid=" + wareid + ";";
			}
			qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN  ";
			qry += "\n     v_discount:=1;";
			qry += "\n   end;";
		}
		// qry += "\n v_discount:=v_vipdiscount;";
		qry += "\n   v_price:=round(v_price0*v_discount," + priceprec + ");"; // 会员折扣价

		// 3.查找是否有调价记录
		qry += "\n   begin";
		qry += "\n     select nvl(price,v_price) into v_price1 from ( ";
		qry += "\n     select case v_pricetype when 0 then b.retailsale  when 1 then b.sale1 when 2 then b.sale2 when 3 then b.sale3 when 4 then b.sale4 when 5 then b.sale5 else b.retailsale end as price";
		qry += "\n     from wareadjusth a join wareadjustm b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n     where a.statetag=2";
		qry += "\n     and a.begindate<to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')+1";
		qry += "\n     and a.enddate>=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') ";
		if (houseid > 0)
			qry += "\n   and exists (select 1 from wareadjustc c where a.accid=c.accid and a.noteno=c.noteno and c.houseid=" + houseid + ")";
		qry += "\n     and b.wareid=" + wareid + " and a.accid=" + Accid + " order by a.notedate desc ) where rownum=1;";
		qry += "\n     if v_price>v_price1 or v_isvip=0 then "; // 如果价格比会员价低或非会员
		qry += "\n        v_price:=v_price1; v_discount:=0; "; // 查到有调价记录
		qry += "\n        if v_price0>0 then";
		qry += "\n           v_discount:=round(v_price/v_price0,2); "; // 有调价记录,折扣=单价/原价
		qry += "\n        end if;";
		qry += "\n     end if ;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN  ";
		qry += "\n     v_price:=round(v_price0*v_discount," + priceprec + ");"; // 没有调价记录，单价=原价*折扣
		qry += "\n   end;";

		qry += "\n   <<exit>>";
		qry += "\n   select v_price00,v_price0,v_discount,v_price into :price00,:price0,:discount,:price from dual; ";
		qry += "\n end; ";

		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("price00", new ProdParam(Types.FLOAT));
		param.put("price0", new ProdParam(Types.FLOAT));
		param.put("discount", new ProdParam(Types.FLOAT));
		param.put("price", new ProdParam(Types.FLOAT));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// 取返回值
		// float amount =
		// Float.parseFloat(param.get("amount").getParamvalue().toString());
		// float curr =
		// Float.parseFloat(param.get("curr").getParamvalue().toString());
		errmess = "\"msg\":\"操作成功！\""//
				+ ",\"PRICE00\":\"" + param.get("price00").getParamvalue().toString() + "\""//
				+ ",\"PRICE0\":\"" + param.get("price0").getParamvalue().toString() + "\""//
				+ ",\"DISCOUNT\":\"" + param.get("discount").getParamvalue().toString() + "\""//
				+ ",\"PRICE\":\"" + param.get("price").getParamvalue().toString() + "\"";
		return 1;
	}

	// 取最近售价
	public int doGetWareoutnearsale(JSONObject jsonObject, int priceprec, int nearsaleok) {
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		long custid = jsonObject.has("custid") ? Long.parseLong(jsonObject.getString("custid")) : 0;
		// long houseid = jsonObject.has("houseid") ?
		// Long.parseLong(jsonObject.getString("houseid")) : 0;
		long saleid = jsonObject.has("saleid") ? Long.parseLong(jsonObject.getString("saleid")) : 0;
		// if (saleid == 3)
		// saleid = 2; // 补单(3)按首单(2)取价格

		// string qry = "select price0,discount,price from nearsale where
		// wareid=" + wareid + " and custid=" + custid + " and saleid=" +
		// saleid;
		//取最近售价
		String qry = "select price0,discount,price  from (";
		qry += "\n   select b.price0,b.discount,b.price from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n   where a.accid=" + Accid + " and a.statetag=1 and a.ntid=1 and a.cleartag=0 and b.wareid=" + wareid + " and a.custid=" + custid;
		//		if (saleid == 3 || saleid == 2) // 补单(3)按首单(2)取价格
		//			qry += "\n   and (b.saleid=2 or b.saleid=3)";// + saleid;
		//		else
		//			qry += "\n   and b.saleid=" + saleid;

		qry += " order by a.notedate desc,b.id desc";
		qry += "\n   ) where rownum=1";
		Table tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "无最近售价记录！";
			return 0;
		}
		errmess = "\"msg\":\"操作成功！\",\"PRICE0\":\"" + tb.getRow(0).get("PRICE0").toString() + "\"" //
				+ ",\"DISCOUNT\":\"" + tb.getRow(0).get("DISCOUNT").toString() + "\"" //
				+ ",\"PRICE\":\"" + tb.getRow(0).get("PRICE").toString() + "\"";

		return 1;
	}

	// 取批发商品售价
	public int doGetWareoutprice(JSONObject jsonObject, int priceprec, int nearsaleok) {
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		long custid = jsonObject.has("custid") ? Long.parseLong(jsonObject.getString("custid")) : 0;
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		long saleid = jsonObject.has("saleid") ? Long.parseLong(jsonObject.getString("saleid")) : 0;
		if (wareid == 0 || custid == 0 || houseid == 0 || saleid == 0) {
			errmess = "wareid或custid或houseid或saleid不是一个有效值！";
			return 0;
		}
		String qry = "declare";
		qry += "\n   v_pricetype number(1); ";
		qry += "\n   v_brandid number(10); ";
		qry += "\n   v_price number(14,2); ";
		qry += "\n   v_price0 number(14,2); ";
		qry += "\n   v_price1 number(14,2); ";
		qry += "\n   v_discount number(14,2); ";
		qry += "\n   v_count number(10); ";
		qry += "\n   vt_price1 number(14,2);vt_discount number(14,2);vt_price number(14,2);";
		qry += "\n begin ";
		// qry += "\n v_discount:=1;v_pricetype:=0;";
		// 1.价格方式 pricetype: 0=零价折扣,1=售价1,2=售价2,3=售价3,4=批发价,5=打包价,6=品牌价格
		// if (custid > 0) {
		qry += "\n   select pricetype,discount into v_pricetype,v_discount from customer where custid=" + custid + " and accid=" + Accid + "; ";
		// 如果有品牌价格折扣，就取该客户的品牌价格及折扣
		qry += "\n      select brandid into v_brandid from warecode where accid=" + Accid + " and wareid=" + wareid + ";";
		qry += "\n      select count(*) into v_count from custbrand where custid=" + custid + " and brandid=v_brandid; ";
		qry += "\n      if v_count>0 then ";
		qry += "\n         select pricetype,discount into v_pricetype,v_discount from custbrand where custid=" + custid + " and brandid=v_brandid; ";
		qry += "\n      end if;";
		// } else if (houseid > 0) {
		// qry += "\n select pricetype into v_pricetype from warehouse where
		// houseid=" + houseid + ";";
		// }
		// qry += "\n if v_pricetype=6 then"; //如果是品牌价格,则查找对应品牌的价格方式和折扣
		// qry += "\n v_pricetype:=0; "; //如果未设定品牌的折扣,则默认为零售价折扣 pricetype=0
		// qry += "\n v_discount:=1; ";
		// qry += "\n end if;";
		qry += "\n   select ";
		qry += "\n   case v_pricetype when 0 then b.retailsale  when 1 then b.sale1 when 2 then b.sale2 when 3 then b.sale3 when 4 then b.sale4 when 5 then b.sale5 else b.retailsale end";
		qry += "\n   into v_price0";
		qry += "\n   from warecode b where accid=" + Accid + " and wareid=" + wareid + ";";
		// 2.查找是否有调价记录（裴晓要求批发单也要取调价单记录）
		qry += "\n   begin";
		// qry += "\n select nvl(price,v_price0) into v_price1 from ( select
		// b.retailsale as price";
		qry += "\n     select price into v_price1 from ( ";
		qry += "\n     select case v_pricetype when 0 then b.retailsale  when 1 then b.sale1 when 2 then b.sale2 when 3 then b.sale3 when 4 then b.sale4 when 5 then b.sale5 else b.retailsale end as price";
		qry += "\n     from wareadjusth a join wareadjustm b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n     where a.statetag=2";
		qry += "\n     and a.begindate<to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')+1";
		qry += "\n     and a.enddate>=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') ";
		if (houseid > 0)
			qry += "\n   and exists (select 1 from wareadjustc c where a.accid=c.accid and a.noteno=c.noteno and c.houseid=" + houseid + ")";
		qry += "\n     and b.wareid=" + wareid + " and a.accid=" + Accid + " order by a.notedate desc ) where rownum=1;";

		qry += "\n     if v_price1>=v_price0 then "; // 如果调后价高于调前价
		qry += "\n        v_price0:=v_price1;v_discount:=1; ";
		qry += "\n        v_price:=v_price1; ";
		qry += "\n     else";
		qry += "\n        v_price:=v_price1; v_discount:=0; "; // 查到有调价记录
		qry += "\n        if v_price0>0 then";
		qry += "\n           v_discount:=round(v_price/v_price0,2); "; // 有调价记录,折扣=单价/原价
		qry += "\n        end if;";
		qry += "\n     end if ;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN  ";
		qry += "\n     v_price:=round(v_price0*v_discount," + priceprec + ");"; // 没有调价记录，单价=原价*折扣
		qry += "\n   end;";
		// 3.如果启用最近售价(批发，批发退货)
		if (nearsaleok == 1 && saleid > 0 && custid > 0) {
			// qry += "\n select count(*) into v_count from nearsale where
			// wareid=" + wareid + " and custid=" + custid + " and saleid=" +
			// saleid + ";";
			// qry += "\n if v_count>0 then ";
			// qry += "\n select price0,discount,price into
			// v_price1,v_discount,v_price from nearsale where wareid=" + wareid
			// + " and custid=" + custid + " and saleid=" + saleid + ";";
			// qry += "\n if v_price0<>v_price1 then";
			// qry += "\n v_discount:=case v_price0 when 0 then 1 else
			// round(v_price/v_price0,2) end;";
			// qry += "\n end if;";
			// qry += "\n end if;";
			//取最近售价
			qry += "\n  begin";
			qry += "\n   select price0,discount,price into vt_price1,vt_discount,vt_price from (";
			qry += "\n   select b.price0,b.discount,b.price from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
			qry += "\n   where a.accid=" + Accid + " and a.statetag=1 and a.ntid=1 and a.cleartag=0 and b.wareid=" + wareid + " and a.custid=" + custid;
			//			if (saleid == 3 || saleid == 2)
			//				qry += "\n   and (b.saleid=2 or b.saleid=3)";
			//			else
			//				qry += "\n   and b.saleid=" + saleid;
			qry += "\n   order by a.notedate desc,b.id desc";
			qry += "\n   ) where rownum=1;";
			qry += "\n    if vt_price1<>null then";
			qry += "\n       v_price1:=vt_price1;v_discount:=vt_discount;v_price:=vt_price;";
			qry += "\n       if v_price0<>v_price1  then";
			qry += "\n          v_discount:=case v_price0 when 0 then 1 else round(v_price/v_price0,2) end;";
			qry += "\n       end if;";
			qry += "\n    end if;";
			qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN  ";
			qry += "\n     v_price1:=0;";
			qry += "\n  end;";

		}
		qry += "\n   select v_price0,v_discount,v_price into :price0,:discount,:price from dual; ";
		qry += "\n end; ";
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("price0", new ProdParam(Types.FLOAT));
		param.put("discount", new ProdParam(Types.FLOAT));
		param.put("price", new ProdParam(Types.FLOAT));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// 取返回值
		// float amount =
		// Float.parseFloat(param.get("amount").getParamvalue().toString());
		// float curr =
		// Float.parseFloat(param.get("curr").getParamvalue().toString());
		errmess = "\"msg\":\"操作成功！\",\"PRICE0\":\"" + param.get("price0").getParamvalue().toString() + "\""//
				+ ",\"DISCOUNT\":\"" + param.get("discount").getParamvalue().toString() + "\""//
				+ ",\"PRICE\":\"" + param.get("price").getParamvalue().toString() + "\"";
		return 1;
	}

	// 取指定客户商品的销售类型
	public int doGetWareoutmsaleid(JSONObject jsonObject) {

		String wareno = jsonObject.has("wareno") ? jsonObject.getString("wareno").replace("'", "''") : "";
		long wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		long custid = jsonObject.has("custid") ? Long.parseLong(jsonObject.getString("custid")) : 0;
		long ntid = jsonObject.has("ntid") ? Integer.parseInt(jsonObject.getString("ntid")) : 0;
		if (ntid == 0) {
			errmess = "\"msg\":\"操作成功！\",\"SALEID\":\"0\"" + ",\"SALENAME\":\"零售\"";
			return 1;
		}
		if (ntid > 2)
			ntid = 1;
		// 0=零售 1=定单 2=首单 3=补货 4=换货 5=代销
		String qry = "declare ";
		qry += "   v_custid number(10); ";
		qry += "   v_wareid number(10); ";
		qry += "   v_accid number(10); ";
		qry += "   v_colorid number(10); ";
		qry += "   v_count number(10); ";
		qry += "   v_saleid number(10); ";
		qry += "   v_salename varchar2(100); ";
		qry += " begin ";
		if (wareid > 0)
			qry += "\n   v_wareid:=" + wareid + ";";
		else if (!Func.isNull(wareno)) {
			qry += "\n   begin";
			qry += "\n     select wareid into v_wareid from warecode where accid=" + Accid + " and wareno='" + wareno + "' and statetag=1 and rownum=1;";
			qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
			qry += "\n     v_saleid:=-1;v_salename:='货号未找到！';";
			qry += "\n     goto exit;";
			qry += "\n   end;";
		}
		/*
		 * qry += "   v_accid:=" + accid + "; "; qry += "   v_custid:=" + custid
		 * + "; "; qry += "   v_wareid:=" + wareid + "; "; qry +=
		 * "   v_colorid:=" + colorid + "; ";
		 */
		if (ntid == 1) // 批发
		{
			qry += "\n   select  count(*) into v_count from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n     where a.statetag=1 and a.ntid=1 and rownum=1 and a.accid=" + Accid;
			qry += "\n     and  b.wareid=v_wareid and a.custid=" + custid + ";";
			qry += "\n   if v_count=0 then v_saleid:=2; else v_saleid:=3; end if; ";
		} else {
			// 找最近一次销售记录
			qry += "\n   begin ";
			qry += "\n    select saleid into v_saleid from (  select  b.saleid from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n     where a.statetag=1 and a.ntid=1  and a.accid=" + Accid;
			qry += "\n     and  b.wareid=v_wareid and a.custid=" + custid + " order by a.notedate desc,b.id desc) where rownum=1; ";
			qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";// 未找到就补货
			qry += "\n     v_saleid:=3; ";
			qry += "\n   end; ";

		}
		qry += "\n   select saleid,salename into v_saleid,v_salename from salecode where saleid=v_saleid; ";
		qry += "\n   <<exit>>";
		qry += "\n   select v_saleid,v_salename into :saleid,:salename from dual; ";
		qry += " end;";
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("saleid", new ProdParam(Types.INTEGER));
		param.put("salename", new ProdParam(Types.VARCHAR));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		Saleid = Long.parseLong(param.get("saleid").getParamvalue().toString());
		if (Saleid < 0) {
			errmess = param.get("salename").getParamvalue().toString();
			return 0;
		}
		// 取返回值
		// float amount =
		// Float.parseFloat(param.get("amount").getParamvalue().toString());
		// float curr =
		// Float.parseFloat(param.get("curr").getParamvalue().toString());
		errmess = "\"msg\":\"操作成功！\",\"SALEID\":\"" + Saleid + "\""//
				+ ",\"SALENAME\":\"" + param.get("salename").getParamvalue().toString() + "\""//
		;
		return 1;

	}

	// 成批增加数据
	public int Appendbat(JSONObject jsonObject, int priceprec, int fs) {
		Wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		Noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : null;
		Price = jsonObject.has("price") ? Float.parseFloat(jsonObject.getString("price")) : null;
		Price0 = jsonObject.has("price0") ? Float.parseFloat(jsonObject.getString("price0")) : 0;
		Discount = jsonObject.has("discount") ? Float.parseFloat(jsonObject.getString("discount")) : 1;
		Saleid = jsonObject.has("saleid") ? Long.parseLong(jsonObject.getString("saleid")) : 0;
		int tocash = jsonObject.has("tocash") ? Integer.parseInt(jsonObject.getString("tocash")) : 0;
		long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		long custid = jsonObject.has("custid") ? Long.parseLong(jsonObject.getString("custid")) : 0;
		int ntid = jsonObject.has("ntid") ? Integer.parseInt(jsonObject.getString("ntid")) : 0;
		int statetag = jsonObject.has("statetag") ? Integer.parseInt(jsonObject.getString("statetag")) : 0;
		// String pricestr = jsonObject.has("pricestr") ?
		// jsonObject.getString("pricestr") : null;
		String remark = jsonObject.has("remark") ? jsonObject.getString("remark") : null;
		String handno = jsonObject.has("handno") ? jsonObject.getString("handno") : null;

		if (Func.isNull(Noteno)) {
			errmess = "noteno不是一个有效值!";
			return 0;
		}

		if (Wareid == null || Wareid == 0) {
			errmess = "wareid不是一个有效值1!";
			return 0;
		}
		if (!pFunc.isWarecodeOk(Accid, Wareid)) {
			errmess = "wareid不是一个有效值2!";
			return 0;
		}
		if (fs == 0 && houseid == 0) { // 不允许负出库，必须传入houseid用于判断库存是否存在
			errmess = "houseid不是一个有效值!";
			return 0;
		}
		if (ntid == 0)
			Saleid = (long) 0;

		if (ntid >= 1 && Saleid == 0) {
			errmess = "saleid不是一个有效值!";
			return 0;
		}

		if (!jsonObject.has("colorsizelist")) {
			errmess = "colorsizelist未传入！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("colorsizelist");

		// if (!Func.isNull(pricestr)) // 如果传入了折扣价，按折后价算折扣
		if (Price != null) {
			// price = decimal.Parse(pricestr);
			// Price = Func.getRound(Float.parseFloat(pricestr), priceprec);
			if (Price0 == 0) {
				Price0 = Price;
				Discount = (float) 1;
			}

			else
				Discount = Func.getRound(Price / Price0, 2);
		} else {
			Price = Func.getRound(Price0 * Discount, priceprec);
			// curr = price * amount;
		}

		String qry = "declare ";
		qry += "\n    v_totalcurr  number;";
		qry += "\n    v_totalcheckcurr  number;";
		qry += "\n    v_totalamt  number;";
		qry += "\n    v_id  number;";
		qry += "\n    v_count  number;";
		qry += "\n    v_custid  number(10);";
		qry += "\n    v_saleid  number(10);";
		qry += "\n    v_tjtag  number(1);";
		qry += "\n    v_noteno  varchar2(20);";
		qry += "\n    v_retcs  varchar2(100);";
		qry += "\n    v_notedate  date;";
		qry += "\n begin ";
		qry += "\n    v_saleid:=" + Saleid + ";";
		qry += "\n    v_custid:=" + custid + ";";
		if (ntid == 1 && Saleid >= 2 && Saleid <= 3) // 如果是批发，且销售类型是首单及补货，要校验
		{
			qry += "\n   select  count(*) into v_count from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n   where a.statetag=1 and rownum=1 and a.ntid=1 and a.accid=" + Accid + "";
			qry += "\n   and  b.wareid=" + Wareid + "  and a.custid=v_custid;"; // and
			qry += "\n   if v_count=0 then v_saleid:=2; else v_saleid:=3; end if; ";
		}
		qry += "\n    update wareoutm set amount=0 where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + " and saleid=v_saleid;";
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
			qry += "\n    select id into v_id from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + " and colorid=" + Colorid //
					+ " and Sizeid=" + Sizeid + " and saleid=v_saleid and price=" + Price + " and rownum=1; ";
			qry += "\n    update wareoutm set amount=amount+" + Amount + ",curr=(amount+" + Amount + ")*price where id=v_id;";
			qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN"; // 未找到，挺插入新记录
			qry += "\n    select tjtag into v_tjtag from warecode where accid=" + Accid + " and wareid=" + Wareid + ";";
			qry += "\n    insert into wareoutm (id,accid,noteno,wareid,colorid,saleid,sizeid,amount,price0,discount,price,curr,remark0,tjtag)";
			qry += "\n    values (wareoutm_id.nextval," + Accid + ",'" + Noteno + "'," + Wareid + "," + Colorid + ",v_saleid," + Sizeid + ","//
					+ Amount + "," + Price0 + "," + Discount + "," + Price + "," + Curr + ",'',v_tjtag);";
			qry += "\n end; ";
		}
		qry += "\n  delete wareoutm where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + " and amount=0;";

		qry += "\n  select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "';";

		qry += "\n  update wareouth set totalcurr=v_totalcurr,totalamt=v_totalamt,lastdate=sysdate ";
		if (remark != null)
			qry += ",remark='" + remark + "'";
		if (handno != null)
			qry += ",handno='" + handno + "'";
		qry += "\n  where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n  commit; ";

		if ((ntid == 1 || ntid == 0) && (statetag == 1 || tocash == 1) && fs == 0 && houseid > 0) {
			// 要判断负库存
			qry += "\n  select notedate into v_notedate from wareouth where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n begin";
			qry += "\n   select b.wareno||','||c.colorname||','||d.sizename into v_retcs from ";
			qry += "\n   (select wareid,colorid,sizeid,sum(amount) as amount from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "'";
			qry += "\n   group by wareid,colorid,sizeid) a";
			qry += "\n   left outer join warecode b on a.wareid=b.wareid";
			qry += "\n   left outer join colorcode c on a.colorid=c.colorid";
			qry += "\n   left outer join sizecode d on a.sizeid=d.sizeid";
			qry += "\n   where a.amount>0 and a.amount>f_getwareamountx(to_char(sysdate,'yyyy-mm-dd')," + Accid + ",a.wareid,a.colorid,a.sizeid," + houseid + ",'" + Calcdate + "') and rownum=1;";

			qry += "\n   v_retcs:='0'||v_retcs||'库存不足，不允许出库！' ; ";
			// qry += " rollback; ";
			qry += "\n   goto exit; ";

			qry += "\n EXCEPTION WHEN NO_DATA_FOUND THEN ";
			qry += "\n   v_retcs:=''; ";
			qry += "\n end;";

		}
		if (tocash == 1) {
			qry += "\n  select count(*) into v_count from waresaleman where accid=" + Accid + " and noteno='" + Noteno + "'; "; // 销售人员数
			qry += "\n  update wareouth set statetag=3,notedate=sysdate,lastdate=sysdate,rs=v_count";
			qry += "\n  where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n  commit; ";
		}
		// if (tocash == 1 || statetag == 1) //上下衣区位处理
		// {
		// qry += "\n p_wareoutsetarea(" + Accid + ",'" + Noteno + "');";
		// }
		qry += "\n   commit; ";
		qry += "\n   v_retcs:='1操作成功！' ; ";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual; ";
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

	// 清除所有数据
	public int Clearall() {
		if (Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		String qry = "begin";
		qry += "\n     delete from wareoutm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n     update wareouth set totalamt=0,totalcurr=0 where accid=" + Accid + " and noteno='" + Noteno + "';";

		qry += "\n     insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n     values (LOGRECORD_id.nextval," + Accid + ",'批发出库','【清空数据】销售单号:" + Noteno + "','" + Lastop + "');";

		qry += " end;";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}
}