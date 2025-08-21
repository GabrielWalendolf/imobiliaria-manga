package br.edu.univille.poo.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteDAO extends BaseDAO {

    // Funcionalidade: Cadastrar Cliente
    public void inserir(Cliente cliente) {
        String sql = "INSERT INTO clientes(nome_completo, cpf, telefone, email) VALUES(?, ?, ?, ?)";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setString(1, cliente.getNomeCompleto());
            pre.setString(2, cliente.getCpf());
            pre.setString(3, cliente.getTelefone());
            pre.setString(4, cliente.getEmail());
            pre.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Relatório 3: Clientes com mais contratos
    public List<String> obterClientesComMaisContratos() {
        List<String> resultado = new ArrayList<>();
        String sql = "SELECT cl.nome_completo, COUNT(co.id_contrato) AS total_de_contratos " +
                "FROM clientes cl " +
                "JOIN contratos co ON cl.id_cliente = co.id_cliente_fk " +
                "GROUP BY cl.id_cliente, cl.nome_completo " +
                "ORDER BY total_de_contratos DESC LIMIT 10";
        try (var con = con(); var pre = con.prepareStatement(sql); var rs = pre.executeQuery()) {
            while (rs.next()) {
                String linha = String.format("Nome: %-30s | Contratos: %d",
                        rs.getString("nome_completo"),
                        rs.getInt("total_de_contratos"));
                resultado.add(linha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }





    public boolean deletarPorId(long id) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            return pre.executeUpdate() > 0; // Retorna true se 1 linha foi afetada
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nome_completo = ?, cpf = ?, telefone = ?, email = ? WHERE id_cliente = ?";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setString(1, cliente.getNomeCompleto());
            pre.setString(2, cliente.getCpf());
            pre.setString(3, cliente.getTelefone());
            pre.setString(4, cliente.getEmail());
            pre.setLong(5, cliente.getId());
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Cliente> obterPorId(long id) {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            var rs = pre.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("id_cliente"));
                cliente.setNomeCompleto(rs.getString("nome_completo"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                return Optional.of(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
