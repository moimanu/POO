package gamificacao;

import java.time.LocalDate;

public class Solicitacao {
    
    private Membro autor;
    private Atividade atividade;
    private LocalDate dataSolicitacao;
    private int valorSugestao;
    private int situacao; //"0" para "Em aberto" / "1" para "Aprovado" / "2" para "Negado"
    private Diretor diretorResponsavel;

    public Solicitacao(Membro autor, Atividade atividade, int valorSugestao) {
        this.autor = autor;
        this.atividade = atividade;
        this.dataSolicitacao = LocalDate.now();
        this.valorSugestao = valorSugestao;
        this.diretorResponsavel = null;
        this.situacao = 0;
    }  
    
    //Overriders

    @Override
    public String toString() {
        return this.dataSolicitacao + 
                " | Autor: " + this.autor.getNome() + 
                " |" + this.atividade.getTitulo() + 
                " | Valor: " + this.atividade.getValor() +
                " | Valor sugerido: " + this.valorSugestao + 
                (this.diretorResponsavel == null ? "" : " | Diretor Responsavel: " + this.diretorResponsavel.getNome()) + 
                (this.situacao == 0 ? " | Em aberto" : this.situacao == 1 ? " | Aprovado" : " | Negado");
    }
    
    //Getters
    
    public Membro getAutor() {
        return autor;
    }

    public Atividade getAtividade() {
        return atividade;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public int getValorSugestao() {
        return valorSugestao;
    }

    public int getSituacao() {
        return situacao;
    }

    public Diretor getDiretorResponsavel() {
        return diretorResponsavel;
    }
    
    //Setters

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }

    public void setDiretorResponsavel(Diretor diretorResponsavel) {
        this.diretorResponsavel = diretorResponsavel;
    }
    
}
