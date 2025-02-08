package gamificacao;

import java.time.LocalDate;

public class Solicitacao {
    
    private Membro autor;
    private Atividade atividade;
    private LocalDate dataSolicitacao;
    private int valorSugestao;
    private int situacao; //"0" para "Em aberto" / "1" para "Aprovado" / "2" para "Negado"
    private Diretor diretorResponsavel;

    public Solicitacao(Membro autor, Atividade atividade, LocalDate dataSolicitacao, int valorSugestao) {
        this.autor = autor;
        this.atividade = atividade;
        this.dataSolicitacao = dataSolicitacao;
        this.valorSugestao = valorSugestao;
        this.diretorResponsavel = null;
        this.situacao = 0;
    }  
    
}
