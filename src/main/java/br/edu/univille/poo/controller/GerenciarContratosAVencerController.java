package br.edu.univille.poo.controller;

import br.edu.univille.poo.dao.ContratoDAO;
import br.edu.univille.poo.model.Contrato;
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
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class GerenciarContratosAVencerController {

    @FXML private TableView<Contrato> tableViewContratos;
    @FXML private TableColumn<Contrato, Long> colId;
    @FXML private TableColumn<Contrato, String> colCliente;
    @FXML private TableColumn<Contrato, String> colImovel;
    @FXML private TableColumn<Contrato, BigDecimal> colValorAluguel;
    @FXML private TableColumn<Contrato, Date> colDataInicio;
    @FXML private TableColumn<Contrato, Date> colDataFim;
    @FXML private TableColumn<Contrato, String> colStatusContrato;

    private ContratoDAO contratoDAO;

    @FXML
    public void initialize() {
        contratoDAO = new ContratoDAO();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colImovel.setCellValueFactory(new PropertyValueFactory<>("imovel"));
        colValorAluguel.setCellValueFactory(new PropertyValueFactory<>("valorAluguel"));
        colDataInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        colDataFim.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
        colStatusContrato.setCellValueFactory(new PropertyValueFactory<>("statusContrato"));
        carregarContratosAVencer();
    }

    private void carregarContratosAVencer() {
        List<Contrato> contratos = contratoDAO.listarContratosAVencer(30);
        ObservableList<Contrato> observableList = FXCollections.observableArrayList(contratos);
        tableViewContratos.setItems(observableList);
    }

    @FXML
    private void handleAtualizarContrato(ActionEvent event) {
        Contrato contratoSelecionado = tableViewContratos.getSelectionModel().getSelectedItem();
        if (contratoSelecionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/univille/poo/view/CadastrarContrato.fxml"));
                Parent root = loader.load();
                CadastrarContratoController controller = loader.getController();
                controller.setContratoParaEdicao(contratoSelecionado);
                Stage stage = new Stage();
                stage.setTitle("Atualizar Contrato");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                carregarContratosAVencer();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar a tela de atualização.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Nenhum Contrato Selecionado", "Por favor, selecione um contrato para atualizar.");
        }
    }

    @FXML
    private void handleDeletarContrato(ActionEvent event) {
        Contrato contratoSelecionado = tableViewContratos.getSelectionModel().getSelectedItem();
        if (contratoSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText("Excluir Contrato");

            // <<< CORREÇÃO AQUI >>>
            // Corrigido de getNome() para getNomeCompleto()
            alert.setContentText("Tem certeza que deseja excluir o contrato selecionado?\nCliente: " + contratoSelecionado.getCliente().getNomeCompleto() + "\nImóvel: " + contratoSelecionado.getImovel().getEndereco());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (contratoDAO.deletar(contratoSelecionado.getId())) {
                    showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Contrato excluído com sucesso!");
                    carregarContratosAVencer();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao excluir contrato.");
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Nenhum Contrato Selecionado", "Por favor, selecione um contrato para deletar.");
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
