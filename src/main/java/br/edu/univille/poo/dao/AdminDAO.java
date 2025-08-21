package br.edu.univille.poo.dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;

public class AdminDAO extends BaseDAO {

    /**
     * Tenta autenticar um administrador com base no usuário e senha.
     * @param usuario O nome de usuário.
     * @param senhaPura A senha em texto puro digitada pelo usuário.
     * @return true se a autenticação for bem-sucedida, false caso contrário.
     */
    public boolean autenticar(String usuario, String senhaPura) {
        String sql = "SELECT senha_hash FROM administradores WHERE usuario = ?";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setString(1, usuario);
            var rs = pre.executeQuery();

            if (rs.next()) {
                String hashDoBanco = rs.getString("senha_hash");
                String hashDaSenhaDigitada = gerarHashSHA256(senhaPura);

                // Compara o hash da senha digitada com o hash armazenado no banco.
                return hashDoBanco.equals(hashDaSenhaDigitada);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Retorna false se o usuário não for encontrado ou se ocorrer um erro.
        return false;
    }

    /**
     * Gera um hash SHA-256 para uma dada string (senha).
     * @param data A string a ser hasheada.
     * @return A representação hexadecimal do hash.
     */
    private String gerarHashSHA256(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            // Converte o array de bytes para uma string hexadecimal
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
            // Este erro é muito improvável de acontecer em um ambiente Java padrão.
            throw new RuntimeException("Algoritmo de hash SHA-256 não encontrado.", e);
        }
    }
}
