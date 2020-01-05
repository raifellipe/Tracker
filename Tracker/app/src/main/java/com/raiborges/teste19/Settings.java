package com.raiborges.teste19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    EditText Tempo;
    EditText Nome;
    EditText Dist;
    Button Finaliza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Tempo = findViewById(R.id.setting_Tempo);
        Nome = findViewById(R.id.setting_Nome);
        Dist = findViewById(R.id.setting_Dist);
        Finaliza = findViewById(R.id.settings_fin);
        Finaliza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valida()){

                    Intent intent = new Intent(Settings.this,Contador.class);
                    intent.putExtra("Nome", Nome.getText().toString());
                    intent.putExtra("Dist", Dist.getText().toString());
                    intent.putExtra("Tempo", Tempo.getText().toString());
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(Settings.this,"Por favor, preencha todas as informações", Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean valida(){

        return (!Tempo.getText().toString().isEmpty()&&!Nome.getText().toString().isEmpty()&&!Dist.getText().toString().isEmpty());
    }
}
