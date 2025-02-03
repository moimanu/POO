package trabalholibrary;

import java.util.Scanner;

public class TrabalhoLibrary {

    public static void main(String[] args) {

        System.out.println("Bem vindo a biblioteca!");
        Biblioteca biblioteca = new Biblioteca();
        
        while(true) {
            
            Scanner t = new Scanner(System.in);
            System.out.println("\nO que deseja fazer?\n1 - Login\n2 - Fechar Programa");
            
            int entrada = t.nextInt();
            while(entrada < 1 || entrada > 2) {
                System.out.print("Opcao invalida, tente novamente: ");
                entrada = t.nextInt();
            }
            
            switch(entrada) {
                case 1 -> biblioteca.login();
                case 2 -> System.exit(0);
            }
            
            while(biblioteca.getUsuarioAtivo() != null) {
                biblioteca.getUsuarioAtivo().apresentarOpcoes(biblioteca);
            }
        }
    }
}
