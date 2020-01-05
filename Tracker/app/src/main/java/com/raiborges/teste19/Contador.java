package com.raiborges.teste19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class Contador extends AppCompatActivity {

    TextView Passos;
    TextView Metros;
    RecyclerView Rview;
    Button Parar;
    ContadorAdapter adapter;
    TrackHandler Tracker;
    String Nome;
    int tmp;
    double dist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);
        final Bundle extra = getIntent().getExtras();
        Nome = extra.getString("Nome");
        tmp = extra.getInt("Tempo");
        try {
            dist = Double.parseDouble(extra.getString("Dist"));
        }catch (Exception e){
            Toast.makeText(Contador.this, "Problema ao carregar distância escolhida, usando 0.5m", Toast.LENGTH_LONG);
        }
        Tracker = new TrackHandler(this, tmp, dist);
        Passos = findViewById(R.id.contador_passos);
        Metros = findViewById(R.id.contador_metros);
        Rview = findViewById(R.id.contador_rv);
        Parar = findViewById(R.id.contador_parar);
        adapter = new ContadorAdapter((ArrayList<String>)Tracker.getPercurso());
        Rview.setLayoutManager(new LinearLayoutManager(Rview.getContext(), LinearLayoutManager.VERTICAL, false));
        Parar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tracker.stopTrack();
                new Salva().execute();
            }
        });
        Rview.setAdapter(adapter);
        Tracker.startTrack();
        Passos.setText("0");
        Metros.setText("0");

    }


    public void atualizaViews(){
        adapter.filterList(this.Tracker.getPercurso());
        Passos.setText(this.Tracker.getnPassos()+"");
        Metros.setText(String.format(("%.2f"),this.Tracker.getDistPercorrida())+" Metros");

    }


    private class TrackHandler {

        private class LocationHandle{
            public String latitude, longitude;
            public  String controler;

            LocationHandle(String latitude, String longitude, String controler){
                this.latitude = latitude;
                this.longitude = longitude;
                this.controler = controler;
            }

            public String toString(){

                return controler + " \n" + "Latitude:" + this.latitude + " \n" + "Longitude" + this.longitude;
            }

        }

        private Context atualContext;
        private ArrayList<LocationHandle> loc = new ArrayList<>();
        private ArrayList<String> percurso;
        private final FirebaseDatabase database = FirebaseDatabase.getInstance();
        private LocationListener listener;
        private LocationManager locationManager;
        private int tempo;
        private float distancia;
        private int nPassos;
        private double distPercorrida;

        TrackHandler(Context atualContext, int tempo, double dist){
            this.atualContext = atualContext;
            this.tempo = tempo*1000;
            this.nPassos = 0;
            this.distPercorrida = 0;
            this.percurso = new ArrayList<>();
            this.distancia = (float) dist;
        }

        public int getnPassos() {
            return nPassos;
        }

        public double getDistPercorrida() {
            return distPercorrida;
        }

        public ArrayList<String> getPercurso() {
            return percurso;
        }

        public void stopTrack(){
            locationManager.removeUpdates(listener);
        }


        @SuppressLint("MissingPermission")
        public void startTrack(){


            listener = new LocationListener() {
                Calendar c = Calendar.getInstance();
                @Override
                public void onLocationChanged(Location location) {
                    DatabaseReference myRef = database.getReference("Log"+Nome);
                    LocationHandle aux = new LocationHandle(""+location.getLatitude(),""+location.getLongitude(),  c.getTime().toString());
                    loc.add(aux);
                    nPassos++;
                    distPercorrida+=(double)distancia;
                    percurso.add(aux.toString());
                    myRef.setValue(loc);
                    atualizaViews();
                    Log.e("chamou o location", "ihuu");
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

            try {
                locationManager = (LocationManager) atualContext.getSystemService(Context.LOCATION_SERVICE);

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        tempo,          // intervalo minimo
                        distancia,      // distancia minima.
                        listener);
            }catch (Exception e){
                Log.e("erro", "catch permission");
                Toast.makeText(atualContext,"Por favor, aceite as permissões",Toast.LENGTH_LONG).show();
            }

        }


    }


    private class Salva extends AsyncTask{


        @Override
        protected Object doInBackground(Object[] objects) {

            AppDb Db = Room.databaseBuilder(getApplicationContext(),AppDb.class,"TabTrack").fallbackToDestructiveMigration().build();
            Db.tabTrackDao().insertAll(new TabTrack(Nome, Tracker.getnPassos(), Tracker.getDistPercorrida()));

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            finish();
        }
    }

}
