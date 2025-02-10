package gamificacao;

import java.util.ArrayList;
import java.util.Scanner;

public class Gamificacao {

    public static void main(String[] args) {

        System.out.println("Bem vindo a gamificacao!");
        Sistema sistema = new Sistema(new ArrayList<>());
        
        while(true) {
            
            Scanner t = new Scanner(System.in);
            System.out.println("\nO que deseja fazer?\n1 - Login\n2 - Fechar Programa");
            
            int entrada = t.nextInt();
            while(entrada < 1 || entrada > 2) {
                System.out.print("Opcao invalida, tente novamente: ");
                entrada = t.nextInt();
            }
            
            switch(entrada) {
                case 1 -> sistema.login();
                case 2 -> System.exit(0);
            }
            
            while(sistema.getUsuarioAtivo() != null) {
                sistema.getUsuarioAtivo().menu(sistema);
            }
        }
    }
}
