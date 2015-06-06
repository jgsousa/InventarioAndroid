package com.sousa.inventario.com.sousa.inventario.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.sousa.inventario.Contagem;
import com.sousa.inventario.R;
import com.sousa.inventario.com.sousa.inventario.adapters.InventarioAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Joao on 06/06/2015.
 */
public class ListaContagens extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_contagens);

        toolbar = (Toolbar) findViewById(R.id.appbar);
        RecyclerView recycle = (RecyclerView) findViewById(R.id.lista);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(20);

        List<Contagem> contagens = new ArrayList<>(Contagem.createDummy().values());
        InventarioAdapter adapter = new InventarioAdapter(this, contagens);
        recycle.setAdapter(adapter);
    }
}
