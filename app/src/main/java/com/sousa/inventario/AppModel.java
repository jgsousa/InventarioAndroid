package com.sousa.inventario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Joao on 06/06/2015.
 */
public class AppModel {

    private static AppModel instance;

    private HashMap<String, Contagem> contagens;
    private Contagem activeContagem;
    private HashMap<String, Loja> lojas;

    public static void initInstance() {
        if (instance == null) {
            // Create the instance
            instance = new AppModel();
        }
    }

    public static AppModel getInstance() {
        // Return the instance
        return instance;
    }

    private AppModel() {
        // Constructor hidden because this is a singleton
        contagens = Contagem.initContagens();
        lojas = Loja.getLojas();
    }

    public List<Contagem> getContagens() {
        // Custom method
        return new ArrayList<>(contagens.values());
    }

    public Contagem getContagemForId(String id) {
        return contagens.get(id);
    }

    public void setActiveContagem(Contagem activa) {
        activeContagem = activa;
    }

    public Contagem getActiveContagem() {
        return activeContagem;
    }

    public List<Loja> getLojas() {
        return new ArrayList<>(lojas.values());
    }
}
