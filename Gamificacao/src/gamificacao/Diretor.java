package gamificacao;

import java.util.ArrayList;

public class Diretor extends Usuario {

    public Diretor(String nome, String senha) {
        super(nome, senha);
    }
    
    @Override
    public void imprimirOpcoesDoUsuario() {
        System.out.println("O que deseja fazer?"); // <- PAREI AQUI
    }
    
    public void cadastrarAtividade(String descricao, int valor, ArrayList atividades) {
        
        atividades.add(new Atividade(descricao, valor));
    }
}
