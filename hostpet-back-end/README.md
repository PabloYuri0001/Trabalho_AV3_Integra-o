# 🐾 HostPet

Bem-vindo ao **HostPet**! Este é um sistema de gerenciamento para hotéis pet, permitindo o cadastro de clientes e seus pets, agendamentos de hospedagem, controle de disponibilidade das baias e gestão financeira. O projeto foi desenvolvido com **Spring Boot** e **SQLite**.

## ⚙️ Tecnologias Utilizadas
- **Java 17** + **Spring Boot**
- **Spring Security** (Autenticação com JWT)
- **SQLite** (Banco de dados)
- **JPA/Hibernate** (ORM)
- **Maven** (Gerenciador de dependências)

---

## 🚀 Como Rodar o Projeto

### 📌 Pré-requisitos
Antes de começar, certifique-se de ter:
- **Java 17** ou superior instalado
- **Maven** configurado
- **IntelliJ IDEA** (ou outra IDE de sua escolha)

### 📂 Clonar o repositório
```sh
git clone https://github.com/seu-usuario/HostPet.git
cd HostPet
```

### 🔧 Configurar o ambiente
O banco de dados utilizado é o **SQLite**, então ele já está embutido no projeto e não requer instalação separada.

Caso precise, verifique a configuração do banco no arquivo `application.properties`:
```properties
spring.datasource.url=jdbc:sqlite:src/main/resources/banco.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=update
```

### ▶️ Executar o projeto
No IntelliJ IDEA, basta rodar a classe principal:
```sh
mvn spring-boot:run
```
Ou, se preferir, executar diretamente:
```sh
java -jar target/hostpet.jar
```

A API estará disponível em **http://localhost:8080** 🚀

---

## 🔑 Autenticação e Segurança
O sistema usa **JWT** para autenticação. Para acessar as rotas protegidas, primeiro você precisa registrar um usuário e fazer login para obter um token.

Para acessar endpoints protegidos, envie o token JWT no **header** da requisição:
```
Authorization: Bearer SEU_TOKEN_AQUI
```

---

## 🛠 Comandos úteis
🔹 Para compilar o projeto:
```sh
mvn clean package
```
🔹 Para rodar os testes:
```sh
mvn test
```
🔹 Para limpar e reconstruir:
```sh
mvn clean install
```

---

## 📦 Dependências Utilizadas
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.xerial</groupId>
        <artifactId>sqlite-jdbc</artifactId>
        <version>3.41.2.1</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

---

## 📝 Observações
- O banco de dados **SQLite** gera um arquivo `banco.db` na raiz do projeto.
- Para visualizar o banco de dados, use ferramentas como **DBeaver**.
- Caso precise resetar os dados, basta excluir `banco.db` e reiniciar a aplicação.

---

## 🎯 Sobre o Projeto
Este repositório tem como foco o back-end da aplicação HostPet, que visa o gerenciamento de hotéis para pets. Ele foi desenvolvido utilizando Spring Boot e SQLite, garantindo funcionalidades como cadastro de clientes e pets, agendamento de hospedagem, controle de disponibilidade de baias e gestão financeira.

