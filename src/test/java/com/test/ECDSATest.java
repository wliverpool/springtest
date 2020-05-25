package com.test;

import org.junit.Test;

import com.util.ECDSAEncrypt;

public class ECDSATest {
	
	@Test
	public void testECDSAKeyGenerator(){
		ECDSAEncrypt.genKeyPair("/Users/wufuming/Documents/git/springtest");
	}

	
	@Test
	public void testLoadDSAKey()throws Exception{
		ECDSAEncrypt.loadECDSAPrivateKeyByStr("MEECAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEJzAlAgEBBCA8L/CKn52KlmwBIhHhHhq9xzWFGgWJHnzdirRkrqStnQ==");
		ECDSAEncrypt.loadECDSAPublicKeyByStr("MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEZyVRWw+tJSEMPcTtiHbjKKqYCrwIUsQkMRZXPMzGQk3isxUz+fjLaX+Y3zajOKApu9ZTe/2sAs77tX6enWponQ==");
	}
	
	@Test
	public void testSignDSA(){
		String encrypt =ECDSAEncrypt.sign("test", "MEECAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEJzAlAgEBBCA8L/CKn52KlmwBIhHhHhq9xzWFGgWJHnzdirRkrqStnQ==", "UTF-8");
		System.out.println("encrypt:" + encrypt);
	}
	
	@Test
	public void testDoCheck(){
		String encrypt = "MEUCIDjoxMUbwTtpoXLP3BPTMCnmRym5N1ku80HuTkXFDjqFAiEA9CnwSzThXkjEN+kZ3zqcl81D+x+ZlRD2SSthYKk0YoQ=";
		boolean checkFlag = ECDSAEncrypt.doCheck("test", encrypt, "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEZyVRWw+tJSEMPcTtiHbjKKqYCrwIUsQkMRZXPMzGQk3isxUz+fjLaX+Y3zajOKApu9ZTe/2sAs77tX6enWponQ==", "UTF-8");
		System.out.println("checkFlag:" + checkFlag);
	}
}
