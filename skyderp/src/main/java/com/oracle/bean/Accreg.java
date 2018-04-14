package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.Func;
import com.common.tool.PinYin;
import com.flyang.yht.SilverPassHttpsUtils;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

// *************************************
// @author:sunhong @date:2017-02-19 12:52:00
// *************************************
public class Accreg implements java.io.Serializable {
	private Long Accid;
	private String Accname;
	private String Regdate;
	private String Mobile;
	private String Company;
	private String Linkman;
	private String Idno;
	private String Address;
	private String Email;
	private String Tel;
	private String Passkey;
	private String Password;
	private Integer Noused;
	private Float Balcurr;
	private Integer Tag;
	private Long Areaid;
	private Date Lastdate;
	private Long Sizenum;
	private String Begindate;
	private String Calcdate;
	private Integer Succtag;
	private Integer Lytag;
	private Integer Qybj;
	private String Qxcs;
	private String UQxcs;
	private Long Usernum;
	private String Validdate;
	private Long Handmanid;  //业务员
	private Long Epid;       //推广人
	private String Smsuid;
	private String Smsuser;
	private String Epidstr;
	private String Smspwd = "******";
	private Integer Md5 = 0;

	private String Dksurname;
	private String Dkidno;
	private String Dkmobile;
	private Integer Dkok;

	private Integer Statetag;
	private String errmess;
	private String Nowdatetime;

	public void setNowdatetime(String nowdatetime) {
		Nowdatetime = nowdatetime;
	}

	public void setEpidstr(String epidstr) {
		Epidstr = epidstr;
	}

	public void setSmspwd(String smspwd) {
		Smspwd = smspwd;
	}

	public void setMd5(int md5) {
		Md5 = md5;
	}

	public void setQybj(int qybj) {
		Qybj = qybj;
	}

	public void setDkok(int dkok) {
		Dkok = dkok;
	}

	public void setDksurname(String dksurname) {
		Dksurname = dksurname;
	}

	public void setDkidno(String dkidno) {
		Dkidno = dkidno;
	}

	public void setDkmobile(String dkmobile) {
		Dkmobile = dkmobile;
	}

	// string epidstr = dataObj["epidstr"] == null ? "" :
	// dataObj["epidstr").toString()();
	// string smspwd = dataObj["smspwd"] == null ? "" :
	// dataObj["smspwd").toString()();
	// int md5 = dataObj["md5"] == null ? 0 :
	// int.Parse(dataObj["md5").toString()());
	// string accname = dataObj["accname"] == null ? "" :
	// dataObj["accname").toString()().ToUpper();
	// string mobile = dataObj["mobile"] == null ? "" :
	// dataObj["mobile").toString()();
	// string company = dataObj["company"] == null ? "" :
	// dataObj["company").toString()();
	// string password = dataObj["password"] == null ? "" :
	// dataObj["password").toString()();
	// int lytag = dataObj["lytag"] == null ? 0 :
	// int.Parse(dataObj["lytag").toString()());

