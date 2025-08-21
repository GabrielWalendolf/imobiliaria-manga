// Crie esta classe temporária para gerar o hash
package br.edu.univille.poo.dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GeradorDeHash {

    public static void main(String[] args) {
        String senhaParaHashear = "admin123"; // A senha que você quer usar
        String hashGerado = gerarHashSHA256(senhaParaHashear);

        System.out.println("A senha é: " + senhaParaHashear);
        System.out.println("O hash SHA-256 correspondente é:");
        System.out.println(hashGerado);
    }

    // Copie este método exatamente como está no seu AdminDAO
    private static String gerarHashSHA256(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo de hash não encontrado.", e);
        }
    }
}
