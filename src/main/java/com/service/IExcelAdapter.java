package com.service;

import java.util.List;

/**
 * 生成excel导出文件接口
 * @author 吴福明
 *
 */
public interface IExcelAdapter<T> {
	
	/**
	 * 生成的excel文件在文件系统上的路径
	 * @param realPath  生成excel文件目录
	 * @param title 导出excel的标题
	 * @param datas 设置excel中的内容信息
	 * @return  excelFilePath  excel文件全路径
	 */
	public String generateExcel(String realPath,String sheetName,List<T> datas);
	
	/**
	 * 
	 * @param filePath excel文件路径
	 * @return excel中的数据列表
	 */
	public List<T> readFromExcel(String filePath);

}
