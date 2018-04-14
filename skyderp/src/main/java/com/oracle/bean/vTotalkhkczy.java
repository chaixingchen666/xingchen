/**
 * 
 */
package com.oracle.bean;

import java.util.Date;

import com.comdot.data.Table;
import com.common.tool.Func;
import com.flyang.main.pFunc;
//import com.common.tool.SizeParam;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.QueryParam;

import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
public class vTotalkhkczy { //客户库存资源

	private String Fieldlist = ""; // 要显示的项目列表
	private String Grouplist = ""; // 分组的项目列表
	private String Sortlist = ""; // 排序的项目列表
	private String Sumlist = ""; // 要求和的项目列表
	private String Calcfield = ""; // 要求和的项目列表

	private String Serverdatetime = "";// 服务器当前时间,-------------必传
	private String Cxdatetime = ""; // 查询库存日期时间 格式 yyyy-MM-dd HH:mm:ss,------------必传

	private String Accbegindate = "2000-01-01"; // 账套开始使用时间
	private String Calcdate = ""; // 账套登账时间

//	private Integer Bj = 0; // bj:1=只查询v_xskczy_data 中的商品的库存,2=减配货占用(返回可用库存) ,3=只查询v_salewhere_data中的商品的库存,4=指定商品，颜色库存,5=库存报警
//	private Long NoCustid = (long) 0; // 不查询的店铺id bj=4时有效
//	private Integer Bj0 = 0; // bj=3时，bj0=1表示只查询有销售的库存

	private Integer Qxbj = 0;// 1=启用权限控
	private Integer Nocust = 0; // 1=不分客户

	private Long Accid = (long) 0;
	private Long Userid = (long) 0;

	private Long Wareid = (long) 0;

	private Long Custid = (long) 0;

	private Long Colorid = (long) 0;

	private Long Sizeid = (long) 0;
	private Long Typeid = (long) -1;
	private Long Brandid = (long) -1;
	private Long Provid = (long) -1;
	private Long Areaid = (long) -1;

	private String Wareno = "";
	private String Warename = "";
	private String Seasonname = "";

	private String Custidlist = "";
	private String Brandidlist = "";

	public void setProvidlist(String providlist) {
		Providlist = providlist;
	}

	public void setAreaidlist(String areaidlist) {
		Areaidlist = areaidlist;
	}

	private String Providlist = "";
	private String Areaidlist = "";
	private String Typeidlist = "";

	public void setTypeidlist(String typeidlist) {
		Typeidlist = typeidlist;
	}

	private String Prodyear = "";
//	private Long Paid = (long) 0; // 搭配id
	private String Prodno = "";
	// private Float Entersale = (float) 0;
	// private Float Retailsale = (float) 0;
	// private Float Sale1 = (float) 0;
	// private Float Sale2 = (float) 0;
	// private Float Sale3 = (float) 0;
	// private Float Sale4 = (float) 0;
	// private Float Sale5 = (float) 0;
	// private String Remark = "";
	private String Sizegroupno = "";
	// private String Useritem1 = "";
	// private String Useritem2 = "";
	// private String Useritem3 = "";
	// private String Useritem4 = "";
	// private String Useritem5 = "";
	// private Integer Lyfs = 0;
	// private Integer Noused = 1;
	// private String Gbbar = "";
	// private String Xdsm = "";
	// private String Zxbz = "";
	// private String Ssdate = "";
	private Integer Xsid = 3;
	// xsid: 0=有库存，1=无库存 ，2=指定范围 ,3=所有
	private Float Minamt = (float) 0;
	private Float Maxamt = (float) 0;

	// int hzfs = jsonObject.has("hzfs") ? Integer.parseInt(jsonObject.getString("hzfs")) : 0;
	// // hzfs:0=货号，1=货号+颜色，2=货号+颜色+尺码
	// int sortid = jsonObject.has("sortid") ? Integer.parseInt(jsonObject.getString("sortid")) : 0;
	// // 排序方式0=汇总项目， 1=数量 2=金额
	// int xsid = jsonObject.has("xsid") ? Integer.parseInt(jsonObject.getString("xsid")) : 0;

	private String Minssdate = "";
	private String Maxssdate = "";

	// private String Dj = "";
	// private String Jlman = "";
	// private String Sjman = "";
	private String Locale = "";
	// private Float Checksale = (float) 0;
	// private String Wareremark = "";
	// private Long Waveid = (long) -1;
	// private Integer Tjtag = 2;
	// private String Salerange = "";
	private Float Maxsale = (float) -1;
	private Float Minsale = (float) -1;
	private Integer Issxy = 0;

	public void setIssxy(Integer issxy) {
		Issxy = issxy;
	}

	private String errmess = "";

