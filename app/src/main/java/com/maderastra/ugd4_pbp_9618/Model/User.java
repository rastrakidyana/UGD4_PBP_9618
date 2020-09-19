package com.maderastra.ugd4_pbp_9618.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "number")
    public int numberU;

    @ColumnInfo(name = "name")
    public String nameU;

    @ColumnInfo(name = "age")
    public int ageU;

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getNumberU() {return numberU;}

    public void setNumberU(int numberU) {this.numberU = numberU;}

    public String getNameU() { return nameU; }

    public void setNameU(String nameU) { this.nameU = nameU; }

    public int getAgeU() { return ageU; }

    public void setAgeU(int ageU) { this.ageU = ageU; }
}
