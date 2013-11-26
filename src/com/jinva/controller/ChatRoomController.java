package com.jinva.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jinva.websocket.WsChatRoom;

@Controller
@RequestMapping("/chatRoom")
public class ChatRoomController extends BaseControllerSupport {

    public static final String API_YOUKU = "https://openapi.youku.com/v2/videos/show_basic.json?client_id=683e07f6f96dd3a0&video_url={params}";
    public static final String API_TUDOU = "http://api.tudou.com/v6/video/info?app_key=6206acbc39bf0491&itemCodes={params}";
    public static final String API_YOUTUBE = "http://gdata.youtube.com/feeds/api/videos/{params}?v=2&alt=json";

    // http://v.youku.com/v_show/id_XNjM4NjczMzI4.html
    
    // http://www.tudou.com/listplay/l3njSlyAKNA/q1VPZ0chB4k.html
    // http://www.tudou.com/albumplay/MLEFkT75uyY/d8UAQ_xdIOM.html
    
    private String proxyIp;
    private Integer proxyPort;
    
    @RequestMapping(value = "")
    public String index() {
        return "chat/chatRoom";
    }

    @RequestMapping(value = "userCount", method = RequestMethod.GET)
    public ResponseEntity<Integer> chatRoom() {
        return new ResponseEntity<Integer>(WsChatRoom.userCount, HttpStatus.OK);
    }
    
