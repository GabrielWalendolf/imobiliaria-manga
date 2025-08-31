package br.edu.univille.poo.dao;

import java.sql.SQLException;
import java.util.Optional;

public class AdminDAO extends BaseDAO {

    public Optional<Admin> obterPorUsuario(String usuario) {
        String sql = "SELECT id_admin, usuario, senha_hash FROM administradores WHERE usuario = ?";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setString(1, usuario);
            try (var rs = pre.executeQuery()) {
                if (rs.next()) {
                    Admin admin = new Admin();
                    admin.setId(rs.getLong("id_admin"));
                    admin.setUsuario(rs.getString("usuario"));
                    admin.setSenhaHash(rs.getString("senha_hash"));
                    return Optional.of(admin);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
