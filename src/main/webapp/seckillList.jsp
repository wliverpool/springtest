<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="common/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>秒杀列表</title>
<%@include file="common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading text-center">
				<h2>秒杀列表</h2>
			</div>
			<div class="panel-body">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>名称</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>创建时间</th>
							<th>详情页</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list }" var="sk">
							<tr>
								<td>${sk.name }</td>
								<td>
									<fmt:formatDate value="${sk.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>
									<fmt:formatDate value="${sk.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>
									<fmt:formatDate value="${sk.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>
									<a class="btn btn-info" href="<%=request.getContextPath() %>/seckill/${sk.seckillId }/detail">详情</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>