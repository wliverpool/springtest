var seckill = {
	//封装秒杀相关ajax的url
	URL:{
		now:function(context){
			return context + '/seckill/time/now';
		},
		exposer : function(context,seckillId) {
			return context + '/seckill/' + seckillId + '/expose';
		},
		execution : function(context,seckillId, md5) {
			return context + '/seckill/' + seckillId + '/' + md5 + '/execute';
		}
	},
	//倒计时
	countdown:function(seckillId,nowTime,startTime,endTime,context){
		var seckillBox = $('#seckillBox');
		if(nowTime>endTime){
			seckillBox.html('秒杀结束');
		}else if(nowTime<startTime){
			//显示倒计时
			var killTime = new Date(startTime + 1000);
			seckillBox.countdown(killTime, function(event) {
				// 时间格式
				var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
				seckillBox.html(format);
				// 时间完成后回调事件
			}).on('finish.countdown', function() {
				// 获取秒杀地址，控制显示逻辑，执行秒杀
				seckill.handleSecKill(seckillId, seckillBox,context);
			});
		}else{
			//秒杀开始
			seckill.handleSecKill(seckillId, seckillBox,context);
		}
	},
	//秒杀逻辑
	handleSecKill:function(seckillId,seckillBox,context){
		seckillBox.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">秒杀开始</button>');
		$.post(seckill.URL.exposer(context,seckillId), {}, function(result) {
			// 在回调函数中，执行交互流程
			if (result && result['begin']) {
				if (result['md5Url']&&""!=result['md5Url']) {
					// 开启秒杀
					var md5 = result['md5Url'];
					var killUrl = seckill.URL.execution(context,seckillId, md5);
					console.log('killUrl=' + killUrl);//TODO
					//绑定一次点击事件
					$('#killBtn').one('click', function() {
						// 执行秒杀请求
						// 1.先禁用按钮
						$(this).addClass('disabled');
						// 2.发送秒杀请求
						$.post(killUrl, {}, function(result) {
							//console.log(result);
							//if (result && result['success']) {
							var killResult = result['msg'];
							//var state = killResult['state'];
							//var stateInfo = killResult['stateInfo'];
							// 3.显示秒杀结果
							seckillBox.html('<span class="label label-success">' + killResult + '</span>');
							//}
						});
					});
					seckillBox.show();
				} else {
					// 未开启秒杀
					var now = result['now'];
					var start = result['start'];
					var end = result['end'];
					// 重新计算计时逻辑
					seckill.countdown(seckillId, now, start, end);
				}
			}
		});
	},
	//验证
	validatePhone:function(phone){
		if(phone&&phone.length==11&&!isNaN(phone)){
			return true;
		}else{
			return false;
		}
	},
	//详情秒杀逻辑
	detail:{
		//详情页初始化
		init:function(params){
			//登陆验证和即时交互流程
			var killPhone = $.cookie('killPhone');
			var startTime = params.startTime;
			var endTime = params.endTime;
			var seckillId = params['seckillId'];
			var context = params['context'];
			if(!seckill.validatePhone(killPhone)){
				//电话验证不通过，显示输入框
				var killPhoneModal = $('#killPhoneModal');
				killPhoneModal.modal({
					show:true,
					backdrop:'static',//禁止位置关闭
					keyboard:false//关闭键盘事件
				});
				$('#killPhoneBtn').click(function(){
					var inputPhone = $('#killPhone').val();
					if(seckill.validatePhone(inputPhone)){
						//写入cookie
						$.cookie('killPhone',inputPhone,{expires:7});
						window.location.reload();
					}else{
						$('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
					}
				});
			}
			//显示倒计时信息
			$.get(seckill.URL.now(context),{},function(result){
				if(result>0&&!isNaN(result)){
					seckill.countdown(seckillId, result, startTime, endTime,context);
				}
			});
		}
	}
}