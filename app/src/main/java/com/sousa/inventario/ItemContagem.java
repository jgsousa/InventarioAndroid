package com.sousa.inventario;

/**
 * Created by Joao on 06/06/2015.
 */
public class ItemContagem {

    public String material;
    public int contado;
    public String unidade;

    public ItemContagem(String mat, int qtd, String unid){
        this.material = mat;
        this.contado = qtd;
        this.unidade = unid;
    }

}
