// Crie esta classe temporária para gerar o hash
package br.edu.univille.poo.dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GeradorDeHash {

    public static void main(String[] args) {
        String senhaParaHashear = "admin123"; // A senha que você quer usar
        String hashGerado = gerarHash(senhaParaHashear);

        System.out.println("A senha é: " + senhaParaHashear);
        System.out.println("O hash SHA-256 correspondente é:");
        System.out.println(hashGerado);
    }

    public static String gerarHash(String data) { // Renomeei para ficar mais genérico
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
            // Em uma aplicação real, logar este erro seria importante.
            throw new RuntimeException("Algoritmo de hash SHA-256 não encontrado.", e);
        }
    }
}
