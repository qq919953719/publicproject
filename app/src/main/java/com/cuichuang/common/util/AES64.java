package com.cuichuang.common.util;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES64 {

	private final Cipher cipher;
	private final SecretKeySpec key;
	private AlgorithmParameterSpec spec;
	// 密钥
	public static final String SEED_16_CHARACTER = "DB6B7916A79828FXS";

	public AES64() throws Exception {
		// hash password with SHA-256 and crop the output to 128-bit for key
		MessageDigest digest = MessageDigest.getInstance("SHA-256");//AES-256-CBC
		digest.update(SEED_16_CHARACTER.getBytes("UTF-8"));
		byte[] keyBytes = new byte[32];
		System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
		cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
		key = new SecretKeySpec(keyBytes, "AES");
		spec = getIV();
	}

	public AlgorithmParameterSpec getIV() {
		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
		IvParameterSpec ivParameterSpec;
		ivParameterSpec = new IvParameterSpec(iv);
		return ivParameterSpec;
	}

	/**
	 * 加密
	 * @param plainText
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String plainText) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, key, spec);
		byte[] encrypted = cipher.doFinal(plainText.trim().getBytes("UTF-8"));
		String encryptedText = new String(Base64.encode(encrypted,
				Base64.DEFAULT), "UTF-8");
		encryptedText = encryptedText.replace("\n", "");
		return encryptedText;
	}

	/**
	 * 解密
	 * @param cryptedText
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String cryptedText) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, key, spec);
		byte[] bytes = Base64.decode(cryptedText, Base64.DEFAULT);
		byte[] decrypted = cipher.doFinal(bytes);
		String decryptedText = new String(decrypted, "UTF-8");
		return decryptedText;
	}

}
