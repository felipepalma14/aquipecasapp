package palma.felipe.aquipecas.model;

import java.util.ArrayList;

/**
 * Created by Roberlandio on 28/11/2016.
 */
public class Marca {
    private long codigo;
    private String nome;
    private ArrayList<Modelo> modelos;


    public Marca() {
    }

    public Marca(String nome, ArrayList<Modelo> modelos) {
        this.nome = nome;
        this.modelos = modelos;
    }

    public Marca(long codigo, String nome, ArrayList<Modelo> modelos) {
        this.codigo = codigo;
        this.nome = nome;
        this.modelos = modelos;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Modelo> getModelos() {
        return modelos;
    }

    public void setModelos(ArrayList<Modelo> modelos) {
        this.modelos = modelos;
    }

    @Override
    public String toString() {
        return "Marca{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                '}';
    }
}
