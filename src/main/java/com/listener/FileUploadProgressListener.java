package com.listener;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

import com.pojo.Progress;

/**
 * 
 * 功能描述： 文件上传进度监听器，实现了common-fileupload中的ProgressListener<br>
 * 获取读取到的上传文件的字节数，上传文件总共大小以及第几个文件
 * @author 吴福明
 */
public class FileUploadProgressListener implements ProgressListener {
	
	private HttpSession session;

	public FileUploadProgressListener() {  }  
	
    public FileUploadProgressListener(HttpSession session) {
        this.session=session;  
        Progress status = new Progress();
        session.setAttribute("upload_ps", status);  
    }  
	
	/**
	 * pBytesRead 到目前为止读取文件的字节数 pContentLength 文件总大小 pItems 目前正在读取第几个文件
	 */
	public void update(long pBytesRead, long pContentLength, int pItems) {
		Progress status = (Progress) session.getAttribute("upload_ps");
		status.setBytesRead(pBytesRead);
		status.setContentLength(pContentLength);
		status.setItems(pItems);
		session.setAttribute("upload_ps", status);
	}
}

