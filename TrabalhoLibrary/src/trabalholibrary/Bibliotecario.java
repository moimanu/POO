package trabalholibrary;

import java.util.Scanner;

public class Bibliotecario extends Usuario {

    private String telefone;
    private int qntdDevolucoes;    

    public Bibliotecario(String nome, String email, String senha, String telefone, int qntdDevolucoes) {
        super(nome, email, senha);
        this.telefone = telefone;
        this.qntdDevolucoes = qntdDevolucoes;
    }

    @Override
    void apresentarOpcoes(Biblioteca biblioteca) {
        Scanner t = new Scanner(System.in);

        System.out.println("\nO que deseja fazer?");
        System.out.println("1 - Cadastrar usuario");
        System.out.println("2 - Ver emprestimos atuais");
        System.out.println("3 - Ver emprestimos em atrasos");
        System.out.println("4 - Logout");
        
        int entrada = t.nextInt();
        while(entrada < 1 || entrada > 4){
            System.out.println("Opcao invalida, tente novamente: ");
            entrada = t.nextInt();
        }

        switch(entrada) {
            case 1 -> biblioteca.cadastrarUsuario();
            case 2 -> biblioteca.verLivrosEmprestados();
            case 3 -> biblioteca.verEmprestimosAtrasados();
            case 4 -> logOut(biblioteca);
        }
    }

    @Override
    String retornarCSV() {
        return "3," + 
                this.nome + "/" +
                this.email + "/" +
                this.senha + "/" +
                this.telefone + "/" +
                this.qntdDevolucoes; 
    }
}