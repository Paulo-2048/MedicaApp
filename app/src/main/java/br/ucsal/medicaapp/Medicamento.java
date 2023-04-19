package br.ucsal.medicaapp;

import android.util.Log;

import java.time.LocalTime;
import java.util.List;

public class Medicamento {
    private String nome;
    private double dose;
    private boolean isReminderActive;

    private LocalTime horarioIngerido;
    private int frequenciaqtd;

    private enum Frequencia {
        DIARIA(1),
        SEMANAL(2),
        MENSAL(3);

        private int valor;

        Frequencia(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return valor;
        }

        public String getText() {
            if (valor == 1) {
                return "dia";
            } else if (valor == 2) {
                return "semana";
            } else if (valor == 3) {
                return "mês";
            }
            return "";
        }
    }

    private Frequencia frequencia;
    private List<LocalTime> horarios;

    public Medicamento(String nome, double dose, int frequenciaqtd, int frequencia, List<LocalTime> horarios) {
        this.nome = nome;
        this.dose = dose;
        this.isReminderActive = true;
        this.frequenciaqtd = frequenciaqtd;
        if (frequencia == 1) {
            this.frequencia = Frequencia.DIARIA;
        } else if (frequencia == 2) {
            this.frequencia = Frequencia.SEMANAL;
        } else if (frequencia == 3) {
            this.frequencia = Frequencia.MENSAL;
        }
        this.horarios = horarios;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isReminderActive() {
        return isReminderActive;
    }

    public void setReminderActive(boolean isReminderActive) {
        this.isReminderActive = isReminderActive;
    }

    public void toggleReminderActive() {
        this.isReminderActive = !this.isReminderActive;
    }

    public String getDose() {
        return dose + " Gramas";
    }

    public String getDoseString() {
        return String.valueOf(dose);
    }

    public void setDose(double dose) {
        this.dose = dose;
    }

    public String getFrequencia() {
        return frequenciaqtd + " vezes por " + frequencia.getText();
    }

    public String getFrequenciaString() {
        return String.valueOf(frequenciaqtd);
    }

    public void setFrequencia(int frequencia) {
        if (frequencia == 1) {
            this.frequencia = Frequencia.DIARIA;
        } else if (frequencia == 2) {
            this.frequencia = Frequencia.SEMANAL;
        } else if (frequencia == 3) {
            this.frequencia = Frequencia.MENSAL;
        }
    }

    public List<LocalTime> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<LocalTime> horarios) {
        this.horarios = horarios;
    }

    public LocalTime getHorarioIngerido() {
        if (horarioIngerido == null) {
            return null;
        }
        return horarioIngerido;
    }

    public void setHorarioIngerido(LocalTime horarioIngerido) {
        this.horarioIngerido = horarioIngerido;
    }

    public void logMedicamento() {
        Log.d("MEDICAMENTO", "MedicamentoName: " + nome);
        Log.d("MEDICAMENTO", "MedicamentoDose: " + dose);
        Log.d("MEDICAMENTO", "MedicamentoFrequenciaQTD: " + frequenciaqtd);
        Log.d("MEDICAMENTO", "MedicamentoFrequencia: " + frequencia.getText());

        for (LocalTime horario : horarios) {
            Log.d("MEDICAMENTO", "MedicamentoHorario: " + horario);
        }

    }
}