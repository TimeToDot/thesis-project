package pl.thesis.data.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Slf4j
@Component
public class AsymmetricEncryptor {

    private final String algorithm = "RSA";
    private final String directory = "secret";
    private final String privateKeyFileName = "private.key";
    private final String publicKeyFileName = "public.key";

    private String path;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    @PostConstruct
    private void init() {
        File f = new File(".");
        Path tempPath = null;

        if (f.exists()){
            tempPath = Paths.get(f.getAbsolutePath());

            if (f.getAbsolutePath().endsWith("thesis-server/.")){
                log.info(tempPath.toAbsolutePath().toString());
                tempPath = tempPath.getParent().getParent().getParent();
            }
        }

        path = tempPath.toAbsolutePath().toString();

        //path = "thesis-project";
        log.info(path);

        if (path == null){
            log.error(path);
            throw new RuntimeException("path does not exist");
        }

        if (!isKeysExist()) {
            log.info("Generate keys");
            KeyPair keyPair = generateKeys();

            log.info("save public and private keys to files at the given location");
            saveKeysToFiles(keyPair.getPublic(), keyPair.getPrivate());
            log.info("public and private keys saved");
        }

        publicKey = loadPublicKey();
        privateKey = loadPrivateKey();

        if (publicKey == null || privateKey == null){
            var message = "keys not initialized";
            log.error(message);

            throw new RuntimeException(message);
        }
        // Test
        String plainText = "This is a  secret message";
        /* Encrypting plain text to ciphertext */
        String encryptedText = encryptText(plainText);
        log.info("Encrypted text = " + encryptedText);

        /* Decrypting encrypted text back to plain text */
        String decryptedText = decryptText(encryptedText);
        log.info("Decrypted text = " + decryptedText);
    }

    public String encryptText(String plainText){
        if (plainText == null || plainText.isEmpty()) {
            log.error("No data to encrypt!");
            return plainText;
        }
        Cipher cipher = null;
        String encryptedString = "";
        try {
            // Creating a Cipher object
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            // Initializing a Cipher object with public key
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // Encrypting the plain text string
            byte[] encryptedText = cipher.doFinal(plainText.getBytes());

            // Encoding the encrypted text to Base64
            encryptedString = Base64.getEncoder().encodeToString(encryptedText);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                 | IllegalBlockSizeException | BadPaddingException ex) {
            var message = "Exception caught while encrypting : " + ExceptionUtils.getMessage(ex);
            log.error(message);
            throw new RuntimeException(message);
        }

        return encryptedString;
    }

    public String decryptText(String cipherText){
        if (cipherText == null || cipherText.isEmpty()) {
            log.error("No data to decrypt!");
            return cipherText;
        }
        String decryptedString = "";
        try {
            // Creating a Cipher object
            var cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            // Initializing a Cipher object with private key
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // Decoding from Base64
            byte[] encryptedText = Base64.getDecoder().decode(cipherText.getBytes());

            // Decrypting to plain text
            decryptedString = new String(cipher.doFinal(encryptedText));

        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
                 | IllegalBlockSizeException | BadPaddingException ex) {
            var message = "Exception caught while decrypting : " + ExceptionUtils.getMessage(ex);
            log.error(message);
            throw new RuntimeException(message);
        }
        return decryptedString;
    }

    private KeyPair generateKeys(){
        KeyPair keyPair = null;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);

            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

            // Initializing KeyPairGenerator
            keyGen.initialize(2048, random);

            // Generate keys
            keyPair = keyGen.generateKeyPair();

        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return keyPair;
    }

    private void saveKeysToFiles(PublicKey publicKey, PrivateKey privateKey){

        FileOutputStream fos = null;

        try {
            File file = new File(path + File.separator + publicKeyFileName);
            if (file.createNewFile()) {
                fos = new FileOutputStream(file);
                var x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                fos.write(x509EncodedKeySpec.getEncoded());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Private Key
        try {
            File file = new File(path + File.separator + privateKeyFileName);
            if (file.createNewFile()) {
                fos = new FileOutputStream(file);
                var pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                fos.write(pkcs8EncodedKeySpec.getEncoded());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private boolean isKeysExist(){
        File publicKeyFile = new File(path + File.separator + publicKeyFileName);
        File privateKeyFile = new File(path + File.separator + privateKeyFileName);

        return (publicKeyFile.exists() && !publicKeyFile.isDirectory()) &&
                (privateKeyFile.exists() && !privateKeyFile.isDirectory());
    }

    private PublicKey loadPublicKey(){
        FileInputStream fis = null;
        PublicKey publicKey = null;
        try {
            File file = new File(path + File.separator + publicKeyFileName);
            fis = new FileInputStream(file);
            byte[] encodedPublicKey = new byte[(int) file.length()];
            fis.read(encodedPublicKey);

            var x509EncodedKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            var keyFactory = KeyFactory.getInstance(algorithm);
            publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error(ExceptionUtils.getStackTrace(e));
                }
            }
        }
        return publicKey;
    }

    private PrivateKey loadPrivateKey(){
        FileInputStream fis = null;
        PrivateKey privateKey = null;

        try {
            var file = new File(path + File.separator + privateKeyFileName);
            fis = new FileInputStream(file);
            byte[] encodedPrivateKey = new byte[(int) file.length()];
            fis.read(encodedPrivateKey);

            var privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
            var keyFactory = KeyFactory.getInstance(algorithm);
            privateKey = keyFactory.generatePrivate(privateKeySpec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return privateKey;
    }

}
