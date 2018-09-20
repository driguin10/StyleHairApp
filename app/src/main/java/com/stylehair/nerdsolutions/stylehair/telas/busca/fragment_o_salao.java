package com.stylehair.nerdsolutions.stylehair.telas.busca;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Tutorial;
import com.stylehair.nerdsolutions.stylehair.telas.agendamento.servicos_agenda.escolherServico;
import com.stylehair.nerdsolutions.stylehair.telas.configuracaoApp;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.minhaConta;


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

   // CardView cardAgendar;
    String idSalao;
    String tipo;
    SharedPreferences getSharedPreferencesL;
    Tutorial tutorial;
    ShowcaseView sv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_o_salao, container, false);
         getSharedPreferencesL = PreferenceManager
                .getDefaultSharedPreferences(getContext());
         tipo = getSharedPreferencesL.getString("typeUserApp","COMUM");

        tutorial = new Tutorial(getContext());
        txtSobre=(TextView) view.findViewById(R.id.txt_SobreSalao);
        txtLocalizacao=(TextView) view.findViewById(R.id.txtLocalizacao);
        txtTelefones=(TextView) view.findViewById(R.id.txtTelefones);
        txtEmail=(TextView) view.findViewById(R.id.txtEmail);
        txtCnpj=(TextView) view.findViewById(R.id.txtCnpj);

       // cardAgendar=(CardView) view.findViewById(R.id.cardAgendar);
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
            if(bundle.getString("sobre")!=null)
               if(!bundle.getString("sobre").equals(""))
                   txtSobre.setText( bundle.getString("sobre"));
               else
                   txtSobre.setText("..........");
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



            /*if(bundle.getString("agendamento").equals("1"))
            {
                if(!tipo.equals("COMUM"))
                {
                    cardAgendar.setAlpha(1f);
                    cardAgendar.setCardElevation(5);
                    cardAgendar.setCardBackgroundColor(getResources().getColor(R.color.corAberto));
                }
                else
                {
                    cardAgendar.setAlpha(.4f);
                    cardAgendar.setCardElevation(0);
                    cardAgendar.setCardBackgroundColor(getResources().getColor(R.color.corFechado));
                }
            }
            else
            {
                cardAgendar.setAlpha(.4f);
                cardAgendar.setCardElevation(0);
                cardAgendar.setCardBackgroundColor(getResources().getColor(R.color.corFechado));
            }

            */
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

       /*cardAgendar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(!tipo.equals("COMUM")) {
                   Intent intent = new Intent(getActivity(), escolherServico.class);
                   intent.putExtra("idSalao", idSalao);
                   startActivity(intent);
               }else {

                   new AlertDialog.Builder(getContext())
                           .setTitle("Deseja concluir seu cadastro?")
                           .setMessage("Para efetuar agendamentos é necessario concluir seu cadastro.")
                           .setIcon(R.drawable.icone_funcionario_preto)
                           .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int which) {
                                   Intent intent = new Intent(getContext(), minhaConta.class);
                                   ((Activity) getContext()).startActivityForResult(intent, 0);
                               }
                           })
                           .setNegativeButton("não", new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int which) {
                                   dialog.dismiss();
                               }
                           })
                           .show();
               }
           }
       });*/

        return  view;
    }


  /*  public void tutorial(){
        if(!tutorial.verTutorial("fragmentGerente")) {
            sv = new ShowcaseView.Builder(getActivity())
                    .withMaterialShowcase()
                    .setTarget(new ViewTarget(btPesquisaSalao))
                    .setContentTitle("teste")
                    .setContentText("mem")
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .setShowcaseEventListener(new SimpleShowcaseEventListener() {

                        @Override
                        public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                            sv = new ShowcaseView.Builder(getActivity())
                                    .withMaterialShowcase()
                                    .setTarget(new ViewTarget(btAbrir))
                                    .setContentTitle("abrir salao")
                                    .setContentText("mem")
                                    .setStyle(R.style.CustomShowcaseTheme2)
                                    .setShowcaseEventListener(new SimpleShowcaseEventListener() {
                                        @Override
                                        public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                                            tutorial.salvaShared("fragmentGerente");
                                        }
                                    })
                                    .build();
                        }
                    }).build();
        }
    }*/

}
