/**
 * 
 */
package com.oracle.bean;

import com.comdot.data.Table;
import com.common.tool.Func;

import com.netdata.db.DbHelperSQL;
import com.netdata.db.QueryParam;

import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
public class vTotalJxchz {

	private String Fieldlist = ""; // 要显示的项目列表
	private String Grouplist = ""; // 分组的项目列表
	private String Sortlist = ""; // 排序的项目列表
	private String Sumlist = ""; // 要求和的项目列表
	private String Calcfield = ""; // 要求和的项目列表

	private Long Accid;// 账户id
	private Long Userid;// 用户id

	private String Serverdatetime = "";// 服务器当前时间,-------------必传
	private String Accbegindate = "";// 账套启用日期
	private Integer Priceprec = 0;
	private Integer Jgfs = 0; // jgfs:0=不显示，1=成本价，2=配送价

	private Long Wareid = (long) 0;//
	private Long Colorid = (long) 0;//
	private Long Sizeid = (long) 0;//
	private Long Provid = (long) -1;//
	private Long Areaid = (long) -1;//
	private Long Typeid = (long) -1;//
	private String Locale = "";
	private String Brandidlist = "";//
	private String Houseidlist = "";//
	private String Typeidlist = "";//
	private String Providlist = "";//
	private String Areaidlist = "";//
	private String Wareno = "";//
	private String Warename = "";//
	private String Seasonname = "";//
	private String Prodyear = "";//

	private String Mindate;
	private String Maxdate;

	private String Calcdate = "";

	public void setMaxdate(String maxdate) {
		Maxdate = maxdate;
	}

	public void setMindate(String mindate) {
		Mindate = mindate;
	}

	public void setCalcdate(String calcdate) {
		Calcdate = calcdate;
	}

	private String errmess;

	public String getErrmess() {
		return errmess;
	}

	public void setPriceprec(Integer priceprec) {
		Priceprec = priceprec;
	}

