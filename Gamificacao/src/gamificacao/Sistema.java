package gamificacao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Sistema {
    
    ArrayList<Usuario> usuarios;
    ArrayList<Atividade> atividades;
    ArrayList<Solicitacao> solicitacoes;
    Usuario usuarioAtivo;
    Scanner t = new Scanner(System.in);

    public Sistema(ArrayList<Usuario> usuarios, ArrayList<Atividade> atividades, ArrayList<Solicitacao> solicitacoes) {
        this.usuarios = usuarios;
        this.atividades = atividades;
        this.solicitacoes = solicitacoes;
        this.usuarioAtivo = null;
    }
    
    //Carregamento e salvamento
    
    public void carregarUsuarios() {
        
    }
    
    public void carregarAtividades() {
        
    }
    
    public void carregarSolicitacoes() {
        
    }
    
    public void salvar() {
        
    }
    
    //Metodos
    
    public int conferirEntrada(int vInicial, int vFinal) {
        int entrada = t.nextInt();
        while(entrada < vInicial || entrada > vFinal) {
            System.out.print("Invalido, tente novamente: ");
            entrada = t.nextInt();
        }
        return entrada;
    }
    
    public Usuario retornarUsuario(String nome) {
        for(Usuario usuario : this.usuarios) {
            if(usuario.getNome().equals(nome)) {
                return usuario;
            }
        }
        return null;
    }
    
    //Ferramentas do sistema
    
    public void verHistoricoGeral() {
    
        System.out.println("\nHISTORICO DE SOLICITACOES(GERAL):");
        
        if(this.solicitacoes.isEmpty()) {
            System.out.println("Nao ha solicitacoes...");
        } else {
            
            boolean existeAprovado = false;
            for(Solicitacao solicitacao : this.solicitacoes) {
                if(solicitacao.getSituacao() == 1) {
                    System.out.println("\n" + solicitacao);
                    existeAprovado = true;
                }
            }
            
            if(existeAprovado == false) {
                System.out.println("Nao ha solicitacoes para visualizar.");
            }
        }
    }
    
    public void verHistoricoPessoal() {
        System.out.println("\nMEU HISTORICO DE SOLICITACOES");
        
        if(this.solicitacoes.isEmpty()) {
            System.out.println("Nao ha solicitacoes...");
        } else {
            
            boolean existeSolicitacaoPessoal = false;
            
            for(Solicitacao solicitacao : this.solicitacoes) {
                if(solicitacao.getAutor().equals(this.usuarioAtivo) || solicitacao.getDiretorResponsavel().equals(this.usuarioAtivo)) {
                    System.out.println("\n" + solicitacao);
                    existeSolicitacaoPessoal = true;
                }
            }
            
            if(!existeSolicitacaoPessoal) {
                System.out.println("Voce nao possui solicitacoes cadastradas.");
            }
        }
    }
    
    public void verClassificacao() {
        System.out.println("\nCLASSIFICACAO:\n");
        ArrayList<Membro> classificacao = new ArrayList<>();
        
        if(this.usuarios.isEmpty()) {
            System.out.println("Nao ha usuarios cadastrados.");
        } else {
            for(Usuario usuario : this.usuarios) {
                if(usuario instanceof Membro) {
                    Membro membro = (Membro) usuario; // Cast para Membro
                    classificacao.add(membro);
                }
            }
            Collections.sort(classificacao);
            int indice = 0;
            for(Membro membro : classificacao) {
                indice++;
                System.out.println(indice + " | " + membro + " | " + membro.getPontuacao() + "pts");
            }
        }
    }
    
    public ArrayList<Solicitacao> verSolicitacoesAbertas() {
        ArrayList<Solicitacao> solicitacoesAbertas = new ArrayList<>();
        System.out.println("\nSOLICITACOES ABERTAS:");
        
        int indice = 0;
        for(Solicitacao solicitacao : this.solicitacoes) {
            if(solicitacao.getSituacao() == 0) {
                indice++;
                System.out.println("\n" + indice + ") " + solicitacao);
                solicitacoesAbertas.add(solicitacao);
            }
        }
        return solicitacoesAbertas;    
    } 
    
    public void processarSolicitacao() {
        ArrayList<Solicitacao> solicitacoesAbertas = verSolicitacoesAbertas();
        
        if(solicitacoesAbertas.isEmpty()) {
            System.out.println("Nao ha solicitacoes disponiveis.");
        } else {
            System.out.print("\nQual solicitacao voce quer processar? ");
            int entrada = conferirEntrada(1, solicitacoesAbertas.size());
            Solicitacao solicitacao = solicitacoesAbertas.get(entrada - 1);

            System.out.println("Como proceder?\n1 - Aprovar\n2 - Negar\n3 - Cancelar operacao");
            entrada = conferirEntrada(1, 3);
            switch (entrada) {
                case 1 -> solicitacao.setSituacao(1);
                case 2 -> solicitacao.setSituacao(2);
                case 3 -> {
                    System.out.println("Operacao cancelada!");
                    return;
                }
            }
            solicitacao.setDiretorResponsavel((Diretor) this.usuarioAtivo); //Cast para Diretor
        }
    }
    
    public void verAtividades() {
        System.out.println("\nATIVIDADES DISPONIVEIS:");
        int indice = 0;
        if(this.atividades.isEmpty()) {
            System.out.println("Nao ha atividades cadastradas.");            
        } else {
            for(Atividade atividade : this.atividades) {
                indice++;
                System.out.println("\n" + indice + ") " + atividade);
            }
        }
    } 
    
    public void verUsuarios() {
        System.out.println("\nUSUARIOS CADASTRADOS\n");
        if(this.usuarios.isEmpty()) {
            System.out.println("Nao ha usuarios cadastrados."); 
        } else {
            for(Usuario usuario : usuarios) {
                System.out.println(usuario.getClass().getSimpleName() + " | " + usuario);
            }
        }
    }
    
    public void iniciarSolicitacao() {
        verAtividades();
                
        System.out.print("\nInforme a atividade: ");
        Atividade atividade = this.atividades.get(conferirEntrada(1, this.atividades.size()) - 1);
        
        System.out.print("Informe um valor de sugestao: ");
        int valorSugestao = conferirEntrada(1, 1000);
        
        Solicitacao novaSolicitacao = new Solicitacao((Membro) this.usuarioAtivo, atividade, valorSugestao);
        System.out.println("Solicitacao criada!");
        
        this.solicitacoes.add(novaSolicitacao);
    }    
    
    public void cadastrarUsuario() {
        System.out.println("\nCADASTRAR USUARIO");
        t.nextLine();
        
        System.out.print("Nome: ");
        String nome = t.nextLine();
        
        System.out.print("Senha: ");
        String senha = t.nextLine();

        System.out.print("Email: ");
        String email = t.nextLine();

        System.out.print("Diretoria: ");
        String diretoria = t.nextLine();
        
        System.out.print("Diretor [1] ou Membro [2]? ");
        int entrada = conferirEntrada(1, 2);
        
        switch(entrada) {
            case 1 -> this.usuarios.add(new Diretor(nome, senha, email, diretoria));
            case 2 -> this.usuarios.add(new Membro(nome, senha, email, diretoria));
        }
        
        System.out.println("Usuario cadastrado!");
    }

    public void cadastrarAtividade() {
        System.out.println("\nCADASTRAR ATIVIDADE: ");
        t.nextLine();
        
        System.out.print("Titulo: ");
        String titulo = t.nextLine();

        System.out.print("Descricao: ");
        String descricao = t.nextLine();
                
        System.out.print("Valor: ");
        int valor = conferirEntrada(1, 1000);
        
        this.atividades.add(new Atividade(titulo, descricao, valor));
        System.out.println("Atividade cadastrada!");
    }

    public void excluirAtividade() {
        verAtividades();
        
        System.out.print("\nQual atividade excluir? Voltar [0] ");
        int entrada = conferirEntrada(0, this.atividades.size());
        
        if(entrada == 0) {
            System.out.println("Operacao cancelada.");
            return;
        }
        
        this.atividades.remove(this.atividades.get(entrada - 1));
        System.out.println("Atividade excluida com sucesso!");
    }
    
    public void login() {
        System.out.println("\nLOGIN");
        
        System.out.print("Nome: ");
        String nome = t.nextLine();
        while(retornarUsuario(nome) == null) {
            System.out.print("Usuario inexistente, tente novamente: ");
            nome = t.nextLine();
        }
        
        Usuario usuarioParaEntrar = retornarUsuario(nome);
        
        System.out.print("Senha: ");
        String senha = t.nextLine();
        while(!usuarioParaEntrar.getSenha().equals(senha)) {
            System.out.print("Senha incorreta, tente novamente: ");
            senha = t.nextLine();
        }
        
        this.usuarioAtivo = usuarioParaEntrar;
    }
    
    public void logout() {
        this.usuarioAtivo = null;
        System.out.println("\nLogout bem sucedido!");
        t.nextLine();
    }

    //Getters

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public ArrayList<Atividade> getAtividades() {
        return atividades;
    }

    public ArrayList<Solicitacao> getSolicitacoes() {
        return solicitacoes;
    }

    public Usuario getUsuarioAtivo() {
        return usuarioAtivo;
    }
    
}
