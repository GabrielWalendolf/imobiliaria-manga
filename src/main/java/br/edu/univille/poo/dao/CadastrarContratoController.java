package br.edu.univille.poo.dao;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

// Verifique se o nome da classe está correto aqui
public class CadastrarContratoController {


    @FXML private ComboBox<Cliente> comboCliente;
    @FXML private ComboBox<Imovel> comboImovel;
    @FXML private TextField campoValorAluguel;
    @FXML private DatePicker campoDataInicio;
    @FXML private DatePicker campoDataFim;
    @FXML private Button botaoSalvar;


    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ImovelDAO imovelDAO = new ImovelDAO();
    private final ContratoDAO contratoDAO = new ContratoDAO();

    @FXML
    public void initialize() {
        comboCliente.setItems(FXCollections.observableArrayList(clienteDAO.obterTodos()));
        comboImovel.setItems(FXCollections.observableArrayList(imovelDAO.obterTodos()));
        // Dentro do método initialize() do CadastrarContratoController

        // --- Configuração da ComboBox de Clientes ---
        comboCliente.setItems(FXCollections.observableArrayList(clienteDAO.obterTodos()));
        StringConverter<Cliente> clienteConverter = new StringConverter<>() {
            @Override
            public String toString(Cliente cliente) {
                return (cliente == null) ? null : cliente.getNomeCompleto() + " | " + cliente.getCpf();
            }

            @Override
            public Cliente fromString(String string) {
                return null;
            }
        };
        comboCliente.setConverter(clienteConverter);


        // --- Configuração da ComboBox de Imóveis ---
        comboImovel.setItems(FXCollections.observableArrayList(imovelDAO.obterTodos()));
        StringConverter<Imovel> imovelConverter = new StringConverter<>() {
            @Override
            public String toString(Imovel imovel) {
                return (imovel == null) ? null :
                        imovel.getEndereco() + " | " + imovel.getBairro() + " | " + imovel.getCidade() + " | " + imovel.getCep();
            }

            @Override
            public Imovel fromString(String string) {
                return null;
            }
        };
        comboImovel.setConverter(imovelConverter);
    }

    @FXML
    private void salvarNovoContrato(ActionEvent event) {
        Cliente clienteSelecionado = comboCliente.getValue();
        Imovel imovelSelecionado = comboImovel.getValue();
        LocalDate dataInicio = campoDataInicio.getValue();
        LocalDate dataFim = campoDataFim.getValue();

        if (clienteSelecionado == null || imovelSelecionado == null || dataInicio == null || dataFim == null) {
            exibirAlerta("Erro de Validação", "Todos os campos são obrigatórios.");
            return;
        }

        try {
            // CORREÇÃO PRINCIPAL AQUI: Usando a classe "Contrato" (singular)
            Contrato novoContrato = new Contrato();
            novoContrato.setCliente(clienteSelecionado);
            novoContrato.setImovel(imovelSelecionado);
            novoContrato.setValorAluguel(new BigDecimal(campoValorAluguel.getText()));
            novoContrato.setDataInicio(Date.valueOf(dataInicio));
            novoContrato.setDataFim(Date.valueOf(dataFim));
            novoContrato.setStatusContrato("Ativo");

            boolean sucesso = contratoDAO.cadastrar(novoContrato);

            if (sucesso) {
                exibirAlerta("Sucesso", "Contrato cadastrado com sucesso!");
                fecharJanela();
            } else {
                exibirAlerta("Erro no Banco de Dados", "Ocorreu um erro ao salvar o contrato.");
            }
        } catch (NumberFormatException e) {
            exibirAlerta("Erro de Formato", "O valor do aluguel deve ser um número válido.");
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
