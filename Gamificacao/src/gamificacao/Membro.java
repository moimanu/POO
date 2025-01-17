package gamificacao;

class Membro extends Usuario {

    private int posicaoTabela;
    private int pontos;
    
    public Membro(String nome, String senha) {
        super(nome, senha);
        this.posicaoTabela = 0;
        this.pontos = 0;
    }
}
