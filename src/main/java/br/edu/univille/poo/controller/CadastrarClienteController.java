package br.edu.univille.poo.controller;

import br.edu.univille.poo.dao.ClienteDAO;
import br.edu.univille.poo.model.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Date;
import java.time.LocalDate;

public class CadastrarClienteController {

    @FXML private TextField campoNome;
    @FXML private TextField campoCpf;
    @FXML private TextField campoTelefone;
    @FXML private TextField campoEmail;
    @FXML private Button botaoSalvar;
    @FXML private Label labelTitulo;

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private Cliente clienteParaEdicao;

    public void setClienteParaEdicao(Cliente cliente) {
        this.clienteParaEdicao = cliente;
        if (cliente != null) {
            labelTitulo.setText("Editar Cliente");
            campoNome.setText(cliente.getNomeCompleto());
            campoCpf.setText(cliente.getCpf());
            campoTelefone.setText(cliente.getTelefone());
            campoEmail.setText(cliente.getEmail());
        }
    }

    @FXML
    private void salvarCliente(ActionEvent event) {
        String nome = campoNome.getText().trim();
        String cpf = campoCpf.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty()) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro de Validação", "Os campos 'Nome Completo' e 'CPF' são obrigatórios.");
            return;
        }

        boolean sucesso;
        String msgSucesso;

        if (clienteParaEdicao == null) {
            Cliente novoCliente = new Cliente();
            novoCliente.setNomeCompleto(nome);
            novoCliente.setCpf(cpf);
            novoCliente.setTelefone(campoTelefone.getText().trim());
            novoCliente.setEmail(campoEmail.getText().trim());
            novoCliente.setDataCadastro(Date.valueOf(LocalDate.now()));
            sucesso = clienteDAO.cadastrar(novoCliente);
            msgSucesso = "Cliente cadastrado com sucesso!";
        } else {
            clienteParaEdicao.setNomeCompleto(nome);
            clienteParaEdicao.setCpf(cpf);
            clienteParaEdicao.setTelefone(campoTelefone.getText().trim());
            clienteParaEdicao.setEmail(campoEmail.getText().trim());
            sucesso = clienteDAO.atualizar(clienteParaEdicao);
            msgSucesso = "Cliente atualizado com sucesso!";
        }

        if (sucesso) {
            exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", msgSucesso);
            fecharJanela();
        } else {
            exibirAlerta(Alert.AlertType.ERROR, "Erro no Banco de Dados", "Ocorreu um erro ao salvar o cliente.");
        }
    }

    // <<< MÉTODO CORRIGIDO/ADICIONADO AQUI >>>
    @FXML
    private void cancelar(ActionEvent event) {
        fecharJanela();
    }

    private void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void fecharJanela() {
        Stage stage = (Stage) botaoSalvar.getScene().getWindow();
        stage.close();
    }
}
