package br.ucsal.medicaapp.ui.listMedReminder;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

import br.ucsal.medicaapp.MedReminder;
import br.ucsal.medicaapp.MedReminderList;
import br.ucsal.medicaapp.Medicamento;
import br.ucsal.medicaapp.MedicamentoList;
import br.ucsal.medicaapp.R;
import br.ucsal.medicaapp.databinding.FragmentNotMedBinding;

public class ListMedReminderFragment extends Fragment {

    private FragmentNotMedBinding binding;

    ListMedReminderAdapter adapter = new ListMedReminderAdapter();

    private MedReminderList listMedReminder;

    private MedicamentoList listMedicamentos;

    private Spinner ddlMedicamentos;
    private TextInputEditText timeViewEditText;
    private RecyclerView listReminder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotMedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        AddTimeInformations(root);

        RecycleViewManager(root);

        AddTime(root);

        return root;
    }

    private void SetNotifications(String medName, LocalTime notificationTime) {
        String medMessage = "Hora de tomar o remédio " + medName + "!";
        Calendar calendar = Calendar.getInstance();
        Calendar calendarNotificationTime = Calendar.getInstance();
        calendarNotificationTime.set(Calendar.HOUR_OF_DAY, notificationTime.getHour());
        calendarNotificationTime.set(Calendar.MINUTE, notificationTime.getMinute());
        calendarNotificationTime.set(Calendar.SECOND, 0);

        NotificationAction.scheduleNotification(getContext(), medName, calendarNotificationTime);
        NotificationAction.shootNotification(getContext(), medName, medMessage);
    }

    private void AddTimeInformations(View root) {
        // Inicializando a dropdown list
        listMedReminder = MedReminderList.getInstance();
        listMedicamentos = MedicamentoList.getInstance();

        ArrayList<String> medNames = new ArrayList<>();

        for (Medicamento medicamento : listMedicamentos.getListaMedicamentos()) {
            if (!medNames.contains(medicamento.getNome())) {
                medNames.add(medicamento.getNome());
            }
        }

        ArrayAdapter<String> adapterDDL = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, medNames);

        ddlMedicamentos = root.findViewById(R.id.frag_notMed_spinner_addMedicamentoDDL);
        ddlMedicamentos.setAdapter(adapterDDL);

        // Definindo o botão de adicionar horario
        timeViewEditText = root.findViewById(R.id.frag_notMed_editText_addHorario);
        timeViewEditText.setInputType(InputType.TYPE_NULL);

        timeViewEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = setTimeView(timeViewEditText, listMedReminder);
                timePickerDialog.show();
            }
        });
    }

    private void RecycleViewManager(View root) {
        // Definindo o RecyclerView dos lembretes
        listReminder = root.findViewById(R.id.frag_not_med_recyclerView_medReminders);
        listReminder.setLayoutManager(new LinearLayoutManager(getContext()));

        listReminder.setAdapter(adapter);
    }

    private void AddTime(View root) {
        // Definindo o botão de adicionar lembrete
        Button btnAddReminder = root.findViewById(R.id.frag_notMed_button_adicionarLembrete);

        btnAddReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String medName = ddlMedicamentos.getSelectedItem().toString();
                LocalTime time = LocalTime.parse(timeViewEditText.getText().toString());

                MedReminder tempReminder = new MedReminder(medName, time);
                listMedReminder.adicionarMedReminder(tempReminder);
                adapter.notifyDataSetChanged();
                SetNotifications(medName, time);
            }
        });
    }

    private TimePickerDialog setTimeView(TextInputEditText timeView, MedReminderList listMedReminder){
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(timeView.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay < 10 && minute < 10)
                    timeView.setText("0" + hourOfDay + ":0" + minute);
                else if (hourOfDay < 10)
                    timeView.setText("0" + hourOfDay + ":" + minute);
                else if (minute < 10)
                    timeView.setText(hourOfDay + ":0" + minute);
                else
                    timeView.setText(hourOfDay + ":" + minute);
            }
        }, hour, minute, true);
        return timePickerDialog;
    }


    public void addMedReminder(String nome, LocalTime horario){
        MedReminder tempReminder = new MedReminder(nome, horario);
        listMedReminder.adicionarMedReminder(tempReminder);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}