package palma.felipe.aquipecas;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import palma.felipe.aquipecas.Utils.CustomAdapterProduto;
import palma.felipe.aquipecas.Utils.GridSpacingItemDecoration;
import palma.felipe.aquipecas.model.Marca;
import palma.felipe.aquipecas.model.Modelo;
import palma.felipe.aquipecas.model.Produto;

public class ListagemProdutoActivity extends AppCompatActivity implements CustomAdapterProduto.AdapterListener {

    FirebaseDatabase database;
    Marca marcaSelecionada;
    Modelo modeloSelecionado;
    //String anoSelecionado;
    boolean encontrado = false;
    ArrayList<Produto> produtos = new ArrayList<>();
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_produto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        loading =(ProgressBar)findViewById(R.id.loading);

        Bundle bundle = getIntent().getExtras();

        marcaSelecionada = (Marca)bundle.get("MARCA");
        modeloSelecionado = (Modelo)bundle.get("MODELO");
        //anoSelecionado = bundle.getString("ANO");

        database = FirebaseDatabase.getInstance();
        //getKeyFromMarca(marcaSelecionada);
        final DatabaseReference rootRef = database.getReference();

        Log.i("MARCA",marcaSelecionada.getNome());
        Log.i("MODELO",modeloSelecionado.getNome());


        rootRef.child("produtos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot produto:dataSnapshot.getChildren()){
                    String keyModelo = produto.child("modelo").getChildren().iterator().next().getKey().toString();
                    Log.i("KEY_PRODUTO",keyModelo + " = " + modeloSelecionado.getKey());
                    if(keyModelo.equals(modeloSelecionado.getKey())){
                        Produto p = new Produto();
                        p.setNome(produto.child("peca").getValue().toString());
                        p.setImagem(produto.child("imagem").getValue().toString());
                        p.setValor(Double.valueOf(produto.child("valor").getValue().toString()));

                        Log.i("KEY_PRODUTO",produto.child("peca").getValue().toString());
                        produtos.add(p);

                    }

                }

                criarAdapter(produtos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });



    }


    private void insereProduto(final String keyModelo){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refProdutos = database.getReference("produtos");

        refProdutos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()) {
                    Produto produto = new Produto();
                    Log.i("PRODUTO_F1",data.child("peca").getValue().toString());
                    produto.setNome(data.child("peca").getValue().toString());
                    produto.setDisponivel(Boolean.valueOf(data.child("isdisponivel").getValue().toString()));
                    produto.setValor(Double.valueOf(data.child("valor").getValue().toString()));
                    produto.setPromocao(Boolean.valueOf(data.child("ispromocao").getValue().toString()));
                    produto.setImagem(data.child("imagem").getValue().toString());
                    Log.i("ADD",produto.getNome());
                    Log.i("KEY_MODELO_PARAM",keyModelo);


                    produtos.add(produto);
                    criarAdapter(produtos);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void criarAdapter(ArrayList<Produto> lista){
        // Criar o adapter

        CustomAdapterProduto adapter = new CustomAdapterProduto(ListagemProdutoActivity.this, lista);

        // Recupera a referencia do nosso RecyclerView do layout
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lista_produtos);
        // Conecta nosso RecyclerView com o Adapter
        /*
        recyclerView.setAdapter(adapter);
        // Seta a orientacao do nosso RecyclerView como LinearLayout ( Vertical )
        GridLayoutManager grid = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        grid.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(grid);
        */

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(10),true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        // Verifica se existe livros na nossa lista e esconde o progressBar

        /*
        Criando o Click
         */
        adapter.setListener(this);
        loading.setVisibility(View.GONE);
        //escondeProgressBar(lista);
        //escondeProgressBar(lista);
    }

    @Override
    public void onClickListener(View view, int position) {
        String pecaSelecionada = produtos.get(position).getNome();

        Intent intent = new Intent(this,DetalhesProdutoActivity.class);
        intent.putExtra("PRODUTO",produtos.get(position));
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
