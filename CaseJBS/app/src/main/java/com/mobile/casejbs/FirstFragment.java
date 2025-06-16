package com.mobile.casejbs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobile.casejbs.databinding.FragmentFirstBinding;

import java.time.LocalDateTime;
import java.util.Date;

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
        Bundle ler = this.getArguments();
        binding.enviaPlaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String placa = binding.placa.getText().toString();
                Usuario usuario = new Usuario(ler.getString("email"));
                Veiculo novoVeiculo = new Veiculo(placa, new Date(), usuario);

                adicionarVeiculo(novoVeiculo);
                binding.placa.setText("");
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new LoginFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        binding.remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String placa = binding.placa.getText().toString();
                removerVeiculo(placa, new Date());
                binding.placa.setText("");
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new LoginFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        Database db = new Database(getContext());
        db.adicionarVeiculo(veiculo);
    }

    public void removerVeiculo(String placa, Date dataHoraSaida) {
        Database db = new Database(getContext());
        db.registrarSaidaVeiculo(placa, dataHoraSaida);
    }
}