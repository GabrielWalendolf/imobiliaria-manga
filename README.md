Sistema de Gestão de Imobiliária
Java

Maven

PostgreSQL
📖 Sobre o Projeto
Este é um sistema de linha de comando (CLI) desenvolvido em Java para gerenciar as operações de uma pequena imobiliária. O projeto foi criado como parte da disciplina de Programação Orientada a Objetos II, aplicando conceitos de persistência de dados com JDBC, padrões de projeto como DAO (Data Access Object) e Singleton, e interação com o usuário via terminal.
O sistema permite o gerenciamento completo de clientes, imóveis e contratos de aluguel, além de um painel de administrador protegido por senha para operações críticas.
✨ Funcionalidades Principais
O sistema oferece as seguintes funcionalidades:
Cadastro de Entidades:
Cadastrar novos imóveis com suas características (endereço, área, quartos, etc.).
Cadastrar novos clientes com seus dados pessoais.
Cadastrar contratos de aluguel, vinculando um cliente a um imóvel.
Relatórios Gerenciais:
Listar todos os imóveis disponíveis para aluguel.
Listar todos os contratos de aluguel que estão atualmente ativos.
Gerar um ranking de clientes com o maior número de contratos.
Listar contratos que estão prestes a expirar (nos próximos 30 dias).
Painel de Administrador Seguro:
Login com usuário e senha para acessar funcionalidades restritas.
Atualizar dados de clientes, imóveis e contratos existentes.
Deletar registros do sistema (clientes, imóveis, contratos).
🛠️ Tecnologias Utilizadas
Linguagem: Java 22
Banco de Dados: PostgreSQL 16
Gerenciador de Dependências: Apache Maven
Conexão com Banco (JDBC): PostgreSQL JDBC Driver
Interface de Terminal Interativa: JLine
🚀 Como Executar o Projeto
Siga os passos abaixo para configurar e rodar o projeto em seu ambiente local.
1. Pré-requisitos
   Antes de começar, você precisará ter instalado:
   JDK (Java Development Kit) - Versão 22 ou superior.
   Apache Maven - Para gerenciar as dependências e o build.
   PostgreSQL - O banco de dados do sistema.
   Git - Para clonar o repositório.
2. Configuração do Banco de Dados
   Após instalar o PostgreSQL, crie um novo banco de dados. Você pode chamá-lo de imobiliaria_db ou outro nome de sua preferência.
   Execute o script SQL completo para criar todas as tabelas, triggers e o usuário administrador padrão. O script pode ser encontrado em: [https://github.com/GabrielWalendolf/imobiliaria-manga/blob/main/src/main/java/br/edu/univille/poo/dao/Script_Criacao_Banco_Imobiliaria.txt].
   Usuário Admin Padrão: admin
   Senha: admin123
3. Configuração do Projeto
   Clone o repositório:
   Bash
   git clone https://github.com/GabrielWalendolf/imobiliaria-manga.git
   cd imobiliaria-manga
   Configure a Conexão com o Banco:
   Abra o arquivo src/main/java/br/edu/univille/poo/dao/ConnectionFactory.java.
   Altere os dados de conexão (url, user, password ) para corresponder à sua configuração do PostgreSQL.
   Java
   // dentro do método get()
   String url = "jdbc:postgresql://localhost:5432/imobiliaria_db"; // Altere o nome do banco se necessário
   String user = "postgres"; // Seu usuário do PostgreSQL
   String password = "sua_senha_aqui"; // Sua senha do PostgreSQL
4. Execução
   Com o Maven configurado, você pode compilar e executar o projeto com um único comando no terminal, a partir da raiz do projeto:
   Bash
   mvn compile exec:java
   O menu interativo do sistema será iniciado no seu terminal. Use as setas (▲/▼) para navegar e a tecla Enter para selecionar as opções.
   👨‍💻 Autor
   Gabriel Walendolf - GitHub
   Este README foi gerado para auxiliar na documentação e uso do projeto. Sinta-se à vontade para contribuir ou reportar issues