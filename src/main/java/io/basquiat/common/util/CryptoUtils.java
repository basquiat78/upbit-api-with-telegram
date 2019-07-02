package io.basquiat.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bouncycastle.util.encoders.Hex;

import io.basquiat.common.code.AlgorithmEnum;

public class CryptoUtils {

	/**
	 * createHashByAlgorithm
	 * @param target
	 * @param algorithm
	 * @return String
	 */
	public static String createHashByAlgorithm(String target, String algorithm) {
		String hex = null;
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			byte[] hash = digest.digest(target.getBytes(StandardCharsets.UTF_8));
			hex = new String(Hex.encode(hash));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        return hex;
	}
	
	/**
	 * createHash sha512
	 * @param target
	 * @return String
	 */
	public static String createHashBySHA512(String target) {
        return CryptoUtils.createHashByAlgorithm(target, AlgorithmEnum.SHA512.algorithmName);
	}
	
	/**
	 * createHash sha256
	 * @param target
	 * @return String
	 */
	public static String createHashBySHA256(String target) {
        return CryptoUtils.createHashByAlgorithm(target, AlgorithmEnum.SHA256.algorithmName);
	}
}
