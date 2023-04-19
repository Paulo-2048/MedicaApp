package br.ucsal.medicaapp;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import br.ucsal.medicaapp.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setData();

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = new Toolbar(this);
        toolbar.setTitle("Minha Toolbar");
        toolbar.setBackgroundColor(getResources().getColor(R.color.white, null));

        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.home_nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.home_nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.homeNavView, navController);
    }

    private void setData(){
        MedicamentoList listaMedicamentos = MedicamentoList.getInstance();

        List<LocalTime> horarios = new ArrayList<>();
        horarios.add(LocalTime.of(8, 0));
        horarios.add(LocalTime.of(12, 0));
        horarios.add(LocalTime.of(18, 0));

        // Frequencia: 1 = diária, 2 = semanal, 3 = mensal
        Medicamento medicamento1 = new Medicamento("Paracetamol", 500, 1, 1, horarios);
        Medicamento medicamento2 = new Medicamento("Dipirona", 500, 1, 1, horarios);
        Medicamento medicamento3 = new Medicamento("Amoxicilina", 500, 1, 1, horarios);
        Medicamento medicamento4 = new Medicamento("Ibuprofeno", 500, 1, 1, horarios);
        listaMedicamentos.adicionarMedicamento(medicamento1);
        listaMedicamentos.adicionarMedicamento(medicamento2);
        listaMedicamentos.adicionarMedicamento(medicamento3);
        listaMedicamentos.adicionarMedicamento(medicamento4);
    }

}