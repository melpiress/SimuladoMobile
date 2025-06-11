
package com.mobile.casejbs;


import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDateTime;
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
    public void atualizarVeiculo(String id, Veiculo veiculo) {
        veiculosRef.document(id).set(veiculo)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(context, "Veículo atualizado com sucesso!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Erro ao atualizar veículo", Toast.LENGTH_SHORT).show());
    }

    public void registrarSaidaVeiculo(String id, LocalDateTime dataHoraSaida) {
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
}