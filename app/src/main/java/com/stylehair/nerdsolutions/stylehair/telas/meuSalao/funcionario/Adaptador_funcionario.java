package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionario;

import java.util.List;

/**
 * Created by dherrera on 15/03/2017.
 */

public class Adaptador_funcionario extends RecyclerView.Adapter<viewHolderFuncionario> {

    List<UsuarioFuncionario> ListaFuncionario;



    public Adaptador_funcionario(List<UsuarioFuncionario> listaFuncionario) {
        ListaFuncionario = listaFuncionario;
    }


    @Override
    public viewHolderFuncionario onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_funcionario, parent, false);

        return new viewHolderFuncionario(vista,ListaFuncionario);
    }

    @Override
    public void onBindViewHolder(viewHolderFuncionario holder, int position) {
         holder.NomeFuncionario.setText(ListaFuncionario.get(position).getNome());
         Picasso.with(holder.contexto).load("http://stylehair.xyz/" + ListaFuncionario.get(position).linkImagem).into(holder.imagemFunc);
}
    @Override
    public int getItemCount() {
        return ListaFuncionario.size();
    }

}

