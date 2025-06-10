package com.mobile.casejbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterVeiculo extends RecyclerView.Adapter<AdapterVeiculo.ViewHolder> {
    private Database db;

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
        holder.placa.setText(veiculos.get(holder.getAdapterPosition()).getPlaca());
        holder.entrada.setText(String.valueOf(veiculos.get(holder.getAdapterPosition()).getDataHoraEntrada()));
        holder.saida.setText(String.valueOf(veiculos.get(holder.getAdapterPosition()).getDataHoraSaida()));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                db.registrarSaidaVeiculo(Integer.parseInt(String.valueOf(holder.usuario)));
                return true;
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
            saida =  itemView.findViewById(R.id.saida);
            usuario = itemView.findViewById(R.id.usuario);
        }
    }
}
