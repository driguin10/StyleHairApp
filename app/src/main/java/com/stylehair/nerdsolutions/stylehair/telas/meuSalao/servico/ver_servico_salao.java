package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.servico;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.stylehair.nerdsolutions.stylehair.R;

public class ver_servico_salao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_servico_salao);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_verSalao);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão



        Bundle bundle = ver_servico_salao.this.getIntent().getExtras();

        if(bundle!=null)
        {

            getSupportActionBar().setTitle(bundle.getString("servico"));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                Intent intent = new Intent(ver_servico_salao.this,servicos_salao.class);

               startActivity(intent);
               finish();
                break;
            default:break;
        }
        return true;
    }
}
