package com.stylehair.nerdsolutions.stylehair.telas;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.stylehair.nerdsolutions.stylehair.Notification.notificacao;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Permissoes;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Tutorial;
import com.stylehair.nerdsolutions.stylehair.telas.busca.busca_salao;
import com.stylehair.nerdsolutions.stylehair.telas.favorito.saloesFavoritos;
import com.stylehair.nerdsolutions.stylehair.telas.minhaAgenda.minha_agenda;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.minhaConta;


public class fragment_principal_usuario extends Fragment {
    TextInputLayout txtPesquisa;
    ImageButton btPesquisar;
    String typeUser;
    CardView btAgendaDia;
    CardView btFavoritos;
    CardView btNotificacoes;
    Permissoes permissoes;
    ShowcaseView sv;
    Tutorial tutorial;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        permissoes = new Permissoes();
        View v = inflater.inflate(R.layout.fragment_fragment_principal_usuario, container, false);
        txtPesquisa = (TextInputLayout)v.findViewById(R.id.txtProcura);
        btPesquisar = (ImageButton)v.findViewById(R.id.btPesquisar);
        btAgendaDia = (CardView)v.findViewById(R.id.card_bt1);
        btFavoritos = (CardView)v.findViewById(R.id.card_bt2);
        btNotificacoes = (CardView)v.findViewById(R.id.card_bt3);
        tutorial = new Tutorial(getContext());
        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getContext());
        typeUser = getSharedPreferences.getString("typeUserApp", "");
        btAgendaDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(typeUser.equals("COMUM")) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Deseja concluir seu cadastro?")
                            .setMessage("Para utilizar agendamentos é necessario concluir seu cadastro.")
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
                else
                {
                    Intent intent = new Intent(getActivity(),minha_agenda.class);
                    intent.putExtra("tipo","0");
                    startActivity(intent);
                }
            }
        });

        btPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(permissoes.habilitarLocalizacao(getActivity())) {
                    Intent intent = new Intent(getActivity(),busca_salao.class);
                    intent.putExtra("query",txtPesquisa.getEditText().getText().toString());
                    startActivity(intent);
                    txtPesquisa.getEditText().setText("");
                }
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
        tutorial();
        return v;
    }

    public void tutorial(){
        if(!tutorial.verTutorial("fragmentUsuario")) {
            sv = new ShowcaseView.Builder(getActivity())
                    .withMaterialShowcase()
                    .setTarget(new ViewTarget(btPesquisar))
                    .setContentTitle("Pesquisar Salões")
                    .setContentText("Aqui você pode buscar por salões, agendar horarios...")
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .setShowcaseEventListener(new SimpleShowcaseEventListener(){
                        @Override
                        public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                            tutorial.salvaShared("fragmentUsuario");
                        }
                    })
                    .build();
        }
    }


}
