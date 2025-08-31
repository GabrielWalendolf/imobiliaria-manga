package br.edu.univille.poo.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteDAO extends BaseDAO {

    public boolean cadastrar(Cliente cliente) {
        String sql = "INSERT INTO clientes(nome_completo, cpf, telefone, email, data_cadastro) VALUES (?, ?, ?, ?, ?)";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setString(1, cliente.getNomeCompleto());
            pre.setString(2, cliente.getCpf());
            pre.setString(3, cliente.getTelefone());
            pre.setString(4, cliente.getEmail());
            pre.setDate(5, cliente.getDataCadastro());
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar cliente: " + e.getMessage());
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
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarPorId(long id) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar cliente: " + e.getMessage());
            return false;
        }
    }

    public Optional<Cliente> obterPorId(long id) {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        try (var con = con(); var pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            try (var rs = pre.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToCliente(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Cliente> obterTodos() {
        String sql = "SELECT * FROM clientes ORDER BY nome_completo";
        List<Cliente> lista = new ArrayList<>();
        try (var con = con(); var pre = con.prepareStatement(sql); var rs = pre.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRowToCliente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private Cliente mapRowToCliente(java.sql.ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("id_cliente"));
        cliente.setNomeCompleto(rs.getString("nome_completo"));
        cliente.setCpf(rs.getString("cpf"));
        cliente.setTelefone(rs.getString("telefone"));
        cliente.setEmail(rs.getString("email"));
        cliente.setDataCadastro(rs.getDate("data_cadastro"));
        return cliente;
    }
}
