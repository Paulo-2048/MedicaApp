package br.ucsal.medicaapp.ui.listMed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListMedViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ListMedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is list fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}