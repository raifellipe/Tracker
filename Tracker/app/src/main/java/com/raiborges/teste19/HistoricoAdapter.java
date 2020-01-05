package com.raiborges.teste19;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.HistoricoViewHolder> {

    private ArrayList<TabTrack> Lista;

    static class HistoricoViewHolder extends RecyclerView.ViewHolder {
        private TextView nome;
        private TextView passos;
        private TextView distancia;


        public HistoricoViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.his_nome);
            passos = itemView.findViewById(R.id.his_passos);
            distancia = itemView.findViewById(R.id.his_dist);

        }
    }

    HistoricoAdapter(ArrayList<TabTrack> Localz) {

        this.Lista = Localz;

    }

    @NonNull
    @Override
    public HistoricoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.historico_adapter,
                parent, false);
        HistoricoViewHolder evh = new HistoricoAdapter.HistoricoViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricoViewHolder holder, int position) {
        TabTrack aux = Lista.get(position);
        holder.nome.setText("Nome: "+aux.Nome);
        holder.passos.setText("Número de passos: "+aux.Passos);
        holder.distancia.setText("Distância Percorrida"+aux.Distancia);
    }

    @Override
    public int getItemCount() {
        return Lista.size();
    }

    void filterList(ArrayList<TabTrack> filteredList) {//filtra a lista
        Lista = filteredList;
        notifyDataSetChanged();
    }

}



