public class Lanche {
    // Atributos
    private String nome;
    private String ingredientes;
    private double preco;
    private boolean pronto;

    // Construtor
    public Lanche(String nome, String ingredientes, double preco) {
        this.nome = nome;
        this.ingredientes = ingredientes;
        this.preco = preco;
        this.pronto = false; // O lanche começa como "não pronto"
    }

    // Método para preparar o lanche
    public void preparar() {
        System.out.println("Preparando o lanche: " + nome);
        this.pronto = true;
    }

    // Método para servir o lanche se estiver pronto
    public void servir() {
        if (pronto) {
            System.out.println("O lanche " + nome + " está pronto para ser servido.");
        } else {
            System.out.println("O lanche " + nome + " ainda não foi preparado!");
        }
    }

    // Método para ajustar o preço do lanche
    public void ajustarPreco(double novoPreco) {
        this.preco = novoPreco;
        System.out.println("O novo preço do lanche " + nome + " é R$" + preco);
    }

    // Método para adicionar ingredientes ao lanche
    public void adicionarIngrediente(String novoIngrediente) {
        this.ingredientes += ", " + novoIngrediente;
        System.out.println(novoIngrediente + " foi adicionado ao lanche " + nome);
    }
}
