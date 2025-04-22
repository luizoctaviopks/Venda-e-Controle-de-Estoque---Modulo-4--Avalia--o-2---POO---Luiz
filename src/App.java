import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class App {
    static Scanner scanner = new Scanner(System.in);
    static List<Produto> produtos = new ArrayList<>();
    static List<Venda> vendas = new ArrayList<>();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\nMenu:");
            System.out.println("1 - Incluir produto:");
            System.out.println("2 - Consultar produto:");
            System.out.println("3 - Listagem de produtos:");
            System.out.println("4 - Vendas por período - detalhado:");
            System.out.println("5 - Realizar venda:");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1 -> incluirProduto();
                case 2 -> consultarProduto();
                case 3 -> listarProdutos();
                case 4 -> vendasPorPeriodo();
                case 5 -> realizarVenda();
                case 0 -> System.out.println("Programa Finalizado!");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    static void incluirProduto() {
        System.out.print("Código: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Valor: ");
        double valor = scanner.nextDouble();
        System.out.print("Quantidade em estoque: ");
        int estoque = scanner.nextInt();
        produtos.add(new Produto(codigo, nome, valor, estoque));

        System.out.println("--------------------------");

        System.out.println("Produto incluído com sucesso!");
    }

    static void consultarProduto() {
        // Verifica se há produtos cadastrados
        if (produtos.isEmpty()) {
            System.out.println("Não há produtos cadastrados. Cadastre um produto primeiro.");
            return;             
        }

        System.out.print("Informe o código do produto: ");
        int codigo = scanner.nextInt();
        for (Produto p : produtos) {
            if (p.getCodigo() == codigo) {
                System.out.println("--------------------------");
                System.out.printf("Código: %d, Nome: %s, Valor: %.2f, Estoque: %d\n", p.getCodigo(), p.getNome(), p.getValor(), p.getEstoque());
                return;
            }
        }

        System.out.println("--------------------------");

        System.out.println("Produto não encontrado.");
    }

    static void listarProdutos() {
        // Verifica se há vendas realizadas
        if (produtos.isEmpty()) {
            System.out.println("Não há produtos cadastrados. Cadastre um produto primeiro.");
            return;
        }

        System.out.println("=== LISTAGEM DE PRODUTOS ===");
        double soma = 0, max = Double.MIN_VALUE, min = Double.MAX_VALUE;

        for (Produto p : produtos) {
            System.out.printf("Código: %d | Nome: %s | Valor: %.2f | Estoque: %d\n", p.getCodigo(), p.getNome(), p.getValor(), p.getEstoque());
            soma += p.getValor();
            if (p.getValor() > max) max = p.getValor();
            if (p.getValor() < min) min = p.getValor();
        }

        if (!produtos.isEmpty()) {
            double media = soma / produtos.size();
            System.out.printf("\nValor Médio: %.2f | Máximo: %.2f | Mínimo: %.2f\n", media, max, min);
        } else {

            System.out.println("--------------------------");

            System.out.println("Nenhum produto cadastrado.");
        }
    }

   
static void vendasPorPeriodo() {
    // Verifica se há produtos cadastrados
    if (vendas.isEmpty()) {
        System.out.println("Nenhuma venda foi realizada. Faça uma venda primeiro.");
        return;
    }
    LocalDate dataInicio = null;
    while (dataInicio == null) {
        System.out.print("Data inicial (dd/mm/aaaa): ");
        String inputInicio = scanner.nextLine();
        if (!inputInicio.isEmpty()) {
            try {
                dataInicio = LocalDate.parse(inputInicio, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Tente novamente.");
            }
        } else {
            System.out.println("Data inicial não pode ser vazia. Tente novamente.");
        }
    }

    
    LocalDate dataFim = null;
    while (dataFim == null) {
        System.out.print("Data final (dd/mm/aaaa): ");
        String inputFim = scanner.nextLine();
        if (!inputFim.isEmpty()) {
            try {
                dataFim = LocalDate.parse(inputFim, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Tente novamente.");
            }
        } else {
            System.out.println("Data final não pode ser vazia. Tente novamente.");
        }
    }

    
    System.out.println("=== RELATÓRIO DE VENDAS DETALHADO ===");
    System.out.printf("Período: %s a %s\n\n", dataInicio.format(formatter), dataFim.format(formatter));

    double soma = 0;
    int totalVendas = 0;

    
    for (Venda v : vendas) {        
        if (!v.getData().isBefore(dataInicio) && !v.getData().isAfter(dataFim)) {
            double total = v.getValorTotal();
            System.out.printf("Data: %s | Produto: %s | Qtd: %d | Valor Unit: %.2f | Total: %.2f\n",
                    v.getData().format(formatter), v.getProduto().getNome(), v.getQuantidade(), v.getProduto().getValor(), total);
            soma += total;
            totalVendas++;
        }
    }

    
    if (totalVendas > 0) {
        System.out.printf("\nValor médio das vendas: %.2f\n", soma / totalVendas);
    } else {

        System.out.println("--------------------------");

        System.out.println("Nenhuma venda no período.");
    }
}
    static void realizarVenda() {
        // Verifica se há produtos cadastrados
        if (produtos.isEmpty()) {
            System.out.println("Não há produtos cadastrados. Cadastre um produto primeiro.");
            return; 
        }

        System.out.print("Código do produto: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        Produto produto = null;
        for (Produto p : produtos) {
            if (p.getCodigo() == codigo) {
                produto = p;
                break;
            }
        }

        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        System.out.print("Quantidade a vender: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        // Verifica se tem estoque suficiente
        if (!produto.verificarEstoque(quantidade)) {
            System.out.println("Estoque insuficiente para realizar a venda.");
            return;
        }

        System.out.print("Data da venda (dd/mm/aaaa): ");
        LocalDate data = LocalDate.parse(scanner.nextLine(), formatter);

        produto.reduzirEstoque(quantidade); // Atualiza o estoque
        vendas.add(new Venda(data, produto, quantidade));

        System.out.println("--------------------------");
        System.out.println("Venda realizada com sucesso!");
    }
}