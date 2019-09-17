package com.platform.system.common.util;

public class RandomNumUtil {
	public static String getFourRandomNum(){
		char[] arr = {48,49,50,51,52,53,54,55,56,57};
		int i=1;
		String str = "";
		while(i++<=4){ //循环六次，得到六位数的验证码 
			char msg =arr[(int)(Math.random()*10)]; 
			str += msg;
		}
		return str;
	}
}
