package com.jinva.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jinva.consts.JinvaConsts;
import com.jinva.service.storage.IStorage;

@Controller
@RequestMapping("/")
public class ResourceController extends BaseControllerSupport{
    
//    public static final String UPLOAD_TYPE_USER_AVATAR = "1"; 
//    public static final String UPLOAD_TYPE_GROUP_AVATAR = "2"; 
//    public static final String UPLOAD_TYPE_RESTNURANT_AVATAR = "3"; 
//    public static final String UPLOAD_TYPE_DISH_AVATAR = "4"; 
//    
//    public static final String USER_AVATAR_PATH = "user_avatar";
//    public static final String GROUP_AVATAR_PATH = "group_avatar";
//    public static final String RESTAURANT_AVATAR_PATH = "restaurant_avatar";
//    public static final String DISH_AVATAR_PATH = "dish_avatar";
    
    

    @Autowired
    private IStorage storage;
    
    @RequestMapping(value = "getImage/{type}/{id}", method = RequestMethod.GET)
    public void image(@PathVariable ("type") String type, @PathVariable ("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException{
        returnImage(response, request, type, id);
    }
    
    private void returnImage(HttpServletResponse response, HttpServletRequest request, String type, String id) throws IOException{
        byte[] content = null;
        if (JinvaConsts.UPLOAD_TYPE_USER_AVATAR.equals(type)) {
            content = storage.read(JinvaConsts.USER_AVATAR_PATH, id);
        } else if (JinvaConsts.UPLOAD_TYPE_GROUP_AVATAR.equals(type)) {
            content = storage.read(JinvaConsts.GROUP_AVATAR_PATH, id);
        } else if (JinvaConsts.UPLOAD_TYPE_RESTNURANT_AVATAR.equals(type)) {
            content = storage.read(JinvaConsts.RESTAURANT_AVATAR_PATH, id);
        } else if (JinvaConsts.UPLOAD_TYPE_DISH_AVATAR.equals(type)) {
            content = storage.read(JinvaConsts.DISH_AVATAR_PATH, id);
        }
        if (content == null) {
            content = defaultImg(request, type);
        }
        response.getOutputStream().write(content);
    }
    
    private byte[] defaultImg(HttpServletRequest request, String type) {
        ServletContext servletContext = request.getServletContext();
        String path = null;
        if (JinvaConsts.UPLOAD_TYPE_USER_AVATAR.equals(type)) {
            path = servletContext.getRealPath("resource/image/default/user.jpg");
        } else if (JinvaConsts.UPLOAD_TYPE_GROUP_AVATAR.equals(type)) {
            path = servletContext.getRealPath("resource/image/default/group.jpg");
        }else if (JinvaConsts.UPLOAD_TYPE_RESTNURANT_AVATAR.equals(type)) {
            path = servletContext.getRealPath("resource/image/default/restaurant.jpg");
        }else if (JinvaConsts.UPLOAD_TYPE_DISH_AVATAR.equals(type)) {
            path = servletContext.getRealPath("resource/image/default/dish.jpg");
        }
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new FileInputStream(path);
            out = new ByteArrayOutputStream();
            IOUtils.copy(in, out);
        } catch (FileNotFoundException e) {
            logger.error(e);
            return null;
        } catch (IOException e) {
            logger.error(e);
            return null;
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
        return out.toByteArray();
    }
    
}
