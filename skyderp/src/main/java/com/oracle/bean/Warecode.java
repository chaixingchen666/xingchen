package com.oracle.bean;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
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
import com.flyang.main.pFunc;
//import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
//import net.sf.json.JSONObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-02-19 12:52:20
//*************************************
public class Warecode implements java.io.Serializable {
	private Long Wareid;
	private String Wareno;
	private String Warename;
	private String Units;
	private String Model;
	private String Shortname;
	private Long Brandid;
	private String Seasonname;
	private Long Accid;
	private Long Typeid;
	private String Prodyear;
	private String Prodno;
	private Float Entersale;
	private Float Retailsale;
	private Float Sale1;
	private Float Sale2;
	private Float Sale3;
	private Float Sale4;
	private Float Sale5;
	private String Remark;
	private String Sizegroupno;
	private String Useritem1;
	private String Useritem2;
	private String Useritem3;
	private String Useritem4;
	private String Useritem5;
	private Integer Lyfs;
	private Long Accid1;
	private Long Wareid1;
	private Integer Noused;
	private Integer Statetag;
	private Integer Downenabled;
	private String Lastop;
	private String Lastdate;
	private String Imagename;
	private Long Colorid0;
	private Long Sizeid0;
	private String Imagename0;
	private String Gbbar;
	private Float Xsamount;
	private String Xdsm;
	private String Zxbz;
	private String Ssdate;
	private String Dj;
	private String Jlman;
	private String Sjman;
	private Long Provid;
	private String Colorlist;
	private Integer Usedbj;
	private String Locale;
	private Float Checksale;
	private Long Areaid;
	private Long Kcamount;
	private String Wareremark;
	private String Retdatestr;
	private Long Waveid;
	private Integer Tjtag;
	private String Salerange;
	private String errmess = "";
	private Long Houseid;

	private String Safety;
	private String Provname = "";
	private String Brandname = "";
	private String Typename = "";
	private String Typename0 = "";// 大类
	private String Areaname = "";
	private String Barcode = "";
	private String Wavename = "";

	public String getBarcode() {
		return Barcode;
	}

	public void setBarcode(String barcode) {
		Barcode = barcode;
	}

	private int Autowareno = 0;// 自动产生货号

	private int Barfs = 0;// 条码产生方式

	public Long getHouseid() {
		return Houseid;
	}

	public void setHouseid(Long houseid) {
		Houseid = houseid;
	}

	public void setAutowareno(int autowareno) {
		Autowareno = autowareno;
	}

	public void setBarfs(int barfs) {
		Barfs = barfs;
	}

	public Float getMaxsale() {
		return Maxsale;
	}

	public void setMaxsale(Float maxsale) {
		Maxsale = maxsale;
	}

	public Float getMinsale() {
		return Minsale;
	}

	public void setMinsale(Float minsale) {
		Minsale = minsale;
	}

	private Float Maxsale = (float) -1;
	private Float Minsale = (float) -1;

	public String getErrmess() {
		return errmess;
	}

	public void setWareid(Long wareid) {
		this.Wareid = wareid;
	}

	public Long getWareid() {
		return Wareid;
	}

	public void setWareno(String wareno) {
		this.Wareno = wareno;
	}

	public String getWareno() {
		if (Wareno == null)
			return "";
		return Wareno;
	}

	public void setWarename(String warename) {
		this.Warename = warename;
	}

	public String getWarename() {
		if (Warename == null)
			return "";
		return Warename;
	}

	public void setUnits(String units) {
		this.Units = units;
	}

	public String getUnits() {
		return Units;
	}

	public void setShortname(String shortname) {
		this.Shortname = shortname;
	}

	public String getShortname() {
		return Shortname;
	}

	public void setBrandid(Long brandid) {
		this.Brandid = brandid;
	}

	public Long getBrandid() {
		if (Brandid == null)
			return (long) -1;
		return Brandid;
	}

	public void setSeasonname(String seasonname) {
		this.Seasonname = seasonname;
	}

