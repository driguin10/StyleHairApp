package com.stylehair.nerdsolutions.stylehair.telas.minhaAgenda.verAgenda;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.telas.minhaAgenda.minha_agenda;

import de.hdodenhof.circleimageview.CircleImageView;

public class verAgendamento extends AppCompatActivity {

    String idAgenda;
    String idUsuario;
    String nome;
    String imagem;
    String data;
    String horaIni;
    String horaFim;
    String status;
    String tipo;

    String nomeFuncionario;
    String idFuncionario;
    String imagemFuncionario;

     String endereco;
     String numero;
     String bairro;
     String cidade;
     String complemento;
     String estado;

    RecyclerView lista;
    TextView txthorario;
    TextView txtdata;
    TextView txtstatus;
    TextView txtnomeFuncionario;
    CircleImageView CirclimagemFuncionario;
    TextView txtnomesalao;
    CircleImageView Circlimagemsalao;
    TextView txtendereco;



    Loading loading;
    int qtTentativas = 3;
    int qtTentativaRealizada = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_agendamento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_verAgendamento);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Ver Agendamento");
        Drawable upArrow = ContextCompat.getDrawable(verAgendamento.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(verAgendamento.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
             idAgenda = bundle.getString("idAgenda");
             idUsuario = bundle.getString("idUsuario");
             nome = bundle.getString("nome");
             imagem = bundle.getString("imagem");
             data = bundle.getString("data");
             horaIni = bundle.getString("horaIni");
             horaFim = bundle.getString("horaFim");
             status = bundle.getString("status");
             tipo = bundle.getString("tipo");
             nomeFuncionario = bundle.getString("nomeFuncionario");
             idFuncionario = bundle.getString("idFuncionario");
             imagemFuncionario = bundle.getString("imagemFuncionario");
             endereco= bundle.getString("endereco");
             numero= bundle.getString("numero");
             bairro= bundle.getString("bairro");
             cidade= bundle.getString("cidade");
             complemento= bundle.getString("complemento");
             estado= bundle.getString("estado");
        }
        txthorario = (TextView) findViewById(R.id.txtHorario);
        txtdata = (TextView) findViewById(R.id.txtData);
        txtnomeFuncionario = (TextView) findViewById(R.id.txtNomeFuncionario);
        txtnomesalao = (TextView) findViewById(R.id.txtNomeSalao);
        txtendereco = (TextView) findViewById(R.id.txtEndereco);
        txtstatus= (TextView) findViewById(R.id.txtStatus);
        CirclimagemFuncionario = (CircleImageView) findViewById(R.id.imagemFuncionario);
        Circlimagemsalao = (CircleImageView) findViewById(R.id.imagemSalao);
        String h = horaIni + " ás " + horaFim;
        txthorario.setText(h);
        String[] d = data.split("-");
        data =d[2]+"/"+d[1]+"/"+d[0];
        txtdata.setText(data);
        if(status.equals("0"))
            txtstatus.setText("Cancelado");
        else
            if(status.equals("1"))
                txtstatus.setText("Aguardando Atendimento");
            else
            if(status.equals("2"))
                txtstatus.setText("Atendimento Finalizado");
            else
            if(status.equals("0"))
                txtstatus.setText("Atendimento Cancelado");
//       Log.d("xex",status);

            txtnomeFuncionario.setText(nomeFuncionario);
            Picasso.with(this).load("http://stylehair.xyz/" + imagemFuncionario).into(CirclimagemFuncionario);

        txtnomesalao.setText(nome);
        Picasso.with(this).load("http://stylehair.xyz/" + imagem).into(Circlimagemsalao);
        txtendereco.setText(endereco);
    }
}
