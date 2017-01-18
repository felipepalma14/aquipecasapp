package palma.felipe.aquipecas.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import palma.felipe.aquipecas.R;
import palma.felipe.aquipecas.Utils.CustomAdapter;
import palma.felipe.aquipecas.model.Empresa;


public class LojasFragment extends Fragment implements CustomAdapter.AdapterListener {

    ArrayList<Empresa> empresas = new ArrayList<>();
    FirebaseDatabase database;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v= inflater.inflate(R.layout.fragment_lojas,container,false);

        database = FirebaseDatabase.getInstance();

        DatabaseReference refEmpresas = database.getReference("empresas");

        refEmpresas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Empresa e = data.getValue(Empresa.class);
                    Log.i("EMPRESA",e+"");
                    empresas.add(e);
                }
                //
                criarAdapter(empresas,v);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return  v;
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public void criarAdapter(ArrayList<Empresa> lista,View view){
        // Criar o adapter
        CustomAdapter adapter = new CustomAdapter(getActivity(), lista);
        // Recupera a referencia do nosso RecyclerView do layout
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lista_empresas);
        // Conecta nosso RecyclerView com o Adapter
        recyclerView.setAdapter(adapter);
        // Seta a orientacao do nosso RecyclerView como LinearLayout ( Vertical )
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Verifica se existe livros na nossa lista e esconde o progressBar

        /*
        Criando o Click
         */
        adapter.setListener(this);

        //escondeProgressBar(lista);
        //escondeProgressBar(lista);
    }


    @Override
    public void onClickListener(View view, int position) {

    }
}
