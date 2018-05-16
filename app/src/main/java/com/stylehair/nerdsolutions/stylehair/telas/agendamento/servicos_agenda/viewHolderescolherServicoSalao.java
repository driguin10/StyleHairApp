package com.stylehair.nerdsolutions.stylehair.telas.agendamento.servicos_agenda;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.telas.agendamento.funcionarios_agenda.escolherFuncionario;
import com.stylehair.nerdsolutions.stylehair.telas.agendamento.servicos_agenda.escolhido.lista_servi_escolhido;

import java.util.ArrayList;
import java.util.List;

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
                    .setIcon(R.drawable.icone_servicos)
                    .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                             int vl = Integer.valueOf(qtServicosEscolhido.getText().toString());
                             qtServicosEscolhido.setText(String.valueOf(vl + 1));
                             String Serv = String.valueOf(servicoSalao.getIdServicoSalao())+"#"+servicoSalao.getServico()+"#"+ String.valueOf(servicoSalao.getValor())+"#"+servicoSalao.getTempo();
                             lista.add(Serv);
                             Toast.makeText(contexto,servicoSalao.getServico()+" adicionado",Toast.LENGTH_LONG).show();

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
            if(lista.size()>0) {
                Intent intent = new Intent(v.getContext(), escolherFuncionario.class);
                intent.putStringArrayListExtra("escolhas", lista);
                intent.putExtra("idSalao", String.valueOf(ListaServicoSalao.get(position).getIdSalao()));
                v.getContext().startActivity(intent);
            }
            else
            {
                Toast.makeText(v.getContext(),"Selecione os serviços.",Toast.LENGTH_LONG).show();
            }
        }else
        if (v.getId() == btListaServicos.getId()) {

            if(lista.size()>0) {
                Intent intent = new Intent(v.getContext(), lista_servi_escolhido.class);
                intent.putStringArrayListExtra("ListaServ", lista);
               // v.getContext().startActivity(intent);
                ((Activity) v.getContext()).startActivityForResult(intent,1);


            }
            else
            {
                Toast.makeText(v.getContext(),"Selecione os serviços.",Toast.LENGTH_LONG).show();
            }

        }


    }



}

