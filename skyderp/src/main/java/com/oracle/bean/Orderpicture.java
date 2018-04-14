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
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-16 10:40:21
//*************************************
public class Orderpicture implements java.io.Serializable {
	private Float Id;
	private Long Wareid;
	private String Imagename;
	private Long Orderid;
	private String Remark;
	private String Lastop;
	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setWareid(Long wareid) {
		this.Wareid = wareid;
	}

	public Long getWareid() {
		return Wareid;
	}

	public void setImagename(String imagename) {
		this.Imagename = imagename;
	}

	public String getImagename() {
		return Imagename;
	}

	public void setOrderid(Long orderid) {
		this.Orderid = orderid;
	}

	public Long getOrderid() {
		return Orderid;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		String qry = "declare ";
		qry += "\n    v_count number;";
		qry += "\n    v_orderid number;";
		qry += "\n    v_id number;";
		qry += "\n    v_imagename varchar2(60);";
		qry += "\n    v_imagename0 varchar2(60);";
		qry += "\n    v_retcs varchar2(200);";
		qry += "\n begin ";
		qry += "\n    v_imagename0:='';";
		qry += "\n    begin";
		qry += "\n      select imagename,orderid into v_imagename,v_orderid from orderpicture where wareid=" + Wareid + " and id=" + Id + ";";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n      v_retcs:= '0未找到商品图片记录！';";
		qry += "\n      goto exit; ";
		qry += "\n    end;";
		qry += "\n    delete from orderpicture where wareid=" + Wareid + " and id=" + Id + ";";
		qry += "\n    if v_orderid=1 then "; // 如果删除的是第一张图片,找到第2张图，将orderid改为1
		qry += "\n       begin";
		qry += "\n         select id,imagename into v_id,v_imagename0 from (select id,imagename from orderpicture where wareid=" + Wareid + " order by orderid,id) where rownum=1;";
		qry += "\n         update orderpicture set orderid=1 where id=v_id and wareid=" + Wareid + ";";
		qry += "\n       EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n         v_id:=0;  ";
		qry += "\n       end;";
		qry += "\n       update warecode set imagename=v_imagename0 where wareid=" + Wareid + ";";
		qry += "\n    end if;";
		qry += "\n    commit;";
		qry += "\n    p_ordersortpicture(" + Wareid + ");"; // 重新排序
		qry += "\n    v_retcs:= '1'||v_imagename;";
		qry += "\n    <<exit>> ";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end; ";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		if (retcs.substring(0, 1).equals("1")) {
			Imagename = retcs.substring(1);
			if (!Imagename.equals("")) // 删除原图片
			{
//				Func.fastDFSDelete(Imagename);
			}
			errmess = "操作成功！";
			return 1;
		} else {
			errmess = retcs.substring(1);
			return 0;
		}
	}

	// 增加记录
	public int Append(long accid, String pictjson) {
		if (Wareid == 0) {
			errmess = "缺少商品id参数！";
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

		StringBuilder strSql = new StringBuilder();

		strSql.append(" declare ");
		strSql.append("\n   v_id number;");
		strSql.append("\n   v_count number;");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n begin ");
		strSql.append("\n    select count(*) into v_count from warecode where accid=" + accid + " and wareid=" + Wareid + ";");
		strSql.append("\n    if v_count=0 then ");
		strSql.append("\n       v_retcs:='0未找到商品！'; ");
		strSql.append("\n       goto exit;");
		strSql.append("\n    end if; ");
		strSql.append("\n    select count(*) into v_count from orderpicture where wareid=" + Wareid + ";");
		strSql.append("\n    if v_count>=10 then ");
		strSql.append("\n       v_retcs:='0商品图片最多只允许上传10张！'; ");
		strSql.append("\n       goto exit;");
		strSql.append("\n    end if; ");
		strSql.append("\n   v_count:=v_count+1;");
		strSql.append("\n   v_id:=orderpicture_id.nextval;");
		strSql.append("\n   insert into orderpicture (id,wareid,imagename,orderid,remark,lastop,lastdate)");
		strSql.append("\n   values (v_id," + Wareid + ",'" + Imagename + "',v_count,'','" + Lastop + "',sysdate); ");
		// 如果是第一张图，自动更新商品编码的图片
		strSql.append("\n   if v_count=1 then ");
		strSql.append("\n      update warecode set imagename='" + Imagename + "' where wareid=" + Wareid + ";");
		strSql.append("\n   end if; ");
		strSql.append("\n   v_retcs:='1'||v_id;");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		// if (master == 1) qry += " update warecode set imagename0='" + imagename + "' where wareid=" + wareid + "; ";
		strSql.append("\n end; ");

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		if (retcs.substring(0, 1).equals("1")) {
			errmess = "\"msg\": \"1\",\"msg\":\"操作成功！\",\"IMGID\": \"" + errmess + "\"" + ",\"IMAGENAME\": \"" + Imagename + "\"";

			return 1;
		} else {
			return 0;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Orderpicture set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Wareid != null) {
			strSql.append("wareid=" + Wareid + ",");
		}
		if (Imagename != null) {
			strSql.append("imagename='" + Imagename + "',");
		}
		if (Orderid != null) {
			strSql.append("orderid=" + Orderid + ",");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Orderpicture ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 修改记录
	public int Update(String pictjson) {
		if (Wareid == 0 || Id == 0) {
			errmess = "缺少商品或记录id参数！";
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

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_orderid number(10);");
		strSql.append("\n begin");

		strSql.append("\n   select orderid into v_orderid from orderpicture where wareid=" + Wareid + " and id=" + Id + ";");
		strSql.append("\n   update orderpicture set imagename='" + Imagename + "' where wareid=" + Wareid + " and id=" + Id + ";");
		strSql.append("\n   if v_orderid=1 then"); // 更改主图
		strSql.append("\n      update warecode set imagename='" + Imagename + "' where wareid=" + Wareid + ";");
		strSql.append("\n   end if;");
		// qry = "update warepicture set imagename='' where wareid=" + wareid + " and id=" + id;
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN ");
		strSql.append("\n   v_orderid:=0;");
		strSql.append("\n end;");
		if (DbHelperSQL.ExecuteSql(strSql.toString()) == 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;

		}
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Orderpicture ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取所有数据
	public Table GetTable(String qry) {

		return DbHelperSQL.GetTable(qry);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Orderpicture");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}
}