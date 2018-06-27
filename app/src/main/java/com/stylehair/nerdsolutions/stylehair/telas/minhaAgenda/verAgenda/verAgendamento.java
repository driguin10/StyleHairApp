package com.stylehair.nerdsolutions.stylehair.telas.minhaAgenda.verAgenda;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.MeuAgendamento;
import com.stylehair.nerdsolutions.stylehair.telas.minhaAgenda.Adaptador_minhaAgenda;
import com.stylehair.nerdsolutions.stylehair.telas.minhaAgenda.minha_agenda;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    TextView txtLabelEnd;
    Button btCancelarAgenda;
    Button btExcluirAgenda;
    Button btFinalizarAgenda;

    Loading loading;
    int qtTentativas = 3;
    int qtTentativaRealizadaCancelar = 0;
    int qtTentativaRealizadaExcluir = 0;
    int qtTentativaRealizadaFinalizar = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_agendamento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_verAgendamento);
        loading = new Loading(this);
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
        txtLabelEnd = (TextView) findViewById(R.id.txtLabelEndereco);
        btCancelarAgenda = (Button) findViewById(R.id.btCancelar);
        btFinalizarAgenda = (Button) findViewById(R.id.btFinalizar);
        btExcluirAgenda = (Button) findViewById(R.id.btExcluir);
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



            if(status.equals("1")) {
                txtstatus.setText("Aguardando Atendimento");
                btExcluirAgenda.setVisibility(View.GONE);

                if(tipo.equals("1"))
                {
                    btFinalizarAgenda.setVisibility(View.VISIBLE);
                }
            }
            else
            if(status.equals("2")) {
                txtstatus.setText("Atendimento Finalizado");
                if(tipo.equals("0")) {
                    btExcluirAgenda.setVisibility(View.VISIBLE);
                }
                else
                {
                    btExcluirAgenda.setVisibility(View.GONE);
                }
                btCancelarAgenda.setVisibility(View.GONE);
            }
            else
            if(status.equals("0")) {
                txtstatus.setText("Atendimento Cancelado");
                btCancelarAgenda.setVisibility(View.GONE);
                if(tipo.equals("0")) {
                    btExcluirAgenda.setVisibility(View.VISIBLE);
                }
                else
                {
                    btExcluirAgenda.setVisibility(View.GONE);
                }
            }


            txtnomeFuncionario.setText(nomeFuncionario);
        if(imagemFuncionario == null)
            CirclimagemFuncionario.setImageDrawable(getResources().getDrawable(R.drawable.img_padrao_user));
        else
            Picasso.with(this).load("http://stylehair.xyz/" + imagemFuncionario).into(CirclimagemFuncionario);

        txtnomesalao.setText(nome);

        if(imagem.equals(""))
            Circlimagemsalao.setImageDrawable(getResources().getDrawable(R.drawable.img_padrao_user));
        else
            Picasso.with(this).load("http://stylehair.xyz/" + imagem).into(Circlimagemsalao);

        String ender= endereco+","+numero+","+bairro+","+cidade;
        txtendereco.setText(ender);

        btCancelarAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelarAgendamento(idAgenda,"0");
                loading.abrir("Aguarde...");
            }
        });

        btExcluirAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    ExcluirAgendamento(idAgenda);
                    loading.abrir("Aguarde...");
            }
        });

        btFinalizarAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizarAgendamento(idAgenda,"2");
                loading.abrir("Aguarde...");
            }
        });


       if(tipo.equals("1"))
        {
            txtnomeFuncionario.setVisibility(View.GONE);
            txtendereco.setVisibility(View.GONE);
            CirclimagemFuncionario.setVisibility(View.GONE);
            txtLabelEnd.setVisibility(View.GONE);
        }
    }

    public void finalizarAgendamento(final String idAgenda ,final String status)
    {
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callAgendamento = iApi.alterStatusAgendamento(idAgenda,status);
        callAgendamento.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                loading.fechar();
                qtTentativaRealizadaFinalizar = 0;
                if(response.isSuccessful()) {
                    switch (response.code()) {
                        case 204:
                            Toast.makeText(verAgendamento.this, "Agendamento Finalizado!!", Toast.LENGTH_LONG).show();
                            btCancelarAgenda.setVisibility(View.GONE);
                            btFinalizarAgenda.setVisibility(View.GONE);
                            txtstatus.setText("Atendimento Finalizado");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (qtTentativaRealizadaFinalizar < qtTentativas) {
                    qtTentativaRealizadaFinalizar++;
                    cancelarAgendamento(idAgenda,status);
                }
                loading.fechar();
            }
        });
    }

    public void cancelarAgendamento(final String idAgenda ,final String status)
    {
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callAgendamento = iApi.alterStatusAgendamento(idAgenda,status);
        callAgendamento.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                loading.fechar();
                qtTentativaRealizadaCancelar = 0;
                if(response.isSuccessful()) {
                    switch (response.code()) {
                        case 204:
                            Toast.makeText(verAgendamento.this, "Agendamento cancelado!!", Toast.LENGTH_LONG).show();
                            btCancelarAgenda.setVisibility(View.GONE);
                            btFinalizarAgenda.setVisibility(View.GONE);
                            if(tipo.equals("0")) {
                                btExcluirAgenda.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                btExcluirAgenda.setVisibility(View.GONE);
                            }
                            txtstatus.setText("Atendimento Cancelado");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (qtTentativaRealizadaCancelar < qtTentativas) {
                    qtTentativaRealizadaCancelar++;
                    cancelarAgendamento(idAgenda,status);
                }
                loading.fechar();
            }
        });
    }

    public void ExcluirAgendamento(final String idAgenda)
    {
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callAgendamento = iApi.excluirAgendamento(idAgenda);
        callAgendamento.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                loading.fechar();
                qtTentativaRealizadaExcluir = 0;
                if(response.isSuccessful()) {
                    switch (response.code()) {
                        case 204:
                            Toast.makeText(verAgendamento.this, "Agendamento excluido!!", Toast.LENGTH_LONG).show();
                            finish();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (qtTentativaRealizadaExcluir < qtTentativas) {
                    qtTentativaRealizadaExcluir++;
                    cancelarAgendamento(idAgenda,status);
                }
                loading.fechar();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
