package com.sousa.inventario.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.sousa.inventario.AppModel;
import com.sousa.inventario.Loja;
import com.sousa.inventario.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joao on 07/06/2015.
 */
public class NovaContagemActivity extends AppCompatActivity implements EditText.OnClickListener {

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
                    String sDate = sYear + "/" + sMonth + "/" + sDay;
                    dataView.setText(sDate);
                }
            };
}
