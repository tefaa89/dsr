package com.dsr.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class FileUtil {
	public static byte[] getByteArrayForFile(File file) {
		byte[] fileContent = null;
		try {
			FileInputStream fin = new FileInputStream(file);
			fileContent = new byte[(int) file.length()];
			fin.read(fileContent);
			fin.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the file " + ioe);
		}
		return fileContent;
	}

	public static File[] getFilesFromDirectory(String directory) {
		try {
			File folder = new File(directory);
			return folder.listFiles();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static File[] getDirectories(String path)
	{
		File currentPath = new File(path);
		Vector<File> folders = new Vector<File>();
		File[] allFiles = currentPath.listFiles();
		for(File file:allFiles)
			if(file.isDirectory())
				folders.add(file);
		return folders.toArray(new File[folders.size()]);
	}
	
	public static boolean containsDirectories(String path)
	{
		File dir = new File(path);
		File[] files = dir.listFiles();
		for(File file:files)
			if(file.isDirectory()) return true;
		return false;
	}
	
	public static <T> T loadObjectFromFile(String fileName) {
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(fileName);
			in = new ObjectInputStream(fis);
			@SuppressWarnings("unchecked")
			T res = (T) in.readObject();
			fis.close();
			in.close();
			return res;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static <T> void writeObjectToFile(T object, String fileName) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(fileName);
			out = new ObjectOutputStream(fos);
			out.writeObject(object);
			out.close();
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
