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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONObject;

@ServerEndpoint("/wsChatRoom")
public class WsChatRoom {

	private static Map<String, String> ses_user = Collections.synchronizedMap(new HashMap<String, String>());
	
	private Log logger = LogFactory.getLog(getClass());
	
	@OnOpen
	public void onOpen(Session session) {
		Map<String, List<String>> req = session.getRequestParameterMap();
		List<String> ids = req.get("id");
		String userId = ids.get(0);
		ses_user.put(session.getId(), userId);
	}

	@OnMessage
	public void onTextMessage(Session session, String message, boolean last) throws IOException {
		JSONObject input = JSONObject.fromObject(message);
		String userid = input.getString("userid");
		String username = input.getString("username");
		String text = input.getString("text");
		
		Set<Session> set = session.getOpenSessions();
		for(Session ses : set){
		    if(ses.getId().equals(session.getId())){
		        continue;
		    }
		    JSONObject output = new JSONObject();
		    output.put("userid", userid);
		    output.put("username", username);
		    output.put("text", text);
		    send(ses, output);
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
		ses_user.remove(session.getId());
	}
	
	private void send(Session session, JSONObject json){
	    try {
            session.getBasicRemote().sendText(json.toString());
        } catch (IOException e) {
        	logger.error("Send message error.", e);
        }
	}

}
