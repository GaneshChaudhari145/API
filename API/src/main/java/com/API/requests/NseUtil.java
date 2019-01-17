package com.API.requests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;
import java.security.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

// TODO: Auto-generated Javadoc
/**
 * The Class NseUtil.
 */
public class NseUtil {
	
	/** The Constant AES_TRANSFORMATION. */
	private static final String AES_TRANSFORMATION = "AES/ECB/PKCS5Padding";
	
	/** The Constant AES_ALGORITHM. */
	private static final String AES_ALGORITHM = "AES";
    
    /** The Constant RSA_ALGORITHM. */
    private static final String RSA_ALGORITHM = "RSA";
    
    /** The Constant RSA_TRANSFORMATION. */
    private static final String RSA_TRANSFORMATION = "RSA/ECB/PKCS1Padding";
	
	/** The enc bits. */
	static int ENC_BITS = 256;
	
	/** The character encoding. */
	static String CHARACTER_ENCODING = "UTF-8";
	
	/** The keygen. */
	public KeyGenerator KEYGEN;
	
	/** The value before MD 5. */
	public String valueBeforeMD5 = "";
	
	/** The value after MD 5. */
	public String valueAfterMD5 = "";
	
	/** The my rand. */
	private static Random myRand;
	
	/** The my secure rand. */
	private static SecureRandom mySecureRand;	
	
	/** The s id. */
	private static String s_id;
    
    /** The Constant PUBLIC_KEY_PATH_CER. */
    public static final String PUBLIC_KEY_PATH_CER = "D:\\APITestAutomation_TestData\\TestData\\Certificate\\GSTN_G2B_Prod_Public.cer";
    
    /** The Constant PUBLIC_KEY_PATH_PEM. */
    public static final String PUBLIC_KEY_PATH_PEM = "D:\\APITestAutomation_TestData\\TestData\\Certificate\\SB_public.pem";
	
	/**
	 * Generate secure key.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String generateSecureKey() throws Exception{
		KEYGEN = KeyGenerator.getInstance(AES_ALGORITHM);
		KEYGEN.init(ENC_BITS);
				SecretKey secretKey = KEYGEN.generateKey();
				return encodeBase64String(secretKey.getEncoded());
			}
	
	/**
	 * Gets the random guid.
	 *
	 * @param secure the secure
	 * @return the random guid
	 */
	/*
     * Method to generate the random GUID
     */
    public String getRandomGuid(boolean secure) {
        MessageDigest md5 = null;
        StringBuffer sbValueBeforeMD5 = new StringBuffer();

        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: " + e);
        }

        try {
            long time = System.currentTimeMillis();
            long rand = 0;

            if (secure) {
                rand = mySecureRand.nextLong();
            } else {
                rand = myRand.nextLong();
            }

            // This StringBuffer can be a long as you need; the MD5
            // hash will always return 128 bits.  You can change
            // the seed to include anything you want here.
            // You could even stream a file through the MD5 making
            // the odds of guessing it at least as great as that
            // of guessing the contents of the file!
            sbValueBeforeMD5.append(s_id);
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(Long.toString(time));
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(Long.toString(rand));

            valueBeforeMD5 = sbValueBeforeMD5.toString();
            md5.update(valueBeforeMD5.getBytes());

            byte[] array = md5.digest();
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < array.length; ++j) {
                int b = array[j] & 0xFF;
                if (b < 0x10) sb.append('0');
                sb.append(Integer.toHexString(b));
            }

