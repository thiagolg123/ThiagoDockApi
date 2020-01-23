# ThiagoDockApi   [![Build Status](https://travis-ci.com/thiagolg123/ThiagoDockApi.svg?branch=master)](https://travis-ci.com/thiagolg123/ThiagoDockApi)
Teste para dock:
O que esperamos como escopo mínimo:

* Implementar path que realiza a criação de uma conta;
* Implementar path que realiza operação de depósito em uma conta;
* Implementar path que realiza operação de consulta de saldo em determinada conta;
* Implementar path que realiza operação de saque em uma conta;
* Implementar path que realiza o bloqueio de uma conta;
* Implementar path que recupera o extrato de transações de uma conta;

# O que será diferencial:

* Implementar extrato por período;
* Elaborar manual de execução;
* Elaborar documentação;
* Elaborar testes.


Documentação da API, os testes foram executados via postman:
https://documenter.getpostman.com/view/3327369/SWT8fK2P

# Pré-requisitos
Maven, java, ferramenta para testes de api.

# Build & Run
Baixando (clonando) o projeto, rodando com java -jar

## 1. Git Clone
git clone https://github.com/thiagolg123/ThiagoDockApi.git

## 2. Construindo a parte Java
mvn package
java -jar target/dockApi-0.0.1-SNAPSHOT.jar
Pronto! --> http://localhost:8080/account ...
