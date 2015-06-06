package com.sousa.inventario.activity;

import android.os.Bundle;
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
import com.sousa.inventario.Contagem;
import com.sousa.inventario.R;
import com.sousa.inventario.adapters.InventarioAdapter;
import com.sousa.inventario.helpers.DividerItemDecoration;

import java.util.List;

/**
 * Created by Joao on 06/06/2015.
 */
public class ListaContagens extends AppCompatActivity implements FloatingActionButton.OnClickListener{

    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_contagens);

        toolbar = (Toolbar) findViewById(R.id.appbar);
        RecyclerView recycle = (RecyclerView) findViewById(R.id.lista);
        recycle.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        recycle.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(20);

        List<Contagem> contagens = AppModel.getInstance().getContagens();
        InventarioAdapter adapter = new InventarioAdapter(this, contagens);
        recycle.setAdapter(adapter);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_action_new);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(imageView)
                .build();

        actionButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Toast t = Toast.makeText(this, "Blah", Toast.LENGTH_LONG);
        t.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_contagens, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
