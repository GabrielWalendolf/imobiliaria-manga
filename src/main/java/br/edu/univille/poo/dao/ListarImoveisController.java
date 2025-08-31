package br.edu.univille.poo.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListarImoveisController implements Initializable {

    @FXML
    private TableView<Imovel> tabelaImoveis;

    @FXML
    private TableColumn<Imovel, Integer> colunaId;

    @FXML
    private TableColumn<Imovel, String> colunaTipo;

    @FXML
    private TableColumn<Imovel, String> colunaEndereco;

    @FXML
    private TableColumn<Imovel, String> colunaCidade;

    @FXML
    private TableColumn<Imovel, String> colunaStatus;

    private final ImovelDAO imovelDAO = new ImovelDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configura as colunas para buscarem os dados da classe Imovel
        // Os nomes em " " devem corresponder exatamente aos nomes dos campos na classe Imovel
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipoImovel"));
        colunaEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colunaCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        carregarImoveis();
    }

    private void carregarImoveis() {
        // Usa o novo método que criamos no ImovelDAO
        List<Imovel> imoveis = imovelDAO.obterTodos();
        ObservableList<Imovel> imoveisObservaveis = FXCollections.observableArrayList(imoveis);
        tabelaImoveis.setItems(imoveisObservaveis);
    }
}
