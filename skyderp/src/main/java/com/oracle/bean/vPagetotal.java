/**
 * 
 */
package com.oracle.bean;

import java.util.Date;

import com.comdot.data.Table;
import com.common.tool.Func;
import com.netdata.db.DbHelperSQL;

/**
 * @author Administrator
 *
 */
public class vPagetotal {
	private Long Accid;
	private Long Userid;
	private Long Levelid;
	private Long Houseid;
	private Integer Priceprec;
	private Integer Housecostbj;
	private Date Nowday;
	private String Currdatetimestr;
	private Integer Qxbj;
	private Integer Dateid;
	private String errmess;

	private String Calcdate = ""; // 账套库存登账日期

	public void setCalcdate(String calcdate) {
		Calcdate = calcdate;
	}

	public String getErrmess() {
		return errmess;
	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	public void setUserid(Long userid) {
		Userid = userid;
	}

	public void setHouseid(Long houseid) {
		Houseid = houseid;
	}

	public void setLevelid(Long levelid) {
		Levelid = levelid;
	}

	public void setPriceprec(Integer priceprec) {
		Priceprec = priceprec;
	}

	public void setDateid(Integer dateid) {
		Dateid = dateid;
	}

	public void setQxbj(Integer qxbj) {
		Qxbj = qxbj;
	}

	public void setHousecostbj(Integer housecostbj) {
		Housecostbj = housecostbj;
	}

	public void setNowday(Date nowday) {
		Nowday = nowday;
	}

	public void setCurrdatetimestr(String currdatetimestr) {
		Currdatetimestr = currdatetimestr;
	}

	// 侧边菜单员工管理功能统计
	public void doTotalEmplpage() {

		// dateid:0=本日，1=昨日，2=本周，3=本月

		String maxdate = "";
		String mindate = "";// nowday.AddDays(-(nowday.Day) + 1).ToString("yyyy-MM-dd");

		if (Dateid == 0) // 本日
		{
			maxdate = Func.DateToStr(Nowday);// nowday.toString("yyyy-MM-dd");
			mindate = Func.DateToStr(Nowday);
		} else if (Dateid == 1) // 昨日
		{
			maxdate = Func.DateToStr(Func.getDataAdd(Nowday, -1));// nowday.AddDays(-1).toString("yyyy-MM-dd");
			mindate = Func.DateToStr(Func.getDataAdd(Nowday, -1));// nowday.AddDays(-1).toString("yyyy-MM-dd");
		} else if (Dateid == 2) // 本周
		{
			maxdate = Func.DateToStr(Nowday);// nowday.toString("yyyy-MM-dd");
			mindate = Func.DateToStr(Func.getFirstDayOfWeek(Nowday));
		} else if (Dateid == 3) // 本月
		{
			maxdate = Func.DateToStr(Nowday);// nowday.toString("yyyy-MM-dd");
			mindate = Func.DateToStr(Func.getFirstDayOfMonth(Nowday));
		}
		String responsestr = "\"msg\":\"操作成功！\",\"daterange\":\"" + mindate + "至" + maxdate + "\"";

		maxdate = Func.DateToStr(Func.getNextDay(maxdate));

		Integer kqzsnum = 0; // 考勤总人次
		Integer kqcdnum = 0; // 迟到人次
		Integer kqkgnum = 0;// 旷工人次
		Integer kqsjnum = 0;// 事假人次
		Integer kqbjnum = 0;// 病假人次
		Integer kqgxnum = 0;// 公休人次
		// 总考勤工人数
		String qry = "select sum(num) as num from (";
		// --上班
		qry += " select count(*) as num ";
		qry += " from checkwork a join employe b on a.epid=b.epid";
		qry += " where a.uptag0=1 and b.accid=" + Accid;
		qry += " and a.classday>='" + mindate + "' and a.classday<'" + maxdate + "'";
		qry += " union all ";
		// --下班
		qry += " select count(*) as num ";
		qry += " from checkwork a join employe b on a.epid=b.epid";
		qry += " where a.uptag1=1 and b.accid=" + Accid;
		qry += " and a.classday>='" + mindate + "' and a.classday<'" + maxdate + "'";
		qry += " ) ";
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			kqzsnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
		}
		// 迟到人次
		qry = "select sum(num) as num from (";
		// --上班迟到人数
		qry += " select count(*) as num ";
		qry += " from checkwork a join employe b on a.epid=b.epid ";
		qry += " where a.checkid=0 and  a.uptag0=1 and a.cdztbj0=1 and b.accid=" + Accid;
		qry += " and a.classday>='" + mindate + "' and a.classday<'" + maxdate + "'";
		qry += " union all  ";
		// --下班早退人数
		qry += " select count(*) as num  ";
		qry += " from checkwork a join employe b on a.epid=b.epid ";
		qry += " where a.checkid=0 and a.uptag1=1 and a.cdztbj1=1 and b.accid=" + Accid;
		qry += " and a.classday>='" + mindate + "' and a.classday<'" + maxdate + "'";
		qry += " ) ";
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			kqcdnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
		}
		// checkid:0=正常考勤 1=旷工2=病假 3=事假 4=公休
		// 旷工人数
		qry = "select count(*) as num ";
		qry += " from checkwork a join employe b on a.epid=b.epid";
		qry += " where a.checkid=1 and b.accid=" + Accid;
		qry += " and a.classday>='" + mindate + "' and a.classday<'" + maxdate + "'";
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			kqkgnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
		}
		// 病假人数
		qry = "select count(*) as num ";
		qry += " from checkwork a join employe b on a.epid=b.epid";
		qry += " where a.checkid=2 and b.accid=" + Accid;
		qry += " and a.classday>='" + mindate + "' and a.classday<'" + maxdate + "'";
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			kqbjnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
		}
		// 事假人数
		qry = "select count(*) as num ";
		qry += " from checkwork a join employe b on a.epid=b.epid";
		qry += " where a.checkid=3 and b.accid=" + Accid;
		qry += " and a.classday>='" + mindate + "' and a.classday<'" + maxdate + "'";
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			kqsjnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
		}
		// 公休人数
		qry = "select count(*) as num ";
		qry += " from checkwork a join employe b on a.epid=b.epid";
		qry += " where a.checkid=4 and b.accid=" + Accid;
		qry += " and a.classday>='" + mindate + "' and a.classday<'" + maxdate + "'";
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			kqgxnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
		}

		// 考勤数据
		responsestr += ",\"KQZSNUM\":\"" + kqzsnum + "\"" // 总人次
				+ ",\"KQCDNUM\":\"" + kqcdnum + "\"" // 迟到人次
				+ ",\"KQKGNUM\":\"" + kqkgnum + "\"" // 旷工人次
				+ ",\"KQSJNUM\":\"" + kqsjnum + "\"" // 事假人次
				+ ",\"KQBJNUM\":\"" + kqbjnum + "\"" // 病假人次
				+ ",\"KQGXNUM\":\"" + kqgxnum + "\"" // 公休人次
		;
		errmess = responsestr;
	}

	// 侧边菜单订货会功能统计
	public void doTotalOMorderpage() {
		String responsestr = "\"msg\":\"操作成功！\",";
		Integer omnum = 0;
		Integer omwarenum = 0;
		Integer ommemnum = 0;
		Integer omwareordnum = 0;
		Float omwareordamt = (float) 0;
		Table tb = new Table();
		// 当前发布的在开的订会货数
		String qry = "select count(*) as num from omsubject where statetag=1 and accid= " + Accid;
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			omnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
		}
		responsestr += "\"sellerlist\":{\"OMNUM\":\"" + omnum.toString() + "\"";
		// 订货会商品款数
		qry = "select count(*) num from omwarecode a";
		qry += " where exists (select 1 from OMSUBJECT b where b.accid=" + Accid + " and b.statetag=1 and a.omid=b.omid)";
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			omwarenum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
		}
		responsestr += ",\"OMWARENUM\":\"" + omwarenum.toString() + "\""; // 订货会商品款数
		// 订货会商家数
		qry = "select count(*) num from ommember a";
		qry += " where exists (select 1 from OMSUBJECT b where b.accid=" + Accid + " and b.statetag=1 and a.omid=b.omid)";
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			ommemnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
		}
		responsestr += ",\"OMMEMNUM\":\"" + ommemnum.toString() + "\""; // 订货会商家数
		// 已订货款数
		qry = "select count(distinct a.wareid ) as num,nvl(sum(a.amount),0) as amount from omdetail a";
		qry += " where exists (select 1 from OMSUBJECT b where b.accid=" + Accid + " and b.statetag=1 and a.omid=b.omid)";
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			omwareordnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			omwareordamt = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
		}
		responsestr += ",\"OMWAREORDNUM\":\"" + omwareordnum.toString() + "\"" // 已订货款数
				+ ",\"OMWAREORDAMT\":\"" + omwareordamt.toString() + "\""; // 已订货数量
		responsestr += "}";
		// ============================================
		// 可以参加的订货会
		qry = "select count(*) as num from omsubject a where a.statetag=1 ";
		qry += " and exists (select 1  from  ommember b where a.omid=b.omid and b.o_accid=" + Accid + ")";
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			omnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
		}
		responsestr += ",\"buyerlist\":{\"OMNUM\":\"" + omnum + "\"";
		// 已订货的款数，订货件数
		qry = "select count(distinct a.wareid) as num,nvl(sum(a.amount),0) as amount from omdetail a where a.o_accid=" + Accid;
		qry += " and exists (select 1 from OMSUBJECT b where b.statetag=1 and a.omid=b.omid)";
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			omwareordnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			omwareordamt = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
		}
		responsestr += ",\"OMWAREORDNUM\":\"" + omwareordnum.toString() + "\"" // 已订货款数
				+ ",\"OMWAREORDAMT\":\"" + omwareordamt.toString() + "\""; // 已订货数量

		responsestr += "}";
		errmess = responsestr;
	}

	// 侧边菜单侧边菜单会员页功能统计
	public void doTotalVippage() {
		String responsestr = "\"msg\":\"操作成功！\",";
		String maxdate = Func.DateToStr(Func.getNextDay(Nowday));// nowday.AddDays(1).toString("yyyy-MM-dd");
		String mindate = Func.DateToStr(Func.getPrevDay(Nowday));// nowday.AddDays(-(nowday.Day) + 1).toString("yyyy-MM-dd");
		//System.out.println("mindate=" + mindate + " maxdate=" + maxdate);
		// maxdate = DateTime.Parse(maxdate).AddDays(1).toString("yyyy-MM-dd");

		Integer totalvipnum = 0;
		Integer newvipnum = 0; // 本月新增会员数
		Integer newsqvipnum = 0; // 新申请会员数
		Float vipxscurr = (float) 0; // 会员消费金额
		Float vipretailcurr = (float) 0;// 会员消费原价金额
		// Float vipdiscount = 0;//会员消费平均折扣
		Float vipxsamt = (float) 0; // 会员消费数量
		Integer vipxsnum = 0; // 会员消费单数
		Integer vipnum = 0; // 会员消费人次
		Integer birthdayvipnum = 0; // 近3天过生会员数
		// Float vipxsrate = 0; //会员消费占比
		Float vipactive = (float) 0; // 会员活跃度
		String fh = "";

		String qry = "  select a.vtid,b.vtname,count(*) as num";
		qry += "  from guestvip a";
		qry += "  left outer join guesttype b on a.vtid=b.vtid";
		qry += "  where a.accid=" + Accid + " and a.vtid>0 and a.statetag=1";
		qry += "  group by a.vtid,b.vtname ";
		qry += "  order by a.vtid";
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		int rs = tb.getRowCount();
		// 会员类型占比
		responsestr += "\"viptypelist\":[";

		for (int i = 0; i < rs; i++) {
			responsestr += fh + "{\"VTID\":\"" + tb.getRow(i).get("VTID").toString() + "\"" //
					+ ",\"VTNAME\":\"" + tb.getRow(i).get("VTNAME").toString() + "\""//
					+ ",\"NUM\":\"" + tb.getRow(i).get("NUM").toString() + "\"}";
			fh = ",";

		}
		responsestr += "]";
		// 总会员数
		qry = "select count(*) as num from guestvip where statetag=1 and accid=" + Accid;
		// WriteLogTXT("debug", "", "2.1");
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			totalvipnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
		}
		responsestr += ",\"TOTALVIPNUM\":\"" + totalvipnum + "\"";

		// 本月新增会员数
		qry = "select count(*) as num from guestvip where statetag=1 and vtid>0 and accid=" + Accid;
		qry += "  and createdate>=to_date('" + mindate + "','yyyy-mm-dd') and createdate<to_date('" + maxdate + "','yyyy-mm-dd')";
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			newvipnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
		}
		responsestr += ",\"NEWVIPNUM\":\"" + newvipnum + "\"";
		// 申请会员数
		qry = "select count(*) as num from guestvip where statetag=1 and accid=" + Accid;
		qry += "  and vtid=0 and lytag=1";
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			newsqvipnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
		}
		responsestr += ",\"NEWSQVIPNUM\":\"" + newsqvipnum + "\"";

		// 本月会员消费金额
		qry = " select nvl(sum(amount),0) as amount,nvl(sum(curr),0) as curr,count(distinct guestid) as vipnum,count(distinct noteno) as xsnum from (";
		// 店铺零售
		qry += "\n     select a.noteno,a.guestid,sum(b.amount) as amount,sum(b.curr) as curr ";
		qry += "\n     from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n     where a.accid=" + Accid + " and a.statetag=1 and a.ntid=0 and a.guestid>0";
		qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
		qry += "\n     group by a.noteno,a.guestid ";
		// 商场零售
		qry += "\n     union all";
		qry += "\n     select a.noteno,a.guestid,sum(b.amount) as amount,sum(b.curr) as curr ";
		qry += "\n     from shopsaleh a join shopsalem b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n     where a.accid=" + Accid + " and a.statetag=1 and a.guestid>0";
		qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
		qry += "\n     group by a.noteno,a.guestid ";
		qry += "\n   )";
		// WriteLogTXT("debug", "", "3.2");
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			vipxscurr = Float.parseFloat(tb.getRow(0).get("CURR").toString());
			vipxsamt = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
			vipxsnum = Integer.parseInt(tb.getRow(0).get("XSNUM").toString()); // 会员消费单数
			vipnum = Integer.parseInt(tb.getRow(0).get("VIPNUM").toString()); // 会员消费人次
			// if (xscurr != 0) vipxsrate = Math.Round(vipxscurr / xscurr, 2, MidpointRounding.AwayFromZero);

		}
		// WriteLogTXT("debug", "", "3.3");
		responsestr += ",\"VIPXSCURR\":\"" + vipxscurr + "\"" // 会员消费金额
				+ ",\"VIPXSAMT\":\"" + vipxsamt + "\"" // 会员消费数量
				+ ",\"VIPXSNUM\":\"" + vipxsnum + "\"" // 会员消费单数
				+ ",\"VIPNUM\":\"" + vipnum + "\"" // 会员消费人次
		;
		if (totalvipnum != 0)
			vipactive = Func.getRound(vipnum / totalvipnum, 4);// Math.Round((Float)vipnum / (Float)totalvipnum, 4, MidpointRounding.AwayFromZero);
		responsestr += ",\"VIPACTIVE\":\"" + vipactive + "\""; // 会员活跃度toString("p")
		// 近3天过生日会员数
		qry = "select count(*) as num from guestvip where statetag=1 and accid=" + Accid;
		qry += " and f_isdate(birthday)=1 and SUBSTR(birthday, 6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(birthday, 6,5) <= to_char(sysdate + 3, 'mm-dd')";

		// qry = "select count(*) as num from guestvip";
		// qry += " where statetag=1 and accid=" + Accid + " and f_isdate(birthday)=1";
		// qry += " and trunc((to_number(to_char(sysdate ,'yyyymmdd') ) - to_number(to_char(to_date(birthday,'yyyy-mm-dd'), 'yyyymmdd') ) ) / 10000 )";
		// qry += " < trunc((to_number(to_char(sysdate ,'yyyymmdd')+3 ) - to_number(to_char(to_date(birthday,'yyyy-mm-dd'), 'yyyymmdd') ) ) / 10000 )";

		// WriteLogTXT("debug", "", "2.1");
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			birthdayvipnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
		}
		responsestr += ",\"BIRTHDAYVIPNUM\":\"" + birthdayvipnum + "\""//
				+ ",\"SRDAYS\":\"3\"";
		errmess = responsestr;
	}

	// 我的首页角色功能统计
	public void doTotalFirstpage() {
		// Levelid: 0=系统管理员,1=店员,2=店长,3=财务,4=经理,5=老板,6=督导,7=收银员,8=AD客服,9=库管
		// dateid:0=本日，1=昨日，2=本周，3=本月
		//System.out.println("doTotalFirstpage 0:" + Currdatetimestr);
		String maxdate = "";
		String mindate = "";
		String mindate0 = Func.DateToStr(Func.getDataAdd(Nowday, -7));// nowday.AddDays(-7).toString("yyyy-MM-dd"); // 近8天

		String maxdate1 = Func.DateToStr(Func.getDataAdd(Nowday, 1));// nowday.AddDays(1).toString("yyyy-MM-dd");
		if (Dateid == 0) // 本日
		{
			maxdate = Func.DateToStr(Nowday);// nowday.toString("yyyy-MM-dd");
			mindate = Func.DateToStr(Nowday);
		} else if (Dateid == 1) // 昨日
		{
			maxdate = Func.DateToStr(Func.getDataAdd(Nowday, -1));// nowday.AddDays(-1).toString("yyyy-MM-dd");
			mindate = Func.DateToStr(Func.getDataAdd(Nowday, -1));// nowday.AddDays(-1).toString("yyyy-MM-dd");
		} else if (Dateid == 2) // 本周
		{
			maxdate = Func.DateToStr(Nowday);// nowday.toString("yyyy-MM-dd");
			mindate = Func.DateToStr(Func.getFirstDayOfWeek(Nowday));
			// maxdate = nowday.toString("yyyy-MM-dd");
			// int weeknow = Convert.ToInt32(nowday.DayOfWeek);
			// //星期日 获取weeknow为0
			// weeknow = weeknow == 0 ? 7 : weeknow;
			// int daydiff = (-1) * weeknow + 1;
			// mindate = nowday.AddDays(daydiff).toString("yyyy-MM-dd"); //本周第一天
		} else if (Dateid == 3) // 本月
		{
			maxdate = Func.DateToStr(Nowday);// nowday.toString("yyyy-MM-dd");
			mindate = Func.DateToStr(Func.getFirstDayOfMonth(Nowday));
			// maxdate = nowday.toString("yyyy-MM-dd");
			// mindate = nowday.AddDays(-(nowday.Day) + 1).toString("yyyy-MM-dd");//本月第一天
		}

		String responsestr = "\"msg\":\"操作成功！\",\"DATERANGE\":\"" + mindate + " 至 " + maxdate + "\"";

		//System.out.println("doTotalFirstpage 1: " + responsestr);
		String maxdate0 = maxdate;

		vTotalCxkczy dal = new vTotalCxkczy();
		dal.setUserid(Userid);
		dal.setAccid(Accid);
		dal.setServerdatetime(Currdatetimestr);

		// maxdate = DateTime.Parse(maxdate).AddDays(1).toString("yyyy-MM-dd");
		maxdate = Func.DateToStr(Func.getNextDay(maxdate));
		Integer rs = 0;
		String fh = "";
		String qry = "";
		// 个人销售计划
		Float rkamt = (float) 0; // 入库数量
		Float rkcurr = (float) 0; // 入库金额
		Integer rknum = 0; // 入库单数
		Float xsamt = (float) 0; // 销售数量
		Float xscurr = (float) 0; // 销售金额
		Float xsprice = (float) 0; // 销售平均价
		Float retailcurr = (float) 0; // 原价金额
		Integer xsnum = 0; // 销售单数
		Integer xsskc = 0; // 销售skc
		Float discount = (float) 0; // 平均销售折扣
		Float xsjhcurr = (float) 0; // 销售计划金额

		Integer totalvipnum = 0; // 我的会员数
		Integer myvipnum = 0; // 我的会员数
		Integer newvipnum = 0; // 新增会员数
		Float vipxscurr = (float) 0; // 会员消费金额
		Float vipretailcurr = (float) 0;// 会员消费原价金额
		Float vipdiscount = (float) 0;// 会员消费平均折扣
		Float vipxsamt = (float) 0; // 会员消费数量
		Integer vipxsnum = 0; // 会员消费单数
		Integer vipnum = 0; // 会员消费人次
		Float vipxsrate = (float) 0; // 会员消费占比
		Float vipactive = (float) 0; // 会员活跃度

		Float jhwcrate = (float) 0; // 计划完成率
		Float drztamt = (float) 0; // 调入未收数
		Integer drztnum = 0; // 调入未收单数
		Float dcztamt = (float) 0; // 调出未收数
		Integer dcztnum = 0; // 调出未收单数
		Integer salenum1 = 0; // 零售单中一张单据一个货号的单据数量
		Integer salenumn = 0; // 零售单中一张单据多个货号的单据数量
		Float linkcurr = (float) 0; // 连带金额
		Float linkrate = (float) 0; // 连带率=salenumn/salenum1
		Integer salemanord = 0; // 店员个人排名
		Integer houseord = 0; // 店铺排名
		// Float totalkcamt = 0; //总库存
		Float kcamt = (float) 0; // 总库存
		Float kccurr = (float) 0; // 库存金额
		Float kcskc = (float) 0; // 库存skc
		Float kcprice = (float) 0; // 库存均价
		Integer kqzsnum = 0; // 考勤总人次
		Integer kqcdnum = 0; // 迟到人次
		Integer kqkgnum = 0;// 旷工人次
		Integer kqsjnum = 0;// 事假人次
		Integer kqbjnum = 0;// 病假人次
		Integer kqgxnum = 0;// 公休人次
		Float wfphamt = (float) 0; // 未发配货数量
		Integer wfphnum = 0; // 未发配货单数
		Integer errskc = 0; // 导常库存款数(负库存)
		if (Houseid == null)
			Houseid = (long) 0;
		Table tb = new Table();
		if (Levelid == 0 || Levelid == 3 || Levelid == 4 || Levelid == 5 || Levelid == 6)
		// Levelid: 0=系统管理员,1=店员,2=店长,3=财务,4=经理,5=老板,6=督导,7=收银员,8=AD客服,9=库管
		{

			// 销售数量，金额，单数，折扣
			qry = " select nvl(sum(amount),0) as amount,nvl(sum(curr),0) as curr,nvl(sum(retailcurr),0) as retailcurr, nvl(sum(cbcurr),0) as cbcurr";
			qry += "\n  ,count(distinct noteno) as num,count(distinct '^'||wareid||'^'||colorid||'^' ) as skc from (";

			qry += "\n     select a.noteno,b.wareid,b.colorid,sum(case a.ntid when 2 then -b.amount else b.amount end) as amount";
			qry += "\n     ,sum(case a.ntid when 2 then -b.curr else b.curr end) as curr";
			qry += "\n     ,sum((case a.ntid when 2 then -b.amount else b.amount end) *d.retailsale) as retailcurr";
			qry += "\n     ,sum((case a.ntid when 2 then -b.amount else b.amount end) *c.costprice) as cbcurr";
			qry += "\n     from wareouth a join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += "\n     left outer join warecost c on b.wareid=c.wareid and c.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
			if (Housecostbj == 1)
				qry += "  and a.Houseid=c.Houseid";
			else
				qry += "  and c.Houseid=0";
			qry += "\n     left outer join warecode d on b.wareid=d.wareid ";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 ";
			qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			// if (qxbj == 1) //1 启用权限控制
			// {
			// qry += "\n and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
			// qry += "\n and (d.brandid=0 or exists (select 1 from employebrand x2 where d.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			// }
			qry += "\n     group by a.noteno,b.wareid,b.colorid";

			// 商场零售
			qry += "\n     union all";
			qry += "\n     select a.noteno,b.wareid,b.colorid,sum(b.amount) as amount,sum(b.curr) as curr";
			qry += "\n     ,sum(b.amount*d.retailsale) as retailcurr,sum(b.amount*c.costprice) as cbcurr";
			qry += "\n     from shopsaleh a join shopsalem b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += "\n     left outer join warecode d on b.wareid=d.wareid ";
			qry += "\n     left outer join warecost c on b.wareid=c.wareid and c.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
			if (Housecostbj == 1)
				qry += "  and a.Houseid=c.Houseid";
			else
				qry += "  and c.Houseid=0";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1";
			qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			// if (qxbj == 1) //1 启用权限控制
			// {
			// qry += "\n and exists (select 1 from employehouse x1 where b.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
			// qry += "\n and (d.brandid=0 or exists (select 1 from employebrand x2 where d.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			// }
			qry += "\n     group by a.noteno,b.wareid,b.colorid ";
			qry += "\n      )";
			// WriteLogTXT("debug", "totalfirstpage1", qry);
			tb = DbHelperSQL.Query(qry).getTable(1);

			if (tb.getRowCount() > 0) {
				xsamt = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
				xscurr = Float.parseFloat(tb.getRow(0).get("CURR").toString());
				retailcurr = Float.parseFloat(tb.getRow(0).get("RETAILCURR").toString());
				xsnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
				xsskc = Integer.parseInt(tb.getRow(0).get("SKC").toString());
			}
			responsestr += ",\"XSAMT\":\"" + xsamt.toString() + "\""//
					+ ",\"XSCURR\":\"" + xscurr.toString() + "\"" //
					+ ",\"RETAILCURR\":\"" + retailcurr.toString() + "\"" //
					+ ",\"XSNUM\":\"" + xsnum.toString() + "\"" //
					+ ",\"XSSKC\":\"" + xsskc.toString() + "\"";
			if (retailcurr != 0)
				discount = Func.getRound(xscurr / retailcurr, 2);// Math.Round(xscurr / retailcurr, 2, MidpointRounding.AwayFromZero);
			responsestr += ",\"DISCOUNT\":\"" + discount.toString() + "\"";

			// if (xsamt != 0) xsprice = Math.Round(xscurr / xsamt, 2, MidpointRounding.AwayFromZero);
			if (xsamt != 0)
				xsprice = Func.getRound(xscurr / xsamt, 2);// Math.Round(xscurr / xsamt, 2, MidpointRounding.AwayFromZero);

			responsestr += ",\"XSPRICE\":\"" + xsprice.toString() + "\"";

			// 销售计划
			qry = "select f_yearsaleplan(" + Accid + ",to_date('" + mindate + "','yyyy-mm-dd'),to_date('" + maxdate0 + "','yyyy-mm-dd')) as curr from dual";

			tb = DbHelperSQL.Query(qry).getTable(1);

			if (tb.getRowCount() > 0) {
				xsjhcurr = Float.parseFloat(tb.getRow(0).get("CURR").toString());
			}
			// WriteLogTXT("debug", "", "2.0.1");
			responsestr += ",\"XSJHCURR\":\"" + xsjhcurr.toString() + "\"";
			if (xsjhcurr == 0)
				jhwcrate = (float) 100;
			else if (xscurr > 0)
				jhwcrate = Func.getRound(xscurr / xsjhcurr * 100, 2);// Math.Round(xscurr / xsjhcurr, 2, MidpointRounding.AwayFromZero)*100;

			responsestr += ",\"JHWCRATE\":\"" + Func.FormatNumber(jhwcrate, 0) + "\"";
			// 入库数量，金额

			qry = " select nvl(sum(amount),0) as amount,nvl(sum(curr),0) as curr,nvl(sum(retailcurr),0) as retailcurr,count(distinct noteno) as num from (";
			qry += "\n     select a.noteno,b.wareid,b.colorid,sum(case a.ntid when 1 then -b.amount else b.amount end) as amount";
			qry += "\n     ,sum(case a.ntid when 1 then -b.curr else b.curr end) as curr";
			qry += "\n     ,sum((case a.ntid when 1 then -b.amount else b.amount end) *d.retailsale) as retailcurr";
			qry += "\n     from wareinh a join wareinm b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += "\n     left outer join warecode d on b.wareid=d.wareid ";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 ";
			qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			// if (qxbj == 1) //1 启用权限控制
			// {
			// qry += "\n and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
			// qry += "\n and (d.brandid=0 or exists (select 1 from employebrand x2 where d.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			// }
			qry += "\n     group by a.noteno,b.wareid,b.colorid";
			qry += "\n      )";
			// WriteLogTXT("debug", "2", qry);
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				rkamt = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
				rkcurr = Float.parseFloat(tb.getRow(0).get("CURR").toString());
				// retailcurr = Float.parseFloat(tb.getRow(0).get("retailcurr").toString());
				rknum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			}
			responsestr += ",\"RKAMT\":\"" + rkamt.toString() + "\"" //
					+ ",\"RKCURR\":\"" + rkcurr.toString() + "\""
					// + ",\"RETAILCURR\":\"" + retailcurr.toString() + "\""
					+ ",\"RKNUM\":\"" + rknum.toString() + "\"";
			// 当前库存数
			// myTotalCxkczy(maxdate0, Userid, Accid, 0, 0, 0, 0, -1, -1, -1, -1, "", "", "", "", "", "", Qxbj, 0);
			dal.setQxbj(Qxbj);
			dal.setCxdatetime(maxdate0);
			dal.setCalcdate(Calcdate);
			dal.doCxkczy();
			qry = "   select nvl(round(sum(a.amount),0),0) as amount,nvl(round(sum(a.amount*b.costprice),2),0) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^' ) as skc";
			qry += "   from v_cxkczy_data a";
			qry += "   left outer join warecost b on a.wareid=b.wareid and b.daystr='" + maxdate0 + "'";
			if (Housecostbj == 1)
				qry += "  and a.Houseid=b.Houseid";
			else
				qry += "  and b.Houseid=0";
			qry += "   where epid=" + Userid;
			qry += "   and a.amount<>0";
			// WriteLogTXT("debug", "3", qry);
			tb = DbHelperSQL.Query(qry).getTable(1);

			if (tb.getRowCount() > 0) {
				kcamt = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
				kccurr = Float.parseFloat(tb.getRow(0).get("CURR").toString());
				kcskc = Float.parseFloat(tb.getRow(0).get("SKC").toString());

				if (kcamt != 0)
					kcprice = Func.getRound(kccurr / kcamt, Priceprec);// Math.Round(kccurr / kcamt, priceprec, MidpointRounding.AwayFromZero);

			}
			responsestr += ",\"KCAMT\":\"" + kcamt + "\""//
					+ ",\"KCCURR\":\"" + Func.FormatNumber(kccurr, 2) + "\""//
					+ ",\"KCPRICE\":\"" + kcprice + "\"" //
					+ ",\"KCSKC\":\"" + kcskc + "\"";
			// 库存品牌占比
			qry = "  select brandid,brandname,amount,skc from (";
			qry += "  select b.brandid,c.brandname,nvl(sum(a.amount),0) as amount,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			qry += "  from v_cxkczy_data a";
			qry += "  left outer join warecode b on a.wareid=b.wareid";
			qry += "  left outer join brand c on b.brandid=c.brandid";
			qry += "  where a.epid=" + Userid;
			// if (Houseid > 0) qry += " and a.Houseid=" + Houseid;
			qry += "  group by b.brandid,c.brandname ";
			qry += "  order by amount desc";
			qry += "  ) where rownum<=10";
			tb = DbHelperSQL.Query(qry).getTable(1);
			// WriteLogTXT("debug", "4", qry);

			fh = "";
			rs = tb.getRowCount();

			responsestr += ",\"brandkclist\":[";
			Float balkcamt = kcamt;
			Float amount = (float) 0;

			for (int i = 0; i < rs; i++) {
				amount = Float.parseFloat(tb.getRow(i).get("AMOUNT").toString());
				responsestr += fh + "{\"BRANDID\":\"" + tb.getRow(i).get("BRANDID").toString() + "\"" //
						+ ",\"BRANDNAME\":\"" + tb.getRow(i).get("BRANDNAME").toString() + "\""//
						+ ",\"AMOUNT\":\"" + amount + "\"" //
						+ ",\"SKC\":\"" + tb.getRow(i).get("SKC").toString() + "\"}";
				fh = ",";

				balkcamt = balkcamt - amount;
			}
			if (balkcamt > 0) {
				responsestr += fh + "{\"BRANDID\":\"0\"" + ",\"BRANDNAME\":\"<其它>\"" //
						+ ",\"AMOUNT\":\"" + balkcamt + "\"" + ",\"SKC\":\"0\"}";

			}
			responsestr += "]";

			// 总会员数
			qry = "select count(*) as num from guestvip where statetag=1 and Accid=" + Accid;
			// WriteLogTXT("debug", "", "2.1");
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				totalvipnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			}
			responsestr += ",\"TOTALVIPNUM\":\"" + totalvipnum + "\"";

			// 新增会员数
			qry = "select count(*) as num from guestvip where statetag=1 and Accid=" + Accid;
			qry += "  and createdate>=to_date('" + mindate + "','yyyy-mm-dd') and createdate<to_date('" + maxdate + "','yyyy-mm-dd')";
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				newvipnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			}
			responsestr += ",\"NEWVIPNUM\":\"" + newvipnum + "\"";
			// WriteLogTXT("debug", "", "3.1");

			// 本月会员消费金额
			qry = " select nvl(sum(amount),0) as amount,nvl(sum(curr),0) as curr,nvl(sum(retailcurr),0) as retailcurr,count(distinct guestid) as vipnum,count(distinct noteno) as xsnum from (";
			// 店铺零售
			qry += "\n     select a.noteno,a.guestid,sum(b.amount) as amount,sum(b.curr) as curr,sum(b.amount*d.retailsale) as retailcurr ";
			qry += "\n     from wareouth a join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += "\n     left outer join warecode d on b.wareid=d.wareid ";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 and a.ntid=0 and a.guestid>0";
			qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			// if (qxbj == 1) //1 启用权限控制
			// {
			// qry += "\n and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
			// qry += "\n and (d.brandid=0 or exists (select 1 from employebrand x2 where d.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			// }
			qry += "\n     group by a.noteno,a.guestid ";
			// 商场零售
			qry += "\n     union all";
			qry += "\n     select a.noteno,a.guestid,sum(b.amount) as amount,sum(b.curr) as curr,sum(b.amount*d.retailsale) as retailcurr ";
			qry += "\n     from shopsaleh a join shopsalem b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += "\n     left outer join warecode d on b.wareid=d.wareid ";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 and a.guestid>0";
			qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			// if (qxbj == 1) //1 启用权限控制
			// {
			// qry += "\n and exists (select 1 from employehouse x1 where b.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
			// qry += "\n and (d.brandid=0 or exists (select 1 from employebrand x2 where d.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			// }
			qry += "\n     group by a.noteno,a.guestid ";
			qry += "\n   )";
			// WriteLogTXT("debug", "5", qry);

			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				vipxscurr = Float.parseFloat(tb.getRow(0).get("CURR").toString());
				vipretailcurr = Float.parseFloat(tb.getRow(0).get("RETAILCURR").toString());
				vipxsamt = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
				vipxsnum = Integer.parseInt(tb.getRow(0).get("XSNUM").toString()); // 会员消费单数
				vipnum = Integer.parseInt(tb.getRow(0).get("VIPNUM").toString()); // 会员消费人次
				if (xscurr != 0)
					vipxsrate = Func.getRound(vipxscurr / xscurr, 4);// Math.Round(vipxscurr / xscurr, 4, MidpointRounding.AwayFromZero);
				if (vipretailcurr != 0)
					vipdiscount = Func.getRound(vipxscurr / vipretailcurr, 2);// Math.Round(vipxscurr / vipretailcurr, 2, MidpointRounding.AwayFromZero); ;

			}
			// WriteLogTXT("debug", "", "3.3");
			responsestr += ",\"VIPXSCURR\":\"" + Func.FormatNumber(vipxscurr, 2) + "\"" // 会员消费金额
					+ ",\"VIPXSAMT\":\"" + vipxsamt + "\"" // 会员消费数量
					+ ",\"VIPXSNUM\":\"" + vipxsnum + "\"" // 会员消费单数
					+ ",\"VIPNUM\":\"" + vipnum + "\"" // 会员消费次数
					+ ",\"VIPXSRATE\":\"" + vipxsrate + "\"" // 会员消费占比
					+ ",\"VIPDISCOUNT\":\"" + vipdiscount + "\"" // 会员消费平均折扣
			;
			if (totalvipnum != 0)
				vipactive = Func.getRound(vipnum / totalvipnum, 4);// Math.Round((Float)vipnum / (Float)totalvipnum, 4, MidpointRounding.AwayFromZero);
			responsestr += ",\"VIPACTIVE\":\"" + vipactive + "\""; // 会员活跃度
			// 考勤统计
			// 总考勤工人数
			// qry = "select sum(num) as num from (";
			// // --上班
			// qry += " select count(*) as num ";
			// qry += " from checkwork a join employe b on a.epid=b.epid";
			// qry += " where a.uptag0=1 and b.Accid=" + Accid;
			// qry += " and a.classday>='" + mindate + "' and a.classday<'" + maxdate + "'";
			// qry += " union all ";
			// // --下班
			// qry += " select count(*) as num ";
			// qry += " from checkwork a join employe b on a.epid=b.epid";
			// qry += " where a.uptag1=1 and b.Accid=" + Accid;
			// qry += " and a.classday>='" + mindate + "' and a.classday<'" + maxdate + "'";
			// qry += " ) ";
			// tb = DbHelperSQL.Query(qry).getTable(1);
			// if (tb.getRowCount() > 0) {
			// kqzsnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			// }
			// // 迟到人次
			// qry = "select sum(num) as num from (";
			// // --上班迟到人数
			// qry += " select count(*) as num ";
			// qry += " from checkwork a join employe b on a.epid=b.epid ";
			// qry += " where a.checkid=0 and a.uptag0=1 and a.cdztbj0=1 and b.Accid=" + Accid;
			// qry += " and a.classday>='" + mindate + "' and a.classday<'" + maxdate + "'";
			// qry += " union all ";
			// // --下班早退人数
			// qry += " select count(*) as num ";
			// qry += " from checkwork a join employe b on a.epid=b.epid ";
			// qry += " where a.checkid=0 and a.uptag1=1 and a.cdztbj1=1 and b.Accid=" + Accid;
			// qry += " and a.classday>='" + mindate + "' and a.classday<'" + maxdate + "'";
			// qry += " ) ";
			// tb = DbHelperSQL.Query(qry).getTable(1);
			// if (tb.getRowCount() > 0) {
			// kqcdnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			// }
			// checkid:0=正常考勤 1=旷工2=病假 3=事假 4=公休
			// 旷工人数
			// qry = "select count(*) as num ";
			// qry += " from checkwork a join employe b on a.epid=b.epid";
			// qry += " where a.checkid=1 and b.Accid=" + Accid;
			// qry += " and a.classday>='" + mindate + "' and a.classday<'" + maxdate + "'";
			// tb = DbHelperSQL.Query(qry).getTable(1);
			// if (tb.getRowCount() > 0) {
			// kqkgnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			// }
			// 病假人数
			// qry = "select count(*) as num ";
			// qry += " from checkwork a join employe b on a.epid=b.epid";
			// qry += " where a.checkid=2 and b.Accid=" + Accid;
			// qry += " and a.classday>='" + mindate + "' and a.classday<'" + maxdate + "'";
			// tb = DbHelperSQL.Query(qry).getTable(1);
			// if (tb.getRowCount() > 0) {
			// kqbjnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			// }
			// 事假人数
			// qry = "select count(*) as num ";
			// qry += " from checkwork a join employe b on a.epid=b.epid";
			// qry += " where a.checkid=3 and b.Accid=" + Accid;
			// qry += " and a.classday>='" + mindate + "' and a.classday<'" + maxdate + "'";
			// tb = DbHelperSQL.Query(qry).getTable(1);
			// if (tb.getRowCount() > 0) {
			// kqsjnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			// }
			// 公休人数
			// qry = "select count(*) as num ";
			// qry += " from checkwork a join employe b on a.epid=b.epid";
			// qry += " where a.checkid=4 and b.Accid=" + Accid;
			// qry += " and a.classday>='" + mindate + "' and a.classday<'" + maxdate + "'";
			// tb = DbHelperSQL.Query(qry).getTable(1);
			// if (tb.getRowCount() > 0) {
			// kqgxnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			// }

			// 考勤数据
			responsestr += ",\"KQZSNUM\":\"" + kqzsnum + "\"" // 总人次
					+ ",\"KQCDNUM\":\"" + kqcdnum + "\"" // 迟到人次
					+ ",\"KQKGNUM\":\"" + kqkgnum + "\"" // 旷工人次
					+ ",\"KQSJNUM\":\"" + kqsjnum + "\"" // 事假人次
					+ ",\"KQBJNUM\":\"" + kqbjnum + "\"" // 病假人次
					+ ",\"KQGXNUM\":\"" + kqgxnum + "\"" // 公休人次
			;
			// responsestr += ",\"saleday7list\":";
			// 近8日销售
			qry = " select datestr,weekstr,nvl(sum(amount),0) as amount,nvl(sum(curr),0) as curr from (";
			// 店铺零售，批发

			qry += "\n     select to_char(a.notedate,'yyyy.mm.dd') as datestr,to_char(a.notedate,'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''') as weekstr,sum(case a.ntid when 2 then -b.amount else b.amount end) as amount";
			qry += "\n     ,sum(case a.ntid when 2 then -b.curr else b.curr end) as curr";
			qry += "\n     from wareouth a join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += "\n     left outer join warecode d on b.wareid=d.wareid";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 ";
			qry += "\n     and a.notedate>=to_date('" + mindate0 + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate1 + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			qry += "\n     group by to_char(a.notedate,'yyyy.mm.dd'),to_char(a.notedate,'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''')";
			// if (Qxbj == 1) //1 启用权限控制
			// {
			// qry += "\n and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
			// qry += "\n and (d.brandid=0 or exists (select 1 from employebrand x2 where d.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			// }
			qry += "\n     union all";
			// 商场零售
			qry += "\n     select to_char(a.notedate,'yyyy.mm.dd') as datestr,to_char(a.notedate,'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''') as weekstr,sum(b.amount) as amount";
			qry += "\n     ,sum(b.curr) as curr";
			qry += "\n     from shopsaleh a join shopsalem b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += "\n     left outer join warecode d on b.wareid=d.wareid";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 ";
			qry += "\n     and a.notedate>=to_date('" + mindate0 + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate1 + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			// if (Qxbj == 1) //1 启用权限控制
			// {
			// qry += "\n and exists (select 1 from employehouse x1 where b.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
			// qry += "\n and (d.brandid=0 or exists (select 1 from employebrand x2 where d.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			// }
			qry += "\n     group by to_char(a.notedate,'yyyy.mm.dd'),to_char(a.notedate,'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''')";
			Date nowday = Func.StrToDate(mindate0);// DateTime.Parse(mindate0);
			// Func.DateToStr(Func.getDataAdd(nowday, i))
			for (int i = 0; i <= 7; i++) {

				String ss = Func.DateToStr(Func.getDataAdd(nowday, i), "yyyy.MM.dd");
				qry += "\n    union all";
				// qry += "\n select '" + nowday.AddDays(i).toString("yyyy.MM.dd") + "' as datestr,to_char(to_date('" + nowday.AddDays(i).toString("yyyy-MM-dd") + "','yyyy-mm-dd'),'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''') as weekstr,0 as amount,0 as curr from dual ";
				qry += "\n    select '" + ss + "' as datestr,to_char(to_date('" + ss + "','yyyy-mm-dd'),'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''') as weekstr,0 as amount,0 as curr from dual ";
			}
			qry += "\n      ) group by datestr,weekstr order by datestr";
			//			//System.out.println(qry);
			// WriteLogTXT("debug", "totalfirstpage6", qry);
			// WriteDebugTXT( "totalfirstpage6", qry);

			tb = DbHelperSQL.Query(qry).getTable(1);
			fh = "";
			rs = tb.getRowCount();

			responsestr += ",\"saleday7list\":[";

			for (int i = 0; i < rs; i++) {
				responsestr += fh + "{\"DATESTR\":\"" + Func.subString(tb.getRow(i).get("DATESTR").toString(), 6, 5) + tb.getRow(i).get("WEEKSTR").toString().replace("星期", "") + "\""
				// + ",\"WEEKSTR\":\"" + tb.getRow(i).get("weekstr").toString().Replace("星期", "")+"\""
						+ ",\"AMOUNT\":\"" + tb.getRow(i).get("AMOUNT").toString() + "\""//
						+ ",\"CURR\":\"" + tb.getRow(i).get("CURR").toString() + "\"}";
				fh = ",";

			}
			responsestr += "]";

			// 销售额前10店铺 要控制查询权限
			qry = " select Houseid,housename,amount,curr from (";
			qry += "\n     select Houseid,housename,round(amount,0) as amount,curr from (";
			// 店铺零售，批发
			qry += "\n     select Houseid,housename,sum(amount) as amount,sum(curr) as curr from (";
			qry += "\n     select a.Houseid,c.housename,sum(case a.ntid when 2 then -b.amount else b.amount end) as amount";
			qry += "\n     ,sum(case a.ntid when 2 then -b.curr else b.curr end) as curr";
			qry += "\n     from wareouth a join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno";
			// qry += "\n left outer join warecost c on b.wareid=c.wareid and c.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
			// qry += "\n left outer join warecode d on b.wareid=d.wareid ";
			qry += "\n     left outer join warehouse c on a.Houseid=c.Houseid ";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 ";
			qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			if (Qxbj == 1) // 1 启用权限控制
			{
				qry += "\n    and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
				// qry += "\n and (d.brandid=0 or exists (select 1 from employebrand x2 where d.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			}

			qry += "\n     group by a.Houseid,c.housename";
			qry += "\n     union all";
			// 商场零售
			qry += "\n     select a.Houseid,c.housename,sum(b.amount) as amount";
			qry += "\n     ,sum(b.curr) as curr";
			qry += "\n     from shopsaleh a join shopsalem b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += "\n     left outer join warehouse c on a.Houseid=c.Houseid ";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 ";
			qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			if (Qxbj == 1) // 1 启用权限控制
			{
				qry += "\n    and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
				// qry += "\n and (d.brandid=0 or exists (select 1 from employebrand x2 where d.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			}
			qry += "\n     group by a.Houseid,c.housename";

			qry += "\n      ) group by Houseid,housename ) ";
			qry += "\n     order by curr desc )  where rownum<=10";
			// WriteLogTXT("debug", "7", qry);

			tb = DbHelperSQL.Query(qry).getTable(1);
			fh = "";
			rs = tb.getRowCount();

			responsestr += ",\"housetop10list\":[";

			for (int i = 0; i < rs; i++) {
				responsestr += fh + "{\"HOUSEID\":\"" + tb.getRow(i).get("HOUSEID").toString() + "\"" //
						+ ",\"HOUSENAME\":\"" + tb.getRow(i).get("HOUSENAME").toString() + "\"" //
						+ ",\"AMOUNT\":\"" + tb.getRow(i).get("AMOUNT").toString() + "\""//
						+ ",\"CURR\":\"" + tb.getRow(i).get("CURR").toString() + "\"}";
				fh = ",";

			}
			responsestr += "]";

		} else if (Levelid == 1 || Levelid == 2 || Levelid == 7)
		// Levelid: 0=系统管理员,1=店员,2=店长,3=财务,4=经理,5=老板,6=督导,7=收银员,8=AD客服,9=库管
		// 店员只能看自己开单的销售额；店长、收银员可以看全店的销售额
		{

			Float manxscurr = (float) 0; // 个人销售金额
			Float manxsamt = (float) 0; // 个人销售数量
			Float manxsnum = (float) 0; // 个人销售款数
			qry = " select nvl(sum(amount),0) as amount,nvl(sum(curr),0) as curr,nvl(sum(retailcurr),0) as retailcurr,count(distinct noteno) as num from (";
			qry += "\n     select a.noteno,a.rs,sum(case a.ntid when 2 then -b.amount else b.amount end)/a.rs as amount,sum(case a.ntid when 2 then -b.curr else b.curr end)/a.rs as curr";
			qry += "\n     ,sum((case a.ntid when 2 then -b.amount else b.amount end) *d.retailsale)/a.rs as retailcurr";
			// qry += "\n ,sum((case a.ntid when 2 then -b.amount else b.amount end) *c.costprice)/a.rs as cbcurr";
			qry += "\n     from wareouth a join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno";
			// qry += "\n left outer join warecost c on b.wareid=c.wareid and c.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
			qry += "\n     left outer join warecode d on b.wareid=d.wareid ";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 and a.rs>0";
			qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			if (Levelid == 1)
				qry += "\n     and exists (select 1 from waresaleman a1 where a.Accid=a1.Accid and a.noteno=a1.noteno and a1.epid=" + Userid + ") ";
			else {
				if (Houseid > 0)
					qry += " and a.Houseid=" + Houseid;
			}
			qry += "\n     group by a.noteno,a.rs";
			// 商场零售
			qry += "\n     union all";
			qry += "\n     select a.noteno,1 as rs,sum(b.amount) as amount,sum(b.curr) as curr";
			qry += "\n     ,sum(b.amount*d.retailsale) as retailcurr";
			// qry += "\n ,sum(b.amount*c.costprice) as cbcurr";
			qry += "\n     from shopsaleh a join shopsalem b on a.Accid=b.Accid and a.noteno=b.noteno";
			// qry += "\n left outer join warecost c on b.wareid=c.wareid and c.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
			qry += "\n     left outer join warecode d on b.wareid=d.wareid ";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1";
			qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			if (Levelid == 1)
				qry += "\n     and b.salemanid=" + Userid;
			else {
				if (Houseid > 0)
					qry += " and a.Houseid=" + Houseid;
			}
			qry += "\n     group by a.noteno";
			qry += "\n      )";
			// WriteLogTXT("debug", "1", qry);
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				manxsamt = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
				manxscurr = Float.parseFloat(tb.getRow(0).get("CURR").toString());
				// retailcurr = Float.parseFloat(tb.getRow(0).get("retailcurr").toString());
				manxsnum = Float.parseFloat(tb.getRow(0).get("NUM").toString());
			}
			responsestr += ",\"MANXSAMT\":\"" + manxsamt + "\"" //
					+ ",\"MANXSCURR\":\"" + Func.FormatNumber(manxscurr, 2) + "\""//
					// + ",\"RETAILCURR\":\"" + retailcurr.toString() + "\""
					+ ",\"MANXSNUM\":\"" + manxsnum + "\"";

			// 销售数量，金额，单数，折扣

			qry = " select nvl(sum(amount),0) as amount,nvl(sum(curr),0) as curr,nvl(sum(retailcurr),0) as retailcurr,count(distinct noteno) as num from (";
			qry += "\n     select a.noteno,a.rs,sum(case a.ntid when 2 then -b.amount else b.amount end)/a.rs as amount,sum(case a.ntid when 2 then -b.curr else b.curr end)/a.rs as curr";
			qry += "\n     ,sum((case a.ntid when 2 then -b.amount else b.amount end) *d.retailsale)/a.rs as retailcurr";
			// qry += "\n ,sum((case a.ntid when 2 then -b.amount else b.amount end) *c.costprice)/a.rs as cbcurr";
			qry += "\n     from wareouth a join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno";
			// qry += "\n left outer join warecost c on b.wareid=c.wareid and c.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
			qry += "\n     left outer join warecode d on b.wareid=d.wareid ";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 and a.rs>0";
			qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			if (Levelid == 1)
				qry += "\n and exists (select 1 from waresaleman a1 where a.Accid=a1.Accid and a.noteno=a1.noteno and a1.epid=" + Userid + ") ";
			else {
				if (Houseid > 0)
					qry += " and a.Houseid=" + Houseid;
			}
			qry += "\n     group by a.noteno,a.rs";
			// 商场零售
			qry += "\n     union all";
			qry += "\n     select a.noteno,1 as rs,sum(b.amount) as amount,sum(b.curr) as curr";
			qry += "\n     ,sum(b.amount*d.retailsale) as retailcurr";
			// qry += "\n ,sum(b.amount*c.costprice) as cbcurr";
			qry += "\n     from shopsaleh a join shopsalem b on a.Accid=b.Accid and a.noteno=b.noteno";
			// qry += "\n left outer join warecost c on b.wareid=c.wareid and c.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
			qry += "\n     left outer join warecode d on b.wareid=d.wareid ";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1";
			qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			if (Levelid == 1)
				qry += "\n and b.salemanid=" + Userid;
			else {
				if (Houseid > 0)
					qry += " and a.Houseid=" + Houseid;
			}
			qry += "\n     group by a.noteno";
			qry += "\n      )";
			// WriteLogTXT("debug", "1", qry);
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				xsamt = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
				xscurr = Float.parseFloat(tb.getRow(0).get("CURR").toString());
				retailcurr = Float.parseFloat(tb.getRow(0).get("RETAILCURR").toString());
				xsnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			}
			responsestr += ",\"XSAMT\":\"" + xsamt + "\"" //
					+ ",\"XSCURR\":\"" + Func.FormatNumber(xscurr, 2) + "\"" //
					+ ",\"RETAILCURR\":\"" + Func.FormatNumber(retailcurr, 2) + "\""//
					+ ",\"XSNUM\":\"" + xsnum + "\"";
			if (retailcurr != 0)
				discount = Func.getRound(xscurr / retailcurr, 2);// Math.Round(xscurr / retailcurr, 2, MidpointRounding.AwayFromZero);
			responsestr += ",\"DISCOUNT\":\"" + discount + "\"";
			// WriteLogTXT("debug", "1.1", "");

			// 近8日销售
			qry = " select datestr,weekstr,nvl(sum(amount),0) as amount,nvl(sum(curr),0) as curr from (";
			// 店铺零售，批发

			qry += "\n     select  to_char(a.notedate,'yyyy.mm.dd') as datestr,to_char(a.notedate,'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''') as weekstr,sum(case a.ntid when 2 then -b.amount else b.amount end) as amount";
			qry += "\n     ,sum(case a.ntid when 2 then -b.curr else b.curr end) as curr";
			qry += "\n     from wareouth a join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += "\n     left outer join warecode d on b.wareid=d.wareid";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 ";
			qry += "\n     and a.notedate>=to_date('" + mindate0 + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate1 + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			if (Levelid == 1)
				qry += "\n     and exists (select 1 from waresaleman a1 where a.Accid=a1.Accid and a.noteno=a1.noteno and a1.epid=" + Userid + ") ";
			else {
				if (Houseid > 0)
					qry += " and a.Houseid=" + Houseid;
			}
			qry += "\n     group by to_char(a.notedate,'yyyy.mm.dd'),to_char(a.notedate,'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''')";
			// if (qxbj == 1) //1 启用权限控制
			// {
			// qry += "\n and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
			// qry += "\n and (d.brandid=0 or exists (select 1 from employebrand x2 where d.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			// }
			qry += "\n     union all";
			// 商场零售
			qry += "\n     select to_char(a.notedate,'yyyy.mm.dd') as datestr,to_char(a.notedate,'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''') as weekstr,sum(b.amount) as amount";
			qry += "\n     ,sum(b.curr) as curr";
			qry += "\n     from shopsaleh a join shopsalem b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += "\n     left outer join warecode d on b.wareid=d.wareid";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 ";
			if (Levelid == 1)
				qry += "\n and b.salemanid=" + Userid;
			else {
				if (Houseid > 0)
					qry += " and a.Houseid=" + Houseid;
			}
			qry += "\n     and a.notedate>=to_date('" + mindate0 + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate1 + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			// if (qxbj == 1) //1 启用权限控制
			// {
			// qry += "\n and exists (select 1 from employehouse x1 where b.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
			// qry += "\n and (d.brandid=0 or exists (select 1 from employebrand x2 where d.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			// }
			qry += "\n     group by to_char(a.notedate,'yyyy.mm.dd'),to_char(a.notedate,'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''')";

			Date nowday = Func.StrToDate(mindate0);// DateTime.Parse(mindate0);
			for (int i = 0; i <= 7; i++) {
				// Date nowday1 = Func.getDataAdd(nowday, i);
				String ss = Func.DateToStr(Func.getDataAdd(nowday, i), "yyyy.MM.dd");

				// String ss = Func.DateToStr(Func.getDataAdd(nowday, i),"yyyy.MM.dd");
				qry += "\n    union all";
				// qry += "\n select '" + nowday.AddDays(i).toString("yyyy.MM.dd") + "' as datestr,to_char(to_date('" + nowday.AddDays(i).toString("yyyy-MM-dd") + "','yyyy-mm-dd'),'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''') as weekstr,0 as amount,0 as curr from dual ";
				qry += "\n    select '" + ss + "' as datestr,to_char(to_date('" + ss + "','yyyy-mm-dd'),'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''') as weekstr,0 as amount,0 as curr from dual ";

				// qry += "\n union all";
				// qry += "\n select '" + ss + "' as datestr,to_char(to_date('" + Func.DateToStr(nowday1, "yyyy-MM-dd")
				// + "','yyyy-mm-dd'),'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''') as weekstr,0 as amount,0 as curr from dual ";
			}
			qry += "\n      ) group by datestr,weekstr order by datestr";
			// WriteLogTXT("debug", "2", qry);

			tb = DbHelperSQL.Query(qry).getTable(1);
			fh = "";
			rs = tb.getRowCount();

			responsestr += ",\"saleday7list\":[";

			for (int i = 0; i < rs; i++) {
				responsestr += fh + "{\"DATESTR\":\"" + Func.subString(tb.getRow(i).get("DATESTR").toString(), 6, 5) + tb.getRow(i).get("WEEKSTR").toString().replace("星期", "") + "\""
				// + ",\"WEEKSTR\":\"" + tb.getRow(i).get("weekstr").toString().Replace("星期", "")+"\""
						+ ",\"AMOUNT\":\"" + tb.getRow(i).get("AMOUNT").toString() + "\"" //
						+ ",\"CURR\":\"" + tb.getRow(i).get("CURR").toString() + "\"}";
				fh = ",";

			}
			responsestr += "]";

			// 销售计划
			// select f_yearhouseplan(561,to_date('2015-01-01','yyyy-mm-dd'),to_date('2016-03-31','yyyy-mm-dd') ) a from dual 取店铺销售计划
			qry = "select nvl(sum(curr),0) as curr from salemanplan where epid=" + Userid;
			qry += "  and saledate>=to_date('" + mindate + "','yyyy-mm-dd') and saledate<to_date('" + maxdate + "','yyyy-mm-dd')";
			// WriteLogTXT("debug", "", "2");

			tb = DbHelperSQL.Query(qry).getTable(1);
			// WriteLogTXT("debug", "", "2.0");
			if (tb.getRowCount() > 0) {
				xsjhcurr = Float.parseFloat(tb.getRow(0).get("CURR").toString());
			}
			// WriteLogTXT("debug", "", "2.0.1");
			responsestr += ",\"XSJHCURR\":\"" + Func.FormatNumber(xsjhcurr, 2) + "\"";
			if (xsjhcurr == 0)
				jhwcrate = (float) 100;
			else if (xscurr > 0)
				jhwcrate = Func.getRound(xscurr / xsjhcurr * 100, 2);// Math.Round(xscurr / xsjhcurr, 2, MidpointRounding.AwayFromZero) * 100;

			responsestr += ",\"JHWCRATE\":\"" + Func.FormatNumber(jhwcrate, 0) + "\"";

			// WriteLogTXT("debug", "", "2.0.2");

			// 总会员数
			qry = "select count(*) as num from guestvip where statetag=1 and Accid=" + Accid;
			// WriteLogTXT("debug", "", "2.1");
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				totalvipnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			}
			responsestr += ",\"TOTALVIPNUM\":\"" + totalvipnum + "\"";

			// 我的会员数
			qry = "select count(*) as num from guestvip where statetag=1 and Accid=" + Accid + " and epid=" + Userid;
			tb = DbHelperSQL.Query(qry).getTable(1);
			// WriteLogTXT("debug", "", "2.2");
			if (tb.getRowCount() > 0) {
				myvipnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			}
			responsestr += ",\"MYVIPNUM\":\"" + myvipnum + "\"";
			// WriteLogTXT("debug", "", "3");

			// 新增会员数
			qry = "select count(*) as num from guestvip where statetag=1 and Accid=" + Accid;
			qry += "  and createdate>=to_date('" + mindate + "','yyyy-mm-dd') and createdate<to_date('" + maxdate + "','yyyy-mm-dd')";
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				newvipnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			}
			responsestr += ",\"NEWVIPNUM\":\"" + newvipnum + "\"";
			// WriteLogTXT("debug", "", "3.1");

			// 会员消费金额
			qry = " select nvl(sum(amount),0) as amount,nvl(sum(curr),0) as curr,nvl(sum(retailcurr),0) as retailcurr,count(distinct guestid) as num from (";
			// 店铺零售

			qry += "\n     select a.guestid,sum(b.amount) as amount,sum(b.curr) as curr,sum(b.amount*d.retailsale) as retailcurr ";
			qry += "\n     from wareouth a join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += "\n     left outer join warecode d on b.wareid=d.wareid ";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 and a.ntid=0 and a.guestid>0";
			qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			if (Houseid > 0)
				qry += " and a.Houseid=" + Houseid;
			qry += "\n     group by a.guestid";
			qry += "\n     union all";
			// 商场零售
			qry += "\n     select a.guestid,sum(b.amount) as amount,sum(b.curr) as curr ,sum(b.amount*d.retailsale) as retailcurr ";
			qry += "\n     from shopsaleh a join shopsalem b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += "\n     left outer join warecode d on b.wareid=d.wareid ";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 and a.guestid>0";
			qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			if (Houseid > 0)
				qry += " and a.Houseid=" + Houseid;
			qry += "\n     group by a.guestid";
			qry += "\n      )";
			// WriteLogTXT("debug", "", "3.2");
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				vipxscurr = Float.parseFloat(tb.getRow(0).get("CURR").toString());
				vipxsamt = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
				vipretailcurr = Float.parseFloat(tb.getRow(0).get("RETAILCURR").toString());
				vipxsnum = Integer.parseInt(tb.getRow(0).get("NUM").toString()); // 会员消费人次

				if (xscurr != 0)
					vipxsrate = Func.getRound(vipxscurr / xscurr, 4);// Math.Round(vipxscurr / xscurr, 4, MidpointRounding.AwayFromZero);
				if (vipretailcurr != 0)
					vipdiscount = Func.getRound(vipxscurr / vipretailcurr, 2);// Math.Round(vipxscurr / vipretailcurr, 2, MidpointRounding.AwayFromZero); ;

			}
			// WriteLogTXT("debug", "", "3.3");
			responsestr += ",\"VIPXSCURR\":\"" + Func.FormatNumber(vipxscurr, 2) + "\"" // 会员消费金额
					+ ",\"VIPXSAMT\":\"" + vipxsamt + "\"" // 会员消费数量
					+ ",\"VIPXSNUM\":\"" + vipxsnum + "\"" // 会员消费次数
					+ ",\"VIPXSRATE\":\"" + vipxsrate + "\"" // 会员消费占比 //toString("p")
					+ ",\"VIPDISCOUNT\":\"" + vipdiscount + "\"" // 会员平均折扣
			;
			if (totalvipnum != 0)
				vipactive = Func.getRound(vipxsnum / totalvipnum, 4);// Math.Round((Float)vipxsnum / (Float)totalvipnum, 4, MidpointRounding.AwayFromZero);
			responsestr += ",\"VIPACTIVE\":\"" + vipactive + "\""; // 会员活跃度toString("p"

			// WriteLogTXT("debug", "", "4");
			// 个人销售计划
			// qry = " select nvl(sum(curr),0) as curr from SALEMANPLAN where epid=" + Userid;
			// qry += " and saledate>=to_date('" + mindate + "','yyyy-mm-dd') and saledate<to_date('" + maxdate + "','yyyy-mm-dd')";
			// tb = DbHelperSQL.Query(qry).getTable(1);
			// responsestr += ",\"MANJHCURR\":\"" + tb.getRow(0).get("curr").toString() + "\"";

			// 调入未收数
			qry = "select nvl(sum(b.amount),0) as amount,count(distinct a.noteno) as num";
			// qry += "\n ,nvl(sum( b.amount*d.retailsale),0) as retailcurr";
			qry += "\n     from allotouth a join allotoutm b on a.Accid=b.Accid and a.noteno=b.noteno";
			// qry += "\n left outer join warecost c on b.wareid=c.wareid and c.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
			qry += "\n     left outer join warecode d on b.wareid=d.wareid ";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 ";
			qry += "\n     and not exists (select 1 from allotinh a1 where a.Accid=a1.Accid and a.noteno=a1.noteno1 and a1.statetag=1)"; // 已经对应调入单的单据不再显示
			if (Houseid > 0)
				qry += " and a.toHouseid=" + Houseid;
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				drztamt = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
				drztnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			}
			responsestr += ",\"DRZTAMT\":\"" + drztamt + "\"" + ",\"DRZTNUM\":\"" + drztnum + "\"";// 调入在途

			// 调出未收数
			qry = "select nvl(sum(b.amount),0) as amount,count(distinct a.noteno) as num";
			// qry += "\n ,nvl(sum( b.amount*d.retailsale),0) as retailcurr";
			qry += "\n     from allotouth a join allotoutm b on a.Accid=b.Accid and a.noteno=b.noteno";
			// qry += "\n left outer join warecost c on b.wareid=c.wareid and c.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
			qry += "\n     left outer join warecode d on b.wareid=d.wareid ";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 ";
			qry += "\n     and not exists (select 1 from allotinh a1 where a.Accid=a1.Accid and a.noteno=a1.noteno1 and a1.statetag=1)"; // 已经对应调入单的单据不再显示
			// qry += "\n and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			if (Houseid > 0)
				qry += " and a.Houseid=" + Houseid;
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				dcztamt = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
				dcztnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			}
			responsestr += ",\"DCZTAMT\":\"" + drztamt + "\"" + ",\"DCZTNUM\":\"" + drztnum + "\"";// 调入在途

			// 零售单中一张单据一个货号的单据数
			qry = " select count(*) as num from (";
			// 店铺零售
			qry += "\n     select a.noteno,count(*) as num ";
			qry += "\n     from wareouth a join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 and a.ntid=0";
			qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			qry += "\n     group by a.noteno having count(*)=1";
			qry += "\n     union all";
			// 商场零售
			qry += "\n     select a.noteno,count(*) as num ";
			qry += "\n     from shopsaleh a join shopsalem b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1";
			qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			qry += "\n     group by a.noteno having count(*)=1";

			qry += "\n     )";
			// WriteLogTXT("debug", "", "3.2");
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				salenum1 = Integer.parseInt(tb.getRow(0).get("NUM").toString());

			}
			// 零售单中一张单据多个货号的单据数
			qry = " select count(*) as num,nvl(sum(curr),0) as curr from (";
			// 店铺零售
			qry += "\n     select a.noteno,count(*) as num,sum(b.curr) as curr ";
			qry += "\n     from wareouth a join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 and a.ntid=0";
			qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			qry += "\n     group by a.noteno having count(*)>1";
			qry += "\n     union all";
			// 商场零售
			qry += "\n     select a.noteno,count(*) as num,sum(b.curr) as curr ";
			qry += "\n     from shopsaleh a join shopsalem b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1";
			qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			qry += "\n     group by a.noteno having count(*)>1";

			qry += "\n     )";
			// WriteLogTXT("debug", "", "3.2");
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				salenumn = Integer.parseInt(tb.getRow(0).get("NUM").toString());
				linkcurr = Float.parseFloat(tb.getRow(0).get("CURR").toString());
			}
			if (salenum1 > 0)
				linkrate = Func.getRound(salenumn / salenum1, 2);// Math.Round((Float)salenumn / (Float)salenum1, 2, MidpointRounding.AwayFromZero);
			// WriteLogTXT("debug", "", "3.3");
			responsestr += ",\"LINKRATE\":\"" + linkrate + "\"" // 连带率
					+ ",\"LINKCURR\":\"" + Func.FormatNumber(linkcurr, 2) + "\"";

			// 店员个人排名
			qry = "select epid,curr,xsord from (";
			qry += " select epid,curr ,rank() over (order by curr desc) as xsord from (";

			qry += " select epid,nvl(sum(curr),0) as curr from (";
			// 店铺零售
			qry += " select c.epid,round(b.curr/a.rs,2) as curr";
			qry += " from wareouth a join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += " join waresaleman c on a.Accid=c.Accid and a.noteno=c.noteno";
			qry += " where a.Accid=" + Accid + " and a.statetag=1 and a.rs>0";
			qry += " and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			qry += " union all";
			// 商场零售
			qry += " select b.salemanid as epid,b.curr";
			qry += " from shopsaleh a join shopsalem b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += " where a.Accid=" + Accid + " and a.statetag=1";
			qry += " and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";

			qry += " ) group by epid  )";
			qry += " )";
			qry += " where epid=" + Userid;
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				salemanord = Integer.parseInt(tb.getRow(0).get("XSORD").toString());
			}
			responsestr += ",\"SALEMANORD\":\"" + salemanord + "\""; // 连带率
			// 店铺销售排名
			if (Houseid > 0) {
				qry = "select Houseid,curr,xsord from (";
				qry += " select Houseid,curr ,rank() over (order by curr desc) as xsord from (";
				qry += " select Houseid,nvl(sum(curr),0) as curr from (";
				// 店铺零售
				qry += " select a.Houseid,sum(case a.ntid when 2 then -b.curr else b.curr end) as curr";
				qry += " from wareouth a join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno";
				qry += " where a.Accid=" + Accid + " and a.statetag=1 ";
				qry += " and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
				qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
				qry += " group by a.Houseid  ";
				qry += " union all";
				// 商场零售
				qry += " select a.Houseid,sum(b.curr) as curr";
				qry += " from shopsaleh a join shopsalem b on a.Accid=b.Accid and a.noteno=b.noteno";
				qry += " where a.Accid=" + Accid + " and a.statetag=1";
				qry += " and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
				qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
				qry += " group by a.Houseid  ";
				qry += " ) group by Houseid";
				qry += " ) )";
				qry += " where Houseid=" + Houseid;
				tb = DbHelperSQL.Query(qry).getTable(1);
				if (tb.getRowCount() > 0) {
					houseord = Integer.parseInt(tb.getRow(0).get("XSORD").toString());
				}
			}
			responsestr += ",\"HOUSEORD\":\"" + houseord + "\""; // 连带率
			// 入库数量，金额

			qry = " select nvl(sum(amount),0) as amount,nvl(sum(curr),0) as curr,nvl(sum(retailcurr),0) as retailcurr,count(distinct noteno) as num from (";
			qry += "\n     select a.noteno,b.wareid,b.colorid,sum(case a.ntid when 1 then -b.amount else b.amount end) as amount";
			qry += "\n     ,sum(case a.ntid when 1 then -b.curr else b.curr end) as curr";
			qry += "\n     ,sum((case a.ntid when 1 then -b.amount else b.amount end) *d.retailsale) as retailcurr";
			qry += "\n     from wareinh a join wareinm b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += "\n     left outer join warecode d on b.wareid=d.wareid ";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 ";
			qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
			if (Houseid > 0)
				qry += " and a.Houseid=" + Houseid;
			qry += "\n     group by a.noteno,b.wareid,b.colorid";
			qry += "\n      )";
			// WriteLogTXT("debug", "", "1");
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				rkamt = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
				rkcurr = Float.parseFloat(tb.getRow(0).get("CURR").toString());
				// retailcurr = Float.parseFloat(tb.getRow(0).get("retailcurr").toString());
				rknum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			}
			responsestr += ",\"RKAMT\":\"" + rkamt + "\"" //
					+ ",\"RKCURR\":\"" + Func.FormatNumber(rkcurr, 2) + "\""//
					// + ",\"RETAILCURR\":\"" + retailcurr.toString() + "\""
					+ ",\"RKNUM\":\"" + rknum + "\"";

			// myTotalCxkczy(maxdate0, Userid, Accid, Houseid, 0, 0, 0, -1, -1, -1, -1, "", "", "", "", "", "", 0, 0);

			dal.setCxdatetime(maxdate0);
			dal.setHouseid(Houseid);
			dal.setQxbj(0);
			dal.doCxkczy();

			qry = "   select nvl(sum(a.amount),0) as amount,nvl(round(sum(a.amount*b.costprice),2),0) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^' ) as skc";
			qry += "   from v_cxkczy_data a";
			qry += "   left outer join warecost b on a.wareid=b.wareid and b.daystr='" + maxdate + "'";
			if (Housecostbj == 1)
				qry += " and a.Houseid=b.Houseid";
			else
				qry += "  and b.Houseid=0";
			qry += "   where a.epid=" + Userid;
			// if (Houseid > 0) qry += " and a.Houseid=" + Houseid;
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				kcamt = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
				kccurr = Float.parseFloat(tb.getRow(0).get("CURR").toString());
				kcskc = Float.parseFloat(tb.getRow(0).get("SKC").toString());

				if (kcamt != 0)
					kcprice = Func.getRound(kccurr / kcamt, Priceprec);// Math.Round(kccurr / kcamt, priceprec, MidpointRounding.AwayFromZero);

			}
			responsestr += ",\"KCAMT\":\"" + kcamt + "\"" //
					+ ",\"KCCURR\":\"" + Func.FormatNumber(kccurr, 2) + "\"" //
					+ ",\"KCPRICE\":\"" + kcprice + "\""//
					+ ",\"KCSKC\":\"" + kcskc + "\"";

		} else if (Levelid == 9 || Levelid == 8)// 库管首页
		// Levelid: 0=系统管理员,1=店员,2=店长,3=财务,4=经理,5=老板,6=督导,7=收银员,8=AD客服,9=库管
		{
			// 库存类型占比
			// myTotalCxkczy(maxdate0, Userid, Accid, Houseid, 0, 0, 0, -1, -1, -1, -1, "", "", "", "", "", "", 0, 0);
			dal.setCxdatetime(maxdate0);
			dal.setHouseid(Houseid);
			dal.setQxbj(0);
			dal.doCxkczy();

			qry = "  select typeid,typename,amount,skc from (";
			qry += "  select b.typeid,c.fullname as typename ,nvl(sum(a.amount),0) as amount,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			qry += "  from v_cxkczy_data a";
			qry += "  left outer join warecode b on a.wareid=b.wareid";
			qry += "  left outer join waretype c on b.typeid=c.typeid";
			qry += "  where a.epid=" + Userid;
			// if (Houseid > 0) qry += " and a.Houseid=" + Houseid;
			qry += "  group by b.typeid,c.fullname ";
			qry += "  order by amount desc";
			qry += "  ) where rownum<=10";
			tb = DbHelperSQL.Query(qry).getTable(1);

			fh = "";
			rs = tb.getRowCount();

			responsestr += ",\"typekclist\":[";

			for (int i = 0; i < rs; i++) {
				responsestr += fh + "{\"TYPEID\":\"" + tb.getRow(i).get("TYPEID").toString() + "\""//
						+ ",\"TYPENAME\":\"" + tb.getRow(i).get("TYPENAME").toString() + "\"" //
						+ ",\"AMOUNT\":\"" + tb.getRow(i).get("AMOUNT").toString() + "\"" //
						+ ",\"SKC\":\"" + tb.getRow(i).get("SKC").toString() + "\"}";
				fh = ",";

			}
			responsestr += "]";
			// 调入未收数
			qry = "select nvl(sum(b.amount),0) as amount,count(distinct a.noteno) as num";
			// qry += "\n ,nvl(sum( b.amount*d.retailsale),0) as retailcurr";
			qry += "\n     from allotouth a join allotoutm b on a.Accid=b.Accid and a.noteno=b.noteno";
			// qry += "\n left outer join warecost c on b.wareid=c.wareid and c.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
			// qry += "\n left outer join warecode d on b.wareid=d.wareid ";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 ";
			qry += "\n     and not exists (select 1 from allotinh a1 where a.Accid=a1.Accid and a.noteno=a1.noteno1 and a1.statetag=1)"; // 已经对应调入单的单据不再显示
			// qry += "\n and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			if (Houseid > 0)
				qry += " and a.toHouseid=" + Houseid;
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				drztamt = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
				drztnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			}
			responsestr += ",\"DRZTAMT\":\"" + drztamt + "\"" //
					+ ",\"DRZTNUM\":\"" + drztnum + "\"";// 调入在途

			// 调出未收数
			qry = "select nvl(sum(b.amount),0) as amount,count(distinct a.noteno) as num";
			// qry += "\n ,nvl(sum( b.amount*d.retailsale),0) as retailcurr";
			qry += "\n     from allotouth a join allotoutm b on a.Accid=b.Accid and a.noteno=b.noteno";
			// qry += "\n left outer join warecost c on b.wareid=c.wareid and c.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
			// qry += "\n left outer join warecode d on b.wareid=d.wareid ";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 ";
			qry += "\n     and not exists (select 1 from allotinh a1 where a.Accid=a1.Accid and a.noteno=a1.noteno1 and a1.statetag=1)"; // 已经对应调入单的单据不再显示
			// qry += "\n and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
			if (Houseid > 0)
				qry += " and a.Houseid=" + Houseid;
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				dcztamt = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
				dcztnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			}
			responsestr += ",\"DCZTAMT\":\"" + drztamt + "\"" + ",\"DCZTNUM\":\"" + drztnum + "\"";// 调入在途
			// 未发配货数
			qry = "select nvl(sum(b.amount),0) as amount,count(distinct a.noteno) as num";
			qry += "\n     from warepeih a join warepeim b on a.Accid=b.Accid and a.noteno=b.noteno";
			qry += "\n     where a.Accid=" + Accid + " and a.statetag<5 ";
			if (Houseid > 0)
				qry += " and a.Houseid=" + Houseid;
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				wfphamt = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
				wfphnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			}
			responsestr += ",\"WFPHAMT\":\"" + wfphamt + "\"" // 未发配货数量
					+ ",\"WFPHNUM\":\"" + wfphnum + "\"";// 未发配货单数
			// 负数库存款数，数量
			// qry = " select skc from (";
			qry = "  select count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			qry += "  from v_cxkczy_data a";
			// qry += " left outer join warecode b on a.wareid=b.wareid";
			// qry += " left outer join waretype c on b.typeid=c.typeid";
			qry += "  where a.epid=" + Userid;
			qry += "  and a.amount<0 ";
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				errskc = Integer.parseInt(tb.getRow(0).get("SKC").toString());
			}
			responsestr += ",\"ERRSKC\":\"" + errskc + "\""; // 导常库存款数

		}
		errmess = responsestr;
		// return 1;
	}

	// 我的首页角色功能统计
	public Table GetTablexs() {
		// Levelid: 0=系统管理员,1=店员,2=店长,3=财务,4=经理,5=老板,6=督导,7=收银员,8=AD客服,9=库管
		// dateid:0=本日，1=昨日，2=本周，3=本月
		//System.out.println("doTotalFirstpage 0:" + Currdatetimestr);
		String maxdate = "";
		String mindate = "";
		// String mindate0 = Func.DateToStr(Func.getDataAdd(Nowday, -8));// nowday.AddDays(-7).toString("yyyy-MM-dd"); // 近8天
		// String maxdate1 = Func.DateToStr(Func.getDataAdd(Nowday, 1));// nowday.AddDays(1).toString("yyyy-MM-dd");
		if (Dateid == 0) // 本日
		{
			maxdate = Func.DateToStr(Nowday);// nowday.toString("yyyy-MM-dd");
			mindate = Func.DateToStr(Nowday);
		} else if (Dateid == 1) // 昨日
		{
			maxdate = Func.DateToStr(Func.getDataAdd(Nowday, -1));// nowday.AddDays(-1).toString("yyyy-MM-dd");
			mindate = Func.DateToStr(Func.getDataAdd(Nowday, -1));// nowday.AddDays(-1).toString("yyyy-MM-dd");
		} else if (Dateid == 2) // 本周
		{
			maxdate = Func.DateToStr(Nowday);// nowday.toString("yyyy-MM-dd");
			mindate = Func.DateToStr(Func.getFirstDayOfWeek(Nowday));
			// maxdate = nowday.toString("yyyy-MM-dd");
			// int weeknow = Convert.ToInt32(nowday.DayOfWeek);
			// //星期日 获取weeknow为0
			// weeknow = weeknow == 0 ? 7 : weeknow;
			// int daydiff = (-1) * weeknow + 1;
			// mindate = nowday.AddDays(daydiff).toString("yyyy-MM-dd"); //本周第一天
		} else if (Dateid == 3) // 本月
		{
			maxdate = Func.DateToStr(Nowday);// nowday.toString("yyyy-MM-dd");
			mindate = Func.DateToStr(Func.getFirstDayOfMonth(Nowday));
			// maxdate = nowday.toString("yyyy-MM-dd");
			// mindate = nowday.AddDays(-(nowday.Day) + 1).toString("yyyy-MM-dd");//本月第一天
		}

		// String responsestr = "\"DATERANGE\":\"" + mindate + " 至 " + maxdate + "\"";

		//System.out.println("doTotalFirstpage 1: " + responsestr);
		// String maxdate0 = maxdate;

		// vTotalCxkczy dal = new vTotalCxkczy();
		// dal.setUserid(Userid);
		// dal.setAccid(Accid);
		// dal.setServerdatetime(Currdatetimestr);

		// maxdate = DateTime.Parse(maxdate).AddDays(1).toString("yyyy-MM-dd");
		maxdate = Func.DateToStr(Func.getNextDay(maxdate));
		String qry = "";
		// Float xsamt = (float) 0; // 销售数量
		// Float xscurr = (float) 0; // 销售金额
		// Float xsprice = (float) 0; // 销售平均价
		// Float retailcurr = (float) 0; // 原价金额
		// Integer xsnum = 0; // 销售单数
		// Integer xsskc = 0; // 销售skc
		// Float discount = (float) 0; // 平均销售折扣
		// Table tb = new Table();

		// 销售数量，金额，单数，折扣
		qry = " select xsfs,nvl(sum(amount),0) as amount,nvl(sum(curr),0) as curr,nvl(sum(retailcurr),0) as retailcurr, nvl(sum(cbcurr),0) as cbcurr";
		qry += "\n  ,count(distinct noteno) as num,count(distinct custid) as khnum,count(distinct '^'||wareid||'^'||colorid||'^' ) as skc from (";

		qry += "\n     select a.custid,a.noteno,b.wareid,b.colorid,0 as xsfs,sum(b.amount ) as amount";
		qry += "\n     ,sum(b.curr) as curr";
		qry += "\n     ,sum(b.amount*d.retailsale) as retailcurr";
		qry += "\n     ,sum(b.amount*c.costprice) as cbcurr";
		qry += "\n     from wareouth a join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno";
		qry += "\n     left outer join warecost c on b.wareid=c.wareid and c.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
		if (Housecostbj == 1)
			qry += "  and a.Houseid=c.Houseid";
		else
			qry += "  and c.Houseid=0";
		qry += "\n     left outer join warecode d on b.wareid=d.wareid ";
		qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 and (a.ntid=0 or a.ntid=1) and a.cleartag<2 ";
		qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
		qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
		// if (qxbj == 1) //1 启用权限控制
		// {
		// qry += "\n and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
		// qry += "\n and (d.brandid=0 or exists (select 1 from employebrand x2 where d.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
		// }
		qry += "\n     group by a.custid,a.noteno,b.wareid,b.colorid";

		// 商场零售
		qry += "\n     union all";
		qry += "\n     select a.custid,a.noteno,b.wareid,b.colorid,0 as xsfs,sum(b.amount) as amount,sum(b.curr) as curr";
		qry += "\n     ,sum(b.amount*d.retailsale) as retailcurr,sum(b.amount*c.costprice) as cbcurr";
		qry += "\n     from shopsaleh a join shopsalem b on a.Accid=b.Accid and a.noteno=b.noteno";
		qry += "\n     left outer join warecode d on b.wareid=d.wareid ";
		qry += "\n     left outer join warecost c on b.wareid=c.wareid and c.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
		if (Housecostbj == 1)
			qry += "  and a.Houseid=c.Houseid";
		else
			qry += "  and c.Houseid=0";
		qry += "\n     where a.Accid=" + Accid + " and a.statetag=1";
		qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
		qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
		// if (qxbj == 1) //1 启用权限控制
		// {
		// qry += "\n and exists (select 1 from employehouse x1 where b.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
		// qry += "\n and (d.brandid=0 or exists (select 1 from employebrand x2 where d.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
		// }
		qry += "\n     group by a.custid,a.noteno,b.wareid,b.colorid ";

		// 批发退单
		qry += "\n     union all";
		qry += "\n     select a.custid,a.noteno,b.wareid,b.colorid,1 as xsfs,sum(b.amount) as amount";
		qry += "\n     ,sum(b.curr) as curr";
		qry += "\n     ,sum(b.amount*d.retailsale) as retailcurr";
		qry += "\n     ,sum(b.amount*c.costprice) as cbcurr";
		qry += "\n     from wareouth a join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno";
		qry += "\n     left outer join warecost c on b.wareid=c.wareid and c.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
		if (Housecostbj == 1)
			qry += "  and a.Houseid=c.Houseid";
		else
			qry += "  and c.Houseid=0";
		qry += "\n     left outer join warecode d on b.wareid=d.wareid ";
		qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 and a.ntid=2 and a.cleartag<2 ";
		qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
		qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
		// if (qxbj == 1) //1 启用权限控制
		// {
		// qry += "\n and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
		// qry += "\n and (d.brandid=0 or exists (select 1 from employebrand x2 where d.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
		// }
		qry += "\n     group by a.custid,a.noteno,b.wareid,b.colorid";

		// 冲单
		qry += "\n     union all";
		qry += "\n     select a.custid,a.noteno,b.wareid,b.colorid,2 as xsfs,sum(case a.ntid when 2 then b.amount else -b.amount end) as amount";
		qry += "\n     ,sum(case a.ntid when 2 then b.curr else -b.curr end) as curr";
		qry += "\n     ,sum(case a.ntid when 2 then b.amount else -b.amount end*d.retailsale) as retailcurr";
		qry += "\n     ,sum(case a.ntid when 2 then b.amount else -b.amount end*c.costprice) as cbcurr";
		qry += "\n     from wareouth a join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno";
		qry += "\n     left outer join warecost c on b.wareid=c.wareid and c.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
		if (Housecostbj == 1)
			qry += "  and a.Houseid=c.Houseid";
		else
			qry += "  and c.Houseid=0";
		qry += "\n     left outer join warecode d on b.wareid=d.wareid ";
		qry += "\n     where a.Accid=" + Accid + " and a.statetag=1 and a.cleartag=2 ";
		qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
		qry += "\n     and a.notedate<=to_date('" + Currdatetimestr + "','yyyy-mm-dd hh24:mi:ss')";
		// if (qxbj == 1) //1 启用权限控制
		// {
		// qry += "\n and exists (select 1 from employehouse x1 where a.Houseid=x1.Houseid and x1.epid=" + Userid + " ) ";
		// qry += "\n and (d.brandid=0 or exists (select 1 from employebrand x2 where d.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
		// }
		qry += "\n     group by a.custid,a.noteno,b.wareid,b.colorid";
		qry += "\n      ) group by xsfs";
		//System.out.println(qry);
		// WriteLogTXT("debug", "totalfirstpage1", qry);
		// tb = DbHelperSQL.Query(qry).getTable(1);
		return DbHelperSQL.GetTable(qry);

		// for (int i = 0; i < tb.getRowCount(); i++) {
		// Float xsamt = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
		// Float xscurr = Float.parseFloat(tb.getRow(0).get("CURR").toString());
		// Float retailcurr = Float.parseFloat(tb.getRow(0).get("RETAILCURR").toString());
		// Integer xsnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
		// Integer xsskc = Integer.parseInt(tb.getRow(0).get("SKC").toString());
		// Float xsprice = (float) 0; // 销售平均价
		// Float discount = (float) 0; // 平均销售折扣
		// responsestr += ",\"XSFS\":\"" + tb.getRow(0).get("XS").toString() + "\""//
		// + ",\"XSAMT\":\"" + xsamt.toString() + "\""//
		// + ",\"XSCURR\":\"" + xscurr.toString() + "\"" //
		// + ",\"RETAILCURR\":\"" + retailcurr.toString() + "\"" //
		// + ",\"XSNUM\":\"" + xsnum.toString() + "\"" //
		// + ",\"XSSKC\":\"" + xsskc.toString() + "\"";
		// if (retailcurr != 0)
		// discount = Func.getRound(xscurr / retailcurr, 2);// Math.Round(xscurr / retailcurr, 2, MidpointRounding.AwayFromZero);
		// responsestr += ",\"DISCOUNT\":\"" + discount.toString() + "\"";
		// // if (xsamt != 0) xsprice = Math.Round(xscurr / xsamt, 2, MidpointRounding.AwayFromZero);
		// if (xsamt != 0)
		// xsprice = Func.getRound(xscurr / xsamt, 2);// Math.Round(xscurr / xsamt, 2, MidpointRounding.AwayFromZero);
		//
		// responsestr += ",\"XSPRICE\":\"" + xsprice.toString() + "\"";
		// }
		//
		// errmess = responsestr;
		// // return 1;
	}

	// 获取线上店铺首页信息
	public void doGetOnlinepage() {
		String maxdate = "";
		String mindate = "";
		// String mindate0 = Func.DateToStr(Func.getDataAdd(Nowday, -8));// nowday.AddDays(-7).toString("yyyy-MM-dd"); // 近8天
		// String maxdate1 = Func.DateToStr(Func.getDataAdd(Nowday, 1));// nowday.AddDays(1).toString("yyyy-MM-dd");
		if (Dateid == 0) // 本日
		{
			maxdate = Func.DateToStr(Nowday);// nowday.toString("yyyy-MM-dd");
			mindate = Func.DateToStr(Nowday);
		} else if (Dateid == 1) // 昨日
		{
			maxdate = Func.DateToStr(Func.getDataAdd(Nowday, -1));// nowday.AddDays(-1).toString("yyyy-MM-dd");
			mindate = Func.DateToStr(Func.getDataAdd(Nowday, -1));// nowday.AddDays(-1).toString("yyyy-MM-dd");
		} else if (Dateid == 2) // 本周
		{
			maxdate = Func.DateToStr(Nowday);// nowday.toString("yyyy-MM-dd");
			mindate = Func.DateToStr(Func.getFirstDayOfWeek(Nowday));
		} else if (Dateid == 3) // 本月
		{
			maxdate = Func.DateToStr(Nowday);// nowday.toString("yyyy-MM-dd");
			mindate = Func.DateToStr(Func.getFirstDayOfMonth(Nowday));
		}
		// maxdate = DateTime.Parse(maxdate).AddDays(1).ToString("yyyy-MM-dd");
		maxdate = Func.DateToStr(Func.getNextDay(maxdate));

		String responsestr = "\"msg\":\"操作成功！\",\"DATERANGE\":\"" + mindate + " 至 " + maxdate + "\"";

		Float xsamt = (float) 0;
		Float xscurr = (float) 0;
		Float retailcurr = (float) 0;
		Integer xsnum = 0;
		Integer xsskc = 0;
		// 销售数量，金额，单数，折扣
		String qry = " select nvl(sum(amount),0) as amount,nvl(sum(curr),0) as curr,nvl(sum(retailcurr),0) as retailcurr, nvl(sum(cbcurr),0) as cbcurr";
		qry += "\n  ,count(distinct noteno) as num,count(distinct '^'||wareid||'^'||colorid||'^' ) as skc from (";
		qry += "\n     select a.noteno,b.wareid,b.colorid,sum(case a.ntid when 2 then -b.amount else b.amount end) as amount";
		qry += "\n     ,sum(case a.ntid when 2 then -b.curr else b.curr end) as curr";
		qry += "\n     ,sum((case a.ntid when 2 then -b.amount else b.amount end) *d.retailsale) as retailcurr";
		qry += "\n     ,sum((case a.ntid when 2 then -b.amount else b.amount end) *c.costprice) as cbcurr";
		qry += "\n     from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n     left outer join warecost c on b.wareid=c.wareid and c.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
		qry += "\n     left outer join warecode d on b.wareid=d.wareid ";
		qry += "\n     where a.accid=" + Accid + " and a.statetag=1 and a.onhouseid= " + Houseid; // 线上销售
		qry += "\n     and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd') and a.notedate<to_date('" + maxdate + "','yyyy-mm-dd')";
		qry += "\n     group by a.noteno,b.wareid,b.colorid";
		qry += "\n      )";
		// WriteLogTXT("debug", dateid.ToString(), qry);
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			xsamt = Float.parseFloat(tb.getRow(0).get("AMOUNT").toString());
			xscurr = Float.parseFloat(tb.getRow(0).get("CURR").toString());
			retailcurr = Float.parseFloat(tb.getRow(0).get("RETAILCURR").toString());
			xsnum = Integer.parseInt(tb.getRow(0).get("NUM").toString());
			xsskc = Integer.parseInt(tb.getRow(0).get("SKC").toString());
		}
		responsestr += ",\"XSAMT\":\"" + xsamt + "\"" //
				+ ",\"XSCURR\":\"" + xscurr + "\""//
				+ ",\"RETAILCURR\":\"" + retailcurr + "\"" //
				+ ",\"XSNUM\":\"" + xsnum + "\"" //
				+ ",\"XSSKC\":\"" + xsskc + "\"";
		// 统计线上店铺浏览量
		qry = " select count(*) as num from onlineview where onhouseid=" + Houseid;
		qry += "  and LOGINDATE>=to_date('" + mindate + "','yyyy-mm-dd') and LOGINDATE<to_date('" + maxdate + "','yyyy-mm-dd')";
		// qry = "select pageview,agreeview from onlinehouse where onhouseid=" + onhouseid;
		tb = DbHelperSQL.Query(qry).getTable(1);
		int pageview = 0;
		if (tb.getRowCount() > 0) {
			pageview = Integer.parseInt(tb.getRow(0).get("NUM").toString());

		}
		responsestr += ",\"PAGEVIEW\":\"" + pageview + "\"";
		errmess = responsestr;
		// 更新最新登录时间
		qry = "update onlinehouse set lastdate=sysdate,PAGEVIEW=PAGEVIEW+1 where onhouseid=" + Houseid;
		DbHelperSQL.ExecuteSql(qry);
	}

}
