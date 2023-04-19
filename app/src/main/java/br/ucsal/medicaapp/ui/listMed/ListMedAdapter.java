package br.ucsal.medicaapp.ui.listMed;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.text.InputType;
import android.util.Log;
import android.util.Size;
import android.util.TypedValue;
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

import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.ucsal.medicaapp.Medicamento;
import br.ucsal.medicaapp.MedicamentoList;
import br.ucsal.medicaapp.R;

public class ListMedAdapter extends RecyclerView.Adapter<ListMedAdapter.ViewHolder>{
    private MedicamentoList listaMedicamentos;

    public ListMedAdapter() {
        listaMedicamentos = MedicamentoList.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_med_item, parent, false);



        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(listaMedicamentos.getMedicamento(position).getNome());
        holder.textDose.setText(listaMedicamentos.getMedicamento(position).getDose());
        holder.textFrequencia.setText(listaMedicamentos.getMedicamento(position).getFrequencia());
        holder.switchLembrete.setChecked(listaMedicamentos.getMedicamento(position).isReminderActive());

        Log.d("REMINDER", "NEWWW: " + listaMedicamentos.getMedicamento(position).getNome());

        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        for (LocalTime horario : listaMedicamentos.getMedicamento(position).getHorarios()) {
            EditText hourText = new EditText(holder.textHorarios.getContext());
            hourText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            hourText.setPadding(10, 10, 10, 10);
            hourText.setInputType(InputType.TYPE_CLASS_DATETIME);

            hourText.setBackground(null);
            hourText.setInputType(InputType.TYPE_NULL);

            hourText.setText(horario.toString());
            holder.textHorarios.addView(hourText);
        }

