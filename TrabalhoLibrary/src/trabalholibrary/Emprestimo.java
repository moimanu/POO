package trabalholibrary;

import java.time.LocalDate;

public class Emprestimo {

    private Livro livro;
    private LocalDate dataEmprestimo;
    
    public Emprestimo(Livro livro, LocalDate dataEmprestimo) {
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
    }
    
    public String toString() {
        return this.livro.getId() + " / " + this.livro.getTitulo() + " / " + this.dataEmprestimo;
    }
    
    //GETTERS

        public Livro getLivro() {
            return livro;
        }

        public LocalDate getDataEmprestimo() {
            return dataEmprestimo;
        }
}