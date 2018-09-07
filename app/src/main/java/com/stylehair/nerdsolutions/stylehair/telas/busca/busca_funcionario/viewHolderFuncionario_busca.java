package com.stylehair.nerdsolutions.stylehair.telas.busca.busca_funcionario;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Permissoes;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.telas.cadastroSalao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.ver_funcionario;
import com.stylehair.nerdsolutions.stylehair.telas.principal;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class viewHolderFuncionario_busca extends ViewHolder implements View.OnClickListener  {
    CircleImageView imagemFunc;
    TextView NomeFuncionario;
    TextView Numero;
    CardView btLigar;
    Context contexto;
    List<UsuarioFuncionario> ListaUsuario;
    UsuarioFuncionario usuarioFuncionario;

    public viewHolderFuncionario_busca(View itemView, List<UsuarioFuncionario> dados) {
        super(itemView);
        imagemFunc = (CircleImageView) itemView.findViewById(R.id.img_funcionario);
        NomeFuncionario = (TextView) itemView.findViewById(R.id.nome_funcionario);
        Numero = (TextView) itemView.findViewById(R.id.txt_funcNumero);
        btLigar = (CardView) itemView.findViewById(R.id.card_btLigar);
        btLigar.setOnClickListener(this);
        ListaUsuario = dados;
        contexto = itemView.getContext();
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        usuarioFuncionario = ListaUsuario.get(position);
        Permissoes permissoes = new Permissoes();
        if(permissoes.habilitarLigacao((Activity)v.getContext())) {
            try {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+usuarioFuncionario.getTelefone()));
                v.getContext().startActivity(callIntent);
            }
            catch (SecurityException s)
            {

            }
        }
    }
}
