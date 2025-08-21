package br.edu.univille.poo.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImovelDAO extends BaseDAO {

    // Funcionalidade: Cadastrar Imóvel
    public void inserir(Imovel imovel) {
        String sql = "INSERT INTO imoveis(endereco, bairro, cidade, cep, tipo_imovel, area_m2, quartos, banheiros, vagas_garagem, status) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setString(1, imovel.getEndereco());
            pre.setString(2, imovel.getBairro());
            pre.setString(3, imovel.getCidade());
            pre.setString(4, imovel.getCep());
            pre.setString(5, imovel.getTipoImovel());
            pre.setBigDecimal(6, imovel.getAreaM2());
            pre.setInt(7, imovel.getQuartos());
            pre.setInt(8, imovel.getBanheiros());
            pre.setInt(9, imovel.getVagasGaragem());
            pre.setString(10, "Disponível"); // Status inicial
            pre.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Relatório 1: Listar imóveis disponíveis
    public List<Imovel> obterDisponiveis() {
        List<Imovel> lista = new ArrayList<>();
        String sql = "SELECT id_imovel, endereco, cidade, tipo_imovel, area_m2, quartos FROM imoveis WHERE status = 'Disponível'";
        try (var con = con(); var pre = con.prepareStatement(sql); var rs = pre.executeQuery()) {
            while (rs.next()) {
                Imovel imovel = new Imovel();
                imovel.setId(rs.getLong("id_imovel"));
                imovel.setEndereco(rs.getString("endereco"));
                imovel.setCidade(rs.getString("cidade"));
                imovel.setTipoImovel(rs.getString("tipo_imovel"));
                imovel.setAreaM2(rs.getBigDecimal("area_m2"));
                imovel.setQuartos(rs.getInt("quartos"));
                lista.add(imovel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Deleta um imóvel do banco de dados com base no seu ID.
     * @param id O ID do imóvel a ser deletado.
     * @return true se a deleção foi bem-sucedida, false caso contrário.
     */
    public boolean deletarPorId(long id) {
        String sql = "DELETE FROM imoveis WHERE id_imovel = ?";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            // executeUpdate() retorna o número de linhas afetadas.
            // Se for > 0, a deleção funcionou.
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar imóvel: " + e.getMessage());
            // Em caso de erro (ex: contrato associado que impede a deleção), a exceção será impressa.
            return false;
        }
    }

    /**
     * Atualiza os dados de um imóvel existente no banco de dados.
     * @param imovel O objeto Imovel com os dados atualizados.
     * @return true se a atualização foi bem-sucedida, false caso contrário.
     */
    public boolean atualizar(Imovel imovel) {
        String sql = "UPDATE imoveis SET endereco = ?, bairro = ?, cidade = ?, cep = ?, " +
                "tipo_imovel = ?, area_m2 = ?, quartos = ?, banheiros = ?, " +
                "vagas_garagem = ?, status = ? WHERE id_imovel = ?";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
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
            pre.setLong(11, imovel.getId()); // ID para a cláusula WHERE
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar imóvel: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca um único imóvel pelo seu ID.
     * @param id O ID do imóvel a ser buscado.
     * @return um Optional contendo o Imovel se encontrado, ou um Optional vazio.
     */
    public Optional<Imovel> obterPorId(long id) {
        String sql = "SELECT * FROM imoveis WHERE id_imovel = ?";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            var rs = pre.executeQuery();
            if (rs.next()) {
                Imovel imovel = new Imovel();
                imovel.setId(rs.getLong("id_imovel"));
                imovel.setEndereco(rs.getString("endereco"));
                imovel.setBairro(rs.getString("bairro"));
                imovel.setCidade(rs.getString("cidade"));
                imovel.setCep(rs.getString("cep"));
                imovel.setTipoImovel(rs.getString("tipo_imovel"));
                imovel.setAreaM2(rs.getBigDecimal("area_m2"));
                imovel.setQuartos(rs.getInt("quartos"));
                imovel.setBanheiros(rs.getInt("banheiros"));
                imovel.setVagasGaragem(rs.getInt("vagas_garagem"));
                imovel.setStatus(rs.getString("status"));
                imovel.setDataCadastro(rs.getTimestamp("data_cadastro"));
                return Optional.of(imovel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
