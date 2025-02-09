package gamificacao;

public class Atividade {
    
    private String titulo;
    private String descricao;
    private int valor;

    public Atividade(String titulo, String descricao, int valor) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.valor = valor;
    }
    
    //Overriders

    @Override
    public String toString() {
        return this.titulo + " | " + this.descricao + " | [" + this.valor + " pts]";
    }
    
    //Getters

    public int getValor() {
        return valor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

}
