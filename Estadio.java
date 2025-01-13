package Teste_Final;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Estadio {
    // Atributos principais da classe Estádio
    private String id; // ID para o estádio
    private String nome; // Nome do estádio
    private String localizacao; // Endereço do estádio
    private int capacidadeMaxima; // Capacidade máxima (100 lugares = 4 setores * 25)
    private List<Setor> setores; // Lista de setores (A, B, C, D)
    private List<Rolote> rolotes; // Lista de rolotes no estádio
    private List<Bilhete> listaDeBilhetes; // Lista de bilhetes (1 para cada lugar)
    private List<Adepto> adeptos; // Lista de adeptos registrados
    private Jogo jogo; // Jogo que está sendo realizado no estádio

    // Construtor para inicializar o estádio
    public Estadio(String nome, String localizacao) {
        this.id = gerarIdUnico(); // Gera um ID único para o estádio
        this.nome = nome;
        this.localizacao = localizacao;
        this.capacidadeMaxima = 100; // Capacidade fixa (4 setores * 25 lugares)


        // Inicializa os setores do estádio (A, B, C, D com preços distintos)
        this.setores = new ArrayList<>();
        setores.add(new Setor("A", 10.0));
        setores.add(new Setor("B", 20.0));
        setores.add(new Setor("C", 30.0));
        setores.add(new Setor("D", 40.0));

        // Inicializa listas vazias para rolotes e adeptos
        this.rolotes = new ArrayList<>();
        this.adeptos = new ArrayList<>();
        this.listaDeBilhetes = new ArrayList<>();
        this.jogo = null; // Nenhum jogo inicial

        // Gera os bilhetes para cada lugar de cada setor
        gerarBilhetes();
    }

    // Inicializa os detalhes do estádio
    public void inicializarEstadio() {
        System.out.println("Nome: " + nome);
        System.out.println("ID: " + id);
        System.out.println("Localização: " + localizacao);
        System.out.println("\nSetores criados:");
        for (Setor setor : setores) {
            System.out.println("- Setor " + setor.getId() + ": " + setor.getCapacidade() + " lugares (" + setor.getPrecoBase() + "€)");
        }
    }

    // Metodo para gerar bilhetes para todos os lugares
    private void gerarBilhetes() {
        for (Setor setor : setores) {
            String setorId = setor.getId();
            for (int i = 1; i <= setor.getCapacidade(); i++) {
                String lugarId = setorId + i; // Exemplo: A1, A2...
                Bilhete bilhete = new Bilhete(lugarId, setor, setor.getPrecoBase());
                listaDeBilhetes.add(bilhete); // Adiciona o bilhete à lista
            }
        }
    }

    // Metodo para gerar um ID único (10 dígitos aleatórios)
    public static String gerarIdUnico() {

        return String.valueOf((long) (Math.random() * 1_000_000_0000L));
    }

    // Metodo para calcular a ocupação atual do estádio
    public int calcularOcupacao() {
        int lugaresOcupados = 0;
        for (Setor setor : setores) {
            lugaresOcupados += (25 - setor.verificarDisponibilidade()); // Total - disponíveis
        }
        return lugaresOcupados;
    }

    // Metodo para adicionar uma nova rolote ao estádio
    public void adicionarRolote(Rolote rolote) {
        if (rolotes.size() < 5) { // Limite de 5 rolotes
            rolotes.add(rolote);
        } else {
            System.out.println("Não é possível adicionar mais rolotes. Limite atingido!");
        }
    }

    // Metodo para adicionar um novo adepto ao estádio
    public void adicionarAdepto(Adepto adepto) {

        adeptos.add(adepto); // Sem limite de adeptos
    }

    // Metodo para iniciar um jogo entre duas equipas
    public void iniciaJogo(Jogo jogo) {
        if (this.jogo == null) { // Verifica se nenhum jogo está em andamento
            this.jogo = jogo;
            System.out.println("Jogo iniciado: " + jogo.getNome());
        } else {
            System.out.println("Já há um jogo em andamento!");
        }
    }

    // Exibe estatísticas do estádio
    public void exibirEstatisticas() {
        System.out.println("\n===== Estatísticas do Estádio =====\n");
        System.out.println("Capacidade total: " + capacidadeMaxima);
        System.out.println("Lugares ocupados: " + calcularOcupacao());
        System.out.println("Adeptos registrados: " + adeptos.size());;
        for (Adepto adepto : adeptos){
            adeptos.size();
        }
        System.out.println("Rolotes ativas: " + rolotes.size());


        // Exibir estado dos setores
        for (Setor setor : setores) {
            System.out.println("\nSetor " + setor.getId() + ":");
            System.out.println(setor.mostrarEstadoAtual(-1, -1)); // Mostrar matriz de lugares
        }

        // Exibir detalhes do jogo (se houver)
        if (jogo != null) {
            System.out.println("\n========= Detalhes do Jogo ========");
            System.out.println("Jogo: " + jogo.getNome());
            System.out.println("Data e Hora: " + jogo.getDataHora());
            System.out.println("Equipa da Casa: " + jogo.getEquipaCasa().getNome());
            System.out.println("Equipa Visitante: " + jogo.getEquipaVisitante().getNome());
        } else {
            System.out.println("\nNenhum jogo em andamento.");
        }

        // Exibir rolotes
        System.out.println("\n========== Rolotes ==========");
        if (!rolotes.isEmpty()) {
            for (Rolote rolote : rolotes) {
                System.out.println("\nRolote: " + rolote.getNome());
                for (Produto produto : rolote.getProdutos()) {
                    System.out.printf("  - %s: %.2f€ (stock: %d)%n", produto.getNome(), produto.getPreco(), produto.getQuantidadeStock());
                }
                System.out.printf("Faturamento: %.2f€%n", rolote.calcularFaturacao());
            }
        } else {
            System.out.println("Nenhuma rolote registrada.");
        }

        // Exibir adeptos
        System.out.println("\n========== Adeptos ==========\n");
        if (!adeptos.isEmpty()) {
            for (Adepto adepto : adeptos) {
                System.out.printf("- ID: %s | Nome: %s | Bilhetes: %d%n", adepto.getId(), adepto.getNome(), adepto.getQuantidadeBilhetes());
            }
        } else {
            System.out.println("\nNenhum adepto registrado.");
        }
    }

    public void carregarAdeptos() {
        String diretorioBase = "src/Teste_Final/Adeptos";
        File pastaBase = new File(diretorioBase);

        if (!pastaBase.exists() || !pastaBase.isDirectory()) {
            System.out.println("Diretório de adeptos não encontrado.");
            return;
        }

        File[] pastasAdeptos = pastaBase.listFiles();
        if (pastasAdeptos == null || pastasAdeptos.length == 0) {
            System.out.println("Nenhum adepto registrado no sistema.");
            return;
        }

        for (File pasta : pastasAdeptos) {
            if (pasta.isDirectory()) {
                String id = pasta.getName();
                File ficheiroInfo = new File(pasta, "info.txt");

                if (ficheiroInfo.exists()) {
                    try (Scanner leitor = new Scanner(ficheiroInfo)) {
                        String nome = "", documento = "", endereco = "";
                        int idade = 0;
                        double carteira = 0.0;

                        while (leitor.hasNextLine()) {
                            String linha = leitor.nextLine();
                            if (linha.startsWith("Nome: ")) {
                                nome = linha.replace("Nome: ", "").trim();
                            } else if (linha.startsWith("Idade: ")) {
                                idade = Integer.parseInt(linha.replace("Idade: ", "").trim());
                            } else if (linha.startsWith("CC: ")) {
                                documento = linha.replace("CC: ", "").trim();
                            } else if (linha.startsWith("Endereço: ")) {
                                endereco = linha.replace("Endereço: ", "").trim();
                            } else if (linha.startsWith("Carteira inicial: ")) {
                                carteira = Double.parseDouble(linha.replace("Carteira inicial: ", "").replace("€", "").trim());
                            }
                        }

                        Adepto adepto = new Adepto(id, nome, idade, documento, endereco, carteira);
                        adeptos.add(adepto); // Adiciona o adepto à lista do estádio

                    } catch (IOException e) {
                        System.out.println("Erro ao ler informações do adepto " + id + ": " + e.getMessage());
                    }
                }
            }
        }

        System.out.println(adeptos.size() + " adeptos carregados com sucesso.");
    }


    // Getters e Setters
    public String getNome() {
        return nome; // Retorna o nome do estádio
    }

    public void setNome(String nome) {

        this.nome = nome; // Permite alterar o nome do estádio
    }

    public String getLocalizacao() {
        return localizacao; // Retorna a localizaçao do estádio
    }

    public void setLocalizacao(String localizacao) {

        this.localizacao = localizacao; // Permite alterar a localizacao do estádio
    }

    public List<Setor> getSetores() {

        return setores; // Retorna a lista de setores
    }

    public List<Rolote> getRolotes() {

        return rolotes; // Retorna a lista de rolotes
    }

    public List<Adepto> getAdeptos() {

        return adeptos; // Retorna a lista de adeptos
    }

    public Jogo getJogo() {

        return jogo; // Retorna o jogo atual
    }

    public void setJogo(Jogo jogo) {

        this.jogo = jogo; // Permite definir o jogo atual
    }
}

