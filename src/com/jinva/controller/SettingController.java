package com.jinva.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jinva.bean.datamodel.User;
import com.jinva.service.JinvaService;
import com.jinva.service.storage.IStorage;

@Controller
@RequestMapping("/setting")
public class SettingController extends BaseControllerSupport {

    @Autowired
    private JinvaService jinvaService;
    
    @Autowired
    private IStorage storage;

    @RequestMapping(value = { "", "/" })
    public String index() {
        return "setting/index";
    }
    

    @RequestMapping(value = "updateBasicInfo")
    public ResponseEntity<JSONObject> updateBasicInfo(HttpSession session, HttpServletRequest request) {
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        User user = getUser(session);
        JSONObject result = new JSONObject();
        if (user != null) {
            user.setNickname(nickname);
            if (StringUtils.isNotBlank(nickname)) {
                user.setNickname(nickname);
            }
            if (StringUtils.isNotBlank(password)) {
                user.setPassword(password);
            }
            jinvaService.update(user);
            setUser(session, user);
            result.put("code", "success");
        } else {
            result.put("code", "error");
            result.put("message", "User not found");
        }
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }

    /*
    @RequestMapping(value = "uploadSingle", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> uploadSingle(@RequestParam("file") CommonsMultipartFile file, @RequestParam("type") String type, HttpSession session, HttpServletRequest request){
        JSONObject result = new JSONObject();
        if(JinvaConsts.UPLOAD_TYPE_USER_AVATAR.equals(type)){
            boolean success = uploadUserAvatar(session, file);
            if(success){
                result.put("code", "success");
            }else{
                result.put("code", "error");
                result.put("message", "上传失败");
            }
        }else if(JinvaConsts.UPLOAD_TYPE_DISH_AVATAR.equals(type)){
            boolean success = uploadDishAvatar(request, file);
            if(success){
                result.put("code", "success");
            }else{
                result.put("code", "error");
                result.put("message", "上传失败");
            }
        }else{
            result.put("code", "error");
            result.put("message", "没有设置上传类型");
        }
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }
    
    private boolean uploadUserAvatar(HttpSession session, CommonsMultipartFile file){
        String filename = getUserId(session);
        return storage.write(JinvaConsts.USER_AVATAR_PATH, filename, file);
    }
    
    private boolean uploadDishAvatar(HttpServletRequest request, CommonsMultipartFile file){
        String filename = request.getParameter("dishId");
        return storage.write(JinvaConsts.DISH_AVATAR_PATH, filename, file);
    }
    */
    
    /*
    public void uploadMulti(HttpServletRequest request) throws IllegalStateException, IOException {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getServletContext());

        // 检查form是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {

                // 由CommonsMultipartFile继承而来,拥有上面的方法.
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    //TODO
                }

            }
        }
    }
    */
    
}
