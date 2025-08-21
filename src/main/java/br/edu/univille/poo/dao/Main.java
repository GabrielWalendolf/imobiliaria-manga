package br.edu.univille.poo.dao;

// Importações necessárias para JLine e funcionalidades
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Constantes para as teclas de navegação
    private static final int TECLA_CIMA = 65;
    private static final int TECLA_BAIXO = 66;
    private static final int TECLA_ENTER = 13;

    // Instâncias dos DAOs para acesso ao banco de dados
    private static final ClienteDAO clienteDAO = new ClienteDAO();
    private static final ImovelDAO imovelDAO = new ImovelDAO();
    private static final ContratoDAO contratoDAO = new ContratoDAO();
    private static final AdminDAO adminDAO = new AdminDAO();
    private static Scanner scanner;

    public static void main(String[] args) throws IOException {
        // O Scanner é usado apenas para entrada de dados dentro das funcionalidades
        Scanner scannerParaEntrada = new Scanner(System.in);
        String[] opcoesMenu = {
                "Cadastrar Novo Imóvel",
                "Cadastrar Novo Cliente",
                "Cadastrar Novo Contrato de Aluguel",
                "Listar Imóveis Disponíveis para Aluguel",
                "Listar Contratos Ativos",
                "Listar Clientes com Mais Contratos",
                "Listar Contratos Expirando nos Próximos 30 Dias",
                "PAINEL DE ADMINISTRADOR (Deletar/Editar)",
                "Sair do Sistema"
        };

        int escolha = -1;
        // O loop principal continua até que a opção "Sair" seja escolhida
        while (escolha != opcoesMenu.length - 1) {
            escolha = exibirMenuInterativo(opcoesMenu, "--- Sistema de Gestão de Imobiliária ---");
            limparTela();

            switch (escolha) {
                case 0: cadastrarImovel(scannerParaEntrada); break;
                case 1: cadastrarCliente(scannerParaEntrada); break;
                case 2: cadastrarContrato(scannerParaEntrada); break;
                case 3: listarImoveisDisponiveis(); break;
                case 4: listarContratosAtivos(); break;
                case 5: listarClientesComMaisContratos(); break;
                case 6: listarContratosExpirando(); break;
                case 7:
                    if (realizarLoginAdmin(scannerParaEntrada)) {
                        menuAcoesAdmin(scannerParaEntrada);
                    }
                    break;
                case 8: break; // Opção "Sair"
            }

            // Pausa para o usuário ver o resultado antes de voltar ao menu
            if (escolha != opcoesMenu.length - 1) {
                pressioneEnterParaContinuar(scannerParaEntrada);
            }
        }
        System.out.println("Saindo do sistema... Até logo!");
        scannerParaEntrada.close();
    }

    /**
     * Exibe um menu interativo genérico usando JLine.
     * @param opcoes As opções a serem exibidas.
     * @param titulo O título do menu.
     * @return O índice da opção selecionada.
     */
    public static int exibirMenuInterativo(String[] opcoes, String titulo) throws IOException {
        Terminal terminal = TerminalBuilder.builder().system(true).build();
        terminal.enterRawMode();
        NonBlockingReader reader = terminal.reader();
        PrintWriter writer = terminal.writer();
        int selecionado = 0;

        while (true) {
            limparTela(writer);
            writer.println(titulo);
            writer.println("Use as setas (▲/▼) para navegar e Enter para selecionar:\n");

            for (int i = 0; i < opcoes.length; i++) {
                if (i == selecionado) {
                    writer.println(" > " + opcoes[i] + " <");
                } else {
                    writer.println("   " + opcoes[i]);
                }
            }
            writer.flush();

            int tecla = reader.read();
            if (tecla == TECLA_CIMA) {
                selecionado = (selecionado - 1 + opcoes.length) % opcoes.length;
            } else if (tecla == TECLA_BAIXO) {
                selecionado = (selecionado + 1) % opcoes.length;
            } else if (tecla == TECLA_ENTER) {
                terminal.close();
                reader.close();
                return selecionado;
            }
        }
    }

    // ==================================================================
    // PAINEL DE ADMINISTRADOR E SUAS AÇÕES
    // ==================================================================

    private static void menuAcoesAdmin(Scanner scanner) throws IOException {
        String[] opcoesAdmin = {
                "Deletar Cliente",
                "Deletar Imóvel",
                "Deletar Contrato",
                "Atualizar Dados de Cliente",
                "Atualizar Dados de Imóvel",
                "Atualizar Dados de Contrato",
                "Voltar ao Menu Principal"
        };

        int escolhaAdmin = -1;
        while (true) {
            escolhaAdmin = exibirMenuInterativo(opcoesAdmin, "--- Painel de Administrador ---");
            limparTela();

            switch (escolhaAdmin) {
                case 0: deletarCliente(scanner); break;
                case 1: deletarImovel(scanner); break;
                case 2: deletarContrato(scanner); break;
                case 3: atualizarCliente(scanner); break;
                case 4: atualizarImovel(scanner); break;
                case 5: atualizarContrato(scanner); break;
                case 6: return; // Voltar
            }

            pressioneEnterParaContinuar(scanner);
        }
    }

    private static boolean realizarLoginAdmin(Scanner scanner) {
        System.out.println("\n--- Autenticação de Administrador Necessária ---");
        System.out.print("Usuário: ");
        String usuario = scanner.nextLine().trim();
        System.out.print("Senha: ");
        String senha = scanner.nextLine().trim();

        if (adminDAO.autenticar(usuario, senha)) {
            System.out.println("\nLogin bem-sucedido! Acesso concedido.");
            return true;
        } else {
            System.out.println("\nUsuário ou senha inválidos. Acesso negado.");
            return false;
        }
    }

    // --- Métodos de Deleção ---
    private static void deletarCliente(Scanner scanner) {
        System.out.println("\n--- Deletar Cliente por ID ---");
        try {
            System.out.print("Digite o ID do CLIENTE a ser deletado: ");
            long id = Long.parseLong(scanner.nextLine());
            if (clienteDAO.deletarPorId(id)) {
                System.out.println("Cliente com ID " + id + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum cliente encontrado com o ID " + id + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Por favor, digite um número.");
        }
    }

    private static void deletarImovel(Scanner scanner) {
        System.out.println("\n--- Deletar Imóvel por ID ---");
        try {
            System.out.print("Digite o ID do IMÓVEL a ser deletado: ");
            long id = Long.parseLong(scanner.nextLine());
            if (imovelDAO.deletarPorId(id)) {
                System.out.println("Imóvel com ID " + id + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum imóvel encontrado com o ID " + id + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Por favor, digite um número.");
        }
    }

    private static void deletarContrato(Scanner scanner) {
        System.out.println("\n--- Deletar Contrato por ID ---");
        try {
            System.out.print("Digite o ID do CONTRATO a ser deletado: ");
            long id = Long.parseLong(scanner.nextLine());
            if (contratoDAO.deletarPorId(id)) {
                System.out.println("Contrato com ID " + id + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum contrato encontrado com o ID " + id + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Por favor, digite um número.");
        }
    }

    // --- Métodos de Atualização ---
    private static void atualizarCliente(Scanner scanner) {
        System.out.println("\n--- Atualizar Cliente por ID ---");
        try {
            System.out.print("Digite o ID do CLIENTE a ser atualizado: ");
            long id = Long.parseLong(scanner.nextLine());
            var clienteOpt = clienteDAO.obterPorId(id);

            if (clienteOpt.isEmpty()) {
                System.out.println("Cliente com ID " + id + " não encontrado.");
                return;
            }

            Cliente cliente = clienteOpt.get();
            System.out.println("Editando cliente: " + cliente.getNomeCompleto());

            System.out.print("Novo Nome Completo (atual: " + cliente.getNomeCompleto() + "): ");
            String nome = scanner.nextLine();
            if (!nome.isBlank()) cliente.setNomeCompleto(nome);

            System.out.print("Novo CPF (atual: " + cliente.getCpf() + "): ");
            String cpf = scanner.nextLine();
            if (!cpf.isBlank()) cliente.setCpf(cpf);

            System.out.print("Novo Telefone (atual: " + cliente.getTelefone() + "): ");
            String telefone = scanner.nextLine();
            if (!telefone.isBlank()) cliente.setTelefone(telefone);

            System.out.print("Novo Email (atual: " + cliente.getEmail() + "): ");
            String email = scanner.nextLine();
            if (!email.isBlank()) cliente.setEmail(email);

            if (clienteDAO.atualizar(cliente)) {
                System.out.println("Cliente atualizado com sucesso!");
            } else {
                System.out.println("Falha ao atualizar o cliente.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Por favor, digite um número.");
        }
    }

    private static void atualizarImovel(Scanner scanner) {
        System.out.println("\n--- Atualizar Dados de Imóvel por ID ---");
        try {
            System.out.print("Digite o ID do IMÓVEL a ser atualizado: ");
            long id = Long.parseLong(scanner.nextLine());

            // 1. Busca o imóvel no banco de dados
            var imovelOpt = imovelDAO.obterPorId(id);

            // 2. Verifica se o imóvel foi encontrado
            if (imovelOpt.isEmpty()) {
                System.out.println("Imóvel com ID " + id + " não encontrado.");
                return;
            }

            Imovel imovel = imovelOpt.get();
            System.out.println("\nEditando imóvel localizado em: " + imovel.getEndereco());
            System.out.println("(Deixe o campo em branco e pressione Enter para não alterar o valor atual)");

            // 3. Pede os novos dados ao usuário, um por um
            System.out.print("Novo Endereço (atual: " + imovel.getEndereco() + "): ");
            String endereco = scanner.nextLine();
            if (!endereco.isBlank()) imovel.setEndereco(endereco);

            System.out.print("Novo Bairro (atual: " + imovel.getBairro() + "): ");
            String bairro = scanner.nextLine();
            if (!bairro.isBlank()) imovel.setBairro(bairro);

            System.out.print("Nova Cidade (atual: " + imovel.getCidade() + "): ");
            String cidade = scanner.nextLine();
            if (!cidade.isBlank()) imovel.setCidade(cidade);

            System.out.print("Novo CEP (atual: " + imovel.getCep() + "): ");
            String cep = scanner.nextLine();
            if (!cep.isBlank()) imovel.setCep(cep);

            System.out.print("Novo Tipo (atual: " + imovel.getTipoImovel() + "): ");
            String tipo = scanner.nextLine();
            if (!tipo.isBlank()) imovel.setTipoImovel(tipo);

            System.out.print("Nova Área em m² (atual: " + imovel.getAreaM2() + "): ");
            String area = scanner.nextLine();
            if (!area.isBlank()) imovel.setAreaM2(new BigDecimal(area));

            System.out.print("Novo nº de Quartos (atual: " + imovel.getQuartos() + "): ");
            String quartos = scanner.nextLine();
            if (!quartos.isBlank()) imovel.setQuartos(Integer.parseInt(quartos));

            System.out.print("Novo nº de Banheiros (atual: " + imovel.getBanheiros() + "): ");
            String banheiros = scanner.nextLine();
            if (!banheiros.isBlank()) imovel.setBanheiros(Integer.parseInt(banheiros));

            System.out.print("Novas Vagas de Garagem (atual: " + imovel.getVagasGaragem() + "): ");
            String vagas = scanner.nextLine();
            if (!vagas.isBlank()) imovel.setVagasGaragem(Integer.parseInt(vagas));

            System.out.print("Novo Status (Disponível/Alugado) (atual: " + imovel.getStatus() + "): ");
            String status = scanner.nextLine();
            if (!status.isBlank()) imovel.setStatus(status);

            // 4. Chama o DAO para salvar as alterações no banco
            if (imovelDAO.atualizar(imovel)) {
                System.out.println("\nImóvel atualizado com sucesso!");
            } else {
                System.out.println("\nFalha ao atualizar o imóvel.");
            }

        } catch (NumberFormatException e) {
            System.out.println("\nErro: ID, número de quartos/banheiros/vagas ou área inválidos. Por favor, digite um número válido.");
        } catch (Exception e) {
            System.out.println("\nOcorreu um erro inesperado: " + e.getMessage());
        }
    }

    private static void atualizarContrato(Scanner scanner) {
        System.out.println("\n--- Atualizar Dados de Contrato por ID ---");
        try {
            System.out.print("Digite o ID do CONTRATO a ser atualizado: ");
            long id = Long.parseLong(scanner.nextLine());

            // 1. Busca o contrato no banco de dados
            var contratoOpt = contratoDAO.obterPorId(id);

            // 2. Verifica se o contrato foi encontrado
            if (contratoOpt.isEmpty()) {
                System.out.println("Contrato com ID " + id + " não encontrado.");
                return;
            }

            Contratos contrato = contratoOpt.get();
            System.out.println("\nEditando contrato do cliente ID " + contrato.getCliente().getId() +
                    " para o imóvel ID " + contrato.getImovel().getId());
            System.out.println("(Deixe o campo em branco e pressione Enter para não alterar o valor atual)");

            // 3. Pede os novos dados ao usuário, um por um
            // Lidar com chaves estrangeiras (Cliente e Imóvel)
            System.out.print("Novo ID do Cliente (atual: " + contrato.getCliente().getId() + "): ");
            String idClienteStr = scanner.nextLine();
            if (!idClienteStr.isBlank()) {
                Cliente novoCliente = new Cliente();
                novoCliente.setId(Long.parseLong(idClienteStr));
                contrato.setCliente(novoCliente);
            }

            System.out.print("Novo ID do Imóvel (atual: " + contrato.getImovel().getId() + "): ");
            String idImovelStr = scanner.nextLine();
            if (!idImovelStr.isBlank()) {
                Imovel novoImovel = new Imovel();
                novoImovel.setId(Long.parseLong(idImovelStr));
                contrato.setImovel(novoImovel);
            }

            // Lidar com valor monetário (BigDecimal)
            System.out.print("Novo Valor do Aluguel Mensal (atual: " + contrato.getValorAluguelMensal() + "): ");
            String valorStr = scanner.nextLine();
            if (!valorStr.isBlank()) {
                contrato.setValorAluguelMensal(new BigDecimal(valorStr));
            }

            // Lidar com datas (Date)
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            System.out.print("Nova Data de Início (dd/mm/aaaa) (atual: " + sdf.format(contrato.getDataInicio()) + "): ");
            String dataInicioStr = scanner.nextLine();
            if (!dataInicioStr.isBlank()) {
                contrato.setDataInicio(sdf.parse(dataInicioStr));
            }

            System.out.print("Nova Data de Fim (dd/mm/aaaa) (atual: " + sdf.format(contrato.getDataFim()) + "): ");
            String dataFimStr = scanner.nextLine();
            if (!dataFimStr.isBlank()) {
                contrato.setDataFim(sdf.parse(dataFimStr));
            }

            // Lidar com status (String)
            System.out.print("Novo Status do Contrato (Ativo/Inativo/Expirado) (atual: " + contrato.getStatusContrato() + "): ");
            String status = scanner.nextLine();
            if (!status.isBlank()) {
                contrato.setStatusContrato(status);
            }

            // 4. Chama o DAO para salvar as alterações no banco
            if (contratoDAO.atualizar(contrato)) {
                System.out.println("\nContrato atualizado com sucesso!");
            } else {
                System.out.println("\nFalha ao atualizar o contrato. Verifique se os IDs de cliente e imóvel são válidos.");
            }

        } catch (NumberFormatException e) {
            System.out.println("\nErro: ID ou valor inválido. Por favor, digite um número válido.");
        } catch (ParseException e) {
            System.out.println("\nErro: Formato de data inválido. Use o formato dd/mm/aaaa.");
        } catch (Exception e) {
            System.out.println("\nOcorreu um erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================================================================
    // MÉTODOS DE CADASTRO E RELATÓRIOS
    // ==================================================================

    private static void cadastrarCliente(Scanner scanner) {
        System.out.println("\n--- Cadastro de Novo Cliente ---");
        try {
            Cliente cliente = new Cliente();
            System.out.print("Nome Completo: ");
            cliente.setNomeCompleto(scanner.nextLine());
            System.out.print("CPF (xxx.xxx.xxx-xx): ");
            cliente.setCpf(scanner.nextLine());
            System.out.print("Telefone: ");
            cliente.setTelefone(scanner.nextLine());
            System.out.print("Email: ");
            cliente.setEmail(scanner.nextLine());
            clienteDAO.inserir(cliente);
            System.out.println("\nCliente cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("\nErro ao cadastrar cliente: " + e.getMessage());
        }
    }

    private static void cadastrarImovel(Scanner scanner) {
        System.out.println("\n--- Cadastro de Novo Imóvel ---");
        try {
            Imovel imovel = new Imovel();
            System.out.print("Endereço Completo: ");
            imovel.setEndereco(scanner.nextLine());
            System.out.print("Bairro: ");
            imovel.setBairro(scanner.nextLine());
            System.out.print("Cidade: ");
            imovel.setCidade(scanner.nextLine());
            System.out.print("CEP: ");
            imovel.setCep(scanner.nextLine());
            System.out.print("Tipo (Casa, Apartamento): ");
            imovel.setTipoImovel(scanner.nextLine());
            System.out.print("Área (m²): ");
            imovel.setAreaM2(new BigDecimal(scanner.nextLine()));
            System.out.print("Quartos: ");
            imovel.setQuartos(Integer.parseInt(scanner.nextLine()));
            System.out.print("Banheiros: ");
            imovel.setBanheiros(Integer.parseInt(scanner.nextLine()));
            System.out.print("Vagas de Garagem: ");
            imovel.setVagasGaragem(Integer.parseInt(scanner.nextLine()));
            imovelDAO.inserir(imovel);
            System.out.println("\nImóvel cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("\nErro ao cadastrar imóvel: " + e.getMessage());
        }
    }

    private static void cadastrarContrato(Scanner scanner) {
        System.out.println("\n--- Cadastro de Novo Contrato ---");
        try {
            Contratos contrato = new Contratos();
            Cliente cliente = new Cliente();
            Imovel imovel = new Imovel();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            System.out.print("ID do Cliente: ");
            cliente.setId(Long.parseLong(scanner.nextLine()));
            contrato.setCliente(cliente);

            System.out.print("ID do Imóvel: ");
            imovel.setId(Long.parseLong(scanner.nextLine()));
            contrato.setImovel(imovel);

            System.out.print("Valor do Aluguel Mensal: ");
            contrato.setValorAluguelMensal(new BigDecimal(scanner.nextLine()));
            System.out.print("Data de Início (dd/mm/aaaa): ");
            contrato.setDataInicio(sdf.parse(scanner.nextLine()));
            System.out.print("Data de Fim (dd/mm/aaaa): ");
            contrato.setDataFim(sdf.parse(scanner.nextLine()));

            contratoDAO.inserir(contrato);
            System.out.println("\nContrato cadastrado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("\nErro: ID ou valor inválido. Por favor, digite um número.");
        } catch (ParseException e) {
            System.out.println("\nErro: Formato de data inválido. Use dd/mm/aaaa.");
        } catch (Exception e) {
            System.out.println("\nErro ao cadastrar contrato: " + e.getMessage());
        }
    }

    private static void listarImoveisDisponiveis() {
        System.out.println("\n--- Relatório: Imóveis Disponíveis ---");
        List<Imovel> imoveis = imovelDAO.obterDisponiveis();
        if (imoveis.isEmpty()) {
            System.out.println("Nenhum imóvel disponível.");
        } else {
            for (Imovel imovel : imoveis) {
                System.out.printf("ID: %d | Tipo: %s | Endereço: %s, %s | Área: %.2f m²\n",
                        imovel.getId(), imovel.getTipoImovel(), imovel.getEndereco(), imovel.getCidade(), imovel.getAreaM2());
            }
        }
    }

    private static void listarContratosAtivos() {
        System.out.println("\n--- Relatório: Contratos Ativos ---");
        List<String> contratos = contratoDAO.obterContratosAtivos();
        if (contratos.isEmpty()) {
            System.out.println("Nenhum contrato ativo.");
        } else {
            contratos.forEach(System.out::println);
        }
    }

    private static void listarClientesComMaisContratos() {
        System.out.println("\n--- Relatório: Clientes com Mais Contratos ---");
        List<String> clientes = clienteDAO.obterClientesComMaisContratos();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum contrato encontrado para gerar o relatório.");
        } else {
            clientes.forEach(System.out::println);
        }
    }

    private static void listarContratosExpirando() {
        System.out.println("\n--- Relatório: Contratos Expirando em 30 Dias ---");
        List<String> contratos = contratoDAO.obterContratosExpirando();
        if (contratos.isEmpty()) {
            System.out.println("Nenhum contrato expirando nos próximos 30 dias.");
        } else {
            contratos.forEach(System.out::println);
        }
    }

    // ==================================================================
    // MÉTODOS UTILITÁRIOS
    // ==================================================================

    private static void pressioneEnterParaContinuar(Scanner scanner) {
        System.out.println("\nPressione Enter para voltar ao menu...");
        scanner.nextLine();
    }

    private static void limparTela() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void limparTela(PrintWriter writer) {
        writer.print("\033[H\033[2J");
        writer.flush();
    }
}
