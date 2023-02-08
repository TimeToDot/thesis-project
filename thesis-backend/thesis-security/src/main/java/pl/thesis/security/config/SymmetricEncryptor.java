package pl.thesis.security.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Component
public class SymmetricEncryptor {

    private String algorithm;
    private SecretKey key;
    private IvParameterSpec ivParameterSpec;

    @PostConstruct
    private void init() throws NoSuchAlgorithmException {
        key = generateKey(128);
        ivParameterSpec = generateIv();
        algorithm = "AES/CBC/PKCS5Padding";
    }


    public String encrypt(UUID input) {
        try {
            log.info("to encrypt: " + input);
            var cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            var stringUuid = input.toString();
            byte[] cipherText = cipher.doFinal(stringUuid.getBytes());
            var encrypted = Base64.getUrlEncoder().encodeToString(cipherText) + "encv1";
            log.info("encrypted: " + encrypted);

            return encrypted;
        } catch (InvalidAlgorithmParameterException | IllegalBlockSizeException | NoSuchPaddingException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String cipherText) {
        log.info("id to decrypt: " + cipherText);

        if (cipherText == null || !cipherText.endsWith("encv1")) {
            return cipherText;
        }

        try {
            cipherText = cipherText.substring(0, cipherText.length() -5);
            log.info("id to decrypt after cut: " + cipherText);
            var cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            byte[] plainText = cipher.doFinal(Base64.getUrlDecoder().decode(cipherText));

            return new String(plainText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException |
                 IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            log.error("decryption failed on: " + cipherText);
            log.error(ExceptionUtils.getStackTrace(e));
            throw new RuntimeException(e);
        }
    }

    private IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    private SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        return keyGenerator.generateKey();

    }
}
