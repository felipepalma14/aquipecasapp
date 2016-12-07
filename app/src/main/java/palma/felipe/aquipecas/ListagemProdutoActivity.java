package palma.felipe.aquipecas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import palma.felipe.aquipecas.Utils.CustomAdapterProduto;
import palma.felipe.aquipecas.model.Produto;

public class ListagemProdutoActivity extends AppCompatActivity implements CustomAdapterProduto.AdapterListener {

    FirebaseDatabase database;
    String marcaSelecionada;
    String modeloSelecionado;
    String anoSelecionado;
    boolean encontrado = false;
    ArrayList<Produto> produtos = new ArrayList<>();
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_produto);

        loading =(ProgressBar)findViewById(R.id.loading);

        Bundle bundle = getIntent().getExtras();

        marcaSelecionada = bundle.getString("MARCA");
        modeloSelecionado = bundle.getString("MODELO");
        anoSelecionado = bundle.getString("ANO");

        database = FirebaseDatabase.getInstance();
        //getKeyFromMarca(marcaSelecionada);
        final DatabaseReference refMarcas = database.getReference();

        refMarcas.child("marcas").orderByChild("nome").equalTo(marcaSelecionada).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot marca :dataSnapshot.getChildren()){
                    Log.i("MARCA",marca.getKey());//MARCA
                    refMarcas.child("marcas").child(marca.getKey()).child("modelos").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String key;
                            for(DataSnapshot modelo:dataSnapshot.getChildren()) {
                                Log.i("LOG", "MODELO: "+ modelo.getKey());//OK
                                key = modelo.getKey();
                                Log.i("MODELOS", modelo.child("modelo").child("nome").getValue().toString());//OK
                                if(modelo.child("modelo").child("nome").getValue().toString().equals(modeloSelecionado)){
                                    Log.i("OK", key);
                                    insereProduto(key);
                                }
                            }
                            //criarAdapter(produtos);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

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
        for (Produto pro:lista) {
            Log.i("PROUTO",String.valueOf(pro.getValor()));
        }
        CustomAdapterProduto adapter = new CustomAdapterProduto(ListagemProdutoActivity.this, lista);
        // Recupera a referencia do nosso RecyclerView do layout
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lista_produtos);
        // Conecta nosso RecyclerView com o Adapter
        recyclerView.setAdapter(adapter);
        // Seta a orientacao do nosso RecyclerView como LinearLayout ( Vertical )
        GridLayoutManager grid = new GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false);
        grid.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(grid);

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
        startActivity(intent);

    }



}
