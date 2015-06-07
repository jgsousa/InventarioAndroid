package com.sousa.inventario.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.sousa.inventario.AppModel;
import com.sousa.inventario.Contagem;
import com.sousa.inventario.R;
import com.sousa.inventario.adapters.DetalheAdapter;
import com.sousa.inventario.helpers.DividerItemDecoration;

/**
 * Created by Joao on 06/06/2015.
 */
public class DetalheContagem extends AppCompatActivity implements FloatingActionButton.OnClickListener{

    private Toolbar toolbar;
    private Contagem contagem;
    private RecyclerView recycle;

    public static final int ITEM_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detalhe_contagem);

        String value = getIntent().getExtras().getString("id");
        this.contagem = AppModel.getInstance().getContagemForId(value);
        AppModel.getInstance().setActiveContagem(this.contagem);

        toolbar = (Toolbar) findViewById(R.id.appbar);
        recycle = (RecyclerView) findViewById(R.id.lista);
        recycle.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setAdapter(new DetalheAdapter(this, this.contagem));
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(20);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_add_white_48dp);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(imageView)
                .setBackgroundDrawable(R.drawable.selector_button_red)
                .build();

        actionButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, Contar.class);
        i.putExtra("id", String.valueOf(contagem.centro));

        this.startActivityForResult(i,ITEM_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            recycle.getAdapter().notifyDataSetChanged();
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