        if(listaMedicamentos.getMedicamento(holder.getAdapterPosition()).getHorarioIngerido() != null){
            holder.takenTime.setText("Ingerido as: " + listaMedicamentos.getMedicamento(holder.getAdapterPosition()).getHorarioIngerido().format(DateTimeFormatter.ofPattern("HH:mm")));
            holder.buttonTaken.setEnabled(false);
        }
        holder.buttonTaken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalTime horarioAtual = LocalTime.now();
                listaMedicamentos.getMedicamento(holder.getAdapterPosition()).setHorarioIngerido(horarioAtual);
                holder.takenTime.setText("Ingerido as: " + horarioAtual.format(DateTimeFormatter.ofPattern("HH:mm")));
                holder.buttonTaken.setEnabled(false);
            }
        });

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.isEditMode){
                    holder.isEditMode = false;
                    onSaveMedicamento(holder);
                    editableText(false, holder);
                    holder.buttonEdit.setText("Editar");
                } else {
                    holder.isEditMode = true;
                    editableText(true, holder);
                    holder.buttonEdit.setText("Salvar");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaMedicamentos.getListaMedicamentosSize();
    }

    public void onSaveMedicamento(ViewHolder holder){
        int position = holder.getAdapterPosition();
        Medicamento medicamento = listaMedicamentos.getMedicamento(position);

        String medName = holder.textView.getText().toString();
        double dose = Double.parseDouble(holder.textDose.getText().toString());
        int frequenciaQTD = Integer.parseInt(holder.textFrequencia.getText().toString());


        int frequencia = 0;

        switch (holder.radioFrequenciaText.getCheckedRadioButtonId()){
            case R.id.daily_radio_button:
                frequencia = 1;
                break;
            case R.id.weekly_radio_button:
                frequencia = 2;
                break;
            case R.id.monthly_radio_button:
                frequencia = 3;
                break;
        }

        ArrayList<LocalTime> horarios = new ArrayList<>();

        for (int i = 0; i < holder.textHorarios.getChildCount(); i++) {
            EditText child = (EditText) holder.textHorarios.getChildAt(i);
            String[] horario = child.getText().toString().split(":");

            String hour = horario[0];
            String minute = horario[1];

            if (hour.length() == 1){
                hour = "0" + hour;
            }

            if (minute.length() == 1){
                minute = "0" + minute;
            }

            String finalHorario = hour + ":" + minute;
            horarios.add(LocalTime.parse(finalHorario, DateTimeFormatter.ofPattern("HH:mm")));
            Log.d("TAG", "onSaveMedicamento: " + finalHorario);
        }

        boolean isReminder = holder.switchLembrete.isChecked();

        Medicamento tempMed = new Medicamento(medName, dose, frequenciaQTD, frequencia, horarios);
        tempMed.setReminderActive(isReminder);

        tempMed.logMedicamento();
        listaMedicamentos.setMedicamento(position, tempMed);
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

    private void editableText(boolean editable, ViewHolder holder){

        if (editable){
            holder.textView.setInputType(InputType.TYPE_CLASS_TEXT);

            holder.textDose.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            holder.textDose.setText(listaMedicamentos.getMedicamento(holder.getAdapterPosition()).getDoseString());

            holder.textFrequencia.setInputType(InputType.TYPE_CLASS_NUMBER);
            holder.textFrequencia.setText(listaMedicamentos.getMedicamento(holder.getAdapterPosition()).getFrequenciaString());

            holder.switchLembrete.setClickable(true);

            holder.radioFrequenciaText.setVisibility(View.VISIBLE);

            for (int i = 0; i < holder.radioFrequenciaText.getChildCount(); i++) {
                holder.radioFrequenciaText.getChildAt(i).setClickable(true);
            }

            for (int i = 0; i < holder.textHorarios.getChildCount(); i++) {
                EditText child = (EditText) holder.textHorarios.getChildAt(i);
                child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setTimeView(child).show();
                    }
                });
            }

        } else {
            holder.textView.setInputType(InputType.TYPE_NULL);

            holder.textDose.setInputType(InputType.TYPE_NULL);
            holder.textDose.setText(listaMedicamentos.getMedicamento(holder.getAdapterPosition()).getDose());

            holder.textFrequencia.setInputType(InputType.TYPE_NULL);
            holder.textFrequencia.setText(listaMedicamentos.getMedicamento(holder.getAdapterPosition()).getFrequencia());

            holder.switchLembrete.setClickable(false);

            holder.radioFrequenciaText.setVisibility(View.GONE);

            for (int i = 0; i < holder.radioFrequenciaText.getChildCount(); i++) {
                holder.radioFrequenciaText.getChildAt(i).setClickable(false );
            }

            for (int i = 0; i < holder.textHorarios.getChildCount(); i++) {
                EditText child = (EditText) holder.textHorarios.getChildAt(i);
                child.setOnClickListener(null);
            }

        }
    }

    public void clearAdapter(){
        bindViewHolder(null, 0);

        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public EditText textView;
        public EditText textDose;
        public EditText textFrequencia;

        public RadioGroup radioFrequenciaText;
        public LinearLayout textHorarios;
        public Button buttonEdit, buttonTaken;
        public Switch switchLembrete;

        public TextView takenTime;
        private boolean isEditMode = false;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.listMedItem_textView_title);
            textView.setInputType(InputType.TYPE_NULL);

            textDose = itemView.findViewById(R.id.listMedItem_textView_dose);
            textDose.setInputType(InputType.TYPE_NULL);

            textFrequencia = itemView.findViewById(R.id.listMedItem_textView_frequency);
            textFrequencia.setInputType(InputType.TYPE_NULL);

            radioFrequenciaText = itemView.findViewById(R.id.listMedItem_radioGroup_frequencia);
            for (int i = 0; i < radioFrequenciaText.getChildCount(); i++) {
                radioFrequenciaText.getChildAt(i).setClickable(false);
            }

            textHorarios = itemView.findViewById(R.id.layout_times);

            takenTime = itemView.findViewById(R.id.list_med_item_textView_taken);

            buttonEdit = itemView.findViewById(R.id.list_med_item_button_btn_edit);
            buttonTaken = itemView.findViewById(R.id.list_med_item_button_btn_taken);
            switchLembrete = itemView.findViewById(R.id.list_ned_item_switch_reminder);
        }
    }
}
