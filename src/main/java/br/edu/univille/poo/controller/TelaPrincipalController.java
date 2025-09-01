package br.edu.univille.poo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaPrincipalController {

    @FXML
    private void abrirTelaCadastroCliente(ActionEvent event) {
        abrirNovaJanela("CadastrarCliente.fxml", "Cadastrar Novo Cliente");
    }

    @FXML
    private void abrirTelaCadastroImovel(ActionEvent event) {
        abrirNovaJanela("CadastrarImovel.fxml", "Cadastrar Novo Imóvel");
    }

    @FXML
    private void abrirTelaCadastroContrato(ActionEvent event) {
        abrirNovaJanela("CadastrarContrato.fxml", "Cadastrar Novo Contrato");
    }

    @FXML
    private void abrirTelaGerenciarImoveis(ActionEvent event) {
        abrirNovaJanela("GerenciarImoveis.fxml", "Gerenciar Imóveis");
    }

    @FXML
    private void abrirTelaGerenciarContratos(ActionEvent event) {
        abrirNovaJanela("GerenciarContratos.fxml", "Gerenciar Contratos");
    }

    @FXML
    private void abrirTelaGerenciarClientes(ActionEvent event) {
        abrirNovaJanela("GerenciarClientes.fxml", "Gerenciar Clientes");
    }

    @FXML
    private void abrirTelaGerenciarContratosAVencer(ActionEvent event) {
        abrirNovaJanela("GerenciarContratosAVencer.fxml", "Gerenciar Contratos a Vencer");
    }

    @FXML
    private void abrirPainelAdmin(ActionEvent event) {
        abrirNovaJanela("LoginAdmin.fxml", "Login do Administrador");
    }

    private void abrirNovaJanela(String fxml, String titulo) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/br/edu/univille/poo/" + fxml));
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
