package com.test;

import java.io.File;
import java.io.IOException;

import com.util.ZipUtil;

public class TestZipFile {
	
	public static void main(String[] args)throws Exception {
		//testCompress();
		testUnCompress();
	}
	
	private static void testCompress() throws IOException{
		String srcFile = "E:\\wufuming\\tools\\activiti-5.4.zip";
		String srcFolder = "E:\\activiti-5.4";

		ZipUtil zip = new ZipUtil(srcFile);
		zip.compress(srcFolder);
	}
	
	private static void testUnCompress() throws IOException{
		String srcFile = "E:\\wufuming\\tools\\activiti-5.4.zip";
		String desFolder = "E:\\activiti-5.4\\";
		File folder = new File(desFolder);
		if(!folder.exists()){
			folder.mkdirs();
		}
		File[] fs = folder.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isFile()) {
				fs[i].delete();
			}
			
		}
		ZipUtil zip = new ZipUtil(srcFile);
		zip.uncompress(desFolder);
	}

}
