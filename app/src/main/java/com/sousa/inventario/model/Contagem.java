package com.sousa.inventario.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Joao on 06/06/2015.
 */
public class Contagem extends RealmObject {

    @PrimaryKey
    private String id;
    private String centro;
    private String nave;
    private Date data;
    private boolean synched;
    private boolean released;
    private String descritivo;

    private RealmList<ItemContagem> itens;

    public Contagem(){

    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    public Contagem(boolean synched){
        this.setId(UUID.randomUUID().toString());
        this.setSynched(synched);
        this.setReleased(false);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getNave() {
        return nave;
    }

    public void setNave(String nave) {
        this.nave = nave;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean isSynched() {
        return synched;
    }

    public void setSynched(boolean synched) {
        this.synched = synched;
    }

    public String getDescritivo() {
        return descritivo;
    }

    public void setDescritivo(String descritivo) {
        this.descritivo = descritivo;
    }

    public RealmList<ItemContagem> getItens() {
        return itens;
    }

    public void setItens(RealmList<ItemContagem> itens) {
        this.itens = itens;
    }
}


