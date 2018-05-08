package com.stylehair.nerdsolutions.stylehair.telas.busca.busca_funcionario;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.telas.busca.busca_funcionario.viewHolderFuncionario_busca;

import java.util.List;

/**
 * Created by dherrera on 15/03/2017.
 */

public class Adaptador_funcionario_busca extends RecyclerView.Adapter<viewHolderFuncionario_busca> {

    List<UsuarioFuncionario> ListaFuncionario;



    public Adaptador_funcionario_busca(List<UsuarioFuncionario> listaFuncionario) {
        ListaFuncionario = listaFuncionario;
    }


    @Override
    public viewHolderFuncionario_busca onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_verfuncionario_busca, parent, false);

        return new viewHolderFuncionario_busca(vista,ListaFuncionario);
    }

    @Override
    public void onBindViewHolder(viewHolderFuncionario_busca holder, int position) {
         holder.NomeFuncionario.setText(ListaFuncionario.get(position).getNome());
         holder.Numero.setText(ListaFuncionario.get(position).getTelefone());
         Picasso.with(holder.contexto).load("http://stylehair.xyz/" + ListaFuncionario.get(position).linkImagem).into(holder.imagemFunc);
}
    @Override
    public int getItemCount() {
        return ListaFuncionario.size();
    }

}

