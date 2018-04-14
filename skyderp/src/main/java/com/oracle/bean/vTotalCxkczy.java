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
public class vTotalCxkczy {

	private String Fieldlist = ""; // 要显示的项目列表
	private String Grouplist = ""; // 分组的项目列表
	private String Sortlist = ""; // 排序的项目列表
	private String Sumlist = ""; // 要求和的项目列表
	private String Calcfield = ""; // 要求和的项目列表

	private String Serverdatetime = "";// 服务器当前时间,-------------必传
	private String Cxdatetime = ""; // 查询库存日期时间 格式 yyyy-MM-dd
									// HH:mm:ss,------------必传

	private String Accbegindate = "2000-01-01"; // 账套开始使用时间
	private String Calcdate = ""; // 账套登账时间

	private Integer Bj = 0; // bj:1=只查询v_xskczy_data 中的商品的库存,2=减配货占用(返回可用库存)
							// ,3=只查询v_salewhere_data中的商品的库存,4=指定商品，颜色库存,5=库存报警
	private Long Nohouseid = (long) 0; // 不查询的店铺id bj=4时有效
	private Integer Bj0 = 0; // bj=3时，bj0=1表示只查询有销售的库存

	private Integer Qxbj = 0;// 1=启用权限控
	private Integer Nohouse = 0; // 1=不分店铺
	private Integer Retday = 0; // 最近退货天数

	private Long Accid = (long) 0;
	private Long Userid = (long) 0;

	private Long Wareid = (long) 0;

	private Long Houseid = (long) 0;

	private Long Colorid = (long) 0;

	private Long Sizeid = (long) 0;
	private Long Typeid = (long) -1;
	private Long Brandid = (long) -1;
	private Long Provid = (long) -1;
	private Long Areaid = (long) -1;

	private String Wareno = ""; // 配匹货号查询
	private String Wareno1 = ""; // 指定货号查询
	private String Warename = "";
	private String Seasonname = "";
	private String Colorname = "";
	private String Sizename = "";

	private String Houseidlist = "";
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
	private Long Paid = (long) 0; // 搭配id
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

	// int hzfs = jsonObject.has("hzfs") ?
	// Integer.parseInt(jsonObject.getString("hzfs")) : 0;
	// // hzfs:0=货号，1=货号+颜色，2=货号+颜色+尺码
	// int sortid = jsonObject.has("sortid") ?
	// Integer.parseInt(jsonObject.getString("sortid")) : 0;
	// // 排序方式0=汇总项目， 1=数量 2=金额
	// int xsid = jsonObject.has("xsid") ?
	// Integer.parseInt(jsonObject.getString("xsid")) : 0;

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
	private Float Maxretailsale = (float) -1;
	private Float Minretailsale = (float) -1;
	private Integer Issxy = 0;

	public void setIssxy(Integer issxy) {
		Issxy = issxy;
	}

	private String errmess = "";

