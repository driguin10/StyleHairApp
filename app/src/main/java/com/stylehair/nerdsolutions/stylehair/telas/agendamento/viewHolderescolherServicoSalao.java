package com.stylehair.nerdsolutions.stylehair.telas.agendamento;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.telas.busca.verSalao_buscado;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.notificacoes.Notificacao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.servico.servicos_salao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.servico.ver_servico_salao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dherrera on 15/03/2017.
 */

public class viewHolderescolherServicoSalao extends ViewHolder implements View.OnLongClickListener,View.OnClickListener  {



    TextView NomeServico;
    TextView valor;
    CardView card;
    Context contexto;
    TextView qtServicosEscolhido;

    List<ServicoSalao> ListaServicoSalao;
    ServicoSalao servicoSalao;

    int qtTentativas = 3;
    int qtTentativaRealizada = 0;

        Loading loading;

        ArrayList<String> lista;

    Button btProsseguir;
    ImageButton btListaServicos;
    AlertDialog alerta;
    public viewHolderescolherServicoSalao(View itemView, List<ServicoSalao> dados,Button bt_Prosseguir,ImageButton bt_ListaServicos) {
        super(itemView);

        btProsseguir = bt_Prosseguir;
        btListaServicos = bt_ListaServicos;
        NomeServico = (TextView) itemView.findViewById(R.id.nome_servico);
        valor = (TextView) itemView.findViewById(R.id.valor_servico);
        card = (CardView) itemView.findViewById(R.id.cardsServico);

        card.setOnLongClickListener(this);
        ListaServicoSalao = dados;
        contexto = itemView.getContext();
        btProsseguir.setOnClickListener(this);
        btListaServicos.setOnClickListener(this);


    }




    @Override
    public boolean onLongClick(View v) {
        int position = getAdapterPosition();
        servicoSalao = ListaServicoSalao.get(position);


        if(v.getId() == card.getId())
        {
            new AlertDialog.Builder(contexto)
                    .setTitle("Adicionar Serviço?")
                    .setMessage("Deseja adicionar este serviço")
                    .setIcon(R.drawable.icone_delete)
                    .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            int vl = Integer.valueOf(qtServicosEscolhido.getText().toString());

                             qtServicosEscolhido.setText(String.valueOf(vl + 1));
                             lista.add(String.valueOf(servicoSalao.getIdServicoSalao()));

                        }
                    })
                    .setNegativeButton("não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();

        if (v.getId() == btProsseguir.getId()) {
            Intent intent = new Intent(v.getContext(), escolherFuncionario.class);
            intent.putStringArrayListExtra("escolhas", lista);
            intent.putExtra("idSalao", String.valueOf(ListaServicoSalao.get(position).getIdSalao()));
            v.getContext().startActivity(intent);
        }else
        if (v.getId() == btListaServicos.getId()) {
            Context c = v.getContext();
            LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            final View view = inflater.inflate(R.layout.activity_dialog_serv_escolhido, null);
            RecyclerView listaServs = view.findViewById(R.id.listaServEscolhidos);
            LinearLayoutManager layout = new LinearLayoutManager(c);
            layout.setOrientation(LinearLayoutManager.VERTICAL);
            listaServs.setHasFixedSize(true);
            listaServs.setAdapter(new Adaptador_ecolherservico_salao(ListaServicos,qtServicosEscolhido,list,btProsseguir,btListaServicos));
            listaServs.setLayoutManager(layout);
            listaServs.setClickable(true);
            AlertDialog.Builder builder = new AlertDialog.Builder(c);
            builder.setView(view);
            alerta = builder.create();
            alerta.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alerta.show();
        }


    }
}
