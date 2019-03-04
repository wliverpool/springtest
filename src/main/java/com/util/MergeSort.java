package com.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * 亿级数据量多路归并排序
 * @author 吴福明
 *
 */
public class MergeSort {

	//拆分文件大小
	private static final int SPLITE_SIZE = 500000;
	//保存拆分小文件路径
	private static final String PATH = "/Users/wufuming/Documents/git/springtest/split";
	//保存小文件前缀名
	private static final String PREFIX_FILENAME = "big_data_";
	//保存小文件后缀名
	private static final String SUFFIX_FILENAME=".txt";
	//保存小文件序号
	private ThreadLocal<Integer> fileNum = new ThreadLocal<>();
	//最终排序玩文件存放路径
	private static final String OUT_FILENAME = PATH + File.separator + "out.txt";
	
	/**
	 * 拆分大文件为小文件
	 */
	public void spliteFile(String file){
		List<Integer> list = new ArrayList<>();
		int count = 0;
		fileNum.set(0);
		//读取待排序数据文件
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String data = reader.readLine();
			while(null!=data){
				list.add(Integer.parseInt(data));
				count++;
				if(count>=SPLITE_SIZE){
					//对小文件进行内排序
					internalSort(list);
					//小文件读取满,保存小文件
					fileNum.set(fileNum.get() + 1);
					saveFile(PATH, list);
					//重置数据计数
					count = 0;
					list.clear();
				}
				data = reader.readLine();
			}
			//未读取到数据时,判断数据是否都写入到小文件中
			if(CollectionUtils.isNotEmpty(list)){
				fileNum.set(fileNum.get() + 1);
				saveFile(PATH, list);
				count = 0;
				list.clear();
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			if(null != reader){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 小文件内部排序
	 */
	public void internalSort(List<Integer> list){
		//实际可以使用更优化的排序算法
		Collections.sort(list);
	}
	
	/**
	 * 合并排序好的小文件
	 */
	public void mergeFile()throws IOException{
		//获取所有小文件
		File dir = new File(PATH);
		File[] files = dir.listFiles();
		List<FileInfo> listFile = new ArrayList<>();
		BufferedWriter writer = null;
		try {
			for(File file : files){
				BufferedReader reader = new BufferedReader(new FileReader(file));
				FileInfo fileInfo = new FileInfo();
				fileInfo.setReader(reader);
				fileInfo.setFileName(file.getName());
				fileInfo.readNext();
				listFile.add(fileInfo);
			}
			//第一次排序各个小文件,找到最小的数据
			Collections.sort(listFile);
			//最终输出排序好的数据文件
			writer = new BufferedWriter(new FileWriter(OUT_FILENAME));
			//归并排序各个文件中的数据
			while(!listFile.isEmpty()){
				//经过之前的排序,第一个文件的内容肯定是最小的数据
				FileInfo fi = listFile.get(0);
				//写入最小的数据
				writer.write(fi.getCurrentNum() + "\r\n");
				//移动文件中游标读取文件下一个数据
				fi.readNext();
				//对移动游标的数据文件再次进行排序,找到最小的数据
				Collections.sort(listFile);
				//读取完的数据文件移除掉
				if(fi.getCurrentNum() == -1){
					listFile.remove(fi);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(null!=writer){
				writer.close();
			}
			for(FileInfo fileInfo : listFile){
				if(null!=fileInfo){
					BufferedReader reader = fileInfo.getReader();
					if(null!=reader){
						try {
							reader.close();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						
					}
				}
			}
		}
	}
	
	/**
	 * 保存拆分文件
	 * @param path
	 * @param list
	 */
	private void saveFile(String path,List<Integer> list)throws IOException{
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		//构建保存文件名
		String fileName = path + File.separator + PREFIX_FILENAME + fileNum.get() + SUFFIX_FILENAME;
		//保存文件内容
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));
			for(Integer integer : list){
				writer.write(integer + "\r\n");
			}
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(null!=writer){
				writer.close();
			}
		}
	}
	
	//排序文件类
	class FileInfo implements Comparable<FileInfo>{
		//待排序的文件名
		private String fileName;
		//文件中排序到第几个数字
		private Integer currentNum;
		//文件读取信息
		private BufferedReader reader;
		
		public void readNext()throws IOException{
			String data = reader.readLine();
			if(null!=data){
				this.currentNum = Integer.parseInt(data);
			}else{
				this.currentNum = Integer.MIN_VALUE;
			}
		}
		
		public String getFileName() {
			return fileName;
		}
		
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		
		public Integer getCurrentNum() {
			return currentNum;
		}
		
		public void setCurrentNum(Integer currentNum) {
			this.currentNum = currentNum;
		}
		
		public BufferedReader getReader() {
			return reader;
		}
		
		public void setReader(BufferedReader reader) {
			this.reader = reader;
		}

		@Override
		public int compareTo(FileInfo o) {
			if(o.getCurrentNum() != getCurrentNum()){
				return currentNum - o.getCurrentNum();
			}else{
				return fileName.compareTo(o.getFileName());
			}
		}
		
	}
	
}
