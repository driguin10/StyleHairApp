package com.stylehair.nerdsolutions.stylehair.telas.busca.busca_funcionario;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.GetUsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_funcionarios_salao extends Fragment {
    RecyclerView lista;
    List<Usuario> usuarios;
    String IdSalao;

    int qtTentativas = 3;
    int qtTentativaRealizada = 0;

    Loading loading;
    Bundle bundle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle= getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_funcionarios_salao, container, false);
        if(bundle !=null)
        {
            IdSalao =  bundle.getString("idServico");
        }
        loading = new Loading(getActivity());
        lista = (RecyclerView) view.findViewById(R.id.listServicos_funcionario_busca);
        lista.setHasFixedSize(true);
        loading.abrir("Aguarde...");
        getFuncionarios(IdSalao);
       return view;
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
                            String Servico = Ufunc.getServico();
                            int IdServico = Ufunc.getIdServico();
                            funcs.add(new UsuarioFuncionario(IdFuncionario,IdUsuario,Nome,LinkImagem,Telefone,IdServico,Servico));
                        }

                        LinearLayoutManager layout = new LinearLayoutManager(getContext());
                        layout.setOrientation(LinearLayoutManager.VERTICAL);
                        lista.setAdapter(new Adaptador_funcionario_busca(funcs));
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

                    getFuncionarios(IdSalao);
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
