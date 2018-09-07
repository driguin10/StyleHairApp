package com.stylehair.nerdsolutions.stylehair.telas.agendamento.servicos_agenda.escolhido;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;

import java.util.List;


public class viewHolderServicoSalao_escolhido extends ViewHolder implements View.OnClickListener  {
    TextView NomeServico;
    TextView valor;
    CardView card;
    Context contexto;
    ImageButton excluir;
    List<ServicoSalao> ListaServicoSalao;
    ServicoSalao servicoSalao;
    RecyclerView Rlista;
    Button btinfo;
    int qtTentativas = 3;
    int qtTentativaRealizada = 0;
    Loading loading;

    public viewHolderServicoSalao_escolhido(View itemView, List<ServicoSalao> dados) {
        super(itemView);
        NomeServico = (TextView) itemView.findViewById(R.id.nome_servico);
        valor = (TextView) itemView.findViewById(R.id.valor_servico);
        card = (CardView) itemView.findViewById(R.id.cardsServico);
        excluir = (ImageButton) itemView.findViewById(R.id.bt_excluir_servico);
        card.setOnClickListener(this);
        excluir.setOnClickListener(this);
        ListaServicoSalao = dados;
        contexto = itemView.getContext();
    }

    @Override
    public void onClick(View v) {
        final int position = getAdapterPosition();
        servicoSalao = ListaServicoSalao.get(position);

        if (v.getId() == excluir.getId())
        {
            new AlertDialog.Builder(contexto)
                    .setTitle("Remover Serviço")
                    .setMessage("Deseja remover este serviço?")
                    .setIcon(R.drawable.icone_delete)
                    .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            List<ServicoSalao> novaLista = ListaServicoSalao;
                            novaLista.remove(position);
                            Rlista.setAdapter(new Adaptador_servico_salaoE(novaLista,Rlista,btinfo));
                            Toast.makeText(contexto,servicoSalao.getServico()+ " removido.",Toast.LENGTH_LONG).show();
                            String textoBt = "VALOR TOTAL R$ ";
                            float valor = 0;
                            for(int x=0; x<novaLista.size();x++)
                            {
                               valor = valor + novaLista.get(x).getValor();
                            }
                            btinfo.setText(textoBt+String.valueOf(valor));
                        }
                    })
                    .setNegativeButton("não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }
    }

