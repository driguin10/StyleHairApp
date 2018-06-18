package com.stylehair.nerdsolutions.stylehair.telas.busca;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.telas.agendamento.servicos_agenda.escolherServico;


public class fragment_o_salao extends Fragment {





    TextView segE ;
    TextView segS ;

    TextView terE ;
    TextView terS ;

    TextView quaE ;
    TextView quaS ;

    TextView quiE ;
    TextView quiS ;

    TextView sexE;
    TextView sexS;

    TextView sabE ;
    TextView sabS ;

    TextView domE;
    TextView domS;


    String SegE ="folga";
    String SegS ="folga";

    String TerE="folga" ;
    String TerS ="folga";

    String QuaE="folga" ;
    String QuaS="folga" ;

    String QuiE="folga" ;
    String QuiS ="folga";

    String SexE="folga";
    String SexS="folga";

    String SabE="folga" ;
    String SabS="folga" ;

    String DomE="folga";
    String DomS="folga";


    Bundle bundle;
    TextView txtSobre;
    TextView txtLocalizacao;
    TextView txtTelefones;
    TextView txtEmail;
    TextView txtCnpj;
    TextView txtStatus;
    CardView cardStatus;
    CardView cardAgendar;
    String idSalao;
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


        segE=(TextView) view.findViewById(R.id.txtSegE);
        segS=(TextView) view.findViewById(R.id.txtSegS);
        terE=(TextView) view.findViewById(R.id.txtTerE);
        terS=(TextView) view.findViewById(R.id.txtTerS);
        quaE=(TextView) view.findViewById(R.id.txtQuaE);
        quaS=(TextView) view.findViewById(R.id.txtQuaS);
        quiE=(TextView) view.findViewById(R.id.txtQuiE);
        quiS=(TextView) view.findViewById(R.id.txtQuiS);
        sexE=(TextView) view.findViewById(R.id.txtSexE);
        sexS=(TextView) view.findViewById(R.id.txtSexS);
        sabE=(TextView) view.findViewById(R.id.txtSabE);
        sabS=(TextView) view.findViewById(R.id.txtSabS);
        domE=(TextView) view.findViewById(R.id.txtDomE);
        domS=(TextView) view.findViewById(R.id.txtDomS);



        if(bundle!=null)
        {
            txtSobre.setText( bundle.getString("sobre"));
            txtLocalizacao.setText( bundle.getString("endereco"));
            txtTelefones.setText( bundle.getString("telefones"));
            txtEmail.setText( bundle.getString("email"));
            txtCnpj.setText( bundle.getString("cnpj"));
            idSalao = bundle.getString("idSalao");

            if(bundle.getString("segE")!=null)
            SegE = bundle.getString("segE").substring(0,5);

            if(bundle.getString("segS")!=null)
            SegS = bundle.getString("segS").substring(0,5);

            if(bundle.getString("terE")!=null)
            TerE = bundle.getString("terE").substring(0,5);

            if(bundle.getString("terS")!=null)
            TerS = bundle.getString("terS").substring(0,5);


            if(bundle.getString("quaE")!=null)
            QuaE = bundle.getString("quaE").substring(0,5);

            if(bundle.getString("quaS")!=null)
            QuaS = bundle.getString("quaS").substring(0,5);

            if(bundle.getString("quiE")!=null)
            QuiE = bundle.getString("quiE").substring(0,5);

            if(bundle.getString("quiS")!=null)
            QuiS = bundle.getString("quiS").substring(0,5);

            if(bundle.getString("sexE")!=null)
            SexE = bundle.getString("sexE").substring(0,5);

            if(bundle.getString("sexS")!=null)
            SexS = bundle.getString("sexS").substring(0,5);

            if(bundle.getString("sabE")!=null)
            SabE = bundle.getString("sabE").substring(0,5);

            if(bundle.getString("sabS")!=null)
            SabS = bundle.getString("sabS").substring(0,5);

            if(bundle.getString("domE")!=null)
            DomE = bundle.getString("domE").substring(0,5);

            if(bundle.getString("domS")!=null)
            DomS = bundle.getString("domS").substring(0,5);

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
                cardAgendar.setAlpha(1f);
                cardAgendar.setCardElevation(5);
                cardAgendar.setCardBackgroundColor(getResources().getColor(R.color.corAberto));
                cardAgendar.setClickable(true);
                cardAgendar.setEnabled(true);
            }
            else
            {
                cardAgendar.setAlpha(.4f);
                cardAgendar.setCardElevation(0);
                cardAgendar.setCardBackgroundColor(getResources().getColor(R.color.corFechado));
                cardAgendar.setClickable(false);
                cardAgendar.setEnabled(false);
            }





        }


        segE.setText(SegE);
        segS.setText(SegS);
        terE.setText(TerE);
        terS.setText(TerS);
        quaE.setText(QuaE);
        quaS.setText(QuaS);
        quiE.setText(SegE);
        quiS.setText(SegE);
        sexE.setText(SexE);
        sexS.setText(SexS);
        sabE.setText(SabE);
        sabS.setText(SabS);
        domE.setText(DomE);
        domS.setText(DomS);



       cardAgendar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(),escolherServico.class);
               intent.putExtra("idSalao",idSalao);
               startActivity(intent);
           }
       });
        return  view;
    }





}
