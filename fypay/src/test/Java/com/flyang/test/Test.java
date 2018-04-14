package com.flyang.test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Base64;

import org.apache.commons.codec.digest.DigestUtils;

public class Test {
	public static void main(String[] args) throws IOException {
		String str = "1234567890";
		byte[] bstr = DigestUtils.sha1(str);
		System.out.println(AnalyticData(bstr,bstr.length));
		String base64str = Base64.getEncoder().encodeToString(bstr);
		System.out.println(base64str);
		
		
	}
	private static ByteBuffer buffer = ByteBuffer.allocate(8); 
	/// 解析客户端数据包
	/// <param name="recBytes">服务器接收的数据包</param>
	/// <param name="recByteLength">有效数据长度</param> 
	private static String AnalyticData(byte[] recBytes, int recByteLength) throws IOException
	{
	    if(recByteLength < 2)
	    {
	        return "";
	    }

	    boolean fin = (recBytes[0] & 0x80) == 0x80; // 1bit，1表示最后一帧
	    if(!fin)
	    {
	        return "";// 超过一帧暂不处理
	    }

	    boolean mask_flag = (recBytes[1] & 0x80) == 0x80; // 是否包含掩码
	    if(!mask_flag)
	    {
	        return "";// 不包含掩码的暂不处理
	    }

	    int payload_len = recBytes[1] & 0x7F; // 数据长度

	    byte[] masks = new byte[4];
	    byte[] payload_data;

	    if(payload_len == 126)
	    {
	        System.arraycopy(recBytes, 4, masks, 0, 4);
	        payload_len = (int)(recBytes[2] << 8 | recBytes[3]);
	        payload_data = new byte[payload_len];
	        System.arraycopy(recBytes, 8, payload_data, 0, payload_len);

	    }
	    else if(payload_len == 127)
	    {
	    	System.arraycopy(recBytes, 10, masks, 0, 4);
	        byte[] uInt64Bytes = new byte[8];
	        for(int i = 0; i < 8; i++)
	        {
	            uInt64Bytes[i] = recBytes[9 - i];
	        }
	        long len = buffer.put(uInt64Bytes, 0,uInt64Bytes.length).getLong();

	        payload_data = new byte[(int) len];
	        for(int i = 0; i < len; i++)
	        {
	            payload_data[i] = recBytes[i + 14];
	        }
	    }
	    else
	    {
	    	System.arraycopy(recBytes, 2, masks, 0, 4);
	        payload_data = new byte[payload_len];
	        System.arraycopy(recBytes, 6, payload_data, 0, payload_len);

	    }

	    for(int i = 0; i < payload_len; i++)
	    {
	        payload_data[i] = (byte)(payload_data[i] ^ masks[i % 4]);
	    }
	    return new String(payload_data,"utf-8");
	}
}
