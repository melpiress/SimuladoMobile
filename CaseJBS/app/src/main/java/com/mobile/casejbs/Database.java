
package com.mobile.casejbs;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class Database {
    private FirebaseFirestore db;
    private CollectionReference veiculosRef;
    private CollectionReference usuariosRef;
    private Context context;

    public Database(Context context) {  
        this.context = context;
        db = FirebaseFirestore.getInstance();
        veiculosRef = db.collection("veiculos");
        usuariosRef = db.collection("usuario");
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        veiculosRef.add(veiculo)
                .addOnSuccessListener(documentReference -> {
                    String documentId = documentReference.getId();
                    veiculo.setId(documentId);

                    veiculosRef.document(documentId).set(veiculo)
                            .addOnSuccessListener(aVoid ->
                                    Toast.makeText(context, "Veículo adicionado com sucesso!", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e ->
                                    Toast.makeText(context, "Erro ao atualizar veículo", Toast.LENGTH_SHORT).show());
                })
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Erro ao adicionar veículo", Toast.LENGTH_SHORT).show());
    }
    public void atualizarVeiculo(String placa, String usuario) {
        veiculosRef.whereEqualTo("placa", placa).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                String id = doc.getId();
                veiculosRef.document(id)
                        .update("placa", placa, "email", usuario);
            }
        });
    }

    public void registrarSaidaVeiculo(String placa, Date dataHoraSaida) {
        veiculosRef.whereEqualTo("placa", placa).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                String id = doc.getId();
                veiculosRef.document(id)
                        .update("dataHoraSaida", dataHoraSaida, "ativo", false)
                        .addOnSuccessListener(aVoid ->
                                Toast.makeText(context, "Saída registrada com sucesso!", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e ->
                                Toast.makeText(context, "Erro ao registrar saída", Toast.LENGTH_SHORT).show());
            }
        });
    }

    public void listarVeiculosNoEstacionamento(List<Veiculo> veiculos, AdapterVeiculo adapterVeiculo) {
        veiculosRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    Veiculo v = doc.toObject(Veiculo.class);
                    if (v.isAtivo()) {
                        v.setId(doc.getId());
                        veiculos.add(v);
                    }
                }
                adapterVeiculo.notifyDataSetChanged();
                Toast.makeText(context, "Encontrados " + veiculos.size() + " veículos no estacionamento", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Erro ao listar veículos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void adicionarUsuario(Usuario usuario) {
        usuariosRef.add(usuario)
                .addOnSuccessListener(documentReference -> {
                    String documentId = documentReference.getId();
                    usuario.setId(documentId);

                    veiculosRef.document(String.valueOf(documentId)).set(usuario)
                            .addOnSuccessListener(aVoid ->
                                    Toast.makeText(context, "Usuário adicionado com sucesso!", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e ->
                                    Toast.makeText(context, "Erro ao atualizar usuário", Toast.LENGTH_SHORT).show());
                })
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Erro ao adicionar usuário", Toast.LENGTH_SHORT).show());
    }

    public interface AdminCallback {
        void onAdminVerified(boolean isAdmin, String message);
        void onError(String error);
    }

    public interface LoginCallback {
        void onLoginSuccess(Usuario usuario);
        void onLoginFailure(String mensagem);
    }

    public void verificarAdmin(String email, String senha, AdminCallback callback) {
        usuariosRef.whereEqualTo("email", email)
                .whereEqualTo("senha", senha)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            DocumentSnapshot doc = task.getResult().getDocuments().get(0);
                            Usuario usuario = doc.toObject(Usuario.class);

                            if ("admin".equals(usuario.getTipoUsuario())) {
                                callback.onAdminVerified(true, "Admin verificado com sucesso!");
                            } else {
                                callback.onAdminVerified(false, "Usuário não possui permissões de administrador");
                            }
                        } else {
                            callback.onAdminVerified(false, "Email ou senha inválidos");
                        }
                    } else {
                        callback.onError("Erro ao conectar ao banco de dados");
                    }
                });

    }

    public void excluirRegistrosMesAnterior(AdminCallback callback) {
        // Calcular data limite (último dia do mês anterior)
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date dataLimite = cal.getTime();

        veiculosRef.whereLessThanOrEqualTo("dataHoraEntrada", dataLimite)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> documentos = task.getResult().getDocuments();

                        if (documentos.isEmpty()) {
                            callback.onAdminVerified(true, "Nenhum registro do mês anterior encontrado.");
                            return;
                        }

                        final int total = documentos.size();
                        final int[] excluidos = {0};
                        final boolean[] erroOcorreu = {false};

                        for (DocumentSnapshot doc : documentos) {
                            doc.getReference().delete()
                                    .addOnSuccessListener(aVoid -> {
                                        excluidos[0]++;
                                        if (excluidos[0] == total && !erroOcorreu[0]) {
                                            callback.onAdminVerified(true,
                                                    "Excluídos " + excluidos[0] + " registros do mês anterior.");
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        erroOcorreu[0] = true;
                                        callback.onError("Erro ao excluir algum registro: " + e.getMessage());
                                    });
                        }

                    } else {
                        callback.onError("Erro ao buscar registros para exclusão");
                    }
            });
    }

    //public void verificarLogin(String email, String senha, Database.LoginCallback callback) {
//        usuariosRef.whereEqualTo("email", email)
//                .whereEqualTo("senha", senha)
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        if (!task.getResult().isEmpty()) {
//                            // Login bem-sucedido
//                            QueryDocumentSnapshot doc = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
//                            Usuario usuario = doc.toObject(Usuario.class);
//                            callback.onLoginSuccess(usuario);
//                        } else {
//                            callback.onLoginFailure("Email ou senha inválidos");
//                        }
//                    } else {
//                        callback.onLoginFailure("Erro ao conectar ao banco de dados");
//                    }
//                });


}