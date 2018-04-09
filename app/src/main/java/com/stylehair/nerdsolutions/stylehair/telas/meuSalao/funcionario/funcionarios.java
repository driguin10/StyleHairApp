package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import com.stylehair.nerdsolutions.stylehair.classes.ConfigSalao;
import com.stylehair.nerdsolutions.stylehair.classes.ConfiguracaoSalao;
import com.stylehair.nerdsolutions.stylehair.classes.Funcionario;
import com.stylehair.nerdsolutions.stylehair.classes.GetUsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.SectionsPageAdapter;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.fragmentLogin;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.fragmentUsuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class funcionarios extends AppCompatActivity {
    RecyclerView lista;
    List<Usuario> usuarios;
    String idSalao;

    int qtTentativas = 3;
    int qtTentativaRealizada = 0;

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionarios);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_funcionarios);
        setSupportActionBar(myToolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.bt_add_funcionario);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(funcionarios.this,cadastrar_funcionario.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Funcionarios");


        lista = (RecyclerView) findViewById(R.id.listFuncionarios);
        lista.setHasFixedSize(true);


        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(funcionarios.this);
        idSalao = getSharedPreferences.getString("idSalao","-1");


        getFuncionarios(idSalao);

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


    public void getFuncionarios(String id)
    {

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<GetUsuarioFuncionario> callBuscaFuncionarios = iApi.buscaFuncionarios(Integer.valueOf(id));
        callBuscaFuncionarios.enqueue(new Callback<GetUsuarioFuncionario>() {
            @Override
            public void onResponse(Call<GetUsuarioFuncionario> call, Response<GetUsuarioFuncionario> response) {
                qtTentativaRealizada = 0 ;
                callBuscaFuncionarios.cancel();



                    switch (response.code())
                    {
                        case 200:
                            GetUsuarioFuncionario func = response.body();
                            List<UsuarioFuncionario> funcs = new ArrayList<>();

                            for (UsuarioFuncionario Ufunc : func.funcionarios) {
                                int IdFuncionario = Ufunc.getIdFuncionario();
                                int IdUsuario = Ufunc.getIdUsuario();
                                String Nome = Ufunc.getNome();
                                String LinkImagem = Ufunc.getLinkImagem();
                                funcs.add(new UsuarioFuncionario(IdUsuario, Nome, LinkImagem, IdFuncionario));
                            }

                            LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
                            layout.setOrientation(LinearLayoutManager.VERTICAL);
                            lista.setAdapter(new Adaptador_funcionario(funcs));
                            lista.setLayoutManager(layout);
                            lista.setClickable(true);
                            break;

                        case 400:
                            Log.d("xex","nenhummm");
                            break;
                    }



            }

            @Override
            public void onFailure(Call<GetUsuarioFuncionario> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;

                    getFuncionarios(idSalao);
                }
                else {
                    //loading.fechar();
                    Log.d("xex","erro");
                    Log.d("xex",t.getMessage());
                }
            }
        });

    }

}
