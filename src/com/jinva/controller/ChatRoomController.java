package com.jinva.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jinva.websocket.WsChatRoom;

@Controller
@RequestMapping("/chatRoom")
public class ChatRoomController extends BaseControllerSupport {

    @RequestMapping(value = "")
    public String index() {
        return "chat/chatRoom";
    }

    @RequestMapping(value = "userCount", method = RequestMethod.GET)
    public ResponseEntity<Integer> chatRoom() {
        return new ResponseEntity<Integer>(WsChatRoom.userCount, HttpStatus.OK);
    }

}
