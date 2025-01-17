package gamificacao;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Sistema {
    
    private Scanner t;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Atividade> atividades;
    private Tabela tabela;
    private Usuario usuarioLogado;
    
    public Sistema() {
        t = new Scanner(System.in);
        usuarios = new ArrayList<>();
        atividades = new ArrayList<>();
        tabela = new Tabela();
        usuarioLogado = null;
    }
    
    //CARREGAMENTO

        //Funcao: ler um arquivo e carregar os usuarios
        public void carregarUsuarios(File arquivoUsuarios) {
            System.out.println("Usuarios carregados!");
        }
        
        public void carregarAtividades(File arquivoAtividades){
            System.out.println("Atividades carregadas!");
        }
    //
        
    //CADASTRO USUARIO (2 funcoes)
        
        //Funcao: instancia um usuario de acordo com o parametro e o armazena
        private void cadastrarUsuario (String nome, String senha, int tipoUsuario) {
            switch (tipoUsuario) {
                case 1 -> usuarios.add(new Diretor(nome, senha));
                case 2 -> {
                    Membro novoMembro = new Membro(nome, senha);
                    usuarios.add(novoMembro);
                    //Preciso adicionar o usuario na tabela no ato do cadastro
                    tabela.adicionarCompetidor(novoMembro);
                }
            }
            t.nextLine();
            System.out.println("Cadastrado com sucesso!");
        }
        
        //Funcao: serve para interagir com o usuario
        public void iniciarCadastro() {
            System.out.println("\nCADASTRO");

            //Nome
            System.out.print("Nome de usuario: ");
            String nomeUsuario = t.nextLine();

            //Senha
            System.out.print("Senha: ");
            String senhaUsuario = t.nextLine();

            //Tipo usuario
            int tipoCargo;
            do{
                System.out.print("Cargo (1 - Diretor | 2 - Membro): ");
                tipoCargo = t.nextInt();
                
                if(tipoCargo < 0 || tipoCargo > 2){
                    System.out.println("Invalido. Tente novamente...");
                }
            } while (tipoCargo < 0 || tipoCargo > 2);

            cadastrarUsuario(nomeUsuario, senhaUsuario, tipoCargo);
        }
    //
        
    //LOGIN USUARIO (3 funcoes)
        
        //Funcao: confere a senha do usuario em um loop
        private boolean conferirSenha(Usuario usuario) {
            System.out.print("Senha: ");
            String senhaUsuario = t.nextLine();

            //Autenticacao da senha
            boolean senhaInvalida = true;

            do{
                if(usuario.getSenha().equals(senhaUsuario)) {
                    break;
                } else {
                    System.out.print("Senha invalida, tente novamente: ");
                    senhaUsuario = t.nextLine();
                }
            } while(senhaInvalida);
            
            System.out.println("Acesso liberado!");
            return true;
        }
        
        //Funcao: retorna um usuario de acordo com o nome
        private Usuario encontrarUsuario() {
            System.out.print("Nome de usuario (0 - Voltar): ");
            String nomeUsuario = t.nextLine();
            if(nomeUsuario.equals("0")) { return null; }
            
            //Autenticacao do nome
            boolean nomeInvalido = true;
            
            do{
                for(Usuario usuario : usuarios){
                    if(usuario.getNome().equals(nomeUsuario)) {
                        return usuario;
                    }
                }
                
                if(nomeInvalido){
                    System.out.print("Usuario invalido, tente novamente (0 - Voltar): ");
                    nomeUsuario = t.nextLine();
                    if(nomeUsuario.equals("0")) {
                        return null;
                    }
                }
            } while(nomeInvalido);
            
            System.err.println("Erro inesperado ao executar a funcao 'encontrarUsuario()'");
            return null;
        }

        //Funcao: encontra um usuario e requisita uma senha
        public void iniciarLogin() {
            System.out.println("\nLOGIN");
            Usuario usuario = encontrarUsuario();
            if(usuario != null) {
                if(conferirSenha(usuario)) {
                    this.usuarioLogado = usuario;
                }
            }
        }
    //
        
    //Getters
        
        public Usuario getUsuarioLogado() {
            return this.usuarioLogado;
        }
}
