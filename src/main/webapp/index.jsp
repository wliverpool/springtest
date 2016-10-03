<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- 被装饰页面使用<meta name="decorator" content="main"/>其中content指定装饰器的名称 --%>
<meta name="decorator" content="main"/>
<title>首页</title>
</head>
<body bgcolor="white">
<a href="${pageContext.request.contextPath}/upload.jsp">缩略图</a><br/>
<a href="${pageContext.request.contextPath}/watermark.jsp">水印</a><br/>
<a href="${pageContext.request.contextPath}/distribute">测试分布式数据源</a><br/>
<a href="${pageContext.request.contextPath}/userFile/indexPage">文件上传系统</a><br/>
<a href="${pageContext.request.contextPath}/ImageTrans.html">图片翻转</a><br/>
<a href="${pageContext.request.contextPath}/pdfjs/web/viewer.html">pdf查看器</a><br/>
<a href="${pageContext.request.contextPath}/ztree/ztreeIndex.html">ztree树</a><br/>
<a href="${pageContext.request.contextPath}/export/toXls">下载2003版excel</a><br/>
<a href="${pageContext.request.contextPath}/export/toXlsx">下载2007版excel</a><br/>
<a href="${pageContext.request.contextPath}/export/toZip">下载zip文件</a><br/>
<a href="${pageContext.request.contextPath}/bigWheel.jsp">幸运大转盘</a><br/>
<a href="${pageContext.request.contextPath}/videojs/examples/simple-embed/index.html">html5的video播放器</a><br/>
<a href="${pageContext.request.contextPath}/lazyload.jsp">图片懒加载</a><br/>
<a href="${pageContext.request.contextPath}/alertInfo.html">弹出层</a><br/>
<a href="${pageContext.request.contextPath}/tabs.jsp">jquery tabs</a>
</body>
</html>