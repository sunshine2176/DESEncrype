package makejar.librarydemo;

public class StringUtils {
	public static final String UTF_8 = "utf-8";

	public static boolean isEmpty(String value) {
		if ((value != null) && (!"".equalsIgnoreCase(value.trim())) && (!"null".equalsIgnoreCase(value.trim()))) {
			return false;
		}
		return true;
	}

	public static String replaceNULLToStr(Object obj) {
		if ((obj == null) || ("null".equals(obj + ""))) {
			return "";
		}
		return obj.toString();
	}

	public static boolean isEquals(String[] agrs) {
		String last = null;
		for (int i = 0; i < agrs.length; i++) {
			String str = agrs[i];
			if (isEmpty(str)) {
				return false;
			}
			if ((last != null) && (!str.equalsIgnoreCase(last))) {
				return false;
			}
			last = str;
		}
		return true;
	}

	public static String bytesToString(byte[] encrytpByte) {
		String result = "";
		byte[] arrayOfByte = encrytpByte;
		int i = arrayOfByte.length;
		for (int j = 0; j < i; j++) {
			Byte bytes = Byte.valueOf(arrayOfByte[j]);
			result = result + (char) bytes.intValue();
		}
		return result;
	}

	public static boolean isPhoneNumber(String phoneNumber) {
		return phoneNumber.matches("^1[3,4,5,6,8,7]\\d{9}$");
	}
}
