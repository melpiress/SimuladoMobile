package com.mobile.casejbs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.casejbs.databinding.FragmentSecondBinding;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    RecyclerView rv;
    List<Veiculo> veiculos = new ArrayList<>();
    private Database db = new Database(getContext());


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AdapterVeiculo adapterVeiculo = new AdapterVeiculo(veiculos);
        binding.rv.setAdapter(adapterVeiculo);
        binding.rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        db.listarVeiculosNoEstacionamento();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}