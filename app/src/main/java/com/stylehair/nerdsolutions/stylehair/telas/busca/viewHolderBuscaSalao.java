package com.stylehair.nerdsolutions.stylehair.telas.busca;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.TopicoNotificacao;
import com.stylehair.nerdsolutions.stylehair.classes.buscaSalao.BuscaSalao;
import com.stylehair.nerdsolutions.stylehair.classes.idNovoFavorito;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class viewHolderBuscaSalao extends ViewHolder implements View.OnClickListener  {
    CardView card;
    CircleImageView imagem;
    TextView nomeSalao;
    TextView pontos;
    TextView endereco;
    CardView bt_favorito;
    ImageView coracao;
    SharedPreferences getSharedPreferences;

    TextView distancia;
    CardView cardStatus;
    TextView status;
    Resources resources;
    Context contexto;
    BuscaSalao objeto;
    List<BuscaSalao> ListaObjeto;
    int qtTentativas = 3;
    int qtTentativaRealizada = 0;
    int qtTentativaRealizadaSalvar = 0;

    public viewHolderBuscaSalao(View itemView, List<BuscaSalao> datos) {
        super(itemView);
        imagem = (CircleImageView) itemView.findViewById(R.id.img_salao);
        nomeSalao = (TextView) itemView.findViewById(R.id.nome_salao);
        pontos = (TextView) itemView.findViewById(R.id.txtqt_pontos);
        endereco = (TextView) itemView.findViewById(R.id.endereco);
        bt_favorito = (CardView) itemView.findViewById(R.id.card_btFavorito);
        coracao = (ImageView) itemView.findViewById(R.id.coracao);
        distancia = (TextView) itemView.findViewById(R.id.distancia);
        cardStatus = (CardView) itemView.findViewById(R.id.cardStatus);
        status = (TextView) itemView.findViewById(R.id.status);
        ListaObjeto = datos;
        card = (CardView) itemView.findViewById(R.id.cardsBusca);
        card.setOnClickListener(this);
        bt_favorito.setOnClickListener(this);
        resources = itemView.getContext().getResources();
        contexto = itemView.getContext();
        getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(contexto);
    }

    @Override
    public void onClick(final View view) {
        int position = getAdapterPosition();
         objeto = ListaObjeto.get(position);

        if (view.getId() == bt_favorito.getId()) {
            if(objeto.getIdFavorito()>=0)
            {
                excluiFavorito(String.valueOf(objeto.getIdFavorito()),position);
            }
            else
            {
                SharedPreferences getSharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(contexto);
                String idLogin = String.valueOf(getSharedPreferences.getInt("idLogin", -1));
                salvaFavorito(String.valueOf(objeto.getIdSalao()),idLogin,position);
            }

        } else
        if(view.getId() == card.getId())
        {
            Intent intent = new Intent(view.getContext(),verSalao_buscado.class);
            intent.putExtra("idSalao",String.valueOf(objeto.getIdSalao()));
            intent.putExtra("idFavorito",String.valueOf(objeto.getIdFavorito()));
            ((Activity)view.getContext()).startActivityForResult(intent,2);
        }
    }


    public void excluiFavorito( final String id, final int posicao)
    {
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callExcluiFavorito = iApi.DeletarFavorito(id);
        callExcluiFavorito.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                qtTentativaRealizada = 0;
                switch (response.code())
                {
                    case 204:
                        objeto.setIdFavorito(-1);
                        atualizaCoracao(posicao,false);
                        TopicoNotificacao topicoNotificacao = new TopicoNotificacao();
                        topicoNotificacao.removeTopico(objeto.getTopicoNotificacao());


                        String topicos = getSharedPreferences.getString("topicosFavoritos","");
                        ArrayList<String> arrayTopFavoritos = new ArrayList<>();
                        Gson gson = new Gson();
                        if(!topicos.equals("")) {
                            arrayTopFavoritos = gson.fromJson(topicos, new TypeToken<ArrayList<String>>() {
                            }.getType());
                            for (int x = 0; x < arrayTopFavoritos.size(); x++) {
                                if (arrayTopFavoritos.get(x).equals(objeto.getTopicoNotificacao())) {
                                    arrayTopFavoritos.remove(x);
                                }
                            }

                            String jsonTopicoFavoritos = gson.toJson(arrayTopFavoritos);
                            SharedPreferences.Editor e = getSharedPreferences.edit();
                            e.putString("topicosFavoritos", jsonTopicoFavoritos);
                            e.apply();
                            e.commit();
                        }






                        break;

                    case 400:
                        Toast.makeText(contexto,"Houve um erro!!",Toast.LENGTH_LONG).show();
                        break;
                }
                callExcluiFavorito.cancel();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    excluiFavorito(id,posicao);
                }
            }
        });
    }

    public void salvaFavorito( final String idsalao,final  String idlogin, final int posicao)
    {
        RequestBody IDSALAO = RequestBody.create(MediaType.parse("text/plain"), idsalao);
        RequestBody IDLOGIN = RequestBody.create(MediaType.parse("text/plain"), idlogin);
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<idNovoFavorito> callSalvarFavorito = iApi.SalvarFavorito(IDSALAO,IDLOGIN);
        callSalvarFavorito.enqueue(new Callback<idNovoFavorito>() {
            @Override
            public void onResponse(Call<idNovoFavorito> call, Response<idNovoFavorito> response) {
                qtTentativaRealizadaSalvar = 0;
                switch (response.code())
                {
                    case 200:
                        idNovoFavorito IdNovoFavorito = new idNovoFavorito();
                        IdNovoFavorito = response.body();
                        objeto.setIdFavorito(IdNovoFavorito.getIdFavorito());
                        atualizaCoracao(posicao,true);
                        TopicoNotificacao topicoNotificacao = new TopicoNotificacao();
                        topicoNotificacao.addTopico(objeto.getTopicoNotificacao());


                        String topicos = getSharedPreferences.getString("topicosFavoritos","");
                        ArrayList<String> arrayTopFavoritos = new ArrayList<>();
                        Gson gson = new Gson();
                        if(!topicos.equals(""))
                        {
                            Log.d("xex", "tem coisa");
                            arrayTopFavoritos = gson.fromJson(topicos, new TypeToken<ArrayList<String>>(){}.getType());
                            if(arrayTopFavoritos.size()>0)
                            {
                                Log.d("xex", "é maior q zero");
                                for(int x=0;x<arrayTopFavoritos.size();x++)
                                {
                                    topicoNotificacao.removeTopico(arrayTopFavoritos.get(x));
                                    Log.d("xex", "remove topic - "+ arrayTopFavoritos.get(x));
                                }
                            }
                            arrayTopFavoritos.add(objeto.getTopicoNotificacao());
                        }
                        String jsonTopicoFavoritos = gson.toJson(arrayTopFavoritos);
                        SharedPreferences.Editor e = getSharedPreferences.edit();
                        e.putString("topicosFavoritos",jsonTopicoFavoritos);
                        e.apply();
                        e.commit();



                        break;

                    case 400:
                        Toast.makeText(contexto,"Houve um erro!!",Toast.LENGTH_LONG).show();
                        break;
                }
                callSalvarFavorito.cancel();
            }

            @Override
            public void onFailure(Call<idNovoFavorito> call, Throwable t) {
                if (qtTentativaRealizadaSalvar < qtTentativas) {
                    qtTentativaRealizadaSalvar++;
                    salvaFavorito(idsalao,idlogin,posicao);
                }
            }
        });
    }

    public void  atualizaCoracao(int posicao, boolean favorito)
    {
        if(favorito)
        {
            coracao.setImageDrawable(resources.getDrawable(R.drawable.icone_favorito));
        }
        else{
            coracao.setImageDrawable(resources.getDrawable(R.drawable.icone_favorito_of));
        }
    }
}
