package com.sousa.inventario.utils;

import com.sousa.inventario.AppModel;
import com.sousa.inventario.model.Artigo;
import com.sousa.inventario.model.Contagem;
import com.sousa.inventario.model.ItemContagem;

import java.util.Date;
import java.util.HashMap;

import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Joao on 07/06/2015.
 */
public class Contagens {

    public static void addItem(Contagem c, ItemContagem item){
        c.getItens().add(item);
    }

    public static HashMap<String, Contagem> initContagens(){
        HashMap<String, Contagem> contagens = new HashMap<>();
        RealmQuery<Contagem> query = AppModel.getInstance().getRealm().where(Contagem.class);
        RealmResults<Contagem> results = query.findAll();
        for(int i = 0; i < results.size(); i++){
            contagens.put(results.get(i).getId(), results.get(i));
        }
        return contagens;
    }

    public static void releaseContagem(Contagem c){
        AppModel.getInstance().transactionStart();
        c.setReleased(true);
        AppModel.getInstance().transactionCommit();
    }
}
