package br.edu.univille.poo.controller;

import br.edu.univille.poo.dao.ImovelDAO;
import br.edu.univille.poo.model.Imovel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.math.BigDecimal;
import java.util.List;

public class ListarImoveisController {

    @FXML private TableView<Imovel> tableViewImoveis;
    @FXML private TableColumn<Imovel, Long> colId;
    @FXML private TableColumn<Imovel, String> colEndereco;
    @FXML private TableColumn<Imovel, String> colTipo;
    @FXML private TableColumn<Imovel, BigDecimal> colArea;
    @FXML private TableColumn<Imovel, String> colStatus;

    private final ImovelDAO imovelDAO = new ImovelDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoImovel"));
        colArea.setCellValueFactory(new PropertyValueFactory<>("areaM2"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        carregarImoveis();
    }

    private void carregarImoveis() {
        List<Imovel> imoveis = imovelDAO.listarTodos();
        tableViewImoveis.setItems(FXCollections.observableArrayList(imoveis));
    }
}
