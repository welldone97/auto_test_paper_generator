package tpg.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	public static String generateMD5(String src){
		byte[] secretBytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(src.getBytes());
			secretBytes = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		//将加密后的数据转换为16进制数字
		String md5code = new BigInteger(1, secretBytes).toString(16);
		// 如果生成数字未满32位，需要前面补0
		for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
		return md5code;
	}
}
