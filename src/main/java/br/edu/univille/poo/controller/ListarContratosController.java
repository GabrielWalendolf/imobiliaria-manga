package br.edu.univille.poo.controller;

import br.edu.univille.poo.dao.ContratoDAO;
import br.edu.univille.poo.model.Contrato;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.math.BigDecimal;
import java.sql.Date;

public class ListarContratosController {

    @FXML private TableView<Contrato> tableViewContratos;
    @FXML private TableColumn<Contrato, Long> colId;
    @FXML private TableColumn<Contrato, String> colCliente;
    @FXML private TableColumn<Contrato, String> colImovel;
    @FXML private TableColumn<Contrato, BigDecimal> colValorAluguel;
    @FXML private TableColumn<Contrato, Date> colDataInicio;
    @FXML private TableColumn<Contrato, Date> colDataFim;
    @FXML private TableColumn<Contrato, String> colStatusContrato;

    private final ContratoDAO contratoDAO = new ContratoDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colImovel.setCellValueFactory(new PropertyValueFactory<>("imovel"));
        colValorAluguel.setCellValueFactory(new PropertyValueFactory<>("valorAluguel"));
        colDataInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        colDataFim.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
        colStatusContrato.setCellValueFactory(new PropertyValueFactory<>("statusContrato"));
        carregarContratos();
    }

    private void carregarContratos() {
        // Corrigido de obterTodosAtivos() para listarAtivos()
        tableViewContratos.setItems(FXCollections.observableArrayList(contratoDAO.listarAtivos()));
    }
}
