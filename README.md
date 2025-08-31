# Sistema de Gestão de Imobiliária

![Java](https://img.shields.io/badge/Java-22-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white )
![JavaFX](https://img.shields.io/badge/JavaFX-21-007396?style=for-the-badge&logo=openjfx&logoColor=white )
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white )
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?style=for-the-badge&logo=postgresql&logoColor=white )
[![Java CI with Maven](https://github.com/GabrielWalendolf/imobiliaria-manga/actions/workflows/build.yml/badge.svg )](https://github.com/GabrielWalendolf/imobiliaria-manga/actions/workflows/build.yml )

## 📖 Sobre o Projeto

Este é um sistema de gestão para imobiliárias desenvolvido em Java, utilizando **JavaFX** para a interface gráfica. O projeto foi criado como parte da disciplina de Programação Orientada a Objetos, aplicando conceitos de persistência de dados com JDBC, o padrão de projeto DAO (Data Access Object) e interação com o usuário através de uma interface gráfica de desktop.

O sistema permite o gerenciamento completo de clientes, imóveis e contratos de aluguel, além de um painel de administrador protegido por senha para operações críticas.

---

## ✨ Funcionalidades Principais

O sistema oferece as seguintes funcionalidades através de sua interface gráfica:

*   **Cadastro de Entidades:**
    *   Cadastrar novos imóveis com suas características (endereço, área, quartos, etc.).
    *   Cadastrar novos clientes com seus dados pessoais.
    *   Cadastrar contratos de aluguel, vinculando um cliente a um imóvel.

*   **Relatórios Gerenciais:**
    *   Listar todos os imóveis disponíveis para aluguel.
    *   Listar todos os contratos de aluguel que estão atualmente ativos.
    *   Gerar um ranking de clientes com o maior número de contratos.
    *   Listar contratos que estão prestes a expirar.

*   **Painel de Administrador Seguro:**
    *   Login com usuário e senha para acessar funcionalidades restritas.
    *   Atualizar e deletar dados de clientes, imóveis e contratos existentes.

---

## 🛠️ Tecnologias Utilizadas

*   **Linguagem:** [Java 22](https://www.oracle.com/java/ )
*   **Interface Gráfica:** [JavaFX](https://openjfx.io/ )
*   **Banco de Dados:** [PostgreSQL 16](https://www.postgresql.org/ )
*   **Gerenciador de Dependências:** [Apache Maven](https://maven.apache.org/ )
*   **Conexão com Banco (JDBC):** [PostgreSQL JDBC Driver](https://jdbc.postgresql.org/ )

---

## 🚀 Como Executar o Projeto

Siga os passos abaixo para configurar e rodar o projeto em seu ambiente local.

### 1. Pré-requisitos

Antes de começar, você precisará ter instalado:
*   [JDK (Java Development Kit)](https://www.oracle.com/java/technologies/downloads/ ) - Versão 22 ou superior.
*   [Apache Maven](https://maven.apache.org/download.cgi ) - Para gerenciar as dependências e o build.
*   [PostgreSQL](https://www.postgresql.org/download/ ) - O banco de dados do sistema.
*   [Git](https://git-scm.com/downloads ) - Para clonar o repositório.

### 2. Configuração do Banco de Dados

1.  Após instalar o PostgreSQL, crie um novo banco de dados. Você pode utilizar o [script de criação](https://github.com/GabrielWalendolf/imobiliaria-manga/blob/main/src/main/java/br/edu/univille/poo/dao/Script_Criacao_Banco_Imobiliaria.txt ) e nomeá-lo como `imobiliaria_db`.
2.  Execute o script SQL completo para criar todas as tabelas, triggers e o usuário administrador padrão.
    *   **Usuário Admin Padrão:** `admin`
    *   **Senha:** `admin123`

### 3. Configuração do Projeto

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/GabrielWalendolf/imobiliaria-manga.git
    cd imobiliaria-manga
    ```

2.  **Configure a Conexão com o Banco:**
    Abra o arquivo `src/main/java/br/edu/univille/poo/dao/ConnectionFactory.java`.
    Altere os dados de conexão (`url`, `user`, `password` ) para corresponder à sua configuração do PostgreSQL.
    ```java
    // dentro do método get()
    String url = "jdbc:postgresql://localhost:5432/imobiliaria_db"; // Altere o nome do banco se necessário
    String user = "postgres"; // Seu usuário do PostgreSQL
    String password = "sua_senha_aqui"; // Sua senha do PostgreSQL
    ```

### 4. Execução

Com o Maven configurado, você pode compilar e executar o projeto com um único comando no terminal, a partir da raiz do projeto. O Maven irá baixar as dependências do JavaFX e iniciar a aplicação.

```bash
mvn clean javafx:run
```

A janela principal do sistema será iniciada em sua tela. </br>

👨‍💻 Dev</br>
[GabrielWalendolf](https://github.com/GabrielWalendolf)</br>

Este README foi gerado para auxiliar na documentação e uso do projeto. Sinta-se à vontade para contribuir ou reportar issues.