

public class Produto {
    private int codigo;
    private String nome;
    private double valor;
    private int estoque;

    public Produto(int codigo, String nome, double valor, int estoque) {
        this.codigo = codigo;
        this.nome = nome;
        this.valor = valor;
        this.estoque = estoque;
    }

    // Verifica se tem estoque suficiente
    public boolean verificarEstoque(int quantidadeSolicitada) {
        return this.estoque >= quantidadeSolicitada;
    }

    // Reduz o estoque após uma venda
    public void reduzirEstoque(int quantidadeVendida) {
        if (verificarEstoque(quantidadeVendida)) {
            this.estoque -= quantidadeVendida;
        } else {
            System.out.println("Erro: Estoque insuficiente para reduzir.");
        }
    }

    // Getters
    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }

    public int getEstoque() {
        return estoque;
    }

    // Setters
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
}