	public void setServerdatetime(String serverdatetime) {
		Serverdatetime = serverdatetime;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	public void setUserid(Long userid) {
		Userid = userid;
	}

	public void setFieldlist(String fieldlist) {
		Fieldlist = fieldlist;
	}

	public void setGrouplist(String grouplist) {
		Grouplist = grouplist;
	}

	public void setSortlist(String sortlist) {
		Sortlist = sortlist;
	}

	public void setSumlist(String sumlist) {
		Sumlist = sumlist;
	}

	public void setCalcfield(String calcfield) {
		Calcfield = calcfield;
	}

	// 汇总
	public int Total(int qxbj, int housecostbj, int maxday) {
		// 校验开始查询日期
		MinDateValid mdv = new MinDateValid(Mindate, Func.subString(Serverdatetime, 1, 10), Accbegindate, maxday);
		Mindate = mdv.getMindate();
		String mess = mdv.getErrmess();
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期
		String mindate0 = Func.DateToStr(Func.getPrevDay(Mindate));
		int bj = 0;
		if (Calcdate.length() > 0 && Calcdate.compareTo(mindate0) < 0) {
			// 计算库存
			vTotalCxkczy kc = new vTotalCxkczy();

			kc.setAccid(Accid);
			kc.setUserid(Userid);
			kc.setCxdatetime(mindate0);
			kc.setCalcdate(Calcdate);
			kc.setQxbj(qxbj);
			kc.setWareid(Wareid);
			kc.setColorid(Colorid);
			kc.setSizeid(Sizeid);
			kc.setProdyear(Prodyear);
			kc.setWarename(Warename);
			kc.setWareno(Wareno);
			kc.setSeasonname(Seasonname);
			kc.setHouseidlist(Houseidlist);
			kc.setBrandidlist(Brandidlist);
			kc.setTypeidlist(Typeidlist);
			kc.setAreaidlist(Areaidlist);
			kc.setProvidlist(Providlist);
			kc.setLocale(Locale);
			kc.setAreaid(Areaid);
			kc.setTypeid(Typeid);
			kc.doCxkczy();
			bj = 1;
		}

		String qry = "declare ";
		qry += "\n    v_epid  number; ";
		qry += "\n    v_accid  number; ";
		qry += "\n    v_mindate varchar2(10); ";
		qry += "\n    v_maxdate varchar2(10); ";
		qry += "\n    v_mindate0 varchar2(10); ";
		qry += "\n    v_bj  number(1); ";
		qry += "\n    v_count  number; ";
		qry += "\n begin ";
		// qry += " delete from v_cxjxchz_data where epid=" + userid + "; ";

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n       select ROWID from v_cxjxchz_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid  rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_cxjxchz_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";
		qry += "\n    commit; ";
		// jgfs:0=成本价，1=配送价
		// housecostbj:1=分店铺算成本
		if (bj == 1) {
			qry += "\n    insert into v_cxjxchz_temp (houseid,wareid,colorid,sizeid,amountqc,currqc)  ";
			qry += "\n    select a.houseid,a.wareid,a.colorid,a.sizeid,sum(a.amount) as amountqc ";
			// if (jgfs == 1)
			qry += ",sum(a.amount*c.costprice) as currqc";
			// qry += ",sum(a.amount*c.costprice) as currqm";
			// else if (jgfs == 2) qry += ",sum(a.amount*c.costprice1) as
			// currqc";
			// else qry += ",0 as currqc";
			qry += "\n    from v_cxkczy_data a ";
			qry += "\n    left outer join warecode b on a.wareid=b.wareid ";
			qry += "\n    left outer join warecost c on a.wareid=c.wareid and c.daystr='" + mindate0 + "'";
			if (housecostbj == 1)
				qry += " and a.houseid=c.houseid";
			else
				qry += " and c.houseid=0";
			qry += "\n    where a.epid=" + Userid;

			qry += "\n    group by a.houseid,a.wareid,a.colorid,a.sizeid ; ";

		} else {
			qry += "\n    insert into v_cxjxchz_temp (houseid,wareid,colorid,sizeid,amountqc,currqc)  ";
			qry += "\n    select a.houseid,a.wareid,a.colorid,a.sizeid,sum(a.amount) as amountqc ";
			// if (jgfs == 1)
			qry += ",sum(a.amount*c.costprice) as currqc";
			// qry += ",sum(a.amount*c.costprice) as currqm";
			// else if (jgfs == 2) qry += ",sum(a.amount*c.costprice1) as
			// currqc";
			// else qry += ",0 as currqc";
			qry += "\n    from waresum a ";
			qry += "\n    left outer join warecode b on a.wareid=b.wareid ";
			// if (jgfs > 0)
			// {
			qry += "\n    left outer join warecost c on a.wareid=c.wareid and a.daystr=c.daystr";
			if (housecostbj == 1)
				qry += " and a.houseid=c.houseid";
			else
				qry += " and c.houseid=0";
			// }
			qry += "\n    where a.daystr='" + mindate0 + "' and b.statetag=1";
			qry += "\n    and b.accid=" + Accid + " ";
			if (qxbj == 1) // 1 启用权限控制
			{
				qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += "\n    and (b.brandid=0 or exists (select 1 from employebrand x2 where b.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
			}

			if (Houseidlist.length() > 0)
				qry += "    and " + Func.getCondition("a.houseid", Houseidlist);
			if (Wareid > 0)
				qry += "       and a.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "       and a.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "       and a.sizeid=" + Sizeid;
			if (Wareno.length() > 0)
				qry += "       and b.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "      and b.warename like '%" + Warename + "%'";
			if (Locale.length() > 0)
				qry += "      and b.locale like '%" + Locale + "%'";
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += " and exists (select 1 from waretype t where t.typeid=b.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += " and b.typeid=" + Typeid;
				}

			if (Brandidlist.length() > 0)
				qry += "    and " + Func.getCondition("b.brandid", Brandidlist);

			if (Providlist.length() > 0)
				qry += "    and " + Func.getCondition("b.provid", Providlist);

			if (Areaid >= 0)
				qry += "       and b.areaid=" + Areaid;
			if (Seasonname.length() > 0)
				qry += "       and b.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "       and b.prodyear='" + Prodyear + "'";
			qry += "\n    group by a.houseid,a.wareid,a.colorid,a.sizeid ; ";
		}
		// --初始入库
		qry += "\n    insert into v_cxjxchz_temp (houseid,wareid,colorid,sizeid,amountcs,currcs)  ";
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amountcs ";
		// if (jgfs == 1)
		qry += ",sum(b.amount*b.price) as currcs";
		// qry += ",sum(b.amount*d.costprice) as currqm";
		// else if (jgfs ==2) qry += ",sum(b.amount*d.costprice1) as currcs";
		// else qry += ",0 as currcs";

		qry += "\n    from firsthouseh a  ";
		qry += "\n    join firsthousem b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		// qry += "\n left outer join warecost d on b.wareid=d.wareid and
		// to_char(a.notedate,'yyyy-mm-dd')=d.daystr";
		// if (housecostbj == 1)
		// qry += " and a.houseid=d.houseid";
		// else
		// qry += " and d.houseid=0";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "    and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "       and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "       and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "       and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "       and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "      and c.warename like '%" + Warename + "%'";
		if (Locale.length() > 0)
			qry += "      and c.locale like '%" + Locale + "%'";
		// if (typeid>0 ) qry+=" and b.typeid="+typeid;
		// if (Typeidlist.length() > 0)
		// qry += " and " + Func.getCondition("c.typeid", Typeidlist);
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += " and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += " and c.typeid=" + Typeid;
			}

