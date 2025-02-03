package trabalholibrary;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Usuario {
    
    protected String nome;
    protected String email;
    protected String senha;
    
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
    
    protected void logOut(Biblioteca biblioteca) {
        biblioteca.salvarUsuarios();
        biblioteca.setUsuarioAtivo(null);
    }
    
    abstract void apresentarOpcoes(Biblioteca biblioteca);
  
    protected void verEmprestimosAtuais(ArrayList<Emprestimo> emprestimos) {
        System.out.println("\nMEUS EMPRESTIMOS ATUAIS");
        if(emprestimos.size() == 0) {
            System.out.println("Nao ha emprestimos...");
        } else {
            for(Emprestimo emprestimo : emprestimos) {
                System.out.println(emprestimo.getLivro().getId() + " / " + emprestimo.getLivro().getTitulo() + " / " + emprestimo.getDataEmprestimo());
            }   
        }
    }
    
    abstract String retornarCSV();
    
    //GETTERS
    
        public String getNome() {
            return nome;
        }

        public String getEmail() {
            return email;
        }

        public String getSenha() {
            return senha;
        }
        
        public ArrayList<Emprestimo> getEmprestimos(){
            return null;
        }
}
