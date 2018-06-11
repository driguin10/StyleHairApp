package com.stylehair.nerdsolutions.stylehair.telas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.stylehair.nerdsolutions.stylehair.Notification.notificacao;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.telas.busca.busca_salao;


public class fragment_principal_usuario extends Fragment {


    TextInputLayout txtPesquisa;
    ImageButton btPesquisar;
    String typeUser;
    CardView btAgendaDia;
    CardView btFavoritos;
    CardView btNotificacoes;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_principal_usuario, container, false);
        txtPesquisa = (TextInputLayout)v.findViewById(R.id.txtProcura);
        btPesquisar = (ImageButton)v.findViewById(R.id.btPesquisar);
        btAgendaDia = (CardView)v.findViewById(R.id.card_bt1);
        btFavoritos = (CardView)v.findViewById(R.id.card_bt2);
        btNotificacoes = (CardView)v.findViewById(R.id.card_bt3);


        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getContext());

        typeUser = getSharedPreferences.getString("typeUserApp", "");

        if(typeUser.equals("COMUMUSER"))
        {
            btAgendaDia.setEnabled(false);
            btAgendaDia.setClickable(false);
            btAgendaDia.setAlpha(.4f);
        }
        else
        {
            btAgendaDia.setEnabled(true);
            btAgendaDia.setClickable(true);
            btAgendaDia.setAlpha(1f);
        }


        btAgendaDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),busca_salao.class);
                intent.putExtra("tipo","0");
                startActivity(intent);
            }
        });

        btPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),busca_salao.class);
                intent.putExtra("query",txtPesquisa.getEditText().getText().toString());
                startActivity(intent);
                txtPesquisa.getEditText().setText("");
            }
        });

        btFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),saloesFavoritos.class);
                startActivity(intent);
            }
        });

        btNotificacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),notificacao.class);
                startActivity(intent);
            }
        });

        return v;
    }




}
