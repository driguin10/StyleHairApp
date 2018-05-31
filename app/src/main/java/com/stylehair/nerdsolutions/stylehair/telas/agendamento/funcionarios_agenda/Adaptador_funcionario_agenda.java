package com.stylehair.nerdsolutions.stylehair.telas.agendamento.funcionarios_agenda;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionarioBusca;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.viewHolderFuncionario;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dherrera on 15/03/2017.
 */

public class Adaptador_funcionario_agenda extends RecyclerView.Adapter<viewHolderFuncionario_agenda> {

    List<UsuarioFuncionarioBusca> ListaFuncionario;
    RecyclerView lista;
    Button Btprosseguir;
    String IdSalao;
    ArrayList<String> ListaServicos;


    public Adaptador_funcionario_agenda(List<UsuarioFuncionarioBusca> listaFuncionario, RecyclerView listaa, Button btProsseguir,String idSalao,ArrayList<String> listaServicos) {
        ListaFuncionario = listaFuncionario;
        lista = listaa;
        Btprosseguir = btProsseguir;
        IdSalao = idSalao;
        ListaServicos = listaServicos;
    }


    @Override
    public viewHolderFuncionario_agenda onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_funcionario_busca, parent, false);
        return new viewHolderFuncionario_agenda(vista,ListaFuncionario,Btprosseguir);
    }

    @Override
    public void onBindViewHolder(viewHolderFuncionario_agenda holder, int position) {
         holder.lista = lista;
         String[] Lservicos = ListaFuncionario.get(position).getServico().split("#");
        ArrayList<String> LServaux = new ArrayList<>();
        String Sconcat = "";
         for (int x = 0 ; x<Lservicos.length;x++)
         {
             String[] Laux = Lservicos[x].split("-");
             LServaux.add(Laux[0]);
             Sconcat = Sconcat + "\n" + Laux[1];
         }


         holder.ListaIdServ = LServaux;
         holder.Servicos.setText(Sconcat);
         holder.idSalao =IdSalao;
         holder.listaServicos = ListaServicos;
         holder.NomeFuncionario.setText(ListaFuncionario.get(position).getNome());
         Picasso.with(holder.contexto).load("http://stylehair.xyz/" + ListaFuncionario.get(position).linkImagem).into(holder.imagemFunc);
}
    @Override
    public int getItemCount() {
        return ListaFuncionario.size();
    }

}

