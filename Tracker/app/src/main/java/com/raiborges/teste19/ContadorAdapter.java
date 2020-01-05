package com.raiborges.teste19;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContadorAdapter extends RecyclerView.Adapter<ContadorAdapter.ContadorViewHolder> {

    private ArrayList<String> Lista;

    static class ContadorViewHolder extends RecyclerView.ViewHolder {
        private TextView adapterText;


        public ContadorViewHolder(@NonNull View itemView) {
            super(itemView);
            adapterText = itemView.findViewById(R.id.adapterText);

        }
    }

    ContadorAdapter(ArrayList<String> Localz) {

        this.Lista = Localz;

    }

    @NonNull
    @Override
    public ContadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contador_adapter,
                parent, false);
        ContadorAdapter.ContadorViewHolder evh = new ContadorAdapter.ContadorViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ContadorViewHolder holder, int position) {
        holder.adapterText.setText(Lista.get(position));
    }

    @Override
    public int getItemCount() {
        return Lista.size();
    }

    void filterList(ArrayList<String> filteredList) {//filtra a lista
        Lista = filteredList;
        notifyDataSetChanged();
    }

}



