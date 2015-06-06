package com.sousa.inventario.com.sousa.inventario.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sousa.inventario.Material;
import com.sousa.inventario.R;

import java.util.HashMap;


public class Contar extends AppCompatActivity {

    private HashMap<String, Material> materiais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_contagem);
        initData();

        ((EditText)findViewById(R.id.editText)).setOnFocusChangeListener(new EditText.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    EditText control = (EditText) v;
                    String ean = control.getText().toString();
                    Material m = materiais.get(ean);
                    if (m == null) {
                        Toast toast = Toast.makeText(v.getContext(), "Artigo nao existe", Toast.LENGTH_LONG);
                        toast.show();
                        EditText e = (EditText) findViewById(R.id.editText2);
                        e.setText("");
                        e = (EditText) findViewById(R.id.editText3);
                        e.setText("");
                        e = (EditText) findViewById(R.id.editText4);
                        e.setText("");
                    } else {
                        EditText e = (EditText) findViewById(R.id.editText2);
                        e.setText(m.description);
                        e = (EditText) findViewById(R.id.editText3);
                        e.setText(m.unit);
                        e = (EditText) findViewById(R.id.editText4);
                        e.setText("0");
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_contagem, menu);
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

    public void initData(){
        materiais = new HashMap<>();
        Material m = new Material("1", "123456", "UN", "Arroz Basmati" );
        materiais.put(m.EAN, m);
        m = new Material("2", "654321", "SAC", "Arroz Elefante" );
        materiais.put(m.EAN, m);
    }
}
