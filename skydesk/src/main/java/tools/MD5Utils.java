package tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    public static String getMD5(String value) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        String new_value = "FlyAng" + value + "@2015";

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(new_value.getBytes("utf-8"));

        byte[] m = md5.digest();
        return getString(m).toUpperCase();

    }
//149466EBC3181FED5C17EDA6507E1D69
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if(DataBase.isCS)
			System.out.println(getMD5("18837174612"));
	}
    public static String getString(byte[] b) {
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            int val = ((int) b[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
//可逆家加密算法，加密和解密
    //加密
    public static String KL(String inStr) {
        // String s = new String(inStr);  
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^'t');
        }
        String s = new String(a);
        return s;
    }

// 加密后解密  
    public static String JM(String inStr) {
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i]^'t');
        }
        String k = new String(a);
        return k;
    }

}
