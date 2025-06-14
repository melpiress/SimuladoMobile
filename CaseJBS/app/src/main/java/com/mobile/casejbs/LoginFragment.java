package com.mobile.casejbs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView direcionar;
    private EditText email;
    private EditText password;
    private Usuario usuarioLogado;


    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        direcionar = view.findViewById(R.id.direcionar);

        if (usuarioLogado != null) {
            Fragment fragment = new FirstFragment();

            Bundle usuario = new Bundle();
            usuario.putString("tipoUsuario", usuarioLogado.getTipoUsuario());
            fragment.setArguments(usuario);

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }


        direcionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new CadastroFragment())
                        .commit();
            }
        });

        return view;
    }

//        public void verificarLogin(String email, String senha, Context context, LoginCallback callback) {
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//            db.collection("usuarios")
//                    .whereEqualTo("email", email)
//                    .whereEqualTo("senha", senha)
//                    .get()
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            if (!task.getResult().isEmpty()) {
//                                DocumentSnapshot doc = task.getResult().getDocuments().get(0);
//                                Usuario usuario = doc.toObject(Usuario.class);
//                                callback.onLoginSuccess(usuario);
//                            } else {
//                                callback.onLoginFailure("Email ou senha inválidos");
//                            }
//                        } else {
//                            callback.onLoginFailure("Erro ao conectar ao banco");
//                        }
//                    });
//        }
}