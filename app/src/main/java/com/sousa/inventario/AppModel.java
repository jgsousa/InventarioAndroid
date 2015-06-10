package com.sousa.inventario;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RetryPolicy;
import com.sousa.inventario.model.Artigo;
import com.sousa.inventario.model.Contagem;
import com.sousa.inventario.model.ItemContagem;
import com.sousa.inventario.model.Loja;
import com.sousa.inventario.network.AppRequestQueue;
import com.sousa.inventario.network.ArtigosRequest;
import com.sousa.inventario.network.ContagemPost;
import com.sousa.inventario.utils.Contagens;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
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
    private ArtigosRequest reqArtigos;

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

    public void fetchArtigosFromBackend(String hostname){
        ArtigosRequest artReq = new ArtigosRequest(hostname);
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        artReq.setRetryPolicy(policy);
        this.reqArtigos = artReq;
        AppRequestQueue.getInstance().addToRequestQueue(artReq);
    }

    public void setArtigosFromBackend(List<Artigo> artigos){
        if(artigos != null) {
            realm.beginTransaction();
            HashMap<String, Artigo> novos = new HashMap<>();

            for (int i = 0; i < artigos.size(); i++) {
                Artigo a = artigos.get(i);
                realm.copyToRealmOrUpdate(a);
                novos.put(a.getEAN(), a);
            }

            realm.commitTransaction();
            this.artigos = novos;
        }
        postContagensToBackend("http://srvisacla.taki.inet:8000");
    }

    public void postContagensToBackend(String host) {
        RealmQuery<Contagem> query = realm.where(Contagem.class).equalTo("released", true)
                .equalTo("synched", false);
        RealmResults<Contagem> results = query.findAll();
        for(int i = 0; i < results.size(); i++){
            JSONObject body = new JSONObject();
            JSONObject js = new JSONObject();
            Contagem c = results.get(i);
            try {
                js.put("ID", c.getId());
                js.put("Loja", c.getCentro());
                js.put("Data", DateFormat.getDateInstance().format(c.getData()));
                body.put("d", js);
            }
            catch (JSONException e){

            }

            ContagemPost postReq = new ContagemPost(host, js);
            postReq.token = reqArtigos.token;
            postReq.cookie = reqArtigos.cookie;
            postReq.contagemId = c.getId();
            int socketTimeout = 30000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            postReq.setRetryPolicy(policy);
            AppRequestQueue.getInstance().addToRequestQueue(postReq);
        }

    }
}
