# Imobiliária Manga - Versão 2.0.0.2025

Bem-vindo ao repositório oficial do projeto Imobiliária Manga, uma aplicação web completa para gestão de imóveis, desenvolvida com tecnologias modernas como Angular e Spring Boot.

[![Licença](https://img.shields.io/badge/licen%C3%A7a-MIT-blue.svg )](https://opensource.org/licenses/MIT )
[![Status](https://img.shields.io/badge/status-ativo-success.svg )]()

![imobiliaria-manga-capa](https://user-images.githubusercontent.com/106390731/230789783-7fa51c8a-7a5f-440c-b24e-48995393972c.png )

Este projeto simula uma plataforma imobiliária onde usuários podem se cadastrar, anunciar imóveis para aluguel ou venda e buscar por propriedades de interesse. A versão 2.0.0.2025 representa uma evolução significativa, com melhorias na arquitetura, novas funcionalidades e atualizações de segurança.

## 📋 Índice

- [Funcionalidades Principais](#-funcionalidades-principais)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Arquitetura do Projeto](#-arquitetura-do-projeto)
- [Como Executar o Projeto](#-como-executar-o-projeto)
- [Como Contribuir](#-como-contribuir)
- [Licença](#-licença)

## ✨ Funcionalidades Principais

A plataforma oferece um conjunto robusto de funcionalidades para administradores, proprietários e clientes:

*   **Cadastro de Usuários:** Sistema de autenticação e autorização para diferentes perfis de usuário.
*   **Gestão de Imóveis:** Funcionalidades de CRUD (Criar, Ler, Atualizar e Deletar) para anúncios de imóveis.
*   **Busca Avançada e Filtros:** Permite aos usuários pesquisar imóveis por tipo, localização, faixa de preço e outras características.
*   **Upload de Imagens:** Suporte para múltiplas imagens por anúncio, armazenadas de forma eficiente.
*   **Design Responsivo:** Interface adaptável para uma ótima experiência em desktops, tablets e smartphones.

## 🛠️ Tecnologias Utilizadas

O projeto é construído sobre uma stack de tecnologias modernas e robustas, separando claramente as responsabilidades entre o frontend e o backend.

### **Frontend (Client-Side)**

*   **Angular 15:** Framework principal para a construção da interface de usuário reativa e dinâmica.
*   **TypeScript:** Superset do JavaScript que adiciona tipagem estática ao código.
*   **HTML5 e SCSS:** Para estruturação e estilização dos componentes.
*   **Angular Material:** Biblioteca de componentes de UI para um design consistente e moderno.

### **Backend (Server-Side)**

*   **Java 17:** Linguagem de programação principal para o desenvolvimento do servidor.
*   **Spring Boot 3:** Framework para a criação de aplicações Java robustas e autoconfiguráveis.
*   **Spring Security:** Para gerenciamento de autenticação e controle de acesso.
*   **Spring Data JPA / Hibernate:** Para persistência de dados e mapeamento objeto-relacional.
*   **PostgreSQL:** Banco de dados relacional utilizado para armazenar os dados da aplicação.
*   **Maven:** Ferramenta para gerenciamento de dependências e build do projeto.

## 🏗️ Arquitetura do Projeto

A aplicação segue uma arquitetura desacoplada, com o frontend (Angular) e o backend (Spring Boot) operando de forma independente.

*   **`imobiliaria-manga` (Frontend):** Contém todo o código-fonte da aplicação Angular. O código desta versão está na branch `AppV.2.0.0.2025`.
*   **`imobiliaria` (Backend):** Contém a API RESTful desenvolvida com Spring Boot, responsável por todas as regras de negócio e comunicação com o banco de dados.

Essa separação facilita a manutenção, escalabilidade e o desenvolvimento paralelo das duas partes do sistema.

## 🚀 Como Executar o Projeto

Para executar a aplicação em seu ambiente local, siga os passos abaixo.

### **Pré-requisitos**

*   [Node.js](https://nodejs.org/ ) e npm/yarn
*   [Angular CLI](https://angular.io/cli )
*   [Java JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html ) ou superior
*   [Maven](https://maven.apache.org/ )
*   [PostgreSQL](https://www.postgresql.org/download/ )

### **1. Backend (API)**

```bash
# Clone o repositório principal
git clone https://github.com/GabrielWalendolf/imobiliaria-manga.git

# Navegue até a pasta do backend
cd imobiliaria-manga/imobiliaria

# Instale as dependências
mvn install

# Configure seu banco de dados no arquivo `src/main/resources/application.properties`
# Exemplo de configuração para PostgreSQL:
# spring.datasource.url=jdbc:postgresql://localhost:5432/nome_do_banco
# spring.datasource.username=seu_usuario
# spring.datasource.password=sua_senha
# spring.jpa.hibernate.ddl-auto=update

# Execute a aplicação
mvn spring-boot:run
```
