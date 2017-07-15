package makejar.librarydemo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DESEncrype {
	private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
			.toCharArray();
	private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

	public static String encryptDES(String encryptString, String encryptKey) throws Exception {
		SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		cipher.init(1, key, zeroIv);
		byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
		return encode(encryptedData);
	}

	public static String decryptDES(String decryptString, String decryptKey) throws Exception {
		byte[] byteMi = decode(decryptString);
		SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		cipher.init(2, key, zeroIv);
		byte[] decryptedData = cipher.doFinal(byteMi);
		return new String(decryptedData,"utf-8");
	}

	public static String decrypt(String message, String key) throws Exception {
		if (StringUtils.isEmpty(message)) {
			return "";
		}
		byte[] bytesrc = convertHexString(message);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(2, secretKey, iv);
		byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte);
	}

	public static String encrypt(String message, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(1, secretKey, iv);
		return toHexString(cipher.doFinal(message.getBytes("UTF-8")));
	}

	public static byte[] convertHexString(String ss) {
		byte[] digest = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}
		return digest;
	}

	public static String toHexString(byte[] b) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xFF & b[i]);
			if (plainText.length() < 2) {
				plainText = "0" + plainText;
			}
			hexString.append(plainText);
		}
		return hexString.toString();
	}

	public static String encode(byte[] data) {
		int start = 0;
		int len = data.length;
		StringBuffer buf = new StringBuffer(data.length * 3 / 2);

		int end = len - 3;
		int i = start;
		int n = 0;

		while (i <= end) {
			int d = (data[i] & 0xFF) << 16 | (data[(i + 1)] & 0xFF) << 8 | data[(i + 2)] & 0xFF;

			buf.append(legalChars[(d >> 18 & 0x3F)]);
			buf.append(legalChars[(d >> 12 & 0x3F)]);
			buf.append(legalChars[(d >> 6 & 0x3F)]);
			buf.append(legalChars[(d & 0x3F)]);

			i += 3;

			if (n++ >= 14) {
				n = 0;
				buf.append(" ");
			}
		}

		if (i == start + len - 2) {
			int d = (data[i] & 0xFF) << 16 | (data[(i + 1)] & 0xFF) << 8;

			buf.append(legalChars[(d >> 18 & 0x3F)]);
			buf.append(legalChars[(d >> 12 & 0x3F)]);
			buf.append(legalChars[(d >> 6 & 0x3F)]);
			buf.append("=");
		} else if (i == start + len - 1) {
			int d = (data[i] & 0xFF) << 16;

			buf.append(legalChars[(d >> 18 & 0x3F)]);
			buf.append(legalChars[(d >> 12 & 0x3F)]);
			buf.append("==");
		}

		return buf.toString();
	}

	public static byte[] decode(String s) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			decode(s, bos);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		byte[] decodedBytes = bos.toByteArray();
		try {
			bos.close();
			bos = null;
		} catch (IOException ex) {
			System.err.println("Error while decoding BASE64: " + ex.toString());
		}
		return decodedBytes;
	}

	private static void decode(String s, OutputStream os) throws IOException {
		int i = 0;

		int len = s.length();
		while (true) {
			if ((i < len) && (s.charAt(i) <= ' ')) {
				i++;
			} else {
				if (i == len) {
					break;
				}

				int tri = (decode(s.charAt(i)) << 18) + (decode(s.charAt(i + 1)) << 12) + (decode(s.charAt(i + 2)) << 6)
						+ decode(s.charAt(i + 3));

				os.write(tri >> 16 & 0xFF);
				if (s.charAt(i + 2) == '=')
					break;
				os.write(tri >> 8 & 0xFF);
				if (s.charAt(i + 3) == '=')
					break;
				os.write(tri & 0xFF);

				i += 4;
			}
		}
	}

	private static int decode(char c) {
		if ((c >= 'A') && (c <= 'Z'))
			return c - 'A';
		if ((c >= 'a') && (c <= 'z'))
			return c - 'a' + 26;
		if ((c >= '0') && (c <= '9')) {
			return c - '0' + 26 + 26;
		}
		switch (c) {
		case '+':
			return 62;
		case '/':
			return 63;
		case '=':
			return 0;
		}
		throw new RuntimeException("unexpected code: " + c);
	}
}
