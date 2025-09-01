package br.edu.univille.poo.controller;

import br.edu.univille.poo.dao.ClienteDAO;
import br.edu.univille.poo.dao.ContratoDAO;
import br.edu.univille.poo.dao.ImovelDAO;
import br.edu.univille.poo.model.Cliente;
import br.edu.univille.poo.model.Contrato;
import br.edu.univille.poo.model.Imovel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import java.util.Optional;

public class PainelAdminController {

    @FXML private ListView<Cliente> listViewClientes;
    @FXML private ListView<Contrato> listViewContratos;
    @FXML private ListView<Imovel> listViewImoveis;

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ContratoDAO contratoDAO = new ContratoDAO();
    private final ImovelDAO imovelDAO = new ImovelDAO();

    @FXML
    public void initialize() {
        carregarListas();
    }

    private void carregarListas() {
        // Corrigido de obterTodos() para listarTodos()
        listViewClientes.setItems(FXCollections.observableArrayList(clienteDAO.listarTodos()));
        listViewContratos.setItems(FXCollections.observableArrayList(contratoDAO.listarTodos()));
        listViewImoveis.setItems(FXCollections.observableArrayList(imovelDAO.listarTodos()));
    }

    @FXML
    private void deletarClienteSelecionado() {
        Cliente selecionado = listViewClientes.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            if (confirmarExclusao("Excluir Cliente", "Tem certeza que deseja excluir o cliente " + selecionado.getNomeCompleto() + "?")) {
                // Corrigido de deletarPorId() para deletar()
                if (clienteDAO.deletar(selecionado.getId())) {
                    showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Cliente excluído.");
                    carregarListas();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erro", "Falha ao excluir cliente.");
                }
            }
        }
    }

    @FXML
    private void deletarContratoSelecionado() {
        Contrato selecionado = listViewContratos.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            if (confirmarExclusao("Excluir Contrato", "Tem certeza que deseja excluir o contrato do cliente " + selecionado.getCliente().getNomeCompleto() + "?")) {
                // Corrigido de deletarPorId() para deletar()
                if (contratoDAO.deletar(selecionado.getId())) {
                    showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Contrato excluído.");
                    carregarListas();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erro", "Falha ao excluir contrato.");
                }
            }
        }
    }

    @FXML
    private void deletarImovelSelecionado() {
        Imovel selecionado = listViewImoveis.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            if (confirmarExclusao("Excluir Imóvel", "Tem certeza que deseja excluir o imóvel no endereço " + selecionado.getEndereco() + "?")) {
                if (imovelDAO.deletar(selecionado.getId())) {
                    showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Imóvel excluído.");
                    carregarListas();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erro", "Falha ao excluir imóvel.");
                }
            }
        }
    }

    private boolean confirmarExclusao(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