	public Long getHouseid() {
		return Houseid;
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

	public void setRetday(Integer retday) {
		Retday = retday;
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

	public void setColorname(String colorname) {
		Colorname = colorname;
	}

	public void setSizename(String sizename) {
		Sizename = sizename;
	}

	public void setBj(Integer bj) {
		Bj = bj;
	}

	public void setBj0(Integer bj0) {
		Bj0 = bj0;
	}

	public void setNohouse(Integer nohouse) {
		Nohouse = nohouse;
	}

	public void setQxbj(Integer qxbj) {
		Qxbj = qxbj;
	}

	public void setPaid(Long paid) {
		Paid = paid;
	}

	public void setErrmess(String errmess) {
		this.errmess = errmess;
	}

	public void setWareid(Long wareid) {
		Wareid = wareid;
	}

	public void setWareno(String wareno) {
		Wareno = wareno;
	}

	public void setWareno1(String wareno) {
		Wareno1 = wareno;
	}

	public void setHouseid(Long houseid) {
		Houseid = houseid;
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

	public void setHouseidlist(String houseidlist) {
		Houseidlist = houseidlist;
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

	public void setMaxretailsale(Float maxretailsale) {
		Maxretailsale = maxretailsale;
	}

	public void setMinretailsale(Float minretailsale) {
		Minretailsale = minretailsale;
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
		boolean daybj = false;//true=表示查询指定全天的库存
		String nowdate2 = "";
		if (Cxdatetime.equals("")) {// 如果查询日期未指定，则默认查询当前日期的库存
			Cxdatetime = Func.DateToStr(pFunc.getServerdatetime(), "yyyy-MM-dd") + " 23:59:59";
			nowdate2 = Cxdatetime;
			//			daybj = true;
		} else {
			if (Cxdatetime.length() != 10 && Cxdatetime.length() != 19) {
				errmess = "日期格式无效！";
				return 0;
			}
			if (Cxdatetime.length() == 10)// 如果查询日期只有年月日，自动默认时间,
			{
				Cxdatetime += " 23:59:59";
				//				daybj = true;
			}
			if (Serverdatetime.equals(""))// 如果服务器时间未传入，自动取服务器时间
				Serverdatetime = Func.DateToStr(pFunc.getServerdatetime(), "yyyy-MM-dd HH:mm:ss");
			// 最大的查询时间
			nowdate2 = Func.subString(Serverdatetime, 1, 10) + " 23:59:59";// nowday.ToString("yyyy-MM-dd")
			if (Cxdatetime.compareTo(nowdate2) < 0)
				nowdate2 = Cxdatetime;
		}

		if (Houseidlist.length() <= 0 && Houseid > 0)
			Houseidlist = "" + Houseid;
		if (Brandidlist.length() <= 0 && Brandid >= 0)
			Brandidlist = "" + Brandid;
		if (Providlist.length() <= 0 && Provid >= 0)
			Providlist = "" + Provid;
		if (Areaidlist.length() <= 0 && Areaid > 0)
			Areaidlist = "" + Areaid;
		Wareno = Wareno.toUpperCase();
		Prodno = Prodno.toUpperCase();
		String qry = "";
		//		System.out.println("Cxdatetime=" + Cxdatetime + " calcdate=" + Calcdate);
		String nowdate0 = Func.DateToStr(Func.getDataAdd(nowdate2, -1)); // nowday.AddDays(-1).ToString("yyyy-MM-dd");
		String nowdate1 = Func.subString(nowdate2, 1, 10) + " 00:00:00";
//		System.out.println("Cxdatetime=" + Cxdatetime);

		if (Func.strRight(Cxdatetime, 8).compareTo("23:59:59") == 0 && Func.subString(Cxdatetime, 1, 10).compareTo(Calcdate) <= 0) {
			nowdate0 = Func.subString(Cxdatetime, 1, 10);
			nowdate1 = nowdate0;
//			System.out.println("查询全天的数据：" + nowdate0);
			daybj = true;
		} else {
			//			daybj = false;
			nowdate0 = Func.DateToStr(Func.getDataAdd(nowdate2, -1)); // nowday.AddDays(-1).ToString("yyyy-MM-dd");
			nowdate1 = Func.subString(nowdate2, 1, 10) + " 00:00:00";
			if (Calcdate.length() > 0) {
				if (Calcdate.compareTo(nowdate0) < 0) {
					nowdate0 = Calcdate;// Func.DateToStr(Func.getDataAdd(Calcdate,
					nowdate1 = Func.DateToStr(Func.getDataAdd(nowdate0, 1)) + " 00:00:00";
				}
			}
		}

		//		System.out.println("totalcxkczy: nowdate0=" + nowdate0 + " nowdate1=" + nowdate1 + " nowdate2=" + nowdate2);

		// System.out.println("Houseidlist="+Houseidlist);
		// System.out.println("nowdate0=" + nowdate0 + " nowdate1=" + nowdate1 +
		// " nowdate2=" + nowdate2);
		qry = " declare";
		qry += "\n    v_begindate varchar2(10); ";
		qry += "\n begin ";
		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n       select ROWID from v_cxkczy_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n            delete from v_cxkczy_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";
		if (Bj == 2)
			qry += "\n    select to_char(begindate,'yyyy-mm-dd') into v_begindate from accreg where accid=" + Accid + ";";

		qry += "\n     insert into v_cxkczy_data (id,epid,Houseid,Wareid,Colorid,Sizeid,amount) ";
		qry += "\n     select v_cxkczy_data_id.nextval," + Userid + ", Houseid,Wareid,Colorid,Sizeid,amount from ( ";

		if (Nohouse == 1)// 不分店铺汇总库存
			qry += "\n     select Wareid,Colorid,Sizeid,0 as houseid,nvl(sum(amount),0) as amount ";

		else
			qry += "\n     select Wareid,Colorid,Sizeid,Houseid,nvl(sum(amount),0) as amount ";
		qry += "\n     from (  ";
		// --期初库存
		// qry += "\n select /*+ordered index(a IX1_WARESUM) */
		// a.Houseid,a.Wareid,a.Colorid,a.Sizeid,a.amount ";
		qry += "\n     select   a.Houseid,a.Wareid,a.Colorid,a.Sizeid,a.amount ";
		qry += "\n     from waresum a ";
		qry += "\n     left outer join warecode b on a.Wareid=b.Wareid ";
		qry += "\n     where a.daystr='" + nowdate0 + "' and (a.amount>0 or a.amount<0)  and b.statetag=1 ";
		if (Bj == 1) // 只查询v_xskczy_data 中的商品的库存
		{
			qry += "\n   and exists (select 1 from v_xskczy_data a1 where a1.Houseid=a.Houseid and a1.Wareid=a.Wareid ";
			// qry += "\n and a1.Colorid=a.Colorid and a1.Sizeid=a.Sizeid ";
			qry += "\n   and a1.epid=" + Userid + ")";
		} else if (Bj == 3) // 只查询v_salewhere_data中的商品的库存
		{
			qry += "\n   and exists (select 1 from v_salewhere_data a1 where a1.Wareid=a.Wareid ";
			if (Bj0 == 1)// bj=3时，bj0=1表示只查询有销售的商品的库存
				qry += "\n   and a1.Colorid=a.Colorid and a1.Sizeid=a.Sizeid ";
			qry += "\n   and a1.epid=" + Userid + ")";

		} else if (Bj == 4) // TotalWareandcolor 汇总指定商品，颜色的店铺尺码分布
		{
			qry += "\n     and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
			if (Nohouseid > 0) // 不查询指定店铺数据
				qry += "\n  and a.houseid<>" + Nohouseid;

		} else if (Bj == 5) {// 库存报警
			qry += "\n     and exists (select 1 from warewarn x1 where a.Houseid=x1.Houseid and a.wareid=x1.wareid and a.colorid=x1.colorid and a.sizeid=x1.sizeid) ";
		}
		if (Paid > 0) {
			qry += "\n   and exists (select 1 from vippairs1 a1 where a1.Wareid=a.Wareid and a1.Paid=" + Paid + ")";
		}
		qry += "\n     and b.accid=" + Accid + " ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
			qry += "\n     and (b.Brandid=0 or exists (select 1 from employebrand x2 where b.Brandid=x2.Brandid and x2.epid=" + Userid + " )) ";
			if (Issxy == 1)// 上下衣要判断区位
				qry += "\n     and (b.areaid=0 or exists (select 1 from emplarea x3 where b.areaid=x3.areaid and x3.epid=" + Userid + "))";
		}
		if (Wareid > 0)
			qry += "      and a.Wareid=" + Wareid;
		if (Colorid > 0)
			qry += "      and a.Colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "      and a.Sizeid=" + Sizeid;
		if (!Wareno.equals(""))
			qry += "      and b.Wareno like '%" + Wareno + "%'";

		if (Wareno1.length() > 0)
			qry += "      and b.Wareno = '" + Wareno1 + "'";

		// if (Colorname.length() > 0)
		// qry += "\n and exists (select 1 from colorcode x1 where x1.accid=" +
		// Accid + " and a.colorid=x1.colorid and x1.colorname = '" + Colorname
		// + "' and x1.statetag=1)";
		// if (Sizename.length() > 0)
		// qry += "\n and exists (select 1 from sizecode x2 where x2.accid=" +
		// Accid + " and a.sizeid=x2.sizeid and b.sizegroupno=x2.groupno and
		// x2.sizename = '" + Sizename + "' and x2.statetag=1)";

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
		if (Minretailsale >= 0)
			qry += " and b.retailsale>=" + Minretailsale;
		if (Maxretailsale >= 0)
			qry += " and b.retailsale<=" + Maxretailsale;
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
		if (!Typeidlist.equals(""))
			qry += " and " + Func.getCondition("b.typeid", Typeidlist);

		if (!Brandidlist.equals(""))
			qry += " and " + Func.getCondition("b.brandid", Brandidlist);
		if (!Providlist.equals(""))
			qry += " and " + Func.getCondition("b.provid", Providlist);
		if (!Areaidlist.equals(""))
			qry += " and " + Func.getCondition("b.areaid", Areaidlist);

		if (!Houseidlist.equals(""))
			qry += " and " + Func.getCondition("a.Houseid", Houseidlist);

		if (Provid >= 0)
			qry += "      and b.Provid=" + Provid;
		// if (Areaid >= 0)
		// qry += " and b.Areaid=" + Areaid;
		if (Paid > 0) {
			qry += "\n   and exists (select 1 from vippairs1 a1 where a1.Wareid=a.Wareid and a1.Paid=" + Paid + ")";
		}
		if (Retday > 0) // 退货截止天数
			qry += " and f_isdate(b.retdatestr)=1 and SUBSTR(b.retdatestr,6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(b.retdatestr, 6,5) <= to_char(sysdate + " + Retday + ", 'mm-dd')";
		// --初始入库
		if (!daybj) {
			qry += "\n     union all ";
			qry += "\n     select a.Houseid,b.Wareid,b.Colorid,b.Sizeid,sum(b.amount) as amount ";
			qry += "\n     from firsthouseh a  ";
			qry += "\n     join firsthousem b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n     left outer join warecode c on b.Wareid=c.Wareid ";
			// qry+=" where a.statetag=1 and
			// a.notedate>=to_date("+nowdate+",'yyyy-mm-dd') and
			// a.notedate<to_date("+nowdate+",'yyyy-mm-dd')+1 ";
			qry += "\n     where a.statetag=1 ";
			qry += "\n     and a.accid=" + Accid + " ";
			qry += "\n     and a.notedate>=to_date('" + nowdate1 + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n     and a.notedate<=to_date('" + nowdate2 + "','yyyy-mm-dd hh24:mi:ss') ";
			if (Bj == 1) // 只查询v_xskczy_data 中的商品的库存
			{
				qry += "\n   and exists (select 1 from v_xskczy_data a1 where a1.Houseid=a.Houseid and a1.Wareid=b.Wareid ";
				// qry += "\n and a1.Colorid=a.Colorid and a1.Sizeid=a.Sizeid ";
				qry += "\n   and a1.epid=" + Userid + ")";
			} else if (Bj == 3) // 只查询v_salewhere_data中的商品的库存
			{
				qry += "\n   and exists (select 1 from v_salewhere_data a1 where a1.Wareid=b.Wareid ";
				if (Bj0 == 1)// bj=3时，bj0=1表示只查询有销售的商品的库存
					qry += "\n   and a1.Colorid=b.Colorid and a1.Sizeid=b.Sizeid ";
				qry += "\n   and a1.epid=" + Userid + ")";

			} else if (Bj == 4) // TotalWareandcolor 汇总指定商品，颜色的店铺尺码分布
			{
				qry += "\n     and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
				if (Nohouseid > 0) // 不查询指定店铺数据
					qry += "\n  and a.houseid<>" + Nohouseid;

			} else if (Bj == 5) {// 库存报警
				qry += "\n     and exists (select 1 from warewarn x1 where a.Houseid=x1.Houseid and b.wareid=x1.wareid and b.colorid=x1.colorid and b.sizeid=x1.sizeid) ";
			}
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
				qry += "\n     and (c.Brandid=0 or exists (select 1 from employebrand x2 where c.Brandid=x2.Brandid and x2.epid=" + Userid + " )) ";
				if (Issxy == 1)// 上下衣要判断区位
					qry += "\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))";
			}
			if (Paid > 0) {
				qry += "\n   and exists (select 1 from vippairs1 a1 where a1.Wareid=b.Wareid and a1.Paid=" + Paid + ")";
			}
			// if (Houseid > 0)
			// qry += " and a.Houseid=" + Houseid;
			if (Wareid > 0)
				qry += "      and b.Wareid=" + Wareid;

			if (Colorid > 0)
				qry += "      and b.Colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "      and b.Sizeid=" + Sizeid;
			if (!Wareno.equals(""))
				qry += "      and c.Wareno like '%" + Wareno + "%'";
			if (Wareno1.length() > 0)
				qry += "      and c.Wareno = '" + Wareno1 + "'";
			// if (Colorname.length() > 0)
			// qry += "\n and exists (select 1 from colorcode x1 where x1.accid=" +
			// Accid + " and b.colorid=x1.colorid and x1.colorname = '" + Colorname
			// + "' and x1.statetag=1)";
			// if (Sizename.length() > 0)
			// qry += "\n and exists (select 1 from sizecode x2 where x2.accid=" +
			// Accid + " and b.sizeid=x2.sizeid and c.sizegroupno=x2.groupno and
			// x2.sizename = '" + Sizename + "' and x2.statetag=1)";

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
			if (Minretailsale >= 0)
				qry += " and c.retailsale>=" + Minretailsale;
			if (Maxretailsale >= 0)
				qry += " and c.retailsale<=" + Maxretailsale;
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "    and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ")";
				} else {
					qry += "    and c.Typeid=" + Typeid;
				}
			if (!Typeidlist.equals(""))
				qry += " and " + Func.getCondition("c.typeid", Typeidlist);
			if (!Brandidlist.equals(""))
				qry += " and " + Func.getCondition("c.brandid", Brandidlist);
			if (!Providlist.equals(""))
				qry += " and " + Func.getCondition("c.provid", Providlist);
			if (!Areaidlist.equals(""))
				qry += " and " + Func.getCondition("c.areaid", Areaidlist);
			if (!Houseidlist.equals(""))
				qry += " and " + Func.getCondition("a.Houseid", Houseidlist);
			if (Retday > 0) // 退货截止天数
				qry += " and f_isdate(c.retdatestr)=1 and SUBSTR(c.retdatestr,6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(c.retdatestr, 6,5) <= to_char(sysdate + " + Retday + ", 'mm-dd')";

			qry += "\n     group by a.Houseid,b.Wareid,b.Colorid,b.Sizeid ";

			// --采购入库
			qry += "\n     union all ";
			qry += "\n     select a.Houseid,b.Wareid,b.Colorid,b.Sizeid,sum(case a.ntid when 0 then b.amount else -b.amount end) as amount ";
			qry += "\n     from wareinh a  ";
			qry += "\n     join wareinm b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n     left outer join warecode c on b.Wareid=c.Wareid ";
			qry += "\n     where a.statetag=1 ";// and a.notedate>=to_date('" +
												// nowdate1 + "','yyyy-mm-dd
												// hh24:mi:ss') and
												// a.notedate<=to_date('" + nowdate2
												// + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n     and a.accid=" + Accid;
			qry += "\n     and a.notedate>=to_date('" + nowdate1 + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n     and a.notedate<=to_date('" + nowdate2 + "','yyyy-mm-dd hh24:mi:ss') ";
			if (Bj == 1) // 只查询v_xskczy_data 中的商品的库存
			{
				qry += "\n   and exists (select 1 from v_xskczy_data a1 where a1.Houseid=a.Houseid and a1.Wareid=b.Wareid ";
				// qry += "\n and a1.Colorid=a.Colorid and a1.Sizeid=a.Sizeid ";
				qry += "\n   and a1.epid=" + Userid + ")";
			} else if (Bj == 3) // 只查询v_salewhere_data中的商品的库存
			{
				qry += "\n   and exists (select 1 from v_salewhere_data a1 where a1.Wareid=b.Wareid ";
				if (Bj0 == 1)// bj=3时，bj0=1表示只查询有销售的商品的库存
					qry += "\n   and a1.Colorid=b.Colorid and a1.Sizeid=b.Sizeid ";
				qry += "\n   and a1.epid=" + Userid + ")";

			} else if (Bj == 4) // TotalWareandcolor 汇总指定商品，颜色的店铺尺码分布
			{
				qry += "\n     and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
				if (Nohouseid > 0) // 不查询指定店铺数据
					qry += "\n  and a.houseid<>" + Nohouseid;

			} else if (Bj == 5) {// 库存报警
				qry += "\n     and exists (select 1 from warewarn x1 where a.Houseid=x1.Houseid and b.wareid=x1.wareid and b.colorid=x1.colorid and b.sizeid=x1.sizeid) ";
			}
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
				qry += "\n     and (c.Brandid=0 or exists (select 1 from employebrand x2 where c.Brandid=x2.Brandid and x2.epid=" + Userid + " )) ";
				if (Issxy == 1)// 上下衣要判断区位
					qry += "\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))";
			}
			if (Paid > 0) {
				qry += "\n   and exists (select 1 from vippairs1 a1 where a1.Wareid=b.Wareid and a1.Paid=" + Paid + ")";
			}
			// if (Houseid > 0)
			// qry += " and a.Houseid=" + Houseid;
			if (Wareid > 0)
				qry += "      and b.Wareid=" + Wareid;
			if (Colorid > 0)
				qry += "      and b.Colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "      and b.Sizeid=" + Sizeid;
			// if (Colorname.length() > 0)
			// qry += "\n and exists (select 1 from colorcode x1 where x1.accid=" +
			// Accid + " and b.colorid=x1.colorid and x1.colorname = '" + Colorname
			// + "' and x1.statetag=1)";
			// if (Sizename.length() > 0)
			// qry += "\n and exists (select 1 from sizecode x2 where x2.accid=" +
			// Accid + " and b.sizeid=x2.sizeid and c.sizegroupno=x2.groupno and
			// x2.sizename = '" + Sizename + "' and x2.statetag=1)";
			if (Wareno1.length() > 0)
				qry += "      and c.Wareno = '" + Wareno1 + "'";
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
			if (Minretailsale >= 0)
				qry += " and c.retailsale>=" + Minretailsale;
			if (Maxretailsale >= 0)
				qry += " and c.retailsale<=" + Maxretailsale;
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

			if (!Typeidlist.equals(""))
				qry += " and " + Func.getCondition("c.typeid", Typeidlist);
			if (!Brandidlist.equals(""))
				qry += " and " + Func.getCondition("c.brandid", Brandidlist);
			if (!Providlist.equals(""))
				qry += " and " + Func.getCondition("c.provid", Providlist);
			if (!Areaidlist.equals(""))
				qry += " and " + Func.getCondition("c.areaid", Areaidlist);
			if (!Houseidlist.equals(""))
				qry += " and " + Func.getCondition("a.Houseid", Houseidlist);
			if (Retday > 0) // 退货截止天数
				qry += " and f_isdate(c.retdatestr)=1 and SUBSTR(c.retdatestr,6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(c.retdatestr, 6,5) <= to_char(sysdate + " + Retday + ", 'mm-dd')";

			// if (Provid >= 0)
			// qry += " and c.Provid=" + Provid;
			// if (Areaid >= 0)
			// qry += " and c.Areaid=" + Areaid;
			qry += "\n     group by a.Houseid,b.Wareid,b.Colorid,b.Sizeid ";

			// --调拨入库
			qry += "\n     union all ";
			qry += "\n     select a.Houseid,b.Wareid,b.Colorid,b.Sizeid,sum(b.amount) as amount ";
			qry += "\n     from allotinh a  ";
			qry += "\n     join allotinm b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n     left outer join warecode c on b.Wareid=c.Wareid ";
			qry += "\n     where a.statetag=1 ";// and a.notedate>=to_date('" +
												// nowdate1 + "','yyyy-mm-dd
												// hh24:mi:ss') and
												// a.notedate<=to_date('" + nowdate2
												// + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n     and a.accid=" + Accid;
			qry += "\n     and a.notedate>=to_date('" + nowdate1 + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n     and a.notedate<=to_date('" + nowdate2 + "','yyyy-mm-dd hh24:mi:ss') ";
			if (Bj == 1) // 只查询v_xskczy_data 中的商品的库存
			{
				qry += "\n   and exists (select 1 from v_xskczy_data a1 where a1.Houseid=a.Houseid and a1.Wareid=b.Wareid ";
				// qry += "\n and a1.Colorid=a.Colorid and a1.Sizeid=a.Sizeid ";
				qry += "\n   and a1.epid=" + Userid + ")";
			} else if (Bj == 3) // 只查询v_salewhere_data中的商品的库存
			{
				qry += "\n   and exists (select 1 from v_salewhere_data a1 where a1.Wareid=b.Wareid ";
				if (Bj0 == 1)// bj=3时，bj0=1表示只查询有销售的商品的库存
					qry += "\n   and a1.Colorid=b.Colorid and a1.Sizeid=b.Sizeid ";
				qry += "\n   and a1.epid=" + Userid + ")";

			} else if (Bj == 4) // TotalWareandcolor 汇总指定商品，颜色的店铺尺码分布
			{
				qry += "\n     and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
				if (Nohouseid > 0) // 不查询指定店铺数据
					qry += "\n  and a.houseid<>" + Nohouseid;

			} else if (Bj == 5) {// 库存报警
				qry += "\n     and exists (select 1 from warewarn x1 where a.Houseid=x1.Houseid and b.wareid=x1.wareid and b.colorid=x1.colorid and b.sizeid=x1.sizeid) ";
			}
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
				qry += "\n     and (c.Brandid=0 or exists (select 1 from employebrand x2 where c.Brandid=x2.Brandid and x2.epid=" + Userid + " )) ";
				if (Issxy == 1)// 上下衣要判断区位
					qry += "\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))";
			}
			if (Paid > 0) {
				qry += "\n   and exists (select 1 from vippairs1 a1 where a1.Wareid=b.Wareid and a1.Paid=" + Paid + ")";
			}
			// if (Houseid > 0)
			// qry += " and a.Houseid=" + Houseid;
			if (Wareid > 0)
				qry += "      and b.Wareid=" + Wareid;
			if (Colorid > 0)
				qry += "      and b.Colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "      and b.Sizeid=" + Sizeid;
			// if (Colorname.length() > 0)
			// qry += "\n and exists (select 1 from colorcode x1 where x1.accid=" +
			// Accid + " and b.colorid=x1.colorid and x1.colorname = '" + Colorname
			// + "' and x1.statetag=1)";
			// if (Sizename.length() > 0)
			// qry += "\n and exists (select 1 from sizecode x2 where x2.accid=" +
			// Accid + " and b.sizeid=x2.sizeid and c.sizegroupno=x2.groupno and
			// x2.sizename = '" + Sizename + "' and x2.statetag=1)";
			if (Wareno1.length() > 0)
				qry += "      and c.Wareno = '" + Wareno1 + "'";
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
			if (Minretailsale >= 0)
				qry += " and c.retailsale>=" + Minretailsale;
			if (Maxretailsale >= 0)
				qry += " and c.retailsale<=" + Maxretailsale;
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

