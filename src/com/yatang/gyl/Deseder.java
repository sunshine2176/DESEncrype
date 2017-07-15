package com.yatang.gyl;

import java.io.UnsupportedEncodingException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.itbt.common.EncryptUtils;

public class Deseder {

	/**
	 * @param args在java中调用sun公司提供的3DES加密解密<a
	 *            href="http://lib.csdn.net/base/datastructure" class=
	 *            'replace_word' title="算法与数据结构知识库" target='_blank' style=
	 *            'color:#df3434; font-weight:bold;'>算法</a>时，需要使
	 *            用到$JAVA_HOME/jre/lib/目录下如下的4个jar包： jce.jar
	 *            security/US_export_policy.jar security/local_policy.jar
	 *            ext/sunjce_provider.jar
	 */
	private static final String Algorithm = "DESede"; 
														// DES,DESede,Blowfish
	// keybyte为加密密钥，长度为24字节
	// src为被加密的数据缓冲区（源）

	public static String encryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return byteArr2HexStr(c1.doFinal(src));// 在单一方面的加密或解密
		} catch (java.security.NoSuchAlgorithmException e1) {
			// TODO: handle exception
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}
	// keybyte为加密密钥，长度为24字节
	// src为加密后的缓冲区

	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			// TODO: handle exception
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}
	
	public static String byteArr2HexStr(byte[] bytea) throws Exception {  
        String sHex = "";  
        int iUnsigned = 0;  
        StringBuffer sbHex = new StringBuffer();  
        for (int i = 0; i < bytea.length; i++) {  
            iUnsigned = bytea[i];  
            if (iUnsigned < 0) {  
                iUnsigned += 256;  
            }  
            if (iUnsigned < 16) {  
                sbHex.append("0");  
            }  
            sbHex.append(Integer.toString(iUnsigned, 16));  
        }  
        sHex = sbHex.toString();  
        return sHex;  
    }  


	public static void main(String[] args) throws UnsupportedEncodingException {
		//添加新安全算法,如果用JCE就要把它添加进去  
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		String s = "yatang.udc.pw.encryptasd";
		final byte[] keyBytes = s.getBytes();
		String szSrc = "This";
		System.out.println("加密前的字符串:" + szSrc);
		String encoded = encryptMode(keyBytes, szSrc.getBytes());
		System.out.println("加密后的字符串:" + new String(encoded));
//		byte[] srcBytes = decryptMode(keyBytes, encoded.getBytes());
//		System.out.println("解密后的字符串:" + (new String(srcBytes)));

		String abc = "scretdes234234234fdgdfgd";
		System.out.println(abc.getBytes().length);
		
		String pwd = EncryptUtils.YLPWDEncrypt("111111");
		System.out.println(pwd);
	}
}