    @RequestMapping(value = "videoInfo", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> videoInfo(@RequestParam("type") String videoType, @RequestParam("url") String videoUrl) {
        String api = getApi(videoType);
        String params = getParams(videoType, videoUrl);

        HttpURLConnection connection = null;
        Proxy proxy = getProxy(videoType);
        InputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONObject result = null;
        String response = null;
        try {
            params = URLEncoder.encode(params, "utf-8");
            api = api.replace("{params}", params);
            URL url = new URL(api);
            connection = proxy == null ? (HttpURLConnection) url.openConnection() : (HttpURLConnection) url.openConnection(proxy);    
            connection.setConnectTimeout(5000);
            connection.connect();
            in = connection.getErrorStream();
            int httpStatus = connection.getResponseCode();
            
            if(in == null && httpStatus == HttpStatus.OK.value()){ // success
                in = connection.getInputStream();
                out = new ByteArrayOutputStream();
                IOUtils.copy(in, out);
                response = new String(out.toByteArray(), "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
        result = getResult(videoType, response);
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }
    
    private Proxy getProxy(String videoType){
        if(StringUtils.isBlank(proxyIp) || proxyPort == null){
            return null;
        }
        if ("youtube".equalsIgnoreCase(videoType)){
            InetSocketAddress addr = new InetSocketAddress(proxyIp, proxyPort);  
            return new Proxy(Proxy.Type.HTTP, addr); 
        }
        return null;
    }
    
    private JSONObject getResult(String videoType, String response){
        JSONObject video = new JSONObject();
        JSONObject json = JSONObject.fromObject(response);
        if(response == null){
            video.put("error", "查询视频信息失败");
        }else if ("youku".equalsIgnoreCase(videoType)) {
            if (!json.containsKey("error")) {
                video.put("title", getString(json, "title"));
                video.put("description", getString(json, "description"));
                video.put("thumbnail", getString(json, new String[] { "thumbnail_v2", "thumbnail" }));
                video.put("player", getString(json, "player"));
            } else {
                video.put("error", getString(json, "error"));
            }
        } else if ("tudou".equalsIgnoreCase(videoType)) {
            if (json.containsKey("results")) {
                JSONArray results = json.getJSONArray("results");
                if(results.size() > 0){
                    JSONObject result = results.getJSONObject(0);
                    video.put("title", getString(result, "title"));
                    video.put("description", getString(result, new String[] {"description", "tags"}));
                    video.put("thumbnail", getString(result, new String[]{"bigPicUrl", "picUrl"}));
                    video.put("player", getString(result, "outerPlayerUrl"));
                }else{
                    video.put("error", "视频不存在");
                }
            }else{
                video.put("error", "视频不存在");
            }
        } else if("youtube".equalsIgnoreCase(videoType)){
            JSONObject mediaGroup = json.getJSONObject("entry").getJSONObject("media$group");
            String title = mediaGroup.getJSONObject("media$title").getString("$t");
            String description = mediaGroup.getJSONObject("media$description").getString("$t");
            String player = mediaGroup.getJSONObject("media$player").getString("url");
            String thumbnail = mediaGroup.getJSONArray("media$thumbnail").getJSONObject(1).getString("url");
            video.put("title", title);
            video.put("description", description);
            video.put("player", player);
            video.put("thumbnail", thumbnail);
        } else {
            video.put("error", "视频类型不支持:" + videoType);
        }
        return video;
    }
    
    private String getParams(String videoType, String videoUrl) {
        if ("youku".equalsIgnoreCase(videoType)) {
            return videoUrl;
        } else if ("tudou".equalsIgnoreCase(videoType)) {
            if(videoUrl.endsWith("/")){
                videoUrl = videoUrl.substring(0, videoUrl.length() - 1);
            }
            String videoId = videoUrl.substring(videoUrl.lastIndexOf("/") + 1);
            if(videoId.contains(".")){
                videoId = videoId.substring(0, videoId.indexOf("."));
            }
            return videoId;
        }else if ("youtube".equalsIgnoreCase(videoType)) {
            String videoId = null;
            if(videoUrl.contains("v/")){
                videoId = videoUrl.substring(videoUrl.indexOf("v/") + 2);
            }else if(videoUrl.contains("v=")){
                videoId = videoUrl.substring(videoUrl.indexOf("v=") + 2);
            }else{
                return null;
            }
            videoId = videoId.split("&|\\?|/")[0];
            return videoId;
        } else {
            return null;
        }
    }

    private String getApi(String videoType) {
        if ("youku".equalsIgnoreCase(videoType)) {
            return API_YOUKU;
        } else if ("tudou".equalsIgnoreCase(videoType)) {
            return API_TUDOU;
        } else if ("youtube".equalsIgnoreCase(videoType)) {
            return API_YOUTUBE;
        } else {
            return null;
        }
    }

    private String getString(JSONObject json, String name){
        if(json.containsKey(name)){
            return json.getString(name);
        }else{
            return null;
        }
    }
    
    private String getString(JSONObject json, String[] names){
        for(String name : names){
            String string = getString(json, name);
            if(StringUtils.isNotBlank(string)){
                return string;
            }
        }
        return null;
    }

    public String getProxyIp() {
        return proxyIp;
    }

    @Value("#{propertiesReader[proxy_ip]}")
    public void setProxyIp(String proxyIp) {
        this.proxyIp = proxyIp;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    @Value("#{propertiesReader[proxy_port]}")
    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }
    
}


/*
    var youku1 = {    
        category: "资讯"
        copyright_type: "original"
        description: "谷歌卫星视图展示了地球自1984年以来不同地区产生的变化，这些变化确实相当有趣。视频抽取拉斯维加斯、迪拜、上海等城市来展示地球母亲在之前的约1/4的世纪里所发生的变化。其中可以看出绿色褪去，沧海变桑田，城市扩张等现象。"
        duration: "147.20"
        id: "XNjM4NjczMzI4"
        link: "http://v.youku.com/v_show/id_XNjM4NjczMzI4.html"
        operation_limit: Array[0]
        paid: 0
        player: "http://player.youku.com/player.php/sid/XNjM4NjczMzI4/v.swf"
        public_type: "all"
        published: "2013-11-23 17:29:14"
        state: "normal"
        streamtypes: Array[4]
        thumbnail: "http://g1.ykimg.com/0100641F4652908F2BEE35138727B9D42AD4E3-E5D7-5B8F-DBE3-FB94DF24EA48"
        thumbnail_v2: "http://r2.ykimg.com/0542040852908F3C6A0A4377C98C4410"
        title: "卫星视图揭秘地球28年变迁 绿色褪去城市扩张131123"
        user: Object
    }
*/

/*
    var tudou1 = {
        "results" : [ {
            "itemCode" : "d8UAQ_xdIOM",
            "vcode" : "XNjE1MTQ1NzI0",
            "title" : "模特魅影",
            "tags" : "",
            "description" : "",
            "picUrl" : "http://r3.ykimg.com/0543040852484AB26A0A4D27EEBEE882",
            "totalTime" : 6233160,
            "pubDate" : "2013-09-30",
            "ownerId" : 113077064,
            "ownerName" : "_113077064",
            "ownerNickname" : "电影客栈",
            "ownerPic" : "http://u1.tdimg.com/u/U-02.gif",
            "ownerURL" : "http://www.tudou.com/home/_113077064",
            "channelId" : 22,
            "outerPlayerUrl" : "http://www.tudou.com/v/d8UAQ_xdIOM/v.swf",
            "playUrl" : "http://www.tudou.com/programs/view/d8UAQ_xdIOM/",
            "mediaType" : "视频",
            "secret" : false,
            "hdType" : "2,3,5",
            "playTimes" : 2275904,
            "commentCount" : 165,
            "bigPicUrl" : "http://r3.ykimg.com/1543040852484AB26A0A4D27EEBEE882",
            "alias" : "",
            "downEnable" : true,
            "location" : "",
            "favorCount" : 147
        } ]
}
*/

/*
    var tudou2 = {
        "results":[{
            "itemCode":"Nxm2zaSwqRc",
            "vcode":"",
            "title":"舒淇拖地裙亮相 深V及腰双峰侧露第50届台湾电影金马奖",
            "tags":"舒淇,高清,红毯,金马奖,性感,shenV",
            "description":"",
            "picUrl":"http://i2.tdimg.com/181/255/861/p.jpg",
            "totalTime":74930,
            "pubDate":"2013-11-23",
            "ownerId":73656293,
            "ownerName":"_73656293",
            "ownerNickname":"娱乐圈爆料",
            "ownerPic":"http://u2.tdimg.com/1/193/32/121953622062923932781282823279271081021.jpg",
            "ownerURL":"http://www.tudou.com/home/_73656293",
            "channelId":1,
            "outerPlayerUrl":"http://www.tudou.com/v/Nxm2zaSwqRc/v.swf",
            "playUrl":"http://www.tudou.com/programs/view/Nxm2zaSwqRc/",
            "mediaType":"视频",
            "secret":false,
            "hdType":"2,3,5",
            "playTimes":0,
            "commentCount":1,
            "bigPicUrl":"http://i2.tdimg.com/181/255/861/w.jpg",
            "alias":"",
            "downEnable":true,
            "location":"",
            "favorCount":0
        }]
    }
*/