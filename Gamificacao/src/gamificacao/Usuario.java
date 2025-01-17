package gamificacao;

public class Usuario {

    protected String nome;
    protected String senha;
    
    public Usuario(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }
    
    public void imprimirOpcoesDoUsuario() {
        System.err.println("Erro inesperado ao executar a funcao 'imprimirOpcoesDoUsuario()'");
    }
    
    //Getters:
        
        public String getNome() {
            return this.nome;
        }
        
        public String getSenha() {
            return this.senha;
        }
    //
}
