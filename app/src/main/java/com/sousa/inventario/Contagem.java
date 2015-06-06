package com.sousa.inventario;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Joao on 06/06/2015.
 */
public class Contagem {
    public String centro;
    public String nave;
    public Date data;
    public boolean synched;

    private List<ItemContagem> itens;

    public Contagem(boolean synched){
        itens = new ArrayList<>();
        this.synched = synched;
    }

    public void addItem(ItemContagem item){
        itens.add(item);
    }

    public List<ItemContagem> getItens(){
        return itens;
    }

    public void randInit(){
        ItemContagem i = new ItemContagem("Arroz Basmati", 120, "UN");
        this.addItem(i);
        i = new ItemContagem("Arroz Elefante", 230, "UN");
        this.addItem(i);
    }

    public static HashMap<String, Contagem> initContagens(){
        return createDummy();
    }

    public static HashMap<String,Contagem> createDummy(){
        HashMap<String,Contagem> contagens = new HashMap<>();
        Contagem c;
        for(int i = 0;i < 4;i++ ){
            c = new Contagem(false);
            c.centro = "Dummy " + String.valueOf(i);
            c.data = new Date();
            c.randInit();
            contagens.put(c.centro, c);
        }
        return contagens;
    }

}
