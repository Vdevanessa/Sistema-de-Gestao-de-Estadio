package Teste_Final;

public class Produto {
    // Atributos
    private int id;
    private String nome;
    private double preco;
    private int quantidadeStock;

    // Construtor
    public Produto(int id, String nome, double preco, int quantidadeStock) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidadeStock = quantidadeStock;
    }

    @Override
    public String toString() {
        return String.format("%s - %.2f€ (stock: %d)", nome, preco, quantidadeStock);
    }


    // Getters e Setters
    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {

        this.nome = nome;
    }

    public double getPreco() {

        return preco;
    }

    public void setPreco(double preco) {

        this.preco = preco;
    }

    public int getQuantidadeStock() {

        return quantidadeStock;
    }

    public void setQuantidadeStock(int quantidadeStock) {

        this.quantidadeStock = quantidadeStock;
    }
}