package br.ucsal.medicaapp.ui.listMedReminder;

import android.app.TimePickerDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import br.ucsal.medicaapp.MedReminderList;
import br.ucsal.medicaapp.R;

public class ListMedReminderAdapter extends RecyclerView.Adapter<ListMedReminderAdapter.ViewHolder>{
    private MedReminderList listMedReminder;


    public ListMedReminderAdapter() {
        listMedReminder = MedReminderList.getInstance();

        //listMedReminder.logAllMedReminder();
        Log.d("REMINDER", "onCreateViewHolder: XXXX " + listMedReminder.getListaMedReminderSize());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_med_reminder_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nome.setText(listMedReminder.getMedReminder(position).getNome());
        holder.horario.setText(listMedReminder.getMedReminder(position).getHorario().toString());


        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = setTimeView(holder);
                timePickerDialog.show();

                }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                listMedReminder.removerMedReminder(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, listMedReminder.getListaMedReminderSize());
            }
        });
    }

    private void onSaveUpdate(ViewHolder holder){
        String finalHorario = holder.horario.getText().toString();
        listMedReminder.getMedReminder(holder.getAdapterPosition()).setHorario(LocalTime.parse(finalHorario, DateTimeFormatter.ofPattern("HH:mm")));
    }

    @Override
    public int getItemCount() {
        return listMedReminder.getListaMedReminderSize();
    }

    private TimePickerDialog setTimeView(ViewHolder holder){
        TextView timeView = holder.horario;
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

                onSaveUpdate(holder);
            }
        }, hour, minute, true);
        return timePickerDialog;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nome;
        private TextView horario;

        private Button buttonEdit;

        private Button buttonDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.listMedRemiderItem_textView_medName);
            horario = itemView.findViewById(R.id.listMedRemiderItem_textView_medTime);
            buttonEdit = itemView.findViewById(R.id.listMedRemiderItem_button_edit);
            buttonDelete = itemView.findViewById(R.id.listMedRemiderItem_button_delete);
        }
    }

}