	public Long getCustid() {
		return Custid;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

	public void setCalcdate(String calcdate) {
		Calcdate = calcdate;
	}

	public String getErrmess() {
		return errmess;
	}

	public void setCxdatetime(String cxdatetime) {
		Cxdatetime = cxdatetime;
	}

	public void setUserid(Long userid) {
		Userid = userid;
	}

	public void setServerdatetime(String serverdatetime) {
		Serverdatetime = serverdatetime;
	}

//	public void setBj(Integer bj) {
//		Bj = bj;
//	}
//
//	public void setBj0(Integer bj0) {
//		Bj0 = bj0;
//	}

	public void setNocust(Integer Nocust) {
		Nocust = Nocust;
	}

	public void setQxbj(Integer qxbj) {
		Qxbj = qxbj;
	}

//	public void setPaid(Long paid) {
//		Paid = paid;
//	}

	public void setErrmess(String errmess) {
		this.errmess = errmess;
	}

	public void setWareid(Long wareid) {
		Wareid = wareid;
	}

	public void setWareno(String wareno) {
		Wareno = wareno;
	}

	public void setCustid(Long custid) {
		Custid = custid;
	}

	public void setColorid(Long colorid) {
		Colorid = colorid;
	}

	public void setSizeid(Long sizeid) {
		Sizeid = sizeid;
	}

	public void setWarename(String warename) {
		Warename = warename;
	}

	public void setBrandid(Long brandid) {
		Brandid = brandid;
	}

	public void setSeasonname(String seasonname) {
		Seasonname = seasonname;
	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	public void setTypeid(Long typeid) {
		Typeid = typeid;
	}

	public void setProdyear(String prodyear) {
		Prodyear = prodyear;
	}

	public void setProdno(String prodno) {
		Prodno = prodno;
	}

	// public void setEntersale(Float entersale) {
	// Entersale = entersale;
	// }

	public void setMinssdate(String minssdate) {
		Minssdate = minssdate;
	}

	public void setMaxssdate(String maxssdate) {
		Maxssdate = maxssdate;
	}

	// public void setRetailsale(Float retailsale) {
	// Retailsale = retailsale;
	// }
	//
	// public void setSale1(Float sale1) {
	// Sale1 = sale1;
	// }
	//
	// public void setSale2(Float sale2) {
	// Sale2 = sale2;
	// }
	//
	// public void setSale3(Float sale3) {
	// Sale3 = sale3;
	// }
	//
	// public void setSale4(Float sale4) {
	// Sale4 = sale4;
	// }
	//
	// public void setSale5(Float sale5) {
	// Sale5 = sale5;
	// }
	//
	// public void setRemark(String remark) {
	// Remark = remark;
	// }

	public void setSizegroupno(String sizegroupno) {
		Sizegroupno = sizegroupno;
	}

	// public void setUseritem1(String useritem1) {
	// Useritem1 = useritem1;
	// }
	//
	// public void setUseritem2(String useritem2) {
	// Useritem2 = useritem2;
	// }
	//
	// public void setUseritem3(String useritem3) {
	// Useritem3 = useritem3;
	// }
	//
	// public void setUseritem4(String useritem4) {
	// Useritem4 = useritem4;
	// }
	//
	// public void setUseritem5(String useritem5) {
	// Useritem5 = useritem5;
	// }
	//
	// public void setLyfs(Integer lyfs) {
	// Lyfs = lyfs;
	// }
	//
	// public void setNoused(Integer noused) {
	// Noused = noused;
	// }

	public void setCustidlist(String Custidlist) {
		Custidlist = Custidlist;
	}

	public void setBrandidlist(String brandidlist) {
		Brandidlist = brandidlist;
	}

	// public void setGbbar(String gbbar) {
	// Gbbar = gbbar;
	// }

	// public void setXdsm(String xdsm) {
	// Xdsm = xdsm;
	// }
	//
	// public void setZxbz(String zxbz) {
	// Zxbz = zxbz;
	// }
	//
	// public void setSsdate(String ssdate) {
	// Ssdate = ssdate;
	// }
	//
	// public void setDj(String dj) {
	// Dj = dj;
	// }
	//
	// public void setJlman(String jlman) {
	// Jlman = jlman;
	// }

	// public void setSjman(String sjman) {
	// Sjman = sjman;
	// }

	public void setProvid(Long provid) {
		Provid = provid;
	}

	public void setLocale(String locale) {
		Locale = locale;
	}

	// public void setChecksale(Float checksale) {
	// Checksale = checksale;
	// }

	public void setAreaid(Long areaid) {
		Areaid = areaid;
	}

	// public void setWareremark(String wareremark) {
	// Wareremark = wareremark;
	// }
	//
	// public void setWaveid(Long waveid) {
	// Waveid = waveid;
	// }

	// public void setTjtag(Integer tjtag) {
	// Tjtag = tjtag;
	// }
	//
	// public void setSalerange(String salerange) {
	// Salerange = salerange;
	// }

	public void setMaxsale(Float maxsale) {
		Maxsale = maxsale;
	}

	public void setMinsale(Float minsale) {
		Minsale = minsale;
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

	public int doCxkczy() {
		// if (Calcdate.length() <= 0) {
		// Calcdate = Func.DateToStr(Func.getPrevDay(pFunc.getServerdatetime()), "yyyy-MM-dd");
		// }
		// System.out.println("totalcxkczy0: Cxdatetime=" + Cxdatetime);
		String nowdate2 = "";
		if (Cxdatetime.equals("")) {// 如果查询日期未指定，则默认查询当前日期的库存
			Cxdatetime = Func.DateToStr(pFunc.getServerdatetime(), "yyyy-MM-dd") + " 23:59:59";
			nowdate2 = Cxdatetime;
		} else {
			if (Cxdatetime.length() != 10 && Cxdatetime.length() != 19) {
				errmess = "日期格式无效！";
				return 0;
			}
			if (Cxdatetime.length() == 10)// 如果查询日期只有年月日，自动默认时间,
				Cxdatetime += " 23:59:59";

			if (Serverdatetime.equals(""))// 如果服务器时间未传入，自动取服务器时间
				Serverdatetime = Func.DateToStr(pFunc.getServerdatetime(), "yyyy-MM-dd HH:mm:ss");
			// 最大的查询时间
			nowdate2 = Func.subString(Serverdatetime, 1, 10) + " 23:59:59";// nowday.ToString("yyyy-MM-dd") + " 23:59:59";
			if (Cxdatetime.compareTo(nowdate2) < 0)
				nowdate2 = Cxdatetime;
		}
		// DateTime nowday = GetServerdate();
		// String nowdate2 =Func.subString(Nowdate, 1, 10)+" 23:59:59";// nowday.ToString("yyyy-MM-dd") + " 23:59:59";

		// nowday = DateTime.Parse(nowdate2);
		String nowdate0 = Func.DateToStr(Func.getDataAdd(nowdate2, -1)); // nowday.AddDays(-1).ToString("yyyy-MM-dd");
		String nowdate1 = Func.subString(nowdate2, 1, 10) + " 00:00:00";
		if (Calcdate.length() > 0) {
			if (Calcdate.compareTo(nowdate0) < 0) {
				nowdate0 = Calcdate;// Func.DateToStr(Func.getDataAdd(Calcdate, -1));
				nowdate1 = Func.DateToStr(Func.getDataAdd(nowdate0, 1)) + " 00:00:00";
			}
		}

		// System.out.println("totalcxkczy: nowdate0=" + nowdate0 + " nowdate1=" + nowdate1 + " nowdate2=" + nowdate2);

		if (Custidlist.length() <= 0 && Custid > 0)
			Custidlist = "" + Custid;
		if (Brandidlist.length() <= 0 && Brandid >= 0)
			Brandidlist = "" + Brandid;
		if (Providlist.length() <= 0 && Provid >= 0)
			Providlist = "" + Provid;
		if (Areaidlist.length() <= 0 && Areaid > 0)
			Areaidlist = "" + Areaid;
		// System.out.println("Custidlist="+Custidlist);
		// System.out.println("nowdate0=" + nowdate0 + " nowdate1=" + nowdate1 + " nowdate2=" + nowdate2);
		String qry = " declare";
		qry += "\n    v_begindate varchar2(10); ";
		qry += "\n begin ";
		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n       select ROWID from v_khkczy_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n            delete from v_khkczy_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";
//		if (Bj == 2)
//			qry += "\n    select to_char(begindate,'yyyy-mm-dd') into v_begindate from accreg where accid=" + Accid + ";";

		qry += "\n     insert into v_khkczy_data (id,epid,Custid,Wareid,Colorid,Sizeid,amount) ";
		qry += "\n     select v_khkczy_data_id.nextval," + Userid + ", Custid,Wareid,Colorid,Sizeid,amount from ( ";

		if (Nocust == 1)// 不分店铺汇总库存
			qry += "\n     select Wareid,Colorid,Sizeid,0 as Custid,nvl(sum(amount),0) as amount ";

		else
			qry += "\n     select Wareid,Colorid,Sizeid,Custid,nvl(sum(amount),0) as amount ";
		qry += "\n     from (  ";
		// --期初库存
		// qry += "\n select /*+ordered index(a IX1_WARESUM) */ a.Custid,a.Wareid,a.Colorid,a.Sizeid,a.amount ";
		qry += "\n     select   a.Custid,a.Wareid,a.Colorid,a.Sizeid,a.amount ";
		qry += "\n     from custsum a ";
		qry += "\n     left outer join warecode b on a.Wareid=b.Wareid ";
		qry += "\n     where a.daystr='" + nowdate0 + "' and (a.amount>0 or a.amount<0)  and b.statetag=1 ";
//		if (Paid > 0) {
//			qry += "\n   and exists (select 1 from vippairs1 a1 where a1.Wareid=a.Wareid and a1.Paid=" + Paid + ")";
//		}
		qry += "\n     and b.accid=" + Accid + " ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.Custid=x1.Custid and x1.epid=" + Userid + " ) ";
			qry += "\n     and (b.Brandid=0 or exists (select 1 from employebrand x2 where b.Brandid=x2.Brandid and x2.epid=" + Userid + " )) ";
		}
		if (Wareid > 0)
			qry += "      and a.Wareid=" + Wareid;
		if (Colorid > 0)
			qry += "      and a.Colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "      and a.Sizeid=" + Sizeid;
		if (!Wareno.equals(""))
			qry += "      and b.Wareno like '%" + Wareno + "%'";
		if (!Prodno.equals(""))
			qry += "      and b.Prodno like '%" + Prodno + "%'";
		if (!Warename.equals(""))
			qry += "      and b.warename like '%" + Warename + "%'";
		if (!Seasonname.equals(""))
			qry += "      and b.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "      and b.prodyear='" + Prodyear + "'";
		if (!Sizegroupno.equals(""))
			qry += "      and b.Sizegroupno='" + Sizegroupno + "'";
		if (!Locale.equals(""))
			qry += "      and b.locale like '%" + Locale + "%'";
		if (Minsale >= 0)
			qry += " and b.retailsale>=" + Minsale;
		if (Maxsale >= 0)
			qry += " and b.retailsale<=" + Maxsale;
		if (!Func.isNull(Minssdate))
			qry += "      and b.ssdate>='" + Minssdate + "'";
		if (!Func.isNull(Maxssdate))
			qry += "      and b.ssdate<='" + Maxssdate + "'";
		// if (Typeid>0 ) qry+=" and b.Typeid="+Typeid;
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "  and exists (select 1 from waretype t where t.Typeid=b.Typeid and t.p_Typeid= " + Typeid + ") ";
			} else {
				qry += "    and b.Typeid=" + Typeid;
			}

		if (!Brandidlist.equals(""))
			qry += " and " + Func.getCondition("b.brandid", Brandidlist);
		if (!Providlist.equals(""))
			qry += " and " + Func.getCondition("b.provid", Providlist);
		if (!Areaidlist.equals(""))
			qry += " and " + Func.getCondition("b.areaid", Areaidlist);

		if (!Custidlist.equals(""))
			qry += " and " + Func.getCondition("a.Custid", Custidlist);

		if (Provid >= 0)
			qry += "      and b.Provid=" + Provid;
		// if (Areaid >= 0)
		// qry += " and b.Areaid=" + Areaid;
//		if (Paid > 0) {
//			qry += "\n   and exists (select 1 from vippairs1 a1 where a1.Wareid=a.Wareid and a1.Paid=" + Paid + ")";
//		}

		// --初始入库
		qry += "\n     union all ";
		qry += "\n     select a.Custid,b.Wareid,b.Colorid,b.Sizeid,sum(b.amount) as amount ";
		qry += "\n     from firstcusth a  ";
		qry += "\n     join firstcustm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n     left outer join warecode c on b.Wareid=c.Wareid ";
		// qry+=" where a.statetag=1 and a.notedate>=to_date("+nowdate+",'yyyy-mm-dd') and a.notedate<to_date("+nowdate+",'yyyy-mm-dd')+1 ";
		qry += "\n     where a.statetag=1 ";
		qry += "\n     and a.accid=" + Accid + " ";
		qry += "\n     and a.notedate>=to_date('" + nowdate1 + "','yyyy-mm-dd hh24:mi:ss') ";
		qry += "\n     and a.notedate<=to_date('" + nowdate2 + "','yyyy-mm-dd hh24:mi:ss') ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.Custid=x1.Custid and x1.epid=" + Userid + " ) ";
			qry += "\n     and (c.Brandid=0 or exists (select 1 from employebrand x2 where c.Brandid=x2.Brandid and x2.epid=" + Userid + " )) ";
			if (Issxy == 1)// 上下衣要判断区位
				qry += "\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))";
		}
		// if (Custid > 0)
		// qry += " and a.Custid=" + Custid;
		if (Wareid > 0)
			qry += "      and b.Wareid=" + Wareid;
		if (Colorid > 0)
			qry += "      and b.Colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "      and b.Sizeid=" + Sizeid;
		if (!Wareno.equals(""))
			qry += "      and c.Wareno like '%" + Wareno + "%'";
		if (!Prodno.equals(""))
			qry += "      and c.Prodno like '%" + Prodno + "%'";
		if (!Warename.equals(""))
			qry += "      and c.warename like '%" + Warename + "%'";
		if (!Seasonname.equals(""))
			qry += "      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "      and c.prodyear='" + Prodyear + "'";
		if (!Func.isNull(Minssdate))
			qry += "      and c.ssdate>='" + Minssdate + "'";
		if (!Func.isNull(Maxssdate))
			qry += "      and c.ssdate<='" + Maxssdate + "'";
		if (!Sizegroupno.equals(""))
			qry += "      and c.Sizegroupno='" + Sizegroupno + "'";
		if (!Locale.equals(""))
			qry += "      and c.locale like '%" + Locale + "%'";
		if (Minsale >= 0)
			qry += " and c.retailsale>=" + Minsale;
		if (Maxsale >= 0)
			qry += " and c.retailsale<=" + Maxsale;
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ")";
			} else {
				qry += "    and c.Typeid=" + Typeid;
			}
		if (!Brandidlist.equals(""))
			qry += " and " + Func.getCondition("c.brandid", Brandidlist);
		if (!Providlist.equals(""))
			qry += " and " + Func.getCondition("c.provid", Providlist);
		if (!Areaidlist.equals(""))
			qry += " and " + Func.getCondition("c.areaid", Areaidlist);
		if (!Custidlist.equals(""))
			qry += " and " + Func.getCondition("a.Custid", Custidlist);

