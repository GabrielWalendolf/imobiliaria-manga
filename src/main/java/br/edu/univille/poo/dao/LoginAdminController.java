package br.edu.univille.poo.dao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Controller para a tela de login do administrador (LoginAdmin.fxml).
 * Responsável por capturar as credenciais, autenticar o usuário e
 * informar o resultado para a tela que o chamou.
 */
public class LoginAdminController {

    @FXML private TextField campoUsuario;
    @FXML private PasswordField campoSenha;
    @FXML private Button botaoLogin;

    private final AdminDAO adminDAO = new AdminDAO();
    private boolean loginBemSucedido = false;

    /**
     * Este método é chamado quando o botão "Entrar" é clicado.
     * Ele contém a lógica completa de autenticação.
     */
    @FXML
    private void realizarLogin(ActionEvent event) {
        String usuario = campoUsuario.getText().trim();
        String senha = campoSenha.getText().trim();

        if (usuario.isEmpty() || senha.isEmpty()) {
            exibirAlerta("Erro de Validação", "Usuário e senha são obrigatórios.");
            return;
        }

        // 1. Usa o DAO para buscar o administrador pelo nome de usuário.
        Optional<Admin> adminOptional = adminDAO.obterPorUsuario(usuario);

        // 2. Verifica se o administrador foi encontrado no banco.
        if (adminOptional.isPresent()) {
            // Se encontrou, pega o objeto Admin.
            Admin adminDoBanco = adminOptional.get();

            // 3. Gera o hash da senha que o usuário DIGITOU.
            String hashDaSenhaDigitada = GeradorDeHash.gerarHash(senha);

            // 4. Compara o hash da senha digitada com o hash que veio do BANCO.
            if (hashDaSenhaDigitada.equals(adminDoBanco.getSenhaHash())) {
                // Se os hashes são iguais, o login é um sucesso!
                System.out.println("INFO: Autenticação bem-sucedida para o usuário: " + usuario);
                this.loginBemSucedido = true;
                fecharJanela();
            } else {
                // Se os hashes são diferentes, a senha está incorreta.
                System.out.println("WARN: Tentativa de login falhou (senha incorreta) para o usuário: " + usuario);
                exibirAlerta("Falha no Login", "Usuário ou senha inválidos.");
                this.loginBemSucedido = false;
            }
        } else {
            // Se o usuário nem sequer foi encontrado no banco.
            System.out.println("WARN: Tentativa de login falhou (usuário não encontrado): " + usuario);
            exibirAlerta("Falha no Login", "Usuário ou senha inválidos.");
            this.loginBemSucedido = false;
        }
    }

    /**
     * Este método permite que a tela principal verifique se o login foi um sucesso.
     * É chamado depois que a janela de login é fechada.
     * @return true se o login foi bem-sucedido, false caso contrário.
     */
    public boolean isLoginBemSucedido() {
        return loginBemSucedido;
    }

    /**
     * Exibe um pop-up de alerta para o usuário.
     * @param titulo O título da janela de alerta.
     * @param mensagem A mensagem a ser exibida.
     */
    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * Fecha a janela de login atual.
     */
    private void fecharJanela() {
        Stage stage = (Stage) botaoLogin.getScene().getWindow();
        stage.close();
    }
}
