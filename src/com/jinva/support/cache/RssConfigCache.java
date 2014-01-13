package com.jinva.support.cache;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.json.JSONObject;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import rss.Test;

@Component
public class RssConfigCache {

    private void init() {
        
    }

    private void reload() {
        
    }
    
    private JSONObject readJson() throws IOException{
        InputStream in = Test.class.getClassLoader().getResourceAsStream("rss/rss_163.json");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(in, out);
        String jsonStr = new String(out.toByteArray(), Charsets.UTF_8);
        IOUtils.closeQuietly(out);
        IOUtils.closeQuietly(in);
        return JSONObject.fromObject(jsonStr);
    }

}