		qry += "\n     group by a.Custid,b.Wareid,b.Colorid,b.Sizeid ";

		// --批发入库\退库
		qry += "\n     union all ";
		qry += "\n     select a.Custid,b.Wareid,b.Colorid,b.Sizeid,sum(case a.ntid when 1 then b.amount else -b.amount end) as amount ";
		qry += "\n     from wareouth a  ";
		qry += "\n     join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n     left outer join warecode c on b.Wareid=c.Wareid ";
		qry += "\n     where a.statetag=1 and (a.ntid=1 or a.ntid=2)";// and a.notedate>=to_date('" + nowdate1 + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + nowdate2 + "','yyyy-mm-dd hh24:mi:ss') ";
		qry += "\n     and a.accid=" + Accid;
		qry += "\n     and a.notedate>=to_date('" + nowdate1 + "','yyyy-mm-dd hh24:mi:ss') ";
		qry += "\n     and a.notedate<=to_date('" + nowdate2 + "','yyyy-mm-dd hh24:mi:ss') ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.Custid=x1.Custid and x1.epid=" + Userid + " ) ";
			qry += "\n     and (c.Brandid=0 or exists (select 1 from employebrand x2 where c.Brandid=x2.Brandid and x2.epid=" + Userid + " )) ";
			if (Issxy == 1)// 上下衣要判断区位
				qry += "\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))";
		}
		// if (Custid > 0)
		// qry += " and a.Custid=" + Custid;
		if (Wareid > 0)
			qry += "      and b.Wareid=" + Wareid;
		if (Colorid > 0)
			qry += "      and b.Colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "      and b.Sizeid=" + Sizeid;
		if (!Wareno.equals(""))
			qry += "      and c.Wareno like '%" + Wareno + "%'";
		if (!Prodno.equals(""))
			qry += "      and c.Prodno like '%" + Prodno + "%'";
		if (!Warename.equals(""))
			qry += "      and c.warename like '%" + Warename + "%'";
		if (!Seasonname.equals(""))
			qry += "      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "      and c.prodyear='" + Prodyear + "'";
		if (!Sizegroupno.equals(""))
			qry += "      and c.Sizegroupno='" + Sizegroupno + "'";
		if (!Locale.equals(""))
			qry += "      and c.locale like '%" + Locale + "%'";
		if (Minsale >= 0)
			qry += " and c.retailsale>=" + Minsale;
		if (Maxsale >= 0)
			qry += " and c.retailsale<=" + Maxsale;
		if (!Func.isNull(Minssdate))
			qry += "      and c.ssdate>='" + Minssdate + "'";
		if (!Func.isNull(Maxssdate))
			qry += "      and c.ssdate<='" + Maxssdate + "'";
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ")";
			} else {
				qry += "    and c.Typeid=" + Typeid;
			}

		// if (Brandid >= 0)
		// qry += " and c.Brandid=" + Brandid;
		if (!Brandidlist.equals(""))
			qry += " and " + Func.getCondition("c.brandid", Brandidlist);
		if (!Providlist.equals(""))
			qry += " and " + Func.getCondition("c.provid", Providlist);
		if (!Areaidlist.equals(""))
			qry += " and " + Func.getCondition("c.areaid", Areaidlist);
		if (!Custidlist.equals(""))
			qry += " and " + Func.getCondition("a.Custid", Custidlist);

		// if (Provid >= 0)
		// qry += " and c.Provid=" + Provid;
		// if (Areaid >= 0)
		// qry += " and c.Areaid=" + Areaid;
		qry += "\n     group by a.Custid,b.Wareid,b.Colorid,b.Sizeid ";


		// --客户销售出库
		qry += "\n     union all ";
		qry += "\n     select a.Custid,b.Wareid,b.Colorid,b.Sizeid,-sum(b.amount) as amount ";
		qry += "\n     from custsaleh a  ";
		qry += "\n     join custsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n     left outer join warecode c on b.Wareid=c.Wareid ";
		qry += "\n     where a.statetag=1 ";// and a.notedate>=to_date('" + nowdate1 + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + nowdate2 + "','yyyy-mm-dd hh24:mi:ss') ";
		qry += "\n     and a.accid=" + Accid;
		qry += "\n     and a.notedate>=to_date('" + nowdate1 + "','yyyy-mm-dd hh24:mi:ss') ";
		qry += "\n     and a.notedate<=to_date('" + nowdate2 + "','yyyy-mm-dd hh24:mi:ss') ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.Custid=x1.Custid and x1.epid=" + Userid + " ) ";
			qry += "\n     and (c.Brandid=0 or exists (select 1 from employebrand x2 where c.Brandid=x2.Brandid and x2.epid=" + Userid + " )) ";
			if (Issxy == 1)// 上下衣要判断区位
				qry += "\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))";
		}
		// if (Custid > 0)
		// qry += " and a.Custid=" + Custid;
		if (Wareid > 0)
			qry += "      and b.Wareid=" + Wareid;
		if (Colorid > 0)
			qry += "      and b.Colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "      and b.Sizeid=" + Sizeid;
		if (!Wareno.equals(""))
			qry += "      and c.Wareno like '%" + Wareno + "%'";
		if (!Prodno.equals(""))
			qry += "      and c.Prodno like '%" + Prodno + "%'";
		if (!Warename.equals(""))
			qry += "      and c.warename like '%" + Warename + "%'";
		if (!Seasonname.equals(""))
			qry += "      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "      and c.prodyear='" + Prodyear + "'";
		if (!Sizegroupno.equals(""))
			qry += "      and c.Sizegroupno='" + Sizegroupno + "'";
		if (!Locale.equals(""))
			qry += "      and c.locale like '%" + Locale + "%'";
		if (Minsale >= 0)
			qry += " and c.retailsale>=" + Minsale;
		if (Maxsale >= 0)
			qry += " and c.retailsale<=" + Maxsale;
		if (!Func.isNull(Minssdate))
			qry += "      and c.ssdate>='" + Minssdate + "'";
		if (!Func.isNull(Maxssdate))
			qry += "      and c.ssdate<='" + Maxssdate + "'";
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ")";
			} else {
				qry += "    and c.Typeid=" + Typeid;
			}

		// if (Brandid >= 0)
		// qry += " and c.Brandid=" + Brandid;
		if (!Brandidlist.equals(""))
			qry += " and " + Func.getCondition("c.brandid", Brandidlist);
		if (!Providlist.equals(""))
			qry += " and " + Func.getCondition("c.provid", Providlist);
		if (!Areaidlist.equals(""))
			qry += " and " + Func.getCondition("c.areaid", Areaidlist);
		if (!Custidlist.equals(""))
			qry += " and " + Func.getCondition("a.Custid", Custidlist);

		// if (Provid >= 0)
		// qry += " and c.Provid=" + Provid;
		// if (Areaid >= 0)
		// qry += " and c.Areaid=" + Areaid;
		qry += "\n     group by a.Custid,b.Wareid,b.Colorid,b.Sizeid ";


		// --盘点
		qry += "\n     union all ";
		qry += "\n     select a.Custid,b.Wareid,b.Colorid,b.Sizeid,sum(b.amount) as amount ";
		qry += "\n     from custcheckh a  ";
		qry += "\n     join custcheckm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n     left outer join warecode c on b.Wareid=c.Wareid ";
		qry += "\n     where a.statetag=1 and b.amount<>0 ";// and a.notedate>=to_date('" + nowdate1 + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + nowdate2 + "','yyyy-mm-dd hh24:mi:ss') ";
		qry += "\n     and a.accid=" + Accid + " ";
		qry += "\n     and a.notedate>=to_date('" + nowdate1 + "','yyyy-mm-dd hh24:mi:ss') ";
		qry += "\n     and a.notedate<=to_date('" + nowdate2 + "','yyyy-mm-dd hh24:mi:ss') ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.Custid=x1.Custid and x1.epid=" + Userid + " ) ";
			qry += "\n     and (c.Brandid=0 or exists (select 1 from employebrand x2 where c.Brandid=x2.Brandid and x2.epid=" + Userid + " )) ";
			if (Issxy == 1)// 上下衣要判断区位
				qry += "\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))";
		}
		// if (Custid > 0)
		// qry += " and a.Custid=" + Custid;
		if (Wareid > 0)
			qry += "\n      and b.Wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.Colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.Sizeid=" + Sizeid;
		if (!Wareno.equals(""))
			qry += "\n      and c.Wareno like '%" + Wareno + "%'";
		if (!Prodno.equals(""))
			qry += "\n      and c.Prodno like '%" + Prodno + "%'";
		if (!Warename.equals(""))
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (!Seasonname.equals(""))
			qry += "\n      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		if (!Sizegroupno.equals(""))
			qry += "\n      and c.Sizegroupno='" + Sizegroupno + "'";
		if (!Locale.equals(""))
			qry += "\n      and c.locale like '%" + Locale + "%'";
		if (Minsale >= 0)
			qry += "\n and c.retailsale>=" + Minsale;
		if (Maxsale >= 0)
			qry += "\n and c.retailsale<=" + Maxsale;
		if (!Func.isNull(Minssdate))
			qry += "\n      and c.ssdate>='" + Minssdate + "'";
		if (!Func.isNull(Maxssdate))
			qry += "\n      and c.ssdate<='" + Maxssdate + "'";
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ")";
			} else {
				qry += "\n    and c.Typeid=" + Typeid;
			}

		// if (Brandid >= 0)
		// qry += " and c.Brandid=" + Brandid;
		if (!Brandidlist.equals(""))
			qry += "\n and " + Func.getCondition("c.brandid", Brandidlist);
		if (!Providlist.equals(""))
			qry += "\n and " + Func.getCondition("c.provid", Providlist);
		if (!Areaidlist.equals(""))
			qry += "\n and " + Func.getCondition("c.areaid", Areaidlist);
		if (!Custidlist.equals(""))
			qry += "\n and " + Func.getCondition("a.Custid", Custidlist);

		// if (Provid >= 0)
		// qry += " and c.Provid=" + Provid;
		// if (Areaid >= 0)
		// qry += " and c.Areaid=" + Areaid;
		qry += "\n     group by a.Custid,b.Wareid,b.Colorid,b.Sizeid ";
		qry += "\n  ) ";
		qry += "\n  group by Wareid,Colorid,Sizeid  ";
		if (Nocust == 0)
			qry += ",Custid";// 1= 不分店铺汇总库存
		qry += ");";
		qry += "\n     commit;";
		qry += "\n end;";
		// LogUtils.LogDebugWrite("totalcxkczy", qry);
		// System.out.println("totalcxkczy1: " + qry);
		int ret = DbHelperSQL.ExecuteSql(qry, 600);// 10分种=60秒*10
		if (ret < 0) {
			errmess = "汇总失败！";
			return 0;
		} else {
			errmess = "汇总成功！";
			return 1;
		}
	}

