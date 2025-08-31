package br.edu.univille.poo.dao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Date;
import java.time.LocalDate;
import javafx.scene.control.cell.TextFieldTableCell;

public class CadastrarClienteController {

    @FXML private TextField campoNome;
    @FXML private TextField campoCpf;
    @FXML private TextField campoTelefone;
    @FXML private TextField campoEmail;
    @FXML private Button botaoSalvar;

    private final ClienteDAO clienteDAO = new ClienteDAO();



    @FXML
    private void salvarNovoCliente(ActionEvent event) {
        String nome = campoNome.getText().trim();
        String cpf = campoCpf.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty()) {
            exibirAlerta("Erro de Validação", "Os campos 'Nome Completo' e 'CPF' são obrigatórios.");
            return;
        }

        Cliente novoCliente = new Cliente();
        novoCliente.setNomeCompleto(nome);
        novoCliente.setCpf(cpf);
        novoCliente.setTelefone(campoTelefone.getText().trim());
        novoCliente.setEmail(campoEmail.getText().trim());
        novoCliente.setDataCadastro(Date.valueOf(LocalDate.now()));

        // Lógica de salvamento revisada
        boolean sucesso = clienteDAO.cadastrar(novoCliente);

        if (sucesso) {
            exibirAlerta("Sucesso", "Cliente cadastrado com sucesso!");
            fecharJanela();
        } else {
            exibirAlerta("Erro no Banco de Dados", "Ocorreu um erro ao salvar o cliente. Verifique o console para mais detalhes.");
        }
    }

    @FXML
    private void cancelarCadastro(ActionEvent event) {
        fecharJanela();
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
