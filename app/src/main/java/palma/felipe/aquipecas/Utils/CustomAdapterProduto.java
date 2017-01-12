package palma.felipe.aquipecas.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oceanbrasil.libocean.Ocean;
import com.oceanbrasil.libocean.control.glide.GlideRequest;

import java.util.ArrayList;

import palma.felipe.aquipecas.R;
import palma.felipe.aquipecas.model.Produto;

/**
 * Created by Roberlandio on 04/09/2016.
 */
public class CustomAdapterProduto extends RecyclerView.Adapter<CustomAdapterProduto.ViewHolder> {

// Contexto da Activity
private final Context context;
// Nossa lista de livros
private ArrayList<Produto> lista;
    /*
    listener para acessar  o click
     */
private AdapterListener listener;



public CustomAdapterProduto(Context context, ArrayList<Produto> lista) {
        this.lista = lista;
        this.context = context;
        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Cria nosso layout do adapter para ser repetido
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto, null);

        // Passa o layout para dentro do ViewHolder (Nosso esquelo do layout)
        ViewHolder holder = new ViewHolder(view);

        return holder;
        }

@Override
public void onBindViewHolder(ViewHolder holder, int position) {
        // Recuperei a referencia
        Produto produto = lista.get(position);

        // Seta os valores do livro para o layout dentro do holder (Nosso esqueleto)

        holder
        .setNome(produto.getNome())
        .setImagem(produto.getImagem())
        .setValor(produto.getValor());



        }

@Override
public int getItemCount() {
        // Total de livros na lista
        return lista.size();
        }

public AdapterListener getListener() {
        return listener;
        }

public void setListener(AdapterListener listener) {
        this.listener = listener;
        }


public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView txtNome;
    private TextView txtValor;
    private ImageView imgCapa;

    public ViewHolder(View itemView) {
        super(itemView);

        // Recuperei as referencias do layout
        imgCapa = (ImageView) itemView.findViewById(R.id.imagemProduto);
        txtNome = (TextView) itemView.findViewById(R.id.nomeProduto);
        txtValor = (TextView) itemView.findViewById(R.id.valorProduto);

        itemView.setOnClickListener( this);

    }




    public ViewHolder setNome(String nome){
        if(txtNome == null) return this;
        txtNome.setText(nome);
        return this;
    }

    public ViewHolder setValor(double valor){
        if(txtValor == null) return this;
        txtValor.setText("R$" + String.valueOf(valor));
        return this;
    }





    public ViewHolder setImagem(String image){
        if(imgCapa == null) return this;

        // Processaa a imagem com a biblioteca OceanLib
        Ocean.glide(context) // Contexto da aplicacao
                .load(image) // URL da imagem
                .build(GlideRequest.BITMAP) // Constroi um pedido para processar a imagem como Bitmap
                //.resize(200, 200) // Recorta a imagem no tamanho especifico
                //.circle() // Arrendoda a imagem
                .resize(150,130)
                .into(imgCapa); // Abre a imagem no imageView passado

        return this;
    }


    @Override
    public void onClick(View view) {
        int position = getPosition();
        if(listener != null){
            //Book livro = lista.get(position);
            listener.onClickListener(view,position);
        }

    }
}

/*
Adapter para identificar o click
 */
public interface AdapterListener{
    /*
    Pode-se usar classe generica
     */
    void onClickListener(View view, int position);
}
}
