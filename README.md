# Sistema de Gestão de Imobiliária - Web

[![Java CI with Maven](https://github.com/GabrielWalendolf/imobiliaria-manga/actions/workflows/build.yml/badge.svg )](https://github.com/GabrielWalendolf/imobiliaria-manga/actions/workflows/build.yml )

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white )
![Spring](https://img.shields.io/badge/Spring_Boot-3-6DB33F?style=for-the-badge&logo=spring&logoColor=white )
![Angular](https://img.shields.io/badge/Angular-15-DD0031?style=for-the-badge&logo=angular&logoColor=white )
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?style=for-the-badge&logo=postgresql&logoColor=white )

## 📖 Sobre o Projeto

Este é um sistema web completo para a gestão de uma imobiliária, desenvolvido com uma arquitetura moderna que separa o backend (API REST) do frontend (Single Page Application). O projeto foi criado para aplicar conceitos avançados de desenvolvimento de software, incluindo persistência de dados com Spring Data JPA, segurança com Spring Security e uma interface de usuário reativa com Angular.

O sistema permite o gerenciamento completo de clientes e imóveis, com funcionalidades para cadastro, busca, visualização e administração, simulando uma plataforma imobiliária real.

---

## ✨ Funcionalidades Principais

A plataforma oferece as seguintes funcionalidades:

*   **Gestão de Imóveis:**
    *   Cadastro de novos imóveis com características detalhadas (endereço, área, quartos, etc.).
    *   Upload de múltiplas imagens por imóvel.
    *   Funcionalidades CRUD (Criar, Ler, Atualizar, Deletar) para os anúncios.

*   **Portal do Cliente:**
    *   Cadastro e autenticação de usuários.
    *   Busca avançada de imóveis com filtros por tipo, preço e localização.
    *   Visualização detalhada das propriedades disponíveis.

*   **Painel de Administrador:**
    *   Acesso a funcionalidades administrativas para gerenciar todos os imóveis e usuários do sistema.
    *   Interface segura e dedicada para operações críticas.

---

## 🛠️ Tecnologias Utilizadas

*   **Backend (API REST):**
    *   **Linguagem:** [Java 17](https://www.oracle.com/java/ )
    *   **Framework:** [Spring Boot 3](https://spring.io/projects/spring-boot )
    *   **Persistência:** [Spring Data JPA](https://spring.io/projects/spring-data-jpa ) / Hibernate
    *   **Segurança:** [Spring Security](https://spring.io/projects/spring-security )
    *   **Banco de Dados:** [PostgreSQL 16](https://www.postgresql.org/ )
    *   **Gerenciador de Dependências:** [Apache Maven](https://maven.apache.org/ )

*   **Frontend (Single Page Application):**
    *   **Framework:** [Angular 15](https://angular.io/ )
    *   **Linguagem:** [TypeScript](https://www.typescriptlang.org/ )
    *   **Estilização:** SCSS e [Angular Material](https://material.angular.io/ )

---

## 🚀 Como Executar o Projeto

Siga os passos abaixo para configurar e rodar o projeto em seu ambiente local.

### 1. Pré-requisitos

Antes de começar, você precisará ter instalado:
*   [JDK (Java Development Kit)](https://www.oracle.com/java/technologies/downloads/ ) - Versão 17 ou superior.
*   [Apache Maven](https://maven.apache.org/download.cgi )
*   [Node.js e npm](https://nodejs.org/ )
*   [Angular CLI](https://angular.io/cli )
*   [PostgreSQL](https://www.postgresql.org/download/ )
*   [Git](https://git-scm.com/downloads )

### 2. Configuração do Banco de Dados

1.  Após instalar o PostgreSQL, crie um novo banco de dados (ex: `imobiliaria_web_db`).
2.  O Spring Boot com JPA (`ddl-auto=update`) pode criar as tabelas automaticamente na primeira execução.

### 3. Configuração e Execução do Backend

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/GabrielWalendolf/imobiliaria-manga.git
    cd imobiliaria-manga
    ```

2.  **Configure a Conexão com o Banco:**
    Abra o arquivo `imobiliaria/src/main/resources/application.properties`.
    Altere os dados de conexão para corresponder à sua configuração do PostgreSQL.
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/imobiliaria_web_db
    spring.datasource.username=seu_usuario_postgres
    spring.datasource.password=sua_senha_postgres
    spring.jpa.hibernate.ddl-auto=update
    ```

3.  **Execute a API:**
    Navegue até a pasta do backend e execute o comando:
    ```bash
    cd imobiliaria
    mvn spring-boot:run
    ```
    A API estará rodando em `http://localhost:8080`.

### 4. Configuração e Execução do Frontend

1.  **Acesse a branch correta:**
    Em um novo terminal, na raiz do projeto, mude para a branch da aplicação web.
    ```bash
    git checkout AppV.2.0.0.2025
    ```

2.  **Instale as dependências:**
    Navegue até a pasta do frontend.
    ```bash
    cd imobiliaria-manga
    npm install
    ```

3.  **Execute a Aplicação:**
    ```bash
    ng serve
    ```
    A interface web estará acessível em `http://localhost:4200`.

---

### 👨‍💻 Autor

*   **[GabrielWalendolf](https://github.com/GabrielWalendolf )**

Este README foi gerado para auxiliar na documentação e uso do projeto. Sinta-se à vontade para contribuir ou reportar issues.
