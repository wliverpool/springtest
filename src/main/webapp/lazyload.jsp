<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery.lazyload.min.js"></script>
<title>图片延时加载</title>
</head>
<body>
	<div id="container">
		<img class="lazy" data-original="images/1.jpg" width="765" height="574" alt="BMW M1 Hood"> <br />
		<noscript><!-- 不支持js时 -->
			<img src="images/1.jpg" width="640" heigh="480">
		</noscript>
		<img class="lazy" data-original="images/2.jpg" width="765" height="574" alt="BMW M1 Side"> <br /> 
		<noscript>
			<img src="images/2.jpg" width="640" heigh="480">
		</noscript>
		<img class="lazy" data-original="images/bg2.jpg" width="765" height="574" alt="Viper 1"><br /> 
		<noscript>
			<img src="images/bg2.jpg" width="640" heigh="480">
		</noscript>
		<img class="lazy" data-original="images/1.jpg" width="765" height="574" alt="Viper Corner"> <br /> 
		<noscript>
			<img src="images/1.jpg" width="640" heigh="480">
		</noscript>
		<img class="lazy" data-original="images/2.jpg" width="765" height="574" alt="BMW M3 GT"><br />
		<noscript>
			<img src="images/2.jpg" width="640" heigh="480">
		</noscript> 
		<img class="lazy" data-original="images/bg2.jpg" width="765" height="574" alt="Corvette Pitstop"> <br />
		<noscript>
			<img src="images/bg2.jpg" width="640" heigh="480">
		</noscript>
	</div>
	<div id="subcontainer">
		点我加载内容
    </div>
	<script type="text/javascript">
		$(function() {
			//图像的地址必须放在data-original属性上。给懒加载图像一个特定的class（例如:lazy）
			$("img.lazy").lazyload({
				//设置加载效果淡入效果
				effect : "fadeIn",
				/*默认情况下在找到第一张不在可见区域的图片时停止循环. 图片被认为是流式分布的, 
				图片在页面中的次序和 HTML 代码中次序相同. 但是在一些布局中, 这样的假设是不成立的*/
				failure_limit : 10,
				//Lazy Load 默认忽略了隐藏图片. 如果你想要加载隐藏图片, 请将 skip_invisible 设为 false
				skip_invisible : false,
				//设置 threshold 为 200 令图片在距离屏幕 200 像素时提前加载
				//threshold : 200,
				//使用jQuery事件加载图片,如click和mouseover等
				event : "click"
			});
			$("#subcontainer").one("click", function () {
                $("#subcontainer").html("<img class=\"lazy\" data-original=\"images/mainbg.gif\" width=\"765\" height=\"574\" alt=\"Corvette Pitstop\"> <br />");
                $("img.lazy").lazyload();
            });
		});
	</script>
</body>
</html>