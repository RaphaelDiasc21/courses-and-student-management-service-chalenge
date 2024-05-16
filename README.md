# Gerenciador de cursos, alunos e matriculas

API REST que tem por finalidade gerenciar 3 principais domininios:
 - Curso
 - Aluno
 - Matricula

Adicionalmente foi  implementado o sistema de avaliação e relatório de Net Promoter Score, conforme solicitado.
Os dominios referentes a esses requisitos são:
 - Rate
 - NPS

O sistema de avaliação, bem como o relatorio NPS, são utilizados através dos endpoints:
 - ``/rates`` - Criar avaliação referente a determinado curso (através do código). Esse endpoint
apenas é acessível por um usuario de Role `ESTUDANTE`

 - ``/nps/courses-nps`` - Endpoint que traz o relatorio referente ao nps de todos os cursos que tenham mais de 4 matricula.
Esse endpoint apenas é acessível por um usuario de Role `ADMIN`.

**Note-se**: Foi implementado a funcionalidade de notificação para o instrutor do curso avaliado, se a nota de avaliacao
for menor que 6. Essa notificação é simulada logando no console, ou seja, foi utilizado o recomendado na descrição do Case.

## Descrição técnica
A estrutura de pacotes utilizada no projeto foi a "Package-by-feature", ou seja, cada dominio tem sua estrutura de pacotes isoladas.
O motivo dessa escolha foi visar o baixo acoplamento entre os dominios da aplicação.

O ponto negativo dessa abordagem, notei que gerou-se muitos pacotes duplicados, deixando o projeto com um aspecto extenso.

A arquitetura foi pensada em camdas, ou seja:
  - Presentation (controller)
  - Business (services)
  - Data (repository e entities)


## Tecnologias utilizadas
 - Java: 21
 - Spring boot: 3
 - Spring security
 - Swagger para documentação (http://localhost:8080/swagger-ui/index.html)
 - Docker

## Execução do projeto

A aplicação pode ser executada atraves da automação criada via Make:
- ``make run``

Ou executando diretamente o docker-compose. Porém, se faz necessário, buildar o projeto antes, utilizando o maven, ficando:
- ``mvn clean package -DskipTests=true``

- ``docker-compose up``

## Dificuldades encontradas e pendencias
 - Testes: Não consegui implementar os testes da camada de apresentação, devido a dificuldade de mockar corretamente, para que o Spring Security reconhecesse a authority do usuario.
   - **Nota-se** tambem que encontrei uma dificuldade em acertar os testes utilizando o flyway, por algum motivo, ocorre um erro ao executar o comando ``mvn test``. Porem os testes executam corretamente de forma separada. Entendo que nao é o ideal.
   - Visto o motivo explicado acima, que para buildar o projeto, estou dando skip na fase de testes ```-skipTests=true```
   
