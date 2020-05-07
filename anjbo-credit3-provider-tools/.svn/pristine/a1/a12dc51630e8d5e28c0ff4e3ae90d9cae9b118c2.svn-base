package com.anjbo.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@ServerEndpoint(value = "/pushWebSocket/{uid}")
@Component
public class WebSocketServer {

	protected static Log log = LogFactory.getLog(WebSocketServer.class);

	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
	private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	
	private String currentUid;

	// 连接打开时执行
	@OnOpen
	public void onOpen(@PathParam("uid") String uid, Session session) {
		this.currentUid = uid;
		this.session = session;
		webSocketSet.add(this);
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketSet.remove(this); // 从set中删除
	}

	public void sendMessage(String message,String uid) throws IOException {
		if(this.currentUid.equals(uid)) {
			this.session.getBasicRemote().sendText(message);
			System.out.println(uid+":"+message);
		}
	}

	/**
	 * 发送消息
	 * @param uid
	 * @param message
	 * @throws IOException
	 */
	public static void sendInfo(String uid,String message) throws IOException {
		log.info(message);
		for (WebSocketServer item : webSocketSet) {
			try {
				item.sendMessage(message,uid);
			} catch (IOException e) {
				continue;
			}
		}
	}
	
	@OnError
	public void onError(Session session, Throwable error) {
		webSocketSet.remove(this); // 从set中删除
	}
	
}