         valueAfterMD5 = sb.toString();

        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
		return valueAfterMD5;
    }     
    
	/**
	 * This method is used to encrypt base64 encoded string using an AES 256 bit key.
	 *
	 * @param plainText            : plain text to decrypt
	 * @param secret            : key to encrypt
	 * @return : Encrypted String
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws NoSuchPaddingException the no such padding exception
	 */	
	public static String encrypt(String plainText, byte[] secret) throws NoSuchAlgorithmException, NoSuchPaddingException{
		
		Cipher ENCRYPT_CIPHER = Cipher.getInstance(AES_TRANSFORMATION);
		try{
			
			SecretKeySpec sk = new SecretKeySpec(secret, AES_ALGORITHM);
			ENCRYPT_CIPHER.init(Cipher.ENCRYPT_MODE, sk);
			return Base64.encodeBase64String(ENCRYPT_CIPHER.doFinal(plainText.getBytes()));
			
		}catch(Exception e){
			e.printStackTrace();
			return "Error in Encryption";
		}
	}
	
	/**
	 * This method is used to decrypt base64 encoded string using an AES 256 bit key.
	 *
	 * @param plainText            : plain text to decrypt
	 * @param secret            : key to decrypt
	 * @return : Decrypted String
	 * @throws InvalidKeyException the invalid key exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws IllegalBlockSizeException the illegal block size exception
	 * @throws BadPaddingException the bad padding exception
	 * @throws Exception the exception
	 */
	    public static byte[] decrypt(String plainText, byte[] secret) throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException,Exception {
	    	Cipher DECRYPT_CIPHER = Cipher.getInstance(AES_TRANSFORMATION);
	    	SecretKeySpec sk = new SecretKeySpec(secret, AES_ALGORITHM);
			DECRYPT_CIPHER.init(Cipher.DECRYPT_MODE, sk);		
	          return DECRYPT_CIPHER.doFinal(decodeBase64StringTOByte(plainText));
	    }	
	
	/* Generate transaction number */
	/**
	 *  This method is used to generate mock Transaction id.
	 *
	 * @return the string of random integer numbers
	 * length of string is 10
	 * and add prefix "GSTN"
	 */
	public String genTransaction(){
		char[] txnid = "1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
		    char c = txnid[random.nextInt(txnid.length)];
		    sb.append(c);
		}
		String transaction = sb.toString();		
		return "GSTN".concat(transaction);
	}

	
	/**
	 * This method is used to encrypt the string , passed to it 
	 * using a public key provided.
	 *
	 * @param plaintext the plaintext
	 * @return       :encrypted string 
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws NoSuchPaddingException the no such padding exception
	 * @throws InvalidKeyException the invalid key exception
	 * @throws IllegalBlockSizeException the illegal block size exception
	 * @throws BadPaddingException the bad padding exception
	 * @throws FileNotFoundException the file not found exception
	 * @throws CertificateException the certificate exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public String encryptwithPK_CER(byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, FileNotFoundException, CertificateException, UnsupportedEncodingException
	{
		String publicKeyUrl =PUBLIC_KEY_PATH_CER;
		FileInputStream fin = new FileInputStream(publicKeyUrl);
		CertificateFactory f = CertificateFactory.getInstance(RSA_ALGORITHM);
		X509Certificate certificate = (X509Certificate)f.generateCertificate(fin);
		PublicKey pk = certificate.getPublicKey();		
		
		Cipher cipher = Cipher.getInstance(RSA_TRANSFORMATION);
		cipher.init(Cipher.ENCRYPT_MODE, pk);
		byte[] encryptedByte= cipher.doFinal(plaintext);
		String encodedString = new String(java.util.Base64.getEncoder().encode(encryptedByte));
		return encodedString;
	}

	/**
	 * Encryptwith P K PEM.
	 *
	 * @param planTextToEncrypt the plan text to encrypt
	 * @return the string
	 */
	public String encryptwithPK_PEM(byte[] planTextToEncrypt){
		Cipher cipher = null;
		
		try {
			Path keyPath = Paths.get(PUBLIC_KEY_PATH_PEM);
			String keyContent = new String(Files.readAllBytes(keyPath)).replace("-----BEGIN RSA PUBLIC KEY-----", "");
			String modifiedKeyContent = keyContent.replace("-----END RSA PUBLIC KEY-----", "");
			byte[] decodedKey = Base64.decodeBase64(modifiedKeyContent.getBytes());
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decodedKey);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
			PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
			
			cipher = Cipher.getInstance(RSA_TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] encryptedByte= cipher.doFinal(planTextToEncrypt);
			String encodedString = new String(java.util.Base64.getEncoder().encode(encryptedByte)); 
			return encodedString;
		} catch (Exception e) {
				e.printStackTrace();
				return null;
		}	
			
	}
	
	
    /**
     * Generate hmac.
     *
     * @param data the data
     * @param ek the ek
     * @return the string
     */
    public static String generateHmac(String data, byte[] ek)
    {
    	String hash = null;
    	try{ 
	    	Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
	        SecretKeySpec secret_key = new SecretKeySpec(ek, "AES_ALGORITHM");
	        sha256_HMAC.init(secret_key);
	        hash = Base64.encodeBase64String(sha256_HMAC.doFinal(data.getBytes(CHARACTER_ENCODING)));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		return hash;
    }
    
    /**
     * Gets the hashdata file.
     *
     * @param filepath the filepath
     * @return the hashdata file
     * @throws Exception the exception
     */
    @SuppressWarnings("unused")
	private static String gethashdataFile(String filepath) throws Exception {

        try {
              MessageDigest md = MessageDigest.getInstance("SHA-256");
              @SuppressWarnings("resource")
              FileInputStream fis = new FileInputStream(filepath);
              byte[] dataBytes = new byte[1024];
              int nread = -1;
              while ((nread = fis.read(dataBytes)) != -1) {
                    md.update(dataBytes, 0, nread);
              }
              byte[] mdbytes = md.digest();

              // convert the byte to hex format method 2
              StringBuilder hexString = new StringBuilder();
              for (int i = 0; i < mdbytes.length; i++) {
                    hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
              }

              return hexString.toString();
        } finally {
        	System.out.println("Error calculating Hash");

        }
  }

 
    /**
     * Gets the hash value.
     *
     * @param value the value
     * @return the hash value
     * @throws Exception the exception
     */
    public static String getHashValue(String value) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(value.getBytes());
        byte[] byteData = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
              sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
                          .substring(1));
        }
        return sb.toString();
  }     

	/**
     * This method is used to encode bytes[] to base64 string.
     * 
      * @param bytes
     *            : Bytes to encode
     * @return : Encoded Base64 String
     */
   public static String encodeBase64String(byte[] bytes) {
         return new String(java.util.Base64.getEncoder().encode(bytes));
   }
   
   /**
    * This method is used to decode the base64 encoded string to byte[].
    *
    * @param stringData            : String to decode
    * @return : decoded String
    * @throws Exception the exception
    */
   public static byte[] decodeBase64StringTOByte(String stringData) throws Exception {
		return java.util.Base64.getDecoder().decode(stringData.getBytes(CHARACTER_ENCODING));
	}
 
   /**
    * Exception handling.
    *
    * @param e the e
    */
   public void exception_handling(Exception e){
	   System.out.println("Exception Occured \n\n");
	   System.out.println(e);
   }
   
   /**
    * The main method.
    *
    * @param args the arguments
    * @throws InvalidKeyException the invalid key exception
    * @throws IllegalBlockSizeException the illegal block size exception
    * @throws BadPaddingException the bad padding exception
    * @throws IOException Signals that an I/O exception has occurred.
    * @throws Exception the exception
    */
   public static void main (String[] args) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, Exception{
//	   System.out.println(Security.getProviders());
//	   String secureKey= "hdLP7beW/uS4w8OiVWUmRt/k3ygnAL1XZFsdM7MNZCg=";  //Generated Secure Key
//	   byte[] appKEY=decodeBase64StringTOByte(secureKey); 
//	   String sek="uVXjpmXjm/1dhcb7ZsIlNhWpurh4XOuqJSEFq32k47LyhhXq/r5tgMpCQbTuGySv";  //SEK from Authenticate API Response
//	   byte[] decryptedSessionId=decrypt(sek, appKEY);  //Decrypt the SEK with APP KEY 
//	   System.out.println("decrypted session id: " +decryptedSessionId);
//	   System.out.println("Encoded session id: " +encodeBase64String(decryptedSessionId));
//	   System.out.println("Hash is="+getHashValue("eyJyZXRfcGVyaW9kIjoiMTAyMDE2Iiwic2VjX3N1bSI6W3sidHRsX2lnc3QiOjAsInNlY19ubSI6IkFUVCIsInR0bF9jZ3N0IjoxMDAsInR0bF9jZXNzIjoxMDAsInR0bF90YXgiOjAsInR0bF92YWwiOjAsInR0bF9yZWMiOjEsImNoa3N1bSI6ImM4ZDYzZjUwMjQ5NThiNjVhNmI5YWIxYTMyNTY5ZjFlN2U5ODQxZTNkOTBhNjNmYWI5ODU0YmY4MGM5OWM4MWYiLCJ0dGxfc2dzdCI6MTAwfSx7InR0bF9pZ3N0IjowLCJzZWNfbm0iOiJFWFBXUEFUIiwidHRsX2Nnc3QiOjAsInR0bF9jZXNzIjowLCJ0dGxfdGF4IjowLCJ0dGxfdmFsIjowLCJ0dGxfcmVjIjowLCJjaGtzdW0iOiJlM2IwYzQ0Mjk4ZmMxYzE0OWFmYmY0Yzg5OTZmYjkyNDI3YWU0MWU0NjQ5YjkzNGNhNDk1OTkxYjc4NTJiODU1IiwidHRsX3Nnc3QiOjB9LHsidHRsX2lnc3QiOjEwMC4wMSwic2VjX25tIjoiQjJDTFQiLCJ0dGxfY2dzdCI6MCwiY3B0eV9zdW0iOlt7InR0bF9pZ3N0IjoxMDAuMDEsInR0bF9jZ3N0IjowLCJ0dGxfY2VzcyI6MTAwLCJ0dGxfdGF4IjoxMDAwLjAxLCJ0dGxfdmFsIjoxMDAwMDAuMDEsInN0YXRlX2NkIjoiMjkiLCJ0dGxfcmVjIjoxLCJjaGtzdW0iOiJlMzk5Y2E1YjhkZDAwOGNmMmJjNTFiNzI1ZmE2OWRiZTQ1ZjZkOGM2MGQ0YmRlYmJjYjgwZWI2MGM0ZDE3ODc5IiwidHRsX3Nnc3QiOjB9XSwidHRsX2Nlc3MiOjEwMCwidHRsX3RheCI6MTAwMC4wMSwidHRsX3ZhbCI6MTAwMDAwLjAxLCJ0dGxfcmVjIjoxLCJjaGtzdW0iOiI1YTg4NDU4NjhiOWE4NzQ1YmQwNjM0M2IyMWY5NjYzYjdhZjg3ZmFmYTNkODkwNjc0ODViZDMzMDhlZWIwMDU2IiwidHRsX3Nnc3QiOjB9LHsidHRsX2lnc3QiOjEwMCwic2VjX25tIjoiRVhQV1BUIiwidHRsX2Nnc3QiOjAsInR0bF9jZXNzIjoxMDAsInR0bF90YXgiOjEwMDAsInR0bF92YWwiOjEwMDA0OC4zNiwidHRsX3JlYyI6MSwiY2hrc3VtIjoiZGIwMjBkZTM2NGQzOTM2YzM3OWU5YzI4NTgxNWM4MjFlMTUwNzg3MGU5N2JiZmYxYTU1ZmJjYTdkMGEyNTg0NCIsInR0bF9zZ3N0IjowfSx7InR0bF9pZ3N0IjowLCJzZWNfbm0iOiJFWFBXT1BBVCIsInR0bF9jZ3N0IjowLCJ0dGxfY2VzcyI6MCwidHRsX3RheCI6MCwidHRsX3ZhbCI6MCwidHRsX3JlYyI6MCwiY2hrc3VtIjoiZTNiMGM0NDI5OGZjMWMxNDlhZmJmNGM4OTk2ZmI5MjQyN2FlNDFlNDY0OWI5MzRjYTQ5NTk5MWI3ODUyYjg1NSIsInR0bF9zZ3N0IjowfSx7InR0bF9pZ3N0IjowLCJzZWNfbm0iOiJFQ09NSU5UUkFUIiwidHRsX2Nnc3QiOjAsInR0bF9jZXNzIjowLCJ0dGxfdGF4IjowLCJ0dGxfdmFsIjowLCJ0dGxfcmVjIjowLCJjaGtzdW0iOiJlM2IwYzQ0Mjk4ZmMxYzE0OWFmYmY0Yzg5OTZmYjkyNDI3YWU0MWU0NjQ5YjkzNGNhNDk1OTkxYjc4NTJiODU1IiwidHRsX3Nnc3QiOjB9LHsidHRsX2lnc3QiOjAsInNlY19ubSI6Ik5JTFQiLCJ0dGxfY2dzdCI6MCwidHRsX2Nlc3MiOjAsInR0bF90YXgiOjAsInR0bF92YWwiOjIyODIyLjQsInR0bF9yZWMiOjEsImNoa3N1bSI6ImU2N2U3MjExMWIzNjNkODBjODEyNGQyODE5MzkyNjAwMDk4MGUxMjExYzc5ODZjYWNiZDI2YWFjYzU1MjhkNDgiLCJ0dGxfc2dzdCI6MH0seyJ0dGxfaWdzdCI6MCwic2VjX25tIjoiQjJDTEFUIiwidHRsX2Nnc3QiOjAsInR0bF9jZXNzIjowLCJ0dGxfdGF4IjowLCJ0dGxfdmFsIjowLCJ0dGxfcmVjIjowLCJjaGtzdW0iOiJlM2IwYzQ0Mjk4ZmMxYzE0OWFmYmY0Yzg5OTZmYjkyNDI3YWU0MWU0NjQ5YjkzNGNhNDk1OTkxYjc4NTJiODU1IiwidHRsX3Nnc3QiOjB9LHsidHRsX2lnc3QiOjAsInNlY19ubSI6IkIyQ1NUIiwidHRsX2Nnc3QiOjAsInR0bF9jZXNzIjowLCJ0dGxfdGF4IjowLCJ0dGxfdmFsIjowLCJ0dGxfcmVjIjowLCJjaGtzdW0iOiJlM2IwYzQ0Mjk4ZmMxYzE0OWFmYmY0Yzg5OTZmYjkyNDI3YWU0MWU0NjQ5YjkzNGNhNDk1OTkxYjc4NTJiODU1IiwidHRsX3Nnc3QiOjB9LHsidHRsX2lnc3QiOjAsInNlY19ubSI6IkVDT01JTlRSVCIsInR0bF9jZ3N0IjowLCJ0dGxfY2VzcyI6MCwidHRsX3RheCI6MCwidHRsX3ZhbCI6MCwidHRsX3JlYyI6MCwiY2hrc3VtIjoiZTNiMGM0NDI5OGZjMWMxNDlhZmJmNGM4OTk2ZmI5MjQyN2FlNDFlNDY0OWI5MzRjYTQ5NTk5MWI3ODUyYjg1NSIsInR0bF9zZ3N0IjowfSx7InR0bF9pZ3N0IjowLCJzZWNfbm0iOiJDRE5BVCIsInR0bF9jZ3N0IjowLCJ0dGxfY2VzcyI6MCwidHRsX3RheCI6MCwidHRsX3ZhbCI6MCwidHRsX3JlYyI6MCwiY2hrc3VtIjoiZTNiMGM0NDI5OGZjMWMxNDlhZmJmNGM4OTk2ZmI5MjQyN2FlNDFlNDY0OWI5MzRjYTQ5NTk5MWI3ODUyYjg1NSIsInR0bF9zZ3N0IjowfSx7InR0bF9pZ3N0IjowLCJzZWNfbm0iOiJFWFBXT1BUIiwidHRsX2Nnc3QiOjAsInR0bF9jZXNzIjowLCJ0dGxfdGF4IjowLCJ0dGxfdmFsIjowLCJ0dGxfcmVjIjowLCJjaGtzdW0iOiJlM2IwYzQ0Mjk4ZmMxYzE0OWFmYmY0Yzg5OTZmYjkyNDI3YWU0MWU0NjQ5YjkzNGNhNDk1OTkxYjc4NTJiODU1IiwidHRsX3Nnc3QiOjB9LHsidHRsX2lnc3QiOjAsInNlY19ubSI6IkIyQ1NBVCIsInR0bF9jZ3N0IjowLCJ0dGxfY2VzcyI6MCwidHRsX3RheCI6MCwidHRsX3ZhbCI6MCwidHRsX3JlYyI6MCwiY2hrc3VtIjoiZTNiMGM0NDI5OGZjMWMxNDlhZmJmNGM4OTk2ZmI5MjQyN2FlNDFlNDY0OWI5MzRjYTQ5NTk5MWI3ODUyYjg1NSIsInR0bF9zZ3N0IjowfSx7InR0bF9pZ3N0IjowLCJzZWNfbm0iOiJUWFBEVCIsInR0bF9jZ3N0IjowLCJ0dGxfY2VzcyI6MCwidHRsX3RheCI6MCwidHRsX3ZhbCI6MCwidHRsX3JlYyI6MCwiY2hrc3VtIjoiZTNiMGM0NDI5OGZjMWMxNDlhZmJmNGM4OTk2ZmI5MjQyN2FlNDFlNDY0OWI5MzRjYTQ5NTk5MWI3ODUyYjg1NSIsInR0bF9zZ3N0IjowfSx7InR0bF9pZ3N0IjowLCJzZWNfbm0iOiJDRE5UIiwidHRsX2Nnc3QiOjUwMCwiY3B0eV9zdW0iOlt7InR0bF9pZ3N0IjowLCJ0dGxfY2dzdCI6NTAwLCJjdGluIjoiMjdHU1BNSDAwMTJHMVo0IiwidHRsX2Nlc3MiOjUwMCwidHRsX3RheCI6MCwidHRsX3ZhbCI6MTAwMDAsInR0bF9yZWMiOjEsImNoa3N1bSI6ImVhMmIwMjQyMGQ1ZDllODQ3N2I1ODc0ODg3YzI5ZjAyM2Q1Nzc3ZTg5YWRmYjk3MzlkNTU0Nzg1NmY2ZGRlY2EiLCJ0dGxfc2dzdCI6OTAwfV0sInR0bF9jZXNzIjo1MDAsInR0bF90YXgiOjAsInR0bF92YWwiOjEwMDAwLCJ0dGxfcmVjIjoxLCJjaGtzdW0iOiJmZDE3M2QxMjA4MzE1NTliNWM5Y2M1MTdlYmYyMjM5M2Y4MGY2MDQ2N2U2NDQxZTRjNTViYjk1NWMwYzVhNTg1IiwidHRsX3Nnc3QiOjkwMH0seyJ0dGxfaWdzdCI6MCwic2VjX25tIjoiQVRBVCIsInR0bF9jZ3N0IjowLCJ0dGxfY2VzcyI6MCwidHRsX3RheCI6MCwidHRsX3ZhbCI6MCwidHRsX3JlYyI6MCwiY2hrc3VtIjoiZTNiMGM0NDI5OGZjMWMxNDlhZmJmNGM4OTk2ZmI5MjQyN2FlNDFlNDY0OWI5MzRjYTQ5NTk5MWI3ODUyYjg1NSIsInR0bF9zZ3N0IjowfSx7InR0bF9pZ3N0IjowLCJzZWNfbm0iOiJCMkJBVCIsInR0bF9jZ3N0IjowLCJ0dGxfY2VzcyI6MCwidHRsX3RheCI6MCwidHRsX3ZhbCI6MCwidHRsX3JlYyI6MCwiY2hrc3VtIjoiZTNiMGM0NDI5OGZjMWMxNDlhZmJmNGM4OTk2ZmI5MjQyN2FlNDFlNDY0OWI5MzRjYTQ5NTk5MWI3ODUyYjg1NSIsInR0bF9zZ3N0IjowfSx7InR0bF9pZ3N0IjowLCJzZWNfbm0iOiJCMkJUIiwidHRsX2Nnc3QiOjEwMCwiY3B0eV9zdW0iOlt7InR0bF9pZ3N0IjowLCJ0dGxfY2dzdCI6MTAwLCJjdGluIjoiMjdHU1BNSDAwMTJHMVo0IiwidHRsX2Nlc3MiOjEwMCwidHRsX3RheCI6MTAwLCJ0dGxfdmFsIjoxMDAwMC4wMSwidHRsX3JlYyI6MSwiY2hrc3VtIjoiZjQ3ZGNiYmZmYWQ1OWQ2MzIzZGIyZjc1YzkxNDNhZjg0OWRlNmZkYzNjMzVhZmI3MDQwNTQzNTJiOTY5YmQxMiIsInR0bF9zZ3N0IjoxMDB9XSwidHRsX2Nlc3MiOjEwMCwidHRsX3RheCI6MTAwLCJ0dGxfdmFsIjoxMDAwMC4wMSwidHRsX3JlYyI6MSwiY2hrc3VtIjoiYjYyYzIyOGQ3NWI2MzMwNGI2ZWFlMzg0ZDZlNWIxYzZkZWU1YzRiNDg1ODZjNGVmMmMyMDI1YTIyMjM2MTI2ZSIsInR0bF9zZ3N0IjoxMDB9XSwiZ3N0aW4iOiIyN0dTUE1IMTAzMUcxWloiLCJjaGtzdW0iOiJlN2MyNzk0MWExYmM5ZWMwNmRjNmE0YTJjMDdkMTZkZDJhMzM1ZTA3ZjYyODFjOTRlZDk0MThmMTJiZWU4MDRjIn0="));

	   System.out.println("file has is = "+gethashdataFile("D:\\Returns_29_May_2017_GSTR1_beta.zip"));
	   
   }
   
}