//	public Table doListcxkczy(QueryParam qp, String nowdate, String order, int hzfs, int sortid, int xsid, int housecostbj, float minamt, float maxamt) {
//		if (nowdate.length() > 10)
//			nowdate = Func.subString(nowdate, 1, 10);
//		String qry = "";
//		String sort = "";
//		if (hzfs == 0) // 按货号汇总
//		{
//			qry = " select a.wareid,b.wareno,b.warename,b.units,b.locale,b.retailsale,b.sale4 as wholesale,b.remark,b.imagename0,sum(a.amount) as amount,sum(a.amount*b.retailsale) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc ";
//			qry += " ,round(sum(a.amount*b.sale4),2) as wholecurr ,round(sum(a.amount*c.costprice),2) as fixedcurr";
//			qry += " ,case sum(a.amount) when 0 then 0 else round(sum(a.amount*c.costprice)/sum(a.amount),2) end as costprice";
//			qry += " FROM v_khkczy_data a";
//			qry += " left outer join warecode b on a.wareid=b.wareid";
//			qry += " left outer join warecost c on a.wareid=c.wareid and c.daystr='" + nowdate + "'";
//			if (housecostbj == 1)
//				qry += "  and a.Custid=c.Custid";
//			else
//				qry += "  and c.Custid=0";
//			qry += " where a.epid=" + Userid;
//			qry += " group by a.wareid,b.wareno,b.warename,b.units,b.locale,b.retailsale,b.sale4,b.remark,b.imagename0";
//			if (xsid == 2) {
//				qry += " having sum(a.amount)>=" + minamt + " and sum(a.amount)<=" + maxamt;
//			} else {
//				if (xsid == 0) {
//					qry += " having sum(a.amount)<>0";
//				} else if (xsid == 1) {
//					qry += " having sum(a.amount)=0";
//				}
//			}
//			if (sortid == 1) {
//				sort = " amount " + order + ",wareid";
//			} else
//
//			if (sortid == 2) {
//				sort = " curr " + order + ",wareid";
//			} else {
//				sort = " wareno " + order + ",wareid";
//			}
//
//		} else if (hzfs == 1) // 按货号+颜色汇总
//		{
//			qry = " select a.wareid,b.wareno,b.warename,b.units,b.locale,b.retailsale,b.sale4 as wholesale,a.colorid,c.colorname,b.remark,b.imagename0,sum(a.amount) as amount,sum(a.amount*b.retailsale) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc ";
//			qry += "   ,round(sum(a.amount*b.sale4),2) as wholecurr,round(sum(a.amount*d.costprice),2) as fixedcurr";
//			qry += " ,case sum(a.amount) when 0 then 0 else round(sum(a.amount*d.costprice)/sum(a.amount),2) end as costprice";
//			qry += " FROM v_khkczy_data a";
//			qry += " left outer join warecode b on a.wareid=b.wareid";
//			qry += " left outer join colorcode c on a.colorid=c.colorid";
//			qry += " left outer join warecost d on a.wareid=d.wareid and d.daystr='" + nowdate + "'";
//			if (housecostbj == 1)
//				qry += "  and a.Custid=d.Custid";
//			else
//				qry += "  and d.Custid=0";
//			qry += " where a.epid=" + Userid;
//			qry += " group by a.wareid,b.wareno,b.warename,b.units,b.locale,b.retailsale,b.sale4,a.colorid,c.colorname,b.remark,b.imagename0";
//			if (xsid == 2) {
//				qry += " having sum(a.amount)>=" + minamt + " and sum(a.amount)<=" + maxamt;
//			} else {
//				if (xsid == 0) {
//					qry += " having sum(a.amount)<>0";
//				} else if (xsid == 1) {
//					qry += " having sum(a.amount)=0";
//				}
//			}
//			if (sortid == 1) {
//				sort = " amount " + order + ",wareid,colorid";
//			} else if (sortid == 2) {
//				sort = " curr " + order + ",wareid,colorid";
//			} else {
//				sort = " wareno " + order + ",wareid,colorid";
//			}
//		} else if (hzfs == 2) // 按货号+颜色+尺码汇总
//		{
//			qry = " select a.wareid,b.wareno,b.warename,b.units,b.locale,b.retailsale,b.sale4 as wholesale,a.colorid,c.colorname,a.sizeid,d.sizename,b.remark,b.imagename0,sum(a.amount) as amount,sum(a.amount*b.retailsale) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc ";
//			qry += "  ,round(sum(a.amount*b.sale4),2) as wholecurr ,round(sum(a.amount*e.costprice),2) as fixedcurr";
//			qry += " ,case sum(a.amount) when 0 then 0 else round(sum(a.amount*e.costprice)/sum(a.amount),2) end as costprice";
//			qry += " FROM v_khkczy_data a";
//			qry += " left outer join warecode b on a.wareid=b.wareid";
//			qry += " left outer join colorcode c on a.colorid=c.colorid";
//			qry += " left outer join sizecode d on a.sizeid=d.sizeid";
//			qry += " left outer join warecost e on a.wareid=e.wareid and e.daystr='" + nowdate + "'";
//			if (housecostbj == 1)
//				qry += "  and a.Custid=e.Custid";
//			else
//				qry += "  and e.Custid=0";
//			qry += " where a.epid=" + Userid;
//			qry += " group by a.wareid,b.wareno,b.warename,b.units,b.locale,b.retailsale,b.sale4,a.colorid,c.colorname,a.sizeid,d.sizename,b.remark,b.imagename0";
//
//			if (xsid == 2) {
//				qry += " having sum(a.amount)>=" + minamt + " and sum(a.amount)<=" + maxamt;
//			} else {
//				if (xsid == 0) {
//					qry += " having sum(a.amount)<>0";
//				} else if (xsid == 1) {
//					qry += " having sum(a.amount)=0";
//				}
//			}
//
//			if (sortid == 1) {
//				sort = " amount " + order + ",wareid,colorid,sizeid";
//			} else if (sortid == 2) {
//				sort = " curr " + order + ",wareid,colorid,sizeid";
//			} else {
//				sort = " wareno " + order + ",wareid,colorid,sizeid";
//			}
//		}
//
//		qp.setQueryString(qry);
//		qp.setSortString(sort);
//		qp.setSumString("nvl(sum(amount),0) as totalamt,nvl(sum(curr),0) as totalcurr,nvl(sum(wholecurr),0) as totalwholecurr,nvl(sum(fixedcurr),0) as totalfixedcurr");
//		return DbHelperSQL.GetTable(qp);
//
//	}

	// 获取分页数据,尺码横向展开
	public String GetTable2Json(QueryParam qp, int sizenum) {
		Grouplist += ",b.sizegroupno";
		Table tb = GetTable(qp);

		String sqlsize = "";
		String sqlsum = "";
		String qry1;

		long wareid = 0;
		long wareid0 = 0;
		long Custid = 0;
		long colorid = 0;
		String fh = "";
		// System.out.println("aaaa: " + qp.getTotalString());
		String retcs = "{\"result\":1,\"msg\":\"操作成功！\"," + qp.getTotalString() + ",\"rows\":[";
		// System.out.println(qp.getTotalString() );
		int rows = tb.getRowCount();
		int cols = tb.getColumnCount();
		for (int i = 0; i < rows; i++) {
			wareid = Long.parseLong(tb.getRow(i).get("WAREID").toString());
			if (wareid != wareid0) {
				SizeParam sp = new SizeParam(wareid, sizenum);
				sqlsize = sp.getSqlSize();
				sqlsum = sp.getSqlSum();
				wareid0 = wareid;
				// System.out.println("wareid=" + wareid + " sqlsize=" + sqlsize + " sqlsum=" + sqlsum);
			}
			// System.out.println("wareid=" + sqlsize);
			retcs += fh + "{";
			for (int j = 0; j < cols; j++) {

				String strKey = tb.getRow(i).getColumn(j + 1).getName().toUpperCase();

				String strValue = tb.getRow(i).get(j).toString();
				// System.out.println(strKey + "=" + strValue);

				if (strKey.equals("Custid"))
					Custid = Long.parseLong(strValue);
				else if (strKey.equals("COLORID"))
					colorid = Long.parseLong(strValue);

				retcs += "\"" + strKey + "\":\"" + strValue + "\",";
				fh = ",";
			}
			qry1 = "select " + sqlsum + "," + sqlsize;
			qry1 += " from v_khkczy_data a where a.epid=" + Userid;
			qry1 += " and a.wareid=" + wareid;
			if (Custid > 0)
				qry1 += " and a.Custid=" + Custid;
			if (colorid > 0)
				qry1 += " and a.colorid=" + colorid;
			// System.out.println("11111");

			Table tb1 = DbHelperSQL.Query(qry1).getTable(1);
			String fh1 = "";

			for (int j = 1; j <= sizenum; j++) {
				retcs += fh1 + "\"AMOUNT" + j + "\":\"" + tb1.getRow(0).get("AMOUNT" + j).toString() + "\""//
						+ ",\"SIZEID" + j + "\":\"" + tb1.getRow(0).get("SIZEID" + j).toString() + "\"" //
						+ ",\"SIZENAME" + j + "\":\"" + tb1.getRow(0).get("SIZENAME" + j).toString() + "\"";
				fh1 = ",";
			}
			// System.out.println("22222");
			retcs += "}";
			fh = ",";
		}
		retcs += "]}";
		return retcs;
	}

	// 获取分页数据,尺码横向展开 listwareandcolor
	public Table GetTable(QueryParam qp, String sqlsum, int sizenum) {

		String qry = "select a.Custid,b.custname,sum(a.amount) as amount0";
		if (sqlsum.length() > 0)
			qry += "," + sqlsum;
		qry += " from v_khkczy_data a";
		qry += " left outer join customer b on a.Custid=b.Custid";
		qry += " where a.epid=" + Userid;
		qry += " group by a.Custid,b.custname";
		// System.out.println(qry);
		qp.setQueryString(qry);
		qp.setSumString("nvl(sum(amount0),0) as totalamt");
		// Table tb =DbHelperSQL.GetTable(qp);
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp) {
		if (Cxdatetime.equals(""))
			Cxdatetime = Func.DateToStr(new Date());

		StringBuilder strSql = new StringBuilder();
		strSql.append("select ");
		if (!Grouplist.equals(""))
			strSql.append(Grouplist + ",");
		if (!Fieldlist.equals(""))
			Fieldlist += ",";

		if (!Grouplist.equals("")) // 汇总查询
		{
			Fieldlist += "min(rownum) as keyid";
		} else
			Fieldlist += "rownum as keyid";
		strSql.append(Fieldlist);
		strSql.append("\n FROM v_khkczy_data a");
		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
		// if (sizenum == 0)
		strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append("\n left outer join brand f on b.brandid=f.brandid");
		strSql.append("\n left outer join waretype g on b.typeid=g.typeid");
		strSql.append("\n left outer join customer h on a.Custid=h.Custid");
		strSql.append("\n left outer join provide i on b.provid=i.provid");
		strSql.append("\n left outer join area r on b.areaid=r.areaid");
		strSql.append("\n left outer join custcost e on a.custid=e.custid and a.wareid=e.wareid and e.daystr='" + Cxdatetime + "'");

		strSql.append("\n where a.epid=" + Userid);

		if (!Grouplist.equals(""))
			strSql.append("\n group by " + Func.delGroupOfAs(Grouplist));
		if (Xsid == 2) {
			strSql.append(" having sum(a.amount)>=" + Minamt + " and sum(a.amount)<=" + Maxamt);
		} else {
			if (Xsid == 0) {
				strSql.append(" having sum(a.amount)<>0");
			} else if (Xsid == 1) {
				strSql.append(" having sum(a.amount)=0");
			}
		}

		qp.setQueryString(strSql.toString());
		if (Sortlist.equals(""))
			Sortlist = "keyid";
		else
			Sortlist += ",keyid";
		qp.setSortString(Sortlist);
		qp.setSumString(Sumlist);
		// Calcfield = Calcfield.replaceAll("\\", "");
		qp.setCalcfield(Calcfield);

		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public String GetTable2Excel(QueryParam qp, int housecostbj, JSONObject jsonObject) {
		if (Cxdatetime.equals(""))
			Cxdatetime = Func.DateToStr(new Date());

		StringBuilder strSql = new StringBuilder();
		strSql.append("select ");
		if (!Grouplist.equals(""))
			strSql.append(Grouplist + ",");
		if (!Fieldlist.equals(""))
			Fieldlist += ",";

		if (!Grouplist.equals("")) // 汇总查询
		{
			Fieldlist += "min(rownum) as keyid";
		} else
			Fieldlist += "rownum as keyid";
		strSql.append(Fieldlist);
		strSql.append("\n FROM v_khkczy_data a");
		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
		// if (sizenum == 0)
		strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append("\n left outer join brand f on b.brandid=f.brandid");
		strSql.append("\n left outer join waretype g on b.typeid=g.typeid");
		strSql.append("\n left outer join customer h on a.Custid=h.Custid");
		strSql.append("\n left outer join provide i on b.provid=i.provid");
//		strSql.append("\n left outer join area r on b.areaid=r.areaid");

		strSql.append("\n left outer join custcost e on a.custid=e.custid and a.wareid=e.wareid and e.daystr='" + Cxdatetime + "'");
//		if (housecostbj == 1)
//			strSql.append("\n  and a.Custid=e.Custid");
//		else
//			strSql.append("\n  and e.Custid=0");
		strSql.append("\n where a.epid=" + Userid);

		if (!Grouplist.equals(""))
			strSql.append("\n group by " + Func.delGroupOfAs(Grouplist));
		if (Xsid == 2) {
			strSql.append(" having sum(a.amount)>=" + Minamt + " and sum(a.amount)<=" + Maxamt);
		} else {
			if (Xsid == 0) {
				strSql.append(" having sum(a.amount)<>0");
			} else if (Xsid == 1) {
				strSql.append(" having sum(a.amount)=0");
			}
		}

		qp.setQueryString(strSql.toString());
		if (Sortlist.equals(""))
			Sortlist = "keyid";
		else
			Sortlist += ",keyid";
		qp.setSortString(Sortlist);
		qp.setSumString(Sumlist);
		// Calcfield = Calcfield.replaceAll("\\", "");
		qp.setCalcfield(Calcfield);
		qp.setPageIndex(1);
		qp.setPageSize(100);
		return DbHelperSQL.Table2Excel(qp, jsonObject);

		// return DbHelperSQL.GetTable(qp);
	}

	// 获取所有数据
	public Table GetTable(String qry) {
		return DbHelperSQL.GetTable(qry);
	}

	// 报警
//	public String doListWarn2Excel(QueryParam qp, int cxfs, JSONObject jsonObject) {
//
//		StringBuilder strSql = new StringBuilder();
//		strSql.append("select a.id,a.wareid,a.colorid,a.sizeid,a.Custid,a.amount,e.minamt,e.maxamt");
//		strSql.append(",b.wareno,b.warename,b.units,c.colorname,d.sizename,f.housename,b.imagename0");
//		strSql.append("\n FROM v_khkczy_data a");
//		strSql.append("\n join warewarn e on a.Custid=e.Custid and a.wareid=e.wareid and a.colorid=e.colorid and a.sizeid=e.sizeid");
//		strSql.append("\n left outer join warehouse f on a.Custid=f.Custid");
//		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
//		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
//		strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");
//		strSql.append("\n where a.epid=" + Userid);
//		// cxfs:0=下限，1=上限，2=上下限，3=所有
//		if (cxfs == 0)
//			strSql.append("\n and not e.minamt is null and a.amount<e.minamt");
//		else if (cxfs == 1)
//			strSql.append("\n and not e.maxamt is null and a.amount>e.maxamt");
//		else if (cxfs == 2)
//			strSql.append("\n and (not e.maxamt is null and a.amount>e.maxamt or not e.minamt is null and a.amount<e.minamt)");
//		qp.setQueryString(strSql.toString());
//
//		qp.setPageIndex(1);
//		qp.setPageSize(100);
//		return DbHelperSQL.Table2Excel(qp, jsonObject);
//		// return DbHelperSQL.GetTable(qp);
//
//	}

	// 报警
//	public Table doListWarn(QueryParam qp, int cxfs) {
//
//		StringBuilder strSql = new StringBuilder();
//		strSql.append("select a.id,a.wareid,a.colorid,a.sizeid,a.Custid,a.amount,e.minamt,e.maxamt");
//		strSql.append(",b.wareno,b.warename,b.units,c.colorname,d.sizename,f.housename,b.imagename0");
//		strSql.append("\n FROM v_khkczy_data a");
//		strSql.append("\n join warewarn e on a.Custid=e.Custid and a.wareid=e.wareid and a.colorid=e.colorid and a.sizeid=e.sizeid");
//		strSql.append("\n left outer join warehouse f on a.Custid=f.Custid");
//		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
//		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
//		strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");
//		strSql.append("\n where a.epid=" + Userid);
//		// cxfs:0=下限，1=上限，2=上下限，3=所有
//		if (cxfs == 0)
//			strSql.append("\n and not e.minamt is null and a.amount<e.minamt");
//		else if (cxfs == 1)
//			strSql.append("\n and not e.maxamt is null and a.amount>e.maxamt");
//		else if (cxfs == 2)
//			strSql.append("\n and (not e.maxamt is null and a.amount>e.maxamt or not e.minamt is null and a.amount<e.minamt)");
//		qp.setQueryString(strSql.toString());
//
//		return DbHelperSQL.GetTable(qp);
//
//	}

	// 获取分页数据
//	public Table GetTableSxy(QueryParam qp, int housecostbj) {
//		if (Cxdatetime.equals(""))
//			Cxdatetime = Func.DateToStr(new Date());
//		StringBuilder strSql = new StringBuilder();
//		strSql.append("select ");
//		if (!Grouplist.equals(""))
//			strSql.append(Grouplist + ",");
//		if (!Fieldlist.equals(""))
//			Fieldlist += ",";
//
//		if (!Grouplist.equals("")) // 汇总查询
//		{
//			Fieldlist += "min(rownum) as keyid";
//		} else
//			Fieldlist += "rownum as keyid";
//		strSql.append(Fieldlist);
//		strSql.append("\n FROM v_khkczy_data a");
//		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
//		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
//		// if (sizenum == 0)
//		strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");
//		strSql.append("\n left outer join brand f on b.brandid=f.brandid");
//		strSql.append("\n left outer join waretype g on b.typeid=g.typeid");
//		strSql.append("\n left outer join warehouse h on a.Custid=h.Custid");
//		strSql.append("\n left outer join provide i on b.provid=i.provid");
//		strSql.append("\n left outer join area r on b.areaid=r.areaid");
//
//		strSql.append("\n where a.epid=" + Userid);
//
//		if (!Grouplist.equals(""))
//			strSql.append("\n group by " + Func.delGroupOfAs(Grouplist));
//		if (Xsid == 2) {
//			strSql.append(" having sum(a.amount)>=" + Minamt + " and sum(a.amount)<=" + Maxamt);
//		} else {
//			if (Xsid == 0) {
//				strSql.append(" having sum(a.amount)<>0");
//			} else if (Xsid == 1) {
//				strSql.append(" having sum(a.amount)=0");
//			}
//		}
//
//		qp.setQueryString(strSql.toString());
//		if (Sortlist.equals(""))
//			Sortlist = "keyid";
//		else
//			Sortlist += ",keyid";
//		qp.setSortString(Sortlist);
//		qp.setSumString(Sumlist);
//		qp.setCalcfield(Calcfield);
//
//		return DbHelperSQL.GetTable(qp);
//	}

	public int doGetWareandcolor() {

		String qry = " select wareno,warename from warecode where accid=" + Accid + " and wareid=" + Wareid;
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		int rs = tb.getRowCount();
		String retcs = "\"WARENO\":\"" + tb.getRow(0).get("WARENO").toString() + "\""//
				+ ",\"WARENAME\":\"" + tb.getRow(0).get("WARENAME").toString() + "\"";

		qry = " select colorname from colorcode where accid=" + Accid + " and colorid=" + Colorid;
		tb = DbHelperSQL.Query(qry).getTable(1);
		// for (int j = 0; j < tb.getColumnCount(); j++) {
		// if (j > 0)
		// retcs += ",";
		// String strKey = tb.getRow(0).getColumn(j + 1).getName().toUpperCase();
		// String strValue = tb.getRow(0).get(j).toString();
		retcs += ",\"COLORNAME\":\"" + tb.getRow(0).get("COLORNAME").toString() + "\"";
		// }

		// 取尺码
		qry = "select a.sizeid,a.sizename,a.sizeno  FROM sizecode a";
		qry += "\n  left outer join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno";
		qry += "\n  where a.statetag=1 and a.noused=0 and a.accid=" + Accid + " and b.wareid=" + Wareid;
		qry += "\n  and not exists (select 1 from warenosize a1 where b.wareid=a1.wareid and a.sizeid=a1.sizeid)";
		qry += "\n  order by a.sizeno,a.sizename";
		tb = DbHelperSQL.Query(qry).getTable(1);
		// fh = "";
		rs = tb.getRowCount();
		// {"colorlist":[{"COLORID":"1","COLORNAME":"红色"},{"COLORID":"2","COLORNAME":"黄色"}]
		// ,"sizelist":[{"SIZEID":"1","SIZENAME":"XS","SIZENO":"01"},{"SIZEID":"2","SIZENAME":"S","SIZENO":"02"},{"SIZEID":"3","SIZENAME":"M","SIZENO":"03"},{"SIZEID":"4","SIZENAME":"L","SIZENO":"04"},{"SIZEID":"5","SIZENAME":"XL","SIZENO":"05"},{"SIZEID":"6","SIZENAME":"XXL","SIZENO":"06"},{"SIZEID":"7","SIZENAME":"3XL","SIZENO":"07"}]}

		retcs += ",\"sizelist\":[";
		String sumsql = "";

		for (int i = 0; i < rs; i++) {
			if (i > 0) {
				retcs += ",";
				sumsql += ",";
			}
			long sizeid = Long.parseLong(tb.getRow(i).get("SIZEID").toString());
			retcs += "{\"SIZEID\":\"" + sizeid + "\"" //
					+ ",\"SIZENAME\":\"" + tb.getRow(i).get("SIZENAME").toString() + "\"}";
			sumsql += "sum(case a.sizeid when " + sizeid + " then a.amount else 0 end) as amount" + sizeid;
		}
		retcs += "],\"sumsql\":\"" + sumsql + "\"";

		errmess = retcs;
		return 1;
	}

}
