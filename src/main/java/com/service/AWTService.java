package com.service;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@Service
public class AWTService {

	//缩略图的宽和高
	public static final int WIDTH = 100;
	public static final int HEIGHT = 100;
	
	//水印图片的文字、字体和位置设置
	public static final String MARK_TEXT = "利物浦";
	public static final String FONT_NAME = "微软雅黑";
	public static final int FONT_SIZE = 120;
	public static final int FONT_STYLE = Font.BOLD;
	public static final Color FONT_COLOR = Color.BLACK;
	public static final int X = 10;
	public static final int Y = 10;
	public static final float ALPHA = 0.3F;
	public static final String pic = "logo.png";
	
	/**
	 * 对上传图片进行缩略
	 * @param orignal
	 * @param uploadPath
	 * @param realUploadPath
	 * @return
	 */
	public String thumbnail(CommonsMultipartFile orignal,String uploadPath,String realUploadPath){
		
		OutputStream os = null;
		
		try {
			
			String des = realUploadPath + "/thum_" + orignal.getOriginalFilename();
			os = new FileOutputStream(des);
			Image image = ImageIO.read(orignal.getInputStream());
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			//获取缩放比例
			int rate1 = width/WIDTH;
			int rate2 = height/HEIGHT;
			
			int rate = 0;
			if(rate1>rate2){
				rate= rate1;
			}else{
				rate = rate2;
			}
			//计算缩放后的最终宽度和高度
			int newWidth = width/rate;
			int newheight = height/rate;
			//画缩放图
			BufferedImage bufferedImage = new BufferedImage(newWidth, newheight, BufferedImage.TYPE_INT_RGB);
			bufferedImage.getGraphics().drawImage(image.getScaledInstance(newWidth, newheight, image.SCALE_SMOOTH), 0, 0, null);
			//图片imageType全部内容:"image/jpeg"
			String imageType = orignal.getContentType().substring(orignal.getContentType().indexOf("/")+1);
			ImageIO.write(bufferedImage, imageType , os);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(os!=null){
				try {
					os.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
		return uploadPath + "/thum_" + orignal.getOriginalFilename();
		
	}
	
	/**
	 * 对上传图片加上文字水印
	 * @param orignal
	 * @param uploadPath
	 * @param realUploadPath
	 * @return
	 */
	public String waterTextMark(CommonsMultipartFile orignal,String uploadPath,String realUploadPath){
		
		OutputStream os = null;
		
		try {
			//获取原图信息
			Image imageOrignal = ImageIO.read(orignal.getInputStream());
			int width = imageOrignal.getWidth(null);
			int height = imageOrignal.getHeight(null);
			//在内存中画出原图
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(imageOrignal, 0, 0, width, width, null);
			//设置水印信息
			g.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
			g.setColor(FONT_COLOR);
			
			int widthT = FONT_SIZE*getTextLength(MARK_TEXT);
			int heightT = FONT_SIZE;
			
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,ALPHA));
			//水印文字旋转
			g.rotate(Math.toRadians(30),bufferedImage.getWidth()/2,bufferedImage.getHeight()/2);
			//在图片上添加单个水印
			/*
			int widthDiff = width-widthT;
			int heightDiff = height-heightT;
			
			int x = X;
			int y = Y;
			if(x>widthDiff){
				x=widthDiff;
			}
			if(y>heightDiff){
				y=heightDiff;
			}
			g.drawString(MARK_TEXT, x, y+FONT_SIZE);
			*/
			//在图片上添加多个水印
			int x = -width/2;
			int y = -height/2;
			while (x<width*1.5) {
				y = -height/2;
				while(y<height*1.5){
					g.drawString(MARK_TEXT, x, y);
					y+=heightT+300;
				}
				x+=widthT+300;
			}
			//关闭画图工具
			g.dispose();
			//保存水印图片到磁盘
			os = new FileOutputStream(realUploadPath + "/waterTextMark_" + orignal.getOriginalFilename());
			JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
			en.encode(bufferedImage);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(os!=null){
				try {
					os.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
		return uploadPath + "/waterTextMark_" + orignal.getOriginalFilename();
	}
	
	/**
	 * 对上传图片加上图片水印
	 * @param orignal
	 * @param uploadPath
	 * @param realUploadPath
	 * @return
	 */
	public String waterPicMark(CommonsMultipartFile orignal,String uploadPath,String realUploadPath){
		
		OutputStream os = null;
		
		String logoPath = realUploadPath + "/" + pic;
		
		try {
			//获取原图信息
			Image imageOrignal = ImageIO.read(orignal.getInputStream());
			int width = imageOrignal.getWidth(null);
			int height = imageOrignal.getHeight(null);
			//在内存中画出原图
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(imageOrignal, 0, 0, width, width, null);
			//设置水印信息
			System.out.println(logoPath);
			File logoFile = new File(logoPath);
			Image imageLogo = ImageIO.read(logoFile);
			int widthT = imageLogo.getWidth(null);
			int heightT = imageLogo.getHeight(null);
			
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,ALPHA));
			g.rotate(Math.toRadians(30),bufferedImage.getWidth()/2,bufferedImage.getHeight()/2);
			//在图片上添加水印
			/*
			int widthDiff = width-widthT;
			int heightDiff = height-heightT;
			
			int x = X;
			int y = Y;
			if(x>widthDiff){
				x=widthDiff;
			}
			if(y>heightDiff){
				y=heightDiff;
			}
			g.drawImage(imageLogo,x,y,null);
			*/
			//提片上添加多个水印
			int x = -width/2;
			int y = -height/2;
			while (x<width*1.5) {
				y = -height/2;
				while(y<height*1.5){
					g.drawImage(imageLogo,x,y,null);
					y+=heightT+300;
				}
				x+=widthT+300;
			}
			//关闭画图工具
			g.dispose();
			//保存水印图片到磁盘
			os = new FileOutputStream(realUploadPath + "/waterPicMark_" + orignal.getOriginalFilename());
			JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
			en.encode(bufferedImage);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(os!=null){
				try {
					os.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
		return uploadPath + "/waterPicMark_" + orignal.getOriginalFilename();
	}
	
	/**
	 * 获取字符串的长度,中文字符byte是占两位的
	 * @param text
	 * @return
	 */
	private int getTextLength(String text){
		int length = text.length();
		for(int i = 0;i<text.length();i++){
			String s = String.valueOf(text.charAt(i));
			if(s.getBytes().length>1){
				length++;
			}
		}
		
		length = length%2==0?length/2:length/2+1;
		
		return length;
	}
	
}
