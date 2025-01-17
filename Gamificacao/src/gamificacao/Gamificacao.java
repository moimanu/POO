package gamificacao;

import java.io.File;
import java.util.Scanner;

public class Gamificacao {

    private static final Scanner t = new Scanner(System.in);

    private static void iniciarMenuEntrada(Sistema sistema) {
        int opcaoEscolhida;
        System.out.print("\nGAMIFICACAO\n1 - Cadastrar\n2 - Entrar\nO que voce quer fazer? ");
        opcaoEscolhida = t.nextInt();
        
        switch (opcaoEscolhida) {
            case 1 -> sistema.iniciarCadastro();
            case 2 -> sistema.iniciarLogin();
            
            default -> {
                System.out.println("\nOpcao invalida, tente novamente...");
                iniciarMenuEntrada(sistema);
            }
        }
        
        //Caso a opcao "0 -  Voltar" seja escolhida:
        if(sistema.getUsuarioLogado() == null) {
            iniciarMenuEntrada(sistema);
        }
    }
    
    public static void main(String[] args) {

        //Inicializacao
        Sistema sistema = new Sistema();
        sistema.carregarUsuarios(new File("C:\\Users\\moise\\Desktop\\usuarios.txt")); //Simulacao
        sistema.carregarAtividades(new File("C:\\Users\\moise\\Desktop\\usuarios.txt")); //Simulacao

        //Cadastro/login
        iniciarMenuEntrada(sistema); //A partir daqui, temos um usuario logado no sistema...
        
        //O usuario logado pode fazer coisas, ate realizar o logout
        System.out.println("\nUsuario Logado: " + sistema.getUsuarioLogado().getNome());
    }
}
