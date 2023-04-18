package br.ucsal.medicaapp.ui.listMed;

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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.ucsal.medicaapp.Medicamento;
import br.ucsal.medicaapp.R;

public class ListMedAdapter extends RecyclerView.Adapter<ListMedAdapter.ViewHolder>{
    private List<String> data;
    private List<Medicamento> listaMedicamentos;

    public ListMedAdapter() {
        data = new ArrayList<>();
        data.add("Item 1");
        data.add("Item 2");
        data.add("Item 3");

        listaMedicamentos = new ArrayList<>();
        List<LocalTime> horarios = new ArrayList<>();
        horarios.add(LocalTime.of(8, 0));
        horarios.add(LocalTime.of(12, 0));
        horarios.add(LocalTime.of(18, 0));

        // Frequencia: 1 = diária, 2 = semanal, 3 = mensal
        Medicamento medicamento1 = new Medicamento("Paracetamol", 500, 1, 1, horarios);
        Medicamento medicamento2 = new Medicamento("Dipirona", 500, 1, 1, horarios);
        Medicamento medicamento3 = new Medicamento("Amoxicilina", 500, 1, 1, horarios);
        Medicamento medicamento4 = new Medicamento("Ibuprofeno", 500, 1, 1, horarios);

        listaMedicamentos.add(medicamento1);
        listaMedicamentos.add(medicamento2);
        listaMedicamentos.add(medicamento3);
        listaMedicamentos.add(medicamento4);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_med_item, parent, false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(listaMedicamentos.get(position).getNome());
        holder.textDose.setText(listaMedicamentos.get(position).getDose());
        holder.textFrequencia.setText(listaMedicamentos.get(position).getFrequencia());

        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(holder.textHorarios.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // O usuário selecionou um horário, faça algo com ele aqui
            }
        }, hour, minute, true);

        for (LocalTime horario : listaMedicamentos.get(position).getHorarios()) {
            EditText hourText = new EditText(holder.textHorarios.getContext());
            hourText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            hourText.setPadding(10, 10, 10, 10);
            hourText.setInputType(InputType.TYPE_CLASS_DATETIME);

            hourText.setBackground(null);
            hourText.setInputType(InputType.TYPE_NULL);

            hourText.setText(horario.toString());
            holder.textHorarios.addView(hourText);
        }

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.isEditMode){
                    holder.isEditMode = false;
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
        return data.size();
    }

    private TimePickerDialog setTimeView(TextView timeView){
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(timeView.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeView.setText(hourOfDay + ":" + minute);
            }
        }, hour, minute, true);
        return timePickerDialog;
    }

    private void editableText(boolean editable, ViewHolder holder){

        if (editable){
            holder.textView.setInputType(InputType.TYPE_CLASS_TEXT);
            holder.textDose.setInputType(InputType.TYPE_CLASS_TEXT);
            holder.textFrequencia.setInputType(InputType.TYPE_CLASS_TEXT);
            holder.switchLembrete.setClickable(true);

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
            holder.textFrequencia.setInputType(InputType.TYPE_NULL);
            holder.switchLembrete.setClickable(false);

            for (int i = 0; i < holder.textHorarios.getChildCount(); i++) {
                EditText child = (EditText) holder.textHorarios.getChildAt(i);
                child.setOnClickListener(null);
            }

        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public EditText textView;
        public EditText textDose;
        public EditText textFrequencia;
        public LinearLayout textHorarios;
        public Button buttonEdit;
        public Switch switchLembrete;

        private boolean isEditMode = false;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.listMedItem_textView_title);
            textView.setInputType(InputType.TYPE_NULL);

            textDose = itemView.findViewById(R.id.listMedItem_textView_dose);
            textDose.setInputType(InputType.TYPE_NULL);

            textFrequencia = itemView.findViewById(R.id.listMedItem_textView_frequency);
            textFrequencia.setInputType(InputType.TYPE_NULL);

            textHorarios = itemView.findViewById(R.id.layout_times);

            buttonEdit = itemView.findViewById(R.id.list_med_item_button_btn_edit);
            switchLembrete = itemView.findViewById(R.id.list_ned_item_switch_reminder);
        }
    }
}