			if (!Typeidlist.equals(""))
				qry += " and " + Func.getCondition("c.typeid", Typeidlist);
			// if (Brandid >= 0)
			// qry += " and c.Brandid=" + Brandid;
			if (!Brandidlist.equals(""))
				qry += " and " + Func.getCondition("c.brandid", Brandidlist);
			if (!Providlist.equals(""))
				qry += " and " + Func.getCondition("c.provid", Providlist);
			if (!Areaidlist.equals(""))
				qry += " and " + Func.getCondition("c.areaid", Areaidlist);
			if (!Houseidlist.equals(""))
				qry += " and " + Func.getCondition("a.Houseid", Houseidlist);
			if (Retday > 0) // 退货截止天数
				qry += " and f_isdate(c.retdatestr)=1 and SUBSTR(c.retdatestr,6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(c.retdatestr, 6,5) <= to_char(sysdate + " + Retday + ", 'mm-dd')";

			// if (Provid >= 0)
			// qry += " and c.Provid=" + Provid;
			// if (Areaid >= 0)
			// qry += " and c.Areaid=" + Areaid;
			qry += "\n     group by a.Houseid,b.Wareid,b.Colorid,b.Sizeid ";

			// --销售出库
			qry += "\n     union all ";
			qry += "\n     select a.Houseid,b.Wareid,b.Colorid,b.Sizeid,sum(case a.ntid when 2 then b.amount else -b.amount end) as amount ";
			qry += "\n     from wareouth a  ";
			qry += "\n     join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n     left outer join warecode c on b.Wareid=c.Wareid ";
			qry += "\n     where a.statetag=1 ";// and a.notedate>=to_date('" +
												// nowdate1 + "','yyyy-mm-dd
												// hh24:mi:ss') and
												// a.notedate<=to_date('" + nowdate2
												// + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n     and a.accid=" + Accid;
			qry += "\n     and a.notedate>=to_date('" + nowdate1 + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n     and a.notedate<=to_date('" + nowdate2 + "','yyyy-mm-dd hh24:mi:ss') ";
			if (Bj == 1) // 只查询v_xskczy_data 中的商品的库存
			{
				qry += "\n   and exists (select 1 from v_xskczy_data a1 where a1.Houseid=a.Houseid and a1.Wareid=b.Wareid ";
				// qry += "\n and a1.Colorid=a.Colorid and a1.Sizeid=a.Sizeid ";
				qry += "\n   and a1.epid=" + Userid + ")";
			} else if (Bj == 3) // 只查询v_salewhere_data中的商品的库存
			{
				qry += "\n   and exists (select 1 from v_salewhere_data a1 where a1.Wareid=b.Wareid ";
				if (Bj0 == 1)// bj=3时，bj0=1表示只查询有销售的商品的库存
					qry += "\n   and a1.Colorid=b.Colorid and a1.Sizeid=b.Sizeid ";
				qry += "\n   and a1.epid=" + Userid + ")";
			} else if (Bj == 4) // TotalWareandcolor 汇总指定商品，颜色的店铺尺码分布
			{
				qry += "\n     and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
				if (Nohouseid > 0) // 不查询指定店铺数据
					qry += "\n  and a.houseid<>" + Nohouseid;

			} else if (Bj == 5) {// 库存报警
				qry += "\n     and exists (select 1 from warewarn x1 where a.Houseid=x1.Houseid and b.wareid=x1.wareid and b.colorid=x1.colorid and b.sizeid=x1.sizeid) ";
			}
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
				qry += "\n     and (c.Brandid=0 or exists (select 1 from employebrand x2 where c.Brandid=x2.Brandid and x2.epid=" + Userid + " )) ";
				if (Issxy == 1)// 上下衣要判断区位
					qry += "\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))";
			}
			if (Paid > 0) {
				qry += "\n   and exists (select 1 from vippairs1 a1 where a1.Wareid=b.Wareid and a1.Paid=" + Paid + ")";
			}
			// if (Houseid > 0)
			// qry += " and a.Houseid=" + Houseid;
			if (Wareid > 0)
				qry += "      and b.Wareid=" + Wareid;
			if (Colorid > 0)
				qry += "      and b.Colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "      and b.Sizeid=" + Sizeid;
			// if (Colorname.length() > 0)
			// qry += "\n and exists (select 1 from colorcode x1 where x1.accid=" +
			// Accid + " and b.colorid=x1.colorid and x1.colorname = '" + Colorname
			// + "' and x1.statetag=1)";
			// if (Sizename.length() > 0)
			// qry += "\n and exists (select 1 from sizecode x2 where x2.accid=" +
			// Accid + " and b.sizeid=x2.sizeid and c.sizegroupno=x2.groupno and
			// x2.sizename = '" + Sizename + "' and x2.statetag=1)";
			if (Wareno1.length() > 0)
				qry += "      and c.Wareno = '" + Wareno1 + "'";
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
			if (Minretailsale >= 0)
				qry += " and c.retailsale>=" + Minretailsale;
			if (Maxretailsale >= 0)
				qry += " and c.retailsale<=" + Maxretailsale;
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

			if (!Typeidlist.equals(""))
				qry += " and " + Func.getCondition("c.typeid", Typeidlist);
			// if (Brandid >= 0)
			// qry += " and c.Brandid=" + Brandid;
			if (!Brandidlist.equals(""))
				qry += " and " + Func.getCondition("c.brandid", Brandidlist);
			if (!Providlist.equals(""))
				qry += " and " + Func.getCondition("c.provid", Providlist);
			if (!Areaidlist.equals(""))
				qry += " and " + Func.getCondition("c.areaid", Areaidlist);
			if (!Houseidlist.equals(""))
				qry += " and " + Func.getCondition("a.Houseid", Houseidlist);
			if (Retday > 0) // 退货截止天数
				qry += " and f_isdate(c.retdatestr)=1 and SUBSTR(c.retdatestr,6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(c.retdatestr, 6,5) <= to_char(sysdate + " + Retday + ", 'mm-dd')";

			// if (Provid >= 0)
			// qry += " and c.Provid=" + Provid;
			// if (Areaid >= 0)
			// qry += " and c.Areaid=" + Areaid;
			qry += "\n     group by a.Houseid,b.Wareid,b.Colorid,b.Sizeid ";

			// --商场销售出库
			qry += "\n     union all ";
			qry += "\n     select a.Houseid,b.Wareid,b.Colorid,b.Sizeid,-sum(b.amount) as amount ";
			qry += "\n     from shopsaleh a  ";
			qry += "\n     join shopsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n     left outer join warecode c on b.Wareid=c.Wareid ";
			qry += "\n     where a.statetag=1 ";// and a.notedate>=to_date('" +
												// nowdate1 + "','yyyy-mm-dd
												// hh24:mi:ss') and
												// a.notedate<=to_date('" + nowdate2
												// + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n     and a.accid=" + Accid + " ";
			qry += "\n     and a.notedate>=to_date('" + nowdate1 + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n     and a.notedate<=to_date('" + nowdate2 + "','yyyy-mm-dd hh24:mi:ss') ";
			if (Bj == 1) // 只查询v_xskczy_data 中的商品的库存
			{
				qry += "\n   and exists (select 1 from v_xskczy_data a1 where a1.Houseid=a.Houseid and a1.Wareid=b.Wareid ";
				// qry += "\n and a1.Colorid=a.Colorid and a1.Sizeid=a.Sizeid ";
				qry += "\n   and a1.epid=" + Userid + ")";
			} else if (Bj == 3) // 只查询v_salewhere_data中的商品的库存
			{
				qry += "\n   and exists (select 1 from v_salewhere_data a1 where a1.Wareid=b.Wareid ";
				if (Bj0 == 1)// bj=3时，bj0=1表示只查询有销售的商品的库存
					qry += "\n   and a1.Colorid=b.Colorid and a1.Sizeid=b.Sizeid ";
				qry += "\n   and a1.epid=" + Userid + ")";

			} else if (Bj == 4) // TotalWareandcolor 汇总指定商品，颜色的店铺尺码分布
			{
				qry += "\n     and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
				if (Nohouseid > 0) // 不查询指定店铺数据
					qry += "\n  and a.houseid<>" + Nohouseid;

			} else if (Bj == 5) {// 库存报警
				qry += "\n     and exists (select 1 from warewarn x1 where a.Houseid=x1.Houseid and b.wareid=x1.wareid and b.colorid=x1.colorid and b.sizeid=x1.sizeid) ";
			}
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
				qry += "\n     and (c.Brandid=0 or exists (select 1 from employebrand x2 where c.Brandid=x2.Brandid and x2.epid=" + Userid + " )) ";
				if (Issxy == 1)// 上下衣要判断区位
					qry += "\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))";
			}
			if (Paid > 0) {
				qry += "\n   and exists (select 1 from vippairs1 a1 where a1.Wareid=b.Wareid and a1.Paid=" + Paid + ")";
			}
			// if (Houseid > 0)
			// qry += " and a.Houseid=" + Houseid;
			if (Wareid > 0)
				qry += "      and b.Wareid=" + Wareid;
			if (Colorid > 0)
				qry += "      and b.Colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "      and b.Sizeid=" + Sizeid;
			if (Wareno1.length() > 0)
				qry += "      and c.Wareno = '" + Wareno1 + "'";
			if (!Wareno.equals(""))
				qry += "      and c.Wareno like '%" + Wareno + "%'";
			// if (Colorname.length() > 0)
			// qry += "\n and exists (select 1 from colorcode x1 where x1.accid=" +
			// Accid + " and b.colorid=x1.colorid and x1.colorname = '" + Colorname
			// + "' and x1.statetag=1)";
			// if (Sizename.length() > 0)
			// qry += "\n and exists (select 1 from sizecode x2 where x2.accid=" +
			// Accid + " and b.sizeid=x2.sizeid and c.sizegroupno=x2.groupno and
			// x2.sizename = '" + Sizename + "' and x2.statetag=1)";
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
			if (Minretailsale >= 0)
				qry += " and c.retailsale>=" + Minretailsale;
			if (Maxretailsale >= 0)
				qry += " and c.retailsale<=" + Maxretailsale;
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

			if (!Typeidlist.equals(""))
				qry += " and " + Func.getCondition("c.typeid", Typeidlist);
			// if (Brandid >= 0)
			// qry += " and c.Brandid=" + Brandid;
			if (!Brandidlist.equals(""))
				qry += " and " + Func.getCondition("c.brandid", Brandidlist);
			if (!Providlist.equals(""))
				qry += " and " + Func.getCondition("c.provid", Providlist);
			if (!Areaidlist.equals(""))
				qry += " and " + Func.getCondition("c.areaid", Areaidlist);
			if (!Houseidlist.equals(""))
				qry += " and " + Func.getCondition("a.Houseid", Houseidlist);
			if (Retday > 0) // 退货截止天数
				qry += " and f_isdate(c.retdatestr)=1 and SUBSTR(c.retdatestr,6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(c.retdatestr, 6,5) <= to_char(sysdate + " + Retday + ", 'mm-dd')";

			// if (Provid >= 0)
			// qry += " and c.Provid=" + Provid;
			// if (Areaid >= 0)
			// qry += " and c.Areaid=" + Areaid;
			qry += "\n     group by a.Houseid,b.Wareid,b.Colorid,b.Sizeid ";

			// --调拨出库
			qry += "\n     union all ";
			qry += "\n     select a.Houseid,b.Wareid,b.Colorid,b.Sizeid,-sum(b.amount) as amount ";
			qry += "\n     from allotouth a  ";
			qry += "\n     join allotoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n     left outer join warecode c on b.Wareid=c.Wareid ";
			qry += "\n     where a.statetag=1 ";// and a.notedate>=to_date('" +
												// nowdate1 + "','yyyy-mm-dd
												// hh24:mi:ss') and
												// a.notedate<=to_date('" + nowdate2
												// + "','yyyy-mm-dd hh24:mi:ss')";
			qry += "\n     and a.accid=" + Accid + " ";
			qry += "\n     and a.notedate>=to_date('" + nowdate1 + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n     and a.notedate<=to_date('" + nowdate2 + "','yyyy-mm-dd hh24:mi:ss') ";
			if (Bj == 1) // 只查询v_xskczy_data 中的商品的库存
			{
				qry += "\n   and exists (select 1 from v_xskczy_data a1 where a1.Houseid=a.Houseid and a1.Wareid=b.Wareid ";
				// qry += "\n and a1.Colorid=a.Colorid and a1.Sizeid=a.Sizeid ";
				qry += "\n   and a1.epid=" + Userid + ")";
			} else if (Bj == 3) // 只查询v_salewhere_data中的商品的库存
			{
				qry += "\n   and exists (select 1 from v_salewhere_data a1 where a1.Wareid=b.Wareid ";
				if (Bj0 == 1)// bj=3时，bj0=1表示只查询有销售的商品的库存
					qry += "\n   and a1.Colorid=b.Colorid and a1.Sizeid=b.Sizeid ";
				qry += "\n   and a1.epid=" + Userid + ")";

			} else if (Bj == 4) // TotalWareandcolor 汇总指定商品，颜色的店铺尺码分布
			{
				qry += "\n     and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
				if (Nohouseid > 0) // 不查询指定店铺数据
					qry += "\n  and a.houseid<>" + Nohouseid;

			} else if (Bj == 5) {// 库存报警
				qry += "\n     and exists (select 1 from warewarn x1 where a.Houseid=x1.Houseid and b.wareid=x1.wareid and b.colorid=x1.colorid and b.sizeid=x1.sizeid) ";
			}
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
				qry += "\n     and (c.Brandid=0 or exists (select 1 from employebrand x2 where c.Brandid=x2.Brandid and x2.epid=" + Userid + " )) ";
				if (Issxy == 1)// 上下衣要判断区位
					qry += "\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))";
			}
			if (Paid > 0) {
				qry += "\n   and exists (select 1 from vippairs1 a1 where a1.Wareid=b.Wareid and a1.Paid=" + Paid + ")";
			}
			// if (Houseid > 0)
			// qry += " and a.Houseid=" + Houseid;
			if (Wareid > 0)
				qry += "      and b.Wareid=" + Wareid;
			if (Colorid > 0)
				qry += "      and b.Colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "      and b.Sizeid=" + Sizeid;
			if (Wareno1.length() > 0)
				qry += "      and c.Wareno = '" + Wareno1 + "'";
			if (!Wareno.equals(""))
				qry += "      and c.Wareno like '%" + Wareno + "%'";
			// if (Colorname.length() > 0)
			// qry += "\n and exists (select 1 from colorcode x1 where x1.accid=" +
			// Accid + " and b.colorid=x1.colorid and x1.colorname = '" + Colorname
			// + "' and x1.statetag=1)";
			// if (Sizename.length() > 0)
			// qry += "\n and exists (select 1 from sizecode x2 where x2.accid=" +
			// Accid + " and b.sizeid=x2.sizeid and c.sizegroupno=x2.groupno and
			// x2.sizename = '" + Sizename + "' and x2.statetag=1)";

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
			if (Minretailsale >= 0)
				qry += " and c.retailsale>=" + Minretailsale;
			if (Maxretailsale >= 0)
				qry += " and c.retailsale<=" + Maxretailsale;
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
			if (!Typeidlist.equals(""))
				qry += " and " + Func.getCondition("c.typeid", Typeidlist);

			// if (Brandid >= 0)
			// qry += " and c.Brandid=" + Brandid;
			if (!Brandidlist.equals(""))
				qry += " and " + Func.getCondition("c.brandid", Brandidlist);
			if (!Providlist.equals(""))
				qry += " and " + Func.getCondition("c.provid", Providlist);
			if (!Areaidlist.equals(""))
				qry += " and " + Func.getCondition("c.areaid", Areaidlist);
			if (!Houseidlist.equals(""))
				qry += " and " + Func.getCondition("a.Houseid", Houseidlist);
			if (Retday > 0) // 退货截止天数
				qry += " and f_isdate(c.retdatestr)=1 and SUBSTR(c.retdatestr,6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(c.retdatestr, 6,5) <= to_char(sysdate + " + Retday + ", 'mm-dd')";

			// if (Provid >= 0)
			// qry += " and c.Provid=" + Provid;
			// if (Areaid >= 0)
			// qry += " and c.Areaid=" + Areaid;
			qry += "\n     group by a.Houseid,b.Wareid,b.Colorid,b.Sizeid ";

			// --盘点
			qry += "\n     union all ";
			qry += "\n     select a.Houseid,b.Wareid,b.Colorid,b.Sizeid,sum(b.amount) as amount ";
			qry += "\n     from warecheckh a  ";
			qry += "\n     join warecheckm b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n     left outer join warecode c on b.Wareid=c.Wareid ";
			qry += "\n     where a.statetag=1 and b.amount<>0 ";
			// and
			// a.notedate>=to_date('"
			// + nowdate1 +
			// "','yyyy-mm-dd
			// hh24:mi:ss') and
			// a.notedate<=to_date('"
			// + nowdate2 +
			// "','yyyy-mm-dd
			// hh24:mi:ss') ";
			qry += "\n     and a.accid=" + Accid + " ";
			qry += "\n     and a.notedate>=to_date('" + nowdate1 + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n     and a.notedate<=to_date('" + nowdate2 + "','yyyy-mm-dd hh24:mi:ss') ";
			if (Bj == 1) // 只查询v_xskczy_data 中的商品的库存
			{
				qry += "\n   and exists (select 1 from v_xskczy_data a1 where a1.Houseid=a.Houseid and a1.Wareid=b.Wareid ";
				// qry += "\n and a1.Colorid=a.Colorid and a1.Sizeid=a.Sizeid ";
				qry += "\n   and a1.epid=" + Userid + ")";
			} else if (Bj == 3) // 只查询v_salewhere_data中的商品的库存
			{

				qry += "\n   and exists (select 1 from v_salewhere_data a1 where a1.Wareid=b.Wareid ";
				if (Bj0 == 1)// bj=3时，bj0=1表示只查询有销售的商品的库存
					qry += "\n   and a1.Colorid=b.Colorid and a1.Sizeid=b.Sizeid ";
				qry += "\n   and a1.epid=" + Userid + ")";

			} else if (Bj == 4) // TotalWareandcolor 汇总指定商品，颜色的店铺尺码分布
			{
				qry += "\n     and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";

				if (Nohouseid > 0) // 不查询指定店铺数据
					qry += "\n  and a.houseid<>" + Nohouseid;
			} else if (Bj == 5) {// 库存报警
				qry += "\n     and exists (select 1 from warewarn x1 where a.Houseid=x1.Houseid and b.wareid=x1.wareid and b.colorid=x1.colorid and b.sizeid=x1.sizeid) ";
			}
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
				qry += "\n     and (c.Brandid=0 or exists (select 1 from employebrand x2 where c.Brandid=x2.Brandid and x2.epid=" + Userid + " )) ";
				if (Issxy == 1)// 上下衣要判断区位
					qry += "\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))";
			}
			if (Paid > 0) {
				qry += "\n   and exists (select 1 from vippairs1 a1 where a1.Wareid=b.Wareid and a1.Paid=" + Paid + ")";
			}
			// if (Houseid > 0)
			// qry += " and a.Houseid=" + Houseid;
			if (Wareid > 0)
				qry += "\n      and b.Wareid=" + Wareid;
			if (Colorid > 0)
				qry += "\n      and b.Colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "\n      and b.Sizeid=" + Sizeid;
			if (!Wareno.equals(""))
				qry += "\n      and c.Wareno like '%" + Wareno + "%'";
			if (Wareno1.length() > 0)
				qry += "      and c.Wareno = '" + Wareno1 + "'";
			// if (Colorname.length() > 0)
			// qry += "\n and exists (select 1 from colorcode x1 where x1.accid=" +
			// Accid + " and b.colorid=x1.colorid and x1.colorname = '" + Colorname
			// + "' and x1.statetag=1)";
			// if (Sizename.length() > 0)
			// qry += "\n and exists (select 1 from sizecode x2 where x2.accid=" +
			// Accid + " and b.sizeid=x2.sizeid and c.sizegroupno=x2.groupno and
			// x2.sizename = '" + Sizename + "' and x2.statetag=1)";
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
			if (Minretailsale >= 0)
				qry += "\n and c.retailsale>=" + Minretailsale;
			if (Maxretailsale >= 0)
				qry += "\n and c.retailsale<=" + Maxretailsale;
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

			if (!Typeidlist.equals(""))
				qry += " and " + Func.getCondition("c.typeid", Typeidlist);
			// if (Brandid >= 0)
			// qry += " and c.Brandid=" + Brandid;
			if (!Brandidlist.equals(""))
				qry += "\n and " + Func.getCondition("c.brandid", Brandidlist);
			if (!Providlist.equals(""))
				qry += "\n and " + Func.getCondition("c.provid", Providlist);
			if (!Areaidlist.equals(""))
				qry += "\n and " + Func.getCondition("c.areaid", Areaidlist);
			if (!Houseidlist.equals(""))
				qry += "\n and " + Func.getCondition("a.Houseid", Houseidlist);
			if (Retday > 0) //退货截止天数
				qry += " and f_isdate(c.retdatestr)=1 and SUBSTR(c.retdatestr,6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(c.retdatestr, 6,5) <= to_char(sysdate + " + Retday + ", 'mm-dd')";

			// if (Provid >= 0)
			// qry += " and c.Provid=" + Provid;
			// if (Areaid >= 0)
			// qry += " and c.Areaid=" + Areaid;
			qry += "\n     group by a.Houseid,b.Wareid,b.Colorid,b.Sizeid ";

			if (Bj == 2) // 减配货占用
			{
				// --配货出库
				qry += "\n     union all ";
				qry += "\n     select a.Houseid,b.Wareid,b.Colorid,b.Sizeid,-sum(b.amount) as amount ";
				qry += "\n     from warepeih a  ";
				qry += "\n     join warepeim b on a.accid=b.accid and a.noteno=b.noteno  ";
				qry += "\n     left outer join warecode c on b.Wareid=c.Wareid ";
				qry += "\n     where a.statetag<5   ";// --statetag:0=草稿,1=提交,2=审核,3=配货中，4=配货完，5=已转出库单";
				qry += "\n     and a.accid=" + Accid;
				// if (Nohouseid > 0) // 不查询指定店铺数据
				// qry += "\n and a.houseid<>" + Nohouseid;
				qry += "\n     and a.notedate>=to_date(v_begindate,'yyyy-mm-dd') ";
				qry += "\n     and a.notedate<=to_date('" + nowdate2 + "','yyyy-mm-dd hh24:mi:ss') ";
				if (Qxbj == 1) {
					qry += "\n     and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
					qry += "\n     and (c.Brandid=0 or exists (select 1 from employebrand x2 where c.Brandid=x2.Brandid and x2.epid=" + Userid + " )) ";
					if (Issxy == 1)// 上下衣要判断区位
						qry += "\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))";
				}
				if (Paid > 0) {
					qry += "\n   and exists (select 1 from vippairs1 a1 where a1.Wareid=b.Wareid and a1.Paid=" + Paid + ")";
				}
				if (Wareid > 0)
					qry += "\n      and b.Wareid=" + Wareid;
				if (Colorid > 0)
					qry += "\n      and b.Colorid=" + Colorid;
				if (Sizeid > 0)
					qry += "\n      and b.Sizeid=" + Sizeid;
				if (!Wareno.equals(""))
					qry += "\n      and c.Wareno like '%" + Wareno + "%'";
				if (Wareno1.length() > 0)
					qry += "      and c.Wareno = '" + Wareno1 + "'";
				// if (Colorname.length() > 0)
				// qry += "\n and exists (select 1 from colorcode x1 where
				// x1.accid=" + Accid + " and b.colorid=x1.colorid and x1.colorname
				// = '" + Colorname + "' and x1.statetag=1)";
				// if (Sizename.length() > 0)
				// qry += "\n and exists (select 1 from sizecode x2 where x2.accid="
				// + Accid + " and b.sizeid=x2.sizeid and c.sizegroupno=x2.groupno
				// and x2.sizename = '" + Sizename + "' and x2.statetag=1)";
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
				if (Minretailsale >= 0)
					qry += "\n and c.retailsale>=" + Minretailsale;
				if (Maxretailsale >= 0)
					qry += "\n and c.retailsale<=" + Maxretailsale;
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

				if (!Typeidlist.equals(""))
					qry += " and " + Func.getCondition("c.typeid", Typeidlist);
				// if (Brandid >= 0)
				// qry += " and c.Brandid=" + Brandid;
				if (!Brandidlist.equals(""))
					qry += "\n and " + Func.getCondition("c.brandid", Brandidlist);
				if (!Providlist.equals(""))
					qry += "\n and " + Func.getCondition("c.provid", Providlist);
				if (!Areaidlist.equals(""))
					qry += "\n and " + Func.getCondition("c.areaid", Areaidlist);
				if (!Houseidlist.equals(""))
					qry += "\n and " + Func.getCondition("a.Houseid", Houseidlist);
				if (Retday > 0) //退货截止天数
					qry += " and f_isdate(c.retdatestr)=1 and SUBSTR(c.retdatestr,6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(c.retdatestr, 6,5) <= to_char(sysdate + " + Retday + ", 'mm-dd')";

				// if (Provid >= 0)
				// qry += " and c.Provid=" + Provid;
				// if (Areaid >= 0)
				// qry += " and c.Areaid=" + Areaid;
				qry += "\n     group by a.Houseid,b.Wareid,b.Colorid,b.Sizeid ";

			}
		}
		qry += "\n  ) ";
		qry += "\n  group by Wareid,Colorid,Sizeid  ";
		if (Nohouse == 0)
			qry += ",houseid";// 1= 不分店铺汇总库存
		qry += ");";
		qry += "\n     commit;";
		qry += "\n end;";
