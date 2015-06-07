package com.sousa.inventario.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.sousa.inventario.db.DBHelperArtigos;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Joao on 05/06/2015.
 */
public class Artigo extends RealmObject {

    @PrimaryKey
    private String EAN;

    private String id;
    private String description;
    private String unit;

    public String getEAN() {
        return EAN;
    }

    public void setEAN(String EAN) {
        this.EAN = EAN;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    /*public Artigo(String mat, String ean, String unit, String desc){
        this.id = mat;
        this.EAN = ean;
        this.unit = unit;
        this.description = desc;
    }*/

    public static List<Artigo> initFromDB(Context context){
        DBHelperArtigos helper = new DBHelperArtigos(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        return helper.initFromDB(db);
    }
}
