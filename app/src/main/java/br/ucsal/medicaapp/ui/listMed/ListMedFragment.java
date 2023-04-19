package br.ucsal.medicaapp.ui.listMed;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.ucsal.medicaapp.R;
import br.ucsal.medicaapp.databinding.FragmentListMedBinding;

public class ListMedFragment extends Fragment {

    private FragmentListMedBinding binding;
    ListMedAdapter adapter = new ListMedAdapter();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentListMedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView listMeds = root.findViewById(R.id.frag_listMed_recyclerView_medicamentos);
        listMeds.setLayoutManager(new LinearLayoutManager(getContext()));

        listMeds.setAdapter(adapter);

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}