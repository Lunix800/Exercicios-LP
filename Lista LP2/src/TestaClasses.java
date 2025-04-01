import java.util.Scanner;

public class TestaClasses {
    public static void main(String[] args) {
        // Instanciando objetos
        Pato pato = new Pato("Pato Real", "Branco", 2.5);
        Carro carro = new Carro("Ford", "Fusion", 2020);
        Cachorro cachorro = new Cachorro("Rex", "Labrador", 4);
        Vaca vaca = new Vaca("Holandesa", "Branca e Preta", 550.0);
        Moto moto = new Moto("Yamaha", "R1", 1000);
        Celular celular = new Celular("Samsung", "Galaxy S21", 4000);
        Livro livro = new Livro("O Alquimista", "Paulo Coelho", 198);
        Galinha galinha = new Galinha("Caipira", "Amarela", 3.0);
        Gato gato = new Gato("Siamês", "Cinza", 4.5);
        Lanche lanche = new Lanche("X-Burguer", "Carne, Queijo, Pão", 15.0);

        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== MENU DE TESTES ===");
            System.out.println("1. Testar Classe Pato");
            System.out.println("2. Testar Classe Carro");
            System.out.println("3. Testar Classe Cachorro");
            System.out.println("4. Testar Classe Vaca");
            System.out.println("5. Testar Classe Moto");
            System.out.println("6. Testar Classe Celular");
            System.out.println("7. Testar Classe Livro");
            System.out.println("8. Testar Classe Galinha");
            System.out.println("9. Testar Classe Gato");
            System.out.println("10. Testar Classe Lanche");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("\nTestando Pato:");
                    pato.nadar();
                    pato.voar();
                    pato.grasnar();
                    break;
                case 2:
                    System.out.println("\nTestando Carro:");
                    carro.acelerar();
                    carro.frear();
                    carro.ligar();
                    break;
                case 3:
                    System.out.println("\nTestando Cachorro:");
                    cachorro.latir();
                    cachorro.correr();
                    cachorro.comer();
                    break;
                case 4:
                    System.out.println("\nTestando Vaca:");
                    vaca.mugir();
                    vaca.produzirLeite();
                    vaca.comer();
                    break;
                case 5:
                    System.out.println("\nTestando Moto:");
                    moto.acelerar();
                    moto.frear();
                    moto.empinar();
                    break;
                case 6:
                    System.out.println("\nTestando Celular:");
                    celular.ligar();
                    celular.tirarFoto();
                    celular.carregarBateria();
                    break;
                case 7:
                    System.out.println("\nTestando Livro:");
                    livro.abrir();
                    livro.folhear();
                    livro.ler();
                    break;
                case 8:
                    System.out.println("\nTestando Galinha:");
                    galinha.cacarejar();
                    galinha.botarOvo();
                    galinha.ciscar();
                    break;
                case 9:
                    System.out.println("\nTestando Gato:");
                    gato.miar();
                    gato.arranhar();
                    gato.pular();
                    break;
                case 10:
                    System.out.println("\nTestando Lanche:");
                    lanche.preparar();
                    lanche.servir();
                    lanche.ajustarPreco(18.0);
                    lanche.adicionarIngrediente("Bacon");
                    break;
                case 0:
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        scanner.close();
    }
}
