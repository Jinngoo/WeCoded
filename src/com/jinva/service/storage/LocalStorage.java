package com.jinva.service.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.jinva.util.InitListener;

public class LocalStorage implements IStorage {

	private Log logger = LogFactory.getLog(getClass());

	public static final String STORAGE_PATH = File.separator + "jinva_storage" + File.separator;

	@Override
	public boolean write(String path, String filename, byte[] content) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new ByteArrayInputStream(content);
//			in = new ByteInputStream(content, content.length);
			out = new FileOutputStream(getStoragePath(path) + filename);
			IOUtils.copy(in, out);
		} catch (FileNotFoundException e) {
			logger.error(e);
			return false;
		} catch (IOException e) {
			logger.error(e);
			return false;
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
		}
		logger.info("Upload done : " + path + " - " + filename);
		return true;
	}

	@Override
	public boolean write(String path, String filename, File file) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(file);
			out = new FileOutputStream(getStoragePath(path) + filename);
			IOUtils.copy(in, out);
		} catch (FileNotFoundException e) {
			logger.error(e);
			return false;
		} catch (IOException e) {
			logger.error(e);
			return false;
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
		}
		logger.info("Upload done : " + path + " - " + filename);
		return true;
	}
	
	public boolean write(String path, String filename, MultipartFile file){
	    InputStream in = null;
        OutputStream out = null;
        try {
            in = file.getInputStream();
            out = new FileOutputStream(getStoragePath(path) + filename);
            IOUtils.copy(in, out);
        } catch (FileNotFoundException e) {
            logger.error(e);
            return false;
        } catch (IOException e) {
            logger.error(e);
            return false;
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
        logger.info("Upload done : " + path + " - " + filename);
        return true;
	}

	@Override
	public byte[] read(String path, String filename) {
		InputStream in = null;
		ByteArrayOutputStream out = null;
		File file = new File(getStoragePath(path) + filename);
		if (!file.exists() && !file.canRead()) {
			return null;
		}
		try {
			in = new FileInputStream(file);
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
	
	private String getStoragePath(String subdir) {
		String webContainerPath = new File(InitListener.CONTEXT_REAL_PATH)
				.getParentFile().getParent();
		String uploadPath = webContainerPath + STORAGE_PATH;
		if(StringUtils.isNotBlank(subdir)){
			if(!File.separator.equals(subdir.substring(subdir.length()-1))){
				subdir += File.separator;
			}
		}
		if (StringUtils.isNotBlank(subdir)) {
			uploadPath += subdir;
		}
		File upload = new File(uploadPath);
		if (!upload.exists()) {
			boolean state = upload.mkdirs();
			if (!state) {
				logger.error("创建文件失败!");
			}
		}
		return uploadPath;
	}

}
