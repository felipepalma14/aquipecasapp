package palma.felipe.aquipecas.model;

/**
 * Created by Roberlandio on 28/11/2016.
 */
public class Modelo {
    private String nome;
    private long codigo;

    public Modelo() {
    }

    public Modelo(String nome, long codigo) {
        this.nome = nome;
        this.codigo = codigo;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }
}

