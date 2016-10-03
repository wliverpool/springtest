package com.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConvertVideoService {

	private Date dt;
	private long begintime;
	private String PATH;
	private String filerealname; // 文件名 不包括扩展名
	private String filename; // 包括扩展名
	private String videofolder; // 别的格式视频的目录
	private String flvfolder; // flv视频的目录
	private String ffmpegpath; // ffmpeg.exe的目录
	private String mencoderpath; // mencoder的目录
	private String videoRealPath; // 视频的目录
	private String imageRealPath; // 截图的目录

	/**
	 * 
	 * @param videofolder
	 *            别的格式视频的目录
	 * @param flvfolder
	 *            flv视频的目录
	 * @param ffmpegpath
	 *            ffmpeg.exe的目录
	 * @param mencoderpath
	 *            mencoder的目录
	 * @param videoRealPath
	 *            视频的目录
	 * @param imageRealPath
	 *            截图的目录
	 */
	public ConvertVideoService(String videofolder, String flvfolder,
			String ffmpegpath, String mencoderpath, String videoRealPath,
			String imageRealPath) {
		this.videofolder = videofolder;
		this.flvfolder = flvfolder;
		this.ffmpegpath = ffmpegpath;
		this.mencoderpath = mencoderpath;
		this.videoRealPath = videoRealPath;
		this.imageRealPath = imageRealPath;
	}

	/**
	 * 
	 * @param videofolder
	 *            别的格式视频的目录
	 * @param flvfolder
	 *            flv视频的目录
	 * @param ffmpegpath
	 *            ffmpeg.exe的目录
	 * @param mencoderpath
	 *            mencoder的目录
	 * @param videoRealPath
	 *            视频的目录
	 * @param imageRealPath
	 *            截图的目录
	 * @param path
	 *            转换格式的文件路径
	 */
	public ConvertVideoService(String videofolder, String flvfolder,
			String ffmpegpath, String mencoderpath, String videoRealPath,
			String imageRealPath, String path) {
		this.videofolder = videofolder;
		this.flvfolder = flvfolder;
		this.ffmpegpath = ffmpegpath;
		this.mencoderpath = mencoderpath;
		this.videoRealPath = videoRealPath;
		this.imageRealPath = imageRealPath;
		this.PATH = path;
	}

	public String getPATH() {
		return PATH;
	}

	public void setPATH(String path) {
		PATH = path;
	}

	public boolean beginConver() {
		File fi = new File(PATH);
		filename = fi.getName();
		filerealname = filename.substring(0, filename.lastIndexOf("."))
				.toLowerCase();
		System.out.println("----接收到文件(" + PATH + ")需要转换-------------------------- ");
		if (!checkfile(PATH)) {
			System.out.println(PATH + "文件不存在" + " ");
			return false;
		}
		dt = new Date();
		begintime = dt.getTime();
		System.out.println("----开始转文件(" + PATH + ")-------------------------- ");
		if (process()) {
			Date dt2 = new Date();
			System.out.println("转换成功 ");
			long endtime = dt2.getTime();
			long timecha = (endtime - begintime);
			String totaltime = sumTime(timecha);
			System.out.println("共用了:" + totaltime + " ");
			if (processImg()) {
				System.out.println("截图成功了 ");
			} else {
				System.out.println("截图不成功了 ");
			}
			PATH = null;
			return true;
		} else {
			PATH = null;
			return false;
		}
	}

	public boolean processImg() {

		List<String> commend = new ArrayList<String>();
		commend.add(ffmpegpath);
		commend.add("-i");
		commend.add(videoRealPath + filerealname + ".flv");
		commend.add("-y");
		commend.add("-f");
		commend.add("image2");
		commend.add("-ss");
		commend.add("38");
		commend.add("-t");
		commend.add("0.001");
		commend.add("-s");
		commend.add("320x240");
		commend.add(imageRealPath + filerealname + ".jpg");
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			builder.start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean process() {
		int type = checkContentType();
		boolean status = false;
		if (type == 0) {

			// status = processFLV(PATH);// 直接将文件转为flv文件
			status = processFLV(PATH);
		} else if (type == 1) {
			String avifilepath = processAVI(type);
			if (avifilepath == null)
				return false;
			// avi文件没有得到
			else {
				System.out.println("kaishizhuang");
				status = processFLV(avifilepath);// 将avi转为flv
			}
		}
		return status;
	}

	private int checkContentType() {
		String type = PATH.substring(PATH.lastIndexOf(".") + 1, PATH.length())
				.toLowerCase();
		// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
		if (type.equals("avi")) {
			return 0;
		} else if (type.equals("mpg")) {
			return 0;
		} else if (type.equals("wmv")) {
			return 0;
		} else if (type.equals("3gp")) {
			return 0;
		} else if (type.equals("mov")) {
			return 0;
		} else if (type.equals("mp4")) {
			return 0;
		} else if (type.equals("asf")) {
			return 0;
		} else if (type.equals("asx")) {
			return 0;
		} else if (type.equals("flv")) {
			return 0;
		}
		// 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
		// 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
		else if (type.equals("wmv9")) {
			return 1;
		} else if (type.equals("rm")) {
			return 1;
		} else if (type.equals("rmvb")) {
			return 1;
		}
		return 9;
	}

	private boolean checkfile(String path) {
		File file = new File(path);
		if (!file.isFile()) {
			return false;
		} else {
			return true;
		}
	}

	// 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等), 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
	private String processAVI(int type) {
		List<String> commend = new ArrayList<String>();
		commend.add(mencoderpath);
		commend.add(PATH);
		commend.add("-oac");
		commend.add("mp3lame");
		commend.add("-lameopts");
		commend.add("preset=64");
		commend.add("-ovc");
		commend.add("xvid");
		commend.add("-xvidencopts");
		commend.add("bitrate=600");
		commend.add("-of");
		commend.add("avi");
		commend.add("-o");
		commend.add(videofolder + filerealname + ".avi");
		// 命令类型：mencoder 1.rmvb -oac mp3lame -lameopts preset=64 -ovc xvid
		// -xvidencopts bitrate=600 -of avi -o rmvb.avi
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			Process p = builder.start();

			doWaitFor(p);
			return videofolder + filerealname + ".avi";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
	private boolean processFLV(String oldfilepath) {

		if (!checkfile(PATH)) {
			System.out.println(oldfilepath + " is not file");
			return false;
		}

		List<String> commend = new ArrayList<String>();
		commend.add(ffmpegpath);
		commend.add("-i");
		commend.add(oldfilepath);
		commend.add("-ab");
		commend.add("64");
		commend.add("-acodec");
		commend.add("libmp3lame");
		commend.add("-ac");
		commend.add("2");
		commend.add("-ar");
		commend.add("22050");
		commend.add("-b");
		commend.add("230");
		commend.add("-r");
		commend.add("24");
		commend.add("-y");
		commend.add(flvfolder + filerealname + ".flv");
		try {
			ProcessBuilder builder = new ProcessBuilder();
			//String cmd = commend.toString();
			builder.command(commend);
			Process p = builder.start();
			doWaitFor(p);
			p.destroy();
			// deleteFile(oldfilepath);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int doWaitFor(Process p) {
		InputStream in = null;
		InputStream err = null;
		int exitValue = -1; // returned to caller when p is finished
		try {
			System.out.println("comeing");
			in = p.getInputStream();
			err = p.getErrorStream();
			boolean finished = false; // Set to true when p is finished

			while (!finished) {
				try {
					while (in.available() > 0) {
						Character c = new Character((char) in.read());
						System.out.print(c);
					}
					while (err.available() > 0) {
						Character c = new Character((char) err.read());
						System.out.print(c);
					}

					exitValue = p.exitValue();
					finished = true;

				} catch (IllegalThreadStateException e) {
					//Thread.currentThread().sleep(500);
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			System.err.println("doWaitFor();: unexpected exception - "
					+ e.getMessage());
		} finally {
			try {
				if (in != null) {
					in.close();
				}

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			if (err != null) {
				try {
					err.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return exitValue;
	}

	public void deleteFile(String filepath) {
		File file = new File(filepath);
		if (PATH.equals(filepath)) {
			if (file.delete()) {
				System.out.println("文件" + filepath + "已删除");
			}
		} else {
			if (file.delete()) {
				System.out.println("文件" + filepath + "已删除 ");
			}
			File filedelete2 = new File(PATH);
			if (filedelete2.delete()) {
				System.out.println("文件" + PATH + "已删除");
			}
		}
	}

	public String sumTime(long ms) {
		int ss = 1000;
		long mi = ss * 60;
		long hh = mi * 60;
		long dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second
				* ss;

		String strDay = day < 10 ? "0" + day + "天" : "" + day + "天";
		String strHour = hour < 10 ? "0" + hour + "小时" : "" + hour + "小时";
		String strMinute = minute < 10 ? "0" + minute + "分" : "" + minute + "分";
		String strSecond = second < 10 ? "0" + second + "秒" : "" + second + "秒";
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond + "毫秒" : "" + strMilliSecond + " 毫秒";
		return strDay + " " + strHour + ":" + strMinute + ":" + strSecond + " " + strMilliSecond;

	}

}
