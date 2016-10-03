package com.dao.util;

/**
 * 存储需要动态切换的数据源holder类
 * @author 吴福明
 *
 */
public class DynamicDataSourceHolder {
	
	/** 
     * 线程threadlocal 
     */  
    private static ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
  
    public static String DB_TYPE_RW = "master";  
    public static String DB_TYPE_R = "slave";  
  
    public static String getDbType() {  
        String db = contextHolder.get();  
        if (db == null) {  
            db = DB_TYPE_RW;// 默认是读写库  
        }  
        return db;  
    }  
  
    /** 
     *  
     * 设置本线程的dbtype 
     *  
     * @param str 
     * @see [相关类/方法](可选) 
     * @since [产品/模块版本](可选) 
     */  
    public static void setDbType(String str) {  
        contextHolder.set(str);  
    }  
  
    /** 
     * clearDBType 
     *  
     * @Title: clearDBType 
     * @Description: 清理连接类型 
     */  
    public static void clearDBType() {  
        contextHolder.remove();  
    }  
}
