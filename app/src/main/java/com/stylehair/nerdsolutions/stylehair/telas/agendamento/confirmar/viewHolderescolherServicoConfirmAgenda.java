package com.stylehair.nerdsolutions.stylehair.telas.agendamento.confirmar;


import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;
import com.stylehair.nerdsolutions.stylehair.R;
import java.util.List;

public class viewHolderescolherServicoConfirmAgenda extends ViewHolder  {
    TextView NomeServico;
    TextView valor;
    List<String> ListaServicoSalao;

    public viewHolderescolherServicoConfirmAgenda(View itemView, List<String> dados) {
        super(itemView);
        NomeServico = (TextView) itemView.findViewById(R.id.nome_servico);
        valor = (TextView) itemView.findViewById(R.id.valor_servico);
        ListaServicoSalao = dados;
    }
}

