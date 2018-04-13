package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.servico;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.GetUsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.Adaptador_funcionario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.cadastrar_funcionario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.funcionarios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class servicos_salao extends AppCompatActivity {
    RecyclerView lista;
    List<ServicoSalao> servicosSalao;
    String idSalao;

    int qtTentativas = 3;
    int qtTentativaRealizada = 0;

    Loading loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicos_salao);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_servicos_salao);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Serviços");
        loading = new Loading(servicos_salao.this);

        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        idSalao = getSharedPreferences.getString("idSalao", "");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.bt_add_servico);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(servicos_salao.this,ver_servico_salao.class);
                startActivity(intent);
            }
        });


        lista = (RecyclerView) findViewById(R.id.listServicos);
        lista.setHasFixedSize(true);
        loading.abrir("carregando");
        getServicos(idSalao);
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
                        List<ServicoSalao> ListaServicos = response.body();
                        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
                        layout.setOrientation(LinearLayoutManager.VERTICAL);
                        lista.setAdapter(new Adaptador_servico_salao(ListaServicos));
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
