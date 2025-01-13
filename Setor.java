package Teste_Final;

import java.util.ArrayList;
import java.util.List;

public class Setor {
    private String id;
    private int capacidade;  // 25 lugares
    private boolean[][] lugares; // matriz 5x5
    private double precoBase;

    // construtor
    public Setor (String id, double precoBase){
        this.id = id;
        this.capacidade = 25;
        this.lugares = new boolean[5][5];
        this.precoBase = precoBase;
    }


    // Mostrar o estado atual do setor
    public String mostrarEstadoAtual(int filaSelecionada, int lugarSelecionado) {
        StringBuilder estado = new StringBuilder("Legenda:\n[ ] = Lugar disponível\n[X] = Lugar ocupado\n[S] = Sua seleção atual\n");
        for (int i = 0; i < lugares.length; i++) { // Percorre as filas
            estado.append("\nFila ").append(i + 1).append(": ");
            for (int j = 0; j < lugares[i].length; j++) { // Percorre os lugares em cada fila
                if (i == filaSelecionada - 1 && j == lugarSelecionado - 1) {
                    estado.append("[S]"); // Lugar selecionado
                } else if (lugares[i][j]) {
                    estado.append("[X]"); // Lugar ocupado
                } else {
                    estado.append("[ ]"); // Lugar disponível
                }
            }
        }
        // Exibindo apenas os lugares disponíveis
        estado.append("\n\nLugares disponíveis: ");
        for (int i = 0; i < lugares.length; i++) {
            for (int j = 0; j < lugares[i].length; j++) {
                if (!lugares[i][j]) { // Se o lugar está disponível
                    estado.append((i + 1) * 10 + (j + 1)).append(", "); // Exemplo: 11, 12, 13
                }
            }
        }
        // Remover a última vírgula e espaço
        if (estado.lastIndexOf(", ") == estado.length() - 2) {
            estado.setLength(estado.length() - 2);
        }
        return estado.toString();
    }


    // Metodo para selecionar um lugar
    public boolean selecionarLugar(int fila, int lugar) {
        // Validações
        if (fila < 1 || fila > 5 || lugar < 1 || lugar > 5) {
            System.out.println("Lugar inválido. Selecione um lugar entre 1 e 5 para fila e posição.");
            return false;
        }

        if (lugares[fila - 1][lugar - 1]) { // Verifica se o lugar está ocupado
            System.out.println("Lugar já ocupado. Tente outro.");
            return false;
        }

        lugares[fila - 1][lugar - 1] = true; // Marca o lugar como ocupado
        return true;
    }


    //verificar disponibilidadr

    public int verificarDisponibilidade(){
        int lugaresLivres = 0;
        for(boolean[] fila : lugares){
            for(boolean lugar: fila){
                if(!lugar) {
                    lugaresLivres++;
                }
            }
        }
        return lugaresLivres;
    }

    // atualizar preço
    public void atualizarPrecos(double novoPreco) {
        if (novoPreco < 0) {
            System.out.println("O preço não pode ser negativo.");
            return;
        }
        this.precoBase = novoPreco;
    }

    // Gerar bilhetes para todos os lugares do setor
    public List<Bilhete> gerarBilhetes() {
        List<Bilhete> bilhetes = new ArrayList<>();
        for (int i = 0; i < lugares.length; i++) {
            for (int j = 0; j < lugares[i].length; j++) {
                String lugarId = id + (i * 5 + j + 1); // Exemplo: A1, A2, ...
                bilhetes.add(new Bilhete(lugarId, this, precoBase));
            }
        }
        return bilhetes;
    }

    // Verificar se um lugar está disponível
    public boolean isDisponivel(int fila, int lugar) {
        return !lugares[fila - 1][lugar - 1];
    }

    // Reservar um lugar
    public boolean reservarLugar(int fila, int lugar) {
        if (isDisponivel(fila, lugar)) {
            lugares[fila - 1][lugar - 1] = true;
            return true;
        }
        return false;
    }

    //Getters e Setters
    public String getId(){

        return id;
    }

    public void setId(String id){

        this.id = id;
    }

    public double getPrecoBase(){

        return precoBase;
    }

    public int getCapacidade() {

        return capacidade;
    }

    // Metodo para obter a matriz de lugares
    public boolean[][] getLugares() {
        return lugares;
    }



}
