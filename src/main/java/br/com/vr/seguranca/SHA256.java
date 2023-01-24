package br.com.vr.seguranca;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
    public static String criptografar(String original) {
        
        try {
            final MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            final byte messageDigest[] = algorithm.digest(original.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();

            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            throw new RuntimeException("Erro ao criptogragar senha", ex);
        }

    }

}
