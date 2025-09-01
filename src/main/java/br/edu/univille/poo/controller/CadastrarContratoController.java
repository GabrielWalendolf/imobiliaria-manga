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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.math.BigDecimal;
import java.sql.Date;

// Este arquivo agora contém APENAS esta classe.
public class CadastrarContratoController {

    @FXML private ComboBox<Cliente> comboBoxCliente;
    @FXML private ComboBox<Imovel> comboBoxImovel;
    @FXML private TextField campoValorAluguel;
    @FXML private DatePicker datePickerInicio;
    @FXML private DatePicker datePickerFim;
    @FXML private ComboBox<String> comboBoxStatus;

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ImovelDAO imovelDAO = new ImovelDAO();
    private final ContratoDAO contratoDAO = new ContratoDAO();
    private Contrato contratoParaEdicao;

    @FXML
    public void initialize() {
        comboBoxCliente.setItems(FXCollections.observableArrayList(clienteDAO.listarTodos()));
        comboBoxImovel.setItems(FXCollections.observableArrayList(imovelDAO.listarTodos()));
        comboBoxStatus.setItems(FXCollections.observableArrayList("Ativo", "Finalizado", "Cancelado"));
    }

    public void setContratoParaEdicao(Contrato contrato) {
        this.contratoParaEdicao = contrato;
        if (contrato != null) {
            comboBoxCliente.setValue(contrato.getCliente());
            comboBoxImovel.setValue(contrato.getImovel());
            campoValorAluguel.setText(contrato.getValorAluguel().toPlainString());
            datePickerInicio.setValue(contrato.getDataInicio().toLocalDate());
            datePickerFim.setValue(contrato.getDataFim().toLocalDate());
            comboBoxStatus.setValue(contrato.getStatusContrato());
        }
    }

    @FXML
    private void salvarContrato() {
        try {
            Cliente cliente = comboBoxCliente.getValue();
            Imovel imovel = comboBoxImovel.getValue();
            BigDecimal valor = new BigDecimal(campoValorAluguel.getText());
            Date dataInicio = Date.valueOf(datePickerInicio.getValue());
            Date dataFim = Date.valueOf(datePickerFim.getValue());
            String status = comboBoxStatus.getValue();

            if (cliente == null || imovel == null || status == null) {
                showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Todos os campos são obrigatórios.");
                return;
            }

            boolean sucesso;
            String msgSucesso;

            if (contratoParaEdicao == null) {
                Contrato novoContrato = new Contrato();
                novoContrato.setCliente(cliente);
                novoContrato.setImovel(imovel);
                novoContrato.setValorAluguel(valor);
                novoContrato.setDataInicio(dataInicio);
                novoContrato.setDataFim(dataFim);
                novoContrato.setStatusContrato(status);
                sucesso = contratoDAO.cadastrar(novoContrato);
                msgSucesso = "Contrato cadastrado com sucesso!";
            } else {
                contratoParaEdicao.setCliente(cliente);
                contratoParaEdicao.setImovel(imovel);
                contratoParaEdicao.setValorAluguel(valor);
                contratoParaEdicao.setDataInicio(dataInicio);
                contratoParaEdicao.setDataFim(dataFim);
                contratoParaEdicao.setStatusContrato(status);
                sucesso = contratoDAO.atualizar(contratoParaEdicao);
                msgSucesso = "Contrato atualizado com sucesso!";
            }

            if (sucesso) {
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", msgSucesso);
                fecharJanela();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erro", "Ocorreu um erro ao salvar o contrato.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Formato", "Verifique se todos os campos estão preenchidos corretamente.");
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelar() {
        fecharJanela();
    }

    private void fecharJanela() {
        Stage stage = (Stage) comboBoxCliente.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
