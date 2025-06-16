package com.mobile.casejbs;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminFragment extends Fragment {

    private EditText senhaEditText;
    private Button excluir;
    private Button exibir;
    private Database database;

    public AdminFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        senhaEditText = view.findViewById(R.id.senha);
        excluir = view.findViewById(R.id.loginAdm);
        exibir = view.findViewById(R.id.mostrar);
        database = new Database(getContext());

        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirRegistrosAntigos();
            }
        });

        exibir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SecondFragment secondFragment = new SecondFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, secondFragment)
                        .commit();
            }
        });

        return view;
    }

    private void excluirRegistrosAntigos() {
        database.excluirRegistrosMesAnterior(new Database.AdminCallback() {
            @Override
            public void onAdminVerified(boolean success, String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                if (success) {
                    senhaEditText.setText("");
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "Erro: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }


}