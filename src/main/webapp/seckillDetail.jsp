<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="common/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>秒杀详情页</title>
<%@include file="common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="panel panel-default text-center">
			<div class="panel-heading"><h1>${seckill.name }</h1></div>
		</div>
		<div class="panel-body">
			<h2 class="text-danger">
				<!-- time图标 -->
				<span class="glyphicon glyphicon-time"></span>
				<!-- 倒计时 -->
				<span class="glyphicon" id="seckillBox"></span>
			</h2>
		</div>
	</div>
	<div id="killPhoneModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title text-center">
						<span class="glyphicon glyphicon-phone"></span>秒杀电话：
					</h3>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-8 col-xs-offset-2">
							<input type="text" name="killPhone" id="killPhone" placeholder="填手机号^o^" class="form-control">
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<!-- 验证信息 -->
					<span id="killPhoneMessage" class="glyphicon"></span>
					<button type="button" id="killPhoneBtn" class="btn btn-success">
						<span class="glyphicon glyphicon-phone"></span>Submit
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-- cookie插件 -->
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.cookie.min.js"></script>
	<!-- 倒计时插件 -->
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.countdown.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/seckill.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function(){
			seckill.detail.init({
				seckillId:${seckill.seckillId},
				startTime:${seckill.startTime.time},
				endTime:${seckill.endTime.time},
				context:'${pageContext.request.contextPath}'
			});
		});
	</script>
</body>
</html>