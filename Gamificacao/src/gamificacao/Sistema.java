package gamificacao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Sistema {
    
    ArrayList<Usuario> usuarios;
    ArrayList<Atividade> atividades;
    ArrayList<Solicitacao> solicitacoes;
    Usuario usuarioAtivo;
    Scanner t = new Scanner(System.in);

    public Sistema(ArrayList<Solicitacao> solicitacoes) {
        this.usuarios = carregarInformacoes(Usuario.class);
        this.atividades = carregarInformacoes(Atividade.class);
        this.solicitacoes = carregarInformacoes(Solicitacao.class);
        this.usuarioAtivo = null;
    }
    
    //Carregamento e salvamento
    
    public <T> ArrayList<T> carregarInformacoes(Class classe) {
        ArrayList<T> infoParaCarregar = new ArrayList<>();
        
        File arquivo = new File("");
        
        if(classe.equals(Usuario.class)){
            arquivo = new File("src\\gamificacao\\usuarios.csv");
        } else if(classe.equals(Atividade.class)) {
            arquivo = new File("src\\gamificacao\\atividades.csv");
        } else if(classe.equals(Solicitacao.class)) {
            arquivo = new File("src\\gamificacao\\solicitacoes.csv");
        }
        
        if(arquivo.exists() && arquivo.canRead() && arquivo.isFile()) {

            try{
                FileReader marcarLeitura = new FileReader(arquivo);
                BufferedReader bufLeitura = new BufferedReader(marcarLeitura);
                
                //linha cabeçalho
                String linha = bufLeitura.readLine();
                
                while(linha != null){
                    linha = bufLeitura.readLine();
                    if(linha != null){
                        if(classe.equals(Usuario.class)){
                            infoParaCarregar.add((T) lerUsuario(linha));
                        } else if(classe.equals(Atividade.class)) {
                            infoParaCarregar.add((T) lerAtividade(linha));                            
                        } else if(classe.equals(Solicitacao.class)) {
                            infoParaCarregar.add((T) lerSolicitacao(linha));
                        }
                    }
                }
                bufLeitura.close();
                
            } catch(FileNotFoundException erro) {
                System.out.println("Caminho do arquivo incorreto"); 
            } catch(IOException erroLeitura) {
                System.out.println("Erro na leitura dos dados");
            }
            return infoParaCarregar;
        }
        return null;
    }
    
    public Usuario lerUsuario(String linha) {
        String info[] = linha.split(",");                        
        int tipoUsuario = Integer.parseInt(info[0]);
        switch(tipoUsuario){
            case 1 -> {return new Diretor(info[1], info[2], info[3], LocalDate.parse(info[4]), info[5]);}
            case 2 -> {return new Membro(info[1], info[2], info[3], LocalDate.parse(info[4]), info[5], Integer.parseInt(info[6]));}
        }
        return null;
    }
    
    public Atividade lerAtividade(String linha) {
        String info[] = linha.split(",");
        return new Atividade(info[0], info[1], Integer.parseInt(info[2]));
    }

    public Solicitacao lerSolicitacao(String linha) {
        String info[] = linha.split(",");
        
        Membro autor = null;
        for(Usuario usuario : this.usuarios) {
            if(usuario.getNome().equals(info[0])){
                autor = (Membro) usuario;
            }
        }
        
        Atividade atividade = null;
        for(Atividade atvd : this.atividades) {
            if(atvd.getTitulo().equals(info[1])){
                atividade = atvd;
            }
        }
        
        Diretor diretor = null;
        for(Usuario usuario : this.usuarios) {
            if(usuario.getNome().equals(info[5])){
                diretor = (Diretor) usuario;
            }
        }
        
        return new Solicitacao(autor, atividade, LocalDate.parse(info[2]), Integer.parseInt(info[3]), Integer.parseInt(info[4]), diretor);
    }
    
    public void salvarIformacoes(Class classe, String nomeArq) {
            
        String textoPSalvar = nomeArq + "\n";
        if(classe.equals(Usuario.class)){
            for(Usuario usuario : this.usuarios) {
               textoPSalvar += usuario.retornarCSV() + "\n";
            }
        } else if(classe.equals(Atividade.class)) {
            for(Atividade atvd : this.atividades) {
               textoPSalvar += atvd.retornarCSV() + "\n";
            }            
        } else if(classe.equals(Solicitacao.class)) {
            for(Solicitacao solicitacao : this.solicitacoes) {
               textoPSalvar += solicitacao.retornarCSV() + "\n";
            }            
        }

        File arqUsuarios = new File("src\\gamificacao\\" + nomeArq + ".csv");

        try{
            FileWriter marcaEscrita = new FileWriter(arqUsuarios, false);
            BufferedWriter bufEscrita = new BufferedWriter(marcaEscrita);

            bufEscrita.write(textoPSalvar);

            bufEscrita.flush();
            bufEscrita.close();

        }catch(IOException ex){
            System.err.println("Arquivo corrompido");
        }
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
                if(solicitacao.getSituacao() == 1 || solicitacao.getSituacao() == 2) {
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
            Collections.reverse(classificacao);
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

            System.out.println("Como proceder?\n1 - Aprovar com valor original\n2 - Aprovar com sugestao\n3 - Negar\n4 - Cancelar operacao");
            entrada = conferirEntrada(1, 3);
            switch (entrada) {
                case 1 -> {
                    solicitacao.setSituacao(1);
                    solicitacao.getAutor().alterarPontuacao(solicitacao.getAtividade().getValor());
                }
                case 2 -> {
                    solicitacao.setSituacao(2);
                    solicitacao.getAutor().alterarPontuacao(solicitacao.getValorSugestao());
                }
                case 3 -> solicitacao.setSituacao(3);
                case 4 -> {
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
        salvarIformacoes(Usuario.class, "usuarios");
        salvarIformacoes(Atividade.class, "atividades");
        salvarIformacoes(Solicitacao.class, "solicitacoes");
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
