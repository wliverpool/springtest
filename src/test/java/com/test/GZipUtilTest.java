package com.test;

import com.util.GZipUtil;

public class GZipUtilTest {

	public static void main(String[] args) throws Exception {

		// testDataCompress("zlex@zlex.org,snowolf@zlex.org,zlex.snowolf@zlex.org");
		testFileCompress();
	}

	public static void testDataCompress(String inputStr) throws Exception {

		System.err.println("原文:\t" + inputStr);

		byte[] input = inputStr.getBytes();
		System.err.println("长度:\t" + input.length);

		byte[] data = GZipUtil.compress(input);
		System.err.println("压缩后:\t");
		System.err.println("长度:\t" + data.length);

		byte[] output = GZipUtil.decompress(data);
		String outputStr = new String(output);
		System.err.println("解压缩后:\t" + outputStr);
		System.err.println("长度:\t" + output.length);

	}

	public static void testFileCompress() throws Exception {

		GZipUtil.compress("C:\\Users\\Mittermeyer\\Documents\\GitHub\\SpringMVCTest\\WebContent\\js\\jquery-ui-1.10.1.custom.min.js", false);

		//GZipUtil.decompress("e://smynesc.exe.gz", false);
	}

}
