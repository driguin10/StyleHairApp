package com.stylehair.nerdsolutions.stylehair.telas.agendamento.servicos_agenda;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dherrera on 15/03/2017.
 */

public class Adaptador_ecolherservico_salao extends RecyclerView.Adapter<viewHolderescolherServicoSalao> {

    List<ServicoSalao> ListaServicoSalao;
    TextView qtServicosEscolhido;
    ArrayList<String> lista;
    Button bt_Prosseguir;
    ImageButton bt_ListaServicos;


    public Adaptador_ecolherservico_salao(List<ServicoSalao> listaServicoSalao,TextView ServicosEscolhido, ArrayList<String> list, Button BtProsseguir,ImageButton BtListaServicos) {
        ListaServicoSalao = listaServicoSalao;
        qtServicosEscolhido = ServicosEscolhido;
        lista = list;
        bt_Prosseguir = BtProsseguir;
        bt_ListaServicos = BtListaServicos;

    }


    @Override
    public viewHolderescolherServicoSalao onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_servico_grid, parent, false);
        return new viewHolderescolherServicoSalao(vista,ListaServicoSalao,bt_Prosseguir,bt_ListaServicos);
    }

    @Override
    public void onBindViewHolder(viewHolderescolherServicoSalao holder, int position) {
         holder.NomeServico.setText(ListaServicoSalao.get(position).getServico());
         holder.valor.setText("R$"+String.valueOf(ListaServicoSalao.get(position).getValor()));
         holder.qtServicosEscolhido = qtServicosEscolhido;
         holder.lista = lista;
}
    @Override
    public int getItemCount() {
        return ListaServicoSalao.size();
    }

}

