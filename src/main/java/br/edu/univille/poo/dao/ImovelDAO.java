package br.edu.univille.poo.dao;

import br.edu.univille.poo.model.Imovel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImovelDAO extends BaseDAO {

    public boolean cadastrar(Imovel imovel) {
        String sql = "INSERT INTO imoveis(endereco, bairro, cidade, cep, tipo_imovel, area_m2, quartos, banheiros, vagas_garagem, status, data_cadastro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (var con = ConnectionFactory.getInstance().get();
             var pre = con.prepareStatement(sql)) {
            pre.setString(1, imovel.getEndereco());
            pre.setString(2, imovel.getBairro());
            pre.setString(3, imovel.getCidade());
            pre.setString(4, imovel.getCep());
            pre.setString(5, imovel.getTipoImovel());
            pre.setBigDecimal(6, imovel.getAreaM2());
            pre.setInt(7, imovel.getQuartos());
            pre.setInt(8, imovel.getBanheiros());
            pre.setInt(9, imovel.getVagasGaragem());
            pre.setString(10, imovel.getStatus());
            pre.setDate(11, imovel.getDataCadastro());
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar imóvel: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(Imovel imovel) {
        String sql = "UPDATE imoveis SET endereco=?, bairro=?, cidade=?, cep=?, tipo_imovel=?, area_m2=?, quartos=?, banheiros=?, vagas_garagem=?, status=? WHERE id=?";
        try (var con = ConnectionFactory.getInstance().get();
             var pre = con.prepareStatement(sql)) {
            pre.setString(1, imovel.getEndereco());
            pre.setString(2, imovel.getBairro());
            pre.setString(3, imovel.getCidade());
            pre.setString(4, imovel.getCep());
            pre.setString(5, imovel.getTipoImovel());
            pre.setBigDecimal(6, imovel.getAreaM2());
            pre.setInt(7, imovel.getQuartos());
            pre.setInt(8, imovel.getBanheiros());
            pre.setInt(9, imovel.getVagasGaragem());
            pre.setString(10, imovel.getStatus());
            pre.setLong(11, imovel.getId());
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar imóvel: " + e.getMessage());
            return false;
        }
    }

    public boolean deletar(long id) {
        String sql = "DELETE FROM imoveis WHERE id=?";
        try (var con = ConnectionFactory.getInstance().get();
             var pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar imóvel: " + e.getMessage());
            return false;
        }
    }

    public Imovel buscarPorId(long id) {
        String sql = "SELECT * FROM imoveis WHERE id=?";
        try (var con = ConnectionFactory.getInstance().get();
             var pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            var res = pre.executeQuery();
            if (res.next()) {
                return new Imovel(
                        res.getLong("id"),
                        res.getString("endereco"),
                        res.getString("bairro"),
                        res.getString("cidade"),
                        res.getString("cep"),
                        res.getString("tipo_imovel"),
                        res.getBigDecimal("area_m2"),
                        res.getInt("quartos"),
                        res.getInt("banheiros"),
                        res.getInt("vagas_garagem"),
                        res.getString("status"),
                        res.getDate("data_cadastro")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar imóvel por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Imovel> listarTodos() {
        var imoveis = new ArrayList<Imovel>();
        String sql = "SELECT * FROM imoveis";
        try (var con = ConnectionFactory.getInstance().get();
             var pre = con.prepareStatement(sql);
             var res = pre.executeQuery()) {
            while (res.next()) {
                imoveis.add(new Imovel(
                        res.getLong("id"),
                        res.getString("endereco"),
                        res.getString("bairro"),
                        res.getString("cidade"),
                        res.getString("cep"),
                        res.getString("tipo_imovel"),
                        res.getBigDecimal("area_m2"),
                        res.getInt("quartos"),
                        res.getInt("banheiros"),
                        res.getInt("vagas_garagem"),
                        res.getString("status"),
                        res.getDate("data_cadastro")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar todos os imóveis: " + e.getMessage());
        }
        return imoveis;
    }
}
