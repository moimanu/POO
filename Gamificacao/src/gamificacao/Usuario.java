package gamificacao;

import java.time.LocalDate;

public abstract class Usuario {
    
    protected String nome;
    protected String senha;
    protected String email;
    protected final LocalDate dataCadastro = LocalDate.now();
    protected String diretoria;

    public Usuario(String nome, String senha, String email, String diretoria) {
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.diretoria = diretoria;
    }
    
    public abstract void menu();
    
}
