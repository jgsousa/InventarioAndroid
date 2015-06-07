package com.sousa.inventario.model;

import java.util.HashMap;

/**
 * Created by Joao on 06/06/2015.
 */
public class Loja {

    public String codigo;
    public String descritivo;

    public HashMap<String, Nave> naves;

    public Loja(String id){
        codigo = id;
        naves = Nave.getNaveForLoja(id);
    }

    public static HashMap<String, Loja> getLojas() {
        HashMap<String, Loja> lojas = new HashMap<>();
        Loja l;
        l = new Loja("L074");
        l.descritivo = "Loja 74";
        lojas.put(l.codigo, l);
        l = new Loja("L130");
        l.descritivo = "Loja 130";
        lojas.put(l.codigo, l);
        return lojas;
    }
}
