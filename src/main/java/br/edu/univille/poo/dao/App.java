package br.edu.univille.poo.dao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL; // Importe a classe URL

public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {
            System.out.println("INFO: Tentando carregar TelaPrincipal.fxml...");

            String fxmlFile = "/br/edu/univille/poo/dao/TelaPrincipal.fxml";

            // CORREÇÃO AQUI: Verificamos o recurso ANTES de criar o FXMLLoader
            URL resource = getClass().getResource(fxmlFile);

            if (resource == null) {
                // Se o recurso for nulo, o arquivo FXML não foi encontrado.
                System.err.println("======================================================================");
                System.err.println("ERRO CRÍTICO: Não foi possível encontrar o arquivo FXML!");
                System.err.println("Caminho procurado: " + fxmlFile);
                System.err.println("Verifique se a estrutura de pastas em 'src/main/resources' está correta.");
                System.err.println("A estrutura deve ser: src/main/resources/br/edu/univille/poo/dao/");
                System.err.println("======================================================================");
                return; // Interrompe a execução
            }

            // Agora que sabemos que o recurso existe, podemos carregar com segurança
            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();
            System.out.println("INFO: FXML carregado com sucesso.");

            Scene scene = new Scene(root);
            stage.setTitle("Imobiliária Manga");
            stage.setScene(scene);
            stage.show();
            System.out.println("INFO: Aplicação iniciada com sucesso!");

        } catch (Exception e) {
            // Se qualquer outra exceção ocorrer, ela será capturada aqui.
            System.err.println("======================================================================");
            System.err.println("ERRO CRÍTICO DURANTE A INICIALIZAÇÃO DO JAVAFX:");
            System.err.println("Causa provável: Inconsistência entre o FXML e o Controller.");
            System.err.println("Verifique se os fx:id e os métodos onAction no FXML correspondem ao Controller.");
            System.err.println("--- MENSAGEM DA EXCEÇÃO ---");
            e.printStackTrace();
            System.err.println("======================================================================");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
