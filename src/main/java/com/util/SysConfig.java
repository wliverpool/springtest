package com.util;

/**
 * 
 * 功能描述：加载constant.properties配置 <br>
 * @author 吴福明
 */
public class SysConfig extends BaseConfig {

	/** 上传系统标志 当前系统 1-外网上传，2-内网上传 **/
	private static int sysType;
	
	private static String uploadFilePath;
	
	private static String FILE_PATH = "constant.properties";

	static {
		loadValue(FILE_PATH, new SysConfig());
	}

	/**
	 * 重新加载配置
	 * 
	 * @author fantasy
	 * @date 2013-9-23
	 */
	public static void reloadValue() {
		reloadValue(FILE_PATH, new SysConfig());
	}

	public static int getFusysType() {
		return sysType;
	}

	public static void setFusysType(int fusysType) {
		SysConfig.sysType = fusysType;
	}

	public static String getUploadFilePath() {
		return uploadFilePath;
	}

	public static void setUploadFilePath(String uploadFilePath) {
		SysConfig.uploadFilePath = uploadFilePath;
	}
}

