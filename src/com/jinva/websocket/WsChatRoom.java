package com.jinva.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@ServerEndpoint("/wsChatRoom")
public class WsChatRoom {

	@SuppressWarnings("unused")
    private Log logger = LogFactory.getLog(getClass());
	
	private StringBuffer buffer;
	
    private void onTextMessage(Session session, String message) {
        Set<Session> set = session.getOpenSessions();
        for (Session ses : set) {
            if (ses.getId().equals(session.getId())) {
                continue;
            }
            try {
                ses.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.error("Send error.", e);
            }
        }
    }
	
	@OnOpen
	public void onOpen(Session session) {
//		Map<String, List<String>> req = session.getRequestParameterMap();
//		List<String> ids = req.get("id");
//		String userId = ids.get(0);
	}

	@OnMessage
    public void onTextMessage(Session session, String message, boolean last) throws IOException {
        if (last) {
            if (buffer != null) {
                buffer.append(message);
                message = buffer.toString();
                buffer = null;
            }
            onTextMessage(session, message);
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

	}
	
//	private void send(Session session, JSONObject json){
//	    try {
//            session.getBasicRemote().sendText(json.toString());
//        } catch (IOException e) {
//        	logger.error("Send message error.", e);
//        }
//	}

}
