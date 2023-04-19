package br.ucsal.medicaapp.ui.addMed;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.ucsal.medicaapp.Medicamento;
import br.ucsal.medicaapp.MedicamentoList;
import br.ucsal.medicaapp.R;
import br.ucsal.medicaapp.databinding.FragmentAddMedBinding;

public class AddMedFragment extends Fragment {

    private FragmentAddMedBinding binding;

    private MedicamentoList listaMedicamentos;

    private TextInputLayout medName, medDose, medFreqQTD, medNewTime;
    private RadioGroup medFreqGroup;
    private RadioButton freqDaily, freqWeekly, freqMonthly;
    private LinearLayout medTimesLayout;
    private LocalTime[] medTimes;

    private Switch medReminderActive;
    private Button addButton, addNewTimeButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        listaMedicamentos = MedicamentoList.getInstance();

        binding = FragmentAddMedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        medName = root.findViewById(R.id.frag_addMed_textInputView_medName);
        medDose = root.findViewById(R.id.frag_addMed_textInputView_medDose);
        medFreqQTD = root.findViewById(R.id.frag_addMed_textInputView_medFreqQTD);
        medNewTime = root.findViewById(R.id.frag_addMed_textInputView_addHorario);
        medFreqGroup = root.findViewById(R.id.frag_addMed_radioGroup_freq);
        freqDaily = root.findViewById(R.id.frag_addMed_radioButton_freqDaily);
        freqWeekly = root.findViewById(R.id.frag_addMed_radioButton_freqWeekly);
        freqMonthly = root.findViewById(R.id.frag_addMed_radioButton_freqMonthly);
        medTimesLayout = root.findViewById(R.id.frag_addMed_linearLayout_horariosAdicionados);
        medReminderActive = root.findViewById(R.id.frag_addMed_switch_reminderActive);
        addNewTimeButton = root.findViewById(R.id.frag_addMed_button_adicionarHorario);
        addButton = root.findViewById(R.id.frag_addMed_button_save);

        medNewTime.getEditText().setInputType(InputType.TYPE_NULL);
        medNewTime.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = setTimeView(medNewTime.getEditText());
                timePickerDialog.show();
            }
        });

        addNewTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTime = medNewTime.getEditText().getText().toString();
                if (newTime.length() == 5) {
                    TextView newTimeView = new TextView(getContext());
                    newTimeView.setText(newTime);
                    newTimeView.setPadding(0, 5, 0, 5);
                    medTimesLayout.addView(newTimeView);
                }
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = medName.getEditText().getText().toString();
                double dose = Double.parseDouble(medDose.getEditText().getText().toString());
                int freqQTD = Integer.parseInt(medFreqQTD.getEditText().getText().toString());
                int freqType = 0;
                if (freqDaily.isChecked())
                    freqType = 1;
                else if (freqWeekly.isChecked())
                    freqType = 2;
                else if (freqMonthly.isChecked())
                    freqType = 3;

                boolean reminderActive = medReminderActive.isChecked();
                List<LocalTime> medTimesList = new ArrayList<LocalTime>();

                for (int i = 0; i < medTimesLayout.getChildCount(); i++) {
                    TextView timeView = (TextView) medTimesLayout.getChildAt(i);
                    String time = timeView.getText().toString();
                    int hour = Integer.parseInt(time.substring(0, 2));
                    int minute = Integer.parseInt(time.substring(3, 5));
                    Log.d("HORA", "onClick: " + hour + ":" + minute + " added to medTimesList");
                    medTimesList.add(LocalTime.of(hour, minute));
                }



                Medicamento newMedicamento = new Medicamento(name, dose, freqQTD, freqType, medTimesList);
                newMedicamento.setReminderActive(reminderActive);

                listaMedicamentos.adicionarMedicamento(newMedicamento);
                clearFields();
            }
        });

        return root;
    }

    private void clearFields(){
        medName.getEditText().setText("");
        medDose.getEditText().setText("");
        medFreqQTD.getEditText().setText("");
        medNewTime.getEditText().setText("");
        medFreqGroup.clearCheck();
        freqDaily.setChecked(true);
        medTimesLayout.removeAllViews();
        medReminderActive.setChecked(false);
    }

    private TimePickerDialog setTimeView(TextView timeView){
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}