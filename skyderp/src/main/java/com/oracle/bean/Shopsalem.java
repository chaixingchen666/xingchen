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
import com.common.tool.LogUtils;
import com.common.tool.PinYin;
////import com.common.tool.SizeParam;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-27 15:07:35
//*************************************
public class Shopsalem implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private String Noteno;
	private Long Userid;
	private Long Houseid;
	private Long Wareid;
	private Long Colorid;
	private Long Sizeid;
	private Float Amount;
	private Float Price0;
	private Float Price00;
	private Float Discount;
	private Float Price;
	private Float Curr;
	private Long Salemanid;
	private String Salemanno;
	private String Remark0;
	private Date Lastdate;
	private Long Saleid;
	private Integer Iszp;
	private Long Areaid;
	private Integer Tjtag;
	private String errmess;
	private String Lastop;

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

	public void setUserid(Long userid) {
		this.Userid = userid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setNoteno(String noteno) {
		this.Noteno = noteno;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getNoteno() {
		return Noteno;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
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

	public void setSalemanid(Long salemanid) {
		this.Salemanid = salemanid;
	}

	public Long getSalemanid() {
		return Salemanid;
	}

	public void setRemark0(String remark0) {
		this.Remark0 = remark0;
	}

	public String getRemark0() {
		return Remark0;
	}

	public void setSaleid(Long saleid) {
		this.Saleid = saleid;
	}

	public Long getSaleid() {
		return Saleid;
	}

	public void setIszp(Integer iszp) {
		this.Iszp = iszp;
	}

	public Integer getIszp() {
		return Iszp;
	}

	public void setAreaid(Long areaid) {
		this.Areaid = areaid;
	}

	public Long getAreaid() {
		return Areaid;
	}

	public void setTjtag(Integer tjtag) {
		this.Tjtag = tjtag;
	}

	public Integer getTjtag() {
		return Tjtag;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int DeletebyId() {
		if (Id == 0 || Noteno.length() <= 0) {
			errmess = "id或noteno不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_totalcurr number; ");
		strSql.append("\n   v_totalamt number; ");
		strSql.append("\n begin ");
		strSql.append("\n  delete from shopsalem ");
		strSql.append("\n  where id=" + Id + " and accid= " + Accid + " and noteno='" + Noteno + "';");
		// 汇总数量及金额
		strSql.append("\n  select  nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from shopsalem where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n  update shopsaleh set totalcurr=v_totalcurr,totalamt=v_totalamt ");
		strSql.append("\n  where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n  commit;");
		strSql.append("\n  select v_totalamt,v_totalcurr into :totalamt,:totalcurr from dual; ");
		strSql.append("\n end;");
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalamt", new ProdParam(Types.FLOAT));
		param.put("totalcurr", new ProdParam(Types.FLOAT));
		// 调用
		if (DbHelperSQL.ExecuteProc(strSql.toString(), param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		errmess = "\"msg\":\"操作成功！\",\"TOTALAMT\":\"" + param.get("totalamt").getParamvalue().toString() + "\"" //
				+ ",\"TOTALCURR\":\"" + param.get("totalcurr").getParamvalue().toString() + "\"";
		return 1;
	}

	// 增加记录
	public int Append(int priceprec, int currprec) {
		if (Amount == null || Amount == 0) {
			errmess = "amount不是一个有效值！";
			return 0;
		}
		Noteno = Noteno.toUpperCase();
		if (Curr == null) {
			if (Price != null) // 如果传入了折扣价，按折后价算折扣
			{
				Price = Func.getRound(Price, priceprec);// Math.Round((decimal)model.price, priceprec, MidpointRounding.AwayFromZero);
				Curr = Func.getRound(Price * Amount, currprec);// Math.Round((decimal)(model.price * model.amount), 2, MidpointRounding.AwayFromZero);
				if (Price0 == 0)
					Discount = (float) 1;
			} else {
				Price = Func.getRound(Price0 * Discount, priceprec);// Math.Round((decimal)(model.price0 * model.discount), priceprec, MidpointRounding.AwayFromZero);
				Curr = Func.getRound(Price * Amount, currprec);
			}
		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		strSql1.append("noteno,");
		strSql2.append("'" + Noteno + "',");
		if (Houseid != null) {
			strSql1.append("houseid,");
			strSql2.append(Houseid + ",");
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
		if (Price0 != null) {
			strSql1.append("price0,price00,");
			strSql2.append(Price0 + "," + Price0 + ",");
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
		// if (Salemanid != null) {
		strSql1.append("salemanid,");
		strSql2.append("v_salemanid,");
		// }
		if (Remark0 != null) {
			strSql1.append("remark0,");
			strSql2.append("'" + Remark0 + "',");
		}
		if (Saleid != null) {
			strSql1.append("saleid,");
			strSql2.append(Saleid + ",");
		}
		if (Iszp != null) {
			strSql1.append("iszp,");
			strSql2.append(Iszp + ",");
		}
		if (Areaid != null) {
			strSql1.append("areaid,");
			strSql2.append(Areaid + ",");
		}
		if (Tjtag != null) {
			strSql1.append("tjtag,");
			strSql2.append(Tjtag + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");

		strSql.append("declare ");
		strSql.append("   v_totalcurr number; ");
		strSql.append("   v_totalamt number; ");
		strSql.append("   v_retcs varchar2(200); ");
		strSql.append("   v_housename varchar2(100); ");
		strSql.append("   v_tjtag number(1); ");
		strSql.append("   v_sizegroupno varchar2(60);");
		strSql.append("   v_id number; ");
		strSql.append("   v_houseid number; ");
		strSql.append("   v_salemanid number; ");
		strSql.append("   v_houseid0 number; ");
		// strSql.Append(" v_custid number; ");
		strSql.append("   v_count number; ");
		strSql.append("\n begin ");
		strSql.append("\n    v_totalamt:=0;v_totalcurr:=0;");

		if (Salemanid != null && Salemanid > 0) {

			strSql.append("\n   select count(*) into v_count from employe where accid=" + Accid + " and epid=" + Salemanid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0销售人无效1！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
			strSql.append("\n   v_salemanid:=" + Salemanid + ";");

		} else {
			if (Salemanno == null || Salemanno.length() == 0) {
				errmess = "salemanno不是一个有效值！";
				return 0;
			}
			strSql.append("\n   begin");
			strSql.append("\n     select epid into v_salemanid from employe where accid=" + Accid + " and epno='" + Salemanno + "' and statetag=1;");
			strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
			strSql.append("\n     v_retcs:='0销售人无效2！';");
			strSql.append("\n     goto exit;");
			strSql.append("\n   end;");

		}
		strSql.append("\n   begin");
		strSql.append("\n     select b.tjtag,a.groupno into v_tjtag,v_sizegroupno ");
		strSql.append("\n     from sizecode a join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno");
		strSql.append("\n     where a.accid=" + Accid + " and a.sizeid=" + Sizeid + " and b.wareid=" + Wareid + "; ");
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n     v_retcs:='0尺码无效！';");
		strSql.append("\n     goto exit;");
		strSql.append("\n   end;");

		//		strSql.append("\n   select count(*) into v_count from sizecode where accid=" + Accid + " and sizeid=" + Sizeid + " and statetag=1 and groupno=v_sizegroupno; ");
		//		strSql.append("\n   if v_count=0 then ");
		//		strSql.append("\n      v_retcs:='0商品与尺码未对应！'; ");
		//		strSql.append("\n      goto exit; ");
		//		strSql.append("\n   end if;");

		strSql.append("\n   select houseid into v_houseid from employe where accid=" + Accid + " and epid=v_salemanid; ");
		strSql.append("\n  begin");
		strSql.append("\n    select houseid into v_houseid0 from shopsaleman where accid=" + Accid + " and salemanid=v_salemanid");
		strSql.append("\n      and workdate>=trunc(sysdate) and workdate<trunc(sysdate)+1 and statetag=1 ;"); // 当天
		strSql.append("\n    v_houseid:=v_houseid0; ");
		strSql.append("\n  EXCEPTION WHEN NO_DATA_FOUND THEN"); // 未找到换班记录
		strSql.append("\n    v_houseid0:=0; ");
		strSql.append("\n  end;");
		strSql.append("\n  if v_houseid=0 then ");
		strSql.append("\n     v_retcs:='0销售人未指定店铺！'; ");
		strSql.append("\n     goto exit; ");
		strSql.append("\n  end if;");
		strSql.append("\n    select housename into v_housename from warehouse where houseid=v_houseid and accid=" + Accid + ";");
		strSql.append("\n    v_id:=shopsalem_id.nextval; ");
		strSql.append("\n    insert into shopsalem (");
		strSql.append(strSql1.toString());
		strSql.append(") \n values (");
		strSql.append(strSql2.toString());
		strSql.append(");");
		// strSql.Append(" end;");
		// 汇总数量及金额

		strSql.append("\n  select  nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from shopsalem where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n  update shopsaleh set totalcurr=v_totalcurr,totalamt=v_totalamt,lastdate=sysdate");
		// if (tocash == 1) strSql.Append(",statetag=3 ,lastdate=sysdate");
		strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n  commit;");
		strSql.append("\n  insert into LOGRECORD (id,accid,progname,remark,lastop)");
		strSql.append("\n  values (LOGRECORD_id.nextval," + Accid + ",'商场开票','【输入增加商品】单据号:" + Noteno + ",商品id:" + Wareid + ",颜色id:" + Colorid + ",尺码id:" + Sizeid + ",数量:" + Amount + "','" + Lastop + "');");

		strSql.append("\n  v_retcs:= '1'||v_id; "); // 不加这2句要出错
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs,v_totalamt,v_totalcurr into :retcs,:totalamt,:totalcurr from dual; "); // 不加这2句要出错
		strSql.append("\n end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		param.put("totalamt", new ProdParam(Types.FLOAT));
		param.put("totalcurr", new ProdParam(Types.FLOAT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		if (retcs.substring(0, 1).equals("0")) {
			errmess = retcs.substring(1);
			return 0;
		}

		Id = Float.parseFloat(retcs.substring(1));
		float totalamt = Float.parseFloat(param.get("totalamt").getParamvalue().toString());
		float totalcurr = Float.parseFloat(param.get("totalcurr").getParamvalue().toString());

		retcs = "\"msg\":\"" + Id + "\""//
				+ ",\"TOTALAMT\":\"" + totalamt + "\"" //
				+ ",\"TOTALCURR\":\"" + totalcurr + "\"";
		String qry = "select a.id,a.wareid,a.colorid,a.sizeid,a.salemanid,a.houseid,a.amount,a.price0,a.discount,a.price,a.curr,a.remark0";
		qry += ",b.warename,b.wareno,b.units,c.colorname,d.sizename,e.epname,f.housename";
		qry += " from shopsalem a ";
		qry += " left outer join warecode b on a.wareid=b.wareid";
		qry += " left outer join colorcode c on a.colorid=c.colorid";
		qry += " left outer join sizecode d on a.sizeid=d.sizeid";
		qry += " left outer join employe e on a.salemanid=e.epid";
		qry += " left outer join warehouse f on a.houseid=f.houseid";
		qry += "  where a.accid=" + Accid + " and a.noteno='" + Noteno + "' and a.id=" + Id;
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		retcs += ",\"rows\":{";
		for (int j = 0; j < tb.getColumnCount(); j++) {//
			if (j > 0)
				retcs += ",";
			String strKey = tb.getRow(0).getColumn(j + 1).getName().toUpperCase();//
			String strValue = tb.getRow(0).get(j).toString();//
			retcs += "\"" + strKey + "\":\"" + strValue + "\"";
		}
		retcs += "}";
		errmess = retcs;
		return 1;
	}

	// 修改记录
	public int Update(JSONObject jsonObject, int priceprec, int currprec, int fs) {

		if (Func.isNull(Noteno) || Id == null || Id == 0) {
			errmess = "noteno或id不是一个有效值！";
			return 0;
		}
		if (Wareid == null || Wareid == 0 || Colorid == null || Colorid == 0 || Sizeid == null || Sizeid == 0) {
			errmess = "wareid或colorid或sizeid不是一个有效值！";
			return 0;
		}
		if (Salemanid == null && Salemanno == null) {
			errmess = "salemanid或salemanno不是一个有效值！";
			return 0;

		}
		Noteno = Noteno.toUpperCase();
		// 以下代码要注释===========================================
		if (Price != null) // 如果传入了折扣价，按折后价算折扣
		{
			Price = Func.getRound(Price, priceprec);
			// model.curr = model.price * model.amount;
			Curr = Func.getRound(Price * Amount, currprec);

			if (Price0 == 0)
				Discount = (float) 1;
			// else model.discount = Math.Round((decimal)(model.price / model.price0), 2, MidpointRounding.AwayFromZero);
		} else {
			Price = Func.getRound(Price0 * Discount, priceprec);
			// model.curr = model.price * model.amount;
			Curr = Func.getRound(Price * Amount, currprec);
		}
		if (Amount == null || Amount == 0) {
			errmess = "数量不允许为零！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_totalcurr number; ");
		strSql.append("\n   v_totalamt number; ");
		strSql.append("\n   v_salemanid number(10); ");
		strSql.append("\n   v_custid number(10); ");
		strSql.append("\n   v_houseid number(10); ");
		strSql.append("\n   v_houseid0 number(10); ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_housename varchar2(100); ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		strSql.append("\n   v_totalamt:=0;v_totalcurr:=0;");
		strSql.append("\n   select count(*) into v_count from warecode where accid=" + Accid + " and statetag=1 and wareid=" + Wareid + ";");
		strSql.append("\n   if v_count=0 then");
		strSql.append("\n      v_retcs:='0商品无效';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   select count(*) into v_count from warecolor a join colorcode b on a.colorid=b.colorid where a.wareid=" + Wareid + " and b.statetag=1 and a.colorid=" + Colorid + ";");
		strSql.append("\n   if v_count=0 then");
		strSql.append("\n      v_retcs:='0商品颜色无效';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		// strSql.append("\n select count(*) into v_count from sizecode where accid="+Accid+" and statetag=1 and sizeid="+Sizeid+";");
		strSql.append("\n   select count(*) into v_count from sizecode a join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno ");
		strSql.append("\n   where a.accid=" + Accid + " and b.wareid=" + Wareid + " and a.sizeid=" + Sizeid + ";");

		strSql.append("\n   if v_count=0 then");
		strSql.append("\n      v_retcs:='0商品尺码无效';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		if (Salemanid != null) {
			// 查找销售人员对应的店铺id
			strSql.append("\n  select houseid,epid into v_houseid,v_salemanid from employe where accid=" + Accid + " and statetag=1 and epid=" + Salemanid + "; ");
			strSql.append("\n  begin");
			strSql.append("\n    select houseid into v_houseid0 from shopsaleman where accid=" + Accid + " and salemanid=" + Salemanid);
			strSql.append("\n      and workdate>=trunc(sysdate) and workdate<trunc(sysdate)+1 and statetag=1 ;"); // 当天
			strSql.append("\n    v_houseid:=v_houseid0; ");
			strSql.append("\n  EXCEPTION WHEN NO_DATA_FOUND THEN"); // 未找到换班记录
			strSql.append("\n    v_houseid0:=0; ");
			strSql.append("\n  end;");
			strSql.append("\n  if v_houseid=0 then ");
			strSql.append("\n     v_retcs:=  '0销售人未指定店铺！'; ");
			strSql.append("\n     goto exit; ");
			strSql.append("\n  end if;");
			strSql.append("\n  select housename into v_housename from  warehouse where accid=" + Accid + " and  houseid=v_houseid;");
		} else {
			strSql.append("\n  select houseid,epid into v_houseid,v_salemanid from employe where accid=" + Accid + " and statetag=1 and epno=" + Salemanno + " and rownum=1; ");
			strSql.append("\n  begin");
			strSql.append("\n    select houseid into v_houseid0 from shopsaleman where accid=" + Accid + " and salemanid=" + Salemanid);
			strSql.append("\n      and workdate>=trunc(sysdate) and workdate<trunc(sysdate)+1 and statetag=1 ;"); // 当天
			strSql.append("\n    v_houseid:=v_houseid0; ");
			strSql.append("\n  EXCEPTION WHEN NO_DATA_FOUND THEN"); // 未找到换班记录
			strSql.append("\n    v_houseid0:=0; ");
			strSql.append("\n  end;");
			strSql.append("\n  if v_houseid=0 then ");
			strSql.append("\n     v_retcs:=  '0销售人未指定店铺！'; ");
			strSql.append("\n     goto exit; ");
			strSql.append("\n  end if;");
			strSql.append("\n  select housename into v_housename from  warehouse where accid=" + Accid + " and  houseid=v_houseid;");

		}

		strSql.append("\n   update shopsalem set ");

		if (Salemanid != null) {
			// if (Houseid != null) {
			strSql.append("houseid=v_houseid ,");
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
			strSql.append("amount=" + Amount + ",");
		}
		if (Price0 != null) {
			strSql.append("price0=" + Price0 + ",price00=" + Price0 + ",");
		}
		if (Discount != null) {
			strSql.append("discount=" + Discount + ",");
		}
		if (Price != null) {
			strSql.append("price=" + Price + ",");
		}
		if (Curr != null) {
			strSql.append("curr=" + Curr + ",");
		}
		// if (Salemanid != null) {
		strSql.append("salemanid=v_salemanid ,");
		// }
		if (Remark0 != null) {
			strSql.append("remark0='" + Remark0 + "',");
		}
		// if (Saleid != null) {
		// strSql.append("saleid=0,");
		// }
		if (Iszp != null) {
			strSql.append("iszp=" + Iszp + ",");
		}
		if (Areaid != null) {
			strSql.append("areaid=" + Areaid + ",");
		}
		if (Tjtag != null) {
			strSql.append("tjtag=" + Tjtag + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("\n  where id=" + Id + " and accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n  commit;");
		strSql.append("\n  select  nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from shopsalem where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n  update shopsaleh set totalcurr=v_totalcurr,totalamt=v_totalamt ");
		strSql.append("\n  where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n  v_retcs:='1'||v_housename ; ");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs,v_totalamt,v_totalcurr into :retcs,:totalamt,:totalcurr from dual;");

		strSql.append("\n end;");
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		param.put("totalamt", new ProdParam(Types.FLOAT));
		param.put("totalcurr", new ProdParam(Types.FLOAT));
		// 调用
		if (DbHelperSQL.ExecuteProc(strSql.toString(), param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		if (retcs.substring(0, 1).equals("0")) {

			errmess = retcs.substring(1);
			return 0;
		}

		// 取返回值
		float totalamt = Float.parseFloat(param.get("totalamt").getParamvalue().toString());
		float totalcurr = Float.parseFloat(param.get("totalcurr").getParamvalue().toString());
		String housename = retcs.substring(1);
		retcs = "\"msg\":\"" + housename + "\""//
				+ ",\"TOTALAMT\":\"" + totalamt + "\"" //
				+ ",\"TOTALCURR\":\"" + totalcurr + "\"";
		String qry = "select a.id,a.wareid,a.colorid,a.sizeid,a.salemanid,a.houseid,a.amount,a.price0,a.discount,a.price,a.curr,a.remark0";
		qry += ",b.warename,b.wareno,b.units,c.colorname,d.sizename,e.epname,f.housename";
		qry += " from shopsalem a ";
		qry += " left outer join warecode b on a.wareid=b.wareid";
		qry += " left outer join colorcode c on a.colorid=c.colorid";
		qry += " left outer join sizecode d on a.sizeid=d.sizeid";
		qry += " left outer join employe e on a.salemanid=e.epid";
		qry += " left outer join warehouse f on a.houseid=f.houseid";
		qry += "  where a.accid=" + Accid + " and a.noteno='" + Noteno + "' and a.id=" + Id;
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		retcs += ",\"rows\":{";
		for (int j = 0; j < tb.getColumnCount(); j++) {//
			if (j > 0)
				retcs += ",";
			String strKey = tb.getRow(0).getColumn(j + 1).getName().toUpperCase();//
			String strValue = tb.getRow(0).get(j).toString();//
			retcs += "\"" + strKey + "\":\"" + strValue + "\"";
		}
		retcs += "}";
		errmess = retcs;
		return 1;
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM shopsalem a");
		strSql.append(" left outer join warecode b on a.wareid=b.wareid");
		strSql.append(" left outer join colorcode c on a.colorid=c.colorid");
		strSql.append(" left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append(" left outer join warehouse e on a.houseid=e.houseid");
		strSql.append(" left outer join employe f on a.salemanid=f.epid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM shopsalem a");
		strSql.append(" left outer join warecode b on a.wareid=b.wareid");
		strSql.append(" left outer join colorcode c on a.colorid=c.colorid");
		strSql.append(" left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append(" left outer join warehouse e on a.houseid=e.houseid");
		strSql.append(" left outer join employe f on a.salemanid=f.epid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		qp.setSumString("nvl(sum(amount),0) as totalamt,nvl(sum(curr),0) as totalcurr");
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from shopsalem");
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
	public int doBarcodein(String barcode, int pricetype, int priceprec, int currprec, int qxbj, String salemanno, long guestid, long houseid, int vippricetype) {
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
		// if (houseid == 0) {
		// errmess = "houseid不是一个有效值！";
		// return 0;
		// }
		String qry = "declare ";
		qry += "\n    v_accid  number;"; // --账户id
		qry += "\n    v_noteno  varchar2(20);"; // --单据号
		qry += "\n    v_barcode  varchar2(30); "; // --条码
		qry += "\n    v_discount  number; "; // --折扣
		qry += "\n    v_discount1  number; "; // --折扣
		// qry += "\n v_pricetype number; "; // --价格方式 0=进价1=零价
		qry += "\n    v_amount  number; "; // --个数
		qry += "\n    v_houseid number(10); "; //
		qry += "\n    v_houseid0 number(10); "; //
		qry += "\n    v_wareid number(10); "; //
		qry += "\n    v_colorid number(10); "; //
		qry += "\n    v_sizeid number(10); "; //
		qry += "\n    v_barcode0  varchar2(30); "; // --条码
		qry += "\n    v_id  number(20); ";
		qry += "\n    v_id0  number(20); ";
		qry += "\n    v_price number;";
		qry += "\n    v_price0 number; ";
		qry += "\n    v_price00 number; ";
		qry += "\n    v_price1 number; ";
		qry += "\n    v_price2 number; ";
		qry += "\n    v_count number; ";
		qry += "\n    v_curr number; ";
		qry += "\n    v_totalcurr number; ";
		qry += "\n    v_totalamt number; ";
		qry += "\n    v_wareno varchar2(40); ";
		qry += "\n    v_warename varchar2(80); ";
		qry += "\n    v_colorname varchar2(40); ";
		qry += "\n    v_sizename varchar2(40); ";

		qry += "\n    v_housename varchar2(50); ";
		qry += "\n    v_retcs varchar2(200); ";
		qry += "\n    v_salemanid number(10); ";
		qry += "\n    v_statetag number(1);";
		qry += "\n    v_tjtag number(1); ";
		qry += "\n    v_pricetype number(1); ";
		qry += "\n begin ";
		qry += "\n  v_id:=0;v_totalcurr:=0;v_totalamt:=0;";
		// 查找销售人员对应的店铺id
		qry += "\n  begin";
		if (Salemanid > 0) {
			qry += "\n  select epid,houseid into v_salemanid,v_houseid from employe where accid=" + Accid + " and epid=" + Salemanid + " and statetag=1 and noused=0; ";
		} else {
			qry += "\n  select epid,houseid  into v_salemanid,v_houseid from employe where accid=" + Accid + " and epno='" + salemanno.toUpperCase() + "' and statetag=1 and noused=0 and rownum=1; ";
		}
		qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN"; // 未找到换班记录
		qry += "\n     v_retcs:='0销售人无效！'; ";
		qry += "\n     goto exit; ";
		qry += "\n  end;";
		if (houseid > 0)
			qry += "\n  v_houseid:=" + houseid + ";";
		qry += "\n  begin";
		qry += "\n    select housename,pricetype into v_housename,v_pricetype from warehouse where houseid=v_houseid and accid=" + Accid + ";";
		qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n    v_retcs:= '0 店铺未找到！';  ";
		qry += "\n    goto exit;";
		qry += "\n  end;";

		qry += "\n    v_amount:=" + Amount + "; ";
		// qry += "\n v_discount:=" + discount + "; ";
		qry += "\n    v_discount:=1; ";

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
		// qry += "\n select '0 未找到条码:'||'" + barcode + "' into :v_mess from dual; ";
		qry += "\n        v_retcs:= '0 未找到条码:'||'" + barcode + "';  ";
		qry += "\n        goto exit;";
		qry += "\n      end;";
		qry += "\n   end if;";

		qry += "\n   begin";

		qry += "\n     select a.wareid,a.colorid,a.sizeid";

		qry += "      ,case v_pricetype when 0 then b.retailsale when 1 then b.sale1 when 2 then b.sale2 when 3 then b.sale3 when 4 then b.sale4 when 5 then b.sale5 else b.retailsale end";
		qry += "       ,b.wareno,b.warename,c.colorname,d.sizename,b.tjtag  ";//
		qry += "\n     into v_wareid,v_colorid,v_sizeid,v_price0 ,v_wareno,v_warename,v_colorname,v_sizename ,v_tjtag ";
		qry += "\n     from warebarcode a  ";
		qry += "\n     left outer join warecode b on a.wareid=b.wareid ";
		qry += "\n     left outer join colorcode c on a.colorid=c.colorid ";
		qry += "\n     left outer join sizecode d on a.sizeid=d.sizeid ";
		qry += "\n     where a.id=v_id and b.sizegroupno=d.groupno and b.statetag=1 ";//
		if (qxbj == 1) // 1 启用权限控制
			qry += "\n and (b.brandid=0 or exists (select 1 from employebrand x where b.brandid=x.brandid and x.epid=" + Userid + "))";
		qry += "     ;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_retcs:= '0 商品未授权或条码尺码异常！' ;  ";
		qry += "\n     goto exit;";
		qry += "\n   end;";
		qry += "\n    if v_tjtag=1 then";// 特价商品不打折
		qry += "\n       v_discount:=1;";
		qry += "\n       v_price:=v_price0;";
		qry += "\n       v_curr:=round(v_price*v_amount,2);";
		qry += "\n       goto exit1;";
		qry += "\n    end if;";

		// if (houseid > 0) {
		// qry += "\n select pricetype into v_pricetype from warehouse where houseid=" + houseid + ";";
		// qry += "\n if v_pricetype>0 then"; // 按店铺价格取售价
		// qry += "\n select case v_pricetype when 1 then b.sale1 when 2 then b.sale2 when 3 then b.sale3 when 4 then b.sale4 when 5 then b.sale5 else b.retailsale end into v_price0";
		// qry += "\n from warecode b where accid=" + Accid + " and wareid=v_wareid;";
		// qry += "\n end if;";
		// }
		// 2.取商场价格
		qry += "\n   begin";
		qry += "\n     select nvl(retailsale,v_price0) into v_price2 from housesaleprice where wareid=v_wareid and houseid=v_houseid;";
		qry += "\n     v_price0:=v_price2;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_price2:=0;";
		qry += "\n   end;";
		qry += "\n   v_price00:=v_price0;";

		// 取会员折扣
		// qry += "\n v_discount:=1;";
		if (guestid > 0) {
			qry += "\n   begin";
			qry += "\n     select b.discount into v_discount1 from guestvip a join guesttype b on a.vtid=b.vtid where a.guestid=" + guestid + ";";
			qry += "\n     v_discount:=v_discount1;"; // 没有调价记录，单价=原价*折扣
			if (vippricetype > 0) {
				qry += "\n   select decode(" + vippricetype + ",1,sale1,2,sale2,3,sale3,retailsale) into v_price0";
				qry += "\n   from warecode b where accid=" + Accid + " and wareid=v_wareid;";
			}

			qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN  ";
			qry += "\n     v_discount:=1;";
			qry += "\n   end;";
		}
		// qry += "\n v_discount:=v_vipdiscount;";
		qry += "\n   v_price:=round(v_price0*v_discount," + priceprec + ");"; // 会员折扣价

		// 2.查找是否有调价记录
		qry += "\n   begin";

		qry += "\n     select nvl(price,v_price) into v_price1 from ( ";// select b.retailsale as price";
		qry += "\n     select case v_pricetype when 0 then b.retailsale  when 1 then b.sale1 when 2 then b.sale2 when 3 then b.sale3 when 4 then b.sale4 when 5 then b.sale5 else b.retailsale end as price";
		qry += "\n     from wareadjusth a join wareadjustm b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n     where a.statetag=2";
		qry += "\n     and a.begindate<to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')+1";
		qry += "\n     and a.enddate>=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') ";
		qry += "\n     and exists (select 1 from wareadjustc c where a.accid=c.accid and a.noteno=c.noteno and c.houseid=v_houseid)";
		qry += "\n     and b.wareid=v_wareid and a.accid=" + Accid + " order by a.notedate desc ) where rownum=1;";

		qry += "\n     if v_price>v_price1 then "; // 如果价格比会员价低
		qry += "\n        v_price:=v_price1; v_discount:=0; "; // 查到有调价记录
		qry += "\n        if v_price0>0 then";
		qry += "\n           v_discount:=round(v_price/v_price0,2); "; // 有调价记录,折扣=单价/原价
		qry += "\n        end if;";
		qry += "\n    end if ;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN  ";
		qry += "\n     v_price:=round(v_price0*v_discount," + priceprec + ");"; // 没有调价记录，单价=原价*折扣
		qry += "\n   end;";

		qry += "\n   v_curr:=round(v_price*v_amount," + currprec + "); ";//
		qry += "\n   <<exit1>>";
		// qry += "\n if v_tjtag=1 then";// 特价商品不打折
		// qry += "\n v_discount:=1;";
		// qry += "\n v_price:=v_price0;";
		// qry += "\n v_curr:=round(v_price*v_amount,2);";
		// qry += "\n end if;";

		qry += "\n   select statetag into v_statetag from shopsaleh where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   if v_statetag>0 then";
		qry += "\n      v_retcs:= '0 当前单据已结账！';  ";
		// qry += "\n insert into LOGRECORD (id,accid,progname,remark,lastop)";
		// qry += "\n values (LOGRECORD_id.nextval," + Accid + ",'商场开票','【扫码增加商品异常】单据号:" + Noteno + ",商品id:'||v_wareid||',颜色id:'||v_colorid||',尺码id:'||v_sizeid||',数量:'||v_amount,'" + Lastop + "');";
		qry += "\n      goto exit;";
		qry += "\n   end if;";

		qry += "\n    v_id:=shopsalem_id.nextval;";
		qry += "\n    insert into shopsalem (id,accid,noteno,houseid,salemanid,wareid,colorid,sizeid,amount,price0,price00,discount,price,curr,remark0,tjtag) ";//
		qry += "\n    values (v_id," + Accid + ",'" + Noteno + "',v_houseid,v_salemanid ,v_wareid,v_colorid,v_sizeid,v_amount,v_price0,v_price00,v_discount,v_price,v_curr,'',v_tjtag); ";

		qry += "\n     select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from shopsalem where accid=" + Accid + " and noteno='" + Noteno + "';  ";
		qry += "\n     update shopsaleh set totalcurr=v_totalcurr,totalamt=v_totalamt  ";
		qry += "\n     where accid=" + Accid + " and noteno='" + Noteno + "'; ";

		qry += "\n     v_retcs:= '1 '||v_warename||':'||v_wareno||','||v_colorname||','||v_sizename||'  条码:'||v_barcode0; ";

		// 填 入条码跟踪表
		qry += "\n     if '" + barcode + "'>v_barcode0  then";
		// noteid:0采购入库1采购退库2期初入库3调拨入库4零售出库5批发出库6经销退库7调拨出库8盘点9临时盘点10采购订单11客户订单12调拨订单13商场销售
		qry += "\n        insert into warebarrecord (id,accid,barcode,noteid,noteno,amount,lastdate)";
		qry += "\n        values (warebarrecord_id.nextval," + Accid + ",'" + barcode + "',13,'" + Noteno + "',v_amount,sysdate); ";
		qry += "\n     end if;";
		qry += "\n    commit; ";
		qry += "\n    insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n    values (LOGRECORD_id.nextval," + Accid + ",'商场开票','【扫码增加商品】单据号:" + Noteno + ",商品id:'||v_wareid||',颜色id:'||v_colorid||',尺码id:'||v_sizeid||',数量:'||v_amount,'" + Lastop + "');";
		qry += "\n    <<exit>> ";
		qry += "\n    select v_retcs,v_id,v_totalamt,v_totalcurr into :retcs,:id,:totalamt,:totalcurr from dual; ";
		qry += "\n end; ";

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		param.put("id", new ProdParam(Types.FLOAT));
		param.put("totalamt", new ProdParam(Types.FLOAT));
		param.put("totalcurr", new ProdParam(Types.FLOAT));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		if (retcs.substring(0, 1).equals("0")) {
			errmess = retcs.substring(1);
			return 0;
		}
		Id = Float.parseFloat(param.get("id").getParamvalue().toString());
		errmess = "\"msg\": \"" + retcs.substring(1) + "\""//
				+ ",\"TOTALAMT\": \"" + param.get("totalamt").getParamvalue().toString() + "\""//
				+ ",\"TOTALCURR\": \"" + param.get("totalcurr").getParamvalue().toString() + "\""//
		;
		qry = "select a.id,a.wareid,a.colorid,a.sizeid,a.salemanid,a.houseid,a.amount,a.price0,a.discount,a.price,a.curr,a.remark0";
		qry += ",b.warename,b.wareno,b.units,c.colorname,d.sizename,e.epname,f.housename";
		qry += " from shopsalem a ";
		qry += " left outer join warecode b on a.wareid=b.wareid";
		qry += " left outer join colorcode c on a.colorid=c.colorid";
		qry += " left outer join sizecode d on a.sizeid=d.sizeid";
		qry += " left outer join employe e on a.salemanid=e.epid";
		qry += " left outer join warehouse f on a.houseid=f.houseid";
		qry += "  where a.accid=" + Accid + " and a.noteno='" + Noteno + "' and a.id=" + Id;
		// WriteLogTXT("debug", "1", qry);
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		// responsestr += ",\"rows\":";
		if (tb.getRowCount() > 0) {
			errmess += ",\"rows\":{";
			String fh1 = "";
			for (int j = 0; j < tb.getColumnCount(); j++) {
				String strKey = tb.getRow(0).getColumn(j + 1).getName().toUpperCase();
				String strValue = tb.getRow(0).get(j).toString();
				errmess += fh1 + "\"" + strKey + "\":\"" + strValue + "\"";
				fh1 = ",";
			}
			errmess += "}";
		}

		return 1;
	}

	// 成批更改商场销售出库折扣
	public int ChangeDisc(long guestid, int priceprec, int vippricetype) {
		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		if (Discount == null) {
			errmess = "discount不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		//		qry += "   v_pricetype number(1);";
		qry += "   v_discount number;";
		qry += "   v_discount1 number;";
		qry += "   v_wareid number(10);";
		qry += "   v_wareid1 number(10);";
		qry += "   v_amount number; ";
		qry += "   v_price0 number; ";
		qry += "   v_price number; ";
		qry += "   v_price1 number; ";
		qry += "   v_houseid number; ";

		// qry += " v_noteno varchar2(20);";
		qry += "   v_count number(10); ";
		qry += "   v_totalcurr number; ";
		qry += "   v_totalamt number; ";
		qry += "   v_id number; ";
		qry += "begin ";
		// qry += " v_accid:=" + accid + ";";
		// qry += " v_noteno:='" + noteno + "';";
		qry += "   v_discount:=" + Discount + ";";
		if (guestid > 0) // 取会员折扣
		{
			// 取会员折扣
			// qry += "\n v_discount:=1;";
			qry += "\n   begin";
			qry += "\n     select b.discount into v_discount1 from guestvip a join guesttype b on a.vtid=b.vtid where a.guestid=" + guestid + ";";
			qry += "\n     if v_discount>v_discount1 then "; // 如果会员折扣比当前折扣小，就用会员折扣
			qry += "\n        v_discount:=v_discount1;";
			qry += "\n     end if;"; // 没有调价记录，单价=原价*折扣

			qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN  ";
			qry += "\n     v_discount1:=1;";
			qry += "\n   end;";

		}
		qry += "\n   v_discount1:=v_discount;";

		qry += "\n   declare cursor v_tempdata is   ";
		qry += "\n     select a.id,a.houseid,a.wareid ";
		if (guestid > 0 && vippricetype > 0) {//会员价方式:会员价方式:0=零售价，1=售价1，2=售价2，3=售价3
			qry += "\n   ,decode(" + vippricetype + ",1,b.sale1,2,b.sale2,3,b.sale3,b.retailsale)  as price0";
		} else
			qry += "\n   ,b.retailsale as price0";

		qry += "\n     from shopsalem a join warecode b on a.wareid=b.wareid ";
		qry += "\n     where a.accid=" + Accid + " and a.noteno='" + Noteno + "' and a.iszp=0";
		qry += "\n     and b.tjtag=0";
		qry += "\n     order by a.wareid;";
		qry += "\n   begin";
		qry += "\n      open v_tempdata;";
		qry += "\n      v_count:=0;";
		qry += "\n      fetch v_tempdata  into v_id,v_houseid,v_wareid,v_price0; ";
		qry += "\n      while v_tempdata%found loop  ";
		qry += "\n         v_wareid1:=v_wareid;";
		qry += "\n         while v_tempdata%found and v_wareid=v_wareid1 loop  ";
		qry += "\n            v_discount:=v_discount1;"; // 折扣恢复为原折扣
		qry += "\n            v_price:=round(v_price0*v_discount," + priceprec + ");"; // 新折扣价
		// 2.查找是否有调价记录
		qry += "\n            begin";
		qry += "\n     select nvl(price,v_price) into v_price1 from ( select b.retailsale as price";
		qry += "\n     from wareadjusth a join wareadjustm b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n     where a.statetag=2";
		qry += "\n     and a.begindate<to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')+1";
		qry += "\n     and a.enddate>=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') ";
		qry += "\n     and exists (select 1 from wareadjustc c where a.accid=c.accid and a.noteno=c.noteno and c.houseid=v_houseid)";
		qry += "\n     and b.wareid=v_wareid and a.accid=" + Accid + " order by a.notedate desc ) where rownum=1;";

		qry += "\n              if v_price>v_price1 then "; // 如果价格比会员价低
		qry += "\n                 v_price:=v_price1; v_discount:=0; "; // 查到有调价记录
		qry += "\n                 if v_price0>0 then";
		qry += "\n                    v_discount:=round(v_price/v_price0,2); "; // 有调价记录,折扣=单价/原价
		qry += "\n                 end if;";
		qry += "\n              end if ;";
		qry += "\n            EXCEPTION WHEN NO_DATA_FOUND THEN  ";
		qry += "\n              v_price:=round(v_price0*v_discount," + priceprec + ");"; // 没有调价记录，单价=原价*折扣
		qry += "\n            end;";
		qry += "\n            update shopsalem set price0=v_price0,discount=v_discount,price=v_price,curr=amount*v_price where id=v_id and accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n            v_count:=v_count+1;";
		qry += "\n            if v_count>1000 then ";
		qry += "\n               commit;";
		qry += "\n               v_count:=0;";
		qry += "\n            end if;";
		qry += "\n            fetch v_tempdata  into v_id,v_houseid,v_wareid,v_price0; ";
		qry += "\n         end loop;";
		qry += "\n      end loop;";
		qry += "\n      close v_tempdata;";
		qry += "\n   end;";

		qry += "\n    commit;";
		qry += "\n    select nvl(sum(curr),0) into v_totalcurr from shopsalem where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n    update shopsaleh set totalcurr=v_totalcurr where accid=" + Accid + " and noteno='" + Noteno + "';";

		qry += "\n    commit;";
		qry += "\n    select v_totalcurr into :totalcurr from dual;";

		qry += "\n end; ";
//		LogUtils.LogDebugWrite("Shopsalem.ChangeDisc", qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalcurr", new ProdParam(Types.FLOAT));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		errmess = param.get("totalcurr").getParamvalue().toString();
		return 1;

	}

	// 更改商场销售商品的赠送状态
	public int ChangeZp(long guestid, int priceprec) {
		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		if (Id == null || Id <= 0) {
			errmess = "id不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		// qry += " v_discount0 number;";
		qry += "\n    v_discount number;";
		qry += "\n    v_discount1 number;";
		qry += "\n    v_wareid number(10);";
		qry += "\n    v_amount number; ";
		qry += "\n    v_price0 number; ";
		qry += "\n    v_price number; ";
		qry += "\n    v_price1 number; ";
		qry += "\n    v_houseid number; ";

		// qry += " v_noteno varchar2(20);";
		qry += "\n    v_count number(10); ";
		qry += "\n    v_totalcurr number; ";
		qry += "\n    v_totalamt number; ";
		qry += "\n    v_id number; ";
		qry += "\n begin ";
		if (Iszp == 1) // 设为赠品
		{

			qry += "\n    update shopsalem set iszp=1,price=0,curr=0,discount=0 where accid=" + Accid + " and noteno='" + Noteno + "' and id=" + Id + ";";
		} else // 取消赠品
		{
			qry += "\n    v_discount:=1;";

			if (guestid > 0) // 取会员折扣
			{
				// 取会员折扣
				// qry += "\n v_discount:=1;";
				qry += "\n   begin";
				qry += "\n     select b.discount into v_discount1 from guestvip a join guesttype b on a.vtid=b.vtid where a.guestid=" + guestid + ";";
				qry += "\n     if v_discount>v_discount1 then "; // 如果会员折扣比当前折扣小，就用会员折扣
				qry += "\n        v_discount:=v_discount1;";
				qry += "\n     end if;"; // 没有调价记录，单价=原价*折扣

				qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN  ";
				qry += "\n     v_discount1:=1;";
				qry += "\n   end;";
			}
			qry += "\n   v_discount1:=v_discount;";
			qry += "\n   select wareid,price0 into v_wareid,v_price0 from shopsalem where accid=" + Accid + " and noteno='" + Noteno + "' and id=" + Id + ";";
			qry += "\n   v_price:=round(v_price0*v_discount," + priceprec + ");"; // 没有调价记录，单价=原价*折扣

			qry += "\n   begin ";
			qry += "\n     select nvl(price,v_price) into v_price1 from ( select b.retailsale as price";
			qry += "\n     from wareadjusth a join wareadjustm b on a.accid=b.accid and a.noteno=b.noteno";
			qry += "\n     where a.statetag=2";
			qry += "\n     and a.begindate<to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')+1";
			qry += "\n     and a.enddate>=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') ";
			qry += "\n     and exists (select 1 from wareadjustc c where a.accid=c.accid and a.noteno=c.noteno and c.houseid=v_houseid)";
			// if (houseid > 0) qry += "\n and exists (select 1 from wareadjustc c where a.accid=c.accid and a.noteno=c.noteno and c.houseid=" + houseid + ")";
			qry += "\n     and b.wareid=v_wareid and a.accid=" + Accid + " order by a.notedate desc ) where rownum=1;";

			qry += "\n     if v_price>v_price1 then "; // 如果价格比会员价低
			qry += "\n        v_price:=v_price1; v_discount:=0; "; // 查到有调价记录
			qry += "\n        if v_price0>0 then";
			qry += "\n           v_discount:=round(v_price/v_price0,2); "; // 有调价记录,折扣=单价/原价
			qry += "\n        end if;";
			qry += "\n     end if ;";
			qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN  ";
			qry += "\n     v_price:=round(v_price0*v_discount," + priceprec + ");"; // 没有调价记录，单价=原价*折扣
			qry += "\n   end;";
			qry += "\n   update shopsalem set iszp=0,discount=v_discount,price=v_price,curr=amount*v_price where id=" + Id + " and accid=" + Accid + " and noteno='" + Noteno + "';";

		}

		qry += "\n    commit;";
		qry += "\n    select nvl(sum(curr),0) into v_totalcurr from shopsalem where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n    update shopsaleh set totalcurr=v_totalcurr where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n    select v_totalcurr,price0,discount,price,curr into :totalcurr,:price0,:discount,:price,:curr from shopsalem where id=" + Id + " and accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n end; ";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalcurr", new ProdParam(Types.FLOAT));
		param.put("price0", new ProdParam(Types.FLOAT));
		param.put("discount", new ProdParam(Types.FLOAT));
		param.put("price", new ProdParam(Types.FLOAT));
		param.put("curr", new ProdParam(Types.FLOAT));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// errmess = param.get("totalcurr").getParamvalue().toString();
		errmess = "\"msg\":\"操作成功！\",\"TOTALCURR\":\"" + param.get("totalcurr").getParamvalue().toString() + "\""//
				+ ",\"rows\":{\"PRICE0\":\"" + param.get("price0").getParamvalue().toString() + "\""//
				+ ",\"DISCOUNT\":\"" + param.get("discount").getParamvalue().toString() + "\""//
				+ ",\"PRICE\":\"" + param.get("price").getParamvalue().toString() + "\""//
				+ ",\"CURR\":\"" + param.get("curr").getParamvalue().toString() + "\"}";

		return 1;
	}

	// 获取采购入库\退货商品+颜色汇总列表
	public int doGetColorsumList(QueryParam qp, int sizenum, int priceprec) {
		if (Noteno.equals("")) {
			errmess = "noteno不是一个有效的值！";
			return 0;
		}

		String qry = "select a.wareid,b.wareno,b.warename,b.units,b.sizegroupno,b.imagename0,b.gbbar,e.brandname,a.colorid,c.colorname";
		qry += "\n,b.retailsale,b.entersale ,min(a.id) as id,sum(a.amount) as amount";
		qry += "\n ,case sum(a.amount) when 0 then 0 else round(sum(a.amount*a.price0)/sum(a.amount)," + priceprec + ") end as price0";
		qry += "\n ,case sum(a.amount*a.price0) when 0 then 1 else round(sum(a.curr)/sum(a.amount*a.price0),2) end as discount";
		qry += "\n ,case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount)," + priceprec + ") end as price";
		qry += "\n ,sum(a.curr) as curr,min(remark0) as remark0,max(a.tjtag) as tjtag";
		qry += "\n from shopsalem a ";
		qry += "\n left outer join warecode b on a.wareid=b.wareid";
		qry += "\n left outer join colorcode c on a.colorid=c.colorid";
		// qry += "\n left outer join salecode d on a.saleid=d.saleid";
		qry += "\n left outer join brand e on b.brandid=e.brandid";
		qry += "\n where a.ACCID=" + Accid + " and a.NOTENO='" + Noteno + "'";
		qry += "\n group by a.wareid,b.wareno,b.warename,b.units,b.sizegroupno,b.imagename0,b.gbbar,e.brandname,a.colorid,c.colorname,b.retailsale,b.entersale";
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
					// jsonString.append(fh1 + "\"" + strKey + "\":\"" + strValue + "\"");
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
				// jsonString.append(fh1 + "\"" + strKey + "\":\"" + strValue + "\"");
				fh1 = ",";
			}

			// 取尺码数据
			qry1 = "select " + sqlsum + "," + sqlsize;
			qry1 += " from shopsalem a where accid=" + Accid + " and noteno='" + Noteno + "'";
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