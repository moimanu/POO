package gamificacao;

import java.util.Scanner;

public class Diretor extends Usuario {

    public Diretor(String nome, String senha, String email, String diretoria) {
        super(nome, senha, email, diretoria);
    }

    @Override
    public void menu(Sistema sistema) {
        Scanner t = new Scanner(System.in);

        System.out.println("\nO que deseja fazer?");
        System.out.println("1 - Cadastrar usuario");
        System.out.println("2 - Ver atividades");        
        System.out.println("3 - Cadastrar atividade");
        System.out.println("4 - Excluir atividade");
        System.out.println("5 - Ver classificacao");
        System.out.println("6 - Processar solicitacao");
        System.out.println("7 - Ver meu historico de processos");
        System.out.println("8 - Ver historico geral");
        System.out.println("9 - Ver todos os usuarios");
        System.out.println("10 - Logout");
        
        int entrada = sistema.conferirEntrada(1, 10);
        
        switch(entrada) {
            case 1 -> sistema.cadastrarUsuario();
            case 2 -> sistema.verAtividades();
            case 3 -> sistema.cadastrarAtividade();
            case 4 -> sistema.excluirAtividade();
            case 5 -> sistema.verClassificacao();
            case 6 -> sistema.processarSolicitacao();
            case 7 -> sistema.verHistoricoPessoal();
            case 8 -> sistema.verHistoricoGeral();
            case 9 -> sistema.verUsuarios();
            case 10 -> sistema.logout();
        }
    }
}
