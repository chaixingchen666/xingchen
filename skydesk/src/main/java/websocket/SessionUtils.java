package websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

/**
 * 功能说明：用来存储业务定义的sessionId和连接的对应关系 利用业务逻辑中组装的sessionId获取有效连接后进行后续操作
 * 作者：liuxing(2014-12-26 02:32)
 */
public class SessionUtils {

	public static Map<String, Session> clients = new ConcurrentHashMap<>();

	public static void put(String relationId, int userCode, Session session) {
		clients.put(getKey(relationId, userCode), session);
	}

	public static Session get(String relationId, int userCode) {
		return clients.get(getKey(relationId, userCode));
	}

	public static void remove(String relationId, int userCode) {
		clients.remove(getKey(relationId, userCode));
	}

	/**
	 * 判断是否有连接
	 * 
	 * @param relationId
	 * @param userCode
	 * @return
	 */
	public static boolean hasConnection(String relationId, int userCode) {
		return clients.containsKey(getKey(relationId, userCode));
	}

	/**
	 * 组装唯一识别的key
	 * 
	 * @param relationId
	 * @param userCode
	 * @return
	 */
	public static String getKey(String relationId, int userCode) {
		return relationId + "_" + userCode;
	}

	/**
	 * 将数据传回客户端 同步的的方式
	 * 
	 * @param relationId
	 * @param userCode
	 * @param message
	 */
	public synchronized static void broadcast(String relationId, int userCode, String message) {
		if (hasConnection(relationId, userCode)) {
//			get(relationId, userCode).getAsyncRemote().sendText(message);
			try {
				get(relationId, userCode).getBasicRemote().sendText(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			throw new NullPointerException(
					getKey(relationId, userCode) + "Connection does not exist");
		}

	}
	/**
	 * 将数据传回客户端 异步的方式
	 * 
	 * @param relationId
	 * @param userCode
	 * @param message
	 */
	public synchronized static void broadcast1(String relationId, int userCode, String message) {
		if (hasConnection(relationId, userCode)) {
		    get(relationId, userCode).getAsyncRemote().sendText(message);
//			try {
//				get(relationId, userCode).getBasicRemote().sendText(message);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		} else {
			throw new NullPointerException(
					getKey(relationId, userCode) + "Connection does not exist");
		}

	}
}