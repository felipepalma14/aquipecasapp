package palma.felipe.aquipecas.service;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import palma.felipe.aquipecas.model.Ano;

/**
 * Created by Roberlandio on 30/12/2016.
 */
public class CallFirebase {
    private Ano ano = null;
    public  CallFirebase(){}


    public void getAnoByKey(final String keyAno, final IChamada<Ano> callback){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
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

                        callback.retorno(ano);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //return ano;
    }



}
