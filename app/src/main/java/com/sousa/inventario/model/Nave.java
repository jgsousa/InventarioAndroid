package com.sousa.inventario.model;

import java.util.HashMap;

/**
 * Created by Joao on 06/06/2015.
 */
public class Nave {
    public String codigo;
    public String descritivo;

    public static HashMap<String, Nave> getNaveForLoja(String loja) {
        HashMap<String, Nave> naves = new HashMap<>();
        Nave l;
        l = new Nave();
        if(loja == "L074") {
            l.codigo = "N074";
            l.descritivo = "Nave 74";
            naves.put(l.codigo, l);
        }
        else {
            l.codigo = "N130";
            l.descritivo = "Nave 130";
            naves.put(l.codigo, l);
        }
        return naves;
    }
}
