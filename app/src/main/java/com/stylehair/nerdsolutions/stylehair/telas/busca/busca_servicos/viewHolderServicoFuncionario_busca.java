package com.stylehair.nerdsolutions.stylehair.telas.busca.busca_servicos;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.Adaptador_servico_funcionario;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class viewHolderServicoFuncionario_busca extends ViewHolder  {
    TextView NomeServico;
    TextView valor;

    Context contexto;
    List<ServicoSalao> ListaServicoSalao;
    ServicoSalao servicoSalao;

    public viewHolderServicoFuncionario_busca(View itemView, List<ServicoSalao> dados) {
        super(itemView);
        NomeServico = (TextView) itemView.findViewById(R.id.nome_servico);
        valor = (TextView) itemView.findViewById(R.id.valor_servico);
        ListaServicoSalao = dados;
        contexto = itemView.getContext();
    }
    }

