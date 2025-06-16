package com.mobile.casejbs;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragment extends Fragment {

    private static final String param1 = "param1";
    private static final String param2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView direcionar;
    private EditText email;
    private EditText password;
    private Button loginButton;
    private Button admin;
    private Usuario usuarioLogado;
    private Database database;

    public LoginFragment() {
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(param1, param1);
        args.putString(param2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(param1);
            mParam2 = getArguments().getString(param2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        direcionar = view.findViewById(R.id.direcionar);
        loginButton = view.findViewById(R.id.enviar);
        database = new Database(getContext());
        admin = view.findViewById(R.id.admin);

        if (usuarioLogado != null) {
            redirecionarParaHome();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarLogin();
                Bundle envelope = new Bundle();
                envelope.putString("email", email.getText().toString());

                FirstFragment firstFragment = new FirstFragment();
                firstFragment.setArguments(envelope);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, firstFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        direcionar.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  getActivity().getSupportFragmentManager()
                          .beginTransaction()
                          .replace(R.id.container, new CadastroFragment())
                          .commit();
              }
          }
        );

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminFragment adminFragment = new AdminFragment();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, adminFragment)
                        .commit();
            }
        });



        return view;
    }

    private void realizarLogin() {
        String emailText = email.getText().toString().trim();
        String senhaText = password.getText().toString().trim();

        if (emailText.isEmpty() || senhaText.isEmpty()) {
            Toast.makeText(getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

    }
    private void redirecionarParaHome() {
        Fragment fragment = new FirstFragment();

        Bundle usuario = new Bundle();
        usuario.putString("tipoUsuario", usuarioLogado.getTipoUsuario());
        fragment.setArguments(usuario);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

}