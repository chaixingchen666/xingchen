/**
 * 
 */
package com.oracle.bean;

import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.comdot.data.Table;
import com.common.tool.Func;
import com.common.tool.JGPush;
import com.common.tool.LogUtils;
import com.common.tool.PinYin;
import com.flyang.main.pFunc;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;

/**
 * @author Administrator
 *
 */
public class Funcpackge implements java.io.Serializable {

	private String Aes_Key = "sun81xjh34wmg91zhb16ccy35lk82xjw"; // 32
	// Aes_Key
	private long userid;
	private long accid;
	private String Accbegindate = "2010-01-01";

	public String getAccbegindate() {
		return Accbegindate;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

	private int ret;

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public void setAccid(long accid) {
		this.accid = accid;
	}

	private String errmess = "";

	public String getErrmess() {
		return errmess;
	}

	public int getTest(String opaction, String opvalue) {
		String qry = "";
		int ret = 0;
		switch (opaction) {
		case "temp":
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
			Date currdate = new Date();
			errmess = df.format(currdate);
			ret = 1;

			break;
		case "prodrun":
			Connection oc = null;
			CallableStatement cst = null;
			try {
				oc = DbHelperSQL.getConnection();

				qry = "declare";
				qry += "\n    v_count number;  ";
				qry += "\n    v_retss varchar2(100);";
				qry += "\n begin   ";
				qry += "\n    v_count:=1;";
				qry += "\n    if v_count=1 then";
				// qry += "\n select '返回一' into :retss from dual;";
				qry += "\n       v_retss:='返回一';";
				// qry += "\n goto exit;";
				qry += "\n    else";
				// qry += "\n select '返回二' into :retss from dual;";
				qry += "\n       v_retss:='返回二';";
				// qry += "\n goto exit;";
				qry += "\n    end if;";
				qry += "\n    <<exit>>";
				qry += "\n    select v_retss into :retss from dual;";
				qry += "\n end;";

				cst = oc.prepareCall(qry);

				cst.registerOutParameter("retss", Types.VARCHAR);
				// System.out.println("设置输出参数 end");

				cst.executeUpdate();
				errmess = "操作成功：" + cst.getString("retss");
				ret = 1;
				System.out.println("ok：" + cst.getString("retss"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// System.out.println("0:" + e.getMessage());
				// LogUtils.LogErrWrite("prodrun0:", e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					cst.close();
					oc.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					// System.out.println("1:" + e.getMessage());

					// LogUtils.LogErrWrite("prodrun1:", e.getMessage());
				}
			}
			break;
		case "pushtoerp":
			// System.out.println(opaction + " begin aaaa1 ");
			String ss = "erp10453^erp10551^erp990^";
			String tag = "erptag1489";
			String strmess = "ERP okok B端向B端发送消息  b====>b okokok";
			// System.out.println(opaction + " begin bbbb1 ");
			ret = JGPush.PushToErp(ss, tag, strmess);

			// JGPush.jSend_notification("erp990", strmess);
			if (ret == 1) {
				errmess = "发送成功:" + strmess;
				return 1;
			}

			else {
				errmess = "发送失败";
				return 0;
			}
			// 、、break;
		case "pushtovip":
			String s1 = "vip181^vip161^vip81^vip201^vip1425^vip141^vip285^vip1966^";
			String tag1 = "viptag49"; // onhouseid

			// String s1 = "erp10453^erp10551^erp990^";
			// String tag1 = "erptag1489";

			String strmess1 = "ERP java1 B端向C端发送消息 b====>c";
			// ret = JGPush.PushToVip(s1, tag1,strmess1,0,0,0);
			ret = JGPush.PushToVip(s1, tag1, strmess1, 49, 1, 0);
			if (ret == 1) {
				errmess = "发送成功:" + strmess1;
				return 1;
			} else {
				errmess = "发送失败";
				return 0;
			}

		case "waretype2shortname":
			Table tb = new Table();

			qry = "select typeid,typename from waretype";

			tb = DbHelperSQL.Query(qry).getTable(1);
			int rs = tb.getRowCount();
			// string typename = "";
			String shortname = "";
			qry = "begin ";
			// YZY.ERP.HTTPSERV.DAL.StrToPinyin pycode = new StrToPinyin();
			// //转首拼
			for (int i = 0; i < rs; i++) {
				shortname = PinYin.getPinYinHeadChar(tb.getRow(i).get("typename").toString()).toUpperCase();
				qry += "  update waretype set shortname='" + shortname + "' where typeid=" + tb.getRow(i).get("typeid").toString() + "; ";
			}
			qry += " commit;";
			qry += " end;";
			if (DbHelperSQL.ExecuteSql(qry) >= 0)
				errmess = "生成商品类型简称成功！";
			else
				errmess = "操作失败！";

			break;
		case "clearregmobile":// 清除注册手机
			if (!Func.isMobile(opvalue)) {
				errmess = "手机号无效！";
				return 0;
			}
			qry = "update accreg set mobile='' where mobile='" + opvalue + "'";
			ret = DbHelperSQL.ExecuteSql(qry);
			if (ret >= 0)
				errmess = "清除注册手机号成功！";
			else
				errmess = "操作失败！";
			break;
		case "clearaccpassword": // 恢复企业账号初始密码
			opvalue = opvalue.toUpperCase();
			if (!Func.isNumAndEnCh(opvalue)) {
				errmess = "企业账号无效！";
				break;
			}
			qry = "declare ";
			qry += "\n    v_accid number; ";
			qry += "\n    v_retcs varchar2(200);";
			qry += "\n begin ";
			qry += "\n  begin ";
			qry += "\n    select accid into v_accid from accreg where accname='" + opvalue + "';";
			qry += "\n    update accreg set password='6BAD136AEF6902EB0D3C0E7666DD2DB7' where accid=v_accid;";
			qry += "\n    update employe set password='6BAD136AEF6902EB0D3C0E7666DD2DB7',epno='ADMIN' where accid=v_accid and levelid=0;";
			qry += "\n    v_retcs:= '1企业账号:" + opvalue + "密码已恢复初始值:123456' ; ";
			qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN ";
			qry += "\n    v_retcs:= '0企业账号 :" + opvalue + " 不存在' ; ";
			qry += "\n  end;";
			qry += "\n  select v_retcs into :retcs from dual;";
			qry += "\n end;";
			// System.out.println(qry);
			// 申请返回值
			Map<String, ProdParam> param = new HashMap<String, ProdParam>();
			param.put("retcs", new ProdParam(Types.VARCHAR));
			// 调用
			if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
				// System.out.println("clearaccpassword 1");
				errmess = "操作失败！";
				break;
			}
			// System.out.println("clearaccpassword 2");
			// 取返回值
			String retcs1 = param.get("retcs").getParamvalue().toString();
			// System.out.println("clearaccpassword 3 " + retcs1);
			errmess = retcs1.substring(1);
			if (retcs1.substring(0, 1).equals("1"))

				ret = 1;

			else
				ret = 0;

			break;
		case "aes":
			// System.out.println("aes begin");
			// // String ss= Func.aesEncrypt1("abcdefghijk123456", Aes_Key);
			// //System.out.println( ss);
			// // String ss=new
			// BASE64Encoder().encode(Func.aesEncrypt1("abcdefghijk123456",
			// Aes_Key)).trim().replaceAll("\r\n", "").replaceAll("\n", "");;
			// Func fyfun = new Func();
			// byte[] bytes = fyfun.aesEncrypt("abcdefghijk123456", Aes_Key);
			// System.out.println("1:" + bytes);
			//
			// String ss = new
			// BASE64Encoder().encode(bytes).trim().replaceAll("\r\n",
			// "").replaceAll("\n", "");
			// // String
			// ss=BASE64Encoder().encode(bytes).trim().replaceAll("\r\n",
			// "").replaceAll("\n", "");
			//
			// System.out.println("2:" + ss);
			// System.out.println("3: " + fyfun.aesEncrypt("abcdefghijk123456",
			// Aes_Key));
			break;
		default:
			errmess = "opaction参数无效";
			break;
		}

		return ret;
	}

	// 汇总库存
	public int myTotalWaresum(String nowdate, long accid) {
		// System.out.println("myTotalWaresum begin");

		Date nowday = Func.StrToDate(nowdate, "yyyy-MM-dd");

		String nowdate0 = Func.DateToStr(Func.getPrevDay(nowday), "yyyy-MM-dd"); // nowday.AddDays(-1).ToString("yyyy-MM-dd");
		String nowdate1 = Func.DateToStr(Func.getNextDay(nowday), "yyyy-MM-dd"); // nowday.AddDays(1).ToString("yyyy-MM-dd");
		String qry = "declare  ";
		qry += "\n    v_nowdate varchar2(10); ";
		qry += "\n    v_nowdate0 varchar2(10); ";
		qry += "\n    v_count number; ";
		qry += "\n begin ";
		// qry += "\n v_nowdate:='" + nowdate + "'; ";
		/*--取前一天作为期初时间*/
		// qry += "\n
		// v_nowdate0:=to_char(to_date(v_nowdate,'yyyy-mm-dd')-1,'yyyy-mm-dd');
		// ";
		// --v_nowdate0:='2015-05-07';
		/*--汇总本日发生填入临时表*/
		// qry+="\n insert into waresum_tmp
		// (houseid,wareid,colorid,sizeid,amount,curr,price) ";

		// --删除旧记录
		/*
		 * qry += "\n    delete from waresum a where daystr='" + nowdate + "' ";
		 * if (accid > 0) qry +=
		 * "\n    and exists (select 1 from warecode b where b.accid=" + accid +
		 * " and b.wareid=a.wareid ) "; else qry +=
		 * "  and exists (select 1 from warecode a1 join accreg b1 on a1.accid=b1.accid where a.wareid=a1.wareid  and b1.begindate<to_date('"
		 * + nowdate + " 23:59:59" + "','yyyy-mm-dd hh24:mi:ss' )"; qry +=
		 * "\n    ; "; qry += "\n   commit; ";
		 */

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		// qry += "\n SELECT ROWID FROM waresum WHERE daystr='2016-03-29' order
		// by rowid; ";// ----按ROWID排序的Cursor，删除条件是XXX=XXXX，根据实际情况来定。

		qry += "\n       SELECT  ROWID from  waresum a where daystr='" + nowdate + "' ";
		if (accid > 0)
			qry += "\n    and exists (select 1 from warecode b where b.accid=" + accid + " and b.wareid=a.wareid ) ";
		// else qry += "\n and exists (select 1 from warecode a1 join accreg b1
		// on a1.accid=b1.accid where a.wareid=a1.wareid and
		// b1.begindate<to_date('" + nowdate + " 23:59:59" + "','yyyy-mm-dd
		// hh24:mi:ss'))";
		qry += "\n       order by rowid; ";

		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 5000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from waresum  where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		// --从临时表里添加记录
		// qry += "\n insert into waresum
		// (id,daystr,houseid,wareid,colorid,sizeid,amount,curr,price) ";
		// qry += "\n select
		// waresum_id.nextval,'"+nowdate+"',houseid,wareid,colorid,sizeid,nvl(amount,0),nvl(curr,0),nvl(price,0)
		// from ( ";
		qry += "\n    declare cursor cur_waresum is ";
		qry += "\n    select houseid,wareid,colorid,sizeid,sum(amount) as amount,0 as curr,0 as price  ";
		qry += "\n    from ( ";
		/*--取期初*/
		qry += "\n    select houseid,wareid,colorid,sizeid,amount,curr from waresum a where daystr='" + nowdate0 + "' and (amount>0 or amount<0) and houseid>0";
		if (accid > 0)
			qry += "\n    and exists (select 1 from warecode b where b.accid=" + accid + " and b.wareid=a.wareid and b.statetag=1 ) ";
		else
			qry += "\n    and exists (select 1 from warecode b where b.wareid=a.wareid and b.statetag=1 ) ";
		// else qry += " and exists (select 1 from warecode a1 join accreg b1 on
		// a1.accid=b1.accid where a.wareid=a1.wareid and
		// b1.begindate<to_date('" + nowdate + " 23:59:59" + "','yyyy-mm-dd
		// hh24:mi:ss' ))";
		qry += "\n    union all ";
		/*--采购入库*/
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amount,SUM(b.curr) as curr ";
		qry += "\n    from wareinh a join wareinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and a.ntid=0 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss' ))";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid ";
		qry += "\n    union all ";
		/*--采购退库*/
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,-sum(b.amount) as amount,-SUM(b.curr) as curr ";
		qry += "\n    from wareinh a join wareinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and a.ntid=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid ";
		qry += "\n    union all ";
		/*--期初入库*/
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amount,SUM(b.curr) as curr ";
		qry += "\n    from firsthouseh a join firsthousem b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid ";
		qry += "\n    union all ";
		/*--调拨入库*/
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amount,SUM(b.curr) as curr ";
		qry += "\n    from allotinh a join allotinm b on a.accid=b.accid and a.noteno=b.noteno   ";
		qry += "\n    where a.statetag=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid ";
		/*--销售出库   */
		qry += "\n    union all ";
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,-sum(b.amount) as amount,-SUM(b.curr) as curr ";
		qry += "\n    from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and (a.ntid=0 or a.ntid=1) and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid ";
		/*--经销退库   */
		qry += "\n    union all ";
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amount,SUM(b.curr) as curr ";
		qry += "\n    from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and a.ntid=2 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid ";

		/*--商场销售   */
		qry += "\n    union all ";
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,-sum(b.amount) as amount,-SUM(b.curr) as curr ";
		qry += "\n    from shopsaleh a join shopsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid ";

		/*--调拨出库*/
		qry += "\n    union all ";
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,-sum(b.amount) as amount,-SUM(b.curr) as curr ";
		qry += "\n    from allotouth a join allotoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid ";
		/*--盘存  */
		qry += "\n    union all  ";
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amount,SUM(b.curr) as curr  ";
		qry += "\n    from warecheckh a join warecheckm b on a.accid=b.accid and a.noteno=b.noteno   ";
		qry += "\n    where a.statetag=1 and (b.amount>0 or b.amount<0) and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd')";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid ";

		qry += "\n    ) group by houseid,wareid,colorid,sizeid ; ";
		qry += "\n      v_row cur_waresum%rowtype;";
		qry += "\n    begin ";
		qry += "\n      v_count:=0; ";
		qry += "\n      for v_row in cur_waresum loop ";
		qry += "\n        delete from waresum where daystr='" + nowdate + "' and houseid=v_row.houseid   and wareid=v_row.wareid  and colorid=v_row.colorid and sizeid=v_row.sizeid ; ";
		qry += "\n        insert into waresum (id,daystr,houseid,wareid,colorid,sizeid,amount,curr,price) ";
		qry += "\n        values ( waresum_id.nextval,'" + nowdate + "',v_row.houseid,v_row.wareid,v_row.colorid,v_row.sizeid,nvl(v_row.amount,0),nvl(v_row.curr,0),nvl(v_row.price,0) ); ";
		qry += "\n        v_count:=v_count+1; ";
		qry += "\n        if v_count>1000 then ";
		qry += "\n           v_count:=0; ";
		qry += "\n           commit; ";
		qry += "\n        end if; ";
		qry += "\n      end loop; ";
		qry += "\n    end;  ";

		qry += "\n    commit; ";
		// 处理商品颜色
		qry += "\n   v_count:=0;";
		qry += "\n   declare cursor cur_data is";
		qry += "\n     select distinct '^'||a.wareid||'^'||a.colorid||'^' as tt ,a.wareid,a.colorid from waresum a ";
		qry += "\n     where a.daystr='" + nowdate + "' and not a.colorid is null";
		if (accid > 0)
			qry += "\n    and exists (select 1 from warecode b where b.accid=" + accid + " and b.wareid=a.wareid ) ";
		qry += "\n     and not exists (select 1 from warecolor c where a.wareid=c.wareid and a.colorid=c.colorid);";
		qry += "\n     v_row cur_data%rowtype;";
		qry += "\n   begin";
		qry += "\n     for v_row in cur_data loop ";
		qry += "\n        insert into warecolor (id,wareid,colorid,lastop)";
		qry += "\n        values (warecolor_id.nextval,v_row.wareid,v_row.colorid,'sysdba');";
		qry += "\n        v_count:=v_count+1;";
		qry += "\n        if v_count>1000 then";
		qry += "\n           commit;";
		qry += "\n           v_count:=0;";
		qry += "\n        end if;";
		qry += "\n     end loop;";
		qry += "\n   end;";
		qry += "\n   commit;";

		qry += "\n end;";
		// LogUtils.LogDebugWrite("totalwaresum", qry);
		DbHelperSQL db = new DbHelperSQL();
		int ret = db.ExecuteSql(qry, 1800); // 30分钟
		if (ret < 0)
			errmess = db.getErrmess();
		else
			errmess = "计算成功！";
		return ret;

		// int ret = DbHelperSQL.ExecuteSql(qry);
		// if (ret == 0)
		// errmess = "计算失败";
		// else
		// errmess = "计算成功！";
		// return ret;
	}

	// 汇总往来账款
	// @SuppressWarnings("static-access")
	public int myTotalCurr(String nowdate, long accid) {
		// System.out.println("myTotalCost begin");

		Date nowday = Func.StrToDate(nowdate, "yyyy-MM-dd");

		String nowdate0 = Func.DateToStr(Func.getPrevDay(nowday), "yyyy-MM-dd"); // nowday.AddDays(-1).ToString("yyyy-MM-dd");
		String nowdate1 = Func.DateToStr(Func.getNextDay(nowday), "yyyy-MM-dd"); // nowday.AddDays(1).ToString("yyyy-MM-dd");
		String qry = "declare ";
		qry += "\n    v_nowdate varchar2(10);";
		qry += "\n    v_nowdate0 varchar2(10);";
		qry += "\n    v_count number;";
		qry += "\n  begin";

		/*
		 * qry += "\n    delete from incomebal a where daystr='" + nowdate +
		 * "' "; if (accid > 0) qry +=
		 * "\n    and exists (select 1 from customer a1 where a1.accid=" + accid
		 * + " and a.custid=a1.custid ) "; //else qry +=
		 * "  and exists (select 1 from customer a1 join accreg b1 on a1.accid=b1.accid where a.custid=a1.custid  and b1.begindate<to_date('"
		 * + nowdate + " 23:59:59" + "','yyyy-mm-dd hh24:mi:ss' )"; qry +=
		 * "\n    ; ";
		 */

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		// qry += "\n SELECT ROWID FROM waresum WHERE daystr='2016-03-29' order
		// by rowid; ";// ----按ROWID排序的Cursor，删除条件是XXX=XXXX，根据实际情况来定。

		qry += "\n      SELECT ROWID from incomebal a where daystr='" + nowdate + "' ";
		if (accid > 0)
			qry += "\n    and exists (select 1 from customer a1 where a1.accid=" + accid + " and a.custid=a1.custid ) ";
		// else qry += " and exists (select 1 from customer a1 join accreg b1 on
		// a1.accid=b1.accid where a.custid=a1.custid and
		// b1.begindate<to_date('" + nowdate + " 23:59:59" + "','yyyy-mm-dd
		// hh24:mi:ss' )";

		qry += "\n       order by rowid; ";

		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 5000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from incomebal where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		qry += "\n   declare cursor cur_incomebal is  ";
		qry += "\n   select custid,houseid,sum(curr) as curr from (  ";
		// --取上日余额
		qry += "\n   select custid,houseid,curr from incomebal a where daystr='" + nowdate0 + "' and (curr>0 or curr<0)  ";
		if (accid > 0)
			qry += "\n    and exists (select 1 from customer a1 where a1.accid=" + accid + " and a.custid=a1.custid ) ";
		// else qry += " and exists (select 1 from customer a1 join accreg b1 on
		// a1.accid=b1.accid where a.custid=a1.custid and
		// b1.begindate<to_date('" + nowdate + " 23:59:59" + "','yyyy-mm-dd
		// hh24:mi:ss' ))";
		// --初始应收款记录
		qry += "\n  union all  ";
		qry += "\n  select a.custid,a.houseid,SUM(a.curr) as curr  ";
		qry += "\n  from firstincomecurr a   ";
		qry += "\n  where a.statetag=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd')  ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n  group by a.custid,a.houseid  ";
		// --销售应收
		qry += "\n  union all ";
		qry += "\n  select a.custid,a.houseid,SUM(case a.ntid when 1 then b.curr else -b.curr end) as curr  ";
		qry += "\n  from wareouth a left outer join wareoutm b on a.accid=b.accid and a.noteno=b.noteno   ";
		qry += "\n  where a.statetag=1 and a.ntid=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd')  ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n  group by a.custid,a.houseid  ";

		// --批发销售应收费用
		qry += "\n  union all ";
		qry += "\n  select a.custid,a.houseid,SUM(b.curr) as curr  ";
		qry += "\n  from wareouth a left outer join wareoutcost b on a.accid=b.accid and a.noteno=b.noteno   ";
		qry += "\n  where a.statetag=1 and (a.ntid=1 or a.ntid=2) and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd')  ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n  group by a.custid,a.houseid  ";

		// --批发退货
		qry += "\n  union all  ";
		qry += "\n  select a.custid,a.houseid,-SUM(b.curr) as curr  ";
		qry += "\n  from wareouth a left outer join wareoutm b on a.accid=b.accid and a.noteno=b.noteno   ";
		qry += "\n  where a.statetag=1 and a.ntid=2 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd')  ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n  group by a.custid,a.houseid  ";
		// --应收费用
		qry += "\n  union all  ";
		qry += "\n  select a.custid,a.houseid,SUM(a.curr) as curr  ";
		qry += "\n  from incomecost a  ";
		qry += "\n  where a.statetag=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd')  ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n  group by a.custid,a.houseid  ";
		// --已收金额,折让金额
		qry += "\n  union all   ";
		qry += "\n  select a.custid,a.houseid,-SUM(a.curr) as curr  ";
		qry += "\n  from incomecurr a   ";
		qry += "\n  where a.statetag=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd')  ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n  group by a.custid,a.houseid  ";

		qry += "\n  ) group by custid,houseid ; ";

		qry += "\n   v_row cur_incomebal%rowtype; ";
		qry += "\n  begin ";
		qry += "\n    v_count:=0;";
		qry += "\n    for v_row in cur_incomebal loop ";
		qry += "\n      delete from incomebal where daystr='" + nowdate + "' and custid=v_row.custid  and houseid=v_row.houseid  ; ";

		qry += "\n      insert into incomebal (id,daystr,custid,houseid,curr) ";
		qry += "\n      values (incomebal_ID.NEXTVAL,'" + nowdate + "',v_row.custid,v_row.houseid,nvl(v_row.curr,0)); ";
		qry += "\n      if v_count>1000 then ";
		qry += "\n         commit; ";
		qry += "\n         v_count:=0;";
		qry += "\n      end if; ";
		qry += "\n    end loop; ";
		qry += "\n  end;  ";

		qry += "\n  commit; ";

		// --删除旧记录
		/*
		 * qry += "\n  delete from paybal a where daystr='" + nowdate + "' "; if
		 * (accid > 0) qry +=
		 * "\n    and exists (select 1 from provide a1 where a1.accid=" + accid
		 * + " and a.provid=a1.provid ) "; //else qry +=
		 * "  and exists (select 1 from provide a1 join accreg b1 on a1.accid=b1.accid where a.provid=a1.provid  and b1.begindate<to_date('"
		 * + nowdate + " 23:59:59" + "','yyyy-mm-dd hh24:mi:ss' )"; qry +=
		 * "\n    ; ";
		 */

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		// qry += "\n SELECT ROWID FROM waresum WHERE daystr='2016-03-29' order
		// by rowid; ";// ----按ROWID排序的Cursor，删除条件是XXX=XXXX，根据实际情况来定。

		qry += "\n      SELECT ROWID from paybal a where daystr='" + nowdate + "' ";
		if (accid > 0)
			qry += "\n    and exists (select 1 from provide a1 where a1.accid=" + accid + " and a.provid=a1.provid ) ";
		// else qry += " and exists (select 1 from customer a1 join accreg b1 on
		// a1.accid=b1.accid where a.custid=a1.custid and
		// b1.begindate<to_date('" + nowdate + " 23:59:59" + "','yyyy-mm-dd
		// hh24:mi:ss' )";

		qry += "\n       order by rowid; ";

		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 5000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from paybal where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		// --从临时表里添加记录
		// qry += "\n insert into paybal (id,daystr,provid,houseid,curr) ";
		qry += "\n  declare cursor cur_paybal is  ";

		// qry += "\n select paybal_id.nextval,'" + nowdate +
		// "',provid,houseid,nvl(curr,0) from ( ";

		// qry += "\n select provid,curr from ( ";
		qry += "\n  select provid,houseid,sum(curr) as curr from ( ";
		// --取上日余额
		qry += "\n  select provid,houseid,curr from paybal a where daystr='" + nowdate0 + "' and (curr>0 or curr<0)   ";
		if (accid > 0)
			qry += "\n    and exists (select 1 from provide a1 where a1.accid=" + accid + " and a.provid=a1.provid ) ";
		// else qry += " and exists (select 1 from provide a1 join accreg b1 on
		// a1.accid=b1.accid where a.provid=a1.provid and
		// b1.begindate<to_date('" + nowdate + " 23:59:59" + "','yyyy-mm-dd
		// hh24:mi:ss') )";
		// --group by provid
		// --初始应付款记录
		qry += "\n  union all ";
		qry += "\n  select a.provid,a.houseid,SUM(a.curr) as curr ";
		qry += "\n  from firstpaycurr a  ";
		qry += "\n  where a.statetag=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n  group by a.provid,a.houseid ";
		// --采购应付
		qry += "\n  union all ";
		qry += "\n  select a.provid,a.houseid,SUM(b.curr) as curr ";
		qry += "\n  from wareinh a left outer join wareinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n  where a.statetag=1 and a.ntid=0 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n  group by a.provid,a.houseid ";
		// --采购应付费用
		qry += "\n  union all ";
		qry += "\n  select a.provid,a.houseid,SUM(case a.ntid when 0 then b.curr else -b.curr end) as curr  ";
		qry += "\n  from wareinh a left outer join wareincost b on a.accid=b.accid and a.noteno=b.noteno   ";
		qry += "\n  where a.statetag=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd')  ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n  group by a.provid,a.houseid  ";

		// --采购应退
		qry += "\n  union all ";
		qry += "\n  select a.provid,a.houseid,-SUM(b.curr) as curr ";
		qry += "\n  from wareinh a left outer join wareinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n  where a.statetag=1 and a.ntid=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n  group by a.provid,a.houseid ";
		// --应付费用
		qry += "\n  union all ";
		qry += "\n  select a.provid,a.houseid,SUM(a.curr) as curr ";
		qry += "\n  from paycost a  ";
		qry += "\n  where a.statetag=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n  group by a.provid,a.houseid ";
		// --已付金额,折让金额
		qry += "\n  union all  ";
		qry += "\n  select a.provid,a.houseid,-SUM(a.curr) as curr ";
		qry += "\n  from paycurr a  ";
		qry += "\n  where a.statetag=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss'))";
		qry += "\n  group by a.provid,a.houseid ";

		qry += "\n  ) group by provid,houseid ;";
		// qry += "\n ); ";
		qry += "\n   v_row cur_paybal%rowtype; ";
		qry += "\n  begin ";
		qry += "\n    v_count:=0; ";
		qry += "\n    for v_row in cur_paybal loop ";
		qry += "\n      delete from paybal where daystr='" + nowdate + "' and provid=v_row.provid  and houseid=v_row.houseid  ; ";
		qry += "\n      insert into paybal (id,daystr,provid,houseid,curr) ";
		qry += "\n      values (paybal_ID.NEXTVAL,'" + nowdate + "',v_row.provid,v_row.houseid,nvl(v_row.curr,0)); ";
		qry += "\n      if v_count>1000 then ";
		qry += "\n         commit; ";
		qry += "\n         v_count:=0;";
		qry += "\n      end if; ";
		qry += "\n    end loop; ";
		qry += "\n  end;  ";

		qry += "\n  commit; ";
		qry += "\n end;";
		// LogUtils.LogDebugWrite("mytotalcurr", qry);
		DbHelperSQL db = new DbHelperSQL();
		int ret = db.ExecuteSql(qry, 1800);
		if (ret < 0)
			errmess = db.getErrmess();
		else
			errmess = "计算成功！";
		return ret;
	}

	// 计算成本
	public int myTotalCost(String nowdate, long accid) {
		// System.out.println("myTotalCost begin");

		Date nowday = Func.StrToDate(nowdate, "yyyy-MM-dd");

		String nowdate0 = Func.DateToStr(Func.getPrevDay(nowday), "yyyy-MM-dd"); // nowday.AddDays(-1).ToString("yyyy-MM-dd");
		String nowdate1 = Func.DateToStr(Func.getNextDay(nowday), "yyyy-MM-dd"); // nowday.AddDays(1).ToString("yyyy-MM-dd");
		// System.out.println(nowdate + " " + nowdate0 + " " + nowdate1);

		String qry = "declare ";
		qry += "     v_nowdate varchar2(10); ";
		qry += "     v_nowdate0 varchar2(10); ";
		qry += "     v_count number(10); ";
		qry += "     v_costprice number(16,4); ";
		qry += "     v_costprice1 number(16,4);   ";
		qry += "     v_amount number; ";
		qry += "     v_curr number; ";
		qry += "     v_curr1 number; ";

		qry += " begin ";
		// --删除旧记录
		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		// qry += "\n SELECT ROWID FROM waresum WHERE daystr='2016-03-29' order
		// by rowid; ";// ----按ROWID排序的Cursor，删除条件是XXX=XXXX，根据实际情况来定。

		qry += "\n       SELECT  ROWID from  warecost a where (daystr='" + nowdate + "' or daystr='" + nowdate1 + "') ";
		if (accid > 0)
			qry += "\n    and exists (select 1 from warecode b where b.accid=" + accid + " and b.wareid=a.wareid ) ";
		// else qry += "\n and exists (select 1 from warecode a1 join accreg b1
		// on a1.accid=b1.accid where a.wareid=a1.wareid and
		// b1.begindate<to_date('" + nowdate + " 23:59:59" + "','yyyy-mm-dd
		// hh24:mi:ss' )";
		qry += "\n       order by rowid;  ";

		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 5000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from warecost  where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";
		// 成本不分店铺计算
		qry += "\n    declare cursor cur_data is ";
		qry += "\n    select a.wareid,a.amountqc,a.currqc,a.currqc1,a.amountrk,a.currrk,a.currrk1,a.amountrt,a.amountck,a.amountpy,a.amountpk ";
		qry += "\n    ,nvl(b.costprice,0) as costprice,nvl(b.costprice1,0) as costprice1 from ( ";
		qry += "\n    select wareid,sum(amountqc) as amountqc,sum(currqc) as currqc,sum(currqc) as currqc1";
		qry += "\n    ,sum(amountrk) as amountrk,sum(currrk) as currrk,sum(currrk1) as currrk1,sum(amountrt) as amountrt,sum(amountck) as amountck  ";
		qry += "\n    ,sum(amountpy) as amountpy,sum(amountpk) as amountpk  ";
		qry += "\n    from (  ";
		qry += "\n    select a.wareid,nvl(sum(b.amount),0) as amountqc,nvl(sum(b.amount*a.costprice),0) as currqc,nvl(sum(b.amount*costprice1),0) as currqc1,0 as amountrk,0 as currrk,0 as currrk1,0 as amountrt,0 as amountck";
		qry += "\n    ,0 as amountpy,0 as amountpk";
		qry += "\n    from warecost a ";
		qry += "\n    left outer join waresum b on a.wareid=b.wareid and a.daystr=b.daystr";

		qry += "\n    where a.daystr='" + nowdate0 + "'";// + "' and
															// (costprice>0 or
															// costprice<0 or
															// costprice1>0 or
															// costprice1<0 ) ";
		if (accid > 0)
			qry += "\n    and exists (select 1 from warecode b where b.accid=" + accid + " and b.wareid=a.wareid and a.daystr='" + nowdate0 + "' ) ";
		qry += "\n    and exists (select 1 from accreg x,warecode y where x.accid=y.accid and a.wareid=y.wareid and substr(x.qxcs,2,1)<>'1')";

		qry += "\n    group by a.wareid";
		qry += "\n    union all  ";
		// 采购入库 amount>0
		// qry += "\n select b.wareid,sum(b.amount) as amount,SUM(b.curr) as
		// curr,sum(b.amount*b.price1) as curr1 ";
		qry += "\n    select b.wareid,0 as amountqc,0 as currqc,0 as currqc1,sum(b.amount) as amountrk,SUM(b.curr) as currrk,sum(b.amount*b.price1) as currrk1,0 as amountrt,0 as amountck ";
		qry += "\n    ,0 as amountpy,0 as amountpk";
		qry += "\n    from wareinh a join wareinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and a.ntid=0";// and b.amount>0 ";
		qry += "\n    and exists (select 1 from accreg x where x.accid=a.accid and substr(x.qxcs,2,1)<>'1')";
		qry += "\n    and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss' ))";
		qry += "\n    group by b.wareid  ";

		// qry += "\n union all ";
		// // --采购入库 amount<0
		// qry += "\n select b.wareid,0 as amountqc,0 as currqc,0 as currqc1,0
		// as amountrk,0 as currrk,0 as currrk1,-sum(b.amount) as amountrt,0 as
		// amountck ";
		// qry += "\n ,0 as amountpy,0 as amountpk";
		// qry += "\n from wareinh a join wareinm b on a.accid=b.accid and
		// a.noteno=b.noteno ";
		// qry += "\n where a.statetag=1 and a.ntid=0 and b.amount<0 ";
		// qry += "\n and exists (select 1 from accreg x where x.accid=a.accid
		// and substr(x.qxcs,2,1)<>'1')";
		// qry += "\n and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd')
		// and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		// qry += "\n group by b.wareid ";
		qry += "\n    union all  ";
		// --采购退库
		qry += "\n    select b.wareid,0 as amountqc,0 as currqc,0 as currqc1,0 as amountrk,0 as currrk,0 as currrk1,sum(b.amount) as amountrt,0 as amountck ";
		qry += "\n    ,0 as amountpy,0 as amountpk";
		qry += "\n    from wareinh a join wareinm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n    where a.statetag=1 and a.ntid=1  ";
		qry += "\n    and exists (select 1 from accreg x where x.accid=a.accid and substr(x.qxcs,2,1)<>'1')";
		qry += "\n    and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		qry += "\n    group by b.wareid";

		// 销售出库
		qry += "\n    union all  ";
		qry += "\n    select b.wareid,0 as amountqc,0 as currqc,0 as currqc1,0 as amountrk,0 as currrk,0 as currrk1,0 as amountrt,sum(case a.ntid when 2 then -b.amount else b.amount end) as amountck ";
		qry += "\n    ,0 as amountpy,0 as amountpk";
		qry += "\n    from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 ";
		qry += "\n    and exists (select 1 from accreg x where x.accid=a.accid and substr(x.qxcs,2,1)<>'1')";
		qry += "\n    and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss' ))";
		qry += "\n    group by b.wareid  ";

		// 商场销售
		qry += "\n    union all  ";
		qry += "\n    select b.wareid,0 as amountqc,0 as currqc,0 as currqc1,0 as amountrk,0 as currrk,0 as currrk1,0 as amountrt,sum(b.amount) as amountck ";
		qry += "\n    ,0 as amountpy,0 as amountpk";
		qry += "\n    from shopsaleh a join shopsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 ";
		qry += "\n    and exists (select 1 from accreg x where x.accid=a.accid and substr(x.qxcs,2,1)<>'1')";
		qry += "\n    and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss' ))";
		qry += "\n    group by b.wareid  ";

		// 盘点
		qry += "\n    union all  ";
		qry += "\n    select b.wareid,0 as amountqc,0 as currqc,0 as currqc1,0 as amountrk,0 as currrk,0 as currrk1,0 as amountrt,0 as amountck ";
		qry += "\n    ,sum(case when b.amount>0 then b.amount else 0 end) as amountpy,sum(case when b.amount<0 then -b.amount else 0 end) as amountpk";
		qry += "\n    from warecheckh a join warecheckm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and (b.amount>0 or b.amount<0) ";
		qry += "\n    and exists (select 1 from accreg x where x.accid=a.accid and substr(x.qxcs,2,1)<>'1')";
		qry += "\n    and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		qry += "\n    group by b.wareid  ";
		// 调拨入库
		qry += "\n    union all  ";
		qry += "\n    select b.wareid,0 as amountqc,0 as currqc,0 as currqc1,0 as amountrk,0 as currrk,0 as currrk1,0 as amountrt,sum(-b.amount) as amountck ";
		qry += "\n    ,0 as amountpy,0 as amountpk";
		qry += "\n    from allotinh a join allotinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 ";
		qry += "\n    and exists (select 1 from accreg x where x.accid=a.accid and substr(x.qxcs,2,1)<>'1')";
		qry += "\n    and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss' ))";
		qry += "\n    group by b.wareid  ";

		// 调拨出库
		qry += "\n    union all  ";
		qry += "\n    select b.wareid,0 as amountqc,0 as currqc,0 as currqc1,0 as amountrk,0 as currrk,0 as currrk1,0 as amountrt,sum(b.amount) as amountck ";
		qry += "\n    ,0 as amountpy,0 as amountpk";
		qry += "\n    from allotouth a join allotoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 ";
		qry += "\n    and exists (select 1 from accreg x where x.accid=a.accid and substr(x.qxcs,2,1)<>'1')";
		qry += "\n    and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss' ))";
		qry += "\n    group by b.wareid  ";

		qry += "\n    union all  ";
		// 期初入库
		// qry += "\n select b.wareid,sum(b.amount) as amount,SUM(b.curr) as
		// curr,sum(b.curr) as curr1 ";
		qry += "\n    select b.wareid,0 as amountqc,0 as currqc,0 as currqc1,sum(b.amount) as amountrk,SUM(b.curr) as currrk,sum(b.curr) as currrk1,0 as amountrt,0 as amountck ";
		qry += "\n    ,0 as amountpy,0 as amountpk";
		qry += "\n    from firsthouseh a join firsthousem b on a.accid=b.accid and a.noteno=b.noteno   ";
		qry += "\n    where a.statetag=1 and a.ntid=0 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		qry += "\n    and exists (select 1 from accreg x where x.accid=a.accid and substr(x.qxcs,2,1)<>'1')";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss' ))";
		qry += "\n    group by b.wareid ) group by wareid ) a ";
		qry += "\n    left outer join warecost b on a.wareid=b.wareid and b.daystr='" + nowdate0 + "' and b.houseid=0";
		qry += "\n    where exists (select 1 from accreg x join warecode y on x.accid=y.accid where a.wareid=y.wareid and substr(x.qxcs,2,1)<>'1')";
		if (accid > 0)
			qry += "\n    and exists (select 1 from warecode b1 where b1.accid=" + accid + " and b1.wareid=a.wareid ) ";
		qry += "\n    ;";
		qry += "\n    v_row cur_data%rowtype; ";
		qry += "\n  begin ";
		qry += "\n    v_count:=0;";
		qry += "\n    for v_row in cur_data loop ";
		qry += "\n       v_costprice:=v_row.costprice;";
		qry += "\n       v_costprice1:=v_row.costprice1; ";
		// qry += "\n if v_row.amountqc>0 then ";
		// qry += "\n v_costprice:=round(v_row.currqc/v_row.amountqc,2); ";//
		// --期初成本
		// qry += "\n v_costprice1:=round(v_row.currqc1/v_row.amountqc,2); ";//
		// --期初配送成本
		// qry += "\n end if; ";
		qry += "\n       v_amount:=v_row.amountqc-v_row.amountrt-v_row.amountpk; "; // 减采购退货数量-盘亏
		// 2017-03-19
		qry += "\n      if v_amount<0 then ";
		qry += "\n         v_amount:=0; ";
		qry += "\n      end if; ";
		// 计算加权成本
		qry += "\n       v_curr:=round(v_amount*v_costprice,2)+v_row.currrk; ";
		qry += "\n       v_curr1:=round(v_amount*v_costprice1,2)+v_row.currrk1; ";
		qry += "\n       v_amount:=v_amount+v_row.amountrk; ";
		qry += "\n       if v_amount>0 then ";
		qry += "\n          v_costprice:=round(v_curr/v_amount,2); ";
		qry += "\n          v_costprice1:=round(v_curr1/v_amount,2); ";
		qry += "\n       end if; ";
		// 结存数量及金额
		qry += "\n       v_amount:=v_amount-v_row.amountck+v_row.amountpy; "; // 结存
		// 不分店铺计算成本

		// 2017-03-19
		qry += "\n      if v_amount<0 then";
		qry += "\n         v_amount:=0; ";
		qry += "\n      end if; ";

		qry += "\n       v_curr:=v_amount*v_costprice;";

		qry += "\n      delete from warecost where daystr='" + nowdate + "' and wareid=v_row.wareid and houseid=0 ; ";
		qry += "\n      insert into warecost (id,daystr,wareid,amount,curr,costprice,costprice1)  ";
		qry += "\n      values ( WARECOST_ID.NEXTVAL,'" + nowdate + "',v_row.wareid,v_amount,v_curr,v_costprice,v_costprice1); ";

		qry += "\n      delete from warecost where daystr='" + nowdate1 + "' and wareid=v_row.wareid and houseid=0  ; ";
		qry += "\n      insert into warecost (id,daystr,wareid,amount,curr,costprice,costprice1)  ";
		qry += "\n      values ( WARECOST_ID.NEXTVAL,'" + nowdate1 + "',v_row.wareid,v_amount,v_curr,v_costprice,v_costprice1); ";
		qry += "\n      v_count:=v_count+1;";
		qry += "\n      if v_count>1000 then ";
		qry += "\n         commit;";
		qry += "\n         v_count:=0; ";
		qry += "\n      end if;";
		qry += "\n    end loop; ";
		qry += "\n  end;     ";
		// 分店铺计算成本

		qry += "\n    declare cursor cur_data is ";
		qry += "\n    select a.houseid,a.wareid,a.amountqc,a.currqc,a.currqc1,a.amountrk,a.currrk,a.currrk1,a.amountrt,a.amountck,a.amountpy,a.amountpk ";
		qry += "\n    ,nvl(b.costprice,0) as costprice,nvl(b.costprice1,0) as costprice1 from ( ";
		qry += "\n    select houseid,wareid,sum(amountqc) as amountqc,sum(currqc) as currqc,sum(currqc) as currqc1";
		qry += "\n    ,sum(amountrk) as amountrk,sum(currrk) as currrk,sum(currrk1) as currrk1,sum(amountrt) as amountrt,sum(amountck) as amountck ";
		qry += "\n    ,sum(amountpy) as amountpy,sum(amountpk) as amountpk  ";
		qry += "\n    from (  ";
		qry += "\n    select a.houseid,a.wareid,nvl(sum(b.amount),0) as amountqc,nvl(sum(b.amount*a.costprice),0) as currqc,nvl(sum(b.amount*a.costprice1),0) as currqc1,0 as amountrk,0 as currrk,0 as currrk1,0 as amountrt,0 as amountck";
		qry += "\n    ,0 as amountpy,0 as amountpk";
		qry += "\n    from warecost a ";
		qry += "\n    left outer join waresum b on a.wareid=b.wareid and a.daystr=b.daystr and a.houseid=b.houseid";

		qry += "\n    where a.daystr='" + nowdate0 + "'";// + "' and
															// (costprice>0 or
															// costprice<0 or
															// costprice1>0 or
															// costprice1<0 ) ";
		if (accid > 0)
			qry += "\n    and exists (select 1 from warecode b where b.accid=" + accid + " and b.wareid=a.wareid and a.daystr='" + nowdate0 + "' ) ";
		qry += "\n    and exists (select 1 from accreg x,warecode y where x.accid=y.accid and a.wareid=y.wareid and substr(x.qxcs,2,1)='1')";
		qry += "\n    group by a.houseid,a.wareid";

		qry += "\n    union all  ";
		// 采购入库amount>0
		qry += "\n    select a.houseid,b.wareid,0 as amountqc,0 as currqc,0 as currqc1,sum(b.amount) as amountrk,SUM(b.curr) as currrk,sum(b.amount*b.price1) as currrk1,0 as amountrt,0 as amountck ";
		qry += "\n    ,0 as amountpy,0 as amountpk";
		qry += "\n    from wareinh a join wareinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and a.ntid=0 ";// and b.amount>0 ";
		qry += "\n    and exists (select 1 from accreg x where x.accid=a.accid and substr(x.qxcs,2,1)='1')";
		qry += "\n    and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss' ))";
		qry += "\n    group by a.houseid,b.wareid  ";
		// 采购入库amount<0
		// qry += "\n union all ";
		// qry += "\n select a.houseid,b.wareid,0 as amountqc,0 as currqc,0 as
		// currqc1,0 as amountrk,0 as currrk,0 as currrk1,-sum(b.amount) as
		// amountrt,0 as amountck ";
		// qry += "\n ,0 as amountpy,0 as amountpk";
		// qry += "\n from wareinh a join wareinm b on a.accid=b.accid and
		// a.noteno=b.noteno ";
		// qry += "\n where a.statetag=1 and a.ntid=0 and b.amount<0 ";
		// qry += "\n and exists (select 1 from accreg x where x.accid=a.accid
		// and substr(x.qxcs,2,1)='1')";
		// qry += "\n and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd')
		// and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		// if (accid > 0)
		// qry += " and a.accid=" + accid;
		// // else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss' ))";
		// qry += "\n group by a.houseid,b.wareid ";

		// 采购退货
		qry += "\n    union all  ";
		qry += "\n    select a.houseid,b.wareid,0 as amountqc,0 as currqc,0 as currqc1,0 as amountrk,0 as currrk,0 as currrk1,sum(b.amount) as amountrt ,0 as amountck";
		qry += "\n    ,0 as amountpy,0 as amountpk";
		qry += "\n    from wareinh a join wareinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and a.ntid=1 ";
		qry += "\n    and exists (select 1 from accreg x where x.accid=a.accid and substr(x.qxcs,2,1)='1')";
		qry += "\n    and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss' ))";
		qry += "\n    group by a.houseid,b.wareid  ";

		// 销售出库
		qry += "\n    union all  ";
		qry += "\n    select a.houseid,b.wareid,0 as amountqc,0 as currqc,0 as currqc1,0 as amountrk,0 as currrk,0 as currrk1,0 as amountrt,sum(case a.ntid when 2 then -b.amount else b.amount end) as amountck ";
		qry += "\n    ,0 as amountpy,0 as amountpk";
		qry += "\n    from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 ";
		qry += "\n    and exists (select 1 from accreg x where x.accid=a.accid and substr(x.qxcs,2,1)='1')";
		qry += "\n    and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss' ))";
		qry += "\n    group by a.houseid,b.wareid  ";

		// 商场销售
		qry += "\n    union all  ";
		qry += "\n    select a.houseid,b.wareid,0 as amountqc,0 as currqc,0 as currqc1,0 as amountrk,0 as currrk,0 as currrk1,0 as amountrt,sum(b.amount) as amountck ";
		qry += "\n    ,0 as amountpy,0 as amountpk";
		qry += "\n    from shopsaleh a join shopsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 ";
		qry += "\n    and exists (select 1 from accreg x where x.accid=a.accid and substr(x.qxcs,2,1)='1')";
		qry += "\n    and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss' ))";
		qry += "\n    group by a.houseid,b.wareid  ";

		// 盘点
		qry += "\n    union all  ";
		qry += "\n    select a.houseid,b.wareid,0 as amountqc,0 as currqc,0 as currqc1,0 as amountrk,0 as currrk,0 as currrk1,0 as amountrt,0 as amountck ";
		qry += "\n    ,sum(case when b.amount>0 then b.amount else 0 end) as amountpy,sum(case when b.amount<0 then -b.amount else 0 end) as amountpk";
		qry += "\n    from warecheckh a join warecheckm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and (b.amount>0 or b.amount<0) ";
		qry += "\n    and exists (select 1 from accreg x where x.accid=a.accid and substr(x.qxcs,2,1)='1')";
		qry += "\n    and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		qry += "\n    group by a.houseid,b.wareid  ";
		// 调拨入库
		qry += "\n    union all  ";
		qry += "\n    select a.houseid,b.wareid,0 as amountqc,0 as currqc,0 as currqc1,0 as amountrk,0 as currrk,0 as currrk1,0 as amountrt,sum(-b.amount) as amountck ";
		qry += "\n    ,0 as amountpy,0 as amountpk";
		qry += "\n    from allotinh a join allotinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 ";
		qry += "\n    and exists (select 1 from accreg x where x.accid=a.accid and substr(x.qxcs,2,1)='1')";
		qry += "\n    and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss' ))";
		qry += "\n    group by a.houseid,b.wareid  ";

		// 调拨出库
		qry += "\n    union all  ";
		qry += "\n    select a.houseid,b.wareid,0 as amountqc,0 as currqc,0 as currqc1,0 as amountrk,0 as currrk,0 as currrk1,0 as amountrt,sum(b.amount) as amountck ";
		qry += "\n    ,0 as amountpy,0 as amountpk";
		qry += "\n    from allotouth a join allotoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 ";
		qry += "\n    and exists (select 1 from accreg x where x.accid=a.accid and substr(x.qxcs,2,1)='1')";
		qry += "\n    and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss' ))";
		qry += "\n    group by a.houseid,b.wareid  ";

		qry += "\n    union all  ";
		// 期初入库
		qry += "\n    select a.houseid,b.wareid,0 as amountqc,0 as currqc,0 as currqc1,sum(b.amount) as amountrk,SUM(b.curr) as currrk,sum(b.curr) as currrk1,0 as amountrt,0 as amountck ";
		qry += "\n    ,0 as amountpy,0 as amountpk";
		qry += "\n    from firsthouseh a join firsthousem b on a.accid=b.accid and a.noteno=b.noteno   ";
		qry += "\n    where a.statetag=1 and a.ntid=0 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		qry += "\n    and exists (select 1 from accreg x where x.accid=a.accid and substr(x.qxcs,2,1)='1')";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss' ))";
		qry += "\n    group by a.houseid,b.wareid ) group by houseid,wareid) a ";

		qry += "\n    left outer join warecost b on a.wareid=b.wareid and b.daystr='" + nowdate0 + "' and b.houseid=a.houseid";
		qry += "\n    where exists (select 1 from accreg x join warecode y on x.accid=y.accid where a.wareid=y.wareid and substr(x.qxcs,2,1)='1')";
		if (accid > 0)
			qry += "\n    and exists (select 1 from warecode b1 where b1.accid=" + accid + " and b1.wareid=a.wareid ) ";
		qry += "\n    ;";

		qry += "\n    v_row cur_data%rowtype; ";
		qry += "\n  begin ";
		qry += "\n    v_count:=0;";
		qry += "\n    for v_row in cur_data loop ";
		qry += "\n       v_costprice:=v_row.costprice;";
		qry += "\n       v_costprice1:=v_row.costprice1; ";
		// qry += "\n if v_row.amountqc>0 then ";
		// qry += "\n v_costprice:=round(v_row.currqc/v_row.amountqc,2); ";//
		// --期初成本
		// qry += "\n v_costprice1:=round(v_row.currqc1/v_row.amountqc,2); ";//
		// --期初配送成本
		// qry += "\n end if; ";
		qry += "\n       v_amount:=v_row.amountqc-v_row.amountrt-v_row.amountpk; "; // 减采购退货数量
		// 2017-03-19
		// qry += "\n if v_amount<0 then ";
		// qry += "\n v_amount:=0; ";
		// qry += "\n end if; ";
		// 计算加权成本
		qry += "\n       v_curr:=round(v_amount*v_costprice,2)+v_row.currrk; ";
		qry += "\n       v_curr1:=round(v_amount*v_costprice1,2)+v_row.currrk1; ";
		qry += "\n       v_amount:=v_amount+v_row.amountrk; ";
		qry += "\n       if v_amount>0 then ";
		qry += "\n          v_costprice:=round(v_curr/v_amount,2); ";
		qry += "\n          v_costprice1:=round(v_curr1/v_amount,2); ";
		qry += "\n       end if; ";
		// 结存数量及金额
		qry += "\n       v_amount:=v_amount-v_row.amountck+v_row.amountpy; "; // 结存
		// 分店铺计算成本

		// 2017-03-19
		// qry += "\n if v_amount<0 then";
		// qry += "\n v_amount:=0; ";
		// qry += "\n end if; ";
		qry += "\n       v_curr:=v_amount*v_costprice;";

		qry += "\n      delete from warecost where daystr='" + nowdate + "' and wareid=v_row.wareid and houseid=v_row.houseid ; ";
		qry += "\n      insert into warecost (id,daystr,wareid,houseid,amount,curr,costprice,costprice1)  ";
		qry += "\n      values ( WARECOST_ID.NEXTVAL,'" + nowdate + "',v_row.wareid,v_row.houseid,v_amount,v_curr,v_costprice,v_costprice1); ";

		qry += "\n      delete from warecost where daystr='" + nowdate1 + "' and wareid=v_row.wareid and houseid=v_row.houseid ; ";
		qry += "\n      insert into warecost (id,daystr,wareid,houseid,amount,curr,costprice,costprice1)  ";
		qry += "\n      values ( WARECOST_ID.NEXTVAL,'" + nowdate1 + "',v_row.wareid,v_row.houseid,v_amount,v_curr,v_costprice,v_costprice1); ";
		qry += "\n      v_count:=v_count+1;";
		qry += "\n      if v_count>1000 then ";
		qry += "\n         commit;";
		qry += "\n         v_count:=0; ";
		qry += "\n      end if;";
		qry += "\n    end loop; ";
		qry += "\n  end;     ";
		qry += "\n   commit; ";
		qry += "\n end;";
		// System.out.println("111111111");
		LogUtils.LogDebugWrite("mytotalcost", qry);
		DbHelperSQL db = new DbHelperSQL();
		int ret = db.ExecuteSql(qry, 1800);
		if (ret < 0)
			errmess = db.getErrmess();
		else
			errmess = "计算成功！";
		return ret;

		// int ret = DbHelperSQL.ExecuteSql(qry);
		// if (ret == 0)
		// errmess = "计算失败";
		// else
		// errmess = "计算成功！";
		// return ret;
	}

	// 未用
	public int myTotalWareout(String nowdate, long accid) {
		if (accid == 0)
			accid = 3464;
		if (accid != 3464)// 让爱回家
		{
			errmess = "计算成功！";
			return 1;
		}
		// 只有让爱回家计算订货余额
		Date nowday = Func.StrToDate(nowdate, "yyyy-MM-dd");

		String nowdate0 = Func.DateToStr(Func.getPrevDay(nowday), "yyyy-MM-dd"); // nowday.AddDays(-1).ToString("yyyy-MM-dd");
		String nowdate1 = Func.DateToStr(Func.getNextDay(nowday), "yyyy-MM-dd"); // nowday.AddDays(1).ToString("yyyy-MM-dd");
		// System.out.println(nowdate + " " + nowdate0 + " " + nowdate1);
		String qry = "declare  ";
		qry += "\n    v_nowdate varchar2(10); ";
		qry += "\n    v_nowdate0 varchar2(10); ";
		qry += "\n    v_count number; ";
		qry += "\n begin ";

		// 汇总客户订货余额begin
		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n       SELECT  ROWID from  custorderbal a where daystr='" + nowdate + "' ";
		if (accid > 0)
			qry += "\n    and exists (select 1 from warecode b where b.accid=" + accid + " and b.wareid=a.wareid ) ";
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 5000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from custorderbal  where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		// --从临时表里添加记录
		qry += "\n    declare cursor cur_tempdate is ";
		qry += "\n    select custid,wareid,colorid,sizeid,sum(amount) as amount  ";
		qry += "\n    from ( ";
		/*--取期初*/
		qry += "\n    select custid,wareid,colorid,sizeid,amount from custorderbal a where daystr='" + nowdate0 + "' and (amount>0 or amount<0) ";
		if (accid > 0)
			qry += "\n    and exists (select 1 from warecode b where b.accid=" + accid + " and b.wareid=a.wareid ) ";
		// 客户订货
		qry += "\n    union all ";
		qry += "\n    select a.custid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amount";// ,SUM(b.curr)
																							// as
																							// curr
																							// ";
		qry += "\n    from custorderh a join custorderm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		qry += "\n    group by a.custid,b.wareid,b.colorid,b.sizeid ";

		/*--销售出库   */
		qry += "\n    union all ";
		qry += "\n    select a.custid,b.wareid,b.colorid,b.sizeid,-sum(b.amount) as amount";// ,SUM(b.curr)
																							// as
																							// curr
																							// ";
		qry += "\n    from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and a.ntid=1 and b.saleid=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		qry += "\n    and exists (select 1 from custorderh a1 join custorderm b1 on a1.accid=b1.accid and a1.noteno=b1.noteno";
		qry += "\n    where a1.statetag=1 and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a1.accid=" + accid;
		qry += "\n    and b.wareid=b1.wareid and b.colorid=b1.colorid and b.sizeid=b1.sizeid )";
		qry += "\n    group by a.custid,b.wareid,b.colorid,b.sizeid ";

		qry += "\n    ) group by custid,wareid,colorid,sizeid ; ";
		qry += "\n      v_row cur_tempdate%rowtype;";
		qry += "\n    begin ";
		qry += "\n      v_count:=0; ";
		qry += "\n      for v_row in cur_tempdate loop ";
		qry += "\n        delete from custorderbal where daystr='" + nowdate + "' and custid=v_row.custid and wareid=v_row.wareid  and colorid=v_row.colorid and sizeid=v_row.sizeid  ; ";
		qry += "\n        insert into custorderbal (id,daystr,custid,wareid,colorid,sizeid,amount) ";
		qry += "\n        values ( custorderbal_id.nextval,'" + nowdate + "',v_row.custid,v_row.wareid,v_row.colorid,v_row.sizeid,nvl(v_row.amount,0)); ";
		qry += "\n        v_count:=v_count+1; ";
		qry += "\n        if v_count>1000 then ";
		qry += "\n           v_count:=0; ";
		qry += "\n           commit; ";
		qry += "\n        end if; ";
		qry += "\n      end loop; ";
		qry += "\n    end;  ";
		qry += "\n    commit; ";

		// 汇总客户订货余额end

		// 汇总采购订货余额begin
		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n       SELECT  ROWID from  provorderbal a where daystr='" + nowdate + "' ";
		if (accid > 0)
			qry += "\n    and exists (select 1 from warecode b where b.accid=" + accid + " and b.wareid=a.wareid ) ";
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 5000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from provorderbal  where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		// --从临时表里添加记录
		qry += "\n    declare cursor cur_tempdate is ";
		qry += "\n    select provid,wareid,colorid,sizeid,sum(amount) as amount  ";
		qry += "\n    from ( ";
		/*--取期初*/
		qry += "\n    select provid,wareid,colorid,sizeid,amount from provorderbal a where daystr='" + nowdate0 + "' and (amount>0 or amount<0) ";
		if (accid > 0)
			qry += "\n    and exists (select 1 from warecode b where b.accid=" + accid + " and b.wareid=a.wareid ) ";
		// 采购订货
		qry += "\n    union all ";
		qry += "\n    select a.provid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amount";// ,SUM(b.curr)
		qry += "\n    from provorderh a join provorderm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		qry += "\n    group by a.provid,b.wareid,b.colorid,b.sizeid ";

		// 采购入库
		qry += "\n    union all ";
		qry += "\n    select a.provid,b.wareid,b.colorid,b.sizeid,-sum(b.amount) as amount";// ,SUM(b.curr)
		qry += "\n    from wareinh a join wareinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and a.ntid=0 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		qry += "\n    and exists (select 1 from provorderh a1 join provorderm b1 on a1.accid=b1.accid and a1.noteno=b1.noteno";
		qry += "\n    where a1.statetag=1 and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a1.accid=" + accid;
		qry += "\n    and b.wareid=b1.wareid and b.colorid=b1.colorid and b.sizeid=b1.sizeid )";
		qry += "\n    group by a.provid,b.wareid,b.colorid,b.sizeid ";

		qry += "\n    ) group by provid,wareid,colorid,sizeid ; ";
		qry += "\n      v_row cur_tempdate%rowtype;";
		qry += "\n    begin ";
		qry += "\n      v_count:=0; ";
		qry += "\n      for v_row in cur_tempdate loop ";
		qry += "\n        delete from provorderbal where daystr='" + nowdate + "' and provid=v_row.provid and wareid=v_row.wareid  and colorid=v_row.colorid and sizeid=v_row.sizeid  ; ";
		qry += "\n        insert into provorderbal (id,daystr,provid,wareid,colorid,sizeid,amount) ";
		qry += "\n        values ( provorderbal_id.nextval,'" + nowdate + "',v_row.provid,v_row.wareid,v_row.colorid,v_row.sizeid,nvl(v_row.amount,0)); ";
		qry += "\n        v_count:=v_count+1; ";
		qry += "\n        if v_count>1000 then ";
		qry += "\n           v_count:=0; ";
		qry += "\n           commit; ";
		qry += "\n        end if; ";
		qry += "\n      end loop; ";
		qry += "\n    end;  ";
		qry += "\n    commit; ";

		// 汇总采购订货余额end

		// 汇总调拨订货余额begin
//		qry += "\n    declare  ";
//		qry += "\n       cursor mycursor is ";
//		qry += "\n       SELECT  ROWID from  allotorderbal a where daystr='" + nowdate + "' ";
//		if (accid > 0)
//			qry += "\n    and exists (select 1 from warecode b where b.accid=" + accid + " and b.wareid=a.wareid ) ";
//		qry += "\n       order by rowid; ";
//		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
//		qry += "\n       v_rowid   rowid_table_type; ";
//		qry += "\n    BEGIN ";
//		qry += "\n       open mycursor; ";
//		qry += "\n       loop ";
//		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 5000;  ";// --------每次处理5000行，也就是每5000行一提交
//		qry += "\n          exit when v_rowid.count=0; ";
//		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
//		qry += "\n          delete from allotorderbal  where rowid=v_rowid(i); ";
//		qry += "\n          commit; ";
//		qry += "\n        end loop; ";
//		qry += "\n       close mycursor; ";
//		qry += "\n    END; ";
//
//		// --从临时表里添加记录
//		qry += "\n    declare cursor cur_tempdate is ";
//		qry += "\n    select houseid,wareid,colorid,sizeid,sum(amount) as amount  ";
//		qry += "\n    from ( ";
//		/*--取期初*/
//		qry += "\n    select houseid,wareid,colorid,sizeid,amount from allotorderbal a where daystr='" + nowdate0 + "' and (amount>0 or amount<0) ";
//		if (accid > 0)
//			qry += "\n    and exists (select 1 from warecode b where b.accid=" + accid + " and b.wareid=a.wareid ) ";
//		// 调拨订货
//		qry += "\n    union all ";
//		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amount";// ,SUM(b.curr)
//																							// as
//																							// curr
//																							// ";
//		qry += "\n    from allotorderh a join allotorderm b on a.accid=b.accid and a.noteno=b.noteno  ";
//		qry += "\n    where a.statetag=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
//		if (accid > 0)
//			qry += " and a.accid=" + accid;
//		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid ";
//
//		// 调拨出库
//		qry += "\n    union all ";
//		qry += "\n    select a.tohouseid,b.wareid,b.colorid,b.sizeid,-sum(b.amount) as amount";// ,SUM(b.curr)
//																								// as
//																								// curr
//																								// ";
//		qry += "\n    from allotouth a join allotoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
//		qry += "\n    where a.statetag=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
//		if (accid > 0)
//			qry += " and a.accid=" + accid;
//		qry += "\n    and exists (select 1 from allotorderh a1 join allotorderm b1 on a1.accid=b1.accid and a1.noteno=b1.noteno";
//		qry += "\n    where a1.statetag=1 and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
//		if (accid > 0)
//			qry += " and a1.accid=" + accid;
//		qry += "\n    and b.wareid=b1.wareid and b.colorid=b1.colorid and b.sizeid=b1.sizeid )";
//		qry += "\n    group by a.tohouseid,b.wareid,b.colorid,b.sizeid ";
//
//		qry += "\n    ) group by houseid,wareid,colorid,sizeid ; ";
//		qry += "\n      v_row cur_tempdate%rowtype;";
//		qry += "\n    begin ";
//		qry += "\n      v_count:=0; ";
//		qry += "\n      for v_row in cur_tempdate loop ";
//		qry += "\n        delete from allotorderbal where daystr='" + nowdate + "' and houseid=v_row.houseid and wareid=v_row.wareid  and colorid=v_row.colorid and sizeid=v_row.sizeid  ; ";
//		qry += "\n        insert into allotorderbal (id,daystr,houseid,wareid,colorid,sizeid,amount) ";
//		qry += "\n        values ( allotorderbal_id.nextval,'" + nowdate + "',v_row.houseid,v_row.wareid,v_row.colorid,v_row.sizeid,nvl(v_row.amount,0)); ";
//		qry += "\n        v_count:=v_count+1; ";
//		qry += "\n        if v_count>1000 then ";
//		qry += "\n           v_count:=0; ";
//		qry += "\n           commit; ";
//		qry += "\n        end if; ";
//		qry += "\n      end loop; ";
//		qry += "\n    end;  ";
//		qry += "\n    commit; ";

		// 汇总调拨订货余额end

		qry += "\n  end;";

		DbHelperSQL db = new DbHelperSQL();
		int ret = db.ExecuteSql(qry, 3600);
		if (ret < 0) {
			ret = 0;
			errmess = db.getErrmess();
		} else {
			ret = 1;
			errmess = "计算成功！";
		}
		return ret;

	}

	/// <summary>
	/// 取客户商品售价
	/// </summary>

	public static String myWareoutprice(long accid, long wareid, long custid, long guestid, long houseid, long saleid, int priceprec, int nearsaleok, int thbj) {
		// guestid与custid不能同时有效

		// System.out.println("saleid="+saleid);
		String qry = "declare";
		qry += "\n   v_pricetype number(1); ";
		qry += "\n   v_brandid number(10); ";
		qry += "\n   v_price number(14,2); ";
		qry += "\n   v_price0 number(14,2); ";
		qry += "\n   v_price1 number(14,2); ";
		qry += "\n   v_discount number(14,2); ";
		qry += "\n   v_discount1 number(14,2); ";
		qry += "\n   v_tjtag number(1); ";
		qry += "\n   v_count number(10); ";
		qry += "\n   vt_price1 number(14,2); vt_discount  number(14,2); vt_price  number(14,2); ";
		qry += "\n begin ";
		// 1.价格方式 pricetype: 0=零价折扣,1=售价1,2=售价2,3=售价3,4=批发价,5=打包价,6=品牌价格
		if (custid > 0) {// 批发，退货
			qry += "\n   select pricetype,discount into v_pricetype,v_discount from customer where custid=" + custid + " and accid=" + accid + "; ";

			// 1.如果有品牌价格折扣，就取该客户的品牌价格及折扣
			qry += "\n   select brandid into v_brandid from warecode where accid=" + accid + " and wareid=" + wareid + ";";
			qry += "\n   select count(*) into v_count from custbrand where custid=" + custid + " and brandid=v_brandid; ";
			qry += "\n   if v_count>0 then ";
			qry += "\n      select pricetype,discount into v_pricetype,v_discount from custbrand where custid=" + custid + " and brandid=v_brandid; ";
			qry += "\n   end if;";

			qry += "\n   select case v_pricetype when 0 then b.retailsale  when 1 then b.sale1 when 2 then b.sale2 when 3 then b.sale3 when 4 then b.sale4 when 5 then b.sale5 else b.retailsale end,b.tjtag";
			qry += "\n   into v_price0,v_tjtag";
			qry += "\n   from warecode b where accid=" + accid + " and wareid=" + wareid + ";";
			qry += "\n   if v_tjtag=1 then"; // 特价商品不允许打折
			qry += "\n      v_discount:=1; v_price:=v_price0; ";
			qry += "\n      goto exit;";
			qry += "\n   end if;";

			qry += "\n   v_price:=round(v_price0*v_discount," + priceprec + ");"; // 没有调价记录，单价=原价*折扣
			if (thbj == 1) { // 如果是退货，取最近一次的销售价
				// 0=零售 1=定单 2=首单 3=补货 4=换货 5=代销
				// long saleid0 = saleid;
				// if (saleid0 == 3)
				// saleid0 = 2; // 如果是补货，则按首单取最近售价
				qry += "\n   begin";
				qry += "\n     select price into v_price1 from (";
				qry += "\n     select b.price from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
				qry += "\n     where a.accid=" + accid + " and a.statetag=1 and a.cleartag=0 and a.ntid=1 and b.wareid=" + wareid;
//				if (saleid == 3 || saleid == 2)
//					qry += "\n     and (b.saleid=2 or b.saleid=3)";
//				else
//					qry += "\n     and b.saleid=" + saleid;

				qry += "\n     and a.custid=" + custid + " order by a.notedate desc,b.id desc";
				qry += "\n     ) where rownum=1;";
				qry += "\n     v_price:=v_price1;";
				qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN  ";
				qry += "\n     v_price1:=v_price;"; // 没有调价记录，单价=原价*折扣
				qry += "\n   end;";
			} else {

				// 2.查找是否有调价记录（裴晓要求批发单也要取调价单记录）
				qry += "\n   begin";

				// qry += "\n select nvl(price,v_price0) into v_price1 from (
				// select b.retailsale as price";

				qry += "\n     select price into v_price1 from ( ";
				qry += "\n     select case v_pricetype when 0 then b.retailsale  when 1 then b.sale1 when 2 then b.sale2 when 3 then b.sale3 when 4 then b.sale4 when 5 then b.sale5 else b.retailsale end as price";
				qry += "\n     from wareadjusth a join wareadjustm b on a.accid=b.accid and a.noteno=b.noteno";
				qry += "\n     where a.statetag=2";
				qry += "\n     and a.begindate<to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')+1";
				qry += "\n     and a.enddate>=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') ";
				if (houseid > 0)
					qry += "\n   and exists (select 1 from wareadjustc c where a.accid=c.accid and a.noteno=c.noteno and c.houseid=" + houseid + ")";
				qry += "\n     and b.wareid=" + wareid + " and a.accid=" + accid + " order by a.notedate desc ) where rownum=1;";

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
				if (nearsaleok == 1 && saleid > 0) {
					// 0=零售 1=定单 2=首单 3=补货 4=换货 5=代销
					// long saleid0 = saleid;
					// if (saleid0 == 3)
					// saleid0 = 2; // 如果是补货，则按首单取最近售价

					// qry += "\n select count(*) into v_count from nearsale
					// where wareid=" + wareid + " and custid=" + custid + " and
					// saleid=" + saleid0 + ";";
					// qry += "\n if v_count>0 then ";
					// qry += "\n select price0,discount,price into
					// v_price1,v_discount,v_price from nearsale where wareid="
					// + wareid + " and custid=" + custid + " and saleid=" +
					// saleid0 + ";";
					// qry += "\n if v_price0<>v_price1 then";
					// qry += "\n v_discount:=case v_price0 when 0 then 1 else
					// round(v_price/v_price0,2) end;";
					// qry += "\n end if;";
					// qry += "\n end if;";
					//取最近售价
					qry += "\n   begin";
					qry += "\n     select price0,discount,price into vt_price1,vt_discount,vt_price from (";
					qry += "\n     select b.price0,b.discount,b.price from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
					qry += "\n     where a.accid=" + accid + " and a.statetag=1 and a.ntid=1 and a.cleartag=0 and b.wareid=" + wareid + " and a.custid=" + custid;

//					if (saleid == 3 || saleid == 2)
//						qry += "\n     and (b.saleid=2 or b.saleid=3)";
//					else
//						qry += "\n     and b.saleid=" + saleid;

					qry += "\n     order by a.notedate desc,b.id desc";
					qry += "\n     ) where rownum=1;";
					// qry += "\n if vt_price1<>null then";
					qry += "\n     v_price1:=vt_price1;v_discount:=vt_discount;v_price:=vt_price;";
					qry += "\n     if v_price0<>v_price1  then";
					qry += "\n        v_discount:=case v_price0 when 0 then 1 else round(v_price/v_price0,2) end;";
					qry += "\n     end if;";
					// qry += "\n end if;";
					qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN  ";
					qry += "\n     vt_price1:=0;"; // 没有调价记录，单价=原价*折扣
					qry += "\n   end;";

				}
			}
		} else {// 零售
		
			// 0.取商品档案零售价
			qry += "\n   v_pricetype:=0;";
			qry += "\n   v_discount:=1;";
			if (houseid > 0)
				qry += "\n  select pricetype into v_pricetype from warehouse where houseid=" + houseid + ";";
			qry += "\n   select case v_pricetype when 1 then b.sale1 when 2 then b.sale2 when 3 then b.sale3 when 4 then b.sale4 when 5 then b.sale5 else b.retailsale end,b.tjtag into v_price0,v_tjtag";
			qry += "\n   from warecode b where accid=" + accid + " and wareid=" + wareid + ";";
			qry += "\n   if v_tjtag=1 then"; // 特价商品不允许打折
			qry += "\n      v_discount:=1; v_price:=v_price0; ";
			qry += "\n      goto exit;";
			qry += "\n   end if;";

			// 1.取商品店铺零售价
			if (houseid > 0) {

				qry += "\n  begin ";
				qry += "\n    select nvl(retailsale,v_price0) into v_price1 from housesaleprice where houseid=" + houseid + " and wareid=" + wareid + ";";
				qry += "\n    v_price0:=v_price1;";
				qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN  ";
				qry += "\n    v_price1:=0;";
				qry += "\n  end;";
			}
			qry += "\n   v_price:=round(v_price0*v_discount," + priceprec + ");"; // 没有调价记录，单价=原价*折扣

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
			qry += "\n     and b.wareid=" + wareid + " and a.accid=" + accid + " order by a.notedate desc ) where rownum=1;";

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

			if (guestid > 0) // 如果传入了会员id,要取会员的折扣
			{
				// 取会员折扣
				qry += "\n   v_discount:=1;";
				qry += "\n   begin";
				qry += "\n     select b.discount into v_discount1 from guestvip a join guesttype b on a.vtid=b.vtid where a.guestid=" + guestid + ";";
				qry += "\n     v_discount:=v_discount1;"; // 没有调价记录，单价=原价*折扣
				qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN  ";
				qry += "\n     v_discount:=1;";
				qry += "\n   end;";
				// qry += "\n v_discount:=v_vipdiscount;";
				qry += "\n   v_price1:=round(v_price0*v_discount," + priceprec + ");"; // 会员折扣价
				qry += "\n   if v_price>v_price1 then "; // 如果会员折后价小于零售价，则取会员折后价
				qry += "\n      v_price:=v_price1; v_discount:=0; ";
				qry += "\n      if v_price0>0 then";
				qry += "\n         v_discount:=round(v_price/v_price0,2); "; // 有调价记录,折扣=单价/原价
				qry += "\n      end if;";
				qry += "\n   end if;";
			}

		}
		qry += "\n   <<exit>>";
		qry += "\n   select v_price0,v_discount,v_price into :price0,:discount,:price from dual; ";
		qry += "\n end; ";
//		 System.out.println(qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("price0", new ProdParam(Types.FLOAT));
		param.put("discount", new ProdParam(Types.FLOAT));
		param.put("price", new ProdParam(Types.FLOAT));

		int ret = DbHelperSQL.ExecuteProc(qry, param);
		String retcs = "";
		if (ret < 0) {
			// errmess = "操作异常！";
		} else {

			retcs = "\"PRICE0\":\"" + param.get("price0").getParamvalue().toString() + "\"" //
					+ ",\"DISCOUNT\":\"" + param.get("discount").getParamvalue().toString() + "\""//
					+ ",\"PRICE\":\"" + param.get("price").getParamvalue().toString() + "\"";
		}
		// System.out.println(retcs);
		return retcs;
	}

	/// <summary>
	/// 将单据按尺码横向展开
	/// </summary>
	/// <param name="wareid"></param>
	/// <returns></returns>
	public static int NotetoSize(long accid, long epid, String tablename, String noteno, int priceprec)

	{
		String qry = "declare ";
		tablename = tablename.toUpperCase();

		qry += "\n  v_noteno varchar2(20);";
		qry += "\n  v_accid number(10); ";
		qry += "\n  v_wareid number(10); ";
		qry += "\n  v_i number(10); ";
		qry += "\n  v_sqlsum clob; ";
		qry += "\n  v_sqlsize clob; ";
		qry += "\n  v_sqlqry clob; ";
		qry += "\n  v_epid number(10); ";
		qry += "\n begin ";

		/*
		 * qry += "  v_noteno:='" + noteno + "'; "; qry +=
		 * "  v_accid:="+accid+"; "; qry += "  v_epid:="+epid+"; ";
		 */
		qry += "\n  delete from v_notesize_data where epid=" + epid + "; ";
		qry += "\n  delete from v_notesize1_data where epid=" + epid + "; ";
		qry += "\n  declare cursor cur_datemx is ";
		qry += "\n    select a.wareid,count(*) as num ";
		qry += "\n    from " + tablename + " a join warecode b on a.wareid=b.wareid  ";
		qry += "\n    where a.accid=" + accid + " and a.noteno='" + noteno + "' ";
		qry += "\n    group by a.wareid; ";
		qry += "\n    v_row cur_datemx%rowtype; ";
		qry += "\n  begin ";
		qry += "\n    for v_row in cur_datemx loop ";
		qry += "\n      v_wareid:=v_row.wareid; ";
		// --查找尺码
		qry += "\n      declare cursor cur_sizecode is  ";
		qry += "\n        select a.sizeid,a.sizename from sizecode a  ";
		qry += "\n        join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno  ";
		qry += "\n        where b.wareid=v_wareid  and a.statetag=1 and a.noused=0 and rownum<= " + pFunc.MAX_SIZE;
		qry += "\n        order by a.sizeno,a.sizeid  ; ";
		qry += "\n        v_row1 cur_sizecode%rowtype; ";
		qry += "\n      begin ";
		qry += "\n        v_sqlsum:=''; v_sqlsize:='';";
		qry += "\n        v_i:=1; ";
		qry += "\n        for v_row1 in cur_sizecode loop ";
		qry += "\n           v_sqlsum:=v_sqlsum||',sum(case a.sizeid when '||v_row1.sizeid||' then a.amount else 0 end) as amount'||v_i; ";
		qry += "\n           v_sqlsize:=v_sqlsize||','''||v_row1.sizename||'''';";
		qry += "\n           v_i:=v_i+1; ";
		qry += "\n        end loop; ";
		qry += "\n        while v_i<=" + pFunc.MAX_SIZE + " loop ";
		qry += "\n           v_sqlsum:=v_sqlsum||',0 as amount'||v_i; ";
		qry += "\n           v_sqlsize:=v_sqlsize||','' ''';";
		qry += "\n           v_i:=v_i+1;  ";
		qry += "\n        end loop; ";
		// qry += "\n v_sqlsum:=v_sqlsum||',sum(a.amount) as amount,sum(a.curr)
		// as curr,case sum(a.amount) when 0 then 0 else
		// round(sum(a.curr)/sum(a.amount)," + priceprec + ") end as price'; ";
		qry += "\n        v_sqlsum:=v_sqlsum||',min(a.id) as minid,min(a.remark0) as remark0,sum(a.amount) as amount,sum(a.curr) as curr,case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount)," + priceprec
				+ ") end as price'; ";
		if (tablename.equals("WAREINM") || tablename.equals("WAREOUTM") || tablename.equals("CUSTORDERM") || tablename.equals("PROVORDERM"))
			qry += "\n       v_sqlsum:=v_sqlsum||',sum(a.amount*a.price0) as curr0,case sum(a.amount) when 0 then 0 else round(sum(a.amount*a.price0)/sum(a.amount)," + priceprec + ") end as price0';";
		else
			qry += "\n       v_sqlsum:=v_sqlsum||',0 as curr0,0 as price0';";
		if (tablename.equals("SHOPSALEM") || tablename.equals("WAREOUTM"))
			qry += "\n   v_sqlsum:=v_sqlsum||',min(tjtag) as tjtag';";
		else
			qry += "\n  v_sqlsum:=v_sqlsum||',0 as tjtag';";

		qry += "\n      end; ";
		qry += "\n      v_sqlqry:='declare '; ";
		qry += "\n      v_sqlqry:=v_sqlqry||' begin '; ";
		qry += "\n      v_sqlqry:=v_sqlqry||'   insert into v_notesize1_data (epid,wareid,size1,size2,size3,size4,size5,size6,size7,size8,size9,size10,size11,size12,size13,size14,size15)'; ";
		qry += "\n      v_sqlqry:=v_sqlqry||'   select '||" + epid + "||','||v_wareid||v_sqlsize||' from dual;'; ";
		qry += "\n      v_sqlqry:=v_sqlqry||'   insert into v_notesize_data (id,epid,noteno,minid,wareid,colorid,saleid,amount1,amount2,amount3,amount4,amount5,amount6,amount7'; ";
		qry += "\n      v_sqlqry:=v_sqlqry||'              ,amount8,amount9,amount10,amount11,amount12,amount13,amount14,amount15,amount,price,curr,price0,curr0,discount,remark0,tjtag)'; ";
		qry += "\n      v_sqlqry:=v_sqlqry||'   select v_notesize_data_id.nextval,'||" + epid + "||','''||'" + noteno + "'||''',minid,wareid,colorid,saleid,amount1,amount2,amount3,amount4,amount5,amount6,amount7'; ";
		qry += "\n      v_sqlqry:=v_sqlqry||'             ,amount8,amount9,amount10,amount11,amount12,amount13,amount14,amount15,amount,price,curr,price0,curr0,case curr0 when 0 then 1 else curr/curr0 end,remark0,tjtag from ('; ";
		qry += "\n      v_sqlqry:=v_sqlqry||'   select a.wareid,a.colorid'; ";
		if (tablename.equals("WAREOUTM"))
			qry += "      v_sqlqry:=v_sqlqry||'   ,a.saleid'; ";
		else
			qry += "\n      v_sqlqry:=v_sqlqry||'   ,0 as saleid'; ";
		qry += "\n      v_sqlqry:=v_sqlqry||     v_sqlsum; ";
		qry += "\n      v_sqlqry:=v_sqlqry||'    from " + tablename + " a where a.accid='||" + accid + "||' and a.noteno='''||'" + noteno + "'||''' and a.wareid='||v_wareid; ";
		qry += "\n      v_sqlqry:=v_sqlqry||'    group by a.wareid,a.colorid'; ";
		if (tablename.equals("WAREOUTM"))
			qry += "      v_sqlqry:=v_sqlqry||'   ,a.saleid'; ";
		qry += "\n      v_sqlqry:=v_sqlqry||' );'; ";
		qry += "\n      v_sqlqry:=v_sqlqry||'  end; '; ";

		// --dbms_output.put_line(v_sqlqry);
		qry += "\n      execute immediate v_sqlqry; ";
		qry += "\n    end loop; ";
		qry += "\n  end; ";
		qry += "\n  commit; ";

		qry += "\n end; ";
		// System.out.println(qry);
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			// errmess = "操作失败！";
			return 0;
		} else {
			// errmess = "操作成功！";
			return 1;
		}
	}
}
