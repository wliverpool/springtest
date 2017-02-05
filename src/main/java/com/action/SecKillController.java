package com.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pojo.SecKillResultVo;
import com.pojo.SeckillVo;
import com.pojo.mybatisauto.Seckill;
import com.service.mybatis.SecKillService;

@Controller
@RequestMapping("/seckill")
public class SecKillController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SecKillService secKillService;

	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model){
		List<Seckill> seckills = secKillService.getSecKillList(1, 10);
		model.addAttribute("list",seckills);
		return "seckillList";
	}
	
	@RequestMapping(value="/{seckillId}/detail",method=RequestMethod.GET)
	public String detail(@PathVariable("seckillId") long seckillId,Model model){
		if(seckillId<=0){
			return "redirect:/seckill/list";
		}
		Seckill seckill = secKillService.getSecKillById(seckillId);
		if(null==seckill){
			return "redirect:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "seckillDetail";
	}
	
	@RequestMapping(value="/{seckillId}/expose",method=RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillVo expose(@PathVariable("seckillId") long seckillId){
		SeckillVo seckillVo = null;
		try {
			seckillVo = secKillService.getSeckillUrl(seckillId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return seckillVo;
	}
	
	@RequestMapping(value="/{seckillId}/{md5}/execute",method=RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public SecKillResultVo execute(@PathVariable("seckillId") long seckillId,@PathVariable("md5") String md5,@CookieValue(value = "killPhone",required = false) String userPhone){
		SecKillResultVo result = new SecKillResultVo();
		if(StringUtils.isBlank(userPhone)||StringUtils.isBlank(md5)||seckillId<=0){
			result.setMsg("未注册或者秒杀信息缺失");
			result.setSuccess(false);
			return result;
		}
		try {
			//boolean flag = secKillService.saveSeckill(seckillId, userPhone, md5);
			boolean flag = secKillService.saveSeckillByProcedure(seckillId, userPhone, md5);
			result.setSuccess(flag);
			result.setSecKillId(seckillId);
			if(flag){
				result.setMsg("秒杀成功");
			}else{
				result.setMsg("秒杀失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			result.setSuccess(false);
			result.setSecKillId(seckillId);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="/time/now",method=RequestMethod.GET)
	@ResponseBody
	public long time(){
		return new Date().getTime();
	}
	
}
