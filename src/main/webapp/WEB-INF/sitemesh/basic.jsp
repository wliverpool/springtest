<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<html>
<head>
	<title>
		<%-- 把请求的原始页面的title内容插入到<title></title>中间,如果被装饰的页面没有title标签则显示default中内容 --%>
		<decorator:title default="装饰器页面..." /> - 附加标题
	</title>
	<%-- 插入原始页面(被包装页面)的head标签中的内容(不包括head标签本身) --%>
	<decorator:head />
</head>
<body>
	sitemesh的例子<hr>   
	<%-- 把请求的原始页面的body内的全部内容插入到相应位置 --%>     
	<decorator:body />      
	<hr>
	wliverpool@163.com
</body>
</html>