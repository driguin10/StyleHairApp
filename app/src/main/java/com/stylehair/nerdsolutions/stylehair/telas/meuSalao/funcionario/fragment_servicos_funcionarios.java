package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario;



import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;

import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_servicos_funcionarios extends Fragment {
    RecyclerView lista;
    List<Usuario> usuarios;
    String idFuncionario;

    int qtTentativas = 3;
    int qtTentativaRealizada = 0;

    Loading loading;

    View view;
    private OnFragmentInteractionListener mListener;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_fragment_servicos, container, false);
        loading = new Loading(getActivity());
        Bundle bundle = getActivity().getIntent().getExtras();

        if(bundle!=null)
        {
            idFuncionario = bundle.getString("idFuncionario");
        }
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.bt_add_servico_funcionario);
        lista = (RecyclerView) view.findViewById(R.id.listServicos_funcionario);
        lista.setHasFixedSize(true);
        loading.abrir("Atualizando...");
        getServicos(idFuncionario);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Cadastro_servico_funcionario.class);
                startActivity(intent);
            }
        });

        return view;
    }
    public void getServicos(String id)
    {


        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<ServicoSalao>> callBuscaServicosfunc = iApi.BuscaServicosFuncionario(id);
        callBuscaServicosfunc.enqueue(new Callback<List<ServicoSalao>>() {
            @Override
            public void onResponse(Call<List<ServicoSalao>> call, Response<List<ServicoSalao>> response) {
                qtTentativaRealizada = 0 ;
                callBuscaServicosfunc.cancel();

                loading.fechar();

                switch (response.code())
                {
                    case 200:
                        List<ServicoSalao> ListaServicos = response.body();
                        LinearLayoutManager layout = new LinearLayoutManager(getContext());
                        layout.setOrientation(LinearLayoutManager.VERTICAL);
                        lista.setAdapter(new Adaptador_servico_funcionario(ListaServicos,idFuncionario,view));
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

                    getServicos(idFuncionario);
                }
                else {
                    loading.fechar();
                    Log.d("xex","erro");
                    Log.d("xex",t.getMessage());
                }
            }
        });

    }










    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
