package com.stylehair.nerdsolutions.stylehair.telas.agendamento.confirmar;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.telas.agendamento.servicos_agenda.viewHolderescolherServicoSalao;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Adaptador_servicoConfirmAgenda extends RecyclerView.Adapter<viewHolderescolherServicoConfirmAgenda> {

    List<String> ListaServicoSalao;
    TextView txtValor;

    public Adaptador_servicoConfirmAgenda(List<String> listaServicoSalao,TextView txtValorTotal) {
        ListaServicoSalao = listaServicoSalao;
        txtValor = txtValorTotal;


       float total = 0;
        for(int x =0;x<ListaServicoSalao.size();x++)
        {
            String[] teste = ListaServicoSalao.get(x).split("#");
            total = total +  Float.valueOf(teste[2]);
        }
        txtValor.setText("R$ "+ String.format(Locale.getDefault(),"%.2f", total));
    }

    @Override
    public viewHolderescolherServicoConfirmAgenda onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_servico_agenda, parent, false);
        return new viewHolderescolherServicoConfirmAgenda(vista,ListaServicoSalao);
    }

    @Override
    public void onBindViewHolder(viewHolderescolherServicoConfirmAgenda holder, int position) {
        String[] teste = ListaServicoSalao.get(position).split("#");
         holder.NomeServico.setText(teste[1]);
         String Valor = "R$ " +String.format(Locale.getDefault(),"%.2f", Float.valueOf(teste[2])) ;
         holder.valor.setText(Valor);
    }

    @Override
    public int getItemCount() {
        return ListaServicoSalao.size();
    }

}

