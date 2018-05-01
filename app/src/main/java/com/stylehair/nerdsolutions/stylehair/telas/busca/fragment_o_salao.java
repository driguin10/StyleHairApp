package com.stylehair.nerdsolutions.stylehair.telas.busca;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;


public class fragment_o_salao extends Fragment {


   /* String endereco = "";
    String email = "";
    String sobre = "";
    String status = "";
    String agendamento = "";
    String cnpj = "";
    String telefones = "";

    String segE = "";
    String segS = "";

    String terE = "";
    String terS = "";

    String quaE = "";
    String quaS = "";

    String quiE = "";
    String quiS = "";

    String sexE = "";
    String sexS = "";

    String sabE = "";
    String sabS = "";

    String domE = "";
    String domS = "";*/




    Bundle bundle;
    TextView txtSobre;
    TextView txtLocalizacao;
    TextView txtTelefones;
    TextView txtEmail;
    TextView txtCnpj;
    TextView txtStatus;
    CardView cardStatus;
    CardView cardAgendar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_o_salao, container, false);
        txtSobre=(TextView) view.findViewById(R.id.txt_SobreSalao);
        txtLocalizacao=(TextView) view.findViewById(R.id.txtLocalizacao);
        txtTelefones=(TextView) view.findViewById(R.id.txtTelefones);
        txtEmail=(TextView) view.findViewById(R.id.txtEmail);
        txtCnpj=(TextView) view.findViewById(R.id.txtCnpj);
        txtStatus=(TextView) view.findViewById(R.id.txtStatus);
        cardStatus=(CardView) view.findViewById(R.id.cardStatus);
        cardAgendar=(CardView) view.findViewById(R.id.cardAgendar);

        if(bundle!=null)
        {
            txtSobre.setText( bundle.getString("sobre"));
            txtLocalizacao.setText( bundle.getString("endereco"));
            txtTelefones.setText( bundle.getString("telefones"));
            txtEmail.setText( bundle.getString("email"));
            txtCnpj.setText( bundle.getString("cnpj"));

            if(bundle.getString("status").equals("1"))
            {
               txtStatus.setText("Salão Aberto");
               cardStatus.setCardBackgroundColor(getResources().getColor(R.color.corAberto));
            }
            else
            {
                txtStatus.setText("Salão Fechado");
                cardStatus.setCardBackgroundColor(getResources().getColor(R.color.corFechado));
            }

            if(bundle.getString("agendamento").equals("1"))
            {
                cardAgendar.setAlpha(.9f);
                cardAgendar.setCardElevation(5);
                cardAgendar.setCardBackgroundColor(getResources().getColor(R.color.corAberto));
            }
            else
            {
                cardAgendar.setAlpha(.4f);
                cardAgendar.setCardElevation(0);
                cardAgendar.setCardBackgroundColor(getResources().getColor(R.color.corFechado));
            }


           /*endereco = bundle.getString("endereco");
           email = bundle.getString("email");
           sobre = bundle.getString("sobre");
           status = bundle.getString("status");
           agendamento = bundle.getString("agendamento");
           cnpj = bundle.getString("cnpj");
           telefones = bundle.getString("telefones");
           segE = bundle.getString("segE");
            segS = bundle.getString("segS");
            terE = bundle.getString("terE");
            terS = bundle.getString("terS");
            quaE = bundle.getString("quaE");
            quaS = bundle.getString("quaS");
            quiE = bundle.getString("quiE");
            quiS = bundle.getString("quiS");
            sexE = bundle.getString("sexE");
            sexS = bundle.getString("sexS");
            sabE = bundle.getString("sabE");
            sabS = bundle.getString("sabS");
            domE = bundle.getString("domE");
            domS = bundle.getString("domS");*/

        }

        //Log.d("xex","> " + teste);
        return  view;
    }





}