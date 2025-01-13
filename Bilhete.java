package Teste_Final;

public class Bilhete {
    private String id; // Exemplo: A1, B2, etc.
    private Setor setor; // Setor ao qual o bilhete pertence
    private double preco; // Preço do bilhete

    // Construtor
    public Bilhete(String id, Setor setor, double preco) {
        this.id = id;
        this.setor = setor;
        this.preco = preco;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public Setor getSetor() {
        return setor;
    }

    public double getPreco() {
        return preco;
    }
}
