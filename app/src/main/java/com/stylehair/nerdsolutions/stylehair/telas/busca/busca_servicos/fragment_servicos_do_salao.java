package com.stylehair.nerdsolutions.stylehair.telas.busca.busca_servicos;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;

import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;

import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_servicos_do_salao extends Fragment {

    String IdSalao="-1";
    int qtTentativas = 3;
    int qtTentativaRealizada = 0;
    Loading loading;
    //GridView lista;
    RecyclerView lista;

    List<ServicoSalao> servicosSalao;
    TextView semServico;
    Bundle bundle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         bundle= getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_servicos_do_salao, container, false);
         semServico = (TextView) view.findViewById(R.id.txtSemServicos);


        if(bundle !=null)
        {
            IdSalao =  bundle.getString("idServico");
        }

        loading = new Loading(getActivity());
        lista = (RecyclerView) view.findViewById(R.id.ListaServicos);

        loading.abrir("carregando");
        getServicos(IdSalao);
        return view;
    }


    public void getServicos(String id)
    {
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<ServicoSalao>> callBuscaServico = iApi.BuscaServicosSalao(id);
        callBuscaServico.enqueue(new Callback<List<ServicoSalao>>() {
            @Override
            public void onResponse(Call<List<ServicoSalao>> call, Response<List<ServicoSalao>> response) {
                qtTentativaRealizada = 0 ;
                callBuscaServico.cancel();

                loading.fechar();
                switch (response.code())
                {
                    case 200:
                       List<ServicoSalao> ListaServicos = response.body();
                        LinearLayoutManager layout = new LinearLayoutManager(getContext());
                        layout.setOrientation(LinearLayoutManager.VERTICAL);
                        lista.setAdapter(new Adaptador_servico_funcionario_busca(ListaServicos));
                        lista.setLayoutManager(layout);
                        lista.setClickable(true);
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<ServicoSalao>> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    getServicos(IdSalao);
                }
                else {
                    loading.fechar();
                }
            }
        });

    }


}
