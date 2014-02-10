package com.jinva.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rss.RssChannel;
import rss.RssLoader;

import com.google.common.base.Charsets;
import com.jinva.controller.base.BaseControllerSupport;
import com.jinva.support.cache.RssConfigCache;

@Controller
@RequestMapping("/social")
public class SocialController extends BaseControllerSupport {

	@Autowired
	private RssConfigCache rssConfigCache;
	
    @RequestMapping(value = { "", "/" })
    public String index() {
        return "social/index";
    }

    @RequestMapping(value = "loadChannel", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> loadChannel(){
    	JSONObject rssConfig = rssConfigCache.getRssConfig();
    	return new ResponseEntity<JSONObject>(rssConfig, HttpStatus.OK);
    }
    
    @RequestMapping(value = "loadRss", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> loadRss(HttpServletRequest request) throws UnsupportedEncodingException{
    	String rssUrl = getString(request, "rssUrl");
    	RssChannel channel = RssLoader.loadRss(rssUrl);
    	JSONObject result = channel == null ? new JSONObject() : JSONObject.fromObject(channel);
    	return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }
    
}
