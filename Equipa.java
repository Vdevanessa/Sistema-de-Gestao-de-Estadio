package Teste_Final;

import java.util.ArrayList;
import java.util.List;

public class Equipa {
    private String nome; // Nome da equipe
    private String cidade; // Cidade da equipa
    private int dataFundacao; // Ano de fundação
    private List<String> plantel; // Lista de 11 jogadores
    private String treinador; // Nome do treinador

    // Construtor
    public Equipa(String nome, String cidade, int dataFundacao, String treinador) {
        this.nome = nome;
        this.cidade = cidade;
        this.dataFundacao = dataFundacao;
        this.treinador = treinador;
        this.plantel = new ArrayList<>(); // Inicializa lista vazia para o plantel
    }

    // Metodo para adicionar jogadores ao plantel
    public void adicionarJogador(String jogador) {
        if (plantel.size() < 11) {
            plantel.add(jogador);
            System.out.println("Jogador '" + jogador + "' adicionado ao plantel da equipe '" + nome + "'.");
        } else {
            System.out.println("\nO plantel da equipe '" + nome + "' já tem 11 jogadores.");
        }
    }

    // Metodo para exibir o plantel
    public void exibirPlantel() {
        System.out.println("\n Plantel da equipe '" + nome + "':");
        for (String jogador : plantel) {
            System.out.println("- " + jogador);
        }
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getDataFundacao() {
        return dataFundacao;
    }

    public void setDataFundacao(int dataFundacao) {
        this.dataFundacao = dataFundacao;
    }

    public List<String> getPlantel() {
        return plantel;
    }

    public void setPlantel(List<String> plantel) {
        if (plantel.size() <= 11) {
            this.plantel = plantel;
        } else {
            System.out.println("O plantel não pode ter mais de 11 jogadores.");
        }
    }

    public String getTreinador() {
        return treinador;
    }

    public void setTreinador(String treinador) {
        this.treinador = treinador;
    }
}


