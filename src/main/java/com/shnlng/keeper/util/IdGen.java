package com.shnlng.keeper.util;

import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

public class IdGen {
	public static String id32(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static String random(int len){
		return RandomStringUtils.randomAlphanumeric(len);
	}
	
	public static void main(String[] args) {
		System.out.println(IdGen.random(9));
	}
}
