package com.sousa.inventario.model;

import io.realm.RealmObject;

/**
 * Created by Joao on 06/06/2015.
 */
public class ItemContagem extends RealmObject {


    private Artigo material;
    private int contado;
    private String unidade;
    private Contagem contagem;

    public Artigo getMaterial() {
        return material;
    }

    public void setMaterial(Artigo material) {
        this.material = material;
    }

    public int getContado() {
        return contado;
    }

    public void setContado(int contado) {
        this.contado = contado;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Contagem getContagem() {
        return contagem;
    }

    public void setContagem(Contagem contagem) {
        this.contagem = contagem;
    }

    public ItemContagem(){

    }

    public ItemContagem(Artigo mat, int qtd, String unid){
        this.material = mat;
        this.contado = qtd;
        this.unidade = unid;
    }

}
