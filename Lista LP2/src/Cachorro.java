public class Cachorro {
    // Atributos da classe Cachorro
    private String nome;    // Nome do cachorro, como "Rex", "Bolinha"
    private String raca;    // Raça do cachorro, como "Labrador", "Poodle"
    private int idade;      // Idade do cachorro em anos

    // Construtor da classe Cachorro
    public Cachorro(String nome, String raca, int idade) {
        this.nome = nome;     // Inicializa o nome com o valor passado
        this.raca = raca;     // Inicializa a raça com o valor passado
        this.idade = idade;   // Inicializa a idade com o valor passado
    }

    // Ação de latir
    public void latir() {
        System.out.println("O cachorro está latindo...");  // Exibe a mensagem indicando que o cachorro está latindo
    }

    // Ação de correr
    public void correr() {
        System.out.println("O cachorro está correndo...");  // Exibe a mensagem indicando que o cachorro está correndo
    }

    // Ação de comer
    public void comer() {
        System.out.println("O cachorro está comendo...");  // Exibe a mensagem indicando que o cachorro está comendo
    }
}
