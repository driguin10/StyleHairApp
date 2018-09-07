package com.stylehair.nerdsolutions.stylehair.telas.agendamento.funcionarios_agenda;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionarioBusca;
import com.stylehair.nerdsolutions.stylehair.telas.agendamento.horarios.horarios_agenda;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class viewHolderFuncionario_agenda extends ViewHolder implements View.OnClickListener  {
    CircleImageView imagemFunc;
    TextView NomeFuncionario;
    TextView Servicos;
    CardView card;
    Context contexto;
    RecyclerView lista;
    Button btProsseguir;
    String idSalao;
    ArrayList<String> listaServicos;
    List<UsuarioFuncionarioBusca> ListaUsuario;
    UsuarioFuncionarioBusca usuarioFuncionario;
    ArrayList<String> ListaIdServ;
    String teste;

    public viewHolderFuncionario_agenda(View itemView, List<UsuarioFuncionarioBusca> dados,Button bt) {
        super(itemView);
        imagemFunc = (CircleImageView) itemView.findViewById(R.id.img_funcionario);
        NomeFuncionario = (TextView) itemView.findViewById(R.id.nome_funcionario);
        Servicos = (TextView) itemView.findViewById(R.id.txtServicos);
        card = (CardView) itemView.findViewById(R.id.cardsFunc);
        card.setOnClickListener(this);
        btProsseguir = bt;
        btProsseguir.setOnClickListener(this);
        ListaUsuario = dados;
        contexto = itemView.getContext();
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        usuarioFuncionario = ListaUsuario.get(position);
        if(v.getId() == card.getId())
        {
            for (int x = 0; x < ListaUsuario.size(); x++) {
                if (ListaUsuario.get(x).isSelected()) {
                    ListaUsuario.get(x).setSelected(false);
                }
            }
            lista.getAdapter().notifyDataSetChanged();
            ListaUsuario.get(position).setSelected(true);
            lista.getAdapter().notifyDataSetChanged();
        }

            if(v.getId() == btProsseguir.getId())
            {
                int posIdFunc = -1;
                for (int x = 0; x < ListaUsuario.size(); x++) {
                    if (ListaUsuario.get(x).isSelected()) {
                        posIdFunc = x;
                    }
                }
                //---------------- ids dos serviços que o funcionario faz( que o cliente escolheu)
                ///--------------- já na formula que envia pro servidor id,id
                String[] ids = ListaUsuario.get(posIdFunc).getServico().split("#");
                String Sconcat = "";
                ArrayList<String> idsAux = new ArrayList<>();
                for (int x = 0 ; x<ids.length;x++)
                {
                    String[] Laux = ids[x].split("-");
                    Sconcat = Sconcat + Laux[0] +",";
                    idsAux.add(Laux[0]);
                }
                Sconcat = Sconcat.substring(0,Sconcat.length()-1);
                //--------------------------------------------------


               //-----------pega os id dos serviços escolhidos pelo usuario
                ArrayList<String> listaServicoIds= new ArrayList<>();
                for(int t=0;t<listaServicos.size();t++)
                {
                    listaServicoIds.add(listaServicos.get(t).split("#")[0]);
                }
                //--------------------------------------------------------------


                //-------verifica e cria a lista de serviços q o funcionario pode prestar dos quais o clinete escolheu
                String[] SconcatAux = Sconcat.split(",");
                ArrayList<String> listaServicosAux = new ArrayList<>();
                for(int x =0;x<SconcatAux.length;x++)//id do servico que o cliente faz
                {
                    for(int t=0;t<listaServicos.size();t++)
                    {
                        if(SconcatAux[x].equals(listaServicoIds.get(t)))
                        {
                            listaServicosAux.add(listaServicos.get(t));
                        }
                    }
                }
                //-----------------------------------------------------------------------
                if (posIdFunc == -1) {
                    Toast.makeText(contexto, "Escolha um Funcionário.", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(contexto,horarios_agenda.class);
                    intent.putExtra("servicos",Sconcat);
                    intent.putExtra("idFuncionario",String.valueOf(ListaUsuario.get(posIdFunc).idFuncionario));
                    intent.putExtra("idSalao",idSalao);
                    intent.putExtra("nomeFuncionario",ListaUsuario.get(posIdFunc).getNome());
                    intent.putExtra("imagemFuncionario",ListaUsuario.get(posIdFunc).getLinkImagem());
                    intent.putStringArrayListExtra("ListaServicos", listaServicosAux);
                    contexto.startActivity(intent);
                }
            }

    }
}
