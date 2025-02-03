package trabalholibrary;

public class Livro {

    private int id;
    private String titulo;
    private int qntdDisponivel;
    
    public Livro(int id, String titulo, int qntdDisponivel) {
        this.id = id;
        this.titulo  = titulo;
        this.qntdDisponivel = qntdDisponivel;
    }
    
    @Override
    public String toString() {
        return this.id + "," + this.titulo + "," + this.qntdDisponivel;
    }
    
    //GETTERS
    
        public int getId() {
            return id;
        }

        public String getTitulo() {
            return titulo;
        }

        public int getQntdDisponivel() {
            return qntdDisponivel;
        }

    //SETTERS

        public void setId(int id) {
            this.id = id;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public void setQntdDisponivel(int qntdDisponivel) {
            this.qntdDisponivel = qntdDisponivel;
        }
}
