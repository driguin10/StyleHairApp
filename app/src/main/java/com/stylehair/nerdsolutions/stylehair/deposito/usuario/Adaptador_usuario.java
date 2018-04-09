package com.stylehair.nerdsolutions.stylehair.deposito.usuario;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;

import java.util.List;

/**
 * Created by dherrera on 15/03/2017.
 */

public class Adaptador_usuario extends RecyclerView.Adapter<viewHolderUsuario> {

    List<Usuario> ListaUsuario;


    public Adaptador_usuario(List<Usuario> listaUsuario) {
        ListaUsuario = listaUsuario;
    }

    @Override
    public viewHolderUsuario onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_funcionario, parent, false);

        return new viewHolderUsuario(vista,ListaUsuario);
    }

    @Override
    public void onBindViewHolder(viewHolderUsuario holder, int position) {
         holder.NomeFuncionario.setText(ListaUsuario.get(position).getNome());
         Picasso.with(holder.contexto).load("http://stylehair.xyz/" + ListaUsuario.get(position).linkImagem).into(holder.imagemFunc);
}
    @Override
    public int getItemCount() {
        return ListaUsuario.size();
    }

}

