package palma.felipe.aquipecas.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Roberlandio on 28/11/2016.
 */
public class Modelo implements Serializable{
    private String key;
    private Marca marca;
    private String nome;
    private ArrayList<Ano> anos = new ArrayList<>();

    public Modelo() {
    }


    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
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


    public ArrayList<Ano> getAnos() {
        return anos;
    }

    public void setAnos(ArrayList<Ano> anos) {
        this.anos = anos;
    }

    @Override
    public String toString() {
        return nome;
    }
}

