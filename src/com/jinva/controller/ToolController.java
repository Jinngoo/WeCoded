package com.jinva.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jinva.service.storage.IStorage;
import com.jinva.util.StorageUtil;

@Controller
@RequestMapping("/tool")
public class ToolController extends BaseControllerSupport{

    @Autowired
    private IStorage storage;
    
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String index(){
        return redirectIndex();
    }
    
    @RequestMapping(value = "uploadImage/{type}/{name}", method = RequestMethod.GET)
    public String uploadImage(HttpServletRequest request, @PathVariable("type") String type, @PathVariable("name") String name){
        request.setAttribute("type", type);
        request.setAttribute("name", name);
        return "tool/uploadImage";
    }
    
    @RequestMapping(value = "uploadImage", method = RequestMethod.POST)
    public ResponseEntity<String> uploadImage(HttpServletRequest request) throws IOException{
        String uploadData = request.getParameter("uploadData");
        String uploadName = request.getParameter("uploadName");
        String uploadType = request.getParameter("uploadType");
        
        String path = StorageUtil.getPathByType(uploadType);
        storage.write(path, uploadName, Base64.decodeBase64(uploadData));
        
        return new ResponseEntity<String>("success", HttpStatus.OK); 
    }    
}
