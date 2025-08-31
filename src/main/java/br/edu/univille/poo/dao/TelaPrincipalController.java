package br.edu.univille.poo.dao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

/**
 * Controller para a tela principal da aplicação (TelaPrincipal.fxml).
 * Gerencia as ações de todos os botões do menu principal.
 */
public class TelaPrincipalController {

    // Os @FXML para os botões permanecem os mesmos.
    // Garanta que todos os botões do seu FXML tenham um @FXML correspondente aqui se você precisar acessá-los.

    // --- MÉTODOS DE AÇÃO (onAction) ---

    @FXML
    private void abrirTelaCadastroCliente(ActionEvent event) {
        abrirNovaJanela("CadastrarNovoCliente.fxml", "Cadastrar Novo Cliente");
    }

    @FXML
    private void abrirTelaCadastroImovel(ActionEvent event) {
        abrirNovaJanela("CadastrarNovoImovel.fxml", "Cadastrar Novo Imóvel");
    }

    @FXML
    private void abrirTelaCadastroContrato(ActionEvent event) {
        abrirNovaJanela("CadastrarNovoContrato.fxml", "Cadastrar Novo Contrato");
    }

    // Dentro da classe TelaPrincipalController.java

    @FXML
    private void abrirPainelAdmin(ActionEvent event) {
        try {
            // 1. Carrega a tela de login
            String caminhoLogin = "/br/edu/univille/poo/dao/LoginAdmin.fxml";
            FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource(caminhoLogin));
            Parent rootLogin = loaderLogin.load();

            // 2. Cria e exibe a janela de login em modo de espera
            Stage stageLogin = new Stage();
            stageLogin.setTitle("Autenticação de Administrador");
            stageLogin.setScene(new Scene(rootLogin));
            stageLogin.initModality(Modality.APPLICATION_MODAL);

            // O showAndWait() pausa a execução aqui até que a janela de login seja fechada
            stageLogin.showAndWait();

            // 3. Após a janela de login fechar, pega o controller dela
            LoginAdminController loginController = loaderLogin.getController();

            // 4. Verifica se o login foi bem-sucedido
            if (loginController.isLoginBemSucedido()) {
                // Se o login foi um sucesso, abre o painel de administrador
                System.out.println("INFO: Login bem-sucedido. Abrindo painel de administrador...");
                abrirNovaJanela("PainelAdmin.fxml", "Painel de Administrador");
            } else {
                // Se o login falhou ou foi cancelado, apenas informa no console
                System.out.println("INFO: Acesso ao painel de administrador cancelado.");
            }

        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela de login ou o painel de administrador.");
            e.printStackTrace();
        }
    }


    // ===== MÉTODOS QUE ESTAVAM FALTANDO (A CORREÇÃO ESTÁ AQUI) =====
    @FXML
    private void listarImoveisDisponiveis(ActionEvent event) {
        System.out.println("Ação: Listar imóveis disponíveis...");
        // Futuramente, abrirá uma tela de listagem.
        // abrirNovaJanela("ListarImoveis.fxml", "Imóveis Disponíveis");
    }

    @FXML
    private void listarContratosAtivos(ActionEvent event) {
        System.out.println("Ação: Listar contratos ativos...");
    }

    @FXML
    private void listarClientesComMaisContratos(ActionEvent event) {
        System.out.println("Ação: Listar clientes com mais contratos...");
    }

    @FXML
    private void listarContratosExpirando(ActionEvent event) {
        System.out.println("Ação: Listar contratos expirando...");
    }
    // ===============================================================


    // --- Método Auxiliar para Abrir Novas Janelas ---
    private void abrirNovaJanela(String nomeArquivoFxml, String tituloJanela) {
        try {
            String caminhoFxml = "/br/edu/univille/poo/dao/" + nomeArquivoFxml;
            URL resource = getClass().getResource(caminhoFxml);

            if (resource == null) {
                System.err.println("ERRO: Não foi possível encontrar o arquivo FXML: " + caminhoFxml);
                return;
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();
            Stage novaJanela = new Stage();
            novaJanela.setTitle(tituloJanela);
            novaJanela.setScene(new Scene(root));
            novaJanela.initModality(Modality.APPLICATION_MODAL);
            novaJanela.showAndWait();

        } catch (IOException e) {
            System.err.println("Erro ao abrir a nova janela: " + nomeArquivoFxml);
            e.printStackTrace();
        }
    }
}