//		System.out.println("totalcxkczy1: " + qry);
		int ret = DbHelperSQL.ExecuteSql(qry, 600);// 10分种=60秒*10
		if (ret < 0) {
			errmess = "汇总失败！";
			return 0;
		} else {
			errmess = "汇总成功！";
			return 1;
		}
	}

	public Table doListcxkczy(QueryParam qp, String nowdate, String order, int hzfs, int sortid, int xsid, int housecostbj, float minamt, float maxamt) {
		if (nowdate.length() > 10)
			nowdate = Func.subString(nowdate, 1, 10);
		String qry = "";
		String sort = "";
		if (hzfs == 0) // 按货号汇总
		{
			qry = " select a.wareid,b.wareno,b.warename,b.units,b.locale,b.retailsale,b.sale4 as wholesale,b.remark,b.imagename0,sum(a.amount) as amount,sum(a.amount*b.retailsale) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc ";
			qry += " ,round(sum(a.amount*b.sale4),2) as wholecurr ,round(sum(a.amount*c.costprice),2) as fixedcurr";
			qry += " ,case sum(a.amount) when 0 then 0 else round(sum(a.amount*c.costprice)/sum(a.amount),2) end as costprice";
			qry += " FROM v_cxkczy_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join warecost c on a.wareid=c.wareid and c.daystr='" + nowdate + "'";
			if (housecostbj == 1)
				qry += "  and a.houseid=c.houseid";
			else
				qry += "  and c.houseid=0";
			qry += " where a.epid=" + Userid;
			qry += " group by a.wareid,b.wareno,b.warename,b.units,b.locale,b.retailsale,b.sale4,b.remark,b.imagename0";
			if (xsid == 2) {
				qry += " having sum(a.amount)>=" + minamt + " and sum(a.amount)<=" + maxamt;
			} else {
				if (xsid == 0) {
					qry += " having sum(a.amount)<>0";
				} else if (xsid == 1) {
					qry += " having sum(a.amount)=0";
				}
			}
			if (sortid == 1) {
				sort = " amount " + order + ",wareid";
			} else

			if (sortid == 2) {
				sort = " curr " + order + ",wareid";
			} else {
				sort = " wareno " + order + ",wareid";
			}

		} else if (hzfs == 1) // 按货号+颜色汇总
		{
			qry = " select a.wareid,b.wareno,b.warename,b.units,b.locale,b.retailsale,b.sale4 as wholesale,a.colorid,c.colorname,b.remark,b.imagename0,sum(a.amount) as amount,sum(a.amount*b.retailsale) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc ";
			qry += "   ,round(sum(a.amount*b.sale4),2) as wholecurr,round(sum(a.amount*d.costprice),2) as fixedcurr";
			qry += " ,case sum(a.amount) when 0 then 0 else round(sum(a.amount*d.costprice)/sum(a.amount),2) end as costprice";
			qry += " FROM v_cxkczy_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join colorcode c on a.colorid=c.colorid";
			qry += " left outer join warecost d on a.wareid=d.wareid and d.daystr='" + nowdate + "'";
			if (housecostbj == 1)
				qry += "  and a.houseid=d.houseid";
			else
				qry += "  and d.houseid=0";
			qry += " where a.epid=" + Userid;
			qry += " group by a.wareid,b.wareno,b.warename,b.units,b.locale,b.retailsale,b.sale4,a.colorid,c.colorname,b.remark,b.imagename0";
			if (xsid == 2) {
				qry += " having sum(a.amount)>=" + minamt + " and sum(a.amount)<=" + maxamt;
			} else {
				if (xsid == 0) {
					qry += " having sum(a.amount)<>0";
				} else if (xsid == 1) {
					qry += " having sum(a.amount)=0";
				}
			}
			if (sortid == 1) {
				sort = " amount " + order + ",wareid,colorid";
			} else if (sortid == 2) {
				sort = " curr " + order + ",wareid,colorid";
			} else {
				sort = " wareno " + order + ",wareid,colorid";
			}
		} else if (hzfs == 2) // 按货号+颜色+尺码汇总
		{
			qry = " select a.wareid,b.wareno,b.warename,b.units,b.locale,b.retailsale,b.sale4 as wholesale,a.colorid,c.colorname,a.sizeid,d.sizename,b.remark,b.imagename0,sum(a.amount) as amount,sum(a.amount*b.retailsale) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc ";
			qry += "  ,round(sum(a.amount*b.sale4),2) as wholecurr ,round(sum(a.amount*e.costprice),2) as fixedcurr";
			qry += " ,case sum(a.amount) when 0 then 0 else round(sum(a.amount*e.costprice)/sum(a.amount),2) end as costprice";
			qry += " FROM v_cxkczy_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join colorcode c on a.colorid=c.colorid";
			qry += " left outer join sizecode d on a.sizeid=d.sizeid";
			qry += " left outer join warecost e on a.wareid=e.wareid and e.daystr='" + nowdate + "'";
			if (housecostbj == 1)
				qry += "  and a.houseid=e.houseid";
			else
				qry += "  and e.houseid=0";
			qry += " where a.epid=" + Userid;
			qry += " group by a.wareid,b.wareno,b.warename,b.units,b.locale,b.retailsale,b.sale4,a.colorid,c.colorname,a.sizeid,d.sizename,b.remark,b.imagename0";

			if (xsid == 2) {
				qry += " having sum(a.amount)>=" + minamt + " and sum(a.amount)<=" + maxamt;
			} else {
				if (xsid == 0) {
					qry += " having sum(a.amount)<>0";
				} else if (xsid == 1) {
					qry += " having sum(a.amount)=0";
				}
			}

			if (sortid == 1) {
				sort = " amount " + order + ",wareid,colorid,sizeid";
			} else if (sortid == 2) {
				sort = " curr " + order + ",wareid,colorid,sizeid";
			} else {
				sort = " wareno " + order + ",wareid,colorid,sizeid";
			}
		}

		qp.setQueryString(qry);
		qp.setSortString(sort);
		qp.setSumString("nvl(sum(amount),0) as totalamt,nvl(sum(curr),0) as totalcurr,nvl(sum(wholecurr),0) as totalwholecurr,nvl(sum(fixedcurr),0) as totalfixedcurr");
		return DbHelperSQL.GetTable(qp);

	}

	// 获取分页数据,尺码横向展开
	public String GetTable2Json(QueryParam qp, int housecostbj, int sizenum) {
		Grouplist += ",b.sizegroupno";
		Table tb = GetTable(qp, housecostbj);

		String sqlsize = "";
		String sqlsum = "";
		String qry1;

		long wareid = 0;
		long wareid0 = 0;
		long houseid = 0;
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
				// System.out.println("wareid=" + wareid + " sqlsize=" + sqlsize
				// + " sqlsum=" + sqlsum);
			}
			// System.out.println("wareid=" + sqlsize);
			retcs += fh + "{";
			for (int j = 0; j < cols; j++) {

				String strKey = tb.getRow(i).getColumn(j + 1).getName().toUpperCase();

				String strValue = tb.getRow(i).get(j).toString();
				// System.out.println(strKey + "=" + strValue);

				if (strKey.equals("HOUSEID"))
					houseid = Long.parseLong(strValue);
				else if (strKey.equals("COLORID"))
					colorid = Long.parseLong(strValue);

				retcs += "\"" + strKey + "\":\"" + strValue + "\",";
				fh = ",";
			}
			qry1 = "select " + sqlsum + "," + sqlsize;
			qry1 += " from v_cxkczy_data a where a.epid=" + Userid;
			qry1 += " and a.wareid=" + wareid;
			if (houseid > 0)
				qry1 += " and a.houseid=" + houseid;
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

		String qry = "select a.houseid,b.housename,sum(a.amount) as amount0";
		if (sqlsum.length() > 0)
			qry += "," + sqlsum;
		qry += " from v_cxkczy_data a";
		qry += " left outer join warehouse b on a.houseid=b.houseid";
		qry += " where a.epid=" + Userid;
		qry += " group by a.houseid,b.housename";
		// System.out.println(qry);
		qp.setQueryString(qry);
		qp.setSumString("nvl(sum(amount0),0) as totalamt");
		// Table tb =DbHelperSQL.GetTable(qp);
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, int housecostbj) {
		if (Cxdatetime.equals(""))
			Cxdatetime = Func.strLeft(Serverdatetime, 10); // yyyy-mm-dd   Func.DateToStr(new Date());

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
		strSql.append("\n FROM v_cxkczy_data a");
		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
		// if (sizenum == 0)
		strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append("\n left outer join brand f on b.brandid=f.brandid");
		strSql.append("\n left outer join waretype g on b.typeid=g.typeid");
		strSql.append("\n left outer join warehouse h on a.houseid=h.houseid");
		strSql.append("\n left outer join provide i on b.provid=i.provid");
		strSql.append("\n left outer join area r on b.areaid=r.areaid");
		strSql.append("\n left outer join warecost e on a.wareid=e.wareid and e.daystr='" + Cxdatetime + "'");
		if (housecostbj == 1)
			strSql.append("\n  and a.houseid=e.houseid");
		else
			strSql.append("\n  and e.houseid=0");
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
		strSql.append("\n FROM v_cxkczy_data a");
		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
		// if (sizenum == 0)
		strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append("\n left outer join brand f on b.brandid=f.brandid");
		strSql.append("\n left outer join waretype g on b.typeid=g.typeid");
		strSql.append("\n left outer join warehouse h on a.houseid=h.houseid");
		strSql.append("\n left outer join provide i on b.provid=i.provid");
		strSql.append("\n left outer join area r on b.areaid=r.areaid");

		strSql.append("\n left outer join warecost e on a.wareid=e.wareid and e.daystr='" + Cxdatetime + "'");
		if (housecostbj == 1)
			strSql.append("\n  and a.houseid=e.houseid");
		else
			strSql.append("\n  and e.houseid=0");
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
	public String doListWarn2Excel(QueryParam qp, int cxfs, JSONObject jsonObject) {

		StringBuilder strSql = new StringBuilder();
		strSql.append("select a.id,a.wareid,a.colorid,a.sizeid,a.houseid,a.amount,e.minamt,e.maxamt");
		strSql.append(",b.wareno,b.warename,b.units,c.colorname,d.sizename,f.housename,b.imagename0");
		strSql.append("\n FROM v_cxkczy_data a");
		strSql.append("\n join warewarn e on a.houseid=e.houseid and a.wareid=e.wareid and a.colorid=e.colorid and a.sizeid=e.sizeid");
		strSql.append("\n left outer join warehouse f on a.houseid=f.houseid");
		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
		strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append("\n where a.epid=" + Userid);
		// cxfs:0=下限，1=上限，2=上下限，3=所有
		if (cxfs == 0)
			strSql.append("\n and not e.minamt is null and a.amount<e.minamt");
		else if (cxfs == 1)
			strSql.append("\n and not e.maxamt is null and a.amount>e.maxamt");
		else if (cxfs == 2)
			strSql.append("\n and (not e.maxamt is null and a.amount>e.maxamt or not e.minamt is null and a.amount<e.minamt)");
		qp.setQueryString(strSql.toString());

		qp.setPageIndex(1);
		qp.setPageSize(100);
		return DbHelperSQL.Table2Excel(qp, jsonObject);
		// return DbHelperSQL.GetTable(qp);

	}

	// 报警
	public Table doListWarn(QueryParam qp, int cxfs) {

		StringBuilder strSql = new StringBuilder();
		strSql.append("select a.id,a.wareid,a.colorid,a.sizeid,a.houseid,a.amount,e.minamt,e.maxamt");
		strSql.append(",b.wareno,b.warename,b.units,c.colorname,d.sizename,f.housename,b.imagename0");
		strSql.append("\n FROM v_cxkczy_data a");
		strSql.append("\n join warewarn e on a.houseid=e.houseid and a.wareid=e.wareid and a.colorid=e.colorid and a.sizeid=e.sizeid");
		strSql.append("\n left outer join warehouse f on a.houseid=f.houseid");
		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
		strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append("\n where a.epid=" + Userid);
		// cxfs:0=下限，1=上限，2=上下限，3=所有
		if (cxfs == 0)
			strSql.append("\n and not e.minamt is null and a.amount<e.minamt");
		else if (cxfs == 1)
			strSql.append("\n and not e.maxamt is null and a.amount>e.maxamt");
		else if (cxfs == 2)
			strSql.append("\n and (not e.maxamt is null and a.amount>e.maxamt or not e.minamt is null and a.amount<e.minamt)");
		qp.setQueryString(strSql.toString());

		return DbHelperSQL.GetTable(qp);

	}

	// 获取分页数据
	public Table GetTableSxy(QueryParam qp, int housecostbj) {
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
		strSql.append("\n FROM v_cxkczy_data a");
		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
		// if (sizenum == 0)
		strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append("\n left outer join brand f on b.brandid=f.brandid");
		strSql.append("\n left outer join waretype g on b.typeid=g.typeid");
		strSql.append("\n left outer join warehouse h on a.houseid=h.houseid");
		strSql.append("\n left outer join provide i on b.provid=i.provid");

		strSql.append("\n left outer join warecost e on a.wareid=e.wareid and e.daystr='" + Cxdatetime + "'");
		if (housecostbj == 1)
			strSql.append("\n  and a.houseid=e.houseid");
		else
			strSql.append("\n  and e.houseid=0");
		strSql.append("\n left outer join area r on b.areaid=r.areaid");
		strSql.append("\n left outer join housesaleprice j on a.wareid=j.wareid and a.houseid=j.houseid");

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
		qp.setCalcfield(Calcfield);

		return DbHelperSQL.GetTable(qp);
	}

	public int doGetWareandcolor() {

		String qry = " select wareno,warename from warecode where accid=" + Accid + " and wareid=" + Wareid;
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		int rs = tb.getRowCount();
		String retcs = "\"msg\":\"操作成功！\",\"WARENO\":\"" + tb.getRow(0).get("WARENO").toString() + "\""//
				+ ",\"WARENAME\":\"" + tb.getRow(0).get("WARENAME").toString() + "\"";

		// for (int j = 0; j < tb.getColumnCount(); j++) {
		// if (j > 0)
		// retcs += ",";
		// String strKey = tb.getRow(0).getColumn(j +
		// 1).getName().toUpperCase();
		// String strValue = tb.getRow(0).get(j).toString();
		// retcs += "\"" + strKey + "\":\"" + strValue + "\"";
		// }
		qry = " select colorname from colorcode where accid=" + Accid + " and colorid=" + Colorid;
		tb = DbHelperSQL.Query(qry).getTable(1);
		// for (int j = 0; j < tb.getColumnCount(); j++) {
		// if (j > 0)
		// retcs += ",";
		// String strKey = tb.getRow(0).getColumn(j +
		// 1).getName().toUpperCase();
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
		// qry = " select a.houseid,a.sizeid,a.amount,b.housename";
		// qry += "\n from v_cxkczy_data a ";
		// qry += "\n left outer join warehouse b on a.houseid=b.houseid ";
		// qry += "\n where a.epid=" + Userid + " and a.wareid=" + Wareid+" and
		// a.colorid=" + Colorid;
		// qry += "\n order by a.houseid,a.sizeid";
		// System.out.println(qry);
		// tb = DbHelperSQL.Query(qry).getTable(1);
		// rs = tb.getRowCount();
		// retcs += ",\"amountlist\":[";
		// for (int i = 0; i < rs; i++) {
		// if (i > 0)
		// retcs += ",";
		// retcs += "{\"HOUSEID\":\"" + tb.getRow(i).get("HOUSEID").toString() +
		// "\"" //
		// + ",\"HOUSENAME\":\"" + tb.getRow(i).get("HOUSENAME").toString() +
		// "\"" //
		// + ",\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"" //
		// + ",\"AMOUNT\":\"" + tb.getRow(i).get("AMOUNT").toString() + "\"}"
		// ;//
		// }
		// retcs += "]";
		errmess = retcs;
		return 1;
	}

	public int doGetwarehousesum() {
		if (Wareno1.length() <= 0) {
			errmess = "货号不是一个有效值！";
			return 0;
		}
		Table tb = new Table();
		String qry = "select wareid,wareno,warename,units,retailsale,sizegroupno from warecode where accid=" + Accid + " and wareno='" + Wareno1 + "' and statetag=1 and rownum=1";
		tb = DbHelperSQL.Query(qry).getTable(1);
		int count = tb.getRowCount();
		if (count == 0) {
			errmess = "商品:" + Wareno1 + "未找到！";
			return 0;
		}
		Wareid = Long.parseLong(tb.getRow(0).get("WAREID").toString());
		String sizegroupno = tb.getRow(0).get("SIZEGROUPNO").toString();
		String warename = tb.getRow(0).get("WARENAME").toString();
		String units = tb.getRow(0).get("UNITS").toString();

		if (Colorname.length() > 0) {
			qry = "select colorid from colorcode where accid=" + Accid + " and colorname='" + Colorname + "' and statetag=1 and rownum=1";
			tb = DbHelperSQL.Query(qry).getTable(1);
			count = tb.getRowCount();
			if (tb.getRowCount() == 0) {
				errmess = "颜色:" + Colorname + "未找到！";
				return 0;
			}
			Colorid = Long.parseLong(tb.getRow(0).get("COLORID").toString());
		}
		if (Sizename.length() > 0) {
			qry = "select sizeid from sizecode where accid=" + Accid + " and Sizename='" + Sizename + "' and groupno='" + sizegroupno + "' and statetag=1 and rownum=1";
			tb = DbHelperSQL.Query(qry).getTable(1);
			count = tb.getRowCount();
			if (tb.getRowCount() == 0) {
				errmess = "尺码:" + Sizename + "未找到！";
				return 0;
			}
			Sizeid = Long.parseLong(tb.getRow(0).get("SIZEID").toString());
		}

		doCxkczy();
		qry = "select b.colorname,c.sizename,a.amount from v_cxkczy_data a ";
		qry += " left outer join colorcode b on a.colorid=b.colorid";
		qry += " left outer join sizecode c on a.sizeid=c.sizeid";
		qry += " where a.epid=" + Userid;

		tb = DbHelperSQL.Query(qry).getTable(1);
		count = tb.getRowCount();
		float totalamt = 0;
		String retcs = "\"msg\":\"操作成功！\",\"rows\":[";
		for (int i = 0; i < count; i++) {
			if (i > 0)
				retcs += ",";
			retcs += "{";
			for (int j = 0; j < tb.getColumnCount(); j++) {//
				if (j > 0)
					retcs += ",";
				String strKey = tb.getRow(i).getColumn(j + 1).getName().toUpperCase();//
				String strValue = tb.getRow(i).get(j).toString();//
				retcs += "\"" + strKey + "\":\"" + strValue + "\"";
			}
			retcs += "}";
			totalamt += Float.parseFloat(tb.getRow(i).get("AMOUNT").toString());
		}
		retcs += "]";
		errmess = "\"WARENO\":\"" + Wareno1 + "\",\"WARENAME\":\"" + warename + "\",\"UNITS\":\"" + units + "\",\"TOTALAMT\":\"" + totalamt + "\"," + retcs;
		return 1;
	}
}
