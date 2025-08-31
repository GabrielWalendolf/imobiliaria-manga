package br.edu.univille.poo.dao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastrarImovelController {

    @FXML private TextField campoCEP;
    @FXML private TextField campoEndereco;
    @FXML private TextField campoBairro;
    @FXML private TextField campoCidade;
    @FXML private TextField campoTipo;
    @FXML private TextField campoArea;
    @FXML private TextField campoQuartos;
    @FXML private TextField campoBanheiros;
    @FXML private TextField campoVagas;
    @FXML private Button botaoSalvar;

    private final ImovelDAO imovelDAO = new ImovelDAO();

    @FXML
    private void salvarNovoImovel(ActionEvent event) {
        String endereco = campoEndereco.getText().trim();
        String bairro = campoBairro.getText().trim();
        String cidade = campoCidade.getText().trim();
        String cep = campoCEP.getText().trim();
        String tipo = campoTipo.getText().trim();

        if (endereco.isEmpty() || bairro.isEmpty() || cidade.isEmpty() || cep.isEmpty() || tipo.isEmpty()) {
            exibirAlerta("Erro de Validação", "Os campos 'Endereço', 'Tipo', 'Bairro', 'Cidade', 'CEP' são " +
                    "obrigatórios.");
            return;
        }

        try {
            Imovel novoImovel = new Imovel();
            novoImovel.setEndereco(endereco);
            novoImovel.setCidade(cidade);
            novoImovel.setCep(cep);
            novoImovel.setBairro(bairro);
            novoImovel.setTipoImovel(tipo);
            // Validação para campos numéricos
            novoImovel.setAreaM2(Double.parseDouble(campoArea.getText()));
            novoImovel.setQuartos(Integer.parseInt(campoQuartos.getText()));
            novoImovel.setBanheiros(Integer.parseInt(campoBanheiros.getText()));
            novoImovel.setVagasGaragem(Integer.parseInt(campoVagas.getText()));
            novoImovel.setStatus("Disponível"); // Status padrão

            boolean sucesso = imovelDAO.cadastrar(novoImovel);

            if (sucesso) {
                exibirAlerta("Sucesso", "Imóvel cadastrado com sucesso!");
                fecharJanela();
            } else {
                exibirAlerta("Erro no Banco de Dados", "Ocorreu um erro ao salvar o imóvel.");
            }
        } catch (NumberFormatException e) {
            exibirAlerta("Erro de Formato", "Os campos de Área, Quartos, Banheiros e Vagas devem conter apenas números válidos.");
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
