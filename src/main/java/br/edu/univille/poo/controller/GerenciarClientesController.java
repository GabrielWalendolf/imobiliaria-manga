package br.edu.univille.poo.controller;

import br.edu.univille.poo.dao.ClienteDAO;
import br.edu.univille.poo.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class GerenciarClientesController {

    @FXML private TableView<Cliente> tableViewClientes;
    @FXML private TableColumn<Cliente, Long> colId;
    @FXML private TableColumn<Cliente, String> colNomeCompleto;
    @FXML private TableColumn<Cliente, String> colCpf;
    @FXML private TableColumn<Cliente, String> colTelefone;
    @FXML private TableColumn<Cliente, String> colEmail;
    @FXML private TableColumn<Cliente, Date> colDataCadastro;

    private ClienteDAO clienteDAO;

    @FXML
    public void initialize() {
        clienteDAO = new ClienteDAO();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomeCompleto.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDataCadastro.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));
        carregarClientes();
    }

    private void carregarClientes() {
        List<Cliente> clientes = clienteDAO.listarTodos();
        ObservableList<Cliente> observableList = FXCollections.observableArrayList(clientes);
        tableViewClientes.setItems(observableList);
    }

    @FXML
    private void handleAtualizarCliente(ActionEvent event) {
        Cliente clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/univille/poo/view/CadastrarCliente.fxml"));
                Parent root = loader.load();
                CadastrarClienteController controller = loader.getController();
                controller.setClienteParaEdicao(clienteSelecionado);
                Stage stage = new Stage();
                stage.setTitle("Atualizar Cliente");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                carregarClientes();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar a tela de atualização.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Nenhum Cliente Selecionado", "Por favor, selecione um cliente para atualizar.");
        }
    }

    @FXML
    private void handleDeletarCliente(ActionEvent event) {
        Cliente clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText("Excluir Cliente: " + clienteSelecionado.getNomeCompleto());
            alert.setContentText("Tem certeza que deseja excluir o cliente selecionado?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (clienteDAO.deletar(clienteSelecionado.getId())) {
                    showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Cliente excluído com sucesso!");
                    carregarClientes();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao excluir cliente.");
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Nenhum Cliente Selecionado", "Por favor, selecione um cliente para deletar.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
