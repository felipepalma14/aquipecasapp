package palma.felipe.aquipecas.model;

import java.io.Serializable;

/**
 * Created by Roberlandio on 28/11/2016.
 */
public class Marca implements Serializable{

    private String key;
    private String nome;


    public Marca() {
    }

    public Marca(String key, String nome) {
        this.key = key;
        this.nome = nome;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
