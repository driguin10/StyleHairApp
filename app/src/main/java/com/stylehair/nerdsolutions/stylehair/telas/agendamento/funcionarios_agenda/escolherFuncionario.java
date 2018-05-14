package com.stylehair.nerdsolutions.stylehair.telas.agendamento.funcionarios_agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.GetUsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.Adaptador_funcionario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.funcionarios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class escolherFuncionario extends AppCompatActivity {
    ArrayList<String> listaServicos;
    String idSalao;
    RecyclerView listaFunc;
    List<Usuario> usuarios;

    int qtTentativas = 3;
    int qtTentativaRealizada = 0;

    Loading loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_funcionario);
        loading = new Loading(escolherFuncionario.this);
        Bundle bundle = getIntent().getExtras();
        if(bundle !=null)
        {
            listaServicos = bundle.getStringArrayList("escolhas");
            idSalao = bundle.getString("idSalao");
        }

        listaFunc = (RecyclerView) findViewById(R.id.listaFunc);
        listaFunc.setHasFixedSize(true);
        loading.abrir("Atualizando...");
        getFuncionarios(idSalao);
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

                loading.fechar();

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
                            String Telefone = Ufunc.getTelefone();
                            funcs.add(new UsuarioFuncionario(IdUsuario, Nome, LinkImagem, IdFuncionario,Telefone));
                        }

                        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
                        layout.setOrientation(LinearLayoutManager.VERTICAL);
                        lista.setAdapter(new Adaptador_funcionario(funcs));
                        lista.setLayoutManager(layout);
                        lista.setClickable(true);
                        break;

                    case 400:

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
                    loading.fechar();
                    Log.d("xex","erro");
                    Log.d("xex",t.getMessage());
                }
            }
        });

    }
}
