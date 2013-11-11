package com.jinva.service.storage;

import java.io.File;


public interface IStorage {

	
	boolean write(String path, String filename, byte[] content);
	
	boolean write(String path, String filename, File file);
	
	byte[] read(String path, String filename);
	
}
