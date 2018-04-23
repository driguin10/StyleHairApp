package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.notificacoes;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionario;

import java.util.List;

/**
 * Created by dherrera on 15/03/2017.
 */

public class Adaptador_funcionarioNotif extends RecyclerView.Adapter<viewHolderFuncionarioNotif> {

    List<UsuarioFuncionario> ListaFuncionario;
Activity activity;


    public Adaptador_funcionarioNotif(List<UsuarioFuncionario> listaFuncionario,Activity act) {
        ListaFuncionario = listaFuncionario;
        this.activity = act;
    }


    @Override
    public viewHolderFuncionarioNotif onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_funcionario, parent, false);

        return new viewHolderFuncionarioNotif(vista,ListaFuncionario);
    }

    @Override
    public void onBindViewHolder(viewHolderFuncionarioNotif holder, int position) {
         holder.NomeFuncionario.setText(ListaFuncionario.get(position).getNome());
         Picasso.with(holder.contexto).load("http://stylehair.xyz/" + ListaFuncionario.get(position).linkImagem).into(holder.imagemFunc);
        holder.activity = this.activity;
    }
    @Override
    public int getItemCount() {
        return ListaFuncionario.size();
    }

}

