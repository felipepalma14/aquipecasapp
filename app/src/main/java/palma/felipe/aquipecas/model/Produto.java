package palma.felipe.aquipecas.model;

/**
 * Created by Roberlandio on 29/11/2016.
 */
public class Produto {
    private String nome;
    private String imagem;
    private boolean isDisponivel;
    private boolean isPromocao;
    private double valor;
    private Categoria categoria;
    private Empresa empresa;

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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
