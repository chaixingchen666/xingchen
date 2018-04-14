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
import com.flyang.main.pFunc;
//import com.common.tool.SizeParam;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-27 22:28:25
//*************************************
public class Warecheckm implements java.io.Serializable {
	private Float Id;
	private String Noteno;
	private Long Accid;
	private Long Wareid;
	private Long Userid;
	private Long Colorid;
	private Long Sizeid;
	private Float Bookamt;
	private Float Checkamt;
	private Float Amount;
	private Float Price;
	private Float Curr;
	private String Remark0;
	private Date Lastdate;
	private String errmess;
	private String Lastop;
	private String Calcdate = ""; // 账套开始使用日期

	public void setCalcdate(String calcdate) {
		Calcdate = calcdate;
	}

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public Float getId() {
		return Id;
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

	public Long getAccid() {
		return Accid;
	}

	public void setWareid(Long wareid) {
		this.Wareid = wareid;
	}

	public void setUserid(Long userid) {
		this.Userid = userid;
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

	public void setBookamt(Float bookamt) {
		this.Bookamt = bookamt;
	}

	public Float getBookamt() {
		return Bookamt;
	}

	public void setCheckamt(Float checkamt) {
		this.Checkamt = checkamt;
	}

	public Float getCheckamt() {
		return Checkamt;
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

	public void setRemark0(String remark0) {
		this.Remark0 = remark0;
	}

	public String getRemark0() {
		return Remark0;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Wareid == 0 || Colorid == 0 || Noteno == "") {
			errmess = "wareid或colorid或noteno不是一个有效值！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();

		strSql.append("declare ");
		strSql.append("\n   v_totalcurr number; ");
		strSql.append("\n   v_totalamt number; ");
		strSql.append("\n begin ");
		strSql.append("\n   delete from warecheckm where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + " and colorid=" + Colorid + "; ");
		strSql.append("\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from warecheckm where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n   update warecheckh set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno='" + Noteno + "';");
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
		if (Bookamt != null) {
			strSql1.append("bookamt,");
			strSql2.append(Bookamt + ",");
		}
		if (Checkamt != null) {
			strSql1.append("checkamt,");
			strSql2.append(Checkamt + ",");
		}
		if (Amount != null) {
			strSql1.append("amount,");
			strSql2.append("'" + Amount + "',");
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
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=Warecheckm_id.nextval; ");
		strSql.append("   insert into Warecheckm (");
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
		strSql.append("   update Warecheckm set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Noteno != null) {
			strSql.append("noteno='" + Noteno + "',");
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
		if (Bookamt != null) {
			strSql.append("bookamt=" + Bookamt + ",");
		}
		if (Checkamt != null) {
			strSql.append("checkamt=" + Checkamt + ",");
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
		if (Remark0 != null) {
			strSql.append("remark0='" + Remark0 + "',");
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
		strSql.append(" FROM Warecheckm ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Warecheckm ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Warecheckm");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 扫码
	public int doBarcodein(String barcode, int qxbj) {
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
		// qry += " v_discount number; "; // --折扣
		// qry += " v_pricetype number; "; // --价格方式 0=进价1=零价
		qry += "\n    v_houseid number(10); "; //
		qry += "\n    v_amount  number; "; // --个数
		qry += "\n    v_wareid number(10); "; //
		qry += "\n    v_colorid number(10); "; //
		qry += "\n    v_sizeid number(10); "; //
		qry += "\n    v_barcode0  varchar2(30); "; // --条码
		qry += "\n    v_id  number(20); ";
		qry += "\n    v_id0  number(20); ";
		qry += "\n    v_price number;";
		qry += "\n    v_count number; ";
		qry += "\n    v_curr number; ";
		qry += "\n    v_wareno varchar2(40); ";
		qry += "\n    v_warename varchar2(80); ";
		qry += "\n    v_colorname varchar2(40); ";
		qry += "\n    v_sizename varchar2(40); ";
		qry += "\n    v_retcs varchar2(200); ";
		qry += "\n    v_totalcurr number; ";
		qry += "\n    v_totalamt number; ";
		qry += "\n    v_bookamt number; ";
		qry += "\n    v_checkamt number; ";
		qry += "\n    v_notedate date;";
		qry += "\n begin ";
		qry += "\n   select notedate,houseid into v_notedate,v_houseid from warecheckh where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n    v_amount:=" + Amount + "; ";
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

		// =======================

		qry += "\n   begin";
		qry += "\n     select a.wareid,a.colorid,a.sizeid,b.retailsale,b.wareno,b.warename,c.colorname,d.sizename  ";//
		qry += "\n     into v_wareid,v_colorid,v_sizeid,v_price ,v_wareno,v_warename,v_colorname,v_sizename  ";
		qry += "\n     from warebarcode a  ";
		qry += "\n     left outer join warecode b on a.wareid=b.wareid ";
		qry += "\n     left outer join colorcode c on a.colorid=c.colorid ";
		qry += "\n     left outer join sizecode d on a.sizeid=d.sizeid ";
		qry += "\n     where a.id=v_id and b.sizegroupno=d.groupno ";//
		if (qxbj == 1) // 1 启用权限控制
			qry += "\n and (b.brandid=0 or exists (select 1 from employebrand x where b.brandid=x.brandid and x.epid=" + Userid + "))";
		qry += "     ;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n      v_retcs:= '0 商品未授权或尺码异常！' ;  ";
		qry += "\n      goto exit;";
		qry += "\n   end;";

		qry += "\n     select count(*) into v_count from warecheckm where accid=" + Accid + " and noteno='" + Noteno + "' ";//
		qry += "\n       and wareid=v_wareid and colorid=v_colorid and sizeid=v_sizeid ; ";//
		qry += "\n     if v_count=0 then ";//
		// qry += " v_price:=TRUNC(v_price0*v_discount+0.5); ";//
		// 取账面数
		qry += "\n        v_bookamt:=f_getwaresumskux(to_char(v_notedate,'yyyy-mm-dd hh24:mi:ss')," + Accid + ",v_wareid,v_colorid,v_sizeid,v_houseid,'" + Calcdate + "');";
		qry += "\n        v_curr:=v_price*(v_amount-v_bookamt); ";//
		qry += "\n        insert into warecheckm (id,accid,noteno,wareid,colorid,sizeid,bookamt,checkamt,amount,price,curr,remark0)";
		qry += "\n        values (warecheckm_id.nextval," + Accid + ",'" + Noteno + "',v_wareid,v_colorid,v_sizeid,v_bookamt,v_amount,v_amount-v_bookamt,v_price,v_curr,'');";
		qry += "\n     else  ";//
		qry += "\n        select id into v_id from warecheckm where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=v_wareid and colorid=v_colorid and sizeid=v_sizeid";
		qry += "\n        and rownum=1 order by id ;";
		qry += "\n        update warecheckm set checkamt=checkamt+v_amount,amount=amount+v_amount, curr=(amount+v_amount)*price where id=v_id and noteno='" + Noteno + "' and accid=" + Accid + ";";
		qry += "\n     end if; ";
		// ===============================
		// 将商品，颜色未输入的尺码自动填为0，表示数量为0
		qry += "\n     v_checkamt:=0; ";
		qry += "\n     declare cursor cur_data is";
		qry += "\n       select a.wareid,b.colorid,c.sizeid,a.retailsale from warecode a";
		qry += "\n       join warecolor b on a.wareid=b.wareid";
		qry += "\n       join colorcode d on b.colorid=d.colorid";
		qry += "\n       join sizecode c on a.accid=c.accid and a.sizegroupno=c.groupno ";
		qry += "\n       where c.statetag=1 and c.noused=0 and d.statetag=1 and d.noused=0 and a.accid=" + Accid;
		qry += "\n       and a.wareid=v_wareid";
		qry += "\n       and not exists (select 1 from warecheckm e where a.wareid=e.wareid and b.colorid=e.colorid and c.sizeid=e.sizeid";
		qry += "\n       and e.accid=" + Accid + " and e.noteno='" + Noteno + "')";

		qry += "\n       and exists (select 1 from warecheckm e where a.wareid=e.wareid and b.colorid=e.colorid";
		qry += "\n       and e.accid=" + Accid + " and e.noteno='" + Noteno + "');";

		qry += "\n        v_row cur_data%rowtype;";
		qry += "\n     begin";
		qry += "\n        for v_row in cur_data loop";
		qry += "\n            v_bookamt:=f_getwaresumskux(to_char(v_notedate,'yyyy-mm-dd hh24:mi:ss')," + Accid + ",v_row.wareid,v_row.colorid,v_row.sizeid,v_houseid,'" + Calcdate + "');";
		qry += "\n            v_curr:=(v_checkamt-v_bookamt)*v_row.retailsale;";
		qry += "\n            insert into warecheckm (id,accid,noteno,wareid,colorid,sizeid,bookamt,checkamt,amount,price,curr,remark0,lastdate)";
		qry += "\n            values (warecheckm_id.nextval," + Accid + ",'" + Noteno + "',v_row.wareid,v_row.colorid,v_row.sizeid,v_bookamt,v_checkamt,v_checkamt-v_bookamt,v_row.retailsale,v_curr,'',sysdate);";
		qry += "\n        end loop;";
		qry += "\n     end;";
		// ===============================

		// qry += " update warecheckh set totalcurr=(select nvl(sum(curr),0) from warecheckm where warecheckh.accid=warecheckm.accid and warecheckh.noteno=warecheckm.noteno) ";
		// qry += " ,totalamt=(select nvl(sum(amount),0) from warecheckm where warecheckh.accid=warecheckm.accid and warecheckh.noteno=warecheckm.noteno ) ";
		qry += "\n     select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from warecheckm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n     update warecheckh set totalcurr=v_totalcurr,totalamt=v_totalamt  where accid=" + Accid + " and noteno='" + Noteno + "'; ";

		qry += "\n    v_retcs:= '1 '||v_warename||':'||v_wareno||','||v_colorname||','||v_sizename||',￥'||v_price||'  条码:'||v_barcode0 ; ";
		// 填 入条码跟踪表
		qry += "\n     if '" + barcode + "'>v_barcode0  then";
		// noteid:0采购入库1采购退库2期初入库3调拨入库4零售出库5批发出库6经销退库7调拨出库8盘点9临时盘点10采购订单11客户订单12调拨订单
		qry += "\n        insert into warebarrecord (id,accid,barcode,noteid,noteno,amount,lastdate)";
		qry += "\n        values (warebarrecord_id.nextval," + Accid + ",'" + barcode + "',8,'" + Noteno + "',v_amount,sysdate); ";
		qry += "\n     end if;";

		qry += "\n     commit; ";
		qry += "\n     <<exit>> ";
		qry += "\n     select v_retcs into :retcs from dual; ";
		qry += "\n end;    ";
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
		if (Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
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
		qry += "\n    v_checkamt number; ";
		qry += "\n    v_bookamt number; ";
		qry += "\n    v_curr number; ";
		qry += "\n    v_id number;";
		qry += "\n    v_notedate  date;";
		qry += "\n    v_houseid  number(10);";
		qry += "\n begin ";
		qry += "\n    select notedate,houseid into v_notedate,v_houseid from warecheckh where accid=" + Accid + " and noteno='" + Noteno + "';";
		// qry += "\n update wareinm set amount=0 where accid=" + accid + " and noteno='" + noteno + "' and wareid=" + wareid + "; ";
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
				qry += "\n  begin ";
				qry += "\n    select id into v_id from warecheckm where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" //
						+ Wareid + " and colorid=" + Colorid + " and sizeid=" + Sizeid + " and rownum=1; ";
				qry += "\n    update warecheckm set checkamt=checkamt+" + Amount + " where accid=" + Accid + " and noteno='" + Noteno + "' and id=v_id;";
				qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN"; // 未找到，插入新记录
				qry += "\n    v_id:=warecheckm_id.nextval;";
				qry += "\n    v_bookamt:=f_getwaresumskux(to_char(v_notedate,'yyyy-mm-dd hh24:mi:ss')," + Accid + "," + Wareid + "," + Colorid + "," + Sizeid + ",v_houseid,'" + Calcdate + "');";
				qry += "\n    v_curr:=round((" + Amount + "-v_bookamt)*" + Price + ",2);";
				qry += "\n    insert into warecheckm (id,accid,noteno,wareid,colorid,sizeid,bookamt,checkamt,amount,price,curr,remark0)";
				qry += "\n    values (v_id," + Accid + ",'" + Noteno + "'," + Wareid + "," + Colorid + "," + Sizeid + ",v_bookamt," + Amount + "," + Amount + "-v_bookamt," + Price + ",v_curr,'');";
				qry += "\n  end; ";
				qry += "\n  update warecheckm set amount=checkamt-bookamt,curr=(checkamt-bookamt)*price where accid=" + Accid + " and noteno='" + Noteno + "' and id=v_id;";
			}
		}
		// ===============================
		// 将商品，颜色未输入的尺码自动填为0，表示数量为0
		qry += "\n     v_checkamt:=0; ";
		qry += "\n     declare cursor cur_data is";
		qry += "\n       select a.wareid,b.colorid,c.sizeid,a.retailsale from warecode a";
		qry += "\n       join warecolor b on a.wareid=b.wareid";
		qry += "\n       join colorcode d on b.colorid=d.colorid";
		qry += "\n       join sizecode c on a.accid=c.accid and a.sizegroupno=c.groupno ";
		qry += "\n       where c.statetag=1 and c.noused=0 and d.statetag=1 and d.noused=0 and a.accid=" + Accid;
		qry += "\n       and a.wareid=" + Wareid;
		qry += "\n       and not exists (select 1 from warecheckm e where a.wareid=e.wareid and b.colorid=e.colorid and c.sizeid=e.sizeid";
		qry += "\n       and e.accid=" + Accid + " and e.noteno='" + Noteno + "')";

		qry += "\n       and exists (select 1 from warecheckm e where a.wareid=e.wareid and b.colorid=e.colorid";
		qry += "\n       and e.accid=" + Accid + " and e.noteno='" + Noteno + "');";

		qry += "\n        v_row cur_data%rowtype;";
		qry += "\n     begin";
		qry += "\n        for v_row in cur_data loop";
		qry += "\n            v_bookamt:=f_getwaresumskux(to_char(v_notedate,'yyyy-mm-dd hh24:mi:ss')," + Accid + ",v_row.wareid,v_row.colorid,v_row.sizeid,v_houseid,'" + Calcdate + "');";
		qry += "\n            v_curr:=(v_checkamt-v_bookamt)*v_row.retailsale;";
		qry += "\n            insert into warecheckm (id,accid,noteno,wareid,colorid,sizeid,bookamt,checkamt,amount,price,curr,remark0,lastdate)";
		qry += "\n            values (warecheckm_id.nextval," + Accid + ",'" + Noteno + "',v_row.wareid,v_row.colorid,v_row.sizeid,v_bookamt,v_checkamt,v_checkamt-v_bookamt,v_row.retailsale,v_curr,'',sysdate);";
		qry += "\n        end loop;";
		qry += "\n     end;";
		// ===============================

		qry += "\n    select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from warecheckm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n    update warecheckh set totalcurr=v_totalcurr,totalamt=v_totalamt";
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

	// 成批增加数据
	public int Appendbat(JSONObject jsonObject, int priceprec) {
		Wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		Noteno = jsonObject.has("noteno") ? jsonObject.getString("noteno") : null;
		Price = jsonObject.has("price") ? Float.parseFloat(jsonObject.getString("price")) : 0;
		// long houseid = jsonObject.has("houseid") ? Long.parseLong(jsonObject.getString("houseid")) : 0;
		// int statetag = jsonObject.has("statetag") ? Integer.parseInt(jsonObject.getString("statetag")) : 0;
		// String pricestr = jsonObject.has("pricestr") ? jsonObject.getString("pricestr") : null;
		String remark = jsonObject.has("remark") ? jsonObject.getString("remark") : null;
		String handno = jsonObject.has("handno") ? jsonObject.getString("handno") : null;

		if (Func.isNull(Noteno)) {
			errmess = "noteno不是一个有效值!";
			return 0;
		}

		if (Wareid == null || Wareid == 0) {
			errmess = "wareid不是一个有效值!";
			return 0;
		}
		if (!pFunc.isWarecodeOk(Accid, Wareid)) {
			errmess = "wareid不是一个有效值2!";
			return 0;
		}

		// if (houseid == 0) {
		// errmess = "houseid不是一个有效值!";
		// return 0;
		// }
		if (!jsonObject.has("colorsizelist")) {
			errmess = "colorsizelist未传入！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("colorsizelist");

		String qry = "declare ";
		qry += "\n    v_totalcurr  number;";
		qry += "\n    v_totalamt  number;";
		qry += "\n    v_checkamt number; ";
		qry += "\n    v_bookamt number; ";
		qry += "\n    v_curr number; ";
		qry += "\n    v_id  number;";
		qry += "\n    v_noteno  varchar2(20);";
		qry += "\n    v_retcs  varchar2(100);";
		qry += "\n    v_notedate  date;";
		qry += "\n    v_houseid  number(10);";
		qry += "\n begin ";
		qry += "\n    select notedate,houseid into v_notedate,v_houseid from warecheckh where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n    update warecheckm set checkamt=0 where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + ";";
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
			// Curr =Func.getRound(Amount * Price,2);

			qry += "\n begin";
			qry += "\n   select id into v_id from warecheckm where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + " and colorid=" + Colorid + " and sizeid=" + Sizeid + " ; ";
			qry += "\n   update warecheckm set checkamt=checkamt+" + Amount + ",amount=amount+" + Amount + ",curr=(amount+" + Amount + ")*price where id=v_id;";
			qry += "\n EXCEPTION WHEN NO_DATA_FOUND THEN";
			qry += "\n   v_bookamt:=f_getwaresumskux(to_char(v_notedate,'yyyy-mm-dd hh24:mi:ss')," + Accid + "," + Wareid + "," + Colorid + "," + Sizeid + ",v_houseid,'" + Calcdate + "' );";
			qry += "\n   v_curr:=round((" + Amount + "-v_bookamt)*" + Price + ",2);";
			qry += "\n   insert into  warecheckm (id,accid,noteno,wareid,colorid,sizeid,bookamt,checkamt,amount,price,curr,remark0)";
			qry += "\n   values (warecheckm_id.nextval," + Accid + ",'" + Noteno + "'," + Wareid + "," + Colorid + "," + Sizeid + ",v_bookamt," + Amount + "," + Amount + "-v_bookamt," + Price + ",v_curr,'');";
			qry += "\n end; ";
		}
		//		qry += "\n  delete warecheckm where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + " and amount=0;";

		// if (houseid > 0) {
		// 将商品，颜色未输入的尺码自动填为0，表示数量为0
		qry += "\n     v_checkamt:=0; ";
		qry += "\n     declare cursor cur_data is";
		qry += "\n       select a.wareid,b.colorid,c.sizeid,a.retailsale from warecode a";
		qry += "\n       join warecolor b on a.wareid=b.wareid";
		qry += "\n       join colorcode d on b.colorid=d.colorid";
		qry += "\n       join sizecode c on a.accid=c.accid and a.sizegroupno=c.groupno ";
		qry += "\n       where c.statetag=1 and c.noused=0 and d.statetag=1 and d.noused=0 and a.accid=" + Accid;
		qry += "\n       and a.wareid=" + Wareid;
		qry += "\n       and not exists (select 1 from warecheckm e where a.wareid=e.wareid and b.colorid=e.colorid and c.sizeid=e.sizeid";
		qry += "\n       and e.accid=" + Accid + " and e.noteno='" + Noteno + "')";

		qry += "\n       and exists (select 1 from warecheckm e where a.wareid=e.wareid and b.colorid=e.colorid";
		qry += "\n       and e.accid=" + Accid + " and e.noteno='" + Noteno + "');";

		qry += "\n        v_row cur_data%rowtype;";
		qry += "\n     begin";
		qry += "\n        for v_row in cur_data loop";
		qry += "\n            v_bookamt:=f_getwaresumskux(to_char(v_notedate,'yyyy-mm-dd hh24:mi:ss')," + Accid + ",v_row.wareid,v_row.colorid,v_row.sizeid,v_houseid,'" + Calcdate + "' );";
		qry += "\n            v_curr:=(v_checkamt-v_bookamt)*v_row.retailsale;";
		qry += "\n            insert into warecheckm (id,accid,noteno,wareid,colorid,sizeid,bookamt,checkamt,amount,price,curr,remark0,lastdate)";
		qry += "\n            values (warecheckm_id.nextval," + Accid + ",'" + Noteno + "',v_row.wareid,v_row.colorid,v_row.sizeid,v_bookamt,v_checkamt,v_checkamt-v_bookamt,v_row.retailsale,v_curr,'',sysdate);";
		qry += "\n        end loop;";
		qry += "\n     end;";

		// }
		qry += "\n  select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from warecheckm where accid=" + Accid + " and noteno='" + Noteno + "';";

		qry += "\n  update warecheckh set totalcurr=v_totalcurr,totalamt=v_totalamt,lastdate=sysdate ";
		if (remark != null)
			qry += ",remark='" + remark + "'";
		if (handno != null)
			qry += ",handno='" + handno + "'";
		// if (statetag == 1)
		// qry += "\n ,statetag=1,notedate=sysdate";

		qry += "\n     where accid=" + Accid + " and noteno='" + Noteno + "';";
		// if (statetag == 1) {
		// qry += "\n update tempcheckh set statetag=1,notedate=sysdate,lastdate=sysdate";
		// qry += "\n where accid=" + Accid + " and noteno='" + Noteno + "';";
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

	public int Write(JSONObject jsonObject, long houseid) {
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
		qry += "\n    v_id  number; ";
		qry += "\n    v_totalcurr  number; ";
		qry += "\n    v_totalamt  number; ";
		qry += "\n    v_retailsale number(16,4); ";
		qry += "\n    v_bookamt number(16,4); ";
		qry += "\n    v_checkamt number(16,4); ";
		qry += "\n    v_notedate  date; ";
		qry += "\n  begin ";
		qry += "\n   select notedate into v_notedate from warecheckh where accid=" + Accid + " and noteno='" + Noteno + "';";
		// qry += "\n delete from warecheckm where accid=" + accid + " and noteno='" + noteno + "' and wareid=" + wareid + "; ";
		qry += "\n   select retailsale into v_retailsale from warecode where wareid=" + Wareid + "; ";
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
			// Price = Float.parseFloat(jsonObject2.getString("price"));
			// Curr = Float.parseFloat(jsonObject2.getString("curr"));
			// qry += "\n insert into tempcheckm (id,accid,noteno,wareid,colorid,sizeid,amount,price,curr,remark0,lastdate)";
			// qry += "\n values (tempcheckm_id.nextval," + Accid + ",'" + Noteno + "'," + Wareid + " ," + Colorid + "," + Sizeid + "," + Amount + "," + Price + "," + Curr + ",'',sysdate);";
			qry += "\n    v_bookamt:=f_getwaresumskux(to_char(v_notedate,'yyyy-mm-dd hh24:mi:ss')," + Accid + "," + Wareid + "," + Colorid + "," + Sizeid + "," + houseid + ",'" + Calcdate + "');";

			qry += "\n    v_checkamt:=" + Amount + "; ";
			qry += "\n    begin ";
			qry += "\n      select id into v_id from warecheckm where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid + " and colorid=" + Colorid + " and sizeid=" + Sizeid + ";";
			qry += "\n      update warecheckm set checkamt=v_checkamt,bookamt=v_bookamt,amount=v_checkamt-v_bookamt,curr=(v_checkamt-v_bookamt)*v_retailsale" //
					+ ",price=v_retailsale,lastdate=sysdate where id=v_id and accid=" + Accid + ";";
			qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN ";
			qry += "\n      v_id:=warecheckm_id.nextval;";
			qry += "\n      insert into warecheckm (id,accid,noteno,wareid,colorid,sizeid,bookamt,checkamt,amount,price,curr,remark0,lastdate)";
			qry += "\n      values (v_id," + Accid + ",'" + Noteno + "'," + Wareid + " ," + Colorid + "," + Sizeid//
					+ ",v_bookamt,v_checkamt,v_checkamt-v_bookamt,v_retailsale,(v_checkamt-v_bookamt)*v_retailsale,'',sysdate);";
			qry += "\n    end;";
		}
		// 将商品，颜色未输入的尺码自动填为0，表示数量为0
		qry += "\n     v_checkamt:=0; ";
		qry += "\n     declare cursor cur_data is";
		qry += "\n       select a.wareid,b.colorid,c.sizeid,a.retailsale from warecode a";
		qry += "\n       join warecolor b on a.wareid=b.wareid";
		qry += "\n       join colorcode d on b.colorid=d.colorid";
		qry += "\n       join sizecode c on a.accid=c.accid and a.sizegroupno=c.groupno ";
		qry += "\n       where c.statetag=1 and c.noused=0 and d.statetag=1 and d.noused=0 and a.accid=" + Accid;
		qry += "\n       and not exists (select 1 from warecheckm e where a.wareid=e.wareid and b.colorid=e.colorid and c.sizeid=e.sizeid";
		qry += "\n       and e.accid=" + Accid + " and e.noteno='" + Noteno + "')";

		qry += "\n       and exists (select 1 from warecheckm e where a.wareid=e.wareid and b.colorid=e.colorid";
		qry += "\n       and e.accid=" + Accid + " and e.noteno='" + Noteno + "');";

		qry += "\n        v_row cur_data%rowtype;";
		qry += "\n     begin";
		qry += "\n        for v_row in cur_data loop";
		// qry += "\n v_bookamt:=f_getwareamountx(to_char(sysdate,'yyyy-mm-dd')," + accid + ",v_row.wareid,v_row.colorid,v_row.sizeid ," + houseid + ",'"+Calcdate+"'); ";
		qry += "\n            v_bookamt:=f_getwaresumskux(to_char(v_notedate,'yyyy-mm-dd hh24:mi:ss')," + Accid + ",v_row.wareid,v_row.colorid,v_row.sizeid," + houseid + ",'" + Calcdate + "');";
		qry += "\n            insert into warecheckm (id,accid,noteno,wareid,colorid,sizeid,bookamt,checkamt,amount,price,curr,remark0,lastdate)";
		qry += "\n            values (warecheckm_id.nextval," + Accid + ",'" + Noteno
				+ "',v_row.wareid,v_row.colorid,v_row.sizeid,v_bookamt,v_checkamt,v_checkamt-v_bookamt,v_row.retailsale,(v_checkamt-v_bookamt)*v_row.retailsale,'',sysdate);";
		qry += "\n        end loop;";
		qry += "\n     end;";

		qry += "\n     select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from warecheckm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n     update warecheckh set totalcurr=v_totalcurr,totalamt=v_totalamt ";
		qry += "\n     where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n     commit; ";
		qry += "\n end; ";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 获取商品盘点商品+颜色汇总列表
	public int doGetColorsumList(QueryParam qp, int sizenum, int priceprec) {
		if (Noteno.equals("")) {
			errmess = "noteno不是一个有效的值！";
			return 0;
		}

		String qry = "select a.wareid,b.wareno,b.warename,b.units,b.imagename0,b.retailsale,b.sizegroupno,e.brandname,a.colorid,c.colorname,min(id) as id,sum(a.amount) as amount";
		qry += "\n ,sum(a.checkamt) as checkamt,sum(a.bookamt) as bookamt";
		// qry += "\n ,case sum(a.amount) when 0 then 0 else round(sum(a.amount*a.price0)/sum(a.amount)," + priceprec + ") end as price0";
		// qry += "\n ,case sum(a.amount*a.price0) when 0 then 1 else round(sum(a.curr)/sum(a.amount*a.price0),2) end as discount";
		qry += "\n ,case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount)," + priceprec + ") end as price";

		qry += "\n ,sum(a.curr) as curr,sum(a.amount*b.retailsale) as retailcurr";
		qry += "\n from warecheckm a ";
		qry += "\n left outer join warecode b on a.wareid=b.wareid";
		qry += "\n left outer join colorcode c on a.colorid=c.colorid";
		qry += "\n left outer join brand e on b.brandid=e.brandid";
		qry += "\n where a.ACCID=" + Accid + " and a.NOTENO='" + Noteno + "'";
		qry += "\n group by a.wareid,b.wareno,b.warename,b.units,b.imagename0,b.retailsale,b.sizegroupno,e.brandname,a.colorid,c.colorname";
		Table tb = new Table();
		qp.setQueryString(qry);
		qp.setSumString("nvl(sum(amount),0) as totalamt,nvl(sum(checkamt),0) as totalcheckamt,nvl(sum(bookamt),0) as totalbookamt,nvl(sum(curr),0) as totalcurr,nvl(sum(retailcurr),0) as retailcurr");
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
		//System.out.println("doGetWareinmcolorsumList 1.6");
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
				SizeParam sp = new SizeParam(Wareid, sizenum, 1);
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
			qry1 += " from warecheckm a where accid=" + Accid + " and noteno='" + Noteno + "'";
			qry1 += " and wareid=" + Wareid + " and colorid=" + Colorid;

			Table tb1 = DbHelperSQL.Query(qry1).getTable(1);
			for (int j = 1; j <= sizenum; j++) {
				retcs += ",\"BOOKAMT" + j + "\":\"" + tb1.getRow(0).get("BOOKAMT" + j).toString() + "\"" //
						+ ",\"CHECKAMT" + j + "\":\"" + tb1.getRow(0).get("CHECKAMT" + j).toString() + "\"" //
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

	public int Load(long houseid, int priceprec, String wareno) {
		if (Noteno.equals("")) {
			errmess = "wareid或noteno不是一个有效的值！";
			return 0;
		}

		Table tb = new Table();
		String qry = "";
		if (Wareid != null && Wareid > 0)
			qry = "select wareid,wareno,warename,retailsale,units,imagename0 from warecode where accid=" + Accid + " and statetag=1 and wareid=" + Wareid;
		else if (wareno.length() > 0) {
			qry = "select wareid,wareno,warename,retailsale,units,imagename0 from warecode where accid=" + Accid + " and wareno='" + wareno + "' and statetag=1 and rownum=1";
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
		Price = Float.parseFloat(tb.getRow(0).get("RETAILSALE").toString());
		// Discount = (float) 1;
		String retcs = "\"msg\":\"操作成功\"";
		// "\"WAREID\":\"" + dt.Rows[0]["wareid"].ToString() + "\""
		// + ",\"WARENO\":\"" + dt.Rows[0]["wareno"].ToString() + "\""
		// + ",\"WARENAME\":\"" + dt.Rows[0]["warename"].ToString() + "\""
		// + ",\"UNITS\":\"" + dt.Rows[0]["units"].ToString() + "\""
		// + ",\"ENTERSALE\":\"" + dt.Rows[0]["entersale"].ToString() + "\""
		// + ",\"RETAILSALE\":\"" + dt.Rows[0]["retailsale"].ToString() + "\""
		// + ",\"IMAGENAME0\":\"" + dt.Rows[0]["imagename0"].ToString() + "\"" ;
		String fh = "";
		for (int j = 0; j < tb.getColumnCount(); j++) {
			String strKey = tb.getRow(0).getColumn(j + 1).getName().toUpperCase();
			String strValue = tb.getRow(0).get(j).toString();
			retcs += ",\"" + strKey + "\":\"" + strValue + "\"";
			// jsonString.append(fh1 + "\"" + strKey + "\":\"" + strValue + "\"");
//			fh = ",";
		}

		// 取颜色
		qry = "select a.colorid,b.colorname";
		qry += "\n  FROM warecolor a";
		qry += "\n  left outer join colorcode b on a.colorid=b.colorid";
		qry += "\n  where a.wareid=" + Wareid + " and b.statetag=1 and b.noused=0";

		qry += "\n  and (a.noused=0 or a.noused=1 and exists (select 1 from warecheckm c ";
		qry += "\n  where c.accid=" + Accid + " and c.noteno='" + Noteno + "' and a.wareid=c.wareid and a.colorid=c.colorid) )";

		qry += "\n  order by b.colorno";
		tb = DbHelperSQL.Query(qry).getTable(1);
		fh = "";
		rs = tb.getRowCount();
		// {"colorlist":[{"COLORID":"1","COLORNAME":"红色"},{"COLORID":"2","COLORNAME":"黄色"}]
		// ,"sizelist":[{"SIZEID":"1","SIZENAME":"XS","SIZENO":"01"},{"SIZEID":"2","SIZENAME":"S","SIZENO":"02"},{"SIZEID":"3","SIZENAME":"M","SIZENO":"03"},{"SIZEID":"4","SIZENAME":"L","SIZENO":"04"},{"SIZEID":"5","SIZENAME":"XL","SIZENO":"05"},{"SIZEID":"6","SIZENAME":"XXL","SIZENO":"06"},{"SIZEID":"7","SIZENAME":"3XL","SIZENO":"07"}]}

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
		qry += "\n  where a.statetag=1 and a.noused=0 and a.accid=" + Accid + " and b.wareid=" + Wareid;
		qry += "\n  and not exists (select 1 from warenosize a1 where b.wareid=a1.wareid and a.sizeid=a1.sizeid)";
		qry += "\n  order by a.sizeno,a.sizename";

		tb = DbHelperSQL.Query(qry).getTable(1);
		fh = "";
		rs = tb.getRowCount();
		// {"colorlist":[{"COLORID":"1","COLORNAME":"红色"},{"COLORID":"2","COLORNAME":"黄色"}]
		// ,"sizelist":[{"SIZEID":"1","SIZENAME":"XS","SIZENO":"01"},{"SIZEID":"2","SIZENAME":"S","SIZENO":"02"},{"SIZEID":"3","SIZENAME":"M","SIZENO":"03"},{"SIZEID":"4","SIZENAME":"L","SIZENO":"04"},{"SIZEID":"5","SIZENAME":"XL","SIZENO":"05"},{"SIZEID":"6","SIZENAME":"XXL","SIZENO":"06"},{"SIZEID":"7","SIZENAME":"3XL","SIZENO":"07"}]}

		retcs += ",\"sizelist\":[";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"" //
					+ ",\"SIZENAME\":\"" + tb.getRow(i).get("SIZENAME").toString() + "\"}";
			fh = ",";

		}
		retcs += "]";
		// 取入库数

		qry = "  select colorid,sizeid,checkamt";
		qry += "\n  from warecheckm a ";
		qry += "\n  where accid=" + Accid + " and noteno='" + Noteno + "' and wareid=" + Wareid;
		qry += "\n  order by colorid";
		tb = DbHelperSQL.Query(qry).getTable(1);
		fh = "";
		rs = tb.getRowCount();
		retcs += ",\"amountlist\":[";
		Float totalcurr = (float) 0;
		Float totalamt = (float) 0;
		Amount = (float) 0;
		// Curr = (float) 0;
		// Price = (float) 0;
		for (int i = 0; i < rs; i++) {
			Amount = Float.parseFloat(tb.getRow(i).get("CHECKAMT").toString());
			// Curr =Func.getRound(Amount * Price,2);// Float.parseFloat(tb.getRow(i).get("CURR").toString());
			// Price0 = Float.parseFloat(tb.getRow(i).get("PRICE0").toString());
			retcs += fh + "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"" //
					+ ",\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"" //
					+ ",\"CHECKAMT\":\"" + Amount + "\"}";//
			// + ",\"PRICE0\":\"" + Price0 + "\""//
			// + ",\"DISCOUNT\":\"" + tb.getRow(i).get("DISCOUNT").toString() + "\"" //
			// + ",\"PRICE\":\"" + tb.getRow(i).get("PRICE").toString() + "\"" //
			// + ",\"CURR\":\"" + Curr + "\"}";
			fh = ",";
			// totalcurr += Curr;
			totalamt += Amount;
		}
		retcs += "]";
		if (houseid > 0) // 如果是采购退货，返回库存
		{
			vTotalCxkczy dal = new vTotalCxkczy();
			dal.setUserid(Userid);
			dal.setHouseid(houseid);
			dal.setWareid(Wareid);
			dal.setAccid(Accid);
			dal.setCalcdate(Calcdate);
			dal.doCxkczy();
			// myTotalCxkczy("", userid, accid, houseid, wareid, 0, 0, -1, -1, -1, -1, "", "", "", "", "", "", 0, 0);
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
				retcs += fh + "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"," //
						+ "\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"," //
						+ "\"AMOUNT\":\"" + tb.getRow(i).get("AMOUNT").toString() + "\"}";
				fh = ",";

			}
			retcs += "]";
		}

		// if (totalamt != 0)
		// Price = Func.getRound(totalcurr / totalamt, priceprec);
		totalcurr = Func.getRound(Price * totalamt, 2);
		// // model.price = (long)(model.price0 * model.discount + (Float)0.5)
		// if (Price0 != 0)
		// Discount = Func.getRound(Price / Price0, 2);

		retcs += ",\"ZAMOUNT\":\"" + totalamt + "\""//
		// + ",\"ZPRICE0\":\"" + Price0 + "\"" //
		// + ",\"ZDISCOUNT\":\"" + Discount + "\"" //
				+ ",\"ZPRICE\":\"" + Price + "\"" //
				+ ",\"ZCURR\":\"" + totalcurr + "\"";
		errmess = retcs;

		return 1;
	}

}