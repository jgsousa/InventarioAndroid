package com.sousa.inventario.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.sousa.inventario.AppModel;
import com.sousa.inventario.model.Contagem;
import com.sousa.inventario.R;
import com.sousa.inventario.adapters.DetalheAdapter;
import com.sousa.inventario.helpers.DividerItemDecoration;
import com.sousa.inventario.model.ItemContagem;
import com.sousa.inventario.utils.Contagens;

import java.text.DateFormat;

/**
 * Created by Joao on 06/06/2015.
 */
public class DetalheContagem extends AppCompatActivity implements FloatingActionButton.OnClickListener {

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
        getSupportActionBar().setTitle(this.contagem.getCentro() + " - " +
                DateFormat.getDateInstance().format(this.contagem.getData()));

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_add_white_48dp);

        if (!contagem.isReleased()) {
            FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                    .setContentView(imageView)
                    .setBackgroundDrawable(R.drawable.selector_button_red)
                    .build();

            actionButton.setOnClickListener(this);
        }


    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, Contar.class);
        i.putExtra("id", String.valueOf(contagem.getCentro()));

        this.startActivityForResult(i, ITEM_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            recycle.getAdapter().notifyDataSetChanged();
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (!contagem.isReleased()) {
            getMenuInflater().inflate(R.menu.menu_nova_contagem, menu);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_done) {
            if(contagem.getItens().size() > 0) {
                new AlertDialog.Builder(this)
                        .setTitle("Submeter contagem?")
                        .setMessage("Se submeter a contagem ja nao podera ser alterada")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Contagens.releaseContagem(contagem);
                                setResult(RESULT_OK);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
            else{
                Toast t = Toast.makeText(this, "Contagem sem itens", Toast.LENGTH_SHORT);
                t.show();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
