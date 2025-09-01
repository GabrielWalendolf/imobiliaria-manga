package br.edu.univille.poo.controller;

import br.edu.univille.poo.dao.ImovelDAO;
import br.edu.univille.poo.model.Imovel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

public class CadastrarImovelController {

    @FXML private TextField campoEndereco;
    @FXML private TextField campoBairro;
    @FXML private TextField campoCidade;
    @FXML private TextField campoCep;
    @FXML private ComboBox<String> comboBoxTipoImovel;
    @FXML private TextField campoAreaM2;
    @FXML private TextField campoQuartos;
    @FXML private TextField campoBanheiros;
    @FXML private TextField campoVagasGaragem;
    @FXML private ComboBox<String> comboBoxStatus;

    private final ImovelDAO imovelDAO = new ImovelDAO();
    private Imovel imovelParaEdicao;

    @FXML
    public void initialize() {
        comboBoxTipoImovel.getItems().addAll("Casa", "Apartamento", "Terreno", "Comercial");
        comboBoxStatus.getItems().addAll("Disponível", "Alugado", "Vendido");
    }

    public void setImovelParaEdicao(Imovel imovel) {
        this.imovelParaEdicao = imovel;
        if (imovel != null) {
            campoEndereco.setText(imovel.getEndereco());
            campoBairro.setText(imovel.getBairro());
            campoCidade.setText(imovel.getCidade());
            campoCep.setText(imovel.getCep());
            comboBoxTipoImovel.setValue(imovel.getTipoImovel());
            campoAreaM2.setText(imovel.getAreaM2().toPlainString());
            campoQuartos.setText(String.valueOf(imovel.getQuartos()));
            campoBanheiros.setText(String.valueOf(imovel.getBanheiros()));
            campoVagasGaragem.setText(String.valueOf(imovel.getVagasGaragem()));
            comboBoxStatus.setValue(imovel.getStatus());
        }
    }

    @FXML
    private void salvarImovel() {
        try {
            String endereco = campoEndereco.getText().trim();
            if (endereco.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erro de Validação", "O campo Endereço é obrigatório.");
                return;
            }

            BigDecimal area = new BigDecimal(campoAreaM2.getText().trim());
            int quartos = Integer.parseInt(campoQuartos.getText().trim());
            int banheiros = Integer.parseInt(campoBanheiros.getText().trim());
            int vagas = Integer.parseInt(campoVagasGaragem.getText().trim());

            boolean sucesso;
            String msgSucesso;

            if (imovelParaEdicao == null) {
                Imovel novoImovel = new Imovel();
                novoImovel.setEndereco(endereco);
                novoImovel.setBairro(campoBairro.getText().trim());
                novoImovel.setCidade(campoCidade.getText().trim());
                novoImovel.setCep(campoCep.getText().trim());
                novoImovel.setTipoImovel(comboBoxTipoImovel.getValue());
                novoImovel.setAreaM2(area);
                novoImovel.setQuartos(quartos);
                novoImovel.setBanheiros(banheiros);
                novoImovel.setVagasGaragem(vagas);
                novoImovel.setStatus(comboBoxStatus.getValue());
                novoImovel.setDataCadastro(Date.valueOf(LocalDate.now()));
                sucesso = imovelDAO.cadastrar(novoImovel);
                msgSucesso = "Imóvel cadastrado com sucesso!";
            } else {
                imovelParaEdicao.setEndereco(endereco);
                imovelParaEdicao.setBairro(campoBairro.getText().trim());
                imovelParaEdicao.setCidade(campoCidade.getText().trim());
                imovelParaEdicao.setCep(campoCep.getText().trim());
                imovelParaEdicao.setTipoImovel(comboBoxTipoImovel.getValue());
                imovelParaEdicao.setAreaM2(area);
                imovelParaEdicao.setQuartos(quartos);
                imovelParaEdicao.setBanheiros(banheiros);
                imovelParaEdicao.setVagasGaragem(vagas);
                imovelParaEdicao.setStatus(comboBoxStatus.getValue());
                sucesso = imovelDAO.atualizar(imovelParaEdicao);
                msgSucesso = "Imóvel atualizado com sucesso!";
            }

            if (sucesso) {
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", msgSucesso);
                fecharJanela();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erro", "Ocorreu um erro ao salvar o imóvel.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Formato", "Campos de número (Área, Quartos, etc.) devem ser válidos.");
        }
    }

    @FXML
    private void cancelar() {
        fecharJanela();
    }

    private void fecharJanela() {
        Stage stage = (Stage) campoEndereco.getScene().getWindow();
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
