package br.ucsal.medicaapp;

import java.util.ArrayList;

public class MedicamentoList {
    private static ArrayList<Medicamento> listaMedicamentos;
    private static MedicamentoList instance = null;

    private MedicamentoList() {
        listaMedicamentos = new ArrayList<>();
    }

    public static MedicamentoList getInstance() {
        if (instance == null) {
            instance = new MedicamentoList();
        }
        return instance;
    }

    public ArrayList<Medicamento> getListaMedicamentos() {
        return listaMedicamentos;
    }

    public int getListaMedicamentosSize() {
        if (listaMedicamentos == null) {
            return 0;
        }
        return listaMedicamentos.size();
    }
    public Medicamento getMedicamento(int index) {
        return listaMedicamentos.get(index);
    }

    public void setListaMedicamentos(ArrayList<Medicamento> listaMedicamentos) {
        MedicamentoList.listaMedicamentos = listaMedicamentos;
    }

    public void setMedicamento(int index, Medicamento medicamento) {
        listaMedicamentos.set(index, medicamento);
    }

    public void adicionarMedicamento(Medicamento medicamento) {
        listaMedicamentos.add(medicamento);
    }

    public void removerMedicamento(Medicamento medicamento) {
        listaMedicamentos.remove(medicamento);
    }

    public void removerMedicamento(int index) {
        listaMedicamentos.remove(index);
    }

    public void logAllMedicamentos() {
        for (Medicamento medicamento : listaMedicamentos) {
            medicamento.logMedicamento();
        }
    }
}
