package com.cn.tju.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Boyu on 2017/2/15.
 */
@Slf4j
@ServerEndpoint(value = "/socket", configurator = GetHttpSessionConfigurator.class)
public class WebSocketHandler {

    private static final ConcurrentHashMap<String,WSClientUser> sessionMaps = new ConcurrentHashMap<String, WSClientUser>();

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        sendMessageToAllOnline(session, message);
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) throws IOException {
        log.info("new connection come");
        WSClientUser clientUser = new WSClientUser();
        clientUser.setSession(session);
        HttpSession httpSession = (HttpSession) endpointConfig.getUserProperties().get(HttpSession.class.getName());
        if(httpSession.getAttribute("user") == null)
        {
            session.close();
            return;
        }
        clientUser.setHttpSession(httpSession);
        clientUser.setUserName((String) clientUser.getHttpSession().getAttribute("userName"));
        sessionMaps.put(session.getId(),clientUser);
    }


    @OnClose
    public void onClose(Session session)
    {
        sessionMaps.remove(session.getId());
    }

    /**
     * 发送信息给所有在线用户
     * @param session
     * @param message
     * @throws IOException
     */
    public void sendMessageToAllOnline(Session session, String message) throws IOException
    {
        Iterator<Map.Entry<String, WSClientUser>> iterator = sessionMaps.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry<String, WSClientUser> next = iterator.next();
            Session iterSession = next.getValue().getSession();
            for(Session sess : iterSession.getOpenSessions())
            {
                if(sess.isOpen())
                {
                    sess.getBasicRemote().sendText(message);
                }
            }
        }
    }



    public boolean sendMessageByUsername(String userName, String message) throws IOException {
        if(sessionMaps.containsKey(userName))
        {
            return false;
        }else{
            sessionMaps.get(userName).getSession().getBasicRemote().sendText(message);
        }
        return true;
    }

}
