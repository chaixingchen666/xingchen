package tools;

import java.io.UnsupportedEncodingException;

public class CodeToString {
	//处理中文字符串的函数
			public String codeString(String str){
				String s = str;
				try {
					byte[] temp = s.getBytes("utf-8");
					s = new String(temp);
					return s ;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return s;
				}
			}
}
