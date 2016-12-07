package palma.felipe.aquipecas.model;

/**
 * Created by Roberlandio on 29/11/2016.
 */
public class Empresa {
    private String cep;
    private long date_criacao;
    private String email;
    private String empresa;
    private String uid;
    private String imagem;

    public Empresa() {
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public long getDate_criacao() {
        return date_criacao;
    }

    public void setDate_criacao(long date_criacao) {
        this.date_criacao = date_criacao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "cep='" + cep + '\'' +
                ", date_criacao=" + date_criacao +
                ", email='" + email + '\'' +
                ", empresa='" + empresa + '\'' +
                ", uid='" + uid + '\'' +
                ", imagem='" + imagem + '\'' +
                '}';
    }
}