	public String getErrmess() {
		return errmess;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setAccname(String accname) {
		this.Accname = accname;
	}

	public String getAccname() {
		return Accname;
	}

	public void setRegdate(String regdate) {
		this.Regdate = regdate;
	}

	public String getRegdate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Regdate);
	}

	public void setMobile(String mobile) {
		this.Mobile = mobile;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setCompany(String company) {
		this.Company = company;
	}

	public String getCompany() {
		return Company;
	}

	public void setLinkman(String linkman) {
		this.Linkman = linkman;
	}

	public String getLinkman() {
		return Linkman;
	}

	public void setAddress(String address) {
		this.Address = address;
	}

	public String getAddress() {
		return Address;
	}

	public void setEmail(String email) {
		this.Email = email;
	}

	public String getEmail() {
		return Email;
	}

	public void setTel(String tel) {
		this.Tel = tel;
	}

	public String getTel() {
		return Tel;
	}

	public void setPasskey(String passkey) {
		this.Passkey = passkey;
	}

	public String getPasskey() {
		return Passkey;
	}

	public void setPassword(String password) {
		this.Password = password;
	}

	public String getPassword() {
		return Password;
	}

	public void setNoused(Integer noused) {
		this.Noused = noused;
	}

	public Integer getNoused() {
		return Noused;
	}

	public void setBalcurr(Float balcurr) {
		this.Balcurr = balcurr;
	}

	public Float getBalcurr() {
		return Balcurr;
	}

	public void setTag(Integer tag) {
		this.Tag = tag;
	}

	public Integer getTag() {
		return Tag;
	}

	public void setAreaid(Long areaid) {
		this.Areaid = areaid;
	}

	public Long getAreaid() {
		return Areaid;
	}

	public void setLastdate(Date lastdate) {
		this.Lastdate = lastdate;
	}

	public String getLastdate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Lastdate);
	}

	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public Long getEpid() {
		return Epid;
	}

	public void setSizenum(Long sizenum) {
		this.Sizenum = sizenum;
	}

	public Long getSizenum() {
		return Sizenum;
	}

	public void setBegindate(String begindate) {
		this.Begindate = begindate;
	}

	public String getBegindate() {
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// return df.format(Begindate);
		return Begindate;
	}

	public void setSucctag(Integer succtag) {
		this.Succtag = succtag;
	}

	public Integer getSucctag() {
		return Succtag;
	}

	public void setLytag(Integer lytag) {
		this.Lytag = lytag;
	}

	public Integer getLytag() {
		return Lytag;
	}

	public void setQxcs(String qxcs) {
		this.Qxcs = qxcs;
	}

	public String getQxcs() {
		return Qxcs;
	}

	public void setUsernum(Long usernum) {
		this.Usernum = usernum;
	}

	public Long getUsernum() {
		return Usernum;
	}

	public void setValiddate(String validdate) {
		this.Validdate = validdate;
	}

	public String getValiddate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Validdate);
	}

	public void setHandmanid(Long handmanid) {
		this.Handmanid = handmanid;
	}

	public Long getHandmanid() {
		return Handmanid;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	// 删除记录
	public int Delete() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("delete from accreg where ...");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 增加记录
	public int Append(int issxy) {
		// System.out.println("accreg.add 1");

		if (Epidstr == null || Epidstr.equals("")) {
			Epidstr = "0";
		} else {
			if (!Func.isNumber(Epidstr))
				Epidstr = Func.Base64Decode(Epidstr);
		}
		long Epid = Long.parseLong(Epidstr);
		String qry;
		// System.out.println("accreg.add 1.1");

		if (Epid != 0) // 判断推广人账户是否有效
		{
			qry = "select count(*) as num from employe where epid=" + Epid;
			if (DbHelperSQL.GetSingleCount(qry) == 0) {
				Epid = 0;
			}
		}
		// model.epid = epid;
		if (Accname == null || Accname.equals("")) {
			errmess = "注册账号必须输入！";
			return 0;
		}

		if (!Func.isNumAndEnCh(Accname)) {
			errmess = "注册账号只能由字母或数字组成！";
			return 0;

		}
		Accname = Accname.toUpperCase();
		if (Accname.length() < 6) {
			errmess = "注册账号必须大于6个字符！";
			return 0;
		}
		// System.out.println("accreg.add 1.2");
		// string mobile = model.mobile;
		if (Mobile == null || Mobile.equals("")) {
			errmess = "移动电话必须输入！";
			return 0;
		}
		// string mobile = model.mobile;
		// if (model.mobile.Length != 11)
		if (!Func.isMobile(Mobile)) {
			errmess = "移动电话不是一个有效的格式！";
			return 0;
		}
		// System.out.println("accreg.add 1.3");

		if (!Smspwd.equals("******")) // ******表示不判断校验码直接注册
		{
			// qry = "select smspwd from smsrecord where mobile='" +
			// model.mobile + "' and floor(to_number(sysdate-senddate)*24*60)<=5
			// and rownum=1 order by senddate desc";
			qry = "select smspwd from (select smspwd from smsrecord where mobile='" + Mobile + "' and floor(to_number(sysdate-senddate)*24*60)<=5 order by senddate desc )  where rownum=1";
			String smspwd1 = DbHelperSQL.RunSql(qry).toString();
			if (smspwd1.compareTo(Smspwd) != 0 || Smspwd == null || Smspwd.equals("")) {
				// WriteResult("0", "短信校验码无效，请重新输入！?" + smspwd1 + " = " +
				// smspwd);
				errmess = "短信校验码无效，请重新输入！";
				return 0;
			}
		}
		// string ss = reg.Exists(accid, accname, mobile);
		qry = "select count(*) as num  from accreg where  accname='" + Accname + "' and rownum=1";
		if (DbHelperSQL.GetSingleCount(qry) > 0) {
			errmess = "账户名称:" + Accname + " 已存在，请重新输入！";
			return 0;
		}
		qry = "select count(*) as num  from accreg where  mobile='" + Mobile + "' and rownum=1";
		if (DbHelperSQL.GetSingleCount(qry) > 0) {
			errmess = "移动电话:" + Mobile + " 已绑定账户，请换个号码重新输入！";
			return 0;
		}
		if (Qybj == null)
			if (issxy == 1)
				Qybj = 2;
			else
				Qybj = 0;

		if (Lytag == null)// lytag:0=充值,1=买断
			Lytag = 0;
		// if (Qybj == 1 || Qybj == 2)// qybj: 0=枫杨，1=原创，2=上下衣 原创和上下衣都是买断
		// Lytag = 1;
		// System.out.println("accreg.add 1.4");
		// 默认密码:123456
		if (Password == null || Password.equals("")) {
			// md5(FlyAng密码@2015)
			Password = "6BAD136AEF6902EB0D3C0E7666DD2DB7";// "E10ADC3949BA59ABBE56E057F20F883E";
															// //123456
															// model.password =
															// System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile('123456',
															// "MD5");
		} else if (Md5 == 1) // 密码未加密
		{
			// :hex_md5("FlyAng"+password+"@2015")
			String password1 = "FlyAng" + Password.toUpperCase() + "@2015";
			Password = Func.StrtoMD5(password1);
		}
		// System.out.println("accreg.add 2");
		if (Company == null)
			Company = "";
		Password = Password.toUpperCase();
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n    v_accname varchar2(16); "); // --注册账号";
		strSql.append("\n    v_company varchar2(50); "); // --密码";
		strSql.append("\n    v_password varchar2(50); "); // --密码";
		strSql.append("\n    v_mobile  varchar2(11);  "); // --移动电话";
		strSql.append("\n    v_epid  varchar2(16);  "); // --推荐人用户id";
		strSql.append("\n    v_areaid  number;  "); // --所属区域";
		strSql.append("\n    v_accid  number ; "); // --返回注册账号";
		strSql.append("\n    v_SENDVALUE number(10):= 5; "); // --推荐用户成功赠送5个枫杨果";
		strSql.append("\n    v_SENDVALUE1 number(10):= 10;"); // --新用户注册赠送枫杨果";
		strSql.append("\n    v_paccid number(10);");
		strSql.append("\n    v_sysdate  date;  "); // --推荐人用户id";
		strSql.append("\n begin");
		strSql.append("\n    v_accname:='" + Accname + "'; ");
		strSql.append("\n    v_password:='" + Password + "'; ");
		strSql.append("\n    v_mobile:='" + Mobile + "'; ");
		strSql.append("\n    v_company:='" + Company + "'; ");
		strSql.append("\n    v_epid:=" + Epid + "; ");
		strSql.append("\n    v_areaid:=0; ");
		strSql.append("\n    v_accid:=accreg_accid.nextval;");
		strSql.append("\n    v_sysdate:=sysdate;");
		// qry+=" --增加注册账号

		// qry += "\n insert into accreg
		// (accid,accname,mobile,password,areaid,epid,company,linkman,address,email,tel,passkey,noused,balcurr,tag,regdate,begindate,lastdate,sizenum,lytag,usernum,validdate,qxcs,uqxcs)";
		// qry += "\n values (v_accid,upper('" + model.accname + "'),'" +
		// model.mobile + "','" + model.password + "',v_areaid," + model.epid +
		// ",v_company,'','','','','',0,v_SENDVALUE1,2,v_sysdate,v_sysdate,v_sysdate,8,2,1,to_date('2017-08-31','yyyy-mm-dd'),'0000000000','000200000000000000000000000000');";
		// lytag:0=充值,1=买断

		// qybj: 0=枫杨，1=原创，2=上下衣
		if (issxy == 1) {
			Lytag = 1;
			strSql.append(
					"\n    insert into accreg (accid,accname,mobile,password,areaid,epid,company,linkman,address,email,tel,passkey,noused,balcurr,tag,regdate,begindate,lastdate,sizenum,lytag,qxcs,uqxcs,usernum,validdate,qybj)");
			strSql.append("\n    values (v_accid,'" + Accname + "','" + Mobile + "','" + Password + "',v_areaid," + Epid + ",v_company,'','','','','',0");
			strSql.append(",v_SENDVALUE1,1,v_sysdate,trunc(v_sysdate),v_sysdate,8," + Lytag + ",'0000000000','000200000000000000000200000000',3,to_date('2017-08-31','yyyy-mm-dd'),2);");
		} else {  // qybj: 0=枫杨，1=原创，2=上下衣 原创和上下衣都是买断
			if (Qybj == 0) {
				Lytag = 0;
				strSql.append(
						"\n    insert into accreg (accid,accname,mobile,password,areaid,epid,company,linkman,address,email,tel,passkey,noused,balcurr,tag,regdate,begindate,lastdate,sizenum,lytag,qxcs,uqxcs,usernum,qybj)");
				strSql.append("\n    values (v_accid,'" + Accname + "','" + Mobile + "','" + Password + "',v_areaid," + Epid + ",v_company,'','','','','',0");
				strSql.append(",v_SENDVALUE1,1,v_sysdate,trunc(v_sysdate),v_sysdate,8," + Lytag + ",'0000000000','000200000000000000000200000000',3," + Qybj + ");");
			} else {// 原创 lytag:0=充值,1=买断
				Lytag = 1;
				strSql.append(
						"\n    insert into accreg (accid,accname,mobile,password,areaid,epid,company,linkman,address,email,tel,passkey,noused,balcurr,tag,regdate,begindate,lastdate,sizenum,lytag,qxcs,uqxcs,usernum,validdate,qybj)");
				strSql.append("\n    values (v_accid,'" + Accname + "','" + Mobile + "','" + Password + "',v_areaid," + Epid + ",v_company,'','','','','',0");
				strSql.append(",v_SENDVALUE1,1,v_sysdate,trunc(v_sysdate),v_sysdate,8," + Lytag + ",'0000000000','000200000000000000000200000000',6,v_sysdate+10," + Qybj + ");");
			}
		}
		strSql.append("\n    insert into accmoney (id,accid,notedate,fs,curr,paycurr,remark,lastdate,lastop,handno)");
		strSql.append("\n    values (accmoney_id.nextval,v_accid,sysdate,0,v_SENDVALUE1,0,'您已成功注册，系统赠送'||v_SENDVALUE1||'个枫杨果。',sysdate,'sysdba','');");

		// qry+=" --自动增加admin用户
		strSql.append("\n    insert into employe (epid,accid,epno,epname,sex,password,lastop,statetag,levelid,passkey,workdate) ");
		strSql.append("\n    values (employe_epid.nextval,v_accid,'ADMIN','系统管理员','男','" + Password + "' ,'sysdba',1,0,'1',sysdate);");
		// qry+=" --增加固定的结算方式:0=现金
		strSql.append("\n    insert into payway (payid,payno,payname,accid,statetag,noused,lastop,payaccount) values (payway_payid.nextval,'0','现金',v_accid,1,0,'sysdba','');");
		// strSql.append( "\n insert into payway
		// (payid,payno,payname,accid,statetag,noused,lastop,payaccount) values
		// (payway_payid.nextval,'1','银行刷卡',v_accid,1,0,'sysdba','');";
		strSql.append("\n    insert into payway (payid,payno,payname,accid,statetag,noused,lastop,payaccount) values (payway_payid.nextval,'V','*储值卡*',v_accid,1,1,'sysdba','');");
		// strSql.append("\n insert into payway
		// (payid,payno,payname,accid,statetag,noused,lastop,payaccount) values
		// (payway_payid.nextval,'Z','*支付宝*',v_accid,2,1,'sysdba','');");
		// strSql.append("\n insert into payway
		// (payid,payno,payname,accid,statetag,noused,lastop,payaccount) values
		// (payway_payid.nextval,'W','*微信*',v_accid,2,1,'sysdba','');");
		// --增加默认的尺码类型
		strSql.append("\n    Insert into SIZECODE (SIZEID,SIZENAME,GROUPNO,SIZENO,ACCID,NOUSED,STATETAG,LASTOP,LYFS,ACCID1,SIZEID1) values (sizecode_sizeid.nextval,'XS','标准','01',v_accid,0,1,'sysdba',0,0,0);");
		strSql.append("\n    Insert into SIZECODE (SIZEID,SIZENAME,GROUPNO,SIZENO,ACCID,NOUSED,STATETAG,LASTOP,LYFS,ACCID1,SIZEID1) values (sizecode_sizeid.nextval,'S','标准','02',v_accid,0,1,'sysdba',0,0,0);");
		strSql.append("\n    Insert into SIZECODE (SIZEID,SIZENAME,GROUPNO,SIZENO,ACCID,NOUSED,STATETAG,LASTOP,LYFS,ACCID1,SIZEID1) values (sizecode_sizeid.nextval,'M','标准','03',v_accid,0,1,'sysdba',0,0,0);");
		strSql.append("\n    Insert into SIZECODE (SIZEID,SIZENAME,GROUPNO,SIZENO,ACCID,NOUSED,STATETAG,LASTOP,LYFS,ACCID1,SIZEID1) values (sizecode_sizeid.nextval,'L','标准','04',v_accid,0,1,'sysdba',0,0,0);");
		strSql.append("\n    Insert into SIZECODE (SIZEID,SIZENAME,GROUPNO,SIZENO,ACCID,NOUSED,STATETAG,LASTOP,LYFS,ACCID1,SIZEID1) values (sizecode_sizeid.nextval,'XL','标准','05',v_accid,0,1,'sysdba',0,0,0);");
		strSql.append("\n    Insert into SIZECODE (SIZEID,SIZENAME,GROUPNO,SIZENO,ACCID,NOUSED,STATETAG,LASTOP,LYFS,ACCID1,SIZEID1) values (sizecode_sizeid.nextval,'XXL','标准','06',v_accid,0,1,'sysdba',0,0,0);");
		strSql.append("\n    Insert into SIZECODE (SIZEID,SIZENAME,GROUPNO,SIZENO,ACCID,NOUSED,STATETAG,LASTOP,LYFS,ACCID1,SIZEID1) values (sizecode_sizeid.nextval,'3XL','标准','07',v_accid,0,1,'sysdba',0,0,0);");
		strSql.append("\n    Insert into SIZECODE (SIZEID,SIZENAME,GROUPNO,SIZENO,ACCID,NOUSED,STATETAG,LASTOP,LYFS,ACCID1,SIZEID1) values (sizecode_sizeid.nextval,'4XL','标准','08',v_accid,0,1,'sysdba',0,0,0);");
		strSql.append("\n    Insert into SIZECODE (SIZEID,SIZENAME,GROUPNO,SIZENO,ACCID,NOUSED,STATETAG,LASTOP,LYFS,ACCID1,SIZEID1) values (sizecode_sizeid.nextval,'F','单品','00',v_accid,0,1,'sysdba',0,0,0);");
		// qry+=" --增加默认的颜色编码
		strSql.append("\n    Insert into COLORCODE (COLORID,COLORNAME,shortname,COLORNO,ACCID,LYFS,ACCID1,COLORID1,NOUSED,STATETAG,LASTOP,LASTDATE)");
		strSql.append("\n    values (COLORCODE_COLORID.NEXTVAL,'无','W','00',v_accid,0,0,0,0,1,'sysdba',sysdate);");
		strSql.append("\n    Insert into COLORCODE (COLORID,COLORNAME,shortname,COLORNO,ACCID,LYFS,ACCID1,COLORID1,NOUSED,STATETAG,LASTOP,LASTDATE)");
		strSql.append("\n    values (COLORCODE_COLORID.NEXTVAL,'红色','HS','01',v_accid,0,0,0,0,1,'sysdba',sysdate);");
		// strSql.append( "\n Insert into COLORCODE
		// (COLORID,COLORNAME,shortname,COLORNO,ACCID,LYFS,ACCID1,COLORID1,NOUSED,STATETAG,LASTOP,LASTDATE)
		// values
		// (COLORCODE_COLORID.NEXTVAL,'黄色','HS','02',v_accid,0,0,0,0,1,'sysdba',sysdate);)";
		// strSql.append( "\n Insert into COLORCODE
		// (COLORID,COLORNAME,shortname,COLORNO,ACCID,LYFS,ACCID1,COLORID1,NOUSED,STATETAG,LASTOP,LASTDATE)
		// values
		// (COLORCODE_COLORID.NEXTVAL,'绿色','LS','03',v_accid,0,0,0,0,1,'sysdba',sysdate);)";
		strSql.append("\n    Insert into COLORCODE (COLORID,COLORNAME,shortname,COLORNO,ACCID,LYFS,ACCID1,COLORID1,NOUSED,STATETAG,LASTOP,LASTDATE) ");
		strSql.append("\n    values (COLORCODE_COLORID.NEXTVAL,'灰色','HS','04',v_accid,0,0,0,0,1,'sysdba',sysdate);");
		strSql.append("\n    Insert into COLORCODE (COLORID,COLORNAME,shortname,COLORNO,ACCID,LYFS,ACCID1,COLORID1,NOUSED,STATETAG,LASTOP,LASTDATE)");
		strSql.append("\n    values (COLORCODE_COLORID.NEXTVAL,'黑色','HS','05',v_accid,0,0,0,0,1,'sysdba',sysdate);");
		// strSql.append( "\n Insert into COLORCODE
		// (COLORID,COLORNAME,shortname,COLORNO,ACCID,LYFS,ACCID1,COLORID1,NOUSED,STATETAG,LASTOP,LASTDATE)
		// values
		// (COLORCODE_COLORID.NEXTVAL,'棕色','ZS','06',v_accid,0,0,0,0,1,'sysdba',sysdate);";
		// strSql.append( "\n Insert into COLORCODE
		// (COLORID,COLORNAME,shortname,COLORNO,ACCID,LYFS,ACCID1,COLORID1,NOUSED,STATETAG,LASTOP,LASTDATE)
		// values
		// (COLORCODE_COLORID.NEXTVAL,'蓝色','LS','07',v_accid,0,0,0,0,1,'sysdba',sysdate);";
		strSql.append("\n    Insert into COLORCODE (COLORID,COLORNAME,shortname,COLORNO,ACCID,LYFS,ACCID1,COLORID1,NOUSED,STATETAG,LASTOP,LASTDATE) ");
		strSql.append("\n    values (COLORCODE_COLORID.NEXTVAL,'白色','BS','08',v_accid,0,0,0,0,1,'sysdba',sysdate);");

		// --增加默认的费用项目
		strSql.append("\n    Insert into CHARGESCODE (CGID,ACCID,CGNAME,shortname,NOUSED,STATETAG,LASTOP,bj) values (CHARGESCODE_cgid.nextval,v_accid,'搬运费','BYF',0,1,'sysdba',1);");
		strSql.append("\n    Insert into CHARGESCODE (CGID,ACCID,CGNAME,shortname,NOUSED,STATETAG,LASTOP,bj) values (CHARGESCODE_cgid.nextval,v_accid,'推广费','TGF',0,1,'sysdba',0);");
		strSql.append("\n    Insert into CHARGESCODE (CGID,ACCID,CGNAME,shortname,NOUSED,STATETAG,LASTOP,bj) values (CHARGESCODE_cgid.nextval,v_accid,'活动费','HDF',0,1,'sysdba',0);");
		// 有推荐人
		if (issxy == 0) {
			strSql.append("\n    if " + Epid + ">0 then");
			strSql.append("\n       select accid into v_paccid from employe where epid=" + Epid + ";");
			strSql.append("\n       insert into accmoney (id,accid,notedate,fs,curr,paycurr,remark,lastdate,lastop,handno)");
			strSql.append("\n       values (accmoney_id.nextval,v_paccid ,sysdate,0,v_SENDVALUE,0,'您成功推荐新用户'||'" + Accname + "'||'注册，系统赠送'||v_SENDVALUE||'个枫杨果。',sysdate,'sysdba','');");
			strSql.append("\n       update accreg set balcurr=(select NVL(sum(curr),0) from accmoney where accreg.accid=accmoney.accid) where accid=v_paccid ;");
			strSql.append("\n    end if;");
		}
		strSql.append("\n    commit;");
		strSql.append("\n    select v_accid into :accid from dual;");
		strSql.append("\n end;");
		//		LogUtils.LogDebugWrite("addaccreg", strSql.toString());
		// System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("accid", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret >= 0) {
			Accid = Long.parseLong(param.get("accid").getParamvalue().toString());
			return 1;
		} else
			return 0;
	}

	// 修改记录
	public int Update() {

		// System.out.println("22222");

		if (Accid == null) {
			errmess = "账户id参数无效！";
			return 0;
		}
		// System.out.println("33333");
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n   v_begindate varchar2(10);");
		strSql.append("\n   v_calcdate varchar2(10);");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n   v_days number;");
		strSql.append("\n   v_count number;");
		strSql.append("\n begin ");

		if (!Func.isNull(Calcdate)) {
			strSql.append("\n   select to_char(calcdate,'yyyy-mm-dd') into v_calcdate from accreg where accid=" + Accid + ";");
			strSql.append("\n   if v_calcdate<'" + Calcdate + "' then ");
			strSql.append("\n      v_retcs:='0登账日期必须小于当前登账日期'||v_calcdate||'！'; ");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
			strSql.append("\n   select to_char(begindate-1,'yyyy-mm-dd'),trunc(calcdate-to_date('" + Calcdate + "','yyyy-mm-dd')) into v_begindate,v_days from accreg where accid=" + Accid + ";");
			strSql.append("\n   if v_begindate>'" + Calcdate + "' then ");
			strSql.append("\n      v_retcs:='0登账日期不能小于开工日期前一天('||v_begindate||')！'; ");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
			strSql.append("\n   if v_days>30 then ");
			strSql.append("\n      v_retcs:='0登账日期不能更改到30天以前的日期！'; ");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");

		}
		if (!Func.isNull(Linkman) && !Func.isNull(Idno)) {
			strSql.append("\n   select count(*) into v_count from accreg where linkman='" + Linkman + "' and idno='" + Idno + "' and accid<>" + Accid + ";");
			strSql.append("\n   if v_count>0 then ");
			strSql.append("\n      v_retcs:='0姓名及身份证号已存在！'; ");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}

		strSql.append("\n   update accreg set ");

		if (Company != null) {
			strSql.append("company='" + Company + "',");
		}
		if (Linkman != null) {
			strSql.append("linkman='" + Linkman + "',");
		}
		if (Idno != null) {
			strSql.append("Idno='" + Idno + "',");
		}
		if (Mobile != null) {
			strSql.append("Mobile='" + Mobile + "',");
		}
		if (Address != null) {
			strSql.append("address='" + Address + "',");
		}
		if (Email != null) {
			strSql.append("email='" + Email + "',");
		}
		if (Tel != null) {
			strSql.append("tel='" + Tel + "',");
		}
		if (Noused != null) {
			strSql.append("noused=" + Noused + ",");
		}
		if (Tag != null) {
			strSql.append("tag=" + Tag + ",");
		}
		if (Areaid != null) {
			strSql.append("areaid=" + Areaid + ",");
		}
		// if (Lastdate != null) {
		// strSql.append("lastdate='" + Lastdate + "',");
		// }
		if (Epid != null) {
			strSql.append("epid=" + Epid + ",");
		}
		if (Succtag != null) {
			strSql.append("succtag=" + Succtag + ",");
		}
		if (Lytag != null) {
			strSql.append("lytag=" + Lytag + ",");
		}
		if (Qxcs != null) {
			strSql.append("qxcs='" + Qxcs + "',");
		}
		if (UQxcs != null) {
			strSql.append("uqxcs='" + UQxcs + "',");
		}
		if (Usernum != null) {
			strSql.append("usernum=" + Usernum + ",");
		}
		if (Validdate != null) {
			strSql.append("validdate=to_date('" + Validdate + "','yyyy-mm-dd'),");
		}
		if (Calcdate != null) {
			strSql.append("Calcdate=to_date('" + Calcdate + "','yyyy-mm-dd'),");
		}
		if (Handmanid != null) {
			strSql.append("handmanid=" + Handmanid + ",");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Smsuid != null) {
			strSql.append("Smsuid='" + Smsuid + "',");
		}
		if (Smsuser != null) {
			strSql.append("Smsuser='" + Smsuser + "',");
		}
		if (Smspwd != null && !Smspwd.equals("******")) {
			strSql.append("Smspwd='" + Smspwd + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where accid=" + Accid + " ;");
		if (Noused != null && Noused == 1) {// 禁用时清除登录标记
			strSql.append("\n   update useronline set ACCESSKEY='' where accid=" + Accid);
			strSql.append("\n   and exists (select 1 from employe where useronline.epid=employe.epid and accid=" + Accid + " and levelid<>0 ); ");
		}
		strSql.append("\n  commit;");
		strSql.append("\n  v_retcs:='1操作成功！';");
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
	public DataSet GetList1(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM accreg a");
		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM accreg a");
		strSql.append("\n left outer join employe b on a.epid=b.epid");
		strSql.append("\n left outer join accreg c on b.accid=c.accid");
		strSql.append("\n left outer join mobilearea d on substr(a.mobile,1,7)=d.mobile");
		strSql.append("\n left outer join employe e on a.handmanid=e.epid");
		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public String GetTable2Excel(QueryParam qp, String strWhere, String fieldlist, JSONObject jsonObject) {
		StringBuilder strSql = new StringBuilder();

		strSql.append("select  " + fieldlist + ",c.accname as accname1,c.company as company1,b.accid as accid1,e.epname as handmanname");
		strSql.append("\n FROM accreg a");
		strSql.append("\n left outer join employe b on a.epid=b.epid");
		strSql.append("\n left outer join accreg c on b.accid=c.accid");
		strSql.append("\n left outer join mobilearea d on substr(a.mobile,1,7)=d.mobile");
		strSql.append("\n left outer join employe e on a.handmanid=e.epid");

		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		qp.setPageIndex(1);
		qp.setPageSize(100);
		return DbHelperSQL.Table2Excel(qp, jsonObject);
	}

	// 统计各省份注册数
	public Table doTotalAccregbyprovince() {

		String qry = "select b.province ,count(*) as num";
		qry += "\n from accreg a ";
		qry += "\n join mobilearea b on substr(a.mobile,1,7)=b.mobile ";
		qry += "\n left outer join employe c on a.epid=c.epid ";
		qry += "\n group by b.province order by num desc";
		return DbHelperSQL.GetTable(qry);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();

		strSql.append("select  " + fieldlist + ",c.accname as accname1,c.company as company1,b.accid as accid1,e.epname as handmanname");
		strSql.append("\n FROM accreg a");
		strSql.append("\n left outer join employe b on a.epid=b.epid");
		strSql.append("\n left outer join accreg c on b.accid=c.accid");
		strSql.append("\n left outer join mobilearea d on substr(a.mobile,1,7)=d.mobile");
		strSql.append("\n left outer join employe e on a.handmanid=e.epid");

		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		// System.out.println(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}
	// 获取分页数据
	public Table GetTable1(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();

		strSql.append("select  " + fieldlist );
		strSql.append("\n FROM accreg a");
		strSql.append("\n left outer join employe b on a.epid=b.epid");
//		strSql.append("\n left outer join accreg c on b.accid=c.accid");
//		strSql.append("\n left outer join mobilearea d on substr(a.mobile,1,7)=d.mobile");
//		strSql.append("\n left outer join employe e on a.handmanid=e.epid");

		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		// System.out.println(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取所有数据
	public Table GetTable(QueryParam qp) {
		//		String qry = "select  (select count(*) from accreg)+3000 as acccount";
		//		//		qry += ",(select count(*) from useronline)+3000 as usercount ";
		//		qry += " from dual ";
		String qry = "select  count(*) as acccount  from accreg";
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		//	System.out.println(tb.getRowCount());
		String totalstr = "\"ACCNUM\":\"" + tb.getRow(0).get("ACCCOUNT").toString() + "\"" //
		//				+ ",\"USERNUM\":\"" + tb.getRow(0).get("USERCOUNT").toString() + "\""//
		;
		qry = "select accname,company,regdate from (select accname,company,regdate from accreg order by regdate desc )  where rownum<=" + qp.getPageSize();
		qp.setTotalString(totalstr);
		qp.setQueryString(qry);
		return DbHelperSQL.GetTable(qp);
	}

	// 获取所有数据
	public Table GetTable(String qry) {

		return DbHelperSQL.GetTable(qry);
	}

	public String doAccregcount1(String nowdate) {
		Table tb = new Table();
		String qry = "select  (select count(*) from accreg a join mobilearea b on substr(a.mobile,1,7)=b.mobile where a.accid>1000) as acccount"; // 注册企业数
		qry += "\n ,(select count(distinct accid) from useronline a where a.accid>1000 and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid) ) as accnum"; // 本日登录企业数
		// qry += "\n ,(select count(*) from useronline a where a.accid>1000 and
		// exists (select 1 from accreg a1 join mobilearea b1 on
		// substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid) ) as
		// usercount ";// 本日登录用户数
		qry += "\n ,(select sum(num) from ("//
				+ " select count(*) as num from useronline a where a.accid>1000"//
				+ "\n and  exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid)"//
				+ "\n union all"//
				+ "\n select count(*) as num from omuseronline a where a.accid>1000"//
				+ "\n and  exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid)) ) as usercount";
		// qry += ",(select count(*) from employe a where a.passkey=1 ) as
		// usersum ";//用户总数
		qry += "\n ,(select count(*) from employe a where a.statetag=1 and a.accid>1000 and a.passkey=1 and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid)) as usersum  ";
		qry += "\n ,(select count(*) from warehouse a where a.statetag=1 and a.accid>1000 and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid)) as housesum  ";

		qry += "\n ,(select count(*) from guestvip a where a.statetag=1 and a.accid>1000 and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid)) as vipsum  "; // B端会员数

		qry += "\n ,(select count(*) from vipgroup a join mobilearea b on substr(a.mobile,1,7)=b.mobile) as cvipsum"; // C端会员数

		// qry += ",(select count(*) from viponline a where ";
		// qry += " a.logindate>=to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		// qry += " and a.logindate<=to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		// qry += " and exists (select 1 from vipgroup a1 join mobilearea b1 on
		// substr(a1.mobile,1,7)=b1.mobile ) ) as convipnum"; //C端今日上线会员数

		qry += "\n ,(select count(*) from v$process) as pnum "; // 进程数
		qry += "\n ,(select count(*) from v$session) as snum"; // 会话数

		qry += "\n ,(select count(*) from v$process p where exists (select 1 from v$session s where p.addr = s.paddr and s.username='FLYANG')) as pnum0 "; // 进程数
		qry += "\n ,(select count(*) from v$session where username='FLYANG') as snum0"; // 会话数
		qry += "\n ,(select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')||to_char(sysdate,'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''') from dual ) as datetimestr";
		qry += "\n  from dual ";
		// System
		// WriteLogTXT("debug", "GetAccregcount1", qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		String responsestr = "{\"result\":1,\"msg\":\"操作成功！\",\"ACCOUNT\": \"" + tb.getRow(0).get("ACCCOUNT").toString() + "\""//
				+ ",\"ACCNUM\":\"" + tb.getRow(0).get("ACCNUM").toString() + "\"" //
				+ ",\"USERSUM\":\"" + tb.getRow(0).get("USERSUM").toString() + "\""//
				+ ",\"USERCOUNT\":\"" + tb.getRow(0).get("USERCOUNT").toString() + "\"" //
				+ ",\"HOUSESUM\":\"" + tb.getRow(0).get("HOUSESUM").toString() + "\"" //
				+ ",\"VIPSUM\":\"" + tb.getRow(0).get("VIPSUM").toString() + "\"" // B端会员数
				+ ",\"CVIPSUM\":\"" + tb.getRow(0).get("CVIPSUM").toString() + "\"" // C端会员数
				+ ",\"PROCESSNUM\":\"" + tb.getRow(0).get("PNUM").toString() + "\"" //
				+ ",\"PROCESSNUM0\":\"" + tb.getRow(0).get("PNUM0").toString() + "\"" //
				+ ",\"SESSIONNUM\":\"" + tb.getRow(0).get("SNUM").toString() + "\"" //
				+ ",\"SESSIONNUM0\":\"" + tb.getRow(0).get("SNUM0").toString() + "\""
				// + ",\"SERVERDATETIME\":\"" +
				// GetServerdatetime().toString("yyyy-MM-dd HH:mm:ss") + "\""
				// + ",\"SERVERDATETIME\":\"" +
				// tb.getRow(0).get("datetimestr").toString()().Replace("星期", "
				// ") + "\""
				+ ",\"SERVERDATETIME\":\"" + tb.getRow(0).get("DATETIMESTR").toString() + "\"";
		// to_char(a.notedate,'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED
		// CHINESE''') as weekstr

		// 充值统计
		qry = "select sum(a.curr) as curr,sum(a.paycurr) as paycurr ";
		qry += " from accmoney a join accreg b on a.accid=b.accid ";
		qry += " join mobilearea c on substr(b.mobile,1,7)=c.mobile ";
		qry += " where a.accid>1000 and a.paycurr>0 and a.fs=0";
		tb = DbHelperSQL.Query(qry).getTable(1);
		// WriteLogTXT("debug", "2", qry);

		responsestr += ",\"FYGBALCURR\":\"" + tb.getRow(0).get("CURR").toString() + "\"" // 充值得枫杨果
				+ ",\"FYGPAYCURR\":\"" + tb.getRow(0).get("PAYCURR").toString() + "\""; // 充值金额

		qry = "  select nvl(sum(xsamount),0) as xsamount,nvl(sum(xscurr),0) as xscurr,nvl(sum(xsnum),0) as xsnum from ( ";
		qry += "  select sum(case a.ntid when 2 then -b.curr else b.curr end ) as xscurr ";
		qry += " ,sum(case a.ntid when 2 then -b.amount else b.amount end ) as xsamount ";
		qry += " ,count(distinct a.accid||'^'||a.noteno) as xsnum ";
		qry += "  from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "  where a.statetag=1 ";
		qry += "  and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a1.accid=a.accid)";
		// qry += " and a.NOTEDATE >= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		// qry += " and a.NOTEDATE <= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		qry += "  and a.notedate >= to_date(to_char(sysdate,'yyyy-mm-dd')||' 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		qry += "  and a.notedate <= to_date(to_char(sysdate,'yyyy-mm-dd')||' 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		// 商场销售
		qry += "  union all";
		qry += "  select sum(b.curr) as xscurr ";
		qry += " ,sum(b.amount) as xsamount ";
		qry += " ,count(distinct a.accid||'^'||a.noteno) as xsnum ";
		qry += "  from shopsaleh a join shopsalem b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "  where a.statetag=1 ";
		qry += "  and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a1.accid=a.accid)";
		qry += "  and a.notedate >= to_date(to_char(sysdate,'yyyy-mm-dd')||' 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		qry += "  and a.notedate <= to_date(to_char(sysdate,'yyyy-mm-dd')||' 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		qry += ")";
		// WriteLogTXT("debug", "3", qry);

		tb = DbHelperSQL.Query(qry).getTable(1);
		responsestr += ",\"XSNUM\": \"" + tb.getRow(0).get("XSNUM").toString() + "\"" + ",\"XSAMOUNT\":\"" + tb.getRow(0).get("XSAMOUNT").toString() + "\"" + ",\"XSCURR\":\"" + tb.getRow(0).get("XSCURR").toString()
				+ "\"";
		// 刷卡金额
		qry = "  select nvl(sum(curr),0) as curr from dealrecord a where a.statetag=1";
		qry += "  and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a1.accid=a.accid)";
		qry += "  and a.notedate >= to_date(to_char(sysdate,'yyyy-mm-dd')||' 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		qry += "  and a.notedate <= to_date(to_char(sysdate,'yyyy-mm-dd')||' 23:59:59','yyyy-mm-dd hh24:mi:ss') ";

		tb = DbHelperSQL.Query(qry).getTable(1);
		responsestr += ",\"SKCURR\": \"" + tb.getRow(0).get("CURR").toString() + "\"";

		qry = " select nvl(sum(case a.ntid when 1 then -b.curr else b.curr end ),0) as cgcurr ";
		qry += " ,nvl(sum(case a.ntid when 1 then -b.amount else b.amount end ),0) as cgamount ";
		qry += " ,count(distinct a.accid||'^'||a.noteno) as cgnum ";
		qry += "  from wareinh a join wareinm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "  where a.statetag=1 ";
		qry += "  and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a1.accid=a.accid)";
		// qry += " and a.NOTEDATE >= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		// qry += " and a.NOTEDATE <= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		qry += "  and a.notedate >= to_date(to_char(sysdate,'yyyy-mm-dd')||' 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		qry += "  and a.notedate <= to_date(to_char(sysdate,'yyyy-mm-dd')||' 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		// WriteLogTXT("debug", "4", qry);

		tb = DbHelperSQL.Query(qry).getTable(1);
		responsestr += ",\"CGNUM\": \"" + tb.getRow(0).get("CGNUM").toString() + "\"" + ",\"CGAMOUNT\":\"" + tb.getRow(0).get("CGAMOUNT").toString() + "\"" + ",\"CGCURR\":\"" + tb.getRow(0).get("CGCURR").toString()
				+ "\"";

		qry = " select uvalue from uparameter where accid=0 and usection='OPTIONS' and USYMBOL='WORKDAY'";
		tb = DbHelperSQL.Query(qry).getTable(1);
		String daystr = tb.getRow(0).get("UVALUE").toString().trim();

		/*
		 * qry =
		 * "select a.daystr,sum(a.amount) as kcamount from waresum a join  uparameter b on a.daystr=b.uvalue "
		 * ; qry +=
		 * "  where b.accid=0 and b.usection='OPTIONS' and b.USYMBOL='WORKDAY'";
		 * qry += " group by a.daystr";
		 */
		qry = "select sum(a.amount) as kcamount from waresum a where a.daystr='" + daystr + "'";
		// WriteLogTXT("debug", "5", qry);

		tb = DbHelperSQL.Query(qry).getTable(1);
		responsestr += ",\"WORKDAY\": \"" + daystr + "\"" + ",\"KCAMOUNT\":\"" + tb.getRow(0).get("KCAMOUNT").toString() + "\"";
		responsestr += "}";
		return responsestr;
	}

	public String doAccregcount1x(String nowdate) {
		Table tb = new Table();
		String qry = "select  (select count(*) from accreg a join mobilearea b on substr(a.mobile,1,7)=b.mobile where a.accid>1000) as acccount"; // 注册企业数
		qry += "\n ,(select count(distinct accid) from useronline a where a.accid>1000 and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid) ) as accnum"; // 本日登录企业数
		// qry += "\n ,(select count(*) from useronline a where a.accid>1000 and
		// exists (select 1 from accreg a1 join mobilearea b1 on
		// substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid) ) as
		// usercount ";// 本日登录用户数
		qry += "\n ,(select sum(num) from ("//
				+ " select count(*) as num from useronline a where a.accid>1000"//
				+ "\n and  exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid)"//
				+ "\n union all"//
				+ "\n select count(*) as num from omuseronline a where a.accid>1000"//
				+ "\n and  exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid)) ) as usercount";
		// qry += ",(select count(*) from employe a where a.passkey=1 ) as
		// usersum ";//用户总数
		qry += "\n ,(select count(*) from employe a where a.statetag=1 and a.accid>1000 and a.passkey=1 and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid)) as usersum  ";
		qry += "\n ,(select count(*) from warehouse a where a.statetag=1 and a.accid>1000 and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid)) as housesum  ";

		qry += "\n ,(select count(*) from guestvip a where a.statetag=1 and a.accid>1000 and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid)) as vipsum  "; // B端会员数

		qry += "\n ,(select count(*) from vipgroup a join mobilearea b on substr(a.mobile,1,7)=b.mobile) as cvipsum"; // C端会员数

		// qry += ",(select count(*) from viponline a where ";
		// qry += " a.logindate>=to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		// qry += " and a.logindate<=to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		// qry += " and exists (select 1 from vipgroup a1 join mobilearea b1 on
		// substr(a1.mobile,1,7)=b1.mobile ) ) as convipnum"; //C端今日上线会员数

		//		qry += "\n ,(select count(*) from v$process) as pnum "; // 进程数
		//		qry += "\n ,(select count(*) from v$session) as snum"; // 会话数
		//
		//		qry += "\n ,(select count(*) from v$process p where exists (select 1 from v$session s where p.addr = s.paddr and s.username='FLYANG')) as pnum0 "; // 进程数
		//		qry += "\n ,(select count(*) from v$session where username='FLYANG') as snum0"; // 会话数
		qry += "\n ,(select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')||to_char(sysdate,'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''') from dual ) as datetimestr";
		qry += "\n  from dual ";
		// System
		// WriteLogTXT("debug", "GetAccregcount1", qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		String responsestr = "{\"result\":1,\"msg\":\"操作成功！\",\"SERVERDATETIME\":\"" + tb.getRow(0).get("DATETIMESTR").toString() + "\",\"rows\":["

				+ "{\"title\":\"企业数\",\"value\":\"" + tb.getRow(0).get("ACCCOUNT").toString() + "(" + tb.getRow(0).get("ACCNUM").toString() + ")个\"}"//
				+ ",{\"title\":\"用户数\",\"value\":\"" + tb.getRow(0).get("USERSUM").toString() + "(" + tb.getRow(0).get("USERCOUNT").toString() + ")人\"}";
		//
		String responsestr1 = ",{\"title\":\"店铺数\",\"value\":\"" + tb.getRow(0).get("HOUSESUM").toString() + "间\"}"//
				+ ",{\"title\":\"会员数B/C\",\"value\":\"" + tb.getRow(0).get("VIPSUM").toString() + "/" + tb.getRow(0).get("CVIPSUM").toString() + "人\"}"//
		//				+ ",\"HOUSESUM\":\"" + tb.getRow(0).get("HOUSESUM").toString() + "\"" //
		//				+ ",\"VIPSUM\":\"" + tb.getRow(0).get("VIPSUM").toString() + "\"" // B端会员数
		//				+ ",\"CVIPSUM\":\"" + tb.getRow(0).get("CVIPSUM").toString() + "\"" // C端会员数
		//				+ ",{\"进程数\": \"" + tb.getRow(0).get("PNUM").toString() + "(" + tb.getRow(0).get("PNUM0").toString() + ")\"}"//
		//				+ ",{\"会话数\": \"" + tb.getRow(0).get("SNUM").toString() + "(" + tb.getRow(0).get("SNUM0").toString() + ")\"}"//
		//				+ ",\"PROCESSNUM\":\"" + tb.getRow(0).get("PNUM").toString() + "\"" //
		//				+ ",\"PROCESSNUM0\":\"" + tb.getRow(0).get("PNUM0").toString() + "\"" //
		//				+ ",\"SESSIONNUM\":\"" + tb.getRow(0).get("SNUM").toString() + "\"" //
		//				+ ",\"SESSIONNUM0\":\"" + tb.getRow(0).get("SNUM0").toString() + "\""
		;
		// + ",\"SERVERDATETIME\":\"" +
		// GetServerdatetime().toString("yyyy-MM-dd HH:mm:ss") + "\""
		// + ",\"SERVERDATETIME\":\"" +
		// tb.getRow(0).get("datetimestr").toString()().Replace("星期", "
		// ") + "\""
		// to_char(a.notedate,'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED
		// CHINESE''') as weekstr

		// 充值统计
		qry = "select sum(a.curr) as curr,sum(a.paycurr) as paycurr ";
		qry += " from accmoney a join accreg b on a.accid=b.accid ";
		qry += " join mobilearea c on substr(b.mobile,1,7)=c.mobile ";
		qry += " where a.accid>1000 and a.paycurr>0 and a.fs=0";
		tb = DbHelperSQL.Query(qry).getTable(1);
		// WriteLogTXT("debug", "2", qry);

		//		responsestr += ",\"FYGBALCURR\":\"" + tb.getRow(0).get("CURR").toString() + "\"" // 充值得枫杨果
		//				+ ",\"FYGPAYCURR\":\"" + tb.getRow(0).get("PAYCURR").toString() + "\""; // 充值金额
		responsestr += ",{\"title\":\"枫杨果\",\"value\":\"" + tb.getRow(0).get("CURR").toString() + "个(" + tb.getRow(0).get("PAYCURR").toString() + "元)\"}";
		responsestr += responsestr1;

		qry = " select nvl(sum(case a.ntid when 1 then -b.curr else b.curr end ),0) as cgcurr ";
		qry += " ,nvl(sum(case a.ntid when 1 then -b.amount else b.amount end ),0) as cgamount ";
		qry += " ,count(distinct a.accid||'^'||a.noteno) as cgnum ";
		qry += "  from wareinh a join wareinm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "  where a.statetag=1 ";
		qry += "  and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a1.accid=a.accid)";
		// qry += " and a.NOTEDATE >= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		// qry += " and a.NOTEDATE <= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		qry += "  and a.notedate >= to_date(to_char(sysdate,'yyyy-mm-dd')||' 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		qry += "  and a.notedate <= to_date(to_char(sysdate,'yyyy-mm-dd')||' 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		// WriteLogTXT("debug", "4", qry);

		tb = DbHelperSQL.Query(qry).getTable(1);
		//		responsestr += ",\"CGNUM\": \"" + tb.getRow(0).get("CGNUM").toString() + "\"" + ",\"CGAMOUNT\":\"" + tb.getRow(0).get("CGAMOUNT").toString() + "\"" + ",\"CGCURR\":\"" + tb.getRow(0).get("CGCURR").toString()
		//				+ "\"";
		responsestr += ",{\"title\":\"本日采购量\",\"value\":\"" + tb.getRow(0).get("CGAMOUNT").toString() + "(" + tb.getRow(0).get("CGNUM").toString() + "单)\"}"//
				+ ",{\"title\":\"本日采购额\",\"value\":\"" + tb.getRow(0).get("CGCURR").toString() + "元\"}";

		qry = "  select nvl(sum(xsamount),0) as xsamount,nvl(sum(xscurr),0) as xscurr,nvl(sum(xsnum),0) as xsnum from ( ";
		qry += "  select sum(case a.ntid when 2 then -b.curr else b.curr end ) as xscurr ";
		qry += " ,sum(case a.ntid when 2 then -b.amount else b.amount end ) as xsamount ";
		qry += " ,count(distinct a.accid||'^'||a.noteno) as xsnum ";
		qry += "  from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "  where a.statetag=1 ";
		qry += "  and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a1.accid=a.accid)";
		// qry += " and a.NOTEDATE >= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		// qry += " and a.NOTEDATE <= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		qry += "  and a.notedate >= to_date(to_char(sysdate,'yyyy-mm-dd')||' 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		qry += "  and a.notedate <= to_date(to_char(sysdate,'yyyy-mm-dd')||' 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		// 商场销售
		qry += "  union all";
		qry += "  select sum(b.curr) as xscurr ";
		qry += " ,sum(b.amount) as xsamount ";
		qry += " ,count(distinct a.accid||'^'||a.noteno) as xsnum ";
		qry += "  from shopsaleh a join shopsalem b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "  where a.statetag=1 ";
		qry += "  and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a1.accid=a.accid)";
		qry += "  and a.notedate >= to_date(to_char(sysdate,'yyyy-mm-dd')||' 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		qry += "  and a.notedate <= to_date(to_char(sysdate,'yyyy-mm-dd')||' 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		qry += ")";
		// WriteLogTXT("debug", "3", qry);

		tb = DbHelperSQL.Query(qry).getTable(1);
		//		responsestr += ",\"XSNUM\": \"" + tb.getRow(0).get("XSNUM").toString() + "\"" + ",\"XSAMOUNT\":\"" + tb.getRow(0).get("XSAMOUNT").toString() + "\"" + ",\"XSCURR\":\"" + tb.getRow(0).get("XSCURR").toString()
		//				+ "\"";
		responsestr += ",{\"title\":\"本日销售量\",\"value\":\"" + tb.getRow(0).get("XSAMOUNT").toString() + "(" + tb.getRow(0).get("XSNUM").toString() + "单)\"}"//
				+ ",{\"title\":\"本日销售额\",\"value\":\"" + tb.getRow(0).get("XSCURR").toString() + "元\"}";
		// 刷卡金额
		//		qry += " ,count(distinct a.accid||'^'||a.noteno) as cgnum ";

		qry = "  select nvl(sum(a.curr),0) as curr,count(distinct a.accid||'^'||a.noteno) as num from dealrecord a where a.statetag=1";
		qry += "  and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a1.accid=a.accid)";
		qry += "  and a.notedate >= to_date(to_char(sysdate,'yyyy-mm-dd')||' 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		qry += "  and a.notedate <= to_date(to_char(sysdate,'yyyy-mm-dd')||' 23:59:59','yyyy-mm-dd hh24:mi:ss') ";

		tb = DbHelperSQL.Query(qry).getTable(1);
		//		responsestr += ",\"SKCURR\": \"" + tb.getRow(0).get("CURR").toString() + "\"";
		responsestr += ",{\"title\":\"本日刷卡额\",\"value\":\"" + tb.getRow(0).get("CURR").toString() + "元(" + tb.getRow(0).get("NUM").toString() + "笔)\"}";

		qry = " select uvalue from uparameter where accid=0 and usection='OPTIONS' and USYMBOL='WORKDAY'";
		tb = DbHelperSQL.Query(qry).getTable(1);
		String daystr = tb.getRow(0).get("UVALUE").toString().trim();

		qry = "select sum(a.amount) as kcamount from waresum a where a.daystr='" + daystr + "'";
		// WriteLogTXT("debug", "5", qry);

		tb = DbHelperSQL.Query(qry).getTable(1);
		//		responsestr += ",\"WORKDAY\": \"" + daystr + "\"" + ",\"KCAMOUNT\":\"" + tb.getRow(0).get("KCAMOUNT").toString() + "\"";
		responsestr += ",{\"title\":\"" + daystr + "库存\",\"value\":\"" + tb.getRow(0).get("KCAMOUNT").toString() + "\"}";

		//		qry = " select a.tablespace_name, round(total,2) as total, round(free,2) as free, round(total,2)-round(free,2) as used, substr(free/total * 100, 1, 5) as FREEB, substr((total - free)/total * 100, 1, 5) as USEDB from ";
		//		qry += "\n (select tablespace_name, sum(bytes)/1024/1024/1000 as total from dba_data_files group by tablespace_name) a,";
		//		qry += "\n (select tablespace_name, sum(bytes)/1024/1024/1000 as free from dba_free_space group by tablespace_name) b";

		qry = " select a.tablespace_name,  total,  free, total-free as used, round(free/total * 100, 2) as FREEB, round((total - free)/total * 100, 2) as USEDB from ";
		qry += "\n (select tablespace_name, round(sum(bytes)/1024/1024/1000,2) as total from dba_data_files group by tablespace_name) a,";
		qry += "\n (select tablespace_name, round(sum(bytes)/1024/1024/1000,2) as free from dba_free_space group by tablespace_name) b";

		qry += "\n where a.tablespace_name = b.tablespace_name";
		qry += "\n and a.tablespace_name ='FLYANG_DATA' ";
		//		qry += "\n order by a.tablespace_name";
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			//		responsestr += ",\"WORKDAY\": \"" + daystr + "\"" + ",\"KCAMOUNT\":\"" + tb.getRow(0).get("KCAMOUNT").toString() + "\"";
			responsestr += ",{\"title\":\"数据空间\",\"value\":\"" + tb.getRow(0).get("TOTAL").toString() + "G\"}"//
					+ ",{\"title\":\"已用空间\",\"value\":\"" + tb.getRow(0).get("USED").toString() + "G(" + tb.getRow(0).get("USEDB").toString() + "%)\"}" //
					+ ",{\"title\":\"未用空间\",\"value\":\"" + tb.getRow(0).get("FREE").toString() + "G(" + tb.getRow(0).get("FREEB").toString() + "%)\"}";
		}

		qry = "select (select count(*) from v$process) as pnum "; // 进程数
		qry += "\n ,(select count(*) from v$session) as snum"; // 会话数
		qry += "\n ,(select count(*) from v$process p where exists (select 1 from v$session s where p.addr = s.paddr and s.username='FLYANG')) as pnum0 "; // 进程数
		qry += "\n ,(select count(*) from v$session where username='FLYANG') as snum0"; // 会话数
		qry += "\n  from dual ";
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			responsestr += ",{\"title\":\"进程数\",\"value\":\"" + tb.getRow(0).get("PNUM").toString() + "(" + tb.getRow(0).get("PNUM0").toString() + ")\"}"//
					+ ",{\"title\":\"会话数\",\"value\":\"" + tb.getRow(0).get("SNUM").toString() + "(" + tb.getRow(0).get("SNUM0").toString() + ")\"}";
		}

		responsestr += "]}";
		return responsestr;
	}

	public String doAccregcount2(int yearid, int monthid) {
		String mindate = yearid + "-01-01 00:00:00";
		String maxdate = yearid + "-12-31 23:59:59";
		Date startdate;
		Date enddate;
		// System.out.println("1:" + mindate + " " + maxdate);
		if (monthid > 0)// 0=年，1-12=1-12月
		{
			// Calendar cal = Calendar.getInstance();
			// cal.set(yearid, monthid, 1);

			startdate = Func.getFirstDayOfMonth(yearid, monthid);
			enddate = Func.getLastDayOfMonth(yearid, monthid);

			mindate = Func.DateToStr(startdate, "yyyy-MM-dd") + " 00:00:00";
			maxdate = Func.DateToStr(enddate, "yyyy-MM-dd") + " 23:59:59";
		} else {
			startdate = Func.StrToDate(mindate, "yyyy-MM-dd");
			//			System.out.println("maxdate="+maxdate);
			enddate = Func.StrToDate(maxdate, "yyyy-MM-dd");
		}
		//		System.out.println("enddate=" + Func.DateToStr(enddate) + "  Nowdatetime=" + Nowdatetime );

		if (Func.DateToStr(enddate).compareTo(Func.strLeft(Nowdatetime, 10)) > 0) {
			enddate = Func.StrToDate(Nowdatetime);   // pFunc.getServerdatetime();
			maxdate = Func.DateToStr(enddate, "yyyy-MM-dd") + " 23:59:59";
		}
		int days = Func.getBetweenDays(startdate, enddate);
		//		System.out.println("days=" + days + "  " + mindate + " " + maxdate);

		Table tb = new Table();

		String qry = "select  (select count(*) from accreg a join mobilearea b on substr(a.mobile,1,7)=b.mobile where a.accid>1000 ";
		qry += "\n  and a.regdate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.regdate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "  ) as acccount"; // 本期注册企业数
		qry += "\n, (select count(*) from accreg a join mobilearea b on substr(a.mobile,1,7)=b.mobile where a.accid>1000 ";
		qry += "\n  and a.regdate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "  ) as ljacccount"; // 累计注册企业数

		qry += "\n ,(select count(*) from employe a where a.statetag=1 and a.accid>1000 and a.passkey=1 ";
		qry += "\n  and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid ";
		qry += "\n  and a1.regdate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += " )) as usersum  ";// 本期累计用户数

		qry += "\n ,(select count(distinct b.accid) from userlogin a join employe b on a.epid=b.epid where  exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where b.accid=a1.accid) and b.accid>1000";
		qry += "  and a.logindate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.logindate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";

		qry += "  ) as accnum"; // 本期登录企业数

		qry += "\n ,(select count(distinct to_char(a.logindate,'yyyy-mm-dd')||'^'||a.epid||'^') from userlogin a join employe b on a.epid=b.epid where  exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where b.accid=a1.accid) and b.accid>1000 ";
		qry += "  and a.logindate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.logindate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "  ) as usercount ";// 本期登录用户数
		// 本期新增店铺数
		// qry += "\n ,(select count(*) from warehouse a where a.statetag=1 and
		// a.accid>1000 and exists (select 1 from accreg a1 join mobilearea b1
		// on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid ";
		// qry += "\n and a1.regdate>=to_date('" + mindate + "','yyyy-mm-dd
		// hh24:mi:ss') and a1.regdate<=to_date('" + maxdate + "','yyyy-mm-dd
		// hh24:mi:ss') ";
		// qry += " )) as housesum ";

		qry += "\n ,(select count(*) from guestvip a where a.statetag=1 and a.accid>1000 and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid  ";
		qry += "  and a.createdate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.createdate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "  )) as vipsum  "; // 本期B端新增会员数

		qry += "\n ,(select count(*) from guestvip a where a.statetag=1 and a.accid>1000 and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid  ";
		qry += " and a.createdate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "  )) as ljvipsum  "; // 本期B端累计会员数

		qry += "\n ,(select count(*) from vipgroup a join mobilearea b on substr(a.mobile,1,7)=b.mobile ";
		qry += "  and a.regdate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.regdate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += " ) as cvipsum"; // 本期C端会员数

		qry += "\n ,(select count(*) from vipgroup a join mobilearea b on substr(a.mobile,1,7)=b.mobile ";
		qry += "  and a.regdate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += " ) as ljcvipsum"; // 本期C端累计会员数

		qry += "\n ,(select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')||to_char(sysdate,'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''') from dual ) as datetimestr";
		qry += "\n  from dual ";
		// Table tb = new Table();
		// WriteDebugTxt("GetAccregcount1", qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		// System.out.println("3");
		int usercount = Integer.parseInt(tb.getRow(0).get("USERCOUNT").toString()); // 本期用户数
		float dayuser = 0;
		if (days > 0)
			dayuser = (float) Func.getRound(usercount / days, 0);

		String responsestr = "{\"result\":1,\"msg\":\"操作成功！\",\"ACCOUNT\": \"" + tb.getRow(0).get("ACCCOUNT").toString() + "\"" // 本期注册企业数
				+ ",\"LJACCCOUNT\":\"" + tb.getRow(0).get("LJACCCOUNT").toString() + "\"" // 本期累计注册企业数

				+ ",\"USERSUM\":\"" + tb.getRow(0).get("USERSUM").toString() + "\"" // 本期累计用户数

				+ ",\"USERCOUNT\":\"" + usercount + "\"" // 本期登录用户数
				+ ",\"ACCNUM\":\"" + tb.getRow(0).get("ACCNUM").toString() + "\"" // 本期登录企业数

				+ ",\"HOUSESUM\":\"0\"" // 店铺数
				+ ",\"VIPSUM\":\"" + tb.getRow(0).get("VIPSUM").toString() + "\"" // 本期B端注册会员数
				+ ",\"LJVIPSUM\":\"" + tb.getRow(0).get("LJVIPSUM").toString() + "\"" // 本期B端累计注册会员数
				+ ",\"CVIPSUM\":\"" + tb.getRow(0).get("CVIPSUM").toString() + "\"" // 本期C端注册会员数
				+ ",\"LJCVIPSUM\":\"" + tb.getRow(0).get("LJCVIPSUM").toString() + "\"" // 本期C端累计注册会员数
				+ ",\"SERVERDATETIME\":\"" + tb.getRow(0).get("DATETIMESTR").toString() + "\"";
		// to_char(a.notedate,'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED
		// CHINESE''') as weekstr

		// 本月充值统计
		qry = "select sum(a.curr) as curr,sum(a.paycurr) as paycurr,count(distinct a.accid) as num ";
		qry += " from accmoney a join accreg b on a.accid=b.accid ";
		qry += " join mobilearea c on substr(b.mobile,1,7)=c.mobile ";
		qry += " where a.accid>1000 and a.paycurr>0 and a.fs=0";
		qry += " and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		// WriteDebugTxt("GetAccregcount2", qry);
		// System.out.println("4");

		tb = DbHelperSQL.Query(qry).getTable(1);

		responsestr += ",\"FYGBALCURR\":\"" + tb.getRow(0).get("CURR").toString() + "\"" // 充值得枫杨果
				+ ",\"FYGPAYCURR\":\"" + tb.getRow(0).get("PAYCURR").toString() + "\"" // 充值金额
				+ ",\"FYGACCNUM\":\"" + tb.getRow(0).get("NUM").toString() + "\"" // 充值企业数
		;
		// 本月交易量
		qry = "  select nvl(sum(xsamount),0) as xsamount,nvl(sum(xscurr),0) as xscurr,nvl(sum(xsnum),0) as xsnum from ( ";
		qry += "  select sum(case a.ntid when 2 then -b.curr else b.curr end ) as xscurr ";
		qry += " ,sum(case a.ntid when 2 then -b.amount else b.amount end ) as xsamount ";
		qry += " ,count(distinct a.accid||'^'||a.noteno) as xsnum ";
		qry += "  from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "  where a.statetag=1 ";
		qry += "  and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a1.accid=a.accid)";
		qry += "  and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		// qry += " and a.notedate >= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		// qry += " and a.notedate <= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		// 商场销售
		qry += "  union all";
		qry += "  select sum(b.curr) as xscurr ";
		qry += " ,sum(b.amount) as xsamount ";
		qry += " ,count(distinct a.accid||'^'||a.noteno) as xsnum ";
		qry += "  from shopsaleh a join shopsalem b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "  where a.statetag=1 ";
		qry += "  and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a1.accid=a.accid)";
		qry += "  and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		// qry += " and a.notedate >= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		// qry += " and a.notedate <= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		qry += ")";
		// WriteDebugTxt("GetAccregcount3", qry);
		// System.out.println("5");

		tb = DbHelperSQL.Query(qry).getTable(1);
		Double xscurr = Double.parseDouble(tb.getRow(0).get("XSCURR").toString());
		responsestr += ",\"XSNUM\": \"" + tb.getRow(0).get("XSNUM").toString() + "\"" //
				+ ",\"XSAMOUNT\":\"" + tb.getRow(0).get("XSAMOUNT").toString() + "\"" //
				+ ",\"XSCURR\":\"" + Func.FormatNumber(xscurr, 2) + "\"";

		// 刷卡金额
		qry = "  select nvl(sum(a.curr),0) as curr,count(distinct a.accid||'^'||a.noteno) as num from dealrecord a where a.statetag=1";
		qry += "  and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a1.accid=a.accid)";
		qry += "  and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";

		tb = DbHelperSQL.Query(qry).getTable(1);
		responsestr += ",\"SKCURR\": \"" + tb.getRow(0).get("CURR").toString() + "\"";

		qry = " select nvl(sum(case a.ntid when 1 then -b.curr else b.curr end ),0) as cgcurr ";
		qry += " ,nvl(sum(case a.ntid when 1 then -b.amount else b.amount end ),0) as cgamount ";
		qry += " ,count(distinct a.accid||'^'||a.noteno) as cgnum ";
		qry += "  from wareinh a join wareinm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "  where a.statetag=1 ";
		qry += "  and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a1.accid=a.accid)";
		qry += "  and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		// qry += " and a.notedate >= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		// qry += " and a.notedate <= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		// WriteDebugTxt("GetAccregcount4", qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		// System.out.println("6");
		Double cgcurr = Double.parseDouble(tb.getRow(0).get("CGCURR").toString());

		// Float cgcurr =
		// Double.parseDouble(tb.getRow(0).get("CGCURR").toString());

		// System.out.println("aaa2 "+tb.getRow(0).get("CGCURR").toString()+"
		// "+cgcurr);
		//
		// System.out.println("bbb1 "+Func.FormatNumber(cgcurr,2)+" ccc
		// "+Func.FormatNumber(cgcurr));
		//
		// System.out.println("wmg "+Func.getNumricStr(cgcurr));

		responsestr += ",\"CGNUM\": \"" + tb.getRow(0).get("CGNUM").toString() + "\"" //
				+ ",\"CGAMOUNT\":\"" + tb.getRow(0).get("CGAMOUNT").toString() + "\""//
				+ ",\"CGCURR\":\"" + Func.FormatNumber(cgcurr, 2) + "\"";

		double daycurr = 0;
		if (days > 0)
			daycurr = (double) Func.getRound((cgcurr + xscurr) / days, 1);
		// Math.Round((decimal)(cgcurr + xscurr) / (decimal)days, 1,
		// MidpointRounding.AwayFromZero);// usercount / days;

		qry = " select uvalue from uparameter where accid=0 and usection='OPTIONS' and USYMBOL='WORKDAY'";
		tb = DbHelperSQL.Query(qry).getTable(1);
		String daystr = tb.getRow(0).get("UVALUE").toString().trim();
		qry = "select sum(a.amount) as kcamount from waresum a where a.daystr='" + daystr + "'";
		tb = DbHelperSQL.Query(qry).getTable(1);
		responsestr += ",\"WORKDAY\": \"" + daystr + "\"" //
				+ ",\"KCAMOUNT\":\"" + tb.getRow(0).get("KCAMOUNT").toString() + "\"";

		responsestr += ",\"DAYS\": \"" + days + "\"" //
				+ ",\"MINDATE\":\"" + mindate + "\"" //
				+ ",\"MAXDATE\":\"" + maxdate + "\"" //
				+ ",\"DAYCURR\":\"" + Func.FormatNumber(daycurr, 2) + "\"" // 日均交易额
				+ ",\"DAYUSER\":\"" + dayuser + "\"" // 日均活跃用户
		;

		responsestr += "}";
		return responsestr;

	}

	public String doAccregcount2x(int yearid, int monthid) {
		String mindate = yearid + "-01-01 00:00:00";
		String maxdate = yearid + "-12-31 23:59:59";
		Date startdate;
		Date enddate;
		// System.out.println("1:" + mindate + " " + maxdate);
		if (monthid > 0)// 0=年，1-12=1-12月
		{
			// Calendar cal = Calendar.getInstance();
			// cal.set(yearid, monthid, 1);

			startdate = Func.getFirstDayOfMonth(yearid, monthid);
			enddate = Func.getLastDayOfMonth(yearid, monthid);

			mindate = Func.DateToStr(startdate, "yyyy-MM-dd") + " 00:00:00";
			maxdate = Func.DateToStr(enddate, "yyyy-MM-dd") + " 23:59:59";
		} else {
			startdate = Func.StrToDate(mindate, "yyyy-MM-dd");
			//			System.out.println("maxdate="+maxdate);
			enddate = Func.StrToDate(maxdate, "yyyy-MM-dd");
		}
		//		System.out.println("enddate=" + Func.DateToStr(enddate) + "  Nowdatetime=" + Nowdatetime );

		if (Func.DateToStr(enddate).compareTo(Func.strLeft(Nowdatetime, 10)) > 0) {
			enddate = Func.StrToDate(Nowdatetime);   // pFunc.getServerdatetime();
			maxdate = Func.DateToStr(enddate, "yyyy-MM-dd") + " 23:59:59";
		}
		int days = Func.getBetweenDays(startdate, enddate);
		//		System.out.println("days=" + days + "  " + mindate + " " + maxdate);

		Table tb = new Table();

		String qry = "select  (select count(*) from accreg a join mobilearea b on substr(a.mobile,1,7)=b.mobile where a.accid>1000 ";
		qry += "\n  and a.regdate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.regdate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "  ) as acccount"; // 本期注册企业数
		qry += "\n, (select count(*) from accreg a join mobilearea b on substr(a.mobile,1,7)=b.mobile where a.accid>1000 ";
		qry += "\n  and a.regdate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "  ) as ljacccount"; // 累计注册企业数

		qry += "\n ,(select count(*) from employe a where a.statetag=1 and a.accid>1000 and a.passkey=1 ";
		qry += "\n  and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid ";
		qry += "\n  and a1.regdate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += " )) as usersum  ";// 本期累计用户数

		qry += "\n ,(select count(distinct b.accid) from userlogin a join employe b on a.epid=b.epid where  exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where b.accid=a1.accid) and b.accid>1000";
		qry += "  and a.logindate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.logindate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";

		qry += "  ) as accnum"; // 本期登录企业数
		//天/人
		qry += "\n ,(select count(distinct to_char(a.logindate,'yyyy-mm-dd')||'^'||a.epid||'^') from userlogin a join employe b on a.epid=b.epid where  exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where b.accid=a1.accid) and b.accid>1000 ";
		qry += "  and a.logindate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.logindate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "  ) as usercount ";// 本期登录用户数
		// 本期新增店铺数
		// qry += "\n ,(select count(*) from warehouse a where a.statetag=1 and
		// a.accid>1000 and exists (select 1 from accreg a1 join mobilearea b1
		// on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid ";
		// qry += "\n and a1.regdate>=to_date('" + mindate + "','yyyy-mm-dd
		// hh24:mi:ss') and a1.regdate<=to_date('" + maxdate + "','yyyy-mm-dd
		// hh24:mi:ss') ";
		// qry += " )) as housesum ";

		qry += "\n ,(select count(*) from guestvip a where a.statetag=1 and a.accid>1000 and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid  ";
		qry += "  and a.createdate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.createdate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "  )) as vipsum  "; // 本期B端新增会员数

		qry += "\n ,(select count(*) from guestvip a where a.statetag=1 and a.accid>1000 and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a.accid=a1.accid  ";
		qry += " and a.createdate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "  )) as ljvipsum  "; // 本期B端累计会员数

		qry += "\n ,(select count(*) from vipgroup a join mobilearea b on substr(a.mobile,1,7)=b.mobile ";
		qry += "  and a.regdate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.regdate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += " ) as cvipsum"; // 本期C端会员数

		qry += "\n ,(select count(*) from vipgroup a join mobilearea b on substr(a.mobile,1,7)=b.mobile ";
		qry += "  and a.regdate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += " ) as ljcvipsum"; // 本期C端累计会员数

		qry += "\n ,(select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')||to_char(sysdate,'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED CHINESE''') from dual ) as datetimestr";
		qry += "\n  from dual ";
		// Table tb = new Table();
		//		LogUtils.LogDebugWrite("GetAccregcount1", qry);

		tb = DbHelperSQL.Query(qry).getTable(1);
		// System.out.println("3");
		int usercount = Integer.parseInt(tb.getRow(0).get("USERCOUNT").toString()); // 本期用户数
		int dayuser = 0;
		if (days > 0)
			dayuser = (int) Func.getRound(usercount / days, 0);
		//				+ ",\"MINDATE\":\"" + mindate + "\"" //
		//				+ ",\"MAXDATE\":\"" + maxdate + "\"" //

		String responsestr = "{\"result\":1,\"msg\":\"操作成功！\",\"SERVERDATETIME\":\"" + tb.getRow(0).get("DATETIMESTR").toString() + "\"" //
				+ ",\"MINDATE\":\"" + mindate + "\"" + ",\"MAXDATE\":\"" + maxdate + "\"" //
				+ ",\"rows\":["//
				+ "{\"title\":\"注册/累计企业数\",\"value\":\"" + tb.getRow(0).get("ACCCOUNT").toString() + "/" + tb.getRow(0).get("LJACCCOUNT").toString() + "个\"}"//
				+ ",{\"title\":\"累计用户数\",\"value\":\"" + tb.getRow(0).get("USERSUM").toString() + "人\"}" //
				+ ",{\"title\":\"登录企业/用户\",\"value\":\"" + tb.getRow(0).get("ACCNUM").toString() + "个/" + usercount + "人/次/天\"}"//
				+ ",{\"title\":\"B端新增/累计会员\",\"value\":\"" + tb.getRow(0).get("VIPSUM").toString() + "/" + tb.getRow(0).get("LJVIPSUM").toString() + "人\"}"//
				+ ",{\"title\":\"C端新增/累计会员\",\"value\":\"" + tb.getRow(0).get("CVIPSUM").toString() + "/" + tb.getRow(0).get("LJCVIPSUM").toString() + "人\"}"//
		;

		//				"{\"ACCOUNT\": \"" + tb.getRow(0).get("ACCCOUNT").toString() + "\"" // 本期注册企业数
		//				+ ",\"LJACCCOUNT\":\"" + tb.getRow(0).get("LJACCCOUNT").toString() + "\"" // 本期累计注册企业数

		//				+ ",\"USERSUM\":\"" + tb.getRow(0).get("USERSUM").toString() + "\"" // 本期累计用户数

		//				+ ",\"USERCOUNT\":\"" + usercount + "\"" // 本期登录用户数
		//				+ ",\"ACCNUM\":\"" + tb.getRow(0).get("ACCNUM").toString() + "\"" // 本期登录企业数

		//				+ ",\"HOUSESUM\":\"0\"" // 店铺数
		//				+ ",\"VIPSUM\":\"" + tb.getRow(0).get("VIPSUM").toString() + "\"" // 本期B端注册会员数
		//				+ ",\"LJVIPSUM\":\"" + tb.getRow(0).get("LJVIPSUM").toString() + "\"" // 本期B端累计注册会员数
		//				+ ",\"CVIPSUM\":\"" + tb.getRow(0).get("CVIPSUM").toString() + "\"" // 本期C端注册会员数
		//				+ ",\"LJCVIPSUM\":\"" + tb.getRow(0).get("LJCVIPSUM").toString() + "\"" // 本期C端累计注册会员数
		//				+ ",\"SERVERDATETIME\":\"" + tb.getRow(0).get("DATETIMESTR").toString() + "\"";
		// to_char(a.notedate,'day','NLS_DATE_LANGUAGE = ''SIMPLIFIED
		// CHINESE''') as weekstr

		// 本月充值统计
		qry = "select sum(a.curr) as curr,sum(a.paycurr) as paycurr,count(distinct a.accid) as num ,count(*) as count";
		qry += " from accmoney a join accreg b on a.accid=b.accid ";
		qry += " join mobilearea c on substr(b.mobile,1,7)=c.mobile ";
		qry += " where a.accid>1000 and a.paycurr>0 and a.fs=0";
		qry += " and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		//		LogUtils.LogDebugWrite("GetAccregcount2", qry);
		// WriteDebugTxt("GetAccregcount2", qry);
		// System.out.println("4");

		tb = DbHelperSQL.Query(qry).getTable(1);
		//		responsestr += //",{\"title\":\"充值企业数\",\"value\":\"" + tb.getRow(0).get("NUM").toString() + "个\"}"//
		//				"{\"title\":\"充值企业数/枫杨果/金额\",\"value\":\"" + tb.getRow(0).get("NUM").toString() + "个/" + tb.getRow(0).get("CURR").toString() + "个/" + tb.getRow(0).get("PAYCURR").toString() + "元\"}"//
		responsestr += ",{\"title\":\"充值企业数\",\"value\":\"" + tb.getRow(0).get("NUM").toString() + "个(" + tb.getRow(0).get("COUNT").toString() + "次)\"}"//
				+ ",{\"title\":\"枫杨果/金额\",\"value\":\"" + tb.getRow(0).get("CURR").toString() + "个/" + tb.getRow(0).get("PAYCURR").toString() + "元\"}"//
		;

		qry = " select nvl(sum(case a.ntid when 1 then -b.curr else b.curr end ),0) as cgcurr ";
		qry += " ,nvl(sum(case a.ntid when 1 then -b.amount else b.amount end ),0) as cgamount ";
		qry += " ,count(distinct a.accid||'^'||a.noteno) as cgnum ";
		qry += "  from wareinh a join wareinm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "  where a.statetag=1 ";
		qry += "  and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a1.accid=a.accid)";
		qry += "  and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		// qry += " and a.notedate >= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		// qry += " and a.notedate <= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		//		LogUtils.LogDebugWrite("GetAccregcount3", qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		// System.out.println("6");
		Double cgcurr = Double.parseDouble(tb.getRow(0).get("CGCURR").toString());

		//		responsestr += ",\"CGNUM\": \"" + tb.getRow(0).get("CGNUM").toString() + "\"" //
		//				+ ",\"CGAMOUNT\":\"" + tb.getRow(0).get("CGAMOUNT").toString() + "\""//
		//				+ ",\"CGCURR\":\"" + Func.FormatNumber(cgcurr, 2) + "\"";
		responsestr += ",{\"title\":\"采购数量\",\"value\":\"" + tb.getRow(0).get("CGAMOUNT").toString() + "(" + tb.getRow(0).get("CGNUM").toString() + "单)\"}"//
				+ ",{\"title\":\"采购金额\",\"value\":\"" + tb.getRow(0).get("CGCURR").toString() + "元\"}";

		// 本月交易量
		qry = "  select nvl(sum(xsamount),0) as xsamount,nvl(sum(xscurr),0) as xscurr,nvl(sum(xsnum),0) as xsnum from ( ";
		qry += "  select /*+ordered index(a IX2_wareouth) */  sum(case a.ntid when 2 then -b.curr else b.curr end ) as xscurr ";
		qry += " ,sum(case a.ntid when 2 then -b.amount else b.amount end ) as xsamount ";
		qry += " ,count(distinct a.accid||'^'||a.noteno) as xsnum ";
		qry += "  from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "  where a.statetag=1 ";
		qry += "  and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a1.accid=a.accid)";
		qry += "  and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		// qry += " and a.notedate >= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		// qry += " and a.notedate <= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		// 商场销售
		qry += "  union all";
		qry += "  select /*+ordered index(a IX2_shopsaleh) */  sum(b.curr) as xscurr ";
		qry += " ,sum(b.amount) as xsamount ";
		qry += " ,count(distinct a.accid||'^'||a.noteno) as xsnum ";
		qry += "  from shopsaleh a join shopsalem b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "  where a.statetag=1 ";
		qry += "  and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a1.accid=a.accid)";
		qry += "  and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		// qry += " and a.notedate >= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		// qry += " and a.notedate <= to_date(to_char(sysdate,'yyyy-mm-dd')||'
		// 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		qry += ")";
		// WriteDebugTxt("GetAccregcount3", qry);
		// System.out.println("5");
		//		LogUtils.LogDebugWrite("GetAccregcount4", qry);

		tb = DbHelperSQL.Query(qry).getTable(1);
		Double xscurr = Double.parseDouble(tb.getRow(0).get("XSCURR").toString());
		//		responsestr += ",\"XSNUM\": \"" + tb.getRow(0).get("XSNUM").toString() + "\"" //
		//				+ ",\"XSAMOUNT\":\"" + tb.getRow(0).get("XSAMOUNT").toString() + "\"" //
		//				+ ",\"XSCURR\":\"" + Func.FormatNumber(xscurr, 2) + "\"";

		responsestr += ",{\"title\":\"销售数量\",\"value\":\"" + tb.getRow(0).get("XSAMOUNT").toString() + "(" + tb.getRow(0).get("XSNUM").toString() + "单)\"}"//
				+ ",{\"title\":\"销售金额\",\"value\":\"" + tb.getRow(0).get("XSCURR").toString() + "元\"}";

		// 刷卡金额
		qry = "  select nvl(sum(a.curr),0) as curr,count(distinct a.accid||'^'||a.noteno) as num from dealrecord a where a.statetag=1";
		qry += "  and exists (select 1 from accreg a1 join mobilearea b1 on substr(a1.mobile,1,7)=b1.mobile where a1.accid=a.accid)";
		qry += "  and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";

		tb = DbHelperSQL.Query(qry).getTable(1);
		//		responsestr += ",\"SKCURR\": \"" + tb.getRow(0).get("CURR").toString() + "\"";
		responsestr += ",{\"title\":\"刷卡金额\",\"value\":\"" + tb.getRow(0).get("CURR").toString() + "元(" + tb.getRow(0).get("NUM").toString() + "笔)\"}";
	//		LogUtils.LogDebugWrite("GetAccregcount5", qry);

		double daycurr = 0;
		if (days > 0)
			daycurr = (double) Func.getRound((cgcurr + xscurr) / days, 1);
		//		qry = " select uvalue from uparameter where accid=0 and usection='OPTIONS' and USYMBOL='WORKDAY'";
		//		tb = DbHelperSQL.Query(qry).getTable(1);
		//		String daystr = tb.getRow(0).get("UVALUE").toString().trim();
		//		qry = "select sum(a.amount) as kcamount from waresum a where a.daystr='" + daystr + "'";
		//		tb = DbHelperSQL.Query(qry).getTable(1);
		//		responsestr += ",\"WORKDAY\": \"" + daystr + "\"" //
		//				+ ",\"KCAMOUNT\":\"" + tb.getRow(0).get("KCAMOUNT").toString() + "\"";

		//		responsestr += ",\"DAYS\": \"" + days + "\"" //
		//				+ ",\"MINDATE\":\"" + mindate + "\"" //
		//				+ ",\"MAXDATE\":\"" + maxdate + "\"" //
		//				+ ",\"DAYCURR\":\"" + Func.FormatNumber(daycurr, 2) + "\"" // 日均交易额
		//				+ ",\"DAYUSER\":\"" + dayuser + "\"" // 日均活跃用户

		responsestr += ",{\"title\":\"日均交易额/活跃用户\",\"value\":\"" + Func.FormatNumber(daycurr, 2) + "元/" + dayuser + "人\"}"//

		;

		responsestr += "]}";
		return responsestr;

	}

	// 每日登账汇总账套数据
	public int doTotalacc(String workday) {
		if (workday.length() <= 0) {
			errmess = "日期参数无效！";
			return 0;
		}
		String qry = "begin ";
		qry += "   p_checkday('" + workday + "');";
		qry += " end;";
		DbHelperSQL db = new DbHelperSQL();
		int ret = db.ExecuteSql(qry, 18000);// 1小时=3600 5小时
		if (ret < 0) {
			errmess = db.getErrmess();
			return 0;
		} else {
			errmess = "操作成功";
			return 1;
		}
	}

	// 每日登账
	public int doPassday(String workday) {
		if (workday.length() <= 0) {
			errmess = "日期参数无效！";
			return 0;
		}

		//		String qry = "begin ";
		//		qry += "  p_passday('" + workday + "');";
		//		qry += " end;";
		//System.out.println(qry);
		//		if (DbHelperSQL.ExecuteSql(qry, 3600) < 0) {
		//			WriteResult(response, 0, "操作异常1!");
		//			return;
		//		}

		String qry = "declare";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n begin";
		qry += "\n  begin";
		qry += "\n    p_passday('" + workday + "');";
		qry += "\n    p_dataprocess;";
		qry += "\n    v_retcs:='1操作成功！';";
		qry += "\n  exception";
		qry += "\n     when others then";
		qry += "\n     v_retcs:='0'||substr(sqlerrm,1,190);";
		qry += "\n  end;";
		qry += "\n  select v_retcs into :retcs from dual;";
		qry += "\n end;";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param, 3600);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));

		//		String qry = "declare ";
		//		qry += "\n   v_nowdate  varchar2(10); ";
		//		qry += "\n   v_count number(10); ";
		//		qry += "\n   v_count1 number(10); ";
		//		qry += "\n   v_SENDVALUE number(10):=10;  ";
		//		qry += "\n begin ";
		//		qry += "\n   v_nowdate:='" + workday + "'; ";
		//		// qry += "\n exec p_cleardemodata; "; //清除演示套账数据
		//		// --删除前一日登录的记录
		//		qry += "\n  delete from useronline where (to_char(sysdate,'yyyy-mm-dd')>to_char(logindate,'yyyy-mm-dd') or epid=0 ) and devicetype<>'ERP'; ";
		//		qry += "\n  delete from omuseronline;";
		//
		//		// --将设备号清空，表示登录超时 devicetype=erp表示接口登录的用户,签名不过期 devicetype=PRT
		//		// qry+=" update useronline set deviceno='' where
		//		// to_char(logindate,'yyyy-mm-dd')<=v_nowdate; ";
		//		qry += "\n  update useronline set deviceno='' where logindate<=to_date(v_nowdate||' 23:59:59','yyyy-mm-dd hh24:mi:ss') and devicetype<>'ERP'; ";
		//		qry += "\n  commit;";
		//
		//		// --计算消耗的枫杨果
		//		// qry+=" insert into accmoney_tmp
		//		// (accid,notedate,fs,curr,paycurr,handno,remark) ";
		//
		//		qry += "\n  insert into accmoney (id,accid,notedate,fs,curr,paycurr,handno,remark,lastop,lastdate) ";
		//		qry += "\n  select accmoney_id.nextval,accid,notedate,fs,curr,paycurr,handno,remark,'sysdba',sysdate from (  ";
		//
		//		// qry += "\n select accid,to_date(v_nowdate||' 23:59:59','yyyy-mm-dd
		//		// hh24:mi:ss') as notedate,1 as fs ,-usernum as curr ,0 as paycurr,''
		//		// as handno ,'每日消耗'||case lytag when 0 then '枫杨果' else '枫杨果' end as
		//		// remark ";
		//		qry += "\n  select accid,to_date(v_nowdate||' 23:59:59','yyyy-mm-dd hh24:mi:ss') as notedate,1 as fs ,-usernum as curr ,0 as paycurr,'' as handno ,'每日消耗枫杨果' as remark  ";
		//		qry += "\n  from ( ";
		//		// qry += "\n select b.accid,c.lytag,count(distinct a.epid) as usernum
		//		// ";
		//		qry += "\n    select b.accid,count(distinct a.epid) as usernum  ";
		//		qry += "\n    from userlogin a ";
		//		qry += "\n    join employe b on a.epid=b.epid   ";
		//		qry += "\n    join accreg c on b.accid=c.accid";
		//		qry += "\n    where a.logindate>=to_date(v_nowdate||' 00:00:00','yyyy-mm-dd hh24:mi:ss') and a.logindate<=to_date(v_nowdate||' 23:59:59','yyyy-mm-dd hh24:mi:ss')  ";
		//		qry += "\n    and b.accid<>1000  and c.lytag=0"; // and a.devicetype<>'PRT'  lytag:0=充值用户才扣枫杨果
		//		// lytag:0=充值,1=买断
		//		// qry += "\n and c.lytag<2"; //上下衣系统注册的账户不扣
		//		// qry += "\n and exists (select 1 from accreg c where b.accid=c.accid
		//		// and c.lytag<2)"; //上下衣系统注册的账户不扣
		//		// --注册日期大于90天的每天要扣枫杨果,在2015-12-20之前注册的用户
		//		// qry += " and exists (select 1 from accreg c where sysdate -
		//		// c.regdate>90 and c.accid=b.accid and c.accid<>'1000') ";
		//		// qry += " and exists (select 1 from accreg c where c.accid=b.accid and
		//		// c.accid<>1000 ";
		//		// qry += " and ( c.regdate<to_date('2015-12-20','yyyy-mm-dd') and
		//		// sysdate - c.regdate>90 or
		//		// c.regdate>=to_date('2015-12-20','yyyy-mm-dd') and sysdate -
		//		// c.regdate>30) ) ";
		//
		//		// qry += "\n group by b.accid,c.lytag ";
		//		qry += "\n    group by b.accid ";
		//		qry += "\n  ) ); ";
		//		qry += "\n  commit;";
		//
		//		// --汇总账户余额 要改为游标方式逐条记录处理
		//		// qry += "\n update accreg set balcurr=(select nvl(sum(curr),0) from
		//		// accmoney where accreg.accid=accmoney.accid) where lytag=0; ";// where
		//		// lytag<2; ";
		//		qry += "\n  v_count:=0; ";
		//		qry += "\n  declare cursor cur_data is  ";
		//		qry += "\n     select accid from accreg where lytag=0 ;";
		//		qry += "\n     v_row cur_data%rowtype; ";
		//		qry += "\n  begin ";
		//		qry += "\n     for v_row in cur_data loop ";
		//		qry += "\n        update accreg set balcurr=(select nvl(sum(curr),0) from accmoney where accreg.accid=accmoney.accid) where accid=v_row.accid; ";// where
		//		qry += "\n        v_count:=v_count+1; ";
		//		qry += "\n        if v_count>1000 then  ";
		//		qry += "\n           commit; ";
		//		qry += "\n           v_count:=0; ";
		//		qry += "\n        end if; ";
		//		qry += "\n     end loop; ";
		//		qry += "\n  end;";
		//		// --写登账日
		//		qry += "\n  select count(*) into v_count from uparameter where accid=0 and usection='OPTIONS' and usymbol='WORKDAY';  ";
		//		qry += "\n  if v_count=0 then  ";
		//		qry += "\n     insert into uparameter (id,accid,usection,usymbol,uvalue,lastdate)  ";
		//		qry += "\n     values (uparameter_id.nextval,0, 'OPTIONS','WORKDAY',v_nowdate,sysdate); ";
		//		qry += "\n  else  ";
		//		qry += "\n     update uparameter set uvalue=v_nowdate,lastdate=sysdate ";
		//		qry += "\n     where accid=0 and usection='OPTIONS' and usymbol='WORKDAY';  ";
		//		qry += "\n  end if; ";
		//		// qry += "\n update accreg set calcdate=to_date('" + workday +
		//		// "','yyyy-mm-dd');";
		//		qry += "\n  commit;";
		//		// 删除未结账的商场零售单
		//		// qry += "\n delete from shopsalem a where exists (select 1 from
		//		// shopsaleh b where a.accid=b.accid and a.noteno=b.noteno";
		//		// qry += "\n and b.statetag=0 and b.notedate<=to_date(v_nowdate||'
		//		// 23:59:59','yyyy-mm-dd hh24:mi:ss') );";
		//		// qry += "\n delete from shopsaleh where statetag=0 and
		//		// notedate<=to_date(v_nowdate||' 23:59:59','yyyy-mm-dd hh24:mi:ss') ;";
		//
		//		// qry += "\n commit;";
		//
		//		// --查询推广人推荐且未获得赠送的账号
		//		// qry += "\n declare cursor cur_data is select
		//		// a.accid,a.accname,b.accid as p_accid,a.lytag from accreg a ";
		//		// qry += "\n join employe b on a.epid=b.epid ";
		//		// qry += "\n where a.epid>0 and not exists (select 1 from ACCGIFT c
		//		// where a.accid=c.accid and b.accid=c.p_accid); ";
		//		// qry += "\n v_row cur_data%rowtype; ";
		//		// qry += "\n begin ";
		//		// qry += "\n for v_row in cur_data loop ";
		//		// // --如果被推广人的账号累计登录10次，推广人获赠10个枫杨果
		//		// qry += "\n select count(distinct to_char(a.logindate,'yyyy-mm-dd'))
		//		// into v_count ";
		//		// qry += "\n from userlogin a join employe b on a.epid=b.epid ";
		//		// qry += "\n where b.accid=v_row.accid; ";
		//		// qry += "\n if v_count>=10 then ";
		//		// qry += "\n insert into ACCGIFT (p_accid,accid,lastdate) values
		//		// (v_row.p_accid,v_row.accid,sysdate); ";
		//		// qry += "\n insert into accmoney
		//		// (id,accid,notedate,fs,curr,paycurr,remark,lastdate,lastop,handno) ";
		//		// qry += "\n values (accmoney_id.nextval,v_row.p_accid
		//		// ,sysdate,0,v_SENDVALUE,0,'您推荐的用户'||v_row.accname||'已累计使用10次，系统赠送'||v_SENDVALUE||'个'||case
		//		// v_row.lytag when 0 then '枫杨果' else '枫杨果'
		//		// end||'。',sysdate,'sysdba',''); ";
		//		// qry += "\n end if; ";
		//		// qry += "\n end loop; ";
		//		// qry += "\n end; ";
		//		// qry += "\n commit;";
		//
		//		// 统计30天内商品销量
		//		//		qry += "\n   update  warecode set xsamount=0 where xsamount>0 or xsamount<0; ";
		//
		//		qry += "\n   v_count:=0;";
		//		qry += "\n   declare cursor cur_data is  ";
		//		qry += "\n     select wareid from warecode where (xsamount>0 or xsamount<0)  and statetag=1; ";
		//		qry += "\n     v_row cur_data%rowtype; ";
		//		qry += "\n   begin ";
		//		qry += "\n     for v_row in cur_data loop ";
		//		qry += "\n       update warecode set xsamount=0 where wareid=v_row.wareid; ";
		//		qry += "\n       v_count:=v_count+1; ";
		//		qry += "\n       if v_count>1000 then  ";
		//		qry += "\n          commit; ";
		//		qry += "\n          v_count:=0; ";
		//		qry += "\n       end if; ";
		//		qry += "\n     end loop; ";
		//		qry += "\n   end; ";
		//		qry += "\n   commit; ";
		////累计销量
		//		qry += "\n   v_count:=0;";
		//		qry += "\n   declare cursor cur_wareout is  ";
		//		qry += "\n    select wareid,sum(amount) as amount from (";
		//		qry += "\n      select b.wareid,sum(case a.ntid when 2 then -b.amount else b.amount end) as amount ";
		//		qry += "\n      from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
		//		qry += "\n      where a.statetag=1 and sysdate - a.notedate>30 ";
		////		qry += "\n      and a.notedate>=to_date(v_nowdate||' 00:00:00','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date(v_nowdate||' 23:59:59','yyyy-mm-dd hh24:mi:ss')  ";
		//		qry += "\n      group by b.wareid";
		//		qry += "\n      union all";
		//		qry += "\n      select b.wareid,sum(b.amount) as amount";
		//		qry += "\n      from shopsaleh a join shopsalem b on a.accid=b.accid and a.noteno=b.noteno";
		//		qry += "\n      where a.statetag=1 and sysdate - a.notedate>30 ";
		////		qry += "\n      and a.notedate>=to_date(v_nowdate||' 00:00:00','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date(v_nowdate||' 23:59:59','yyyy-mm-dd hh24:mi:ss')  ";
		//		qry += "\n      group by b.wareid";
		//		qry += "\n      ) group by wareid ;  ";
		//		qry += "\n   v_row cur_wareout%rowtype; ";
		//		qry += "\n begin ";
		//		qry += "\n   for v_row in cur_wareout loop ";
		//		qry += "\n       update warecode set xsamount=v_row.amount where wareid=v_row.wareid; ";
		//		qry += "\n       v_count:=v_count+1; ";
		//		qry += "\n       if v_count>1000 then  ";
		//		qry += "\n          commit; ";
		//		qry += "\n          v_count:=0; ";
		//		qry += "\n       end if; ";
		//		qry += "\n   end loop; ";
		//		qry += "\n end; ";
		//		qry += "\n commit; ";
		//
		//		// 统计30天内客户销售单数  累计客单数
		//		
		////		qry += "\n update  customer set xsnum=0 where xsnum>0 or xsnum<0; ";
		//
		//		qry += "\n   v_count:=0;";
		//		qry += "\n   declare cursor cur_data is  ";
		//		qry += "\n     select custid from customer where (xsnum>0 or xsnum<0)  and statetag=1; ";
		//		qry += "\n     v_row cur_data%rowtype; ";
		//		qry += "\n   begin ";
		//		qry += "\n     for v_row in cur_data loop ";
		//		qry += "\n       update customer set xsnum=0 where custid=v_row.custid; ";
		//		qry += "\n       v_count:=v_count+1; ";
		//		qry += "\n       if v_count>1000 then  ";
		//		qry += "\n          commit; ";
		//		qry += "\n          v_count:=0; ";
		//		qry += "\n       end if; ";
		//		qry += "\n     end loop; ";
		//		qry += "\n   end; ";
		//		qry += "\n   commit; ";
		//		//   累计客单数
		//		qry += "\n v_count:=0;";
		//		qry += "\n declare cursor cur_wareout is  ";
		//		qry += "\n   select a.custid,count(*) as num from wareouth a  ";
		//		qry += "\n   where a.ntid=1 and a.statetag=1 and sysdate - a.notedate>30 ";// group by a.custid ; ";
		//		qry += "\n   and a.notedate>=to_date(v_nowdate||' 00:00:00','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date(v_nowdate||' 23:59:59','yyyy-mm-dd hh24:mi:ss')  ";
		//		qry += "\n   group by a.custid;";
		//		qry += "\n   v_row cur_wareout%rowtype; ";
		//		qry += "\n begin ";
		//		qry += "\n   for v_row in cur_wareout loop ";
		//		qry += "\n     update customer set xsnum=v_row.num where custid=v_row.custid; ";
		//		qry += "\n       v_count:=v_count+1; ";
		//		qry += "\n       if v_count>1000 then  ";
		//		qry += "\n         commit; ";
		//		qry += "\n         v_count:=0; ";
		//		qry += "\n       end if; ";
		//		qry += "\n   end loop; ";
		//		qry += "\n end; ";
		//		qry += "\n commit; ";
		//
		//		// 统计商品库存
		//		qry += "\n v_count:=0;";
		//		qry += "\n declare cursor cur_data is ";
		//		qry += "\n   select wareid,sum(amount) as amount from waresum where daystr=v_nowdate";
		//		qry += "\n   group by wareid;";
		//		qry += "\n v_row cur_data%rowtype;";
		//		qry += "\n begin";
		//		qry += "\n    for v_row in cur_data loop";
		//		qry += "\n        update warecode set kcamount=v_row.amount where wareid=v_row.wareid;";
		//		qry += "\n        v_count:=v_count+1;";
		//		qry += "\n        if v_count>1000 then";
		//		qry += "\n           v_count:=0;";
		//		qry += "\n           commit;";
		//		qry += "\n        end if;";
		//		qry += "\n    end loop;";
		//		qry += "\n end;";
		//		qry += "\n commit; ";
		//
		//		// 处理过期优惠券
		//		qry += "\n update Housecoupon set statetag=2 where to_char(sysdate,'yyyy-mm-dd')>to_char(overdate,'yyyy-mm-dd');   ";
		//		qry += "\n commit; ";
		//
		//		// --更新商品上市日期
		//		qry += "\n  v_count:=0;";
		//		qry += "\n  declare cursor cur_warecode is  ";
		//		qry += "\n  select wareid,min(ssdate) as ssdate from ( ";
		//		qry += "\n  select b.wareid,to_char(min(a.notedate),'yyyy-mm-dd') as ssdate from wareinh a join wareinm b on a.accid=b.accid and a.noteno=b.noteno and a.statetag=1 ";
		//		qry += "\n  where exists (select 1 from warecode c where b.wareid=c.wareid and (length(c.ssdate)=0 or c.ssdate is null)) ";
		//		qry += "\n  group by b.wareid ";
		//		qry += "\n  union all ";
		//		qry += "\n  select b.wareid,to_char(min(a.notedate),'yyyy-mm-dd') as ssdate from firsthouseh a join firsthousem b on a.accid=b.accid and a.noteno=b.noteno and a.statetag=1 ";
		//		qry += "\n  where exists (select 1 from warecode c where b.wareid=c.wareid and (length(c.ssdate)=0 or c.ssdate is null)) ";
		//		qry += "\n  group by b.wareid ";
		//		qry += "\n  ) group by wareid ; ";
		//		qry += "\n    v_row cur_warecode%rowtype; ";
		//		qry += "\n  begin ";
		//		qry += "\n    for v_row in cur_warecode loop ";
		//		qry += "\n       update warecode set ssdate=v_row.ssdate where wareid=v_row.wareid; ";
		//		qry += "\n       v_count:=v_count+1; ";
		//		qry += "\n       if v_count>1000 then  ";
		//		qry += "\n          commit; ";
		//		qry += "\n          v_count:=0; ";
		//		qry += "\n       end if; ";
		//		qry += "\n    end loop; ";
		//
		//		qry += "\n  end; ";
		//		qry += "\n  commit; ";
		//
		//		// --更新店铺商品上货日期
		//		qry += "\n  v_count:=0;";
		//		qry += "\n  declare cursor cur_warecode is  ";
		//		qry += "\n  select houseid,wareid,min(ssdate) as shdate from ( ";
		//		qry += "\n  select a.houseid,b.wareid,to_char(min(a.notedate),'yyyy-mm-dd') as ssdate from wareinh a join wareinm b on a.accid=b.accid and a.noteno=b.noteno and a.statetag=1 ";
		//		// qry += " where not exists (select 1 from housewaredate c where
		//		// a.houseid=c.houseid and b.wareid=c.wareid ) ";
		//		qry += "\n  where not exists (select 1 from housesaleprice c where a.houseid=c.houseid and b.wareid=c.wareid  and not c.shdate is null) ";
		//		qry += "\n  group by a.houseid,b.wareid ";
		//		qry += "\n  union all ";
		//		qry += "\n  select a.houseid,b.wareid,to_char(min(a.notedate),'yyyy-mm-dd') as ssdate from firsthouseh a join firsthousem b on a.accid=b.accid and a.noteno=b.noteno and a.statetag=1 ";
		//		// qry += " where not exists (select 1 from housewaredate c where
		//		// a.houseid=c.houseid and b.wareid=c.wareid) ";
		//		qry += "\n  where not exists (select 1 from housesaleprice c where  a.houseid=c.houseid and b.wareid=c.wareid and not c.shdate is null) ";
		//		qry += "\n  group by a.houseid,b.wareid ";
		//		qry += "\n  union all ";
		//		qry += "\n  select a.houseid,b.wareid,to_char(min(a.notedate),'yyyy-mm-dd') as ssdate from allotinh a join allotinm b on a.accid=b.accid and a.noteno=b.noteno and a.statetag=1 ";
		//		// qry += " where not exists (select 1 from housewaredate c where
		//		// a.houseid=c.houseid and b.wareid=c.wareid) ";
		//		qry += "\n  where not exists (select 1 from housesaleprice c where  a.houseid=c.houseid and b.wareid=c.wareid and not c.shdate is null) ";
		//		qry += "\n  group by a.houseid,b.wareid ";
		//		qry += "\n  ) group by houseid,wareid; ";
		//		qry += "\n    v_row cur_warecode%rowtype; ";
		//		qry += "\n  begin ";
		//		qry += "\n    for v_row in cur_warecode loop ";
		//		qry += "\n       select count(*) into v_count1 from housesaleprice where houseid=v_row.houseid and wareid=v_row.wareid;";
		//		qry += "\n       if v_count1=0 then";
		//		qry += "\n          insert into housesaleprice (houseid,wareid,shdate) ";
		//		qry += "\n          values (v_row.houseid,v_row.wareid,v_row.shdate); ";
		//		qry += "\n       else ";
		//		qry += "\n          update housesaleprice set shdate=v_row.shdate where houseid=v_row.houseid and wareid=v_row.wareid;";
		//		qry += "\n       end if;";
		//		// qry += " insert into housesaleprice (houseid,wareid,shdate) ";
		//		// qry += " values (v_row.houseid,v_row.wareid,v_row.shdate); ";
		//		qry += "\n       v_count:=v_count+1; ";
		//		qry += "\n       if v_count>1000 then  ";
		//		qry += "\n          commit; ";
		//		qry += "\n          v_count:=0; ";
		//		qry += "\n       end if; ";
		//		qry += "\n    end loop; ";
		//		qry += "\n  end; ";
		//		qry += "\n  commit;   ";
		//		qry += "\n  p_dataprocess; "; // 处理编码使用状态
		//		qry += "\n end;";
		//		LogUtils.LogDebugWrite("passday", qry);
		//		DbHelperSQL db = new DbHelperSQL();
		//		int ret = db.ExecuteSql(qry, 3600);// 60分钟
		//		if (ret < 0) {
		//			errmess = db.getErrmess();
		//			return 0;
		//		} else {
		//			errmess = "操作成功";
		//			return 1;
		//		}
	}

	// 更改建账日期
	public int doChangeAccbegindate(String accdate, String Lastop, int housecostbj) {

		if (Begindate.compareTo(accdate) <= 0) {
			errmess = "新的建账日期必须在原建账日期" + accdate + "之后！";
			return 0;
		}

		// begindate
		/*
		 * 将指定建账日期前一天的库存，往来款余额作为期初数据生成建账日期的单据
		 */

		String qry = "begin";
		qry += "\n   p_changeaccbegindate(" + Accid + ",'" + Begindate + "','" + Lastop + "'," + housecostbj + ");";
		qry += "\n end;";

		//		String qry = "declare ";
		//		qry += "\n  v_accid number(10); ";
		//		qry += "\n  v_begindate varchar2(10); ";
		//		qry += "\n  v_begindate0 varchar2(10); ";
		//		qry += "\n  v_houseid number(10); ";
		//		qry += "\n  v_houseid0 number(10); ";
		//		qry += "\n  v_custid number(10); ";
		//		qry += "\n  v_provid number(10); ";
		//		qry += "\n  v_wareid number(10); ";
		//		qry += "\n  v_colorid number(10); ";
		//		qry += "\n  v_sizeid number(10); ";
		//		qry += "\n  v_amount number; ";
		//		qry += "\n  v_price number; ";
		//		qry += "\n  v_curr number; ";
		//		qry += "\n  v_totalcurr number; ";
		//		qry += "\n  v_totalamt number; ";
		//		qry += "\n  v_count number; ";
		//		qry += "\n  v_noteno varchar2(20); ";
		//		qry += "\n  v_lastop varchar2(20); ";
		//		qry += "\n  v_sysdate date; ";
		//		qry += "\n begin ";
		//
		//		qry += "\n    v_accid:=" + Accid + "; ";
		//		qry += "\n    v_lastop:='" + Lastop + "'; ";
		//		qry += "\n    v_sysdate:=sysdate; ";
		//		qry += "\n    v_begindate:='" + Begindate + "'; ";
		//		qry += "\n    v_begindate0:=to_char(to_date(v_begindate,'yyyy-mm-dd')-1,'yyyy-mm-dd'); ";
		//
		//		// --结转期初库存
		//		qry += "\n  declare cursor cur_firsthouse is ";
		//		qry += "\n    select a.houseid,a.wareid,a.colorid,a.sizeid,a.amount,case when c.costprice is null then b.entersale else c.costprice end as price  ";
		//		qry += "\n    from waresum a join warecode b on a.wareid=b.wareid ";
		//		qry += "\n    left outer join warecost c on a.wareid=c.wareid and a.daystr=c.daystr";
		//		if (housecostbj == 1)// 分店铺计算成本
		//			qry += " and a.houseid=c.houseid ";
		//		else
		//			qry += " and c.houseid=0";
		//		qry += "\n    where a.daystr=v_begindate0 and b.accid=v_accid  ";
		//		qry += "\n    order by a.houseid; ";
		//		qry += "\n  begin ";
		//		qry += "\n     open cur_firsthouse; ";
		//		qry += "\n     fetch cur_firsthouse  into v_houseid,v_wareid,v_colorid,v_sizeid,v_amount,v_price; ";
		//		qry += "\n     while cur_firsthouse%found loop ";
		//		qry += "\n       v_houseid0:=v_houseid; ";
		//		// --生成期初入库记录文件
		//		qry += "\n       select 'QR'||To_Char(v_sysdate,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from firsthouseh ";
		//		qry += "\n       where accid=v_accid and LENGTH(noteno)=15 and substr(noteno,1,10)= 'QR'||To_Char(v_sysdate,'yyyymmdd'); ";
		//		qry += "\n       insert into firsthouseh (id, accid,noteno,notedate,houseid,statetag,totalamt,totalcurr,remark,handno,operant,checkman,lastdate) ";
		//		qry += "\n       values (FIRSTHOUSEH_ID.NEXTVAL,v_accid,v_noteno,to_date(v_begindate,'yyyy-mm-dd'),v_houseid0,1,0,0,'重设建账日期自动生成','',v_lastop,'',sysdate);  ";
		//		qry += "\n       v_totalcurr:=0; ";
		//		qry += "\n       v_totalamt:=0; ";
		//		qry += "\n       while cur_firsthouse%found and v_houseid=v_houseid0 loop ";
		//		qry += "\n          v_curr:=round(v_amount*v_price,2);";
		//		qry += "\n          insert into firsthousem (id,accid,noteno,wareid,colorid,sizeid,amount,price,curr,remark0) ";
		//		qry += "\n          values (firsthousem_id.nextval,v_accid,v_noteno,v_wareid,v_colorid,v_sizeid ,v_amount,v_price,v_curr ,''); ";
		//		qry += "\n          v_totalcurr:=v_totalcurr+v_curr; ";
		//		qry += "\n          v_totalamt:=v_totalamt+v_amount; ";
		//		qry += "\n          fetch cur_firsthouse  into v_houseid,v_wareid,v_colorid,v_sizeid,v_amount,v_price; ";
		//		qry += "\n       end loop; ";
		//		qry += "\n       update firsthouseh set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=v_accid and noteno=v_noteno; ";
		//		qry += "\n       commit; ";
		//		qry += "\n     end loop; ";
		//		qry += "\n     close cur_firsthouse; ";
		//		qry += "\n  end; ";
		//		qry += "\n  commit; ";
		//
		//		// qry += "\n delete from waresum a where a.daystr=v_begindate0 ";
		//		// qry += "\n and exists (select 1 from warecode b where b.accid=v_accid
		//		// and a.wareid=b.wareid); ";
		//		//删除建账日之前的库存
		//
		//		qry += "\n    declare  ";
		//		qry += "\n       cursor mycursor is ";
		//		// qry += "\n SELECT ROWID FROM waresum WHERE daystr='2016-03-29' order
		//		// by rowid; ";// ----按ROWID排序的Cursor，删除条件是XXX=XXXX，根据实际情况来定。
		//		qry += "\n      SELECT  ROWID from waresum a where a.daystr<v_begindate0  ";
		//		qry += "\n      and exists (select 1 from warecode b where b.accid=v_accid and a.wareid=b.wareid) ";
		//		qry += "\n      order by rowid; ";
		//		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		//		qry += "\n       v_rowid   rowid_table_type; ";
		//		qry += "\n    BEGIN ";
		//		qry += "\n       open mycursor; ";
		//		qry += "\n       loop ";
		//		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 5000;  ";// --------每次处理5000行，也就是每5000行一提交
		//		qry += "\n          exit when v_rowid.count=0; ";
		//		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		//		qry += "\n          delete from waresum  where rowid=v_rowid(i); ";
		//		qry += "\n          commit; ";
		//		qry += "\n        end loop; ";
		//		qry += "\n       close mycursor; ";
		//		qry += "\n    END; ";
		//		qry += "\n  commit; ";
		//
		//		// 结转期初应付款
		//
		//		qry += "\n  declare cursor cur_firstpaycurr is ";
		//		qry += "\n    select a.provid,a.houseid,a.curr  ";
		//		qry += "\n    from paybal a join provide b on a.provid=b.provid ";
		//		qry += "\n    where a.daystr=v_begindate0 and b.accid=v_accid;  ";
		//		qry += "\n  begin ";
		//		qry += "\n     open cur_firstpaycurr; ";
		//		qry += "\n     fetch cur_firstpaycurr  into v_provid,v_houseid,v_curr; ";
		//		qry += "\n     while cur_firstpaycurr%found loop ";
		//		// 生成期初应付款记录
		//		qry += "\n       select 'QF'||To_Char(v_sysdate,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from FIRSTPAYCURR ";
		//		qry += "\n       where accid=v_accid and LENGTH(noteno)=15 and substr(noteno,1,10)= 'QF'||To_Char(v_sysdate,'yyyymmdd'); ";
		//		qry += "\n       insert into FIRSTPAYCURR (id, accid,noteno,notedate,provid,houseid,curr,remark,handno,statetag,operant,checkman,lastop,lastdate) ";
		//		qry += "\n       values (FIRSTPAYCURR_ID.NEXTVAL,v_accid,v_noteno,to_date(v_begindate,'yyyy-mm-dd'),v_provid,v_houseid,v_curr,'重设建账日期自动生成','',1,v_lastop,'',v_lastop,sysdate);  ";
		//		qry += "\n       fetch cur_firstpaycurr  into v_provid,v_houseid,v_curr; ";
		//		qry += "\n     end loop; ";
		//		qry += "\n     close cur_firstpaycurr; ";
		//		qry += "\n  end; ";
		//		qry += "\n  commit; ";
		//
		//		// qry += "\n delete from paybal a where a.daystr=v_begindate0 ";
		//		// qry += "\n and exists (select 1 from provide b where b.accid=v_accid
		//		// and a.provid=b.provid); ";
		//		//删除建账日之前的应付款余额
		//		qry += "\n    declare  ";
		//		qry += "\n       cursor mycursor is ";
		//		qry += "\n       SELECT  ROWID  from paybal a where a.daystr<=v_begindate0  ";
		//		qry += "\n       and exists (select 1 from provide b where b.accid=v_accid and a.provid=b.provid)  ";
		//		qry += "\n       order by rowid; ";
		//
		//		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		//		qry += "\n       v_rowid   rowid_table_type; ";
		//		qry += "\n    BEGIN ";
		//		qry += "\n       open mycursor; ";
		//		qry += "\n       loop ";
		//		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 5000;  ";// --------每次处理5000行，也就是每5000行一提交
		//		qry += "\n          exit when v_rowid.count=0; ";
		//		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		//		qry += "\n          delete from paybal  where rowid=v_rowid(i); ";
		//		qry += "\n          commit; ";
		//		qry += "\n        end loop; ";
		//		qry += "\n       close mycursor; ";
		//		qry += "\n    END; ";
		//
		//		qry += "\n   commit; ";
		//
		//		// --结转期初应收款
		//
		//		qry += "\n  declare cursor cur_firstincomecurr is ";
		//		qry += "\n    select a.custid,a.houseid,a.curr  ";
		//		qry += "\n    from incomebal a join customer b on a.custid=b.custid ";
		//		qry += "\n    where a.daystr=v_begindate0 and b.accid=v_accid;  ";
		//		qry += "\n  begin ";
		//		qry += "\n     open cur_firstincomecurr; ";
		//		qry += "\n     fetch cur_firstincomecurr  into v_custid,v_houseid,v_curr; ";
		//		qry += "\n     while cur_firstincomecurr%found loop ";
		//		// --生成期初应付款记录
		//		qry += "\n       select 'QS'||To_Char(v_sysdate,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from FIRSTINCOMECURR ";
		//		qry += "\n       where accid=v_accid and LENGTH(noteno)=15 and substr(noteno,1,10)= 'QS'||To_Char(v_sysdate,'yyyymmdd'); ";
		//		qry += "\n       insert into FIRSTINCOMECURR (id, accid,noteno,notedate,custid,houseid,curr,remark,handno,statetag,operant,checkman,lastop,lastdate) ";
		//		qry += "\n       values (FIRSTINCOMECURR_ID.NEXTVAL,v_accid,v_noteno,to_date(v_begindate,'yyyy-mm-dd'),v_custid,v_houseid,v_curr,'重设建账日期自动生成','',1,v_lastop,'',v_lastop,sysdate);  ";
		//		qry += "\n       fetch cur_firstincomecurr  into v_custid,v_houseid,v_curr; ";
		//		qry += "\n     end loop; ";
		//		qry += "\n     close cur_firstincomecurr; ";
		//		qry += "\n  end; ";
		//		qry += "\n  commit; ";
		//		// qry += "\n delete from incomebal a where a.daystr=v_begindate0 ";
		//		// qry += "\n and exists (select 1 from customer b where b.accid=v_accid
		//		// and a.custid=b.custid); ";
		//		//删除建账日之前的应收款余额
		//		qry += "\n    declare  ";
		//		qry += "\n       cursor mycursor is ";
		//		qry += "\n       SELECT  ROWID  from incomebal a where a.daystr<=v_begindate0  ";
		//		qry += "\n       and exists (select 1 from customer b where b.accid=v_accid and a.custid=b.custid)  ";
		//
		//		qry += "\n       order by rowid; ";
		//
		//		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		//		qry += "\n       v_rowid   rowid_table_type; ";
		//		qry += "\n    BEGIN ";
		//		qry += "\n       open mycursor; ";
		//		qry += "\n       loop ";
		//		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 5000;  ";// --------每次处理5000行，也就是每5000行一提交
		//		qry += "\n          exit when v_rowid.count=0; ";
		//		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		//		qry += "\n          delete from incomebal  where rowid=v_rowid(i); ";
		//		qry += "\n          commit; ";
		//		qry += "\n        end loop; ";
		//		qry += "\n       close mycursor; ";
		//		qry += "\n    END; ";
		//
		//		qry += "\n  commit; ";
		//
		//		//删除建账日之前的销售单据
		//		qry += "\n    declare  ";
		//		qry += "\n       cursor mycursor is ";
		//		qry += "\n       SELECT  ROWID  from wareouth a where a.notedate<=to_date(v_begindate0||' 23:59:59','yyyy-mm-dd hh24:mi:ss')  ";
		//		qry += "\n       order by rowid; ";
		//
		//		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		//		qry += "\n       v_rowid   rowid_table_type; ";
		//		qry += "\n    BEGIN ";
		//		qry += "\n       open mycursor; ";
		//		qry += "\n       loop ";
		//		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		//		qry += "\n          exit when v_rowid.count=0; ";
		//		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		//		qry += "\n          update wareouth set statetag=2,lastdate=sysdate where rowid=v_rowid(i); ";
		//		qry += "\n          commit; ";
		//		qry += "\n        end loop; ";
		//		qry += "\n       close mycursor; ";
		//		qry += "\n    END; ";
		//
		//		qry += "\n  commit; ";
		//
		//		//删除建账日之前的采购单据
		//		qry += "\n    declare  ";
		//		qry += "\n       cursor mycursor is ";
		//		qry += "\n       SELECT  ROWID  from wareinh a where a.notedate<=to_date(v_begindate0||' 23:59:59','yyyy-mm-dd hh24:mi:ss')  ";
		//		qry += "\n       order by rowid; ";
		//
		//		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		//		qry += "\n       v_rowid   rowid_table_type; ";
		//		qry += "\n    BEGIN ";
		//		qry += "\n       open mycursor; ";
		//		qry += "\n       loop ";
		//		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		//		qry += "\n          exit when v_rowid.count=0; ";
		//		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		//		qry += "\n          update wareinh set statetag=2,lastdate=sysdate where rowid=v_rowid(i); ";
		//		qry += "\n          commit; ";
		//		qry += "\n        end loop; ";
		//		qry += "\n       close mycursor; ";
		//		qry += "\n    END; ";
		//
		//		qry += "\n  commit; ";
		//
		//		// 更改商品使用标志为0
		//
		//		qry += "\n  declare cursor mycursor is  ";
		//		qry += "\n    SELECT  ROWID FROM warecode WHERE accid=v_accid and usedbj=1  order by rowid;  ";// ----按ROWID排序的Cursor，删除条件是XXX=XXXX，根据实际情况来定。
		//		qry += "\n    type rowid_table_type is  table  of rowid index by pls_integer; ";
		//		qry += "\n    v_rowid   rowid_table_type; ";
		//		qry += "\n  BEGIN ";
		//		qry += "\n     open mycursor; ";
		//		qry += "\n     loop ";
		//		qry += "\n        fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		//		qry += "\n        exit when v_rowid.count=0; ";
		//		qry += "\n        forall i in v_rowid.first..v_rowid.last ";
		//		qry += "\n        update  warecode set usedbj=0  where rowid=v_rowid(i); ";
		//		qry += "\n        commit; ";
		//		qry += "\n      end loop; ";
		//		qry += "\n     close mycursor; ";
		//		qry += "\n  END;  ";
		//		qry += "\n  commit; ";
		//
		//		// 更改颜色使用标志为0
		//
		//		qry += "\n  declare cursor mycursor is  ";
		//		qry += "\n    SELECT  ROWID FROM colorcode WHERE accid=v_accid and usedbj=1  order by rowid;  ";// ----按ROWID排序的Cursor，删除条件是XXX=XXXX，根据实际情况来定。
		//		qry += "\n    type rowid_table_type is  table  of rowid index by pls_integer; ";
		//		qry += "\n    v_rowid   rowid_table_type; ";
		//		qry += "\n  BEGIN ";
		//		qry += "\n     open mycursor; ";
		//		qry += "\n     loop ";
		//		qry += "\n        fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		//		qry += "\n        exit when v_rowid.count=0; ";
		//		qry += "\n        forall i in v_rowid.first..v_rowid.last ";
		//		qry += "\n        update  colorcode set usedbj=0  where rowid=v_rowid(i); ";
		//		qry += "\n        commit; ";
		//		qry += "\n      end loop; ";
		//		qry += "\n     close mycursor; ";
		//		qry += "\n  END;  ";
		//		qry += "\n  commit; ";
		//
		//		// 更改尺码使用标志为0
		//
		//		qry += "\n  declare cursor mycursor is  ";
		//		qry += "\n    SELECT  ROWID FROM sizecode WHERE accid=v_accid and usedbj=1  order by rowid;  ";// ----按ROWID排序的Cursor，删除条件是XXX=XXXX，根据实际情况来定。
		//		qry += "\n    type rowid_table_type is  table  of rowid index by pls_integer; ";
		//		qry += "\n    v_rowid   rowid_table_type; ";
		//		qry += "\n  BEGIN ";
		//		qry += "\n     open mycursor; ";
		//		qry += "\n     loop ";
		//		qry += "\n        fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		//		qry += "\n        exit when v_rowid.count=0; ";
		//		qry += "\n        forall i in v_rowid.first..v_rowid.last ";
		//		qry += "\n        update  sizecode set usedbj=0  where rowid=v_rowid(i); ";
		//		qry += "\n        commit; ";
		//		qry += "\n      end loop; ";
		//		qry += "\n     close mycursor; ";
		//		qry += "\n  END;  ";
		//		qry += "\n commit; ";
		//		qry += "\n  update accreg set begindate=to_date(v_begindate,'yyyy-mm-dd'),lastdate=sysdate";
		//		qry += "\n    ,calcdate=case when calcdate<=to_date(v_begindate,'yyyy-mm-dd') then to_date(v_begindate,'yyyy-mm-dd')-1 else calcdate end";
		//		qry += "\n  where accid=v_accid; ";
		//
		//		qry += "\n  insert into LOGRECORD (id,accid,progname,remark,lastop)";
		//		qry += "\n  values (LOGRECORD_id.nextval,v_accid,'更改建账日期',v_begindate,v_lastop);";
		//
		//		qry += "\n  commit; ";
		//		qry += "\n end; ";

		// DbHelperSQL db = new DbHelperSQL();
		DbHelperSQL db = new DbHelperSQL();
		// int ret = db.ExecuteSql(qry, 1800);
		if (db.ExecuteSql(qry, 3600) < 0) {
			errmess = db.getErrmess();
			return 0;
		} else {
			errmess = "操作成功";
			return 1;
		}
	}

	// 校验注册企业账号是否有效
	public int doValid() {

		if (Accname.trim().equals("")) {
			errmess = "注册账号不允许为空！";
			return 0;
		}
		if (Accname.length() < 6) {
			errmess = "注册账号(" + Accname + ")长度不足6位！";
			return 0;
		}
		if (!Func.isNumAndEnCh(Accname)) {
			errmess = "注册账号只能由字母或数字组成！";
			return 0;
		}

		String qry = "select accid from accreg where accname='" + Accname + "'";
		if (DbHelperSQL.Exists(qry)) {
			errmess = "注册账号(" + Accname + ")已存在，请重新输入！";
			return 0;
		}

		errmess = "注册账号(" + Accname + ")可用";
		return 1;
	}

	public int doFindbymobile() {
		if (!Func.isMobile(Mobile)) {
			errmess = "移动电话无效！";
			return 0;
		}
		if (!Func.isNumAndEnCh(Smspwd)) {
			errmess = "密码格式无效！";
			return 0;
		}
		// String qry = "select a.accid,a.accname,b.password from accreg a ";
		// qry += " join employe b on a.accid=b.accid where a.mobile='" + Mobile
		// + "' and b.statetag=1 and b.levelid=0";
		String qry = "select a.accid,b.accname,a.password,b.qybj from employe a join accreg b on a.accid=b.accid";
		qry += " where a.statetag=1 and a.levelid=0 and rownum=1 and b.mobile='" + Mobile + "'";
		if (Qybj != null)
			qry += " and b.qybj=" + Qybj;
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "移动电话" + Mobile + "未注册！";
			return 0;
		}

		qry = "select smspwd from (select smspwd from smsrecord where mobile='" + Mobile + "' and floor(to_number(sysdate-senddate)*24*60)<=5 order by senddate desc )  where rownum=1";
		String smspwd1 = DbHelperSQL.RunSql(qry).toString();

		if (smspwd1.compareTo(Smspwd) != 0 || Smspwd.equals("")) {
			// WriteResult("0", "短信校验码无效，请重新输入！?" + smspwd1 + " = " + smspwd);
			errmess = "短信校验码无效，请重新输入！";
			return 0;

		}

		errmess = "\"msg\":\"操作成功！\",\"ACCID\":\"" + tb.getRow(0).get("ACCID").toString() //
				+ "\",\"ACCNAME\":\"" + tb.getRow(0).get("ACCNAME").toString() //
				+ "\",\"PASSWORD\":\"" + tb.getRow(0).get("PASSWORD").toString() + "\"";

		return 1;
	}

	// 取所有参数
	public int GetAllcs(int fs) {
		String responsestr = "{\"result\":1,\"msg\":\"操作成功！\",";
		String qry = "";
		String ss = "";
		Table tb = new Table();
		if (fs == 0) {
			// qry = "select uvalue from uparameter where accid=" + accid + "
			// and usection='OPTIONS' and usymbol='QXPUBLIC'";
			qry = "select uqxcs from accreg where accid=" + Accid;
			ss = DbHelperSQL.RunSql(qry).toString() + "0000000000000000000000000000000000000000000000000000";
			responsestr += "\"QXPUBLIC\": \"" + Func.subString(ss, 1, 40) + "\"";

			// 取默认客户
			qry = "select uvalue from uparameter where accid=" + Accid + " and usection='OPTIONS' and usymbol='DEFAULTCUST'";
			ss = DbHelperSQL.RunSql(qry).toString().trim();
			if (ss == null || ss == "" || !Func.isNumber(ss)) {
				responsestr += ",\"CUSTID\": \"\"" + ",\"CUSTNAME\":\"\"" //
						+ ",\"DISCOUNT\":\"1\"" + ",\"PRICETYPE\":\"0\"";

			} else {
				qry = "select custid,custname,pricetype,discount from customer where accid=" + Accid + " and custid=" + ss;
				tb = DbHelperSQL.Query(qry).getTable(1);
				if (tb.getRowCount() == 0) {
					responsestr += ",\"CUSTID\": \"\"" + ",\"CUSTNAME\":\"\"" + ",\"DISCOUNT\":\"1\"" + ",\"PRICETYPE\":\"0\"";
				} else {
					responsestr += ",\"CUSTID\": \"" + tb.getRow(0).get("CUSTID").toString() + "\"" //
							+ ",\"CUSTNAME\":\"" + tb.getRow(0).get("CUSTNAME").toString() + "\"" //
							+ ",\"DISCOUNT\":\"" + tb.getRow(0).get("DISCOUNT").toString() + "\"" //
							+ ",\"PRICETYPE\":\"" + tb.getRow(0).get("PRICETYPE").toString() + "\"";
				}
			}
			responsestr += ",";
		}

		// 取商品默认颜色
		qry = "select uvalue from uparameter where accid=" + Accid + " and usection='OPTIONS' and usymbol='DEFAULTCOLOR'";
		ss = DbHelperSQL.RunSql(qry).toString();
		if (ss == null || ss == "" || !Func.isNumber(ss)) {
			responsestr += "\"COLORID\": \"\"" + ",\"COLORNAME\":\"\"";

		} else {
			qry = "select colorid,colorname from colorcode where accid=" + Accid + " and colorid=" + ss;
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() == 0) {
				responsestr += "\"COLORID\": \"\"" + ",\"COLORNAME\":\"\"";
			} else {
				responsestr += "\"COLORID\": \"" + tb.getRow(0).get("COLORID").toString() + "\"" //
						+ ",\"COLORNAME\":\"" + tb.getRow(0).get("COLORNAME").toString() + "\"";
			}
		}
		// 取商品尺码颜色
		qry = "select uvalue from uparameter where accid=" + Accid + " and usection='OPTIONS' and usymbol='DEFAULTSIZE'";
		ss = DbHelperSQL.RunSql(qry).toString();
		if (ss == null || ss == "") {
			responsestr += ",\"SIZEGROUPNO\":\"\"";

		} else {
			qry = "select groupno from sizecode where accid=" + Accid + " and groupno='" + ss + "'";
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() == 0) {
				responsestr += ",\"SIZEGROUPNO\":\"\"";
			} else {
				responsestr += ",\"SIZEGROUPNO\": \"" + tb.getRow(0).get("GROUPNO").toString() + "\"";
			}
		}

		// if (fs == 0) //取尺码组参数
		// {

		qry = "SELECT groupno, TRANSLATE (LTRIM (text, '~'), '*~', '*,') sizelist";
		qry += " FROM (SELECT ROW_NUMBER () OVER (PARTITION BY groupno ORDER BY groupno,lvl DESC) rn,groupno, text";
		qry += " FROM (SELECT groupno, LEVEL lvl, SYS_CONNECT_BY_PATH (sizename,'~') text";
		qry += " FROM (SELECT groupno, sizename,sizeno, ROW_NUMBER () OVER (PARTITION BY groupno ORDER BY groupno,sizeno,sizeid) x";
		qry += " FROM sizecode where accid=" + Accid + " and noused=0 and statetag=1 and accid1=0 ORDER BY groupno) a";
		qry += " CONNECT BY groupno = PRIOR groupno AND x - 1 = PRIOR x))";
		qry += " WHERE rn = 1";
		qry += " ORDER BY groupno";
		tb = DbHelperSQL.Query(qry).getTable(1);
		String fh = "";
		int rs = tb.getRowCount();

		responsestr += ",\"sizegrouplist\":[";

		for (int i = 0; i < rs; i++) {
			responsestr += fh + "{\"GROUPNO\":\"" + tb.getRow(i).get("GROUPNO").toString() + "\"," //
					+ "\"SIZELIST\":\"" + tb.getRow(i).get("SIZELIST").toString() + "\"}";
			fh = ",";

		}
		responsestr += "]}";
		errmess = responsestr;
		return 1;
	}

	// 取用户参数
	public int GetUqxcs() {
		String qry = "select uqxcs from accreg where accid=" + Accid;
		errmess = Func.strLeft(DbHelperSQL.RunSql(qry).toString() + "0000000000000000000000000000000000000000", 40);
		return 1;
	}

	// 用户写权限参数
	public int WriteUqxcs(int locate, String value) {
		if (locate == 0) {
			errmess = "位置参数无效" + locate;
			return 0;
		}
		String qry = "begin ";
		qry += "   update accreg set uqxcs=substr(substr(uqxcs||'0000000000000000000000000000000000000000',1," + locate + "-1)||'" + value//
				+ "'||substr(uqxcs||'0000000000000000000000000000000000000000'," + locate + "+length('" + value + "')),1,40) where accid=" + Accid + "; ";
		qry += "end; ";

		int ret = DbHelperSQL.ExecuteSql(qry);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 重置密码
	public int doReset(String oldpwd) {
		if (Accid < 0) {
			errmess = "账户id无效";
			return 0;
		}
		if (oldpwd.equals("") || Password.equals("")) {
			errmess = "密码参数无效";
			return 0;

		}
		String qry = "declare ";
		qry += "\n    v_oldpwd1 varchar2(60);";
		qry += "\n    v_retcs varchar2(200);";
		qry += "\n begin";

		qry += "\n   select password into v_oldpwd1 from employe where accid=" + Accid + " and statetag=1 and levelid=0 and rownum=1;";
		qry += "\n   if v_oldpwd1<>'" + oldpwd + "' then";
		qry += "\n      v_retcs:='0密码错误！';";
		qry += "\n      goto exit;";
		qry += "\n   end if;";
		//
		qry += "\n   update accreg set password='" + Password + "' where accid=" + Accid + ";";

		qry += "\n   update employe set password='" + Password + "' where accid=" + Accid + " and statetag=1 and levelid=0;";

		qry += "\n   v_retcs:='1操作成功！';";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";
		// System.out.print(qry);
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

	// 客户准入
	public int doCustaccess() {
		if (Func.isNull(Dksurname)) {
			//			errmess = "申请人姓名不是一个有效值！";
			errmess = "\"code\":\"-1\",\"msg\":\"申请人姓名不是一个有效值！\"";
			return 0;
		}
		if (Func.isNull(Dkidno)) {
			//			errmess = "身份证号不是一个有效值！";
			errmess = "\"code\":\"-1\",\"msg\":\"身份证号不是一个有效值！\"";
			return 0;
		}
		if (Func.isNull(Dkmobile)) {
			//			errmess = "移动电话不能为空！";
			errmess = "\"code\":\"-1\",\"msg\":\"移动电话不能为空！\"";
			return 0;
		}
		if (!Func.isMobile(Dkmobile)) {
			//			errmess = "移动电话不是一个有效值！";
			errmess = "\"code\":\"-1\",\"msg\":\"移动电话不是一个有效值！\"";
			return 0;
		}
		//    System.out.println("000000000000000000000");
		String qry = "";
		//		String qry = "select count(*) as num from accreg where accid<>" + Accid + " and dksurname='" + Dksurname + "' and dkidno='" + Dkidno + "' and rownum=1";
		//
		//		if (DbHelperSQL.GetSingleCount(qry) > 0) {
		//			//			errmess = "贷款人信息已绑定其他账户，请重新输入！";
		//			errmess = "\"code\":\"-1\",\"msg\":\"贷款人信息已绑定其他账户，请重新输入！\"";
		//			return 0;
		//		}

		// System.out.println("begin....0000000");

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("certNo", Dkidno);
		map.put("certType", "101");
		map.put("custName", Dksurname);
		map.put("mobile", Dkmobile);

		// 客户准入申请
		//		System.out.println("begin....2222222");
		JSONObject resobj = SilverPassHttpsUtils.httpPost("/cust/access.do", map);
		//		System.out.println("1111:" + resobj.toString());
		JSONObject body = JSONObject.fromObject(resobj.getString("body"));
		if (!body.has("code")) {
			// errmess = "贷款接口调用异常！";
			errmess = "\"code\":\"-1\",\"msg\":\"贷款接口调用异常!\"";
			return 0;
		}
		String code = body.getString("code");
		if (code.equals("9999")) { // 不成功
			// errmess = body.getString("message");
			errmess = "\"code\":\"" + code + "\",\"msg\":\"" + body.getString("message") + "\"";
			return 0;
		}
		// 准入成功
		qry = " begin";
		qry += "\n   update accreg set dksurname='',dkidno='',dkmobile='' where accid<>" + Accid + " and dksurname='" + Dksurname + "' and dkidno='" + Dkidno + "';";
		qry += "\n   update accreg set lastdate=sysdate";
		qry += "  ,dksurname='" + Dksurname + "'";
		qry += "  ,dkidno='" + Dkidno + "'";
		qry += "  ,dkmobile='" + Dkmobile + "'";
		qry += "  ,dkok=" + Dkok;
		qry += "  where accid=" + Accid + ";";
		qry += "\n end;";
		//		System.out.println(qry);
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "\"code\":\"" + code + "\",\"msg\":\"" + body.getString("message") + "\"";
			// errmess = "操作成功！";
			return 1;
		}
	}

}