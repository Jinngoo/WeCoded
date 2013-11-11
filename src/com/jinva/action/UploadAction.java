package com.jinva.action;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jinva.service.storage.IStorage;

public class UploadAction extends BaseActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2459897790112876174L;
	
	private File file;
	private String fileFileName; //文件名称
    private String fileContentType; //文件类型
    private String type;
    
    private Map<String, Object> result;
	
    public static final String UPLOAD_TYPE_USER_AVATAR = "1"; 
    public static final String UPLOAD_TYPE_GROUP_AVATAR = "2"; 
    public static final String UPLOAD_TYPE_RESTNURANT_AVATAR = "3"; 
    public static final String UPLOAD_TYPE_DISH_AVATAR = "4"; 
    
    public static final String USER_AVATAR_PATH = "user_avatar";
    public static final String GROUP_AVATAR_PATH = "group_avatar";
    public static final String RESTAURANT_AVATAR_PATH = "restaurant_avatar";
    public static final String DISH_AVATAR_PATH = "dish_avatar";
	
    @Autowired
    private IStorage storage;
    
	public String execute() throws Exception {
		setResult(new HashMap<String, Object>());
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println(file);
		System.out.println(fileFileName);
		System.out.println(fileContentType);
		System.out.println(type);
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		
		
		if(UPLOAD_TYPE_USER_AVATAR.equals(type)){
			boolean success = uploadUserAvatar();
			if(success){
				getResult().put("code", "success");
			}else{
				getResult().put("code", "error");
				getResult().put("message", "上传失败");
			}
		}else if(UPLOAD_TYPE_DISH_AVATAR.equals(type)){
			boolean success = uploadDishAvatar();
			if(success){
				getResult().put("code", "success");
			}else{
				getResult().put("code", "error");
				getResult().put("message", "上传失败");
			}
		}else{
			getResult().put("code", "error");
			getResult().put("message", "没有设置上传类型");
		}
		
		return super.execute();
	}

	private boolean uploadUserAvatar(){
		String filename = getUserId();
		return storage.write(USER_AVATAR_PATH, filename, file);
	}
	
	private boolean uploadDishAvatar(){
		String filename = request.getParameter("dishId");
		return storage.write(DISH_AVATAR_PATH, filename, file);
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
