package com.pb.rsa;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
public class RsaEncrypt {
	public static void main(String[] args) {
		genKeyPair();
		String str=encrypt("aa", (RSAPublicKey)map.get("公钥"));
		String str2=decrypt(str, (RSAPrivateKey)map.get("私钥"));
		System.out.println(str2);
	}
	public static Map<String, Object> map=new HashMap<String,Object>();
	/**
	 * 随机生成密钥对
	 * @throws NoSuchAlgorithmException 
	 */
	public static void genKeyPair() {
		//KeyPair	Generator用于生成公钥和私钥对,基于RSA算法生成对象
		KeyPairGenerator rsaKeyPair = null;
		try {
			rsaKeyPair = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//初始化密钥生成器，密钥大小为96-1024位
		rsaKeyPair.initialize(1025,new SecureRandom());
		//生成一个密钥对，保存在keyPair中
		KeyPair keyPair=rsaKeyPair.generateKeyPair();
		//得到私钥
		RSAPrivateKey  privateKey=(RSAPrivateKey) keyPair.getPrivate();
		//得到公钥
		RSAPublicKey publicKey=(RSAPublicKey) keyPair.getPublic();
		/*String privateKeyString=Base64Coded.encode(privateKey.getEncoded());
		String publicKeyString=Base64Coded.encode(publicKey.getEncoded());*/
		map.put("公钥",publicKey);
		map.put("私钥", privateKey);
	}
	/**
	 * 公钥加密
	 */
	public static String encrypt(String message,RSAPublicKey publicKey) {
		//RSA加密
		String outStr=null;
		try {
			Cipher ciper=Cipher.getInstance("RSA");
			ciper.init(ciper.ENCRYPT_MODE, publicKey);
			Encoder encoder=Base64.getEncoder();
			outStr=encoder.encodeToString(ciper.doFinal(message.getBytes("UTF-8")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outStr;
	}
	/**
	 * 私钥解密
	 */
	public static String decrypt(String message,RSAPrivateKey privateKey) {
		Decoder decoder=Base64.getDecoder();
		//base64解码加密后的字符串
		String outStr=null;
		try {
			byte[] bytes=decoder.decode(message.getBytes("utf-8"));
			//RSA解密
			Cipher cipher=Cipher.getInstance("RSA");
			cipher.init(cipher.DECRYPT_MODE, privateKey);
			outStr=new String(cipher.doFinal(bytes));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outStr;
	}
}
