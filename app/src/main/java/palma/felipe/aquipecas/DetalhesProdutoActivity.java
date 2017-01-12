package palma.felipe.aquipecas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.oceanbrasil.libocean.Ocean;
import com.oceanbrasil.libocean.control.glide.GlideRequest;
import com.oceanbrasil.libocean.control.glide.ImageDelegate;

import palma.felipe.aquipecas.model.Produto;

public class DetalhesProdutoActivity extends AppCompatActivity {

    Intent intent;
    Produto produto;
    Toolbar toolbar;
    int mutedColor = R.attr.colorPrimary;
    CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(palma.felipe.aquipecas.R.layout.activity_detalhes_produto);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolBarLayout);
        //collapsingToolbar.setTitle("Pemaza");
        ImageView imgView  = (ImageView)findViewById(R.id.imagemProduto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        produto = (Produto) bundle.get("PRODUTO");


        TextView txtNome = (TextView)findViewById(R.id.nomeProduto);
        TextView txtValor = (TextView)findViewById(R.id.valorProduto);

        txtNome.setText( produto.getNome());
        txtValor.setText("R$ "+String.valueOf(produto.getValor()));



        Ocean.glide(this) // Contexto da aplicacao
                .load(produto.getImagem()) // URL da imagem
                .build(GlideRequest.BITMAP).addDelegateImageBitmap(new ImageDelegate.BitmapListener() {
            @Override
            public void createdImageBitmap(Bitmap bitmap) {
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @SuppressWarnings("ResourceType")
                    @Override
                    public void onGenerated(Palette palette) {

                        mutedColor = palette.getMutedColor(R.color.primary);
                        collapsingToolbar.setContentScrimColor(mutedColor);
                        collapsingToolbar.setStatusBarScrimColor(R.color.black_trans80);
                    }
                });

            }
        }) // Constroi um pedido para processar a imagem como Bitmap
                //.resize(200, 200) // Recorta a imagem no tamanho especifico
                //.circle() // Arrendoda a imagem
                .into(imgView); // Abre a imagem no imageView passado












    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable){
        BitmapDrawable bitmapDrawable= (BitmapDrawable)drawable;
        return bitmapDrawable.getBitmap();

    }
}
