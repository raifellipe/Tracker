package com.raiborges.teste19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Historico extends AppCompatActivity {

    RecyclerView histRv;
    Button voltar;
    Button excluir;
    ArrayList<TabTrack> Lista;
    HistoricoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        histRv = findViewById(R.id.hist_rv);
        voltar = findViewById(R.id.hist_voltar);
        excluir = findViewById(R.id.hist_excluir);
        Lista = new ArrayList<TabTrack>();
        new Task().execute();

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ExcludeTask().execute();
            }
        });

    }

    private class ExcludeTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {

            AppDb Db = Room.databaseBuilder(getApplicationContext(),AppDb.class,"TabTrack").fallbackToDestructiveMigration().build();
            Db.clearAllTables();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            finish();
        }
    }

    private class Task extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {

            AppDb Db = Room.databaseBuilder(getApplicationContext(),AppDb.class,"TabTrack").fallbackToDestructiveMigration().build();
            Lista = (ArrayList<TabTrack>) Db.tabTrackDao().getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            adapter = new HistoricoAdapter(Lista);
            histRv.setLayoutManager(new LinearLayoutManager(histRv.getContext(), LinearLayoutManager.VERTICAL, false));
            histRv.setAdapter(adapter);
        }
    }
}
