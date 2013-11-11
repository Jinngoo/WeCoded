package com.jinva.service.storage;

import java.io.File;

public class KvdbStorage implements IStorage {

	@Override
	public boolean write(String path, String filename, byte[] content) {
		System.out.println("KvdbStorage");
		return false;
	}

	@Override
	public boolean write(String path, String filename, File file) {
		System.out.println("KvdbStorage");
		return false;
	}

	@Override
	public byte[] read(String path, String filename) {
		System.out.println("KvdbStorage");
		return null;
	}

}
