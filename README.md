# Sistema de Gerenciamento de Eventos em Estádio de Futebol

Este projeto tem como objetivo a criação de um sistema de gerenciamento de eventos em um estádio de futebol. A aplicação permitirá ao usuário realizar operações de compra de bilhetes, adicionar rolotes, criar adeptos, entre outras funcionalidades. O sistema também oferece estatísticas sobre a ocupação do estádio, os jogos realizados e o faturamento das rolotes, além de simular a realização de jogos com resultados aleatórios.

## Funcionalidades

1. **Criação de Estádio e Setores**
   - Um estádio com 4 setores (A, B, C, D) com preços e capacidades distintas.
   - Cada setor possui uma matriz 5x5 representando os lugares.

2. **Criação de Jogo**
   - Criação de um jogo com duas equipas (visitante e da casa).
   - Definição da data e hora do jogo.

3. **Gestão de Rolotes**
   - O utilizador pode criar rolotes no estádio, adicionar produtos e controlar o faturamento diário.
   - Rolotes podem ser abertas ou fechadas durante o evento.

4. **Gestão de Adeptos**
   - Criação de fichas de adeptos com identificadores únicos.
   - Cada adepto pode comprar bilhetes e produtos nas rolotes, e os dados de cada transação são armazenados em um arquivo específico.

5. **Compra de Bilhetes**
   - O utilizador pode comprar bilhetes para diferentes setores do estádio.
   - Os bilhetes são associados a um adepto, e os dados de compra são registrados.

6. **Visualização de Estatísticas**
   - O sistema exibe estatísticas sobre o estádio, como o número de lugares ocupados, o jogo atual, as rolotes abertas e os adeptos presentes.

7. **Simulação de Jogo**
   - O jogo é iniciado aleatoriamente, com uma probabilidade de 10% de cada equipa marcar um gol a cada 10 segundos.
   - O jogo tem uma pausa de 5 segundos ao final do primeiro tempo.
   - Ao final, o resultado do jogo e a receita do estádio são mostrados.

## Classes e Métodos

### Setor
Representa os setores do estádio com preços, capacidade e disponibilidade de lugares.
- **Métodos**:
  - `verificarDisponibilidade()`
  - `atualizarPrecos(double novoPreco)`

### Estádio
Armazena os dados do estádio, incluindo setores, rolotes, adeptos e jogos.
- **Métodos**:
  - `calcularOcupação()`
  - `adicionarRolote()`
  - `adicionarAdepto()`
  - `iniciaJogo()`

### Jogo
Representa o jogo entre as equipas com resultados aleatórios e cálculo de receita.
- **Métodos**:
  - `iniciarJogo()`
  - `calcularReceita()`
  - `getResultado()`

### Equipas
Representa as equipas de futebol, com dados como nome, cidade e plantel.
- **Métodos**: 
  - Getters e Setters.

### Rolote
Representa as barracas de comida no estádio.
- **Métodos**:
  - `abrirRolote()`
  - `fechaRolote()`
  - `adicionaProduto()`
  - `vendeProduto()`
  - `calcularFaturacao()`

### Produto
Define os produtos vendidos nas rolotes.
- **Métodos**: 
  - Getters e Setters.

### Adepto
Representa um adepto do estádio, com informações pessoais e histórico de compras.
- **Métodos**:
  - `comprarBilhete()`
  - `comprarComida()`
  - `consultarGastoTotal()`

### Bilhete
Representa um bilhete comprado por um adepto.
- **Métodos**:
  - Getters e Setters.

## Como Rodar o Projeto

1. Clone o repositório para sua máquina local:
   ```bash
   git clone https://github.com/usuario/projeto-estadio.git

2. Compile e execute o projeto:

-Abra o projeto em sua IDE preferida.
-Compile o código Java.
-Execute a aplicação e siga as instruções para interagir com o sistema.

3. Interaja com o sistema:

-O sistema permite a criação de um estádio, setores, rolotes, adeptos e a compra de bilhetes.
-Após configurar o estádio, você pode iniciar um jogo e visualizar as estatísticas.


## Tecnologias Utilizadas

-Java: Utilizado para implementar a lógica de programação orientada a objetos.  
-Arquivos: Utilizados para armazenar os dados de adeptos, bilhetes e transações.  
-Matrizes 5x5: Utilizadas para representar a disponibilidade de lugares nos setores do estádio.  

## Observações  
O sistema permite a simulação de jogos com resultados aleatórios.  
As operações de compra de bilhetes e alimentos são registradas em arquivos específicos para cada adepto.  
O sistema também oferece funcionalidades de gerenciamento de rolotes, permitindo o controle de produtos vendidos e faturamento diário.  
As estatísticas de ocupação e faturamento são atualizadas dinamicamente, oferecendo uma visão em tempo real do estádio.  
