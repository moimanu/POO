package gamificacao;

import java.time.LocalDate;

public class Solicitacao implements FormatacaoCSV {
    
    private Membro autor;
    private Atividade atividade;
    private LocalDate dataSolicitacao;
    private int valorSugestao;
    private int situacao; //"0" para "Em aberto" / "1" para "Aprovado com valor original" / "2" para "Aprovado com sugestao" / "3" para "Negado"
    private Diretor diretorResponsavel;

    //Construtor para solicitacao exclusivamente nova
    public Solicitacao(Membro autor, Atividade atividade, int valorSugestao) {
        this.autor = autor;
        this.atividade = atividade;
        this.dataSolicitacao = LocalDate.now();
        this.valorSugestao = valorSugestao;
        this.diretorResponsavel = null;
        this.situacao = 0;
    }
    
    //Construtor para carregamento por arquivo
    public Solicitacao(Membro autor, Atividade atividade, LocalDate dataSolicitacao, int valorSugestao, int situacao, Diretor diretorResponsavel) {
        this.autor = autor;
        this.atividade = atividade;
        this.dataSolicitacao = dataSolicitacao;
        this.valorSugestao = valorSugestao;
        this.diretorResponsavel = diretorResponsavel;
        this.situacao = situacao;
    }
    
    //Overriders

    @Override
    public String toString() {
        return this.dataSolicitacao + 
                " | Autor: " + this.autor.getNome() + 
                " |" + this.atividade.getTitulo() + 
                " | Valor: " + this.atividade.getValor() +
                " | Valor sugerido: " + this.valorSugestao + 
                (this.diretorResponsavel == null ? "" : " | Diretor(a) Responsavel: " + this.diretorResponsavel.getNome()) + 
                (this.situacao == 0 ? " | Em aberto" :
                this.situacao == 1 ? " | Aprovado com valor original" :
                this.situacao == 2 ? " | Aprovado com sugestao" :
                " | Negado");
    }
    
    @Override
    public String retornarCSV() {
        return this.autor.getNome() + "," + this.atividade.getTitulo() + "," + 
                this.dataSolicitacao + "," + this.valorSugestao + "," +
                this.situacao + "," + 
                (this.diretorResponsavel == null ? null : this.diretorResponsavel.getNome());
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
