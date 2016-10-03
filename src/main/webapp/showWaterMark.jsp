<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示水印</title>
</head>
<body>
<h4>图片信息</h4>
<hr/>
<table width="100%">
	<c:forEach items="${infos}" var="info">
	<tr>
		<td width="50%" align="center"><img src="${pageContext.request.contextPath}${info.imageOrginalUrl}"/></td>
		<td width="50%" align="center"><img src="${pageContext.request.contextPath}${info.waterMarkImageUrl}"/></td>
	</tr>
	</c:forEach>
</table>
<hr/>
<a href="${pageContext.request.contextPath}">返回</a>
</body>
</html>