package tools;

import net.sf.json.JSONArray;

public class MyjsonUtils {
	/**
	 * 把两个JSONarry 转成一个JSONArray
	 * @param mData
	 * @param array
	 * @return
	 */
	public JSONArray joinJsonArry(JSONArray mData, JSONArray array){
		mData.addAll(JSONArray.toCollection(array));
		return mData;
	}
}
