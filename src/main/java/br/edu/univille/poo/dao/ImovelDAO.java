package br.edu.univille.poo.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImovelDAO extends BaseDAO {

    public boolean cadastrar(Imovel imovel) {
        String sql = "INSERT INTO imoveis(endereco, tipo_imovel, area_m2, quartos, banheiros, " +
                "vagas_garagem, status, bairro, cidade, cep) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setString(1, imovel.getEndereco());
            pre.setString(2, imovel.getTipoImovel());
            pre.setDouble(3, imovel.getAreaM2());
            pre.setInt(4, imovel.getQuartos());
            pre.setInt(5, imovel.getBanheiros());
            pre.setInt(6, imovel.getVagasGaragem());
            pre.setString(7, imovel.getStatus());
            pre.setString(8, imovel.getBairro());
            pre.setString(9, imovel.getCidade());
            pre.setString(10, imovel.getCep());
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar imóvel: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(Imovel imovel) {
        String sql = "UPDATE imoveis SET endereco = ?, tipo_imovel = ?, area_m2 = ?, quartos = ?, " +
                "banheiros = ?, vagas_garagem = ?, status = ?, bairro = ?, cep = ? WHERE id_imovel = ?";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setString(1, imovel.getEndereco());
            pre.setString(2, imovel.getTipoImovel());
            pre.setDouble(3, imovel.getAreaM2());
            pre.setInt(4, imovel.getQuartos());
            pre.setInt(5, imovel.getBanheiros());
            pre.setInt(6, imovel.getVagasGaragem());
            pre.setString(7, imovel.getStatus());
            pre.setString(8, imovel.getBairro());
            pre.setString(9, imovel.getCep());
            pre.setLong(10, imovel.getId());
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar imóvel: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarPorId(long id) {
        String sql = "DELETE FROM imoveis WHERE id_imovel = ?";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar imóvel: " + e.getMessage());
            return false;
        }
    }

    public Optional<Imovel> obterPorId(long id) {
        String sql = "SELECT * FROM imoveis WHERE id_imovel = ?";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            try (var rs = pre.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToImovel(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Imovel> obterTodos() {
        String sql = "SELECT * FROM imoveis ORDER BY endereco";
        List<Imovel> lista = new ArrayList<>();
        try (var con = con(); var pre = con.prepareStatement(sql); var rs = pre.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRowToImovel(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private Imovel mapRowToImovel(java.sql.ResultSet rs) throws SQLException {
        Imovel imovel = new Imovel();
        imovel.setId(rs.getLong("id_imovel"));
        imovel.setEndereco(rs.getString("endereco"));
        imovel.setCidade(rs.getString("cidade"));
        imovel.setCep(rs.getString("cep"));
        imovel.setTipoImovel(rs.getString("tipo_imovel"));
        imovel.setAreaM2(rs.getDouble("area_m2"));
        imovel.setQuartos(rs.getInt("quartos"));
        imovel.setBanheiros(rs.getInt("banheiros"));
        imovel.setVagasGaragem(rs.getInt("vagas_garagem"));
        imovel.setStatus(rs.getString("status"));
        imovel.setBairro(rs.getString("bairro"));
        return imovel;
    }
}
