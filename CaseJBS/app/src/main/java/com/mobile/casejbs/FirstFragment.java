package com.mobile.casejbs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.mobile.casejbs.databinding.FragmentFirstBinding;

import java.time.LocalDateTime;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.enviaPlaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText placa = new EditText(getContext());
                Veiculo novoVeiculo = new Veiculo(placa.getText().toString(), LocalDateTime.now());

                NavHostFragment navHost = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.FirstFragment);
                Fragment fragAtual = navHost.getChildFragmentManager().getPrimaryNavigationFragment();
                if (fragAtual instanceof FirstFragment) {
                    ((SecondFragment) fragAtual).adicionarVeiculo(novoVeiculo);
                }
                Toast.makeText(getContext(), "Veículo adicionado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}