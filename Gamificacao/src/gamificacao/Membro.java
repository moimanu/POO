package gamificacao;

import java.time.LocalDate;
import java.util.Scanner;

public class Membro extends Usuario implements Comparable<Membro> {
    
    private int pontuacao;

    //Construtor para usuario exclusivamente novo    
    public Membro(String nome, String senha, String email,  String diretoria) {
        super(nome, senha, email, diretoria);
        this.pontuacao = 0;
    }

    //Construtor para carregamento por arquivo
    public Membro(String nome, String senha, String email, LocalDate dataCadastro, String diretoria, int pontuacao) {
        super(nome, senha, email, dataCadastro, diretoria);
        this.pontuacao = pontuacao;
    }

    public void alterarPontuacao(int valor) {
        this.pontuacao += valor;
    }
    
    //Overriders
    
    @Override
    public void menu(Sistema sistema) {
        Scanner t = new Scanner(System.in);

        System.out.println("\nO que deseja fazer?");
        System.out.println("1 - Ver atividades");        
        System.out.println("2 - Ver classificacao");
        System.out.println("3 - Ver todos os usuarios");
        System.out.println("4 - Iniciar solicitacao");
        System.out.println("5 - Ver minhas solicitacoes");
        System.out.println("6 - Ver historico geral");
        System.out.println("7 - Logout");
        
        int entrada = sistema.conferirEntrada(1, 7);
        
        switch(entrada) {
            case 1 -> sistema.verAtividades();
            case 2 -> sistema.verClassificacao();
            case 3 -> sistema.verUsuarios();
            case 4 -> sistema.iniciarSolicitacao();
            case 5 -> sistema.verHistoricoPessoal();
            case 6 -> sistema.verHistoricoGeral();
            case 7 -> sistema.logout();
        }
    }
    
    @Override
    public String retornarCSV() {
        return "2," + this.nome + "," + this.senha + "," + this.email + "," + this.dataCadastro + "," + this.diretoria + "," + this.pontuacao;
    }
    
    @Override
    public int compareTo(Membro outroUsuario) {
        return this.pontuacao - outroUsuario.getPontuacao();
    }
    
    //Getters

    public int getPontuacao() {
        return pontuacao;
    }
    
}
