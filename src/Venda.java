
import java.time.LocalDate;

public class Venda {
    private LocalDate data;
    private Produto produto;
    private int quantidade;

    public Venda(LocalDate data, Produto produto, int quantidade) {
        this.data = data;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    // Calcula o valor total da venda
    public double getValorTotal() {
        return produto.getValor() * quantidade;  // Usando o getter do produto
    }

    // Getters
    public LocalDate getData() {
        return data;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    // Setters
    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
