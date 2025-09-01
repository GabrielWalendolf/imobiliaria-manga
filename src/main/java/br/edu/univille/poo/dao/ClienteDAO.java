package br.edu.univille.poo.dao;

import br.edu.univille.poo.model.Cliente;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends BaseDAO {

    public boolean cadastrar(Cliente cliente) {
        String sql = "INSERT INTO clientes(nome_completo, cpf, telefone, email, data_cadastro) VALUES (?, ?, ?, ?, ?)";
        try (var con = ConnectionFactory.getInstance().get();
             var pre = con.prepareStatement(sql)) {
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
        String sql = "UPDATE clientes SET nome_completo=?, cpf=?, telefone=?, email=? WHERE id=?";
        try (var con = ConnectionFactory.getInstance().get();
             var pre = con.prepareStatement(sql)) {
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

    public boolean deletar(long id) {
        String sql = "DELETE FROM clientes WHERE id=?";
        try (var con = ConnectionFactory.getInstance().get();
             var pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar cliente: " + e.getMessage());
            return false;
        }
    }

    public Cliente buscarPorId(long id) {
        String sql = "SELECT * FROM clientes WHERE id=?";
        try (var con = ConnectionFactory.getInstance().get();
             var pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            var res = pre.executeQuery();
            if (res.next()) {
                return new Cliente(
                        res.getLong("id"),
                        res.getString("nome_completo"),
                        res.getString("cpf"),
                        res.getString("telefone"),
                        res.getString("email"),
                        res.getDate("data_cadastro")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Cliente> listarTodos() {
        var clientes = new ArrayList<Cliente>();
        String sql = "SELECT * FROM clientes";
        try (var con = ConnectionFactory.getInstance().get();
             var pre = con.prepareStatement(sql);
             var res = pre.executeQuery()) {
            while (res.next()) {
                clientes.add(new Cliente(
                        res.getLong("id"),
                        res.getString("nome_completo"),
                        res.getString("cpf"),
                        res.getString("telefone"),
                        res.getString("email"),
                        res.getDate("data_cadastro")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar todos os clientes: " + e.getMessage());
        }
        return clientes;
    }
}
