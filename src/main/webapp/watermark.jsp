<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传水印图片</title>
</head>
<body>
<h4>添加文字水印</h4>
<hr/>
<form name="form1" action="${pageContext.request.contextPath}/waterTextMap" method="post" enctype="multipart/form-data">
	<input type="file" name="image"/>
	<input type="file" name="image"/>
	<input type="file" name="image"/>
	<input type="file" name="image"/>
	<input type="submit" value="上传图片"/>
</form>
<h4>添加图片水印</h4>
<hr/>
<form name="form2" action="${pageContext.request.contextPath}/waterPicMap" method="post" enctype="multipart/form-data">
	<input type="file" name="image"/>
	<input type="file" name="image"/>
	<input type="file" name="image"/>
	<input type="file" name="image"/>
	<input type="submit" value="上传图片"/>
</form>
</body>
</html>