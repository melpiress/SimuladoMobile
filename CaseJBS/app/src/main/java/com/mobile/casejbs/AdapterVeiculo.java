package com.mobile.casejbs;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.util.List;

public class AdapterVeiculo extends RecyclerView.Adapter<AdapterVeiculo.ViewHolder> {

    private List<Veiculo> veiculos;

    public AdapterVeiculo(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    @NonNull
    @Override
    public AdapterVeiculo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVeiculo.ViewHolder holder, int position) {
        holder.placa.setText(String.valueOf(veiculos.get(holder.getAdapterPosition()).getPlaca()));
        Log.d("ADAPTER", "Placa: " + veiculos.get(position).getPlaca());
        holder.usuario.setText(String.valueOf(veiculos.get(holder.getAdapterPosition()).getUsuario().getNome()));
        holder.entrada.setText(String.valueOf(veiculos.get(holder.getAdapterPosition()).getDataHoraEntrada()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertaNovo = new AlertDialog.Builder(v.getContext());
                EditText editVeiculo = new EditText(v.getContext());
                EditText editUsuario = new EditText(v.getContext());
                String placa = editVeiculo.getText().toString();
                String usuario = editUsuario.getText().toString();
                editVeiculo.setHint("Placa");
                editUsuario.setHint("Usuário");

                LinearLayout layout = new LinearLayout(v.getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(editVeiculo);
                layout.addView(editUsuario);
                layout.setPadding(30, 0, 30, 0);
                alertaNovo.setView(layout);

                alertaNovo.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Database db = new Database(v.getContext());
                        db.atualizarVeiculo(placa, usuario);
                        Toast.makeText(v.getContext(), "Alteração salva", Toast.LENGTH_SHORT).show();
                    }
                });
                alertaNovo.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return veiculos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView placa, entrada, saida, usuario;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placa = itemView.findViewById(R.id.placa);
            entrada =  itemView.findViewById(R.id.entrada);
            usuario = itemView.findViewById(R.id.usuario);
        }
    }
}
