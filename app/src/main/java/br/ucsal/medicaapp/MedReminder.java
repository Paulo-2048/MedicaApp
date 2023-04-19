package br.ucsal.medicaapp;

import android.util.Log;

import java.time.LocalTime;

public class MedReminder {
    private String nome;
    private LocalTime horario;

    public MedReminder(String nome, LocalTime horario) {
        this.nome = nome;
        this.horario = horario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public void logMedReminder() {
        Log.d("MedReminder", "Nome: " + nome + " Horário: " + horario);
    }
}
