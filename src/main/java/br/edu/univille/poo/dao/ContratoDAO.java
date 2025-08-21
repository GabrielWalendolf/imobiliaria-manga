package br.edu.univille.poo.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContratoDAO extends BaseDAO {

    // Funcionalidade: Cadastrar Contrato
    public void inserir(Contratos contrato) {
        String sql = "INSERT INTO contratos(id_cliente_fk, id_imovel_fk, valor_aluguel_mensal, data_inicio, data_fim, status_contrato) VALUES(?,?,?,?,?,?)";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setLong(1, contrato.getCliente().getId());
            pre.setLong(2, contrato.getImovel().getId());
            pre.setBigDecimal(3, contrato.getValorAluguelMensal());
            pre.setDate(4, new java.sql.Date(contrato.getDataInicio().getTime()));
            pre.setDate(5, new java.sql.Date(contrato.getDataFim().getTime()));
            pre.setString(6, "Ativo"); // Status inicial
            pre.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Relatório 2: Listar contratos ativos
    public List<String> obterContratosAtivos() {
        List<String> resultado = new ArrayList<>();
        String sql = "SELECT co.id_contrato, cl.nome_completo, im.endereco, co.data_fim " +
                "FROM contratos co " +
                "JOIN clientes cl ON co.id_cliente_fk = cl.id_cliente " +
                "JOIN imoveis im ON co.id_imovel_fk = im.id_imovel " +
                "WHERE co.status_contrato = 'Ativo' ORDER BY co.data_fim ASC";
        try (var con = con(); var pre = con.prepareStatement(sql); var rs = pre.executeQuery()) {
            while (rs.next()) {
                String linha = String.format("ID: %d | Cliente: %-25s | Imóvel: %-30s | Vencimento: %s",
                        rs.getInt("id_contrato"),
                        rs.getString("nome_completo"),
                        rs.getString("endereco"),
                        rs.getDate("data_fim").toLocalDate().toString());
                resultado.add(linha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    // Relatório 4: Contratos expirando nos próximos 30 dias
    public List<String> obterContratosExpirando() {
        List<String> resultado = new ArrayList<>();
        // SQL CORRIGIDO:
        String sql = "SELECT cl.nome_completo, im.endereco, co.data_fim " +
                "FROM contratos co " +
                "JOIN clientes cl ON co.id_cliente_fk = cl.id_cliente " +
                "JOIN imoveis im ON co.id_imovel_fk = im.id_imovel " +
                "WHERE co.status_contrato = 'Ativo' " +
                "AND co.data_fim >= CURRENT_DATE " +
                "AND co.data_fim < (CURRENT_DATE + INTERVAL '31 days') " +
                "ORDER BY co.data_fim ASC";
        try (var con = con(); var pre = con.prepareStatement(sql); var rs = pre.executeQuery()) {
            while (rs.next()) {
                String linha = String.format("Cliente: %-25s | Imóvel: %-30s | Vence em: %s",
                        rs.getString("nome_completo"),
                        rs.getString("endereco"),
                        rs.getDate("data_fim").toLocalDate().toString());
                resultado.add(linha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }



// ... (métodos inserir e de relatórios) ...

    /**
     * Deleta um contrato do banco de dados com base no seu ID.
     * @param id O ID do contrato a ser deletado.
     * @return true se a deleção foi bem-sucedida, false caso contrário.
     */
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

    /**
     * Atualiza os dados de um contrato existente no banco de dados.
     * @param contrato O objeto Contrato com os dados atualizados.
     * @return true se a atualização foi bem-sucedida, false caso contrário.
     */
    public boolean atualizar(Contratos contrato) {
        String sql = "UPDATE contratos SET id_cliente_fk = ?, id_imovel_fk = ?, " +
                "valor_aluguel_mensal = ?, data_inicio = ?, data_fim = ?, " +
                "status_contrato = ? WHERE id_contrato = ?";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setLong(1, contrato.getCliente().getId());
            pre.setLong(2, contrato.getImovel().getId());
            pre.setBigDecimal(3, contrato.getValorAluguelMensal());
            // Converte java.util.Date para java.sql.Date
            pre.setDate(4, new java.sql.Date(contrato.getDataInicio().getTime()));
            pre.setDate(5, new java.sql.Date(contrato.getDataFim().getTime()));
            pre.setString(6, contrato.getStatusContrato());
            pre.setLong(7, contrato.getId()); // ID para a cláusula WHERE
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar contrato: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca um único contrato pelo seu ID.
     * Este método também busca os dados básicos do cliente e do imóvel associados.
     * @param id O ID do contrato a ser buscado.
     * @return um Optional contendo o Contrato se encontrado, ou um Optional vazio.
     */
    public Optional<Contratos> obterPorId(long id) {
        // Usamos JOIN para já trazer os dados do cliente e do imóvel, se necessário.
        String sql = "SELECT * FROM contratos WHERE id_contrato = ?";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            var rs = pre.executeQuery();
            if (rs.next()) {
                Contratos contrato = new Contratos();
                contrato.setId(rs.getLong("id_contrato"));
                contrato.setValorAluguelMensal(rs.getBigDecimal("valor_aluguel_mensal"));
                contrato.setDataInicio(rs.getDate("data_inicio"));
                contrato.setDataFim(rs.getDate("data_fim"));
                contrato.setStatusContrato(rs.getString("status_contrato"));
                contrato.setDataAssinatura(rs.getTimestamp("data_assinatura"));

                // Cria objetos básicos para cliente e imóvel e seta os IDs.
                // Para obter todos os dados, seria necessário um JOIN mais complexo
                // ou chamadas separadas aos outros DAOs.
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("id_cliente_fk"));
                contrato.setCliente(cliente);

                Imovel imovel = new Imovel();
                imovel.setId(rs.getLong("id_imovel_fk"));
                contrato.setImovel(imovel);

                return Optional.of(contrato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
