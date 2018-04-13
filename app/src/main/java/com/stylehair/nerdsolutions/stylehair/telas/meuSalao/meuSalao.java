package com.stylehair.nerdsolutions.stylehair.telas.meuSalao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.funcionarios;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.servico.servicos_salao;

public class meuSalao extends AppCompatActivity {
    CardView bt_meuSalao;
    CardView bt_funcionario;
    CardView bt_Configuracao;
    CardView bt_Servicos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_salao);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_meu_salao);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Meu Salão");

        bt_meuSalao = (CardView) findViewById(R.id.card_bt1);
        bt_funcionario = (CardView) findViewById(R.id.card_bt2);
        bt_Servicos = (CardView) findViewById(R.id.card_bt3);
        bt_Configuracao = (CardView) findViewById(R.id.card_bt6);

        bt_meuSalao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(meuSalao.this,editar_salao.class);
                startActivity(intent);
            }
        });

        bt_funcionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(meuSalao.this,funcionarios.class);
                startActivity(intent);
            }
        });

        bt_Configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(meuSalao.this,configuracaoSalao.class);
                startActivity(intent);
            }
        });

        bt_Servicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(meuSalao.this,servicos_salao.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                finish();
                break;
            default:break;
        }
        return true;
    }
}
