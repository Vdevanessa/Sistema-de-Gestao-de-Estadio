package Teste_Final;

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nBem-vindo ao Sistema de Gestão do Estádio!\n");

        // Criar Estádio
        System.out.println("Criando Estádio...\n");

        // Espera de 2 segundos
        try {
            Thread.sleep(2000); // espera 2 segundos
        } catch (InterruptedException e) {
            System.out.println("Erro ao pausar a execução: " + e.getMessage());
        }

        // Criar o estádio com nome e localização
        Estadio estadio = new Estadio("Estádio José Alvalade", "Rua Professor Fernando da Fonseca, Lisboa");

        // Exibir os detalhes do estádio
        System.out.println("Nome: " + estadio.getNome());
        System.out.println("ID: " + estadio.gerarIdUnico());
        System.out.println("Localização: " + estadio.getLocalizacao());
        System.out.println("\nSetores criados:");

        // Exibir os setores criados
        for (Setor setor : estadio.getSetores()) {
            System.out.println("- Setor " + setor.getId() + ": " + setor.getCapacidade() + " lugares (" + setor.getPrecoBase() + "€)");
        }

        // Exibir visualização inicial dos setores
        System.out.println("\nVisualização inicial dos setores:");
        System.out.println("(Legenda: [ ] = Livre, [X] = Ocupado)\n");

        for (Setor setor : estadio.getSetores()) {
            System.out.print("Setor " + setor.getId() + ": ");
            System.out.println();
            exibirLugares(setor.getLugares());
        }


        // Criar Jogo
        System.out.println("\nCriando Jogo...\n");

        // Espera de 2 segundos
        try {
            Thread.sleep(2000); // espera 2 segundos
        } catch (InterruptedException e) {
            System.out.println("Erro ao pausar a execução: " + e.getMessage());
        }

        Equipa equipaCasa = new Equipa("Sporting CP", "Lisboa", 1906, "Rúben Amorim");
        equipaCasa.setPlantel(Arrays.asList(
                "Adán", "Coates", "Inácio", "Porro", "Reis",
                "Ugarte", "Morita", "Edwards", "Gonçalves", "Trincão", "Paulinho"
        ));

        Equipa equipaVisitante = new Equipa("FC Porto", "Porto", 1893, "Sérgio Conceição");
        equipaVisitante.setPlantel(Arrays.asList(
                "Costa", "Pepe", "Cardoso", "Manafá", "Grujic",
                "Uribe", "Otávio", "Eustáquio", "Galeno", "Taremi", "Evanilson"
        ));

        // Criar o jogo
        Jogo jogo = new Jogo("Sporting CP vs FC Porto", "2024-01-15 20:30", equipaCasa, equipaVisitante);

        // Exibir os detalhes do jogo
        System.out.println("\nDetalhes do Jogo:\n");
        System.out.println("Equipa da Casa: " + equipaCasa.getNome());
        System.out.println("-Cidade: " + equipaCasa.getCidade());
        System.out.println("-Fundação: " + equipaCasa.getDataFundacao());
        System.out.println("-Treinador: " + equipaCasa.getTreinador());
        System.out.println("Plantel: " + equipaCasa.getPlantel().size() + " jogadores carregados:");
        System.out.println(String.join(", ", equipaCasa.getPlantel()));
        System.out.println("\nEquipa Visitante: " + equipaVisitante.getNome());
        System.out.println("-Cidade: " + equipaVisitante.getCidade());
        System.out.println("-Fundação: " + equipaVisitante.getDataFundacao());
        System.out.println("-Treinador: " + equipaVisitante.getTreinador());
        System.out.println("-Plantel: " + equipaVisitante.getPlantel().size() + " jogadores carregados:");
        System.out.println(String.join(", ", equipaVisitante.getPlantel()));

        System.out.println("\nJogo criado com sucesso: " + jogo.getNome());
        System.out.println("\nData e Hora: " + jogo.getDataHora());

        estadio.setJogo(jogo); // Associar o jogo ao estádio


        List<Rolote> rolotes = new ArrayList<>();
        boolean criarNovaRolote = true;

        while (criarNovaRolote) {
            // Chama o metodo estático para criar a rolote
            Rolote novaRolote = Rolote.criarRolote(scanner, estadio.getRolotes().size() + 1, estadio);

            // Perguntar se deseja criar outra rolote
            System.out.print("\nDeseja criar uma nova rolote? (s/n): ");
            criarNovaRolote = scanner.nextLine().equalsIgnoreCase("s");
        }


        List<Adepto> adeptos = new ArrayList<>();
        boolean criarNovoAdepto = true;

        while (criarNovoAdepto) {
            // Criar um novo adepto
            Adepto adepto = Adepto.criarAdepto(scanner);

            // Registrar o adepto (criar diretório e ficheiro)
            adepto.registrarAdepto();

            // Adicionar o adepto à lista de adeptos
            adeptos.add(adepto);

            // Perguntar se deseja criar outro adepto
            System.out.print("\nDeseja criar um novo adepto? (s/n): ");
            criarNovoAdepto = scanner.nextLine().equalsIgnoreCase("s");

        }

        // Exibir resumo dos adeptos criados
        System.out.println("\nResumo dos Adeptos Criados:");
        for (Adepto adepto : adeptos) {
            adepto.exibirResumo();
        }

        System.out.println("\n\n============ Bilheteria ============");

        System.out.print("\nQual o seu ID de adepto? ");
        String idAdepto = scanner.nextLine().toUpperCase();

        Adepto adeptoSelecionado = Adepto.carregarAdepto(idAdepto);

        System.out.println("\nCarregando informações...\n");
        System.out.println("Adepto: " + adeptoSelecionado.getNome() + "(saldo: " + adeptoSelecionado.getCarteira() + "€)");

        if (adeptoSelecionado == null) {
            System.out.println("Adepto não encontrado.");
            return;
        }

        // Chamar o metodo comprarBilhete da classe Adepto
        if (!adeptoSelecionado.comprarBilhete(scanner, estadio, jogo)) {
            System.out.println("Compra não realizada.");
        }


        boolean jogoIniciado = false;

        while (!jogoIniciado) {
            System.out.println("\n=========== Menu Principal ===========\n");
            System.out.println("O que deseja fazer?\n");
            System.out.println("1. Adicionar nova rolote");
            System.out.println("2. Criar novo adepto");
            System.out.println("3. Comprar comida");
            System.out.println("4. Comprar mais bilhetes");
            System.out.println("5. Estatísticas do Estádio");
            System.out.println("6. Iniciar jogo");
            System.out.print("\nSelecione uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1:
                    criarNovaRolote = true;
                    while (criarNovaRolote) {
                        // Chama o metodo estático para criar a rolote
                        Rolote novaRolote = Rolote.criarRolote(scanner, estadio.getRolotes().size() + 1, estadio);

                        // Perguntar se deseja criar outra rolote
                        System.out.print("\nDeseja criar uma nova rolote? (s/n): ");
                        criarNovaRolote = scanner.nextLine().equalsIgnoreCase("s");
                    }

                    break;

                case 2:
                    // Criar um novo adepto
                    Adepto adepto = Adepto.criarAdepto(scanner);

                    // Registrar o adepto (criar diretório e ficheiro)
                    adepto.registrarAdepto();

                    // Adicionar o adepto à lista de adeptos
                    adeptos.add(adepto);

                    break;
                case 3:
                    adeptoSelecionado.comprarComida(rolotes, scanner, estadio);
                    break;
                case 4:
                    System.out.println("\n\n============ Bilheteria ============");

                    // Chamar o metodo comprarBilhete da classe Adepto
                    if (!adeptoSelecionado.comprarBilhete(scanner, estadio, jogo)) {
                        System.out.println("Compra não realizada.");
                    }
                    break;
                case 5:
                    estadio.carregarAdeptos();
                    estadio.exibirEstatisticas();
                    break;
                case 6:
                    // Verificar se existe um jogo configurado
                    if (estadio.getJogo() == null) {
                        System.out.println("Nenhum jogo foi configurado. Por favor, configure um jogo antes de iniciar.");
                        break;
                    }
                    // Iniciar o jogo
                    estadio.getJogo().iniciarJogo(estadio);
                    jogoIniciado=true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
    // Metodo para exibir a matriz de lugares de um setor
    public static void exibirLugares(boolean[][] lugares) {
        for (int i = 0; i < lugares.length; i++) {
            for (int j = 0; j < lugares[i].length; j++) {
                System.out.print(lugares[i][j] ? "[X]" : "[ ]"); // [X] = Ocupado, [ ] = Livre
            }
            System.out.println();
        }
        System.out.println(); // Linha em branco entre setores


    }
}