		if (Providlist.length() > 0)
			qry += "    and " + Func.getCondition("c.provid", Providlist);

		if (Brandidlist.length() > 0)
			qry += "    and " + Func.getCondition("c.brandid", Brandidlist);

		if (Provid >= 0)
			qry += "       and c.provid=" + Provid;
		if (Areaid >= 0)
			qry += "       and c.areaid=" + Areaid;
		if (Seasonname.length() > 0)
			qry += "       and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "       and c.prodyear='" + Prodyear + "'";

		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid; ";

		// --采购入库
		qry += "\n    insert into v_cxjxchz_temp (houseid,wareid,colorid,sizeid,amountcg,currcg)  ";
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amountcg ";
		// if (jgfs == 1)
		qry += ",sum(b.amount*b.price) as currcg";
		// qry += ",sum(b.amount*d.costprice) as currqm";
		// else if (jgfs == 2)
		// qry += ",sum(b.amount*d.costprice1) as currcg";
		// else
		// qry += ",0 as currcg";
		qry += "\n    from wareinh a  ";
		qry += "\n    join wareinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		// if (jgfs > 0) {
		// qry += "\n left outer join warecost d on b.wareid=d.wareid and
		// to_char(a.notedate,'yyyy-mm-dd')=d.daystr";
		// if (housecostbj == 1)
		// qry += " and a.houseid=d.houseid";
		// else
		// qry += " and d.houseid=0";
		// }
		qry += "\n    where a.accid=" + Accid + " and a.statetag=1  and a.ntid=0";
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";

		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "    and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "       and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "       and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "       and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "       and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "      and c.warename like '%" + Warename + "%'";
		if (Locale.length() > 0)
			qry += "      and c.locale like '%" + Locale + "%'";
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += " and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += " and c.typeid=" + Typeid;
			}

		if (Providlist.length() > 0)
			qry += "    and " + Func.getCondition("c.provid", Providlist);

		if (Brandidlist.length() > 0)
			qry += "    and " + Func.getCondition("c.brandid", Brandidlist);

		if (Provid >= 0)
			qry += "       and c.provid=" + Provid;
		if (Areaid >= 0)
			qry += "       and c.areaid=" + Areaid;
		if (Seasonname.length() > 0)
			qry += "       and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "       and c.prodyear='" + Prodyear + "'";

		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid; ";

		// --采购退库
		qry += "\n    insert into v_cxjxchz_temp (houseid,wareid,colorid,sizeid,amountct,currct)  ";
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,-sum(b.amount) as amountct ";
		// if (jgfs == 1)
		qry += ",-sum(b.amount*b.price) as currct";
		// qry += ",-sum(b.amount*d.costprice) as currqm";
		// else if (jgfs == 2)
		// qry += ",-sum(b.amount*d.costprice1) as currct";
		// else
		// qry += ",0 as currct";
		qry += "    from wareinh a  ";
		qry += "    join wareinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "    left outer join warecode c on b.wareid=c.wareid ";
		// + if (jgfs > 0) {
		// qry += " left outer join warecost d on b.wareid=d.wareid and
		// to_char(a.notedate,'yyyy-mm-dd')=d.daystr";
		// if (housecostbj == 1)
		// qry += " and a.houseid=d.houseid";
		// else
		// qry += " and d.houseid=0";
		// }
		qry += "\n    where a.accid=" + Accid + " and a.statetag=1  and a.ntid=1";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "    and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "       and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "       and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "       and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "       and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "      and c.warename like '%" + Warename + "%'";
		if (Locale.length() > 0)
			qry += "      and c.locale like '%" + Locale + "%'";
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += " and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += " and c.typeid=" + Typeid;
			}

		if (Providlist.length() > 0)
			qry += "    and " + Func.getCondition("c.provid", Providlist);

		if (Brandidlist.length() > 0)
			qry += "    and " + Func.getCondition("c.brandid", Brandidlist);

		if (Provid >= 0)
			qry += "       and c.provid=" + Provid;
		if (Areaid >= 0)
			qry += "       and c.areaid=" + Areaid;
		if (Seasonname.length() > 0)
			qry += "       and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "       and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid; ";

		// --调入
		qry += "\n    insert into v_cxjxchz_temp (houseid,wareid,colorid,sizeid,amountdr,currdr)  ";
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amountdr ";
		// if (jgfs == 1)
		qry += ",sum(b.amount*b.price) as currdr";
		// qry += ",sum(b.amount*d.costprice) as currqm";
		// else if (jgfs == 2)
		// qry += ",sum(b.amount*d.costprice1) as currdr";
		// else
		// qry += ",0 as currdr";
		qry += "\n    from allotinh a  ";
		qry += "\n    join allotinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		// if (jgfs > 0) {
		// qry += "\n left outer join warecost d on b.wareid=d.wareid and
		// to_char(a.notedate,'yyyy-mm-dd')=d.daystr";
		// if (housecostbj == 1)
		// qry += " and a.houseid=d.houseid";
		// else
		// qry += " and d.houseid=0";
		// }
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";

		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "    and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "       and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "       and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "       and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "       and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "      and c.warename like '%" + Warename + "%'";
		if (Locale.length() > 0)
			qry += "      and c.locale like '%" + Locale + "%'";
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += " and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += " and c.typeid=" + Typeid;
			}

		if (Providlist.length() > 0)
			qry += "    and " + Func.getCondition("c.provid", Providlist);

		if (Brandidlist.length() > 0)
			qry += "    and " + Func.getCondition("c.brandid", Brandidlist);

		if (Provid >= 0)
			qry += "       and c.provid=" + Provid;
		if (Areaid >= 0)
			qry += "       and c.areaid=" + Areaid;
		if (Seasonname.length() > 0)
			qry += "       and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "       and c.prodyear='" + Prodyear + "'";

		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid; ";

		// --盘盈
		qry += "\n    insert into v_cxjxchz_temp (houseid,wareid,colorid,sizeid,amountpy,currpy)  ";
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amountpy ";
		// if (jgfs == 1)
		qry += ",sum(b.amount*b.price) as currpy";
		// qry += ",sum(b.amount*d.costprice) as currqm";
		// else if (jgfs == 2)
		// qry += ",sum(b.amount*d.costprice1) as currpy";
		// else
		// qry += ",0 as currpy";
		qry += "\n    from warecheckh a  ";
		qry += "\n    join warecheckm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		// if (jgfs > 0) {
		// qry += "\n left outer join warecost d on b.wareid=d.wareid and
		// to_char(a.notedate,'yyyy-mm-dd')=d.daystr";
		// if (housecostbj == 1)
		// qry += " and a.houseid=d.houseid";
		// else
		// qry += " and d.houseid=0";
		// }
		qry += "\n    where a.statetag=1 and a.accid=" + Accid + " and b.amount>0 ";// and
																					// to_char(a.notedate,'yyyy-mm-dd')
																					// >='"+mindate+"'
																					// ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "    and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "       and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "       and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "       and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "       and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "      and c.warename like '%" + Warename + "%'";
		if (Locale.length() > 0)
			qry += "      and c.locale like '%" + Locale + "%'";
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += " and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += " and c.typeid=" + Typeid;
			}

		if (Providlist.length() > 0)
			qry += "    and " + Func.getCondition("c.provid", Providlist);

		if (Brandidlist.length() > 0)
			qry += "    and " + Func.getCondition("c.brandid", Brandidlist);

		if (Provid >= 0)
			qry += "       and c.provid=" + Provid;
		if (Areaid >= 0)
			qry += "       and c.areaid=" + Areaid;
		if (Seasonname.length() > 0)
			qry += "       and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "       and c.prodyear='" + Prodyear + "'";
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid; ";

		// --零售
		qry += "\n    insert into v_cxjxchz_temp (houseid,wareid,colorid,sizeid,amountls,currls)  ";
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amountls ";
		// if (jgfs == 1)
		qry += ",sum(b.amount*b.price) as currls";
		// qry += ",-sum(b.amount*d.costprice) as currqm";
		// else if (jgfs == 2)
		// qry += ",sum(b.amount*d.costprice1) as currls";
		// else
		// qry += ",0 as currls";
		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		// if (jgfs > 0) {
		// qry += "\n left outer join warecost d on b.wareid=d.wareid and
		// to_char(a.notedate,'yyyy-mm-dd')=d.daystr";
		// if (housecostbj == 1)
		// qry += " and a.houseid=d.houseid";
		// else
		// qry += " and d.houseid=0";
		// }
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.ntid=0 ";// and to_char(a.notedate,'yyyy-mm-dd')
										// <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "    and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "       and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "       and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "       and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "       and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "      and c.warename like '%" + Warename + "%'";
		if (Locale.length() > 0)
			qry += "      and c.locale like '%" + Locale + "%'";
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += " and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += " and c.typeid=" + Typeid;
			}

		if (Providlist.length() > 0)
			qry += "    and " + Func.getCondition("c.provid", Providlist);
		if (Brandidlist.length() > 0)
			qry += "    and " + Func.getCondition("c.brandid", Brandidlist);

		if (Provid >= 0)
			qry += "       and c.provid=" + Provid;
		if (Areaid >= 0)
			qry += "       and c.areaid=" + Areaid;
		if (Seasonname.length() > 0)
			qry += "       and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "       and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid; ";

		// --商场零售
		qry += "\n    insert into v_cxjxchz_temp (houseid,wareid,colorid,sizeid,amountls,currls)  ";
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amountls ";
		// if (jgfs == 1)
		qry += ",sum(b.amount*b.price) as currls";
		// qry += ",-sum(b.amount*d.costprice) as currqm";
		// else if (jgfs == 2)
		// qry += ",sum(b.amount*d.costprice1) as currls";
		// else
		// qry += ",0 as currls";
		qry += "\n    from shopsaleh a  ";
		qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		// if (jgfs > 0) {
		// qry += "\n left outer join warecost d on b.wareid=d.wareid and
		// to_char(a.notedate,'yyyy-mm-dd')=d.daystr";
		// if (housecostbj == 1)
		// qry += " and a.houseid=d.houseid";
		// else
		// qry += " and d.houseid=0";
		// }
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		// qry += " and a.ntid=0 ";//and to_char(a.notedate,'yyyy-mm-dd')
		// <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "    and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "       and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "       and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "       and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "       and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "      and c.warename like '%" + Warename + "%'";
		if (Locale.length() > 0)
			qry += "      and c.locale like '%" + Locale + "%'";
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += " and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += " and c.typeid=" + Typeid;
			}

		if (Providlist.length() > 0)
			qry += "    and " + Func.getCondition("c.provid", Providlist);

		if (Brandidlist.length() > 0)
			qry += "    and " + Func.getCondition("c.brandid", Brandidlist);

		if (Provid >= 0)
			qry += "       and c.provid=" + Provid;
		if (Areaid >= 0)
			qry += "       and c.areaid=" + Areaid;
		if (Seasonname.length() > 0)
			qry += "       and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "       and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid; ";

		// --经销
		qry += "\n    insert into v_cxjxchz_temp (houseid,wareid,colorid,sizeid,amountxs,currxs)  ";
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amountxs ";
		// if (jgfs == 1)
		qry += ",sum(b.amount*b.price) as currxs";
		// qry += ",-sum(b.amount*d.costprice) as currqm";
		// else if (jgfs == 2)
		// qry += ",sum(b.amount*d.costprice1) as currxs";
		// else
		// qry += ",0 as currxs";
		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		// if (jgfs > 0) {
		// qry += "\n left outer join warecost d on b.wareid=d.wareid and
		// to_char(a.notedate,'yyyy-mm-dd')=d.daystr";
		// if (housecostbj == 1)
		// qry += " and a.houseid=d.houseid";
		// else
		// qry += " and d.houseid=0";
		// }
		qry += "\n    where a.statetag=1";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.ntid=1 ";// and to_char(a.notedate,'yyyy-mm-dd')
										// <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "    and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "       and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "       and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "       and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "       and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "      and c.warename like '%" + Warename + "%'";
		if (Locale.length() > 0)
			qry += "      and c.locale like '%" + Locale + "%'";
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += " and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += " and c.typeid=" + Typeid;
			}

		if (Providlist.length() > 0)
			qry += "    and " + Func.getCondition("c.provid", Providlist);

		if (Brandidlist.length() > 0)
			qry += "    and " + Func.getCondition("c.brandid", Brandidlist);

		if (Provid >= 0)
			qry += "       and c.provid=" + Provid;
		if (Areaid >= 0)
			qry += "       and c.areaid=" + Areaid;
		if (Seasonname.length() > 0)
			qry += "       and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "       and c.prodyear='" + Prodyear + "'";
		qry += "\n     group by a.houseid,b.wareid,b.colorid,b.sizeid,a.houseid; ";

		// --批发退货
		qry += "\n    insert into v_cxjxchz_temp (houseid,wareid,colorid,sizeid,amountxt,currxt)  ";
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,-sum(b.amount) as amountxt ";
		// if (jgfs == 1)
		qry += ",-sum(b.amount*b.price) as currxt";
		// qry += ",sum(b.amount*d.costprice) as currqm";
		// else if (jgfs == 2)
		// qry += ",-sum(b.amount*d.costprice1) as currxt";
		// else
		// qry += ",0 as currxt";
		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		// if (jgfs > 0) {
		// qry += "\n left outer join warecost d on b.wareid=d.wareid and
		// to_char(a.notedate,'yyyy-mm-dd')=d.daystr";
		// if (housecostbj == 1)
		// qry += " and a.houseid=d.houseid";
		// else
		// qry += " and d.houseid=0";
		// }
		qry += "\n    where a.statetag=1";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.ntid=2 ";// and to_char(a.notedate,'yyyy-mm-dd')
										// <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "    and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "       and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "       and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "       and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "       and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "      and c.warename like '%" + Warename + "%'";
		if (Locale.length() > 0)
			qry += "      and c.locale like '%" + Locale + "%'";

		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += " and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += " and c.typeid=" + Typeid;
			}

		if (Providlist.length() > 0)
			qry += "    and " + Func.getCondition("c.provid", Providlist);

		if (Brandidlist.length() > 0)
			qry += "    and " + Func.getCondition("c.brandid", Brandidlist);

		if (Provid >= 0)
			qry += "       and c.provid=" + Provid;
		if (Areaid >= 0)
			qry += "       and c.areaid=" + Areaid;
		if (Seasonname.length() > 0)
			qry += "       and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "       and c.prodyear='" + Prodyear + "'";

		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid; ";

		// --调出
		qry += "\n    insert into v_cxjxchz_temp (houseid,wareid,colorid,sizeid,amountdc,currdc)  ";
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amountdc ";
		// if (jgfs == 1)
		qry += ",sum(b.amount*b.price) as currdc";
		// qry += ",-sum(b.amount*d.costprice) as currqm";
		// else if (jgfs == 2)
		// qry += ",sum(b.amount*d.costprice1) as currdc";
		// else
		// qry += ",0 as currdc";
		qry += "\n    from allotouth a  ";
		qry += "\n    join allotoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		// if (jgfs > 0) {
		// qry += "\n left outer join warecost d on b.wareid=d.wareid and
		// to_char(a.notedate,'yyyy-mm-dd')=d.daystr";
		// if (housecostbj == 1)
		// qry += " and a.houseid=d.houseid";
		// else
		// qry += " and d.houseid=0";
		// }
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "    and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "       and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "       and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "       and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "       and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "      and c.warename like '%" + Warename + "%'";
		if (Locale.length() > 0)
			qry += "      and c.locale like '%" + Locale + "%'";
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += " and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += " and c.typeid=" + Typeid;
			}

		if (Providlist.length() > 0)
			qry += "    and " + Func.getCondition("c.provid", Providlist);
		if (Brandidlist.length() > 0)
			qry += "    and " + Func.getCondition("c.brandid", Brandidlist);

		if (Provid >= 0)
			qry += "       and c.provid=" + Provid;
		if (Areaid >= 0)
			qry += "       and c.areaid=" + Areaid;
		if (Seasonname.length() > 0)
			qry += "       and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "       and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid; ";

		// --盘亏
		qry += "\n    insert into v_cxjxchz_temp (houseid,wareid,colorid,sizeid,amountpk,currpk)  ";
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid,-sum(b.amount) as amountpk ";
		// if (jgfs == 1)
		qry += ",-sum(b.amount*b.price) as currpk";
		// qry += ",sum(b.amount*d.costprice) as currqm";
		// else if (jgfs == 2)
		// qry += ",-sum(b.amount*d.costprice1) as currpk";
		// else
		// qry += ",0 as currpk";
		qry += "\n    from warecheckh a  ";
		qry += "\n    join warecheckm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		// if (jgfs > 0) {
		// qry += "\n left outer join warecost d on b.wareid=d.wareid and
		// to_char(a.notedate,'yyyy-mm-dd')=d.daystr";
		// if (housecostbj == 1)
		// qry += " and a.houseid=d.houseid";
		// else
		// qry += " and d.houseid=0";
		// }
		qry += "\n    where a.statetag=1 and a.accid=" + Accid + " and b.amount<0 ";// and
																					// to_char(a.notedate,'yyyy-mm-dd')
																					// >='"+mindate+"'
																					// ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "    and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "       and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "       and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "       and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "       and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "      and c.warename like '%" + Warename + "%'";
		if (Locale.length() > 0)
			qry += "      and c.locale like '%" + Locale + "%'";
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += " and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += " and c.typeid=" + Typeid;
			}

		if (Providlist.length() > 0)
			qry += "    and " + Func.getCondition("c.provid", Providlist);
		if (Brandidlist.length() > 0)
			qry += "    and " + Func.getCondition("c.brandid", Brandidlist);

		if (Provid >= 0)
			qry += "       and c.provid=" + Provid;
		if (Areaid >= 0)
			qry += "       and c.areaid=" + Areaid;
		if (Seasonname.length() > 0)
			qry += "       and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "       and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid; ";

		qry += "\n   update v_cxjxchz_temp set amountqm=amountqc+amountcg+amountct+amountcs+amountdr+amountpy-amountls-amountxs-amountxt-amountdc-amountpk ;";

		// qry += "\n where epid=" + Userid + "; ";
		// 计算期末成本金额
		if (housecostbj == 1)
			qry += "\n   update v_cxjxchz_temp a set currqm=amountqm*nvl((select costprice from warecost b where a.wareid=b.wareid and a.houseid=b.houseid and b.daystr='" + Maxdate + "'),0); ";
		else
			qry += "\n   update v_cxjxchz_temp a set currqm=amountqm*nvl((select costprice from warecost b where a.wareid=b.wareid and b.houseid=0 and b.daystr='" + Maxdate + "'),0) ;";
		// qry += "\n where epid=" + Userid + "; ";

		qry += "\n    insert into v_cxjxchz_data (id,epid,wareid,colorid,sizeid,amountqc,amountcg,amountct,amountcs,amountdr,amountpy,amountls,amountxs,amountxt,amountdc,amountpk,amountqm,currqc,currcg,currct,currcs,currdr,currpy,currls,currxs,currxt,currdc,currpk,currqm) ";
		qry += "\n    select v_cxjxchz_data_id.nextval," + Userid
				+ ",wareid,colorid,sizeid,amountqc,amountcg,amountct,amountcs,amountdr,amountpy,amountls,amountxs,amountxt,amountdc,amountpk,amountqm ";
		qry += "\n     ,currqc,currcg,currct,currcs,currdr,currpy,currls,currxs,currxt,currdc,currpk,currqm";
		qry += "\n    from( ";
		qry += "\n       select a.wareid,a.colorid,a.sizeid,sum(amountqc) as amountqc,sum(amountcg) as amountcg,sum(amountct) as amountct,sum(amountcs) as amountcs,sum(amountdr) as amountdr ";
		qry += "\n       ,sum(amountpy) as amountpy ,sum(amountls) as amountls,sum(amountxs) as amountxs,sum(amountxt) as amountxt,sum(amountdc) as amountdc,sum(amountpk) as amountpk,sum(amountqm) as amountqm";
		qry += "\n       ,sum(currqc) as currqc,sum(currcg) as currcg,sum(currct) as currct,sum(currcs) as currcs,sum(currdr) as currdr ";
		qry += "\n       ,sum(currpy) as currpy,sum(currls) as currls,sum(currxs) as currxs,sum(currxt) as currxt,sum(currdc) as currdc,sum(currpk) as currpk ";
		qry += "\n       ,sum(currqm) as currqm";
		qry += "\n       from v_cxjxchz_temp a";
		qry += "\n       left outer join warecode b on a.wareid=b.wareid";
		qry += "\n       group by a.wareid,a.colorid,a.sizeid ";
		qry += "\n     ); ";

		// qry += "\n update v_cxjxchz_data set
		// amountqm=amountqc+amountcg+amountct+amountcs+amountdr+amountpy-amountls-amountxs-amountxt-amountdc-amountpk
		// ";

		// qry += "\n where epid=" + Userid + "; ";

		qry += "\n    update v_cxjxchz_data a set retailsale=(select retailsale from warecode b where a.wareid=b.wareid) where epid=" + Userid + ";";

		qry += "\n    commit;";
		qry += "\n end;";
		// LogUtils.LogDebugWrite("totaljxchz", qry);
		// System.out.println(qry);
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		qry = " select nvl(sum(amountqc),0) as amountqc,nvl(sum(amountcg),0) as amountcg,nvl(sum(amountct),0) as amountct,nvl(sum(amountcs),0) as amountcs,nvl(sum(amountdr),0) as amountdr,nvl(sum(amountpy),0) as amountpy,nvl(sum(amountls),0) as amountls";
		qry += ",nvl(sum(amountxs),0) as amountxs,nvl(sum(amountxt),0) as amountxt,nvl(sum(amountdc),0) as amountdc,nvl(sum(amountpk),0) as amountpk,nvl(sum(amountqm),0) as amountqm ";
		qry += "\n ,nvl(sum(currqc),0) as currqc,nvl(sum(currcg),0) as currcg,nvl(sum(currct),0) as currct,nvl(sum(currcs),0) as currcs,nvl(sum(currdr),0) as currdr,nvl(sum(currpy),0) as currpy,nvl(sum(currls),0) as currls,nvl(sum(currxs),0) as currxs";
		qry += "\n ,nvl(sum(currxt),0) as currxt,nvl(sum(currdc),0) as currdc,nvl(sum(currpk),0) as currpk,nvl(sum(currqm),0) as currqm";
		qry += "\n from v_cxjxchz_data a ";
		qry += "\n where epid=" + Userid;
		Table tb = new Table();
		tb = DbHelperSQL.GetTable(qry);
		errmess = "\"msg\":\"操作成功！\",\"warning\":\"" + mess + "\"";
		for (int j = 0; j < tb.getColumnCount(); j++) {//
			// if (j > 0)
			// errmess += ",";
			String strKey = tb.getRow(0).getColumn(j + 1).getName().toUpperCase();//
			String strValue = tb.getRow(0).get(j).toString();//
			errmess += ",\"" + strKey + "\":\"" + strValue + "\"";
		}

		return 1;
	}

	// 分页显示
	public Table GetTable(QueryParam qp) {

		StringBuilder strSql = new StringBuilder();
		strSql.append("select ");
		if (!Grouplist.equals(""))
			strSql.append(Grouplist + ",");
		if (!Fieldlist.equals(""))
			Fieldlist += ",";
		if (!Grouplist.equals("")) // 汇总查询
			Fieldlist += "min(rownum) as keyid";
		else
			Fieldlist += "rownum as keyid";
		strSql.append(Fieldlist);
		strSql.append("\n FROM v_cxjxchz_data a");
		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
		strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append("\n left outer join brand f on b.brandid=f.brandid");
		strSql.append("\n left outer join waretype e on b.typeid=e.typeid");
		strSql.append("\n left outer join provide i on b.provid=i.provid");
		strSql.append("\n where a.epid=" + Userid);

		if (!Grouplist.equals(""))
			strSql.append("\n group by " + Func.delGroupOfAs(Grouplist));

		// System.out.println("aaa2="+strSql.toString());
		qp.setQueryString(strSql.toString());
		if (Sortlist.equals(""))
			Sortlist = "keyid";
		else
			Sortlist += ",keyid";
		qp.setSortString(Sortlist);
		qp.setSumString(Sumlist);
		qp.setCalcfield(Calcfield);
		return DbHelperSQL.GetTable(qp);
	}

	// 分页显示
	public String GetTable2Excel(QueryParam qp,JSONObject jsonObject) {

		StringBuilder strSql = new StringBuilder();
		strSql.append("select ");
		if (!Grouplist.equals(""))
			strSql.append(Grouplist + ",");
		if (!Fieldlist.equals(""))
			Fieldlist += ",";
		if (!Grouplist.equals("")) // 汇总查询
			Fieldlist += "min(rownum) as keyid";
		else
			Fieldlist += "rownum as keyid";
		strSql.append(Fieldlist);
		strSql.append("\n FROM v_cxjxchz_data a");
		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
		strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append("\n left outer join brand f on b.brandid=f.brandid");
		strSql.append("\n left outer join waretype e on b.typeid=e.typeid");
		strSql.append("\n left outer join provide i on b.provid=i.provid");
		strSql.append("\n where a.epid=" + Userid);

		if (!Grouplist.equals(""))
			strSql.append("\n group by " + Func.delGroupOfAs(Grouplist));

		// System.out.println("aaa2="+strSql.toString());
		qp.setQueryString(strSql.toString());
		if (Sortlist.equals(""))
			Sortlist = "keyid";
		else
			Sortlist += ",keyid";
		qp.setSortString(Sortlist);
		qp.setSumString(Sumlist);
		qp.setCalcfield(Calcfield);
		qp.setPageIndex(1);
		qp.setPageSize(100);
		return DbHelperSQL.Table2Excel(qp, jsonObject);	
		
//		return DbHelperSQL.GetTable(qp);
	}

}
