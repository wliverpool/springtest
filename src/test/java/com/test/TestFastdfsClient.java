package com.test;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.util.FastDFSClient;

public class TestFastdfsClient {

	// 客户端配置文件
	private String conf_filename = "/fdfs_client.conf";
	// 本地文件，要上传的文件
	private String filename = "/Users/mittermeyer/Desktop/屏幕快照 2017-10-11 下午3.42.42.png";

	@Test
	public void test() throws Exception {
		// 初始化fdfs的配置文件，指定配置文件的完整路径
		String url = this.getClass().getResource(conf_filename).getPath();
		ClientGlobal.init(url);
		// 获取tracker的客户端
		TrackerClient client = new TrackerClient();
		// 获取tracker的服务端
		TrackerServer trackerServer = client.getConnection();
		// 获取storage的客户端，
		// 第二个参数是storageserver，可以指定某个storageserver上传图片
		StorageClient storageClient = new StorageClient(trackerServer, null);
		// 通过storage的客户端完成上传操作
		String[] upload_file = storageClient.upload_file(filename, "png", null);
		for (String string : upload_file) {
			System.out.println(string);
		}
	}

	@Test
	public void test02() throws Exception {
		// 创建FastDFSClient
		FastDFSClient client = new FastDFSClient();
		// 上传指定文件，并获得上传路径
		String uploadFile = client.uploadFile(filename, filename.substring(filename.lastIndexOf(".") + 1));
		System.out.println(uploadFile);
	}

}
