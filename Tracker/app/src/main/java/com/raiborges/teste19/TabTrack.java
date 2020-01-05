package com.raiborges.teste19;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TabTrack {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    int ID;

    @ColumnInfo(name = "Nome")
    String Nome;

    @ColumnInfo(name = "Passos")
    int Passos;

    @ColumnInfo(name = "Distancia")
    double Distancia;

    public TabTrack(String Nome, int passos, double distancia){
        this.Nome = Nome;
        this.Passos = passos;
        this.Distancia = distancia;
    }

    public TabTrack(){}
}
