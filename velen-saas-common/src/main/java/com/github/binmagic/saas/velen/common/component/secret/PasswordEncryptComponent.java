package com.github.binmagic.saas.velen.common.component.secret;

import java.security.MessageDigest;
import java.util.UUID;

public class PasswordEncryptComponent
{
	private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
			"6", "!", "#", "@", "a", "b", "c", "d", "*", "f", "g", "F"};

	private final static String CHARSET_UTF8 = "utf-8";

	private Object salt;
	private String algorithm;

	public static final String MD5 = "MD5";

	public PasswordEncryptComponent(Object salt, String algorithm)
	{
		this.salt = salt;
		this.algorithm = algorithm;
	}

	public String encode(String rawPass)
	{
		String result = null;
		try
		{
			MessageDigest md = MessageDigest.getInstance(algorithm);
			// 加密后的字符串
			result = byteArrayToHexString(md.digest(mergePasswordAndSalt(
					rawPass).getBytes(CHARSET_UTF8)));
		}
		catch(Exception ex)
		{
			throw new RuntimeException(ex);
		}
		return result;
	}

	public boolean isPasswordValid(String encPass, String rawPass)
	{
		String pass1 = "" + encPass;
		String pass2 = encode(rawPass);

		return pass1.equals(pass2);
	}

	private String mergePasswordAndSalt(String password)
	{
		if(password == null)
		{
			password = "";
		}

		if((salt == null) || "".equals(salt))
		{
			return password;
		}
		else
		{
			return password + "{" + salt.toString() + "}";
		}
	}

	/**
	 * 转换字节数组为16进制字串
	 *
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	private static String byteArrayToHexString(byte[] b)
	{
		StringBuffer resultSb = new StringBuffer();
		for(int i = 0; i < b.length; i++)
		{
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b)
	{
		int n = b;
		if(n < 0)
			n = 256 + n;
		int d1 = n / hexDigits.length;
		int d2 = n % hexDigits.length;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static void main(String[] args)
	{
		String salt = UUID.randomUUID().toString();
		PasswordEncryptComponent encoderMd5 = new PasswordEncryptComponent(salt, "sha-256");
		String encodedPassword = encoderMd5.encode("xiaochangwei's password");
		System.out.println("加密后密码：" + encodedPassword + "\n密码长度：" + encodedPassword.length());
		System.out.println("salt:" + salt);
	}
}