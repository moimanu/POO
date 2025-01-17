package gamificacao;

import java.util.ArrayList;

class Tabela {
    
    private ArrayList<Membro> competidores;

    public Tabela() {
        competidores = new ArrayList<>();
    }
    
    //Essa funcao procura, em uma ArrayList, quais usuarios sao membros e adiciona aos competidores
    public void preencherTabela(ArrayList<Usuario> usuarios) {
        for(Usuario usuario: usuarios){
            if(usuario.getClass() == Membro.class){
                this.competidores.add((Membro) usuario);
            }
        }
    }
    
    //Essa funcao adiciona um competidor na ultima posciao dos competidores
    public void adicionarCompetidor(Membro membro) {
        competidores.add(membro);
    }
    
    //Preciso de uma funcao para ordenar a ArrayList de acordo com a pontuacao
}
