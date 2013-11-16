package com.jinva.service.storage;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;


public interface IStorage {

	
	boolean write(String path, String filename, byte[] content);
	
	boolean write(String path, String filename, File file);
	
	boolean write(String path, String filename, MultipartFile file);
	
	byte[] read(String path, String filename);
	
}
