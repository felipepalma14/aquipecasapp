package palma.felipe.aquipecas.model;

import java.io.Serializable;

/**
 * Created by Roberlandio on 28/11/2016.
 */
public class Ano implements Serializable{
    private String key;
    private String nome;


    public Ano() {
    }

    public Ano(String key, String nome) {
        this.key = key;
        this.nome = nome;
    }

    public String getCodigo() {
        return key;
    }

    public void setCodigo(String codigo) {
        this.key = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
