package pss.trabalhofinal.bancodeimagens.factory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class PasswordEncryptor {

    public static final String encrypt(String password) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte mdPasswordAdmin[] = md.digest(password.getBytes("UTF-8"));

            StringBuilder hexStringPassword = new StringBuilder();

            for (byte b : mdPasswordAdmin) {
                hexStringPassword.append(String.format("%02X", 0xFF & b));
            }

            String passwordHash = hexStringPassword.toString();

            return passwordHash;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {

            throw new RuntimeException("Erro ao criptogravar senha.");
        }
    }
}
