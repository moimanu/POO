package trabalholibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca {
    
    private ArrayList<Livro> acervo;
    private ArrayList<Usuario> usuarios;
    private Usuario usuarioAtivo;
    
    public Biblioteca() {
        this.acervo = carregarAcervo();
        this.usuarios = carregarUsuarios();
        this.usuarioAtivo = null;
    }
    
    //CARREGAMENTO DE ACERVO E DE USUARIOS
    
        private ArrayList<Livro> carregarAcervo(){

            ArrayList<Livro> acervo = new ArrayList<>();

            //Achando arquivo
            File arqAcervo = new File("src/trabalholibrary/acervo.csv");

            //Conferindo arquivo
            if(arqAcervo.exists() && arqAcervo.canRead() && arqAcervo.isFile()) {

                try{
                    //Iniciar marcacao de leitura
                    FileReader marcarLeitura = new FileReader(arqAcervo);
                    BufferedReader bufLeitura = new BufferedReader(marcarLeitura);

                    //linha cabecalho
                    String linha = bufLeitura.readLine();

                    while(linha != null){
                        linha = bufLeitura.readLine();

                        //verificar se nao esgotamos TODAS as linhas do arquivo
                        if(linha != null){
                            String pedacosLinha[] = linha.split(",");

                            //transformar em Livro
                            Livro livro = new Livro(Integer.parseInt(pedacosLinha[0]), pedacosLinha[1], Integer.parseInt(pedacosLinha[2]));

                            //Livro do arquivo disponivel para a biblioteca
                            acervo.add(livro);
                        }
                    }
                    bufLeitura.close();

                    return acervo;

                } catch(FileNotFoundException erro) {
                    System.out.println("Caminho do arquivo incorreto"); 
                } catch(IOException erroLeitura) {
                    System.out.println("Erro na leitura dos dados");
                }
            }
            return null;
        }

        public ArrayList<Usuario> carregarUsuarios() {

            ArrayList<Usuario> usuarios = new ArrayList<>();

            //Achando arquivo
            File arqUsuarios = new File("src/trabalholibrary/usuarios.csv");

            //Conferindo arquivo
            if(arqUsuarios.exists() && arqUsuarios.canRead() && arqUsuarios.isFile()) {

                try{
                    //Iniciar marcacao de leitura
                    FileReader marcarLeitura = new FileReader(arqUsuarios);
                    BufferedReader bufLeitura = new BufferedReader(marcarLeitura);

                    //linha cabeçalho
                    String linha = bufLeitura.readLine();

                    while(linha != null){
                        linha = bufLeitura.readLine();

                        //verificar se nao esgotamos TODAS as linhas do arquivo
                        if(linha != null){
                            String pedacosLinha[] = linha.split(",");

                            //Informacoes basicas
                            String tipoUsuario = pedacosLinha[0];
                            String infoPessoais[] = pedacosLinha[1].split("/");

                            ArrayList<Emprestimo> emprestimos = new ArrayList<>();
                            if(pedacosLinha.length > 2) {
                                //ArrayList de emprestimos
                                String emprestimo[] = pedacosLinha[2].split("/");
                                for(String infoEmprestimo : emprestimo) {
                                    
                                    String infoEmprestimos[] = infoEmprestimo.split("%");
                                    
                                    Livro livroEmprestado = acervo.get(Integer.parseInt(infoEmprestimos[0]) - 1); //Correcao de indice
                                    emprestimos.add(new Emprestimo(livroEmprestado, LocalDate.parse(infoEmprestimos[1])));
                                    //Aqui, foi preciso alterar a quantidade disponivel no acervo
                                    livroEmprestado.setQntdDisponivel(livroEmprestado.getQntdDisponivel() - 1);
                                }                            
                            }

                            //Nomeando
                            String nome = infoPessoais[0];
                            String email = infoPessoais[1];
                            String senha = infoPessoais[2];

                            //transformar em usuario e adiciona-lo
                            switch(Integer.parseInt(tipoUsuario)){

                                //Professor => String nome, String email, String senha, String departamento, ArrayList<Livro> emprestimos
                                case 1 -> {
                                    String departamento = infoPessoais[3];
                                    usuarios.add(new Professor(nome, email, senha, departamento, emprestimos));
                                }

                                //Aluno => String nome, String email, String senha, String matricula, String curso, ArrayList<Livro> emprestimos
                                case 2 -> {
                                    String matricula = infoPessoais[3];
                                    String curso = infoPessoais[4];
                                    usuarios.add(new Aluno(nome, email, senha, matricula, curso, emprestimos));
                                }

                                //Bibliotecario => String nome, String email, String senha, String telefone, int qntdDevolucoes
                                case 3 -> {
                                    String telefone = infoPessoais[3];
                                    int qntdDevolucoes = Integer.parseInt(infoPessoais[4]);
                                    usuarios.add(new Bibliotecario(nome, email, senha, telefone, qntdDevolucoes));
                                }   
                            }
                        }
                    }
                    bufLeitura.close();

                    return usuarios;

                } catch(FileNotFoundException erro) {
                    System.out.println("Caminho do arquivo incorreto"); 
                } catch(IOException erroLeitura) {
                    System.out.println("Erro na leitura dos dados");
                }
            }
            return null;
        }
    
    //SALVAMENTO DE INFORMACOES
        
        public void salvarUsuarios() {
            
            String textoPSalvar = "Usuarios\n";
            for(Usuario usuario : usuarios) {
                textoPSalvar += usuario.retornarCSV() + "\n";
            }
            
            File arqUsuarios = new File("src/trabalholibrary/usuarios.csv");
        
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
        
    //LOGIN
        
        public void login() {

            Scanner t = new Scanner(System.in);

            System.out.println("\nLOGIN");

            System.out.print("Nome: ");
            Usuario usuarioParaEntrar = encontrarUsuario(t.nextLine());
            while(usuarioParaEntrar == null) {
                System.out.print("Usuario inexistente, tente novamente: ");
                usuarioParaEntrar = encontrarUsuario(t.nextLine());
            }

            System.out.print("Senha: ");
            String senha = t.nextLine();
            while(!conferirSenha(usuarioParaEntrar, senha)) {
                System.out.print("Senha incorreta, tente novamente: ");
                senha = t.nextLine();
            }

            this.usuarioAtivo = usuarioParaEntrar;
        }

        public Usuario encontrarUsuario(String nome) {
            for(Usuario usuario : this.usuarios) {
                if(usuario.getNome().equals(nome)){
                    return usuario;
                }
            }
            return null;
        }

        public boolean conferirSenha(Usuario usuario, String senha) {
            return usuario.getSenha().equals(senha);
        }

    //FERRAMENTAS
        
        public void conferirLivroPorID(ArrayList<Livro> arrayList) {
            Scanner t = new Scanner(System.in);
            boolean livroExiste = false;
            System.out.print("\nDigite o ID do livro: ");
            int id = t.nextInt();
            for(Livro livro : arrayList) {
                if(livro.getId() == id) {
                    System.out.println(livro);
                    livroExiste = true;
                }
            }
            if(livroExiste == false) {
                System.out.println("Livro inexistente...");
            }
        }
        
        public void conferirLivroPorTitulo(ArrayList<Livro> arrayList) {
            Scanner t = new Scanner(System.in);
            boolean livroExiste = false;
            System.out.print("\nDigite o titulo do livro: ");
            String titulo = t.nextLine();
            for(Livro livro : arrayList) {
                if(livro.getTitulo()== titulo) {
                    System.out.println(livro);
                    livroExiste = true;
                }
            }
            if(livroExiste == false) {
                System.out.println("Livro inexistente...");
            }
        }
        
        public void conferirQntdLivro() {
            
            Scanner t = new Scanner(System.in);
            System.out.println("\nComo deseja conferir?\n1 - Por ID\n2 - Por nome");
            int entrada = t.nextInt();
            while(entrada < 1 || entrada > 2){
                System.out.print("Opcao invalida, tente novamente: ");
                entrada = t.nextInt();
            }

            switch(entrada) {
                case 1 -> conferirLivroPorID(this.acervo);
                case 2 -> conferirLivroPorTitulo(this.acervo);
            }
        }
        
        public void pegarLivro(int limite, ArrayList<Emprestimo> emprestimos) {
            
            for(Emprestimo emprestimo : this.usuarioAtivo.getEmprestimos()){
                
                if(ChronoUnit.DAYS.between(emprestimo.getDataEmprestimo(), LocalDate.now()) > 7) {
                    System.out.println("Voce possui atrasos de devolucao. Regularize-se para poder utilizar outros livros.");
                    return;
                }
            }
            
            //Realizando o emprestimo
            Scanner t = new Scanner(System.in);
            System.out.print("Digite o ID da obra: ");
            int entrada = t.nextInt();

            if(emprestimos.size() < limite) {
                boolean idExiste = false;
                for(Livro livro : this.acervo){

                    //Conferindo se existe
                    if(livro.getId() == entrada) {

                        //Conferindo disponibilidade
                        if (livro.getQntdDisponivel() > 0) {
                            System.out.println(livro + " -> " + (livro.getQntdDisponivel() - 1));
                            livro.setQntdDisponivel(livro.getQntdDisponivel() - 1);
                            emprestimos.add(new Emprestimo(livro, LocalDate.now()));

                        } else {
                            System.out.println("Livro indisponivel...");
                        }
                        idExiste = true;
                    } 
                }
                if(idExiste == false) {
                    System.out.println("ID inexistente...");
                }    
            } else {
                System.out.println("Voce nao pode ultrapassar o limite de " + limite + " emprestimos...");
            }
        }
        
        protected void devolverLivro(Usuario usuario, ArrayList<Emprestimo> emprestimos) {
        
            Scanner t = new Scanner(System.in);

            System.out.print("\nQual obra voce deseja devolver?");
            usuario.verEmprestimosAtuais(emprestimos);
            System.out.print("Digite uma ID: ");
            int entrada = t.nextInt();

            for(Emprestimo emprestimo : emprestimos) {
                if(emprestimo.getLivro().getId() == entrada) {
                    emprestimo.getLivro().setQntdDisponivel(emprestimo.getLivro().getQntdDisponivel() + 1);
                    emprestimos.remove(emprestimo);
                    break;
                }
            }
        }
        
        private boolean conferirSeNomeExiste(String nome) {
            for(Usuario usuario : this.usuarios) {
                if(usuario.getNome().equals(nome)) {
                    return true;
                }
            }
            return false;
        }
        
        public void cadastrarUsuario() {
        
            Scanner t = new Scanner(System.in);

            System.out.println("CADASTRO");

            System.out.print("Nome: ");
            String nome = t.nextLine();
            while(conferirSeNomeExiste(nome)) {
                System.out.print("Esse usuario ja existe... Tente outro: ");
                nome = t.nextLine();
            }

            System.out.print("Email: ");
            String email = t.nextLine();

            System.out.print("Senha: ");
            String senha = t.nextLine();

            System.out.println("1 - Professor\n2 - Aluno\n3 - Bibliotecario");
            int tipoUsuario = 0;
            while(tipoUsuario < 1 || tipoUsuario > 3){
                tipoUsuario = t.nextInt();
            }

            switch(tipoUsuario){

                //Professor
                case 1 -> {
                    t.nextLine();
                    System.out.print("Departamento: ");
                    String departamento = t.nextLine();

                    this.usuarios.add(new Professor(nome, email, senha, departamento, new ArrayList<>()));
                }

                //Aluno
                case 2 -> {
                    t.nextLine();
                    System.out.print("Matricula: ");
                    String matricula = t.nextLine();

                    System.out.print("Curso: ");
                    String curso = t.nextLine();

                    this.usuarios.add(new Aluno(nome, email, senha, matricula, curso, new ArrayList<>()));
                }

                //Bibliotecario
                case 3 -> {
                    t.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = t.nextLine();

                    System.out.print("Quantidade de devolucoes: ");
                    int qntdDevolucoes = t.nextInt();

                    this.usuarios.add(new Bibliotecario(nome, email, senha, telefone, qntdDevolucoes));
                }
            }
        }
        
        public void verLivrosEmprestados() {
            
            System.out.println("\nEMPRESTIMOS");
            for(Usuario usuario : this.usuarios) {
                if(usuario.getClass() != Bibliotecario.class){
                    for(Emprestimo emprestimo : usuario.getEmprestimos()) {
                        System.out.println(usuario.getNome() + " / " + emprestimo);
                    }
                }
            }
            
        }
        
        public void verEmprestimosAtrasados() {
            System.out.println("\nEMPRESTIMOS EM ATRASO");
            for(Usuario usuario : this.usuarios) {
                if(usuario.getClass() != Bibliotecario.class){
                    for(Emprestimo emprestimo : usuario.getEmprestimos()) {
                        if(ChronoUnit.DAYS.between(emprestimo.getDataEmprestimo(), LocalDate.now()) > 7) {
                            System.out.println(usuario.getNome() + " / " + emprestimo);
                        }
                    }
                }
            }
        }
        
    //GETTERS
    
        public ArrayList<Livro> getAcervo() {
            return acervo;
        }

        public ArrayList<Usuario> getUsuarios() {
            return usuarios;
        }

        public Usuario getUsuarioAtivo() {
            return usuarioAtivo;
        }
        
    //SETTERS
        
        public void setAcervo(ArrayList<Livro> acervo) {
            this.acervo = acervo;
        }

        public void setUsuarios(ArrayList<Usuario> usuarios) {
            this.usuarios = usuarios;
        }

        public void setUsuarioAtivo(Usuario usuarioAtivo) {
            this.usuarioAtivo = usuarioAtivo;
        }
}