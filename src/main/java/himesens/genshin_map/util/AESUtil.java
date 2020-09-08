package himesens.genshin_map.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESUtil {
	public static String AESEncode(String rule,String content){
		try{
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			//windows下生成128位随机源
			keygen.init(128, new SecureRandom(rule.getBytes()));
			//linux下生成128位随机源
			/*SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(rule.getBytes());
            keygen.init(128,secureRandom);*/
			//产生原始对称密钥
			SecretKey original_key = keygen.generateKey();
			//获得原始对称密钥的字节数组
			byte[] raw = original_key.getEncoded();
			//根据字节数组生成AES密钥
			SecretKey key = new SecretKeySpec(raw,"AES");
			//根据指定算法AES自成密码器
			Cipher cipher = Cipher.getInstance("AES");
			//初始化密码器
			cipher.init(Cipher.ENCRYPT_MODE, key);
			//获取加密内容字节数组
			byte[] byte_encode = content.getBytes("utf-8");
			//将数据加密
			byte[] byte_AES = cipher.doFinal(byte_encode);
			String AES_encode = new String(new BASE64Encoder().encode(byte_AES));
			
			return AES_encode;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static String AESDecode(String rule,String content){
		try{
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			//windows下生成128位随机源
			keygen.init(128, new SecureRandom(rule.getBytes()));
			//linux下生成128位随机源
			/*SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(rule.getBytes());
            keygen.init(128,secureRandom);*/
            
			SecretKey original_key = keygen.generateKey();
			byte[] raw = original_key.getEncoded();
			SecretKey key = new SecretKeySpec(raw,"AES");
			
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			
			byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
			byte[] byte_decode = cipher.doFinal(byte_content);
			
			String AES_decode = new String(byte_decode,"utf-8");
			return AES_decode;		
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/*public static void main(String[] args){
		AESUtil AU = new AESUtil();
		String jiami = AU.AESEncode("123", "ajwidoahsaofiowa_+wda");
		String jiemi = AU.AESDecode("123", jiami);
		System.out.println(jiami);
		System.out.println(jiemi);
	}*/
}
