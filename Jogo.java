package Teste_Final;

import java.util.ArrayList;
import java.util.List;


package Teste_Final;

import java.util.ArrayList;
import java.util.List;

public class Jogo {
    private String nome; // Nome do jogo (e.g., "Casa vs Visitante")
    private String dataHora; // Data e hora do jogo
    private Equipa equipaCasa; // Equipa da casa
    private Equipa equipaVisitante; // Equipa visitante
    private List<Bilhete> bilhetesVendidos; // Lista de bilhetes vendidos
    private int golsCasa; // Gols marcados pela equipa da casa
    private int golsVisitante; // Gols marcados pela equipa visitante

    // Construtor
    public Jogo(String nome, String dataHora, Equipa equipaCasa, Equipa equipaVisitante) {
        this.nome = nome;
        this.dataHora = dataHora;
        this.equipaCasa = equipaCasa;
        this.equipaVisitante = equipaVisitante;
        this.bilhetesVendidos = new ArrayList<>();
        this.golsCasa = 0;
        this.golsVisitante = 0;
    }

    // Metodo para iniciar o jogo
    public void iniciarJogo(Estadio estadio) {
        System.out.println("\n============= Iniciando Jogo =============\n");
        System.out.println(nome);
        System.out.println("Data e hora: " + dataHora);


        System.out.println("\n=========== Primeira Parte ============");
        System.out.println("1' Início do jogo");

        for (int minuto = 1; minuto <= 90; minuto++) {
            // Intervalo aos 45 minutos
            if (minuto == 45) {
                System.out.println("\n============== Intervalo ==============");
                exibirEstatisticasParciais(estadio);
                try {
                    Thread.sleep(5000); // 5 segundos de intervalo
                } catch (InterruptedException e) {
                    System.out.println("Erro no intervalo: " + e.getMessage());
                }
                System.out.println("\n============= Segunda Parte ===========\n");
                System.out.println("46' Recomeço");
            }

            // Probabilidade de gol a cada 10 minutos
            if (minuto % 10 == 0) {
                if (Math.random() < 0.1) { // 10% de chance para equipe da casa
                    golsCasa++;
                    String jogadorCasa = selecionarMarcador(equipaCasa.getPlantel());
                    System.out.println(minuto + "' GOLO! " + equipaCasa.getNome() + " (" + golsCasa + "-" + golsVisitante + ")");
                    System.out.println("Marcador: " + jogadorCasa);
                }
                if (Math.random() < 0.1) { // 10% de chance para equipe visitante
                    golsVisitante++;
                    String jogadorVisitante = selecionarMarcador(equipaVisitante.getPlantel());
                    System.out.println(minuto + "' GOLO! " + equipaVisitante.getNome() + " (" + golsCasa + "-" + golsVisitante + ")");
                    System.out.println("Marcador: " + jogadorVisitante);
                }
            }

            try {
                Thread.sleep(1000); // Cada minuto equivale a 1 segundo
            } catch (InterruptedException e) {
                System.out.println("Erro durante o jogo: " + e.getMessage());
            }
        }

        System.out.println("\nFim do Jogo!!!\n");
        System.out.println("Resultado final: " + getResultado());
        System.out.printf("Receita total: €%.2f%n", calcularReceita());

    }

    // Seleciona um marcador aleatório do plantel
    private String selecionarMarcador(List<String> plantel) {
        if (plantel.isEmpty()) return "Desconhecido";
        int index = (int) (Math.random() * plantel.size());
        return plantel.get(index);
    }

    // Exibe estatísticas parciais no intervalo
    private void exibirEstatisticasParciais(Estadio estadio) {
        System.out.println("\nEstatísticas Parciais:");
        System.out.printf("- Ocupação: %.0f%%%n", (estadio.calcularOcupacao() / 100.0) * 100);
        System.out.println("- Rolotes abertas: " + estadio.getRolotes().stream().filter(Rolote::isAberto).count());
        double faturamentoTotal = estadio.getRolotes().stream()
                .mapToDouble(Rolote::calcularFaturacao).sum();
        System.out.printf("- Faturamento rolotes: €%.2f%n", faturamentoTotal);
    }


    // Metodo para calcular receita total
    public double calcularReceita() {

        double receitaTotal = 0;
        for (Bilhete bilhete : bilhetesVendidos) {
            receitaTotal += bilhete.getPreco();
        }
        return receitaTotal;
    }

    // Metodo para adicionar bilhete à lista de bilhetes vendidos
    public void adicionarBilhete(Bilhete bilhete) {
        bilhetesVendidos.add(bilhete);
    }


    // Metodo para obter o resultado do jogo
    public String getResultado() {
        return equipaCasa.getNome() + " " + golsCasa + " - " + golsVisitante + " " + equipaVisitante.getNome();
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public String getDataHora() {
        return dataHora;
    }

    public Equipa getEquipaCasa() {
        return equipaCasa;
    }

    public Equipa getEquipaVisitante() {
        return equipaVisitante;
    }

    public List<Bilhete> getBilhetesVendidos() {
        return bilhetesVendidos;
    }

    public void venderBilhete(Bilhete bilhete) {
        bilhetesVendidos.add(bilhete);
    }
}


