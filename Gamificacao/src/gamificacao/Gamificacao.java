package gamificacao;

public class Gamificacao {
    
    /* Resumo da main:
    
        novo sistema
        while(true){
            1 - realiza login
                while(usuarioAtivo != null){
                    apresenta menu do usuario ativo;
                }
            2 - fecha programa
        }
    */

    public static void main(String[] args) {

        Sistema sistema = new Sistema();
        sistema.conferirValidadeCompeticao();
        System.out.println("Bem vindo a gamificacao!");
        
        while(true) {
            
            System.out.println("\nO que deseja fazer?\n1 - Login\n2 - Fechar Programa");
            
            int entrada = sistema.conferirEntrada(1, 2);
                    
            switch(entrada) {
                case 1 -> {
                    sistema.login();
                    while(sistema.getUsuarioAtivo() != null) {
                        sistema.getUsuarioAtivo().menu(sistema);
                    }
                }
                case 2 -> System.exit(0);
            }
        }
    }
}
