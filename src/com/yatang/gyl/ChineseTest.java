package com.yatang.gyl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.alibaba.fastjson.JSONObject;

//import makejar.librarydemo.DESEncrype;

public class ChineseTest {

	public void writeFile(String filename, String str) {
		try{
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filename), "utf-8");
			out.write(str);
			out.flush();
			out.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String readFile(String filename) {
		StringBuffer sb = new StringBuffer();
		try {
			InputStreamReader input = new InputStreamReader(new FileInputStream(filename),"utf-8");
//			InputStream input = new FileInputStream(filename);
			BufferedReader br = new BufferedReader(input);
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public String getTest() throws Exception {
		String qq = "EeJKAOHy1H8oxB200Irq79AQZg3OAE9OHLNV5eiokXi+pGibDC2V1Obe9jJW CfKOZ41b0KOBIr9Wvtr8zpJq6gQm8RRfj5fwxO0Gh6JjUzpIwoqPe8jXiL6K +N4vvQGPameQMvak9ZX/JEVFskp5Hj/0ho833Og2QKc5hEk3rmnyPDSpWuJi Ae0CfhwwXSIdRGJOiIMrkwiR3l9gn+mEliU18g6Irj++iPOZOCR1zo5+oOOE I1Qi6tvuiU1m3DDlDJLe4l6x9y/KlGlg3OE9yS4L9phTGxvQyJcYUsx5a6p3 va3k083R+Hm6LPLpJMIqjE+ZbFMsSLPqIrb+GxAjnof8BEwAk53FeCiT/JMx ixt242NJBiitBs/65mX0mLHOhRuAOMXyTbgmp+JFT7NYfwuOpSNkn7cJv//3 G4AB3SiMqTIgwDuIjA6F3mK6JeVaNoWkYlMHwkI=";
		String returnlogin = qq;
		// String returnlogin = vars.get("reLogin");
		String strDecrypt = DESEncrype.decryptDES(returnlogin, "509f4351");
//		String filepath = "decrypt.txt";
//		writeFile(filepath, strDecrypt);
//		strDecrypt = readFile(filepath);
		System.out.println(strDecrypt);
		// vars.put("Loginreturn",strDecrypt);

		JSONObject jsStr = JSONObject.parseObject(strDecrypt);
		String StoreSerialNameDefault = jsStr.getJSONObject("mapdata").getString("StoreSerialNameDefault");
		System.out.println("default1 : " + StoreSerialNameDefault);
//		StoreSerialNameDefault = new String(StoreSerialNameDefault.getBytes(), "utf-8");
//		System.out.println("default2 : " + StoreSerialNameDefault);
		String UserName = jsStr.getJSONObject("mapdata").getString("UserName");
//		String bb = new String(UserName.getBytes(), "utf-8");
		// vars.put("username",bb);
		System.out.println("bb : " + UserName);
		String UserId = jsStr.getJSONObject("mapdata").getString("UserId");
		// vars.put("userid",UserId);
		String FinancialAccount = jsStr.getJSONObject("mapdata").getString("FinancialAccount");
		String Token = jsStr.getJSONObject("mapdata").getString("Token");
		String StoreSerialNoDefault = jsStr.getJSONObject("mapdata").getString("StoreSerialNoDefault");
		// vars.put("StoreSerialNoDefault",StoreSerialNoDefault);
		// String s = Loginreturn.matches("\"Token":"(.*?)\"")
//		System.out.println("default3 : " + StoreSerialNameDefault);
		return strDecrypt;
	}

	public static void main(String[] args) throws Exception {
		ChineseTest test = new ChineseTest();
		test.getTest();

	}

}
