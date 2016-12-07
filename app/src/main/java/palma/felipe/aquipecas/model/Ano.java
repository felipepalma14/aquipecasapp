package palma.felipe.aquipecas.model;

/**
 * Created by Roberlandio on 28/11/2016.
 */
public class Ano {
    private String codigo;
    private String nome;


    public Ano() {
    }

    public Ano(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
