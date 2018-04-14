package tools;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {

	/**
	 * 
	 * @param str
	 *            需要几米的字符串
	 * @param Key
	 *            加密的密钥
	 * @return 返回加密后的字符串
	 * @throws NullPointerException
	 */
	public String aesEncrypt(String str, String key) {
		if (str == null || key == null)
			return null;
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("UTF-8"), "AES"));
			byte[] bytes = cipher.doFinal(str.getBytes("UTF-8"));
			return Base64.getEncoder().encodeToString(bytes).trim().replaceAll("\r\n", "").replaceAll("\n", "").replaceAll("\t", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
/**
 * 
 * 解密
 * @param str
 * @param key
 * @return
 */
	public String aesDecrypt(String str, String key) {
		
		if (str == null || key == null)
			return null;
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("UTF-8"), "AES"));
			byte[] bytes = Base64.getDecoder().decode(str);
			bytes = cipher.doFinal(bytes);
			return new String(bytes, "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		AESUtils aesUtils = new AESUtils();
//		String res = aesUtils.aesEncrypt("abcdefg1234567890张明𤇍ABCD@#$%", "sun81xjh34wmg91zhb16ccy35lk82xjw");
		String res = aesUtils.aesEncrypt("{\"accname\":\"YEWENFOREVER\",\"fieldlist\":\"*\",\"flyang\":\"20150107\"}","980552e6c01e83fb85381ffbb977461d");
		System.out.println("加密：" + res);
		
//		 String ores= aesUtils.aesDecrypt("2qRGKWbQAxmDDbPdJ4cakQyVDtP13dFz63beRmAFgdiwOpOtLfrWQu8ef2LWk0oG", "sun81xjh34wmg91zhb16ccy35lk82xjw");
//		 System.out.println("解密：" + ores);
//		 System.out.println("解密11：" + ToolUtils.getBase64String("123456789"));
		/*JSONObject jsonObject = JSONObject.fromObject("{\"key\":\"value\"}");
		JSONArray jsonArray = JSONArray.fromObject("[{\"key\":\"value1\",\"total\":1},{\"key\":\"value2\",\"total\":2}]");
		String value = jsonObject.getString("key");
		System.out.println("jsonValue===" + value);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			String value2 = jsonObject2.getString("key");
			System.out.println("jsonString===" + value2);
			int total = jsonObject2.getInt("total");
			System.out.println("jsonInt===" + total);
		}*/
	}

}