package gamificacao;

import java.util.ArrayList;
import java.util.Scanner;

public class Gamificacao {

    public static void main(String[] args) {

        //Hard code
        
        Membro membro1 = new Membro("Moises", "123", "email", "Marketing");

        Diretor diretor1 = new Diretor("Cidinha", "123", "email", "Marketing");

        Atividade atividade1 = new Atividade("Participar do MEJ", "Ir aos eventos relacionados a semana do Movimento Empresa Junior", 25);
        
        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios.add(membro1);
        usuarios.add(diretor1);
        
        ArrayList<Atividade> atividades = new ArrayList<>();
        atividades.add(atividade1);
        
        System.out.println("Bem vindo a gamificacao!");
        Sistema sistema = new Sistema(usuarios, atividades, new ArrayList<>());
        
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
