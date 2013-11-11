package com.jinva.action;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.jinva.service.storage.IStorage;

public class ImgAction extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4312893911462410386L;

	@Autowired
    private IStorage storage;
	
	public void img() throws IOException {
		String type = request.getParameter("type");
		byte[] content = null;
		if (UploadAction.UPLOAD_TYPE_USER_AVATAR.equals(type)) {
			content = storage.read(UploadAction.USER_AVATAR_PATH, getUserId());
		} else if (UploadAction.UPLOAD_TYPE_GROUP_AVATAR.equals(type)) {
			String groupId = request.getParameter("id");
			content = storage.read(UploadAction.GROUP_AVATAR_PATH, groupId);
		} else if (UploadAction.UPLOAD_TYPE_RESTNURANT_AVATAR.equals(type)) {
			String restaurantId = request.getParameter("id");
			content = storage.read(UploadAction.RESTAURANT_AVATAR_PATH, restaurantId);
		} else if (UploadAction.UPLOAD_TYPE_DISH_AVATAR.equals(type)) {
			String dishId = request.getParameter("id");
			content = storage.read(UploadAction.DISH_AVATAR_PATH, dishId);
		}
		if (content == null) {
			content = defaultImg(type);
		}
		response.getOutputStream().write(content);
	}
	
	private byte[] defaultImg(String type) {
		String path = null;
		if (UploadAction.UPLOAD_TYPE_USER_AVATAR.equals(type)) {
			path = application.getRealPath("image/default/user.jpg");
		} else if (UploadAction.UPLOAD_TYPE_GROUP_AVATAR.equals(type)) {
			path = application.getRealPath("image/default/group.jpg");
		}else if (UploadAction.UPLOAD_TYPE_RESTNURANT_AVATAR.equals(type)) {
			path = application.getRealPath("image/default/restaurant.jpg");
		}else if (UploadAction.UPLOAD_TYPE_DISH_AVATAR.equals(type)) {
			path = application.getRealPath("image/default/dish.jpg");
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
