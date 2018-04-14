package mains.help;


import tools.AppData;
import tools.DataBase;

public class waretypehelp {
	
	public static String getaccwaretype(String i, AppData appData){
		//TODO 获取账户商品类型
		String url = DataBase.HostIP +"accwaretypelist&page=1&rows="+appData.pagecount+"&accid="+appData.accid+"&p_typeid="+i
				+"&fieldlist=a.TYPEID,a.TYPENAME,a.FULLNAME,a.P_TYPEID,a.LASTNODE"+DataBase.signature(appData);
		String jsonstr = DataBase.getJsonContent(url);
		if(appData.isTotal(jsonstr)){
			return jsonstr;
		}
		return "f";
	}
}
