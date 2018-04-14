package com.oracle.bean;
 import java.sql.Types;
 import java.text.DateFormat;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.Map;
 import com.comdot.data.DataSet;
 import com.comdot.data.Table;
 import com.common.tool.PinYin;
 import com.netdata.db.DbHelperSQL;
 import com.netdata.db.ProdParam;
 import com.netdata.db.QueryParam;
 import net.sf.json.JSONObject;
//*************************************
//  @author:sunhong  @date:2017-03-14 09:32:16
//*************************************
 public class Guesttye  implements java.io.Serializable  {
 private String errmess;
 //private String Accbegindate = "2000-01-01"; // 账套开始使用日期
  public String getErrmess() { // 取错误提示
  	return errmess;
  }
  //public void setAccbegindate(String accbegindate) {
  //	  this.Accbegindate = accbegindate;
  //}
  //删除记录 
  public int Delete() {
	StringBuilder strSql = new StringBuilder();
	strSql.append("select count(*) as num from (");
	//strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
	strSql.append(")");
	if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		errmess = "当前记录已使用，不允许删除";
		return 0;
	}
	strSql.setLength(0);
	//strSql.append(" update Guesttye set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
	//strSql.append(" where id=" + id + " and accid=" + Accid);
	//if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
	//	errmess = "删除失败！";
	//	return 0;
   //}
	return 1;}
 //增加记录 
 public int Append() {
	StringBuilder strSql = new StringBuilder();
	StringBuilder strSql1 = new StringBuilder();
	StringBuilder strSql2 = new StringBuilder();
	strSql1.append("id,");
	strSql2.append("v_id,");
	strSql1.append("lastdate");
	strSql2.append("sysdate");
	strSql.append("declare ");
	strSql.append("   v_id number; ");
	strSql.append(" begin ");
	strSql.append("   v_id:=Guesttye_id.nextval; ");
	strSql.append("   insert into Guesttye (");
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
	//id = Long.parseLong(param.get("id").getParamvalue().toString());
	if (ret < 0)
		return 0;
	else
		return 1;}
   //修改记录 
   public int Update() {
	StringBuilder strSql = new StringBuilder();
	strSql.append("begin ");
   strSql.append("   update Guesttye set ");
   strSql.append("lastdate=sysdate");
   strSql.append("  where id=id ;");
   strSql.append("  commit;");
	strSql.append(" end;");
	int ret= DbHelperSQL.ExecuteSql(strSql.toString());
	if (ret < 0)
		return 0;
	else
		return 1;
}
   // 获得数据列表
   public DataSet GetList(String strWhere, String fieldlist) {
	StringBuilder strSql = new StringBuilder();
	strSql.append("select " + fieldlist);
	strSql.append(" FROM Guesttye ");
	if (!strWhere.equals("")) {
		strSql.append(" where " + strWhere);
	}
	return DbHelperSQL.Query(strSql.toString());
}
  // 获取分页数据
    public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
	  StringBuilder strSql = new StringBuilder();
	  strSql.append("select " + fieldlist);
	  strSql.append(" FROM Guesttye ");
     if (!strWhere.equals("")) {
		strSql.append(" where " + strWhere);
	  }
	qp.setQueryString(strSql.toString());
	return DbHelperSQL.GetTable(qp);
}
  public boolean Exists() {
  	StringBuilder strSql = new StringBuilder();
  	strSql.append("select count(*) as num  from Guesttye");
//	strSql.append(" where brandname='" + Brandname + "' and statetag=1  and rownum=1 and accid=" + Accid);
//  	if (Brandid != null)
//  		strSql.append(" and brandid<>" + Brandid);
//  	if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
//  		errmess = "???:" + Brandname + " 已存在，请重新输入！";
//  		return true;
//  	}
  	return false;
  }}