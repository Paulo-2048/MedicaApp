package br.ucsal.medicaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    // Elelments



    // Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user has already seen the welcome screen
        int currentVersionCode = BuildConfig.VERSION_CODE;
        SharedPreferences prefs = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
        int savedVersionCode = prefs.getInt("VERSION_CODE_KEY", 0);

        Log.d("VERSION_CODE_KEY", String.valueOf(currentVersionCode));
        if (savedVersionCode == currentVersionCode) {
            // The user has already seen the welcome screen, so skip it
            Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
            return;
        }

        // Save the current version code to the shared preferences
        prefs.edit().putInt("VERSION_CODE_KEY", currentVersionCode).apply();

        setContentView(R.layout.activity_main);

        // Criando a lista de funcionalidades
        ArrayList<String> funcionalidades = new ArrayList<String>();
        funcionalidades.add("Agendar consultas");
        funcionalidades.add("Consultar histórico médico");
        funcionalidades.add("Acompanhar resultados de exames");
        funcionalidades.add("Receber lembretes de medicamentos");
        funcionalidades.add("Buscar clínicas e hospitais próximos");

        // Definindo a lista na ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, funcionalidades);
        ListView listaFuncionalidades = (ListView) findViewById(R.id.main_listView_welcome_detail);
        listaFuncionalidades.setAdapter(adapter);

        // Definindo ação do botão
        Button buttonIntoApp = findViewById(R.id.main_button_into_app);
        buttonIntoApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }


}