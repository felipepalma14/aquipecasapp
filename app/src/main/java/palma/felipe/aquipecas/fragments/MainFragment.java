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
import palma.felipe.aquipecas.model.Ano;
import palma.felipe.aquipecas.model.Marca;
import palma.felipe.aquipecas.model.Modelo;
import palma.felipe.aquipecas.service.CallFirebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    ArrayList<Marca> marcas = new ArrayList<>();
    ArrayList<Modelo> modelos = new ArrayList<>();
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

        spMarcas = (Spinner) v.findViewById(R.id.spinnerMarcaVeiculo);
        spModelos = (Spinner) v.findViewById(R.id.spinnerModeloVeiculo);
        spAnos = (Spinner) v.findViewById(R.id.spinnerAnoVeiculo);
        loading = (ProgressBar) v.findViewById(R.id.loading);
        btnBuscarPeca = (Button) v.findViewById(R.id.btnBuscarPorVeiculo);

        btnBuscarPeca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modelo modeloSelecionado =(Modelo) spModelos.getSelectedItem();
                Ano anoSelecionado = (Ano)spAnos.getSelectedItem();
                Marca marcaSelecionada = (Marca)spMarcas.getSelectedItem();

                Intent intent = new Intent(getActivity(), ListagemProdutoActivity.class);

                intent.putExtra("MARCA", marcaSelecionada);

                intent.putExtra("MODELO", modeloSelecionado);
                //intent.putExtra("ANO", anoSelecionado);

                startActivity(intent);

            }
        });



        database = FirebaseDatabase.getInstance();
        DatabaseReference refMarcas = database.getReference("marcas");

        /*
        Spinner Marcas
         */
        refMarcas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                marcas.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.i("Palma", snapshot.child("nome").getValue().toString());
                    Log.i("KEY_MARCA",snapshot.getKey());
                    Marca marca = new Marca(snapshot.getKey(),snapshot.child("nome").getValue().toString());
                    marcas.add(marca);
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
        ArrayAdapter<Marca> adapter = new ArrayAdapter<Marca>(getActivity(), android.R.layout.simple_list_item_1, marcas);
        spMarcas.setAdapter(adapter);

        loading.setVisibility(View.GONE);

    }


    public void encontraModelos(final Marca marca){
        modelos.clear();
        //anos.clear();
        final CallFirebase callFirebase = new CallFirebase();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference refModelos = database.getReference("modelos");
        refModelos.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    String keyMarca = data.child("marca").getChildren().iterator().next().getKey().toString();
                    if(keyMarca.equals(marca.getKey())){
                        final Modelo modelo = new Modelo();
                        modelo.setKey(data.getKey());
                        modelo.setMarca(marca);
                        modelo.setNome(data.child("modelo").child("nome").getValue().toString());
                        //modelo.setAno();
                        modelos.add(modelo);
                    }


                }

                ArrayAdapter<Modelo> adapterModelo = new ArrayAdapter<Modelo>(getActivity(),
                        android.R.layout.simple_list_item_1,modelos);
                spModelos.setAdapter(adapterModelo);

                spModelos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Modelo modelo = (Modelo)spModelos.getSelectedItem();
                        encontraAnos(modelo);
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
    }

    public void encontraAnos(final Modelo modelo){
        DatabaseReference refModelo = database.getReference("modelos/"+ modelo.getKey()+"/anos");
        refModelo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                CallFirebase callFirebase = new CallFirebase();
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    for (DataSnapshot anoSnapshot : data.getChildren()) {
                        Log.i("ANO_2", anoSnapshot.getKey());
                        final String keyAno = anoSnapshot.getKey();
                        /*
                        callFirebase.getAnoByKey(anoSnapshot.getKey(), new IChamada<Ano>() {
                            @Override
                            public void retorno(Ano ano) {
                                modelo.getAnos().add(ano);
                                Log.i("CALL_ANO", ano.getNome());
                                //Log.i("ANO_SIZE", modelo.getAnos().size() + "");
                            }
                        });
                        */
                        DatabaseReference anos = database.getReference("anos");

                        anos.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot anoSnapShot: dataSnapshot.getChildren()) {
                                    if(keyAno.equals(anoSnapShot.getKey())) {
                                        Log.i("ANO_FUNCAO", anoSnapShot.getValue().toString());
                                        Ano ano = new Ano();
                                        Log.i("ANOS_FUNC",anoSnapShot.child("nome").getValue().toString());
                                        ano.setNome(anoSnapShot.child("nome").getValue().toString());
                                        ano.setCodigo(anoSnapShot.getKey());

                                        modelo.getAnos().add(ano);
                                    }
                                }
                                ArrayAdapter<Ano> adapterAno = new ArrayAdapter<Ano>(getActivity(),android.R.layout.simple_list_item_1,modelo.getAnos());

                                spAnos.setAdapter(adapterAno);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

                Log.i("ANO_SIZE", modelo.getAnos().size() + "");



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    /*
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
    */


}
