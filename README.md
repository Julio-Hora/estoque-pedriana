# Gerenciador de Estoque - Padrão DAO

Trabalho da 2ª avaliação da disciplina de Padrões de Projeto.

## Equipe

- JULIO CESAR DE ASSIS DA HORA
- ANDREW MENDES PEREIRA

## Sobre o projeto

Aplicação em Java que gerencia uma lista de produtos de um estoque, com as operações de CRUD (criar, ler, atualizar e deletar).

Para separar o acesso aos dados da lógica de negócio foi usado o padrão **DAO (Data Access Object)**. Assim, se a fonte de dados mudar no futuro (por exemplo de memória para arquivo, ou para um banco de dados), a camada de negócio não precisa ser alterada.

## Estrutura

```
src/
  Principal.java
  modelo/
    Produto.java
  dao/
    ProdutoDAO.java
    ProdutoDAOMemoria.java
    ProdutoDAOArquivo.java
  servico/
    ProdutoService.java
```

- **modelo/Produto**: classe com id, nome, preço e quantidade.
- **dao/ProdutoDAO**: interface com as operações de acesso aos dados (salvar, buscarPorId, listarTodos, atualizar e deletar).
- **dao/ProdutoDAOMemoria**: implementação que guarda os produtos em uma lista na memória.
- **dao/ProdutoDAOArquivo**: implementação que guarda os produtos em um arquivo de texto (`produtos.txt`).
- **servico/ProdutoService**: lógica de negócio (validações e cálculo do valor total do estoque). Ela só conhece a interface `ProdutoDAO`, nunca uma implementação específica.
- **Principal**: demonstra o CRUD funcionando com as duas fontes de dados.

## Como a troca de fonte de dados funciona

O `ProdutoService` recebe um `ProdutoDAO` no construtor. Para trocar a fonte de dados basta mudar qual implementação é passada na `Principal`:

```java
ProdutoDAO dao = new ProdutoDAOMemoria();
ProdutoService service = new ProdutoService(dao);
```

```java
ProdutoDAO dao = new ProdutoDAOArquivo("produtos.txt");
ProdutoService service = new ProdutoService(dao);
```

A classe `ProdutoService` não muda em nada.

## Como executar

Precisa ter o JDK instalado (Java 8 ou mais novo).

Na pasta do projeto:

```
javac -d out src/modelo/*.java src/dao/*.java src/servico/*.java src/Principal.java
java -cp out Principal
```

A `Principal` executa o CRUD primeiro com a fonte em memória e depois com o arquivo. Na execução com arquivo, o `produtos.txt` é criado na pasta onde o comando foi executado. Se rodar mais de uma vez, apague o `produtos.txt` antes para a demonstração começar do zero.
