package palma.felipe.aquipecas.model;

import java.io.Serializable;

/**
 * Created by Roberlandio on 29/11/2016.
 */
public class Produto implements Serializable{
    private String nome;
    private String imagem;
    private boolean isDisponivel;
    private boolean isPromocao;
    private double valor;
    private String Keycategoria;
    private String Keyempresa;

    public Produto() {
    }

    public Produto(String nome, String imagem, boolean isDisponivel, boolean isPromocao, double valor, String keycategoria, String keyempresa) {
        this.nome = nome;
        this.imagem = imagem;
        this.isDisponivel = isDisponivel;
        this.isPromocao = isPromocao;
        this.valor = valor;
        Keycategoria = keycategoria;
        Keyempresa = keyempresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public boolean isDisponivel() {
        return isDisponivel;
    }

    public void setDisponivel(boolean disponivel) {
        isDisponivel = disponivel;
    }

    public boolean isPromocao() {
        return isPromocao;
    }

    public void setPromocao(boolean promocao) {
        isPromocao = promocao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getKeycategoria() {
        return Keycategoria;
    }

    public void setKeycategoria(String keycategoria) {
        Keycategoria = keycategoria;
    }

    public String getKeyempresa() {
        return Keyempresa;
    }

    public void setKeyempresa(String keyempresa) {
        Keyempresa = keyempresa;
    }

    @Override
    public String toString() {
        return nome;
    }
}
