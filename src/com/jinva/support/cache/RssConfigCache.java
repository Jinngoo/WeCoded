package com.jinva.support.cache;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.json.JSONObject;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

@Component
public class RssConfigCache {

    private JSONObject rssConfig;
	
    public static void main(String[] args) throws IOException {
        new RssConfigCache();
    }
    
    public RssConfigCache() {
        reload();
    }

    private void reload() {
    	rssConfig = new JSONObject();
    	JSONObject rss163 = readAsJson("rss/rss_163.json");
    	rssConfig.put("rss163", rss163);
    }
    
    private JSONObject readAsJson(String path){
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(path);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
			IOUtils.copy(in, out);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
        String jsonStr = new String(out.toByteArray(), Charsets.UTF_8);
        IOUtils.closeQuietly(out);
        IOUtils.closeQuietly(in);
        return JSONObject.fromObject(jsonStr);
    }

	public JSONObject getRssConfig() {
		return rssConfig;
	}

	public void setRssConfig(JSONObject rssConfig) {
		this.rssConfig = rssConfig;
	}

}
