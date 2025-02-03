package trabalholibrary;

import java.util.ArrayList;
import java.util.Scanner;

public class Professor extends Usuario {

    private String departamento;
    protected ArrayList<Emprestimo> emprestimos;

    public Professor(String nome, String email, String senha, String departamento, ArrayList<Emprestimo> emprestimos) {
        super(nome, email, senha);
        this.departamento = departamento;
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
            case 2 -> biblioteca.pegarLivro(10, emprestimos);
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
        
        return "1," + 
                this.nome + "/" +
                this.email + "/" +
                this.senha + "/" +
                this.departamento +
                idsEmprestimos;    
    }
    
    @Override
    public ArrayList<Emprestimo> getEmprestimos() {
        return this.emprestimos;
    }
}
