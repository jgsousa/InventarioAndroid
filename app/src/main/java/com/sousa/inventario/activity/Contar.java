package com.sousa.inventario.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sousa.inventario.AppModel;
import com.sousa.inventario.Contagem;
import com.sousa.inventario.ItemContagem;
import com.sousa.inventario.Material;
import com.sousa.inventario.R;
import com.sousa.inventario.scanner.CaptureActivityAny;

import java.util.HashMap;


public class Contar extends AppCompatActivity implements Button.OnClickListener{

    private HashMap<String, Material> materiais;
    private Toolbar toolbar;
    private Contagem contagem;
    private EditText contagemView;
    private EditText materialView;
    private EditText eanView;
    private EditText unidadeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_contagem);
        contagem = AppModel.getInstance().getActiveContagem();
        initData();

        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(20);

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(this);
        b.setBackgroundColor(getResources().getColor(R.color.primaryColor));
        b.setTextColor(Color.WHITE);

        contagemView = (EditText)findViewById(R.id.editText5);
        eanView = (EditText)findViewById(R.id.editText);
        materialView = (EditText)findViewById(R.id.editText2);
        unidadeView = (EditText)findViewById(R.id.editText3);

        eanView.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    EditText control = (EditText) v;
                    String ean = control.getText().toString();
                    setEAN(ean);
                }
            }
        });
    }

    public void initData() {
        materiais = new HashMap<>();
        Material m = new Material("1", "123456", "UN", "Arroz Basmati");
        materiais.put(m.EAN, m);
        m = new Material("2", "654321", "SAC", "Arroz Elefante");
        materiais.put(m.EAN, m);
        m = new Material("3", "711719818731", "SAC", "Playstation TV");
        materiais.put(m.EAN, m);
    }

    private void setEAN(String ean){
        Material m = materiais.get(ean);
        if (m == null) {
            Toast toast = Toast.makeText(this, "Artigo nao existe", Toast.LENGTH_LONG);
            toast.show();

            materialView.setText("");
            unidadeView.setText("");
            EditText e = (EditText) findViewById(R.id.editText4);
            e.setText("");
        } else {
            materialView.setText(m.description);
            unidadeView.setText(m.unit);
            EditText e = (EditText) findViewById(R.id.editText4);
            e.setText("0");
            //if(contagemView.requestFocus()) {
                //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            //}
        }
    }

    @Override
    public void onClick(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureActivityAny.class);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show();
            } else {
                eanView.setText(result.getContents());
                setEAN(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nova_contagem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            if(contagemView.getText().toString().length() == 0){
                Toast t = Toast.makeText(this, "Entrar contagem", Toast.LENGTH_SHORT);
                t.show();
            }
            else{
                ItemContagem ic = new ItemContagem(materialView.getText().toString(),
                        Integer.valueOf(contagemView.getText().toString()),
                        unidadeView.getText().toString());
                contagem.addItem(ic);
                this.setResult(RESULT_OK);
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
