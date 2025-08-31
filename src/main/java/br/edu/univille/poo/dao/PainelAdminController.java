package br.edu.univille.poo.dao;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.Optional;

/**
 * Controller para a tela do Painel de Administrador (PainelAdmin.fxml).
 * Gerencia as tabelas e ações de CRUD para Clientes, Imóveis e Contratos,
 * com funcionalidade de edição em linha.
 */
public class PainelAdminController {

    // --- DAOs para acesso aos dados ---
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ImovelDAO imovelDAO = new ImovelDAO();
    private final ContratoDAO contratoDAO = new ContratoDAO();

    // --- Componentes da Aba Clientes ---
    @FXML private TableView<Cliente> tabelaClientes;
    @FXML private TableColumn<Cliente, Long> colunaClienteId;
    @FXML private TableColumn<Cliente, String> colunaClienteNome;
    @FXML private TableColumn<Cliente, String> colunaClienteCPF;
    @FXML private TableColumn<Cliente, String> colunaClienteTelefone;

    // --- Componentes da Aba Imóveis ---
    @FXML private TableView<Imovel> tabelaImoveis;
    @FXML private TableColumn<Imovel, Long> colunaImovelId;
    @FXML private TableColumn<Imovel, String> colunaImovelEndereco;
    @FXML private TableColumn<Imovel, String> colunaImovelBairro;
    @FXML private TableColumn<Imovel, String> colunaImovelTipo;
    @FXML private TableColumn<Imovel, String> colunaImovelStatus;

    // --- Componentes da Aba Contratos ---
    @FXML private TableView<Contrato> tabelaContratos;
    @FXML private TableColumn<Contrato, Long> colunaContratoId;
    @FXML private TableColumn<Contrato, String> colunaContratoCliente;
    @FXML private TableColumn<Contrato, String> colunaContratoImovel;
    @FXML private TableColumn<Contrato, String> colunaContratoInicio;
    @FXML private TableColumn<Contrato, String> colunaContratoFim;
    @FXML private TableColumn<Contrato, String> colunaContratoStatus;

    @FXML
    public void initialize() {
        configurarTabelasEditaveis();
        carregarDados();
    }

