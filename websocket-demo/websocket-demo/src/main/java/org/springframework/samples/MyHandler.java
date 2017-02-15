package org.springframework.samples;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyHandler extends TextWebSocketHandler {


	public static final ConcurrentHashMap<String,WebSocketSession> onlineUsers = new ConcurrentHashMap<String,WebSocketSession>();

	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		sendMessageToOtherUser(session,message);

	}

	private void sendMessageToOtherUser(WebSocketSession session, TextMessage message) throws IOException {
		Iterator<Map.Entry<String, WebSocketSession>> iterator = onlineUsers.entrySet().iterator();
		while(iterator.hasNext())
		{
			Map.Entry<String, WebSocketSession> next = iterator.next();
			if(!next.getKey().equals(session.getAttributes().get("user").toString()))
			{
				next.getValue().sendMessage(message);
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		onlineUsers.remove(session.getAttributes().get("user").toString());
		super.afterConnectionClosed(session, status);
	}

	public boolean sendMessageByUsername(String userName, TextMessage message) throws IOException {
		if(onlineUsers.containsKey(userName))
		{
			return false;
		}else{
			onlineUsers.get(userName).sendMessage(message);
		}
		return true;
	}


	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		if(!onlineUsers.containsKey(session.getAttributes().get("user").toString()))
		{
			onlineUsers.put(session.getAttributes().get("user").toString(),session);
		}
		String userName = session.getAttributes().get("user").toString();
		sendMessageToOtherUser(session,new TextMessage(userName+"进入房间"));
		super.afterConnectionEstablished(session);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		super.handleTransportError(session, exception);
	}
}