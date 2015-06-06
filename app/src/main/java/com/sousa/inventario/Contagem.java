package com.sousa.inventario;

import java.util.ArrayList;
import java.util.Collections;
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
        itens = Collections.emptyList();
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

    public static HashMap<String,Contagem> createDummy(){
        HashMap<String,Contagem> contagens = new HashMap<>();
        Contagem c = new Contagem(true);
        c.centro = "Dummy";
        c.data = new Date();
        contagens.put(c.centro, c);
        c = new Contagem(false);
        c.centro = "Dummy 2";
        c.data = new Date();
        contagens.put(c.centro, c);
        for(int i = 3;i < 20;i++ ){
            c = new Contagem(false);
            c.centro = "Dummy " + String.valueOf(i);
            c.data = new Date();
            contagens.put(c.centro, c);
        }
        return contagens;
    }

}
