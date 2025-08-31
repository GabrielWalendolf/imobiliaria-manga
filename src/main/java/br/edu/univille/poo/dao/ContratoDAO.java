package br.edu.univille.poo.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para a entidade Contrato.
 * Utiliza JOINs para buscar dados relacionados de Clientes e Imóveis de forma eficiente.
 */
public class ContratoDAO extends BaseDAO {

    // --- MÉTODOS DE MODIFICAÇÃO (INSERT, UPDATE, DELETE) ---
    // Estes métodos não precisam de JOINs, pois afetam apenas a tabela 'contratos'.

    public boolean cadastrar(Contrato contrato) {
        // Para cadastrar, precisamos dos IDs do cliente e do imóvel.
        if (contrato.getCliente() == null || contrato.getImovel() == null) {
            System.err.println("Erro ao cadastrar: Cliente ou Imóvel não associado ao contrato.");
            return false;
        }
        String sql = "INSERT INTO contratos(id_cliente, id_imovel, valor_aluguel, data_inicio, data_fim, status_contrato) VALUES (?, ?, ?, ?, ?, ?)";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setLong(1, contrato.getCliente().getId());
            pre.setLong(2, contrato.getImovel().getId());
            pre.setBigDecimal(3, contrato.getValorAluguel());
            pre.setDate(4, contrato.getDataInicio());
            pre.setDate(5, contrato.getDataFim());
            pre.setString(6, contrato.getStatusContrato());
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar contrato: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(Contrato contrato) {
        if (contrato.getCliente() == null || contrato.getImovel() == null) {
            System.err.println("Erro ao atualizar: Cliente ou Imóvel não associado ao contrato.");
            return false;
        }
        String sql = "UPDATE contratos SET id_cliente = ?, id_imovel = ?, valor_aluguel = ?, data_inicio = ?, data_fim = ?, status_contrato = ? WHERE id_contrato = ?";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setLong(1, contrato.getCliente().getId());
            pre.setLong(2, contrato.getImovel().getId());
            pre.setBigDecimal(3, contrato.getValorAluguel());
            pre.setDate(4, contrato.getDataInicio());
            pre.setDate(5, contrato.getDataFim());
            pre.setString(6, contrato.getStatusContrato());
            pre.setLong(7, contrato.getId());
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar contrato: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarPorId(long id) {
        String sql = "DELETE FROM contratos WHERE id_contrato = ?";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar contrato: " + e.getMessage());
            return false;
        }
    }


    // --- MÉTODOS DE CONSULTA (SELECT) COM JOIN ---

    /**
     * Busca um contrato completo pelo seu ID, incluindo os dados do Cliente e do Imóvel.
     * @param id O ID do contrato a ser buscado.
     * @return um Optional contendo o Contrato completo se encontrado.
     */
    public Optional<Contrato> obterPorId(long id) {
        // A consulta SQL agora une as três tabelas para buscar todos os dados de uma vez.
        String sql = "SELECT * FROM contratos c " +
                "JOIN clientes cl ON c.id_cliente = cl.id_cliente " +
                "JOIN imoveis im ON c.id_imovel = im.id_imovel " +
                "WHERE c.id_contrato = ?";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            try (var rs = pre.executeQuery()) {
                if (rs.next()) {
                    // O método de mapeamento agora constrói o objeto Contrato e seus filhos.
                    return Optional.of(mapRowToContratoCompleto(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Retorna uma lista com todos os contratos, cada um com seus respectivos
     * dados de Cliente e Imóvel já preenchidos.
     * @return Uma lista de objetos Contrato completos.
     */
    public List<Contrato> obterTodos() {
        String sql = "SELECT * FROM contratos c " +
                "JOIN clientes cl ON c.id_cliente = cl.id_cliente " +
                "JOIN imoveis im ON c.id_imovel = im.id_imovel " +
                "ORDER BY c.data_inicio DESC";
        List<Contrato> lista = new ArrayList<>();
        try (var con = con(); var pre = con.prepareStatement(sql); var rs = pre.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRowToContratoCompleto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Método auxiliar privado para mapear uma linha do ResultSet (que contém dados das 3 tabelas)
     * para um objeto Contrato totalmente preenchido.
     * @param rs O ResultSet posicionado na linha a ser mapeada.
     * @return Um objeto Contrato completo.
     * @throws SQLException
     */
    private Contrato mapRowToContratoCompleto(java.sql.ResultSet rs) throws SQLException {
        // 1. Mapeia os dados do Cliente a partir das colunas da tabela 'clientes'
        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("id_cliente"));
        cliente.setNomeCompleto(rs.getString("nome_completo"));
        cliente.setCpf(rs.getString("cpf"));
        cliente.setTelefone(rs.getString("telefone"));
        cliente.setEmail(rs.getString("email"));
        cliente.setDataCadastro(rs.getDate("data_cadastro"));

        // 2. Mapeia os dados do Imóvel a partir das colunas da tabela 'imoveis'
        Imovel imovel = new Imovel();
        imovel.setId(rs.getLong("id_imovel"));
        imovel.setEndereco(rs.getString("endereco"));
        imovel.setTipoImovel(rs.getString("tipo_imovel"));
        imovel.setAreaM2(rs.getDouble("area"));
        imovel.setQuartos(rs.getInt("numero_quartos"));
        imovel.setBanheiros(rs.getInt("numero_banheiros"));
        imovel.setVagasGaragem(rs.getInt("vagas_garagem"));
        imovel.setStatus(rs.getString("status"));

        // 3. Mapeia os dados do Contrato e associa os objetos Cliente e Imóvel
        Contrato contrato = new Contrato();
        contrato.setId(rs.getLong("id_contrato"));
        contrato.setValorAluguel(rs.getBigDecimal("valor_aluguel"));
        contrato.setDataInicio(rs.getDate("data_inicio"));
        contrato.setDataFim(rs.getDate("data_fim"));
        contrato.setStatusContrato(rs.getString("status_contrato"));

        // Associa os objetos já preenchidos
        contrato.setCliente(cliente);
        contrato.setImovel(imovel);

        return contrato;
    }
}
