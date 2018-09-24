package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.servico;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.Notification.bancoNotificacoes.BancoNotifyController;
import com.stylehair.nerdsolutions.stylehair.Notification.notificacao;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Logout;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.ver_funcionario;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class viewHolderServicoSalao extends ViewHolder implements View.OnClickListener  {
    TextView NomeServico;
    TextView valor;
    LinearLayout card;
    Context contexto;
    ImageButton excluir;
    RecyclerView Lista;
    List<ServicoSalao> ListaServicoSalao;
    ServicoSalao servicoSalao;
    int qtTentativas = 3;
    int qtTentativaRealizada = 0;
    Loading loading;

    public viewHolderServicoSalao(View itemView, List<ServicoSalao> dados) {
        super(itemView);
        NomeServico = (TextView) itemView.findViewById(R.id.nome_servico);
        valor = (TextView) itemView.findViewById(R.id.valor_servico);
        card = (LinearLayout) itemView.findViewById(R.id.cardsServico);
        excluir = (ImageButton) itemView.findViewById(R.id.bt_excluir_servico);
        card.setOnClickListener(this);
        excluir.setOnClickListener(this);
        ListaServicoSalao = dados;
        contexto = itemView.getContext();
        loading = new Loading(((Activity)contexto));
    }

    @Override
    public void onClick(View v) {
        final int position = getAdapterPosition();
        servicoSalao = ListaServicoSalao.get(position);
        if (v.getId() == excluir.getId())
        {
            new AlertDialog.Builder(contexto)
                    .setTitle("Excluir")
                    .setMessage("Deseja excluir este serviço?")
                    .setIcon(R.drawable.icone_delete)
                    .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            excluiServico(String.valueOf(servicoSalao.getIdServicoSalao()),position);
                            loading.abrir("Aguarde...");
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
        if(v.getId() == card.getId())
        {
            Intent intent = new Intent(v.getContext(),ver_servico_salao.class);
            intent.putExtra("idSalao", String.valueOf(servicoSalao.getIdSalao()));
            intent.putExtra("idServico",String.valueOf(servicoSalao.getIdServicoSalao()));
            intent.putExtra("servico",servicoSalao.getServico());
            intent.putExtra("valor",String.valueOf(servicoSalao.getValor()));
            intent.putExtra("tempo",servicoSalao.getTempo());
            intent.putExtra("sexo",servicoSalao.getSexo());
            ((Activity)contexto).startActivityForResult(intent,2);
        }
    }

    public void excluiServico(String id, final int posi)
    {
            IApi iApi = IApi.retrofit.create(IApi.class);
            final Call<ResponseBody> callExcluiServico = iApi.ExcluirServicosSalao(id);
            callExcluiServico.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    loading.fechar();
                    qtTentativaRealizada = 0;
                    switch (response.code())
                    {
                        case 204:
                            ListaServicoSalao.remove(posi);
                            Lista.setAdapter(new Adaptador_servico_salao(ListaServicoSalao,Lista));
                            break;

                        case 400:
                            switch (response.message())
                            {
                                case "02":
                                    Toast.makeText(contexto,"Parametros incorretos!!",Toast.LENGTH_LONG).show();
                                    break;

                                case "05":
                                    Toast.makeText(contexto,"Erro ao excluir!!",Toast.LENGTH_LONG).show();
                                    break;
                            }
                            break;
                    }
                    callExcluiServico.cancel();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (qtTentativaRealizada < qtTentativas) {
                        qtTentativaRealizada++;
                        excluiServico(String.valueOf(servicoSalao.getIdServicoSalao()),posi);
                        loading.fechar();
                    } else {
                        loading.fechar();
                    }
                }
            });
        }
    }

