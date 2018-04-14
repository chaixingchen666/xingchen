/**
 * 
 */
package com.oracle.bean;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.common.tool.Func;
import com.flyang.main.pFunc;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;

/**
 * @author Administrator
 *
 */
public class vRemovesubmit {

	private Float Id;
	private String Tablename;
	private String Noteno;

	private Long Accid;
	private String Lastop;
	private String Qxpublic;
	private String errmess;

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	public Float getId() {
		return Id;
	}

	public void setId(Float id) {
		Id = id;
	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	public String getTablename() {
		return Tablename;
	}

	public void setTablename(String tablename) {
		Tablename = tablename;
	}

	public String getNoteno() {
		return Noteno;
	}

	public void setNoteno(String noteno) {
		Noteno = noteno;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setLastop(String lastop) {
		Lastop = lastop;
	}

	public String getQxpublic() {
		return Qxpublic;
	}

	public void setQxpublic(String qxpublic) {
		Qxpublic = qxpublic;
	}

	public int Run() {
		// qxpublic = string.IsNullOrEmpty(qxpublic) ? "0000000000000000000000000000000000000000" : qxpublic;
		// * 000 XXXXXXXXXXXXXXXXXXXXXXXXXXX
		// * 33 1=允许更改单据日期
		// * |||__3 启用最近售价
		// * ||___2 允许负数出库
		// * |____1 启用权限控制
		int changedatebj = 0;
		if (Qxpublic.substring(33 - 1, 1) == "1")
			changedatebj = 1;
		if (Tablename == null || Tablename.equals("")) {
			errmess = "参数无效:Tablename！";
			return 0;
		}
		if (Noteno == null || Noteno.equals("")) {
			errmess = "参数无效:Noteno！";
			return 0;
		}
		if (Id == null || Id == 0) {
			errmess = "参数无效:id！";
			return 0;
		}
		Noteno = Noteno.toUpperCase();
		Tablename = Tablename.toUpperCase();
		if (Tablename.compareTo("REFUNDASKH") == 0)
			Tablename = "REFUNDOUTH";

		String progname = Tablename;
		String qry = " ";

		qry = " declare";
		qry += "\n   v_ntid number(1); ";
		qry += "\n   v_notedate varchar2(10); ";
		qry += "\n   v_orderno varchar2(20); ";
		qry += "\n   v_Noteno1 varchar2(20); ";
		qry += "\n   v_progname varchar2(40); ";
		qry += "\n   v_retcs varchar2(200); ";
		qry += "\n   v_guestid number(10); ";
		qry += "\n   v_cleartag number(1);";
		qry += "\n   v_cent number(10); ";
		qry += "\n   v_paycurrv number(12,2); ";
		qry += "\n   v_count number(10); ";
		qry += "\n   v_payNoteno_zfb varchar2(30);v_payNoteno_wx varchar2(30);v_payNoteno_lkl varchar2(30);";
		qry += "\n begin ";
		qry += "\n  v_progname:='';";

		qry += "\n   select count(*) into v_count from  " + Tablename + " where Accid=" + Accid + " and id=" + Id;
		if (Noteno.compareTo("***") != 0)
			qry += " and Noteno='" + Noteno + "'";
		qry += "\n   and statetag>=1; ";
		qry += "\n   if v_count=0 then  ";
		qry += "\n      v_retcs:= '0当前单据已撤单！'; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if;";

		qry += "\n   v_progname:='" + Tablename + "'; ";

		if (Tablename.compareTo("WAREOUTH") == 0) {
			qry += "\n   select to_char(notedate,'yyyy-mm-dd'),ntid,guestid,cent,orderno,cleartag,payNoteno_zfb,payNoteno_wx,payNoteno_lkl";
			qry += "\n   into v_notedate,v_ntid,v_guestid,v_cent,v_orderno,v_cleartag,v_payNoteno_zfb,v_payNoteno_wx,v_payNoteno_lkl from " + Tablename + " where Accid=" + Accid + " and id=" + Id + " and Noteno='"
					+ Noteno + "'; ";
			if (changedatebj == 0) // 不允许更改单据日期
			{
				qry += "\n   if v_notedate<to_char(sysdate,'yyyy-mm-dd') then ";
				qry += "\n      v_retcs:=  '0今日之前的单据不允许撤单！'; ";
				qry += "\n      goto exit; ";
				qry += "\n   end if; ";
			}
			qry += "\n   if v_cleartag=1  then ";
			qry += "\n      v_retcs:=  '0当前单据已被冲单，不允许撤单！' ; ";
			qry += "\n      goto exit; ";
			qry += "\n   elsif v_cleartag=2  then ";
			qry += "\n      v_retcs:=  '0当前单据是系统生成的冲账单据，不允许撤单！' ; ";
			qry += "\n      goto exit; ";
			qry += "\n   end if; ";
			qry += "\n   if length(v_payNoteno_zfb)>0 or length(v_payNoteno_wx)>0 or length(v_payNoteno_lkl)>0 then ";
			qry += "\n      v_retcs:=  '0当前单据有第三方支付记录，不允许撤单！'; ";
			qry += "\n      goto exit; ";
			qry += "\n   end if;";
			// qry += "\n select ntid,guestid,cent,orderno into v_ntid,v_guestid,v_cent,v_orderno from wareouth where Accid=" + Accid + " and Noteno='" + Noteno + "' and id=" + Id + "; ";
			qry += "\n  if v_ntid=0 then "; // 零售
			qry += "\n     v_progname:='店铺零售'; ";
			qry += "\n     begin";
			qry += "\n       select a.curr into v_paycurrv from waresalepay a join payway b on a.payid=b.payid where a.Accid=" + Accid + " and a.Noteno='" + Noteno + "' and b.payno='V';";
			qry += "\n     EXCEPTION WHEN NO_DATA_FOUND THEN ";
			qry += "\n       v_paycurrv:=0;";
			qry += "\n     end;";
			qry += "\n     delete from waresalepay where Accid=" + Accid + " and Noteno='" + Noteno + "'; ";
			qry += "\n     if v_guestid>0 and ( v_cent<>0 or v_paycurrv>0) then";
			qry += "\n        delete from guestcent where guestid=v_guestid and xsNoteno='" + Noteno + "'; "; // 删除消费积分
			qry += "\n        delete from guestcurr where guestid=v_guestid and xsNoteno='" + Noteno + "'; "; // 删除消费积分
			qry += "\n        update guestvip set balcent=nvl((select sum(case fs when 0 then cent else -cent end) from guestcent where guestvip.guestid=guestcent.guestid),0）";
			qry += "\n          ,balcurr=nvl((select sum(case fs when 0 then curr else -curr end) from guestcurr where guestvip.guestid=guestcurr.guestid),0）";
			qry += "\n        where guestid=v_guestid; ";
			qry += "\n     end if; ";

			qry += "\n  elsif v_ntid=1 then "; // 批发出库
			qry += "\n     v_progname:='批发开票'; ";
			qry += "\n     delete from incomecurr where Accid=" + Accid + " and yNoteno='" + Noteno + "'; ";// and payid>0; ";
			qry += "\n  elsif v_ntid=2 then "; // 批发退库要把申请单退单标志清除
			qry += "\n     v_progname:='批发退货'; ";
			qry += "\n     delete from incomecurr where Accid=" + Accid + " and yNoteno='" + Noteno + "'; ";// and payid>0; ";
			// qry += "\n if length(v_orderno)>0 then ";
			// qry += "\n update refundouth set thtag=0 where Accid=" + Accid + " and Noteno=v_orderno; ";// and payid>0; ";
			// qry += "\n end if; ";
			qry += "\n  end if; ";
			qry += "\n  update " + Tablename + " set statetag=0,cent=0,checkman='',paylist='',lastdate=sysdate where Accid=" + Accid + " and id=" + Id + " and Noteno='" + Noteno + "'; ";
		} else if (Tablename.compareTo("SHOPSALEH") == 0) {
			qry += "\n   select to_char(notedate,'yyyy-mm-dd'),ntid,guestid,cent,payNoteno_zfb,payNoteno_wx,payNoteno_lkl into v_notedate,v_ntid,v_guestid,v_cent,v_payNoteno_zfb,v_payNoteno_wx,v_payNoteno_lkl from "
					+ Tablename + " where Accid=" + Accid + " and id=" + Id + " and Noteno='" + Noteno + "'; ";
			if (changedatebj == 0) // 不允许更改单据日期
			{
				qry += "\n   if v_notedate<to_char(sysdate,'yyyy-mm-dd') then ";
				qry += "\n      v_retcs:=  '0今日之前的单据不允许撤单！'; ";
				qry += "\n      goto exit; ";
				qry += "\n   end if; ";
			}
			qry += "\n   if length(v_payNoteno_zfb)>0 or length(v_payNoteno_wx)>0 or length(v_payNoteno_lkl)>0 then ";
			qry += "\n      v_retcs:=  '0当前单据有第三方支付记录，不允许撤单！' ; ";
			qry += "\n      goto exit; ";
			qry += "\n   end if;";

			qry += "\n     v_progname:='商场零售'; ";
			qry += "\n     begin";
			qry += "\n       select a.curr into v_paycurrv from shopsalepay a join payway b on a.payid=b.payid where a.Accid=" + Accid + " and a.Noteno='" + Noteno + "' and b.payno='V';";
			qry += "\n     EXCEPTION WHEN NO_DATA_FOUND THEN ";
			qry += "\n       v_paycurrv:=0;";
			qry += "\n     end;";
			qry += "\n     delete from shopsalepay where Accid=" + Accid + " and Noteno='" + Noteno + "'; ";
			qry += "\n     if v_guestid>0 and ( v_cent<>0 or v_paycurrv>0) then";
			qry += "\n        delete from guestcent where guestid=v_guestid and xsNoteno='" + Noteno + "'; "; // 删除消费积分
			qry += "\n        delete from guestcurr where guestid=v_guestid and xsNoteno='" + Noteno + "'; "; // 删除消费积分
			qry += "\n        update guestvip set balcent=nvl((select sum(case fs when 0 then cent else -cent end) from guestcent where guestvip.guestid=guestcent.guestid),0）";
			qry += "\n          ,balcurr=nvl((select sum(case fs when 0 then curr else -curr end) from guestcurr where guestvip.guestid=guestcurr.guestid),0）";
			qry += "\n        where guestid=v_guestid; ";
			qry += "\n     end if; ";

			qry += "\n  update " + Tablename + " set statetag=0,cent=0,checkman='',paylist='' where Accid=" + Accid + " and id=" + Id + " and Noteno='" + Noteno + "'; ";
		} else if (Tablename.compareTo("WAREINH") == 0) {
			qry += "\n   select to_char(notedate,'yyyy-mm-dd'),ntid,cleartag into v_notedate,v_ntid,v_cleartag from " + Tablename + " where Accid=" + Accid + " and id=" + Id + " and Noteno='" + Noteno + "'; ";
			if (changedatebj == 0) // 不允许更改单据日期
			{
				qry += "\n   if v_notedate<to_char(sysdate,'yyyy-mm-dd') then ";
				qry += "\n      v_retcs:=  '0今日之前的单据不允许撤单！'; ";
				qry += "\n      goto exit; ";
				qry += "\n   end if; ";
			}
			qry += "\n   if v_cleartag=1  then ";
			qry += "\n      v_retcs:=  '0当前单据已被冲单，不允许撤单！'; ";
			qry += "\n      goto exit; ";
			qry += "\n   elsif v_cleartag=2  then ";
			qry += "\n      v_retcs:=  '0当前单据是系统生成的冲账单据，不允许撤单！'; ";
			qry += "\n      goto exit; ";
			qry += "\n   end if; ";

			// qry += "\n select ntid into v_ntid from wareinh where Accid=" + Accid + " and Noteno='" + Noteno + "' and id=" + Id + "; ";
			qry += "\n  v_progname:='采购退货'; ";
			qry += "\n  if v_ntid=0 then "; // 采购入库
			qry += "\n     v_progname:='采购入库'; ";
			qry += "\n     delete from paycurr where Accid=" + Accid + " and yNoteno='" + Noteno + "'; ";// and payid>0; ";
			qry += "\n  end if; ";
			qry += "\n  update " + Tablename + " set statetag=0,paylist='' where Accid=" + Accid + " and id=" + Id + " and Noteno='" + Noteno + "' ; ";
		} else if (Tablename.compareTo("ALLOTOUTH") == 0) // 如果调拨出库已有调拨入库单，不允许撤单
		{
			qry += "\n   select to_char(notedate,'yyyy-mm-dd'),ntid,cleartag,Noteno1 into v_notedate,v_ntid,v_cleartag,v_Noteno1 from " + Tablename + " where Accid=" + Accid + " and id=" + Id + " and Noteno='" + Noteno
					+ "'; ";
			if (changedatebj == 0) // 不允许更改单据日期
			{
				qry += "\n   if v_notedate<to_char(sysdate,'yyyy-mm-dd') then ";
				qry += "\n      v_retcs:=  '0今日之前的单据不允许撤单！'; ";
				qry += "\n      goto exit; ";
				qry += "\n   end if; ";
			}
			qry += "\n   if v_cleartag=1  then ";
			qry += "\n      v_retcs:=  '0当前单据已被冲单，不允许撤单！' ; ";
			qry += "\n      goto exit; ";
			qry += "\n   elsif v_cleartag=2  then ";
			qry += "\n      v_retcs:=  '0当前单据是系统生成的冲账单据，不允许撤单！' ; ";
			qry += "\n      goto exit; ";
			qry += "\n   end if; ";

			// qry += "\n select count(*) into v_count from allotinh where Accid=" + Accid + " and Noteno1='" + Noteno + "' and rownum=1; ";
			// qry += "\n if v_count>0 then ";
			qry += "\n  if length(v_Noteno1)>0 then ";
			qry += "\n      v_retcs:=  '0当前调出单已有调入单记录，不允许撤单！' ; ";
			qry += "\n      goto exit; ";
			qry += "\n   end if; ";
			qry += "\n   v_progname:='调拨出库'; ";

			qry += "\n  update " + Tablename + " set statetag=0 where Accid=" + Accid + " and id=" + Id + " and Noteno='" + Noteno + "' ; ";
		} else if (Tablename.compareTo("ALLOTINH") == 0) {
			qry += "\n   v_progname:='调拨入库'; ";
			if (changedatebj == 0) // 不允许更改单据日期
			{
				qry += "\n   select to_char(notedate,'yyyy-mm-dd') into v_notedate from " + Tablename + " where Accid=" + Accid + " and id=" + Id + " and Noteno='" + Noteno + "'; ";
				qry += "\n   if v_notedate<to_char(sysdate,'yyyy-mm-dd') then ";
				qry += "\n      v_retcs:=  '0今日之前的单据不允许撤单！' ; ";
				qry += "\n      goto exit; ";
				qry += "\n   end if; ";
			}
			qry += "\n  update " + Tablename + " set statetag=0 where Accid=" + Accid + " and id=" + Id + " and Noteno='" + Noteno + "'; ";
			qry += "\n  select Noteno1 into v_Noteno1 from allotinh where Accid=" + Accid + " and id=" + Id + " and Noteno='" + Noteno + "'; ";
			qry += "\n  if length(v_Noteno1)>0 then ";
			qry += "\n    update allotouth set Noteno1='' where Accid=" + Accid + " and Noteno=v_Noteno1; ";
			qry += "\n  end if; ";
		} else

		if (Tablename.compareTo("REFUNDOUTH") == 0) // 如果退货申请单被退货单载入，不允许撤单
		{
			if (changedatebj == 0) // 不允许更改单据日期
			{
				qry += "\n   select to_char(notedate,'yyyy-mm-dd') into v_notedate from " + Tablename + " where Accid=" + Accid + " and id=" + Id + " and Noteno='" + Noteno + "'; ";
				qry += "\n   if v_notedate<to_char(sysdate,'yyyy-mm-dd') then ";
				qry += "\n      v_retcs:=  '0今日之前的单据不允许撤单！' ; ";
				qry += "\n      goto exit; ";
				qry += "\n   end if; ";
			}
			qry += "\n   v_progname:='退货申请'; ";
			qry += "\n   select count(*) into v_count from wareouth where Accid=" + Accid + " and ntid=2 and orderno='" + Noteno + "'; ";
			qry += "\n   if v_count>0 then ";
			qry += "\n      v_retcs:=  '0当前退货申请转入退货单，不允许撤单！' ; ";
			qry += "\n      goto exit; ";
			qry += "\n   end if; ";

			qry += "\n  update " + Tablename + " set statetag=0 where Accid=" + Accid + " and id=" + Id + " and Noteno='" + Noteno + "' ; ";
		}

		else

		if (Tablename.compareTo("WAREPEIH") == 0) // 如果配货单被批发单载入，不允许撤单
		{
			if (changedatebj == 0) // 不允许更改单据日期
			{
				qry += "\n   select to_char(notedate,'yyyy-mm-dd') into v_notedate from " + Tablename + " where Accid=" + Accid + " and id=" + Id + " and Noteno='" + Noteno + "'; ";
				qry += "\n   if v_notedate<to_char(sysdate,'yyyy-mm-dd') then ";
				qry += "\n      v_retcs:=  '0今日之前的单据不允许撤单！' ; ";
				qry += "\n      goto exit; ";
				qry += "\n   end if; ";
			}
			qry += "\n   v_progname:='商品配货'; ";
			qry += "\n   select count(*) into v_count from wareouth where Accid=" + Accid + " and ntid=1 and orderno='" + Noteno + "'; ";
			qry += "\n   if v_count>0 then ";
			qry += "\n      v_retcs:=  '0当前配货已生成批发出库单，不允许撤单！'; ";
			qry += "\n      goto exit; ";
			qry += "\n   end if; ";

			qry += "\n  update " + Tablename + " set statetag=0 where Accid=" + Accid + " and id=" + Id + " and Noteno='" + Noteno + "' ; ";
		} else {
			switch (Tablename) {
			// case "WAREPEIH":
			// qry += "\n v_progname:='配货单'; ";
			// break;
			case "WAREADJUSTH":
				qry += "\n   v_progname:='商品调价单'; ";
				break;
			case "CUSTORDERH":
				qry += "\n   v_progname:='客户订单'; ";
				break;
			case "PROVORDERH":
				qry += "\n   v_progname:='采购订单'; ";
				break;
			case "ALLOTORDERH":
				qry += "\n   v_progname:='调拨订单'; ";
				break;
			case "WARECHECKH":
				qry += "\n   v_progname:='库存盘点单'; ";
				break;
			case "TEMPCHECKH":
				qry += "\n   v_progname:='临时盘点单'; ";
				break;
			case "FIRSTHOUSEH":
				qry += "\n   v_progname:='期初入库单'; ";
				break;
			case "FIRSTPAYCURR":
				qry += "\n   v_progname:='期初应付款'; ";
				break;
			case "PAYCURR":
				qry += "\n   v_progname:='采购付款单'; ";
				break;
			case "PAYCOST":
				qry += "\n   v_progname:='采购费用单'; ";
				break;
			case "FIRSTINCOMECURR":
				qry += "\n   v_progname:='期初应收款'; ";
				break;
			case "INCOMECURR":
				qry += "\n   v_progname:='销售收款单'; ";
				break;
			case "INCOMECOST":
				qry += "\n   v_progname:='销售费用单'; ";
				break;
			case "HOUSECOST":
				qry += "\n   v_progname:='店铺费用单'; ";
				break;
			case "SHOPSALEMAN":
				qry += "\n   v_progname:='商场调班记录'; ";
				break;
			}
			// 商品配货单，调价单撤单不用限定当日的单据
			if (Tablename.compareTo("WAREADJUSTH") != 0) // 商场调班表单独处理撤单
			{

				if (changedatebj == 0) // 不允许更改单据日期
				{
					if (Tablename.compareTo("SHOPSALEMAN") == 0) // 商场调班表单独处理撤单
					{
						qry += "\n   select to_char(workdate,'yyyy-mm-dd') into v_notedate from " + Tablename + " where Accid=" + Accid;
						qry += "\n   and id=" + Id + "; ";
					} else {
						qry += "\n   select to_char(notedate,'yyyy-mm-dd') into v_notedate from " + Tablename + " where Accid=" + Accid;
						qry += "\n   and Noteno='" + Noteno + "'";
						qry += "\n   and id=" + Id + "; ";
					}

					qry += "\n   if v_notedate<to_char(sysdate,'yyyy-mm-dd') then ";
					qry += "\n      select '0今日之前的单据不允许撤单！' into :retcs from dual; ";
					qry += "\n      return; ";
					qry += "\n   end if; ";
				}
			}
			qry += "\n   update " + Tablename + " set statetag=0,lastdate=sysdate where Accid=" + Accid + " and id=" + Id;
			if (Noteno.compareTo("***") != 0)
				qry += " and Noteno='" + Noteno + "'";

			qry += "  ;";
		}
		qry += "\n   commit;";
		// qry += "\n select '1操作成功！',v_progname into :retcs,:progname from dual; ";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs,v_progname into :retcs,:progname  from dual;  ";
		qry += "\n end; ";

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		param.put("progname", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作失败!";
			return 0;
		}
		// 取返回值
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		if (retcs.substring(0, 1).equals("0")) {
			return 0;
		} else {
			progname = param.get("progname").getParamvalue().toString();
			pFunc.myWriteLog(Accid, progname, "【撤单】" + Noteno, Lastop); // 写日志
			return 1;
		}

	}
}
