package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario;


import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
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
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.servico.Adaptador_servico_salao;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cadastro_servico_funcionario extends AppCompatActivity {
    RecyclerView lista;
    List<Usuario> usuarios;
    String idSalao;
    String idFuncionario;

    int qtTentativas = 3;
    int qtTentativaRealizada = 0;
    int qtTentativaRealizadaSalvar = 0;
Button btSalvarServico;
    Loading loading;
    List<ServicoSalao> ListaServicos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_servico_funcionario);
        loading = new Loading(Cadastro_servico_funcionario.this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_cadastro_servicos_funcionario);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Adicionar Serviços");
        Drawable upArrow = ContextCompat.getDrawable(Cadastro_servico_funcionario.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(Cadastro_servico_funcionario.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            idFuncionario = bundle.getString("idFuncionario");
        }
        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        idSalao = getSharedPreferences.getString("idSalao", "");
        lista = (RecyclerView) findViewById(R.id.listServicosCadastroFunc);
        lista.setHasFixedSize(true);
        btSalvarServico = (Button) findViewById(R.id.bt_salvarServicoFunc);
        btSalvarServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idServicoCad =  "";
                for(int x= 0; x< lista.getChildCount(); x++)//percorre a lista
                {
                    if(lista.getChildAt(x).findViewById(R.id.cardsServicoEscolhido).isSelected())//verifica qual esta selecionado
                    {
                        idServicoCad = String.valueOf(ListaServicos.get(x).getIdServicoSalao());
                    }
                }


                int posIdServCad = -1;
                for (int x = 0; x < ListaServicos.size(); x++) {
                    if (ListaServicos.get(x).isSelected()) {
                        posIdServCad = x;
                    }
                }
                if (posIdServCad == -1) {
                    Toast.makeText(Cadastro_servico_funcionario.this, "Escolha uma serviço.", Toast.LENGTH_LONG).show();
                } else {
                    idServicoCad = String.valueOf(ListaServicos.get(posIdServCad).getIdServicoSalao());
                    salvarServicos(idFuncionario,idServicoCad);
                }







            }
        });
        loading.abrir("Aguarde...");
        getServicos(idSalao);

    }

    public void salvarServicos(final String idFunc, final String idServ)
    {

        RequestBody func = RequestBody.create(MediaType.parse("text/plain"),idFunc);
        RequestBody serv = RequestBody.create(MediaType.parse("text/plain"),idServ);

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callServicoFunc = iApi.SalvarServicoFuncionario(func,serv);
        callServicoFunc.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                qtTentativaRealizadaSalvar = 0 ;
                callServicoFunc.cancel();

                loading.fechar();
                switch (response.code())
                {
                    case 204:
                        Toast.makeText(Cadastro_servico_funcionario.this,"Salvo com sucesso !!",Toast.LENGTH_LONG).show();
                       finish();
                        break;

                    case 400:
                        switch (response.message())
                        {
                            case "01":
                                Toast.makeText(Cadastro_servico_funcionario.this,"Já cadastrado !!",Toast.LENGTH_LONG).show();
                                break;

                            case "03":
                                Toast.makeText(Cadastro_servico_funcionario.this,"erro !!",Toast.LENGTH_LONG).show();
                                break;
                        }
                        break;
                }



            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (qtTentativaRealizadaSalvar < qtTentativas) {
                    qtTentativaRealizadaSalvar++;

                    salvarServicos(idFunc,idServ);
                }
                else {
                    loading.fechar();
                    Log.d("xex","erro");
                    Log.d("xex",t.getMessage());
                }
            }
        });

    }




    public void getServicos(String id)
    {

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<ServicoSalao>> callBuscaFuncionarios = iApi.BuscaServicosSalao(id);
        callBuscaFuncionarios.enqueue(new Callback<List<ServicoSalao>>() {
            @Override
            public void onResponse(Call<List<ServicoSalao>> call, Response<List<ServicoSalao>> response) {
                qtTentativaRealizada = 0 ;
                callBuscaFuncionarios.cancel();

                loading.fechar();

                switch (response.code())
                {
                    case 200:
                        ListaServicos = response.body();
                        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
                        layout.setOrientation(LinearLayoutManager.VERTICAL);
                        lista.setAdapter(new Adaptador_servico_funcionario_escolhido(ListaServicos,idFuncionario,lista));
                        lista.setLayoutManager(layout);
                        lista.setClickable(true);
                        break;

                    case 400:

                        break;
                }



            }

            @Override
            public void onFailure(Call<List<ServicoSalao>> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;

                    getServicos(idSalao);
                }
                else {
                    loading.fechar();
                    Log.d("xex","erro");
                    Log.d("xex",t.getMessage());
                }
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
