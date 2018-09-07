package com.stylehair.nerdsolutions.stylehair.telas;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Permissoes;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Tutorial;
import com.stylehair.nerdsolutions.stylehair.classes.Salao;
import com.stylehair.nerdsolutions.stylehair.telas.agendamento.pesqAgendaUser;
import com.stylehair.nerdsolutions.stylehair.telas.busca.busca_salao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.notificacoes.Notificacao;
import com.stylehair.nerdsolutions.stylehair.telas.minhaAgenda.minha_agenda;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_principal_gerente extends Fragment {
    CardView btAbrir;
    CardView btFechar;
    CardView btAlmoco;
    CardView btAgendaDia;
    CardView btAgendar;
    CardView btNotificar;
    CardView btPesquisaSalao;
    Loading loading;
    SharedPreferences getSharedPreferences;
    SharedPreferences.Editor editorShared ;
    int qtTentativas = 3;
    int qtTentativaRealizada = 0;
    int qtTentativaRealizadaBuscaS = 0;
    String idSalao;
    Permissoes permissoes;
    ShowcaseView sv;
    Tutorial tutorial;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fragment_principal_gerente, container, false);
        getActivity().setTitle("Bem Vindo");
        permissoes = new Permissoes();
        tutorial = new Tutorial(getContext());
        getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getContext());

        idSalao = getSharedPreferences.getString("idSalao", "-1");
        loading = new Loading(getActivity());
        btAbrir= (CardView) view.findViewById(R.id.card_aberto);
        btFechar= (CardView) view.findViewById(R.id.card_fechado);
        btAlmoco= (CardView) view.findViewById(R.id.card_almoco);
        btAgendaDia= (CardView) view.findViewById(R.id.card_bt1);
        btAgendar= (CardView) view.findViewById(R.id.card_bt2);
        btNotificar= (CardView) view.findViewById(R.id.card_bt3);
        btPesquisaSalao= (CardView) view.findViewById(R.id.card_bt4);
        btPesquisaSalao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(permissoes.habilitarLocalizacao(getActivity())) {
                    Intent intent = new Intent(getActivity(), busca_salao.class);
                    startActivity(intent);
                }
            }

        });

        btAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),pesqAgendaUser.class);
                intent.putExtra("idSalao",idSalao);
                startActivity(intent);
            }

        });

        btAgendaDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), minha_agenda.class);
                intent.putExtra("tipo","1");
                startActivity(intent);
            }

        });

        btNotificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Notificacao.class);
                startActivity(intent);
            }

        });


        btAbrir.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
              mudarStatus(1);
                return true;
            }
        });

        btAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText(getContext(),"Mantenha pressionado para atualizar o status", Toast.LENGTH_LONG).show();
            }
        });

        btFechar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
              mudarStatus(0);
                return true;
            }
        });

        btFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Mantenha pressionado para atualizar o status", Toast.LENGTH_LONG).show();
            }
        });


        btAlmoco.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               mudarStatus(2);
                return true;
            }
        });
        btAlmoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Mantenha pressionado para atualizar o status", Toast.LENGTH_LONG).show();
            }
        });

        pegarSalao(idSalao);
        return view;
    }


    public void tutorial(){
        if(!tutorial.verTutorial("fragmentGerente")) {
            sv = new ShowcaseView.Builder(getActivity())
                    .withMaterialShowcase()
                    .setTarget(new ViewTarget(btPesquisaSalao))
                    .setContentTitle("Pesquisar Salões")
                    .setContentText("Aqui você pode buscar por salões, agendar horarios...")
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .setShowcaseEventListener(new SimpleShowcaseEventListener() {

                        @Override
                        public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                            sv = new ShowcaseView.Builder(getActivity())
                                    .withMaterialShowcase()
                                    .setTarget(new ViewTarget(btAbrir))
                                    .setContentTitle("abrir salao")
                                    .setContentText("Mude o status do salão mantendo pressionado a opção desejada.")
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
    }

    public void statusSalao(int status)
    {
        if(status == 0)
        {
            btAbrir.setAlpha(.4f);
            btFechar.setAlpha(1f);
            btAlmoco.setAlpha(.4f);
        }
        else
        if(status == 1)
        {
            btAbrir.setAlpha(1f);
            btFechar.setAlpha(.4f);
            btAlmoco.setAlpha(.4f);
        }
        else
        {
            btAbrir.setAlpha(.4f);
            btFechar.setAlpha(.4f);
            btAlmoco.setAlpha(1f);
        }
    }


    public void mudarStatus(final int status)
    {
        loading.abrir("Aguarde...");
            RequestBody IDSALAO = RequestBody.create(MediaType.parse("text/plain"),idSalao);
            RequestBody STATUS = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(status));
            IApi iApi = IApi.retrofit.create(IApi.class);
            final Call<ResponseBody> callMudaStatus = iApi.EditarStatusSalao(IDSALAO,STATUS);
            callMudaStatus.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    loading.fechar();
                    qtTentativaRealizada = 0;
                    switch (response.code())
                    {
                        case 204:
                            statusSalao(status);
                            Toast.makeText(getContext(),"Status alterado com sucesso!!",Toast.LENGTH_LONG).show();
                            break;

                    }

                    callMudaStatus.cancel();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (qtTentativaRealizada < qtTentativas) {
                        qtTentativaRealizada++;
                        mudarStatus(status);
                    } else {
                        loading.fechar();
                    }
                }
            });
        }

    public void pegarSalao(final String id_Salao){

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<Salao>> callBuscaSalao = iApi.BuscaSalaoIdSalao(Integer.valueOf(id_Salao));
        callBuscaSalao.enqueue(new Callback<List<Salao>>() {
            @Override
            public void onResponse(Call<List<Salao>> call, Response<List<Salao>> response) {
                callBuscaSalao.cancel();
                switch (response.code()) {
                    case 200:
                        qtTentativaRealizadaBuscaS = 0;
                        List<Salao> saloes = response.body();
                        Salao salao = saloes.get(0);
                        statusSalao(salao.getStatus());
                        tutorial();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Salao>> call, Throwable t) {
                if (qtTentativaRealizadaBuscaS < qtTentativas) {
                    qtTentativaRealizadaBuscaS++;
                    pegarSalao(id_Salao);
                }
            }
        });

    }

}

