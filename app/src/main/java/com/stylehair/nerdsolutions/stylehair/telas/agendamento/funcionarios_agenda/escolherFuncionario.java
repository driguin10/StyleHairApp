package com.stylehair.nerdsolutions.stylehair.telas.agendamento.funcionarios_agenda;

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
import android.widget.Button;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.GetUsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.classes.GetUsuarioFuncionarioBusca;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionarioBusca;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.configuracaoSalao;
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
    String idsServicos="";
    Button BTprosseguir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_funcionario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_escolherFunc);
        BTprosseguir = (Button) findViewById(R.id.btProsseguir);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Escolha o Funcionário");
        Drawable upArrow = ContextCompat.getDrawable(escolherFuncionario.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(escolherFuncionario.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        loading = new Loading(escolherFuncionario.this);
        Bundle bundle = getIntent().getExtras();
        if(bundle !=null)
        {
            listaServicos = bundle.getStringArrayList("escolhas");//servico que o cliente escolheu
            idSalao = bundle.getString("idSalao");
        }

        // pega a lista de serviços selecionados e monta a string com os ID
        for(int x = 0 ; x< listaServicos.size();x++)
        {
            if(listaServicos.get(x)!=null) {
                String[] val = listaServicos.get(x).split("#");
                idsServicos = idsServicos + val[0] + ",";
            }
        }
        char[] codigoChar = idsServicos.toCharArray();
        codigoChar[idsServicos.length() -1] = ' ';//retirar a ultima virgula
        idsServicos = String.valueOf(codigoChar);
        //----------------------------------------------------------------
        listaFunc = (RecyclerView) findViewById(R.id.listaFunc);
        listaFunc.setHasFixedSize(true);
        loading.abrir("Atualizando...");
        getFuncionarios(idSalao,idsServicos);
    }

    public void getFuncionarios(String id,String servicos)
    {
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<GetUsuarioFuncionarioBusca> callBuscaFuncionarios = iApi.buscaFuncionariosBusca(Integer.valueOf(id),servicos);
        callBuscaFuncionarios.enqueue(new Callback<GetUsuarioFuncionarioBusca>() {
            @Override
            public void onResponse(Call<GetUsuarioFuncionarioBusca> call, Response<GetUsuarioFuncionarioBusca> response) {
                qtTentativaRealizada = 0 ;
                callBuscaFuncionarios.cancel();
                loading.fechar();

                switch (response.code())
                {
                    case 200:
                        GetUsuarioFuncionarioBusca func = response.body();
                        List<UsuarioFuncionarioBusca> funcs = new ArrayList<>();
                        List<UsuarioFuncionarioBusca> funcsAux = new ArrayList<>();
                        for (UsuarioFuncionarioBusca Ufunc : func.funcionarios) {
                            int IdFuncionario = Ufunc.getIdFuncionario();
                            int IdUsuario = Ufunc.getIdUsuario();
                            String Nome = Ufunc.getNome();
                            String LinkImagem = Ufunc.getLinkImagem();
                            String Telefone = Ufunc.getTelefone();
                            String Servico = String.valueOf(Ufunc.getIdServico())+"-"+Ufunc.getServico();
                            int IdServico = Ufunc.getIdServico();
                            funcs.add(new UsuarioFuncionarioBusca(IdFuncionario,IdUsuario,Nome,LinkImagem,Telefone,IdServico,Servico));
                        }


                        int pos = -1;
                        for(int x=0;x<funcs.size();x++) {
                            if(funcsAux.size()>0)
                            {
                                for(int y=0;y<funcsAux.size();y++)
                                {
                                    if(funcs.get(x).getIdFuncionario()==funcsAux.get(y).getIdFuncionario())
                                    {
                                        pos = y;//pega posição que foi encontrado id igual
                                    }
                                }

                                if(pos == -1)// se não foi encontrado é igual -1
                                {
                                    funcsAux.add(funcs.get(x));
                                }
                                else
                                {
                                    String concatena =funcsAux.get(pos).getServico() +"#" + funcs.get(x).getServico();
                                    funcsAux.get(pos).setServico(concatena);
                                    pos = -1;
                                }
                            }
                            else
                            {
                                funcsAux.add(funcs.get(x));
                            }
                        }

                        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
                        layout.setOrientation(LinearLayoutManager.VERTICAL);
                        listaFunc.setAdapter(new Adaptador_funcionario_agenda(funcsAux,listaFunc,BTprosseguir,idSalao,listaServicos));
                        listaFunc.setLayoutManager(layout);
                        listaFunc.setClickable(true);
                        break;
                }
            }

            @Override
            public void onFailure(Call<GetUsuarioFuncionarioBusca> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;

                    getFuncionarios(idSalao,idsServicos);
                }
                else {
                    loading.fechar();
                }
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
