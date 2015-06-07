package com.sousa.inventario;

import android.content.Context;

import com.sousa.inventario.model.Artigo;
import com.sousa.inventario.model.Contagem;
import com.sousa.inventario.model.ItemContagem;
import com.sousa.inventario.model.Loja;
import com.sousa.inventario.utils.Contagens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Joao on 06/06/2015.
 */
public class AppModel {

    private static AppModel instance;

    private HashMap<String, Contagem> contagens;
    private HashMap<String, Artigo> artigos;
    private Contagem activeContagem;
    private HashMap<String, Loja> lojas;
    private Context context;
    private Realm realm;

    public static void initInstance(Context context) {
        if (instance == null) {
            // Create the instance
            instance = new AppModel();
            instance.context = context;
        }
    }

    public static AppModel getInstance() {
        // Return the instance
        return instance;
    }

    private AppModel() {
        // Constructor hidden because this is a singleton
        lojas = Loja.getLojas();
        artigos = new HashMap<>();
    }

    public void addContagem(Contagem c) {
        realm.beginTransaction();
        Contagem rc = realm.copyToRealm(c);
        realm.commitTransaction();
        contagens.put(c.getId(), rc);
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

    public Artigo getArtigoForEAN(String EAN) {
        return artigos.get(EAN);
    }

    public void initArtigos() {
        realm.beginTransaction();

        Artigo art = realm.createObject(Artigo.class);
        art.setId("1");
        art.setEAN("123456");
        art.setUnit("UN");
        art.setDescription("Arroz Basmati");

        art = realm.createObject(Artigo.class);
        art.setId("2");
        art.setEAN("654321");
        art.setUnit("SAC");
        art.setDescription("Arroz Elefante");

        art = realm.createObject(Artigo.class);
        art.setId("3");
        art.setEAN("711719818731");
        art.setUnit("SAC");
        art.setDescription("Playstation TV");

        realm.commitTransaction();
    }

    public void initRealm(Context context) {
        realm = Realm.getInstance(context);

        RealmQuery<Artigo> query = realm.where(Artigo.class);
        RealmResults<Artigo> results = query.findAll();
        if (results.size() == 0) {
            initArtigos();
            results = query.findAll();
        }
        for (int i = 0; i < results.size(); i++) {
            artigos.put(results.get(i).getEAN(), results.get(i));
        }
        contagens = Contagens.initContagens();
    }

    public void addItemToContagem(Contagem c, ItemContagem itm) {
        realm.beginTransaction();
        ItemContagem itc = realm.copyToRealm(itm);
        Contagens.addItem(c, itc);
        realm.commitTransaction();
    }

    public void transactionStart() {
        realm.beginTransaction();
    }

    public void transactionCommit() {
        realm.commitTransaction();
    }

    public Realm getRealm() {
        return realm;
    }
}
