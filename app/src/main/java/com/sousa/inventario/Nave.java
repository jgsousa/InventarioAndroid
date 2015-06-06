package com.sousa.inventario;

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
            l.descritivo = "Loja 74";
            naves.put(l.codigo, l);
        }
        else {
            l.codigo = "L130";
            l.descritivo = "Loja 130";
            naves.put(l.codigo, l);
        }
        return naves;
    }
}
