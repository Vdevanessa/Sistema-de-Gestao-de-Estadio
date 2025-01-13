package Teste_Final;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Adepto {
    private String id; // Ex.: AD001, AD002...
    private String nome;
    private int idade;
    private String documento; // Ex.: Número de identificação
    private String endereco;
    private double carteira; // Dinheiro disponível
    private List<Bilhete> bilhetes; // Lista de bilhetes comprados

    // Construtor
    public Adepto(String id, String nome, int idade, String documento, String endereco, double carteira) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.documento = documento;
        this.endereco = endereco;
        this.carteira = carteira;
        this.bilhetes = new ArrayList<>(); // Inicializa lista vazia de bilhetes
    }

    // Metodo para criar um novo adepto
    public static Adepto criarAdepto(Scanner scanner) {
        String diretorioBase = "src/Teste_Final/Adeptos";
        File pastaBase = new File(diretorioBase);

        // Listar subdiretórios e identificar o último ID
        int ultimoId = 0;
        if (pastaBase.exists() && pastaBase.isDirectory()) {
            File[] subdiretorios = pastaBase.listFiles(File::isDirectory);
            if (subdiretorios != null) {
                for (File subdiretorio : subdiretorios) {
                    String nomeDiretorio = subdiretorio.getName(); // Nome do diretório
                    if (nomeDiretorio.startsWith("AD")) {
                        try {
                            int idAtual = Integer.parseInt(nomeDiretorio.substring(2)); // Extrai o número do ID
                            if (idAtual > ultimoId) {
                                ultimoId = idAtual; // Atualiza o maior ID encontrado
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Diretório inválido ignorado: " + nomeDiretorio);
                        }
                    }
                }
            }
        }

        // Gerar o próximo ID
        String idAdepto = "AD" + String.format("%03d", ultimoId + 1);
        System.out.println("\nCriando novo adepto...");
        System.out.println("ID: " + idAdepto);

        // Solicitar informações do adepto
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        while (nome.trim().isEmpty()) {
            System.out.print("O nome não pode estar vazio. Digite novamente: ");
            nome = scanner.nextLine();
        }

        System.out.print("Idade: ");
        int idade = -1;
        while (idade <= 0) {
            if (scanner.hasNextInt()) {
                idade = scanner.nextInt();
                if (idade <= 0) {
                    System.out.print("Idade deve ser um número positivo. Tente novamente: ");
                }
            } else {
                System.out.print("Por favor, insira um número válido para a idade: ");
            }
            scanner.nextLine(); // Consumir a quebra de linha
        }

        System.out.print("CC (Cartão de Cidadão): ");
        String documento;
        while (true) {
            documento = scanner.nextLine();
            if (documento.matches("\\d{9}")) {
                break;
            } else {
                System.out.print("Erro: O CC deve conter exatamente 9 dígitos. Tente novamente: ");
            }
        }

        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        while (endereco.trim().isEmpty()) {
            System.out.print("O endereço não pode estar vazio. Digite novamente: ");
            endereco = scanner.nextLine();
        }

        System.out.print("Carteira inicial (€): ");
        double carteira = -1;
        while (carteira < 0) {
            if (scanner.hasNextDouble()) {
                carteira = scanner.nextDouble();
                if (carteira < 0) {
                    System.out.print("A carteira não pode ter um valor negativo. Tente novamente: ");
                }
            } else {
                System.out.print("Por favor, insira um valor válido para a carteira: ");
            }
            scanner.nextLine(); // Consumir a quebra de linha
        }

        System.out.println("\nAdepto criado com sucesso!");
        return new Adepto(idAdepto, nome, idade, documento, endereco, carteira);


    }


    // Criar o diretório do adepto e salvar informações no ficheiro
    public void registrarAdepto() {
        // Diretório base para todos os adeptos
        String diretorioBase = "src" + File.separator + "Teste_Final" + File.separator + "Adeptos";
        File pastaBase = new File(diretorioBase);

        // Criar o diretório base se necessário
        if (!pastaBase.exists() && pastaBase.mkdirs()) {
            System.out.println("Diretório base criado: " + diretorioBase);
        } else if (!pastaBase.exists()) {
            System.out.println("Erro ao criar o diretório base: " + diretorioBase);
            return; // Interrompe se o diretório base não puder ser criado
        }

        // Subdiretório para o adepto
        String diretorioAdepto = diretorioBase + File.separator + id;
        File pastaAdepto = new File(diretorioAdepto);

        if (pastaAdepto.mkdirs()) {
            System.out.println("Subdiretório criado: " + diretorioAdepto);
        } else if (pastaAdepto.exists()) {
            System.out.println("Subdiretório já existente: " + diretorioAdepto);
        } else {
            System.out.println("Erro ao criar o subdiretório: " + diretorioAdepto);
            return; // Interrompe se o subdiretório não puder ser criado
        }

        // Ficheiro com as informações do adepto
        String ficheiro = diretorioAdepto + File.separator + "info.txt";
        try (FileWriter writer = new FileWriter(ficheiro)) {
            writer.write("ID: " + id + "\n");
            writer.write("Nome: " + nome + "\n");
            writer.write("Idade: " + idade + "\n");
            writer.write("CC: " + documento + "\n");
            writer.write("Endereço: " + endereco + "\n");
            writer.write("Carteira inicial: " + carteira + "€\n");
            System.out.println("Ficheiro criado: " + ficheiro);
        } catch (IOException e) {
            System.out.println("Erro ao criar o ficheiro: " + e.getMessage());
        }
    }

    public static Adepto carregarAdepto(String idAdepto) {
        String diretorioAdepto = "src/Teste_Final/Adeptos/" + idAdepto.toUpperCase();
        File pastaAdepto = new File(diretorioAdepto);

        if (pastaAdepto.exists() && pastaAdepto.isDirectory()) {
            try {
                // Ler informações do arquivo info.txt
                File ficheiroInfo = new File(diretorioAdepto + "/info.txt");
                Scanner leitor = new Scanner(ficheiroInfo);
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
                leitor.close();

                // Retorna uma instância do adepto carregado
                return new Adepto(idAdepto.toUpperCase(), nome, idade, documento, endereco, carteira);

            } catch (IOException e) {
                System.out.println("Erro ao carregar o adepto: " + e.getMessage());
            }
        }

        // Caso o diretório ou ficheiro não exista, retorna null
        System.out.println("Adepto não encontrado.");
        return null;
    }



    // Exibir resumo dos adeptos criados
    public void exibirResumo() {
        System.out.printf("\n- ID: %s", id);
        System.out.printf("\n- Nome: %s", nome);
        System.out.printf("\n- Idade: %d", idade);
        System.out.printf("\n- CC: %s", documento);
        System.out.printf("\n- Carteira: %.2f€%n", carteira);

    }


    // Metodo para comprar bilhete
    public boolean comprarBilhete(Scanner scanner, Estadio estadio, Jogo jogo) {


        // Quantidade de bilhetes
        System.out.print("\nQuantos bilhetes quer? ");
        int quantidadeBilhetes = scanner.nextInt();
        scanner.nextLine(); // Consumir quebra de linha

        System.out.println("\nSetores disponíveis:");
        for (Setor setor : estadio.getSetores()) {
            System.out.printf("%s - %.2f€ [%d/%d lugares]%n",
                    setor.getId(), setor.getPrecoBase(), setor.verificarDisponibilidade(), setor.getCapacidade());
        }

        // Escolha do setor
        System.out.print("\nSeleção do setor: ");
        String setorEscolhido = scanner.nextLine().toUpperCase();

        Setor setorSelecionado = null;
        for (Setor setor : estadio.getSetores()) {
            if (setor.getId().equalsIgnoreCase(setorEscolhido)) {
                setorSelecionado = setor;
                break;
            }
        }

        if (setorSelecionado == null) {
            System.out.println("Setor inválido. Compra cancelada.");
            return false;
        }

        // Exibir estado inicial do setor
        System.out.println("\n===== Estado inicial do Setor " + setorSelecionado.getId() + " =====");
        System.out.println(setorSelecionado.mostrarEstadoAtual(-1, -1)); // Sem seleção inicial

        List<Bilhete> bilhetesSelecionados = new ArrayList<>();
        double totalPreco = 0.0;

        for (int i = 1; i <= quantidadeBilhetes; i++) {
            System.out.printf("\nSelecione o lugar #%d: ", i);
            String lugarSelecionado = scanner.nextLine();

            // Verificar se a entrada tem o formato correto (exemplo: "11", "12", "23", "25", etc.)
            if (lugarSelecionado.length() != 2 || !lugarSelecionado.matches("\\d{2}")) {
                System.out.println("Formato inválido. Tente novamente.");
                i--; // Permitir nova tentativa
                continue;
            }

            int fila = Integer.parseInt(lugarSelecionado.substring(0, 1));  // Primeiro dígito (linha)
            int lugar = Integer.parseInt(lugarSelecionado.substring(1, 2)); // Segundo dígito (coluna)

            if (lugar < 1 || lugar > 5) {
                System.out.println("Lugar inválido. Tente novamente.");
                i--; // Permitir nova tentativa
                continue;
            }

            if (!setorSelecionado.reservarLugar(fila, lugar)) {
                System.out.println("Lugar já ocupado. Tente novamente.");
                i--; // Permitir nova tentativa
                continue;
            }

            Bilhete bilhete = new Bilhete(lugarSelecionado, setorSelecionado, setorSelecionado.getPrecoBase());
            bilhetesSelecionados.add(bilhete);

            totalPreco += bilhete.getPreco();

            System.out.println("\n====== Estado atualizado do Setor " + setorSelecionado.getId() + " =======");
            System.out.println(setorSelecionado.mostrarEstadoAtual(fila, lugar));
        }

        System.out.println("\nProcessando pagamento...");
        try {
            Thread.sleep(2000); // Espera 2 segundos
        } catch (InterruptedException e) {
            System.out.println("Erro ao pausar a execução: " + e.getMessage());
        }

        System.out.printf("\nCarteira anterior: %.2f€%n", carteira);

        if (carteira >= totalPreco) {
            carteira -= totalPreco;

            System.out.printf("Pagamento efetuado: -%.2f€%n", totalPreco);
            System.out.printf("Carteira atual: %.2f€%n", carteira);
            bilhetes.addAll(bilhetesSelecionados);
            System.out.println("\nCompra realizada com sucesso!\n");

            for (Bilhete bilhete : bilhetesSelecionados) {
                jogo.adicionarBilhete(bilhete); // Atualiza a lista de bilhetes vendidos no Jogo
            }

            for (Bilhete bilhete : bilhetesSelecionados) {
                salvarBilhete(bilhete);
                adicionarBilhete(bilhete);
                jogo.adicionarBilhete(bilhete);
            }
            return true;
        } else {
            System.out.println("\nSaldo insuficiente. A compra foi cancelada.");
            for (Bilhete bilhete : bilhetesSelecionados) {
                String lugarId = bilhete.getId();
                int fila = (Integer.parseInt(lugarId.substring(1)) - 1) / 5 + 1;
                int lugar = (Integer.parseInt(lugarId.substring(1)) - 1) % 5 + 1;
                setorSelecionado.reservarLugar(fila, lugar); // Liberar o lugar
            }
            return false;
        }
    }

    // Adicionar bilhete ao adepto
    public void adicionarBilhete(Bilhete bilhete) {
        bilhetes.add(bilhete);
    }

    // Metodo para obter a quantidade de bilhetes
    public int getQuantidadeBilhetes() {
        return bilhetes.size();
    }


    // Salvar bilhete no diretório do adepto
    public void salvarBilhete(Bilhete bilhete) {
        String diretorioAdepto = "src/Teste_Final/Adeptos/" + id; // Diretório do adepto
        String ficheiroBilhete = diretorioAdepto + "/" + bilhete.getId() + ".txt"; // Nome do ficheiro

        try (FileWriter writer = new FileWriter(ficheiroBilhete)) {
            writer.write("ID do Adepto: " + id + "\n");
            writer.write("Setor: " + bilhete.getSetor().getId() + "\n");
            writer.write("Lugar: " + bilhete.getId() + "\n");
            writer.write("Preço: " + bilhete.getPreco() + "€\n");
            writer.write("Data da Compra: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n");
            System.out.println("Bilhete salvo: " + ficheiroBilhete);
        } catch (IOException e) {
            System.out.println("Erro ao salvar bilhete: " + e.getMessage());
        }
    }


    // Metodo para comprar comida de uma rolote
    public void comprarComida(List<Rolote> rolotes, Scanner scanner, Estadio estadio) {
        System.out.println("\n============= Comprar Comida ============\n");

        // Selecionar uma rolote
        if (!estadio.getRolotes().isEmpty()) {
            System.out.println("\nRolotes Disponíveis:");
            for (Rolote rolote : estadio.getRolotes()) {
                // Adicionar o ID e o Nome da rolote
                System.out.printf("\nID: %d | Nome: %s%n", rolote.getId(), rolote.getNome());
            }
        } else {
            System.out.println("Nenhuma rolote registrada.");
        }

        //escolher a rolote
        System.out.print("\nDigite o ID da rolote: ");
        int idRolote = scanner.nextInt();
        scanner.nextLine(); // Consumir quebra de linha

        Rolote roloteSelecionada = null;
        for (Rolote rolote : estadio.getRolotes()) {
            if (rolote.getId() == idRolote) {
                roloteSelecionada = rolote;
                break;
            }
        }

        if (roloteSelecionada == null) {
            System.out.println("Rolote não encontrada. Compra cancelada.");
            return;
        }

        // Exibir produtos disponíveis
        System.out.println("\nProdutos disponíveis na Rolote selecionada:\n");
        for (Produto produto : roloteSelecionada.getProdutos()) {
            System.out.printf("\nID %d - %s: %.2f€ (Stock: %d)%n", produto.getId() ,produto.getNome(), produto.getPreco(), produto.getQuantidadeStock());
        }

        // Selecionar produtos
        List<Produto> produtosComprados = new ArrayList<>();
        boolean continuar = true;

        while (continuar) {
            System.out.print("\nDigite o ID do produto que deseja comprar: ");
            int idProduto = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            Produto produtoSelecionado = null;
            for (Produto produto : roloteSelecionada.getProdutos()) {
                if (produto.getId() == idProduto) {
                    produtoSelecionado = produto;
                    break;
                }
            }

            if (produtoSelecionado == null) {
                System.out.println("Produto não encontrado.");
                continue;
            }

            // Escolher a quantidade do produto
            int quantidade = 0;
            boolean quantidadeValida = false;
            while (!quantidadeValida) {
                System.out.print("Qual a quantidade? ");
                quantidade = scanner.nextInt();
                scanner.nextLine(); // Consumir quebra de linha

                if (quantidade > 0 && quantidade <= produtoSelecionado.getQuantidadeStock()) {
                    quantidadeValida = true;
                } else {
                    System.out.println("Erro: Quantidade inválida. Verifique o estoque disponível.");
                }
            }

            // Adicionar o produto comprado com a quantidade
            for (int i = 0; i < quantidade; i++) {
                produtosComprados.add(produtoSelecionado);
            }

            System.out.print("Deseja adicionar mais produtos ao carrinho? (s/n): ");
            continuar = scanner.nextLine().equalsIgnoreCase("s");
        }

        // Calcular o custo total
        double totalPreco = 0;
        for (Produto produto : produtosComprados) {
            totalPreco += produto.getPreco();
        }

        // Processar a compra
        if (carteira >= totalPreco) {
            carteira -= totalPreco; // Deduz o valor da carteira
            roloteSelecionada.vendeProduto(produtosComprados); // Atualiza o stock na rolote
            System.out.printf("\nCompra realizada com sucesso! Total: %.2f€. Saldo restante: %.2f€.%n", totalPreco, carteira);
        } else {
            System.out.println("\nSaldo insuficiente. Compra cancelada.");
        }
    }


    // Metodo para consultar o gasto total
    public double consultarGastoTotal() {
        double totalGasto = 0;
        for (Bilhete bilhete : bilhetes) {
            totalGasto += bilhete.getPreco(); // Soma os preços de todos os bilhetes
        }
        return totalGasto;
    }

    // Getters e Setters
    public String getId() {

        return id;
    }

    public String getNome() {

        return nome;
    }

    public int getIdade(){

        return idade;
    }

    public String getDocumento(){

        return documento;
    }

    public void setNome(String nome) {

        this.nome = nome;
    }

    public double getCarteira() {

        return carteira;
    }

    public void setCarteira(double carteira) {

        this.carteira = carteira;
    }

    public List<Bilhete> getBilhetes() {

        return bilhetes;
    }
}