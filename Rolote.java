package Teste_Final;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Rolote {
    private int id; // ID único da rolote (1 a 5)
    private String nome; // Nome da rolote
    private boolean aberto; // Indica se a rolote está aberta
    private List<Produto> produtos; // Lista de produtos disponíveis
    private double faturamentoDiario; // Total arrecadado no dia

    // Construtor
    public Rolote(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.aberto = false; // Inicialmente fechada
        this.produtos = new ArrayList<>();
        this.faturamentoDiario = 0.0;
    }

    public static Rolote criarRolote(Scanner scanner, int idRolote, Estadio estadio) {
        scanner = new Scanner(System.in);
        System.out.println("\nCriando rolote (#" + idRolote + ")...");
        try {
            Thread.sleep(1000); // Espera 1 segundo
        } catch (InterruptedException e) {
            System.out.println("Erro ao pausar a execução: " + e.getMessage());
        }

        // Captura o nome da rolote
        System.out.print("\nDigite o nome da rolote: ");
        String nomeRolote = scanner.nextLine();

        // Instancia a nova rolote
        Rolote rolote = new Rolote(idRolote, nomeRolote);

        System.out.println("\n\nAdicionar produtos à rolote...");
        boolean adicionarMaisProdutos = true;

        // Loop para adicionar produtos
        while (adicionarMaisProdutos) {
            // Captura os dados do produto
            System.out.print("\nDigite o nome do produto: ");
            String nomeProduto = scanner.nextLine();

            double precoProduto;
            while (true) {
                System.out.print("Digite o preço do produto: ");
                if (scanner.hasNextDouble()) {
                    precoProduto = scanner.nextDouble();
                    if (precoProduto > 0) break; // Preço válido
                    System.out.println("Erro: O preço deve ser maior que zero!");
                } else {
                    System.out.println("Erro: Por favor, insira um valor numérico!");
                }
                scanner.nextLine(); // Consumir entrada inválida
            }

            int stockProduto;
            while (true) {
                System.out.print("Digite a quantidade em stock: ");
                if (scanner.hasNextInt()) {
                    stockProduto = scanner.nextInt();
                    if (stockProduto >= 0) break; // Stock válido
                    System.out.println("Erro: A quantidade em stock não pode ser negativa!");
                } else {
                    System.out.println("Erro: Por favor, insira um número inteiro!");
                }
                scanner.nextLine(); // Consumir entrada inválida
            }

            scanner.nextLine(); // Consumir quebra de linha
            rolote.adicionaProduto(nomeProduto, precoProduto, stockProduto);

            // Perguntar se deseja adicionar mais produtos
            System.out.print("\nDeseja adicionar mais produtos à rolote? (s/n): ");
            adicionarMaisProdutos = scanner.nextLine().equalsIgnoreCase("s");
        }

        // Registrar a rolote
        rolote.registrarRolote();

        //Abrir Rolote
        rolote.abrirRolote();

        // Exibir resumo dos produtos
        rolote.resumoProdutos();
        System.out.println("Faturamento atual: " + rolote.calcularFaturacao() + "€");


        // Adicionar a rolote à lista de rolotes do sistema
        estadio.adicionarRolote(rolote);


        // Retornar a rolote criada
        return rolote;
    }


    // Metodo para registrar a rolote
    public void registrarRolote() {
        System.out.println("\nRolote '" + nome + "' criada com sucesso!");

    }

    // Metodo para abrir a rolote
    public void abrirRolote() {
        if (!aberto) {
            aberto = true;
            System.out.println("Estado: Aberto");
        } else {
            System.out.println("\nA rolote '" + nome + "' já está aberta.");
        }
    }

    // Metodo para fechar a rolote
    public void fecharRolote() {
        if (aberto) {
            aberto = false;
            System.out.println("Estado: Fechado");
        } else {
            System.out.println("A rolote '" + nome + "' já está fechada.");
        }
    }

    // Metodo para adicionar um produto à rolote
    public void adicionaProduto(String nomeProduto, double preco, int quantidade) {
        if (preco <= 0 || quantidade < 0) {
            System.out.println("Erro: Preço ou quantidade inválidos.");
            return;
        }
        Produto produto = new Produto(produtos.size() + 1, nomeProduto, preco, quantidade);
        produtos.add(produto);
        System.out.println("\nProduto '" + nomeProduto + "' adicionado com sucesso!");
    }


    // Metodo para vender produtos
    public void vendeProduto(List<Produto> listaProdutos) {
        for (Produto produto : listaProdutos) {
            for (Produto p : produtos) {
                if (p.getId() == produto.getId() && p.getQuantidadeStock() > 0) {
                    p.setQuantidadeStock(p.getQuantidadeStock() - 1); // Diminui o estoque
                    faturamentoDiario += p.getPreco(); // Adiciona ao faturamento
                    System.out.println("Produto '" + p.getNome() + "' vendido.");
                } else if (p.getId() == produto.getId() && p.getQuantidadeStock() == 0) {
                    System.out.println("Produto '" + p.getNome() + "' está fora de estoque.");
                }
            }
        }
    }

    public void resumoProdutos() {
        System.out.println("\nResumo dos Produtos na Rolote '" + nome + "':");
        for (int i = 0; i < produtos.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, produtos.get(i).toString());
        }
    }


    // Metodo para calcular o faturamento diário
    public double calcularFaturacao() {
        return faturamentoDiario;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public boolean isAberto() {
        return aberto;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}

