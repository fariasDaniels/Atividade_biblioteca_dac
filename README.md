API de biblioteca utilizando transações.

Passo a passo para execução:

Após clonar o repositório, execute o arquivo main diretamente se estiver usando IDE. Caso contrário, abra o diretório raiz através do terminal, e execute 
    mvn clean install
    java -jar target/Library.jar

No Insomnia, teste os endpoints da seguinte forma: 
    -cadastrar livro 
    http://localhost:8080/livros
    POST
    Exemplo de boby:
    
      {
        "titulo": "Memórias do Subsolo",
        "autor": "Fiódor Dostoievski",
        "isbn": "978-6587435367",
        "anoPublicacao": 1864,
        "quantidadeEstoque": 5
      }


-Buscar livro 
    http://localhost:8080/livros
    GET
      *caso queira buscar por id
    (http://localhost:8080/livros{id})
      *caso queira buscar por isbn
    (http://localhost:8080/livros/isbn/{isbn})

-atualizar livro
    http://localhost:8080/livros/{id}
    PUT
    Exemplo de boby:
    
      {
        "titulo": "Memórias do Subsolo",
        "autor": "Fiódor Dostoievski",
        "isbn": "978-6587435367",
        "anoPublicacao": 1864,
        "quantidadeEstoque": 2
      }

-deletar livro
     http://localhost:8080/livros/{id}
     DELETE


Para executar os testes, rode o arquivo LibraryApplicationTests localizado no diretório test
