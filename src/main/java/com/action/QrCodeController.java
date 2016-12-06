package com.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pojo.QrCodeLoginUserVo;
import com.pojo.UserVo;
import com.util.StringUtil;
import com.util.TwoDimensionCode;

/**
 * 具体流程通过访问/generateQrCode生成可扫描的二维码,扫描二维码后移动端跳转到移动端登录页面,移动端登录页面填写了用户名密码之后发送到/loginByQrCode进行登录验证
 * 如果验证通过吧uuid和对应用户信息放入成功登录的map中,pc端通过访问/validateLogin获取当前登录页面中的uuid对应信息是否成功验证登录,如果取到对应uuid的登录信息
 * 则可以取出用户信息,进行登录成功之后的session初始化,完成整个登录过程。实际应用中可以通过移动客户端取代移动端登录页面,在移动客户端中使用二维码扫描发送在移动端登录的用户信息
 * 到pc端后台,pc端仍然通过访问/validateLogin获取当前登录页面中的uuid对应信息是否成功验证登录
 * @author 二维码登录控制器
 *
 */
@Controller
@RequestMapping("/")
public class QrCodeController {

	@RequestMapping(value = "/generateQrCode", method = RequestMethod.GET)
	public String generateLoginQrCode(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			// 生成唯一ID
			int uuid = (int) (Math.random() * 100000);
			// 设置二维码内容
			String content = "http://260ad8a7.ittun.com/springtest/login.html?uuid=" + uuid;
			System.out.println(content);
			// 生成二维码图片
			String imgName = uuid + "_" + (int) (new Date().getTime() / 1000) + ".png";
			String imagePath = "/images";
			String imgPath = request.getSession().getServletContext().getRealPath(imagePath) + File.separator + imgName;
			TwoDimensionCode handler = new TwoDimensionCode();
			handler.encoderQRCode(content, imgPath, "png");
			// 生成的图片访问地址
			String qrCodeImg = "http://260ad8a7.ittun.com/springtest" + imagePath + "/" + imgName;
			String jsonStr = "{\"uuid\":" + uuid + ",\"qrCodeImg\":\"" + qrCodeImg + "\"}";
			out.print(jsonStr);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}

		return null;
	}

	@RequestMapping(value = "/validateLogin")
	public String validateLogin(HttpServletRequest request, HttpServletResponse response) {
		//验证登录,验证完成之后从正在登录的用户map中删除登录成功的用户
		
		String uuid = request.getParameter("uuid");
		String jsonStr = "";
		System.out.println("in");
		System.out.println("uuid:" + uuid);

		long inTime = new Date().getTime();
		Boolean bool = true;
		//循环判断当前二维码登录使用的uuid是否在登录成功的map有登录成功的信息
		while (bool) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//检测登录,根据uuid获取存放在正在登录的用户map中的对应用户的信息
			UserVo userVo = QrCodeLoginUserVo.getLoginUserMap().get(uuid);
			System.out.println("userVo:" + userVo);
			if (userVo != null) {
				//如果取到对应用户信息,则把该用户信息从正在登录的用户列表中删除,并返回用户登录成功信息
				bool = false;
				jsonStr = "{\"uname\":\"" + userVo.getUname() + "\"}";
				QrCodeLoginUserVo.getLoginUserMap().remove(uuid);
				System.out.println("login ok : " + jsonStr);
				//在此处可以完成session初始化的操作
				PrintWriter out = null;
				try {
					out = response.getWriter();
					out.print(jsonStr);
					out.flush();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					out.close();
				}
				bool = false;
			} else {
				//如果根据uuid长时间未取到对应用户的信息,则登录失败,可以做扩展使得前台的二维码也同时失效
				if (new Date().getTime() - inTime > 5000) {
					bool = false;
				}
			}
		}
		
		return null;
	}

	@RequestMapping(value = "/loginByQrCode")
	public String loginByQrCode(HttpServletRequest request, HttpServletResponse response){
		String uuid = request.getParameter("uuid");
		String uname = request.getParameter("uname");
		String upwd = request.getParameter("upwd");

		System.out.println(uuid);
		System.out.println(uname);
		System.out.println(upwd);

		//验证登录
		//在此处可以完成对用户名密码的登录校验
		//将登陆信息存入map
		String flag = "";
		if(StringUtil.isNotBlank(uuid)){
			UserVo userVo = QrCodeLoginUserVo.getLoginUserMap().get(uuid);
			if(userVo == null){
				userVo = new UserVo();
				userVo.setUname(uname);
				userVo.setUpwd(upwd);
				QrCodeLoginUserVo.getLoginUserMap().put(uuid,userVo);
				flag = "success";
			}
		}else{
			flag = "false";
		}
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(flag);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}

		return null;
	}

}
