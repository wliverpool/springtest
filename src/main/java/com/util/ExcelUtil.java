package com.util;

/**
 * excel工具类
 * @author 吴福明
 *
 */
public class ExcelUtil {
	
	/** 
     *  
     * 是否是2003的excel，返回true是2003 
     * @param filePath　文件完整路径 
     * @return 是否是2003的excel
     */  
  
    public static boolean isExcel2003(String filePath)  
    {  
  
        return filePath.matches("^.+\\.(?i)(xls)$");  
  
    }  
  
    /** 
     *  
     * 是否是2007的excel，返回true是2007 
     * @param filePath　文件完整路径 
     * @return 是否是2007的excel
     */  
  
    public static boolean isExcel2007(String filePath)  
    {  
  
        return filePath.matches("^.+\\.(?i)(xlsx)$");  
  
    }

}
