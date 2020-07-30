package encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * AES为对称加密，加密的内容不限制长度
 * Created by Niki on 2018/8/14 16:01
 */
public class AESCoder {
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法

    /**
     * 生成加密密钥
     *
     * @return
     */
    public static byte[] initSecretKey() {
        // 返回生成指定算法密钥生成器
        KeyGenerator kg = null;

        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }

        kg.init(128);
        //试生产一个密钥
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * 生成密钥
     *
     * @param key
     * @return
     */
    public static Key toKey(byte[] key) {
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }

    /**
     * 加密数据
     *
     * @param data 需要加密的数据
     * @param key  密钥
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 加密数据
     *
     * @param data 需要加密的数据
     * @param key  密钥
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, Key key) throws Exception {
        return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 加密数据
     *
     * @param data            需要加密的数据
     * @param key             密钥
     * @param cipherAlgorithm 加密算法
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
        //还原密钥
        Key k = toKey(key);
        return encrypt(data, k, cipherAlgorithm);
    }

    /**
     * 加密数据
     *
     * @param data            需要加密的数据
     * @param key             密钥
     * @param cipherAlgorithm 加密算法
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
        //实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //使用密钥初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, key);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密数据
     *
     * @param data 已加密数据
     * @param key  密钥
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 解密数据
     *
     * @param data 已加密数据
     * @param key  密钥
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, Key key) throws Exception {
        return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 解密数据
     *
     * @param data            已加密数据
     * @param key             密钥
     * @param cipherAlgorithm 算法
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
        //还原密钥
        Key k = toKey(key);
        return decrypt(data, k, cipherAlgorithm);
    }

    /**
     * 解密数据
     *
     * @param data            已加密数据
     * @param key             密钥
     * @param cipherAlgorithm 算法
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
        //实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //使用密钥初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key);
        //执行操作
        return cipher.doFinal(data);
    }


    private static String showByteArray(byte[] data) {
        if (null == data) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(b).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        byte[] key = initSecretKey();
        System.out.println("key：" + showByteArray(key));
        Key k = toKey(key); //生成秘钥
        String data = "AES数据";
        System.out.println("加密前数据: string:" + data);
        System.out.println("加密前数据: byte[]:" + showByteArray(data.getBytes()));
        System.out.println();
        byte[] encryptData = encrypt(data.getBytes(), k);//数据加密
        System.out.println("加密后数据: byte[]:" + showByteArray(encryptData));
//       System.out.println("加密后数据: hexStr:"+Hex.encodeHexStr(encryptData));
        System.out.println();
        byte[] decryptData = decrypt(encryptData, k);//数据解密
        System.out.println("解密后数据: byte[]:" + showByteArray(decryptData));
        System.out.println("解密后数据: string:" + new String(decryptData));
    }
}
