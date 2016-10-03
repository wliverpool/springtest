package com.test;

import java.sql.Connection;

import javax.transaction.Transaction;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.jta.TransactionFactory;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.dao.atomikos.LiveWarmDao;
import com.dao.atomikos.TmpUserDao;
import com.pojo.LiveWarm;
import com.pojo.TmpUser;
import com.service.atomikos.AtomikosService;

public class AtomikosTest {
	
	ApplicationContext context = null;
	
	@Before
	public void setUp(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	@Test
	public void testAtomikosService(){
		/*AtomikosDataSourceBean dsA = (AtomikosDataSourceBean) context.getBean("firstDatabase");
		AtomikosDataSourceBean dsB = (AtomikosDataSourceBean) context.getBean("secondDatabase");
		
		Connection connA = null;
		Connection connB = null;
		
		UserTransactionManager utm = (UserTransactionManager) context.getBean("atomikosUserTransactionManager");
		try {
			utm.begin();
			connA = dsA.getConnection();
			connB = dsB.getConnection();
			connA.prepareStatement("insert into tmp_user (user_id,org_id,orgid) values (54321,'测试org',12345)").execute();
			connB.prepareStatement("insert into live_warm (webcast_id,vod_id) values ('53lksaf23','1ddr231')").execute();
			utm.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		AtomikosService service = (AtomikosService) context.getBean("atomikosService");
		
		TmpUser user = new TmpUser();
		user.setOrg("测试6666");
		user.setOrgId(8888L);
		user.setUserId(9999L);
		
		LiveWarm liveWarm = new LiveWarm();
		liveWarm.setVodId("8888L");
		liveWarm.setWebcastId("9999L");
		
		service.save(liveWarm, user);

	}

}
