package com.jinva.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jinva.bean.datamodel.User;

@ServerEndpoint("/wsChatRoom")
public class WsChatRoom {

    private Log logger = LogFactory.getLog(getClass());
	
	private StringBuffer buffer;
	
	private static Map<String, User> users = Collections.synchronizedMap(new HashMap<String, User>());
	
	/**
	 * 向除了自身的其他客户端广播
	 * @param session
	 * @param message
	 */
    private void broadcast(Session session, String message) {
        Set<Session> set = session.getOpenSessions();
        for (Session ses : set) {
            if (ses.getId().equals(session.getId())) {
                continue;
            }
            sendBasicMessage(ses, message);
        }
    }
    /**
     * 向指定客户端发送消息
     * @param session
     * @param message
     */
    private void sendBasicMessage(Session session, String message){
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.error("Send error.", e);
        }
    }
    
    private String buildUserListMessage(){
        JSONObject message = new JSONObject();
        message.put("type", "userlist");
        message.put("userlist", JSONArray.fromObject(users.values()));
        return message.toString();
        
    }
    
    private String getParameter(Session session, String name) {
        List<String> parameters = session.getRequestParameterMap().get(name);
        if (CollectionUtils.isNotEmpty(parameters)) {
            return parameters.get(0);
        } else {
            return null;
        }
    }
    
    private User buildUser(Session session){
        String userid = getParameter(session, "userid");
        String username = getParameter(session, "username");
        User user = new User();
        user.setId(userid);
        user.setNickname(username);
        return user;
    }
    
	
	@OnOpen
	public void onOpen(Session session) {
	    users.put(session.getId(), buildUser(session));
	    
	    String message = buildUserListMessage();
	    sendBasicMessage(session, message);
        broadcast(session, message);
	}

	@OnMessage
    public void onTextMessage(Session session, String message, boolean last) throws IOException {
        if (last) {
            if (buffer != null) {
                buffer.append(message);
                message = buffer.toString();
                buffer = null;
            }
            broadcast(session, message);
        } else {
            if (buffer == null) {
                buffer = new StringBuffer();
            }
            buffer.append(message);
        }
    }

	@OnMessage
	public void onBinaryMessage(Session session, ByteBuffer bb, boolean last) {
		System.out.println("onBinaryMessage");
	}

	@OnMessage
	public void onPongMessage(PongMessage pm) {
		System.out.println("onPongMessage");
	}

	@OnClose
	public void onClose(Session session) {
	    if(users.containsKey(session.getId())){
	        users.remove(session.getId());
	    }
	    String message = buildUserListMessage();
        broadcast(session, message);
	}
	
//	private void send(Session session, JSONObject json){
//	    try {
//            session.getBasicRemote().sendText(json.toString());
//        } catch (IOException e) {
//        	logger.error("Send message error.", e);
//        }
//	}

}
