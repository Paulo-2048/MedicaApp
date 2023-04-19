package br.ucsal.medicaapp;

import java.util.ArrayList;

public class MedReminderList {

    private static ArrayList<MedReminder> listaMedReminder;

    private static MedReminderList instance = null;

    private MedReminderList() {
        listaMedReminder = new ArrayList<>();
    }

    public static MedReminderList getInstance() {
        if (instance == null) {
            instance = new MedReminderList();
        }
        return instance;
    }

    public ArrayList<MedReminder> getListaMedReminder() {
        return listaMedReminder;
    }

    public int getListaMedReminderSize() {
        if (listaMedReminder == null) {
            return 0;
        }
        return listaMedReminder.size();
    }

    public MedReminder getMedReminder(int index) {
        return listaMedReminder.get(index);
    }

    public void setListaMedReminder(ArrayList<MedReminder> listaMedReminder) {
        MedReminderList.listaMedReminder = listaMedReminder;
    }

    public void setMedReminder(int index, MedReminder medReminder) {
        listaMedReminder.set(index, medReminder);
    }

    public void adicionarMedReminder(MedReminder medReminder) {
        listaMedReminder.add(medReminder);
    }

    public void removerMedReminder(MedReminder medReminder) {
        listaMedReminder.remove(medReminder);
    }

    public void removerMedReminder(int index) {
        listaMedReminder.remove(index);
    }

    public void logAllMedReminder() {
        for (MedReminder medReminder : listaMedReminder) {
            medReminder.logMedReminder();
        }
    }
}