    /**
     * Configura as colunas de todas as tabelas, habilitando a edição em linha
     * para Clientes e Imóveis.
     */
    private void configurarTabelasEditaveis() {
        // Habilita a edição nas tabelas
        tabelaClientes.setEditable(true);
        tabelaImoveis.setEditable(true);

        // --- Configuração da Tabela de Clientes (com edição) ---
        colunaClienteId.setCellValueFactory(new PropertyValueFactory<>("id"));

        colunaClienteNome.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
        colunaClienteNome.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaClienteNome.setOnEditCommit(event -> {
            Cliente cliente = event.getRowValue();
            cliente.setNomeCompleto(event.getNewValue());
            atualizarClienteNoBanco(cliente);
        });

        colunaClienteCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaClienteCPF.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaClienteCPF.setOnEditCommit(event -> {
            Cliente cliente = event.getRowValue();
            cliente.setCpf(event.getNewValue());
            atualizarClienteNoBanco(cliente);
        });

        colunaClienteTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colunaClienteTelefone.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaClienteTelefone.setOnEditCommit(event -> {
            Cliente cliente = event.getRowValue();
            cliente.setTelefone(event.getNewValue());
            atualizarClienteNoBanco(cliente);
        });

        // --- Configuração da Tabela de Imóveis (com edição) ---
        colunaImovelId.setCellValueFactory(new PropertyValueFactory<>("id"));

        colunaImovelEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colunaImovelEndereco.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaImovelEndereco.setOnEditCommit(event -> {
            Imovel imovel = event.getRowValue();
            imovel.setEndereco(event.getNewValue());
            atualizarImovelNoBanco(imovel);
        });

        colunaImovelBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        colunaImovelBairro.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaImovelBairro.setOnEditCommit(event -> {
            Imovel imovel = event.getRowValue();
            imovel.setBairro(event.getNewValue());
            atualizarImovelNoBanco(imovel);
        });

        colunaImovelTipo.setCellValueFactory(new PropertyValueFactory<>("imovel"));
        colunaImovelTipo.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaImovelTipo.setOnEditCommit(event -> {
            Imovel imovel = event.getRowValue();
            imovel.setTipoImovel(event.getNewValue());
            atualizarImovelNoBanco(imovel);
        });

        // Para o Status, usamos uma ComboBox para garantir valores válidos
        colunaImovelStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colunaImovelStatus.setCellFactory(ComboBoxTableCell.forTableColumn("Disponível", "Alugado", "Manutenção"));
        colunaImovelStatus.setOnEditCommit(event -> {
            Imovel imovel = event.getRowValue();
            imovel.setStatus(event.getNewValue());
            atualizarImovelNoBanco(imovel);
        });


        // --- Configuração da Tabela de Contratos (não editável por enquanto) ---
        colunaContratoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaContratoStatus.setCellValueFactory(new PropertyValueFactory<>("statusContrato"));
        colunaContratoInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        colunaContratoFim.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
        colunaContratoCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNomeCompleto()));
        colunaContratoImovel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImovel().getEndereco()));
    }

    /**
     * Busca os dados nos DAOs e popula as tabelas.
     */
    private void carregarDados() {
        tabelaClientes.setItems(FXCollections.observableArrayList(clienteDAO.obterTodos()));
        tabelaImoveis.setItems(FXCollections.observableArrayList(imovelDAO.obterTodos()));
        tabelaContratos.setItems(FXCollections.observableArrayList(contratoDAO.obterTodos()));
    }

    // --- MÉTODOS DE ATUALIZAÇÃO NO BANCO ---

    private void atualizarClienteNoBanco(Cliente cliente) {
        boolean sucesso = clienteDAO.atualizar(cliente);
        if (!sucesso) {
            exibirAlerta("Erro de Atualização", "Não foi possível salvar as alterações do cliente.");
            carregarDados(); // Reverte a mudança visual na tabela
        }
    }

    private void atualizarImovelNoBanco(Imovel imovel) {
        boolean sucesso = imovelDAO.atualizar(imovel);
        if (!sucesso) {
            exibirAlerta("Erro de Atualização", "Não foi possível salvar as alterações do imóvel.");
            carregarDados(); // Reverte a mudança visual na tabela
        }
    }

    // --- MÉTODOS DE AÇÃO PARA OS BOTÕES ---

    @FXML
    private void deletarClienteSelecionado(ActionEvent event) {
        Cliente clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado == null) {
            exibirAlerta("Seleção Necessária", "Por favor, selecione um cliente para deletar.");
            return;
        }
        if (confirmarAcao("Deletar Cliente", "Tem certeza que deseja deletar o cliente '" + clienteSelecionado.getNomeCompleto() + "'?")) {
            if (!clienteDAO.deletarPorId(clienteSelecionado.getId())) {
                exibirAlerta("Erro", "Não foi possível deletar o cliente. Verifique se ele não possui contratos ativos.");
            }
            carregarDados();
        }
    }

    @FXML
    private void deletarImovelSelecionado(ActionEvent event) {
        Imovel imovelSelecionado = tabelaImoveis.getSelectionModel().getSelectedItem();
        if (imovelSelecionado == null) {
            exibirAlerta("Seleção Necessária", "Por favor, selecione um imóvel para deletar.");
            return;
        }
        if (confirmarAcao("Deletar Imóvel", "Tem certeza que deseja deletar o imóvel no endereço '" + imovelSelecionado.getEndereco() + "'?")) {
            if (!imovelDAO.deletarPorId(imovelSelecionado.getId())) {
                exibirAlerta("Erro", "Não foi possível deletar o imóvel. Verifique se ele não está vinculado a contratos.");
            }
            carregarDados();
        }
    }

    @FXML
    private void deletarContratoSelecionado(ActionEvent event) {
        Contrato contratoSelecionado = tabelaContratos.getSelectionModel().getSelectedItem();
        if (contratoSelecionado == null) {
            exibirAlerta("Seleção Necessária", "Por favor, selecione um contrato para deletar.");
            return;
        }
        if (confirmarAcao("Deletar Contrato", "Tem certeza que deseja deletar o contrato do cliente '" + contratoSelecionado.getCliente().getNomeCompleto() + "'?")) {
            if (!contratoDAO.deletarPorId(contratoSelecionado.getId())) {
                exibirAlerta("Erro", "Ocorreu um erro ao deletar o contrato.");
            }
            carregarDados();
        }
    }

    // Os botões de edição agora são informativos, pois a edição é direta na tabela.
    @FXML private void editarClienteSelecionado(ActionEvent event) {
        exibirAlerta("Edição Direta", "Para editar, dê um duplo clique na célula desejada na tabela de clientes.");
    }
    @FXML private void editarImovelSelecionado(ActionEvent event) {
        exibirAlerta("Edição Direta", "Para editar, dê um duplo clique na célula desejada na tabela de imóveis.");
    }
    @FXML private void editarContratoSelecionado(ActionEvent event) {
        exibirAlerta("Funcionalidade Indisponível", "A edição de contratos não está implementada nesta versão.");
    }

    // --- MÉTODOS AUXILIARES ---

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private boolean confirmarAcao(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }
}
