package com.vn.ld.btc.fe.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.vn.ld.common.Constants;

public class GoogleAuthenticateUtils {
	public static final String TOTP_URL = "otpauth://totp/{1}@{2}?secret={3}";
	public static final Integer QRCODE_HEIGHT = 125;
	public static final Integer QRCODE_WIDTH = 125;
	public static final Integer PRIVATE_KEY_LENGTH = 32;
	public static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	public static final String AES_ENCRYPT_ALGORITHM = "AES";
	
	private static Logger LOG = Logger.getLogger(GoogleAuthenticateUtils.class);
	
	public static String generateSecretKey(String hashKey) {
		LOG.debug("Start generate secret key");
		// Allocating the buffer
		byte[] buffer = new byte[Constants.SECRET_SIZE + Constants.NUMBER_OF_SCRATCH_CODES
				* Constants.SCRATCH_CODE_SIZE];

		// Filling the buffer with random numbers.
		// Notice: you want to reuse the same random generator
		// while generating larger random number sequences.
		new SecureRandom().nextBytes(buffer);

		// Getting the key and converting it to Base32
		Base32 codec = new Base32();
		byte[] secretKey = Arrays.copyOf(buffer, Constants.SECRET_SIZE);
		byte[] bEncodedKey = codec.encode(secretKey);
		String encodedKey = new String(bEncodedKey);
		
		String privateKey = generatePrivateKey(hashKey);
		String returnKey = encryptAES(privateKey, encodedKey);
		LOG.debug("End generate secret key");
		return returnKey;
	}

	public static Boolean checkCode(String secret, String hashKey, long code, long timeMsec) {
		LOG.debug("Start check the code");
		if (StringUtils.isBlank(secret)) {
			LOG.info("Secret code is empty.");
			return false;
		}
		String privateKey = generatePrivateKey(hashKey);
		String decryptedSecret = decryptAES(privateKey, secret);
		Base32 codec = new Base32();
		byte[] decodedKey = codec.decode(decryptedSecret);

		// convert unix msec time into a 30 second "window"
		// this is per the TOTP spec (see the RFC for details)
		long t = (timeMsec / 1000L) / 30L;
		// Window is used to check codes generated in the near past.
		// You can use this value to tune how far you're willing to go.

		for (int i = - Constants.WINDOW_SIZE; i <= Constants.WINDOW_SIZE; ++i) {
			long hash;
			try {
				hash = verifyCode(decodedKey, t + i);
			} catch (Exception e) {
				LOG.error("Error while verify the code.", e);
				throw new RuntimeException(e.getMessage());
			}
			if (hash == code) {
				return true;
			}
		}
		// The validation code is invalid.
		return false;
	}
	
	public static byte[] generateQRCode(String user, String host, String secret) {
		String s = TOTP_URL.replace("{1}", user).replace("{2}", host).replace("{3}", secret);
		ByteArrayOutputStream outs = QRCode.from(s).to(ImageType.PNG).withSize(QRCODE_WIDTH, QRCODE_HEIGHT).stream();
		return outs.toByteArray();
	}
	
	public static String encryptAES(String key, String stringToEncrypt) {
		String encryptedString = null;
		try {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), AES_ENCRYPT_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
			encryptedString = Base64.encodeBase64String(cipher.doFinal(stringToEncrypt.getBytes()));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | UnsupportedEncodingException | InvalidAlgorithmParameterException e) {
			LOG.error("Error while encrypting.", e);
		}
		return encryptedString;
	}
	
	public static String decryptAES(String key, String stringToDecrypt) {
		String decryptedString = null;
		try {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), AES_ENCRYPT_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
			decryptedString = new String(cipher.doFinal(Base64.decodeBase64(stringToDecrypt)), "UTF-8");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException | InvalidAlgorithmParameterException e) {
			LOG.error("Error while decrypting.", e);
		}
		return decryptedString;
	}
	
	public static String generatePrivateKey(String hashKey) {
		if (StringUtils.isBlank(hashKey)) {
			return null;
		} else if (hashKey.length() > PRIVATE_KEY_LENGTH) {
			return hashKey.substring(0, PRIVATE_KEY_LENGTH);
		} else {
			StringBuffer buf = new StringBuffer(hashKey);
			while (buf.length() < PRIVATE_KEY_LENGTH) {
				buf.append(hashKey);
			}
			return buf.substring(0, PRIVATE_KEY_LENGTH);
		}
	}

	private static int verifyCode(byte[] key, long t)
			throws NoSuchAlgorithmException, InvalidKeyException {
		byte[] data = new byte[8];
		long value = t;
		for (int i = 8; i-- > 0; value >>>= 8) {
			data[i] = (byte) value;
		}

		SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(signKey);
		byte[] hash = mac.doFinal(data);

		int offset = hash[19] & 0xF;

		long truncatedHash = 0;
		for (int i = 0; i < 4; ++i) {
			truncatedHash <<= 8;
			// We are dealing with signed bytes:
			// we just keep the first byte.
			truncatedHash |= (hash[offset + i] & 0xFF);
		}

		truncatedHash &= 0x7FFFFFFF;
		truncatedHash %= 1000000;

		return (int) truncatedHash;
	}
}
