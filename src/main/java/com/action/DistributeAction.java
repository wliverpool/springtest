package com.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pojo.LiveWarm;
import com.pojo.TmpUser;
import com.service.atomikos.AtomikosService;

@Controller
@RequestMapping("/")
public class DistributeAction {
	
	private AtomikosService atomikosService;
	
	public AtomikosService getAtomikosService() {
		return atomikosService;
	}

	@Autowired
	public void setAtomikosService(AtomikosService atomikosService) {
		this.atomikosService = atomikosService;
	}

	@RequestMapping(value="distribute")
	public String thumbnail(){
		
		LiveWarm warn = new LiveWarm();
		warn.setVodId("ttt2222");
		warn.setWebcastId("444333dedd");
		
		TmpUser user = new TmpUser();
		user.setOrg("测试机构");
		user.setOrgId(222L);
		user.setUserId(77722L);
		
		atomikosService.save(warn, user);
		
		return "redirect:/";
	}

}
