package br.edu.univille.poo.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ListarContratosController implements Initializable {

    @FXML
    private TableView<Contrato> tabelaContratos;

    @FXML
    private TableColumn<Contrato, Integer> colunaId;
    @FXML
    private TableColumn<Contrato, Integer> colunaIdCliente;
    @FXML
    private TableColumn<Contrato, Integer> colunaIdImovel;
    @FXML
    private TableColumn<Contrato, LocalDate> colunaDataInicio;
    @FXML
    private TableColumn<Contrato, LocalDate> colunaDataFim;
    @FXML
    private TableColumn<Contrato, Double> colunaValor;
    @FXML
    private TableColumn<Contrato, String> colunaStatus;

    private final ContratoDAO contratoDAO = new ContratoDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configura as colunas para buscarem os dados da classe Contrato
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colunaIdImovel.setCellValueFactory(new PropertyValueFactory<>("idImovel"));
        colunaDataInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        colunaDataFim.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("valorAluguel"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        carregarContratos();
    }

    private void carregarContratos() {
        List<Contrato> contratos = contratoDAO.obterTodosAtivos();
        ObservableList<Contrato> contratosObservaveis = FXCollections.observableArrayList(contratos);
        tabelaContratos.setItems(contratosObservaveis);
    }
}
