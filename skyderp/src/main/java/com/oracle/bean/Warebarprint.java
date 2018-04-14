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
//import com.common.tool.SizeParam;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-04-07 14:50:49
//*************************************
public class Warebarprint implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private Long Epid;
	private Long Wareid;
	private Long Colorid;
	private Long Sizeid;
	private Float Amount;
	private Float Barsale;
	private String Remark;
	private Date Lastdate;
	private String errmess;
	private String Sizeidlist;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getEpid() {
		return Epid;
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

	public void setBarsale(Float barsale) {
		this.Barsale = barsale;
	}

	public Float getBarsale() {
		return Barsale;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public void setSizeidlist(String sizeidlist) {
		this.Sizeidlist = sizeidlist;
	}

	public String getRemark() {
		return Remark;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Wareid == 0 || Colorid == 0) {
			errmess = "wareid或colorid不是一个有效值！";
			return 0;
		}

		String qry = "declare ";
		qry += "   v_totalcurr number; ";
		qry += "   v_totalamt number; ";
		qry += "begin ";
		qry += "   delete from warebarprint where epid=" + Epid + " and wareid=" + Wareid + " and colorid=" + Colorid + "; ";
		qry += "   commit;";
		qry += " end;";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
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
		if (Epid != null) {
			strSql1.append("epid,");
			strSql2.append(Epid + ",");
		}
		if (Wareid != null) {
			strSql1.append("wareid,");
			strSql2.append(Wareid + ",");
		}
		if (Colorid != null) {
			strSql1.append("colorid,");
			strSql2.append(Colorid + ",");
		}
		// if (Prtok != null) {
		// strSql1.append("Prtok,");
		// strSql2.append(Prtok + ",");
		// }
		if (Sizeid != null) {
			strSql1.append("sizeid,");
			strSql2.append(Sizeid + ",");
		}
		if (Amount != null) {
			strSql1.append("amount,");
			strSql2.append("'" + Amount + "',");
		}
		if (Barsale != null) {
			strSql1.append("barsale,");
			strSql2.append("'" + Barsale + "',");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=Warebarprint_id.nextval; ");
		strSql.append("   insert into Warebarprint (");
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
		strSql.append("   update Warebarprint set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Epid != null) {
			strSql.append("epid=" + Epid + ",");
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
		if (Barsale != null) {
			strSql.append("barsale='" + Barsale + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
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
		strSql.append(" FROM Warebarprint ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM Warebarprint a");
		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
		strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append("\n left outer join brand f on b.brandid=f.brandid");
		strSql.append("\n left outer join waretype g on b.typeid=g.typeid");
		strSql.append("\n left outer join waretype h on g.p_typeid=h.typeid");
		strSql.append("\n left outer join area i on b.areaid=i.areaid");
		strSql.append("\n left outer join employe k on a.epid=k.epid");
		strSql.append("\n left outer join housesaleprice j on a.wareid=j.wareid and k.houseid=j.houseid");
		strSql.append("\n join warebarcode e on a.wareid=e.wareid and a.colorid=e.colorid and a.sizeid=e.sizeid");

		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		// LogUtils.LogErrWrite("GetWarebarprintList", strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String qry) {

		qp.setQueryString(qry);
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Warebarprint");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	public int doGetColorsumList(QueryParam qp, int sizenum) {
		String qry = "select a.wareid,b.wareno,b.warename,b.units,a.colorid,c.colorname,a.barsale";
		qry += ",case when e.retailsale is null then b.retailsale else e.retailsale end retailsale";
		qry += ",sum(a.amount) as amount,min(a.id) as id";
		qry += "\n from warebarprint a ";
		qry += "\n left outer join warecode b on a.wareid=b.wareid";
		qry += "\n left outer join colorcode c on a.colorid=c.colorid";
		qry += "\n left outer join employe d on a.epid=d.epid";
		qry += "\n left outer join housesaleprice e on a.wareid=e.wareid and d.houseid=e.houseid";
		qry += "\n where a.epid=" + Epid;
		qry += "\n group by a.wareid,b.wareno,b.warename,b.units,a.colorid,c.colorname,a.barsale";
		qry += ",case when e.retailsale is null then b.retailsale else e.retailsale end ";

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
			qry1 += " from warebarprint a where epid=" + Epid;
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

	// 获取商品条码打印的颜色尺码明细
	public int doSum(long houseid) {
		if (Wareid == 0) {
			errmess = "wareid不是一个有效值！";
			return 0;
		}
		// string qry = "select wareid,wareno,warename,entersale,retailsale,sale1,sale2,sale3,sale4,sale5,units,imagename from warecode where wareid=" + wareid;
		String qry = "select a.wareid,a.wareno,a.warename,a.units";
		if (houseid > 0)
			qry += ",case when b.retailsale is null then a.retailsale else b.retailsale end as retailsale";
		else
			qry += ",a.retailsale";
		qry += " from warecode a";
		if (houseid > 0)
			qry += " left outer join housesaleprice b on a.wareid=b.wareid and b.houseid=" + houseid;
		qry += " where a.wareid=" + Wareid;
		Table tb = new Table();

		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "未找到商品！";
			return 0;
		}
		String retcs = "\"msg\":\"操作成功\"";

		for (int j = 0; j < tb.getColumnCount(); j++) {//
			String strKey = tb.getRow(0).getColumn(j + 1).getName().toUpperCase();//
			String strValue = tb.getRow(0).get(j).toString();//
//			if (j > 0)
//				retcs += ",";
			retcs += ",\"" + strKey + "\":\"" + strValue + "\"";
		}
		// + ",\"IMAGENAME\":\"" + dt.Rows[0]["imagename"].ToString() + "\"";

		// 取颜色
		qry = "select a.colorid,b.colorname";
		qry += " FROM warecolor a";
		qry += " left outer join colorcode b on a.colorid=b.colorid";
		qry += " where a.wareid=" + Wareid + " and b.statetag=1 and b.noused=0 order by b.colorno";
		tb = DbHelperSQL.Query(qry).getTable(1);

		retcs += ",\"colorlist\":[";

		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				retcs += ",";
			retcs += "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"" //
					+ ",\"COLORNAME\":\"" + tb.getRow(i).get("COLORNAME").toString() + "\"}";

		}
		retcs += "]";

		// 取尺码
		qry = "select a.sizeid,a.sizename,a.sizeno  FROM sizecode a";
		qry += " left outer join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno";
		qry += " where a.statetag=1 and a.noused=0  and b.wareid=" + Wareid;
		qry += " and not exists (select 1 from warenosize a1 where b.wareid=a1.wareid and a.sizeid=a1.sizeid)";
		qry += " order by a.sizeno,a.sizename";
		// qry += " where a.statetag=1 and a.noused=0 and b.wareid=" + wareid + " order by a.sizeno,a.sizename";
		tb = DbHelperSQL.Query(qry).getTable(1);
		retcs += ",\"sizelist\":[";

		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				retcs += ",";
			retcs += "{\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\""//
					+ ",\"SIZENAME\":\"" + tb.getRow(i).get("SIZENAME").toString() + "\"}";

		}
		retcs += "]";
		// 取入库数

		qry = "  select colorid,sizeid,amount,barsale";
		qry += " from warebarprint a ";
		qry += " where epid=" + Epid + " and wareid=" + Wareid;
		qry += " order by colorid";
		tb = DbHelperSQL.Query(qry).getTable(1);
		retcs += ",\"amountlist\":[";

		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				retcs += ",";
			retcs += "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"" //
					+ ",\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"" //
					+ ",\"AMOUNT\":\"" + tb.getRow(i).get("AMOUNT").toString() + "\""//
					+ ",\"BARSALE\":\"" + tb.getRow(i).get("BARSALE").toString() + "\"}";

		}
		retcs += "]";
		errmess = retcs;
		return 1;
	}

	// 保存商条码打印的颜色尺码明细
	public int Write(JSONObject jsonObject) {
		if (Wareid == 0) {
			errmess = "wareid不是一个有效的值！";
			return 0;
		}
		if (!jsonObject.has("rows")) {
			errmess = "rows不是一个有效的值！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("rows");

		String qry = "declare ";
		// qry += " v_totalcurr number; ";
		// qry += " v_totalamt number; ";
		// qry += " v_retailsale number; ";
		qry += "\n    v_id  number;";
		qry += "\n begin ";
		qry += "\n     delete from warebarprint where epid=" + Epid + " and wareid=" + Wareid + "; ";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			Sizeid = Long.parseLong(jsonObject2.getString("sizeid"));
			Colorid = Long.parseLong(jsonObject2.getString("colorid"));
			Amount = Float.parseFloat(jsonObject2.getString("amount"));
			Barsale = Float.parseFloat(jsonObject2.getString("barsale"));
			qry += "\n    insert into warebarprint (id,epid,wareid,colorid,sizeid,amount,barsale,remark,lastdate)";
			qry += "\n    values (warebarprint_id.nextval," + Epid + "," + Wareid + " ," + Colorid + "," + Sizeid + "," + Amount + "," + Barsale + ",'',sysdate);";
			// qry += " end; ";
		}
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

	// 清除所有数据
	public int Clear() {

		String qry = "begin ";
		qry += "   delete from warebarprint where epid=" + Epid + "; ";
		qry += " end;";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 全选或清除商品调价单的店铺
	public int ChangePrice(int pricetype, int wscl, Float rate) {
		String qry = "declare ";
		qry += "\n  v_epid number(10); ";
		qry += "\n  v_pricetype number(1);  ";// --价格方式: 0=零售价 ，1=售价一 ，2=售价二 ，3=售价三 ，4=批发价,5=打包价
		qry += "\n  v_rate  number(12,2);  ";// --提价比例
		qry += "\n  v_wscl  number(1);     ";// --尾数处理方式 :0=4舍5入,1=尾数为8,2=尾数为6,3=尾数为0
		qry += "\n  v_wschar  varchar2(1);   ";// --尾数处理方式 :0=4舍5入,1=尾数为8,2=尾数为6,3=尾数为0
		qry += "\n  v_barsale  number(16,4);  ";// --尾数处理方式 :0=4舍5入,1=尾数为8,2=尾数为6,3=尾数为0
		qry += "\n  v_barsalestr  varchar2(20);  ";
		qry += "\n begin ";
		qry += "\n  v_epid:=" + Epid + "; ";
		qry += "\n  v_pricetype:=" + pricetype + "; ";
		qry += "\n  v_rate:=" + rate + "; ";
		qry += "\n  v_wscl:=" + wscl + "; ";
		qry += "\n  if v_wscl=1 then  ";
		qry += "\n    v_wschar:='8'; ";
		qry += "\n  elsif  v_wscl=2 then  ";
		qry += "\n    v_wschar:='6';  ";
		qry += "\n  elsif  v_wscl=3 then  ";
		qry += "\n    v_wschar:='0'; ";
		qry += "\n  else ";
		qry += "\n    v_wschar:=''; ";
		qry += "\n  end if; ";

		qry += "\n  declare cursor cur_tempdata is ";
		qry += "\n    select a.wareid, ";
		qry += "\n    case v_pricetype  ";
		qry += "\n      when 0 then b.retailsale when 1 then b.sale1 ";
		qry += "\n      when 2 then b.sale2 when 3 then b.sale3 ";
		qry += "\n      when 4 then b.sale4  when 5 then b.sale5 ";
		qry += "\n      else b.retailsale end as barsale   ";
		qry += "\n    ,count(*) as num ";
		qry += "\n    from warebarprint a join warecode b on a.wareid=b.wareid  ";
		qry += "\n    where a.epid=v_epid group by a.wareid, ";
		qry += "\n     case v_pricetype  ";
		qry += "\n      when 0 then b.retailsale when 1 then b.sale1 ";
		qry += "\n      when 2 then b.sale2 when 3 then b.sale3 ";
		qry += "\n      when 4 then b.sale4  when 5 then b.sale5 ";
		qry += "\n      else b.retailsale end  ; ";
		qry += "\n     v_row cur_tempdata%rowtype; ";
		qry += "\n  begin ";
		qry += "\n    for v_row in cur_tempdata loop ";
		qry += "\n      v_barsale:=round(v_row.barsale*v_rate,0); ";
		// --wscl尾数处理方式 :0=4舍5入,1=尾数为8,2=尾数为6,3=尾数为0
		qry += "\n      if v_wscl>=1 and v_wscl<=3 and v_barsale>10 then  ";
		qry += "\n         v_barsalestr:=trim(to_char(v_barsale)); ";
		qry += "\n         v_barsalestr:=substr(v_barsalestr,1,length(v_barsalestr)-1)||v_wschar; ";
		qry += "\n         v_barsale:=to_number(v_barsalestr); ";
		qry += "\n      end if; ";
		qry += "\n      update warebarprint set barsale=v_barsale where wareid=v_row.wareid and epid=v_epid; ";
		qry += "\n    end loop; ";
		qry += "\n  end; ";
		qry += "\n  commit;";
		qry += "\n end;";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 载入库存
	public int LoadKc(int loadbj) {
		String qry = "declare ";

		qry += "   v_notedate date; ";
		qry += "   v_totalcurr number; ";
		qry += "   v_totalamt number; ";
		qry += "begin ";
		qry += "  v_notedate:=sysdate;";
		if (loadbj == 0) // loadbj:0清空原记录 1追加新记录
		{
			// qry += " delete from warebarprint where epid=" + userid + ";";
			qry += "\n    declare  ";
			qry += "\n       cursor mycursor is ";
			qry += "         select ROWID from warebarprint where epid=" + Epid;
			qry += "\n       order by rowid; ";
			qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
			qry += "\n       v_rowid   rowid_table_type; ";
			qry += "\n    BEGIN ";
			qry += "\n       open mycursor; ";
			qry += "\n       loop ";
			qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
			qry += "\n          exit when v_rowid.count=0; ";
			qry += "\n          forall i in v_rowid.first..v_rowid.last ";
			qry += "\n          delete from warebarprint where rowid=v_rowid(i); ";
			qry += "\n          commit; ";
			qry += "\n        end loop; ";
			qry += "\n       close mycursor; ";
			qry += "\n    END; ";

			qry += "   insert into warebarprint (id,epid,wareid,colorid,sizeid,amount,barsale,remark)";
			qry += "   select warebarprint_id.nextval," + Epid + ",wareid,colorid,sizeid,amount,retailsale,'' from ( ";
			qry += "   select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount) as amount";
			qry += "   from v_cxkczy_data a left outer join warecode b on a.wareid=b.wareid";
			qry += "   where a.epid=" + Epid;
			// if (houseid > 0)
			// qry += " and a.houseid=" + houseid;
			qry += "   group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0 ); ";
		} else {

			qry += "   insert into warebarprint (id,epid,wareid,colorid,sizeid,amount,barsale,remark)";
			qry += "   select warebarprint_id.nextval," + Epid + ",wareid,colorid,sizeid,amount,retailsale,'' from ( ";
			qry += "   select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount) as amount";
			qry += "   from v_cxkczy_data a left outer join warecode b on a.wareid=b.wareid";
			qry += "   where a.epid=" + Epid;
			qry += "   and not exists (select 1 from warebarprint c where c.epid=" + Epid;
			qry += "   and a.wareid=c.wareid and a.colorid=c.colorid and a.sizeid=c.sizeid)  ";
			// if (houseid > 0)
			// qry += " and a.houseid=" + houseid;
			qry += "   group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0 ); ";

		}
		qry += " end;";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 商品条码打印载入指定单据
	public int LoadNote(long houseid, String noteno, String notetype, int loadbj) {
		// Notetype:CG=采购入库，QC=期初入库，LP=临时盘点，DC=调出，DR=调入，XS=批发，XT=批发退货，KO=客户订单，CO=采购订单

		if (!"^CG^QC^LP^DC^DR^XT^XS^KO^CO^".contains("^" + notetype + "^")) {
			// if (!notetype.equals("CG") && notetype.ToUpper() != "QC" && notetype.ToUpper() != "LP" && notetype.ToUpper() != "DC" && notetype.ToUpper() != "DR") {
			errmess = "notetype参数无效！";
			return 0;
		}
		if (noteno.length() <= 0) {
			errmess = "noteno参数无效！";
			return 0;
		}

		String qry = "declare ";

		// qry += "\n v_notedate date; ";
		qry += "\n   v_id number; ";
		qry += "\n   v_count number; ";
		qry += "\n begin ";
		if (loadbj == 0) // loadbj:0清空原记录 1追加新记录
		{
			qry += "\n   delete from warebarprint where epid=" + Epid + ";";
		}
		qry += "\n   declare cursor cur_data is";
		if (notetype.equals("CG")) // 采购入库
		{
			qry += "\n   select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount)  as amount,min(a.id) as id";
			qry += "\n   from wareinm a left outer join warecode b on a.wareid=b.wareid";
			qry += "\n   where a.accid=" + Accid + " and a.noteno='" + noteno + "'";
			qry += "\n   group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0 order by id; ";
		} else if (notetype.equals("QC")) // 期初入库
		{
			qry += "\n   select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount) as amount,min(a.id) as id ";
			qry += "\n   from firsthousem a left outer join warecode b on a.wareid=b.wareid";
			qry += "\n   where a.accid=" + Accid + " and a.noteno='" + noteno + "'";
			qry += "\n   group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0  order by id;";
		} else if (notetype.equals("LP")) // 临时盘点
		{
			qry += "\n   select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount) as amount,min(a.id) as id ";
			qry += "\n   from tempcheckm a left outer join warecode b on a.wareid=b.wareid";
			qry += "\n   where a.accid=" + Accid + " and a.noteno='" + noteno + "'";
			qry += "\n   group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0 order by id; ";
		} else if (notetype.equals("DC")) // 调拨出库
		{
			qry += "\n   select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount) as amount,min(a.id) as id ";
			qry += "\n   from allotoutm a left outer join warecode b on a.wareid=b.wareid";
			qry += "\n   where a.accid=" + Accid + " and a.noteno='" + noteno + "'";
			qry += "\n   group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0  order by id;";
		} else if (notetype.equals("DR")) // 调拨入库
		{
			qry += "\n   select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount) as amount,min(a.id) as id ";
			qry += "\n   from allotinm a left outer join warecode b on a.wareid=b.wareid";
			qry += "\n   where a.accid=" + Accid + " and a.noteno='" + noteno + "'";
			qry += "\n   group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0  order by id;";
		} else if (notetype.equals("XT") || notetype.equals("XS")) // 批发及 批发退货
		{
			qry += "\n   select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount)  as amount,min(a.id) as id";
			qry += "\n   from wareoutm a left outer join warecode b on a.wareid=b.wareid";
			qry += "\n   where a.accid=" + Accid + " and a.noteno='" + noteno + "'";
			qry += "\n   group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0 order by id; ";
		} else if (notetype.equals("KO")) // 客户订单
		{
			qry += "\n   select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount)  as amount,min(a.id) as id";
			qry += "\n   from custorderm a left outer join warecode b on a.wareid=b.wareid";
			qry += "\n   where a.accid=" + Accid + " and a.noteno='" + noteno + "'";
			qry += "\n   group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0 order by id; ";
		} else if (notetype.equals("CO")) // 采购订单
		{
			qry += "\n   select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount)  as amount,min(a.id) as id";
			qry += "\n   from provorderm a left outer join warecode b on a.wareid=b.wareid";
			qry += "\n   where a.accid=" + Accid + " and a.noteno='" + noteno + "'";
			qry += "\n   group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0 order by id; ";
		}
		// qry += ");";
		// } else // 追加载入
		// {
		// qry += "\n declare cursor cur_data is";
		// if (notetype.equals("CG")) // 采购入库
		// {
		// qry += "\n select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount) as amount,min(a.id) as id ";
		// qry += "\n from wareinm a left outer join warecode b on a.wareid=b.wareid ";
		// qry += "\n where a.accid=" + Accid + " and a.noteno='" + noteno + "'";
		// qry += "\n group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0 order by id; ";
		// } else if (notetype.equals("QC")) // 期初入库
		// {
		// qry += "\n select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount) as amount ,min(a.id) as id";
		// qry += "\n from firsthousem a left outer join warecode b on a.wareid=b.wareid ";
		// qry += "\n where a.accid=" + Accid + " and a.noteno='" + noteno + "'";
		// qry += "\n group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0 order by id; ";
		// } else if (notetype.equals("LP")) // 期初入库
		// {
		// qry += "\n select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount) as amount ,min(a.id) as id";
		// qry += "\n from tempcheckm a left outer join warecode b on a.wareid=b.wareid ";
		// qry += "\n where a.accid=" + Accid + " and a.noteno='" + noteno + "'";
		// qry += "\n group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0 order by id; ";
		// } else if (notetype.equals("DC")) // 调拨出库
		// {
		// qry += "\n select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount) as amount,min(a.id) as id ";
		// qry += "\n from allotoutm a left outer join warecode b on a.wareid=b.wareid ";
		// qry += "\n where a.accid=" + Accid + " and a.noteno='" + noteno + "'";
		// qry += "\n group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0 order by id; ";
		// } else if (notetype.equals("DR")) // 调拨入库
		// {
		// qry += "\n select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount) as amount,min(a.id) as id ";
		// qry += "\n from allotinm a left outer join warecode b on a.wareid=b.wareid ";
		// qry += "\n where a.accid=" + Accid + " and a.noteno='" + noteno + "'";
		// qry += "\n group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0 order by id; ";
		// } else if (notetype.equals("XT")) // 批发退货
		// {
		// qry += "\n select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount) as amount,min(a.id) as id ";
		// qry += "\n from wareoutm a left outer join warecode b on a.wareid=b.wareid ";
		// qry += "\n where a.accid=" + Accid + " and a.noteno='" + noteno + "'";
		// qry += "\n group by a.wareid,a.colorid,a.sizeid,b.retailsale having sum(a.amount)>0 order by id; ";
		// }
		// }

		qry += "\n     v_row cur_data%rowtype;  ";
		qry += "\n  begin ";
		qry += "\n    v_count:=0;";
		qry += "\n    for v_row in cur_data loop  ";
		qry += "\n       begin ";
		qry += "\n         select id into v_id from warebarprint where epid=" + Epid + " and wareid=v_row.wareid and colorid=v_row.colorid  and sizeid=v_row.sizeid and rownum=1; ";
		qry += "\n         update warebarprint set amount=amount+v_row.amount where id=v_id; ";
		qry += "\n       EXCEPTION WHEN NO_DATA_FOUND THEN  ";
		qry += "\n         insert into warebarprint (id,epid,wareid,colorid,sizeid,amount,barsale,remark) ";
		qry += "\n         values (warebarprint_id.nextval," + Epid + ",v_row.wareid,v_row.colorid,v_row.sizeid,v_row.amount,v_row.retailsale,''); ";
		qry += "\n       end; ";
		qry += "\n       v_count:=v_count+1;";
		qry += "\n       if v_count>1000 then ";
		qry += "\n          commit;";
		qry += "\n          v_count:=0;";
		qry += "\n       end if;";
		qry += "\n    end loop; ";
		qry += "\n  end;";

		qry += "\n end;";
		// System.out.println(qry);

		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}
}