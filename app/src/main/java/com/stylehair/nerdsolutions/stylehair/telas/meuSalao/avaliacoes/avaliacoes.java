package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.avaliacoes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.auxiliar.EnviarReceber;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;

import java.util.List;

public class avaliacoes extends AppCompatActivity{
Loading loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacoes);
        loading = new Loading(avaliacoes.this);


    }


}
