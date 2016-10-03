package com.test;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.util.TarUtil;

public class TarUtilTest {

	public static void main(String[] args) throws Exception {
    	//testArchiveFile("test.txt","E:\\test\\1.doc");
    	testArchiveDir();
	}
  
    public static void testArchiveFile(String name,String inputStr) throws Exception {   
  
        byte[] contentOfEntry = inputStr.getBytes();   
  
        String path = "d:/" + name;   
  
        FileOutputStream fos = new FileOutputStream(path);   
  
        fos.write(contentOfEntry);   
        fos.flush();   
        fos.close();   
  
        TarUtil.archive(path);   
  
        TarUtil.dearchive(path + ".tar");   
  
        File file = new File(path);   
  
        FileInputStream fis = new FileInputStream(file);   
  
        DataInputStream dis = new DataInputStream(fis);   
  
        byte[] data = new byte[(int) file.length()];   
  
        dis.readFully(data);   
  
        fis.close();   
  
    }   
  
    public static void testArchiveDir() throws Exception {   
        String path = "E:\\test";   
        TarUtil.archive(path);   
  
        TarUtil.dearchive(path + ".tar", "d:");   
    }   
	
}
