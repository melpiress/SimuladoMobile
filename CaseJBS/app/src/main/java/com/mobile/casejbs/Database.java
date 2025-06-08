
package com.mobile.casejbs;


import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
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
        usuariosRef = db.collection("usuarios");
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

    public void buscarVeiculoPorPlaca(String placa) {
        veiculosRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    Veiculo v = doc.toObject(Veiculo.class);
                    if (v.getPlaca().equals(placa) && v.isAtivo()) {
                        v.setId(doc.getId());
                        Toast.makeText(context, "Veículo encontrado: " + v.getPlaca(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Toast.makeText(context, "Veículo não encontrado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Erro na busca", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buscarVeiculoPorId(String id) {
        veiculosRef.document(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                Veiculo v = task.getResult().toObject(Veiculo.class);
                if (v != null) {
                    v.setId(task.getResult().getId());
                    Toast.makeText(context, "Veículo encontrado: " + v.getPlaca(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Erro ao converter documento", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Veículo não encontrado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void atualizarVeiculo(String id, Veiculo veiculo) {
        veiculosRef.document(id).set(veiculo)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(context, "Veículo atualizado com sucesso!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Erro ao atualizar veículo", Toast.LENGTH_SHORT).show());
    }

    public void registrarSaidaVeiculo(String id, Date dataHoraSaida) {
        veiculosRef.document(id)
                .update("dataHoraSaida", dataHoraSaida, "ativo", false)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(context, "Saída registrada com sucesso!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Erro ao registrar saída", Toast.LENGTH_SHORT).show());
    }

    public void listarVeiculosNoEstacionamento() {
        veiculosRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Veiculo> veiculos = new ArrayList<>();
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    Veiculo v = doc.toObject(Veiculo.class);
                    if (v.isAtivo()) {
                        v.setId(doc.getId());
                        veiculos.add(v);
                    }
                }
                Toast.makeText(context, "Encontrados " + veiculos.size() + " veículos no estacionamento", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Erro ao listar veículos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void listarHistoricoVeiculosPorUsuario(String usuario) {
        veiculosRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Veiculo> veiculos = new ArrayList<>();
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    Veiculo v = doc.toObject(Veiculo.class);
                    if (v.getUsuario().equals(usuario)) {
                        v.setId(doc.getId());
                        veiculos.add(v);
                    }
                }
                Toast.makeText(context, "Histórico carregado: " + veiculos.size() + " registros", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Erro ao carregar histórico", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void excluirVeiculo(String id) {
        veiculosRef.document(id).delete()
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(context, "Veículo excluído com sucesso!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Erro ao excluir veículo", Toast.LENGTH_SHORT).show());
    }

    public void verificarPlacaJaAtiva(String placa) {
        veiculosRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                boolean placaAtiva = false;
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    Veiculo v = doc.toObject(Veiculo.class);
                    if (v.getPlaca().equals(placa) && v.isAtivo()) {
                        placaAtiva = true;
                        break;
                    }
                }
                if (placaAtiva) {
                    Toast.makeText(context, "Placa já está ativa no estacionamento", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Placa disponível", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Erro ao verificar placa", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void contarVeiculosNoEstacionamento() {
        veiculosRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                int count = 0;
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    Veiculo v = doc.toObject(Veiculo.class);
                    if (v.isAtivo()) {
                        count++;
                    }
                }
                Toast.makeText(context, "Total de veículos: " + count, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Erro ao contar veículos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}