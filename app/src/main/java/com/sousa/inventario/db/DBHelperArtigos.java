package com.sousa.inventario.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sousa.inventario.model.Artigo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Joao on 07/06/2015.
 */
public class DBHelperArtigos extends SQLiteOpenHelper {

    private Context _context;
    private static final String DATABASE_NAME = "Artigo.sqlite";
    private static final int DATABASE_VERSION = 2;
    private static final String MATERIAL_TABLE_CREATE =
            "CREATE TABLE artigo ( _id integer primary key autoincrement, " +
                    "codigo TEXT," +
                    "descritivo TEXT," +
                    "EAN TEXT," +
                    "unidade TEXT );";
    private static final String MATERIAL_TABLE_INDEX = "CREATE UNIQUE INDEX material_idx ON material(EAN)";

    public DBHelperArtigos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(MATERIAL_TABLE_CREATE);
            db.execSQL(MATERIAL_TABLE_INDEX);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void onUpgrade(SQLiteDatabase db, int i, int j) {

    }

    public List<Artigo> initFromDB(SQLiteDatabase db) {
        List<Artigo> listaMaterial = new ArrayList<Artigo>();
        String sql = "Select * from artigo";
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            //Artigo mat = new Artigo(c.getString(1),c.getString(3),c.getString(4),c.getString(2) );
            //listaMaterial.add(mat);
            c.moveToNext();
        }
        c.close();
        return listaMaterial;
    }

    public boolean saveToDB(SQLiteDatabase db, List<Artigo> lista) {
        boolean correct = true;
        String sql;
        try {
            db.beginTransaction();
            for (Iterator<Artigo> itr = lista.iterator(); itr.hasNext(); ) {
                Artigo mat = itr.next();
                sql = getSQLForMaterialInsert(mat);
                db.execSQL(sql, new String[]{mat.getId(), mat.getDescription(), mat.getEAN(), mat.getUnit()});
            }
        } catch (Exception e) {
            correct = false;
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
        }
        return correct;
    }

    private String getSQLForMaterialInsert(Artigo mat) {
        String sql = "replace into material values (null, ?1, ?2, ?3, ?4);";
        return sql;
    }
}
