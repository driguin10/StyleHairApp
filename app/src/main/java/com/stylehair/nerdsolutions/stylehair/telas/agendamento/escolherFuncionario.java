package com.stylehair.nerdsolutions.stylehair.telas.agendamento;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.stylehair.nerdsolutions.stylehair.R;

import java.util.ArrayList;

public class escolherFuncionario extends AppCompatActivity {
ArrayList<String> lista;
String idSalao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_funcionario);

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null)
        {
            lista = bundle.getStringArrayList("escolhas");
            idSalao = bundle.getString("idSalao");

            Log.d("xex",lista.toString());

        }
    }
}
