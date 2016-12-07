package palma.felipe.aquipecas.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import palma.felipe.aquipecas.ListagemProdutoActivity;
import palma.felipe.aquipecas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    ArrayList<String> marcas = new ArrayList<>();
    ArrayList<String> modelos = new ArrayList<>();
    ArrayList<String> anos = new ArrayList<>();
    Spinner spMarcas;
    Spinner spModelos;
    Spinner spAnos;
    ProgressBar loading;
    Button btnBuscarPeca;

    FirebaseDatabase database;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        /*
        Chamar XML aqui
         */

        spMarcas = (Spinner)v.findViewById(R.id.spinnerMarcaVeiculo);
        spModelos = (Spinner)v.findViewById(R.id.spinnerModeloVeiculo);
        spAnos = (Spinner)v.findViewById(R.id.spinnerAnoVeiculo);
        loading = (ProgressBar)v.findViewById(R.id.loading);
        btnBuscarPeca = (Button)v.findViewById(R.id.btnBuscarPorVeiculo);

        btnBuscarPeca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String modeloSelecionado = spModelos.getSelectedItem().toString();
                String anoSelecionado = spAnos.getSelectedItem().toString();
                String marcaSelecionada = spMarcas.getSelectedItem().toString();

                Intent intent = new Intent(getActivity(),ListagemProdutoActivity.class);

                Log.i("SELECIONADOS_MAIN",modeloSelecionado + " - " + anoSelecionado);
                intent.putExtra("MARCA",marcaSelecionada);
                intent.putExtra("MODELO",modeloSelecionado);
                intent.putExtra("ANO",anoSelecionado);

                startActivity(intent);

            }
        });
        database = FirebaseDatabase.getInstance();
        DatabaseReference refMarcas = database.getReference("marcas");

        refMarcas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                marcas.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Log.i("Palma",snapshot.child("nome").getValue().toString());
                    marcas.add(snapshot.child("nome").getValue().toString());
                }

                encontraMarcas();

                spMarcas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        encontraModelos(marcas.get(i));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return v;
    }

    private void encontraMarcas() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,marcas);
        spMarcas.setAdapter(adapter);

        loading.setVisibility(View.GONE);

    }

    public void encontraModelos(String marca){
        modelos.clear();
        anos.clear();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference refMarcas = database.getReference("marcas");
        refMarcas.orderByChild("nome").equalTo(marca).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){

                    for(DataSnapshot modelo: data.child("modelos").getChildren()){
                        modelos.add(modelo.child("modelo").child("nome").getValue().toString());
                    }
                    for(DataSnapshot modelo: data.child("modelos").getChildren()){
                        anos.add(modelo.child("ano").child("nome").getValue().toString());
                    }

                }

                ArrayAdapter<String> adapterModelo = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,modelos);
                spModelos.setAdapter(adapterModelo);

                ArrayAdapter<String> adapterAno = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,anos);
                spAnos.setAdapter(adapterAno);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
