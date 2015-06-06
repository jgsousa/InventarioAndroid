package com.sousa.inventario.com.sousa.inventario.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.sousa.inventario.Contagem;
import com.sousa.inventario.R;
import com.sousa.inventario.com.sousa.inventario.adapters.DetalheAdapter;

import java.util.HashMap;

/**
 * Created by Joao on 06/06/2015.
 */
public class DetalheContagem extends AppCompatActivity {

    private Toolbar toolbar;

    String value = getIntent().getExtras().getString("id");
    HashMap<String,Contagem> contagens = Contagem.createDummy();
    Contagem c = contagens.get(value);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detalhe_contagem);

        toolbar = (Toolbar) findViewById(R.id.appbar);
        RecyclerView recycle = (RecyclerView) findViewById(R.id.lista);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setAdapter(new DetalheAdapter(this, c));
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(20);


    }

}
