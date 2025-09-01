package br.edu.univille.poo.dao;

import br.edu.univille.poo.model.Cliente;
import br.edu.univille.poo.model.Contrato;
import br.edu.univille.poo.model.Imovel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContratoDAO extends BaseDAO {

    public boolean cadastrar(Contrato contrato) {
        if (contrato.getCliente() == null || contrato.getImovel() == null) {
            System.err.println("Erro ao cadastrar: Cliente ou Imóvel não associado ao contrato.");
            return false;
        }
        String sql = "INSERT INTO contratos(id_cliente, id_imovel, valor_aluguel, data_inicio, data_fim, status_contrato) VALUES (?, ?, ?, ?, ?, ?)";
        try (var con = ConnectionFactory.getInstance().get();
             var pre = con.prepareStatement(sql)) {
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
        String sql = "UPDATE contratos SET id_cliente=?, id_imovel=?, valor_aluguel=?, data_inicio=?, data_fim=?, status_contrato=? WHERE id=?";
        try (var con = ConnectionFactory.getInstance().get();
             var pre = con.prepareStatement(sql)) {
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

    public boolean deletar(long id) {
        String sql = "DELETE FROM contratos WHERE id=?";
        try (var con = ConnectionFactory.getInstance().get();
             var pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar contrato: " + e.getMessage());
            return false;
        }
    }

    public Contrato buscarPorId(long id) {
        String sql = "SELECT c.*, cl.nome_completo AS cliente_nome, i.endereco AS imovel_endereco " +
                "FROM contratos c " +
                "JOIN clientes cl ON c.id_cliente = cl.id " +
                "JOIN imoveis i ON c.id_imovel = i.id " +
                "WHERE c.id=?";
        try (var con = ConnectionFactory.getInstance().get();
             var pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            var res = pre.executeQuery();
            if (res.next()) {
                var cliente = new Cliente();
                cliente.setId(res.getLong("id_cliente"));
                cliente.setNomeCompleto(res.getString("cliente_nome"));
                var imovel = new Imovel();
                imovel.setId(res.getLong("id_imovel"));
                imovel.setEndereco(res.getString("imovel_endereco"));
                return new Contrato(
                        res.getLong("id"),
                        res.getBigDecimal("valor_aluguel"),
                        res.getDate("data_inicio"),
                        res.getDate("data_fim"),
                        res.getString("status_contrato"),
                        cliente,
                        imovel
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar contrato por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Contrato> listarTodos() {
        var contratos = new ArrayList<Contrato>();
        String sql = "SELECT c.*, cl.nome_completo AS cliente_nome, i.endereco AS imovel_endereco " +
                "FROM contratos c " +
                "JOIN clientes cl ON c.id_cliente = cl.id " +
                "JOIN imoveis i ON c.id_imovel = i.id";
        try (var con = ConnectionFactory.getInstance().get();
             var pre = con.prepareStatement(sql);
             var res = pre.executeQuery()) {
            while (res.next()) {
                var cliente = new Cliente();
                cliente.setId(res.getLong("id_cliente"));
                cliente.setNomeCompleto(res.getString("cliente_nome"));
                var imovel = new Imovel();
                imovel.setId(res.getLong("id_imovel"));
                imovel.setEndereco(res.getString("imovel_endereco"));
                contratos.add(new Contrato(
                        res.getLong("id"),
                        res.getBigDecimal("valor_aluguel"),
                        res.getDate("data_inicio"),
                        res.getDate("data_fim"),
                        res.getString("status_contrato"),
                        cliente,
                        imovel
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar todos os contratos: " + e.getMessage());
        }
        return contratos;
    }

    public List<Contrato> listarAtivos() {
        var contratos = new ArrayList<Contrato>();
        String sql = "SELECT c.*, cl.nome_completo AS cliente_nome, i.endereco AS imovel_endereco " +
                "FROM contratos c " +
                "JOIN clientes cl ON c.id_cliente = cl.id " +
                "JOIN imoveis i ON c.id_imovel = i.id " +
                "WHERE c.status_contrato = 'Ativo'";
        try (var con = ConnectionFactory.getInstance().get();
             var pre = con.prepareStatement(sql);
             var res = pre.executeQuery()) {
            while (res.next()) {
                var cliente = new Cliente();
                cliente.setId(res.getLong("id_cliente"));
                cliente.setNomeCompleto(res.getString("cliente_nome"));
                var imovel = new Imovel();
                imovel.setId(res.getLong("id_imovel"));
                imovel.setEndereco(res.getString("imovel_endereco"));
                contratos.add(new Contrato(
                        res.getLong("id"),
                        res.getBigDecimal("valor_aluguel"),
                        res.getDate("data_inicio"),
                        res.getDate("data_fim"),
                        res.getString("status_contrato"),
                        cliente,
                        imovel
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar contratos ativos: " + e.getMessage());
        }
        return contratos;
    }

    public List<Contrato> listarContratosAVencer(int dias) {
        var contratos = new ArrayList<Contrato>();
        String sql = "SELECT c.*, cl.nome_completo AS cliente_nome, i.endereco AS imovel_endereco " +
                "FROM contratos c " +
                "JOIN clientes cl ON c.id_cliente = cl.id " +
                "JOIN imoveis i ON c.id_imovel = i.id " +
                "WHERE c.data_fim <= (CURRENT_DATE + CAST(? || ' days' AS INTERVAL)) AND c.status_contrato = 'Ativo'";
        try (var con = ConnectionFactory.getInstance().get();
             var pre = con.prepareStatement(sql)) {
            pre.setInt(1, dias);
            var res = pre.executeQuery();
            while (res.next()) {
                var cliente = new Cliente();
                cliente.setId(res.getLong("id_cliente"));
                cliente.setNomeCompleto(res.getString("cliente_nome"));
                var imovel = new Imovel();
                imovel.setId(res.getLong("id_imovel"));
                imovel.setEndereco(res.getString("imovel_endereco"));
                contratos.add(new Contrato(
                        res.getLong("id"),
                        res.getBigDecimal("valor_aluguel"),
                        res.getDate("data_inicio"),
                        res.getDate("data_fim"),
                        res.getString("status_contrato"),
                        cliente,
                        imovel
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar contratos a vencer: " + e.getMessage());
        }
        return contratos;
    }
}
