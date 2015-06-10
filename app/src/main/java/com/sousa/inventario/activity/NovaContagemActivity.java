package com.sousa.inventario.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sousa.inventario.AppModel;
import com.sousa.inventario.model.Contagem;
import com.sousa.inventario.model.Loja;
import com.sousa.inventario.model.Nave;
import com.sousa.inventario.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * Created by Joao on 07/06/2015.
 */
public class NovaContagemActivity extends AppCompatActivity implements EditText.OnClickListener,
                Spinner.OnItemSelectedListener
{

    private Toolbar toolbar;
    private EditText descritivoView;
    public EditText dataView;
    private Spinner centroView;
    private Spinner naveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nova_contagem);
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        initActivity();
        initSpinner();
        dataView.setOnClickListener(this);
        centroView.setOnItemSelectedListener(this);
        dataView.setText(DateFormat.getDateInstance().format(new Date()));
    }

    private void initActivity() {
        descritivoView = (EditText) findViewById(R.id.descritivo);
        dataView = (EditText) findViewById(R.id.data);
        centroView = (Spinner) findViewById(R.id.loja);
        naveView = (Spinner) findViewById(R.id.nave);
    }

    private void initSpinner() {
        ArrayList<String> spinnerArray = new ArrayList<>();
        List<Loja> lojas = AppModel.getInstance().getLojas();
        spinnerArray.add("Escolher loja");
        for (int i = 0; i < lojas.size(); i++) {
            spinnerArray.add(lojas.get(i).descritivo);
        }
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);
        centroView.setAdapter(spinnerArrayAdapter);
    }

    @Override
    public void onClick(View view) {
        DatePickerDialog dpd = new DatePickerDialog(this, mDateSetListener,2015, 5, 22);
        dpd.show();
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    String sYear = String.valueOf(year);
                    String sMonth = String.valueOf(monthOfYear + 1);
                    String sDay = String.valueOf(dayOfMonth);
                    Date d = new Date(year - 1900, monthOfYear, dayOfMonth);
                    dataView.setText(DateFormat.getDateInstance().format(d));
                }
            };
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
            Contagem contagem = new Contagem(false);
            contagem.setCentro((String) centroView.getSelectedItem());
            contagem.setNave((String) naveView.getSelectedItem());
            String sData = dataView.getText().toString();
            SimpleDateFormat  format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                contagem.setData(format.parse(sData));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(validarContagem(contagem)) {
                AppModel.getInstance().addContagem(contagem);
                this.setResult(RESULT_OK);
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validarContagem(Contagem contagem){
        contagem.setDescritivo(descritivoView.getText().toString());
        if(contagem.getDescritivo().equalsIgnoreCase("")){
            Toast t = Toast.makeText(this, "Introduzir descritivo", Toast.LENGTH_SHORT);
            t.show();
        }
        else if(contagem.getCentro().equalsIgnoreCase("Escolher loja")){
            Toast t = Toast.makeText(this, "Introduzir loja", Toast.LENGTH_SHORT);
            t.show();
        }
        else if(contagem.getNave() == null){
            Toast t = Toast.makeText(this, "Introduzir nave", Toast.LENGTH_SHORT);
            t.show();
        }
        else if(contagem.getData() == null){
            Toast t = Toast.makeText(this, "Introduzir data", Toast.LENGTH_SHORT);
            t.show();
        }
        else{
            return true;
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(i != 0){
            Loja loja = AppModel.getInstance().getLojas().get(i - 1);
            List<Nave> naves = new ArrayList<>(loja.naves.values());
            ArrayList<String> spinnerArray = new ArrayList<>();
            for (int j = 0; j < naves.size(); j++) {
                spinnerArray.add(naves.get(j).descritivo);
            }
            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    spinnerArray);
            naveView.setAdapter(spinnerArrayAdapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
