package com.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	public static int bufferSize = 2048;
	private String fileName;
	private int iBegin = 0;

	public ZipUtil(String fileName) {
		this.fileName = fileName;
	}

	public void compress(String srcFolder) throws IOException {
		File folder = new File(srcFolder);
		if (!folder.exists() || !folder.isDirectory()) {
			throw new IOException("源目录不存在");
		}

		File f = new File(fileName);
		if (f.exists()) {
			f.delete();
		} else {
			File fP = f.getParentFile();
			if (!fP.isDirectory()) {
				fP.mkdirs();
			}
		}

		f.createNewFile();
		iBegin = srcFolder.length();
		if (!srcFolder.endsWith(File.separator)) {
			iBegin++;
		}

		FileOutputStream dest = null;
		ZipOutputStream out = null;
		try {
			dest = new FileOutputStream(f);
			out = new ZipOutputStream(new BufferedOutputStream(dest));

			traver(folder, out);
		} finally {
			if (out != null) {
				out.close();
			}
			if (dest != null) {
				dest.close();
			}
		}
	}

	private void traver(File folder, ZipOutputStream out) throws IOException {
		File[] fs = folder.listFiles();
		for (int i = 0; i < fs.length; i++) {
			File f = fs[i];
			if (f.isFile()) {
				FileInputStream fi = null;
				BufferedInputStream origin = null;
				try {
					fi = new FileInputStream(f);
					origin = new BufferedInputStream(fi);
					ZipEntry entry = new ZipEntry(f.getPath().substring(iBegin));

					out.putNextEntry(entry);
					System.out.println(entry.getName());
					int count;
					byte[] data = new byte[bufferSize];
					while ((count = origin.read(data, 0, bufferSize)) != -1) {
						out.write(data, 0, count);
					}
				} finally {
					if (origin != null) {
						origin.close();
					}
					if (fi != null) {
						fi.close();
					}
				}
			} else {
				traver(f, out);
			}
		}
	}

	public void uncompress(String desFolder) throws IOException {
		File folder = new File(desFolder);
		if (!folder.isDirectory()) {
			throw new IOException(desFolder + "不存在");
		}
		String desF = desFolder;
		if (!desF.endsWith(File.separator)) {
			desF += File.separator;
		}
		FileInputStream fis = null;
		ZipInputStream zin = null;
		try {
			fis = new FileInputStream(fileName);
			zin = new ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry = null;
			while ((entry = zin.getNextEntry()) != null) {

				String destPath = desF + entry.getName();
				System.out.println(destPath);
				File f = new File(destPath);
				if (entry.isDirectory()) {
					f.mkdirs();
				} else {
					f.createNewFile();
					BufferedOutputStream dest = null;
					try {
						dest = new BufferedOutputStream(
								new FileOutputStream(f), bufferSize);
						int count = 0;
						byte[] data = new byte[bufferSize];
						while ((count = zin.read(data, 0, bufferSize)) != -1) {

							dest.write(data, 0, count);
						}

					} finally {
						if (dest != null) {
							dest.close();
						}
					}
				}

			}
		} finally {
			if (zin != null) {
				zin.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
	}
	
	/**  
     *   
     * @param srcfile 文件名数组  
     * @param zipfile 压缩后文件  
     */  
    public static void ZipFiles(File[] srcfile, File zipfile) {  
        byte[] buf = new byte[1024];  
        try {  
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(  
                    zipfile));  
            for (int i = 0; i < srcfile.length; i++) {  
                FileInputStream in = new FileInputStream(srcfile[i]);  
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));  
                int len;  
                while ((len = in.read(buf)) > 0) {  
                    out.write(buf, 0, len);  
                }  
                out.closeEntry();  
                in.close();  
            }  
            out.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  

}