	public String getSeasonname() {
		return Seasonname;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setTypeid(Long typeid) {
		this.Typeid = typeid;
	}

	public Long getTypeid() {
		if (Typeid == null)
			return (long) -1;
		return Typeid;
	}

	public void setProdyear(String prodyear) {
		this.Prodyear = prodyear;
	}

	public String getProdyear() {
		return Prodyear;
	}

	public void setProdno(String prodno) {
		this.Prodno = prodno;
	}

	public String getProdno() {
		return Prodno;
	}

	public void setEntersale(Float entersale) {
		this.Entersale = entersale;
	}

	public Float getEntersale() {
		if (Entersale == null)
			return (float) 0;
		return Entersale;
	}

	public void setRetailsale(Float retailsale) {
		this.Retailsale = retailsale;
	}

	public Float getRetailsale() {
		if (Retailsale == null)
			return (float) 0;
		return Retailsale;
	}

	public void setSale1(Float sale1) {
		this.Sale1 = sale1;
	}

	public Float getSale1() {
		if (Sale1 == null)
			return (float) 0;
		return Sale1;
	}

	public void setSale2(Float sale2) {
		this.Sale2 = sale2;
	}

	public Float getSale2() {
		if (Sale2 == null)
			return (float) 0;
		return Sale2;
	}

	public void setSale3(Float sale3) {
		this.Sale3 = sale3;
	}

	public Float getSale3() {
		if (Sale3 == null)
			return (float) 0;
		return Sale3;
	}

	public void setSale4(Float sale4) {
		this.Sale4 = sale4;
	}

	public Float getSale4() {
		if (Sale4 == null)
			return (float) 0;
		return Sale4;
	}

	public void setSale5(Float sale5) {
		this.Sale5 = sale5;
	}

	public Float getSale5() {
		if (Sale5 == null)
			return (float) 0;
		return Sale5;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setSizegroupno(String sizegroupno) {
		this.Sizegroupno = sizegroupno;
	}

	public String getSizegroupno() {
		return Sizegroupno;
	}

	public void setUseritem1(String useritem1) {
		this.Useritem1 = useritem1;
	}

	public String getUseritem1() {
		return Useritem1;
	}

	public void setUseritem2(String useritem2) {
		this.Useritem2 = useritem2;
	}

	public String getUseritem2() {
		return Useritem2;
	}

	public void setUseritem3(String useritem3) {
		this.Useritem3 = useritem3;
	}

	public String getUseritem3() {
		return Useritem3;
	}

	public void setUseritem4(String useritem4) {
		this.Useritem4 = useritem4;
	}

	public String getUseritem4() {
		return Useritem4;
	}

	public void setUseritem5(String useritem5) {
		this.Useritem5 = useritem5;
	}

	public String getUseritem5() {
		return Useritem5;
	}

	public void setLyfs(Integer lyfs) {
		this.Lyfs = lyfs;
	}

	public Integer getLyfs() {
		return Lyfs;
	}

	public void setAccid1(Long accid1) {
		this.Accid1 = accid1;
	}

	public Long getAccid1() {
		return Accid1;
	}

	public void setWareid1(Long wareid1) {
		this.Wareid1 = wareid1;
	}

	public Long getWareid1() {
		return Wareid1;
	}

	public void setNoused(Integer noused) {
		this.Noused = noused;
	}

	public Integer getNoused() {
		if (Noused == null)
			Noused = 0;
		return Noused;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setDownenabled(Integer downenabled) {
		this.Downenabled = downenabled;
	}

	public Integer getDownenabled() {
		return Downenabled;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setLastdate(String lastdate) {
		this.Lastdate = lastdate;
	}

	public String getLastdate() {
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// return df.format(Lastdate);
		return Lastdate;
	}
	//
	// public void setMinsale(Float minsale) {
	// Minsale = minsale;
	// }
	//
	// public void setMaxsale(Float maxsale) {
	// Maxsale = maxsale;
	// }

	public void setImagename(String imagename) {
		this.Imagename = imagename;
	}

	public String getImagename() {
		return Imagename;
	}

	public void setColorid0(Long colorid0) {
		this.Colorid0 = colorid0;
	}

	public Long getColorid0() {
		return Colorid0;
	}

	public void setSizeid0(Long sizeid0) {
		this.Sizeid0 = sizeid0;
	}

	public Long getSizeid0() {
		return Sizeid0;
	}

	public void setImagename0(String imagename0) {
		this.Imagename0 = imagename0;
	}

	public String getImagename0() {
		if (Imagename0 == null)
			return "";
		return Imagename0;
	}

	public void setGbbar(String gbbar) {
		this.Gbbar = gbbar;
	}

	public String getGbbar() {
		return Gbbar;
	}

	public void setXsamount(Float xsamount) {
		this.Xsamount = xsamount;
	}

	public Float getXsamount() {
		return Xsamount;
	}

	public void setXdsm(String xdsm) {
		this.Xdsm = xdsm;
	}

	public String getXdsm() {
		return Xdsm;
	}

	public void setZxbz(String zxbz) {
		this.Zxbz = zxbz;
	}

	public String getZxbz() {
		return Zxbz;
	}

	public void setSsdate(String ssdate) {
		this.Ssdate = ssdate;
	}

	public String getSsdate() {
		return Ssdate;
	}

	public void setDj(String dj) {
		this.Dj = dj;
	}

	public String getDj() {
		return Dj;
	}

	public void setJlman(String jlman) {
		this.Jlman = jlman;
	}

	public String getJlman() {
		return Jlman;
	}

	public void setSjman(String sjman) {
		this.Sjman = sjman;
	}

	public String getSjman() {
		return Sjman;
	}

	public void setProvid(Long provid) {
		this.Provid = provid;
	}

	public Long getProvid() {
		return Provid;
	}

	public void setColorlist(String colorlist) {
		this.Colorlist = colorlist;
	}

	public String getColorlist() {
		return Colorlist;
	}

	public void setUsedbj(Integer usedbj) {
		this.Usedbj = usedbj;
	}

	public Integer getUsedbj() {
		return Usedbj;
	}

	public void setLocale(String locale) {
		this.Locale = locale;
	}

	public String getLocale() {
		return Locale;
	}

	public void setChecksale(Float checksale) {
		this.Checksale = checksale;
	}

	public Float getChecksale() {
		return Checksale;
	}

	public void setAreaid(Long areaid) {
		this.Areaid = areaid;
	}

	public Long getAreaid() {
		return Areaid;
	}

	public void setKcamount(Long kcamount) {
		this.Kcamount = kcamount;
	}

	public Long getKcamount() {
		return Kcamount;
	}

	public void setWareremark(String wareremark) {
		this.Wareremark = wareremark;
	}

	public String getWareremark() {
		return Wareremark;
	}

	public void setWaveid(Long waveid) {
		this.Waveid = waveid;
	}

	public Long getWaveid() {
		if (Waveid == null)
			return (long) -1;
		return Waveid;
	}

	public void setTjtag(Integer tjtag) {
		this.Tjtag = tjtag;
	}

	public Integer getTjtag() {
		return Tjtag;
	}

	public void setSalerange(String salerange) {
		this.Salerange = salerange;
	}

	public String getSalerange() {
		return Salerange;
	}

	// 删除记录
	public int Delete() {
		if (Wareid == 0) {
			errmess = "商品id无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();

		strSql.append("declare ");
		strSql.append("\n   v_warename varchar2(300);");
		strSql.append("\n   v_wareno varchar2(40);");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n   v_usedbj number(1);");
		strSql.append("\n begin ");

		strSql.append("\n  begin ");
		strSql.append("\n    select wareno,warename,f_wareisused(wareid,usedbj) into v_wareno,v_warename,v_usedbj ");
		strSql.append("\n    from warecode where wareid=" + Wareid + " and accid=" + Accid + " and statetag=1;");
		// strSql.Append("\n select f_wareisused(" + wareid + ",v_usedbj) into
		// v_usedbj from dual;");
		strSql.append("\n    if v_usedbj=1 then ");
		strSql.append("\n       v_retcs:= '0当前商品已有库存，不允许删除！' ;");
		strSql.append("\n       goto exit;");
		strSql.append("\n    end if ;");

		strSql.append("\n    update warecode set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append("\n    where wareid=" + Wareid + " and accid=" + Accid + "; ");
		strSql.append("\n    delete from warecolor where wareid=" + Wareid + "; ");
		strSql.append("\n    delete from warenosize where wareid=" + Wareid + "; ");
		strSql.append("\n    delete from warebarcode where accid=" + Accid + " and wareid=" + Wareid + "; ");

		strSql.append("\n    insert into LOGRECORD (id,accid,progname,remark,lastop)");
		strSql.append("\n    values (LOGRECORD_id.nextval," + Accid + ",'商品档案','【删除记录】商品id:" + Wareid + "; 货号:'||v_wareno||'; 商品名称:'||v_warename,'" + Lastop + "');");

		strSql.append("\n    commit;");
		strSql.append("\n    v_retcs:= '1删除成功！' ;");
		strSql.append("\n  EXCEPTION WHEN NO_DATA_FOUND THEN ");
		strSql.append("\n    v_retcs:= '0未找到商品！';");
		strSql.append("\n  end;");

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

	// 增加记录
	public int Append(int fs, JSONObject jsonObject, String pictjson) {
		// if (Wareno != null && Wareno.equals("")) {
		if (Wareno == null || Autowareno == 1)
			Wareno = "";

		if (!Func.isNull(Wareno)) {
			if (fs == 0 && !pFunc.isWarecode(Wareno)) {
				errmess = "货号只允许输入字母、数字及 +-.#*() ";
				return 0;
			}
		}

		if (Func.isNull(Warename)) {
			errmess = "请输入商品名称！";
			return 0;
		}
		if (Typeid == null)
			Typeid = (long) 0;
		if (Sizegroupno == null || Sizegroupno.equals("")) {
			errmess = "未选定尺码组!";
			return 0;
		}
		if (!Func.isNull(Ssdate)) {
			if (Ssdate.length() > 0 && !Func.isValidDate(Ssdate)) {
				errmess = "上市日期不是一个有效值！(yyyy-mm-dd)";
				return 0;
			}
		}
		if (!Func.isNull(Retdatestr)) {
			if (Retdatestr.length() > 0 && !Func.isValidDate(Retdatestr)) {
				errmess = "退货日期不是一个有效值！(yyyy-mm-dd)";
				return 0;
			}
		}

		if (pictjson != null && !pictjson.equals("")) {

			JSONObject jsonObject1 = JSONObject.fromObject(pictjson);
			if (jsonObject1.has("base64string")) {
				String base64string = jsonObject1.getString("base64string");
				String retcs = Func.Base64UpFastDFS(base64string);
				if (retcs.substring(0, 1).equals("1")) {
					Imagename0 = retcs.substring(1);
				}
			}
		}
		// System.out.println("11111");
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("wareid,wareno,");
		strSql2.append("v_wareid,v_wareno,");

		// strSql1.append("wareno,");
		// strSql2.append("v_wareno,");

		// if (Autowareno == 1) {// 自动产生货号
		// strSql1.append("wareno,");
		// strSql2.append("f_autotowareno(" + Accid + "),"); // 自动生成货号
		// } else {
		// strSql1.append("wareno,");
		// strSql2.append("'" + Wareno.toUpperCase() + "',");
		// }

		// if (Wareno != null) {
		// strSql1.append("wareno,");
		// if (Autowareno == 1)
		// strSql2.append("f_autotowareno(" + Accid + "),"); // 自动生成货号
		// else
		// strSql2.append("'" + Wareno.toUpperCase() + "',");
		// }
		if (Warename != null) {
			Warename = Warename.replace("'", "''");
			strSql1.append("warename,");
			strSql2.append("'" + Func.strLeft(Warename, 60) + "',");
			Shortname = Func.strLeft(PinYin.getPinYinHeadChar(Warename), 30);
		}
		if (Units != null) {
			strSql1.append("units,");
			strSql2.append("'" + Units + "',");
		}
		if (Model != null) {
			strSql1.append("model,");
			strSql2.append("'" + Model + "',");
		}
		if (Shortname != null) {
			strSql1.append("shortname,");
			strSql2.append("'" + Shortname + "',");
		}
		if (Brandid != null) {
			strSql1.append("brandid,");
			strSql2.append(Brandid + ",");
		}
		if (Seasonname != null) {
			strSql1.append("seasonname,");
			strSql2.append("'" + Seasonname + "',");
		}
		if (Retdatestr != null) {
			strSql1.append("Retdatestr,");
			strSql2.append("'" + Retdatestr + "',");
		}
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Typeid != null) {
			strSql1.append("typeid,");
			strSql2.append(Typeid + ",");
		}
		if (Prodyear != null) {
			strSql1.append("prodyear,");
			strSql2.append("'" + Func.strLeft(Prodyear, 4) + "',");
		}
		if (Prodno != null) {
			strSql1.append("prodno,");
			strSql2.append("'" + Prodno.toUpperCase() + "',");
		}
		if (Entersale != null) {
			strSql1.append("entersale,");
			strSql2.append(Entersale + ",");
		}
		if (Retailsale != null) {
			strSql1.append("retailsale,");
			strSql2.append(Retailsale + ",");
		}
		if (Sale1 != null) {
			strSql1.append("sale1,");
			strSql2.append(Sale1 + ",");
		}
		if (Sale2 != null) {
			strSql1.append("sale2,");
			strSql2.append(Sale2 + ",");
		}
		if (Sale3 != null) {
			strSql1.append("sale3,");
			strSql2.append(Sale3 + ",");
		}
		if (Sale4 != null) {
			strSql1.append("sale4,");
			strSql2.append(Sale4 + ",");
		}
		if (Sale5 != null) {
			strSql1.append("sale5,");
			strSql2.append(Sale5 + ",");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Sizegroupno != null) {
			strSql1.append("sizegroupno,");
			strSql2.append("'" + Sizegroupno + "',");
		}
		if (Useritem1 != null) {
			strSql1.append("useritem1,");
			strSql2.append("'" + Useritem1 + "',");
		}
		if (Useritem2 != null) {
			strSql1.append("useritem2,");
			strSql2.append("'" + Useritem2 + "',");
		}
		if (Useritem3 != null) {
			strSql1.append("useritem3,");
			strSql2.append("'" + Useritem3 + "',");
		}
		if (Useritem4 != null) {
			strSql1.append("useritem4,");
			strSql2.append("'" + Useritem4 + "',");
		}
		if (Useritem5 != null) {
			strSql1.append("useritem5,");
			strSql2.append("'" + Useritem5 + "',");
		}
		if (Safety != null) {
			strSql1.append("safety,");
			strSql2.append("'" + Safety + "',");
		}
		// if (Lyfs != null) {
		strSql1.append("lyfs,");
		strSql2.append(Lyfs + ",");
		// }
		if (Accid1 != null) {
			strSql1.append("accid1,");
			strSql2.append(Accid1 + ",");
		}
		if (Wareid1 != null) {
			strSql1.append("wareid1,");
			strSql2.append(Wareid1 + ",");
		}
		// if (Noused != null) {
		strSql1.append("noused,");
		strSql2.append(Noused + ",");
		// }
		// if (Statetag != null) {
		strSql1.append("statetag,");
		strSql2.append(Statetag + ",");
		// }
		// if (Downenabled != null) {
		strSql1.append("downenabled,");
		strSql2.append("0,");
		// }
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		// if (Lastdate != null) {
		// strSql1.append("lastdate,");
		// strSql2.append("" + Lastdate + ",");
		// }
		if (Imagename != null) {
			strSql1.append("imagename,");
			strSql2.append("'" + Imagename + "',");
		}
		if (Imagename0 != null) {
			strSql1.append("imagename0,");
			strSql2.append("'" + Imagename0 + "',");
		}
		if (Colorid0 != null) {
			strSql1.append("colorid0,");
			strSql2.append(Colorid0 + ",");
		}
		if (Sizeid0 != null) {
			strSql1.append("sizeid0,");
			strSql2.append(Sizeid0 + ",");
		}
		if (Gbbar != null) {
			strSql1.append("gbbar,");
			strSql2.append("'" + Gbbar + "',");
		}
		// if (Xsamount != null) {
		strSql1.append("xsamount,");
		strSql2.append("0,");
		// }
		if (Xdsm != null) {
			strSql1.append("xdsm,");
			strSql2.append("'" + Xdsm + "',");
		}
		if (Zxbz != null) {
			strSql1.append("zxbz,");
			strSql2.append("'" + Zxbz + "',");
		}
		if (Ssdate != null) {
			strSql1.append("ssdate,");
			strSql2.append("'" + Ssdate + "',");
		}
		if (Dj != null) {
			strSql1.append("dj,");
			strSql2.append("'" + Dj + "',");
		}
		if (Jlman != null) {
			strSql1.append("jlman,");
			strSql2.append("'" + Jlman + "',");
		}
		if (Sjman != null) {
			strSql1.append("sjman,");
			strSql2.append("'" + Sjman + "',");
		}
		if (Provid != null) {
			strSql1.append("provid,");
			strSql2.append(Provid + ",");
		}
		// if (Colorlist != null) {
		// strSql1.append("colorlist,");
		// strSql2.append("'" + Colorlist + "',");
		// }
		if (Usedbj != null) {
			strSql1.append("usedbj,");
			strSql2.append(Usedbj + ",");
		}
		if (Locale != null) {
			strSql1.append("locale,");
			strSql2.append("'" + Locale + "',");
		}
		if (Checksale != null) {
			strSql1.append("checksale,");
			strSql2.append(Checksale + ",");
		}
		if (Areaid != null) {
			strSql1.append("areaid,");
			strSql2.append(Areaid + ",");
		}
		// if (Kcamount != null) {
		strSql1.append("kcamount,");
		strSql2.append("0,");
		// }
		if (Wareremark != null) {
			strSql1.append("wareremark,");
			strSql2.append("'" + Wareremark + "',");
		}
		if (Waveid != null) {
			strSql1.append("waveid,");
			strSql2.append(Waveid + ",");
		}
		if (Tjtag != null) {// 特价
			strSql1.append("tjtag,");
			strSql2.append(Tjtag + ",");
		}
		if (Salerange != null) {
			strSql1.append("salerange,");
			strSql2.append("'" + Salerange + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_wareid number; ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n   v_wareno varchar2(40); ");
		strSql.append("\n begin ");
		strSql.append("\n   v_wareid:=0; ");
		if (Autowareno == 1) {// 自动产生货号
			strSql.append("\n   v_wareno:=f_autotowareno(" + Accid + ");");
		} else {
			strSql.append("\n   v_wareno:='" + Wareno.toUpperCase() + "';");
		}

		// if (!Func.isNull(Wareno)) {
		strSql.append("\n   select count(*) into v_count  from warecode");
		strSql.append("\n   where wareno=v_wareno and accid=" + Accid + " and statetag=1;");
		// strSql.append("\n and wareid<>" + Wareid + ";");
		strSql.append("\n   if v_count>0 then ");
		strSql.append("\n      v_retcs:= '0货号'||v_wareno||'已存在，请重新输入！' ;");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if ;");
		// }

		if (Typeid != null && Typeid > 0) {
			strSql.append("\n   select count(*) into v_count from waretype where (accid=0 or accid=" + Accid + ") and typeid=" + Typeid + ";");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0未找到商品类型!';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Brandid != null && Brandid > 0) {
			strSql.append("\n   select count(*) into v_count from brand where accid=" + Accid + " and brandid=" + Brandid + ";");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0未找到品牌!';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");

		}
		if (Provid != null && Provid > 0) {
			strSql.append("\n   select count(*) into v_count from provide where accid=" + Accid + " and provid=" + Provid + ";");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0未找到供应商!';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");

		}
		if (Areaid != null && Areaid > 0) {
			strSql.append("\n   select count(*) into v_count from area where accid=" + Accid + " and areaid=" + Areaid + ";");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0未找到区位!';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");

		}

		strSql.append("\n   select count(*) into v_count from sizecode where accid=" + Accid + " and groupno='" + Sizegroupno + "' and rownum=1;");// fetch
																																					// first
																																					// 1
																																					// row
																																					// only";
		strSql.append("\n   if v_count=0 then ");
		strSql.append("\n      v_retcs:='0未找到尺码组!';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		strSql.append("\n    v_wareid:=warecode_wareid.nextval; ");
		strSql.append("\n    insert into warecode (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("\n  commit; ");
		if (jsonObject.has("colorlist")) {
			JSONArray jsonArray = jsonObject.getJSONArray("colorlist");

			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				long colorid = Long.parseLong(jsonObject2.getString("colorid"));

				strSql.append("\n    select count(*) into v_count from warecolor where wareid=v_wareid and colorid=" + colorid + ";");
				strSql.append("\n    if v_count=0  then");
				strSql.append("\n       insert into warecolor (id,wareid,colorid,ywly,lastdate,lastop) ");
				strSql.append("\n       values (warecolor_id.nextval,v_wareid, " + colorid + ",0,sysdate,'" + Lastop + "');");
				strSql.append("\n    end if;");

			}
			strSql.append("\n  commit; ");
			strSql.append("\n  update warecode set colorlist=f_getwarecolorname(wareid) where wareid=v_wareid and accid=" + Accid + ";");
		}

		if (Imagename0 != null && Imagename0.length() > 0) {
			strSql.append("\n   insert into warepicture (id,wareid,imagename,orderid,remark,lastop,lastdate)");
			strSql.append("\n   values (warepicture_id.nextval,v_wareid,'" + Imagename0 + "',1,'','" + Lastop + "',sysdate); ");
		}
		strSql.append("\n   p_autobarcode(v_wareid, " + Barfs + ");"); // 自动产生条码

		strSql.append("\n   select wareid,wareno into v_wareid,v_wareno from warecode where wareid=v_wareid ; ");
		strSql.append("\n   v_retcs:='1操作成功！';");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_wareid,v_wareno,v_retcs into :wareid,:wareno,:retcs from dual; ");
		strSql.append("\n end;");
		// System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("wareid", new ProdParam(Types.BIGINT));
		param.put("wareno", new ProdParam(Types.VARCHAR));
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			String retcs = param.get("retcs").getParamvalue().toString();
			if (retcs.substring(0, 1).equals("1")) {
				Wareno = param.get("wareno").getParamvalue().toString();
				Wareid = Long.parseLong(param.get("wareid").getParamvalue().toString());
				return 1;
			} else {
				errmess = retcs.substring(1);
				return 0;
			}
		}
	}

	// 修改记录
	public int Update(int fs) {
		if (Wareid == null || Wareid == 0) {
			errmess = "商品id无效！";
			return 0;
		}
		if (Wareno != null) {
			// Wareno = model.wareno.ToUpper();
			// if (!IsNumAndEnCh(model.wareno))
			if (fs == 0 && !pFunc.isWarecode(Wareno)) {
				errmess = "货号只允许输入字母、数字及 +-.#*() ";
				return 0;
			}

			// if (Exists()) // 异常
			// {
			// // WriteResult("0", ss);
			// return 0;
			// }
		}
		if (Warename != null && Warename.equals("")) {
			errmess = "请输入商品名称！";
			return 0;
		}
		if (!Func.isNull(Ssdate)) {
			if (Ssdate.length() > 0 && !Func.isValidDate(Ssdate)) {
				errmess = "上市日期不是一个有效值！(yyyy-mm-dd)";
				return 0;
			}
		}
		if (!Func.isNull(Retdatestr)) {

			if (Retdatestr.length() > 0 && !Func.isValidDate(Retdatestr)) {
				errmess = "退货日期不是一个有效值！(yyyy-mm-dd)";
				return 0;
			}
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_count number(10);");
		strSql.append("\n   v_groupno varchar2(60);");
		strSql.append("\n   v_wareid number(10);");
		strSql.append("\n   v_accid number(10);");
		strSql.append("\n   v_usedbj number(1);");
		strSql.append("\n   v_oldgroupno varchar2(60);");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n begin ");
		strSql.append("\n  v_wareid:=" + Wareid + "; ");
		if (!Func.isNull(Wareno)) {
			strSql.append("\n   select count(*) into v_count  from warecode");
			strSql.append("\n   where wareno='" + Wareno + "' and accid=" + Accid + " and statetag=1");
			strSql.append("\n   and wareid<>" + Wareid + ";");
			strSql.append("\n   if v_count>0 then ");
			strSql.append("\n      v_retcs:= '0货号已存在，请重新输入！' ;");
			strSql.append("\n      goto exit; ");
			strSql.append("\n   end if ;");

		}
		if (Sizegroupno != null) // 如果货号已使用，不允许更改尺码组
		{
			strSql.append("\n  select sizegroupno,usedbj into v_oldgroupno,v_usedbj from warecode where wareid=v_wareid; ");
			strSql.append("\n  if v_oldgroupno<>'" + Sizegroupno + "' then ");
			strSql.append("\n    if f_wareisused(v_wareid,v_usedbj)=1 then ");
			strSql.append("\n       v_retcs:= '0当前商品已有库存，不允许更改尺码组('||v_oldgroupno||')！' ;");
			strSql.append("\n       goto exit; ");
			strSql.append("\n    end if ;");
			strSql.append("\n  end if ;");
		}

		if (Areaid != null && Areaid > 0) {
			strSql.append("\n   select count(*) into v_count from area where accid=" + Accid + " and areaid=" + Areaid + ";");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0未找到区位!';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");

		}

		strSql.append("\n   update warecode set ");

		if (Wareno != null) {
			strSql.append("wareno='" + Wareno.toUpperCase() + "',");
		}
		if (Warename != null) {
			Warename = Warename.replace("'", "''");
			strSql.append("warename='" + Func.strLeft(Warename, 60) + "',");
			Shortname = Func.strLeft(PinYin.getPinYinHeadChar(Warename), 30);
		}
		if (Units != null) {
			strSql.append("units='" + Units + "',");
		}
		if (Model != null) {
			strSql.append("model='" + Model + "',");
		}
		if (Shortname != null) {
			strSql.append("shortname='" + Shortname + "',");
		}
		if (Brandid != null) {
			strSql.append("brandid=" + Brandid + ",");
		}
		if (Seasonname != null) {
			strSql.append("seasonname='" + Seasonname + "',");
		}
		if (Retdatestr != null) {
			strSql.append("Retdatestr='" + Retdatestr + "',");
		}
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		if (Typeid != null) {
			strSql.append("typeid=" + Typeid + ",");
		}
		if (Prodyear != null) {
			strSql.append("prodyear='" + Func.strLeft(Prodyear, 4) + "',");
		}
		if (Prodno != null) {
			strSql.append("prodno='" + Prodno.toUpperCase() + "',");
		}
		if (Entersale != null) {
			strSql.append("entersale=" + Entersale + ",");
		}
		if (Retailsale != null) {
			strSql.append("retailsale=" + Retailsale + ",");
		}
		if (Sale1 != null) {
			strSql.append("sale1=" + Sale1 + ",");
		}
		if (Sale2 != null) {
			strSql.append("sale2=" + Sale2 + ",");
		}
		if (Sale3 != null) {
			strSql.append("sale3=" + Sale3 + ",");
		}
		if (Sale4 != null) {
			strSql.append("sale4=" + Sale4 + ",");
		}
		if (Sale5 != null) {
			strSql.append("sale5=" + Sale5 + ",");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Sizegroupno != null) {
			strSql.append("sizegroupno='" + Sizegroupno.toUpperCase() + "',");
		}
		if (Useritem1 != null) {
			strSql.append("useritem1='" + Useritem1 + "',");
		}
		if (Useritem2 != null) {
			strSql.append("useritem2='" + Useritem2 + "',");
		}
		if (Useritem3 != null) {
			strSql.append("useritem3='" + Useritem3 + "',");
		}
		if (Useritem4 != null) {
			strSql.append("useritem4='" + Useritem4 + "',");
		}
		if (Useritem5 != null) {
			strSql.append("useritem5='" + Useritem5 + "',");
		}

		if (Noused != null) {
			strSql.append("noused=" + Noused + ",");
		}

		if (Imagename != null) {
			strSql.append("imagename='" + Imagename + "',");
		}
		if (Colorid0 != null) {
			strSql.append("colorid0=" + Colorid0 + ",");
		}
		if (Sizeid0 != null) {
			strSql.append("sizeid0=" + Sizeid0 + ",");
		}
		if (Imagename0 != null) {
			strSql.append("imagename0='" + Imagename0 + "',");
		}
		if (Gbbar != null) {
			strSql.append("gbbar='" + Gbbar.toUpperCase() + "',");
		}
		if (Xsamount != null) {
			strSql.append("xsamount=" + Xsamount + ",");
		}
		if (Xdsm != null) {
			strSql.append("xdsm='" + Xdsm + "',");
		}
		if (Zxbz != null) {
			strSql.append("zxbz='" + Zxbz + "',");
		}
		if (Ssdate != null) {
			strSql.append("ssdate='" + Ssdate + "',");
		}
		if (Dj != null) {
			strSql.append("dj='" + Dj + "',");
		}
		if (Safety != null) {
			strSql.append("Safety='" + Safety + "',");
		}
		if (Jlman != null) {
			strSql.append("jlman='" + Jlman + "',");
		}
		if (Sjman != null) {
			strSql.append("sjman='" + Sjman + "',");
		}
		if (Provid != null) {
			strSql.append("provid=" + Provid + ",");
		}
		// if (Colorlist != null) {
		// strSql.append("colorlist='" + Colorlist + "',");
		// }
		if (Usedbj != null) {
			strSql.append("usedbj=" + Usedbj + ",");
		}
		if (Locale != null) {
			strSql.append("locale='" + Locale + "',");
		}
		if (Checksale != null) {
			strSql.append("checksale=" + Checksale + ",");
		}
		if (Areaid != null) {
			strSql.append("areaid=" + Areaid + ",");
		}
		if (Kcamount != null) {
			strSql.append("kcamount=" + Kcamount + ",");
		}
		if (Wareremark != null) {
			strSql.append("wareremark='" + Wareremark + "',");
		}
		if (Waveid != null) {
			strSql.append("waveid=" + Waveid + ",");
		}
		if (Tjtag != null) {
			strSql.append("tjtag=" + Tjtag + ",");
		}
		if (Salerange != null) {
			strSql.append("salerange='" + Salerange + "',");
		}
		strSql.append("lastop='" + Lastop + "',");
		strSql.append("lastdate=sysdate");
		strSql.append("  where wareid=" + Wareid + " and accid=" + Accid + " ;");

		strSql.append("\n  update warecode set colorlist=f_getwarecolorname(wareid) where wareid=v_wareid and accid=" + Accid + ";");
		// strSql.append("\n commit;");
		strSql.append("\n  p_waresetused(v_wareid);");

		strSql.append("\n  p_autobarcode(v_wareid, " + Barfs + ");"); // 自动产生条码

		strSql.append("\n  v_retcs:= '1操作成功！' ;");

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

	/**
	 * @return
	 */
	private boolean Exists() {// 判断货号是否存在

		StringBuilder strSql = new StringBuilder();

		strSql.append("select  wareid  from warecode");
		strSql.append(" where wareno='" + Wareno + "' and accid=" + Accid + " and statetag=1");
		if (Wareid != null)
			strSql.append("and wareid<>" + Wareid);
		if (DbHelperSQL.Exists(strSql.toString())) {
			errmess = "货号:" + Wareno + " 已存在！";
			return true;
		} else
			return false;

	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		// strSql.append("select " + fieldlist);
		// strSql.append(" FROM warecode ");
		strSql.append("select " + fieldlist + ",f_getwarecolorname(a.wareid)  as colornamelist");
		strSql.append("\n FROM warecode a");
		strSql.append("\n left outer join waretype b on a.typeid=b.typeid");
		strSql.append("\n left outer join brand c on a.brandid=c.brandid");
		// strSql.append(" left outer join colorcode d on
		// a.colorid0=d.colorid");
		// strSql.append(" left outer join sizecode e on a.sizeid0=e.sizeid");
		strSql.append("\n left outer join provide f on a.provid=f.provid");
		strSql.append("\n left outer join area g on a.areaid=g.areaid");
		strSql.append("\n left outer join warewave h on a.waveid=h.waveid");

		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		// System.out.println(strSql.toString());
		return DbHelperSQL.Query(strSql.toString());
	}

	// // 获取分页数据
	// public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
	// StringBuilder strSql = new StringBuilder();
	// strSql.append("select " + fieldlist);
	// strSql.append(" FROM warecode a");
	// strSql.append(" left outer join waretype b on a.typeid=b.typeid");
	// strSql.append(" left outer join brand c on a.brandid=c.brandid");
	// strSql.append(" left outer join colorcode d on a.colorid0=d.colorid");
	// strSql.append(" left outer join sizecode e on a.sizeid0=e.sizeid");
	// strSql.append(" left outer join provide f on a.provid=f.provid");
	// strSql.append(" left outer join area g on a.areaid=g.areaid");
	// strSql.append(" left outer join warewave h on a.waveid=h.waveid");
	//
	// if (!strWhere.equals("")) {
	// strSql.append(" where " + strWhere);
	// }
	// qp.setQueryString(strSql.toString());
	// return DbHelperSQL.GetTable(qp);
	// }

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM warecode a");
		strSql.append("\n left outer join waretype b on a.typeid=b.typeid");
		strSql.append("\n left outer join waretype d on b.p_typeid=d.typeid");
		strSql.append("\n left outer join brand c on a.brandid=c.brandid");
		// strSql.append(" left outer join colorcode d on
		// a.colorid0=d.colorid");
		// strSql.append(" left outer join sizecode e on a.sizeid0=e.sizeid");
		strSql.append("\n left outer join provide f on a.provid=f.provid");
		strSql.append("\n left outer join area g on a.areaid=g.areaid");
		strSql.append("\n left outer join warewave h on a.waveid=h.waveid");
		if (Houseid != null && Houseid > 0)
			strSql.append("\n left outer join housesaleprice h on a.wareid=h.wareid and h.houseid=" + Houseid);

		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
//System.out.println(strSql.toString());
		// if (qp.getPageIndex() > 0)

		return DbHelperSQL.GetTable(qp);
		// else
		// return DbHelperSQL.GetTableAll(qp);
	}

	// 获取分页数据
	public String GetTable2Excel(QueryParam qp, String strWhere, String fieldlist, JSONObject jsonObject) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM warecode a");
		strSql.append("\n left outer join waretype b on a.typeid=b.typeid");
		strSql.append("\n left outer join waretype d on b.p_typeid=d.typeid");
		strSql.append("\n left outer join brand c on a.brandid=c.brandid");
		strSql.append("\n left outer join provide f on a.provid=f.provid");
		strSql.append("\n left outer join area g on a.areaid=g.areaid");
		strSql.append("\n left outer join warewave h on a.waveid=h.waveid");
		if (Houseid != null && Houseid > 0)
			strSql.append("\n left outer join housesaleprice h on a.wareid=h.wareid and h.houseid=" + Houseid);

		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		qp.setPageIndex(1);
		qp.setPageSize(100);
		return DbHelperSQL.Table2Excel(qp, jsonObject);
	}

	// 获取分页数据 ok 获取店铺商品零售价列表
	public Table GetTable1(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM warecode a");
		strSql.append("\n left outer join waretype b on a.typeid=b.typeid");
		strSql.append("\n left outer join brand c on a.brandid=c.brandid");
		strSql.append("\n left outer join provide f on a.provid=f.provid");
		strSql.append("\n left outer join area g on a.areaid=g.areaid");
		strSql.append("\n left outer join housesaleprice h on a.wareid=h.wareid");

		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 上传商品图片 增加更改warepicture中的图片
	public int doUpdateWarePicture(float recid, int dhhbj, int ordid) {

		if (recid == 0) {
			if (ordid < 1 || ordid > 10) {
				errmess = "图片序号无效！";
				return 0;
			}
		}

		String qry = "declare ";
		// qry += "\n v_imagename1 varchar2(60); ";
		qry += "\n   v_retcs varchar2(200); ";
		qry += "\n   v_count number(10); ";
		qry += "\n   v_wareid number(10); ";
		qry += "\n   v_id number; ";
		qry += "\n   v_orderid number; ";
		qry += "\n begin ";

		qry += "\n  begin";
		if (Wareid == 0) {
			// wareno = wareno.ToUpper();
			qry += "\n   select wareid into v_wareid from warecode where accid=" + Accid + " and wareno='" + Wareno + "' and rownum=1 and statetag=1;";
		} else {
			qry += "\n   select wareid into v_wareid from warecode where accid=" + Accid + " and wareid=" + Wareid + " and rownum=1 and statetag=1;";
		}
		qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n     v_retcs:='0货号未找到！';";
		qry += "\n     goto exit;";
		qry += "\n  end;";

		if (recid > 0) // 更改图片
		{
			if (dhhbj == 1) // 订货会图片
			{
				qry += "\n    begin ";
				qry += "\n      select orderid,id into v_orderid,v_id from orderpicture where wareid=v_wareid and id=" + recid + "; ";
				qry += "\n      update orderpicture set imagename='" + Imagename + "' where id=v_id;";
				qry += "\n      if v_orderid=1 then";
				qry += "\n         update warecode set imagename='" + Imagename + "',lastdate=sysdate,lastop='" + Lastop + "' where wareid=v_wareid; ";
				qry += "\n      end if;";
				qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
				qry += "\n    end;";
			} else {
				qry += "\n    begin ";
				qry += "\n      select orderid,id into v_orderid,v_id from warepicture where wareid=v_wareid and id=" + recid + "; ";
				qry += "\n      update warepicture set imagename='" + Imagename + "' where id=v_id;";
				qry += "\n      if v_orderid=1 then";
				qry += "\n         update warecode set imagename0='" + Imagename + "',lastdate=sysdate,lastop='" + Lastop + "' where wareid=v_wareid; ";
				qry += "\n      end if;";
				qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
				qry += "\n    end;";

			}
		} else {
			if (dhhbj == 1) // 订货会图片
			{
				// 更新主图
				if (ordid == 1) {
					qry += "\n    update warecode set imagename='" + Imagename + "',lastdate=sysdate,lastop='" + Lastop + "' where wareid=v_wareid; ";
				}
				qry += "\n    begin ";
				qry += "\n      select id into v_id from orderpicture where wareid=v_wareid and orderid=" + ordid + " and rownum=1; ";
				qry += "\n      update orderpicture set imagename='" + Imagename + "' where id=v_id;";
				qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
				qry += "\n      insert into orderpicture (id,wareid,imagename,orderid,remark,lastop,lastdate)";
				qry += "\n      values (orderpicture_id.nextval,v_wareid,'" + Imagename + "'," + ordid + ",'','" + Lastop + "',sysdate); ";
				qry += "\n    end;";
			} else // 商品档案图片
			{

				// 更新主图
				if (ordid == 1) {
					qry += "\n    update warecode set imagename0='" + Imagename + "',lastdate=sysdate,lastop='" + Lastop + "' where wareid=v_wareid; ";
				}
				qry += "\n    begin ";
				qry += "\n      select id into v_id from warepicture where wareid=v_wareid and orderid=" + ordid + " and rownum=1; ";
				qry += "\n      update warepicture set imagename='" + Imagename + "' where id=v_id;";
				qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
				qry += "\n      insert into warepicture (id,wareid,imagename,orderid,remark,lastop,lastdate)";
				qry += "\n      values (warepicture_id.nextval,v_wareid,'" + Imagename + "'," + ordid + ",'','" + Lastop + "',sysdate); ";
				qry += "\n    end;";
			}
		}
		qry += "\n  v_retcs:='1操作成功！'; ";
		qry += "\n  <<exit>>";
		qry += "\n  select v_retcs into :retcs from dual; ";

		qry += "\n end; ";
		//		LogUtils.LogDebugWrite("UploadWarepicture", qry);
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

	// 增加订货会商品图片
	public int doUploadOmpicture(String pictjson) {
		if (Wareid == 0) {
			errmess = "商品id参数无效！";
			return 0;
		}
		if (pictjson == null || pictjson.equals("")) {
			errmess = "未传入图片组参数:pictjson！";
			return 0;
		}
		JSONObject jsonObject = JSONObject.fromObject(pictjson);
		if (!jsonObject.has("base64string")) {
			errmess = "未传入图片值参数:base64string！";
			return 0;
		}
		String base64string = jsonObject.getString("base64string");
		String retcs = Func.Base64UpFastDFS(base64string);
		if (retcs.substring(0, 1).equals("1")) {
			Imagename = retcs.substring(1);
		} else {
			errmess = "商品图片上传失败！" + retcs.substring(1);
			return 0;
		}

		String qry = "declare ";
		qry += "\n  v_oldimagename varchar(60);";
		qry += "\n begin ";
		qry += "\n   select imagename into v_oldimagename from warecode where wareid=" + Wareid + ";";
		qry += "\n   update warecode set imagename='" + Imagename + "' where wareid=" + Wareid + ";";
		qry += "\n   select v_oldimagename into :retcs from dual;";
		qry += "\n end;";
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		// 调用
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "上传失败！";
			return 0;
		}
		// 取返回值
		String oldimagename = param.get("retcs").getParamvalue().toString();
		if (oldimagename != null && !oldimagename.equals("")) {
			// Func.fastDFSDelete(oldimagename);
		}
		errmess = "上传成功！";
		return 1;
	}

	// 增加商品图片
	public int doUploadWarepicture(String pictjson) {
		if (Wareid == 0) {
			errmess = "商品id参数无效！";
			return 0;
		}
		if (pictjson == null || pictjson.equals("")) {
			errmess = "未传入图片组参数:pictjson！";
			return 0;
		}
		JSONObject jsonObject = JSONObject.fromObject(pictjson);
		if (!jsonObject.has("base64string")) {
			errmess = "未传入图片值参数:base64string！";
			return 0;
		}
		String base64string = jsonObject.getString("base64string");
		String retcs = Func.Base64UpFastDFS(base64string);
		if (retcs.substring(0, 1).equals("1")) {
			Imagename0 = retcs.substring(1);
		} else {
			errmess = "商品图片上传失败！" + retcs.substring(1);
			return 0;
		}

		String qry = "declare ";
		qry += "\n  v_oldimagename varchar(60);";
		qry += "\n begin ";
		qry += "\n   select imagename0 into v_oldimagename from warecode where wareid=" + Wareid + ";";
		qry += "\n   update warecode set imagename0='" + Imagename0 + "' where wareid=" + Wareid + ";";
		qry += "\n   select v_oldimagename into :retcs from dual;";
		qry += "\n end;";
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		// 调用
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "上传失败！";
			return 0;
		}
		// 取返回值，删除老图片
		// String oldimagename = param.get("retcs").getParamvalue().toString();
		// if (oldimagename != null && !oldimagename.equals("")) {
		// Func.fastDFSDelete(oldimagename);
		// }
		errmess = "上传成功！";
		return 1;
	}

	// 根据货号增加或更新商品信息 用于从excel中导入商品编码
	public int doLoadfromExcel() {

		Table tb = new Table();

		if (Wareno != null && !Wareno.equals("")) {
			Wareno = Func.strLeft(Wareno, 20);
			if (!pFunc.isWarecode(Wareno)) {
				errmess = "货号不是一个有效值！ 货号只允许输入字母、数字及 +/-.#*()";
				return 0;
			}
		}
		if (Brandname != null && !Brandname.equals("")) {
			String qry1 = " select brandid from brand where accid=" + Accid + " and statetag=1 and brandname='" + Brandname + "'";
			tb = DbHelperSQL.Query(qry1).getTable(1);
			if (tb.getRowCount() > 0) {
				Brandid = Long.parseLong(tb.getRow(0).get("BRANDID").toString());
			}
		}

		// 查找区位begin
		if (Areaname != null && !Areaname.equals("")) {
			String qry1 = " select areaid from area where accid=" + Accid + " and statetag=1 and areaname='" + Areaname + "'";
			tb = DbHelperSQL.Query(qry1).getTable(1);
			if (tb.getRowCount() > 0) {
				Areaid = Long.parseLong(tb.getRow(0).get("AREAID").toString());
			}
		}
		if (Typename != null && !Typename.equals("")) {
			if (Typename0.length() <= 0) {
				errmess = "商品大类名称无效！";
				return 0;
			}
			if (Typename0.equals("无") || Typename.equals("无")) {
				Typeid = (long) 0;
			} else {
				String qry1 = " select typeid from waretype where lastnode=1 and (accid=0 or accid=" + Accid + ") and fullname='" + Typename0 + "-" + Typename + "'";

				tb = DbHelperSQL.Query(qry1).getTable(1);

				if (tb.getRowCount() == 0) {
					errmess = "商品类型无效！";
					return 0;
				}
				Typeid = Long.parseLong(tb.getRow(0).get("TYPEID").toString());
			}
		}
		if (Provname != null && !Provname.equals("")) {
			String qry1 = " select provid from provide where accid=" + Accid + " and rownum=1 and statetag=1 and provname='" + Provname + "'";
			tb = DbHelperSQL.Query(qry1).getTable(1);
			if (tb.getRowCount() > 0) {
				Provid = Long.parseLong(tb.getRow(0).get("PROVID").toString());
			}
		}
		if (Wavename != null && !Wavename.equals("")) {

			String qry1 = " select waveid from warewave where accid=" + Accid + " and statetag=1 and Wavename='" + Wavename + "'";
			tb = DbHelperSQL.Query(qry1).getTable(1);

			if (tb.getRowCount() > 0) {
				Waveid = Long.parseLong(tb.getRow(0).get("WAVEID").toString());
			}

		}

		if (!Func.isNull(Ssdate)) {
			if (Ssdate.length() > 0 && !Func.isValidDate(Ssdate)) {
				errmess = "上市日期不是一个有效值！(yyyy-mm-dd)";
				return 0;
			}
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");// "select wareid from warecode where accid="
									// + accid + " and wareno='" + wareno + "'
									// and rownum=1";
		strSql.append("\n    v_wareid number(10); ");
		strSql.append("\n    v_colorid number(10); ");
		strSql.append("\n    v_count number(10); ");
		strSql.append("\n    v_brandid number(10); ");
		strSql.append("\n    v_retcs varchar2(200); ");
		strSql.append("\n    v_wareno varchar2(30); ");
		strSql.append("\n begin ");

		if (Wareid != null && Wareid > 0) // 修改商品
		{
			strSql.append("\n   select count(*) into v_count from warecode where wareid=" + Wareid + " and statetag=1 and accid=" + Accid + ";");
			strSql.append("\n   if v_count=0 then");
			strSql.append("\n      v_retcs:='0商品id:" + Wareid + " 未找到！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
			strSql.append("\n   v_wareid:=" + Wareid + ";");
			// if (Autowareno == 0 && Wareno != null && !Wareno.equals("")) //
			// 不是自动产生货号，要判断货号是否重复
			if (Wareno != null && !Wareno.equals("")) // 不是自动产生货号，要判断货号是否重复
			{
				strSql.append("\n   v_wareno:='" + Wareno.toUpperCase() + "';");
				strSql.append("\n   select count(*) into v_count from warecode where wareno=v_wareno and statetag=1 and rownum=1 and wareid<>" + Wareid + " and accid=" + Accid + ";");
				strSql.append("\n   if v_count>0 then");
				strSql.append("\n      v_retcs:='0货号:" + Wareno + " 已存在！';");
				strSql.append("\n      goto exit;");
				strSql.append("\n   end if;");
			}
			strSql.append("\n begin ");

			strSql.append("\n   update warecode set ");

			if (Wareno != null && !Wareno.equals("")) {
				strSql.append("wareno=v_wareno,");
			}
			if (Warename != null) {
				strSql.append("warename='" + Func.strLeft(Warename, 50) + "',");
				Shortname = Func.strLeft(PinYin.getPinYinHeadChar(Warename), 20);
				strSql.append("shortname='" + Shortname + "',");
			}
			if (Units != null) {
				strSql.append("units='" + Units + "',");
			}
			if (Model != null) {
				strSql.append("Model='" + Model + "',");
			}
			if (Brandid != null) {
				strSql.append("brandid=" + Brandid + ",");
			}
			if (Seasonname != null) {
				strSql.append("seasonname='" + Seasonname + "',");
			}
			if (Noused != null) {
				strSql.append("Noused=" + Noused + ",");
			}
			if (Typeid != null) {
				strSql.append("typeid=" + Typeid + ",");
			}
			if (Prodyear != null) {
				strSql.append("prodyear='" + Func.strLeft(Prodyear, 4) + "',");
			}
			if (Prodno != null) {
				strSql.append("prodno='" + Prodno.toUpperCase() + "',");
			}
			if (Entersale != null) {
				strSql.append("entersale=" + Entersale + ",");
			}
			if (Retailsale != null) {
				strSql.append("retailsale=" + Retailsale + ",");
			}
			if (Sale1 != null) {
				strSql.append("sale1=" + Sale1 + ",");
			}
			if (Sale2 != null) {
				strSql.append("sale2=" + Sale2 + ",");
			}
			if (Sale3 != null) {
				strSql.append("sale3=" + Sale3 + ",");
			}
			if (Sale4 != null) {
				strSql.append("sale4=" + Sale4 + ",");
			}
			if (Sale5 != null) {
				strSql.append("sale5=" + Sale5 + ",");
			}
			if (Remark != null) {
				strSql.append("remark='" + Remark + "',");
			}
			// if (Sizegroupno != null) { 改入商品不允许更改尺码组
			// strSql.append("sizegroupno='" + Sizegroupno.toUpperCase() +
			// "',");
			// }
			if (Useritem1 != null) {
				strSql.append("useritem1='" + Useritem1 + "',");
			}
			if (Useritem2 != null) {
				strSql.append("useritem2='" + Useritem2 + "',");
			}
			if (Useritem3 != null) {
				strSql.append("useritem3='" + Useritem3 + "',");
			}
			if (Useritem4 != null) {
				strSql.append("useritem4='" + Useritem4 + "',");
			}
			if (Useritem5 != null) {
				strSql.append("useritem5='" + Useritem5 + "',");
			}
			if (Imagename != null) {
				strSql.append("imagename='" + Imagename + "',");
			}
			if (Colorid0 != null) {
				strSql.append("colorid0=" + Colorid0 + ",");
			}
			if (Sizeid0 != null) {
				strSql.append("sizeid0=" + Sizeid0 + ",");
			}
			if (Imagename0 != null) {
				strSql.append("imagename0='" + Imagename0 + "',");
			}
			if (Gbbar != null) {
				strSql.append("gbbar='" + Gbbar.toUpperCase() + "',");
			}
			if (Xsamount != null) {
				strSql.append("xsamount=" + Xsamount + ",");
			}
			if (Xdsm != null) {
				strSql.append("xdsm='" + Xdsm + "',");
			}
			if (Zxbz != null) {
				strSql.append("zxbz='" + Zxbz + "',");
			}
			if (Ssdate != null) {
				strSql.append("ssdate='" + Ssdate + "',");
			}
			if (Dj != null) {
				strSql.append("dj='" + Dj + "',");
			}
			if (Jlman != null) {
				strSql.append("jlman='" + Jlman + "',");
			}
			if (Sjman != null) {
				strSql.append("sjman='" + Sjman + "',");
			}
			if (Provid != null) {
				strSql.append("provid=" + Provid + ",");
			}
			if (Locale != null) {
				strSql.append("locale='" + Locale + "',");
			}
			if (Checksale != null) {
				strSql.append("checksale=" + Checksale + ",");
			}
			if (Areaid != null) {
				strSql.append("areaid=" + Areaid + ",");
			}
			if (Wareremark != null) {
				strSql.append("wareremark='" + Wareremark + "',");
			}
			if (Waveid != null) {
				strSql.append("waveid=" + Waveid + ",");
			}
			if (Tjtag != null) {
				strSql.append("tjtag=" + Tjtag + ",");
			}
			if (Salerange != null) {
				strSql.append("salerange='" + Salerange + "',");
			}
			strSql.append("statetag=1,lastop='" + Lastop + "',");
			strSql.append("lastdate=sysdate");
			strSql.append("  where wareid=" + Wareid + " and accid=" + Accid + " ;");
			strSql.append("\n   commit;");
			if (Colorlist != null && Colorlist.trim().length() > 0) {
				String colorlist = Colorlist.trim();

				if (!colorlist.substring(colorlist.length() - 1).equals(","))
					colorlist += ",";
				while (colorlist.toString().length() > 0) {
					int p = colorlist.indexOf(",");
					String colorname = colorlist.substring(0, p).trim();
					if (colorname.length() > 0) {
						strSql.append("\n    begin");
						strSql.append("\n      select colorid into v_colorid from colorcode where accid=" + Accid + " and accid1=0 and statetag=1 and colorname='" + colorname + "' and rownum=1;");
						strSql.append("\n    EXCEPTION WHEN NO_DATA_FOUND THEN");
						strSql.append("\n      v_colorid:=colorcode_colorid.nextval; ");
						strSql.append("\n      insert into colorcode (accid,colorid,colorname,colorno,shortname,statetag,lastop)");
						strSql.append("\n      values (" + Accid + ",v_colorid,'" + colorname + "','','" + PinYin.getPinYinHeadChar(colorname) + "',1,'" + Lastop + "');");
						strSql.append("\n    end;");
						strSql.append("\n    select count(*) into v_count from warecolor where wareid=" + Wareid + " and colorid=v_colorid;");
						strSql.append("\n    if v_count=0  then");
						strSql.append("\n       insert into warecolor (id,wareid,colorid,lastop) values (warecolor_id.nextval," + Wareid + ",v_colorid,'" + Lastop + "'); ");
						strSql.append("\n    end if;");
					}
					colorlist = colorlist.substring(p + 1);
				}

				strSql.append("\n   commit;");
				//				strSql.append("\n   update warecode set colorlist=f_getwarecolorname(wareid) where wareid=v_wareid and accid=" + Accid + ";");
			}

			//			strSql.append("\n  v_retcs:='1操作成功！';");
			//			strSql.append("\n  exception");
			//			strSql.append("\n    when others then ");
			//			strSql.append("\n    v_retcs:='0'||substr(sqlerrm,1,190);");
			//			strSql.append("\n  end;");
			//			strSql.append("\n  <<exit>>");
		} else { // 增加商品

			if (Func.isNull(Sizegroupno)) // 增加商品时尺码组不能为空
			{
				errmess = "尺码组不能为空！";
				return 0;
			}
			// if (Sizegroupno != null) {
			String qry1 = " select groupno from sizecode where accid=" + Accid + " and rownum=1 and statetag=1 and groupno='" + Sizegroupno + "'";
			tb = DbHelperSQL.Query(qry1).getTable(1);
			if (tb.getRowCount() == 0) {
				errmess = "尺码组无效！" + Sizegroupno;
				return 0;
			}
			// }

			if (Autowareno == 0)// 不自动产生货号，要判断货号必须传入
			{
				if (Wareno == null || Wareno.length() <= 0) {
					errmess = "货号无效！";
					return 0;
				}
			}
			StringBuilder strSql1 = new StringBuilder();
			StringBuilder strSql2 = new StringBuilder();
			strSql1.append("wareid,");
			strSql2.append("v_wareid,");
			// if (Wareno != null) {
			// strSql1.append("wareno,");
			// if (Autowareno == 1)
			// strSql2.append("f_autotowareno(" + Accid + "),"); // 自动生成货号
			// else
			// strSql2.append("'" + Wareno.toUpperCase() + "',");
			//
			// }
			// if (Wareno != null) {
			strSql1.append("wareno,");
			strSql2.append("v_wareno,");
			// if (Autowareno == 1 && (Wareno == null || Wareno.length() <= 0))
			// strSql2.append("f_autotowareno(" + Accid + "),"); // 自动生成货号
			// else
			// strSql2.append("'" + Wareno.toUpperCase() + "',");

			// }
			if (Warename != null) {
				strSql1.append("warename,");
				strSql2.append("'" + Func.strLeft(Warename, 50) + "',");
				Shortname = Func.strLeft(PinYin.getPinYinHeadChar(Warename), 20);
			}
			if (Units != null) {
				strSql1.append("units,");
				strSql2.append("'" + Units + "',");
			}
			if (Model != null) {
				strSql1.append("Model,");
				strSql2.append("'" + Model + "',");
			}
			if (Shortname != null) {
				strSql1.append("shortname,");
				strSql2.append("'" + Shortname + "',");
			}
			if (Brandid != null) {
				strSql1.append("brandid,");
				strSql2.append(Brandid + ",");
			}
			if (Seasonname != null) {
				strSql1.append("seasonname,");
				strSql2.append("'" + Seasonname + "',");
			}
			// if (Accid != null) {
			strSql1.append("accid,");
			strSql2.append(Accid + ",");
			// }
			if (Typeid != null) {
				strSql1.append("typeid,");
				strSql2.append(Typeid + ",");
			}
			if (Prodyear != null) {
				strSql1.append("prodyear,");
				strSql2.append("'" + Func.strLeft(Prodyear, 4) + "',");
			}
			if (Prodno != null) {
				strSql1.append("prodno,");
				strSql2.append("'" + Prodno.toUpperCase() + "',");
			}
			if (Entersale != null) {
				strSql1.append("entersale,");
				strSql2.append(Entersale + ",");
			}
			if (Retailsale != null) {
				strSql1.append("retailsale,");
				strSql2.append(Retailsale + ",");
			}
			if (Sale1 != null) {
				strSql1.append("sale1,");
				strSql2.append(Sale1 + ",");
			}
			if (Sale2 != null) {
				strSql1.append("sale2,");
				strSql2.append(Sale2 + ",");
			}
			if (Sale3 != null) {
				strSql1.append("sale3,");
				strSql2.append(Sale3 + ",");
			}
			if (Sale4 != null) {
				strSql1.append("sale4,");
				strSql2.append(Sale4 + ",");
			}
			if (Sale5 != null) {
				strSql1.append("sale5,");
				strSql2.append(Sale5 + ",");
			}
			if (Remark != null) {
				strSql1.append("remark,");
				strSql2.append("'" + Remark + "',");
			}
			if (Sizegroupno != null) {
				strSql1.append("sizegroupno,");
				strSql2.append("'" + Sizegroupno.toUpperCase() + "',");
			}
			if (Useritem1 != null) {
				strSql1.append("useritem1,");
				strSql2.append("'" + Useritem1 + "',");
			}
			if (Useritem2 != null) {
				strSql1.append("useritem2,");
				strSql2.append("'" + Useritem2 + "',");
			}
			if (Useritem3 != null) {
				strSql1.append("useritem3,");
				strSql2.append("'" + Useritem3 + "',");
			}
			if (Useritem4 != null) {
				strSql1.append("useritem4,");
				strSql2.append("'" + Useritem4 + "',");
			}
			if (Useritem5 != null) {
				strSql1.append("useritem5,");
				strSql2.append("'" + Useritem5 + "',");
			}
			// if (Lyfs != null) {
			strSql1.append("lyfs,");
			strSql2.append(Lyfs + ",");
			// }
			if (Accid1 != null) {
				strSql1.append("accid1,");
				strSql2.append(Accid1 + ",");
			}
			if (Wareid1 != null) {
				strSql1.append("wareid1,");
				strSql2.append(Wareid1 + ",");
			}
			if (Tjtag != null) {
				strSql1.append("Tjtag,");
				strSql2.append(Tjtag + ",");
			}
			if (Noused != null) {
				strSql1.append("noused,");
				strSql2.append(Noused + ",");
			}

			if (Gbbar != null) {
				strSql1.append("gbbar,");
				strSql2.append("'" + Func.strLeft(Gbbar, 20) + "',");
			}
			// if (Xsamount != null) {
			strSql1.append("xsamount,");
			strSql2.append("0,");
			// }
			if (Xdsm != null) {
				strSql1.append("xdsm,");
				strSql2.append("'" + Xdsm + "',");
			}
			if (Zxbz != null) {
				strSql1.append("zxbz,");
				strSql2.append("'" + Zxbz + "',");
			}
			if (Ssdate != null) {
				strSql1.append("ssdate,");
				strSql2.append("'" + Ssdate + "',");
			}
			if (Dj != null) {
				strSql1.append("dj,");
				strSql2.append("'" + Dj + "',");
			}
			if (Jlman != null) {
				strSql1.append("jlman,");
				strSql2.append("'" + Jlman + "',");
			}
			if (Sjman != null) {
				strSql1.append("sjman,");
				strSql2.append("'" + Sjman + "',");
			}
			if (Provid != null) {
				strSql1.append("provid,");
				strSql2.append(Provid + ",");
			}
			if (Locale != null) {
				strSql1.append("locale,");
				strSql2.append("'" + Locale + "',");
			}
			if (Checksale != null) {
				strSql1.append("checksale,");
				strSql2.append(Checksale + ",");
			}
			if (Areaid != null) {
				strSql1.append("areaid,");
				strSql2.append(Areaid + ",");
			}
			// if (Kcamount != null) {
			strSql1.append("kcamount,");
			strSql2.append("0,");
			// }
			if (Wareremark != null) {
				strSql1.append("wareremark,");
				strSql2.append("'" + Wareremark + "',");
			}
			if (Waveid != null) {
				strSql1.append("waveid,");
				strSql2.append(Waveid + ",");
			}

			if (Salerange != null) {
				strSql1.append("salerange,");
				strSql2.append("'" + Salerange + "',");
			}
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
			// }
			// if (Downenabled != null) {
			strSql1.append("downenabled,");
			strSql2.append("0,");
			// }
			// if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
			strSql1.append("lastdate");
			strSql2.append("sysdate");

			// if (Autowareno == 0 && Wareno != null && !Wareno.equals("")) //
			// 不是自动产生货号，要判断货号是否重复

			if (Autowareno == 1 && (Wareno == null || Wareno.length() <= 0))
				strSql.append("\n   v_wareno:=f_autotowareno(" + Accid + ");"); // 自动生成货号
			else
				strSql.append("\n   v_wareno:='" + Wareno.toUpperCase() + "';");

			strSql.append("\n   select count(*) into v_count from warecode where wareno=v_wareno and statetag=1 and rownum=1 and accid=" + Accid + ";");
			strSql.append("\n   if v_count>0 then");
			strSql.append("\n      v_retcs:='0货号:'||v_wareno||' 已存在！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");

			strSql.append("\n    v_wareid:=warecode_wareid.nextval; ");
			strSql.append("\n begin ");
			strSql.append("\n    insert into warecode (");
			strSql.append("  " + strSql1.toString());
			strSql.append(")");
			strSql.append(" values (");
			strSql.append("  " + strSql2.toString());
			strSql.append(");");
			strSql.append("\n  commit; ");

			strSql.append("\n  p_autobarcode(v_wareid, " + Barfs + ");"); // 自动产生条码

			if (Colorlist != null && Colorlist.trim().length() > 0) {
				String colorlist = Colorlist.trim();

				if (!colorlist.substring(colorlist.length() - 1).equals(","))
					colorlist += ",";
				while (colorlist.toString().length() > 0) {
					int p = colorlist.indexOf(",");
					String colorname = colorlist.substring(0, p).trim();
					if (colorname.length() > 0) {
						strSql.append("\n    begin");
						strSql.append("\n      select colorid into v_colorid from colorcode where accid=" + Accid + " and accid1=0 and statetag=1 and colorname='" + colorname + "' and rownum=1;");
						strSql.append("\n    EXCEPTION WHEN NO_DATA_FOUND THEN");
						strSql.append("\n      v_colorid:=colorcode_colorid.nextval; ");
						strSql.append("\n      insert into colorcode (accid,colorid,colorname,colorno,shortname,statetag,lastop)");
						strSql.append("\n      values (" + Accid + ",v_colorid,'" + colorname + "','','" + PinYin.getPinYinHeadChar(colorname) + "',1,'" + Lastop + "');");
						strSql.append("\n    end;");
						strSql.append("\n    select count(*) into v_count from warecolor where wareid=v_wareid and colorid=v_colorid;");
						strSql.append("\n    if v_count=0  then");
						strSql.append("\n       insert into warecolor (id,wareid,colorid,lastop) values (warecolor_id.nextval,v_wareid,v_colorid,'" + Lastop + "'); ");
						strSql.append("\n    end if;");
					}
					colorlist = colorlist.substring(p + 1);
				}
				strSql.append("\n   commit;");
				//				strSql.append("\n    update warecode set colorlist=f_getwarecolorname(wareid) where wareid=v_wareid and accid=" + Accid + ";");
			}

			//			strSql.append("\n  v_retcs:='1操作成功！';");
			//			strSql.append("\n  exception");
			//			strSql.append("\n    when others then ");
			//			strSql.append("\n    v_retcs:='0'||substr(sqlerrm,1,190);");
			//			strSql.append("\n  end;");
			//			strSql.append("\n <<exit>>");

		}
		strSql.append("\n  v_retcs:='1操作成功！';");
		strSql.append("\n  exception");
		strSql.append("\n    when others then ");
		strSql.append("\n    v_retcs:='2'||substr(sqlerrm,1,190);");
		strSql.append("\n  end;");
		strSql.append("\n  <<exit>>");

		strSql.append("\n  select v_retcs into :retcs from dual; ");
		strSql.append("\n end; ");
		// System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1).replace("\"", "'");
		if (retcs.substring(0, 1).equals("1")) {
			return 1;
		} else {
			if (retcs.substring(0, 1).equals("2"))
				LogUtils.LogErrWrite("appendwarecode", strSql.toString());
			return 0;
		}
		//		return Integer.parseInt(retcs.substring(0, 1));
	}

	public String doGetWareinfo(int fs) {
		Table tb = new Table();
		String responsestr = "{\"result\":1,\"msg\":\"1\"";
		String qry = "";
		String ss = "";

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
		responsestr += "]";

		responsestr += ",\"seasonlist\":[";
		responsestr += "{\"seasonname\":\"春\"}" + ",{\"seasonname\":\"夏\"}" + ",{\"seasonname\":\"秋\"}" //
				+ ",{\"seasonname\":\"冬\"}" + ",{\"seasonname\":\"春秋\"}" + ",{\"seasonname\":\"春夏\"}" //
				+ ",{\"seasonname\":\"秋冬\"}" + ",{\"seasonname\":\"四季\"}";

		responsestr += "]";
		if (fs == 1) {
			// 取商品默认颜色
			qry = "select uvalue from uparameter where accid=" + Accid + " and usection='OPTIONS' and usymbol='DEFAULTCOLOR'";
			ss = DbHelperSQL.RunSql(qry).toString();
			if (Func.isNull(ss)) {
				responsestr += ",\"COLORID\": \"\"" + ",\"COLORNAME\":\"\"";

			} else {
				qry = "select colorid,colorname from colorcode where accid=" + Accid + " and colorid=" + ss;
				tb = DbHelperSQL.Query(qry).getTable(1);
				if (tb.getRowCount() == 0) {
					responsestr += ",\"COLORID\": \"\"" + ",\"COLORNAME\":\"\"";
				} else {
					responsestr += ",\"COLORID\": \"" + tb.getRow(0).get("COLORID").toString() + "\"" + ",\"COLORNAME\":\"" + tb.getRow(0).get("COLORNAME").toString() + "\"";
				}
			}
			// 取商品尺码颜色
			qry = "select uvalue from uparameter where accid=" + Accid + " and usection='OPTIONS' and usymbol='DEFAULTSIZE'";
			ss = DbHelperSQL.RunSql(qry).toString();
			if (Func.isNull(ss)) {
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
		}
		if (Wareid > 0) // 取商品现在的尺码使用状态
		{
			qry = "  select a.sizeid,a.sizename,a.sizeno, 1 as selbj,f_waresizeisused(" + Wareid + ",a.sizeid ) as lockbj from sizecode a ";
			qry += "  join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno";
			qry += "  where b.wareid=" + Wareid + " and a.statetag=1 and a.noused=0 and a.accid=" + Accid;
			qry += "  and not exists (select 1 from warenosize c where b.wareid=c.wareid and a.sizeid=c.sizeid)";
			qry += "  union all";
			qry += "  select a.sizeid,a.sizename,a.sizeno, 0 as selbj,0 as lockbj from sizecode a ";
			qry += "  join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno";
			qry += "  where b.wareid=" + Wareid + " and a.statetag=1 and a.noused=0 and a.accid=" + Accid;
			qry += "  and  exists (select 1 from warenosize c where b.wareid=c.wareid and a.sizeid=c.sizeid)";
			qry += "  order by sizeno,sizeid";

			tb = DbHelperSQL.Query(qry).getTable(1);
			rs = tb.getRowCount();

			responsestr += ",\"sizeuselist\":[";
			fh = "";
			for (int i = 0; i < rs; i++) {
				responsestr += fh + "{\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"" + ",\"SIZENAME\":\"" + tb.getRow(i).get("SIZENAME").toString() + "\"" + ",\"SELBJ\":\""
						+ tb.getRow(i).get("SELBJ").toString() + "\"" + ",\"LOCKBJ\":\"" + tb.getRow(i).get("LOCKBJ").toString() + "\"}";
				fh = ",";

			}
			responsestr += "]";

		}
		responsestr += "}";
		return responsestr;
	}

	public int doWarenoExists() {
		if (Wareno.equals("")) {
			errmess = "货号无效！";
			return 0;
		}
		if (Exists()) {
			errmess = "货号已使用！";
			return 0;
		} else {
			errmess = "货号可用！";
			return 1;
		}
	}

	// 更改商品图片顺序
	public int Sort(JSONObject jsonObject, int dhhbj) {

		if (!jsonObject.has("sortlist")) {
			errmess = "未传入排序参数！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("sortlist");
		String qry = "declare ";
		qry += "    v_imagename nvarchar2(50); ";
		qry += "\n begin ";
		if (dhhbj == 1) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				Float recid = Float.parseFloat(jsonObject2.getString("imgid"));

				qry += "\n  update orderpicture set orderid=" + (i + 1) + ",lastdate=sysdate,lastop='" + Lastop + "' where wareid=" + Wareid;
				qry += "  and id=" + recid + ";";
			}
			qry += "\n   select imagename into v_imagename from orderpicture where wareid=" + Wareid + " and orderid=1;";
			qry += "\n   update warecode set imagename=v_imagename where wareid=" + Wareid + ";";
		} else {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				Float recid = Float.parseFloat(jsonObject2.getString("imgid"));
				qry += "\n  update warepicture set orderid=" + (i + 1) + ",lastdate=sysdate,lastop='" + Lastop + "' where wareid=" + Wareid;
				qry += "  and id=" + recid + ";";
			}
			qry += "\n   select imagename into v_imagename from warepicture where wareid=" + Wareid + " and orderid=1;";
			qry += "\n   update warecode set imagename0=v_imagename where wareid=" + Wareid + ";";
		}
		qry += "   EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "   v_imagename:='';";
		qry += " end; ";
		int ret = DbHelperSQL.ExecuteSql(qry);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}

	// 返回汇总表商品颜色尺码二维表格式
	public int doListColorandSize(long userid, int fs) {
		if (Wareid == 0) {
			errmess = "wareid不是一个有效的值！";
			return 0;
		}
		String retcs = "\"msg\":\"操作成功\"";
		Table tb = new Table();
		String qry = "select wareid,wareno,warename,retailsale,units,imagename0 from warecode where wareid=" + Wareid;
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "未找到商品！";
			return 0;
		}

		String fh = "";
		for (int j = 0; j < tb.getColumnCount(); j++) {//
			String strKey = tb.getRow(0).getColumn(j + 1).getName().toUpperCase();//
			String strValue = tb.getRow(0).get(j).toString();//
			retcs += ",\"" + strKey + "\":\"" + strValue + "\"";
			//			fh = ",";//
		}
		// 取颜色
		qry = "select a.colorid,b.colorname";
		qry += " FROM warecolor a";
		qry += " left outer join colorcode b on a.colorid=b.colorid";
		qry += " where a.wareid=" + Wareid + " and b.statetag=1 and b.noused=0 order by b.colorno";
		tb = DbHelperSQL.Query(qry).getTable(1);
		fh = "";
		retcs += ",\"colorlist\":[";

		for (int i = 0; i < tb.getRowCount(); i++) {
			retcs += fh + "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"," + "\"COLORNAME\":\"" + tb.getRow(i).get("COLORNAME").toString() + "\"}";
			fh = ",";

		}
		retcs += "]";

		// 取尺码
		qry = "select a.sizeid,a.sizename,a.sizeno  FROM sizecode a";
		qry += " left outer join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno";
		qry += " where a.statetag=1 and a.noused=0 and b.wareid=" + Wareid;
		qry += " and not exists (select 1 from warenosize a1 where b.wareid=a1.wareid and a.sizeid=a1.sizeid)";
		qry += " order by a.sizeno,a.sizename";
		// qry += " where a.statetag=1 and a.noused=0 and b.wareid=" + wareid +
		// " order by a.sizeno,a.sizename";
		tb = DbHelperSQL.Query(qry).getTable(1);
		fh = "";
		retcs += ",\"sizelist\":[";

		for (int i = 0; i < tb.getRowCount(); i++) {
			retcs += fh + "{\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"," + "\"SIZENAME\":\"" + tb.getRow(i).get("SIZENAME").toString() + "\"}";
			fh = ",";

		}
		retcs += "]";

		if (fs == 0)// 库存资源
		{
			qry = "   select colorid,sizeid,sum(amount) as amount";
			qry += "   from v_cxkczy_data a where  epid=" + userid + " and wareid=" + Wareid;
			qry += "   group by colorid,sizeid";

		} else if (fs == 1)// 采购入库汇总
		{
			qry = "   select colorid,sizeid,sum(amount) as amount";
			qry += "   from v_cxwareinhz_data a where  epid=" + userid + " and wareid=" + Wareid;
			qry += "   group by colorid,sizeid";

		} else if (fs == 2)// 销售出库汇总
		{
			qry = "   select colorid,sizeid,sum(amount) as amount";
			qry += "   from v_cxwareouthz_data a where  epid=" + userid + " and wareid=" + Wareid;
			qry += "   group by colorid,sizeid";

		} else if (fs == 3)// 调拨入库汇总
		{
			qry = "   select colorid,sizeid,sum(amount) as amount";
			qry += "   from v_cxallotinhz_data a where  epid=" + userid + " and wareid=" + Wareid;
			qry += "   group by colorid,sizeid";

		} else if (fs == 4)// 调拨出库汇总
		{
			qry = "   select colorid,sizeid,sum(amount) as amount";
			qry += "   from v_cxallotouthz_data a where  epid=" + userid + " and wareid=" + Wareid;
			qry += "   group by colorid,sizeid";

		} else if (fs == 5)// 采购订货汇总
		{
			qry = "   select colorid,sizeid,sum(amount) as amount";
			qry += "   from v_cxprovorderhz_data a where  epid=" + userid + " and wareid=" + Wareid;
			qry += "   group by colorid,sizeid";

		} else if (fs == 6)// 客户订货汇总
		{
			qry = "   select colorid,sizeid,sum(amount) as amount";
			qry += "   from v_cxcustorderhz_data a where  epid=" + userid + " and wareid=" + Wareid;
			qry += "   group by colorid,sizeid";

		} else if (fs == 7)// 调拨订货汇总
		{
			qry = "   select colorid,sizeid,sum(amount) as amount";
			qry += "   from v_cxallotorderhz_data a where  epid=" + userid + " and wareid=" + Wareid;
			qry += "   group by colorid,sizeid";

		}
		tb = DbHelperSQL.Query(qry).getTable(1);
		fh = "";

		retcs += ",\"amountlist\":[";

		for (int i = 0; i < tb.getRowCount(); i++) {
			retcs += fh + "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"," + "\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"," + "\"AMOUNT\":\"" + tb.getRow(i).get("AMOUNT").toString()
					+ "\"}";
			fh = ",";

		}
		retcs += "]";
		errmess = retcs;
		return 1;
	}

	// 下载
	public int doDown(String downdate)// downdate="",同步所有数据，只同步更新的数据
	{
		StringBuilder sql = new StringBuilder();
		String headsql = "";
		boolean isupdate = false;
		String qry = "select wareid,wareno,warename,shortname,units,model,prodyear,typeid,brandid,provid,seasonname,sizegroupno"//
				+ ",entersale,retailsale,sale1,sale2,sale3,sale4,sale5,statetag,imagename0,remark,tjtag,accid";
		qry += "\n from warecode where accid=" + Accid;
		if (downdate.length() <= 0) {
			qry += " and statetag=1 and noused=0";
			headsql = " insert ";
		} else {
			qry += " and lastdate>=to_date('" + downdate + "','yyyy-mm-dd hh24:mi:ss')";
			isupdate = true; // 更改
			headsql = " replace ";
		}
		Table tb = new Table();
		// System.out.println(qry);
		tb = DbHelperSQL.GetTable(qry);

		if (!isupdate) {
			sql.append("\n begin;");
			sql.append("\n delete from warecode where accid=" + Accid + ";");
			sql.append("\n commit;");
		}
		sql.append("\n begin;");

		for (int i = 0; i < tb.getRowCount(); i++) {
			String sql1 = "";// "accid";
			String sql2 = "";// Accid.toString();
			for (int j = 0; j < tb.getColumnCount(); j++) {//
				String fieldtype = tb.getMeta().get(j + 1).getType();

				String strKey = tb.getRow(i).getColumn(j + 1).getName();//
				String strValue = "";
				if (fieldtype.equals("NUMBER"))
					strValue = tb.getRow(i).get(j).toString();//
				else
					strValue = "'" + tb.getRow(i).get(j).toString() + "'";//
				sql1 += strKey + ",";
				sql2 += strValue + ",";

			}
			sql.append("\n " + headsql + " into warecode (" + sql1 + " lastdate) values (" + sql2 + " datetime('now','localtime'));");
			if (i > 0 && i % 100 == 0) {
				sql.append("\n commit;");
				sql.append("\n begin;");
			}
		}
		sql.append("\n commit;");

		String path = Func.getRootPath();
		String filename = "down" + Func.desEncode(Accid + "_" + Func.DateToStr(new Date(), "yyyyMMddHHmmss")).toLowerCase() + ".sql";// "brand.sql";
		String savefile = path + "/temp/" + filename;
		PrintStream printStream;
		try {
			printStream = new PrintStream(new FileOutputStream(savefile));
			printStream.println(sql.toString());
			printStream.close();
			errmess = "/temp/" + filename;
			return 1;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errmess = "操作失败：" + e.getMessage();
			return 0;
		}

	}

	// 上传商品图片
	public int Updatepict(String pictjson) {
		if (Wareid == 0) {
			errmess = "wareid不是一个有效值！";
			return 0;
		}
		if (pictjson == null || pictjson.equals("")) {
			errmess = "未传入图片组参数:pictjson！";
			return 0;
		}
		JSONObject jsonObject = JSONObject.fromObject(pictjson);
		if (!jsonObject.has("base64string")) {
			errmess = "未传入图片值参数:base64string！";
			return 0;
		}
		String base64string = jsonObject.getString("base64string");
		String retcs = Func.Base64UpFastDFS(base64string);
		if (retcs.substring(0, 1).equals("1")) {
			Imagename0 = retcs.substring(1);
		} else {
			errmess = "商品图片上传失败！" + retcs.substring(1);
			return 0;
		}
		String qry = "declare ";
		qry += "\n   v_imagename0 varchar2(60); ";
		qry += "\n   v_imagename1 varchar2(60); ";
		qry += "\n   v_count number(10); ";
		qry += "\n   v_id number; ";
		qry += "\n begin ";
		// if (dhhbj == 1)
		// {
		// qry += " update warecode set imagename='" + imagename +
		// "',lastdate=sysdate,lastop='" + lastop + "' where wareid=" + wareid +
		// "; ";
		// }
		// else
		// {
		qry += "\n    select imagename0 into v_imagename0 from warecode where accid=" + Accid + " and wareid=" + Wareid + ";";

		qry += "\n    v_imagename1:='';";
		qry += "\n    update warecode set imagename0='" + Imagename0 + "',lastdate=sysdate,lastop='" + Lastop + "' where wareid=" + Wareid + "; ";
		qry += "\n    begin";
		qry += "\n      select id,imagename into v_id,v_imagename1  from  warepicture where wareid=" + Wareid + " and orderid=1 and rownum=1; ";
		qry += "\n      update warepicture set imagename='" + Imagename0 + "',lastop='" + Lastop + "',lastdate=sysdate where  wareid=" + Wareid + " and id=v_id;";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n      insert into warepicture (id,wareid,imagename,orderid,remark,lastop,lastdate)";
		qry += "\n      values (warepicture_id.nextval," + Wareid + ",'" + Imagename0 + "',1,'','" + Lastop + "',sysdate); ";
		qry += "\n      v_imagename1:='';";
		qry += "\n    end;";
		// }
		qry += "\n    select nvl(v_imagename0,''),nvl(v_imagename1,'') into :imagename0,:imagename1 from dual; ";
		qry += "\n end; ";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("imagename0", new ProdParam(Types.VARCHAR));
		param.put("imagename1", new ProdParam(Types.VARCHAR));
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		errmess = Imagename0;

		// LogUtils.LogDebugWrite("AddWarecodepicture 0", "ok === " + errmess);
		// String imagename0 =
		// param.get("imagename0").getParamvalue().toString();
		// String imagename1 =
		// param.get("imagename1").getParamvalue().toString();

		// LogUtils.LogDebugWrite("AddWarecodepicture 1", "imagename0=" +
		// imagename0 + " imagename1=" + imagename1);
		// 删除原来的图片
		// if (!Func.isNull(imagename0))
		// try {
		// LogUtils.LogDebugWrite("AddWarecodepicture 2", "delete begin...");
		// Func.fastDFSDelete(imagename0);
		// LogUtils.LogDebugWrite("AddWarecodepicture 2", "delete ok");
		// } catch (Exception e) {
		// LogUtils.LogErrWrite("Warecode.Updatepict", "Err1045:" +
		// e.getMessage());
		// }

		// if (!Func.isNull(imagename1))
		// try {
		// LogUtils.LogDebugWrite("AddWarecodepicture 3", "delete begin...");
		// Func.fastDFSDelete(imagename1);
		// LogUtils.LogDebugWrite("AddWarecodepicture 3", "delete ok");
		// } catch (Exception e) {
		// LogUtils.LogErrWrite("Warecode.Updatepict", "Err1044:" +
		// e.getMessage());
		// }
		// LogUtils.LogDebugWrite("AddWarecodepicture 4", "end");

		return 1;
	}

	//分享商品资料
	public int doShareWarecode() {
		if (Wareid == 0) {
			errmess = "wareid不是一个有效值！";
			return 0;
		}
		Table tb = new Table();
		//		String company = "";
		//		String tel = "";
		//		String address = "";
		//		String qry = "select company,tel,address from accreg where accid=" + Accid;
		//		tb = DbHelperSQL.Query(qry).getTable(1);// DbHelperSQL.GetTable(qry);
		//		if (tb.getRowCount() > 0) {
		//			company = tb.getRow(0).get("COMPANY").toString();
		//		}

		String qry = "select a.warename,a.wareno,a.units,a.retailsale,a.sale4 as wholesale,b.brandname,a.seasonname,a.prodyear,a.imagename0";
		qry += ",f_getwarecolorname(a.wareid) as colornamelist,f_getwaresizename(a.wareid) as sizenamelist";
		qry += "\n from warecode a ";
		qry += "\n left outer join brand b on a.brandid=b.brandid";
		qry += "\n where a.accid=" + Accid + " and a.wareid=" + Wareid;
		qry += "\n and a.statetag=1";
		//		System.out.println(qry);
		tb = DbHelperSQL.Query(qry).getTable(1);// DbHelperSQL.GetTable(qry);
		if (tb.getRowCount() == 0) {
			errmess = "未找到商品！";
			return 0;
		}

		String qry1 = "select a.imagename";
		qry1 += "\n from warepicture a ";
		qry1 += "\n where a.wareid=" + Wareid;
		qry1 += "\n order by orderid";
		//		System.out.println(qry1);
		Table tb1 = DbHelperSQL.Query(qry1).getTable(1);// DbHelperSQL.GetTable(qry);
		if (tb1.getRowCount() == 0) {
			errmess = "商品无图片，不能分享！";
			return 0;
		}
		String path = Func.getRootPath();
		String filename = "sp" + Func.desEncode(Accid + "_" + Wareid + "_" + Func.getRandomNumA(5)).toLowerCase() + ".html";
		String htmlfile = path + "/temp/" + filename;
		StringBuilder sb = new StringBuilder();
		try {
			PrintStream printStream = new PrintStream(new FileOutputStream(htmlfile));

			//			sb.append("<!DOCTYPE html>");
			//			sb.append("<html lang=\"zh-CN\">");
			//			sb.append("<head>");

			sb.append("<!DOCTYPE html>");
			sb.append("<html lang=\"en\">");
			sb.append("<head>");
			sb.append("    <meta charset=\"UTF-8\">");
			sb.append("    <title>蓝窗店管家-移动销售管理专家</title>");
			sb.append("    <link rel=\"shortcut icon\" href=\"../share/ware/src/favicon.ico\">");
			sb.append("    <meta name=\"viewport\"");
			sb.append("          content=\"width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no\">");
			sb.append("    <link rel=\"stylesheet\" href=\"../share/ware/lib/bootstrap/css/bootstrap.min.css\">");
			sb.append("    <link rel=\"stylesheet\" href=\"../share/ware/css/share-ware.css\">");
			//			<link rel="stylesheet" href="../share/ware/css/documents.css"
			sb.append("</head>");
			sb.append("<body>");
			sb.append("<div class=\"container\">");
			//			    <!--商品图片-->
			sb.append("<div id=\"goods_images_container\" class=\"swiper-container\">");

			sb.append("<div class=\"swiper-wrapper\">");

			for (int i = 0; i < tb1.getRowCount(); i++) {
				sb.append("<div class=\"swiper-slide\"><img src=\"https://dfs.skydispark.com/image/" + tb1.getRow(i).get("IMAGENAME").toString() + "\" alt=\"\"></div>");
			}
			sb.append("</div>");
			sb.append("<div class=\"swiper-pagination\"></div>");
			sb.append("</div>");
			//			    <!--商品名称、货号、售价等信息-->
			sb.append("<div class=\"ware-name-info\">");
			sb.append("   <div><p class=\"light-textColor\">" + tb.getRow(0).get("WARENAME").toString() + "</p></div>");
			sb.append("   <div><p class=\"textColor\">" + tb.getRow(0).get("WARENO").toString() + "</p></div>");
			sb.append("   <div class=\"price-area\">");
			Sale4 = Float.parseFloat(tb.getRow(0).get("WHOLESALE").toString());
			Retailsale = Float.parseFloat(tb.getRow(0).get("RETAILSALE").toString());
			sb.append("        <div><span class=\"light-textColor\">零售价：</span><span class=\"price-mark\">¥</span><span class=\"price-num\">" + Func.FormatNumber(Retailsale, 2) + "</span></div>");
			if (Sale4 > 0)
				sb.append("        <div><span class=\"light-textColor\">批发价：</span><span class=\"price-mark\">¥</span><span class=\"price-num\">" + Func.FormatNumber(Sale4, 2) + "</span></div>");
			sb.append("    </div>");
			sb.append("</div>");
			//			    <!--商品详细信息-->
			sb.append("<div class=\"ware-info\">");

			sb.append("     <div class=\"row cell\">");
			sb.append("       <div class=\"cell-title col-xs-3 light-textColor\">品牌</div>");
			sb.append("       <div class=\"cell-content col-xs-7 textColor\">" + tb.getRow(0).get("BRANDNAME").toString() + "</div>");
			sb.append("     </div>");

			sb.append("     <div class=\"row cell\">");
			sb.append("       <div class=\"cell-title col-xs-3 light-textColor\">季节</div>");
			sb.append("       <div class=\"cell-content col-xs-7 textColor\">" + tb.getRow(0).get("SEASONNAME").toString() + "</div>");
			sb.append("     </div>");

			sb.append("     <div class=\"row cell\">");
			sb.append("       <div class=\"cell-title col-xs-3 light-textColor\">生产年份</div>");
			sb.append("       <div class=\"cell-content col-xs-7 textColor\">" + tb.getRow(0).get("PRODYEAR").toString() + "</div>");
			sb.append("     </div>");

			sb.append("     <div class=\"row cell\">");
			sb.append("       <div class=\"cell-title col-xs-3 light-textColor\">颜色</div>");
			sb.append("       <div class=\"cell-content col-xs-7 textColor\">" + tb.getRow(0).get("COLORNAMELIST").toString() + "</div>");
			sb.append("     </div>");

			sb.append("     <div class=\"row cell\">");
			sb.append("       <div class=\"cell-title col-xs-3 light-textColor\">尺码</div>");
			sb.append("       <div class=\"cell-content col-xs-7 textColor\">" + tb.getRow(0).get("SIZENAMELIST").toString() + "</div>");
			sb.append("     </div>");

			sb.append("    </div>");

			sb.append("</div>");
			//			sb.append("  <div class=\"bottom-info light-textColor\">");
			//			sb.append("    蓝窗店管家-移动销售管理专家");
			//			sb.append("	 </div>");

			sb.append(" <div class=\"advertising container\">");
			sb.append("   <a class=\"light-text-color\" href=\"http://www.skydispark.com\" >");
			sb.append("   <img src=\"../share/ware/icon.png\"  style=\"width: 15px;height: 15px;\" >");
			sb.append("   <span>我在用</span><span class=\"tick-text-color\">蓝窗店管家</span><span>管理店铺，推荐你也使用</span>");
			sb.append("   </a>");
			sb.append(" </div>");

			sb.append("</body>");
			sb.append("<script src=\"../share/ware/lib/swiper/swiper.min.js\"></script>");
			sb.append(" 	<script>");
			sb.append("	      var swiper = new Swiper('#goods_images_container', {");
			sb.append("		  pagination: '.swiper-pagination',");
			sb.append("		  slidesPerView: 1,");
			sb.append("		  paginationClickable: true,");
			sb.append("		  spaceBetween: 0");
			sb.append("	       });");
			sb.append("	   </script>");
			sb.append("</html>");

			printStream.println(sb.toString());
			printStream.close();
			errmess = "/temp/" + filename;
			return 1;
			// printStream.
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			errmess = "操作异常！" + e.getMessage();
			return 0;
		}
	}
}