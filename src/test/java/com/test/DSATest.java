package com.test;

import org.junit.Test;

import com.util.DSAEncrypt;

public class DSATest {
	
	@Test
	public void testDSAKeyGenerator(){
		DSAEncrypt.genKeyPair("/Users/wufuming/Documents/git/springtest");
	}

	
	@Test
	public void testLoadDSAKey()throws Exception{
		DSAEncrypt.loadDSAPrivateKeyByStr("MIHGAgEAMIGoBgcqhkjOOAQBMIGcAkEA/KaCzo4Syrom78z3EQ5SbbB4sF7ey80etKII864WF64B81uRpH5t9jQTxeEu0ImbzRMqzVDZkVG9xD7nN1kuFwIVAJYu3cw2nLqOuyYO5rahJtk0bjjFAkBnhHGyepz0TukaScUUfbGpqvJE8FpDTWSGkx0tFCcbnjUDC3H9c9oXkGmzLik1Yw4cIGI1TQ2iCmxBblC+eUykBBYCFFqWyEd9F5EgsvaBau3NDcx5GPFp");
		DSAEncrypt.loadDSAPublicKeyByStr("MIHwMIGoBgcqhkjOOAQBMIGcAkEA/KaCzo4Syrom78z3EQ5SbbB4sF7ey80etKII864WF64B81uRpH5t9jQTxeEu0ImbzRMqzVDZkVG9xD7nN1kuFwIVAJYu3cw2nLqOuyYO5rahJtk0bjjFAkBnhHGyepz0TukaScUUfbGpqvJE8FpDTWSGkx0tFCcbnjUDC3H9c9oXkGmzLik1Yw4cIGI1TQ2iCmxBblC+eUykA0MAAkAao9qdKDnPSA441seoAa/1QJ0zCjOQ8sqtfjGwM/BEjmoJXHNvRxRAmK0c8n7FyjXf08p+afM/0WhPjA4+qtra");
	}
	
	@Test
	public void testSignDSA(){
		String encrypt =DSAEncrypt.sign("test", "MIHGAgEAMIGoBgcqhkjOOAQBMIGcAkEA/KaCzo4Syrom78z3EQ5SbbB4sF7ey80etKII864WF64B81uRpH5t9jQTxeEu0ImbzRMqzVDZkVG9xD7nN1kuFwIVAJYu3cw2nLqOuyYO5rahJtk0bjjFAkBnhHGyepz0TukaScUUfbGpqvJE8FpDTWSGkx0tFCcbnjUDC3H9c9oXkGmzLik1Yw4cIGI1TQ2iCmxBblC+eUykBBYCFFqWyEd9F5EgsvaBau3NDcx5GPFp", "UTF-8");
		System.out.println("encrypt:" + encrypt);
	}
	
	@Test
	public void testDoCheck(){
		String encrypt = "MCwCFEJQ9SMcl1Lfi8lJve52m3Vvu2nPAhQSgRQTDmgbbS6WYbRYGP8rzGMKrA==";
		boolean checkFlag = DSAEncrypt.doCheck("test", encrypt, "MIHwMIGoBgcqhkjOOAQBMIGcAkEA/KaCzo4Syrom78z3EQ5SbbB4sF7ey80etKII864WF64B81uRpH5t9jQTxeEu0ImbzRMqzVDZkVG9xD7nN1kuFwIVAJYu3cw2nLqOuyYO5rahJtk0bjjFAkBnhHGyepz0TukaScUUfbGpqvJE8FpDTWSGkx0tFCcbnjUDC3H9c9oXkGmzLik1Yw4cIGI1TQ2iCmxBblC+eUykA0MAAkAao9qdKDnPSA441seoAa/1QJ0zCjOQ8sqtfjGwM/BEjmoJXHNvRxRAmK0c8n7FyjXf08p+afM/0WhPjA4+qtra", "UTF-8");
		System.out.println("checkFlag:" + checkFlag);
	}
}
