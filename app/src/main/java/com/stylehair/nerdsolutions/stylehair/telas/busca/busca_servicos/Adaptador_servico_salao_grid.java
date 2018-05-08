package com.stylehair.nerdsolutions.stylehair.telas.busca.busca_servicos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.servico.viewHolderServicoSalao;

import java.util.List;

/**
 * Created by dherrera on 15/03/2017.
 */

public class Adaptador_servico_salao_grid extends BaseAdapter {
    private Context ctx;
    private List<ServicoSalao> ListaServicoSalao;
    private LayoutInflater inflater;
    private TextView txtSemServ;

    public Adaptador_servico_salao_grid(Context ctx, List<ServicoSalao> listaServicoSalao,TextView txtSemServ) {
        this.ctx = ctx;
        ListaServicoSalao = listaServicoSalao;
       this.txtSemServ = txtSemServ;
    }


    @Override
    public int getCount() {
        return ListaServicoSalao.size();
    }

    @Override
    public Object getItem(int position) {
        return ListaServicoSalao.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;
        inflater =(LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        gridView = inflater.inflate(R.layout.estilo_servico_grid,parent,false);
        TextView servico = (TextView) gridView.findViewById(R.id.nome_servico);
        TextView valor = (TextView) gridView.findViewById(R.id.valor_servico);
        txtSemServ.bringToFront();

        if(getCount()>=0)
            txtSemServ.setVisibility(View.INVISIBLE);
        else
            txtSemServ.setVisibility(View.VISIBLE);

        servico.setText(ListaServicoSalao.get(position).getServico());
        return gridView;
    }
}

