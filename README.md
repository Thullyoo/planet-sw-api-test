# Projeto de Estudo: Testes com JUnit

## Descrição
Este projeto foi desenvolvido com o objetivo de estudar e praticar testes automatizados utilizando o framework **JUnit**. O foco está na implementação de **testes de unidade** e **testes de integração**, explorando boas práticas de escrita de testes, organização de código e validação de funcionalidades.

## Tecnologias Utilizadas
- **Java**: Linguagem de programação principal.
- **JUnit 5**: Framework para criação e execução de testes.
- **Maven**: Gerenciador de dependências e build.
- **Mockito** (opcional): Para simulação de dependências em testes de unidade.
- **H2 Database** (opcional): Banco de dados em memória para testes de integração.

## Tipos de Testes Implementados
### Testes de Unidade
- Testam componentes isolados da aplicação (ex.: métodos de uma classe).
- Utilizam **Mockito** para simular dependências externas.
- Exemplos: Validação de regras de negócio, cálculos e lógica interna.

### Testes de Integração
- Testam a interação entre diferentes componentes do sistema (ex.: integração com banco de dados).
- Utilizam **H2 Database** para simular um ambiente real.
- Exemplos: Testes de persistência, chamadas a APIs internas e fluxos completos.

## Pré-requisitos
- Java 11 ou superior
- Maven 3.6.0 ou superior
- IDE compatível (IntelliJ, Eclipse, etc.)
