package com.stylehair.nerdsolutions.stylehair.Notification;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.Notification.bancoNotificacoes.BancoNotifyController;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.funcionarios;

public class ver_notificacao extends AppCompatActivity {
    String id;
    TextView texto;
    TextView nome_salao;
    TextView hora;
    String visualizacao;
    Button excluir;
    BancoNotifyController crud;
    String Btitulo;
    String Btexto;
    String Bnome_salao;
    String Bhora;
    String BidLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_notificacao);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_notificacoes);
        setSupportActionBar(myToolbar);
        Bundle extra = getIntent().getExtras();
        crud = new BancoNotifyController(getBaseContext());
        texto = (TextView) findViewById(R.id.texto_Lnotificacao);
        nome_salao = (TextView) findViewById(R.id.nome_salao_Lnotificacao);
        hora = (TextView) findViewById(R.id.hora_Lnotificacao);
        excluir = (Button) findViewById(R.id.bt_excluir_Lnotificacao);

        if(extra!=null)
        {
            id = extra.getString("id_notificacao");
            texto.setText(extra.getString("texto_notificacao"));
            visualizacao = extra.getString("visualizacao_notificacao");
            hora.setText(extra.getString("hora_notificacao"));
            nome_salao.setText(extra.getString("nome_salao_notificacao"));
            Btitulo = extra.getString("titulo_notificacao");
            Btexto = extra.getString("texto_notificacao");
            Bnome_salao = extra.getString("nome_salao_notificacao");
            Bhora = extra.getString("hora_notificacao");
            BidLogin = extra.getString("idLogin");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(extra.getString("titulo_notificacao"));
        Drawable upArrow = ContextCompat.getDrawable(ver_notificacao.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(ver_notificacao.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ver_notificacao.this)
                        .setTitle("Excluir")
                        .setMessage("Deseja excluir esta notificação?")
                        .setIcon(R.drawable.icone_delete)
                        .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                crud.deletaRegistro(Integer.valueOf(id));
                                Intent intent = new Intent(ver_notificacao.this,notificacao.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //não exclui, apenas fecha a mensagem
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        crud.alteraRegistro(Integer.valueOf(id),Btitulo,Btexto,Bhora,"1",Bnome_salao);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                Intent intent = new Intent(ver_notificacao.this,notificacao.class);
                startActivity(intent);
                finish();
                break;
            default:break;
        }
        return true;
    }
}
