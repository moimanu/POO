package trabalholibrary;

import java.util.ArrayList;
import java.util.Scanner;

public class Aluno extends Usuario {
    
    private String matricula;
    private String curso;
    private ArrayList<Emprestimo> emprestimos;

    public Aluno(String nome, String email, String senha, String matricula, String curso, ArrayList<Emprestimo> emprestimos) {
        super(nome, email, senha);
        this.matricula  = matricula;
        this.curso = curso;
        this.emprestimos = emprestimos;
    }

    @Override
    void apresentarOpcoes(Biblioteca biblioteca) {
        Scanner t = new Scanner(System.in);

        System.out.println("\nO que deseja fazer?");
        System.out.println("1 - Conferir disponibilidade de livro");
        System.out.println("2 - Pegar livro");
        System.out.println("3 - Devolver livro");
        System.out.println("4 - Ver meus emprestimos");
        System.out.println("5 - Logout");
        
        int entrada = t.nextInt();
        while(entrada < 1 || entrada > 5){
            System.out.println("Opcao invalida, tente novamente: ");
            entrada = t.nextInt();
        }

        switch(entrada) {
            case 1 -> biblioteca.conferirQntdLivro();
            case 2 -> biblioteca.pegarLivro(5, emprestimos);
            case 3 -> biblioteca.devolverLivro(this, this.emprestimos);
            case 4 -> verEmprestimosAtuais(this.emprestimos);
            case 5 -> logOut(biblioteca);
        }
    }

    @Override
    String retornarCSV() {
        
        String idsEmprestimos = "";
        for(int i = 0; i < emprestimos.size(); i++) {
            if(i == 0) {
                idsEmprestimos += "," + this.emprestimos.get(i).getLivro().getId() + "%" + this.emprestimos.get(i).getDataEmprestimo();
            } else {
                idsEmprestimos += "/" + this.emprestimos.get(i).getLivro().getId() + "%" + this.emprestimos.get(i).getDataEmprestimo();
            }
        }
        
        return "2," + 
                this.nome + "/" +
                this.email + "/" +
                this.senha + "/" +
                this.matricula + "/" +
                this.curso +
                idsEmprestimos;
    }
    
    @Override
    public ArrayList<Emprestimo> getEmprestimos() {
        return this.emprestimos;
    }
}
